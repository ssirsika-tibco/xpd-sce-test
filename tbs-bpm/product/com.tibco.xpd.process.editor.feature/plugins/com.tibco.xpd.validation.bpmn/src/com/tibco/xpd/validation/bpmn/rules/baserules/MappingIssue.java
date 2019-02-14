/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules.baserules;

/**
 * These are the possible issue that this validation rule checks for.
 * 
 * @author aallway
 * @since 3.3 (12 May 2010)
 */
public enum MappingIssue {

    /**
     * &lt;mapping_type&gt;: mapping source data '&lt;data_path&gt;' is no
     * longer available.
     */
    SOURCE_MISSING("abstractMappingRule.sourceMissing"), //$NON-NLS-1$

    /**
     * &lt;mapping_type&gt;: mapping target data '&lt;data_path&gt;' is no
     * longer available.
     */
    TARGET_MISSING("abstractMappingRule.targetMissing"), //$NON-NLS-1$

    /**
     * &lt;mapping_type&gt;: The data types are incompatible for mapping
     * '&lt;data_path&gt;' to '&lt;data_path&gt;'.
     */
    INCOMPATIBLE_TYPES("abstractMappingRule.incompatibleTypes"), //$NON-NLS-1$

    /**
     * &lt;mapping_type&gt;: Single to multiple instance data mapping is not
     * supported for '&lt;data_path&gt;' to '&lt;data_path&gt;'.
     */
    SINGLE_TO_MULTI_UNSUPPORTED("abstractMappingRule.singleToMultiUnsupported"), //$NON-NLS-1$

    /**
     * &lt;mapping_type&gt;: Multiple to single instance data mapping is not
     * supported for '&lt;data_path&gt;' to '&lt;data_path&gt;'.
     */
    MULTI_TO_SINGLE_UNSUPPORTED("abstractMappingRule.multiToSingleUnsupported"), //$NON-NLS-1$

    /**
     * &lt;mapping_type&gt;: Multiple to multiple instance data mapping is not
     * supported for '&lt;data_path&gt;' to '&lt;data_path&gt;'.
     */
    MULTI_TO_MULTI_UNSUPPORTED("abstractMappingRule.multiToMultiUnsupported"), //$NON-NLS-1$

    /**
     * &lt;mapping_type&gt;: Mapping to read-only target '&lt;data_path&gt;' is
     * not permitted.
     */
    MAPPING_TO_READONLY("abstractMappingRule.mappingToReadOnly"), //$NON-NLS-1$

    /**
     * &lt;mapping_type&gt;: mapping from the level of '&lt;data_path&gt;' in
     * source tree is not supported.
     */
    UNSUPPORTED_SOURCE_LEVEL("abstractMappingRule.unsupportedSourceLevel"), //$NON-NLS-1$

    /**
     * &lt;mapping_type&gt;: mapping to the level of '&lt;data_path&gt;' in
     * target tree is not supported.
     */
    UNSUPPORTED_TARGET_LEVEL("abstractMappingRule.unsupportedTargetLevel"), //$NON-NLS-1$

    /**
     * &lt;mapping_type&gt;: concatenation mappings (multiple into one) are not
     * supported.
     * 
     * (no concatenation is supported regardless of target).
     */
    CONCATENATION_UNSUPPORTED("abstractMappingRule.concatenationUnsupported"), //$NON-NLS-1$

    /**
     * &lt;mapping_type&gt;: concatenation mappings are not supported for target
     * '&lt;data_path&gt;' data type.
     * 
     * (concatenation is supported on some but not all types).
     */
    CONCATENATION_UNSUPPORTED_FOR_TYPE(
            "abstractMappingRule.concatenationUnsupportedForType"), //$NON-NLS-1$

    /**
     * &lt;mapping_type&gt;: script mappings are not supported.
     * 
     * (script mappings are not supported for any target).
     */
    SCRIPT_UNSUPPORTED("abstractMappingRule.scriptUnsupported"), //$NON-NLS-1$

