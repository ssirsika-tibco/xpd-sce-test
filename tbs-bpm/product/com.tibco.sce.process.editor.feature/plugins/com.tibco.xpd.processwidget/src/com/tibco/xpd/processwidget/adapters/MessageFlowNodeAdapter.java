/**
 * MessageFlowNodeAdapter.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processwidget.adapters;

import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * MessageFlowNodeAdapter
 *
 */
public interface MessageFlowNodeAdapter {

    /**
     * @return list of messages connections
     */
    public abstract List getSourceMessageFlows();

    /**
     * @return list of messages connections
     */
    public abstract List getTargetMessageFlows();

    /**
     * Create command that create new message flow connection from this object
     * to provided target
     * 
     * @param editingDomain
     * @param targetNode
     * @param bendPoints
     * @param startAnchorPos
     *            Percent portion of source object line for start anchor or null
     *            for default anchoring
     * @param endAnchorPos
     *            Percent portion of target object line for end anchor or null
     *            for default anchoring
     * 
     * @return
     */
    public abstract Command getCreateMessageFlowCommand(
            EditingDomain editingDomain, EObject targetNode, List bendPoints,
            Double startAnchorPos, Double endAnchorPos, String lineColor);

}