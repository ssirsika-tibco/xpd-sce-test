/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.process.datamapper.usertaskscripts;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.datamapper.scripts.AbstractScriptDataMapperEditorProvider;
import com.tibco.xpd.datamapper.scripts.DataMapperUserDefinedMappingScriptsProvider;
import com.tibco.xpd.process.datamapper.common.AbstractProcessDataMapperScriptProperties;
import com.tibco.xpd.process.datamapper.common.AbstractProcessDataMapperSection;
import com.tibco.xpd.process.datamapper.scriptdata.UserTaskScriptDataMapperProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider;
import com.tibco.xpd.script.ui.internal.BaseScriptSection;

/**
 * Responsible for creating source/target info providers, command provider and
 * script section content provider for the process data mapper section in the
 * User Task Open Script tab.
 * 
 * @author Ali
 * @since 30 Jan 2015
 */
public class OpenUserTaskDataMapperSection extends
        AbstractProcessDataMapperSection {

    private static String SCRIPT_CONTEXT =
            ProcessScriptContextConstants.OPEN_USER_TASK;

    private UserTaskScriptDataMapperProvider dataMapperProvider;

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
            dataMapperProvider =
                    new UserTaskScriptDataMapperProvider(SCRIPT_CONTEXT);
        }
        return dataMapperProvider;
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.AbstractEditableMappingSection#getScriptSection()
     * 
     * @return
     */
    @Override
    protected BaseScriptSection getScriptSection() {
        return new AbstractProcessDataMapperScriptProperties() {

            private AbstractScriptInfoProvider mapperScriptsInfoProvider;

            @Override
            protected AbstractScriptInfoProvider getScriptSectionInfoProvider() {
                if (mapperScriptsInfoProvider == null) {
                    mapperScriptsInfoProvider =
                            new DataMapperUserDefinedMappingScriptsProvider(
                                    getScriptDataMapperProvider());
                }
                return mapperScriptsInfoProvider;
            }

            @Override
            public String getScriptContext() {
                return ProcessScriptContextConstants.DATA_MAPPER_WM_MAPPING_SCRIPTS;
            }
        };
    }

    /**
     * 
     * @see com.tibco.xpd.process.datamapper.common.AbstractProcessDataMapperSection#getSetDataMapperGrammarCommand(org.eclipse.emf.edit.domain.EditingDomain,
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

        return ProcessScriptUtil
                .getSetOpenUserTaskScriptGrammarCommand(editingDomain,
                        scriptGrammar,
                        eObject,
                        false,
                        getDataMapperContext());
    }

}
