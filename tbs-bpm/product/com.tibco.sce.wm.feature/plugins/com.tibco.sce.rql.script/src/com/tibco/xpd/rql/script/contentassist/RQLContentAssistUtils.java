/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.rql.script.contentassist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.w3c.dom.Node;

import com.tibco.xpd.rql.parser.eval.EntityType;
import com.tibco.xpd.rql.script.ImageCache;
import com.tibco.xpd.rql.script.RQLScriptPlugin;

/**
 * Content Assist Utils for RQL
 * 
 * @author rsomayaj
 * 
 */
public class RQLContentAssistUtils {

    private static List<String> regulatorExpresionList;

    private static List<String> combiningExpresionList;

    private static List<String> operatorWordList;

    private static List<String> keyWordList;

    private static List<String> keyWordListInEntityBracket;

    public static final String RQL_SINGLE_LITERAL_STRING = "\'"; //$NON-NLS-1$

    public static final String RQL_DOUBLE_LITERAL_STRING = "\""; //$NON-NLS-1$

    public static final String RQL_OPEN_BRACKET_STRING = "("; //$NON-NLS-1$

    public static final String RQL_CLOSING_BRACKET_STRING = ")"; //$NON-NLS-1$

    public static final String RQL_CONNECTION_WORD_STRING = "."; //$NON-NLS-1$

    public static final String EMPTY_STRING = ""; //$NON-NLS-1$

    public static final String EMPTY_SPACE = " "; //$NON-NLS-1$

    private static final String NO_CONTENT_ASSIST = "___"; //$NON-NLS-1$

    public static final String RQL_NAME_ATTRIBUTE = "name"; //$NON-NLS-1$

    public static final String RQL_TYPE_ATTRIBUTE = "type"; //$NON-NLS-1$

    public static final String RQL_ATTRIBUTE_ATTRIBUTE = "attribute"; //$NON-NLS-1$

    public static final String RQL_REGULATOR_INTERSECT = "intersect "; //$NON-NLS-1$

    public static final String RQL_REGULATOR_NOT = "not "; //$NON-NLS-1$

    public static final String RQL_REGULATOR_UNION = "union "; //$NON-NLS-1$

    public static String getRQLStartText(String string, int end) {
        String startText = ""; //$NON-NLS-1$
        if (end < 0) {
            end = 0;
        }
        if (string != null && string.length() >= end) {
            String oeString = string.substring(0, end);
            if (RQLContentAssistUtils.isInsideLiteral(string, end, true)) {
                startText = getInsideTextExpression(oeString);
            } else {
                if (oeString.endsWith(RQL_OPEN_BRACKET_STRING)) {
                    startText = getOpenBracketStartText(oeString);
                } else if (endsWithCombiningExpression(oeString)) {
                    startText = EMPTY_STRING;
                } else if (endsWithOperatorExpression(oeString)) {
                    startText = NO_CONTENT_ASSIST;
                } else if (endsWithConnectionExpression(oeString)) {
                    startText = getEndingConnectionStartText(oeString);
                } else {
                    String combiningExpressionsStartText =
                            getCombiningExpressionsStartText(oeString, false);
                    String closedBracketExpressionsStartText =
                            getClosedBracketExpressionsStartText(oeString, true);
                    if (combiningExpressionsStartText.length() < closedBracketExpressionsStartText
                            .length()) {
                        startText = combiningExpressionsStartText;
                    } else {
                        startText = closedBracketExpressionsStartText;
                    }
                }
            }
        }
        return startText;
    }

    private static String getInsideTextExpression(String oeString) {
        String combiningExpressionsStartText =
                getCombiningExpressionsStartText(oeString, false);
        String connectionExpressionsStartText =
                getConnectionExpressionsStartText(oeString, false);
        if (combiningExpressionsStartText.length() < connectionExpressionsStartText
                .length()) {
            oeString = combiningExpressionsStartText;
        } else {
            oeString = connectionExpressionsStartText;
        }
        return oeString;
    }

