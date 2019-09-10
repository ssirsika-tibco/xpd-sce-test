/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.om.resources.wc;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import com.tibco.xpd.om.core.om.AttributeType;
import com.tibco.xpd.om.core.om.AttributeValue;
import com.tibco.xpd.om.core.om.EnumValue;
import com.tibco.xpd.om.core.om.Group;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.Position;
import com.tibco.xpd.om.core.om.Privilege;
import com.tibco.xpd.om.core.om.PrivilegeAssociation;
import com.tibco.xpd.om.core.om.SystemAction;

/**
 * Performs the migration of System Actions; removing the deprecated actions by moving them to, or merging them with,
 * other actions. When merging System Actions the merge will only occur within the scope at which the System Actions are
 * defined. The System Action's scope is the org-model entity to which it is assigned.
 *
 * @author pwatson
 * @since 22 Aug 2019
 */
public class SystemActionMigration {

    /**
     * The collection of migration actions to be performed. This collection identifies those System Actions that are to
     * be migrated and the action to be taken to perform that migration.
     */
    @SuppressWarnings("nls")
    private static final MigrationAction[] MIGRATIONS = {
            MigrationAction.reasign("EC", "queryStatistics", "EC", "queryAudit"), //
            MigrationAction.delete("EC", "purgeAudit"), //
            MigrationAction.delete("EC", "manageAuditConfiguration"), //
            MigrationAction.delete("EC", "directAuditAccess"), //
            MigrationAction.delete("EC", "listProcessTemplateAuditTrail"), //
            MigrationAction.delete("EC", "showProcessInstanceAuditTrail"), //
            MigrationAction.delete("EC", "openWorkItemAuditTrail"), //

            MigrationAction.reasign("BDS", "createGlobalData", "CDM", "createCase"), //
            MigrationAction.reasign("BDS", "updateGlobalData", "CDM", "updateCase"), //
            MigrationAction.reasign("BDS", "deleteGlobalData", "CDM", "deleteCase"), //
            MigrationAction.reasign("BDS", "readGlobalData", "CDM", "readCase"), //
            MigrationAction.reasign("BDS", "cmisUser", "APPDEV", "useCaseDocument"), //
            MigrationAction.reasign("BDS", "cmisAdmin", "APPDEV", "administerCaseDocument"), //

            MigrationAction.delete("BDS", "manageDataViews"), //
            MigrationAction.delete("BDS", "accessGlobalDataScripts"), //
            MigrationAction.delete("BDS", "administerGlobalDataScripts") //
    };

    /**
     * The entry point of the migration process. The given Org-Model is traversed for all System Action definitions.
     * These include the definitions at the root of the model, the nested Groups, the Org-Units and the Positions.
     * 
     * @param aOrgModel
     *            the Org-Model whose System Action definitions are to be migrated.
     */
    public void migrate(OrgModel aOrgModel) {
        migrateSystemActions(aOrgModel.getSystemActions());

        migrateGroupActions(aOrgModel.getGroups());

        for (Organization organization : aOrgModel.getOrganizations()) {
            migrateOrgUnitActions(organization.getUnits());
        }
    }

    /**
     * Performs the migration of System Actions assigned to the given collection of Groups. It will recursively traverse
     * the nested Groups.
     * 
     * @param aGroups
     *            the Groups whose System Actions are to be migrated.
     */
    private void migrateGroupActions(Collection<Group> aGroups) {
        for (Group group : aGroups) {
            migrateSystemActions(group.getSystemActions());

            // recurse down into sub-groups
            migrateGroupActions(group.getSubGroups());
        }
    }

    /**
     * Performs the migration of System Actions assigned to the given collection of Org-Units, and the Positions within
     * those Org-Units.
     * 
     * @param aOrgUnits
     *            the Org-Units, and Positions, whose System Actions are to be migrated.
     */
    private void migrateOrgUnitActions(Collection<OrgUnit> aOrgUnits) {
        for (OrgUnit orgUnit : aOrgUnits) {
            migrateSystemActions(orgUnit.getSystemActions());

            migratePositionActions(orgUnit.getPositions());
        }
    }

    /**
     * Performs the migration of System Actions assigned to the given collection of Positions.
     * 
     * @param aOrgUnits
     *            the Positions whose System Actions are to be migrated.
     */
    private void migratePositionActions(Collection<Position> aPositions) {
        for (Position position : aPositions) {
            migrateSystemActions(position.getSystemActions());
        }
    }

