/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.resources.postimport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.ReservedWords;
import com.tibco.xpd.analyst.resources.xpdl2.migrate.IMigrationCommandInjector;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.n2.resources.BundleActivator;
import com.tibco.xpd.process.js.parser.util.ScriptParserUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.script.parser.antlr.JScriptLexer;
import com.tibco.xpd.script.parser.antlr.JScriptParser;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.resources.XpdlMigrate;
import com.tibco.xpd.xpdl2.util.ResetExpressionContentCommand;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

import antlr.Token;
import antlr.TokenStreamException;

/**
 * XPDL Migration command injector for migrating AMX BPM script to ACE semantics
 *
 * @author aallway
 * @since 11 Jul 2019
 */
public class Bpm2CeProcessScriptMigration implements IMigrationCommandInjector {

    /**
     * Store the original FormarVersion of the XPDL we are migrating. We ONLY want to migrate scripts that have not
     * already been migrated to ACE, the initial FormatVersion for ACE is 1000 so we will migrate only if FormatVersion
     * is before the initial ACE one.
     */
    private int originalFormatVersion;

    /**
     * Log some stats...
     */
    private static int totalPackages = 0;

    private static int totalScripts = 0;

    private static long totalTime = 0;

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.migrate.IMigrationCommandInjector#setOriginalFormatVersion(int)
     *
     * @param originalFormatVersion
     */
    @Override
    public void setOriginalFormatVersion(int originalFormatVersion) {
        this.originalFormatVersion = originalFormatVersion;
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.migrate.IMigrationCommandInjector#getCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      com.tibco.xpd.xpdl2.Package)
     *
     * @param editingDomain
     * @param pkg
     * @return
     */
    @Override
    public Command getCommand(EditingDomain editingDomain, Package pkg) {
        totalPackages++;

        if (originalFormatVersion >= XpdlMigrate.ACE_INITIAL_FORMAT_VERSION) {
            /**
             * Store the original FormarVersion of the XPDL we are migrating. We ONLY want to migrate scripts that have
             * not already been migrated to ACE, the initial FormatVersion for ACE is 1000 so we will NOT migrate if the
             * XPDL FormatVersion is on or after the initial ACE one.
             */
            return null;
        }

        long startTime = System.nanoTime();
        int numScripts = 0;

        CompoundCommand cmd = new CompoundCommand("Migrate JavaScript"); //$NON-NLS-1$

        for (TreeIterator<EObject> iterator = pkg.eAllContents(); iterator.hasNext();) {
            EObject eo = iterator.next();

            if (eo instanceof Expression) {
                Expression expression = (Expression) eo;

                if (ScriptGrammarFactory.JAVASCRIPT.equals(expression.getScriptGrammar())) {
                    numScripts++;

                    // TODO EXCLUDE JAVASCRIPT xpdl2:Actual IN DATA MAPPINGs
                    if (!ignoreScript(expression)) {
                        String newScript = highLevelConvert(expression);

                        if (newScript != null) {
                            cmd.append(new ResetExpressionContentCommand((TransactionalEditingDomain) editingDomain,
                                    "Migrate JavaScript", expression, //$NON-NLS-1$
                                    newScript));
                        }
                    }
                }
            }
        }

        long nanoTimeTaken = System.nanoTime() - startTime;

        totalScripts += numScripts;
        totalTime += nanoTimeTaken;

        BundleActivator.getDefault().getLogger().info(String.format(
                "Migrated %d scripts in %d.%09d seconds for %s (Totals - Packages(%d) Scripts(%d) Total-Time(%d.%09d)\n", //$NON-NLS-1$
                numScripts,
                (nanoTimeTaken / 1000000000),
                (nanoTimeTaken % 1000000000),
                WorkingCopyUtil.getFile(pkg).getFullPath(),
                totalPackages,
                totalScripts,
                (totalTime / 1000000000),
                (totalTime % 1000000000)));

        return cmd.isEmpty() ? null : cmd;
    }

    /**
     * In Studio, sometimes xpdl2:Expression types are used
     * 
     * @param expression
     * @return
     */
    private boolean ignoreScript(Expression expression) {
        /*
         * Ignore <xpdl2:DataMapping>/<xpdl2:Actual> as this is an xpdl2:Expression type BUT is only the source/target
         * mapper tree content path (and should not be mangled and migrated here).
         * 
         * Note that actual script mappings are always defined in xpdExt:ScriptInformation elements now in DataMappr
         * mapping scenarios (which is all that is supported in ACE).
         */
        return (expression == null)
                || (Xpdl2Package.eINSTANCE.getDataMapping_Actual().equals(expression.eContainingFeature()));
    }

    /**
     * Perform the migrations from BPM script to ACE script.
     * 
     * @param expression
     *            The script to convert.
     * 
     * @return the new script.
     */
    private String highLevelConvert(Expression expression) {
        String newScript = null;

        if (expression != null) {
            String scriptText = expression.getText();
            if (scriptText != null && !scriptText.isEmpty()) {

                Collection<RefactorRule> replacementRules = getRefactorRules(expression);
                if (!replacementRules.isEmpty()) {
                    // find all replacements
                    List<ScriptItemReplacementRef> replacements = parseScript(scriptText, replacementRules);

                    // apply all replacements
                    newScript = convertScript(scriptText, replacements);
                }
            }
        }

        return newScript;
    }

    /**
     * Build the RefactorRules to be applied to the script. Uses the given Expression to derive the data items, so that
     * the rules may be data aware.
     */
    private Collection<RefactorRule> getRefactorRules(Expression expression) {
        Collection<RefactorRule> result = new ArrayList<>();

        // for static classes to the old-name->new-name top level identifiers map.
        result.add(new BOMFactoryReplacement());
        result.add(new StaticRefReplacement("Process", "bpm.process")); //$NON-NLS-1$ //$NON-NLS-2$
        result.add(new StaticRefReplacement("WorkManagerFactory", "bpm.workManager")); //$NON-NLS-1$ //$NON-NLS-2$

        Collection<ProcessRelevantData> inScopeData = getInScopeData(expression);
        if (!inScopeData.isEmpty()) {
            // for all the in-scope data fields to the old-name->new-name top level identifiers map
            result.add(new TopLevelFieldIdReplacement(inScopeData));
        }

        // add array method refactors
        result.add(new ArrayAccessorReplacement());
        result.add(new MethodRefactorRule("add", "push")); //$NON-NLS-1$//$NON-NLS-2$
        result.add(new MethodRefactorRule("addAll", "pushAll")); //$NON-NLS-1$ //$NON-NLS-2$

        return result;
    }

    /**
     * Get all the data in scope of the activity (if it's a script under an activity) else the process
     * 
     * @param expression
     * @return The data in scope of the given expression.
     */
    private Collection<ProcessRelevantData> getInScopeData(Expression expression) {

        /*
         * If the expression is in a conditional flow, then we need to get the data available in it's container.
         */
        Transition seqFlowParent = (Transition) Xpdl2ModelUtil.getAncestor(expression, Transition.class);
        if (seqFlowParent != null) {
            return ProcessInterfaceUtil.getAllAvailableRelevantDataForSequenceFlow(seqFlowParent);
        }

        /* If the expression is in an activity get all the data available to it. */
        Activity activity = Xpdl2ModelUtil.getParentActivity(expression);
        if (activity != null) {
            return ProcessInterfaceUtil.getAllAvailableRelevantDataForActivity(activity);
        }

        /* Fall back on all data in process. */
        Process process = Xpdl2ModelUtil.getProcess(expression);
        if (process != null) {
            return ProcessInterfaceUtil.getAllProcessRelevantData(process);
        }

        return Collections.emptyList();
    }

    /**
     * Parses the given script and creates a collection of ScriptItemReplacementRef. Each ScriptItemReplacementRef is
     * able to perform a migration of a given part of the script, by replacing the section of the script it identifies
     * with the migrated value.
     * 
     * Shamelessly ripped out of {@link ScriptParserUtil#replaceDataRefByName(String, Map)} and modified as the use case
     * here will grow to be more than just simple name reference replacement.
     * 
     * @param strScript
     *            the script
     * @param oldTopLevelIdent2NewNameMap
     *            Top level identifier (process fields, static classes etc) map to new names/path.
     * 
     * @return the collection of ScriptItemReplacementRef that describe how the migrations are to be applied to the
     *         script.
     * 
     */
    private List<ScriptItemReplacementRef> parseScript(String strScript, Collection<RefactorRule> aReplacementRules) {
        if (strScript == null || strScript.trim().length() == 0) {
            return Collections.emptyList();
        }

        // Make sure we are newline terminated else get exceptions.
        String fixedScript;
        if (!strScript.endsWith("\n")) { //$NON-NLS-1$
            fixedScript = strScript + "\n"; //$NON-NLS-1$
        } else {
            fixedScript = strScript;
        }

        StringReader reader = new StringReader(fixedScript);
        JScriptLexer lexer = new JScriptLexer(reader);

        // THIS IS REALLY IMPORTANT - SET TAB SIZE TO ZERO - LExer counts tabs
        // as 8 chars by default when calculating the 'column' for a token.
        lexer.setTabSize(1);

        JScriptParser scriptParser = new JScriptParser(lexer);

        // search for all occurrences of any field in the list to
        // replace - keeping track of the line and column number.
        List<ScriptItemReplacementRef> references = new ArrayList<>();

        try {
            int tokenIdx = 1;
            Token token = scriptParser.LT(tokenIdx);
            while ((token != null) && (token.getType() != Token.EOF_TYPE)) {
                // find the first matching rule
                for (RefactorRule rule : aReplacementRules) {
                    if (rule.isMatch(scriptParser, tokenIdx)) {
                        // add the rule's replacements
                        references.addAll(rule.getReplacements(scriptParser, tokenIdx));
                        break;
                    }
                }

                // Get next token from sript
                token = scriptParser.LT(++tokenIdx);
            }
        } catch (TokenStreamException e) {
            // ON exception return empty replacement list
            // - can't do it too many problems for lexer to cope with.
            return Collections.emptyList();
        }

        return references;
    }

    /**
     * Applies the given collection of ScriptItemReplacementRef to the given script.
     * 
     * @param aScript
     *            the script to be migrated.
     * @param aReplacements
     *            the migration replacements to be applied to the script.
     * @return the migrated script.
     */
    private String convertScript(String aScript, List<ScriptItemReplacementRef> aReplacements) {
        if ((aScript == null) || (aScript.trim().length() == 0) || (aReplacements.isEmpty())) {
            return aScript;
        }

        // Attempt to preserve original line termination
        String lineTermination;
        if (aScript.contains("\r\n")) { //$NON-NLS-1$
            lineTermination = "\r\n"; //$NON-NLS-1$
        } else if (aScript.contains("\r")) { //$NON-NLS-1$
            lineTermination = "\r"; //$NON-NLS-1$
        } else {
            lineTermination = "\n"; //$NON-NLS-1$
        }

        // Go thru line by line.
        BufferedReader inputScript = new BufferedReader(new StringReader(aScript));

        // Sort references by ascending line number then descending column number.
        Collections.sort(aReplacements);

        // The output script.
        StringBuilder outScript = new StringBuilder();

        // Track the input line number.
        int inputLineNum = 1;

        // The current reference idx
        int refIdx = 0;
        int numRefs = aReplacements.size();

        ScriptItemReplacementRef ref = aReplacements.get(refIdx);

        while (refIdx < numRefs) {
            // Wind on thru input script till we get to the given line.
            int currRefLine = ref.line;
            while (inputLineNum < currRefLine) {
                String line = nextScriptLine(inputScript);
                inputLineNum++;

                outScript.append(line).append(lineTermination);
            }

            // The input has caught up with the next line to replace reference
            // on.
            // Get the line to replace refs on and go thru refs making
            // replacements.
            String line = nextScriptLine(inputScript);
            inputLineNum++;

            StringBuilder lineBuff = new StringBuilder(line);

            // Don't worry! Refs are from last on line to first on line order,
            // so simple iteration is ok.
            while (ref.getLine() == currRefLine) {
                ref.replaceRef(lineBuff);

                // OK, that's this reference replaced, get next.
                refIdx++;
                if (refIdx >= numRefs) {
                    break;
                }

                ref = aReplacements.get(refIdx);
            }

            // That's all the refs finished in this line. output and continue.
            outScript.append(lineBuff).append(lineTermination);
        }

        // Output remainder of script.
        String line = nextScriptLine(inputScript);
        while (line != null) {
            outScript.append(line).append(lineTermination);
            line = nextScriptLine(inputScript);
        }

        return outScript.toString();
    }

    /**
     * Get next line from buffer or null at end of stream.
     * 
     * @param inputScript
     * @return
     */
    private String nextScriptLine(BufferedReader inputScript) {
        try {
            return inputScript.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ReplacementRules define patterns that identify elements of the JavaScript that is to be replaced with new values.
     * For example; a method may have been refactored with a new name. In which case, a rule would identify uses of the
     * original method name and create ScriptItemReplacementRefs to replace them.
     *
     * @author pwatson
     * @since 23 Jul 2019
     */
    private static interface RefactorRule {
        public boolean isMatch(JScriptParser aParser, int aIndex) throws TokenStreamException;

        public Collection<ScriptItemReplacementRef> getReplacements(JScriptParser aParser, int aIndex)
                throws TokenStreamException;
    }

    /**
     * Get a script text replacement for the given top level identifier token IF it represents a BOM factory (e.g.
     * "com_my_bom_Factory.createClass1()")
     */
    private static class BOMFactoryReplacement implements RefactorRule {
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
         * We could be more clever and check against a list of available BOM packages in scope of the script BUT that
         * would be slower and if the BOM package hadn't been migrated then would cause us problems.
         * 
         * @see com.tibco.xpd.n2.resources.postimport.Bpm2CeProcessScriptMigration.RefactorRule#isMatch(com.tibco.xpd.script.parser.antlr.JScriptParser,
         *      int)
         */
        @Override
        public boolean isMatch(JScriptParser aParser, int aIndex) throws TokenStreamException {
            Token prevToken = (aIndex <= 1) ? null : aParser.LT(aIndex - 1);
            Token token = aParser.LT(aIndex);

            if (token != null && token.getType() == JScriptTokenTypes.IDENT) {
                if (prevToken == null || prevToken.getType() != JScriptTokenTypes.DOT) {
                    String identifierText = token.getText();

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
         * @see com.tibco.xpd.n2.resources.postimport.Bpm2CeProcessScriptMigration.RefactorRule#getReplacements(com.tibco.xpd.script.parser.antlr.JScriptParser,
         *      int)
         */
        @Override
        public Collection<ScriptItemReplacementRef> getReplacements(JScriptParser aParser, int aIndex)
                throws TokenStreamException {
            Token token = aParser.LT(aIndex);
            String identifierText = token.getText();
            String newFactoryName = identifierText.substring(0, identifierText.length() - BOM_FACTORY_SUFFIX.length());

            String newIdentifierText =
                    ReservedWords.BOM_FACTORY_WRAPPER_OBJECT_NAME + ConceptPath.CONCEPTPATH_SEPARATOR + newFactoryName;

            return Collections.singleton(new ScriptItemReplacementRef(token, newIdentifierText));
        }
    }

    /**
     * Identifies top-level data field references by name and refactors them with new names.
     */
    private static class StaticRefReplacement implements RefactorRule {
        private final String oldIdent;

        private final String newIdent;

        public StaticRefReplacement(String aIdent, String aReplacement) {
            oldIdent = aIdent;
            newIdent = aReplacement;
        }

        /**
         * @see com.tibco.xpd.n2.resources.postimport.Bpm2CeProcessScriptMigration.RefactorRule#isMatch(com.tibco.xpd.script.parser.antlr.JScriptParser,
         *      int)
         */
        @Override
        public boolean isMatch(JScriptParser aParser, int aIndex) throws TokenStreamException {
            Token prevToken = (aIndex <= 1) ? null : aParser.LT(aIndex - 1);
            Token token = aParser.LT(aIndex);

            if (token != null && token.getType() == JScriptTokenTypes.IDENT) {
                if (prevToken == null || prevToken.getType() != JScriptTokenTypes.DOT) {
                    // IDENT is either a symbol (data field / formal param) or a class property/method (such as
                    // DateTime.Date)
                    //
                    // We only want to change data fields / params so we will ensure that previous token is not ".".
                    // This is very crude, but at this point we do not have the ability to distinguish between them.

                    return (token.getType() == JScriptTokenTypes.IDENT) && (oldIdent.equals(token.getText()))
                            && (prevToken == null || prevToken.getType() != JScriptTokenTypes.DOT);
                }
            }

            return false;
        }

        /**
         * @see com.tibco.xpd.n2.resources.postimport.Bpm2CeProcessScriptMigration.RefactorRule#getReplacements(com.tibco.xpd.script.parser.antlr.JScriptParser,
         *      int)
         */
        @Override
        public Collection<ScriptItemReplacementRef> getReplacements(JScriptParser aParser, int aIndex)
                throws TokenStreamException {
            Token token = aParser.LT(aIndex);
            return Collections.singleton(new ScriptItemReplacementRef(token, newIdent));
        }
    }

    /**
     * Identifies top-level data field references by name and refactors them by prefixing the data wrapper object.
     */
    private static class TopLevelFieldIdReplacement implements RefactorRule {
        private final Map<String, String> oldIdentMap = new HashMap<>();

        public TopLevelFieldIdReplacement(Collection<ProcessRelevantData> inScopeData) {
            // for all the in-scope data fields to the old-name->new-name top level identifiers map
            for (ProcessRelevantData data : inScopeData) {
                String fieldName = data.getName();

                // rule for OldProcessFieldName to bpm.OldProcessFieldName
                oldIdentMap.put(fieldName,
                        ReservedWords.PROCESS_DATA_WRAPPER_OBJECT_NAME + ConceptPath.CONCEPTPATH_SEPARATOR + fieldName);
            }
        }

        /**
         * @see com.tibco.xpd.n2.resources.postimport.Bpm2CeProcessScriptMigration.RefactorRule#isMatch(com.tibco.xpd.script.parser.antlr.JScriptParser,
         *      int)
         */
        @Override
        public boolean isMatch(JScriptParser aParser, int aIndex) throws TokenStreamException {
            Token prevToken = (aIndex <= 1) ? null : aParser.LT(aIndex - 1);
            Token token = aParser.LT(aIndex);

            // IDENT is either a symbol (data field / formal param) or a class property/method (such as
            // DateTime.Date)
            //
            // We only want to change data fields / params so we will ensure that previous token is not ".".
            // This is very crude, but at this point we do not have the ability to distinguish between them.

            if ((token != null) && (token.getType() == JScriptTokenTypes.IDENT)
                    && (prevToken == null || prevToken.getType() != JScriptTokenTypes.DOT)) {
                return oldIdentMap.containsKey(token.getText());
            }

            return false;
        }

        /**
         * @see com.tibco.xpd.n2.resources.postimport.Bpm2CeProcessScriptMigration.RefactorRule#getReplacements(com.tibco.xpd.script.parser.antlr.JScriptParser,
         *      int)
         */
        @Override
        public Collection<ScriptItemReplacementRef> getReplacements(JScriptParser aParser, int aIndex)
                throws TokenStreamException {
            Token token = aParser.LT(aIndex);
            return Collections.singleton(new ScriptItemReplacementRef(token, oldIdentMap.get(token.getText())));
        }
    }

    /**
     * Identifies uses of the array accessor ".get(int)" and replaces them with the bracketed accessor "[int]".
     */
    private static class ArrayAccessorReplacement implements RefactorRule {
        private static final String ACCESSOR = "get"; //$NON-NLS-1$

        /**
         * @see com.tibco.xpd.n2.resources.postimport.Bpm2CeProcessScriptMigration.RefactorRule#isMatch(com.tibco.xpd.script.parser.antlr.JScriptParser,
         *      int)
         */
        @Override
        public boolean isMatch(JScriptParser aParser, int aIndex) throws TokenStreamException {
            if (aIndex > 2) {
                Token fieldNameToken = aParser.LT(aIndex - 2);
                Token prevToken = aParser.LT(aIndex - 1);
                Token token = aParser.LT(aIndex);
                Token nextToken = aParser.LT(aIndex + 1);

                // if the elements are not on the same line - cannot handle line breaks
                if ((fieldNameToken == null) || (prevToken == null) || (token == null) || (nextToken == null) //
                        || (prevToken.getLine() != token.getLine()) || (token.getLine() != nextToken.getLine())) {
                    return false;
                }

                // compares the current token for one that matches the pattern ".get("
                return (fieldNameToken.getType() == JScriptTokenTypes.IDENT)
                        && (prevToken.getType() == JScriptTokenTypes.DOT) //
                        && (token.getType() == JScriptTokenTypes.IDENT)
                        && (nextToken.getType() == JScriptTokenTypes.LPAREN)
                        && (ArrayAccessorReplacement.ACCESSOR.equals(token.getText()));
            }
            return false;
        }

        /**
         * @see com.tibco.xpd.n2.resources.postimport.Bpm2CeProcessScriptMigration.RefactorRule#getReplacements(com.tibco.xpd.script.parser.antlr.JScriptParser,
         *      int)
         */
        @Override
        public Collection<ScriptItemReplacementRef> getReplacements(JScriptParser aParser, int aIndex)
                throws TokenStreamException {
            Collection<ScriptItemReplacementRef> result = new ArrayList<>();

            // create replacement for accessor method (with leading dot and following parenthesis) with bracket
            Token prevToken = aParser.LT(aIndex - 1);
            Token token = aParser.LT(aIndex);
            Token nextToken = aParser.LT(aIndex + 1);

            // we will be replacing all these tokens
            int totalLength = prevToken.getText().length() + token.getText().length() + nextToken.getText().length();

            result.add(new ScriptItemReplacementRef(prevToken.getLine(), prevToken.getColumn(), totalLength, "[")); //$NON-NLS-1$

            // find and replace the matching closing parenthesis with a bracket
            int openCount = 0;
            while (true) {
                nextToken = aParser.LT(++aIndex);
                int nextType = nextToken.getType();

                // we should always hit one - immediately following the accessor
                if (nextType == JScriptTokenTypes.LPAREN) {
                    openCount++;
                }

                else if (nextType == JScriptTokenTypes.RPAREN) {
                    // are we back to the initial opening bracket
                    if (--openCount == 0) {
                        result.add(new ScriptItemReplacementRef(nextToken, "]")); //$NON-NLS-1$
                        break;
                    }
                }
            }

            return result;
        }
    }

    /**
     * Refactors method names for a given collection of fields.
     */
    private static class MethodRefactorRule implements RefactorRule {
        private final String oldMethod;

        private final String newMethod;

        public MethodRefactorRule(String aOldMethod, String aNewMethod) {
            oldMethod = aOldMethod;
            newMethod = aNewMethod;
        }

        /**
         * @see com.tibco.xpd.n2.resources.postimport.Bpm2CeProcessScriptMigration.RefactorRule#isMatch(com.tibco.xpd.script.parser.antlr.JScriptParser,
         *      int)
         */
        @Override
        public boolean isMatch(JScriptParser aParser, int aIndex) throws TokenStreamException {
            if (aIndex > 2) {
                // ensure that the token is for the old method name and is preceeded by the field name and a dot
                Token fieldNameToken = aParser.LT(aIndex - 2);
                Token prevToken = aParser.LT(aIndex - 1);
                Token token = aParser.LT(aIndex);
                Token nextToken = aParser.LT(aIndex + 1);

                if ((fieldNameToken == null) || (fieldNameToken.getType() != JScriptTokenTypes.IDENT)
                        || (prevToken == null) || (prevToken.getType() != JScriptTokenTypes.DOT) //
                        || (token == null) || (token.getType() != JScriptTokenTypes.IDENT) //
                        || (nextToken == null) || (nextToken.getType() != JScriptTokenTypes.LPAREN)) {
                    return false;
                }

                return oldMethod.equals(token.getText());
            }

            return false;
        }

        /**
         * @see com.tibco.xpd.n2.resources.postimport.Bpm2CeProcessScriptMigration.RefactorRule#getReplacements(com.tibco.xpd.script.parser.antlr.JScriptParser,
         *      int)
         */
        @Override
        public Collection<ScriptItemReplacementRef> getReplacements(JScriptParser aParser, int aIndex)
                throws TokenStreamException {
            Token token = aParser.LT(aIndex);
            return Collections.singleton(new ScriptItemReplacementRef(token, newMethod));
        }
    }

    /**
     * Data class to store individual field/param reference (and be able to sort into line in ascending order, then
     * column IN REVERSE ORDER!
     * <p>
     * This helps when doing replacements because we can always guarantee that we don't shuffle text we're interested in
     * away from it's original offset.
     * </p>
     * 
     * @author aallway - shamelessly ripped out of ScriptParserUtil as the use case here will grow to be more than just
     *         simple name reference replacement.
     */
    private static class ScriptItemReplacementRef implements Comparable<ScriptItemReplacementRef> {
        private final int line;

        private final int col;

        private final int len;

        private final String newValue;

        /**
         * Creates a replacement reference with the properties of the given token.
         * 
         * @param aToken
         *            the token that identifies the position and text to be replaced.
         * @param aNewValue
         *            the replacement text.
         */
        public ScriptItemReplacementRef(Token aToken, String aNewValue) {
            this.line = aToken.getLine();
            this.col = aToken.getColumn();
            this.len = aToken.getText().length();
            this.newValue = aNewValue;
        }

        /**
         * Creates a replacement reference with the given properties.
         * 
         * @param aLine
         *            the line on which the replacement is to begin.
         * @param aCol
         *            the column on which the replacement is to begin (this is a 1 based index).
         * @param aLength
         *            the length of text to be replaced.
         * @param aNewValue
         *            the replacement text.
         */
        public ScriptItemReplacementRef(int aLine, int aCol, int aLength, String aNewValue) {
            this.line = aLine;
            this.col = aCol;
            this.len = aLength;
            this.newValue = aNewValue;
        }

        // Sort by line then column.
        @Override
        public int compareTo(ScriptItemReplacementRef o) {
            // Sort by ascending line number
            int ret = line - o.line;
            if (ret == 0) {
                // then descending (reverse) line number.
                ret = o.col - col;
            }
            return ret;
        }

        public int getLine() {
            return line;
        }

        /**
         * Replace the text for this script item with the given replacement text in the given StringBuilder containing
         * the original script.
         * 
         * NOTE that the caller is expected to call this function <b>IN REVERSE ORDER</b> for each line (as this object
         * only stores the location of the reference in the original script).
         * 
         * @param stringBuilder
         */
        public void replaceRef(StringBuilder stringBuilder) {
            int startIdx = col - 1;
            stringBuilder.replace(startIdx, startIdx + len, newValue);
        }
    }
}
