package com.tibco.xpd.core.createtestwizards.validationtestwizard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.resources.projectconfig.SpecialFolder;

public class CreateValidationTestAction implements IObjectActionDelegate {

    private Shell shell;

    private List<IResource> sourceResources = Collections.emptyList();

    /**
     * Constructor for Action1.
     */
    public CreateValidationTestAction() {
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
            CreateValidationTestWizard wizard =
                    getValidationTestWizard(sourceResources);

            WizardDialog dialog = new WizardDialog(shell, wizard);

            // The dialog performFinish() will do everything if the user Ok's
            // everything.
            dialog.open();

        }

        return;
    }

    /**
     * @return
     */
    protected CreateValidationTestWizard getValidationTestWizard(
            List<IResource> sourceResources) {
        CreateValidationTestWizard wizard =
                new CreateValidationTestWizard(sourceResources);
        return wizard;
    }

    /**
     * @see IActionDelegate#selectionChanged(IAction, ISelection)
     */
    public void selectionChanged(IAction action, ISelection selection) {
        action.setEnabled(getSourceFiles(selection));
    }

    private boolean getSourceFiles(ISelection selection) {
        sourceResources = new ArrayList<IResource>();

        boolean isValid = false;

        if (!selection.isEmpty() && selection instanceof StructuredSelection) {
            int fileCount = 0;

            StructuredSelection ssel = (StructuredSelection) selection;

            for (Iterator iterator = ssel.iterator(); iterator.hasNext();) {
                Object o = iterator.next();

                if (o instanceof SpecialFolder) {
                    o = ((SpecialFolder) o).getFolder();
                }

                if (o instanceof IFile || o instanceof IFolder
                        || o instanceof IProject) {
                    isValid = true; // have a file selected that's good

                    sourceResources.add((IResource) o);

                } else {
                    isValid = false;
                    break;
                }
            }
        }

        return isValid;
    }

}
