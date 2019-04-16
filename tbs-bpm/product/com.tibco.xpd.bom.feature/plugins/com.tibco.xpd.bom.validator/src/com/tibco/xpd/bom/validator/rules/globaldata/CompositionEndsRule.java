/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.bom.validator.rules.globaldata;

import static com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager.StereotypeKind.CASE;
import static com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager.StereotypeKind.GLOBAL;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager.StereotypeKind;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.bom.resources.utils.UML2ModelUtil;
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

    protected static final String INTRABOM_ISSUE_ID =
            "bom.globaldata.composition.unidirectional.intrabom.issue"; //$NON-NLS-1$   

    protected static final String INTRABOM_HAS_MUTABLE_SOLUTION_ISSUE_ID =
            "bom.globaldata.composition.unidirectional.intrabom.mutable.issue"; //$NON-NLS-1$

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
            if (BOMUtils.isIntraBomRelationship(association)) {
                doIntraBomUnidirectionalValidation(association,
                        scope,
                        wholeclassTypes,
                        partclassTypes);
            } else {
                doInterBomUnidirectionalValidation(scope,
                        association,
                        wholeclassTypes,
                        partclassTypes);
            }

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
     * @param scope
     * @param association
     * @param wholeclassTypes
     * @param partclassTypes
     */
    private void doInterBomUnidirectionalValidation(IValidationScope scope,
            Association association, Set<StereotypeKind> wholeclassTypes,
            Set<StereotypeKind> partclassTypes) {

        String[] msgParams = null;

        if (wholeclassTypes.contains(CASE)) {
            if (partclassTypes.contains(CASE)) {
                // disallowed!
                msgParams =
                        new String[] { WHOLECLASS_ARG, CASETYPE_ARG,
                                PARTCLASS_ARG, CASETYPE_ARG };
            } else if (partclassTypes.contains(GLOBAL)) {
                // disallowed!
                msgParams =
                        new String[] { WHOLECLASS_ARG, CASETYPE_ARG,
                                PARTCLASS_ARG, GLOBALTYPE_ARG };
            } else { // whole class is LOCAL
                // disallowed: whole should have mutated to 'global'
                msgParams =
                        new String[] { WHOLECLASS_ARG, CASETYPE_ARG,
                                PARTCLASS_ARG, LOCALTYPE_ARG };
            }
        } else if (wholeclassTypes.contains(GLOBAL)) {
            if (partclassTypes.contains(CASE)) {
                // disallowed!
                msgParams =
                        new String[] { WHOLECLASS_ARG, GLOBALTYPE_ARG,
                                PARTCLASS_ARG, CASETYPE_ARG };
            } else if (partclassTypes.contains(GLOBAL)) {
                // disallowed!
                msgParams =
                        new String[] { WHOLECLASS_ARG, GLOBALTYPE_ARG,
                                PARTCLASS_ARG, GLOBALTYPE_ARG };
            } else { // part class is LOCAL
                // disallowed: whole should have mutated to 'global'
                msgParams =
                        new String[] { WHOLECLASS_ARG, GLOBALTYPE_ARG,
                                PARTCLASS_ARG, LOCALTYPE_ARG };
            }
        } else { // whole class is LOCAL
            if (partclassTypes.contains(CASE)) {
                // allowed
            } else if (partclassTypes.contains(GLOBAL)) {
                // allowed
            } else { // part class is LOCAL
                // allowed
            }
        }

        if (msgParams != null && msgParams.length == 4) {

            Map<String, String> additionalInfo = new HashMap<String, String>();
            additionalInfo.put(RELATIONSHIP_LOCATION, association.eResource()
                    .getURIFragment(association));

            // get whole end to set issue against
            List<Class> associationEnds =
                    UML2ModelUtil.getAssociationEnds(association);
            if (associationEnds.size() >= 2) {
                Classifier classifier = associationEnds.get(0);
                if (classifier != null) {

                    scope.createIssue(INTRABOM_ISSUE_ID,
                            BOMValidationUtil.getLocation(classifier),
                            classifier.eResource().getURIFragment(classifier),
                            Arrays.asList(msgParams),
                            additionalInfo);
                }
            }
        }
    }

    /**
     * @param association
     * @param scope
     * @param wholeclassTypes
     * @param partclassTypes
     */
    private void doIntraBomUnidirectionalValidation(Association association,
            IValidationScope scope, Set<StereotypeKind> wholeclassTypes,
            Set<StereotypeKind> partclassTypes) {

        // Classifier wholeClass = getWholeClass(association);
        Classifier partClass = getPartClass(association);

        ClassMutationSolution mutationSolution = null;
        String[] msgParams = null;

        if (wholeclassTypes.contains(CASE)) {
            if (partclassTypes.contains(CASE)) {
                // disallowed!
                msgParams =
                        new String[] { WHOLECLASS_ARG, CASETYPE_ARG,
                                PARTCLASS_ARG, CASETYPE_ARG };
            } else if (partclassTypes.contains(GLOBAL)) {
                // allowed
            } else { // whole class is LOCAL
                // disallowed!
                msgParams =
                        new String[] { WHOLECLASS_ARG, CASETYPE_ARG,
                                PARTCLASS_ARG, LOCALTYPE_ARG };
                mutationSolution =
                        new ClassMutationSolution(GLOBAL, partClass,
                                INTRABOM_HAS_MUTABLE_SOLUTION_ISSUE_ID);
            }
        } else if (wholeclassTypes.contains(GLOBAL)) {
            if (partclassTypes.contains(CASE)) {
                // disallowed!
                msgParams =
                        new String[] { WHOLECLASS_ARG, GLOBALTYPE_ARG,
                                PARTCLASS_ARG, CASETYPE_ARG };
            } else if (partclassTypes.contains(GLOBAL)) {
                // allowed
            } else { // part class is LOCAL
                // disallowed!
                msgParams =
                        new String[] { WHOLECLASS_ARG, GLOBALTYPE_ARG,
                                PARTCLASS_ARG, LOCALTYPE_ARG };
                mutationSolution =
                        new ClassMutationSolution(GLOBAL, partClass,
                                INTRABOM_HAS_MUTABLE_SOLUTION_ISSUE_ID);
            }
        } else { // whole class is LOCAL
            if (partclassTypes.contains(CASE)) {
                // allowed
            } else if (partclassTypes.contains(GLOBAL)) {
                // allowed
            } else { // part class is LOCAL
                // allowed
            }
        }

        if (msgParams != null && msgParams.length == 4) {

            String issueID =
                    (mutationSolution == null) ? INTRABOM_ISSUE_ID
                            : mutationSolution.issueID;

            scope.createIssue(issueID,
                    BOMValidationUtil.getLocation(association),
                    association.eResource().getURIFragment(association),
                    Arrays.asList(msgParams),
                    getAdditionalInfo(mutationSolution, association));
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
