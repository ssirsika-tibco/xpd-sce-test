/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.providers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.core.providers.AbstractViewProvider;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.notation.View;

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
import com.tibco.xpd.bom.modeler.diagram.part.UMLVisualIDRegistry;
import com.tibco.xpd.bom.modeler.diagram.view.factories.AssociationCardinalityAtSourceLabelViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.AssociationCardinalityAtTargetLabelViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.AssociationClassClassifierAttributesCompartmentViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.AssociationClassClassifierOperationsCompartmentViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.AssociationClassConnectorViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.AssociationClassDanglingNodeViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.AssociationClassNameViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.AssociationClassOperationViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.AssociationClassPropertyViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.AssociationClassStereoTypeLabelViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.AssociationClassSuperClassNameViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.AssociationClassViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.AssociationEndTargetMultiplicityLabelViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.AssociationEndTargetNameLabelViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.AssociationEndViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.AssociationNameViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.AssociationSourceLabelViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.AssociationTargetLabelViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.AssociationViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.BadgeAuthorViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.BadgeDateCreatedViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.BadgeDateModifiedViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.BadgeDiagramNameViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.BadgeLabelAuthorViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.BadgeLabelDateCreatedViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.BadgeLabelDateModifiedViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.BadgeLabelModelIconViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.BadgeModelNameViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.BadgeViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.CanvasPackageViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.ClassClassAttributesCompartmentViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.ClassClassOperationsCompartmentViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.ClassNameViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.ClassStereoTypeLabelViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.ClassViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.EnumerationEnumLitCompartmentViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.EnumerationLiteralViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.EnumerationNameViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.EnumerationStereoTypeLabelViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.EnumerationSuperTypeNameViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.EnumerationViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.GeneralizationViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.OperationViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.PackageBadgeProfilesCompartmentViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.PackageNameViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.PackagePackageContentsCompartmentViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.PackageStereoTypeLabelViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.PackageViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.PrimTypeStereoTypeLabelViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.PrimTypeSuperTypeNameLabelViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.PrimitiveTypeNameViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.PrimitiveTypeViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.ProfileApplicationViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.PropertyViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.StereotypeLineLabelViewFactory;
import com.tibco.xpd.bom.modeler.diagram.view.factories.SuperClassNameLabelViewFactory;

/**
 * @generated
 */
public class UMLViewProvider extends AbstractViewProvider {

    /**
     * @generated
     */
    protected Class getDiagramViewClass(IAdaptable semanticAdapter,
            String diagramKind) {
        EObject semanticElement = getSemanticElement(semanticAdapter);
        if (CanvasPackageEditPart.MODEL_ID.equals(diagramKind)
                && UMLVisualIDRegistry.getDiagramVisualID(semanticElement) != -1) {
            return CanvasPackageViewFactory.class;
        }
        return null;
    }

