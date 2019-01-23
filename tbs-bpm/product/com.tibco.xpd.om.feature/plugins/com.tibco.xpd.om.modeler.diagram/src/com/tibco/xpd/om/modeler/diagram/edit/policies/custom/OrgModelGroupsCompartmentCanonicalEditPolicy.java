/*
 * Copyright (c) TIBCO Software Inc 2010. All rights reserved.
 */
package com.tibco.xpd.om.modeler.diagram.edit.policies.custom;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy;
import org.eclipse.gmf.runtime.notation.View;

import com.tibco.xpd.om.core.om.Group;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.GroupItemEditPart;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelDiagramUpdater;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelNodeDescriptor;

/**
 * @generated
 */
public class OrgModelGroupsCompartmentCanonicalEditPolicy extends
        CanonicalEditPolicy {

    private static final String ORGMODEL_ITEM_VIEW = String
            .valueOf(GroupItemEditPart.VISUAL_ID);

    private final Map<Group, String> groupFilterIds =
            new HashMap<Group, String>();

    /**
     * @generated NOT
     */
    Set<EStructuralFeature> myFeaturesToSynchronize;

    @Override
    public void activate() {
        super.activate();
        /*
         * As this edit part will also list sub-groups it needs to listen to all
         * groups.
         */
        EObject host = getSemanticHost();
        if (host instanceof OrgModel) {
            for (Group group : ((OrgModel) host).getGroups()) {
                registerGroup(group);
            }
        }
    }

    /**
     * Add the given group (and sub-groups, recursive method) to the listener
     * list.
     * 
     * @param group
     */
    private void registerGroup(Group group) {
        if (group != null) {
            addGroupToSemanticListenerList(group);
            for (Group subGroup : group.getSubGroups()) {
                registerGroup(subGroup);
            }
        }
    }

    /**
     * @generated
     */
    @Override
    protected List getSemanticChildrenList() {
        View viewObject = (View) getHost().getModel();
        List result = new LinkedList();
        for (Iterator it =
                OrganizationModelDiagramUpdater
                        .getOrgModelGroupCompartment_SemanticChildren(viewObject)
                        .iterator(); it.hasNext();) {
            result.add(((OrganizationModelNodeDescriptor) it.next())
                    .getModelElement());
        }
        return result;
    }

    /**
     * @generated
     */
    @Override
    protected boolean isOrphaned(Collection semanticChildren, final View view) {
        if (ORGMODEL_ITEM_VIEW.equals(view.getType())) {
            return !semanticChildren.contains(view.getElement());
        }
        return false;
    }

    /**
     * @generated
     */
    @Override
    protected String getDefaultFactoryHint() {
        return null;
    }

    /**
     * @generated NOT
     */
    @Override
    protected Set<EStructuralFeature> getFeaturesToSynchronize() {
        if (myFeaturesToSynchronize == null) {
            myFeaturesToSynchronize = new HashSet<EStructuralFeature>();
            myFeaturesToSynchronize.add(OMPackage.eINSTANCE
                    .getOrgModel_Groups());
            myFeaturesToSynchronize.add(OMPackage.eINSTANCE
                    .getGroup_SubGroups());
        }
        return myFeaturesToSynchronize;
    }

    @Override
    protected void handleNotificationEvent(Notification event) {
        super.handleNotificationEvent(event);
        Object notifier = event.getNotifier();

        /*
         * If a new group is added or removed then we need to update the
         * listener filter. This is required as this edit part will also have to
         * be aware of sub-groups.
         */
        if ((notifier instanceof OrgModel || notifier instanceof Group)) {
            switch (event.getEventType()) {
            case Notification.ADD:
                if (event.getNewValue() instanceof Group) {
                    addGroupToSemanticListenerList((Group) event.getNewValue());
                }
                break;
            case Notification.REMOVE:
                if (event.getOldValue() instanceof Group) {
                    removeGroupFromSemanticListenerList((Group) event
                            .getOldValue());
                }
                break;
            }
        }
    }

    /**
     * Add a listener to the given Group.
     * 
     * @param grp
     */
    private void addGroupToSemanticListenerList(Group grp) {
        if (grp != null && grp.eResource() != null) {
            String filterId = grp.eResource().getURIFragment(grp);
            if (filterId != null) {
                addListenerFilter(filterId, this, grp);
                groupFilterIds.put(grp, filterId);
            }
        }
    }

    /**
     * Remove the listener from the given Group.
     * 
     * @param grp
     */
    private void removeGroupFromSemanticListenerList(Group grp) {
        if (grp != null && groupFilterIds.containsKey(grp)) {
            removeListenerFilter(groupFilterIds.get(grp));
            groupFilterIds.remove(grp);
        }
    }

}
