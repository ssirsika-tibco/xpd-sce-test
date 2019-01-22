/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.validator.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.IFilter;

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.OrganizationType;
import com.tibco.xpd.om.resources.ui.commonpicker.OMTypeQuery;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.picker.PickerService;
import com.tibco.xpd.resources.ui.picker.PickerTypeQuery;
import com.tibco.xpd.resources.ui.picker.filters.SameResourceFilter;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Quick fix resolution for {@link Organization} without a type set. This will
 * display a picker to the user to select an {@link OrganizationType} to apply
 * to the <code>Organization</code>.
 * 
 * @author njpatel
 * 
 */
public class UnTypedOrganizationResolution extends
        AbstractWorkingCopyResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        if (target instanceof Organization) {
            Object elem =
                    PickerService
                            .getInstance()
                            .openSinglePickerDialog(XpdResourcesPlugin
                                    .getStandardDisplay().getActiveShell(),
                                    new PickerTypeQuery[] { new OMTypeQuery(
                                            OMTypeQuery.TYPE_ID_ORGANIZATION_TYPE) },
                                    null,
                                    null,
                                    null,
                                    new IFilter[] { new SameResourceFilter(
                                            target) });

            if (elem instanceof OrganizationType) {
                return SetCommand.create(editingDomain,
                        target,
                        OMPackage.eINSTANCE.getOrganization_OrganizationType(),
                        elem);
            }
        }
        return null;
    }

}
