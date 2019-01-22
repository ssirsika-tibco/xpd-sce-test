/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.bom.test.transform.imports_wsdl_bom;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.Assert;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.xsd.XSDSchema;
import org.openarchitectureware.workflow.issues.Issues;
import org.openarchitectureware.workflow.issues.IssuesImpl;
import org.openarchitectureware.xsd.builder.OawXSDResource;

import com.tibco.xpd.bom.test.transform.common.TransformationTest;
import com.tibco.xpd.bom.test.transform.util.TransformUtil;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Junit test for protecting issue fixed in XPD-3229. Resolves the issue where
 * BOM autogeneration used to be incorrect if a wsdl has two schemas with same
 * complex type extension names prefixed with different namespaces.
 * 
 * @author sajain
 * @since Jul 8, 2013
 */
public class WSDLBOM24_WsdlHasTwoSchemasWithSameComplexTypeExtensionNamesPrefixedWithDifferentNamespaces
        extends TransformationTest {

    protected String TEST_FILE_NAME = "myTest.bom";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/migrated";

    protected String MODEL_FILE = "TestSR.wsdl";

    protected String DEPENDENCY_FILE1 = "Resource.xsd";

    protected String DEPENDENCY_FILE2 = "Product.xsd";

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
        modelFileNames.add(DEPENDENCY_FILE1);
        modelFileNames.add(DEPENDENCY_FILE2);

        super.setUp();
    }

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

        Model model = (Model) wc.getRootElement();

        EList<PackageableElement> packagedElements =
                model.getPackagedElements();
        assertEquals("Number of packaged elements in model", 3, packagedElements.size()); //$NON-NLS-1$

        // check classes
        org.eclipse.uml2.uml.Class classCcls =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "TestSR"); //$NON-NLS-1$
        org.eclipse.uml2.uml.Class inputClassCls =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "NewOperationType"); //$NON-NLS-1$
        org.eclipse.uml2.uml.Class outputClassCls =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "NewOperationResponseType"); //$NON-NLS-1$

        // check attributes
        TransformUtil.assertAttributeSize(classCcls, 0);
        TransformUtil.assertAttributeSize(inputClassCls, 1);
        TransformUtil.assertAttributeSize(outputClassCls, 1);

        // check operations
        TransformUtil.assertOperationSize(classCcls, 1);
        Operation operation =
                TransformUtil.assertOperationInClass(classCcls
                        .getAllOperations(), "NewOperation", outputClassCls); //$NON-NLS-1$

        TransformUtil.assertOperationParameterSize(operation, 2);
        TransformUtil
                .assertParameterInOperation(operation.getOwnedParameters(),
                        "parameters", inputClassCls); //$NON-NLS-1$

        /*
         * Verify Resource.xsd file
         */
        IFile modelFile1 =
                outputSpecialFolder.getFolder().getFile(DEPENDENCY_FILE1);
        OawXSDResource resource1 =
                new OawXSDResource(URI.createURI(modelFile1.getFullPath()
                        .toPortableString()));
        resource1.load(Collections.EMPTY_MAP);
        XSDSchema schema1 = resource1.getSchema();
        TEST_FILE_NAME =
                XSDUtil.getJavaPackageNameFromNamespaceURI(modelFile1
                        .getProject(), schema1.getTargetNamespace())
                        + ".bom"; //$NON-NLS-1$

        IPath outputBOMPath1 =
                outputSpecialFolder.getFolder().getFullPath()
                        .append(TEST_FILE_NAME);
        List<IStatus> result1 =
                importXSDtoBOM(new File(modelFiles.get(1).getLocationURI()),
                        outputBOMPath1);
        List<IStatus> errorsInXSD1 = getErrors(result1);
        if (!errorsInXSD1.isEmpty()) {
            throw new Exception(errorsInXSD1.get(0).getMessage());
        }

        bomFile =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getFile(outputBOMPath1);
        assertTrue("Cannot find newly exported BOM file", bomFile.exists()); //$NON-NLS-1$

        // call checkBomElements() with bomFile pointing to Resource.xsd
        checkBomElements();

        /*
         * Verify Product.xsd file
         */
        IFile modelFile2 =
                outputSpecialFolder.getFolder().getFile(DEPENDENCY_FILE2);
        OawXSDResource resource2 =
                new OawXSDResource(URI.createURI(modelFile2.getFullPath()
                        .toPortableString()));
        resource2.load(Collections.EMPTY_MAP);
        XSDSchema schema2 = resource2.getSchema();
        TEST_FILE_NAME =
                XSDUtil.getJavaPackageNameFromNamespaceURI(modelFile2
                        .getProject(), schema2.getTargetNamespace())
                        + ".bom"; //$NON-NLS-1$

        IPath outputBOMPath2 =
                outputSpecialFolder.getFolder().getFullPath()
                        .append(TEST_FILE_NAME);
        List<IStatus> result2 =
                importXSDtoBOM(new File(modelFiles.get(2).getLocationURI()),
                        outputBOMPath2);
        List<IStatus> errorsInXSD2 = getErrors(result2);
        if (!errorsInXSD2.isEmpty()) {
            throw new Exception(errorsInXSD2.get(0).getMessage());
        }

        bomFile =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getFile(outputBOMPath2);
        assertTrue("Cannot find newly exported BOM file", bomFile.exists()); //$NON-NLS-1$

        // call checkBomElements() with bomFile pointing to Product.xsd
        checkBomElements();
    }

    public void checkBomElements() throws Exception {
        // check resulting bom file is correct
        WorkingCopy wc =
                XpdResourcesPlugin.getDefault().getWorkingCopy(bomFile);
        assertNotNull("Cannot create WorkingCopy for newly exported BOM file", wc); //$NON-NLS-1$
        assertTrue("Root element is null or not of type Model", wc.getRootElement() instanceof Model); //$NON-NLS-1$

        Model model = (Model) wc.getRootElement();

        EList<PackageableElement> packagedElements =
                model.getPackagedElements();

        ArrayList<org.eclipse.uml2.uml.Class> allClazzes =
                new ArrayList<org.eclipse.uml2.uml.Class>();

        ArrayList<Association> allAssociations = new ArrayList<Association>();

        for (PackageableElement packageableElement : packagedElements) {
            if (packageableElement instanceof Association) {
                allAssociations.add((Association) packageableElement);
            } else if (packageableElement instanceof org.eclipse.uml2.uml.Class) {
                allClazzes.add((org.eclipse.uml2.uml.Class) packageableElement);
            }
        }

        // check for 3 classes
        assertEquals("Number of classes in model", 3, allClazzes.size()); //$NON-NLS-1$

        // check names of classes
        org.eclipse.uml2.uml.Class class1 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "Address"); //$NON-NLS-1$
        org.eclipse.uml2.uml.Class class2 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "addressType"); //$NON-NLS-1$
        org.eclipse.uml2.uml.Class class3 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "ContactMedium"); //$NON-NLS-1$

        // check for 1 association
        assertEquals("Number of associations in model", 1, allAssociations.size()); //$NON-NLS-1$

        // Its name to be "Composition0"
        Assert.assertEquals("Unexpected name for Composition.",
                "Composition0",
                allAssociations.get(0).getName());

        // check that addressType should have a generalization
        if (0 != class2.getGeneralizations().size()) {
            assertEquals("Number of generalization with addressType", 1, class2.getGeneralizations().size()); //$NON-NLS-1$
        }

        System.out.println();

    }

}
