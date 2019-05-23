/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.bom.validator.rules.globaldata;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager.StereotypeKind;
import com.tibco.xpd.bom.validator.internal.Messages;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * Validates an Composition's end classes' applied stereotypes
 * 
 * @author patkinso
 */
public class CompositionEndsRule extends BaseAssociationEndsRule {

    protected static final String ISSUE_ID_UNSUPPORTED_BIDIRECTIONAL =
            "bom.globaldata.composition.bidirectional.unsupported.issue"; //$NON-NLS-1$

    protected static final String ISSUE_ID_UNSUPPORTED_MULTIPLICITY =
            "bom.globaldata.composition.multiplicity.issue"; //$NON-NLS-1$

    protected final String RELATIONSHIP_LABEL =
            Messages.CompositionEndsRule_association_relationship_label;

    protected final String PARTCLASS_ARG =
            Messages.AggregationEntity_partclass_issue_arg_message;

    protected final String WHOLECLASS_ARG =
            Messages.AggregationEntity_wholeclass_issue_arg_message;

    /**
     * @see com.tibco.xpd.bom.validator.rules.globaldata.BaseAssociationEndsRule#getRulesAggregationKind()
     * 
     * @return
     */
    @Override
    protected AggregationKind getRulesAggregationKind() {
        return AggregationKind.COMPOSITE_LITERAL;
    }

    /**
     * @see com.tibco.xpd.bom.validator.rules.globaldata.BaseAssociationEndsRule#doUnidirectionalValidation(org.eclipse.uml2.uml.Association,
     *      com.tibco.xpd.validation.provider.IValidationScope)
     * 
     * @param association
     * @param scope
     */
    @Override
    protected void doUnidirectionalValidation(Association association,
            IValidationScope scope) {
        Set<StereotypeKind> wholeclassTypes = new HashSet<StereotypeKind>();
        Set<StereotypeKind> partclassTypes = new HashSet<StereotypeKind>();

        boolean compositionHasValidClasses =
                gdManager.getClassStereotypeKinds(association,
                        wholeclassTypes,
                        partclassTypes);

        if (compositionHasValidClasses) {

            // Add a check to ensure that the start of the composition
            // is not multiple
            for (Property ownedEnd : association.getOwnedEnds()) {
                if ((ownedEnd.getUpper() > 1) || (ownedEnd.getUpper() == -1)) {
                    scope.createIssue(ISSUE_ID_UNSUPPORTED_MULTIPLICITY,
                            BOMValidationUtil.getLocation(association),
                            association.eResource().getURIFragment(association));
                }
            }
        }
    }

    /**
     * @see com.tibco.xpd.bom.validator.rules.globaldata.BaseAssociationEndsRule#doBidirectionalValidation(org.eclipse.uml2.uml.Association,
     *      com.tibco.xpd.validation.provider.IValidationScope)
     * 
     * @param association
     * @param scope
     */
    @Override
    protected void doBidirectionalValidation(Association association,
            IValidationScope scope) {
        scope.createIssue(ISSUE_ID_UNSUPPORTED_BIDIRECTIONAL,
                BOMValidationUtil.getLocation(association),
                association.eResource().getURIFragment(association));
    }

    /**
     * @see com.tibco.xpd.bom.validator.rules.globaldata.BaseAssociationEndsRule#getRelationshipLabel()
     * 
     * @return
     */
    @Override
    protected String getRelationshipLabel() {
        return RELATIONSHIP_LABEL;
    }

}
