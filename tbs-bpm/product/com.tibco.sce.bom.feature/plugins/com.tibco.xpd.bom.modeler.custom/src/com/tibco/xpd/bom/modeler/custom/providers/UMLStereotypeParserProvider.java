package com.tibco.xpd.bom.modeler.custom.providers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.GetParserOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserProvider;
import org.eclipse.gmf.runtime.notation.View;

import com.tibco.xpd.bom.modeler.custom.parsers.StereotypeParser;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationClassStereoTypeLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.ClassStereoTypeLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.EnumerationStereoTypeLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.PackageStereoTypeLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.PrimTypeStereoTypeLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.StereotypeLineLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.part.UMLVisualIDRegistry;
import com.tibco.xpd.bom.modeler.diagram.providers.UMLElementTypes;

public class UMLStereotypeParserProvider extends AbstractProvider implements
        IParserProvider {

    private IParser stereotypeParser;

    private IParser getStereotypeParser() {
        if (stereotypeParser == null) {
            stereotypeParser = createStereotypeParser();
        }
        return stereotypeParser;
    }

    private IParser createStereotypeParser() {
        StereotypeParser parser = new StereotypeParser();
        return parser;
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

    private IParser getParser(int visualID) {
        switch (visualID) {
        // These edit parts use custom parsers
        case ClassStereoTypeLabelEditPart.VISUAL_ID:
        case PrimTypeStereoTypeLabelEditPart.VISUAL_ID:
        case PackageStereoTypeLabelEditPart.VISUAL_ID:
        case StereotypeLineLabelEditPart.VISUAL_ID:
        case EnumerationStereoTypeLabelEditPart.VISUAL_ID:
        case AssociationClassStereoTypeLabelEditPart.VISUAL_ID:
            return getStereotypeParser();
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
