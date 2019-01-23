package com.tibco.xpd.bom.modeler.diagram.edit.parts.custom;

import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.transaction.NotificationFilter;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.ResourceSetListener;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.gmf.runtime.diagram.ui.editparts.LabelEditPart;
import org.eclipse.gmf.runtime.notation.View;

/**
 * 
 * An extension of CompartmentEditPart that includes a resource listener for
 * stereotype changes. When a resource change event is received the listener
 * delegates to a method to test whether the notification relates to an addition
 * or removal of a stereotype. If it does then the edit part refresh method is
 * invoked.
 * 
 * 
 * @author rgreen
 * 
 */
public class BOMStereoAwareLabelEditPart extends LabelEditPart {

    public BOMStereoAwareLabelEditPart(View view) {
        super(view);
    }

    /**
     * Handler to check for notifications of the Label changing
     * 
     */
    protected class LabelResourceListener implements ResourceSetListener {

        public LabelResourceListener() {
        }

        @Override
        public NotificationFilter getFilter() {
            return null;
        }

        @Override
        public boolean isAggregatePrecommitListener() {
            return false;
        }

        @Override
        public boolean isPostcommitOnly() {
            return true;
        }

        @Override
        public boolean isPrecommitOnly() {
            return false;
        }

        @Override
        public void resourceSetChanged(ResourceSetChangeEvent event) {
            // Loop through all the notification this event contains and
            // locate the one we are interested in.
            List<Notification> notifications = event.getNotifications();

            // Also need to refresh if a label stereotype has changed
            if (notifications != null) {
                for (Notification notification : notifications) {
                    if (!(notification.isTouch())) {
                        if (BOMEditPartUtils.isStereotypeBeingSet(notification)) {
                            // do the label refresh
                            refresh();
                        }
                    }
                }
            }
        }

        @Override
        public org.eclipse.emf.common.command.Command transactionAboutToCommit(
                ResourceSetChangeEvent event) throws RollbackException {
            return null;
        }
    }
}
