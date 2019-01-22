/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2.properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.implementer.resources.xpdl2.Messages;
import com.tibco.xpd.implementer.script.ActivityMessageProvider;
import com.tibco.xpd.implementer.script.ActivityMessageProviderFactory;
import com.tibco.xpd.implementer.script.ActivityMessageUtil;
import com.tibco.xpd.implementer.script.IWsdlPath;
import com.tibco.xpd.implementer.script.WsdlPartProblemPath;
import com.tibco.xpd.implementer.script.WsdlUtil;
import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.mapper.MappingDelta;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.AbstractDataMappingCommandFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.IMappingCommandFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositorFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.script.SingleMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ScriptInformationUtil;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * A factory for commands to create or remove mappings through an
 * {@link ActivityMessageProvider}.
 * 
 */
public class ActivityMessageMappingCommandFactory extends
        AbstractDataMappingCommandFactory implements IMappingCommandFactory {

    /** The mapping direction. */
    private MappingDirection createDirection;

    /**
     * Define the direction for these mappings when it is created.
     * 
     * @param direction
     *            The mapping direction.
     */
    public ActivityMessageMappingCommandFactory(MappingDirection direction) {
        super();
        this.createDirection = direction;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractDataMappingCommandFactory#getUnmappedScriptContainerObject(org.eclipse.emf.ecore.EObject,
     *      com.tibco.xpd.xpdl2.DataMapping)
     * 
     * @param owner
     * @param dataMapping
     * @return
     */
    @Override
    protected OtherElementsContainer getUnmappedScriptContainerObject(
            EObject owner, DataMapping dataMapping) {
        if (owner instanceof Activity) {
            return (Activity) owner;
        }
        return null;
    }

    /**
     * @param ed
     *            The editing domain.
     * @param owner
     *            The data mapping owner.
     * @param source
     *            The source object.
     * @param target
     *            The target object.
     * @param dragDirection
     *            The drag direction.
     * @return The command to create the mapping.
     * @see com.tibco.xpd.processeditor.xpdl2.properties.IMappingCommandFactory#getAddMappingCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, java.lang.Object, java.lang.Object)
     */
    @Override
    public Command getAddMappingCommand(EditingDomain ed, EObject owner,
            Object source, Object target) {

        /*
         * If source or target is a dummy formal parameter (created when BOM is
         * not generated) then don't return a command.
         */
        if (isDummyMappingSourceOrTarget(source)
                || isDummyMappingSourceOrTarget(target)) {
            return null;
        }

        Activity act = (Activity) owner;
        Command command = null;
        Message message = getMessage(act);
        DirectionType dir =
                createDirection.equals(MappingDirection.IN) ? DirectionType.IN_LITERAL
                        : DirectionType.OUT_LITERAL;
        if (message != null) {
            CompoundCommand cmd =
                    new CompoundCommand(Messages.addMappingCommand);
            String targetName = ActivityMessageUtil.getParameterName(target);
            String path = ActivityMessageUtil.getPath(target);
            String text = ""; //$NON-NLS-1$
            boolean isScriptCopy = false;
            if (source instanceof ScriptInformation) {
                if (((ScriptInformation) source).eContainer() instanceof DataMapping) {
                    isScriptCopy = true;
                }
                if (createDirection.equals(MappingDirection.IN)) {
                    ScriptInformation information = (ScriptInformation) source;
                    Expression oldExpression = information.getExpression();
                    if (oldExpression != null) {
                        text = oldExpression.getText();
                    }
                } else {
                    text = "_SCRIPT_"; //$NON-NLS-1$
                }
            }
            EObject container = findContainer(act, target, path, dir);
            if (!isScriptCopy) {
                if (container == null) {
                    String grammar = getScriptGrammar(act, dir);
                    if (grammar == null) {
                        grammar =
                                ScriptGrammarFactory
                                        .getDefaultScriptGrammar(act);
                    }
                    ScriptMappingCompositorFactory factory =
                            ScriptMappingCompositorFactory
                                    .getCompositorFactory(grammar, dir);
                    ScriptMappingCompositor compositor =
                            factory.getCompositor(act, targetName);
                    if (compositor instanceof SingleMappingCompositor) {
                        SingleMappingCompositor scriptMapping =
                                (SingleMappingCompositor) compositor;

                        if (source instanceof ScriptInformation) {
                            ScriptInformation information =
                                    (ScriptInformation) source;
                            String script = null;
                            EObject parent = information.eContainer();
                            if (parent == null || parent instanceof Activity) {
                                Expression expression =
                                        information.getExpression();
                                if (expression != null) {
                                    script = expression.getText();
                                }
                            } else if (parent instanceof DataMapping) {
                                script =
                                        DataMappingUtil
                                                .getScript((DataMapping) parent);
                            }
                            scriptMapping.setScript(script);
                        } else {
                            scriptMapping.addMapping(source, target);
                        }
                        // If activity has generated Web Operations, and the
                        // target is co-relation data.
                        DataMapping mapping =
                                Xpdl2Factory.eINSTANCE.createDataMapping();
                        container = mapping;
                        mapping.setDirection(dir);
                        if (createDirection.equals(MappingDirection.IN)) {
                            mapping.setFormal(path);
                            mapping.setActual(scriptMapping.getExpression());
                        } else {
                            Expression expression =
                                    Xpdl2ModelUtil.createExpression(path);
                            if (source instanceof ScriptInformation) {
                                mapping.setFormal(text);
                            } else {
                                mapping.setFormal(scriptMapping.getExpression()
                                        .getText());
                            }
                            expression.setScriptGrammar(grammar);
                            mapping.setActual(expression);
                        }
                        appendAddMappingCommand(ed,
                                cmd,
                                message,
                                mapping,
                                source,
                                target);
                    }
                } else if (container instanceof DataMapping) {
                    DataMapping mapping = (DataMapping) container;
                    String script = DataMappingUtil.getScript(mapping);
                    String grammar = DataMappingUtil.getGrammar(mapping);
                    ScriptMappingCompositorFactory factory =
                            ScriptMappingCompositorFactory
                                    .getCompositorFactory(grammar, dir);
                    ScriptMappingCompositor compositor =
                            factory.getCompositor(act, targetName, script);
                    if (compositor instanceof SingleMappingCompositor) {
                        SingleMappingCompositor scriptMapping =
                                (SingleMappingCompositor) compositor;
                        if (source instanceof ScriptInformation) {
                            ScriptInformation information =
                                    (ScriptInformation) source;
                            script = null;
                            Expression expression = information.getExpression();
                            if (expression != null) {
                                script = expression.getText();
                            }
                            if (script != null && script.length() != 0) {
                                scriptMapping.setScript(script);
                            }
                        } else {
                            scriptMapping.addMapping(source, target);
                        }
                        DataMappingUtil.getSetDataMappingExpressionCommand(ed,
                                cmd,
                                mapping,
                                compositor);
                    }
                }
            }
            if (source instanceof ScriptInformation) {
                ScriptInformation information = (ScriptInformation) source;
                EObject informationContainer = information.eContainer();
                if (informationContainer == null) {
                    information.setActivity(null);
                    information.setName(ScriptInformationUtil
                            .getNextScriptName(act, dir));
                    String grammar = getScriptGrammar(act, dir);
                    if (grammar == null) {
                        grammar =
                                ScriptGrammarFactory
                                        .getDefaultScriptGrammar(act);
                    }
                    information.getExpression().setScriptGrammar(grammar);
                    if (container.eContainer() == null) {
                        Xpdl2ModelUtil
                                .addOtherElement((OtherElementsContainer) container,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_Script(),
                                        information);
                    } else {
                        cmd.append(Xpdl2ModelUtil.getAddOtherElementCommand(ed,
                                (OtherElementsContainer) container,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_Script(),
                                information));
                    }
                } else if (informationContainer instanceof Activity) {
                    if (createDirection.equals(MappingDirection.IN)
                            && information.getExpression() != null) {
                        cmd.append(SetCommand.create(ed,
                                information,
                                XpdExtensionPackage.eINSTANCE
                                        .getScriptInformation_Expression(),
                                null));
                    }
                    cmd.append(Xpdl2ModelUtil.getAddOtherElementCommand(ed,
                            (OtherElementsContainer) container,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_Script(),
                            information));
                } else if (informationContainer instanceof DataMapping) {
                    DataMapping mapping = (DataMapping) informationContainer;
                    int i = 1;
                    String name =
                            String.format(Messages.ActivityMessageMappingCommandFactory_message,
                                    i,
                                    information.getName());
                    while (ScriptInformationUtil.scriptNameExists(act,
                            dir,
                            name)) {
                        i++;
                        name =
                                String.format(Messages.ActivityMessageMappingCommandFactory_message,
                                        i,
                                        information.getName());
                    }
                    String script = DataMappingUtil.getScript(mapping);
                    String grammar = DataMappingUtil.getGrammar(mapping);
                    information =
                            XpdExtensionFactory.eINSTANCE
                                    .createScriptInformation();
                    information.setName(name);
                    information.setDirection(dir);
                    String formal;
                    String actual;
                    if (DirectionType.IN_LITERAL.equals(dir)) {
                        formal = path;
                        actual = script;
                    } else {
                        formal = "_SCRIPT_"; //$NON-NLS-1$
                        actual = path;
                        Expression scriptExpression =
                                Xpdl2ModelUtil.createExpression(script);
                        scriptExpression.setScriptGrammar(grammar);
                        information.setExpression(scriptExpression);
                    }
                    mapping = Xpdl2Factory.eINSTANCE.createDataMapping();
                    Xpdl2ModelUtil.addOtherElement(mapping,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_Script(),
                            information);
                    Expression exp = Xpdl2ModelUtil.createExpression(actual);
                    exp.setScriptGrammar(grammar);
                    mapping.setActual(exp);
                    mapping.setDirection(dir);
                    mapping.setFormal(formal);
                    appendAddMappingCommand(ed,
                            cmd,
                            message,
                            mapping,
                            source,
                            target);
                }
            }
            command = cmd;
        }
        return command;
    }

    /**
     * @param act
     * @param dir
     * @return the script grammar to use.
     */
    protected String getScriptGrammar(Activity act, DirectionType dir) {
        return ScriptGrammarFactory.getScriptGrammar(act, dir);
    }

    /**
     * @param mappingSourceOrTarget
     * @return <code>true</code> If source or target is a dummy formal parameter
     *         (created when BOM is not generated) then don't return a command.
     */
    public static boolean isDummyMappingSourceOrTarget(
            Object mappingSourceOrTarget) {
        return ((mappingSourceOrTarget instanceof ConceptPath) && JavaScriptConceptUtil
                .isInvalidPartParameter((ConceptPath) mappingSourceOrTarget))
                || (mappingSourceOrTarget instanceof WsdlPartProblemPath);
    }

    /**
     * Append the command for adding the given data mapping to the data mapping
     * container to the given compound command
     * 
     * @param ed
     * @param cmd
     * @param message
     * @param mapping
     * @param source
     *            The original source tree content object used to create the
     *            mapping.
     * @param target
     *            The original target tree content object used to create the
     *            mapping.
     */
    protected void appendAddMappingCommand(EditingDomain ed,
            CompoundCommand cmd, Message message, DataMapping mapping,
            Object source, Object target) {
        cmd.append(AddCommand.create(ed,
                message,
                Xpdl2Package.eINSTANCE.getMessage_DataMappings(),
                mapping));
    }

    /**
     * @param act
     * @param targetName
     * @return
     */
    private DataMapping findContainer(Activity act, Object target,
            String targetName, DirectionType direction) {
        DataMapping container = null;
        if (targetName != null) {
            List<DataMapping> mappings =
                    Xpdl2ModelUtil.getDataMappings(act, direction);
            container =
                    ActivityMessageUtil.findByTargetParameter(mappings,
                            targetName,
                            direction);
            // The following code is used to find target paths prior to version
            // 3.0.1
            // For nested arrays the path string may have change from 3.0.0 to
            // 3.0.1 and so the code above will not find the target.
            if (container == null) {
                container =
                        findByTargetObject(act, mappings, target, direction);
            }
        }
        return container;
    }

    /**
     * @param mappings
     * @param target
     * @param direction
     * @return
     */
    private DataMapping findByTargetObject(Activity act,
            List<DataMapping> mappings, Object target, DirectionType direction) {
        DataMapping container = null;
        for (DataMapping mapping : mappings) {
            String mappingTarget = DataMappingUtil.getTarget(mapping);
            if (DirectionType.IN_LITERAL.equals(direction)) {
                IWsdlPath path =
                        WsdlUtil.resolveParameter(act, mappingTarget, true);
                if (path != null && path.equals(target)) {
                    container = mapping;
                    break;
                }
            }
        }
        return container;
    }

    /**
     * @param subFlow
     * @param dataField
     * @return
     */
    private Collection<DataMapping> findContainersBySource(Activity activity,
            Message message, ConceptPath source) {
        Collection<DataMapping> container = new ArrayList<DataMapping>();
        DirectionType dir =
                createDirection.equals(MappingDirection.IN) ? DirectionType.IN_LITERAL
                        : DirectionType.OUT_LITERAL;
        String name = source.getPath();
        if (name != null) {
            for (DataMapping mapping : Xpdl2ModelUtil.getDataMappings(activity,
                    dir)) {
                if (dir.equals(mapping.getDirection())) {
                    if (!DataMappingUtil.isScripted(mapping)) {
                        String grammar = DataMappingUtil.getGrammar(mapping);
                        String script = DataMappingUtil.getScript(mapping);
                        ScriptMappingCompositorFactory factory =
                                ScriptMappingCompositorFactory
                                        .getCompositorFactory(grammar, dir);
                        if (factory != null) {
                            ScriptMappingCompositor compositor =
                                    factory.getCompositor(activity,
                                            null,
                                            script);
                            if (compositor instanceof SingleMappingCompositor) {
                                SingleMappingCompositor scriptMapping =
                                        (SingleMappingCompositor) compositor;
                                Collection<String> names =
                                        scriptMapping.getSourceItemNames();
                                if (names.contains(name)) {
                                    container.add(mapping);
                                }
                            }
                        }
                    }
                }
            }
        }
        return container;
    }

    /**
     * @param subFlow
     * @param dataField
     * @return
     */
    private Collection<DataMapping> findContainersBySource(Activity activity,
            Message message, IWsdlPath source) {
        Collection<DataMapping> container = new ArrayList<DataMapping>();
        DirectionType dir =
                createDirection.equals(MappingDirection.IN) ? DirectionType.IN_LITERAL
                        : DirectionType.OUT_LITERAL;
        for (DataMapping mapping : Xpdl2ModelUtil
                .getDataMappings(activity, dir)) {
            if (dir.equals(mapping.getDirection())) {
                if (!DataMappingUtil.isScripted(mapping)) {
                    String grammar = DataMappingUtil.getGrammar(mapping);
                    String script = DataMappingUtil.getScript(mapping);
                    ScriptMappingCompositorFactory factory =
                            ScriptMappingCompositorFactory
                                    .getCompositorFactory(grammar, dir);
                    if (factory != null) {
                        ScriptMappingCompositor compositor =
                                factory.getCompositor(activity, null, script);
                        if (compositor instanceof SingleMappingCompositor) {
                            SingleMappingCompositor scriptMapping =
                                    (SingleMappingCompositor) compositor;
                            Collection<Object> items =
                                    scriptMapping
                                            .getSourceItems(DirectionType.IN_LITERAL
                                                    .equals(dir));
                            if (items.contains(source)) {
                                container.add(mapping);
                            }
                        }
                    }
                }
            }
        }
        return container;
    }

    /**
     * @param ed
     *            The editing domain.
     * @param owner
     *            The data mapping owner.
     * @param source
     *            The source object.
     * @param target
     *            The target object.
     * @param dragDirection
     *            The drag direction.
     * @return The command to create the mapping.
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties
     *      .MappingCommandFactory#getRemoveMappingCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, java.lang.Object, java.lang.Object)
     */
    @Override
    public Command getRemoveMappingCommand(EditingDomain ed, EObject owner,
            Object source, Object target) {
        // XPD-4275:Interface ImappingCommandFactory is deprecated and the new
        // interface IMappingCommandFactory2 and its methods will be used, hence
        // this method is no more required to do anything.
        return UnexecutableCommand.INSTANCE;
    }

    /**
     * Remove the given data mapping.
     * 
     * @param ed
     *            The editing domain.
     * @param cmd
     *            The command to add to.
     * @param message
     * @param dataMapping
     *            The container to remove.
     */
    protected void appendRemoveDataMappingCommand(EditingDomain ed,
            CompoundCommand cmd, Message message, EObject dataMapping) {
        cmd.append(RemoveCommand.create(ed, dataMapping));
    }

    /**
     * @param ed
     *            The editing domain.
     * @param owner
     *            The input object.
     * @param changes
     *            The mapping changes.
     * @return The command to make the changes.
     * @see com.tibco.xpd.processeditor.xpdl2.properties.IMappingCommandFactory#getChangeMappingCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, com.tibco.xpd.mapper.MappingDelta)
     * @deprecated This method is never called via the Mapper which always does
     *             remove and re-add
     */
    @Override
    @Deprecated
    public Command getChangeMappingCommand(EditingDomain ed, EObject owner,
            Collection<MappingDelta> changes) {
        return UnexecutableCommand.INSTANCE;
    }

    /**
     * @param activitiy
     *            The activity.
     * @param targetToScript
     * @param mapping
     */
    private void addInputMapping(Activity activity,
            Map<String, ScriptMappingCompositor> targetToScript, Mapping mapping) {
        Object source = mapping.getSource();
        Object target = mapping.getTarget();
        if (source instanceof ProcessRelevantData
                && target instanceof IWsdlPath) {
            ProcessRelevantData data = (ProcessRelevantData) source;
            IWsdlPath path = (IWsdlPath) target;
            String targetName = ActivityMessageUtil.getParameterName(path);
            ScriptMappingCompositor script = targetToScript.get(targetName);
            if (script == null) {
                script = getJavaScriptInputMapping(activity, targetName);
                targetToScript.put(targetName, script);
            }
            script.addMapping(data, path);
        }
    }

    /**
     * @param activitiy
     *            The activity.
     * @param targetToScript
     * @param mapping
     */
    private void removeInputMapping(Activity activity,
            Map<String, ScriptMappingCompositor> targetToScript, Mapping mapping) {
        Object source = mapping.getSource();
        Object target = mapping.getTarget();
        if (source instanceof ProcessRelevantData
                && target instanceof IWsdlPath) {
            ProcessRelevantData data = (ProcessRelevantData) source;
            IWsdlPath path = (IWsdlPath) target;
            String targetName = ActivityMessageUtil.getParameterName(path);
            ScriptMappingCompositor script = targetToScript.get(targetName);
            if (script == null) {
                script = getJavaScriptInputMapping(activity, targetName);
                targetToScript.put(targetName, script);
            }
            script.removeMapping(data, path);
        }
    }

    /**
     * @param activity
     * @param targetName
     * @return
     */
    private ScriptMappingCompositor getJavaScriptInputMapping(
            Activity activity, String targetName) {
        ScriptMappingCompositor script = null;
        Message message = getMessage(activity);
        DirectionType dir =
                createDirection.equals(MappingDirection.IN) ? DirectionType.IN_LITERAL
                        : DirectionType.OUT_LITERAL;
        if (message != null) {
            List<?> mappings = message.getDataMappings();
            DataMapping mapping =
                    ActivityMessageUtil.findByTargetParameter(mappings,
                            targetName,
                            dir);
            if (mapping != null) {
                Expression expression = mapping.getActual();
                if (expression != null) {
                    ScriptMappingCompositorFactory factory =
                            ScriptMappingCompositorFactory
                                    .getCompositorFactory(expression
                                            .getScriptGrammar(),
                                            DirectionType.IN_LITERAL);
                    if (factory != null) {
                        script =
                                factory.getCompositor(activity,
                                        targetName,
                                        expression.getText());
                    }
                }
            }
        }
        if (script == null) {
            script = new JavaScriptProcessToWsdlMapping(activity, targetName);
        }
        return script;
    }

    /**
     * @param activity
     *            The activity to get the message for.
     * @return The message for the current mapping direction.
     */
    private Message getMessage(Activity activity) {
        Message message;
        ActivityMessageProvider messageAdapter = getMessageAdapter(activity);
        if (createDirection.equals(MappingDirection.IN)) {
            message = messageAdapter.getMessageIn(activity);
            if (message == null) {
                message = messageAdapter.getMessageOut(activity);
            }
        } else {
            message = messageAdapter.getMessageOut(activity);
        }
        return message;
    }

    /**
     * @param activity
     *            The activity.
     * @return The message adapter for the activity.
     */
    private ActivityMessageProvider getMessageAdapter(Activity activity) {
        return ActivityMessageProviderFactory.INSTANCE
                .getMessageProvider(activity);
    }

}
