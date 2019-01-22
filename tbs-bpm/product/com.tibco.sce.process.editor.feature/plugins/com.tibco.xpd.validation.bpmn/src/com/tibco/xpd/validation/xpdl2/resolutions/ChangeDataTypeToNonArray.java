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
import com.tibco.xpd.xpdl2.Length;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * @author NWilson
 * 
 */
public class ChangeDataTypeToNonArray extends AbstractWorkingCopyResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain ed, EObject target,
            IMarker marker) throws ResolutionException {
        CompoundCommand cmd = null;
        if (target instanceof ProcessRelevantData) {
            ProcessRelevantData field = (ProcessRelevantData) target;
            cmd =
                    new CompoundCommand(
                            Messages.ChangeDataTypeToString_ChangeDataTypeToNonArrayCommand);
            BasicType type = Xpdl2Factory.eINSTANCE.createBasicType();
            type.setType(BasicTypeType.STRING_LITERAL);
            Length length = Xpdl2Factory.eINSTANCE.createLength();
            length.setValue("50"); //$NON-NLS-1$
            type.setLength(length);
            cmd.append(SetCommand.create(ed,
                    field,
                    Xpdl2Package.eINSTANCE.getProcessRelevantData_DataType(),
                    type));
            cmd.append(SetCommand.create(ed,
                    field,
                    Xpdl2Package.eINSTANCE.getProcessRelevantData_IsArray(),
                    Boolean.FALSE));
        }
        return cmd;
    }

}
