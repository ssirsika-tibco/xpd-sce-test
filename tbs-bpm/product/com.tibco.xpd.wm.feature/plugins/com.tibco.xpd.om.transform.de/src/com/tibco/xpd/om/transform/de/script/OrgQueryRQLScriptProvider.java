/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.om.transform.de.script;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.text.IDocument;

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgQuery;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.ui.ScriptGrammarContributionsUtil;
import com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider;

/**
 * @author rsomayaj
 * 
 */
public class OrgQueryRQLScriptProvider extends AbstractScriptInfoProvider {

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
                Command setScriptCmd =
                        getSetScriptCommand(editingDomain,
                                eObject,
                                modifiedScript,
                                getScriptGrammar());
                if (setScriptCmd != null && setScriptCmd.canExecute()) {
                    editingDomain.getCommandStack().execute(setScriptCmd);
                }
            }
        }

    }

    /**
     * 
     */
    private static final String RQL_GRAMMAR = "RQL"; //$NON-NLS-1$

    private static String N2UT_DESTINATION =
            "com.tibco.n2.ut.resources.destination.v1.0.0";//$NON-NLS-1$

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
        return SetCommand.create(ed, input, OMPackage.eINSTANCE
                .getOrgQuery_Query(), script);
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
        return SetCommand.create(editingDomain,
                scriptContainer,
                OMPackage.eINSTANCE.getOrgQuery_Grammar(),
                scriptGrammar);
    }

    /**
     * @see com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider#getScript(org.eclipse.emf.ecore.EObject)
     * 
     * @param input
     * @return
     */
    @Override
    public String getScript(EObject input) {
        if (input instanceof OrgQuery) {
            return ((OrgQuery) input).getQuery();
        }
        return null;
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
                        .getScriptRelevantData(getDestinations(getInput()),
                                getScriptContext(),
                                RQL_GRAMMAR,
                                getInput(),
                                N2UT_DESTINATION));
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
                            .getComplexScriptRelevantData(getDestinations(getInput()),
                                    getScriptContext(),
                                    RQL_GRAMMAR,
                                    getInput(),
                                    N2UT_DESTINATION);
        } catch (CoreException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
        return srdList;
    }

    /**
     * @param input
     * @return
     */
    private List<String> getDestinations(EObject input) {
        IProject prj = WorkingCopyUtil.getProjectFor(input);
        if (prj != null) {

            String[] selectedValidationDestinations =
                    com.tibco.xpd.destinations.GlobalDestinationUtil
                            .getSelectedValidationDestinations(prj);
            return Arrays.asList(selectedValidationDestinations);
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * @see com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider#getDecriptionLabel()
     * 
     * @return
     */
    @Override
    public String getDecriptionLabel() {
        return Messages.OrgQueryRQLScriptProvider__scriptInfoProvider_desc;
    }
}
