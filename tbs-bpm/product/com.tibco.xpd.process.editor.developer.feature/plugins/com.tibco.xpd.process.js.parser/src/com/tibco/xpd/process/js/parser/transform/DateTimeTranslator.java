package com.tibco.xpd.process.js.parser.transform;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import antlr.collections.AST;

import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.process.js.parser.antlr.JScriptTreeParser;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.parser.antlr.JScriptEmitter;
import com.tibco.xpd.script.parser.antlr.JScriptLexer;
import com.tibco.xpd.script.parser.antlr.JScriptParser;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
import com.tibco.xpd.script.parser.util.ParseUtil;
import com.tibco.xpd.script.parser.validator.ISymbolTable;

public class DateTimeTranslator {

    public static void translateTree(JScriptTreeParser treeParser,
            List<String> supportedContexts) {
        printScript(treeParser);
        AST treeAST = treeParser.getAST();
        if (treeAST == null) {
            return;
        }
        while (true) {
            doWorkOnEachStatement(treeAST, treeParser, supportedContexts);
            AST nsAST = treeAST.getNextSibling();
            if (nsAST != null) {
                treeAST = nsAST;
            } else {
                break;
            }
        }
        printScript(treeParser);
    }

    private static void doWorkOnEachStatement(AST statAST,
            JScriptTreeParser treeParser, List<String> supportedContexts) {
        int statType = statAST.getType();
        if (statType == JScriptTokenTypes.EXPR) {
            doWorkOnExprStatement(statAST, treeParser, supportedContexts);
        }
    }

    private static void doWorkOnExprStatement(AST exprStat,
            JScriptTreeParser treeParser, List<String> supportedContexts) {
        AST fcAST = exprStat.getFirstChild();
        if (fcAST == null) {
            return;
        }
        int fcType = fcAST.getType();
        if (fcType == JScriptTokenTypes.ASSIGN) {
            doWorkOnAssignmentExprStatement(exprStat,
                    treeParser,
                    supportedContexts);
        }
    }

    private static void doWorkOnAssignmentExprStatement(AST exprStat,
            JScriptTreeParser treeParser, List<String> supportedContexts) {
        AST assignAST = exprStat.getFirstChild();
        String inOutMappingMode = getMappingMode(assignAST, supportedContexts);
        // handle lhs
        AST lhsAST = assignAST.getFirstChild();
        AST rhsAST = lhsAST.getNextSibling();
        // in means going to iPE server
        if (inOutMappingMode.equalsIgnoreCase(MAPPING_IN)) {
            List<AST> qualifiedDotASTList =
                    getQualifiedDotASTList(rhsAST, supportedContexts);
            if (qualifiedDotASTList != null) {
                for (AST dotAST : qualifiedDotASTList) {
                    boolean isDateConstructorAdded =
                            addNewDateConstructorAST(exprStat,
                                    dotAST,
                                    assignAST,
                                    treeParser,
                                    supportedContexts);
                    if (isDateConstructorAdded) {
                        break;
                    }
                }
            }
        } else if (inOutMappingMode.equals(MAPPING_OUT)) {
            // handle rhs
            List<AST> qualifiedDotASTList =
                    getQualifiedDotASTList(lhsAST, supportedContexts);
            if (qualifiedDotASTList != null) {
                for (AST dotAST : qualifiedDotASTList) {
                    boolean isAdded =
                            addNewDateTimeConstructorAST(dotAST,
                                    exprStat,
                                    treeParser,
                                    supportedContexts);
                    if (isAdded) {
                        break;
                    }
                }
            }
        }
    }

    private static final String MAPPING_IN = "IN"; //$NON-NLS-1$

    private static final String MAPPING_OUT = "OUT"; //$NON-NLS-1$

    private static String getMappingMode(AST assignAST,
            List<String> supportedContexts) {
        String mappingMode = MAPPING_IN;
        AST lhsAST = assignAST.getFirstChild();
        List<AST> qualifiedDotASTList =
                getQualifiedDotASTList(lhsAST, supportedContexts);
        if (qualifiedDotASTList != null && !qualifiedDotASTList.isEmpty()) {
            mappingMode = MAPPING_OUT;
        }
        return mappingMode;
    }

