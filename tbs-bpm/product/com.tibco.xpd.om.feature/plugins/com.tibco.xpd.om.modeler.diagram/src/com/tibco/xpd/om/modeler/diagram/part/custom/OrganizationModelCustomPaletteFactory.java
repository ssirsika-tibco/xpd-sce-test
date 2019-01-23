package com.tibco.xpd.om.modeler.diagram.part.custom;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
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

import com.tibco.xpd.om.core.om.OrgElementType;
import com.tibco.xpd.om.core.om.OrgMetaModel;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.OrgUnitRelationshipType;
import com.tibco.xpd.om.core.om.OrganizationType;
import com.tibco.xpd.om.core.om.provider.OMModelImages;
import com.tibco.xpd.om.modeler.diagram.part.Messages;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelPaletteFactory;
import com.tibco.xpd.om.modeler.diagram.providers.OrganizationModelElementTypes;
import com.tibco.xpd.om.modeler.subdiagram.part.custom.IOrganizationModelDiagramConstants;
import com.tibco.xpd.om.modeler.subdiagram.part.custom.OMTypeCustomTypeCreationTool;
import com.tibco.xpd.om.modeler.subdiagram.part.custom.OrgUnitRelUnspecifiedTypeConnectionTool;

public class OrganizationModelCustomPaletteFactory extends
        OrganizationModelPaletteFactory {

    public void fillPalette(PaletteRoot paletteRoot, OrgModel om) {

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
        super.fillPalette(paletteRoot);

        // Now add our new palette types. We'll have a seperate drawer for each
        // type category i.e. Organization, OrgUnitrelationships
        List lstcreateNewNodesDrawer = createNewPaletteDrawers(om);

        for (Object object : lstcreateNewNodesDrawer) {
            paletteRoot.add((PaletteContainer) object);
        }

    }

    /**
     * Creates PaletteDrawers for relevant OrgElementType and populates each
     * drawer with those types defined in the metamodel of the OrgModel passed
     * in.
     * 
     * @param OrgModel
     * @return List<PaletteContainer>
     * 
     * @generated NOT
     */
    private List<PaletteContainer> createNewPaletteDrawers(OrgModel om) {
        List<PaletteContainer> lstPalContainers = null;
        OrgMetaModel embeddedMetamodel = om.getEmbeddedMetamodel();

        if (embeddedMetamodel != null) {
            EList<OrganizationType> organizationTypes =
                    embeddedMetamodel.getOrganizationTypes();
            EList<OrgUnitRelationshipType> orgUnitRelationshipTypes =
                    embeddedMetamodel.getOrgUnitRelationshipTypes();
            lstPalContainers = new ArrayList<PaletteContainer>();

            // Organization types
            if (!organizationTypes.isEmpty()) {
                PaletteDrawer paletteContainer =
                        new PaletteDrawer(
                                Messages.OrganizationModelCustomPaletteFactory_TypedOrganization_palette_title);
                paletteContainer.setId("OrganizationTypesContainer"); //$NON-NLS-1$
                paletteContainer
                        .setDescription(Messages.OrganizationModelCustomPaletteFactory_TypedOrganization_palette_desc);

                for (OrganizationType orgType : organizationTypes) {
                    paletteContainer
                            .add(createOrganization1CreationTool(orgType));
                }
                lstPalContainers.add(paletteContainer);
            }

            // OrgunitRelationship types
            if (!orgUnitRelationshipTypes.isEmpty()) {
                PaletteDrawer paletteContainer =
                        new PaletteDrawer(
                                Messages.OrganizationModelCustomPaletteFactory_TypedAssociation_palette_title);
                paletteContainer.setId("AssociationTypesContainer"); //$NON-NLS-1$
                paletteContainer
                        .setDescription(Messages.OrganizationModelCustomPaletteFactory_TypedAssociation_palette_desc);

                for (OrgUnitRelationshipType relType : orgUnitRelationshipTypes) {
                    paletteContainer
                            .add(createAssociationCreationTool(relType));
                }
                lstPalContainers.add(paletteContainer);
            }
        }

        return lstPalContainers;
    }

    /**
     * Creates an Organization creation tool entry and sets the passed in
     * OrganizationType in the ToolEntry request's extended data.
     * 
     * @param OrganizationType
     * @return ToolEntry
     */
    private ToolEntry createOrganization1CreationTool(OrganizationType orgType) {
        List<IElementType> types = new ArrayList<IElementType>(1);
        types.add(OrganizationModelElementTypes.Organization_1001);
        CustomNodeToolEntry entry =
                new CustomNodeToolEntry(
                        orgType.getDisplayName(),
                        Messages.OrganizationModelCustomPaletteFactory_createOrganization_toolEntry_desc,
                        types, orgType);

        Image img = null;
        img =
                ExtendedImageRegistry.INSTANCE.getImage(OMModelImages
                        .getImageObject(OMModelImages.IMAGE_ORGANISATION_TYPE));

        ImageDescriptor imgDesc = ImageDescriptor.createFromImage(img);

        entry.setSmallIcon(imgDesc);
        entry.setLargeIcon(entry.getSmallIcon());
        return entry;
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
    private ToolEntry createAssociationCreationTool(
            OrgUnitRelationshipType relType) {
        List<IElementType> types = new ArrayList<IElementType>(1);
        types.add(OrganizationModelElementTypes.OrgUnitRelationship_3001);
        OrgUnitRelLinkToolEntry entry =
                new OrgUnitRelLinkToolEntry(
                        relType.getDisplayName(),
                        Messages.OrganizationModelCustomPaletteFactory_createTypedAssocation_linkTool_desc,
                        types, relType);

        Image img = null;
        img =
                ExtendedImageRegistry.INSTANCE
                        .getImage(OMModelImages
                                .getImageObject(OMModelImages.IMAGE_ORG_UNIT_RELATIONSHIP_TYPE));

        ImageDescriptor imgDesc =
                ExtendedImageRegistry.INSTANCE.getImageDescriptor(img);

        entry.setSmallIcon(imgDesc);
        entry.setLargeIcon(entry.getSmallIcon());

        // This is non-hierarchical so set the relevant tool property
        entry.setToolProperty(IOrganizationModelDiagramConstants.OMCreationToolIsHierarchicalRel,
                new Boolean(false));

        return entry;
    }

    /**
     * 
     * A custom ToolEntry that creates a customised tool
     * OMTypeCustomTypeCreationTool
     * 
     * @author rgreen
     * 
     */
    private static class CustomNodeToolEntry extends PaletteToolEntry {
        private final List elementTypes;

        private OrgElementType type;

        public CustomNodeToolEntry(String title, String description,
                List elementTypes, OrgElementType type) {
            super(null, title, null);
            this.setDescription(description);
            this.elementTypes = elementTypes;
            this.type = type;
        }

        @Override
        public Tool createTool() {
            Tool tool = new OMTypeCustomTypeCreationTool(elementTypes, type);
            tool.setProperties(getToolProperties());
            return tool;
        }
    }

    /**
     * 
     * A custom ToolEntry that creates a customised tool
     * OrgUnitRelUnspecifiedTypeConnectionTool
     * 
     * @generated NOT
     * 
     * @author rgreen
     * 
     */
    private static class OrgUnitRelLinkToolEntry extends ToolEntry {

        private final List relationshipTypes;

        private final OrgElementType relType;

        private OrgUnitRelLinkToolEntry(String title, String description,
                List relationshipTypes) {
            super(title, description, null, null);
            this.relationshipTypes = relationshipTypes;
            this.relType = null;
        }

        private OrgUnitRelLinkToolEntry(String title, String description,
                List relationshipTypes, OrgElementType relType) {
            super(title, description, null, null);
            this.relationshipTypes = relationshipTypes;
            this.relType = relType;

        }

        @Override
        public Tool createTool() {
            // We create a custom tool to hold the
            // extra information in the tool request's extended data
            Tool tool =
                    new OrgUnitRelUnspecifiedTypeConnectionTool(
                            relationshipTypes, relType);
            tool.setProperties(getToolProperties());
            return tool;
        }
    }

}
