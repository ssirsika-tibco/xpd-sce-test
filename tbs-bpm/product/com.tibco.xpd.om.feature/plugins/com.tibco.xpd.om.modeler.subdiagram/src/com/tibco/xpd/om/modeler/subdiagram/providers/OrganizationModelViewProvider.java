package com.tibco.xpd.om.modeler.subdiagram.providers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.core.providers.AbstractViewProvider;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.notation.View;

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
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.custom.LabelOrgModelSubBadgeEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.custom.LabelOrgTypeSubBadgeEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.custom.OrgModelNameSubBadgeEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.custom.OrgTypeSubBadgeEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.custom.OrganizationNameSubBadgeEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.custom.OrganizationSubBadgeEditPart;
import com.tibco.xpd.om.modeler.subdiagram.part.OrganizationModelVisualIDRegistry;
import com.tibco.xpd.om.modeler.subdiagram.view.factories.DynamicOrgReferenceWrappingLabelViewFactory;
import com.tibco.xpd.om.modeler.subdiagram.view.factories.DynamicOrgUnitDisplayNameViewFactory;
import com.tibco.xpd.om.modeler.subdiagram.view.factories.DynamicOrgUnitViewFactory;
import com.tibco.xpd.om.modeler.subdiagram.view.factories.OrgUnitPositionSubCompartmentViewFactory;
import com.tibco.xpd.om.modeler.subdiagram.view.factories.OrgUnitSubDisplayNameViewFactory;
import com.tibco.xpd.om.modeler.subdiagram.view.factories.OrgUnitSubFeatureNameViewFactory;
import com.tibco.xpd.om.modeler.subdiagram.view.factories.OrgUnitSubRelationshipDisplayNameViewFactory;
import com.tibco.xpd.om.modeler.subdiagram.view.factories.OrgUnitSubRelationshipViewFactory;
import com.tibco.xpd.om.modeler.subdiagram.view.factories.OrgUnitSubViewFactory;
import com.tibco.xpd.om.modeler.subdiagram.view.factories.OrganizationSubViewFactory;
import com.tibco.xpd.om.modeler.subdiagram.view.factories.PositionSubViewFactory;
import com.tibco.xpd.om.modeler.subdiagram.view.factories.custom.LabelOrgModelNameSubBadgeViewFactory;
import com.tibco.xpd.om.modeler.subdiagram.view.factories.custom.LabelOrgTypeSubBadgeViewFactory;
import com.tibco.xpd.om.modeler.subdiagram.view.factories.custom.OrgModelNameSubBadgeViewFactory;
import com.tibco.xpd.om.modeler.subdiagram.view.factories.custom.OrgTypeSubBadgeViewFactory;
import com.tibco.xpd.om.modeler.subdiagram.view.factories.custom.OrganizationNameSubBadgeViewFactory;
import com.tibco.xpd.om.modeler.subdiagram.view.factories.custom.OrganizationSubBadgeViewFactory;

/**
 * @generated
 */
public class OrganizationModelViewProvider extends AbstractViewProvider {

	/**
	 * @generated
	 */
	@Override
	protected Class getDiagramViewClass(IAdaptable semanticAdapter,
			String diagramKind) {
		EObject semanticElement = getSemanticElement(semanticAdapter);
		if (OrganizationSubEditPart.MODEL_ID.equals(diagramKind)
				&& OrganizationModelVisualIDRegistry
						.getDiagramVisualID(semanticElement) != -1) {
			return OrganizationSubViewFactory.class;
		}
		return null;
	}

