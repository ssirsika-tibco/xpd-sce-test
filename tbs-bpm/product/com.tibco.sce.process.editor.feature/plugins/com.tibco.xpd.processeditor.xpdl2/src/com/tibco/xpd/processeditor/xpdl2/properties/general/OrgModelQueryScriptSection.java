/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.general;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.processeditor.xpdl2.properties.script.BaseProcessScriptSection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.script.ui.api.AbstractScriptEditorSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.Participant;

/**
 * Property section that contains the script editor for a participant of type
 * Organisational Model Query Script
 * 
 * @author rsomayaj
 * 
 */
public class OrgModelQueryScriptSection extends BaseProcessScriptSection {

    private static final String PLAIN_TEXT_GRAMMAR_ID = "Text"; //$NON-NLS-1$

    private Composite orgModelComposite;

    /**
     * @param inputType
     */
    public OrgModelQueryScriptSection(EClass inputType) {
        super(inputType);
    }

    /**
     * @see com.com.tibco.xpd.script.ui.internal.BaseScriptSection#getCurrentSetScriptGrammarId()
     * 
     * @return
     */
    @Override
    public String getCurrentSetScriptGrammarId() {

        String currentScriptGrammarId = null;

        EObject objInput = getInput();
        if (objInput instanceof Participant) {
            currentScriptGrammarId =
                    TaskObjectUtil
                            .getExistingQueryParticipantScriptGrammarId((Participant) objInput);
        } else if (objInput instanceof DataField) {
            currentScriptGrammarId =
                    TaskObjectUtil
                            .getExistingQueryParticipantPerformerScriptGrammarId((DataField) objInput);
        }
        return currentScriptGrammarId;
    }

    /**
     * @see com.com.tibco.xpd.script.ui.internal.BaseScriptSection#getScriptContext()
     * 
     * @return
     */
    @Override
    public String getScriptContext() {
        return ProcessScriptContextConstants.QUERY_PARTICIPANT;
    }

    /**
     * @see com.com.tibco.xpd.script.ui.internal.BaseScriptSection#getSetScriptGrammarCommand(java.lang.String,
     *      com.tibco.xpd.script.ui.api.com.tibco.xpd.script.ui.internal.AbstractScriptEditorSection)
     * 
     * @param grammar
     * @param editorSection
     * @return
     */
    @Override
    protected Command getSetScriptGrammarCommand(String grammar,
            AbstractScriptEditorSection editorSection) {
        Command cmd = null;

        EditingDomain ed = getEditingDomain();
        EObject objInput = getInput();
        if (objInput instanceof Participant) {
            Participant participant = (Participant) objInput;
            if (participant != null && ed != null && editorSection != null) {
                cmd = editorSection.getSetScriptGrammarCommand(ed, participant);
            }
        } else if (objInput instanceof DataField) {
            DataField dataField = (DataField) objInput;
            if (dataField != null && ed != null && editorSection != null) {
                cmd = editorSection.getSetScriptGrammarCommand(ed, dataField);
            }
        }
        return cmd;
    }

    /**
     * overwriting this method for setting the layout so that the task script
     * text area script window gets the space
     * */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        orgModelComposite = toolkit.createComposite(parent);

        toolkit.adapt(orgModelComposite);
        orgModelComposite.setLayout(new FillLayout());

        orgModelComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL
                | GridData.FILL_VERTICAL));

        return super.doCreateControls(orgModelComposite, toolkit);
    }

    @Override
    protected void doRefreshTabs() {
        // do nothing
    }

    /**
     * @see com.tibco.xpd.script.ui.internal.BaseScriptSection#getAlternativeEditorSection(java.lang.String)
     * 
     * @param currentSetGrammarId
     * @return
     */
    @SuppressWarnings("restriction")
    @Override
    protected EditorSectionComposite getAlternativeEditorSection(
            String currentSetGrammarId) {
        EditorSectionComposite editorSectionCompositeForGrammarId =
                getEditorSectionCompositeForGrammarId(PLAIN_TEXT_GRAMMAR_ID);

        enableEditorSection(false, editorSectionCompositeForGrammarId);

        return editorSectionCompositeForGrammarId;
    }
}