    private static boolean addNewDateConstructorAST(AST exprStat, AST dotAST,
            AST assignAST, JScriptTreeParser treeParser,
            List<String> supportedContexts) {
        boolean astChanged = false;
        AST fcAST = dotAST.getFirstChild();
        String fcText = fcAST.getText();
        boolean supportedClass =
                DtTranslateUtil.isSupportedClass(fcText,
                        treeParser.getProcessDestinationList(),
                        treeParser.getScriptType());
        boolean supportedContext =
                DtTranslateUtil.isSupportedContext(fcText, supportedContexts);
        if (!supportedClass && supportedContext) {
            AST nextSibling = fcAST.getNextSibling();
            String nsText = nextSibling.getText();
            boolean isDateTime = isDateTimeProcesVar(nsText, treeParser);
            if (isDateTime) {
                String studioDate = "process." + nsText + "_D"; //$NON-NLS-1$ //$NON-NLS-2$
                String studioTime = "process." + nsText + "_T"; //$NON-NLS-1$ //$NON-NLS-2$
                AST newDateConstructor =
                        deserialiseDateConstructor(studioDate, studioTime);
                AST lhsAST = assignAST.getFirstChild();
                lhsAST.setNextSibling(newDateConstructor.getFirstChild());
                astChanged = true;
            } else {
                boolean isDateTimeArray =
                        isDateTimeArrayProcesVar(nsText, treeParser);
                if (isDateTimeArray) {
                    String studioDate = "process." + nsText + "_D"; //$NON-NLS-1$ //$NON-NLS-2$
                    String studioTime = "process." + nsText + "_T"; //$NON-NLS-1$ //$NON-NLS-2$
                    AST lhsAST = assignAST.getFirstChild();
                    AST newDateConstructor =
                            deserialiseDateConstructorInForLoop(lhsAST,
                                    studioDate,
                                    studioTime);
                    exprStat.setType(JScriptTokenTypes.LITERAL_for);
                    exprStat.setText("for"); //$NON-NLS-1$
                    exprStat.setFirstChild(newDateConstructor.getFirstChild());
                    astChanged = true;
                }
            }
        }
        return astChanged;
    }

    private static boolean addNewDateTimeConstructorAST(AST dotAST,
            AST exprStat, JScriptTreeParser treeParser,
            List<String> supportedContexts) {
        AST assignAST = exprStat.getFirstChild();
        AST assignLHSAST = assignAST.getFirstChild();
        AST assignRHSAST = assignLHSAST.getNextSibling();
        String strRHS = parseRHS(assignRHSAST);
        boolean astChanged = false;
        AST fcAST = dotAST.getFirstChild();
        String fcText = fcAST.getText();
        boolean supportedClass =
                DtTranslateUtil.isSupportedClass(fcText,
                        treeParser.getProcessDestinationList(),
                        treeParser.getScriptType());
        boolean supportedContext =
                DtTranslateUtil.isSupportedContext(fcText, supportedContexts);
        if (!supportedClass && supportedContext) {
            AST nextSibling = fcAST.getNextSibling();
            String nsText = nextSibling.getText();
            String studioDate = "process." + nsText + "_D"; //$NON-NLS-1$ //$NON-NLS-2$
            String studioTime = "process." + nsText + "_T"; //$NON-NLS-1$ //$NON-NLS-2$
            boolean isDateTime = isDateTimeProcesVar(nsText, treeParser);
            if (isDateTime) {
                AST newDateConstructor = deserialiseJustDateConstructor(strRHS);
                nextSibling.setText(nextSibling.getText() + "_D"); //$NON-NLS-1$
                assignLHSAST.setNextSibling(newDateConstructor.getFirstChild());
                AST previousNSAst = exprStat.getNextSibling();
                AST newTimeConstructorAST =
                        deserialiseJustTimeConstructor(studioTime, strRHS);
                exprStat.setNextSibling(newTimeConstructorAST);
                if (previousNSAst != null) {
                    newTimeConstructorAST.setNextSibling(previousNSAst);
                }
                astChanged = true;
            } else {
                boolean isDateTimeArray =
                        isDateTimeArrayProcesVar(nsText, treeParser);
                if (isDateTimeArray) {
                    AST newAST =
                            deserialiseDateTimeConstructorInForLoop(assignRHSAST,
                                    studioDate,
                                    studioTime);
                    exprStat.setType(JScriptTokenTypes.LITERAL_for);
                    exprStat.setText("for"); //$NON-NLS-1$
                    exprStat.setFirstChild(newAST.getFirstChild());
                    astChanged = true;
                }
            }
        }
        return astChanged;
    }

