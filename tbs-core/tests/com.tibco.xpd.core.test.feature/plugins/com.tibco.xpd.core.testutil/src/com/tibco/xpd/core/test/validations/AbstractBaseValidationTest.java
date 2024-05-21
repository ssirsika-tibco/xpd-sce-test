/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.core.test.validations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.IMarkerResolution;

import com.tibco.xpd.core.test.util.AbstractBuildingBaseResourceTest;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.validation.provider.IIssue;
import com.tibco.xpd.validation.resolutions.GenericResolutionGenerator;

/**
 * AbstractValidationTest
 * 
 * 
 * @author aallway
 * @since 3.3 (24 Jul 2009)
 */
public abstract class AbstractBaseValidationTest
        extends AbstractBuildingBaseResourceTest {

    protected Map<String, List<ValidationsTestProblemMarkerInfo>> resUriToMarkerInfoMap;

    private final Boolean isCheckProblemExists;

    private boolean log = false;

    /**
     * @return LIst of problem markers infos defining each problem and resource
     *         to check.
     */
    protected abstract ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos();

    /**
     * @return Sorted List of problem markers infos defining each problem and
     *         resource to check.
     */
    public ValidationsTestProblemMarkerInfo[] getSortedValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] unsortedMarkerInfos =
                getValidationProblemMarkerInfos();
        List<ValidationsTestProblemMarkerInfo> sortedMarkerInfosList =
                Arrays.asList(unsortedMarkerInfos);
        Collections.sort(sortedMarkerInfosList);
        ValidationsTestProblemMarkerInfo[] sortedMarkerInfos =
                new ValidationsTestProblemMarkerInfo[sortedMarkerInfosList
                        .size()];

        for (int i = 0; i < sortedMarkerInfosList.size(); i++) {
            ValidationsTestProblemMarkerInfo validationsTestProblemMarkerInfo =
                    sortedMarkerInfosList.get(i);
            sortedMarkerInfos[i] = validationsTestProblemMarkerInfo;
        }
        return sortedMarkerInfos;
    }

    public AbstractBaseValidationTest(Boolean isCheckProblemExists) {
        this.isCheckProblemExists = isCheckProblemExists;
    }

    /**
     * 
     */
    public AbstractBaseValidationTest() {
        this(true);
    }

    protected void doTestValidations() throws Exception {
        System.out.println("******************"); //$NON-NLS-1$
        System.out.println("Test name:" + getTestName()); //$NON-NLS-1$
        // Check all files created correctly.
        checkTestFilesCreated();

        /*
         * Get info about problems markers to check for etc from specific test
         * sub-class
         */
        ValidationsTestProblemMarkerInfo[] markerInfos =
                getSortedValidationProblemMarkerInfos();

        Map<ValidationsTestProblemMarkerInfo, IMarker> testMarkerInfoToLiveMarkerMap =
                new HashMap<ValidationsTestProblemMarkerInfo, IMarker>();

        /*
         * Combine all the problems for individual resources.
         */
        resUriToMarkerInfoMap =
                new HashMap<String, List<ValidationsTestProblemMarkerInfo>>();

        for (ValidationsTestProblemMarkerInfo markerInfo : markerInfos) {
            List<ValidationsTestProblemMarkerInfo> resourceMarkerInfos =
                    resUriToMarkerInfoMap.get(markerInfo.getResourceURI());

            if (resourceMarkerInfos == null) {
                resourceMarkerInfos =
                        new ArrayList<ValidationsTestProblemMarkerInfo>();
                resUriToMarkerInfoMap.put(markerInfo.getResourceURI(),
                        resourceMarkerInfos);
            }

            resourceMarkerInfos.add(markerInfo);
        }

        if (log) {
            System.out.println(
                    "doTestValidations()- Test for...\n======================================================="); //$NON-NLS-1$
            for (Entry<String, List<ValidationsTestProblemMarkerInfo>> testEntry : resUriToMarkerInfoMap
                    .entrySet()) {
                System.out.println("  Resource: " + testEntry.getKey()); //$NON-NLS-1$

                List<ValidationsTestProblemMarkerInfo> resourceMarkerInfos =
                        testEntry.getValue();

                for (ValidationsTestProblemMarkerInfo markerInfo : resourceMarkerInfos) {
                    System.out.println("    Marker: " //$NON-NLS-1$
                            + markerInfo.getProblemText() + " (Location: " //$NON-NLS-1$
                            + markerInfo.getLocationURI() + ")"); //$NON-NLS-1$

                    if (markerInfo.getQuickFixLabel() != null
                            && markerInfo.getQuickFixLabel().length() > 0) {
                        System.out.println("       Fix: " //$NON-NLS-1$
                                + markerInfo.getQuickFixLabel());
                    }
                }
            }
            System.out.println(
                    "==================================================="); //$NON-NLS-1$
        }

        /*
         * Go thru each resource and then check that the selected problems are
         * present.
         */
        boolean failed = false;
        StringBuffer failedStrings = new StringBuffer();
        if (isCheckProblemExists) {
            failed = doTestsForProblemsThatExist(failedStrings,
                    testMarkerInfoToLiveMarkerMap);
        } else {
            failed = doTestsForProblemsThatShouldntExist(failedStrings,
                    testMarkerInfoToLiveMarkerMap);
        }

        if (failed) {
            fail(failedStrings.toString());
        }

        /*
         * TA DA!!!
         */
        return;
    }

    /**
     * @param failedStrings
     * @param testMarkerInfoToLiveMarkerMap
     * @return
     */
    private boolean doTestsForProblemsThatShouldntExist(
            StringBuffer failedStrings,
            Map<ValidationsTestProblemMarkerInfo, IMarker> testMarkerInfoToLiveMarkerMap) {
        boolean failed = false;
        for (Entry<String, List<ValidationsTestProblemMarkerInfo>> testEntry : resUriToMarkerInfoMap
                .entrySet()) {

            String resourceURI = testEntry.getKey();

            List<ValidationsTestProblemMarkerInfo> resourceMarkerInfos =
                    testEntry.getValue();

            /*
             * Get the resource itself.
             */
            IPath path = new Path(resourceURI);
            IResource problemResource =
                    ResourcesPlugin.getWorkspace().getRoot().findMember(path);

            if (!problemResource.exists()) {
                fail("Unable to find resource '" + resourceURI //$NON-NLS-1$
                        + "' for  test problem markers"); //$NON-NLS-1$
            }

            /*
             * Get Markers and check that all exist.
             */
            List<ValidationsTestProblemMarkerInfo> liveMarkerInfos =
                    getProblemMarkers(problemResource);

            for (ValidationsTestProblemMarkerInfo markerInfo : resourceMarkerInfos) {

                ValidationsTestProblemMarkerInfo liveMarkerInfo = null;

                for (ValidationsTestProblemMarkerInfo lmi : liveMarkerInfos) {
                    if (lmi.equals(markerInfo)) {
                        liveMarkerInfo = lmi;
                    }
                }

                if (liveMarkerInfo != null) {
                    failed = true;
                    failedStrings.append("Validation Problem Marker (" //$NON-NLS-1$
                            + markerInfo.getProblemId()
                            + ") was found for resource whereas it shouldnt have been there\n  '" //$NON-NLS-1$
                            + markerInfo.getResourceURI() + "." //$NON-NLS-1$
                            + markerInfo.getLocationURI() + ":\n  \"" //$NON-NLS-1$
                            + markerInfo.getProblemText() + "\"\n"); //$NON-NLS-1$
                    continue;
                }

            }
        }

        if (failed) {
            fail(failedStrings.toString());
        }

        return failed;
    }

    private boolean doTestsForProblemsThatExist(StringBuffer failedStrings,
            Map<ValidationsTestProblemMarkerInfo, IMarker> testMarkerInfoToLiveMarkerMap) {
        boolean failed = false;
        for (Entry<String, List<ValidationsTestProblemMarkerInfo>> testEntry : resUriToMarkerInfoMap
                .entrySet()) {

            String resourceURI = testEntry.getKey();

            List<ValidationsTestProblemMarkerInfo> resourceMarkerInfos =
                    testEntry.getValue();

            /*
             * Get the resource itself.
             */
            IPath path = new Path(resourceURI);
            IResource problemResource =
                    ResourcesPlugin.getWorkspace().getRoot().findMember(path);

            if (problemResource == null || !problemResource.exists()) {
                fail("Unable to find resource '" + resourceURI //$NON-NLS-1$
                        + "' for  test problem markers"); //$NON-NLS-1$
            }

            /*
             * Get Markers and check that all exist.
             */
            List<ValidationsTestProblemMarkerInfo> liveMarkerInfos =
                    getProblemMarkers(problemResource);

			if (log)
			{
				System.out.println("Problems Markers for resource : " + problemResource.getFullPath().toString());
				System.out.println("=========================================================================="); //$NON-NLS-1$

				for (ValidationsTestProblemMarkerInfo liveMarkerInfo : liveMarkerInfos)
				{
					System.out.println("    Marker: " //$NON-NLS-1$
							+ liveMarkerInfo.getProblemText() + " (Location: " //$NON-NLS-1$
							+ liveMarkerInfo.getLocationURI() + ")"); //$NON-NLS-1$

					if (liveMarkerInfo.getQuickFixLabel() != null && liveMarkerInfo.getQuickFixLabel().length() > 0)
					{
						System.out.println("       Fix: " //$NON-NLS-1$
								+ liveMarkerInfo.getQuickFixLabel());
					}
				}
				System.out.println("=========================================================================="); //$NON-NLS-1$

			}

            for (ValidationsTestProblemMarkerInfo markerInfo : resourceMarkerInfos) {

                ValidationsTestProblemMarkerInfo liveMarkerInfo = null;

                for (ValidationsTestProblemMarkerInfo lmi : liveMarkerInfos) {
                    if (lmi.equals(markerInfo)
                            || lmi.toString().equals(markerInfo.toString())) {
                        liveMarkerInfo = lmi;
                        /*
                         * We cannot have duplicate lmi, so once we find the
                         * matching from validation test, should break out of
                         * this loop
                         */
                        break;
                    }
                }

                if (liveMarkerInfo == null) {
                    failed = true;
                    failedStrings.append("Validation Problem Marker (" //$NON-NLS-1$
                            + markerInfo.getProblemId()
                            + ") was not found for resource\n  '" //$NON-NLS-1$
                            + markerInfo.getResourceURI() + "." //$NON-NLS-1$
                            + markerInfo.getLocationURI() + ":\n  \"" //$NON-NLS-1$
                            + markerInfo.getProblemText() + "\"\n"); //$NON-NLS-1$
                    continue;
                }

                // Store the actual live marker for this marker Info - we will
                // need it again when we do the (optional) quick fix defined in
                // test.
                testMarkerInfoToLiveMarkerMap.put(markerInfo,
                        liveMarkerInfo.getSourceMarker());

            }
        }

        if (failed) {
            fail(failedStrings.toString());
        }

        /*
         * Go thru each resource and then execute any selected quick fixes.
         */
        Set<IResource> changedResources = new HashSet<IResource>();
        boolean someQuickFixesDone = false;

        GenericResolutionGenerator quickFixGenerator =
                new GenericResolutionGenerator();

        //
        // For each resource...
        for (Entry<String, List<ValidationsTestProblemMarkerInfo>> testEntry : resUriToMarkerInfoMap
                .entrySet()) {
            List<ValidationsTestProblemMarkerInfo> resourceMarkerInfos =
                    testEntry.getValue();

            //
            // For each Validation Test markerInfo on resource
            for (ValidationsTestProblemMarkerInfo markerInfo : resourceMarkerInfos) {

                //
                // If it has a quick fix...
                String quickFixLabel = markerInfo.getQuickFixLabel();

                if (quickFixLabel != null && quickFixLabel.length() > 0) {
                    //
                    // Get the actual problem marker on the resource and get the
                    // available resolutions (we stored earlier).
                    IMarker marker =
                            testMarkerInfoToLiveMarkerMap.get(markerInfo);

                    IMarkerResolution liveQuickFix = null;

                    IMarkerResolution[] liveQuickFixes =
                            quickFixGenerator.getResolutions(marker);
                    if (liveQuickFixes != null) {
                        //
                        // FInd the quick fix resolution with the same label as
                        // the one in the test.
                        for (IMarkerResolution qf : liveQuickFixes) {
                            if (quickFixLabel.equals(qf.getLabel())) {
                                liveQuickFix = qf;
                                break;
                            }
                        }
                    }

                    if (liveQuickFix == null) {
                        failed = true;
                        failedStrings.append(
                                "Resolution Generator did not return test-defined quick fix '" //$NON-NLS-1$
                                        + quickFixLabel
                                        + "' for problem marker: \"" //$NON-NLS-1$
                                        + markerInfo.getProblemText() + "\"\n"); //$NON-NLS-1$
                        continue;
                    }

                    /*
                     * Everything ok - execute the quick fix.
                     */
                    liveQuickFix.run(marker);

                    someQuickFixesDone = true;
                    changedResources.add(marker.getResource());
                }
            }
        }

        if (failed) {
            fail(failedStrings.toString());
        }

        if (someQuickFixesDone) {
            /*
             * Now Save, re-do the build, wait for it to finish.
             * 
             * Strictly speaking the save is not necessary - but for debugging
             * purposes should teh test fail, it will be useful to be able to
             * set AbstractBAseResourceTest.cleanProjectAtEnd=false and the open
             * the work space in a normal studio and be able to look at the
             * quick fixed resources.
             */
            for (IResource changedRes : changedResources) {
                WorkingCopy wc = XpdResourcesPlugin.getDefault()
                        .getWorkingCopy(changedRes);
                try {
                    wc.save();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            buildAndWait();

            /*
             * Finally go thru each markerInfo with a quick fix and check that
             * the marker has not been added to resource after quick fix was
             * run.
             * 
             * i.e. check tyhat the quick fix fixed the problem.
             */
            for (Entry<String, List<ValidationsTestProblemMarkerInfo>> testEntry : resUriToMarkerInfoMap
                    .entrySet()) {

                String resourceURI = testEntry.getKey();

                List<ValidationsTestProblemMarkerInfo> resourceMarkerInfos =
                        testEntry.getValue();

                /*
                 * Get the resource itself.
                 */
                IPath path = new Path(resourceURI);
                IResource problemResource = ResourcesPlugin.getWorkspace()
                        .getRoot().findMember(path);

                /*
                 * Get Markers now associated with resource.
                 */
                List<ValidationsTestProblemMarkerInfo> liveMarkerInfos =
                        getProblemMarkers(problemResource);

                for (ValidationsTestProblemMarkerInfo markerInfo : resourceMarkerInfos) {
                    String quickFixClassName = markerInfo.getQuickFixLabel();

                    if (quickFixClassName != null
                            && quickFixClassName.length() > 0) {

                        if (liveMarkerInfos.contains(markerInfo)) {
                            failed = true;
                            failedStrings.append("Quick Fix '" //$NON-NLS-1$
                                    + quickFixClassName
                                    + "' did not fix the resource '" //$NON-NLS-1$
                                    + markerInfo.getResourceURI() + "." //$NON-NLS-1$
                                    + markerInfo.getLocationURI()
                                    + "' problem: " //$NON-NLS-1$
                                    + markerInfo.getProblemText() + "\n"); //$NON-NLS-1$
                        }

                    }
                }
            }
        }

        return failed;
    }

    /**
     * Get a List of IMarkers that indicate an ERROR type problem - converted to
     * {@link ValidationsTestProblemMarkerInfo}'s so that direct comparisons can
     * be made.
     * 
     * @param resource
     *            A resource that contains the markers
     * 
     * @return A List of {@link ValidationsTestProblemMarkerInfo}
     */
    protected List<ValidationsTestProblemMarkerInfo> getProblemMarkers(
            IResource resource) {
        List<ValidationsTestProblemMarkerInfo> markerInfoList =
                new ArrayList<ValidationsTestProblemMarkerInfo>();
        try {
            IMarker[] findMarkers = resource
                    .findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_ZERO);

            for (IMarker marker : findMarkers) {
                markerInfoList.add(new ValidationsTestProblemMarkerInfo(
                        marker.getResource().getFullPath().toPortableString(),
                        (String) marker.getAttribute(IIssue.ID),
                        (String) marker.getAttribute(IMarker.LOCATION),
                        marker.getAttribute(IMarker.MESSAGE, ""), "", marker)); //$NON-NLS-1$ //$NON-NLS-2$
            }

        } catch (CoreException e) {
            fail("Get Problem Markers Failed: " + e.getMessage()); //$NON-NLS-1$
            e.printStackTrace();
        }
        return markerInfoList;
    }

    /**
     * Waits for the validator to finish validation.
     * 
     * @throws InterruptedException
     * 
     */
    protected void waitForValidationToFinish() throws InterruptedException {

        /*
         * Need some delay so that the validation builder starts the validation
         * job. Without this, waitForValidatior might return while there may
         * still be some jobs in the queue.
         */
        Thread.sleep(2000);

        TestUtil.waitForValidatior();
    }

	/**
	 * @param log
	 *            the log to set
	 */
	public void setLog(boolean logOn)
	{
		this.log = logOn;
	}
}
