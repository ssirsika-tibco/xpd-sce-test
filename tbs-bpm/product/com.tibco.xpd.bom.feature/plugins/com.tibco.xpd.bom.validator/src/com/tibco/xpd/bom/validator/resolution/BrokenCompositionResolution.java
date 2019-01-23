/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.bom.validator.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * XPD-5089: certain very old bom models may have composition relationship via a
 * class having a property whose type is another class (no visual representation
 * of the relationship exists in the diagram editor) without having the
 * aggregation defined on the property in the model. This resolution fixes the
 * problem by adding aggregation=composite on that property in the model
 * 
 * @author bharge
 * @since 9 Jul 2013
 */
public class BrokenCompositionResolution extends AbstractWorkingCopyResolution {

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

        Command cmd = null;

        if (target instanceof Property) {

            final Property property = (Property) target;

            AggregationKind aggregation = property.getAggregation();

            if (AggregationKind.NONE_LITERAL.equals(aggregation)) {

                /*
                 * Do not change real associations, only owned attributes that
                 * have no association of any kind (an attribute defined by an
                 * association type link should_not_ have an aggregation
                 * attribute.
                 */
                if (property.getAssociation() == null) {
                    cmd =
                            new RecordingCommand(
                                    (TransactionalEditingDomain) editingDomain) {

                                @Override
                                protected void doExecute() {

                                    property.setAggregation(AggregationKind.COMPOSITE_LITERAL);
                                }
                            };
                }
            }
        }

        return cmd;
    }

}