    /**
     * @generated NOT
     */
    protected Class getNodeViewClass(IAdaptable semanticAdapter,
            View containerView, String semanticHint) {
        if (containerView == null) {
            return null;
        }
        IElementType elementType = getSemanticElementType(semanticAdapter);
        EObject domainElement = getSemanticElement(semanticAdapter);
        int visualID;
        if (semanticHint == null) {
            // Semantic hint is not specified. Can be a result of call from
            // CanonicalEditPolicy.
            // In this situation there should be NO elementType, visualID will
            // be determined
            // by VisualIDRegistry.getNodeVisualID() for domainElement.
            if (elementType != null || domainElement == null) {
                return null;
            }
            visualID =
                    UMLVisualIDRegistry.getNodeVisualID(containerView,
                            domainElement);
        } else {
            visualID = UMLVisualIDRegistry.getVisualID(semanticHint);
            if (elementType != null) {
                // Semantic hint is specified together with element type.
                // Both parameters should describe exactly the same diagram
                // element.
                // In addition we check that visualID returned by
                // VisualIDRegistry.getNodeVisualID() for
                // domainElement (if specified) is the same as in element type.
                if (!UMLElementTypes.isKnownElementType(elementType)
                        || (!(elementType instanceof IHintedType))) {
                    return null; // foreign element type
                }
                String elementTypeHint =
                        ((IHintedType) elementType).getSemanticHint();
                if (!semanticHint.equals(elementTypeHint)) {
                    return null; // if semantic hint is specified it should be
                    // the same as in element type
                }
                if (domainElement != null
                        && visualID != UMLVisualIDRegistry
                                .getNodeVisualID(containerView, domainElement)) {
                    return null; // visual id for node EClass should match
                    // visual id from element type
                }
            } else {
                // Element type is not specified. Domain element should be
                // present (except pure design elements).
                // This method is called with EObjectAdapter as parameter from:
                // - ViewService.createNode(View container, EObject eObject,
                // String type, PreferencesHint preferencesHint)
                // - generated ViewFactory.decorateView() for parent element
                if (!CanvasPackageEditPart.MODEL_ID.equals(UMLVisualIDRegistry
                        .getModelID(containerView))) {
                    return null; // foreign diagram
                }
                switch (visualID) {
                case ClassEditPart.VISUAL_ID:
                case PrimitiveTypeEditPart.VISUAL_ID:
                case EnumerationEditPart.VISUAL_ID:
                case AssociationClassEditPart.VISUAL_ID:
                case PropertyEditPart.VISUAL_ID:
                case OperationEditPart.VISUAL_ID:
                case EnumerationLiteralEditPart.VISUAL_ID:
                case ProfileApplicationEditPart.VISUAL_ID:
                case PackageEditPart.VISUAL_ID:
                case AssociationClassDanglingNodeEditPart.VISUAL_ID:
                case BadgeEditPart.VISUAL_ID:
                case AssociationClassPropertyEditPart.VISUAL_ID:
                case AssociationClassOperationEditPart.VISUAL_ID:
                    if (domainElement == null
                            || visualID != UMLVisualIDRegistry
                                    .getNodeVisualID(containerView,
                                            domainElement)) {
                        return null; // visual id in semantic hint should match
                        // visual id for domain element
                    }
                    break;
                case PackageNameEditPart.VISUAL_ID:
                case PackageStereoTypeLabelEditPart.VISUAL_ID:
                case PackagePackageContentsCompartmentEditPart.VISUAL_ID:
                    if (PackageEditPart.VISUAL_ID != UMLVisualIDRegistry
                            .getVisualID(containerView)
                            || containerView.getElement() != domainElement) {
                        return null; // wrong container
                    }
                    break;
                case ClassNameEditPart.VISUAL_ID:
                case SuperClassNameLabelEditPart.VISUAL_ID:
                case ClassStereoTypeLabelEditPart.VISUAL_ID:
                case ClassClassAttributesCompartmentEditPart.VISUAL_ID:
                case ClassClassOperationsCompartmentEditPart.VISUAL_ID:
                    if (ClassEditPart.VISUAL_ID != UMLVisualIDRegistry
                            .getVisualID(containerView)
                            || containerView.getElement() != domainElement) {
                        return null; // wrong container
                    }
                    break;
                case PrimitiveTypeNameEditPart.VISUAL_ID:
                case PrimTypeSuperTypeNameLabelEditPart.VISUAL_ID:
                case PrimTypeStereoTypeLabelEditPart.VISUAL_ID:
                    if (PrimitiveTypeEditPart.VISUAL_ID != UMLVisualIDRegistry
                            .getVisualID(containerView)
                            || containerView.getElement() != domainElement) {
                        return null; // wrong container
                    }
                    break;
                case EnumerationNameEditPart.VISUAL_ID:
                case EnumerationSuperTypeNameEditPart.VISUAL_ID:
                case EnumerationStereoTypeLabelEditPart.VISUAL_ID:
                case EnumerationEnumLitCompartmentEditPart.VISUAL_ID:
                    if (EnumerationEditPart.VISUAL_ID != UMLVisualIDRegistry
                            .getVisualID(containerView)
                            || containerView.getElement() != domainElement) {
                        return null; // wrong container
                    }
                    break;
                case AssociationClassNameEditPart.VISUAL_ID:
                case AssociationClassSuperClassNameEditPart.VISUAL_ID:
                case AssociationClassStereoTypeLabelEditPart.VISUAL_ID:
                case AssociationClassClassifierAttributesCompartmentEditPart.VISUAL_ID:
                case AssociationClassClassifierOperationsCompartmentEditPart.VISUAL_ID:
                    if (AssociationClassEditPart.VISUAL_ID != UMLVisualIDRegistry
                            .getVisualID(containerView)
                            || containerView.getElement() != domainElement) {
                        return null; // wrong container
                    }
                    break;
                case BadgeModelNameEditPart.VISUAL_ID:
                case BadgeLabelAuthorEditPart.VISUAL_ID:
                case BadgeAuthorEditPart.VISUAL_ID:
                case BadgeLabelModelIconEditPart.VISUAL_ID:
                case BadgeLabelDateCreatedEditPart.VISUAL_ID:
                case BadgeDateCreatedEditPart.VISUAL_ID:
                case BadgeLabelDateModifiedEditPart.VISUAL_ID:
                case BadgeDateModifiedEditPart.VISUAL_ID:
                case PackageBadgeProfilesCompartmentEditPart.VISUAL_ID:
                    if (BadgeEditPart.VISUAL_ID != UMLVisualIDRegistry
                            .getVisualID(containerView)
                            || containerView.getElement() != domainElement) {
                        return null; // wrong container
                    }
                    break;
                case StereotypeLineLabelEditPart.VISUAL_ID:
                    if (GeneralizationEditPart.VISUAL_ID != UMLVisualIDRegistry
                            .getVisualID(containerView)
                            || containerView.getElement() != domainElement) {
                        return null; // wrong container
                    }
                    break;
                case AssociationNameEditPart.VISUAL_ID:
                case AssociationCardinalityAtSourceLabelEditPart.VISUAL_ID:
                case AssociationSourceLabelEditPart.VISUAL_ID:
                case AssociationCardinalityAtTargetLabelEditPart.VISUAL_ID:
                case AssociationTargetLabelEditPart.VISUAL_ID:
                    if (AssociationEditPart.VISUAL_ID != UMLVisualIDRegistry
                            .getVisualID(containerView)
                            || containerView.getElement() != domainElement) {
                        return null; // wrong container
                    }
                    break;
                case AssociationEndTargetNameLabelEditPart.VISUAL_ID:
                case AssociationEndTargetMultiplicityLabelEditPart.VISUAL_ID:
                    if (AssociationEndEditPart.VISUAL_ID != UMLVisualIDRegistry
                            .getVisualID(containerView)
                            || containerView.getElement() != domainElement) {
                        return null; // wrong container
                    }
                    break;

                case BadgeDiagramNameEditPart.VISUAL_ID:
                    break;

                default:
                    return null;
                }
            }
        }
        return getNodeViewClass(containerView, visualID);
    }

