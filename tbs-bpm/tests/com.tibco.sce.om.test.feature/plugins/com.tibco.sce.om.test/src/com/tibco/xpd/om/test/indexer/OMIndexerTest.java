/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.test.indexer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.om.core.om.Attribute;
import com.tibco.xpd.om.core.om.Group;
import com.tibco.xpd.om.core.om.Location;
import com.tibco.xpd.om.core.om.LocationType;
import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.core.om.OMFactory;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgMetaModel;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.OrgUnitFeature;
import com.tibco.xpd.om.core.om.OrgUnitType;
import com.tibco.xpd.om.core.om.OrganizationType;
import com.tibco.xpd.om.core.om.PositionType;
import com.tibco.xpd.om.resources.ui.types.OMTypesFactory;
import com.tibco.xpd.om.resources.wc.OMWorkingCopy;
import com.tibco.xpd.om.test.AbstractOMTestCase;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.indexer.IndexerService;
import com.tibco.xpd.resources.ui.types.TypeInfo;

/**
 * Test the Organization Model indexer. This will copy an OM resource from this
 * plug-in into the test project and update it to ensure that all types of
 * elements that get indexed are present. The indexer will then be checked
 * whether it has managed to index all elements.
 * 
 * @author njpatel
 * 
 */
public class OMIndexerTest extends AbstractOMTestCase {

    private static final String RESOURCE_PATH = "/resources/IndexerTestOrgModel.om"; //$NON-NLS-1$
    private static final String INDEXER_ID = "com.tibco.xpd.om.resources.indexing.omIndexer"; //$NON-NLS-1$
    private static final String ID_ATTR = "id"; //$NON-NLS-1$

    private final TransactionalEditingDomain ed = XpdResourcesPlugin
            .getDefault().getEditingDomain();

    /**
     * Test the indexer by creating a model and checking whether all elements of
     * interest are indexed.
     * 
     * @throws Exception
     */
    public void testIndexer() throws Exception {
        OrgModel model = loadModel();
        CompoundCommand ccmd = new CompoundCommand();
        /*
         * Need to add a Group and Location to complete the model to test
         */
        Group grp = OMFactory.eINSTANCE.createGroup();
        grp.setName("MyGroup"); //$NON-NLS-1$
        Command cmd = AddCommand.create(ed, model, OMPackage.eINSTANCE
                .getOrgModel_Groups(), grp);
        assertTrue("Cannot execute command to add a Group to the Model", cmd //$NON-NLS-1$
                .canExecute());
        ccmd.append(cmd);

        Location loc = OMFactory.eINSTANCE.createLocation();
        loc.setName("MyLocation"); //$NON-NLS-1$
        cmd = AddCommand.create(ed, model, OMPackage.eINSTANCE
                .getOrgModel_Locations(), loc);
        assertTrue("Cannot execute command to add a Location to the Model", cmd //$NON-NLS-1$
                .canExecute());
        ccmd.append(cmd);

        assertTrue(
                "Cannot execute compound command to add a Group and Location to the Model", //$NON-NLS-1$
                ccmd.canExecute());
        ed.getCommandStack().execute(ccmd);

        TestUtil.waitForJobs();

        IndexerService service = XpdResourcesPlugin.getDefault()
                .getIndexerService();
        assertNotNull("Unable to acquire the Indexer Service", service); //$NON-NLS-1$

        // Validate capability category
        validateElement(model, service,
                OMTypesFactory.TYPE_CAPABILITY_CATEGORY, OMPackage.eINSTANCE
                        .getOrgModel_CapabilityCategories());
        // Validate capability
        validateElement(model, service, OMTypesFactory.TYPE_CAPABILITY,
                OMPackage.eINSTANCE.getOrgModel_Capabilities());
        // Validate group
        validateElement(model, service, OMTypesFactory.TYPE_GROUP,
                OMPackage.eINSTANCE.getOrgModel_Groups());
        // Validate location
        validateElement(model, service, OMTypesFactory.TYPE_LOCATION,
                OMPackage.eINSTANCE.getOrgModel_Locations());

        // Validate organization
        validateElement(model, service, OMTypesFactory.TYPE_ORGANIZATION,
                OMPackage.eINSTANCE.getOrgModel_Organizations());

        // Validate OrgUnits
        validateElement(model.getOrganizations().get(0), service,
                OMTypesFactory.TYPE_ORG_UNIT, OMPackage.eINSTANCE
                        .getOrganization_Units());

        // Validate positions
        for (OrgUnit unit : model.getOrganizations().get(0).getUnits()) {
            if (!unit.getPositions().isEmpty()) {
                validateElement(unit, service, OMTypesFactory.TYPE_POSITION,
                        OMPackage.eINSTANCE.getOrgUnit_Positions());
                break;
            }
        }

        // Validate privilege category
        validateElement(model, service, OMTypesFactory.TYPE_PRIVILEGE_CATEGORY,
                OMPackage.eINSTANCE.getOrgModel_PrivilegeCategories());
        // Validate privilege
        validateElement(model, service, OMTypesFactory.TYPE_PRIVILEGE,
                OMPackage.eINSTANCE.getOrgModel_Privileges());
        // Validate resources
        validateElement(model, service, OMTypesFactory.TYPE_RESOURCE,
                OMPackage.eINSTANCE.getOrgModel_Resources());

        validateMetaModel(model.getEmbeddedMetamodel(), service);
    }

