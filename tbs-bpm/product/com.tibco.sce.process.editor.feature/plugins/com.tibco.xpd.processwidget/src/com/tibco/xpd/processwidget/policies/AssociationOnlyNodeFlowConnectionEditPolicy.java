/**
 * GeneralNodeFlowConnectionEditPolicy.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processwidget.policies;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

import com.tibco.xpd.processwidget.adapters.AssociationAdapter;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.internal.Messages;
import com.tibco.xpd.processwidget.parts.AssociationEditPart;
import com.tibco.xpd.processwidget.parts.BaseFlowNodeEditPart;
import com.tibco.xpd.processwidget.parts.EventEditPart;
import com.tibco.xpd.processwidget.parts.MessageFlowEditPart;
import com.tibco.xpd.processwidget.parts.SequenceFlowEditPart;
import com.tibco.xpd.processwidget.parts.WidgetRootEditPart;

/**
 * GeneralNodeFlowConnectionEditPolicy
 * 
 * This class is the edit policy that allows a graphical node to be connected
 * with any one of the conenction types...
 * <li>Association</li>
 * 
 */
public class AssociationOnlyNodeFlowConnectionEditPolicy extends
        ConnectableGraphicalNodeObjectEditPolicy {

    /**
     * @param editingDomain
     */
    public AssociationOnlyNodeFlowConnectionEditPolicy(
            EditingDomain editingDomain) {
        super(editingDomain);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.processwidget.policies.FlowGraphicalNodeObjectEditPolicy#understandsRequest(org.eclipse.gef.Request)
     */
    public boolean understandsRequest(Request req) {
        // Now check our own rules...
        if (!super.understandsRequest(req)) {
            Object connType = getConnectionType(req);

            if (connType != AssociationAdapter.class) {
                return false;
            }
        }
        return (true);
    }

    /**
     * Return tru if the given edit part is a process flow related one (i.e. a
     * task, gateway, event, sequence flow or messageflow).
     * 
     * @param ep
     * @return
     */
    private boolean isFlowRelatedEditPart(EditPart ep) {
        if ((ep instanceof BaseFlowNodeEditPart)
                || (ep instanceof SequenceFlowEditPart)
                || (ep instanceof MessageFlowEditPart)) {
            return true;
        }

        return false;
    }

    /**
     * Check if association connection is valid between the 2 objects and return
     * reason for invalidity if so.
     * 
     * @param connectionSource
     * @param connectionTarget
     * 
     * @return String reason if invalid else null if valid.
     */
    private String isInvalidConnection(EditPart connectionSource,
            EditPart connectionTarget) {
        String ret = null;

        // One end of association Must be a non-flow artifact
        // and the other end MUST be a flow node OR a
        // sequence/messag flow
        //
        // (Note this one condition also (intentionally covers
        // disallowing object self-association)
        if ((connectionSource instanceof AssociationEditPart || connectionTarget instanceof AssociationEditPart)) {
            // Cannot associate to an association connection.
            ret = Messages.AssociationOnlyNodeFlowConnectionEditPolicy_CanOnlyConnectNonFlowWithFlow_message;

        } else if (isFlowRelatedEditPart(connectionSource) == isFlowRelatedEditPart(connectionTarget)) {
            // both ends of association are flow-related or both are
            // non-flow related.

            // There is one circumstance that this is allowed (actually
            // required). If the source is a compensation on boundary of a task
            // then BPMN says that you can ONLY have association.
            boolean isBorderCompensation = false;
            if (connectionSource instanceof EventEditPart && connectionSource != connectionTarget) {
                EventEditPart eventEP = (EventEditPart) connectionSource;

                if (eventEP.isAttachedToTaskBorder()
                        && eventEP.getEventTriggerType() == EventTriggerType.EVENT_COMPENSATION_CATCH) {
                    isBorderCompensation = true;
                }
            }
            
            if (!isBorderCompensation) {
                ret = Messages.AssociationOnlyNodeFlowConnectionEditPolicy_CanOnlyConnectNonFlowWithFlow_message;
            }
        }

        return ret;

    }

    /**
     * Override to stop response to get target for anything except association
     * connections.
     * 
     * MUST do this in getTargetEditPart rather than in create command in
     * adapter otherwise the end of connection will still snap to object even
     * when it's invalid.
     * 
     * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getTargetEditPart(org.eclipse.gef.Request)
     */
    public EditPart getTargetEditPart(Request request) {
        Object connType = getConnectionType(request);

        // If it's a connection request and its not association then
        // the host is only valid target if this is a request for associations.

        if (connType == AssociationAdapter.class) {
            WidgetRootEditPart root = (WidgetRootEditPart) getHost().getRoot();

            if (REQ_CONNECTION_END.equals(request.getType())) {
                EditPart sourceEditPart = ((CreateConnectionRequest) request)
                        .getSourceEditPart();

                EditPart endEditPart = getHost();

                if (sourceEditPart != endEditPart) {
                    String errStr = isInvalidConnection(sourceEditPart,
                            endEditPart);

                    if (errStr != null) {
                        root.setErrorTipHelperText(getHostFigure(), errStr);
                        return null;
                    }
                } else {
                    return null;
                }

            } else if (REQ_CONNECTION_START.equals(request.getType())) {
                if (getHost() instanceof AssociationEditPart) {
                    // Disallow associating from an association.
                    return null;
                }

            } else if (REQ_RECONNECT_TARGET.equals(request.getType())
                    || REQ_RECONNECT_SOURCE.equals(request.getType())) {
                ConnectionEditPart connEP = ((ReconnectRequest) request)
                        .getConnectionEditPart();

                EditPart targetEditPart;
                EditPart sourceEditPart;

                if (REQ_RECONNECT_TARGET.equals(request.getType())) {
                    sourceEditPart = connEP.getSource();
                    targetEditPart = getHost();
                } else {
                    sourceEditPart = getHost();
                    targetEditPart = connEP.getTarget();
                }

                // One end of association Must be a non-flow artifact
                // and the other end MUST be a flow node OR a
                // sequence/messag flow
                //
                // (Note this one condition also (intentionally covers
                // disallowing object self-association)
                String errStr = isInvalidConnection(sourceEditPart,
                        targetEditPart);

                if (errStr != null) {
                    root.setErrorTipHelperText(getHostFigure(), errStr);
                    return null;
                }

            }
            // root.setErrorTipHelperText(getHostFigure(), null);
            return getHost();
        }

        return null;
    }
}
