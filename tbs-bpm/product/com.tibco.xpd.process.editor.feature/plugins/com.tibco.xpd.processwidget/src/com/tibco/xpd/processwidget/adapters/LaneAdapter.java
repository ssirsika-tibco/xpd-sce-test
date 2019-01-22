/*
 ** 
 **  MODULE:             $RCSfile: $ 
 **                      $Revision: $ 
 **                      $Date: $ 
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc., All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */

package com.tibco.xpd.processwidget.adapters;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * BPMN's Lane
 * 
 * @author wzurek
 */
public interface LaneAdapter extends NamedElementAdapter, ProcessContainerAdapter,
        GraphicalColorAdapter {

    /**
     * Default lane size on initial creation.
     */
    public static final int DEFAULT_LANE_SIZE = 300;


    /**
     * Lane size
     * 
     * @return
     */
    int getSize();


    /**
     * Set lane size command
     * 
     * If size is passed as 0 then the lane should be set to it's minimum to
     * contain all objects inside it.
     * 
     * @param editingDomain
     * @param size
     * @return
     */
    Command getSetSizeCommand(EditingDomain editingDomain, int size);
    
    /**
     * Get closed status of lane.
     * 
     * @return
     */
    boolean isClosed();
    
    /**
     * Set closed status command.
     * 
     * @param editingDomain
     * @param isClosed
     * @return
     */
    Command getSetIsClosedCommand (EditingDomain editingDomain, boolean isClosed);

}
