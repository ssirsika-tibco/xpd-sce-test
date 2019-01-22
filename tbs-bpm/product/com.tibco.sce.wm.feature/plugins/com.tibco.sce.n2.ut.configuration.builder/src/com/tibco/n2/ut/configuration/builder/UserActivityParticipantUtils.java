/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.n2.ut.configuration.builder;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

import com.tibco.n2.ut.model.DynamicOrgAttributeType;
import com.tibco.n2.ut.model.EntityType;
import com.tibco.n2.ut.model.UserTaskDynamicOrgAttributesType;
import com.tibco.n2.ut.model.UsertaskFactory;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.bpm.om.BPMProcessOrgModelUtil;
import com.tibco.xpd.om.core.om.Capability;
import com.tibco.xpd.om.core.om.Group;
import com.tibco.xpd.om.core.om.Location;
import com.tibco.xpd.om.core.om.ModelElement;
import com.tibco.xpd.om.core.om.OrgQuery;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.Position;
import com.tibco.xpd.om.core.om.Privilege;
import com.tibco.xpd.xpdExtension.DynamicOrgIdentifierRef;
import com.tibco.xpd.xpdExtension.DynamicOrganizationMapping;
import com.tibco.xpd.xpdExtension.DynamicOrganizationMappings;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * This class supplies a set of utility methods that can be used to help with
 * operations involving participants
 * 
 */
public class UserActivityParticipantUtils {

    /**
     * Resolves and gets EObject from xpdl external reference.
     * 
     * @param externalReference
     *            The reference of the object.
     * @return the referenced Element in form of ModelElement.
     */
    static public ModelElement getOMModelElement(
            ExternalReference externalReference) {

        /*
         * Sid XPD-3572: Access to org model entity was required by Xpdl2Bpel to
         * satisfy some runtime DE requirements for calendar/org-model reference
         * list for calculation of calendar.
         * 
         * Code pushed down to process analyst feature...
         */
        return BPMProcessOrgModelUtil.getOMModelElement(externalReference);
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
        /*
         * Sid XPD-3572: Access to org model entity was required by Xpdl2Bpel to
         * satisfy some runtime DE requirements for calendar/org-model reference
         * list for calculation of calendar.
         * 
         * Code pushed down to process analyst feature...
         */
        return BPMProcessOrgModelUtil.getOMModelVersion(omModelElement);
    }

    /**
     * Finds out if a model element is of type Org Query and return the query
     * 
     * @param omModelElement
     *            Model element to check
     * @return Null is not an Org Query, otherwise the query string
     */
    static public String getOrgQuery(ModelElement omModelElement) {
        String orgQuery = null;

        if (omModelElement instanceof OrgQuery) {
            orgQuery = ((OrgQuery) omModelElement).getQuery();
        }

        return orgQuery;
    }

    /**
     * Given a Model Element will return exactly which type of entity it is
     * 
     * @param refObj
     *            The Model Element to check the type of
     * @return The Entity type
     */
    static public EntityType getEntityType(ModelElement refObj) {
        EntityType entityType = null;

        if (refObj instanceof Group) {
            entityType = EntityType.ROLE_LITERAL;
        } else if (refObj instanceof Organization) {
            entityType = EntityType.ORGANIZATION_LITERAL;
        } else if (refObj instanceof OrgUnit) {
            entityType = EntityType.ORGANIZATIONALUNIT_LITERAL;
        } else if (refObj instanceof Position) {
            entityType = EntityType.POSITION_LITERAL;
        } else if (refObj instanceof Location) {
            entityType = EntityType.LOCATION_LITERAL;
        } else if (refObj instanceof Capability) {
            entityType = EntityType.CAPABILITY_LITERAL;
        } else if (refObj instanceof Privilege) {
            entityType = EntityType.PRIVILEGE_LITERAL;
        }

        // Note: There is another supported type of OrgQuery - however we do not
        // deal with this in the same manner, therefore there is no model type
        // to cover this

        return entityType;
    }

    /**
     * Given a participant type will return the corresponding Entity Type
     * 
     * @param partType
     *            Participant type to check
     * @return The Entity type that matches
     */
    static public EntityType getEntityType(ParticipantType partType) {
        EntityType entityType = null;

        if (partType.getValue() == ParticipantType.ROLE) {
            entityType = EntityType.ROLE_LITERAL;
        } else if (partType.getValue() == ParticipantType.HUMAN) {
            entityType = EntityType.RESOURCE_LITERAL;
        } else if (partType.getValue() == ParticipantType.ORGANIZATIONAL_UNIT) {
            entityType = EntityType.ORGANIZATIONALUNIT_LITERAL;
        } else if (partType.getValue() == ParticipantType.RESOURCE_SET) {
            entityType = EntityType.ORGANIZATIONALQUERY_LITERAL;
        }

        return entityType;
    }

