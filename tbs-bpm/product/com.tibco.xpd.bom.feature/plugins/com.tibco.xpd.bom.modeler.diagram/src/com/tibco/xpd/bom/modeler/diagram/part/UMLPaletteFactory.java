/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.part;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.Tool;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gmf.runtime.diagram.ui.internal.services.palette.PaletteToolEntry;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeConnectionTool;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeCreationTool;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;

import com.tibco.xpd.bom.modeler.diagram.providers.UMLElementTypes;

/**
 * @generated
 */
public class UMLPaletteFactory {

    public static final String CHILDREN_CONTAINER_ID = "children"; //$NON-NLS-1$

    public static final String ELEMENTS_CONTAINER_ID = "elements"; //$NON-NLS-1$

    public static final String RELATIONSHIP_CONTAINER_ID = "relationship"; //$NON-NLS-1$

    private boolean isSubDiag = false;

    public void fillPalette(PaletteRoot paletteRoot, boolean isSubDiag) {
        setSubDiagram(isSubDiag);
        fillPalette(paletteRoot);
    }

    /**
     * @generated NOT
     */
    public void fillPalette(PaletteRoot paletteRoot) {

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

        // Need to manually remove AssociationClass tool from palette entries
        PaletteContainer elements = createElements1Group();
        elements.setId(ELEMENTS_CONTAINER_ID); //$NON-NLS-1$
        removeEntryFromPaletteContainer(elements,
                Messages.AssociationClass5CreationTool_title);
        paletteRoot.add(elements);

        // Don't need to remove anything from here
        PaletteContainer childrenGroup = createChildren2Group();
        childrenGroup.setId(CHILDREN_CONTAINER_ID); //$NON-NLS-1$
        paletteRoot.add(childrenGroup);

        // Need to manually remove AssociationEnd tool from palette entries
        PaletteContainer rels = createRelationships3Group();
        rels.setId(RELATIONSHIP_CONTAINER_ID);
        removeEntryFromPaletteContainer(rels,
                Messages.AssociationEnd5CreationTool_title);
        paletteRoot.add(rels);

    }

    /**
     * 
     * The tool entry specified by the entryTitle string is removed from the
     * palette container.
     * 
     * @param paletteContainer
     * @param entryTitle
     * @return PaletteContainer
     */
    private void removeEntryFromPaletteContainer(
            PaletteContainer paletteContainer, String entryTitle) {

        if (paletteContainer != null) {
            List relsChildren = paletteContainer.getChildren();
            PaletteEntry relEntry = null;
            for (Object object : relsChildren) {
                if (object instanceof PaletteEntry) {
                    PaletteEntry pe = (PaletteEntry) object;
                    if (entryTitle.equals(pe.getLabel())) {
                        relEntry = pe;
                    }
                }
            }

            if (relEntry != null) {
                paletteContainer.remove(relEntry);
            }
        }

    }

    /**
     * Creates "Elements" palette tool group
     * 
     * @generated
     */
    private PaletteContainer createElements1Group() {
        PaletteDrawer paletteContainer =
                new PaletteDrawer(Messages.Elements1Group_title);
        paletteContainer.setDescription(Messages.Elements1Group_desc);
        paletteContainer.add(createClass2CreationTool());
        paletteContainer.add(createPrimitiveType3CreationTool());
        paletteContainer.add(createEnumeration4CreationTool());
        if (!isSubDiagram()) {
            paletteContainer.add(createPackage1CreationTool());
        }
        paletteContainer.add(createAssociationClass5CreationTool());
        return paletteContainer;
    }

    /**
     * Creates "Children" palette tool group
     * 
     * @generated
     */
    private PaletteContainer createChildren2Group() {
        PaletteDrawer paletteContainer =
                new PaletteDrawer(Messages.Children2Group_title);
        paletteContainer.setDescription(Messages.Children2Group_desc);
        paletteContainer.add(createAttribute1CreationTool());
        /*
         * ACE-481: Saket: Operations not supported in ACE.
         */
        // paletteContainer.add(createOperation2CreationTool());
        paletteContainer.add(createEnumLiteral3CreationTool());
        return paletteContainer;
    }

