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
import com.tibco.xpd.xpdl2.Length;
import com.tibco.xpd.xpdl2.Precision;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * SetBasicTypeLengthResolution - opens a dialog (with default value ten) for
 * length/precision value to be entered by the user
 * 
 * 
 * @author bharge
 * @since 3.3 (22 Apr 2010)
 */
public class SetBasicTypeLengthResolution extends AbstractWorkingCopyResolution {

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
                if (BasicTypeType.FLOAT_LITERAL.equals(basicType.getType())
                        || BasicTypeType.INTEGER_LITERAL.equals(basicType
                                .getType())
                        || BasicTypeType.STRING_LITERAL.equals(basicType
                                .getType())) {
                    setLength(editingDomain, cmd, basicType);
                }
            }
        }
        if (target instanceof TypeDeclaration) {
            TypeDeclaration typeDeclaration = (TypeDeclaration) target;
            BasicType basicType = typeDeclaration.getBasicType();
            if (null != basicType) {
                if (BasicTypeType.FLOAT_LITERAL.equals(basicType.getType())
                        || BasicTypeType.INTEGER_LITERAL.equals(basicType
                                .getType())
                        || BasicTypeType.STRING_LITERAL.equals(basicType
                                .getType())) {
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
        String oldValue = ""; //$NON-NLS-1$
        if (BasicTypeType.STRING_LITERAL.equals(basicType.getType())) {
            if (basicType.getLength() != null) {
                oldValue = basicType.getLength().getValue();
            }
            String newValue =
                    setLengthValue(oldValue,
                            ProcessRelevantDataUtil.DEFAULT_STRINGTYPE_LENGTH);
            Length length = Xpdl2Factory.eINSTANCE.createLength();
            length.setValue(newValue);
            cmd.append(SetCommand.create(editingDomain,
                    basicType,
                    Xpdl2Package.eINSTANCE.getBasicType_Length(),
                    length));
        } else {
            if (null != basicType.getPrecision()) {
                oldValue = String.valueOf(basicType.getPrecision().getValue());
            }
            String newPrecisionValue =
                    setLengthValue(oldValue,
                            String
                                    .valueOf(ProcessRelevantDataUtil.DEFAULT_NUMBERTYPE_PRECISION));
            if (newPrecisionValue.length() > 0) {
                Precision newPrecision =
                        Xpdl2Factory.eINSTANCE.createPrecision();
                newPrecision.setValue(ProcessRelevantDataUtil
                        .safeParseShort(newPrecisionValue));
                cmd.append(SetCommand.create(editingDomain,
                        basicType,
                        Xpdl2Package.eINSTANCE.getBasicType_Precision(),
                        newPrecision));
            }
        }
    }

    /**
     * @param valueOf
     * @param valueOf2
     * @return
     */
    private String setLengthValue(String oldValue, String defaultValue) {
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
