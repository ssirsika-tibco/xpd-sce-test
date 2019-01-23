package com.tibco.xpd.implementer.nativeservices.emailservice.utilities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.implementer.nativeservices.emailservice.email.AttachmentType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.ConfigurationType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.DefinitionType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailPackage;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.ErrorType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.FromType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.resolvers.DataReferenceContext;
import com.tibco.xpd.xpdl2.resolvers.IFieldContextResolverExtension;
import com.tibco.xpd.xpdl2.resolvers.ProcessDataReferenceAndContexts;

public class EMailDataFieldResolver implements IFieldContextResolverExtension {

    /**
     * @see com.tibco.xpd.xpdl2.resolvers.IFieldContextResolverExtension#getDataReferences(com.tibco.xpd.xpdl2.Activity,
     *      java.util.Set)
     * 
     * @param activity
     * @param dataSet
     * @return
     */
    @Override
    public Set<ProcessDataReferenceAndContexts> getDataReferences(
            Activity activity, Set<ProcessRelevantData> dataSet) {

        Set<ProcessRelevantData> dataReferences =
                getActivityDataReferences(activity, dataSet);

        if (dataReferences != null) {
            Set<ProcessDataReferenceAndContexts> dataRefAndContexts =
                    new HashSet<ProcessDataReferenceAndContexts>();

            for (ProcessRelevantData data : dataReferences) {
                dataRefAndContexts.add(new ProcessDataReferenceAndContexts(
                        data,
                        DataReferenceContext.CONTEXT_ACTIVITY_IMPLEMENTATION));
            }

            return dataRefAndContexts;
        }
        return null;
    }

    @Override
    public Set<ProcessRelevantData> getActivityDataReferences(
            Activity activity, Set<ProcessRelevantData> dataSet) {
        // First make sure that this is an email task at all.
        if (activity.getImplementation() instanceof Task) {
            TaskService tsvc =
                    ((Task) activity.getImplementation()).getTaskService();

            if (tsvc != null) {
                EmailType email = EmailExtUtil.getEmailExtension(tsvc);

                if (email != null) {
                    return getEmailDataReferences(email, dataSet);
                }
            }
        }
        return null;
    }

    private Set<ProcessRelevantData> getEmailDataReferences(EmailType email,
            Set<ProcessRelevantData> dataSet) {
        Set<ProcessRelevantData> result = new HashSet<ProcessRelevantData>();

        DefinitionType def = email.getDefinition();
        if (def != null) {
            addDataReferences(def.getBcc(), dataSet, result);
            addDataReferences(def.getCc(), dataSet, result);
            addDataReferences(def.getHeaders(), dataSet, result);
            addDataReferences(def.getPriority(), dataSet, result);
            addDataReferences(def.getReplyTo(), dataSet, result);
            addDataReferences(def.getSubject(), dataSet, result);
            addDataReferences(def.getTo(), dataSet, result);

            FromType ft = def.getFrom();
            if (ConfigurationType.CUSTOM_LITERAL.equals(ft.getConfiguration())) {
                addDataReferences(ft.getValue(), dataSet, result);
            }
        }

        AttachmentType attach = email.getAttachment();
        if (attach != null) {
            addDataReferences(attach.getValue(), dataSet, result);
        }

        addDataReferences(email.getBody(), dataSet, result);

        ErrorType error = email.getError();
        if (error != null) {

            addDirectDataReferences(error.getReturnCode(), dataSet, result);
            addDirectDataReferences(error.getReturnMessage(), dataSet, result);
        }
        return result;
    }

    /**
     * Check for %field name% references in the given text
     * 
     * @param str
     * @param dataSet
     */
    private void addDataReferences(String str,
            Set<ProcessRelevantData> dataSet, Set<ProcessRelevantData> result) {
        if (str != null) {
            for (ProcessRelevantData data : dataSet) {
                String lookFor = "%" + data.getName() + "%"; //$NON-NLS-1$ //$NON-NLS-2$

                if (str.contains(lookFor)) {
                    result.add(data);
                }
            }
        }

    }

