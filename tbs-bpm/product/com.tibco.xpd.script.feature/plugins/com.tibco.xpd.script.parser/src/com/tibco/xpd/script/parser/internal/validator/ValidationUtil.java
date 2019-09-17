/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.script.parser.internal.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.script.ScriptException;

import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.DefaultScriptRelevantData;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.IUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.JsDataType;
import com.tibco.xpd.script.model.client.JsExpression;
import com.tibco.xpd.script.model.jscript.JScriptUtils;
import com.tibco.xpd.script.parser.antlr.JScriptParser;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
import com.tibco.xpd.script.parser.internal.util.ExpressionUtil;
import com.tibco.xpd.script.parser.internal.validator.jscript.AbstractExpressionValidator;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptExpressionValidator;
import com.tibco.xpd.script.parser.util.ParseUtil;
import com.tibco.xpd.script.parser.util.ScriptEngineUtil;
import com.tibco.xpd.script.parser.validator.ISymbolTable;
import com.tibco.xpd.script.parser.validator.IValidationStrategy;
import com.tibco.xpd.script.parser.validator.jscript.JScriptValidationStrategy;

import antlr.Token;
import antlr.collections.AST;

public class ValidationUtil {

    public static final String SUPPRESS_SCRIPT_VALIDATION_KEY =
            "suppressScriptValidation";

    private static void validateExpr(AST exprAST, Token token,
            JScriptParser parser) {
        // List<IScriptValidationStrategy> scriptValidationStrategy =
        // ValidationUtil.getScriptValidatorStrategyList(parser);
        // for (IScriptValidationStrategy scriptValidatorStrategy :
        // scriptValidationStrategy) {
        // IExpr expr = new BaseAntlrExpr(exprAST, token);
        // scriptValidatorStrategy.validateExpression(expr);
        // }
    }

    public static void validateMethodCall(AST methodAST, Token token,
            JScriptParser parser) {
        List<IValidationStrategy> validatorStrategyList =
                ValidationUtil.getValidatorStrategyList(parser);
        for (IValidationStrategy validatorStartegy : validatorStrategyList) {
            validatorStartegy.validateMethodCall(methodAST, token);
        }
        ValidationUtil.validateExpr(methodAST, token, parser);
    }

    public static void validateNewExpression(AST expressionAST, Token token,
            JScriptParser parser) {
        List<IValidationStrategy> validatorStrategyList =
                ValidationUtil.getValidatorStrategyList(parser);
        for (IValidationStrategy validatorStartegy : validatorStrategyList) {
            validatorStartegy.validateNewExpression(expressionAST, token);
        }
        ValidationUtil.validateExpr(expressionAST, token, parser);
    }

    public static void validateConditionalExpression(AST expressionAST,
            Token token, JScriptParser parser) {
        List<IValidationStrategy> validatorStrategyList =
                ValidationUtil.getValidatorStrategyList(parser);
        for (IValidationStrategy validatorStartegy : validatorStrategyList) {
            validatorStartegy.validateConditionalExpression(expressionAST,
                    token);
        }
        ValidationUtil.validateExpr(expressionAST, token, parser);
    }

    public static void validateVariableDeclaration(JScriptParser parser,
            AST varDefAST, Token token) {
        List<IValidationStrategy> validatorStrategyList =
                getValidatorStrategyList(parser);
        for (IValidationStrategy validatorStartegy : validatorStrategyList) {
            validatorStartegy.validateVariableDeclaration(varDefAST, token);
        }
        ValidationUtil.validateExpr(varDefAST, token, parser);
    }

    public static void validateUndefinedVariableUse(JScriptParser parser,
            AST expression, Token token) {
        List<IValidationStrategy> validatorStrategyList =
                getValidatorStrategyList(parser);
        if (validatorStrategyList != null) {
            for (IValidationStrategy validatorStartegy : validatorStrategyList) {
                if (validatorStartegy != null) {
                    validatorStartegy.validateUndefinedVariableUse(expression,
                            token);
                }
            }
        }
        ValidationUtil.validateExpr(expression, token, parser);
    }

