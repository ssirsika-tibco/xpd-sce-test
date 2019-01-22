/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.bx.validation.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.ConfigurationType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.DefinitionType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailFactory;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailPackage;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.FromType;
import com.tibco.xpd.implementer.nativeservices.emailservice.utilities.EmailExtUtil;
import com.tibco.xpd.implementer.nativeservices.utilities.TaskServiceExtUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;

/**
 * Set an E-Mail service task's 'From' configuration attribute to 'Custom'.
 * Provision added to generate the <code>FromType</code>'s parent
 * <code>EmailType</code> structure if one is not already present.
 * 
 * @author patkinso
 * @since 4 Apr 2012
 */
public class SetEmailFromCustomConfig extends AbstractWorkingCopyResolution {

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     * 
     * @param editingDomain
     * @param target
     * @param marker
     * @return
     * @throws ResolutionException
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {

        CompoundCommand compoundCmd =
                new CompoundCommand(
                        Messages.SetEmailTaskFromConfigurationToCustom_label);

        Activity activity =
                (target instanceof Activity) ? (Activity) target : null;

        if (activity != null) {
            Task task = (Task) activity.getImplementation();
            if (task != null) {
                TaskService tsvc = task.getTaskService();
                if (tsvc != null) {

                    EmailType emailType =
                            getOrCreateEmailType(editingDomain,
                                    compoundCmd,
                                    tsvc);

                    DefinitionType definitionType =
                            getOrCreateDefinitionType(editingDomain,
                                    compoundCmd,
                                    emailType);

                    FromType fromType =
                            getOrCreateFromType(editingDomain,
                                    compoundCmd,
                                    definitionType);

                    compoundCmd.append(SetCommand.create(editingDomain,
                            fromType,
                            EmailPackage.eINSTANCE.getFromType_Configuration(), // FromType_Value()
                            ConfigurationType.CUSTOM_LITERAL));
                }
            }
        }

        return compoundCmd;

    }

    /**
     * @param editingDomain
     * @param compoundCmd
     * @param definitionType
     * @return <code>FromType</code> structure in which to set its
     *         'Configuration' attribute to 'Custom'
     */
    private FromType getOrCreateFromType(EditingDomain editingDomain,
            CompoundCommand compoundCmd, DefinitionType definitionType) {

        FromType fromType = definitionType.getFrom();
        if (fromType == null) {

            fromType = EmailFactory.eINSTANCE.createFromType();

            Command cmd =
                    SetCommand.create(editingDomain,
                            definitionType,
                            EmailPackage.eINSTANCE.getFromType(),
                            fromType);
            compoundCmd.append(cmd);
        }

        return fromType;
    }

    /**
     * @param editingDomain
     * @param compoundCmd
     * @param emailType
     * @return parent <code>DefinitionType</code> structure to contain the
     *         'Custom' <code>EmailType</code>
     */
    private DefinitionType getOrCreateDefinitionType(
            EditingDomain editingDomain, CompoundCommand compoundCmd,
            EmailType emailType) {

        DefinitionType defType = emailType.getDefinition();
        if (defType == null) {

            defType = EmailFactory.eINSTANCE.createDefinitionType();

            Command cmd =
                    SetCommand.create(editingDomain,
                            emailType,
                            EmailPackage.eINSTANCE.getDefinitionType(),
                            defType);
            compoundCmd.append(cmd);
        }

        return defType;
    }

    /**
     * @param editingDomain
     * @param compoundCmd
     * @param tsvc
     * @return parent <code>EmailType</code> structure to contain the 'Custom'
     *         <code>EmailType</code>
     */
    private EmailType getOrCreateEmailType(EditingDomain editingDomain,
            CompoundCommand compoundCmd, TaskService tsvc) {

        EmailType emailType = EmailExtUtil.getEmailExtension(tsvc);
        if (emailType == null) {

            Command cmd =
                    TaskServiceExtUtil.addExtendedModel(editingDomain,
                            tsvc,
                            EmailPackage.eINSTANCE.getDocumentRoot_Email(),
                            EmailFactory.eINSTANCE.createEmailType());
            compoundCmd.append(cmd);
        }

        return emailType;
    }

}
