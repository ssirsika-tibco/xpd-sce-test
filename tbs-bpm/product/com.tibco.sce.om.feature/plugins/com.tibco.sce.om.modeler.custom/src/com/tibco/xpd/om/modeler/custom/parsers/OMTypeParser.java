package com.tibco.xpd.om.modeler.custom.parsers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.emf.ui.services.parser.ISemanticParser;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgElementType;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.OrganizationType;

public class OMTypeParser implements ISemanticParser {

    public boolean areSemanticElementsAffected(EObject listener,
            Object notification) {

        if (notification instanceof Notification) {
            Notification n = (Notification) notification;

            if (n.getFeature() == OMPackage.Literals.ORGANIZATION__ORGANIZATION_TYPE) {
                // Check its the superclass of this class!
                if (listener instanceof Organization) {
                    Organization org = (Organization) listener;
                    // The notifier needs to tell itself that it has just
                    // added a had a "Feature set" on itself and needs to
                    // refresh its label
                    if (org == n.getNotifier()) {
                        return true;
                    }
                }
            }
        }

        return false;

    }

    public List getSemanticElementsBeingParsed(EObject element) {

        List<EObject> eoList = new ArrayList<EObject>();
        eoList.add(element);

        if (element instanceof Organization) {
            Organization org = (Organization) element;
            OrgElementType type = org.getType();

            // Add the feature so that we can pick up name changes for example.
            if (type != null) {
                eoList.add(type);
            }

        }
        return eoList;
    }

    public IContentAssistProcessor getCompletionProcessor(IAdaptable element) {
        // TODO Auto-generated method stub
        return null;
    }

    public String getEditString(IAdaptable element, int flags) {
        // TODO Auto-generated method stub
        return null;
    }

    public ICommand getParseCommand(IAdaptable element, String newString,
            int flags) {
        // TODO Auto-generated method stub
        return null;
    }

    public String getPrintString(IAdaptable element, int flags) {
        EObject obj = getElement(element);

        if (obj instanceof Organization) {
            Organization org = (Organization) obj;

            OrgElementType type = org.getType();

            if (type != null) {
                String text = null;
                text = type.getDisplayName();
                // text = "<" + feature.getDisplayName() + ">";
                return text;
            }
        }

        return ""; //$NON-NLS-1$
    }

    protected EObject getElement(IAdaptable element) {
        return (EObject) element.getAdapter(EObject.class);
    }

    public boolean isAffectingEvent(Object event, int flags) {
        boolean isAffected = false;
        if (event instanceof Notification) {
            Notification n = (Notification) event;
            Object notifier = n.getNotifier();

            if (notifier instanceof Organization) {
                // If return parameter then check if type has changed
                isAffected =
                        n.getFeature() == OMPackage.Literals.ORGANIZATION__ORGANIZATION_TYPE;
            } else if (notifier instanceof OrganizationType) {

                Object feature = n.getFeature();

                if (feature == OMPackage.Literals.NAMED_ELEMENT__NAME
                        || feature == OMPackage.Literals.NAMED_ELEMENT__DISPLAY_NAME) {
                    isAffected = true;
                }
            }
        }

        return isAffected;
    }

    public IParserEditStatus isValidEditString(IAdaptable element,
            String editString) {
        // TODO Auto-generated method stub
        return null;
    }

}
