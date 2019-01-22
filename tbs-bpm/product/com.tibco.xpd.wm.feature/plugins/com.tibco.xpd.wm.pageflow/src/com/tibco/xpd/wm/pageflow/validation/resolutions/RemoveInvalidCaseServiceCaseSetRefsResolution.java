/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.wm.pageflow.validation.resolutions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.CaseService;
import com.tibco.xpd.xpdExtension.VisibleForCaseStates;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Resolution to remove invalid case state references in the case service.
 * 
 * @author Ali
 * @since 13 Aug 2014
 */
public class RemoveInvalidCaseServiceCaseSetRefsResolution extends
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

            CaseService caseService = getCaseService(process);

            if (caseService != null) {

                ExternalReference caseClassRef = caseService.getCaseClassType();

                if (caseClassRef != null) {

                    EObject eo =
                            ProcessUIUtil.getReferencedClassifier(caseClassRef,

                            WorkingCopyUtil.getProjectFor(process));
                    Class caseClass = null;
                    if (eo instanceof Class) {

                        caseClass = (Class) eo;

                        Property caseClassCaseStateAttrib =
                                ProcessUIUtil.getCaseClassCaseState(caseClass);

                        if (caseClassCaseStateAttrib != null) {
                            VisibleForCaseStates caseServiceCaseStates =
                                    caseService.getVisibleForCaseStates();

                            Enumeration caseStateEnum =
                                    (Enumeration) caseClassCaseStateAttrib
                                            .getType();
                            EList<EnumerationLiteral> caseClassCaseStatesList =
                                    caseStateEnum.getOwnedLiterals();

                            List<ExternalReference> refsToRemove =
                                    new ArrayList<ExternalReference>();
                            if (caseServiceCaseStates != null) {

                                for (ExternalReference state : caseServiceCaseStates
                                        .getCaseState()) {

                                    /*
                                     * Check if the case state attrib exists in
                                     * the case class, if it doesn't then remove
                                     * it from the case service
                                     */
                                    boolean found = false;
                                    for (EnumerationLiteral caseClassEnumLit : caseClassCaseStatesList) {
                                        ExternalReference ref =
                                                ProcessUIUtil
                                                        .getExternalRefForEnumLit(caseService,
                                                                caseClassEnumLit);
                                        if (EcoreUtil.equals(ref, state)) {
                                            found = true;
                                            break;
                                        }
                                    }
                                    if (!found) {
                                        refsToRemove.add(state);
                                    }

                                }

                                CompoundCommand cmpCmd = new CompoundCommand();
                                for (ExternalReference ref : refsToRemove) {

                                    cmpCmd.append(RemoveCommand
                                            .create(editingDomain, ref));
                                }

                                return cmpCmd;
                            }
                        }
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
