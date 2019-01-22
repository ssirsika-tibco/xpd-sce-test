/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.providers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.GetParserOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserProvider;

import com.tibco.xpd.bom.modeler.custom.parsers.MultiplicityParser;
import com.tibco.xpd.bom.resources.ui.providers.PropertyItemProvider;

/**
 * @author wzurek
 * 
 */
public class UMLPropertyParserProvider extends AbstractProvider implements
        IParserProvider {

    private IParser multiplicityParser;

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

    protected IParser getParser(PropertyItemProvider.IDs property) {
        switch (property) {
        case MULTIPLICITY:
            return getMultiplicityParser();
        }
        return null;
    }

    public IParser getParser(IAdaptable hint) {
        // String vid = (String) hint.getAdapter(String.class);
        // if (vid != null) {
        // return getParser(UMLVisualIDRegistry.getVisualID(vid));
        // }
        PropertyItemProvider.IDs prop = (PropertyItemProvider.IDs) hint
                .getAdapter(PropertyItemProvider.IDs.class);
        if (prop != null) {
            return getParser(prop);
        }
        // View view = (View) hint.getAdapter(View.class);
        // if (view != null) {
        // return getParser(UMLVisualIDRegistry.getVisualID(view));
        // }
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
