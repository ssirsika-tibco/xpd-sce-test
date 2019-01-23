/*
 **
 **  MODULE:             $RCSfile: ConditionGeneratorImpl.java $
 **                      $Revision: 1.0 $
 **                      $Date: 2005-08-18 $
 **
 **  DESCRIPTION:
 **
 **
 **  ENVIRONMENT:  Java - Platform independent
 **
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc, All Rights Reserved.
 **
 **  MODIFICATION HISTORY:
 **
 **    $Log: $
 **
 */
package com.tibco.xpd.simulation.preprocess.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.simulation.SimulationFactory;
import com.tibco.xpd.simulation.SimulationPackage;
import com.tibco.xpd.simulation.SplitParameterType;
import com.tibco.xpd.simulation.SplitSimulationDataType;
import com.tibco.xpd.simulation.StructuredConditionType;
import com.tibco.xpd.simulation.TransitionSimulationDataType;
import com.tibco.xpd.simulation.common.util.SimulationXpdlUtils;
import com.tibco.xpd.simulation.preprocess.GeneratorContext;
import com.tibco.xpd.simulation.preprocess.SimDataGenerator;
import com.tibco.xpd.simulation.preprocess.GeneratorContext.SimEnumValue;
import com.tibco.xpd.simulation.preprocess.util.StringUtils;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Condition;
import com.tibco.xpd.xpdl2.ConditionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.JoinSplitType;
import com.tibco.xpd.xpdl2.Route;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.commands.SetMixedCommand;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

public class ConditionGeneratorImpl implements SimDataGenerator {

    private static final int DEFAULT_WEIGHT = 10;

    private static String SIM_VAR_PREFIX = "simulation"; //$NON-NLS-1$

    private Set processParameters;

    private static class TransSimEnum {

        private Transition transition;

        private final int transIndex;

        private double weight = -1;

        private String condition;

        private boolean isDefault = false;

        private boolean recreateSimData = true;

        private boolean hadSimData;

        private boolean isParameterDetermined;

        private String parameterId;

        private String value;

        private String operator;

        private TransitionSimulationDataType transSimData;

        public TransSimEnum(Transition trans, int index,
                TransitionSimulationDataType transSimData, String condition,
                boolean isDefault) {
            this.transition = trans;
            this.transIndex = index;
            this.transSimData = transSimData;
            this.condition = condition;
            this.isDefault = isDefault;
            if (transSimData == null) {
                hadSimData = false;
                isParameterDetermined = true;
            } else {
                hadSimData = true;
                isParameterDetermined =
                        transSimData.isParameterDeterminedCondition();
                StructuredConditionType sc =
                        transSimData.getStructuredCondition();
                if (isParameterDetermined && sc != null) {
                    parameterId = sc.getParameterId();
                    value = sc.getParameterValue();
                    operator = sc.getOperator();
                }
            }
        }

        public String toString() {
            return "Value=\"" + value + "\", WeightFactor=\"" + weight + "\"" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                    + ", isDefault=" + isDefault; //$NON-NLS-1$
        }

    }

    public void generateSimDataForActivity(GeneratorContext context) {

        Activity activity = context.getCurrentActivity();
        Transition[] outTransitions = context.getOutgoingTransitions();
        /*
         * If parameter has only one transition then delete condition, other
         * split and transition related data should remains untouched for
         * feature use.
         */
        if (outTransitions.length < 2) {
            if (outTransitions.length == 1) {
                deleteConditionContentFromTransition(context, outTransitions[0]);
            }
            return;
        }

        /* AND/XOR condition split */
        Route route = activity.getRoute();
        if (route != null) {

            JoinSplitType split = Xpdl2ModelUtil.safeGetGatewayType(activity);
            if (split != null && JoinSplitType.PARALLEL_LITERAL.equals(split)) {
                // AND
                generateForAndActivity(context);
            } else {
                // XOR
                generateForXorActivity(context);
            }
        }
    }

