/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.emailservice.utilities;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.implementer.nativeservices.emailservice.email.AttachmentType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.ConfigurationType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.DefinitionType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailFactory;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailPackage;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.ErrorType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.FromType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.SMTPType;
import com.tibco.xpd.implementer.nativeservices.utilities.TaskServiceExtUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;

/**
 * Email extension utility. Static class can be used to create various contents
 * of the Email extended model
 * 
 * @author njpatel
 * 
 */
public class EmailExtUtil {

    /**
     * Get Email object from <code>TaskService</code>
     * 
     * @param service
     * @return
     */
    public static EmailType getEmailExtension(TaskService service) {
        EmailType email = null;

        EObject eo =
                TaskServiceExtUtil.getExtendedModel(service,
                        EmailPackage.eINSTANCE.getDocumentRoot_Email());

        if (eo instanceof EmailType) {
            email = (EmailType) eo;
        }

        return email;
    }

    public static TaskService getTaskService(Activity activity) {
        if (activity.getImplementation() instanceof Task) {
            Task task = (Task) activity.getImplementation();
            if (null != task.getTaskService()) {
                return task.getTaskService();
            }
        }
        return null;
    }

    public static EmailType getEmailType(Activity activity) {
        EmailType emailTask = null;
        TaskService taskService = getTaskService(activity);
        if (null != taskService) {
            emailTask = getEmailExtension(taskService);
        }
        return emailTask;
    }

    public static ConfigurationType getConfigurationType(Activity activity) {
        ConfigurationType configType = null;
        EmailType emailType = getEmailType(activity);
        if (null != emailType) {
            configType = emailType.getSMTP().getConfiguration();
        }
        return configType;
    }

    public static EmailType getOrAddEmailType(Activity activity,
            EditingDomain editingDomain, CompoundCommand cmd) {
        EmailType emailTask = null;

        emailTask = getEmailType(activity);
        if (null == emailTask) {
            emailTask =
                    createEmailExtension(editingDomain,
                            cmd,
                            getTaskService(activity));
        }
        return emailTask;
    }

    public static SMTPType getOrAddSMTPType(Activity activity,
            EditingDomain ed, CompoundCommand compoundCmd) {
        SMTPType smtpType = null;
        EmailType emailType = getEmailType(activity);
        if (null == emailType) {
            emailType =
                    createEmailExtension(ed,
                            compoundCmd,
                            getTaskService(activity));
        }
        if (null != emailType) {
            smtpType = emailType.getSMTP();
        }
        if (null == smtpType) {
            smtpType = createSMTPType();
            emailType.setSMTP(smtpType);
            compoundCmd.append(TaskServiceExtUtil.addExtendedModel(ed,
                    getTaskService(activity),
                    EmailPackage.eINSTANCE.getDocumentRoot_Email(),
                    emailType));
        }
        return smtpType;
    }

    public static SMTPType getSMTPTypeProperties(Activity activity) {
        SMTPType smtpType = null;
        EmailType emailTask = getEmailType(activity);
        if (null != emailTask) {
            smtpType = emailTask.getSMTP();
        }
        return smtpType;
    }

    public static ErrorType getEmailErrorTypeProperties(Activity activity) {
        ErrorType errorType = null;
        EmailType emailTask = getEmailType(activity);
        if (null != emailTask) {
            errorType = emailTask.getError();
        }

        return errorType;
    }

    public static ErrorType getOrAddEmailErrorTypeProperties(Activity activity,
            EditingDomain ed, CompoundCommand compoundCmd) {
        ErrorType errorType = null;
        EmailType emailTask = getEmailType(activity);
        if (null == emailTask) {
            emailTask =
                    createEmailExtension(ed,
                            compoundCmd,
                            getTaskService(activity));
        }
        if (null != emailTask) {
            errorType = emailTask.getError();
        }
        if (null == errorType) {
            errorType = createError(ed, compoundCmd, emailTask);

            compoundCmd.append(SetCommand.create(ed,
                    emailTask,
                    EmailPackage.eINSTANCE.getEmailType_Error(),
                    errorType));
        }
        return errorType;
    }

