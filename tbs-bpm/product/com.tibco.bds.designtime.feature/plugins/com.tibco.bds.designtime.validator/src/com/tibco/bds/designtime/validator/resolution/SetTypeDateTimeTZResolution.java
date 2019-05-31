package com.tibco.bds.designtime.validator.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.uml2.common.edit.command.ChangeCommand;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Set the attribute to text type
 *
 * @author pwatson
 * @since 31 May 2019
 */
public class SetTypeDateTimeTZResolution extends
        AbstractWorkingCopyResolution implements IResolution {

    private final PrimitiveType dateTimeTzType;

    public SetTypeDateTimeTZResolution() {
        dateTimeTzType = PrimitivesUtil.getStandardPrimitiveTypeByName(
                XpdResourcesPlugin.getDefault().getEditingDomain()
                        .getResourceSet(),
                PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME);
    }
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            final EObject target, IMarker marker) throws ResolutionException {

        if (target instanceof Property) {
            return new ChangeCommand(editingDomain, new Runnable() {
                @Override
                public void run() {
                    ((Property) target).setType(dateTimeTzType);
                }
            });
        }

        if (target instanceof PrimitiveType) {
            // create a super-class reference to date-time-tz
            Generalization gen = UMLFactory.eINSTANCE.createGeneralization();
            gen.setGeneral(dateTimeTzType);
            EList<Generalization> genList = new BasicEList<Generalization>(1);
            genList.add(gen);

            // set the super-class of the primitive type
            return SetCommand.create(editingDomain,
                    target,
                    UMLPackage.eINSTANCE.getClassifier_Generalization(),
                    genList);
        }
        return null;
    }
}
