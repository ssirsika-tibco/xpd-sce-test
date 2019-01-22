package com.tibco.xpd.bom.modeler.custom.parsers;

import java.text.DateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserEditStatus;
import org.eclipse.gmf.runtime.emf.ui.services.parser.ISemanticParser;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.uml2.uml.Model;

import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;

public class BOMFileDateCreatedParser implements ISemanticParser {

    public boolean areSemanticElementsAffected(EObject listener,
            Object notification) {

        return false;
    }

    public List<?> getSemanticElementsBeingParsed(EObject element) {
        return Collections.singletonList(element);
    }

    public IContentAssistProcessor getCompletionProcessor(IAdaptable element) {
        // TODO Auto-generated method stub
        return null;
    }

    public String getEditString(IAdaptable element, int flags) {
        EObject obj = getElement(element);

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
        String text = Messages.BOMFileDateCreatedParser_UnknownDate_label;

        EObject obj = getElement(element);

        if (obj instanceof Model) {
            Model mod = (Model) obj;

            EAnnotation annotation = mod
                    .getEAnnotation(BOMResourcesPlugin.ModelEannotationMetaSource);

            if (annotation != null) {
                EMap<String, String> details = annotation.getDetails();
                text = details
                        .get(BOMResourcesPlugin.ModelEannotationMetaSource_datecreated);

                try {
                    long timeStamp = Long.valueOf(text);
                    Date d1 = new Date(timeStamp);
                    DateFormat df = DateFormat.getInstance();
                    text = df.format(d1); //$NON-NLS-1$

                } catch (Exception e) {
                    text = Messages.BOMFileDateCreatedParser_UnknownDate_label;
                }
            }

        }

        return text;
    }

    // TODO:
    public boolean isAffectingEvent(Object event, int flags) {
        if (event instanceof Notification) {
            Notification n = (Notification) event;

            // if (n.getFeature() instanceof EAttribute) {
            // EAttribute ea = (EAttribute) n.getFeature();
            //
            // // Check for changes to attribute name.
            // return ea == UMLPackage.Literals.NAMED_ELEMENT__NAME;
            //
            // }
        }
        return false;
    }

    public IParserEditStatus isValidEditString(IAdaptable element,
            String editString) {
        return ParserEditStatus.EDITABLE_STATUS;
    }

}
