/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import java.util.Properties;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.validation.bpmn.rules.UnresolvedDataExternalReferenceRule;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validation.rules.ValidationUtil;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Member;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Sets the Namespace of the external reference to that of the referenced BOM.
 * 
 * @author kthombar
 * @since 05-Mar-2014
 */
public class SetCorrectExternalRefNamespaceResolution extends
        AbstractWorkingCopyResolution {

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

        Properties property = ValidationUtil.getAdditionalInfo(marker);

        if (property != null) {

            String namespaceURI =
                    property.getProperty(UnresolvedDataExternalReferenceRule.INCORRECT_NAMESPACE_ON_EXT_REF);
            if (namespaceURI != null) {

                ExternalReference externalRef = null;
                if (target instanceof ProcessRelevantData) {

                    ProcessRelevantData pRD = (ProcessRelevantData) target;
                    if (pRD.getDataType() instanceof ExternalReference) {

                        externalRef = (ExternalReference) pRD.getDataType();
                    } else if (pRD.getDataType() instanceof RecordType) {

                        EList<Member> member =
                                ((RecordType) pRD.getDataType()).getMember();
                        if (member.get(0) != null) {

                            externalRef = member.get(0).getExternalReference();
                        }
                    }
                } else if (target instanceof TypeDeclaration) {

                    TypeDeclaration typeDeclare = (TypeDeclaration) target;
                    if (typeDeclare.getExternalReference() != null) {

                        externalRef = typeDeclare.getExternalReference();
                    } else if (typeDeclare.getRecordType() != null) {

                        EList<Member> member =
                                typeDeclare.getRecordType().getMember();
                        if (member.get(0) != null) {

                            externalRef = member.get(0).getExternalReference();
                        }
                    }
                }
                if (externalRef != null) {

                    return SetCommand.create(editingDomain,
                            externalRef,
                            Xpdl2Package.eINSTANCE
                                    .getExternalReference_Namespace(),
                            namespaceURI);
                }
            }
        }
        return null;
    }
}
