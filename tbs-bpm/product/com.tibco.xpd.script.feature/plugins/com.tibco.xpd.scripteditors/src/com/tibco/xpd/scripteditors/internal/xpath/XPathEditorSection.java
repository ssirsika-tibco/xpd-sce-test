/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.scripteditors.internal.xpath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IPluginContribution;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.script.model.client.JsClassDefinitionReader;
import com.tibco.xpd.script.ui.ScriptGrammarContributionsUtil;
import com.tibco.xpd.script.ui.api.AbstractScriptEditorSection;
import com.tibco.xpd.scripteditors.ScriptEditorsPlugin;

/**
 * XPath Script editor section, facilitates script editing in XPath.
 * 
 * @author rsomayaj
 * 
 */
public class XPathEditorSection extends AbstractScriptEditorSection implements
        IPluginContribution {

    public static final String XPATH_GRAMMAR = "XPath"; //$NON-NLS-1$

    public static final String XPATH_DESTINATION = "xPath1.x"; //$NON-NLS-1$

    public XPathEditorSection(EClass eClass) {
        super(eClass);
    }

    public XPathEditorSection() {
        super(null);
    }

    /**
     * @return
     * @see com.tibco.xpd.script.ui.api.processeditor.xpdl2.properties.script.AbstractScriptEditorSection#getScriptGrammar()
     */
    @Override
    protected String getScriptGrammar() {
        return XPATH_GRAMMAR;
    }

    /**
     * @see org.eclipse.ui.IPluginContribution#getPluginId()
     * 
     * @return
     */
    public String getPluginId() {
        return ScriptEditorsPlugin.PLUGIN_ID;
    }

    /**
     * @see com.tibco.xpd.script.ui.api.AbstractScriptEditorSection#getPreferenceStore()
     * 
     * @return
     */
    @Override
    protected IPreferenceStore getPreferenceStore() {
        return ScriptEditorsPlugin.getDefault().getPreferenceStore();
    }

    /**
     * @see com.tibco.xpd.script.ui.api.AbstractScriptEditorSection#getDefinitionReaders()
     * 
     * @return
     */
    @Override
    protected List<JsClassDefinitionReader> getDefinitionReaders() {
        List<JsClassDefinitionReader> xpathClassDefinitionReaders =
                Collections.EMPTY_LIST;
        try {
            xpathClassDefinitionReaders =
                    ScriptGrammarContributionsUtil.INSTANCE
                            .getJsClassDefinitionReader(new ArrayList<String>(
                                    getEnabledDestinations()),
                                    getScriptContext(),
                                    XPATH_GRAMMAR,
                                    XPATH_DESTINATION);

        } catch (CoreException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
        return xpathClassDefinitionReaders;
    }

    /**
     * Contribution local identifier.
     * 
     * @see org.eclipse.ui.IPluginContribution#getLocalId()
     */
    public String getLocalId() {
        return "developer.editor"; //$NON-NLS-1$
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
