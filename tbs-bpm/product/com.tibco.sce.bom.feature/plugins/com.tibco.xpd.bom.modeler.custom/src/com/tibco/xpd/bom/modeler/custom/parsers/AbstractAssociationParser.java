/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.parsers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserEditStatus;
import org.eclipse.gmf.runtime.emf.ui.services.parser.ISemanticParser;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * 
 * Abstract base class extended by custom Association parser classes. Implements
 * IParser and ISemanticParser.
 * 
 * @author rgreen
 * 
 */
public abstract class AbstractAssociationParser implements IParser,
        ISemanticParser {

    protected final int index;
    private final List<EReference> affectingfeatures;

    /**
     * @param index
     */
    public AbstractAssociationParser(int index) {
        this.index = index;

        affectingfeatures = Arrays.asList(UMLPackage.eINSTANCE
                .getAssociation_MemberEnd(), UMLPackage.eINSTANCE
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
        return null;
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
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gmf.runtime.common.ui.services.parser.IParser#isAffectingEvent
     * (java.lang.Object, int)
     */
    public boolean isAffectingEvent(Object event, int flags) {
        return false;
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
     * org.eclipse.gmf.runtime.common.ui.services.parser.IParser#isValidEditString
     * (org.eclipse.core.runtime.IAdaptable, java.lang.String)
     */
    public IParserEditStatus isValidEditString(IAdaptable element,
            String editString) {
        return ParserEditStatus.EDITABLE_STATUS;
    }

    /**
     * Get the {@link Association} from the adaptable element.
     * 
     * @param element
     * @return <code>Association</code> or <code>null</code> if cannot be
     *         adapted.
     */
    protected Association getAssociation(IAdaptable element) {
        return (Association) element.getAdapter(Association.class);
    }

    /**
     * Get the {@link Property} of the given index (passed to constructor) from
     * the <code>Association</code> adapted from the element.
     * 
     * @param element
     * @return <code>Property</code> or <code>null</code> if element cannot be
     *         adapted to <code>Association</code>.
     */
    protected Property getProperty(IAdaptable element) {
        Association assoc = getAssociation(element);
        Property prop = null;
        if (assoc != null) {
            prop = assoc.getMemberEnds().get(index);
        }
        return prop;
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
            if (affectingfeatures.contains(n.getFeature())) {
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

        List<EObject> lst = null;
        if (element instanceof Association) {
            lst = new ArrayList<EObject>();
            lst.add(element);

            if (((Association) element).getMemberEnds().size() > index) {
                Property prop = (Property) ((Association) element)
                        .getMemberEnds().get(index);
                lst.add(prop);
                if (prop.getUpperValue() != null) {
                    lst.add(prop.getUpperValue());
                }
                if (prop.getLowerValue() != null) {
                    lst.add(prop.getLowerValue());
                }

            }
        }
        return lst == null ? Collections.EMPTY_LIST : lst;
    }

}
