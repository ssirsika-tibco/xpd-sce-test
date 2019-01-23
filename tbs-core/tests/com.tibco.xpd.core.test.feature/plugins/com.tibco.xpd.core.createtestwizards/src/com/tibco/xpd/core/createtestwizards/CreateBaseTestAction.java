package com.tibco.xpd.core.createtestwizards;

import java.util.ArrayList;
import java.util.Collection;
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
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.resources.projectconfig.SpecialFolder;

public class CreateBaseTestAction implements IObjectActionDelegate {

    private Shell shell;

    private List<IResource> sourceResources = Collections.emptyList();

    /**
     * Constructor for Action1.
     */
    public CreateBaseTestAction() {
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
            IWizard wizard = getTestCreationWizard(sourceResources);

            WizardDialog dialog = new WizardDialog(shell, wizard);

            // The dialog performFinish() will do everything if the user Ok's
            // everything.
            dialog.open();

        }

        return;
    }

    /**
     * @param srcResources
     * @return wizard
     */
    protected IWizard getTestCreationWizard(Collection<IResource> srcResources) {
        CreateBaseTestWizard wizard = new CreateBaseTestWizard(srcResources);
        return wizard;
    }

    /**
     * @see IActionDelegate#selectionChanged(IAction, ISelection)
     */
    public void selectionChanged(IAction action, ISelection selection) {
        boolean isEnabled = false;

        if (getSourceFiles(selection)) {
            isEnabled = isEnabled(sourceResources);
        }

        action.setEnabled(isEnabled);
    }

    /**
     * @param sourceResources
     * @return
     */
    protected boolean isEnabled(Collection<IResource> srcResources) {
        return !srcResources.isEmpty();
    }

    protected boolean getSourceFiles(ISelection selection) {
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
