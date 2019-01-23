package com.tibco.xpd.bom.modeler.diagram.internal.clipboard;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.ui.action.global.GlobalActionId;
import org.eclipse.gmf.runtime.common.ui.services.action.global.IGlobalActionContext;
import org.eclipse.gmf.runtime.common.ui.util.CustomData;
import org.eclipse.gmf.runtime.common.ui.util.ICustomData;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.commands.CommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramRootEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramCommandStack;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart;
import org.eclipse.gmf.runtime.diagram.ui.providers.DiagramGlobalActionHandler;
import org.eclipse.gmf.runtime.diagram.ui.render.clipboard.AWTClipboardHelper;
import org.eclipse.gmf.runtime.diagram.ui.requests.EditCommandRequestWrapper;
import org.eclipse.gmf.runtime.diagram.ui.requests.PasteViewRequest;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.MapModeUtil;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.xpd.bom.modeler.diagram.edit.parts.BadgeEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.EnumerationLiteralEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.OperationEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.PropertyEditPart;
import com.tibco.xpd.bom.modeler.diagram.part.Messages;
import com.tibco.xpd.bom.resources.ui.clipboard.BOMCopyPasteCommandFactory;

public class BOMGlobalActionHandler extends DiagramGlobalActionHandler {

    // Location of where paste should go
    private Point pasteLocation;

    public ICommand getCommand(IGlobalActionContext cntxt) {

        /* Check if the active part is a IDiagramWorkbenchPart */
        IWorkbenchPart part = cntxt.getActivePart();
        if (!(part instanceof IDiagramWorkbenchPart)) {
            return null;
        }

        /* Get the model operation context */
        IDiagramWorkbenchPart diagramPart = (IDiagramWorkbenchPart) part;

        String actionId = cntxt.getActionId();

        if (actionId.equals(GlobalActionId.DELETE)) {
            return getDeleteCommand(getEditingDomain(diagramPart), cntxt);
        }
        if (actionId.equals(GlobalActionId.PASTE)) {
            TransactionalEditingDomain editingDomain =
                    getEditingDomain(diagramPart);

            PasteViewRequest pasteReq = createPasteViewRequest();

            /* Get the selected edit parts */
            Object[] objects =
                    ((IStructuredSelection) cntxt.getSelection()).toArray();

            if (objects.length == 1 && objects[0] instanceof GraphicalEditPart) {
                GraphicalEditPart editPart = (GraphicalEditPart) objects[0];
                View view = (View) editPart.getAdapter(View.class);

                ICommand paste;

                if (pasteLocation != null) {
                    paste =
                            BOMCopyPasteCommandFactory
                                    .getInstance()
                                    .createPasteCommand(editingDomain,
                                            view,
                                            pasteReq.getData(),
                                            MapModeUtil.getMapMode(editPart
                                                    .getFigure()),
                                            new org.eclipse.draw2d.geometry.Point(
                                                    pasteLocation.x,
                                                    pasteLocation.y));
                } else {
                    paste =
                            BOMCopyPasteCommandFactory.getInstance()
                                    .createPasteCommand(editingDomain,
                                            view,
                                            pasteReq.getData(),
                                            MapModeUtil.getMapMode(editPart
                                                    .getFigure()));
                }

                /* Send the request to the currently selected part */
                // Command paste = ((EditPart) objects[0]).getCommand(pasteReq);
                if (paste != null) {
                    /* Set the command */
                    CommandStack cs =
                            diagramPart.getDiagramEditDomain()
                                    .getDiagramCommandStack();
                    cs.execute(new ICommandProxy(paste));

                    CommandResult result = paste.getCommandResult();

                    if (result == null || result.getStatus().isOK()) {
                        diagramPart.getDiagramEditPart().getFigure()
                                .invalidate();
                        diagramPart.getDiagramEditPart().getFigure().validate();
                        selectAddedObject(diagramPart
                                .getDiagramGraphicalViewer(),
                                DiagramCommandStack.getReturnValues(paste));
                    } else if (result != null
                            && result.getStatus().getSeverity() == IStatus.ERROR) {
                        ErrorDialog
                                .openError(diagramPart.getSite().getShell(),
                                        Messages.BOMGlobalActionHandler_failedToPaste_errorDialog_title,
                                        Messages.BOMGlobalActionHandler_failedToPaste_errorDialog_message,
                                        result.getStatus());
                    }
                    return null;
                }
            }
        }

        return super.getCommand(cntxt);
    }

