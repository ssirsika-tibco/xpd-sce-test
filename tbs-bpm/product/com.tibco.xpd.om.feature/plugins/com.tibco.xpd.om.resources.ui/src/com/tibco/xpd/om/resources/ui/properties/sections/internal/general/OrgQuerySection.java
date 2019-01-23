/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.internal.general;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgQuery;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.script.ui.api.AbstractScriptEditorSection;
import com.tibco.xpd.script.ui.internal.BaseScriptSection;

/**
 * Properties general section for an {@link OrgQuery} object.
 * 
 * @author njpatel
 * 
 */
public class OrgQuerySection extends BaseScriptSection {

    private static String GENERIC_OM_VALIDATION_DEST =
            "com.tibco.xpd.om.validator.generic.destination"; //$NON-NLS-1$

    /**
     * 
     */
    public OrgQuerySection() {
        super(OMPackage.eINSTANCE.getOrgQuery());
    }

    /**
     * @param inputType
     */
    public OrgQuerySection(EClass inputType) {
        super(inputType);
    }

    private static final String ORG_QUERY_SCRIPT_CONTEXT = "OrgQuery"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.script.ui.internal.BaseScriptSection#getEnabledDestinations(org.eclipse.emf.ecore.EObject)
     * 
     * @param input
     * @return
     */
    @Override
    public Collection<String> getEnabledDestinations(EObject input) {
        IProject prj = WorkingCopyUtil.getProjectFor(input);
        if (prj != null) {
            String[] selectedValidationDestinations =
                    com.tibco.xpd.destinations.GlobalDestinationUtil
                            .getSelectedValidationDestinations(prj);
            List<String> destsFromPrj =
                    Arrays.asList(selectedValidationDestinations);
            List<String> dests = new ArrayList<String>(destsFromPrj);
            if (!dests.contains(GENERIC_OM_VALIDATION_DEST)) {
                dests.add(GENERIC_OM_VALIDATION_DEST);
            }

            return dests;
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * @see com.tibco.xpd.script.ui.internal.BaseScriptSection#getScriptContext()
     * 
     * @return
     */
    @Override
    public String getScriptContext() {
        return ORG_QUERY_SCRIPT_CONTEXT;
    }

    /**
     * @see com.tibco.xpd.script.ui.internal.BaseScriptSection#getSetScriptGrammarCommand(java.lang.String,
     *      com.tibco.xpd.script.ui.api.AbstractScriptEditorSection)
     * 
     * @param grammar
     * @param editorSection
     * @return
     */
    @Override
    protected Command getSetScriptGrammarCommand(String grammar,
            AbstractScriptEditorSection editorSection) {
        Command cmd = null;

        EditingDomain ed = XpdResourcesPlugin.getDefault().getEditingDomain();
        EObject inputObj = getInput();
        if (inputObj != null && ed != null && editorSection != null) {
            cmd = editorSection.getSetScriptGrammarCommand(ed, inputObj);
        }
        return cmd;
    }

    /**
     * @see com.tibco.xpd.script.ui.internal.BaseScriptSection#getCurrentSetScriptGrammarId()
     * 
     * @return
     */
    @Override
    public String getCurrentSetScriptGrammarId() {
        EObject inputObject = getInput();
        if (inputObject instanceof OrgQuery) {
            return ((OrgQuery) inputObject).getGrammar();
        }
        return null;
    }
}
