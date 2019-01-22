/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.actions;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.implementer.resources.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.xpdExtension.XpdModelType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * GenerateServiceProcessAction - Implementation class to generate service
 * process from a start type none in a business process.
 * 
 * @author bharge
 * @since 24 Feb 2015
 */
public class GenerateServiceProcessOnStartTypeNoneAction extends
        AbstractActivityProcessGeneratorAction {

    private final String DEFAULT_DISPLAY_FRAGMENT_ID =
            "invoke_start_type_none_biz_proc_fragment_id";//$NON-NLS-1$

    private final String ROOT_CATEGORY_ID =
            "com.tibco.xpd.processeditor.fragments.serviceprocess"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.actions.AbstractActivityProcessGeneratorAction#selectionChanged(org.eclipse.jface.action.IAction,
     *      org.eclipse.jface.viewers.ISelection)
     * 
     * @param action
     * @param selection
     */
    @Override
    public void selectionChanged(IAction action, ISelection selection) {

        if (selection instanceof StructuredSelection) {

            StructuredSelection structured = (StructuredSelection) selection;
            if (structured.size() == 1) {

                Object item = structured.getFirstElement();
                if (item instanceof IAdaptable) {

                    Activity activity =
                            (Activity) ((IAdaptable) item)
                                    .getAdapter(Activity.class);
                    if (activity != null) {

                        if (EventFlowType.FLOW_START_LITERAL
                                .equals(EventObjectUtil.getFlowType(activity))
                                && EventTriggerType.EVENT_NONE_LITERAL
                                        .equals(EventObjectUtil
                                                .getEventTriggerType(activity))) {

                            setGeneratorActivity(activity);
                            action.setEnabled(true);
                        }
                    }
                }
            }
        }
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.actions.AbstractActivityProcessGeneratorAction#generateNewProcess(com.tibco.xpd.xpdl2.Process,
     *      org.eclipse.emf.common.command.CompoundCommand)
     * 
     * @param newProcess
     * @param createProcessCommand
     * @param xpdl2WC
     */
    @Override
    protected Process generateNewProcess(CompoundCommand createProcessCommand,
            Xpdl2WorkingCopyImpl xpdl2WC) {

        Activity generatorActivity = getGeneratorActivity();
        ServiceProcessOnStartTypeNoneCreator serviceProcCreator =
                new ServiceProcessOnStartTypeNoneCreator(ROOT_CATEGORY_ID,
                        DEFAULT_DISPLAY_FRAGMENT_ID,
                        ProcessWidgetType.SERVICE_PROCESS, generatorActivity);

        Package xpdlPackage = Xpdl2ModelUtil.getPackage(generatorActivity);

        String name = Xpdl2ModelUtil.getDisplayNameOrName(generatorActivity);

        String procName =
                Xpdl2ModelUtil.getDisplayNameOrName(generatorActivity.getProcess());
        if (procName == null || procName.length() == 0) {

            procName = Xpdl2ResourcesConsts.DEFAULT_PROCESS_NAME;
        }

        name = procName + "-" + name; //$NON-NLS-1$

        Process newProcess =
                GenerateProcessUtil.createProcess(XpdModelType.SERVICE_PROCESS);
        Command command =
                serviceProcCreator.createProcess(xpdl2WC,
                        xpdlPackage,
                        newProcess,
                        name,
                        ""); //$NON-NLS-1$
        createProcessCommand.append(command);
        return newProcess;
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.actions.AbstractActivityProcessGeneratorAction#getCommandLabel()
     * 
     * @return
     */
    @Override
    protected String getCommandLabel() {

        return Messages.GenerateServiceProcessFromStartTypeNone_GenerateAction_command_title;
    }

}
