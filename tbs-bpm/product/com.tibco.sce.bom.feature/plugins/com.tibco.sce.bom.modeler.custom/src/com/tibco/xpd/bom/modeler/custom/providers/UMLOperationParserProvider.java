package com.tibco.xpd.bom.modeler.custom.providers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.GetParserOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserProvider;
import org.eclipse.gmf.runtime.notation.View;

import com.tibco.xpd.bom.modeler.custom.parsers.MultiplicityParser;
import com.tibco.xpd.bom.modeler.custom.parsers.OperationParser;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationClassOperationEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.OperationEditPart;
import com.tibco.xpd.bom.modeler.diagram.part.UMLVisualIDRegistry;
import com.tibco.xpd.bom.resources.ui.providers.OperationItemProvider;
import com.tibco.xpd.bom.resources.ui.providers.OperationItemProvider.OperationHintIDs;

public class UMLOperationParserProvider extends AbstractProvider implements
        IParserProvider {

    private IParser operationParser;
    private IParser multiplicityParser;

    private IParser getOperationParser() {
        if (operationParser == null) {
            operationParser = createOperationParser();
        }
        return operationParser;
    }

    private IParser createOperationParser() {
        OperationParser parser = new OperationParser();
        return parser;
    }

    /*
     * Source multiplicity
     */
    private IParser getMultiplicityParser() {
        if (multiplicityParser == null) {
            multiplicityParser = createMultiplicityParser();
        }
        return multiplicityParser;
    }

    protected IParser createMultiplicityParser() {
        MultiplicityParser parser = new MultiplicityParser();
        return parser;
    }

    public IParser getParser(IAdaptable hint) {

        // Check first fo a custom hint. I.e. we have defined one for parsing
        // multilplicity from an Operation argument/returnType. (Similarly this
        // method is used for parsing Property multiplicity. If it matches our
        // custom hint then we return a Multiplicity parser. It is the job of
        // the MultiplicityParser to deifferentiate between a Property and an
        // Operation.
        OperationItemProvider.OperationHintIDs op = (OperationItemProvider.OperationHintIDs) hint
                .getAdapter(OperationItemProvider.OperationHintIDs.class);
        if (op != null) {
            return getParser(op);
        }

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

    private IParser getParser(OperationHintIDs op) {
        switch (op) {
        case MULTIPLICITY:
            return getMultiplicityParser();
        }
        return null;
    }

    private IParser getParser(int visualID) {
        switch (visualID) {
        // These edit parts use custom parsers
        case OperationEditPart.VISUAL_ID:
        case AssociationClassOperationEditPart.VISUAL_ID:
            return getOperationParser();
        }

        return null;
    }

    public boolean provides(IOperation operation) {
        if (operation instanceof GetParserOperation) {
            IAdaptable hint = ((GetParserOperation) operation).getHint();
            return getParser(hint) != null;
        }
        return false;
    }

}
