package com.tibco.xpd.analyst.resources.xpdl2.wizards.newparticipant;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.wizards.pages.AbstractSpecialFolderFileSelectionPage;
import com.tibco.xpd.analyst.resources.xpdl2.wizards.pages.PackageOrProcessSelectionPage;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.ParticipantTypeElem;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * New Participant wizard
 * 
 * @author njpatel
 */
public class NewBPMParticipantWizard extends AbstractNewParticipantWizard {

    /*
     * Predefined participant id to distinguish between new participant for
     * decision flow file and new participant for xpdl file. (Similar such
     * constant is defined in NewDecisionsParticipantWizard for uniqueness in
     * decision flow file)
     */
    public static final String NEW_BPM_PARTICIPANT_ID =
            "$$_NEW_BPM_PARTICIPANT_ID_$$"; //$NON-NLS-1$

    @Override
    protected EObject getEContainer(
            AbstractSpecialFolderFileSelectionPage locationPage) {
        return locationPage.getEContainer();
    }

    @Override
    protected void addExtraContainerPageListeners(
            AbstractSpecialFolderFileSelectionPage containerSelectionPage) {
        ((PackageOrProcessSelectionPage) containerSelectionPage)
                .addProcessOrInterfaceModifyListeners(getTxtModifyListener());
    }

    @Override
    protected AbstractSpecialFolderFileSelectionPage getFileSelectionPage() {
        return new PackageOrProcessSelectionPage("process.participant.wizard"); //$NON-NLS-1$
    }

    @Override
    public EObject createTemplate() {
        Xpdl2Factory fact = Xpdl2Factory.eINSTANCE;
        Participant input = fact.createParticipant();
        input.setName(Messages.NewParticipantWizard_3);
        Xpdl2ModelUtil
                .setOtherAttribute(input,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_DisplayName(),
                        Messages.NewParticipantWizard_3);
        // Set role participant type
        ParticipantTypeElem typeElem = fact.createParticipantTypeElem();
        typeElem.setType(ParticipantType.ROLE_LITERAL);

        input.setParticipantType(typeElem);

        /*
         * PropertySection contribution filter needs to be able to distinguish
         * between new participant for decision flow file and new participant
         * for normal xpdl.
         * 
         * The filter usually works on whether the participant is in a dflow
         * xpdl model but new participants are in the model yet!.
         * 
         * So we set a known predefined ID on the participant here so that the
         * filter can detect that and filter in participants with this ID..
         * 
         * Then just before we create the comman dwe will set it to something
         * unique again.
         */

        input.eSet(Xpdl2Package.eINSTANCE.getUniqueIdElement_Id(),
                NEW_BPM_PARTICIPANT_ID);

        return input;
    }
}