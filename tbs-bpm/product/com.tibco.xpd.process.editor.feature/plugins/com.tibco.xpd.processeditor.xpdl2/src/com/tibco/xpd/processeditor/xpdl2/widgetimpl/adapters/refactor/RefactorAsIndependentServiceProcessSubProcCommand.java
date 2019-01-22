/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor;

import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.adapters.DiagramModelImageProvider;
import com.tibco.xpd.xpdExtension.ServiceProcessConfiguration;
import com.tibco.xpd.xpdExtension.SubProcessStartStrategy;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdExtension.XpdModelType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Priority;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Command to refactor selected objects into a new service process.
 * 
 * @author bharge
 * @since 16 Feb 2015
 */
public class RefactorAsIndependentServiceProcessSubProcCommand extends
        RefactorAsIndependentSubProcCommand {

    /**
     * @param editingDomain
     * @param objects
     * @param imageProvider
     */
    public RefactorAsIndependentServiceProcessSubProcCommand(
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
                XpdModelType.SERVICE_PROCESS);

        /* Add Service Process Configuration on to the new process */
        ServiceProcessConfiguration serviceProcessConfiguration =
                XpdExtensionFactory.eINSTANCE
                        .createServiceProcessConfiguration();

        Process parentProcess2 = getParentProcess();
        /*
         * If the parent process is pageflow or its sub type, then set the
         * deploy target as pageflow run-time
         */
        if (Xpdl2ModelUtil.isPageflowOrSubType(parentProcess2)) {

            serviceProcessConfiguration.setDeployToPageflowRuntime(true);
        } else {
            /* default it to process run-time */
            serviceProcessConfiguration.setDeployToProcessRuntime(true);
        }

        Xpdl2ModelUtil.setOtherElement(newProcess,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_ServiceProcessConfiguration(),
                serviceProcessConfiguration);

        return newProcess;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.RefactorAsIndependentSubProcCommand#getTargetSubProcessType()
     * 
     * @return
     */
    @Override
    protected ProcessWidgetType getTargetSubProcessType() {

        return ProcessWidgetType.SERVICE_PROCESS;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.RefactorAsIndependentSubProcCommand#configureProcessInvokeActivity(org.eclipse.emf.common.command.CompoundCommand,
     *      com.tibco.xpd.xpdl2.Activity)
     * 
     * @param cmd
     * @param newProcessInvokeActivity
     * @return
     */
    @Override
    protected Dimension configureProcessInvokeActivity(CompoundCommand cmd,
            Activity newProcessInvokeActivity) {

        Dimension dimension =
                super.configureProcessInvokeActivity(cmd,
                        newProcessInvokeActivity);

        SubFlow indiSubProcRef = getNewIndiSubProcRef();

        Process parentProcess2 = getParentProcess();
        /*
         * Set the valid sub proc invoke combination based on the parent process
         */
        if (Xpdl2ModelUtil.isBusinessProcess(parentProcess2)) {
            /*
             * A business process can invoke a service process sub proc when it
             * is schedule start
             */
            /* Set to Schedule Start */
            cmd.append(Xpdl2ModelUtil
                    .getSetOtherAttributeCommand(getEditingDomain(),
                            indiSubProcRef,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_StartStrategy(),
                            SubProcessStartStrategy.SCHEDULE_START));
            /*
             * If set to Schedule Start then add default priority of "inherit
             * from parent (which is a priority element with no value.
             */
            Priority priority = Xpdl2Factory.eINSTANCE.createPriority();
            priority.setValue(""); //$NON-NLS-1$

            cmd.append(SetCommand.create(getEditingDomain(),
                    newProcessInvokeActivity,
                    Xpdl2Package.eINSTANCE.getActivity_Priority(),
                    priority));

        } else if (Xpdl2ModelUtil.isPageflowOrSubType(parentProcess2)
                || Xpdl2ModelUtil.isServiceProcess(parentProcess2)) {
            /*
             * A service process or a pageflow (and its sub types) can invoke a
             * service process sub proc when it is start immediately
             */
            /* Set to Start Immediately */
            cmd.append(Xpdl2ModelUtil
                    .getSetOtherAttributeCommand(getEditingDomain(),
                            indiSubProcRef,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_StartStrategy(),
                            SubProcessStartStrategy.START_IMMEDIATELY));
        }

        return dimension;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.RefactorAsIndependentSubProcCommand#performFinalConfiguration(org.eclipse.emf.common.command.CompoundCommand,
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
                            .getInstance(ProcessWidgetType.SERVICE_PROCESS)));
        }
    }

}
