package com.tibco.xpd.om.modeler.subdiagram.part.custom;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.gef.Tool;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gmf.runtime.diagram.ui.internal.services.palette.PaletteToolEntry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.om.core.om.Feature;
import com.tibco.xpd.om.core.om.OrgElementType;
import com.tibco.xpd.om.core.om.OrgMetaModel;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.OrgUnitFeature;
import com.tibco.xpd.om.core.om.OrgUnitRelationshipType;
import com.tibco.xpd.om.core.om.OrgUnitType;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.OrganizationType;
import com.tibco.xpd.om.core.om.PositionFeature;
import com.tibco.xpd.om.core.om.provider.OMModelImages;
import com.tibco.xpd.om.modeler.subdiagram.part.Messages;
import com.tibco.xpd.om.modeler.subdiagram.part.OrganizationModelPaletteFactory;
import com.tibco.xpd.om.modeler.subdiagram.part.OrganizationModelSubDiagramEditorPlugin;
import com.tibco.xpd.om.modeler.subdiagram.providers.OrganizationModelElementTypes;

public class OrganizationTypePaletteFactory extends
        OrganizationModelPaletteFactory {

    public void fillOrganizationTypePalette(PaletteRoot paletteRoot,
            Organization org) {

        // First action will be to clean the palette so we can start afresh.
        List lst = paletteRoot.getChildren();
        List<PaletteEntry> lst2remove = new ArrayList<PaletteEntry>();

        for (Object obj : lst) {
            if (obj instanceof PaletteEntry) {
                PaletteEntry pe = (PaletteEntry) obj;

                if ("standardGroup".equals(pe.getId())) { //$NON-NLS-1$
                    continue;
                }
                lst2remove.add(pe);
            }
        }

        for (PaletteEntry entry : lst2remove) {
            paletteRoot.remove(entry);
        }

        // Fill with the standard OM tools
        super.fillPalette(paletteRoot, org.isDynamic());

        // Now add our new palette features
        List<PaletteContainer> lstcreateNewNodesDrawer =
                createNewNodesDrawer(org);

        for (Object object : lstcreateNewNodesDrawer) {
            paletteRoot.add((PaletteContainer) object);
        }

    }

    /**
     * Looks through the passed in Organization to see if there are any typed
     * elements and features to add to the palette.
     * 
     * Returns a list of drawers (PaletteContainers) containing creation tools.
     * 
     * There will be one drawer for each OrgUnitType with creation tools for all
     * the features defined in it (OrgUnitFeatures and PositionFeatures). If
     * there are no features defined in the OrgUnitType then a drawer will not
     * be create (we don't want an empty drawer).
     * 
     * There will be a single drawer for OrgUnitRelationshipType elements (if
     * any are defined).
     * 
     * @generated NOT
     */
    private List<PaletteContainer> createNewNodesDrawer(Organization org) {

        List<PaletteContainer> lstPalContainers =
                new ArrayList<PaletteContainer>();
        int idx = 0;

        OrganizationType organizationType = org.getOrganizationType();
        if (organizationType != null) {

            // A Set to collect all the OrgUnitTypes
            Set<OrgUnitType> orgUnitTypes = new HashSet<OrgUnitType>();

            EList<OrgUnitFeature> orgUnitFeaturesOfOrgType =
                    organizationType.getOrgUnitFeatures();

            for (OrgUnitFeature f : orgUnitFeaturesOfOrgType) {
                OrgUnitType type = f.getFeatureType();

                if (type != null) {
                    if (!orgUnitTypes.contains(type)) {
                        orgUnitTypes.add(type);
                        addSubtypes(type, orgUnitTypes);
                    }
                }

            }
            // Drawer for OgranizationType
            PaletteDrawer orgTypeDrawer =
                    new PaletteDrawer(organizationType.getDisplayName());
            orgTypeDrawer.setId("OrganizationTypedElements" + idx);
            orgTypeDrawer.setDescription(organizationType.getDisplayName());

            for (OrgUnitFeature f : organizationType.getOrgUnitFeatures()) {
                orgTypeDrawer.add(createOrgUnitFeatureCreationTool(f));

            }
            lstPalContainers.add(orgTypeDrawer);

            // Drawers for OgranizationUnitTypes
            for (OrgUnitType orgUnitType : orgUnitTypes) {
                PaletteDrawer orgUnitTypeDrawer =
                        new PaletteDrawer(orgUnitType.getDisplayName());
                orgUnitTypeDrawer.setDescription(orgUnitType.getDisplayName());

                for (OrgUnitFeature f : orgUnitType.getOrgUnitFeatures()) {
                    orgUnitTypeDrawer.add(createOrgUnitFeatureCreationTool(f));
                }
                for (PositionFeature f : orgUnitType.getPositionFeatures()) {
                    orgUnitTypeDrawer.add(createPositionFeatureCreationTool(f));
                }
                lstPalContainers.add(orgUnitTypeDrawer);
            }
        }

        // There may also be some OrgUnitRelationshipType tools to create. We
        // only want one drawer to hold all the tools.
        EObject container = org.eContainer();
        if (container instanceof OrgModel) {
            OrgModel om = (OrgModel) container;
            OrgMetaModel embeddedMetamodel = om.getEmbeddedMetamodel();

            if (embeddedMetamodel != null) {
                EList<OrgUnitRelationshipType> relTypes =
                        embeddedMetamodel.getOrgUnitRelationshipTypes();

                if (!relTypes.isEmpty()) {
                    PaletteDrawer paletteContainer =
                            new PaletteDrawer(
                                    Messages.OrganizationTypePaletteFactory_PaletteLabel);
                    paletteContainer.setId("AssociationTypesContainer");
                    paletteContainer
                            .setDescription(Messages.OrganizationTypePaletteFactory_PaletteDesc);

                    for (OrgUnitRelationshipType relType : relTypes) {
                        // paletteContainer
                        // .add(createOrgUnitRelationshipCreationTool(
                        // relType, new Boolean(true)));
                        paletteContainer
                                .add(createOrgUnitRelationshipCreationTool(relType,
                                        new Boolean(false)));
                    }
                    lstPalContainers.add(paletteContainer);
                }
            }

        }

        return lstPalContainers;
    }

    private void addSubtypes(OrgUnitType orgUnitType,
            Set<OrgUnitType> orgUnitTypes) {

        EList<OrgUnitFeature> orgUnitFeatures =
                orgUnitType.getOrgUnitFeatures();

        for (OrgUnitFeature f : orgUnitFeatures) {
            OrgUnitType t = f.getFeatureType();
            if (t != null && !orgUnitTypes.contains(t)) {
                orgUnitTypes.add(t);
                addSubtypes(t, orgUnitTypes);
            }

        }
    }

    /**
     * @param Feature
     * @return ToolEntry
     */
    private ToolEntry createOrgUnitFeatureCreationTool(Feature feature) {
        List<IElementType> types = new ArrayList<IElementType>(1);
        types.add(OrganizationModelElementTypes.OrgUnit_1001);
        OMCustomNodeToolEntry entry =
                new OMCustomNodeToolEntry(feature.getDisplayName(),
                        Messages.OrganizationUnit2CreationTool_desc, types,
                        feature);

        Image img = null;
        img =
                ExtendedImageRegistry.INSTANCE.getImage(OMModelImages
                        .getImageObject(OMModelImages.IMAGE_ORG_UNIT_FEATURE));

        ImageDescriptor imgDesc = ImageDescriptor.createFromImage(img);

        entry.setSmallIcon(imgDesc);
        entry.setLargeIcon(entry.getSmallIcon());
        // entry.setToolProperty("feature", feature);
        return entry;
    }

    /**
     * @param Feature
     * @return ToolEntry
     */
    private ToolEntry createPositionFeatureCreationTool(Feature feature) {
        List<IElementType> types = new ArrayList<IElementType>(1);
        types.add(OrganizationModelElementTypes.Position_2001);
        OMCustomNodeToolEntry entry =
                new OMCustomNodeToolEntry(feature.getDisplayName(),
                        Messages.Position3CreationTool_desc, types, feature);

        Image img = null;
        img =
                ExtendedImageRegistry.INSTANCE.getImage(OMModelImages
                        .getImageObject(OMModelImages.IMAGE_POSITION_FEATURE));

        ImageDescriptor imgDesc = ImageDescriptor.createFromImage(img);

        entry.setSmallIcon(imgDesc);
        entry.setLargeIcon(entry.getSmallIcon());
        // entry.setToolProperty("feature", feature);
        return entry;
    }

    /**
     * 
     * Custom NodeToolEntry for OM to handle setting featuretype on creation.
     * 
     * Extends CustomPaletteEntryTool so that palette DnD is enabled.
     * 
     * @author rgreen
     * 
     */
    private static class OMCustomNodeToolEntry extends PaletteToolEntry {
        private final List elementTypes;

        private Feature feature;

        public OMCustomNodeToolEntry(String title, String description,
                List elementTypes, Feature feature) {
            super(null, title, null);
            this.setDescription(description);
            this.elementTypes = elementTypes;
            this.feature = feature;
        }

        @Override
        public Tool createTool() {
            Tool tool = new OMTypeCustomTypeCreationTool(elementTypes, feature);
            tool.setProperties(getToolProperties());
            return tool;
        }
    }

    /**
     * Creates an Association creation tool entry and sets the passed in
     * OrgUnitRelationshipType in the ToolEntry request's extended data.
     * 
     * Association means a non-hierarchical relationship
     * 
     * @param OrganizationType
     * @return ToolEntry
     */
    private ToolEntry createOrgUnitRelationshipCreationTool(
            OrgUnitRelationshipType relType, Boolean isHierarchical) {
        List<IElementType> types = new ArrayList<IElementType>(1);
        types.add(OrganizationModelElementTypes.OrgUnitRelationship_3001);
        OrgUnitRelLinkToolEntry entry =
                new OrgUnitRelLinkToolEntry(
                        relType.getDisplayName(),
                        Messages.OrganizationTypePaletteFactory_OrgUnitRelLinkToolEntryDesc,
                        types, relType, isHierarchical);

        if (isHierarchical.booleanValue() == Boolean.TRUE) {
            entry.setSmallIcon(OrganizationModelSubDiagramEditorPlugin
                    .getBundledImageDescriptor("icons/obj16/hierarchy.png")); //$NON-NLS-1$
        } else {

            Image img = null;
            img =
                    ExtendedImageRegistry.INSTANCE
                            .getImage(OMModelImages
                                    .getImageObject(OMModelImages.IMAGE_ORG_UNIT_RELATIONSHIP_TYPE));

            ImageDescriptor imgDesc = ImageDescriptor.createFromImage(img);

            entry.setSmallIcon(imgDesc); //$NON-NLS-1$            
        }
        entry.setLargeIcon(entry.getSmallIcon());

        return entry;
    }

    /**
     * 
     * A custom ToolEntry that creates a customised tool
     * OrgUnitRelUnspecifiedTypeConnectionTool
     * 
     * @author rgreen
     * 
     */
    private static class OrgUnitRelLinkToolEntry extends ToolEntry {

        private final List relationshipTypes;

        private final OrgElementType relType;

        private final Boolean isHierarchical;

        private OrgUnitRelLinkToolEntry(String title, String description,
                List relationshipTypes) {
            super(title, description, null, null);
            this.relationshipTypes = relationshipTypes;
            this.relType = null;
            this.isHierarchical = null;
        }

        private OrgUnitRelLinkToolEntry(String title, String description,
                List relationshipTypes, OrgElementType relType) {
            super(title, description, null, null);
            this.relationshipTypes = relationshipTypes;
            this.relType = relType;
            this.isHierarchical = null;

        }

        private OrgUnitRelLinkToolEntry(String title, String description,
                List relationshipTypes, OrgElementType relType,
                Boolean isHierarchical) {
            super(title, description, null, null);
            this.relationshipTypes = relationshipTypes;
            this.relType = relType;
            this.isHierarchical = isHierarchical;

        }

        @Override
        public Tool createTool() {
            // We create a custom tool to hold the
            // extra information in the tool request's extended data
            Tool tool =
                    new OrgUnitRelUnspecifiedTypeConnectionTool(
                            relationshipTypes, relType, isHierarchical);
            tool.setProperties(getToolProperties());
            return tool;
        }
    }

}
