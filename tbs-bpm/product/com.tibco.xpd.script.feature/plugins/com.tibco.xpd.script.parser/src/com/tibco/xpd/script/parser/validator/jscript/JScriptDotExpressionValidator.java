/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.parser.validator.jscript;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.DefaultJsReference;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.IUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.JsMethod;
import com.tibco.xpd.script.model.client.JsMethodParam;
import com.tibco.xpd.script.model.internal.client.ITypeResolution;
import com.tibco.xpd.script.model.jscript.JScriptGenericsService;
import com.tibco.xpd.script.model.jscript.JScriptUtils;
import com.tibco.xpd.script.parser.Messages;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
import com.tibco.xpd.script.parser.internal.expr.IExpr;
import com.tibco.xpd.script.parser.internal.validator.IValidateResult;
import com.tibco.xpd.script.parser.internal.validator.jscript.AbstractExpressionValidator;

import antlr.Token;
import antlr.collections.AST;

/**
 * @author mtorres
 * 
 *         ExpressionValidator class that handles the validation of the dot expression after a field, Class, Factory,
 *         etc... ie: FactoryClass.method(""); ie: field.method("");
 * 
 */
public class JScriptDotExpressionValidator extends AbstractExpressionValidator {

    @Override
    public IValidateResult evaluate(IExpr expression) {
        IScriptRelevantData returnDataType =
                createScriptRelevantData(JsConsts.UNDEFINED_DATA_TYPE, JsConsts.UNDEFINED_DATA_TYPE, false, null, null);
        if (expression != null) {
            Object expr = expression.getExpr();
            Object token = expression.getToken();
            if (expr instanceof AST && token instanceof Token) {
                AST astExpression = (AST) expr;
                switch (astExpression.getType()) {
                case JScriptTokenTypes.DOT:
                    IValidateResult processDotExpression =
                            processDotExpression(expression, astExpression, (Token) token);
                    if (processDotExpression != null) {
                        return processDotExpression;
                    }
                    break;

                default:
                    break;
                }
            }
        }
        IValidateResult result =
                updateResult(expression, returnDataType, JScriptUtils.getCurrentGenericContext(returnDataType));
        return result;
    }

    private IValidateResult processDotExpression(IExpr expression, AST astExpression, Token token) {
        AST firstChild = astExpression.getFirstChild();
        IValidateResult result = null;
        if (firstChild != null) {
            if (firstChild.getFirstChild() == null) {
                // It is the end of the tree so this is the root of the dot expr
                // Delegate the evaluation of the child
                IValidateResult delegateEvaluateExpression = delegateEvaluateExpression(firstChild, token);
                if (delegateEvaluateExpression != null) {
                    boolean isStaticReference = false;
                    if (firstChild.getType() == JScriptTokenTypes.IDENT) {

                        String identExpression = firstChild.getText();

                        /*
                         * Check if it's a statically contributed JSCLass from UML.
                         */
                        if (JScriptUtils.isASupportedClass(identExpression, getSupportedJsClasses(getInfoObject()))) {
                            isStaticReference = true;

                        } else {
                            /*
                             * Else it might be a scriptRelevant data that represents a static class.
                             */
                            IScriptRelevantData scriptRelevantData =
                                    getSupportedScriptRelevantDataMap(getInfoObject()).get(identExpression);

                            if (scriptRelevantData != null) {
                                isStaticReference = scriptRelevantData.isStatic();
                            }
                        }
                    }

                    return processExpressionAfterDot(expression,
                            delegateEvaluateExpression.getType(),
                            astExpression,
                            firstChild,
                            token,
                            isStaticReference);
                }
            } else {
                IValidateResult delegateEvaluateExpression = delegateEvaluateExpression(firstChild, token);
                return processExpressionAfterDot(expression,
                        delegateEvaluateExpression.getType(),
                        astExpression,
                        firstChild,
                        token,
                        false);
            }
        } else {
            // Not sure this is possible
            logUnexpectedExpressionValidatorProblem();
        }
        return result;
    }

