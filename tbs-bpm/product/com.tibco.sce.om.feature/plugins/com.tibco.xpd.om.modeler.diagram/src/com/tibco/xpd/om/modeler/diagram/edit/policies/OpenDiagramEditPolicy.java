package com.tibco.xpd.om.modeler.diagram.edit.policies;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.OpenEditPolicy;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.HintedDiagramLinkStyle;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.Style;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.om.modeler.diagram.edit.parts.OrgModelEditPart;
import com.tibco.xpd.om.modeler.diagram.part.Messages;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelDiagramEditor;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelDiagramEditorPlugin;

/**
 * @generated
 */
public class OpenDiagramEditPolicy extends OpenEditPolicy {

    /**
     * @generated
     */
    protected Command getOpenCommand(Request request) {
        EditPart targetEditPart = getTargetEditPart(request);
        if (false == targetEditPart.getModel() instanceof View) {
            return null;
        }
        View view = (View) targetEditPart.getModel();
        Style link = view.getStyle(NotationPackage.eINSTANCE
                .getHintedDiagramLinkStyle());
        if (false == link instanceof HintedDiagramLinkStyle) {
            return null;
        }
        return new ICommandProxy(new OpenDiagramCommand(
                (HintedDiagramLinkStyle) link));
    }

    /**
     * @generated
     */
    private static class OpenDiagramCommand extends
            AbstractTransactionalCommand {

        /**
         * @generated
         */
        private final HintedDiagramLinkStyle diagramFacet;

        /**
         * @generated
         */
        OpenDiagramCommand(HintedDiagramLinkStyle linkStyle) {
            // editing domain is taken for original diagram,
            // if we open diagram from another file, we should use another
            // editing domain
            super(TransactionUtil.getEditingDomain(linkStyle),
                    Messages.CommandName_OpenDiagram, null);
            diagramFacet = linkStyle;
        }

        // FIXME canExecute if !(readOnly && getDiagramToOpen == null), i.e.
        // open works on ro diagrams only when there's associated diagram
        // already

        /**
         * @generated
         */
        protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
                IAdaptable info) throws ExecutionException {
            try {
                Diagram diagram = getDiagramToOpen();
                if (diagram == null) {
                    diagram = intializeNewDiagram();
                }
                URI uri = EcoreUtil.getURI(diagram);
                String editorName = uri.lastSegment()
                        + "#" + diagram.eResource().getContents().indexOf(diagram); //$NON-NLS-1$
                IEditorInput editorInput = new URIEditorInput(uri, editorName);
                IWorkbenchPage page = PlatformUI.getWorkbench()
                        .getActiveWorkbenchWindow().getActivePage();
                page.openEditor(editorInput, getEditorID());
                return CommandResult.newOKCommandResult();
            } catch (Exception ex) {
                throw new ExecutionException(
                        Messages.OpenSubDiagramEditPolicy_cannotOpenDiagram_error_shortdesc,
                        ex);
            }
        }

        /**
         * @generated
         */
        protected Diagram getDiagramToOpen() {
            return diagramFacet.getDiagramLink();
        }

        /**
         * @generated NOT
         */
        protected Diagram intializeNewDiagram() throws ExecutionException {
            Diagram d = ViewService.createDiagram(getDiagramDomainElement(),
                    getDiagramKind(), getPreferencesHint());
            if (d == null) {
                throw new ExecutionException(
                        String
                                .format(
                                        Messages.OpenSubDiagramEditPolicy_cannotCreateDiagramKind_error_shortdesc,
                                        getDiagramKind()));
            }
            diagramFacet.setDiagramLink(d);
            assert diagramFacet.eResource() != null;
            diagramFacet.eResource().getContents().add(d);
            EObject container = diagramFacet.eContainer();
            while (container instanceof View) {
                ((View) container).persist();
                container = container.eContainer();
            }
            return d;
        }

        /**
         * @generated
         */
        protected EObject getDiagramDomainElement() {
            // use same element as associated with EP
            return ((View) diagramFacet.eContainer()).getElement();
        }

        /**
         * @generated
         */
        protected PreferencesHint getPreferencesHint() {
            // XXX prefhint from target diagram's editor?
            return OrganizationModelDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT;
        }

        /**
         * @generated
         */
        protected String getDiagramKind() {
            return OrgModelEditPart.MODEL_ID;
        }

        /**
         * @generated
         */
        protected String getEditorID() {
            return OrganizationModelDiagramEditor.ID;
        }
    }

}
