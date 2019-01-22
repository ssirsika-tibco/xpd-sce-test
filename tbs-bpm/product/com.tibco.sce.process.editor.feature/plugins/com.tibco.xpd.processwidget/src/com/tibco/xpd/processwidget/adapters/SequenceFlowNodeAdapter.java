/**
 * SequenceFlowNodeAdapter.java
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
 * SequenceFlowNodeAdapter
 * 
 */
public interface SequenceFlowNodeAdapter {

    /**
     * @return list of incoming sequence flows.
     */
    public abstract List getTargetSequenceFlows();

    /**
     * @return list of outgoing sequence flow connections
     */
    public abstract List getSourceSequenceFlows();

    /**
     * Create command that create new sequence flow connection from this object
     * to provided target
     * 
     * @param editingDomain
     * @param targetNode
     * @param flowType
     * @param condition
     * @param bendPoints
     * @param startAnchorPos
     *            Percent portion of source object line for start anchor or null
     *            for default anchoring
     * @param endAnchorPos
     *            Percent portion of target object line for end anchor or null
     *            for default anchoring
     * @param name
     *            TODO
     * @param labelPos
     *            TODO
     * @return
     */
    public abstract Command getCreateSequenceFlowCommand(
            EditingDomain editingDomain, EObject targetNode,
            SequenceFlowType flowType, String condition, String grammarId,
            List bendPoints, Double startAnchorPos, Double endAnchorPos,
            String name, ConnectionLabelPosition labelPos, String lineColor);

}