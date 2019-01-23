package com.tibco.xpd.om.modeler.custom.providers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.GetParserOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserProvider;

import com.tibco.xpd.om.modeler.custom.parsers.OMFileDateCreatedParser;
import com.tibco.xpd.om.modeler.custom.parsers.OMFileDateModifiedParser;
import com.tibco.xpd.om.modeler.custom.parsers.OMTypeParser;
import com.tibco.xpd.om.modeler.custom.parsers.OrgModelAuthorParser;
import com.tibco.xpd.om.modeler.custom.parsers.OrgModelVersionParser;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.OrgModelAuthorBadgeEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.OrgModelDateCreatedBadgeEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.OrgModelDateModifiedBadgeEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.OrgModelVersionBadgeEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.custom.OrgTypeSubBadgeEditPart;

public class OMBadgeParserProvider extends AbstractProvider implements
        IParserProvider {

    private IParser omFileDateCreatedParser;
    private IParser omFileDateModifiedParser;
    private IParser orgModelAuthorParser;
    private IParser orgModelVersionParser;
    private IParser omTypeParser;

    private IParser getOMVersionParser() {
        if (orgModelVersionParser == null) {
            orgModelVersionParser = createOMVersionParser();
        }
        return orgModelVersionParser;
    }

    private IParser getOMAuthorParser() {
        if (orgModelAuthorParser == null) {
            orgModelAuthorParser = createOMAuthorParser();
        }
        return orgModelAuthorParser;
    }

    private IParser getCreatedDateParser() {
        if (omFileDateCreatedParser == null) {
            omFileDateCreatedParser = createDateCreatedParser();
        }
        return omFileDateCreatedParser;
    }

    private IParser getModifiedDateParser() {
        if (omFileDateModifiedParser == null) {
            omFileDateModifiedParser = createDateModifiedParser();
        }
        return omFileDateModifiedParser;
    }

    private IParser getOMTypeParser() {
        if (omTypeParser == null) {
            omTypeParser = createOMTypeParser();
        }
        return omTypeParser;
    }

    private IParser createOMVersionParser() {
        OrgModelVersionParser parser = new OrgModelVersionParser();
        return parser;
    }

    private IParser createOMAuthorParser() {
        OrgModelAuthorParser parser = new OrgModelAuthorParser();
        return parser;
    }

    private IParser createDateCreatedParser() {
        OMFileDateCreatedParser parser = new OMFileDateCreatedParser();
        return parser;
    }

    private IParser createDateModifiedParser() {
        OMFileDateModifiedParser parser = new OMFileDateModifiedParser();
        return parser;
    }

    private IParser createOMTypeParser() {
        OMTypeParser parser = new OMTypeParser();
        return parser;
    }

    public IParser getParser(IAdaptable hint) {

        String id = (String) hint.getAdapter(String.class);
        if (id != null) {

            if (OrgModelAuthorBadgeEditPart.VISUAL_ID.equals(id)) {
                return getOMAuthorParser();
            }

            if (OrgModelDateCreatedBadgeEditPart.VISUAL_ID.equals(id)) {
                return getCreatedDateParser();
            }

            if (OrgModelDateModifiedBadgeEditPart.VISUAL_ID.equals(id)) {
                return getModifiedDateParser();
            }

            if (OrgModelVersionBadgeEditPart.VISUAL_ID.equals(id)) {
                return getOMVersionParser();
            }

            if (OrgTypeSubBadgeEditPart.VISUAL_ID.equals(id)) {
                return getOMTypeParser();
            }
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
