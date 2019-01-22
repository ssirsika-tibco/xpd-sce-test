/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.highlighting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageDescriptor;

import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.extensions.AbstractStaticHighlighterContribution;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Highlights process API activities.
 * 
 * @author aallway
 * @since 8 Feb 2011
 */
public class ApiActivitiesHighlighterContribution extends
        AbstractStaticHighlighterContribution {

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractStaticHighlighterContribution#getActivatedTooltipLabel()
     * 
     * @return
     */
    @Override
    public String getActivatedTooltipLabel() {
        return Messages.ApiActivitiesHighlighterContribution_MsgApiActivitiesHighlight_tooltip;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractStaticHighlighterContribution#getHighlightedDiagramModelObjects(com.tibco.xpd.xpdl2.Process)
     * 
     * @param diagramProcess
     * @return
     */
    @Override
    public Collection<? extends EObject> getHighlightedDiagramModelObjects(
            Process diagramProcess) {
        /*
         * We only want to return end error events if there were some
         * request/reply activities.
         */
        boolean reqReplyFound = false;

        List<Activity> apiActs = new ArrayList<Activity>();

        for (Activity activity : Xpdl2ModelUtil
                .getAllActivitiesInProc(diagramProcess)) {
            if (ReplyActivityUtil.isIncomingRequestActivity(activity)) {
                apiActs.add(activity);
                reqReplyFound = true;

            } else if (ReplyActivityUtil.isReplyActivity(activity)) {
                apiActs.add(activity);
                reqReplyFound = true;

            } else if (activity.getEvent() instanceof EndEvent) {
                if (EventTriggerType.EVENT_ERROR_LITERAL.equals(EventObjectUtil
                        .getEventTriggerType(activity))) {
                    apiActs.add(activity);
                }
            }
        }

        /*
         * We only want to return end error events if there were some
         * request/reply activities.
         */
        if (reqReplyFound) {
            return apiActs;
        }

        return Collections.emptyList();
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractStaticHighlighterContribution#getMenuImageDescriptor()
     * 
     * @return
     */
    @Override
    public ImageDescriptor getMenuImageDescriptor() {
        return Xpdl2ProcessEditorPlugin
                .getImageDescriptor("icons/msgApiActsMenu.png"); //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractStaticHighlighterContribution#getMenuText()
     * 
     * @return
     */
    @Override
    public String getMenuText() {
        return Messages.ApiActivitiesHighlighterContribution_ApiMsgActivities_menu;
    }

}
