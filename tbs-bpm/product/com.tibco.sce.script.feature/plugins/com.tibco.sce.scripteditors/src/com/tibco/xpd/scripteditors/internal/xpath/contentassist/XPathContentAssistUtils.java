/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.scripteditors.internal.xpath.contentassist;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.w3c.dom.Node;

import com.tibco.xpd.script.model.jscript.JScriptUtils;
import com.tibco.xpd.scripteditors.ScriptEditorsPlugin;
import com.tibco.xpd.scripteditors.internal.ImageCache;

/**
 * Utils for XPath Content Assist
 * 
 * @author rsomayaj
 * 
 */
public class XPathContentAssistUtils {

    private static List<String> keyWordList = null;

    private static List<String> triggerWordList = null;

    /*
     * private static Map<Character, Character> bracketsMap = null;
     * 
     * private static List<Character> triggerCharList = null;
     * 
     * private static List<Character> literalCharList = null;
     */

    private static final String XPATH_OPEN_COMMENT_STRING = "<!--"; //$NON-NLS-1$

    private static final String XPATH_CLOSING_COMMENT_STRING = "-->"; //$NON-NLS-1$

    private static final String XPATH_SINGLE_LITERAL_STRING = "\'"; //$NON-NLS-1$

    private static final String XPATH_DOUBLE_LITERAL_STRING = "\""; //$NON-NLS-1$

    public static String getXPathStartText(String string, int end) {
        String startText = ""; //$NON-NLS-1$
        if (end < 0) {
            end = 0;
        }
        if (string != null && string.length() >= end) {
            String oeString = string.substring(0, end);
            if (!XPathContentAssistUtils.isLiteral(oeString)
                    && !XPathContentAssistUtils.isComment(oeString)) {
                // Remove the comments
                String strNoComments =
                        XPathContentAssistUtils.removeComments(oeString);
                if (strNoComments != null) {
                    startText = getUnclosedBracketStartText(strNoComments);
                    if (startText != null) {
                        startText =
                                XPathContentAssistUtils
                                        .getKeyWordsStartText(startText);
                        if (startText != null) {
                            startText =
                                    XPathContentAssistUtils
                                            .getTriggerWordsStartText(startText);
                            if (startText != null) {
                                startText =
                                        XPathContentAssistUtils
                                                .removeLeadingSpaces(startText);
                            }
                        }
                    }
                }
            } else {
                return null;
            }
        }

        return startText;
    }

    private static String removeLeadingSpaces(String string) {
        String startText = string;
        if (startText != null) {
            for (int i = 0; i < startText.length(); i++) {
                if (startText.charAt(i) != ' ') {
                    startText = startText.substring(i);
                    break;
                }
            }
        }
        return startText;
    }

    private static String getTriggerWordsStartText(String string) {
        String startText = string;
        if (string != null) {
            String lastTriggerWord =
                    XPathContentAssistUtils.findLastTriggerword(string);
            if (lastTriggerWord != null) {
                int lastTriggerWordPos = string.lastIndexOf(lastTriggerWord);
                if (lastTriggerWordPos != -1
                        && !XPathContentAssistUtils.isInsideLiteral(string,
                                lastTriggerWordPos,
                                true)
                        && !XPathContentAssistUtils.isInsideLiteral(string,
                                lastTriggerWordPos,
                                false)
                        && !isInsideClosedBracket(string,
                                lastTriggerWordPos,
                                '[',
                                ']')
                        && !isInsideClosedBracket(string,
                                lastTriggerWordPos,
                                '(',
                                ')')) {
                    startText = string.substring(lastTriggerWordPos);
                }
            }
        }
        return startText;
    }

    private static String getKeyWordsStartText(String string) {
        String startText = string;
        if (string != null) {
            String lastKeyWord =
                    XPathContentAssistUtils.findLastKeyword(string);
            if (lastKeyWord != null) {
                int lastKeyWordPos = string.lastIndexOf(lastKeyWord);
                if (lastKeyWordPos != -1
                        && !XPathContentAssistUtils.isInsideLiteral(string,
                                lastKeyWordPos,
                                true)
                        && !XPathContentAssistUtils.isInsideLiteral(string,
                                lastKeyWordPos,
                                false)
                        && !isInsideClosedBracket(string,
                                lastKeyWordPos,
                                '[',
                                ']')
                        && !isInsideClosedBracket(string,
                                lastKeyWordPos,
                                '(',
                                ')')) {
                    startText = string.substring(lastKeyWordPos);
                }
            }
        }
        if (startText != null && startText.length() > 0
                && !startText.equals(string)) {
            startText = startText.substring(1, startText.length());
        }
        return startText;
    }

