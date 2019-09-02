package com.tibco.bds.designtime.validator.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.uml2.common.edit.command.ChangeCommand;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager.StereotypeKind;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Remove "searchable" property from array attribute.
 *
 * @author pwatson
 * @since 2 Sep 2019
 */
public class UnsetSearchableResolution extends AbstractWorkingCopyResolution implements IResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain, EObject target, IMarker marker)
            throws ResolutionException {

        if (!(target instanceof Property)) {
            return null;
        }

        final Property property = (Property) target;
        if (!BOMGlobalDataUtils.isSearchable(property)) {
            return null;
        }

        return new ChangeCommand(editingDomain, () -> {
            final Stereotype stereotype =
                    GlobalDataProfileManager.getInstance().getStereotype(StereotypeKind.SEARCHABLE);
            property.unapplyStereotype(stereotype);
        });
    }
}
