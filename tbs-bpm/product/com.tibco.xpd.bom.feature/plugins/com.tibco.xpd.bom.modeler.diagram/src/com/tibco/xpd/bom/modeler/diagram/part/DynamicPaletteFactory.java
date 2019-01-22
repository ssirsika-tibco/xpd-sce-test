package com.tibco.xpd.bom.modeler.diagram.part;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.gef.Tool;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gmf.runtime.diagram.ui.internal.services.palette.PaletteToolEntry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.VisibilityKind;

import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager.StereotypeKind;
import com.tibco.xpd.bom.modeler.diagram.providers.UMLElementTypes;
import com.tibco.xpd.bom.resources.utils.BOMProfileUtils;
import com.tibco.xpd.bom.resources.utils.UML2ModelUtil;

@SuppressWarnings("restriction")
public class DynamicPaletteFactory extends UMLPaletteFactory {

    public void fillDynamicPalette(PaletteRoot paletteRoot,
            List<Profile> lstProfs, boolean isSubDiag) {
        setSubDiagram(isSubDiag);
        fillDynamicPalette(paletteRoot, lstProfs);
    }

    public void fillDynamicPalette(PaletteRoot paletteRoot,
            List<Profile> lstProfs) {

        // First actio will be to clean the palette so we can start afresh.
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

        // Fill with the standard BOM tools
        super.fillPalette(paletteRoot);

        // Now add our new palette features
        for (Profile profile : lstProfs) {

            if (profile != null
                    && !BOMProfileUtils.isProfileGlobalDataProfile(profile)) {
                if (profile.eResource() != null) {
                    URI uri = profile.eResource().getURI();
                    if (uri != null && !uri.isPlatformResource()) {
                        continue;
                    }
                }
            }

            paletteRoot.add(createNewNodesDrawer(profile));
        }

    }

    /**
     * Creates "Nodes" palette tool group
     * 
     * @generated NOT
     */
    private PaletteContainer createNewNodesDrawer(Profile prof) {

        String profileContainerName = null;

        if (BOMProfileUtils.isProfileGlobalDataProfile(prof)) {
            profileContainerName =
                    Messages.DynamicPaletteFactory_globalDataTitle_label;
        } else {
            profileContainerName = prof.getName();
        }

        PaletteDrawer paletteContainer =
                new PaletteDrawer(profileContainerName);

        paletteContainer.setId(prof.getLabel());

        paletteContainer
                .setDescription(String
                        .format(Messages.DynamicPaletteFactory_paletteContainer_shortdesc,
                                prof.getLabel()));

        // Loop through each stereotype
        List<Stereotype> lstStereos = prof.getOwnedStereotypes();

        for (Stereotype st : lstStereos) {
            // Check for cases that have visibility set to anything other
            // than public, because we do not want to show those ones
            if (st.getVisibility() != VisibilityKind.PUBLIC_LITERAL) {
                continue;
            }
            EList<Class> lstMeta = st.getExtendedMetaclasses();

            for (Class ext : lstMeta) {
                if (ext.getName().equals(UMLPackage.eINSTANCE.getClass_()
                        .getName())) {
                    paletteContainer.add(createClassCreationTool(st));
                } else if (ext.getName().equals(UMLPackage.eINSTANCE
                        .getPackage().getName())) {
                    paletteContainer.add(createPackageCreationTool(st));
                } else if (ext.getName().equals(UMLPackage.eINSTANCE
                        .getPrimitiveType().getName())) {
                    paletteContainer.add(createPrimitiveTypeCreationTool(st));
                } else if (ext.getName().equals(UMLPackage.eINSTANCE
                        .getProperty().getName())) {
                    paletteContainer.add(createAttributeCreationTool(st));
                } else if (ext.getName().equals(UMLPackage.eINSTANCE
                        .getOperation().getName())) {
                    paletteContainer.add(createOperationCreationTool(st));
                } else if (ext.getName().equals(UMLPackage.eINSTANCE
                        .getAssociation().getName())) {
                    paletteContainer.add(createAssociationCreationTool(st));
                } else if (ext.getName().equals(UMLPackage.eINSTANCE
                        .getGeneralization().getName())) {
                    paletteContainer.add(createGeneralizationCreationTool(st));
                } else if (ext.getName().equals(UMLPackage.eINSTANCE
                        .getEnumeration().getName())) {
                    paletteContainer.add(createEnumerationCreationTool(st));
                } else if (ext.getName().equals(UMLPackage.eINSTANCE
                        .getEnumerationLiteral().getName())) {
                    paletteContainer.add(createEnumLitCreationTool(st));
                }

            }
        }

        return paletteContainer;
    }

