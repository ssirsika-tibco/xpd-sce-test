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

import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * BPMN's Pool
 * 
 * @author wzurek
 */
public interface PoolAdapter extends NamedElementAdapter,
        GraphicalColorAdapter, MessageFlowNodeAdapter {

    /**
     * List of contained lanes
     * 
     * @return
     */
    List getLanes();

    /**
     * Add new lane command
     * 
     * @param editingDomain
     * @param position
     * @return
     */
    Command getCreateNewLaneCommand(EditingDomain editingDomain, int position,
            String fillColor, String lineColor);

    /**
     * Get Closed status of pool.
     * 
     * @return
     */
    boolean isClosed();

    /**
     * Set Closed status command.
     * 
     * @param editingDomain
     * @param isClosed
     * @return
     */
    Command getSetIsClosedCommand(EditingDomain editingDomain, boolean isClosed);

    /**
     * Get the command to move a lane from one place in its parent pool to
     * another place in same pool.
     * 
     * @param editingDomain
     * @param fromIndex
     * @param toIndex
     * @return
     */
    Command getMoveLaneCommand(EditingDomain editingDomain, int fromIndex,
            int toIndex);

    /**
     * Get the command for adding an existing lane (from another pool) to given
     * place in this pool
     * 
     * @param editingDomain
     * @param laneModel
     * @param toIndex
     * @return
     */
    Command getAddLaneCommand(EditingDomain editingDomain, Object laneModel,
            int toIndex);

}
