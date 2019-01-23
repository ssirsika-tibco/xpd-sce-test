/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.types;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.RunnableWithResult;
import org.eclipse.emf.transaction.TransactionalCommandStack;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.uml2.uml.ElementImport;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLFactory;

import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * 
 * <p>
 * <i>Created: 14 Jan 2008</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class PrimitivesUtilTest extends TestCase {

    private IProject project;

    private URI testResourceURI;

    private final ResourceSet rs =
            XpdResourcesPlugin.getDefault().getEditingDomain().getResourceSet();

    private Model model;

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        project = TestUtil.createProject("TestPrimitives");
        testResourceURI =
                URI.createPlatformResourceURI(project.getFullPath()
                        .append("test.uml").toPortableString(), true);
        TransactionalEditingDomain ed =
                XpdResourcesPlugin.getDefault().getEditingDomain();
        TransactionalCommandStack stack =
                (TransactionalCommandStack) ed.getCommandStack();
        stack.execute(new RecordingCommand(ed) {
            @Override
            protected void doExecute() {
                model = UMLFactory.eINSTANCE.createModel();
                model.setName("testPrimitives");
                Resource resource = rs.createResource(testResourceURI);
                resource.getContents().add(model);
            }
        }, Collections.EMPTY_MAP);
    }

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        Resource resource = rs.getResource(testResourceURI, false);
        if (resource != null && resource.isLoaded()) {
            resource.unload();
        }
        TestUtil.removeProject(project.getName());
        super.tearDown();
    }

    public void testImportPackagePrimitiveType() throws Exception {
        TransactionalEditingDomain ed =
                XpdResourcesPlugin.getDefault().getEditingDomain();
        TransactionalCommandStack stack =
                (TransactionalCommandStack) ed.getCommandStack();
        stack.execute(new RecordingCommand(ed) {
            @Override
            protected void doExecute() {
                PrimitivesUtil.importPrimitiveTypes(model);
                PrimitiveType textType =
                        (PrimitiveType) model.getImportedMember("Text");
                Object facetPropertyValue =
                        PrimitivesUtil.getFacetPropertyValue(textType,
                                "textLength");
                System.out.println("\"textLength\" tagged value is: "
                        + facetPropertyValue);
            }
        }, Collections.EMPTY_MAP);
    }

    @SuppressWarnings("unchecked")
    public void testGetBasePrimitiveTypeNames() throws Exception {
        TransactionalEditingDomain ed =
                XpdResourcesPlugin.getDefault().getEditingDomain();
        Collection<String> typeNames =
                (Collection<String>) ed
                        .runExclusive(new RunnableWithResult.Impl() {
                            /*
                             * (non-Javadoc)
                             * 
                             * @see java.lang.Runnable#run()
                             */
                            public void run() {
                                setResult(PrimitivesUtil
                                        .getStandardPrimtiveTypeNames(rs));

                            }
                        });
        System.out.println("Type names:" + typeNames);

    }

    public void testGetFacetPropertiesNames() throws Exception {
        TransactionalEditingDomain ed =
                XpdResourcesPlugin.getDefault().getEditingDomain();
        ed.runExclusive(new Runnable() {
            /*
             * (non-Javadoc)
             * 
             * @see java.lang.Runnable#run()
             */
            public void run() {
                Collection<String> primtiveTypeNames =
                        PrimitivesUtil.getStandardPrimtiveTypeNames(rs);
                for (String typeName : primtiveTypeNames) {
                    System.out.println("Type name: " + typeName);
                    Collection<String> names =
                            PrimitivesUtil
                                    .getFacetPropertiesNames(rs, typeName);
                    for (String name : names) {
                        System.out.println(name);
                    }
                }

            }
        });
        // TestUtil.waitForJobs();
    }

    public void testSetGetFacetValue() throws Exception {

        final String textTypeName = "Text";
        final String textLengthPropertyName = "textLength";
        final int defaultTextLength = 50;
        TransactionalEditingDomain ed =
                XpdResourcesPlugin.getDefault().getEditingDomain();
        TransactionalCommandStack stack =
                (TransactionalCommandStack) ed.getCommandStack();
        stack.execute(new RecordingCommand(ed) {
            @Override
            protected void doExecute() {
                System.out.println("Creating new package primitive types...");
                ElementImport importPrimitiveType =
                        PrimitivesUtil.importPrimitiveType(model, textTypeName);
                PrimitiveType textType =
                        (PrimitiveType) model
                                .getImportedMember(importPrimitiveType
                                        .getName());
                PrimitiveType postCodeType =
                        PrimitivesUtil.createPackagePrimitiveType(model,
                                textType,
                                "PostCode");

                // default length test
                Integer defaultValue = Integer.valueOf(defaultTextLength);
                assertEquals("Default value of text length other then expected.",
                        defaultValue,
                        PrimitivesUtil.getFacetPropertyValue(postCodeType,
                                textLengthPropertyName));

                // setting test
                Integer newValue = Integer.valueOf(10);
                PrimitivesUtil.setFacetPropertyValue(postCodeType,
                        textLengthPropertyName,
                        newValue);

                assertEquals("Default value of text length other then expected.",
                        newValue,
                        PrimitivesUtil.getFacetPropertyValue(postCodeType,
                                textLengthPropertyName));

                // resetting test
                Integer resetedValue = null;
                PrimitivesUtil.setFacetPropertyValue(postCodeType,
                        textLengthPropertyName,
                        resetedValue);

                assertEquals("Default value of text length other then expected.",
                        defaultValue,
                        PrimitivesUtil.getFacetPropertyValue(postCodeType,
                                textLengthPropertyName));

            }
        },
                Collections.EMPTY_MAP);
    }

    public void testSetGetFacetContextValue() throws Exception {

        final String textTypeName = "Text";
        final String textLengthPropertyName = "textLength";
        final int defaultTextLength = 50;
        TransactionalEditingDomain ed =
                XpdResourcesPlugin.getDefault().getEditingDomain();
        TransactionalCommandStack stack =
                (TransactionalCommandStack) ed.getCommandStack();
        stack.execute(new RecordingCommand(ed) {
            @Override
            protected void doExecute() {
                System.out.println("Creating new package primitive types...");
                ElementImport importPrimitiveType =
                        PrimitivesUtil.importPrimitiveType(model, textTypeName);
                PrimitiveType textType =
                        (PrimitiveType) model
                                .getImportedMember(importPrimitiveType
                                        .getName());
                PrimitiveType simpleTextType =
                        PrimitivesUtil.createPackagePrimitiveType(model,
                                textType,
                                "SimpleTextType");

                System.out.println("Creating example class  ...");
                org.eclipse.uml2.uml.Class class_ =
                        model.createOwnedClass("TheClass", false);
                System.out.println("Class: " + class_.getQualifiedName()
                        + " was created.");
                System.out
                        .println("Applying standard primitive type to class attribute ...");
                Property simpleAttribute =
                        class_.createOwnedAttribute("simpleAttribute",
                                simpleTextType);

                // default length test
                Integer defaultValue = Integer.valueOf(defaultTextLength);
                assertEquals("Default value of text length other then expected.",
                        defaultValue,
                        PrimitivesUtil.getFacetPropertyValue(simpleTextType,
                                textLengthPropertyName,
                                simpleAttribute));

                // setting test
                Integer newValue = Integer.valueOf(10);
                PrimitivesUtil.setFacetPropertyValue(simpleTextType,
                        textLengthPropertyName,
                        newValue,
                        simpleAttribute);

                assertEquals("Default value of text length other then expected.",
                        newValue,
                        PrimitivesUtil.getFacetPropertyValue(simpleTextType,
                                textLengthPropertyName,
                                simpleAttribute));
                // but setting must not modify type.
                assertEquals("Default value of text length other then expected.",
                        defaultValue,
                        PrimitivesUtil.getFacetPropertyValue(simpleTextType,
                                textLengthPropertyName));

                // resetting test - setting to null will clear the limit
                Integer resetedValue = null;
                PrimitivesUtil.setFacetPropertyValue(simpleTextType,
                        textLengthPropertyName,
                        resetedValue,
                        simpleAttribute);

                assertEquals("Default value of text length other then expected.",
                        -1,
                        PrimitivesUtil.getFacetPropertyValue(simpleTextType,
                                textLengthPropertyName,
                                simpleAttribute));

            }
        },
                Collections.EMPTY_MAP);
    }

    public void testGetFacetType() throws Exception {
        ResourceSet rs =
                XpdResourcesPlugin.getDefault().getEditingDomain()
                        .getResourceSet();
        int count = 0;
        Collection<String> types =
                PrimitivesUtil.getStandardPrimtiveTypeNames(rs);
        List<String> supportedTypes =
                Arrays.asList("UnlimitedNatural",
                        "Integer",
                        "String",
                        "Boolean",
                        "integerType",
                        "decimalType",
                        "objectType");
        for (String typeName : types) {
            Collection<String> facets =
                    PrimitivesUtil.getFacetPropertiesNames(rs, typeName);
            for (String facet : facets) {
                Type type = PrimitivesUtil.getFacetType(rs, facet);
                assertTrue("Unsupported type: " + type.getName(),
                        supportedTypes.contains(type.getName()));
                count++;
            }
        }
        assertTrue(count > 0);
    }

    public void testGetFacetLabel() throws Exception {
        int count = 0;
        Collection<String> types =
                PrimitivesUtil.getStandardPrimtiveTypeNames(rs);
        for (String typeName : types) {
            Collection<String> facets =
                    PrimitivesUtil.getFacetPropertiesNames(rs, typeName);
            for (String facet : facets) {
                // Ignore private facets as they will not be externalized
                if (!PrimitivesUtil.isPrivate(rs, facet)) {
                    String label = PrimitivesUtil.getFacetLabel(rs, facet);
                    assertFalse("Facet name not translated: " + label, label
                            .equals(facet));
                }
                count++;
            }
        }
        assertTrue(count > 0);
    }

    public void testTestDefaultPrimitiveType() throws Exception {
        PrimitiveType dpt =
                PrimitivesUtil.getDefaultPrimitiveType(XpdResourcesPlugin
                        .getDefault().getEditingDomain().getResourceSet());
        assertNotNull("Cannot find default primitive type!", dpt);
    }

    public void testGetFacetDefaultValuePropertyName() throws Exception {
        ResourceSet rs =
                XpdResourcesPlugin.getDefault().getEditingDomain()
                        .getResourceSet();

        // Types without default value.
        List<String> withoutDefault =
                Arrays.asList("Attachment", "Duration", "ID", "Object");

        // Types with default value property but with non standard facet's
        // property name.
        List<String> exceptions = Arrays.asList("URI");

        Collection<String> types =
                PrimitivesUtil.getStandardPrimtiveTypeNames(rs);
        for (String t : types) {
            if (withoutDefault.contains(t)) {
                assertNull("For types without default value method should return null.",
                        PrimitivesUtil.getFacetDefaultValuePropertyName(rs, t));
            } else if (exceptions.contains(t)) {
                assertNotNull("For non standard default value property names returned value should not be null",
                        PrimitivesUtil.getFacetDefaultValuePropertyName(rs, t));
            } else {
                assertEquals(t.substring(0, 1).toLowerCase() + t.substring(1)
                        + "DefaultValue", PrimitivesUtil
                        .getFacetDefaultValuePropertyName(rs, t));
            }

        }
    }

    public void testSetGetDisplayLabel() throws Exception {
        final String textTypeName = "Text";
        TransactionalEditingDomain ed =
                XpdResourcesPlugin.getDefault().getEditingDomain();
        TransactionalCommandStack stack =
                (TransactionalCommandStack) ed.getCommandStack();
        stack.execute(new RecordingCommand(ed) {
            @Override
            protected void doExecute() {
                System.out.println("Creating new package primitive types...");
                ElementImport importPrimitiveType =
                        PrimitivesUtil.importPrimitiveType(model, textTypeName);
                PrimitiveType textType =
                        (PrimitiveType) model
                                .getImportedMember(importPrimitiveType
                                        .getName());

                org.eclipse.uml2.uml.Class class_ =
                        model.createOwnedClass("TheClass", false);
                System.out.println("Class: " + class_.getQualifiedName()
                        + " was created.");
                System.out
                        .println("Applying standard primitive type to class attribute ...");
                Property simpleAttribute =
                        class_
                                .createOwnedAttribute("simpleAttribute",
                                        textType);

                assertNull(PrimitivesUtil.getDisplayLabel(class_, false));
                PrimitivesUtil.setDisplayLabel(class_, "class_Label");
                assertEquals("class_Label", PrimitivesUtil
                        .getDisplayLabel(class_));

                assertNull(PrimitivesUtil.getDisplayLabel(simpleAttribute,
                        false));
                PrimitivesUtil.setDisplayLabel(simpleAttribute,
                        "simpleAttributeLabel");
                assertEquals("simpleAttributeLabel", PrimitivesUtil
                        .getDisplayLabel(simpleAttribute));

            }
        },
                Collections.EMPTY_MAP);
    }
}