    public static List<IValidationStrategy> getValidatorStrategyList(
            JScriptParser parser) {
        List<IValidationStrategy> validatorStrategyList =
                parser.getValidatorStrategyList();
        return validatorStrategyList;
    }

    public static void addLocalVariable(AST varDefAST, JScriptParser parser,
            Token token) {
        if (parser != null && !parser.getValidatorStrategyList().isEmpty()) {
            ValidationUtil
                    .addLocalVarToParserSymbolTable(varDefAST, parser, token);
        }
    }

    public static void addLocalVariableWithoutVar(AST varDefAST,
            JScriptParser parser, Token token) {
        if (parser != null && !parser.getValidatorStrategyList().isEmpty()) {
            ValidationUtil.addLocalVarToParserSymbolTableWithoutVar(varDefAST,
                    parser,
                    token);
        }
    }

    /**
     * This method is only here because we would like to maintain a
     * comprehensive symbol table at the parser level. The symbol table stores
     * the var name and the var data type
     * 
     * @param typeSpec
     * @param variableDeclaration
     * @param variableInitialisation
     * @param parser
     * @param token
     * @param table
     */
    private static void addLocalVarToParserSymbolTable(AST varDefAST,
            JScriptParser parser, Token token) {
        AST typeAST = varDefAST.getFirstChild();
        AST identAST = typeAST.getNextSibling();
        addLocalVarToParserSymbolTableWithoutVar(identAST, parser, token);
    }

    /**
     * This method is only here because we would like to maintain a
     * comprehensive symbol table at the parser level. The symbol table stores
     * the var name and the var data type
     * 
     * @param typeSpec
     * @param variableDeclaration
     * @param variableInitialisation
     * @param parser
     * @param token
     * @param table
     */
    private static void addLocalVarToParserSymbolTableWithoutVar(AST identAST,
            JScriptParser parser, Token token) {
        AST varInitialiser = identAST.getNextSibling();
        String varName = null;
        IScriptRelevantData varType = new DefaultScriptRelevantData();
        varType.setType(JsConsts.UNDEFINED_DATA_TYPE);
        if (identAST.getType() == JScriptTokenTypes.IDENT) {
            varName = identAST.getText();
            varType.setName(varName);
        }
        if (varInitialiser != null) {
            // The varInitialiser is the expression "= blahblah"
            varType = getLocalVariableType(varInitialiser,
                    token,
                    parser,
                    varType);
        }
        Map<String, IScriptRelevantData> localVariableMap =
                parser.getSymbolTable().getLocalVariableMap();
        Map<String, IScriptRelevantData> globalVariableMap =
                parser.getSymbolTable().getScriptRelevantDataTypeMap();
        if (varName != null) {
            if (varType == null) {
                varType = new DefaultScriptRelevantData();
                varType.setName(varName);
                varType.setType(JsConsts.UNDEFINED_DATA_TYPE);
            }
            if (isGlobalVariable(varName, globalVariableMap)) {
                globalVariableMap.put(varName, varType);
            } else {
                localVariableMap.put(varName, varType);
            }
        }
    }

    private static boolean isGlobalVariable(String name,
            Map<String, IScriptRelevantData> globalVariableMap) {
        if (name != null && globalVariableMap.containsKey(name)) {
            return true;
        }
        return false;
    }

