package com.tibco.xpd.om.modeler.subdiagram.edit.parts;

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ListCompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DragDropEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ResizableCompartmentEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.figures.ResizableCompartmentFigure;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateUnspecifiedTypeRequest;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.DrawerStyle;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;

import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.Position;
import com.tibco.xpd.om.core.om.PositionFeature;
import com.tibco.xpd.om.modeler.subdiagram.edit.policies.OrgUnitPositionSubCompartmentCanonicalEditPolicy;
import com.tibco.xpd.om.modeler.subdiagram.edit.policies.OrgUnitPositionSubCompartmentItemSemanticEditPolicy;
import com.tibco.xpd.om.modeler.subdiagram.edit.policies.custom.OMContainerEditPolicy;
import com.tibco.xpd.om.modeler.subdiagram.part.Messages;
import com.tibco.xpd.om.modeler.subdiagram.part.custom.IOrganizationModelDiagramConstants;
import com.tibco.xpd.om.modeler.subdiagram.providers.OrganizationModelElementTypes;

/**
 * @generated
 */
public class OrgUnitPositionSubCompartmentEditPart extends
		ListCompartmentEditPart {

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 5001;

	/**
	 * @generated
	 */
	public OrgUnitPositionSubCompartmentEditPart(View view) {
		super(view);
	}

	/**
	 * @generated
	 */
	protected boolean hasModelChildrenChanged(Notification evt) {
		return false;
	}

	/**
	 * @generated
	 */
	public String getCompartmentName() {
		return Messages.OrgUnitPositionSubCompartmentEditPart_title;
	}

	/**
	 * @generated NOT
	 */
	public IFigure createFigure() {
		ResizableCompartmentFigure result = (ResizableCompartmentFigure) super
				.createFigure();
		result.setTitle(Messages.OrgUnitPositionSubCompartmentEditPart_label);
		// result.setTitleVisibility(false);
		return result;
	}

	/**
	 * @generated NOT
	 */
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE,
				new ResizableCompartmentEditPolicy());
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
				new OrgUnitPositionSubCompartmentItemSemanticEditPolicy());
		installEditPolicy(EditPolicyRoles.CREATION_ROLE,
				new CreationEditPolicy());
		installEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE,
				new DragDropEditPolicy());
		installEditPolicy(EditPolicyRoles.CANONICAL_ROLE,
				new OrgUnitPositionSubCompartmentCanonicalEditPolicy());
		installEditPolicy(EditPolicy.CONTAINER_ROLE,
				new OMContainerEditPolicy());
	}

	@Override
	public Command getCommand(Request _request) {

		if (_request instanceof CreateUnspecifiedTypeRequest) {
			CreateUnspecifiedTypeRequest req = (CreateUnspecifiedTypeRequest) _request;
			List elementTypes = req.getElementTypes();

			for (Object object : elementTypes) {
				IElementType t = (IElementType) object;

				if (t == OrganizationModelElementTypes.Position_2001) {
					// If we are creating a typed position we need to check if
					// it is a valid type for this containing OrgUnit
					Object obj2 = req
							.getExtendedData()
							.get(
									IOrganizationModelDiagramConstants.OMCreationToolFeature);

					if (obj2 != null && obj2 instanceof PositionFeature) {
						PositionFeature posFeature = (PositionFeature) obj2;
						EObject elem = resolveSemanticElement();

						if (elem instanceof OrgUnit) {
							OrgUnit ou = (OrgUnit) elem;
							if (ou.getFeature() != null) {
								if (posFeature.eContainer() != ou.getFeature()
										.getFeatureType()) {
									return UnexecutableCommand.INSTANCE;
								}
							} else {
								return UnexecutableCommand.INSTANCE;
							}
						}
					}
				}

			}

		}

		return super.getCommand(_request);
	}

	/**
	 * @generated
	 */
	protected void setRatio(Double ratio) {
		// nothing to do -- parent layout does not accept Double constraints as ratio
		// super.setRatio(ratio); 
	}

	@Override
	protected void refreshVisuals() {
		// TODO Auto-generated method stub
		super.refreshVisuals();

		IFigure cptFigure = getFigure();

		if (cptFigure instanceof ResizableCompartmentFigure) {
			ResizableCompartmentFigure fig = (ResizableCompartmentFigure) cptFigure;

			if (fig.isExpanded()) {
				fig.setTitleVisibility(false);
			} else {
				fig.setTitleVisibility(true);
			}
		}

	}

	@Override
	protected void handleNotificationEvent(Notification event) {

		super.handleNotificationEvent(event);

		Object feature = event.getFeature();

		if ((feature == NotationPackage.Literals.DRAWER_STYLE__COLLAPSED)
				&& (event.getEventType() == Notification.SET)) {

			if (event.getNotifier() instanceof DrawerStyle) {
				DrawerStyle style = (DrawerStyle) event.getNotifier();
				EObject container = style.eContainer();

				if (container == getModel()) {
					IFigure cptFigure = getFigure();

					if (cptFigure instanceof ResizableCompartmentFigure) {
						ResizableCompartmentFigure fig = (ResizableCompartmentFigure) cptFigure;

						if (event.getNewBooleanValue() == true) {
							// Is being collapsed, so show title ONLY if there
							// are
							// children inside compartment
							// if (!getChildren().isEmpty()) {
							fig.setTitleVisibility(true);
							// }

						} else if (event.getNewBooleanValue() == false) {
							fig.setTitleVisibility(false);
						}
					}
				}
			}
		}

	}

}
