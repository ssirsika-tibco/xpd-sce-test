/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.bom.validator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.bom.validator.IBOMValidationPreferenceManager.ValidationDestination;
import com.tibco.xpd.validation.destinations.Destination;

/**
 * WSDL are transformed to BOM. However, there are a few cases when these WSDLs
 * have to marked as errors. This scope provider enabled validation of WSDLs
 * 
 * @author rsomayaj
 * @since 3.3 (11 Mar 2010)
 */
public class WSDLtoBOMScopeProvider extends BOMScopeProvider {

    final static String WSDL_BOM_GEN_NATURE_ID =
            "com.tibco.xpd.wsdltobom.wsdlBomNature"; //$NON-NLS-1$

    final static String BOM_GEN_NATURE_ID =
            "com.tibco.xpd.bom.gen.bomGenNature"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.bom.validator.BOMScopeProvider#include(org.eclipse.emf.ecore.EObject)
     * 
     * @param eo
     * @return
     */
    @Override
    protected boolean include(EObject eo) {
        return true;
    }

    /**
     * Validation provider which restricts validation only if the validation
     * component is contained the project worked on.
     */
    @Override
    protected boolean isValidationEnabled(IProject project,
            Destination destination) {
        /* Is destination enabled normally */
        boolean enabled = super.isValidationEnabled(project, destination);
        if (enabled) {
            if (BOMValidatorActivator.getDefault() != null) {
                /* Re-check including project preference override setting */
                enabled =
                        BOMValidatorActivator.getDefault()
                                .isValidationDestinationEnabled(project,
                                        ValidationDestination.WSDL_TO_BOM
                                                .getDestinationId());
            }
            if (enabled) {
                /*
                 * XPD-5879:(we do not want wsdl->bom validations for SOA
                 * projects). Finally if validations are still enabled, check if
                 * the project has 'Wsdl to BOM' and 'BOM' generation nature.
                 */
                enabled = hasWsdlBomGenNature(project);
            }
        }
        return enabled;
    }

    /**
     * Return <code>true</code> if the project has both 'BOM' and 'Wsdl to BOM'
     * generation nature, else return <code>false</code>.
     * 
     * @param project
     *            {@link IProject} whose nature is to be tested.
     * @return
     */
    private boolean hasWsdlBomGenNature(IProject project) {
        try {
            if (project.isAccessible()) {
                return project.hasNature(WSDL_BOM_GEN_NATURE_ID)
                        && project.hasNature(BOM_GEN_NATURE_ID);
            }
        } catch (CoreException e) {
            BOMValidatorActivator.getLogger().error(e);
        }
        return false;
    }
}
