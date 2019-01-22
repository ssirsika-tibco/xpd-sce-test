/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.xpdl2.resources;

import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.RollbackException;

import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.Process;

/**
 * Pre-commit listener for when destination environment is changed on a process.
 * 
 * @author aallway
 * @since 24 Aug 2012
 */
public abstract class AbstractDestinationChangedPreCommitContribution extends
        AbstractProcessPreCommitContributor {

    public AbstractDestinationChangedPreCommitContribution() {
    }

    /**
     * @see com.tibco.xpd.xpdl2.resources.IProcessPreCommitContributor#contributeCommand(org.eclipse.emf.transaction.ResourceSetChangeEvent,
     *      java.util.Collection)
     * 
     * @param event
     * @param notifications
     * @return
     * @throws RollbackException
     */
    @Override
    public Command contributeCommand(ResourceSetChangeEvent event,
            Collection<ENotificationImpl> notifications)
            throws RollbackException {

        CompoundCommand cmd = new CompoundCommand();

        for (ENotificationImpl notification : notifications) {

            Process process =
                    (Process) getTypedAncestor(notification, Process.class);
            if (process != null) {

                if (notification.getNewValue() instanceof ExtendedAttribute
                        && Notification.ADD == notification.getEventType()) {
                    if (DestinationUtil.DESTINATION_ATTRIBUTE
                            .equals(((ExtendedAttribute) notification
                                    .getNewValue()).getName())) {

                        String globalDestinationId =
                                DestinationUtil
                                        .getGlobalDestinationType(((ExtendedAttribute) notification
                                                .getNewValue()).getValue());

                        Command c =
                                getDestinationAddedCommand(event.getEditingDomain(),
                                        process,
                                        globalDestinationId);

                        if (c != null) {
                            cmd.append(c);
                        }
                    }

                } else if (notification.getOldValue() instanceof ExtendedAttribute
                        && Notification.REMOVE == notification.getEventType()) {
                    if (DestinationUtil.DESTINATION_ATTRIBUTE
                            .equals(((ExtendedAttribute) notification
                                    .getOldValue()).getName())) {

                        String globalDestinationId =
                                DestinationUtil
                                        .getGlobalDestinationType(((ExtendedAttribute) notification
                                                .getOldValue()).getValue());

                        Command c =
                                getDestinationRemovedCommand(event.getEditingDomain(),
                                        process,
                                        globalDestinationId);

                        if (c != null) {
                            cmd.append(c);
                        }
                    }
                }

            }
        }

        if (!cmd.isEmpty()) {
            return cmd;
        }

        return null;
    }

    /**
     * Allows sub-class to contribute commands when global destination is added
     * to a process.
     * 
     * @param editingDomain
     * @param process
     * @param globalDestinationId
     *            The global destination type id.
     * 
     * @return Command for any changes required when destination environment
     *         added or <code>null</code> if none required.
     */
    protected abstract Command getDestinationAddedCommand(
            EditingDomain editingDomain, Process process,
            String globalDestinationId);

    /**
     * Allows sub-class to contribute commands when global destination is
     * removed from a process.
     * 
     * @param editingDomain
     * @param process
     * @param globalDestinationId
     *            The global destination type id.
     * 
     * @return Command for any changes required when destination environment
     *         removed or <code>null</code> if none required.
     */
    protected abstract Command getDestinationRemovedCommand(
            EditingDomain editingDomain, Process process,
            String globalDestinationId);

}
