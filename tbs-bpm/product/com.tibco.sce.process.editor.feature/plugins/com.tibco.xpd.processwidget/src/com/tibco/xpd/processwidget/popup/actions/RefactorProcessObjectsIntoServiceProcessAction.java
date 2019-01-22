/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.processwidget.popup.actions;

import java.util.List;

import com.tibco.xpd.processwidget.adapters.CreateAccessibleObjectCommand;
import com.tibco.xpd.processwidget.impl.ProcessWidgetImpl;

/**
 * Action that refactors selected objects in a business process/pageflow process
 * and its sub types/service process into a new service process. This will cause
 * the validation errors for any un-supported activities in a service process
 * after refactor. Also this will cause the new service process to set the
 * default deployment target to "Deploy to Process Run-time" if the parent
 * process is business process or "Deploy to Pageflow Run-time" if the parent
 * process is pageflow or its sub type .
 * 
 * @author bharge
 * @since 16 Feb 2015
 */
public class RefactorProcessObjectsIntoServiceProcessAction extends
        AbstractRefactorActivitiesAsProcessAction {

    /**
     * @see com.tibco.xpd.processwidget.popup.actions.AbstractRefactorActivitiesAsProcessAction#getRefactorAsProcessCommand(com.tibco.xpd.processwidget.impl.ProcessWidgetImpl,
     *      java.util.List)
     * 
     * @param widget
     * @param objs
     * @return
     */
    @Override
    protected CreateAccessibleObjectCommand getRefactorAsProcessCommand(
            ProcessWidgetImpl widget, List<Object> objs) {

        CreateAccessibleObjectCommand refactorCmd =
                getProcessAdapter()
                        .getRefactorAsIndependentServiceProcessCommand(getEditingDomain(),
                                objs,
                                widget);

        return refactorCmd;
    }

}
