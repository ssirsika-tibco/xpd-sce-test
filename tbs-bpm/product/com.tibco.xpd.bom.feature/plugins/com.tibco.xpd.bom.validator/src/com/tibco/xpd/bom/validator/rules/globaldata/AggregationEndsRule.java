/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.bom.validator.rules.globaldata;

import static com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager.StereotypeKind.CASE;
import static com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager.StereotypeKind.GLOBAL;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;

import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager.StereotypeKind;
import com.tibco.xpd.bom.validator.internal.Messages;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * Validates an Aggregation's end classes' applied stereotypes
 * 
 * @author patkinso
 * @since 27 Feb 2013
 */
public class AggregationEndsRule extends BaseAssociationEndsRule {

    protected static final String ISSUE_ID_UNSUPPORTED_BIDIRECTIONAL =
            "bom.globaldata.aggregation.bidirectional.unsupported.issue"; //$NON-NLS-1$

    protected static String ISSUE_ID =
            "bom.globaldata.aggregation.unidirectional.issue"; //$NON-NLS-1$    

    protected static final String ISSUE_ID_BIDIRECTIONAL_HETEROGENEOUS =
            "bom.globaldata.aggregation.bidirectional.hetrogeneous.ends.issue"; //$NON-NLS-1$

    protected static final String ISSUE_ID_BIDIRECTIONAL_HOMOGENEOUS =
            "bom.globaldata.aggregation.bidirectional.homogeneous.ends.issue"; //$NON-NLS-1$

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
        return AggregationKind.SHARED_LITERAL;
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

        boolean aggregationHasValidClasses =
                gdManager.getClassStereotypeKinds(association,
                        wholeclassTypes,
                        partclassTypes);

        if (aggregationHasValidClasses) {
            String[] msgParams = null;

            if (wholeclassTypes.contains(CASE)) {
                if (partclassTypes.contains(CASE)) {
                    // allowed
                } else if (partclassTypes.contains(GLOBAL)) {
                    // disallowed!
                    msgParams =
                            new String[] { WHOLECLASS_ARG, CASETYPE_ARG,
                                    PARTCLASS_ARG, GLOBALTYPE_ARG };
                } else { // whole class is LOCAL
                    // disallowed!
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
                    // disallowed!
                    msgParams =
                            new String[] { WHOLECLASS_ARG, GLOBALTYPE_ARG,
                                    PARTCLASS_ARG, LOCALTYPE_ARG };
                }
            } else { // whole class is LOCAL
                if (partclassTypes.contains(CASE)) {
                    // disallowed!
                    msgParams =
                            new String[] { WHOLECLASS_ARG, LOCALTYPE_ARG,
                                    PARTCLASS_ARG, CASETYPE_ARG };
                } else if (partclassTypes.contains(GLOBAL)) {
                    // disallowed!
                    msgParams =
                            new String[] { WHOLECLASS_ARG, LOCALTYPE_ARG,
                                    PARTCLASS_ARG, GLOBALTYPE_ARG };
                } else { // part class is LOCAL
                    // disallowed!
                    msgParams =
                            new String[] { WHOLECLASS_ARG, LOCALTYPE_ARG,
                                    PARTCLASS_ARG, LOCALTYPE_ARG };
                }
            }

            if (msgParams != null && msgParams.length == 4) {
                scope.createIssue(ISSUE_ID,
                        association.eClass().getName(),
                        association.eResource().getURIFragment(association),
                        Arrays.asList(msgParams));
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

        Set<StereotypeKind> end1classTypes = new HashSet<StereotypeKind>();
        Set<StereotypeKind> end2classTypes = new HashSet<StereotypeKind>();

        boolean associationHasValidClasses =
                gdManager.getClassStereotypeKinds(association,
                        end1classTypes,
                        end2classTypes);

        if (associationHasValidClasses) {
            String[] msgParams = null;

            Set<StereotypeKind> endclassTypes = new HashSet<StereotypeKind>();
            endclassTypes.addAll(end1classTypes);
            endclassTypes.addAll(end2classTypes);

            String issueID = null;

            boolean hasCASE = endclassTypes.contains(CASE);
            boolean hasGLOBAL = endclassTypes.contains(GLOBAL);
            boolean hasLOCAL =
                    (!end1classTypes.contains(CASE) && !end1classTypes
                            .contains(GLOBAL))
                            || (!end2classTypes.contains(CASE) && !end2classTypes
                                    .contains(GLOBAL));

            if (hasCASE) {
                if (hasGLOBAL) { // CASE-GLOBAL
                    // disallowed!
                    issueID = ISSUE_ID_BIDIRECTIONAL_HETEROGENEOUS;
                    msgParams = new String[] { CASETYPE_ARG, GLOBALTYPE_ARG };
                } else if (hasLOCAL) { // CASE-LOCAL
                    // disallowed!
                    issueID = ISSUE_ID_BIDIRECTIONAL_HETEROGENEOUS;
                    msgParams = new String[] { CASETYPE_ARG, LOCALTYPE_ARG };
                } else { // CASE-CASE
                    // allowed
                }
            } else if (hasGLOBAL) {
                if (hasLOCAL) { // GLOBAL-LOCAL
                    // disallowed!
                    issueID = ISSUE_ID_BIDIRECTIONAL_HETEROGENEOUS;
                    msgParams = new String[] { GLOBALTYPE_ARG, LOCALTYPE_ARG };
                } else { // GLOBAL-GLOBAL
                    // disallowed!
                    msgParams = new String[] { GLOBALTYPE_ARG };
                    issueID = ISSUE_ID_BIDIRECTIONAL_HOMOGENEOUS;
                }
            } else { // LOCAL-LOCAL
                // disallowed!
                msgParams = new String[] { LOCALTYPE_ARG };
                issueID = ISSUE_ID_BIDIRECTIONAL_HOMOGENEOUS;
            }

            if (issueID != null) {
                scope.createIssue(issueID,
                        association.eClass().getName(),
                        association.eResource().getURIFragment(association),
                        Arrays.asList(msgParams));
            }
        }
    }

    /**
     * @see com.tibco.xpd.bom.validator.rules.globaldata.BaseAssociationEndsRule#getRelationshipLabel()
     * 
     * @return
     */
    @Override
    protected String getRelationshipLabel() {
        // TODO Auto-generated method stub
        return null;
    }

}
