/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.bx.validation.rules.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.ConfigurationType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.DefinitionType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.ErrorType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.FromType;
import com.tibco.xpd.implementer.nativeservices.emailservice.utilities.EmailExtUtil;
import com.tibco.xpd.validation.bpmn.developer.rules.BaseEmailRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.TaskService;

/**
 * any n2 specific rules for email task are defined here
 * 
 * 1. complex type variable reference is invalid
 * 
 * 
 * @author bharge
 * @since 3.3 (6 Aug 2010)
 */
public class EmailRule extends BaseEmailRule {

    private static final String INVALID_COMPLEX_TYPEVARIABLE_REFERENCE =
            "bx.EmailInvalidComplexTypeVarRef"; //$NON-NLS-1$

    private static final String INVALID_EMAIL_FROM_SELECTION =
            "bx.emailFromOnlyCustomConfigSupported"; //$NON-NLS-1$

    public static final String ISSUE_EMAIL_FROM_PATTERN_FAILURE =
            "bx.emailValidationFailureOnFromField"; //$NON-NLS-1$

    public static final String EMAIL_REPLYTO_ADDR_LABEL =
            "bx.emailReplyToAddress.label"; //$NON-NLS-1$

    public static final String ISSUE_REQUIRED_FIELD =
            "n2pe.ext.email.missingRequiredField"; //$NON-NLS-1$

    public static final String ISSUE_NOT_SUPPORTED_FIELD =
            "n2pe.ext.email.notSupportedField"; //$NON-NLS-1$

    private static final Pattern EMAIL_ADDR_LIST_DELIMITER = Pattern
            .compile("[;,\\s]+"); //$NON-NLS-1$

    private static final Pattern EMAIL_ADDR_REGEX =
            Pattern.compile("^([_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,}))|(%[A-Za-z0-9_]+%)$" //$NON-NLS-1$
            );

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.developer.rules.BaseEmailRule#
     * validateVariableReferenceStr(java.lang.String, java.lang.String,
     * com.tibco.xpd.xpdl2.Activity)
     */
    @Override
    protected void validateVariableReferenceStr(String value,
            String sectionName, Activity activity) {

        super.validateVariableReferenceStr(value, sectionName, activity);
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
                        if (tailPercent >= 0) {
                            String fieldRef =
                                    remainder.substring(0, tailPercent);

                            if (fieldRef.length() > 0) {
                                ProcessRelevantData data =
                                        resolveVariableName(fieldRef, activity);
                                if (data != null
                                        && data.getDataType() instanceof ExternalReference) {
                                    List<String> messages =
                                            new ArrayList<String>();
                                    messages.add(fieldRef);
                                    messages.add(sectionName);
                                    addIssue(INVALID_COMPLEX_TYPEVARIABLE_REFERENCE,
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

    /**
     * Checks to ensure 'Custom' Configuration is selected for 'From' email
     * address. Validates presence / structure of Custom 'From' email address
     * value.
     * 
     * @param tsvc
     * @param activity
     */
    @Override
    protected void validateExtension(TaskService tsvc, Activity activity) {
        EmailType email = EmailExtUtil.getEmailExtension(tsvc);

        // transform correspondents
        if (email != null) {
            DefinitionType def = email.getDefinition();

            if (def != null) {

                // email field: 'To'
                String toEmailAddr = def.getTo();
                if ((toEmailAddr != null) && (toEmailAddr.trim().length() > 0)) {
                    if (!isValidEmailRepresentation(toEmailAddr)) {
                        addError(ISSUE_EMAIL_FROM_PATTERN_FAILURE,
                                activity,
                                Messages.EmailRule_ToField,
                                toEmailAddr);
                    }
                }

                // email field: 'Cc'
                String ccEmailAddr = def.getCc();
                if ((ccEmailAddr != null) && (ccEmailAddr.trim().length() > 0)) {
                    if (!isValidEmailRepresentation(ccEmailAddr)) {
                        addError(ISSUE_EMAIL_FROM_PATTERN_FAILURE,
                                activity,
                                Messages.EmailRule_CcField,
                                ccEmailAddr);
                    }
                }

                // email field: 'Bcc'
                String bccEmailAddr = def.getBcc();
                if ((bccEmailAddr != null)
                        && (bccEmailAddr.trim().length() > 0)) {
                    if (!isValidEmailRepresentation(bccEmailAddr)) {
                        addError(ISSUE_EMAIL_FROM_PATTERN_FAILURE,
                                activity,
                                Messages.EmailRule_BccField,
                                bccEmailAddr);
                    }
                }

                // email field: 'ReplyTo'
                String replyToEmailAddr = def.getReplyTo();
                if ((replyToEmailAddr != null)
                        && (replyToEmailAddr.trim().length() > 0)) {
                    if (!isValidEmailRepresentation(replyToEmailAddr)) {
                        addError(ISSUE_EMAIL_FROM_PATTERN_FAILURE,
                                activity,
                                Messages.EmailRule_ReplyToField,
                                replyToEmailAddr);
                    }
                }

                // email field: 'From'
                FromType fromType = def.getFrom();

                String fromEmailAddr = "dummy"; //$NON-NLS-1$
                if (fromType != null) {

                    ConfigurationType ctype = fromType.getConfiguration();
                    if (ctype.getValue() == ConfigurationType.CUSTOM) {
                        fromEmailAddr = fromType.getValue();
                        if ((fromEmailAddr == null)
                                || (fromEmailAddr.trim().length() <= 0)) {
                            addError(ISSUE_REQUIRED_FIELD,
                                    activity,
                                    Messages.EmailRule_CustomConfig);
                        } else if (!isValidEmailRepresentation(fromEmailAddr)) {
                            addError(ISSUE_EMAIL_FROM_PATTERN_FAILURE,
                                    activity,
                                    Messages.EmailRule_FromField,
                                    fromEmailAddr);
                        }
                    } else {
                        addError(INVALID_EMAIL_FROM_SELECTION, activity);
                    }

                }

                ErrorType errType = email.getError();
                if (errType != null && errType.getReturnCode() != null) {
                    addError(ISSUE_NOT_SUPPORTED_FIELD,
                            activity,
                            Messages.EmailRule_ErrReturnCode);
                }
                if (errType != null && errType.getReturnMessage() != null) {
                    addError(ISSUE_NOT_SUPPORTED_FIELD,
                            activity,
                            Messages.EmailRule_ErrReturnMsg);
                }
            }
        }
    }

    /**
     * Attempts to parse a text representing zero or more email addresses and
     * validate them individually. Failure indication returned if one of the
     * email address tokens fails validation
     * 
     * @param text
     *            representing one or more email addresses
     */
    boolean isValidEmailRepresentation(String emailText) {

        for (String emailAddr : EMAIL_ADDR_LIST_DELIMITER.split(emailText)) {
            if ((emailAddr != null) && (emailAddr.length() > 0)) {
                if (!EMAIL_ADDR_REGEX.matcher(emailAddr).find())
                    return false;
            }
        }

        return true;
    }

    /**
     * Associate error with an XPD object. Supports argument placeholders for
     * error messages.
     * 
     * @param message
     *            key
     * @param message
     *            arguments
     * @param XPD
     *            object to associate to error message
     */
    private void addError(String msgCode, EObject obj, String... arguments) {
        List<String> args = Arrays.asList(arguments);
        this.addIssue(msgCode, obj, args);
    }
}
