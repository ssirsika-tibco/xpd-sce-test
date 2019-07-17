/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.resources.postimport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
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
 *
 *
 * @author aallway
 * @since 11 Jul 2019
 */
public class Bpm2CeProcessScriptMigration implements IMigrationCommandInjector {

    /**
     * Store the original FormarVersion of the XPDL we are migrating. We ONLY
     * want to migrate scripts that have not already been migrated to ACE, the
     * initial FormatVersion for ACE is 1000 so we will migrate only if
     * FormatVersion is before the initial ACE one.
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
             * Store the original FormarVersion of the XPDL we are migrating. We
             * ONLY want to migrate scripts that have not already been migrated
             * to ACE, the initial FormatVersion for ACE is 1000 so we will NOT
             * migrate if the XPDL FormatVersion is on or after the initial ACE
             * one.
             */
            return null;
        }

        long startTime = System.nanoTime();
        int numScripts = 0;

        CompoundCommand cmd = new CompoundCommand("Migrate JavaScript"); //$NON-NLS-1$

        for (Iterator iterator = pkg.eAllContents(); iterator.hasNext();) {
            EObject eo = (EObject) iterator.next();

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
         * Ignore <xpdl2:DataMapping>/<xpdl2:Actual> as this is an
         * xpdl2:Expression type BUT is only the source/target mapper tree
         * content path (and should not be mangled and migrated here).
         * 
         * Note that actual script mappings are always defined in
         * xpdExt:ScriptInformation elements now in DataMappr mapping scenarios
         * (which is all that is supported in ACE).
         */
        if (Xpdl2Package.eINSTANCE.getDataMapping_Actual().equals(expression.eContainingFeature())) {
            return true;
        }

        return false;
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

                List<ProcessRelevantData> inScopeData = getInScopeData(expression);

                if (inScopeData != null) {
                    /*
                     * Build a map of OldProcessFieldName to
                     * bpm.OldProcessFieldName.
                     */
                    Map<String, String> nameMap = new HashMap<String, String>();

                    for (ProcessRelevantData data : inScopeData) {
                        String fieldName = data.getName();
                        nameMap.put(fieldName,
                                ReservedWords.PROCESS_DATA_WRAPPER_OBJECT_NAME + ConceptPath.CONCEPTPATH_SEPARATOR
                                        + fieldName);
                    }

                    /*
                     * Swap the references to this field name in the script.
                     */
                    newScript = parseAndConvertScript(scriptText, nameMap);

                }
            }
        }

        return newScript;
    }

    /**
     * Get all the data in scope of the activity (if it's a script under an
     * activity) else the process
     * 
     * @param expression
     * @return The data in scope of the given expression.
     */
    private List<ProcessRelevantData> getInScopeData(Expression expression) {
        /*  */

        List<ProcessRelevantData> data = Collections.emptyList();

        /*
         * If the expression is in a conditional flow, then we need to get the
         * data available in it's container.
         */
        Transition seqFlowParent = (Transition) Xpdl2ModelUtil.getAncestor(expression, Transition.class);

        if (seqFlowParent != null) {
            data = ProcessInterfaceUtil.getAllAvailableRelevantDataForSequenceFlow(seqFlowParent);

        } else {
            /*
             * If the expression is in an activity get all the data available to
             * it.
             */
            Activity activity = Xpdl2ModelUtil.getParentActivity(expression);

            if (activity != null) {
                data = ProcessInterfaceUtil.getAllAvailableRelevantDataForActivity(activity);

            } else {
                /* Fall back on all data in process. */
                Process process = Xpdl2ModelUtil.getProcess(expression);

                if (process != null) {
                    data = ProcessInterfaceUtil.getAllProcessRelevantData(process);
                }
            }
        }
        return data;
    }

    /**
     * Perform the detailed conversion of the script
     * 
     * shamelessly ripped out of
     * {@link ScriptParserUtil#replaceDataRefByName(String, Map)} and modified
     * as the use case here will grow to be more than just simple name reference
     * replacement.
     * 
     * @param strScript
     *            the script
     * @param oldTopLevelIdent2NewNameMap
     *            Top level identifier (process fields, static classes etc) map
     *            to new names/path.
     * 
     * @return new script or null if error in token stream.
     * 
     */
    private String parseAndConvertScript(String strScript, Map<String, String> oldTopLevelIdent2NewNameMap) {
        if (strScript == null || strScript.trim().length() == 0) {
            return strScript;
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

        //
        // First of all, search for all occurrences of any field in the list to
        // replace - keeping track of the line and column number.
        List<ScriptItemReplacementRef> references = new ArrayList<ScriptItemReplacementRef>();

        Token token = null;
        Token prevToken = null;
        int tokenIdx = 1;
        while (true) {
            try {
                //
                // Get next token from sript
                token = scriptParser.LT(tokenIdx++);
                if (token == null || token.getType() == Token.EOF_TYPE) {
                    // End of script. That's it all done.
                    break;
                }

                if (token != null && token.getType() == JScriptTokenTypes.IDENT) {
                    ScriptItemReplacementRef changedDataRef =
                            getTopLevelIdentifierReplacement(token, prevToken, oldTopLevelIdent2NewNameMap);

                    if (changedDataRef != null) {
                        references.add(changedDataRef);
                    }
                }

                prevToken = token;

            } catch (TokenStreamException e) {
                // ON exception return original - can't do it too man y problems
                // for lexer to cope with.
                return strScript;
            }

        }

        //
        // Recreate script (only if there were any references)...
        if (references.size() == 0) {
            return strScript;
        }

        // Attempt to preserve original line termination
        String lineTermination;
        if (strScript.contains("\r\n")) { //$NON-NLS-1$
            lineTermination = "\r\n"; //$NON-NLS-1$
        } else if (strScript.contains("\r")) { //$NON-NLS-1$
            lineTermination = "\r"; //$NON-NLS-1$
        } else {
            lineTermination = "\n"; //$NON-NLS-1$
        }

        // Go thru line by line.
        BufferedReader inputScript = new BufferedReader(new StringReader(strScript));

        // Sort references by ascending line number then descending column
        // number.
        Collections.sort(references);

        // The output script.
        StringBuilder outScript = new StringBuilder();

        // Track the input line number.
        int inputLineNum = 1;

        // The current reference idx
        int refIdx = 0;
        int numRefs = references.size();

        ScriptItemReplacementRef ref = references.get(refIdx);

        while (refIdx < numRefs) {
            // Wind on thru input script till we get to the given line.
            int currRefLine = ref.line;
            while (inputLineNum < currRefLine) {
                String line = nextScriptLine(inputScript);
                inputLineNum++;

                outScript.append(line);
                outScript.append(lineTermination);
            }

            // The input has caught up with the next line to replace reference
            // on.
            // Get the line to replace refs on and go thru refs making
            // replacements.
            String line = nextScriptLine(inputScript);
            inputLineNum++;

            StringBuilder lineBuff = new StringBuilder(line);

            //
            // Don't worry! Refs are from last on line to first on line order,
            // so simple iteration is ok.
            while (ref.line == currRefLine) {
                int startIdx = ref.col - 1;
                ref.replaceRef(lineBuff);

                // OK, that's this reference replaced, get next.
                refIdx++;
                if (refIdx >= numRefs) {
                    break;
                }

                ref = references.get(refIdx);
            }

            // That's all the refs finished in this line. output and continue.
            outScript.append(lineBuff);
            outScript.append(lineTermination);
        }

        // Output remainder of script.
        String line = nextScriptLine(inputScript);
        while (line != null) {
            outScript.append(line);
            outScript.append(lineTermination);
            line = nextScriptLine(inputScript);
        }

        return outScript.toString();
    }

    /**
     * Check if this is a top level identifer and if so, check if it is one that
     * is changing name/path and return a dataref object to allow the name to be
     * extracted and replaced.
     * 
     * @param token
     * @param prevToken
     * @param oldTopLevelIdent2NewNameMap
     * 
     * @return data reference for identifier name replacement OR null if no
     *         replacement necessary
     */
    private ScriptItemReplacementRef getTopLevelIdentifierReplacement(Token token, Token prevToken,
            Map<String, String> oldTopLevelIdent2NewNameMap) {
        ScriptItemReplacementRef changedDataRef = null;

        // IDENT is either a symbol (data field / formal param) or a
        // class property/method (such as DateTime.Date)
        //
        // We only want to change data fields / params so we will
        // ensure that previous token is not ".".
        // This is very crude, but at this point we do not have the
        // ability to distinguish between them.
        if (prevToken == null || prevToken.getType() != JScriptTokenTypes.DOT) {

            // Check if it's in rename map and if so, store the
            // reference info.
            String newName = oldTopLevelIdent2NewNameMap.get(token.getText());
            if (newName != null) {
                changedDataRef =
                        new ScriptItemReplacementRef(token.getLine(), token.getColumn(), token.getText(), newName);
            }
        }
        return changedDataRef;
    }


    /**
     * Get next line from buffer or null at end of stream.
     * 
     * @param inputScript
     * @return
     */
    private String nextScriptLine(BufferedReader inputScript) {
        String line = ""; //$NON-NLS-1$
        try {
            line = inputScript.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            line = null;
        }

        return line;
    }

    /**
     * Data class to store individual field/param reference (and be able to sort
     * into line in ascending order, then column IN REVERSE ORDER!
     * <p>
     * This helps when doing replacements because we can always guarantee that
     * we don't shuffle text we're interested in away from it's original offset.
     * </p>
     * 
     * @author aallway - shamelessly ripped out of ScriptParserUtil as the use
     *         case here will grow to be more thatn just simple name reference
     *         replacement.
     * 
     */
    private static class ScriptItemReplacementRef implements Comparable<ScriptItemReplacementRef> {
        int line = 0;

        int col = 0;

        String oldName;

        String newName;

        public ScriptItemReplacementRef(int line, int col, String oldName, String newName) {
            this.line = line;
            this.col = col;
            this.oldName = oldName;
            this.newName = newName;
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

        /**
         * Replace the text for this script item with the given replacement text
         * in the given StringBuilder containing the original script.
         * 
         * NOTE that the caller is expected to call this function <b>IN REVERSE
         * ORDER</b> for each line (as this object only stores the location of
         * the reference in the original script).
         * 
         * @param stringBuilder
         */
        public void replaceRef(StringBuilder stringBuilder) {
            int startIdx = col - 1;
            stringBuilder.replace(startIdx, startIdx + oldName.length(), newName);
        }

    }

}
