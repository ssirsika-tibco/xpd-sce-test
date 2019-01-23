/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2.properties;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.implementer.script.ActivityMessageProvider;
import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;

/**
 * Mappings for any activity that has a message (through the
 * {@link ActivityMessageProvider} adapter. Converts XPDL {@link DataMapping}
 * into the generic {@link Mapping} element understood by the Mapper.
 * Understands expressions for actual parameters that use the WsdlPath script
 * syntax, and returns the appropriate XSD element for these as part of the
 * mapping.
 * 
 * @author scrossle
 */
public class ActivityMessageWsdlMappingItemProvider implements
        IStructuredContentProvider {

    /** The direction for this provider. */
    private MappingDirection direction;

    /** True if an input parameter. */
    private boolean wsdlInput;

    private IStructuredContentProvider activityWSDLMappingItemProvider;

    /**
     * @param direction
     *            The direction for this provider.
     * @param wsdlInput
     *            True if an input parameter.
     * @param adapterFactory
     *            The adapter factory.
     */
    public ActivityMessageWsdlMappingItemProvider(MappingDirection direction) {
        this(direction, true);
    }

    /**
     * @param direction
     *            The direction for this provider.
     * @param wsdlInput
     *            True if an input parameter.
     * @param adapterFactory
     *            The adapter factory.
     */
    public ActivityMessageWsdlMappingItemProvider(MappingDirection direction,
            boolean wsdlInput) {
        this.direction = direction;
        this.wsdlInput = wsdlInput;
    }

    /**
     * @param parent
     *            The parent activity.
     * @return A collection of mappings.
     * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#getElements(java.lang.Object)
     */
    @Override
    public Object[] getElements(Object parent) {
        if (null != activityWSDLMappingItemProvider) {
            return activityWSDLMappingItemProvider.getElements(parent);
        }
        return new Object[] {};
    }

    /**
     * 
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     */
    @Override
    public void dispose() {
    }

    /**
     * @param viewer
     * @param oldInput
     * @param newInput
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     */
    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        if (newInput instanceof Activity) {
            Activity activity = (Activity) newInput;
            String scriptGrammar =
                    ScriptGrammarFactory.getScriptGrammar(activity,
                            DirectionType.get(direction.toString()));

            activityWSDLMappingItemProvider = null;

            if (ScriptGrammarFactory.JAVASCRIPT.equals(scriptGrammar)) {
                activityWSDLMappingItemProvider =
                        createJavascriptMappingItemProvider();
            } else if (ScriptGrammarFactory.XPATH.equals(scriptGrammar)) {
                activityWSDLMappingItemProvider =
                        createXpathMappingItemProvider();
            }

            if (activityWSDLMappingItemProvider == null) {
                String defaultScriptGrammar = getDefaultScriptGrammar(activity);
                if (ScriptGrammarFactory.JAVASCRIPT
                        .equals(defaultScriptGrammar)) {
                    activityWSDLMappingItemProvider =
                            createJavascriptMappingItemProvider();
                } else if (ScriptGrammarFactory.XPATH
                        .equals(defaultScriptGrammar)) {
                    activityWSDLMappingItemProvider =
                            createXpathMappingItemProvider();
                }

            }

            if (activityWSDLMappingItemProvider != null) {
                activityWSDLMappingItemProvider.inputChanged(viewer,
                        oldInput,
                        newInput);
            }
        }

        return;
    }

    /**
     * @param activity
     * @return The default script grammar to use.
     */
    protected String getDefaultScriptGrammar(Activity activity) {
        return ScriptGrammarFactory.getDefaultScriptGrammar(activity);
    }

    protected IStructuredContentProvider createJavascriptMappingItemProvider() {
        JavaScriptMappingItemProvider javaMappingItemProvider =
                new JavaScriptMappingItemProvider(direction, wsdlInput);
        return javaMappingItemProvider;
    }

    protected IStructuredContentProvider createXpathMappingItemProvider() {
        XPathMappingItemProvider xpathMappingItemProvider =
                new XPathMappingItemProvider(direction, wsdlInput);
        return xpathMappingItemProvider;
    }

    /**
     * @return the direction
     */
    protected MappingDirection getDirection() {
        return direction;
    }

    /**
     * @return the wsdlInput
     */
    protected boolean isWsdlInput() {
        return wsdlInput;
    }
}
