/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.rules_wsdl;

import java.util.ArrayList;
import java.util.Collection;

import junit.framework.Assert;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;

import com.tibco.xpd.bom.test.transform.common.TransformationTest;
import com.tibco.xpd.bom.validator.BOMValidatorActivator;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.explicit.Validator;
import com.tibco.xpd.validation.provider.IIssue;

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
public class BOMWSDLRule03_OperationParamMultiplicityNotSupportedRule extends
        TransformationTest {

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/bom-wsdl-rules";

    protected String MODEL_FILE =
            "BOMWSDLRule03OperationParamMultiplicityNotSupportedRule.bom";

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
        String[] destIds =
                new String[] { BOMValidatorActivator.VALIDATION_DEST_ID_WSDL };
        Collection<com.tibco.xpd.validation.destinations.Destination> destinations =
                new ArrayList<com.tibco.xpd.validation.destinations.Destination>();

        for (String destId : destIds) {
            com.tibco.xpd.validation.destinations.Destination destination =
                    ValidationActivator.getDefault().getDestination(destId);
            if (destination != null) {
                destinations.add(destination);
            }
        }
        Validator validator = new Validator(destinations);
        Collection<IIssue> issues = validator.validate(modelFiles.get(0));
        boolean hasErrorMarker = false;
        for (IIssue issue : issues) {
            int markerSeverity = issue.getSeverity();
            if (markerSeverity == IMarker.SEVERITY_ERROR) {
                hasErrorMarker = true;
                break;
            }
        }

        Assert
                .assertTrue("No WSDL Error Marker has been found when it indeed is an invalid BOM for WSDL Destinations", hasErrorMarker); //$NON-NLS-1$
    }
}