    private static boolean endsWithCombiningExpression(String oeString) {
        List<String> combiningExpressionList = getCombiningExpressionList();
        for (String combiningExpression : combiningExpressionList) {
            if (oeString.endsWith(combiningExpression)) {
                return true;
            }
        }
        return false;
    }

    private static boolean endsWithConnectionExpression(String oeString) {
        if (oeString.endsWith(RQL_CONNECTION_WORD_STRING)) {
            return true;
        }
        return false;
    }

    private static boolean endsWithOperatorExpression(String oeString) {
        List<String> operatorWordList = getOperatorWordList();
        for (String operatorExpression : operatorWordList) {
            if (oeString.endsWith(operatorExpression)) {
                return true;
            }
        }
        return false;
    }

    private static String getOpenBracketStartText(String oeString) {
        if (oeString != null) {
            String combiningExpressionsStartText =
                    getCombiningExpressionsStartText(oeString, false);
            String connectionExpressionsStartText =
                    getConnectionExpressionsStartText(oeString, false);
            if (combiningExpressionsStartText.length() < connectionExpressionsStartText
                    .length()) {
                oeString = combiningExpressionsStartText;
            } else {
                oeString = connectionExpressionsStartText;
            }
        }
        return oeString;
    }

    private static String getEndingConnectionStartText(String oeString) {
        if (oeString != null) {
            oeString = getCombiningExpressionsStartText(oeString, false);
        }
        return oeString;
    }

    private static String getClosedBracketExpressionsStartText(String oeString,
            boolean includeKeyWord) {
        String closedBracketStartText =
                RQLContentAssistUtils.getKeyWordsStartText(oeString,
                        Collections.singletonList(RQL_CLOSING_BRACKET_STRING),
                        includeKeyWord);
        if (closedBracketStartText != null
                && !closedBracketStartText
                        .startsWith(RQL_CLOSING_BRACKET_STRING
                                + RQL_CONNECTION_WORD_STRING)) {
            return closedBracketStartText;
        }
        return oeString;
    }

    private static String getCombiningExpressionsStartText(String oeString,
            boolean includeKeyWord) {
        return RQLContentAssistUtils.getKeyWordsStartText(oeString,
                RQLContentAssistUtils.getCombiningExpressionList(),
                includeKeyWord);
    }

    private static String getConnectionExpressionsStartText(String oeString,
            boolean includeKeyWord) {
        return RQLContentAssistUtils.getKeyWordsStartText(oeString, Collections
                .singletonList(RQL_CONNECTION_WORD_STRING), includeKeyWord);
    }

    private static String getKeyWordsStartText(String string,
            List<String> keyWordList, boolean includeKeyWord) {
        return getKeyWordsStartText(string, keyWordList, includeKeyWord, true);
    }

    private static String getKeyWordsStartText(String string,
            List<String> keyWordList, boolean includeKeyWord,
            boolean removeLiterals) {
        String startText = string;
        if (string != null) {
            String lastKeyWord =
                    RQLContentAssistUtils.findLastKeyword(string,
                            keyWordList,
                            removeLiterals);
            if (lastKeyWord != null) {
                int lastKeyWordPos = string.lastIndexOf(lastKeyWord);
                if (lastKeyWordPos != -1
                        && !RQLContentAssistUtils.isInsideLiteral(string,
                                lastKeyWordPos,
                                true)
                        && !RQLContentAssistUtils.isInsideLiteral(string,
                                lastKeyWordPos,
                                false)) {
                    if (includeKeyWord) {
                        startText = string.substring(lastKeyWordPos);
                    } else if (string.length() >= lastKeyWordPos
                            + lastKeyWord.length()) {
                        startText =
                                string.substring(lastKeyWordPos
                                        + lastKeyWord.length());
                    }
                }
            }
        }
        return startText;
    }

    public static String findLastSignificantKeyword(String string,
            boolean removeLiterals) {
        return findLastKeyword(string,
                getSignificantKeyWordList(),
                removeLiterals);
    }