    /**
     * Creates "Relationships" palette tool group
     * 
     * @generated
     */
    private PaletteContainer createRelationships3Group() {
        PaletteDrawer paletteContainer =
                new PaletteDrawer(Messages.Relationships3Group_title);
        paletteContainer.setDescription(Messages.Relationships3Group_desc);
        /*
         * ACE-481: Saket: Generalization not supported in ACE.
         */
        // paletteContainer.add(createGeneralization1CreationTool());
        paletteContainer.add(createAssociation2CreationTool());
        /*
         * ACE-481: Saket: Aggregation not supported in ACE.
         */
        // paletteContainer.add(createAggregation3CreationTool());
        paletteContainer.add(createComposition4CreationTool());
        paletteContainer.add(createAssociationEnd5CreationTool());
        return paletteContainer;
    }

    /**
     * Creates "Nodes" palette tool group
     * 
     * @generated NOT
     */
    // private PaletteContainer createNodes1Group() {
    // PaletteDrawer paletteContainer = new PaletteDrawer(
    // Messages.Nodes1Group_title);
    //
    //        paletteContainer.setId("BOMnodes"); //$NON-NLS-1$
    // paletteContainer.setDescription(Messages.Nodes1Group_desc);
    // paletteContainer.add(createPackage1CreationTool());
    // paletteContainer.add(createClass2CreationTool());
    // paletteContainer.add(createPrimitiveType3CreationTool());
    // paletteContainer.add(createAttribute4CreationTool());
    // paletteContainer.add(createOperation5CreationTool());
    // paletteContainer.add(createEnumeration6CreationTool());
    // paletteContainer.add(createEnumLiteral7CreationTool());
    // return paletteContainer;
    // }
    /**
     * Creates "Links" palette tool group
     * 
     * @generated NOT
     */
    // private PaletteContainer createLinks2Group() {
    // PaletteDrawer paletteContainer = new PaletteDrawer(
    // Messages.Links2Group_title);
    //
    //        paletteContainer.setId("BOMlinks"); //$NON-NLS-1$
    //
    // paletteContainer.setDescription(Messages.Links2Group_desc);
    // paletteContainer.add(createGeneralization1CreationTool());
    // paletteContainer.add(createAssociation2CreationTool());
    // paletteContainer.add(createAggregation3CreationTool());
    // paletteContainer.add(createComposition4CreationTool());
    // return paletteContainer;
    // }
    /**
     * @generated
     */
    private ToolEntry createPackage1CreationTool() {
        List/* <IElementType> */types = new ArrayList/* <IElementType> */(1);
        types.add(UMLElementTypes.Package_1001);
        NodeToolEntry entry =
                new NodeToolEntry(Messages.Package1CreationTool_title,
                        Messages.Package1CreationTool_desc, types);
        entry.setSmallIcon(UMLElementTypes
                .getImageDescriptor(UMLElementTypes.Package_1001));
        entry.setLargeIcon(entry.getSmallIcon());
        return entry;
    }

    /**
     * @generated
     */
    private ToolEntry createClass2CreationTool() {
        List/* <IElementType> */types = new ArrayList/* <IElementType> */(1);
        types.add(UMLElementTypes.Class_1002);
        NodeToolEntry entry =
                new NodeToolEntry(Messages.Class2CreationTool_title,
                        Messages.Class2CreationTool_desc, types);
        entry.setSmallIcon(UMLElementTypes
                .getImageDescriptor(UMLElementTypes.Class_1002));
        entry.setLargeIcon(entry.getSmallIcon());
        return entry;
    }

    /**
     * @generated
     */
    private ToolEntry createPrimitiveType3CreationTool() {
        List/* <IElementType> */types = new ArrayList/* <IElementType> */(1);
        types.add(UMLElementTypes.PrimitiveType_1003);
        NodeToolEntry entry =
                new NodeToolEntry(Messages.PrimitiveType3CreationTool_title,
                        Messages.PrimitiveType3CreationTool_desc, types);
        entry.setSmallIcon(UMLElementTypes
                .getImageDescriptor(UMLElementTypes.PrimitiveType_1003));
        entry.setLargeIcon(entry.getSmallIcon());
        return entry;
    }

