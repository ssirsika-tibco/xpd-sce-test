/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.wm.test.om.transform;

import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;

import com.tibco.n2.directory.model.de.ModelOrgUnit;
import com.tibco.n2.directory.model.de.ModelTemplate;
import com.tibco.n2.directory.model.de.ModelType;
import com.tibco.n2.directory.model.de.OrgUnit;
import com.tibco.n2.directory.model.de.Organization;
import com.tibco.n2.directory.model.de.Position;
import com.tibco.n2.directory.model.de.Privilege;
import com.tibco.n2.directory.model.de.PrivilegeCategory;
import com.tibco.n2.directory.model.de.PrivilegeHolding;
import com.tibco.n2.directory.model.de.SystemAction;
import com.tibco.xpd.core.test.util.AbstractBuildingBaseResourceTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.resources.wc.OMWorkingCopy;
import com.tibco.xpd.om.transform.de.transform.OrgModelTransformer;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

import junit.framework.Assert;

/**
 * Abstract Test Class for Validating transformed DE Model.The extending class
 * should implement
 * {@link AbstractDETransformationTest#assertTransformedModel()} method.
 * 
 * @author aprasad
 * @since 21-Oct-2013
 */
public abstract class AbstractDETransformationTest extends
        AbstractBuildingBaseResourceTest {

    protected static final boolean RECURSE = true;

    protected static final boolean ASSERT_EXISTS = true;

    /**
     * Org Model resource
     */
    private static final String OM_RESOURCE_PATH =
            "OrgModelProject/Organization/OrgModel.om"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestName()
     * 
     * @return
     */
    @Override
    protected String getTestName() {
        return "OrgDeployModelTransformationTest"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestResources()
     * 
     * @return
     */
    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] { new TestResourceInfo(
                        "resources/OrgDeployModelTransformationTest", //$NON-NLS-1$
                        OM_RESOURCE_PATH) {
                } };
        return testResources;
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestPlugInId()
     * 
     * @return
     */
    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.wm.test"; //$NON-NLS-1$
    }

    /**
     * The test method creates the basic OrgModel, does the Org Model to DE
     * model transformation. Validates the transformed DE model .
     * 
     * @throws Exception
     */
    public void testOrgDeployModelTransformation() throws Exception {
        // Load the Model Resource
        IFile om_file = createOrgModelResource();
        // get OrgModel WorkingCopy
        WorkingCopy om_workingCopy = WorkingCopyUtil.getWorkingCopy(om_file);
        // obtain the OrgModel
        OrgModel om_orgModel = null;
        if (om_workingCopy instanceof OMWorkingCopy
                && ((OMWorkingCopy) om_workingCopy).getRootElement() instanceof OrgModel
                && ((OMWorkingCopy) om_workingCopy).getRootElement()
                        .eResource() != null) {
            om_orgModel =
                    (OrgModel) ((OMWorkingCopy) om_workingCopy)
                            .getRootElement();
        }
        assertNotNull("Org Model could not be loaded", om_orgModel); //$NON-NLS-1$
        // Transform to DE model
        ModelType deModel =
                new OrgModelTransformer().transformOrgModel(om_orgModel,
                        "1.0.0.123456789");
        Assert.assertNotNull(deModel);
        assertTransformedModel(deModel, om_orgModel);
    }

    /**
     * Provide custom assertion for transformed DE ModelType. This method will
     * only be called if transformed DE {@link ModelType} is not null. <br/>
     * It is expected that subclasses will only test deModel but source orgModel
     * is also passed in case it is needed.
     * 
     * @param deModel
     *            directory engine model after transformation.
     * @param orgModel
     *            source model.
     * 
     */
    protected abstract void assertTransformedModel(ModelType deModel,
            OrgModel orgModel);

    private IFile createOrgModelResource() {
        // Create the sample om model in this folder
        URI uri = URI.createPlatformResourceURI(OM_RESOURCE_PATH, true);
        // Get the Org Model name from the user profile settings
        if (uri.isPlatformResource()) {
            String path = uri.toPlatformString(true);
            if (path != null) {
                IFile file =
                        ResourcesPlugin.getWorkspace().getRoot()
                                .getFile(new Path(path));
                if (file != null && file.getProject() != null) {
                    return file;
                }
            }
        }

        return null;
    }

    /**
     * @param name
     * @return {@link ModelTemplate} with the given name if exists in the Model,
     *         null otherwise.
     */
    protected ModelTemplate findModelTemplateByName(ModelType deModel,
            String name, boolean assertExists) {
        for (ModelTemplate template : deModel.getModelTemplate()) {
            if (name.equals(template.getName())) {
                return template;
            }
        }
        if (assertExists) {
            fail(String.format("ModelTemplate '%s' not found.", name)); //$NON-NLS-1$
        }
        return null;
    }

    /**
     * @param systemActions
     *            , Collection of SystemActions
     * @param actionName
     * 
     * @return Returns {@link SystemAction} with the given name if exists in the
     *         given collection, null otherwise.
     */
    protected SystemAction getSystemActionByName(
            Collection<SystemAction> systemActions, String actionName) {
        if (actionName != null) {
            for (SystemAction systemAction : systemActions) {
                if (actionName != null
                        && actionName.equals(systemAction.getName())) {
                    return systemAction;
                }
            }
        }
        return null;
    }

    /**
     * @param privilegeHeld
     *            , Collection of PrivilegeHolding
     * @param privilegeName
     * @return Returns {@link PrivilegeHolding} with the given ID if exists in
     *         the given collection, null otherwise.
     */
    protected PrivilegeHolding getPrivilegeHoldingByName(
            Collection<PrivilegeHolding> privilegeHeld, String privilegeName) {
        if (privilegeName != null) {
            for (PrivilegeHolding privilegeHolding : privilegeHeld) {
                if (privilegeHolding.getPrivilege() != null
                        && privilegeName.equals(privilegeHolding.getPrivilege()
                                .getName())) {
                    return privilegeHolding;
                }
            }
        }
        return null;
    }

    /**
     * 
     * @param modelPositions
     *            , Collection of Model positions
     * @param string
     *            , Name of the ModelPosition to search.
     * @return, {@link Position} with the given name if exists in the given
     *          collection, null otherwise.
     */
    protected Position getModelPositionByName(
            Collection<Position> modelPositions, String modelPositionName) {
        if (modelPositionName != null) {
            for (Position modelPosition : modelPositions) {
                if (modelPositionName.equals(modelPosition.getName())) {
                    return modelPosition;
                }
            }
        }
        return null;
    }

    /**
     * @param privileges
     *            , Collection of {@link Privilege}
     * @param privilegeName
     *            , name of the privilege to look for.
     * @return {@link Privilege} with the given name if exists in the given
     *         collection, null otherwise.
     */
    protected Privilege getPrivilegeByName(EList<Privilege> privileges,
            String privilegeName) {
        if (privilegeName != null) {
            for (Privilege privilege : privileges) {
                if (privilege.getName().equals(privilegeName)) {
                    return privilege;
                }
            }
        }
        return null;
    }

    /**
     * Gets the Privilege that is present in the DE Model, also fetches the
     * privilege in the PrivilegeCategories.
     * 
     * @param deModel
     * @param privilegeName
     * @return the {@link Privilege} in the entire DE model by name.
     */
    protected Privilege getPrivilegeByName(ModelType deModel,
            String privilegeName) {

        Privilege privilegeByName =
                getPrivilegeByName(deModel.getPrivilege(), privilegeName);

        if (privilegeByName != null) {
            return privilegeByName;
        }

        return getPrivilegeInCategory(deModel.getPrivilegeCategory(),
                privilegeName);

    }

    /**
     * Gets the Privilege of the passed name from the Privilege Catogeries.
     * 
     * @param privilegeCategories
     * @param privilegeName
     * @return
     */
    private Privilege getPrivilegeInCategory(
            EList<PrivilegeCategory> privilegeCategories, String privilegeName) {

        for (PrivilegeCategory privilegeCategory : privilegeCategories) {

            Privilege privilegeByName =
                    getPrivilegeByName(privilegeCategory.getPrivilege(),
                            privilegeName);

            if (privilegeByName != null) {
                return privilegeByName;
            }

            Privilege privilegeFromcategories =
                    getPrivilegeInCategory(privilegeCategory.getPrivilegeCategory(),
                            privilegeName);

            if (privilegeFromcategories != null) {
                return privilegeFromcategories;
            }
        }

        return null;
    }

    /**
     * Finds organization by name in a deModel.
     * 
     * @param deModel
     *            deModel in which to search.
     * @param name
     *            the name of organization to find.
     * @param recursive
     *            if search should recurse.
     * @param assertExists
     *            if assertion error should be thrown when element not found.
     * @return found organization or 'null' (if assertExists is 'false')
     */
    protected Organization findOrganizationByName(ModelType deModel,
            String name, boolean assertExists) {
        for (Organization o : deModel.getOrganization()) {
            if (name.equals(o.getName())) {
                return o;
            }
        }
        if (assertExists) {
            fail(String.format("Organization '%s' not found.", name)); //$NON-NLS-1$
        }
        return null;
    }

    /**
     * Finds OrgUnit (breadth first) by name within descendants of an
     * organization.
     * 
     * @param organization
     *            organization in which to search.
     * @param name
     *            the name of org unit to find.
     * @param recursive
     *            if search should recurse.
     * @param assertExists
     *            if assertion error should be thrown when element not found.
     * @return found org unit or 'null' (if assertExists is 'false')
     */
    protected OrgUnit findOrgUnitByName(Organization organization, String name,
            boolean recursive, boolean assertExists) {
        for (OrgUnit o : organization.getOrgUnit()) {
            if (name.equals(o.getName())) {
                return o;
            }
        }
        if (recursive) {
            for (OrgUnit subOu : organization.getOrgUnit()) {
                OrgUnit found = findOrgUnitByName(subOu, name, true);
                if (found != null) {
                    return found;
                }
            }
        }
        if (assertExists) {
            fail(String
                    .format("OrgUnit '%s' not found inside organization '%s' (recursive=%b).", //$NON-NLS-1$
                            name,
                            organization.getName(),
                            recursive));
        }
        return null;
    }

    /**
     * Finds ModelOrgUnit (breadth first) by name within descendants of an
     * ModelTemplate.
     * 
     * @param modelTemplate
     *            model template in which to search.
     * @param name
     *            the name of model org unit to find.
     * @param recursive
     *            if search should recurse.
     * @param assertExists
     *            if assertion error should be thrown when element not found.
     * @return found model org unit or 'null' (if assertExists is 'false')
     */
    protected ModelOrgUnit findModelOrgUnitByName(ModelTemplate modelTemplate,
            String name, boolean recursive, boolean assertExists) {
        for (ModelOrgUnit o : modelTemplate.getModelOrgUnit()) {
            if (name.equals(o.getName())) {
                return o;
            }
        }
        if (recursive) {
            for (ModelOrgUnit subOu : modelTemplate.getModelOrgUnit()) {
                ModelOrgUnit found = findModelOrgUnitByName(subOu, name, true);
                if (found != null) {
                    return found;
                }
            }
        }
        if (assertExists) {
            fail(String
                    .format("ModelOrgUnit '%s' not found inside model template '%s' (recursive=%b).", //$NON-NLS-1$
                            name,
                            modelTemplate.getName(),
                            recursive));
        }
        return null;
    }

    /**
     * Finds OrgUnit (breadth first) by name within descendants of an orgUnit
     * (ou).
     * 
     * @param orgUnit
     *            org unit in which to search.
     * @param name
     *            the name of org unit to find.
     * @param recursive
     *            if search should recurse.
     * @param assertExists
     *            if assertion error should be thrown when element not found.
     * @return found org unit or 'null' (if assertExists is 'false')
     */
    protected OrgUnit findOrgUnitByName(OrgUnit orgUnit, String name,
            boolean recursive, boolean assertExists) {
        OrgUnit found = findOrgUnitByName(orgUnit, name, recursive);
        if (assertExists && found == null) {
            fail(String
                    .format("OrgUnit '%s' not found inside OrgUnit '%s'. (recursive=%b)", //$NON-NLS-1$
                            name,
                            orgUnit.getName(),
                            recursive));
            return null; // Unreachable code.
        } else {
            return found;
        }
    }

    /**
     * Finds ModelOrgUnit (breadth first) by name within descendant of an
     * modelOrgUnit (ou).
     * 
     * @param modelOrgUnit
     *            model org unit in which to search.
     * @param name
     *            the name of model org unit to find.
     * @param recursive
     *            if search should recurse.
     * @param assertExists
     *            if assertion error should be thrown when element not found.
     * @return found model org unit or 'null' (if assertExists is 'false')
     */
    protected ModelOrgUnit findModelOrgUnitByName(ModelOrgUnit modelOrgUnit,
            String name, boolean recursive, boolean assertExists) {
        ModelOrgUnit found =
                findModelOrgUnitByName(modelOrgUnit, name, recursive);
        if (assertExists && found == null) {
            fail(String
                    .format("ModelOrgUnit '%s' not found inside ModelOrgUnit '%s'. (recursive=%b)", //$NON-NLS-1$
                            name,
                            modelOrgUnit.getName(),
                            recursive));
            return null; // Unreachable code.
        } else {
            return found;
        }
    }

    private OrgUnit findOrgUnitByName(OrgUnit ou, String name, boolean recursive) {
        for (OrgUnit subOu : ou.getOrgUnit()) {
            if (name.equals(subOu.getName())) {
                return subOu;
            }
        }
        if (recursive) {
            for (OrgUnit subOu : ou.getOrgUnit()) {
                OrgUnit found = findOrgUnitByName(subOu, name, true);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }

    private ModelOrgUnit findModelOrgUnitByName(ModelOrgUnit ou, String name,
            boolean recursive) {
        for (ModelOrgUnit subOu : ou.getModelOrgUnit()) {
            if (name.equals(subOu.getName())) {
                return subOu;
            }
        }
        if (recursive) {
            for (ModelOrgUnit subOu : ou.getModelOrgUnit()) {
                ModelOrgUnit found = findModelOrgUnitByName(subOu, name, true);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }
}
