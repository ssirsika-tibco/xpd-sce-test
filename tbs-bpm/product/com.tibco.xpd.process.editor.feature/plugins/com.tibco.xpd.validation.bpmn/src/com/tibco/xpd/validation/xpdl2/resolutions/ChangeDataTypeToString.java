/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
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
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * @author NWilson
 * 
 */
public class ChangeDataTypeToString extends AbstractWorkingCopyResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain ed, EObject target,
            IMarker marker) throws ResolutionException {
        CompoundCommand cmd = null;
        if (target instanceof DataField) {
            DataField field = (DataField) target;
            cmd =
                new CompoundCommand(
                        Messages.ChangeDataTypeToString_ChangeDataTypeCommand);
            BasicType type = Xpdl2Factory.eINSTANCE.createBasicType();
            type.setType(BasicTypeType.STRING_LITERAL);
            cmd.append(SetCommand.create(ed, field, Xpdl2Package.eINSTANCE
                    .getProcessRelevantData_DataType(), type));
            cmd.append(SetCommand.create(ed, field, Xpdl2Package.eINSTANCE
                    .getProcessRelevantData_IsArray(), Boolean.FALSE));
        }
        return cmd;
    }

}
