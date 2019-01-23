/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.part.custom.firstclassprofiles;

import java.util.List;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.PaletteSeparator;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Extension;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.VisibilityKind;

import com.tibco.xpd.bom.modeler.diagram.part.BOMDiagramEditorPlugin;
import com.tibco.xpd.bom.modeler.diagram.part.DynamicPaletteFactory.BOMAssociationLinkToolEntry;
import com.tibco.xpd.bom.modeler.diagram.part.DynamicPaletteFactory.BOMNodeToolEntry;
import com.tibco.xpd.bom.modeler.diagram.part.Messages;
import com.tibco.xpd.bom.modeler.diagram.part.UMLPaletteFactory;
import com.tibco.xpd.bom.modeler.diagram.providers.UMLElementTypes;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.firstclassprofiles.ExtendedMetaData;
import com.tibco.xpd.bom.resources.firstclassprofiles.IFirstClassProfileExtension;
import com.tibco.xpd.bom.resources.utils.UML2ModelUtil;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * @author rgreen
 * 
 */
public class FirstClassProfilePaletteFactory {

    private boolean isSubDiag = false;

    public void fillPalette(PaletteRoot paletteRoot,
            IFirstClassProfileExtension extension, boolean isSubDiag) {
        setSubDiag(isSubDiag);
        fillPalette(paletteRoot, extension);
    }

    public void fillPalette(PaletteRoot paletteRoot,
            IFirstClassProfileExtension extension) {

        if (paletteRoot != null && extension != null) {
            PaletteContainer elementContainer =
                    getContainer(paletteRoot,
                            UMLPaletteFactory.ELEMENTS_CONTAINER_ID);

            if (elementContainer != null) {
                if (!elementContainer.getChildren().isEmpty()) {
                    elementContainer.add(0, new PaletteSeparator());
                }
            } else {
                elementContainer =
                        new PaletteDrawer(Messages.Elements1Group_title);
                elementContainer.setDescription(Messages.Elements1Group_desc);
                elementContainer.setId(UMLPaletteFactory.ELEMENTS_CONTAINER_ID);
                paletteRoot.add(elementContainer);
            }

            PaletteContainer childrenContainer =
                    getContainer(paletteRoot,
                            UMLPaletteFactory.CHILDREN_CONTAINER_ID);
            if (childrenContainer != null) {
                if (!childrenContainer.getChildren().isEmpty()) {
                    childrenContainer.add(0, new PaletteSeparator());
                }
            } else {
                childrenContainer =
                        new PaletteDrawer(Messages.Children2Group_title);
                childrenContainer.setDescription(Messages.Children2Group_desc);
                childrenContainer
                        .setId(UMLPaletteFactory.CHILDREN_CONTAINER_ID);
                paletteRoot.add(childrenContainer);
            }

            PaletteContainer relationshipContainer =
                    getContainer(paletteRoot,
                            UMLPaletteFactory.RELATIONSHIP_CONTAINER_ID);

            if (relationshipContainer != null) {
                if (!relationshipContainer.getChildren().isEmpty()) {
                    relationshipContainer.add(0, new PaletteSeparator());
                }
            } else {
                relationshipContainer =
                        new PaletteDrawer(Messages.Relationships3Group_title);
                relationshipContainer
                        .setDescription(Messages.Relationships3Group_desc);
                relationshipContainer
                        .setId(UMLPaletteFactory.RELATIONSHIP_CONTAINER_ID);
                paletteRoot.add(relationshipContainer);
            }

            // Now read the extensions from the profile
            Profile profile = extension.getProfile();

            EList<Extension> ownedext =
                    profile != null ? profile.getOwnedExtensions(false)
                            : new BasicEList<Extension>();
            EList<ToolEntry> elementEntries = new BasicEList<ToolEntry>();
            EList<ToolEntry> childrenEntries = new BasicEList<ToolEntry>();
            EList<ToolEntry> relationshipEntries = new BasicEList<ToolEntry>();

            for (Extension oe : ownedext) {
                Class cls = oe.getMetaclass();
                Stereotype stereotype = oe.getStereotype();

                // Private stereotypes should be hidden from the palette
                if (!stereotype.isSetVisibility()
                        || stereotype.getVisibility() != VisibilityKind.PRIVATE_LITERAL) {
                    if (cls.getName() != null && cls.getName().equals("Class")) { //$NON-NLS-1$
                        elementEntries.add(createClassCreationTool(stereotype));
                    }

                    if (cls.getName() != null
                            && cls.getName().equals("Package")) { //$NON-NLS-1$

                        if (!isSubDiag()) {
                            elementEntries
                                    .add(createPackageCreationTool(stereotype));
                        }

                    }

                    if (cls.getName() != null
                            && cls.getName().equals("PrimitiveType")) { //$NON-NLS-1$
                        elementEntries
                                .add(createPrimitiveTypeCreationTool(stereotype));
                    }

                    if (cls.getName() != null
                            && cls.getName().equals("Enumeration")) { //$NON-NLS-1$
                        elementEntries
                                .add(createEnumerationCreationTool(stereotype));
                    }

                    if (cls.getName() != null
                            && cls.getName().equals("Property")) { //$NON-NLS-1$
                        childrenEntries
                                .add(createAttributeCreationTool(stereotype));
                    }

                    if (cls.getName() != null
                            && cls.getName().equals("EnumerationLiteral")) { //$NON-NLS-1$
                        childrenEntries
                                .add(createEnumLitCreationTool(stereotype));
                    }

                    if (cls.getName() != null
                            && cls.getName().equals("Generalization")) { //$NON-NLS-1$
                        relationshipEntries
                                .add(createGeneralizationCreationTool(stereotype));
                    }

                    if (cls.getName() != null
                            && cls.getName().equals("Association")) { //$NON-NLS-1$
                        EList<Property> ownedEnds = oe.getOwnedEnds();
                        Property property = ownedEnds.get(0);
                        AggregationKind aggregation = property.getAggregation();
                        relationshipEntries
                                .add(createAssociationCreationTool(stereotype,
                                        aggregation));
                    }
                }
            }

            // Add the elements to the top of the palette drawer
            if (!elementEntries.isEmpty()) {
                int idx = 0;
                for (ToolEntry entry : elementEntries) {
                    elementContainer.add(idx++, entry);
                }
            }
            // Add the children to the top of the palette drawer
            if (!childrenEntries.isEmpty()) {
                int idx = 0;
                for (ToolEntry entry : childrenEntries) {
                    childrenContainer.add(idx++, entry);
                }
            }
            // Add the relationship to the top of the palette drawer
            if (!relationshipEntries.isEmpty()) {
                int idx = 0;
                for (ToolEntry entry : relationshipEntries) {
                    relationshipContainer.add(idx++, entry);
                }
            }
        }
    }

