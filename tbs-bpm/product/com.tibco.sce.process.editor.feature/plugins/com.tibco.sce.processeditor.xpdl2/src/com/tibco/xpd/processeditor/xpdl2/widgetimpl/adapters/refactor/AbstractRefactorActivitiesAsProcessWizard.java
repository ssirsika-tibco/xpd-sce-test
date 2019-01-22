/**
 * RefactorAsIndiSubProcWizard.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.RefactorAsIndiSubprocWizardInfo.TaskGroupReferenceInfo;
import com.tibco.xpd.processwidget.adapters.DiagramModelImageProvider;
import com.tibco.xpd.xpdExtension.RetainFamiliarActivities;
import com.tibco.xpd.xpdExtension.SeparationOfDutiesActivities;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Refactor a set of activities as a process wizard.
 * 
 * @author aallway
 * @since 3.4.2 (15 Sep 2010)
 */
public abstract class AbstractRefactorActivitiesAsProcessWizard extends
        BaseRefactorAsSubProcWizard {

    private RefactorAsIndiSubprocWizardInfo refactorInfo;

    /**
     * @param refactorInfo
     * @param editingDomain
     * @param wizardTitle
     * @param wizardDescription
     */
    public AbstractRefactorActivitiesAsProcessWizard(
            RefactorAsIndiSubprocWizardInfo refactorInfo,
            EditingDomain editingDomain, String title, String titleDesc) {

        super(refactorInfo, editingDomain, title, titleDesc);
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.
     * BaseRefactorAsSubProcWizard#init(java.lang.Object)
     */
    @Override
    public void init(Object inputObject) {
        super.init(inputObject);

        this.refactorInfo = (RefactorAsIndiSubprocWizardInfo) inputObject;

        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.
     * BaseRefactorAsSubProcWizard#getConfigurationItems(java.lang.Object)
     */
    @Override
    protected List<ProcessRefactorConfigurationItem> getConfigurationItems(
            Object inputObject) {
        //
        // Add the standard sub-proc configurations
        List<ProcessRefactorConfigurationItem> items =
                super.getConfigurationItems(inputObject);

        ImageRegistry ir =
                Xpdl2ProcessEditorPlugin.getDefault().getImageRegistry();

        //
        // If there are any data fields that are used SOLELY in the selected
        // objects then provide option to move OR duplicate in new sub-process.
        if (refactorInfo.referencedDataFields.size() > 0) {
            ProcessRefactorConfigurationItem dataFieldGroup = null;

            for (RefactorReferencedDataFieldInfo dataFieldInfo : refactorInfo.referencedDataFields) {

                if (!dataFieldInfo.referencedElseWhere) {
                    ReferencedDataFieldConfigItem dataFieldItem =
                            new ReferencedDataFieldConfigItem(dataFieldInfo, ir);

                    if (dataFieldGroup == null) {
                        dataFieldGroup =
                                new ProcessRefactorConfigurationItem(
                                        "MoveOrDuplicateDataFieldGroup", //$NON-NLS-1$
                                        Messages.RefactorAsIndiSubProcWizard_RefactorAsIndiSubprocMoveAllField_message,
                                        Messages.RefactorAsIndiSubProcWizard_RefactorAsIndiSubprocMoveAllField_longdesc,
                                        true,
                                        false,
                                        ir
                                                .get(ProcessEditorConstants.IMG_DATAFIELD));
                    }
                    dataFieldGroup.addChildItem(dataFieldItem);
                }
            }

            if (dataFieldGroup != null) {
                items.add(dataFieldGroup);
            }
        }

        /*
         * If there are broken separation of duty / retain familiar task groups
         * (some tasks in and some out of refactor selection) then user must
         * confirm that they wish to break the group.
         */
        if ((refactorInfo.validationInfo & RefactorAsSubProcWizardInfo.SUBPROC_INDI_BREAKS_SEP_OF_DUTY_GROUP) != 0
                || (refactorInfo.validationInfo & RefactorAsSubProcWizardInfo.SUBPROC_INDI_BREAKS_RETAIN_FAMILIAR_GROUP) != 0) {
            ProcessRefactorConfigurationItem brokenTaskGroups = null;

            for (TaskGroupReferenceInfo taskGroupInfo : refactorInfo.taskGroups) {
                if (taskGroupInfo.isReferenced) {
                    if (!taskGroupInfo.unselectedActivitiesInGroup.isEmpty()) {
                        /*
                         * A referenced task group has some tasks left outside
                         * of group so will be broken and have to be removed.
                         */
                        ReferencedTaskGroupConfigItem taskGroupItem =
                                new ReferencedTaskGroupConfigItem(
                                        taskGroupInfo, ir);

                        if (brokenTaskGroups == null) {
                            brokenTaskGroups =
                                    new ProcessRefactorConfigurationItem(
                                            "RemoveBrokenTaskGroups", //$NON-NLS-1$
                                            Messages.RefactorAsIndiSubProcWizard_RemoveAllBrokenTaskGroups_message,
                                            Messages.RefactorAsIndiSubProcWizard_RemoveAllBrokenTaskGroups_longdesc,
                                            false,
                                            true,
                                            ir
                                                    .get(ProcessEditorConstants.IMG_TASK_GROUP));
                        }
                        brokenTaskGroups.addChildItem(taskGroupItem);
                    }
                }
            }

            if (brokenTaskGroups != null) {
                items.add(brokenTaskGroups);
            }
        }

        return items;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.
     * BaseRefactorAsSubProcWizard#isConfigurationComplete(java.lang.Object,
     * java.util.List)
     */
    @Override
    protected boolean isConfigurationComplete(Object inputObject,
            List<ProcessRefactorConfigurationItem> configItems) {
        if (super.isConfigurationComplete(inputObject, configItems)) {
            for (TaskGroupReferenceInfo taskGroupInfo : refactorInfo.taskGroups) {
                if (taskGroupInfo.isReferenced) {
                    /*
                     * If all activities in group were included in refactor
                     * selection then isOkToRemove won't be set because there
                     * would have been no config item for it, so ignore any
                     * group that has all its activities selected.
                     */
                    if (!taskGroupInfo.unselectedActivitiesInGroup.isEmpty()
                            && !taskGroupInfo.isOkToRemove) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * ReferencedDataFieldConfigItem
     * 
     * Small class to join between RefactorReferencedDataFieldInfo and its
     * refactor configuration item.
     * 
     * Basically, creates the appropriate refactor config item and keeps the
     * 'Move / duplicate flag up to date.
     */
    private class ReferencedDataFieldConfigItem extends
            ProcessRefactorConfigurationItem {

        public ReferencedDataFieldConfigItem(
                RefactorReferencedDataFieldInfo dataFieldInfo, ImageRegistry ir) {
            super(
                    dataFieldInfo,
                    String
                            .format(Messages.RefactorAsIndiSubProcWizard_RefactorAsIndiSubProcMoveField_message,
                                    dataFieldInfo.dataFieldOrParam.getName()),
                    String
                            .format(Messages.RefactorAsIndiSubProcWizard_RefactorAsIndiSubProcMoveField_longdesc,
                                    dataFieldInfo.dataFieldOrParam.getName()),
                    true, false, ir.get(ProcessEditorConstants.IMG_DATAFIELD));

        }

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.
         * ProcessRefactorConfigurationItem#setChecked(boolean)
         */
        @Override
        public void setChecked(boolean isChecked) {
            // Use the 'move or duplicate flag in original partic info for check
            // status.
            RefactorReferencedDataFieldInfo dataFieldInfo =
                    (RefactorReferencedDataFieldInfo) getInputObject();
            dataFieldInfo.moveDataField = isChecked;

        }

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.
         * ProcessRefactorConfigurationItem#isChecked()
         */
        @Override
        public boolean isChecked() {
            // Use the 'move or duplicate flag in original partic info for check
            // status.
            RefactorReferencedDataFieldInfo dataFieldInfo =
                    (RefactorReferencedDataFieldInfo) getInputObject();
            return dataFieldInfo.moveDataField;
        }

    }

    /**
     * 
     * Small class to join between RefactorReferencedParticipantInfo and its
     * refactor configuration item.
     * 
     * Basically, creates the appropriate refactor config item and keeps the
     * 'Move / duplicate flag up to date.
     * 
     * @author aallway
     * @since 3.4.2 (7 Sep 2010)
     */
    private class ReferencedTaskGroupConfigItem extends
            ProcessRefactorConfigurationItem {

        public ReferencedTaskGroupConfigItem(
                TaskGroupReferenceInfo taskGroupInfo, ImageRegistry ir) {
            super(
                    taskGroupInfo,
                    (taskGroupInfo.taskGroup instanceof SeparationOfDutiesActivities) ? String
                            .format(Messages.RefactorAsIndiSubProcWizard_RemoveSepOfDutyGroup_message,
                                    ((SeparationOfDutiesActivities) taskGroupInfo.taskGroup)
                                            .getName())
                            : String
                                    .format(Messages.RefactorAsIndiSubProcWizard_RemoveRetainFamiliarGroup_message,
                                            ((RetainFamiliarActivities) taskGroupInfo.taskGroup)
                                                    .getName()),
                    (taskGroupInfo.taskGroup instanceof SeparationOfDutiesActivities) ? String
                            .format(Messages.RefactorAsIndiSubProcWizard_RemoveSepOfDutyGroup_longDesc,
                                    ((SeparationOfDutiesActivities) taskGroupInfo.taskGroup)
                                            .getName())
                            : String
                                    .format(Messages.RefactorAsIndiSubProcWizard_RemoveRetainFamiliarGroup_longdesc,
                                            ((RetainFamiliarActivities) taskGroupInfo.taskGroup)
                                                    .getName()), false, true,
                    ir.get(ProcessEditorConstants.IMG_TASK_GROUP));

        }

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.
         * ProcessRefactorConfigurationItem#setChecked(boolean)
         */
        @Override
        public void setChecked(boolean isChecked) {
            TaskGroupReferenceInfo info =
                    (TaskGroupReferenceInfo) getInputObject();
            info.isOkToRemove = isChecked;
            return;
        }

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.
         * ProcessRefactorConfigurationItem#isChecked()
         */
        @Override
        public boolean isChecked() {
            TaskGroupReferenceInfo info =
                    (TaskGroupReferenceInfo) getInputObject();
            return info.isOkToRemove;

        }

        @Override
        public Image getConfigItemPreviewImage(Dimension sizeHint) {
            TaskGroupReferenceInfo info =
                    (TaskGroupReferenceInfo) getInputObject();

            List<Activity> actsInGroup = new ArrayList<Activity>();

            for (String id : info.unselectedActivitiesInGroup) {
                Activity activity =
                        Xpdl2ModelUtil.getActivityById(info.parentProcess, id);
                if (activity != null) {
                    actsInGroup.add(activity);
                }
            }

            for (String id : info.selectedActivitiesInGroup) {
                Activity activity =
                        Xpdl2ModelUtil.getActivityById(info.parentProcess, id);
                if (activity != null) {
                    actsInGroup.add(activity);
                }
            }

            Image img =
                    refactorInfo.imageProvider
                            .getDiagramImageFromModel(actsInGroup,
                                    refactorInfo.selectedObjects,
                                    actsInGroup,
                                    sizeHint,
                                    IMG_MARGIN,
                                    DiagramModelImageProvider.PAINT_UNSELECTED_OBJECTS
                                            | DiagramModelImageProvider.HIGHLIGHT_CHILDREN
                                            | DiagramModelImageProvider.MAX_SCALE_1_TO_1);

            return img;
        }
    }

}