    private static boolean isDateTimeProcesVar(String nsText,
            JScriptTreeParser treeParser) {
        Map<String, String> oldNewProcessDataMap =
                treeParser.getOldNewProcessDataMap();
        ISymbolTable symbolTable = treeParser.getSymbolTable();
        boolean contains = oldNewProcessDataMap.containsValue(nsText);
        String oldName = ""; //$NON-NLS-1$
        if (contains) {
            Set<Entry<String, String>> entrySet =
                    oldNewProcessDataMap.entrySet();
            for (Entry<String, String> eachEntry : entrySet) {
                String eachValue = eachEntry.getValue();
                if (eachValue.equals(nsText)) {
                    oldName = eachEntry.getKey();
                    break;
                }
            }
            IScriptRelevantData processDataType =
                    symbolTable.getScriptRelevantDataType(oldName);
            if (processDataType != null) {
                String processDataTypeName = processDataType.getType();
                if (!processDataType.isArray() && processDataTypeName != null
                        && processDataTypeName.equals(ProcessJsConsts.DATETIME)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isDateTimeArrayProcesVar(String nsText,
            JScriptTreeParser treeParser) {
        Map<String, String> oldNewProcessDataMap =
                treeParser.getOldNewProcessDataMap();
        ISymbolTable symbolTable = treeParser.getSymbolTable();
        boolean contains = oldNewProcessDataMap.containsValue(nsText);
        String oldName = ""; //$NON-NLS-1$
        if (contains) {
            Set<Entry<String, String>> entrySet =
                    oldNewProcessDataMap.entrySet();
            for (Entry<String, String> eachEntry : entrySet) {
                String eachValue = eachEntry.getValue();
                if (eachValue.equals(nsText)) {
                    oldName = eachEntry.getKey();
                    break;
                }
            }

            IScriptRelevantData processDataType =
                    symbolTable.getScriptRelevantDataType(oldName);

            /*
             * SID SIA-106: Do not dispose the symbol table here - we didn't
             * create it here and something else might try and use it again
             * after us!!
             */
            // if (symbolTable instanceof SymbolTable) {
            // ((SymbolTable) symbolTable).dispose();
            // }
            // symbolTable = null;

            if (processDataType != null) {
                String processDataTypeName = processDataType.getType();
                if (processDataType.isArray() && processDataTypeName != null
                        && processDataTypeName.equals(ProcessJsConsts.DATETIME)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 
     * studioDate = process.myDateTime_D studioTime = process.myDateTime_T
     * 
     * @param studioDate
     * @param studioTime
     * @return
     */
    private static AST deserialiseDateConstructor(String studioDate,
            String studioTime) {
        String fullDateExpression = "new Date(" + studioDate //$NON-NLS-1$
                + ".getTime() + " //$NON-NLS-1$
                + studioTime + ".getTime());"; //$NON-NLS-1$
        AST ast = deserialiseExpression(fullDateExpression);
        return ast;
    }

    /**
     * for(var index=0;index<process.MYDT_D.length;index++){
     * parameter.dt[index]=new Date
     * (process.MYDT_D[index].getTime()+process.MYDT_T[index].getTime()); }
     * 
     * @param studioDate
     * @param studioTime
     * @return
     */
    private static AST deserialiseDateConstructorInForLoop(AST lhsAST,
            String studioDateArray, String studioTimeArray) {
        String parseRHS = parseRHS(lhsAST);
        String forLoop = "for(var dtArrayIndex = 0; dtArrayIndex < " //$NON-NLS-1$
                + studioDateArray + ".length; dtArrayIndex++){"; //$NON-NLS-1$
        String fullDateExpression =
                parseRHS + "[dtArrayIndex] = new Date(" + studioDateArray //$NON-NLS-1$
                        + "[dtArrayIndex].getTime() + " //$NON-NLS-1$
                        + studioTimeArray + "[dtArrayIndex].getTime());"; //$NON-NLS-1$
        String endForLoop = "}"; //$NON-NLS-1$
        AST ast =
                deserialiseStatement(forLoop + fullDateExpression + endForLoop);
        return ast;
    }

    private static AST deserialiseDateTimeConstructorInForLoop(AST rhsAST,
            String studioDateArray, String studioTimeArray) {
        String parseRHS = parseRHS(rhsAST);
        String forLoop = "for(var dtArrayIndex = 0; dtArrayIndex <" + parseRHS //$NON-NLS-1$
                + ".length; dtArrayIndex++){"; //$NON-NLS-1$
        String dateExpression =
                studioDateArray
                        + "[dtArrayIndex] = " + parseRHS + "[dtArrayIndex];"; //$NON-NLS-1$ //$NON-NLS-2$
        String timeExpression =
                studioTimeArray
                        + "[dtArrayIndex] = " + parseRHS + "[dtArrayIndex];"; //$NON-NLS-1$ //$NON-NLS-2$
        String endForLoop = "}"; //$NON-NLS-1$
        AST ast =
                deserialiseStatement(forLoop + dateExpression + timeExpression
                        + endForLoop);
        return ast;
    }

    /**
     * lhsSide = process.MyDateTimeDf_D studioDate = javaDate.variable
     * 
     * @param javaReturnVar
     * @return
     */
    private static AST deserialiseJustDateConstructor(String javaReturnVar) {
        String justDateExpression = javaReturnVar + ";"; //$NON-NLS-1$
        AST ast = deserialiseExpression(justDateExpression);
        return ast;

    }

    /**
     * lhsSide = process.MyDateTimeDf_D studioTime= javaTime.variable
     * 
     * @param javaReturnVar
     * @return
     */
    private static AST deserialiseJustTimeConstructor(String studioTime,
            String javaReturnVar) {
        String justTimeExpression = studioTime + "= " //$NON-NLS-1$
                + javaReturnVar + ";"; //$NON-NLS-1$
        AST ast = deserialiseExpression(justTimeExpression);
        return ast;
    }

    private static AST deserialiseStatement(String strScript) {
        try {
            JScriptParser scriptParser = getScriptParser(strScript);
            scriptParser.statement();
            return scriptParser.getAST();
        } catch (RecognitionException e) {
            e.printStackTrace();
        } catch (TokenStreamException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static AST deserialiseExpression(String strScript) {
        try {
            JScriptParser scriptParser = getScriptParser(strScript);
            scriptParser.expression();
            return scriptParser.getAST();
        } catch (RecognitionException e) {
            e.printStackTrace();
        } catch (TokenStreamException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static JScriptParser getScriptParser(String strScript) {
        StringReader reader = new StringReader(strScript);
        JScriptLexer lexer = new JScriptLexer(reader);
        JScriptParser parser = new JScriptParser(lexer);
        return parser;
    }

    private static List<AST> getQualifiedDotASTList(AST lhsAST,
            List<String> supportedContexts) {
        List<AST> dotASTList = new ArrayList<AST>();
        if (lhsAST.getType() == JScriptTokenTypes.DOT) {
            dotASTList.add(lhsAST);
        }
        List<Integer> astTypeList = new ArrayList<Integer>();
        astTypeList.add(JScriptTokenTypes.DOT);
        List<AST> firstLevelChildren =
                ParseUtil.getFirstLevelChildren(lhsAST, astTypeList);
        if (firstLevelChildren != null && !firstLevelChildren.isEmpty()) {
            dotASTList.addAll(firstLevelChildren);
        }
        List<AST> qualifiedList = new ArrayList<AST>();
        for (AST dotAST : dotASTList) {
            AST firstChild = dotAST.getFirstChild();
            String fcText = firstChild.getText();
            if (supportedContexts.contains(fcText)) {
                qualifiedList.add(dotAST);
            }
        }
        return qualifiedList;
    }

    private static void printScript(JScriptTreeParser treeParser) {
        AST toPrintAST = treeParser.getAST();
        StringBuffer buffer = new StringBuffer();
        // IpeScriptEmitter out = new IpeScriptEmitter(buffer);
        JScriptEmitter out = new JScriptEmitter(buffer);
        try {
            out.compilationUnit(toPrintAST);
            String translatedScript = buffer.toString();
            if (translatedScript != null) {
                translatedScript = translatedScript.trim();
            }
        } catch (RecognitionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static String parseRHS(AST assignRHSAST) {
        StringBuffer buffer = new StringBuffer();
        JScriptEmitter emitter = new JScriptEmitter(buffer);
        try {
            emitter.expr(assignRHSAST);
            String string = buffer.toString();
            return string;
        } catch (RecognitionException e) {
            e.printStackTrace();
        }
        return null;
    }

}