    /**
     * Gets the performer for a given ID
     * 
     * @param activity
     *            The activity to search for the participant
     * @param performerId
     *            The ID of the performer
     * @return Participant field object or null if not found
     */
    static public ProcessRelevantData getDynamicParticipant(Activity activity,
            String performerId) {
        // Check to see if the ID can be found in the fields or parameters
        // So we need to find a data field with the corresponding id
        ProcessRelevantData dataField = null;

        for (ProcessRelevantData procData : ProcessInterfaceUtil
                .getAllAvailableRelevantDataForActivity(activity)) {
            if (procData.getId().equals(performerId)) {
                dataField = procData;
                break;
            }
        }

        return dataField;
    }

    /**
     * Finds the enclosing organisation and if it has dynamic="true" set then
     * return the id otherwise null
     * 
     * @param omModelElement
     *            Model Element to find if the parent Organization is dynamic
     * @return id of org if it is dynamic, otherwise null
     */
    static public String getDynamicOrganizationId(ModelElement omModelElement) {
        // Find the enclosing Organization object
        EObject modelObject = omModelElement.eContainer();
        while ((modelObject != null) && !(modelObject instanceof Organization)) {
            modelObject = modelObject.eContainer();
        }

        // Check if the Organization object was found
        if (modelObject != null) {
            Organization org = (Organization) modelObject;
            boolean dynamic = org.isDynamic();
            if (!dynamic) {
                return null;
            }
            String id = org.getId();
            return id;
        }

        return null;
    }

    /**
     * Gets the Dynamic Org Attributes information for a given participant
     * 
     * @param omModelElement
     *            Org Model element referenced by participant
     * @return The User Task Participant Dynamic Org Attributes or null if not a
     *         reference to a dynamic org
     */
    static public UserTaskDynamicOrgAttributesType getDynamicOrgAttrs(
            UsertaskFactory factory, Process process, Participant participant,
            ModelElement omModelElement) {
        // return null if not a dynamic organization
        String dynamicOrgId = getDynamicOrganizationId(omModelElement);
        if (dynamicOrgId == null) {
            return null;
        }

        String orgModelPath = participant.getExternalReference().getLocation();

        // OK, we've got the dynamicOrgId and orgModelPath, now look through
        // the list of DynamicOrganizationMappings in the Process and any that
        // match should be added to the UserTaskDynamicOrgAttributesType
        UserTaskDynamicOrgAttributesType dynOrgAttrs =
                factory.createUserTaskDynamicOrgAttributesType();
        EList<DynamicOrgAttributeType> attribList =
                dynOrgAttrs.getDynamicOrgAttribute();
        Object dynMappingsObj =
                Xpdl2ModelUtil.getOtherElement(process,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_DynamicOrganizationMappings());
        if (dynMappingsObj instanceof DynamicOrganizationMappings) {
            DynamicOrganizationMappings mappings =
                    (DynamicOrganizationMappings) dynMappingsObj;
            EList<DynamicOrganizationMapping> mappingList =
                    mappings.getDynamicOrganizationMapping();
            for (int ix = mappingList.size() - 1; ix >= 0; ix--) {
                DynamicOrganizationMapping mapping = mappingList.get(ix);
                DynamicOrgIdentifierRef orgRef =
                        mapping.getDynamicOrgIdentifierRef();
                // Need to also check the orgModelPath with its spaces escaped
                // as there can be the case where one is escaped, and the other
                // is not so the diff will not match unless we also check for
                // escaped spaces. We check both, as in theory the call to
                // orgRef.getOrgModelPath() should have returned the path
                // already URI encoded, but it does not, by checking both
                // encoded and unencoded it will protect ourselves against
                // someone changing Studio in the future to return it as encoded
                // (We do not want to encoded an already encoded string for
                // comparison as it will look a real mess)
                URI orgModelPathURI =
                        URI.createFileURI(orgRef.getOrgModelPath());
                String orgModelPathEncoded = orgModelPathURI.toString();

                if (orgRef.getDynamicOrgId().equals(dynamicOrgId)
                        && (orgRef.getOrgModelPath().equals(orgModelPath) || orgModelPathEncoded
                                .equals(orgModelPath))) {
                    DynamicOrgAttributeType newAttr =
                            factory.createDynamicOrgAttributeType();
                    newAttr.setDeName(orgRef.getIdentifierName());
                    newAttr.setPeName(mapping.getSourcePath());
                    attribList.add(newAttr);
                }
            }
        }

        if (attribList.size() > 0) {
            return dynOrgAttrs;
        }
        return null;
    }
}
