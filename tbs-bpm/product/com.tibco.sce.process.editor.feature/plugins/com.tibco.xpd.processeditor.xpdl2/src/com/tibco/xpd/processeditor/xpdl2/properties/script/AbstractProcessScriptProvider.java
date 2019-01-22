/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.script;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.text.IDocument;

import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.ui.ScriptGrammarContributionsUtil;
import com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author nwilson
 */
public abstract class AbstractProcessScriptProvider extends
        AbstractScriptInfoProvider {

    /**
     * @param input
     * @return
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptProvider#getProcess(org.eclipse.emf.ecore.EObject)
     */
    public Process getProcess(EObject input) {
        Process process = null;
        if (input instanceof Process) {
            process = (Process) input;
        } else if (input != null) {
            process = Xpdl2ModelUtil.getProcess(input);
        }
        return process;
    }

    /**
     * @param input
     * @return
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptProvider#getRelevantEObject(org.eclipse.emf.ecore.EObject)
     */
    @Override
    public EObject getRelevantEObject(EObject input) {
        return input;
    }

    /**
     * @see com.tibco.xpd.script.ui.internal.IScriptDetailsProvider#isNewScriptInformation(org.eclipse.emf.ecore.EObject)
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

    /**
     * @see com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider#executeSaveCommand(org.eclipse.jface.text.IDocument)
     * 
     * @param document
     */
    @Override
    public void executeSaveCommand(IDocument document) {
        String modifiedScript = document.get();
        if (getInput() == null) {
            return;
        }
        EObject eObject = getInput();
        if (eObject != null) {
            EditingDomain editingDomain =
                    WorkingCopyUtil.getEditingDomain(eObject);
            if (editingDomain == null && eObject instanceof ScriptInformation) {
                Activity act = ((ScriptInformation) eObject).getActivity();
                if (act != null) {
                    editingDomain = WorkingCopyUtil.getEditingDomain(act);
                }
            }
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
            if (object instanceof ProcessRelevantData) {
                parent = object.eContainer();
            }
            if (null == parent)
                return Collections
                        .singletonList(BaseProcessScriptSection.BPMN1_VALIDATION_DESTINATION);
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
                                getScriptGrammar(),
                                getInput(),
                                getDefaultDestination()));
            } catch (CoreException e) {
                // TODO Ravi - LOG exception
            }
        }
        return getCachedSrdList();
    }

    /**
     * @return
     */
    protected abstract String getDefaultDestination();

    /**
     * @see com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider#getComplexScriptRelevantData()
     * 
     * @return
     */
    @Override
    public List getComplexScriptRelevantData() {
        try {
            return ScriptGrammarContributionsUtil.INSTANCE
                    .getComplexScriptRelevantData(getProcessDestinations(getInput()),
                            getScriptContext(),
                            getScriptGrammar(),
                            getInput(),
                            getDefaultDestination());
        } catch (CoreException e) {
            // TODO Ravi - LOG exception
        }
        return Collections.EMPTY_LIST;
    }

}
