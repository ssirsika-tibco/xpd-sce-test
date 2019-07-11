/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.globalSignalDefinition.PayloadDataField;
import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.n2.process.globalsignal.datamapper.GlobalSignalDataMapperConstants;
import com.tibco.xpd.n2.process.globalsignal.datamapper.contentcontributors.ProcessCorrelationDataContentContributor;
import com.tibco.xpd.n2.process.globalsignal.internal.Messages;
import com.tibco.xpd.n2.process.globalsignal.mapping.PayloadConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.StandardMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ScriptInformationUtil;
import com.tibco.xpd.xpdExtension.CorrelationDataMappings;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.SignalData;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Util class for Global Signal Mappings.
 * 
 * @author kthombar
 * @since Feb 10, 2015
 */
public final class GlobalSignalMappingUtil {

    /**
     * 
     * @param ed
     * @param source
     *            the process data in scope of throw global signal events.
     * @param target
     *            the global signal payload
     * @param throwSignalEvent
     * @return Command to create Throw Global Signal Mappings(i.e. mappings
     *         between process data and global signal pauyload)
     */
    public static Command getCreateThrowGlobalSignalMappingCommand(
            EditingDomain ed, Object source, Object target,
            Activity throwSignalEvent) {

        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.GlobalSignalMappingUtil_AddMappingToPayloadCommand_label);

        String sourceName = ""; //$NON-NLS-1$
        boolean isScriptCopy = false;

        if (source instanceof ScriptInformation) {
            /*
             * Source is a script.
             */
            if (((ScriptInformation) source).eContainer() instanceof DataMapping) {
                /*
                 * User as dragged a script that is already mapped to a target,
                 * create a copy for the mapping.
                 */
                isScriptCopy = true;

                DataMapping dtaMapping =
                        (DataMapping) ((ScriptInformation) source).eContainer();
                sourceName = dtaMapping.getActual().getText();

                source =
                        StandardMappingUtil
                                .copyMappingScript((ScriptInformation) source,
                                        throwSignalEvent,
                                        DirectionType.IN_LITERAL);
            } else {

                sourceName =
                        ((ScriptInformation) source).getExpression().getText();
            }

        } else {
            /*
             * Source is a concept path.
             */
            sourceName = getName(source);
        }

        /*
         * get the target name, for throw global events target will be payload
         * data.
         */
        String targetName = getName(target);

        /*
         * We don't need to worry about multiple mappings to same object because
         * mapper should have been setup to not support concatenation.
         * 
         * So always create a new data mapping.
         */

        SignalData signalData =
                getOrCreateSignalDataElement(ed, throwSignalEvent, cmd);

        DataMapping dataMapping = Xpdl2Factory.eINSTANCE.createDataMapping();

        String grammar =
                ScriptGrammarFactory.getScriptGrammar(throwSignalEvent,
                        DirectionType.IN_LITERAL);

        if (grammar == null) {
            grammar =
                    ScriptGrammarFactory
                            .getDefaultScriptGrammar(throwSignalEvent);
        }

        Expression expression = Xpdl2ModelUtil.createExpression(sourceName);
        expression.setScriptGrammar(grammar);

        /*
         * The payload data is the formal parameter and the process data is the
         * actual parameter.
         */
        dataMapping.setFormal(targetName);
        dataMapping.setDirection(DirectionType.IN_LITERAL);
        dataMapping.setActual(expression);

