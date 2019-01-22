/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.policies;

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
import org.eclipse.uml2.uml.Package;

import com.tibco.xpd.bom.modeler.diagram.edit.parts.CanvasPackageEditPart;
import com.tibco.xpd.bom.modeler.diagram.part.BOMDiagramEditorPlugin;
import com.tibco.xpd.bom.modeler.diagram.part.Messages;
import com.tibco.xpd.bom.modeler.diagram.part.UMLDiagramEditor;

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
                URI uri = EcoreUtil.getURI(diagram);
                String editorName = null;

                if (diagram.getElement() instanceof Package) {
                    Package pkg = (Package) diagram.getElement();

                    editorName = uri.lastSegment() + "#" + pkg.getName();
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
                        Messages.OpenDiagramEditPolicy_cannotOpenDiagram_error_shortdesc,
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
                                .format(Messages.OpenDiagramEditPolicy_cannotCreateDiagram_error_shortdesc,
                                        getDiagramKind()));
            }
            diagramFacet.setDiagramLink(d);
            assert diagramFacet.eResource() != null;
            diagramFacet.eResource().getContents().add(d);

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
            return BOMDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT;
        }

        /**
         * @generated
         */
        protected String getDiagramKind() {
            return CanvasPackageEditPart.MODEL_ID;
        }

        /**
         * @generated
         */
        protected String getEditorID() {
            return UMLDiagramEditor.ID;
        }
    }

}
