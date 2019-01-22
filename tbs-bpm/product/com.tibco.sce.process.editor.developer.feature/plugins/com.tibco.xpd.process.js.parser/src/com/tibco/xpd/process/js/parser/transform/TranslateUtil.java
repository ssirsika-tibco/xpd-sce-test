/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.process.js.parser.transform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;

import antlr.ASTFactory;
import antlr.collections.AST;

import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.process.js.parser.antlr.IpeScriptEmitter;
import com.tibco.xpd.process.js.parser.antlr.IpeScriptTranslator;
import com.tibco.xpd.process.js.parser.util.Consts;
import com.tibco.xpd.process.js.parser.util.Utility;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.DefaultScriptRelevantData;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.JsClassDefinitionReader;
import com.tibco.xpd.script.model.client.JsMethod;
import com.tibco.xpd.script.model.client.JsMethodParam;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
import com.tibco.xpd.script.parser.util.ParseUtil;
import com.tibco.xpd.script.parser.validator.ISymbolTable;

public class TranslateUtil {

    private static final String STR_RETURN = "$RETURN"; //$NON-NLS-1$

    private static final String STR_EXIT = "EXIT"; //$NON-NLS-1$

    private static final String STR_EXPR = "EXPR"; //$NON-NLS-1$

    private static final String USER_OPEN_SCRIPT = "OpenScript"; //$NON-NLS-1$

    private static final String USER_CLOSE_SCRIPT = "CloseScript"; //$NON-NLS-1$

    private static final String USER_SUBMIT_SCRIPT = "SubmitScript"; //$NON-NLS-1$

    private static final String OUTPUT_MAPPING_SCRIPT = "OutputMappingScript"; //$NON-NLS-1$    

    public static String getMethodName(AST methodAST) {
        AST modifiersAST = methodAST.getFirstChild();
        AST typeAST = modifiersAST.getNextSibling();
        AST methodNameAST = typeAST.getNextSibling();
        return methodNameAST.getText();
    }

    /**
     * This will return boolean if the passed list contains any unsupported
     * keywords
     */

    public static boolean isAnyUnSupportedKeyWordPresent(List<AST> list,
            List<String> destinationList, String scriptType) {
        for (Iterator iter = list.iterator(); iter.hasNext();) {
            AST element = (AST) iter.next();
            boolean b =
                    Utility.getUnSupportedClasses(destinationList, scriptType)
                            .contains(element.getText());
            if (b) {
                return true;
            }
        }
        return false;
    }

    /**
     * returns true if the method call is from supported class
     * 
     * @param methodCallList
     * @return
     */
    public static boolean isMethodCallListFromSupportedClass(
            IpeScriptEmitter outParser, List<AST> methodCallList) {
        boolean toReturn = true;
        for (Iterator iter = methodCallList.iterator(); iter.hasNext();) {
            AST methodCall = (AST) iter.next();
            AST firstIdentifierAST = methodCall.getFirstChild();
            boolean identifier = false;
            boolean stringLiteral = false;
            while (true) {
                if (firstIdentifierAST.getType() == JScriptTokenTypes.IDENT) {
                    identifier = true;
                    break;
                }
                if (firstIdentifierAST.getType() == JScriptTokenTypes.STRING_LITERAL) {
                    stringLiteral = true;
                    break;
                }
                firstIdentifierAST = firstIdentifierAST.getFirstChild();
            }
            if (identifier) {
                String className = firstIdentifierAST.getText();
                if (STR_EXIT.equals(className)) {
                    // this is a case for the conversion of a return
                    // statement into $RETURN and EXIT().
                    return true;
                }
                List<String> processDestination =
                        outParser.getProcessDestination();
                String scriptType = outParser.getScriptType();
                List<String> supportedClasses =
                        Utility.getSupportedClasses(processDestination,
                                scriptType);
                boolean bool = supportedClasses.contains(className);
                if (!bool) {
                    toReturn = false;
                    break;
                }
            } else if (stringLiteral) {
                toReturn = false;
                break;
            }
        }
        return toReturn;
    }

    public static boolean isMethodCallFromSupportedClass(
            IpeScriptEmitter outParser, AST methodCallAST) {
        ArrayList<AST> list = new ArrayList<AST>();
        list.add(methodCallAST);
        boolean result = isMethodCallListFromSupportedClass(outParser, list);
        return result;
    }

    public static boolean isUnSupportedKeyWord(String keyWord,
            List<String> destinationList, String scriptType) {
        List<String> unSupportedClasses =
                Utility.getUnSupportedClasses(destinationList, scriptType);
        return unSupportedClasses.contains(keyWord);
    }

    public static boolean isSupportedClass(IpeScriptEmitter outParser,
            String keyWord) {
        List<String> processDestination = outParser.getProcessDestination();
        String scriptType = outParser.getScriptType();
        List<String> supportedClasses =
                Utility.getSupportedClasses(processDestination, scriptType);
        return supportedClasses.contains(keyWord);
    }

    public static String getCorrespondingKeyWord(String keyWord) {
        String corrKeyWord = TranslateConsts.correspondingKeyWord.get(keyWord);
        if (corrKeyWord != null) {
            return corrKeyWord;
        } else {
            return keyWord;
        }
    }