    public static String findLastSignificantKeywordInEntityBracket(
            String string, boolean removeLiterals) {
        return findLastKeyword(string,
                getSignificantKeyWordListInBracket(),
                removeLiterals);
    }

    public static String findLastOperatorInEntityBracket(String string,
            boolean removeLiterals) {
        return findLastKeyword(string, getOperatorWordList(), removeLiterals);
    }

    public static String findLastKeyword(String string, List<String> keyWords,
            boolean removeLiterals) {
        String lastKeyword = null;
        int keyWorkPos = 0;
        if (removeLiterals) {
            string = RQLContentAssistUtils.removeAllLiterals(string);
        }
        if (keyWords != null && string != null) {
            for (String keyWord : keyWords) {
                int lastIndexOf = string.lastIndexOf(keyWord);
                if (lastIndexOf >= keyWorkPos) {
                    if (lastKeyword == null || !lastKeyword.contains(keyWord)) {
                        keyWorkPos = lastIndexOf;
                        lastKeyword = keyWord;
                    }
                }
            }
        }
        return lastKeyword;
    }
    
    public static Image getCombiningExpressionIcon(String combiningExpr) {
        if (combiningExpr != null) {
            if (combiningExpr.equals(RQL_REGULATOR_NOT)) {
                return RQLContentAssistUtils
                        .getIconForPath(ImageCache.RQL_NOT_IMAGE);
            } else if (combiningExpr.equals(RQL_REGULATOR_INTERSECT)) {
                return RQLContentAssistUtils
                        .getIconForPath(ImageCache.RQL_INTERSECT_IMAGE);
            } else if (combiningExpr.equals(RQL_REGULATOR_UNION)) {
                return RQLContentAssistUtils
                        .getIconForPath(ImageCache.RQL_UNION_IMAGE);
            }
        }
        return null;
    }

    public static List<String> getCombiningExpressionList() {
        if (combiningExpresionList == null) {
            combiningExpresionList = new ArrayList<String>();
            combiningExpresionList.add(RQL_REGULATOR_NOT); //$NON-NLS-1$
            combiningExpresionList.add(RQL_REGULATOR_UNION); //$NON-NLS-1$
            combiningExpresionList.add(RQL_REGULATOR_INTERSECT); //$NON-NLS-1$
        }
        return combiningExpresionList;
    }

    public static List<String> getRegulatorExpressionList() {
        if (regulatorExpresionList == null) {
            regulatorExpresionList = new ArrayList<String>();
            regulatorExpresionList.add("and "); //$NON-NLS-1$
            regulatorExpresionList.add("or "); //$NON-NLS-1$
        }
        return regulatorExpresionList;
    }

    private static List<String> getOperatorWordList() {
        if (operatorWordList == null) {
            operatorWordList = new ArrayList<String>();
            operatorWordList.add("!="); //$NON-NLS-1$
            operatorWordList.add("<="); //$NON-NLS-1$
            operatorWordList.add(">="); //$NON-NLS-1 
            operatorWordList.add("<>"); //$NON-NLS-1 
            operatorWordList.add("="); //$NON-NLS-1$
            operatorWordList.add(">"); //$NON-NLS-1$
            operatorWordList.add("<"); //$NON-NLS-1$          
        }
        return operatorWordList;
    }

    private static List<String> getSignificantKeyWordList() {
        if (keyWordList == null) {
            keyWordList = new ArrayList<String>();
            keyWordList.addAll(getCombiningExpressionList());
            keyWordList.add(RQL_CONNECTION_WORD_STRING);
            keyWordList.add(RQL_OPEN_BRACKET_STRING);
            keyWordList.add(RQL_CLOSING_BRACKET_STRING);
        }
        return keyWordList;
    }

