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

/**
 * Flow object adapter. Flow objects can have incoming and outgoing sequence
 * flows and mesage connections
 * 
 * This interface distinguishes non-flow related objects (artifacts such as
 * annotation and data object) from flow-related objects suchg as task/gateway
 * etc
 * 
 */
public interface FlowNodeAdapter extends BaseGraphicalNodeAdapter,
        MessageFlowNodeAdapter, SequenceFlowNodeAdapter {


}
