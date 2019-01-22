/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.implementer.nativeservices.java.internal.Messages;
import com.tibco.xpd.implementer.script.ActivityMessageUtil;
import com.tibco.xpd.mapper.MappingDelta;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.AbstractDataMappingCommandFactory;
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
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author nwilson
 */
public class JavaServiceMappingCommandFactory extends
        AbstractDataMappingCommandFactory implements IMappingCommandFactory {

    private MappingDirection direction;

    public JavaServiceMappingCommandFactory(MappingDirection direction) {
        this.direction = direction;
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
        Activity act = (Activity) owner;
        Command command = null;
        Message message = getMessage(act);
        DirectionType dir =
                direction.equals(MappingDirection.IN) ? DirectionType.IN_LITERAL
                        : DirectionType.OUT_LITERAL;
        if (message != null) {
            String targetName;
            targetName =
                    JavaServiceMappingUtil.getScriptName(target, direction);
            CompoundCommand cmd =
                    new CompoundCommand(
                            Messages.JavaServiceMappingCommandFactory_AddMapping);
            String text = ""; //$NON-NLS-1$
            boolean isScriptCopy = false;
            if (source instanceof ScriptInformation) {
                if (((ScriptInformation) source).eContainer() instanceof DataMapping) {
                    isScriptCopy = true;
                }
                if (direction.equals(MappingDirection.IN)) {
                    ScriptInformation information = (ScriptInformation) source;
                    Expression oldExpression = information.getExpression();
                    if (oldExpression != null) {
                        text = oldExpression.getText();
                    }
                } else {
                    text = "_SCRIPT_"; //$NON-NLS-1$
                }
            }
            EObject container = findContainer(act, targetName);
            if (!isScriptCopy) {
                if (container == null) {
                    String grammar =
                            ScriptGrammarFactory.getScriptGrammar(act, dir);
                    if (grammar == null) {
                        grammar =
                                ScriptGrammarFactory
                                        .getDefaultScriptGrammar(act);
                    }
                    ScriptMappingCompositorFactory factory =
                            ScriptMappingCompositorFactory
                                    .getCompositorFactory(grammar,
                                            dir,
                                            JavaServiceMappingUtil.SCRIPT_CONTEXT);
                    if (factory != null) {
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
                                if (parent == null
                                        || parent instanceof Activity) {
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
                            DataMapping mapping =
                                    Xpdl2Factory.eINSTANCE.createDataMapping();
                            container = mapping;
                            mapping.setDirection(dir);
                            if (direction.equals(MappingDirection.IN)) {
                                mapping.setFormal(targetName);
                                mapping.setActual(scriptMapping.getExpression());
                            } else {
                                Expression expression =
                                        Xpdl2ModelUtil
                                                .createExpression(targetName);
                                if (source instanceof ScriptInformation) {
                                    mapping.setFormal(text);
                                } else {
                                    mapping.setFormal(scriptMapping
                                            .getExpression().getText());
                                }
                                expression.setScriptGrammar(grammar);
                                mapping.setActual(expression);
                            }
                            cmd.append(AddCommand.create(ed,
                                    message,
                                    Xpdl2Package.eINSTANCE
                                            .getMessage_DataMappings(),
                                    container));
                        }
                    }
                } else if (container instanceof DataMapping) {
                    DataMapping mapping = (DataMapping) container;
                    String script = DataMappingUtil.getScript(mapping);
                    String grammar = DataMappingUtil.getGrammar(mapping);
                    ScriptMappingCompositorFactory factory =
                            ScriptMappingCompositorFactory
                                    .getCompositorFactory(grammar,
                                            dir,
                                            JavaServiceMappingUtil.SCRIPT_CONTEXT);
                    if (factory != null) {
                        ScriptMappingCompositor compositor =
                                factory.getCompositor(act, targetName, script);
                        if (compositor instanceof SingleMappingCompositor) {
                            SingleMappingCompositor scriptMapping =
                                    (SingleMappingCompositor) compositor;
                            if (source instanceof ScriptInformation) {
                                ScriptInformation information =
                                        (ScriptInformation) source;
                                script = null;
                                Expression expression =
                                        information.getExpression();
                                if (expression != null) {
                                    script = expression.getText();
                                }
                                if (script != null && script.length() != 0) {
                                    scriptMapping.setScript(script);
                                }
                            } else {
                                scriptMapping.addMapping(source, target);
                            }
                            DataMappingUtil
                                    .getSetDataMappingExpressionCommand(ed,
                                            cmd,
                                            mapping,
                                            compositor);
                        }
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
                    if (direction.equals(MappingDirection.IN)
                            && information.getExpression() != null) {
                        cmd.append(SetCommand.create(ed,
                                information,
                                XpdExtensionPackage.eINSTANCE
                                        .getScriptInformation_Expression(),
                                null));
                        // information.setExpression(null);
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
                            String.format(Messages.JavaServiceMappingCommandFactory_CopyOf_message,
                                    i,
                                    information.getName());
                    while (ScriptInformationUtil.scriptNameExists(act,
                            dir,
                            name)) {
                        i++;
                        name =
                                String.format(Messages.JavaServiceMappingCommandFactory_CopyOf_message,
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
                        formal = targetName;
                        actual = script;
                    } else {
                        formal = "_SCRIPT_"; //$NON-NLS-1$
                        actual = targetName;
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
                    cmd.append(AddCommand.create(ed,
                            message,
                            Xpdl2Package.eINSTANCE.getMessage_DataMappings(),
                            mapping));
                }
            }
            command = cmd;
        }
        return command;
    }

    /**
     * @param act
     * @param targetName
     * @return
     */
    private DataMapping findContainer(Activity act, String targetName) {
        DataMapping container = null;
        DirectionType dir =
                direction.equals(MappingDirection.IN) ? DirectionType.IN_LITERAL
                        : DirectionType.OUT_LITERAL;
        Message message = getMessage(act);
        if (message != null) {
            List<?> mappings = message.getDataMappings();
            container =
                    ActivityMessageUtil.findByTargetParameter(mappings,
                            targetName,
                            dir);
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
     * @param activity
     * @param message
     * @param path
     * @return
     */
    private Collection<DataMapping> findContainersBySource(Activity activity,
            Message message, String name) {
        Collection<DataMapping> container = new ArrayList<DataMapping>();
        DirectionType dir =
                direction.equals(MappingDirection.IN) ? DirectionType.IN_LITERAL
                        : DirectionType.OUT_LITERAL;
        if (name != null) {
            for (Object next : message.getDataMappings()) {
                DataMapping mapping = (DataMapping) next;
                if (dir.equals(mapping.getDirection())) {
                    if (!DataMappingUtil.isScripted(mapping)) {
                        String grammar = DataMappingUtil.getGrammar(mapping);
                        String script = DataMappingUtil.getScript(mapping);
                        ScriptMappingCompositorFactory factory =
                                ScriptMappingCompositorFactory
                                        .getCompositorFactory(grammar,
                                                dir,
                                                JavaServiceMappingUtil.SCRIPT_CONTEXT);
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
     * @param ed
     *            The editing domain.
     * @param cmd
     *            The command to add to.
     * @param container
     *            The container to remove.
     */
    private void removeContainer(EditingDomain ed, CompoundCommand cmd,
            EObject container) {
        Object feature = Xpdl2Package.eINSTANCE.getMessage_DataMappings();
        cmd.append(RemoveCommand.create(ed,
                container.eContainer(),
                feature,
                container));
    }

    /**
     * @param ed
     *            The editing domain.
     * @param input
     *            The input object.
     * @param delta
     *            The mapping delta.
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

    private Message getMessage(Activity act) {
        Message message = null;
        Implementation implementation = act.getImplementation();
        if (implementation instanceof Task) {
            TaskService service = ((Task) implementation).getTaskService();
            if (service != null) {
                if (direction.equals(MappingDirection.IN)) {
                    message = service.getMessageIn();
                } else {
                    message = service.getMessageOut();
                }
            }
        }
        return message;
    }
}
