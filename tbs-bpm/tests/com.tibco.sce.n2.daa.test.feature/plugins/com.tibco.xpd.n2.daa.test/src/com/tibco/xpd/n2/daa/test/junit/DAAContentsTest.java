package com.tibco.xpd.n2.daa.test.junit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.tibco.xpd.core.test.util.FileComparator;
import com.tibco.xpd.core.test.util.FileComparatorExtensionManager;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.daa.internal.preferences.DAAGenPreferences;
import com.tibco.xpd.destinations.GlobalDestinationUtil;
import com.tibco.xpd.n2.daa.Activator;
import com.tibco.xpd.n2.daa.ProjectDAAGenerator;
import com.tibco.xpd.n2.daa.test.junit.listeners.ConsoleReporter;
import com.tibco.xpd.n2.daa.test.junit.listeners.SummaryReporter;
import com.tibco.xpd.n2.daa.test.util.DAAUtil;
import com.tibco.xpd.n2.resources.util.N2Utils;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.XpdConsts;

public abstract class DAAContentsTest extends TestCase {

    private static final String CRLF = "\r\n"; //$NON-NLS-1$

    public static final String GOLD_DAA_FOLDER = "GoldFiles"; //$NON-NLS-1$

    public static final String DAA = "daa"; //$NON-NLS-1$

    private static final String AMX_RAD_PROJECT = ".com.tibco.amx.rad"; //$NON-NLS-1$

    private static final String NO_GOLD_FILE_TXT = "NoGoldFile.txt"; //$NON-NLS-1$

    private List<DaaTestListener> listeners;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        listeners = new ArrayList<DaaTestListener>();
        listeners.add(new ConsoleReporter());
        try {
            listeners.add(new SummaryReporter(getSecondReportFileName()));
        } catch (IOException e) {
            System.err.println("Unable to instantiate Summary Report!!!"); //$NON-NLS-1$
        }
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();

