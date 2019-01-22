package com.tibco.xpd.om.modeler.custom.parsers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.emf.workspace.EMFCommandOperation;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserEditStatus;
import org.eclipse.gmf.runtime.emf.ui.services.parser.ISemanticParser;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.Position;
import com.tibco.xpd.om.core.om.PositionFeature;
import com.tibco.xpd.om.core.om.Resource;
import com.tibco.xpd.om.core.om.ResourceAssociation;
import com.tibco.xpd.om.core.om.ResourceType;

public class OMPositionParser implements ISemanticParser {

    public boolean areSemanticElementsAffected(EObject listener,
            Object notification) {

        if (notification instanceof Notification) {
            Notification n = (Notification) notification;

            if (n.getNotifier() instanceof Position) {
                if (n.getFeature() == OMPackage.Literals.ASSOCIABLE_WITH_RESOURCES__RESOURCE_ASSOCIATION) {
                    return true;
                }
                if (n.getFeature() == OMPackage.Literals.POSITION__FEATURE) {
                    return true;
                }
            }

        }

        return false;

    }

    public List getSemanticElementsBeingParsed(EObject element) {

        List<EObject> eoList = new ArrayList<EObject>();
        eoList.add(element);

        if (element instanceof Position) {
            Position pos = (Position) element;
            PositionFeature feature = pos.getFeature();

            // Add the feature so that we can pick up name changes for example.
            if (feature != null) {
                eoList.add(feature);
            }

            // And also add human reaources
            EList<ResourceAssociation> resourceAssociations =
                    pos.getResourceAssociation();

            for (ResourceAssociation resAssoc : resourceAssociations) {
                Resource resource = resAssoc.getResource();

                if (resource != null) {
                    ResourceType resourceType = resource.getResourceType();

                    if (resourceType != null
                            && resourceType.isHumanResourceType()) {
                        eoList.add(resource);
                    }

                }

            }

        }

        return eoList;
    }

    public IContentAssistProcessor getCompletionProcessor(IAdaptable element) {
        // TODO Auto-generated method stub
        return null;
    }

    public String getEditString(IAdaptable element, int flags) {
        EObject elem = getElement(element);

        if (elem instanceof Position) {
            Position pos = (Position) elem;

            return pos.getDisplayName();
        }

        return ""; //$NON-NLS-1$
    }

    public ICommand getParseCommand(IAdaptable element, String newString,
            int flags) {
        EObject obj = getElement(element);

        if (obj instanceof Position) {
            Position pos = (Position) obj;
            TransactionalEditingDomain ed =
                    TransactionUtil.getEditingDomain(pos);
            Command cmd =
                    SetCommand.create(ed, pos, OMPackage.eINSTANCE
                            .getNamedElement_DisplayName(), newString);
            CompositeCommand result =
                    new CompositeCommand("Change position name"); //$NON-NLS-1$
            result.add(new EMFCommandOperation(ed, cmd));
            return result;

        }

        return UnexecutableCommand.INSTANCE;
    }

    public String getPrintString(IAdaptable element, int flags) {
        EObject obj = getElement(element);
        String text = ""; //$NON-NLS-1$

        if (obj instanceof Position) {
            Position position = (Position) obj;
            text = position.getDisplayName();

            // Append feature if necessary
            if (position.getFeature() != null) {
                text = text + " <" + position.getFeature().getDisplayName() //$NON-NLS-1$
                        + ">"; //$NON-NLS-1$

                EList<ResourceAssociation> resourceAssociation =
                        position.getResourceAssociation();

                for (ResourceAssociation resAssoc : resourceAssociation) {
                    Resource resource = resAssoc.getResource();

                    if (resource != null) {
                        ResourceType resourceType = resource.getResourceType();

                        // And append Human resource type (a way of displaying
                        // the
                        // person assigned to a positio for example.
                        if (resourceType != null
                                && resourceType.isHumanResourceType()) {
                            text =
                                    text
                                            + " (" + resource.getDisplayName() + ")"; //$NON-NLS-1$ //$NON-NLS-2$
                        }

                    }

                }

            }
        }

        return text; //$NON-NLS-1$
    }

    protected EObject getElement(IAdaptable element) {
        return (EObject) element.getAdapter(EObject.class);
    }

    public boolean isAffectingEvent(Object event, int flags) {
        boolean isAffected = false;
        if (event instanceof Notification) {
            Notification n = (Notification) event;
            Object notifier = n.getNotifier();
            Object feature = n.getFeature();

            if (notifier instanceof Position
                    || notifier instanceof PositionFeature
                    || notifier instanceof Resource) {

                if (feature == OMPackage.Literals.NAMED_ELEMENT__NAME
                        || feature == OMPackage.Literals.NAMED_ELEMENT__DISPLAY_NAME) {
                    isAffected = true;
                } else if ((feature == OMPackage.Literals.POSITION__FEATURE)
                        || (feature == OMPackage.Literals.ASSOCIABLE_WITH_RESOURCES__RESOURCE_ASSOCIATION)) {
                    isAffected = true;

                }
            }

        }
        return isAffected;
    }

    public IParserEditStatus isValidEditString(IAdaptable element,
            String editString) {
        return ParserEditStatus.EDITABLE_STATUS;
    }

}
