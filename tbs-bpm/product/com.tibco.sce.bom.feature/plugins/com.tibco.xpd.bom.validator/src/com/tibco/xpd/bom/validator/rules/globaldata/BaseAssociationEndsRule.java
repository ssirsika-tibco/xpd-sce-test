/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.bom.validator.rules.globaldata;

import static com.tibco.xpd.bom.validator.internal.IAdditionalInfoMarkerKeys.ADDITIONAL_STEREOTYPE_KIND_NAME;
import static com.tibco.xpd.bom.validator.internal.IAdditionalInfoMarkerKeys.RELATIONSHIP_NAME;
import static com.tibco.xpd.bom.validator.internal.IAdditionalInfoMarkerKeys.TARGETS_FRAGMENT_URI_LOCATION;
import static com.tibco.xpd.bom.validator.internal.IAdditionalInfoMarkerKeys.TARGETS_RESOURCE_URI_LOCATION;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Relationship;

import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager.StereotypeKind;
import com.tibco.xpd.bom.validator.internal.Messages;
import com.tibco.xpd.bom.resources.utils.UML2ModelUtil;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Base validation for end classes' applied stereotypes types of association
 * relationships. As these relationships can be uni- or bi-directional
 * subclasses implement their own validation for these scenarios.
 * 
 * @author patkinso
 * @since 27 Feb 2013
 */
public abstract class BaseAssociationEndsRule implements IValidationRule {

    protected final String CASETYPE_ARG =
            Messages.ClassStereotypes_case_type_issue_arg_message;

    protected final String GLOBALTYPE_ARG =
            Messages.ClassStereotypes_global_type_issue_arg_message;

    protected final String LOCALTYPE_ARG =
            Messages.ClassStereotypes_local_type_issue_arg_message;

    public static final String RELATIONSHIP_LOCATION =
            "bom.relationship.issue.cause"; //$NON-NLS-1$

    protected GlobalDataProfileManager gdManager = GlobalDataProfileManager
            .getInstance();

    /**
     * Holder for data relating to suggested class mutation solution
     * 
     * @author patkinso
     * @since 27 Sep 2013
     */
    protected class ClassMutationSolution {

        StereotypeKind viableMutationToType = null;

        Classifier viableClassToMutate = null;

        String issueID;

        /**
         * @param viableMutationToType
         * @param viableClassToMutate
         * @param issueID
         */
        ClassMutationSolution(StereotypeKind viableMutationToType,
                Classifier viableClassToMutate, String issueID) {
            super();
            this.viableMutationToType = viableMutationToType;
            this.viableClassToMutate = viableClassToMutate;
            this.issueID = issueID;
        }

    }

    /**
     * @return
     */
    protected abstract AggregationKind getRulesAggregationKind();

    /**
     * @param association
     * @param scope
     */
    protected abstract void doUnidirectionalValidation(Association association,
            IValidationScope scope);

    /**
     * @param association
     * @param scope
     */
    protected abstract void doBidirectionalValidation(Association association,
            IValidationScope scope);

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule#getTargetClass()
     * 
     * @return
     */
    @Override
    public Class<?> getTargetClass() {
        return Association.class;
    }

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule#validate(com.tibco.xpd.validation.provider.IValidationScope,
     *      java.lang.Object)
     * 
     * @param scope
     * @param o
     */
    @Override
    public void validate(IValidationScope scope, Object o) {
        Association association = (Association) o;

        if (UML2ModelUtil.getAggregationType(association)
                .equals(getRulesAggregationKind())) {

            if (UML2ModelUtil.isAssociationBiDirectional(association)) {
                doBidirectionalValidation(association, scope);
            } else {
                doUnidirectionalValidation(association, scope);
            }
        }
    }

    /**
     * @param association
     * @return
     */
    protected Classifier getPartClass(Association association) {

        List<org.eclipse.uml2.uml.Class> associationEnds =
                UML2ModelUtil.getAssociationEnds(association);
        if (associationEnds.size() >= 2) {
            return associationEnds.get(1);
        }
        return null;
    }

    /**
     * @param association
     * @return
     */
    protected Classifier getWholeClass(Association association) {

        List<org.eclipse.uml2.uml.Class> associationEnds =
                UML2ModelUtil.getAssociationEnds(association);
        if (associationEnds.size() >= 2) {
            return associationEnds.get(0);
        }
        return null;
    }

    /**
     * @param mutationSolution
     * @param association
     * @return
     */
    protected Map<String, String> getAdditionalInfo(
            ClassMutationSolution mutationSolution, Relationship relationship) {

        Map<String, String> additionalInfo = new HashMap<String, String>();

        additionalInfo.put(RELATIONSHIP_LOCATION, relationship.eResource()
                .getURIFragment(relationship));

        if (mutationSolution != null) {
            additionalInfo.put(RELATIONSHIP_NAME, getRelationshipLabel());

            // populate with info for class mutation
            String stereotypeKindName =
                    mutationSolution.viableMutationToType.name();
            Classifier classToMutate = mutationSolution.viableClassToMutate;
            additionalInfo.put(ADDITIONAL_STEREOTYPE_KIND_NAME,
                    stereotypeKindName);
            additionalInfo.put(TARGETS_RESOURCE_URI_LOCATION, classToMutate
                    .eResource().getURI().toPlatformString(true));
            additionalInfo.put(TARGETS_FRAGMENT_URI_LOCATION, classToMutate
                    .eResource().getURIFragment(classToMutate));
        }

        return additionalInfo;
    }

    /**
     * @return
     */
    protected abstract String getRelationshipLabel();

}
