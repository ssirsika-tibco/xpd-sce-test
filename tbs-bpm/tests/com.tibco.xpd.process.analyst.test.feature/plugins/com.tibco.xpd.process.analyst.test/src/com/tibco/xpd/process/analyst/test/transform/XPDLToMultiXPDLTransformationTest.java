/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.process.analyst.test.transform;

import java.io.InputStream;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.transaction.TransactionalCommandStack;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.process.om.XtendTransformerMultXpdl;
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
public class XPDLToMultiXPDLTransformationTest extends TestCase {

    protected static String TEST_PROJECT_NAME = "XpdlToMultiXpdlTestProject";    
    private static final String PLATFORM_EXAMPLE_FILES_BASE = "platform:/plugin/com.tibco.xpd.process.analyst.test/resources";
    private static final String XPDL_MODEL_FILE = "fragmentCategory.xpdl";

    protected final ResourceSet rs;
    protected final TransactionalEditingDomain ed;
    protected final TransactionalCommandStack stack;
    protected final Xpdl2Factory factory;

    protected URI testResourceURI;
    protected IProject project;
    
    protected IFile metamodelFile;

    protected WorkingCopy workingCopy;    

    /**
     * 
     */
    public XPDLToMultiXPDLTransformationTest() {
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
        SpecialFolder specialFolder = TestUtil.createSpecialFolder(project,
                "Processes", //$NON-NLS-1$
                "processes"); //$NON-NLS-1$

        // get the xpdl file that we want to transform to multiple ones
        final IFile modelFile = specialFolder.getFolder()
                .getFile(XPDL_MODEL_FILE);
        InputStream modelInputStream = new ResourceSetImpl().getURIConverter()
                .createInputStream(
                        URI.createURI(PLATFORM_EXAMPLE_FILES_BASE + '/'
                                + XPDL_MODEL_FILE));
        modelFile.create(modelInputStream, true, null);
        metamodelFile = specialFolder.getFolder().getFile(
                XPDL_MODEL_FILE);
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
        boolean isSuccessful = new XtendTransformerMultXpdl().transform(metamodelFile);        
    }

    private void clean() {
        /*IProject proj = ResourcesPlugin.getWorkspace().getRoot().getProject(
                TEST_PROJECT_NAME);
        if (proj.exists()) {
            TestUtil.removeProject(proj.getName());
        }*/
    }
}
