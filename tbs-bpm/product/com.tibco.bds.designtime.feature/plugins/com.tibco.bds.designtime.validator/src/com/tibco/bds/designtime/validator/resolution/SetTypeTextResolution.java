package com.tibco.bds.designtime.validator.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.uml2.common.edit.command.ChangeCommand;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Set the attribute to text type
 *
 * @author aallway
 * @since 9 May 2019
 */
public class SetTypeTextResolution extends
        AbstractWorkingCopyResolution implements IResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        Command cmd = null;
        if (target instanceof Property) {
            final PrimitiveType textPrimitiveType =
                    PrimitivesUtil.getStandardPrimitiveTypeByName(
                            XpdResourcesPlugin.getDefault().getEditingDomain()
                                    .getResourceSet(),
                            PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);

            final Property prop = (Property) target;
            cmd = new ChangeCommand(editingDomain, new Runnable() {
                @Override
                public void run() {
                    prop.setType(textPrimitiveType);
                }
            });
        }
        return cmd;
    }
}
