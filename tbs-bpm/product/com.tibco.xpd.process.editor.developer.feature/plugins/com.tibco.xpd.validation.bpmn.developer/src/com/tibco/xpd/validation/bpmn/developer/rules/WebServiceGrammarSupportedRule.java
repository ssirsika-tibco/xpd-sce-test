/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.rules;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;

import com.tibco.xpd.bom.validator.BOMValidatorActivator;
import com.tibco.xpd.bom.wsdltransform.api.WSDLTransformUtil;
import com.tibco.xpd.bom.wsdltransform.builder.WsdlToBomBuilder;
import com.tibco.xpd.bom.xsdtransform.utils.Bom2XsdUtil;
import com.tibco.xpd.implementer.resources.xpdl2.properties.JavaScriptConceptUtil;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.WsdlFilter.WsdlDirection;
import com.tibco.xpd.implementer.script.ActivityMessageProvider;
import com.tibco.xpd.implementer.script.Xpdl2WsdlUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processeditor.xpdl2.util.WebServiceOperationUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * WebServiceGrammarMappingRule - looks for the grammar type for the activity.
 * If the grammar is Java Script, and BDS support is disabled then shows
 * unsupported error
 * 
 * 
 * @author bharge
 * @author rsomayaj
 * @since 3.3 (28 Apr 2010)
 */
