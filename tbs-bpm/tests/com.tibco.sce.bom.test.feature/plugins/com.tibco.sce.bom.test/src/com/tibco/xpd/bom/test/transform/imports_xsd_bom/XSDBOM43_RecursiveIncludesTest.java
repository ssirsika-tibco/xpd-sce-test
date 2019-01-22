/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.bom.test.transform.imports_xsd_bom;

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
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.xsd.XSDSchema;
import org.openarchitectureware.xsd.builder.OawXSDResource;

import com.tibco.xpd.bom.test.transform.common.TransformationTest;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * This came from the SR for which XPD-5716 has been raised to fix
 * StackOverflowError that we get when there are schema includes in a recursive
 * way. For instance if xsd1 includes xsd2 and xsd2 includes xsd1 we used to get
 * this exception
 * 
 * @author bharge
 * @since 13 Feb 2014
 */
public class XSDBOM43_RecursiveIncludesTest extends TransformationTest {

    protected String TEST_FILE_NAME = "XSDBOM43_RecursiveIncludesTest";

    private static final String MODEL_FILE = "Schema1.xsd";

    private static final String MODEL_FILE2 = "Schema2.xsd";

    private static final String MODEL_FILE3 = "Schema3.xsd";

    private static final String MODEL_FILE4 = "Schema4.xsd";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/xsd/XPD5716";

    /**
     * @see com.tibco.xpd.bom.test.transform.common.TransformationTest#setUp()
     * 
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception {

        modelFileNames = new ArrayList<String>();
        modelFiles = new ArrayList<IFile>();

        platformExampleFilesBase = PLATFORM_EXAMPLE_FILES_BASE;

        modelFileNames.add(MODEL_FILE);
        modelFileNames.add(MODEL_FILE2);
        modelFileNames.add(MODEL_FILE3);
        modelFileNames.add(MODEL_FILE4);

        super.setUp();
    }

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
        /* check resulting bom file is correct */
        bomFile =
                ResourcesPlugin.getWorkspace().getRoot().getFile(outputBOMPath);
        assertTrue("Cannot find newly exported BOM file", bomFile.exists()); //$NON-NLS-1$

        WorkingCopy wc =
                XpdResourcesPlugin.getDefault().getWorkingCopy(bomFile);
        assertNotNull("Cannot create WorkingCopy for newly exported BOM file", wc); //$NON-NLS-1$
        assertTrue("Root element is null or not of type Model", wc.getRootElement() instanceof Model); //$NON-NLS-1$

        Model model = (Model) wc.getRootElement();

        /* Validate BOM elements */
        checkBOMElements(model);
    }

    /**
     * @param model
     */
    private void checkBOMElements(Model model) {

        EList<PackageableElement> packagedElements =
                model.getPackagedElements();
        ArrayList<Class> allClazzes = new ArrayList<Class>();

        for (PackageableElement packageableElement : packagedElements) {

            if (packageableElement instanceof Class) {

                allClazzes.add((Class) packageableElement);
            }
        }

        /* We expect 4 Classes */
        Assert.assertEquals("Unexpected number of Classes.",
                4,
                allClazzes.size());
    }

}
