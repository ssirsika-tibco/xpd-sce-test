/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.tests.importmigration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.om.core.om.AttributeType;
import com.tibco.xpd.om.core.om.AttributeValue;
import com.tibco.xpd.om.core.om.EnumValue;
import com.tibco.xpd.om.core.om.Group;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.Position;
import com.tibco.xpd.om.core.om.PrivilegeAssociation;
import com.tibco.xpd.om.core.om.SystemAction;
import com.tibco.xpd.om.core.om.util.OMUtil;
import com.tibco.xpd.om.resources.wc.OMWorkingCopy;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetMigrationManager;
import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetMigrationManager.ProjectCompatibilityWithCode;
import com.tibco.xpd.resources.util.ProjectImporter;
import com.tibco.xpd.resources.util.SpecialFolderUtil;

import junit.framework.TestCase;

/**
 *
 *
 * @author pwatson
 * @since 23 Aug 2019
 */
@SuppressWarnings("nls")
public class SystemActionMigrationTest extends TestCase {
    private static final String[][] DEPRECATED_ACTIONS = { //
            { "EC", "purgeAudit" }, //
            { "EC", "manageAuditConfiguration" }, //
            { "EC", "queryStatistics" }, //
            { "EC", "directAuditAccess" }, //
            { "EC", "listProcessTemplateAuditTrail" }, //
            { "EC", "showProcessInstanceAuditTrail" }, //
            { "EC", "openWorkItemAuditTrail" }, //

            { "BDS", "createGlobalData" }, //
            { "BDS", "updateGlobalData" }, //
            { "BDS", "deleteGlobalData" }, //
            { "BDS", "readGlobalData" }, //
            { "BDS", "cmisUser" }, //
            { "BDS", "cmisAdmin" }, //

            { "BDS", "manageDataViews" }, //
            { "BDS", "accessGlobalDataScripts" }, //
            { "BDS", "administerGlobalDataScripts" }, //

            { "OS", "contributeGadget" }, //
            { "OS", "manageGadgets" }, //
            { "OS", "viewHubPolicy" }, //
            { "OS", "editHubPolicy" }, //
            { "OS", "openspaceFeatureSetA" }, //
            { "OS", "openspaceFeatureSetB" }, //
            { "OS", "openspaceFeatureSetC" }, //

            { "OS", "openspaceFeatureSetC" }, //

            { "BIZSVC", "executeBusinessService" }, //

            { "WSB", "startBusinessService" }, //
            { "WSB", "applicationConfiguration" }, //

            { "PE", "resumeProcessInstance" }, //
            { "PE", "suspendProcessInstance" }, //
            { "PE", "cancelProcessInstance" }, //
            { "PE", "purgeProcessInstances" }, //
            { "PE", "bulkResumeProcessInstances" }, //
            { "PE", "bulkSuspendProcessInstances" }, //
            { "PE", "bulkCancelProcessInstances" }, //
            { "PE", "bulkPurgeProcessInstances" }, //
            { "PE", "queryProcessInstance" }, //
            { "PE", "queryProcessTemplate" }, //
            { "PE", "haltedProcessAdministration" }, //

            { "APPDEV", "canViewRoles" }, //
            { "APPDEV", "canEditRoles" } //

    };

    // the new actions that are migrated from older actions during migration
    private static final String[][] ADDITIONAL_ACTIONS = { //
            { "CDM", "createUpdateCase" }, //
            { "CDM", "deleteCase" }, //
            { "CDM", "readCase" }, //
            { "APPDEV", "useCaseDocument" }, //
            { "APPDEV", "administerCaseDocument" }, //
            { "APPDEV", "appDev" }, //
            { "PE", "resumeSuspendProcessInstance" }, //
            { "PE", "cancelPurgeProcessInstance" }, //
            { "PE", "bulkResumeSuspendProcessInstances" }, //
            { "PE", "bulkCancelPurgeProcessInstances" }, //
            { "PE", "queryProcess" } //

    };

