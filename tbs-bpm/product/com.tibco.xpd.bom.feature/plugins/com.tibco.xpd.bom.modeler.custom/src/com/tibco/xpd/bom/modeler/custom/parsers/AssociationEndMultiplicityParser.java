/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.parsers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.emf.ui.services.parser.ISemanticParser;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Parser for the Association (Class) End multiplicity.
 * 
 * @author njpatel
 * 
 */
public class AssociationEndMultiplicityParser implements IParser,
        ISemanticParser {

    private final MultiplicityParser multiplicity = new MultiplicityParser();
    private final List<EReference> features;

    /**
     * Association (class) end multiplicity parser.
     */
    public AssociationEndMultiplicityParser() {
        features = Arrays.asList(UMLPackage.eINSTANCE
                .getMultiplicityElement_LowerValue(), UMLPackage.eINSTANCE
                .getMultiplicityElement_UpperValue());
    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.gmf.runtime.common.ui.services.parser.IParser#
     * getCompletionProcessor(org.eclipse.core.runtime.IAdaptable)
     */
    public IContentAssistProcessor getCompletionProcessor(IAdaptable element) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getEditString
     * (org.eclipse.core.runtime.IAdaptable, int)
     */
    public String getEditString(IAdaptable element, int flags) {
        return multiplicity.getEditString(element, flags);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getParseCommand
     * (org.eclipse.core.runtime.IAdaptable, java.lang.String, int)
     */
    public ICommand getParseCommand(IAdaptable element, String newString,
            int flags) {
        return multiplicity.getParseCommand(element, newString, flags);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getPrintString
     * (org.eclipse.core.runtime.IAdaptable, int)
     */
    public String getPrintString(IAdaptable element, int flags) {
        return getEditString(element, flags);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gmf.runtime.common.ui.services.parser.IParser#isAffectingEvent
     * (java.lang.Object, int)
     */
    public boolean isAffectingEvent(Object event, int flags) {
        return multiplicity.isAffectingEvent(event, flags);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gmf.runtime.common.ui.services.parser.IParser#isValidEditString
     * (org.eclipse.core.runtime.IAdaptable, java.lang.String)
     */
    public IParserEditStatus isValidEditString(IAdaptable element,
            String editString) {
        return multiplicity.isValidEditString(element, editString);
    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.gmf.runtime.emf.ui.services.parser.ISemanticParser#
     * areSemanticElementsAffected(org.eclipse.emf.ecore.EObject,
     * java.lang.Object)
     */
    public boolean areSemanticElementsAffected(EObject listener,
            Object notification) {
        if (notification instanceof Notification) {
            Notification n = (Notification) notification;

            if (features.contains(n.getFeature())) {
                return true;
            }
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.gmf.runtime.emf.ui.services.parser.ISemanticParser#
     * getSemanticElementsBeingParsed(org.eclipse.emf.ecore.EObject)
     */
    public List<?> getSemanticElementsBeingParsed(EObject element) {
        List<EObject> lst = new ArrayList<EObject>();
        if (element instanceof Property) {
            Property prop = (Property) element;
            lst.add(prop);

            if (prop.getUpperValue() != null) {
                lst.add(prop.getUpperValue());
            }
            if (prop.getLowerValue() != null) {
                lst.add(prop.getLowerValue());
            }

        }
        return lst;
    }

}
