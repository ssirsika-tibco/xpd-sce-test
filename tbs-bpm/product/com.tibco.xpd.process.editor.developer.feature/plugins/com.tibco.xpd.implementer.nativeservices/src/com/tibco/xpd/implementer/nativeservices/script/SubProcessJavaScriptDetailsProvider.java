/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.implementer.nativeservices.script;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.implementer.script.Xpdl2WsdlUtil;
import com.tibco.xpd.process.js.model.script.ProcessJavaScriptInfoProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * @author rsomayaj
 * 
 */
public class SubProcessJavaScriptDetailsProvider extends
        ProcessJavaScriptInfoProvider {
    /**
     * @see com.tibco.xpd.process.js.model.script.ProcessJavaScriptInfoProvider#getScript(org.eclipse.emf.ecore.EObject)
     * 
     * @param input
     * @return
     */
    @Override
    public String getScript(EObject input) {
        String scriptContext = getScriptContext();
        if (ProcessScriptContextConstants.SUB_PROCESS_TASK
                .equals(scriptContext)) {
            if (input instanceof ScriptInformation) {
                return Xpdl2WsdlUtil
                        .getWebServiceTaskScript((ScriptInformation) getInput());
            } else {
                return ""; //$NON-NLS-1$
            }
        }
        return super.getScript(input);
    }

    /**
     * @see com.tibco.xpd.process.js.model.script.ProcessJavaScriptInfoProvider#getSetScriptCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, java.lang.String, java.lang.String)
     * 
     * @param editingDomain
     * @param object
     * @param strScript
     * @param grammar
     * @return
     */
    @Override
    public Command getSetScriptCommand(EditingDomain editingDomain,
            EObject eObject, String strScript, String grammar) {
        String scriptContext = getScriptContext();
        if (ProcessScriptContextConstants.SUB_PROCESS_TASK
                .equals(scriptContext)) {

            String oldScript = getScript(eObject);
            if (strScript != null && !strScript.equals(oldScript)) {
                if (eObject instanceof ScriptInformation) {
                    return Xpdl2WsdlUtil
                            .getSetWebServiceTaskScriptCommand(editingDomain,
                                    strScript,
                                    (ScriptInformation) eObject,
                                    getScriptGrammar());
                } else {
                    return Xpdl2WsdlUtil
                            .getCreateWebServiceTaskScriptCommand(editingDomain,
                                    strScript,
                                    (ProcessRelevantData) eObject,
                                    getScriptGrammar());
                }
            }
        }
        return super.getSetScriptCommand(editingDomain,
                eObject,
                strScript,
                grammar);
    }
}
