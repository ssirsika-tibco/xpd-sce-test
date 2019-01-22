package com.tibco.xpd.om.modeler.subdiagram.part;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.DynamicOrgReferenceLabelEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.DynamicOrgUnitDisplayNameEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.DynamicOrgUnitEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.OrgUnitPositionSubCompartmentEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.OrgUnitSubDisplayNameEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.OrgUnitSubEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.OrgUnitSubFeatureNameEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.OrgUnitSubRelationshipDisplayNameEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.OrgUnitSubRelationshipEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.OrganizationSubEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.PositionSubEditPart;

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
            "com.tibco.xpd.om.modeler.subdiagram/debug/visualID"; //$NON-NLS-1$

    /**
     * @generated
     */
    public static int getVisualID(View view) {
        if (view instanceof Diagram) {
            if (OrganizationSubEditPart.MODEL_ID.equals(view.getType())) {
                return OrganizationSubEditPart.VISUAL_ID;
            } else {
                return -1;
            }
        }
        return com.tibco.xpd.om.modeler.subdiagram.part.OrganizationModelVisualIDRegistry
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
                OrganizationModelSubDiagramEditorPlugin
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
        if (OMPackage.eINSTANCE.getOrganization()
                .isSuperTypeOf(domainElement.eClass())
                && isDiagram((Organization) domainElement)) {
            return OrganizationSubEditPart.VISUAL_ID;
        }
        return -1;
    }

    /**
     * @generated
     */
    public static int getNodeVisualID(View containerView, EObject domainElement) {
        if (domainElement == null) {
            return -1;
        }
        String containerModelID =
                com.tibco.xpd.om.modeler.subdiagram.part.OrganizationModelVisualIDRegistry
                        .getModelID(containerView);
        if (!OrganizationSubEditPart.MODEL_ID.equals(containerModelID)) {
            return -1;
        }
        int containerVisualID;
        if (OrganizationSubEditPart.MODEL_ID.equals(containerModelID)) {
            containerVisualID =
                    com.tibco.xpd.om.modeler.subdiagram.part.OrganizationModelVisualIDRegistry
                            .getVisualID(containerView);
        } else {
            if (containerView instanceof Diagram) {
                containerVisualID = OrganizationSubEditPart.VISUAL_ID;
            } else {
                return -1;
            }
        }
        switch (containerVisualID) {
        case OrgUnitPositionSubCompartmentEditPart.VISUAL_ID:
            if (OMPackage.eINSTANCE.getPosition()
                    .isSuperTypeOf(domainElement.eClass())) {
                return PositionSubEditPart.VISUAL_ID;
            }
            break;
        case OrganizationSubEditPart.VISUAL_ID:
            if (OMPackage.eINSTANCE.getDynamicOrgUnit()
                    .isSuperTypeOf(domainElement.eClass())) {
                return DynamicOrgUnitEditPart.VISUAL_ID;
            }

            if (OMPackage.eINSTANCE.getOrgUnit()
                    .isSuperTypeOf(domainElement.eClass())) {
                return OrgUnitSubEditPart.VISUAL_ID;
            }

            break;
        }
        return -1;
    }

    /**
     * @generated
     */
    public static boolean canCreateNode(View containerView, int nodeVisualID) {
        String containerModelID =
                com.tibco.xpd.om.modeler.subdiagram.part.OrganizationModelVisualIDRegistry
                        .getModelID(containerView);
        if (!OrganizationSubEditPart.MODEL_ID.equals(containerModelID)) {
            return false;
        }
        int containerVisualID;
        if (OrganizationSubEditPart.MODEL_ID.equals(containerModelID)) {
            containerVisualID =
                    com.tibco.xpd.om.modeler.subdiagram.part.OrganizationModelVisualIDRegistry
                            .getVisualID(containerView);
        } else {
            if (containerView instanceof Diagram) {
                containerVisualID = OrganizationSubEditPart.VISUAL_ID;
            } else {
                return false;
            }
        }
        switch (containerVisualID) {
        case OrgUnitSubEditPart.VISUAL_ID:
            if (OrgUnitSubDisplayNameEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (OrgUnitSubFeatureNameEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (OrgUnitPositionSubCompartmentEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            break;
        case DynamicOrgUnitEditPart.VISUAL_ID:
            if (DynamicOrgUnitDisplayNameEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (DynamicOrgReferenceLabelEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            break;
        case OrgUnitPositionSubCompartmentEditPart.VISUAL_ID:
            if (PositionSubEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            break;
        case OrganizationSubEditPart.VISUAL_ID:
            if (OrgUnitSubEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (DynamicOrgUnitEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            break;
        case OrgUnitSubRelationshipEditPart.VISUAL_ID:
            if (OrgUnitSubRelationshipDisplayNameEditPart.VISUAL_ID == nodeVisualID) {
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
            return OrgUnitSubRelationshipEditPart.VISUAL_ID;
        }
        return -1;
    }

    /**
     * User can change implementation of this method to handle some specific
     * situations not covered by default logic.
     * 
     * @generated
     */
    private static boolean isDiagram(Organization element) {
        return true;
    }

}