    public static void handleIfBlock(AST ifAST, StringBuffer lineOutput,
            Stack<CodeBlock> blockStack) {
        AST exprAST = ifAST.getFirstChild();
        AST lBrace = exprAST.getNextSibling();
        CodeBlock parentBlock = getCurrentCodeBlock(blockStack);
        if (lBrace != null) {
            AST elseBrace = lBrace.getNextSibling();
            if (lBrace.getText().equals("{")) { //$NON-NLS-1$
                if (elseBrace != null) {
                    if (elseBrace.getText().equalsIgnoreCase("if")) { //$NON-NLS-1$
                        // as this if could be part of the if else if statement
                        // so we do not need to add this block
                        if (lineOutput.toString().trim().equals("ELSEIF")) { //$NON-NLS-1$
                            parentBlock
                                    .setBlockName(TranslateConsts.IF_ELSEIF_STATEMENT);
                            parentBlock.incrementChildBlockCount();
                        } else {
                            CodeBlock ifElseIfBlock =
                                    new CodeBlock(
                                            TranslateConsts.IF_ELSEIF_STATEMENT);
                            ifElseIfBlock.setParentBlock(parentBlock);
                            blockStack.push(ifElseIfBlock);
                        }
                    } else if (elseBrace.getText().equalsIgnoreCase("{")) { //$NON-NLS-1$
                        if (lineOutput.toString().trim().equals("ELSEIF")) { //$NON-NLS-1$
                            parentBlock
                                    .setBlockName(TranslateConsts.IF_ELSE_STATEMENT);
                            parentBlock.incrementChildBlockCount();
                        } else {
                            CodeBlock ifElseBlock =
                                    new CodeBlock(
                                            TranslateConsts.IF_ELSE_STATEMENT);
                            ifElseBlock.setParentBlock(parentBlock);
                            blockStack.push(ifElseBlock);
                        }
                    } else {
                        if (lineOutput.toString().trim().equals("ELSEIF")) { //$NON-NLS-1$
                            CodeBlock block = getCurrentCodeBlock(blockStack);
                            block.setBlockName(TranslateConsts.IF_STATEMENT);
                            parentBlock.incrementChildBlockCount();
                        } else {
                            CodeBlock ifBlock =
                                    new CodeBlock(TranslateConsts.IF_STATEMENT);
                            ifBlock.setParentBlock(parentBlock);
                            blockStack.push(ifBlock);
                        }
                    }
                }// end of if(elseBrace!=null){
                else {
                    // no else statement
                    if (lineOutput.toString().trim().equals("ELSEIF")) { //$NON-NLS-1$
                        CodeBlock block = getCurrentCodeBlock(blockStack);
                        block.setBlockName(TranslateConsts.IF_STATEMENT);
                        block.incrementChildBlockCount();
                    } else {
                        CodeBlock ifBlock =
                                new CodeBlock(TranslateConsts.IF_STATEMENT);
                        ifBlock.setParentBlock(parentBlock);
                        blockStack.push(ifBlock);
                    }
                }// end of if(elseBrace!=null) else
            } // end of if(lBrace.getText().equals("{")){
            else {
                // there is no left curly brace....
                int elseBraceType = -1;
                if (elseBrace != null) {
                    elseBraceType = elseBrace.getType();
                }
                if (lineOutput.toString().trim().equals("ELSEIF")) { //$NON-NLS-1$
                    parentBlock
                            .setBlockName(TranslateConsts.IF_ELSEIF_STATEMENT);
                    parentBlock.incrementChildBlockCount();
                } else {
                    String blockName = TranslateConsts.IF_STATEMENT;
                    if (elseBraceType == JScriptTokenTypes.LITERAL_if) {
                        blockName = TranslateConsts.IF_ELSEIF_STATEMENT;
                    }
                    CodeBlock ifBlock = new CodeBlock(blockName);
                    ifBlock.setParentBlock(parentBlock);
                    blockStack.push(ifBlock);
                }
            }// end of if(lBrace.getText().equals("{")){ else
        } // end of if(lBrace!=null){
        else {
            // when the if statement does not have a {
        }// end of if(lBrace!=null){ else

    }

    /**
     * returns whether the line should be commented
     * 
     * @param variableDef
     * @return
     */
    public boolean varDefintionInit(IpeScriptEmitter outParser,
            AST variableDef, Stack blockStack) {
        boolean commentLine = false;
        AST typeSpec = variableDef.getFirstChild().getNextSibling();
        AST varType = typeSpec.getFirstChild();
        List<String> processDestinationList = outParser.getProcessDestination();
        String scriptType = outParser.getScriptType();
        boolean isUnSupport =
                TranslateUtil.isUnSupportedKeyWord(varType.getText(),
                        processDestinationList,
                        scriptType);
        if (varType != null && isUnSupport) {
            commentLine = true;
            AST identifierAST = typeSpec.getNextSibling();
            if (!blockStack.empty()) {
                CodeBlock block = getCurrentCodeBlock(blockStack);
                block.addIdentifierToIgnore(identifierAST.getText());
            }

        }
        List<Integer> methodASTList = new ArrayList<Integer>();
        methodASTList.add(JScriptTokenTypes.METHOD_CALL);
        List<AST> methodCallList =
                ParseUtil.getFirstLevelChildren(variableDef, methodASTList);
        boolean b =
                TranslateUtil.isMethodCallListFromSupportedClass(outParser,
                        methodCallList);
        if (!b) {
            commentLine = true;
        }
        List<Integer> identASTList = new ArrayList<Integer>();
        identASTList.add(JScriptTokenTypes.IDENT);
        List<AST> childList =
                ParseUtil.getFirstLevelChildren(variableDef, identASTList);
        boolean isPresent =
                TranslateUtil.isAnyUnSupportedKeyWordPresent(childList,
                        processDestinationList,
                        scriptType);
        if (isPresent) {
            commentLine = true;
        }
        return commentLine;
    }

    public static String handleNewExpression(AST newAST) {
        String toReturn = null;
        AST className = newAST.getFirstChild();
        int type = className.getType();
        if (type == JScriptTokenTypes.IDENT) {
            String text = className.getText();
            if ("Date".equals(text)) { //$NON-NLS-1$
                AST exprSibling = className.getNextSibling();
                toReturn = handleDateConstructor(exprSibling);
            } else if ("String".equals(text)) { //$NON-NLS-1$
                AST sibling = className.getNextSibling();
                AST expr = sibling.getFirstChild();
                if (expr != null) {
                    AST stringLiteral = expr.getFirstChild();
                    if (stringLiteral.getType() == JScriptTokenTypes.STRING_LITERAL) {
                        toReturn = stringLiteral.getText();
                    }
                }
            } else if ("Boolean".equals(text)) { //$NON-NLS-1$
                AST sibling = className.getNextSibling();
                AST expr = sibling.getFirstChild();
                if (expr != null) {
                    AST stringLiteral = expr.getFirstChild();
                    if (stringLiteral.getType() == JScriptTokenTypes.STRING_LITERAL) {
                        toReturn = removeQuotes(stringLiteral.getText());
                    } else {
                        toReturn = stringLiteral.getText();
                    }
                }
            } else if ("Number".equals(text)) { //$NON-NLS-1$
                AST exprSibling = className.getNextSibling();
                toReturn = handleNumberConstructor(exprSibling);
            }
        }
        return toReturn;
    }

    private static String handleNumberConstructor(AST eList) {
        String toReturn = null;
        AST exprAST = eList.getFirstChild();
        if (exprAST != null) {
            AST identAST = exprAST.getFirstChild();
            int astType = identAST.getType();
            if (astType == JScriptTokenTypes.UNARY_MINUS
                    || astType == JScriptTokenTypes.UNARY_PLUS) {
                toReturn = identAST.getText();
                identAST = identAST.getFirstChild();
            }
            if (identAST.getType() == JScriptTokenTypes.NUM_INT
                    || identAST.getType() == JScriptTokenTypes.NUM_LONG
                    || identAST.getType() == JScriptTokenTypes.NUM_DOUBLE
                    || identAST.getType() == JScriptTokenTypes.NUM_FLOAT) {
                if (toReturn != null) {
                    toReturn = toReturn + identAST.getText();
                } else {
                    toReturn = identAST.getText();
                }
            }
        }
        return toReturn;
    }