    private static IScriptRelevantData getLocalVariableType(
            AST variableInitialisation, Token token, JScriptParser parser,
            IScriptRelevantData varType) {
        int antlType = JScriptTokenTypes.UNDEFINE_DATA_TYPE;
        AST fcAST = variableInitialisation.getFirstChild();
        List<JsClass> supportedJsClasses = getAllSupportedJsClasses(parser);
        Map<String, IScriptRelevantData> scriptRelevantDataMap =
                new HashMap<String, IScriptRelevantData>();
        Map<String, IScriptRelevantData> localVariablesMap =
                new HashMap<String, IScriptRelevantData>();
        Map<String, IScriptRelevantData> localMethodsMap =
                new HashMap<String, IScriptRelevantData>();
        ISymbolTable symbolTable = parser.getSymbolTable();
        if (symbolTable != null) {
            scriptRelevantDataMap = symbolTable.getScriptRelevantDataTypeMap();
            localVariablesMap = symbolTable.getLocalVariableMap();
            localMethodsMap = symbolTable.getLocalMethodMap();
        }
        IScriptRelevantData resolvedVarType = null;
        List<IValidationStrategy> validatorStrategyList =
                parser.getValidatorStrategyList();
        if (validatorStrategyList != null && !validatorStrategyList.isEmpty()) {
            for (IValidationStrategy validationStrategy : validatorStrategyList) {
                if (validationStrategy instanceof IValidationStrategyExtended) {
                    IValidateResult evaluateExpression =
                            ((IValidationStrategyExtended) validationStrategy)
                                    .evaluateExpression(fcAST, token);
                    if (evaluateExpression != null
                            && evaluateExpression.getType() != null) {
                        IScriptRelevantData type = evaluateExpression.getType();
                        if (type.getType() != null && !type.getType()
                                .equals(JsConsts.UNDEFINED_DATA_TYPE)) {
                            resolvedVarType = type;
                            break;
                        }
                    }
                }
            }
        }
        if (resolvedVarType == null) {
            // either firstChild can be expression or arrayInitializer
            if (JScriptTokenTypes.EXPR == fcAST.getType()) {
                // Get the type for the expression
                varType = getLocalVariableTypeForExpression(fcAST,
                        token,
                        parser,
                        varType,
                        antlType,
                        supportedJsClasses,
                        localVariablesMap,
                        localMethodsMap,
                        scriptRelevantDataMap);

            } else if (JScriptTokenTypes.ARRAY_INIT == fcAST.getType()) {
                // Get the type for the arrayInitializer
                varType = getLocalVariableTypeForArrayInitializer(antlType,
                        varType,
                        supportedJsClasses);
            }
            if (!(varType instanceof IUMLScriptRelevantData)) {
                varType = JScriptUtils.resolveJavaScriptStringType(
                        varType.getName(),
                        varType.getType(),
                        varType.isArray(),
                        supportedJsClasses);
            }
        } else {
            varType = resolvedVarType;
        }
        varType = JScriptUtils.setReadOnly(varType, false);
        return varType;
    }

    public static IScriptRelevantData getLocalVariableTypeForArrayInitializer(
            int antlType, IScriptRelevantData varType,
            List<JsClass> supportedJsClasses) {
        antlType = JScriptTokenTypes.ARRAY_TYPE;
        // ACE-1322
        String varRelevantDataType = JsConsts.UNDEFINED_DATA_TYPE;
        // DataTypeMapper.getCorrespondingDataType(antlType);
        varType = JScriptUtils.resolveJavaScriptStringType(varType.getName(),
                varRelevantDataType,
                true,
                supportedJsClasses);
        return varType;
    }

    public static IScriptRelevantData getLocalVariableTypeForExpression(
            AST expAST, Token token, JScriptParser parser,
            IScriptRelevantData varType, int antlType,
            List<JsClass> supportedJsClasses,
            Map<String, IScriptRelevantData> localVariablesMap,
            Map<String, IScriptRelevantData> localMethodsMap,
            Map<String, IScriptRelevantData> scriptRelevantDataMap) {
        AST exprChildAST = expAST.getFirstChild();
        if (exprChildAST != null) {
            int childASTType = exprChildAST.getType();
            boolean isLeaf = ParseUtil.isLeaf(exprChildAST);
            if (isLeaf && isLiteral(exprChildAST)) {
                // Literal expressions
                varType = getLocalVariableTypeForLiteralExpression(exprChildAST,
                        supportedJsClasses);
            } else if (JScriptTokenTypes.IDENT == childASTType
                    || JScriptTokenTypes.METHOD_CALL == childASTType
                    || JScriptTokenTypes.DOT == childASTType
                    || childASTType == JScriptTokenTypes.INDEX_OP
                    || childASTType == JScriptTokenTypes.LITERAL_new) {
                // Non arithmetic expressions
                varType = getLocalVariableTypeForNonArithmeticExpression(token,
                        parser,
                        exprChildAST,
                        supportedJsClasses,
                        localVariablesMap,
                        localMethodsMap,
                        scriptRelevantDataMap);
            } else {
                // arithmetic expressions
                varType = getLocalVariableTypeForArithmeticExpression(
                        exprChildAST,
                        token,
                        parser,
                        supportedJsClasses,
                        localVariablesMap,
                        scriptRelevantDataMap);
            }
        }

        return varType;
    }

