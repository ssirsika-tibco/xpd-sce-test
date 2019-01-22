package com.tibco.bx.validation.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;
import org.eclipse.ltk.ui.refactoring.resource.RenameResourceWizard;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Invoke Eclipse's RenameResourceWizard to give user an opportunity to change
 * project name to a valid one
 * 
 * @author patkinso
 * @since 18 Oct 2012
 */
public class RenameProjectResolution implements IResolution {

    public RenameProjectResolution() {
        // TODO Auto-generated constructor stub
    }

    public void run(IMarker marker) throws ResolutionException {

        // for BPM destination projects want to prevent use of a leading dot
        //if (!projectName.startsWith(".")) { //$NON-NLS-1$

        IProject proj = (IProject) marker.getResource();
        RenameResourceWizard refactoringWizard = new RenameResourceWizard(proj);
        RefactoringWizardOpenOperation op =
                new RefactoringWizardOpenOperation(refactoringWizard);
        try {
            Shell activeShell =
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                            .getShell();
            op.run(activeShell, ""); //$NON-NLS-1$
        } catch (InterruptedException e) {
            // do nothing
        }

    }

}
