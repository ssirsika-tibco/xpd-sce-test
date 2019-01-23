package com.tibco.xpd.bom.modeler.custom.precommit;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.NotificationFilter;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.ResourceSetListener;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.LiteralSpecification;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager.StereotypeKind;
import com.tibco.xpd.bom.resources.utils.UML2ModelUtil;

/**
 * Handles removing the fetching stereotype when a change is made resulting it
 * the fetching information no longer being valid
 * 
 */
public class FetchingPreCommitListener implements ResourceSetListener {

    @Override
    public NotificationFilter getFilter() {
        return null;
    }

    @Override
    public Command transactionAboutToCommit(ResourceSetChangeEvent event)
            throws RollbackException {
        Command cmdDetails = null;
        for (Notification notification : event.getNotifications()) {
            Object notifier = notification.getNotifier();

            // Check if the multiplicity has changed
            if ((notifier instanceof LiteralSpecification)
                    && (cmdDetails == null)) {
                LiteralSpecification litNotif = (LiteralSpecification) notifier;
                // Get the parent of the multiplicity input
                // Set this as the notifier value as we want to do the same
                // check as a normal property
                notifier = litNotif.getOwner();
            }

            if (notifier instanceof Property) {
                final Property prop = (Property) notifier;

                // Check associations
                final Association association = prop.getAssociation();
                if ((association != null)) {
                    cmdDetails = getRemoveFetchingCommand(event, association);
                }

                // Only look at the property itself if we have not already got a
                // command to run
                if (cmdDetails == null) {
                    cmdDetails = getRemoveFetchingCommand(event, prop);
                }
            }

            // If we have already found the command to run, stop searching the
            // notifications
            if (cmdDetails != null) {
                break;
            }
        }
        return cmdDetails;
    }

    @Override
    public void resourceSetChanged(ResourceSetChangeEvent event) {
    }

    @Override
    public boolean isAggregatePrecommitListener() {
        return false;
    }

    @Override
    public boolean isPrecommitOnly() {
        return true;
    }

    @Override
    public boolean isPostcommitOnly() {
        return false;
    }

    /**
     * Creates a command to remove the stereotype if needed
     * 
     * @param event
     * @param namedElem
     * @return
     */
    private Command getRemoveFetchingCommand(ResourceSetChangeEvent event,
            final NamedElement namedElem) {
        final Stereotype stereoFetching =
                GlobalDataProfileManager.getInstance()
                        .getStereotype(StereotypeKind.FETCHING);
        if (stereoFetching == null) {
            return null;
        }

        Command cmdDetails = null;
        if ((namedElem != null)
                && (namedElem.getAppliedStereotypes().contains(stereoFetching))) {
            // Check if it should have the stereotype any more
            if (!validForFetching(namedElem)) {
                return new RecordingCommand(event.getEditingDomain()) {
                    @Override
                    protected void doExecute() {
                        namedElem.unapplyStereotype(stereoFetching);
                    }
                };
            }
        }
        return cmdDetails;
    }

    /**
     * Check if it is valid to have a fetching stereotype on this type of object
     * 
     * @param eo
     * @return
     */
    private boolean validForFetching(EObject eo) {
        if (eo == null) {
            return false;
        }

        // Only display the fetching information for properties
        // or compositions with upper multiplicity
        if (eo instanceof Property) {
            Property prop = (Property) eo;

            // Avoid putting fetching when the multiplicity on a composition
            // is selected, just want the actual composition
            if (prop.getAssociation() == null) {
                if ((prop.getUpper() > 1) || (prop.getUpper() == -1)) {
                    return true;
                }
            }
        } else if (eo instanceof Association) {
            Association assoc = (Association) eo;
            if (UML2ModelUtil.getAggregationType(assoc) == AggregationKind.COMPOSITE_LITERAL) {
                for (Property assocEnd : assoc.getMemberEnds()) {
                    if ((assocEnd.getUpper() > 1)
                            || (assocEnd.getUpper() == -1)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
