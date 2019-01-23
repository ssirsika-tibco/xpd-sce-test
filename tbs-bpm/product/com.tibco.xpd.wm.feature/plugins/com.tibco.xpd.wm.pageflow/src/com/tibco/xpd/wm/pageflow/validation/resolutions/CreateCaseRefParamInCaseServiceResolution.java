/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.wm.pageflow.validation.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.CaseService;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Member;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Resolution to create a formal parameter of type case service case class and
 * mode IN and add to the given case.
 * 
 * @author Ali
 * @since 13 Aug 2014
 */
public class CreateCaseRefParamInCaseServiceResolution extends
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

        if (target instanceof Process) {

            Process process = (Process) target;

            if (process != null) {

                CaseService caseService = getCaseService(process);
                if (caseService != null) {
                    ExternalReference externalRef =
                            EcoreUtil.copy(caseService.getCaseClassType());

                    if (externalRef != null) {

                        FormalParameter param =
                                Xpdl2Factory.eINSTANCE.createFormalParameter();
                        param.setMode(ModeType.IN_LITERAL);

                        EObject eo =
                                ProcessUIUtil
                                        .getReferencedClassifier(caseService
                                                .getCaseClassType(),
                                                WorkingCopyUtil
                                                        .getProjectFor(process));
                        Class caseClass = null;
                        if (eo instanceof Class) {

                            caseClass = (Class) eo;
                            param.setName(caseClass.getName() + "Ref"); //$NON-NLS-1$
                            Xpdl2ModelUtil.setOtherAttribute(param,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_DisplayName(),
                                    caseClass.getLabel() + "Ref"); //$NON-NLS-1$
                        }

                        RecordType caseRefType =
                                Xpdl2Factory.eINSTANCE.createRecordType();
                        Member member = Xpdl2Factory.eINSTANCE.createMember();
                        member.setExternalReference(externalRef);
                        caseRefType.getMember().add(member);
                        param.setDataType(caseRefType);

                        Command cmd =
                                AddCommand
                                        .create(editingDomain,
                                                process,
                                                Xpdl2Package.eINSTANCE
                                                        .getFormalParametersContainer_FormalParameters(),
                                                param);
                        return cmd;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 
     * @return case service set in the given process or null
     */
    public static CaseService getCaseService(Process process) {

        if (process != null) {
            Object caseService =
                    Xpdl2ModelUtil.getOtherElement(process,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_CaseService());
            if (caseService instanceof CaseService) {
                return (CaseService) caseService;
            }
        }
        return null;
    }
}