    /**
     * Add data references that are not %...% wrapped to result set
     * 
     * @param str
     * @param dataSet
     * @param result
     */
    private void addDirectDataReferences(String str,
            Set<ProcessRelevantData> dataSet, Set<ProcessRelevantData> result) {
        if (str != null) {
            for (ProcessRelevantData data : dataSet) {
                if (str.equals(data.getName())) {
                    result.add(data);
                }
            }
        }

    }

    @Override
    public Set<ProcessRelevantData> getTransitionDataReferences(
            Transition transition, Set<ProcessRelevantData> dataSet) {
        return null; // No email stuff in transitions
    }

    @Override
    public Command getSwapActivityDataIdReferencesCommand(
            EditingDomain editingDomain, Activity activity,
            Map<String, String> idMap) {
        // There are never references by field id.
        return null;
    }

    @Override
    public Command getSwapTransitionDataIdReferencesCommand(
            EditingDomain editingDomain, Transition transition,
            Map<String, String> idMap) {
        // No email stuff in transitions
        return null;
    }

    @Override
    public Command getSwapTransitionDataNameReferencesCommand(
            EditingDomain editingDomain, Transition transition,
            Map<String, String> nameMap) {
        // No email stuff in transitions
        return null;
    }

    @Override
    public Command getSwapActivityDataNameReferencesCommand(
            EditingDomain editingDomain, Activity activity,
            Map<String, String> nameMap) {
        // First make sure that this is an email task at all.
        if (activity.getImplementation() instanceof Task) {
            TaskService tsvc =
                    ((Task) activity.getImplementation()).getTaskService();

            if (tsvc != null) {
                EmailType email = EmailExtUtil.getEmailExtension(tsvc);

                if (email != null) {
                    return getSwapEmailDataReferences(editingDomain,
                            email,
                            nameMap);
                }
            }
        }
        return null;
    }

    @Override
    public Command getDeleteDataFromActivityCommand(
            EditingDomain editingDomain, Activity activity,
            ProcessRelevantData data) {
        // First make sure that this is an email task at all.
        if (activity.getImplementation() instanceof Task) {
            TaskService tsvc =
                    ((Task) activity.getImplementation()).getTaskService();

            if (tsvc != null) {
                EmailType email = EmailExtUtil.getEmailExtension(tsvc);

                if (email != null) {
                    return getDeleteEmailDataReferences(editingDomain,
                            email,
                            data);
                }
            }
        }
        return null;
    }

