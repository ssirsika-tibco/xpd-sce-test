package com.tibco.xpd.bom.modeler.custom.parsers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserEditStatus;
import org.eclipse.gmf.runtime.emf.ui.services.parser.ISemanticParser;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.resources.firstclassprofiles.FirstClassProfileManager;

public class StereotypeParser implements ISemanticParser {

    public boolean areSemanticElementsAffected(EObject listener,
            Object notification) {

        return false;
    }

    public List<?> getSemanticElementsBeingParsed(EObject element) {
        // The name of this method is not very enlightening!
        // Check the javadocs for a better description.
        // Basically, we build up a list of all the semantic
        // elements that our EObject element and Parser need to listen to.
        if (element instanceof Classifier) {
            ArrayList<EObject> list = new ArrayList<EObject>();
            list.add(element);

            return (list);
        }
        return Collections.singletonList(element);
    }

    public IContentAssistProcessor getCompletionProcessor(IAdaptable element) {
        // TODO Auto-generated method stub
        return null;
    }

    public String getEditString(IAdaptable element, int flags) {
        EObject obj = getElement(element);

        if (obj instanceof Classifier) {
            Classifier cl = (Classifier) obj;
            if (cl.getName() != null) {
                return cl.getName();
            }
        }
        return ""; //$NON-NLS-1$
    }

    protected EObject getElement(IAdaptable element) {
        return (EObject) element.getAdapter(EObject.class);
    }

    public ICommand getParseCommand(IAdaptable element, String newString,
            int flags) {
        // TODO Auto-generated method stub
        return null;
    }

    public String getPrintString(IAdaptable element, int flags) {
        String text = ""; //$NON-NLS-1$
        EObject obj = getElement(element);
        List<Stereotype> lstStereos = null;
        final FirstClassProfileManager firstClassManager =
                FirstClassProfileManager.getInstance();

        if (obj instanceof PackageableElement) {
            PackageableElement pe = (PackageableElement) obj;
            if (!firstClassManager.isFirstClassProfileApplied(pe.getModel())) {
                lstStereos = pe.getAppliedStereotypes();
            }
        }

        if (obj instanceof Generalization) {
            Generalization pe = (Generalization) obj;
            if (!firstClassManager.isFirstClassProfileApplied(pe.getModel())) {
                lstStereos = pe.getAppliedStereotypes();
            }

        }

        if ((lstStereos != null) && (lstStereos.size() > 0)) {
            int idx = 0;
            for (Stereotype type : lstStereos) {

                Profile profile = type.getProfile();

                if (profile != null && profile.eResource() != null
                        && !profile.eIsProxy()) {
                    URI uri = profile.eResource().getURI();
                    if (uri != null && !uri.isPlatformResource()) {
                        continue;
                    }

                    if (idx == 0) {
                        text = type.getLabel();
                    } else {
                        text = text + ", " + type.getLabel(); //$NON-NLS-1$
                    }
                    idx++;
                }
            }
            if (idx > 0) {
                text = "<<" + text + ">>"; //$NON-NLS-1$ //$NON-NLS-2$
            }
        }

        return text;
    }

    // TODO:
    public boolean isAffectingEvent(Object event, int flags) {
        if (event instanceof Notification) {
            Notification n = (Notification) event;

            if (n.getFeature() instanceof EAttribute) {
                EAttribute ea = (EAttribute) n.getFeature();

                // Check for changes to attribute name.
                return ea == UMLPackage.Literals.NAMED_ELEMENT__NAME;

            }
        }
        return false;
    }

    public IParserEditStatus isValidEditString(IAdaptable element,
            String editString) {
        return ParserEditStatus.EDITABLE_STATUS;
    }

}
