/**
 * 
 */
package com.tibco.xpd.bom.modeler.custom.actions;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Polyline;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.ui.actions.DiagramAction;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest.ViewAndElementDescriptor;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationClassDanglingNodeEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.CanvasPackageEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.PackageEditPart;
import com.tibco.xpd.bom.modeler.diagram.part.BOMDiagramEditorPlugin;
import com.tibco.xpd.bom.modeler.diagram.providers.UMLElementTypes;
import com.tibco.xpd.bom.modeler.diagram.requests.custom.RefactorToAssocClassCustomRequest;
import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.ui.BOMImages;
import com.tibco.xpd.bom.resources.ui.util.BomUIUtil;

/**
 * @author rgreen
 * 
 */
public class RefactorToAssocClassDiagramAction extends DiagramAction {

    private IWorkbenchPage page;

    public RefactorToAssocClassDiagramAction(IWorkbenchPage workbenchPage) {
        super(workbenchPage);
        page = workbenchPage;
        setText(Messages.RefactorToAssocClassDiagramAction_RefactorToAssociationClassLabel);
        Image img =
                Activator.getDefault().getImageRegistry().get(BOMImages.CLASS);

        setImageDescriptor(ImageDescriptor.createFromImage(img));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gmf.runtime.diagram.ui.actions.DiagramAction#createTargetRequest
     * ()
     */
    @Override
    protected Request createTargetRequest() {
        Request request = null;
        ISelection selection = page.getSelection();

        if (selection instanceof IStructuredSelection) {
            Object firstElement =
                    ((IStructuredSelection) selection).getFirstElement();

            if (firstElement instanceof AssociationEditPart) {
                AssociationEditPart assocEP =
                        (AssociationEditPart) firstElement;
                request = new RefactorToAssocClassCustomRequest(assocEP);
            }

        }
        return request;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gmf.runtime.diagram.ui.actions.DiagramAction#isSelectionListener
     * ()
     */
    @Override
    protected boolean isSelectionListener() {
        return true;
    }

    @Override
    protected Command getCommand(Request request) {

        IStructuredSelection selection = getStructuredSelection();
        Object element = selection.getFirstElement();

        AssociationEditPart assocEP;
        if (element instanceof AssociationEditPart) {
            assocEP = (AssociationEditPart) element;
        } else {
            return UnexecutableCommand.INSTANCE;
        }

        /*
         * This refactor is not supported in user diagrams
         */
        Object model = assocEP.getModel();
        if (model instanceof View
                && BomUIUtil.isUserDiagram(((View) model).getDiagram())) {
            return UnexecutableCommand.INSTANCE;
        }

        CompoundCommand command = new CompoundCommand(getCommandLabel());
        EditPart parent = assocEP.getParent();

        // We have to ask the associations container editpolicy for the command.
        // Therefore we need to find the association's container edit part.
        EObject eo = assocEP.resolveSemanticElement();

        if (eo instanceof Association) {

            // Retrieve the semantic elements we need from the Association
            Association assoc = (Association) eo;
            Property sourceEnd = assoc.getMemberEnds().get(0);
            Property targetEnd = assoc.getMemberEnds().get(1);

            // Remember we create the dangling node editpart and then
            // leave it to the parent packages CanonicalEditPolicy refresh
            // to create the rectangle node and connections.
            ViewAndElementDescriptor desc =
                    new ViewAndElementDescriptor(
                            new CreateElementRequestAdapter(
                                    new CreateElementRequest(
                                            UMLElementTypes.AssociationClass_1006)),
                            Node.class,
                            String
                                    .valueOf(AssociationClassDanglingNodeEditPart.VISUAL_ID),
                            BOMDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);

            CreateViewAndElementRequest req =
                    new CreateViewAndElementRequest(desc);

            // Set the location as the mid-point of the original
            // Association connection
            IFigure figure = assocEP.getFigure();
            Rectangle bounds = null;

            if (figure instanceof Polyline) {
                Polyline pl = (Polyline) figure;
                bounds = pl.getPoints().getBounds();

                if (bounds != null) {
                    Point location =
                            new Point((bounds.x + (bounds.width / 2)),
                                    (bounds.y + (bounds.height / 2)));
                    // Take editor's zoom level into account
                    figure.translateToAbsolute(location);
                    req.setLocation(location);
                }

            }

            // Need to pass through the AssociationClass endmembers to
            // the AssociationClassCreateCommand via the extended
            // attributes
            Map<Object, Object> extData = new HashMap<Object, Object>();

            extData.put("sourceEnd", sourceEnd); //$NON-NLS-1$
            extData.put("targetEnd", targetEnd); //$NON-NLS-1$
            extData.put("name", assoc.getName()); //$NON-NLS-1$

            req.setExtendedData(extData);

            EditPartViewer viewer = assocEP.getViewer();
            Map editPartRegistry = viewer.getEditPartRegistry();
            Collection values = editPartRegistry.values();

            // We're going to create the association class in the association's
            // parent package
            EObject container = assoc.eContainer();

            for (Object object : values) {

                if (!(object instanceof CanvasPackageEditPart)
                        && !(object instanceof PackageEditPart)) {
                    continue;
                }

                if (object instanceof GraphicalEditPart) {
                    GraphicalEditPart ep = (GraphicalEditPart) object;
                    EObject elem = ep.resolveSemanticElement();

                    if (elem != null) {
                        if (container == elem) {
                            Command curCommand = ep.getCommand(req);

                            if (curCommand != null) {
                                command.add(curCommand);

                                // Now destroy the Association
                                DestroyElementRequest destroyReq =
                                        new DestroyElementRequest(assoc, false);

                                ICommand destroyCommand =
                                        ElementTypeRegistry.getInstance()
                                                .getElementType(eo)
                                                .getEditCommand(destroyReq);

                                if (destroyCommand != null) {
                                    command.add(new ICommandProxy(
                                            destroyCommand));
                                }

                                break;

                            }

                        }
                    }

                }

            }

        }

        return command.isEmpty() ? UnexecutableCommand.INSTANCE
                : (Command) command;

    }

    @Override
    protected String getCommandLabel() {
        return Messages.RefactorToAssocClassDiagramAction_RefactorToAssociationClassComandLabel;
    }

}
