/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.commands;

import java.util.List;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.resources.utils.UML2ModelUtil;

/**
 * Command to execute model changes when navigability is changed.
 * 
 * @author rgreen
 * 
 */

public class ModifyNavigabilityCommand extends RecordingCommand {

    public enum AssociationDirection {
        BIDIRECTIONAL, FIRSTTOSECOND, SECONDTOFIRST
    }

    private AssociationDirection direction;

    private Association association;

    public ModifyNavigabilityCommand(TransactionalEditingDomain domain) {
        super(domain);
    }

    public ModifyNavigabilityCommand(TransactionalEditingDomain editingDomain,
            AssociationDirection direction, Association association) {
        this(editingDomain);

        this.direction = direction;

        this.association = association;
    }

    @Override
    protected void doExecute() {
        // semantic part
        Class sourceType = null, targetType = null;
        Property sourceEnd = null, targetEnd = null;
        List<Property> memberEnds = association.getMemberEnds();

        sourceEnd = memberEnds.get(0);
        sourceType = (Class) sourceEnd.getType();
        targetEnd = memberEnds.get(1);
        targetType = (Class) targetEnd.getType();
        if (direction == AssociationDirection.SECONDTOFIRST) {
            if (UML2ModelUtil.isAssociationBiDirectional(association)) {
                sourceType.getOwnedAttributes().remove(targetEnd);
                association.getOwnedEnds().add(targetEnd);
            } else {
                // If Aggregation kind is set at end(1) already then we
                // need to swap Aggregation kind to the opposite end
                List<Property> lstOwnedEnds = association.getOwnedEnds();

                if (lstOwnedEnds.size() == 1) {
                    // Get the owned property and its referencing class
                    Property ownedProp = lstOwnedEnds.get(0);
                    // Class ownedPropsClass = (Class) ownedProp.getType();

                    if (ownedProp == targetEnd) {
                        // "Arrow right to left"
                        if (targetEnd.getAggregation() == AggregationKind.COMPOSITE_LITERAL) {
                            sourceEnd
                                    .setAggregation(AggregationKind.COMPOSITE_LITERAL);
                            targetEnd
                                    .setAggregation(AggregationKind.NONE_LITERAL);
                        } else if (targetEnd.getAggregation() == AggregationKind.SHARED_LITERAL) {
                            sourceEnd
                                    .setAggregation(AggregationKind.SHARED_LITERAL);
                            targetEnd
                                    .setAggregation(AggregationKind.NONE_LITERAL);
                        }
                    }

                }

                association.getOwnedEnds().remove(sourceEnd);
                targetType.getOwnedAttributes().add(sourceEnd);
                association.getOwnedEnds().add(targetEnd);
                sourceType.getOwnedAttributes().remove(targetEnd);
            }
        } else if (direction == AssociationDirection.FIRSTTOSECOND) {
            if (UML2ModelUtil.isAssociationBiDirectional(association)) {
                targetType.getOwnedAttributes().remove(sourceEnd);
                association.getOwnedEnds().add(sourceEnd);
            } else {

                // If Aggregation kind is set at end(1) already then we
                // need to swap Aggregation kind to the opposite end
                List<Property> lstOwnedEnds = association.getOwnedEnds();

                if (lstOwnedEnds.size() == 1) {
                    // Get the owned property and its referencing class
                    Property ownedProp = lstOwnedEnds.get(0);
                    // Class ownedPropsClass = (Class) ownedProp.getType();

                    if (ownedProp == targetEnd) {
                        // "Arrow right to left"
                        if (sourceEnd.getAggregation() == AggregationKind.COMPOSITE_LITERAL) {
                            targetEnd
                                    .setAggregation(AggregationKind.COMPOSITE_LITERAL);
                            sourceEnd
                                    .setAggregation(AggregationKind.NONE_LITERAL);
                        } else if (sourceEnd.getAggregation() == AggregationKind.SHARED_LITERAL) {
                            targetEnd
                                    .setAggregation(AggregationKind.SHARED_LITERAL);
                            sourceEnd
                                    .setAggregation(AggregationKind.NONE_LITERAL);
                        }
                    }

                }

                association.getOwnedEnds().remove(targetEnd);
                sourceType.getOwnedAttributes().add(targetEnd);
                association.getOwnedEnds().add(sourceEnd);
                targetType.getOwnedAttributes().remove(sourceEnd);
            }
        } else if (direction == AssociationDirection.BIDIRECTIONAL) {
            // Need to make sure that if Aggregation kind is set then it
            // is at end(1)
            if (!UML2ModelUtil.isAssociationBiDirectional(association)) {
                List<Property> lstOwnedEnds = association.getOwnedEnds();

                if (lstOwnedEnds.size() == 1) {
                    // Get the owned property and its referencing class
                    Property ownedProp = lstOwnedEnds.get(0);
                    // Class ownedPropsClass = (Class) ownedProp.getType();

                    if (ownedProp == targetEnd) {
                        // "Arrow right to left"
                        if (sourceEnd.getAggregation() == AggregationKind.COMPOSITE_LITERAL) {
                            targetEnd
                                    .setAggregation(AggregationKind.COMPOSITE_LITERAL);
                            sourceEnd
                                    .setAggregation(AggregationKind.NONE_LITERAL);
                        } else if (sourceEnd.getAggregation() == AggregationKind.SHARED_LITERAL) {
                            targetEnd
                                    .setAggregation(AggregationKind.SHARED_LITERAL);
                            sourceEnd
                                    .setAggregation(AggregationKind.NONE_LITERAL);
                        }
                    }

                }

            }

            if (association.getOwnedEnds().contains(sourceEnd)) {
                association.getOwnedEnds().remove(sourceEnd);
                targetType.getOwnedAttributes().add(sourceEnd);
            } else if (association.getOwnedEnds().contains(targetEnd)) {
                association.getOwnedEnds().remove(targetEnd);
                sourceType.getOwnedAttributes().add(targetEnd);
            }
        }

    }

}
