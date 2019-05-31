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
public class SetBasicTypeDecimalPlacesResolution
        extends AbstractWorkingCopyResolution {

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
            ProcessRelevantData data = (ProcessRelevantData) target;
            DataType dataType = data.getDataType();
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
        short oldValue = (null == basicType.getScale()) ? Short.MIN_VALUE
                : basicType.getScale().getValue();

        // allow the user to select a new value
        String newScaleValue = getNewValue(oldValue,
                ProcessRelevantDataUtil.DEFAULT_FLOATTYPE_SCALE);

        // if a new value given
        if ((newScaleValue != null) && (!newScaleValue.isEmpty())) {
            short newValue =
                    ProcessRelevantDataUtil.safeParseShort(newScaleValue);

            Scale newScale = Xpdl2Factory.eINSTANCE.createScale();
            newScale.setValue(newValue);
            cmd.append(SetCommand.create(editingDomain,
                    basicType,
                    Xpdl2Package.eINSTANCE.getBasicType_Scale(),
                    newScale));
        }
    }

    /**
     * Ask the user what value to set as the decimal places.
     * 
     * @param oldValue
     *            the current decimal places setting.
     * @param defaultValue
     *            the default decimal places setting
     * @return the chosen decimal places setting - as a string. If the user
     *         choses "cancel", the return value will be an empty string.
     */
    private String getNewValue(short oldValue, short defaultValue) {

        String old =
                (oldValue == Short.MIN_VALUE) ? "" : String.valueOf(oldValue); //$NON-NLS-1$
        String def = (defaultValue == Short.MIN_VALUE) ? "" //$NON-NLS-1$
                : String.valueOf(defaultValue);
        return RenameDialog.getDecimalPlaces(old, def);
    }
}
