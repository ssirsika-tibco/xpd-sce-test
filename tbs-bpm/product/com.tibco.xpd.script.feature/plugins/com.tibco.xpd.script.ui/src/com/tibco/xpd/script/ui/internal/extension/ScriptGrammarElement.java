/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.script.ui.internal.extension;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

import com.tibco.xpd.script.sourceviewer.internal.contentassist.AbstractTibcoContentAssistProcessor;
import com.tibco.xpd.script.sourceviewer.internal.preferences.AbstractScriptCommonUIPreferenceNames;
import com.tibco.xpd.script.sourceviewer.internal.viewer.listeners.AbstractLineStyleListenerProvider;
import com.tibco.xpd.script.ui.api.AbstractScriptEditorSection;

/**
 * 
 * Representation of the
 * 
 * @author kupadhya
 */
public class ScriptGrammarElement {

    public static final String SCRIPT_GRAMMAR_EXTENSION_POINT_NAME =
            "scriptGrammar"; //$NON-NLS-1$

    public static final String ATT_GRAMMAR_ID = "grammarId"; //$NON-NLS-1$

    public static final String ATT_GRAMMAR_NAME = "grammarName"; //$NON-NLS-1$

    public static final String ELE_SCRIPT_GRAMMAR = "scriptGrammar"; //$NON-NLS-1$

    public static final String ATT_ID = "id"; //$NON-NLS-1$

    public static final String ATT_NAME = "name"; //$NON-NLS-1$

    public static final String ELEMENT_EDITOR_SECTION = "editorSection"; //$NON-NLS-1$

    public static final String ELEMENT_CONTENT_ASSIST_PROCESSOR =
            "contentAssistProcessor"; //$NON-NLS-1$

    public static final String ATT_EDITOR_SECTION = "editorSectionClass"; //$NON-NLS-1$

    public static final String ELEMENT_EDITOR_STYLE_PROCESSOR =
            "editorStyleProcessor"; //$NON-NLS-1$

    public static final String ATT_MAPPABLE = "mappable"; //$NON-NLS-1$  

    public static final String ATT_CONTENT_ASSIST_CLASS_NAME = "className"; //$NON-NLS-1$

    public static final String ATT_CONTENT_ASSIST_PREFERENCE_CLASS_NAME =
            "preferencesClassName"; //$NON-NLS-1$

    public static final String ATT_EDITOR_STYLE_PROCESSOR_LINE_STYLE_LISTENER_PROVIDER =
            "lineStyleListenerProvider"; //$NON-NLS-1$

    private AbstractScriptEditorSection iSectionClass = null;

    private AbstractTibcoContentAssistProcessor contentAssistProcessor = null;

    private AbstractLineStyleListenerProvider lineStyleListenerProvider = null;

    private IConfigurationElement configElement = null;

    private AbstractScriptCommonUIPreferenceNames scriptPreferenceNames;

    private boolean mappable;

    /**
     * Default constructor
     * 
     * @param configElement
     */
    public ScriptGrammarElement(IConfigurationElement configurationElement) {
        this.configElement = configurationElement;

    }

    /**
     * Get the extension configuration element
     * 
     * @return
     */
    public IConfigurationElement getConfigElement() {
        return configElement;
    }

    /**
     * Get the <i>name</i> set in this extension
     * 
     * @return Extension name
     */
    public String getName() {
        return getAttribute(ATT_NAME);
    }

    /**
     * Get the <i>grammar</i> set in this extension
     * 
     * @return grammar id
     */
    public String getGrammarId() {
        if (configElement == null) {
            return null;
        }
        return configElement.getAttribute(ATT_GRAMMAR_ID);
    }

    /**
     * Get the <i>grammar</i> set in this extension
     * 
     * @return grammar id
     */
    public String getGrammarName() {
        if (configElement == null) {
            return null;
        }
        return configElement.getAttribute(ATT_GRAMMAR_NAME);
    }

    /**
     * Get the <i>grammar</i> set in this extension
     * 
     * @return grammar id
     */
    public boolean getMappable() {
        if (configElement == null) {
            return false;
        }
        IConfigurationElement[] children =
                configElement.getChildren(ELEMENT_EDITOR_SECTION);
        if (children != null && children.length > 0) {
            IConfigurationElement configurationElement = children[0];
            String att = configurationElement.getAttribute(ATT_MAPPABLE);
            if (att != null) {
                mappable = Boolean.parseBoolean(att);
            }
        }

        return mappable;
    }