    private void generateForXorActivity(GeneratorContext context) {
        Activity activity = context.getCurrentActivity();

        /* Check split if there is not split create new */
        if (!context.hasSplitSimulationData()
                || (context.getSplitParameterId() == null && context
                        .isParameterDeterminedSplit())
                || (context.getSplitParameterId().length() == 0 && context
                        .isParameterDeterminedSplit())) {
            createSplitSimulationData(context,
                    true,
                    getSimulationVarName(activity));
        }

        if (context.isParameterDeterminedSplit()) {
            String splitParamId = context.getSplitParameterId();
            if (context.existProcessParameters(splitParamId)) {
                context.setProcessParameterIsUsedInSplit(splitParamId, true);
            } else {
                context.addProcessParameters(splitParamId, true, true);
            }
            /* default transition */
            int defaultTransIndex = context.getDefaultTransitionIndex();
            if (defaultTransIndex == -1) { // there is no default
                defaultTransIndex = makeDefaultTransition(context);
            }
            TransSimEnum[] transSimEnums = createTransSimEnums(context);
            addSplitSimulationCommands(context, transSimEnums);

        } else {
            /*
             * Not determined split. Do no more. Is up to the user to set up
             * correctly conditions.
             */
            return;
        }

    }

    private void addSplitSimulationCommands(GeneratorContext context,
            TransSimEnum[] transSimEnums) {
        // TODO Create Transition simulation data + conditions
        for (int i = 0; i < transSimEnums.length; i++) {
            TransSimEnum e = transSimEnums[i];
            if (e.isParameterDetermined) {
                if (e.recreateSimData) {
                    StructuredConditionType sc =
                            SimulationFactory.eINSTANCE
                                    .createStructuredConditionType();
                    sc.setParameterId(e.parameterId);
                    sc.setOperator(e.operator == null ? "=" : e.operator); //$NON-NLS-1$
                    sc.setParameterValue(e.value == null ? "" : e.value); //$NON-NLS-1$
                    createTransitionSimulationData(context,
                            e.transition,
                            e.isParameterDetermined,
                            sc);
                }

                String conditionText = e.parameterId + " == \"" + e.value //$NON-NLS-1$
                        + "\";"; //$NON-NLS-1$

                setCondition(context,
                        e.transition,
                        getConditionText(e.condition, conditionText),
                        e.isDefault);
            }
        }
    }

    private String getConditionText(String oldCondition, String newCondition) {
        String generatedStartMark = "/** @generated */"; //$NON-NLS-1$
        String conditionText;
        if (newCondition != null && !newCondition.trim().equals("")) { //$NON-NLS-1$
            conditionText = generatedStartMark + "\n" + newCondition; //$NON-NLS-1$
        } else {
            conditionText = ""; //$NON-NLS-1$
        }
        // comment previous condition if exist and it's not
        // generated
        if (oldCondition != null && !oldCondition.trim().equals("")) { //$NON-NLS-1$
            String prevCond = oldCondition.trim();
            int index = prevCond.indexOf(generatedStartMark);
            if (index != -1) {
                prevCond = prevCond.substring(0, index).trim();
            }
            if (!prevCond.equals("")) { //$NON-NLS-1$
                if (!(prevCond.startsWith("/*") && prevCond.endsWith("*/"))) { //$NON-NLS-1$ //$NON-NLS-2$
                    prevCond = "/*" + prevCond + "*/"; //$NON-NLS-1$//$NON-NLS-2$
                }
                conditionText = prevCond + conditionText;
            }
        }
        return conditionText;
    }