    // @Test
    public void testSystemActions() throws Exception {
        ProjectImporter projectImporter = TestUtil.importProjectsFromZip("com.tibco.xpd.sce.test",
                new String[] { "resources/SystemActionMigrationTest/system-actions/" },
                new String[] { "system-actions" });

        assertTrue("Failed to load projects from resources/SystemActionMigrationTest/", projectImporter != null);
        try {
            TestUtil.buildAndWait();

            IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("system-actions");

            // we expect no validation markers
            boolean hasErrorMarkers = TestUtil.hasErrorProblemMarker(project,
                    true,
                    Collections.singletonList("com.tibco.xpd.forms.validation.project.misconfigured"),
                    "testSystemActions");

            if (hasErrorMarkers) {
                ProjectCompatibilityWithCode projectCompatibilityWithCode =
                        ProjectAssetMigrationManager.getInstance().getProjectCompatibilityWithCode(project);

                if (ProjectCompatibilityWithCode.NOT_SCE.equals(projectCompatibilityWithCode)) {
                    System.err.println(
                            "** SystemActionMigrationTest.testSystemActions() - Project HAS NOT been migrated to CE (ProjectCompatibilityWithCode:"
                                    + projectCompatibilityWithCode + ")");
                } else {
                    System.err.println(
                            "** SystemActionMigrationTest.testSystemActions() - Project HAS been migrated to CE (ProjectCompatibilityWithCode:"
                                    + projectCompatibilityWithCode + ")");
                }

            }

            assertFalse("system-actions projects has unexpected problem markers (see console) output)",
                    hasErrorMarkers);

            checkOrgModel(findOrgModel(project));
        } finally {
            if (projectImporter != null) {
                projectImporter.performDelete();
            }
        }
    }

    /**
     * Checks the System Actions assigned to the given orgModel. It will recursively traverse the nested elements.
     * 
     * @param aOrgModel
     *            the OrgModel whose System Actions are to be checked.
     */
    private void checkOrgModel(OrgModel aOrgModel) {
        assertNotNull(aOrgModel);

        checkSystemActions(aOrgModel.getSystemActions());
        checkNewActions(aOrgModel.getSystemActions());

        checkGroupActions(aOrgModel.getGroups());

        for (Organization organization : aOrgModel.getOrganizations()) {
            checkOrgUnitActions(organization.getUnits());
        }
    }

    /**
     * Tests that all the new actions appear in the given collection. This is only called on org-model level actions -
     * as that is the only place where ALL actions will appear.
     */
    private void checkNewActions(Collection<SystemAction> aActions) {
        boolean[] foundAdditions = new boolean[SystemActionMigrationTest.ADDITIONAL_ACTIONS.length];
        Arrays.fill(foundAdditions, false);

        for (SystemAction action : aActions) {
            // look for the new actions
            for (int i = 0; i < SystemActionMigrationTest.ADDITIONAL_ACTIONS.length; i++) {
                String[] addition = SystemActionMigrationTest.ADDITIONAL_ACTIONS[i];
                if ((Objects.equals(action.getComponent(), addition[0]))
                        && (Objects.equals(action.getActionId(), addition[1]))) {
                    foundAdditions[i] = true;
                }
            }
        }

        for (int i = 0; i < foundAdditions.length; i++) {
            String[] action = SystemActionMigrationTest.ADDITIONAL_ACTIONS[i];
            assertTrue("Failed to locate new action: " + action[0] + ":" + action[1], foundAdditions[i]);
        }
    }

    /**
     * Checks the System Actions assigned to the given collection of Groups. It will recursively traverse the nested
     * Groups.
     * 
     * @param aGroups
     *            the Groups whose System Actions are to be checked.
     */
    private void checkGroupActions(Collection<Group> aGroups) {
        for (Group group : aGroups) {
            checkSystemActions(group.getSystemActions());

            // recurse down into sub-groups
            checkGroupActions(group.getSubGroups());
        }
    }

    /**
     * Checks the System Actions assigned to the given collection of Org-Units, and the Positions within those
     * Org-Units.
     * 
     * @param aOrgUnits
     *            the Org-Units, and Positions, whose System Actions are to be checked.
     */
    private void checkOrgUnitActions(Collection<OrgUnit> aOrgUnits) {
        for (OrgUnit orgUnit : aOrgUnits) {
            checkSystemActions(orgUnit.getSystemActions());

            checkPositionActions(orgUnit.getPositions());
        }
    }

    /**
     * Checks the System Actions assigned to the given collection of Positions.
     * 
     * @param aOrgUnits
     *            the Positions whose System Actions are to be checked.
     */
    private void checkPositionActions(Collection<Position> aPositions) {
        for (Position position : aPositions) {
            checkSystemActions(position.getSystemActions());
        }
    }