    /**
     * @generated
     */
    private ToolEntry createEnumeration4CreationTool() {
        List/* <IElementType> */types = new ArrayList/* <IElementType> */(1);
        types.add(UMLElementTypes.Enumeration_1004);
        NodeToolEntry entry =
                new NodeToolEntry(Messages.Enumeration4CreationTool_title,
                        Messages.Enumeration4CreationTool_desc, types);
        entry.setSmallIcon(UMLElementTypes
                .getImageDescriptor(UMLElementTypes.Enumeration_1004));
        entry.setLargeIcon(entry.getSmallIcon());
        return entry;
    }

    /**
     * @generated
     */
    private ToolEntry createAssociationClass5CreationTool() {
        List/* <IElementType> */types = new ArrayList/* <IElementType> */(1);
        types.add(UMLElementTypes.AssociationClass_1005);
        NodeToolEntry entry =
                new NodeToolEntry(Messages.AssociationClass5CreationTool_title,
                        Messages.AssociationClass5CreationTool_desc, types);
        entry.setSmallIcon(UMLElementTypes
                .getImageDescriptor(UMLElementTypes.AssociationClass_1005));
        entry.setLargeIcon(entry.getSmallIcon());
        return entry;
    }

    /**
     * @generated
     */
    private ToolEntry createAttribute1CreationTool() {
        List/* <IElementType> */types = new ArrayList/* <IElementType> */(2);
        types.add(UMLElementTypes.Property_2001);
        types.add(UMLElementTypes.Property_2004);
        NodeToolEntry entry =
                new NodeToolEntry(Messages.Attribute1CreationTool_title,
                        Messages.Attribute1CreationTool_desc, types);
        entry.setSmallIcon(UMLElementTypes
                .getImageDescriptor(UMLElementTypes.Property_2001));
        entry.setLargeIcon(entry.getSmallIcon());
        return entry;
    }

    /**
     * @generated
     */
    private ToolEntry createOperation2CreationTool() {
        List/* <IElementType> */types = new ArrayList/* <IElementType> */(2);
        types.add(UMLElementTypes.Operation_2002);
        types.add(UMLElementTypes.Operation_2005);
        NodeToolEntry entry =
                new NodeToolEntry(Messages.Operation2CreationTool_title,
                        Messages.Operation2CreationTool_desc, types);
        entry.setSmallIcon(UMLElementTypes
                .getImageDescriptor(UMLElementTypes.Operation_2002));
        entry.setLargeIcon(entry.getSmallIcon());
        return entry;
    }

    /**
     * @generated
     */
    private ToolEntry createEnumLiteral3CreationTool() {
        List/* <IElementType> */types = new ArrayList/* <IElementType> */(1);
        types.add(UMLElementTypes.EnumerationLiteral_2003);
        NodeToolEntry entry =
                new NodeToolEntry(Messages.EnumLiteral3CreationTool_title,
                        Messages.EnumLiteral3CreationTool_desc, types);
        entry.setSmallIcon(UMLElementTypes
                .getImageDescriptor(UMLElementTypes.EnumerationLiteral_2003));
        entry.setLargeIcon(entry.getSmallIcon());
        return entry;
    }

    /**
     * @generated
     */
    private ToolEntry createGeneralization1CreationTool() {
        List/* <IElementType> */types = new ArrayList/* <IElementType> */(1);
        types.add(UMLElementTypes.Generalization_3001);
        LinkToolEntry entry =
                new LinkToolEntry(Messages.Generalization1CreationTool_title,
                        Messages.Generalization1CreationTool_desc, types);
        entry.setSmallIcon(UMLElementTypes
                .getImageDescriptor(UMLElementTypes.Generalization_3001));
        entry.setLargeIcon(entry.getSmallIcon());
        return entry;
    }

