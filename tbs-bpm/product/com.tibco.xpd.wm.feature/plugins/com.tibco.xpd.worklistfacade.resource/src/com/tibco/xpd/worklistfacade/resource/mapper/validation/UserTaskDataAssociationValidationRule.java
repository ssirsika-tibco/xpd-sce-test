/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.worklistfacade.resource.mapper.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.worklistfacade.resource.mapper.WorkListFacadeMapperUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * User task validation rule , check for the association of Process Data which
 * is mapped to Physical Work Item Attribute with the User Task. Raises an issue
 * on a User Task if a Process Data mapped to Physical Work Item Attribute is
 * not implicitly/explicitly associated with it .
 * 
 * @author aprasad
 * @since 06-Jan-2014
 */
public class UserTaskDataAssociationValidationRule extends
        ProcessValidationRule {

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validate(com.tibco.xpd.xpdl2.Process)
     * 
     * @param process
     */
    @Override
    public void validate(Process process) {
        // All Process Relevant Data which is mapped to a Physical Work Item
        // Attribute
        Collection<ProcessRelevantData> allMappedProcessData =
                WorkListFacadeMapperUtil
                        .getAllProcessDataMappedToPhysicalAttribute(process);

        // All Activities in the Process
        Collection<Activity> activityList =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);

        for (Activity activity : activityList) {

            TaskType taskType = TaskObjectUtil.getTaskTypeStrict(activity);
            // ONLY interested in User Tasks
            if (TaskType.USER_LITERAL.equals(taskType)) {

                // raise issue for all mapped process data not associated with
                // the User Task.
                for (ProcessRelevantData procData : WorkListFacadeMapperUtil
                        .getAllUnAssociatedProcessDataForActivity(activity,
                                allMappedProcessData)) {

                    List<String> messages = new ArrayList<String>();
                    messages.add(procData.getName());
                    addIssue("user.task.missing.data.association", //$NON-NLS-1$
                            activity,
                            messages);

                }

            }

        }
    }

}