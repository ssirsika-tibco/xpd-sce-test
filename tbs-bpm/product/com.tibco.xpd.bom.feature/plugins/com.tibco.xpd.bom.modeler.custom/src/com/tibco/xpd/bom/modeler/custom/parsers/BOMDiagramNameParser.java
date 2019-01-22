package com.tibco.xpd.bom.modeler.custom.parsers;

import java.util.ArrayList;
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
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;

import com.tibco.xpd.bom.modeler.custom.internal.Messages;

public class BOMDiagramNameParser implements ISemanticParser {

    public boolean areSemanticElementsAffected(EObject listener,
            Object notification) {

        return false;
    }

    public List<?> getSemanticElementsBeingParsed(EObject element) {
        List<EObject> lstElems = new ArrayList<EObject>();

        if (element instanceof Diagram) {
            lstElems.add(element);
        }
        return lstElems;
    }

    public IContentAssistProcessor getCompletionProcessor(IAdaptable element) {
        // TODO Auto-generated method stub
        return null;
    }

    public String getEditString(IAdaptable element, int flags) {
        EObject obj = getElement(element);
        String text = ""; //$NON-NLS-1$
        if (obj instanceof Diagram) {
            Diagram diag = (Diagram) obj;
            text = diag.getName();
        }
        return text;
    }

    protected EObject getElement(IAdaptable element) {
        return (EObject) element.getAdapter(EObject.class);
    }

    public ICommand getParseCommand(IAdaptable element, final String newString,
            int flags) {

        EObject obj = getElement(element);

        if (obj instanceof Diagram) {
            final Diagram diag = (Diagram) obj;
            TransactionalEditingDomain ed =
                    TransactionUtil.getEditingDomain(diag);

            Command cmd =
                    SetCommand.create(ed, diag, NotationPackage.eINSTANCE
                            .getDiagram_Name(), newString);
            CompositeCommand result =
                    new CompositeCommand(
                            Messages.BOMBadgeDiagramNameParser_command_message);
            result.add(new EMFCommandOperation(ed, cmd));
            return result;
        }
        return UnexecutableCommand.INSTANCE;
    }

    public String getPrintString(IAdaptable element, int flags) {
        String text = Messages.BOMAuthorParser_AuthorNotSet_label;
        EObject obj = getElement(element);

        if (obj instanceof Diagram) {
            Diagram diag = (Diagram) obj;
            String name = diag.getName();
            text = Messages.BOMDiagramNameParser_DiagramPrefix_label + name;
        }

        return text;
    }

    public boolean isAffectingEvent(Object event, int flags) {
        if (event instanceof Notification) {
            Notification n = (Notification) event;

            if (n.getFeature() instanceof EAttribute) {
                if (n.getNotifier() instanceof Diagram) {
                    if (n.getFeature() == NotationPackage.eINSTANCE
                            .getDiagram_Name()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public IParserEditStatus isValidEditString(IAdaptable element,
            String editString) {
        return ParserEditStatus.EDITABLE_STATUS;
    }

}
