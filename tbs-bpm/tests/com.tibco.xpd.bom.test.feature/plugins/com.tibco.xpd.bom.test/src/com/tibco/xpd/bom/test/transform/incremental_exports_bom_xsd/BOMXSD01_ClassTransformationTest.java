/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.incremental_exports_bom_xsd;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;

import com.tibco.xpd.bom.test.transform.common.TransformationTest;
import com.tibco.xpd.bom.test.transform.util.TransformUtil;
import com.tibco.xpd.bom.validator.BOMValidatorActivator;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Its a very base transformation test it only test for now if transformation
 * will run without exceptions. You can later test manually the result in the
 * junit-workspace.
 * <p>
 * <i>Created: 06 Feb 2009</i>
 * </p>
 * 
 * @author glewis
 */
@SuppressWarnings("nls")
public class BOMXSD01_ClassTransformationTest extends TransformationTest {

    protected String TEST_FILE_NAME = "myTest.xsd";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/incremental-bom-xsd";

    protected String MODEL_FILE = "BOMXSD01_ClassTransformationTest.bom";

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
        String[] destIds =
                new String[] { BOMValidatorActivator.VALIDATION_DEST_ID_XSD,
                        BOMValidatorActivator.VALIDATION_DEST_ID_BOMGENERIC };
        boolean hasErrorMarker =
                TransformUtil.isMarkerSeverityExist(destIds,
                        modelFiles.get(0),
                        IMarker.SEVERITY_ERROR);
        assertEquals("There are XSD or BOM GENERIC errors on the BOM",
                false,
                hasErrorMarker);
        
        IFile file = modelFiles.get(0);
        assertNotNull(file);
        WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(file);
        assertNotNull(workingCopy);
        EObject rootElement = workingCopy.getRootElement();
        assertNotNull(rootElement);
        assertTrue(rootElement instanceof Model);
        
        PackageableElement packagedElement = ((Model)rootElement).getPackagedElement("Class1");
        assertTrue(packagedElement instanceof org.eclipse.uml2.uml.Class);
        
        List<IStatus> result =
                incrementalExportBOMtoXSD((Class)packagedElement, ResourcesPlugin
                        .getWorkspace().getRoot().getProject(testProjectName)
                        .getFullPath().append(TEST_FILE_NAME).toString(), false, false, true);
        assertEquals("There were errors on this transformation process (simulating an export from wizard).",
                0,
                getErrors(result).size());
    }

}
