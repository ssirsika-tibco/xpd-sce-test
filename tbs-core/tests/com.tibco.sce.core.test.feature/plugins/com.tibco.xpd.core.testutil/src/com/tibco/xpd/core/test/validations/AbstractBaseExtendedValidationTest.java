/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.core.test.validations;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;

import com.tibco.xpd.core.test.util.TestResourceInfo;

/**
 * AbstractBaseScriptValidationTest
 * 
 * This abstract class extends the AbstractBaseValidationTest in the way that
 * allows the validation of unregistered markers and also to reduce the scope of
 * the validation to the desired objects.
 * 
 * @author mtorres
 * @since 3.3 (18 March 2010)
 */
public abstract class AbstractBaseExtendedValidationTest extends
        AbstractBaseValidationTest {

    public AbstractBaseExtendedValidationTest(boolean isCheckProblemExists) {
        super(isCheckProblemExists);
    }

    @Override
    protected void doTestValidations() throws Exception {
        super.doTestValidations();
        if (shouldTestUnregisteredErrors()) {
            doTestUnregisteredErrors();
        }
    }

    public void doTestUnregisteredErrors() {
        StringBuffer failedStrings = new StringBuffer();
        boolean failed = false;
        TestResourceInfo[] testResources = getTestResources();
        if (testResources != null) {
            for (TestResourceInfo testResourceInfo : testResources) {
                if (shouldTestUnregisteredErrors(testResourceInfo)) {
                    // Get the test plugin source resource file.
                    Path path =
                            new Path(TestResourceInfo.PATH_SEPARATOR
                                    + testResourceInfo.getProjectName()
                                    + TestResourceInfo.PATH_SEPARATOR
                                    + testResourceInfo.getTestFilePath());
                    String resourceURI = testResourceInfo.getTestFilePath();

                    IResource resource =
                            ResourcesPlugin.getWorkspace().getRoot()
                                    .findMember(path);
                    if (resource == null || !resource.exists()) {
                        fail("Unable to find resource '" + resourceURI //$NON-NLS-1$
                                + "' for  test problem markers"); //$NON-NLS-1$
                    }
                    if (resUriToMarkerInfoMap != null) {
                        List<ValidationsTestProblemMarkerInfo> registeredMarkerInfoList =
                                resUriToMarkerInfoMap.get(path
                                        .toPortableString());
                        List<ValidationsTestProblemMarkerInfo> unregisteredProblemMarkers =
                                getUnregisteredProblemMarkers(resource,
                                        registeredMarkerInfoList);
                        if (unregisteredProblemMarkers != null
                                && !unregisteredProblemMarkers.isEmpty()) {
                            Set<String> affectedIds = getAffectedIds(resource);
                            for (ValidationsTestProblemMarkerInfo markerInfo : unregisteredProblemMarkers) {
                                if (affectedIds.contains(markerInfo
                                        .getLocationURI())) {
                                    failed = true;
                                    failedStrings
                                            .append("Unregistered Validation Problem Marker (" //$NON-NLS-1$
                                                    + markerInfo.getProblemId()
                                                    + ") was found for resource\n  '" //$NON-NLS-1$
                                                    + markerInfo
                                                            .getResourceURI()
                                                    + "." //$NON-NLS-1$
                                                    + markerInfo
                                                            .getLocationURI()
                                                    + ":\n  \"" //$NON-NLS-1$
                                                    + markerInfo
                                                            .getProblemText()
                                                    + "\"\n"); //$NON-NLS-1$                                      
                                    failedStrings
                                            .append("If this marker is expected, add the following code to the test: \n\n" //$NON-NLS-1$
                                                    + "new ValidationsTestProblemMarkerInfo(\n\"" //$NON-NLS-1$
                                                    + markerInfo
                                                            .getResourceURI()
                                                    + "\", //$NON-NLS-1$\n\"" //$NON-NLS-1$
                                                    + markerInfo.getProblemId()
                                                    + "\", //$NON-NLS-1$\n\"" //$NON-NLS-1$
                                                    + markerInfo
                                                            .getLocationURI()
                                                    + "\", //$NON-NLS-1$\n\"" //$NON-NLS-1$
                                                    + markerInfo
                                                            .getProblemText()
                                                    + "\", //$NON-NLS-1$\n\"" //$NON-NLS-1$
                                                    + markerInfo
                                                            .getQuickFixLabel()
                                                    + "\"), //$NON-NLS-1$\n\n"); //$NON-NLS-1$
                                }
                            }
                        }
                    }
                }
            }
        }

        if (failed) {
            fail(failedStrings.toString());
        }
    }

    /**
     * Get a List of Unregistered IMarkers that indicate an ERROR type problem -
     * converted to {@link ValidationsTestProblemMarkerInfo}'s so that direct
     * comparisons can be made.
     * 
     * @param resource
     *            A resource that contains the markers
     * @param registeredMarkerInfoList
     * 
     * @return A List of {@link ValidationsTestProblemMarkerInfo}
     */
    protected List<ValidationsTestProblemMarkerInfo> getUnregisteredProblemMarkers(
            IResource resource,
            List<ValidationsTestProblemMarkerInfo> registeredMarkerInfoList) {
        List<ValidationsTestProblemMarkerInfo> unregisteredMarkerInfoList =
                new ArrayList<ValidationsTestProblemMarkerInfo>();
        List<ValidationsTestProblemMarkerInfo> problemMarkers =
                getProblemMarkers(resource);
        if (problemMarkers != null) {
            if (registeredMarkerInfoList == null
                    || registeredMarkerInfoList.isEmpty()) {
                unregisteredMarkerInfoList.addAll(problemMarkers);
            } else {
                for (ValidationsTestProblemMarkerInfo marker : problemMarkers) {
                    if (registeredMarkerInfoList == null
                            || !registeredMarkerInfoList.contains(marker)) {
                        unregisteredMarkerInfoList.add(marker);
                    }

                }
            }
        }
        return unregisteredMarkerInfoList;
    }

    protected abstract Set<String> getAffectedIds(IResource resource);

    protected abstract boolean shouldTestUnregisteredErrors(
            TestResourceInfo testResource);

    protected boolean shouldTestUnregisteredErrors() {
        return true;
    }

}
