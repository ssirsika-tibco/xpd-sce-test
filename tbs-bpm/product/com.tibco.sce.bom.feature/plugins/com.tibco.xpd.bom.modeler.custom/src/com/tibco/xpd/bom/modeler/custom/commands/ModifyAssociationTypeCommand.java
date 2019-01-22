package com.tibco.xpd.bom.modeler.custom.commands;

import java.util.List;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.resources.utils.UML2ModelUtil;

public class ModifyAssociationTypeCommand extends RecordingCommand {

    public enum AssociationType {
        NORMAL, COMPOSITION_SRC, COMPOSITION_DST, AGGREGATION_SRC, AGGREGATION_DST
    }

    private AssociationType newType;

    private Association association;

    public ModifyAssociationTypeCommand(TransactionalEditingDomain domain) {
        super(domain);
    }

    public ModifyAssociationTypeCommand(
            TransactionalEditingDomain editingDomain, AssociationType type,
            Association association) {
        this(editingDomain);
        this.newType = type;
        this.association = association;
    }

    @Override
    protected void doExecute() {
        // semantic part
        Property sourceEnd = null, targetEnd = null;

        List<Property> memberEnds = association.getMemberEnds();
        sourceEnd = memberEnds.get(0);
        targetEnd = memberEnds.get(1);

        if (this.newType == AssociationType.NORMAL) {
            // Set Composition for the source
            sourceEnd.setAggregation(AggregationKind.NONE_LITERAL);
            targetEnd.setAggregation(AggregationKind.NONE_LITERAL);
        }

        // COMPOSITION AT SOURCE
        if (this.newType == AssociationType.COMPOSITION_SRC) {

            // Check whether there is navigability set for this assocoiation
            if (!UML2ModelUtil.isAssociationBiDirectional(association)) {
                List<Property> lstOwnedEnds = association.getOwnedEnds();

                if (lstOwnedEnds.size() == 1) {
                    // Get the owned property and its referencing class
                    Property ownedProp = lstOwnedEnds.get(0);
                    // Class ownedPropsClass = (Class) ownedProp.getType();

                    if (ownedProp == targetEnd) {
                        // "Arrow right to left"
                        sourceEnd
                                .setAggregation(AggregationKind.COMPOSITE_LITERAL);
                        return;
                    }

                }
            }

            // Set Composition for the source
            targetEnd.setAggregation(AggregationKind.COMPOSITE_LITERAL);

        }

        // AGGREGATION AT SOURCE
        if (this.newType == AssociationType.AGGREGATION_SRC) {

            // Check whether there is navigability set for this association
            if (!UML2ModelUtil.isAssociationBiDirectional(association)) {
                List<Property> lstOwnedEnds = association.getOwnedEnds();

                if (lstOwnedEnds.size() == 1) {
                    // Get the owned property and its referencing class
                    Property ownedProp = lstOwnedEnds.get(0);
                    // Class ownedPropsClass = (Class) ownedProp.getType();

                    if (ownedProp == targetEnd) {
                        // "Arrow right to left"
                        sourceEnd
                                .setAggregation(AggregationKind.SHARED_LITERAL);
                        return;
                    }
                }
            }

            // Set Aggregation for the source
            targetEnd.setAggregation(AggregationKind.SHARED_LITERAL);

        }

    }

}
