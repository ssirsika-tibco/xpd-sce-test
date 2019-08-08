/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.resources.postimport;

import java.util.Collection;
import java.util.Collections;

import com.tibco.xpd.analyst.resources.xpdl2.ReservedWords;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.script.parser.antlr.JScriptParser;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;

import antlr.Token;
import antlr.TokenStreamException;

/**
 * Get a script text replacement for the given top level identifier token IF it represents a BOM factory (e.g.
 * "com_my_bom_Factory.createClass1()")
 */
class BOMFactoryRefactor implements ScriptRefactorRule {
    /**
     * The AMX BPM BOM factory class suffix (as in "com_my_bom_Factory.createXXX()"
     */
    private static final String BOM_FACTORY_SUFFIX = "_Factory"; //$NON-NLS-1$

    /**
     * The AMX BPM BOM factory class suffix (as in "com_my_bom_Factory.createXXX()"
     */
    private static final String BOM_CLASS_CREATE_METHOD_PREFIX = "create"; //$NON-NLS-1$

    /**
     * The approach we take is fairly basic and based only on the text we know must appear for factory classes (i.e.
     * that there is a top level identifier that ends in "_Factory" followed by a creator function identifier that
     * starts with "create")
     * 
     * We could be more clever and check against a list of available BOM packages in scope of the script BUT that would
     * be slower and if the BOM package hadn't been migrated then would cause us problems.
     * 
     * @see com.tibco.xpd.n2.resources.postimport.ScriptRefactorRule#isMatch(antlr.Token,
     *      com.tibco.xpd.script.parser.antlr.JScriptParser, int)
     */
    @Override
    public boolean isMatch(Token aToken, JScriptParser aParser, int aIndex) throws TokenStreamException {
        Token prevToken = (aIndex <= 1) ? null : aParser.LT(aIndex - 1);

        if (aToken != null && aToken.getType() == JScriptTokenTypes.IDENT) {
            if (prevToken == null || prevToken.getType() != JScriptTokenTypes.DOT) {
                String identifierText = aToken.getText();

                if (identifierText != null && identifierText.endsWith(BOM_FACTORY_SUFFIX)) {
                    Token nextToken = aParser.LT(++aIndex);

                    if (nextToken != null && nextToken.getType() == JScriptTokenTypes.DOT) {
                        nextToken = aParser.LT(++aIndex);

                        if (nextToken != null && nextToken.getType() == JScriptTokenTypes.IDENT
                                && nextToken.getText() != null
                                && nextToken.getText().startsWith(BOM_CLASS_CREATE_METHOD_PREFIX)) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * @see com.tibco.xpd.n2.resources.postimport.ScriptRefactorRule#getReplacements(antlr.Token,
     *      com.tibco.xpd.script.parser.antlr.JScriptParser, int)
     */
    @Override
    public Collection<ScriptItemReplacementRef> getReplacements(Token aToken, JScriptParser aParser, int aIndex)
            throws TokenStreamException {
        String identifierText = aToken.getText();
        String newFactoryName = identifierText.substring(0, identifierText.length() - BOM_FACTORY_SUFFIX.length());

        String newIdentifierText =
                ReservedWords.BOM_FACTORY_WRAPPER_OBJECT_NAME + ConceptPath.CONCEPTPATH_SEPARATOR + newFactoryName;

        return Collections.singleton(new ScriptItemReplacementRef(aToken, newIdentifierText));
    }
}
