/**
 * RefactorAsIndependentSubProcAction.java
 *
 * Action that refactors selected objects as an embedded sub-proc.
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processwidget.popup.actions;

import java.util.List;

import com.tibco.xpd.processwidget.adapters.CreateAccessibleObjectCommand;
import com.tibco.xpd.processwidget.impl.ProcessWidgetImpl;

/**
 * Action that refactors selected objects into independent sub-process. (guts
 * moved to {@link AbstractRefactorActivitiesAsProcessAction}
 * 
 * @author aallway
 * @since 3.4.2 (15 Sep 2010)
 */
public class RefactorAsPageflowProcAction extends
        AbstractRefactorActivitiesAsProcessAction {

    @Override
    protected CreateAccessibleObjectCommand getRefactorAsProcessCommand(
            ProcessWidgetImpl widget, List<Object> objs) {

        CreateAccessibleObjectCommand refactorCmd =
                getProcessAdapter()
                        .getRefactorAsPageflowProcCommand(getEditingDomain(),
                                objs,
                                widget);

        return refactorCmd;
    }

}
