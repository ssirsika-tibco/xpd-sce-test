package com.tibco.xpd.om.modeler.subdiagram.navigator;

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

import com.tibco.xpd.om.core.om.OrgUnitRelationship;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.DynamicOrgUnitDisplayNameEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.DynamicOrgUnitEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.OrgUnitSubDisplayNameEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.OrgUnitSubEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.OrgUnitSubRelationshipDisplayNameEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.OrgUnitSubRelationshipEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.OrganizationSubEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.PositionSubEditPart;
import com.tibco.xpd.om.modeler.subdiagram.part.OrganizationModelSubDiagramEditorPlugin;
import com.tibco.xpd.om.modeler.subdiagram.part.OrganizationModelVisualIDRegistry;
import com.tibco.xpd.om.modeler.subdiagram.providers.OrganizationModelElementTypes;
import com.tibco.xpd.om.modeler.subdiagram.providers.OrganizationModelParserProvider;

/**
 * @generated
 */
public class OrganizationModelSubNavigatorLabelProvider extends LabelProvider
		implements ICommonLabelProvider, ITreePathLabelProvider {

	/**
	 * @generated
	 */
	static {
		OrganizationModelSubDiagramEditorPlugin
				.getInstance()
				.getImageRegistry()
				.put(
						"Navigator?UnknownElement", ImageDescriptor.getMissingImageDescriptor()); //$NON-NLS-1$
		OrganizationModelSubDiagramEditorPlugin
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
		if (element instanceof OrganizationModelSubNavigatorItem
				&& !isOwnView(((OrganizationModelSubNavigatorItem) element)
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
		if (element instanceof OrganizationModelSubNavigatorGroup) {
			OrganizationModelSubNavigatorGroup group = (OrganizationModelSubNavigatorGroup) element;
			return OrganizationModelSubDiagramEditorPlugin.getInstance()
					.getBundledImage(group.getIcon());
		}

		if (element instanceof OrganizationModelSubNavigatorItem) {
			OrganizationModelSubNavigatorItem navigatorItem = (OrganizationModelSubNavigatorItem) element;
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
		case OrganizationSubEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Diagram?http://www.tibco.com/om/1.0?Organization", OrganizationModelElementTypes.Organization_79); //$NON-NLS-1$
		case OrgUnitSubEditPart.VISUAL_ID:
			return getImage(
					"Navigator?TopLevelNode?http://www.tibco.com/om/1.0?OrgUnit", OrganizationModelElementTypes.OrgUnit_1001); //$NON-NLS-1$
		case DynamicOrgUnitEditPart.VISUAL_ID:
			return getImage(
					"Navigator?TopLevelNode?http://www.tibco.com/om/1.0?DynamicOrgUnit", OrganizationModelElementTypes.DynamicOrgUnit_1002); //$NON-NLS-1$
		case PositionSubEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Node?http://www.tibco.com/om/1.0?Position", OrganizationModelElementTypes.Position_2001); //$NON-NLS-1$
		case OrgUnitSubRelationshipEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Link?http://www.tibco.com/om/1.0?OrgUnitRelationship", OrganizationModelElementTypes.OrgUnitRelationship_3001); //$NON-NLS-1$
		}
		return getImage("Navigator?UnknownElement", null); //$NON-NLS-1$
	}

	/**
	 * @generated
	 */
	private Image getImage(String key, IElementType elementType) {
		ImageRegistry imageRegistry = OrganizationModelSubDiagramEditorPlugin
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
		if (element instanceof OrganizationModelSubNavigatorGroup) {
			OrganizationModelSubNavigatorGroup group = (OrganizationModelSubNavigatorGroup) element;
			return group.getGroupName();
		}

		if (element instanceof OrganizationModelSubNavigatorItem) {
			OrganizationModelSubNavigatorItem navigatorItem = (OrganizationModelSubNavigatorItem) element;
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
		case OrganizationSubEditPart.VISUAL_ID:
			return getOrganization_79Text(view);
		case OrgUnitSubEditPart.VISUAL_ID:
			return getOrgUnit_1001Text(view);
		case DynamicOrgUnitEditPart.VISUAL_ID:
			return getDynamicOrgUnit_1002Text(view);
		case PositionSubEditPart.VISUAL_ID:
			return getPosition_2001Text(view);
		case OrgUnitSubRelationshipEditPart.VISUAL_ID:
			return getOrgUnitRelationship_3001Text(view);
		}
		return getUnknownElementText(view);
	}

	/**
	 * @generated
	 */
	private String getOrganization_79Text(View view) {
		Organization domainModelElement = (Organization) view.getElement();
		if (domainModelElement != null) {
			return domainModelElement.getName();
		} else {
			OrganizationModelSubDiagramEditorPlugin.getInstance().logError(
					"No domain element for view with visualID = " + 79); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getOrgUnit_1001Text(View view) {
		IAdaptable hintAdapter = new OrganizationModelParserProvider.HintAdapter(
				OrganizationModelElementTypes.OrgUnit_1001,
				(view.getElement() != null ? view.getElement() : view),
				OrganizationModelVisualIDRegistry
						.getType(OrgUnitSubDisplayNameEditPart.VISUAL_ID));
		IParser parser = ParserService.getInstance().getParser(hintAdapter);

		if (parser != null) {
			return parser.getPrintString(hintAdapter, ParserOptions.NONE
					.intValue());
		} else {
			OrganizationModelSubDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 4001); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}

	}

	/**
	 * @generated
	 */
	private String getDynamicOrgUnit_1002Text(View view) {
		IAdaptable hintAdapter = new OrganizationModelParserProvider.HintAdapter(
				OrganizationModelElementTypes.DynamicOrgUnit_1002, (view
						.getElement() != null ? view.getElement() : view),
				OrganizationModelVisualIDRegistry
						.getType(DynamicOrgUnitDisplayNameEditPart.VISUAL_ID));
		IParser parser = ParserService.getInstance().getParser(hintAdapter);

		if (parser != null) {
			return parser.getPrintString(hintAdapter, ParserOptions.NONE
					.intValue());
		} else {
			OrganizationModelSubDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 4010); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}

	}

	/**
	 * @generated
	 */
	private String getPosition_2001Text(View view) {
		IAdaptable hintAdapter = new OrganizationModelParserProvider.HintAdapter(
				OrganizationModelElementTypes.Position_2001,
				(view.getElement() != null ? view.getElement() : view),
				OrganizationModelVisualIDRegistry
						.getType(PositionSubEditPart.VISUAL_ID));
		IParser parser = ParserService.getInstance().getParser(hintAdapter);

		if (parser != null) {
			return parser.getPrintString(hintAdapter, ParserOptions.NONE
					.intValue());
		} else {
			OrganizationModelSubDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 2001); //$NON-NLS-1$
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
						.getType(OrgUnitSubRelationshipDisplayNameEditPart.VISUAL_ID));
		IParser parser = ParserService.getInstance().getParser(hintAdapter);

		if (parser != null) {
			return parser.getPrintString(hintAdapter, ParserOptions.NONE
					.intValue());
		} else {
			OrganizationModelSubDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 4003); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}

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
		return OrganizationSubEditPart.MODEL_ID
				.equals(OrganizationModelVisualIDRegistry.getModelID(view));
	}

}
