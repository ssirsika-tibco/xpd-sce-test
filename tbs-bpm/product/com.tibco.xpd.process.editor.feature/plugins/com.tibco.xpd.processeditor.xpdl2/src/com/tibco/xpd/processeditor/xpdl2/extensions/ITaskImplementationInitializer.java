/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.extensions;

import org.eclipse.emf.common.command.Command;

import com.tibco.xpd.xpdl2.Activity;

/**
 * This interface is to create the initial structure of the model
 * for the task implementation
 * 
 * @author mtorres
 *
 */
public interface ITaskImplementationInitializer {

    /**
     * This method is an opportunity to create the initial structure in the
     * model for a given activity
     * 
     * @return Command the command that creates the initial structure in the
     *         model for a given activity
     */
    Command getInitialStructureCommand(Activity activity);

    /**
     * This method does a cleanup in the model for a given activity
     * 
     * @return Command the command that cleanup the model
     **/
    Command getCleanupCommand(Activity activity);
    
}
