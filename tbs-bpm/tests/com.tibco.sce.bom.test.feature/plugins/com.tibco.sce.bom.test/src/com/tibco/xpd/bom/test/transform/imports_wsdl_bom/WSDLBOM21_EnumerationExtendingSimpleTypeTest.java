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
import org.openarchitectureware.workflow.issues.Issues;
import org.openarchitectureware.workflow.issues.IssuesImpl;

import com.tibco.xpd.bom.test.transform.common.TransformationTest;
import com.tibco.xpd.bom.test.transform.util.TransformUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Junit test for protecting the fix made in XPD-3896. Checks if the Enumeration
 * extending a simple type in an user defined wsdl shows the superclass
 * relationship in the generated BOM.
 * 
 * @author kthombar
 * @since 05-Jul-2013
 */
public class WSDLBOM21_EnumerationExtendingSimpleTypeTest extends
        TransformationTest {

    protected String TEST_FILE_NAME = "myTest.bom";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl";

    protected String MODEL_FILE =
            "WSDLBOM21_EnumerationExtendingSimpleTypeTest.wsdl";

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

        checkBOMElements(model);

    }

    /**
     * Main method for starting validation of Generated BOM elements
     * 
     * @param model
     * @throws Exception
     */
    private void checkBOMElements(Model model) throws Exception {

        ArrayList<Class> allClazzes = new ArrayList<Class>();

        ArrayList<Enumeration> allEnumerations = new ArrayList<Enumeration>();

        ArrayList<PrimitiveType> allPrimTypes = new ArrayList<PrimitiveType>();

        EList<PackageableElement> packagedElements =
                model.getPackagedElements();
        assertEquals("Number of packaged elements in model", 6, packagedElements.size()); //$NON-NLS-1$

        for (PackageableElement packageableElement : packagedElements) {
            if (packageableElement instanceof PrimitiveType) {
                allPrimTypes.add((PrimitiveType) packageableElement);
            } else if (packageableElement instanceof Class) {
                allClazzes.add((Class) packageableElement);
            } else if (packageableElement instanceof Enumeration) {
                allEnumerations.add((Enumeration) packageableElement);
            }
        }
        // Check if the Enumeration and Primitive type is generated correctly
        // and if the Primitive Type is the super Class of Enumeration.

        assertEquals("Unexpected number of Primitive types", 1, allPrimTypes.size()); //$NON-NLS-1$

        assertEquals("Unexpected number of Enumerations", 1, allEnumerations.size()); //$NON-NLS-1$

        assertEquals("Unexpected number of Classes", 4, allClazzes.size()); //$NON-NLS-1$

        Enumeration enumeration = allEnumerations.get(0);
        PrimitiveType primitiveType = allPrimTypes.get(0);

        assertEquals("Unexpected number of Enumeration", "ELEM1", enumeration.getName()); //$NON-NLS-1$

        assertEquals("Unexpected Super type/Class of Enumeration",
                "intType",
                enumeration.getGeneralizations().get(0).getGeneral().getName()); //$NON-NLS-1$

        assertEquals("Unexpected Number of Enum Literals", 3, enumeration.getOwnedLiterals().size()); //$NON-NLS-1$

        assertEquals("Unexpected number of Primitive Type", "intType", primitiveType.getName()); //$NON-NLS-1$

    }

}