    private TransSimEnum[] createTransSimEnums(GeneratorContext context) {
        Transition[] trans = context.getOutgoingTransitions();
        String parameterId = context.getSplitParameterId();
        Map simEnumValues = context.getProcessParameter().getValues();
        double meanWeight =
                context.getProcessParameter().getMeanParameterEnumWeight();
        meanWeight = (meanWeight > 0.0d) ? meanWeight : DEFAULT_WEIGHT;
        TransSimEnum[] transSimEnums = new TransSimEnum[trans.length];
        for (int i = 0; i < trans.length; i++) {
            Transition t = trans[i];
            boolean isDefaultTrans = (context.getDefaultTransitionIndex() == i);
            TransitionSimulationDataType transitionSimulationData =
                    SimulationXpdlUtils.getTransitionSimulationData(t);
            String condition = SimulationXpdlUtils.getTransitionCondition(t);
            transSimEnums[i] =
                    new TransSimEnum(t, i, transitionSimulationData, condition,
                            isDefaultTrans);
            if (!transSimEnums[i].isDefault) {
                if (!transSimEnums[i].hadSimData
                        && context.isParameterDeterminedSplit()) {
                    transSimEnums[i].isParameterDetermined = true;
                    transSimEnums[i].parameterId =
                            context.getSplitParameterId();
                    transSimEnums[i].weight = meanWeight;
                }
                if (!transSimEnums[i].parameterId.equals(context
                        .getSplitParameterId())
                        && context.isParameterDeterminedSplit()) {
                    transSimEnums[i].parameterId =
                            context.getSplitParameterId();
                }
                if (simEnumValues.containsKey(transSimEnums[i].value)) {
                    SimEnumValue processSimEnumValue =
                            ((GeneratorContext.SimEnumValue) simEnumValues
                                    .get(transSimEnums[i].value));
                    transSimEnums[i].weight =
                            processSimEnumValue.getWeightFactor();
                    processSimEnumValue.setUsedInCondition(true);
                    processSimEnumValue.setCreated(false);
                } else {
                    String transValue = getTransitionValue(t, i);
                    transSimEnums[i].value = transValue;
                    GeneratorContext.SimEnumValue processSimEnumValue =
                            new GeneratorContext.SimEnumValue(transValue,
                                    meanWeight);
                    processSimEnumValue.setCreated(true);
                    processSimEnumValue.setUsedInCondition(true);
                    simEnumValues.put(transValue, processSimEnumValue);
                }
            } else {
                if (context.isParameterDeterminedSplit()) {
                    transSimEnums[i].isParameterDetermined = true;
                    transSimEnums[i].parameterId =
                            context.getSplitParameterId();
                }

            }
        }
        // generate default value for parameter only if there are values not
        // assigned to transitions.
        if (isDefaultValueNeeded(simEnumValues, transSimEnums)) {
            String transValue = "DefaultValue"; //$NON-NLS-1$
            GeneratorContext.SimEnumValue processSimEnumValue =
                    new GeneratorContext.SimEnumValue(transValue, meanWeight);
            processSimEnumValue.setCreated(true);
            processSimEnumValue.setUsedInCondition(true);
            simEnumValues.put(transValue, processSimEnumValue);
        }
        return transSimEnums;
    }

    private boolean isDefaultValueNeeded(Map simEnumValues,
            TransSimEnum[] transSimEnums) {
        Set transSimEnumsSet = new HashSet();
        for (int i = 0; i < transSimEnums.length; i++) {
            transSimEnumsSet.add(transSimEnums[i].value);
        }
        return transSimEnumsSet.containsAll(simEnumValues.keySet());
    }

    private void createSplitSimulationData(GeneratorContext context,
            boolean isParamControled, String splitParameterId) {
        Activity activity = context.getCurrentActivity();
        EditingDomain ed = context.getEitingDomain();
        CompoundCommand compoundCmd = context.getCompoundCommand();

        Command cmd = null;

        // remowing existing extended attributes (of that type) if any
        // exists.
        List existingEASimData = new ArrayList();
        for (Iterator iter = activity.getExtendedAttributes().iterator(); iter
                .hasNext();) {
            ExtendedAttribute ea = (ExtendedAttribute) iter.next();
            if ("SplitSimulationData".equals(ea.getName())) { //$NON-NLS-1$
                existingEASimData.add(ea);
            }
        }
        if (existingEASimData.size() > 0) {
            cmd =
                    RemoveCommand
                            .create(ed,
                                    activity,
                                    Xpdl2Package.eINSTANCE
                                            .getExtendedAttributesContainer_ExtendedAttributes(),
                                    existingEASimData);
            compoundCmd.append(cmd);
        }

        // creating extended attributes.
        ExtendedAttribute ea = Xpdl2Factory.eINSTANCE.createExtendedAttribute();
        ea.setName("SplitSimulationData"); //$NON-NLS-1$

        // creating and attaching default distribution.
        SplitSimulationDataType splitSimData =
                SimulationFactory.eINSTANCE.createSplitSimulationDataType();
        splitSimData.setParameterDeterminedSplit(isParamControled);
        SplitParameterType splitParameter =
                SimulationFactory.eINSTANCE.createSplitParameterType();
        splitParameter.setParameterId(splitParameterId);
        splitSimData.setSplitParameter(splitParameter);
        ea.getMixed().add(SimulationPackage.eINSTANCE
                .getDocumentRoot_SplitSimulationData(),
                splitSimData);

        // adding extended attribute.
        cmd =
                AddCommand
                        .create(ed,
                                activity,
                                Xpdl2Package.eINSTANCE
                                        .getExtendedAttributesContainer_ExtendedAttributes(),
                                ea);
        compoundCmd.append(cmd);

        // updating context
        context.setSplitData(splitSimData);
    }

