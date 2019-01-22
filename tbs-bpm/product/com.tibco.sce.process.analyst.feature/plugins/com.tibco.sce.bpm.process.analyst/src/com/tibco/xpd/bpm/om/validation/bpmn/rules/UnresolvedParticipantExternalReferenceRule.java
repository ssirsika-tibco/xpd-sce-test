/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bpm.om.validation.bpmn.rules;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessOrgModelUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantsContainer;
import com.tibco.xpd.xpdl2.Process;

/**
 * Rules for participant organisation model references
 * 
 * 
 * @author aallway
 * @since 29 Jul 2011
 */
public class UnresolvedParticipantExternalReferenceRule extends
        PackageValidationRule {

    public static final String UNRESOLVED_DATATYPE_REFERENCE =
            "bpmn.unresolvedDataExternalReference"; //$NON-NLS-1$ 

    public static final String UNSET_DATATYPE_REFERENCE =
            "bpmn.unsetDataExternalReference"; //$NON-NLS-1$ 

    public static final String UNRESOLVED_ORGMODEL_REFERENCE =
            "bpmn.unresolvedParticipantExternalReference"; //$NON-NLS-1$ 

    public static final String UNSET_ORGMODEL_REFERENCE =
            "bpmn.unsetParticipantExternalReference"; //$NON-NLS-1$ 

    /**
     * @param pckg
     *            The package to check.
     * @see com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule#validate(com.tibco.xpd.xpdl2.Package)
     */
    @Override
    public void validate(Package pckg) {

        EList<Participant> participants = null;

        participants = pckg.getParticipants();
        validateParticipants(participants, pckg);

        List<Process> processes = pckg.getProcesses();
        for (Process process : processes) {
            participants = process.getParticipants();

            validateParticipants(participants, pckg);
        }
    }

    /**
     * @param formalParameterFields
     * @param process
     */
    private void validateParticipants(EList<Participant> participants,
            EObject container) {
        for (Participant data : participants) {
            validateParticipant(data, container);
        }
    }

    /**
     * 
     * @param data
     * @param parent
     */
    private void validateParticipant(Participant data, EObject parent) {
        ExternalReference externalReference = data.getExternalReference();
        if (externalReference != null) {

            if (isExternalReferenceSet(externalReference)) {
                if (ProcessOrgModelUtil
                        .getReferencedOrgModelEntityName((ParticipantsContainer) data
                                .eContainer(),
                                data) == null) {

                    List<String> msgs = new ArrayList<String>();
                    String location = externalReference.getLocation();
                    msgs.add(location != null ? location : ""); //$NON-NLS-1$
                    String xref = externalReference.getXref();
                    msgs.add(xref != null ? xref : ""); //$NON-NLS-1$

                    addIssue(UNRESOLVED_ORGMODEL_REFERENCE, data, msgs);
                }
            } else {
                // External ref has not been set
                addIssue(UNSET_ORGMODEL_REFERENCE, data);
            }
        }
    }

    /**
     * Check if the external reference is set.
     * 
     * @param ref
     * @return <code>true</code> if the location and xref values are set,
     *         <code>false</code> otherwise.
     */
    private boolean isExternalReferenceSet(ExternalReference ref) {
        return ref != null && ref.getLocation() != null
                && ref.getLocation().length() > 0 && ref.getXref() != null
                && ref.getXref().length() > 0;
    }

    /**
     * @return The project for the current input.
     */
    protected IProject getProject(Object input) {
        IProject project = null;
        if (input instanceof EObject) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor((EObject) input);

            // Sometimes (like new EObject Wizard) input Eobject is not yet
            // associated with working copy. in which case we should try getting
            // working copy for input container.
            if (wc == null) {
                EObject container = ((EObject) input).eContainer();
                if (container != null) {
                    wc = WorkingCopyUtil.getWorkingCopyFor(container);
                }
            }

            if (wc != null) {
                IResource resource = wc.getEclipseResources().get(0);
                if (resource != null) {
                    project = resource.getProject();
                }
            }
        }
        return project;
    }

}
