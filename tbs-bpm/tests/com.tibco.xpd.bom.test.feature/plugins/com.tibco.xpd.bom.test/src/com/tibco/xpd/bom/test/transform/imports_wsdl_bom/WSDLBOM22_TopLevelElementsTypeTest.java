/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.bom.test.transform.imports_wsdl_bom;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip;
import com.tibco.xpd.bom.test.transform.util.TransformUtil;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Transformation test for Top level elements generated from WSDL-BOM-XSD
 * process
 * 
 * @author rsawant
 * @since 09-Jul-2013
 */
public class WSDLBOM22_TopLevelElementsTypeTest extends
        TransformationTestRoundtrip {

    protected String TEST_FILE_NAME = "org.schema1.bom";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM22_TopLevelElementsTypeTest";

    protected String MODEL_FILE = "WSDLBOM22_TopLevelElementsTypeTest.wsdl";

    protected String MODEL_XSD_FILE = "org.schema1.xsd";

    protected String PLATFORM_WIZARD_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM22_TopLevelElementsTypeTest";

    protected String PLATFORM_BUILDER_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM22_TopLevelElementsTypeTest/goldXSDs";

    private ArrayList<Enumeration> allEnums = new ArrayList<Enumeration>();

    private ArrayList<Class> allClazzes = new ArrayList<Class>();

    private ArrayList<PrimitiveType> allPrimTypes =
            new ArrayList<PrimitiveType>();

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
        platformWizardGoldFilesBase = PLATFORM_WIZARD_GOLD_FILES_BASE;
        platformBuilderGoldFilesBase = PLATFORM_BUILDER_GOLD_FILES_BASE;

        modelFileNames.add(MODEL_FILE);
        goldFileNames.add(MODEL_XSD_FILE);

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

        bomFile = outputSpecialFolder.getFolder().getFile(TEST_FILE_NAME);
        assertTrue("Cannot find newly exported BOM file", bomFile.exists());

        WorkingCopy wc =
                XpdResourcesPlugin.getDefault().getWorkingCopy(bomFile);
        assertNotNull("Cannot create WorkingCopy for newly exported BOM file", wc); //$NON-NLS-1$
        assertTrue("Root element is null or not of type Model", wc.getRootElement() instanceof Model); //$NON-NLS-1$

        Model model = (Model) wc.getRootElement();

        checkBOMElements(model);

        exportTest();
    }

    /**
     * Main method for starting validation of Generated BOM elements
     * 
     * @param model
     * @throws Exception
     */
    private void checkBOMElements(Model model) throws Exception {

        EList<PackageableElement> packagedElements =
                model.getPackagedElements();
        for (PackageableElement pe : packagedElements) {
            if (pe instanceof Class) {
                allClazzes.add((Class) pe);
            } else if (pe instanceof Enumeration) {
                allEnums.add((Enumeration) pe);
            } else if (pe instanceof PrimitiveType) {
                allPrimTypes.add((PrimitiveType) pe);
            }
        }

        checkClazzes(packagedElements);
        checkPrimitiveTypes(packagedElements);
        checkEnumerations(packagedElements);
    }

    /**
     * @param packagedElements
     * @throws Exception
     */
    private void checkClazzes(EList<PackageableElement> packagedElements)
            throws Exception {

        assertEquals("Number of Classes expected:", 5, allClazzes.size());

        Class class1 = null;

        EList<Property> ownedAttributes = null;

        // Check BedragComponenten
        class1 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "BedragComponenten");

        ownedAttributes = class1.getOwnedAttributes();
        assertEquals("Check number of owned Attributes:",
                2,
                ownedAttributes.size());

        // Check DienstBetrekkingInkomstenType
        class1 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "DienstBetrekkingInkomstenType");

        ownedAttributes = class1.getOwnedAttributes();
        assertEquals("Check number of owned Attributes:",
                2,
                ownedAttributes.size());

        // Check LopendeLeningType
        class1 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "LopendeLeningType");

        ownedAttributes = class1.getOwnedAttributes();
        assertEquals("Check number of owned Attributes:",
                7,
                ownedAttributes.size());

        // Check MyAnonyType
        class1 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "MyAnonyType");

        ownedAttributes = class1.getOwnedAttributes();
        assertEquals("Check number of owned Attributes:",
                1,
                ownedAttributes.size());

        // Check UitgaveType
        class1 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "UitgaveType");

        ownedAttributes = class1.getOwnedAttributes();
        assertEquals("Check number of owned Attributes:",
                5,
                ownedAttributes.size());
    }

    /**
     * @param packagedElements
     * @throws Exception
     */
    private void checkEnumerations(EList<PackageableElement> packagedElements)
            throws Exception {

        assertEquals("Number of Enumerations expected", 1, allEnums.size());

        // Check SoortUitgavenType
        Enumeration eNmn = null;

        eNmn =
                TransformUtil
                        .assertPackagedElementEnumeration(packagedElements,
                                "SoortUitgavenType");

        TransformUtil.assertEnumLiteralCount(eNmn, 1);
        TransformUtil.assertEnumLiteralInEnum(eNmn.getOwnedLiterals(),
                "LOPENDE_LENING");
    }

    /**
     * @param packagedElements
     * @throws Exception
     */
    private void checkPrimitiveTypes(EList<PackageableElement> packagedElements)
            throws Exception {

        assertEquals("Number of Primitive types expected",
                1,
                allPrimTypes.size());

        // Check BedragType
        PrimitiveType pt = null;

        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "BedragType");

        PrimitiveType basePrimitiveType =
                PrimitivesUtil.getBasePrimitiveType(pt);

        assertTrue("Base Primitive Type:",
                basePrimitiveType.getName()
                        .equals(PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME));
    }
}