    private static IScriptRelevantData getLocalVariableTypeForNonArithmeticExpression(
            Token token, JScriptParser parser, AST nonAritmeticExpAST,
            List<JsClass> supportedJsClasses,
            Map<String, IScriptRelevantData> localVariablesMap,
            Map<String, IScriptRelevantData> localMethodsMap,
            Map<String, IScriptRelevantData> scriptRelevantDataMap) {
        int childASTType = nonAritmeticExpAST.getType();
        IScriptRelevantData strParameterType =
                new DefaultScriptRelevantData(JsConsts.UNDEFINED_DATA_TYPE,
                        JsConsts.UNDEFINED_DATA_TYPE, false);
        if (JScriptTokenTypes.IDENT == childASTType) {
            // taking care of an identifier
            strParameterType =
                    getLocalVariableTypeForIdentExpression(nonAritmeticExpAST,
                            localVariablesMap,
                            scriptRelevantDataMap);
        } else if (JScriptTokenTypes.DOT == childASTType
                || JScriptTokenTypes.METHOD_CALL == childASTType
                || JScriptTokenTypes.INDEX_OP == childASTType) {
            // taking care of an dot notation, array, and method call
            strParameterType =
                    getLocalVariableTypeForDotExpression(nonAritmeticExpAST,
                            supportedJsClasses,
                            localVariablesMap,
                            localMethodsMap,
                            scriptRelevantDataMap);
        } else if (JScriptTokenTypes.LITERAL_new == childASTType) {
            // taking care of a new expression
            strParameterType = getLocalVariableTypeForNewExpression(token,
                    parser,
                    nonAritmeticExpAST,
                    supportedJsClasses,
                    localVariablesMap,
                    scriptRelevantDataMap);
        }
        return strParameterType;
    }

    private static IScriptRelevantData getLocalVariableTypeForArithmeticExpression(
            AST arithmeticExpressionAST, Token token, JScriptParser parser,
            List<JsClass> supportedJsClasses,
            Map<String, IScriptRelevantData> localVariablesMap,
            Map<String, IScriptRelevantData> scriptRelevantDataMap) {
        try {
            Map<String, IScriptRelevantData> varMap =
                    new HashMap<String, IScriptRelevantData>();
            varMap.putAll(localVariablesMap);
            varMap.putAll(scriptRelevantDataMap);

            // to fix the issue mentioned in the javadocs of
            // JScriptExprValidator
            JScriptExprValidator validator = null;
            List<IValidationStrategy> ivsList =
                    parser.getValidatorStrategyList();
            for (IValidationStrategy ivs : ivsList) {
                if (ivs instanceof JScriptValidationStrategy) {
                    validator = new JScriptExprValidator();
                    validator.setScriptParser(parser);
                    validator.setVarNameResolver(ivs.getVarNameResolver());
                    break;
                }
            }

            String strExpression =
                    ParseUtil.resolveExpressionString(arithmeticExpressionAST,
                            varMap,
                            token,
                            validator);
            Object returnValue =
                    ScriptEngineUtil.getScriptEngine().eval(strExpression);
            IScriptRelevantData dataType =
                    ParseUtil.getDataType(returnValue, supportedJsClasses);
            return dataType;
        } catch (ScriptException e) {

        }
        IScriptRelevantData scriptRelevantData =
                new DefaultScriptRelevantData(JsConsts.UNDEFINED_DATA_TYPE,
                        JsConsts.UNDEFINED_DATA_TYPE, false);
        return scriptRelevantData;
    }

