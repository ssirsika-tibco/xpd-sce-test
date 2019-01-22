/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositorFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.script.SingleMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ScriptInformationUtil;
import com.tibco.xpd.processeditor.xpdl2.util.SubProcUtil;
import com.tibco.xpd.xpdExtension.InitialParameterValue;
import com.tibco.xpd.xpdExtension.InitialValues;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Length;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Utility methods for some standard mapper content/command providers etc
 * providers to for manipulating xpdl2 mappings.
 * 
 * @author aallway
 * @since 3.2
 */
public class StandardMappingUtil {

    /**
     * Formal parameter representing the "[Error Code]" ($ERRORCODE in model)
     * special parameter for catch error event.
     */
    public static final FormalParameter CATCH_ERRORCODE_FORMALPARAMETER;

    /**
     * Formal parameter representing the "[Error Detail]" ($ERRORDETAIL in
     * model) special parameter for catch error event.
     */
    public static final FormalParameter CATCH_ERRORDETAIL_FORMALPARAMETER;

    /**
     * Formal parameter representing "[Process Id]" in source data for
     * Reply-Immediate Start Event/Asynchronous Call Sub-Process
     * "Output Process Id" mappings.
     */
    public static final FormalParameter REPLY_IMMEDIATE_PROCESS_ID_FORMALPARAMETER;

    /** The WSDL part name to use for generated WSDL process Id. */
    public static final String REPLY_IMMEDIATE_PROCESS_PART_NAME = "ProcessId"; //$NON-NLS-1$

    /**
     * Reply immediate parameter length.
     */
    private static final int REPLY_IMMEDIATE_PROCESS_ID_FORMALPARAMETER_LENGTH =
            32;

    static {

        CATCH_ERRORCODE_FORMALPARAMETER =
                createDummyMappingParameter("$ERRORCODE", //$NON-NLS-1$
                        Messages.CatchErrorMapperErrCodeSourceParam_ErrorCodeParam_label,
                        BasicTypeType.STRING_LITERAL,
                        ModeType.OUT_LITERAL);
        CATCH_ERRORDETAIL_FORMALPARAMETER =
                createDummyMappingParameter("$ERRORDETAIL", //$NON-NLS-1$
                        Messages.CatchErrorMapperErrDetailsSourceParam_ErrorDetailParam_label,
                        BasicTypeType.STRING_LITERAL,
                        ModeType.OUT_LITERAL);

        /*
         * XPD-7429: Saket: Need to set the length of [ProcessId] formal
         * parameter to 32 to avoid size incompatability warnings.
         */
        Length processIdLength = Xpdl2Factory.eINSTANCE.createLength();

        processIdLength.setValue(String
                .valueOf(REPLY_IMMEDIATE_PROCESS_ID_FORMALPARAMETER_LENGTH));

        REPLY_IMMEDIATE_PROCESS_ID_FORMALPARAMETER =
                createDummyMappingParameter(Xpdl2ModelUtil.REPLY_IMMEDIATE_PROCESS_ID_PARAMETER_NAME,
                        Messages.StandardMappingUtil_MappingContentProcessId_label,
                        BasicTypeType.STRING_LITERAL,
                        ModeType.OUT_LITERAL,
                        processIdLength);

    }

    /**
     * Create a dummy formal parameter (not contained in any process) for use as
     * virtual content in mapper content provider.
     * 
     * @param name
     * @param label
     * @param type
     * @param mode
     * 
     * @return The newly generatedf formal parameter.
     */
    public static FormalParameter createDummyMappingParameter(String name,
            String label, BasicTypeType type, ModeType mode) {
        FormalParameter dummyParameter =
                Xpdl2Factory.eINSTANCE.createFormalParameter();
        dummyParameter.setName(name);
        Xpdl2ModelUtil.setOtherAttribute(dummyParameter,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                label);

        BasicType bt = Xpdl2Factory.eINSTANCE.createBasicType();
        bt.setType(type);
        dummyParameter.setDataType(bt);
        dummyParameter.setMode(mode);

        return dummyParameter;
    }

