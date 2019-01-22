/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.xsdbom_tle;

import java.io.File;
import java.io.IOException;
import java.util.List;

import junit.framework.Assert;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.uml2.uml.Model;

import com.tibco.xpd.bom.test.transform.util.TransformUtil;
import com.tibco.xpd.bom.validator.BOMValidatorActivator;

/**
 * Test extension of a complex type.
 * <p>
 * Tests 3.13 as described in 'BDS Support for Handling - Studio BOM Import'.
 * </p>
 * 
 * @author glewis
 * 
 */
public class XSDBOM_TLE13_DuplicateNamesValidationCheck extends AbstractTLETest {

    private static final String CHILDTYPE_CLASS = "ChildType";

    private static final String PERSON_CLASS = "Person";

    public XSDBOM_TLE13_DuplicateNamesValidationCheck() {
        super("XSDBOM_TLE10A_RootElementReference.xsd");

    }

    @Override
    public void testTransformation() throws Exception {
        IFile modelFile =
                outputSpecialFolder
                        .getFolder()
                        .getFile("com.example.XSDBOM_TLE10A_RootElementReference.bom");
        IPath outputBOMPath = modelFile.getFullPath();

        List<IStatus> result1 =
                importXSDtoBOM(new File(modelFiles.get(0).getLocationURI()),
                        outputBOMPath);

        /*
         * SID XPD-5118: This used to check for 1 error on result bom file -
         * however there were none.
         * 
         * I have checked the round trip in live studio and .bom2xsd copy
         * faithfully reproduces the original schema. So now we will check for 0
         * errors and zero errors in resultant bom.
         */
        List<IStatus> errors = getErrors(result1);
        Assert.assertEquals(0, errors.size());

        String[] destIds =
                new String[] { BOMValidatorActivator.VALIDATION_DEST_ID_XSD,
                        BOMValidatorActivator.VALIDATION_DEST_ID_BOMGENERIC,
                        BOMValidatorActivator.VALIDATION_DEST_ID_CDS };
        boolean hasErrorMarker =
                TransformUtil.isMarkerSeverityExist(destIds,
                        modelFile,
                        IMarker.SEVERITY_ERROR);
        assertEquals("There are problem markers on the BOM",
                false,
                hasErrorMarker);

    }

    @Override
    protected void checkBOMElements(Model model) throws Exception {
    }

    @Override
    protected void copyBuilderGoldFileResources() throws IOException,
            CoreException {
    }

    @Override
    protected void copyWizardGoldFileResources() throws IOException,
            CoreException {
    }
}
