/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.refactor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.ltk.core.refactoring.participants.RenameArguments;

import com.tibco.xpd.globalSignalDefinition.util.GlobalSignalUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Factory for GSD reference change in scripts.
 * 
 * @author sajain
 * @since May 13, 2015
 */
public class ScriptGSDReferenceChangeFactory {

    /**
     * Script container.
     */
    private EObject scriptContainer;

    /**
     * List of GSD reference changes.
     */
    private List<AbstractScriptGSDReferenceChange> changes;

    /**
     * Rename arguments.
     */
    private RenameArguments args = null;

    /**
     * Element being refactored.
     */
    private EObject element = null;

    /**
     * Factory for GSD reference change in scripts.
     * 
     * @param scriptContainer
     * @param args
     * @param element
     */
    public ScriptGSDReferenceChangeFactory(EObject scriptContainer,
            RenameArguments args, EObject element) {
        this.scriptContainer = scriptContainer;
        this.args = args;
        this.element = element;
    }

    /**
     * Get changes in script.
     * 
     * @return Changes in script.
     */
    public List<AbstractScriptGSDReferenceChange> getChanges() {

        if (changes == null) {

            TransactionalEditingDomain editingDomain =
                    XpdResourcesPlugin.getDefault().getEditingDomain();

            changes = new ArrayList<AbstractScriptGSDReferenceChange>();

            String scriptGrammar = JsConsts.JAVASCRIPT_GRAMMAR;

            if (scriptContainer instanceof Activity) {

                Activity activity = (Activity) scriptContainer;

                if (EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL
                        .equals(EventObjectUtil.getEventTriggerType(activity))
                        && GlobalSignalUtil.isGlobalSignalEvent(activity)) {

                    List<ScriptInformation> allMappedAndUnMappedScriptInfo =
                            new ArrayList<ScriptInformation>();

                    /*
                     * Add all the unMapped Scripts.
                     */
                    allMappedAndUnMappedScriptInfo.addAll(ProcessScriptUtil
                            .getScriptInformationTasksWithScriptType(activity,
                                    scriptGrammar,
                                    DirectionType.OUT_LITERAL.getLiteral()));

                    List<DataMapping> dataMappings =
                            Xpdl2ModelUtil.getDataMappings(activity,
                                    DirectionType.OUT_LITERAL);

                    for (DataMapping dataMapping : dataMappings) {

                        ScriptInformation scriptInformationFromDataMapping =
                                ProcessScriptUtil
                                        .getScriptInformationFromDataMapping(dataMapping);

                        if (scriptInformationFromDataMapping != null) {

                            /*
                             * Add all mapped Scripts.
                             */
                            allMappedAndUnMappedScriptInfo
                                    .add(scriptInformationFromDataMapping);
                        }
                    }

                    for (ScriptInformation eachScriptInfo : allMappedAndUnMappedScriptInfo) {

                        if (null != eachScriptInfo
                                && null != eachScriptInfo.getExpression()) {

                            String catchGlobalSignalEventScript =
                                    eachScriptInfo.getExpression().getText();

                            changes.add(new CatchSignalEventScriptGSDReferenceChange(
                                    catchGlobalSignalEventScript, args,
                                    element, activity, editingDomain,
                                    eachScriptInfo));

                        }
                    }
                }
            }
        }

        return changes;
    }
}
