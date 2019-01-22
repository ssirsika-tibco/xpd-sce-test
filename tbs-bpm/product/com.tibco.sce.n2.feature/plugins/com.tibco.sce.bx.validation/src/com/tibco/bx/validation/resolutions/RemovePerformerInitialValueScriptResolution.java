/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.bx.validation.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Sets the Data Field Performer Initial Value script to 'Free Text'
 * 
 * @author kthombar
 * @since 11-Sep-2013
 */
public class RemovePerformerInitialValueScriptResolution extends
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

        if (target instanceof DataField) {
            DataField dataField = (DataField) target;
            BasicType basicType =
                    BasicTypeConverterFactory.INSTANCE.getBasicType(dataField);

            if (basicType != null) {
                CompoundCommand cmd = new CompoundCommand();
                cmd.setLabel(Messages.RemovePerformerInitialValueScriptResolution_RemoveInitialValueScriptCommand_label);

                /*
                 * Setting the participant query to null as we do not permit RQL
                 * performer with array type.
                 */
                cmd.append(Xpdl2ModelUtil
                        .getSetOtherElementCommand(editingDomain,
                                basicType,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ParticipantQuery(),
                                null));

                return cmd;
            }
        }
        return null;
    }
}
