/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.n2.test.generate;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.bom.test.util.BOMTestUtils;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Test the BOM on-demand builder.
 * 
 * @author nwilson
 * @since 19 Aug 2014
 */
public class OnDemandGenTest extends AbstractGenTest {

    private IProject projectP;

    private IFile bomP;

    private WorkingCopy wcP;

    /**
     * @see com.tibco.xpd.n2.test.generate.gen.AbstractGenTest#setUp()
     * 
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        projectP = TestUtil.createProjectFromWizard("P"); //$NON-NLS-1$
        assertTrue("Failed to create project", //$NON-NLS-1$
                projectP != null && projectP.isAccessible());

        configureProject(projectP);

        bomP = projectP.getFile("Process Packages/P.xpdl"); //$NON-NLS-1$
        assertTrue(bomP.exists());
        wcP = WorkingCopyUtil.getWorkingCopy(bomP);
        assertNotNull(wcP);

        createDataField("x", clsX); //$NON-NLS-1$
        createDataField("y2", clsY2); //$NON-NLS-1$

        wcP.save();

        TestUtil.buildAndWait(projectP);

        exportDAA();
    }

    /**
     * 
     */
    private void createDataField(String name, Class type) {
        EObject root = wcP.getRootElement();
        if (root instanceof Package) {
            Package pckg = (Package) root;
            com.tibco.xpd.xpdl2.Process proc = pckg.getProcesses().get(0);
            DataField df = Xpdl2Factory.eINSTANCE.createDataField();
            df.setName(name);
            ExternalReference dt =
                    Xpdl2Factory.eINSTANCE.createExternalReference();
            IResource resource =
                    WorkingCopyUtil.getWorkingCopyFor(type)
                            .getEclipseResources().get(0);
            dt.setLocation(resource.getName());
            dt.setNamespace("http://www.eclipse.org/uml2/3.0.0/UML"); //$NON-NLS-1$
            dt.setXref(((XMLResource) type.eResource()).getID(type));
            df.setDataType(dt);
            Command cmd =
                    AddCommand.create(wcP.getEditingDomain(),
                            proc,
                            Xpdl2Package.eINSTANCE
                                    .getDataFieldsContainer_DataFields(),
                            df);
            if (cmd.canExecute()) {
                wcP.getEditingDomain().getCommandStack().execute(cmd);
            }
        }
    }

    /**
     * If no BOMs are changed then no BDS projects should be rebuilt.
     */
    public void testNoChanges() {

        TestUtil.waitForBuilds(20, 500);

        IProject xTarget = getTargetProject(bomX);
        long timestampX = xTarget.getLocalTimeStamp();
        IProject yTarget = getTargetProject(bomY);
        long timestampY = yTarget.getLocalTimeStamp();
        IProject y2Target = getTargetProject(bomY2);
        long timestampY2 = y2Target.getLocalTimeStamp();
        IProject zTarget = getTargetProject(bomZ);
        long timestampZ = zTarget.getLocalTimeStamp();

        exportDAA();

        assertEquals("BDS for Project X Rebuilt Unexpectedly When no Chaneg to ANY BOM", //$NON-NLS-1$
                timestampX,
                xTarget.getLocalTimeStamp());
        assertEquals("BDS for Project Y Rebuilt Unexpectedly When no Chaneg to ANY BOM", //$NON-NLS-1$
                timestampY,
                yTarget.getLocalTimeStamp());
        assertEquals("BDS for Project Y2 Rebuilt Unexpectedly When no Chaneg to ANY BOM", //$NON-NLS-1$
                timestampY2,
                y2Target.getLocalTimeStamp());
        assertEquals("BDS for Project Z Rebuilt Unexpectedly When no Chaneg to ANY BOM", //$NON-NLS-1$
                timestampZ,
                zTarget.getLocalTimeStamp());
    }