    /**
     * Create a dummy formal parameter (not contained in any process) for use as
     * virtual content in mapper content provider.
     * <p>
     * Note that this method would return a parameter of specified length.
     * <p>
     * 
     * @param name
     * @param label
     * @param type
     * @param mode
     * @param processIdLength
     * 
     * @return The newly generated formal parameter.
     */
    public static FormalParameter createDummyMappingParameter(String name,
            String label, BasicTypeType type, ModeType mode,
            Length processIdLength) {

        FormalParameter dummyParameter =
                createDummyMappingParameter(name, label, type, mode);

        BasicType processIdType =
                (BasicType) BasicTypeConverterFactory.INSTANCE
                        .getBaseType(dummyParameter, true);

        processIdType.setLength(processIdLength);

        return dummyParameter;
    }

    /**
     * Named element comparator.
     */
    public static final Comparator<? super NamedElement> NAMED_ELEMENT_COMPARATOR =
            new Comparator<NamedElement>() {
                @Override
                public int compare(NamedElement o1, NamedElement o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            };

    /**
     * Get the command to add a mapping to the given set of data mappings. The
     * source is excepted to be either a concept path (representing process
     * relevant data) or a mapping-script and the target is also process
     * relevant data.
     * 
     * @param ed
     * @param source
     * @param target
     * @param activity
     * @param subFlow
     * @return command as described above.
     */
    public static Command createMapFromScriptOrProcDataToProcDataCommand(
            EditingDomain ed, Object source, Object target, Activity activity,
            EObject dataMappingsContainer,
            EReference dataMappingsFeatureReference,
            EList<DataMapping> dataMappings, MappingDirection creationDirection) {

        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.SubProcMappingCommandFactory_AddMapping);
        DataMapping container =
                getExistingMappingToTarget(target,
                        dataMappings,
                        creationDirection);

        DirectionType direction =
                creationDirection.equals(MappingDirection.IN) ? DirectionType.IN_LITERAL
                        : DirectionType.OUT_LITERAL;

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
                        copyMappingScript((ScriptInformation) source,
                                activity,
                                direction);
            }