    /**
     * Validate the indexing of the meta model.
     * 
     * @param metaModel
     * @param service
     */
    private void validateMetaModel(OrgMetaModel metaModel,
            IndexerService service) {

        // Validate location types
        validateElement(metaModel, service, OMTypesFactory.TYPE_LOCATION_TYPE,
                OMPackage.eINSTANCE.getOrgMetaModel_LocationTypes());
        // Validate organization types
        validateElement(metaModel, service,
                OMTypesFactory.TYPE_ORGANIZATION_TYPE, OMPackage.eINSTANCE
                        .getOrgMetaModel_OrganizationTypes());
        // Validate OrgUnit relationship types
        validateElement(metaModel, service, OMTypesFactory.TYPE_LOCATION_TYPE,
                OMPackage.eINSTANCE.getOrgMetaModel_LocationTypes());
        // Validate OrgUnit types
        validateElement(metaModel, service, OMTypesFactory.TYPE_ORG_UNIT_TYPE,
                OMPackage.eINSTANCE.getOrgMetaModel_OrgUnitTypes());

        // Validate position features
        validateElement(metaModel.getOrgUnitTypes().get(0), service,
                OMTypesFactory.TYPE_POSITION_FEATURE, OMPackage.eINSTANCE
                        .getOrgUnitType_PositionFeatures());

        // Validate position types
        validateElement(metaModel, service, OMTypesFactory.TYPE_POSITION_TYPE,
                OMPackage.eINSTANCE.getOrgMetaModel_PositionTypes());
        // Validate resource types
        validateElement(metaModel, service, OMTypesFactory.TYPE_RESOURCE_TYPE,
                OMPackage.eINSTANCE.getOrgMetaModel_ResourceTypes());

        // Validate the attributes in the meta model
        validateAttributes(metaModel, service);

        // Validate the OrgUnit features in the meta model
        validateOrgUnitFeatures(metaModel, service);
    }

    /**
     * Validate the {@link OrgUnitFeature}s in the embedded meta model. The
     * sample meta model has <code>OrgUnitFeature</code>s defined in the
     * Standard {@link OrganizationType} and {@link OrgUnitType}.
     * 
     * @param metaModel
     * @param service
     */
    private void validateOrgUnitFeatures(OrgMetaModel metaModel,
            IndexerService service) {
        EList<OrgUnitFeature> features = new BasicEList<OrgUnitFeature>();
        IndexerItemImpl criteria = new IndexerItemImpl();
        criteria.setType(OMTypesFactory.TYPE_ORG_UNIT_FEATURE.getTypeId());
        Collection<IndexerItem> result = service.query(INDEXER_ID, criteria);

        // Get the features from the Organization and OrgUnit types
        features.addAll(metaModel.getOrganizationTypes().get(0)
                .getOrgUnitFeatures());
        features
                .addAll(metaModel.getOrgUnitTypes().get(0).getOrgUnitFeatures());

        assertEquals(
                "Number of OrgUnit Features (in Organization and OrgUnit Type elements in the meta model) in the indexer", //$NON-NLS-1$
                features.size(), result.size());

        for (OrgUnitFeature feature : features) {
            String id = metaModel.eResource().getURIFragment(feature);
            assertNotNull(String.format(
                    "Cannot get id of OrgUnit Feature '%s' from the resource", //$NON-NLS-1$
                    feature.getDisplayName()), id);
            boolean found = false;
            for (IndexerItem item : result) {
                if (id.equals(item.get(ID_ATTR))) {
                    found = true;
                    // Compare the name in the indexer
                    assertEquals("Name of the OrgUnit Feature in the index", //$NON-NLS-1$
                            OMWorkingCopy.getQualifiedName(feature), item
                                    .getName());
                    break;
                }
            }
            if (!found) {
                fail(String.format("Item '%s' has not been indexed", feature //$NON-NLS-1$
                        .getDisplayName()));
            }
        }

    }

