/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.script.parser.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.mozilla.javascript.IdScriptableObject;

import antlr.RecognitionException;
import antlr.Token;
import antlr.collections.AST;

import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.IUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.JsExpression;
import com.tibco.xpd.script.model.client.JsExpressionMethod;
import com.tibco.xpd.script.model.jscript.JScriptUtils;
import com.tibco.xpd.script.parser.antlr.JScriptEmitter;
import com.tibco.xpd.script.parser.antlr.JScriptParser;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
import com.tibco.xpd.script.parser.antlr.exceptions.Messages;
import com.tibco.xpd.script.parser.internal.util.ExpressionUtil;
import com.tibco.xpd.script.parser.internal.util.JavaScriptConsts;
import com.tibco.xpd.script.parser.internal.validator.DataTypeMapper;
import com.tibco.xpd.script.parser.internal.validator.ValidationUtil.JScriptExprValidator;
import com.tibco.xpd.script.parser.validator.ErrorMessage;
import com.tibco.xpd.script.parser.validator.ErrorType;

public class ParseUtil {
    
    private static final String UNEXPECTED_TOKEN = "unexpected token:";//$NON-NLS-1$
    
    private static final String UNEXPECTED_ENDOF_SUBTREE = "unexpected end of subtree";//$NON-NLS-1$  
    
    private static final String UNEXPECTED_AST_NODE = "unexpected AST node:";//$NON-NLS-1$  
    
    private static final String EXPECTED_SEMI = "expecting SEMI, found 'null'";//$NON-NLS-1$  
    
    
    /**
     * This method returns a list of children ASTs which are children of the
     * first child.
     */
    public static List<AST> getFirstLevelChildren(AST varDef,
            List<Integer> astTypeList) {
        ArrayList<AST> childList = new ArrayList<AST>();
        doWorkForChildAST(varDef.getFirstChild(), astTypeList, childList);
        return childList;
    }

    /**
     * This method returns a list of child ASTs which are of the passed type.
     */
    public static List<AST> getChildASTList(AST varDef,
            List<Integer> astTypeList) {
        ArrayList<AST> childList = new ArrayList<AST>();
        doWorkForChildAST(varDef, astTypeList, childList);
        return childList;
    }

    private static void doWorkForChildAST(AST ast, List<Integer> astTypeList,
            ArrayList<AST> childList) {
        for (AST sibling = ast; sibling != null; sibling =
                sibling.getNextSibling()) {
            if (isOfInterest(astTypeList, sibling)) {
                childList.add(sibling);
                continue;
            }
            AST firstChild = sibling.getFirstChild();
            if (firstChild != null) {
                doWorkForChildAST(firstChild, astTypeList, childList);
            }
        }
    }

    private static boolean isOfInterest(List<Integer> astTypeList,
            AST currentAST) {
        boolean toReturn = false;
        for (Integer astType : astTypeList) {
            if (astType == currentAST.getType()) {
                toReturn = true;
                break;
            }
        }
        return toReturn;
    }

    public static String[] getClassAndMethodName(AST methodCall, Map<String, IScriptRelevantData> scriptRelevantDataMap) {
        String[] toReturn = new String[2];
        //Get the JsExpression of that AST
        JsExpression jsExpression = ExpressionUtil.getJsExpressionFromAST(methodCall, DataTypeMapper.getSymbolTableKeyWords(), scriptRelevantDataMap);
        
        if(jsExpression != null){
            if (jsExpression.getName() != null
                    && (jsExpression.getName().equals(JsConsts.INTEGER)
                            || jsExpression.getName().equals(JsConsts.FLOAT) || jsExpression
                            .getName().equals(JsConsts.DOUBLE))) {
                toReturn[0] = JsConsts.NUMBER; 
            }else{
                toReturn[0] = jsExpression.getName();
            }
            JsExpression methodExpression = jsExpression.getNextExpression();
            if(methodExpression instanceof JsExpressionMethod){
                toReturn[1] = methodExpression.getName();
            }
        }
        return toReturn;
    }

    public static boolean isLeaf(AST node) {
        boolean toReturn = false;
        AST firstChild = node.getFirstChild();
        if (firstChild == null) {
            toReturn = true;
        }
        return toReturn;
    }

    public static List<AST> getDotAstList(AST anyAST) {
        ArrayList<AST> childList = new ArrayList<AST>();
        doWorkForDotAST(anyAST, childList);
        return childList;
    }

