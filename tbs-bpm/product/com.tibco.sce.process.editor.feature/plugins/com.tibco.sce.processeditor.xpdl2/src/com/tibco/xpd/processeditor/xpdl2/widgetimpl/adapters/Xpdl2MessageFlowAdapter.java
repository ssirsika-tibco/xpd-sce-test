/*
 * 
 */

package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.NotificationImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processwidget.adapters.MessageFlowAdapter;
import com.tibco.xpd.xpdl2.MessageFlow;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.UniqueIdElement;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.ocl.NotificationsConstants;
import com.tibco.xpd.xpdl2.util.ocl.SimpleViewerNotification;

/**
 * BPMN's MessageFlow adapter to the XPDL2 MessageFlow
 * 
 * @author wzurek
 */
public class Xpdl2MessageFlowAdapter extends Xpdl2BaseConnectionAdapter
        implements MessageFlowAdapter {

    /**
     * @return (MessageFlow) getTarget()
     */
    protected MessageFlow getMessageFlow() {
        return (MessageFlow) getTarget();
    }

    @Override
    public EObject getSourceNode() {
        return getMessageFlow().getPackage()
                .findNamedElement(getMessageFlow().getSource());
    }

    @Override
    public EObject getTargetNode() {
        return getMessageFlow().getPackage()
                .findNamedElement(getMessageFlow().getTarget());
    }

    @Override
    public void notifyChanged(Notification msg) {
        if (msg instanceof SimpleViewerNotification) {
            /* Sid XPD-8302 - pass message in so can ignore adapter removal */
            fireDiagramNotification(msg);
        } else {
            if (!msg.isTouch()) {

                MessageFlow mf = getMessageFlow();
                Package pck = mf.getPackage();

                if (msg.getNotifier() == getTarget()
                        && (msg.getEventType() == Notification.SET
                                || msg.getEventType() == Notification.UNSET)) {
                    switch (msg.getFeatureID(Transition.class)) {
                    case Xpdl2Package.NAMED_ELEMENT__NAME:
                        /*
                         * Sid XPD-8302 - pass message in so can ignore adapter
                         * removal
                         */
                        fireDiagramNotification(msg);
                        break;
                    case Xpdl2Package.MESSAGE_FLOW__SOURCE:
                    case Xpdl2Package.MESSAGE_FLOW__TARGET:
                        // notify activities about connection reconnect
                        String oldVal = msg.getOldStringValue();
                        String newVal = msg.getNewStringValue();
                        EObject act;
                        act = pck.findNamedElement(oldVal);
                        if (act != null) {
                            act.eNotify(new NotificationImpl(
                                    NotificationsConstants.REFRESH_REFERENCED_ELEMENT,
                                    null, null));
                        }
                        act = pck.findNamedElement(newVal);
                        if (act != null) {
                            act.eNotify(new NotificationImpl(
                                    NotificationsConstants.REFRESH_REFERENCED_ELEMENT,
                                    null, null));
                        }
                        break;
                    }
                }
            }
        }
    }

    @Override
    public Command getSetTargetCommand(EditingDomain editingDomain,
            EObject targetNode, Double endAnchorPos) {
        if (targetNode instanceof UniqueIdElement) {

            CompoundCommand cmd = new CompoundCommand();
            cmd.setLabel(
                    Messages.Xpdl2MessageFlowAdapter_ReconnectMessageFlow_menu);
            cmd.append(SetCommand.create(editingDomain,
                    getMessageFlow(),
                    Xpdl2Package.eINSTANCE.getMessageFlow_Target(),
                    ((UniqueIdElement) targetNode).getId()));

            appendResetEndAnchorCommand(editingDomain, cmd, endAnchorPos);
            return cmd;
        }

        return UnexecutableCommand.INSTANCE;
    }

    @Override
    public Command getSetSourceCommand(EditingDomain editingDomain,
            EObject sourceNode, Double startAnchorPos) {
        if (sourceNode instanceof UniqueIdElement) {
            CompoundCommand cmd = new CompoundCommand();
            cmd.setLabel(
                    Messages.Xpdl2MessageFlowAdapter_ReconnectMessageFlow_menu);
            cmd.append(SetCommand.create(editingDomain,
                    getMessageFlow(),
                    Xpdl2Package.eINSTANCE.getMessageFlow_Source(),
                    ((UniqueIdElement) sourceNode).getId()));

            appendResetStartAnchorCommand(editingDomain, cmd, startAnchorPos);
            return cmd;
        }

        return UnexecutableCommand.INSTANCE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.
     * Xpdl2BaseFlowAdapter#getDeleteCommand(org.eclipse.emf.edit.domain.
     * EditingDomain)
     */
    @Override
    public Command getDeleteCommand(EditingDomain editingDomain) {
        CompoundCommand cmd = new CompoundCommand();

        cmd.setLabel(Messages.Xpdl2MessageFlowAdapter_DeleteMessageFlow_menu);

        Command delCmd = super.getDeleteCommand(editingDomain);

        if (delCmd == null) {
            return UnexecutableCommand.INSTANCE;
        }

        cmd.append(delCmd);
        return cmd;
    }

}
