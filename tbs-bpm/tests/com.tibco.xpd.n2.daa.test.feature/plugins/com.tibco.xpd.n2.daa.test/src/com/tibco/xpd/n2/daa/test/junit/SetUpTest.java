/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.daa.test.junit;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.PlatformUI;

import com.tibco.amf.tools.packager.internal.validatedaa.ValidateDaaOperation;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.daa.internal.util.CompositeUtil;
import com.tibco.xpd.daa.internal.util.DAANamingUtils;
import com.tibco.xpd.destinations.GlobalDestinationUtil;
import com.tibco.xpd.n2.daa.ProjectDAAGenerator;
import com.tibco.xpd.n2.daa.internal.Messages;
import com.tibco.xpd.n2.daa.propertytester.DeployableBPMAssetsTester;
import com.tibco.xpd.n2.daa.test.Activator;
import com.tibco.xpd.n2.daa.test.junit.JunitConfiguration.ProjectType;
import com.tibco.xpd.n2.daa.utils.N2PENamingUtils;
import com.tibco.xpd.n2.resources.util.N2Utils;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.builder.BuildSynchronizerUtil;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.ui.util.PostImportUtil;
import com.tibco.xpd.resources.util.ProjectImporter;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.SubProgressMonitorEx;

/**
 * @author kupadhya
 * 
 */
public abstract class SetUpTest extends TestCase {

    private static final String RESOURCES_FOLDER = "resources/"; //$NON-NLS-1$

    private static final String FIRST_TIMESTAMP = "FIRST_TIMESTAMP"; //$NON-NLS-1$

    /**
     * @throws java.lang.Exception
     */
    @Override
    protected void setUp() throws Exception {

        try {
            BuildSynchronizerUtil
                    .waitForBuildsToFinish(new NullProgressMonitor());
            importProjects();
            generateDAA();

        } catch (InterruptedException e) {
            e.printStackTrace();
            throw e;
        }

    }

    protected void importProjects() {
        ProjectImporter projectImporter =
                ProjectImporter
                        .createPluginFolderProjectImporter(getContextPlugInId(),
                                getUATProjectFolder());
        projectImporter.performImport();
    }

    public void testProjectPresence() {
        List<String> projectNames =
                TestUtil.getAllStudioProjectNamesInWorkSpace();
        checkProjectExistance(projectNames, true);
    }

    private void checkProjectExistance(List<String> projectNames,
            boolean shouldExist) {
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        for (String projectName : projectNames) {
            IProject project = root.getProject(projectName);
            if (shouldExist) {
                assertTrue(project.exists());
                IFile generatedDAA =
                        ProjectDAAGenerator.getInstance().getDAAFile(project);
                if (generatedDAA != null) {
                    assertTrue(generatedDAA.exists());
                }
            } else {
                assertFalse(project.exists());
            }
        }
    }

    /**
     * Generate DAAs for all projects in the workspace.
     * 
     * @see #generateDAA(List)
     */
    protected void generateDAA() {
        generateDAA((List<ProjectType>) null);
    }