    /**
     * Checks that the given collection contains none of the deprecated SystemActions.
     * 
     * @param aActions
     *            the actions to be checked.
     */
    private void checkSystemActions(Collection<SystemAction> aActions) {
        List<String> failures = new ArrayList<>();

        for (SystemAction action : aActions) {
            for (String[] deprecated : SystemActionMigrationTest.DEPRECATED_ACTIONS) {
                if ((Objects.equals(action.getComponent(), deprecated[0]))
                        && (Objects.equals(action.getActionId(), deprecated[1]))) {
                    failures.add(String.format("%1$s - %2$s", deprecated[0], deprecated[1]));
                }
            }

            // test for merged privileges with EC - Query Audit
            checkMergedPrivileges(action,
                    "EC",
                    "queryAudit",
                    new ExpectedPrivAssoc[] { //
                            new ExpectedPrivAssoc("QueryAudit", "Value1", "Value2", "Value3", "Value4"), //
                            new ExpectedPrivAssoc("QueryStatistics", "Value1", "Value2") //
                    });

            // test for merged privileges with BDS createCase, updateCase into createUpdateCase
            checkMergedPrivileges(action,
                    "CDM",
                    "createUpdateCase",
                    new ExpectedPrivAssoc[] { //
                            new ExpectedPrivAssoc("CreateGlobalData"), //
                            new ExpectedPrivAssoc("UpdateGlobalData") //
                    });

            // test for merged privileges with APPDEV canEditLocales, canEditApplication, canPublishApplication,
            // contributeGadget into appDev
            checkMergedPrivileges(action,
                    "APPDEV",
                    "appDev",
                    new ExpectedPrivAssoc[] { //
                            new ExpectedPrivAssoc("CanEditLocales"), //
                            new ExpectedPrivAssoc("CanEditApplication"), //
                            new ExpectedPrivAssoc("CanPublishApplication"), //
                            new ExpectedPrivAssoc("ContributeGadget") //
                    });


            /*
             * test for merged privileges with PE resumeProcessInstance, suspendProcessInstance, cancelProcessInstance,
             * purgeProcessInstances, bulkResumeProcessInstances, bulkSuspendProcessInstances,
             * bulkCancelProcessInstances, bulkPurgeProcessInstances, queryProcessInstance, queryProcessTemplate,
             * haltedProcessAdministration into various
             */
            checkMergedPrivileges(action,
                    "PE",
                    "resumeSuspendProcessInstance",
                    new ExpectedPrivAssoc[] { //
                            new ExpectedPrivAssoc("ResumeProcessInstance"), //
                            new ExpectedPrivAssoc("SuspendProcessInstance"),
                            new ExpectedPrivAssoc("HaltedProcessAdministration") });

            checkMergedPrivileges(action,
                    "PE",
                    "cancelPurgeProcessInstance",
                    new ExpectedPrivAssoc[] { //
                            new ExpectedPrivAssoc("CancelProcessInstance"), //
                            new ExpectedPrivAssoc("PurgeProcessInstances") });

            checkMergedPrivileges(action,
                    "PE",
                    "bulkResumeSuspendProcessInstances",
                    new ExpectedPrivAssoc[] { //
                            new ExpectedPrivAssoc("BulkResumeProcessInstances"), //
                            new ExpectedPrivAssoc("BulkSuspendProcessInstances") });

            checkMergedPrivileges(action,
                    "PE",
                    "bulkCancelPurgeProcessInstances",
                    new ExpectedPrivAssoc[] { //
                            new ExpectedPrivAssoc("BulkCancelProcessInstances"), //
                            new ExpectedPrivAssoc("BulkPurgeProcessInstances") });

            checkMergedPrivileges(action,
                    "PE",
                    "queryProcess",
                    new ExpectedPrivAssoc[] { //
                            new ExpectedPrivAssoc("QueryProcessInstance"), //
                            new ExpectedPrivAssoc("QueryProcessTemplate") });

        }

        assertTrue(failures.toString(), failures.isEmpty());
    }