    private static String handleDateConstructor(AST eList) {
        String toReturn = null;
        AST exprAST = eList.getFirstChild();
        if (exprAST != null) {
            AST identAST = exprAST.getFirstChild();
            if (identAST.getType() == JScriptTokenTypes.STRING_LITERAL) {
                toReturn = removeQuotes(identAST.getText());
            } else if (identAST.getType() == JScriptTokenTypes.NUM_INT) {
                String year = "00"; //$NON-NLS-1$
                String month = "00"; //$NON-NLS-1$
                String day = "00"; //$NON-NLS-1$
                year = identAST.getText();
                AST monthAST = exprAST.getNextSibling();
                AST monthText = monthAST.getFirstChild();
                if (monthText.getType() == JScriptTokenTypes.NUM_INT) {
                    month = monthText.getText();
                } else {
                    /**
                     * do not want to to create a date constant if it is not a
                     * number
                     */
                    return toReturn;
                }
                AST dayAST = monthAST.getNextSibling();
                AST dayText = dayAST.getFirstChild();
                if (dayText.getType() == JScriptTokenTypes.NUM_INT) {
                    day = dayText.getText();
                } else {
                    /**
                     * do not want to to create a date constant if it is not a
                     * number
                     */
                    return toReturn;
                }
                AST notSupported = dayAST.getNextSibling();
                /**
                 * for the time being we do not support anything other yy/mm/dd
                 * so if any other constructor is used then we will just return
                 * null and which should result in line being commented out.
                 */
                if (notSupported != null) {
                    return toReturn;
                }
                toReturn = year + "/" + month + "/" + day; //$NON-NLS-1$ //$NON-NLS-2$
            }
        }
        if (toReturn != null) {
            toReturn = "!" + toReturn + "!"; //$NON-NLS-1$ //$NON-NLS-2$
        }
        return toReturn;
    }

    public static boolean isProcessDataField(AST leftAST,
            Map<String, IScriptRelevantData> dataFields) {
        int leftType = leftAST.getType();
        String fieldName = null;
        if (leftType == JScriptTokenTypes.IDENT) {
            fieldName = leftAST.getText();
        } else if (leftType == JScriptTokenTypes.INDEX_OP) {
            AST arrNameAST = leftAST.getFirstChild();
            fieldName = arrNameAST.getText();
        } else if (leftType == JScriptTokenTypes.DOT) {
            AST fcAST = leftAST.getFirstChild();
            if (fcAST.getType() == JScriptTokenTypes.IDENT) {
                fieldName = fcAST.getText();
            } else if (fcAST.getType() == JScriptTokenTypes.INDEX_OP) {
                AST arrNameAST = fcAST.getFirstChild();
                fieldName = arrNameAST.getText();
            }
        }
        boolean isField = dataFields.containsKey(fieldName);
        if (!isField) {
            // this is a case for the conversion of a return
            // statement into $RETURN and EXIT().
            if (STR_RETURN.equals(fieldName)) {
                isField = true;
            }
        }
        return isField;
    }

