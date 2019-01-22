/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.implementer.resources.xpdl2.internal.Messages;
import com.tibco.xpd.implementer.resources.xpdl2.utils.ProcessDeveloperUtil;
import com.tibco.xpd.implementer.script.Xpdl2WsdlUtil;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.XpdModelType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * GenerateStartEventPageflowAction - to generate a business service on a
 * business process having request activity (i.e., message start event, receive
 * task, catch message intermediate event including event handler)
 * 
 * @author bharge
 * @since 25 Feb 2015
 */
public class GenerateBizServiceOnIncomingReqActivityAction extends
        AbstractActivityProcessGeneratorAction {

    private final String ROOT_CATEGORY_ID =
            "com.tibco.xpd.processeditor.fragments.businessservice"; //$NON-NLS-1$

    private static final String DEFAULT_DISPLAY_FRAGMENT_ID =
            "_invoke_business_process_fragment_id";//$NON-NLS-1$

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.actions.AbstractActivityProcessGeneratorAction#run(org.eclipse.jface.action.IAction)
     * 
     * @param action
     */
    @Override
    public void run(IAction action) {

        Activity requestActivity = getGeneratorActivity();
        WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(requestActivity);

        if (Xpdl2ModelUtil.isGeneratedRequestActivity(requestActivity)) {

            if (wc.isWorkingCopyDirty()) {
                MessageDialog
                        .openInformation(PlatformUI.getWorkbench()
                                .getActiveWorkbenchWindow().getShell(),
                                Messages.GenerateStartEventPageflowAction_SaveProcessPriorBizServiceGenerationDialog_title,
                                Messages.GenerateStartEventPageflowAction_SaveProcessPriorBizServiceGenerationDialog_desc);

                return;
                /*
                 * TODO Actually here we should display a yes/no dialog to save
                 * the process (i.e. The process must be saved (and build
                 * completed) before the business service is generated. Do you
                 * wish to save the process now?) and provide ProgressMonitor to
                 * track and wait for build to complete and then proceed with
                 * business service generation. i.e. Something like :
                 */
                // boolean openQuestion =
                // MessageDialog
                // .openQuestion(PlatformUI.getWorkbench()
                // .getActiveWorkbenchWindow()
                // .getShell(),
                // Messages.GenerateStartEventPageflowAction_SaveProcessPriorBizServiceGenerationDialog_title,
                // "The process must be saved (and build completed) before the business service is generated . Do you wish to save the process now?");
                // if (openQuestion) {
                // try {
                // wc.save();
                // } catch (IOException e) {
                // e.printStackTrace();
                // }
                // // TODO Progress Monitor to track build completion.
                // } else {
                // return;
                // }
            }

            /*
             * Check if the wsdl file is generated. If not then abort the
             * generation of business service with appropriate message dialog.
             */
            IFile wsdlFile = Xpdl2WsdlUtil.getWsdlFile(requestActivity);
            if (wsdlFile == null) {

                String errorMessage =
                        String.format(Messages.GenerateStartEventPageflowAction_WsdlFileNotAvailable_desc,
                                Xpdl2ModelUtil
                                        .getDisplayNameOrName(requestActivity));
                MessageDialog
                        .openError(PlatformUI.getWorkbench()
                                .getActiveWorkbenchWindow().getShell(),
                                Messages.JavaScriptConceptUtil_CannotGenerateBusinessServiceErrorDialog_title,
                                errorMessage);
                return;
            }
        }
        super.run(action);
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.actions.AbstractActivityProcessGeneratorAction#generateNewProcess(com.tibco.xpd.xpdl2.Process,
     *      org.eclipse.emf.common.command.CompoundCommand,
     *      com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl)
     * 
     * @param newProcess
     * @param createProcessCommand
     * @param xpdl2WC
     */
    @Override
    protected Process generateNewProcess(CompoundCommand createProcessCommand,
            Xpdl2WorkingCopyImpl xpdl2WC) {

        Activity generatorActivity = getGeneratorActivity();
        BizServiceOnIncomingRequestActivityCreator bizServiceCreator =
                new BizServiceOnIncomingRequestActivityCreator(
                        ROOT_CATEGORY_ID, DEFAULT_DISPLAY_FRAGMENT_ID,
                        ProcessWidgetType.PAGEFLOW_PROCESS, generatorActivity);
        Package xpdlPackage = Xpdl2ModelUtil.getPackage(generatorActivity);

        String name = Xpdl2ModelUtil.getDisplayNameOrName(generatorActivity);

        String procName =
                Xpdl2ModelUtil.getDisplayNameOrName(generatorActivity.getProcess());
        if (procName == null || procName.length() == 0) {

            procName = Xpdl2ResourcesConsts.DEFAULT_PROCESS_NAME;
        }

        name = procName + "-" + name; //$NON-NLS-1$

        Process newProcess =
                GenerateProcessUtil.createProcess(XpdModelType.PAGE_FLOW);
        Command command =
                bizServiceCreator.createProcess(xpdl2WC,
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

        return Messages.GenerateStartEventPageflowAction_GenerateAction;
    }

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

                        if (ProcessDeveloperUtil.isRequestActivity(activity)) {

                            setGeneratorActivity(activity);
                            action.setEnabled(true);
                        }
                    }
                }
            }
        }
    }

}
