/**
 * CopyToClipboardAsImageAction.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processwidget.popup.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.gef.tools.ToolUtilities;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.actions.ActionDelegate;

import com.tibco.xpd.processwidget.XPDImageClipboardUtil;
import com.tibco.xpd.processwidget.parts.EditPartUtil;
import com.tibco.xpd.processwidget.parts.ModelAdapterEditPart;

/**
 * CopyToClipboardAsImageAction
 * 
 * Copy the selected diagram objects to clipboard as an image.
 */
public class CopyImageToClipboardAction extends ActionDelegate {
    private static final boolean IMAGE_COPY_SUPPORTED =
            System.getProperty("os.name").toUpperCase().startsWith("WIN"); //$NON-NLS-1$ //$NON-NLS-2$

    private List<ModelAdapterEditPart> selectionList = null;

    public void run(IAction action) {

        if (selectionList != null && selectionList.size() > 0) {

            // SWT does not support Image->Clipboard so we have to convert to
            // AWT image and clipboard
            if (IMAGE_COPY_SUPPORTED) {
                Shell shell = Display.getDefault().getActiveShell();
                Cursor curs = null;
                if (shell != null) {
                    curs = shell.getCursor();
                    shell.setCursor(Display.getDefault()
                            .getSystemCursor(SWT.CURSOR_WAIT));
                }

                try {
                    Image img =
                            EditPartUtil
                                    .createImageFromEditParts(selectionList);

                    if (img != null) {
                        ImageData imgData = img.getImageData();
                        img.dispose();

                        XPDImageClipboardUtil.copyImageToClipboard(imgData);

                    }
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    
                } finally {
                    if (shell != null) {
                        shell.setCursor(curs);
                    }
                }
            }
        }
    }

    public void selectionChanged(IAction action, ISelection selection) {
        boolean cmd_ok = true;

        List<ModelAdapterEditPart> selParts =
                new ArrayList<ModelAdapterEditPart>();

        if (selection instanceof IStructuredSelection && IMAGE_COPY_SUPPORTED) {
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
            selectionList =
                    (List<ModelAdapterEditPart>) ToolUtilities
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
