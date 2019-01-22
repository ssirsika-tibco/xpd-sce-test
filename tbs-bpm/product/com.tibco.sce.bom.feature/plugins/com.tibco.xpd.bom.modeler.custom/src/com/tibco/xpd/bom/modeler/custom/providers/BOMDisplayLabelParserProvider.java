package com.tibco.xpd.bom.modeler.custom.providers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.GetParserOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserProvider;
import org.eclipse.uml2.uml.NamedElement;

import com.tibco.xpd.bom.modeler.custom.parsers.DisplayLabelParser;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.ClassNameEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.EnumerationNameEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.PackageNameEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.PrimitiveTypeNameEditPart;
import com.tibco.xpd.bom.modeler.diagram.part.UMLVisualIDRegistry;

public class BOMDisplayLabelParserProvider extends AbstractProvider implements
        IParserProvider {

    private IParser displayLabelParser;

    private IParser getDisplayLabelParser() {
        if (displayLabelParser == null) {
            displayLabelParser = createDisplayLabelParser();
        }
        return displayLabelParser;
    }

    private IParser createDisplayLabelParser() {
        DisplayLabelParser parser = new DisplayLabelParser();
        return parser;
    }

    protected IParser getParser(int visualID) {
        switch (visualID) {
        // These edit parts use custom parsers
        case ClassNameEditPart.VISUAL_ID:
        case PrimitiveTypeNameEditPart.VISUAL_ID:
        case EnumerationNameEditPart.VISUAL_ID:
        case PackageNameEditPart.VISUAL_ID:
            return getDisplayLabelParser();
        }
        return null;
    }

    public IParser getParser(IAdaptable hint) {
        String vid = (String) hint.getAdapter(String.class);
        if (vid != null) {
            return getParser(UMLVisualIDRegistry.getVisualID(vid));
        }

        return null;
    }

    public boolean provides(IOperation operation) {
        if (operation instanceof GetParserOperation) {
            IAdaptable hint = ((GetParserOperation) operation).getHint();

            if (hint == null) {
                return false;
            }

            // Ensure we are only servicing EObject from the BOM/UML2
            EObject eo = (EObject) hint.getAdapter(EObject.class);

            if (eo instanceof NamedElement) {
                return getParser(hint) != null;
            }
        }
        return false;
    }

}
