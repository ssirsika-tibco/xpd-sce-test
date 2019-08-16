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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.migrate.IMigrationCommandInjector;
import com.tibco.xpd.n2.resources.BundleActivator;
import com.tibco.xpd.process.js.parser.util.ScriptParserUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.script.parser.antlr.JScriptLexer;
import com.tibco.xpd.script.parser.antlr.JScriptParser;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.resources.XpdlMigrate;
import com.tibco.xpd.xpdl2.util.ResetExpressionContentCommand;

import antlr.Token;
import antlr.TokenStreamException;

/**
 * XPDL Migration command injector for migrating AMX BPM script to ACE semantics
 *
 * @author aallway
 * @author pwatson
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

        // create a DataFieldResolver for the given process data references
        DataFieldResolver fieldResolver;
        try {
            fieldResolver = new DataFieldResolver(pkg);
        } catch (CoreException e) {
            e.printStackTrace();
            return null;
        }

        CompoundCommand cmd = new CompoundCommand("Migrate JavaScript"); //$NON-NLS-1$

        for (TreeIterator<EObject> iterator = pkg.eAllContents(); iterator.hasNext();) {
            EObject eo = iterator.next();

            if (eo instanceof Expression) {
                Expression expression = (Expression) eo;

                if (ScriptGrammarFactory.JAVASCRIPT.equals(expression.getScriptGrammar())) {
                    numScripts++;

                    if (!ignoreScript(expression)) {
                        // set the scope for the DataFieldResolver
                        fieldResolver.setExpression(expression);

                        // perform the refactor
                        String newScript = highLevelConvert(expression, fieldResolver);

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
    private String highLevelConvert(Expression expression, DataFieldResolver aFieldResolver) {
        if (expression == null) {
            return null;
        }

        String scriptText = expression.getText();
        if (scriptText == null || scriptText.isEmpty()) {
            return null;
        }

        Collection<ScriptRefactorRule> replacementRules = getRefactorRules(aFieldResolver);
        if (replacementRules.isEmpty()) {
            return null;
        }

        // find all replacements
        List<ScriptItemReplacementRef> replacements = parseScript(scriptText, replacementRules);

        // apply all replacements
        return convertScript(scriptText, replacements);
    }

    /**
     * Build the RefactorRules to be applied to the script. Uses the given Expression to derive the data items, so that
     * the rules may be data aware.
     * 
     * @throws CoreException
     */
    private Collection<ScriptRefactorRule> getRefactorRules(final DataFieldResolver aFieldResolver)
    {
        Collection<ScriptRefactorRule> result = new ArrayList<>();

        // for static classes to the old-name->new-name top level identifiers map.
        result.add(new BOMFactoryRefactor());
        result.add(new StaticRefRefactor("Process", "bpm.process")); //$NON-NLS-1$ //$NON-NLS-2$
        result.add(new StaticRefRefactor("WorkManagerFactory", "bpm.workManager")); //$NON-NLS-1$ //$NON-NLS-2$

        // a method filter to check that the method belongs to an array field
        MethodNameRefactor.MethodFilter arrayFilter = (parser, index) -> {
            // index references the method token
            // lookup field identified by current token - 2
            ConceptPath conceptPath = aFieldResolver.resolve(parser, index - 2);
            return (conceptPath != null) && (conceptPath.isArray());
        };

        // for all the in-scope data fields to the old-name->new-name top level identifiers map
        result.add(new TopLevelFieldIdRefactor(aFieldResolver));

        // add array method refactors
        result.add(new MethodNameRefactor("addAll", "pushAll")); //$NON-NLS-1$ //$NON-NLS-2$
        result.add(new MethodNameRefactor("add", "push", arrayFilter)); //$NON-NLS-1$//$NON-NLS-2$
        result.add(new ArrayAccessorRefactor(aFieldResolver));
        result.add(new ArraySizeRefactor(aFieldResolver));

        // date refactors
        result.add(new DateConstructorRefactor());
        Map<String, String> dateMethods = new HashMap<>();
        dateMethods.put("getYear", "getFullYear"); //$NON-NLS-1$ //$NON-NLS-2$
        dateMethods.put("setYear", "setFullYear"); //$NON-NLS-1$ //$NON-NLS-2$
        dateMethods.put("getDay", "getDate"); //$NON-NLS-1$ //$NON-NLS-2$
        dateMethods.put("setDay", "setDate"); //$NON-NLS-1$ //$NON-NLS-2$
        dateMethods.put("getHour", "getHours"); //$NON-NLS-1$ //$NON-NLS-2$
        dateMethods.put("setHour", "setHours"); //$NON-NLS-1$ //$NON-NLS-2$
        dateMethods.put("getMinute", "getMinutes"); //$NON-NLS-1$ //$NON-NLS-2$
        dateMethods.put("setMinute", "setMinutes"); //$NON-NLS-1$ //$NON-NLS-2$
        dateMethods.put("getSecond", "getSeconds"); //$NON-NLS-1$ //$NON-NLS-2$
        dateMethods.put("setSecond", "setSeconds"); //$NON-NLS-1$ //$NON-NLS-2$
        dateMethods.put("getMillisecond", "getMilliseconds"); //$NON-NLS-1$ //$NON-NLS-2$
        dateMethods.put("setMillisecond", "setMilliseconds"); //$NON-NLS-1$ //$NON-NLS-2$
        result.add(new MethodNameRefactor(dateMethods));

        // enumeration refactors
        result.add(new EnumRefactor(aFieldResolver));

        // case-access refactors
        result.add(new CaseAccessRefactor(aFieldResolver));

        // scriptUtil refactors
        Map<String, String> scriptUtilMethods = new HashMap<>();
        scriptUtilMethods.put("copy", "copy"); //$NON-NLS-1$ //$NON-NLS-2$
        scriptUtilMethods.put("copyAll", "copyAll"); //$NON-NLS-1$ //$NON-NLS-2$
        result.add(new ScriptUtilRefactor(scriptUtilMethods));

        return result;
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
    private List<ScriptItemReplacementRef> parseScript(String strScript,
            Collection<ScriptRefactorRule> aReplacementRules) {
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
                for (ScriptRefactorRule rule : aReplacementRules) {
                    if (rule.isMatch(token, scriptParser, tokenIdx)) {
                        // add the rule's replacements
                        references.addAll(rule.getReplacements(token, scriptParser, tokenIdx));
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
            int currRefLine = ref.getLine();
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
}
