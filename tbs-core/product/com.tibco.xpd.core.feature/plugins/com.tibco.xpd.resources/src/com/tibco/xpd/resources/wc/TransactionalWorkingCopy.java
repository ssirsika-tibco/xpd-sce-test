/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.resources.wc;

import org.eclipse.core.commands.operations.IUndoContext;

import com.tibco.xpd.resources.WorkingCopy;

/**
 * Interface to be implemented by a transaction-enabled working copy.
 * 
 * @author wzurek
 * 
 */
public interface TransactionalWorkingCopy extends WorkingCopy {

    IUndoContext getUndoContext();
}
