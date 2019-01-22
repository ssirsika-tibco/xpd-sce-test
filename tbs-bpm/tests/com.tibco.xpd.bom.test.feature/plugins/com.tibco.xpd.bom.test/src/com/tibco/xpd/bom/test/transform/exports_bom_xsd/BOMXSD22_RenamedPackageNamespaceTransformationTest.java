/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.exports_bom_xsd;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.xsd.XSDSchema;

import com.tibco.xpd.bom.test.transform.common.TransformationTest;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Its a very base transformation test it only test for now if transformation
 * will run without exceptions. You can later test manually the result in the
 * junit-workspace.
 * <p>
 * <i>Created: 19 July 2010</i>
 * </p>
 * 
 * @author glewis
 */
@SuppressWarnings("nls")
public class BOMXSD22_RenamedPackageNamespaceTransformationTest extends
        TransformationTest {

    protected String TEST_FILE_NAME = "myTest.xsd";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/bom-xsd";

    protected String MODEL_FILE =
            "BOMXSD22_RenamedPackageNamespaceTransformationTest.bom";

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
        List<IStatus> result =
                exportBOMtoXSD(modelFiles.get(0), true, ResourcesPlugin
                        .getWorkspace().getRoot().getProject(testProjectName)
                        .getFullPath().append("XsdOutput"), true);
        assertEquals("There were errors on this transformation process (simulating an export from wizard).",
                0,
                getErrors(result).size());

        IFile bomFile = outputSpecialFolder.getFolder().getFile(MODEL_FILE);
        WorkingCopy wc =
                XpdResourcesPlugin.getDefault().getWorkingCopy(bomFile);
        assertNotNull("Cannot create WorkingCopy for existing BOM file", wc); //$NON-NLS-1$

        assertTrue("Root element is null or not of type Model", wc.getRootElement() instanceof Model); //$NON-NLS-1$

        Model model = (Model) wc.getRootElement();

        String outputXSDFile = model.getName() + ".xsd"; //$NON-NLS-1$
        String outputXSDFileRenamed = model.getName() + "Renamed.xsd"; //$NON-NLS-1$

        // Rename the model
        wc.getEditingDomain()
                .getCommandStack()
                .execute(SetCommand.create(wc.getEditingDomain(),
                        model,
                        UMLPackage.eINSTANCE.getNamedElement_Name(),
                        model.getName() + "Renamed"));
        // Save and wait for builder etc.
        wc.save();
        TestUtil.waitForJobs();

        result =
                exportBOMtoXSD(modelFiles.get(0), true, ResourcesPlugin
                        .getWorkspace().getRoot().getProject(testProjectName)
                        .getFullPath().append("XsdOutput"), true);
        assertEquals("There were errors on this transformation process (simulating an export from wizard).",
                0,
                getErrors(result).size());

        IFile resultXSDFile =
                project.getFolder("XsdOutput").getFile(outputXSDFile);
        assertNotNull(resultXSDFile);

        IFile resultXSDFileRenamed =
                project.getFolder("XsdOutput").getFile(outputXSDFileRenamed);
        assertNotNull(resultXSDFileRenamed);

        wc = XpdResourcesPlugin.getDefault().getWorkingCopy(resultXSDFile);
        XSDSchema schema = (XSDSchema) wc.getRootElement();

        wc =
                XpdResourcesPlugin.getDefault()
                        .getWorkingCopy(resultXSDFileRenamed);
        XSDSchema schemaRenamed = (XSDSchema) wc.getRootElement();

        assertEquals("http://example.com/renamedpackagenamespace", schema.getTargetNamespace()); //$NON-NLS-1$
        assertEquals("http://example.com/renamedpackagenamespaceRenamed", schemaRenamed.getTargetNamespace()); //$NON-NLS-1$

    }
}