    /**
     * Performs the migration of the given collection of System Actions. The migration involves:
     * <ol>
     * <li>Removing those System Actions that are no longer supported.</li>
     * <li>Merging those System Actions that have been incorporated into another System Action.</li>
     * </ol>
     * 
     * When transposing, or incorporating, a System Action into another System Action any Required Privileges assigned
     * must also be merged. So, if System Action "a", with the Required Privilege "p1", is to be merged with System
     * Action "b", with the Required Privilege "p2", then after the merge System Action "b" will have the Required
     * Privileges "p1" and "p2".
     * 
     * @param aActions
     *            the collection of System Actions to be migrated.
     */
    private void migrateSystemActions(Collection<SystemAction> aActions) {
        // keep track of the System Actions we've encountered
        Map<SystemActionKey,SystemAction> processedActions = new HashMap<>();

        for (Iterator<SystemAction> iterator = aActions.iterator(); iterator.hasNext();) {
            SystemAction action = iterator.next();

            boolean migrated = false;
            for (MigrationAction migration : MIGRATIONS) {
                if (migration.appliesTo(action)) {
                    // perform the migration
                    action = migration.migrate(action);

                    // if not deleted - must be a transposition
                    if (action != null) {
                        // look for a matching System Action in same scope
                        SystemActionKey key = new SystemActionKey(action);
                        SystemAction processed = processedActions.get(key);

                        // if we've already encountered a matching action
                        if (processed != null) {
                            // merge with the existing action
                            merge(processed, action);

                            // remove the original action
                            iterator.remove();
                        } else {
                            // add it to the list
                            processedActions.put(new SystemActionKey(action), action);
                        }
                    } else {
                        // remove the original action
                        iterator.remove();
                    }

                    migrated = true;
                    break;
                }
            }

            // if no migration to be applied
            if (!migrated) {
                // add it to the list
                processedActions.put(new SystemActionKey(action), action);
            }
        }
    }

    /**
     * System Actions can only appear once within the same scope (the scope being the org-unit to which the action is
     * assigned). So, when a System Action is to be replaced with another, it must be merged with any instance of that
     * other action that may be in the same scope. The privilege associations assigned to the source SystemAction also
     * need to be merged with those of the destination SystemAction.
     * 
     * @param aDest
     *            the SystemAction to which the privilege associations are to be moved.
     * @param aSrc
     *            the System action whose privilege associations are to be moved.
     */
    private void merge(SystemAction aDest, SystemAction aSrc) {
        for (Iterator<PrivilegeAssociation> iterator = aSrc.getPrivilegeAssociations().iterator(); iterator
                .hasNext();) {
            PrivilegeAssociation srcPriv = iterator.next();

            Privilege priv = srcPriv.getPrivilege();
            PrivilegeAssociation found = null;

            for (PrivilegeAssociation destPriv : aDest.getPrivilegeAssociations()) {
                if (priv.getId().equals(destPriv.getPrivilege().getId())) {
                    found = destPriv;
                    break;
                }
            }

            // if not already in the destination's list
            if (found == null) {
                iterator.remove();
                aDest.getPrivilegeAssociations().add(srcPriv);
            }

            // attempt to merge the qualifier values
            else if ((priv.getQualifierAttribute() != null) &&
                    (srcPriv.getQualifierValue() != null)) {
                mergeQualifiers(found, srcPriv);
            }
        }
    }

    /**
     * When merging the Required Privileges of a System Action, any qualifiers of matching Privileges must also be
     * merged.
     * 
     * @param aDest
     *            the Required Privilege of the destination System Action.
     * @param aSrc
     *            the Required Privilege of the source System Action.
     */
    private void mergeQualifiers(PrivilegeAssociation aDest, PrivilegeAssociation aSrc) {
        AttributeType qualifierType = aSrc.getQualifierValue().getType();

        // if destination doesn't yet have a qualifier
        AttributeValue destQualifier = aDest.getQualifierValue();
        if (destQualifier == null) {
            // simply move the qualifier value
            AttributeValue value = aSrc.getQualifierValue();
            aSrc.setQualifierValue(null);
            aDest.setQualifierValue(value);
        }

        // we need to add additional value to dest - only for Set types
        else if (qualifierType == AttributeType.ENUM_SET) {
            mergeEnumSets(destQualifier.getEnumSetValues(), aSrc.getQualifierValue().getEnumSetValues());
        }

        else if (qualifierType == AttributeType.SET) {
            mergeSets(destQualifier.getSetValues(), aSrc.getQualifierValue().getSetValues());
        }

        else {
            // we can't merge the qualifier - what now? - leave original as-is
        }
    }

    /**
     * Merges the two collections of Enum literal values so that the result is the addition of both collections.
     * 
     * @param aDest
     *            the destination collection in which the merged result will be returned.
     * @param aSrc
     *            the source collection to be merged with the destination. Any merged elements will be removed.
     */
    private void mergeEnumSets(Collection<EnumValue> aDest, Collection<EnumValue> aSrc) {
        for (Iterator<EnumValue> iterator = aSrc.iterator(); iterator.hasNext();) {
            EnumValue src = iterator.next();

            boolean found = false;
            for (EnumValue dest : aDest) {
                if (Objects.equals(src.getValue(), dest.getValue())) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                iterator.remove();
                aDest.add(src);
            }
        }
    }

