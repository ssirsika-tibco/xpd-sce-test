package com.tibco.xpd.om.modeler.diagram.edit.policies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.actions.ActionIds;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.requests.ArrangeRequest;
import org.eclipse.gmf.runtime.notation.View;

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.modeler.diagram.edit.parts.DynamicOrgUnitEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrgUnitEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrganizationOrgUnitCompartmentEditPart;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelDiagramUpdater;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelNodeDescriptor;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelVisualIDRegistry;

/**
 * @generated
 */
public class OrganizationOrgUnitCompartmentCanonicalEditPolicy extends
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
				.getOrganizationOrgUnitCompartment_5001SemanticChildren(
						viewObject).iterator(); it.hasNext();) {
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
		case OrgUnitEditPart.VISUAL_ID:
		case DynamicOrgUnitEditPart.VISUAL_ID:
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
					.getOrganization_Units());
		}
		return myFeaturesToSynchronize;
	}

	// /*
	// * (non-Javadoc)
	// *
	// *
	// * Overide superclass method so that we can perform an autoarrange after
	// * every refresh
	// *
	// *
	// @seeorg.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy#
	// * refreshSemantic()
	// */
	// public void refreshSemanticWithAutoArrange() {
	// List createdViews = refreshSemanticChildren();
	// makeViewsImmutable(createdViews);
	//
	// // We want to perform and autoarrange to keep the compartments diagram
	// // tidy
	// performAutoArrange();
	//
	// }

	public void performAutoArrange() {
		EditPart host = getHost();
		if (host != null
				&& host instanceof OrganizationOrgUnitCompartmentEditPart) {

			ArrangeRequest request = new ArrangeRequest(
					ActionIds.ACTION_ARRANGE_ALL, "DEFAULT"); //$NON-NLS-1$

			List<EditPart> editParts = new ArrayList<EditPart>();
			editParts.add(host);
			request.setPartsToArrange(editParts);
			Command cmd = host.getCommand(request);

			if (cmd != null) {
				if (cmd.canExecute()) {
					executeCommand(cmd);
				}
			}
		}

	}

}