    private static void doWorkForDotAST(AST anyAst, ArrayList<AST> childList) {
        for (AST sibling = anyAst; sibling != null; sibling =
                sibling.getNextSibling()) {
            if (sibling != null && sibling.getType() == JScriptTokenTypes.DOT) {
                childList.add(sibling);
                continue;
            }
            AST firstChild = sibling.getFirstChild();
            if (firstChild != null) {
                doWorkForDotAST(firstChild, childList);
            }
        }
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
    public static String resolveExpressionString(AST expressionAST,
            Map<String, IScriptRelevantData> varMap, Token token,
            JScriptExprValidator validator) {

        StringBuffer buffer = new StringBuffer();
        JScriptEmitter emitter = new JScriptEmitter(buffer);
        Map<String, String> defaultValueMap =
                DataTypeMapper.getDefaultValue(varMap);
        
        AST newExpressionAST = expressionAST;
        if (validator != null) {
        	// if there is method call resolve it, else script like below will fail:
        	// 					...
        	// 					var now = new Date();
        	// 					var age = now.getFullYear() - birthDate.getFullYear();
        	// 					if (age < 0) {
        	// 					}
        	List<AST> methodCallList = getChildASTList(newExpressionAST, Collections.singletonList(JScriptTokenTypes.METHOD_CALL));
        	if (methodCallList.size() > 0)
        		newExpressionAST = validator.resolveMethodCall(expressionAST, ExpressionUtil.getSupportedClasses(), token);
        }
        
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
    }

    /**
     * This method needs to return the data type that are configured on the
     * content assist.
     * 
     * @param returnValue
     * @return
     */
    public static IScriptRelevantData getDataType(Object returnValue,
            List<JsClass> supportedJsClasses) {
        IScriptRelevantData dataType;
        if (returnValue instanceof IdScriptableObject) {
            IdScriptableObject scriptObject = (IdScriptableObject) returnValue;
            String className = scriptObject.getClassName();
            dataType =
                    JScriptUtils.resolveJavaScriptStringType(className,
                            className, false,
                            supportedJsClasses);
            return dataType;
        }
        if (returnValue instanceof Number) {
            if (returnValue instanceof Double || returnValue instanceof Float) {
                dataType =
                        JScriptUtils.resolveJavaScriptStringType(JsConsts.FLOAT,
                                JsConsts.NUMBER, false,
                                supportedJsClasses);
                return dataType;
            } else if (returnValue instanceof Integer) {
                dataType =
                        JScriptUtils.resolveJavaScriptStringType(JsConsts.INTEGER,
                                JsConsts.NUMBER, false,
                                supportedJsClasses);
                return dataType;
            } else if (returnValue instanceof Boolean) {
                dataType =
                        JScriptUtils.resolveJavaScriptStringType(JsConsts.BOOLEAN,
                                JsConsts.BOOLEAN, false,
                                supportedJsClasses);
                return dataType;
            }
        }
        dataType =
                JScriptUtils.resolveJavaScriptStringType(returnValue.getClass()
                        .getSimpleName(), returnValue.getClass()
                        .getSimpleName(), false, supportedJsClasses);
        return dataType;
    }

    public static AST transformVarDeclationToExpression(AST varDeclarationAST, JScriptParser scriptParser){
      //Create the new expression
        AST newExpressionAST = scriptParser.getASTFactory().create(JScriptTokenTypes.EXPR);
        //Create the new assignment
        AST newAssignmentAST = scriptParser.getASTFactory().create(JScriptTokenTypes.ASSIGN);
        newExpressionAST.setFirstChild(newAssignmentAST);
        //AST newAssignmentExpresionAST = scriptParser.getASTFactory().create(JScriptTokenTypes.IDENT);
        AST newAssignmentIdentifierAST = scriptParser.getASTFactory().create(JScriptTokenTypes.IDENT);
        
        AST firstChildAST = varDeclarationAST.getFirstChild(); 
        if(firstChildAST != null && firstChildAST.getType() == JScriptTokenTypes.TYPE){
            AST identifierAST = firstChildAST.getNextSibling();
            if(identifierAST != null && identifierAST.getType() == JScriptTokenTypes.IDENT){
                newAssignmentIdentifierAST.setText(identifierAST.getText());                
                AST assignmentAST = identifierAST.getNextSibling();
                if(assignmentAST != null && assignmentAST.getType() == JScriptTokenTypes.ASSIGN){
                    AST expressionAST = assignmentAST.getFirstChild();
                    if(expressionAST != null && expressionAST.getType() == JScriptTokenTypes.EXPR){
                        AST assignmentContentAST = expressionAST.getFirstChild();
                        if(assignmentContentAST != null){
                            //newAssignmentExpresionAST = scriptParser.getASTFactory().dup(assignmentContentAST);
                            newAssignmentAST.setFirstChild(newAssignmentIdentifierAST);
                            newAssignmentIdentifierAST.setNextSibling(assignmentContentAST); // add expr
                        }  
                    }
                } 
            }                
        }
        //newAssignmentAST.addChild(newAssignmentIdentifierAST);            
        //newAssignmentIdentifierAST.setNextSibling(newAssignmentExpresionAST);
        return newExpressionAST;
    }
    
    public static String getIdentifierNameFromVarDeclaration(AST varDeclarationAST){
        String identName = null;
        AST firstChildAST = varDeclarationAST.getFirstChild(); 
        if(firstChildAST != null && firstChildAST.getType() == JScriptTokenTypes.TYPE){
            AST identifierAST = firstChildAST.getNextSibling();
            if(identifierAST != null && identifierAST.getType() == JScriptTokenTypes.IDENT){
                return identifierAST.getText();
            }
        }
        return identName;
    }
    
    public static boolean isHandCodedVarDeclaration(AST varDeclarationAST){
        AST firstChildAST = varDeclarationAST.getFirstChild(); 
        if(firstChildAST != null && firstChildAST.getType() == JScriptTokenTypes.TYPE){
            AST varIdentifierAST = firstChildAST.getFirstChild();
            if(varIdentifierAST != null && varIdentifierAST.getType() == JScriptTokenTypes.LITERAL_var){
                 if(varIdentifierAST.getText() != null && varIdentifierAST.getText().equals(JavaScriptConsts.KEYWORD_HCVAR)){
                     return true;
                 }
            }
        }
        return false;
    }
    
    public static AST getLastExpressionFromTree(AST treeAST){
        AST lastExpAST = null;
        if(treeAST != null){
            lastExpAST = treeAST.getNextSibling();
            while (lastExpAST != null && lastExpAST.getNextSibling() != null) {
                lastExpAST = lastExpAST.getNextSibling();
            }
        }
        if(lastExpAST==null){
            lastExpAST = treeAST;
        }
        return lastExpAST;
    }
    
    public static ErrorMessage getANTLRFormattedMessage(int lineNumber,
            int columnNumber, String errorMessage, ErrorType errorType) {
        String formattedMsg = null;
        List<String> additionalAttributes = new ArrayList<String>();

        if (errorMessage != null) {
            if (errorMessage.startsWith(UNEXPECTED_TOKEN)) {
                formattedMsg = Messages.TCNoViableAltException_Unexpected_Token;
                additionalAttributes
                        .add(getTokenFromErrorMessage(errorMessage));
            } else if (errorMessage.startsWith(UNEXPECTED_ENDOF_SUBTREE)) {
                formattedMsg =
                        Messages.TCNoViableAltException_UnexpectedEndOfSubtree;
            } else if (errorMessage.startsWith(UNEXPECTED_AST_NODE)) {
                formattedMsg =
                        Messages.TCNoViableAltException_UnexpectedASTNode;
                additionalAttributes
                        .add(getAstNodeFromErrorMessage(errorMessage));
            } else if (errorMessage.startsWith(EXPECTED_SEMI)) {
                formattedMsg =
                    Messages.TCNoViableAltException_ExpectedSemi;
            } 
        }
        if (formattedMsg == null) {
            formattedMsg = errorMessage;
        }
        ErrorMessage returnErrorMessage =
                new ErrorMessage(lineNumber, columnNumber, formattedMsg,
                        errorType, additionalAttributes);
        return returnErrorMessage;
    }
    
    private static String getTokenFromErrorMessage(String message){
        String token = null;
        if(message != null){
            token = message.replace(UNEXPECTED_TOKEN, "").trim();//$NON-NLS-1$
        }
        return token;
    }
    
    private static String getAstNodeFromErrorMessage(String message){
        String token = null;
        if(message != null){
            token = message.replace(UNEXPECTED_AST_NODE, "").trim();//$NON-NLS-1$
        }
        return token;
    }
    
//    public static void nulifySRDReferences(
//            Collection<IScriptRelevantData> scriptRelevantDataList) {
//        if (scriptRelevantDataList != null && !scriptRelevantDataList.isEmpty()) {
//            for (IScriptRelevantData iScriptRelevantData : scriptRelevantDataList) {
//                if (JScriptUtils
//                        .isFactoryScriptRelevantData(iScriptRelevantData)) {
//                    IUMLScriptRelevantData iumlScriptRelevantData =
//                            (IUMLScriptRelevantData) iScriptRelevantData;
//                    JsClass jsClass = iumlScriptRelevantData.getJsClass();
//                    if (jsClass != null && jsClass.getUmlClass() != null) {
//                        Class umlClass = jsClass.getUmlClass();
//                        if (umlClass.getOwnedOperations() != null
//                                && !umlClass.getOwnedOperations().isEmpty()) {
//                            for (Operation operation : umlClass
//                                    .getOwnedOperations()) {
//                                operation.setType(null);
//                                for (Parameter param : operation
//                                        .getOwnedParameters()) {
//                                    param.eAdapters().clear();
//                                }
//                                operation.getOwnedParameters().clear();
//                                operation.eAdapters().clear();
//                            }
//                        }
//                        umlClass.eAdapters().clear();
//                        umlClass = null;
//                    }
//                }
//            }
//        }
//    }
            
}