    /**
     * @generated
     */
    protected Class getNodeViewClass(View containerView, int visualID) {
        if (containerView == null
                || !UMLVisualIDRegistry.canCreateNode(containerView, visualID)) {
            return null;
        }
        switch (visualID) {
        case PackageEditPart.VISUAL_ID:
            return PackageViewFactory.class;
        case PackageNameEditPart.VISUAL_ID:
            return PackageNameViewFactory.class;
        case PackageStereoTypeLabelEditPart.VISUAL_ID:
            return PackageStereoTypeLabelViewFactory.class;
        case ClassEditPart.VISUAL_ID:
            return ClassViewFactory.class;
        case ClassNameEditPart.VISUAL_ID:
            return ClassNameViewFactory.class;
        case SuperClassNameLabelEditPart.VISUAL_ID:
            return SuperClassNameLabelViewFactory.class;
        case ClassStereoTypeLabelEditPart.VISUAL_ID:
            return ClassStereoTypeLabelViewFactory.class;
        case PrimitiveTypeEditPart.VISUAL_ID:
            return PrimitiveTypeViewFactory.class;
        case PrimitiveTypeNameEditPart.VISUAL_ID:
            return PrimitiveTypeNameViewFactory.class;
        case PrimTypeSuperTypeNameLabelEditPart.VISUAL_ID:
            return PrimTypeSuperTypeNameLabelViewFactory.class;
        case PrimTypeStereoTypeLabelEditPart.VISUAL_ID:
            return PrimTypeStereoTypeLabelViewFactory.class;
        case EnumerationEditPart.VISUAL_ID:
            return EnumerationViewFactory.class;
        case EnumerationNameEditPart.VISUAL_ID:
            return EnumerationNameViewFactory.class;
        case EnumerationSuperTypeNameEditPart.VISUAL_ID:
            return EnumerationSuperTypeNameViewFactory.class;
        case EnumerationStereoTypeLabelEditPart.VISUAL_ID:
            return EnumerationStereoTypeLabelViewFactory.class;
        case AssociationClassEditPart.VISUAL_ID:
            return AssociationClassViewFactory.class;
        case AssociationClassNameEditPart.VISUAL_ID:
            return AssociationClassNameViewFactory.class;
        case AssociationClassSuperClassNameEditPart.VISUAL_ID:
            return AssociationClassSuperClassNameViewFactory.class;
        case AssociationClassStereoTypeLabelEditPart.VISUAL_ID:
            return AssociationClassStereoTypeLabelViewFactory.class;
        case AssociationClassDanglingNodeEditPart.VISUAL_ID:
            return AssociationClassDanglingNodeViewFactory.class;
        case BadgeEditPart.VISUAL_ID:
            return BadgeViewFactory.class;
        case BadgeModelNameEditPart.VISUAL_ID:
            return BadgeModelNameViewFactory.class;
        case BadgeLabelAuthorEditPart.VISUAL_ID:
            return BadgeLabelAuthorViewFactory.class;
        case BadgeAuthorEditPart.VISUAL_ID:
            return BadgeAuthorViewFactory.class;
        case BadgeLabelModelIconEditPart.VISUAL_ID:
            return BadgeLabelModelIconViewFactory.class;
        case BadgeLabelDateCreatedEditPart.VISUAL_ID:
            return BadgeLabelDateCreatedViewFactory.class;
        case BadgeDateCreatedEditPart.VISUAL_ID:
            return BadgeDateCreatedViewFactory.class;
        case BadgeLabelDateModifiedEditPart.VISUAL_ID:
            return BadgeLabelDateModifiedViewFactory.class;
        case BadgeDateModifiedEditPart.VISUAL_ID:
            return BadgeDateModifiedViewFactory.class;
        case PropertyEditPart.VISUAL_ID:
            return PropertyViewFactory.class;
        case OperationEditPart.VISUAL_ID:
            return OperationViewFactory.class;
        case EnumerationLiteralEditPart.VISUAL_ID:
            return EnumerationLiteralViewFactory.class;
        case AssociationClassPropertyEditPart.VISUAL_ID:
            return AssociationClassPropertyViewFactory.class;
        case AssociationClassOperationEditPart.VISUAL_ID:
            return AssociationClassOperationViewFactory.class;
        case ProfileApplicationEditPart.VISUAL_ID:
            return ProfileApplicationViewFactory.class;
        case PackagePackageContentsCompartmentEditPart.VISUAL_ID:
            return PackagePackageContentsCompartmentViewFactory.class;
        case ClassClassAttributesCompartmentEditPart.VISUAL_ID:
            return ClassClassAttributesCompartmentViewFactory.class;
        case ClassClassOperationsCompartmentEditPart.VISUAL_ID:
            return ClassClassOperationsCompartmentViewFactory.class;
        case EnumerationEnumLitCompartmentEditPart.VISUAL_ID:
            return EnumerationEnumLitCompartmentViewFactory.class;
        case AssociationClassClassifierAttributesCompartmentEditPart.VISUAL_ID:
            return AssociationClassClassifierAttributesCompartmentViewFactory.class;
        case AssociationClassClassifierOperationsCompartmentEditPart.VISUAL_ID:
            return AssociationClassClassifierOperationsCompartmentViewFactory.class;
        case PackageBadgeProfilesCompartmentEditPart.VISUAL_ID:
            return PackageBadgeProfilesCompartmentViewFactory.class;
        case StereotypeLineLabelEditPart.VISUAL_ID:
            return StereotypeLineLabelViewFactory.class;
        case AssociationNameEditPart.VISUAL_ID:
            return AssociationNameViewFactory.class;
        case AssociationCardinalityAtSourceLabelEditPart.VISUAL_ID:
            return AssociationCardinalityAtSourceLabelViewFactory.class;
        case AssociationSourceLabelEditPart.VISUAL_ID:
            return AssociationSourceLabelViewFactory.class;
        case AssociationCardinalityAtTargetLabelEditPart.VISUAL_ID:
            return AssociationCardinalityAtTargetLabelViewFactory.class;
        case AssociationTargetLabelEditPart.VISUAL_ID:
            return AssociationTargetLabelViewFactory.class;
        case AssociationEndTargetNameLabelEditPart.VISUAL_ID:
            return AssociationEndTargetNameLabelViewFactory.class;
        case AssociationEndTargetMultiplicityLabelEditPart.VISUAL_ID:
            return AssociationEndTargetMultiplicityLabelViewFactory.class;
        case BadgeDiagramNameEditPart.VISUAL_ID:
            return BadgeDiagramNameViewFactory.class;
        }
        return null;
    }

