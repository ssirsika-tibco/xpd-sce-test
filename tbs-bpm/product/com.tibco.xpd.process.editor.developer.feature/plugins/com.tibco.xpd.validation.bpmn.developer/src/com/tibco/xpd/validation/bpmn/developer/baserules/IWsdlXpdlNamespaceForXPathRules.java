/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.baserules;

/**
 * Marker interface to highlight rule's implementation across inheritance
 * lineages
 * 
 * @author patkinso
 * @since 30 May 2012
 */
public interface IWsdlXpdlNamespaceForXPathRules {

    final String ISSUE_WSDL_XPDL_NAMESPACE_DISCREPANCY =
            "bpmn.dev.wsdlToXPathNamespaceMismatch"; //$NON-NLS-1$

    final String ISSUE_MISSING_WSDL_XPDL_NAMESPACE_MAP =
            "bpmn.dev.wsdlToXPathNamespaceMapNotExisting"; //$NON-NLS-1$

    final String ISSUE_SAME_PREFIX_DIFFERENT_NS =
            "bpmn.dev.wsdlToXPathNamespaceMapDifferentNsPrefixes"; //$NON-NLS-1$

    final String ISSUE_DIFFERENT_PREFIX_SAME_NS =
            "bpmn.dev.wsdlToXPathNamespaceMapDifferentNsForPrefix"; //$NON-NLS-1$

    final String ISSUE_WSDL_XPDL_NS_MAP_DISABLED_BUT_REQUIRED =
            "bpmn.dev.wsdlToXPathNamespaceMapNsPrefixRequired"; //$NON-NLS-1$

    final String ISSUE_REQUIRE_ADDITIONAL_XSD_NS =
            "bpmn.dev.wsdlToXPathNamespaceMapRequireAdditionalXsdNsPrefixes"; //$NON-NLS-1$

}
