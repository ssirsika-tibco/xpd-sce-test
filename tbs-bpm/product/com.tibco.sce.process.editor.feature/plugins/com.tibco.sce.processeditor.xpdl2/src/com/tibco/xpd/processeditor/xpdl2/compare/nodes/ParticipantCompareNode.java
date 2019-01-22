/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.compare.nodes;

import java.util.Collection;

import org.eclipse.emf.ecore.EStructuralFeature;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessOrgModelUtil;
import com.tibco.xpd.processeditor.xpdl2.compare.Messages;
import com.tibco.xpd.processeditor.xpdl2.compare.nodes.base.ProcessDescendentNamedElementCompareNode;
import com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory;
import com.tibco.xpd.resources.ui.compare.nodes.XpdPropertyInfoNode;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.ParticipantsContainer;

/**
 * Participant compare node
 * 
 * @author aallway
 * @since 29 Nov 2010
 */
public class ParticipantCompareNode extends
        ProcessDescendentNamedElementCompareNode {

    private Participant participant;

    /**
     * @param participant
     * @param listIndex
     * @param feature
     * @param parentCompareNode
     * @param compareNodeFactory
     */
    public ParticipantCompareNode(Participant participant, int listIndex,
            EStructuralFeature feature, Object parentCompareNode,
            EMFCompareNodeFactory compareNodeFactory) {
        super(participant, listIndex, feature, parentCompareNode,
                compareNodeFactory);

        this.participant = participant;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.compare.nodes.base.NamedElementCompareNode#createInfoPropertyChildren()
     * 
     * @return
     */
    @Override
    protected Collection<XpdPropertyInfoNode> createInfoPropertyChildren() {
        Collection<XpdPropertyInfoNode> props =
                super.createInfoPropertyChildren();

        /* Add the type as info. */
        String type = null;

        if (participant.getParticipantType() != null) {

            ParticipantType participantType =
                    participant.getParticipantType().getType();

            switch (participantType.getValue()) {
            case ParticipantType.HUMAN:
                type = Messages.ParticipantCompareNode_Human_label;
                break;
            case ParticipantType.ROLE:
                type = Messages.ParticipantCompareNode_Role_label;
                break;
            case ParticipantType.SYSTEM:
                type = Messages.ParticipantCompareNode_System_label;
                break;
            case ParticipantType.ORGANIZATIONAL_UNIT:
                type = Messages.ParticipantCompareNode_OrgUnit_label;
                break;
            case ParticipantType.RESOURCE_SET:
                type = Messages.ParticipantCompareNode_OrgModelQuery_label;
                break;

            case ParticipantType.RESOURCE:
                type = Messages.ParticipantCompareNode_OrgModelRef_label;
                String displayName = null;

                ExternalReference externalReference =
                        participant.getExternalReference();
                if (externalReference != null) {
                    try {
                        displayName =
                                ProcessOrgModelUtil
                                        .getReferencedOrgModelEntitySimpleName((ParticipantsContainer) participant
                                                .eContainer(),
                                                participant);
                    } catch (IllegalArgumentException e) {
                    }
                }

                if (displayName == null || displayName.length() == 0) {
                    displayName =
                            Messages.ParticipantCompareNode_CannotLocateOrgModel_label;
                }

                type += " (" + displayName + ")"; //$NON-NLS-1$ //$NON-NLS-2$
                break;
            }

        }

        if (type != null) {
            props.add(new XpdPropertyInfoNode(Messages.CompareNode_Type_label
                    + " " //$NON-NLS-1$
                    + type, 20, this, this.getType(), "participantTypeInfo")); //$NON-NLS-1$
        }
        return props;
    }
}