    private IValidateResult processExpressionAfterDot(IExpr expression, IScriptRelevantData type, AST astExpression,
            AST firstChild, Token token, boolean isStaticReference) {
        if (type != null) {
            List<IScriptRelevantData> resolvedTypes = getResolutionTypes(type);
            // Check expression after the dot
            AST nextSibling = firstChild.getNextSibling();
            if (resolvedTypes != null && nextSibling != null) {
                if (nextSibling.getType() == JScriptTokenTypes.IDENT) {
                    if (areValidResolutionTypes(resolvedTypes)) {
                        Map<String, IScriptRelevantData> parameters = null;
                        if (astExpression.getNextSibling() != null
                                && astExpression.getNextSibling().getType() == JScriptTokenTypes.ELIST) {
                            parameters = resolveMethodParameters(astExpression.getNextSibling(), token);
                        }
                        String propertyOrMethodName = nextSibling.getText();
                        return processOwnedFeature(expression,
                                firstChild.getText(),
                                propertyOrMethodName,
                                resolvedTypes,
                                parameters,
                                token,
                                isStaticReference);
                    } else {
                        if (type.getType() != null && type.getType().equals(JsConsts.VOID_DATA_TYPE)) {
                            String message = Messages.ExpressionValidator_Operation_Void;
                            addErrorMessage(token, message);
                        } else {
                            String message = Messages.ExpressionValidator_Operation_Undefined;
                            addWarningMessage(token, message);
                        }
                    }
                } else {
                    logUnexpectedExpressionValidatorProblem();
                }
            } else if (type.getType() != null && type.getType().equals(JsConsts.VOID_DATA_TYPE)) {
                String message = Messages.ExpressionValidator_Operation_Void;
                addErrorMessage(token, message);
            } else {
                String message = Messages.ExpressionValidator_Operation_Undefined;
                addWarningMessage(token, message);
            }
        } else {
            logUnexpectedExpressionValidatorProblem();
        }
        return null;
    }

    private IValidateResult processOwnedFeature(IExpr expression, String dataType, String propertyOrMethodName,
            List<IScriptRelevantData> umlTypes, Map<String, IScriptRelevantData> parameters, Token token,
            boolean isStaticReference) {
        if (parameters == null) {
            // It is a property
            return processProperty(expression,
                    dataType,
                    propertyOrMethodName,
                    umlTypes,
                    parameters,
                    token,
                    isStaticReference);
        } else {
            // It is a method
            return processMethod(expression,
                    dataType,
                    propertyOrMethodName,
                    umlTypes,
                    parameters,
                    token,
                    isStaticReference);
        }
    }

    private IValidateResult processProperty(IExpr expression, String dataType, String propertyName,
            List<IScriptRelevantData> umlTypes, Map<String, IScriptRelevantData> parameters, Token token,
            boolean isStaticReference) {
        IScriptRelevantData matchingPropertyType = null;
        for (IScriptRelevantData umlType : umlTypes) {
            if (umlType instanceof IUMLScriptRelevantData) {
                matchingPropertyType =
                        getMatchingPropertyType((IUMLScriptRelevantData) umlType, propertyName, isStaticReference);
                if (matchingPropertyType != null) {
                    break;
                }
            }
        }
        if (matchingPropertyType != null) {
            addResolutionTypes(matchingPropertyType,
                    matchingPropertyType.isArray(),
                    JScriptUtils.getCurrentGenericContext(matchingPropertyType));
            return updateResult(expression,
                    matchingPropertyType,
                    createGenericContext(matchingPropertyType,
                            isGenericContextArray(matchingPropertyType, matchingPropertyType)));
        } else {
            // No property found
            String message = Messages.ExpressionValidator_PropertyInvalid_For_Data_Type;
            List<String> additionalAttributes = new ArrayList<String>();
            additionalAttributes.add(propertyName);
            addErrorMessage(token, message, additionalAttributes);
            return null;
        }
    }

