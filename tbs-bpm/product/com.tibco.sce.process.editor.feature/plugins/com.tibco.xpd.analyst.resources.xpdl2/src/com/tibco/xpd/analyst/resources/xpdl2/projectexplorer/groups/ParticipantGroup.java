package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorElementType;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Participants (logical) group of the Package or Process in the Project
 * Explorer tree
 * 
 * @author njpatel
 * 
 */
public class ParticipantGroup extends AbstractAssetGroup {

    private static final String TITLE = Messages.ParticipantGroup_TITLE;

    /**
     * Default constructor
     * 
     * @param parent
     */
    public ParticipantGroup(EObject parent) {
        super(parent, ProcessEditorElementType.participant);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.BpmArtefactGroup
     * #getFeature()
     */
    @Override
    public EStructuralFeature getFeature() {
        return Xpdl2Package.eINSTANCE.getParticipantsContainer_Participants();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.BpmArtefactGroup
     * #getText()
     */
    @Override
    public String getText() {
        return TITLE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.BpmArtefactGroup
     * #getImage()
     */
    @Override
    public Image getImage() {
        return Xpdl2ResourcesPlugin.getDefault().getImageRegistry()
                .get(Xpdl2ResourcesConsts.ICON_PARTICIPANT_GROUP);
    }

    @Override
    public EClass getReferenceType() {
        return Xpdl2Package.eINSTANCE.getParticipant();
    }

}
