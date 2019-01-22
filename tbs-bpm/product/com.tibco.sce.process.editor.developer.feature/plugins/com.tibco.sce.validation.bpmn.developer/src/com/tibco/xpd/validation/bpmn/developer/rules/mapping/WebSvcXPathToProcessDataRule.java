/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.rules.mapping;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.xsd.XSDTypeDefinition;

import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.implementer.script.IWsdlPath;
import com.tibco.xpd.implementer.script.XsdPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.validation.bpmn.developer.baserules.AbstractWebSvcToProcessDataRuleForXPath;
import com.tibco.xpd.validation.bpmn.developer.rules.BpmnTypeUtil;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * BPMN Validate the mappings for Web Service XPath to Process Data content.
 * <p>
 * i.e. This validates service invocation output mappings (ServiceTask output
 * from service) AND non-generated request activities input (input to process)
 * 
 * @author aallway
 * @since 3.4.2 (19 Jul 2010)
 */
public class WebSvcXPathToProcessDataRule extends
        AbstractWebSvcToProcessDataRuleForXPath {

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRule#
     * checkTypeCompatibility(java.lang.Object, java.lang.Object,
     * java.lang.Object)
     */
    @Override
    protected boolean checkTypeCompatibility(Object sourceObjectInTree,
            Object targetObjectInTree, Object mapping) {
        if (targetObjectInTree instanceof ConceptPath
                && sourceObjectInTree instanceof IWsdlPath
                && getModelObjectForMapping(mapping) instanceof DataMapping) {

            ConceptPath conceptPath = (ConceptPath) targetObjectInTree;
            IWsdlPath wsdlPath = (IWsdlPath) sourceObjectInTree;
            DataMapping dataMapping =
                    (DataMapping) getModelObjectForMapping(mapping);

            if (conceptPath.getRoot().getItem() instanceof ProcessRelevantData) {
                ProcessRelevantData processData =
                        (ProcessRelevantData) conceptPath.getRoot().getItem();

                /*
                 * Resolve the source object into a basic type if possible.
                 */
                BasicType basicType =
                        BasicTypeConverterFactory.INSTANCE
                                .getBasicType(conceptPath.getItem());
                XSDTypeDefinition wsdlDataType = wsdlPath.getType();

                /* Must have managed to resolve both to types for them to match. */
                if (wsdlDataType != null) {
                    /*
                     * Note that unlike the old rules we only check type
                     * compatibility, regardless of whether source/target are
                     * array or non-array.
                     * 
                     * Mixed Multi<->Single mapping problems are handled by
                     * different part of the rule.
                     */
                    if (BpmnTypeUtil.typesMatch(wsdlDataType,
                            basicType,
                            dataMapping)) {
                        /* Basic types match */
                        return true;

                    } else if (BpmnTypeUtil
                            .checkOtherAcceptableMatches(wsdlDataType,
                                    basicType)) {
                        /* Some other acceptable type match. */
                        return true;
                    }
                }
                /*
                 * XPD-1491: In BPMN terms we allow mapping of xsd:any to any
                 * complex type - it is up to the user to ensure they're
                 * compatible - else the destination specific rule must overrie
                 * and prevent.
                 */
                else if (wsdlPath instanceof XsdPath
                        && ((XsdPath) wsdlPath).isXsdAny()) {
                    if (conceptPath.getType() instanceof Classifier) {
                        return true;
                    }
                }
            }
        } else if (sourceObjectInTree instanceof ScriptInformation) {
            /*
             * Script return type appropriateness is checked elsewhere (if at
             * all).
             */
            return true;
        }

        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRule#
     * isConcatenationMappingSupported()
     */
    @Override
    protected boolean isConcatenationMappingSupported() {
        /*
         * For BPMN (i.e.e generically) it is allowed - specific destinations
         * can validate against it.
         */
        return true;
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
        /*
         * For BPMN (i.e.e generically) it is allowed - specific destinations
         * can validate against it.
         */
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRule#
     * isMultiToMultiSupported(java.lang.Object, java.lang.Object)
     */
    @Override
    protected boolean isMultiToMultiSupported(Object multiInstanceSource,
            Object singleInstanceTarget) {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRule#
     * isMultiToSingleSupported(java.lang.Object, java.lang.Object)
     */
    @Override
    protected boolean isMultiToSingleSupported(Object multiInstanceSource,
            Object singleInstanceTarget) {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRule#
     * isScriptMappingSupported()
     */
    @Override
    protected boolean isScriptMappingSupported() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRule#
     * isScriptMappingSupportedForTarget(java.lang.Object)
     */
    @Override
    protected boolean isScriptMappingSupportedForTarget(Object targetObject) {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRule#
     * isSingleToMultiSupported(java.lang.Object, java.lang.Object)
     */
    @Override
    protected boolean isSingleToMultiSupported(Object singleInstanceSource,
            Object multiInstanceTarget) {
        return false;
    }

}
