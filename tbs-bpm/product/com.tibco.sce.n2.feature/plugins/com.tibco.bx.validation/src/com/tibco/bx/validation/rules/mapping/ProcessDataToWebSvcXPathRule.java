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
 * N2 specific overrides to BPMN validation of the XPath mappings for process
 * Data to Web Service content.
 * <p>
 * i.e. This validate service invocation input mappings (ServiceTask &
 * Send-one-way SendTask/EndEvent) AND reply activities/throw end error for
 * non-generated request activities.
 * 
 * @author aallway
 * @since 3.4.2 (14 Jul 2010)
 */
public class ProcessDataToWebSvcXPathRule
        extends
        com.tibco.xpd.validation.bpmn.developer.rules.mapping.ProcessDataToWebSvcXPathRule {

    private static final String ISSUE_XSD_ANY_NOT_SUPPORTED =
            "bx.xpathMappingToXsdAnyUnsupported"; //$NON-NLS-1$

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
         * Disallow mapping from child content of complex types
         * 
         * Note that, for N2 we also do not allow mapping to complex types but
         * that will be handled by type checking (there is currently no way to
         * correlate a BOM complex type to XPath complex type).
         */
        if (sourceObjectInTree instanceof ConceptPath) {
            ConceptPath path = (ConceptPath) sourceObjectInTree;

            ConceptPath parent = path.getParent();
            while (parent != null) {
                Classifier classifier = parent.getType();
                if (classifier != null) {
                    return false;
                }
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
         * Disallow mapping to individual array elements.
         * 
         * Note that, for N2 we also do not allow mapping to complex types but
         * that will be handled by type checking (there is currently no way to
         * correlate a BOM complex type to XPath complex type).
         */
        if (targetObjectInTree instanceof XsdPath) {
            XsdPath xsdPath = (XsdPath) targetObjectInTree;

            while (xsdPath != null) {
                if (xsdPath.getArrayIndex() >= 0) {
                    return false;
                }

                xsdPath = xsdPath.getParent();
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
        if (targetObjectInTree instanceof XsdPath
                && ((XsdPath) targetObjectInTree).isXsdAny()) {
            Activity activity =
                    (Activity) Xpdl2ModelUtil
                            .getAncestor((EObject) ((Mapping) mapping)
                                    .getMappingModel(), Activity.class);

            addIssue(ISSUE_XSD_ANY_NOT_SUPPORTED,
                    getModelObjectForMapping(mapping),
                    createMessageList(getMappingTypeDescription(activity)),
                    createAdditionalInfo(MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                            ((XsdPath) targetObjectInTree).getPath()));

        }
        return;
    }

    /**
     * 
     * @see com.tibco.xpd.validation.bpmn.developer.rules.mapping.ProcessDataToWebSvcXPathRule#isMultiToMultiSupported(java.lang.Object,
     *      java.lang.Object)
     * 
     * @param multiInstanceSource
     * @param singleInstanceTarget
     * @return
     */
    @Override
    protected boolean isMultiToMultiSupported(Object multiInstanceSource,
            Object singleInstanceTarget) {
        // Due to BX-1430, this is disallowed
        return false;
    }

}
