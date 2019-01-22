package com.tibco.xpd.script.parser.internal.refactoring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import antlr.Token;
import antlr.collections.AST;

import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.DefaultScriptRelevantData;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.IUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.jscript.JScriptUtils;
import com.tibco.xpd.script.parser.antlr.JScriptRefactorParser;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
import com.tibco.xpd.script.parser.internal.validator.ValidationUtil;
import com.tibco.xpd.script.parser.validator.ISymbolTable;

/**
 * Utilitity for the refactoring of elements
 * 
 * @author mtorres
 * 
 */
public class RefactoringUtil {

    public static void refactorUndefinedVariableUse(
            JScriptRefactorParser parser, AST expression, Token token) {
        List<IRefactoringStrategy> refactoringStrategyList =
                getRefactoringStrategyList(parser);
        if (refactoringStrategyList != null) {
            for (IRefactoringStrategy refactoringStrategy : refactoringStrategyList) {
                if (refactoringStrategy != null) {
                    refactoringStrategy
                            .refactorUndefinedVariableUse(expression, token);
                }
            }
        }
    }

    public static void refactorConditionalExpr(JScriptRefactorParser parser,
            AST expression, Token token) {
        List<IRefactoringStrategy> refactoringStrategyList =
                getRefactoringStrategyList(parser);
        if (refactoringStrategyList != null) {
            for (IRefactoringStrategy refactoringStrategy : refactoringStrategyList) {
                if (refactoringStrategy != null) {
                    refactoringStrategy
                            .refactorConditionalExpression(expression, token);
                }
            }
        }
    }

    public static void refactorMethodCallExpr(JScriptRefactorParser parser,
            AST expression, Token token) {
        List<IRefactoringStrategy> refactoringStrategyList =
                getRefactoringStrategyList(parser);
        if (refactoringStrategyList != null) {
            for (IRefactoringStrategy refactoringStrategy : refactoringStrategyList) {
                if (refactoringStrategy != null) {
                    refactoringStrategy.refactorMethodCall(expression, token);
                }
            }
        }
    }

    public static void refactorExpression(JScriptRefactorParser parser,
            AST expression, Token token) {
        List<IRefactoringStrategy> refactoringStrategyList =
                getRefactoringStrategyList(parser);
        if (refactoringStrategyList != null) {
            for (IRefactoringStrategy refactoringStrategy : refactoringStrategyList) {
                if (refactoringStrategy != null) {
                    refactoringStrategy.refactorExpression(expression, token);
                }
            }
        }
    }

    public static void refactorVariableDeclaration(
            JScriptRefactorParser parser, AST expression, Token token) {
        List<IRefactoringStrategy> refactoringStrategyList =
                getRefactoringStrategyList(parser);
        if (refactoringStrategyList != null) {
            for (IRefactoringStrategy refactoringStrategy : refactoringStrategyList) {
                if (refactoringStrategy != null) {
                    refactoringStrategy.refactorVariableDeclaration(expression,
                            token);
                }
            }
        }
    }

    public static List<IRefactoringStrategy> getRefactoringStrategyList(
            JScriptRefactorParser parser) {
        return parser.getRefactoringStrategyList();
    }

    public static void addLocalVariable(AST varDefAST,
            JScriptRefactorParser parser, Token token) {
        if (parser != null && !parser.getRefactoringStrategyList().isEmpty()) {
            RefactoringUtil.addLocalVarToParserSymbolTable(varDefAST,
                    parser,
                    token);
        }
    }

    public static void addLocalVariableWithoutVar(AST varDefAST,
            JScriptRefactorParser parser, Token token) {
        if (parser != null && !parser.getRefactoringStrategyList().isEmpty()) {
            RefactoringUtil.addLocalVarToParserSymbolTableWithoutVar(varDefAST,
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
            JScriptRefactorParser parser, Token token) {
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
            JScriptRefactorParser parser, Token token) {
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
            varType =
                    getLocalVariableType(varInitialiser, token, parser, varType);
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

    private static IScriptRelevantData getLocalVariableType(
            AST variableInitialisation, Token token,
            JScriptRefactorParser parser, IScriptRelevantData varType) {
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
        List<IRefactoringStrategy> refactoringStrategyList =
                parser.getRefactoringStrategyList();
        if (refactoringStrategyList != null
                && !refactoringStrategyList.isEmpty()) {
            for (IRefactoringStrategy refactoringStrategy : refactoringStrategyList) {
                RefactorResult evaluateExpression =
                        refactoringStrategy.evaluateExpression(fcAST, token);
                if (evaluateExpression != null
                        && evaluateExpression.getType() != null) {
                    IScriptRelevantData type = evaluateExpression.getType();
                    if (type.getType() != null
                            && !type.getType()
                                    .equals(JsConsts.UNDEFINED_DATA_TYPE)) {
                        resolvedVarType = type;
                        break;
                    }
                }
            }
        }
        if (resolvedVarType == null) {
            // either firstChild can be expression or arrayInitializer
            if (JScriptTokenTypes.EXPR == fcAST.getType()) {
                // Get the type for the expression
                varType =
                        ValidationUtil.getLocalVariableTypeForExpression(fcAST,
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
                varType =
                        ValidationUtil
                                .getLocalVariableTypeForArrayInitializer(antlType,
                                        varType,
                                        supportedJsClasses);
            }
            if (!(varType instanceof IUMLScriptRelevantData)) {
                varType =
                        JScriptUtils.resolveJavaScriptStringType(varType
                                .getName(), varType.getType(), varType
                                .isArray(), supportedJsClasses);
            }
        } else {
            varType = resolvedVarType;
        }
        varType = JScriptUtils.setReadOnly(varType, false);
        return varType;
    }

    private static boolean isGlobalVariable(String name,
            Map<String, IScriptRelevantData> globalVariableMap) {
        if (name != null && globalVariableMap.containsKey(name)) {
            return true;
        }
        return false;
    }

    private static List<JsClass> getAllSupportedJsClasses(
            JScriptRefactorParser parser) {
        List<JsClass> allSupportedJsClasses = new ArrayList<JsClass>();
        if (parser != null) {
            List<IRefactoringStrategy> refactoringStrategyList =
                    parser.getRefactoringStrategyList();
            if (refactoringStrategyList != null) {
                for (Iterator<IRefactoringStrategy> iterator =
                        refactoringStrategyList.iterator(); iterator.hasNext();) {
                    IRefactoringStrategy refactoringStrategy =
                            (IRefactoringStrategy) iterator.next();
                    List<JsClass> vsSupportedJsClasses =
                            refactoringStrategy.getSupportedJsClasses();
                    // TODO: Remove duplicates in the list of supported classes
                    allSupportedJsClasses.addAll(vsSupportedJsClasses);
                }
            }
        }
        return allSupportedJsClasses;
    }

}