    private ToolEntry createPackageCreationTool(Stereotype stereo) {
        BOMNodeToolEntry entry =
                new BOMNodeToolEntry(stereo.getLabel(),
                        Messages.DynamicPaletteFactory_CreatePackage_tooltip,
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
        entry.setToolProperty("stereotype", stereo); //$NON-NLS-1$
        return entry;
    }

    public static ToolEntry createClassCreationTool(Stereotype stereo) {

        String toolTipLabel = null;

        if (stereo.getName().equals(StereotypeKind.CASE.toString())) {
            toolTipLabel =
                    Messages.DynamicPaletteFactory_CreateCaseClass_tooltip;
        } else if (stereo.getName().equals(StereotypeKind.GLOBAL.toString())) {
            toolTipLabel =
                    Messages.DynamicPaletteFactory_CreateGlobalClass_tooltip;
        }

        if (toolTipLabel == null) {
            toolTipLabel =
                    String.format(Messages.DynamicPaletteFactory_tool_label,
                            stereo.getLabel());
        }

        BOMNodeToolEntry entry =
                new BOMNodeToolEntry(stereo.getLabel(), toolTipLabel,
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
        entry.setToolProperty("stereotype", stereo); //$NON-NLS-1$
        return entry;
    }

    private ToolEntry createPrimitiveTypeCreationTool(Stereotype stereo) {
        BOMNodeToolEntry entry =
                new BOMNodeToolEntry(
                        stereo.getLabel(),
                        Messages.DynamicPaletteFactory_CreatePrimitiveType_tooltip,
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
        entry.setToolProperty("stereotype", stereo); //$NON-NLS-1$
        return entry;
    }

    private ToolEntry createEnumerationCreationTool(Stereotype stereo) {
        BOMNodeToolEntry entry =
                new BOMNodeToolEntry(
                        stereo.getLabel(),
                        Messages.DynamicPaletteFactory_CreateEnumeration_tooltip,
                        UMLElementTypes.Enumeration_1004);

        ImageDescriptor stereoImageDesc =
                UML2ModelUtil.getStereotypeImageDescriptor(stereo);

        if (stereoImageDesc != null) {
            entry.setSmallIcon(UML2ModelUtil
                    .getStereotypeImageDescriptor(stereo));
        } else {

            entry.setSmallIcon(UMLElementTypes
                    .getImageDescriptor(UMLElementTypes.Enumeration_1004));
            entry.setLargeIcon(entry.getSmallIcon());
        }
        entry.setToolProperty("stereotype", stereo); //$NON-NLS-1$
        return entry;
    }

    private ToolEntry createEnumLitCreationTool(Stereotype stereo) {
        BOMNodeToolEntry entry =
                new BOMNodeToolEntry(
                        stereo.getLabel(),
                        Messages.DynamicPaletteFactory_CreateEnumerationLiteral_tooltip,
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
        entry.setToolProperty("stereotype", stereo); //$NON-NLS-1$
        return entry;
    }

    /**
     * @generated
     */
    public static ToolEntry createAttributeCreationTool(Stereotype stereo) {
        String toolTipLabel = null;

        if (stereo.getName()
                .equals(StereotypeKind.AUTO_CASE_IDENTIFIER.toString())) {
            toolTipLabel =
                    Messages.DynamicPaletteFactory_CreateCaseIdentifier_tooltip;
        } else if(stereo.getName()
                .equals(StereotypeKind.CASE_STATE.toString())) {
            toolTipLabel =
                    Messages.DynamicPaletteFactory_CreateCaseState_tooltip;
        }

        // Need to catch the case where the Label would be Camel Case
        // Auto CID, we don't want to display the Auto part
        // In the ideal world we would just change the Stereotype
        // but this has already got lots of test artifacts
        String label = stereo.getLabel();
        if (StereotypeKind.AUTO_CASE_IDENTIFIER.toString()
                .compareToIgnoreCase(label) == 0) {
            label = Messages.DynamicPaletteFactory_CaseIdentifier_label;
        } else if(StereotypeKind.CASE_STATE.toString()
                .compareToIgnoreCase(label) == 0) {
            label = Messages.DynamicPaletteFactory_CaseState_label;
        } 

        if (toolTipLabel == null) {
            toolTipLabel =
                    String.format(Messages.DynamicPaletteFactory_tool_label,
                            label);
        }

        BOMNodeToolEntry entry =
                new BOMNodeToolEntry(label, toolTipLabel,
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
        entry.setToolProperty("stereotype", stereo); //$NON-NLS-1$
        return entry;
    }

    private ToolEntry createOperationCreationTool(Stereotype stereo) {
        BOMNodeToolEntry entry =
                new BOMNodeToolEntry(stereo.getLabel(),
                        Messages.DynamicPaletteFactory_CreateOperation_tooltip,
                        UMLElementTypes.Operation_2002);
        entry.setSmallIcon(UMLElementTypes
                .getImageDescriptor(UMLElementTypes.Operation_2002));
        entry.setLargeIcon(entry.getSmallIcon());
        entry.setToolProperty("stereotype", stereo); //$NON-NLS-1$
        return entry;
    }

    private ToolEntry createAssociationCreationTool(Stereotype stereo) {
        BOMAssociationLinkToolEntry entry =
                new BOMAssociationLinkToolEntry(
                        stereo.getName(),
                        Messages.DynamicPaletteFactory_CreateAssociation_tooltip,
                        UMLElementTypes.Association_3002);
        entry.setSmallIcon(UMLElementTypes
                .getImageDescriptor(UMLElementTypes.Association_3002));
        entry.setLargeIcon(entry.getSmallIcon());

        entry.setToolProperty("aggregationKind", "none"); //$NON-NLS-1$//$NON-NLS-2$
        entry.setToolProperty("stereotype", stereo); //$NON-NLS-1$

        return entry;
    }

    private ToolEntry createGeneralizationCreationTool(Stereotype stereo) {
        BOMAssociationLinkToolEntry entry =
                new BOMAssociationLinkToolEntry(
                        stereo.getName(),
                        Messages.DynamicPaletteFactory_CreateGeneralization_tooltip,
                        UMLElementTypes.Generalization_3001);
        entry.setSmallIcon(UMLElementTypes
                .getImageDescriptor(UMLElementTypes.Generalization_3001));
        entry.setLargeIcon(entry.getSmallIcon());

        entry.setToolProperty("stereotype", stereo); //$NON-NLS-1$

        return entry;
    }

    public static class BOMNodeToolEntry extends PaletteToolEntry {

        private final IElementType elementType;

        public BOMNodeToolEntry(String title, String description,
                IElementType elementType) {
            super(null, title, null);
            this.setDescription(description);
            this.elementType = elementType;
        }

        @Override
        public Tool createTool() {
            Tool tool = new BOMTypeCreationTool(elementType);
            tool.setProperties(getToolProperties());
            return tool;
        }
    }

    public static class BOMAssociationLinkToolEntry extends ToolEntry {

        private final IElementType elementType;

        public BOMAssociationLinkToolEntry(String title, String description,
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
