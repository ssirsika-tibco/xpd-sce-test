package com.tibco.bx.validation.resolutions;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import com.tibco.bx.validation.BxValidationPlugin;
import com.tibco.xpd.processeditor.xpdl2.wizards.NewBusinessProcessWizard;
import com.tibco.xpd.processeditor.xpdl2.wizards.NewPageflowProcessWizard;
import com.tibco.xpd.processeditor.xpdl2.wizards.NewProcessWizard;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Hierarchy of classes for the initialisation and opening of new process wizard
 * dialogs.
 * 
 * 
 * @author patkinso
 * @since 9 Jul 2012
 */
public class CreatePackageProcessResolution {

    protected static abstract class BaseCreatePackageProcessResolution
            implements IResolution {

        public void run(IMarker marker) throws ResolutionException {

            final IFile xpdlFile = (IFile) marker.getResource();

            Display.getDefault().asyncExec(new Runnable() {

                public void run() {
                    IWorkbench wb = PlatformUI.getWorkbench();

                    NewProcessWizard wizard = getWizard();
                    wizard.init(wb, new StructuredSelection(xpdlFile));

                    int ret =
                            (new WizardDialog(wb.getActiveWorkbenchWindow()
                                    .getShell(), wizard)).open();

                    /*
                     * Validation won't get re-run until file has saved.
                     */
                    if (ret == WizardDialog.OK) {
                        WorkingCopy workingCopy =
                                WorkingCopyUtil.getWorkingCopy(xpdlFile);

                        if (workingCopy != null
                                && workingCopy.isWorkingCopyDirty()) {
                            try {
                                workingCopy.save();

                            } catch (IOException e) {
                                BxValidationPlugin
                                        .getDefault()
                                        .getLogger()
                                        .error(e,
                                                "Save fail on: " + xpdlFile.getFullPath().toString()); //$NON-NLS-1$
                            }
                        }
                    }
                }

            });
        }

        protected abstract NewProcessWizard getWizard();

    }

    public static class CreateBusinessProcessResolution extends
            BaseCreatePackageProcessResolution {

        /**
         * @see com.tibco.bx.validation.resolutions.CreatePackageProcessResolution.BaseCreatePackageProcessResolution#getWizard()
         * 
         * @return
         */
        @Override
        protected NewProcessWizard getWizard() {
            return new NewBusinessProcessWizard();
        }
    }

    public static class CreatePageFlowResolution extends
            BaseCreatePackageProcessResolution {

        /**
         * @see com.tibco.bx.validation.resolutions.CreatePackageProcessResolution.BaseCreatePackageProcessResolution#getWizard()
         * 
         * @return
         */
        @Override
        protected NewProcessWizard getWizard() {
            return new NewPageflowProcessWizard();
        }
    }

}
