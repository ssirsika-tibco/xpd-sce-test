/**
 * IPreCommandExecutionWrapper.java
 *
 * 
 *
 * @author aallway
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.xpdl2.resources;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * IPreCommandExecutionWrapper
 * 
 */
public interface IPreCommandExecutionWrapper {

    /**
     * Each contributor is given the chance to wrap a command that is just about
     * to be executed on the command stack.
     * 
     * @param editingDomain
     * @param cmd
     * 
     * @return Command that wraps the given command or the original command if nothing to do.
     */
    public Command wrapCommandBeforeExecution(Xpdl2WorkingCopyImpl workingCopy, EditingDomain editingDomain,
            Command cmd);
}
