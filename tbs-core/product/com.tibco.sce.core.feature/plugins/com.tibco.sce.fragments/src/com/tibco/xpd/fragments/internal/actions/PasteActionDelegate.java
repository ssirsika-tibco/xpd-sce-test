package com.tibco.xpd.fragments.internal.actions;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.actions.ActionFactory;

import com.tibco.xpd.fragments.FragmentsActivator;
import com.tibco.xpd.fragments.FragmentsContributor.ClipboardFragmentData;
import com.tibco.xpd.fragments.internal.Messages;
import com.tibco.xpd.fragments.internal.FragmentsExtensionHelper.FragmentsProvider;
import com.tibco.xpd.fragments.internal.impl.FragmentCategoryImpl;
import com.tibco.xpd.fragments.internal.impl.FragmentImpl;
import com.tibco.xpd.fragments.internal.operations.CreateFragmentOperation;
import com.tibco.xpd.fragments.internal.utils.FragmentsUtil;

/**
 * Paste action in the fragments view.
 * 
 * @author njpatel
 * 
 */
public class PasteActionDelegate extends FragmentActionDelegate {

    private ClipboardFragmentData clipboardData;
    private Shell shell;

    @Override
    public void init(IViewPart view) {
        shell = view.getSite().getShell();
        super.init(view);
        IAction action = getAction();

        if (action != null) {
            view.getViewSite().getActionBars().setGlobalActionHandler(
                    ActionFactory.PASTE.getId(), action);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.IActionDelegate2#runWithEvent(org.eclipse.jface.action
     * .IAction, org.eclipse.swt.widgets.Event)
     */
    public void runWithEvent(IAction action, Event event) {
        if (clipboardData != null) {
            IStructuredSelection selection = getSelection();
            Object element = selection.getFirstElement();
            FragmentCategoryImpl cat = null;

            if (element instanceof FragmentCategoryImpl) {
                cat = (FragmentCategoryImpl) element;
            } else if (element instanceof FragmentImpl) {
                cat = (FragmentCategoryImpl) ((FragmentImpl) element)
                        .getParent();
            }

            if (cat != null) {
                // Get the fragment name - if this is a local transfer paste
                // then the copy name should be used
                if (clipboardData.getFragment() != null) {
                    clipboardData.setName(FragmentsUtil.getCopyOfName(cat,
                            clipboardData.getFragment()));
                } else if (clipboardData.getName() == null) {
                    clipboardData.setName(cat.getNextFragmentName());
                }

                CreateFragmentOperation op = new CreateFragmentOperation(cat,
                        clipboardData);

                try {
                    FragmentsUtil.execute(op, null);
                } catch (ExecutionException e) {
                    FragmentsActivator.getDefault().getLogger().error(e);
                    ErrorDialog
                            .openError(
                                    shell,
                                    Messages.PasteActionDelegate_newFragmentError_dialog_title,
                                    Messages.PasteActionDelegate_newFragmentError_dialog_message,
                                    new Status(IStatus.ERROR,
                                            FragmentsActivator.PLUGIN_ID, e
                                                    .getLocalizedMessage(), e));
                }
            }
        }
    }

    @Override
    protected boolean isEnabled(Object sel) {
        clipboardData = null;
        FragmentCategoryImpl cat = null;

        if (sel instanceof FragmentCategoryImpl) {
            cat = (FragmentCategoryImpl) sel;
        } else if (sel instanceof FragmentImpl) {
            cat = (FragmentCategoryImpl) ((FragmentImpl) sel).getParent();
        }

        // Can only paste in user categories
        if (cat != null && !cat.isSystem()) {
            FragmentsProvider provider = cat.getProvider();

            if (provider != null) {
                try {
                    clipboardData = provider.getProviderClass()
                            .getFromClipboard(cat);
                } catch (CoreException e) {
                    FragmentsActivator.getDefault().getLogger().error(e);
                }
            }
        }

        return clipboardData != null && clipboardData.getData() != null;
    }

}
