/**
 *(c) Copyright 2008, TIBCO Software Inc.  All rights reserved.
 *
 * LEGAL NOTICE:  This source code is provided to specific authorized end
 * users pursuant to a separate license agreement.  You MAY NOT use this
 * source code if you do not have a separate license from TIBCO Software
 * Inc.  Except as expressly set forth in such license agreement, this
 * source code, or any portion thereof, may not be used, modified,
 * reproduced, transmitted, or distributed in any form or by any means,
 * electronic or mechanical, without written permission from
 * TIBCO Software Inc.
 */
package com.tibco.xpd.daa.internal.util;

import java.util.Collections;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.Transaction;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.impl.InternalTransaction;
import org.eclipse.emf.transaction.impl.InternalTransactionalEditingDomain;

import com.tibco.amf.sca.componenttype.ComponentTypeActivator;

/**
 * An abstract base class for performing model changes on a
 * {@link TransactionalEditingDomain}, without having to create {@link Command}
 * s. This can be used in situations where Undo/Redo is not required.
 * 
 * @since Mar 9, 2009
 * @author <a href="mailto:ddwarapu@tibco.com">Dhiraj</a>
 * @version $Revision$, Mar 9, 2009
 * 
 *          This has been copied from amf feature. We should delete this once we
 *          can using their version which should be available when they do their
 *          M3 build
 * 
 */
public abstract class UnprotectedWriteOperation {
    protected final InternalTransactionalEditingDomain domain;

    protected final Map<?, ?> txOptions;

    protected UnprotectedWriteOperation(TransactionalEditingDomain domain) {
        this(domain, null);
    }

    protected UnprotectedWriteOperation(TransactionalEditingDomain domain,
            Map<?, ?> options) {
        this.domain = (InternalTransactionalEditingDomain) domain;
        if (options == null) {
            this.txOptions =
                    Collections
                            .<Object, Object> singletonMap(Transaction.OPTION_UNPROTECTED,
                                    Boolean.TRUE);
        } else {
            Map<Object, Object> myOptions =
                    new java.util.HashMap<Object, Object>(options);
            myOptions.put(Transaction.OPTION_UNPROTECTED, Boolean.TRUE);
            this.txOptions = Collections.unmodifiableMap(myOptions);
        }
    }

    public Object execute() {
        InternalTransaction writeTransaction = null;
        try {
            if (domain != null) {
                writeTransaction =
                        this.domain.startTransaction(false, txOptions);
            }
            return doExecute();
        } catch (InterruptedException e) {
            ComponentTypeActivator.getDefault().getLog().log(new Status(
                    IStatus.ERROR, ComponentTypeActivator.PLUGIN_ID, e
                            .getMessage(), e));
        } finally {
            if (writeTransaction != null) {
                try {
                    writeTransaction.commit();
                } catch (RollbackException e) {
                    ComponentTypeActivator.getDefault().getLog()
                            .log(new Status(IStatus.ERROR,
                                    ComponentTypeActivator.PLUGIN_ID, e
                                            .getMessage(), e));
                }
            }
        }

        return null;
    }

    /**
     * Sub-classes should provide the model-modification code in this method.
     */
    protected abstract Object doExecute();
}
