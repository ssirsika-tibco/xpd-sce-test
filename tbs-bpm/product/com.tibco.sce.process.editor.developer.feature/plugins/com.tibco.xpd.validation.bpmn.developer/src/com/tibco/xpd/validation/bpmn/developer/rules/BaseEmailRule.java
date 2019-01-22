/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.rules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.implementer.nativeservices.NativeServicesConsts;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.AttachmentType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.DefinitionType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.FromType;
import com.tibco.xpd.implementer.nativeservices.emailservice.utilities.EmailExtUtil;
import com.tibco.xpd.validation.bpmn.developer.internal.Messages;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * base class to define rules for email task. this class would get contributed
 * from the respective ipe and bx destination plugins.
 * 
 * 
 * @author bharge
 * @since 3.3 (6 Aug 2010)
 */
public class BaseEmailRule extends ProcessValidationRule {

    private static final String REQUIRED_TO_VALUE = "bpmn.EMailMustHaveToValue"; //$NON-NLS-1$

    private static final String INVALID_VARIABLE_REFERENCE =
            "bpmn.EMailInvalidVariableReference"; //$NON-NLS-1$

    private static final String UNMATCHED_VARIABLE_CHARACTER =
            "bpmn.EMailUnmatchedVariableCharacter"; //$NON-NLS-1$

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        for (Iterator iter = activities.iterator(); iter.hasNext();) {
            Activity activity = (Activity) iter.next();

            if (activity.getImplementation() instanceof Task) {
                TaskService tsvc =
                        ((Task) activity.getImplementation()).getTaskService();

                if (tsvc != null) {
                    String implType =
                            (String) Xpdl2ModelUtil
                                    .getOtherAttribute(tsvc,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ImplementationType());

                    if (NativeServicesConsts.EMAIL_SERVICE_ID.equals(implType)) {
                        validateToValue(tsvc, activity);
                        validateVariableReferences(tsvc, activity);
                        validateExtension(tsvc, activity);
                    }
                }
            }
        }
    }

    /**
     * @param tsvc
     * @param activity
     */
    protected void validateExtension(TaskService tsvc, Activity activity) {
        // child class provide the implementation
    }

    protected void validateToValue(TaskService tsvc, Activity activity) {
        String to = null;
        EmailType email = EmailExtUtil.getEmailExtension(tsvc);

        if (email != null) {
            DefinitionType def = email.getDefinition();
            if (def != null) {
                to = def.getTo();
            }
        }

        if (to == null || to.length() == 0) {
            addIssue(REQUIRED_TO_VALUE, activity);
        }
    }

    protected void validateVariableReferences(TaskService tsvc,
            Activity activity) {
        EmailType email = EmailExtUtil.getEmailExtension(tsvc);
        if (email != null) {
            DefinitionType def = email.getDefinition();
            if (def != null) {
                FromType from = def.getFrom();
                if (from != null) {
                    validateVariableReferenceStr(from.getValue(),
                            Messages.EmailRule_From,
                            activity);
                }
                String to = def.getTo();
                if (to != null) {
                    validateVariableReferenceStr(to,
                            Messages.EmailRule_To,
                            activity);
                }
                String cc = def.getCc();
                if (cc != null) {
                    validateVariableReferenceStr(cc,
                            Messages.EmailRule_CC,
                            activity);
                }
                String bcc = def.getBcc();
                if (bcc != null) {
                    validateVariableReferenceStr(bcc,
                            Messages.EmailRule_Bcc,
                            activity);
                }
                String replyTo = def.getReplyTo();
                if (replyTo != null) {
                    validateVariableReferenceStr(replyTo,
                            Messages.EmailRule_ReplyTo,
                            activity);
                }
                String headers = def.getHeaders();
                if (headers != null) {
                    validateVariableReferenceStr(headers,
                            Messages.EmailRule_Headers,
                            activity);
                }
                String priority = def.getPriority();
                if (priority != null) {
                    validateVariableReferenceStr(priority,
                            Messages.EmailRule_Priority,
                            activity);
                }
                String subject = def.getSubject();
                if (subject != null) {
                    validateVariableReferenceStr(subject,
                            Messages.EmailRule_Subject,
                            activity);
                }
            }
            String body = email.getBody();
            if (body != null) {
                validateVariableReferenceStr(body,
                        Messages.EmailRule_Body,
                        activity);
            }
            AttachmentType attachment = email.getAttachment();
            if (attachment != null) {
                validateVariableReferenceStr(attachment.getValue(),
                        Messages.EmailRule_FieldContents,
                        activity);
            }
        }
    }

    protected void validateVariableReferenceStr(String value,
            String sectionName, Activity activity) {
        if (value != null && sectionName != null) {

            // SID XPD-213 BEGIN
            /*
             * Split into lines (don't allow split of variable name over lines).
             */
            StringTokenizer lines = new StringTokenizer(value, "\n", false); //$NON-NLS-1$
            while (lines.hasMoreTokens()) {
                String line = lines.nextToken();
                line = line.replaceAll("\r", ""); //$NON-NLS-1$ //$NON-NLS-2$

                /* Go thru line Look for % field reference chars. */
                while (true) {
                    int leadPercent = line.indexOf('%');
                    if (leadPercent >= 0) {
                        String remainder = line.substring(leadPercent + 1);

                        int tailPercent = remainder.indexOf('%');
                        if (tailPercent < 0) {
                            /* No tail % end of ref marker. */
                            addIssue(UNMATCHED_VARIABLE_CHARACTER,
                                    activity,
                                    Collections.singletonList(sectionName));

                            /* No point checking any more. */
                            break;

                        } else {
                            String fieldRef =
                                    remainder.substring(0, tailPercent);

                            if (fieldRef.length() > 0) {
                                ProcessRelevantData data =
                                        resolveVariableName(fieldRef, activity);
                                if (data == null) {
                                    List<String> messages =
                                            new ArrayList<String>();
                                    messages.add(fieldRef);
                                    messages.add(sectionName);
                                    addIssue(INVALID_VARIABLE_REFERENCE,
                                            activity,
                                            messages);
                                }

                            } else {
                                /* found % escaped with following % - ignore. */
                            }
                        }

                        /* Get remainder of line after field ref. */
                        line = remainder.substring(tailPercent + 1);

                    } else {
                        /* No more %'s on line. */
                        break;
                    }
                } /* Next part of current line (after field ref %xxx%) */

            } /* Next line. */
            // SID XPD-213 END

        }

        return;
    }

    protected ProcessRelevantData resolveVariableName(String variableName,
            Activity activity) {
        if (variableName != null && activity != null) {
            List<ProcessRelevantData> prdList =
                    ProcessDataUtil.getProcessRelevantData(activity);
            if (prdList != null && !prdList.isEmpty()) {
                for (ProcessRelevantData prd : prdList) {
                    if (prd != null && prd.getName() != null
                            && prd.getName().equals(variableName)) {
                        return prd;
                    }
                }
            }
        }
        return null;
    }

}
