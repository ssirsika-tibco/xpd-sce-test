/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
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
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * 
 * 
 * @author aallway
 * @since 3.3 (8 Jan 2010)
 */
public class RemovePerformerDataLengthResolution extends
        AbstractWorkingCopyResolution {

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#
     * getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     * org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        if (target instanceof ProcessRelevantData) {
            ProcessRelevantData data = (ProcessRelevantData) target;

            if (data.getDataType() instanceof BasicType) {
                BasicType basicType = (BasicType) data.getDataType();

                if (BasicTypeType.PERFORMER_LITERAL.equals(basicType.getType())) {
                    CompoundCommand cmd = new CompoundCommand();

                    if (basicType.getLength() != null) {
                        cmd.append(SetCommand.create(editingDomain,
                                basicType,
                                Xpdl2Package.eINSTANCE.getBasicType_Length(),
                                SetCommand.UNSET_VALUE));
                    }

                    if (basicType.getPrecision() != null) {
                        cmd.append(SetCommand
                                .create(editingDomain,
                                        basicType,
                                        Xpdl2Package.eINSTANCE
                                                .getBasicType_Precision(),
                                        SetCommand.UNSET_VALUE));
                    }

                    if (basicType.getScale() != null) {
                        cmd.append(SetCommand.create(editingDomain,
                                basicType,
                                Xpdl2Package.eINSTANCE.getBasicType_Scale(),
                                SetCommand.UNSET_VALUE));
                    }

                    return cmd;
                }
            }
        }

        return null;
    }

}