    private PaletteContainer getContainer(PaletteRoot paletteRoot, String id) {
        if (paletteRoot != null && id != null) {
            List<?> children = paletteRoot.getChildren();
            if (children != null) {
                for (Object child : children) {
                    if (child instanceof PaletteContainer
                            && id.equals(((PaletteContainer) child).getId())) {
                        return (PaletteContainer) child;
                    }
                }
            }
        }
        return null;
    }

    private ToolEntry createPackageCreationTool(Stereotype stereo) {
        BOMNodeToolEntry entry =
                new BOMNodeToolEntry(
                        stereo.getLabel(),
                        Messages.FirstClassProfilePaletteFactory_CreatePackage_tooltip,
                        UMLElementTypes.Package_1001);

        ImageDescriptor stereoImageDesc =
                UML2ModelUtil.getStereotypeImageDescriptor(stereo);

        if (stereoImageDesc != null) {
            entry.setSmallIcon(UML2ModelUtil
                    .getStereotypeImageDescriptor(stereo));
        } else {
            entry.setSmallIcon(UMLElementTypes
                    .getImageDescriptor(UMLElementTypes.Package_1001));
        }

        entry.setLargeIcon(entry.getSmallIcon());
        entry.setToolProperty(BOMResourcesPlugin.CREATE_ELEMENT_REQUEST_PARAM.STEREOTYPE,
                stereo);

        return entry;
    }

    private ToolEntry createClassCreationTool(Stereotype stereo) {
        BOMNodeToolEntry entry =
                new BOMNodeToolEntry(
                        stereo.getLabel(),
                        Messages.FirstClassProfilePaletteFactory_CreateClass_tooltip,
                        UMLElementTypes.Class_1002);
        ImageDescriptor stereoImageDesc =
                UML2ModelUtil.getStereotypeImageDescriptor(stereo);

        if (stereoImageDesc != null) {
            entry.setSmallIcon(UML2ModelUtil
                    .getStereotypeImageDescriptor(stereo));
        } else {
            entry.setSmallIcon(UMLElementTypes
                    .getImageDescriptor(UMLElementTypes.Class_1002));
        }

        entry.setLargeIcon(entry.getSmallIcon());
        entry.setToolProperty(BOMResourcesPlugin.CREATE_ELEMENT_REQUEST_PARAM.STEREOTYPE,
                stereo);

        return entry;
    }

