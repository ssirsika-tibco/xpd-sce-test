/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.rules_wsdl;

import java.util.ArrayList;

import junit.framework.Assert;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;

import com.tibco.xpd.bom.gen.ui.preferences.BomGenPreferenceConstants;
import com.tibco.xpd.bom.test.transform.common.TransformationTest;

/**
 * Its a very base transformation test it only test for now if transformation
 * will run without exceptions. You can later test manually the result in the
 * junit-workspace.
 * <p>
 * <i>Created: 16 April 2009</i>
 * </p>
 * 
 * @author glewis
 */
@SuppressWarnings("nls")
public class BOMWSDLRule01_ClassifierOtherBOMReferenceNotAllowedTest extends
        TransformationTest {

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/bom-wsdl-rules";

    protected String MODEL_FILE =
            "BOMWSDLRule01_ClassifierOtherBOMReferenceNotAllowedRule.bom";

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        modelFileNames = new ArrayList<String>();
        modelFiles = new ArrayList<IFile>();

        platformExampleFilesBase = PLATFORM_EXAMPLE_FILES_BASE;
        modelFileNames.add(MODEL_FILE);

        super.setUp();
    }

    public void testTransformation() throws Exception {
        IMarker[] markers =
                modelFiles
                        .get(0)
                        .findMarkers(BomGenPreferenceConstants.WSDL_MARKER_TYPE,
                                true,
                                IResource.DEPTH_INFINITE); ////$NON-NLS-1$
        boolean hasErrorMarker = false;
        for (IMarker marker : markers) {
            int markerSeverity =
                    marker.getAttribute(IMarker.SEVERITY,
                            IMarker.SEVERITY_WARNING);
            if (markerSeverity == IMarker.SEVERITY_ERROR) {
                hasErrorMarker = true;
                break;
            }
        }

        Assert
                .assertFalse("WSDL Error Marker has been found when it indeed is a valid BOM for WSDL Destinations", hasErrorMarker); //$NON-NLS-1$
    }
}
