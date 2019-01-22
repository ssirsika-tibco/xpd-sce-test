/**
 * SetFillColorAction.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processwidget.popup.actions;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionDelegate;

import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.WidgetRGB;
import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter;
import com.tibco.xpd.processwidget.adapters.GraphicalColorAdapter;
import com.tibco.xpd.processwidget.internal.Messages;
import com.tibco.xpd.processwidget.parts.ModelAdapterEditPart;

/**
 * SetFillColorAction
 * 
 */
public class SetFillColorAction extends ActionDelegate {
    boolean cmd_ok = false;

    ArrayList selectionList = null;

    private EditingDomain editingDomain;

    public void run(IAction action) {
        if (cmd_ok && selectionList.size() > 0) {
            ColorDialog colorDlg =
                    new ColorDialog(PlatformUI.getWorkbench()
                            .getActiveWorkbenchWindow().getShell());

            ModelAdapterEditPart ep =
                    (ModelAdapterEditPart) selectionList.get(0);
            if (ep != null) {

                BaseProcessAdapter baseAdapter = ep.getModelAdapter();
                GraphicalColorAdapter adp = (GraphicalColorAdapter) baseAdapter;
                if (adp != null) {
                    // Set initial color for color dialog (from first in
                    // selection list)
                    String colorIDForType = ep.getFillColorIDForPartType();

                    ProcessWidgetColors colors = ProcessWidgetColors.getInstance(baseAdapter);
                    WidgetRGB wRGB =
                            colors.getGraphicalNodeColor(adp, colorIDForType);

                    if (wRGB != null) {
                        colorDlg.setRGB(wRGB.getRGB());
                    } else {
                        colorDlg.setRGB(new RGB(255, 255, 255));
                    }

                    colorDlg.setText(Messages.SetFillColorAction_title);

                    RGB newColor = colorDlg.open();
                    if (newColor != null) {
                        // User selected new color, set it on all the objects.
                        WidgetRGB newWRGB = new WidgetRGB(newColor);
                        String newColorStr = newWRGB.toString();

                        CompoundCommand cmd = new CompoundCommand();

                        for (Iterator iter = selectionList.iterator(); iter
                                .hasNext();) {
                            ep = (ModelAdapterEditPart) iter.next();
                            if (ep != null) {
                                adp =
                                        (GraphicalColorAdapter) ep
                                                .getModelAdapter();
                                if (adp != null) {
                                    cmd
                                            .append(adp
                                                    .getSetFillColorCommand(editingDomain,
                                                            newColorStr));
                                }
                            }
                        }

                        // And finally execute the command.
                        if (editingDomain != null) {
                            editingDomain.getCommandStack().execute(cmd);
                        }
                    }
                }
            }
        }
    }

    public void selectionChanged(IAction action, ISelection selection) {
        cmd_ok = true;

        if (selectionList == null) {
            selectionList = new ArrayList();
        } else {
            selectionList.clear();
        }

        if (selection instanceof IStructuredSelection) {
            IStructuredSelection sel = (IStructuredSelection) selection;
            if (sel.size() > 0) {

                for (Iterator iter = sel.iterator(); iter.hasNext();) {
                    Object obj = iter.next();
                    if (obj instanceof ModelAdapterEditPart) {
                        Object adp =
                                ((ModelAdapterEditPart) obj).getModelAdapter();

                        if (!(adp instanceof GraphicalColorAdapter)) {
                            cmd_ok = false;
                            break;
                        } else {
                            // It's ok, add to list of selections.
                            selectionList.add(obj);
                            editingDomain =
                                    ((ModelAdapterEditPart) obj)
                                            .getEditingDomain();
                        }
                    } else {
                        cmd_ok = false;
                        break;
                    }
                }
            }
        } else {
            cmd_ok = false;
        }

        // If everything is ok create the command to change the transactional
        // status of the selected subprocess activity(s)
        if (cmd_ok) {
            action.setEnabled(true);
        } else {
            action.setEnabled(false);
            selectionList.clear();
        }

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

        editingDomain = null;

        super.dispose();
    }

}