    /**
     * Extends <code>JScriptExpressionValidator</code> to get access in to
     * {@link #resolveMethodCall(AST, List, Token)}. HACK ALERT!!
     * 
     * <pre>
     * 		This is to access the method {@link AbstractExpressionValidator#resolveMethodCall(AST, List, Token)} as otherwise
     * 		scripts like below were failing with error: 'Conditional Expression Expected'.
     * 			..
     * 			var now = new Date();
     * 			var age = now.getFullYear() - birthDate.getFullYear();
     * 			if (age &lt; 0) {}
     * 		This is because when the expression contains a method, 
     * 		{@link ParseUtil#resolveExpressionString(AST, Map, Token, 
     * 			com.tibco.xpd.script.parser.internal.validator.ValidationUtil.JScriptExprValidator)}
     * 		was returning an undefined data type for the expression which was causing the above error. Here we are trying to
     * 		resolve the method invocations with out touching the existing parser/validator design.
     * </pre>
     * 
     * @since 3.1.0
     */
    public static class JScriptExprValidator
            extends JScriptExpressionValidator {
        /** {@inheritDoc}. */
        @Override
        public AST resolveMethodCall(AST expressionAST,
                List<String> supportedClasses, Token token) {
            return super.resolveMethodCall(expressionAST,
                    supportedClasses,
                    token);
        }
    }

    private static IScriptRelevantData getLocalVariableTypeForLiteralExpression(
            AST literalExpressionAST, List<JsClass> supportedJsClasses) {
        int antlType = literalExpressionAST.getType();
        String varRelevantDataType =
                DataTypeMapper.getCorrespondingDataType(antlType);
        IScriptRelevantData varType = JScriptUtils.resolveJavaScriptStringType(
                JsConsts.UNDEFINED_DATA_TYPE,
                varRelevantDataType,
                false,
                supportedJsClasses);
        return varType;
    }

    private static IScriptRelevantData getLocalVariableTypeForIdentExpression(
            AST identExpressionAST,
            Map<String, IScriptRelevantData> localVariablesMap,
            Map<String, IScriptRelevantData> scriptRelevantDataMap) {
        String varName = identExpressionAST.getText();
        IScriptRelevantData varType =
                JScriptUtils.getVariableType(localVariablesMap,
                        scriptRelevantDataMap,
                        varName);
        return varType;
    }

    private static IScriptRelevantData getLocalVariableTypeForDotExpression(
            AST dotExpressionAST, List<JsClass> supportedJsClasses,
            Map<String, IScriptRelevantData> localVariablesMap,
            Map<String, IScriptRelevantData> localMethodsMap,
            Map<String, IScriptRelevantData> scriptRelevantDataMap) {
        IScriptRelevantData variableType =
                new DefaultScriptRelevantData(JsConsts.UNDEFINED_DATA_TYPE,
                        JsConsts.UNDEFINED_DATA_TYPE, false);
        // Get the JsExpression of that AST
        JsExpression jsExpression =
                ExpressionUtil.getJsExpressionFromAST(dotExpressionAST,
                        DataTypeMapper.getSymbolTableKeyWords(),
                        scriptRelevantDataMap);
        if (jsExpression != null) {
            JsDataType jsDataType =
                    JScriptUtils.getScriptRelevantDataType(jsExpression,
                            supportedJsClasses,
                            scriptRelevantDataMap,
                            localVariablesMap,
                            localMethodsMap);
            if (jsDataType != null) {
                IScriptRelevantData dataType = jsDataType.getType();
                if (dataType instanceof IUMLScriptRelevantData) {
                    variableType = dataType;
                } else {
                    if (dataType != null) {
                        variableType = JScriptUtils.resolveJavaScriptStringType(
                                variableType.getName(),
                                dataType.getType(),
                                dataType.isArray(),
                                supportedJsClasses);
                    } else {
                        variableType = new DefaultScriptRelevantData(
                                variableType.getName(),
                                JsConsts.UNDEFINED_DATA_TYPE, false);
                    }
                }
            }
        }
        return variableType;
    }

