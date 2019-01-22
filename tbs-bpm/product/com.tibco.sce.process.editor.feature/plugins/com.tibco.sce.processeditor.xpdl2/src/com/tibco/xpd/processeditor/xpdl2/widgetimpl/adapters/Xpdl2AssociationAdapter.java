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
import com.tibco.xpd.processwidget.adapters.AssociationAdapter;
import com.tibco.xpd.xpdl2.Association;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.UniqueIdElement;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.ocl.NotificationsConstants;
import com.tibco.xpd.xpdl2.util.ocl.SimpleViewerNotification;

/**
 * BPMN's Association in XPDL 2
 * 
 * @author wzurek
 */
public class Xpdl2AssociationAdapter extends Xpdl2BaseConnectionAdapter
        implements AssociationAdapter {

    private Association getAssociation() {
        return (Association) getTarget();
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

        cmd.setLabel(Messages.Xpdl2AssociationAdapter_DeleteAssociation_menu);

        Command delCmd = super.getDeleteCommand(editingDomain);

        if (delCmd == null) {
            return UnexecutableCommand.INSTANCE;
        }

        cmd.append(delCmd);
        return cmd;
    }

    @Override
    public void notifyChanged(Notification msg) {
        if (msg instanceof SimpleViewerNotification) {
            /* Sid XPD-8302 - pass message in so can ignore adapter removal */
            fireDiagramNotification(msg);
        } else {
            if (!msg.isTouch()) {
                Association ass = getAssociation();
                Package pck = ass.getPackage();

                if (msg.getNotifier() == getTarget()
                        && (msg.getEventType() == Notification.SET
                                || msg.getEventType() == Notification.UNSET)) {
                    switch (msg.getFeatureID(Transition.class)) {
                    case Xpdl2Package.NAMED_ELEMENT__NAME:
                    case Xpdl2Package.ASSOCIATION__ASSOCIATION_DIRECTION:
                        /*
                         * Sid XPD-8302 - pass message in so can ignore adapter
                         * removal
                         */
                        fireDiagramNotification(msg);
                        break;
                    case Xpdl2Package.ASSOCIATION__SOURCE:
                    case Xpdl2Package.ASSOCIATION__TARGET:
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
                    Messages.Xpdl2AssociationAdapter_ReconnectAssociation_menu);
            cmd.append(SetCommand.create(editingDomain,
                    getAssociation(),
                    Xpdl2Package.eINSTANCE.getAssociation_Target(),
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
                    Messages.Xpdl2AssociationAdapter_DeleteAssociation_menu);
            cmd.append(SetCommand.create(editingDomain,
                    getAssociation(),
                    Xpdl2Package.eINSTANCE.getAssociation_Source(),
                    ((UniqueIdElement) sourceNode).getId()));

            appendResetStartAnchorCommand(editingDomain, cmd, startAnchorPos);
            return cmd;
        }

        return UnexecutableCommand.INSTANCE;
    }

    @Override
    public EObject getSourceNode() {
        return getAssociation().getPackage()
                .findNamedElement(getAssociation().getSource());
    }

    @Override
    public EObject getTargetNode() {
        return getAssociation().getPackage()
                .findNamedElement(getAssociation().getTarget());
    }

    public Command getSetDirectedAssociationCommand(EditingDomain ed,
            boolean directed) {

        return SetCommand.create(ed,
                getAssociation(),
                Xpdl2Package.eINSTANCE.getAssociation_AssociationDirection(),
                new Boolean(directed));
    }

    @Override
    public Command getSetAssociationDirectionCommand(EditingDomain ed,
            com.tibco.xpd.processwidget.adapters.AssociationAdapter.AssociationDirectionType direction) {

        Object val;
        if (direction == AssociationAdapter.DIRECTION_NONE) {
            val = com.tibco.xpd.xpdl2.AssociationDirectionType.NONE_LITERAL;
        } else if (direction == AssociationAdapter.DIRECTION_BOTH) {
            val = com.tibco.xpd.xpdl2.AssociationDirectionType.BOTH_LITERAL;
        } else if (direction == AssociationAdapter.DIRECTION_TO_SOURCE) {
            val = com.tibco.xpd.xpdl2.AssociationDirectionType.FROM_LITERAL;
        } else if (direction == AssociationAdapter.DIRECTION_TO_TARGET) {
            val = com.tibco.xpd.xpdl2.AssociationDirectionType.TO_LITERAL;
        } else {
            return UnexecutableCommand.INSTANCE;
        }

        CompoundCommand cmd = new CompoundCommand();
        cmd.setLabel(
                Messages.Xpdl2AssociationAdapter_SetDirectionOfAssociation_menu);

        cmd.append(SetCommand.create(ed,
                getAssociation(),
                Xpdl2Package.eINSTANCE.getAssociation_AssociationDirection(),
                val));
        return cmd;
    }

    @Override
    public AssociationAdapter.AssociationDirectionType getAssociationDirection() {

        Association ass = getAssociation();
        com.tibco.xpd.xpdl2.AssociationDirectionType ad =
                ass.getAssociationDirection();
        if (ad == null) {
            return AssociationAdapter.DIRECTION_NONE;
        }
        switch (ad.getValue()) {
        case com.tibco.xpd.xpdl2.AssociationDirectionType.NONE:
            return AssociationAdapter.DIRECTION_NONE;
        case com.tibco.xpd.xpdl2.AssociationDirectionType.BOTH:
            return AssociationAdapter.DIRECTION_BOTH;
        case com.tibco.xpd.xpdl2.AssociationDirectionType.FROM:
            return AssociationAdapter.DIRECTION_TO_SOURCE;
        case com.tibco.xpd.xpdl2.AssociationDirectionType.TO:
            return AssociationAdapter.DIRECTION_TO_TARGET;
        }

        return AssociationAdapter.DIRECTION_NONE;
    }

}
