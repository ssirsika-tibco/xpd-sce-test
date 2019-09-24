/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.scripteditors.internal.javascript;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateContextType;
import org.eclipse.swt.graphics.Image;
import org.w3c.dom.Node;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.DefaultUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.DynamicJsAttribute;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.JsAttribute;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.JsMethod;
import com.tibco.xpd.script.model.client.JsReference;
import com.tibco.xpd.script.model.internal.client.DefaultJsEnumeration;
import com.tibco.xpd.script.model.jscript.JScriptUtils;
import com.tibco.xpd.script.parser.antlr.JScriptParser;
import com.tibco.xpd.script.parser.validator.DefaultVarNameResolver;
import com.tibco.xpd.script.parser.validator.ISymbolTable;
import com.tibco.xpd.script.parser.validator.IValidationStrategy;
import com.tibco.xpd.script.parser.validator.SymbolTable;
import com.tibco.xpd.script.sourceviewer.internal.contentassist.AbstractTibcoContentAssistProcessor;
import com.tibco.xpd.script.sourceviewer.internal.contentassist.CustomCompletionProposal;
import com.tibco.xpd.script.sourceviewer.internal.contentassist.MyCompletionStringNode;
import com.tibco.xpd.script.sourceviewer.internal.contentassist.MyFollowClass;
import com.tibco.xpd.script.sourceviewer.internal.preferences.AbstractScriptCommonUIPreferenceNames;
import com.tibco.xpd.script.sourceviewer.internal.util.Debug;
import com.tibco.xpd.script.ui.ScriptGrammarContributionsUtil;
import com.tibco.xpd.scripteditors.ScriptEditorsPlugin;
import com.tibco.xpd.scripteditors.internal.javascript.contentassist.ContentAssistUtil;
import com.tibco.xpd.scripteditors.internal.javascript.contentassist.JsContentAssistConstants;

/**
 * Java Script Content Assist Processor.
 * <p>
 * XPD-5115: <b>PLEASE ENSURE THAT IF ANY CHANGES ARE MADE TO THE STRING COMPLETION ALGORITHM THEN THE ALGORITHM IS
 * RE-TESTED VIA com.tibco.xpd.n2.test.general.JavaScriptStringCompleteTest JUnit TEST.</b>
 * <p>
 * 
 * @author rsomayaj
 * 
 */
public class JavaScriptContentAssistProcessor extends AbstractTibcoContentAssistProcessor {

    // Indicator Used to avoid repeat call to getStringToComplete() method

    private static final String PREFIX = "PREFIX-";//$NON-NLS-1$

    private static final String FUNCTION_NAME = "/functionname/"; //$NON-NLS-1$

    static protected final Character chRightBrace = new Character(')');

    static protected final Character chRightBracket = new Character(']');

    static protected final Character chStringDelim = new Character('\"');

    static protected final String MULTILINE_COMMENT_START = new String("/*"); //$NON-NLS-1$

    static protected final String MULTILINE_COMMENT_END = new String("*/"); //$NON-NLS-1$

    static protected final String SINGLELINE_COMMENT_START = new String("//"); //$NON-NLS-1$

    static protected final List<Character> ALLOWED_CHAR_BEFORE_WHITESPACE =
            new ArrayList<Character>(Arrays.asList('.', ')', '/', '*'));

    private JScriptParser cachedParser = null;

    private static final Logger LOG = XpdResourcesPlugin.getDefault().getLogger();

    public JavaScriptContentAssistProcessor() {
        super();
    }

