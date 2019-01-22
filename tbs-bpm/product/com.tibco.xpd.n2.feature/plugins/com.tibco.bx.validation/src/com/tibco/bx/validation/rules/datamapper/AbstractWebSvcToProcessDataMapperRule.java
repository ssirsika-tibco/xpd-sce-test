/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.bx.validation.rules.datamapper;

import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.validation.rules.mapping.WebSvcJavaScriptToProcessDataRule;
import com.tibco.xpd.datamapper.infoProviders.WrappedContributedContent;
import com.tibco.xpd.validation.bpmn.rules.baserules.JavaScriptTypeCompatibilityResult;

/**
 * Base rule for datamapper grammar data mappings from process to web service
 * data.
 * <p>
 * This handles the type compatibility checking.
 * 
 * @author aallway
 * @since 17 Feb 2016
 */
public abstract class AbstractWebSvcToProcessDataMapperRule extends
        AbstractN2DataMapperMappingRule {

    /**
     * A delegate rule that allows us to ensure that type compatibility rules
     * match those of JavaScript rules
     */
    private WebSvcJavaScriptToProcessDataRule delegateTypeCompatRule =
            new WebSvcJavaScriptToProcessDataRule();

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validate(java.lang.Object)
     * 
     * @param o
     */
    @Override
    public void validate(Object o) {
        delegateTypeCompatRule.setScope(getScope());
        super.validate(o);
    }

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#objectIsApplicable(org.eclipse.emf.ecore.EObject)
     * 
     * @param eo
     * @return
     */
    @Override
    protected boolean objectIsApplicable(EObject eo) {
        delegateTypeCompatRule.objectIsApplicable(eo);
        return super.objectIsApplicable(eo);
    }

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#objectValidateDone(org.eclipse.emf.ecore.EObject)
     * 
     * @param objectToValidate
     */
    @Override
    protected void objectValidateDone(EObject objectToValidate) {
        super.objectValidateDone(objectToValidate);
        delegateTypeCompatRule.objectValidateDone(objectToValidate);
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#clearScriptInformationCache()
     * 
     */
    @Override
    public void clearScriptInformationCache() {
        delegateTypeCompatRule.clearScriptInformationCache();
        super.clearScriptInformationCache();
    }

    /**
     * @see com.tibco.bx.validation.rules.datamapper.AbstractN2DataMapperMappingRule#checkJavaScriptTypeCompatibility(java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     * 
     * @param sourceObjectInTree
     * @param targetObjectInTree
     * @param mapping
     * @return
     */
    @Override
    protected JavaScriptTypeCompatibilityResult checkJavaScriptTypeCompatibility(
            Object sourceObjectInTree, Object targetObjectInTree, Object mapping) {
        boolean isLikeMapping = isLikeMapping(mapping);

        if (isLikeMapping) {
            return JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_SUCCEEDED;
        }

        /*
         * Delegate type compatibility checking to the standard JavaScript
         * mapping validation rule.
         */
        Object unwrappedSource = sourceObjectInTree;
        Object unwrappedTarget = targetObjectInTree;
        if (sourceObjectInTree instanceof WrappedContributedContent) {
            unwrappedSource =
                    getConceptPath((WrappedContributedContent) sourceObjectInTree);
        }
        if (targetObjectInTree instanceof WrappedContributedContent) {
            unwrappedTarget =
                    getConceptPath((WrappedContributedContent) targetObjectInTree);
        }

        return delegateTypeCompatRule
                .checkJavaScriptTypeCompatibility(unwrappedSource,
                        unwrappedTarget,
                        mapping);
    }

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#performAdditionalMappingValidation(java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     * 
     * @param mapping
     * @param sourceObjectInTree
     * @param targetObjectInTree
     */
    @Override
    protected void performAdditionalMappingValidation(Object mapping,
            Object sourceObjectInTree, Object targetObjectInTree) {
        super.performAdditionalMappingValidation(mapping,
                sourceObjectInTree,
                targetObjectInTree);

        if (!isLikeMapping(mapping)) {
            /*
             * Delegate additional type compatibility checking to the standard
             * JavaScript mapping validation rule.
             */
            /*
             * Delegate type compatibility checking to the standard JavaScript
             * mapping validation rule.
             */
            Object unwrappedSource = sourceObjectInTree;
            Object unwrappedTarget = targetObjectInTree;
            if (sourceObjectInTree instanceof WrappedContributedContent) {
                unwrappedSource =
                        getConceptPath((WrappedContributedContent) sourceObjectInTree);
            }
            if (targetObjectInTree instanceof WrappedContributedContent) {
                unwrappedTarget =
                        getConceptPath((WrappedContributedContent) targetObjectInTree);
            }

            delegateTypeCompatRule
                    .performAdditionalTypeCompatibilityRules(unwrappedSource,
                            unwrappedTarget,
                            mapping);

            delegateTypeCompatRule
                    .performAdditionalCorrelationMappingRules(unwrappedSource,
                            unwrappedTarget,
                            mapping);
        }
    }
}
