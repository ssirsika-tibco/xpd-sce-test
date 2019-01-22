/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.process.xpath.model.script;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.text.IDocument;

import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.implementer.script.Xpdl2WsdlUtil;
import com.tibco.xpd.process.xpath.model.ProcessXPathConsts;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.ui.ScriptGrammarContributionsUtil;
import com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Details provider for the XPath Script editor section
 * 
 * @author rsomayaj
 * 
 */
public class ProcessXPathScriptDetailsProvider extends
        AbstractScriptInfoProvider {

    /**
     * 
     */
    private static final String DEFAULT_XPATH_DESTINATION = "xPath1.x"; //$NON-NLS-1$

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
            if (information.eContainer() == null) {
                Activity activity = information.getActivity();
                if (activity != null) {
                    EditingDomain ed =
                            WorkingCopyUtil.getEditingDomain(activity);
                    Command createTaskScriptCommand =
                            Xpdl2WsdlUtil.getSetWebServiceTaskScriptCommand(ed,
                                    script,
                                    information,
                                    ProcessXPathConsts.XPATH_GRAMMAR);
                    if (createTaskScriptCommand != null
                            && createTaskScriptCommand.canExecute()) {
                        ed.getCommandStack().execute(createTaskScriptCommand);
                    }
                }
            } else {
                EditingDomain ed =
                        WorkingCopyUtil.getEditingDomain(information);
                Command setTaskScriptCommand =
                        Xpdl2WsdlUtil.getSetWebServiceTaskScriptCommand(ed,
                                script,
                                information,
                                ProcessXPathConsts.XPATH_GRAMMAR);
                if (setTaskScriptCommand != null
                        && setTaskScriptCommand.canExecute()) {
                    ed.getCommandStack().execute(setTaskScriptCommand);
                }
            }
        }
    }

    /**
     * @see com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider#getScriptRelevantData()
     * 
     * @return
     */
    @Override
    public List<IScriptRelevantData> getScriptRelevantData() {
        EObject input = getInput();
        Process process = Xpdl2ModelUtil.getProcess(input);
        Set<String> enabledValidationDestinations =
                DestinationUtil.getEnabledValidationDestinations(process);
        if (getCachedSrdList() == null) {
            setCachedSrdList(Collections.EMPTY_LIST);
            try {
                setCachedSrdList(ScriptGrammarContributionsUtil.INSTANCE
                        .getScriptRelevantData(new ArrayList<String>(
                                enabledValidationDestinations),
                                getScriptContext(),
                                getScriptGrammar(),
                                input,
                                DEFAULT_XPATH_DESTINATION));
            } catch (CoreException e) {
                XpdResourcesPlugin.getDefault().getLogger().error(e);
            }
        }
        return getCachedSrdList();
    }

    /**
     * @see com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider#getComplexScriptRelevantData()
     * 
     * @return
     */
    @Override
    public List getComplexScriptRelevantData() {
        EObject input = getInput();
        Process process = Xpdl2ModelUtil.getProcess(input);
        Set<String> enabledValidationDestinations =
                DestinationUtil.getEnabledValidationDestinations(process);
        List complexScriptRelevantData = Collections.EMPTY_LIST;
        try {
            complexScriptRelevantData =
                    ScriptGrammarContributionsUtil.INSTANCE
                            .getScriptRelevantData(new ArrayList<String>(
                                    enabledValidationDestinations),
                                    getScriptContext(),
                                    getScriptGrammar(),
                                    input,
                                    DEFAULT_XPATH_DESTINATION);
        } catch (CoreException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
        return complexScriptRelevantData;
    }

    /**
     * @see com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider#getSetScriptCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, java.lang.String, java.lang.String)
     * 
     * @param ed
     * @param input
     * @param script
     * @param grammar
     * @return
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
     * @see com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider#getScript(org.eclipse.emf.ecore.EObject)
     * 
     * @param input
     * @return
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

}
