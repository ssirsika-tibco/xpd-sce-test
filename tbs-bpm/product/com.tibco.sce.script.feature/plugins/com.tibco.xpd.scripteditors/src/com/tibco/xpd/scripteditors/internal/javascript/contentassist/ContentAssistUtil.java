/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.scripteditors.internal.javascript.contentassist;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import antlr.RecognitionException;
import antlr.Token;
import antlr.TokenStreamException;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.script.parser.antlr.JScriptLexer;
import com.tibco.xpd.script.parser.antlr.JScriptParser;
import com.tibco.xpd.script.parser.internal.validator.IVarNameResolver;
import com.tibco.xpd.script.parser.validator.ISymbolTable;
import com.tibco.xpd.script.parser.validator.IValidationStrategy;
import com.tibco.xpd.script.parser.validator.jscript.JScriptValidationStrategy;

/**
 * @author rsomayaj
 * 
 */
public class ContentAssistUtil {

    public static final String PLUGIN_ID = "com.tibco.xpd.process.js.parser"; //$NON-NLS-1$

    public static final String VALIDATION_STRATEGY = "validationStrategy"; //$NON-NLS-1$   

    public static final String CLASS_NAME = "className"; //$NON-NLS-1$  

    public static final String GRAMMAR_TYPE = "grammarType"; //$NON-NLS-1$ 

    private static Map<String, List<IValidationStrategy>> validationStrategyForGrammar =
            null;

    public static JScriptParser validateScript(String strScript,
            List<String> destinationList,
            List<IValidationStrategy> validationStrategyList,
            ISymbolTable symbolTable, IVarNameResolver varNameResolver) {
        if (strScript == null || strScript.trim().length() < 1) {
            return null;
        }
        if (!strScript.endsWith("\n")) { //$NON-NLS-1$
            strScript += "\n"; //$NON-NLS-1$
        }
        JScriptParser parser = getScriptParser(strScript);
        if (parser == null) {
            return null;
        }
        parser.setValidationStrategyList(validationStrategyList);
        parser.setVarNameResolver(varNameResolver);
        parser.setSymbolTable(symbolTable);
        try {
            /*
             * XPD-2896: we were validating the script in content assist. now we
             * are setting the boolean flag to disable the validation in content
             * assist thus giving much improvement in performance
             */
            parser.compilationUnit(false);
        } catch (RecognitionException e) {
            return parser;
        } catch (TokenStreamException e) {
            return parser;
        }
        return parser;
    }

    private static JScriptParser getScriptParser(String strScript) {
        StringReader reader = new StringReader(strScript);
        JScriptLexer lexer = new JScriptLexer(reader);
        JScriptParser parser = new JScriptParser(lexer);
        Token token;
        try {
            token = parser.LT(1);
            // check there to handle case when there is only comments in the
            // script
            if (token != null && token.getText() != null) {
                return parser;
            }
        } catch (TokenStreamException e) {
            // Don' worry about the errors now
        }
        return null;
    }

    public static List<IValidationStrategy> getAllValidationStrategies(
            String grammarType) {
        List<IValidationStrategy> validationList =
                getValidationStrategy(grammarType);
        if (validationList.isEmpty()) {
            validationList.add(new JScriptValidationStrategy());
        }
        return validationList;
    }

    /**
     * Returns a list of validation strategies depending upon the passed
     * grammarType.
     * 
     * @param grammarType
     * @return
     */
    private static List<IValidationStrategy> getValidationStrategy(
            String grammarType) {
        if (validationStrategyForGrammar == null) {
            readContributedValidationStrategy();
        }
        return validationStrategyForGrammar.get(grammarType);
    }

    private static void readContributedValidationStrategy() {
        IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
        IConfigurationElement[] configurationElementsFor =
                extensionRegistry.getConfigurationElementsFor(PLUGIN_ID,
                        VALIDATION_STRATEGY);
        validationStrategyForGrammar =
                new HashMap<String, List<IValidationStrategy>>();
        for (IConfigurationElement element : configurationElementsFor) {
            try {
                if (element != null) {
                    // Get the Grammar Type
                    String elementGrammarType =
                            element.getAttribute(GRAMMAR_TYPE);
                    if (elementGrammarType != null) {
                        List<IValidationStrategy> existingValidationStrategies =
                                validationStrategyForGrammar
                                        .get(elementGrammarType);
                        if (existingValidationStrategies == null) {
                            existingValidationStrategies =
                                    new ArrayList<IValidationStrategy>();
                        }
                        Object object =
                                element.createExecutableExtension(CLASS_NAME);
                        if (object instanceof IValidationStrategy) {
                            IValidationStrategy tempProvider =
                                    (IValidationStrategy) object;
                            if (!existingValidationStrategies
                                    .contains(tempProvider)) {
                                existingValidationStrategies.add(tempProvider);
                                validationStrategyForGrammar
                                        .put(elementGrammarType,
                                                existingValidationStrategies);
                            }
                        }
                    }
                }
            } catch (CoreException e) {
                XpdResourcesPlugin.getDefault().getLogger().error(e);
            }
        }
    }

}
