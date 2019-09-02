/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.bom.validator.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.uml2.common.edit.command.ChangeCommand;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.globaldata.resources.SummaryInfoUtils;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Make an attribute non-summary.
 *
 * @author pwatson
 * @since 2 Sep 2019
 */
public class RemoveSummaryResolution extends AbstractWorkingCopyResolution implements IResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain, EObject target, IMarker marker)
            throws ResolutionException {

        if (!(target instanceof Property)) {
            return null;
        }

        final Property property = (Property) target;

        return new ChangeCommand(editingDomain, () -> {
            SummaryInfoUtils.setSummary(property, false);
        });
    }
}
