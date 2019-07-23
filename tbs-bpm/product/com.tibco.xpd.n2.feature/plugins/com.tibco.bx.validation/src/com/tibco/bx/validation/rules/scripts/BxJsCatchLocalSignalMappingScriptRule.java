/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.bx.validation.rules.scripts;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.js.validation.rules.MappingRuleUtil;
import com.tibco.xpd.js.validation.tools.CatchSignalMappingScriptTool;
import com.tibco.xpd.js.validation.tools.ScriptTool;
import com.tibco.xpd.n2.process.localsignal.datamapper.mapping.CatchLocalSignalMappedScriptTool;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Script Mapping Rule for catch LOCAL signals script, both for mapped and
 * un-mapped scripts.
 *
 * @author sajain
 * @since Jul 18, 2019
 */
public class BxJsCatchLocalSignalMappingScriptRule extends AbstractBxJsCatchSignalMappingScriptRule {

    public BxJsCatchLocalSignalMappingScriptRule() {
        super("bx.error.catchLocalSignalMapToSignalScript", "bx.warning.catchLocalSignalMapToSignalScript"); //$NON-NLS-1$//$NON-NLS-2$
    }

    /**
     * @see com.tibco.xpd.js.validation.rules.AbstractExpressionRule#createScriptToolIfInterested(com.tibco.xpd.xpdl2.Expression)
     * 
     * @param expression
     * @return
     */
    @Override
    protected ScriptTool createScriptToolIfInterested(Expression expression) {
        if (expression.eContainer() instanceof ScriptInformation) {

            ScriptInformation scriptInformation = (ScriptInformation) expression.eContainer();

            /*
             * Sid XPD-8009: to be on the safe side explicitly do not validate
             * process data mapper script mapping scripts.
             */
            if (!MappingRuleUtil.isProcessDataScriptMappingScript(scriptInformation)) {

                /*
                 * Sid XPD-7914 only pick up mapping scripts we are specifically
                 * interested in (else we will validate process data mapper task
                 * scripts)
                 */
                if (Xpdl2ModelUtil.getAncestor(scriptInformation, TriggerResultSignal.class) != null) {
                    Activity activity = (Activity) Xpdl2ModelUtil.getAncestor(expression, Activity.class);

                    if (activity != null && EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL
                            .equals(EventObjectUtil.getEventTriggerType(activity))) {

                        EObject dataMapping = Xpdl2ModelUtil.getAncestor(scriptInformation, DataMapping.class);
                        if (EventObjectUtil.isLocalSignalEvent(activity)) {
                            if (dataMapping != null) {
                                // for mapped scenario
                                return getScope().getTool(CatchLocalSignalMappedScriptTool.class, dataMapping);
                            } else {
                                // for unmapped scenario
                                return getScope().getTool(CatchSignalMappingScriptTool.class, scriptInformation);
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.js.validation.rules.AbstractExpressionRule#getScriptContext()
     * 
     * @return
     */
    @Override
    protected String getScriptContext() {

        return ProcessScriptContextConstants.CATCH_SIGNAL_EVENTMAPPING;
    }
}
