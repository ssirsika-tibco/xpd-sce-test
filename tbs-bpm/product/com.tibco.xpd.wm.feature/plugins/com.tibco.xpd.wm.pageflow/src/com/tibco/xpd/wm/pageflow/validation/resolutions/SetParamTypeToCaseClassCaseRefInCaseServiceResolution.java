/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.wm.pageflow.validation.resolutions;

import java.util.Properties;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validation.rules.ValidationUtil;
import com.tibco.xpd.wm.pageflow.validation.rules.CaseServiceValidationRules;
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
 * Resolution to set case service parameter type to case class case reference
 * 
 * @author Ali
 * @since 13 Aug 2014
 */
public class SetParamTypeToCaseClassCaseRefInCaseServiceResolution extends
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

        if (target instanceof FormalParameter) {

            FormalParameter param = (FormalParameter) target;

            Process process = Xpdl2ModelUtil.getProcess(param);

            if (process != null) {

                CaseService caseService = getCaseService(process);
                if (caseService != null) {
                    ExternalReference externalRef =
                            EcoreUtil.copy(caseService.getCaseClassType());

                    if (externalRef != null) {
                        RecordType caseRefType =
                                Xpdl2Factory.eINSTANCE.createRecordType();
                        Member member = Xpdl2Factory.eINSTANCE.createMember();
                        member.setExternalReference(externalRef);
                        caseRefType.getMember().add(member);
                        CompoundCommand cmpCmd = new CompoundCommand();
                        cmpCmd.append(SetCommand.create(editingDomain,
                                param,
                                Xpdl2Package.eINSTANCE
                                        .getProcessRelevantData_DataType(),
                                caseRefType));

                        /* if param mode is not IN, set it to IN */
                        if (ModeType.IN != param.getMode().getValue()) {
                            cmpCmd.append(SetCommand.create(editingDomain,
                                    param,
                                    Xpdl2Package.eINSTANCE
                                            .getFormalParameter_Mode(),
                                    ModeType.IN_LITERAL));
                        }

                        return cmpCmd;
                    }
                }

            }
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#getResolutionLabel(java.lang.String,
     *      org.eclipse.core.resources.IMarker)
     * 
     * @param propertiesLabel
     * @param marker
     * @return
     */
    @Override
    protected String getResolutionLabel(String propertiesLabel, IMarker marker) {

        if (propertiesLabel != null) {

            Properties addInfo = ValidationUtil.getAdditionalInfo(marker);
            if (addInfo != null) {
                String paramLabel =
                        addInfo.getProperty(CaseServiceValidationRules.PARAM_LABEL_ADDITIONAL_INFO_KEY);
                String paramType =
                        addInfo.getProperty(CaseServiceValidationRules.PARAM_TYPE_ADDITIONAL_INFO_KEY);

                if (paramLabel != null && paramType != null) {
                    return String
                            .format(propertiesLabel, paramLabel, paramType);
                }
            }
        }
        return super.getResolutionLabel(propertiesLabel, marker);
    }

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#getResolutionDescription(java.lang.String,
     *      org.eclipse.core.resources.IMarker)
     * 
     * @param propertiesDescription
     * @param marker
     * @return
     */
    @Override
    protected String getResolutionDescription(String propertiesDescription,
            IMarker marker) {

        return getResolutionLabel(propertiesDescription, marker);
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
