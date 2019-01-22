/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.rql.script.editor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IPluginContribution;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.rql.parser.util.RQLParserUtil;
import com.tibco.xpd.rql.script.RQLScriptPlugin;
import com.tibco.xpd.script.model.client.JsClassDefinitionReader;
import com.tibco.xpd.script.ui.ScriptGrammarContributionsUtil;
import com.tibco.xpd.script.ui.api.AbstractScriptEditorSection;

/**
 * The Editor section for RQL Script. The model relevant command and data are
 * contributed through extension points.
 * 
 * @author rsomayaj
 * 
 */
public class RQLScriptEditorSection extends AbstractScriptEditorSection
        implements IPluginContribution {

    private static final String SCRIPT_GRAMMAR = "RQL"; //$NON-NLS-1$

    /**
     * @param eClass
     */
    public RQLScriptEditorSection(EClass eClass) {
        super(eClass);

    }

    /**
     * @param eClass
     */
    public RQLScriptEditorSection() {
        this(null);
    }

    /**
     * @see com.tibco.xpd.script.ui.api.processeditor.xpdl2.properties.script.AbstractScriptEditorSection#getScriptGrammar()
     * 
     * @return
     */
    @Override
    protected String getScriptGrammar() {
        return SCRIPT_GRAMMAR;
    }

    /**
     * Contributing plug-in identifier.
     * 
     * @see org.eclipse.ui.IPluginContribution#getPluginId()
     */
    public String getPluginId() {
        return RQLScriptPlugin.PLUGIN_ID;
    }

    /**
     * @see com.tibco.xpd.script.ui.api.AbstractScriptEditorSection#getPreferenceStore()
     * 
     * @return
     */
    @Override
    protected IPreferenceStore getPreferenceStore() {
        return RQLScriptPlugin.getDefault().getPreferenceStore();
    }

    /**
     * @see com.tibco.xpd.script.ui.api.AbstractScriptEditorSection#getDefinitionReaders()
     * 
     * @return
     */
    @Override
    protected List<JsClassDefinitionReader> getDefinitionReaders() {
        List<JsClassDefinitionReader> jsClassProvider = Collections.EMPTY_LIST;
        try {
            jsClassProvider =
                    ScriptGrammarContributionsUtil.INSTANCE
                            .getJsClassDefinitionReader(new ArrayList<String>(
                                    getEnabledDestinations()),
                                    getScriptContext(),
                                    SCRIPT_GRAMMAR,
                                    RQLParserUtil.N2UT_DESTINATION);
        } catch (CoreException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
        return jsClassProvider;

    }

    /**
     * Contribution local identifier.
     * 
     * @see org.eclipse.ui.IPluginContribution#getLocalId()
     */
    public String getLocalId() {
        return "developer.rqleditor"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection#getPluginContributon()
     * 
     * @return
     */
    @Override
    public IPluginContribution getPluginContributon() {
        return this;
    }

}
