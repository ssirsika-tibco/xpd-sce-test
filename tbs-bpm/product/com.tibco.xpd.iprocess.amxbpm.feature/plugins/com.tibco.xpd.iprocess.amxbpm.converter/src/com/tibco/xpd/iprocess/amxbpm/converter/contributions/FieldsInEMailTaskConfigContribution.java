/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.iprocess.amxbpm.converter.contributions;

import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.customer.api.iprocess.amxbpm.conversion.AbstractIProcessToBPMContribution;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.AttachmentType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.ConfigurationType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.DefinitionType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.FromType;
import com.tibco.xpd.implementer.nativeservices.emailservice.utilities.EmailExtUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.xpdExtension.Audit;
import com.tibco.xpd.xpdExtension.AuditEvent;
import com.tibco.xpd.xpdExtension.AuditEventType;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Length;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * If an EMail task has taken an SW_ field into use in it's configuration, then
 * this contribution modifies those configurations to fit into AMX-BPM
 * environment because ultimately SW_AndIDX_ProcessDataContribution would delete
 * the SW_ field.
 * <p>
 * This contribution also resolves the field references which were broken
 * because of mismatch in the field name case (however the references broken
 * because of mismatch in the field name spelling will be left as they were).
 * 
 * @author sajain
 * @since Sep 2, 2014
 */
