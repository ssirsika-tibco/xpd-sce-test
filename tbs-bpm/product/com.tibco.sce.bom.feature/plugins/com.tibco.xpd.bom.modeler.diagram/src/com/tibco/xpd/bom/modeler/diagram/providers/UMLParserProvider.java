/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.providers;

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
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationClassNameEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationClassOperationEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationClassPropertyEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationNameEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.BadgeAuthorEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.BadgeModelNameEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.ClassNameEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.EnumerationLiteralEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.EnumerationNameEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.OperationEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.PackageNameEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.PrimitiveTypeNameEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.ProfileApplicationEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.PropertyEditPart;
import com.tibco.xpd.bom.modeler.diagram.parsers.MessageFormatParser;
import com.tibco.xpd.bom.modeler.diagram.part.UMLVisualIDRegistry;

/**
 * @generated
 */
public class UMLParserProvider extends AbstractProvider implements
        IParserProvider {

    /**
     * @generated
     */
    private IParser packageName_4001Parser;

    /**
     * @generated
     */
    private IParser getPackageName_4001Parser() {
        if (packageName_4001Parser == null) {
            packageName_4001Parser = createPackageName_4001Parser();
        }
        return packageName_4001Parser;
    }

    /**
     * @generated
     */
    protected IParser createPackageName_4001Parser() {
        EAttribute[] features = new EAttribute[] { UMLPackage.eINSTANCE
                .getNamedElement_Name(), };
        MessageFormatParser parser = new MessageFormatParser(features);
        return parser;
    }

    /**
     * @generated
     */
    private IParser className_4003Parser;

    /**
     * @generated
     */
    private IParser getClassName_4003Parser() {
        if (className_4003Parser == null) {
            className_4003Parser = createClassName_4003Parser();
        }
        return className_4003Parser;
    }

    /**
     * @generated
     */
    protected IParser createClassName_4003Parser() {
        EAttribute[] features = new EAttribute[] { UMLPackage.eINSTANCE
                .getNamedElement_Name(), };
        MessageFormatParser parser = new MessageFormatParser(features);
        return parser;
    }

    /**
     * @generated
     */
    private IParser primitiveTypeName_4006Parser;

    /**
     * @generated
     */
    private IParser getPrimitiveTypeName_4006Parser() {
        if (primitiveTypeName_4006Parser == null) {
            primitiveTypeName_4006Parser = createPrimitiveTypeName_4006Parser();
        }
        return primitiveTypeName_4006Parser;
    }

    /**
     * @generated
     */
    protected IParser createPrimitiveTypeName_4006Parser() {
        EAttribute[] features = new EAttribute[] { UMLPackage.eINSTANCE
                .getNamedElement_Name(), };
        MessageFormatParser parser = new MessageFormatParser(features);
        return parser;
    }

    /**
     * @generated
     */
    private IParser enumerationName_4015Parser;

    /**
     * @generated
     */
    private IParser getEnumerationName_4015Parser() {
        if (enumerationName_4015Parser == null) {
            enumerationName_4015Parser = createEnumerationName_4015Parser();
        }
        return enumerationName_4015Parser;
    }

    /**
     * @generated
     */
    protected IParser createEnumerationName_4015Parser() {
        EAttribute[] features = new EAttribute[] { UMLPackage.eINSTANCE
                .getNamedElement_Name(), };
        MessageFormatParser parser = new MessageFormatParser(features);
        return parser;
    }

    /**
     * @generated
     */
    private IParser associationClassName_4018Parser;

    /**
     * @generated
     */
    private IParser getAssociationClassName_4018Parser() {
        if (associationClassName_4018Parser == null) {
            associationClassName_4018Parser = createAssociationClassName_4018Parser();
        }
        return associationClassName_4018Parser;
    }

    /**
     * @generated
     */
    protected IParser createAssociationClassName_4018Parser() {
        EAttribute[] features = new EAttribute[] { UMLPackage.eINSTANCE
                .getNamedElement_Name(), };
        MessageFormatParser parser = new MessageFormatParser(features);
        return parser;
    }

    /**
     * @generated
     */
    private IParser packageName_4023Parser;

    /**
     * @generated
     */
    private IParser getPackageName_4023Parser() {
        if (packageName_4023Parser == null) {
            packageName_4023Parser = createPackageName_4023Parser();
        }
        return packageName_4023Parser;
    }

    /**
     * @generated
     */
    protected IParser createPackageName_4023Parser() {
        EAttribute[] features = new EAttribute[] { UMLPackage.eINSTANCE
                .getNamedElement_Name(), };
        MessageFormatParser parser = new MessageFormatParser(features);
        return parser;
    }

    /**
     * @generated
     */
    private IParser packageName_4025Parser;

    /**
     * @generated
     */
    private IParser getPackageName_4025Parser() {
        if (packageName_4025Parser == null) {
            packageName_4025Parser = createPackageName_4025Parser();
        }
        return packageName_4025Parser;
    }

    /**
     * @generated
     */
    protected IParser createPackageName_4025Parser() {
        EAttribute[] features = new EAttribute[] { UMLPackage.eINSTANCE
                .getNamedElement_Name(), };
        MessageFormatParser parser = new MessageFormatParser(features);
        return parser;
    }

    /**
     * @generated
     */
    private IParser property_2001Parser;

    /**
     * @generated
     */
    private IParser getProperty_2001Parser() {
        if (property_2001Parser == null) {
            property_2001Parser = createProperty_2001Parser();
        }
        return property_2001Parser;
    }

    /**
     * @generated
     */
    protected IParser createProperty_2001Parser() {
        EAttribute[] features = new EAttribute[] { UMLPackage.eINSTANCE
                .getNamedElement_Name(), };
        MessageFormatParser parser = new MessageFormatParser(features);
        return parser;
    }

    /**
     * @generated
     */
    private IParser operation_2002Parser;

    /**
     * @generated
     */
    private IParser getOperation_2002Parser() {
        if (operation_2002Parser == null) {
            operation_2002Parser = createOperation_2002Parser();
        }
        return operation_2002Parser;
    }

    /**
     * @generated
     */
    protected IParser createOperation_2002Parser() {
        EAttribute[] features = new EAttribute[] { UMLPackage.eINSTANCE
                .getNamedElement_Name(), };
        MessageFormatParser parser = new MessageFormatParser(features);
        return parser;
    }

    /**
     * @generated
     */
    private IParser enumerationLiteral_2003Parser;

    /**
     * @generated
     */
    private IParser getEnumerationLiteral_2003Parser() {
        if (enumerationLiteral_2003Parser == null) {
            enumerationLiteral_2003Parser = createEnumerationLiteral_2003Parser();
        }
        return enumerationLiteral_2003Parser;
    }

    /**
     * @generated
     */
    protected IParser createEnumerationLiteral_2003Parser() {
        EAttribute[] features = new EAttribute[] { UMLPackage.eINSTANCE
                .getNamedElement_Name(), };
        MessageFormatParser parser = new MessageFormatParser(features);
        return parser;
    }

    /**
     * @generated
     */
    private IParser property_2004Parser;

    /**
     * @generated
     */
    private IParser getProperty_2004Parser() {
        if (property_2004Parser == null) {
            property_2004Parser = createProperty_2004Parser();
        }
        return property_2004Parser;
    }

    /**
     * @generated
     */
    protected IParser createProperty_2004Parser() {
        EAttribute[] features = new EAttribute[] { UMLPackage.eINSTANCE
                .getNamedElement_Name(), };
        MessageFormatParser parser = new MessageFormatParser(features);
        return parser;
    }

    /**
     * @generated
     */
    private IParser operation_2005Parser;

    /**
     * @generated
     */
    private IParser getOperation_2005Parser() {
        if (operation_2005Parser == null) {
            operation_2005Parser = createOperation_2005Parser();
        }
        return operation_2005Parser;
    }

    /**
     * @generated
     */
    protected IParser createOperation_2005Parser() {
        EAttribute[] features = new EAttribute[] { UMLPackage.eINSTANCE
                .getNamedElement_Name(), };
        MessageFormatParser parser = new MessageFormatParser(features);
        return parser;
    }

    /**
     * @generated
     */
    private IParser profileApplication_2006Parser;

    /**
     * @generated
     */
    private IParser getProfileApplication_2006Parser() {
        if (profileApplication_2006Parser == null) {
            profileApplication_2006Parser = createProfileApplication_2006Parser();
        }
        return profileApplication_2006Parser;
    }

    /**
     * @generated
     */
    protected IParser createProfileApplication_2006Parser() {
        EAttribute[] features = new EAttribute[] { UMLPackage.eINSTANCE
                .getProfileApplication_IsStrict(), };
        MessageFormatParser parser = new MessageFormatParser(features);
        return parser;
    }

    /**
     * @generated
     */
    private IParser associationName_4010Parser;

    /**
     * @generated
     */
    private IParser getAssociationName_4010Parser() {
        if (associationName_4010Parser == null) {
            associationName_4010Parser = createAssociationName_4010Parser();
        }
        return associationName_4010Parser;
    }

    /**
     * @generated
     */
    protected IParser createAssociationName_4010Parser() {
        EAttribute[] features = new EAttribute[] { UMLPackage.eINSTANCE
                .getNamedElement_Name(), };
        MessageFormatParser parser = new MessageFormatParser(features);
        return parser;
    }

    /**
     * @generated
     */
    protected IParser getParser(int visualID) {
        switch (visualID) {
        case PackageNameEditPart.VISUAL_ID:
            return getPackageName_4001Parser();
        case ClassNameEditPart.VISUAL_ID:
            return getClassName_4003Parser();
        case PrimitiveTypeNameEditPart.VISUAL_ID:
            return getPrimitiveTypeName_4006Parser();
        case EnumerationNameEditPart.VISUAL_ID:
            return getEnumerationName_4015Parser();
        case AssociationClassNameEditPart.VISUAL_ID:
            return getAssociationClassName_4018Parser();
        case BadgeModelNameEditPart.VISUAL_ID:
            return getPackageName_4023Parser();
        case BadgeAuthorEditPart.VISUAL_ID:
            return getPackageName_4025Parser();
        case PropertyEditPart.VISUAL_ID:
            return getProperty_2001Parser();
        case OperationEditPart.VISUAL_ID:
            return getOperation_2002Parser();
        case EnumerationLiteralEditPart.VISUAL_ID:
            return getEnumerationLiteral_2003Parser();
        case AssociationClassPropertyEditPart.VISUAL_ID:
            return getProperty_2004Parser();
        case AssociationClassOperationEditPart.VISUAL_ID:
            return getOperation_2005Parser();
        case ProfileApplicationEditPart.VISUAL_ID:
            return getProfileApplication_2006Parser();
        case AssociationNameEditPart.VISUAL_ID:
            return getAssociationName_4010Parser();
        }
        return null;
    }

    /**
     * @generated
     */
    public IParser getParser(IAdaptable hint) {
        String vid = (String) hint.getAdapter(String.class);
        if (vid != null) {
            return getParser(UMLVisualIDRegistry.getVisualID(vid));
        }
        View view = (View) hint.getAdapter(View.class);
        if (view != null) {
            return getParser(UMLVisualIDRegistry.getVisualID(view));
        }
        return null;
    }

    /**
     * @generated
     */
    public boolean provides(IOperation operation) {
        if (operation instanceof GetParserOperation) {
            IAdaptable hint = ((GetParserOperation) operation).getHint();
            if (UMLElementTypes.getElement(hint) == null) {
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
