/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.bds.designtime.validator.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.uml2.uml.EnumerationLiteral;

import com.tibco.xpd.bom.modeler.custom.enumlitext.util.EnumLitValueUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Quick-fix resolution to reset the value of an {@link EnumerationLiteral}.
 * This will set the value to match the name of the literal
 * 
 * @author njpatel
 */
public class ResetEnumLiteralValue extends AbstractWorkingCopyResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            final EObject target, IMarker marker) throws ResolutionException {
        if (target instanceof EnumerationLiteral) {
            final String name = ((EnumerationLiteral) target).getName();
            if (name != null && !name.isEmpty()) {
                return new RecordingCommand(
                        (TransactionalEditingDomain) editingDomain,
                        "Reset the Literal value") {

                    @Override
                    protected void doExecute() {
                        EnumLitValueUtil
                                .setSingleValue((EnumerationLiteral) target,
                                        name);
                    }
                };
            }
        }
        return null;
    }

}
