/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.refactor;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ltk.core.refactoring.participants.RenameArguments;

import com.tibco.xpd.n2.process.globalsignal.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Class to handle changes in references made to GSD element(s) from a Catch
 * signal event script.
 * 
 * @author sajain
 * @since May 14, 2015
 */
public class CatchSignalEventScriptGSDReferenceChange extends
        AbstractScriptGSDReferenceChange {

    /**
     * Catch signal event activity.
     */
    private Activity activity = null;

    /**
     * Script info to be changed.
     */
    private ScriptInformation scriptInfo = null;

    /**
     * Class to handle changes in references made to GSD element(s) from a Catch
     * signal event script.
     * 
     * @param oldScript
     * @param args
     * @param element
     * @param activity
     * @param editingDomain
     */
    public CatchSignalEventScriptGSDReferenceChange(String oldScript,
            RenameArguments args, EObject element, Activity activity,
            EditingDomain editingDomain, ScriptInformation scriptInfo) {

        super(oldScript, args, element, activity, editingDomain);

        this.activity = activity;
        this.scriptInfo = scriptInfo;
    }

    @Override
    protected Command getRefactorCommand() {

        return ProcessScriptUtil
                .getSetDataMappingScriptCommand(getEditingDomain(),
                        getNewScript(),
                        activity,
                        getScriptGrammar(),
                        scriptInfo);
    }

    @Override
    protected String getScriptType() {
        return ProcessScriptContextConstants.GLOBAL_CATCH_SIGNAL_EVENTMAPPING;
    }

    @Override
    protected String getScriptName() {

        if (scriptInfo != null && scriptInfo.getName() != null) {

            return scriptInfo.getName();
        }

        return Messages.GSEventScriptGSDReferenceChange_CatchSignalEvent;
    }
}
