/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.test.cases;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.viewers.StructuredSelection;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.om.core.om.Attribute;
import com.tibco.xpd.om.core.om.AttributeType;
import com.tibco.xpd.om.core.om.AttributeValue;
import com.tibco.xpd.om.core.om.Location;
import com.tibco.xpd.om.core.om.LocationType;
import com.tibco.xpd.om.core.om.OMFactory;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgElementType;
import com.tibco.xpd.om.core.om.OrgMetaModel;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.OrgUnitFeature;
import com.tibco.xpd.om.core.om.OrgUnitType;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.OrganizationType;
import com.tibco.xpd.om.core.om.Position;
import com.tibco.xpd.om.core.om.ResourceType;
import com.tibco.xpd.om.resources.ui.internal.navigator.actions.OMDeleteAction;
import com.tibco.xpd.om.test.AbstractOMTestCase;
import com.tibco.xpd.om.test.OMTestUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Test the typed elements. This includes the following tests:
 * <ul>
 * <li>{@link #testChangeAttributeType()} - checks whether associated
 * {@link AttributeValue}s are removed when an {@link Attribute} type is changed
 * </li>
 * <li>{@link #testClearOrganizationType()} - checks whether the {@link OrgUnit}
 * and {@link Position} features are cleared when the {@link Organization}'s
 * type is unset</li>
 * <li>{@link #testDeleteAttribute()} - checks whether associated
 * <code>AttributeValue</code>s are removed</li>
 * <li>{@link #testDeleteHumanResourceType()} - checks whether a Human
 * {@link ResourceType} can be deleted: it shouldn't be allowed</li>
 * <li>{@link #testDeleteType()} - checks whether the <code>Organization</code>
 * 's type is cleared when the {@link OrganizationType} is deleted</li>
 * </ul>
 * 
 * @author njpatel
 * 
 */
public class TestOrgTypedElements extends AbstractOMTestCase {

    private static final String LOCATION_TYPE_LABEL = "My Location Type"; //$NON-NLS-1$

    private static final String LOCATION_TYPE_NAME = "MyLocationType"; //$NON-NLS-1$

    private static final String LOCATION_TYPE_ATTR_LABEL =
            "My$ Location Attribute"; //$NON-NLS-1$

    private static final String LOCATION_TYPE_ATTR_NAME = "MyLocationAttribute"; //$NON-NLS-1$

    private static final String LOCATION_LABEL = "My Location"; //$NON-NLS-1$

    private static final String LOCATION_NAME = "MyLocation"; //$NON-NLS-1$

    private static final String LOCATION_ATTR_VALUE = "MyValue"; //$NON-NLS-1$

    private static final String RESOURCETYPE_LABEL = "My Resource Type"; //$NON-NLS-1$

    private final TransactionalEditingDomain ed =
            XpdResourcesPlugin.getDefault().getEditingDomain();

    /**
     * Test changing attribute type. This should remove any attribute values set
     * for this attribute.
     * 
     * @throws Exception
     */
    public void testChangeAttributeType() throws Exception {
        OrgModel model =
                OMTestUtil.createModelWithDiagram(testResourceURI,
                        Boolean.TRUE,
                        Boolean.TRUE);
        OrgMetaModel metaModel = model.getEmbeddedMetamodel();
        assertNotNull("No embedded meta model found", metaModel); //$NON-NLS-1$

        // Add a new location type
        LocationType locationType =
                createLocationType(metaModel, LOCATION_TYPE_LABEL);
        assertNotNull("Failed to create Location Type", locationType); //$NON-NLS-1$
        assertEquals("New Location Type label", //$NON-NLS-1$
                LOCATION_TYPE_LABEL,
                locationType.getDisplayName());
        assertEquals("New Location Type name", LOCATION_TYPE_NAME, locationType //$NON-NLS-1$
                .getName());

        // Add a new location
        Location location = createLocation(model, LOCATION_LABEL);
        assertNotNull("Failed to create Location", location); //$NON-NLS-1$
        assertEquals("New Location label", LOCATION_LABEL, location //$NON-NLS-1$
                .getDisplayName());
        assertEquals("New Location name", LOCATION_NAME, location.getName()); //$NON-NLS-1$

        // Set the location type on the location and set the attribute value
        setLocationType(location, locationType);
        setLocationAttributeValue(location);

        // Test changing the attribute type
        _testChangeAttributeType(location);
    }

    /**
     * Test deleting the attribute. The expected result is the AttributeValue
     * associated with this Attribute should also be removed.
     * 
     * @throws Exception
     */
    public void testDeleteAttribute() throws Exception {
        OrgModel model =
                OMTestUtil.createModelWithDiagram(testResourceURI,
                        Boolean.TRUE,
                        Boolean.TRUE);
        OrgMetaModel metaModel = model.getEmbeddedMetamodel();
        assertNotNull("No embedded meta model found", metaModel); //$NON-NLS-1$

        // Add a new location type
        LocationType locationType =
                createLocationType(metaModel, LOCATION_TYPE_LABEL);
        assertNotNull("Failed to create Location Type", locationType); //$NON-NLS-1$
        assertEquals("New Location Type label", //$NON-NLS-1$
                LOCATION_TYPE_LABEL,
                locationType.getDisplayName());
        assertEquals("New Location Type name", LOCATION_TYPE_NAME, locationType //$NON-NLS-1$
                .getName());

        // Add a new location
        Location location = createLocation(model, LOCATION_LABEL);
        assertNotNull("Failed to create Location", location); //$NON-NLS-1$
        assertEquals("New Location label", LOCATION_LABEL, location //$NON-NLS-1$
                .getDisplayName());
        assertEquals("New Location name", LOCATION_NAME, location.getName()); //$NON-NLS-1$

        // Set the location type on the location and set the attribute value
        setLocationType(location, locationType);
        setLocationAttributeValue(location);

        // Test changing the attribute type
        _testDeleteAttribute(location);
    }

    /**
     * Test deleting the Organization Type. The Organization should have it's
     * type cleared.
     * 
     * @throws Exception
     */
    public void testDeleteType() throws Exception {
        OrgModel model =
                OMTestUtil.createModelWithDiagram(testResourceURI,
                        Boolean.TRUE,
                        Boolean.TRUE);
        assertEquals("Number of Organizations in the model", 1, model //$NON-NLS-1$
                .getOrganizations().size());
        Organization org = model.getOrganizations().get(0);
        assertNotNull("Expected the Organization to be of the Standard Organization Type", //$NON-NLS-1$
                org.getType());

        // Remove the type
        OMDeleteAction action = new OMDeleteAction(ed, true);
        action.selectionChanged(new StructuredSelection(org.getType()));
        assertTrue("The action to delete the Organization type is disabled after updating its selection", //$NON-NLS-1$
                action.isEnabled());
        action.run();
        TestUtil.waitForJobs();

        // Check that type has been deleted
        assertEquals("Number of Organization Types after deleting the Type", //$NON-NLS-1$
                0,
                model.getEmbeddedMetamodel().getOrganizationTypes().size());
        // Verify that the Organization's type has been cleared
        assertNull("Expected the Organization's type to be cleared after the Organization Type had been deleted", //$NON-NLS-1$
                org.getType());
    }

    /**
     * Test the deletion of the Human resource type. It should not be possible
     * to delete a human resource type. This uses the Project Explorer's
     * {@link OMDeleteAction}.
     * 
     * @throws Exception
     */
    public void testDeleteHumanResourceType() throws Exception {
        OrgModel model =
                OMTestUtil.createModelWithDiagram(testResourceURI,
                        Boolean.TRUE,
                        Boolean.TRUE);
        OrgMetaModel metamodel = model.getEmbeddedMetamodel();
        EList<ResourceType> resourceTypes = metamodel.getResourceTypes();
        assertEquals("Number of resource types in the model", 3, resourceTypes //$NON-NLS-1$
                .size());
        ResourceType resourceType = model.getHumanResourceType();
        assertTrue("Expected the resource type in the model to be a Human type", //$NON-NLS-1$
                resourceType.isHumanResourceType());

        // Set up action to delete it
        OMDeleteAction action = new OMDeleteAction(ed, true);
        action.selectionChanged(new StructuredSelection(resourceType));
        assertFalse("Delete action should not be enabled for the human resource type", //$NON-NLS-1$
                action.isEnabled());

        // Add another resource type
        ResourceType newResourceType = OMFactory.eINSTANCE.createResourceType();
        newResourceType.setDisplayName(RESOURCETYPE_LABEL);
        Command cmd =
                AddCommand.create(ed, metamodel, OMPackage.eINSTANCE
                        .getOrgMetaModel_ResourceTypes(), newResourceType);
        assertTrue("Cannot execute command to add a new resource type", cmd //$NON-NLS-1$
                .canExecute());
        ed.getCommandStack().execute(cmd);

        // Verify that the resource type has been added
        assertEquals("Number of resource types after adding a new resource type", //$NON-NLS-1$
                4,
                metamodel.getResourceTypes().size());

        // Make the new resource type into a human resource type
        cmd =
                SetCommand.create(ed, model, OMPackage.eINSTANCE
                        .getOrgModel_HumanResourceType(), newResourceType);
        assertTrue("Cannot execute command to set a human resource type", cmd //$NON-NLS-1$
                .canExecute());
        ed.getCommandStack().execute(cmd);
        // Verify that the human resource type has been changed
        assertTrue("The human resource type has not been switched after making the new resource type a Human type", //$NON-NLS-1$
                !resourceType.isHumanResourceType()
                        && newResourceType.isHumanResourceType());

        // Try deleting the resource types - this should not be allowed as it
        // will contain the human resource type
        cmd = DeleteCommand.create(ed, metamodel.getResourceTypes());
        assertFalse("Can execute command to delete a collection of resource types that contains a Human resource type", //$NON-NLS-1$
                cmd.canExecute());

        // Should be able to delete the non-human resource type
        cmd = DeleteCommand.create(ed, resourceType);
        assertTrue("Cannot execute command to delete a non-Human resource type", //$NON-NLS-1$
                cmd.canExecute());
        // Delete the resource type
        ed.getCommandStack().execute(cmd);

        // Only one human type should remain and it will be the human resource
        // type
        assertEquals("Number of resource types after deleting one", //$NON-NLS-1$
                3,
                metamodel.getResourceTypes().size());

        resourceType = model.getHumanResourceType();
        assertEquals("Human resource type", RESOURCETYPE_LABEL, resourceType //$NON-NLS-1$
                .getDisplayName());

        // Should not be able to unset the human resource type
        cmd =
                SetCommand.create(ed,
                        model,
                        OMPackage.eINSTANCE.getOrgModel_HumanResourceType(),
                        SetCommand.UNSET_VALUE);
        assertFalse("Able to unset the Human resource type", cmd.canExecute()); //$NON-NLS-1$
    }

    /**
     * Test clearing of the Organization type. This should also clear any
     * elements (features) set on the contained OrgUnits and the Positions.
     * 
     * @throws Exception
     */
    public void testClearOrganizationType() throws Exception {
        OrgModel model =
                OMTestUtil.createModelWithDiagram(testResourceURI,
                        Boolean.TRUE,
                        Boolean.TRUE);
        assertEquals("Number of Organization in the Model", 1, model //$NON-NLS-1$
                .getOrganizations().size());
        Organization org = model.getOrganizations().get(0);
        assertNotNull("The Organization type is not set on the Organization", //$NON-NLS-1$
                org.getType());
        OrganizationType type = (OrganizationType) org.getType();
        assertTrue("No OrgUnit features found in the Organization Type", !type //$NON-NLS-1$
                .getOrgUnitFeatures().isEmpty());
        OrgUnitFeature orgUnitFeature = type.getOrgUnitFeatures().get(0);
        assertNotNull("The OrgUnit feature does not have a type set", //$NON-NLS-1$
                orgUnitFeature.getFeatureType());
        OrgUnitType orgUnitType = orgUnitFeature.getFeatureType();
        assertTrue("No Position features found in the OrgUnit feature's type", //$NON-NLS-1$
                !orgUnitType.getPositionFeatures().isEmpty());

        // Create an OrgUnit and set it's feature
        OrgUnit unit = OMFactory.eINSTANCE.createOrgUnit();
        unit.setFeature(type.getOrgUnitFeatures().get(0));
        Position position = OMFactory.eINSTANCE.createPosition();
        position.setFeature(orgUnitType.getPositionFeatures().get(0));
        unit.getPositions().add(position);
        Command cmd =
                AddCommand.create(ed, org, OMPackage.eINSTANCE
                        .getOrganization_Units(), unit);
        assertTrue("Cannot execute command to add an OrgUnit to an Organization", //$NON-NLS-1$
                cmd.canExecute());
        ed.getCommandStack().execute(cmd);

        // Check that the OrgUnit and Position have been added and have a
        // feature set
        assertEquals("Number of OrgUnits in the Organization after adding one", //$NON-NLS-1$
                1,
                org.getUnits().size());
        unit = org.getUnits().get(0);
        assertEquals("Number of Positions in the OgrUnit after adding one", //$NON-NLS-1$
                1,
                unit.getPositions().size());
        position = unit.getPositions().get(0);
        assertNotNull("No feature set on the OrgUnit", unit.getFeature()); //$NON-NLS-1$
        assertNotNull("No feature set on the Position", position.getFeature()); //$NON-NLS-1$

        // Clear the Organization's type
        cmd =
                SetCommand.create(ed,
                        org,
                        OMPackage.eINSTANCE.getOrganization_OrganizationType(),
                        SetCommand.UNSET_VALUE);
        assertTrue("Cannot execute command to clear the Organization's type", //$NON-NLS-1$
                cmd.canExecute());
        ed.getCommandStack().execute(cmd);

        // Confirm that the type has been cleared
        assertNull("The type has not been cleared after unsetting it from the Organization", //$NON-NLS-1$
                org.getType());
        // Confirm that the OrgUnit and Position features have been cleared
        assertNull("The OrgUnit feature has not been cleared after unsetting the Organization's type", //$NON-NLS-1$
                unit.getFeature());
        assertNull("The Position feature has not been cleared after unsetting the Organization's type", //$NON-NLS-1$
                position.getFeature());
    }

    /**
     * Create a location type with the given label and adds a single attribute
     * (default Text type).
     * 
     * @param metaModel
     * @param locationTypeLabel
     * @return {@link LocationType} if created or <code>null</code> otherwise.
     */
    private LocationType createLocationType(OrgMetaModel metaModel,
            String locationTypeLabel) {
        LocationType newType = OMFactory.eINSTANCE.createLocationType();
        LocationType retType = null;
        newType.setDisplayName(locationTypeLabel);

        Command cmd =
                AddCommand.create(ed, metaModel, OMPackage.eINSTANCE
                        .getOrgMetaModel_LocationTypes(), newType);
        assertTrue("Cannot execute command to add new location type", cmd //$NON-NLS-1$
                .canExecute());
        ed.getCommandStack().execute(cmd);

        // Check that the location type was added
        EList<LocationType> types = metaModel.getLocationTypes();
        assertEquals("Number of Location Types after adding new Location Type", //$NON-NLS-1$
                2,
                types.size());
        for (LocationType type : types) {
            if (locationTypeLabel.equals(type.getDisplayName())) {
                retType = type;
                break;
            }
        }

        assertNotNull("Cannot find the new Location Type in the model", retType); //$NON-NLS-1$

        /*
         * Add an attribute (of default type which should be text) to the type
         */
        Attribute attr = OMFactory.eINSTANCE.createAttribute();
        attr.setDisplayName(LOCATION_TYPE_ATTR_LABEL);
        cmd =
                AddCommand.create(ed, retType, OMPackage.eINSTANCE
                        .getOrgElementType_Attributes(), attr);
        assertTrue("Cannot execute command to add an attribute to the new location type", //$NON-NLS-1$
                cmd.canExecute());
        ed.getCommandStack().execute(cmd);

        // Check if the attribute has been set
        EList<Attribute> attributes = retType.getAttributes();
        assertEquals("Attribute failed to be added to the Location Type", //$NON-NLS-1$
                1,
                attributes.size());
        Attribute attribute = attributes.get(0);
        assertEquals("New Attribute label", LOCATION_TYPE_ATTR_LABEL, attribute //$NON-NLS-1$
                .getDisplayName());
        assertEquals("New Attribute name", LOCATION_TYPE_ATTR_NAME, attribute //$NON-NLS-1$
                .getName());
        assertEquals("Type of the new attribute added to the Location Type", //$NON-NLS-1$
                AttributeType.TEXT,
                attribute.getType());

        return retType;
    }

    /**
     * Create a Location.
     * 
     * @param model
     * @param label
     * @return
     */
    private Location createLocation(OrgModel model, String label) {
        Location location = OMFactory.eINSTANCE.createLocation();
        location.setDisplayName(label);

        Command cmd =
                AddCommand.create(ed, model, OMPackage.eINSTANCE
                        .getOrgModel_Locations(), location);
        assertTrue("Cannot execute command to add new location", cmd //$NON-NLS-1$
                .canExecute());
        ed.getCommandStack().execute(cmd);
        // Confirm that location was added to the model
        EList<Location> locations = model.getLocations();
        assertEquals("Number of Locations after adding new Location", //$NON-NLS-1$
                1,
                locations.size());

        return locations.get(0);
    }

    /**
     * Set the location type on the new location.
     * 
     * @param location
     * @param type
     */
    private void setLocationType(Location location, LocationType type) {
        Command cmd =
                SetCommand.create(ed, location, OMPackage.eINSTANCE
                        .getLocation_LocationType(), type);
        assertTrue("Cannot execute command to set Location Type", cmd //$NON-NLS-1$
                .canExecute());
        ed.getCommandStack().execute(cmd);

        // Confirm the location type was set
        assertEquals("Location type set on new Location", type, location //$NON-NLS-1$
                .getType());
        // Confirm the location type has an attribute
        assertEquals("Number of Attributes set on the Location Type set on the new Location", //$NON-NLS-1$
                1,
                location.getType().getAttributes().size());
    }

    private void setLocationAttributeValue(Location location) {
        OrgElementType type = location.getType();
        EList<Attribute> attributes = type.getAttributes();
        Attribute attr = attributes.get(0);

        // Set attribute value on the location
        AttributeValue value = OMFactory.eINSTANCE.createAttributeValue();
        value.setAttribute(attr);
        value.setValue(LOCATION_ATTR_VALUE);

        Command cmd =
                AddCommand.create(ed, location, OMPackage.eINSTANCE
                        .getOrgTypedElement_AttributeValues(), value);
        assertTrue("Cannot execute command to set the Location's AttributeValue", //$NON-NLS-1$
                cmd.canExecute());
        ed.getCommandStack().execute(cmd);

        // Confirm that the attribute value has been added
        EList<AttributeValue> values = location.getAttributeValues();
        assertEquals("Number of attribute values in the Location after adding a value", //$NON-NLS-1$
                1,
                values.size());
        AttributeValue attrValue = values.get(0);
        assertNotNull("New AttributeValue not set", attrValue); //$NON-NLS-1$
        assertEquals("Attribute of the added attribute value", attr, attrValue //$NON-NLS-1$
                .getAttribute());
        assertEquals("Value of the added AttributeValue", //$NON-NLS-1$
                LOCATION_ATTR_VALUE,
                attrValue.getValue());
    }

    /**
     * Test changing the attribute type of the Location Type. Expected result is
     * that the attribute value should be removed for the Location as the value
     * will be wrong.
     * 
     * @param location
     */
    private void _testChangeAttributeType(Location location) {
        OrgElementType type = location.getType();
        Attribute attribute = type.getAttributes().get(0);

        Command cmd =
                SetCommand.create(ed, attribute, OMPackage.eINSTANCE
                        .getAttribute_Type(), AttributeType.DATE_TIME);
        assertTrue("Cannot execute command to change the Attribute type", cmd //$NON-NLS-1$
                .canExecute());
        ed.getCommandStack().execute(cmd);

        // Check that the type has been changed
        assertEquals("Type of attribute after changing it", //$NON-NLS-1$
                AttributeType.DATE_TIME,
                attribute.getType());

        // Confirm that the attribute value of the Location has been removed
        assertEquals("Number of attribute values of the Location after changing attribute type", //$NON-NLS-1$
                0,
                location.getAttributeValues().size());
    }

    private void _testDeleteAttribute(Location location) {
        OrgElementType type = location.getType();
        Attribute attribute = type.getAttributes().get(0);

        Command cmd = DeleteCommand.create(ed, attribute);
        assertTrue("Cannot execute command to change the Attribute type", cmd //$NON-NLS-1$
                .canExecute());
        ed.getCommandStack().execute(cmd);

        // Check that the attribute has been removed
        assertEquals("Number of attributes in the Location Type after deleting the Attribute", //$NON-NLS-1$
                0,
                type.getAttributes().size());

        // Confirm that the attribute value of the Location has been removed
        assertEquals("Number of attribute values of the Location after changing attribute type", //$NON-NLS-1$
                0,
                location.getAttributeValues().size());
    }

}
