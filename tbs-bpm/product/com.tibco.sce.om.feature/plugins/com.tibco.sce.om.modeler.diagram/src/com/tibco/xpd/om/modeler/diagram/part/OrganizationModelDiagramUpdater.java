package com.tibco.xpd.om.modeler.diagram.part;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.notation.View;

import com.tibco.xpd.om.core.om.DynamicOrgReference;
import com.tibco.xpd.om.core.om.DynamicOrgUnit;
import com.tibco.xpd.om.core.om.Group;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.OrgUnitRelationship;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.Position;
import com.tibco.xpd.om.modeler.diagram.edit.parts.DynamicOrgReferenceEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.DynamicOrgUnitEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrgModelEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrgUnitEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrgUnitPositionCompartmentEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrgUnitRelationshipEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrganizationEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrganizationOrgUnitCompartmentEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.PositionEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.GroupItemEditPart;
import com.tibco.xpd.om.modeler.diagram.providers.OrganizationModelElementTypes;

/**
 * @generated
 */
public class OrganizationModelDiagramUpdater {

	/**
	 * @generated
	 */
	public static List getSemanticChildren(View view) {
		switch (OrganizationModelVisualIDRegistry.getVisualID(view)) {
		case OrganizationOrgUnitCompartmentEditPart.VISUAL_ID:
			return getOrganizationOrgUnitCompartment_5001SemanticChildren(view);
		case OrgUnitPositionCompartmentEditPart.VISUAL_ID:
			return getOrgUnitPositionCompartment_5002SemanticChildren(view);
		case OrgModelEditPart.VISUAL_ID:
			return getOrgModel_79SemanticChildren(view);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getOrganizationOrgUnitCompartment_5001SemanticChildren(
			View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.EMPTY_LIST;
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		Organization modelElement = (Organization) containerView.getElement();
		List result = new LinkedList();
		for (Iterator it = modelElement.getUnits().iterator(); it.hasNext();) {
			OrgUnit childElement = (OrgUnit) it.next();
			int visualID = OrganizationModelVisualIDRegistry.getNodeVisualID(
					view, childElement);
			if (visualID == OrgUnitEditPart.VISUAL_ID) {
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
	 * @param view
	 * @return
	 */
	public static List getOrgModelGroupCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.EMPTY_LIST;
		}
		View containerView = (View) view.eContainer();

		// The following lines are commented out (they were inherited from the
		// copied generated code). We differ from the default expectation in
		// this case. Our container editpart is the SAME semantic element as the
		// canvas and for some reason this meant that the setElement() method
		// wasn't called during initialization in createView(). Debug this code
		// to confirm why.

		// if (!containerView.isSetElement()) {
		// return Collections.EMPTY_LIST;
		// }

		if (containerView.getElement() == null) {
			return Collections.EMPTY_LIST;
		}

		OrgModel modelElement = (OrgModel) containerView.getElement();

		List result = new LinkedList();

		for (Iterator it = modelElement.getGroups().iterator(); it.hasNext();) {
			Group childElement = (Group) it.next();
			int visualID = OrganizationModelVisualIDRegistry.getNodeVisualID(
					view, childElement);
			if (visualID == GroupItemEditPart.VISUAL_ID) {
				result.add(new OrganizationModelNodeDescriptor(childElement,
						visualID));
				collectSubGroups(result, childElement);

				continue;
			}
		}
		return result;
	}

	private static List collectSubGroups(List grps, Group group) {

		EList<Group> subGroups = group.getSubGroups();

		for (Group subGrp : subGroups) {
			grps.add(new OrganizationModelNodeDescriptor(subGrp,
					GroupItemEditPart.VISUAL_ID));
			collectSubGroups(grps, subGrp);
		}

		return grps;
	}

	/**
	 * @generated
	 */
	public static List getOrgUnitPositionCompartment_5002SemanticChildren(
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
			if (visualID == PositionEditPart.VISUAL_ID) {
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
	public static List getOrgModel_79SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		OrgModel modelElement = (OrgModel) view.getElement();
		List result = new LinkedList();
		for (Iterator it = modelElement.getOrganizations().iterator(); it
				.hasNext();) {
			Organization childElement = (Organization) it.next();
			int visualID = OrganizationModelVisualIDRegistry.getNodeVisualID(
					view, childElement);
			if (visualID == OrganizationEditPart.VISUAL_ID) {
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
		case OrgModelEditPart.VISUAL_ID:
			return getOrgModel_79ContainedLinks(view);
		case OrganizationEditPart.VISUAL_ID:
			return getOrganization_1001ContainedLinks(view);
		case OrgUnitEditPart.VISUAL_ID:
			return getOrgUnit_2001ContainedLinks(view);
		case PositionEditPart.VISUAL_ID:
			return getPosition_2002ContainedLinks(view);
		case DynamicOrgUnitEditPart.VISUAL_ID:
			return getDynamicOrgUnit_2003ContainedLinks(view);
		case OrgUnitRelationshipEditPart.VISUAL_ID:
			return getOrgUnitRelationship_3001ContainedLinks(view);
		case DynamicOrgReferenceEditPart.VISUAL_ID:
			return getDynamicOrgReference_3002ContainedLinks(view);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getIncomingLinks(View view) {
		switch (OrganizationModelVisualIDRegistry.getVisualID(view)) {
		case OrganizationEditPart.VISUAL_ID:
			return getOrganization_1001IncomingLinks(view);
		case OrgUnitEditPart.VISUAL_ID:
			return getOrgUnit_2001IncomingLinks(view);
		case PositionEditPart.VISUAL_ID:
			return getPosition_2002IncomingLinks(view);
		case DynamicOrgUnitEditPart.VISUAL_ID:
			return getDynamicOrgUnit_2003IncomingLinks(view);
		case OrgUnitRelationshipEditPart.VISUAL_ID:
			return getOrgUnitRelationship_3001IncomingLinks(view);
		case DynamicOrgReferenceEditPart.VISUAL_ID:
			return getDynamicOrgReference_3002IncomingLinks(view);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getOutgoingLinks(View view) {
		switch (OrganizationModelVisualIDRegistry.getVisualID(view)) {
		case OrganizationEditPart.VISUAL_ID:
			return getOrganization_1001OutgoingLinks(view);
		case OrgUnitEditPart.VISUAL_ID:
			return getOrgUnit_2001OutgoingLinks(view);
		case PositionEditPart.VISUAL_ID:
			return getPosition_2002OutgoingLinks(view);
		case DynamicOrgUnitEditPart.VISUAL_ID:
			return getDynamicOrgUnit_2003OutgoingLinks(view);
		case OrgUnitRelationshipEditPart.VISUAL_ID:
			return getOrgUnitRelationship_3001OutgoingLinks(view);
		case DynamicOrgReferenceEditPart.VISUAL_ID:
			return getDynamicOrgReference_3002OutgoingLinks(view);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getOrgModel_79ContainedLinks(View view) {
		OrgModel modelElement = (OrgModel) view.getElement();
		List result = new LinkedList();
		result
				.addAll(getContainedTypeModelFacetLinks_DynamicOrgReference_3002(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getOrganization_1001ContainedLinks(View view) {
		Organization modelElement = (Organization) view.getElement();
		List result = new LinkedList();
		result
				.addAll(getContainedTypeModelFacetLinks_OrgUnitRelationship_3001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getOrgUnit_2001ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getPosition_2002ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getDynamicOrgUnit_2003ContainedLinks(View view) {
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
	public static List getDynamicOrgReference_3002ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getOrganization_1001IncomingLinks(View view) {
		Organization modelElement = (Organization) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource()
				.getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_DynamicOrgReference_3002(
				modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getOrgUnit_2001IncomingLinks(View view) {
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
	public static List getPosition_2002IncomingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getDynamicOrgUnit_2003IncomingLinks(View view) {
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
	public static List getOrgUnitRelationship_3001IncomingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getDynamicOrgReference_3002IncomingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getOrganization_1001OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getOrgUnit_2001OutgoingLinks(View view) {
		OrgUnit modelElement = (OrgUnit) view.getElement();
		List result = new LinkedList();
		result
				.addAll(getOutgoingTypeModelFacetLinks_OrgUnitRelationship_3001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getPosition_2002OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getDynamicOrgUnit_2003OutgoingLinks(View view) {
		DynamicOrgUnit modelElement = (DynamicOrgUnit) view.getElement();
		List result = new LinkedList();
		result
				.addAll(getOutgoingTypeModelFacetLinks_OrgUnitRelationship_3001(modelElement));
		result
				.addAll(getOutgoingTypeModelFacetLinks_DynamicOrgReference_3002(modelElement));
		return result;
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
	public static List getDynamicOrgReference_3002OutgoingLinks(View view) {
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
			if (OrgUnitRelationshipEditPart.VISUAL_ID != OrganizationModelVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			OrgUnit dst = link.getTo();
			OrgUnit src = link.getFrom();
			result.add(new OrganizationModelLinkDescriptor(src, dst, link,
					OrganizationModelElementTypes.OrgUnitRelationship_3001,
					OrgUnitRelationshipEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection getContainedTypeModelFacetLinks_DynamicOrgReference_3002(
			OrgModel container) {
		Collection result = new LinkedList();
		for (Iterator links = container.getDynamicOrgReferences().iterator(); links
				.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof DynamicOrgReference) {
				continue;
			}
			DynamicOrgReference link = (DynamicOrgReference) linkObject;
			if (DynamicOrgReferenceEditPart.VISUAL_ID != OrganizationModelVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			Organization dst = link.getTo();
			DynamicOrgUnit src = link.getFrom();
			result.add(new OrganizationModelLinkDescriptor(src, dst, link,
					OrganizationModelElementTypes.DynamicOrgReference_3002,
					DynamicOrgReferenceEditPart.VISUAL_ID));
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
			if (OrgUnitRelationshipEditPart.VISUAL_ID != OrganizationModelVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			OrgUnit src = link.getFrom();
			result.add(new OrganizationModelLinkDescriptor(src, target, link,
					OrganizationModelElementTypes.OrgUnitRelationship_3001,
					OrgUnitRelationshipEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection getIncomingTypeModelFacetLinks_DynamicOrgReference_3002(
			Organization target, Map crossReferences) {
		Collection result = new LinkedList();
		Collection settings = (Collection) crossReferences.get(target);
		for (Iterator it = settings.iterator(); it.hasNext();) {
			EStructuralFeature.Setting setting = (EStructuralFeature.Setting) it
					.next();
			if (setting.getEStructuralFeature() != OMPackage.eINSTANCE
					.getDynamicOrgReference_To()
					|| false == setting.getEObject() instanceof DynamicOrgReference) {
				continue;
			}
			DynamicOrgReference link = (DynamicOrgReference) setting
					.getEObject();
			if (DynamicOrgReferenceEditPart.VISUAL_ID != OrganizationModelVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			DynamicOrgUnit src = link.getFrom();
			result.add(new OrganizationModelLinkDescriptor(src, target, link,
					OrganizationModelElementTypes.DynamicOrgReference_3002,
					DynamicOrgReferenceEditPart.VISUAL_ID));
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
			if (OrgUnitRelationshipEditPart.VISUAL_ID != OrganizationModelVisualIDRegistry
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
					OrgUnitRelationshipEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection getOutgoingTypeModelFacetLinks_DynamicOrgReference_3002(
			DynamicOrgUnit source) {
		OrgModel container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element
				.eContainer()) {
			if (element instanceof OrgModel) {
				container = (OrgModel) element;
			}
		}
		if (container == null) {
			return Collections.EMPTY_LIST;
		}
		Collection result = new LinkedList();
		for (Iterator links = container.getDynamicOrgReferences().iterator(); links
				.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof DynamicOrgReference) {
				continue;
			}
			DynamicOrgReference link = (DynamicOrgReference) linkObject;
			if (DynamicOrgReferenceEditPart.VISUAL_ID != OrganizationModelVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			Organization dst = link.getTo();
			DynamicOrgUnit src = link.getFrom();
			if (src != source) {
				continue;
			}
			result.add(new OrganizationModelLinkDescriptor(src, dst, link,
					OrganizationModelElementTypes.DynamicOrgReference_3002,
					DynamicOrgReferenceEditPart.VISUAL_ID));
		}
		return result;
	}

}
