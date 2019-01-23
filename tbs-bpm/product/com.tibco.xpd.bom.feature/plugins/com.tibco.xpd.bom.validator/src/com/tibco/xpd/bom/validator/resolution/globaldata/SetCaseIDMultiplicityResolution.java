/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.bom.validator.resolution.globaldata;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * This resolution will be used for CaseId/CID and AutoCaseId/AutoCID to set the
 * mandatory applicable multiplicity, which is 1 for CaseID and 0..1 for
 * AutoCaseID.
 * 
 * @author aprasad
 * @since 3 Jun 2013
 */
public class SetCaseIDMultiplicityResolution extends
        AbstractWorkingCopyResolution {

    private static final Integer CID_MULTIPLICITY_BOUND = new Integer(1);

    private static final Integer AUTO_CID_MULTIPLICITY_BOUND_U = new Integer(1);

    private static final Integer AUTO_CID_MULTIPLICITY_BOUND_L = new Integer(0);

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
        if (target instanceof Property) {
            Property prop = (Property) target;
            GlobalDataProfileManager gdProfileManager =
                    GlobalDataProfileManager.getInstance();
            if (gdProfileManager.isAutoCaseIdentifier(prop)) {
                CompoundCommand cmd = new CompoundCommand();
                cmd.append(SetCommand.create(editingDomain,
                        prop,
                        UMLPackage.eINSTANCE.getMultiplicityElement_Lower(),
                        AUTO_CID_MULTIPLICITY_BOUND_L));
                cmd.append(SetCommand.create(editingDomain,
                        prop,
                        UMLPackage.eINSTANCE.getMultiplicityElement_Upper(),
                        AUTO_CID_MULTIPLICITY_BOUND_U));
                return cmd;
            } else if (gdProfileManager.isCID(prop)
                    || gdProfileManager.isCompositeCaseIdentifier(prop)) {
                CompoundCommand cmd = new CompoundCommand();
                cmd.append(SetCommand.create(editingDomain,
                        prop,
                        UMLPackage.eINSTANCE.getMultiplicityElement_Lower(),
                        CID_MULTIPLICITY_BOUND));
                cmd.append(SetCommand.create(editingDomain,
                        prop,
                        UMLPackage.eINSTANCE.getMultiplicityElement_Upper(),
                        CID_MULTIPLICITY_BOUND));
                return cmd;
            }
        }

        return null;
    }

}
