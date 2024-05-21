/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.bx.validation.internal.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Relationship;
import org.eclipse.uml2.uml.Type;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.n2.cds.script.IRestScriptRelevantData;
import com.tibco.xpd.n2.cds.script.RestJsClass;
import com.tibco.xpd.n2.cds.utils.CDSUtils;
import com.tibco.xpd.process.js.parser.validator.AbstractDotExpressionValidator;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.DefaultScriptRelevantData;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.IUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.JsMethod;
import com.tibco.xpd.script.model.client.JsMethodParam;
import com.tibco.xpd.script.model.client.JsReference;
import com.tibco.xpd.script.model.client.ParameterCoercionCriteria;
import com.tibco.xpd.script.model.client.globaldata.CaseClassCriteriaJsMethodParam;
import com.tibco.xpd.script.model.client.globaldata.CaseUMLScriptRelevantData;
import com.tibco.xpd.script.model.internal.client.ITypeResolution;
import com.tibco.xpd.script.model.jscript.JScriptUtils;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

import antlr.Token;

/**
 * @author mtorres
 * 
 *         ExpressionValidator class that handles the validation of the dot expression after a field, Class, Factory,
 *         etc... ie: FactoryClass.method(""); ie: field.method("");
 * 
 */
public class N2JScriptDotExpressionValidator extends AbstractDotExpressionValidator {

    /**
     * auditLog method name on Process java script class from PEJavaScript.uml
     */
    private static final String AUDIT_LOG_METHOD_NAME = "auditLog"; //$NON-NLS-1$

    /**
     * getAuthenticatedUser() method name on bpm.process java script class from PEJavaScript.uml
     */
    private static final String GETAUTHENTICATEDUSER_METHOD_NAME = "getAuthenticatedUser"; //$NON-NLS-1$

    /**
     * Process java script class name from PEJavascript.uml
     */
    private static final String PROCESS_JAVASCRIPT_CLASS_NAME = "Process"; //$NON-NLS-1$

    private static final String ADD_ACTIVITY_LOOP_ADDITIONAL_INSTANCES_METHOD = "addActivityLoopAdditionalInstances"; //$NON-NLS-1$

    private static final String[] activityLoopAdditionalForbiddenContexts = new String[] {};

