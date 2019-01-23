package com.tibco.xpd.bpm.om;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessOrgModelUtil;
import com.tibco.xpd.bpm.om.internal.Messages;
import com.tibco.xpd.om.core.om.ModelElement;
import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.util.OMUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantsContainer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

public class BPMProcessOrgModelUtil extends ProcessOrgModelUtil {

    private static final String DEFAULT_PARTICIPANT_NAME_PATTERN =
            Messages.ParticipantPropertySection_ParticipantName_value + "\\d*"; //$NON-NLS-1$

    /**
     * @param orgModelObject
     * @return
     */
    public static Command getSetOrgModelParticipantRefCommand(
            EditingDomain editingDomain,
            ParticipantsContainer participantContainer,
            Participant participant, EObject orgModelObject) {
        CompoundCommand command = null;

        try {
            String previousExternalRefName = null;

            ExternalReference externalReference =
                    participant.getExternalReference();
            if (externalReference != null) {
                previousExternalRefName =
                        getOrgModelElementDisplayName(participantContainer,
                                participant);
                ProcessOrgModelUtil
                        .getReferencedOrgModelEntitySimpleName(participantContainer,
                                participant);
            }

            if (previousExternalRefName == null) {
                previousExternalRefName = ""; //$NON-NLS-1$
            }

            String uniqueID = OMUtil.getID(orgModelObject);

            EObject refObject = OMUtil.getEObjectByID(uniqueID);
            String orgModelRefdisplayName = ""; //$NON-NLS-1$

            if (refObject instanceof NamedElement) {
                NamedElement namedElement = (NamedElement) refObject;
                orgModelRefdisplayName = namedElement.getDisplayName();
            }

            command = new CompoundCommand();
            command.setLabel(Messages.ParticipantPropertySection_SetExternalReference_menu);
            if (refObject != null && refObject.eResource() != null
                    && refObject.eResource().getURI() != null) {
                command.append(SetCommand.create(editingDomain,
                        externalReference,
                        Xpdl2Package.eINSTANCE.getExternalReference_Location(),
                        refObject.eResource().getURI().lastSegment()));
            }
            command.append(SetCommand.create(editingDomain,
                    externalReference,
                    Xpdl2Package.eINSTANCE.getExternalReference_Namespace(),
                    OMPackage.eNS_URI));
            command.append(SetCommand.create(editingDomain,
                    externalReference,
                    Xpdl2Package.eINSTANCE.getExternalReference_Xref(),
                    uniqueID));

            // if participant is still at default name then we set
            // the display name/ name as the organisation one
            if (orgModelRefdisplayName != null
                    && orgModelRefdisplayName.length() > 0) {

                String displayName = Xpdl2ModelUtil.getDisplayName(participant);
                Pattern pattern =
                        Pattern.compile(DEFAULT_PARTICIPANT_NAME_PATTERN);
                Matcher matcher = pattern.matcher(displayName);

                boolean isDefaultName = matcher.matches();

                // Also, if current display name matches the
                // existing external reference name (or index
                // suffixed thereof) then change it's name.
                boolean isDefaultNameForExtRef = false;

                if (previousExternalRefName != null
                        && previousExternalRefName.length() > 0) {
                    Pattern pattern2 =
                            Pattern.compile(previousExternalRefName + "\\d*"); //$NON-NLS-1$
                    Matcher matcher2 = pattern2.matcher(displayName);

                    isDefaultNameForExtRef = matcher2.matches();
                }

                if (isDefaultName || isDefaultNameForExtRef) {
                    // Ensure that the token name we're proposing is
                    // unique.
                    int nameIndex = 1;

                    Set<String> existingParticTokenNames =
                            new HashSet<String>();
                    Set<String> existingParticDisplayNames =
                            new HashSet<String>();

                    if (participantContainer instanceof Process) {
                        //
                        // Participant in process must be unique
                        // within that process participants AND package
                        // participants.
                        for (Participant p : ((Process) participantContainer)
                                .getParticipants()) {
                            existingParticTokenNames.add(p.getName());
                            existingParticDisplayNames.add(Xpdl2ModelUtil
                                    .getDisplayName(p));
                        }

                        for (Participant p : ((Process) participantContainer)
                                .getPackage().getParticipants()) {
                            existingParticTokenNames.add(p.getName());
                            existingParticDisplayNames.add(Xpdl2ModelUtil
                                    .getDisplayName(p));
                        }
                    } else if (participantContainer instanceof Package) {
                        // participant in package must be unique in
                        // package participants and ALL processes.
                        for (Participant p : ((Package) participantContainer)
                                .getParticipants()) {
                            existingParticTokenNames.add(p.getName());
                            existingParticDisplayNames.add(Xpdl2ModelUtil
                                    .getDisplayName(p));
                        }

                        for (Process proc : ((Package) participantContainer)
                                .getProcesses()) {
                            for (Participant p : proc.getParticipants()) {
                                existingParticTokenNames.add(p.getName());
                                existingParticDisplayNames.add(Xpdl2ModelUtil
                                        .getDisplayName(p));
                            }
                        }
                    }

                    String newParticTokenName = orgModelRefdisplayName;

                    String indexedName = newParticTokenName;

                    while (true) {
                        if (!existingParticTokenNames.contains(indexedName)
                                && !existingParticDisplayNames
                                        .contains(indexedName)) {
                            newParticTokenName = indexedName;
                            break;
                        }

                        nameIndex++;
                        indexedName = newParticTokenName + nameIndex;
                    }

                    command.append(Xpdl2ModelUtil
                            .getSetOtherAttributeCommand(editingDomain,
                                    participant,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_DisplayName(),
                                    newParticTokenName));
                    command.append(SetCommand.create(editingDomain,
                            participant,
                            Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                            newParticTokenName));
                }
            }

        } catch (IllegalArgumentException e) {
        }
        return command;
    }

