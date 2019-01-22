/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.scripteditors.internal.javascript;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.IPluginContribution;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.script.model.client.JsClassDefinitionReader;
import com.tibco.xpd.script.parser.validator.IValidationStrategy;
import com.tibco.xpd.script.ui.ScriptGrammarContributionsUtil;
import com.tibco.xpd.script.ui.api.AbstractScriptEditorSection;
import com.tibco.xpd.scripteditors.ScriptEditorsPlugin;

/**
 * JavaScript Editor Section, facilitates script editing in JavaScript. Used in
 * different contexts and for different models
 * 
 * @author rsomayaj
 * 
 */
public class JavaScriptEditorSection extends AbstractScriptEditorSection
        implements IPluginContribution {

    private static final String SCRIPT_GRAMMAR = "JavaScript"; //$NON-NLS-1$

    private static final String DEFAULT_JAVASCRIPT_DESTINATION =
            "javaScript1.x"; //$NON-NLS-1$

    public JavaScriptEditorSection(EClass eClass) {
        super(eClass);
    }

    public JavaScriptEditorSection() {
        super(null);
    }

    protected boolean isValidFieldName(String prdDataName) {
        if (prdDataName == null || prdDataName.trim().length() < 1) {
            return false;
        }
        int index = prdDataName.indexOf(" "); //$NON-NLS-1$
        if (index == -1) {
            // no space found, so good name.
            return true;
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.script.ui.api.AbstractScriptEditorSection#getScriptGrammar()
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
        List<JsClassDefinitionReader> jsClassDefinitionReaders =
                Collections.EMPTY_LIST;
        try {
            jsClassDefinitionReaders =
                    ScriptGrammarContributionsUtil.INSTANCE
                            .getJsClassDefinitionReader(new ArrayList<String>(
                                    getEnabledDestinations()),
                                    getScriptContext(),
                                    SCRIPT_GRAMMAR,
                                    DEFAULT_JAVASCRIPT_DESTINATION);
        } catch (CoreException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
        return jsClassDefinitionReaders;

    }

    @Override
    protected List<IValidationStrategy> getValidationStrategies() {
        List<IValidationStrategy> validationStrategies = Collections.EMPTY_LIST;
        try {
            validationStrategies =
                    ScriptGrammarContributionsUtil.INSTANCE
                            .getValidationStrategy(new ArrayList<String>(
                                    getEnabledDestinations()),
                                    getScriptContext(),
                                    SCRIPT_GRAMMAR,
                                    DEFAULT_JAVASCRIPT_DESTINATION);
        } catch (CoreException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
        return validationStrategies;
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

    /**
     * Sid XPD-7575 - Set offset margin as we have nested sections that waste a
     * lot of space.
     * 
     * @see com.tibco.xpd.script.ui.api.AbstractScriptEditorSection#getSectionMarginOffset()
     * 
     * @return
     */
    @Override
    public Point getSectionMarginOffset() {
        return new Point(0, -10);
    }
}