	@Override
	protected Class getNodeViewClass(IAdaptable semanticAdapter,
			View containerView, String semanticHint) {
		if (containerView == null) {
			return null;
		}

		if (semanticHint != null
				&& semanticHint.equals(OrganizationSubBadgeEditPart.VISUAL_ID)) {
			return OrganizationSubBadgeViewFactory.class;
		}

		if (semanticHint != null
				&& semanticHint
						.equals(OrganizationNameSubBadgeEditPart.VISUAL_ID)) {
			return OrganizationNameSubBadgeViewFactory.class;
		}

		if (semanticHint != null
				&& semanticHint.equals(OrgModelNameSubBadgeEditPart.VISUAL_ID)) {
			return OrgModelNameSubBadgeViewFactory.class;
		}

		if (semanticHint != null
				&& semanticHint.equals(LabelOrgModelSubBadgeEditPart.VISUAL_ID)) {
			return LabelOrgModelNameSubBadgeViewFactory.class;
		}

		if (semanticHint != null
				&& semanticHint.equals(OrgTypeSubBadgeEditPart.VISUAL_ID)) {
			return OrgTypeSubBadgeViewFactory.class;
		}

		if (semanticHint != null
				&& semanticHint.equals(LabelOrgTypeSubBadgeEditPart.VISUAL_ID)) {
			return LabelOrgTypeSubBadgeViewFactory.class;
		}

		return getNodeViewClassGen(semanticAdapter, containerView, semanticHint);
	}