            if (creationDirection.equals(MappingDirection.IN)) {
                ScriptInformation information = (ScriptInformation) source;
                oldExpression = information.getExpression();
                if (oldExpression != null) {
                    text = oldExpression.getText();
                }
            } else {
                text = "_SCRIPT_"; //$NON-NLS-1$
            }

        } else if (source instanceof InitialValue) {
            /*
             * Source is a predefined drop-down choice.
             */
            InitialValue initial = (InitialValue) source;
            text = initial.getValue();

        } else {
            /*
             * SOurce is a concept path
             */
            text = getName(source);
        }

        if (creationDirection.equals(MappingDirection.IN)) {
            String targetName = getName(target);
            if (container == null) {
                DataMapping dataMapping =
                        Xpdl2Factory.eINSTANCE.createDataMapping();
                container = dataMapping;
                Expression expression = Xpdl2ModelUtil.createExpression(text);
                String grammar =
                        ScriptGrammarFactory.getScriptGrammar(activity,
                                DirectionType.IN_LITERAL);
                if (grammar == null) {
                    grammar =
                            ScriptGrammarFactory
                                    .getDefaultScriptGrammar(activity);
                }
                expression.setScriptGrammar(grammar);
                dataMapping.setFormal(targetName);
                dataMapping.setDirection(DirectionType.IN_LITERAL);
                dataMapping.setActual(expression);
                cmd.append(AddCommand.create(ed,
                        dataMappingsContainer,
                        dataMappingsFeatureReference,
                        dataMapping));
                if (source instanceof InitialValue) {
                    Xpdl2ModelUtil.setOtherAttribute(dataMapping,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_InitialValueMapping(),
                            Boolean.TRUE);
                    InitialParameterValue parameter =
                            InitialValue.getInitialParameterValue(activity,
                                    targetName);
                    cmd.append(Xpdl2ModelUtil.getRemoveOtherElementCommand(ed,
                            activity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_InitialParameterValue(),
                            parameter));
                }
            } else {
                Expression actual = container.getActual();
                if (actual != null) {
                    ScriptMappingCompositorFactory factory =
                            ScriptMappingCompositorFactory
                                    .getCompositorFactory(actual
                                            .getScriptGrammar(),
                                            direction,
                                            SubProcUtil.SCRIPT_CONTEXT);
                    if (factory != null) {
                        ScriptMappingCompositor compositor =
                                factory.getCompositor(activity,
                                        targetName,
                                        actual.getText());
                        if (compositor instanceof SingleMappingCompositor) {
                            SingleMappingCompositor scriptMapping =
                                    (SingleMappingCompositor) compositor;
                            scriptMapping.addMapping(source, target);
                            cmd.append(SetCommand.create(ed,
                                    container,
                                    Xpdl2Package.eINSTANCE
                                            .getDataMapping_Actual(),
                                    scriptMapping.getExpression()));
                        }
                    }
                }
            }
        } else {
            String targetName = getName(target);
            if (container == null) {
                DataMapping dataMapping =
                        Xpdl2Factory.eINSTANCE.createDataMapping();
                container = dataMapping;
                Expression expression =
                        Xpdl2ModelUtil.createExpression(targetName);
                String grammar =
                        ScriptGrammarFactory.getScriptGrammar(activity,
                                DirectionType.OUT_LITERAL);
                if (grammar == null) {
                    grammar =
                            ScriptGrammarFactory
                                    .getDefaultScriptGrammar(activity);
                }
                expression.setScriptGrammar(grammar);
                dataMapping.setFormal(text);
                dataMapping.setDirection(DirectionType.OUT_LITERAL);
                dataMapping.setActual(expression);
                cmd.append(AddCommand.create(ed,
                        dataMappingsContainer,
                        dataMappingsFeatureReference,
                        dataMapping));
            } else {
                Object other =
                        Xpdl2ModelUtil.getOtherElement(container,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_Expression());
                String script;
                String grammar;
                if (other instanceof Expression) {
                    Expression expression = (Expression) other;
                    script = expression.getText();
                    grammar = expression.getScriptGrammar();
                } else {
                    script = container.getFormal();
                    grammar =
                            ScriptGrammarFactory.getScriptGrammar(activity,
                                    DirectionType.OUT_LITERAL);
                    if (grammar == null) {
                        grammar =
                                ScriptGrammarFactory
                                        .getDefaultScriptGrammar(activity);
                    }
                }
                Expression actual = container.getActual();
                if (actual != null) {
                    ScriptMappingCompositorFactory factory =
                            ScriptMappingCompositorFactory
                                    .getCompositorFactory(grammar,
                                            direction,
                                            SubProcUtil.SCRIPT_CONTEXT);
                    if (factory != null) {
                        ScriptMappingCompositor compositor =
                                factory.getCompositor(activity,
                                        targetName,
                                        script);
                        if (compositor instanceof SingleMappingCompositor) {
                            SingleMappingCompositor scriptMapping =
                                    (SingleMappingCompositor) compositor;
                            scriptMapping.addMapping(source, target);
                            cmd.append(Xpdl2ModelUtil
                                    .getSetOtherElementCommand(ed,
                                            container,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_Expression(),
                                            scriptMapping.getExpression()));
                            cmd.append(SetCommand.create(ed,
                                    container,
                                    Xpdl2Package.eINSTANCE
                                            .getDataMapping_Formal(),
                                    "_SCRIPT_")); //$NON-NLS-1$
                        }
                    }
                }
            }
        }

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
                            .getNextScriptName(activity, direction));

                    Expression expression = Xpdl2ModelUtil.createExpression(""); //$NON-NLS-1$
                    String grammar =
                            ScriptGrammarFactory.getScriptGrammar(activity,
                                    direction);
                    if (grammar == null) {
                        grammar =
                                ScriptGrammarFactory
                                        .getDefaultScriptGrammar(activity);
                    }
                    expression.setScriptGrammar(grammar);
                    information.setExpression(expression);
                }

                if (container.eContainer() == null) {
                    /*
                     * If it's a brand new data mapping then we can simply put
                     * the script straight in.
                     */
                    Xpdl2ModelUtil.addOtherElement(container,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_Script(),
                            information);
                } else {
                    /*
                     * Otherwise if we're adding a second source to an existing
                     * mapping then we have to ammend the current.
                     */
                    cmd.append(Xpdl2ModelUtil.getAddOtherElementCommand(ed,
                            container,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_Script(),
                            information));
                }
            } else if (informationContainer instanceof Activity) {
                /*
                 * Add existing unmapped script to data mapping added above.
                 */
                if (creationDirection.equals(MappingDirection.IN)
                        && information.getExpression() != null) {
                    cmd.append(SetCommand.create(ed,
                            information,
                            XpdExtensionPackage.eINSTANCE
                                    .getScriptInformation_Expression(),
                            null));
                }
                cmd.append(Xpdl2ModelUtil.getAddOtherElementCommand(ed,
                        container,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_Script(),
                        information));

            }

            /*
             * Changed code for
             * "copy existing mapped script and create new data mapping" so that
             * the data map[ping is created in one place (we just crete a copy
             * of the script before adding datamapping.
             */

        }
        return cmd;
    }

    /**
     * @param script
     * @param activity
     * @param direction
     * @return a copy of the given script, uniquely named.
     */
    public static ScriptInformation copyMappingScript(ScriptInformation script,
            Activity activity, DirectionType direction) {
        ScriptInformation newScript = EcoreUtil.copy(script);

        int i = 0;
        String name;
        do {
            i++;
            name =
                    String.format(Messages.StandardMappingUtil_message,
                            i,
                            newScript.getName());
        } while (ScriptInformationUtil.scriptNameExists(activity,
                direction,
                name));

        newScript.setName(name);
        return newScript;
    }

    /**
     * Get the command to remove a mapping from the given set of data mappings.
     * The source is excepted to be either a concept path (representing process
     * relevant data) or a mapping-script and the target is also process
     * relevant data.
     * 
     * @param ed
     * @param source
     * @param target
     * @param act
     * @param dataMappingsContainer
     * @param dataMappingsFeatureReference
     * @param dataMappings
     * @param mappingDirection
     * @return Command as described above.
     */
    public static Command createDelMapFromScriptOrProcDataToProcDataCommand(
            EditingDomain ed, Object source, Object target, Activity act,
            EObject dataMappingsContainer,
            EReference dataMappingsFeatureReference,
            EList<DataMapping> dataMappings, MappingDirection mappingDirection) {

        ConceptPath dataField;
        ConceptPath formalParam;
        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.MappingCommandFactory_RemoveMapping_menu);
        DirectionType direction;
        if (mappingDirection.equals(MappingDirection.IN)) {
            direction = DirectionType.IN_LITERAL;
            if (target == null) {
                if (source instanceof ConceptPath) {
                    dataField = (ConceptPath) source;
                    Collection<DataMapping> existingDataMappings =
                            getExistingMappingFromSource(act,
                                    dataMappings,
                                    dataField,
                                    mappingDirection);

                    for (DataMapping dataMapping : existingDataMappings) {
                        cmd.append(RemoveCommand.create(ed,
                                dataMappingsContainer,
                                dataMappingsFeatureReference,
                                dataMapping));
                    }

                } else if (source instanceof ScriptInformation) {
                    ScriptInformation information = (ScriptInformation) source;
                    EObject parent = information.eContainer();
                    if (parent instanceof DataMapping) {
                        DataMapping dataMapping = (DataMapping) parent;
                        Expression expression = dataMapping.getActual();
                        if (expression != null) {
                            Expression newExpression =
                                    Xpdl2ModelUtil.createExpression(expression
                                            .getText());
                            newExpression.setScriptGrammar(expression
                                    .getScriptGrammar());
                            cmd.append(SetCommand.create(ed,
                                    information,
                                    XpdExtensionPackage.eINSTANCE
                                            .getScriptInformation_Expression(),
                                    newExpression));
                        }
                        cmd.append(Xpdl2ModelUtil.getAddOtherElementCommand(ed,
                                act,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_Script(),
                                information));
                        cmd.append(RemoveCommand.create(ed,
                                dataMappingsContainer,
                                dataMappingsFeatureReference,
                                dataMapping));
                    }
                }

            } else {
                formalParam = (ConceptPath) target;
                DataMapping dataMapping =
                        getExistingMappingToTarget(formalParam,
                                dataMappings,
                                mappingDirection);
                if (dataMapping != null) {
                    if (source instanceof ScriptInformation) {
                        ScriptInformation information =
                                (ScriptInformation) source;
                        Expression expression = dataMapping.getActual();
                        if (expression != null) {
                            Expression newExpression =
                                    Xpdl2ModelUtil.createExpression(expression
                                            .getText());
                            newExpression.setScriptGrammar(expression
                                    .getScriptGrammar());
                            cmd.append(SetCommand.create(ed,
                                    information,
                                    XpdExtensionPackage.eINSTANCE
                                            .getScriptInformation_Expression(),
                                    newExpression));
                        }
                        cmd.append(Xpdl2ModelUtil.getAddOtherElementCommand(ed,
                                act,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_Script(),
                                information));
                        cmd.append(RemoveCommand.create(ed,
                                dataMappingsContainer,
                                dataMappingsFeatureReference,
                                dataMapping));
                    } else if (source instanceof InitialValue) {
                        String value = DataMappingUtil.getScript(dataMapping);
                        InitialValue initial = (InitialValue) source;
                        cmd.append(RemoveCommand.create(ed,
                                dataMappingsContainer,
                                dataMappingsFeatureReference,
                                dataMapping));
                        InitialParameterValue initialParameter =
                                XpdExtensionFactory.eINSTANCE
                                        .createInitialParameterValue();
                        initialParameter.setName(initial.getFormal().getName());
                        initialParameter.setValue(value);
                        cmd.append(Xpdl2ModelUtil
                                .getAddOtherElementCommand(ed,
                                        act,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_InitialParameterValue(),
                                        initialParameter));
                    } else {
                        Expression actual = dataMapping.getActual();
                        if (actual != null) {
                            ScriptMappingCompositorFactory factory =
                                    ScriptMappingCompositorFactory
                                            .getCompositorFactory(actual
                                                    .getScriptGrammar(),
                                                    direction,
                                                    SubProcUtil.SCRIPT_CONTEXT);
                            if (factory != null) {
                                String targetName = getName(target);
                                ScriptMappingCompositor compositor =
                                        factory.getCompositor(act,
                                                targetName,
                                                actual.getText());
                                if (compositor instanceof SingleMappingCompositor) {
                                    SingleMappingCompositor scriptMapping =
                                            (SingleMappingCompositor) compositor;
                                    scriptMapping.removeMapping(source, target);
                                    if (scriptMapping.containsMappings()) {
                                        cmd.append(SetCommand
                                                .create(ed,
                                                        dataMapping,
                                                        Xpdl2Package.eINSTANCE
                                                                .getDataMapping_Actual(),
                                                        scriptMapping
                                                                .getExpression()));
                                    } else {
                                        cmd.append(RemoveCommand.create(ed,
                                                dataMappingsContainer,
                                                dataMappingsFeatureReference,
                                                dataMapping));
                                    }
                                }
                            }
                        }
                    }

                }
            }
        } else {
            direction = DirectionType.OUT_LITERAL;
            dataField = (ConceptPath) target;
            DataMapping dataMapping =
                    getExistingMappingToTarget(dataField,
                            dataMappings,
                            mappingDirection);

            if (dataMapping != null) {
                if (source instanceof ScriptInformation) {
                    ScriptInformation information = (ScriptInformation) source;
                    cmd.append(Xpdl2ModelUtil.getAddOtherElementCommand(ed,
                            act,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_Script(),
                            information));
                    cmd.append(RemoveCommand.create(ed,
                            dataMappingsContainer,
                            dataMappingsFeatureReference,
                            dataMapping));
                } else {
                    Object other =
                            Xpdl2ModelUtil.getOtherElement(dataMapping,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_Expression());
                    String script;
                    String grammar;
                    if (other instanceof Expression) {
                        Expression expression = (Expression) other;
                        script = expression.getText();
                        grammar = expression.getScriptGrammar();
                    } else {
                        script = dataMapping.getFormal();
                        grammar =
                                ScriptGrammarFactory.getScriptGrammar(act,
                                        DirectionType.OUT_LITERAL);
                        if (grammar == null) {
                            grammar =
                                    ScriptGrammarFactory
                                            .getDefaultScriptGrammar(act);
                        }
                    }
                    ScriptMappingCompositorFactory factory =
                            ScriptMappingCompositorFactory
                                    .getCompositorFactory(grammar,
                                            direction,
                                            SubProcUtil.SCRIPT_CONTEXT);
                    if (factory != null) {
                        String targetName = getName(target);
                        ScriptMappingCompositor compositor =
                                factory.getCompositor(act, targetName, script);
                        if (compositor instanceof SingleMappingCompositor) {
                            SingleMappingCompositor scriptMapping =
                                    (SingleMappingCompositor) compositor;
                            scriptMapping.removeMapping(source, target);
                            int count =
                                    scriptMapping.getSourceItemNames().size();
                            if (count == 0) {
                                cmd.append(RemoveCommand.create(ed,
                                        dataMappingsContainer,
                                        dataMappingsFeatureReference,
                                        dataMapping));
                            } else if (count == 1) {
                                if (other instanceof Expression) {
                                    cmd.append(Xpdl2ModelUtil
                                            .getRemoveOtherElementCommand(ed,
                                                    dataMapping,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getDocumentRoot_Expression(),
                                                    other));
                                }
                                cmd.append(SetCommand
                                        .create(ed,
                                                dataMapping,
                                                Xpdl2Package.eINSTANCE
                                                        .getDataMapping_Formal(),
                                                scriptMapping.getExpression()
                                                        .getText()));
                            } else {
                                Expression expression =
                                        scriptMapping.getExpression();
                                cmd.append(Xpdl2ModelUtil
                                        .getSetOtherElementCommand(ed,
                                                dataMapping,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_Expression(),
                                                expression));
                            }
                        }
                    }
                }

            }
        }

        return cmd;
    }

    /**
     * The existing data mappings that have the given source object as theor
     * source (or within it if it's a composite).
     * 
     * @param target
     * @param activity
     * @return The existing data mappings that have the given source object as
     *         theor source (or within it if it's a composite).
     */
    public static DataMapping getExistingMappingToTarget(Object target,
            EList<DataMapping> dataMappings, MappingDirection creationDirection) {
        DataMapping existingDataMapping = null;
        DirectionType dir =
                creationDirection.equals(MappingDirection.IN) ? DirectionType.IN_LITERAL
                        : DirectionType.OUT_LITERAL;
        String name = getName(target);
        if (name != null) {
            for (Object next : dataMappings) {
                DataMapping mapping = (DataMapping) next;
                if (dir.equals(mapping.getDirection())) {
                    if (dir.equals(DirectionType.IN_LITERAL)) {
                        if (name.equals(mapping.getFormal())) {
                            existingDataMapping = mapping;
                        }
                    } else {
                        Expression actual = mapping.getActual();
                        if (actual != null) {
                            if (name.equals(actual.getText())) {
                                existingDataMapping = mapping;
                            }
                        }
                    }
                }
            }
        }
        return existingDataMapping;
    }

    /**
     * Get the existing data mappings that have the given source object as theor
     * source (or within it if it's a composite).
     * 
     * @param activity
     * @param dataMappings
     * @param source
     * @param creationDirection
     * @return The existing data mappings that have the given source object as
     *         theor source (or within it if it's a composite).
     */
    private static Collection<DataMapping> getExistingMappingFromSource(
            Activity activity, EList<DataMapping> dataMappings,
            ConceptPath source, MappingDirection creationDirection) {
        Collection<DataMapping> container = new ArrayList<DataMapping>();
        DirectionType dir =
                creationDirection.equals(MappingDirection.IN) ? DirectionType.IN_LITERAL
                        : DirectionType.OUT_LITERAL;

        String name = getName(source);
        if (name != null) {
            for (Object next : dataMappings) {
                DataMapping mapping = (DataMapping) next;
                if (dir.equals(mapping.getDirection())) {
                    if (!DataMappingUtil.isScripted(mapping)) {
                        String grammar = DataMappingUtil.getGrammar(mapping);
                        String script = DataMappingUtil.getScript(mapping);
                        ScriptMappingCompositorFactory factory =
                                ScriptMappingCompositorFactory
                                        .getCompositorFactory(grammar,
                                                dir,
                                                SubProcUtil.SCRIPT_CONTEXT);
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
     * Get the mappings content (array of {@link Mapping}'s for when an activity
     * is mapping local process data (+ initial values + scripts) to / from
     * another process's data (depending on given direction). (i.e. used for
     * things like sub-process task mapping where otherProcessData is ther data
     * from the process thats being invoked.
     * 
     * @param otherProcessData
     *            The data that is available for mapping to/from the other
     *            process.
     * @param targetActivity
     *            The activity that the mappings are for.
     * @param xpdlMappings
     *            The data mappings from the
     * 
     * @return Array of Mapping's current defined for the activity.
     */
    public static Object[] getProcessDataToOtherProcessDataMappings(
            List<FormalParameter> otherProcessData, Activity targetActivity,
            List<DataMapping> xpdlMappings, MappingDirection mappingDirection) {

        if (xpdlMappings != null) {
            List<Mapping> result = new ArrayList<Mapping>();

            for (DataMapping xpdlMapping : xpdlMappings) {
                // Get formal parameter
                DirectionType dir;
                if (MappingDirection.IN.equals(mappingDirection)) {
                    dir = DirectionType.IN_LITERAL;
                    if (xpdlMapping.getDirection()
                            .equals(DirectionType.IN_LITERAL)) {
                        String formal = xpdlMapping.getFormal();
                        FormalParameter formalObj = null;
                        NamedElement dummyFormalSearch =
                                new SearchParameter(formal);
                        int formalInd =
                                Collections.binarySearch(otherProcessData,
                                        dummyFormalSearch,
                                        NAMED_ELEMENT_COMPARATOR);
                        if (formalInd >= 0) {
                            formalObj = otherProcessData.get(formalInd);
                        }
                        ConceptPath formalConcept = null;
                        if (formalObj != null) {
                            Class formalClass =
                                    ConceptUtil.getConceptClass(formalObj);
                            formalConcept =
                                    new ConceptPath(formalObj, formalClass);
                        }
                        boolean isInitialValueMapping = false;
                        Object other =
                                Xpdl2ModelUtil
                                        .getOtherAttribute(xpdlMapping,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_InitialValueMapping());
                        if (other != null && other instanceof Boolean) {
                            isInitialValueMapping =
                                    ((Boolean) other).booleanValue();
                        }
                        ScriptInformation information =
                                (ScriptInformation) Xpdl2ModelUtil
                                        .getOtherElement(xpdlMapping,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_Script());
                        if (isInitialValueMapping) {
                            InitialValue initial = null;
                            Expression actual = xpdlMapping.getActual();
                            if (actual != null) {
                                if (information == null) {
                                    String path = actual.getText();
                                    Object initVal =
                                            Xpdl2ModelUtil
                                                    .getOtherElement(formalObj,
                                                            XpdExtensionPackage.eINSTANCE
                                                                    .getDocumentRoot_InitialValues());
                                    if (initVal instanceof InitialValues) {
                                        InitialValues values =
                                                (InitialValues) initVal;
                                        List<?> tokens = values.getValue();
                                        if (tokens.size() > 0) {
                                            if (tokens.contains(path)) {
                                                initial =
                                                        new InitialValue(
                                                                targetActivity,
                                                                formalObj);
                                            }
                                        }
                                    }
                                }
                            }

                            result.add(new Mapping(initial, formalConcept,
                                    xpdlMapping));
                        } else {
                            // Get actual parameter
                            Object actualObj = null;
                            Expression actual = xpdlMapping.getActual();
                            if (actual != null) {
                                if (information == null) {
                                    String path = actual.getText();

                                    String grammar =
                                            DataMappingUtil
                                                    .getGrammar(xpdlMapping);
                                    if (grammar == null
                                            || grammar.length() == 0) {
                                        grammar =
                                                ScriptGrammarFactory
                                                        .getDefaultScriptGrammar(targetActivity);
                                    }
                                    ScriptMappingCompositorFactory factory =
                                            ScriptMappingCompositorFactory
                                                    .getCompositorFactory(grammar,
                                                            dir,
                                                            SubProcUtil.SCRIPT_CONTEXT);
                                    if (factory != null) {
                                        ScriptMappingCompositor compositor =
                                                factory.getCompositor(targetActivity,
                                                        formal,
                                                        path);
                                        if (compositor != null
                                                && compositor instanceof SingleMappingCompositor) {
                                            SingleMappingCompositor scriptMapping =
                                                    (SingleMappingCompositor) compositor;
                                            for (Object next : scriptMapping
                                                    .getSourceItems(true)) {
                                                result.add(new Mapping(next,
                                                        formalConcept,
                                                        xpdlMapping));
                                            }
                                        }
                                    }
                                } else {
                                    actualObj = information;
                                    result.add(new Mapping(actualObj,
                                            formalConcept, xpdlMapping));
                                }
                            } else {
                                // Actual parameter no longer exists.
                            }
                        }
                    }
                } else {
                    dir = DirectionType.OUT_LITERAL;
                    if (xpdlMapping.getDirection()
                            .equals(DirectionType.OUT_LITERAL)) {
                        // Get actual parameter
                        Object actualObj = null;
                        Expression actual = xpdlMapping.getActual();
                        if (actual != null) {
                            String path = actual.getText();
                            actualObj =
                                    ConceptUtil
                                            .resolveConceptPath(targetActivity,
                                                    path);

                            /*
                             * Carry on EVEN if we cannot find that target so
                             * that the Mapping gets created and user can se the
                             * BROKEN mapping.
                             */

                            ScriptInformation information =
                                    (ScriptInformation) Xpdl2ModelUtil
                                            .getOtherElement(xpdlMapping,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getDocumentRoot_Script());
                            Object formalObj = null;
                            if (information == null) {
                                Object other =
                                        Xpdl2ModelUtil
                                                .getOtherElement(xpdlMapping,
                                                        XpdExtensionPackage.eINSTANCE
                                                                .getDocumentRoot_Expression());
                                String script;
                                String grammar;
                                if (other != null
                                        && other instanceof Expression) {
                                    Expression expression = (Expression) other;
                                    script = expression.getText();
                                    grammar = expression.getScriptGrammar();
                                } else {
                                    script = xpdlMapping.getFormal();
                                    grammar =
                                            ScriptGrammarFactory
                                                    .getScriptGrammar(targetActivity,
                                                            DirectionType.OUT_LITERAL);
                                    if (grammar == null) {
                                        grammar =
                                                ScriptGrammarFactory
                                                        .getDefaultScriptGrammar(targetActivity);
                                    }
                                }
                                ScriptMappingCompositorFactory factory =
                                        ScriptMappingCompositorFactory
                                                .getCompositorFactory(grammar,
                                                        dir,
                                                        SubProcUtil.SCRIPT_CONTEXT);
                                if (factory != null) {
                                    ScriptMappingCompositor compositor =
                                            factory.getCompositor(targetActivity,
                                                    path,
                                                    script);
                                    if (compositor != null
                                            && compositor instanceof SingleMappingCompositor) {
                                        SingleMappingCompositor scriptMapping =
                                                (SingleMappingCompositor) compositor;
                                        for (Object next : scriptMapping
                                                .getSourceItems(false)) {
                                            result.add(new Mapping(next,
                                                    actualObj, xpdlMapping));
                                        }
                                    }
                                }
                            } else {
                                formalObj = information;
                                result.add(new Mapping(formalObj, actualObj,
                                        xpdlMapping));
                            }
                        }
                    }
                }
            }

            return result.toArray();
        }

        return new Object[0];
    }

    /**
     * Simple NamedElement object that can be used for doign a search by name in
     * a list of process data.
     * 
     */
    private static class SearchParameter extends EObjectImpl implements
            NamedElement {
        private String name;

        public SearchParameter(String name) {
            this.name = name;
        }

        @Override
        public String getId() {
            return null;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public void setName(String value) {
        }

        @Override
        public FeatureMap getOtherAttributes() {
            return null;
        }
    }

}