public class FieldsInEMailTaskConfigContribution extends
        AbstractIProcessToBPMContribution {

    /**
     * SW_ field text.
     */
    private final String SW_ = "SW_"; //$NON-NLS-1$

    /**
     * String field length.
     */
    private static final short DEF_STRING_LENGTH = 50;

    /**
     * @see com.tibco.xpd.customer.api.iprocess.amxbpm.conversion.AbstractIProcessToBPMContribution#convert(java.util.List,
     *      java.util.List, org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param processes
     * @param processInterfaces
     * @param monitor
     * @return
     */
    @Override
    public IStatus convert(List<Process> processes,
            List<ProcessInterface> processInterfaces, IProgressMonitor monitor) {

        try {
            monitor.beginTask("", processes.size()); //$NON-NLS-1$

            for (Process eachProcess : processes) {

                /*
                 * Handle SW_ fields usage in EMail task config which we don't
                 * support in AMX BPM.
                 */
                convertEMailTaskConfig(eachProcess);

                monitor.worked(1);
            }

            return Status.OK_STATUS;

        } finally {
            monitor.done();
        }
    }

    /**
     * Convert configurations of all EMail tasks in the specified process.
     * 
     * @param proc
     *            The process to look into.
     */
    private void convertEMailTaskConfig(Process proc) {

        /*
         * Fetch all activities from the current process.
         */
        Collection<Activity> allActivities =
                Xpdl2ModelUtil.getAllActivitiesInProc(proc);

        /*
         * Go through all activities to check if there are any EMail tasks.
         */
        for (Activity eachActivity : allActivities) {

            /*
             * Fetch activity implementation.
             */
            com.tibco.xpd.xpdl2.Implementation actImpl =
                    eachActivity.getImplementation();

            /*
             * Check if it's an instance of Task.
             */
            if (actImpl instanceof Task) {

                Task task = (Task) actImpl;

                /*
                 * Fetch TaskService.
                 */
                TaskService taskService = task.getTaskService();

                /*
                 * Check if it is a service task.
                 */
                if (taskService != null) {

                    /*
                     * If it is, then check if it's implementation type is
                     * EMail.
                     */
                    Object extensionAttribute =
                            getExtensionAttribute(taskService,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_ImplementationType());

                    if (extensionAttribute instanceof String) {

                        String implType = extensionAttribute.toString();

                        if (TaskImplementationTypeDefinitions.EMAIL_SERVICE
                                .equalsIgnoreCase(implType)) {

                            /*
                             * XPD-6639/XPD-6653: Saket: Handle SW_ field(s)
                             * usage in an email task and if it's not a SW_
                             * field, then look if any process relevant data is
                             * being referenced with a different case (for
                             * example FIELD being referenced as Field) and fix
                             * the reference.
                             */
                            resolveBrokenDataReference(eachActivity,
                                    taskService);

                        }

                    }

                }
            }
        }
    }

    /**
     * Resolve broken data reference for the specified email task.
     * 
     * @param activity
     *            The activity.
     * @param taskService
     *            The enclosed service task.
     */
    private void resolveBrokenDataReference(Activity activity,
            TaskService taskService) {

        /*
         * Get email extension.
         */
        EmailType email = EmailExtUtil.getEmailExtension(taskService);

        if (email != null) {

            /*
             * Get email definition.
             */
            DefinitionType def = email.getDefinition();

            String newValue = new String();

            if (def != null) {

                /*
                 * Get "From".
                 */
                FromType from = def.getFrom();

                /*
                 * Get it's configuration type.
                 */
                ConfigurationType configType = from.getConfiguration();

                /*
                 * See if the configuration type is custom because in that case
                 * only there may be a string value.
                 */
                if (ConfigurationType.CUSTOM_LITERAL.equals(configType)) {
                    /*
                     * Get the from string value.
                     */
                    String fromValue = from.getValue();

                    if (fromValue != null && !fromValue.isEmpty()) {
                        /*
                         * Get new value for "From".
                         */
                        newValue = getResolvedString(fromValue, activity);

                        /*
                         * If the new value is valid, set it.
                         */
                        if (newValue != null) {

                            from.setValue(newValue);
                        }
                    }
                }

                /*
                 * Get "To".
                 */
                String to = def.getTo();
                if (to != null) {

                    /*
                     * Get new value for "To".
                     */
                    newValue = getResolvedString(to, activity);

                    /*
                     * If the new value is valid, set it.
                     */
                    if (newValue != null) {

                        def.setTo(newValue);
                    }
                }

                /*
                 * Get "Cc".
                 */
                String cc = def.getCc();
                if (cc != null) {

                    /*
                     * Get new value for "Cc".
                     */
                    newValue = getResolvedString(cc, activity);

                    /*
                     * If the new value is valid, set it.
                     */
                    if (newValue != null) {

                        def.setCc(newValue);
                    }
                }

                /*
                 * Get "Bcc".
                 */
                String bcc = def.getBcc();
                if (bcc != null) {

                    /*
                     * Get new value for "Bcc".
                     */
                    newValue = getResolvedString(bcc, activity);

                    /*
                     * If the new value is valid, set it.
                     */
                    if (newValue != null) {

                        def.setBcc(newValue);
                    }
                }

                /*
                 * Get "Reply to".
                 */
                String replyTo = def.getReplyTo();
                if (replyTo != null) {

                    /*
                     * Get new value for "Reply to".
                     */
                    newValue = getResolvedString(replyTo, activity);

                    /*
                     * If the new value is valid, set it.
                     */
                    if (newValue != null) {

                        def.setReplyTo(newValue);
                    }
                }

                /*
                 * Get headers.
                 */
                String headers = def.getHeaders();
                if (headers != null) {

                    /*
                     * Get new value for "headers".
                     */
                    newValue = getResolvedString(headers, activity);

                    /*
                     * If the new value is valid, set it.
                     */
                    if (newValue != null) {

                        def.setHeaders(newValue);
                    }
                }

                /*
                 * Get priority.
                 */
                String priority = def.getPriority();
                if (priority != null) {

                    /*
                     * Get new value for "priority".
                     */
                    newValue = getResolvedString(priority, activity);

                    /*
                     * If the new value is valid, set it.
                     */
                    if (newValue != null) {

                        def.setPriority(newValue);
                    }
                }

                /*
                 * Get "subject".
                 */
                String subject = def.getSubject();
                if (subject != null) {

                    /*
                     * Get new value for "subject".
                     */
                    newValue = getResolvedString(subject, activity);

                    /*
                     * If the new value is valid, set it.
                     */
                    if (newValue != null) {

                        def.setSubject(newValue);
                    }
                }
            }

            /*
             * Get body.
             */
            String body = email.getBody();
            if (body != null) {

                /*
                 * Get new value for "body".
                 */
                newValue = getResolvedString(body, activity);

                /*
                 * If the new value is valid, set it.
                 */
                if (newValue != null) {

                    email.setBody(newValue);
                }
            }

            /*
             * Get "attachment".
             */
            AttachmentType attachment = email.getAttachment();
            if (attachment != null) {

                /*
                 * Get new value for "attachment".
                 */
                newValue = getResolvedString(attachment.getValue(), activity);

                /*
                 * If the new value is valid, set it.
                 */
                if (newValue != null) {

                    attachment.setValue(newValue);
                }
            }
        }
    }

    /**
     * Get resolved string with all references intact.
     * 
     * @param oldValue
     *            The string to be resolved.
     * @param activity
     *            The EMail task activity.
     * @return Resolved process data name, <code>null</code> if there's nothing
     *         to resolve.
     */
    private String getResolvedString(String oldValue, Activity activity) {

        /*
         * New value.
         */
        StringBuffer newValue = new StringBuffer();

        if (oldValue != null) {

            /*
             * Split into lines (don't allow split of variable name over lines).
             */
            StringTokenizer lines = new StringTokenizer(oldValue, "\n", true); //$NON-NLS-1$

            /*
             * Go through all the lines.
             */
            while (lines.hasMoreTokens()) {

                /*
                 * Get the next line.
                 */
                String line = lines.nextToken();

                /*
                 * Go through the current line Look for % field reference
                 * characters.
                 */
                while (true) {

                    /*
                     * Get the index of first '%'.
                     */
                    int leadPercent = line.indexOf('%');

                    /*
                     * This index shouldn't be negative.
                     */
                    if (leadPercent >= 0) {

                        /*
                         * Append everything till index = leadPercent to output.
                         */
                        newValue.append(line.substring(0, leadPercent + 1));

                        /*
                         * Get the rest of the line after the first '%'.
                         */
                        String remainder = line.substring(leadPercent + 1);

                        /*
                         * Get the index of the next '%' in 'remainder'.
                         */
                        int tailPercent = remainder.indexOf('%');

                        /*
                         * Check if tail % is just after lead %. In other words
                         * check if index of tail % in remainder is 0.
                         */
                        if (tailPercent == 0) {

                            /*
                             * If that's the case, then directly append "%" to
                             * the output.
                             */
                            newValue.append("%"); //$NON-NLS-1$

                        } else if (tailPercent > 0) {

                            /*
                             * tailPercent is greater than 0, then it means that
                             * there must be a field referenced by the name
                             * enclosed between the lead % and tail %.
                             */

                            /*
                             * Probable name of the data since it's enclosed
                             * between two '%'s.
                             */
                            String fieldRef =
                                    remainder.substring(0, tailPercent);

                            /*
                             * This fieldRef should at least be of one
                             * character.
                             */
                            if (fieldRef.length() > 0) {

                                /*
                                 * Append updated field name.
                                 */
                                newValue.append(getUpdatedFieldName(activity,
                                        fieldRef));

                                /*
                                 * Append tail %.
                                 */
                                newValue.append("%"); //$NON-NLS-1$

                            } else {

                                /*
                                 * Found % escaped with following % - add as it
                                 * is.
                                 */
                                newValue.append("%"); //$NON-NLS-1$
                            }

                        } else {

                            /*
                             * There's no tail %, so just append the remainder
                             * (i.e., the line after the lead %) to the output
                             * and break out of the inner while(true) loop.
                             */
                            newValue.append(remainder);
                            break;

                        }

                        /*
                         * Get remainder of line after fieldRef.
                         */
                        line = remainder.substring(tailPercent + 1);

                    } else {

                        /*
                         * No more %'s on line, so append it on new value and
                         * BREAK the loop.
                         */

                        if (line != null) {

                            /*
                             * If that is the case, then directly append the
                             * line to the newValue.
                             */
                            newValue.append(line);
                        }
                        break;
                    }
                }
            }
        }

        return newValue.toString();
    }

    /**
     * Get the updated field name i.e., name of the replacement of SW field OR
     * name of the field whose reference was broken because of case-mismatch.
     * 
     * @param activity
     *            Activity to look into.
     * @param fieldRefString
     *            The string by which a field is being referenced CURRENTLY.
     * @return Name of the replacement of SW field OR name of the field whose
     *         reference was broken because of case-mismatch.
     */
    private String getUpdatedFieldName(Activity activity, String fieldRefString) {

        String newFieldName = null;

        /*
         * See if it's an SW_ field because if it is, then it has to be
         * processed differently.
         */
        if (fieldRefString.startsWith(SW_)) {

            /*
             * If it is a SW field, then we'll have to see which field is it and
             * then act accordingly.
             */

            /*
             * Note: Not using switch-case here because it doesn't operate on
             * Strings in Java versions below 1.7. So just playing it safe.
             */
            if (fieldRefString.equals("SW_NA")) { //$NON-NLS-1$

                newFieldName =
                        formBaseToHandleSWFieldsInEmailTaskConfig(activity,
                                BasicTypeType.STRING_LITERAL,
                                "_null_", "=null;", fieldRefString); //$NON-NLS-1$ //$NON-NLS-2$

            } else if (fieldRefString.equals("SW_TIME")) { //$NON-NLS-1$

                newFieldName =
                        formBaseToHandleSWFieldsInEmailTaskConfig(activity,
                                BasicTypeType.TIME_LITERAL,
                                "_currentTime_", "=DateTimeUtil.createTime();", fieldRefString); //$NON-NLS-1$ //$NON-NLS-2$

            } else if (fieldRefString.equals("SW_DATE")) { //$NON-NLS-1$

                newFieldName =
                        formBaseToHandleSWFieldsInEmailTaskConfig(activity,
                                BasicTypeType.DATE_LITERAL,
                                "_currentDate_", "=DateTimeUtil.createDate();", fieldRefString); //$NON-NLS-1$ //$NON-NLS-2$

            } else if (fieldRefString.equals("SW_PRONAME")) { //$NON-NLS-1$

                newFieldName =
                        formBaseToHandleSWFieldsInEmailTaskConfig(activity,
                                BasicTypeType.STRING_LITERAL,
                                "_processName_", "=Process.getName();", fieldRefString); //$NON-NLS-1$ //$NON-NLS-2$

            } else if (fieldRefString.equals("SW_CP_VALUE")) { //$NON-NLS-1$

                newFieldName =
                        formBaseToHandleSWFieldsInEmailTaskConfig(activity,
                                BasicTypeType.INTEGER_LITERAL,
                                "_processPriority_", "=Process.getPriority();", fieldRefString); //$NON-NLS-1$ //$NON-NLS-2$

            } else if (fieldRefString.equals("SW_CASENUM")) { //$NON-NLS-1$

                newFieldName =
                        formBaseToHandleSWFieldsInEmailTaskConfig(activity,
                                BasicTypeType.STRING_LITERAL,
                                "_processId_", "=Process.getId();", fieldRefString); //$NON-NLS-1$ //$NON-NLS-2$
            } else {

                /*
                 * No matches found with any of the SW_ fields we are handling,
                 * so leave as it is.
                 */
                newFieldName = fieldRefString;
            }

        } else {

            /*
             * Not an SW field, so just resolve references.
             */

            /*
             * See if we have any data with this name (here we WILL match case.)
             */
            ProcessRelevantData data =
                    getProcRelDataByName(fieldRefString, activity);

            /*
             * See if we've found any data.
             */
            if (data == null) {

                /*
                 * Couldn't find any data with that name, so look for resembling
                 * data (i.e., data with name in DIFFERENT case).
                 */
                ProcessRelevantData resemblingData =
                        getResemblingData(fieldRefString, activity);

                /*
                 * See if we've found a data with resembling name.
                 */
                if (resemblingData != null) {

                    /*
                     * Store it's name.
                     */
                    newFieldName = resemblingData.getName();

                } else {

                    /*
                     * If we haven't found data with similar name as well, then
                     * add as it is and let the user deal with the reference
                     * problem markers.
                     */
                    newFieldName = fieldRefString;

                }

            } else {

                /*
                 * Found data with exactly that name, so add it to the newLine
                 * directly.
                 */
                newFieldName = fieldRefString;
            }
        }

        return newFieldName;
    }

    /**
     * Form base to handle SW fields used in email task configurations. This is
     * done by:
     * <p>
     * 1. Creating or fetching (if it already exists) the field with an
     * appropriate name and return it's name.
     * <p>
     * 2. Creating initiate script to initialise the field created above.
     * 
     * @param activity
     *            The activity to look in.
     * @param basicType
     *            Basic type of the field.
     * @param fieldNameSubStr
     *            Substring to be used while naming the field.
     * @param scriptSubStr
     *            Substring to be used while generating the script expression.
     * @param originalSWFieldName
     *            The name of the original SW field whose replacement is to be
     *            created.
     * @return Name of the field.
     */
    private String formBaseToHandleSWFieldsInEmailTaskConfig(Activity activity,
            BasicTypeType basicType, String fieldNameSubStr,
            String scriptSubStr, String originalSWFieldName) {
        /*
         * Create new data field.
         */
        DataField field =
                getOrCreateFieldWithName(activity, "_" + activity.getName() //$NON-NLS-1$
                        + fieldNameSubStr, basicType, originalSWFieldName);

        /*
         * Create initiate script (or append to the existing initiate script).
         */
        getInitiateScript(activity, field, field.getName() + scriptSubStr);

        return field.getName();

    }

    /**
     * Create initiate script for the specified activity to initialise the field
     * with the specified name.
     * 
     * @param activity
     *            The activity wherein initiate script is to be added.
     * @param newField
     *            The field to be initialised in the initiate script.
     * @param expr
     *            Expression to be set in the initiate script.
     */
    private void getInitiateScript(Activity activity, DataField newField,
            String expressionStringToBeAppended) {

        /*
         * Get the audit object from activity.
         */
        Object auditObj =
                Xpdl2ModelUtil.getOtherElement(activity,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_Audit());

        /*
         * Get or generate audit element based on whether the activity already
         * has an audit element or not.
         */
        Audit audit = getOrGenerateNewAuditElement(auditObj, activity);

        /*
         * Get audit event of type "initiated" in the specified audit.
         */
        AuditEvent auditEvent = getAuditEventOfTypeInitiated(audit);

        String initiateScriptText = null;

        if (auditEvent != null) {

            /*
             * Get the script text.
             */
            if (auditEvent.getInformation() != null) {

                initiateScriptText = auditEvent.getInformation().getText();
            }

            /*
             * See if there's already some text in the script because in that
             * case we'll have to append our script.
             */
            if (initiateScriptText != null && !initiateScriptText.isEmpty()) {

                /*
                 * There's some text, so see if expressionStringToBeAppended is
                 * already there and append only if it is not there already.
                 */
                if (!initiateScriptText.contains(expressionStringToBeAppended)) {

                    /*
                     * Append.
                     */
                    initiateScriptText =
                            initiateScriptText
                                    + "\n" + expressionStringToBeAppended; //$NON-NLS-1$

                }

            } else {

                /*
                 * The script is empty, so simply initialise.
                 */
                initiateScriptText = expressionStringToBeAppended;
            }

            /*
             * Create javascript expression to be used in initiate script.
             */
            Expression expr =
                    Xpdl2ModelUtil.createExpression(initiateScriptText);

            /*
             * Set script grammar type of the expression.
             */
            expr.setScriptGrammar(ScriptGrammarFactory.JAVASCRIPT);

            /*
             * Set expression to the audit event.
             */
            auditEvent.setInformation(expr);
        }

    }

    /**
     * Get audit event of type "Initiated" in the specified audit. Return
     * <code>null</code> if there's no such audit event instance in the given
     * audit.
     * 
     * @param audit
     *            The audit element.
     * @return Audit event of type initiated in the specified audit,
     *         <code>null</code> if there's no such audit event instance in the
     *         given audit.
     */
    private AuditEvent getAuditEventOfTypeInitiated(Audit audit) {

        /*
         * Fetch the list of audit events in this audit.
         */
        EList<AuditEvent> allAuditEvents = audit.getAuditEvent();

        /*
         * See if there's atleast one audit event with type "initiated" in the
         * list.
         */
        if (allAuditEvents != null && !allAuditEvents.isEmpty()) {

            /*
             * Get audit event of type "initiated" to which expression is to be
             * added.
             */
            for (AuditEvent eachAuditEvent : audit.getAuditEvent()) {

                /*
                 * See if there's an audit event of type "Initiated".
                 */
                if (eachAuditEvent != null
                        && AuditEventType.INITIATED_LITERAL
                                .equals(eachAuditEvent.getType())) {

                    /*
                     * If there is, return it.
                     */
                    return eachAuditEvent;
                }
            }
        }

        /*
         * Return null if there's no audit element of type "initiated".
         */
        return null;
    }

    /**
     * Get or generate audit element based on whether the activity already has
     * an audit element or not.
     * 
     * @param auditObj
     *            The audit object.
     * @param activity
     *            The activity to look in.
     * 
     * @return An audit element.
     */
    private Audit getOrGenerateNewAuditElement(Object auditObj,
            Activity activity) {

        Audit audit = null;

        AuditEvent auditEvent = null;

        /*
         * See if the audit object is valid. It will signify whether the audit
         * element already exists or not.
         */
        if (auditObj == null || !(auditObj instanceof Audit)) {

            /*
             * Activity doesn't have an audit element.
             */

            /*
             * Create audit.
             */
            audit = XpdExtensionFactory.eINSTANCE.createAudit();

            /*
             * Create audit event.
             */
            auditEvent = XpdExtensionFactory.eINSTANCE.createAuditEvent();

            /*
             * Set type to "Initiated".
             */
            auditEvent.setType(AuditEventType.INITIATED_LITERAL);

            if (audit.getAuditEvent() != null) {

                /*
                 * Add audit event to audit.
                 */
                audit.getAuditEvent().add(auditEvent);
            }

            /*
             * Add audit to activity.
             */
            Xpdl2ModelUtil.setOtherElement(activity,
                    XpdExtensionPackage.eINSTANCE.getDocumentRoot_Audit(),
                    audit);

        } else {

            /*
             * Activity already has an audit element.
             */

            audit = (Audit) auditObj;

            /*
             * Get audit event of type initiated.
             */
            auditEvent = getAuditEventOfTypeInitiated(audit);

            /*
             * See if there's an audit event of type "initiated".
             */
            if (auditEvent == null) {
                /*
                 * If we have no such audit event, then create an audit event
                 * which is to be set to audit.
                 */
                auditEvent = XpdExtensionFactory.eINSTANCE.createAuditEvent();

                /*
                 * Set type to "Initiated".
                 */
                auditEvent.setType(AuditEventType.INITIATED_LITERAL);

                /*
                 * Add it to audit.
                 */
                audit.getAuditEvent().add(auditEvent);

            }
        }

        return audit;
    }

    /**
     * Create a new field with the specified name if it doesn't already exist
     * and add it to the specified activity. If a field with the specified name
     * already exists, then simply fetch it and return it.
     * 
     * @param activity
     *            The activity to which the field is to be added.
     * 
     * @param fieldName
     *            The name of the field.
     * 
     * @param basicType
     *            The type to be set to the field.
     * @param originalSWFieldName
     *            The name of the original SW field whose replacement is to be
     *            created.
     * 
     * @return Data field with the specified name.
     */
    private DataField getOrCreateFieldWithName(Activity activity,
            String fieldName, BasicTypeType basicType,
            String originalSWFieldName) {

        /*
         * See if the field exists.
         */
        DataField field = getExistingField(activity, fieldName);

        if (field != null) {

            /*
             * If it exists, then simply return it.
             */
            return field;

        } else {

            /*
             * Else create a new data field.
             */
            DataField newField = Xpdl2Factory.eINSTANCE.createDataField();

            /*
             * Set it's name.
             */
            newField.setName(fieldName);

            /*
             * Set it's type and length.
             */
            BasicType dataType = Xpdl2Factory.eINSTANCE.createBasicType();
            dataType.setType(basicType);

            /*
             * Need to set length only for String fields.
             */
            if (BasicTypeType.STRING_LITERAL.equals(basicType)) {

                Length defLength = Xpdl2Factory.eINSTANCE.createLength();
                defLength.setValue(Short.toString(DEF_STRING_LENGTH));
                dataType.setLength(defLength);

            }

            newField.setDataType(dataType);

            /*
             * Set it's display name.
             */
            Xpdl2ModelUtil
                    .setOtherAttribute(newField,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName(),
                            String.format(com.tibco.xpd.iprocess.amxbpm.converter.internal.Messages.FieldsInEMailTaskConfigContribution_DisplayName_text,
                                    originalSWFieldName));

            /*
             * Set readOnly to false.
             */
            newField.setReadOnly(false);

            /*
             * Add it to the list of fields in the activity.
             */
            activity.getDataFields().add(newField);

            return newField;
        }

    }

    /**
     * If a field with the specified name already exists in the specified
     * activity, then return it. If it doesn't exist, then return
     * <code>null</code>.
     * 
     * @param activity
     *            The activity to look in.
     * @param fieldName
     *            The field name to look for.
     * @return a field with the specified name if it already exists in the
     *         specified activity. If it doesn't exist, then return
     *         <code>null</code>.
     */
    private DataField getExistingField(Activity activity, String fieldName) {

        /*
         * Go through all the data fields.
         */
        for (DataField eachField : activity.getDataFields()) {

            /*
             * If we have a field with the specified name, then return it.
             */
            if (eachField != null && fieldName.equals(eachField.getName())) {

                return eachField;
            }
        }

        /*
         * Else return null.
         */
        return null;
    }

    /**
     * Get process relevant data by name.
     * 
     * @param dataName
     *            Name of the process relevant data.
     * @param activity
     *            The EMail task activity.
     * @return Process relevant data if that exists by the name specified,
     *         <code>null</code> otherwise.
     */
    private ProcessRelevantData getProcRelDataByName(String dataName,
            Activity activity) {

        if (dataName != null && activity != null) {

            /*
             * Get all process relevant data.
             */
            List<ProcessRelevantData> prdList =
                    ProcessDataUtil.getProcessRelevantData(activity);

            /*
             * See if there's any data.
             */
            if (prdList != null && !prdList.isEmpty()) {

                /*
                 * Go through the list.
                 */
                for (ProcessRelevantData prd : prdList) {

                    /*
                     * See if we've found a process relevant data with the
                     * specified name.
                     */
                    if (prd != null && prd.getName() != null
                            && prd.getName().equals(dataName)) {

                        /*
                         * Return it if we have.
                         */
                        return prd;
                    }
                }
            }
        }

        /*
         * Return null if we haven't found anything.
         */
        return null;
    }

    /**
     * Get the process relevant data which might have been named in a different
     * case as that of the name specified, but spells exactly the same.
     * 
     * @param dataName
     *            Name of the data.
     * @param activity
     *            The EMail task activity.
     * @return The process relevant data which might have been named in a
     *         different case as that of the name specified, but spells exactly
     *         the same. Return <code>null</code> if there's no such data.
     */
    private ProcessRelevantData getResemblingData(String dataName,
            Activity activity) {

        if (dataName != null && activity != null) {

            /*
             * Get all process relevant data.
             */
            List<ProcessRelevantData> prdList =
                    ProcessDataUtil.getProcessRelevantData(activity);

            /*
             * See if there's any data.
             */
            if (prdList != null && !prdList.isEmpty()) {

                /*
                 * Go through the list.
                 */
                for (ProcessRelevantData prd : prdList) {

                    /*
                     * See if we've found a process relevant data with the
                     * specified name, but ignore case while comparing.
                     */
                    if (prd != null && prd.getName() != null
                            && prd.getName().equalsIgnoreCase(dataName)) {

                        /*
                         * Return it if we have.
                         */
                        return prd;
                    }
                }
            }
        }

        /*
         * Return null if we haven't found anything.
         */
        return null;
    }
}
