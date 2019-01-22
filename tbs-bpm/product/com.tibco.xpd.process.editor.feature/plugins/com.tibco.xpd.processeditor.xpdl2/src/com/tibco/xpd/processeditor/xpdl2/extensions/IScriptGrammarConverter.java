package com.tibco.xpd.processeditor.xpdl2.extensions;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.xpdl2.Expression;

/**
 * This interface is designed to be implemented by the callers of the various
 * getSetxxxxxScriptCommand() methods in trhe utility class.
 * 
 * 
 * @author aallway
 * @since 23 May 2011
 */
public interface IScriptGrammarConverter {

    /**
     * Opportunity to perform script translation during setting of script
     * grammar.
     * 
     * @param sourceGrammar
     *            Existing grammar
     * @param sourceScript
     *            Existing script text
     * @param targetGrammar
     *            Grammar script is changing to.
     * @param expression
     *            The expression that the script comes from.
     * @param context
     * 
     * @return new script text
     * @throws GrammarConversionCancelledException
     *             if user has cancelled.
     */
    public String convertScriptForGrammar(String sourceGrammar,
            String sourceScript, String targetGrammar, Expression expression,
            String context) throws GrammarConversionCancelledException;

    /**
     * @param contextObject
     *            Nominally be the object considered to be 'owner' of the script
     *            (Activity, Transition, Participant, DataField)
     * 
     * @return <code>true</code> If the grammar converter is enabled for the
     *         given context object.
     */
    public boolean isEnabled(EObject contextObject);

    /**
     * Exception denoting that the user chose to cancel the grammar conversion.
     * 
     * 
     * @author aallway
     * @since 22 May 2015
     */
    public class GrammarConversionCancelledException extends Exception {
    }

}