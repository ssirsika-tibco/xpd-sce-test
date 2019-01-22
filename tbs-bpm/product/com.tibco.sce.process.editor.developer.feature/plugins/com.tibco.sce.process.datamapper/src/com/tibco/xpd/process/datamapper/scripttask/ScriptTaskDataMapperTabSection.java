/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.process.datamapper.scripttask;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IPluginContribution;

import com.tibco.xpd.datamapper.scripts.AbstractScriptDataMapperEditorProvider;
import com.tibco.xpd.datamapper.scripts.DataMapperUserDefinedMappingScriptsProvider;
import com.tibco.xpd.process.datamapper.ProcessDataMapperPlugin;
import com.tibco.xpd.process.datamapper.common.AbstractProcessDataMapperScriptProperties;
import com.tibco.xpd.process.datamapper.common.AbstractProcessDataMapperSection;
import com.tibco.xpd.process.datamapper.internal.Messages;
import com.tibco.xpd.process.datamapper.scriptdata.ScriptTaskScriptDataMapperProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.parts.TaskEditPart;
import com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider;
import com.tibco.xpd.script.ui.internal.BaseScriptSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Responsible for creating source/target info providers, command provider and
 * script section content provider for the process data mapper section in Script
 * Task Data Mapper tab.
 * 
 * @author Ali
 * @since 12 Jan 2015
 */
public class ScriptTaskDataMapperTabSection extends
        AbstractProcessDataMapperSection {

    private static String SCRIPT_CONTEXT =
            ProcessScriptContextConstants.SCRIPT_TASK;

    private ScriptTaskScriptDataMapperProvider dataMapperProvider;

    private boolean wantHeaderLabel = true;

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperSection#getDataMapperContext()
     * 
     * @return
     */
    @Override
    protected String getDataMapperContext() {
        return SCRIPT_CONTEXT;
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperSection#getScriptDataMapperProvider()
     * 
     * @return
     */
    @Override
    protected AbstractScriptDataMapperEditorProvider getScriptDataMapperProvider() {
        if (dataMapperProvider == null) {
            dataMapperProvider = new ScriptTaskScriptDataMapperProvider();
        }
        return dataMapperProvider;
    }

    /**
     * @see com.tibco.com.tibco.xpd.datamapper.api.AbstractDataMapperSection#getScriptSection()
     * 
     * @return
     */
    @Override
    protected BaseScriptSection getScriptSection() {

        return new AbstractProcessDataMapperScriptProperties() {
            @Override
            protected AbstractScriptInfoProvider getScriptSectionInfoProvider() {
                return new DataMapperUserDefinedMappingScriptsProvider(
                        getScriptDataMapperProvider());
            }

            @Override
            public String getScriptContext() {
                return ProcessScriptContextConstants.DATA_MAPPER_PE_MAPPING_SCRIPTS;
            }
        };
    }

    /**
     * @see com.tibco.xpd.process.datamapper.common.AbstractProcessDataMapperSection#getSetScriptGrammarCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      java.lang.String, org.eclipse.emf.ecore.EObject)
     * 
     * @param editingDomain
     * @param scriptGrammar
     * @param eObject
     * @return
     */
    @Override
    public Command getSetDataMapperGrammarCommand(EditingDomain editingDomain,
            String scriptGrammar, EObject eObject) {
        return ProcessScriptUtil.getSetScriptTaskGrammarCommand(editingDomain,
                scriptGrammar,
                eObject,
                false,
                getDataMapperContext());

    }

    /**
     * Sid XPD-7575 For Process data mapper as separate section add the header
     * label (it was removed fromt he base section for space reasons).
     * 
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperSection#createHeaderLabelControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param tk
     * @return
     */
    @Override
    protected Control createHeaderLabelControls(Composite parent,
            XpdFormToolkit tk) {
        if (wantHeaderLabel) {
            return tk.createLabel(parent,
                    Messages.ProcessDataMapperSection_title);
        }

        return super.createHeaderLabelControls(parent, tk);
    }

    /**
     * @param wantHeaderLabel
     *            the wantHeaderLabel to set
     */
    public void setWantHeaderLabel(boolean wantHeaderLabel) {
        this.wantHeaderLabel = wantHeaderLabel;
    }

    /**
     * 
     * Filter for selection of Data Mapper Tab in Script Task when the selected
     * grammar is 'Data Mapper'
     */
    public static class Filter implements IFilter, IPluginContribution {

        /**
         * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
         * 
         * @param toTest
         * @return
         */
        @Override
        public boolean select(Object toTest) {
            EObject eo = null;

            if (toTest instanceof TaskEditPart) {
                TaskEditPart tep = (TaskEditPart) toTest;
                eo = (EObject) tep.getModel();
            }

            if (eo != null) {
                if (eo instanceof Activity) {

                    String scriptGrammr =
                            TaskObjectUtil
                                    .getExistingSetScriptGrammarId((Activity) eo);
                    if (scriptGrammr != null
                            && ScriptGrammarFactory.DATAMAPPER
                                    .equals(scriptGrammr)) {
                        return true;
                    }
                }
            }
            return false;
        }

        /**
         * Contribution local identifier.
         * 
         * @see org.eclipse.ui.IPluginContribution#getLocalId()
         */
        @Override
        public String getLocalId() {
            return "DataMapperSection"; //$NON-NLS-1$
        }

        /**
         * Contributing plug-in identifier.
         * 
         * @see org.eclipse.ui.IPluginContribution#getPluginId()
         */
        @Override
        public String getPluginId() {
            return ProcessDataMapperPlugin.PLUGIN_ID;
        }
    }
}
