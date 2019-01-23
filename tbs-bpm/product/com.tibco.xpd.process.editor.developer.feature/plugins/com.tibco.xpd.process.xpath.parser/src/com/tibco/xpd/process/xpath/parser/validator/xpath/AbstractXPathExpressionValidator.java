/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.process.xpath.parser.validator.xpath;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.uml2.uml.Parameter;
import org.eclipse.wst.wsdl.Part;
import org.eclipse.xsd.XSDComplexTypeDefinition;
import org.eclipse.xsd.XSDComponent;
import org.eclipse.xsd.XSDTypeDefinition;
import org.eclipse.xsd.impl.XSDAttributeUseImpl;
import org.eclipse.xsd.impl.XSDElementDeclarationImpl;
import org.jaxen.JaxenException;
import org.jaxen.expr.AllNodeStep;
import org.jaxen.expr.BinaryExpr;
import org.jaxen.expr.CommentNodeStep;
import org.jaxen.expr.EqualityExpr;
import org.jaxen.expr.Expr;
import org.jaxen.expr.FilterExpr;
import org.jaxen.expr.FunctionCallExpr;
import org.jaxen.expr.LiteralExpr;
import org.jaxen.expr.LocationPath;
import org.jaxen.expr.LogicalExpr;
import org.jaxen.expr.NameStep;
import org.jaxen.expr.NumberExpr;
import org.jaxen.expr.PathExpr;
import org.jaxen.expr.Predicate;
import org.jaxen.expr.RelationalExpr;
import org.jaxen.expr.Step;
import org.jaxen.expr.TextNodeStep;
import org.jaxen.expr.UnaryExpr;
import org.jaxen.expr.VariableReferenceExpr;
import org.jaxen.saxpath.Axis;

import antlr.LLkParser;
import antlr.Token;

import com.tibco.xpd.implementer.script.WsdlUtil;
import com.tibco.xpd.process.xpath.model.AbstractXPathMappingTypeResolver;
import com.tibco.xpd.process.xpath.model.ProcessXPathConsts;
import com.tibco.xpd.process.xpath.model.XPathConsts;
import com.tibco.xpd.process.xpath.parser.antlr.XPathParser;
import com.tibco.xpd.process.xpath.parser.util.ProcessXPathUtil;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.IUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.JsAttribute;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.JsMethod;
import com.tibco.xpd.script.model.client.JsMethodParam;
import com.tibco.xpd.script.model.client.JsReference;
import com.tibco.xpd.script.model.internal.client.ContentAssistElement;
import com.tibco.xpd.script.model.internal.xpath.XPathUtil;
import com.tibco.xpd.script.parser.internal.expr.IExpr;
import com.tibco.xpd.script.parser.internal.expr.IInfoObject;
import com.tibco.xpd.script.parser.internal.util.ValidationConstants;
import com.tibco.xpd.script.parser.internal.validator.AbstractValidator;
import com.tibco.xpd.script.parser.internal.validator.IValidateResult;
import com.tibco.xpd.script.parser.validator.ErrorType;
import com.tibco.xpd.script.parser.validator.IExpressionValidator;
import com.tibco.xpd.script.parser.validator.ISymbolTable;

/**
 * 
 * 
 * <p>
 * <i>Created: 20 Feb 2007</i>
 * </p>
 * 
 * @author Miguel Torres
 * 
 */
