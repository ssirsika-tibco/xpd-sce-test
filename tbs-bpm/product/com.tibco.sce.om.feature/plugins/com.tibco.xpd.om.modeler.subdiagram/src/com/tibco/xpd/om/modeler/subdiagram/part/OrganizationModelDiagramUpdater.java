package com.tibco.xpd.om.modeler.subdiagram.part;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.notation.View;

import com.tibco.xpd.om.core.om.DynamicOrgUnit;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.OrgUnitRelationship;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.Position;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.DynamicOrgUnitEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.OrgUnitPositionSubCompartmentEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.OrgUnitSubEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.OrgUnitSubRelationshipEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.OrganizationSubEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.PositionSubEditPart;
import com.tibco.xpd.om.modeler.subdiagram.providers.OrganizationModelElementTypes;

/**
 * @generated
 */
public class OrganizationModelDiagramUpdater {

	/**
	 * @generated
	 */
	public static List getSemanticChildren(View view) {
		switch (OrganizationModelVisualIDRegistry.getVisualID(view)) {
		case OrgUnitPositionSubCompartmentEditPart.VISUAL_ID:
			return getOrgUnitPositionCompartment_5001SemanticChildren(view);
		case OrganizationSubEditPart.VISUAL_ID:
			return getOrganization_79SemanticChildren(view);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getOrgUnitPositionCompartment_5001SemanticChildren(
			View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.EMPTY_LIST;
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		OrgUnit modelElement = (OrgUnit) containerView.getElement();
		List result = new LinkedList();
		for (Iterator it = modelElement.getPositions().iterator(); it.hasNext();) {
			Position childElement = (Position) it.next();
			int visualID = OrganizationModelVisualIDRegistry.getNodeVisualID(
					view, childElement);
			if (visualID == PositionSubEditPart.VISUAL_ID) {
				result.add(new OrganizationModelNodeDescriptor(childElement,
						visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List getOrganization_79SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		Organization modelElement = (Organization) view.getElement();
		List result = new LinkedList();
		for (Iterator it = modelElement.getUnits().iterator(); it.hasNext();) {
			OrgUnit childElement = (OrgUnit) it.next();
			int visualID = OrganizationModelVisualIDRegistry.getNodeVisualID(
					view, childElement);
			if (visualID == OrgUnitSubEditPart.VISUAL_ID) {
				result.add(new OrganizationModelNodeDescriptor(childElement,
						visualID));
				continue;
			}
			if (visualID == DynamicOrgUnitEditPart.VISUAL_ID) {
				result.add(new OrganizationModelNodeDescriptor(childElement,
						visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List getContainedLinks(View view) {
		switch (OrganizationModelVisualIDRegistry.getVisualID(view)) {
		case OrganizationSubEditPart.VISUAL_ID:
			return getOrganization_79ContainedLinks(view);
		case OrgUnitSubEditPart.VISUAL_ID:
			return getOrgUnit_1001ContainedLinks(view);
		case DynamicOrgUnitEditPart.VISUAL_ID:
			return getDynamicOrgUnit_1002ContainedLinks(view);
		case PositionSubEditPart.VISUAL_ID:
			return getPosition_2001ContainedLinks(view);
		case OrgUnitSubRelationshipEditPart.VISUAL_ID:
			return getOrgUnitRelationship_3001ContainedLinks(view);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getIncomingLinks(View view) {
		switch (OrganizationModelVisualIDRegistry.getVisualID(view)) {
		case OrgUnitSubEditPart.VISUAL_ID:
			return getOrgUnit_1001IncomingLinks(view);
		case DynamicOrgUnitEditPart.VISUAL_ID:
			return getDynamicOrgUnit_1002IncomingLinks(view);
		case PositionSubEditPart.VISUAL_ID:
			return getPosition_2001IncomingLinks(view);
		case OrgUnitSubRelationshipEditPart.VISUAL_ID:
			return getOrgUnitRelationship_3001IncomingLinks(view);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getOutgoingLinks(View view) {
		switch (OrganizationModelVisualIDRegistry.getVisualID(view)) {
		case OrgUnitSubEditPart.VISUAL_ID:
			return getOrgUnit_1001OutgoingLinks(view);
		case DynamicOrgUnitEditPart.VISUAL_ID:
			return getDynamicOrgUnit_1002OutgoingLinks(view);
		case PositionSubEditPart.VISUAL_ID:
			return getPosition_2001OutgoingLinks(view);
		case OrgUnitSubRelationshipEditPart.VISUAL_ID:
			return getOrgUnitRelationship_3001OutgoingLinks(view);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getOrganization_79ContainedLinks(View view) {
		Organization modelElement = (Organization) view.getElement();
		List result = new LinkedList();
		result
				.addAll(getContainedTypeModelFacetLinks_OrgUnitRelationship_3001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getOrgUnit_1001ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getDynamicOrgUnit_1002ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getPosition_2001ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getOrgUnitRelationship_3001ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getOrgUnit_1001IncomingLinks(View view) {
		OrgUnit modelElement = (OrgUnit) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource()
				.getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_OrgUnitRelationship_3001(
				modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getDynamicOrgUnit_1002IncomingLinks(View view) {
		DynamicOrgUnit modelElement = (DynamicOrgUnit) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource()
				.getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_OrgUnitRelationship_3001(
				modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getPosition_2001IncomingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getOrgUnitRelationship_3001IncomingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getOrgUnit_1001OutgoingLinks(View view) {
		OrgUnit modelElement = (OrgUnit) view.getElement();
		List result = new LinkedList();
		result
				.addAll(getOutgoingTypeModelFacetLinks_OrgUnitRelationship_3001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getDynamicOrgUnit_1002OutgoingLinks(View view) {
		DynamicOrgUnit modelElement = (DynamicOrgUnit) view.getElement();
		List result = new LinkedList();
		result
				.addAll(getOutgoingTypeModelFacetLinks_OrgUnitRelationship_3001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getPosition_2001OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getOrgUnitRelationship_3001OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	private static Collection getContainedTypeModelFacetLinks_OrgUnitRelationship_3001(
			Organization container) {
		Collection result = new LinkedList();
		for (Iterator links = container.getOrgUnitRelationships().iterator(); links
				.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof OrgUnitRelationship) {
				continue;
			}
			OrgUnitRelationship link = (OrgUnitRelationship) linkObject;
			if (OrgUnitSubRelationshipEditPart.VISUAL_ID != OrganizationModelVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			OrgUnit dst = link.getTo();
			OrgUnit src = link.getFrom();
			result.add(new OrganizationModelLinkDescriptor(src, dst, link,
					OrganizationModelElementTypes.OrgUnitRelationship_3001,
					OrgUnitSubRelationshipEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection getIncomingTypeModelFacetLinks_OrgUnitRelationship_3001(
			OrgUnit target, Map crossReferences) {
		Collection result = new LinkedList();
		Collection settings = (Collection) crossReferences.get(target);
		for (Iterator it = settings.iterator(); it.hasNext();) {
			EStructuralFeature.Setting setting = (EStructuralFeature.Setting) it
					.next();
			if (setting.getEStructuralFeature() != OMPackage.eINSTANCE
					.getOrgUnitRelationship_To()
					|| false == setting.getEObject() instanceof OrgUnitRelationship) {
				continue;
			}
			OrgUnitRelationship link = (OrgUnitRelationship) setting
					.getEObject();
			if (OrgUnitSubRelationshipEditPart.VISUAL_ID != OrganizationModelVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			OrgUnit src = link.getFrom();
			result.add(new OrganizationModelLinkDescriptor(src, target, link,
					OrganizationModelElementTypes.OrgUnitRelationship_3001,
					OrgUnitSubRelationshipEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection getOutgoingTypeModelFacetLinks_OrgUnitRelationship_3001(
			OrgUnit source) {
		Organization container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element
				.eContainer()) {
			if (element instanceof Organization) {
				container = (Organization) element;
			}
		}
		if (container == null) {
			return Collections.EMPTY_LIST;
		}
		Collection result = new LinkedList();
		for (Iterator links = container.getOrgUnitRelationships().iterator(); links
				.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof OrgUnitRelationship) {
				continue;
			}
			OrgUnitRelationship link = (OrgUnitRelationship) linkObject;
			if (OrgUnitSubRelationshipEditPart.VISUAL_ID != OrganizationModelVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			OrgUnit dst = link.getTo();
			OrgUnit src = link.getFrom();
			if (src != source) {
				continue;
			}
			result.add(new OrganizationModelLinkDescriptor(src, dst, link,
					OrganizationModelElementTypes.OrgUnitRelationship_3001,
					OrgUnitSubRelationshipEditPart.VISUAL_ID));
		}
		return result;
	}

}
