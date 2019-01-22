/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.bom.test.transform.imports_wsdl_bom;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * XPD-5930: Test that checks for bom generation of same named primitive types
 * in two schemas. schema2 has primitive type "MyString" and complex type with
 * an element of type "MyString". Schema1 also has a primitive type "MyString"
 * and complex type with an element of type "MyString" from same schema
 * 'Schema1' and one more complex type with an element of type "MyString" from
 * Schema2
 * 
 * @author bharge
 * @since 28 Feb 2014
 */
public class WSDLBOM36_SameNamePrimitiveTypeInTwoSchemasTest extends
        TransformationTestRoundtrip {

    protected String TEST_FILE_NAME =
            "WSDLBOM36_SameNamePrimitiveTypeInTwoSchemasTest";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM36_SameNamePrimTypeInTwoSchemas";

    // The name of the XML Schema that is being tested
    protected String MODEL_FILE = "PrimTypeWsdl.wsdl";

    protected String MODEL_XSD_FILE1 = "PrimTypeSchema1.xsd";

    protected String MODEL_XSD_FILE2 = "PrimTypeSchema2.xsd";

    protected String PLATFORM_WIZARD_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM36_SameNamePrimTypeInTwoSchemas/gold/builder";

    protected String PLATFORM_BUILDER_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM36_SameNamePrimTypeInTwoSchemas/gold/builder";

    protected String GOLD_XSD_FILE1 = "org.example.PrimSchema1.xsd";

    protected String GOLD_XSD_FILE2 = "org.example.PrimSchema2.xsd";

    /**
     * @see com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip#setUp()
     * 
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception {

        modelFileNames = new ArrayList<String>();
        modelFiles = new ArrayList<IFile>();

        platformExampleFilesBase = PLATFORM_EXAMPLE_FILES_BASE;
        platformWizardGoldFilesBase = PLATFORM_WIZARD_GOLD_FILES_BASE;
        platformBuilderGoldFilesBase = PLATFORM_BUILDER_GOLD_FILES_BASE;

        modelFileNames.add(MODEL_FILE);
        modelFileNames.add(MODEL_XSD_FILE1);
        modelFileNames.add(MODEL_XSD_FILE2);

        goldFileNames.add(GOLD_XSD_FILE1);
        goldFileNames.add(GOLD_XSD_FILE2);
        super.setUp();
    }

    /**
     * @see com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip#testTransformation()
     * 
     * @throws Exception
     */
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

        ArrayList<Class> schema1Classes = new ArrayList<Class>();
        ArrayList<Class> schema2Classes = new ArrayList<Class>();
        ArrayList<PrimitiveType> schema1PrimitiveTypes =
                new ArrayList<PrimitiveType>();
        ArrayList<PrimitiveType> schema2PrimitiveTypes =
                new ArrayList<PrimitiveType>();

        IResource[] members = outputSpecialFolder.getFolder().members();
        for (IResource member : members) {

            if (member instanceof IFile
                    && BOMResourcesPlugin.BOM_FILE_EXTENSION.equals(member
                            .getFileExtension())) {

                IFile generatedBOMFile = (IFile) member;
                WorkingCopy wc =
                        XpdResourcesPlugin.getDefault()
                                .getWorkingCopy(generatedBOMFile);
                assertNotNull("Cannot create WorkingCopy for newly exported BOM file", wc); //$NON-NLS-1$
                assertTrue("Root element is null or not of type Model", wc.getRootElement() instanceof Model); //$NON-NLS-1$
                Model model = (Model) wc.getRootElement();

                if ("org.example.PrimSchema1.bom".equals(generatedBOMFile
                        .getName())) {

                    schema1Classes = getSchema1Classes(model);
                    schema1PrimitiveTypes = getSchema1PrimitiveTypes(model);

                } else if ("org.example.PrimSchema2.bom"
                        .equals(generatedBOMFile.getName())) {

                    schema2Classes = getSchema2Classes(model);
                    schema2PrimitiveTypes = getSchema2PrimitiveTypes(model);
                }
            }
        }
        checkSchema1Elements(schema1Classes,
                schema1PrimitiveTypes,
                schema2PrimitiveTypes);
        checkSchema2Elements(schema2Classes, schema2PrimitiveTypes);
        exportTest();
    }

    private ArrayList<PrimitiveType> getSchema2PrimitiveTypes(Model model) {

        ArrayList<PrimitiveType> allPrimitiveTypes =
                new ArrayList<PrimitiveType>();

        EList<PackageableElement> packagedElements =
                model.getPackagedElements();
        for (PackageableElement packageableElement : packagedElements) {

            if (packageableElement instanceof PrimitiveType) {

                allPrimitiveTypes.add((PrimitiveType) packageableElement);
            }
        }
        return allPrimitiveTypes;
    }

    private ArrayList<PrimitiveType> getSchema1PrimitiveTypes(Model model) {

        ArrayList<PrimitiveType> allPrimitiveTypes =
                new ArrayList<PrimitiveType>();

        EList<PackageableElement> packagedElements =
                model.getPackagedElements();
        for (PackageableElement packageableElement : packagedElements) {

            if (packageableElement instanceof PrimitiveType) {

                allPrimitiveTypes.add((PrimitiveType) packageableElement);
            }
        }
        return allPrimitiveTypes;
    }

    private ArrayList<Class> getSchema1Classes(Model model) {

        ArrayList<Class> allClazzes = new ArrayList<Class>();

        EList<PackageableElement> packagedElements =
                model.getPackagedElements();
        for (PackageableElement packageableElement : packagedElements) {

            if (packageableElement instanceof Class) {

                allClazzes.add((Class) packageableElement);
            }
        }
        return allClazzes;
    }

    private ArrayList<Class> getSchema2Classes(Model model) {

        ArrayList<Class> allClazzes = new ArrayList<Class>();

        EList<PackageableElement> packagedElements =
                model.getPackagedElements();
        for (PackageableElement packageableElement : packagedElements) {

            if (packageableElement instanceof Class) {

                allClazzes.add((Class) packageableElement);
            }
        }
        return allClazzes;
    }

    private void checkSchema1Elements(ArrayList<Class> schema1Classes,
            ArrayList<PrimitiveType> schema1PrimitiveTypes,
            ArrayList<PrimitiveType> schema2PrimitiveTypes) {

        /* We expect 2 Classes */
        Assert.assertEquals("Unexpected number of Classes.",
                2,
                schema1Classes.size());
        /* We expect 1 Primitive type */
        Assert.assertEquals("Unexpected number of Primitive Types.",
                1,
                schema1PrimitiveTypes.size());

        for (Class class1 : schema1Classes) {

            if ("ComplexWithPrimTypeInSameSchema".equals(class1.getName())) {

                EList<Property> ownedAttributes = class1.getOwnedAttributes();
                for (Property property : ownedAttributes) {

                    assertEquals("Unexpected Primitive type",
                            schema1PrimitiveTypes.get(0).getName(),
                            property.getType().getName());

                    assertEquals("Unexpected Primitive type",
                            schema1PrimitiveTypes.get(0).getQualifiedName(),
                            property.getType().getQualifiedName());
                }
            } else if ("ComplexWithPrimTypeInOtherSchema".equals(class1
                    .getName())) {

                EList<Property> ownedAttributes = class1.getOwnedAttributes();
                for (Property property : ownedAttributes) {

                    assertEquals("Unexpected Primitive type",
                            schema2PrimitiveTypes.get(0).getName(),
                            property.getType().getName());

                    assertEquals("Unexpected Primitive type",
                            schema2PrimitiveTypes.get(0).getQualifiedName(),
                            property.getType().getQualifiedName());
                }
            }
        }
    }

    private void checkSchema2Elements(ArrayList<Class> schema2Classes,
            ArrayList<PrimitiveType> schema2PrimitiveTypes) {

        /* We expect 2 Classes */
        Assert.assertEquals("Unexpected number of Classes.",
                1,
                schema2Classes.size());
        /* We expect 1 Primitive type */
        Assert.assertEquals("Unexpected number of Primitive Types.",
                1,
                schema2PrimitiveTypes.size());
        for (Class class1 : schema2Classes) {

            if ("ComplexWithPrimTypeInSameSchema".equals(class1.getName())) {

                EList<Property> ownedAttributes = class1.getOwnedAttributes();
                for (Property property : ownedAttributes) {

                    assertEquals("Unexpected Primitive type",
                            schema2PrimitiveTypes.get(0).getName(),
                            property.getType().getName());
                    assertEquals("Unexpected Primitive type",
                            schema2PrimitiveTypes.get(0).getQualifiedName(),
                            property.getType().getQualifiedName());
                }
            }
        }
    }

}
