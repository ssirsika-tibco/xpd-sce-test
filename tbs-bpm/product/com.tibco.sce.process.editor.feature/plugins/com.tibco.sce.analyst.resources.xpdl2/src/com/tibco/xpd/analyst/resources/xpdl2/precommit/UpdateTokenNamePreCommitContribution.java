/**
 * UpdateTokenNamePreCommitContribution.java
 *
 * 
 *
 * @author aallway
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.precommit;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.commands.RenameFieldOrParamCommand;
import com.tibco.xpd.xpdl2.commands.RenameParticipantCommand;
import com.tibco.xpd.xpdl2.resources.IProcessPreCommitContributor;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * UpdateTokenNamePreCommitContribution
 * <p>
 * Contribution to the
 * <code>com.tibco.xpd.xpdl2.resources.processPreCommitCommandContributor</code>
 * extension point that deals with the automatic update of the TOKEN NAME (the
 * xpdl2 Name attribute)of an xpdl2:NamedElement-based object when the
 * xpdExtDisplayName attribute value changes.
 */
public class UpdateTokenNamePreCommitContribution implements
        IProcessPreCommitContributor {

    private class OldNameNewName {
        String oldName = null;

        String newName = null;
    }

    private static final InterfaceMethod StartMethod = null;

    private EAttribute xpdExtDisplayNameFeature = XpdExtensionPackage.eINSTANCE
            .getDocumentRoot_DisplayName();

    private EAttribute xpdl2NameFeature = Xpdl2Package.eINSTANCE
            .getNamedElement_Name();

    private Logger logger = XpdResourcesPlugin.getDefault().getLogger();

    private boolean consoleLogging = false;

    /**
     * 
     */
    public UpdateTokenNamePreCommitContribution() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.xpdl2.resources.IProcessPreCommitContributor#contributeCommand
     * (org.eclipse.emf.transaction.ResourceSetChangeEvent,
     * java.util.Collection)
     */
    @Override
    public Command contributeCommand(ResourceSetChangeEvent event,
            Collection<ENotificationImpl> notifications)
            throws RollbackException {
        CompoundCommand retCmd = null;

        if (consoleLogging) {
            System.out
                    .println("==> " + this.getClass().getSimpleName() + ".contributeCommand()"); //$NON-NLS-1$//$NON-NLS-2$
        }

        //
        // Go thru the notifications looking for changes to xpdExt:DisplayName
        // and/or xpdl2 Name (token name)
        //
        Map<NamedElement, OldNameNewName> namedElementDisplayNameChanges = null;
        Map<NamedElement, OldNameNewName> namedElementTokenNameChanges = null;

        for (ENotificationImpl notification : notifications) {

            //
            // Check for changes in xpdl2 Name attribute.
            //
            if (notification.getFeature() == xpdl2NameFeature) {
                //
                // Notifier SHOULD be a NamedElement, only interested if it is
                // anyway.
                //
                NamedElement namedElement =
                        getInterestingNamedElementNotifier(notification);
                if (namedElement != null) {

                    int eventType = notification.getEventType();
                    if (eventType == Notification.UNSET
                            || eventType == Notification.SET) {

                        //
                        // Just in case name is set by first unsetting and then
                        // resetting to new value we need to keep track of the
                        // oldvalue from theUNSET command which will be first in
                        // list of notifications.
                        //
                        OldNameNewName oldAndNewDisplayName = null;

                        //
                        // Get or create the old value new value pair.
                        //
                        if (namedElementTokenNameChanges == null) {
                            namedElementTokenNameChanges =
                                    new HashMap<NamedElement, OldNameNewName>();

                        } else {
                            oldAndNewDisplayName =
                                    namedElementTokenNameChanges
                                            .get(namedElement);
                        }

                        if (oldAndNewDisplayName == null) {
                            oldAndNewDisplayName = new OldNameNewName();
                            namedElementTokenNameChanges.put(namedElement,
                                    oldAndNewDisplayName);
                        }

                        if (eventType == Notification.UNSET) {
                            // Take the old value from the UNSET
                            oldAndNewDisplayName.oldName =
                                    notification.getOldStringValue();

                        } else {
                            // Take the new value from the SET
                            oldAndNewDisplayName.newName =
                                    notification.getNewStringValue();

                            // Just in case some one changes things so that we
                            // go straight from old to new value, if the
                            // oldValue ion the SET notification is not null
                            // then save the old value too.
                            String oldValue = notification.getOldStringValue();
                            if (oldValue != null) {
                                oldAndNewDisplayName.oldName = oldValue;
                            }
                        }
                    }
                }
            }
            //
            // Check for changes in Display name.
            //
            else if (notification.getFeature() == xpdExtDisplayNameFeature) {
                //
                // Notifier SHOULD be a NamedElement, only interested if it is
                // anyway.
                //
                NamedElement namedElement =
                        getInterestingNamedElementNotifier(notification);
                if (namedElement != null) {

                    int eventType = notification.getEventType();
                    if (eventType == Notification.UNSET
                            || eventType == Notification.SET) {

                        //
                        // Unfortunately xpdExt:DisplayName, being a ##other
                        // attribute in a feature map tends to be Unset to null
                        // and then reset to new value.
                        //
                        // So we need to keep track of the oldvalue from the
                        // UNSET command which will be first in list of
                        // notifications.
                        //
                        OldNameNewName oldAndNewDisplayName = null;

                        //
                        // Get or create the old value new value pair.
                        //
                        if (namedElementDisplayNameChanges == null) {
                            namedElementDisplayNameChanges =
                                    new HashMap<NamedElement, OldNameNewName>();

                        } else {
                            oldAndNewDisplayName =
                                    namedElementDisplayNameChanges
                                            .get(namedElement);
                        }

                        if (oldAndNewDisplayName == null) {
                            oldAndNewDisplayName = new OldNameNewName();
                            namedElementDisplayNameChanges.put(namedElement,
                                    oldAndNewDisplayName);
                        }

                        if (eventType == Notification.UNSET) {
                            // Take the old value from the UNSET
                            oldAndNewDisplayName.oldName =
                                    notification.getOldStringValue();

                        } else {
                            // Take the new value from the SET
                            oldAndNewDisplayName.newName =
                                    notification.getNewStringValue();

                            // Just in case some one changes things so that we
                            // go straight from old to new value, if the
                            // oldValue ion the SET notification is not null
                            // then save the old value too.
                            String oldValue = notification.getOldStringValue();
                            if (oldValue != null) {
                                oldAndNewDisplayName.oldName = oldValue;
                            }
                        }

                    }
                }
            } else {
                if (notification.getFeature() == Xpdl2Package.eINSTANCE
                        .getNamedElement_Name()) {
                    System.out
                            .println(this.getClass()
                                    + ".contributeCommand() - Something is setting the Token Name of a named element other than this class...\nThis should only be valid from property sheet Name control itself."); //$NON-NLS-1$
                }
            }
        }

        //
        // Now go thru all our changed display names and modify the token name
        // to match
        //
        if (namedElementDisplayNameChanges != null) {
            TransactionalEditingDomain editingDomain = event.getEditingDomain();

            for (Entry<NamedElement, OldNameNewName> entry : namedElementDisplayNameChanges
                    .entrySet()) {

                NamedElement namedElement = entry.getKey();

                //
                // We will only modify the Token Name from the new display name
                // IF the Token Name has not also been set in the same
                // transaction!
                //
                if (namedElementTokenNameChanges == null
                        || !namedElementTokenNameChanges
                                .containsKey(namedElement)) {

                    OldNameNewName oldAndNewDisplayName = entry.getValue();

                    //
                    // Get and add the change token name command.
                    // This will throw a RollbackException if something nasty
                    // happens - So if we get a return we can assume everything
                    // is OK.
                    //
                    Command renameTokenCmd =
                            getSetTokenNameFromDisplayNameCommand(editingDomain,
                                    namedElement,
                                    oldAndNewDisplayName);
                    if (renameTokenCmd != null) {
                        if (retCmd == null) {
                            retCmd = new CompoundCommand();
                        }
                        retCmd.append(renameTokenCmd);
                    }
                } else {
                    if (consoleLogging) {
                        System.out
                                .println("    NamedElement(Id=" //$NON-NLS-1$
                                        + namedElement.getId()
                                        + "): NOT Auto-assigning new token name because token name is being set in same command transaction."); //$NON-NLS-1$

                    }
                }
            }
        }

        //
        // Now go thru any DIRECT changes to the xpdl2 Name attribute i.e. the
        // Token Name itself.
        //
        if (namedElementTokenNameChanges != null) {
            TransactionalEditingDomain editingDomain = event.getEditingDomain();

            for (Entry<NamedElement, OldNameNewName> entry : namedElementTokenNameChanges
                    .entrySet()) {

                NamedElement namedElement = entry.getKey();

                OldNameNewName oldAndNewTokenName = entry.getValue();

                //
                // Get refactor command if there is one.
                //

                Command refactorTokenCmd =
                        getRenameAndRefactorTokenNameCommand(editingDomain,
                                namedElement,
                                oldAndNewTokenName.oldName,
                                oldAndNewTokenName.newName,
                                false);
                if (refactorTokenCmd != null) {
                    if (consoleLogging) {
                        System.out
                                .println("    NamedElement(Id=" //$NON-NLS-1$
                                        + namedElement.getId()
                                        + "): Detected Direct Token Name change - performing necessary refactoring (from '" //$NON-NLS-1$
                                        + oldAndNewTokenName.oldName + "' to '" //$NON-NLS-1$
                                        + oldAndNewTokenName.newName + "')"); //$NON-NLS-1$

                    }

                    //
                    // The token is being renamed according to new display name
                    //
                    if (!refactorTokenCmd.canExecute()) {
                        //
                        // If the command cannot be executed then log an
                        // error and throw exception to rollback original
                        // command.
                        //
                        String errorStr =
                                "    NamedElement(Id=" //$NON-NLS-1$
                                        + namedElement.getId()
                                        + "): Refactor because of Direct Token Name change Command can't execute (token name changed from '" //$NON-NLS-1$
                                        + oldAndNewTokenName.oldName + "' to '" //$NON-NLS-1$
                                        + oldAndNewTokenName.newName + "')"; //$NON-NLS-1$

                        if (consoleLogging) {
                            System.err.println(errorStr);
                        }

                        logger.error(errorStr);

                        throw new RollbackException(new Status(IStatus.ERROR,
                                Xpdl2ResourcesPlugin.PLUGIN_ID, errorStr));

                    } else {
                        //
                        // We have a refactor on rename token name command so
                        // append it to the overall command and carry on.
                        //
                        if (retCmd == null) {
                            retCmd = new CompoundCommand();
                        }
                        retCmd.append(refactorTokenCmd);

                    }

                } else {
                    if (consoleLogging) {
                        System.out
                                .println("    NamedElement(Id=" //$NON-NLS-1$
                                        + namedElement.getId()
                                        + "): Detected Direct Token Name change - refactoring (from '" //$NON-NLS-1$
                                        + oldAndNewTokenName.oldName
                                        + "' to '" //$NON-NLS-1$
                                        + oldAndNewTokenName.newName
                                        + "') - NO refactoring necessary"); //$NON-NLS-1$

                    }

                }

            }
        }

        if (consoleLogging) {
            System.out
                    .println("<== " + this.getClass().getSimpleName() + ".contributeCommand()"); //$NON-NLS-1$//$NON-NLS-2$
        }

        return retCmd;
    }

    private NamedElement getInterestingNamedElementNotifier(
            Notification notification) {
        Object notifier = notification.getNotifier();
        if (notifier instanceof NamedElement) {
            // if (!(notifier instanceof Participant)) {
            return (NamedElement) notifier;
            // }
        }
        return null;
    }

    /**
     * If the Token Name for the given named element needs to change because of
     * a change to the display name then return the command to do so.
     * 
     * @param editingDomain
     * @param namedElement
     * @param oldAndNewDisplayName
     * 
     * @return Command to reset the token name according to the new display name
     *         (including any refactoring off of the back of the token name
     *         change) or <code>null</code> if no change to token name is
     *         necessary.
     * @throws RollbackException
     *             Problem renaming (or refactoring because of rename) token
     *             name
     */
    private Command getSetTokenNameFromDisplayNameCommand(
            TransactionalEditingDomain editingDomain,
            NamedElement namedElement, OldNameNewName oldAndNewDisplayName)
            throws RollbackException {
        Command renameTokenCmd = null;

        /*
         * Do not attempt to handle things when the target object is in a read
         * only resource.
         */
        if (editingDomain.isReadOnly(namedElement.eResource())) {
            return null;
        }

        /*
         * Grab the various versions of names that we will need.
         */
        String tokenNameForOldDisplayName =
                getTokenName(namedElement, oldAndNewDisplayName.oldName);

        String currentTokenName = namedElement.getName();
        if (currentTokenName == null) {
            currentTokenName = ""; //$NON-NLS-1$
        }

        //
        // If then currentTokenName value is correct tokenised name
        // for the old (pre-command exec) value then we can assume that
        // the user has NOT reset the token name manually.
        //
        // Therefore when the displayName changes we can change the
        // tokenName to match it.
        //
        // - Otherwise there is nothing to do.
        if (currentTokenName.equals(tokenNameForOldDisplayName)) {
            String tokenNameForNewDisplayName =
                    getTokenName(namedElement, oldAndNewDisplayName.newName);
            // namedElement instanceof Participant ?
            // oldAndNewDisplayName.newName
            // : getTokenName(oldAndNewDisplayName.newName);

            //
            // Also only need to bother if the token name is also
            // different from the existing one.
            //
            if (!currentTokenName.equals(tokenNameForNewDisplayName)) {
                String label =
                        "    NamedElement(Id=" //$NON-NLS-1$
                                + namedElement.getId()
                                + "): Auto-assign new token name '" //$NON-NLS-1$
                                + tokenNameForNewDisplayName
                                + "' for new display name '" //$NON-NLS-1$
                                + oldAndNewDisplayName.newName + "'"; //$NON-NLS-1$

                if (consoleLogging) {
                    System.out.println(label);
                }

                //
                // Get and add the change token name command.
                //
                renameTokenCmd =
                        getRenameAndRefactorTokenNameCommand(editingDomain,
                                namedElement,
                                currentTokenName,
                                tokenNameForNewDisplayName,
                                true);
                //
                // The token is being renamed according to new display name
                //
                if (renameTokenCmd != null && !renameTokenCmd.canExecute()) {
                    //
                    // If the command cannot be executed then log an
                    // error and throw exception to rollback original command.
                    //

                    String errorStr =
                            "    NamedElement(Id=" //$NON-NLS-1$
                                    + namedElement.getId()
                                    + "): Rename Token Command can't execute for rename token from '" //$NON-NLS-1$
                                    + currentTokenName + "' to '" //$NON-NLS-1$
                                    + tokenNameForNewDisplayName + "'"; //$NON-NLS-1$

                    if (consoleLogging) {
                        System.err.println(errorStr);
                    }

                    logger.error(errorStr);

                    throw new RollbackException(new Status(IStatus.ERROR,
                            Xpdl2ResourcesPlugin.PLUGIN_ID, errorStr));

                }

            } else {
                if (consoleLogging) {
                    System.out
                            .println("    NamedElement(Id=" //$NON-NLS-1$
                                    + namedElement.getId()
                                    + "): NOT Auto-Assigning new Token Name. Token name is same for new and old display name"); //$NON-NLS-1$
                }
            }

        } else {
            if (consoleLogging) {
                System.out
                        .println("    NamedElement(Id=" //$NON-NLS-1$
                                + namedElement.getId()
                                + "): NOT Auto-Assigning new Token Name (appears to have been manually set) - current token name='" //$NON-NLS-1$
                                + currentTokenName
                                + "' which does not match for original display name '" //$NON-NLS-1$
                                + oldAndNewDisplayName.oldName + "'"); //$NON-NLS-1$
            }
        }

        return renameTokenCmd;
    }

    /**
     * Get the change token name command for the given NamedElement
     * 
     * @param editingDomain
     * @param namedElement
     * @param newTokenName
     * 
     * @return The command or null if nothing to do.
     */
    private Command getRenameAndRefactorTokenNameCommand(
            TransactionalEditingDomain editingDomain,
            NamedElement namedElement, String oldTokenName,
            String newTokenName, boolean resetFromDisplayName) {

        // ALSO, think it is a bad idea to do refactor when new token name
        // may have changed to "" if so, just change the token name by
        // falling thru to normal behaviour below.
        if (newTokenName != null && newTokenName.length() > 0
                && !newTokenName.equals(oldTokenName)) {
            //
            // Data Fields / Formal Parameter refactor (change references by
            // name).
            //
            if (namedElement instanceof ProcessRelevantData) {
                return getRefactorFieldOrParamNameCommand(editingDomain,
                        (ProcessRelevantData) namedElement,
                        oldTokenName,
                        newTokenName,
                        resetFromDisplayName);
            }
            //
            // Process Interface Name (update Port Type Name in Message
            // Start/Intermediate Methods.
            //
            else if (namedElement instanceof ProcessInterface) {
                return getRefactorProcessInterfaceNameCommand(editingDomain,
                        (ProcessInterface) namedElement,
                        oldTokenName,
                        newTokenName,
                        resetFromDisplayName);
            }
            //
            // Process Interface Message Method
            //
            else if (namedElement instanceof InterfaceMethod) {
                return getRefactorInterfaceMethodNameCommand(editingDomain,
                        (InterfaceMethod) namedElement,
                        oldTokenName,
                        newTokenName,
                        resetFromDisplayName);

            } else if (namedElement instanceof Participant) {
                return getRefactorParticipantNameCommand(editingDomain,
                        (Participant) namedElement,
                        oldTokenName,
                        newTokenName,
                        resetFromDisplayName);
            }
            //
            // Process Interface Name (update Port Type Name in Message
            // Start/Intermediate Methods.
            //
            else if (namedElement instanceof Process) {
                return getRefactorProcessNameCommand(editingDomain,
                        (Process) namedElement,
                        oldTokenName,
                        newTokenName,
                        resetFromDisplayName);
            }
        }

        //
        // By default just set the token name if doing a set token name because
        // display name changed.
        //
        // Otherwise (if responding to a direct token name change) then
        // do nothing
        //
        if (resetFromDisplayName) {
            return SetCommand.create(editingDomain,
                    namedElement,
                    Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                    newTokenName);
        }

        return null;
    }

    /**
     * @param editingDomain
     * @param namedElement
     * @param oldTokenName
     * @param newTokenName
     * @param resetFromDisplayName
     * @return
     */
    private Command getRefactorProcessNameCommand(
            TransactionalEditingDomain editingDomain, Process proc,
            String oldTokenName, String newTokenName,
            boolean resetFromDisplayName) {
        Collection<Activity> activitiesInProc =
                Xpdl2ModelUtil.getAllActivitiesInProc(proc);

        CompoundCommand cmd = new CompoundCommand();

        if (resetFromDisplayName) {
            // If we're auto-assigning token name from changed display name...
            cmd.append(SetCommand.create(editingDomain,
                    proc,
                    Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                    newTokenName));
        }

        for (Activity activity : activitiesInProc) {
            if (!(ProcessInterfaceUtil.isEventImplemented(activity))
                    && Xpdl2ModelUtil.isGeneratedRequestActivity(activity)) {
                PortTypeOperation portTypeOp =
                        Xpdl2ModelUtil.getPortTypeOperation(activity);
                if (portTypeOp != null) {
                    cmd.append(updatePortTypeName(editingDomain,
                            portTypeOp,
                            newTokenName));
                }
            }

        }
        if (!cmd.isEmpty()) {
            return cmd;
        }
        return null;
    }

    /**
     * Get command to rename / refactor the field / param
     * 
     * @param editingDomain
     * @param fieldOrParam
     * @param oldTokenName
     * @param newTokenName
     * @param resetFromDisplayName
     * 
     * @return The command or null if nothing to do.
     */
    private Command getRefactorFieldOrParamNameCommand(
            TransactionalEditingDomain editingDomain,
            ProcessRelevantData fieldOrParam, String oldTokenName,
            String newTokenName, boolean resetFromDisplayName) {
        // Changing name of formal parameter or data field has to
        // refactor references to it.
        //
        // NOTE that this will fail if there are duplicate names in the
        // same scope - WHICH I THINK IS WHAT WE WILL WANT! IF you allow
        // rename of this field to same as another field, then user
        // realises their mistake and tries to rename it to something else, the
        // 2nd
        // rename will refactor references to the original field with the
        // duplicated field name too!

        // If doing a set token name because display name changed then
        // change name and refactor.
        //
        // Otherwise (if responding to a direct token name change) then
        // only do refactor.
        boolean refactorOnly = !resetFromDisplayName;

        return RenameFieldOrParamCommand.create(editingDomain,
                fieldOrParam,
                fieldOrParam.eContainer(),
                oldTokenName,
                newTokenName,
                false,
                refactorOnly);
    }

    private Command getRefactorParticipantNameCommand(
            TransactionalEditingDomain editingDomain, Participant participant,
            String oldTokenName, String newTokenName,
            boolean resetFromDisplayName) {
        boolean refactorOnly = !resetFromDisplayName;
        return RenameParticipantCommand.create(editingDomain,
                participant,
                participant.eContainer(),
                oldTokenName,
                newTokenName,
                false,
                refactorOnly);
    }

    /**
     * Get command to rename / refactor the Process Interface i.e. set the port
     * type name in the auto generated message info within message type methods
     * in the interface
     * 
     * @param editingDomain
     * @param processInterface
     * @param oldTokenName
     * @param newTokenName
     * @param resetFromDisplayName
     * 
     * @return The command or null if nothing to do.
     */
    private Command getRefactorProcessInterfaceNameCommand(
            TransactionalEditingDomain editingDomain,
            ProcessInterface processInterface, String oldTokenName,
            String newTokenName, boolean resetFromDisplayName) {

        CompoundCommand retCmd = new CompoundCommand();

        if (resetFromDisplayName) {
            // If we're auto-assigning token name from changed display name...
            retCmd.append(SetCommand.create(editingDomain,
                    processInterface,
                    Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                    newTokenName));
        }

        List<StartMethod> startMethods = processInterface.getStartMethods();
        for (StartMethod startMethod : startMethods) {
            if (startMethod.getTrigger() == TriggerType.MESSAGE_LITERAL) {
                PortTypeOperation portTypeOperation =
                        getPortTypeOperation(startMethod);
                if (portTypeOperation != null) {
                    retCmd.append(updatePortTypeName(editingDomain,
                            portTypeOperation,
                            newTokenName));
                }
            }
        }

        List<IntermediateMethod> intermediateMethods =
                processInterface.getIntermediateMethods();
        for (IntermediateMethod intermediateMethod : intermediateMethods) {
            if (intermediateMethod.getTrigger() == TriggerType.MESSAGE_LITERAL) {
                PortTypeOperation portTypeOperation =
                        getPortTypeOperation(intermediateMethod);
                if (portTypeOperation != null) {
                    retCmd.append(updatePortTypeName(editingDomain,
                            portTypeOperation,
                            newTokenName));
                }
            }
        }

        retCmd.setLabel(Messages.RenameAction_RenameProcessInterface_menu);

        if (!retCmd.isEmpty()) {
            return retCmd;
        }

        return null;
    }

    /**
     * @param startMethod
     */
    private PortTypeOperation getPortTypeOperation(
            InterfaceMethod interfaceMethod) {
        TriggerResultMessage triggerResultMessage =
                interfaceMethod.getTriggerResultMessage();
        Object otherElement =
                Xpdl2ModelUtil.getOtherElement(triggerResultMessage,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_PortTypeOperation());
        if (otherElement instanceof PortTypeOperation) {
            return (PortTypeOperation) otherElement;
        }
        return null;
    }

    /**
     * Get command to rename / refactor the Process Interface Methods i.e. set
     * the operation name in the auto generated message info within message type
     * methods in the interface
     * 
     * @param editingDomain
     * @param interfaceMethod
     * @param oldTokenName
     * @param newTokenName
     * @param resetFromDisplayName
     * 
     * @return The command or null if nothing to do.
     */
    private Command getRefactorInterfaceMethodNameCommand(
            TransactionalEditingDomain editingDomain,
            InterfaceMethod interfaceMethod, String oldTokenName,
            String newTokenName, boolean resetFromDisplayName) {
        CompoundCommand retCmd = new CompoundCommand();

        if (resetFromDisplayName) {
            // If we're auto-assigning token name from changed display name...
            retCmd.append(SetCommand.create(editingDomain,
                    interfaceMethod,
                    Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                    newTokenName));
        }

        if (interfaceMethod.getTrigger() == TriggerType.MESSAGE_LITERAL) {
            retCmd.append(updatePortTypeOperationName(editingDomain,
                    interfaceMethod,
                    newTokenName));

            if (interfaceMethod == StartMethod) {
                retCmd.setLabel(Messages.RenameAction_RenameStartMethod_menu);
            } else {
                retCmd.setLabel(Messages.RenameAction_RenameIntermediateMethod_menu);
            }
        }

        if (!retCmd.isEmpty()) {
            return retCmd;
        }

        return null;
    }

    /**
     * Get the internal token name for the given display name.
     * 
     * @param displayName
     * @return guaranteed to be non-null - but maybe nullstr.
     */
    private String getTokenName(NamedElement namedElement, String displayName) {
        String token = ""; //$NON-NLS-1$
        if (displayName != null) {
            if (namedElement instanceof Participant) {
                token = displayName;
            } else if (namedElement instanceof ProcessRelevantData
                    || namedElement instanceof TypeDeclaration) {
                token = NameUtil.getInternalName(displayName, true);
            } else {
                token = NameUtil.getInternalName(displayName, false);
            }
        }
        return token;
    }

    private String getEventType(ENotificationImpl notification) {
        switch (notification.getEventType()) {
        case Notification.CREATE:
            return "CREATE"; //$NON-NLS-1$
        case Notification.SET:
            return "SET"; //$NON-NLS-1$
        case Notification.UNSET:
            return "UNSET"; //$NON-NLS-1$
        case Notification.ADD:
            return "ADD"; //$NON-NLS-1$
        case Notification.REMOVE:
            return "REMOVE"; //$NON-NLS-1$
        case Notification.ADD_MANY:
            return "ADD_MANY"; //$NON-NLS-1$
        case Notification.REMOVE_MANY:
            return "REMOVE_MANY"; //$NON-NLS-1$
        case Notification.MOVE:
            return "MOVE"; //$NON-NLS-1$
        case Notification.REMOVING_ADAPTER:
            return "REMOVING_ADAPTER"; //$NON-NLS-1$
        case Notification.RESOLVE:
            return "RESOLVE"; //$NON-NLS-1$
        case Notification.EVENT_TYPE_COUNT:
            return "EVENT_TYPE_COUNT"; //$NON-NLS-1$
        }
        return notification.getEventType() + "<Unknown>"; //$NON-NLS-1$

    }

    /**
     * Update the message interface method message port operation name from the
     * new token (xpdl2) name.
     * 
     * @param editingDomain
     * @param interfaceMethod
     * @param tokenName
     * @return
     */
    private Command updatePortTypeOperationName(EditingDomain editingDomain,
            InterfaceMethod interfaceMethod, String tokenName) {

        TriggerResultMessage triggerResultMessage =
                interfaceMethod.getTriggerResultMessage();

        if (triggerResultMessage != null) {
            PortTypeOperation porttypeOperation =
                    (PortTypeOperation) Xpdl2ModelUtil
                            .getOtherElement(triggerResultMessage,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_PortTypeOperation());
            if (porttypeOperation != null) {
                /*
                 * XPD-5911: if an activity name has leading digit(s) then
                 * prefix with underscore
                 */
                if (tokenName != null && Character.isDigit(tokenName.charAt(0))) {

                    tokenName = "_" + tokenName; //$NON-NLS-1$
                }

                return SetCommand.create(editingDomain,
                        porttypeOperation,
                        XpdExtensionPackage.eINSTANCE
                                .getPortTypeOperation_OperationName(),
                        tokenName);
            }
        }
        return null;
    }

    /**
     * Update the port type name in an interface message method from the new
     * process interface token name.
     * 
     * @param editingDomain
     * @param interfaceMethod
     * @param tokenName
     * @return
     */
    private Command updatePortTypeName(EditingDomain editingDomain,
            PortTypeOperation portTypeOperation, String tokenName) {

        if (portTypeOperation != null) {
            /*
             * XPD-5911: if a process name has leading digit(s) then prefix with
             * underscore
             */
            if (tokenName != null && Character.isDigit(tokenName.charAt(0))) {

                tokenName = "_" + tokenName; //$NON-NLS-1$
            }

            return SetCommand.create(editingDomain,
                    portTypeOperation,
                    XpdExtensionPackage.eINSTANCE
                            .getPortTypeOperation_PortTypeName(),
                    tokenName);
        }
        return null;
    }

}