    private static IScriptRelevantData getLocalVariableTypeForNewExpression(
            Token token, JScriptParser parser, AST newExpressionAST,
            List<JsClass> supportedJsClasses,
            Map<String, IScriptRelevantData> localVariablesMap,
            Map<String, IScriptRelevantData> scriptRelevantDataMap) {
        AST classNameAST = newExpressionAST.getFirstChild();
        IScriptRelevantData varType =
                new DefaultScriptRelevantData(JsConsts.UNDEFINED_DATA_TYPE,
                        JsConsts.UNDEFINED_DATA_TYPE, false);
        int antlType = getClassIntType(classNameAST);
        if (antlType == JScriptTokenTypes.ARRAY_TYPE) {
            if (classNameAST != null) {
                AST paramListAST = classNameAST.getNextSibling();
                if (paramListAST != null) {
                    // Check if the array is empty
                    AST arrayParam = paramListAST.getFirstChild();
                    if (arrayParam == null) {
                        String varRelevantDataType = DataTypeMapper
                                .getCorrespondingDataType(JsConsts.OBJECT_AST);
                        varType = JScriptUtils.resolveJavaScriptStringType(
                                varType.getName(),
                                varRelevantDataType,
                                true,
                                supportedJsClasses);
                    } else {
                        IScriptRelevantData parameterDataType =
                                getLocalVariableType(paramListAST,
                                        token,
                                        parser,
                                        varType);
                        if (parameterDataType != null) {
                            if (parameterDataType instanceof IUMLScriptRelevantData) {
                                IUMLScriptRelevantData umlScriptData =
                                        (IUMLScriptRelevantData) parameterDataType;
                                varType =
                                        JScriptUtils.evaluateScriptRelevantData(
                                                varType.getName(),
                                                umlScriptData.getJsClass(),
                                                supportedJsClasses,
                                                parameterDataType.isArray());
                            } else {
                                varType = JScriptUtils
                                        .resolveJavaScriptStringType(
                                                varType.getName(),
                                                parameterDataType.getType(),
                                                parameterDataType.isArray(),
                                                supportedJsClasses);
                            }
                        }
                    }
                    varType.setIsArray(true);
                }
            }
        } else {
            String varRelevantDataType =
                    DataTypeMapper.getCorrespondingDataType(antlType);
            if (varRelevantDataType != null && varRelevantDataType
                    .equals(JsConsts.UNDEFINED_DATA_TYPE)) {
                varRelevantDataType = classNameAST.getText();
            }
            varType = JScriptUtils.resolveJavaScriptStringType(varType
                    .getName(), varRelevantDataType, false, supportedJsClasses);
        }
        return varType;
    }

    private static List<JsClass> getAllSupportedJsClasses(
            JScriptParser parser) {
        List<JsClass> allSupportedJsClasses = new ArrayList<JsClass>();
        if (parser != null) {
            List<IValidationStrategy> validationStrategyList =
                    parser.getValidatorStrategyList();
            if (validationStrategyList != null) {
                for (Iterator<IValidationStrategy> iterator =
                        validationStrategyList.iterator(); iterator
                                .hasNext();) {
                    IValidationStrategy validationStrategy = iterator.next();
                    List<JsClass> vsSupportedJsClasses =
                            validationStrategy.getSupportedJsClasses();
                    // TODO: Remove duplicates in the list of supported classes
                    allSupportedJsClasses.addAll(vsSupportedJsClasses);
                }
            }
        }
        return allSupportedJsClasses;
    }

    public static void validateMethodDeclaration(JScriptParser parser,
            AST methodDefinition, Token token) {
        List<IValidationStrategy> validatorStrategyList =
                getValidatorStrategyList(parser);
        for (IValidationStrategy strategy : validatorStrategyList) {
            strategy.validateMethodDeclaration(methodDefinition, token);
        }
        ValidationUtil.validateExpr(methodDefinition, token, parser);
    }

    public static void validateExpression(JScriptParser parser,
            AST expressionAST, Token token) {
        List<IValidationStrategy> validatorStrategyList =
                getValidatorStrategyList(parser);
        for (IValidationStrategy strategy : validatorStrategyList) {
            if (strategy != null)
                strategy.validateExpression(expressionAST, token);
        }
        ValidationUtil.validateExpr(expressionAST, token, parser);
    }

