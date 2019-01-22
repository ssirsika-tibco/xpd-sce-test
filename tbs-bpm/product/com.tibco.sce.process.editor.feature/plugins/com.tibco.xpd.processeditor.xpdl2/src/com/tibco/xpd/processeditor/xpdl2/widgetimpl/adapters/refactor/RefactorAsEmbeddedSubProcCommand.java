/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor;

import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.ElementsFactory;
import com.tibco.xpd.processwidget.WidgetRGB;
import com.tibco.xpd.processwidget.adapters.DiagramModelImageProvider;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Command to refactor to an Embedded Subprocess .
 * 
 * @author aprasad
 * @since Oct 15, 2014
 */
public class RefactorAsEmbeddedSubProcCommand extends
        AbstractRefactorAsSubProcCommand {

    /**
     * Constructor.
     * 
     * @param editingDomain
     * @param objects
     * @param imageProvider
     */
    public RefactorAsEmbeddedSubProcCommand(EditingDomain editingDomain,
            List<Object> objects, DiagramModelImageProvider imageProvider) {

        super(editingDomain, objects, imageProvider);
    }




    /**
     * @see com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.AbstractRefactorAsSubProcCommand#getCommandLabel()
     * 
     * @return Label for the Command.
     */
    @Override
    protected String getCommandLabel() {
        return Messages.RefactorAsEmbeddedSubProcCommand_RefactorAsEmbSubproc_menu;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.AbstractRefactorAsSubProcCommand#createSubProcTask(org.eclipse.draw2d.geometry.Rectangle,
     *      com.tibco.xpd.processwidget.WidgetRGB,
     *      com.tibco.xpd.processwidget.WidgetRGB)
     * 
     * @see com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.AbstractRefactorAsSubProcCommand#createSubProcTask(com.tibco.xpd.processwidget.WidgetRGB,
     *      com.tibco.xpd.processwidget.WidgetRGB)
     * 
     * @param fillColor
     * @param lineColor
     * @return New Embedded SubProcess Activity.
     */
    @Override
    protected Activity createSubProcTask(WidgetRGB fillColor,
            WidgetRGB lineColor) {

        Rectangle contentBounds = getContentBounds();
        return newSubProcActivity =
                ElementsFactory.createTask(contentBounds.getCenter(),
                        contentBounds.getSize(),
                        "", //$NON-NLS-1$
                        TaskType.EMBEDDED_SUBPROCESS_LITERAL,
                        fillColor.toString(),
                        lineColor.toString());
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.AbstractRefactorAsSubProcCommand#getUniqueSubProcName(com.tibco.xpd.xpdl2.Process)
     * 
     * @param process
     * @return Unique Subprocess Name.
     */
    @Override
    protected String getUniqueSubProcName(Process process) {

        return Xpdl2ModelUtil
                .getUniqueLabelInSet(getRefactorInfo().subprocName,
                Xpdl2ModelUtil.getAllActivitiesInProc(process));
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.AbstractRefactorAsSubProcCommand#getRefactorWizard()
     * 
     * @return Wizard for refactor to Embedded SubProcess.
     */
    @Override
    protected BaseRefactorAsSubProcWizard getRefactorWizard() {

        return new RefactorAsEmbeddedSubProcWizard(getRefactorInfo(),
                editingDomain);
    }

}
