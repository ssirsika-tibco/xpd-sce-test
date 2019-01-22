/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import java.util.Properties;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.validation.bpmn.rules.UnresolvedDataExternalReferenceRule;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validation.rules.ValidationUtil;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Resolution to fix any unresolved generated BOM data type references.
 * 
 * Currently this handles any external references that point to a uri with _type
 * that comes from Studio 352
 * 
 * 
 * @author bharge
 * @since 16 Oct 2012
 */
public class RepairGeneratedBOMReference extends AbstractWorkingCopyResolution {

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

        Properties props = ValidationUtil.getAdditionalInfo(marker);
        if (null != props) {

            String xref =
                    props.getProperty(UnresolvedDataExternalReferenceRule.UNRESOLVEDCOMPLEXDATAFOR_TYPE);

            if (null != xref) {
                ExternalReference extRef = null;

                if (target instanceof ProcessRelevantData) {
                    if (((ProcessRelevantData) target).getDataType() instanceof ExternalReference) {
                        extRef =
                                (ExternalReference) ((ProcessRelevantData) target)
                                        .getDataType();
                    }
                } else if (target instanceof TypeDeclaration) {
                    if (((TypeDeclaration) target).getExternalReference() != null) {
                        extRef =
                                ((TypeDeclaration) target)
                                        .getExternalReference();
                    }
                }

                if (null != extRef) {
                    return SetCommand.create(editingDomain,
                            extRef,
                            Xpdl2Package.eINSTANCE.getExternalReference_Xref(),
                            xref);
                }
            }
        }
        return null;
    }
}
