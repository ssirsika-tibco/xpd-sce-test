/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.parser.internal.validator.jscript;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.AbstractUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.BaseJsMethodParam;
import com.tibco.xpd.script.model.client.DefaultJsAttribute;
import com.tibco.xpd.script.model.client.DefaultJsClass;
import com.tibco.xpd.script.model.client.DefaultScriptRelevantData;
import com.tibco.xpd.script.model.client.DefaultUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.IScriptRelevantDataFactory;
import com.tibco.xpd.script.model.client.ITypeResolver;
import com.tibco.xpd.script.model.client.IUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.IUnionScriptRelevantData;
import com.tibco.xpd.script.model.client.JsAttribute;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.JsClassDefinitionReader;
import com.tibco.xpd.script.model.client.JsDataType;
import com.tibco.xpd.script.model.client.JsExpression;
import com.tibco.xpd.script.model.client.JsExpressionMethod;
import com.tibco.xpd.script.model.client.JsMethod;
import com.tibco.xpd.script.model.client.JsMethodParam;
import com.tibco.xpd.script.model.client.JsReference;
import com.tibco.xpd.script.model.client.ParameterCoercionCriteria;
import com.tibco.xpd.script.model.client.globaldata.CaseJsMethodParam;
import com.tibco.xpd.script.model.internal.client.DefaultJsEnumeration;
import com.tibco.xpd.script.model.internal.client.IDataTypeMapper;
import com.tibco.xpd.script.model.internal.client.IGlobalDataDefinitionReader;
import com.tibco.xpd.script.model.internal.client.IJsElementExt;
import com.tibco.xpd.script.model.internal.client.ITypeResolution;
import com.tibco.xpd.script.model.internal.client.IUMLElement;
import com.tibco.xpd.script.model.internal.client.JsEnumeration;
import com.tibco.xpd.script.model.internal.client.JsEnumerationLiteral;
import com.tibco.xpd.script.model.internal.jscript.IJScriptDataTypeMapper;
import com.tibco.xpd.script.model.jscript.JScriptGenericsService;
import com.tibco.xpd.script.model.jscript.JScriptUtils;
import com.tibco.xpd.script.parser.Messages;
import com.tibco.xpd.script.parser.antlr.JScriptEmitter;
import com.tibco.xpd.script.parser.antlr.JScriptParser;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
import com.tibco.xpd.script.parser.internal.expr.IExpr;
import com.tibco.xpd.script.parser.internal.expr.IInfoObject;
import com.tibco.xpd.script.parser.internal.util.ExpressionUtil;
import com.tibco.xpd.script.parser.internal.validator.AbstractValidator;
import com.tibco.xpd.script.parser.internal.validator.DataTypeMapper;
import com.tibco.xpd.script.parser.internal.validator.ISymbolTableExt;
import com.tibco.xpd.script.parser.internal.validator.IValidateResult;
import com.tibco.xpd.script.parser.internal.validator.IVarNameResolver;
import com.tibco.xpd.script.parser.internal.validator.ValidationUtil;
import com.tibco.xpd.script.parser.util.ParseUtil;
import com.tibco.xpd.script.parser.util.ScriptEngineUtil;
import com.tibco.xpd.script.parser.validator.IExpressionValidator;
import com.tibco.xpd.script.parser.validator.ISymbolTable;

import antlr.ASTFactory;
import antlr.LLkParser;
import antlr.RecognitionException;
import antlr.Token;
import antlr.collections.AST;