public class WebServiceGrammarSupportedRule extends
        AbstractWebServiceActivityRule {

    private static final String JAVASCRIPT_MAPPINGS_NOT_ALLOWED =
            "bpmn.javaScriptGrammarNotAllowed"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String JAVA_SCRIPT = "JavaScript"; //$NON-NLS-1$

    private static final String BOM_FILES_DONT_EXIST =
            "bpmn.bomFileForJavascriptWebServiceDoesntExist"; //$NON-NLS-1$

    private static final String BOM_FILES_ARENT_UPTODATE =
            "bpmn.bomFileForJSNotUpToDate"; //$NON-NLS-1$

    private HashMap<IFile, String> referencedWsdlCheckResult;

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validate(com.tibco.xpd.xpdl2.Process)
     * 
     * @param process
     */
    @Override
    public void validate(Process process) {
        /*
         * This rule used to re-check the up-to-dateness of the BOMs generated
         * for WSDL for each individual activity. So we could save some time by
         * only checking once per process and caching the result for previous
         * activity.
         * 
         * Later (when there's more time) we could contribute a validation-tool
         * which caches this for a given validation run.
         */
        referencedWsdlCheckResult = new HashMap<IFile, String>();

        for (Activity activity : Xpdl2ModelUtil.getAllActivitiesInProc(process)) {
            validateActivity(activity);
        }

    }

    /**
     * @see com.tibco.xpd.validation.bpmn.developer.rules.AbstractWebServiceActivityRule#validateActivity(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     */
    @Override
    protected void validateActivity(Activity activity) {
        // Not interested in validating generated mappings
        if (shouldNotValidateForActivity(activity)) {
            return;
        }
        // Not interested in validating mappings other than WS mappings
        boolean isWebServiceImplementationType =
                WebServiceOperationUtil
                        .isWebServiceImplementationType(activity);
        if (!isWebServiceImplementationType) {
            return;
        }
        // Validate for required Attributes

        validateActivityForJavaScript(activity);
    }

    /**
     * @param activity
     */
    private void validateActivityForJavaScript(Activity activity) {

        if (isNonGeneratedIncomingRequestActivity(activity)
                || isCatchErrorActivity(activity)) {

            // DataMapping direction - DirectionType.OUT_LITERAL
            // WSDLDirection - IN
            String scriptGrammar =
                    ScriptGrammarFactory.getScriptGrammar(activity,
                            DirectionType.OUT_LITERAL);
            if (scriptGrammar == null) {
                scriptGrammar =
                        ScriptGrammarFactory.getDefaultScriptGrammar(activity);
            }

            validateJavaScriptInfo(activity, scriptGrammar, WsdlDirection.IN);
        } else if (isNonGeneratedReplyActivity(activity)
                || isNonGeneratedThrowFaultMessage(activity)) {
            // DataMapping direction - DirectionType.IN_LITERAL
            // WSDLDirection - OUT
            String scriptGrammar =
                    ScriptGrammarFactory.getScriptGrammar(activity,
                            DirectionType.IN_LITERAL);
            if (scriptGrammar == null) {
                scriptGrammar =
                        ScriptGrammarFactory.getDefaultScriptGrammar(activity);
            }
            validateJavaScriptInfo(activity, scriptGrammar, WsdlDirection.OUT);
        } else if (isServiceInvocationTask(activity)) {
            // DataMapping direction - DirectionType.IN_LITERAL
            // WSDLDirection - IN
            String scriptGrammar =
                    ScriptGrammarFactory.getScriptGrammar(activity,
                            DirectionType.IN_LITERAL);
            if (scriptGrammar == null) {
                scriptGrammar =
                        ScriptGrammarFactory.getDefaultScriptGrammar(activity);
            }
            validateJavaScriptInfo(activity, scriptGrammar, WsdlDirection.IN);
            // DataMapping direction - DirectionType.OUT_LITERAL
            // WSDLDirection - OUT
            scriptGrammar =
                    ScriptGrammarFactory.getScriptGrammar(activity,
                            DirectionType.OUT_LITERAL);
            if (scriptGrammar == null) {
                scriptGrammar =
                        ScriptGrammarFactory.getDefaultScriptGrammar(activity);
            }
            validateJavaScriptInfo(activity, scriptGrammar, WsdlDirection.OUT);

        } else if (isOneWayServiceInvocationTask(activity)) {
            /*
             * XPD-5861: For send task/throw message event, only check IN
             * mappings.
             */
            // DataMapping direction - DirectionType.IN_LITERAL
            // WSDLDirection - IN
            String scriptGrammar =
                    ScriptGrammarFactory.getScriptGrammar(activity,
                            DirectionType.IN_LITERAL);
            if (scriptGrammar == null) {
                scriptGrammar =
                        ScriptGrammarFactory.getDefaultScriptGrammar(activity);
            }
            validateJavaScriptInfo(activity, scriptGrammar, WsdlDirection.IN);
        }

    }

    /**
     * @param activity
     * @param scriptGrammar
     * @param wsdlDirection
     */
    private void validateJavaScriptInfo(Activity activity,
            String scriptGrammar, WsdlDirection wsdlDirection) {
        if (JAVA_SCRIPT.equals(scriptGrammar)) {
            IFile wsdlFile = Xpdl2WsdlUtil.getWsdlFile(activity);

            // show error if java script grammar is used to a wsdl that
            // has bds disabled
            if (wsdlFile != null
                    && wsdlFile.exists()
                    && BOMValidatorActivator
                            .getDefault()
                            .isValidationDestinationEnabled(wsdlFile.getProject(),
                                    BOMValidatorActivator.VALIDATION_DEST_ID_WSDL_TO_BOM)) {

                // Only validate if the WSDL to BOM component is present in the
                // destination the project is asserted to
                boolean bdsSupportEnabled =
                        WSDLTransformUtil.isBDSSupportEnabled(wsdlFile);

                if (!bdsSupportEnabled) {
                    addIssue(JAVASCRIPT_MAPPINGS_NOT_ALLOWED, activity);

                } else {
                    /**
                     * mark the activity with the problem
                     * 
                     * <li>BOMs generated for the WSDL dont exist</li>
                     * 
                     * <li>if the BOM's exist but the timestamp is less than
                     * that of the WSDL</li>
                     * 
                     * <li>if the parts of the WSDL are not BOM resolveable -
                     * check indexer whether it contains BOM Object reference
                     * for parts</li>
                     */
                    boolean isWsdlGenerated =
                            WsdlToBomBuilder.isWSDLGenerated(wsdlFile);

                    if (!isWsdlGenerated) {

                        /*
                         * SID XPD-3875: Check if we've already cached a result
                         * for this WSDL. If we do then add issue and stop
                         * checking for anything else.
                         */
                        if (referencedWsdlCheckResult.containsKey(wsdlFile)) {
                            String previousIssueForWsdl =
                                    referencedWsdlCheckResult.get(wsdlFile);

                            if (previousIssueForWsdl != null) {
                                addIssue(previousIssueForWsdl, activity);
                                return;
                            }

                        } else {
                            /*
                             * Not checked this wsdl yet.
                             */
                            String wsdlCheckResult =
                                    checkGeneratedBomsUpToDate(wsdlFile);

                            /*
                             * Cache result for next time in (for this process
                             * for this validation run.
                             */
                            referencedWsdlCheckResult.put(wsdlFile,
                                    wsdlCheckResult);

                            if (wsdlCheckResult != null) {
                                addIssue(wsdlCheckResult, activity);
                                return;
                            }
                        }

                        /*
                         * Sid: This last part seems to be a double check that
                         * of all the BOMs are up to date then make sure
                         * everything is indexsed correct and we can access the
                         * BOM classes generated for operation parts.
                         */
                        ActivityMessageProvider messageProvider =
                                JavaScriptConceptUtil.INSTANCE
                                        .getMessageProvider(activity);
                        if (messageProvider != null) {
                            WebServiceOperation wso =
                                    messageProvider
                                            .getWebServiceOperation(activity);
                            Collection<ConceptPath> webServiceChildren =
                                    JavaScriptConceptUtil.INSTANCE
                                            .getWebServiceChildren(wso,
                                                    activity,
                                                    wsdlDirection);
                            for (ConceptPath conceptPath : webServiceChildren) {
                                if (conceptPath.getType() == null) {
                                    addIssue(BOM_FILES_ARENT_UPTODATE, activity);
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * @param wsdlFile
     * @param wsdlCheckResult
     * 
     * @return <code>null</code> if ok otherwise issue found whilst checking
     *         whether Boms generated from given WSDL and it's imported XSDs are
     *         up to date (each BOM with it's respective source file)
     */
    private String checkGeneratedBomsUpToDate(IFile wsdlFile) {
        /*
         * SID XPD-3875: Get the BOM files that SHOULD be created for regardless
         * of whether they exist yet or not.
         */
        Set<IFile> bomFiles =
                WsdlToBomBuilder.getBOMFiles(wsdlFile, true, false);

        for (IFile bomFile : bomFiles) {
            if (!bomFile.isAccessible()) {
                return BOM_FILES_DONT_EXIST;
            }
        }

        /*
         * SID XPD-3875: It is not enough to compare the WSDL timestamp with ALL
         * the boms generated from that WSDL and it's imported/included XSDs.
         * This is because the generated BOM for an existing schema that is
         * imported into a new wsdl will ALWAYS be earlier that the WSDL and
         * hence appear out of date.
         * 
         * We need to check that individual BOMs as later that the individual
         * XSDs
         */

        for (IFile bomFile : bomFiles) {
            long bomTimestamp = bomFile.getLocalTimeStamp();

            /*
             * Get WSDL/XSD that are the sources of this generated BOM.
             * 
             * XPD-6062 We now have a method in Bom2XsdUtil that will use the
             * src schemas annotation to ascertain and return the WSLDs/XSDs
             * used to build bom. so we can use that rather than looking for a
             * xsd ref'd by WSDL with same simple name as in bom src
             * schemaLcoation annotation.
             */
            Set<IResource> relevantWSDLOrXSDFiles =
                    Bom2XsdUtil.findRelevantWSDLOrXSDFiles(bomFile);

            for (IResource wsdlOrXsdFile : relevantWSDLOrXSDFiles) {

                /*
                 * Check it's up to date with source wsdl/xsd.
                 */
                long wsdlOrXsdFileTimestamp = wsdlOrXsdFile.getLocalTimeStamp();
                if (bomTimestamp < wsdlOrXsdFileTimestamp) {
                    return BOM_FILES_ARENT_UPTODATE;
                }

                /*
                 * XPD-6062. Don't stop after just one file because more than
                 * one xsd can be merged into a single BOM.
                 */
            }
        }

        return null;
    }

}
