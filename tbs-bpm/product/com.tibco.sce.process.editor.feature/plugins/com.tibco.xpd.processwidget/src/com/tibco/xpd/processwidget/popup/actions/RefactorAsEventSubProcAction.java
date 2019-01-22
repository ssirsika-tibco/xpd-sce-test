/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.processwidget.popup.actions;

import java.util.List;

import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processwidget.adapters.CreateAccessibleObjectCommand;
import com.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter;
import com.tibco.xpd.processwidget.impl.ProcessWidgetImpl;

/**
 * Action class to refactor an event handler flow to an event sub-process.This
 * action is only applicable when the selection contains only one Event handler
 * and no Start Event.
 * 
 * @author sajain
 * @since Oct 7, 2014
 */
public class RefactorAsEventSubProcAction extends
        RefactorAsEmbeddedSubProcAction {

    /**
     * @see com.tibco.xpd.processwidget.popup.actions.RefactorAsEmbeddedSubProcAction#getRefactorCommand(java.util.List,
     *      com.tibco.xpd.processwidget.impl.ProcessWidgetImpl)
     * 
     * @param objs
     * @param widget
     * @return
     */
    @Override
    protected CreateAccessibleObjectCommand getRefactorCommand(
            List<Object> objs, ProcessWidgetImpl widget) {

        ProcessDiagramAdapter processAdapter = getProcessAdapter();

        EditingDomain editingDomain = getEditingDomain();

        return processAdapter.getRefactorAsEventSubProcCommand(editingDomain,
                objs,
                widget);
    }

}