    private Command getDeleteEmailDataReferences(EditingDomain editingDomain,
            EmailType email, ProcessRelevantData data) {
        // we can simply use the swap name method with name map OldName->""
        Map<String, String> nameMap = new HashMap<String, String>();
        nameMap.put(data.getName(), ""); //$NON-NLS-1$

        CompoundCommand cmd = new CompoundCommand();

        DefinitionType def = email.getDefinition();
        if (def != null) {
            addSwapReferenceCommand(editingDomain,
                    cmd,
                    def.getBcc(),
                    def,
                    EmailPackage.eINSTANCE.getDefinitionType_Bcc(),
                    nameMap,
                    true);

            addSwapReferenceCommand(editingDomain,
                    cmd,
                    def.getCc(),
                    def,
                    EmailPackage.eINSTANCE.getDefinitionType_Cc(),
                    nameMap,
                    true);

            addSwapReferenceCommand(editingDomain,
                    cmd,
                    def.getHeaders(),
                    def,
                    EmailPackage.eINSTANCE.getDefinitionType_Headers(),
                    nameMap,
                    true);

            addSwapReferenceCommand(editingDomain,
                    cmd,
                    def.getPriority(),
                    def,
                    EmailPackage.eINSTANCE.getDefinitionType_Priority(),
                    nameMap,
                    true);

            addSwapReferenceCommand(editingDomain,
                    cmd,
                    def.getReplyTo(),
                    def,
                    EmailPackage.eINSTANCE.getDefinitionType_ReplyTo(),
                    nameMap,
                    true);

            addSwapReferenceCommand(editingDomain,
                    cmd,
                    def.getSubject(),
                    def,
                    EmailPackage.eINSTANCE.getDefinitionType_Subject(),
                    nameMap,
                    true);

            addSwapReferenceCommand(editingDomain,
                    cmd,
                    def.getTo(),
                    def,
                    EmailPackage.eINSTANCE.getDefinitionType_To(),
                    nameMap,
                    true);

            FromType ft = def.getFrom();
            if (ConfigurationType.CUSTOM_LITERAL.equals(ft.getConfiguration())) {
                addSwapReferenceCommand(editingDomain,
                        cmd,
                        ft.getValue(),
                        ft,
                        EmailPackage.eINSTANCE.getFromType_Value(),
                        nameMap,
                        true);
            }
        }

        AttachmentType attach = email.getAttachment();
        if (attach != null) {
            addSwapReferenceCommand(editingDomain,
                    cmd,
                    attach.getValue(),
                    attach,
                    EmailPackage.eINSTANCE.getAttachmentType_Value(),
                    nameMap,
                    true);
        }

        addSwapReferenceCommand(editingDomain,
                cmd,
                email.getBody(),
                email,
                EmailPackage.eINSTANCE.getEmailType_Body(),
                nameMap,
                true);

        ErrorType error = email.getError();
        if (error != null) {
            addSwapReferenceCommand(editingDomain,
                    cmd,
                    error.getReturnCode(),
                    error,
                    EmailPackage.eINSTANCE.getErrorType_ReturnCode(),
                    nameMap,
                    false);

            addSwapReferenceCommand(editingDomain,
                    cmd,
                    error.getReturnMessage(),
                    error,
                    EmailPackage.eINSTANCE.getErrorType_ReturnMessage(),
                    nameMap,
                    false);
        }

        // Only return commands if we have any
        if (cmd.getCommandList().size() > 0) {
            return cmd;
        }
        return null;
    }

    /**
     * Swap the field name references provided in the given map in all the email
     * task internals.
     * 
     * @param editingDomain
     * @param email
     * @param nameMap
     * @return
     */
    private Command getSwapEmailDataReferences(EditingDomain editingDomain,
            EmailType email, Map<String, String> nameMap) {

        CompoundCommand cmd = new CompoundCommand();

        DefinitionType def = email.getDefinition();
        if (def != null) {
            addSwapReferenceCommand(editingDomain,
                    cmd,
                    def.getBcc(),
                    def,
                    EmailPackage.eINSTANCE.getDefinitionType_Bcc(),
                    nameMap,
                    true);

            addSwapReferenceCommand(editingDomain,
                    cmd,
                    def.getCc(),
                    def,
                    EmailPackage.eINSTANCE.getDefinitionType_Cc(),
                    nameMap,
                    true);

            addSwapReferenceCommand(editingDomain,
                    cmd,
                    def.getHeaders(),
                    def,
                    EmailPackage.eINSTANCE.getDefinitionType_Headers(),
                    nameMap,
                    true);

            addSwapReferenceCommand(editingDomain,
                    cmd,
                    def.getPriority(),
                    def,
                    EmailPackage.eINSTANCE.getDefinitionType_Priority(),
                    nameMap,
                    true);

            addSwapReferenceCommand(editingDomain,
                    cmd,
                    def.getReplyTo(),
                    def,
                    EmailPackage.eINSTANCE.getDefinitionType_ReplyTo(),
                    nameMap,
                    true);

            addSwapReferenceCommand(editingDomain,
                    cmd,
                    def.getSubject(),
                    def,
                    EmailPackage.eINSTANCE.getDefinitionType_Subject(),
                    nameMap,
                    true);

            addSwapReferenceCommand(editingDomain,
                    cmd,
                    def.getTo(),
                    def,
                    EmailPackage.eINSTANCE.getDefinitionType_To(),
                    nameMap,
                    true);

            FromType ft = def.getFrom();
            if (ConfigurationType.CUSTOM_LITERAL.equals(ft.getConfiguration())) {
                addSwapReferenceCommand(editingDomain,
                        cmd,
                        ft.getValue(),
                        ft,
                        EmailPackage.eINSTANCE.getFromType_Value(),
                        nameMap,
                        true);
            }
        }

        AttachmentType attach = email.getAttachment();
        if (attach != null) {
            addSwapReferenceCommand(editingDomain,
                    cmd,
                    attach.getValue(),
                    attach,
                    EmailPackage.eINSTANCE.getAttachmentType_Value(),
                    nameMap,
                    true);
        }

        addSwapReferenceCommand(editingDomain,
                cmd,
                email.getBody(),
                email,
                EmailPackage.eINSTANCE.getEmailType_Body(),
                nameMap,
                true);

        ErrorType error = email.getError();
        if (error != null) {
            addSwapReferenceCommand(editingDomain,
                    cmd,
                    error.getReturnCode(),
                    error,
                    EmailPackage.eINSTANCE.getErrorType_ReturnCode(),
                    nameMap,
                    false);

            addSwapReferenceCommand(editingDomain,
                    cmd,
                    error.getReturnMessage(),
                    error,
                    EmailPackage.eINSTANCE.getErrorType_ReturnMessage(),
                    nameMap,
                    false);
        }

        // Only return commands if we have any
        if (cmd.getCommandList().size() > 0) {
            return cmd;
        }
        return null;
    }

