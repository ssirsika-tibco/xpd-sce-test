/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.mappings;

import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.uml2.uml.NamedElement;

import com.tibco.xpd.implementer.resources.xpdl2.Messages;
import com.tibco.xpd.implementer.resources.xpdl2.properties.JavaScriptConceptUtil;
import com.tibco.xpd.implementer.script.ActivityMessageUtil;
import com.tibco.xpd.implementer.script.RestActivityAdapterFactory;
import com.tibco.xpd.implementer.script.RestActivityMessageProvider;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.AbstractDataMappingCommandFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
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
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Command factory used for creating process to/from REST service mapping
 * commands.
 *
 * @author jarciuch
 * @since 2 Apr 2015
 */
public class RestMappingCommandFactory
        extends AbstractDataMappingCommandFactory {

    /*
     * Contest for ScriptMappingCompositorFactory. As used in
     * 'scriptMappingCompositor' extension.
     */
    private final static String REST_CONTEXT = "Rest"; //$NON-NLS-1$

    /** The mapping direction. */
    private MappingDirection createDirection;

    /**
     * Define the direction for these mappings when it is created.
     * 
     * @param direction
     *            The mapping direction.
     */
    public RestMappingCommandFactory(MappingDirection direction) {
        this.createDirection = direction;
    }

    /**
     * {@inheritDoc}
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
        DirectionType dir = createDirection.equals(MappingDirection.IN)
                ? DirectionType.IN_LITERAL
                : DirectionType.OUT_LITERAL;
        if (message != null) {
            CompoundCommand cmd =
                    new CompoundCommand(Messages.addMappingCommand);
            String targetName = getTargetName(target);
            String path = getTargetPath(target);
            String text = ""; //$NON-NLS-1$
            boolean isScriptCopy = false;
            if (source instanceof ScriptInformation) {
                if (((ScriptInformation) source)
                        .eContainer() instanceof DataMapping) {
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
                        grammar = ScriptGrammarFactory
                                .getDefaultScriptGrammar(act);
                    }
                    ScriptMappingCompositorFactory factory =
                            ScriptMappingCompositorFactory.getCompositorFactory(
                                    grammar,
                                    dir,
                                    REST_CONTEXT);
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
                                script = DataMappingUtil
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
                            ScriptMappingCompositorFactory.getCompositorFactory(
                                    grammar,
                                    dir,
                                    REST_CONTEXT);
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
                    information.setName(
                            ScriptInformationUtil.getNextScriptName(act, dir));
                    String grammar = getScriptGrammar(act, dir);
                    if (grammar == null) {
                        grammar = ScriptGrammarFactory
                                .getDefaultScriptGrammar(act);
                    }
                    information.getExpression().setScriptGrammar(grammar);
                    if (container.eContainer() == null) {
                        Xpdl2ModelUtil.addOtherElement(
                                (OtherElementsContainer) container,
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
                    String name = String.format(
                            Messages.ActivityMessageMappingCommandFactory_message,
                            i,
                            information.getName());
                    while (ScriptInformationUtil
                            .scriptNameExists(act, dir, name)) {
                        i++;
                        name = String.format(
                                Messages.ActivityMessageMappingCommandFactory_message,
                                i,
                                information.getName());
                    }
                    String script = DataMappingUtil.getScript(mapping);
                    String grammar = DataMappingUtil.getGrammar(mapping);
                    information = XpdExtensionFactory.eINSTANCE
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

    protected String getTargetPath(Object target) {
        if (target instanceof RestParamTreeItem) {
            return ((RestParamTreeItem) target).getPath();
        } else if (target instanceof ConceptPath) {
            // TODO Change when RestConceptPath is introduced.
            return ((ConceptPath) target).getPath();
        }
        return null;
    }

    public static boolean isDummyMappingSourceOrTarget(
            Object mappingSourceOrTarget) {
        return ((mappingSourceOrTarget instanceof ConceptPath)
                && JavaScriptConceptUtil.isInvalidPartParameter(
                        (ConceptPath) mappingSourceOrTarget));
    }

    protected String getScriptGrammar(Activity act, DirectionType dir) {
        return ScriptGrammarFactory.getScriptGrammar(act, dir);
    }

    /**
     * @param activity
     *            The activity to get the message for.
     * @return The message for the current mapping direction.
     */
    private Message getMessage(Activity activity) {
        Message message;
        RestActivityMessageProvider messageAdapter = RestActivityAdapterFactory
                .getInstance().getMessageProvider(activity);
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

    private DataMapping findContainer(Activity act, Object target,
            String targetName, DirectionType direction) {
        DataMapping container = null;
        if (targetName != null) {
            List<DataMapping> mappings =
                    Xpdl2ModelUtil.getDataMappings(act, direction);
            container = ActivityMessageUtil
                    .findByTargetParameter(mappings, targetName, direction);
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

    private DataMapping findByTargetObject(Activity act,
            List<DataMapping> mappings, Object target,
            DirectionType direction) {
        DataMapping container = null;
        for (DataMapping mapping : mappings) {
            String mappingTarget = DataMappingUtil.getTarget(mapping);
            if (DirectionType.IN_LITERAL.equals(direction)) {
                // FIXME Why was REST mapping looking up an IWsdlPath?
                // IWsdlPath path =
                // WsdlUtil.resolveParameter(act, mappingTarget, true);
                // if (path != null && path.equals(target)) {
                // container = mapping;
                // break;
                // }
            }
        }
        return container;
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
     * {@inheritDoc}
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
     * @param target
     *            The traget object.
     * @return The name of the object.
     */
    protected String getTargetName(Object target) {
        if (target instanceof com.tibco.xpd.xpdl2.NamedElement) {
            return ((com.tibco.xpd.xpdl2.NamedElement) target).getName();
        } else if (target instanceof RestParamTreeItem) {
            return ((RestParamTreeItem) target).getParam().getName();
        } else if (target instanceof ConceptPath) {
            Object item = ((ConceptPath) target).getItem();
            if (item instanceof com.tibco.xpd.xpdl2.NamedElement) {
                return ((com.tibco.xpd.xpdl2.NamedElement) item).getName();
            } else if (item instanceof NamedElement) { // org.eclipse.uml2.uml.NamedElement
                return ((NamedElement) item).getName();
            }
        }
        // TODO Add more cases for concept paths
        return null;
    }
}
