/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.n2.test.generate;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.gen.internal.BOMGeneratorHelper;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.bom.test.util.BOMTestUtils;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.Destination;
import com.tibco.xpd.resources.projectconfig.Destinations;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.ProjectConfigFactory;
import com.tibco.xpd.resources.projectconfig.ProjectDetails;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

import junit.framework.TestCase;

/**
 * 
 * 
 * @author nwilson
 * @since 19 Aug 2014
 */
public class AbstractGenTest extends TestCase {
    protected IProject projectX;

    protected IProject projectY;

    protected IProject projectZ;

    protected IFile bomX;

    protected IFile bomY;

    protected IFile bomY2;

    protected IFile bomZ;

    protected Property propertyY;

    protected Property propertyY2;

    protected Property propertyZ;

    protected WorkingCopy wcY;

    protected WorkingCopy wcY2;

    protected WorkingCopy wcZ;

    protected Class clsX;

    protected Class clsY2;

    /**
     * Process project X depends on BOM project Y which depends on BOM project
     * Z.
     * 
     * @see junit.framework.TestCase#setUp()
     * 
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception {
        try {
            projectZ = TestUtil.createProjectFromWizard("Z"); //$NON-NLS-1$
            assertTrue("Failed to create project", //$NON-NLS-1$
                    projectZ != null && projectZ.isAccessible());
            configureProject(projectZ);

            bomZ = projectZ.getFile("Business Objects/Z.bom"); //$NON-NLS-1$
            assertTrue(bomZ.exists());
            wcZ = WorkingCopyUtil.getWorkingCopy(bomZ);
            assertNotNull(wcZ);

            Class clsZ = createClass(wcZ, "Z"); //$NON-NLS-1$
            propertyZ = createProperty(wcZ, clsZ, "z"); //$NON-NLS-1$
            wcZ.save();

            projectY = TestUtil.createProjectFromWizard("Y"); //$NON-NLS-1$
            assertTrue("Failed to create project", //$NON-NLS-1$
                    projectY != null && projectY.isAccessible());
            configureProject(projectY);

            IProjectDescription descY = projectY.getDescription();
            descY.setReferencedProjects(new IProject[] { projectZ });
            projectY.setDescription(descY, null);

            bomY = projectY.getFile("Business Objects/Y.bom"); //$NON-NLS-1$
            assertTrue(bomY.exists());
            wcY = WorkingCopyUtil.getWorkingCopy(bomY);
            assertNotNull(wcY);

            bomY2 =
                    BOMTestUtils.createBOMdiagram("Y", //$NON-NLS-1$
                            "Business Objects/Y2.bom"); //$NON-NLS-1$
            assertTrue(bomY2.exists());
            wcY2 = WorkingCopyUtil.getWorkingCopy(bomY2);
            assertNotNull(wcY2);

            Class clsY = createClass(wcY, "Y"); //$NON-NLS-1$
            clsY2 = createClass(wcY2, "Y2"); //$NON-NLS-1$
            propertyY = createTypedProperty(wcY, clsY, clsZ, "y"); //$NON-NLS-1$
            propertyY2 = createTypedProperty(wcY2, clsY2, clsY, "y2"); //$NON-NLS-1$

            wcY.save();
            wcY2.save();

            projectX = TestUtil.createProjectFromWizard("X"); //$NON-NLS-1$
            assertTrue("Failed to create project", //$NON-NLS-1$
                    projectX != null && projectX.isAccessible());

            configureProject(projectX);

            bomX = projectX.getFile("Business Objects/X.bom"); //$NON-NLS-1$
            assertTrue(bomX.exists());
            WorkingCopy wcX = WorkingCopyUtil.getWorkingCopy(bomX);
            assertNotNull(wcX);

            clsX = createClass(wcX, "X"); //$NON-NLS-1$

            createTypedProperty(wcX, clsX, clsY, "x"); //$NON-NLS-1$

            IProjectDescription descX = projectX.getDescription();
            descX.setReferencedProjects(new IProject[] { projectY });
            projectX.setDescription(descX, null);

            wcX.save();
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        TestUtil.buildAndWait();
    }

    /**
     * @see junit.framework.TestCase#tearDown()
     * 
     * @throws Exception
     */
    @Override
    protected void tearDown() throws Exception {
        TestUtil.waitForBuilds(30, 500);
        TestUtil.deleteAllWorkpsaceProjects();
        TestUtil.waitForBuilds(30, 500);
    }

