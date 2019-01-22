/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.om.transform.de.script;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.text.IDocument;

import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.om.transform.de.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.script.BaseProcessScriptSection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.rql.parser.util.RQLParserUtil;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.ui.ScriptGrammarContributionsUtil;
import com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Script information provider for Query Participant (context). The script
 * editor retrieves information regarding the Query Participant that it requires
 * from this class.
 * 
 * @author rsomayaj
 * 
 */
public class QueryParticipantScriptDetailsProvider extends
        AbstractScriptInfoProvider {

    /**
     * @see com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider#executeSaveCommand(org.eclipse.jface.text.IDocument)
     * 
     * @param document
     */
    @Override
    public void executeSaveCommand(IDocument document) {

        String modifiedScript = document.get();
        EObject eObject = getInput();
        if (eObject != null) {
            EditingDomain editingDomain =
                    WorkingCopyUtil.getEditingDomain(eObject);
            if (editingDomain != null) {
                Command setTaskScriptCommand =
                        getSetScriptCommand(editingDomain,
                                eObject,
                                modifiedScript,
                                getScriptGrammar());
                if (setTaskScriptCommand != null
                        && setTaskScriptCommand.canExecute()) {
                    editingDomain.getCommandStack()
                            .execute(setTaskScriptCommand);
                }
            }
        }

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
    public Command getSetScriptCommand(EditingDomain editingDomain,
            EObject input, String script, String grammar) {
        return ProcessScriptUtil
                .getSetParticipantQueryScriptCommand(editingDomain,
                        script,
                        grammar,
                        input,
                        Messages.RQLScriptEditorSection_SetParticipantQuery);
    }

    /**
     * @see com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider#getSetScriptGrammarCommand(org.eclipse.emf.edit.domain.EditingDomain,
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
        return ProcessScriptUtil
                .getSetQueryParticipantScriptGrammarCommand(editingDomain,
                        scriptGrammar,
                        scriptContainer);
    }

    /**
     * @see com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider#getScript(org.eclipse.emf.ecore.EObject)
     * 
     * @param input
     * @return
     */
    @Override
    public String getScript(EObject input) {
        String scriptContext = getScriptContext();
        if (scriptContext
                .equals(ProcessScriptContextConstants.QUERY_PARTICIPANT)) {
            return ProcessScriptUtil.getQueryParticipantScript(getInput());
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider#getRelevantEObject(org.eclipse.emf.ecore.EObject)
     * 
     * @param input
     * @return
     */
    @Override
    public EObject getRelevantEObject(EObject input) {
        if (input instanceof Participant
                || input instanceof ProcessRelevantData) {
            return input;
        }
        return super.getRelevantEObject(input);
    }

    /**
     * @see com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider#getDecriptionLabel()
     * 
     * @return
     */
    @Override
    public String getDecriptionLabel() {
        return Messages.QueryParticipantScriptDetailsProvider__scriptInfoProvider_desc0;
    }

    /**
     * @see com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider#getScriptRelevantData()
     * 
     * @return
     */
    @Override
    public List<IScriptRelevantData> getScriptRelevantData() {
        if (getCachedSrdList() == null) {
            setCachedSrdList(Collections.EMPTY_LIST);
            try {
                setCachedSrdList(ScriptGrammarContributionsUtil.INSTANCE
                        .getScriptRelevantData(getProcessDestinations(getInput()),
                                getScriptContext(),
                                RQLParserUtil.RQL_GRAMMAR,
                                getInput(),
                                RQLParserUtil.N2UT_DESTINATION));
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
        List<IScriptRelevantData> srdList = Collections.EMPTY_LIST;
        try {
            srdList =
                    ScriptGrammarContributionsUtil.INSTANCE
                            .getComplexScriptRelevantData(getProcessDestinations(getInput()),
                                    getScriptContext(),
                                    RQLParserUtil.RQL_GRAMMAR,
                                    getInput(),
                                    RQLParserUtil.N2UT_DESTINATION);
        } catch (CoreException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
        return srdList;
    }

    /**
     * @see com.tibco.xpd.script.ui.internal.BaseScriptSection#getEnabledDestinations(org.eclipse.emf.ecore.EObject)
     * 
     * @param input
     * @return
     */
    public List<String> getProcessDestinations(EObject input) {
        List<String> enabledDestinations = new ArrayList<String>();
        com.tibco.xpd.xpdl2.Process process = Xpdl2ModelUtil.getProcess(input);
        if (process == null) {
            /**
             * if a data field is created at package level then it would not
             * have a process associated with it which results in process=null
             * and hence the data field script drop down will not have any
             * grammar added to it . hence we check if the parent of the data
             * field is package and add grammar to the data field script drop
             * down
             */
            EObject object = getInput();
            EObject parent = null;
            if (object instanceof ProcessRelevantData
                    && object instanceof Participant) {
                parent = object.eContainer();
            }
        } else {
            enabledDestinations.addAll(DestinationUtil
                    .getEnabledValidationDestinations(process));

            // BPMN1 destination is not added to the list of destinations. By
            // default, this should be added.
            if (!enabledDestinations
                    .contains(BaseProcessScriptSection.BPMN1_VALIDATION_DESTINATION)) {
                enabledDestinations
                        .add(BaseProcessScriptSection.BPMN1_VALIDATION_DESTINATION);
            }
        }
        return enabledDestinations;

    }

}
