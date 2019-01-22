package com.tibco.xpd.om.modeler.diagram.providers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.core.providers.AbstractViewProvider;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.notation.View;

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
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.LabelAuthorBadgeEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.LabelDateCreatedBadgeEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.LabelDateModifiedBadgeEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.LabelOrgModelGroupsEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.LabelVersionBadgeEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.OrgModelAuthorBadgeEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.OrgModelBadgeEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.OrgModelDateCreatedBadgeEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.OrgModelDateModifiedBadgeEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.OrgModelGroupsCompartmentEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.OrgModelGroupsEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.OrgModelNameBadgeEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.OrgModelVersionBadgeEditPart;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelVisualIDRegistry;
import com.tibco.xpd.om.modeler.diagram.view.factories.DynamicOrgReferenceViewFactory;
import com.tibco.xpd.om.modeler.diagram.view.factories.DynamicOrgUnitDisplayNameViewFactory;
import com.tibco.xpd.om.modeler.diagram.view.factories.DynamicOrgUnitViewFactory;
import com.tibco.xpd.om.modeler.diagram.view.factories.OrgModelViewFactory;
import com.tibco.xpd.om.modeler.diagram.view.factories.OrgUnitDisplayNameViewFactory;
import com.tibco.xpd.om.modeler.diagram.view.factories.OrgUnitFeatureNameViewFactory;
import com.tibco.xpd.om.modeler.diagram.view.factories.OrgUnitPositionCompartmentViewFactory;
import com.tibco.xpd.om.modeler.diagram.view.factories.OrgUnitRelationshipDisplayNameViewFactory;
import com.tibco.xpd.om.modeler.diagram.view.factories.OrgUnitRelationshipViewFactory;
import com.tibco.xpd.om.modeler.diagram.view.factories.OrgUnitViewFactory;
import com.tibco.xpd.om.modeler.diagram.view.factories.OrganizationDisplayNameViewFactory;
import com.tibco.xpd.om.modeler.diagram.view.factories.OrganizationOrgUnitCompartmentViewFactory;
import com.tibco.xpd.om.modeler.diagram.view.factories.OrganizationTypeNameViewFactory;
import com.tibco.xpd.om.modeler.diagram.view.factories.OrganizationViewFactory;
import com.tibco.xpd.om.modeler.diagram.view.factories.PositionViewFactory;
import com.tibco.xpd.om.modeler.diagram.view.factories.custom.GroupItemViewFactory;
import com.tibco.xpd.om.modeler.diagram.view.factories.custom.LabelAuthorBadgeViewFactory;
import com.tibco.xpd.om.modeler.diagram.view.factories.custom.LabelDateCreatedBadgeViewFactory;
import com.tibco.xpd.om.modeler.diagram.view.factories.custom.LabelDateModifiedBadgeViewFactory;
import com.tibco.xpd.om.modeler.diagram.view.factories.custom.LabelOrgModelGroupsViewFactory;
import com.tibco.xpd.om.modeler.diagram.view.factories.custom.LabelVersionBadgeViewFactory;
import com.tibco.xpd.om.modeler.diagram.view.factories.custom.OrgModelAuthorBadgeViewFactory;
import com.tibco.xpd.om.modeler.diagram.view.factories.custom.OrgModelBadgeViewFactory;
import com.tibco.xpd.om.modeler.diagram.view.factories.custom.OrgModelDateCreatedBadgeViewFactory;
import com.tibco.xpd.om.modeler.diagram.view.factories.custom.OrgModelGroupsCompartmentViewFactory;
import com.tibco.xpd.om.modeler.diagram.view.factories.custom.OrgModelGroupsViewFactory;
import com.tibco.xpd.om.modeler.diagram.view.factories.custom.OrgModelNameBadgeViewFactory;
import com.tibco.xpd.om.modeler.diagram.view.factories.custom.OrgModelVersionBadgeViewFactory;

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
		if (OrgModelEditPart.MODEL_ID.equals(diagramKind)
				&& OrganizationModelVisualIDRegistry
						.getDiagramVisualID(semanticElement) != -1) {
			return OrgModelViewFactory.class;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.gmf.runtime.diagram.core.providers.AbstractViewProvider#
	 * getNodeViewClass(org.eclipse.core.runtime.IAdaptable,
	 * org.eclipse.gmf.runtime.notation.View, java.lang.String)
	 */
	@Override
	protected Class getNodeViewClass(IAdaptable semanticAdapter,
			View containerView, String semanticHint) {
		if (containerView == null) {
			return null;
		}

		if (semanticHint != null
				&& semanticHint.equals(OrgModelBadgeEditPart.VISUAL_ID)) {
			return OrgModelBadgeViewFactory.class;
		}

		if (semanticHint != null
				&& semanticHint.equals(OrgModelAuthorBadgeEditPart.VISUAL_ID)) {
			return OrgModelAuthorBadgeViewFactory.class;
		}

		if (semanticHint != null
				&& semanticHint.equals(OrgModelNameBadgeEditPart.VISUAL_ID)) {
			return OrgModelNameBadgeViewFactory.class;
		}

		if (semanticHint != null
				&& semanticHint
						.equals(OrgModelDateCreatedBadgeEditPart.VISUAL_ID)) {
			return OrgModelDateCreatedBadgeViewFactory.class;
		}

		if (semanticHint != null
				&& semanticHint
						.equals(OrgModelDateModifiedBadgeEditPart.VISUAL_ID)) {
			return OrgModelDateCreatedBadgeViewFactory.class;
		}

		if (semanticHint != null
				&& semanticHint.equals(LabelAuthorBadgeEditPart.VISUAL_ID)) {
			return LabelAuthorBadgeViewFactory.class;
		}

		if (semanticHint != null
				&& semanticHint.equals(LabelDateCreatedBadgeEditPart.VISUAL_ID)) {
			return LabelDateCreatedBadgeViewFactory.class;
		}

		if (semanticHint != null
				&& semanticHint
						.equals(LabelDateModifiedBadgeEditPart.VISUAL_ID)) {
			return LabelDateModifiedBadgeViewFactory.class;
		}

		if (semanticHint != null
				&& semanticHint.equals(OrgModelVersionBadgeEditPart.VISUAL_ID)) {
			return OrgModelVersionBadgeViewFactory.class;
		}

		if (semanticHint != null
				&& semanticHint.equals(LabelVersionBadgeEditPart.VISUAL_ID)) {
			return LabelVersionBadgeViewFactory.class;
		}

		return getNodeViewClass2(semanticAdapter, containerView, semanticHint);
	}

	/*
	 * Unfortunately, most of the generated method needs to be duplicated since
	 * it provides a good way of determining whether the CreateViewRequest
	 * originates from another diagram.
	 * 
	 * TODO: Eventually, the badge views dealt with in getNodeViewClass() should
	 * be moved into this method and then this method can supersede the current
	 * getNodeViewClass()
	 */
	protected Class getNodeViewClass2(IAdaptable semanticAdapter,
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
			visualID = OrganizationModelVisualIDRegistry.getNodeVisualID(
					containerView, domainElement);
		} else {
			visualID = OrganizationModelVisualIDRegistry
					.getVisualID(semanticHint);
			if (elementType != null) {
				// Semantic hint is specified together with element type.
				// Both parameters should describe exactly the same diagram
				// element.
				// In addition we check that visualID returned by
				// VisualIDRegistry.getNodeVisualID() for
				// domainElement (if specified) is the same as in element type.
				if (!OrganizationModelElementTypes
						.isKnownElementType(elementType)
						|| (!(elementType instanceof IHintedType))) {
					return null; // foreign element type
				}
				String elementTypeHint = ((IHintedType) elementType)
						.getSemanticHint();
				if (!semanticHint.equals(elementTypeHint)) {
					return null; // if semantic hint is specified it should be
					// the same as in element type
				}
				if (domainElement != null
						&& visualID != OrganizationModelVisualIDRegistry
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
				if (!OrgModelEditPart.MODEL_ID
						.equals(OrganizationModelVisualIDRegistry
								.getModelID(containerView))) {
					return null; // foreign diagram
				}
				switch (visualID) {
				case OrganizationEditPart.VISUAL_ID:
				case OrgUnitEditPart.VISUAL_ID:
				case DynamicOrgUnitEditPart.VISUAL_ID:
				case PositionEditPart.VISUAL_ID:

					if (domainElement == null
							|| visualID != OrganizationModelVisualIDRegistry
									.getNodeVisualID(containerView,
											domainElement)) {
						return null; // visual id in semantic hint should match
						// visual id for domain element
					}
					break;
				case OrganizationDisplayNameEditPart.VISUAL_ID:
				case OrganizationTypeNameEditPart.VISUAL_ID:
				case OrganizationOrgUnitCompartmentEditPart.VISUAL_ID:
					if (OrganizationEditPart.VISUAL_ID != OrganizationModelVisualIDRegistry
							.getVisualID(containerView)
							|| containerView.getElement() != domainElement) {
						return null; // wrong container
					}
					break;

				case OrgUnitDisplayNameEditPart.VISUAL_ID:
				case OrgUnitFeatureNameEditPart.VISUAL_ID:
				case OrgUnitPositionCompartmentEditPart.VISUAL_ID:
					if (OrgUnitEditPart.VISUAL_ID != OrganizationModelVisualIDRegistry
							.getVisualID(containerView)
							|| containerView.getElement() != domainElement) {
						return null; // wrong container
					}
					break;

				case DynamicOrgUnitDisplayNameEditPart.VISUAL_ID:
					if (DynamicOrgUnitEditPart.VISUAL_ID != OrganizationModelVisualIDRegistry
							.getVisualID(containerView)
							|| containerView.getElement() != domainElement) {
						return null; // wrong container
					}
					break;

				case OrgUnitRelationshipDisplayNameEditPart.VISUAL_ID:
					if (OrgUnitRelationshipEditPart.VISUAL_ID != OrganizationModelVisualIDRegistry
							.getVisualID(containerView)
							|| containerView.getElement() != domainElement) {
						return null; // wrong container
					}
					break;
				case OrgModelGroupsEditPart.VISUAL_ID:
				case LabelOrgModelGroupsEditPart.VISUAL_ID:
				case OrgModelGroupsCompartmentEditPart.VISUAL_ID:
					return (getNodeViewClass(containerView, visualID));
				default:
					return null;
				}
			}
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
				if (!OrgModelEditPart.MODEL_ID
						.equals(OrganizationModelVisualIDRegistry
								.getModelID(containerView))) {
					return null; // foreign diagram
				}
				switch (visualID) {
				case OrganizationEditPart.VISUAL_ID:
				case OrgUnitEditPart.VISUAL_ID:
				case PositionEditPart.VISUAL_ID:
				case DynamicOrgUnitEditPart.VISUAL_ID:
					if (domainElement == null
							|| visualID != OrganizationModelVisualIDRegistry
									.getNodeVisualID(containerView,
											domainElement)) {
						return null; // visual id in semantic hint should match visual id for domain element
					}
					break;
				case OrganizationDisplayNameEditPart.VISUAL_ID:
				case OrganizationTypeNameEditPart.VISUAL_ID:
				case OrganizationOrgUnitCompartmentEditPart.VISUAL_ID:
					if (OrganizationEditPart.VISUAL_ID != OrganizationModelVisualIDRegistry
							.getVisualID(containerView)
							|| containerView.getElement() != domainElement) {
						return null; // wrong container
					}
					break;
				case OrgUnitDisplayNameEditPart.VISUAL_ID:
				case OrgUnitFeatureNameEditPart.VISUAL_ID:
				case OrgUnitPositionCompartmentEditPart.VISUAL_ID:
					if (OrgUnitEditPart.VISUAL_ID != OrganizationModelVisualIDRegistry
							.getVisualID(containerView)
							|| containerView.getElement() != domainElement) {
						return null; // wrong container
					}
					break;
				case DynamicOrgUnitDisplayNameEditPart.VISUAL_ID:
					if (DynamicOrgUnitEditPart.VISUAL_ID != OrganizationModelVisualIDRegistry
							.getVisualID(containerView)
							|| containerView.getElement() != domainElement) {
						return null; // wrong container
					}
					break;
				case OrgUnitRelationshipDisplayNameEditPart.VISUAL_ID:
					if (OrgUnitRelationshipEditPart.VISUAL_ID != OrganizationModelVisualIDRegistry
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
	 * 
	 * Customized from generated code.
	 * 
	 * @param containerView
	 * @param visualID
	 * @return
	 */
	protected Class getNodeViewClass(View containerView, int visualID) {
		if (containerView == null
				|| !OrganizationModelVisualIDRegistry.canCreateNode(
						containerView, visualID)) {
			return null;
		}
		switch (visualID) {
		case OrgModelGroupsEditPart.VISUAL_ID:
			return OrgModelGroupsViewFactory.class;
		case LabelOrgModelGroupsEditPart.VISUAL_ID:
			return LabelOrgModelGroupsViewFactory.class;
		case OrgModelGroupsCompartmentEditPart.VISUAL_ID:
			return OrgModelGroupsCompartmentViewFactory.class;
		case GroupItemEditPart.VISUAL_ID:
			return GroupItemViewFactory.class;
		}
		return getNodeViewClassGen(containerView, visualID);
	}

	/**
	 * @generated
	 */
	protected Class getNodeViewClassGen(View containerView, int visualID) {
		if (containerView == null
				|| !OrganizationModelVisualIDRegistry.canCreateNode(
						containerView, visualID)) {
			return null;
		}
		switch (visualID) {
		case OrganizationEditPart.VISUAL_ID:
			return OrganizationViewFactory.class;
		case OrganizationDisplayNameEditPart.VISUAL_ID:
			return OrganizationDisplayNameViewFactory.class;
		case OrganizationTypeNameEditPart.VISUAL_ID:
			return OrganizationTypeNameViewFactory.class;
		case OrgUnitEditPart.VISUAL_ID:
			return OrgUnitViewFactory.class;
		case OrgUnitDisplayNameEditPart.VISUAL_ID:
			return OrgUnitDisplayNameViewFactory.class;
		case OrgUnitFeatureNameEditPart.VISUAL_ID:
			return OrgUnitFeatureNameViewFactory.class;
		case PositionEditPart.VISUAL_ID:
			return PositionViewFactory.class;
		case DynamicOrgUnitEditPart.VISUAL_ID:
			return DynamicOrgUnitViewFactory.class;
		case DynamicOrgUnitDisplayNameEditPart.VISUAL_ID:
			return DynamicOrgUnitDisplayNameViewFactory.class;
		case OrganizationOrgUnitCompartmentEditPart.VISUAL_ID:
			return OrganizationOrgUnitCompartmentViewFactory.class;
		case OrgUnitPositionCompartmentEditPart.VISUAL_ID:
			return OrgUnitPositionCompartmentViewFactory.class;
		case OrgUnitRelationshipDisplayNameEditPart.VISUAL_ID:
			return OrgUnitRelationshipDisplayNameViewFactory.class;
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
		case OrgUnitRelationshipEditPart.VISUAL_ID:
			return OrgUnitRelationshipViewFactory.class;
		case DynamicOrgReferenceEditPart.VISUAL_ID:
			return DynamicOrgReferenceViewFactory.class;
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
