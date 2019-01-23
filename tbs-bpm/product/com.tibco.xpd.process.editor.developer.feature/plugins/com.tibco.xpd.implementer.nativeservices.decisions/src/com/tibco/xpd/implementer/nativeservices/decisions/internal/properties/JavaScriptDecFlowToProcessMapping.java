/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.decisions.internal.properties;

import java.util.ArrayList;
import java.util.Collection;

import com.tibco.xpd.processeditor.xpdl2.properties.AbstractProcToSubProcMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.properties.AbstractSubProcToProcMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processeditor.xpdl2.util.DecisionTaskObjectUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParametersContainer;
import com.tibco.xpd.xpdl2.Process;

/**
 * Mapping compositor for JavaScript grammar mappings from process to
 * dec-flow.
 * <p>
 * Note: Main content abstracted to
 * {@link AbstractProcToSubProcMappingCompositor} @ v3.4.2
 * 
 * SID : This handles composite mappings in DecisionService invocation tasks 
 * 
 * @author mtorres
 * @since 3.6
 */
public class JavaScriptDecFlowToProcessMapping extends
        AbstractSubProcToProcMappingCompositor {

    private Activity activity;
    
    /**
     * @param activity
     * @param target
     */
    public JavaScriptDecFlowToProcessMapping(Activity activity, String target) {
        super(activity, target);
        this.activity = activity;
    }

    /**
     * @param activity
     *            The activity.
     * @param target
     *            The target.
     * @param script
     *            The current script.
     */
    public JavaScriptDecFlowToProcessMapping(Activity activity,
            String target, String script) {
        super(activity, target, script);
        this.activity = activity;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.xpdl2.properties.
     * AbstractSubProcToProcMappingCompositor#getScriptGrammar()
     */
    @Override
    protected String getScriptGrammar() {
        return ScriptGrammarFactory.JAVASCRIPT;
    }
    
    /**
     * @param isInput
     *            true if input parameters, false if output.
     * @return The source items.
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.SingleMappingCompositor#getSourceItems()
     */
    public Collection<Object> getSourceItems(boolean isInput) {
        Collection<Object> items = new ArrayList<Object>();

        FormalParametersContainer procOrIfc = getSourceProcessOrIfc();

        for (String name : getSourceItemNames()) {
            if (procOrIfc instanceof Process) {
                ConceptPath path =
                        ConceptUtil.resolveConceptPath((Process) procOrIfc,
                                name);
                items.add(path);
            }
        }

        return items;
    }
    
    /**
     * Get the process that's the source of the data
     * 
     * @return process, process inteface or null
     */
    private FormalParametersContainer getSourceProcessOrIfc() {        
            return (FormalParametersContainer) DecisionTaskObjectUtil
                    .getDecisionFlow(activity);
    }
    
}
