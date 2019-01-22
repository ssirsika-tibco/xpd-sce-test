/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.providers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.GetParserOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserProvider;
import org.eclipse.gmf.runtime.notation.View;

import com.tibco.xpd.bom.modeler.custom.parsers.AssociationEndMultiplicityParser;
import com.tibco.xpd.bom.modeler.custom.parsers.AssociationMultiplicityParser;
import com.tibco.xpd.bom.modeler.custom.parsers.AssociationRoleParser;
import com.tibco.xpd.bom.modeler.custom.parsers.MultiplicityParser;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationCardinalityAtSourceLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationCardinalityAtTargetLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationEndTargetMultiplicityLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationSourceLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationTargetLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.part.UMLVisualIDRegistry;
import com.tibco.xpd.bom.modeler.diagram.providers.UMLElementTypes;
import com.tibco.xpd.bom.resources.ui.providers.AssociationItemProvider;

/**
 * 
 * Provides an IParser for the Parser service. The parser retrieves the semantic
 * feature of an Association specified by the supplied IAdaptable hint (The
 * VISUAL_ID of an editpart).
 * 
 * @author rgreen
 * 
 */
public class UMLAssocParserProvider extends AbstractProvider implements
        IParserProvider {

    private IParser srcRoleNameParser;

    private IParser trgRoleNameParser;

    private IParser srcMultiplicityParser;

    private IParser trgMultiplicityParser;

    private IParser assocEndMultiplicityParser;

    private IParser srcMultiplicityParserForItemProviderID;

    private IParser trgMultiplicityParserForItemProviderID;

    /*
     * Source Role Name
     */
    private IParser getSrcRoleNameParser() {
        if (srcRoleNameParser == null) {
            srcRoleNameParser = createSrcRoleNameParser();
        }
        return srcRoleNameParser;
    }

    protected IParser createSrcRoleNameParser() {
        AssociationRoleParser parser = new AssociationRoleParser(0);
        return parser;
    }

    /*
     * Target Role Name
     */
    private IParser getTrgRoleNameParser() {
        if (trgRoleNameParser == null) {
            trgRoleNameParser = createTrgRoleNameParser();
        }
        return trgRoleNameParser;
    }

    protected IParser createTrgRoleNameParser() {
        AssociationRoleParser parser = new AssociationRoleParser(1);
        return parser;
    }

    /*
     * Source multiplicity
     */
    private IParser getSrcMultiplicityParser() {
        if (srcMultiplicityParser == null) {
            srcMultiplicityParser = createSrcMultiplicityParser();
        }
        return srcMultiplicityParser;
    }

    protected IParser createSrcMultiplicityParser() {
        MultiplicityParser parser = new MultiplicityParser();
        return parser;
    }

    /*
     * Target multiplicity
     */
    private IParser getTrgMultiplicityParser() {
        if (trgMultiplicityParser == null) {
            trgMultiplicityParser = createTrgMultiplicityParser();
        }
        return trgMultiplicityParser;
    }

    protected IParser createTrgMultiplicityParser() {
        MultiplicityParser parser = new MultiplicityParser();
        return parser;
    }

    /*
     * The following AssociationMultiplicityParser parsers are provided for an
     * Association EObject, as opposed to the usual Property object which gets a
     * Multiplicity Parser.
     * 
     * AssociationMultiplicityParser is a wrapper around the Multiplicity
     * parser.
     * 
     * Whereas the diagram labels use a Multiplicity parser (since their
     * semantic element is directly the Association endmember Property) we may
     * have to parse out multiplicity values when an Association is the current
     * selection. An example of this is displaying multipicity values on the
     * Advanced Tab when an Association selected.
     */
    private IParser getSrcMultiplicityParserForItemProviderID() {
        if (srcMultiplicityParserForItemProviderID == null) {
            srcMultiplicityParserForItemProviderID =
                    createSrcMultiplicityParserForItemProviderID();
        }
        return srcMultiplicityParserForItemProviderID;
    }

    protected IParser createSrcMultiplicityParserForItemProviderID() {
        AssociationMultiplicityParser parser =
                new AssociationMultiplicityParser(0);
        return parser;
    }

    private IParser getTrgMultiplicityParserForItemProviderID() {
        if (trgMultiplicityParserForItemProviderID == null) {
            trgMultiplicityParserForItemProviderID =
                    createTrgMultiplicityParserForItemProviderID();
        }
        return trgMultiplicityParserForItemProviderID;
    }

    protected IParser createTrgMultiplicityParserForItemProviderID() {
        AssociationMultiplicityParser parser =
                new AssociationMultiplicityParser(1);
        return parser;
    }

    private IParser getAssocEndMultiplicityParser() {
        if (assocEndMultiplicityParser == null) {
            assocEndMultiplicityParser = createAssocEndMultiplicityParser();
        }
        return assocEndMultiplicityParser;
    }

    /**
     * Create an association end multiplicity parser.
     * 
     * @return
     */
    protected IParser createAssocEndMultiplicityParser() {
        return new AssociationEndMultiplicityParser();
    }

    protected IParser getParser(int visualID) {
        switch (visualID) {
        // These edit parts use custom parsers
        case AssociationSourceLabelEditPart.VISUAL_ID:
            return getSrcRoleNameParser();
        case AssociationTargetLabelEditPart.VISUAL_ID:
            return getTrgRoleNameParser();
        case AssociationCardinalityAtSourceLabelEditPart.VISUAL_ID:
            return getSrcMultiplicityParser();
        case AssociationCardinalityAtTargetLabelEditPart.VISUAL_ID:
            return getTrgMultiplicityParser();
        case AssociationEndTargetMultiplicityLabelEditPart.VISUAL_ID:
            return getAssocEndMultiplicityParser();
        }

        return null;
    }

    protected IParser getParser(AssociationItemProvider.IDs property) {
        switch (property) {
        case SOURCE_ROLE_MULTIPLICITY_PARSER:
            return getSrcMultiplicityParserForItemProviderID();
        case SOURCE_ROLE_NAME_PARSER:
            return getSrcRoleNameParser();
        case TARGET_ROLE_MULTIPLICITY_PARSER:
            return getTrgMultiplicityParserForItemProviderID();
        case TARGET_ROLE_NAME_PARSER:
            return getTrgRoleNameParser();
        }
        return null;
    }

    public IParser getParser(IAdaptable hint) {
        String vid = (String) hint.getAdapter(String.class);
        if (vid != null) {
            return getParser(UMLVisualIDRegistry.getVisualID(vid));
        }
        AssociationItemProvider.IDs prop =
                (AssociationItemProvider.IDs) hint
                        .getAdapter(AssociationItemProvider.IDs.class);
        if (prop != null) {
            return getParser(prop);
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