    private void generateForAndActivity(GeneratorContext context) {
        /* Check split */
        if (!context.hasSplitSimulationData()) {
            clearConditionsOnAllTransitions(context);
            return;
        } else {
            if (context.isParameterDeterminedSplit()) {
                clearConditionsOnAllTransitions(context);
            } else {
                /*
                 * Not determined split. Do no more. Is up to user to set up
                 * correctly conditions. TODO Maybe check if there is default
                 * transition and if not create one of the transition default.
                 * But I am not sure.
                 */
                return;
            }
        }

    }

    private void clearConditionsOnAllTransitions(GeneratorContext context) {
        /* TODO does not create any param but clear all conditions */
        Transition[] trans = context.getOutgoingTransitions();
        for (int i = 0; i < trans.length; i++) {
            String conditionText =
                    SimulationXpdlUtils.getTransitionCondition(trans[i]);
            if (conditionText != null) {
                // comment condition if not generated or remove generated
                String newCondition = getConditionText(conditionText, null);
                if (!newCondition.equals("")) { //$NON-NLS-1$
                    setCondition(context, trans[i], newCondition, false);
                } else {
                    deleteConditionFromTransition(context, trans[i]);
                }
            }
        }
    }

    private int makeDefaultTransition(GeneratorContext context) {
        Command cmd = null;
        /* take first without condition */
        Transition[] trans = context.getOutgoingTransitions();
        int defaultTransIndex = -1;
        for (int i = 0; i < trans.length; i++) {
            Condition cond = trans[i].getCondition();
            if (cond == null) {
                defaultTransIndex = i;
                break;
            }
        }
        /* if not found then take first transition */
        if (defaultTransIndex == -1) {
            defaultTransIndex = 0;
        }

        if (trans[defaultTransIndex].getCondition() != null) {
            cmd =
                    SetCommand.create(context.getEitingDomain(),
                            trans[defaultTransIndex].getCondition(),
                            Xpdl2Package.eINSTANCE.getCondition_Type(),
                            ConditionType.OTHERWISE_LITERAL);
            context.getCompoundCommand().append(cmd);
        } else {
            Condition condition = Xpdl2Factory.eINSTANCE.createCondition();
            condition.setType(ConditionType.OTHERWISE_LITERAL);
            cmd =
                    SetCommand.create(context.getEitingDomain(),
                            trans[defaultTransIndex],
                            Xpdl2Package.eINSTANCE.getTransition_Condition(),
                            condition);
            context.getCompoundCommand().append(cmd);
        }
        // setting context.
        context.setDefaultTransitionIndex(defaultTransIndex);
        return defaultTransIndex;
    }

    private void deleteConditionFromTransition(GeneratorContext context,
            Transition transition) {
        // transition.setCondition(null);
        Command cmd =
                SetCommand.create(context.getEitingDomain(),
                        transition,
                        Xpdl2Package.eINSTANCE.getTransition_Condition(),
                        null);
        context.getCompoundCommand().append(cmd);
    }

    private void deleteConditionContentFromTransition(GeneratorContext context,
            Transition trans) {
        Condition condition = trans.getCondition();
        // transition.setCondition(null);
        if (condition != null) {
            Command cmd = null;
            Expression expression = condition.getExpression();
            FeatureMap mixed = expression.getMixed();
            cmd =
                    new SetMixedCommand(mixed, XMLTypePackage.eINSTANCE
                            .getXMLTypeDocumentRoot_Text(), 0, ""); //$NON-NLS-1$
            context.getCompoundCommand().append(cmd);
        }
    }

