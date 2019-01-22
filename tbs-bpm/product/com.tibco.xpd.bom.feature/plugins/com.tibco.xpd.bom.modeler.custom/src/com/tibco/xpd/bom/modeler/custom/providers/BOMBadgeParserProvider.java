package com.tibco.xpd.bom.modeler.custom.providers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.GetParserOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserProvider;

import com.tibco.xpd.bom.modeler.custom.parsers.BOMAuthorParser;
import com.tibco.xpd.bom.modeler.custom.parsers.BOMDiagramNameParser;
import com.tibco.xpd.bom.modeler.custom.parsers.BOMFileDateCreatedParser;
import com.tibco.xpd.bom.modeler.custom.parsers.BOMFileDateModifiedParser;
import com.tibco.xpd.bom.modeler.custom.parsers.BOMModelNameParser;
import com.tibco.xpd.bom.modeler.custom.parsers.ProfileApplicationParser;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.BadgeAuthorEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.BadgeDateCreatedEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.BadgeDateModifiedEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.BadgeDiagramNameEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.BadgeModelNameEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.ProfileApplicationEditPart;
import com.tibco.xpd.bom.modeler.diagram.part.UMLVisualIDRegistry;

public class BOMBadgeParserProvider extends AbstractProvider implements
        IParserProvider {

    private IParser bomModelNameParser;

    private IParser bomFileDateCreatedParser;

    private IParser bomFileDateModifiedParser;

    private IParser bomAuthorParser;

    private IParser profileApplicationParser;

    private IParser bomDiagramNameParser;

    private IParser getBOMModelNameParser() {
        if (bomModelNameParser == null) {
            bomModelNameParser = createBOMModelNameParser();
        }
        return bomModelNameParser;
    }

    private IParser getBOMAuthorParser() {
        if (bomAuthorParser == null) {
            bomAuthorParser = createBOMAuthorParser();
        }
        return bomAuthorParser;
    }

    private IParser getCreatedDateParser() {
        if (bomFileDateCreatedParser == null) {
            bomFileDateCreatedParser = createDateCreatedParser();
        }
        return bomFileDateCreatedParser;
    }

    private IParser getModifiedDateParser() {
        if (bomFileDateModifiedParser == null) {
            bomFileDateModifiedParser = createDateModifiedParser();
        }
        return bomFileDateModifiedParser;
    }

    private IParser getProfileApplicationParser() {

        if (profileApplicationParser == null) {
            profileApplicationParser = createProfileApplicationParser();
        }
        return profileApplicationParser;
    }

    private IParser getBOMDiagramNameParser() {
        if (bomDiagramNameParser == null) {
            bomDiagramNameParser = createBOMDiagramNameParser();
        }
        return bomDiagramNameParser;
    }

    private IParser createBOMModelNameParser() {
        BOMModelNameParser parser = new BOMModelNameParser();
        return parser;
    }

    private IParser createBOMAuthorParser() {
        BOMAuthorParser parser = new BOMAuthorParser();
        return parser;
    }

    private IParser createDateCreatedParser() {
        BOMFileDateCreatedParser parser = new BOMFileDateCreatedParser();
        return parser;
    }

    private IParser createDateModifiedParser() {
        BOMFileDateModifiedParser parser = new BOMFileDateModifiedParser();
        return parser;
    }

    private IParser createProfileApplicationParser() {
        ProfileApplicationParser parser = new ProfileApplicationParser();
        return parser;
    }

    private IParser createBOMDiagramNameParser() {
        BOMDiagramNameParser parser = new BOMDiagramNameParser();
        return parser;
    }

    protected IParser getParser(int visualID) {
        switch (visualID) {
        // These edit parts use custom parsers
        case BadgeDiagramNameEditPart.VISUAL_ID:
            return getBOMDiagramNameParser();
        case BadgeModelNameEditPart.VISUAL_ID:
            return getBOMModelNameParser();
        case BadgeAuthorEditPart.VISUAL_ID:
            return getBOMAuthorParser();
        case BadgeDateCreatedEditPart.VISUAL_ID:
            return getCreatedDateParser();
        case BadgeDateModifiedEditPart.VISUAL_ID:
            return getModifiedDateParser();
        case ProfileApplicationEditPart.VISUAL_ID:
            return getProfileApplicationParser();
        }
        return null;
    }

    public IParser getParser(IAdaptable hint) {
        String vid = (String) hint.getAdapter(String.class);
        if (vid != null) {
            Object object = hint.getAdapter(EObject.class);

            if (object != null) {
                if (object instanceof org.eclipse.uml2.uml.Package
                        && vid.equals(String
                                .valueOf(BadgeModelNameEditPart.VISUAL_ID))) {
                    return getBOMModelNameParser();
                }
            }

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
            return getParser(hint) != null;
        }
        return false;
    }

}
