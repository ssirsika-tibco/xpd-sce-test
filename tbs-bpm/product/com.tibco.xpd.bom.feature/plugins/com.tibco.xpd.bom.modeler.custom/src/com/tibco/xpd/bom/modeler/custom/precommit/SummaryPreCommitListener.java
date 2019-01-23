package com.tibco.xpd.bom.modeler.custom.precommit;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.transaction.NotificationFilter;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.ResourceSetListener;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.globaldata.resources.SummaryInfoUtils;

/**
 * Handles changes that impact the Summary dialog, like adding or removing case
 * identifiers or case states
 * 
 */
public class SummaryPreCommitListener implements ResourceSetListener {

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

            if (notifier instanceof Property) {
                final Property prop = (Property) notifier;

                String renameValue = null;
                // Check if this is a rename
                if ((notification.getOldStringValue() != null)
                        && (notification.getNewStringValue() != null)) {
                    renameValue = notification.getOldStringValue();
                }

                // A Property changed, so we can just check to see if we need to
                // do something to update the summary information
                cmdDetails =
                        getPropertyChangedCommand(event, prop, renameValue);
            } else if (notifier instanceof Class) {
                // When an attribute is deleted from the class, it is sent as a
                // notification on the actual class. There is also one sent for
                // the property, but the reference back to the class for that
                // one will be null, so we cannot use that to update the summary
                cmdDetails = getRemovePropertyCommand(event, (Class) notifier);
            } else if (notification.getNewValue() instanceof Property) {
                // Check for the case where a stereotype on the property has
                // changed. This can mean that a normal attribute has changed to
                // a case identifier or a case state
                cmdDetails =
                        getPropertyChangedCommand(event,
                                (Property) notification.getNewValue(),
                                null);
            }

            // Note: Because the annotation that deals with the Summary
            // attributes is part of the Case Class Stereotype, when the
            // stereotype for the case class is removed, it will automatically
            // remove the summary details part as well, so we do not need to
            // deal with this

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
     * Creates the command to response to operations such as an attribute being
     * added, renamed or deleted
     * 
     * @param event
     * @param prop
     * @param previousName
     * @return
     */
    private Command getPropertyChangedCommand(ResourceSetChangeEvent event,
            final Property prop, final String previousName) {
        // Check to see if this is a case class
        Class mainCaseClass = prop.getClass_();

        if (mainCaseClass == null) {
            return null;
        }

        CompoundCommand cmdDetails = new CompoundCommand();

        // Check to see if this is a case class, as only case classes can
        // have summary details applied to them
        if (GlobalDataProfileManager.getInstance().isCase(mainCaseClass)) {
            List<Class> allClasses = new ArrayList<Class>();
            allClasses.add(mainCaseClass);
            // Get all of the classes that extend this one
            allClasses.addAll(getExtendingClasses(mainCaseClass));

            for (final Class caseClass : allClasses) {
                // Get the existing entries that are in the summary list
                List<String> summaryItems =
                        SummaryInfoUtils.getSummaryArray(caseClass);

                // If there are no summary items specified then no need to do
                // anything as the defaults will already be used
                if (!summaryItems.isEmpty()) {
                    // Check if we need to rename one of the items in the
                    // summary list
                    if ((previousName != null)
                            && summaryItems.contains(previousName)) {
                        cmdDetails.append(new RecordingCommand(event
                                .getEditingDomain()) {
                            @Override
                            protected void doExecute() {
                                // Need to handle the case where an
                                // attribute was renamed
                                SummaryInfoUtils.renameSummaryValue(caseClass,
                                        prop.getName(),
                                        previousName);
                            }
                        });
                    } else {
                        // Need to make sure all the case identifiers and case
                        // states are in the list, if not then we need to add
                        // them to the end of the list
                        List<String> defaultSummaryArray =
                                SummaryInfoUtils
                                        .getDefaultSummaryArray(caseClass);

                        for (final String requiredVal : defaultSummaryArray) {
                            if (!summaryItems.contains(requiredVal)) {
                                cmdDetails.append(new RecordingCommand(event
                                        .getEditingDomain()) {
                                    @Override
                                    protected void doExecute() {
                                        if (previousName != null) {
                                            SummaryInfoUtils
                                                    .renameSummaryValue(caseClass,
                                                            requiredVal,
                                                            previousName);
                                        } else {
                                            SummaryInfoUtils
                                                    .addSummaryValue(caseClass,
                                                            requiredVal);
                                        }
                                    }
                                });
                            }
                        }

                        // Check if we need to process and deleted from the
                        // class
                        Command removePropertyCommand =
                                getRemovePropertyCommand(event, mainCaseClass);
                        if (removePropertyCommand != null) {
                            cmdDetails.append(removePropertyCommand);
                        }
                    }
                }
            }
        }

        if (cmdDetails.isEmpty()) {
            cmdDetails = null;
        }
        return cmdDetails;
    }

    /**
     * Given a class, will check to see if any of the properties that are listed
     * in the summary should be removed
     * 
     * @param event
     * @param mainCaseClass
     * @return
     */
    private Command getRemovePropertyCommand(ResourceSetChangeEvent event,
            final Class mainCaseClass) {
        CompoundCommand cmdDetails = new CompoundCommand();

        // Check to see if this is a case class, as only case classes can
        // have summary details applied to them
        if (GlobalDataProfileManager.getInstance().isCase(mainCaseClass)) {
            List<Class> allClasses = new ArrayList<Class>();
            allClasses.add(mainCaseClass);
            // Get all of the classes that extend this one
            allClasses.addAll(getExtendingClasses(mainCaseClass));

            for (final Class caseClass : allClasses) {
                // Get the existing entries that are in the summary list
                List<String> summaryItems =
                        SummaryInfoUtils.getSummaryArray(caseClass);

                // If there are no summary items specified then no need to do
                // anything as the defaults will already be used
                if (!summaryItems.isEmpty()) {
                    // Now check to see if any items in the summary should
                    // not be there any more, so get all the names that
                    // currently exist
                    List<String> allAttribNames = new ArrayList<String>();
                    for (Property property : caseClass.getAllAttributes()) {
                        allAttribNames.add(property.getName());
                    }

                    // Check each item in the list to make sure it is still
                    // there
                    for (final String existingItem : summaryItems) {
                        if (!allAttribNames.contains(existingItem)) {
                            cmdDetails.append(new RecordingCommand(event
                                    .getEditingDomain()) {
                                @Override
                                protected void doExecute() {
                                    SummaryInfoUtils
                                            .removeSummaryValue(caseClass,
                                                    existingItem);
                                }
                            });
                        }
                    }
                }
            }
        }

        if (cmdDetails.isEmpty()) {
            cmdDetails = null;
        }
        return cmdDetails;
    }

    /**
     * Gets all the Case Classes that extend the given Case Class
     * 
     * @param baseClass
     * @return
     */
    private List<Class> getExtendingClasses(Class baseClass) {
        List<Class> extendingClasses = new ArrayList<Class>();
        List<NamedElement> ownedMembers =
                baseClass.getPackage().getOwnedMembers();
        for (NamedElement namedElement : ownedMembers) {
            if (namedElement instanceof Class) {
                // Check to see if this class extends the one we are looking for
                if (((Class) namedElement).getGenerals().contains(baseClass)) {
                    // Add this class to the list
                    extendingClasses.add((Class) namedElement);
                    // Check if anything extends this class, we want everything
                    // in the tree
                    extendingClasses
                            .addAll(getExtendingClasses((Class) namedElement));
                }
            }
        }
        return extendingClasses;
    }
}
