/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.implementer.nativeservices.decisions.actions;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.implementer.nativeservices.decisions.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.DecisionTaskObjectUtil;
import com.tibco.xpd.processwidget.IActivityHyperlinkHandler;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.DecisionFlowUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Activity hyperlink handler for decision service tasks.
 * 
 * @author aallway
 * @since 23 Aug 2011
 */
public class DecisionServiceTaskHyperlinkHandler implements
        IActivityHyperlinkHandler {

    private Boolean isDecisionFeatureAvailable = null;

    public DecisionServiceTaskHyperlinkHandler() {
    }

    /**
     * @see com.tibco.xpd.processwidget.IActivityHyperlinkHandler#isApplicableActivity(java.lang.Object)
     * 
     * @param activityModelObject
     * @return
     */
    @Override
    public boolean isApplicableActivity(Object activityModelObject) {
        if (activityModelObject instanceof Activity) {
            Activity activity = (Activity) activityModelObject;

            if (DecisionTaskObjectUtil.isDecisionServiceTask(activity)) {

                /*
                 * Unfortunately decision table task in decision flow has the
                 * same task implementation id as decision servie task in bpm
                 * process - so make sure this isn't a decision tasble task in
                 * decision flow!
                 */
                if (!DecisionFlowUtil.isDecisionFlow(activity.getProcess())) {
                    if (isDecisionFeatureAvailable == null) {
                        isDecisionFeatureAvailable =
                                Xpdl2ResourcesPlugin
                                        .isDecisionsFeatureAvailable();
                    }

                    if (isDecisionFeatureAvailable) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.processwidget.IActivityHyperlinkHandler#getEnablementContextObject(java.lang.Object)
     * 
     * @param activityModelObject
     * @return
     */
    @Override
    public Object getEnablementContextObject(Object activityModelObject) {
        if (activityModelObject instanceof Activity) {
            EObject decFlow =
                    DecisionTaskObjectUtil
                            .getDecisionFlow((Activity) activityModelObject);
            if (decFlow != null) {
                return decFlow;
            }
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.processwidget.IActivityHyperlinkHandler#getHyperlinkTooltip(java.lang.Object,
     *      java.lang.Object)
     * 
     * @param activityModelObject
     * @param contextObject
     * @return
     */
    @Override
    public String getHyperlinkTooltip(Object activityModelObject,
            Object contextObject) {
        if (contextObject instanceof Process) {
            return String
                    .format(Messages.DecisionServiceTaskHyperlinkHandler_OpenDecisionFlowHyperlink_tooltip,
                            Xpdl2ModelUtil
                                    .getDisplayNameOrName((Process) contextObject));
        }
        return Messages.DecisionServiceTaskHyperlinkHandler_DecisionFlowNotSet_tooltip2;
    }

    /**
     * @see com.tibco.xpd.processwidget.IActivityHyperlinkHandler#doHyperLink(java.lang.Object,
     *      java.lang.Object)
     * 
     * @param activityModelObject
     * @param contextObject
     */
    @Override
    public void doHyperLink(Object activityModelObject, Object contextObject) {
        if (activityModelObject instanceof Activity
                && contextObject instanceof Process) {
            DecisionTaskObjectUtil
                    .openDecisionFlowEditor((Activity) activityModelObject);
        }
    }

}
