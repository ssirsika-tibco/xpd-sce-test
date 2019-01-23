package com.tibco.xpd.daa.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.BaseSelectionListenerAction;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonMenuConstants;

import com.tibco.xpd.daa.internal.util.BpmProjectChecksumUtils;

/**
 * Shows "Up To Date Status" in project explorer context menu for a DAA in the
 * debug mode.
 *
 * @author jarciuch
 * @since 14 Jul 2015
 */
public class BpmDaaChecksumActionProvider extends CommonActionProvider {

    public BpmDaaChecksumActionProvider() {
    }

    /**
     * @see org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.action.IMenuManager)
     * 
     * @param menu
     */
    @Override
    public void fillContextMenu(IMenuManager menu) {

        if (Platform.inDebugMode()) {
            if (null != getContext()
                    && getContext().getSelection() instanceof IStructuredSelection) {
                IStructuredSelection structuredSelection =
                        (IStructuredSelection) getContext().getSelection();
                if (structuredSelection.size() == 1
                        && structuredSelection.getFirstElement() instanceof IFile) {

                    CheckUpToDateStatus checkUpToDateAction =
                            new CheckUpToDateStatus(
                                    Messages.BpmDaaChecksumActionProvider_CheckDaaStatus_menu);
                    // TODO set image descriptor
                    // checkUpToDateAction.setImageDescriptor(...));

                    checkUpToDateAction.selectionChanged(structuredSelection);

                    menu.appendToGroup(ICommonMenuConstants.GROUP_ADDITIONS,
                            checkUpToDateAction);
                }
            }
        }
    }

    public static class CheckUpToDateStatus extends BaseSelectionListenerAction {

        protected CheckUpToDateStatus(String text) {
            super(text);
        }

        @Override
        public void run() {
            IStructuredSelection selection = getStructuredSelection();
            if (selection.size() == 1
                    && selection.getFirstElement() instanceof IFile) {
                IFile file = (IFile) selection.getFirstElement();
                IProject project = file.getProject();

                Shell shell =
                        PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                                .getShell();
                MessageBox messageBox =
                        new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
                messageBox.setText(Messages.BpmDaaChecksumActionProvider_DaaStatus_title);
                boolean hasProjectChanged =
                        BpmProjectChecksumUtils
                                .hasProjectChangedForDaa(project, file);
                String msg;
                if (hasProjectChanged) {
                    msg =
                            String.format(Messages.BpmDaaChecksumActionProvider_DaaStatusNotUpToDate_message,
                                    file.getName(),
                                    project.getName());
                } else {
                    msg =
                            String.format(Messages.BpmDaaChecksumActionProvider_DaaStatusUpToDate_message,
                                    file.getName(),
                                    project.getName());
                }
                messageBox.setMessage(msg);
                messageBox.open();

            }
        }
    }
}
