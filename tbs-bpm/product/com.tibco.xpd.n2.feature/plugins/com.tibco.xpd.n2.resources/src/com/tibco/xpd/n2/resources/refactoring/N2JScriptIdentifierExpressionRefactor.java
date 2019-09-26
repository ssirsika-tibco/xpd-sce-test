/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.n2.resources.refactoring;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.jscript.JScriptUtils;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
import com.tibco.xpd.script.parser.internal.expr.IExpr;
import com.tibco.xpd.script.parser.internal.refactoring.RefactorResult;
import com.tibco.xpd.script.parser.internal.refactoring.RefactoringInfo;
import com.tibco.xpd.script.parser.internal.refactoring.jscript.JScriptIdentifierExpressionRefactor;

import antlr.Token;
import antlr.collections.AST;

/**
 * @author mtorres
 * 
 *         ExpressionValidator class that handles the validation of an
 *         identifier ie: field;
 * 
 */
public class N2JScriptIdentifierExpressionRefactor extends
        JScriptIdentifierExpressionRefactor {

    private final static String FACTORY_END_QUALIFIER = "_Factory";//$NON-NLS-1$

    @Override
    @SuppressWarnings("restriction")
    public RefactorResult evaluate(IExpr expresion) {
        if (expresion != null) {
            Object expr = expresion.getExpr();
            Object token = expresion.getToken();
            if (expr instanceof AST && token instanceof Token) {
                AST astExpr = (AST) expr;
                Token antlrToken = (Token) token;
                if (astExpr != null && antlrToken != null) {
                    switch (astExpr.getType()) {
                    case JScriptTokenTypes.IDENT:
                        String identifierText = astExpr.getText();
                        if (identifierText != null) {
                            IScriptRelevantData identifierScriptRelevantDataType =
                                    JScriptUtils
                                            .getIdentifierScriptRelevantDataType(identifierText,
                                                    getSupportedJsClasses(),
                                                    getSupportedGlobalProperties(),
                                                    getSupportedScriptRelevantDataMap(),
                                                    getLocalVariablesMap(),
                                                    getLocalMethodsMap(),
                                                    getScriptRelevantDataFactory(),
                                                    null);
                            if (identifierScriptRelevantDataType != null) {
                                RefactoringInfo info = getRefactoringInfo();
                                RefactorResult result = new RefactorResult();
                                result.setType(identifierScriptRelevantDataType);
                                if (info != null) {
                                    EObject refactoredElement =
                                            info.getRefactoredElement();
                                    String oldValue = info.getOldValue();
                                    String newValue = info.getNewValue();
                                    if (oldValue != null && newValue != null) {
                                        /*
                                         * Sid ACE-3153 BOM package rename now handled in N2JSDotExpressionRefactor as
                                         * they are children of factory.xxx or pkg.xxx
                                         * 
                                         * removed pkg / enumeration handling from here.
                                         * 
                                         * In fact, now that there are no root objects left that the user decides on the
                                         * name of (because everything is wrapped in factory or pkg class (and data
                                         * fields are handled elsewhere), then there is nothing to do here for the root
                                         * level identifier.
                                         */

                                    }
                                    return result;
                                }
                            }
                        }
                        break;
                    default:
                        break;
                    }
                }
            }
        }
        return null;
    }


}
