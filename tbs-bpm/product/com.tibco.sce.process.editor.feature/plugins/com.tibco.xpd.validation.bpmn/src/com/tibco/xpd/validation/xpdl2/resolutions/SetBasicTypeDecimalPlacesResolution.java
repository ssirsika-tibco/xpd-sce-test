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
import org.eclipse.jface.window.Window;

import com.tibco.xpd.processeditor.xpdl2.util.ProcessRelevantDataUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Scale;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * SetBasicTypeDecimalPlacesResolution - opens a dialog (with default value two)
 * for scale value to be entered by the user
 * 
 * 
 * @author bharge
 * @since 3.3 (18 Mar 2010)
 */
public class SetBasicTypeDecimalPlacesResolution extends
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
        CompoundCommand cmd = new CompoundCommand();
        if (target instanceof ProcessRelevantData) {
            ProcessRelevantData processRelevantData =
                    (ProcessRelevantData) target;
            DataType dataType = processRelevantData.getDataType();
            if (dataType instanceof BasicType) {
                BasicType basicType = (BasicType) dataType;
                if (BasicTypeType.FLOAT_LITERAL.equals(basicType.getType())) {
                    setDecimalPlaces(editingDomain, cmd, basicType);
                }
            }
        }
        if (target instanceof TypeDeclaration) {
            TypeDeclaration typeDeclaration = (TypeDeclaration) target;
            BasicType basicType = typeDeclaration.getBasicType();
            if (null != basicType) {
                if (BasicTypeType.FLOAT_LITERAL.equals(basicType.getType())) {
                    setDecimalPlaces(editingDomain, cmd, basicType);
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
    private void setDecimalPlaces(EditingDomain editingDomain,
            CompoundCommand cmd, BasicType basicType) {
        String oldValue = ""; //$NON-NLS-1$
        if (null != basicType.getScale()) {
            oldValue = String.valueOf(basicType.getScale().getValue());
        }

        short newValue = 0;
        String newScaleValue =
                setDecimalValue(oldValue,
                        String
                                .valueOf(ProcessRelevantDataUtil.DEFAULT_FLOATTYPE_SCALE));
        if (newScaleValue.length() > 0) {
            newValue =
                    ProcessRelevantDataUtil
                            .safeParseShort((String) newScaleValue);

            Scale newScale = Xpdl2Factory.eINSTANCE.createScale();
            newScale.setValue(newValue);
            cmd.append(SetCommand.create(editingDomain,
                    basicType,
                    Xpdl2Package.eINSTANCE.getBasicType_Scale(),
                    newScale));
        }
    }

    /**
     * @param name
     *            The current name.
     * @return The new name.
     */
    private String setDecimalValue(String oldValue, String defaultValue) {
        RenameDialog dialog = new RenameDialog(null, oldValue);
        dialog.setDefaultName(defaultValue);
        dialog.setBlockOnOpen(true);
        int result = dialog.open();
        if (result == Window.OK) {
            return dialog.getName();
        } else {
            return ""; //$NON-NLS-1$
        }
    }
}