public abstract class AbstractExpressionValidator extends AbstractValidator
        implements IExpressionValidator {

    public static final int NOT_ALLOWED_OPERATION = -8888;

    private JScriptParser scriptParser;

    private IVarNameResolver varNameResolver;

    private IInfoObject infoObject;

    /**
     * As per grammar, an expression can be 1.postfixExpression a.
     * primaryExpression 1. Identifier 2. Literal 3. true, false, 4. constant
     * [NUM_INT|STRING_LITERAL|NUM_FLOAT|NUM_LONG|NUM_DOUBLE b. Identifier c.
     * Method Call d. New Expression e. Reference to Array with index
     * 
     * @param exprAST
     * @param token
     * @return
     */
    protected IScriptRelevantData convertExprASTInString(AST exprAST,
            Token token) {
        AST exprChildAST = exprAST.getFirstChild();
        IScriptRelevantData strExprChild =
                convertExprChildASTInString(exprChildAST, token);
        return strExprChild;
    }

    /**
     * As per grammar, an expression can be 1.postfixExpression a.
     * primaryExpression 1. Identifier 2. Literal 3. true, false, 4. constant
     * [NUM_INT|STRING_LITERAL|NUM_FLOAT|NUM_LONG|NUM_DOUBLE b. Identifier c.
     * Method Call d. New Expression e. Reference to Array with index
     * 
     * @param exprAST
     * @param token
     * @return
     */
    protected IScriptRelevantData convertExprChildASTInString(AST exprChildAST,
            Token token) {
        IScriptRelevantData strParameterType;
        if (exprChildAST == null) {
            return null;
        }
        int childASTType = exprChildAST.getType();
        if (JScriptTokenTypes.IDENT == childASTType
                || JScriptTokenTypes.METHOD_CALL == childASTType
                || JScriptTokenTypes.DOT == childASTType
                || childASTType == JScriptTokenTypes.INDEX_OP
                || childASTType == JScriptTokenTypes.LITERAL_new) {
            // taking care of an identifier
            strParameterType =
                    convertNonArithmeticExprChildASTInString(exprChildAST,
                            token);
        } else {
            // Assuming a constant/expression is passed, so getting a
            // specific type.
            boolean isLiteral = ValidationUtil.isLiteral(exprChildAST);
            if (isLiteral) {
                String dataTypeName =
                        DataTypeMapper.getCorrespondingDataType(childASTType);
                strParameterType =
                        JScriptUtils.resolveJavaScriptStringType(dataTypeName,
                                dataTypeName,
                                false,
                                getSupportedJsClasses());
            } else {
                strParameterType =
                        evaluateArithmeticExpression(exprChildAST, token);
            }
        }
        return strParameterType;
    }

    /**
     * As per grammar, an expression can be 1.postfixExpression a.
     * primaryExpression 1. Identifier 2. Literal 3. true, false, 4. constant
     * [NUM_INT|STRING_LITERAL|NUM_FLOAT|NUM_LONG|NUM_DOUBLE b. Identifier c.
     * Method Call d. New Expression e. Reference to Array with index
     * 
     * @param exprAST
     * @param token
     * @return
     */
    protected IScriptRelevantData convertNonArithmeticExprChildASTInString(
            AST exprChildAST, Token token) {
        int childASTType = exprChildAST.getType();
        IScriptRelevantData strParameterType =
                new DefaultScriptRelevantData(JsConsts.UNDEFINED_DATA_TYPE,
                        JsConsts.UNDEFINED_DATA_TYPE, false);
        if (JScriptTokenTypes.IDENT == childASTType) {
            // taking care of an identifier
            JsDataType strParameterDataType =
                    getIdentDataType(exprChildAST, token);
            if (strParameterDataType.isTypeUndefined() && strParameterDataType
                    .getUndefinedCause() == JsConsts.UNDEFINED_DATA_TYPE_CAUSE) {
                String message =
                        Messages.AbstractExpressionValidator_Undefined_DataType;
                addWarningMessage(token, message);
            }
            strParameterType = strParameterDataType.getType();

        } else if (JScriptTokenTypes.DOT == childASTType
                || JScriptTokenTypes.METHOD_CALL == childASTType
                || JScriptTokenTypes.INDEX_OP == childASTType) {
            // taking care of an dot notation, array, and method call
            List<JsClass> supportedJsClasses = getSupportedJsClasses();
            Map<String, IScriptRelevantData> localVariablesMap =
                    getLocalVariablesMap();
            Map<String, IScriptRelevantData> localMethodsMap =
                    getLocalMethodsMap();
            // start of fix for MR 36870
            if (JScriptTokenTypes.INDEX_OP == childASTType) {
                AST identAST = exprChildAST.getFirstChild();
                if (identAST != null
                        && JScriptTokenTypes.IDENT == identAST.getType()) {
                    String identName = identAST.getText();
                    IScriptRelevantData scriptRelevantData = getSymbolTable()
                            .getScriptRelevantDataType(identName);
                    if (scriptRelevantData != null) {
                        // do not want to check for local variable as it is
                        // javascript where a variable can change its datatype
                        // dynamically
                        /*
                         * scriptRelevantData = getSymbolTable()
                         * .getLocalVariableType(identName);
                         */
                        boolean array = scriptRelevantData.isArray();
                        if (!array) {
                            List<String> additionalInfo =
                                    new ArrayList<String>();
                            additionalInfo.add(scriptRelevantData.getName());
                            addErrorMessage(token,
                                    Messages.AbstractExpressionValidator_Not_An_Array,
                                    additionalInfo);
                        }
                    }

                }
            }
            // end of fix for MR 36870
            strParameterType = evaluateDotAST(exprChildAST,
                    token,
                    supportedJsClasses,
                    localVariablesMap,
                    localMethodsMap);
        } else if (JScriptTokenTypes.LITERAL_new == childASTType) {
            // MyArr[0]
            strParameterType = evaluateNewExpression(exprChildAST, token);
            if (strParameterType != null && strParameterType.getType() != null
                    && strParameterType.getType()
                            .equals(JsConsts.UNDEFINED_DATA_TYPE)) {
                String message =
                        Messages.AbstractExpressionValidator_Undefined_DataType;
                addWarningMessage(token, message);
            }
        }
        return strParameterType;
    }

    /**
     * This method is called by a method which expects the return type as a
     * simple string, not converted into antlr specific.
     * 
     * @param methodAST
     * @param token
     * @return
     */
    /*
     * protected String getMethodReturnType(AST methodAST, Token token) { String
     * toReturn = null; String[] classAndMethodName = ParseUtil
     * .getClassAndMethodName(methodAST); String className =
     * classAndMethodName[0]; String methodName = classAndMethodName[1]; boolean
     * validNames = isClassAndMethodNameValid(className, methodName, token); if
     * (!validNames) { return JsConsts.UNDEFINED_DATA_TYPE; } List<AST>
     * methodParametersList = getMethodParameterAST(methodAST, token);
     * 
     * JsMethod jsMethod = getMethodWithSignature(className, methodName,
     * methodParametersList, token);
     * 
     * if (jsMethod == null) { toReturn = JsConsts.UNDEFINED_DATA_TYPE; } else {
     * JsMethodParam returnType = jsMethod.getReturnType(); if (returnType !=
     * null) { toReturn = returnType.getType(); } } return toReturn; }
     */

    /**
     * It should try to identify a particular method matching the name and the
     * type of params
     * 
     * @param className
     * @param methodName
     * @param token
     * @return
     */
    protected JsMethod getMethodWithSignature(String className,
            String methodName, List<AST> passedParamTypeList, Token token) {
        boolean supportedClass = isSupportedClass(className, token);
        if (!supportedClass) {
            return null;
        }
        List<JsClassDefinitionReader> classDefinitionReaders =
                getClassDefinitionReader();
        JsClass jsClass = null;
        if (classDefinitionReaders != null
                && !classDefinitionReaders.isEmpty()) {
            for (JsClassDefinitionReader jsClassDefinitionReader : classDefinitionReaders) {
                if (jsClassDefinitionReader != null && jsClassDefinitionReader
                        .getJsClass(className) != null) {
                    jsClass = jsClassDefinitionReader.getJsClass(className);
                    break;
                }
            }
        }
        List<JsMethod> methodList = null;
        if (methodName != null && jsClass != null) {
            methodList = jsClass.getMethodList(methodName);
        }
        if (methodList == null || methodList.isEmpty()) {
            String errorMessage =
                    Messages.JsValidationStrategy_MethodNotSupportedOnClass;
            List<String> additionalAttributes = new ArrayList<String>();
            additionalAttributes.add(methodName);
            additionalAttributes.add(className);
            addErrorMessage(token, errorMessage, additionalAttributes);
            return null;
        }
        JsMethod matchedMethod = null;
        Map<String, List<String>> tempErrorList =
                new HashMap<String, List<String>>();
        Map<String, List<String>> tempWarningList =
                new HashMap<String, List<String>>();
        for (JsMethod jsMethod : methodList) {
            List<JsMethodParam> expectedParamTypeList =
                    jsMethod.getParameterType();
            tempErrorList = new HashMap<String, List<String>>();
            tempWarningList = new HashMap<String, List<String>>();
            boolean paramMatch = matchParamsType(expectedParamTypeList,
                    passedParamTypeList,
                    token,
                    className,
                    methodName,
                    tempErrorList,
                    tempWarningList);
            if (paramMatch) {
                matchedMethod = jsMethod;
                break;
            }
        }
        if (matchedMethod == null) {
            Set<String> warningKeySet = tempWarningList.keySet();
            if (warningKeySet != null) {
                for (String warningKey : warningKeySet) {
                    List<String> additionalInformation =
                            tempWarningList.get(warningKey);
                    addWarningMessage(token, warningKey, additionalInformation);
                }
            }
            Set<String> errorKeySet = tempErrorList.keySet();
            for (String errorKey : errorKeySet) {
                List<String> additionalInformation =
                        tempErrorList.get(errorKey);
                addErrorMessage(token, errorKey, additionalInformation);
            }
        }
        return matchedMethod;
    }

    protected boolean isSupportedClass(String className, Token token) {

        List<JsClassDefinitionReader> classDefinitionReaders =
                getClassDefinitionReader();
        JsClass jsClass = null;
        if (classDefinitionReaders != null
                && !classDefinitionReaders.isEmpty()) {
            for (JsClassDefinitionReader jsClassDefinitionReader : classDefinitionReaders) {
                if (jsClassDefinitionReader != null && jsClassDefinitionReader
                        .getJsClass(className) != null) {
                    jsClass = jsClassDefinitionReader.getJsClass(className);
                    break;
                }
            }
        }
        String errorMessage = Messages.JsValidationStrategy_ClassNoSupported;
        if (jsClass == null) {
            List<String> additionalAttributes = new ArrayList<String>();
            additionalAttributes.add(className);
            addErrorMessage(token, errorMessage, additionalAttributes);
            return false;
        }
        return true;
    }

    protected List<IScriptRelevantData> convertMethodParamTypesInString(
            List<AST> astParameterList, Token token) {
        ArrayList<IScriptRelevantData> strParameterTypeList =
                new ArrayList<IScriptRelevantData>();
        for (AST exprAST : astParameterList) {
            IScriptRelevantData strParameterType =
                    convertExprASTInString(exprAST, token);
            if (strParameterType == null) {
                strParameterType = new DefaultScriptRelevantData(
                        JsConsts.UNDEFINED_DATA_TYPE,
                        JsConsts.UNDEFINED_DATA_TYPE, false);
            }
            strParameterTypeList.add(strParameterType);
        }
        return strParameterTypeList;
    }

    /**
     * This method returns a list of exprAST[type is EXPR] which form the list
     * of parameters for the method call.
     * 
     * @param methodCall
     * @param token
     * @return
     */
    protected List<AST> getMethodParameterAST(AST methodCall, Token token) {
        ArrayList<AST> toReturn = new ArrayList<AST>();
        AST dotAST = methodCall.getFirstChild();
        if (dotAST != null && dotAST.getType() == JScriptTokenTypes.DOT) {
            AST eListAST = dotAST.getNextSibling();
            if (eListAST != null
                    && eListAST.getType() == JScriptTokenTypes.ELIST) {
                // getting the first parameter.
                AST exprAST = eListAST.getFirstChild();
                if (exprAST == null) {
                    return toReturn;
                }
                // as per grammar, all the parameterWrapperAST are expression
                toReturn.add(exprAST);
                // iterating through all the rest of parameters
                while (exprAST.getNextSibling() != null) {
                    AST tempAST = exprAST.getNextSibling();
                    toReturn.add(tempAST);
                    exprAST = tempAST;
                }

            }
        }
        return toReturn;
    }

    /**
     * Some strParamterType starts with double quotes, so removing them.
     * 
     * @param strParameterType
     * @return
     */
    protected String massagePassedString(String strParameterType) {
        return ExpressionUtil.massagePassedString(strParameterType);
    }

    protected String getExpressionString(AST expression, Token token) {
        // AST firstChild = expression.getFirstChild();
        // AST nextSibling = firstChild.getNextSibling();
        // String toRetrun = firstChild.getText() + expression.getText()
        // + nextSibling.getText();
        Map<String, IScriptRelevantData> varMap =
                new HashMap<String, IScriptRelevantData>();
        varMap.putAll(getSupportedScriptRelevantDataMap());
        varMap.putAll(getLocalVariablesMap());
        List<String> supportedClasses = getSupportedClasses();
        String toReturn = resolveExpressionString(expression,
                varMap,
                supportedClasses,
                token);
        return toReturn;
    }

    protected boolean isClassAndMethodNameValid(String className,
            String methodName, Token token) {
        String errorMessage = null;
        boolean valid = true;
        if (className == null || className.length() < 1) {
            valid = false;
            boolean b = isLocalMethodDefintionAllowed();
            if (!b) {
                errorMessage = Messages.AbstractExpressionValidator_1;
            }
        }
        if (methodName == null || methodName.length() < 1) {
            valid = false;
            errorMessage = Messages.AbstractExpressionValidator_2;
        }
        if (!valid) {
            addErrorMessage(token, errorMessage);
        }
        return valid;
    }

    /**
     * This method will parse the expression and return a String reprsentation
     * of the expression which can be passed to rhino for evaluation.
     * 
     * 
     * @param expressionAST
     * @param varMap
     * @return
     */
    protected String resolveExpressionString(AST expressionAST,
            Map<String, IScriptRelevantData> varMap,
            List<String> supportedClasses, Token token) {

        StringBuffer buffer = new StringBuffer();
        JScriptEmitter emitter = new JScriptEmitter(buffer);
        Map<String, String> defaultValueMap =
                DataTypeMapper.getDefaultValue(varMap);
        // need to ensure that there is no method_call definition.
        // TODO: Check if this is ever a method call, if it isnt, use
        // the resolveMethodCall in ParseUtil
        AST newExpressionAST =
                resolveMethodCall(expressionAST, supportedClasses, token);
        emitter.setOldNewProcessDataMap(defaultValueMap);
        String toReturn = ""; //$NON-NLS-1$
        try {
            emitter.expr(newExpressionAST);
            toReturn = buffer.toString();
        } catch (RecognitionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return toReturn;

        // AST firstChild = expressionAST.getFirstChild();
        // boolean isChildLeaf = isLeaf(firstChild);
        // if (!isChildLeaf) {
        // toReturn += getExpressionString(firstChild, varMap, dataTypeMapper,
        // supportedClasses);
        // AST nextSibling = firstChild.getNextSibling();
        // String ncText = parseNextSibling(nextSibling, varMap,
        // dataTypeMapper, supportedClasses);
        // toReturn = "(" + toReturn + expressionAST.getText() + ncText + ")";
        // } else {
        // int fcType = firstChild.getType();
        // String fcText = "";
        // if (fcType == JScriptTokenTypes.IDENT) {
        // boolean supportedClass = supportedClasses.contains(firstChild
        // .getText());
        // if (supportedClass) {
        //
        // } else {
        // fcText = getDefaultValueForIdent(firstChild, varMap,
        // dataTypeMapper);
        // }
        // } else {
        // fcText = firstChild.getText();
        // }
        // AST nextSibling = firstChild.getNextSibling();
        // String ncText = parseNextSibling(nextSibling, varMap,
        // dataTypeMapper, supportedClasses);
        // toReturn = "(" + toReturn + fcText + expressionAST.getText()
        // + ncText + ")";
        // }
        // return toReturn;
    }

    protected AST resolveMethodCall(AST expressionAST,
            List<String> supportedClasses, Token token) {

        List<Integer> astTypeList = new ArrayList<Integer>();
        astTypeList.add(JScriptTokenTypes.METHOD_CALL);
        astTypeList.add(JScriptTokenTypes.DOT);
        List<AST> childASTList =
                ParseUtil.getChildASTList(expressionAST, astTypeList);
        if (childASTList == null || childASTList.isEmpty()) {
            return expressionAST;
        }
        ASTFactory astFactory = new ASTFactory();
        AST dupExpresssionAST = astFactory.dupTree(expressionAST);
        childASTList =
                ParseUtil.getChildASTList(dupExpresssionAST, astTypeList);
        for (AST methodCallAST : childASTList) {
            IScriptRelevantData returnType = evaluateDotAST(methodCallAST,
                    token,
                    getSupportedJsClasses(),
                    getLocalVariablesMap(),
                    getLocalMethodsMap());
            String methodReturnType = JsConsts.UNDEFINED_DATA_TYPE;
            if (returnType != null) {
                if (returnType.isArray()) {
                    methodReturnType = JsConsts.ARRAY;
                } else {
                    methodReturnType = returnType.getType();
                }
            }
            int iReturnType =
                    DataTypeMapper.mapReturnDataType(methodReturnType);
            String defaultValue =
                    DataTypeMapper.getDefaultValue(methodReturnType);
            methodCallAST.setType(iReturnType);
            methodCallAST.setText(defaultValue);
        }
        return dupExpresssionAST;
    }

    @Override
    public void setScriptParser(LLkParser scriptParser) {
        if (scriptParser instanceof JScriptParser) {
            this.scriptParser = (JScriptParser) scriptParser;
        }
    }

    protected JScriptParser getScriptParser() {
        return this.scriptParser;
    }

    protected ISymbolTable getSymbolTable() {
        if (this.scriptParser != null) {
            return this.scriptParser.getSymbolTable();
        }
        return null;
    }

    /**
     * 
     * @return
     */
    protected boolean isLocalMethodDefintionAllowed() {
        return true;
    }

    protected IScriptRelevantData evaluateDotAST(AST dotAST, Token token,
            List<JsClass> supportedJsClasses,
            Map<String, IScriptRelevantData> localVariablesMap,
            Map<String, IScriptRelevantData> localMethodsMap) {
        int fAstType = dotAST.getType();
        Map<String, IScriptRelevantData> scriptRelevantDataMap =
                getSupportedScriptRelevantDataMap();
        if (fAstType == JScriptTokenTypes.DOT
                || fAstType == JScriptTokenTypes.INDEX_OP
                || fAstType == JScriptTokenTypes.IDENT
                || fAstType == JScriptTokenTypes.METHOD_CALL) {
            // Get the JsExpression of that AST
            JsExpression jsExpression =
                    ExpressionUtil.getJsExpressionFromAST(dotAST,
                            DataTypeMapper.getSymbolTableKeyWords(),
                            scriptRelevantDataMap);
            if (jsExpression != null) {
                // We can not check if a local
                // function has been defined at the moment
                if (jsExpression instanceof JsExpressionMethod) {
                    String methodName = jsExpression.getName();
                    if (localMethodsMap != null
                            && localMethodsMap.containsKey(methodName)) {
                        IScriptRelevantData methodType =
                                localMethodsMap.get(methodName);
                        if (methodType != null) {
                            jsExpression.setName(
                                    JScriptUtils.resolveJavaScriptDataType(
                                            methodType.getType()));
                        }
                    } else {
                        String message =
                                Messages.AbstractExpressionValidator_Undefined_DataType;
                        addWarningMessage(token, message);
                        IScriptRelevantData returnDataType =
                                JScriptUtils.resolveJavaScriptStringType(
                                        jsExpression.getName(),
                                        JsConsts.OBJECT,
                                        false,
                                        getSupportedJsClasses());
                        return returnDataType;
                    }
                }
                // Evaluate method parameters
                evaluateMethodParamTypes(token, jsExpression);
                // Check the data type of the expression
                JsDataType dataType = null;
                dataType = JScriptUtils.getScriptRelevantDataType(jsExpression,
                        supportedJsClasses,
                        scriptRelevantDataMap,
                        localVariablesMap,
                        localMethodsMap);
                if (dataType == null || dataType.isTypeUndefined()) {
                    createJsParserErrorMessage(token,
                            dataType,
                            jsExpression.getName());
                    IScriptRelevantData returnDataType =
                            new DefaultScriptRelevantData(
                                    jsExpression.getName(),
                                    JsConsts.UNDEFINED_DATA_TYPE, false);
                    return returnDataType;
                }
                // Get all the array index expressions
                List<JsExpression> arrayIndexExpressionList = ExpressionUtil
                        .getArrayIndexExpressionList(jsExpression);
                // Check the data type of all the arrayIndexExpressions
                for (Iterator<JsExpression> iterator =
                        arrayIndexExpressionList.iterator(); iterator
                                .hasNext();) {
                    JsExpression arrayIndexExpression = iterator.next();
                    JsDataType arrayIndexExpressionType =
                            evaluateArrayIndexExpression(arrayIndexExpression,
                                    token);
                    if (!JScriptUtils.isValidIndexType(
                            arrayIndexExpressionType.getTypeName())) {
                        if (arrayIndexExpressionType
                                .getUndefinedCause() == JsConsts.UNDEFINED_DATA_TYPE_CAUSE) {
                            String message =
                                    Messages.AbstractExpressionValidator_Undefined_DataType;
                            addWarningMessage(token, message);
                        } else {
                            String message =
                                    Messages.AbstractExpressionValidator_Invalid_Index_Type;
                            List<String> additionalAttributes =
                                    new ArrayList<String>();
                            additionalAttributes
                                    .add(arrayIndexExpression.getName());
                            addErrorMessage(token,
                                    message,
                                    additionalAttributes);
                        }
                        IScriptRelevantData returnDataType =
                                new DefaultScriptRelevantData(
                                        jsExpression.getName(),
                                        JsConsts.UNDEFINED_DATA_TYPE, false);
                        return returnDataType;
                    }
                }
                return dataType.getType();
            }
        }
        String warningMessage =
                Messages.AbstractExpressionValidator_Undefined_DataType;
        addWarningMessage(token, warningMessage);
        IScriptRelevantData returnDataType =
                new DefaultScriptRelevantData(JsConsts.UNDEFINED_DATA_TYPE,
                        JsConsts.UNDEFINED_DATA_TYPE, false);
        return returnDataType;
    }

    protected void evaluateMethodParamTypes(Token token,
            JsExpression jsExpression) {
        if (jsExpression != null) {
            JsExpression nextExpression =
                    JScriptUtils.cloneJsExpression(jsExpression);
            JsExpression parentExpression = null;
            while (nextExpression != null) {
                if (nextExpression instanceof JsExpressionMethod
                        && parentExpression != null) {
                    JsExpressionMethod jsExpressionMethod =
                            (JsExpressionMethod) nextExpression;
                    JsDataType dataType = JScriptUtils
                            .getScriptRelevantDataType(parentExpression,
                                    getSupportedJsClasses(),
                                    getSupportedScriptRelevantDataMap(),
                                    getLocalVariablesMap(),
                                    getLocalMethodsMap());
                    if (dataType != null && !dataType.isTypeUndefined()) {
                        if (dataType
                                .getType() instanceof IUMLScriptRelevantData) {
                            IUMLScriptRelevantData type =
                                    (IUMLScriptRelevantData) dataType.getType();
                            JsClass jsClass = type.getJsClass();
                            if (jsClass != null) {
                                List<JsMethod> supportedMethodList =
                                        jsClass.getMethodList(
                                                jsExpressionMethod.getName());
                                if (supportedMethodList != null) {
                                    evaluateMethodParamTypesMatch(token,
                                            jsExpressionMethod.getName(),
                                            supportedMethodList,
                                            jsExpressionMethod
                                                    .getMethodParameterList());
                                }
                            }
                        }
                    }
                }
                JsExpression newNextExpression =
                        JScriptUtils.cloneJsExpression(nextExpression);
                newNextExpression.setNextExpression(null);
                if (parentExpression == null) {
                    parentExpression = newNextExpression;
                } else {
                    parentExpression.setLastExpression(newNextExpression);
                }
                nextExpression = nextExpression.getNextExpression();
            }
        }
    }

    protected void evaluateMethodParamTypesMatch(Token token, String methodName,
            List<JsMethod> supportedMethodList,
            List<JsExpression> methodParameterList) {

    }

    public void createJsParserErrorMessage(Token token, JsDataType dataType,
            String propertyName) {
        String message =
                Messages.AbstractExpressionValidator_Undefined_DataType;
        List<String> additionalAttributes = new ArrayList<String>();
        if (dataType != null) {
            String dataTypeName = dataType.getTypeName();
            if (dataTypeName != null
                    && dataTypeName.equals(JsConsts.UNDEFINED_DATA_TYPE)) {
                int errorCause = dataType.getUndefinedCause();
                String expression = "";
                JsExpression jsExpression = dataType.getJsExpression();
                JsExpression nextExpression = null;
                if (jsExpression != null) {
                    expression = jsExpression.getName();
                    nextExpression = jsExpression.getNextExpression();
                } else {
                    message =
                            Messages.AbstractExpressionValidator_Undefined_DataType;
                    addWarningMessage(token, message);
                    return;
                }
                switch (errorCause) {
                case JsConsts.UNDEFINED_DATA_TYPE_CAUSE:
                    message =
                            Messages.AbstractExpressionValidator_Undefined_DataType;
                    addWarningMessage(token, message);
                    return;
                case JsConsts.UNKNOWN_PROPERTY_DATA_TYPE_CAUSE:
                    if (nextExpression != null) {
                        message =
                                Messages.JsValidationStrategy_PropertyInvalid_For_Data_Type;
                        additionalAttributes = new ArrayList<String>();
                        additionalAttributes.add(nextExpression.getName());
                        additionalAttributes.add(expression);
                        addErrorMessage(token, message, additionalAttributes);
                        return;
                    }
                    break;
                case JsConsts.UNKNOWN_METHOD_DATA_TYPE_CAUSE:
                    if (nextExpression != null) {
                        message =
                                Messages.JsValidationStrategy_MethodInvalid_For_Data_Type;
                        additionalAttributes = new ArrayList<String>();
                        additionalAttributes
                                .add(nextExpression.getName() + "()");//$NON-NLS-1$
                        additionalAttributes.add(expression);
                        addErrorMessage(token, message, additionalAttributes);
                        return;
                    }
                    break;
                case JsConsts.ARRAY_EXPECTED_DATA_TYPE_CAUSE:
                    message = Messages.JsValidationStrategy_Array_Expected;
                    additionalAttributes = new ArrayList<String>();
                    additionalAttributes.add(expression);
                    addErrorMessage(token, message, additionalAttributes);
                    return;
                case JsConsts.ARRAY_NOT_EXPECTED_DATA_TYPE_CAUSE:
                    message = Messages.JsValidationStrategy_Array_NotExpected;
                    additionalAttributes = new ArrayList<String>();
                    additionalAttributes.add(expression);
                    addErrorMessage(token, message, additionalAttributes);
                    return;
                case JsConsts.INVALID_INDEX_DATA_TYPE_CAUSE:
                    message =
                            Messages.AbstractExpressionValidator_Invalid_Index_Type;
                    additionalAttributes = new ArrayList<String>();
                    additionalAttributes.add(expression);
                    addErrorMessage(token, message, additionalAttributes);
                    return;
                case JsConsts.UNDEFINED_VARIABLE_DATA_TYPE_CAUSE:
                    message = Messages.JsValidationStrategy_Variable_Undefined;
                    additionalAttributes = new ArrayList<String>();
                    additionalAttributes.add(expression);
                    addErrorMessage(token, message, additionalAttributes);
                    return;
                case JsConsts.UNDEFINED_CLASS_DATA_TYPE_CAUSE:
                    message = Messages.JsValidationStrategy_ClassNotDefined;
                    additionalAttributes = new ArrayList<String>();
                    additionalAttributes.add(expression);
                    addErrorMessage(token, message, additionalAttributes);
                    return;
                default:
                    message =
                            Messages.AbstractExpressionValidator_Undefined_DataType;
                    addWarningMessage(token, message);
                }
            }
        } else {
            message = Messages.AbstractExpressionValidator_30;
            addErrorMessage(token, message);
            return;
        }
    }

    @Override
    public void setVarNameResolver(IVarNameResolver varNameResolver) {
        this.varNameResolver = varNameResolver;
    }

    protected IVarNameResolver getVarNameResolver() {
        return this.varNameResolver;
    }

    protected String toASTString(int astType) {
        if (astType == NOT_ALLOWED_OPERATION) {
            return Messages.AbstractExpressionValidator_26;
        }
        String tokenName = getScriptParser().getTokenName(astType);
        return tokenName;
    }

    protected JsDataType evaluateArrayIndexExpression(
            JsExpression arrayIndexExpression, Token token) {
        JsDataType dataType = new JsDataType();
        dataType.setJsExpression(arrayIndexExpression);
        if (arrayIndexExpression != null) {
            String varName =
                    ExpressionUtil.getJsExpressionName(arrayIndexExpression);
            if (isSimpleNumericIndexExpression(arrayIndexExpression)) {
                IScriptRelevantData intScriptRelevantData =
                        new DefaultScriptRelevantData(
                                arrayIndexExpression.getName(),
                                JsConsts.INTEGER, false);
                dataType.setType(intScriptRelevantData);
                return dataType;
            } else if (isLocalVariable(varName)) {
                // get the type of the variable
                dataType = getVariableType(varName, token);
                return dataType;
            } else {
                ISymbolTable symbolTable = getSymbolTable();
                if (symbolTable != null) {
                    Map<String, IScriptRelevantData> scriptRelevantDataMap =
                            symbolTable.getScriptRelevantDataTypeMap();
                    Map<String, IScriptRelevantData> localVariablesMap =
                            symbolTable.getLocalVariableMap();
                    Map<String, IScriptRelevantData> localMethodsMap =
                            symbolTable.getLocalMethodMap();
                    dataType = JScriptUtils.getScriptRelevantDataType(
                            arrayIndexExpression,
                            getSupportedJsClasses(),
                            scriptRelevantDataMap,
                            localVariablesMap,
                            localMethodsMap);
                }
                return dataType;
            }
        }
        return dataType;
    }

    /*
     * protected int evaluateArrayIndexOperator(AST arrayIndexAST, Token token)
     * { AST arrVarAST = arrayIndexAST.getFirstChild(); boolean isArray =
     * isArrayVariable(arrVarAST, token); if (!isArray) { // this also stops
     * MyDataField[1][2] usage, so thats good :-) addErrorMessage(token,
     * Messages.AbstractExpressionValidator_11); return
     * JScriptTokenTypes.UNDEFINE_DATA_TYPE; } AST indexExprAST =
     * arrVarAST.getNextSibling(); // indexExprAST has to be EXPR, so using the
     * function. String exprStrValue = convertExprASTInString(indexExprAST,
     * token); Integer iReturnValue = getDataTypeMapper().mapReturnDataType(
     * exprStrValue); boolean isNumeric =
     * isNonDecimalNumericDataType(iReturnValue); if (!isNumeric) {
     * addErrorMessage(token, Messages.AbstractExpressionValidator_12); return
     * JScriptTokenTypes.UNDEFINE_DATA_TYPE; }
     * 
     * IScriptRelevantData arrVariableType =
     * getVariableType(arrVarAST.getText(), token); String arrayElementType =
     * getDataTypeMapper().getArrayElementType( arrVariableType); Integer
     * iArrElementType = getDataTypeMapper().mapReturnDataType(
     * arrayElementType); return iArrElementType; }
     */

    protected boolean isArrayVariable(AST varAST, Token token) {
        int astType = varAST.getType();
        if (astType == JScriptTokenTypes.IDENT) {
            String varName = varAST.getText();
            boolean isVarDefined = isVarDefined(varName, token);
            if (isVarDefined) {
                JsDataType variableType = getVariableType(varName, token);
                boolean isArray = isArrayDataType(variableType.getType());
                if (isArray) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This method returns true if the passed data type is an array type.
     * 
     * @param dataType
     * @return
     */
    protected boolean isArrayDataType(IScriptRelevantData dataType) {
        if (dataType != null && dataType.isArray()) {
            return true;
        }
        return false;
    }

    protected boolean isNumericDataType(int dataType) {
        if (dataType == JScriptTokenTypes.NUM_INT
                || dataType == JScriptTokenTypes.NUM_FLOAT
                || dataType == JScriptTokenTypes.NUM_DOUBLE
                || dataType == JScriptTokenTypes.NUM_LONG) {
            return true;
        }
        return false;
    }

    protected boolean isNumericDataType(IScriptRelevantData scriptData) {
        if (scriptData == null) {
            return false;
        }
        return isNumericDataType(scriptData.getType());
    }

    protected boolean isNumericDataType(String dataType) {
        if (dataType == null) {
            return false;
        }
        if (JsConsts.INT.equals(dataType) || JsConsts.INTEGER.equals(dataType)
                || JsConsts.FLOAT.equals(dataType)
                || JsConsts.LONG.equals(dataType)
                || JsConsts.DOUBLE.equals(dataType)
                || JsConsts.DECIMAL.equals(dataType)
                || JsConsts.NUMBER.equals(dataType)) {
            return true;
        }
        return false;
    }

    protected String getBiggerNumericDataType(
            IScriptRelevantData numericDataType1,
            IScriptRelevantData numericDataType2) {
        String biggerNumericDataType = null;
        String typeStr1 = convertSpecificToGenericType(numericDataType1);
        String typeStr2 = convertSpecificToGenericType(numericDataType2);
        if (typeStr1 != null && typeStr2 != null) {
            if (typeStr1.equals(JsConsts.DECIMAL)
                    || typeStr2.equals(JsConsts.DECIMAL)) {
                return JsConsts.DECIMAL;
            } else if (typeStr1.equals(JsConsts.INTEGER)
                    || typeStr2.equals(JsConsts.INTEGER)) {
                return JsConsts.INTEGER;
            } else {
                return typeStr1;
            }

        }
        return biggerNumericDataType;
    }

    protected boolean isIntegerDataType(IScriptRelevantData scriptData) {
        if (scriptData == null) {
            return false;
        }
        String dataType = scriptData.getType();
        if (JsConsts.INT.equals(dataType) || JsConsts.INTEGER.equals(dataType)
                || JsConsts.LONG.equals(dataType)) {
            return true;
        }
        return false;
    }

    protected boolean isNonDecimalNumericDataType(int dataType) {
        if (dataType == JScriptTokenTypes.NUM_INT
                || dataType == JScriptTokenTypes.NUM_LONG) {
            return true;
        }
        return false;
    }

    protected boolean isDecimalNumericDataType(int dataType) {
        if (dataType == JScriptTokenTypes.NUM_FLOAT
                || dataType == JScriptTokenTypes.NUM_DOUBLE) {
            return true;
        }
        return false;
    }

    protected boolean isNumericVariable(AST fcAST, Token token) {
        String strVarName = fcAST.getText();
        boolean isVarDefined = isVarDefined(strVarName, token);
        if (!isVarDefined) {
            return false;
        }
        JsDataType variableType = getVariableType(strVarName, token);
        if (variableType.isTypeUndefined()) {
            return false;
        }
        String variableTypeName = variableType.getTypeName();
        if (variableTypeName == null) {
            return false;
        }
        // myIntField = 0; changes the data type of the script relevant data
        // from
        // integer to number. so including Number as check.
        if (variableTypeName.equalsIgnoreCase(JsConsts.FLOAT)
                || variableTypeName.equalsIgnoreCase(JsConsts.INTEGER)
                || variableTypeName.equalsIgnoreCase(JsConsts.NUMBER)) {
            return true;
        }
        return false;
    }

    protected boolean isBooleanVariable(AST fcAST, Token token) {
        String strVarName = fcAST.getText();
        boolean isVarDefined = isVarDefined(strVarName, token);
        if (!isVarDefined) {
            return false;
        }
        JsDataType variableType = getVariableType(strVarName, token);
        if (variableType.isTypeUndefined()) {
            return false;
        }
        String variableTypeName = variableType.getTypeName();
        if (variableTypeName == null) {
            return false;
        }
        if (variableTypeName.equalsIgnoreCase(JsConsts.BOOLEAN)) {
            return true;
        }
        return false;
    }

    protected boolean isSimpleNumericIndexExpression(
            JsExpression jsExpression) {
        boolean isNumeric = false;
        if (jsExpression != null
                && !JScriptUtils.hasMoreJSChildren(jsExpression)) {
            String name = jsExpression.getName();
            if (name != null && !name.contains(".")) {
                try {
                    int intValue = Integer.parseInt(name);
                    isNumeric = true;
                } catch (NumberFormatException e) {
                    // Do nothing, this just means that it is not an integer
                }
            }
        }
        return isNumeric;
    }

    protected JsDataType getIdentDataType(AST exprChildAST, Token token) {
        JsDataType strParameterType = new JsDataType();
        String varName = exprChildAST.getText();
        boolean isDefined = isVarDefined(varName, token);
        if (isDefined) {
            JsDataType tempParamType = getVariableType(varName, token);
            if (tempParamType != null) {
                strParameterType = tempParamType;
            }
        }
        return strParameterType;
    }

    protected boolean isLocalVariable(String varName) {
        boolean isLocalVariable = false;
        ISymbolTable symbolTable = getSymbolTable();
        if (symbolTable != null) {
            isLocalVariable = symbolTable.isLocalVariable(varName);
        }
        return isLocalVariable;
    }

    /**
     * This method works out whether the specific variable is defined or not.
     * This has to be specific to each ValidationStrategy, but the default
     * behaviour is provided here
     * 
     * @param varName
     * @param token
     * @return
     */
    protected boolean isVarDefined(String varName, Token token) {
        ISymbolTable symbolTable = getScriptParser().getSymbolTable();
        boolean bool = false;
        String errorMessage = Messages.JsValidationStrategy_Variable_Undefined;
        if (symbolTable != null) {
            Map<String, IScriptRelevantData> localVariableMap =
                    symbolTable.getLocalVariableMap();
            Map<String, IScriptRelevantData> scriptRelevantDataMap =
                    symbolTable.getScriptRelevantDataTypeMap();
            bool = JScriptUtils.isVariableDefined(localVariableMap,
                    scriptRelevantDataMap,
                    DataTypeMapper.getSymbolTableKeyWords(),
                    varName);
            // Variable is not defined
            if (!bool) {
                /*
                 * XPD-4800 check if there is an Enumeration with given
                 * unqualified name.
                 */
                for (String string : scriptRelevantDataMap.keySet()) {
                    if (scriptRelevantDataMap.get(
                            string) instanceof AbstractUMLScriptRelevantData) {
                        AbstractUMLScriptRelevantData data =
                                (AbstractUMLScriptRelevantData) scriptRelevantDataMap
                                        .get(string);
                        if (data.getJsClass() instanceof JsEnumeration) {
                            JsEnumeration enumeration =
                                    (JsEnumeration) data.getJsClass();
                            /*
                             * unqualified names for enumeration is not
                             * supported in ambiguous situation
                             */
                            if (enumeration.getDataType() != null && enumeration
                                    .getDataType().getName().equals(varName)) {
                                errorMessage =
                                        Messages.JsValidationStrategy_Ambiguity_Unqualified_Enum_NotSupported;
                                break;
                            }
                        }

                    }

                }
            }

        }
        if (!bool) {
            List<String> additionalAttributes = new ArrayList<String>();
            additionalAttributes.add(varName);
            addErrorMessage(token, errorMessage, additionalAttributes);
        }
        return bool;
    }

    /**
     * 
     * @param varName
     * @param token
     * @return
     */
    protected JsDataType getVariableType(String varName, Token token) {
        JsDataType variableType = new JsDataType();
        ISymbolTable symbolTable = getScriptParser().getSymbolTable();
        // Search the type in the local variables
        IScriptRelevantData localVarType =
                symbolTable.getLocalVariableType(varName);
        if (localVarType != null) {
            variableType.setType(localVarType);
            return variableType;
        }
        IScriptRelevantData toReturn =
                symbolTable.getScriptRelevantDataType(varName);
        variableType.setType(toReturn);
        if (toReturn == null) {
            String message =
                    Messages.AbstractExpressionValidator_Undefined_DataType;
            addWarningMessage(token, message);
        }

        return variableType;
    }

    /**
     * This method will help each specific validation strategy to be define
     * their own algorithm for matching the passed and expected parameters, the
     * default behaviour is provided.
     * 
     * @param expectedParameterType
     * @param actualParameterType
     * @param token
     * @param className
     * @param methodName
     * @return
     */
    protected boolean matchParamsType(
            List<JsMethodParam> expectedParameterTypeList,
            List<AST> actualParameterTypeList, Token token, String className,
            String methodName, Map<String, List<String>> errorList,
            Map<String, List<String>> warningList) {
        return true;
    }

    protected IScriptRelevantData evaluateArithmeticExpression(AST exprChildAST,
            Token token) {
        try {
            String strExpression = getExpressionString(exprChildAST, token);
            ScriptEngine engine = ScriptEngineUtil.getScriptEngine();
            Object returnValue = engine.eval(strExpression);
            IScriptRelevantData dataType =
                    ParseUtil.getDataType(returnValue, getSupportedJsClasses());
            return dataType;
        } catch (ScriptException e) {

        }
        IScriptRelevantData scriptRelevantData =
                new DefaultScriptRelevantData(JsConsts.UNDEFINED_DATA_TYPE,
                        JsConsts.UNDEFINED_DATA_TYPE, false);
        return scriptRelevantData;
    }

    protected IScriptRelevantData evaluateNewExpression(AST exprChildAST,
            Token token) {
        AST fcAST = exprChildAST.getFirstChild();
        int fcType = fcAST.getType();
        if (fcType != JScriptTokenTypes.IDENT) {
            addErrorMessage(token,
                    Messages.JScriptExpressionValidator_NewKeywordShouldBeFollowedByAnIdentifier2);
            IScriptRelevantData returnDataType =
                    new DefaultScriptRelevantData(JsConsts.UNDEFINED_DATA_TYPE,
                            JsConsts.UNDEFINED_DATA_TYPE, false);
            return returnDataType;
        }
        String fcText = fcAST.getText();
        List<String> inBuiltClasses = getSupportedClasses();
        boolean contains = inBuiltClasses.contains(fcText);
        if (contains) {
            boolean isArray = false;
            // returning the name of supported class.
            if (JsConsts.ARRAY.equals(fcText)) {
                isArray = true;
            }
            IScriptRelevantData returnDataType =
                    JScriptUtils.resolveJavaScriptStringType(fcText,
                            fcText,
                            isArray,
                            getSupportedJsClasses());
            return returnDataType;
        }
        IScriptRelevantData returnDataType = new DefaultScriptRelevantData(
                fcText, JsConsts.UNDEFINED_DATA_TYPE, false);
        return returnDataType;
    }

    protected List<JsClassDefinitionReader> getClassDefinitionReader() {
        return ExpressionUtil.getClassDefintionReader();
    }

    protected List<String> getSupportedClasses() {
        return ExpressionUtil.getSupportedClasses();
    }

    protected Map<String, IScriptRelevantData> getLocalVariablesMap() {
        Map<String, IScriptRelevantData> localVariablesMap =
                new HashMap<String, IScriptRelevantData>();
        ISymbolTable symbolTable = getSymbolTable();
        if (symbolTable != null) {
            Map<String, IScriptRelevantData> tempLocalVariables =
                    symbolTable.getLocalVariableMap();
            if (tempLocalVariables != null) {
                return tempLocalVariables;
            }
        }
        return localVariablesMap;
    }

    protected Map<String, IScriptRelevantData> getLocalMethodsMap() {
        Map<String, IScriptRelevantData> localMethodsMap =
                new HashMap<String, IScriptRelevantData>();
        ISymbolTable symbolTable = getSymbolTable();
        if (symbolTable != null) {
            Map<String, IScriptRelevantData> tempLocalMethods =
                    symbolTable.getLocalMethodMap();
            if (tempLocalMethods != null) {
                return tempLocalMethods;
            }
        }
        return localMethodsMap;
    }

    /**
     * Use getSupportedJsClasses(IInfoObject infoObject)
     **/
    @Override
    @Deprecated
    public List<JsClass> getSupportedJsClasses() {
        return ExpressionUtil.getSupportedJsClasses();
    }

    protected List<String> getInBuiltClasses() {
        List<String> inbuildClasses = new ArrayList<String>();
        inbuildClasses.add("Array"); //$NON-NLS-1$
        inbuildClasses.add("Boolean"); //$NON-NLS-1$
        inbuildClasses.add("Date"); //$NON-NLS-1$
        inbuildClasses.add("Error"); //$NON-NLS-1$
        inbuildClasses.add("Function"); //$NON-NLS-1$
        inbuildClasses.add("Number"); //$NON-NLS-1$
        inbuildClasses.add("Object"); //$NON-NLS-1$
        inbuildClasses.add("String"); //$NON-NLS-1$
        return inbuildClasses;

    }

    /**
     * Override this method if a filter to the supported script relevant data
     * classes is to be made, by default all the scriptRelevantDataMap is
     * returned
     * 
     * @return Map<String, IScriptRelevantData> all the supported script
     *         relevant data classes
     */
    protected Map<String, IScriptRelevantData> getSupportedScriptRelevantDataMap() {
        Map<String, IScriptRelevantData> supportedScriptRelevantDataMap =
                new HashMap<String, IScriptRelevantData>();
        ISymbolTable symbolTable = getSymbolTable();
        if (symbolTable != null) {
            Map<String, IScriptRelevantData> scriptRelevantDataMap =
                    symbolTable.getScriptRelevantDataTypeMap();

            supportedScriptRelevantDataMap.putAll(scriptRelevantDataMap);
        }
        return supportedScriptRelevantDataMap;
    }

    protected boolean isExistingVariableName(String name) {
        boolean isNewVariableName = false;
        Map<String, IScriptRelevantData> supportedScriptRelevantDataMap =
                getSupportedScriptRelevantDataMap();
        if (name != null && supportedScriptRelevantDataMap != null
                && supportedScriptRelevantDataMap.containsKey(name)) {
            return true;
        }
        return isNewVariableName;
    }

    /**
     * This method uses the expression factory to create a wrapper expression
     * 
     * @param expr
     * @param token
     * 
     * @return {@link IExpr}
     **/
    protected IExpr createExpression(Object expr, Object token) {
        if (infoObject != null && infoObject.getExpressionFactory() != null) {
            return infoObject.getExpressionFactory().createExpr(expr, token);
        } else {
            List<String> additionalAttributes = new ArrayList<String>();
            additionalAttributes.add(getClass().getName());
            String text = String.format(
                    Messages.AbstractExpressionValidator_ExpressionFactoryNotRegistered,
                    additionalAttributes.toArray());
            LOG.error(text);
        }
        return null;
    }

    /**
     * This method should be called to delegate the evaluation of an expression
     * 
     * @param expr
     * @param token
     * 
     * @return {@link IValidateResult}
     **/
    public IValidateResult delegateEvaluateExpression(Object expr,
            Object token) {
        IExpr createdExpression = createExpression(expr, token);
        if (createdExpression != null) {
            if (infoObject != null
                    && infoObject.getExpresionValidatorFactory() != null) {
                IExpressionValidator expresionValidator =
                        infoObject.getExpresionValidatorFactory()
                                .getExpressionValidator(createdExpression);
                if (expresionValidator != null) {
                    expresionValidator.setInfoObject(getInfoObject());
                    IValidateResult evaluate =
                            expresionValidator.evaluate(createdExpression);
                    getErrorMessageList()
                            .addAll(expresionValidator.getErrorMessageList());
                    getWarningMessageList()
                            .addAll(expresionValidator.getWarningMessageList());
                    return evaluate;
                } else {
                    List<String> additionalAttributes = new ArrayList<String>();
                    additionalAttributes.add(getClass().getName());
                    String text = String.format(
                            Messages.AbstractExpressionValidator_ExpressionValidatorNotFound,
                            additionalAttributes.toArray());
                    LOG.warn(text);
                }
            } else {
                List<String> additionalAttributes = new ArrayList<String>();
                additionalAttributes.add(getClass().getName());
                String text = String.format(
                        Messages.AbstractExpressionValidator_ExpressionValidatorFactoryNotRegistered,
                        additionalAttributes.toArray());
                LOG.error(text);
            }
        }
        return null;
    }

    @Override
    public void validate(AST expression, Token token) {
        if (getInfoObject() == null) {
            List<String> additionalAttributes = new ArrayList<String>();
            additionalAttributes.add(getClass().getName());
            String text = String.format(
                    Messages.AbstractExpressionValidator_InfoObject,
                    additionalAttributes.toArray());
            LOG.error(text);
        }
        delegateEvaluateExpression(expression, token);
    }

    @Override
    public IInfoObject getInfoObject() {
        return infoObject;
    }

    @Override
    public void setInfoObject(IInfoObject infoObject) {
        this.infoObject = infoObject;
    }

    @Override
    public IValidateResult evaluate(IExpr expression) {
        // TODO Auto-generated method stub
        return null;
    }

    protected Map<String, String> getTypeMap(IInfoObject infoObject) {
        IDataTypeMapper dataTypeMapper = getDataTypeMapper(infoObject);
        if (dataTypeMapper instanceof IJScriptDataTypeMapper) {
            IJScriptDataTypeMapper jsDataTypeMapper =
                    (IJScriptDataTypeMapper) dataTypeMapper;
            return jsDataTypeMapper.getJavaScriptType();
        }
        return null;
    }

    protected IValidateResult getValidateResult() {
        return new JSValidateResult();
    }

    protected IValidateResult updateResult(IExpr expression,
            IScriptRelevantData type, IScriptRelevantData genericContext) {
        IValidateResult validateResult = getValidateResult();
        if (validateResult != null) {
            validateResult.setExpr(expression);
            if (type instanceof ITypeResolution) {
                ((ITypeResolution) type).setGenericContextType(genericContext);
                List<IScriptRelevantData> typesResolution =
                        ((ITypeResolution) type).getTypesResolution();
                if (typesResolution != null && !typesResolution.isEmpty()) {
                    for (IScriptRelevantData typeResolution : typesResolution) {
                        if (typeResolution instanceof ITypeResolution) {
                            ((ITypeResolution) typeResolution)
                                    .setGenericContextType(genericContext);
                        }
                    }
                }
            }
            validateResult.setType(type);
        }
        return validateResult;
    }

    protected List<JsClass> getSupportedJsClasses(IInfoObject infoObject) {
        List<JsClassDefinitionReader> classDefinitionReaders =
                getClassDefinitionReader(infoObject);
        if (classDefinitionReaders != null
                && !classDefinitionReaders.isEmpty()) {
            List<JsClass> allSupportedClasses = new ArrayList<JsClass>();
            for (JsClassDefinitionReader jsClassDefinitionReader : classDefinitionReaders) {
                List<JsClass> supportedClasses =
                        jsClassDefinitionReader.getSupportedClasses();
                if (supportedClasses != null) {
                    allSupportedClasses.addAll(supportedClasses);
                }
            }
            return allSupportedClasses;
        }
        return Collections.emptyList();
    }

    protected List<String> getSupportedClassNames(IInfoObject infoObject) {
        List<JsClassDefinitionReader> classDefinitionReaders =
                getClassDefinitionReader(infoObject);
        if (classDefinitionReaders != null
                && !classDefinitionReaders.isEmpty()) {
            List<String> allSupportedClassNames = new ArrayList<String>();
            for (JsClassDefinitionReader jsClassDefinitionReader : classDefinitionReaders) {
                if (jsClassDefinitionReader != null) {
                    List<String> supportedClassNames =
                            jsClassDefinitionReader.getSupportedClassNames();
                    if (supportedClassNames != null) {
                        allSupportedClassNames.addAll(supportedClassNames);
                    }
                }
            }
            return allSupportedClassNames;
        }
        return Collections.emptyList();
    }

    protected List<JsMethod> getGlobalMethodsWithName(String globalMethodName) {
        if (globalMethodName != null) {
            List<JsMethod> supportedGlobalMethods =
                    getSupportedGlobalMethods(getInfoObject());
            if (supportedGlobalMethods != null
                    && !supportedGlobalMethods.isEmpty()) {
                List<JsMethod> globalMethodsWithName =
                        new ArrayList<JsMethod>();
                for (JsMethod jsMethod : supportedGlobalMethods) {
                    if (jsMethod != null && jsMethod.getName() != null
                            && jsMethod.getName().equals(globalMethodName)) {
                        globalMethodsWithName.add(jsMethod);
                    }
                }
                return globalMethodsWithName;
            }
        }
        return Collections.emptyList();
    }

    protected List<JsMethod> getSupportedGlobalMethods(IInfoObject infoObject) {
        List<JsClassDefinitionReader> classDefinitionReaders =
                getClassDefinitionReader(infoObject);
        if (classDefinitionReaders != null
                && !classDefinitionReaders.isEmpty()) {
            List<JsMethod> allSupportedGlobalMethods =
                    new ArrayList<JsMethod>();
            for (JsClassDefinitionReader jsClassDefinitionReader : classDefinitionReaders) {
                if (jsClassDefinitionReader instanceof IGlobalDataDefinitionReader) {
                    IGlobalDataDefinitionReader globalDataDefinitionReader =
                            (IGlobalDataDefinitionReader) jsClassDefinitionReader;
                    List<JsMethod> supportedMethods = globalDataDefinitionReader
                            .getSupportedGlobalMethods();
                    if (supportedMethods != null
                            && !supportedMethods.isEmpty()) {
                        allSupportedGlobalMethods.addAll(supportedMethods);
                    }
                }
            }
            return allSupportedGlobalMethods;
        }
        return Collections.emptyList();
    }

    protected List<JsAttribute> getSupportedGlobalProperties(
            IInfoObject infoObject) {
        List<JsClassDefinitionReader> classDefinitionReaders =
                getClassDefinitionReader(infoObject);
        if (classDefinitionReaders != null
                && !classDefinitionReaders.isEmpty()) {
            List<JsAttribute> allSupportedGlobalProperties =
                    new ArrayList<JsAttribute>();
            for (JsClassDefinitionReader jsClassDefinitionReader : classDefinitionReaders) {
                if (jsClassDefinitionReader instanceof IGlobalDataDefinitionReader) {
                    IGlobalDataDefinitionReader globalDataDefinitionReader =
                            (IGlobalDataDefinitionReader) jsClassDefinitionReader;
                    List<JsAttribute> supportedProperties =
                            globalDataDefinitionReader
                                    .getSupportedGlobalProperties();
                    if (supportedProperties != null
                            && !supportedProperties.isEmpty()) {
                        allSupportedGlobalProperties
                                .addAll(supportedProperties);
                    }
                }
            }
            return allSupportedGlobalProperties;
        }
        return Collections.emptyList();
    }

    protected List<JsClassDefinitionReader> getClassDefinitionReader(
            IInfoObject infoObject) {
        List<JsClassDefinitionReader> classDefinitionReader =
                Collections.EMPTY_LIST;
        if (infoObject != null) {
            classDefinitionReader = infoObject.getClassDefinitionReaders();
        }
        if (classDefinitionReader.isEmpty()) {
            throw new IllegalStateException(Messages.Utility_8);
        }
        return classDefinitionReader;
    }

    /**
     * Override this method if a filter to the supported script relevant data
     * classes is to be made, by default all the scriptRelevantDataMap is
     * returned
     * 
     * @return Map<String, IScriptRelevantData> all the supported script
     *         relevant data classes
     */
    protected Map<String, IScriptRelevantData> getSupportedScriptRelevantDataMap(
            IInfoObject infoObject) {
        Map<String, IScriptRelevantData> supportedScriptRelevantDataMap =
                new HashMap<String, IScriptRelevantData>();
        ISymbolTable symbolTable = getSymbolTable(infoObject);
        if (symbolTable != null) {
            Map<String, IScriptRelevantData> scriptRelevantDataMap =
                    symbolTable.getScriptRelevantDataTypeMap();

            supportedScriptRelevantDataMap.putAll(scriptRelevantDataMap);
        }
        return supportedScriptRelevantDataMap;
    }

    protected ISymbolTable getSymbolTable(IInfoObject infoObject) {
        if (infoObject != null && infoObject.getScriptParser() != null
                && infoObject.getScriptParser()
                        .getScriptParser() instanceof JScriptParser) {
            return ((JScriptParser) infoObject.getScriptParser()
                    .getScriptParser()).getSymbolTable();
        }
        return null;
    }

    protected String getScriptType(IInfoObject infoObject) {
        if (infoObject != null) {
            return infoObject.getScriptType();
        }
        return null;
    }

    protected EObject getInput(IInfoObject infoObject) {
        if (infoObject != null && infoObject.getScriptParser() != null
                && infoObject.getScriptParser()
                        .getScriptParser() instanceof JScriptParser) {
            ISymbolTable symbolTable = ((JScriptParser) infoObject
                    .getScriptParser().getScriptParser()).getSymbolTable();
            if (symbolTable != null) {
                return symbolTable.getInput();
            }
        }
        return null;
    }

    protected IVarNameResolver getVarNameResolver(IInfoObject infoObject) {
        if (infoObject != null) {
            return infoObject.getVarNameResolver();
        }
        return null;
    }

    protected IDataTypeMapper getDataTypeMapper(IInfoObject infoObject) {
        if (infoObject != null) {
            return infoObject.getDataTypeMapper();
        }
        return null;
    }

    public List<String> getSymbolTableKeyWords(IInfoObject infoObject) {
        IDataTypeMapper dataTypeMapper = getDataTypeMapper(infoObject);
        if (dataTypeMapper != null) {
            return dataTypeMapper.getSymbolTableKeyWords();
        }
        return null;
    }

    public List<String> getSymbolTableFutureKeyWords(IInfoObject infoObject) {
        IDataTypeMapper dataTypeMapper = getDataTypeMapper(infoObject);
        if (dataTypeMapper != null) {
            return dataTypeMapper.getSymbolTableFutureKeyWords();
        }
        return null;
    }

    protected Map<String, IScriptRelevantData> getLocalVariablesMap(
            IInfoObject infoObject) {
        Map<String, IScriptRelevantData> localVariablesMap =
                new HashMap<String, IScriptRelevantData>();
        ISymbolTable symbolTable = getSymbolTable(infoObject);
        if (symbolTable != null) {
            Map<String, IScriptRelevantData> tempLocalVariables =
                    symbolTable.getLocalVariableMap();
            if (tempLocalVariables != null) {
                return tempLocalVariables;
            }
        }
        return localVariablesMap;
    }

    public IScriptRelevantDataFactory getScriptRelevantDataFactory(
            IInfoObject infoObject) {
        if (infoObject != null
                && infoObject.getScriptRelevantDataFactory() != null) {
            return infoObject.getScriptRelevantDataFactory();
        }
        return null;
    }

    protected Map<String, IScriptRelevantData> getLocalMethodsMap(
            IInfoObject infoObject) {
        Map<String, IScriptRelevantData> localMethodsMap =
                new HashMap<String, IScriptRelevantData>();
        ISymbolTable symbolTable = getSymbolTable(infoObject);
        if (symbolTable != null) {
            Map<String, IScriptRelevantData> tempLocalMethods =
                    symbolTable.getLocalMethodMap();
            if (tempLocalMethods != null) {
                return tempLocalMethods;
            }
        }
        return localMethodsMap;
    }

    public void logUnexpectedExpressionValidatorProblem() {
        List<String> additionalAttributes = new ArrayList<String>();
        additionalAttributes.add(getClass().getName());
        String text =
                String.format(Messages.ExpressionValidator_ProblemProcessingAST,
                        additionalAttributes.toArray());
        LOG.error(text);
    }

    protected List<JsMethod> getMethodsWithName(
            List<IScriptRelevantData> contextTypes, String methodName) {
        if (contextTypes != null && !contextTypes.isEmpty()
                && methodName != null) {
            List<JsMethod> addMethodsWithName = new ArrayList<JsMethod>();
            for (IScriptRelevantData context : contextTypes) {
                if (context instanceof IUMLScriptRelevantData) {
                    JsClass jsClass =
                            ((IUMLScriptRelevantData) context).getJsClass();
                    if (jsClass != null) {
                        addMethodsWithName
                                .addAll(jsClass.getMethodList(methodName));
                    }
                }
            }
            return addMethodsWithName;
        }
        return Collections.emptyList();
    }

    protected List<JsMethod> getMethodsWithParamNumber(
            List<JsMethod> methodList, int parametersNumber) {
        if (methodList != null) {
            List<JsMethod> matchingParamsMethods = new ArrayList<JsMethod>();
            for (JsMethod jsMethod : methodList) {
                if (JScriptUtils.hasRepeatingInputParameters(jsMethod)) {
                    int upperMaxRepeatingInputParameters = JScriptUtils
                            .getUpperMaxRepeatingInputParameters(jsMethod);
                    int lowerRepeatingInputParameters = JScriptUtils
                            .getLowerRepeatingInputParameters(jsMethod);
                    boolean validUpperNumber = false;
                    boolean validLowerNumber = false;
                    if (upperMaxRepeatingInputParameters == -1
                            || upperMaxRepeatingInputParameters >= parametersNumber) {
                        validUpperNumber = true;
                    }
                    if (lowerRepeatingInputParameters <= parametersNumber) {
                        validLowerNumber = true;
                    }
                    if (validUpperNumber && validLowerNumber) {
                        matchingParamsMethods.add(jsMethod);
                    }
                } else {
                    int methodParamNumber = 0;
                    List<JsMethodParam> methodParams =
                            jsMethod.getParameterType();
                    if (methodParams != null) {
                        methodParamNumber = methodParams.size();
                    }
                    if (methodParamNumber == parametersNumber) {
                        matchingParamsMethods.add(jsMethod);
                    }
                }
            }
            return matchingParamsMethods;
        }
        return Collections.emptyList();
    }

    protected List<JsMethod> getMethodsWithMatchingParamTypes(
            List<JsMethod> methodList,
            Map<String, IScriptRelevantData> parameters,
            IScriptRelevantData contextType) {
        if (methodList != null && parameters != null) {
            List<JsMethod> compatibleMethods = new ArrayList<JsMethod>();
            // We have to match parameter types,
            for (int i = 0; i < methodList.size(); i++) {
                JsMethod matchingParamMethod = methodList.get(i);
                if (matchingParamMethod != null) {
                    if (JScriptUtils
                            .hasRepeatingInputParameters(matchingParamMethod)) {
                        List<JsMethodParam> parameterType =
                                matchingParamMethod.getParameterType();
                        if (parameterType != null && !parameterType.isEmpty()) {
                            // Get the first param
                            JsMethodParam firstParam =
                                    parameterType.iterator().next();
                            int paramsSize = parameters.size();
                            if (paramsSize != 0) {
                                List<JsMethodParam> dynamicParams =
                                        new ArrayList<JsMethodParam>();
                                for (int j = 0; j < paramsSize; j++) {
                                    dynamicParams.add(firstParam);
                                }
                                if (isMatchingParamMethod(dynamicParams,
                                        parameters,
                                        contextType)) {
                                    compatibleMethods.add(matchingParamMethod);
                                }
                            }
                        }
                    } else {
                        if (matchingParamMethod != null
                                && matchingParamMethod
                                        .getParameterType() != null
                                && matchingParamMethod.getParameterType()
                                        .size() == parameters.size()) {
                            if (isMatchingParamMethod(
                                    matchingParamMethod.getParameterType(),
                                    parameters,
                                    contextType)) {
                                compatibleMethods.add(matchingParamMethod);
                            }
                        }
                    }
                }
            }
            return compatibleMethods;
        }
        return Collections.emptyList();
    }

    @SuppressWarnings("restriction")
    private boolean isMatchingParamMethod(List<JsMethodParam> methodParams,
            Map<String, IScriptRelevantData> parameters,
            IScriptRelevantData genericContext) {
        Set<String> keySet = parameters.keySet();
        Iterator<String> iterator = keySet.iterator();
        int counter = 0;
        while (iterator.hasNext()) {

            IScriptRelevantData rhsDataType = null;
            IScriptRelevantData lhsDataType = null;
            String key = iterator.next();

            JsMethodParam jsMethodParam = methodParams.get(counter);

            if (key != null) {

                rhsDataType = parameters.get(key);

                if (rhsDataType != null && rhsDataType.getType() != null) {

                    if (rhsDataType instanceof ITypeResolution && JScriptUtils
                            .isGenericType(rhsDataType.getType())) {

                        IScriptRelevantData rhsContextType =
                                ((ITypeResolution) rhsDataType)
                                        .getGenericContextType();
                        List<IScriptRelevantData> resolveType =
                                resolveType(rhsDataType, false, rhsContextType);
                        if (resolveType != null && !resolveType.isEmpty()) {

                            rhsDataType = resolveType.iterator().next();
                        }
                    }

                    if (jsMethodParam != null) {
                        String dataType = JScriptUtils
                                .getJsMethodParamBaseDataType(jsMethodParam);
                        JScriptGenericsService gs = new JScriptGenericsService();
                        if (gs.isGeneric(jsMethodParam)) {
                            Map<String, Type> typeMap = gs.createTypeMap(genericContext, jsMethodParam);
                            Type type = typeMap.get(dataType);
                            if (type != null) {
                                if (type instanceof PrimitiveType) {
                                    dataType = type.getName();
                                } else {
                                    dataType = type.getQualifiedName().replace("::", "."); //$NON-NLS-1$ //$NON-NLS-2$
                                }
                            } else {
                                dataType = JsConsts.UNDEFINED_DATA_TYPE;
                            }
                        }

                        /*
                         * this generic context must be checked for UnionSRD
                         */
                        if (genericContext instanceof IUnionScriptRelevantData) {

                            /*
                             * get the matching srd for lhs from the union
                             * member types that corresponds to rhs
                             */
                            lhsDataType =
                                    getMatchingSRDFromUnion(genericContext,
                                            jsMethodParam,
                                            rhsDataType);

                        } else if (jsMethodParam instanceof CaseJsMethodParam) {

                            if (jsMethodParam.getScriptRelevantData() != null) {

                                lhsDataType =
                                        jsMethodParam.getScriptRelevantData();
                            } else {

                                lhsDataType = createUMLScriptRelevantData(
                                        jsMethodParam.getName(),
                                        jsMethodParam.canRepeat(),
                                        ((CaseJsMethodParam) jsMethodParam)
                                                .getUmlClass(),
                                        genericContext,
                                        jsMethodParam);
                            }
                        } else {

                            lhsDataType = createScriptRelevantData(
                                    jsMethodParam.getName(),
                                    dataType,
                                    jsMethodParam.canRepeat(),
                                    genericContext,
                                    jsMethodParam);
                        }
                        if (JScriptUtils
                                .isGenericType(jsMethodParam.getType())) {
                            List<IScriptRelevantData> resolveType =
                                    resolveType(lhsDataType,
                                            false,
                                            genericContext);
                            if (resolveType != null && !resolveType.isEmpty()) {
                                boolean isArray = lhsDataType.isArray();

                                lhsDataType = resolveType.iterator().next();

                                /*
                                 * Sid XPD-5920: Make sure the multiplicity of
                                 * generic method params is preserved thru type
                                 * resolotuib (p.s. the isMultiple param on
                                 * resolveType doesn't work
                                 */
                                lhsDataType.setIsArray(isArray);

                            }
                        }
                    }
                }
            }
            if (lhsDataType != null && rhsDataType != null) {
                /*
                 * Set the parameter coercion criteria (this tells
                 * isValidAssignment() whether the intention of the parameter is
                 * to allow subType and/or superType compatibility of the
                 * parameter definition and the provided parameter value.
                 */
                ParameterCoercionCriteria paramCoercionCriteria =
                        getParamCoercionCriteria(jsMethodParam, lhsDataType);

                if (paramCoercionCriteria != null) {

                    ((DefaultScriptRelevantData) lhsDataType)
                            .setParamCoercionCriteria(paramCoercionCriteria);
                }

                if (JsConsts.UNDEFINED_DATA_TYPE.equals(lhsDataType.getType())
                        || JsConsts.UNDEFINED_DATA_TYPE
                                .equals(rhsDataType.getType())
                        || isValidAssignment(lhsDataType, rhsDataType)) {

                    counter++;
                    continue;
                } else {

                    return false;
                }
            } else {

                return false;
            }
        }
        return true;
    }

    /**
     * Get the parameter coercion criteria (this tells isValidaAssignment()
     * whether the intention of the parameter is to allow subType and/or
     * superType compatibility of the parameter definition and the provided
     * parameter value.
     * 
     * @param jsMethodParam
     * @param dataType
     * @return The allowed coercion type or <code>null</code> if not explicitly
     *         set in parameter.
     */
    protected ParameterCoercionCriteria getParamCoercionCriteria(
            JsMethodParam jsMethodParam, IScriptRelevantData dataType) {

        if (jsMethodParam instanceof BaseJsMethodParam) {

            BaseJsMethodParam baseJsMethodParam =
                    (BaseJsMethodParam) jsMethodParam;
            return baseJsMethodParam.getParamCoercionCriteria();
        }

        return null;
    }

    /**
     * @param genericContext
     * @param jsMethodParam
     * @param rhsDataType
     * @return
     */
    private IScriptRelevantData getMatchingSRDFromUnion(
            IScriptRelevantData genericContext, JsMethodParam jsMethodParam,
            IScriptRelevantData rhsDataType) {

        IScriptRelevantData lhsDataType = null;

        if (genericContext instanceof IUnionScriptRelevantData) {
            IUnionScriptRelevantData iUnionScriptRelevantData =
                    (IUnionScriptRelevantData) genericContext;
            List<IScriptRelevantData> srdList =
                    iUnionScriptRelevantData.getSrdList();

            if (null != srdList) {
                for (IScriptRelevantData iScriptRelevantData : srdList) {
                    lhsDataType = iScriptRelevantData;
                    boolean validAssignment =
                            isValidAssignment(lhsDataType, rhsDataType);
                    if (validAssignment) {
                        return lhsDataType;
                    }
                }
            }

        }

        return lhsDataType;
    }

    @SuppressWarnings("restriction")
    protected IScriptRelevantData getMatchingPropertyType(
            IUMLScriptRelevantData context, String propertyName,
            boolean isStaticReference) {
        String dataType = null;
        boolean isMultiple = false;
        boolean isStaticProperty = false;
        boolean isReadOnly = false;
        boolean isContextReadOnly = false;
        Object extendedInfo = null;
        if (context instanceof ITypeResolution) {
            isContextReadOnly = ((ITypeResolution) context).isReadOnly();
        }
        IScriptRelevantData matchingProperty = null;
        IScriptRelevantData genericContext =
                JScriptUtils.getCurrentGenericContext(context);
        if (context != null && propertyName != null) {
            JsClass jsClass = context.getJsClass();
            if (jsClass != null) {

                List<JsAttribute> attributeList = jsClass.getAttributeList();
                // Check if it is an attribute
                if (attributeList != null) {
                    for (JsAttribute jsAttribute : attributeList) {
                        if (jsAttribute != null && jsAttribute.getName() != null
                                && jsAttribute.getName().equals(propertyName)) {
                            extendedInfo = jsAttribute;
                            if (jsAttribute instanceof IJsElementExt) {
                                isStaticProperty = ((IJsElementExt) jsAttribute)
                                        .isStatic();
                                isReadOnly = ((IJsElementExt) jsAttribute)
                                        .isReadOnly();
                            }
                            if (jsAttribute instanceof JsEnumerationLiteral) {
                                isReadOnly = true;
                                List<IScriptRelevantData> resolveType =
                                        resolveType(jsAttribute,
                                                false,
                                                context);
                                if (resolveType != null
                                        && !resolveType.isEmpty()) {
                                    matchingProperty =
                                            resolveType.iterator().next();
                                } else {
                                    JsEnumerationLiteral jsEnumerationLiteral =
                                            (JsEnumerationLiteral) jsAttribute;
                                    if (jsEnumerationLiteral
                                            .getOwner() != null) {
                                        JsEnumeration jsEnumeration =
                                                new DefaultJsEnumeration(
                                                        jsEnumerationLiteral
                                                                .getOwner());
                                        matchingProperty =
                                                createUMLScriptRelevantData(
                                                        jsEnumerationLiteral
                                                                .getName(),
                                                        false,
                                                        jsEnumeration,
                                                        genericContext,
                                                        extendedInfo);
                                    }
                                }
                            } else if (JScriptUtils.isUnion(jsAttribute)
                                    && jsAttribute.isMultiple()) {
                                /*
                                 * Check that jsAttribute is a Union
                                 * 
                                 * Create a union Script Relevant Data
                                 */
                                isMultiple = jsAttribute.isMultiple();
                                matchingProperty =
                                        createUnionScriptRelevantData(
                                                propertyName,
                                                propertyName,
                                                jsAttribute.isMultiple(),
                                                context,
                                                jsAttribute);

                            } else {
                                dataType = getAttributeDataType(jsAttribute);
                                isMultiple =
                                        JScriptUtils.isMultiple(jsAttribute,
                                                jsClass.getUmlClass());
                            }
                            break;
                        }
                    }
                }
                if (dataType == null && matchingProperty == null) {
                    // Check if it is a reference
                    List<JsReference> referenceList =
                            jsClass.getReferenceList();
                    // Check if it is an attribute
                    if (referenceList != null) {
                        for (JsReference jsReference : referenceList) {
                            if (jsReference != null
                                    && jsReference.getName() != null
                                    && jsReference.getName()
                                            .equals(propertyName)) {
                                extendedInfo = jsReference;
                                isMultiple = jsReference.isMultiple();
                                dataType =
                                        JScriptUtils.getJsReferenceBaseDataType(
                                                jsReference);
                                if (jsReference instanceof IJsElementExt) {
                                    isStaticProperty =
                                            ((IJsElementExt) jsReference)
                                                    .isStatic();
                                    isReadOnly = ((IJsElementExt) jsReference)
                                            .isReadOnly();
                                }
                                JsClass referencedJsClass =
                                        getJsClass(context, jsReference);
                                if (referencedJsClass != null) {
                                    matchingProperty =
                                            createUMLScriptRelevantData(
                                                    propertyName,
                                                    jsReference.isMultiple(),
                                                    referencedJsClass,
                                                    genericContext,
                                                    extendedInfo);
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (isStaticReference && !isStaticProperty) {
            return null;
        }
        if (dataType != null && matchingProperty == null) {
            matchingProperty = createScriptRelevantData(dataType,
                    dataType,
                    isMultiple,
                    genericContext,
                    isReadOnly,
                    extendedInfo);
        }
        if (matchingProperty != null && matchingProperty.isArray()) {
            isReadOnly = true;
        }
        if (matchingProperty instanceof ITypeResolution) {
            if (isContextReadOnly) {
                isReadOnly = isContextReadOnly;
            }
            ((ITypeResolution) matchingProperty).setReadOnly(isReadOnly);
            matchingProperty.setIsArray(isMultiple);
        }
        return matchingProperty;
    }

    /**
     * Get the JsClass for a script relevant data.
     * 
     * @param context
     * @param jsReference
     * @return
     */
    protected JsClass getJsClass(IUMLScriptRelevantData context,
            JsReference jsReference) {
        return jsReference.getReferencedJsClass();
    }

    protected String getAttributeDataType(JsAttribute jsAttribute) {
        return JScriptUtils.getJsAttributeBaseDataType(jsAttribute);
    }

    protected IScriptRelevantData createUnionScriptRelevantData(String name,
            String type, boolean isArray, IScriptRelevantData genericContext,
            Object extendedInfo) {

        if (extendedInfo instanceof JsAttribute) {

            List<IScriptRelevantData> srdList =
                    new ArrayList<IScriptRelevantData>();

            JsAttribute jsAttribute = (JsAttribute) extendedInfo;
            DataType unionDataType = JScriptUtils.getUnionDataType(jsAttribute);

            List<DataType> unionMemberTypes =
                    JScriptUtils.getUnionMemberTypes(unionDataType);

            /*
             * iterate thru union member types and for each member type create a
             * script relevant data and add it to the list of SRD. and at the
             * end create a default union SRD passing this list of SRD
             */
            for (DataType dataType : unionMemberTypes) {

                String dataTypeName = null;
                IScriptRelevantData scriptRelevantData = null;

                if (dataType instanceof PrimitiveType) {

                    String basePrimitiveDataType = JScriptUtils
                            .getBasePrimitiveDataType((PrimitiveType) dataType);
                    if (null != basePrimitiveDataType) {
                        dataTypeName = basePrimitiveDataType;
                    }
                    if (null == dataTypeName) {
                        dataTypeName = dataType.getName();
                    }

                    scriptRelevantData = createScriptRelevantData(dataTypeName,
                            dataTypeName,
                            false,
                            genericContext,
                            dataType);
                } else if (dataType instanceof Enumeration) {
                    Enumeration umlEnumeration = (Enumeration) dataType;

                    DefaultJsEnumeration jsEnumeration =
                            new DefaultJsEnumeration(umlEnumeration);

                    jsEnumeration.setContentAssistIconProvider(
                            JScriptUtils.getJsContentAssistIconProvider());

                    scriptRelevantData =
                            createUMLScriptRelevantData(dataType.getName(),
                                    false,
                                    jsEnumeration,
                                    genericContext,
                                    true,
                                    dataType);
                }

                if (null != scriptRelevantData) {
                    srdList.add(scriptRelevantData);
                }
            }

            if (srdList.size() > 0) {
                IScriptRelevantData scriptRelevantData =
                        createScriptRelevantData(name,
                                type,
                                isArray,
                                genericContext,
                                jsAttribute);
                if (scriptRelevantData instanceof IUnionScriptRelevantData) {
                    IUnionScriptRelevantData iUnionScriptRelevantData =
                            (IUnionScriptRelevantData) scriptRelevantData;
                    iUnionScriptRelevantData.setSrdList(srdList);
                    return iUnionScriptRelevantData;

                }
            }
        }

        return null;
    }

    protected IScriptRelevantData createScriptRelevantData(String name,
            String type, boolean isArray, IScriptRelevantData genericContext,
            boolean isReadOnly, Object extendedInfo) {
        if (getInfoObject() != null
                && getInfoObject().getScriptRelevantDataFactory() != null) {
            IScriptRelevantDataFactory scriptRelevantDataFactory =
                    getInfoObject().getScriptRelevantDataFactory();

            IScriptRelevantData scriptRelevantData =
                    scriptRelevantDataFactory.createScriptRelevantData(name,
                            type,
                            isArray,
                            genericContext,
                            isReadOnly,
                            extendedInfo);
            return scriptRelevantData;

        } else {
            List<String> additionalAttributes = new ArrayList<String>();
            additionalAttributes.add(getClass().getName());
            String text = String.format(
                    Messages.AbstractExpressionValidator_ScriptRelevantDataFactoryNotRegistered,
                    additionalAttributes.toArray());
            LOG.error(text);
        }
        return null;
    }

    protected IScriptRelevantData createScriptRelevantData(String name,
            String type, boolean isArray, IScriptRelevantData genericContext,
            Object extendedInfo) {
        return createScriptRelevantData(name,
                type,
                isArray,
                genericContext,
                false,
                extendedInfo);
    }

    protected IUMLScriptRelevantData createUMLScriptRelevantData(String name,
            boolean isArray, org.eclipse.uml2.uml.Class umlClass,
            IScriptRelevantData genericContext, Object extendedInfo) {
        return createUMLScriptRelevantData(name,
                isArray,
                umlClass,
                genericContext,
                false,
                extendedInfo);
    }

    protected IUMLScriptRelevantData createUMLScriptRelevantData(String name,
            boolean isArray, org.eclipse.uml2.uml.Class umlClass,
            IScriptRelevantData genericContext, boolean isReadOnly,
            Object extendedInfo) {
        if (getInfoObject() != null
                && getInfoObject().getScriptRelevantDataFactory() != null) {
            IScriptRelevantDataFactory scriptRelevantDataFactory =
                    getInfoObject().getScriptRelevantDataFactory();
            IUMLScriptRelevantData scriptRelevantData =
                    scriptRelevantDataFactory.createUMLScriptRelevantData(name,
                            isArray,
                            umlClass,
                            genericContext,
                            isReadOnly,
                            extendedInfo);
            return scriptRelevantData;
        } else {
            List<String> additionalAttributes = new ArrayList<String>();
            String className = null;
            if (getClass() != null && getClass().getName() != null) {
                className = getClass().getName();
            }
            additionalAttributes.add(className);
            String text = String.format(
                    Messages.AbstractExpressionValidator_ScriptRelevantDataFactoryNotRegistered,
                    additionalAttributes.toArray());
            LOG.error(text);
        }
        return null;
    }

    protected IUMLScriptRelevantData createUMLScriptRelevantData(String name,
            boolean isArray, JsClass jsClass,
            IScriptRelevantData genericContext, Object extendedInfo) {
        return createUMLScriptRelevantData(name,
                isArray,
                jsClass,
                genericContext,
                false,
                extendedInfo);
    }

    protected IUMLScriptRelevantData createUMLScriptRelevantData(String name,
            boolean isArray, JsClass jsClass,
            IScriptRelevantData genericContext, boolean isReadOnly,
            Object extendedInfo) {
        if (getInfoObject() != null
                && getInfoObject().getScriptRelevantDataFactory() != null) {
            IScriptRelevantDataFactory scriptRelevantDataFactory =
                    getInfoObject().getScriptRelevantDataFactory();
            String type = null;
            if (jsClass != null) {
                type = jsClass.getName();
            }
            IUMLScriptRelevantData scriptRelevantData =
                    scriptRelevantDataFactory.createUMLScriptRelevantData(name,
                            type,
                            isArray,
                            jsClass,
                            genericContext,
                            isReadOnly,
                            extendedInfo);
            return scriptRelevantData;
        } else {
            List<String> additionalAttributes = new ArrayList<String>();
            String className = null;
            if (getClass() != null && getClass().getName() != null) {
                className = getClass().getName();
            }
            additionalAttributes.add(className);
            String text = String.format(
                    Messages.AbstractExpressionValidator_ScriptRelevantDataFactoryNotRegistered,
                    additionalAttributes.toArray());
            LOG.error(text);
        }
        return null;
    }

    protected List<IScriptRelevantData> getResolutionTypes(
            IScriptRelevantData type) {
        if (type instanceof ITypeResolution) {
            return ((ITypeResolution) type).getTypesResolution();
        }
        return Collections.singletonList(type);
    }

    public List<IScriptRelevantData> resolveType(IScriptRelevantData type,
            boolean isMultiple, IScriptRelevantData genericContext) {
        return JScriptUtils.resolveType(type,
                getTypeResolvers(getInfoObject()),
                isMultiple,
                genericContext);
    }

    public List<IScriptRelevantData> resolveType(JsAttribute jsAttribute,
            boolean isMultiple, IScriptRelevantData genericContext) {
        return JScriptUtils.resolveType(jsAttribute,
                getTypeResolvers(getInfoObject()),
                isMultiple,
                genericContext);
    }

    public void addResolutionTypes(IScriptRelevantData type, boolean isMultiple,
            IScriptRelevantData genericContext) {
        if (type instanceof ITypeResolution) {
            List<IScriptRelevantData> resolveJavaScriptStringTypes =
                    resolveType(type, isMultiple, genericContext);
            if (resolveJavaScriptStringTypes != null
                    && !resolveJavaScriptStringTypes.isEmpty()) {
                ((ITypeResolution) type)
                        .setTypesResolution(resolveJavaScriptStringTypes);
            }
        }
    }

    public String convertSpecificToGenericType(
            IScriptRelevantData specificType) {
        String genericTypeStr = null;
        String specificTypeStr = null;

        if (specificType != null) {
            specificTypeStr = JScriptUtils.getFQType(specificType);
            if (specificType.isArray()) {
                List<IScriptRelevantData> resolutionTypes =
                        getResolutionTypes(specificType);
                if (resolutionTypes != null && !resolutionTypes.isEmpty()) {
                    if (resolutionTypes.size() == 1) {
                        specificType = resolutionTypes.iterator().next();
                        if (specificType instanceof DefaultUMLScriptRelevantData) {
                            DefaultUMLScriptRelevantData defaultUMLScriptRelevantData =
                                    (DefaultUMLScriptRelevantData) specificType;
                            IScriptRelevantData genericContextType =
                                    defaultUMLScriptRelevantData
                                            .getGenericContextType();
                            specificTypeStr =
                                    JScriptUtils.getFQType(genericContextType);
                            return getGenericTypeStr(specificTypeStr);

                        } else {
                            specificTypeStr =
                                    JScriptUtils.getFQType(specificType);
                        }
                    }
                }
            }
            if (JScriptUtils.isGenericType(specificTypeStr)) {
                if (specificType instanceof ITypeResolution) {
                    @SuppressWarnings("restriction")
                    IScriptRelevantData genericContextType =
                            ((ITypeResolution) specificType)
                                    .getGenericContextType();
                    if (genericContextType != null) {
                        specificType = genericContextType;
                        specificTypeStr =
                                JScriptUtils.getFQType(genericContextType);
                    }
                }
            } else {
                /* XPD-2098 (AMXBPM Studio 3.5.2 V13) onwards */
                /*
                 * to a list, items specific to that list type must only be
                 * allowed to be added. eg.dateList.add(date) is permitted but
                 * dateList.add(time) must not be permitted.
                 */
                if (specificType instanceof IUMLScriptRelevantData) {
                    IUMLScriptRelevantData defaultUMLScriptRelevantData =
                            (IUMLScriptRelevantData) specificType;
                    specificTypeStr = JScriptUtils
                            .getFQType(defaultUMLScriptRelevantData);
                    /*
                     * XPD-2338: to a list if a sub list is added find the
                     * specific type of the sub list. for eg.
                     * cust2.items.addAll(cust1.items.subList(1, 5));
                     */
                    if (JsConsts.ARRAY.equals(specificTypeStr)) {

                        if (defaultUMLScriptRelevantData instanceof DefaultUMLScriptRelevantData) {
                            DefaultUMLScriptRelevantData defaultUMLScriptRelevantData2 =
                                    (DefaultUMLScriptRelevantData) defaultUMLScriptRelevantData;
                            IScriptRelevantData genericContextType =
                                    defaultUMLScriptRelevantData2
                                            .getGenericContextType();
                            specificTypeStr =
                                    JScriptUtils.getFQType(genericContextType);
                        }
                    } else if (JsConsts.XML_GREGORIAN_CALENDAR
                            .equals(specificTypeStr)) {
                        if (defaultUMLScriptRelevantData instanceof DefaultUMLScriptRelevantData) {
                            DefaultUMLScriptRelevantData defaultUMLScriptRelevantData2 =
                                    (DefaultUMLScriptRelevantData) defaultUMLScriptRelevantData;
                            Object extendedInfo = defaultUMLScriptRelevantData2
                                    .getExtendedInfo();
                            if (extendedInfo instanceof DefaultJsAttribute) {
                                DefaultJsAttribute defaultJsAttribute =
                                        (DefaultJsAttribute) extendedInfo;
                                Element element =
                                        defaultJsAttribute.getElement();
                                if (element instanceof Property) {
                                    Property property = (Property) element;
                                    if (!JsConsts.BOM_DATE.equalsIgnoreCase(
                                            defaultUMLScriptRelevantData2
                                                    .getName())
                                            && !defaultUMLScriptRelevantData2
                                                    .getName()
                                                    .equalsIgnoreCase(property
                                                            .getType()
                                                            .getName())) {
                                        return defaultUMLScriptRelevantData2
                                                .getName();
                                    }
                                    specificTypeStr =
                                            property.getType().getName();
                                }
                            }
                        }
                    }

                    if (null == specificTypeStr) {
                        IScriptRelevantData genericContextType =
                                ((DefaultScriptRelevantData) defaultUMLScriptRelevantData)
                                        .getGenericContextType();
                        specificTypeStr =
                                JScriptUtils.getFQType(genericContextType);
                    }
                    /*
                     * You should not be able to add a list of dates to a list
                     * of integers for example dateList.addAll(intList) is not
                     * allowed
                     */
                } else if (specificType instanceof IScriptRelevantData) {
                    IScriptRelevantData defaultScriptRelevantData =
                            specificType;
                    if (defaultScriptRelevantData instanceof ITypeResolution) {
                        if (JsConsts.OBJECT.equals(specificTypeStr)) {
                            /**
                             * Sid XPD-5920 - The following piece of code (I
                             * think) effectively was treating "Object" types to
                             * the parent class by coercing them to such. BUT
                             * THAT IS WHAT THE "E" type is supposed to be for.
                             * 
                             * If we coerce the Object type then how do we ever
                             * specify 'any type' parameters on method.
                             * 
                             * I discovered that the Object type parameters in
                             * Criteria.setQueryParameter() method would get
                             * coerced to "Criteria" UML class type - which is
                             * PANTS! So removed this coercion.
                             * 
                             * IF you're tempted to undo this change then you;ll
                             * need to find a viable alternative for
                             * Criteria.setQueryParameter().
                             * 
                             * But there may be other things relying on this
                             * coercion, my 'guess' is that these should be
                             * handled as "E" type rather than "Object" type.,
                             */
                            // specificTypeStr =
                            // JScriptUtils
                            // .getFQType(defaultScriptRelevantData);
                            //
                            // if (null == specificTypeStr
                            // || JsConsts.OBJECT.equals(specificTypeStr)) {
                            // @SuppressWarnings("restriction")
                            // IScriptRelevantData genericContextType =
                            // ((ITypeResolution) defaultScriptRelevantData)
                            // .getGenericContextType();
                            // specificTypeStr =
                            // JScriptUtils
                            // .getFQType(genericContextType);
                            //
                            // }
                        }
                    }
                }
            }
            genericTypeStr = getGenericTypeStr(specificTypeStr);
            if (null == genericTypeStr) {
                genericTypeStr = specificType.getType();
            }
        }
        return genericTypeStr;
    }

    /**
     * @param genericTypeStr
     * @param specificTypeStr
     * @return
     */
    private String getGenericTypeStr(String specificTypeStr) {
        String genericTypeStr = specificTypeStr;
        IDataTypeMapper dataTypeMapper = getDataTypeMapper(getInfoObject());
        if (dataTypeMapper instanceof IJScriptDataTypeMapper) {
            IJScriptDataTypeMapper jsDataTypeMapper =
                    (IJScriptDataTypeMapper) dataTypeMapper;
            Map<String, String> specificToGenericTypeConversionMap =
                    jsDataTypeMapper.getSpecificToGenericTypeConversionMap();
            if (specificToGenericTypeConversionMap != null) {
                String genericCandidate =
                        specificToGenericTypeConversionMap.get(specificTypeStr);
                if (genericCandidate != null) {
                    genericTypeStr = genericCandidate;
                }
            }
        }
        return genericTypeStr;
    }

    public String getDestinationName(IInfoObject infoObject) {
        if (infoObject != null) {
            return infoObject.getDestinationName();
        }
        return null;
    }

    @SuppressWarnings("restriction")
    protected boolean isValidAssignment(IScriptRelevantData lhsDataType,
            IScriptRelevantData rhsDataType) {
        if (lhsDataType != null && lhsDataType.getType() != null
                && rhsDataType != null && rhsDataType.getType() != null) {
            if (isExplicitAssignmentRestriction(lhsDataType, rhsDataType)) {
                return false;
            }
            if (isExplicitAssignmentAllowance(lhsDataType, rhsDataType)) {
                return true;
            }
            String lhsTypeStr = convertSpecificToGenericType(lhsDataType);
            String rhsTypeStr = convertSpecificToGenericType(rhsDataType);
            Map<String, Set<String>> compatibleAssignmentOperatorTypesMap =
                    null;
            IDataTypeMapper dataTypeMapper = getDataTypeMapper(getInfoObject());
            if (dataTypeMapper instanceof IJScriptDataTypeMapper) {
                IJScriptDataTypeMapper jsDataTypeMapper =
                        (IJScriptDataTypeMapper) dataTypeMapper;
                compatibleAssignmentOperatorTypesMap =
                        jsDataTypeMapper.getCompatibleAssignmentTypesMap();
                compatibleAssignmentOperatorTypesMap =
                        jsDataTypeMapper.convertSpecificMapToGeneric(
                                compatibleAssignmentOperatorTypesMap);
            }
            if (compatibleAssignmentOperatorTypesMap != null) {
                Set<String> compatibleEqualityOperatorSet =
                        compatibleAssignmentOperatorTypesMap.get(lhsTypeStr);
                if (compatibleEqualityOperatorSet != null
                        && compatibleEqualityOperatorSet.contains(rhsTypeStr)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean isExplicitAssignmentRestriction(
            IScriptRelevantData lhsDataType, IScriptRelevantData rhsDataType) {
        if (!haveSameMultiplicity(lhsDataType, rhsDataType)) {
            return true;
        }
        String lhsTypeStr = convertSpecificToGenericType(lhsDataType);
        String rhsTypeStr = convertSpecificToGenericType(rhsDataType);
        // Assignment integer = String;
        if (JsConsts.INTEGER.equals(lhsTypeStr)
                || JsConsts.DECIMAL.equals(lhsTypeStr)) {
            if (JsConsts.TEXT.equals(rhsTypeStr)) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("restriction")
    protected boolean isExplicitAssignmentAllowance(
            IScriptRelevantData lhsDataType, IScriptRelevantData rhsDataType) {
        String lhsTypeStr = convertSpecificToGenericType(lhsDataType);
        String rhsTypeStr = convertSpecificToGenericType(rhsDataType);
        if (lhsTypeStr.equals(rhsTypeStr)) {
            return true;
        }
        if (JsConsts.NULL.equals(rhsTypeStr)) {
            return true;
        }
        if (JsConsts.OBJECT.equals(lhsTypeStr)
                || JsConsts.OBJECT.equals(rhsTypeStr)) {
            return true;
        }
        if (JsConsts.ARRAY.equals(lhsTypeStr) && rhsDataType.isArray()) {
            return true;
        }
        if (JsConsts.XML_GREGORIAN_CALENDAR.equals(lhsTypeStr)
                && isXMLGregorianCalendarType(rhsTypeStr)) {
            return true;
        }
        if (JScriptUtils.isSubType(lhsDataType, rhsDataType)
                || JScriptUtils.isSubType(rhsDataType, lhsDataType)) {
            return true;
        }
        // TODO Remove when we implement XPD-2035 fully
        if (JScriptUtils.isDynamicComplexType(lhsDataType)
                && JScriptUtils.isEObjectType(rhsDataType)) {
            return true;
        }
        // End remove

        if (JScriptUtils.isEObjectType(lhsDataType)
                && JScriptUtils.isDynamicComplexType(rhsDataType)) {
            return true;
        }

        return false;
    }

    protected boolean isXMLGregorianCalendarType(String type) {
        if (type != null && (type.equals(JsConsts.TIME)
                || type.equals(JsConsts.DATE) || type.equals(JsConsts.DATETIME)
                || type.equals(JsConsts.DATETIMETZ))) {
            return true;
        }
        return false;
    }

    protected boolean haveSameMultiplicity(IScriptRelevantData lhsDataType,
            IScriptRelevantData rhsDataType) {
        if (lhsDataType != null && rhsDataType != null
                && !JsConsts.UNDEFINED_DATA_TYPE.equals(lhsDataType.getType())
                && !JsConsts.UNDEFINED_DATA_TYPE
                        .equals(rhsDataType.getType())) {
            if (JScriptUtils.isXsdDerivedObject(lhsDataType)
                    || JScriptUtils.isXsdDerivedObject(rhsDataType)) {
                return true;
            }
            boolean isLhsArray = lhsDataType.isArray();
            if (JScriptUtils.isMultipleClass(lhsDataType.getType())) {
                isLhsArray = true;
            }
            boolean isRhsArray = rhsDataType.isArray();
            if (JScriptUtils.isMultipleClass(rhsDataType.getType())) {
                isRhsArray = true;
            }
            if (isLhsArray != isRhsArray) {
                return false;
            }
        }
        return true;
    }

    protected IScriptRelevantData createGenericContext(
            IScriptRelevantData genericContext, boolean isArrayContext) {
        Object extendedInfo = null;
        if (genericContext instanceof ITypeResolution) {
            extendedInfo = ((ITypeResolution) genericContext).getExtendedInfo();
        }
        if (genericContext instanceof IUMLScriptRelevantData) {
            return createUMLScriptRelevantData(genericContext.getName(),
                    isArrayContext,
                    ((IUMLScriptRelevantData) genericContext).getJsClass(),
                    null,
                    extendedInfo);
        } else if (genericContext instanceof IUnionScriptRelevantData) {
            return createUnionScriptRelevantData(genericContext.getName(),
                    genericContext.getName(),
                    isArrayContext,
                    null,
                    extendedInfo);
        } else if (genericContext instanceof IScriptRelevantData) {
            return createScriptRelevantData(genericContext.getName(),
                    genericContext.getType(),
                    isArrayContext,
                    null,
                    extendedInfo);
        }
        return null;
    }

    protected boolean isLocalVariable(String varName, IInfoObject infoObject) {
        boolean isLocalVariable = false;
        ISymbolTable symbolTable = getSymbolTable(infoObject);
        if (symbolTable != null) {
            isLocalVariable = symbolTable.isLocalVariable(varName);
        }
        return isLocalVariable;
    }

    protected String parseTypeMessage(IScriptRelevantData type) {
        String typeStr = type.getType();
        if (JScriptUtils.isGenericType(typeStr)
                || JScriptUtils.isDynamicComplexType(type,
                        getSupportedJsClasses(getInfoObject()))) {
            typeStr = convertSpecificToGenericType(type);
        } else {
            String objectSubType = getObjectSubType(type);
            if (null != objectSubType && objectSubType.length() > 0) {
                typeStr = objectSubType;
            }
        }
        // Strip the enum suffix from enum types
        if (typeStr.endsWith(DefaultJsClass.BOM_ENUM_SUFFIX)) {
            typeStr = typeStr.substring(0, typeStr.length() - DefaultJsClass.BOM_ENUM_SUFFIX.length());
        }
        return typeStr;
    }

    /**
     * @param type
     * @return
     */
    private String getObjectSubType(IScriptRelevantData type) {

        String objectSubType = null;

        if (type instanceof ITypeResolution) {
            Object extendedInfo = ((ITypeResolution) type).getExtendedInfo();
            if (extendedInfo instanceof IUMLElement) {
                Element element = ((IUMLElement) extendedInfo).getElement();
                /* this tells if the source is an object with a sub type */
                if (element instanceof Property) {
                    objectSubType =
                            JScriptUtils.getObjectSubType((Property) element);
                }
            }
        }
        return objectSubType;
    }

    /**
     * This method given an AST ELIST resolves the parameters
     * 
     * @param parametersAST
     * @param token
     * 
     * @return a map with the parameters and its types, empty map if no maps are
     *         found
     **/
    protected Map<String, IScriptRelevantData> resolveMethodParameters(
            AST parametersAST, Token token) {
        AST currentParameterExpression = parametersAST.getFirstChild();
        if (currentParameterExpression != null) {
            Map<String, IScriptRelevantData> parameters =
                    new LinkedHashMap<String, IScriptRelevantData>();
            int paramCount = 1;
            while (currentParameterExpression != null) {
                AST parameterToProcess =
                        currentParameterExpression.getFirstChild();
                if (parameterToProcess != null) {
                    IValidateResult delegateEvaluateExpression =
                            delegateEvaluateExpression(parameterToProcess,
                                    token);
                    if (delegateEvaluateExpression != null) {
                        String parameterName = "arg" + paramCount;//$NON-NLS-1$
                        if (parameterToProcess
                                .getType() == JScriptTokenTypes.STRING_LITERAL) {
                            String text = parameterToProcess.getText();
                            if (text != null && text.length() > 2) {
                                String parameterValue =
                                        text.substring(1, text.length() - 1);
                                if (parameterValue != null
                                        && parameterValue.length() > 0) {
                                    parameterName += "#" + parameterValue;//$NON-NLS-1$
                                }
                            }
                        }
                        parameters.put(parameterName,
                                delegateEvaluateExpression.getType());
                        paramCount++;
                    }
                    currentParameterExpression =
                            currentParameterExpression.getNextSibling();
                }
            }
            return parameters;
        }
        return Collections.emptyMap();
    }

    protected String getParameterNames(
            Map<String, IScriptRelevantData> parameters) {
        StringBuffer paramNames = new StringBuffer();
        Set<String> keySet = parameters.keySet();
        Iterator<String> iterator = keySet.iterator();
        paramNames.append("(");//$NON-NLS-1$
        while (iterator.hasNext()) {
            String key = iterator.next();
            if (key != null) {
                IScriptRelevantData type = parameters.get(key);
                if (type != null) {
                    String typeName = type.getType();
                    // XPD-7360: Added List type to error message.
                    if (JScriptUtils.isContextlessType(type)
                            && type instanceof ITypeResolution) {
                        IScriptRelevantData genericType =
                                ((ITypeResolution) type)
                                        .getGenericContextType();
                        typeName += "<" + genericType.getType() + ">"; //$NON-NLS-1$ //$NON-NLS-2$
                    }
                    paramNames.append(typeName);
                    if (iterator.hasNext()) {
                        paramNames.append(",");//$NON-NLS-1$
                    }
                }
            }
        }
        paramNames.append(")");//$NON-NLS-1$
        return paramNames.toString();
    }

    protected Class getUmlClass(JsMethodParam paramType) {
        return JScriptUtils.getReturnedClass(paramType);
    }

    protected List<ITypeResolver> getTypeResolvers(IInfoObject infoObject) {
        if (infoObject != null && infoObject.getTypeResolvers() != null) {
            return infoObject.getTypeResolvers();
        }
        return Collections.emptyList();
    }

    protected boolean shouldValidateGlobalFunctions(IInfoObject infoObject) {
        if (infoObject != null) {
            return infoObject.isValidateGlobalFunctions();
        }
        return false;
    }

    protected boolean isGenericContextArray(IScriptRelevantData originalType,
            IScriptRelevantData genericContextType) {
        if (originalType instanceof IUnionScriptRelevantData) {
            return originalType.isArray();
        }
        if (originalType != null && genericContextType != null
                && JScriptUtils.isGenericType(originalType.getType())
                && originalType.isArray()) {
            return true;
        }
        return false;
    }

    protected void registerReturnType(IScriptRelevantData returnType) {
        ISymbolTable symbolTable = getSymbolTable(getInfoObject());
        if (symbolTable instanceof ISymbolTableExt) {
            ((ISymbolTableExt) symbolTable).setReturnType(returnType);
        }
    }

}