        if (isCorrelationData(target)) {

            /*
             * If the target is correlation payload then add correlation
             * mappings.
             */

            CorrelationDataMappings correlationMappings =
                    signalData.getCorrelationMappings();

            if (correlationMappings == null) {
                /*
                 * if no correlation mappings already present then create them.
                 */
                correlationMappings =
                        XpdExtensionFactory.eINSTANCE
                                .createCorrelationDataMappings();

                cmd.append(SetCommand.create(ed,
                        signalData,
                        XpdExtensionPackage.eINSTANCE
                                .getSignalData_CorrelationMappings(),
                        correlationMappings));

            }
            /*
             * Add Data mapping to correlation mapping.
             */
            cmd.append(AddCommand.create(ed,
                    correlationMappings,
                    XpdExtensionPackage.eINSTANCE
                            .getCorrelationDataMappings_DataMappings(),
                    dataMapping));

        } else {
            cmd.append(AddCommand.create(ed,
                    signalData,
                    XpdExtensionPackage.eINSTANCE.getSignalData_DataMappings(),
                    dataMapping));
        }

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
                            .getNextScriptName(throwSignalEvent,
                                    DirectionType.IN_LITERAL));

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
                 * The Actual expression stores the script so remove the
                 * expression from script information.
                 */
                cmd.append(SetCommand.create(ed,
                        information,
                        XpdExtensionPackage.eINSTANCE
                                .getScriptInformation_Expression(),
                        null));
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
     * 
     * @param ed
     * @param source
     *            the global signal payload
     * 
     * @param target
     *            the process data in scope of catch global signal events.
     * @param catchSignalEvent
     * @return Command to create Catch Global Signal Mappings(i.e. mappings
     *         between global signal pauyload and process data)
     */
    public static Command getCreateCatchGlobalSignalMappingCommand(
            EditingDomain ed, Object source, Object target,
            Activity catchSignalEvent) {

        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.GlobalSignalMappingUtil_AddMappingFromPayloadCommand_label);

        String sourceName = ""; //$NON-NLS-1$
        boolean isScriptCopy = false;

        if (source instanceof ScriptInformation) {
            /*
             * Source is a script.
             */
            if (((ScriptInformation) source).eContainer() instanceof DataMapping) {
                /*
                 * User as dragged a script that is already mapped to a target,
                 * create a copy for the mapping.
                 */
                isScriptCopy = true;

                source =
                        StandardMappingUtil
                                .copyMappingScript((ScriptInformation) source,
                                        catchSignalEvent,
                                        DirectionType.OUT_LITERAL);
            }

            sourceName = "_SCRIPT_"; //$NON-NLS-1$

        } else {

            sourceName = getName(source);
        }

        String targetName = getName(target);

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
        /*
         * The payload data is the formal parameter and the process data is the
         * actual parameter.
         */
        dataMapping.setFormal(sourceName);
        dataMapping.setDirection(DirectionType.OUT_LITERAL);
        dataMapping.setActual(expression);

        if (isCorrelationData(target)) {
            /*
             * If the target is correlation payload then add correlation
             * mappings.
             */
            CorrelationDataMappings correlationMappings =
                    signalData.getCorrelationMappings();

            if (correlationMappings == null) {
                /*
                 * If no correlation mappings already present then create new.
                 */
                correlationMappings =
                        XpdExtensionFactory.eINSTANCE
                                .createCorrelationDataMappings();

                cmd.append(SetCommand.create(ed,
                        signalData,
                        XpdExtensionPackage.eINSTANCE
                                .getSignalData_CorrelationMappings(),
                        correlationMappings));
            }
            /*
             * Add DataMapping to correlation mappings.
             */
            cmd.append(AddCommand.create(ed,
                    correlationMappings,
                    XpdExtensionPackage.eINSTANCE
                            .getCorrelationDataMappings_DataMappings(),
                    dataMapping));

        } else {
            cmd.append(AddCommand.create(ed,
                    signalData,
                    XpdExtensionPackage.eINSTANCE.getSignalData_DataMappings(),
                    dataMapping));
        }

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
     * 
     * @param item
     * @return <code>true</code> if the passed item is a correlation Data else
     *         return <code>false</code>
     */
    private static boolean isCorrelationData(Object item) {
        if (item instanceof PayloadConceptPath) {

            PayloadDataField payloadDataField =
                    ((PayloadConceptPath) item).getPayloadDataField();

            if (payloadDataField != null) {

                return payloadDataField.isCorrelation();
            }

        } else if (item instanceof ConceptPath) {

            ConceptPath conceptPath = (ConceptPath) item;
            Object item2 = conceptPath.getItem();

            if (item2 instanceof ProcessRelevantData) {

                if (item2 instanceof DataField) {

                    if (((DataField) item2).isCorrelation()) {

                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 
     * @param item
     * @return the name of the passed item.
     */
    private static String getName(Object item) {
        String name = null;
        if (item instanceof NamedElement) {

            name = ((NamedElement) item).getName();
        } else if (item instanceof ConceptPath) {

            name = ((ConceptPath) item).getPath();
        } else if (item instanceof PayloadConceptPath) {

            PayloadDataField payloadDataField =
                    ((PayloadConceptPath) item).getPayloadDataField();

            if (payloadDataField != null) {

                DataField dataField = payloadDataField;
                if (dataField != null) {

                    name = dataField.getName();
                }
            }
        }
        return name;
    }

    /**
     * 
     * @param ed
     * @param globalSignalEvent
     * @param cmd
     * @return SignalData if already present, else create one and returns it.
     */
    public static SignalData getOrCreateSignalDataElement(EditingDomain ed,
            Activity globalSignalEvent, CompoundCommand cmd) {

        TriggerResultSignal triggerResultSignal =
                (TriggerResultSignal) globalSignalEvent.getEvent()
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

    /**
     * 
     * @param globalThrowSignalEvent
     *            the global throw signal event.
     * @return the Global Throw Signal event mappings.
     */
    public static Object[] getThrowGlobalSignalMappings(
            Activity globalThrowSignalEvent) {

        SignalData signalData =
                EventObjectUtil
                        .getSignalDataExtensionElement(globalThrowSignalEvent);

        if (signalData != null) {

            List<Mapping> resultMappings = new ArrayList<Mapping>();

            List<DataMapping> signalDataAndCorrelationMappings =
                    new ArrayList<DataMapping>();
            /*
             * We need to scan through the normal data mappings as well as
             * correlation data mappings.
             */
            signalDataAndCorrelationMappings.addAll(signalData
                    .getDataMappings());

            if (signalData.getCorrelationMappings() != null) {
                signalDataAndCorrelationMappings.addAll(signalData
                        .getCorrelationMappings().getDataMappings());
            }
            /*
             * first scan and add all Data Mappings
             */
            for (DataMapping xpdlMapping : signalDataAndCorrelationMappings) {
                if (xpdlMapping.getDirection().equals(DirectionType.IN_LITERAL)) {
                    /*
                     * get the target payload first.
                     */
                    String targetPayloadName = xpdlMapping.getFormal();

                    PayloadConceptPath targetPayloadConceptPath = null;

                    if (targetPayloadName != null) {
                        /*
                         * re-create PayloadConceptPath from the target payload,
                         * let its equals method tell if the 2
                         * payloadconceptpaths are equal.
                         */
                        targetPayloadConceptPath =
                                new PayloadConceptPath(globalThrowSignalEvent,
                                        targetPayloadName);

                        /*
                         * get the source
                         */
                        ScriptInformation information =
                                (ScriptInformation) Xpdl2ModelUtil
                                        .getOtherElement(xpdlMapping,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_Script());

                        if (information != null) {
                            /*
                             * if source is a script add it to the mapping list.
                             */
                            resultMappings.add(new Mapping(information,
                                    targetPayloadConceptPath, xpdlMapping));

                        } else {
                            /*
                             * Source is process data.
                             */
                            Expression actual = xpdlMapping.getActual();

                            String sourceName = actual.getText();

                            ConceptPath sourceObj = null;
                            if (globalThrowSignalEvent != null) {

                                sourceObj =
                                        ConceptUtil
                                                .resolveConceptPath(globalThrowSignalEvent,
                                                        sourceName);
                                /*
                                 * Add to mapping list even if source object is
                                 * null so as to show broken mappings.
                                 */
                                resultMappings.add(new Mapping(sourceObj,
                                        targetPayloadConceptPath, xpdlMapping));

                            }
                        }
                    }
                }
            }
            return resultMappings.toArray();
        }
        return new Object[0];
    }

    /**
     * 
     * @param globalCatchSignalEvent
     * @return the Global Catch Signal event mappings.
     */
    public static Object[] getCatchGlobalSignalMappings(
            Activity globalCatchSignalEvent) {

        SignalData signalData =
                EventObjectUtil
                        .getSignalDataExtensionElement(globalCatchSignalEvent);

        if (signalData != null) {

            List<Mapping> resultMappings = new ArrayList<Mapping>();
            List<DataMapping> signalDataAndCorrelationMappings =
                    new ArrayList<DataMapping>();
            /*
             * We need to scan through the normal data mappings as well as
             * correlation data mappings.
             */
            signalDataAndCorrelationMappings.addAll(signalData
                    .getDataMappings());

            if (signalData.getCorrelationMappings() != null) {
                signalDataAndCorrelationMappings.addAll(signalData
                        .getCorrelationMappings().getDataMappings());
            }
            /*
             * first scan and add all Data Mappings
             */
            for (DataMapping xpdlMapping : signalDataAndCorrelationMappings) {

                if (xpdlMapping.getDirection()
                        .equals(DirectionType.OUT_LITERAL)) {

                    /*
                     * Get the target first, thats the process data in scope of
                     * catch global signal event
                     */
                    Expression actual = xpdlMapping.getActual();

                    if (actual != null) {
                        /*
                         * get the target object concept path.
                         */
                        ConceptPath targetObj =
                                ConceptUtil
                                        .resolveConceptPath(globalCatchSignalEvent,
                                                actual.getText());

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
                            String payloadName = xpdlMapping.getFormal();
                            /*
                             * re-create PayloadConceptPath from the target
                             * payload, let its equals method tell if the 2
                             * payloadconceptpaths are equal.
                             */
                            PayloadConceptPath sourcePayloadConceptPath =
                                    new PayloadConceptPath(
                                            globalCatchSignalEvent, payloadName);

                            /*
                             * Add mapping even if the source payload is null
                             * that would create broken mappings.
                             */
                            resultMappings.add(new Mapping(
                                    sourcePayloadConceptPath, targetObj,
                                    xpdlMapping));

                        }
                    }
                }
            }
            return resultMappings.toArray();
        }
        return new Object[0];
    }

    /**
     * Return <code>true</code> if target contributor ID is the same as that of
     * the contributor ID of
     * {@link ProcessCorrelationDataContentContributor},
     * <code>false</code> otherwise.
     * 
     * @param dataMapping
     * @return return <code>true</code> if the target of the specified mapping
     *         is correlation data, <code>false</code> otherwise.
     */
    public static boolean isMappedToCorrelationData(DataMapping dataMapping) {

        Object targetAttributeName =
                Xpdl2ModelUtil.getOtherAttribute(dataMapping,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_TargetContributorId());

        return GlobalSignalDataMapperConstants.GS_CATCH_CORRELATION_DATAMAPPER_CONTENT_CONTRIBUTOR_ID
                .equals(targetAttributeName);
    }
}