    /**
     * &lt;mapping_type&gt;: script mappings are not supported for target
     * '&lt;data_path&gt;' data type.
     * 
     * (script mappings is supported on some but not all types).
     */
    SCRIPT_UNSUPPORTED_FOR_TYPE("abstractMappingRule.scriptUnsupportedForType"), //$NON-NLS-1$

    /**
     * &lt;mapping_type&gt;: script return type is not resolved.
     * 
     * (script return type is not resolved).
     */
    SCRIPT_RETURN_TYPE_UNRESOLVED(
            "abstractMappingRule.scriptReturnTypeUnresolved"), //$NON-NLS-1$

    /**
     * &lt;mapping_type&gt;: the mandatory target '&lt;data_path&gt;' must be
     * mapped to complete the target data.
     */
    UNMAPPED_REQUIRED_TARGET("abstractMappingRule.unmappedRequiredTarget"), //$NON-NLS-1$

    /**
     * &lt;mapping_type&gt;: the mandatory target '&lt;data_path&gt;' should be
     * mapped to complete the target data.
     */
    UNMAPPED_REQUIRED_TARGET_WARN(
            "abstractMappingRule.unmappedRequiredTarget.warning"), //$NON-NLS-1$

    /**
     * &lt;mapping_type&gt;: the mandatory target '&lt;data_path&gt;' or a
     * sibling must be mapped to complete the target data.
     */
    UNMAPPED_REQUIRED_CHOICE("abstractMappingRule.unmappedRequiredChoice"), //$NON-NLS-1$

    /**
     * &lt;mapping_type&gt;: the mandatory target '&lt;data_path&gt;' or a
     * sibling should be mapped to complete the target data.
     */
    UNMAPPED_REQUIRED_CHOICE_WARN(
            "abstractMappingRule.unmappedRequiredChoice.warning"), //$NON-NLS-1$

    /**
     * &lt;mapping_type&gt;: mapping to both the target '&lt;data_path&gt;' and
     * its descendants is not permitted.
     */
    INVALID_NESTED_MAPPING("abstractMappingRule.invalidNestedMapping"), //$NON-NLS-1$

    /**
     * &lt;mapping_type&gt;: simple content target '&lt;data_path&gt;' must be
     * mapped when included by child mappings.
     */
    UNMAPPED_INCLUDED_SIMPLE_CONTENT(
            "abstractMappingRule.unmappedIncludedSimpleContent"), //$NON-NLS-1$

    /**
     * &lt;mapping_type&gt;: simple content target '&lt;data_path&gt;' should be
     * mapped when included by child mappings.
     */
    UNMAPPED_INCLUDED_SIMPLE_CONTENT_WARN(
            "abstractMappingRule.unmappedIncludedSimpleContent.warning"), //$NON-NLS-1$
    /**
     * &lt;mapping_type&gt;: only one object within a choice group can be mapped
     * (&lt;data_path&gt;).
     */
    MULTIPLE_CHOICES_MAPPED("abstractMappingRule.multipleChoicesSelected"), //$NON-NLS-1$

    /**
     * &lt;mapping_type&gt;: a maximum of &lt;n&gt; elements have already been
     * mapped for &lt;data_path&gt;.
     */
    MAXIMUM_ELEMENTS_EXCEEDED("abstractMappingRule.maxElementsExceeded"), //$NON-NLS-1$

    /**
     * &lt;mapping_type&gt;: a maximum of &lt;n&gt; elements have already been
     * mapped for &lt;data_path&gt;.
     */
    ABSTRACT_TYPE_USED_IN_MAPPING("abstractMappingRule.abstractTypeSource"), //$NON-NLS-1$

    UNABLE_TO_CHECK_MAPPING_TYPES("bpmn.mappingTypesCouldNotBeChecked.warning"), //$NON-NLS-1$
    ;

    private String issueId;

    private MappingIssue(String problemText) {
        this.issueId = problemText;
    }

    /**
     * @return the plugin.xml issue Id for this mapping issue.
     */
    public String getIssueId() {
        return issueId;
    }

}