    @Override
    public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int documentOffset) {
        /*
         * parseScript() used to use super.getScriptText() to parse and hence assess symbols in script. The script
         * returned by super.getScriptText() IS NOT UPDATED until super.getProposalForConfiguredClasses() is called
         * below!
         * 
         * Therefore, changed parseScript() to take latest script as parameter instead
         */

        String text = viewer.getDocument().get();
        parseScript(text);

        ICompletionProposal result[] = null;
        /*
         * get prefix to complete note: this is accessed here to be able to decide on , if the templates list should be
         * appended or not
         */
        String prefix = getStringToComplete(viewer, documentOffset, null);

        /*
         * The String PREFIX- is appended to as a marker that prefix is calculated, as the prefix value could be a BLANK
         * string as well.
         */
        result = getProposalForConfiguredClasses(viewer, text, PREFIX + prefix, documentOffset);

        ProposalComparator c = new ProposalComparator();
        // Sort result list
        if (result != null && result.length > 0) {
            Arrays.sort(result, c);
        }
        /*
         * Append the templates if the prefix is blank, leading to the result list containing the Global suggestion
         * list.
         */
        if (prefix != null && prefix.length() == 0 && result != null) {
            ICompletionProposal templates[] = templateComputeCompletionProposals(viewer, documentOffset);
            if (templates == null) {
                return result;
            } else {
                ICompletionProposal all[] = new ICompletionProposal[result.length + templates.length];
                System.arraycopy(result, 0, all, 0, result.length);
                System.arraycopy(templates, 0, all, result.length, templates.length);
                return all;
            }
        }
        return result;
    }

    @Override
    protected Template[] getTemplates(String contextTypeId) {
        return ScriptEditorsPlugin.getDefault().getJavaScriptTemplateStore().getTemplates();
    }

    /**
     * 
     * @param viewer
     * @param documentPosition
     * @param node
     * @return
     */
    @Override
    protected String getStringToComplete(ITextViewer viewer, int documentPosition, Node node) {
        org.eclipse.jface.text.IDocument doc = viewer.getDocument();

        return calculateStringToComplete(doc, documentPosition);
    }

    /**
     * XPD-5115: <b>PLEASE ENSURE THAT IF ANY CHANGES ARE MADE TO THE STRING COMPLETION ALGORITHM THEN THE ALGORITHM IS
     * RE-TESTED VIA com.tibco.xpd.n2.test.general.JavaScriptStringCompleteTest JUnit TEST.</b>
     * <p>
     * - redesigned this algorithm to go forwards thru the string keeping track of whether "pos" is within certain
     * constructs that we ignore and also tracking the start / end of java identifiers.
     * <p>
     * We process thru string keeping track of whether we are in a string or comment etc until and when we are not in
     * such a place, we keep track of start and end of currentJavaIdentifier.
     * <p>
     * Then when we reach the position in doc where ctrl+space is pressed we know whether we are in a place where it
     * should be ignored and if not, what the start of the current java identifier is.
     * <p>
     * <b> This method is public only for the purpose of JUnit tests.
     * <p>
     * 
     * @param doc
     * @param cursorLocation
     * 
     * @return The beginning of string to complete prior to documentPosition, "" if documentPosition is in 'open space'
     *         and <code>null</code> if documentPosition is nopt in a valid place for string-completion
     */
    public String calculateStringToComplete(IDocument document, int cursorLocation) {

        if (cursorLocation > document.getLength()) {
            return null;
        }

        /*
         * First off, parsing thru any string that has parts in that must be ignored when looking for character
         * sequences is a pain. So the first thing to do is exchange all for whitespace and constant strings/characters
         * for special invalid characters (int val 1)
         * 
         * That will mean that we can look for matching brackets and so on without coming across similar in strings and
         * comments
         */
        StringBuffer doc = new StringBuffer();
        try {
            doc.append(document.get(0, document.getLength()));
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        // XPD-7432 Ignore return when calculating completion strings.
        if (doc.toString().startsWith("return ")) { //$NON-NLS-1$
            doc.delete(0, 7);
            cursorLocation -= 7;
        }
        if (!normaliseCommentsAndStrings(doc, cursorLocation)) {
            /*
             * Doc position is in middle of string or comment, so nothing we can do.
             */
            // System.out.println(this.getClass().getSimpleName()
            // + ": auto-complete in middle of string / comment.");
            return null;
        }

        return recursiveProcessDocOrBracketedContent(doc, cursorLocation);
    }

    /**
     * XPD-5115: <b>PLEASE ENSURE THAT IF ANY CHANGES ARE MADE TO THE STRING COMPLETION ALGORITHM THEN THE ALGORITHM IS
     * RE-TESTED VIA com.tibco.xpd.n2.test.general.JavaScriptStringCompleteTest JUnit TEST.</b>
     * <p>
     * Does the bulk of the work for {@link #calculateStringToComplete(IDocument, int)} in finding the java identifier
     * prior to the given documentPosition.
     * <p>
     * This code then is RECURSIVE and will look for the first at the whole document and then if it should find that the
     * cursor location is within a set of brackets "()" or "[]" will recurs with just the content of those brackets.
     * 
     * @param documentOrBracketedContent
     * @param cursorLocation
     * 
     * @return The beginning of string to complete prior to documentPosition, "" if documentPosition is in 'open space'
     *         and <code>null</code> if documentPosition is not in a valid place for string-completion
     */
    private String recursiveProcessDocOrBracketedContent(StringBuffer documentOrBracketedContent, int cursorLocation) {

        if (documentOrBracketedContent.length() == 0) {
            /*
             * content assist in empty brackets means pull up complete identifier list.
             */
            return ""; //$NON-NLS-1$

        } else if (cursorLocation > documentOrBracketedContent.length()) {
            /* Urk - this shouldn't happen. */
            return null;
        }

        /*
         * currentJavaIdentifier will keep track of the current java token (field or field.xxx or even string "abc".aa
         * 
         * Basically we will initialise at the first character we come across that's a valid java identifier start
         * character and unset it when we come across a character that is not a valid part of an identifier.
         */
        StringBuffer currentJavaIdentifier = new StringBuffer();

        try {
            for (int pos = 0; pos < cursorLocation; pos++) {

                char charAtPos = documentOrBracketedContent.charAt(pos);

                if (Character.isJavaIdentifierStart(charAtPos) || Character.isJavaIdentifierPart(charAtPos)) {

                    /*
                     * Check for a broken word (this will be if we have content in currentJavaIdentifier that ends with
                     * whitespace unless that whitespace is not just after a period.
                     */
                    if (endsInWhitespaceNotAfterPeriod(currentJavaIdentifier)) {
                        /*
                         * Ok, we have a broken word, so let's just restart the java identifier from scratch.
                         */
                        currentJavaIdentifier = new StringBuffer();
                    }

                    currentJavaIdentifier.append(charAtPos);

                }
                /*
                 * If it's a parent->child joiner (i.e. abc. child)
                 */
                else if ('.' == charAtPos) {
                    if (currentJavaIdentifier.length() > 0) {
                        currentJavaIdentifier.append(charAtPos);
                    }
                }
                /*
                 * Whitespace
                 */
                else if (Character.isWhitespace(charAtPos)) {
                    /*
                     * If we've started a java identifier then we'll add the whitespace to the end of it. This is so
                     * that if we get another valid java id char then we know it is the start of something new (see
                     * isJavaIdentifierPart handling above.
                     */
                    if (currentJavaIdentifier.length() > 0) {
                        currentJavaIdentifier.append(charAtPos);
                    }

                }
                /*
                 * Brackets - we either need to tack (xxx) / [xxx] constructs onto end of current java identifier.
                 */
                else if ('(' == charAtPos || '[' == charAtPos) {
                    /* Find the end bracket for this start */
                    char endBracket = (charAtPos == '(' ? ')' : ']');
                    int endBracketPos = findEndBracket(charAtPos, endBracket, documentOrBracketedContent, pos);

                    if (endBracketPos > 0) {
                        if (cursorLocation > endBracketPos) {
                            /*
                             * If the cursor location is anywhere after the end bracket then simply add all of bracketed
                             * content to end of java identifier (if there is a current one) as this is probably a
                             * function call etc
                             * 
                             * Otherwise if we haven't started a java id yet and cursor location is after the brackets
                             * then we can skip to after bracketed section.
                             */
                            if (currentJavaIdentifier.length() > 0) {
                                currentJavaIdentifier
                                        .append(documentOrBracketedContent.subSequence(pos, endBracketPos + 1));
                            }

                            pos = endBracketPos;

                        } else {
                            /*
                             * The cursor location is within the bracketed section.
                             * 
                             * So we can just recurs but with just the bracketed content. We can IGNORE any java
                             * identifier we currently have because the start of that must be outside the bracket and
                             * the location is inside - so we are actually auto-completing something inside the bracket.
                             */
                            return recursiveProcessDocOrBracketedContent(
                                    new StringBuffer(documentOrBracketedContent.substring(pos + 1, endBracketPos)),
                                    /*
                                     * Adjust cursorLocation for new start of bracketed string
                                     */
                                    cursorLocation - (pos + 1));
                        }

                    } else {
                        /*
                         * If we didn't find an end bracket then reset java identifier so we start over.
                         */
                        currentJavaIdentifier = new StringBuffer();
                    }
                }
                /*
                 * It's not a java identifier character and not something that may following a java identifier (such as
                 * a "." "()" or [])
                 */
                else {
                    /*
                     * Reset the the current identifier ready to find start of next one.
                     */
                    currentJavaIdentifier = new StringBuffer();
                }

            }

        } catch (Exception e) {
            ScriptEditorsPlugin.getDefault().getLogger().error(e,
                    "Error identifying string to complete for content assist"); //$NON-NLS-1$

            return ""; //$NON-NLS-1$
        }

        if (endsInWhitespaceNotAfterPeriod(currentJavaIdentifier)) {
            /*
             * The user pressed ctrl+space after whitespace after a java identifier and not after a joining period after
             * an identifier - we can't do anything about that and shouldn't offer any auto complete.
             */
            return null;

        }

        /* Finally remove whitespace from result. */
        String stringToComplete = currentJavaIdentifier.toString().replaceAll("\\s", ""); //$NON-NLS-1$ //$NON-NLS-2$

        if (stringToComplete.length() > 0) {
            char lastChar = stringToComplete.charAt(stringToComplete.length() - 1);
            if (lastChar == ')' || lastChar == ']') {
                /*
                 * Can't reasonably append anything to xxx() or xxx[] without a training "." so don't provide any
                 * content assist at all.
                 */
                return null;
            }
        }
        // System.out.println(this.getClass().getSimpleName()
        // + ": Complete String= '" + stringToComplete + "'");

        return stringToComplete;
    }

    /**
     * Find the matching end bracket and return it's location.
     * <p>
     * String is expected to have been passed thru {@link #normaliseCommentsAndStrings(StringBuffer, int)} wipe over
     * strings and comments.
     * 
     * @param charAtPos
     * @param documentOrBracketedContent
     * @param posOfStartBracket
     * 
     * @return > 0 location of end bracket that matches given begin bracket. < 0 if no matching end bracket.
     */
    private int findEndBracket(char startBracket, char endBracket, StringBuffer documentOrBracketedContent,
            int posOfStartBracket) {

        int startBracketCount = 0;

        for (int pos = posOfStartBracket + 1; pos < documentOrBracketedContent.length(); pos++) {
            char charAtPos = documentOrBracketedContent.charAt(pos);

            if (charAtPos == startBracket) {
                startBracketCount++;

            } else if (charAtPos == endBracket) {
                if (startBracketCount == 0) {
                    return pos;
                } else {
                    startBracketCount--;
                }
            }
        }

        return -1;
    }

    /**
     * The character token with which we replace strings (in *
     * {@link JavaScriptContentAssistProcessor#normaliseCommentsAndStrings(StringBuffer, int)} to make other processing
     * easier
     */
    private static char STRING_REPLACE_TOKEN = '#';

    /**
     * Enum for use by {@link JavaScriptContentAssistProcessor#normaliseCommentsAndStrings(StringBuffer, int)} to keep
     * state of parsing as it loops thru parsing out strings and comments.
     * 
     * @author aallway
     * @since 18 Jul 2013
     */
    private enum ParseProcessingType {
        /** Not in any significant block that we are processing thru to ignore. */
        NONE,
        /** we are in a "string" looking for end */
        IN_STRING,
        /** we are in a 'character' looking for end */
        IN_CHARACTER,
        /** we are in a // comment looking for end of line. */
        IN_SLASHSLASH_COMMENT,
        /** We are in a / * ... * / comment looking for end * / */
        IN_SLASHSTAR_COMMENT
    }

    /**
     * First off, parsing thru any string that has parts in that must be ignored when looking for character sequences is
     * a pain. So the first thing to do is exchange all for whitespace and constant strings/characters for special
     * invalid characters (char with int val 1)
     * 
     * That will mean that we can look for matching brackets and so on without coming across similar in strings and
     * comments
     * 
     * @param doc
     * @param cursorLocation
     * 
     * @return <code>true</code> on success or <code>false</code> if documentPosition lies within a string or comment.
     */
    private boolean normaliseCommentsAndStrings(StringBuffer doc, int cursorLocation) {

        ParseProcessingType parseProcessingType = ParseProcessingType.NONE;

        for (int pos = 0; pos < doc.length(); pos++) {
            /*
             * If we come to the cursor location and we're in the middle of string / comment then can get out now.
             */
            if (!ParseProcessingType.NONE.equals(parseProcessingType) && pos == cursorLocation) {
                return false;
            }

            /*
             * Note original position this time around loop so we can check whether we advanced pos again during this
             * iteration (i.e. skipped past an extra character.
             */
            int originalPosForIteration = pos;

            char charAtPos = doc.charAt(pos);

            char nextChar = 0;

            if (pos < (doc.length() - 1)) {
                nextChar = doc.charAt(pos + 1);
            }

            /*
             * Handle processing within a string ("x")
             */
            if (ParseProcessingType.IN_STRING.equals(parseProcessingType)) {
                /*
                 * Looking for end of string,
                 */

                if ('\n' == charAtPos) {
                    /*
                     * Even though we haven't actually found end of string yet, new-lines are not allowed so chances are
                     * user made a mistake in not terminating string? So we'll imagine that the string is terminated a
                     * \n rather than messing up end of string with start of next.
                     */
                    parseProcessingType = ParseProcessingType.NONE;

                } else {
                    /*
                     * For everything else replace everything (including end of string), with the special string
                     * replacement character
                     */
                    doc.setCharAt(pos, STRING_REPLACE_TOKEN);

                    if ('\"' == charAtPos) {
                        /* Found end of string. */
                        parseProcessingType = ParseProcessingType.NONE;

                    } else if ('\\' == charAtPos) {
                        /*
                         * Skip next character after an escaping \ character (by effectively incrementing pos here and
                         * in main for loop this will happen)
                         */
                        pos++;

                        doc.setCharAt(pos, STRING_REPLACE_TOKEN);

                        /*
                         * Check if incremented pos (end " string) is cursorLocation.
                         */
                        if (pos == cursorLocation) {
                            return false; /* cursorLocation in comment. */
                        }
                    }
                }
            }
            /*
             * Handle processing within a constant character ('x')
             */
            else if (ParseProcessingType.IN_CHARACTER.equals(parseProcessingType)) {
                /* Looking for end of character constant. */
                if ('\n' == charAtPos) {
                    /*
                     * Even though we haven't actually found end of string yet, new-lines are not allowed so chances are
                     * user made a mistake in not terminating string? So we'll imagine that the string is terminated a
                     * \n rather than messing up end of string with start of next.
                     */
                    parseProcessingType = ParseProcessingType.NONE;

                } else {
                    /*
                     * For everything else replace everything (including end of string), with the special string
                     * replacement character
                     */
                    doc.setCharAt(pos, STRING_REPLACE_TOKEN);

                    if ('\'' == charAtPos) {
                        /* Found end of character constant. */
                        parseProcessingType = ParseProcessingType.NONE;

                    } else if ('\\' == charAtPos) {
                        /*
                         * Skip next character after an escaping \ character (by effectively incrementing pos here and
                         * in main for loop this will happen)
                         */
                        pos++;

                        doc.setCharAt(pos, STRING_REPLACE_TOKEN);

                        /*
                         * Check if incremented pos (end ' of character) is cursorLocation.
                         */
                        if (pos == cursorLocation) {
                            return false; /* cursorLocation in comment. */
                        }
                    }
                }
            }
            /*
             * Handle processing within a // comment.
             */
            else if (ParseProcessingType.IN_SLASHSLASH_COMMENT.equals(parseProcessingType)) {
                /*
                 * Looking for end of line (replace everything else with whitespace..
                 */
                if ('\n' == charAtPos) {
                    parseProcessingType = ParseProcessingType.NONE;
                } else {
                    doc.setCharAt(pos, ' ');
                }
            }
            /*
             * Handle processing within a * / comment.
             */
            else if (ParseProcessingType.IN_SLASHSTAR_COMMENT.equals(parseProcessingType)) {
                /* Looking for terminating * / */
                doc.setCharAt(pos, ' ');

                if ('*' == charAtPos) {
                    if ('/' == nextChar) {
                        /*
                         * Found terminating * / skip '*' now so that main loop increments past '/'
                         */
                        parseProcessingType = ParseProcessingType.NONE;

                        pos++;
                        doc.setCharAt(pos, ' ');

                        /*
                         * Check if incremented pos (last char of comment) is cursorLocation.
                         */
                        if (pos == cursorLocation) {
                            return false; /* cursorLocation in comment. */
                        }
                    }
                }
            }
            /**
             * Not currently processing within a section that should be ignored (string, comment etc etc). So check for
             * start of strings comments etc.
             */
            else { /* parseProcessType == NONE */
                /* Check for start of String */
                if ('\"' == charAtPos) {
                    parseProcessingType = ParseProcessingType.IN_STRING;
                    doc.setCharAt(pos, STRING_REPLACE_TOKEN);

                }
                /* Check for start of constant character */
                else if ('\'' == charAtPos) {
                    parseProcessingType = ParseProcessingType.IN_CHARACTER;
                    doc.setCharAt(pos, STRING_REPLACE_TOKEN);

                }
                /*
                 * Check for start of slash-slash or slash-star comment.
                 * 
                 * Bear in mind that a java identifier may be interrupted by a comment and that this is valid - so we
                 * will NOT reset currentJavaIdentifier just because we come across a comment.
                 */
                else if ('/' == charAtPos) {
                    if ('/' == nextChar) {
                        parseProcessingType = ParseProcessingType.IN_SLASHSLASH_COMMENT;
                        /*
                         * Increment pass first '/' (and main loop will increment past second '/')
                         */
                        doc.setCharAt(pos, ' ');
                        pos++;
                        doc.setCharAt(pos, ' ');

                        /*
                         * Check if incremented pos (2nd char of comment) is cursorLocation.
                         */
                        if (pos == cursorLocation) {
                            return false; /* cursorLocation in comment. */
                        }

                    } else if ('*' == nextChar) {
                        parseProcessingType = ParseProcessingType.IN_SLASHSTAR_COMMENT;
                        /*
                         * Increment pass first / (and main loop will increment past the following '*')
                         */
                        doc.setCharAt(pos, ' ');
                        pos++;
                        doc.setCharAt(pos, ' ');

                        /*
                         * Check if incremented pos (2nd char of comment) is cursorLocation.
                         */
                        if (pos == cursorLocation) {
                            return false; /* cursorLocation in comment. */
                        }
                    }
                }
            }

        } /* process next character at location 'pos' */

        if (!ParseProcessingType.NONE.equals(parseProcessingType) && cursorLocation >= doc.length()) {
            /*
             * Ended in middle of string / comment etc (i.e. broken string / comment at end).
             */
            return false;
        }

        return true;
    }

    /**
     * @param currentJavaIdentifier
     * 
     * @return <code>true</code> if string ends in whitespace that is NOT following a period character.
     */
    private boolean endsInWhitespaceNotAfterPeriod(StringBuffer currentJavaIdentifier) {
        if (currentJavaIdentifier.length() > 0) {
            if (Character.isWhitespace(currentJavaIdentifier.charAt(currentJavaIdentifier.length() - 1))) {
                if (!currentJavaIdentifier.toString().trim().endsWith(".")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * given the existing string to complete, return a list of completions
     * 
     * @param estring
     *            the string in the document at which the cursor is at the end of and which we are to complete
     * @param docPosition
     *            the position in the document that the cursor and the end of the estring are at.
     */
    @SuppressWarnings("unchecked")
    @Override
    protected Vector<CustomCompletionProposal> getProposalVector(ITextViewer viewer, String oestring, int docpos,
            MyFollowClass myFollowClass) {
        Vector<CustomCompletionProposal> retval = new Vector<CustomCompletionProposal>();
        if (oestring.startsWith(PREFIX)) {
            // remove dummy indicator
            oestring = oestring.replace(PREFIX, ""); //$NON-NLS-1$
        }
        getProposalJsVector(viewer, "." + oestring, docpos, myFollowClass, retval); //$NON-NLS-1$
        return retval;
    }

    /**
     * Given the existing string to complete, return a list of completions
     * 
     * @param estring
     *            the string in the document at which the cursor is at the end of and which we are to complete. This
     *            string is required to begin with a . or a [. This means the caller might need to add one.
     * @param docpos
     *            the position in the document that the cursor and the end of the estring are at.
     * @param fc
     *            the followclass that we start our parse at.
     * @param outval
     *            is the vector to which this method should add CompletionProposals The behavior we want is firstly to
     *            analyze the right string. Here are the cases... ((Note: $ represents the end of string marker.))
     *            ((Note: ... represents arbitrary text. .... represents a dot followed by arbitrary text.
     * 
     * 
     *            a) [$ In this case we really don't want to be called. There are so many possibilities. They might want
     *            something like [0] or ['images'] or [arbitraryexpression] The first two situations technically require
     *            that we know what is left of the [, but the final case is always possible. I think for the moment we
     *            will only support the final case. So, in this case we don't want to be called with anything other than
     *            ".$".
     * 
     *            b) [blah$ In the case we should be able to detect which the three cases above is being attempted
     *            simply by looking at the first character to the right of the [. There is the issue of us determining
     *            if what they've typed is even valid though. For now though, I think we'll only handle the final case
     *            of just having an arbitrary expression to the right of the [ So like case (a), we don't want to be
     *            called. We want the caller to have passed us ".blah$" instead.
     * 
     *            c) [blahindex$ See (b). We'd like to treat blahindex as an arbitrary expression right now to avoid
     *            having to consider what occurs to the left.
     * 
     *            d) [blahindex]$ In this case the next character can be a dot or a [ or the value to the left can be
     *            considered complete. I guess we could offer the choices of <complete> or "." or "[". I'd prefer to
     *            offer nothing though.
     * 
     *            e) [blahindex][... Recurse starting with the index on that next [ for the next segment. The state that
     *            the next analysis begins with can be determined by possibly taking the union of all possible values
     *            for blahindex, or we can take a stab at analyzing blah index to determine what it's value is and what
     *            follow state maps to that.
     * 
     *            f) [blahindex].... See (e) except that the next index points to that first dot.
     * 
     *            g) .$ h) .blah$ i) .blahname$ These cases are pretty straight forward. In the case (i) we will
     *            probably chose to return nothing. Another possibility is to return "blahname"... and if also a
     *            syntactic possibility "blahnamelonger". This last comment deals with the possibility of having
     *            possible values like "location" and "locationColor" where one complete value is a substring of
     *            another.
     * 
     *            j) .blahname[...]... Straight forward. Next segment to parse will begin with [. And we'll definitely
     *            consider the value "blahname" to figure out the followclass.
     * 
     *            k) .blahname.... See (j) except the next segment begins with "." instead of "[".
     * 
     */
    @SuppressWarnings("restriction")
    private void getProposalJsVector(ITextViewer viewer, String oestring, int docpos, MyFollowClass myFollowClass,
            Vector<CustomCompletionProposal> outval) {
        int idx0 = 0;
        int idx1;
        if (oestring.length() == 0) {
            throw new RuntimeException("getProposalVector: estring input parameter must have a length"); //$NON-NLS-1$
        }
        if (Debug.jsDebugContextAssist) {
            // System.out.println( "in getProposalVector( "+oestring+"
            // ,,"+fc.getName()+",,)" );
        }
        // todo: do a lot more checking for end of string in here. Don't need
        // index exceptions
        char startchar = oestring.charAt(0);
        // assert:
        String estring = oestring.substring(1);
        if (myFollowClass == null) {
            return;
        }
        if ((startchar == '[') || (startchar == '(')) {
            int popcnt = 1;
            idx1 = idx0;
            while (popcnt > 0) {
                int idx2 = strNextPos(estring, idx1, ')', ']');
                int idx3 = strNextPos(estring, idx1, '(', '[');
                // todo: enhance this to not be thrown off by quoted chars like
                // '(' or "]" within the area we're scanning
                idx1 = (idx2 == -1) ? idx3 : (idx3 == -1) ? idx2 : (idx2 < idx3) ? idx2 : idx3;
                if (idx1 == -1)
                    break; // not a matching number of brackets on the
                // remainder of this line
                popcnt = (idx2 == idx1) ? popcnt - 1 : popcnt + 1;
                idx1++; // skip it for the next pass
            }
            if (idx1 == -1) {
                // case (a), (b), (c) above.
                // We prefer that the caller not call us for this.
                throw new RuntimeException("caller passed the context parser too much file"); //$NON-NLS-1$
            } else {
                idx1--;
                // we over indexed, so correct it to point to that last
                // ] case (d), (e), (f).
            }
        } else {
            int idx2 = estring.indexOf('.', idx0);
            int idx3 = strNextPos(estring, idx0, '(', '[');
            idx1 = (idx2 == -1) ? idx3 : (idx3 == -1) ? idx2 : (idx2 < idx3) ? idx2 : idx3;
        }
        // case (a), (b) and (c) eliminated.
        // idx1 points to ] for cases (d)(e)(f)
        // idx1 == -1 for cases (g)(h)(i) ( .$, .blah$, .blahindex$ )
        // idx1 points to [ for case (j)
        // idx1 points to . for case (k)

        if (idx1 < 0) {
            // cases (g)(h)(i) ( .$, .blah$, .blahindex$ )
            String nextstr = estring.substring(idx0);
            Iterator<MyCompletionStringNode> myCompNodeItr = myFollowClass.getCompletionNodes().iterator();
            while (myCompNodeItr.hasNext()) {
                MyCompletionStringNode myCompNode = myCompNodeItr.next();
                String cs = myCompNode.getCompletionString();
                if (cs.equals("#isa")) { //$NON-NLS-1$
                    // also parse here with another class
                    // FollowClass fc2 = getFollowClass(csn.getFCName());
                    // getProposalVector(oestring, docpos, fc2, outval,
                    // new FCContext(csn, fcc));
                } else if (cs.toUpperCase().startsWith(nextstr.toUpperCase())) {

                    // String strAddInfo = getAdditionalInfoText(fcc, fc, csn);
                    String strAddInfo = myCompNode.getComment();
                    if (JScriptUtils.needsQuotes(cs)) {
                        // needs to be quoted
                        /*
                         * XPD-5535: Saket: Calling the CustomCompletionProposal constructor with 'relevance' parameter
                         * in order to sort the content assist list grouped by the type of object (e.g. attribute,
                         * method etc).
                         */
                        CustomCompletionProposal cap = new CustomCompletionProposal("['" + cs + "']" //$NON-NLS-2$//$NON-NLS-1$
                                , docpos - (nextstr.length() + 1), nextstr.length() + 1, cs.length() + 4
                                // , csn.getImage(fcc), cs + " "
                                // +strADText
                                // //$NON-NLS-1$
                                , myCompNode.getImage(), myCompNode.getCompletionString(), getContextInformation(),
                                strAddInfo, myCompNode.getSortGroup());
                        outval.add(cap);
                    } else {
                        // ==> // String reststring =
                        // cs.substring(nextstr.length());
                        if (cs.length() > 0) {
                            // XPD-2064: DO NOT Show unqualified name of
                            // enumeration in content-assist
                            if (!enumWithUnqualifiedName(myFollowClass, cs)) {
                                /*
                                 * XPD-5535: Saket: Calling the CustomCompletionProposal constructor with 'relevance'
                                 * parameter in order to sort the content assist list grouped by the type of object
                                 * (e.g. attribute, method etc).
                                 */
                                CustomCompletionProposal cap = new CustomCompletionProposal(cs // reststring
                                        , docpos - nextstr.length(), nextstr.length() // , docpos, 0
                                        , cs.length() // ,
                                                      // reststring.length()
                                        // , csn.getImage(fcc), cs + " "
                                        // +strADText
                                        // //$NON-NLS-1$
                                        , myCompNode.getImage(), myCompNode.getCompletionString(),
                                        getContextInformation(), strAddInfo, myCompNode.getSortGroup());
                                outval.add(cap);
                            }
                        }
                    }
                }
            } // endwhile
        } else if (estring.charAt(idx1) == ')') {
            // todo: test this
            if ((idx1 + 1) == estring.length()) {
                // case (d) [blahindex]$
                return; // nothing to suggest until they give us one more
                // character as a clue. And in fact they might be done.
            }
            getProposalJsVector(viewer, estring.substring(idx1 + 1), docpos, myFollowClass, outval);
        } else if (estring.charAt(idx1) == ']') {
            // cases (d), (e), (f) ([blahindex]$ [blahindex][ [blahindex].)
            String nextstr = estring.substring(idx0, idx1);
            if ((idx1 + 1) == estring.length()) {
                // case (d) [blahindex]$
                return; // nothing to suggest until they give us one more
                // character as a clue. And in fact they might be done.
            }
            HashSet tHashSet = new HashSet();

            char ch1 = ' ';
            if (nextstr.length() >= 1) {
                ch1 = nextstr.charAt(0);
            }
            int intt = -1;
            try {
                intt = Integer.parseInt(nextstr.substring(0, idx1));
            } catch (Exception exc) {
            }
            if ((ch1 == '\'') || (ch1 == '\"') || (intt >= 0)) {
                String ch1str = ((ch1 == '\'') || (ch1 == '\"')) ? String.valueOf(ch1) : ""; //$NON-NLS-1$
                Iterator<MyCompletionStringNode> myCompNodeItr = myFollowClass.getCompletionNodes().iterator();
                while (myCompNodeItr.hasNext()) {
                    MyCompletionStringNode csn = myCompNodeItr.next();
                    String cs = csn.getCompletionString();
                    boolean match = false;
                    if (cs.charAt(0) == '/') {
                        if (cs.equals("/number/")) { //$NON-NLS-1$
                            int nint = -1;
                            try {
                                nint = Integer.parseInt(nextstr);
                            } catch (Exception x) {
                            }
                            match = (nint >= 0);
                            // it's not worth the code for check for a quoted
                            // number like '0' or "1".
                        }
                    } else if (cs.equals("#isa")) { //$NON-NLS-1$
                        // also parse here with another class
                        // FollowClass fc2 = getFollowClass(csn.getFCName());
                        // getProposalVector(oestring, docpos, fc2, outval,
                        // new FCContext(csn, fcc));
                        // match = false;
                    } else {
                        match = (ch1str + cs + ch1str).equalsIgnoreCase(nextstr);
                        if (cs.indexOf("()") > 0) //$NON-NLS-1$
                            match = false; // we are very unlikely to specify a
                        // method or function via []
                        // notation.//$NON-NLS-1$
                    }
                    // if (match) tHashSet.add( csn.getFCName());
                    if (match)
                        tHashSet.add(csn);
                }
            } else {
                // case (e) & (f) [blahindex].... [blahindex][...
                // this appears to be a formula inside the [], so anything is
                // possible as a follow
                // class.
                Iterator<MyCompletionStringNode> myCompNodeItr = myFollowClass.getCompletionNodes().iterator();
                while (myCompNodeItr.hasNext()) {
                    MyCompletionStringNode csn = myCompNodeItr.next();
                    String cs = csn.getCompletionString();
                    boolean match = false;
                    if (cs.charAt(0) == '/') {
                        if (cs.equals("/number/")) { //$NON-NLS-1$
                            match = true;
                        } else /* if (cs.equals("/imagename/")) */ {
                            match = true;
                        }
                    } else if (cs.equals("#isa")) { //$NON-NLS-1$
                        // also parse here with another class
                        // FollowClass fc2 = getFollowClass(csn.getFCName());
                        // getProposalVector(oestring, docpos, fc2, outval,
                        // new FCContext(csn, fcc));
                        // match = false;
                    } else {
                        // just a string, but since the formula could generate
                        // that string, it's a possibility too.
                        match = true;
                        if (cs.indexOf("()") > 0) //$NON-NLS-1$
                            match = false; // we are very unlikely to specify a
                        // method or function via []
                        // notation.//$NON-NLS-1$
                    }
                    if (match) {
                        tHashSet.add(csn);
                    }
                } // endwhile
            }
            Iterator iter2 = tHashSet.iterator();
            while (iter2.hasNext()) {
                MyCompletionStringNode csn = (MyCompletionStringNode) iter2.next();
                MyFollowClass fc2 = getMyFollowClass(csn.getCompletionString(), false);
                getProposalJsVector(viewer, estring.substring(idx1 + 1), docpos, fc2, outval);
            }
            // If myFollowClass is an Array then add proposals for the array instance type
            if (JsConsts.ARRAY.equals(myFollowClass.getClassName())) {
                IScriptRelevantData genericCtx = myFollowClass.getGenericContext();
                // Create a dummy attribute to represent the array instance field
                JsAttribute att = new DynamicJsAttribute(genericCtx.getName(), genericCtx.getType(), false, null, null);
                MyCompletionStringNode csn = new MyCompletionStringNode(att);
                MyFollowClass fc2 = getMyFollowClassForMatchingNode(csn,
                        false,
                        myFollowClass.getGenericContext().getType(),
                        myFollowClass.getGenericContext());
                getProposalJsVector(viewer, estring.substring(idx1 + 1), docpos, fc2, outval);
            }
        } else if (estring.charAt(idx1) == '(') {
            String nextstr = estring.substring(idx0, idx1 + 1);
            String nextstrUC = nextstr.toUpperCase();
            Iterator<MyCompletionStringNode> myCompNodeItr = myFollowClass.getCompletionNodes().iterator();
            while (myCompNodeItr.hasNext()) {
                MyCompletionStringNode csn = myCompNodeItr.next();
                String cs = csn.getCompletionString();
                if (cs.toUpperCase().startsWith(nextstrUC)) {
                    String contextClass = null;
                    if (myFollowClass != null) {
                        contextClass = cs;
                    }
                    MyFollowClass fc2 = getMyFollowClassForMatchingNode(csn,
                            false,
                            contextClass,
                            myFollowClass.getGenericContext());
                    getProposalJsVector(viewer, estring.substring(idx1), docpos, fc2, outval);
                }
            }
        } else {
            // cases (j) & (k) (.blahname[...]... .blahname....
            String nextstr = estring.substring(idx0, idx1);
            Iterator<MyCompletionStringNode> myCompNodeItr = myFollowClass.getCompletionNodes().iterator();
            while (myCompNodeItr.hasNext()) {
                MyCompletionStringNode csn = myCompNodeItr.next();
                String cs = csn.getCompletionString();
                boolean isArray = false;
                if (estring != null && estring.length() > 1) {
                    String aString = estring.substring(0, estring.length() - 1);
                    if (aString.equals(cs)) {
                        String contextClass = null;
                        if (myFollowClass != null) {
                            contextClass = cs;
                        }
                        MyFollowClass fc2 = getMyFollowClassForMatchingNode(csn,
                                false,
                                contextClass,
                                myFollowClass.getGenericContext());
                        aString = estring.substring(estring.length() - 1);
                        if (aString != null && aString.length() > 0) {
                            getProposalJsVector(viewer, aString, docpos, fc2, outval);
                        }
                    }
                }
                // if (cs.contains(JsConsts.ARRAY_CONTENT_ASSIST_SUFFIX)) {
                // isArray = true;
                // }
                if (csn.isMultiple()) {
                    isArray = true;
                }
                boolean match = false;
                if (cs.charAt(0) == '/') {
                    if (cs.equals("/number/")) { //$NON-NLS-1$
                        int nint = -1;
                        try {
                            nint = Integer.parseInt(nextstr);
                        } catch (Exception x) {
                        }
                        match = (nint >= 0);
                    }
                } else if (cs.equals("#isa")) { //$NON-NLS-1$
                    // also parse here with another class
                    // FollowClass fc2 = getFollowClass(csn.getFCName());
                    // getProposalVector(oestring, docpos, fc2, outval,
                    // new FCContext(csn, fcc));
                    // match = false;
                } else {
                    String currentCs = cs;
                    // if (isArray) {
                    // currentCs =
                    // cs
                    // .replace(JsConsts.ARRAY_CONTENT_ASSIST_SUFFIX,
                    // ""); //$NON-NLS-1$
                    // }
                    if (currentCs.equals(nextstr)) {
                        match = true;
                    }
                }
                if (match) {
                    String newOeString = estring.substring(idx1);
                    boolean isExpressionArray = isExpressionArray(newOeString);
                    String contextClass = null;
                    if (myFollowClass != null) {
                        contextClass = cs;
                    }
                    MyFollowClass fc2 = getMyFollowClassForMatchingNode(csn,
                            isExpressionArray,
                            contextClass,
                            myFollowClass.getGenericContext());
                    // Script Editor enhancements for TIBCO Forms
                    List<MyCompletionStringNode> listCN = null;
                    if (fc2 != null) {
                        listCN = fc2.getCompletionNodes();
                    }
                    if (isArray) {
                        if (newOeString.length() >= 3) {
                            // Script Editor enhancements for TIBCO Forms
                            String aNewOeString = removeIndexExpressionFromArray(newOeString);
                            if ((aNewOeString == null) && (listCN != null) && (listCN.size() != 0)) {
                                newOeString = estring.substring(idx1);
                            } else if (aNewOeString != null && newOeString != null
                                    && !aNewOeString.equals(newOeString)) {
                                isExpressionArray = true;
                                newOeString = aNewOeString;
                            } else {
                                newOeString = aNewOeString;
                            }
                        } else {
                            // Script Editor enhancements for TIBCO Forms
                            if (listCN == null || listCN.size() == 0) {
                                fc2 = getMyFollowClass(JsConsts.ARRAY, isExpressionArray);
                            } else {
                                newOeString = estring.substring(idx1);
                            }
                        }
                    }
                    if (newOeString != null && !newOeString.equals("")) { //$NON-NLS-1$
                        getProposalJsVector(viewer, newOeString, docpos, fc2, outval);
                    }
                }
            }
        }
        return;
    }

    /**
     * Returns true if this is an entry for the unqualified name of an enumeration.
     * 
     * @param myFollowClass
     * 
     * @param cs
     * @return
     */
    private boolean enumWithUnqualifiedName(MyFollowClass myFollowClass, String cs) {
        // XPD-4634: This check is only required for the root level
        // Enumerations, not for the members of other classes
        if (START_CLASS.equals(myFollowClass.getClassName())) {
            for (IScriptRelevantData iScriptRelevantData : getScriptDataList()) {
                if (iScriptRelevantData instanceof DefaultUMLScriptRelevantData
                        && iScriptRelevantData.getName().equalsIgnoreCase(cs)) {
                    JsClass jsClass = ((DefaultUMLScriptRelevantData) iScriptRelevantData).getJsClass();
                    if (jsClass != null && jsClass instanceof DefaultJsEnumeration) {
                        if (!((DefaultJsEnumeration) jsClass).requireQualifiedName()) {
                            /*
                             * XPD-2064: For enumeration only qualified name should be available in content-assist, not
                             * unqualified name. Skip unqualified name, it will only be supported in validation.
                             */
                            return true;

                        }
                    }
                }

            }
        }
        return false;
    }

    private String removeIndexExpressionFromArray(String arrayString) {
        if (arrayString == null) {
            return arrayString;
        }
        if (arrayString.charAt(0) == '[') {
            int indCloseBracket = JScriptUtils.findCloseBracketsPos(arrayString, 1, '[', ']');
            if (indCloseBracket != -1 && arrayString.length() > indCloseBracket) {
                arrayString = arrayString.substring(indCloseBracket + 1);
            }
        } else {
            return null;
        }
        return arrayString;
    }

    private boolean isExpressionArray(String arrayString) {
        if (arrayString == null) {
            return false;
        }
        if (arrayString.charAt(0) == '[') {
            int indCloseBracket = JScriptUtils.findCloseBracketsPos(arrayString, 1, '[', ']');
            if (indCloseBracket != -1 && arrayString.length() > indCloseBracket) {
                return true;
            }
        }
        return false;
    }

    protected MyFollowClass getMyFollowClassForMatchingNode(MyCompletionStringNode csn, boolean isExpressionArray,
            String newContextClass, IScriptRelevantData genericContext) {
        MyFollowClass fc2 = null;
        JsAttribute jsAttribute = csn.getJsAttribute();
        JsReference jsReference = csn.getJsReference();
        JsMethod jsMethod = csn.getJsMethod();
        if (jsAttribute != null) {
            fc2 = getMyFollowJsAttributeClass(jsAttribute, isExpressionArray);
        } else if (jsReference != null) {
            fc2 = getMyFollowReferenceClass(jsReference, isExpressionArray, genericContext);
        } else if (jsMethod != null) {
            fc2 = getMyFollowMethodClass(jsMethod, genericContext);
        } else {
            fc2 = getMyFollowClass(csn.getCompletionString(), isExpressionArray);
        }
        return fc2;
    }

    protected MyFollowClass getMyFollowJsAttributeClass(JsAttribute jsAttribute, boolean isExpressionArray) {
        MyFollowClass toReturn = null;
        if (jsAttribute != null) {
            String jsAttributeType = jsAttribute.getDataType();
            boolean isMultiple = jsAttribute.isMultiple();
            if (isMultiple && isExpressionArray) {
                toReturn = getMyFollowPropertyClass(jsAttribute, jsAttribute.getName(), jsAttributeType, false);
                return toReturn;
            }
        }
        return getMyFollowAttributeClass(jsAttribute);
    }

    @Override
    protected void addCustomCompletionString(MyFollowClass myStartFollowClass) {
        // Do nothing
    }

    /*
     * Override this method to filter scriptRelevantData elements that contain spaces in the name or starting with
     * numbers
     */
    @Override
    protected List<IScriptRelevantData> getScriptDataList() {
        List<IScriptRelevantData> iScriptRelevantDataList = new ArrayList<IScriptRelevantData>();
        if (getScriptInfoProvider() != null) {
            iScriptRelevantDataList = getScriptInfoProvider().getScriptRelevantData();
        }
        iScriptRelevantDataList = filterValidScriptData(iScriptRelevantDataList);
        return iScriptRelevantDataList;
    }

    private List<IScriptRelevantData> filterValidScriptData(List<IScriptRelevantData> iScriptRelevantDataList) {
        List<IScriptRelevantData> validScriptRelevantDataList = new ArrayList<IScriptRelevantData>();
        if (iScriptRelevantDataList != null) {
            for (Iterator<IScriptRelevantData> iterator = iScriptRelevantDataList.iterator(); iterator.hasNext();) {
                IScriptRelevantData iScriptRelevantData = iterator.next();
                if (iScriptRelevantData != null) {
                    String name = iScriptRelevantData.getName();
                    if (JScriptUtils.isValidScriptRelevantName(name)) {
                        validScriptRelevantDataList.add(iScriptRelevantData);
                    }
                }
            }
        }
        return validScriptRelevantDataList;
    }

    @Override
    protected Image getImage(Template template) {
        Image image = ScriptEditorsPlugin.getDefault().getImageRegistry().get(JsContentAssistConstants.Function);
        return image;
    }

    private IContextInformation getContextInformation() {
        return null;
    }

    @Override
    protected String getArrayContentAssistName(String name) {
        // return name + JsConsts.ARRAY_CONTENT_ASSIST_SUFFIX;
        return name;
    }

    @Override
    protected TemplateContextType getContextType(ITextViewer viewer, IRegion region) {
        return ScriptEditorsPlugin.getDefault().getJSContextTypeRegistry()
                .getContextType(JsContentAssistConstants.JS_TEMPLATE_CONTEXT);
    }

    private Map<String, IScriptRelevantData> localScriptDataMap;

    @Override
    protected Map<String, IScriptRelevantData> getLocalScriptDataMap() {
        localScriptDataMap = new HashMap<String, IScriptRelevantData>();
        if (cachedParser != null) {
            // Get the symbol table
            ISymbolTable symbolTable = cachedParser.getSymbolTable();
            if (symbolTable != null) {
                Map<String, IScriptRelevantData> localVariableMap = symbolTable.getLocalVariableMap();
                if (localVariableMap != null) {
                    Set<Entry<String, IScriptRelevantData>> entrySet = localVariableMap.entrySet();
                    for (Iterator<Entry<String, IScriptRelevantData>> iterator = entrySet.iterator(); iterator
                            .hasNext();) {
                        Entry<String, IScriptRelevantData> next = iterator.next();
                        String key = next.getKey();
                        IScriptRelevantData localVariableType = next.getValue();
                        if (localVariableType != null) {
                            localVariableType.setIcon(getImage(null));
                            localScriptDataMap.put(key, localVariableType);
                        }

                    }
                }
            }
        }
        return localScriptDataMap;
    }

    private Map<String, IScriptRelevantData> globalScriptDataMap;

    @Override
    protected Map<String, IScriptRelevantData> getGlobalScriptDataMap() {
        globalScriptDataMap = new HashMap<String, IScriptRelevantData>();
        if (cachedParser != null) {
            // Get the symbol table
            ISymbolTable symbolTable = cachedParser.getSymbolTable();
            if (symbolTable != null) {
                Map<String, IScriptRelevantData> globalVariableMap = symbolTable.getScriptRelevantDataTypeMap();
                if (globalVariableMap != null) {
                    Set<Entry<String, IScriptRelevantData>> entrySet = globalVariableMap.entrySet();
                    for (Iterator<Entry<String, IScriptRelevantData>> iterator = entrySet.iterator(); iterator
                            .hasNext();) {
                        Entry<String, IScriptRelevantData> next = iterator.next();
                        String key = next.getKey();
                        IScriptRelevantData globalVariableType = next.getValue();
                        if (globalVariableType != null) {
                            if (globalVariableType.getIcon() == null) {

                                globalVariableType.setIcon(getImage(null));
                            }
                            globalVariableMap.put(key, globalVariableType);
                        }

                    }
                }
            }
        }
        return globalScriptDataMap;
    }

    /**
     * @see com.tibco.xpd.script.sourceviewer.internal.contentassist.AbstractTibcoContentAssistProcessor#getGrammarType()
     * 
     * @return
     */
    @Override
    protected String getGrammarType() {
        return JsContentAssistConstants.JAVASCRIPT_GRAMMAR;
    }

    /**
     * @see com.tibco.xpd.script.sourceviewer.internal.contentassist.AbstractTibcoContentAssistProcessor#getCommonUIPreferenceNames()
     * 
     * @return
     * @throws CoreException
     */
    @Override
    protected AbstractScriptCommonUIPreferenceNames getCommonUIPreferenceNames() {
        try {
            return ScriptGrammarContributionsUtil.INSTANCE.getScriptCommonUIPreferenceNames(getGrammarType());
        } catch (CoreException e) {
            LOG.error(e);
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.script.sourceviewer.internal.contentassist.AbstractTibcoContentAssistProcessor#getPreferenceStore()
     * 
     * @return
     */
    @Override
    protected IPreferenceStore getPreferenceStore() {
        return ScriptEditorsPlugin.getDefault().getPreferenceStore();
    }

    protected void parseScript(String latestScript) {
        // Parse the script
        List<IValidationStrategy> validationStrategyList = getValidationStrategyList();
        if (validationStrategyList == null) {
            validationStrategyList = new ArrayList<IValidationStrategy>();
        }

        ISymbolTable symbolTable = new SymbolTable();

        Map<String, IScriptRelevantData> scriptRelevantDataTypeMap = new HashMap<String, IScriptRelevantData>();
        List<IScriptRelevantData> scriptRelevantDataList = getScriptDataList();
        for (Iterator<IScriptRelevantData> iterator = scriptRelevantDataList.iterator(); iterator.hasNext();) {
            IScriptRelevantData scriptRelevantData = iterator.next();
            if (scriptRelevantData != null) {
                scriptRelevantDataTypeMap.put(scriptRelevantData.getName(), scriptRelevantData);
            }
        }
        symbolTable.setScriptRelevantDataTypeMap(scriptRelevantDataTypeMap);
        cachedParser = ContentAssistUtil.validateScript(latestScript,
                new ArrayList<String>(),
                validationStrategyList,
                symbolTable,
                new DefaultVarNameResolver());
    }
}
