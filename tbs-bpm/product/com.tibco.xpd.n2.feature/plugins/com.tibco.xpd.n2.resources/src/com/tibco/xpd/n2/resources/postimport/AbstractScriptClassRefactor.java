/*
 * (c) 2004-2023 Cloud Software Group, Inc.
 */

package com.tibco.xpd.n2.resources.postimport;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.tibco.xpd.script.parser.antlr.JScriptParser;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;

import antlr.Token;
import antlr.TokenStreamException;

/**
 * Refactor an AMX BPM script utility class (and optionally it's methods) into the equivalent BPMe class
 * 
 * (Content copied from original {@link ScriptUtilRefactor} with minor changes to pass in oldClassName and
 * newClassName).
 *
 * @author aallway
 * @since Jan 2023
 */
class AbstractScriptClassRefactor implements ScriptRefactorRule {
    private String oldClassName;

    private String newClassName;

    private final Map<String, String> methodReplacements;

    private boolean preserveOtherClassContent;

    /**
     * Creates a MethodRefactorRule to replace multiple method names with a corresponding value in the given class and
     * also change the script class name.
     * 
     * @param aOldClassName
     *            the old script utility class name
     * @param aNewClassName
     *            the new script utility class name
     * @param aMethodMap
     *            the map of method names to be replaced. The key is the old method name.
     * @param aPreserveOtherClassContent
     *            if <code>false</code> then if class content (methods, properties) other than the methods in aMethodMap
     *            are found then no migration will be performed. If <code>true</code> then methods and properties not in
     *            the aMethodMap will be migrated with the same name as existing method/proeprty.
     */
    public AbstractScriptClassRefactor(String aOldClassName, String aNewClassName, Map<String, String> aMethodMap,
            boolean aPreserveOtherClassContent) {
        oldClassName = aOldClassName;
        newClassName = aNewClassName;
        methodReplacements = new HashMap<>(aMethodMap);
        preserveOtherClassContent = aPreserveOtherClassContent;
    }

    /**
     * @see com.tibco.xpd.n2.resources.postimport.ScriptRefactorRule#isMatch(antlr.Token,
     *      com.tibco.xpd.script.parser.antlr.JScriptParser, int)
     */
    @Override
    public boolean isMatch(Token aToken, JScriptParser aParser, int aIndex) throws TokenStreamException {
        if ((aToken.getType() != JScriptTokenTypes.IDENT) ||
                (!oldClassName.equals(aToken.getText()))) {
            return false;
        }
        
        // ensure that the token is for the old method name and is preceeded by a dot
        Token dotToken = aParser.LT(aIndex + 1);
        Token methodToken = aParser.LT(aIndex + 2);
        Token openParen = aParser.LT(aIndex + 3);

        if ((dotToken == null) || (dotToken.getType() != JScriptTokenTypes.DOT) //
                || (methodToken == null) || (methodToken.getType() != JScriptTokenTypes.IDENT) //
                || (openParen == null) || (openParen.getType() != JScriptTokenTypes.LPAREN)) {
            /*
             * Sid ACE-6615 if we're preserving the original methods and content then preserve static fields as well as
             * methods.
             */
            if (preserveOtherClassContent) {
                return true;
            } else {
                return false;
            }
        }

        /*
         * Sid ACE-6615 if ignoring migration for methods that are not mapped to a new name, then only convert util lass
         * name and method name if the existing method has a new counterpart in the method map.
         */
        if (!preserveOtherClassContent) {
            if (!methodReplacements.containsKey(methodToken.getText())) {
                return false;
            }
        }

        return true;
    }

    /**
     * @see com.tibco.xpd.n2.resources.postimport.ScriptRefactorRule#getReplacements(antlr.Token,
     *      com.tibco.xpd.script.parser.antlr.JScriptParser, int)
     */
    @Override
    public Collection<ScriptItemReplacementRef> getReplacements(Token aToken, JScriptParser aParser, int aIndex)
            throws TokenStreamException {
        Token methodToken = aParser.LT(aIndex + 2);
        String newMethod = methodReplacements.get(methodToken.getText());

        /*
         * Sid ACE-6615 if we're preserving existing methods then there won't be a name in the map, so default to
         * original name.
         */
        if (newMethod == null) {
            newMethod = methodToken.getText();
        }

        return Arrays.asList(new ScriptItemReplacementRef(aToken, newClassName),
                new ScriptItemReplacementRef(methodToken, newMethod));
    }

    /**
     * Provides additional filtering to the {@link MethodNameRefactor}.
     */
    public static interface MethodFilter {
        /**
         * Returns <code>true</code> if the filter accepts the token(s) identified by the parser and index.
         * 
         * @param aParser
         *            the parser from which tokens are traversed.
         * @param aIndex
         *            the index of the field name token to be resolved.
         * @return <code>true</code> if filter accepts the token.
         */
        public boolean accept(JScriptParser aParser, int aIndex) throws TokenStreamException;
    }
}
