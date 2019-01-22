package com.tibco.xpd.om.modeler.subdiagram.providers;

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
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.DynamicOrgReferenceLabelEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.DynamicOrgUnitDisplayNameEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.OrgUnitSubDisplayNameEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.OrgUnitSubRelationshipDisplayNameEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.PositionSubEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.custom.OrgModelNameSubBadgeEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.custom.OrganizationNameSubBadgeEditPart;
import com.tibco.xpd.om.modeler.subdiagram.parsers.MessageFormatParser;
import com.tibco.xpd.om.modeler.subdiagram.parsers.NativeParser;
import com.tibco.xpd.om.modeler.subdiagram.part.OrganizationModelVisualIDRegistry;

/**
 * @generated
 */
public class OrganizationModelParserProvider extends AbstractProvider implements
		IParserProvider {

	private IParser orgBadgeLabel_Parser;

	private IParser getOrgBadgeLabel_Parser() {
		if (orgBadgeLabel_Parser == null) {
			orgBadgeLabel_Parser = createOrgBadgeLabel_Parser();
		}
		return orgBadgeLabel_Parser;
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
	private IParser dynamicOrgUnitDisplayName_4010Parser;

	/**
	 * @generated
	 */
	private IParser getDynamicOrgUnitDisplayName_4010Parser() {
		if (dynamicOrgUnitDisplayName_4010Parser == null) {
			dynamicOrgUnitDisplayName_4010Parser = createDynamicOrgUnitDisplayName_4010Parser();
		}
		return dynamicOrgUnitDisplayName_4010Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createDynamicOrgUnitDisplayName_4010Parser() {
		EAttribute[] features = new EAttribute[] { OMPackage.eINSTANCE
				.getNamedElement_DisplayName(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser dynamicOrgUnitDisplayName_4011Parser;

	/**
	 * @generated
	 */
	private IParser getDynamicOrgUnitDisplayName_4011Parser() {
		if (dynamicOrgUnitDisplayName_4011Parser == null) {
			dynamicOrgUnitDisplayName_4011Parser = createDynamicOrgUnitDisplayName_4011Parser();
		}
		return dynamicOrgUnitDisplayName_4011Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createDynamicOrgUnitDisplayName_4011Parser() {
		EAttribute[] features = new EAttribute[] { OMPackage.eINSTANCE
				.getNamedElement_DisplayName(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser position_2001Parser;

	/**
	 * @generated
	 */
	private IParser getPosition_2001Parser() {
		if (position_2001Parser == null) {
			position_2001Parser = createPosition_2001Parser();
		}
		return position_2001Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createPosition_2001Parser() {
		EAttribute[] features = new EAttribute[] { OMPackage.eINSTANCE
				.getNamedElement_DisplayName(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser orgUnitRelationshipDisplayName_4003Parser;

	/**
	 * @generated
	 */
	private IParser getOrgUnitRelationshipDisplayName_4003Parser() {
		if (orgUnitRelationshipDisplayName_4003Parser == null) {
			orgUnitRelationshipDisplayName_4003Parser = createOrgUnitRelationshipDisplayName_4003Parser();
		}
		return orgUnitRelationshipDisplayName_4003Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createOrgUnitRelationshipDisplayName_4003Parser() {
		EAttribute[] features = new EAttribute[] { OMPackage.eINSTANCE
				.getNamedElement_DisplayName(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	/**
	 * @generated
	 */
	protected IParser getParser(int visualID) {
		switch (visualID) {
		case OrgUnitSubDisplayNameEditPart.VISUAL_ID:
			return getOrgUnitDisplayName_4001Parser();
		case DynamicOrgUnitDisplayNameEditPart.VISUAL_ID:
			return getDynamicOrgUnitDisplayName_4010Parser();
		case DynamicOrgReferenceLabelEditPart.VISUAL_ID:
			return getDynamicOrgUnitDisplayName_4011Parser();
		case PositionSubEditPart.VISUAL_ID:
			return getPosition_2001Parser();
		case OrgUnitSubRelationshipDisplayNameEditPart.VISUAL_ID:
			return getOrgUnitRelationshipDisplayName_4003Parser();
		}
		return null;
	}

	/**
	 * Get parser for a String identifier
	 * 
	 * @param String
	 *            id
	 * @return IParser
	 */
	protected IParser getParser(String id) {
		if (OrganizationNameSubBadgeEditPart.VISUAL_ID.equals(id)
				|| OrgModelNameSubBadgeEditPart.VISUAL_ID.equals(id)) {
			return getOrgBadgeLabel_Parser();
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
	@Override
	public IParser getParser(IAdaptable hint) {
		// Custom method to provide for custom elements that
		// that are linked to semantic elements
		String id = (String) hint.getAdapter(String.class);
		if (id != null) {

			if (OrganizationNameSubBadgeEditPart.VISUAL_ID.equals(id)
					|| OrgModelNameSubBadgeEditPart.VISUAL_ID.equals(id)) {
				return getParser(id);
			}

		}
		return getParserGen(hint);
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
	@Override
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
		@Override
		public Object getAdapter(Class adapter) {
			if (IElementType.class.equals(adapter)) {
				return elementType;
			}
			return super.getAdapter(adapter);
		}
	}

}
