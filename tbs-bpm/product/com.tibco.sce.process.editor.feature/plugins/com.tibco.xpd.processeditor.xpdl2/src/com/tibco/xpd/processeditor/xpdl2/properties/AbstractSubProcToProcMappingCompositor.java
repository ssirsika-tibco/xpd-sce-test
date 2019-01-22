/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.event.error.CatchBpmnErrorMapperSection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.JavaScriptUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.SingleMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.util.SubProcUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.FormalParametersContainer;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Mapping compositor for any grammar mappings from sub-process to process
 * whereby the source data is simply the name of a process field/parameter OR
 * "field+field+field" style concatenation.
 * <p>
 * Note: Main content abstracted from
 * {@link AbstractSubProcToProcMappingCompositor} @ v3.4.2
 * 
 * @author aallway
 * @since 3.4.2 (23 Aug 2010)
 */
public abstract class AbstractSubProcToProcMappingCompositor implements
        SingleMappingCompositor {

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
    public AbstractSubProcToProcMappingCompositor(Activity activity,
            String target) {
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
    public AbstractSubProcToProcMappingCompositor(Activity activity,
            String target, String script) {
        this(activity, target);
        this.script = script;
    }

    /**
     * @return The script grammar to use for the scripts.
     */
    protected abstract String getScriptGrammar();

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
        script += SubProcUtil.getParameterName(source);
    }

    /**
     * @return true if the script contains any valid mappings.
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositor#
     *      containsMappings()
     */
    public boolean containsMappings() {
        boolean valid = false;
        if (script != null && script.trim().length() != 0) {
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
        actual.setScriptGrammar(getScriptGrammar());
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
        String parameter = SubProcUtil.getParameterName(source);
        script = JavaScriptUtil.removeParameter(script, parameter);
    }

    /**
     * @param target
     *            The target.
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositor#setTarget(java.lang.String)
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
        MappingDirection direction =
                isInput ? MappingDirection.IN : MappingDirection.OUT;

        FormalParametersContainer procOrIfc = getSourceProcessOrIfc();

        for (String name : getSourceItemNames()) {
            ConceptPath path =
                    SubProcUtil.resolveParameter(procOrIfc, direction, name);
            items.add(path);
        }

        return items;
    }

    /**
     * Get the process that's the source of the data
     * 
     * @return process, process inteface or null
     */
    private FormalParametersContainer getSourceProcessOrIfc() {
        if (activity.getImplementation() instanceof SubFlow) {
            return (FormalParametersContainer) TaskObjectUtil
                    .getSubProcessOrInterface(activity);

        } else if (activity.getEvent() instanceof IntermediateEvent) {
            IntermediateEvent event = (IntermediateEvent) activity.getEvent();

            if (TriggerType.ERROR_LITERAL.equals(event.getTrigger())) {
                Object thrower =
                        CatchBpmnErrorMapperSection
                                .getCatchTypeOrSpecificErrorEndEvent(activity);
                if (thrower instanceof Activity) {
                    return ((Activity) thrower).getProcess();
                } else if (thrower instanceof InterfaceMethod) {
                    return ProcessInterfaceUtil
                            .getProcessInterface((InterfaceMethod) thrower);
                }

            }
        }

        return null;
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
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.SingleMappingCompositor#setScript(java.lang.String)
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
        String parameter = SubProcUtil.getParameterName(source);
        script = JavaScriptUtil.removeParameter(script, parameter);
    }

}
