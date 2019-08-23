/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */
package com.tibco.xpd.resources.wc;

import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.ResourceSetListener;
import org.eclipse.emf.transaction.ResourceSetListenerImpl;
import org.eclipse.emf.transaction.RollbackException;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;


/**
 * Prevents modifications of read only working copies.
 * 
 * @author jarciuch
 * @since 22 Aug 2019
 */
public class ReadOnlyResourceSetListener extends ResourceSetListenerImpl implements ResourceSetListener {

    /**
     * @see org.eclipse.emf.transaction.ResourceSetListenerImpl#isPrecommitOnly()
     *
     * @return
     */
    @Override
    public boolean isPrecommitOnly() {
        return true;
    }
    
    /**
     * Don't allow any changes to ReadOnly working copies.
     * 
     * @see org.eclipse.emf.transaction.ResourceSetListenerImpl#transactionAboutToCommit(org.eclipse.emf.transaction.ResourceSetChangeEvent)
     *
     * @param event
     * @return
     * @throws RollbackException
     */
    @Override
    public Command transactionAboutToCommit(ResourceSetChangeEvent event) throws RollbackException {
        Command cmdDetails = null;
        if (event.getTransaction().isReadOnly()) {
            // We are not interested in readOnly transactions.
            return cmdDetails;
        }
        for (Notification notification : event.getNotifications()) {
            Object notifier = notification.getNotifier();
            if (notifier instanceof EObject) {
                WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor((EObject) notifier);
                if (wc != null && wc.isReadOnly()) {
                    // The RollbackException will prevent the change to be committed.
                    throw new RollbackException(Status.OK_STATUS);
                }
            } 
        }
        return cmdDetails;
    }

}
