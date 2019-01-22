/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2.properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

import com.tibco.xpd.implementer.script.ActivityMessageUtil;
import com.tibco.xpd.implementer.script.IWsdlPath;
import com.tibco.xpd.implementer.script.WsdlUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.JavaScriptUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.SingleMappingCompositor;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author nwilson
 */
public class XPathWsdlToProcessMapping implements SingleMappingCompositor {

    /** The script grammar. */
    private static final String SCRIPT_GRAMMAR = "XPath"; //$NON-NLS-1$

    /** The activity. */
    private Activity activity;

    /** The target. */
    private String target;

    /** The script. */
    private String script;

    /**
     * @param activity
     *            The activity.
     * @param target
     *            The target.
     */
    public XPathWsdlToProcessMapping(Activity activity, String target) {
        this.activity = activity;
        this.target = target;
    }

    /**
     * @param activity
     *            The activity.
     * @param target
     *            The target.
     * @param script
     *            The current script.
     */
    public XPathWsdlToProcessMapping(Activity activity, String target,
            String script) {
        this(activity, target);
        this.script = script;
    }

    /**
     * @param source
     *            The source parameter.
     * @param target
     *            The target parameter.
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositor#
     *      addMapping(java.lang.Object, java.lang.Object)
     */
    public void addMapping(Object source, Object target) {
        if (script == null) {
            script = ""; //$NON-NLS-1$
        }
        if (script.length() != 0) {
            script += " + "; //$NON-NLS-1$
        }
        script += ActivityMessageUtil.getPath(source);
    }

    /**
     * @return true if the script contains any valid mappings.
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositor#
     *      containsMappings()
     */
    public boolean containsMappings() {
        boolean valid = false;
        if (getSourceItemNames().size() > 0) {
            valid = true;
        }
        return valid;
    }

    /**
     * @return The full script as an Expression.
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositor#
     *      getExpression()
     */
    public Expression getExpression() {
        Expression actual = Xpdl2ModelUtil.createExpression(script == null ? "" //$NON-NLS-1$
                : script);
        actual.setScriptGrammar(SCRIPT_GRAMMAR);
        return actual;
    }

    /**
     * @return The target.
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositor#
     *      getTarget()
     */
    public String getTarget() {
        return target;
    }

    /**
     * @param source
     *            The source parameter.
     * @param target
     *            The target parameter.
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositor#
     *      removeMapping(java.lang.Object, java.lang.Object)
     */
    public void removeMapping(Object source, Object target) {
        String parameter = ActivityMessageUtil.getPath(source);
        String oldParameter = getOldParameter(source);
        script = JavaScriptUtil.removeParameter(script, parameter);
        if (oldParameter != null && !oldParameter.equals(parameter)) {
            script = JavaScriptUtil.removeParameter(script, oldParameter);
        }
    }

    /**
     * @param source
     * @return
     */
    private String getOldParameter(Object source) {
        String oldParameter = null;
        for (String name : getSourceItemNames()) {
            IWsdlPath path = WsdlUtil.resolveParameter(activity, name, false);
            if (path != null && path.equals(source)) {
                oldParameter = name;
                break;
            }
        }
        return oldParameter;
    }

    /**
     * @param target
     *            The target.
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositor#setTarget(
     *      java.lang.String)
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * @return The script.
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.SingleMappingCompositor#getScript()
     */
    public String getScript() {
        return script;
    }

    /**
     * @param isInput
     *            true if input parameters, false if output.
     * @return The source items.
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.SingleMappingCompositor#getSourceItems()
     */
    public Collection<Object> getSourceItems(boolean isInput) {
        Collection<Object> items = new ArrayList<Object>();
        if (script != null && script.contains("wso:")) { //$NON-NLS-1$
            StringTokenizer tokenizer = new StringTokenizer(script, " +"); //$NON-NLS-1$
            while (tokenizer.hasMoreTokens()) {
                String path = tokenizer.nextToken();
                items.add(WsdlUtil.resolveParameter(activity, path, isInput));
            }
        } else {
            for (String name : getSourceItemNames()) {
                items.add(WsdlUtil.resolveParameter(activity, name, isInput));
            }
        }
        return items;
    }

    /**
     * @return A collection of source item names.
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.SingleMappingCompositor#getSourceItemNames()
     */
    public Collection<String> getSourceItemNames() {
        Collection<String> items = new ArrayList<String>();
        StringTokenizer tokenizer = new StringTokenizer(script, "+"); //$NON-NLS-1$
        while (tokenizer.hasMoreTokens()) {
            items.add(tokenizer.nextToken().trim());
        }
        return items;
    }

    /**
     * @param script
     *            The script.
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.SingleMappingCompositor#setScript(
     *      java.lang.String)
     */
    public void setScript(String script) {
        this.script = script;
    }

    /**
     * @param source
     *            The source item to remove from the mapping.
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositor#
     *      removeAllMappingsFrom(java.lang.Object)
     */
    public void removeAllMappingsFrom(Object source) {
        String parameter = ActivityMessageUtil.getPath(source);
        script = JavaScriptUtil.removeParameter(script, parameter);
    }

}
