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
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.HintedDiagramLinkStyle;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.Style;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrganizationDisplayNameEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrganizationEditPart;
import com.tibco.xpd.om.modeler.diagram.part.Messages;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelDiagramEditorPlugin;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.custom.OrganizationSubBadgeEditPart;
import com.tibco.xpd.om.modeler.subdiagram.part.OrganizationModelSubDiagramEditorPlugin;

/**
 * @generated
 */
public class OpenSubDiagramEditPolicy extends OpenEditPolicy {

    /**
     * @generated NOT
     */
    protected Command getOpenCommand(Request request) {
        EditPart targetEditPart = getTargetEditPart(request);

        if (targetEditPart instanceof OrganizationDisplayNameEditPart) {
            OrganizationDisplayNameEditPart nameEP =
                    (OrganizationDisplayNameEditPart) targetEditPart;
            EditPart parent = nameEP.getParent();

            if (parent instanceof OrganizationEditPart) {
                targetEditPart = parent;
            }
        }

        if (false == targetEditPart.getModel() instanceof View) {
            return null;
        }
        View view = (View) targetEditPart.getModel();
        Style link =
                view.getStyle(NotationPackage.eINSTANCE
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
         * @generated NOT
         */
        protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
                IAdaptable info) throws ExecutionException {
            try {
                Diagram diagram = getDiagramToOpen();
                if (diagram == null) {
                    diagram = intializeNewDiagram();
                }

                // Set the editor name extension to the name of the Organization
                URI uri = EcoreUtil.getURI(diagram);
                EObject eo = getDiagramDomainElement();
                String editorName = ""; //$NON-NLS-1$
                if (eo instanceof Organization) {
                    Organization org = (Organization) eo;
                    editorName = org.getDisplayName();
                } else {
                    editorName =
                            uri.lastSegment()
                                    + "#" + diagram.eResource().getContents().indexOf(diagram); //$NON-NLS-1$                    
                }

                IEditorInput editorInput = new URIEditorInput(uri, editorName);
                IWorkbenchPage page =
                        PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                                .getActivePage();
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
            Diagram d =
                    ViewService.createDiagram(getDiagramDomainElement(),
                            getDiagramKind(),
                            getPreferencesHint());
            if (d == null) {
                throw new ExecutionException(
                        String
                                .format(Messages.OpenSubDiagramEditPolicy_cannotCreateDiagramKind_error_shortdesc,
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

            Node subDiagBadgeNode =
                    ViewService
                            .createNode(d,
                                    getDiagramDomainElement(),
                                    OrganizationSubBadgeEditPart.VISUAL_ID,
                                    OrganizationModelSubDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);

            LayoutConstraint constraintSubDiagBadge =
                    subDiagBadgeNode.getLayoutConstraint();

            if (constraintSubDiagBadge instanceof Bounds) {
                ((Bounds) constraintSubDiagBadge).setX(0);
                ((Bounds) constraintSubDiagBadge).setY(0);
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
            return "Organization Model Sub";
        }

        /**
         * @generated
         */
        protected String getEditorID() {
            return "com.tibco.xpd.om.modeler.subdiagram.part.OrganizationModelSubDiagramEditorID";
        }
    }

}