        for (DaaTestListener listener : listeners) {
            listener.fireTestComplete();
        }
    }

    /**
     * test the entry count & compare contents of each file
     * 
     * @throws CoreException
     */
    public void testCompareGeneratedAndGoldDAA() throws CoreException {
        Map<String, List<IStatus>> testStatusMap =
                compareDAAs(TestUtil.getAllStudioProjectsInWorkSpace());

        /*
         * Get the DAA comparison report
         */
        StringBuffer reportStatus = reportStatus(testStatusMap);

        /*
         * If the report is not null that means that DAA comparison had errors.
         */
        if (reportStatus != null) {
            fail(reportStatus.toString());
        }
    }

    /**
     * Iterates the specified projects comparing generated DAAs and 'Gold' ones.
     * 
     * @param projectsToCompare
     * @return Map of status objects keyed by project name.
     * @throws CoreException
     */
    private Map<String, List<IStatus>> compareDAAs(
            List<IProject> projectsToCompare) throws CoreException {
        Map<String, List<IStatus>> testStatusMap =
                new HashMap<String, List<IStatus>>();
        for (IProject project : projectsToCompare) {

            String projectName = project.getName();

            /*
             * Ignore projects without BPM nature (so that supporting soa
             * projects for instance, don't get tested.
             */
            if (project.isAccessible()
                    && project.hasNature(XpdConsts.PROJECT_NATURE_ID)
                    && GlobalDestinationUtil
                            .isGlobalDestinationEnabled(project,
                                    N2Utils.N2_GLOBAL_DESTINATION_ID)) {

                List<IStatus> projectTestStatusList = new ArrayList<IStatus>();

                testStatusMap.put(projectName, projectTestStatusList);
                Status status =
                        new Status(IStatus.INFO, Activator.PLUGIN_ID,
                                "****Starting comparing gold & generated DAA for Project " //$NON-NLS-1$
                                        + projectName);
                fireTestEvent(projectName, status, DaaTestListener.Level.DEBUG);
                projectTestStatusList.add(status);
                compareGoldAndGeneratedDAA(project, projectTestStatusList);
                status =
                        new Status(IStatus.INFO, Activator.PLUGIN_ID,
                                "****Finished comparing gold & generated DAA for Project " //$NON-NLS-1$
                                        + projectName);
                fireTestEvent(projectName, status, DaaTestListener.Level.DEBUG);
                projectTestStatusList.add(status);
            }
        }
        return testStatusMap;
    }

    private void fireTestEvent(String projectName, IStatus iStatus,
            DaaTestListener.Level level) {
        for (DaaTestListener listener : listeners) {
            listener.fire(projectName, iStatus, level);
        }

    }

    /**
     * Reports status both to System.err and to a file for publication.
     * 
     * @param testStatusMap
     *            Status objects keyed by project name.
     * @return String containing the failed DAA compare report, if no failure
     *         then return <code>null</code>
     */
    private StringBuffer reportStatus(Map<String, List<IStatus>> testStatusMap) {
        Map<String, String> failedProjectsTestStatus =
                new HashMap<String, String>();
        Map<String, String> passedProjectsTestStatus =
                new HashMap<String, String>();
        /*
         * Stores the failure message
         */
        StringBuffer daaCompareResult = null;

        if (!testStatusMap.isEmpty()) {
            Set<Entry<String, List<IStatus>>> entrySet =
                    testStatusMap.entrySet();
            for (Entry<String, List<IStatus>> entry : entrySet) {
                String projectName = entry.getKey();
                List<IStatus> statusList = entry.getValue();
                String constructReportingString =
                        constructErrorReportingString(projectName, statusList);
                if (constructReportingString != null
                        && constructReportingString.length() > 0) {
                    failedProjectsTestStatus.put(projectName,
                            constructReportingString);
                } else {
                    constructReportingString = "No DAA mismatch reported"; //$NON-NLS-1$
                    passedProjectsTestStatus.put(projectName,
                            constructReportingString);
                    Status status =
                            new Status(IStatus.INFO, Activator.PLUGIN_ID,
                                    constructReportingString);
                    fireTestEvent(projectName,
                            status,
                            DaaTestListener.Level.SUMMARY);
                }

            }
        }

        File report = new File(getFirstReportFileName());
        System.err.println("Outputing acceptance test report to: " //$NON-NLS-1$
                + report.getAbsolutePath());
        PrintWriter out = null;
        try {
            out = new PrintWriter(report);
            // write header for report file
            out.println("Project\t\t\t\tResult"); //$NON-NLS-1$

            if (passedProjectsTestStatus.size() > 0) {
                Set<Entry<String, String>> passedProjectsEntrySet =
                        passedProjectsTestStatus.entrySet();
                for (Entry<String, String> entry : passedProjectsEntrySet) {
                    System.err.println("For Project " + entry.getKey() + ", " //$NON-NLS-1$ //$NON-NLS-2$
                            + entry.getValue());
                    out.println(entry.getKey() + "\t" + entry.getValue()); //$NON-NLS-1$
                }
            } else {
                System.err.println("No DAA comparison was successful"); //$NON-NLS-1$
            }

            if (failedProjectsTestStatus.size() > 0) {
                daaCompareResult = new StringBuffer();
                daaCompareResult
                        .append(String
                                .format("The Following %1$s Project/s failed during DAA comparison:", //$NON-NLS-1$
                                        failedProjectsTestStatus.size()));
                Set<Entry<String, String>> failedProjectsEntrySet =
                        failedProjectsTestStatus.entrySet();
                for (Entry<String, String> entry : failedProjectsEntrySet) {

                    String errorString =
                            "DAA comparison failed for Project '" //$NON-NLS-1$
                                    + entry.getKey() + "', " + entry.getValue(); //$NON-NLS-1$

                    daaCompareResult.append("\n"); //$NON-NLS-1$
                    daaCompareResult.append(errorString);

                    System.err.println(errorString);
                    out.println(entry.getKey() + "\t" + entry.getValue()); //$NON-NLS-1$
                }
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            out.close();
        }
        return daaCompareResult;
    }

    /**
     * Compares gold & generated DAA
     * 
     * @param project
     * @param projectTestStatusList
     */
    private void compareGoldAndGeneratedDAA(IProject project,
            List<IStatus> projectTestStatusList) {
        String errorMsg;
        String projectName = project.getName();

        IFile generatedDAA =
                ProjectDAAGenerator.getInstance().getDAAFile(project);
        boolean generatedDaaPresent =
                (generatedDAA != null) && generatedDAA.exists();
        IFile goldDAA = DAAUtil.getGoldDAA(project);
        boolean goldDaaPresent = (goldDAA != null) && goldDAA.exists();

        // First, check if we have an explanation file to add to report
        IFolder folder = project.getFolder(DAAContentsTest.GOLD_DAA_FOLDER);
        IFile noGoldFile = folder.getFile(NO_GOLD_FILE_TXT);
        boolean missingGoldDaaExplanationPresent =
                (noGoldFile != null) && noGoldFile.exists();

        // if there's no explanation file present and a gold DAA is present then
        // demand generated DAA be present
        if (!generatedDaaPresent && !missingGoldDaaExplanationPresent
                && goldDaaPresent) {
            errorMsg =
                    projectName
                            + " generated DAA could not be found\n" + describedProblemMarkers(project); //$NON-NLS-1$

            Status status =
                    new Status(IStatus.ERROR, Activator.PLUGIN_ID, errorMsg);
            projectTestStatusList.add(status);
            fireTestEvent(projectName, status, DaaTestListener.Level.SUMMARY);
            return;
        }

        if (missingGoldDaaExplanationPresent) {
            InputStream is = null;
            try {
                // read 40 chars from explanation file and output that
                is = noGoldFile.getContents();
                byte[] b = new byte[100];
                is.read(b, 0, 100);
                Status status =
                        new Status(IStatus.WARNING, Activator.PLUGIN_ID,
                                new String(b));
                projectTestStatusList.add(status);
                fireTestEvent(projectName,
                        status,
                        DaaTestListener.Level.SUMMARY);
                return;
            } catch (Exception e) {
                e.printStackTrace();
                // allow this to continue as if there was no explanation for the
                // lack of gold file
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    ;
                }
            }
        }

        // next, check if we have gold file to compare to
        if (!goldDaaPresent) {
            errorMsg = "Gold DAA could not be found"; //$NON-NLS-1$
            Status status =
                    new Status(IStatus.WARNING, Activator.PLUGIN_ID, errorMsg);
            projectTestStatusList.add(status);
            fireTestEvent(projectName, status, DaaTestListener.Level.SUMMARY);
            return;
        }

        try {
            String generatedOSPath = generatedDAA.getLocation().toString();
            ZipFile generatedDAAZip = new ZipFile(new File(generatedOSPath));
            String goldOSPath = goldDAA.getLocation().toString();
            ZipFile goldDAAZip = new ZipFile(new File(goldOSPath));

            // comparing the number of entries in the gold & generated DAA
            int goldDAASize = goldDAAZip.size();
            int generatedDAASize = generatedDAAZip.size();
            String goldMsg = " No. of entries in gold DAA " + goldDAASize; //$NON-NLS-1$
            Status status =
                    new Status(IStatus.INFO, Activator.PLUGIN_ID, goldMsg);
            projectTestStatusList.add(status);

            String generatedMsg =
                    " No. of entries in generated DAA " + generatedDAASize; //$NON-NLS-1$
            projectTestStatusList.add(new Status(IStatus.INFO,
                    Activator.PLUGIN_ID, generatedMsg));

            if (goldDAASize != generatedDAASize) {
                errorMsg = projectName + " entry count do not match" + goldMsg //$NON-NLS-1$
                        + generatedMsg;
                status =
                        new Status(IStatus.ERROR, Activator.PLUGIN_ID, errorMsg);
                projectTestStatusList.add(status);
                fireTestEvent(projectName, status, DaaTestListener.Level.DETAIL);
            }

            // comparing contents of each entry
            compareZipContents(project,
                    goldDAAZip,
                    generatedDAAZip,
                    projectTestStatusList);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            projectTestStatusList.add(new Status(IStatus.ERROR,
                    Activator.PLUGIN_ID, ioe.getMessage()));
        }
    }

    /**
     * @param project
     * @return textual list of problem markers
     */
    private String describedProblemMarkers(IProject project) {
        StringBuilder problems = new StringBuilder();
        problems.append("        Problem Markers For Project: "); //$NON-NLS-1$
        problems.append(project.getName() + "\n"); //$NON-NLS-1$
        problems.append("        -----------------------------------------------------------------\n"); //$NON-NLS-1$

        try {
            IMarker[] findMarkers =
                    project.findMarkers(IMarker.PROBLEM,
                            true,
                            IResource.DEPTH_INFINITE);

            if (findMarkers != null && findMarkers.length > 0) {
                for (IMarker iMarker : findMarkers) {
                    if (iMarker.getAttribute(IMarker.SEVERITY, 0) >= IMarker.SEVERITY_ERROR) {
                        problems.append("    " //$NON-NLS-1$
                                + iMarker.getAttribute(IMarker.MESSAGE,
                                        "<no message>") //$NON-NLS-1$
                                + "    Location: '" //$NON-NLS-1$
                                + (iMarker.getResource() != null ? iMarker
                                        .getResource().getFullPath().toString()
                                        : "<no location>") + "'\n"); //$NON-NLS-1$ //$NON-NLS-2$
                    }

                }
                problems.append("        -----------------------------------------------------------------\n\n"); //$NON-NLS-1$

            } else {
                problems.append("        - No Problem Markers found -\n"); //$NON-NLS-1$
                problems.append("        -----------------------------------------------------------------\n\n"); //$NON-NLS-1$
            }

        } catch (CoreException e) {
        }
        return problems.toString();
    }

    private String constructErrorReportingString(String projectName,
            List<IStatus> statusList) {
        StringBuilder toReturn = new StringBuilder();
        if (statusList != null) {
            for (IStatus status : statusList) {
                if (IStatus.ERROR == status.getSeverity()) {
                    String message = status.getMessage();
                    toReturn.append("ERROR:: " + message + DAAContentsTest.CRLF); //$NON-NLS-1$
                }
            }
        }
        if (toReturn.length() > 0) {
            toReturn.insert(0, "Problems reported for " + projectName //$NON-NLS-1$
                    + " are::" + DAAContentsTest.CRLF); //$NON-NLS-1$
        }
        return toReturn.toString();
    }

    private void compareZipContents(IProject project, ZipFile goldDAAZip,
            ZipFile generatedDAAZip, List<IStatus> projectTestStatusList)
            throws IOException {

        // comparing contents of each entry
        String projectId = ProjectUtil.getProjectId(project);
        String projectName = project.getName();
        String entryPath = "plugins/" + projectId + "_"; //$NON-NLS-1$ //$NON-NLS-2$
        String goldBQ = DAAUtil.getDAABuildQualifier(project, goldDAAZip);
        String generatedBQ =
                DAAUtil.getDAABuildQualifier(project, generatedDAAZip);

        Enumeration<? extends ZipEntry> goldEntries = goldDAAZip.entries();

        while (goldEntries.hasMoreElements()) {
            String errorMsg = ""; //$NON-NLS-1$
            ZipEntry goldZE = goldEntries.nextElement();
            String entryName = goldZE.getName();
            errorMsg = "Gold zip entry name is " + entryName; //$NON-NLS-1$
            Status status =
                    new Status(IStatus.INFO, Activator.PLUGIN_ID, errorMsg);
            projectTestStatusList.add(status);
            fireTestEvent(projectName, status, DaaTestListener.Level.DEBUG);

            /*
             * SId XPD-7258: It may be that there
             */
            if (entryName.startsWith(entryPath)) {
                entryName = entryName.replaceAll(goldBQ, generatedBQ);
            } else if (entryName.contains(goldBQ)) {

                /*
                 * helpful for replacing gold DAA time stamp with generated for
                 * jar entries in the daa
                 */
                entryName = entryName.replaceAll(goldBQ, generatedBQ);
            }
            ZipEntry generatedZE = generatedDAAZip.getEntry(entryName);
            if (generatedZE == null) {

                errorMsg =
                        "Generated zip entry not be found for " //$NON-NLS-1$
                                + entryName
                                + " so cannot compare & moving on with next entry"; //$NON-NLS-1$
                boolean cacheBomJars = DAAGenPreferences.cacheBomJars();

                /*
                 * bharti: When bom jar caching is turned off, the time stamp on
                 * jar is current time stamp (as the caching is turned off jar
                 * is generated always). So we will raise a warning instead of
                 * error. Even if the zip entry for jar file is found, we won't
                 * compare the jar files (as there is no file comparator for jar
                 * file)
                 * 
                 * The other option is to update the
                 * DAAUtil.getDAABuildQualifier() to return the current
                 * timestamp as the build qualifier for a jar file when the
                 * caching is turned off. That will help in find the zip entry
                 * name for the jar file from the generated daa zip, and will go
                 * past this condition. But when it tries to get the file
                 * comparator for the jar file, it fails to find one (because we
                 * don't have any comparator for a jar file). And we create a
                 * warning status when the file comparator is not found. So I
                 * think its ok we raise a warning here and ignore in case of
                 * jar entries (when caching is turned off).
                 */
                if (!cacheBomJars && entryName.endsWith(".jar")) { //$NON-NLS-1$

                    status =
                            new Status(IStatus.WARNING, Activator.PLUGIN_ID,
                                    errorMsg);
                } else {

                    status =
                            new Status(IStatus.ERROR, Activator.PLUGIN_ID,
                                    errorMsg);
                }
                projectTestStatusList.add(status);
                fireTestEvent(projectName, status, DaaTestListener.Level.DETAIL);
                continue;
            }
            // get Stream for zip entry
            InputStream goldIS = goldDAAZip.getInputStream(goldZE);
            InputStream generatedIS =
                    generatedDAAZip.getInputStream(generatedZE);
            String goldEntryName = goldZE.getName();
            String generatedEntryName = generatedZE.getName();

            // get file comparator
            FileComparator fileComparator =
                    FileComparatorExtensionManager.getInstance()
                            .getFileComparator(projectName,
                                    goldEntryName,
                                    generatedEntryName);
            if (fileComparator == null) {
                errorMsg =
                        " File Comparator could not be found for goldEntryName " //$NON-NLS-1$
                                + goldEntryName
                                + " and generatedEntryName " //$NON-NLS-1$
                                + generatedEntryName
                                + " so cannot compare & moving on with next entry"; //$NON-NLS-1$
                status =
                        new Status(IStatus.WARNING, Activator.PLUGIN_ID,
                                errorMsg);
                projectTestStatusList.add(status);
                fireTestEvent(projectName, status, DaaTestListener.Level.DETAIL);
                continue;
            }
            errorMsg =
                    "Starting comparing goldEntryName " + goldEntryName //$NON-NLS-1$
                            + " and generatedEntryName " + generatedEntryName; //$NON-NLS-1$
            status = new Status(IStatus.WARNING, Activator.PLUGIN_ID, errorMsg);
            projectTestStatusList.add(status);
            fireTestEvent(projectName, status, DaaTestListener.Level.DETAIL);

            // get gold zip entry to compare to
            InputStream massagedGoldIS =
                    fileComparator.massageInputStream(goldIS, project);
            InputStream massagedGeneratedIS =
                    fileComparator.massageInputStream(generatedIS, project);
            IStatus compareStatus =
                    fileComparator.compareContents(massagedGoldIS,
                            massagedGeneratedIS,
                            goldEntryName);
            if (compareStatus != null) {
                IStatus[] children = compareStatus.getChildren();
                Status summaryStatus =
                        new Status(IStatus.ERROR, Activator.PLUGIN_ID,
                                goldEntryName + " does not match"); //$NON-NLS-1$
                if (children != null && children.length > 0) {
                    boolean haveMismatch = false;
                    List<IStatus> asList = Arrays.asList(children);
                    projectTestStatusList.addAll(asList);
                    for (IStatus iStatus : asList) {
                        if (iStatus.getSeverity() == IStatus.ERROR) {
                            haveMismatch = true;
                        }
                        fireTestEvent(projectName,
                                iStatus,
                                DaaTestListener.Level.DETAIL);
                    }
                    if (haveMismatch) {
                        fireTestEvent(projectName,
                                summaryStatus,
                                DaaTestListener.Level.SUMMARY);
                    }
                } else {
                    projectTestStatusList.add(compareStatus);
                    if (compareStatus.getSeverity() == IStatus.ERROR) {
                        fireTestEvent(projectName,
                                summaryStatus,
                                DaaTestListener.Level.SUMMARY);
                    }
                    fireTestEvent(projectName,
                            compareStatus,
                            DaaTestListener.Level.DETAIL);
                }
            }

            // closing streams
            try {
                if (massagedGoldIS != null) {
                    massagedGoldIS.close();
                }
            } catch (IOException ioe) {
            }
            try {
                if (massagedGeneratedIS != null) {
                    massagedGeneratedIS.close();
                }
            } catch (IOException ioe) {
            }

        }
        goldDAAZip.close();
        generatedDAAZip.close();

    }

    private static final String FILE_PREFIX = "acceptance-test-report"; //$NON-NLS-1$

    private static final String FILE_SUFFIX = "txt"; //$NON-NLS-1$

    // acceptance-test-report2.txt
    private String getFirstReportFileName() {
        StringBuilder builder = new StringBuilder();
        builder.append(FILE_PREFIX);
        builder.append("1_"); //$NON-NLS-1$
        builder.append(getFileQualifier());
        builder.append("."); //$NON-NLS-1$
        builder.append(FILE_SUFFIX);
        return builder.toString();
    }

    private String getSecondReportFileName() {
        StringBuilder builder = new StringBuilder();
        builder.append(FILE_PREFIX);
        builder.append("2_"); //$NON-NLS-1$
        builder.append(getFileQualifier());
        builder.append("."); //$NON-NLS-1$
        builder.append(FILE_SUFFIX);
        return builder.toString();
    }

    protected abstract String getFileQualifier();
}
