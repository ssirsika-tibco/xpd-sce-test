package com.tibco.xpd.bom.modeler.custom.parsers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserEditStatus;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.ui.services.parser.ISemanticParser;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.uml2.uml.Model;

import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;

public class BOMAuthorParser implements ISemanticParser {

    public boolean areSemanticElementsAffected(EObject listener,
            Object notification) {

        return false;
    }

    public List<?> getSemanticElementsBeingParsed(EObject element) {
        List<EObject> lstElems = new ArrayList<EObject>();

        if (element instanceof Model) {
            Model mod = (Model) element;
            lstElems.add(mod);

            EAnnotation annotation =
                    mod
                            .getEAnnotation(BOMResourcesPlugin.ModelEannotationMetaSource);

            if (annotation != null) {
                lstElems.add(annotation);

                for (EObject content : annotation.eContents()) {
                    if (content != null) {
                        lstElems.add(content);
                    }
                }
            }

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

        if (obj instanceof Model) {
            final Model mod = (Model) obj;
            TransactionalEditingDomain ed =
                    TransactionUtil.getEditingDomain(mod);
            Resource resource = mod.eResource();

            if (resource != null) {
                IFile file = WorkspaceSynchronizer.getFile(resource);

                return new SetModelAuthorCommand(ed, file, mod, newString);
            }
        }
        return UnexecutableCommand.INSTANCE;
    }

    public String getPrintString(IAdaptable element, int flags) {
        String text = Messages.BOMAuthorParser_AuthorNotSet_label;

        EObject obj = getElement(element);

        if (obj instanceof Model) {
            Model mod = (Model) obj;

            EAnnotation annotation =
                    mod
                            .getEAnnotation(BOMResourcesPlugin.ModelEannotationMetaSource);

            if (annotation != null) {
                EMap<String, String> details = annotation.getDetails();
                text =
                        details
                                .get(BOMResourcesPlugin.ModelEannotationMetaSource_author);
            }
        }
        return text;
    }

    public boolean isAffectingEvent(Object event, int flags) {
        if (event instanceof Notification) {
            Notification n = (Notification) event;

            if (n.getFeature() instanceof EAttribute) {
                if (n.getNotifier() instanceof EStringToStringMapEntryImpl) {
                    return true;
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
    class SetModelAuthorCommand extends AbstractTransactionalCommand {

        TransactionalEditingDomain ed;

        Model mod;

        String authorName;

        public SetModelAuthorCommand(TransactionalEditingDomain domain,
                IFile file, Model model, String name) {
            super(domain, Messages.BOMAuthorParser_SetAuthorCommand_label,
                    Collections.singletonList(file));
            ed = domain;
            mod = model;
            authorName = name;
        }

        @Override
        protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
                IAdaptable info) throws ExecutionException {

            EAnnotation anot =
                    mod
                            .getEAnnotation(BOMResourcesPlugin.ModelEannotationMetaSource);
            anot.getDetails()
                    .put(BOMResourcesPlugin.ModelEannotationMetaSource_author,
                            authorName);

            return CommandResult.newOKCommandResult();
        }

    }
}
