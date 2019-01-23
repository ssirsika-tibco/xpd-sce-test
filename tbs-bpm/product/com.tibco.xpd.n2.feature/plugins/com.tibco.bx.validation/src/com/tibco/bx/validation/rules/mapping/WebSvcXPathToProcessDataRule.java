/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.bx.validation.rules.mapping;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;

import com.tibco.xpd.implementer.script.XsdPath;
import com.tibco.xpd.mapper.MapperContentProvider;
import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * N2Validate the mappings for Web Service XPath to Process Data content.
 * <p>
 * i.e. This validates service invocation output mappings (ServiceTask output
 * from service) AND non-generated request activities input (input to process)
 * 
 * @author aallway
 * @since 3.4.2 (19 Jul 2010)
 */
public class WebSvcXPathToProcessDataRule
        extends
        com.tibco.xpd.validation.bpmn.developer.rules.mapping.WebSvcXPathToProcessDataRule {

    private static final String ISSUE_XSD_ANY_NOT_SUPPORTED =
            "bx.xpathMappingFromXsdAnyUnsupported"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRule#
     * isConcatenationMappingSupported()
     */
    @Override
    protected boolean isConcatenationMappingSupported() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRule#
     * isConcatenationMappingSupportedForTarget(java.lang.Object)
     */
    @Override
    protected boolean isConcatenationMappingSupportedForTarget(
            Object targetObject) {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.developer.baserules.
     * AbstractProcessDataToWebSvcMappingRule
     * #isMappingFromSourceLevelSupported(java.lang.Object)
     */
    @Override
    protected boolean isMappingFromSourceLevelSupported(
            Object sourceObjectInTree) {
        /*
         * Disallow mapping from individual array elements.
         * 
         * Note that, for N2 we also do not allow mapping to complex types but
         * that will be handled by type checking (there is currently no way to
         * correlate a BOM complex type to XPath complex type).
         */
        if (sourceObjectInTree instanceof XsdPath) {
            XsdPath xsdPath = (XsdPath) sourceObjectInTree;

            while (xsdPath != null) {
                if (xsdPath.getArrayIndex() >= 0) {
                    return false;
                }

                xsdPath = xsdPath.getParent();
            }
        }

        return super.isMappingFromSourceLevelSupported(sourceObjectInTree);
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.developer.baserules.
     * AbstractProcessDataToWebSvcMappingRule
     * #isMappingToTargetLevelSupported(java.lang.Object)
     */
    @Override
    protected boolean isMappingToTargetLevelSupported(Object targetObjectInTree) {
        /*
         * Disallow mapping to child content of complex types
         * 
         * Note that, for N2 we also do not allow mapping to complex types but
         * that will be handled by type checking (there is currently no way to
         * correlate a BOM complex type to XPath complex type).
         */
        if (targetObjectInTree instanceof ConceptPath) {
            ConceptPath path = (ConceptPath) targetObjectInTree;

            ConceptPath parent = path.getParent();
            while (parent != null) {
                Classifier classifier = parent.getType();
                if (classifier != null) {
                    return false;
                }
            }
        }

        return super.isMappingToTargetLevelSupported(targetObjectInTree);
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.developer.baserules.AbstractProcessDataToWebSvcRule#performAdditionalMappingValidation(java.lang.Object,
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

        /* XPD-1491: Validate against any kind of mapping to xsd:any. */
        if (sourceObjectInTree instanceof XsdPath
                && ((XsdPath) sourceObjectInTree).isXsdAny()) {
            Activity activity =
                    (Activity) Xpdl2ModelUtil
                            .getAncestor((EObject) ((Mapping) mapping)
                                    .getMappingModel(), Activity.class);

            addIssue(ISSUE_XSD_ANY_NOT_SUPPORTED,
                    getModelObjectForMapping(mapping),
                    createMessageList(getMappingTypeDescription(activity)),
                    createAdditionalInfo(MapperContentProvider.DATAMAPPING_SOURCE_URI_ISSUEINFO,
                            ((XsdPath) sourceObjectInTree).getPath()));

        }

        return;
    }
}
