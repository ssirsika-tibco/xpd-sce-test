package com.tibco.xpd.core.createtestwizards.classapi;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.core.createtestwizards.CreateBaseTestPage;

public class CreateClassApiTestAction implements IObjectActionDelegate {

    private Shell shell;

    private IProject selectedTargetJavaProject = null;

    private IFolder selectedTargetJavaPackageFolder = null;

    /**
     * Constructor for Action1.
     */
    public CreateClassApiTestAction() {
        super();
    }

    /**
     * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
     */
    public void setActivePart(IAction action, IWorkbenchPart targetPart) {
        shell = targetPart.getSite().getShell();
    }

    /**
     * @see IActionDelegate#run(IAction)
     */
    public void run(IAction action) {

        if (PlatformUI.getWorkbench().saveAllEditors(true)) {
            CreateClassApiTestWizard wizard =
                    new CreateClassApiTestWizard(selectedTargetJavaProject,
                            selectedTargetJavaPackageFolder);

            WizardDialog dialog = new WizardDialog(shell, wizard);

            // The dialog performFinish() will do everything if the user Ok's
            // everything.
            dialog.open();
        }

        return;
    }

    /**
     * @see IActionDelegate#selectionChanged(IAction, ISelection)
     */
    public void selectionChanged(IAction action, ISelection selection) {
        selectedTargetJavaProject = null;
        selectedTargetJavaPackageFolder = null;

        /*
         * Valid selection is a folder that is under the src folder in a java
         * plugin project.
         */
        if (!selection.isEmpty() && selection instanceof StructuredSelection) {
            StructuredSelection ssel = (StructuredSelection) selection;

            if (ssel.size() == 1) {
                Object sel = ssel.getFirstElement();

                if (sel instanceof IPackageFragment) {
                    IPackageFragment pkgFrag = (IPackageFragment) sel;

                    if (pkgFrag.getResource() instanceof IFolder) {

                        IFolder folder = (IFolder) pkgFrag.getResource();

                        IProject project = folder.getProject();

                        if (isJavaProjectAndPackageFolder(project, folder)) {
                            selectedTargetJavaProject = project;
                            selectedTargetJavaPackageFolder = folder;
                        }
                    }
                }
            }
        }

        action.setEnabled(selectedTargetJavaProject != null
                && selectedTargetJavaPackageFolder != null);

        return;
    }

    /**
     * @param project
     * @param folder
     * @return true if project is java plugin and folder is package folder.
     */
    private boolean isJavaProjectAndPackageFolder(IProject project,
            IFolder folder) {
        try {
            if (project.hasNature(CreateBaseTestPage.PLUGIN_PROJECT_NATURE)
                    && !project.getName().startsWith(".")) {

                IResource parent = folder.getParent();
                while (parent instanceof IFolder) {
                    if (CreateBaseTestPage.JAVA_SRC_FOLDERNAME.equals(parent
                            .getName())) {
                        return true;
                    }

                    parent = parent.getParent();
                }
            }

        } catch (CoreException e) {
            e.printStackTrace();
        }
        return false;
    }
}
