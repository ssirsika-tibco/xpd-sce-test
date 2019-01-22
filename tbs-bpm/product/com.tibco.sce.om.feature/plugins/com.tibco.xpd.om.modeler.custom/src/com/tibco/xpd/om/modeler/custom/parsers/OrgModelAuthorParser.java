package com.tibco.xpd.om.modeler.custom.parsers;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EAttribute;
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
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.modeler.diagram.part.Messages;

public class OrgModelAuthorParser implements ISemanticParser {

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
        EObject obj = getElement(element);

        if (obj instanceof OrgModel) {
            OrgModel om = (OrgModel) obj;
            TransactionalEditingDomain ed = TransactionUtil
                    .getEditingDomain(om);
            Command cmd = SetCommand.create(ed, om, OMPackage.eINSTANCE.getBaseOrgModel_Author(), newString);
            CompositeCommand result = new CompositeCommand(
                    "Change author name"); //$NON-NLS-1$
            result.add(new EMFCommandOperation(ed, cmd));
            return result;

        }

        return UnexecutableCommand.INSTANCE;
    }

    public String getPrintString(IAdaptable element, int flags) {
        String text = com.tibco.xpd.om.modeler.custom.internal.Messages.OrgModelAuthorParser_AuthorNotSet_label;

        EObject obj = getElement(element);

        if (obj instanceof OrgModel) {
            OrgModel om = (OrgModel) obj;
            text = om.getAuthor();
        }

        return text;
    }

    // TODO:
    public boolean isAffectingEvent(Object event, int flags) {
        if (event instanceof Notification) {
            Notification n = (Notification) event;

            if (n.getFeature() instanceof EAttribute) {
                EAttribute ea = (EAttribute) n.getFeature();
                return ea == OMPackage.Literals.BASE_ORG_MODEL__AUTHOR;
            }
        }
        return false;
    }

    public IParserEditStatus isValidEditString(IAdaptable element,
            String editString) {
        return ParserEditStatus.EDITABLE_STATUS;
    }

}
