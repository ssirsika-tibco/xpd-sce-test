/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.part;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationCardinalityAtSourceLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationCardinalityAtTargetLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationClassClassifierAttributesCompartmentEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationClassClassifierOperationsCompartmentEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationClassConnectorEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationClassDanglingNodeEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationClassEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationClassNameEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationClassOperationEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationClassPropertyEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationClassStereoTypeLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationClassSuperClassNameEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationEndEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationEndTargetMultiplicityLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationEndTargetNameLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationNameEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationSourceLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationTargetLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.BadgeAuthorEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.BadgeDateCreatedEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.BadgeDateModifiedEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.BadgeDiagramNameEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.BadgeEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.BadgeLabelAuthorEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.BadgeLabelDateCreatedEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.BadgeLabelDateModifiedEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.BadgeLabelModelIconEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.BadgeModelNameEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.CanvasPackageEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.ClassClassAttributesCompartmentEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.ClassClassOperationsCompartmentEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.ClassEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.ClassNameEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.ClassStereoTypeLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.EnumerationEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.EnumerationEnumLitCompartmentEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.EnumerationLiteralEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.EnumerationNameEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.EnumerationStereoTypeLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.EnumerationSuperTypeNameEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.GeneralizationEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.OperationEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.PackageBadgeProfilesCompartmentEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.PackageEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.PackageNameEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.PackagePackageContentsCompartmentEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.PackageStereoTypeLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.PrimTypeStereoTypeLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.PrimTypeSuperTypeNameLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.PrimitiveTypeEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.PrimitiveTypeNameEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.ProfileApplicationEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.PropertyEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.StereotypeLineLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.SuperClassNameLabelEditPart;
import com.tibco.xpd.bom.resources.utils.UML2ModelUtil;

/**
 * This registry is used to determine which type of visual object should be
 * created for the corresponding Diagram, Node, ChildNode or Link represented by
 * a domain model object.
 * 
 * @generated
 */
public class UMLVisualIDRegistry {

    /**
     * @generated
     */
    private static final String DEBUG_KEY =
            "com.tibco.xpd.bom.modeler.diagram/debug/visualID"; //$NON-NLS-1$

