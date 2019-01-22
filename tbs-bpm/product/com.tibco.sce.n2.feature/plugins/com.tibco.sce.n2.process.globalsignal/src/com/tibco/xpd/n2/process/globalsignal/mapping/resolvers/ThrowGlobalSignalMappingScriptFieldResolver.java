/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.mapping.resolvers;

import java.util.ArrayList;
import java.util.List;

import com.tibco.xpd.globalSignalDefinition.util.GlobalSignalUtil;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.utils.AbstractMappingJavaScriptProcessFieldResolver;
import com.tibco.xpd.n2.process.globalsignal.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Mapping and Script resolver for Throw Global Signal Events.
 * 
 * @author kthombar
 * @since Feb 17, 2015
 */
public class ThrowGlobalSignalMappingScriptFieldResolver extends
        AbstractMappingJavaScriptProcessFieldResolver {

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.AbstractMappingScriptProcessFieldResolver#getMappingInReferenceContextLabel(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected String getMappingInReferenceContextLabel(Activity activity) {

        return Messages.ThrowGlobalSignalMappingScriptFieldResolver_MappingReferenceContext_label;
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.AbstractMappingScriptProcessFieldResolver#getMappingOutReferenceContextLabel(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected String getMappingOutReferenceContextLabel(Activity activity) {

        return Messages.ThrowGlobalSignalMappingScriptFieldResolver_MappingReferenceContext_label;
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
        return EventTriggerType.EVENT_SIGNAL_THROW_LITERAL
                .equals(EventObjectUtil.getEventTriggerType(activity))
                && GlobalSignalUtil.isGlobalSignalEvent(activity);
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.AbstractMappingScriptProcessFieldResolver#getActivityScriptInformationList(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected List<ScriptInformation> getActivityScriptInformationList(
            Activity activity) {

        List<ScriptInformation> allMappedAndUnMappedScriptInfo =
                new ArrayList<ScriptInformation>();
        /*
         * Add all the unMapped Scripts.
         */
        allMappedAndUnMappedScriptInfo.addAll(ProcessScriptUtil
                .getScriptInformationTasksWithScriptType(activity,
                        getGrammarType(),
                        DirectionType.IN_LITERAL.getLiteral()));

        List<DataMapping> dataMappings =
                Xpdl2ModelUtil.getDataMappings(activity,
                        DirectionType.IN_LITERAL);

        for (DataMapping dataMapping : dataMappings) {

            ScriptInformation scriptInformationFromDataMapping =
                    ProcessScriptUtil
                            .getScriptInformationFromDataMapping(dataMapping);
            if (scriptInformationFromDataMapping != null) {
                /*
                 * Add all mapped Scripts.
                 */
                allMappedAndUnMappedScriptInfo
                        .add(scriptInformationFromDataMapping);
            }
        }
        return allMappedAndUnMappedScriptInfo;
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.AbstractMappingScriptProcessFieldResolver#getTaskName()
     * 
     * @return
     */
    @Override
    protected String getTaskName() {
        return ProcessScriptContextConstants.GLOBAL_THROW_SIGNAL_EVENTMAPPING;
    }
}
