/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.implementer.nativeservices.java.javaservice.utils;

import java.util.List;

import com.tibco.xpd.implementer.nativeservices.java.internal.Messages;
import com.tibco.xpd.implementer.script.ActivityMessageProvider;
import com.tibco.xpd.implementer.script.ActivityMessageProviderFactory;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author rsomayaj
 * 
 */
public class WebServiceMappingJavaScriptFieldResolver extends
        AbstractMappingJavaScriptProcessFieldResolver {

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.AbstractMappingScriptProcessFieldResolver#getActivityScriptInformationList(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected List<ScriptInformation> getActivityScriptInformationList(
            Activity activity) {
        return ProcessScriptUtil.getAllServiceScriptInformations(activity,
                getGrammarType());
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.AbstractMappingScriptProcessFieldResolver#getTaskName()
     * 
     * @return
     */
    @Override
    protected String getTaskName() {
        return ProcessJsConsts.WEBSERVICE_TASK;
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.AbstractMappingScriptProcessFieldResolver#isInterestingActivity(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected boolean isInterestingActivity(Activity activity) {
        if (isWebServiceImplementationType(activity)) {
            if (ProcessScriptUtil.isWebServiceActivityWithScriptType(activity,
                    ProcessJsConsts.JAVASCRIPT_GRAMMAR)) {
                return true;
            }
        }
        return false;
    }

    private boolean isWebServiceImplementationType(Activity activity) {
        ActivityMessageProvider messageProvider =
                ActivityMessageProviderFactory.INSTANCE
                        .getMessageProvider(activity);
        return messageProvider != null;

    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.AbstractMappingScriptProcessFieldResolver#getMappingInReferenceContextLabel(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected String getMappingInReferenceContextLabel(Activity activity) {
        if (Xpdl2ModelUtil.isProcessAPIActivity(activity)) {
            return Messages.mappingFieldResolver_OutputFromProcess_label;
        } else {
            return Messages.MappingFieldResolver_InputToService_label;
        }
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.AbstractMappingScriptProcessFieldResolver#getMappingOutReferenceContextLabel(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected String getMappingOutReferenceContextLabel(Activity activity) {
        if (Xpdl2ModelUtil.isProcessAPIActivity(activity)) {
            return Messages.MappingFieldResolver_InputToProcess_label;
        } else {
            return Messages.MappingFieldResolver_OutputFromService_label;
        }
    }
}