    /**
     * @generated
     */
    public static int getVisualID(View view) {
        if (view instanceof Diagram) {
            if (CanvasPackageEditPart.MODEL_ID.equals(view.getType())) {
                return CanvasPackageEditPart.VISUAL_ID;
            } else {
                return -1;
            }
        }
        return com.tibco.xpd.bom.modeler.diagram.part.UMLVisualIDRegistry
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
                return (String) annotation.getDetails().get("modelID"); //$NON-NLS-1$
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
            if (Boolean.TRUE.toString().equalsIgnoreCase(Platform
                    .getDebugOption(DEBUG_KEY))) {
                BOMDiagramEditorPlugin
                        .getInstance()
                        .logError("Unable to parse view type as a visualID number: " //$NON-NLS-1$
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
        if (UMLPackage.eINSTANCE.getPackage().isSuperTypeOf(domainElement
                .eClass())
                && isDiagram((Package) domainElement)) {
            return CanvasPackageEditPart.VISUAL_ID;
        }
        return -1;
    }

    /**
     * @generated NOT
     */
    public static int getNodeVisualID(View containerView, EObject domainElement) {
        if (domainElement == null) {
            return -1;
        }

        String containerModelID =
                com.tibco.xpd.bom.modeler.diagram.part.UMLVisualIDRegistry
                        .getModelID(containerView);
        if (!CanvasPackageEditPart.MODEL_ID.equals(containerModelID)) {
            return -1;
        }
        int containerVisualID;
        if (CanvasPackageEditPart.MODEL_ID.equals(containerModelID)) {
            containerVisualID =
                    com.tibco.xpd.bom.modeler.diagram.part.UMLVisualIDRegistry
                            .getVisualID(containerView);
        } else {
            if (containerView instanceof Diagram) {
                containerVisualID = CanvasPackageEditPart.VISUAL_ID;
            } else {
                return -1;
            }
        }
        switch (containerVisualID) {
        case CanvasPackageEditPart.VISUAL_ID:
            if (domainElement instanceof AssociationClass
                    && !hasViewChild(containerView,
                            domainElement,
                            AssociationClassDanglingNodeEditPart.VISUAL_ID)) {
                return AssociationClassDanglingNodeEditPart.VISUAL_ID;
            }
            if (domainElement instanceof AssociationClass
                    && !hasViewChild(containerView,
                            domainElement,
                            AssociationClassEditPart.VISUAL_ID)) {

                return AssociationClassEditPart.VISUAL_ID;
            }
            if (domainElement instanceof Model) {
                return BadgeEditPart.VISUAL_ID;
            }
            if (domainElement instanceof Package) {
                if (containerView.getElement() == domainElement) {
                    return BadgeEditPart.VISUAL_ID;
                }
            }

        case PackagePackageContentsCompartmentEditPart.VISUAL_ID:

            if (domainElement instanceof AssociationClass
                    && !hasViewChild(containerView,
                            domainElement,
                            AssociationClassDanglingNodeEditPart.VISUAL_ID)) {
                return AssociationClassDanglingNodeEditPart.VISUAL_ID;
            }
            if (domainElement instanceof AssociationClass
                    && !hasViewChild(containerView,
                            domainElement,
                            AssociationClassEditPart.VISUAL_ID)) {

                return AssociationClassEditPart.VISUAL_ID;
            }

            if (UMLPackage.eINSTANCE.getPackage().isSuperTypeOf(domainElement
                    .eClass())) {
                return PackageEditPart.VISUAL_ID;

            }
            if (UMLPackage.eINSTANCE.getClass_().isSuperTypeOf(domainElement
                    .eClass())) {
                return ClassEditPart.VISUAL_ID;
            }
            if (UMLPackage.eINSTANCE.getPrimitiveType()
                    .isSuperTypeOf(domainElement.eClass())) {
                return PrimitiveTypeEditPart.VISUAL_ID;
            }
            if (UMLPackage.eINSTANCE.getEnumeration()
                    .isSuperTypeOf(domainElement.eClass())) {
                return EnumerationEditPart.VISUAL_ID;
            }

            break;
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
                com.tibco.xpd.bom.modeler.diagram.part.UMLVisualIDRegistry
                        .getModelID(containerView);
        if (!CanvasPackageEditPart.MODEL_ID.equals(containerModelID)) {
            return -1;
        }
        int containerVisualID;
        if (CanvasPackageEditPart.MODEL_ID.equals(containerModelID)) {
            containerVisualID =
                    com.tibco.xpd.bom.modeler.diagram.part.UMLVisualIDRegistry
                            .getVisualID(containerView);
        } else {
            if (containerView instanceof Diagram) {
                containerVisualID = CanvasPackageEditPart.VISUAL_ID;
            } else {
                return -1;
            }
        }
        switch (containerVisualID) {
        case ClassClassAttributesCompartmentEditPart.VISUAL_ID:
            if (UMLPackage.eINSTANCE.getProperty().isSuperTypeOf(domainElement
                    .eClass())) {
                return PropertyEditPart.VISUAL_ID;
            }
            break;
        case ClassClassOperationsCompartmentEditPart.VISUAL_ID:
            if (UMLPackage.eINSTANCE.getOperation().isSuperTypeOf(domainElement
                    .eClass())) {
                return OperationEditPart.VISUAL_ID;
            }
            break;
        case EnumerationEnumLitCompartmentEditPart.VISUAL_ID:
            if (UMLPackage.eINSTANCE.getEnumerationLiteral()
                    .isSuperTypeOf(domainElement.eClass())) {
                return EnumerationLiteralEditPart.VISUAL_ID;
            }
            break;
        case AssociationClassClassifierAttributesCompartmentEditPart.VISUAL_ID:
            if (UMLPackage.eINSTANCE.getProperty().isSuperTypeOf(domainElement
                    .eClass())) {
                return AssociationClassPropertyEditPart.VISUAL_ID;
            }
            break;
        case AssociationClassClassifierOperationsCompartmentEditPart.VISUAL_ID:
            if (UMLPackage.eINSTANCE.getOperation().isSuperTypeOf(domainElement
                    .eClass())) {
                return AssociationClassOperationEditPart.VISUAL_ID;
            }
            break;
        case PackageBadgeProfilesCompartmentEditPart.VISUAL_ID:
            if (UMLPackage.eINSTANCE.getProfileApplication()
                    .isSuperTypeOf(domainElement.eClass())) {
                return ProfileApplicationEditPart.VISUAL_ID;
            }
            break;
        case CanvasPackageEditPart.VISUAL_ID:
            if (UMLPackage.eINSTANCE.getPackage().isSuperTypeOf(domainElement
                    .eClass())) {
                return PackageEditPart.VISUAL_ID;
            }
            if (UMLPackage.eINSTANCE.getClass_().isSuperTypeOf(domainElement
                    .eClass())) {
                return ClassEditPart.VISUAL_ID;
            }
            if (UMLPackage.eINSTANCE.getPrimitiveType()
                    .isSuperTypeOf(domainElement.eClass())) {
                return PrimitiveTypeEditPart.VISUAL_ID;
            }
            if (UMLPackage.eINSTANCE.getEnumeration()
                    .isSuperTypeOf(domainElement.eClass())) {
                return EnumerationEditPart.VISUAL_ID;
            }
            if (UMLPackage.eINSTANCE.getAssociationClass()
                    .isSuperTypeOf(domainElement.eClass())) {
                return AssociationClassEditPart.VISUAL_ID;
            }
            if (UMLPackage.eINSTANCE.getAssociationClass()
                    .isSuperTypeOf(domainElement.eClass())) {
                return AssociationClassDanglingNodeEditPart.VISUAL_ID;
            }
            if (UMLPackage.eINSTANCE.getPackage().isSuperTypeOf(domainElement
                    .eClass())) {
                return BadgeEditPart.VISUAL_ID;
            }
            break;
        }
        return -1;
    }

    /**
     * @generated NOT
     */
    public static boolean canCreateNode(View containerView, int nodeVisualID) {
        String containerModelID =
                com.tibco.xpd.bom.modeler.diagram.part.UMLVisualIDRegistry
                        .getModelID(containerView);

        int containerVisualID;
        if (CanvasPackageEditPart.MODEL_ID.equals(containerModelID)) {
            containerVisualID =
                    com.tibco.xpd.bom.modeler.diagram.part.UMLVisualIDRegistry
                            .getVisualID(containerView);
        } else {
            if (containerView instanceof Diagram) {
                containerVisualID = CanvasPackageEditPart.VISUAL_ID;
            } else {
                return false;
            }
        }

        switch (containerVisualID) {

        case BadgeEditPart.VISUAL_ID:
            if (BadgeDiagramNameEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }

        case PackagePackageContentsCompartmentEditPart.VISUAL_ID:
            if (ClassEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (PackageEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (PrimitiveTypeEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (EnumerationEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (AssociationClassEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (AssociationClassDanglingNodeEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }

            break;
        }

        return canCreateNodeGen(containerView, nodeVisualID);
    }

    /**
     * @generated
     */
    public static boolean canCreateNodeGen(View containerView, int nodeVisualID) {
        String containerModelID =
                com.tibco.xpd.bom.modeler.diagram.part.UMLVisualIDRegistry
                        .getModelID(containerView);
        if (!CanvasPackageEditPart.MODEL_ID.equals(containerModelID)) {
            return false;
        }
        int containerVisualID;
        if (CanvasPackageEditPart.MODEL_ID.equals(containerModelID)) {
            containerVisualID =
                    com.tibco.xpd.bom.modeler.diagram.part.UMLVisualIDRegistry
                            .getVisualID(containerView);
        } else {
            if (containerView instanceof Diagram) {
                containerVisualID = CanvasPackageEditPart.VISUAL_ID;
            } else {
                return false;
            }
        }
        switch (containerVisualID) {
        case PackageEditPart.VISUAL_ID:
            if (PackageNameEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (PackageStereoTypeLabelEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (PackagePackageContentsCompartmentEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            break;
        case ClassEditPart.VISUAL_ID:
            if (ClassNameEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (SuperClassNameLabelEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (ClassStereoTypeLabelEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (ClassClassAttributesCompartmentEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (ClassClassOperationsCompartmentEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            break;
        case PrimitiveTypeEditPart.VISUAL_ID:
            if (PrimitiveTypeNameEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (PrimTypeSuperTypeNameLabelEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (PrimTypeStereoTypeLabelEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            break;
        case EnumerationEditPart.VISUAL_ID:
            if (EnumerationNameEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (EnumerationSuperTypeNameEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (EnumerationStereoTypeLabelEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (EnumerationEnumLitCompartmentEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            break;
        case AssociationClassEditPart.VISUAL_ID:
            if (AssociationClassNameEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (AssociationClassSuperClassNameEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (AssociationClassStereoTypeLabelEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (AssociationClassClassifierAttributesCompartmentEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (AssociationClassClassifierOperationsCompartmentEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            break;
        case BadgeEditPart.VISUAL_ID:
            if (BadgeModelNameEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (BadgeLabelAuthorEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (BadgeAuthorEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (BadgeLabelModelIconEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (BadgeLabelDateCreatedEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (BadgeDateCreatedEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (BadgeLabelDateModifiedEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (BadgeDateModifiedEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (PackageBadgeProfilesCompartmentEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            break;
        case ClassClassAttributesCompartmentEditPart.VISUAL_ID:
            if (PropertyEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            break;
        case ClassClassOperationsCompartmentEditPart.VISUAL_ID:
            if (OperationEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            break;
        case EnumerationEnumLitCompartmentEditPart.VISUAL_ID:
            if (EnumerationLiteralEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            break;
        case AssociationClassClassifierAttributesCompartmentEditPart.VISUAL_ID:
            if (AssociationClassPropertyEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            break;
        case AssociationClassClassifierOperationsCompartmentEditPart.VISUAL_ID:
            if (AssociationClassOperationEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            break;
        case PackageBadgeProfilesCompartmentEditPart.VISUAL_ID:
            if (ProfileApplicationEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            break;
        case CanvasPackageEditPart.VISUAL_ID:
            if (PackageEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (ClassEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (PrimitiveTypeEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (EnumerationEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (AssociationClassEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (AssociationClassDanglingNodeEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (BadgeEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            break;
        case GeneralizationEditPart.VISUAL_ID:
            if (StereotypeLineLabelEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            break;
        case AssociationEditPart.VISUAL_ID:
            if (AssociationNameEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (AssociationCardinalityAtSourceLabelEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (AssociationSourceLabelEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (AssociationCardinalityAtTargetLabelEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (AssociationTargetLabelEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            break;
        case AssociationEndEditPart.VISUAL_ID:
            if (AssociationEndTargetNameLabelEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            if (AssociationEndTargetMultiplicityLabelEditPart.VISUAL_ID == nodeVisualID) {
                return true;
            }
            break;
        }
        return false;
    }

    /**
     * @generated NOT
     */
    public static int getLinkWithClassVisualID(EObject domainElement) {
        if (domainElement == null) {
            return -1;
        }
        if (domainElement instanceof AssociationClass) {
            if (UMLPackage.eINSTANCE.getAssociationClass()
                    .isSuperTypeOf(domainElement.eClass())) {
                return AssociationClassConnectorEditPart.VISUAL_ID;
            }
        }

        if (UMLPackage.eINSTANCE.getAssociation().isSuperTypeOf(domainElement
                .eClass())) {

            // Check each end member's Aggregation kind so that we select the
            // correct Association edit part
            // i.e. Association, Aggregation or Composite
            Association assoc = (Association) domainElement;
            AggregationKind aggKind = UML2ModelUtil.getAggregationType(assoc);

            if (aggKind == AggregationKind.NONE_LITERAL) {
                return AssociationEditPart.VISUAL_ID;
            }
            if (aggKind == AggregationKind.SHARED_LITERAL) {
                return AssociationEditPart.VISUAL_ID;
            }
            if (aggKind == AggregationKind.COMPOSITE_LITERAL) {
                return AssociationEditPart.VISUAL_ID;
            }
            return -1;
        }

        return getLinkWithClassVisualIDGen(domainElement);
    }

    /**
     * @generated
     */
    public static int getLinkWithClassVisualIDGen(EObject domainElement) {
        if (domainElement == null) {
            return -1;
        }
        if (UMLPackage.eINSTANCE.getGeneralization()
                .isSuperTypeOf(domainElement.eClass())) {
            return GeneralizationEditPart.VISUAL_ID;
        }
        if (UMLPackage.eINSTANCE.getAssociation().isSuperTypeOf(domainElement
                .eClass())) {
            return AssociationEditPart.VISUAL_ID;
        }
        if (UMLPackage.eINSTANCE.getProperty().isSuperTypeOf(domainElement
                .eClass())) {
            return AssociationEndEditPart.VISUAL_ID;
        }
        if (UMLPackage.eINSTANCE.getAssociationClass()
                .isSuperTypeOf(domainElement.eClass())) {
            return AssociationClassConnectorEditPart.VISUAL_ID;
        }
        return -1;
    }

    /**
     * User can change implementation of this method to handle some specific
     * situations not covered by default logic.
     * 
     * @generated
     */
    private static boolean isDiagram(Package element) {
        return true;
    }

    protected static boolean hasViewChild(View containerView,
            EObject domainElement, int visualId) {
        if (domainElement == null) {
            return false;
        }
        for (Object next : containerView.getChildren()) {
            View nextView = (View) next;
            if (domainElement.equals(nextView.getElement())
                    && getType(visualId).equals(nextView.getType())) {
                return true;
            }
        }
        return false;
    }

}