    private static String getOrgModelElementDisplayName(
            ParticipantsContainer participantsContainer, Participant participant) {
        String displayName = null;

        ExternalReference externalReference =
                participant.getExternalReference();
        if (externalReference != null) {
            try {
                displayName =
                        ProcessOrgModelUtil
                                .getReferencedOrgModelEntitySimpleName(participantsContainer,
                                        participant);
            } catch (IllegalArgumentException e) {
            }
        }

        return displayName;
    }

    /**
     * Resolves and gets EObject from xpdl participant (if it is an extenal
     * reference type).
     * 
     * @param participant
     * 
     * @return The org model element referenced by the given participant or
     *         <code>null</code> if either the participant is NOT an external
     *         reference type or teh referenced org model entity cannot be
     *         found.
     */
    static public ModelElement getOMModelElement(Participant participant) {
        ExternalReference externalReference =
                participant.getExternalReference();
        if (externalReference != null) {
            return getOMModelElement(externalReference);
        }
        return null;
    }

    /**
     * Resolves and gets EObject from xpdl external reference.
     * 
     * @param externalReference
     *            The reference of the object.
     * @return the referenced Element in form of ModelElement.
     */
    static public ModelElement getOMModelElement(
            ExternalReference externalReference) {

        // Make sure there is actually an external reference specified
        if ((externalReference.getLocation() == null)
                || (externalReference.getXref() == null)) {
            return null;
        }

        ModelElement omModelElement = null;

        IProject project =
                WorkspaceSynchronizer.getFile(externalReference.eResource())
                        .getProject();

        // Get the special folder for the Org Model we are using
        IFile file =
                SpecialFolderUtil.resolveSpecialFolderRelativePath(project,
                        OMUtil.OM_SPECIAL_FOLDER_KIND,
                        URI.decode(externalReference.getLocation()),
                        true);

        if (file != null) {
            WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(file);
            if (workingCopy.getRootElement() != null) {
                Resource resource = workingCopy.getRootElement().eResource();

                EObject refObj =
                        resource.getEObject(externalReference.getXref());
                if (refObj instanceof ModelElement) {
                    omModelElement = (ModelElement) refObj;
                }
            }
        }

        return omModelElement;
    }

    /**
     * Finds the major version number for the Organisational model for a given
     * element
     * 
     * @param omModelElement
     *            Model Element to find the version of
     * @return Major Version number
     */
    static public Integer getOMModelVersion(ModelElement omModelElement) {
        // Set the default version to not set - DE will auto select latest if
        // required
        Integer version = null;

        // Find the OrgModel object
        EObject modelObject = omModelElement.eContainer();
        while ((modelObject != null) && !(modelObject instanceof OrgModel)) {
            modelObject = modelObject.eContainer();
        }

        // Check if the OrgModel object was found
        if (modelObject != null) {
            String versionStr = ((OrgModel) modelObject).getVersion();
            // If this is a multiple-part version then we only want the major
            // part
            // e.g 1.2.3 (so require only 1 in this instance)
            if (versionStr.contains(".")) {
                versionStr = versionStr.substring(0, versionStr.indexOf("."));
            }
            try {
                version = new Integer(versionStr);
            } catch (NumberFormatException e) {
                // Version number is not a number for some reason, this should
                // have been caught in the studio validations, set to null
                // so the version number is auto-selected by DE
                version = null;
            }
        }

        return version;
    }

}
