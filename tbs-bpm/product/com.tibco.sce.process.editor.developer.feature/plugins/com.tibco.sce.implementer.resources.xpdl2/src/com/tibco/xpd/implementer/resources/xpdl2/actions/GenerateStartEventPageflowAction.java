/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.actions;

import java.util.Collection;
import java.util.HashMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.Xpdl2ProcessorUtil;
import com.tibco.xpd.fragments.FragmentsActivator;
import com.tibco.xpd.fragments.IFragment;
import com.tibco.xpd.fragments.IFragmentCategory;
import com.tibco.xpd.fragments.IFragmentElement;
import com.tibco.xpd.implementer.resources.xpdl2.Activator;
import com.tibco.xpd.implementer.resources.xpdl2.internal.Messages;
import com.tibco.xpd.implementer.resources.xpdl2.utils.ProcessDeveloperUtil;
import com.tibco.xpd.implementer.script.Xpdl2WsdlUtil;
import com.tibco.xpd.navigator.packageexplorer.editors.EditorInputFactory;
import com.tibco.xpd.processeditor.xpdl2.actions.CloseOpenProcessEditorCommand;
import com.tibco.xpd.processeditor.xpdl2.preCommit.UpdateSendTaskMappings;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.ResetDefaultActivityColourCommand;
import com.tibco.xpd.processeditor.xpdl2.wizards.AddExtrasToNewProcessCommand;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.ui.wizards.newproject.BasicNewXpdResourceWizard;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdExtension.XpdModelType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.commands.AbstractLateExecuteCommand;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * GenerateStartEventPageflowAction - to generate a business service pageflow
 * process on a business process having request activity (i.e., message start
 * event, receive task, catch message intermediate event including event
 * handler)
 * 
 * 
 * @author bharge
 * @since 3.3 (16 Mar 2010)
 * @deprecated Not in Use can be deleted.
 */
@Deprecated
public class GenerateStartEventPageflowAction implements IObjectActionDelegate {

    private Activity requestActivity;

    public static final String DEFAULT_DISPLAY_FRAGMENT_ID =
            "_invoke_business_process_fragment_id";//$NON-NLS-1$

    HashMap<String, String> destinationEnvs = null;

    private static final Logger LOG = XpdResourcesPlugin.getDefault()
            .getLogger();

    private Object templateElements;

    private Process pageflowProcess;

