package com.tibco.xpd.om.modeler.subdiagram.part;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.Tool;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeConnectionTool;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeCreationTool;

import com.tibco.xpd.om.modeler.subdiagram.part.custom.OrgUnitRelUnspecifiedTypeConnectionTool;
import com.tibco.xpd.om.modeler.subdiagram.providers.OrganizationModelElementTypes;

/**
 * @generated
 */
public class OrganizationModelPaletteFactory {

    /**
     * @generated NOT
     */
    public void fillPalette(PaletteRoot paletteRoot) {
        fillPalette(paletteRoot, false);
    }

    /**
     * Fill the palette of the editor.
     * 
     * @param paletteRoot
     * @param isDynamicOrganization
     *            <code>true</code> if editing a Dynamic Organization.
     */
    public void fillPalette(PaletteRoot paletteRoot,
            boolean isDynamicOrganization) {
        // Because we have dynamic palettes we may need to clean up depending on
        // what the user has done e.g. removed the Organization Type. Therefore
        // our first task is always to wipe the palette clean so that it can be
        // drawn afresh.
        List<?> lst = paletteRoot.getChildren();
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

        PaletteContainer nodes = createNodes1Group(isDynamicOrganization);

        List<?> children = nodes.getChildren();
        PaletteEntry orgEntry = null;

        // Need to remove the Organization Tool which is picked up from the
        // generator definition, and add a custom tool to define an Association.
        for (Object object : children) {
            if (object instanceof PaletteEntry) {
                PaletteEntry pe = (PaletteEntry) object;
                if (Messages.Organization1CreationTool_title.equals(pe
                        .getLabel())) {
                    orgEntry = pe;
                }
            }
        }

        if (orgEntry != null) {
            nodes.remove(orgEntry);
        }

        paletteRoot.add(nodes);

        PaletteContainer links = createLinks2Group();
        links.add(createAssociation1CreationTool());
        paletteRoot.add(links);

    }

    /**
     * Creates "Nodes" palette tool group
     * 
     * @param isDynamicOrganization
     *            <code>true</code> if editing a Dynamic Organization.
     * @generated NOT
     */
    private PaletteContainer createNodes1Group(boolean isDynamicOrganization) {
        PaletteDrawer paletteContainer =
                new PaletteDrawer(Messages.Nodes1Group_title);
        paletteContainer.setDescription(Messages.Nodes1Group_desc);
        // paletteContainer.add(createOrganization1CreationTool());
        paletteContainer.add(createOrganizationUnit2CreationTool());
        paletteContainer.add(createPosition3CreationTool());
        // paletteContainer.add(createDynamicOrganization4CreationTool());

        /*
         * Dynamic Organization cannot have Dynamic OrgUnit
         */
        if (!isDynamicOrganization) {
            paletteContainer.add(createDynamicOrganizationUnit5CreationTool());
        }
        return paletteContainer;
    }

    /**
     * Creates "Links" palette tool group
     * 
     * @generated
     */
    private PaletteContainer createLinks2Group() {
        PaletteDrawer paletteContainer =
                new PaletteDrawer(Messages.Links2Group_title);
        paletteContainer.add(createHierarchy1CreationTool());
        return paletteContainer;
    }

    /**
     * @generated
     */
    private ToolEntry createOrganization1CreationTool() {
        ToolEntry entry =
                new ToolEntry(Messages.Organization1CreationTool_title,
                        Messages.Organization1CreationTool_desc, null, null) {
                };
        return entry;
    }

    /**
     * @generated
     */
    private ToolEntry createOrganizationUnit2CreationTool() {
        List/* <IElementType> */types = new ArrayList/* <IElementType> */(1);
        types.add(OrganizationModelElementTypes.OrgUnit_1001);
        NodeToolEntry entry =
                new NodeToolEntry(Messages.OrganizationUnit2CreationTool_title,
                        Messages.OrganizationUnit2CreationTool_desc, types);
        entry.setSmallIcon(OrganizationModelElementTypes
                .getImageDescriptor(OrganizationModelElementTypes.OrgUnit_1001));
        entry.setLargeIcon(entry.getSmallIcon());
        return entry;
    }

    /**
     * @generated
     */
    private ToolEntry createPosition3CreationTool() {
        List/* <IElementType> */types = new ArrayList/* <IElementType> */(1);
        types.add(OrganizationModelElementTypes.Position_2001);
        NodeToolEntry entry =
                new NodeToolEntry(Messages.Position3CreationTool_title,
                        Messages.Position3CreationTool_desc, types);
        entry.setSmallIcon(OrganizationModelElementTypes
                .getImageDescriptor(OrganizationModelElementTypes.Position_2001));
        entry.setLargeIcon(entry.getSmallIcon());
        return entry;
    }

