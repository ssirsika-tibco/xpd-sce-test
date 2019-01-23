/*
 ** 
 **  MODULE:             $RCSfile: $ 
 **                      $Revision: $ 
 **                      $Date: $ 
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc., All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */

package com.tibco.xpd.processwidget.popup.actions;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.xpd.processwidget.adapters.ActivityMarkerType;
import com.tibco.xpd.processwidget.adapters.TaskAdapter;
import com.tibco.xpd.processwidget.internal.Messages;
import com.tibco.xpd.processwidget.parts.TaskEditPart;

/**
 * @author wzurek
 * 
 */
public class BaseSetMarkerAction implements IActionDelegate {

    private final ActivityMarkerType type;

    private TaskAdapter[] taskAdapters;

    /**
     * Constructor for IsServiceTaskAction.
     */
    public BaseSetMarkerAction(ActivityMarkerType type) {
        super();
        this.type = type;
    }

    /*
     * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
     */
    public void setActivePart(IAction action, IWorkbenchPart targetPart) {
    }

    /*
     * @see IActionDelegate#run(IAction)
     */
    public void run(IAction action) {
        if (taskAdapters != null && taskAdapters.length > 0) {

            TaskAdapter adapter = taskAdapters[0];
            EObject eo = (EObject) adapter.getTarget();
            IEditingDomainProvider ep = (IEditingDomainProvider) EcoreUtil
                    .getExistingAdapter(eo.eResource(),
                            IEditingDomainProvider.class);

            CompoundCommand cmd = new CompoundCommand();
            cmd.setLabel(Messages.BaseSetMarkerAction_SetMarker_menu);
            EditingDomain ed = ep.getEditingDomain();
            for (int i = 0; i < taskAdapters.length; i++) {
                Set tt = new HashSet(taskAdapters[i].getMarkers());
                if (!action.isChecked()) {
                    tt.remove(type);
                } else {
                    if (!tt.contains(type)) {
                        tt.add(type);
                    }
                }
                validateMarkerSet(tt);
                cmd.append(taskAdapters[i].getSetMarkersCommand(ed, tt));
            }
            ed.getCommandStack().execute(cmd);
        }
    }
    
    /**
     * This method is called just prior to creating the setMarkers Command 
     * for an activity.
     * 
     * This is intended to give the sub-class the opportunity to modify the
     * set of markers that are switched on when an individual marker is changed.
     * 
     * @param markerTypes
     */
    protected void validateMarkerSet(Set markerTypes) {
    	// Nothing to do by default.
    }

	/*
     * @see IActionDelegate#selectionChanged(IAction, ISelection)
     */
    public void selectionChanged(IAction action, ISelection selection) {

        if (selection instanceof IStructuredSelection) {
            IStructuredSelection sel = (IStructuredSelection) selection;
            if (sel.size() > 0) {
                taskAdapters = new TaskAdapter[sel.size()];
                int i = 0;
                boolean tt = true;
                for (Iterator iter = sel.iterator(); iter.hasNext();) {
                    Object obj = iter.next();
                    if (obj instanceof TaskEditPart) {
                        TaskAdapter adapter = (TaskAdapter) ((TaskEditPart) obj)
                                .getModelAdapter();
                        if (!adapter.getMarkers().contains(type)) {
                            tt = false;
                        }
                        taskAdapters[i++] = adapter;
                    } else {
                        taskAdapters = null;
                        action.setEnabled(false);
                        action.setChecked(false);
                        return;
                    }
                }
                action.setEnabled(true);
                action.setChecked(tt);
                return;
            }
        }
        action.setEnabled(false);
        action.setChecked(false);
    }
}
