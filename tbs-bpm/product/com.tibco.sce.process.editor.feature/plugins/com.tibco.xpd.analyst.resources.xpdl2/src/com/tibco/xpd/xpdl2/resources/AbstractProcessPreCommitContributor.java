/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.xpdl2.resources;

import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;

import com.tibco.xpd.xpdl2.Package;

/**
 * A special version of IProcessPreCommitContributor that can elect to allow
 * command contribution recursion.
 * <p>
 * Normally {@link Xpdl2PreCommitResourceSetListener} will prevent 1st set of
 * commands executed causing a recursion to allow for more commands to be
 * contributed off the back of teh extra contribtued commands and so on.
 * <p>
 * However, if there is a case where one command contributor work on parts of
 * the model affected by another contributor then it may be necessary to recurs
 * (because order of contribution is not guaranteed).
 * <p>
 * In order to allow command contribution recursion the contribution can
 * subclass this class and return true from the allowCOntributionRecursion.
 * <b>It is extremely important that these contributions do not cause a
 * recursion (i.e. contribution1 changes modelElementA if modelElementB changes
 * and contribution2 changes modelElementB if modelElementA changes).</b>
 * 
 * 
 * @author aallway
 * @since 3.2
 */
public abstract class AbstractProcessPreCommitContributor implements
        IProcessPreCommitContributor {

    /**
     * In order to allow command contribution recursion the contribution can
     * subclass this class and return true from the allowCOntributionRecursion.
     * <p>
     * <b>It is extremely important that these contributions do not cause a
     * recursion (i.e. contribution1 changes modelElementA if modelElementB
     * changes and contribution2 changes modelElementB if modelElementA
     * changes).</b>
     * 
     * @param event
     * @param notifications
     * @return true if the contributeCommand() method should be called even
     *         though this reacting to notifications caused by previously
     *         contributed commands.
     */
    protected boolean allowContributionRecursion(ResourceSetChangeEvent event,
            Collection<ENotificationImpl> notifications) {
        return false;
    }

    /**
     * @param notification
     * @return The ancestor of given type from the notifier eobject in the given
     *         notification.
     */
    protected EObject getTypedAncestor(Notification notification,
            Class typeClass) {
        if (notification.getNotifier() instanceof EObject) {
            EObject eo = (EObject) notification.getNotifier();

            while (eo != null) {
                if (typeClass.isInstance(eo)) {
                    return eo;
                }
                eo = eo.eContainer();
            }
        }

        return null;
    }

    /**
     * Append the toAppend command to cmd IF it is not null.
     * <p>
     * If cmd is null then it will be created.
     * 
     * @param cmd
     * @param toAppend
     * 
     * @return cmd
     */
    protected static CompoundCommand appendOrCreateCommand(CompoundCommand cmd,
            Command toAppend) {
        if (toAppend != null) {
            if (cmd == null) {
                cmd = new CompoundCommand();
            }
            cmd.append(toAppend);
        }
        return cmd;
    }

    /**
     * Check if the given set of notificaitons indicatesd that a process package
     * has just been added.
     * 
     * @param notifications
     * 
     * @return Created package if the notifications indicate creation of a new
     *         package or <code>null</code> if there is no create package
     *         notification.
     */
    protected static Package isCreateNewPackageNotification(
            Collection<ENotificationImpl> notifications) {
        return ProcessPackageAddedNotificationCommand
                .isPackageCreatedNotification(notifications);
    }

    /**
     * Output the event notification detail for debug purposes.
     * 
     * @param notification
     */
    public static void outputNotfication(Notification notification) {
        System.out.println("  Notification: "); //$NON-NLS-1$
        System.out
                .println("  ----------------------------------------------------------------------"); //$NON-NLS-1$
        System.out.println("        Type: " + getEventTypeName(notification)); //$NON-NLS-1$
        Object notifier = notification.getNotifier();
        if (notifier != null) {
            System.out
                    .println("    Notifier: " + notifier.getClass().getSimpleName()); //$NON-NLS-1$
        }

        EStructuralFeature eStructuralFeature =
                (EStructuralFeature) notification.getFeature();
        if (eStructuralFeature != null) {
            System.out.println("     Feature: " + eStructuralFeature.getName()); //$NON-NLS-1$
        }
        System.out.println("    OldValue: " + notification.getOldValue()); //$NON-NLS-1$
        System.out.println("    NewValue: " + notification.getNewValue()); //$NON-NLS-1$
        System.out
                .println("  ----------------------------------------------------------------------\n"); //$NON-NLS-1$
    }

    public static String getEventTypeName(Notification notification) {
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
}