    private static String findLastTriggerword(String string) {
        String lastTriggerWord = null;
        int triggerWorkPos = 0;
        List<String> triggerWords =
                XPathContentAssistUtils.getTriggerWordList();
        String strWithoutLiterals =
                XPathContentAssistUtils.removeLiterals(string, true);
        strWithoutLiterals =
                XPathContentAssistUtils.removeLiterals(strWithoutLiterals,
                        false);
        if (triggerWords != null && strWithoutLiterals != null) {
            for (String triggerWord : triggerWords) {
                int lastIndexOf = strWithoutLiterals.lastIndexOf(triggerWord);
                if (lastIndexOf > triggerWorkPos) {
                    triggerWorkPos = lastIndexOf;
                    lastTriggerWord = triggerWord;
                }
            }
        }
        return lastTriggerWord;
    }

    private static String findLastKeyword(String string) {
        String lastKeyword = null;
        int keyWorkPos = 0;
        List<String> keyWords = XPathContentAssistUtils.getKeyWordList();
        String strWithoutLiterals =
                XPathContentAssistUtils.removeLiterals(string, true);
        strWithoutLiterals =
                XPathContentAssistUtils.removeLiterals(strWithoutLiterals,
                        false);
        if (keyWords != null && strWithoutLiterals != null) {
            for (String keyWord : keyWords) {
                int lastIndexOf = strWithoutLiterals.lastIndexOf(keyWord);
                if (lastIndexOf > keyWorkPos) {
                    keyWorkPos = lastIndexOf;
                    lastKeyword = keyWord;
                }
            }
        }
        return lastKeyword;
    }

    private static String getUnclosedBracketStartText(String string) {
        String startText = string;
        if (startText != null) {
            // Check brackets []
            int squareBracketStart =
                    XPathContentAssistUtils.getUnclosedBracketStart(string,
                            '[',
                            ']');
            // Check brackets ()
            int roundBracketStart =
                    XPathContentAssistUtils.getUnclosedBracketStart(string,
                            '(',
                            ')');
            if (squareBracketStart < roundBracketStart) {
                startText =
                        string.substring(roundBracketStart, string.length());
            } else {
                startText =
                        string.substring(squareBracketStart, string.length());
            }
        }
        if (startText != null && startText.length() > 0
                && !startText.equals(string)) {
            startText = startText.substring(1, startText.length());
        }
        return startText;
    }

    private static int getUnclosedBracketStart(String string,
            Character openBracket, Character closeBracket) {
        int startPos = 0;
        if (string != null && string.length() > 0) {
            int end = string.length() - 1;
            for (; end >= 0; end--) {
                if (string.charAt(end) == openBracket) {
                    int closeBracketPos = -1;
                    if (string.length() > end) {
                        closeBracketPos =
                                JScriptUtils.findCloseBracketsPos(string,
                                        end + 1,
                                        openBracket,
                                        closeBracket);
                    }
                    if (closeBracketPos == -1) {
                        return end;
                    }
                }
            }
        }
        return startPos;
    }

    /*
     * private static boolean isXPathKeyChar(char aChar) { if
     * (getCharList().contains(aChar) || getTriggerCharList().contains(aChar)) {
     * return true; } return false; }
     * 
     * private static boolean isTriggerChar(char aChar) { if
     * (getTriggerCharList().contains(aChar)) { return true; } return false; }
     * 
     * 
     * private static List<Character> getTriggerCharList(){ if(triggerCharList ==
     * null){ triggerCharList = new ArrayList<Character>();
     * triggerCharList.add('$'); } return triggerCharList; }
     */

    private static List<String> getTriggerWordList() {
        if (triggerWordList == null) {
            triggerWordList = new ArrayList<String>();
            triggerWordList.add("$"); //$NON-NLS-1$
        }
        return triggerWordList;
    }

    private static List<String> getKeyWordList() {
        if (keyWordList == null) {
            keyWordList = new ArrayList<String>();
            keyWordList.add("="); //$NON-NLS-1$
            keyWordList.add("<"); //$NON-NLS-1$
            keyWordList.add(">"); //$NON-NLS-1$
            keyWordList.add("+"); //$NON-NLS-1$
            keyWordList.add("-"); //$NON-NLS-1$
            keyWordList.add(" div "); //$NON-NLS-1$
            keyWordList.add(" mod "); //$NON-NLS-1$
            keyWordList.add("|"); //$NON-NLS-1$
            keyWordList.add(" and "); //$NON-NLS-1$
            keyWordList.add(" or "); //$NON-NLS-1$
            keyWordList.add(","); //$NON-NLS-1$
        }
        return keyWordList;
    }

    private static boolean isComment(String string) {
        boolean isComment = false;
        if (string != null) {
            String strWithoutComment =
                    XPathContentAssistUtils.removeComments(string);
            if (strWithoutComment == null) {
                return true;
            }
        }
        return isComment;
    }

