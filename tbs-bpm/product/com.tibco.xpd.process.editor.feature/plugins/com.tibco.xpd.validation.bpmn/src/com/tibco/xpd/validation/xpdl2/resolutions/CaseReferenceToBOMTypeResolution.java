/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validations.bpmn.internal.Messages;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Resolution to convert a Case-Reference type <code>ProcessRelevantData</code>
 * to BOM-type.
 * 
 * @author sajain
 * @since Jul 16, 2015
 */
public class CaseReferenceToBOMTypeResolution extends
        AbstractWorkingCopyResolution {

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     * 
     * @param editingDomain
     * @param target
     * @param marker
     * @return
     * @throws ResolutionException
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {

        if (target instanceof ProcessRelevantData) {

            ProcessRelevantData prd = (ProcessRelevantData) target;

            DataType dt = prd.getDataType();

            if (dt != null) {

                /*
                 * Make sure that we proceed only for data which are of Case-ref
                 * type.
                 */
                if (dt instanceof RecordType) {

                    ExternalReference extRef =
                            ((RecordType) dt).getMember().get(0)
                                    .getExternalReference();

                    ExternalReference newExtRefForBOMType =
                            Xpdl2Factory.eINSTANCE.createExternalReference();
                    newExtRefForBOMType.setLocation(extRef.getLocation());
                    newExtRefForBOMType.setNamespace(extRef.getNamespace());
                    newExtRefForBOMType.setXref(extRef.getXref());

                    CompoundCommand cmd =
                            new CompoundCommand(
                                    Messages.CaseReferenceToBOMTypeResolution_Command_label);

                    cmd.append(SetCommand.create(editingDomain,
                            prd,
                            Xpdl2Package.eINSTANCE
                                    .getProcessRelevantData_DataType(),
                            newExtRefForBOMType));

                    return cmd;
                }
            }
        }

        return null;
    }
}