    private IValidateResult processMethod(IExpr expression, String dataType, String methodName,
            List<IScriptRelevantData> umlTypes, Map<String, IScriptRelevantData> parameters, Token token,
            boolean isStaticReference) {
        IScriptRelevantData umlTypeContext = null;
        if (umlTypes != null && !umlTypes.isEmpty()) {
            umlTypeContext = umlTypes.iterator().next();
        }
        IScriptRelevantData currentGenericContext = JScriptUtils.getCurrentGenericContext(umlTypeContext);

        List<JsMethod> methodsWithName = getMethodsWithName(umlTypes, methodName);
        if (methodsWithName == null || methodsWithName.isEmpty()) {
            // No method found
            String message = Messages.ExpressionValidator_MethodInvalid_For_Data_Type;
            List<String> additionalAttributes = new ArrayList<String>();
            additionalAttributes.add(methodName);
            addErrorMessage(token, message, additionalAttributes);
            return null;
        } else {
            int parametersNumber = parameters.size();
            List<JsMethod> methodsWithParamNumber = getMethodsWithParamNumber(methodsWithName, parametersNumber);
            if (methodsWithParamNumber == null || methodsWithParamNumber.isEmpty()) {
                // param number don't match
                String message = Messages.ExpressionValidator_MethodParamsNumberInvalid_For_Data_Type;
                List<String> additionalAttributes = new ArrayList<String>();
                additionalAttributes.add(methodName);
                addErrorMessage(token, message, additionalAttributes);
                return null;
            } else {
                // Check if param types are compatible
                List<JsMethod> methodsWithMatchingParamTypes =
                        getMethodsWithMatchingParamTypes(methodsWithParamNumber, parameters, currentGenericContext);
                if (methodsWithMatchingParamTypes == null || methodsWithMatchingParamTypes.isEmpty()) {
                    // param types don't match
                    String message = Messages.ExpressionValidator_MethodParamsTypeInvalid_For_Data_Type;
                    List<String> additionalAttributes = new ArrayList<String>();
                    additionalAttributes.add(methodName);
                    additionalAttributes.add(getParameterNames(parameters));
                    addErrorMessage(token, message, additionalAttributes);
                    return null;
                } else {
                    JsMethod matchMethod = methodsWithMatchingParamTypes.iterator().next();
                    if (matchMethod != null) {
                        if (!validateUMLArrayNullParams(methodName,
                                parameters,
                                token,
                                currentGenericContext,
                                matchMethod)) {
                            return null;
                        }
                        JsMethodParam returnType = matchMethod.getReturnType();
                        boolean isGeneric = false;
                        // FIXME The above return type comes back as T for generic array returns, we need to convert it.
                        IScriptRelevantData createScriptRelevantData = null;
                        if (returnType != null) {
                            String paramDataType = JScriptUtils.getJsMethodParamBaseDataType(returnType);
                            JScriptGenericsService gs = new JScriptGenericsService();
                            Class umlClass = getUmlClass(returnType);
                            if (gs.isGeneric(returnType)) {
                                isGeneric = true;
                                Map<String, Type> typeMap = gs.createTypeMap(currentGenericContext, returnType);
                                Type type = typeMap.get(paramDataType);
                                if (type != null) {
                                    paramDataType = type.getName();
                                    if (type instanceof Class) {
                                        umlClass = (Class) type;
                                    } else {
                                        umlClass = null;
                                    }
                                }
                            }
                            if (umlClass != null) {
                                createScriptRelevantData = createUMLScriptRelevantData(paramDataType,
                                        returnType.canRepeat(),
                                        umlClass,
                                        currentGenericContext,
                                        returnType);
                            } else if (returnType.getScriptRelevantData() != null) {
                                /*
                                 * XPD-3129 ScriptrelevantData is used to resolve local var of case ref types which are
                                 * otherwise resolved using UMLClass and for Case Ref types they do not have any
                                 * UMLClass representation
                                 */
                                createScriptRelevantData = returnType.getScriptRelevantData();
                            } else {
                                createScriptRelevantData = createScriptRelevantData(paramDataType,
                                        paramDataType,
                                        returnType.canRepeat(),
                                        currentGenericContext,
                                        returnType);
                            }
                        } else {
                            // The return method type is void
                            createScriptRelevantData = createScriptRelevantData(JsConsts.VOID_DATA_TYPE,
                                    JsConsts.VOID_DATA_TYPE,
                                    false,
                                    currentGenericContext,
                                    returnType);
                        }
                        if (createScriptRelevantData != null) {
                            if (!isSupportedMethod(umlTypes, methodName, token, parameters)) {
                                String message = Messages.ExpressionValidator_MethodNotSupported;
                                List<String> additionalAttributes = new ArrayList<String>();
                                additionalAttributes.add(methodName);
                                addErrorMessage(token, message, additionalAttributes);
                            }
                            validateStatic(isStaticReference,
                                    JScriptUtils.isStaticMethod(matchMethod),
                                    methodName,
                                    token);
                            IScriptRelevantData newGenericContext = null;
                            if (createScriptRelevantData != null && createScriptRelevantData.getType() != null
                                    && (JScriptUtils.isGenericType(createScriptRelevantData.getType())
                                            || JScriptUtils.isContextlessType(createScriptRelevantData) || isGeneric)) {
                                newGenericContext = currentGenericContext;
                            } else {
                                newGenericContext = createGenericContext(createScriptRelevantData,
                                        isGenericContextArray(createScriptRelevantData, createScriptRelevantData));
                            }
                            boolean isArrayContext = false;
                            if (getCurrentSpecialGenericContextForMethod(newGenericContext,
                                    matchMethod,
                                    parameters) != null) {
                                newGenericContext = getCurrentSpecialGenericContextForMethod(newGenericContext,
                                        matchMethod,
                                        parameters);
                                isArrayContext = newGenericContext.isArray();
                            }
                            performSpecificMethodValidation(methodName,
                                    currentGenericContext,
                                    newGenericContext,
                                    matchMethod,
                                    createScriptRelevantData,
                                    isArrayContext,
                                    parameters,
                                    token);
                            addResolutionTypes(createScriptRelevantData,
                                    createScriptRelevantData.isArray(),
                                    createGenericContext(newGenericContext, isArrayContext));
                            return updateResult(expression,
                                    createScriptRelevantData,
                                    createGenericContext(newGenericContext, isArrayContext));
                        } else {
                            logUnexpectedExpressionValidatorProblem();
                        }
                    }
                }
            }
        }
        return null;
    }

