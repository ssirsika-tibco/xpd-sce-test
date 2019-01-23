/* 
 ** 
 **  MODULE:             $RCSfile: FlowProcessorImpl.java $ 
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
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.simulation.EnumerationValueType;
import com.tibco.xpd.simulation.ParameterDistribution;
import com.tibco.xpd.simulation.SimulationFactory;
import com.tibco.xpd.simulation.SimulationPackage;
import com.tibco.xpd.simulation.WorkflowProcessSimulationDataType;
import com.tibco.xpd.simulation.preprocess.FlowProcessor;
import com.tibco.xpd.simulation.preprocess.GeneratorContext;
import com.tibco.xpd.simulation.preprocess.SimDataGenerator;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

public class FlowProcessorImpl implements FlowProcessor {

    public void processFlow(SimDataGenerator[] generators,
            FlowContainer flowContainer, GeneratorContext context) {

        EditingDomain ed = context.getEitingDomain();
        CompoundCommand compoundCmd = context.getCompoundCommand();

        WorkflowProcessSimulationDataType workflowSimData =
                SimulationFactory.eINSTANCE
                        .createWorkflowProcessSimulationDataType();

        for (Iterator iter = flowContainer.getActivities().iterator(); iter
                .hasNext();) {
            Activity activity = (Activity) iter.next();

            for (int i = 0; i < generators.length; i++) {
                context.setCurrentActivity(activity);
                generators[i].generateSimDataForActivity(context);
                context.setCurrentActivity(null);
            }
        }
        createWSDFromContext(context);
    }

    private void createWSDFromContext(GeneratorContext context) {
        WorkflowProcessSimulationDataType wpSimData =
                SimulationFactory.eINSTANCE
                        .createWorkflowProcessSimulationDataType();
        Set processParameters = new HashSet();
        for (Iterator iter =
                ProcessInterfaceUtil.getAllFormalParameters(context
                        .getWorkflowProcess()).iterator(); iter.hasNext();) {
            FormalParameter param = (FormalParameter) iter.next();
            processParameters.add(param.getName());
        }
        Collection simProcessParams =
                context.getSimProcessParameters().values();
        CompoundCommand compoundCmd = null;
        Command cmd = null;
        for (Iterator iter = simProcessParams.iterator(); iter.hasNext();) {
            GeneratorContext.SimProcessParameter simProcessParameter =
                    (GeneratorContext.SimProcessParameter) iter.next();
            String parameterId = simProcessParameter.getId();
            if (!processParameters.contains(parameterId)
                    && simProcessParameter.isUsedInSplit()) {
                FormalParameter fp =
                        Xpdl2Factory.eINSTANCE.createFormalParameter();
                fp.setName(parameterId);
                Xpdl2ModelUtil.setOtherAttribute(fp,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_DisplayName(),
                        parameterId);
                fp.setMode(ModeType.IN_LITERAL);

                DataType dt = Xpdl2Factory.eINSTANCE.createBasicType();
                fp.setDataType(dt);

                cmd =
                        AddCommand.create(context.getEitingDomain(), context
                                .getWorkflowProcess(), Xpdl2Package.eINSTANCE
                                .getFormalParameter(), fp);
                // context.getCompoundCommand().append(cmd);
                if (compoundCmd == null) {
                    compoundCmd = new CompoundCommand();
                }
                compoundCmd.append(cmd);
                processParameters.add(parameterId);
            }

            ParameterDistribution paramDistribution =
                    SimulationFactory.eINSTANCE.createParameterDistribution();
            paramDistribution.setParameterId(parameterId);
            for (Iterator iterator =
                    simProcessParameter.getValues().values().iterator(); iterator
                    .hasNext();) {
                GeneratorContext.SimEnumValue simEnumValue =
                        (GeneratorContext.SimEnumValue) iterator.next();
                EnumerationValueType enumValue =
                        SimulationFactory.eINSTANCE
                                .createEnumerationValueType();
                enumValue.setValue(simEnumValue.getValue());
                enumValue.setWeightingFactor(simEnumValue.getWeightFactor());
                paramDistribution.getEnumerationValue().add(enumValue);
            }
            wpSimData.getParameterDistribution().add(paramDistribution);
        }
        createWSDExtendedAttribute(context, wpSimData);
        // adding the creation of formal parameters after the work flow
        // simulation data has been added.
        if (compoundCmd != null) {
            context.getCompoundCommand().append(compoundCmd);
        }
    }

    private void createWSDExtendedAttribute(GeneratorContext context,
            WorkflowProcessSimulationDataType workflowSimData) {
        Process process = context.getWorkflowProcess();
        EditingDomain ed = context.getEitingDomain();
        CompoundCommand compoundCmd = context.getCompoundCommand();

        Command cmd = null;

        // remowing existing extended attributes (of that type) if any
        // exists.
        List existingEASimData = new ArrayList();
        for (Iterator iter = process.getExtendedAttributes().iterator(); iter
                .hasNext();) {
            ExtendedAttribute ea = (ExtendedAttribute) iter.next();
            if ("WorkflowProcessSimulationData".equals(ea.getName())) { //$NON-NLS-1$
                existingEASimData.add(ea);
            }
        }
        if (existingEASimData.size() > 0) {
            cmd =
                    RemoveCommand
                            .create(ed,
                                    process,
                                    Xpdl2Package.eINSTANCE
                                            .getExtendedAttributesContainer_ExtendedAttributes(),
                                    existingEASimData);
            compoundCmd.append(cmd);
        }

        // creating extended attributes.
        ExtendedAttribute ea = Xpdl2Factory.eINSTANCE.createExtendedAttribute();
        ea.setName("WorkflowProcessSimulationData"); //$NON-NLS-1$

        // attaching object to extended attr.

        ea.getMixed().add(SimulationPackage.eINSTANCE
                .getDocumentRoot_WorkflowProcessSimulationData(),
                workflowSimData);

        // adding extended attribute.
        cmd =
                AddCommand
                        .create(ed,
                                process,
                                Xpdl2Package.eINSTANCE
                                        .getExtendedAttributesContainer_ExtendedAttributes(),
                                ea);
        compoundCmd.append(cmd);
    }
}
