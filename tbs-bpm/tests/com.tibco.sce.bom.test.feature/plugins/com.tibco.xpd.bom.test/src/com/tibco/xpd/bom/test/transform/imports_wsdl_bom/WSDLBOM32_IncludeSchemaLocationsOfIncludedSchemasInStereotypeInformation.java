/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.bom.test.transform.imports_wsdl_bom;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;
import org.openarchitectureware.workflow.issues.Issues;
import org.openarchitectureware.workflow.issues.IssuesImpl;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip;
import com.tibco.xpd.bom.test.transform.util.TransformUtil;
import com.tibco.xpd.bom.xsdtransform.XsdStereotypeUtils;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Junit test for protecting issue fixed in XPD-4166 which helps to have the
 * schema locations of included schemas as well in the stereotype information we
 * put for a bom package.
 * 
 * @author sajain
 * @since Jul 24, 2013
 */
public class WSDLBOM32_IncludeSchemaLocationsOfIncludedSchemasInStereotypeInformation
        extends TransformationTestRoundtrip {

    protected String TEST_FILE_NAME = "org.example.brm.bom";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/migrated";

    protected String MODEL_FILE1 = "RepWsdl.wsdl";

    protected String MODEL_FILE2 = "brm.xsd";

    protected String MODEL_FILE3 = "brmexception.xsd";

    protected String MODEL_FILE4 = "brmdto.xsd";

    protected String PLATFORM_BUILDER_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/xsd/gold/builder/XPD-4166";

    protected String GOLD_XSD_FILE1 = "org.example.RepWsdl.xsd";

    protected String GOLD_XSD_FILE2 = "org.example.brm.xsd";

    protected String GOLD_XSD_FILE3 = "org.example.brmexception.xsd";

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
        platformBuilderGoldFilesBase = PLATFORM_BUILDER_GOLD_FILES_BASE;

        modelFileNames.add(MODEL_FILE1);
        modelFileNames.add(MODEL_FILE2);
        modelFileNames.add(MODEL_FILE3);
        modelFileNames.add(MODEL_FILE4);

        goldFileNames.add(GOLD_XSD_FILE2);
        goldFileNames.add(GOLD_XSD_FILE1);
        goldFileNames.add(GOLD_XSD_FILE3);

        super.setUp();
    }

    @Override
    public void testTransformation() throws Exception {
        List<IStatus> statusArr =
                importWSDLtoBOM(new File(modelFiles.get(0).getLocationURI()),
                        outputSpecialFolder.getFolder().getFullPath()
                                .append(TEST_FILE_NAME));
        List<IStatus> errors = getErrors(statusArr);
        if (!errors.isEmpty()) {
            throw new Exception(errors.get(0).getMessage());
        }
        // check resulting bom file is correct
        Issues issues = new IssuesImpl();
        IFile file =
                TransformUtil.getOutputWSDLFile(issues,
                        modelFiles.get(0),
                        outputSpecialFolder.getFolder());
        assertTrue("Cannot find newly exported BOM file", file.exists()); //$NON-NLS-1$

        WorkingCopy wc = XpdResourcesPlugin.getDefault().getWorkingCopy(file);
        assertNotNull("Cannot create WorkingCopy for newly exported BOM file", wc); //$NON-NLS-1$
        assertTrue("Root element is null or not of type Model", wc.getRootElement() instanceof Model); //$NON-NLS-1$

        bomFile = outputSpecialFolder.getFolder().getFile(TEST_FILE_NAME);
        wc = XpdResourcesPlugin.getDefault().getWorkingCopy(bomFile);
        assertNotNull("Cannot create WorkingCopy for newly exported BOM file", wc); //$NON-NLS-1$
        assertTrue("Root element is null or not of type Model", wc.getRootElement() instanceof Model); //$NON-NLS-1$

        // check for the schema location stereotype on the generated bom package
        IResource[] members = outputSpecialFolder.getFolder().members();
        for (IResource member : members) {
            if (member instanceof IFile
                    && BOMResourcesPlugin.BOM_FILE_EXTENSION.equals(member
                            .getFileExtension())) {

                IFile generatedBOMFile = (IFile) member;

                // check resulting bom file is correct
                wc =
                        XpdResourcesPlugin.getDefault()
                                .getWorkingCopy(generatedBOMFile);
                assertNotNull("Cannot create WorkingCopy for newly exported BOM file", wc); //$NON-NLS-1$
                assertTrue("Root element is null or not of type Model", wc.getRootElement() instanceof Model); //$NON-NLS-1$

                Model model = (Model) wc.getRootElement();

                Package currentPackage = model.getNearestPackage();

                Iterator<Profile> profilesIter =
                        model.getAppliedProfiles().iterator();

                Stereotype stereotype = null;

                while (profilesIter.hasNext()) {
                    Profile profile = profilesIter.next();

                    if (profile
                            .getName()
                            .equals(XsdStereotypeUtils.XSD_NOTATION_PROFILE_NAME)) {
                        Iterator<Stereotype> ownedStereotypesIter =
                                profile.getOwnedStereotypes().iterator();
                        while (ownedStereotypesIter.hasNext()) {
                            stereotype = ownedStereotypesIter.next();
                            if (XsdStereotypeUtils.XSD_BASED_MODEL
                                    .equals(stereotype.getName())) {
                                Object value =
                                        currentPackage
                                                .getValue(stereotype,
                                                        XsdStereotypeUtils.XSD_SCHEMA_LOCATION);
                                if (member.getName()
                                        .equals("org.example.RepWsdl.bom"))
                                    Assert.assertEquals("Business Object Models/RepWsdl.wsdl",
                                            value.toString());
                                else if (member.getName()
                                        .equals("org.example.brm.bom"))
                                    Assert.assertEquals("Business Object Models/RepWsdl.wsdl;Business Object Models/brm.xsd;Business Object Models/brmdto.xsd",
                                            value.toString());
                                else if (member.getName()
                                        .equals("org.example.brmexception.bom"))
                                    Assert.assertEquals("Business Object Models/RepWsdl.wsdl;Business Object Models/brmexception.xsd",
                                            value.toString());
                            }
                        }
                    }
                }
            }
        }

        // Perform the export and validate the derived XSD. Make sure the Gold
        // files have been created and are in the correct location
        exportTest();
    }

    /**
     * @see com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip#exportTest()
     * 
     * @throws Exception
     */
    @Override
    public void exportTest() throws Exception {

        super.exportTest();
    }
}
