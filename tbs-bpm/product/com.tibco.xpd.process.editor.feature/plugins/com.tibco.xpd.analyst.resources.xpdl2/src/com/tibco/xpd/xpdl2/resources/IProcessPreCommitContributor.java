/**
 * IProcessPreCommitContributor.java
 *
 * 
 *
 * @author aallway
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.xpdl2.resources;

import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.RollbackException;

/**
 * IProcessPreCommitContributor
 * <p>
 * This extension point allows contribution of extra commands to an EMF model
 * change Transaction just prior to the model changes being committed.
 * <p>
 * The contributor's class will be provided Notification objects for each model
 * element that has changed within the current transaction and may, if it
 * wishes, contribute extra commands.
 */
public interface IProcessPreCommitContributor {

    /**
     * Give contributor the opportunity to return a command to execute prior to
     * EMF transaction commit.
     * 
     * @param event
     *            The original transactionAboutToCommitEvent()
     * @param notifications
     *            Those notifications from the event that are related to process
     *            models.
     * 
     * @return Extra command to execute prior to EMF model changes are commited
     *         or <code>null</code> if no changes are required.
     * 
     * @throws TransactionRolledbackException
     *             Throw this if you wish to force the whole command to roll
     *             back.
     */
    Command contributeCommand(ResourceSetChangeEvent event,
            Collection<ENotificationImpl> notifications)
            throws RollbackException;

}
