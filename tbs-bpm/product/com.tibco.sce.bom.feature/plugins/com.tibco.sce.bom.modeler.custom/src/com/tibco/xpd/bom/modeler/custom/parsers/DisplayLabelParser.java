package com.tibco.xpd.bom.modeler.custom.parsers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserEditStatus;
import org.eclipse.gmf.runtime.emf.ui.services.parser.ISemanticParser;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.custom.commands.SetDisplayLabelCommand;
import com.tibco.xpd.bom.resources.ui.util.BomUIUtil;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.bom.types.PrimitivesUtil;

public class DisplayLabelParser implements ISemanticParser {

    public boolean areSemanticElementsAffected(EObject listener,
            Object notification) {

        return false;
    }

    public List<?> getSemanticElementsBeingParsed(EObject element) {
        List<EObject> lstElems = new ArrayList<EObject>();

        if (element instanceof NamedElement) {
            lstElems.add(element);
        }
        return lstElems;
    }

    public IContentAssistProcessor getCompletionProcessor(IAdaptable element) {
        return null;
    }

    public String getEditString(IAdaptable element, int flags) {
        return getPrintString(element, flags);
    }

    protected EObject getElement(IAdaptable element) {
        return (EObject) element.getAdapter(EObject.class);
    }

    public ICommand getParseCommand(IAdaptable element, final String newString,
            int flags) {

        EObject obj = getElement(element);

        if (obj instanceof NamedElement) {
            final NamedElement ne = (NamedElement) obj;
            TransactionalEditingDomain ed =
                    TransactionUtil.getEditingDomain(ne);
            Resource resource = ne.eResource();

            if (resource != null) {
                IFile file = WorkspaceSynchronizer.getFile(resource);

                return new SetDisplayLabelCommand(ed, file, ne, newString);
            }
        }

        return UnexecutableCommand.INSTANCE;
    }

    public String getPrintString(IAdaptable element, int flags) {
        String text = ""; //$NON-NLS-1$

        EObject obj = getElement(element);

        /*
         * If the element is a View and comes from a user diagram then show
         * fully (model relative) qualified name where necessary
         */
        if (obj instanceof View) {
            View view = (View) obj;
            // Set the object to the semantic element
            obj = view.getElement();

            if (view.getDiagram() != null
                    && BomUIUtil.isUserDiagram(view.getDiagram())) {
                EObject elem = view.getElement();

                if (elem instanceof NamedElement) {
                    String elemName =
                            PrimitivesUtil.getDisplayLabel((NamedElement) elem);
                    if (elemName != null) {
                        String qualification =
                                getModelRelativeQualification(elem.eContainer());
                        if (qualification.length() > 0) {
                            return qualification
                                    + BOMWorkingCopy.UML_PACKAGE_SEPARATOR
                                    + elemName;
                        }
                    }
                }
            }
        }

        if (obj instanceof NamedElement) {
            NamedElement elem = (NamedElement) obj;
            text = PrimitivesUtil.getDisplayLabel(elem);
        }

        return text;
    }

    private String getModelRelativeQualification(EObject elem) {
        String text = ""; //$NON-NLS-1$

        if (elem instanceof NamedElement && !(elem instanceof Model)) {
            text = ((NamedElement) elem).getName();
            EObject container = elem.eContainer();
            if (container instanceof NamedElement) {
                String qualification =
                        getModelRelativeQualification((NamedElement) container);
                if (qualification.length() > 0) {
                    text =
                            qualification
                                    + BOMWorkingCopy.UML_PACKAGE_SEPARATOR
                                    + text;
                }
            }
        }

        return text;
    }

    public boolean isAffectingEvent(Object event, int flags) {
        if (event instanceof Notification) {
            Notification n = (Notification) event;

            if (n.getFeature() instanceof EAttribute) {
                if (n.getNotifier() instanceof NamedElement) {
                    if (n.getFeature() == UMLPackage.Literals.NAMED_ELEMENT__NAME) {
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

    /**
     * 
     * Sets the author value in the model's EAnnoation details.
     * 
     * @author rgreen
     * 
     */
    // class SetDisplayLabelCommand extends AbstractTransactionalCommand {
    //
    // TransactionalEditingDomain ed;
    //
    // NamedElement elem;
    //
    // String displayLabel;
    //
    // public SetDisplayLabelCommand(TransactionalEditingDomain domain,
    // IFile file, NamedElement ne, String label) {
    // super(domain, Messages.DisplayLabelParser_CommandDescription_label,
    // Collections.singletonList(file));
    // ed = domain;
    // elem = ne;
    // displayLabel = label;
    // }
    //
    // @Override
    // protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
    // IAdaptable info) throws ExecutionException {
    //
    // PrimitivesUtil.setDisplayLabel(elem, displayLabel);
    //
    // return CommandResult.newOKCommandResult();
    // }
    //
    // }
}
