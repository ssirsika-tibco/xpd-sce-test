package com.tibco.xpd.bom.validator.resolution.xsd;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.common.edit.command.ChangeCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Stereotype;

import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * A resolution that removes the default value from a PrimitiveType
 * 
 * @author smorgan
 * 
 */
public class DefaultValueRemovalResolution extends
        AbstractWorkingCopyResolution implements IResolution {

    private static final String RESTRICTED_TYPE_STEREOTYPE_QNAME =
            "PrimitiveTypeFacets::RestrictedType"; //$NON-NLS-1$

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        final PrimitiveType pt = (PrimitiveType) target;
        final Stereotype st =
                pt.getAppliedStereotype(RESTRICTED_TYPE_STEREOTYPE_QNAME);
        ChangeCommand cmd = new ChangeCommand(editingDomain, new Runnable() {
            public void run() {
                // Given that changing a PT's type doesn't flush out the
                // redundant values for other types, it seems reasonable to
                // clear them all here.
                pt.setValue(st, "textDefaultValue", null); //$NON-NLS-1$
                pt.setValue(st, "integerDefaultValue", null); //$NON-NLS-1$
                pt.setValue(st, "dateTimeDefaultValue", null); //$NON-NLS-1$
                pt.setValue(st, "dateDefaultValue", null); //$NON-NLS-1$
                pt.setValue(st, "timeDefaultValue", null); //$NON-NLS-1$
                pt.setValue(st, "booleanDefaultValue", null); //$NON-NLS-1$
                pt.setValue(st, "decimalDefaultValue", null); //$NON-NLS-1$
                // TODO This is a temporary hack to force
                // revalidation of the BOM. Need to find out the proper
                // way to do this.
                String name = pt.getName();
                pt.setName("temp"); //$NON-NLS-1$
                pt.setName(name);
            }
        });
        return cmd;
    }

}
