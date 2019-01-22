/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.processwidget.popup.actions;

import java.util.List;

import com.tibco.xpd.processwidget.adapters.CreateAccessibleObjectCommand;
import com.tibco.xpd.processwidget.impl.ProcessWidgetImpl;

/**
 * 
 * 
 * @author bharge
 * @since 15 Jul 2011
 */
public class RefactorAsIndependentPageflowSubProcAction extends
        RefactorAsIndependentSubProcAction {

    /**
     * @see com.tibco.xpd.processwidget.popup.actions.RefactorAsIndependentSubProcAction#getRefactorAsProcessCommand(com.tibco.xpd.processwidget.impl.ProcessWidgetImpl,
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
                        .getRefactorAsIndependentPageflowSubProcCommand(getEditingDomain(),
                                objs,
                                widget);

        return refactorCmd;
    }
}
