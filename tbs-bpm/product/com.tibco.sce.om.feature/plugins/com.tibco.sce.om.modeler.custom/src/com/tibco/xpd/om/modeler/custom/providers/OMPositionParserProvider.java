package com.tibco.xpd.om.modeler.custom.providers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.GetParserOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserProvider;
import org.eclipse.gmf.runtime.notation.View;

import com.tibco.xpd.om.modeler.custom.parsers.OMPositionParser;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.PositionSubEditPart;
import com.tibco.xpd.om.modeler.subdiagram.part.OrganizationModelVisualIDRegistry;

public class OMPositionParserProvider extends AbstractProvider implements
        IParserProvider {

    private IParser positionParser;

    private IParser getOMPositionParser() {
        if (positionParser == null) {
            positionParser = createPositionParser();
        }
        return positionParser;
    }

    private IParser createPositionParser() {
        OMPositionParser parser = new OMPositionParser();
        return parser;
    }

    public IParser getParser(IAdaptable hint) {
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

    private IParser getParser(int visualID) {
        if (visualID == PositionSubEditPart.VISUAL_ID) {
            return getOMPositionParser();
        }

        return null;
    }

    public boolean provides(IOperation operation) {
        if (operation instanceof GetParserOperation) {
            IAdaptable hint = ((GetParserOperation) operation).getHint();
            if (hint == null) {
                return false;
            }
            return getParser(hint) != null;
        }
        return false;
    }
}
