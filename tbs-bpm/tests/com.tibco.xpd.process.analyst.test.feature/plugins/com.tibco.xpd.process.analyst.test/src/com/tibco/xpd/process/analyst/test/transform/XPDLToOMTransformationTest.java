/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.process.analyst.test.transform;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalCommandStack;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.process.om.XtendTransformer;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.xpdl2.Xpdl2Factory;

/**
 * Its a very base transformation test it only test for now if transformation
 * will run without exceptions. You can later test manually the result in the
 * junit-workspace.
 * <p>
 * <i>Created: 31 July 2008</i>
 * </p>
 * 
 * @author glewis
 */
@SuppressWarnings("nls")
public class XPDLToOMTransformationTest extends TestCase {

    protected static String TEST_PROJECT_NAME = "XpdlToOmTestProject";
    protected static String TEST_FILE_NAME = "myTest.om";
    private static final String PLATFORM_EXAMPLE_FILES_BASE = "platform:/plugin/com.tibco.xpd.process.analyst.test/resources";
    private static final String XPDL_MODEL_FILE = "ProcessPackage.xpdl";
    private static final String REF_OM_MODEL = "MyOrganizationModel.om";

    protected final ResourceSet rs;
    protected final TransactionalEditingDomain ed;
    protected final TransactionalCommandStack stack;
    protected final Xpdl2Factory factory;

    protected URI testResourceURI;
    protected IProject project;

    protected ArrayList<IFile> selectedPackages = new ArrayList<IFile>();
    protected ArrayList<EObject> referencedOrgModels = new ArrayList<EObject>();
    protected ArrayList<String> referencedURI = new ArrayList<String>();

    protected WorkingCopy workingCopy;

    /**
     * 
     */
    public XPDLToOMTransformationTest() {
        rs = XpdResourcesPlugin.getDefault().getEditingDomain()
                .getResourceSet();
        ed = XpdResourcesPlugin.getDefault().getEditingDomain();
        stack = (TransactionalCommandStack) ed.getCommandStack();
        factory = Xpdl2Factory.eINSTANCE;
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
        project.refreshLocal(IResource.DEPTH_ZERO, null);

        testResourceURI = URI.createPlatformResourceURI(project.getFullPath()
                .append(TEST_FILE_NAME).toPortableString(), true);
        SpecialFolder specialFolder = TestUtil.createSpecialFolder(project,
                "Packages", //$NON-NLS-1$
                "om"); //$NON-NLS-1$

        // get the xpdl file that we want to transform to om
        final IFile modelFile = specialFolder.getFolder().getFile(
                XPDL_MODEL_FILE);
        InputStream modelInputStream = new ResourceSetImpl().getURIConverter()
                .createInputStream(
                        URI.createURI(PLATFORM_EXAMPLE_FILES_BASE + '/'
                                + XPDL_MODEL_FILE));
        modelFile.create(modelInputStream, true, null);
        modelFile.refreshLocal(IResource.DEPTH_ZERO, null);

        IFile metamodelFile = specialFolder.getFolder()
                .getFile(XPDL_MODEL_FILE);
        metamodelFile.refreshLocal(IResource.DEPTH_ZERO, null);
        selectedPackages.add(metamodelFile);

        // get the Org Model that this XPDL file references for some of its
        // participant values
        final IFile refOrgModelFile = specialFolder.getFolder().getFile(
                REF_OM_MODEL);
        InputStream refOrgModelInputStream = new ResourceSetImpl()
                .getURIConverter().createInputStream(
                        URI.createURI(PLATFORM_EXAMPLE_FILES_BASE + '/'
                                + REF_OM_MODEL));
        refOrgModelFile.create(refOrgModelInputStream, true, null);
        refOrgModelFile.refreshLocal(IResource.DEPTH_ZERO, null);
        stack.execute(new RecordingCommand(ed) {
            @Override
            protected void doExecute() {
                URI omURI = URI.createPlatformResourceURI(refOrgModelFile
                        .getFullPath().toPortableString(), true);
                Resource resource = rs.getResource(omURI, true);

                referencedOrgModels.add(resource.getContents().get(0));
                referencedURI.add(refOrgModelFile.getFullPath()
                        .toPortableString());
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
        clean();
        super.tearDown();
    }

    public void testXPDLToOMTransformation() throws Exception {
        boolean isSuccessful = new XtendTransformer().transform(
                selectedPackages, referencedOrgModels, referencedURI,
                TEST_FILE_NAME);
    }

    private void clean() {
        IProject proj = ResourcesPlugin.getWorkspace().getRoot().getProject(
                TEST_PROJECT_NAME);
        if (proj.exists()) {
            TestUtil.removeProject(proj.getName());
        }
    }
}
