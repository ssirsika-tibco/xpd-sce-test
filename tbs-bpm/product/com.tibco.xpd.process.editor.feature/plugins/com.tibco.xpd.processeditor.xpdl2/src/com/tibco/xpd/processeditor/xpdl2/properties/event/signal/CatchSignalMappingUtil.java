/**
 * Copyright (c) TIBCO Software Inc 2004-2012. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.event.signal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.StandardMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ScriptInformationUtil;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.SignalData;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Mapping util class for catch signal mappings.
 * 
 * @author aallway
 * @since 26 Apr 2012
 */
public class CatchSignalMappingUtil {

    /**
     * Get the command to add a new data mapping from the given source to the
     * given target for a catch signal event mapping.
     * 
     * @param ed
     * @param source
     * @param target
     * @param catchSignalEvent
     * @param subFlow
     * @return command as described above.
     */
    public static Command getCreateMappingCommand(EditingDomain ed,
            Object source, Object target, Activity catchSignalEvent) {

        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.CatchSignalMappingUtil_AddMappingFromSignal_menu);

        Expression oldExpression = null;
        String text = ""; //$NON-NLS-1$
        boolean isScriptCopy = false;

        if (source instanceof ScriptInformation) {
            /*
             * Source is a script.
             */
            if (((ScriptInformation) source).eContainer() instanceof DataMapping) {
                /*
                 * User as dragged a script that is already mapped to a target,
                 * create a copy. for the mapping.
                 */
                isScriptCopy = true;

                source =
                        StandardMappingUtil
                                .copyMappingScript((ScriptInformation) source,
                                        catchSignalEvent,
                                        DirectionType.OUT_LITERAL);
            }

            text = "_SCRIPT_"; //$NON-NLS-1$

        } else {
            /*
             * Source is a concept path.
             */
            text = getName(source);
        }

        String targetName = getName(target);

        /*
         * We don't need to worry about multiple mappings to same object because
         * mapper should have been setup to not support concatenation.
         * 
         * So always create a new data mapping.
         */

        SignalData signalData =
                getOrCreateSignalDataElement(ed, catchSignalEvent, cmd);

        DataMapping dataMapping = Xpdl2Factory.eINSTANCE.createDataMapping();

        String grammar =
                ScriptGrammarFactory.getScriptGrammar(catchSignalEvent,
                        DirectionType.OUT_LITERAL);
        if (grammar == null) {
            grammar =
                    ScriptGrammarFactory
                            .getDefaultScriptGrammar(catchSignalEvent);
        }

        Expression expression = Xpdl2ModelUtil.createExpression(targetName);
        expression.setScriptGrammar(grammar);

        dataMapping.setFormal(text);
        dataMapping.setDirection(DirectionType.OUT_LITERAL);
        dataMapping.setActual(expression);

        cmd.append(AddCommand.create(ed,
                signalData,
                XpdExtensionPackage.eINSTANCE.getSignalData_DataMappings(),
                dataMapping));

        /*
         * For mapping from script, there's just a couple more things to sort
         * out.
         */
        if (source instanceof ScriptInformation) {

            ScriptInformation information = (ScriptInformation) source;
            EObject informationContainer = information.eContainer();
            if (informationContainer == null) {
                /*
                 * Brand new script or copy of existing..
                 */
                if (!isScriptCopy) {
                    /*
                     * If it's brand new then make sure we set the initial
                     * values up.
                     */
                    information.setName(ScriptInformationUtil
                            .getNextScriptName(catchSignalEvent,
                                    DirectionType.OUT_LITERAL));

                    Expression scriptExpression =
                            Xpdl2ModelUtil.createExpression(""); //$NON-NLS-1$
                    scriptExpression.setScriptGrammar(grammar);
                    information.setExpression(scriptExpression);
                }

                /* Add script to new mapping. */
                Xpdl2ModelUtil.addOtherElement(dataMapping,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_Script(),
                        information);

            } else if (informationContainer instanceof Activity) {
                /*
                 * Add existing unmapped script to data mapping added above.
                 */
                cmd.append(Xpdl2ModelUtil.getAddOtherElementCommand(ed,
                        dataMapping,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_Script(),
                        information));
            }
        }