    private String getTransitionValue(Transition transition, int i) {
        String prefix = String.valueOf(i) + " : "; //$NON-NLS-1$
        if (transition.getDescription() != null
                && transition.getDescription().getValue() != null
                && transition.getDescription().getValue().length() != 0) {
            return prefix + transition.getDescription();
        } else if (transition.getName() != null
                && transition.getName().length() != 0) {
            return prefix + transition.getName();
        } else {
            return prefix + "(transitionId=" + transition.getId() + ")"; //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    private String getSimulationVarName(Activity activity) {
        String result = null;
        if (activity.getDescription() != null
                && activity.getDescription().getValue() != null
                && activity.getDescription().getValue().length() != 0) {
            result =
                    StringUtils.toVariableLikeString(activity.getDescription()
                            .getValue());
        } else if (activity.getName() != null
                && activity.getName().length() != 0) {
            result = StringUtils.toVariableLikeString(activity.getName());
        } else {
            result = "varForActivity";// + //$NON-NLS-1$
            // StringUtils.toVariableLikeString(activity.getId());
            // //$NON-NLS-1$
        }
        result += StringUtils.toVariableLikeString(activity.getId());
        return SIM_VAR_PREFIX + result;
    }

    private void createTransitionSimulationData(GeneratorContext context,
            Transition trans, boolean isControled,
            StructuredConditionType structuredCondition) {
        EditingDomain ed = context.getEitingDomain();
        CompoundCommand compoundCmd = context.getCompoundCommand();

        Command cmd = null;

        // remowing existing extended attributes (of that type) if any
        // exists.
        List existingEASimData = new ArrayList();
        for (Iterator iter = trans.getExtendedAttributes().iterator(); iter
                .hasNext();) {
            ExtendedAttribute ea = (ExtendedAttribute) iter.next();
            if ("TransitionSimulationData".equals(ea.getName())) { //$NON-NLS-1$
                existingEASimData.add(ea);
            }
        }
        if (existingEASimData.size() > 0) {
            cmd =
                    RemoveCommand
                            .create(ed,
                                    trans,
                                    Xpdl2Package.eINSTANCE
                                            .getExtendedAttributesContainer_ExtendedAttributes(),
                                    existingEASimData);
            compoundCmd.append(cmd);
        }

        // creating extended attributes.
        ExtendedAttribute ea = Xpdl2Factory.eINSTANCE.createExtendedAttribute();
        ea.setName("TransitionSimulationData"); //$NON-NLS-1$

        // creating and attaching object to extended attr.
        TransitionSimulationDataType transSimData =
                SimulationFactory.eINSTANCE
                        .createTransitionSimulationDataType();
        transSimData.setParameterDeterminedCondition(isControled);
        transSimData.setStructuredCondition(structuredCondition);
        ea.getMixed().add(SimulationPackage.eINSTANCE
                .getDocumentRoot_TransitionSimulationData(),
                transSimData);

        // adding extended attribute.
        cmd =
                AddCommand
                        .create(ed,
                                trans,
                                Xpdl2Package.eINSTANCE
                                        .getExtendedAttributesContainer_ExtendedAttributes(),
                                ea);
        compoundCmd.append(cmd);
    }

    private void setCondition(GeneratorContext context, Transition trans,
            String conditionText, boolean makeOtherwise) {
        Command cmd = null;
        if (trans.getCondition() != null) {
            ConditionType conditionType = null;
            if (makeOtherwise) {
                conditionType = ConditionType.OTHERWISE_LITERAL;
            } else if (trans.getCondition().getType() == ConditionType.CONDITION_LITERAL
                    || trans.getCondition().getType() == ConditionType.DEFAULTEXCEPTION_LITERAL
                    || trans.getCondition().getType() == ConditionType.EXCEPTION_LITERAL) {
                conditionType = trans.getCondition().getType();
            } else {
                conditionType = ConditionType.CONDITION_LITERAL;
            }
            cmd =
                    SetCommand.create(context.getEitingDomain(), trans
                            .getCondition(), Xpdl2Package.eINSTANCE
                            .getCondition_Type(), conditionType);
            context.getCompoundCommand().append(cmd);
            Expression expression = Xpdl2Factory.eINSTANCE.createExpression();
            cmd =
                    SetCommand.create(context.getEitingDomain(), trans
                            .getCondition(), Xpdl2Package.eINSTANCE
                            .getCondition_Expression(), expression);
            context.getCompoundCommand().append(cmd);
            cmd =
                    new SetMixedCommand(expression.getMixed(),
                            XMLTypePackage.eINSTANCE
                                    .getXMLTypeDocumentRoot_Text(), 0,
                            conditionText);
            context.getCompoundCommand().append(cmd);
        } else {
            Condition condition = Xpdl2Factory.eINSTANCE.createCondition();
            ConditionType conditionType;
            if (!makeOtherwise) {
                conditionType = ConditionType.CONDITION_LITERAL;
            } else {
                conditionType = ConditionType.OTHERWISE_LITERAL;
            }
            condition.setType(conditionType);
            Expression expression = Xpdl2Factory.eINSTANCE.createExpression();
            condition.setExpression(expression);
            expression.getMixed().add(0,
                    XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                    conditionText);
            cmd =
                    SetCommand.create(context.getEitingDomain(),
                            trans,
                            Xpdl2Package.eINSTANCE.getTransition_Condition(),
                            condition);
            context.getCompoundCommand().append(cmd);
        }
    }
}