    /**
     * Generate DAAs for the given projects.
     * 
     * @param studioProjectTypes
     */
    protected List<IResource> generateDAA(List<ProjectType> studioProjectTypes) {
        List<IResource> allGeneratedDaas = new ArrayList<IResource>();
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

        if (studioProjectTypes == null || studioProjectTypes.isEmpty()) {
            TestUtil.getAllStudioProjectNamesInWorkSpace();
        }

        for (ProjectType projectType : studioProjectTypes) {
            String projectName = projectType.getName();
            int startIndex = projectType.getStartIndex();
            int iterations = projectType.getIterations();
            iterations = iterations + startIndex;
            IProject project = root.getProject(projectName);

            if (!project.exists()) {
                fail("Can't find project: " + project.getName()); //$NON-NLS-1$
            }
            if (TestUtil.isBPMStudioProject(project)) {
                try {
                    // waiting for all builders to run before attempting to
                    // migrate
                    // xpdl
                    try {
                        BuildSynchronizerUtil
                                .waitForBuildsToFinish(new NullProgressMonitor());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    TestUtil.migrateProjectXpdlFiles(project);

                    /*
                     * do the post import tasks only on bpm studio projects
                     * (skips on java projects etc.)
                     */
                    if (isBPMStudioProject(project)) {

                        PostImportUtil
                                .getInstance()
                                .performPostImportTasks(Collections.singletonList(project),
                                        SubProgressMonitorEx
                                                .createMainProgressMonitor(new NullProgressMonitor(),
                                                        1));
                    }

                    BuildSynchronizerUtil.synchronizedBuild(Collections
                            .singletonList(project),
                            new NullProgressMonitor(),
                            false);
                    String daaTimestamp = FIRST_TIMESTAMP;
                    int iterationDeadlockControler = 0;
                    // Iterations generation
                    for (int i = startIndex; i < iterations; i++) {
                        // We have to wait for 1 minute so daa file names don't
                        // collide
                        // TestUtil.delay(62000); //JA: for 60+ projects
                        // it's over hour wasted!!!!!
                        // Generate.

                        /*
                         * generate the daa only if there are any bpm deployable
                         * assets
                         */
                        if (shouldGenerateDAA(project)) {

                            assertTrue(String.format("DAA generation failed for project '%s'", //$NON-NLS-1$
                                    project.getName()),
                                    generateDAA(project));
                            ArrayList<IResource> generatedDAAs =
                                    SpecialFolderUtil
                                            .getResourcesInSpecialFolderOfKind(project,
                                                    N2PENamingUtils.COMPOSITE_MODULES_OUTPUT_KIND,
                                                    DAANamingUtils.DAA_FILE_EXTENSION);

                            assertTrue(String.format("Could not find generated DAA for project '%s'", //$NON-NLS-1$
                                    project.getName()),
                                    generatedDAAs.size() > 0);
                            IResource generatedDaa =
                                    generatedDAAs.iterator().next();
                            String nextDaaTimestamp =
                                    getDaaTimestamp(generatedDaa);
                            if (isDaaIterationCorrect(daaTimestamp,
                                    nextDaaTimestamp)) {
                                daaTimestamp = nextDaaTimestamp;
                                allGeneratedDaas.add(generatedDaa);
                                iterationDeadlockControler = 0;
                                // copyGeneratedDAAInExportFolder((IFile)
                                // generatedDaa,
                                // getOnlyTimestamp(nextDaaTimestamp,
                                // (IFile) generatedDaa));
                                copyGeneratedDAAInExportFolder((IFile) generatedDaa,
                                        ""); //$NON-NLS-1$
                            } else if (iterationDeadlockControler < 5) {
                                iterationDeadlockControler++;
                                i--;
                            }
                        } else {

                            assertTrue(String.format("generate DAA skipped for project '%s'", //$NON-NLS-1$
                                    project.getName()),
                                    true);
                        }

                        // Validate generated DAAs.
                        for (IResource daa : allGeneratedDaas) {
                            if (daa instanceof IFile) {
                                IFile daaFile = (IFile) daa;
                                ValidateDaaOperation validateDaaOperation =
                                        new ValidateDaaOperation(daaFile);
                                validateDaaOperation
                                        .run(new NullProgressMonitor());
                                IStatus validationStatus =
                                        validateDaaOperation
                                                .getValidationResults();
                                // TODO: uncomment this after the jira SDS-4022
                                // on
                                // this
                                // is fixed

                                // validationStatus.getMessage();

                                // assertTrue(String
                                // .format("DAA validation failed for project '%1$s'. Status: %2$s",
                                // project.getName(),
                                // validationStatus),
                                // validationStatus.getSeverity() <=
                                // IStatus.WARNING);
                            }
                        }
                    }

                } catch (CoreException e) {
                    e.printStackTrace();
                } catch (Throwable e) {
                    e.printStackTrace();
                    // fail();
                }
            }
        }
        return allGeneratedDaas;
    }

    private boolean shouldGenerateDAA(IProject project) {

        DeployableBPMAssetsTester tester = new DeployableBPMAssetsTester();
        return tester.test(project,
                DeployableBPMAssetsTester.PROP_HAS_DEPLOYABLE_BPM_ASSETS,
                new Object[] {},
                null);
    }

    private boolean isBPMStudioProject(IProject project) {

        if (project != null) {

            if (project.isAccessible()
                    && ProjectUtil.isStudioProject(project)
                    && GlobalDestinationUtil
                            .isGlobalDestinationEnabled(project,
                                    N2Utils.N2_GLOBAL_DESTINATION_ID))
                return true;
        }
        return false;
    }

    private String getOnlyTimestamp(String fileTimestamp, IFile file) {
        if (fileTimestamp != null) {
            String name = file.getName();
            if (name != null && name.endsWith(".daa")) { //$NON-NLS-1$
                name = name.replace(".daa", ""); //$NON-NLS-1$ //$NON-NLS-2$
                fileTimestamp = fileTimestamp.replace(name, ""); //$NON-NLS-1$
            }
        }
        return fileTimestamp;
    }

    private boolean isDaaIterationCorrect(String previousDaaTimestamp,
            String nextDaaTimestamp) {
        if (previousDaaTimestamp == null) {
            return false;
        } else if (previousDaaTimestamp.equals(FIRST_TIMESTAMP)) {
            return true;
        }
        if (nextDaaTimestamp != null
                && !nextDaaTimestamp.equals(previousDaaTimestamp)) {
            return true;
        }
        return false;
    }

    private String getDaaTimestamp(IResource nextDaa) {
        try {
            ZipFile zipFile = new ZipFile(nextDaa.getLocation().toFile());
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry nextElement = entries.nextElement();
                if (nextElement != null) {
                    String name = nextElement.getName();
                    if (name != null && name.endsWith(".composite")) { //$NON-NLS-1$
                        name = name.replaceFirst("plugins/", ""); //$NON-NLS-1$ //$NON-NLS-2$
                        if (name != null) {
                            int indexOf = name.indexOf("/"); //$NON-NLS-1$
                            if (indexOf != -1) {
                                return name.substring(0, indexOf);
                            }
                        }
                    }
                }
            }
        } catch (ZipException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    protected static final Logger LOG = XpdResourcesPlugin.getDefault()
            .getLogger();

    /**
     * Generates the DAA before deployment can take place
     * 
     * @param project
     * @return
     */
    protected boolean generateDAA(final IProject project) {
        if (!PlatformUI.getWorkbench().saveAllEditors(true)) {
            return false;
        }

        try {
            IRunnableContext runnable = new IRunnableContext() {
                public void run(boolean fork, boolean cancelable,
                        IRunnableWithProgress runnable)
                        throws InvocationTargetException, InterruptedException {
                    IWorkspace workspace = ResourcesPlugin.getWorkspace();
                    try {
                        project.build(IncrementalProjectBuilder.CLEAN_BUILD,
                                new NullProgressMonitor());
                    } catch (CoreException e) {
                        LOG.error(e);
                    }
                    if (workspace.isAutoBuilding()) {
                        try {
                            ResourcesPlugin
                                    .getWorkspace()
                                    .build(IncrementalProjectBuilder.INCREMENTAL_BUILD,
                                            new NullProgressMonitor());
                        } catch (CoreException e) {
                            LOG.error(e);
                        }
                    } else {
                        try {
                            project.build(IncrementalProjectBuilder.FULL_BUILD,
                                    new NullProgressMonitor());
                        } catch (CoreException e) {
                            LOG.error(e);
                        }
                    }
                }
            };

            runnable.run(true, true, null);

        } catch (InvocationTargetException e) {
            LOG.error(e);
        } catch (InterruptedException e) {
            LOG.error(e);
        }

        final boolean[] result = new boolean[1];

        JUnitMultiProjectDAAGenerationWithProgress daaGenOperation = null;
        try {

            boolean hasErrorLevelProblemMarkers =
                    CompositeUtil.hasErrorLevelProblemMarkers(project);
            if (hasErrorLevelProblemMarkers) {
                LOG.error(Messages.InputOutputSelectionWizardPage_projects_contain_errors_label);
                result[0] = false;
            } else {
                /*
                 * Sid XPD-7258: Allow sub-class to adjust configuration during
                 * DAA generation
                 */
                aboutToGenerateDAA(project);

                // Generate DAA for new selected projects.
                daaGenOperation =
                        new JUnitMultiProjectDAAGenerationWithProgress(
                                Arrays.asList(project), true);

                /*
                 * Sid XPD-7258: Allow sub-class to adjust configuration during
                 * DAA generation
                 */
                configureDAAGenOperation(daaGenOperation);

                /*
                 * Sid XPD-8243. Switch Xpdl2Bpel converter back to using
                 * numbers to suffix _BX_flow_xx activity names instead of UUID
                 * (as changed to in product code by default in this JIRA).
                 * Having UUID's is always going to fail the tests. So whilst it
                 * isn't ideal that the code executed isn't exactly the same in
                 * the test, the benefits of stable testing outway this
                 */
                System.setProperty("bx_flownames_number_suffixed", "TRUE"); //$NON-NLS-1$ //$NON-NLS-2$

                PlatformUI.getWorkbench().getProgressService()
                        .busyCursorWhile(daaGenOperation);
                result[0] = true;

            }
        } catch (InvocationTargetException e) {
            LOG.error(e);
            result[0] = false;
        } catch (InterruptedException e) {
            if (daaGenOperation != null
                    && daaGenOperation.getStatus() != null
                    && daaGenOperation.getStatus().getSeverity() != IStatus.CANCEL) {
                LOG.error(e);
            }
            result[0] = false;
        } catch (CoreException e1) {
            e1.printStackTrace();
            LOG.error(e1);
            result[0] = false;

        } catch (Exception e2) {
            e2.printStackTrace();
            LOG.error(e2);
            result[0] = false;

        } finally {
            /*
             * Sid XPD-8243. Unset the property that causes _BX_flow_xx activity
             * names to use number suffixes.
             */
            System.clearProperty("bx_flownames_number_suffixed"); //$NON-NLS-1$ 

            /*
             * Sid XPD-7258: Allow sub-class to adjust configuration during DAA
             * generation
             */
            generateDAADone(project);
        }

        return result[0];
    }

    /**
     * Configure DAA generation operation as appropriate.
     * 
     * @param daaGenOperation
     */
    protected void configureDAAGenOperation(
            JUnitMultiProjectDAAGenerationWithProgress daaGenOperation) {
        daaGenOperation.setReplaceQualifierWithTS(true);
    }

    /**
     * Sid XPD-7258: Allow sub-class to adjust configuration during DAA
     * generation Simple lifecycle method called just before DAA is generated.
     * 
     * @param project
     */
    protected void aboutToGenerateDAA(IProject project) {
    }

    /**
     * Sid XPD-7258: Allow sub-class to adjust configuration during DAA
     * generation Simple lifecycle method called just after DAA is generated.
     * <p>
     * Note that this method will be called whether or not DAA generation
     * failed.
     * 
     * @param project
     */
    protected void generateDAADone(IProject project) {
    }

    /**
     * Override this method to copy the generated DAA in Export Folder
     */
    protected abstract boolean copyGeneratedDAAInExportFolder(IFile daaFile,
            String suffix);

    protected String getUATProjectFolder() {
        return SetUpTest.RESOURCES_FOLDER;
    }

    protected String getContextPlugInId() {
        return Activator.PLUGIN_ID;
    }
}