	/**
	 * @generated
	 */
	protected Class getNodeViewClassGen(IAdaptable semanticAdapter,
			View containerView, String semanticHint) {
		if (containerView == null) {
			return null;
		}
		IElementType elementType = getSemanticElementType(semanticAdapter);
		EObject domainElement = getSemanticElement(semanticAdapter);
		int visualID;
		if (semanticHint == null) {
			// Semantic hint is not specified. Can be a result of call from CanonicalEditPolicy.
			// In this situation there should be NO elementType, visualID will be determined
			// by VisualIDRegistry.getNodeVisualID() for domainElement.
			if (elementType != null || domainElement == null) {
				return null;
			}
			visualID = OrganizationModelVisualIDRegistry.getNodeVisualID(
					containerView, domainElement);
		} else {
			visualID = OrganizationModelVisualIDRegistry
					.getVisualID(semanticHint);
			if (elementType != null) {
				// Semantic hint is specified together with element type.
				// Both parameters should describe exactly the same diagram element.
				// In addition we check that visualID returned by VisualIDRegistry.getNodeVisualID() for
				// domainElement (if specified) is the same as in element type.
				if (!OrganizationModelElementTypes
						.isKnownElementType(elementType)
						|| (!(elementType instanceof IHintedType))) {
					return null; // foreign element type
				}
				String elementTypeHint = ((IHintedType) elementType)
						.getSemanticHint();
				if (!semanticHint.equals(elementTypeHint)) {
					return null; // if semantic hint is specified it should be the same as in element type
				}
				if (domainElement != null
						&& visualID != OrganizationModelVisualIDRegistry
								.getNodeVisualID(containerView, domainElement)) {
					return null; // visual id for node EClass should match visual id from element type
				}
			} else {
				// Element type is not specified. Domain element should be present (except pure design elements).
				// This method is called with EObjectAdapter as parameter from:
				//   - ViewService.createNode(View container, EObject eObject, String type, PreferencesHint preferencesHint) 
				//   - generated ViewFactory.decorateView() for parent element
				if (!OrganizationSubEditPart.MODEL_ID
						.equals(OrganizationModelVisualIDRegistry
								.getModelID(containerView))) {
					return null; // foreign diagram
				}
				switch (visualID) {
				case OrgUnitSubEditPart.VISUAL_ID:
				case DynamicOrgUnitEditPart.VISUAL_ID:
				case PositionSubEditPart.VISUAL_ID:
					if (domainElement == null
							|| visualID != OrganizationModelVisualIDRegistry
									.getNodeVisualID(containerView,
											domainElement)) {
						return null; // visual id in semantic hint should match visual id for domain element
					}
					break;
				case OrgUnitSubDisplayNameEditPart.VISUAL_ID:
				case OrgUnitSubFeatureNameEditPart.VISUAL_ID:
				case OrgUnitPositionSubCompartmentEditPart.VISUAL_ID:
					if (OrgUnitSubEditPart.VISUAL_ID != OrganizationModelVisualIDRegistry
							.getVisualID(containerView)
							|| containerView.getElement() != domainElement) {
						return null; // wrong container
					}
					break;
				case DynamicOrgUnitDisplayNameEditPart.VISUAL_ID:
				case DynamicOrgReferenceLabelEditPart.VISUAL_ID:
					if (DynamicOrgUnitEditPart.VISUAL_ID != OrganizationModelVisualIDRegistry
							.getVisualID(containerView)
							|| containerView.getElement() != domainElement) {
						return null; // wrong container
					}
					break;
				case OrgUnitSubRelationshipDisplayNameEditPart.VISUAL_ID:
					if (OrgUnitSubRelationshipEditPart.VISUAL_ID != OrganizationModelVisualIDRegistry
							.getVisualID(containerView)
							|| containerView.getElement() != domainElement) {
						return null; // wrong container
					}
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
				|| !OrganizationModelVisualIDRegistry.canCreateNode(
						containerView, visualID)) {
			return null;
		}
		switch (visualID) {
		case OrgUnitSubEditPart.VISUAL_ID:
			return OrgUnitSubViewFactory.class;
		case OrgUnitSubDisplayNameEditPart.VISUAL_ID:
			return OrgUnitSubDisplayNameViewFactory.class;
		case OrgUnitSubFeatureNameEditPart.VISUAL_ID:
			return OrgUnitSubFeatureNameViewFactory.class;
		case DynamicOrgUnitEditPart.VISUAL_ID:
			return DynamicOrgUnitViewFactory.class;
		case DynamicOrgUnitDisplayNameEditPart.VISUAL_ID:
			return DynamicOrgUnitDisplayNameViewFactory.class;
		case DynamicOrgReferenceLabelEditPart.VISUAL_ID:
			return DynamicOrgReferenceWrappingLabelViewFactory.class;
		case PositionSubEditPart.VISUAL_ID:
			return PositionSubViewFactory.class;
		case OrgUnitPositionSubCompartmentEditPart.VISUAL_ID:
			return OrgUnitPositionSubCompartmentViewFactory.class;
		case OrgUnitSubRelationshipDisplayNameEditPart.VISUAL_ID:
			return OrgUnitSubRelationshipDisplayNameViewFactory.class;
		}
		return null;
	}

	/**
	 * @generated
	 */
	@Override
	protected Class getEdgeViewClass(IAdaptable semanticAdapter,
			View containerView, String semanticHint) {
		IElementType elementType = getSemanticElementType(semanticAdapter);
		if (!OrganizationModelElementTypes.isKnownElementType(elementType)
				|| (!(elementType instanceof IHintedType))) {
			return null; // foreign element type
		}
		String elementTypeHint = ((IHintedType) elementType).getSemanticHint();
		if (elementTypeHint == null) {
			return null; // our hint is visual id and must be specified
		}
		if (semanticHint != null && !semanticHint.equals(elementTypeHint)) {
			return null; // if semantic hint is specified it should be the same as in element type
		}
		int visualID = OrganizationModelVisualIDRegistry
				.getVisualID(elementTypeHint);
		EObject domainElement = getSemanticElement(semanticAdapter);
		if (domainElement != null
				&& visualID != OrganizationModelVisualIDRegistry
						.getLinkWithClassVisualID(domainElement)) {
			return null; // visual id for link EClass should match visual id from element type
		}
		return getEdgeViewClass(visualID);
	}

	/**
	 * @generated
	 */
	protected Class getEdgeViewClass(int visualID) {
		switch (visualID) {
		case OrgUnitSubRelationshipEditPart.VISUAL_ID:
			return OrgUnitSubRelationshipViewFactory.class;
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
