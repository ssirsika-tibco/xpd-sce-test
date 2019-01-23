/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeExtPointHelper;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeReference;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Member;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.edit.ui.ComplexDataUIUtil;

/**
 * Resolution to selected external reference for the target data field /
 * parameter / type declaration.
 * 
 * 
 * @author aallway
 * @since 29 Jul 2011
 */
public class SelectDataExternalReferenceResolution extends
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
        if (target instanceof ProcessRelevantData) {
            ProcessRelevantData prd = (ProcessRelevantData) target;
            IProject project = WorkingCopyUtil.getProjectFor(prd);

            /*
             * XPD-5882: Saket: Need to distinguish between data that are case
             * ref and data that is BOM type.
             */
            DataType dt = prd.getDataType();

            if (dt != null) {
                if (dt instanceof RecordType) {

                    RecordType caseRefType = getCaseRefType(project);

                    if (caseRefType != null) {
                        return SetCommand.create(editingDomain,
                                prd,
                                Xpdl2Package.eINSTANCE
                                        .getProcessRelevantData_DataType(),
                                caseRefType);
                    } else {
                        return null;
                    }
                } else {

                    ComplexDataTypeReference complexDataTypeReference =
                            ComplexDataUIUtil
                                    .getComplexDataTypeReferenceFromPicker(Display
                                            .getCurrent().getActiveShell(),
                                            project,
                                            ComplexDataTypeExtPointHelper
                                                    .getAllComplexDataTypesMergedInfo(),
                                            null,
                                            false);
                    if (complexDataTypeReference != null) {

                        ExternalReference extReference =
                                getExternalRef(complexDataTypeReference);

                        return SetCommand.create(editingDomain,
                                prd,
                                Xpdl2Package.eINSTANCE
                                        .getProcessRelevantData_DataType(),
                                extReference);
                    }
                }
            }
        } else if (target instanceof TypeDeclaration) {
            TypeDeclaration typeDeclaration = (TypeDeclaration) target;
            IProject project = WorkingCopyUtil.getProjectFor(typeDeclaration);

            /*
             * XPD-5882: Saket: Need to distinguish between data that are case
             * ref and data that is BOM type.
             */
            RecordType recType = typeDeclaration.getRecordType();

            if (recType != null) {
                RecordType caseRefType = getCaseRefType(project);

                return SetCommand.create(editingDomain,
                        typeDeclaration,
                        Xpdl2Package.eINSTANCE.getTypeDeclaration_RecordType(),
                        caseRefType);
            } else {
                ComplexDataTypeReference complexDataTypeReference =
                        ComplexDataUIUtil
                                .getComplexDataTypeReferenceFromPicker(Display
                                        .getCurrent().getActiveShell(),
                                        project,
                                        ComplexDataTypeExtPointHelper
                                                .getAllComplexDataTypesMergedInfo(),
                                        null,
                                        false);
                if (complexDataTypeReference != null) {
                    ExternalReference extReference =
                            getExternalRef(complexDataTypeReference);
                    return SetCommand.create(editingDomain,
                            typeDeclaration,
                            Xpdl2Package.eINSTANCE
                                    .getTypeDeclaration_ExternalReference(),
                            extReference);
                }
            }
        }
        return null;
    }

    /**
     * Convert a complex data type extension point reference to an xpdl2
     * External reference.
     * 
     * @param extRef
     * @return
     */
    private ExternalReference complexDataTypeRefToXpdl2Ref(
            ComplexDataTypeReference reference) {

        ExternalReference extReference =
                Xpdl2Factory.eINSTANCE.createExternalReference();

        extReference.setLocation(reference.getLocation());
        extReference.setXref(reference.getXRef());
        extReference.setNamespace(reference.getNameSpace());

        return extReference;

    }

    /**
     * Returns the selected case ref type.
     * 
     * @param project
     * @return caseRefType
     */
    private RecordType getCaseRefType(IProject project) {
        Member extRefMember = null;
        ExternalReference extReference = null;
        RecordType caseRefType = Xpdl2Factory.eINSTANCE.createRecordType();

        ComplexDataTypeReference complexDataTypeReference =
                ComplexDataUIUtil.getComplexDataTypeReferenceFromPicker(Display
                        .getCurrent().getActiveShell(),
                        project,
                        ComplexDataTypeExtPointHelper
                                .getAllComplexDataTypesMergedInfo(),
                        null,
                        true);
        if (complexDataTypeReference != null) {
            extReference =
                    complexDataTypeRefToXpdl2Ref(complexDataTypeReference);

        } else {
            return null;
        }

        EList<Member> member = caseRefType.getMember();

        if (member.size() > 0) {
            extRefMember = member.get(0);
        } else {
            extRefMember = Xpdl2Factory.eINSTANCE.createMember();
        }

        if (null != extRefMember) {
            extRefMember.setExternalReference(extReference);
            caseRefType.getMember().add(extRefMember);
        }

        return caseRefType;
    }

    /**
     * Returns the selected external reference.
     * 
     * @param complexDataTypeReference
     * @return extReference
     */
    private ExternalReference getExternalRef(
            ComplexDataTypeReference complexDataTypeReference) {
        ExternalReference extReference =
                Xpdl2Factory.eINSTANCE.createExternalReference();
        extReference.setLocation(complexDataTypeReference.getLocation());
        extReference.setXref(complexDataTypeReference.getXRef());
        extReference.setNamespace(complexDataTypeReference.getNameSpace());

        return extReference;
    }

}