    /**
     * Get the command to delete the selected edit parts from the diagram. This
     * behaves in the same manner the "Delete From Model" context menu does.
     * 
     * @param ed
     * @param cntxt
     * @return
     */
    private ICommand getDeleteCommand(TransactionalEditingDomain ed,
            IGlobalActionContext cntxt) {
        EditCommandRequestWrapper req =
                new EditCommandRequestWrapper(new DestroyElementRequest(ed,
                        false));
        CompositeTransactionalCommand command =
                new CompositeTransactionalCommand(ed, cntxt.getLabel());
        for (Iterator<?> iter =
                ((IStructuredSelection) cntxt.getSelection()).iterator(); iter
                .hasNext();) {
            Object next = iter.next();
            if (next instanceof EditPart) {
                EditPart editPart = (EditPart) next;
                // disable on diagram links
                if (editPart instanceof IGraphicalEditPart) {
                    IGraphicalEditPart gEditPart =
                            (IGraphicalEditPart) editPart;
                    View view = (View) gEditPart.getModel();
                    // Don't delete diagram from model only if it is the top
                    // most diagram
                    EObject container = view.eContainer();
                    EObject element = ViewUtil.resolveSemanticElement(view);
                    if ((element instanceof Diagram)
                            || (view instanceof Diagram && (container == null || !(container instanceof View)))) {
                        return null;
                    }
                }
                Command curCommand = editPart.getCommand(req);
                if (curCommand != null) {
                    command.compose(new CommandProxy(curCommand));
                }
            }
        }
        return command;
    }

    @Override
    protected ICommand getCopyCommand(IGlobalActionContext cntxt,
            final IDiagramWorkbenchPart diagramPart, final boolean isUndoable) {
        ICommand cmd = null;
        TransactionalEditingDomain editingDomain =
                getEditingDomain(diagramPart);
        IStructuredSelection currSel =
                (IStructuredSelection) cntxt.getSelection();
        List<Object> newSel = new BasicEList<Object>();
        for (Iterator<?> iter = currSel.iterator(); iter.hasNext();) {
            Object next = iter.next();
            if (next instanceof EditPart
                    && !isBadgeOrChildOfBadgeEditPart((EditPart) next)) {
                newSel.add(next);
            }
        }
        if (editingDomain != null) {
            cmd =
                    BOMCopyPasteCommandFactory.getInstance()
                            .createCopyImageCommand(editingDomain,
                                    diagramPart.getDiagram(),
                                    getSelectedViews(new StructuredSelection(
                                            newSel)),
                                    diagramPart.getDiagramEditPart(),
                                    isUndoable);
        }

        return cmd;
    }

    @Override
    protected boolean canCopy(IGlobalActionContext cntxt) {
        ISelection selection = cntxt.getSelection();

        if (selection instanceof IStructuredSelection) {
            // All selected elements should be from the same parent
            EditPart parent = null;
            for (Iterator<?> iter =
                    ((IStructuredSelection) selection).iterator(); iter
                    .hasNext();) {
                Object next = iter.next();

                if (next instanceof EditPart
                        && !(next instanceof ConnectionNodeEditPart)) {

                    EditPart myParent = ((EditPart) next).getParent();
                    if (myParent instanceof DiagramRootEditPart) {
                        myParent =
                                ((DiagramRootEditPart) myParent).getContents();
                    }

                    if (parent == null) {
                        parent = myParent;
                    } else if (parent != myParent) {
                        return false;
                    }
                }
            }

            for (Iterator<?> iter =
                    ((IStructuredSelection) selection).iterator(); iter
                    .hasNext();) {
                Object next = iter.next();

                if (next instanceof EditPart) {
                    if (canCopy((EditPart) next)) {
                        return super.canCopy(cntxt);
                    }
                }
            }
        }

        return false;
    }

    /**
     * Check if the given {@link EditPart} can be copied. The follow edit parts
     * cannot be copied:
     * <ul>
     * <li><code>BadgeEditPart</code> or any of its children</li>
     * <li><code>OrgUnitEditPart</code></li>
     * </ul>
     * 
     * @param editPart
     * @return <code>true</code> if it can be copied.
     */
    private boolean canCopy(EditPart editPart) {
        /*
         * Cannot copy any badge edit parts or OrgUnits on the top level
         * diagram.
         */
        if (isBadgeOrChildOfBadgeEditPart(editPart)) {
            return false;
        }
        return true;
    }

    /**
     * Check if the given edit part is a badge or child of a badge edit part.
     * 
     * @param editPart
     * @return
     */
    private boolean isBadgeOrChildOfBadgeEditPart(EditPart editPart) {

        if (editPart != null) {
            if (editPart instanceof BadgeEditPart) {
                return true;
            } else if (editPart.getParent() != null) {
                return isBadgeOrChildOfBadgeEditPart(editPart.getParent());
            }
        }

        return false;
    }

