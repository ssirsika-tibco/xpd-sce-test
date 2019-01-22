/**
 * GeneralNodeFlowConnectionEditPolicy.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processwidget.policies;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

import com.tibco.xpd.processwidget.adapters.MessageFlowAdapter;
import com.tibco.xpd.processwidget.adapters.SequenceFlowAdapter;
import com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart;
import com.tibco.xpd.processwidget.parts.MessageFlowEditPart;
import com.tibco.xpd.processwidget.parts.SequenceFlowEditPart;
import com.tibco.xpd.processwidget.parts.WidgetRootEditPart;

/**
 * GeneralNodeFlowConnectionEditPolicy
 * 
 * This class is the edit policy that allows a graphical node to be connected
 * with any one of the conenction types...
 * <li>SequenceFlow</li>
 * <li>MessageFlow</li>
 * <li>Association</li>
 * 
 */
public class AllConnectionTypesNodeEditPolicy extends
        AssociationOnlyNodeFlowConnectionEditPolicy {

    Set<Class> validConnectionAdapterTypes = null;
    
    /**
     * @param editingDomain
     */
    public AllConnectionTypesNodeEditPolicy(EditingDomain editingDomain) {
        super(editingDomain);
        
        validConnectionAdapterTypes = new HashSet<Class>();
        
        validConnectionAdapterTypes.add(SequenceFlowAdapter.class);
        validConnectionAdapterTypes.add(MessageFlowAdapter.class);
    }

    /**
     * Remove handling for given connection adapter type
     * @param validConnectionAdapterClass Either SequenceFlowAdapter.class or MessageFlowAdapter.class
     */
    public void removeValidConnectionAdapterType(Class validConnectionAdapterClass) {
        validConnectionAdapterTypes.remove(validConnectionAdapterClass);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.processwidget.policies.FlowGraphicalNodeObjectEditPolicy#understandsRequest(org.eclipse.gef.Request)
     */
    public boolean understandsRequest(Request req) {
        if (!super.understandsRequest(req)) {
            Object connType = getConnectionType(req);

            if (connType != SequenceFlowAdapter.class
                    && connType != MessageFlowAdapter.class) {
                return false;
            }
        }
        return (true);
    }

    /*
     * (non-Javadoc) Unfortunately, when GEF's create connection / reconnect end
     * of connection is performed, it updates the feedback BEFORE checking
     * whether the created command is valid and hence snaps to anchor point even
     * when the request cannot be performed.
     * 
     * So here, we have to return a dummy anchor to keep the end of connection
     * with the mouse if over a connection that will be invalid.
     * 
     * @see org.eclipse.gef.editparts.AbstractEditPart#getTargetEditPart(org.eclipse.gef.Request)
     */
    public EditPart getTargetEditPart(Request request) {
        Object connType = getConnectionType(request);

        if (validConnectionAdapterTypes.contains(connType)) {
            WidgetRootEditPart root = (WidgetRootEditPart) getHost().getRoot();

            if (REQ_CONNECTION_END.equals(request.getType()) || REQ_CONNECTION_START.equals(request.getType())) {
                CreateConnectionRequest creq = (CreateConnectionRequest) request;

                EditPart connectionSource;
                EditPart connectionTarget;

                // Validate that a connection between these 2 objects is valid.
                if (REQ_CONNECTION_START.equals(request.getType())) {
                    connectionSource = getHost();
                    connectionTarget = null;
                }
                else {
                    connectionSource = creq.getSourceEditPart();
                    connectionTarget = getHost();
                }

                String errStr = null;
                if (connType == SequenceFlowAdapter.class) {
                    errStr = SequenceFlowEditPart.isInvalidConnection(
                            (BaseGraphicalEditPart) connectionSource,
                            (BaseGraphicalEditPart) connectionTarget);
                } else {
                    errStr = MessageFlowEditPart.isInvalidConnection(
                            (BaseGraphicalEditPart) connectionSource,
                            (BaseGraphicalEditPart) connectionTarget, null);
                }

                // If not valid, set up an Error Tool Tip.
                if (errStr != null) {
                    root.setErrorTipHelperText(getHostFigure(), errStr);

                    // And say that we're not a valid target for this request.
                    return null;
                }
                else {
                    root.clearErrorTipHelper();
                }

            } else if (REQ_RECONNECT_TARGET.equals(request.getType())
                    || REQ_RECONNECT_SOURCE.equals(request.getType())) {

                ReconnectRequest creq = (ReconnectRequest) request;

                ConnectionEditPart connectionEP = creq.getConnectionEditPart();

                BaseGraphicalEditPart connectionSource;
                BaseGraphicalEditPart connectionTarget;

                if (REQ_RECONNECT_TARGET.equals(request.getType())) {
                    connectionSource = (BaseGraphicalEditPart) connectionEP
                            .getSource();
                    connectionTarget = (BaseGraphicalEditPart) getHost();
                } else {
                    connectionTarget = (BaseGraphicalEditPart) connectionEP
                            .getTarget();
                    connectionSource = (BaseGraphicalEditPart) getHost();
                }

                String errStr = null;
                if (connType == SequenceFlowAdapter.class) {
                    errStr = SequenceFlowEditPart.isInvalidConnection(
                            (BaseGraphicalEditPart) connectionSource,
                            (BaseGraphicalEditPart) connectionTarget);
                } else {
                    errStr = MessageFlowEditPart.isInvalidConnection(
                            (BaseGraphicalEditPart) connectionSource,
                            (BaseGraphicalEditPart) connectionTarget,
                            connectionEP);
                }

                // If not valid, set up an Error Tool Tip.
                if (errStr != null) {
                    root.setErrorTipHelperText(getHostFigure(), errStr);

                    // And say that we're not a valid target for this request.
                    return null;
                }
                else {
                    root.clearErrorTipHelper();
                }

            }

            return getHost();
        }

        return super.getTargetEditPart(request);
    }

}
