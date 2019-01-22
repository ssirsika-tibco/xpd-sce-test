/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.test.cases;

import java.util.List;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.resources.ui.internal.navigator.actions.OMDeleteAction;
import com.tibco.xpd.om.test.OMTestUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.wc.gmf.AbstractGMFWorkingCopy;

/**
 * Test deletion of organizations. This should delete the Diagram associated
 * with the Organization and close its editor if open. This test will also test
 * deletion of the .om file.
 * 
 * @author njpatel
 * 
 */
public class TestOrganizationsAndEditors extends TestCase {

    private final static String TEST_PROJECT_NAME = "TestOrgAndEditorsProject"; //$NON-NLS-1$

    private final static String TEST_FILE_NAME = "OrgAndEditorTestFile.om"; //$NON-NLS-1$

    private final TransactionalEditingDomain ed = XpdResourcesPlugin
            .getDefault().getEditingDomain();

    private OrgModel model;

    private IProject project;

    @Override
    protected void setUp() throws Exception {
        try {
            project = TestUtil.createProject(TEST_PROJECT_NAME);
            assertNotNull("Failed to create the project", project); //$NON-NLS-1$
            assertTrue("Cannot access newly created project", //$NON-NLS-1$
                    project.isAccessible());

            URI testResourceURI =
                    URI.createPlatformResourceURI(project.getFullPath()
                            .toString() + "/" + TEST_FILE_NAME, true); //$NON-NLS-1$
            model =
                    OMTestUtil.createModelWithDiagram(testResourceURI,
                            Boolean.TRUE,
                            Boolean.TRUE);
        } catch (Exception e) {
            tearDown();
        }
    }

    /**
     * This will test the deletion of the .om file (OM file without any
     * changes). This should result in its editor being closed.
     * 
     * @throws Exception
     */
    public void testDeleteOMFile() throws Exception {
        // Verify that an editor has been opened
        IEditorReference[] editorReferences = getEditorReferences();
        assertEquals("Number of open editors after creation an Organization Model", //$NON-NLS-1$
                1,
                editorReferences.length);

        // Delete the file
        deleteFile(model.eResource());

        // Verify that the editor has been closed
        editorReferences = getEditorReferences();
        assertEquals("Number of open editors after deletion of the .om file", //$NON-NLS-1$
                0,
                editorReferences.length);
    }

    /**
     * Test the deletion of an Organization. This should result in its editor
     * being closed.
     * 
     * @throws Exception
     */
    public void testDeleteOrganization() throws Exception {
        // Verify that an editor has been opened
        IEditorReference[] editorReferences = getEditorReferences();
        assertEquals("Number of open editors after creation an Organization Model", //$NON-NLS-1$
                1,
                editorReferences.length);

        // Delete the Organization
        assertEquals("Number of Organizations in the model", 1, model //$NON-NLS-1$
                .getOrganizations().size());
        Organization org = model.getOrganizations().get(0);

        WorkingCopy wc = getWorkingCopy(model);
        assertTrue("Unable to get the working copy for the Organization Model", //$NON-NLS-1$
                wc instanceof AbstractGMFWorkingCopy);
        AbstractGMFWorkingCopy gmfWc = (AbstractGMFWorkingCopy) wc;

        // Confirm that there a 2 diagrams in the resource
        assertEquals("Number of Diagrams in the Model resource", 2, gmfWc //$NON-NLS-1$
                .getDiagrams().size());

        Diagram diagram = getDiagram(gmfWc.getDiagrams(), org);
        assertNotNull("No diagram associated with the Organization found in the resource", //$NON-NLS-1$
                diagram);

        OMDeleteAction deleteAction = new OMDeleteAction(ed, true);
        deleteAction.selectionChanged(new StructuredSelection(org));
        assertTrue("Delete action is not enabled after updating its selection with the Organization to delete", //$NON-NLS-1$
                deleteAction.isEnabled());
        // Run the action to delete the Organization
        deleteAction.run();
        TestUtil.waitForJobs(500);

        // Check that the Organization has been deleted
        assertEquals("Number of Organizations after the Organization was deleted", //$NON-NLS-1$
                0,
                model.getOrganizations().size());
        // Should only be one Diagram
        assertEquals("Number of diagrams after the Organization was deleted", //$NON-NLS-1$
                1,
                gmfWc.getDiagrams().size());
        // Get the Diagram associated with the Model - if one is not found then
        // the wrong Diagram has been deleted
        diagram = getDiagram(gmfWc.getDiagrams(), model);
        assertNotNull("The wrong Diagram has been removed when the Organization was deleted", //$NON-NLS-1$
                diagram);

        // No editors should be open
        editorReferences = getEditorReferences();
        assertEquals("Number of open editors after the Organization was deleted", //$NON-NLS-1$
                0,
                editorReferences.length);
    }

    @Override
    protected void tearDown() throws Exception {
        if (project != null) {
            TestUtil.removeProject(project.getName());
        }
    }

    /**
     * Get the diagram associated with the given semantic element.
     * 
     * @param eo
     * @return
     */
    private Diagram getDiagram(List<Diagram> diagrams, EObject eo) {
        for (Diagram diag : diagrams) {
            EObject element = diag.getElement();
            if (element instanceof View) {
                element = ((View) element).getElement();
            }

            if (element == eo) {
                return diag;
            }
        }
        return null;
    }

    /**
     * Delete the {@link IFile file} associated with this {@link Resource}.
     * 
     * @param resource
     * @throws CoreException
     */
    private void deleteFile(Resource resource) throws CoreException {
        final IFile file = getFile(resource);

        // Delete the file
        ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
            @Override
            public void run(IProgressMonitor monitor) throws CoreException {
                file.delete(true, monitor);
            }
        }, new NullProgressMonitor());
        TestUtil.waitForJobs(500);

        // Check if the file has been deleted
        assertFalse("File + " + file.getName() + " has not been deleted.", //$NON-NLS-1$ //$NON-NLS-2$
                file.exists());
    }

    /**
     * Get the IFile associated with the resource.
     * 
     * @param res
     * @return
     */
    private IFile getFile(Resource res) {
        IFile file = WorkspaceSynchronizer.getFile(res);
        assertNotNull("Unable to get the file from the Organization model resource", //$NON-NLS-1$
                file);
        return file;
    }

    /**
     * Get the open editors.
     * 
     * @return
     */
    private IEditorReference[] getEditorReferences() {
        assertNotNull("Cannot get the workbench", PlatformUI.getWorkbench()); //$NON-NLS-1$
        assertNotNull("Cannot get the active workbench window", PlatformUI //$NON-NLS-1$
                .getWorkbench().getActiveWorkbenchWindow());
        assertNotNull("Cannot get the active page from the active workbench window", //$NON-NLS-1$
                PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage());
        return PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                .getActivePage().getEditorReferences();
    }

    private WorkingCopy getWorkingCopy(EObject eo) {
        assertNotNull("Cannot get resource from the given object", //$NON-NLS-1$
                eo.eResource());
        IFile file = getFile(eo.eResource());
        WorkingCopy wc = XpdResourcesPlugin.getDefault().getWorkingCopy(file);
        assertNotNull("Unable to get working copy for resource: " //$NON-NLS-1$
                + file.getName(),
                wc);
        return wc;
    }

}
