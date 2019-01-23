/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2.properties;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.WsdlFilter.WsdlDirection;
import com.tibco.xpd.implementer.script.ActivityMessageProvider;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;

/**
 * Item provider for WSDL and XSD elements of a Web Service Operation accessed
 * through the {@link ActivityMessageProvider} adapter. This is an item provider
 * for all elements, expect these to be filtered in the viewer.
 * 
 * @author scrossle
 */
public class ActivityMessageWsdlItemProvider implements ITreeContentProvider {

    private static final String XPATH = ScriptGrammarFactory.XPATH;

    private static final String JAVA_SCRIPT = ScriptGrammarFactory.JAVASCRIPT;

    private static final Logger LOG = XpdResourcesPlugin.getDefault()
            .getLogger();

    /** The mapping direction. */
    private MappingDirection direction;

    /** The mapping direction relative to the service. */
    private DirectionType directionToService;

    private ITreeContentProvider javascriptBomContentProvider;

    private ITreeContentProvider xpathContentProvider;

    private ITreeContentProvider activityMessageContentProvider;

    private WsdlDirection wsdlDirection;

    private ITreeContentProvider getJavascriptBomContentProvider() {
        if (javascriptBomContentProvider == null) {
            javascriptBomContentProvider =
                    new JavaScriptBomContentProvider(direction,
                            directionToService, wsdlDirection);
        }
        return javascriptBomContentProvider;
    }

    private ITreeContentProvider getXpathContentProvider() {
        if (xpathContentProvider == null) {
            xpathContentProvider =
                    new XPathContentProvider(direction, directionToService,
                            wsdlDirection);
        }
        return xpathContentProvider;
    }

    /**
     * @param adapterFactory
     *            The adapter factory.
     * @param direction
     *            The mapping direction.
     */
    public ActivityMessageWsdlItemProvider(MappingDirection direction) {
        this.direction = direction;

    }

    /**
     * @param adapterFactory
     *            The adapter factory.
     * @param direction
     *            The mapping direction.
     */
    public ActivityMessageWsdlItemProvider(MappingDirection direction,
            DirectionType directionToService, WsdlDirection wsdlDirection) {
        this.direction = direction;
        this.directionToService = directionToService;
        this.wsdlDirection = wsdlDirection;

    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
     * 
     * @param parentElement
     * @return
     */
    @Override
    public Object[] getChildren(Object parentElement) {
        if (null != activityMessageContentProvider) {
            return activityMessageContentProvider.getChildren(parentElement);
        }
        return new Object[] {};
    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public Object getParent(Object element) {
        if (null != activityMessageContentProvider) {
            return activityMessageContentProvider.getParent(element);
        }
        return null;
    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public boolean hasChildren(Object element) {
        if (null != activityMessageContentProvider) {
            return activityMessageContentProvider.hasChildren(element);
        }
        return false;
    }

    /**
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
     * 
     * @param inputElement
     * @return
     */
    @Override
    public Object[] getElements(Object inputElement) {
        if (null != activityMessageContentProvider) {
            return activityMessageContentProvider.getElements(inputElement);
        }
        return new Object[] {};
    }

    /**
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     * 
     */
    @Override
    public void dispose() {
        if (null != activityMessageContentProvider) {
            activityMessageContentProvider.dispose();
        }
    }

    /**
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     * 
     * @param viewer
     * @param oldInput
     * @param newInput
     */
    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        if (newInput instanceof Activity) {
            Activity activity = (Activity) newInput;
            String scriptGrammar =
                    ScriptGrammarFactory.getScriptGrammar(activity,
                            directionToService);

            activityMessageContentProvider = null;

            if (JAVA_SCRIPT.equals(scriptGrammar)) {
                activityMessageContentProvider =
                        getJavascriptBomContentProvider();
            } else if (XPATH.equals(scriptGrammar)) {
                activityMessageContentProvider = getXpathContentProvider();
            }

            if (activityMessageContentProvider == null) {
                /*
                 * Script grammar for reply-immediate has to be forced to be
                 * Xpath (as it is in the property section)
                 */
                if (ReplyActivityUtil
                        .isStartMessageWithReplyImmediate(activity)
                        && DirectionType.IN_LITERAL.equals(directionToService)) {
                    activityMessageContentProvider = getXpathContentProvider();

                } else {
                    String defaultScriptGrammar =
                            getDefaultScriptGrammar(activity);
                    if (JAVA_SCRIPT.equals(defaultScriptGrammar)) {
                        activityMessageContentProvider =
                                getJavascriptBomContentProvider();
                    } else if (XPATH.equals(defaultScriptGrammar)) {
                        activityMessageContentProvider =
                                getXpathContentProvider();
                    }
                }

            }

            if (activityMessageContentProvider != null) {
                activityMessageContentProvider.inputChanged(viewer,
                        oldInput,
                        newInput);
            }
        }

        return;
    }

    /**
     * @param activity
     * @return The default script grammar to use in the absence of any existing
     *         mappings.
     */
    protected String getDefaultScriptGrammar(Activity activity) {
        return ScriptGrammarFactory.getDefaultScriptGrammar(activity);
    }

}