    /**
     * Create an Email extension in the XPDL2 model. The command to add the new
     * Email object will be appended to the compound command provided
     * 
     * @param ed
     * @param compoundCmd
     * @param service
     * @return The <code>Email</code> object created.
     */
    public static EmailType createEmailExtension(EditingDomain ed,
            CompoundCommand compoundCmd, TaskService service) {

        EmailType email = null;

        if (service != null && ed != null && compoundCmd != null) {
            email = EmailFactory.eINSTANCE.createEmailType();

            // Set the definition with default from setting - server
            // configuration
            email.setDefinition(createDefinitionType());

            // Set the default SMTP type - server configuration
            email.setSMTP(createSMTPType());

            compoundCmd.append(TaskServiceExtUtil.addExtendedModel(ed,
                    service,
                    EmailPackage.eINSTANCE.getDocumentRoot_Email(),
                    email));
        }

        return email;
    }

    /**
     * Create a definition part of the email extension.
     * 
     * @param ed
     * @param compoundCmd
     *            If a new <code>DefitionType</code> is set then the command
     *            will be added to this compound command
     * @param email
     *            The parent <code>EmailType</code>
     * @return The <code>DefintionType</code> model of the email extension
     */
    public static DefinitionType createDefinition(EditingDomain ed,
            CompoundCommand compoundCmd, EmailType email) {
        DefinitionType definition = null;

        if (email != null && ed != null && compoundCmd != null) {

            definition = createDefinitionType();

            compoundCmd.append(SetCommand.create(ed,
                    email,
                    EmailPackage.eINSTANCE.getEmailType_Definition(),
                    definition));
        }

        return definition;
    }

    /**
     * Create the Attachment part of the email
     * 
     * @param ed
     * @param compoundCmd
     * @param email
     * @return
     */
    public static AttachmentType createAttachment(EditingDomain ed,
            CompoundCommand compoundCmd, EmailType email) {
        AttachmentType attachment = null;

        if (ed != null && compoundCmd != null && email != null) {
            attachment = EmailFactory.eINSTANCE.createAttachmentType();

            compoundCmd.append(SetCommand.create(ed,
                    email,
                    EmailPackage.eINSTANCE.getEmailType_Attachment(),
                    attachment));
        }

        return attachment;
    }

    /**
     * Create the From part of the email
     * 
     * @param ed
     * @param compoundCmd
     * @param definition
     * @return
     */
    public static FromType createFrom(EditingDomain ed,
            CompoundCommand compoundCmd, DefinitionType definition) {
        FromType from = null;

        if (ed != null && compoundCmd != null && definition != null) {
            from = EmailFactory.eINSTANCE.createFromType();

            // By default the from type will be set to server config
            from.setConfiguration(ConfigurationType.SERVER_LITERAL);

            compoundCmd.append(SetCommand.create(ed,
                    definition,
                    EmailPackage.eINSTANCE.getDefinitionType_From(),
                    from));
        }

        return from;
    }

    /**
     * Create the Error handler part of the email
     * 
     * @param ed
     * @param compoundCmd
     * @param email
     * @return
     */
    public static ErrorType createError(EditingDomain ed,
            CompoundCommand compoundCmd, EmailType email) {
        ErrorType error = null;

        if (ed != null && compoundCmd != null && email != null) {
            error = EmailFactory.eINSTANCE.createErrorType();

            compoundCmd.append(SetCommand.create(ed,
                    email,
                    EmailPackage.eINSTANCE.getEmailType_Error(),
                    error));
        }

        return error;
    }

    /**
     * Create the definition type and set the default From configuration (Custom
     * config).
     * 
     * @return
     */
    private static DefinitionType createDefinitionType() {
        DefinitionType definition;
        definition = EmailFactory.eINSTANCE.createDefinitionType();

        // Set the From type to server configuration by default
        FromType from = EmailFactory.eINSTANCE.createFromType();
        from.setConfiguration(ConfigurationType.CUSTOM_LITERAL);
        from.setValue(""); //$NON-NLS-1$
        definition.setFrom(from);
        return definition;
    }

    /**
     * Create the SMTP configuration type and set the default - server
     * configuration
     * 
     * @return
     */
    private static SMTPType createSMTPType() {
        SMTPType smtp = EmailFactory.eINSTANCE.createSMTPType();

        // Set default configuration to server type
        smtp.setConfiguration(ConfigurationType.SERVER_LITERAL);

        return smtp;
    }

}