    protected IProject getTargetProject(IFile bom) {
        Model model = BOMUtils.getModel(bom);
        assertNotNull(model);
        String name = "." + model.getName() + ".bds"; //$NON-NLS-1$ //$NON-NLS-2$
        IProject project =
                ResourcesPlugin.getWorkspace().getRoot().getProject(name);
        assertNotNull(project);
        assertTrue(project.exists() && project.isAccessible());
        return project;
    }

    protected void configureProject(IProject project) {
        ProjectConfig projectConfig =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);
        if (projectConfig != null) {
            ProjectDetails projDetails =
                    ProjectConfigFactory.eINSTANCE.createProjectDetails();
            projectConfig.setProjectDetails(projDetails);
            Destinations createDestinations =
                    ProjectConfigFactory.eINSTANCE.createDestinations();
            projDetails.setGlobalDestinations(createDestinations);
            projDetails.setId(project.getName());
            Destination destination =
                    ProjectConfigFactory.eINSTANCE.createDestination();
            destination.setType("CE"); //$NON-NLS-1$
            createDestinations.getDestination().add(destination);
        }

    }

    /**
     * @param wc
     * @throws ExecutionException
     */
    private org.eclipse.uml2.uml.Class createClass(WorkingCopy wc, String name)
            throws ExecutionException {
        Object clsObject = BOMTestUtils.createClass2(wc);
        assertNotNull(clsObject);
        assertTrue(clsObject instanceof org.eclipse.uml2.uml.Class);
        org.eclipse.uml2.uml.Class cls = (org.eclipse.uml2.uml.Class) clsObject;
        BOMTestUtils.changeClassName(cls, name);
        return cls;
    }

    /**
     * @param wc
     * @throws ExecutionException
     */
    private org.eclipse.uml2.uml.Property createProperty(WorkingCopy wc,
            org.eclipse.uml2.uml.Class cls, String name)
            throws ExecutionException {
        Object propertyObject = BOMTestUtils.createProperty(wc, cls);
        assertNotNull(propertyObject);
        assertTrue(propertyObject instanceof Property);
        Property property = (Property) propertyObject;
        BOMTestUtils.changePropertyName(property, name);
        return property;
    }

    /**
     * @param wc
     * @throws ExecutionException
     */
    private org.eclipse.uml2.uml.Property createTypedProperty(WorkingCopy wc,
            org.eclipse.uml2.uml.Class cls, org.eclipse.uml2.uml.Class type,
            String name) throws ExecutionException {
        Object propertyObject = BOMTestUtils.createTypedProperty(wc, cls, type);
        assertNotNull(propertyObject);
        assertTrue(propertyObject instanceof Property);
        Property property = (Property) propertyObject;
        BOMTestUtils.changePropertyName(property, name);
        return property;
    }

    /**
     * 
     */
    protected void exportDAA() {
        Set<String> generatorIds = new HashSet<String>();
        generatorIds.add("com.tibco.bds.designtime.generator"); //$NON-NLS-1$
        try {
            BOMGeneratorHelper.getInstance().runOnDemandGeneration(projectX,
                    new NullProgressMonitor(),
                    generatorIds);
        } catch (CoreException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        TestUtil.waitForBuilds(20, 500);
    }

    protected void assertNotEquals(Object expected, Object actual) {
        assertNotEquals(expected, actual, null);
    }

    protected void assertNotEquals(Object expected, Object actual,
            String message) {
        if (expected == null && actual != null)
            return;
        if (expected != null && !expected.equals(actual))
            return;
        failNotEquals(message, expected, actual);
    }

}
