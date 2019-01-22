/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.implementer.script;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.process.js.parser.util.WebServicesScriptTranslator;
import com.tibco.xpd.processeditor.xpdl2.properties.script.MultipleMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositorFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.script.SingleMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * An abstract implementation of {@link BaseActivityMessageProvider} interface providing implementation of the common methods. 
 *
 * @author jarciuch
 * @since 7 Apr 2015
 */
public abstract class BaseAbstractActivityMessageAdapter implements
        BaseActivityMessageProvider {

    /**
     * Search the given input message for references to a set of data.
     * 
     * @param act
     *            Activity in which the message is located.
     * @param message
     *            Message to search.
     * @param dataSet
     *            Data to search for.
     * @return Subset of input data that is referenced by message.
     */
    protected Collection<ProcessRelevantData> getMessageDataReferences(
            Activity act, Message message, Set<ProcessRelevantData> dataSet) {
        Collection<ProcessRelevantData> result =
                new HashSet<ProcessRelevantData>();
        if (message != null) {
            for (Object mappingObj : message.getDataMappings()) {
                DataMapping dataMapping = (DataMapping) mappingObj;
                String target = DataMappingUtil.getTarget(dataMapping);
                String script = DataMappingUtil.getScript(dataMapping);
                String grammar = DataMappingUtil.getGrammar(dataMapping);
                if (target != null && script != null && grammar != null) {
                    ScriptMappingCompositorFactory factory =
                            ScriptMappingCompositorFactory
                                    .getCompositorFactory(grammar,
                                            dataMapping.getDirection());
                    if (factory != null) {
                        ScriptMappingCompositor compositor =
                                factory.getCompositor(act, target, script);
                        if (compositor instanceof MultipleMappingCompositor) {
                            MultipleMappingCompositor multiple =
                                    (MultipleMappingCompositor) compositor;
                            Collection<Object> paths =
                                    multiple.getTargetItems();
                            for (Object path : paths) {
                                Collection<Object> allRefs =
                                        multiple.getSourceItems(path);
                                Collection<ProcessRelevantData> workCopy =
                                        new HashSet<ProcessRelevantData>(
                                                dataSet);
                                workCopy.retainAll(allRefs);
                                result.addAll(workCopy);
                            }
                        } else if (compositor instanceof SingleMappingCompositor) {
                            SingleMappingCompositor single =
                                    (SingleMappingCompositor) compositor;
                            Collection<Object> allRefs =
                                    single.getSourceItems(DirectionType.IN_LITERAL
                                            .equals(dataMapping.getDirection()));
                            Collection<ProcessRelevantData> workCopy =
                                    new HashSet<ProcessRelevantData>(dataSet);
                            workCopy.retainAll(allRefs);
                            result.addAll(workCopy);
                        }
                    }
                }
            }
        }
        return result;
    }

    @Override
    public Set<ProcessRelevantData> getDataReferences(Activity act,
            Set<ProcessRelevantData> dataSet) {
        Set<ProcessRelevantData> result = new HashSet<ProcessRelevantData>();
        if (hasMappingIn(act)) {
            result.addAll(getMessageDataReferences(act,
                    getMessageIn(act),
                    dataSet));
        }
        if (hasMappingOut(act)) {
            result.addAll(getMessageDataReferences(act,
                    getMessageOut(act),
                    dataSet));
        }
        return result;
    }

    @Override
    public Command getSwapDataIdReferencesCommand(EditingDomain editingDomain,
            Activity act, Map<String, String> idMap) {
        // No references by id within the mappings
        return null;
    }

    @Override
    public Command getSwapDataNameReferencesCommand(
            EditingDomain editingDomain, Activity act,
            Map<String, String> nameMap) {
        CompoundCommand ccmd =
                new CompoundCommand(
                        Messages.AbstractActivityMessageAdapter_changeReferences);
        if (hasMappingIn(act)) {
            Message message = getMessageIn(act);
            appendSwapDataNameInMessageCommands(editingDomain,
                    act,
                    message,
                    nameMap,
                    ccmd);
        }
        if (hasMappingOut(act)) {
            Message message = getMessageOut(act);
            appendSwapDataNameInMessageCommands(editingDomain,
                    act,
                    message,
                    nameMap,
                    ccmd);
        }
        return ccmd.getCommandList().size() > 0 ? ccmd : null;
    }

    @Override
    public Command getDeleteDataReferencesCommand(EditingDomain editingDomain,
            Activity act, ProcessRelevantData data) {
        CompoundCommand ccmd =
                new CompoundCommand(
                        Messages.AbstractActivityMessageAdapter_removeReferences);
        if (hasMappingIn(act)) {
            Message message = getMessageIn(act);
            appendDeleteDataReferenceMessageCommands(editingDomain,
                    act,
                    message,
                    data,
                    ccmd);
        }
        if (hasMappingOut(act)) {
            Message message = getMessageOut(act);
            appendDeleteDataReferenceMessageCommands(editingDomain,
                    act,
                    message,
                    data,
                    ccmd);
        }
        return ccmd.getCommandList().size() > 0 ? ccmd : null;
    }

    protected void appendDeleteDataReferenceMessageCommands(
            EditingDomain editingDomain, Activity act, Message message,
            ProcessRelevantData data, CompoundCommand ccmd) {
        for (Object mappingObj : message.getDataMappings()) {
            DataMapping dataMapping = (DataMapping) mappingObj;
            String target = DataMappingUtil.getTarget(dataMapping);
            String script = DataMappingUtil.getScript(dataMapping);
            String grammar = DataMappingUtil.getGrammar(dataMapping);
            if (target != null && script != null && grammar != null) {
                ScriptMappingCompositorFactory factory =
                        ScriptMappingCompositorFactory
                                .getCompositorFactory(grammar,
                                        dataMapping.getDirection());
                if (factory != null) {
                    ScriptMappingCompositor compositor =
                            factory.getCompositor(act, target, script);
                    compositor.removeAllMappingsFrom(data);
                    DataMappingUtil
                            .getSetDataMappingExpressionCommand(editingDomain,
                                    ccmd,
                                    dataMapping,
                                    compositor);
                }
            }
        }

    }

    /**
     * @param editingDomain
     * @param act
     * @param message
     * @param nameMap
     * @param ccmd
     */
    protected void appendSwapDataNameInMessageCommands(
            EditingDomain editingDomain, Activity act, Message message,
            Map<String, String> nameMap, CompoundCommand ccmd) {
        for (Object mappingObj : message.getDataMappings()) {
            DataMapping dataMapping = (DataMapping) mappingObj;
            String target = DataMappingUtil.getTarget(dataMapping);
            String script = DataMappingUtil.getScript(dataMapping);
            String grammar = DataMappingUtil.getGrammar(dataMapping);
            if (target != null && script != null && grammar != null) {
                ScriptMappingCompositorFactory factory =
                        ScriptMappingCompositorFactory
                                .getCompositorFactory(grammar,
                                        dataMapping.getDirection());
                if (factory != null) {
                    ScriptMappingCompositor compositor =
                            factory.getCompositor(act, target, script);
                    if (compositor instanceof MultipleMappingCompositor) {
                        MultipleMappingCompositor multiple =
                                (MultipleMappingCompositor) compositor;
                        Collection<Object> paths = multiple.getTargetItems();
                        for (Object path : paths) {
                            String oldScriptText = multiple.getScript(path);
                            String newScriptText =
                                    WebServicesScriptTranslator
                                            .getTranslatedScript(act
                                                    .getProcess(),
                                                    oldScriptText,
                                                    nameMap,
                                                    ProcessJsConsts.SCRIPT_TASK);
                            if (newScriptText != null) {
                                multiple.setScript(path, newScriptText);
                            }
                        }
                    } else if (compositor instanceof SingleMappingCompositor) {
                        SingleMappingCompositor single =
                                (SingleMappingCompositor) compositor;
                        String oldScriptText = single.getScript();
                        String newScriptText =
                                WebServicesScriptTranslator
                                        .getTranslatedScript(act.getProcess(),
                                                oldScriptText,
                                                nameMap,
                                                ProcessJsConsts.SCRIPT_TASK);
                        if (newScriptText != null) {
                            single.setScript(newScriptText);
                        }
                    }
                    DataMappingUtil
                            .getSetDataMappingExpressionCommand(editingDomain,
                                    ccmd,
                                    dataMapping,
                                    compositor);
                }
            }
        }

    }

    /**
     * @return
     */
    protected Command getSetGrammarCommand(Activity activity, String grammar,
            DirectionType dir) {
        if (grammar == null) {
            return null;
        }
        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.TaskServiceMessageAdapter_getSetGrammerCommand);
        if (activity != null) {
            EditingDomain ed = WorkingCopyUtil.getEditingDomain(activity);
            List<?> others =
                    Xpdl2ModelUtil.getOtherElementList(activity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_Script());
            for (Object other : others) {
                if (other instanceof ScriptInformation) {
                    ScriptInformation information = (ScriptInformation) other;
                    if (dir.equals(information.getDirection())) {

                        Expression expression = information.getExpression();
                        if (expression != null) {
                            String current = expression.getScriptGrammar();
                            if (!grammar.equals(current)) {
                                expression =
                                        Xpdl2ModelUtil.createExpression(""); //$NON-NLS-1$
                                expression.setScriptGrammar(grammar);
                                Object feature =
                                        XpdExtensionPackage.eINSTANCE
                                                .getScriptInformation_Expression();
                                cmd.append(SetCommand.create(ed,
                                        information,
                                        feature,
                                        expression));
                            }
                        }
                    }
                }
            }
        }
        return cmd;
    }

}