    private static final String[] activityLoopAdditionalAllowedRecursiveContexts =
            new String[] { "CompletedScriptTask" }; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.script.parser.validator.jscript.JScriptDotExpressionValidator#performSpecificMethodValidation(java.lang.String,
     *      com.tibco.xpd.script.model.client.IScriptRelevantData,
     *      com.tibco.xpd.script.model.client.IScriptRelevantData, com.tibco.xpd.script.model.client.JsMethod,
     *      com.tibco.xpd.script.model.client.IScriptRelevantData, boolean, java.util.Map, antlr.Token)
     * 
     * @param methodName
     * @param currentGenericContext
     * @param newGenericContext
     * @param matchMethod
     * @param returnType
     * @param isArrayContext
     * @param parameters
     * @param token
     */
    @Override
    protected void performSpecificMethodValidation(String methodName, IScriptRelevantData currentGenericContext,
            IScriptRelevantData newGenericContext, JsMethod matchMethod, IScriptRelevantData returnType,
            boolean isArrayContext, Map<String, IScriptRelevantData> parameters, Token token) {

        /*
         * SID ACE-2424 - MOST of this module gets removed because it is all based on validating DQL / CRITERIA string
         * parameters for old CASREF.xxxx() methods and cac_xxx.yyy() methods which have been superseded in ACE.
         * 
         * 
         * Leaving this method behind as a hint to ACE-1833 which is due to re-do the DQL string validation. It will
         * likely use a different method (such as creating a new primitive type 'DQLString' based on text type and
         * therefore be able to tag DQL paramters in function schema UML as DQLString and detect that here.
         */

        JsMethodParam[] methodParams = matchMethod.getParameterType().toArray(new JsMethodParam[0]);

        IScriptRelevantData[] actualParams = parameters.values().toArray(new IScriptRelevantData[0]);

        String[] actualParamNames = parameters.keySet().toArray(new String[0]);

        for (int i = 0; i < methodParams.length; i++) {

            if (i < actualParams.length) {

                if (JsConsts.DQL_STRING.equals(methodParams[i].getType())) {

                    /*
                     * validate the DQL String. this calls BDS API to validate
                     */
                    // handleDQLStringCase(currentGenericContext,
                    // matchMethod,
                    // token,
                    // actualParamNames[i]);
                } else if (JsConsts.CASE_TYPE_NAME.equals(methodParams[i].getType())) {
					handleTypeName(token, actualParamNames[i], true);
                } else if (JsConsts.CLASS_TYPE_NAME.equals(methodParams[i].getType())) {
					// ACE-7310 Nikita Handle new type method introduced to convert BOM fields to and from JSON strings
					// in process Script
					handleTypeName(token, actualParamNames[i], false);
                } else if (JsConsts.ASSOCIATION_LINK_NAME.equals(methodParams[i].getType())) {
                    handleAssociationLinkName(token, actualParamNames[i]);
                } else if (JsConsts.CRITERIA.equals(methodParams[i].getType())) {
                    /*
                     * XPD-5976: validate the correct criteria context for expected param and actual param
                     * (currentGenericContext has the expected param context. try to get the actual case class context
                     * from actual params)
                     */
                    CaseClassCriteriaJsMethodParam caseClassCriteriaJsMethodParam = null;
                    if (actualParams[i] instanceof DefaultScriptRelevantData) {

                        DefaultScriptRelevantData defaultScriptRelevantData =
                                (DefaultScriptRelevantData) actualParams[i];
                        Object extendedInfo = defaultScriptRelevantData.getExtendedInfo();
                        if (extendedInfo instanceof CaseClassCriteriaJsMethodParam) {

                            caseClassCriteriaJsMethodParam = (CaseClassCriteriaJsMethodParam) extendedInfo;
                        }

                    }
                    if (null != caseClassCriteriaJsMethodParam) {

                        // handleCriteriaSpecificCase(currentGenericContext,
                        // matchMethod,
                        // token,
                        // caseClassCriteriaJsMethodParam);
                    }
                }
            }
        }

        super.performSpecificMethodValidation(methodName,
                currentGenericContext,
                newGenericContext,
                matchMethod,
                returnType,
                isArrayContext,
                parameters,
                token);
    }

    /**
     * Validates the DQL Query string using the given class as case class context and displays error/warning message
     * returned from the DQL API
     * 
     * @param token
     * @param contextCaseClass
     * @param dqlQuery
     */
    private void validateDQLQueryString(Token token, Class contextCaseClass, String dqlQuery) {

        if (null != contextCaseClass) {
            /**
             * SID ACE-122 - we are removing com.tibco.bds.designtime.feature as it is mainly for unsupported things in
             * ACE. DQL 9case data queery language will survive in some form BUT will be very different and much more
             * simple. I've spoken to Joshy and Simon and they have agreed that this will be the case. Thereore
             * commenting this peice out and throwing an exception so that we can come back later and replace with
             * something else.
             */
            addErrorMessage(token, "SCE: DQL Query string validation requires port to new DQL language."); //$NON-NLS-1$

            // try {
            //
            // List<ValidationResult> validateDQL =
            // BDSAPI.validateDQL(contextCaseClass, dqlQuery);
            //
            // if (!validateDQL.isEmpty()) {
            //
            // for (ValidationResult validationResult : validateDQL) {
            //
            // List<ValidationResult> errors =
            // validationResult.getErrors(validateDQL);
            // if (!errors.isEmpty()) {
            //
            // for (ValidationResult err : errors) {
            //
            // StringBuffer errMsg = new StringBuffer(
            // Messages.N2JScriptDotExpressionValidator_InvalidDQLQueryStringMsg);
            // errMsg.append(err.getMessage());
            // addErrorMessage(token, errMsg.toString());
            // }
            // }
            //
            // List<ValidationResult> warnings =
            // validationResult.getWarnings(validateDQL);
            // if (errors.isEmpty() && !warnings.isEmpty()) {
            //
            // for (ValidationResult warn : warnings) {
            //
            // StringBuffer warningMsg = new StringBuffer(
            // Messages.N2JScriptDotExpressionValidator_DQLQueryWarningMsg);
            // warningMsg.append(warn.getMessage());
            // addWarningMessage(token, warningMsg.toString());
            // }
            // }
            // }
            // }
            // } catch (DQLValidationException e) {
            //
            // BxValidationPlugin.getDefault().getLogger()
            // .error(e.getMessage());
            // String errMsg =
            // Messages.N2JScriptDotExpressionValidator_ErrorValidatingDQLQueryString;
            // addErrorMessage(token, errMsg);
            // }
        }
    }

    @SuppressWarnings("restriction")
    @Override
    protected boolean isSupportedMethod(List<IScriptRelevantData> types, String methodName, Token token,
            Map<String, IScriptRelevantData> parameters) {
        for (IScriptRelevantData type : types) {

            if (type != null && type.getType() != null && methodName != null) {

                List<JsClass> supportedJsClasses = getSupportedJsClasses(getInfoObject());
                if (JScriptUtils.isDynamicComplexType(type, supportedJsClasses)) {

                    if (!(type instanceof CaseUMLScriptRelevantData)) {

                        String message = Messages.N2JScriptDotExpressionValidator_OperationfromComplextypesDisallowed;
                        addErrorMessage(token, message);
                        return true;
                    }
                }

                /*
                 * Sid ACE-194 - we don't have XSD derived BOMs any more - removed XSD base rules
                 */

                String genericType = convertSpecificToGenericType(type);
                if (genericType != null) {
					/*
					 * Nikita - ACE-7011 Now we support the split method for String objects. Removed the check to
					 * disable it.
					 * 
					 * Due to inadequate code commentary not really sure why it was added in the first place. Tested e2e
					 * to confirm 'split' works okay
					 */

					if (genericType.equals(JsConsts.REGULAR_EXPRESSION))
					{
                        if (methodName.equals("exec")) {//$NON-NLS-1$
                            return false;
                        }
                    } else if (PROCESS_JAVASCRIPT_CLASS_NAME.equals(genericType)) {

                        /*
                         * XPD-6022: auditLog() method on Process java script class from PEJavaScript.uml must be
                         * available to audit messages only from business processes. If this method is used in Pageflow
                         * process or Business Service then a warning must be shown
                         */
                        EObject input = getInput(getInfoObject());
                        Process process = Xpdl2ModelUtil.getProcess(input);

                        if (AUDIT_LOG_METHOD_NAME.equals(methodName)) {

                            if (Xpdl2ModelUtil.isPageflow(process)
                                    || Xpdl2ModelUtil.isPageflowBusinessService(process)) {

                                String message =
                                        Messages.N2JScriptDotExpressionValidator_ProcessAuditLogMethodContext_message;
                                List<String> additionalAttributes = new ArrayList<String>();
                                additionalAttributes.add(methodName);
                                addWarningMessage(token, message, additionalAttributes);

                            } else if (Xpdl2ModelUtil.isServiceProcess(process)) {
                                /*
                                 * Sid XPD-8271: Warn that Process.auditLog() is not available in serviec process
                                 * scripts.
                                 */
                                String message =
                                        Messages.N2JScriptDotExpressionValidator_ServiceProcessAuditLogMethodContext_message;
                                List<String> additionalAttributes = new ArrayList<String>();
                                additionalAttributes.add(methodName);
                                addWarningMessage(token, message, additionalAttributes);

                            }
                        }
                        /*
                         * Sid ACE-6616 handle validation against use of bpm.process.getAuthenticatedUser() is
                         * process-engine executed scripts.
                         */
                        if (GETAUTHENTICATEDUSER_METHOD_NAME.equals(methodName)) {
                            /*
                             * bpm.process.getAuthenticatedUser() can be used in any pageflow-derived process type (as
                             * there will always be an authenticated user for these)
                             */
                            if (!Xpdl2ModelUtil.isPageflowOrSubType(process)) {
                                /*
                                 * You can't use it in service-process with the business-process deployment target (but
                                 * just pageflow target is ok)
                                 */
                                if (Xpdl2ModelUtil.isServiceProcess(process)) {
                                    if (ProcessInterfaceUtil.isProcessEngineServiceProcess(process)) {
                                        /* Service process with business process deploy target so can't use it here */
                                        String message =
                                                Messages.N2JScriptDotExpressionValidator_MethodNotSupportedInProcessEngineServiceProcess_message;
                                        List<String> additionalAttributes = new ArrayList<String>();
                                        additionalAttributes.add(methodName);
                                        addErrorMessage(token, message, additionalAttributes);
                                    }
                                } else {
                                    /* Not a pageflow process and not a service-process so can't use it here */
                                    String message =
                                            Messages.N2JScriptDotExpressionValidator_MethodNotSupportedInBusinessProcess_message;
                                    List<String> additionalAttributes = new ArrayList<String>();
                                    additionalAttributes.add(methodName);
                                    addErrorMessage(token, message, additionalAttributes);

                                }
                            }
                        }
                    }
                }
            }
        }
        if (methodName != null && methodName.equals(ADD_ACTIVITY_LOOP_ADDITIONAL_INSTANCES_METHOD)) {
            validateAddActivityLoopAdditionalInstancesMethod(methodName, token, parameters);
        }
        
        /*
         * Sid ACE-3309 bpm.process.getContextVariable() and setVariable() removed from process JS class, so no need to
         * validate them out here anymore
         */

        return true;
    }

    @SuppressWarnings("restriction")
    protected void validateAddActivityLoopAdditionalInstancesMethod(String methodName, Token token,
            Map<String, IScriptRelevantData> parameters) {
        String scriptType = getScriptType(getInfoObject());
        EObject input = getInput(getInfoObject());
        Process process = null;
        String targetActivity = null;
        if (input != null) {
            process = Xpdl2ModelUtil.getProcess(input);
        }
        // Get the parameter
        Entry<String, IScriptRelevantData> firstParameter = parameters.entrySet().iterator().next();
        if (firstParameter != null) {
            if (process != null) {
                String key = firstParameter.getKey();
                if (key != null && key.contains("arg1#")) { //$NON-NLS-1$
                    String paramValue = key.replace("arg1#", ""); //$NON-NLS-1$//$NON-NLS-2$
                    if (paramValue != null && paramValue.length() > 0) {
                        targetActivity = paramValue;
                    }
                }
            }
        }

        if (scriptType != null) {
            for (String forbidContext : activityLoopAdditionalForbiddenContexts) {
                if (forbidContext.equals(scriptType)) {
                    String message = Messages.N2JScriptDotExpressionValidator_AddActivityLoopAdditionalContext;
                    List<String> additionalAttributes = new ArrayList<String>();
                    additionalAttributes.add(ADD_ACTIVITY_LOOP_ADDITIONAL_INSTANCES_METHOD);
                    addErrorMessage(token, message, additionalAttributes);
                    return;
                }
            }

            if (input == null || process == null || targetActivity == null) {
                String message = Messages.N2JScriptDotExpressionValidator_UnableToValidateActivityName;
                List<String> additionalAttributes = new ArrayList<String>();
                additionalAttributes.add(ADD_ACTIVITY_LOOP_ADDITIONAL_INSTANCES_METHOD);
                addWarningMessage(token, message, additionalAttributes);
                return;
            } else {
                Activity activityByName = Xpdl2ModelUtil.getActivityByName(process, targetActivity);
                if (activityByName == null) {
                    String message = Messages.N2JScriptDotExpressionValidator_TargetActivityDoesNotExist;
                    List<String> additionalAttributes = new ArrayList<String>();
                    additionalAttributes.add(targetActivity);
                    addErrorMessage(token, message, additionalAttributes);
                    return;
                }
                boolean allowedRecursive = false;
                for (String allowedRecursiveContext : activityLoopAdditionalAllowedRecursiveContexts) {
                    if (allowedRecursiveContext.equals(scriptType)) {
                        allowedRecursive = true;
                        break;
                    }
                }
                if (!allowedRecursive) {
                    // Get the import to make sure
                    if (activityByName.equals(input)) {
                        String message = Messages.N2JScriptDotExpressionValidator_TargetActivityCannotBeSource;
                        List<String> additionalAttributes = new ArrayList<String>();
                        additionalAttributes.add(targetActivity);
                        addErrorMessage(token, message, additionalAttributes);
                        return;
                    }
                }
            }
        }
    }

    @Override
    protected boolean isValidAssignment(IScriptRelevantData lhsDataType, IScriptRelevantData rhsDataType) {

        if (lhsDataType != null && CDSUtils.isEObjectType(lhsDataType.getType())) {

            if (rhsDataType != null) {

                if (JScriptUtils.isGenericType(rhsDataType.getType())) {

                    if (rhsDataType instanceof ITypeResolution) {

                        IScriptRelevantData genericContextType =
                                ((ITypeResolution) rhsDataType).getGenericContextType();
                        if (genericContextType != null) {

                            if (!genericContextType.isArray() && CDSUtils.isBDSObject(genericContextType)) {

                                return true;
                            }
                        }
                    }
                }
                /*
                 * XPD-2178: if xsdAnyType has an assignment with ScriptUtils.copy on the rhs it is a valid assignment.
                 * rest other assignments using ScriptUtils.copy is invalid
                 */
                if (JScriptUtils.isXsdAnyType(rhsDataType)) {

                    return true;
                }

                if (rhsDataType.isArray() == lhsDataType.isArray() && CDSUtils.isBDSObject(rhsDataType)) {

                    if (rhsDataType instanceof CaseUMLScriptRelevantData) {
                        /*
                         * XPD-5705: Return false if a case ref type is assigned to a BOM Class.
                         */
                        return false;
                    }

                    return true;
                }
            }
        }

        return super.isValidAssignment(lhsDataType, rhsDataType);
    }

    @SuppressWarnings({ "deprecation", "restriction" })
    @Override
    protected IScriptRelevantData getCurrentSpecialGenericContextForMethod(IScriptRelevantData currentGenericContext,
            JsMethod mathMethod, Map<String, IScriptRelevantData> parameters) {
        if (parameters != null && mathMethod != null) {
            if (JScriptUtils.isStaticMethod(mathMethod) && JScriptUtils.isAnEMethod(mathMethod)
                    && CDSUtils.expectsEObjectParam(mathMethod)) {
                for (String key : parameters.keySet()) {
                    if (key != null) {
                        IScriptRelevantData scriptRelevantData = parameters.get(key);
                        if (scriptRelevantData != null) {
                            if (JScriptUtils.isGenericType(scriptRelevantData.getType())) {
                                if (scriptRelevantData instanceof ITypeResolution) {
                                    IScriptRelevantData genericContextType =
                                            ((ITypeResolution) scriptRelevantData).getGenericContextType();
                                    if (genericContextType != null) {
                                        if (!genericContextType.isArray() && CDSUtils.isBDSObject(genericContextType)) {
                                            return genericContextType;
                                        }
                                    }
                                }
                            }
                            if (CDSUtils.isBDSObject(scriptRelevantData)) {
                                return scriptRelevantData;
                            }
                        }
                    }
                }
            } else if (JScriptUtils.getContextParameter(mathMethod) != null) {
                String contextParameter = JScriptUtils.getContextParameter(mathMethod);
                for (String key : parameters.keySet()) {
                    if (key != null && key.startsWith(contextParameter + "#")) {//$NON-NLS-1$
                        String contextType = key.replaceFirst(contextParameter + "#", ""); //$NON-NLS-1$//$NON-NLS-2$
                        if (contextType != null && getSymbolTable(getInfoObject()) != null) {
                            Map<String, IScriptRelevantData> scriptRelevantDataTypeMap =
                                    getSymbolTable(getInfoObject()).getScriptRelevantDataTypeMap();
                            if (scriptRelevantDataTypeMap != null && !scriptRelevantDataTypeMap.isEmpty()) {
                                for (IScriptRelevantData scriptRelevantDataType : scriptRelevantDataTypeMap.values()) {
                                    if (scriptRelevantDataType instanceof IUMLScriptRelevantData) {
                                        IUMLScriptRelevantData umlScriptRelevantData =
                                                (IUMLScriptRelevantData) scriptRelevantDataType;
                                        JsClass jsClass = umlScriptRelevantData.getJsClass();
                                        if (jsClass != null && jsClass.getUmlClass() != null) {
                                            String qualifiedName = jsClass.getUmlClass().getQualifiedName();
                                            if (qualifiedName != null) {
                                                String umlName = qualifiedName.replace("::", "."); //$NON-NLS-1$//$NON-NLS-2$
                                                if (contextType.equals(umlName)) {
                                                    return createUMLScriptRelevantData(jsClass
                                                            .getName(), false, jsClass.getUmlClass(), null, null);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return createScriptRelevantData(JsConsts.UNDEFINED_DATA_TYPE,
                        JsConsts.UNDEFINED_DATA_TYPE,
                        false,
                        null,
                        null);
            }
        }
        return super.getCurrentSpecialGenericContextForMethod(currentGenericContext, mathMethod, parameters);
    }

    @Override
    protected boolean isExplicitAssignmentAllowance(IScriptRelevantData lhsDataType, IScriptRelevantData rhsDataType) {

        /*
         * Sid ACE-194 - we don't have XSD derived BOMs any more - removed XSD base rules
         */

        if (isDQLQueryMethodParam(lhsDataType) && isValidToAssignToDQLParam(rhsDataType)) {
            /*
             * XPD-5493. Have to allow String->DQLString for _method parameters_ as valid assignments. The
             * performSpecificMethodValidation() method above will validate further that they are string literals and
             * validate them against DQL Query language
             */
            return true;
        } else if (isCaseTypeNameQueryMethodParam(lhsDataType) && isValidStringLiteral(rhsDataType)) {
            return true;
        } else if (isClassTypeNameQueryMethodParam(lhsDataType) && isValidStringLiteral(rhsDataType)) {
			// ACE-7310 Nikita Handle new type method introduced to convert BOM fields to and from JSON strings in
			// process Script
            return true;
        } else if (isAssociationLinkNameQueryMethodParam(lhsDataType)
                && isValidToAssignToAssociationLinkNameParam(rhsDataType)) {
            return true;
        } else {

            /*
             * XPD-4445: If LHS is a case reference, then check that RHS is compatible case reference.
             * 
             * for eg. superRefTypeList.add(subRefType) is allowed but subRefTypeList.add(superRefType) is not
             */
            ParameterCoercionCriteria typeCoercionCriteria = null;

            if (lhsDataType instanceof DefaultScriptRelevantData) {

                typeCoercionCriteria = ((DefaultScriptRelevantData) lhsDataType).getParamCoercionCriteria();
            }

            if (typeCoercionCriteria != null) {
                /*
                 * Handle subType / superType coercion checking. (WHen explicitly stated on LHS)
                 */
                Class lhsCaseRefUmlClass = resolveClassType(lhsDataType);

                Class rhsCaseRefUmlClass = null;

                if (lhsCaseRefUmlClass != null) {

                    rhsCaseRefUmlClass = resolveClassType(rhsDataType);
                }

                if (null != lhsCaseRefUmlClass && null != rhsCaseRefUmlClass) {

                    /*
                     * this is to allow cac casting between super type and sub type.
                     * 
                     * for eg. cac_Customer.customerRef(personRef) must be allowed
                     */
                    if (ParameterCoercionCriteria.SUPER_TYPE_TO_SUB_TYPE.equals(typeCoercionCriteria)) {

                        boolean lhsSubTypeOfRhs = JScriptUtils.isSubType(lhsCaseRefUmlClass, rhsCaseRefUmlClass);
                        if (lhsSubTypeOfRhs || lhsCaseRefUmlClass.equals(rhsCaseRefUmlClass)) {

                            return true;
                        }
                    }
                    /*
                     * this is to allow assignments between super type and sub type for cac create/read/update/delete
                     * methods
                     */
                    else if (ParameterCoercionCriteria.SUB_TYPE_TO_SUPER_TYPE.equals(typeCoercionCriteria)) {

                        boolean rhsSubTypeOfLhs = JScriptUtils.isSubType(rhsCaseRefUmlClass, lhsCaseRefUmlClass);
                        if (rhsSubTypeOfLhs || lhsCaseRefUmlClass.equals(rhsCaseRefUmlClass)) {

                            return true;
                        }

                    } else if (ParameterCoercionCriteria.SUB_TYPE_AND_SUPER_TYPE.equals(typeCoercionCriteria)) {

                        if (JScriptUtils.isSubType(rhsCaseRefUmlClass, lhsCaseRefUmlClass)
                                || JScriptUtils.isSubType(lhsCaseRefUmlClass, rhsCaseRefUmlClass)) {

                            return true;
                        }
                    }

                    return false;
                }
            }
        }

        return super.isExplicitAssignmentAllowance(lhsDataType, rhsDataType);
    }

    /**
     * @param scritpRelDataType
     * @return <code>true</code> if data type is valid to assign to DQL query string method parameter.
     */
    private boolean isValidToAssignToDQLParam(IScriptRelevantData scritpRelDataType) {

        String dataType = scritpRelDataType.getType();
        if (JsConsts.STRING.equals(dataType) || JsConsts.STRING_LITERAL.equals(dataType)
                || JsConsts.TEXT.equals(dataType) || JsConsts.DQL_STRING.equals(dataType)) {

            return true;
        }

        return false;
    }

    /**
     * @param srd
     * @return <code>true</code> if the given script relvant data is fort a method param of DQLString type.
     */
    private boolean isDQLQueryMethodParam(IScriptRelevantData srd) {

        if (JsConsts.DQL_STRING.equals(srd.getType())) {

            if (srd instanceof DefaultScriptRelevantData) {

                if (((DefaultScriptRelevantData) srd).getExtendedInfo() instanceof JsMethodParam) {

                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param scritpRelDataType
     *            The data type.
     * @return <code>true</code> if data type is valid to assign to a Case Type Name string method parameter.
     */
	private boolean isValidStringLiteral(IScriptRelevantData scritpRelDataType)
	{
        String dataType = scritpRelDataType.getType();
        if (JsConsts.STRING.equals(dataType) || JsConsts.STRING_LITERAL.equals(dataType)
                || JsConsts.TEXT.equals(dataType)) {
            return true;
        }
        return false;
    }

    /**
     * @param srd
     *            The data type.
     * @return <code>true</code> if the given script relevant data is for a method param of Case Type Name type.
     */
    private boolean isCaseTypeNameQueryMethodParam(IScriptRelevantData srd) {
        if (JsConsts.CASE_TYPE_NAME.equals(srd.getType())) {
            if (srd instanceof DefaultScriptRelevantData) {
                if (((DefaultScriptRelevantData) srd).getExtendedInfo() instanceof JsMethodParam) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
	 * ACE-7310 Nikita Handle new type method introduced to convert BOM fields to and from JSON strings in process
	 * Script
	 * 
	 * @param srd
	 *            The data type.
	 * @return <code>true</code> if the given script relevant data is for a method param of Class Type Name type.
	 */
	private boolean isClassTypeNameQueryMethodParam(IScriptRelevantData srd) {
		if (JsConsts.CLASS_TYPE_NAME.equals(srd.getType())) {
			if (srd instanceof DefaultScriptRelevantData) {
				if (((DefaultScriptRelevantData) srd).getExtendedInfo() instanceof JsMethodParam) {
					return true;
				}
			}
		}
		return false;
	}

    /**
     * @param scritpRelDataType
     *            The data type.
     * @return <code>true</code> if data type is valid to assign to AssociationLink string method parameter.
     */
    private boolean isValidToAssignToAssociationLinkNameParam(IScriptRelevantData scritpRelDataType) {
        String dataType = scritpRelDataType.getType();
        if (JsConsts.STRING.equals(dataType) || JsConsts.STRING_LITERAL.equals(dataType)
                || JsConsts.TEXT.equals(dataType)) {
            return true;
        }
        return false;
    }

    /**
     * @param srd
     *            The data type.
     * @return <code>true</code> if the given script relevant data is for a method param of AssociationLink type.
     */
    private boolean isAssociationLinkNameQueryMethodParam(IScriptRelevantData srd) {
        if (JsConsts.ASSOCIATION_LINK_NAME.equals(srd.getType())) {
            if (srd instanceof DefaultScriptRelevantData) {
                if (((DefaultScriptRelevantData) srd).getExtendedInfo() instanceof JsMethodParam) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Resolve a script relevant data to a case reference's underlying case-class type IF the relevant data is a case
     * reference / case reference list.
     * 
     * @param dataType
     * 
     * @return Case-class type or <code>null</code> if the script relevant data is not a case reference.
     */
    private Class resolveClassType(IScriptRelevantData dataType) {

        if (dataType instanceof CaseUMLScriptRelevantData) {

            IScriptRelevantData resolvedType = JScriptUtils.resolveGenericType(dataType);

            if (resolvedType != null) {

                JsClass jsClass = ((IUMLScriptRelevantData) resolvedType).getJsClass();

                if (jsClass != null) {
                    return jsClass.getUmlClass();
                }
            }

        } else if (dataType instanceof DefaultScriptRelevantData) {

            DefaultScriptRelevantData defaultScriptRelevantData = (DefaultScriptRelevantData) dataType;
            IScriptRelevantData dataTypeGC = defaultScriptRelevantData.getGenericContextType();

            if (dataTypeGC instanceof CaseUMLScriptRelevantData) {
                return resolveClassType(((DefaultScriptRelevantData) dataType).getGenericContextType());

            } else if (dataTypeGC instanceof IUMLScriptRelevantData) {
                /*
                 * for cac classes generic context is DefaultUMLScriptRelevantData, so in order to check for
                 * subtype-supertype hierarchy we need the uml class representation of cac which is at the moment ONLY
                 * possible to get the following way!
                 */
                JsClass dataTypeJsClass = ((IUMLScriptRelevantData) dataTypeGC).getJsClass();

                return dataTypeJsClass.getUmlClass();
            }
        }

        return null;
    }

    @Override
    protected boolean isExplicitAssignmentRestriction(IScriptRelevantData lhsDataType,
            IScriptRelevantData rhsDataType) {
        /*
         * Sid ACE-194 - we don't have XSD derived BOMs any more - removed XSD base rules
         */

        /*
         * XPD-5705: ONLY check when both are Global data CaseRef types, Do not allow using BOM types and Case Ref types
         * interchangeably. Case Ref types can ONLY be assigned from Case Ref types and not BOM type process data or
         * Object type.
         */
        boolean validCaseRefAssignment = isValidCaseRefAssignment(lhsDataType, rhsDataType);

        if (!validCaseRefAssignment) {

            return true;
        }

        if (JScriptUtils.isEObjectType(lhsDataType) && JScriptUtils.isDynamicComplexType(rhsDataType)) {

            if (rhsDataType instanceof CaseUMLScriptRelevantData) {
                /*
                 * XPD-5705: Return false if a case ref type is assigned to a BOM Class.
                 */
                return false;
            }
            return true;
        }
        return super.isExplicitAssignmentRestriction(lhsDataType, rhsDataType);
    }

    /**
     * @see com.tibco.xpd.script.parser.internal.validator.jscript.AbstractExpressionValidator#getParamCoercionCriteria(com.tibco.xpd.script.model.client.JsMethodParam,
     *      com.tibco.xpd.script.model.client.IScriptRelevantData)
     * 
     * @param jsMethodParam
     * @param dataType
     * @return
     */
    @SuppressWarnings("restriction")
    @Override
    protected ParameterCoercionCriteria getParamCoercionCriteria(JsMethodParam jsMethodParam,
            IScriptRelevantData dataType) {

        ParameterCoercionCriteria paramCoercionCriteria = super.getParamCoercionCriteria(jsMethodParam, dataType);

        if (paramCoercionCriteria != null) {
            /* Coercion explicitly set in the parameter so use that */
            return paramCoercionCriteria;
        }

        /*
         * If the assignment coercion if not explicitly defined in the the parameter, then we need to cover the SPECIAL
         * case whereby...
         * 
         * - Parameters to methods in Lists of BOM Class objects are SubType OR SuperType compatible (you can use either
         * in MyBOmArrayField.add(bomField))
         * 
         * - Parameters to methods in Lists of CaseRef's are SubType compatible ONLY (parameter to
         * PersonRefArray.add(refField) MUST be PersonRef OR a SubType of PersonRef such as CustomerRef).
         * 
         * So if the parameter (lhsDataType) is a case ref AND we are in a List class method, then enforce subType
         * compatibility.
         */
        if (paramCoercionCriteria == null) {

            if (dataType instanceof CaseUMLScriptRelevantData) {

                if (isListMethodParam(jsMethodParam)) {

                    paramCoercionCriteria = ParameterCoercionCriteria.SUB_TYPE_TO_SUPER_TYPE;
                }
            }
        }

        return paramCoercionCriteria;
    }

    /**
     * @param jsMethodParam
     * @return <code>true</code> if the parameter is from List or PaginatedList class method.
     */
    private boolean isListMethodParam(JsMethodParam jsMethodParam) {

        Parameter parameter = jsMethodParam.getUMLParameter();

        if (parameter != null) {
            EObject eContainer = parameter.eContainer().eContainer();

            if (eContainer instanceof Class) {
                Class paramClass = (Class) eContainer;

                if (paramClass.getPackage() != null) {
                    /* Check if it's the default "List" class. */
                    Class defaultCDSMultipleClass = CDSUtils.getDefaultCDSMultipleClass();

                    if (defaultCDSMultipleClass != null && defaultCDSMultipleClass.getPackage() != null) {

                        if (defaultCDSMultipleClass.getName().equals(paramClass.getName()) && defaultCDSMultipleClass
                                .getPackage().getName().equals(paramClass.getPackage().getName())) {

                            return true;
                        }
                    }

                }

            }
        }

        return false;
    }

    /**
     * This method checks if the assignment is a valid Case Ref mapping. CaseRef type can only be assigned to/from
     * CaseRef type. Returns true if both lhs and rhs are of CaseRef type.
     * 
     * @param lhsDataType
     * @param rhsDataType
     * @return true if both source and target are of CaseRef type. CaseRef type can only be assignment to a CaseRef
     *         type.
     */
	@SuppressWarnings("restriction")
	private boolean isValidCaseRefAssignment(IScriptRelevantData lhsDataType, IScriptRelevantData rhsDataType)
	{

        boolean validCaseRefAssignment = true;

        /**
         * Sid XPD-7360. Returns / params for list methods won't necessarily be directly CaseUMLScriptRelevantData (for
         * example CaseRefArray.subList(1, 3) will return type "List" with a generic context specific type reference
         * inside.
         * 
         * So if we're given a generic context type object then switch to the proper context specific type.
         */

        if (JScriptUtils.isContextlessType(rhsDataType) && rhsDataType instanceof ITypeResolution) {
            rhsDataType = ((ITypeResolution) rhsDataType).getGenericContextType();
        }

        if (JScriptUtils.isContextlessType(lhsDataType) && rhsDataType instanceof ITypeResolution) {
            lhsDataType = ((ITypeResolution) lhsDataType).getGenericContextType();
        }

		/*
		 * Sid ACE-8226 There is now a wider definition of what constitutes a case-reference other than generic any-case
		 * reference data (specific case-ref type params, process data class caseRef attributes, top level PSL function
		 * caseRef parameters) etc So use isCaseReference() function that deals with them all.
		 */
		boolean lhsIsCaseRef = isCaseReference(lhsDataType);
		boolean rhsisCaseRef = isCaseReference(rhsDataType);

		/*
		 * Valid when RHS is case reference and LHS is generic Object (which is also allowed for things like
		 * bpm.scriptUtil.copy(textField)
		 */
		if (!JsConsts.OBJECT.equals(lhsDataType.getType()))
		{
		    /*
		     * Invalid when lhs is CaseUMLScriptRelevantData and rhs is NOT CaseUMLScriptRelevantData
		     */
		
			if (lhsIsCaseRef && !rhsisCaseRef)
			{
		
		        validCaseRefAssignment = false;
		
		    }
		    /*
		     * Invalid when lhs is NEITHER CaseUMLScriptRelevantData NOR Object, but rhs is CaseUMLScriptRelevantData.
		     */
			if (!lhsIsCaseRef && rhsisCaseRef)
			{
		
		        validCaseRefAssignment = false;
		
		    }
		}
		
        return validCaseRefAssignment;
    }

    /**
     * Overridden to ensure that RestUMLScriptRelevantData are converted to RestJsClass instances.
     * 
     * @see com.tibco.xpd.script.parser.internal.validator.jscript.AbstractExpressionValidator#getJsClass(com.tibco.xpd.script.model.client.IUMLScriptRelevantData,
     *      com.tibco.xpd.script.model.client.JsReference)
     * 
     * @param context
     *            The context.
     * @param jsReference
     *            The JsReference.
     * @return The corresponding JsClass.
     */
    @Override
    protected JsClass getJsClass(IUMLScriptRelevantData context, JsReference jsReference) {
        JsClass cls = super.getJsClass(context, jsReference);
        if (context instanceof IRestScriptRelevantData) {
            Class umlClass = cls.getUmlClass();
            cls = new RestJsClass(umlClass, null);
            cls.setIcon(jsReference.getIcon());
        }
        return cls;
    }

    /**
	 * Validates that the case type name parameter refers to a valid case class.
	 * 
	 * @param token
	 *            The token being parsed.
	 * @param actualParamName
	 *            The parameter name to check.
	 * @param isCaseType
	 *            The boolean flag to indicate if the type is a Case type name
	 */
	private void handleTypeName(Token token, String actualParamName, boolean isCaseType)
	{

        String stringLiteralValue = null;

        int hashIdx = actualParamName.indexOf('#');
        if (hashIdx >= 0) {
            stringLiteralValue = actualParamName.substring(hashIdx + 1);
        }

        if (stringLiteralValue != null) {
            // Check that it's a valid case or class type name.
            boolean valid = false;
            int lastDot = stringLiteralValue.lastIndexOf('.');
            if (lastDot != -1 && (lastDot + 1) < stringLiteralValue.length()) {
                String packageName = stringLiteralValue.substring(0, lastDot);
                String className = stringLiteralValue.substring(lastDot + 1);
                EObject input = getInput(getInfoObject());
                if (input != null) {
                    Process process = Xpdl2ModelUtil.getProcess(input);
                    Set<Package> packages = CDSUtils.getReferencedBomPackages(process);
                    for (Package pkg : packages) {
                        if (packageName.equals(pkg.getName())) {
                            Type cls = pkg.getOwnedType(className);
                            if (cls instanceof Class) {
                            	if (isCaseType) {
                            		if (BOMGlobalDataUtils.isCaseClass((Class) cls)) {
                            			valid = true;
                                        break;		
                            		}
                            	} else {
                            		valid = true;
                                    break;	
                            	}                                
                            }
                        }
                    }
                }
            }
            if (!valid) {
				addErrorMessage(token, isCaseType ? Messages.N2JScriptDotExpressionValidator_invalidCaseTypeName
						: Messages.N2JScriptDotExpressionValidator_invalidClassTypeName);
            }
        } else {
			addErrorMessage(token, isCaseType ? Messages.N2JScriptDotExpressionValidator_nonLiteralCaseTypeName
					: Messages.N2JScriptDotExpressionValidator_nonLiteralClassTypeName);
        }
    }

    /**
     * Validates that the parameter refers to a valid association link name.
     * 
     * @param token
     *            The token being parsed.
     * @param actualParamName
     *            The parameter name to check.
     */
    private void handleAssociationLinkName(Token token,
            String actualParamName) {

        String stringLiteralValue = null;

        int hashIdx = actualParamName.indexOf('#');
        if (hashIdx >= 0) {
            stringLiteralValue = actualParamName.substring(hashIdx + 1);
        }

        if (stringLiteralValue != null) {
            // Check that it's a valid association link name
            boolean valid = false;
            EObject input = getInput(getInfoObject());
            if (input != null) {
                Process process = Xpdl2ModelUtil.getProcess(input);
                Set<Package> packages = CDSUtils.getReferencedBomPackages(process);
                loops:
                for (Package pkg : packages) {
                    EList<Type> types = pkg.getOwnedTypes();
                    for (Type type : types) {
                        if (type instanceof Class && BOMGlobalDataUtils.isCaseClass((Class) type)) {
                            EList<Relationship> relationships = type.getRelationships();
                            for (Relationship relationship : relationships) {
                                if (relationship instanceof Association) {
                                    Association association = (Association) relationship;
                                    for (Property property : association.getMemberEnds()) {
                                        if (stringLiteralValue.equals(property.getName())) {
                                            valid = true;
                                            break loops;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (!valid) {
                addErrorMessage(token, Messages.N2JScriptDotExpressionValidator_invalidAssociationLinkName);
            }
        } else {
            addErrorMessage(token, Messages.N2JScriptDotExpressionValidator_nonLiteralAssociationLinkName);
        }
    }

}
