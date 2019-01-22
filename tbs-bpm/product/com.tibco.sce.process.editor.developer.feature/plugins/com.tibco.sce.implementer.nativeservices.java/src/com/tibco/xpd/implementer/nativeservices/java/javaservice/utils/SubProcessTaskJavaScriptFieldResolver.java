/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.java.javaservice.utils;

import java.util.List;

import com.tibco.xpd.implementer.nativeservices.java.internal.Messages;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;

/**
 * 
 * <p>
 * <i>Created: 05 March 2008</i>
 * </p>
 * 
 * @author Miguel Torres
 * 
 */
public class SubProcessTaskJavaScriptFieldResolver extends
        AbstractMappingJavaScriptProcessFieldResolver {

    @Override
    protected List<ScriptInformation> getActivityScriptInformationList(
            Activity activity) {
        return ProcessScriptUtil
                .getAllSubProcessTaskScriptInformations(activity,
                        getGrammarType());
    }

    @Override
    protected boolean isInterestingActivity(Activity activity) {
        return ProcessScriptUtil.isSubProcessTaskWithScriptType(activity,
                getGrammarType());
    }

    @Override
    protected String getTaskName() {
        return ProcessJsConsts.SUBPROCESS_TASK;
    }

    @Override
    protected String getGrammarType() {
        return ProcessJsConsts.JAVASCRIPT_GRAMMAR;
    }

    @Override
    protected String getDefaultDestination() {
        return ProcessJsConsts.JSCRIPT_DESTINATION;
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.AbstractMappingScriptProcessFieldResolver#getMappingInReferenceContextLabel(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected String getMappingInReferenceContextLabel(Activity activity) {
        return Messages.SubProcessTaskJavaScriptFieldResolver_InputToSubProcess_label;
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.AbstractMappingScriptProcessFieldResolver#getMappingOutReferenceContextLabel(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected String getMappingOutReferenceContextLabel(Activity activity) {
        return Messages.SubProcessTaskJavaScriptFieldResolver_OutputFromSubProcess_label;
    }

}
