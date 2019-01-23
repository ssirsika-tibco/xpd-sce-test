package com.tibco.xpd.om.modeler.diagram.providers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.GetParserOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserProvider;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.ui.services.parser.ParserHintAdapter;
import org.eclipse.gmf.runtime.notation.View;

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.modeler.diagram.edit.parts.DynamicOrgUnitDisplayNameEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrgUnitDisplayNameEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrgUnitRelationshipDisplayNameEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrganizationDisplayNameEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.PositionEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.OrgModelDateCreatedBadgeEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.OrgModelDateModifiedBadgeEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.GroupItemEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.OrgModelNameBadgeEditPart;
import com.tibco.xpd.om.modeler.diagram.parsers.MessageFormatParser;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelVisualIDRegistry;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.custom.OrgModelNameSubBadgeEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.custom.OrganizationNameSubBadgeEditPart;

/**
 * @generated
 */
public class OrganizationModelParserProvider extends AbstractProvider implements
		IParserProvider {

	/**
	 * @generated
	 */
	private IParser organizationDisplayName_4003Parser;

	/**
	 * @generated
	 */
	private IParser getOrganizationDisplayName_4003Parser() {
		if (organizationDisplayName_4003Parser == null) {
			organizationDisplayName_4003Parser = createOrganizationDisplayName_4003Parser();
		}
		return organizationDisplayName_4003Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createOrganizationDisplayName_4003Parser() {
		EAttribute[] features = new EAttribute[] { OMPackage.eINSTANCE
				.getNamedElement_DisplayName(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	// Custom code to retrieve semamtic names for badge labels
	private IParser orgModelBadgeLabel_Parser;

	private IParser getOrgModelBadgeLabel_Parser() {
		if (orgModelBadgeLabel_Parser == null) {
			orgModelBadgeLabel_Parser = createOrgBadgeLabel_Parser();
		}
		return orgModelBadgeLabel_Parser;
	}

	protected IParser createOrgBadgeLabel_Parser() {
		EAttribute[] features = new EAttribute[] { OMPackage.eINSTANCE
				.getNamedElement_DisplayName(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser orgUnitDisplayName_4001Parser;

	/**
	 * @generated
	 */
	private IParser getOrgUnitDisplayName_4001Parser() {
		if (orgUnitDisplayName_4001Parser == null) {
			orgUnitDisplayName_4001Parser = createOrgUnitDisplayName_4001Parser();
		}
		return orgUnitDisplayName_4001Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createOrgUnitDisplayName_4001Parser() {
		EAttribute[] features = new EAttribute[] { OMPackage.eINSTANCE
				.getNamedElement_DisplayName(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser position_2002Parser;

	/**
	 * @generated
	 */
	private IParser getPosition_2002Parser() {
		if (position_2002Parser == null) {
			position_2002Parser = createPosition_2002Parser();
		}
		return position_2002Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createPosition_2002Parser() {
		EAttribute[] features = new EAttribute[] { OMPackage.eINSTANCE
				.getNamedElement_DisplayName(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser dynamicOrgUnitDisplayName_4006Parser;

	/**
	 * @generated
	 */
	private IParser getDynamicOrgUnitDisplayName_4006Parser() {
		if (dynamicOrgUnitDisplayName_4006Parser == null) {
			dynamicOrgUnitDisplayName_4006Parser = createDynamicOrgUnitDisplayName_4006Parser();
		}
		return dynamicOrgUnitDisplayName_4006Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createDynamicOrgUnitDisplayName_4006Parser() {
		EAttribute[] features = new EAttribute[] { OMPackage.eINSTANCE
				.getNamedElement_DisplayName(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	private IParser getGroupItem_2050Parser() {
		if (position_2002Parser == null) {
			position_2002Parser = createGroupItem_2050Parser();
		}
		return position_2002Parser;
	}

	protected IParser createGroupItem_2050Parser() {
		EAttribute[] features = new EAttribute[] { OMPackage.eINSTANCE
				.getNamedElement_DisplayName(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser orgUnitRelationshipDisplayName_4005Parser;

	/**
	 * @generated
	 */
	private IParser getOrgUnitRelationshipDisplayName_4005Parser() {
		if (orgUnitRelationshipDisplayName_4005Parser == null) {
			orgUnitRelationshipDisplayName_4005Parser = createOrgUnitRelationshipDisplayName_4005Parser();
		}
		return orgUnitRelationshipDisplayName_4005Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createOrgUnitRelationshipDisplayName_4005Parser() {
		EAttribute[] features = new EAttribute[] { OMPackage.eINSTANCE
				.getNamedElement_DisplayName(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	/**
	 * Get parser for a String identifier
	 * 
	 * @param String
	 *            id
	 * @return IParser
	 */
	protected IParser getParser(String id) {
		if (OrgModelNameBadgeEditPart.VISUAL_ID.equals(id)
				|| OrgModelNameSubBadgeEditPart.VISUAL_ID.equals(id)) {
			return getOrgModelBadgeLabel_Parser();
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gmf.runtime.common.ui.services.parser.IParserProvider#getParser
	 * (org.eclipse.core.runtime.IAdaptable)
	 */
	public IParser getParser(IAdaptable hint) {
		// Custom method to provide for custom elements that
		// that are linked to semantic elements
		String id = (String) hint.getAdapter(String.class);
		if (id != null) {

			if (OrganizationNameSubBadgeEditPart.VISUAL_ID.equals(id)
					|| OrgModelNameBadgeEditPart.VISUAL_ID.equals(id)) {
				return getParser(id);
			}

			if (OrgModelDateCreatedBadgeEditPart.VISUAL_ID.equals(id)
					|| OrgModelDateModifiedBadgeEditPart.VISUAL_ID.equals(id)) {
				return getParser(id);
			}

		}
		return getParserGen(hint);
	}

	protected IParser getParser(int visualID) {
		switch (visualID) {
		case GroupItemEditPart.VISUAL_ID:
			return getGroupItem_2050Parser();
		}
		return getParserGen(visualID);
	}

	/**
	 * @generated
	 */
	protected IParser getParserGen(int visualID) {
		switch (visualID) {
		case OrganizationDisplayNameEditPart.VISUAL_ID:
			return getOrganizationDisplayName_4003Parser();
		case OrgUnitDisplayNameEditPart.VISUAL_ID:
			return getOrgUnitDisplayName_4001Parser();
		case PositionEditPart.VISUAL_ID:
			return getPosition_2002Parser();
		case DynamicOrgUnitDisplayNameEditPart.VISUAL_ID:
			return getDynamicOrgUnitDisplayName_4006Parser();
		case OrgUnitRelationshipDisplayNameEditPart.VISUAL_ID:
			return getOrgUnitRelationshipDisplayName_4005Parser();
		}
		return null;
	}

	/**
	 * @generated
	 */
	public IParser getParserGen(IAdaptable hint) {
		String vid = (String) hint.getAdapter(String.class);
		if (vid != null) {
			return getParser(OrganizationModelVisualIDRegistry.getVisualID(vid));
		}
		View view = (View) hint.getAdapter(View.class);
		if (view != null) {
			return getParser(OrganizationModelVisualIDRegistry
					.getVisualID(view));
		}
		return null;
	}

	/**
	 * @generated
	 */
	public boolean provides(IOperation operation) {
		if (operation instanceof GetParserOperation) {
			IAdaptable hint = ((GetParserOperation) operation).getHint();
			if (OrganizationModelElementTypes.getElement(hint) == null) {
				return false;
			}
			return getParser(hint) != null;
		}
		return false;
	}

	/**
	 * @generated
	 */
	public static class HintAdapter extends ParserHintAdapter {

		/**
		 * @generated
		 */
		private final IElementType elementType;

		/**
		 * @generated
		 */
		public HintAdapter(IElementType type, EObject object, String parserHint) {
			super(object, parserHint);
			assert type != null;
			elementType = type;
		}

		/**
		 * @generated
		 */
		public Object getAdapter(Class adapter) {
			if (IElementType.class.equals(adapter)) {
				return elementType;
			}
			return super.getAdapter(adapter);
		}
	}

}