    private ToolEntry createPrimitiveTypeCreationTool(Stereotype stereo) {
        BOMNodeToolEntry entry =
                new BOMNodeToolEntry(
                        stereo.getLabel(),
                        Messages.FirstClassProfilePaletteFactory_CreatePrimitiveType_tooltip,
                        UMLElementTypes.PrimitiveType_1003);
        ImageDescriptor stereoImageDesc =
                UML2ModelUtil.getStereotypeImageDescriptor(stereo);

        if (stereoImageDesc != null) {
            entry.setSmallIcon(UML2ModelUtil
                    .getStereotypeImageDescriptor(stereo));
        } else {
            entry.setSmallIcon(UMLElementTypes
                    .getImageDescriptor(UMLElementTypes.PrimitiveType_1003));
        }

        entry.setLargeIcon(entry.getSmallIcon());
        entry.setToolProperty(BOMResourcesPlugin.CREATE_ELEMENT_REQUEST_PARAM.STEREOTYPE,
                stereo);

        return entry;
    }

    private ToolEntry createEnumerationCreationTool(Stereotype stereo) {
        BOMNodeToolEntry entry =
                new BOMNodeToolEntry(
                        stereo.getLabel(),
                        Messages.FirstClassProfilePaletteFactory_CreateEnumeration_tooltip,
                        UMLElementTypes.Enumeration_1004);
        ImageDescriptor stereoImageDesc =
                UML2ModelUtil.getStereotypeImageDescriptor(stereo);

        if (stereoImageDesc != null) {
            entry.setSmallIcon(UML2ModelUtil
                    .getStereotypeImageDescriptor(stereo));
        } else {
            entry.setSmallIcon(UMLElementTypes
                    .getImageDescriptor(UMLElementTypes.Enumeration_1004));
        }

        entry.setLargeIcon(entry.getSmallIcon());
        entry.setToolProperty(BOMResourcesPlugin.CREATE_ELEMENT_REQUEST_PARAM.STEREOTYPE,
                stereo);

        return entry;
    }

    private ToolEntry createEnumLitCreationTool(Stereotype stereo) {
        BOMNodeToolEntry entry =
                new BOMNodeToolEntry(
                        stereo.getLabel(),
                        Messages.FirstClassProfilePaletteFactory_CreateEnumerationLiteral_tooltip,
                        UMLElementTypes.EnumerationLiteral_2003);
        ImageDescriptor stereoImageDesc =
                UML2ModelUtil.getStereotypeImageDescriptor(stereo);

        if (stereoImageDesc != null) {
            entry.setSmallIcon(UML2ModelUtil
                    .getStereotypeImageDescriptor(stereo));
        } else {
            entry.setSmallIcon(UMLElementTypes
                    .getImageDescriptor(UMLElementTypes.EnumerationLiteral_2003));
        }

        entry.setLargeIcon(entry.getSmallIcon());
        entry.setToolProperty(BOMResourcesPlugin.CREATE_ELEMENT_REQUEST_PARAM.STEREOTYPE,
                stereo);

        return entry;
    }

    private ToolEntry createAttributeCreationTool(Stereotype stereo) {
        ExtendedMetaData extData = ExtendedMetaData.INSTANCE;

        BOMNodeToolEntry entry =
                new BOMNodeToolEntry(
                        stereo.getLabel(),
                        Messages.FirstClassProfilePaletteFactory_CreateAttribute_tooltip,
                        UMLElementTypes.Property_2001);
        ImageDescriptor stereoImageDesc =
                UML2ModelUtil.getStereotypeImageDescriptor(stereo);

        if (stereoImageDesc != null) {
            entry.setSmallIcon(UML2ModelUtil
                    .getStereotypeImageDescriptor(stereo));
        } else {
            entry.setSmallIcon(UMLElementTypes
                    .getImageDescriptor(UMLElementTypes.Property_2001));
        }

        entry.setLargeIcon(entry.getSmallIcon());
        entry.setToolProperty(BOMResourcesPlugin.CREATE_ELEMENT_REQUEST_PARAM.STEREOTYPE,
                stereo);
        String type = extData.getType(stereo);
        if (type != null) {
            PrimitiveType pType =
                    PrimitivesUtil
                            .getStandardPrimitiveTypeByName(XpdResourcesPlugin
                                    .getDefault().getEditingDomain()
                                    .getResourceSet(), type);
            if (pType != null) {
                entry.setToolProperty(BOMResourcesPlugin.CREATE_ELEMENT_REQUEST_PARAM.TYPE,
                        pType);
            }
        }

        return entry;
    }

