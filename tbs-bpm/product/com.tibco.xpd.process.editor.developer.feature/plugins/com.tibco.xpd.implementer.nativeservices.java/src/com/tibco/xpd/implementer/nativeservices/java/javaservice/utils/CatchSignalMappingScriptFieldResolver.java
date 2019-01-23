/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.implementer.nativeservices.java.javaservice.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.implementer.nativeservices.java.internal.Messages;
import com.tibco.xpd.process.js.model.CatchSignalScriptRelevantDataProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Field resolver for catch signal mappings.
 * 
 * @author aallway
 * @since 20 Jun 2012
 */
public class CatchSignalMappingScriptFieldResolver extends
        AbstractMappingJavaScriptProcessFieldResolver {

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.AbstractMappingScriptProcessFieldResolver#isInterestingActivity(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected boolean isInterestingActivity(Activity activity) {
        if (isLocalCatchSignal(activity)) {
            List<DataMapping> dataMappings =
                    Xpdl2ModelUtil.getDataMappings(activity,
                            DirectionType.OUT_LITERAL);

            if (!dataMappings.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.AbstractMappingScriptProcessFieldResolver#getActivityScriptInformationList(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected List<ScriptInformation> getActivityScriptInformationList(
            Activity activity) {
        return ProcessScriptUtil.getAllCatchSignalScriptInformations(activity,
                getGrammarType());
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.AbstractMappingScriptProcessFieldResolver#getTaskName()
     * 
     * @return
     */
    @Override
    protected String getTaskName() {
        return ProcessScriptContextConstants.CATCH_SIGNAL_EVENTMAPPING;
    }

    /**
     * @param activity
     * @return <code>true</code> if activity is a Local catch signal event.
     */
    private boolean isLocalCatchSignal(Activity activity) {
        /*
         * XPD-7075: Applicable only for Local Catch signal events.
         */
        if (EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL.equals(EventObjectUtil
                .getEventTriggerType(activity))) {
            return EventObjectUtil.isLocalSignalEvent(activity);
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.AbstractMappingScriptProcessFieldResolver#getMappingInReferenceContextLabel(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected String getMappingInReferenceContextLabel(Activity activity) {
        return Messages.CatchSignalMappingScriptFieldResolver_MapFromSignal_label;
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.AbstractMappingScriptProcessFieldResolver#getMappingOutReferenceContextLabel(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected String getMappingOutReferenceContextLabel(Activity activity) {
        return Messages.CatchSignalMappingScriptFieldResolver_MapFromSignal_label;
    }

    /**
     * 
     * XPD-7032: We need to override this method as the LHS data in the 'Map
     * from Signal' mapper is actually the process data which is in the scope of
     * the Throw Signal Event which the catch signal is catching. The super
     * class method is only designed to update the 'Actual' parameter in the
     * data mapping because it assumes that the Actual params is the only thing
     * associated with the catch signal event. However this is a special case,
     * as the 'Formal' parameter is also in scope of the catch signal
     * event.(because Local throw and catch signal events are in the same
     * process so they share the same process data.). Hence we also need to
     * explicitly update the references to the Formal params in the Data
     * mappings
     * 
     * @see com.tibco.xpd.implementer.nativeservices.script.AbstractMappingScriptProcessFieldResolver#getSwapActivityDataNameReferencesCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      com.tibco.xpd.xpdl2.Activity, java.util.Map)
     * 
     * @param editingDomain
     * @param activity
     * @param nameMap
     * @return
     */
    @Override
    public Command getSwapActivityDataNameReferencesCommand(
            EditingDomain editingDomain, Activity activity,
            Map<String, String> nameMap) {

        /*
         * XPD-7032: We need to override this method as the LHS data in the 'Map
         * from Signal' mapper is actually the process data which is in the
         * scope of the Throw Signal Event which the catch signal is catching.
         * The super class method is only designed to update the 'Actual'
         * parameter in the data mapping because it assumes that the Actual
         * params is the only thing associated with the catch signal event.
         * However this is a special case, as the 'Formal' parameter is also in
         * scope of the catch signal event.(because Local throw and catch signal
         * events are in the same process so they share the same process data.).
         * Hence we also need to explicitly update the references to the Formal
         * params in the Data mappings
         */

        boolean interestedActivity = isInterestingActivity(activity);
        if (!interestedActivity) {
            return null;
        }

        /*
         * let the super class update all the references in the 'Actual' param
         * of the data mapping.
         */
        CompoundCommand setActivityScriptCommand =
                (CompoundCommand) super
                        .getSwapActivityDataNameReferencesCommand(editingDomain,
                                activity,
                                nameMap);

        if (setActivityScriptCommand == null) {
            /*
             * If the super class does not do anything, then create a command.
             * The command does not need a label as it wont be seen in
             * undo/redo.
             */
            setActivityScriptCommand = new CompoundCommand();
        }

        /*
         * In script mapping, in order to distinguish the data local to throw
         * signal event we prefix it with the 'Signal_' key. Hence we also need
         * to take care and update all such scripts.
         */
        Map<String, String> nameMapWithSignalPrefixes =
                new HashMap<String, String>();

        /*
         * add all the existing name mao
         */
        nameMapWithSignalPrefixes.putAll(nameMap);

        for (String oldName : nameMap.keySet()) {
            /*
             * Adding the 'Signal_oldName' to the 'Signal_newName' entry to the
             * map.
             */
            nameMapWithSignalPrefixes
                    .put(CatchSignalScriptRelevantDataProvider.SIGNAL_PAYLOAD_DATA_PREFIX
                            + oldName,
                            CatchSignalScriptRelevantDataProvider.SIGNAL_PAYLOAD_DATA_PREFIX
                                    + nameMap.get(oldName));

        }

        List<ScriptInformation> scriptInformations =
                getActivityScriptInformationList(activity);

        Process process = activity.getProcess();

        if (scriptInformations != null) {
            for (ScriptInformation scriptInformation : scriptInformations) {
                String script = getScript(scriptInformation);
                /*
                 * get the translated script.
                 */
                String translatedScript =
                        getTranslatedScript(process,
                                script,
                                nameMapWithSignalPrefixes,
                                getTaskName());
                if (translatedScript == null
                        || translatedScript.trim().length() < 1) {
                    continue;
                }
                /*
                 * set the new script.
                 */
                setActivityScriptCommand
                        .append(getSetScriptInformationScriptCommand(editingDomain,
                                translatedScript,
                                scriptInformation));
            }
        }

        /*
         * get all the data mappings.
         */
        List<DataMapping> dataMappingList =
                getActivityDataMappingList(activity);

        for (DataMapping dataMapping : dataMappingList) {
            if (ProcessScriptUtil.isAScriptMapping(dataMapping)
                    && ignoreScriptComposite()) {
                continue;
            }
            /*
             * get the formal param in the data mapping
             */
            String formalField = dataMapping.getFormal();

            if (formalField != null) {
                /*
                 * get the changed/translated param.
                 */
                String translatedRef =
                        getTranslatedParameterRef(activity,
                                formalField,
                                nameMap);

                if (translatedRef != null && !translatedRef.equals("")) { //$NON-NLS-1$
                    /*
                     * update the references of the formal param of the
                     * datamapping.
                     */
                    setActivityScriptCommand.append(SetCommand
                            .create(editingDomain,
                                    dataMapping,
                                    Xpdl2Package.eINSTANCE
                                            .getDataMapping_Formal(),
                                    translatedRef));
                }
            }
        }

        if (setActivityScriptCommand.isEmpty()) {
            return null;
        }

        return setActivityScriptCommand;
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.utils.AbstractMappingJavaScriptProcessFieldResolver#ignoreScriptComposite()
     * 
     * @return
     */
    @Override
    protected boolean ignoreScriptComposite() {
        /*
         * XPD-7032: We need to return false from here because the super class
         * method 'getSwapActivityDataNameReferencesCommand' uses this method &&
         * ProcessScriptUtil.isAScriptMapping(dataMapping) to decide if the
         * Actual param should be updated. For Catch Signal Mappings, the Formal
         * Param actually stores the Script Mappings(not the actual param), and
         * hence even if it is a Script Mapping we still would want the Actual
         * Param in the DataMapping to be updated.
         */
        return false;
    }
}
