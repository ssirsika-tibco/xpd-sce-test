/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor;

import java.util.List;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.adapters.DiagramModelImageProvider;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdExtension.XpdModelType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Command to refactor selected objects into a new pageflow process.
 * 
 * @author bharge
 * @since 15 Jul 2011
 */
public class RefactorAsIndependentPageflowSubProcCommand extends
        RefactorAsIndependentSubProcCommand {

    /**
     * @param editingDomain
     * @param objects
     * @param imageProvider
     */
    public RefactorAsIndependentPageflowSubProcCommand(
            EditingDomain editingDomain, List<EObject> objects,
            DiagramModelImageProvider imageProvider) {
        super(editingDomain, objects, imageProvider);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.RefactorAsIndependentSubProcCommand#createNewProcess()
     * 
     * @return
     */
    @Override
    protected Process createNewProcess() {
        Process newProcess = Xpdl2Factory.eINSTANCE.createProcess();

        Xpdl2ModelUtil.setOtherAttribute(newProcess,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_XpdModelType(),
                XpdModelType.PAGE_FLOW);

        return newProcess;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.RefactorAsIndependentSubProcCommand#getTargetSubProcessType()
     * 
     * @return
     */
    @Override
    protected ProcessWidgetType getTargetSubProcessType() {
        return ProcessWidgetType.PAGEFLOW_PROCESS;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.AbstractRefactorActivitiesAsProcessCommand#performFinalConfiguration(org.eclipse.emf.common.command.CompoundCommand,
     *      org.eclipse.emf.ecore.EObject)
     * 
     * @param cmd
     * @param object
     */
    @Override
    protected void performFinalConfiguration(CompoundCommand cmd, EObject object) {

        super.performFinalConfiguration(cmd, object);
        if (object instanceof Activity) {

            Activity activity = (Activity) object;
            cmd.append(new ResetDefaultActivityColourCommand(
                    getEditingDomain(), activity, ProcessWidgetColors
                            .getInstance(TaskObjectUtil
                                    .getProcessType(getParentProcess())),
                    ProcessWidgetColors
                            .getInstance(ProcessWidgetType.PAGEFLOW_PROCESS)));
        }
    }
}
