package com.tibco.xpd.bom.modeler.custom.providers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.GetParserOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserProvider;
import org.eclipse.gmf.runtime.notation.View;

import com.tibco.xpd.bom.modeler.custom.parsers.SuperTypeParser;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationClassSuperClassNameEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.EnumerationSuperTypeNameEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.PrimTypeSuperTypeNameLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.SuperClassNameLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.part.UMLVisualIDRegistry;
import com.tibco.xpd.bom.modeler.diagram.providers.UMLElementTypes;

public class UMLSuperTypeParserProvider extends AbstractProvider implements
        IParserProvider {

    private IParser superTypeParser;

    private IParser getSuperTypeParser() {
        if (superTypeParser == null) {
            superTypeParser = createSuperTypeParser();
        }
        return superTypeParser;
    }

    private IParser createSuperTypeParser() {
        SuperTypeParser parser = new SuperTypeParser();
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
        case SuperClassNameLabelEditPart.VISUAL_ID:
        case PrimTypeSuperTypeNameLabelEditPart.VISUAL_ID:
        case EnumerationSuperTypeNameEditPart.VISUAL_ID:
        case AssociationClassSuperClassNameEditPart.VISUAL_ID:
            return getSuperTypeParser();
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
