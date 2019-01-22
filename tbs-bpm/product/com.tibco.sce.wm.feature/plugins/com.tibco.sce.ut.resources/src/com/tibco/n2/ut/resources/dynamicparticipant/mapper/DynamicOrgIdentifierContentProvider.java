/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.n2.ut.resources.dynamicparticipant.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.n2.ut.configuration.builder.UserActivityParticipantUtils;
import com.tibco.n2.ut.resources.internal.Messages;
import com.tibco.xpd.om.core.om.DynamicOrgIdentifier;
import com.tibco.xpd.om.core.om.ModelElement;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.Position;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Create content provider with the Participants referencing dynamic
 * organization entity as the Root and the Referenced Dynamic Org identifier as
 * their Children.
 * 
 * @author kthombar
 * @since 26-Sep-2013
 */
public class DynamicOrgIdentifierContentProvider implements
        ITreeContentProvider {

    /**
     * 
     */
    public DynamicOrgIdentifierContentProvider() {

    }

    /**
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     * 
     */
    public void dispose() {
    }

    /**
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     * 
     * @param viewer
     * @param oldInput
     * @param newInput
     */
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }

    /**
     * Returns Dynamic Organizations. i.e. Dynamic Organization having entites
     * referenced by process participants.
     * 
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getElements(java.lang.Object)
     * 
     * @param inputElement
     * @return
     */
    public Object[] getElements(Object inputElement) {

        if (inputElement instanceof Process) {
            Process process = (Process) inputElement;

            Set<Organization> dynamicOrganization = new HashSet<Organization>();

            /*
             * Get the Process as well as Package level Participants.
             */
            List<Participant> allParticipants = new ArrayList<Participant>();
            allParticipants.addAll(process.getParticipants());

            allParticipants.addAll(process.getPackage().getParticipants());

            for (Participant eachParticipant : allParticipants) {
                ExternalReference externalReference =
                        eachParticipant.getExternalReference();
                /*
                 * check if the external reference is valid and if the
                 * participant is used in a user task.
                 */
                if (externalReference != null
                        && externalReference.getLocation() != null
                        && externalReference.getXref() != null
                        && isParticipantUsedInUserTask(process, eachParticipant)) {

                    ModelElement omModelElement =
                            UserActivityParticipantUtils
                                    .getOMModelElement(externalReference);

                    Organization organization =
                            getOrganizationFromOrgEntity(omModelElement);

                    if (organization != null && organization.isDynamic()) {
                        /*
                         * Using HastSet as we need to avoid duplicate
                         * references to Dynamic Organization.
                         */
                        dynamicOrganization.add(organization);
                    }
                }

            }
            if (dynamicOrganization.isEmpty()) {
                /*
                 * If there are no participants referencing the dynamic
                 * Organization entities then display message in the Target
                 * section stating that no dynamic identifiers available.
                 */
                String[] str = new String[1];
                str[0] =
                        Messages.DynamicOrgIdentifierContentProvider_NoDynamicIdentifiersToMap_label0;
                return str;

            }
            Organization dynamicOrganizationArray[] =
                    dynamicOrganization
                            .toArray(new Organization[dynamicOrganization
                                    .size()]);

            Arrays.sort(dynamicOrganizationArray, new TargetContentComparator());

            return dynamicOrganizationArray;
        }
        return new Object[0];

    }

    /**
     * Returns true if the participant is used in the user task of a process.
     * 
     * @param process
     * @param eachParticipant
     * @return
     */
    private boolean isParticipantUsedInUserTask(Process process,
            Participant eachParticipant) {
        Collection<Activity> allActivitiesInProc =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);

        for (Activity activity : allActivitiesInProc) {
            if (TaskType.USER_LITERAL.equals(TaskObjectUtil
                    .getTaskTypeStrict(activity))) {
                EList<Performer> performerList = activity.getPerformerList();
                for (Performer eachPerformer : performerList) {
                    if (eachParticipant.getId()
                            .equals(eachPerformer.getValue())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Returns the Dynamic Org Identifiers of the referenced dynamic
     * organiztion. These Org Identifiers are wrapped in a List of
     * DynamicOrgIdentiferPath
     * 
     * @see com.tibco.xpd.processeditor.xpdl2.properties.resource.DynOrgIdPath
     * 
     * @param parentElement
     * @return
     */
    public Object[] getChildren(Object parentElement) {
        if (parentElement instanceof Organization) {
            List<DynamicOrgIdentiferPath> dynamicOrgIdentiferPath =
                    new ArrayList<DynamicOrgIdentiferPath>();
            Organization dynamicOrganization = (Organization) parentElement;

            EList<DynamicOrgIdentifier> dynamicOrgIdentifiers =
                    dynamicOrganization.getDynamicOrgIdentifiers();

            DynamicOrgIdentifier dynamicIdentifierArray[] =
                    dynamicOrgIdentifiers
                            .toArray(new DynamicOrgIdentifier[dynamicOrgIdentifiers
                                    .size()]);

            Arrays.sort(dynamicIdentifierArray, new TargetContentComparator());

            for (DynamicOrgIdentifier eachdynamicOrgIdentifier : dynamicIdentifierArray) {
                DynamicOrgIdentiferPath orgIdPath =
                        new DynamicOrgIdentiferPath(eachdynamicOrgIdentifier);
                dynamicOrgIdentiferPath.add(orgIdPath);

            }
            return dynamicOrgIdentiferPath.toArray();
        }
        return new Object[0];
    }

    /**
     * Returns the Organization from the containing Organization
     * Entity(OrgUnit/Position)
     * 
     * @param referencedOrgModelNode
     * @return
     */
    private Organization getOrganizationFromOrgEntity(
            ModelElement referencedOrgModelNode) {
        if (referencedOrgModelNode instanceof Position) {
            Position position = (Position) referencedOrgModelNode;
            EObject eContainer = position.eContainer();
            return ((OrgUnit) eContainer).getOrganization();
        } else if (referencedOrgModelNode instanceof OrgUnit) {
            return ((OrgUnit) referencedOrgModelNode).getOrganization();
        }

        return null;
    }

    /**
     * Returns the Parent of the Dynamic org identifiers, i.e. dynamic
     * Organization
     * 
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
     * 
     * @param element
     * @return
     */
    public Object getParent(Object element) {
        if (element instanceof DynamicOrgIdentiferPath) {
            DynamicOrgIdentiferPath dynOrgIdPath =
                    (DynamicOrgIdentiferPath) element;

            return dynOrgIdPath.getParent();
        }

        return null;
    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
     * 
     * @param element
     * @return
     */
    public boolean hasChildren(Object element) {
        /*
         * Only Organization(root element) can have children.
         */
        if (element instanceof Organization) {
            return true;
        }
        return false;
    }

    /**
     * Comparator for sorting Organization(Root/Parent) and
     * DynamicOrgIdentifier(Children) alphabetically.
     * 
     * 
     * @author kthombar
     * @since 05-Oct-2013
     */
    private class TargetContentComparator implements Comparator<EObject> {

        /**
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         * 
         * @param paramT1
         * @param paramT2
         * @return
         */
        public int compare(EObject paramT1, EObject paramT2) {

            if (paramT1 instanceof Organization
                    && paramT2 instanceof Organization) {
                /*
                 * Sort the Organization's alphabetically
                 */
                Organization org1 = (Organization) paramT1;
                Organization org2 = (Organization) paramT2;

                String displayName1 = org1.getDisplayName();
                String displayName2 = org2.getDisplayName();

                if (displayName1 != null && displayName1.length() != 0
                        && displayName2 != null && displayName2.length() != 0) {
                    return displayName1.compareToIgnoreCase(displayName2);
                }

            } else if (paramT1 instanceof DynamicOrgIdentifier
                    && paramT2 instanceof DynamicOrgIdentifier) {
                /*
                 * Sort the DynamicOrgIdentifier's alphabetically
                 */
                DynamicOrgIdentifier identifier1 =
                        (DynamicOrgIdentifier) paramT1;
                DynamicOrgIdentifier identifier2 =
                        (DynamicOrgIdentifier) paramT2;

                String displayName1 = identifier1.getDisplayName();
                String displayName2 = identifier2.getDisplayName();

                if (displayName1 != null && displayName1.length() != 0
                        && displayName2 != null && displayName2.length() != 0) {
                    return displayName1.compareToIgnoreCase(displayName2);
                }

            }
            return 0;
        }
    }
}