    /**
     * @generated NOT
     */
    private ToolEntry createAssociation2CreationTool() {
        AssociationLinkToolEntry entry =
                new AssociationLinkToolEntry(
                        Messages.Association2CreationTool_title,
                        Messages.Association2CreationTool_desc,
                        UMLElementTypes.Association_3002);
        entry.setSmallIcon(UMLElementTypes
                .getImageDescriptor(UMLElementTypes.Association_3002));
        entry.setLargeIcon(entry.getSmallIcon());

        entry.setToolProperty("aggregationKind", "none"); //$NON-NLS-1$ //$NON-NLS-2$

        return entry;
    }

    /**
     * @generated NOT
     */
    private ToolEntry createAggregation3CreationTool() {
        AssociationLinkToolEntry entry =
                new AssociationLinkToolEntry(
                        Messages.Aggregation3CreationTool_title,
                        Messages.Aggregation3CreationTool_desc,
                        UMLElementTypes.Association_3002);
        entry
                .setSmallIcon(BOMDiagramEditorPlugin
                        .findImageDescriptor("/com.tibco.xpd.bom.resources.ui/icons/items/16x16/AssociationAggregationUni.gif")); //$NON-NLS-1$
        entry
                .setLargeIcon(BOMDiagramEditorPlugin
                        .findImageDescriptor("/com.tibco.xpd.bom.resources.ui/icons/items/16x16/AssociationAggregationUni.gif")); //$NON-NLS-1$

        entry.setToolProperty("aggregationKind", "aggregation"); //$NON-NLS-1$ //$NON-NLS-2$

        return entry;
    }

    /**
     * @generated NOT
     */
    private ToolEntry createComposition4CreationTool() {
        AssociationLinkToolEntry entry =
                new AssociationLinkToolEntry(
                        Messages.Composition4CreationTool_title,
                        Messages.Composition4CreationTool_desc,
                        UMLElementTypes.Association_3002);
        entry
                .setSmallIcon(BOMDiagramEditorPlugin
                        .findImageDescriptor("/com.tibco.xpd.bom.resources.ui/icons/items/16x16/AssociationCompositionUni.gif")); //$NON-NLS-1$
        entry
                .setLargeIcon(BOMDiagramEditorPlugin
                        .findImageDescriptor("/com.tibco.xpd.bom.resources.ui/icons/items/16x16/AssociationCompositionUni.gif")); //$NON-NLS-1$

        entry.setToolProperty("aggregationKind", "composition"); //$NON-NLS-1$ //$NON-NLS-2$

        return entry;
    }

    /**
     * @generated
     */
    private ToolEntry createAssociationEnd5CreationTool() {
        List/* <IElementType> */types = new ArrayList/* <IElementType> */(1);
        types.add(UMLElementTypes.Property_3003);
        LinkToolEntry entry =
                new LinkToolEntry(Messages.AssociationEnd5CreationTool_title,
                        Messages.AssociationEnd5CreationTool_desc, types);
        entry.setSmallIcon(UMLElementTypes
                .getImageDescriptor(UMLElementTypes.Property_3003));
        entry.setLargeIcon(entry.getSmallIcon());
        return entry;
    }

    public void setSubDiagram(boolean isSubDiagram) {
        this.isSubDiag = isSubDiagram;
    }

    public boolean isSubDiagram() {
        return isSubDiag;
    }

    /**
     * @generated
     */
    private static class NodeToolEntry extends PaletteToolEntry {

        /**
         * @generated
         */
        private final List elementTypes;

        /**
         * @generated
         */
        private NodeToolEntry(String title, String description,
                List elementTypes) {
            super(null, title, null);
            this.setDescription(description);
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
    private static class AssociationLinkToolEntry extends ToolEntry {

        private final IElementType elementType;

        private AssociationLinkToolEntry(String title, String description,
                IElementType type) {
            super(title, description, null, null);
            elementType = type;
        }

        @Override
        public Tool createTool() {
            // We create a custom tool to hold useful information
            // e.g. the aggregation kind of an association
            Tool tool = new AssociationConnectionTool(elementType);
            tool.setProperties(getToolProperties());
            return tool;
        }
    }
}
