package com.tibco.xpd.om.modeler.custom.parsers;

import java.io.File;
import java.net.URI;
import java.text.DateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserEditStatus;
import org.eclipse.gmf.runtime.emf.ui.services.parser.ISemanticParser;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;

import com.tibco.xpd.om.modeler.custom.internal.Messages;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

public class OMFileDateModifiedParser implements ISemanticParser {

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
        String text = Messages.OMFileDateModifiedParser_UnknownDate_label;
        EObject obj = getElement(element);

        IFile file = WorkingCopyUtil.getFile(obj);
        long modified = 0;
        if (file != null) {
            URI uri = file.getRawLocationURI();
            File ff = new File(uri);

            if (ff.exists()) {
                modified = ff.lastModified();

            }
        } else {
            /*
             * This model may be from revision history so check if the resource
             * URI has a time stamp.
             */
            Resource resource = obj.eResource();
            if (resource != null && resource.getURI().hasQuery()) {
                String timeStamp = resource.getURI().query();
                if (timeStamp != null) {
                    try {
                        modified = Long.parseLong(timeStamp);
                    } catch (NumberFormatException e) {
                        modified = 0;
                    }
                }
            }
        }
        if (modified > 0) {
            Date d1 = new Date(modified);
            DateFormat df = DateFormat.getInstance();
            text = df.format(d1); //$NON-NLS-1$
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
