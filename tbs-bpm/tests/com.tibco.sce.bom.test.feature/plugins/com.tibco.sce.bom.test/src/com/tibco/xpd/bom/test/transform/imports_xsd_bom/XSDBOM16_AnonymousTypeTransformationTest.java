/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.imports_xsd_bom;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.xsd.XSDSchema;
import org.openarchitectureware.xsd.builder.OawXSDResource;

import com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip;
import com.tibco.xpd.bom.test.transform.util.TransformUtil;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * <p>
 * <i>Created: 29 Feb 2010</i>
 * </p>
 * 
 * @author glewis
 */
@SuppressWarnings("nls")
public class XSDBOM16_AnonymousTypeTransformationTest extends
        TransformationTestRoundtrip {

    protected String TEST_FILE_NAME = "myTest.bom";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/xsd";

    protected String MODEL_FILE =
            "XSDBOM16_AnonymousTypeTransformationTest.xsd";

    private ArrayList<Enumeration> allEnums = new ArrayList<Enumeration>();

    private ArrayList<Class> allClazzes = new ArrayList<Class>();

    private ArrayList<Association> allAssociations =
            new ArrayList<Association>();

    private ArrayList<PrimitiveType> allPrimTypes =
            new ArrayList<PrimitiveType>();

    protected String PLATFORM_WIZARD_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/xsd/gold/wizard";

    protected String PLATFORM_BUILDER_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/xsd/gold/builder";

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
        goldFileNames.add(MODEL_FILE);

        super.setUp();
    }

    @Override
    public void testTransformation() throws Exception {
        IFile modelFile = outputSpecialFolder.getFolder().getFile(MODEL_FILE);
        OawXSDResource resource =
                new OawXSDResource(URI.createURI(modelFile.getFullPath()
                        .toPortableString()));
        resource.load(Collections.EMPTY_MAP);
        XSDSchema schema = resource.getSchema();
        TEST_FILE_NAME =
                XSDUtil.getJavaPackageNameFromNamespaceURI(modelFile
                        .getProject(), schema.getTargetNamespace())
                        + ".bom"; //$NON-NLS-1$

        IPath outputBOMPath =
                outputSpecialFolder.getFolder().getFullPath()
                        .append(TEST_FILE_NAME);
        List<IStatus> result =
                importXSDtoBOM(new File(modelFiles.get(0).getLocationURI()),
                        outputBOMPath);
        List<IStatus> errors = getErrors(result);
        if (!errors.isEmpty()) {
            throw new Exception(errors.get(0).getMessage());
        }
        // check resulting bom file is correct
        bomFile =
                ResourcesPlugin.getWorkspace().getRoot().getFile(outputBOMPath);
        assertTrue("Cannot find newly exported BOM file", bomFile.exists()); //$NON-NLS-1$

        WorkingCopy wc =
                XpdResourcesPlugin.getDefault().getWorkingCopy(bomFile);
        assertNotNull("Cannot create WorkingCopy for newly exported BOM file", wc); //$NON-NLS-1$

        assertTrue("Root element is null or not of type Model", wc.getRootElement() instanceof Model); //$NON-NLS-1$

        Model model = (Model) wc.getRootElement();

        checkBOMElements(model);

        // exportTest();
    }

    private void checkBOMElements(Model model) throws Exception {
        EList<PackageableElement> packagedElements =
                model.getPackagedElements();

        // Collect all UML packaged elements
        for (PackageableElement pe : packagedElements) {
            if (pe instanceof Class) {
                allClazzes.add((Class) pe);
            } else if (pe instanceof Association) {
                allAssociations.add((Association) pe);
            } else if (pe instanceof Enumeration) {
                allEnums.add((Enumeration) pe);
            } else if (pe instanceof PrimitiveType) {
                allPrimTypes.add((PrimitiveType) pe);
            }
        }

        checkPrimitiveTypes(packagedElements);
        checkEnumerations(packagedElements);
        checkClazzes(packagedElements);

    }

    private void checkClazzes(EList<PackageableElement> packagedElements)
            throws Exception {

        assertEquals("Number of Classes expected:", 11, allClazzes.size());

        Class class1 = null;

        EList<Property> ownedAttributes = null;

        // Check TypeWithAnonymousSimpleType
        class1 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "typeWithAnonymousSimpleType");

        ownedAttributes = class1.getOwnedAttributes();
        assertEquals("Check number of owned Attributes:",
                2,
                ownedAttributes.size());

        Property prop1 = null;

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "typeCode",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        assertPropertyMultiplicity(prop1, 1, 1);

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "anonymousSimpleType",
                        PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);
        assertPropertyMultiplicity(prop1, 0, 1);

        // Check TypeWithAnonymousEnumeration
        class1 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "typeWithAnonymousEnumeration");

        ownedAttributes = class1.getOwnedAttributes();
        assertEquals("Check number of owned Attributes:",
                7,
                ownedAttributes.size());

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "issuerId",
                        PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);
        assertPropertyMultiplicity(prop1, 1, 1);

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "serialNumber",
                        PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);
        assertPropertyMultiplicity(prop1, 1, 1);

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "validForRefill",
                        PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME);
        assertPropertyMultiplicity(prop1, 1, 1);

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "validForActivation",
                        PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME);
        assertPropertyMultiplicity(prop1, 1, 1);

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "faceValue",
                        PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);
        assertPropertyMultiplicity(prop1, 1, 1);

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "typeCode",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        assertPropertyMultiplicity(prop1, 1, 1);

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "anonymousEnumeration",
                        null);
        assertPropertyMultiplicity(prop1, 1, 1);

        Type type = prop1.getType();
        assertTrue(type instanceof Enumeration);
        Enumeration en = (Enumeration) type;
        assertEquals("Name of Enumeration",
                "anonymousEnumerationType",
                en.getName());
        assertTrue("Enumeration type must exit in BOM", allEnums.contains(en));

        // Check TypeWithAnonymousExtensionOfSimpleType
        class1 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "typeWithAnonymousExtensionOfSimpleType");

        ownedAttributes = class1.getOwnedAttributes();
        assertEquals("Check number of owned Attributes:",
                7,
                ownedAttributes.size());

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "issuerId",
                        PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);
        assertPropertyMultiplicity(prop1, 1, 1);

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "serialNumber",
                        PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);
        assertPropertyMultiplicity(prop1, 1, 1);

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "validForRefill",
                        PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME);
        assertPropertyMultiplicity(prop1, 1, 1);

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "validForActivation",
                        PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME);
        assertPropertyMultiplicity(prop1, 1, 1);

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "faceValue",
                        PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);
        assertPropertyMultiplicity(prop1, 1, 1);

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "typeCode",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        assertPropertyMultiplicity(prop1, 1, 1);

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "anonymousExtensionOfSimpleType",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        assertPropertyMultiplicity(prop1, 1, 1);

        // Check TypeWithAnonymousRestrictionOfSimpleType
        class1 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "typeWithAnonymousRestrictionOfSimpleType");

        ownedAttributes = class1.getOwnedAttributes();
        assertEquals("Check number of owned Attributes:",
                3,
                ownedAttributes.size());

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "typeCode",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        assertPropertyMultiplicity(prop1, 1, 1);

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "anonymousExtensionOfSimpleType1",
                        PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);
        assertPropertyMultiplicity(prop1, 1, 1);

        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "anonymousExtensionOfSimpleType2",
                        null);
        assertPropertyMultiplicity(prop1, 1, 1);
        type = prop1.getType();
        assertTrue("Check attribute type is PrimitiveType",
                type instanceof PrimitiveType);
        assertTrue("Check attribute type PrimitiveType is user defined",
                type == allPrimTypes.get(0));

        checkAssociatedClazzes(packagedElements);

    }

    /**
     * Asserts the upper and lower Multiplicty values of the Property match the
     * values passed in.
     * 
     * @param prop
     * @param lowerValue
     * @param upperValue
     */
    private void assertPropertyMultiplicity(Property prop, int lowerValue,
            int upperValue) {

        int actualLower = prop.getLower();
        int actualUpper = prop.getUpper();

        assertEquals("Check Property multiplicity lower value",
                actualLower,
                lowerValue);
        assertEquals("Check Property multiplicity upper value",
                actualUpper,
                upperValue);
    }

    /**
     * Asserts that the Property type matches baseTypeName, where baseTypeName
     * should be one of the literals defined in PrimitiveUtils
     * 
     * @param prop
     * @param baseTypeName
     */
    private void assertPropertyBasePrimitiveType(Property prop,
            String baseTypeName) {

        Type type = prop.getType();
        assertTrue(type instanceof PrimitiveType);

        PrimitiveType basePrimitiveType =
                PrimitivesUtil.getBasePrimitiveType((PrimitiveType) type);

        assertTrue("Base Primitive Type:",
                basePrimitiveType.getName().equals(baseTypeName));

    }

    /**
     * Asserts that the Property type matches baseTypeName, where baseTypeName
     * should be one of the literals defined in PrimitiveUtils
     * 
     * @param prop
     * @param baseTypeName
     */
    private void assertPropertyType(Property prop, String baseTypeName) {

        Type type = prop.getType();
        assertTrue(type instanceof PrimitiveType);

        PrimitiveType basePrimitiveType =
                PrimitivesUtil.getBasePrimitiveType((PrimitiveType) type);

        assertTrue("Base Primitive Type:",
                basePrimitiveType.getName().equals(baseTypeName));

    }

    private void checkAssociatedClazzes(
            EList<PackageableElement> packagedElements) throws Exception {
        /*
         * Check the next two classes - When we have time we'll need to optimize
         * with reusable code
         */
        Class class1 = null;
        Class class2 = null;
        EList<Property> ownedAttributes = null;
        Property prop = null;

        // The following set of Classes are linked in a chain via Associations
        // Starts with Apple
        class1 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "typeWithAnonymousTypeRecursion");

        class2 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "anonymousComplexTypeFieldType1");

        ownedAttributes = class1.getOwnedAttributes();
        assertEquals("Check number of ownedAttributes:",
                1,
                ownedAttributes.size());
        prop = ownedAttributes.get(0);
        assertEquals("Check attribute name:",
                "anonymousComplexTypeField",
                prop.getName());

        Type type = prop.getType();
        assertEquals("Property type should be a Class", class2, type);
        Association assoc = prop.getAssociation();
        assertNotNull("Association should exist", assoc);
        TransformUtil.assertIsComposition(assoc, class1);

        // Now check the Association has the correct end members i.e. class1 and
        // class2
        EList<Property> memberEnds = assoc.getMemberEnds();
        Property end0 = memberEnds.get(0);
        Property end1 = memberEnds.get(1);
        assertTrue("", end0.getType() != end1.getType());
        assertTrue("Association source:",
                (end0.getType() == class1) || (end0.getType() == class2));
        assertTrue("Association target:",
                (end1.getType() == class1) || (end1.getType() == class2));

        // And that it has an owned end of class2 (because Association is a
        // Composition)
        EList<Property> ownedEnds = assoc.getOwnedEnds();
        assertEquals("Size of owned ends should be 1", 1, ownedEnds.size());
        assertEquals("Owned end should be a Property of type Class", ownedEnds
                .get(0).getType(), class1);

        /*
         * Check the next two classes - When we have time we'll need to optimize
         * with reusable code
         */
        class1 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "anonymousComplexTypeFieldType1");

        class2 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "anotherFieldOfTypeAnonymousType");

        ownedAttributes = class1.getOwnedAttributes();
        assertEquals("Check number of ownedAttributes:",
                1,
                ownedAttributes.size());
        prop = ownedAttributes.get(0);
        assertEquals("Check attribute name:",
                "anotherFieldOfTypeAnonymous",
                prop.getName());

        type = prop.getType();
        assertEquals("Property type should be a Class", class2, type);
        assoc = prop.getAssociation();
        assertNotNull("Association should exist", assoc);
        TransformUtil.assertIsComposition(assoc, class1);

        // Now check the Association has the correct end members i.e. class1 and
        // class2
        memberEnds = assoc.getMemberEnds();
        end0 = memberEnds.get(0);
        end1 = memberEnds.get(1);
        assertTrue("", end0.getType() != end1.getType());
        assertTrue("Association source:",
                (end0.getType() == class1) || (end0.getType() == class2));
        assertTrue("Association target:",
                (end1.getType() == class1) || (end1.getType() == class2));

        // And that it has an owned end of class2
        ownedEnds = assoc.getOwnedEnds();
        assertEquals("Size of owned ends should be 1", 1, ownedEnds.size());
        assertEquals("Owned end should be a Property of type Class", ownedEnds
                .get(0).getType(), class1);

        /*
         * Checks on the final class in the chain
         */
        prop =
                TransformUtil.assertAttributeInClass(class2
                        .getOwnedAttributes(),
                        "validForRefill",
                        PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME);
        assertPropertyMultiplicity(prop, 1, 1);

        /*
         * Check the next two classes - When we have time we'll need to optimize
         * with reusable code
         */
        class1 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "typeWithAnonymousComplexType");

        class2 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "anonymousComplexTypeFieldType");

        ownedAttributes = class1.getOwnedAttributes();
        assertEquals("Check number of ownedAttributes:",
                7,
                ownedAttributes.size());

        prop =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "issuerId",
                        PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);
        assertPropertyMultiplicity(prop, 1, 1);

        prop =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "serialNumber",
                        PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);
        assertPropertyMultiplicity(prop, 1, 1);

        prop =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "validForRefill",
                        PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME);
        assertPropertyMultiplicity(prop, 1, 1);

        prop =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "validForActivation",
                        PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME);
        assertPropertyMultiplicity(prop, 1, 1);

        prop =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "faceValue",
                        PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);
        assertPropertyMultiplicity(prop, 1, 1);

        prop =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "typeCode",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        assertPropertyMultiplicity(prop, 1, 1);

        prop =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "anonymousComplexTypeField",
                        null);
        assertPropertyMultiplicity(prop, 1, 1);

        type = prop.getType();
        assertEquals("Property type should be a Class", class2, type);
        assoc = prop.getAssociation();
        assertNotNull("Association should exist", assoc);
        TransformUtil.assertIsComposition(assoc, class1);

        // Now check the Association has the correct end members i.e. class1 and
        // class2
        memberEnds = assoc.getMemberEnds();
        end0 = memberEnds.get(0);
        end1 = memberEnds.get(1);
        assertTrue("", end0.getType() != end1.getType());
        assertTrue("Association source:",
                (end0.getType() == class1) || (end0.getType() == class2));
        assertTrue("Association target:",
                (end1.getType() == class1) || (end1.getType() == class2));

        // And that it has an owned end of class2 (because Association is a
        // Composition)
        ownedEnds = assoc.getOwnedEnds();
        assertEquals("Size of owned ends should be 1", 1, ownedEnds.size());
        assertEquals("Owned end should be a Property of type Class", ownedEnds
                .get(0).getType(), class1);

        /*
         * Check the next two classes - When we have time we'll need to optimize
         * with reusable code
         */
        class1 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "typeWithAnonymousExtensionOfComplexType");

        class2 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "anonymousExtensionOfComplexTypeType");

        ownedAttributes = class1.getOwnedAttributes();
        assertEquals("Check number of ownedAttributes:",
                1,
                ownedAttributes.size());

        prop =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "anonymousExtensionOfComplexType",
                        null);
        assertPropertyMultiplicity(prop, 1, 1);

        type = prop.getType();
        assertEquals("Property type should be a Class", class2, type);
        assoc = prop.getAssociation();
        assertNotNull("Association should exist", assoc);
        TransformUtil.assertIsComposition(assoc, class1);

        // Now check the Association has the correct end members i.e. class1 and
        // class2
        memberEnds = assoc.getMemberEnds();
        end0 = memberEnds.get(0);
        end1 = memberEnds.get(1);
        assertTrue("", end0.getType() != end1.getType());
        assertTrue("Association source:",
                (end0.getType() == class1) || (end0.getType() == class2));
        assertTrue("Association target:",
                (end1.getType() == class1) || (end1.getType() == class2));

        // And that it has an owned end of class2 (because Association is a
        // Composition)
        ownedEnds = assoc.getOwnedEnds();
        assertEquals("Size of owned ends should be 1", 1, ownedEnds.size());
        assertEquals("Owned end should be a Property of type Class", ownedEnds
                .get(0).getType(), class1);

        /*
         * Checks on the final class in the chain
         */
        ownedAttributes = class2.getOwnedAttributes();
        assertEquals("Check number of ownedAttributes:",
                3,
                ownedAttributes.size());

        prop =
                TransformUtil.assertAttributeInClass(class2
                        .getOwnedAttributes(),
                        "address",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        assertPropertyMultiplicity(prop, 1, 1);

        prop =
                TransformUtil.assertAttributeInClass(class2
                        .getOwnedAttributes(),
                        "city",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        assertPropertyMultiplicity(prop, 1, 1);

        prop =
                TransformUtil.assertAttributeInClass(class2
                        .getOwnedAttributes(),
                        "country",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        assertPropertyMultiplicity(prop, 1, 1);

        // and finally ... AnonymousExtensionOfComplexTypeType should generalize
        // TypeWithAnonymousComplexType
        EList<Generalization> gens = class2.getGeneralizations();

        assertEquals("Check number of generalizations:", 1, gens.size());

        Generalization gen = gens.get(0);
        Classifier general = gen.getGeneral();
        Class superClass =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "typeWithAnonymousComplexType");

        assertEquals("Check superclass", superClass, general);

    }

    private void checkEnumerations(EList<PackageableElement> packagedElements)
            throws Exception {

        assertEquals("Number of Enumerations expected", 1, allEnums.size());

        // Check AnonymousEnumerationType
        Enumeration eNmn = null;

        eNmn =
                TransformUtil
                        .assertPackagedElementEnumeration(packagedElements,
                                "anonymousEnumerationType");

        TransformUtil.assertEnumLiteralCount(eNmn, 2);
        TransformUtil.assertEnumLiteralInEnum(eNmn.getOwnedLiterals(),
                "ACTIVATION");
        TransformUtil
                .assertEnumLiteralInEnum(eNmn.getOwnedLiterals(), "REFILL");

    }

    private void checkPrimitiveTypes(EList<PackageableElement> packagedElements)
            throws Exception {

        assertEquals("Number of Primitive types expected",
                1,
                allPrimTypes.size());

        // Check MySimpleType
        PrimitiveType pt = null;

        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "MySimpleType");

        PrimitiveType basePrimitiveType =
                PrimitivesUtil.getBasePrimitiveType(pt);

        assertTrue("Base Primitive Type:",
                basePrimitiveType.getName()
                        .equals(PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME));

    }

}