    /**
     * @generated
     */
    private ToolEntry createDynamicOrganization4CreationTool() {
        ToolEntry entry =
                new ToolEntry(Messages.DynamicOrganization4CreationTool_title,
                        Messages.DynamicOrganization4CreationTool_desc, null,
                        null) {
                };
        return entry;
    }

    /**
     * @generated
     */
    private ToolEntry createDynamicOrganizationUnit5CreationTool() {
        List/* <IElementType> */types = new ArrayList/* <IElementType> */(1);
        types.add(OrganizationModelElementTypes.DynamicOrgUnit_1002);
        NodeToolEntry entry =
                new NodeToolEntry(
                        Messages.DynamicOrganizationUnit5CreationTool_title,
                        Messages.DynamicOrganizationUnit5CreationTool_desc,
                        types);
        entry.setSmallIcon(OrganizationModelElementTypes
                .getImageDescriptor(OrganizationModelElementTypes.DynamicOrgUnit_1002));
        entry.setLargeIcon(entry.getSmallIcon());
        return entry;
    }

    /**
     * @generated NOT
     */
    private ToolEntry createHierarchy1CreationTool() {
        List/* <IElementType> */types = new ArrayList/* <IElementType> */(1);
        types.add(OrganizationModelElementTypes.OrgUnitRelationship_3001);
        LinkToolEntry entry =
                new LinkToolEntry(Messages.Hierarchy1CreationTool_title,
                        Messages.Hierarchy1CreationTool_desc, types);
        entry.setSmallIcon(OrganizationModelSubDiagramEditorPlugin
                .getBundledImageDescriptor("icons/obj16/hierarchy.png")); //$NON-NLS-1$
        entry.setLargeIcon(entry.getSmallIcon());
        return entry;
    }

    /**
     * An OrgUnitRelationship initialized as isHierarchical=false
     * 
     * @return
     */
    private ToolEntry createAssociation1CreationTool() {
        List/* <IElementType> */types = new ArrayList/* <IElementType> */(1);
        types.add(OrganizationModelElementTypes.OrgUnitRelationship_3001);

        OrgUnitRelLinkToolEntry entry =
                new OrgUnitRelLinkToolEntry(
                        Messages.AssociationCreationTool_title,
                        Messages.AssociationCreationTool_desc, types);

        entry.setSmallIcon(OrganizationModelSubDiagramEditorPlugin
                .getBundledImageDescriptor("icons/obj16/association.png")); //$NON-NLS-1$

        entry.setLargeIcon(entry.getSmallIcon());

        entry.setToolProperty("isHierarchical", new Boolean(false)); //$NON-NLS-1$

        return entry;
    }

    /**
     * @generated
     */
    private static class NodeToolEntry extends ToolEntry {

        /**
         * @generated
         */
        private final List elementTypes;

        /**
         * @generated
         */
        private NodeToolEntry(String title, String description,
                List elementTypes) {
            super(title, description, null, null);
            this.elementTypes = elementTypes;
        }

        /**
         * @generated
         */
        @Override
        public Tool createTool() {
            Tool tool = new UnspecifiedTypeCreationTool(elementTypes);
            tool.setProperties(getToolProperties());
            return tool;
        }
    }

    /**
     * @generated
     */
    private static class LinkToolEntry extends ToolEntry {

        /**
         * @generated
         */
        private final List relationshipTypes;

        /**
         * @generated
         */
        private LinkToolEntry(String title, String description,
                List relationshipTypes) {
            super(title, description, null, null);
            this.relationshipTypes = relationshipTypes;
        }

        /**
         * @generated
         */
        @Override
        public Tool createTool() {
            Tool tool = new UnspecifiedTypeConnectionTool(relationshipTypes);
            tool.setProperties(getToolProperties());
            return tool;
        }
    }

    /**
     * @generated NOT
     * 
     * @author rgreen
     * 
     */
    private static class OrgUnitRelLinkToolEntry extends ToolEntry {

        private final List relationshipTypes;

        private OrgUnitRelLinkToolEntry(String title, String description,
                List relationshipTypes) {
            super(title, description, null, null);
            this.relationshipTypes = relationshipTypes;
        }

        @Override
        public Tool createTool() {
            // We create a custom tool to hold useful information
            // e.g. the aggregation kind of an association
            Tool tool =
                    new OrgUnitRelUnspecifiedTypeConnectionTool(
                            relationshipTypes);
            tool.setProperties(getToolProperties());
            return tool;
        }
    }
}