    protected void performSpecificMethodValidation(String propertyName, IScriptRelevantData currentGenericContext,
            IScriptRelevantData newGenericContext, JsMethod matchMethod, IScriptRelevantData returnType,
            boolean isArrayContext, Map<String, IScriptRelevantData> parameters, Token token) {
        if (propertyName != null && propertyName.equals("addAll") && JScriptUtils.isXsdAnyType(currentGenericContext) //$NON-NLS-1$
                && parameters != null && parameters.size() == 1) {
            IScriptRelevantData parameter = parameters.values().iterator().next();
            if (parameter != null && !JScriptUtils.isDynamicComplexType(parameter)) {
                String message = Messages.ExpressionValidator_MethodParamsTypeInvalid_For_Data_Type;
                List<String> additionalAttributes = new ArrayList<String>();
                additionalAttributes.add(propertyName);
                additionalAttributes.add(getParameterNames(parameters));
                addErrorMessage(token, message, additionalAttributes);
            }
        }
    }

    /**
     * Validates a UML Property that is an array and ensures no null arguments can be added or set
     * 
     * @param propertyName
     * @param parameters
     * @param token
     * @param currentGenericContext
     * @param mathMethod
     * @return
     */
    private boolean validateUMLArrayNullParams(String propertyName, Map<String, IScriptRelevantData> parameters,
            Token token, IScriptRelevantData currentGenericContext, JsMethod mathMethod) {
        if (mathMethod.getName().equals("add") || mathMethod.getName().equals("set")) { //$NON-NLS-1$ //$NON-NLS-2$
            for (IScriptRelevantData data : parameters.values()) {
                if (data.getName().equals("null")) { //$NON-NLS-1$
                    if (currentGenericContext instanceof ITypeResolution) {
                        Object extendedInfo = ((ITypeResolution) currentGenericContext).getExtendedInfo();
                        if (extendedInfo instanceof DefaultJsReference) {
                            Element element = ((DefaultJsReference) extendedInfo).getElement();
                            if (element instanceof Property) {
                                Property property = (Property) element;
                                if (property.getUpper() == -1 || property.getUpper() > 1) {
                                    String message = Messages.ExpressionValidator_MethodParamsTypeInvalid_For_Data_Type;
                                    List<String> additionalAttributes = new ArrayList<String>();
                                    additionalAttributes.add(propertyName);
                                    additionalAttributes.add(getParameterNames(parameters));
                                    addErrorMessage(token, message, additionalAttributes);
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    protected boolean isSupportedMethod(List<IScriptRelevantData> types, String methodName, Token token,
            Map<String, IScriptRelevantData> parameters) {
        return true;
    }

    protected void validateStatic(boolean isStaticReference, boolean isStaticMethod, String propertyName, Token token) {
        if (isStaticReference && !isStaticMethod) {
            String message = Messages.ExpressionValidator_StaticCallToNonStaticMethod;
            List<String> additionalAttributes = new ArrayList<String>();
            additionalAttributes.add(propertyName);
            addErrorMessage(token, message, additionalAttributes);
        } else if (!isStaticReference && isStaticMethod) {
            String message = Messages.ExpressionValidator_NonStaticCallToStaticMethod;
            List<String> additionalAttributes = new ArrayList<String>();
            additionalAttributes.add(propertyName);
            addWarningMessage(token, message, additionalAttributes);
        }
    }

    private boolean areValidResolutionTypes(List<IScriptRelevantData> resolutionTypes) {
        if (resolutionTypes != null && !resolutionTypes.isEmpty()) {
            for (IScriptRelevantData type : resolutionTypes) {
                if (type instanceof IUMLScriptRelevantData && type.getType() != null
                        && !type.getType().equals(JsConsts.UNDEFINED_DATA_TYPE)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected IScriptRelevantData getCurrentSpecialGenericContextForMethod(IScriptRelevantData currentGenericContext,
            JsMethod mathMethod, Map<String, IScriptRelevantData> parameters) {
        return null;
    }

    /**
     * @see com.tibco.xpd.script.parser.internal.validator.AbstractValidator#addWarningMessage(antlr.Token,
     *      java.lang.String, java.util.List)
     * 
     * @param token
     * @param warningMessage
     * @param additionalAttributes
     */
    @Override
    protected void addWarningMessage(Token token, String warningMessage, List<String> additionalAttributes) {
        /*
         * XPD-4375: overriding this method for Forms to have scope on this method
         */
        super.addWarningMessage(token, warningMessage, additionalAttributes);
    }

    /**
     * @see com.tibco.xpd.script.parser.internal.validator.AbstractValidator#addErrorMessage(antlr.Token,
     *      java.lang.String, java.util.List)
     * 
     * @param token
     * @param errorMessage
     * @param additionalAttributes
     */
    @Override
    protected void addErrorMessage(Token token, String errorMessage, List<String> additionalAttributes) {
        /*
         * XPD-4375: overriding this method for Forms to have scope on this method
         */
        super.addErrorMessage(token, errorMessage, additionalAttributes);
    }
}
