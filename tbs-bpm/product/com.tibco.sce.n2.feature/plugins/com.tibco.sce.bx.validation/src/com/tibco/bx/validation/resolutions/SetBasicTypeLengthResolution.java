/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.bx.validation.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.util.ProcessRelevantDataUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.Precision;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Set the length of the integer fields to maximum integer length
 * <code>ProcessRelevantDataUtil.MAX_NUMBERTYPE_PRECISION</code> for a selected
 * field
 * 
 * @author bharge
 * 
 */
public class SetBasicTypeLengthResolution extends AbstractWorkingCopyResolution {

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
        CompoundCommand cmd = new CompoundCommand();
        if (target instanceof ProcessRelevantData) {
            ProcessRelevantData processRelevantData =
                    (ProcessRelevantData) target;
            DataType dataType = processRelevantData.getDataType();
            if (dataType instanceof BasicType) {
                BasicType basicType = (BasicType) dataType;
                if (BasicTypeType.INTEGER_LITERAL.equals(basicType.getType())) {
                    setLength(editingDomain, cmd, basicType);
                }
            }
        }
        if (target instanceof TypeDeclaration) {
            TypeDeclaration typeDeclaration = (TypeDeclaration) target;
            BasicType basicType = typeDeclaration.getBasicType();
            if (null != basicType) {
                if (BasicTypeType.INTEGER_LITERAL.equals(basicType.getType())) {
                    setLength(editingDomain, cmd, basicType);
                }
            }
        }
        return cmd;

    }

    /**
     * @param editingDomain
     * @param cmd
     * @param basicType
     */
    private void setLength(EditingDomain editingDomain, CompoundCommand cmd,
            BasicType basicType) {
        Precision newPrecision = Xpdl2Factory.eINSTANCE.createPrecision();
        newPrecision.setValue(ProcessRelevantDataUtil.MAX_NUMBERTYPE_PRECISION);
        cmd.append(SetCommand.create(editingDomain,
                basicType,
                Xpdl2Package.eINSTANCE.getBasicType_Precision(),
                newPrecision));

    }

}
