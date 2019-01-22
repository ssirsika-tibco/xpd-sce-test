/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.bom.test.transform.imports_wsdl_bom;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip;
import com.tibco.xpd.bom.test.transform.util.TransformUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Junit test for protecting issue fixed in XPD-5786. Resolves the Global data
 * JavaScript issue where name couldn't be a JavaScript keyword (case)
 * (Property). Error was shown in Studio when we used to provide amx-bpm EC WSDL
 * for getting Case Audit.
 * 
 * @author sajain
 * @since Feb 25, 2014
 */
public class WSDLBOM38_NameConflicts_FixedIn_XPD_5786 extends
        TransformationTestRoundtrip {

    protected String TEST_FILE_NAME =
            "com.tibco.FOS.Clients.ProductService.bom";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM38_NameConflicts_FixedIn_XPD_5786";

    protected String MODEL_FILE1 = "Client_ProductService.wsdl";

    protected String MODEL_FILE2 = "Client_PriceService.wsdl";

    protected String MODEL_FILE3 = "Client_OfferService.wsdl";

    protected String GOLD_XSD_1 = "com.example.xmlns._13396641156247878.xsd";

    protected String GOLD_XSD_2 = "com.tibco.AFF.classes.order.xsd";

    protected String GOLD_XSD_3 = "com.tibco.AFF.classes.product.xsd";

    protected String GOLD_XSD_4 = "com.tibco.AFF.classes.resultstatus.xsd";

    protected String GOLD_XSD_5 = "com.tibco.AFF.classes.segment.xsd";

    protected String GOLD_XSD_6 = "com.tibco.AFF.OCV.services.xsd";

    protected String GOLD_XSD_7 = "com.tibco.AFF.PC.services.xsd";

    protected String GOLD_XSD_8 =
            "com.tibco.AFF.services.pricing.PriceService.xsd";

    protected String GOLD_XSD_9 = "com.tibco.AFF.V4._0._0.classes.order.xsd";

    protected String GOLD_XSD_10 =
            "com.tibco.AFF.V4._0._0.classes.resultstatus.xsd";

    protected String GOLD_XSD_11 = "com.tibco.FOS.Clients.OfferService.xsd";

    protected String GOLD_XSD_12 = "com.tibco.FOS.Clients.ProductService.xsd";

    protected String PLATFORM_WIZARD_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM38_NameConflicts_FixedIn_XPD_5786/gold/builder";

    protected String PLATFORM_BUILDER_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM38_NameConflicts_FixedIn_XPD_5786/gold/builder";

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

        modelFileNames.add(MODEL_FILE1);
        modelFileNames.add(MODEL_FILE2);
        modelFileNames.add(MODEL_FILE3);

        goldFileNames.add(GOLD_XSD_1);
        goldFileNames.add(GOLD_XSD_2);
        goldFileNames.add(GOLD_XSD_3);
        goldFileNames.add(GOLD_XSD_4);
        goldFileNames.add(GOLD_XSD_5);
        goldFileNames.add(GOLD_XSD_6);
        goldFileNames.add(GOLD_XSD_7);
        goldFileNames.add(GOLD_XSD_8);
        goldFileNames.add(GOLD_XSD_9);
        goldFileNames.add(GOLD_XSD_10);
        goldFileNames.add(GOLD_XSD_11);
        goldFileNames.add(GOLD_XSD_12);

        super.setUp();
    }

    /**
     * @see com.tibco.xpd.bom.test.transform.common.TransformationTest#tearDown()
     * 
     * @throws Exception
     */
    @Override
    protected void tearDown() throws Exception {
        // TODO Auto-generated method stub
        super.tearDown();
    }

    @Override
    public void testTransformation() throws Exception {
        List<IStatus> statusArr =
                importWSDLtoBOM(new File(modelFiles.get(0).getLocationURI()),
                        outputSpecialFolder.getFolder().getFullPath()
                                .append(TEST_FILE_NAME));

        List<IStatus> statusArr2 =
                importWSDLtoBOM(new File(modelFiles.get(1).getLocationURI()),
                        outputSpecialFolder.getFolder().getFullPath()
                                .append(TEST_FILE_NAME));

        List<IStatus> statusArr3 =
                importWSDLtoBOM(new File(modelFiles.get(2).getLocationURI()),
                        outputSpecialFolder.getFolder().getFullPath()
                                .append(TEST_FILE_NAME));
        if (!statusArr2.isEmpty()) {

            statusArr.addAll(statusArr2);
        }
        if (!statusArr3.isEmpty()) {

            statusArr.addAll(statusArr3);
        }

        List<IStatus> errors = getErrors(statusArr);
        if (!errors.isEmpty()) {
            throw new Exception(errors.get(0).getMessage());
        }

        int bomCount = 0;

        IResource[] members = outputSpecialFolder.getFolder().members();
        for (IResource member : members) {
            if (member instanceof IFile
                    && BOMResourcesPlugin.BOM_FILE_EXTENSION.equals(member
                            .getFileExtension())) {

                bomCount++;

                IFile generatedBOMFile = (IFile) member;
                WorkingCopy wc =
                        XpdResourcesPlugin.getDefault()
                                .getWorkingCopy(generatedBOMFile);
                assertNotNull("Cannot create WorkingCopy for newly exported BOM file", wc); //$NON-NLS-1$
                assertTrue("Root element is null or not of type Model", wc.getRootElement() instanceof Model); //$NON-NLS-1$

                Model model = (Model) wc.getRootElement();
                if (generatedBOMFile.getName()
                        .equals("com.tibco.AFF.classes.order.bom")) {
                    checkBOMElements1(model);
                } else if (generatedBOMFile.getName()
                        .equals("com.tibco.AFF.classes.resultstatus.bom")) {
                    checkBOMElements2(model);
                }
            }
        }

        /*
         * check number of BOMs.
         */
        assertEquals(12, bomCount);

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

    /**
     * @param model
     * @throws Exception
     */
    private void checkBOMElements1(Model model) throws Exception {

        EList<PackageableElement> packagedElements =
                model.getPackagedElements();

        ArrayList<org.eclipse.uml2.uml.Class> allClazzes =
                new ArrayList<org.eclipse.uml2.uml.Class>();

        ArrayList<org.eclipse.uml2.uml.Enumeration> allEnums =
                new ArrayList<org.eclipse.uml2.uml.Enumeration>();

        ArrayList<Association> allAssociations = new ArrayList<Association>();

        EList<Property> ownedAttributes = null;

        Property prop1 = null;

        for (PackageableElement packageableElement : packagedElements) {
            if (packageableElement instanceof Association) {
                allAssociations.add((Association) packageableElement);
            } else if (packageableElement instanceof org.eclipse.uml2.uml.Class) {
                allClazzes.add((org.eclipse.uml2.uml.Class) packageableElement);
            } else if (packageableElement instanceof org.eclipse.uml2.uml.Enumeration) {
                allEnums.add((org.eclipse.uml2.uml.Enumeration) packageableElement);
            }
        }

        // check for 1 ENum
        assertEquals("Number of Enumerations in model", 1, allEnums.size()); //$NON-NLS-1$

        // check name of Enum

        org.eclipse.uml2.uml.Enumeration enum1 =
                TransformUtil
                        .assertPackagedElementEnumeration(packagedElements,
                                "OrderLineActionType");

        // check literals
        EList<EnumerationLiteral> enumLits = enum1.getOwnedLiterals();

        assertEquals("Check number of owned Attributes:", 5, enumLits.size());
        EnumerationLiteral enumLit =
                TransformUtil.assertEnumLiteralInEnum(enumLits, "PROVIDE");
        enumLit = TransformUtil.assertEnumLiteralInEnum(enumLits, "UPDATE");
        enumLit = TransformUtil.assertEnumLiteralInEnum(enumLits, "CEASE");
        enumLit = TransformUtil.assertEnumLiteralInEnum(enumLits, "CANCEL");
        enumLit = TransformUtil.assertEnumLiteralInEnum(enumLits, "_");

        // check for 17 classes
        assertEquals("Number of classes in model", 17, allClazzes.size()); //$NON-NLS-1$

        // check name of class 1

        org.eclipse.uml2.uml.Class class1 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "SLAType"); //$NON-NLS-1$

        // check attributes
        ownedAttributes = class1.getOwnedAttributes();
        assertEquals("Check number of owned Attributes:",
                1,
                ownedAttributes.size());
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "SLAID",
                        "Text");

        // check name of class 2
        org.eclipse.uml2.uml.Class class2 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "SLAsType"); //$NON-NLS-1$

        ownedAttributes = class2.getOwnedAttributes();
        assertEquals("Check number of owned Attributes:",
                1,
                ownedAttributes.size());
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "SLA",
                        class1.getName());

        // check name of class 3
        org.eclipse.uml2.uml.Class class3 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "UDFType"); //$NON-NLS-1$

        ownedAttributes = class3.getOwnedAttributes();
        assertEquals("Check number of owned Attributes:",
                2,
                ownedAttributes.size());
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "name",
                        "Text");

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "value",
                        "Text");

        // check name of class 4
        org.eclipse.uml2.uml.Class class4 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "UDFsType"); //$NON-NLS-1$

        ownedAttributes = class4.getOwnedAttributes();
        assertEquals("Check number of owned Attributes:",
                1,
                ownedAttributes.size());
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "UDF",
                        class3.getName());

        // check name of class 5
        org.eclipse.uml2.uml.Class class5 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "InvoiceAddressType"); //$NON-NLS-1$

        ownedAttributes = class5.getOwnedAttributes();
        assertEquals("Check number of owned Attributes:",
                6,
                ownedAttributes.size());
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "addressLine1",
                        "Text");

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "addressLine2",
                        "Text");

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "locality",
                        "Text");

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "region",
                        "Text");

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "postCode",
                        "Text");

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "country",
                        "Text");

        // check name of class 6
        org.eclipse.uml2.uml.Class class6 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "DeliveryAddressType"); //$NON-NLS-1$

        ownedAttributes = class6.getOwnedAttributes();
        assertEquals("Check number of owned Attributes:",
                6,
                ownedAttributes.size());
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "addressLine1",
                        "Text");

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "addressLine2",
                        "Text");

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "locality",
                        "Text");

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "region",
                        "Text");

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "postCode",
                        "Text");

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "country",
                        "Text");

        // check name of class 7
        org.eclipse.uml2.uml.Class class7 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "OrderHeaderType"); //$NON-NLS-1$

        ownedAttributes = class7.getOwnedAttributes();
        assertEquals("Check number of owned Attributes:",
                17,
                ownedAttributes.size());
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "orderRef",
                        "Text");

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "version",
                        "Integer");

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "isLatestVersion",
                        "Boolean");

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "orderDescription",
                        "Text");

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "orderStatus",
                        "Text");

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "customerID",
                        "Text");

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "subscriberID",
                        "Text");

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "submittedDate",
                        "Date");

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "submittedTime",
                        "Time");

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "requiredByDate",
                        "DateTime");

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "expirationDate",
                        "Date");

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "expectedCompletionDate",
                        "DateTime");

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "invoiceAddress",
                        class5.getName());

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "deliveryAddress",
                        class6.getName());

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "notes",
                        "Text");
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "SLAs",
                        "SLAsType");
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "UDFs",
                        "UDFsType");

        // check name of class 8
        org.eclipse.uml2.uml.Class class8 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "ValueType"); //$NON-NLS-1$

        ownedAttributes = class8.getOwnedAttributes();
        assertEquals("Check number of owned Attributes:",
                5,
                ownedAttributes.size());
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "name",
                        "Text");
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "type",
                        "Text");
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "value",
                        "Text");
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "valueFrom",
                        "Text");
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "valueTo",
                        "Text");

        // check name of class 9
        org.eclipse.uml2.uml.Class class9 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "ValuesType"); //$NON-NLS-1$

        ownedAttributes = class9.getOwnedAttributes();
        assertEquals("Check number of owned Attributes:",
                1,
                ownedAttributes.size());

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "value",
                        "ValueType");

        // check name of class 10
        org.eclipse.uml2.uml.Class class10 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "CharacteristicType"); //$NON-NLS-1$

        ownedAttributes = class10.getOwnedAttributes();
        assertEquals("Check number of owned Attributes:",
                3,
                ownedAttributes.size());
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "name",
                        "Text");

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "description",
                        "Text");

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "values",
                        "ValuesType");

        // check name of class 11
        org.eclipse.uml2.uml.Class class11 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "OrderDetailsType"); //$NON-NLS-1$

        ownedAttributes = class11.getOwnedAttributes();
        assertEquals("Check number of owned Attributes:",
                1,
                ownedAttributes.size());

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "orderLines",
                        "OrderLinesType");

        // check name of class 12
        org.eclipse.uml2.uml.Class class12 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "OrderType"); //$NON-NLS-1$

        ownedAttributes = class12.getOwnedAttributes();
        assertEquals("Check number of owned Attributes:",
                3,
                ownedAttributes.size());
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "orderID",
                        "Text");

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "orderHeader",
                        class7.getName());

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "orderDetails",
                        class11.getName());

        // check name of class 13
        org.eclipse.uml2.uml.Class class13 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "OrderLineType"); //$NON-NLS-1$

        ownedAttributes = class13.getOwnedAttributes();
        assertEquals("Check number of owned Attributes:",
                18,
                ownedAttributes.size());
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "lineNumber",
                        "Text");
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "orderLineStatus",
                        "Text");
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "subscriberID",
                        "Text");
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "productID",
                        "Text");
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "quantity",
                        "Integer");
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "UOM",
                        "Text");
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "invoiceAddress",
                        class5.getName());
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "deliveryAddress",
                        class6.getName());
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "orderLineAction",
                        enum1.getName());
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "orderLineActionMode",
                        "Text");
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "requiredByDate",
                        "DateTime");
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "linkID",
                        "Text");
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "inventoryID",
                        "Text");
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "notes",
                        "Text");
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "customerItemID",
                        "Text");
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "SLAs",
                        "SLAsType1");
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "UDFs",
                        "UDFsType1");
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "characteristics",
                        "CharacteristicsType");

        // check name of class 14
        org.eclipse.uml2.uml.Class class14 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "OrderLinesType"); //$NON-NLS-1$

        ownedAttributes = class14.getOwnedAttributes();
        assertEquals("Check number of owned Attributes:",
                1,
                ownedAttributes.size());
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "orderLine",
                        class13.getName());

        // check name of class 15
        org.eclipse.uml2.uml.Class class15 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "SLAsType1"); //$NON-NLS-1$

        ownedAttributes = class15.getOwnedAttributes();
        assertEquals("Check number of owned Attributes:",
                1,
                ownedAttributes.size());
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "SLA",
                        class1.getName());

        // check name of class 16
        org.eclipse.uml2.uml.Class class16 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "CharacteristicsType"); //$NON-NLS-1$

        ownedAttributes = class16.getOwnedAttributes();
        assertEquals("Check number of owned Attributes:",
                1,
                ownedAttributes.size());
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "characteristic",
                        class10.getName());

        // check name of class 17
        org.eclipse.uml2.uml.Class class17 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "UDFsType1"); //$NON-NLS-1$

        ownedAttributes = class17.getOwnedAttributes();
        assertEquals("Check number of owned Attributes:",
                1,
                ownedAttributes.size());
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "UDF",
                        class3.getName());

        // there should be 8 associations
        assertEquals("Number of associations in model", 8, allAssociations.size()); //$NON-NLS-1$

        /*
         * Please note that all associations here are compositions whose name
         * have already been checked while checking the attribute names of the
         * respective classes.
         */
    }

    /**
     * @param model
     * @throws Exception
     */
    private void checkBOMElements2(Model model) throws Exception {

        EList<PackageableElement> packagedElements =
                model.getPackagedElements();

        ArrayList<org.eclipse.uml2.uml.Class> allClazzes =
                new ArrayList<org.eclipse.uml2.uml.Class>();

        ArrayList<Association> allAssociations = new ArrayList<Association>();

        EList<Property> ownedAttributes = null;

        Property prop1 = null;

        for (PackageableElement packageableElement : packagedElements) {
            if (packageableElement instanceof Association) {
                allAssociations.add((Association) packageableElement);
            } else if (packageableElement instanceof org.eclipse.uml2.uml.Class) {
                allClazzes.add((org.eclipse.uml2.uml.Class) packageableElement);
            }
        }

        // check for 1 class
        assertEquals("Number of classes in model", 1, allClazzes.size()); //$NON-NLS-1$

        // check name of class 1

        org.eclipse.uml2.uml.Class class1 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "ResultStatusType"); //$NON-NLS-1$

        // check attributes
        ownedAttributes = class1.getOwnedAttributes();
        assertEquals("Check number of owned Attributes:",
                6,
                ownedAttributes.size());
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "component",
                        "Text");
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "role",
                        "Text");
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "operation",
                        "Text");
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "severity",
                        "Text");
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "code",
                        "Text");
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "message",
                        "Text");

        // there shouldn't be any associations
        assertEquals("Number of associations in model", 0, allAssociations.size()); //$NON-NLS-1$
    }
}
