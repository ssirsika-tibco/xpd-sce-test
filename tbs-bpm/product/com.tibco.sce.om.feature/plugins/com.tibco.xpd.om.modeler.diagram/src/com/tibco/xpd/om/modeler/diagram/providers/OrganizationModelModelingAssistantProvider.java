package com.tibco.xpd.om.modeler.diagram.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.ModelingAssistantProvider;
import org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.SelectExistingElementForSourceOperation;
import org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.SelectExistingElementForTargetOperation;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

import com.tibco.xpd.om.modeler.diagram.edit.parts.DynamicOrgUnitEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrgModelEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrgUnitEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrganizationEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrganizationOrgUnitCompartmentEditPart;
import com.tibco.xpd.om.modeler.diagram.part.Messages;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelDiagramEditorPlugin;

/**
 * @generated
 */
public class OrganizationModelModelingAssistantProvider extends
		ModelingAssistantProvider {

	/**
	 * @generated NOT
	 */
	public List getTypesForPopupBar(IAdaptable host) {
		IGraphicalEditPart editPart = (IGraphicalEditPart) host
				.getAdapter(IGraphicalEditPart.class);
		// if (editPart instanceof OrgUnitEditPart) {
		// List types = new ArrayList();
		// types.add(OrganizationModelElementTypes.Position_2002);
		// return types;
		// }
		// if (editPart instanceof OrganizationOrgUnitCompartmentEditPart) {
		// List types = new ArrayList();
		// types.add(OrganizationModelElementTypes.OrgUnit_2001);
		// return types;
		// }
		if (editPart instanceof OrgModelEditPart) {
			List types = new ArrayList();
			types.add(OrganizationModelElementTypes.Organization_1001);
			return types;
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated NOT
	 */
	public List getRelTypesOnSource(IAdaptable source) {
		// IGraphicalEditPart sourceEditPart = (IGraphicalEditPart) source
		// .getAdapter(IGraphicalEditPart.class);
		// if (sourceEditPart instanceof OrgUnitEditPart) {
		// List types = new ArrayList();
		// types.add(OrganizationModelElementTypes.OrgUnitRelationship_3001);
		// return types;
		// }
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated NOT
	 */
	public List getRelTypesOnTarget(IAdaptable target) {
		// IGraphicalEditPart targetEditPart = (IGraphicalEditPart) target
		// .getAdapter(IGraphicalEditPart.class);
		// if (targetEditPart instanceof OrgUnitEditPart) {
		// List types = new ArrayList();
		// types.add(OrganizationModelElementTypes.OrgUnitRelationship_3001);
		// return types;
		// }
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated NOT
	 */
	public List getRelTypesOnSourceAndTarget(IAdaptable source,
			IAdaptable target) {
		// IGraphicalEditPart sourceEditPart = (IGraphicalEditPart) source
		// .getAdapter(IGraphicalEditPart.class);
		// IGraphicalEditPart targetEditPart = (IGraphicalEditPart) target
		// .getAdapter(IGraphicalEditPart.class);
		// if (sourceEditPart instanceof OrgUnitEditPart) {
		// List types = new ArrayList();
		// if (targetEditPart instanceof OrgUnitEditPart) {
		// types
		// .add(OrganizationModelElementTypes.OrgUnitRelationship_3001);
		// }
		// return types;
		// }
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated NOT
	 */
	public List getTypesForSource(IAdaptable target,
			IElementType relationshipType) {
		// IGraphicalEditPart targetEditPart = (IGraphicalEditPart) target
		// .getAdapter(IGraphicalEditPart.class);
		// if (targetEditPart instanceof OrgUnitEditPart) {
		// List types = new ArrayList();
		// if (relationshipType ==
		// OrganizationModelElementTypes.OrgUnitRelationship_3001) {
		// types.add(OrganizationModelElementTypes.OrgUnit_2001);
		// }
		// return types;
		// }
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated NOT
	 */
	public List getTypesForTarget(IAdaptable source,
			IElementType relationshipType) {
		// IGraphicalEditPart sourceEditPart = (IGraphicalEditPart) source
		// .getAdapter(IGraphicalEditPart.class);
		// if (sourceEditPart instanceof OrgUnitEditPart) {
		// List types = new ArrayList();
		// if (relationshipType ==
		// OrganizationModelElementTypes.OrgUnitRelationship_3001) {
		// types.add(OrganizationModelElementTypes.OrgUnit_2001);
		// }
		// return types;
		// }
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public EObject selectExistingElementForSource(IAdaptable target,
			IElementType relationshipType) {
		return selectExistingElement(target, getTypesForSource(target,
				relationshipType));
	}

	/**
	 * @generated
	 */
	public EObject selectExistingElementForTarget(IAdaptable source,
			IElementType relationshipType) {
		return selectExistingElement(source, getTypesForTarget(source,
				relationshipType));
	}

	/**
	 * @generated
	 */
	protected EObject selectExistingElement(IAdaptable host, Collection types) {
		if (types.isEmpty()) {
			return null;
		}
		IGraphicalEditPart editPart = (IGraphicalEditPart) host
				.getAdapter(IGraphicalEditPart.class);
		if (editPart == null) {
			return null;
		}
		Diagram diagram = (Diagram) editPart.getRoot().getContents().getModel();
		Collection elements = new HashSet();
		for (Iterator it = diagram.getElement().eAllContents(); it.hasNext();) {
			EObject element = (EObject) it.next();
			if (isApplicableElement(element, types)) {
				elements.add(element);
			}
		}
		if (elements.isEmpty()) {
			return null;
		}
		return selectElement((EObject[]) elements.toArray(new EObject[elements
				.size()]));
	}

	/**
	 * @generated
	 */
	protected boolean isApplicableElement(EObject element, Collection types) {
		IElementType type = ElementTypeRegistry.getInstance().getElementType(
				element);
		return types.contains(type);
	}

	/**
	 * @generated
	 */
	protected EObject selectElement(EObject[] elements) {
		Shell shell = Display.getCurrent().getActiveShell();
		ILabelProvider labelProvider = new AdapterFactoryLabelProvider(
				OrganizationModelDiagramEditorPlugin.getInstance()
						.getItemProvidersAdapterFactory());
		ElementListSelectionDialog dialog = new ElementListSelectionDialog(
				shell, labelProvider);
		dialog
				.setMessage(Messages.OrganizationModelModelingAssistantProviderMessage);
		dialog
				.setTitle(Messages.OrganizationModelModelingAssistantProviderTitle);
		dialog.setMultipleSelection(false);
		dialog.setElements(elements);
		EObject selected = null;
		if (dialog.open() == Window.OK) {
			selected = (EObject) dialog.getFirstResult();
		}
		return selected;
	}

	@Override
	public boolean provides(IOperation operation) {
		// Disable the "Existing Element" option on the pop-up.
		if (operation instanceof SelectExistingElementForSourceOperation) {
			return false;
		} else if (operation instanceof SelectExistingElementForTargetOperation) {
			return false;
		}

		return super.provides(operation);
	}

}
