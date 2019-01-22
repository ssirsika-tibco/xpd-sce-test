/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.properties;

import org.eclipse.emf.common.command.Command;

import com.tibco.xpd.processeditor.xpdl2.preCommit.UpdateOneWaySendTaskMappings;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;

/**
 * Property section used web service details for the Service tasks, intermediate
 * throw message events, and end message events.
 * 
 * @author rsomayaj
 * @since 3.3 (2 Jul 2010)
 */
public class TaskSendSection extends TaskWebServiceSection {

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.WebServiceDetailsSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {
        boolean isReplyActivity =
                ReplyActivityUtil.isReplyActivity(getActivityInput());
        if (isReplyActivity) {
            generateWsdlButton.setVisible(false);
        } else {
            generateWsdlButton.setVisible(true);
        }

        super.doRefresh();
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.TaskWebServiceSection#getUpdateMappingsCommand()
     * 
     * @return
     */
    @Override
    protected Command getUpdateMappingsCommand() {
        return new UpdateOneWaySendTaskMappings(getEditingDomain(),
                getActivityInput());
    }
}