    /**
     * Get the <i>class</i> set in the extension
     * <p>
     * Note: This may return
     * 
     * @return <code>ISection</code> object defined in the extension
     * @throws CoreException
     */
    public AbstractScriptEditorSection getISectionExec() throws CoreException {
        if (configElement == null) {
            return null;
        }
        if (iSectionClass != null) {
            return iSectionClass;
        }

        IConfigurationElement[] children =
                configElement.getChildren(ELEMENT_EDITOR_SECTION);
        if (children != null && children.length > 0) {
            IConfigurationElement configurationElement = children[0];
            Object sectionObj =
                    configurationElement
                            .createExecutableExtension(ATT_EDITOR_SECTION);
            if (sectionObj instanceof AbstractScriptEditorSection) {
                iSectionClass = (AbstractScriptEditorSection) sectionObj;
            }
        }

        return iSectionClass;
    }

    /**
     * Get the given attribute from the configuration element
     * 
     * @param attr
     * @return Attribute
     */
    private String getAttribute(String attr) {
        if (configElement != null) {
            return configElement.getAttribute(attr);
        }
        return null;
    }

    public String getId() {
        return getAttribute(ATT_ID);
    }

    /**
     * @return
     * @throws CoreException
     */
    public AbstractTibcoContentAssistProcessor getContentAssistProcessor()
            throws CoreException {
        if (contentAssistProcessor != null) {
            return contentAssistProcessor;
        }
        if (configElement != null) {
            IConfigurationElement[] configurationElements =
                    configElement.getChildren(ELEMENT_CONTENT_ASSIST_PROCESSOR);
            if (configurationElements != null
                    && configurationElements.length > 0) {
                IConfigurationElement configurationElement =
                        configurationElements[0];
                Object contentAssistObj =
                        configurationElement
                                .createExecutableExtension(ATT_CONTENT_ASSIST_CLASS_NAME);
                if (contentAssistObj instanceof AbstractTibcoContentAssistProcessor) {
                    contentAssistProcessor =
                            (AbstractTibcoContentAssistProcessor) contentAssistObj;
                }
            }
        }
        return contentAssistProcessor;
    }

    public AbstractScriptCommonUIPreferenceNames getScriptCommonUIPreferenceNames()
            throws CoreException {
        if (scriptPreferenceNames != null) {
            return scriptPreferenceNames;
        }

        if (configElement != null) {
            IConfigurationElement[] configurationElements =
                    configElement.getChildren(ELEMENT_CONTENT_ASSIST_PROCESSOR);
            if (configurationElements != null
                    && configurationElements.length > 0) {
                IConfigurationElement configurationElement =
                        configurationElements[0];
                Object scriptPreferenceNamesObj =
                        configurationElement
                                .createExecutableExtension(ATT_CONTENT_ASSIST_PREFERENCE_CLASS_NAME);
                if (scriptPreferenceNamesObj instanceof AbstractScriptCommonUIPreferenceNames) {
                    scriptPreferenceNames =
                            (AbstractScriptCommonUIPreferenceNames) scriptPreferenceNamesObj;
                }
            }
        }
        return scriptPreferenceNames;
    }

    /**
     * @return
     * @throws CoreException
     */
    public AbstractLineStyleListenerProvider getLineStyleListenerProvider()
            throws CoreException {
        if (lineStyleListenerProvider != null) {
            return lineStyleListenerProvider;
        }

        if (configElement != null) {
            IConfigurationElement[] configurationElements =
                    configElement.getChildren(ELEMENT_EDITOR_STYLE_PROCESSOR);
            if (configurationElements != null
                    && configurationElements.length > 0) {
                IConfigurationElement configurationElement =
                        configurationElements[0];
                Object lineStyleListenerObj =
                        configurationElement
                                .createExecutableExtension(ATT_EDITOR_STYLE_PROCESSOR_LINE_STYLE_LISTENER_PROVIDER);
                if (lineStyleListenerObj instanceof AbstractLineStyleListenerProvider) {
                    lineStyleListenerProvider =
                            (AbstractLineStyleListenerProvider) lineStyleListenerObj;
                }
            }
        }
        return lineStyleListenerProvider;
    }
}
