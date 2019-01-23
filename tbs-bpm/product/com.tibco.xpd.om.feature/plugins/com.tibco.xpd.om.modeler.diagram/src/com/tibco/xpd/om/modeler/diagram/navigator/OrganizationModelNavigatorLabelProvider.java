package com.tibco.xpd.om.modeler.diagram.navigator;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserOptions;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserService;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ITreePathLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.ViewerLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.ICommonLabelProvider;

import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.OrgUnitRelationship;
import com.tibco.xpd.om.modeler.diagram.edit.parts.DynamicOrgReferenceEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.DynamicOrgUnitDisplayNameEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.DynamicOrgUnitEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrgModelEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrgUnitDisplayNameEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrgUnitEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrgUnitRelationshipDisplayNameEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrgUnitRelationshipEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrganizationDisplayNameEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrganizationEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.PositionEditPart;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelDiagramEditorPlugin;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelVisualIDRegistry;
import com.tibco.xpd.om.modeler.diagram.providers.OrganizationModelElementTypes;
import com.tibco.xpd.om.modeler.diagram.providers.OrganizationModelParserProvider;

/**
 * @generated
 */
public class OrganizationModelNavigatorLabelProvider extends LabelProvider
		implements ICommonLabelProvider, ITreePathLabelProvider {

	/**
	 * @generated
	 */
	static {
		OrganizationModelDiagramEditorPlugin
				.getInstance()
				.getImageRegistry()
				.put(
						"Navigator?UnknownElement", ImageDescriptor.getMissingImageDescriptor()); //$NON-NLS-1$
		OrganizationModelDiagramEditorPlugin
				.getInstance()
				.getImageRegistry()
				.put(
						"Navigator?ImageNotFound", ImageDescriptor.getMissingImageDescriptor()); //$NON-NLS-1$
	}

	/**
	 * @generated
	 */
	public void updateLabel(ViewerLabel label, TreePath elementPath) {
		Object element = elementPath.getLastSegment();
		if (element instanceof OrganizationModelNavigatorItem
				&& !isOwnView(((OrganizationModelNavigatorItem) element)
						.getView())) {
			return;
		}
		label.setText(getText(element));
		label.setImage(getImage(element));
	}

	/**
	 * @generated
	 */
	public Image getImage(Object element) {
		if (element instanceof OrganizationModelNavigatorGroup) {
			OrganizationModelNavigatorGroup group = (OrganizationModelNavigatorGroup) element;
			return OrganizationModelDiagramEditorPlugin.getInstance()
					.getBundledImage(group.getIcon());
		}

		if (element instanceof OrganizationModelNavigatorItem) {
			OrganizationModelNavigatorItem navigatorItem = (OrganizationModelNavigatorItem) element;
			if (!isOwnView(navigatorItem.getView())) {
				return super.getImage(element);
			}
			return getImage(navigatorItem.getView());
		}

		return super.getImage(element);
	}

	/**
	 * @generated
	 */
	public Image getImage(View view) {
		switch (OrganizationModelVisualIDRegistry.getVisualID(view)) {
		case OrgModelEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Diagram?http://www.tibco.com/om/1.0?OrgModel", OrganizationModelElementTypes.OrgModel_79); //$NON-NLS-1$
		case OrganizationEditPart.VISUAL_ID:
			return getImage(
					"Navigator?TopLevelNode?http://www.tibco.com/om/1.0?Organization", OrganizationModelElementTypes.Organization_1001); //$NON-NLS-1$
		case OrgUnitEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Node?http://www.tibco.com/om/1.0?OrgUnit", OrganizationModelElementTypes.OrgUnit_2001); //$NON-NLS-1$
		case PositionEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Node?http://www.tibco.com/om/1.0?Position", OrganizationModelElementTypes.Position_2002); //$NON-NLS-1$
		case DynamicOrgUnitEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Node?http://www.tibco.com/om/1.0?DynamicOrgUnit", OrganizationModelElementTypes.DynamicOrgUnit_2003); //$NON-NLS-1$
		case OrgUnitRelationshipEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Link?http://www.tibco.com/om/1.0?OrgUnitRelationship", OrganizationModelElementTypes.OrgUnitRelationship_3001); //$NON-NLS-1$
		case DynamicOrgReferenceEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Link?http://www.tibco.com/om/1.0?DynamicOrgReference", OrganizationModelElementTypes.DynamicOrgReference_3002); //$NON-NLS-1$
		}
		return getImage("Navigator?UnknownElement", null); //$NON-NLS-1$
	}

	/**
	 * @generated
	 */
	private Image getImage(String key, IElementType elementType) {
		ImageRegistry imageRegistry = OrganizationModelDiagramEditorPlugin
				.getInstance().getImageRegistry();
		Image image = imageRegistry.get(key);
		if (image == null
				&& elementType != null
				&& OrganizationModelElementTypes
						.isKnownElementType(elementType)) {
			image = OrganizationModelElementTypes.getImage(elementType);
			imageRegistry.put(key, image);
		}

		if (image == null) {
			image = imageRegistry.get("Navigator?ImageNotFound"); //$NON-NLS-1$
			imageRegistry.put(key, image);
		}
		return image;
	}

	/**
	 * @generated
	 */
	public String getText(Object element) {
		if (element instanceof OrganizationModelNavigatorGroup) {
			OrganizationModelNavigatorGroup group = (OrganizationModelNavigatorGroup) element;
			return group.getGroupName();
		}

		if (element instanceof OrganizationModelNavigatorItem) {
			OrganizationModelNavigatorItem navigatorItem = (OrganizationModelNavigatorItem) element;
			if (!isOwnView(navigatorItem.getView())) {
				return null;
			}
			return getText(navigatorItem.getView());
		}

		return super.getText(element);
	}

	/**
	 * @generated
	 */
	public String getText(View view) {
		if (view.getElement() != null && view.getElement().eIsProxy()) {
			return getUnresolvedDomainElementProxyText(view);
		}
		switch (OrganizationModelVisualIDRegistry.getVisualID(view)) {
		case OrgModelEditPart.VISUAL_ID:
			return getOrgModel_79Text(view);
		case OrganizationEditPart.VISUAL_ID:
			return getOrganization_1001Text(view);
		case OrgUnitEditPart.VISUAL_ID:
			return getOrgUnit_2001Text(view);
		case PositionEditPart.VISUAL_ID:
			return getPosition_2002Text(view);
		case DynamicOrgUnitEditPart.VISUAL_ID:
			return getDynamicOrgUnit_2003Text(view);
		case OrgUnitRelationshipEditPart.VISUAL_ID:
			return getOrgUnitRelationship_3001Text(view);
		case DynamicOrgReferenceEditPart.VISUAL_ID:
			return getDynamicOrgReference_3002Text(view);
		}
		return getUnknownElementText(view);
	}

	/**
	 * @generated
	 */
	private String getOrgModel_79Text(View view) {
		OrgModel domainModelElement = (OrgModel) view.getElement();
		if (domainModelElement != null) {
			return domainModelElement.getName();
		} else {
			OrganizationModelDiagramEditorPlugin.getInstance().logError(
					"No domain element for view with visualID = " + 79); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getOrganization_1001Text(View view) {
		IAdaptable hintAdapter = new OrganizationModelParserProvider.HintAdapter(
				OrganizationModelElementTypes.Organization_1001, (view
						.getElement() != null ? view.getElement() : view),
				OrganizationModelVisualIDRegistry
						.getType(OrganizationDisplayNameEditPart.VISUAL_ID));
		IParser parser = ParserService.getInstance().getParser(hintAdapter);

		if (parser != null) {
			return parser.getPrintString(hintAdapter, ParserOptions.NONE
					.intValue());
		} else {
			OrganizationModelDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 4003); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}

	}

	/**
	 * @generated
	 */
	private String getOrgUnit_2001Text(View view) {
		IAdaptable hintAdapter = new OrganizationModelParserProvider.HintAdapter(
				OrganizationModelElementTypes.OrgUnit_2001,
				(view.getElement() != null ? view.getElement() : view),
				OrganizationModelVisualIDRegistry
						.getType(OrgUnitDisplayNameEditPart.VISUAL_ID));
		IParser parser = ParserService.getInstance().getParser(hintAdapter);

		if (parser != null) {
			return parser.getPrintString(hintAdapter, ParserOptions.NONE
					.intValue());
		} else {
			OrganizationModelDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 4001); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}

	}

	/**
	 * @generated
	 */
	private String getPosition_2002Text(View view) {
		IAdaptable hintAdapter = new OrganizationModelParserProvider.HintAdapter(
				OrganizationModelElementTypes.Position_2002,
				(view.getElement() != null ? view.getElement() : view),
				OrganizationModelVisualIDRegistry
						.getType(PositionEditPart.VISUAL_ID));
		IParser parser = ParserService.getInstance().getParser(hintAdapter);

		if (parser != null) {
			return parser.getPrintString(hintAdapter, ParserOptions.NONE
					.intValue());
		} else {
			OrganizationModelDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 2002); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getDynamicOrgUnit_2003Text(View view) {
		IAdaptable hintAdapter = new OrganizationModelParserProvider.HintAdapter(
				OrganizationModelElementTypes.DynamicOrgUnit_2003, (view
						.getElement() != null ? view.getElement() : view),
				OrganizationModelVisualIDRegistry
						.getType(DynamicOrgUnitDisplayNameEditPart.VISUAL_ID));
		IParser parser = ParserService.getInstance().getParser(hintAdapter);

		if (parser != null) {
			return parser.getPrintString(hintAdapter, ParserOptions.NONE
					.intValue());
		} else {
			OrganizationModelDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 4006); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}

	}

	/**
	 * @generated
	 */
	private String getOrgUnitRelationship_3001Text(View view) {
		IAdaptable hintAdapter = new OrganizationModelParserProvider.HintAdapter(
				OrganizationModelElementTypes.OrgUnitRelationship_3001,
				(view.getElement() != null ? view.getElement() : view),
				OrganizationModelVisualIDRegistry
						.getType(OrgUnitRelationshipDisplayNameEditPart.VISUAL_ID));
		IParser parser = ParserService.getInstance().getParser(hintAdapter);

		if (parser != null) {
			return parser.getPrintString(hintAdapter, ParserOptions.NONE
					.intValue());
		} else {
			OrganizationModelDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 4005); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}

	}

	/**
	 * @generated
	 */
	private String getDynamicOrgReference_3002Text(View view) {
		return ""; //$NON-NLS-1$
	}

	/**
	 * @generated
	 */
	private String getUnknownElementText(View view) {
		return "<UnknownElement Visual_ID = " + view.getType() + ">"; //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * @generated
	 */
	private String getUnresolvedDomainElementProxyText(View view) {
		return "<Unresolved domain element Visual_ID = " + view.getType() + ">"; //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * @generated
	 */
	public void init(ICommonContentExtensionSite aConfig) {
	}

	/**
	 * @generated
	 */
	public void restoreState(IMemento aMemento) {
	}

	/**
	 * @generated
	 */
	public void saveState(IMemento aMemento) {
	}

	/**
	 * @generated
	 */
	public String getDescription(Object anElement) {
		return null;
	}

	/**
	 * @generated
	 */
	private boolean isOwnView(View view) {
		return OrgModelEditPart.MODEL_ID
				.equals(OrganizationModelVisualIDRegistry.getModelID(view));
	}

}
