/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.parsers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Property;

/**
 * Custom parser that retrieves the name upper and lower values of the
 * Association end member Property representing multiplicity. The index of the
 * Property end member is specified in the constructor argument.
 * 
 * @author wzurek
 * 
 */
public class AssociationMultiplicityParser extends AbstractAssociationParser {

    private MultiplicityParser multiplicity = new MultiplicityParser() {
        protected Property getProperty(IAdaptable element) {
            return AssociationMultiplicityParser.this.getProperty(element);
        }
    };

    /**
     * The Constructor.
     */
    public AssociationMultiplicityParser(int index) {
        super(index);
    }

    @Override
    public ICommand getParseCommand(IAdaptable element, String newString,
            int flags) {

        Object eo = element.getAdapter(EObject.class);

        // If the EObject we are providing for is an Association we need to pass
        // the appropriate Property to getParseCommand.
        if (eo != null || eo instanceof Association) {
            Association assoc = (Association) eo;
            Property prop = assoc.getMemberEnds().get(index);
            if (prop != null) {
                element = new EObjectAdapter(prop);
            }
        }

        return multiplicity.getParseCommand(element, newString, flags);
    }

    @Override
    public String getEditString(IAdaptable element, int flags) {
        return multiplicity.getEditString(element, flags);
    }

    @Override
    public String getPrintString(IAdaptable element, int flags) {
        return multiplicity.getEditString(element, flags);
    }

    @Override
    public boolean isAffectingEvent(Object event, int flags) {
        return multiplicity.isAffectingEvent(event, flags);
    }

    @Override
    public IContentAssistProcessor getCompletionProcessor(IAdaptable element) {
        return multiplicity.getCompletionProcessor(element);
    }

}
