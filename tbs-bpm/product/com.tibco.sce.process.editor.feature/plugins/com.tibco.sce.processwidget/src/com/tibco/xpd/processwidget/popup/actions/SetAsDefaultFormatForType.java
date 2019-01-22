/**
 * SetAsDefaultFormatForType.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processwidget.popup.actions;

import java.util.ArrayList;

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
import com.tibco.xpd.processwidget.parts.ModelAdapterEditPart;
import com.tibco.xpd.processwidget.parts.TaskEditPart;

/**
 * SetAsDefaultFormatForType
 * 
 */
public class SetAsDefaultFormatForType extends ActionDelegate {
    boolean cmd_ok = false;

    ArrayList selectionList = null;

    @Override
    public void run(IAction action) {
        if (cmd_ok && selectionList.size() > 0) {
            ModelAdapterEditPart ep =
                    (ModelAdapterEditPart) selectionList.get(0);
            if (ep != null) {

                setAsDefaultFormatForType(ep);
            }
        }
        return;
    }

    /**
     * @param ep
     * @param baseAdapter
     */
    public static void setAsDefaultFormatForType(ModelAdapterEditPart ep) {
        BaseProcessAdapter baseAdapter = ep.getModelAdapter();
        if (baseAdapter instanceof GraphicalColorAdapter) {
            GraphicalColorAdapter adp = (GraphicalColorAdapter) baseAdapter;

            if (adp != null) {
                // Set initial color for color dialog (from first in
                // selection list)
                String colorIDForType = ep.getFillColorIDForPartType();
                ProcessWidgetColors colors =
                        ProcessWidgetColors.getInstance(ep);
                if (colorIDForType != null) {
                    WidgetRGB wRGB =
                            colors.getGraphicalNodeColor(adp,
                                    colorIDForType,
                                    true);

                    colors.setDefaultForType(colorIDForType, wRGB);
                }

                colorIDForType = ep.getLineColorIDForPartType();
                if (colorIDForType != null) {
                    WidgetRGB wRGB =
                            colors.getGraphicalNodeColor(adp,
                                    colorIDForType,
                                    true);

                    colors.setDefaultForType(colorIDForType, wRGB);
                }

                /* Store icon path as default for type */
                if (ep instanceof TaskEditPart) {
                    TaskAdapter actAdp =
                            ((TaskEditPart) ep).getActivityAdapter();

                    String taskIconPath = actAdp.getTaskIcon();
                    if (taskIconPath == null) {
                        taskIconPath = ""; //$NON-NLS-1$
                    }

                    String iconRegId = actAdp.getTaskTypeIconRegistryId();
                    if (iconRegId != null && iconRegId.length() > 0) {
                        IPreferenceStore prefStore =
                                ProcessWidgetPlugin.getDefault()
                                        .getPreferenceStore();
                        prefStore.setValue(iconRegId, taskIconPath);
                    }
                }
            }
        }
        return;
    }

    @Override
    public void selectionChanged(IAction action, ISelection selection) {
        cmd_ok = false;

        if (selectionList == null) {
            selectionList = new ArrayList();
        } else {
            selectionList.clear();
        }

        if (selection instanceof IStructuredSelection) {
            IStructuredSelection sel = (IStructuredSelection) selection;
            if (sel.size() == 1) {

                Object obj = sel.getFirstElement();
                if (obj instanceof ModelAdapterEditPart) {
                    BaseProcessAdapter adp =
                            ((ModelAdapterEditPart) obj).getModelAdapter();

                    if ((adp instanceof GraphicalColorAdapter)) {
                        // It's ok, add to list of selections.
                        selectionList.add(obj);

                        cmd_ok = true;
                    }
                }
            }
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

        super.dispose();
    }

}
