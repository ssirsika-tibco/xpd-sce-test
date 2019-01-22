/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.wm.pageflow.component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.n2.resources.util.N2Utils;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.resources.IProcessPreCommitContributor;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Pre-commit listener class to update default business service category with
 * the new package name when the package is renamed
 * 
 * @author bharge
 * @since 3.3 (27 Aug 2010)
 */
public class RenamePackagePreCommitContribution implements
        IProcessPreCommitContributor {

    private boolean consoleLogging = false;

    private EAttribute xpdExtDisplayNameFeature =
            XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName();

    private EAttribute xpdl2NameFeature =
            Xpdl2Package.eINSTANCE.getNamedElement_Name();

    private Logger logger = XpdResourcesPlugin.getDefault().getLogger();

    private class OldNameNewName {
        String oldName = null;

        String newName = null;
    }

    public RenamePackagePreCommitContribution() {
    }

    public Command contributeCommand(ResourceSetChangeEvent event,
            Collection<ENotificationImpl> notifications)
            throws RollbackException {
        CompoundCommand retCmd = null;
        TransactionalEditingDomain editingDomain = event.getEditingDomain();
        Package pckg = null;

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
                if (namedElement instanceof Package) {
                    pckg = (Package) namedElement;

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

            } // 
            // Check for changes in Display name.
            //
            else if (notification.getFeature() == xpdExtDisplayNameFeature) {
                //
                // Notifier SHOULD be a NamedElement, only interested if it is
                // anyway.
                //
                NamedElement namedElement =
                        getInterestingNamedElementNotifier(notification);

                if (namedElement instanceof Package) {
                    pckg = (Package) namedElement;

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

            }

        }

        //
        // Now go thru all our changed display names and modify the token name
        // to match
        //
        if (namedElementDisplayNameChanges != null) {

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
                    // Get and add the change token name command and update the
                    // business service category.
                    // This will throw a RollbackException if something nasty
                    // happens - So if we get a return we can assume everything
                    // is OK.
                    //
                    Command renameTokenCmd =
                            getSetBusinessServiceCategoryCommand(editingDomain,
                                    pckg,
                                    oldAndNewDisplayName.newName,
                                    oldAndNewDisplayName.oldName);
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

            for (Entry<NamedElement, OldNameNewName> entry : namedElementTokenNameChanges
                    .entrySet()) {

                NamedElement namedElement = entry.getKey();

                OldNameNewName oldAndNewTokenName = entry.getValue();

                // 
                // Get refactor command if there is one.
                //

                Command refactorTokenCmd =
                        getSetBusinessServiceCategoryCommand(editingDomain,
                                pckg,
                                oldAndNewTokenName.newName,
                                oldAndNewTokenName.oldName);
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

    /**
     * @param editingDomain
     * @param pckg
     * @param oldName
     * @param oldAndNewDisplayName
     * @return
     */
    private Command getSetBusinessServiceCategoryCommand(
            TransactionalEditingDomain editingDomain, Package pckg,
            String newName, String oldName) {
        CompoundCommand command = new CompoundCommand();
        if (null != pckg) {
            for (Process process : pckg.getProcesses()) {
                if (Xpdl2ModelUtil.isPageflowBusinessService(process)) {
                    /**
                     * if the category is not set or is set to default only then
                     * modify on rename. otherwise if the user has specified his own
                     * category then do not modify
                     */
                    if (isDefaultCategory(process, oldName)) {
                        Set<String> globalDestinationIds =
                                DestinationUtil
                                        .getEnabledGlobalDestinationTypes(process);
                        if (globalDestinationIds
                                .contains(N2Utils.N2_GLOBAL_DESTINATION_ID)) {
                            command
                                    .append(updateBusinessServiceCommand(editingDomain,
                                            process,
                                            newName));
                        }
                    }
                }
            }
            if (!command.isEmpty()) {
                return command;
            }
        }
        return null;
    }

    private NamedElement getInterestingNamedElementNotifier(
            Notification notification) {
        Object notifier = notification.getNotifier();
        if (notifier instanceof Package) {
            return (NamedElement) notifier;
        }
        return null;
    }

    private Command updateBusinessServiceCommand(EditingDomain editingDomain,
            Process pageflowProcess, String newName) {
        CompoundCommand command = new CompoundCommand();
        IProject project = WorkingCopyUtil.getProjectFor(pageflowProcess);
        String category =
                Xpdl2ModelUtil.getBusinessServiceDefaultCategory(project
                        .getName(), newName);
        command.append(Xpdl2ModelUtil
                .getSetOtherAttributeCommand(editingDomain,
                        pageflowProcess,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_BusinessServiceCategory(),
                        category));
        return command;
    }

    /*
     * return true if category is not set or is set to default
     */
    private boolean isDefaultCategory(Process process, String oldName) {
        if (null != process) {
            String category =
                    (String) Xpdl2ModelUtil.getOtherAttribute(process,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_BusinessServiceCategory());
            if (null == category || category.trim().length() == 0) {
                return true;
            }
            if (null != category) {
                IProject project = WorkingCopyUtil.getProjectFor(process);
                String defaultCategory =
                        Xpdl2ModelUtil
                                .getBusinessServiceDefaultCategory(project
                                        .getName(), oldName);
                if (category.equals(defaultCategory)) {
                    return true;
                }
            }
        }
        return false;
    }
    
}