    /**
     * Validate the {@link Attribute}s in the embedded meta model. The sample
     * meta model has attributes defined in the Standard {@link LocationType}
     * and {@link PositionType}.
     * 
     * @param metaModel
     * @param service
     */
    private void validateAttributes(OrgMetaModel metaModel,
            IndexerService service) {
        EList<Attribute> attributes = new BasicEList<Attribute>();
        IndexerItemImpl criteria = new IndexerItemImpl();
        criteria.setType(OMTypesFactory.TYPE_ATTRIBUTE.getTypeId());
        Collection<IndexerItem> result = service.query(INDEXER_ID, criteria);

        // Collect all the attributes from the metaModel to compare to the
        // indexer
        attributes.addAll(metaModel.getLocationTypes().get(0).getAttributes());
        attributes.addAll(metaModel.getPositionTypes().get(0).getAttributes());

        assertEquals(
                "Number of Attributes (for OrgElementType elements in the meta model) in the indexer", //$NON-NLS-1$
                attributes.size(), result.size());

        for (Attribute attr : attributes) {
            String id = metaModel.eResource().getURIFragment(attr);
            assertNotNull(String.format(
                    "Cannot get id of attribute '%s' from the resource", attr //$NON-NLS-1$
                            .getDisplayName()), id);
            boolean found = false;
            for (IndexerItem item : result) {
                if (id.equals(item.get(ID_ATTR))) {
                    found = true;
                    // Compare the name in the indexer
                    assertEquals("Name of the attribute in the index", //$NON-NLS-1$
                            OMWorkingCopy.getQualifiedName(attr), item
                                    .getName());
                    break;
                }
            }
            if (!found) {
                fail(String.format("Item '%s' has not been indexed", attr //$NON-NLS-1$
                        .getDisplayName()));
            }
        }
    }

    /**
     * Create the Org model from the local resource and add a Group and Location
     * to fill in all elements.
     * 
     * @throws CoreException
     * @throws IOException
     */
    private OrgModel loadModel() throws CoreException, IOException {
        final InputStream inStream = getClass().getResourceAsStream(
                RESOURCE_PATH);
        assertNotNull(
                "Cannot read the sample OrgModel file from the test plug-in", //$NON-NLS-1$
                inStream);
        final IFile file = project.getFile(TEST_FILE_NAME);
        try {

            ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
                public void run(IProgressMonitor monitor) throws CoreException {
                    file.create(inStream, true, monitor);
                }

            }, new NullProgressMonitor());

            TestUtil.waitForJobs();

            assertTrue("Failed to create OM file", file.isAccessible()); //$NON-NLS-1$

            // Load the model
            WorkingCopy wc = XpdResourcesPlugin.getDefault().getWorkingCopy(
                    file);
            assertNotNull(
                    "Cannot get working copy of the newly created OM file", wc); //$NON-NLS-1$
            assertTrue("Failed to get OrgModel from the newly created OM file", //$NON-NLS-1$
                    wc.getRootElement() instanceof OrgModel);

            return (OrgModel) wc.getRootElement();

        } finally {
            if (inStream != null) {
                inStream.close();
            }
        }
    }

    /**
     * Validate the given (feature) child element of the model with the indexer
     * entry.
     * 
     * @param parent
     * @param service
     * @param typeInfo
     * @param feature
     */
    private void validateElement(EObject parent, IndexerService service,
            TypeInfo typeInfo, EStructuralFeature feature) {
        IndexerItemImpl item = new IndexerItemImpl();
        item.setType(typeInfo.getTypeId());

        Object element = parent.eGet(feature);
        if (element instanceof EList<?>) {
            EList<?> list = (EList<?>) element;

            Collection<IndexerItem> result = service.query(INDEXER_ID, item);
            assertEquals("Number of " + feature.getName() + " indexed", list //$NON-NLS-1$ //$NON-NLS-2$
                    .size(), result.size());

            for (Object obj : list) {
                assertTrue("Object is not a NamedElement", //$NON-NLS-1$
                        obj instanceof NamedElement);
                NamedElement elem = (NamedElement) obj;
                String id = parent.eResource().getURIFragment(elem);
                assertNotNull("Failed to get ID of element in the model", id); //$NON-NLS-1$
                boolean found = false;
                for (IndexerItem indexedItem : result) {
                    if (indexedItem.get(ID_ATTR).equals(id)) {
                        found = true;
                        // Confirm the name has been indexed correctly
                        assertEquals("Name of the " + feature.getName() //$NON-NLS-1$
                                + " in the index", OMWorkingCopy //$NON-NLS-1$
                                .getQualifiedName(elem), indexedItem.getName());
                        break;
                    }
                }
                if (!found) {
                    fail(String.format("Item '%s' has not been indexed", elem //$NON-NLS-1$
                            .getDisplayName()));
                }
            }
        } else {
            fail(feature.getName() + " is not a collection"); //$NON-NLS-1$
        }
    }

}
