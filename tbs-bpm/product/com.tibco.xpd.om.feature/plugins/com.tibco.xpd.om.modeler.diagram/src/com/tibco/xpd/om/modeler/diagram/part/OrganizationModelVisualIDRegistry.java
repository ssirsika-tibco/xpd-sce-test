package com.tibco.xpd.om.modeler.diagram.part;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.modeler.diagram.edit.parts.DynamicOrgReferenceEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.DynamicOrgUnitDisplayNameEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.DynamicOrgUnitEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrgModelEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrgUnitDisplayNameEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrgUnitEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrgUnitFeatureNameEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrgUnitPositionCompartmentEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrgUnitRelationshipDisplayNameEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrgUnitRelationshipEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrganizationDisplayNameEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrganizationEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrganizationOrgUnitCompartmentEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrganizationTypeNameEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.PositionEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.GroupItemEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.LabelOrgModelGroupsEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.OrgModelGroupsCompartmentEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.OrgModelGroupsEditPart;

/**
 * This registry is used to determine which type of visual object should be
 * created for the corresponding Diagram, Node, ChildNode or Link represented by
 * a domain model object.
 * 
 * @generated
 */
public class OrganizationModelVisualIDRegistry {

    /**
     * @generated
     */
    private static final String DEBUG_KEY =
            "com.tibco.xpd.om.modeler.diagram/debug/visualID"; //$NON-NLS-1$

    /**
     * @generated
     */
    public static int getVisualID(View view) {
        if (view instanceof Diagram) {
            if (OrgModelEditPart.MODEL_ID.equals(view.getType())) {
                return OrgModelEditPart.VISUAL_ID;
            } else {
                return -1;
            }
        }
        return com.tibco.xpd.om.modeler.diagram.part.OrganizationModelVisualIDRegistry
                .getVisualID(view.getType());
    }

    /**
     * @generated
     */
    public static String getModelID(View view) {
        View diagram = view.getDiagram();
        while (view != diagram) {
            EAnnotation annotation = view.getEAnnotation("Shortcut"); //$NON-NLS-1$
            if (annotation != null) {
                return annotation.getDetails().get("modelID"); //$NON-NLS-1$
            }
            view = (View) view.eContainer();
        }
        return diagram != null ? diagram.getType() : null;
    }

    /**
     * @generated
     */
    public static int getVisualID(String type) {
        try {
            return Integer.parseInt(type);
        } catch (NumberFormatException e) {
            if (Boolean.TRUE.toString()
                    .equalsIgnoreCase(Platform.getDebugOption(DEBUG_KEY))) {
                OrganizationModelDiagramEditorPlugin
                        .getInstance()
                        .logError("Unable to parse view type as a visualID number: "
                                + type);
            }
        }
        return -1;
    }

    /**
     * @generated
     */
    public static String getType(int visualID) {
        return String.valueOf(visualID);
    }

    /**
     * @generated
     */
    public static int getDiagramVisualID(EObject domainElement) {
        if (domainElement == null) {
            return -1;
        }
        if (OMPackage.eINSTANCE.getOrgModel()
                .isSuperTypeOf(domainElement.eClass())
                && isDiagram((OrgModel) domainElement)) {
            return OrgModelEditPart.VISUAL_ID;
        }
        return -1;
    }

    public static int getNodeVisualID(View containerView, EObject domainElement) {
        if (domainElement == null) {
            return -1;
        }
        String containerModelID =
                com.tibco.xpd.om.modeler.diagram.part.OrganizationModelVisualIDRegistry
                        .getModelID(containerView);
        if (!OrgModelEditPart.MODEL_ID.equals(containerModelID)) {
            return -1;
        }
        int containerVisualID;
        if (OrgModelEditPart.MODEL_ID.equals(containerModelID)) {
            containerVisualID =
                    com.tibco.xpd.om.modeler.diagram.part.OrganizationModelVisualIDRegistry
                            .getVisualID(containerView);
        } else {
            if (containerView instanceof Diagram) {
                containerVisualID = OrgModelEditPart.VISUAL_ID;
            } else {
                return -1;
            }
        }
        switch (containerVisualID) {
        case OrgModelGroupsCompartmentEditPart.VISUAL_ID:
            return GroupItemEditPart.VISUAL_ID;
        }
        return getNodeVisualIDGen(containerView, domainElement);
    }

