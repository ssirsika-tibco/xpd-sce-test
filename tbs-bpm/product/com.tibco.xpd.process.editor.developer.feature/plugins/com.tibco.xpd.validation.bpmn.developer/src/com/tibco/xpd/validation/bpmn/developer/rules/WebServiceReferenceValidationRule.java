/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.rules;

import javax.wsdl.Operation;

import org.eclipse.core.resources.IFile;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.implementer.script.ActivityMessageProvider;
import com.tibco.xpd.implementer.script.ActivityMessageProviderFactory;
import com.tibco.xpd.implementer.script.Xpdl2WsdlUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule;
import com.tibco.xpd.wsdlgen.wsdl.rules.CheckGeneratedWsdlReferencesRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * 
 * @author aallway
 * @since 3.3 (20 Jan 2010)
 */
public class WebServiceReferenceValidationRule extends
        FlowContainerValidationRule {

    public static final String ISSUE_WEBSERVICE_NOT_GENERATED =
            "bx.webServiceNotGenerated"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule#validate
     * (com.tibco.xpd.xpdl2.FlowContainer)
     */
    @Override
    public void validate(FlowContainer container) {
        /*
         * Don’t validate this issue for Pageflow / Business-service, because
         * the types of activity it relates to (incoming message requests) are
         * not supported in pageflows anyway. So adding issues will only get in
         * the way of the “not supported activity type” issues that are more
         * relevant.
         */
        Process process = null;
        if (container instanceof ActivitySet) {
            process = ((ActivitySet) container).getProcess();
        } else if (container instanceof Process) {
            process = (Process) container;
        }
        if (Xpdl2ModelUtil.isPageflow(process)
                || ProcessInterfaceUtil.isPageflowEngineServiceProcess(process)) {
            return;
        }
        IFile xpdlFile = WorkingCopyUtil.getFile(container);
        if (xpdlFile != null && xpdlFile.exists()) {

            for (Activity activity : container.getActivities()) {
                /*
                 * If activity has a message provider then it's web-service
                 * related.
                 * 
                 * (Also check that it's not a message provider for an activity
                 * that only has operation by reference (such as throw/catch
                 * wsdl error).
                 */
                ActivityMessageProvider messageProvider =
                        ActivityMessageProviderFactory.INSTANCE
                                .getMessageProvider(activity);
                if (messageProvider != null
                        && messageProvider.isActualWebServiceActivity()) {

                    /*
                     * BPMN checks that web-service is set / accessible - so we
                     * just need to check that the operation for generating
                     * activities exists.
                     */
                    if (isGeneratedRequestOrReply(activity)) {

                        /*
                         * XPD-914 - Don't bother checking if WSDLGEN is already
                         * complaining that the ExternalReference location WSDL
                         * file is not correct for this xpdl file
                         * (filerename/copy can do this).
                         * 
                         * So we either get "generated.wsdl file name out of
                         * synch OR wsdl not generated (not both!)
                         */
                        /*
                         * XPD-7845: We weren't considering the fact that the
                         * activity may implementing process interfaces message
                         * methods hence use 'ProcessInterfaceUtil
                         * .getDerivedWsdlFile(activity)' which will give us the
                         * correct wsdl file.
                         */
                        IFile derivedWsdlFile =
                                ProcessInterfaceUtil
                                        .getDerivedWsdlFile(activity);

                        if (!CheckGeneratedWsdlReferencesRule
                                .checkBadGeneratedWsdlRef(activity,
                                        derivedWsdlFile.getName())) {
                            if (!isWebServiceWsdlAvailable(activity,
                                    messageProvider)) {
                                addIssue(ISSUE_WEBSERVICE_NOT_GENERATED,
                                        activity);
                            }
                        }
                    }
                }
            }
        }

        return;
    }

    /**
     * @param activity
     * @param messageProvider
     * 
     * @return true if the WSDL operation is accessible.
     */
    private boolean isWebServiceWsdlAvailable(Activity activity,
            ActivityMessageProvider messageProvider) {
        Operation operation = Xpdl2WsdlUtil.getOperation(activity);
        if (operation != null) {
            return true;
        }
        return false;
    }

    /**
     * @param activity
     * @return true if the activity is a generated request or a reply to one.
     */
    private boolean isGeneratedRequestOrReply(Activity activity) {
        if (ReplyActivityUtil.isReplyActivity(activity)) {
            activity =
                    ReplyActivityUtil
                            .getRequestActivityForReplyActivity(activity);
        }

        if (activity != null) {
            return Xpdl2ModelUtil.isGeneratedRequestActivity(activity);
        }

        return false;
    }
}
