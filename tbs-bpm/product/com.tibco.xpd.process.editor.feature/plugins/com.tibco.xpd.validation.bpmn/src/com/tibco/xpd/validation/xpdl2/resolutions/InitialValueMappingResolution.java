/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.xpdl2.resolutions;

import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.InitialValue;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.util.SubProcUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validations.bpmn.internal.Messages;
import com.tibco.xpd.xpdExtension.InitialParameterValue;
import com.tibco.xpd.xpdExtension.InitialValues;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author nwilson
 */
public class InitialValueMappingResolution extends
        AbstractWorkingCopyResolution {

    /**
     * @param ed
     *            The editing domain.
     * @param target
     *            The target EObject.
     * @param marker
     *            The problem marker.
     * @return The command for the resolution.
     * @throws ResolutionException
     *             If the resolution failed.
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#
     *      getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     */
    @Override
    protected Command getResolutionCommand(EditingDomain ed, EObject target,
            IMarker marker) throws ResolutionException {
        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.InitialValueMappingResolution_MapToAllowedValueCommand);
        if (target instanceof DataMapping) {
            DataMapping mapping = (DataMapping) target;
            String mappingTarget = DataMappingUtil.getTarget(mapping);
            String grammar = DataMappingUtil.getScript(mapping);
            cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                    mapping,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_InitialValueMapping(),
                    Boolean.TRUE));
            Activity activity = Xpdl2ModelUtil.getParentActivity(mapping);
            String value = null;
            InitialParameterValue initial =
                    InitialValue.getInitialParameterValue(activity,
                            mappingTarget);
            if (initial != null) {
                value = initial.getValue();
            }
            if (value == null) {
                SubFlow subFlow = getSubFlow(mapping);
                if (subFlow != null) {
                    Process subproc = SubProcUtil.getSubProcess(subFlow);
                    if (subproc != null) {
                        ConceptPath formalConcept =
                                SubProcUtil.resolveParameter(subproc,
                                        MappingDirection.IN,
                                        mappingTarget);
                        if (formalConcept != null) {
                            Object item = formalConcept.getItem();
                            if (item instanceof FormalParameter) {
                                FormalParameter formal = (FormalParameter) item;
                                // Use the first available value.
                                Object other =
                                        Xpdl2ModelUtil
                                                .getOtherElement(formal,
                                                        XpdExtensionPackage.eINSTANCE
                                                                .getDocumentRoot_InitialValues());
                                if (other instanceof InitialValues) {
                                    InitialValues values =
                                            (InitialValues) other;
                                    List<?> tokens = values.getValue();
                                    if (tokens.size() > 0) {
                                        Object token = tokens.get(0);
                                        if (token instanceof String) {
                                            value = (String) token;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (value == null) {
                String message =
                        Messages.InitialValueMappingResolution_ValidAllowedValueNotFoundError;
                message = String.format(message, mappingTarget);
                throw new ResolutionException(message);
            }
            Expression expression = Xpdl2ModelUtil.createExpression(value);
            expression.setScriptGrammar(grammar);
            cmd.append(SetCommand.create(ed, mapping, Xpdl2Package.eINSTANCE
                    .getDataMapping_Actual(), expression));
        }
        return cmd;
    }

    /**
     * @param mapping
     *            The data mapping.
     * @return The containing subflow.
     */
    private SubFlow getSubFlow(DataMapping mapping) {
        SubFlow flow = null;
        EObject parent = mapping.eContainer();
        if (parent instanceof SubFlow) {
            flow = (SubFlow) parent;
        }
        return flow;
    }

}