    private static List<String> getSignificantKeyWordListInBracket() {
        if (keyWordListInEntityBracket == null) {
            keyWordListInEntityBracket = new ArrayList<String>();
            keyWordListInEntityBracket.addAll(getCombiningExpressionList());
            keyWordListInEntityBracket.addAll(getRegulatorExpressionList());
            keyWordListInEntityBracket.add(RQL_OPEN_BRACKET_STRING);
            keyWordListInEntityBracket.add(RQL_CLOSING_BRACKET_STRING);
            keyWordListInEntityBracket.add(RQL_SINGLE_LITERAL_STRING);
            keyWordListInEntityBracket.add(RQL_DOUBLE_LITERAL_STRING);
        }
        return keyWordListInEntityBracket;
    }

    public static boolean isInsideLiteral(String string, int position,
            boolean single) {
        boolean isInsideLiteral = false;
        String literalString = RQL_DOUBLE_LITERAL_STRING;
        if (single) {
            literalString = RQL_SINGLE_LITERAL_STRING;
        }
        if (string != null) {
            boolean openLiteral = false;
            for (int i = 0; i < position; i++) {
                char element = string.charAt(i);
                if (Character.toString(element).equals(literalString)) {
                    openLiteral = !openLiteral;
                }
            }
            isInsideLiteral = openLiteral;
        }
        return isInsideLiteral;
    }

    private static String removeAllLiterals(String string) {
        if (string != null) {
            string = removeLiterals(string, true);
            if (string != null) {
                string = removeLiterals(string, false);
            }
        }
        return string;
    }

    private static String removeLiterals(String string, boolean single) {
        String strWithoutLiterals = string;
        String literalString = RQL_DOUBLE_LITERAL_STRING;
        if (single) {
            literalString = RQL_SINGLE_LITERAL_STRING;
        }
        if (string != null) {
            if (string.contains(literalString)) {
                int openLiteral = string.indexOf(literalString);
                int closeLiteral =
                        string.indexOf(literalString, openLiteral + 1);
                if (closeLiteral == -1) {
                    // Unclosed Literal
                    closeLiteral = string.length() - 1;
                }
                if (string.length() >= closeLiteral + literalString.length()) {
                    String literalStr =
                            string.substring(openLiteral, closeLiteral
                                    + literalString.length());
                    strWithoutLiterals =
                            RQLContentAssistUtils.removeLiterals(string
                                    .replace(literalStr, ""), single);//$NON-NLS-1$
                }
            }
        }
        return strWithoutLiterals;
    }

    public static String removeComments(String string) {
        return string;
    }
    
    public static Image getIconForPath(String path){
        ImageCache imageCache = RQLScriptPlugin.getDefault().getImageCache();
        Image image = null;
        if (imageCache != null) {
            if (path != null) {
                image = imageCache.getImage(path);
            }
        }
        return image;
    }

    public static Image getIconForNode(Node node) {
        ImageCache imageCache = RQLScriptPlugin.getDefault().getImageCache();
        Image image = null;
        if (imageCache != null) {
            if (node != null) {
                image = imageCache.getImage(ImageCache.RQL_IMAGE);
            }
        }
        return image;
    }

    public static boolean isInsideBrackets(String string) {
        boolean isInsideBracket = false;
        string = RQLContentAssistUtils.removeAllLiterals(string);
        if (string != null) {
            int openBracketsCount = 0;
            int closedBracketsCount = 0;
            if (string != null) {
                for (int i = 0; i < string.length(); i++) {
                    char element = string.charAt(i);
                    if (element == RQLContentAssistUtils.RQL_OPEN_BRACKET_STRING
                            .charAt(0)) {
                        openBracketsCount++;
                    } else if (element == RQLContentAssistUtils.RQL_CLOSING_BRACKET_STRING
                            .charAt(0)) {
                        closedBracketsCount++;
                    }
                }
                if (openBracketsCount > closedBracketsCount) {
                    isInsideBracket = true;
                }
            }
        }
        return isInsideBracket;
    }
    
    public static String getEntityType(String entityName) {
        if (entityName != null) {
            if (entityName.equals("orgunit")) {
                return EntityType.ORGANIZATIONAL_UNIT.toString();
            } else {
                return EntityType.valueOf(entityName.toUpperCase()).toString();
            }
        }
        return null;
    }

}
