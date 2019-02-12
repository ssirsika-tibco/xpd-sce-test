/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.mapping.resolvers;

import java.util.Collections;
import java.util.List;

import com.tibco.xpd.globalSignalDefinition.util.GlobalSignalUtil;
import com.tibco.xpd.implementer.nativeservices.script.AbstractMappingJavaScriptProcessFieldResolver;
import com.tibco.xpd.n2.process.globalsignal.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Mapping resolver for Catch Global Signal Events.
 * 
 * @author kthombar
 * @since Feb 17, 2015
 */
public class CatchGlobalSignalMappingScriptFieldResolver
        extends AbstractMappingJavaScriptProcessFieldResolver {

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.AbstractMappingScriptProcessFieldResolver#getMappingInReferenceContextLabel(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected String getMappingInReferenceContextLabel(Activity activity) {

        return Messages.CatchGlobalSignalMappingScriptFieldResolver_CatchGlobalSignalReferenceContext_label;
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.AbstractMappingScriptProcessFieldResolver#getMappingOutReferenceContextLabel(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected String getMappingOutReferenceContextLabel(Activity activity) {

        return Messages.CatchGlobalSignalMappingScriptFieldResolver_CatchGlobalSignalReferenceContext_label;
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.AbstractMappingScriptProcessFieldResolver#isInterestingActivity(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected boolean isInterestingActivity(Activity activity) {
        /*
         * Is Global Throw Signal event.
         */
        return EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL
                .equals(EventObjectUtil.getEventTriggerType(activity))
                && GlobalSignalUtil.isGlobalSignalEvent(activity);
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.AbstractMappingScriptProcessFieldResolver#getActivityScriptInformationList(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    protected List<ScriptInformation> getActivityScriptInformationList(
            Activity activity) {
        /*
         * process data would not be used in scripts hence return empty list.
         */
        return Collections.EMPTY_LIST;
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.AbstractMappingScriptProcessFieldResolver#getTaskName()
     * 
     * @return
     */
    @Override
    protected String getTaskName() {
        return ProcessScriptContextConstants.GLOBAL_CATCH_SIGNAL_EVENTMAPPING;
    }
}
