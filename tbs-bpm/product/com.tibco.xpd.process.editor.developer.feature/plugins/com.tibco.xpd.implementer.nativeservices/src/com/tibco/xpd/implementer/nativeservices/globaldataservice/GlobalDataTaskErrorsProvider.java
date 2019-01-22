/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.globaldataservice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableError;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorFolder;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.IBpmnCatchableErrorTreeItem;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.IBpmnCatchableErrorsContributor;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.DiagramDropObjectUtils;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.xpdExtension.CaseReferenceOperationsType;
import com.tibco.xpd.xpdExtension.ErrorThrowerType;
import com.tibco.xpd.xpdExtension.GlobalDataOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Error contributor for the Global Data Task implementation.
 * 
 * @author njpatel
 */
public class GlobalDataTaskErrorsProvider implements
        IBpmnCatchableErrorsContributor {

    public GlobalDataTaskErrorsProvider() {
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.errorEvents.IBpmnCatchableErrorsContributor#isApplicableErrorThrower(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param errorTask
     * @return
     */
    @Override
    public boolean isApplicableErrorThrower(Activity errorTask) {
        TaskType taskType = TaskObjectUtil.getTaskTypeStrict(errorTask);
        if (taskType == TaskType.SERVICE_LITERAL) {
            String extensionId =
                    TaskObjectUtil.getTaskImplementationExtensionId(errorTask);

            return TaskImplementationTypeDefinitions.GLOBAL_DATA
                    .equals(extensionId);
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.errorEvents.IBpmnCatchableErrorsContributor#getCatchableErrorTreeItems(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param errorTask
     * @return
     */
    @Override
    public Collection<IBpmnCatchableErrorTreeItem> getCatchableErrorTreeItems(
            Activity errorTask) {
        List<IBpmnCatchableErrorTreeItem> errors =
                new ArrayList<IBpmnCatchableErrorTreeItem>();

        Implementation impl = errorTask.getImplementation();
        if (impl instanceof Task) {
            GlobalDataOperation op =
                    (GlobalDataOperation) ((Task) impl).getTaskService()
                            .getOtherElement(XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_GlobalDataOperation()
                                    .getName());

            if (op != null) {
                boolean isUpdate = false;
                boolean isAddLink = false;
                boolean isRemoveLink = false;
                boolean isDelete = false;
                boolean isCreateCase = false;
                boolean isSingleInstanceCaseRefOperation = false;

                if (op.getCaseReferenceOperations() != null) {
                    CaseReferenceOperationsType refOperations =
                            op.getCaseReferenceOperations();
                    isUpdate = refOperations.getUpdate() != null;
                    isAddLink = refOperations.getAddLinkAssociations() != null;
                    isRemoveLink =
                            refOperations.getRemoveLinkAssociations() != null;
                    isDelete = refOperations.getDelete() != null;

                    /*
                     * Check if the Case Ref field passed to the operation is
                     * single instance or not (i.e. is array type or not)
                     */
                    ProcessRelevantData processRelevantData =
                            getProcessRelevantData(refOperations.getCaseRefField(),
                                    errorTask);
                    if (processRelevantData != null) {
                        /*
                         * Set isSingleInstanceCaseRefOperation = true if the
                         * Case Ref field is not an array
                         */
                        isSingleInstanceCaseRefOperation =
                                !processRelevantData.isIsArray();
                    }

                } else if (op.getCaseAccessOperations() != null) {
                    isCreateCase =
                            op.getCaseAccessOperations().getCreate() != null;
                }

                BpmnCatchableErrorFolder errorFolder =
                        new BpmnCatchableErrorFolder(
                                getActivityName(errorTask),
                                getActivityImage(errorTask));

                errors.add(errorFolder);

                /*
                 * User application error is always available.
                 */
                errorFolder.addChild(new BpmnCatchableError(errorTask,
                        ErrorThrowerType.PROCESS_ACTIVITY,
                        "UserApplicationError", this)); //$NON-NLS-1$
                /*
                 * Add Case Out Of Sync error
                 */
                if (isUpdate || isAddLink || isRemoveLink || isDelete) {
                    errorFolder.addChild(new BpmnCatchableError(errorTask,
                            ErrorThrowerType.PROCESS_ACTIVITY,
                            "CaseOutOfSyncError", this)); //$NON-NLS-1$
                }

                /*
                 * Add NonUniqueCaseIdentifierError
                 */
                if (isCreateCase) {
                    errorFolder.addChild(new BpmnCatchableError(errorTask,
                            ErrorThrowerType.PROCESS_ACTIVITY,
                            "NonUniqueCaseIdentifierError", this)); //$NON-NLS-1$
                }
                /*
                 * XPD-6151: Add 'UnsafeToDeleteCaseError' error if the user
                 * selects a Delete Case Ref operation with a single
                 * instance(non-array) Case Ref Data field.
                 * 
                 * XPD-6961 - UnsafeToDeleteCaseError is not available for
                 * pageflow/biz-service/case-service
                 */
                if (isDelete && isSingleInstanceCaseRefOperation
                        && !Xpdl2ModelUtil.isPageflow(errorTask.getProcess())) {
                    errorFolder.addChild(new BpmnCatchableError(errorTask,
                            ErrorThrowerType.PROCESS_ACTIVITY,
                            "UnsafeToDeleteCaseError", this)); //$NON-NLS-1$ 
                }
            }
        }
        return errors;
    }

    /**
     * Find and return the process data field with the given name within the
     * scope if this activity.
     * 
     * @param name
     * @param activity
     * @return the process data field with the given name within the scope if
     *         the passed activity.
     */
    private ProcessRelevantData getProcessRelevantData(String name,
            Activity activity) {
        if (name != null) {
            List<ProcessRelevantData> relevantData =
                    ProcessDataUtil.getProcessRelevantData(activity);
            for (ProcessRelevantData data : relevantData) {
                if (name.equals(data.getName())) {
                    return data;
                }
            }
        }
        return null;
    }

    private Image getActivityImage(Activity errorThrowerTaskOrEvent) {
        if (errorThrowerTaskOrEvent.getImplementation() instanceof Task) {
            return DiagramDropObjectUtils.getImageForTaskType(TaskObjectUtil
                    .getTaskType(errorThrowerTaskOrEvent));

        } else if (errorThrowerTaskOrEvent.getEvent() != null) {
            return DiagramDropObjectUtils.getImageForEventType(EventObjectUtil
                    .getFlowType(errorThrowerTaskOrEvent), EventObjectUtil
                    .getEventTriggerType(errorThrowerTaskOrEvent));
        }
        return null;
    }

    private String getActivityName(Activity errorThrowerTaskOrEvent) {
        String name = ProcessDataUtil.getActivityName(errorThrowerTaskOrEvent);
        if (name == null || name.length() == 0) {
            if (errorThrowerTaskOrEvent.getImplementation() instanceof Task) {
                name =
                        "<"     + TaskObjectUtil.getTaskType(errorThrowerTaskOrEvent) //$NON-NLS-1$
                                        .toString() + ">"; //$NON-NLS-1$

            } else if (errorThrowerTaskOrEvent.getEvent() != null) {
                name =
                        "<"     + EventObjectUtil.getFlowType(errorThrowerTaskOrEvent) //$NON-NLS-1$
                                        .toString() + ">"; //$NON-NLS-1$
            } else {
                name = "?"; //$NON-NLS-1$
            }
        }

        return name;
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.errorEvents.IBpmnCatchableErrorsContributor#getErrorImage(java.lang.Object,
     *      java.lang.String)
     * 
     * @param errorThrower
     * @param errorId
     * @return
     */
    @Override
    public Image getErrorImage(Object errorThrower, String errorId) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.errorEvents.IBpmnCatchableErrorsContributor#getErrorThrowerId(java.lang.Object)
     * 
     * @param errorThrower
     * @return
     */
    @Override
    public String getErrorThrowerId(Object errorThrower) {
        // Safe to assume that errorThrower is the one we returned which is
        // ALWAYS an activity.
        return ((Activity) errorThrower).getId();
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.errorEvents.IBpmnCatchableErrorsContributor#getErrorThrowerContainerId(java.lang.Object)
     * 
     * @param errorThrower
     * @return
     */
    @Override
    public String getErrorThrowerContainerId(Object errorThrower) {
        // Safe to assume that errorThrower is the one we returned which is
        // ALWAYS an activity.
        return ((Activity) errorThrower).getProcess().getId();
    }

}