public abstract class AbstractXPathExpressionValidator extends
        AbstractValidator implements IExpressionValidator {

    private static final String iso8601DateTimePattern =
            "yyyy-MM-dd'T'HH:mm:ssz"; //$NON-NLS-1$ 

    private static final String iso8601TimePattern = "HH:mm:ssz"; //$NON-NLS-1$ 

    private static final String iso8601DatePattern = "yyyy-MM-dd"; //$NON-NLS-1$ 

    private final DateFormat localisedDateTimeFormat = DateFormat
            .getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);

    private final DateFormat localisedTimeFormat = DateFormat
            .getTimeInstance(DateFormat.SHORT);

    private final DateFormat localisedDateFormat = DateFormat
            .getDateInstance(DateFormat.SHORT);

    XPathParser scriptParser = null;

    @Override
    public void setScriptParser(LLkParser scriptParser) {
        if (scriptParser instanceof XPathParser) {
            this.scriptParser = (XPathParser) scriptParser;
        }
    }

    public XPathParser getScriptParser() {
        return this.scriptParser;
    }

    protected ISymbolTable getSymbolTable() {
        if (this.scriptParser != null) {
            return this.scriptParser.getSymbolTable();
        }
        return null;
    }

    /**
     * This method checks if the variable is defined
     * 
     * @param name
     *            the name of the variable
     * @return true if the variable is defined
     */
    protected boolean isDefinedVariable(String name) {
        Map<String, IScriptRelevantData> supportedVariables =
                getSupportedScriptRelevantData();
        if (supportedVariables.containsKey(name)) {
            return true;
        }
        if (isWsdlSupported()) {
            Part wsdlPart = getWsdlPart();
            if (wsdlPart != null) {
                String partName = null;
                if (wsdlPart.getElementName() != null) {
                    partName = wsdlPart.getElementName().getLocalPart();
                    if (partName != null && partName.equals(name)) {
                        return true;
                    }
                } else {
                    partName = wsdlPart.getName();
                    if (partName != null && partName.equals(name)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * This method checks if the method is supported
     * 
     * @param name
     *            the name of the method
     * @return true if the method is supported
     */
    protected boolean isSupportedMethodName(String name) {
        if (name != null) {
            List<String> supportedMethodNameList = getSupportedMethodNameList();
            for (String methodName : supportedMethodNameList) {
                if (methodName != null && methodName.equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This method should return a map of supported script relevant data
     * 
     * @return true the map of supported script relevant data
     */
    abstract Map<String, IScriptRelevantData> getSupportedScriptRelevantData();

    /**
     * This method should return the supported script relevant data for a given
     * name
     * 
     * @param name
     *            the name of the script relevant data
     * @return IScriptRelevantData the supported script relevant data for a
     *         given name
     */
    abstract IScriptRelevantData getSupportedScriptRelevantData(String name);

    /**
     * This method should return a list of names of the supported methods
     * 
     * @return List<String> the list of supported methods names
     */
    abstract List<String> getSupportedMethodNameList();

    /**
     * This should return a list of supported methods
     * 
     * @return List<JsMethod> the list of supported methods
     */
    abstract List<JsMethod> getSupportedMethodList();

    /**
     * This method should return the supported method for a given name
     * 
     * @param name
     *            the name of the method
     * @return JsMethod the supported method
     */
    abstract JsMethod getSupportedMethod(String methodName);

    /**
     * This method validates the Absolute Path location xpath expression
     * 
     * @param LocationPath
     *            the location Path to validate
     * @param token
     */
    protected void validateAbsoluteLocationXPathExpression(
            LocationPath locationPathExpr, Token token, boolean isInPredicate) {
        if (locationPathExpr != null) {
            Expr contextExpr = null;
            validateLocationPathXPathExpression(locationPathExpr,
                    contextExpr,
                    token,
                    isInPredicate);
        }
    }

    /**
     * This method validates the Relative Path location xpath expression
     * 
     * @param LocationPath
     *            the location Path to validate
     * @param contextExpr
     *            the context expression
     * @param token
     */
    protected void validateRelativeLocationXPathExpression(
            LocationPath locationPathExpr, Expr contextExpr, Token token,
            boolean isInPredicate) {
        if (locationPathExpr != null) {
            validateLocationPathXPathExpression(locationPathExpr,
                    contextExpr,
                    token,
                    isInPredicate);
        }
    }

    /**
     * This method validates the step xpath expression
     * 
     * @param Step
     *            the step to validate
     * @param token
     */
    protected void validateLocationPathXPathExpression(
            LocationPath locationPathExpr, Expr contextExpr, Token token,
            boolean isInPredicate) {
        if (locationPathExpr != null) {
            LocationPath locationPathPart = null;
            try {
                locationPathPart =
                        XPathUtil.getXPathFactory()
                                .createRelativeLocationPath();
                // Add all the steps in the context
                if (contextExpr != null && contextExpr instanceof LocationPath) {
                    // Get context steps
                    List<Step> contextSteps =
                            ((LocationPath) contextExpr).getSteps();
                    if (contextSteps != null) {
                        for (Step contextStep : contextSteps) {
                            // Add the step to the location path without
                            // predicates
                            Step newStep =
                                    XPathUtil
                                            .createStepWithoutPredicates(contextStep);
                            locationPathPart.addStep(newStep);
                        }
                    }
                }
            } catch (JaxenException e) {
                // Do nothing
            }
            List<Step> steps = locationPathExpr.getSteps();
            if (steps != null) {
                Iterator<Step> stepIterator = steps.iterator();
                while (stepIterator.hasNext()) {
                    Step step = stepIterator.next();
                    if (step != null) {
                        // Add the step to the location path without predicates
                        Step newStep =
                                XPathUtil.createStepWithoutPredicates(step);
                        locationPathPart.addStep(newStep);
                        if (step instanceof NameStep) {
                            // Validate the Predicates
                            List<Predicate> predicates = step.getPredicates();
                            if (predicates != null) {
                                for (Predicate predicate : predicates) {
                                    if (predicate != null) {
                                        validatePredicateXPathExpression(predicate,
                                                locationPathPart,
                                                token);
                                    }
                                }
                            }
                            String stepName = XPathUtil.getStepName(step);
                            if (stepName != null) {
                                IScriptRelevantData nodeRelevantData = null;
                                nodeRelevantData =
                                        getSupportedScriptRelevantData(stepName);
                                if (nodeRelevantData == null) {
                                    // Validate the wsdl
                                    if (isWsdlSupported()
                                            && locationPathPart != null) {
                                        if (!validateNodeNameAgainstWsdl(getWsdlPart(),
                                                locationPathPart,
                                                newStep,
                                                token)) {
                                            break;
                                        }
                                    } else {
                                        // Validate script relevant data in
                                        // complex data
                                        // types
                                        if (!ProcessXPathUtil
                                                .isNodePresentInComplexDataList(getSupportedScriptRelevantData(),
                                                        stepName)) {
                                            Map<String, String> resolutionMap =
                                                    new HashMap<String, String>();
                                            ErrorType errorType =
                                                    new ErrorType(
                                                            ValidationConstants.ERRORTYPE_KEY_UNDEFINED_NODE,
                                                            resolutionMap);
                                            List<String> additionalAttributes =
                                                    new ArrayList<String>();
                                            additionalAttributes.add(stepName);
                                            String errorMessage =
                                                    Messages.XPathVariableReferenceValidator_node_undefined;
                                            addErrorMessage(token,
                                                    errorMessage,
                                                    errorType,
                                                    additionalAttributes);
                                        }
                                    }
                                }
                            }
                        } else {
                            // check if there is only one step and is valid
                            boolean unknownProperty = true;
                            if (!stepIterator.hasNext()
                                    && isSupportedLastStepFunction(step)) {
                                unknownProperty = false;
                            }
                            if (unknownProperty) {
                                String propertyName =
                                        XPathUtil.getStepName(step);
                                Step firstStep = steps.iterator().next();
                                String variableName =
                                        XPathUtil.getStepName(firstStep);
                                List<String> additionalAttributes =
                                        new ArrayList<String>();
                                additionalAttributes.add(propertyName);
                                additionalAttributes.add(variableName);
                                String errorMessage =
                                        Messages.XPathVariableReferenceValidator_variable_unknown_property;
                                addErrorMessage(token,
                                        errorMessage,
                                        additionalAttributes);
                            }
                        }
                    }
                }
            }
        }
    }

    protected void validateNodeNameAgainstComplexType(
            IUMLScriptRelevantData complexType, Token token, String variableName) {
        if (complexType != null) {
            JsClass jsClass = complexType.getJsClass();
            List<JsAttribute> attributeList = jsClass.getAttributeList();
            List<JsReference> referenceList = jsClass.getReferenceList();
            List<ContentAssistElement> elementList =
                    new ArrayList<ContentAssistElement>();
            if (attributeList != null) {
                elementList.addAll(attributeList);
            }
            if (referenceList != null) {
                elementList.addAll(referenceList);
            }
        }
    }

    protected boolean validateNodeNameAgainstWsdl(Part wsdlPart,
            LocationPath locationPathExpr, Step step, Token token) {
        boolean nodeOk = false;
        String nodeName = XPathUtil.getStepName(step);
        if (wsdlPart != null && locationPathExpr != null) {
            String xmlDocStr = ProcessXPathUtil.getXmlDocStr(wsdlPart);
            String expressionStr = locationPathExpr.getText();
            if (xmlDocStr != null && expressionStr != null) {
                List pathNodeResults =
                        XPathUtil
                                .getXPathExpressionEvaluationNodeResults(xmlDocStr,
                                        expressionStr,
                                        getPartNamespaces());
                if (pathNodeResults != null && !pathNodeResults.isEmpty()) {
                    nodeOk = true;
                }
            }
            if (!nodeOk) {
                int axis = step.getAxis();
                if (axis == Axis.ATTRIBUTE) {
                    Map<String, String> resolutionMap =
                            new HashMap<String, String>();
                    ErrorType errorType =
                            new ErrorType(
                                    ValidationConstants.ERRORTYPE_KEY_UNDEFINED_ATTRIBUTE,
                                    resolutionMap);
                    List<String> additionalAttributes = new ArrayList<String>();
                    additionalAttributes.add(nodeName);
                    String errorMessage =
                            Messages.XPathVariableReferenceValidator_attribute_undefined;
                    addErrorMessage(token,
                            errorMessage,
                            errorType,
                            additionalAttributes);
                } else {
                    Map<String, String> resolutionMap =
                            new HashMap<String, String>();
                    ErrorType errorType =
                            new ErrorType(
                                    ValidationConstants.ERRORTYPE_KEY_UNDEFINED_NODE,
                                    resolutionMap);
                    List<String> additionalAttributes = new ArrayList<String>();
                    additionalAttributes.add(nodeName);
                    String errorMessage =
                            Messages.XPathVariableReferenceValidator_node_undefined;
                    addErrorMessage(token,
                            errorMessage,
                            errorType,
                            additionalAttributes);
                }
            }
        }
        return nodeOk;
    }

    protected void validateNodeNameAgainstComplexType(
            LocationPath locationPath, IUMLScriptRelevantData complexType,
            Token token, String variableName) {
        if (locationPath != null && complexType != null) {
            List<Step> steps = locationPath.getSteps();
            if (steps != null) {
                JsClass jsClass = complexType.getJsClass();
                List<JsAttribute> attributeList = jsClass.getAttributeList();
                List<JsReference> referenceList = jsClass.getReferenceList();
                List<ContentAssistElement> elementList =
                        new ArrayList<ContentAssistElement>();
                if (attributeList != null) {
                    elementList.addAll(attributeList);
                }
                if (referenceList != null) {
                    elementList.addAll(referenceList);
                }
                if (steps != null && !steps.isEmpty()) {
                    Iterator<Step> stepIterator = steps.iterator();
                    validateVariableReferenceNextStep(stepIterator,
                            elementList,
                            token,
                            variableName);
                }
            }
        }
    }

    /**
     * This method validates the Predicate xpath expression
     * 
     * @param Expr
     *            the expression to validate
     * @param token
     */
    protected void validatePredicateXPathExpression(Predicate predicate,
            Expr contextExpr, Token token) {
        if (predicate != null) {
            if (ProcessXPathUtil.isPositionPredicate(predicate)) {
                NumberExpr numberExpr =
                        ProcessXPathUtil.getPositionPredicateValue(predicate);
                if (numberExpr != null) {
                    double positionValue = numberExpr.getNumber().doubleValue();
                    if (positionValue <= 0) {
                        List<String> additionalAttributes =
                                new ArrayList<String>();
                        additionalAttributes.add(predicate.getText());
                        String errorMessage =
                                Messages.XPathVariableReferenceValidator_wrong_indexInPredicate;
                        addErrorMessage(token,
                                errorMessage,
                                additionalAttributes);
                    }
                }
            } else {
                Expr expression = predicate.getExpr();
                if (expression != null) {
                    validateExpressionXPathExpression(expression,
                            contextExpr,
                            token,
                            true);
                }
            }
        }
    }

    /**
     * This method validates the Expression xpath expression
     * 
     * @param Expr
     *            the expression to validate
     * @param contextExpr
     *            context expression
     * @param token
     */
    protected void validateExpressionXPathExpression(Expr expression,
            Expr contextExpr, Token token, boolean isInPredicate) {
        if (expression instanceof LocationPath) {
            LocationPath locationPathExpression = (LocationPath) expression;
            if (locationPathExpression.isAbsolute()) {
                validateAbsoluteLocationXPathExpression(locationPathExpression,
                        token,
                        isInPredicate);
            } else {
                validateRelativeLocationXPathExpression(locationPathExpression,
                        contextExpr,
                        token,
                        isInPredicate);
            }
        } else if (expression instanceof BinaryExpr) {

            validateBinaryXPathExpression((BinaryExpr) expression,
                    contextExpr,
                    token,
                    isInPredicate);

        } else if (expression instanceof UnaryExpr) {

            validateUnaryXPathExpression((UnaryExpr) expression,
                    contextExpr,
                    token,
                    isInPredicate);

        } else if (expression instanceof PathExpr) {

            validatePathXPathExpression((PathExpr) expression,
                    contextExpr,
                    token,
                    isInPredicate);

        } else if (expression instanceof FilterExpr) {

            validateFilterXPathExpression((FilterExpr) expression,
                    contextExpr,
                    token,
                    isInPredicate);

        } else if (expression instanceof VariableReferenceExpr) {

            validateVariableReferenceXPathExpression((VariableReferenceExpr) expression,
                    token);

        } else if (expression instanceof LiteralExpr) {

            validateLiteralXPathExpression((LiteralExpr) expression, token);

        } else if (expression instanceof NumberExpr) {

            validateNumberXPathExpression((NumberExpr) expression, token);

        } else if (expression instanceof FunctionCallExpr) {

            validateFunctionCallXPathExpression((FunctionCallExpr) expression,
                    token);

        }
    }

    /**
     * This method validates the BinaryExpression xpath expression
     * 
     * @param BinaryExpr
     *            the binary expression to validate
     * @param token
     */
    protected void validateBinaryXPathExpression(BinaryExpr binaryExpr,
            Expr contextExpr, Token token, boolean isInPredicate) {
        if (binaryExpr != null) {
            Expr binaryLhs = binaryExpr.getLHS();
            validateExpressionXPathExpression(binaryLhs,
                    contextExpr,
                    token,
                    isInPredicate);
            Expr binaryRhs = binaryExpr.getRHS();
            validateExpressionXPathExpression(binaryRhs,
                    contextExpr,
                    token,
                    isInPredicate);
            if (binaryExpr instanceof LogicalExpr) {
                validateLogicalXPathExpression((LogicalExpr) binaryExpr,
                        binaryLhs,
                        binaryRhs,
                        token);
            }
        }
    }

    protected void validateLogicalXPathExpression(LogicalExpr logicalExpr,
            Expr binaryLhs, Expr binaryRhs, Token token) {
        boolean correctLogical = false;
        if ((binaryLhs instanceof EqualityExpr || binaryLhs instanceof RelationalExpr)
                && (binaryRhs instanceof EqualityExpr || binaryRhs instanceof RelationalExpr)) {
            correctLogical = true;
        }
        if (!correctLogical) {
            String errorMessage =
                    Messages.XPathVariableReferenceValidator_invalid_logicalExpression_sides;
            addErrorMessage(token, errorMessage);
        }
    }

    /**
     * This method validates the UnaryExpression xpath expression
     * 
     * @param UnaryExpr
     *            the unary expression to validate
     * @param token
     */
    protected void validateUnaryXPathExpression(UnaryExpr unaryExpr,
            Expr contextExpr, Token token, boolean isInPredicate) {
        if (unaryExpr != null) {
            Expr expr = unaryExpr.getExpr();
            validateExpressionXPathExpression(expr,
                    contextExpr,
                    token,
                    isInPredicate);
        }
    }

    /**
     * This method validates the PathExpression xpath expression
     * 
     * @param PathExpr
     *            the path expression to validate
     * @param token
     */
    protected void validatePathXPathExpression(PathExpr pathExpr,
            Expr contextExpr, Token token, boolean isInPredicate) {
        if (pathExpr != null) {
            if (ProcessXPathUtil.isVariableReferenceWithPath(pathExpr)) {
                validateVariableReferenceWithPathXPathExpression(pathExpr,
                        token,
                        isInPredicate);
            } else {
                FilterExpr filterExpr = (FilterExpr) pathExpr.getFilterExpr();
                LocationPath locationPath = pathExpr.getLocationPath();
                validateExpressionXPathExpression(filterExpr,
                        contextExpr,
                        token,
                        isInPredicate);
                if (locationPath != null) {
                    if (locationPath.isAbsolute()) {
                        validateAbsoluteLocationXPathExpression(locationPath,
                                token,
                                isInPredicate);
                    } else {
                        validateRelativeLocationXPathExpression(locationPath,
                                contextExpr,
                                token,
                                isInPredicate);
                    }
                }
            }
        }
    }

    /**
     * This method validates the FilterExpression xpath expression
     * 
     * @param FilterExpr
     *            the filter expression to validate
     * @param token
     */
    protected void validateFilterXPathExpression(FilterExpr filterExpr,
            Expr contextExpr, Token token, boolean isInPredicate) {
        if (filterExpr != null) {
            Expr expr = filterExpr.getExpr();
            validateExpressionXPathExpression(expr,
                    contextExpr,
                    token,
                    isInPredicate);
            // Validate the predicates
            List<Predicate> predicates = filterExpr.getPredicates();
            if (predicates != null) {
                for (Predicate predicate : predicates) {
                    if (predicate != null) {
                        validatePredicateXPathExpression(predicate, expr, token);
                    }
                }
            }
        }
    }

    /**
     * This method validates the VariableReferenceExpr xpath expression
     * 
     * @param variableReferenceExpr
     *            the variable reference expression to validate
     * @param token
     */
    protected void validateVariableReferenceXPathExpression(
            VariableReferenceExpr variableReferenceExpr, Token token) {
        if (variableReferenceExpr != null) {
            String variableName = variableReferenceExpr.getVariableName();
            if (!isDefinedVariable(variableName)) {
                Map<String, String> resolutionMap =
                        new HashMap<String, String>();
                resolutionMap
                        .put(ValidationConstants.ERRORTYPE_KEY_UNDEFINED_VARIABLE,
                                variableName);
                ErrorType errorType =
                        new ErrorType(
                                ValidationConstants.ERRORTYPE_KEY_UNDEFINED_VARIABLE,
                                resolutionMap);
                List<String> additionalAttributes = new ArrayList<String>();
                additionalAttributes.add(variableName);
                String errorMessage =
                        Messages.XPathVariableReferenceValidator_variable_undefined;
                addErrorMessage(token,
                        errorMessage,
                        errorType,
                        additionalAttributes);
            }
        }
    }

    /**
     * This method validates the LiteralExpr xpath expression
     * 
     * @param LiteralExpr
     *            the literalExpr expression to validate
     * @param token
     */
    protected void validateLiteralXPathExpression(LiteralExpr literalExpr,
            Token token) {
        if (literalExpr != null) {
            // Do nothing, literal expressions are allowed
        }
    }

    /**
     * This method validates the NumberExpr xpath expression
     * 
     * @param numberExpr
     *            the numberExpr expression to validate
     * @param token
     */
    protected void validateNumberXPathExpression(NumberExpr numberExpr,
            Token token) {
        if (numberExpr != null) {
            // Do nothing, number expressions are allowed
        }
    }

    /**
     * This method validates the FunctionCall xpath expression
     * 
     * @param FunctionCallExpr
     *            the functionCallExpr expression to validate
     * @param token
     */
    protected void validateFunctionCallXPathExpression(
            FunctionCallExpr functionCallExpr, Token token) {
        if (functionCallExpr != null) {
            String functionName = functionCallExpr.getFunctionName();
            if (functionName != null) {
                JsMethod jsMethod = getSupportedMethod(functionName);
                if (jsMethod != null) {
                    List<JsMethodParam> methodParameters =
                            jsMethod.getParameterType();
                    List<Expr> expressionParameters =
                            functionCallExpr.getParameters();
                    // Validate if the number of parameters match
                    validateFunctionCallParameterNumberXPathExpression(methodParameters,
                            expressionParameters,
                            token,
                            functionName);
                    // Check the format of the expressions
                    for (Expr expr : expressionParameters) {
                        if (expr != null) {
                            validateExpressionXPathExpression(expr,
                                    null,
                                    token,
                                    false);
                        }
                    }
                    // Check if the types of the parameters match
                    validateFunctionCallParameterTypeXPathExpression(methodParameters,
                            expressionParameters,
                            token,
                            functionName);
                } else {
                    List<String> additionalAttributes = new ArrayList<String>();
                    additionalAttributes.add(functionName);
                    String errorMessage =
                            Messages.XPathVariableReferenceValidator_method_unsupported;
                    addErrorMessage(token, errorMessage, additionalAttributes);
                }
            }
        }
    }

    private boolean hasUndefinedParameterNumber(
            List<JsMethodParam> methodParameters) {
        boolean undefinedParameterNumber = false;
        if (methodParameters != null && !methodParameters.isEmpty()) {
            // Get the last parameter
            JsMethodParam methodParam =
                    methodParameters.get(methodParameters.size() - 1);
            if (methodParam != null && methodParam.getUMLParameter() != null) {
                if (methodParam.getUMLParameter().getName() != null
                        && methodParam.getUMLParameter().getName().equals("*")) {//$NON-NLS-1$
                    return true;
                }
            }
        }
        return undefinedParameterNumber;
    }

    private boolean hasCorrectNumberOfParameters(
            List<Expr> expressionParameters,
            List<JsMethodParam> methodParameters) {
        boolean correctNumber = true;
        if (hasUndefinedParameterNumber(methodParameters)) {
            if (expressionParameters.size() < methodParameters.size() - 1) {
                correctNumber = false;
            }
        } else {
            if (expressionParameters.size() != methodParameters.size()) {
                correctNumber = false;
            }
        }
        return correctNumber;
    }

    /**
     * This method validates if the number of parameters passed to the function
     * call xpath expression is valid
     * 
     * @param methodParameters
     *            the parameters of the method in the class
     * @param expressionParameters
     *            the parameters of the method in the expression
     * @param token
     * 
     */
    protected void validateFunctionCallParameterNumberXPathExpression(
            List<JsMethodParam> methodParameters,
            List<Expr> expressionParameters, Token token, String functionName) {
        boolean correctNumber = true;
        if (methodParameters == null) {
            methodParameters = new ArrayList<JsMethodParam>();
        }
        if (expressionParameters == null) {
            expressionParameters = new ArrayList<Expr>();
        }

        correctNumber =
                hasCorrectNumberOfParameters(expressionParameters,
                        methodParameters);
        if (!correctNumber) {
            List<String> additionalAttributes = new ArrayList<String>();
            additionalAttributes.add(functionName);
            String errorMessage =
                    Messages.XPathVariableReferenceValidator_method_wrong_parameter_number;
            addErrorMessage(token, errorMessage, additionalAttributes);
        }
    }

    /**
     * This method validates if the type of parameters passed to the function
     * call xpath expression match
     * 
     * @param methodParameters
     *            the parameters of the method in the class
     * @param expressionParameters
     *            the parameters of the method in the expression
     * @param token
     * 
     */
    protected void validateFunctionCallParameterTypeXPathExpression(
            List<JsMethodParam> methodParameters,
            List<Expr> expressionParameters, Token token, String functionName) {
        if (methodParameters == null) {
            methodParameters = new ArrayList<JsMethodParam>();
        }
        if (expressionParameters == null) {
            expressionParameters = new ArrayList<Expr>();
        }
        if (hasCorrectNumberOfParameters(expressionParameters, methodParameters)) {
            if (hasUndefinedParameterNumber(methodParameters)) {
                // Get the last parameter
                JsMethodParam undefinedNumberedMethodParam =
                        methodParameters.get(methodParameters.size() - 1);
                for (int i = 0; i < expressionParameters.size(); i++) {
                    Expr expr = expressionParameters.get(i);
                    JsMethodParam methodParameter = null;
                    if (i < methodParameters.size()) {
                        methodParameter = methodParameters.get(i);
                    } else {
                        methodParameter = undefinedNumberedMethodParam;
                    }
                    validateParameterType(expr,
                            methodParameter,
                            functionName,
                            i + 1,
                            token);
                }
            } else {
                for (int i = 0; i < expressionParameters.size(); i++) {
                    Expr expr = expressionParameters.get(i);
                    JsMethodParam methodParameter = methodParameters.get(i);
                    validateParameterType(expr,
                            methodParameter,
                            functionName,
                            i + 1,
                            token);
                }
            }
        }
    }

    protected void validateParameterType(Expr expr,
            JsMethodParam methodParameter, String functionName,
            int paramNumber, Token token) {
        if (methodParameter != null && methodParameter.getType() != null
                && expr != null) {
            String methodType = methodParameter.getType();
            IScriptRelevantData expressionType =
                    ProcessXPathUtil.getXPathExpressionType(expr,
                            getSupportedMethodList(),
                            getSupportedScriptRelevantData(),
                            getWsdlPart(),
                            getPartNamespaces());

            String parameterType = expressionType.getType();

            if (XPathConsts.XPATH_TYPE_UNDEFINED.equals(parameterType)) {
                List<String> additionalAttributes = new ArrayList<String>();
                additionalAttributes.add(String.valueOf(paramNumber));
                additionalAttributes.add(functionName);
                String errorMessage =
                        Messages.XPathVariableReferenceValidator_method_unresolved_parameter_type;
                addWarningMessage(token, errorMessage, additionalAttributes);
            } else {

                if (!isCorrectMultiplicity(methodParameter, expressionType)) {
                    List<String> additionalAttributes = new ArrayList<String>();
                    additionalAttributes.add(String.valueOf(paramNumber));
                    additionalAttributes.add(functionName);
                    String errorMessage =
                            Messages.XPathVariableReferenceValidator_method_wrong_parameter_multiplicity;
                    addErrorMessage(token, errorMessage, additionalAttributes);
                }

                if (!ProcessXPathConsts.isValidParameterType(methodType,
                        parameterType)
                        || !areSpecialFunctionRulesValid(functionName,
                                methodType,
                                parameterType)) {
                    List<String> additionalAttributes = new ArrayList<String>();
                    additionalAttributes.add(String.valueOf(paramNumber));
                    additionalAttributes.add(functionName);
                    String errorMessage =
                            Messages.XPathVariableReferenceValidator_method_wrong_parameter_type;
                    addErrorMessage(token, errorMessage, additionalAttributes);
                }
            }
        }
    }

    protected boolean areSpecialFunctionRulesValid(String functionName,
            String methodType, String parameterType) {
        boolean isValid = true;
        if (functionName != null) {
            if (functionName.equals("boolean")) {//$NON-NLS-1$
                if (parameterType != null
                        && parameterType
                                .toUpperCase()
                                .equals(XPathConsts.XPATH_TYPE_STRING.toUpperCase())) {
                    isValid = false;
                }
            } else if (functionName.equals("sum")) {//$NON-NLS-1$
                if (parameterType != null
                        && (parameterType
                                .toUpperCase()
                                .equals(XPathConsts.XPATH_TYPE_BOOLEAN.toUpperCase()) || parameterType
                                .toUpperCase()
                                .equals(XPathConsts.HEXBINARY.toUpperCase()))) {
                    isValid = false;
                }
            }
        }
        return isValid;
    }

    public boolean isCorrectMultiplicity(JsMethodParam methodParameter,
            IScriptRelevantData expressionType) {
        boolean correctMultiplicity = false;
        if (methodParameter != null && expressionType != null) {
            String expectedParameterType = methodParameter.getType();
            boolean isExpressionMultiple = expressionType.isArray();
            boolean isParameterMultiple = false;
            Parameter parameter = methodParameter.getUMLParameter();
            if (parameter != null) {
                isParameterMultiple = parameter.isMultivalued();
            }
            if (isExpressionMultiple == isParameterMultiple
                    || (expectedParameterType != null && expectedParameterType
                            .equals(XPathConsts.XPATH_TYPE_NODESET))) {
                correctMultiplicity = true;
            }
        }
        return correctMultiplicity;
    }

    public Expr getXPathExpression() {
        Expr expression = null;
        XPathParser parser = getScriptParser();
        if (parser != null) {
            expression = parser.getFinalExpression();
        }
        return expression;
    }

    public Part getWsdlPart() {
        Part wsdlPart = null;
        XPathParser parser = getScriptParser();
        if (parser != null) {
            wsdlPart = parser.getWsdlPart();
        }
        return wsdlPart;
    }

    public List<XSDComponent> getWsdlComponents() {
        List<XSDComponent> components = new ArrayList<XSDComponent>();
        if (getWsdlPart() != null) {
            components = ProcessXPathUtil.getWsdlComponents(getWsdlPart());
        }
        if (components == null) {
            components = new ArrayList<XSDComponent>();
        }
        return components;
    }

    public IScriptRelevantData getMappingType() {
        IScriptRelevantData mappingType = null;
        XPathParser parser = getScriptParser();
        if (parser != null) {
            mappingType = parser.getMappingType();
        }
        return mappingType;
    }

    public boolean isWsdlSupported() {
        boolean isSupported = false;
        XPathParser parser = getScriptParser();
        if (parser != null) {
            isSupported = parser.isWsdlSupported();
        }
        return isSupported;
    }

    /**
     * This method validates the PathExpression when it is a variable reference
     * xpath expression
     * 
     * Examples: $variableName/attribute
     * 
     * @param PathExpr
     *            the path expression to validate
     * @param token
     */
    protected void validateVariableReferenceWithPathXPathExpression(
            PathExpr pathExpr, Token token, boolean isInPredicate) {
        if (pathExpr != null) {
            LocationPath locationPath = pathExpr.getLocationPath();
            VariableReferenceExpr variableReference = null;
            Expr expr = pathExpr.getFilterExpr();
            if (expr instanceof VariableReferenceExpr) {
                variableReference = (VariableReferenceExpr) expr;
            } else if (expr instanceof FilterExpr) {
                FilterExpr filterExpr = (FilterExpr) expr;
                Expr filterExprExpr = filterExpr.getExpr();
                if (filterExprExpr instanceof VariableReferenceExpr) {
                    variableReference = (VariableReferenceExpr) filterExprExpr;
                } else if (filterExprExpr instanceof FilterExpr) {
                    Expr filterExprExprExpr =
                            ((FilterExpr) filterExprExpr).getExpr();
                    if (filterExprExprExpr instanceof VariableReferenceExpr) {
                        variableReference =
                                (VariableReferenceExpr) filterExprExprExpr;
                    }
                }
            }
            if (variableReference != null) {
                String variableName = variableReference.getVariableName();
                // Check if the variable exists
                IScriptRelevantData variableRelevantData = null;
                if (variableName != null) {
                    variableRelevantData =
                            getSupportedScriptRelevantData(variableName);
                }
                if (variableRelevantData == null) {
                    // Validate the wsdl
                    if (isWsdlSupported()) {
                        validateVariableReferencePathAgainstWsdl(locationPath,
                                null,
                                getWsdlPart(),
                                variableName,
                                token,
                                isInPredicate);
                    } else {
                        Map<String, String> resolutionMap =
                                new HashMap<String, String>();
                        resolutionMap
                                .put(ValidationConstants.ERRORTYPE_KEY_UNDEFINED_VARIABLE,
                                        variableName);
                        ErrorType errorType =
                                new ErrorType(
                                        ValidationConstants.ERRORTYPE_KEY_UNDEFINED_VARIABLE,
                                        resolutionMap);
                        List<String> additionalAttributes =
                                new ArrayList<String>();
                        additionalAttributes.add(variableName);
                        String errorMessage =
                                Messages.XPathVariableReferenceValidator_variable_undefined;
                        addErrorMessage(token,
                                errorMessage,
                                errorType,
                                additionalAttributes);
                    }
                } else if (variableRelevantData instanceof IUMLScriptRelevantData) {
                    validateVariableReferencePathAgainstComplexType(locationPath,
                            null,
                            (IUMLScriptRelevantData) variableRelevantData,
                            token,
                            variableName);
                } else if (variableRelevantData instanceof IScriptRelevantData) {
                    if (locationPath != null) {
                        List<Step> steps = locationPath.getSteps();
                        if (steps != null && !steps.isEmpty()) {
                            boolean unknownProperty = true;
                            // check if there is only one step and is valid
                            if (steps.size() == 1) {
                                unknownProperty =
                                        isSupportedLastStepFunction(steps
                                                .get(0));
                            }
                            if (unknownProperty) {
                                Step firstStep = steps.iterator().next();
                                String propertyName =
                                        XPathUtil.getStepName(firstStep);
                                List<String> additionalAttributes =
                                        new ArrayList<String>();
                                additionalAttributes.add(propertyName);
                                additionalAttributes.add(variableName);
                                String errorMessage =
                                        Messages.XPathVariableReferenceValidator_variable_unknown_property;
                                addErrorMessage(token,
                                        errorMessage,
                                        additionalAttributes);
                            }
                        }
                    }
                }
            }
        }
    }

    protected boolean isSupportedLastStepFunction(Step lastStep) {
        if (lastStep instanceof TextNodeStep
                || lastStep instanceof CommentNodeStep
                || lastStep instanceof AllNodeStep) {
            return true;
        }
        return false;
    }

    protected void validateVariableReferencePathAgainstWsdl(
            LocationPath locationPath, Expr contextExpr, Part wsdlPart,
            String variableName, Token token, boolean isInPredicate) {
        if (locationPath != null && wsdlPart != null) {
            LocationPath newLocationPath = null;
            try {
                newLocationPath =
                        XPathUtil.getXPathFactory()
                                .createRelativeLocationPath();
                Step variableNameStep =
                        XPathUtil.getXPathFactory().createNameStep(Axis.CHILD,
                                null,
                                variableName);
                newLocationPath.addStep(variableNameStep);
                List<Step> stepList = locationPath.getSteps();
                if (stepList != null) {
                    for (Step step : stepList) {
                        newLocationPath.addStep(step);
                    }
                }
                validateLocationPathXPathExpression(newLocationPath,
                        null,
                        token,
                        isInPredicate);
            } catch (JaxenException e) {
                // Do nothing
            }
        }
    }

    protected void validateWsdlVariableReferenceNextStep(
            Iterator<Step> stepIterator, List<XSDComponent> components,
            Token token, String variableName) {
        XSDComponent element = null;
        if (stepIterator.hasNext()) {
            Step currentStep = stepIterator.next();
            if (currentStep != null) {
                if (components != null) {
                    if (ProcessXPathUtil.isStepAttribute(currentStep)) {
                        element =
                                ProcessXPathUtil
                                        .getWsdlFirstAttribute(components,
                                                XPathUtil
                                                        .getStepName(currentStep));
                    } else {
                        element =
                                ProcessXPathUtil
                                        .getWsdlFirstElement(components,
                                                XPathUtil
                                                        .getStepName(currentStep));
                    }
                }
                if (currentStep.getAxis() == Axis.ATTRIBUTE) {
                    if (element instanceof XSDAttributeUseImpl) {
                        XSDAttributeUseImpl attributeUseImpl =
                                (XSDAttributeUseImpl) element;
                        List elementComponents =
                                attributeUseImpl.getXSDContents();
                        validateWsdlVariableReferenceNextStep(stepIterator,
                                elementComponents,
                                token,
                                variableName);
                    } else {
                        String propertyName =
                                XPathUtil.getStepName(currentStep);
                        List<String> additionalAttributes =
                                new ArrayList<String>();
                        additionalAttributes.add(propertyName);
                        additionalAttributes.add(variableName);
                        String errorMessage =
                                Messages.XPathVariableReferenceValidator_variable_unknown_attribute;
                        addErrorMessage(token,
                                errorMessage,
                                additionalAttributes);
                    }
                } else {
                    if (element instanceof XSDElementDeclarationImpl) {
                        XSDElementDeclarationImpl elementDeclarationImpl =
                                (XSDElementDeclarationImpl) element;
                        XSDTypeDefinition typeDefinition =
                                elementDeclarationImpl.getType();
                        List elementComponents = new ArrayList();
                        if (typeDefinition instanceof XSDComplexTypeDefinition) {
                            elementComponents =
                                    WsdlUtil.getTypeChildren(typeDefinition);
                        } else {
                            elementComponents =
                                    elementDeclarationImpl.getXSDContents();
                        }
                        validateWsdlVariableReferenceNextStep(stepIterator,
                                elementComponents,
                                token,
                                variableName);
                    } else {
                        String propertyName =
                                XPathUtil.getStepName(currentStep);
                        List<String> additionalAttributes =
                                new ArrayList<String>();
                        additionalAttributes.add(propertyName);
                        additionalAttributes.add(variableName);
                        String errorMessage =
                                Messages.XPathVariableReferenceValidator_variable_unknown_property;
                        addErrorMessage(token,
                                errorMessage,
                                additionalAttributes);
                    }
                }
            }
        }
    }

    protected void validateVariableReferencePathAgainstComplexType(
            LocationPath locationPath, Expr contextExpr,
            IUMLScriptRelevantData complexType, Token token, String variableName) {
        if (locationPath != null && complexType != null) {
            List<Step> steps = locationPath.getSteps();
            if (steps != null) {
                JsClass jsClass = complexType.getJsClass();
                List<JsAttribute> attributeList = jsClass.getAttributeList();
                List<JsReference> referenceList = jsClass.getReferenceList();
                List<ContentAssistElement> elementList =
                        new ArrayList<ContentAssistElement>();
                if (attributeList != null) {
                    elementList.addAll(attributeList);
                }
                if (referenceList != null) {
                    elementList.addAll(referenceList);
                }
                if (steps != null && !steps.isEmpty()) {
                    Iterator<Step> stepIterator = steps.iterator();
                    validateVariableReferenceNextStep(stepIterator,
                            elementList,
                            token,
                            variableName);
                }
            }
        }
    }

    protected void validateVariableReferenceNextStep(
            Iterator<Step> stepIterator,
            List<ContentAssistElement> elementList, Token token,
            String variableName) {
        ContentAssistElement element = null;
        if (stepIterator.hasNext()) {
            Step currentStep = stepIterator.next();
            if (currentStep != null) {
                if (!stepIterator.hasNext()
                        && isSupportedLastStepFunction(currentStep)) {
                    return;
                } else {
                    element =
                            ProcessXPathUtil.getElement(elementList,
                                    XPathUtil.getStepName(currentStep));
                    if (element instanceof JsAttribute) {
                        if (stepIterator.hasNext()) {
                            Step nextStep = stepIterator.next();
                            if (!stepIterator.hasNext()
                                    && isSupportedLastStepFunction(nextStep)) {
                                return;
                            } else {
                                String propertyName =
                                        XPathUtil.getStepName(nextStep);
                                List<String> additionalAttributes =
                                        new ArrayList<String>();
                                additionalAttributes.add(propertyName);
                                additionalAttributes.add(variableName);
                                String errorMessage =
                                        Messages.XPathVariableReferenceValidator_variable_unknown_property;
                                addErrorMessage(token,
                                        errorMessage,
                                        additionalAttributes);
                            }
                        }
                    } else if (element instanceof JsReference) {
                        JsReference jsReference = (JsReference) element;
                        JsClass jsClass = jsReference.getReferencedJsClass();
                        List<JsAttribute> attributeList =
                                jsClass.getAttributeList();
                        List<JsReference> referenceList =
                                jsClass.getReferenceList();
                        elementList = new ArrayList<ContentAssistElement>();
                        if (attributeList != null) {
                            elementList.addAll(attributeList);
                        }
                        if (referenceList != null) {
                            elementList.addAll(referenceList);
                        }
                        validateVariableReferenceNextStep(stepIterator,
                                elementList,
                                token,
                                variableName);
                    } else {
                        String propertyName =
                                XPathUtil.getStepName(currentStep);
                        List<String> additionalAttributes =
                                new ArrayList<String>();
                        additionalAttributes.add(propertyName);
                        additionalAttributes.add(variableName);
                        String errorMessage =
                                Messages.XPathVariableReferenceValidator_variable_unknown_property;
                        addErrorMessage(token,
                                errorMessage,
                                additionalAttributes);
                    }
                }
            }
        }
    }

    protected void validateMappedTypesMatch(Expr expression, Token token) {
        if (ProcessXPathUtil.isLiteralExpression(expression)) {
            LiteralExpr literalExpr =
                    ProcessXPathUtil.getLiteralExpression(expression);
            validateLiteralMappedTypesMatch(literalExpr, token);
        }
        if (ProcessXPathUtil.isNumberExpression(expression)) {
            NumberExpr numberExpr =
                    ProcessXPathUtil.getNumberExpression(expression);
            validateNumberMappedTypesMatch(numberExpr, token);
        } else {
            IScriptRelevantData mappingType = getMappingType();
            if (mappingType != null) {
                String strMappingType = mappingType.getType();
                IScriptRelevantData expressionType =
                        getXPathExpressionType(expression);
                String strExpressionType = expressionType.getType();
                if (XPathConsts.XPATH_TYPE_UNDEFINED.equals(strExpressionType)
                        || XPathConsts.XPATH_TYPE_UNDEFINED
                                .equals(strMappingType)) {
                    String errorMessage =
                            Messages.XPathVariableReferenceValidator_script_unresolved_expression_type;
                    addWarningMessage(token, errorMessage);
                } else {
                    if (mappingType.isArray() != expressionType.isArray()) {
                        String errorMessage =
                                Messages.XPathVariableReferenceValidator_mapping_unmatch_multiplicity;
                        addErrorMessage(token, errorMessage);
                    }
                    if (!isValidMappingType(strExpressionType, strMappingType)) {
                        String errorMessage =
                                Messages.XPathVariableReferenceValidator_mapping_unmatch_types;
                        addErrorMessage(token, errorMessage);
                    }
                }
            }
        }
    }

    protected void validateLiteralMappedTypesMatch(LiteralExpr literalExpr,
            Token token) {
        IScriptRelevantData mappingType = getMappingType();
        if (mappingType != null) {
            String strMappingType = mappingType.getType();
            if (mappingType.isArray()) {
                String errorMessage =
                        Messages.XPathVariableReferenceValidator_mapping_unmatch_multiplicity;
                addErrorMessage(token, errorMessage);
            }
            if (!isValidLiteralMappingType(literalExpr, strMappingType)) {
                List<String> additionalAttributes = new ArrayList<String>();
                additionalAttributes.add(strMappingType);
                String errorMessage =
                        Messages.XPathVariableReferenceValidator_literal_mapping_unmatch_types;
                addErrorMessage(token, errorMessage, additionalAttributes);
                if (strMappingType.toUpperCase()
                        .equals(XPathConsts.DATE.toUpperCase())) {
                    String dateFormat =
                            Messages.XPathVariableReferenceValidator_literal_dateFormat;
                    addWarningMessage(token, dateFormat, additionalAttributes);
                } else if (strMappingType.toUpperCase()
                        .equals(XPathConsts.TIME.toUpperCase())) {
                    String timeFormat =
                            Messages.XPathVariableReferenceValidator_literal_timeFormat;
                    addWarningMessage(token, timeFormat, additionalAttributes);
                } else if (strMappingType.toUpperCase()
                        .equals(XPathConsts.DATETIME.toUpperCase())) {
                    String dateTimeFormat =
                            Messages.XPathVariableReferenceValidator_literal_dateTimeFormat;
                    addWarningMessage(token,
                            dateTimeFormat,
                            additionalAttributes);
                }
            }
        }
    }

    protected void validateNumberMappedTypesMatch(NumberExpr numberExpr,
            Token token) {
        IScriptRelevantData mappingType = getMappingType();
        if (mappingType != null) {
            String strMappingType = mappingType.getType();
            if (mappingType.isArray()) {
                String errorMessage =
                        Messages.XPathVariableReferenceValidator_mapping_unmatch_multiplicity;
                addErrorMessage(token, errorMessage);
            }
            if (!isValidNumberMappingType(numberExpr, strMappingType)) {
                List<String> additionalAttributes = new ArrayList<String>();
                additionalAttributes.add(strMappingType);
                String errorMessage =
                        Messages.XPathVariableReferenceValidator_number_mapping_unmatch_types;
                addErrorMessage(token, errorMessage, additionalAttributes);
            }
        }
    }

    protected IScriptRelevantData getXPathExpressionType(Expr expression) {
        return ProcessXPathUtil.getXPathExpressionType(expression,
                getSupportedMethodList(),
                getSupportedScriptRelevantData(),
                getWsdlPart(),
                getPartNamespaces());
    }

    protected boolean isValidMappingType(String sourceType, String targetType) {
        if (getMappingTypeResolver() != null) {
            if (isWsdlSupported()) {
                return getMappingTypeResolver()
                        .isValidWSToProcessMappingType(sourceType, targetType);
            } else {
                return getMappingTypeResolver()
                        .isValidProcessToWSMappingType(sourceType, targetType);
            }
        }
        return false;
    }

    private boolean isValidNumberMappingType(NumberExpr numberExpr,
            String targetType) {
        if (isWsdlSupported()) {
            return isValidNumberWSToProcessMappingType(numberExpr, targetType);
        } else {
            return isValidNumberProcessToWSMappingType(numberExpr, targetType);
        }
    }

    private boolean isValidLiteralMappingType(LiteralExpr literalExpr,
            String targetType) {
        if (isWsdlSupported()) {
            return isValidLiteralWSToProcessMappingType(literalExpr, targetType);
        } else {
            return isValidLiteralProcessToWSMappingType(literalExpr, targetType);
        }
    }

    private boolean isValidNumberWSToProcessMappingType(NumberExpr numberExpr,
            String targetType) {
        boolean isValid = false;
        if (targetType != null && numberExpr != null) {
            targetType = targetType.toUpperCase();
            if (targetType.equals(XPathConsts.XPATH_TYPE_STRING.toUpperCase())) {
                return true;
            } else if (targetType.equals(XPathConsts.PERFORMER.toUpperCase())) {
                return true;
            } else if (targetType.equals(XPathConsts.XPATH_TYPE_BOOLEAN
                    .toUpperCase()) && isValidIntNumberExpression(numberExpr)) {
                return true;
            } else if (targetType.equals(XPathConsts.FLOAT.toUpperCase())
                    && isValidFloatNumberExpression(numberExpr)) {
                return true;
            } else if (targetType.equals(XPathConsts.DOUBLE.toUpperCase())
                    && isValidDoubleNumberExpression(numberExpr)) {
                return true;
            } else if (targetType.equals(XPathConsts.DECIMAL.toUpperCase())
                    && isValidDoubleNumberExpression(numberExpr)) {
                return true;
            } else if ((targetType.equals(XPathConsts.INT.toUpperCase()) || targetType
                    .equals(XPathConsts.INTEGER.toUpperCase()))
                    && isValidIntNumberExpression(numberExpr)) {
                return true;
            }
        }
        return isValid;
    }

    private boolean isValidNumberProcessToWSMappingType(NumberExpr numberExpr,
            String targetType) {
        boolean isValid = false;
        if (targetType != null && numberExpr != null) {
            targetType = targetType.toUpperCase();
            if (targetType.equals(XPathConsts.XPATH_TYPE_STRING.toUpperCase())) {
                return true;
            } else if (targetType.equals(XPathConsts.INT.toUpperCase())
                    && isValidIntNumberExpression(numberExpr)) {
                return true;
            } else if (targetType.equals(XPathConsts.FLOAT.toUpperCase())
                    && isValidFloatNumberExpression(numberExpr)) {
                return true;
            } else if (targetType.equals(XPathConsts.DOUBLE.toUpperCase())
                    && isValidDoubleNumberExpression(numberExpr)) {
                return true;
            } else if (targetType.equals(XPathConsts.XPATH_TYPE_BOOLEAN
                    .toUpperCase())
                    && isValidBooleanNumberExpression(numberExpr)) {
                return true;
            }
        }
        return isValid;
    }

    protected boolean isValidIntNumberExpression(NumberExpr numberExpr) {
        boolean isValid = false;
        if (numberExpr != null) {
            String number = numberExpr.getText();
            try {
                Integer.parseInt(number);
                isValid = true;
            } catch (NumberFormatException e) {
                // Ignore
            }
        }
        return isValid;
    }

    protected boolean isValidFloatNumberExpression(NumberExpr numberExpr) {
        boolean isValid = false;
        if (numberExpr != null) {
            String number = numberExpr.getText();
            try {
                Float.parseFloat(number);
                isValid = true;
            } catch (NumberFormatException e) {
                // Ignore
            }
        }
        return isValid;
    }

    protected boolean isValidDoubleNumberExpression(NumberExpr numberExpr) {
        boolean isValid = false;
        if (numberExpr != null) {
            String number = numberExpr.getText();
            try {
                Double.parseDouble(number);
                isValid = true;
            } catch (NumberFormatException e) {
                // Ignore
            }
        }
        return isValid;
    }

    protected boolean isValidBooleanNumberExpression(NumberExpr numberExpr) {
        boolean isValid = false;
        if (numberExpr != null) {
            String number = numberExpr.getText();
            if (number != null && (number.equals("0") || number.equals("1"))) {//$NON-NLS-1$//$NON-NLS-2$
                isValid = true;
            }
        }
        return isValid;
    }

    private boolean isValidLiteralWSToProcessMappingType(
            LiteralExpr literalExpr, String targetType) {
        boolean isValid = false;
        if (targetType != null && literalExpr != null) {
            targetType = targetType.toUpperCase();
            if (targetType.equals(XPathConsts.XPATH_TYPE_STRING.toUpperCase())) {
                return true;
            } else if (targetType.equals(XPathConsts.PERFORMER.toUpperCase())) {
                return true;
            } else if (targetType.equals(XPathConsts.XPATH_TYPE_BOOLEAN
                    .toUpperCase())
                    && isValidBooleanLiteralExpression(literalExpr)) {
                return true;
            } else if (targetType.equals(XPathConsts.FLOAT.toUpperCase())
                    && isValidFloatLiteralExpression(literalExpr)) {
                return true;
            } else if (targetType.equals(XPathConsts.DOUBLE.toUpperCase())
                    && isValidDoubleLiteralExpression(literalExpr)) {
                return true;
            } else if (targetType.equals(XPathConsts.DECIMAL.toUpperCase())
                    && isValidDoubleLiteralExpression(literalExpr)) {
                return true;
            } else if ((targetType.equals(XPathConsts.INT.toUpperCase()) || targetType
                    .equals(XPathConsts.INTEGER.toUpperCase()))
                    && isValidIntLiteralExpression(literalExpr)) {
                return true;
            } else if ((targetType.equals(XPathConsts.LONG.toUpperCase()) || targetType
                    .equals(XPathConsts.LONG.toUpperCase()))
                    && isValidLongLiteralExpression(literalExpr)) {
                return true;
            }
        }
        return isValid;
    }

    private boolean isValidLiteralProcessToWSMappingType(
            LiteralExpr literalExpr, String targetType) {
        boolean isValid = false;
        if (targetType != null && literalExpr != null) {
            targetType = targetType.toUpperCase();
            if (targetType.equals(XPathConsts.XPATH_TYPE_STRING.toUpperCase())) {
                return true;
            } else if (targetType.equals(XPathConsts.INT.toUpperCase())
                    && isValidIntLiteralExpression(literalExpr)) {
                return true;
            } else if (targetType.equals(XPathConsts.LONG.toUpperCase())
                    && isValidLongLiteralExpression(literalExpr)) {
                return true;
            } else if (targetType.equals(XPathConsts.FLOAT.toUpperCase())
                    && isValidFloatLiteralExpression(literalExpr)) {
                return true;
            } else if (targetType.equals(XPathConsts.DECIMAL.toUpperCase())
                    && isValidFloatLiteralExpression(literalExpr)) {
                return true;
            } else if (targetType.equals(XPathConsts.DOUBLE.toUpperCase())
                    && isValidDoubleLiteralExpression(literalExpr)) {
                return true;
            } else if (targetType.equals(XPathConsts.DATE.toUpperCase())
                    && isValidDateLiteralExpression(literalExpr)) {
                return true;
            } else if (targetType.equals(XPathConsts.TIME.toUpperCase())
                    && isValidTimeLiteralExpression(literalExpr)) {
                return true;
            } else if (targetType.equals(XPathConsts.DATETIME.toUpperCase())
                    && isValidDateTimeLiteralExpression(literalExpr)) {
                return true;
            } else if (targetType.equals(XPathConsts.XPATH_TYPE_BOOLEAN
                    .toUpperCase())
                    && isValidBooleanLiteralExpression(literalExpr)) {
                return true;
            } else if (targetType.equals(XPathConsts.HEXBINARY.toUpperCase())
                    && isValidHexBinaryLiteralExpression(literalExpr)) {
                return true;
            }
        }
        return isValid;
    }

    protected boolean isValidIntLiteralExpression(LiteralExpr literalExpr) {
        boolean isValid = false;
        if (literalExpr != null) {
            String literal = literalExpr.getLiteral();
            try {
                Integer.parseInt(literal);
                isValid = true;
            } catch (NumberFormatException e) {
                // Ignore
            }
        }
        return isValid;
    }

    protected boolean isValidLongLiteralExpression(LiteralExpr literalExpr) {
        boolean isValid = false;
        if (literalExpr != null) {
            String literal = literalExpr.getLiteral();
            try {
                Long.parseLong(literal);
                isValid = true;
            } catch (NumberFormatException e) {
                // Ignore
            }
        }
        return isValid;
    }

    protected boolean isValidFloatLiteralExpression(LiteralExpr literalExpr) {
        boolean isValid = false;
        if (literalExpr != null) {
            String literal = literalExpr.getLiteral();
            try {
                Float.parseFloat(literal);
                isValid = true;
            } catch (NumberFormatException e) {
                // Ignore
            }
        }
        return isValid;
    }

    protected boolean isValidDoubleLiteralExpression(LiteralExpr literalExpr) {
        boolean isValid = false;
        if (literalExpr != null) {
            String literal = literalExpr.getLiteral();
            try {
                Double.parseDouble(literal);
                isValid = true;
            } catch (NumberFormatException e) {
                // Ignore
            }
        }
        return isValid;
    }

    protected boolean isValidDateLiteralExpression(LiteralExpr literalExpr) {
        boolean isValid = false;
        if (literalExpr != null) {
            try {
                String literal = literalExpr.getLiteral();
                int literalLength = -1;
                if (literal != null) {
                    literal = literal.trim();
                    literalLength = literal.length();
                }
                ParsePosition parsePosition = new ParsePosition(0);
                Date localisedDate =
                        localisedDateFormat.parse(literal, parsePosition);
                if (parsePosition.getIndex() != literalLength) {
                    return false;
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat();
                dateFormat.applyPattern(iso8601DatePattern);
                String value = dateFormat.format(localisedDate);
                isValid = true;
            } catch (Exception e) {
                // Ignore
            }
        }
        return isValid;
    }

    protected boolean isValidTimeLiteralExpression(LiteralExpr literalExpr) {
        boolean isValid = false;
        if (literalExpr != null) {
            try {
                String literal = literalExpr.getLiteral();
                int literalLength = -1;
                if (literal != null) {
                    literal = literal.trim();
                    literalLength = literal.length();
                }
                ParsePosition parsePosition = new ParsePosition(0);
                Date localisedDate =
                        localisedTimeFormat.parse(literal, parsePosition);
                if (parsePosition.getIndex() != literalLength) {
                    return false;
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat();
                dateFormat.applyPattern(iso8601TimePattern);
                String value = dateFormat.format(localisedDate);
                isValid = true;
            } catch (Exception e) {
                // Ignore
            }
        }
        return isValid;
    }

    protected boolean isValidDateTimeLiteralExpression(LiteralExpr literalExpr) {
        boolean isValid = false;
        if (literalExpr != null) {
            try {
                String literal = literalExpr.getLiteral();
                int literalLength = -1;
                if (literal != null) {
                    literal = literal.trim();
                    literalLength = literal.length();
                }
                ParsePosition parsePosition = new ParsePosition(0);
                Date localisedDate =
                        localisedDateTimeFormat.parse(literal, parsePosition);
                if (parsePosition.getIndex() != literalLength) {
                    return false;
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat();
                dateFormat.applyPattern(iso8601DateTimePattern);
                String value = dateFormat.format(localisedDate);
                isValid = true;
            } catch (Exception e) {
                // Ignore
            }
        }
        return isValid;
    }

    protected boolean isValidBooleanLiteralExpression(LiteralExpr literalExpr) {
        boolean isValid = false;
        if (literalExpr != null) {
            String literal = literalExpr.getLiteral();
            if (literal != null && (literal.equals("0") || literal.equals("1")//$NON-NLS-1$//$NON-NLS-2$
                    || literal.equals("true") || literal//$NON-NLS-1$
                        .equals("false"))) {//$NON-NLS-1$
                isValid = true;
            }
        }
        return isValid;
    }

    protected boolean isValidHexBinaryLiteralExpression(LiteralExpr literalExpr) {
        boolean isValid = false;
        if (literalExpr != null) {
            String literal = literalExpr.getLiteral();
            if (literal != null && literal.length() > 1 && literal.length() < 7) {
                for (int i = 0; i < literal.length(); i++) {
                    String aChar = literal.substring(i, i + 1);
                    if (aChar != null) {
                        if (isValidHexLetter(aChar)) {
                            continue;
                        } else {
                            try {
                                Integer.parseInt(aChar);
                                continue;
                            } catch (NumberFormatException ex) {
                                return false;
                            }
                        }
                    } else {
                        return false;
                    }
                }
                isValid = true;
            }
        }
        return isValid;
    }

    private boolean isValidHexLetter(String aChar) {
        boolean isValidLetter = false;
        String[] validHexArray = new String[] { "A", "B", "C", "D", "E", "F" };//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$//$NON-NLS-5$//$NON-NLS-6$
        if (aChar != null) {
            for (int i = 0; i < validHexArray.length; i++) {
                String validHex = validHexArray[i];
                if (aChar.equals(validHex)) {
                    return true;
                }
            }
        }
        return isValidLetter;
    }

    protected abstract AbstractXPathMappingTypeResolver getMappingTypeResolver();

    protected Map<String, String> getPartNamespaces() {
        Map<String, String> namespaces = new HashMap<String, String>();
        if (getWsdlPart() != null) {
            namespaces = ProcessXPathUtil.getPartNamespaces(getWsdlPart());
        }
        return namespaces;
    }

    // TODO: remove this method when is implemented in all validators
    @Override
    public IValidateResult evaluate(IExpr expresion) {
        // TODO Auto-generated method stub
        return null;
    }

    // TODO: Implement this method
    @Override
    public IInfoObject getInfoObject() {
        // TODO Auto-generated method stub
        return null;
    }

    // TODO: Implement this method
    @Override
    public void setInfoObject(IInfoObject infoObject) {
        // TODO Auto-generated method stub

    }
}