    private EObject input;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.
     * action.IAction, org.eclipse.ui.IWorkbenchPart)
     */
    @Override
    public void setActivePart(IAction action, IWorkbenchPart targetPart) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action
     * .IAction, org.eclipse.jface.viewers.ISelection)
     */
    @Override
    public void selectionChanged(IAction action, ISelection selection) {
        requestActivity = null;

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
                            requestActivity = activity;
                            input =
                                    GenerateProcessUtil
                                            .createTemplate(XpdModelType.PAGE_FLOW);
                        }
                    }
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     */
    @Override
    public void run(IAction action) {

        if (null != requestActivity) {

            try {

                WorkingCopy wc =
                        WorkingCopyUtil.getWorkingCopyFor(requestActivity);

                if (Xpdl2ModelUtil.isGeneratedRequestActivity(requestActivity)) {

                    if (wc.isWorkingCopyDirty()) {
                        MessageDialog
                                .openInformation(PlatformUI.getWorkbench()
                                        .getActiveWorkbenchWindow().getShell(),
                                        Messages.GenerateStartEventPageflowAction_SaveProcessPriorBizServiceGenerationDialog_title,
                                        Messages.GenerateStartEventPageflowAction_SaveProcessPriorBizServiceGenerationDialog_desc);

                        return;
                        /*
                         * TODO Actually here we should display a yes/no dialog
                         * to save the process (i.e. The process must be saved
                         * (and build completed) before the business service is
                         * generated. Do you wish to save the process now?) and
                         * provide ProgressMonitor to track and wait for build
                         * to complete and then proceed with business service
                         * generation. i.e. Something like :
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
                     * Check if the wsdl file is generated. If not then abort
                     * the generation of business service with appropriate
                     * message dialog.
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

                EditingDomain editingDomain =
                        WorkingCopyUtil.getEditingDomain(requestActivity);

                CompoundCommand cmpdCmd =
                        new CompoundCommand(
                                Messages.GenerateStartEventPageflowAction_GenerateAction);

                /** set process elements from the fragment */
                setProcessElements(wc);

                if (input instanceof Process) {
                    pageflowProcess = (Process) input;
                    String name =
                            Xpdl2ModelUtil
                                    .getDisplayNameOrName(requestActivity);

                    String procName =
                            Xpdl2ModelUtil.getDisplayNameOrName(requestActivity
                                    .getProcess());
                    if (procName == null || procName.length() == 0) {
                        procName = Xpdl2ResourcesConsts.DEFAULT_PROCESS_NAME;
                    }

                    name = procName + "-" + name; //$NON-NLS-1$

                    Xpdl2ModelUtil.setOtherAttribute(pageflowProcess,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName(),
                            name);
                    pageflowProcess.setName(NameUtil.getInternalName(name,
                            false));
                }

                /** create a default command to create pool and lane */
                Command createDefaultProcessCmd =
                        GenerateProcessUtil.createCommand(input, wc);

                NewStartEventPageflowProcess generatePageflow =
                        new NewStartEventPageflowProcess(requestActivity);

                /**
                 * add the template elements to the process and add the process
                 * to the model
                 */

                cmpdCmd.append(generatePageflow
                        .createFinalAddProcessCommand(editingDomain,
                                wc,
                                pageflowProcess,
                                createDefaultProcessCmd));

                /*
                 * Creates Associated Parameters for all the user tasks in the
                 * given process
                 */
                AssociateParametersToUserTaskCommand assocParamsToUserTaskCmd =
                        new AssociateParametersToUserTaskCommand(editingDomain,
                                pageflowProcess);
                cmpdCmd.append(assocParamsToUserTaskCmd);

                /**
                 * add the wsdl information and data mappings for the send task
                 * present in the newly generated process
                 */

                AbstractLateExecuteCommand addWsdlAndMappingsCmd =
                        new AddWsdlInfoAndSendTaskMappingsCommand(
                                editingDomain, pageflowProcess);

                cmpdCmd.append(addWsdlAndMappingsCmd);

                /**
                 * Convert any activity that has default colour for pageflow
                 * process to use default colour for business service.
                 */

                AbstractLateExecuteCommand addColorsCmd =
                        new AddColorsCommand(editingDomain, pageflowProcess);
                cmpdCmd.append(addColorsCmd);

                if (null != cmpdCmd && cmpdCmd.canExecute()) {
                    editingDomain.getCommandStack().execute(cmpdCmd);
                }

                if (null != wc
                        && wc.getAttributes()
                                .containsKey(AddExtrasToNewProcessCommand.TEMPLATEDATA)) {
                    wc.getAttributes()
                            .remove(AddExtrasToNewProcessCommand.TEMPLATEDATA);
                }
                /**
                 * select the process in the explorer and show it in the diagram
                 * editor
                 */
                selectAndReveal(pageflowProcess);
            } catch (CoreException e) {
                /*
                 * If Business Service cannot be generated show an error dialog
                 * and throw Core Exception.
                 */
                MessageDialog
                        .openError(PlatformUI.getWorkbench()
                                .getActiveWorkbenchWindow().getShell(),
                                Messages.JavaScriptConceptUtil_CannotGenerateBusinessServiceErrorDialog_title,
                                e.getMessage());
                Activator
                        .getDefault()
                        .getLogger()
                        .error(e,
                                Messages.JavaScriptConceptUtil_CannotGenerateBusinessServiceErrorDialog_title);
            }
        }
    }

    protected void selectAndReveal(Object objectToSelect) {

        IConfigurationElement facConfig =
                XpdResourcesUIActivator
                        .getEditorFactoryConfigFor(pageflowProcess);

        if (facConfig != null) {
            String editorID = facConfig.getAttribute("editorID"); //$NON-NLS-1$
            IWorkbenchPage page =
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                            .getActivePage();
            try {
                EditorInputFactory f =
                        (EditorInputFactory) facConfig
                                .createExecutableExtension("factory"); //$NON-NLS-1$
                IEditorInput input = f.getEditorInputFor(pageflowProcess);
                page.openEditor(input, editorID);

                /** Select the new process in the Project Explorer */
                if (pageflowProcess != null) {
                    IWorkbench workbench = PlatformUI.getWorkbench();

                    if (workbench != null) {
                        BasicNewXpdResourceWizard
                                .selectAndReveal(objectToSelect,
                                        workbench.getActiveWorkbenchWindow());
                    }
                }

            } catch (PartInitException e) {
                LOG.error(e.getMessage());
            } catch (CoreException e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * @param wc
     */
    private void setProcessElements(WorkingCopy wc) {
        /**
         * 1. query for the invoke business process fragment 2. get the process
         * elements from the fragment 3. set that collection as template data
         */

        Object pageflowRootFragment = getInitialSelection();

        if (pageflowRootFragment == null) {
            pageflowRootFragment = Messages.StartEventPageflowFragment_NotFound;
        }

        if (pageflowRootFragment instanceof IFragment) {
            IFragment fragment = (IFragment) pageflowRootFragment;

            templateElements =
                    Xpdl2ProcessorUtil.getFragmentDropObjects(fragment
                            .getLocalizedData());
        }

        if (wc != null) {
            wc.getAttributes().put(AddExtrasToNewProcessCommand.TEMPLATEDATA,
                    templateElements);
        }
    }

    public Object getInitialSelection() {
        IFragmentCategory rootCategory = null;

        if (null != requestActivity) {
            /**
             * XPD-495: not to have a separate folder for invoke business
             * process but include it in the basic fragments
             */

            // rootCategory =
            // getRequiredPageflowCategory(getPageflowRootCategory());
            rootCategory = getPageflowRootCategory();
        }

        Object defaultFragment = getDefaultFragment(rootCategory);
        return defaultFragment;
    }

    // private IFragmentCategory getRequiredPageflowCategory(
    // IFragmentCategory pfRootCateg) {
    // if (null != startEventActivity) {
    // // return only "Invoke Business Process" Category
    // IFragmentCategory category =
    // findCategory(pfRootCateg, INVOKE_BUSINESS_PROCESS);
    // return category;
    // }
    // return pfRootCateg;
    // }

    /**
     * @param pfRootCateg
     * @param fragElementName
     * @return
     */
    // private IFragmentCategory findCategory(IFragmentCategory pfRootCateg,
    // String fragElementName) {
    // if (null != pfRootCateg) {
    // for (IFragmentElement fragElement : pfRootCateg.getChildren()) {
    // if (fragElementName.equals(fragElement.getName())
    // && fragElement instanceof IFragmentCategory) {
    // return (IFragmentCategory) fragElement;
    // }
    // }
    // }
    // return null;
    // }
    /**
     * @param rootCategory
     * @return
     */
    private Object getDefaultFragment(IFragmentCategory category) {
        if (category != null) {
            for (IFragmentElement fragElement : category.getChildren()) {
                if (fragElement instanceof IFragmentCategory) {
                    Object defaultFragment =
                            getDefaultFragment((IFragmentCategory) fragElement);
                    if (defaultFragment != null) {
                        return defaultFragment;
                    }
                } else {
                    Package fragmentPackage =
                            Xpdl2ProcessorUtil
                                    .getFragmentPackage((IFragment) fragElement);

                    if (DEFAULT_DISPLAY_FRAGMENT_ID.equals(fragmentPackage
                            .getId())) {
                        return fragElement;
                    }

                }
            }
        }
        return null;
    }

    private IFragmentCategory getPageflowRootCategory() {
        IFragmentCategory rootCategory = null;
        try {
            rootCategory =
                    FragmentsActivator
                            .getDefault()
                            .getRootCategory("com.tibco.xpd.processeditor.fragments.businessservice"); //$NON-NLS-1$
        } catch (CoreException e) {
            LOG.error(e);
        }
        return rootCategory;
    }

    /**
     * Goes thru all the user tasks in the given process and creates associated
     * parameters for them
     * 
     * 
     * @author bharge
     * @since 12 Aug 2014
     */
    private class AssociateParametersToUserTaskCommand extends
            AbstractLateExecuteCommand {

        Process process;

        /**
         * @param editingDomain
         * @param contextObject
         */
        public AssociateParametersToUserTaskCommand(
                EditingDomain editingDomain, Object contextObject) {

            super(editingDomain, contextObject);
            if (contextObject instanceof Process) {

                this.process = (Process) contextObject;
            }
        }

        /**
         * @see com.tibco.xpd.xpdl2.commands.LateExecuteCompoundCommand#execute()
         * 
         */
        @Override
        public Command createCommand(EditingDomain editingDomain,
                Object contextObject) {

            if (null != process) {

                CompoundCommand cmd = new CompoundCommand();

                Collection<Activity> allActivitiesInProc =
                        Xpdl2ModelUtil.getAllActivitiesInProc(process);
                for (Activity activity : allActivitiesInProc) {

                    if (TaskType.USER_LITERAL.equals(TaskObjectUtil
                            .getTaskTypeStrict(activity))) {

                        AssociatedParameters associatedParameters =
                                XpdExtensionFactory.eINSTANCE
                                        .createAssociatedParameters();

                        for (ProcessRelevantData data : process.getDataFields()) {

                            /*
                             * case ref types are not allowed as associated
                             * parameters for user tasks in pageflow processes
                             */
                            boolean caseRefTypeFieldInPageflow = false;
                            if (Xpdl2ModelUtil.isPageflow(process)) {

                                if (data.getDataType() instanceof RecordType) {

                                    caseRefTypeFieldInPageflow = true;
                                }
                            }

                            if (!caseRefTypeFieldInPageflow) {

                                AssociatedParameter associatedParam =
                                        ProcessInterfaceUtil
                                                .createAssociatedParam(data);
                                associatedParam.setMandatory(true);

                                associatedParameters.getAssociatedParameter()
                                        .add(associatedParam);
                            }
                        }

                        if (!associatedParameters.getAssociatedParameter()
                                .isEmpty()) {

                            cmd.append(Xpdl2ModelUtil
                                    .getSetOtherElementCommand(editingDomain,
                                            activity,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_AssociatedParameters(),
                                            associatedParameters));
                        }
                    }
                }
                return cmd;
            }
            return null;
        }
    }

    /**
     * This class publishes the pageflow process as business service and adds
     * the wsdl information and data mappings for the send task present in the
     * newly generated business service
     * 
     * @author bharge
     * @since 24 Nov 2011
     */
    private final class AddWsdlInfoAndSendTaskMappingsCommand extends
            AbstractLateExecuteCommand {

        /**
         * @param editingDomain
         * @param contextObject
         */
        public AddWsdlInfoAndSendTaskMappingsCommand(
                EditingDomain editingDomain, Object contextObject) {
            super(editingDomain, contextObject);
        }

        /**
         * @see com.tibco.xpd.xpdl2.commands.AbstractLateExecuteCommand#createCommand(org.eclipse.emf.edit.domain.EditingDomain,
         *      java.lang.Object)
         * 
         * @param editingDomain
         * @param contextObject
         * @return
         */
        @Override
        protected Command createCommand(EditingDomain editingDomain,
                Object contextObject) {

            CompoundCommand cmpdCmd = new CompoundCommand();

            Process parentBizProcess = requestActivity.getProcess();
            Process pageflowProcess = (Process) contextObject;

            cmpdCmd.append(GenerateProcessUtil
                    .setPublishAsBusinessService(editingDomain, pageflowProcess));

            for (Activity activity : pageflowProcess.getActivities()) {

                if (isSendTask(activity)) {

                    ProcessDeveloperUtil.generateWsdlInfo(editingDomain,
                            cmpdCmd,
                            parentBizProcess,
                            activity,
                            requestActivity);

                    cmpdCmd.append(new UpdateSendTaskMappings(editingDomain,
                            activity));

                    /* Set send task name to "Invoke ProcessName ActivityName" */
                    String sendTaskName =
                            Xpdl2ModelUtil
                                    .getDisplayNameOrName(requestActivity);

                    cmpdCmd.append(Xpdl2ModelUtil
                            .getSetOtherAttributeCommand(editingDomain,
                                    activity,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_DisplayName(),
                                    String.format(Messages.GenerateStartEventPageflowAction_InvokeBusinessProcessTaskLabel_label,
                                            sendTaskName)));

                    if (null != cmpdCmd && cmpdCmd.canExecute()) {
                        return cmpdCmd;
                    }
                }
            }

            return null;
        }

        /**
         * @param activity
         * @return
         */
        private boolean isSendTask(Activity activity) {
            if (activity.getImplementation() instanceof Task) {
                Task task = (Task) activity.getImplementation();
                if (null != task.getTaskSend()) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * 
     * This class converts pageflow colours to business service colours
     * 
     * @author bharge
     * @since 24 Nov 2011
     */
    private class AddColorsCommand extends AbstractLateExecuteCommand {

        /**
         * @param editingDomain
         * @param contextObject
         */
        public AddColorsCommand(EditingDomain editingDomain,
                Object contextObject) {
            super(editingDomain, contextObject);
        }

        /**
         * @see com.tibco.xpd.xpdl2.commands.AbstractLateExecuteCommand#createCommand(org.eclipse.emf.edit.domain.EditingDomain,
         *      java.lang.Object)
         * 
         * @param editingDomain
         * @param contextObject
         * @return
         */
        @Override
        protected Command createCommand(EditingDomain editingDomain,
                Object contextObject) {

            /**
             * Convert any activity that has default colour for pageflow process
             * to use default colour for business service.
             */
            CompoundCommand colorCmd =
                    new CloseOpenProcessEditorCommand(pageflowProcess);
            ProcessWidgetColors businessServiceColours =
                    ProcessWidgetColors
                            .getInstance(ProcessWidgetType.BUSINESS_SERVICE);
            ProcessWidgetColors pageflowColours =
                    ProcessWidgetColors
                            .getInstance(ProcessWidgetType.PAGEFLOW_PROCESS);

            Collection<Activity> allActivitiesInProc =
                    Xpdl2ModelUtil.getAllActivitiesInProc(pageflowProcess);

            for (Activity activity : allActivitiesInProc) {
                colorCmd.append(new ResetDefaultActivityColourCommand(
                        editingDomain, activity, pageflowColours,
                        businessServiceColours));
            }

            return colorCmd;
        }

    }
}