    /**
     * Append command to the result cmd to swap all the field names given in the
     * map to new names in the given string.
     * 
     * @param editingDomain
     * @param cmd
     * @param str
     * @param owner
     * @param feature
     * @param nameMap
     * @param percentWrap
     *            Whether the field name in the given feature is wrapped with
     *            %...%
     */
    private void addSwapReferenceCommand(EditingDomain editingDomain,
            CompoundCommand cmd, String str, EObject owner,
            EStructuralFeature feature, Map<String, String> nameMap,
            boolean percentWrap) {

        if (str != null && str.length() != 0) {
            String newStr = str;
            Set<Entry<String, String>> eset = nameMap.entrySet();

            // Replace all occurences of all data field names to map

            for (Entry entry : eset) {
                String lookFor;
                String replaceWith;

                String replaceVal = (String) entry.getValue();

                if (percentWrap) {
                    lookFor = "%" + entry.getKey() + "%"; //$NON-NLS-1$ //$NON-NLS-2$

                    if (replaceVal != null && replaceVal.length() > 0) {
                        replaceWith = "%" + replaceVal + "%"; //$NON-NLS-1$ //$NON-NLS-2$
                    } else {
                        replaceWith = ""; //$NON-NLS-1$
                    }

                } else {
                    lookFor = (String) entry.getKey();
                    replaceWith = replaceVal;
                }

                newStr = newStr.replace(lookFor, replaceWith);
            }

            // If the result is different create the command to replace the
            // text.
            if (!newStr.equals(str)) {
                if (newStr.length() == 0) {
                    newStr = null; // Setting to nullstr should delete item.
                }
                cmd.append(SetCommand.create(editingDomain,
                        owner,
                        feature,
                        newStr));
            }
        }
        return;
    }

    @Override
    public Command getDeleteDataFromTransitionCommand(
            EditingDomain editingDomain, Transition transition,
            ProcessRelevantData data) {
        return null;
    }

    /**
     * @see com.tibco.xpd.xpdl2.resolvers.IFieldContextResolverExtension#getDataReferences(com.tibco.xpd.xpdl2.Transition,
     *      java.util.Set)
     * 
     * @param transition
     * @param dataSet
     * @return
     */
    @Override
    public Set<ProcessDataReferenceAndContexts> getDataReferences(
            Transition transition, Set<ProcessRelevantData> dataSet) {
        return null;
    }

}
