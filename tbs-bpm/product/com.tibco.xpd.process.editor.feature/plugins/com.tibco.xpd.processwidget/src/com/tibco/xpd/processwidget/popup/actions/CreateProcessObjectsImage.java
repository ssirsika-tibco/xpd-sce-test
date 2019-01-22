/**
 * CreateProcessObjectsImage.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processwidget.popup.actions;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.gef.tools.ToolUtilities;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionDelegate;

import com.tibco.xpd.processwidget.internal.Messages;
import com.tibco.xpd.processwidget.parts.EditPartUtil;
import com.tibco.xpd.processwidget.parts.ModelAdapterEditPart;

/**
 * CreateProcessObjectsImage
 * 
 */
public class CreateProcessObjectsImage extends ActionDelegate {
    private List<ModelAdapterEditPart> selectionList = null;

    public void run(IAction action) {

        if (selectionList != null && selectionList.size() > 0) {

            Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                    .getShell();
            FileDialog fd = new FileDialog(shell, SWT.SAVE);

            // When SWT image loader supports png and gif properly we can use
            // this code...
            // fd.setFilterExtensions(new String[] { "*.bmp;*.png;*.jpg;*.gif"
            // });
            // fd
            // .setFilterNames(new String[] { "Image Files
            // (*.bmp;*.png;*.jpg;*.gif)" });

            fd.setFilterExtensions(new String[] { "*.bmp;*.jpg" }); //$NON-NLS-1$
            fd
                    .setFilterNames(new String[] { Messages.CreateProcessObjectsImage_ImageFiles_label });

            String filePath = fd.open();

            if (filePath != null && filePath.length() > 0) {
                File file = new File(filePath);

                if (file.exists()) {
                    MessageDialog msg = new MessageDialog(
                            shell,
                            Messages.CreateProcessObjectsImage_SaveAsImageDialog_title,
                            null,
                            "\"" //$NON-NLS-1$
                                    + filePath + "\" " //$NON-NLS-1$
                                    + Messages.CreateProcessObjectsImage_FileAlreadyExists_message,
                            MessageDialog.WARNING, new String[] {
                                    IDialogConstants.YES_LABEL,
                                    IDialogConstants.NO_LABEL }, 1);
                    int ret = msg.open();
                    if (ret != 0) {
                        return;
                    }
                }

                EditPartUtil.createImageFileFromEditParts(selectionList, file);
            }
        }
    }

    public void selectionChanged(IAction action, ISelection selection) {
        boolean cmd_ok = true;

        List<ModelAdapterEditPart> selParts = new ArrayList<ModelAdapterEditPart>();

        if (selection instanceof IStructuredSelection) {
            IStructuredSelection sel = (IStructuredSelection) selection;
            if (sel.size() > 0) {

                for (Iterator iter = sel.iterator(); iter.hasNext();) {
                    Object obj = iter.next();
                    if (obj instanceof ModelAdapterEditPart) {
                        ModelAdapterEditPart ep = (ModelAdapterEditPart) obj;
                        selParts.add(ep);

                    } else {
                        cmd_ok = false;
                        break;
                    }
                }
            }
        } else {
            cmd_ok = false;
        }

        if (cmd_ok) {
            action.setEnabled(true);
            selectionList = (List<ModelAdapterEditPart>) ToolUtilities
                    .getSelectionWithoutDependants(selParts);

        } else {
            action.setEnabled(false);
            selectionList = (List<ModelAdapterEditPart>) Collections.EMPTY_LIST;
        }

        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.actions.ActionDelegate#dispose()
     */
    public void dispose() {
        if (selectionList != null) {
            selectionList.clear();
            selectionList = null;
        }

        super.dispose();
    }

}
