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
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * Validates an Association's end classes' applied stereotypes
 * 
 * @author patkinso
 * @since 27 Feb 2013
 */
public class AssociationEndsRule extends BaseAssociationEndsRule {

    protected static final String ISSUE_ID_BIDIRECTIONAL_HETEROGENEOUS =
            "bom.globaldata.association.bidirectional.hetrogeneous.ends.issue"; //$NON-NLS-1$

    protected static final String ISSUE_ID_BIDIRECTIONAL_HOMOGENEOUS =
            "bom.globaldata.association.bidirectional.homogeneous.ends.issue"; //$NON-NLS-1$

    protected static String ISSUE_ID =
            "bom.globaldata.association.unidirectional.issue"; //$NON-NLS-1$   

    protected final String SOURCECLASS_ARG =
            Messages.AssociationTypesRule_sourceclass_issue_arg_message;

    protected final String TARGETCLASS_ARG =
            Messages.AssociationTypesRule_targetclass_issue_arg_message;

    /**
     * @see com.tibco.xpd.bom.validator.rules.globaldata.BaseAssociationEndsRule#getRulesAggregationKind()
     * 
     * @return
     */
    @Override
    protected AggregationKind getRulesAggregationKind() {
        return AggregationKind.NONE_LITERAL;
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
        Set<StereotypeKind> targetclassTypes = new HashSet<StereotypeKind>();
        Set<StereotypeKind> sourceclassTypes = new HashSet<StereotypeKind>();

        boolean associationHasValidClasses =
                gdManager.getClassStereotypeKinds(association,
                        sourceclassTypes,
                        targetclassTypes);

        if (associationHasValidClasses) {
            String[] msgParams = null;

            if (targetclassTypes.contains(CASE)) {
                if (sourceclassTypes.contains(CASE)) {
                    // allowed
                } else if (sourceclassTypes.contains(GLOBAL)) {
                    // disallowed!
                    msgParams =
                            new String[] { TARGETCLASS_ARG, CASETYPE_ARG,
                                    SOURCECLASS_ARG, GLOBALTYPE_ARG };
                } else { // whole class is LOCAL
                    // disallowed!
                    msgParams =
                            new String[] { TARGETCLASS_ARG, CASETYPE_ARG,
                                    SOURCECLASS_ARG, LOCALTYPE_ARG };
                }
            } else if (targetclassTypes.contains(GLOBAL)) {
                if (sourceclassTypes.contains(CASE)) {
                    // disallowed!
                    msgParams =
                            new String[] { TARGETCLASS_ARG, GLOBALTYPE_ARG,
                                    SOURCECLASS_ARG, CASETYPE_ARG };
                } else if (sourceclassTypes.contains(GLOBAL)) {
                    // disallowed!
                    msgParams =
                            new String[] { TARGETCLASS_ARG, GLOBALTYPE_ARG,
                                    SOURCECLASS_ARG, GLOBALTYPE_ARG };
                } else { // part class is LOCAL
                    // disallowed!
                    msgParams =
                            new String[] { TARGETCLASS_ARG, GLOBALTYPE_ARG,
                                    SOURCECLASS_ARG, LOCALTYPE_ARG };
                }
            } else { // whole class is LOCAL
                if (sourceclassTypes.contains(CASE)) {
                    // disallowed!
                    msgParams =
                            new String[] { TARGETCLASS_ARG, LOCALTYPE_ARG,
                                    SOURCECLASS_ARG, CASETYPE_ARG };
                } else if (sourceclassTypes.contains(GLOBAL)) {
                    // disallowed!
                    msgParams =
                            new String[] { TARGETCLASS_ARG, LOCALTYPE_ARG,
                                    SOURCECLASS_ARG, GLOBALTYPE_ARG };
                } else { // part class is LOCAL
                    // disallowed!
                    msgParams =
                            new String[] { TARGETCLASS_ARG, LOCALTYPE_ARG,
                                    SOURCECLASS_ARG, LOCALTYPE_ARG };
                }
            }

            if (msgParams != null && msgParams.length == 4) {
                scope.createIssue(ISSUE_ID,
                        BOMValidationUtil.getLocation(association),
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
                        BOMValidationUtil.getLocation(association),
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