        return cmd;
    }

    /**
     * Get the command to delete a data mapping from the given source to the
     * given target in a catch signal event mapping.
     * 
     * @param ed
     * @param source
     * @param target
     * @param catchSignalEvent
     * @param dataMappingsContainer
     * @param dataMappingsFeatureReference
     * @param dataMappings
     * @param mappingDirection
     * @return Command as described above.
     */
    public static Command createDeleteMappingCommand(EditingDomain ed,
            Object source, Object target, Activity catchSignalEvent) {

        if (target instanceof ConceptPath) {
            ConceptPath targetData = (ConceptPath) target;

            SignalData signalData =
                    EventObjectUtil
                            .getSignalDataExtensionElement(catchSignalEvent);

            if (signalData != null) {

                DataMapping dataMapping =
                        StandardMappingUtil
                                .getExistingMappingToTarget(targetData,
                                        signalData.getDataMappings(),
                                        MappingDirection.OUT);

                if (dataMapping != null) {
                    CompoundCommand cmd =
                            new CompoundCommand(
                                    Messages.CatchSignalMappingUtil_RemoveMappingFromSignal_menu);

                    if (source instanceof ScriptInformation) {
                        /*
                         * When deleting a mapping from a script, preserve the
                         * script by adding (re-parenting) it back up onto the
                         * activity.
                         */
                        ScriptInformation information =
                                (ScriptInformation) source;

                        cmd.append(Xpdl2ModelUtil.getAddOtherElementCommand(ed,
                                catchSignalEvent,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_Script(),
                                information));

                    }

                    /*
                     * Delete the mapping.
                     */
                    cmd.append(RemoveCommand.create(ed,
                            signalData,
                            XpdExtensionPackage.eINSTANCE
                                    .getSignalData_DataMappings(),
                            dataMapping));

                    return cmd;
                }
            }

        } else if (source instanceof ConceptPath) {
            /*
             * Have to also allow for target being unset because of broken
             * mappings.
             */
            ConceptPath sourceData = (ConceptPath) source;

            SignalData signalData =
                    EventObjectUtil
                            .getSignalDataExtensionElement(catchSignalEvent);

            if (signalData != null) {
                DataMapping toRemove = null;
                String sourcePath = sourceData.getPath();

                for (DataMapping dataMapping : signalData.getDataMappings()) {
                    if (sourcePath.equals(dataMapping.getFormal())) {
                        toRemove = dataMapping;
                        break;
                    }
                }

                if (toRemove != null) {
                    CompoundCommand cmd =
                            new CompoundCommand(
                                    Messages.CatchSignalMappingUtil_RemoveMappingFromSignal_menu);

                    /*
                     * Delete the mapping.
                     */
                    cmd.append(RemoveCommand.create(ed,
                            signalData,
                            XpdExtensionPackage.eINSTANCE
                                    .getSignalData_DataMappings(),
                            toRemove));

                    return cmd;
                }
            }

        } else if (source instanceof ScriptInformation
                && ((ScriptInformation) source).eContainer() instanceof DataMapping) {
            /*
             * To remove a mapping from a script who's target is broken then we
             * need to move the script to activity and remove the data mapping.
             */

            EObject dataMapping = ((ScriptInformation) source).eContainer();

            EObject copyScript = EcoreUtil.copy((ScriptInformation) source);

            CompoundCommand cmd =
                    new CompoundCommand(
                            Messages.CatchSignalMappingUtil_RemoveMappingFromSignal_menu);

            cmd.append(RemoveCommand.create(ed, dataMapping));
            cmd.append(Xpdl2ModelUtil.getAddOtherElementCommand(ed,
                    catchSignalEvent,
                    XpdExtensionPackage.eINSTANCE.getDocumentRoot_Script(),
                    copyScript));

            return cmd;
        }

        return UnexecutableCommand.INSTANCE;
    }

    /**
     * @param item
     *            The item to get the name for.
     * @return The name.
     */
    private static String getName(Object item) {
        String name = null;
        if (item instanceof NamedElement) {
            name = ((NamedElement) item).getName();
        } else if (item instanceof ConceptPath) {
            name = ((ConceptPath) item).getPath();
        }
        return name;
    }

    /**
     * Get the mappings content (array of {@link Mapping}'s for catch signal
     * event
     * 
     * @param catchSignalActivity
     *            The activity that the mappings are for.
     * 
     * @return Array of Mapping's current defined for the activity.
     */
    public static Object[] getMappings(Activity catchSignalActivity,
            Collection<ProcessRelevantData> signalPayload) {

        SignalData signalData =
                EventObjectUtil
                        .getSignalDataExtensionElement(catchSignalActivity);

        if (signalData != null) {

            /*
             * When attached to a task, the scope of the data should include
             * that task's local data.
             */
            Activity targetDataScopeActivity =
                    EventObjectUtil.getTaskAttachedTo(catchSignalActivity);

            if (targetDataScopeActivity == null) {
                targetDataScopeActivity = catchSignalActivity;
            }

            /*
             * Use the first signal event that throws the signal that input
             * activity catches for resolving payload data concept paths.
             */
            Activity throwSignalEvent = null;
            Collection<Activity> throwSignalsInScope =
                    EventObjectUtil.getThrowSignalsInScope(catchSignalActivity);
            if (!throwSignalsInScope.isEmpty()) {
                throwSignalEvent = throwSignalsInScope.iterator().next();
            }

            List<Mapping> resultMappings = new ArrayList<Mapping>();

            for (DataMapping xpdlMapping : signalData.getDataMappings()) {
                if (xpdlMapping.getDirection()
                        .equals(DirectionType.OUT_LITERAL)) {

                    /* Get Target data */
                    Expression actual = xpdlMapping.getActual();

                    if (actual != null) {
                        String path = actual.getText();
                        ConceptPath targetObj =
                                ConceptUtil
                                        .resolveConceptPath(targetDataScopeActivity,
                                                path);

                        /*
                         * Carry on EVEN if we cannot find that target so that
                         * the Mapping gets created and user can see the BROKEN
                         * mapping.
                         */
                        ScriptInformation information =
                                (ScriptInformation) Xpdl2ModelUtil
                                        .getOtherElement(xpdlMapping,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_Script());

                        if (information != null) {
                            /*
                             * Mapping from script.
                             */
                            resultMappings.add(new Mapping(information,
                                    targetObj, xpdlMapping));

                        } else {
                            /*
                             * Mapping from payload data
                             */
                            Object other =
                                    Xpdl2ModelUtil
                                            .getOtherElement(xpdlMapping,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getDocumentRoot_Expression());

                            String script;

                            if (other != null && other instanceof Expression) {
                                Expression expression = (Expression) other;
                                script = expression.getText();
                            } else {
                                script = xpdlMapping.getFormal();
                            }

                            /* Find the source data in the payload data. */
                            ConceptPath sourceObj = null;
                            if (throwSignalEvent != null) {
                                /*
                                 * Resolve the source path in the context of the
                                 * throw signal activity.
                                 */
                                sourceObj =
                                        ConceptUtil
                                                .resolveConceptPath(throwSignalEvent,
                                                        script);

                                /*
                                 * Then double check that this field is still
                                 * part of the signalPayload (in case it has
                                 * become disassociated.
                                 */
                                if (sourceObj != null) {
                                    boolean foundInPayload = false;

                                    ConceptPath rootObj = sourceObj.getRoot();

                                    if (rootObj.getItem() instanceof ProcessRelevantData) {
                                        for (ProcessRelevantData payloadData : signalPayload) {
                                            if (rootObj.getItem()
                                                    .equals(payloadData)) {
                                                foundInPayload = true;
                                                break;
                                            }
                                        }
                                    }

                                    if (!foundInPayload) {
                                        sourceObj = null;
                                    }
                                }
                            }

                            resultMappings.add(new Mapping(sourceObj,
                                    targetObj, xpdlMapping));
                        }
                    }
                }
            }

            return resultMappings.toArray();
        }

        return new Object[0];
    }

    /**
     * Get the xpdExt:SignalData from the xpdl2:TriggerResultSignal element
     * <p>
     * If it is not already there then add command to cmd to add it.
     * 
     * @param ed
     * @param catchSignalEvent
     * @param cmd
     * 
     * @return SignalData
     */
    public static SignalData getOrCreateSignalDataElement(EditingDomain ed,
            Activity catchSignalEvent, CompoundCommand cmd) {

        TriggerResultSignal triggerResultSignal =
                (TriggerResultSignal) catchSignalEvent.getEvent()
                        .getEventTriggerTypeNode();

        SignalData signalData =
                (SignalData) Xpdl2ModelUtil
                        .getOtherElement(triggerResultSignal,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_SignalData());

        if (signalData == null) {
            signalData = XpdExtensionFactory.eINSTANCE.createSignalData();

            cmd.append(Xpdl2ModelUtil.getSetOtherElementCommand(ed,
                    triggerResultSignal,
                    XpdExtensionPackage.eINSTANCE.getDocumentRoot_SignalData(),
                    signalData));
        }

        return signalData;
    }
}