    /**
     * Merges the two collections of literal values so that the result is the addition of both collections.
     * 
     * @param aDest
     *            the destination collection in which the merged result will be returned.
     * @param aSrc
     *            the source collection to be merged with the destination. Any merged elements will be removed.
     */
    private void mergeSets(Collection<String> aDest, Collection<String> aSrc) {
        for (Iterator<String> iterator = aSrc.iterator(); iterator.hasNext();) {
            String src = iterator.next();

            boolean found = false;
            for (String dest : aDest) {
                if (Objects.equals(src, dest)) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                iterator.remove();
                aDest.add(src);
            }
        }
    }

    /**
     * Provides a mapping from an old SystemAction to a new SystemAction. Provides methods to test if a given
     * SystemAction is to be migrated and also to perform that migration.
     */
    private static class MigrationAction {
        /**
         * A factory method to create a MigrationAction to transpose a SystemAction from one component and ID tuple to
         * another.
         * 
         * @param aOldComponent
         *            the original System Action component ID
         * @param aOldActionId
         *            the original System Action action ID
         * @param aNewComponent
         *            the new System Action component ID
         * @param aNewActionId
         *            the new System Action action ID
         * @return the MigrationAction to perform the transformation.
         */
        public static MigrationAction reasign(String aOldComponent, String aOldActionId, String aNewComponent,
                String aNewActionId) {
            return new MigrationAction(aOldComponent, aOldActionId, aNewComponent, aNewActionId, false);
        }

        /**
         * A factory method to create a MigrationAction to delete a SystemAction with the given component and ID.
         * 
         * @param aOldComponent
         *            the original System Action component ID
         * @param aOldActionId
         *            the original System Action action ID
         * @return the MigrationAction to perform the deletion.
         */
        public static MigrationAction delete(String aOldComponent, String aOldActionId) {
            return new MigrationAction(aOldComponent, aOldActionId, null, null, true);
        }

        private String oldComponent;

        private String oldActionId;

        private String newComponent;

        private String newActionId;

        private boolean deletion;

        private MigrationAction(String aOldComponent, String aOldActionId, String aNewComponent, String aNewActionId,
                boolean aDeletion) {
            oldComponent = aOldComponent;
            oldActionId = aOldActionId;
            newComponent = aNewComponent;
            newActionId = aNewActionId;
            deletion = aDeletion;
        }

        /**
         * Tests whether this MigrationAction applies to the given System Action.
         * 
         * @param aAction
         *            the System Action to be compared.
         * @return <code>true</code> if this migration applies to the given System Action.
         */
        public boolean appliesTo(SystemAction aAction) {
            return (Objects.equals(oldActionId, aAction.getActionId()))
                    && (Objects.deepEquals(oldComponent, aAction.getComponent()));
        }

        /**
         * Performs the migration action. If the System Action is to be deleted, the return value will be
         * <code>null</code>. If the System Action is to be merged with another System Action, the return value will
         * have the identity of the destination System Action.
         * <p>
         * This method should only be called if {@link #appliesTo(SystemAction)} returned <code>true</code> for the same
         * System Action.
         * 
         * @param aAction
         *            the System Action on which the migration action is to be performed.
         * @return the result of the migration.
         */
        public SystemAction migrate(SystemAction aAction) {
            if (deletion) {
                return null;
            }

            aAction.setComponent(newComponent);
            aAction.setActionId(newActionId);

            return aAction;
        }
    }

    /**
     * An adapter to provide a SystemAction with identity based on its component and action ID. Allows the SystemAction
     * to be used as a key value in a Map.
     */
    private static class SystemActionKey {
        private final SystemAction action;

        private final int hashCache;

        public SystemActionKey(SystemAction aAction) {
            action = aAction;
            hashCache = 31 * (31 * (action.getActionId() == null ? 0 : action.getActionId().hashCode()))
                    + (action.getComponent() == null ? 0 : action.getComponent().hashCode());
        }

        /**
         * A hash-code based on the properties of the referenced System Action.
         */
        @Override
        public int hashCode() {
            return hashCache;
        }

        /**
         * Compares this SystemActionKey for equality with the given object. The two are considered equal if the given
         * object is a SystemActionKey that references a System Action with the same component and ID values.
         */
        @Override
        public boolean equals(Object aOther) {
            if (this == aOther)
                return true;

            if ((aOther == null) || (!(aOther instanceof SystemActionKey)))
                return false;

            SystemActionKey other = (SystemActionKey) aOther;
            return Objects.equals(action.getActionId(), other.action.getActionId())
                    && Objects.equals(action.getComponent(), other.action.getComponent());
        }
    }
}