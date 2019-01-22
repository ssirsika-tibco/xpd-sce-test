/**
 * 
 */
package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor;

import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.ElementsFactory;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.adapters.DiagramModelImageProvider;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.FormImplementation;
import com.tibco.xpd.xpdExtension.FormImplementationType;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdExtension.XpdModelType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskUser;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.commands.AbstractLateExecuteCommand;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Refactor a set of activities into a pageflow process.
 * 
 * @author aallway
 * @since 3.4.2 (15 Sep 2010)
 */
@SuppressWarnings("unchecked")
public class RefactorAsPageflowProcessCommand extends
        AbstractRefactorActivitiesAsProcessCommand {

    private AssociatedParameters associatedParameters;

    public RefactorAsPageflowProcessCommand(EditingDomain editingDomain,
            List<EObject> objects, DiagramModelImageProvider imageProvider) {
        super(
                editingDomain,
                objects,
                new RefactorAsPageflowWizardInfo(
                        imageProvider,
                        Messages.RefactorAsPageflowProcessCommand_DefaultPageflowProcess_name));
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.
     * AbstractRefactorActivitiesAsProcessCommand#createNewProcess()
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
     * @see com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.AbstractRefactorActivitiesAsProcessCommand#getTargetSubProcessType()
     * 
     * @return
     */
    @Override
    protected ProcessWidgetType getTargetSubProcessType() {
        return ProcessWidgetType.PAGEFLOW_PROCESS;
    }

    @Override
    protected BaseRefactorAsSubProcWizard createRefactorWizard() {
        BaseRefactorAsSubProcWizard wizard =
                new RefactorAsPageflowProcessWizard(
                        (RefactorAsPageflowWizardInfo) getRefactorInfo(),
                        getEditingDomain());
        return wizard;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.
     * AbstractRefactorActivitiesAsProcessCommand
     * #configureProcessInvokeActivity(
     * org.eclipse.emf.common.command.CompoundCommand,
     * com.tibco.xpd.xpdl2.Activity)
     */
    @Override
    protected Dimension configureProcessInvokeActivity(CompoundCommand cmd,
            Activity newProcessInvokeActivity) {
        RefactorAsPageflowWizardInfo refactorInfo =
                (RefactorAsPageflowWizardInfo) getRefactorInfo();

        Task task =
                ElementsFactory
                        .createActivityTaskElement(TaskType.USER_LITERAL);
        TaskUser taskUser = task.getTaskUser();

        FormImplementation formImpl =
                XpdExtensionFactory.eINSTANCE.createFormImplementation();
        formImpl.setFormType(FormImplementationType.PAGEFLOW);

        IPath path =
                SpecialFolderUtil
                        .getSpecialFolderRelativePath(getParentProcess());
        URI uri = URI.createURI(path.toString());
        uri = uri.appendFragment(getNewProcess().getId());
        formImpl.setFormURI(uri.toString());

        Xpdl2ModelUtil.setOtherElement(taskUser, XpdExtensionPackage.eINSTANCE
                .getDocumentRoot_FormImplementation(), formImpl);

        cmd.append(SetCommand.create(getEditingDomain(),
                newProcessInvokeActivity,
                Xpdl2Package.eINSTANCE.getActivity_Implementation(),
                task));

        /*
         * Remove old associated parameters in preparation for adding new ones
         * for the formal parameters to be created in new pageflow.
         */
        Object oldAssocParams =
                Xpdl2ModelUtil.getOtherElement(newProcessInvokeActivity,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_AssociatedParameters());
        if (oldAssocParams != null) {
            cmd.append(Xpdl2ModelUtil
                    .getRemoveOtherElementCommand(getEditingDomain(),
                            newProcessInvokeActivity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_AssociatedParameters(),
                            oldAssocParams));
        }

        associatedParameters =
                XpdExtensionFactory.eINSTANCE.createAssociatedParameters();
        associatedParameters.getAssociatedParameter();

        cmd.append(Xpdl2ModelUtil.getSetOtherElementCommand(getEditingDomain(),
                newProcessInvokeActivity,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_AssociatedParameters(),
                associatedParameters));

        /*
         * Add activity performer (either the single one that was referenced by
         * all user tasks in selection or the one of many referenced selected by
         * the user in config dialog.
         */
        if (refactorInfo.pageflowTaskPerformer != null) {
            cmd.append(TaskObjectUtil
                    .getSetPerformersCommand(getEditingDomain(),
                            newProcessInvokeActivity,
                            new EObject[] { refactorInfo.pageflowTaskPerformer }));
        }

        /*
         * Setup default resource pattern settings.
         */
        TaskObjectUtil.appendSetResourcePatternsCommand(getEditingDomain(),
                newProcessInvokeActivity,
                TaskType.USER_LITERAL,
                cmd);

        /*
         * Add Adhoc configurations to the User Task Activity left back in the
         * main process.
         */
        if (singleSelAdHocTaskConfiguration != null) {

            cmd.append(Xpdl2ModelUtil
                    .getSetOtherElementCommand(getEditingDomain(),
                            newProcessInvokeActivity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_AdHocTaskConfiguration(),
                            singleSelAdHocTaskConfiguration));

        }

        return new Dimension(ProcessWidgetConstants.TASK_WIDTH_SIZE,
                ProcessWidgetConstants.TASK_HEIGHT_SIZE);
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.
     * AbstractRefactorActivitiesAsProcessCommand
     * #isOkToRemoveDataField(com.tibco.xpd.xpdl2.DataField)
     */
    @Override
    protected boolean isOkToRemoveDataField(DataField field) {
        if (super.isOkToRemoveDataField(field)) {
            /*
             * Do not allow move (removal from source process) of a data field
             * that has been selected as the performer for the new pageflow
             * process invocaiton task.
             */
            if (field != ((RefactorAsPageflowWizardInfo) getRefactorInfo()).pageflowTaskPerformer) {
                return true;
            }
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.
     * AbstractRefactorActivitiesAsProcessCommand
     * #configureProcessInvokeActivityForData
     * (org.eclipse.emf.common.command.CompoundCommand,
     * com.tibco.xpd.xpdl2.ProcessRelevantData,
     * com.tibco.xpd.xpdl2.FormalParameter, com.tibco.xpd.xpdl2.ModeType)
     */
    @Override
    protected void configureProcessInvokeActivityForData(CompoundCommand cmd,
            ProcessRelevantData oldFieldOrParam,
            FormalParameter newFormalParam, ModeType newParamMode) {

        AssociatedParameter associatedParameter =
                XpdExtensionFactory.eINSTANCE.createAssociatedParameter();

        associatedParameter.setFormalParam(oldFieldOrParam.getName());
        associatedParameter.setMode(newParamMode);
        associatedParameter.setMandatory(false);

        associatedParameters.getAssociatedParameter().add(associatedParameter);

        return;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.AbstractRefactorActivitiesAsProcessCommand#configureProcessInvokeActivityForNoData()
     * 
     */
    @Override
    protected void configureProcessInvokeActivityForNoData() {
        super.configureProcessInvokeActivityForNoData();

        if (associatedParameters != null) {
            associatedParameters.setDisableImplicitAssociation(true);
            associatedParameters.getAssociatedParameter().clear();
        }
        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.
     * AbstractRefactorActivitiesAsProcessCommand
     * #performFinalConfiguration(org.eclipse
     * .emf.common.command.CompoundCommand, org.eclipse.emf.ecore.EObject)
     */
    @Override
    protected void performFinalConfiguration(CompoundCommand cmd, EObject object) {
        super.performFinalConfiguration(cmd, object);

        if (object instanceof Activity) {
            Activity activity = (Activity) object;

            TaskType taskType = TaskObjectUtil.getTaskTypeStrict(activity);

            cmd.append(new ResetDefaultActivityColourCommand(
                    getEditingDomain(), activity, ProcessWidgetColors
                            .getInstance(ProcessWidgetType.BPMN_PROCESS),
                    ProcessWidgetColors
                            .getInstance(ProcessWidgetType.PAGEFLOW_PROCESS)));

            if (TaskType.USER_LITERAL.equals(taskType)
                    || TaskType.MANUAL_LITERAL.equals(taskType)) {
                cmd.append(new RemoveTaskPerformersCommand(getEditingDomain(),
                        activity));
            }

            /*
             * remove the Ad-Hoc Configurations from the original task.
             */
            if (singleSelAdHocTaskConfiguration != null) {

                Object adHocConfig =
                        Xpdl2ModelUtil
                                .getOtherElement(activity,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_AdHocTaskConfiguration());

                if (adHocConfig != null) {
                    cmd.append(Xpdl2ModelUtil
                            .getRemoveOtherElementCommand(getEditingDomain(),
                                    activity,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_AdHocTaskConfiguration(),
                                    adHocConfig));
                }
            }
        }

        return;
    }

    /**
     * Command to remove the performers from a user task or
     * 
     * @author aallway
     * @since 3.4.2 (17 Sep 2010)
     */
    private static class RemoveTaskPerformersCommand extends
            AbstractLateExecuteCommand {

        /**
         * @param editingDomain
         * @param contextObject
         */
        public RemoveTaskPerformersCommand(EditingDomain editingDomain,
                Object contextObject) {
            super(editingDomain, contextObject);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.xpdl2.commands.AbstractLateExecuteCommand#createCommand
         * (org.eclipse.emf.edit.domain.EditingDomain, java.lang.Object)
         */
        @Override
        protected Command createCommand(EditingDomain editingDomain,
                Object contextObject) {
            Activity activity = (Activity) contextObject;

            EObject[] activityPerformers =
                    TaskObjectUtil.getActivityPerformers(activity);
            if (activityPerformers != null && activityPerformers.length > 0) {
                return TaskObjectUtil.getSetPerformersCommand(editingDomain,
                        activity,
                        new EObject[0]);
            }
            return null;
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.
     * AbstractRefactorActivitiesAsProcessCommand
     * #addParticipants(org.eclipse.emf.common.command.CompoundCommand)
     */
    @Override
    protected void addParticipants(CompoundCommand cmd) {
        /*
         * We never move or duplidate participants into the pageflow process
         * because it shouldn't have any
         */
        return;
    }
}