    /**
     * @generated
     */
    public static int getNodeVisualIDGen(View containerView,
            EObject domainElement) {
        if (domainElement == null) {
            return -1;
        }
        String containerModelID =
                com.tibco.xpd.om.modeler.diagram.part.OrganizationModelVisualIDRegistry
                        .getModelID(containerView);
        if (!OrgModelEditPart.MODEL_ID.equals(containerModelID)) {
            return -1;
        }
        int containerVisualID;
        if (OrgModelEditPart.MODEL_ID.equals(containerModelID)) {
            containerVisualID =
                    com.tibco.xpd.om.modeler.diagram.part.OrganizationModelVisualIDRegistry
                            .getVisualID(containerView);
        } else {
            if (containerView instanceof Diagram) {
                containerVisualID = OrgModelEditPart.VISUAL_ID;
            } else {
                return -1;
            }
        }
        switch (containerVisualID) {
        case OrganizationOrgUnitCompartmentEditPart.VISUAL_ID:
            if (OMPackage.eINSTANCE.getDynamicOrgUnit()
                    .isSuperTypeOf(domainElement.eClass())) {
                return DynamicOrgUnitEditPart.VISUAL_ID;
            }

            if (OMPackage.eINSTANCE.getOrgUnit()
                    .isSuperTypeOf(domainElement.eClass())) {
                return OrgUnitEditPart.VISUAL_ID;
            }

            break;
        case OrgUnitPositionCompartmentEditPart.VISUAL_ID:
            if (OMPackage.eINSTANCE.getPosition()
                    .isSuperTypeOf(domainElement.eClass())) {
                return PositionEditPart.VISUAL_ID;
            }
            break;
        case OrgModelEditPart.VISUAL_ID:
            if (OMPackage.eINSTANCE.getOrganization()
                    .isSuperTypeOf(domainElement.eClass())) {
                return OrganizationEditPart.VISUAL_ID;
            }
            break;
        }
        return -1;
    }

    public static boolean canCreateNode(View containerView, int nodeVisualID) {
        String containerModelID =
                com.tibco.xpd.om.modeler.diagram.part.OrganizationModelVisualIDRegistry
                        .getModelID(containerView);
        if (!OrgModelEditPart.MODEL_ID.equals(containerModelID)) {
            return false;
        }
        int containerVisualID;
        if (OrgModelEditPart.MODEL_ID.equals(containerModelID)) {
            containerVisualID =
                    com.tibco.xpd.om.modeler.diagram.part.OrganizationModelVisualIDRegistry
                            .getVisualID(containerView);
        } else {
            if (containerView instanceof Diagram) {
                containerVisualID = OrgModelEditPart.VISUAL_ID;
            } else {
                return false;
            }
        }
        switch (containerVisualID) {
        case OrgModelEditPart.VISUAL_ID:
            if (OrgModelGroupsEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            break;
        case OrgModelGroupsEditPart.VISUAL_ID:
            if (LabelOrgModelGroupsEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (OrgModelGroupsCompartmentEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            break;
        case OrgModelGroupsCompartmentEditPart.VISUAL_ID:
            if (GroupItemEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
        }
        return canCreateNodeGen(containerView, nodeVisualID);
    }

    /**
     * @generated
     */
    public static boolean canCreateNodeGen(View containerView, int nodeVisualID) {
        String containerModelID =
                com.tibco.xpd.om.modeler.diagram.part.OrganizationModelVisualIDRegistry
                        .getModelID(containerView);
        if (!OrgModelEditPart.MODEL_ID.equals(containerModelID)) {
            return false;
        }
        int containerVisualID;
        if (OrgModelEditPart.MODEL_ID.equals(containerModelID)) {
            containerVisualID =
                    com.tibco.xpd.om.modeler.diagram.part.OrganizationModelVisualIDRegistry
                            .getVisualID(containerView);
        } else {
            if (containerView instanceof Diagram) {
                containerVisualID = OrgModelEditPart.VISUAL_ID;
            } else {
                return false;
            }
        }
        switch (containerVisualID) {
        case OrganizationEditPart.VISUAL_ID:
            if (OrganizationDisplayNameEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (OrganizationTypeNameEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (OrganizationOrgUnitCompartmentEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            break;
        case OrgUnitEditPart.VISUAL_ID:
            if (OrgUnitDisplayNameEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (OrgUnitFeatureNameEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (OrgUnitPositionCompartmentEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            break;
        case DynamicOrgUnitEditPart.VISUAL_ID:
            if (DynamicOrgUnitDisplayNameEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            break;
        case OrganizationOrgUnitCompartmentEditPart.VISUAL_ID:
            if (OrgUnitEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (DynamicOrgUnitEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            break;
        case OrgUnitPositionCompartmentEditPart.VISUAL_ID:
            if (PositionEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            break;
        case OrgModelEditPart.VISUAL_ID:
            if (OrganizationEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            break;
        case OrgUnitRelationshipEditPart.VISUAL_ID:
            if (OrgUnitRelationshipDisplayNameEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            break;
        }
        return false;
    }

    /**
     * @generated
     */
    public static int getLinkWithClassVisualID(EObject domainElement) {
        if (domainElement == null) {
            return -1;
        }
        if (OMPackage.eINSTANCE.getOrgUnitRelationship()
                .isSuperTypeOf(domainElement.eClass())) {
            return OrgUnitRelationshipEditPart.VISUAL_ID;
        }
        if (OMPackage.eINSTANCE.getDynamicOrgReference()
                .isSuperTypeOf(domainElement.eClass())) {
            return DynamicOrgReferenceEditPart.VISUAL_ID;
        }
        return -1;
    }

    /**
     * User can change implementation of this method to handle some specific
     * situations not covered by default logic.
     * 
     * @generated
     */
    private static boolean isDiagram(OrgModel element) {
        return true;
    }

}
