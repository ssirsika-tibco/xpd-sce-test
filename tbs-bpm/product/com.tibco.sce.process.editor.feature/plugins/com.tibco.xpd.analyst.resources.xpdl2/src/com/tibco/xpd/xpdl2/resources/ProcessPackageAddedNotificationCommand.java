/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.xpdl2.resources;

import java.util.Collection;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * This command can be executed on a newly created package so that pre-commit
 * listeners can detect the notifications to tell that a package has been
 * created.
 * 
 * @author aallway
 * @since 3.3 (5 Jul 2010)
 */
public class ProcessPackageAddedNotificationCommand extends CompoundCommand {
    private EditingDomain editingDomain;

    private Package pkg;

    public static String PACKAGE_ADDED_EXT_ATTR_NAME = "_PackageAdded_"; //$NON-NLS-1$

    /**
     * @param editingDomain
     * @param pkg
     */
    public ProcessPackageAddedNotificationCommand(EditingDomain editingDomain,
            Package pkg) {
        super();
        this.editingDomain = editingDomain;
        this.pkg = pkg;
    }

    @Override
    public void execute() {
        super.execute();

        /*
         * To notify pre-commit listeners of creation of a new project then we
         * can simply add an ExtendedAttribute
         */
        ExtendedAttribute extAttr =
                Xpdl2Factory.eINSTANCE.createExtendedAttribute();
        extAttr.setName(PACKAGE_ADDED_EXT_ATTR_NAME);

        this.appendAndExecute(AddCommand.create(editingDomain,
                pkg,
                Xpdl2Package.eINSTANCE
                        .getExtendedAttributesContainer_ExtendedAttributes(),
                extAttr));
        this.appendAndExecute(RemoveCommand.create(editingDomain, extAttr));

        return;
    }

    @Override
    public boolean canExecute() {
        return true;
    }

    /**
     * Check if the notification list is for a command that includes this
     * command's operations that notify listeners of an creation of a new
     * package
     * 
     * @param notifications
     * @return Created package if the notifications indicate creation of a new
     *         package or <code>null</code> if there is no create package
     *         notification.
     */
    public static Package isPackageCreatedNotification(
            Collection<ENotificationImpl> notifications) {
        for (ENotificationImpl notification : notifications) {
            if (Notification.ADD == notification.getEventType()) {
                if (notification.getNotifier() instanceof Package) {
                    if (notification.getNewValue() instanceof ExtendedAttribute) {
                        ExtendedAttribute extAttr =
                                (ExtendedAttribute) notification.getNewValue();
                        if (PACKAGE_ADDED_EXT_ATTR_NAME.equals(extAttr
                                .getName())) {
                            return (Package) notification.getNotifier();
                        }
                    }
                }
            }
        }
        return null;
    }
}
