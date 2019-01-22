package com.tibco.xpd.script.parser.internal.refactoring;

import java.util.List;

import antlr.LLkParser;
import antlr.Token;
import antlr.collections.AST;

import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.parser.internal.validator.IExpressionFactory;
import com.tibco.xpd.script.parser.internal.validator.IVarNameResolver;

/**
 * Interface for the refactoring strategy
 * 
 * @author mtorres
 *
 */
public interface IRefactoringStrategy {
    /**
     * This method will refactor the method call
     * 
     * @param methodAST
     * @param token
     */
    void refactorMethodCall(AST methodAST, Token token);

    /**
     * This method will refactor the local function definition
     * 
     * @param methodAST
     * @param token
     */
    void refactorMethodDeclaration(AST methodAST, Token token);

    /**
     * 
     * @param typeSpec
     * @param variableDeclaration
     * @param variableInitialisation
     * @param token
     * @return
     */
    void refactorVariableDeclaration(AST varDefAST, Token token);

    /**
     * This is refactor the new expression.
     * 
     * @param expressionAST
     * @param token
     */
    void refactorNewExpression(AST expressionAST, Token token);

    /**
     * This will refactor whether the passed expression evaluates to true/false.
     * 
     * @param expressionAST
     * @param token
     */
    void refactorConditionalExpression(AST expressionAST, Token token);

    /**
     * This method reports whether the variable in question has been defined or
     * not.
     * 
     * @param expression
     * @param token
     */
    void refactorUndefinedVariableUse(AST expression, Token token);

    /**
     * 
     * @param statementAST
     * @param token
     */
    void refactorStatement(AST statementAST, Token token);

    /**
     * 
     * @param expressionAST
     * @param token
     */
    void refactorExpression(AST expressionAST, Token token);

    /**
     * This passed astTree is the whole tree which can be refactored.
     * 
     * @param astTree
     * @param token
     *            TODO
     */
    void refactorASTTree(AST astTree, Token token);

    /**
     * 
     * @param parser
     */
    void setScriptParser(LLkParser parser);

    /**
     * 
     * @param varNameResolver
     */
    void setVarNameResolver(IVarNameResolver varNameResolver);

    /**
     * 
     * @return
     */
    IVarNameResolver getVarNameResolver();

//    /**
//	 * 
//	 */
//    List<JsClass> getSupportedJsClasses();

    /**
     * Returns the destination id for this Strategy
     * 
     * @return
     */
    String getDestinationName();

    /**
     * Sets the destination id for this Strategy
     * 
     * @return
     */
    void setDestinationName(String destinationName);

    /**
     * Returns the script type.
     * 
     * @return
     */
    String getScriptType();

    /**
     * Sets the script Type.
     */
    void setScriptType(String scriptType);
    
    /**
     * Returns the Expression refactor factory
     * @return
     */
    IExpressionRefactorFactory getExpresionRefactorFactory();
    
    /**
     * Returns the expression factory
     * @return
     */
    IExpressionFactory getExpresionFactory();
    
    /**
     * Sets the refactoring information
     * @param info
     */
    void setRefactoringInfo(RefactoringInfo info);
    
    /**
     * Sets the refactor result list
     * 
     * @param refactorResultList
     */
    void setRefactorResultList(List<RefactorResult> refactorResultList);
    
    /**
     * Gets the refactor result list
     * @return
     */
    List<RefactorResult> getRefactorResultList();
    

    /**
     * Evaluates the expression
     * 
     * @param expressionAST
     * @param token
     * @return
     */
    RefactorResult evaluateExpression(AST expressionAST, Token token);    

    /**
     * Gets the supported JsClasses
     * 
     * @return
     */
    List<JsClass> getSupportedJsClasses();
    
}