    private ToolEntry createOperationCreationTool(Stereotype stereo) {
        BOMNodeToolEntry entry =
                new BOMNodeToolEntry(
                        stereo.getLabel(),
                        Messages.FirstClassProfilePaletteFactory_CreateOperation_tooltip,
                        UMLElementTypes.Operation_2002);
        ImageDescriptor stereoImageDesc =
                UML2ModelUtil.getStereotypeImageDescriptor(stereo);

        if (stereoImageDesc != null) {
            entry.setSmallIcon(UML2ModelUtil
                    .getStereotypeImageDescriptor(stereo));
        } else {
            entry.setSmallIcon(UMLElementTypes
                    .getImageDescriptor(UMLElementTypes.Operation_2002));
        }

        entry.setLargeIcon(entry.getSmallIcon());
        entry.setToolProperty(BOMResourcesPlugin.CREATE_ELEMENT_REQUEST_PARAM.STEREOTYPE,
                stereo);

        return entry;
    }

    private ToolEntry createAssociationCreationTool(Stereotype stereo,
            AggregationKind agg) {
        BOMAssociationLinkToolEntry entry =
                new BOMAssociationLinkToolEntry(
                        stereo.getLabel(),
                        Messages.FirstClassProfilePaletteFactory_CreateAssociation_tooltip,
                        UMLElementTypes.Association_3002);
        ImageDescriptor stereoImageDesc =
                UML2ModelUtil.getStereotypeImageDescriptor(stereo);

        if (stereoImageDesc != null) {
            entry.setSmallIcon(UML2ModelUtil
                    .getStereotypeImageDescriptor(stereo));
        } else if (agg == AggregationKind.COMPOSITE_LITERAL) {
            entry.setSmallIcon(BOMDiagramEditorPlugin
                    .findImageDescriptor("/com.tibco.xpd.bom.resources.ui/icons/items/16x16/AssociationCompositionUni.gif")); //$NON-NLS-1$
        } else if (agg == AggregationKind.SHARED_LITERAL) {
            entry.setSmallIcon(BOMDiagramEditorPlugin
                    .findImageDescriptor("/com.tibco.xpd.bom.resources.ui/icons/items/16x16/AssociationAggregationUni.gif")); //$NON-NLS-1$
        } else if (agg == AggregationKind.NONE_LITERAL) {
            entry.setSmallIcon(UMLElementTypes
                    .getImageDescriptor(UMLElementTypes.Association_3002));
        }

        entry.setLargeIcon(entry.getSmallIcon());
        entry.setToolProperty("aggregationKind", agg.toString()); //$NON-NLS-1$
        entry.setToolProperty(BOMResourcesPlugin.CREATE_ELEMENT_REQUEST_PARAM.STEREOTYPE,
                stereo);

        return entry;
    }

    private ToolEntry createGeneralizationCreationTool(Stereotype stereo) {
        BOMAssociationLinkToolEntry entry =
                new BOMAssociationLinkToolEntry(
                        stereo.getLabel(),
                        Messages.FirstClassProfilePaletteFactory_CreateGeneralization_tooltip,
                        UMLElementTypes.Generalization_3001);
        ImageDescriptor stereoImageDesc =
                UML2ModelUtil.getStereotypeImageDescriptor(stereo);

        if (stereoImageDesc != null) {
            entry.setSmallIcon(UML2ModelUtil
                    .getStereotypeImageDescriptor(stereo));
        } else {
            entry.setSmallIcon(UMLElementTypes
                    .getImageDescriptor(UMLElementTypes.Generalization_3001));
        }

        entry.setLargeIcon(entry.getSmallIcon());
        entry.setToolProperty(BOMResourcesPlugin.CREATE_ELEMENT_REQUEST_PARAM.STEREOTYPE,
                stereo);

        return entry;
    }

    public boolean isSubDiag() {
        return isSubDiag;
    }

    private void setSubDiag(boolean isSubDiag) {
        this.isSubDiag = isSubDiag;
    }
}