    /**
     * @generated
     */
    protected Class getEdgeViewClass(IAdaptable semanticAdapter,
            View containerView, String semanticHint) {
        IElementType elementType = getSemanticElementType(semanticAdapter);
        if (!UMLElementTypes.isKnownElementType(elementType)
                || (!(elementType instanceof IHintedType))) {
            return null; // foreign element type
        }
        String elementTypeHint = ((IHintedType) elementType).getSemanticHint();
        if (elementTypeHint == null) {
            return null; // our hint is visual id and must be specified
        }
        if (semanticHint != null && !semanticHint.equals(elementTypeHint)) {
            return null; // if semantic hint is specified it should be the same
            // as in element type
        }
        int visualID = UMLVisualIDRegistry.getVisualID(elementTypeHint);
        EObject domainElement = getSemanticElement(semanticAdapter);
        if (domainElement != null
                && visualID != UMLVisualIDRegistry
                        .getLinkWithClassVisualID(domainElement)) {
            return null; // visual id for link EClass should match visual id
            // from element type
        }
        return getEdgeViewClass(visualID);
    }

    /**
     * @generated
     */
    protected Class getEdgeViewClass(int visualID) {
        switch (visualID) {
        case GeneralizationEditPart.VISUAL_ID:
            return GeneralizationViewFactory.class;
        case AssociationEditPart.VISUAL_ID:
            return AssociationViewFactory.class;
        case AssociationEndEditPart.VISUAL_ID:
            return AssociationEndViewFactory.class;
        case AssociationClassConnectorEditPart.VISUAL_ID:
            return AssociationClassConnectorViewFactory.class;
        }
        return null;
    }

    /**
     * @generated
     */
    private IElementType getSemanticElementType(IAdaptable semanticAdapter) {
        if (semanticAdapter == null) {
            return null;
        }
        return (IElementType) semanticAdapter.getAdapter(IElementType.class);
    }
}
