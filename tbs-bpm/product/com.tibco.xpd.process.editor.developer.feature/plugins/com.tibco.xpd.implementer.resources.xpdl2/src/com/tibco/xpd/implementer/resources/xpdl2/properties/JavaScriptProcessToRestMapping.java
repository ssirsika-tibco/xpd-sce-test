/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2.properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

import com.tibco.xpd.implementer.script.ActivityMessageUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.JavaScriptUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositorFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.script.SingleMappingCompositor;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Script mapping compositor for Process to REST service mappings. Contains
 * Factory class used by the extension.
 * 
 * See also: 'com.tibco.xpd.processeditor.xpdl2.scriptMappingCompositor'
 * extension.
 * 
 * @author jarciuch
 * @since 10 Apr 2015
 */
public class JavaScriptProcessToRestMapping implements SingleMappingCompositor {

    public static class Factory extends ScriptMappingCompositorFactory {

        /**
         * @param activity
         *            The activity containing the mapping.
         * @param target
         *            The target field name.
         * @return The ScriptMappingCompositor.
         */
        @Override
        public ScriptMappingCompositor getCompositor(Activity activity,
                String target) {
            return new JavaScriptProcessToRestMapping(activity, target);
        }

        /**
         * @param activity
         *            The activity containing the mapping.
         * @param target
         *            The target field name.
         * @param script
         *            The existing script.
         * @return The ScriptMappingCompositor.
         */
        @Override
        public ScriptMappingCompositor getCompositor(Activity activity,
                String target, String script) {
            return new JavaScriptProcessToRestMapping(activity, target, script);
        }

    }

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
    public JavaScriptProcessToRestMapping(Activity activity, String target) {
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
    public JavaScriptProcessToRestMapping(Activity activity, String target,
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
    @Override
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
    @Override
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
    @Override
    public Expression getExpression() {
        Expression actual = Xpdl2ModelUtil.createExpression(script == null ? "" //$NON-NLS-1$
                : script);
        actual.setScriptGrammar(ScriptGrammarFactory.JAVASCRIPT);
        return actual;
    }

    /**
     * @return The target.
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositor#
     *      getTarget()
     */
    @Override
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
    @Override
    public void removeMapping(Object source, Object target) {
        String parameter = ActivityMessageUtil.getPath(source);
        script = JavaScriptUtil.removeParameter(script, parameter);
    }

    /**
     * @param target
     *            The target.
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositor
     *      #setTarget(java.lang.String)
     */
    @Override
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * @return The script.
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.SingleMappingCompositor
     *      #getScript()
     */
    @Override
    public String getScript() {
        return script;
    }

    /**
     * @param isInput
     *            true if input parameters, false if output.
     * @return The source items.
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.SingleMappingCompositor
     *      #getSourceItems()
     */
    @Override
    public Collection<Object> getSourceItems(boolean isInput) {
        Collection<Object> items = new ArrayList<Object>();
        for (String name : getSourceItemNames()) {
            ConceptPath path = ConceptUtil.resolveConceptPath(activity, name);
            items.add(path);
        }
        return items;
    }

    /**
     * @return A collection of source item names.
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.SingleMappingCompositor#getSourceItemNames()
     */
    @Override
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
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.SingleMappingCompositor#setScript(java.lang.String)
     */
    @Override
    public void setScript(String script) {
        this.script = script;
    }

    /**
     * @param source
     *            The source item to remove from the mapping.
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositor#
     *      removeAllMappingsFrom(java.lang.Object)
     */
    @Override
    public void removeAllMappingsFrom(Object source) {
        String parameter = ActivityMessageUtil.getPath(source);
        script = JavaScriptUtil.removeParameter(script, parameter);
    }

}