    /**
     * If Z BOM is changed then Z, Y, X and Y2 should be rebuilt.
     */
    public void testChangeZ() {
        TestUtil.waitForBuilds(20, 500);
        IProject xTarget = getTargetProject(bomX);
        long timestampX = xTarget.getLocalTimeStamp();
        IProject yTarget = getTargetProject(bomY);
        long timestampY = yTarget.getLocalTimeStamp();
        IProject y2Target = getTargetProject(bomY2);
        long timestampY2 = y2Target.getLocalTimeStamp();
        IProject zTarget = getTargetProject(bomZ);
        long timestampZ = zTarget.getLocalTimeStamp();

        try {
            BOMTestUtils.changePropertyName(propertyZ, "zz"); //$NON-NLS-1$
            wcZ.save();
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        TestUtil.waitForBuilds(20, 500);

        exportDAA();

        assertNotEquals(timestampX,
                xTarget.getLocalTimeStamp(),
                "BDS for Project X Not Rebuilt (on change to BOM Z)"); //$NON-NLS-1$
        assertNotEquals(timestampY,
                yTarget.getLocalTimeStamp(),
                "BDS for Project Y Not Rebuilt (on change to BOM Z)"); //$NON-NLS-1$
        assertNotEquals(timestampY2,
                y2Target.getLocalTimeStamp(),
                "BDS for Project Y2 Not Rebuilt (on change to BOM Z)"); //$NON-NLS-1$
        assertNotEquals(timestampZ,
                zTarget.getLocalTimeStamp(),
                "BDS for Project Z Not Rebuilt (on change to BOM Z)"); //$NON-NLS-1$
    }

    /**
     * If Y2 BOM is changed then only Y2 should be rebuilt.
     */
    public void testChangeY2() {
        TestUtil.waitForBuilds(20, 500);
        IProject xTarget = getTargetProject(bomX);
        long timestampX = xTarget.getLocalTimeStamp();
        IProject yTarget = getTargetProject(bomY);
        long timestampY = yTarget.getLocalTimeStamp();
        IProject y2Target = getTargetProject(bomY2);
        long timestampY2 = y2Target.getLocalTimeStamp();
        IProject zTarget = getTargetProject(bomZ);
        long timestampZ = zTarget.getLocalTimeStamp();

        try {
            BOMTestUtils.changePropertyName(propertyY2, "yy2"); //$NON-NLS-1$
            wcY2.save();
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        TestUtil.waitForBuilds(20, 500);

        exportDAA();

        assertEquals("BDS for Project X Rebuilt Unexpectedly on change to BOM Y2", //$NON-NLS-1$
                timestampX,
                xTarget.getLocalTimeStamp());
        assertEquals("BDS for Project Y Rebuilt Unexpectedly on change to BOM Y2", //$NON-NLS-1$
                timestampY,
                yTarget.getLocalTimeStamp());
        assertNotEquals(timestampY2,
                y2Target.getLocalTimeStamp(),
                "BDS for Project Y2 Not Rebuilt (on change to BOM Y2)"); //$NON-NLS-1$
        assertEquals("BDS for Project Z Rebuilt Unexpectedly on change to BOM Y2", //$NON-NLS-1$
                timestampZ,
                zTarget.getLocalTimeStamp());
    }

    /**
     * If Y BOM is changed then Y, X and Y2 should be rebuilt.
     */
    public void testChangeY() {
        TestUtil.waitForBuilds(20, 500);
        IProject xTarget = getTargetProject(bomX);
        long timestampX = xTarget.getLocalTimeStamp();
        IProject yTarget = getTargetProject(bomY);
        long timestampY = yTarget.getLocalTimeStamp();
        IProject y2Target = getTargetProject(bomY2);
        long timestampY2 = y2Target.getLocalTimeStamp();
        IProject zTarget = getTargetProject(bomZ);
        long timestampZ = zTarget.getLocalTimeStamp();

        try {
            BOMTestUtils.changePropertyName(propertyY, "yy"); //$NON-NLS-1$
            wcY.save();
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        TestUtil.waitForBuilds(20, 500);

        exportDAA();

        assertNotEquals(timestampX,
                xTarget.getLocalTimeStamp(),
                "BDS for Project X Not Rebuilt (on change to BOM Y)"); //$NON-NLS-1$

        assertNotEquals(timestampY,
                yTarget.getLocalTimeStamp(),
                "BDS for Project Y Not Rebuilt (on change to BOM Y)"); //$NON-NLS-1$
        assertNotEquals(timestampY2,
                y2Target.getLocalTimeStamp(),
                "BDS for Project Y2 Not Rebuilt (on change to BOM Y)"); //$NON-NLS-1$

        assertEquals("BDS for Project Z Rebuilt Unexpectedly on change to BOM Y", //$NON-NLS-1$
                timestampZ,
                zTarget.getLocalTimeStamp());

    }
}
