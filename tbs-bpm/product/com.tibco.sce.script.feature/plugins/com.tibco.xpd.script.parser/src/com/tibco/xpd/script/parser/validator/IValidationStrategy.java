package com.tibco.xpd.script.parser.validator;

import java.util.List;

import antlr.LLkParser;
import antlr.Token;
import antlr.collections.AST;

import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.parser.internal.validator.IVarNameResolver;

public interface IValidationStrategy {
    /**
     * This method will validate the method call
     * 
     * @param methodAST
     * @param token
     */
    void validateMethodCall(AST methodAST, Token token);

    /**
     * This method will validate the local function definition
     * 
     * @param methodAST
     * @param token
     */
    void validateMethodDeclaration(AST methodAST, Token token);

    /**
     * This will return true/false value depending upon whether the variable
     * declaration is valid or not.
     * 
     * @param typeSpec
     * @param variableDeclaration
     * @param variableInitialisation
     * @param token
     * @return
     */
    void validateVariableDeclaration(AST varDefAST, Token token);

    /**
     * This is validate the new expression.
     * 
     * @param expressionAST
     * @param token
     */
    void validateNewExpression(AST expressionAST, Token token);

    /**
     * This will validate whether the passed expression evaluates to true/false.
     * 
     * @param expressionAST
     * @param token
     */
    void validateConditionalExpression(AST expressionAST, Token token);

    /**
     * This method reports whether the variable in question has been defined or
     * not.
     * 
     * @param expression
     * @param token
     */
    void validateUndefinedVariableUse(AST expression, Token token);

    /**
     * 
     * @param statementAST
     * @param token
     */
    void validateStatement(AST statementAST, Token token);

    /**
     * 
     * @param expressionAST
     * @param token
     */
    void validateExpression(AST expressionAST, Token token);

    /**
     * This passed astTree is the whole tree which can be validated.
     * 
     * @param astTree
     * @param token
     *            TODO
     */
    void validateASTTree(AST astTree, Token token);

    /**
     * 
     * @param parser
     */
    void setScriptParser(LLkParser parser);

    /**
     * 
     * @return
     */
    List<ErrorMessage> getErrorList();

    /**
     * 
     * @return
     */
    List<ErrorMessage> getWarningList();

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

    /**
	 * 
	 */
    List<JsClass> getSupportedJsClasses();

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

}
