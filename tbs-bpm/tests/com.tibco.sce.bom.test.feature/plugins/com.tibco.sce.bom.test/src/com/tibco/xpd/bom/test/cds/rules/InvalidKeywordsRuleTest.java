/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.cds.rules;

import java.io.InputStream;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.transaction.TransactionalCommandStack;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.Destination;
import com.tibco.xpd.resources.projectconfig.Destinations;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.ProjectConfigFactory;
import com.tibco.xpd.resources.projectconfig.ProjectDetails;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;

/**
 * Its a very base transformation test it only test for now if transformation
 * will run without exceptions. You can later test manually the result in the
 * junit-workspace.
 * <p>
 * <i>Created: 16 April 2009</i>
 * </p>
 * 
 * @author glewis
 */
@SuppressWarnings("nls")
public class InvalidKeywordsRuleTest extends TestCase {

    protected static String TEST_PROJECT_NAME = "InvalidKeywordsRuleTest";

    private static final String PLATFORM_EXAMPLE_FILES_BASE_PREFIX =
            "platform:/plugin/com.tibco.xpd.bom.test/";

    private static final String PLATFORM_EXAMPLE_FILES_BASE_POSTFIX =
            "test-resources/cds";

    private static final String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/cds";

    private static final String BOM_MODEL_FILE = "AllBOMInvalidKeywords.bom";

    protected final ResourceSet rs;

    protected final TransactionalEditingDomain ed;

    protected final TransactionalCommandStack stack;

    protected URI testResourceURI;

    protected IProject project;

    protected WorkingCopy workingCopy;

    protected IFile modelFile;

    protected String modelLocation;

    /**
     * 
     */
    public InvalidKeywordsRuleTest() {
        rs =
                XpdResourcesPlugin.getDefault().getEditingDomain()
                        .getResourceSet();
        ed = XpdResourcesPlugin.getDefault().getEditingDomain();
        stack = (TransactionalCommandStack) ed.getCommandStack();
    }

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        clean();

        // crating project, special folder, and copy example files to it
        project = TestUtil.createProject(TEST_PROJECT_NAME);

        ProjectConfig projectConfig =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);
        if (projectConfig != null) {
            ProjectDetails projDetails =
                    ProjectConfigFactory.eINSTANCE.createProjectDetails();
            projectConfig.setProjectDetails(projDetails);
            Destinations createDestinations =
                    ProjectConfigFactory.eINSTANCE.createDestinations();
            projDetails.setGlobalDestinations(createDestinations);
            Destination destination =
                    ProjectConfigFactory.eINSTANCE.createDestination();
            destination.setType("BPM"); //$NON-NLS-1$
            createDestinations.getDestination().add(destination);
        }

        SpecialFolder specialFolder =
                TestUtil.createSpecialFolder(project, "Business Object Models", //$NON-NLS-1$
                        BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND);

        modelFile = specialFolder.getFolder().getFile(BOM_MODEL_FILE);
        InputStream modelInputStream =
                new ResourceSetImpl().getURIConverter().createInputStream(URI
                        .createURI(PLATFORM_EXAMPLE_FILES_BASE + '/'
                                + BOM_MODEL_FILE));
        modelFile.create(modelInputStream, true, null);
        modelFile.refreshLocal(IResource.DEPTH_ZERO, new NullProgressMonitor());
        modelLocation =
                modelFile.getLocation().removeLastSegments(1)
                        .toPortableString();
        project.build(IncrementalProjectBuilder.FULL_BUILD,
                new NullProgressMonitor());
    }

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        clean();
        super.tearDown();
    }

    public void testTransformation() throws Exception {
        IMarker[] markers =
                modelFile
                        .findMarkers("com.tibco.xpd.bom.validator.javaValidationMarker",
                                true,
                                IResource.DEPTH_INFINITE); //$NON-NLS-1$
        int errorCount = 0;
        for (IMarker marker : markers) {
            int markerSeverity =
                    marker.getAttribute(IMarker.SEVERITY,
                            IMarker.SEVERITY_WARNING);
            if (markerSeverity == IMarker.SEVERITY_ERROR) {
                errorCount++;
            }
        }

        if (errorCount != 50) {
            throw new Exception(
                    "There should be 50 CDS Errors and this test only found " + errorCount); //$NON-NLS-1$
        }
    }

    private void clean() {
        IProject proj =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getProject(TEST_PROJECT_NAME);
        if (proj.exists()) {
            TestUtil.removeProject(proj.getName());
        }
    }
}