    private void checkMergedPrivileges(SystemAction action, String targetComponent, String targetAction,
            ExpectedPrivAssoc[] expected) {
        if ((Objects.equals(targetComponent, action.getComponent()))
                && (Objects.equals(targetAction, action.getActionId()))) {

            // this should now have enum values merged from EC - Query Statistics action
            EList<PrivilegeAssociation> associations = action.getPrivilegeAssociations();
            assertEquals("Unexpected number of privilege association for '" + targetAction + "' system action",
                    expected.length,
                    associations.size());
            for (PrivilegeAssociation privAssoc : associations) {
                boolean found = false;

                for (ExpectedPrivAssoc expect : expected) {
                    if (expect.compare(privAssoc)) {
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    fail("Unexpected privilege association for '" + targetAction + "' system action: "
                            + privAssoc.getPrivilege().getName());
                }
            }
        }
    }


    /**
     * Searches the given project for an org-model. If no org-model can be found the result will be <code>null</code>.
     * 
     * @param aProject
     *            the project to be searched.
     * @return the 1st org-model file in the project (project validation ensures only one)
     * @throws CoreException
     */
    private OrgModel findOrgModel(IProject aProject) throws CoreException {
        if (aProject == null) {
            return null;
        }

        // look for all special folders - those releated to org-models
        List<SpecialFolder> sFolders =
                SpecialFolderUtil.getAllSpecialFoldersOfKind(aProject, OMUtil.OM_SPECIAL_FOLDER_KIND);
        if (sFolders == null) {
            return null;
        }

        for (SpecialFolder sFolder : sFolders) {
            if (sFolder.getFolder() != null && sFolder.getFolder().exists()) {
                // iterator over all 'files' within the folder
                IResource[] members = sFolder.getFolder().members();
                for (IResource resource : members) {
                    // if it's a file with the org-model extension
                    if (resource instanceof IFile && OMUtil.OM_FILE_EXTENSION.equals(resource.getFileExtension())) {
                        return read(resource);
                    }
                }
            }
        }

        return null;
    }

    /**
     * Attempts to read the OrgModel from the given IResource. If no Org-Model can be found the return value will be
     * <code>null</code>.
     * 
     * @param aResource
     *            the resource (file) from which the OrgModel will be read.
     * @return
     */
    private OrgModel read(IResource aResource) {
        WorkingCopy wc = XpdResourcesPlugin.getDefault().getWorkingCopy(aResource);

        if (wc instanceof OMWorkingCopy && ((OMWorkingCopy) wc).getRootElement() instanceof OrgModel
                && ((OMWorkingCopy) wc).getRootElement().eResource() != null) {
            EObject orgModel = ((OMWorkingCopy) wc).getRootElement();
            return (OrgModel) orgModel;
        }

        return null;
    }

    /**
     * A simple class to compare the qualifiers of a System Action's associated privilege.
     */
    private static class ExpectedPrivAssoc {
        String privName;

        Collection<String> qualifiers;

        /**
         * Creates a new expectation.
         * 
         * @param aPrivilege
         *            the name of the associated privilege.
         * @param aQualifiers
         *            the optional privilege qualifiers.
         */
        public ExpectedPrivAssoc(String aPrivilege, String... aQualifiers) {
            privName = aPrivilege;
            qualifiers = (aQualifiers == null) ? Collections.emptyList() : Arrays.asList(aQualifiers);
        }

        /**
         * Compares the given privilege association. If the privilege is the same name as this expectation, the
         * qualifier values are then compared. If the qualifiers don't match, an assertion failure is raised.
         * 
         * @param aAssocPrivilege
         *            a SystemAction's associated Privilege (and qualifiers)
         * @return true if the association is a match (and it's qualifiers).
         */
        public boolean compare(PrivilegeAssociation aAssocPrivilege) {
            if (!Objects.equals(privName, aAssocPrivilege.getPrivilege().getName())) {
                return false;
            }

            compareQualifiers(aAssocPrivilege.getQualifierValue());
            return true;
        }

        private void compareQualifiers(AttributeValue aActual) {
            Collection<String> actualValues;

            if (aActual == null) {
                actualValues = Collections.emptySet();
            }

            else if (aActual.getType() == AttributeType.ENUM_SET) {
                EList<EnumValue> enumSetValues = aActual.getEnumSetValues();
                actualValues = (enumSetValues == null) ? Collections.emptySet()
                        : enumSetValues.stream().map(v -> v.getValue()).collect(Collectors.toList());
            }

            else if (aActual.getType() == AttributeType.SET) {
                EList<String> setValues = aActual.getSetValues();
                actualValues = (setValues == null) ? Collections.emptySet() : setValues;
            }
            
            else {
                actualValues = Collections.singleton(aActual.getValue());
            }

            assertEquals(qualifiers.size(), actualValues.size());
            for (String actualValue : actualValues) {
                boolean found = false;
                for (String expected : qualifiers) {
                    if (Objects.equals(expected, actualValue)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    fail("Unexpected enum value: " + actualValue);
                }
            }
        }
    }
}
