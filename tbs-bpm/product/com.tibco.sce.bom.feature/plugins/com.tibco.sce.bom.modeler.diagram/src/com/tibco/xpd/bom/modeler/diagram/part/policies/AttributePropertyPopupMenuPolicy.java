/*
 * Copyright (c) TIBCO Software Inc 2014. All rights reserved.
 */

package com.tibco.xpd.bom.modeler.diagram.part.policies;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.ui.services.action.contributionitem.IPopupMenuContributionPolicy;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.PropertyEditPart;

/**
 * Checks if the right click menu on a property should contain the convert to
 * Attribute entry
 * 
 */
public class AttributePropertyPopupMenuPolicy implements
        IPopupMenuContributionPolicy {

    /**
     * @see org.eclipse.gmf.runtime.common.ui.services.action.contributionitem.IPopupMenuContributionPolicy#appliesTo(org.eclipse.jface.viewers.ISelection,
     *      org.eclipse.core.runtime.IConfigurationElement)
     * 
     * @param selection
     * @param configuration
     * @return
     */
    @Override
    public boolean appliesTo(ISelection selection,
            IConfigurationElement configuration) {
        // Check everything in the selection to ensure that ALL the selection is
        // valid for this operation
        if (selection instanceof StructuredSelection) {
            StructuredSelection elements = ((StructuredSelection) selection);
            for (Object object : elements.toList()) {
                if ((object != null) && (object instanceof PropertyEditPart)) {
                    PropertyEditPart editPart = (PropertyEditPart) object;
                    EObject toMutateObject = editPart.resolveSemanticElement();

                    if ((toMutateObject == null)
                            || !(toMutateObject instanceof Property)) {
                        return false;
                    }

                    Property toMutateProperty = (Property) toMutateObject;
                    // If the global data profile is not applied, don't show the
                    // convert
                    if (!BOMGlobalDataUtils
                            .hasGlobalDataProfile(toMutateProperty.getModel())) {
                        return false;
                    }

                    // Check to see if this property is a case state or case
                    // identifier, if it is not then we do not want to show the
                    // option to change it to an attribute
                    if (!GlobalDataProfileManager.getInstance()
                            .isCaseState(toMutateProperty)
                            && !GlobalDataProfileManager.getInstance()
                                    .isCID(toMutateProperty)
                            && !GlobalDataProfileManager.getInstance()
                                    .isAutoCaseIdentifier(toMutateProperty)
                            && !GlobalDataProfileManager
                                    .getInstance()
                                    .isCompositeCaseIdentifier(toMutateProperty)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}