    public static void validateTreeAST(JScriptParser parser, Token token) {
        List<IValidationStrategy> validatorStrategyList =
                getValidatorStrategyList(parser);
        for (IValidationStrategy strategy : validatorStrategyList) {
            if (strategy != null) {
                strategy.validateASTTree(parser.getAST(), token);
            }
        }
        ValidationUtil.validateExpr(parser.getAST(), token, parser);
    }

    public static void validateStatement(JScriptParser parser, AST statementAST,
            Token token) {
        List<IValidationStrategy> validatorStrategyList =
                getValidatorStrategyList(parser);
        if (validatorStrategyList != null) {
            for (IValidationStrategy validatorStartegy : validatorStrategyList) {
                if (validatorStartegy != null) {
                    validatorStartegy.validateStatement(statementAST, token);
                }
            }
        }
        ValidationUtil.validateExpr(statementAST, token, parser);
    }

    private static int getClassIntType(AST classNameAST) {
        if (classNameAST == null) {
            return JScriptTokenTypes.UNDEFINE_DATA_TYPE;
        }
        String className = classNameAST.getText();
        if (JsConsts.STRING.equals(className)) {
            return JScriptTokenTypes.STRING_LITERAL;
        } else if (JsConsts.NUMBER.equals(className)) {
            return JScriptTokenTypes.NUMBER;
        } else if (JsConsts.DATE.equals(className)) {
            return JScriptTokenTypes.DATE;
        } else if (JsConsts.ARRAY.equals(className)) {
            return JScriptTokenTypes.ARRAY_TYPE;
        } else if (JsConsts.BOOLEAN.equals(className)) {
            return JScriptTokenTypes.BOOLEAN;
        } else if (JsConsts.OBJECT.equals(className)) {
            return JsConsts.OBJECT_AST;
        }
        return JScriptTokenTypes.UNDEFINE_DATA_TYPE;
    }

    /**
     * This method helps to decide whether the passed AST reprsents an arthemtic
     * expression.
     * 
     * @param expression
     * @return
     */
    public static boolean isLiteral(AST expression) {
        int exprType = expression.getType();
        if (exprType == JScriptTokenTypes.LITERAL_true
                || exprType == JScriptTokenTypes.LITERAL_false
                || exprType == JScriptTokenTypes.LITERAL_null
                || exprType == JScriptTokenTypes.NUM_INT
                || exprType == JScriptTokenTypes.NUM_LONG
                || exprType == JScriptTokenTypes.NUM_FLOAT
                || exprType == JScriptTokenTypes.NUM_DOUBLE
                || exprType == JScriptTokenTypes.STRING_LITERAL) {
            return true;
        }
        return false;
    }

    public static Map<String, IScriptRelevantData> getNonUMLAndDateScriptRelevantData(
            Map<String, IScriptRelevantData> allScriptRelevantData) {
        Map<String, IScriptRelevantData> nonUMLScriptRelevantData =
                new HashMap<String, IScriptRelevantData>();
        if (allScriptRelevantData != null) {
            Set<Map.Entry<String, IScriptRelevantData>> entrySet =
                    allScriptRelevantData.entrySet();
            for (Iterator<Map.Entry<String, IScriptRelevantData>> iterator =
                    entrySet.iterator(); iterator.hasNext();) {
                Map.Entry<String, IScriptRelevantData> entry = iterator.next();
                if (entry != null) {
                    IScriptRelevantData type = entry.getValue();
                    if (type != null
                            && !(type instanceof IUMLScriptRelevantData)) {
                        nonUMLScriptRelevantData.put(entry.getKey(), type);
                    } else {
                        if (type instanceof IUMLScriptRelevantData) {
                            if (type.getName() != null && JsConsts.DATETIME
                                    .equals(type.getType())) {
                                nonUMLScriptRelevantData.put(entry.getKey(),
                                        type);
                            }
                        }
                    }
                }
            }
        }
        return nonUMLScriptRelevantData;
    }
}
