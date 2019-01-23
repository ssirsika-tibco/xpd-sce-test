/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2.properties;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.text.IDocument;

import com.tibco.xpd.implementer.resources.xpdl2.internal.Messages;
import com.tibco.xpd.implementer.script.Xpdl2WsdlUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.AbstractProcessScriptProvider;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;

/**
 * @author nwilson
 */
public abstract class AbstractWebServiceScriptProvider extends
        AbstractProcessScriptProvider {

    /**
     * 
     */
    public AbstractWebServiceScriptProvider(String grammar) {
        setScriptGrammar(grammar);
    }

    /**
     * @return
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptProvider#
     *      getDecriptionLabel()
     */
    @Override
    public String getDecriptionLabel() {
        return Messages.WebServiceScriptProvider_MappingLabel;
    }

    /**
     * @param input
     * @return
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptProvider#
     *      getScript(org.eclipse.emf.ecore.EObject)
     */
    @Override
    public String getScript(EObject input) {
        String script = ""; //$NON-NLS-1$
        if (input instanceof ScriptInformation) {
            ScriptInformation information = (ScriptInformation) input;
            script = Xpdl2WsdlUtil.getWebServiceTaskScript(information);
        }
        return script;
    }

    /**
     * @param ed
     * @param input
     * @param script
     * @param grammar
     * @return
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptProvider#
     *      getSetScriptCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, java.lang.String, java.lang.String)
     */
    @Override
    public Command getSetScriptCommand(EditingDomain ed, EObject input,
            String script, String grammar) {
        Command cmd = null;
        if (input instanceof ScriptInformation) {
            ScriptInformation information = (ScriptInformation) input;
            String oldScript = getScript(information);
            if (script != null && !script.equals(oldScript)) {
                cmd =
                        Xpdl2WsdlUtil.getSetWebServiceTaskScriptCommand(ed,
                                script,
                                information,
                                grammar);
            }
        }
        return cmd;
    }

    /**
     * @see com.tibco.xpd.script.ui.internal.IScriptDetailsProvider#getSetScriptGrammarCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, java.lang.String)
     * 
     * @param editingDomain
     * @param scriptContainer
     * @param scriptGrammar
     * @return
     */
    @Override
    public Command getSetScriptGrammarCommand(EditingDomain editingDomain,
            EObject scriptContainer, String scriptGrammar) {
        // TODO Ravi - Need to add logic from erstwhile
        // AbstractScriptEditorSection

        return null;
    }

    /**
     * @see com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider#executeSaveCommand(org.eclipse.jface.text.IDocument)
     * 
     * @param document
     */
    @Override
    public void executeSaveCommand(IDocument document) {
        if (getInput() instanceof ScriptInformation) {

            ScriptInformation information = (ScriptInformation) getInput();
            String script = document.get();
            String oldScript = getScript(information);
            if (script != null && !script.equals(oldScript)) {
                if (information.eContainer() == null) {
                    Activity activity = information.getActivity();
                    if (activity != null) {
                        EditingDomain ed =
                                WorkingCopyUtil.getEditingDomain(activity);
                        Command createTaskScriptCommand =
                                Xpdl2WsdlUtil
                                        .getSetWebServiceTaskScriptCommand(ed,
                                                script,
                                                information,
                                                getGrammar());
                        if (createTaskScriptCommand != null
                                && createTaskScriptCommand.canExecute()) {
                            ed.getCommandStack()
                                    .execute(createTaskScriptCommand);
                        }
                    }
                } else {
                    EditingDomain ed =
                            WorkingCopyUtil.getEditingDomain(information);
                    Command setTaskScriptCommand =
                            Xpdl2WsdlUtil.getSetWebServiceTaskScriptCommand(ed,
                                    script,
                                    information,
                                    getGrammar());
                    if (setTaskScriptCommand != null
                            && setTaskScriptCommand.canExecute()) {
                        ed.getCommandStack().execute(setTaskScriptCommand);
                    }
                }
            }
        }
    }

    protected abstract String getGrammar();

    /**
     * @see com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider#isNewScriptInformation(org.eclipse.emf.ecore.EObject)
     * 
     * @param object
     * @return
     */
    @Override
    public Boolean isNewScriptInformation(EObject object) {
        boolean isNewSI = false;
        if (object instanceof ScriptInformation) {
            ScriptInformation scriptInformation = (ScriptInformation) object;
            if (scriptInformation.eContainer() == null) {
                isNewSI = true;
            }
        }
        return isNewSI;
    }

    @Override
    protected abstract String getDefaultDestination();
}