    private static boolean isLiteral(String string) {
        boolean isLiteral = false;
        if (string != null) {
            String strWithoutComments =
                    XPathContentAssistUtils.removeComments(string);
            if (strWithoutComments != null) {
                String strWithoutLiterals =
                        XPathContentAssistUtils
                                .removeLiterals(strWithoutComments, false);
                if (strWithoutLiterals == null) {//$NON-NLS-1$
                    return true;
                }
                strWithoutLiterals =
                        XPathContentAssistUtils
                                .removeLiterals(strWithoutComments, true);
                if (strWithoutLiterals == null) {//$NON-NLS-1$
                    return true;
                }
            }
        }
        return isLiteral;
    }

    private static boolean isInsideLiteral(String string, int position,
            boolean single) {
        boolean isInsideLiteral = false;
        String literalString = XPATH_DOUBLE_LITERAL_STRING;
        if (single) {
            literalString = XPATH_SINGLE_LITERAL_STRING;
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

    private static boolean isInsideClosedBracket(String string, int position,
            char openBracket, char closeBracket) {
        boolean isInsideClosedBracket = false;
        int openBracketsCount = 0;
        int closedBracketsCount = 0;
        if (string != null) {
            for (int i = 0; i < position; i++) {
                char element = string.charAt(i);
                if (element == openBracket) {
                    openBracketsCount++;
                } else if (element == closeBracket) {
                    closedBracketsCount++;
                }
            }
            if (openBracketsCount != closedBracketsCount) {
                isInsideClosedBracket = true;
            }
        }
        return isInsideClosedBracket;
    }

    private static String removeLiterals(String string, boolean single) {
        String strWithoutLiterals = string;
        String literalString = XPATH_DOUBLE_LITERAL_STRING;
        if (single) {
            literalString = XPATH_SINGLE_LITERAL_STRING;
        }
        if (string != null) {
            if (string.contains(literalString)) {
                int openLiteral = string.indexOf(literalString);
                int closeLiteral =
                        string.indexOf(literalString, openLiteral + 1);
                if (closeLiteral == -1) {
                    // Unclosed Literal
                    strWithoutLiterals = null;//$NON-NLS-1$
                } else {
                    if (string.length() >= closeLiteral
                            + literalString.length()) {
                        String literalStr =
                                string.substring(openLiteral, closeLiteral
                                        + literalString.length());
                        strWithoutLiterals =
                                XPathContentAssistUtils.removeLiterals(string
                                        .replace(literalStr, ""), single);//$NON-NLS-1$
                    }
                }
            }
        }
        return strWithoutLiterals;
    }

    public static String removeComments(String string) {
        String strWithoutComments = string;
        if (string != null) {
            if (string.contains(XPATH_OPEN_COMMENT_STRING)) {
                int openComment = string.indexOf(XPATH_OPEN_COMMENT_STRING);
                int closeComment = string.indexOf(XPATH_CLOSING_COMMENT_STRING);
                if (closeComment == -1) {
                    // Unclosed Comment
                    strWithoutComments = null;//$NON-NLS-1$
                } else {
                    if (string.length() >= closeComment
                            + XPATH_CLOSING_COMMENT_STRING.length()) {
                        String commentStr =
                                string
                                        .substring(openComment, closeComment
                                                + XPATH_CLOSING_COMMENT_STRING
                                                        .length());
                        strWithoutComments =
                                XPathContentAssistUtils.removeComments(string
                                        .replace(commentStr, ""));//$NON-NLS-1$
                    }
                }
            }
        }
        return strWithoutComments;
    }

    /**
     * @param node
     * @return
     */
    public static Image getIconForNode(Node node) {
        ImageCache imageCache =
                ScriptEditorsPlugin.getDefault().getImageCache();
        Image image = null;
        // TODO - Ravi - Check whether the classes from the correct libraries
        // are used.
        if (imageCache != null) {
            if (node instanceof org.apache.xerces.dom.DeferredAttrImpl
                    || node instanceof org.apache.xerces.dom.DeferredAttrNSImpl) {
                image = imageCache.getImage(ImageCache.ATTRIBUTE);
            } else if (node instanceof org.apache.xerces.dom.DeferredDocumentImpl) {
                image = imageCache.getImage(ImageCache.PART);
            } else {
                image = imageCache.getImage(ImageCache.ELEMENT);
            }
        }
        return image;
    }

    /*
     * private static boolean isOpenBracket(Character aChar) { boolean
     * isOpenBracket = false; Collection<Character> openBracketValues =
     * getBracketsMap().values(); if (openBracketValues != null &&
     * !openBracketValues.isEmpty()) { for (Character openBracket :
     * openBracketValues) { if (openBracket.equals(aChar)) { return true; } } }
     * return isOpenBracket; }
     * 
     * private static Map<Character, Character> getBracketsMap() { if
     * (bracketsMap == null) { bracketsMap = new HashMap<Character,
     * Character>(); bracketsMap.put(']', '['); bracketsMap.put(')', '('); }
     * return bracketsMap; }
     * 
     * private static List<Character> getLiteralCharList(){ if(literalCharList ==
     * null){ literalCharList = new ArrayList<Character>();
     * literalCharList.add('\''); literalCharList.add('\"'); } return
     * literalCharList; }
     */

}