    private static String removeQuotes(String str) {
        boolean bool = str.startsWith("\""); //$NON-NLS-1$
        if (bool) {
            str = str.substring(1);
        }
        bool = str.endsWith("\""); //$NON-NLS-1$
        if (bool) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    private static CodeBlock getCurrentCodeBlock(Stack blockStack) {
        CodeBlock block = (CodeBlock) blockStack.peek();
        return block;

    }

    public static void handleBooleanValue(AST assignAST) {
        List<Integer> booleanASTList = new ArrayList<Integer>();
        booleanASTList.add(JScriptTokenTypes.LITERAL_true);
        booleanASTList.add(JScriptTokenTypes.LITERAL_false);
        List<AST> childASTList =
                ParseUtil.getChildASTList(assignAST, booleanASTList);
        if (childASTList != null && childASTList.size() > 0) {
            for (AST eachAST : childASTList) {
                if (eachAST.getType() == JScriptTokenTypes.LITERAL_true) {
                    eachAST.setText("1"); //$NON-NLS-1$
                } else if (eachAST.getType() == JScriptTokenTypes.LITERAL_false) {
                    eachAST.setText("0"); //$NON-NLS-1$
                }
            }
        }
    }

    public static void handleStringLiteral(AST assignAST) {
        List<Integer> stringASTList = new ArrayList<Integer>();
        stringASTList.add(JScriptTokenTypes.STRING_LITERAL);
        List<AST> childASTList =
                ParseUtil.getChildASTList(assignAST, stringASTList);
        if (childASTList != null && childASTList.size() > 0) {
            for (AST eachAST : childASTList) {
                String astText = eachAST.getText();
                boolean startsWith = astText.startsWith("'"); //$NON-NLS-1$
                if (startsWith) {
                    String midString =
                            astText.substring(1, astText.length() - 1);
                    midString = midString.replaceAll("\"", "'"); //$NON-NLS-1$ //$NON-NLS-2$
                    String toReturn = "\"" + midString + "\""; //$NON-NLS-1$ //$NON-NLS-2$
                    eachAST.setText(toReturn);
                }
            }
        }
    }

    public static void handleDateTimeDotAST(AST expressionAST,
            IpeScriptEmitter outTreeParser) {
        List<Integer> tokenList = new ArrayList<Integer>();
        tokenList.add(JScriptTokenTypes.DOT);
        List<AST> dotASTList =
                ParseUtil.getChildASTList(expressionAST, tokenList);
        ISymbolTable symbolTable = outTreeParser.getSymbolTable();
        if (dotASTList != null && dotASTList.size() > 0) {
            for (AST eachAST : dotASTList) {
                AST lhsAST = eachAST.getFirstChild();
                String processVarName = ""; //$NON-NLS-1$
                if (lhsAST.getType() == JScriptTokenTypes.IDENT) {
                    processVarName = lhsAST.getText();
                } else if (lhsAST.getType() == JScriptTokenTypes.INDEX_OP) {
                    AST fcAST = lhsAST.getFirstChild();
                    processVarName = fcAST.getText();
                } else {
                    continue;
                }
                boolean isProcessData =
                        symbolTable.isScriptRelevantData(processVarName);
                if (isProcessData) {
                    IScriptRelevantData processDataType =
                            symbolTable
                                    .getScriptRelevantDataType(processVarName);
                    boolean isDotAllowed =
                            Utility.isDotOperatorAllowed(processDataType);
                    if (isDotAllowed) {
                        if (processDataType != null
                                && processDataType.getType() != null
                                && processDataType.getType()
                                        .equals(ProcessJsConsts.DATETIME)) {
                            AST nextSibling = lhsAST.getNextSibling();
                            if (nextSibling != null) {
                                String nsText = nextSibling.getText();
                                if (nsText.equals(ProcessJsConsts.DATE)
                                        || nsText.equals(ProcessJsConsts.TIME)) {
                                    massageDotAST(eachAST, outTreeParser);
                                }
                            }

                        }
                    }
                }
            }
        }
    }

    private static void massageDotAST(AST dotAST, IpeScriptEmitter outTreeParser) {
        AST fcAST = dotAST.getFirstChild();
        Map<String, String> oldNewProcessDataMap =
                outTreeParser.getOldNewProcessDataMap();
        if (fcAST.getType() == JScriptTokenTypes.IDENT) {
            String processVarName = fcAST.getText();
            String processVarNewName = oldNewProcessDataMap.get(processVarName);
            if (processVarNewName == null) {
                processVarNewName = processVarName;
            }
            AST nsAST = fcAST.getNextSibling();
            String nsText = nsAST.getText();
            String suffix = ""; //$NON-NLS-1$
            if (nsText.equals(ProcessJsConsts.DATE)) {
                suffix = "_D"; //$NON-NLS-1$
            } else if (nsText.equals(ProcessJsConsts.TIME)) {
                suffix = "_T"; //$NON-NLS-1$
            }
            String newIdentName = processVarNewName + suffix;
            dotAST.setType(JScriptTokenTypes.IDENT);
            dotAST.setText(newIdentName);
        } else if (fcAST.getType() == JScriptTokenTypes.INDEX_OP) {
            AST arrNameAST = fcAST.getFirstChild();
            AST indexExprAST = arrNameAST.getNextSibling();
            String processVarName = arrNameAST.getText();
            AST nextSibling = fcAST.getNextSibling();
            String nsText = nextSibling.getText();
            String processVarNewName = oldNewProcessDataMap.get(processVarName);
            if (processVarNewName == null) {
                processVarNewName = processVarName;
            }
            String suffix = ""; //$NON-NLS-1$
            if (nsText.equals(ProcessJsConsts.DATE)) {
                suffix = "_D"; //$NON-NLS-1$
            } else if (nsText.equals(ProcessJsConsts.TIME)) {
                suffix = "_T"; //$NON-NLS-1$
            }
            dotAST.setType(JScriptTokenTypes.INDEX_OP);
            String newArrName = processVarNewName + suffix;
            ASTFactory factory = new ASTFactory();
            AST newArrNameAST =
                    factory.create(JScriptTokenTypes.IDENT, newArrName);
            dotAST.setFirstChild(newArrNameAST);
            newArrNameAST.setNextSibling(indexExprAST);
        }

    }

    public static String getTranslatedName(String oldName,
            Map<String, String> oldNewProcessDataMap) {
        String newName = oldNewProcessDataMap.get(oldName);
        if (newName == null) {
            newName = oldName;
        }
        return newName;
    }

    public static boolean ignoreIdentifierName(String varName,
            List<String> identifierList) {
        if (identifierList == null) {
            return false;
        }
        boolean b = identifierList.contains(varName);
        if (b) {
            return true;
        }
        return false;
    }

    /**
     * This method adds the new additional date time data fields to the symbol
     * table.
     * 
     * @param symbolTable
     * @param oldNewDataNameMap
     */
    public static void addDateTimeFields(ISymbolTable symbolTable,
            Map<String, String> oldNewDataNameMap) {
        Map<String, IScriptRelevantData> processDataMap =
                symbolTable.getScriptRelevantDataTypeMap();
        Map<String, IScriptRelevantData> dateTimeMap =
                new HashMap<String, IScriptRelevantData>();
        Set<Entry<String, IScriptRelevantData>> entrySet =
                processDataMap.entrySet();
        for (Entry<String, IScriptRelevantData> eachEntry : entrySet) {
            String varName = eachEntry.getKey();
            IScriptRelevantData srdType = eachEntry.getValue();
            if (srdType != null) {
                String varType = srdType.getType();
                if (varType.equals(ProcessJsConsts.DATETIME)) {
                    String varNewName = oldNewDataNameMap.get(varName);
                    if (varNewName != null) {
                        createDateTimeScriptRelevantData(varNewName,
                                dateTimeMap);
                    }
                }
            }
        }
        if (!dateTimeMap.isEmpty()) {
            processDataMap.putAll(dateTimeMap);
        }
    }

    public static void createDateTimeScriptRelevantData(String varNewName,
            Map<String, IScriptRelevantData> dateTimeMap) {
        IScriptRelevantData dateTypeScriptRelevantData =
                new DefaultScriptRelevantData();
        dateTypeScriptRelevantData.setName(varNewName + "_D");//$NON-NLS-1$
        dateTypeScriptRelevantData.setType(ProcessJsConsts.DATE);
        dateTimeMap.put(dateTypeScriptRelevantData.getName(),
                dateTypeScriptRelevantData);
        IScriptRelevantData timeTypeScriptRelevantData =
                new DefaultScriptRelevantData();
        timeTypeScriptRelevantData.setName(varNewName + "_T");//$NON-NLS-1$
        timeTypeScriptRelevantData.setType(ProcessJsConsts.TIME);
        dateTimeMap.put(timeTypeScriptRelevantData.getName(),
                timeTypeScriptRelevantData);
    }

    // @ComplexTypeChange -->
    public static void handleDateTimeExpression(AST lhsAST, AST rhsAST,
            IpeScriptEmitter emitter) {
        if (lhsAST == null || rhsAST == null) {
            return;
        }
        Map<String, IScriptRelevantData> processDataMap =
                emitter.getSymbolTable().getScriptRelevantDataTypeMap();
        int lhsType = lhsAST.getType();
        String processVarName = ""; //$NON-NLS-1$
        IScriptRelevantData varType = null;
        if (lhsType == JScriptTokenTypes.IDENT) {
            processVarName = lhsAST.getText();
            varType = processDataMap.get(processVarName);
        } else if (lhsType == JScriptTokenTypes.INDEX_OP) {
            AST fcAST = lhsAST.getFirstChild();
            processVarName = fcAST.getText();
            varType = processDataMap.get(processVarName);
        }
        if (varType != null) {
            String varTypeType = varType.getType();
            if (varTypeType != null && varTypeType.equals(ProcessJsConsts.DATE)) {
                int rhsType = rhsAST.getType();
                if (rhsType == JScriptTokenTypes.STRING_LITERAL) {
                    rhsAST.setText("!" + massagePassedString(rhsAST.getText()) //$NON-NLS-1$
                            + "!"); //$NON-NLS-1$
                }
            } else if (varTypeType != null
                    && varTypeType.equals(ProcessJsConsts.TIME)) {
                int rhsType = rhsAST.getType();
                if (rhsType == JScriptTokenTypes.STRING_LITERAL) {
                    rhsAST.setText("#" + massagePassedString(rhsAST.getText()) //$NON-NLS-1$
                            + "#"); //$NON-NLS-1$
                }
            }
        }

    }

    /**
     * Some strParamterType starts with double quotes, so removing them.
     * 
     * @param strParameterType
     * @return
     */
    public static String massagePassedString(String strParameterType) {
        String doubleQuotes = "\""; //$NON-NLS-1$
        String singleQuotes = "'"; //$NON-NLS-1$
        if (strParameterType != null
                && strParameterType.startsWith(doubleQuotes)
                && strParameterType.endsWith(doubleQuotes)) {
            strParameterType =
                    strParameterType
                            .substring(1, strParameterType.length() - 1);
            return strParameterType;
        } else if (strParameterType != null
                && strParameterType.startsWith(singleQuotes)
                && strParameterType.endsWith(singleQuotes))
            strParameterType =
                    strParameterType
                            .substring(1, strParameterType.length() - 1);
        return strParameterType;
    }

    /**
     * This method checks whether the passed method call expects date or time
     * params. if yes then it checks whether String literals has been passed as
     * params for thos date or time params. if yes, the it will convert the
     * String literal into the expected formats for date and time.
     * 
     * @param outParser
     * @param methodCallAst
     */
    public static void handleMethodParams(IpeScriptTranslator outParser,
            AST methodCallAst) {
        AST dotAST = methodCallAst.getFirstChild();
        AST classNameAST = dotAST.getFirstChild();
        String className = classNameAST.getText();
        AST methodNameAST = classNameAST.getNextSibling();
        String methodName = methodNameAST.getText();
        AST eListAST = dotAST.getNextSibling();
        String processDestination =
                Consts.IPM_DEST_FOR_EXPLICIT_JSCLASS_SUPPORT_OR_TRANSLATION;
        String scriptType = outParser.getScriptType();
        List<JsClassDefinitionReader> classDefinitionReaders =
                Utility.getClassDefintionReader(processDestination, scriptType);
        JsClass jsClass = null;
        if (classDefinitionReaders != null && !classDefinitionReaders.isEmpty()) {
            for (JsClassDefinitionReader jsClassDefinitionReader : classDefinitionReaders) {
                if (jsClassDefinitionReader != null
                        && jsClassDefinitionReader.getJsClass(className) != null) {
                    jsClass = jsClassDefinitionReader.getJsClass(className);
                    break;
                }
            }
        }
        if (jsClass == null) {
            return;
        }
        List<JsMethod> methodList = jsClass.getMethodList(methodName);
        JsMethod matchingMethod =
                getMethodWithRequiredParam(methodList, eListAST);
        if (matchingMethod == null) {
            return;
        }
        List<AST> paramASTList = convertElistIntoParamList(eListAST);
        List<JsMethodParam> paramTypeList = matchingMethod.getParameterType();
        int index = 0;
        for (JsMethodParam param : paramTypeList) {
            AST ast = paramASTList.get(index);
            if (param.getType().equals(ProcessJsConsts.DATE)) {
                AST fcAST = ast.getFirstChild();
                if (fcAST.getType() == JScriptTokenTypes.STRING_LITERAL) {
                    fcAST.setText("!" + massagePassedString(fcAST.getText()) //$NON-NLS-1$
                            + "!"); //$NON-NLS-1$
                }
            } else if (param.getType().equals(ProcessJsConsts.TIME)) {
                AST fcAST = ast.getFirstChild();
                if (fcAST.getType() == JScriptTokenTypes.STRING_LITERAL) {
                    fcAST.setText("#" + massagePassedString(fcAST.getText()) //$NON-NLS-1$
                            + "#"); //$NON-NLS-1$
                }
            } else if (param.getType().equals(JsConsts.BOOLEAN)) {
                handleBooleanExpression(ast, outParser);
            }
            index++;
        }
    }

    private static final List<String> attributeMethodList =
            new ArrayList<String>();
    static {
        attributeMethodList.add("IPEStarterUtil.GETATTRIBUTE"); //$NON-NLS-1$
        attributeMethodList.add("IPEUserUtil.GETATTRIBUTE"); //$NON-NLS-1$
        attributeMethodList.add("IPEGroupUtil.GETATTRIBUTE"); //$NON-NLS-1$
    }

    public static void translateAttributeMethodCall(
            IpeScriptTranslator outParser, AST methodCallAst) {
        AST dotAST = methodCallAst.getFirstChild();
        AST classNameAST = dotAST.getFirstChild();
        String className = classNameAST.getText();
        AST methodNameAST = classNameAST.getNextSibling();
        String methodName = methodNameAST.getText();
        AST eListAST = dotAST.getNextSibling();
        String combinedName = className + "." + methodName; //$NON-NLS-1$
        boolean contains = attributeMethodList.contains(combinedName);
        if (!contains) {
            return;
        }
        List<AST> paramASTList = convertElistIntoParamList(eListAST);
        ASTFactory factory = new ASTFactory();
        String objName = null;
        if (className.equals("IPEStarterUtil")) { //$NON-NLS-1$
            objName = "SW_STARTER"; //$NON-NLS-1$
        } else if (className.equals("IPEUserUtil")) { //$NON-NLS-1$
            objName = "SW_USER"; //$NON-NLS-1$
        } else if (className.equals("IPEGroupUtil")) { //$NON-NLS-1$
            objName = "SW_GROUP"; //$NON-NLS-1$
        }
        String attributeName = paramASTList.get(0).getFirstChild().getText();
        attributeName = massagePassedString(attributeName);
        AST userAttribute =
                factory.create(JScriptTokenTypes.IDENT, objName
                        + ":" + attributeName); //$NON-NLS-1$
        methodCallAst.setType(JScriptTokenTypes.IDENT);
        methodCallAst.setText(userAttribute.getText());
    }

    private static final List<String> processNameMethodList =
            new ArrayList<String>();
    static {
        processNameMethodList.add("IPEProcessNameUtil.GETPROCESSNAME"); //$NON-NLS-1$        
    }

    public static void translateProcessNameMethodCall(
            IpeScriptTranslator outParser, AST methodCallAst) {
        boolean contains =
                isMethodCallPresent(methodCallAst, processNameMethodList);
        if (!contains) {
            return;
        }
        AST dotAST = methodCallAst.getFirstChild();
        AST eListAST = dotAST.getNextSibling();
        List<AST> paramASTList = convertElistIntoParamList(eListAST);
        ASTFactory factory = new ASTFactory();
        String processName = paramASTList.get(0).getFirstChild().getText();
        processName = massagePassedString(processName);
        processName = "\"" + getiPMProcessName(processName) + "\""; //$NON-NLS-1$ //$NON-NLS-2$
        AST userAttribute =
                factory.create(JScriptTokenTypes.IDENT, processName);
        methodCallAst.setType(JScriptTokenTypes.IDENT);
        methodCallAst.setText(userAttribute.getText());
    }

    private static final List<String> taskNameMethodList =
            new ArrayList<String>();
    static {
        taskNameMethodList.add("IPETaskNameUtil.GETTASKNAME"); //$NON-NLS-1$        
    }

    public static void translateTaskNameMethodCall(
            IpeScriptTranslator outParser, AST methodCallAst) {
        boolean contains =
                isMethodCallPresent(methodCallAst, taskNameMethodList);
        if (!contains) {
            return;
        }
        AST dotAST = methodCallAst.getFirstChild();
        AST eListAST = dotAST.getNextSibling();
        List<AST> paramASTList = convertElistIntoParamList(eListAST);
        ASTFactory factory = new ASTFactory();
        String studioProcessName =
                paramASTList.get(0).getFirstChild().getText();
        studioProcessName = massagePassedString(studioProcessName);
        String studioTaskName = paramASTList.get(1).getFirstChild().getText();
        studioTaskName = massagePassedString(studioTaskName);
        String ipmTaskName =
                getiPMTaskName(studioProcessName,
                        studioTaskName,
                        outParser.getStudioIPMTaskNameMap());
        ipmTaskName = "\"" + ipmTaskName + "\""; //$NON-NLS-1$ //$NON-NLS-2$
        AST userAttribute =
                factory.create(JScriptTokenTypes.IDENT, ipmTaskName);
        methodCallAst.setType(JScriptTokenTypes.IDENT);
        methodCallAst.setText(userAttribute.getText());
    }

    private static boolean isMethodCallPresent(AST methodCallAst,
            List<String> methodCallList) {
        boolean toReturn = false;
        AST dotAST = methodCallAst.getFirstChild();
        AST classNameAST = dotAST.getFirstChild();
        String className = classNameAST.getText();
        AST methodNameAST = classNameAST.getNextSibling();
        String methodName = methodNameAST.getText();
        AST eListAST = dotAST.getNextSibling();
        String combinedName = className + "." + methodName; //$NON-NLS-1$
        boolean contains = methodCallList.contains(combinedName);
        if (contains) {
            toReturn = true;
        }
        return toReturn;
    }

    private static List<AST> convertElistIntoParamList(AST eListAST) {
        List<AST> methodParamList = new ArrayList<AST>();
        AST paramAST = eListAST.getFirstChild();
        methodParamList.add(paramAST);
        while (true) {
            AST nextParamAST = paramAST.getNextSibling();
            if (nextParamAST == null) {
                break;
            }
            methodParamList.add(nextParamAST);
            paramAST = nextParamAST;
        }
        return methodParamList;
    }

    private static JsMethod getMethodWithRequiredParam(
            List<JsMethod> methodList, AST eListAST) {
        JsMethod methodToReturn = null;
        int actualParamCount = eListAST.getNumberOfChildren();
        for (JsMethod method : methodList) {
            int expectedParamCount = method.getParameterType().size();
            if (actualParamCount != expectedParamCount) {
                continue;
            }
            boolean isDateTimeMethodParam = isMethodWithRequiredParam(method);
            if (isDateTimeMethodParam) {
                methodToReturn = method;
            }
        }
        return methodToReturn;
    }

    private static boolean isMethodWithRequiredParam(JsMethod method) {
        List<JsMethodParam> parameterType = method.getParameterType();
        boolean isRequiredTypeParam = false;
        for (JsMethodParam param : parameterType) {
            String paramType = param.getType();
            if (paramType.equals(ProcessJsConsts.DATE)
                    || paramType.equals(ProcessJsConsts.TIME)
                    || paramType.equals(JsConsts.BOOLEAN)) {
                isRequiredTypeParam = true;
                break;
            }
        }
        return isRequiredTypeParam;
    }

    public static void handleBooleanExpression(AST exprAST,
            IpeScriptTranslator outParser) {
        if (exprAST == null) {
            return;
        }
        AST fcAST = exprAST.getFirstChild();
        if (fcAST == null) {
            return;
        }
        if (fcAST.getType() == JScriptTokenTypes.IDENT) {
            String varName = fcAST.getText();
            boolean isBoolean = isBooleanDataField(varName, outParser);
            if (isBoolean) {
                AST identAST = createIdentAST(varName);
                exprAST.setFirstChild(createNotEqualAST(identAST));
            }
        } else if (fcAST.getType() == JScriptTokenTypes.INDEX_OP) {
            AST firstChild = fcAST.getFirstChild();
            String varName = firstChild.getText();
            boolean isBoolean = isBooleanDataField(varName, outParser);
            if (isBoolean) {
                ASTFactory factory = new ASTFactory();
                AST dupFCAST = factory.dupTree(fcAST);
                exprAST.setFirstChild(createNotEqualAST(dupFCAST));
            }
        } else if (fcAST.getType() == JScriptTokenTypes.LITERAL_true
                || fcAST.getType() == JScriptTokenTypes.LITERAL_false) {
            handleBooleanLiterals(exprAST);
        } else if (fcAST.getType() == JScriptTokenTypes.ASSIGN) {
            handleBooleanAssignmentExpr(exprAST, outParser);
        } else {
            handleBooleanExprSubTree(fcAST, outParser);
        }
    }

    private static void handleBooleanAssignmentExpr(AST exprAST,
            IpeScriptTranslator outParser) {
        AST fcAST = exprAST.getFirstChild();
        AST fchildAST = fcAST.getFirstChild();
        String varName = null;
        if (fchildAST.getType() == JScriptTokenTypes.IDENT) {
            varName = fchildAST.getText();
        } else if (fchildAST.getType() == JScriptTokenTypes.INDEX_OP) {
            AST firstChild = fchildAST.getFirstChild();
            varName = firstChild.getText();
        } else {
            // the lhs should be one of ident or array field
            return;
        }
        boolean isBoolean = isBooleanDataField(varName, outParser);
        if (!isBoolean) {
            return;
        }
        AST nsAST = fchildAST.getNextSibling();
        if (ParseUtil.isLeaf(nsAST)) {
            ASTFactory factory = new ASTFactory();
            AST dupFCAST = factory.dupTree(fcAST);
            AST comparisionAST = null;
            if (nsAST.getType() == JScriptTokenTypes.LITERAL_true) {
                comparisionAST = factory.create(JScriptTokenTypes.EQUAL, "=="); //$NON-NLS-1$
            } else {
                comparisionAST =
                        factory.create(JScriptTokenTypes.NOT_EQUAL, "!="); //$NON-NLS-1$
            }
            comparisionAST.setFirstChild(dupFCAST);
            AST numIntAST = factory.create(JScriptTokenTypes.NUM_INT, "1"); //$NON-NLS-1$
            dupFCAST.setNextSibling(numIntAST);
            exprAST.setFirstChild(comparisionAST);
        } else if (nsAST.getType() == JScriptTokenTypes.METHOD_CALL) {
            // case: MyBooleanDataField = IPEEnvironmentUtil.ISWINDOWS()
            ASTFactory factory = new ASTFactory();
            AST comparisionAST =
                    factory.create(JScriptTokenTypes.NOT_EQUAL, "!="); //$NON-NLS-1$
            AST dupAssignAST = factory.dupTree(fcAST);
            comparisionAST.setFirstChild(dupAssignAST);
            AST numIntAST = factory.create(JScriptTokenTypes.NUM_INT, "0"); //$NON-NLS-1$
            dupAssignAST.setNextSibling(numIntAST);
            exprAST.setFirstChild(comparisionAST);
        } else {
            // case: MyBooleanDataField = true||false
            handleBooleanExprSubTree(nsAST, outParser);
            ASTFactory factory = new ASTFactory();
            AST comparisionAST =
                    factory.create(JScriptTokenTypes.NOT_EQUAL, "!="); //$NON-NLS-1$
            AST dupAssignAST = factory.dupTree(fcAST);
            comparisionAST.setFirstChild(dupAssignAST);
            AST numIntAST = factory.create(JScriptTokenTypes.NUM_INT, "0"); //$NON-NLS-1$
            dupAssignAST.setNextSibling(numIntAST);
            exprAST.setFirstChild(comparisionAST);
        }
    }

    private static void handleBooleanLiterals(AST exprAST) {
        AST fcAST = exprAST.getFirstChild();
        ASTFactory factory = new ASTFactory();
        AST lhsAST = factory.create(JScriptTokenTypes.NUM_INT, "1"); //$NON-NLS-1$
        AST rhsAST = getBooleanRhsAst(fcAST);
        AST equalAST = factory.create(JScriptTokenTypes.EQUAL, "=="); //$NON-NLS-1$
        equalAST.setFirstChild(lhsAST);
        lhsAST.setNextSibling(rhsAST);
        exprAST.setFirstChild(equalAST);
    }

    private static boolean isLogicalOperator(AST fcAST) {
        if (fcAST.getType() == JScriptTokenTypes.BOR
                || fcAST.getType() == JScriptTokenTypes.LOR
                || fcAST.getType() == JScriptTokenTypes.BAND
                || fcAST.getType() == JScriptTokenTypes.LAND) {
            return true;
        }
        return false;
    }

    private static void handleBooleanExprSubTree(AST fcAST,
            IpeScriptTranslator outParser) {
        if (fcAST == null) {
            return;
        }
        if (isLogicalOperator(fcAST)) {
            AST lhsAST = fcAST.getFirstChild();
            if (lhsAST == null) {
                return;
            }
            if (isLogicalOperator(lhsAST)) {
                handleBooleanExprSubTree(lhsAST, outParser);
            }
            if (lhsAST.getType() == JScriptTokenTypes.IDENT) {
                String varName = lhsAST.getText();
                boolean isBoolean = isBooleanDataField(varName, outParser);
                if (isBoolean) {
                    AST rhsAST = lhsAST.getNextSibling();
                    AST identAST = createIdentAST(varName);
                    AST notEqualAST = createNotEqualAST(identAST);
                    fcAST.setFirstChild(notEqualAST);
                    notEqualAST.setNextSibling(rhsAST);
                }
            } else if (lhsAST.getType() == JScriptTokenTypes.INDEX_OP) {
                AST firstChild = lhsAST.getFirstChild();
                String varName = firstChild.getText();
                boolean isBoolean = isBooleanDataField(varName, outParser);
                if (isBoolean) {
                    AST rhsAST = lhsAST.getNextSibling();
                    ASTFactory factory = new ASTFactory();
                    AST dupLHS = factory.dupTree(lhsAST);
                    AST notEqualAST = createNotEqualAST(dupLHS);
                    fcAST.setFirstChild(notEqualAST);
                    notEqualAST.setNextSibling(rhsAST);
                }
            } else if (lhsAST.getType() == JScriptTokenTypes.LITERAL_true
                    || lhsAST.getType() == JScriptTokenTypes.LITERAL_false) {
                ASTFactory factory = new ASTFactory();
                AST lAST = factory.create(JScriptTokenTypes.NUM_INT, "1"); //$NON-NLS-1$
                AST rAST = getBooleanRhsAst(lhsAST);
                lAST.setNextSibling(rAST);
                lhsAST.setType(JScriptTokenTypes.EQUAL);
                lhsAST.setText("=="); //$NON-NLS-1$
                lhsAST.setFirstChild(lAST);
            }
            lhsAST = fcAST.getFirstChild();
            AST rhsAST = lhsAST.getNextSibling();
            if (rhsAST == null) {
                return;
            }
            if (isLogicalOperator(rhsAST)) {
                handleBooleanExprSubTree(rhsAST, outParser);
            }
            if (rhsAST.getType() == JScriptTokenTypes.IDENT) {
                String varName = rhsAST.getText();
                boolean isBoolean = isBooleanDataField(varName, outParser);
                if (isBoolean) {
                    AST identAST = createIdentAST(varName);
                    AST notEqualAST = createNotEqualAST(identAST);
                    lhsAST.setNextSibling(notEqualAST);
                }
            } else if (rhsAST.getType() == JScriptTokenTypes.INDEX_OP) {
                AST firstChild = rhsAST.getFirstChild();
                String varName = firstChild.getText();
                boolean isBoolean = isBooleanDataField(varName, outParser);
                if (isBoolean) {
                    ASTFactory factory = new ASTFactory();
                    AST dupRHS = factory.dupTree(rhsAST);
                    AST notEqualAST = createNotEqualAST(dupRHS);
                    lhsAST.setNextSibling(notEqualAST);
                }
            } else if (rhsAST.getType() == JScriptTokenTypes.LITERAL_true
                    || rhsAST.getType() == JScriptTokenTypes.LITERAL_false) {
                ASTFactory factory = new ASTFactory();
                AST lAST = factory.create(JScriptTokenTypes.NUM_INT, "1"); //$NON-NLS-1$
                AST rAST = getBooleanRhsAst(rhsAST);
                lAST.setNextSibling(rAST);
                rhsAST.setType(JScriptTokenTypes.EQUAL);
                rhsAST.setText("=="); //$NON-NLS-1$
                rhsAST.setFirstChild(lAST);
            }
        }
    }

    private static AST getBooleanRhsAst(AST ast) {
        ASTFactory factory = new ASTFactory();
        AST rAST = null;
        if (ast.getType() == JScriptTokenTypes.LITERAL_true) {
            rAST = factory.create(JScriptTokenTypes.NUM_INT, "1"); //$NON-NLS-1$
        } else {
            rAST = factory.create(JScriptTokenTypes.NUM_INT, "0"); //$NON-NLS-1$
        }
        return rAST;
    }

    private static AST createIdentAST(String varName) {
        ASTFactory factory = new ASTFactory();
        AST identAST = factory.create(JScriptTokenTypes.IDENT);
        identAST.setText(varName);
        return identAST;
    }

    private static AST createNotEqualAST(AST lhsAST) {
        ASTFactory factory = new ASTFactory();
        AST notEqualAST = factory.create(JScriptTokenTypes.NOT_EQUAL);
        AST numAST = factory.create(JScriptTokenTypes.NUM_INT);
        numAST.setText("0"); //$NON-NLS-1$
        lhsAST.setNextSibling(numAST);
        notEqualAST.setFirstChild(lhsAST);
        return notEqualAST;
    }

    private static boolean isBooleanDataField(String varName,
            IpeScriptTranslator outParser) {
        ISymbolTable symbolTable = outParser.getSymbolTable();
        if (symbolTable == null) {
            return false;
        }
        IScriptRelevantData processDataType =
                symbolTable.getScriptRelevantDataType(varName);
        if (processDataType != null) {
            String processDataTypeType = processDataType.getType();
            if (processDataTypeType != null
                    && (processDataTypeType.equals(JsConsts.BOOLEAN))) {
                return true;
            }
        }
        return false;
    }

    public static void addDummyBooleanExpression(AST forConditionAST,
            IpeScriptTranslator parser) {
        AST fcAST = forConditionAST.getFirstChild();
        if (fcAST != null) {
            return;
        }
        forConditionAST.setFirstChild(createEqualAST());
    }

    private static AST createEqualAST() {
        ASTFactory factory = new ASTFactory();
        AST equalAST = factory.create(JScriptTokenTypes.EQUAL);
        AST identAST = factory.create(JScriptTokenTypes.NUM_INT);
        identAST.setText("1"); //$NON-NLS-1$
        AST numAST = factory.create(JScriptTokenTypes.NUM_INT);
        numAST.setText("1"); //$NON-NLS-1$
        identAST.setNextSibling(numAST);
        equalAST.setFirstChild(identAST);
        AST exprAST = factory.create(JScriptTokenTypes.EXPR);
        exprAST.setFirstChild(equalAST);
        return exprAST;
    }

    // TODO: once we move the translation stuff to ipm feature
    // then we should strat using
    // com.tibco.xpd.ipm.export.conversion.TransformScript.getiPMName(String)
    // and delete this
    private static final String ALPHA_NUMERIC_REGEX = "[^a-zA-Z0-9]"; //$NON-NLS-1$

    private static final int LENGTH_8 = 8;

    public static String getiPMProcessName(String name) {
        if (name == null) {
            name = ""; //$NON-NLS-1$
        }
        String tmp = name.replaceAll(ALPHA_NUMERIC_REGEX, "") //$NON-NLS-1$
                .toUpperCase();
        return tmp.substring(0, Math.min(LENGTH_8, tmp.length()));
    }

    public static String getiPMTaskName(String processName, String taskName,
            Map<String, Map<String, String>> taskNameMap) {
        if (taskName == null || taskNameMap == null) {
            taskName = ""; //$NON-NLS-1$
        }
        Map<String, String> actNameMap = taskNameMap.get(processName);
        if (actNameMap == null) {
            return taskName;
        }
        String tmp = actNameMap.get(taskName);
        if (tmp == null) {
            tmp = taskName;
        }
        return tmp;
    }

    /**
     * This method will change the AST from variable definition to expression or
     * varaible assignment. This method is needed as we add dummy node of
     * variable definition in the script parser.
     * 
     * @param varDeclaration
     */
    public static void massageVariableDefinition(AST varDeclaration) {
        if (varDeclaration == null) {
            return;
        }
        AST varTypeAST = varDeclaration.getFirstChild();
        if (varTypeAST == null) {
            return;
        }
        if (varTypeAST.getType() != JScriptTokenTypes.TYPE
                || "HCvar".equals(varTypeAST.getText())) { //$NON-NLS-1$
            return;
        }
        AST varNameAST = varTypeAST.getNextSibling();
        AST assignAST = varNameAST.getNextSibling();
        if (assignAST == null
                || assignAST.getType() != JScriptTokenTypes.ASSIGN) {
            return;
        }
        String varName = varNameAST.getText();
        ASTFactory astFactory = new ASTFactory();
        AST newVarNameAST = astFactory.create(JScriptTokenTypes.IDENT, varName);
        // till now we have seen that for variable defintiion,
        // there is an expr node and then the child is the actual data assigned.
        // so, getting rid
        // of the expr node
        AST exprAST = assignAST.getFirstChild();
        newVarNameAST.setNextSibling(exprAST.getFirstChild());
        assignAST.setFirstChild(newVarNameAST);
        varDeclaration.setFirstChild(assignAST);
        varDeclaration.setType(JScriptTokenTypes.EXPR);
        varDeclaration.setText("EXPR"); //$NON-NLS-1$       
    }

    /**
     * This method converts the compound operation assignment into normal
     * operator and assignment. This method converts the PlusAssign AST into
     * normal Plus AST.
     * 
     * @param compoundAssignAST
     */
    public static void translateCompoundOperatorAssignment(AST compoundAssignAST) {
        int tokenId = -1;
        String strToken = ""; //$NON-NLS-1$
        if (JScriptTokenTypes.PLUS_ASSIGN == compoundAssignAST.getType()) {
            tokenId = JScriptTokenTypes.PLUS;
            strToken = " + "; //$NON-NLS-1$
        } else if (JScriptTokenTypes.MINUS_ASSIGN == compoundAssignAST
                .getType()) {
            tokenId = JScriptTokenTypes.MINUS;
            strToken = " - "; //$NON-NLS-1$
        } else if (JScriptTokenTypes.STAR_ASSIGN == compoundAssignAST.getType()) {
            tokenId = JScriptTokenTypes.STAR;
            strToken = " * "; //$NON-NLS-1$
        } else if (JScriptTokenTypes.DIV_ASSIGN == compoundAssignAST.getType()) {
            tokenId = JScriptTokenTypes.DIV;
            strToken = " / "; //$NON-NLS-1$
        } else if (JScriptTokenTypes.BAND_ASSIGN == compoundAssignAST.getType()) {
            tokenId = JScriptTokenTypes.BAND;
            strToken = " AND "; //$NON-NLS-1$
        } else if (JScriptTokenTypes.BXOR_ASSIGN == compoundAssignAST.getType()) {
            tokenId = JScriptTokenTypes.BXOR;
            strToken = " ^ "; //$NON-NLS-1$
        } else if (JScriptTokenTypes.BOR_ASSIGN == compoundAssignAST.getType()) {
            tokenId = JScriptTokenTypes.BOR;
            strToken = " OR "; //$NON-NLS-1$
        } else {
            return;
        }
        AST varAST = compoundAssignAST.getFirstChild();
        AST varSibling = varAST.getNextSibling();
        ASTFactory astFactory = new ASTFactory();
        AST oprAST = astFactory.create(tokenId, strToken);
        AST dupVarAST = astFactory.dup(varAST);
        oprAST.setFirstChild(dupVarAST);
        dupVarAST.setNextSibling(varSibling);
        varAST.setNextSibling(oprAST);
        compoundAssignAST.setType(JScriptTokenTypes.ASSIGN);
        compoundAssignAST.setText("="); //$NON-NLS-1$        
    }

    /**
     * This method will convert a Return statement into ipm equivalent return
     * "string"; into $RETURN := "string" EXIT()
     * 
     * @param returnAST
     */
    public static void translateReturnStatement(AST returnAST,
            String scriptContext) {
        boolean isSupported = isReturnStatementSupported(scriptContext);
        if (!isSupported) {
            return;
        }
        AST firstChild = returnAST.getFirstChild();
        if (firstChild == null) {
            // returnAST.setText(STR_RETURN);
            return;
        }
        // AST nextSibling = returnAST.getNextSibling();
        ASTFactory astFactory = new ASTFactory();
        if (JScriptTokenTypes.EXPR == firstChild.getType()) {
            AST exprChild = firstChild.getFirstChild();
            AST assignAST = astFactory.create(JScriptTokenTypes.ASSIGN, "="); //$NON-NLS-1$
            AST idenAST =
                    astFactory.create(JScriptTokenTypes.IDENT, STR_RETURN);
            returnAST.setType(JScriptTokenTypes.EXPR);
            returnAST.setText(STR_EXPR);
            returnAST.setFirstChild(assignAST);
            assignAST.setFirstChild(idenAST);
            idenAST.setNextSibling(exprChild);
        }
        // creating AST for exit method
        AST exitMethodExpr =
                astFactory.create(JScriptTokenTypes.EXPR, STR_EXPR);
        AST methodCallAST = astFactory.create(JScriptTokenTypes.METHOD_CALL);
        exitMethodExpr.setFirstChild(methodCallAST);
        AST identAST = astFactory.create(JScriptTokenTypes.IDENT, STR_EXIT);
        methodCallAST.setFirstChild(identAST);
        AST eListAST = astFactory.create(JScriptTokenTypes.ELIST);
        identAST.setNextSibling(eListAST);
        returnAST.setNextSibling(exitMethodExpr);
    }

    /**
     * Returns true for the context which supports return statement i.e. $RETURN
     * = "string";
     * 
     * @param contextType
     * @return
     */
    private static boolean isReturnStatementSupported(String contextType) {
        boolean toReturn = false;
        if (USER_OPEN_SCRIPT.equals(contextType)
                || USER_CLOSE_SCRIPT.equals(contextType)
                || USER_SUBMIT_SCRIPT.equals(contextType)
                || OUTPUT_MAPPING_SCRIPT.equals(contextType)) {
            toReturn = true;
        }
        return toReturn;
    }
}
