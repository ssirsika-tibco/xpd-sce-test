/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.n2.resources.script;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.n2.resources.util.N2Utils;
import com.tibco.xpd.processeditor.xpdl2.extensions.IScriptGrammarConverter;
import com.tibco.xpd.studioiprocess.scriptconverter.AbstractIProcessToJavaScriptConverter;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * The AMX BPM Script grammar converter contribution for converting
 * iProcessScript grammar scripts to JavaScript for AMX BPM grammar scripts.
 * <p>
 * Currently there are no specialisations for conversion from iprocesScript to
 * javaScript for AMX BPM BUT there could be by customising the
 * {@link AbstractIProcessToJavaScriptConverter} used here.
 * <p>
 * This converter is contributed in such a way that it is ONLY enabled if the
 * script is in a Process with the AMX BPM destination set and that in this case
 * it will take priority over any default converter contribution from iProcess
 * Add-in.
 * 
 * @author aallway
 * @since 26 May 2011
 */
public class IProcessScriptToBPMJavaScriptContribution implements
        IScriptGrammarConverter {

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.IScriptGrammarConverter#convertScriptForGrammar(java.lang.String,
     *      java.lang.String, java.lang.String, org.eclipse.emf.ecore.EObject)
     * 
     * @param sourceGrammar
     * @param sourceScript
     * @param targetGrammar
     * @param expression
     * @return
     */
    @Override
    public String convertScriptForGrammar(String sourceGrammar,
            String sourceScript, String targetGrammar, Expression expression,
            String context) throws GrammarConversionCancelledException {
        IProcessScriptToBPMJavaScriptConverter converter =
                new IProcessScriptToBPMJavaScriptConverter(sourceScript,
                        expression);

        return converter.getJavaScriptApproximation();
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.IScriptGrammarConverter#isEnabled(org.eclipse.emf.ecore.EObject)
     * 
     * @param contextObject
     * @return
     */
    @Override
    public boolean isEnabled(EObject contextObject) {
        Process process = Xpdl2ModelUtil.getProcess(contextObject);
        if (process != null) {
            if (DestinationUtil.getEnabledGlobalDestinationTypes(process)
                    .contains(N2Utils.N2_GLOBAL_DESTINATION_ID)) {
                return true;
            }
        }
        return false;
    }

}
