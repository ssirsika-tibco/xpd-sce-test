package com.tibco.xpd.om.modeler.subdiagram.edit.policies;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.core.listener.NotificationUtil;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy;
import org.eclipse.gmf.runtime.notation.View;

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.PositionSubEditPart;
import com.tibco.xpd.om.modeler.subdiagram.part.OrganizationModelDiagramUpdater;
import com.tibco.xpd.om.modeler.subdiagram.part.OrganizationModelNodeDescriptor;
import com.tibco.xpd.om.modeler.subdiagram.part.OrganizationModelVisualIDRegistry;

/**
 * @generated
 */
public class OrgUnitPositionSubCompartmentCanonicalEditPolicy extends
		CanonicalEditPolicy {

	/**
	 * @generated
	 */
	Set myFeaturesToSynchronize;

	/**
	 * @generated
	 */
	protected List getSemanticChildrenList() {
		View viewObject = (View) getHost().getModel();
		List result = new LinkedList();
		for (Iterator it = OrganizationModelDiagramUpdater
				.getOrgUnitPositionCompartment_5001SemanticChildren(viewObject)
				.iterator(); it.hasNext();) {
			result.add(((OrganizationModelNodeDescriptor) it.next())
					.getModelElement());
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected boolean isOrphaned(Collection semanticChildren, final View view) {
		int visualID = OrganizationModelVisualIDRegistry.getVisualID(view);
		switch (visualID) {
		case PositionSubEditPart.VISUAL_ID:
			if (!semanticChildren.contains(view.getElement())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @generated
	 */
	protected String getDefaultFactoryHint() {
		return null;
	}

	/**
	 * @generated
	 */
	protected Set getFeaturesToSynchronize() {
		if (myFeaturesToSynchronize == null) {
			myFeaturesToSynchronize = new HashSet();
			myFeaturesToSynchronize.add(OMPackage.eINSTANCE
					.getOrgUnit_Positions());
		}
		return myFeaturesToSynchronize;
	}

	/**
	 * In addition, refresh order of attributes, it have to be in synch with the
	 * model.
	 */
	@Override
	protected void refreshSemantic() {
		super.refreshSemantic();
		refreshOrder();
	}

	/**
	 * Listen to change order events as well.
	 */
	@Override
	protected boolean shouldHandleNotificationEvent(Notification event) {
		boolean fromsuper = super.shouldHandleNotificationEvent(event);

		// should handle move as well
		if (!fromsuper) {
			Object element = event.getNotifier();
			if (element instanceof EObject && !(element instanceof View)) {
				Set features = getFeaturesToSynchronize();
				if (features != null && !features.isEmpty()) {
					if (features.contains(event.getFeature())
							&& (NotificationUtil.isMove(event))) {
						return true;
					}
				}
			}
		}
		return fromsuper;
	}

	/**
	 * Synch order of the notation elements with the semantic elements.
	 */
	private void refreshOrder() {
		List<?> children = getSemanticChildrenList();
		View parent = ((View) host().getModel());
		@SuppressWarnings("unchecked")
		EList<View> viewChildren = parent.getChildren();

		out: for (int i = 0; i < children.size(); i++) {
			Object model = children.get(i);

			for (int j = i; j < viewChildren.size(); j++) {
				View view = viewChildren.get(j);
				if (view.getElement() == model) {
					if (i != j) {
						ViewUtil.repositionChildAt(parent, view, i);
						continue out;
					}
				}
			}
		}
	}

}
