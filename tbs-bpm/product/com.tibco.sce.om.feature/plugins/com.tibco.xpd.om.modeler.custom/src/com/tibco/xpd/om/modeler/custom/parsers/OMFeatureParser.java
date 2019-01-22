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
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.OrgUnitFeature;
import com.tibco.xpd.om.core.om.Position;
import com.tibco.xpd.om.core.om.PositionFeature;

public class OMFeatureParser implements ISemanticParser {

    public boolean areSemanticElementsAffected(EObject listener,
            Object notification) {

        if (notification instanceof Notification) {
            Notification n = (Notification) notification;

            if (n.getFeature() == OMPackage.Literals.ORG_UNIT__FEATURE) {
                // Check its the superclass of this class!
                if (listener instanceof OrgUnit) {
                    OrgUnit ou = (OrgUnit) listener;
                    // The notifier needs to tell itself that it has just
                    // added a had a "Feature set" on itself and needs to
                    // refresh its label
                    if (ou == n.getNotifier()) {
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

        if (element instanceof OrgUnit) {
            OrgUnit ou = (OrgUnit) element;
            OrgUnitFeature feature = ou.getFeature();

            // Add the feature so that we can pick up name changes for example.
            if (feature != null) {
                eoList.add(feature);
            }

        }

        if (element instanceof Position) {
            Position pos = (Position) element;
            PositionFeature feature = pos.getFeature();

            // Add the feature so that we can pick up name changes for example.
            if (feature != null) {
                eoList.add(feature);
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

        if (obj instanceof OrgUnit) {
            OrgUnit ou = (OrgUnit) obj;

            OrgUnitFeature feature = ou.getFeature();

            if (feature != null) {
                String text = null;
                text = feature.getDisplayName();
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

            if (notifier instanceof OrgUnit) {
                // If return parameter then check if type has changed
                isAffected =
                        n.getFeature() == OMPackage.Literals.ORG_UNIT__FEATURE;
            } else if (notifier instanceof OrgUnitFeature
                    || notifier instanceof PositionFeature) {

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
