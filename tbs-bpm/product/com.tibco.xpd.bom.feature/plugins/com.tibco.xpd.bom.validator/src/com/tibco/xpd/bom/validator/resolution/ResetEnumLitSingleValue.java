/**
 * Copyright (c) TIBCO Software Inc 2004-2012. All rights reserved.
 */
package com.tibco.xpd.bom.validator.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.uml2.uml.EnumerationLiteral;

import com.tibco.xpd.bom.modeler.custom.enumlitext.DomainValue;
import com.tibco.xpd.bom.modeler.custom.enumlitext.SingleValue;
import com.tibco.xpd.bom.modeler.custom.enumlitext.util.EnumLitValueUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Quick-fix resolution to reset the single value of an enumeration literal.
 * 
 * @author njpatel
 * @since 3.5.10
 */
public class ResetEnumLitSingleValue extends AbstractWorkingCopyResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        Command cmd = null;

        if (target instanceof EnumerationLiteral) {
            DomainValue value =
                    EnumLitValueUtil
                            .getDomainValue((EnumerationLiteral) target);
            if (value instanceof SingleValue) {
                cmd =
                        EnumLitValueUtil
                                .getSetSingleValueCommand((TransactionalEditingDomain) editingDomain,
                                        (EnumerationLiteral) target,
                                        ""); //$NON-NLS-1$
            }
        }

        return cmd;
    }
}
