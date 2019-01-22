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
import java.util.List;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.actions.ActionDelegate;

import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.ProcessWidgetPlugin;
import com.tibco.xpd.processwidget.WidgetRGB;
import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter;
import com.tibco.xpd.processwidget.adapters.GraphicalColorAdapter;
import com.tibco.xpd.processwidget.adapters.TaskAdapter;
import com.tibco.xpd.processwidget.internal.Messages;
import com.tibco.xpd.processwidget.parts.ModelAdapterEditPart;
import com.tibco.xpd.processwidget.parts.TaskEditPart;

/**
 * SetFillColorAction
 * 
 */
public class ResetToDefaultFormat extends ActionDelegate {
    boolean cmd_ok = false;

    List<ModelAdapterEditPart> selectionList = null;

    private EditingDomain editingDomain;

    @Override
    public void run(IAction action) {
        if (cmd_ok && selectionList.size() > 0 && editingDomain != null) {

            List<ModelAdapterEditPart> editParts = selectionList;
            CompoundCommand cmd =
                    getResetToDefaultFormatCommand(editingDomain, editParts);

            // And finally execute the command.
            editingDomain.getCommandStack().execute(cmd);
        }

        return;
    }

    /**
     * @param editingDomain2
     * @param editParts
     * @return
     */
    public static CompoundCommand getResetToDefaultFormatCommand(
            EditingDomain editingDomain, List<ModelAdapterEditPart> editParts) {
        CompoundCommand cmd = new CompoundCommand();
        cmd.setLabel(Messages.ResetToDefaultFormat_menu);

        for (Iterator iter = editParts.iterator(); iter.hasNext();) {
            ModelAdapterEditPart ep = (ModelAdapterEditPart) iter.next();
            if (ep != null) {
                BaseProcessAdapter baseAdapter = ep.getModelAdapter();

                ProcessWidgetColors colors =
                        ProcessWidgetColors.getInstance(baseAdapter);

                String colorIDForType = ep.getFillColorIDForPartType();
                if (colorIDForType != null) {
                    // Get default fill colour for type
                    WidgetRGB wRGB =
                            colors.getGraphicalNodeColor(null, colorIDForType);

                    GraphicalColorAdapter adp =
                            (GraphicalColorAdapter) ep.getModelAdapter();
                    if (adp != null) {
                        cmd.append(adp.getSetFillColorCommand(editingDomain,
                                wRGB != null ? wRGB.toString() : null));
                    }
                }

                colorIDForType = ep.getLineColorIDForPartType();
                if (colorIDForType != null) {
                    // Get default line colour for type
                    WidgetRGB wRGB =
                            colors.getGraphicalNodeColor(null, colorIDForType);

                    GraphicalColorAdapter adp =
                            (GraphicalColorAdapter) ep.getModelAdapter();
                    if (adp != null) {
                        cmd.append(adp.getSetLineColorCommand(editingDomain,
                                wRGB != null ? wRGB.toString() : null));
                    }
                }

                /*
                 * Return any custom task icon back to default
                 */
                if (ep instanceof TaskEditPart) {
                    TaskAdapter actAdp =
                            ((TaskEditPart) ep).getActivityAdapter();

                    String taskIcon = actAdp.getTaskIcon();

                    String defIconPath = null;
                    String iconRegId = actAdp.getTaskTypeIconRegistryId();
                    if (iconRegId != null && iconRegId.length() > 0) {
                        IPreferenceStore prefStore =
                                ProcessWidgetPlugin.getDefault()
                                        .getPreferenceStore();
                        defIconPath = prefStore.getString(iconRegId);
                    }

                    boolean taskIconDefined =
                            (taskIcon != null && taskIcon.length() > 0);
                    boolean regIconDefined =
                            (defIconPath != null && defIconPath.length() > 0);

                    if (taskIconDefined != regIconDefined) {
                        cmd.append(actAdp.getSetTaskIconCommand(editingDomain,
                                defIconPath));
                    }
                }
            }
        }
        return cmd;
    }

    @Override
    public void selectionChanged(IAction action, ISelection selection) {
        cmd_ok = true;

        if (selectionList == null) {
            selectionList = new ArrayList<ModelAdapterEditPart>();
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
                            selectionList.add((ModelAdapterEditPart) obj);
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
    @Override
    public void dispose() {
        if (selectionList != null) {
            selectionList.clear();
            selectionList = null;
        }

        editingDomain = null;

        super.dispose();
    }

}
