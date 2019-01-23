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
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * Sequence Flow adapter
 * 
 * @author wzurek
 */
public interface SequenceFlowAdapter extends BaseConnectionAdapter {

    /**
     * @return type of the flow, {@see SequenceFlowAdapter.FlowConnectionType}
     */
    SequenceFlowType getFlowType();

    /**
     * Type of sequence flow
     * 
     * @param editingDomain
     * @param data
     * @return
     */
    Command getSetFlowTypeCommand(EditingDomain editingDomain,
            SequenceFlowType data);

    /**
     * @return condition as string
     */
    String getCondition();

    /**
     * 
     * @return the existing script grammar
     */
    String getExistingSetScriptGrammarId();

    /**
     * 
     * @return
     */
    EObject getTransitionObject();

    /**
     * Outgoing Sequence flow order can be significant in BPMN (for instance for
     * XOR gateway you need to know what order to evaluate the conditions in.
     * 
     * This method is called to get the command that switches the given sequence
     * flow with the sequence flow for this adapter.
     * 
     * @param eDomain
     * @param swapWithSequenceFlow
     * @return
     */
    Command getSwapSequenceFlowOrderCommand(EditingDomain eDomain,
            Object swapWithSequenceFlow);

    /**
     * @return Return the model objects representing outgoing seq flow in theior
     *         order of evaluation - <b>should always also contain this
     *         adpater's sequence flow.</b>
     */
    List<Object> getOutgoingFlowInOrder();

}