    protected boolean canPaste(IGlobalActionContext cntxt) {
        boolean hasCustomData = false;
        // Get the paste location (point where context menu was opened)
        pasteLocation = getLocation(cntxt);

        ISelection selection = cntxt.getSelection();

        if (selection instanceof StructuredSelection) {
            StructuredSelection sel = (StructuredSelection) selection;

            List<?> list = sel.toList();

            if (list.size() > 1) {
                return false;
            } else if (list.size() == 1) {
                Object obj = list.get(0);

                if (obj instanceof EditPart) {
                    if (isBadgeOrChildOfBadgeEditPart((EditPart) obj)) {
                        return false;
                    }
                }
            }

        }

        // Check if there is custom data for image copy
        if (AWTClipboardHelper.getInstance().isImageCopySupported()) {
            hasCustomData = AWTClipboardHelper.getInstance().hasCustomData();
        }

        if (!hasCustomData) {
            // No image copy so check standard copy
            hasCustomData = super.canPaste(cntxt);
        }

        if (hasCustomData) {

        }

        return hasCustomData;
    }

    /**
     * Get cursor location to use as the paste target. This will return a point
     * if the context menu was used to paste (relative to the target edit part).
     * 
     * @param context
     * @return point if context menu was used, <code>null</code> otherwise.
     */
    private Point getLocation(IGlobalActionContext context) {
        Point p = null;

        if (context != null
                && context.getActivePart() instanceof IDiagramWorkbenchPart) {
            IDiagramWorkbenchPart part =
                    (IDiagramWorkbenchPart) context.getActivePart();
            if (part.getDiagramGraphicalViewer() != null) {
                MenuManager manager =
                        part.getDiagramGraphicalViewer().getContextMenu();
                if (manager != null && manager.getMenu() != null
                        && manager.getMenu().isVisible()) {
                    p =
                            manager.getMenu().getShell().getDisplay()
                                    .getCursorLocation();
                    p =
                            part.getDiagramGraphicalViewer().getControl()
                                    .toControl(p);

                    /*
                     * Get the location relative to the target edit part
                     */
                    EditPart editPart =
                            part
                                    .getDiagramGraphicalViewer()
                                    .findObjectAt(new org.eclipse.draw2d.geometry.Point(
                                            p.x, p.y));
                    if (editPart instanceof GraphicalEditPart) {
                        IFigure figure =
                                ((GraphicalEditPart) editPart).getFigure();
                        if (figure != null) {
                            org.eclipse.draw2d.geometry.Point point =
                                    new org.eclipse.draw2d.geometry.Point(p.x,
                                            p.y);
                            figure.translateToRelative(point);

                            Rectangle bounds = figure.getBounds();

                            point.performTranslate(-bounds.x
                                    - figure.getInsets().right, -bounds.y
                                    - figure.getInsets().bottom);
                            p.x = point.x;
                            p.y = point.y;
                        }
                    }
                }
            }

        }

        return p;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gmf.runtime.diagram.ui.providers.DiagramGlobalActionHandler
     * #createPasteViewRequest()
     */
    protected PasteViewRequest createPasteViewRequest() {

        // If image copy is supported and there is custom data for image copy
        // then use that
        if (AWTClipboardHelper.getInstance().isImageCopySupported()) {
            CustomData data = AWTClipboardHelper.getInstance().getCustomData();

            if (data != null) {
                return new PasteViewRequest(new ICustomData[] { data });
            }
        }

        return super.createPasteViewRequest();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected List<?> getSelectedViews(ISelection sel) {
        List<Object> selectedViews = super.getSelectedViews(sel);

        for (Iterator<?> iter = ((IStructuredSelection) sel).iterator(); iter
                .hasNext();) {
            Object next = iter.next();

            View view = null;

            if (next instanceof PropertyEditPart) {
                view = (View) ((PropertyEditPart) next).getAdapter(View.class);
            } else if (next instanceof OperationEditPart) {
                view = (View) ((OperationEditPart) next).getAdapter(View.class);
            } else if (next instanceof EnumerationLiteralEditPart) {
                view =
                        (View) ((EnumerationLiteralEditPart) next)
                                .getAdapter(View.class);
            }

            if (view != null) {
                if (view.eResource() != null) {
                    selectedViews.add(view);
                }
            }
        }

        return selectedViews;
    }

    /**
     * Gets the transactional editing domain associated with the workbench
     * <code>part</code>.
     * 
     * @param part
     *            the diagram workbench part
     * @return the editing domain, or <code>null</code> if there is none.
     */
    private TransactionalEditingDomain getEditingDomain(
            IDiagramWorkbenchPart part) {

        TransactionalEditingDomain result = null;

        IEditingDomainProvider provider =
                (IEditingDomainProvider) part
                        .getAdapter(IEditingDomainProvider.class);

        if (provider != null) {
            EditingDomain domain = provider.getEditingDomain();

            if (domain != null && domain instanceof TransactionalEditingDomain) {
                result = (TransactionalEditingDomain) domain;
            }
        }

        return result;
    }

}
