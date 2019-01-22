/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.schema.ui.test;

import junit.framework.TestCase;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.rest.ui.RestServicesUtil;
import com.tibco.xpd.rsd.ui.wizards.NewRsdFileWizard;
import com.tibco.xpd.ui.wizards.newproject.BasicNewXpdResourceWizard;

/**
 * Functional test for RSD editor. Test creating a new RSD file through the
 * wizard.
 * 
 * @author nwilson
 * @since 30 Mar 2015
 */
public class RsdEditorTest extends TestCase {

    /**
     * Test creating a new RSD file through the wizard.
     */
    public void testCreateRsdFile() {
        NewRsdFileWizard wizard = new NewRsdFileWizard();
        createWizard("createRSD", wizard); //$NON-NLS-1$
        assertTrue(wizard.canFinish());
        assertTrue(wizard.performFinish());
        IEditorPart editor = getActiveEditor();
        /*
         * XPD-7633: In this test we first created a Rest service project and
         * then created RSD file "createRSD" in it. Now while creating a new
         * Rest Service project we create a default rsd file with the name of
         * the project. Hence if a new file is created with the same name then
         * it will be suffixed with an incremented number.
         */
        assertEquals("createRSD1.rsd", editor.getTitle()); //$NON-NLS-1$
    }

    /**
     * @return The currently active editor.
     */
    private IEditorPart getActiveEditor() {
        IWorkbench workbench = PlatformUI.getWorkbench();
        assertNotNull(workbench);
        IWorkbenchWindow activeWorkbenchWindow =
                workbench.getActiveWorkbenchWindow();
        assertNotNull(activeWorkbenchWindow);
        IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
        assertNotNull(activePage);
        IEditorPart editor = activePage.getActiveEditor();
        assertNotNull(editor);
        return editor;
    }

    /**
     * @param projectName
     *            The name of the project to create.
     * @param wizard
     *            The wizard to use.
     */
    private void createWizard(String projectName,
            BasicNewXpdResourceWizard wizard) {
        IProject project =
                TestUtil.createBPMProjectFromWizard(projectName,
                        "com.tibco.xpd.rest.wizard.project.new"); //$NON-NLS-1$
        SpecialFolder sf =
                SpecialFolderUtil.getSpecialFolderOfKind(project,
                        RestServicesUtil.REST_SPECIAL_FOLDER_KIND);
        IWorkbench workbench = PlatformUI.getWorkbench();
        assertNotNull(workbench);
        wizard.init(workbench, new StructuredSelection(sf));
        IWorkbenchWindow activeWorkbenchWindow =
                workbench.getActiveWorkbenchWindow();
        assertNotNull(activeWorkbenchWindow);
        WizardDialog dialog =
                new WizardDialog(activeWorkbenchWindow.getShell(), wizard);
        dialog.create();
    }
}
