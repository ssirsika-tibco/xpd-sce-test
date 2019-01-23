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
public class XSDBOM17_AnonymousTypeNameClashTest extends
        TransformationTestRoundtrip {

    protected String TEST_FILE_NAME = "myTest.bom";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/xsd";

    protected String MODEL_FILE = "XSDBOM17_AnonymousTypeNameClash.xsd";

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

        EList<PackageableElement> packagedElements =
                model.getPackagedElements();

        checkPrimitiveTypes(packagedElements);
        checkEnumerations(packagedElements);
        checkClazzes(packagedElements);

        exportTest();
    }

    private void checkClazzes(EList<PackageableElement> packagedElements)
            throws Exception {

        ArrayList<Class> allClazzes = new ArrayList<Class>();
        ArrayList<Association> allAssociations = new ArrayList<Association>();

        for (PackageableElement pe : packagedElements) {
            // Collect all the PackagedElements
            if (pe instanceof Class) {
                allClazzes.add((Class) pe);
            }
            if (pe instanceof Association) {
                allAssociations.add((Association) pe);
            }
        }

        assertEquals("Number of Classes expected=9", 9, allClazzes.size());

        Class class1 = null;
        Class class2 = null;

        EList<Property> ownedAttributes = null;

        // Check Apple1
        class1 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "apple1");

        ownedAttributes = class1.getOwnedAttributes();
        assertEquals("Apple1 should have 1 attribute.",
                1,
                ownedAttributes.size());
        Property prop = ownedAttributes.get(0);
        assertEquals("apple1 attribute name:",
                "topLevelAppleAttr",
                prop.getName());

        // Check AppleType
        class1 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "AppleType");

        ownedAttributes = class1.getOwnedAttributes();
        assertEquals("AppleType should have no attribute.",
                0,
                ownedAttributes.size());

        // Check BananaType1
        class1 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "BananaType1");

        ownedAttributes = class1.getOwnedAttributes();
        assertEquals("BananaType1 should have 1 attribute.",
                1,
                ownedAttributes.size());
        prop = ownedAttributes.get(0);
        assertEquals("BananaType1 attribute name:",
                "attrInBananaType1",
                prop.getName());

        // Check AppleType
        class1 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "Cherry");

        ownedAttributes = class1.getOwnedAttributes();
        assertEquals("Cherry should have no attribute.",
                0,
                ownedAttributes.size());

        // The following set of Classes are linked in a chain via Associations
        // Starts with Apple
        class1 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "Apple");

        class2 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "AppleType1");

        ownedAttributes = class1.getOwnedAttributes();
        assertEquals("Apple should have 1 attribute.",
                1,
                ownedAttributes.size());
        prop = ownedAttributes.get(0);
        assertEquals("Apple attribute name:", "apple", prop.getName());

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
        assertTrue("Association source should be Apple or AppleType1",
                (end0.getType() == class1) || (end0.getType() == class2));
        assertTrue("Association target should be Apple or AppleType1",
                (end1.getType() == class1) || (end1.getType() == class2));

        // And that it has an owned end of class2
        EList<Property> ownedEnds = assoc.getOwnedEnds();
        assertEquals("Size of owned ends should be 1", 1, ownedEnds.size());
        assertEquals("Owned end should be a Property of type Apple", ownedEnds
                .get(0).getType(), class1);

        System.out.println("");

        checkAssociatedClazzes(packagedElements);

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
                        "Apple");

        class2 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "AppleType1");

        ownedAttributes = class1.getOwnedAttributes();
        assertEquals("Apple should have 1 attribute.",
                1,
                ownedAttributes.size());
        prop = ownedAttributes.get(0);
        assertEquals("Apple attribute name:", "apple", prop.getName());

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
        assertTrue("Association source should be Apple or AppleType1",
                (end0.getType() == class1) || (end0.getType() == class2));
        assertTrue("Association target should be Apple or AppleType1",
                (end1.getType() == class1) || (end1.getType() == class2));

        // And that it has an owned end of class2
        EList<Property> ownedEnds = assoc.getOwnedEnds();
        assertEquals("Size of owned ends should be 1", 1, ownedEnds.size());
        assertEquals("Owned end should be a Property of type Apple", ownedEnds
                .get(0).getType(), class1);

        /*
         * Check the next two classes - When we have time we'll need to optimize
         * with reusable code
         */
        class1 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "AppleType1");

        class2 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "AppleType2");

        ownedAttributes = class1.getOwnedAttributes();
        assertEquals("AppleType1 should have 1 attribute.",
                1,
                ownedAttributes.size());
        prop = ownedAttributes.get(0);
        assertEquals("Apple attribute name:", "apple", prop.getName());

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
        assertTrue("Association source should be AppleType1 or AppleType2",
                (end0.getType() == class1) || (end0.getType() == class2));
        assertTrue("Association target should be AppleType1 or AppleType2",
                (end1.getType() == class1) || (end1.getType() == class2));

        // And that it has an owned end of class2
        ownedEnds = assoc.getOwnedEnds();
        assertEquals("Size of owned ends should be 1", 1, ownedEnds.size());
        assertEquals("Owned end should be a Property of type AppleType1",
                ownedEnds.get(0).getType(),
                class1);

        /*
         * Next two
         */
        class1 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "AppleType2");

        class2 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "bananaType");

        ownedAttributes = class1.getOwnedAttributes();
        assertEquals("AppleType2 should have 1 attribute.",
                1,
                ownedAttributes.size());
        prop = ownedAttributes.get(0);
        assertEquals("AppleType2 attribute name:", "banana", prop.getName());

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
        assertTrue("Association source should be AppleType2 or BananaType",
                (end0.getType() == class1) || (end0.getType() == class2));
        assertTrue("Association target should be AppleType2 or BananaType",
                (end1.getType() == class1) || (end1.getType() == class2));

        // And that it has an owned end of class2
        ownedEnds = assoc.getOwnedEnds();
        assertEquals("Size of owned ends should be 1", 1, ownedEnds.size());
        assertEquals("Owned end should be a Property of type AppleType1",
                ownedEnds.get(0).getType(),
                class1);

        /*
         * Next two
         */
        class1 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "bananaType");

        class2 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "BananaType3");

        ownedAttributes = class1.getOwnedAttributes();
        assertEquals("BananaType should have 4 attribute.",
                4,
                ownedAttributes.size());

        // 3 of the attributes are normal owned properties, the fourth is the
        // Composition's Property
        TransformUtil.assertAttributeInClass(ownedAttributes, "attr11", "Text");
        TransformUtil.assertAttributeInClass(ownedAttributes,
                "attr1",
                "Integer");
        TransformUtil
                .assertAttributeInClass(ownedAttributes, "appleType", null);

        prop = null;

        for (Property property : ownedAttributes) {
            if (property.getName().equals("banana")) {
                prop = property;
            }

        }

        assertNotNull(prop);
        assertEquals("BananaType attribute name should be banana",
                "banana",
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
        assertTrue("Association source should be BananaType or BananaType3",
                (end0.getType() == class1) || (end0.getType() == class2));
        assertTrue("Association target should be BananaType or BananaType3",
                (end1.getType() == class1) || (end1.getType() == class2));

        // And that it has an owned end of class2
        ownedEnds = assoc.getOwnedEnds();
        assertEquals("Size of owned ends should be 1", 1, ownedEnds.size());
        assertEquals("Owned end should be a Property of type BananaType",
                ownedEnds.get(0).getType(),
                class1);

        // ... and finally
        // BananaType3 should have a sigle attribute named "deepNestedBanana"
        TransformUtil.assertAttributeInClass(class2.getOwnedAttributes(),
                "deepNestedBananaName",
                "Text");

    }

    private void checkEnumerations(EList<PackageableElement> packagedElements)
            throws Exception {
        ArrayList<Enumeration> allEnums = new ArrayList<Enumeration>();

        for (PackageableElement pe : packagedElements) {
            // Collect all the PackagedElements
            if (pe instanceof Enumeration) {
                allEnums.add((Enumeration) pe);
            }
        }

        assertEquals("Number of Enumerations expected=3", 3, allEnums.size());

        // Check _cherry
        Enumeration eNmn = null;

        eNmn =
                TransformUtil
                        .assertPackagedElementEnumeration(packagedElements,
                                "_cherry1");

        TransformUtil.assertEnumLiteralCount(eNmn, 2);
        TransformUtil.assertEnumLiteralInEnum(eNmn.getOwnedLiterals(), "GLACE");
        TransformUtil.assertEnumLiteralInEnum(eNmn.getOwnedLiterals(),
                "MORELLO");

        // Check BananaType2
        eNmn =
                TransformUtil
                        .assertPackagedElementEnumeration(packagedElements,
                                "BananaType2");

        TransformUtil.assertEnumLiteralCount(eNmn, 2);
        TransformUtil.assertEnumLiteralInEnum(eNmn.getOwnedLiterals(), "CURVY");
        TransformUtil.assertEnumLiteralInEnum(eNmn.getOwnedLiterals(),
                "STRAIGHT");

        // Check AppleTypeType
        eNmn =
                TransformUtil
                        .assertPackagedElementEnumeration(packagedElements,
                                "AppleTypeType");

        TransformUtil.assertEnumLiteralCount(eNmn, 4);
        TransformUtil.assertEnumLiteralInEnum(eNmn.getOwnedLiterals(),
                "GRANNY_SMITH2");
        TransformUtil.assertEnumLiteralInEnum(eNmn.getOwnedLiterals(),
                "GRANNY_SMITH3");
        TransformUtil.assertEnumLiteralInEnum(eNmn.getOwnedLiterals(),
                "GRANNYSMITH");
        TransformUtil.assertEnumLiteralInEnum(eNmn.getOwnedLiterals(),
                "GRANNYSMITH1");

    }

    private void checkPrimitiveTypes(EList<PackageableElement> packagedElements)
            throws Exception {
        /*
         * Check the PrimitiveTypes
         */
        ArrayList<PrimitiveType> allPrimTypes = new ArrayList<PrimitiveType>();
        for (PackageableElement pe : packagedElements) {
            // Collect all the PackagedElements
            if (pe instanceof PrimitiveType) {
                allPrimTypes.add((PrimitiveType) pe);
            }
        }

        assertEquals("Number of Primitive types expected=3",
                3,
                allPrimTypes.size());

        // Check Banana1
        PrimitiveType pt = null;

        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "Banana");

        EList<Generalization> gens = pt.getGeneralizations();
        assertEquals("Number of generalizations for Banana", 1, gens.size());
        Generalization gen = gens.get(0);
        Classifier general = gen.getGeneral();
        assertTrue("Banana does not generalize a PrimitiveType",
                (general instanceof PrimitiveType));
        assertEquals("Expected type was Integer", "Integer", general.getName());

        // Check Banana2
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "banana1");

        gens = pt.getGeneralizations();
        assertEquals("Number of generalizations for Banana1", 1, gens.size());
        gen = gens.get(0);
        general = gen.getGeneral();
        assertTrue("Banana1 does not generalize a PrimitiveType",
                (general instanceof PrimitiveType));
        assertEquals("Expected type was Integer", "Integer", general.getName());

        // Check cherry1
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "cherry2");

        gens = pt.getGeneralizations();
        assertEquals("Number of generalizations for cherry2", 1, gens.size());
        gen = gens.get(0);
        general = gen.getGeneral();
        assertTrue("cherry2 does not generalize a PrimitiveType",
                (general instanceof PrimitiveType));
        assertEquals("Expected type was Decimal", "Decimal", general.getName());

    }
}
