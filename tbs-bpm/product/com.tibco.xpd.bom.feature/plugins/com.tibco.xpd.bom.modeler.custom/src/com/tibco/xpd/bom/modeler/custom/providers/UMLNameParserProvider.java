/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.providers;

import java.util.Arrays;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.GetParserOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserProvider;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.custom.parsers.NameRoleParser;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationClassPropertyEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationEndTargetNameLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationNameEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.EnumerationLiteralEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.PropertyEditPart;
import com.tibco.xpd.bom.modeler.diagram.part.UMLVisualIDRegistry;
import com.tibco.xpd.bom.modeler.diagram.providers.UMLElementTypes;

/**
 * 
 * Provides an IParser for the Parser service. The parser retrieves the semantic
 * feature of an Association specified by the supplied IAdaptable hint (The
 * VISUAL_ID of an editpart).
 * 
 * @author rgreen
 * 
 */
public class UMLNameParserProvider extends AbstractProvider implements
        IParserProvider {

    private IParser nameParser;

    /*
     * Source Role Name
     */
    private IParser getNameParser() {
        if (nameParser == null) {
            nameParser = createNameParser();
        }
        return nameParser;
    }

    protected IParser createNameParser() {
        NameRoleParser parser = new NameRoleParser(UMLPackage.eINSTANCE
                .getNamedElement_Name(), Arrays.asList(UMLPackage.eINSTANCE
                .getNamedElement_Name(), UMLPackage.eINSTANCE
                .getTypedElement_Type(),
                UMLPackage.Literals.MULTIPLICITY_ELEMENT__LOWER_VALUE,
                UMLPackage.Literals.MULTIPLICITY_ELEMENT__UPPER_VALUE));
        return parser;
    }

    protected IParser getParser(int visualID) {
        switch (visualID) {
        // These edit parts use custom parsers
        case PropertyEditPart.VISUAL_ID:
        case AssociationClassPropertyEditPart.VISUAL_ID:
        case AssociationNameEditPart.VISUAL_ID:
        case EnumerationLiteralEditPart.VISUAL_ID:
        case AssociationEndTargetNameLabelEditPart.VISUAL_ID:
            return getNameParser();
        }

        return null;
    }

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
}
