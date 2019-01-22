/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.modeler.custom.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.CopyToClipboardCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.EMFCommandOperation;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.ui.action.actions.global.ClipboardContentsHelper;
import org.eclipse.gmf.runtime.common.ui.action.actions.global.ClipboardManager;
import org.eclipse.gmf.runtime.common.ui.action.global.GlobalActionId;
import org.eclipse.gmf.runtime.common.ui.services.action.global.AbstractGlobalActionHandlerProvider;
import org.eclipse.gmf.runtime.common.ui.services.action.global.IGlobalActionContext;
import org.eclipse.gmf.runtime.common.ui.services.action.global.IGlobalActionHandler;
import org.eclipse.gmf.runtime.common.ui.services.action.global.IGlobalActionHandlerContext;
import org.eclipse.gmf.runtime.common.ui.util.ICustomData;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.commands.CommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramRootEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart;
import org.eclipse.gmf.runtime.diagram.ui.providers.DiagramGlobalActionHandler;
import org.eclipse.gmf.runtime.diagram.ui.requests.EditCommandRequestWrapper;
import org.eclipse.gmf.runtime.diagram.ui.requests.PasteViewRequest;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.xpd.om.modeler.custom.internal.Messages;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrgUnitEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrganizationEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.PositionSubEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.custom.BadgeEditPart;
import com.tibco.xpd.om.resources.ui.clipboard.OMClipboardHelper;

/**
 * Organization Modeler's global action handler that overrides the default CUT,
 * COPY and PASTE actions.
 * 
 * @author njpatel
 * 
 */
public class OMGlobalEditActionHandler extends
        AbstractGlobalActionHandlerProvider {

    @Override
    public IGlobalActionHandler getGlobalActionHandler(
            IGlobalActionHandlerContext context) {
        return handler;
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

    private IGlobalActionHandler handler = new DiagramGlobalActionHandler() {

        // Register the location to paste in the editor
        private Point pastePosition;

        @Override
        public ICommand getCommand(IGlobalActionContext cntxt) {
            /* Check if the active part is a IDiagramWorkbenchPart */
            IWorkbenchPart part = cntxt.getActivePart();
            if (!(part instanceof IDiagramWorkbenchPart)) {
                return null;
            }

            /* Get the model operation context */
            IDiagramWorkbenchPart diagramPart = (IDiagramWorkbenchPart) part;

            String actionId = cntxt.getActionId();

            // Over-ride the delete action
            if (actionId.equals(GlobalActionId.DELETE)) {
                return getDeleteCommand(getEditingDomain(diagramPart), cntxt);
            }
            return super.getCommand(cntxt);
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

                    if (next instanceof EditPart) {
                        EditPart myParent = ((EditPart) next).getParent();
                        if (myParent instanceof DiagramRootEditPart) {
                            myParent =
                                    ((DiagramRootEditPart) myParent)
                                            .getContents();
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
         * Check if the given {@link EditPart} can be copied. The follow edit
         * parts cannot be copied:
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
            if (isBadgeOrChildOfBadgeEditPart(editPart)
                    || editPart instanceof OrgUnitEditPart) {
                return false;
            }
            return true;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected List getSelectedViews(ISelection sel) {
            if (sel instanceof IStructuredSelection) {
                List<Object> selectionWithoutBadges = new ArrayList<Object>();
                // Remove any edit parts that cannot be copied from the
                // selection
                for (Iterator<Object> iter =
                        ((IStructuredSelection) sel).iterator(); iter.hasNext();) {
                    Object next = iter.next();
                    if (next instanceof EditPart) {
                        if (canCopy((EditPart) next)) {
                            selectionWithoutBadges.add(next);
                        }
                    } else if (next != null) {
                        selectionWithoutBadges.add(next);
                    }
                }
                // Get the selected views
                List<Object> selectedViews =
                        super.getSelectedViews(new StructuredSelection(
                                selectionWithoutBadges));

                // The super.selectedViews will ignore position nodes so add to
                // list
                for (Object selectedItem : selectionWithoutBadges) {
                    if (selectedItem instanceof PositionSubEditPart) {
                        View view =
                                (View) ((PositionSubEditPart) selectedItem)
                                        .getAdapter(View.class);

                        if (view != null && view.eResource() != null) {
                            selectedViews.add(view);
                        }
                    }
                }
                return selectedViews;
            }

            return super.getSelectedViews(sel);
        }

        @Override
        protected ICommand getCopyCommand(IGlobalActionContext cntxt,
                IDiagramWorkbenchPart diagramPart, boolean isUndoable) {
            ICommand cmd = null;
            TransactionalEditingDomain editingDomain =
                    getEditingDomain(diagramPart);

            if (editingDomain != null) {
                List<?> views = getSelectedViews(cntxt.getSelection());

                cmd =
                        OMClipboardHelper
                                .getInstance()
                                .getCopyWithImageCommand(editingDomain,
                                        Messages.OMGlobalEditActionHandler_copy_action,
                                        diagramPart.getDiagram(),
                                        diagramPart.getDiagramEditPart(),
                                        views,
                                        isUndoable);

                /*
                 * Also make a copy of the selected semantic elements as the
                 * user may select to paste back in the project explorer rather
                 * than the diagram.
                 */
                List<EObject> elements = getSelectedSemanticElements(views);
                if (!elements.isEmpty()) {
                    CompositeCommand ccmd =
                            new CompositeCommand(
                                    Messages.OMGlobalEditActionHandler_copy_action);
                    ccmd.add(cmd);
                    ccmd.add(new EMFCommandOperation(editingDomain,
                            CopyToClipboardCommand.create(editingDomain,
                                    elements)));
                    return ccmd;
                }
            }

            return cmd;
        }

        @Override
        protected boolean canPaste(IGlobalActionContext cntxt) {
            // Get the paste location (point where context menu was opened)
            pastePosition = getLocation(cntxt);

            if (cntxt.getSelection() instanceof IStructuredSelection) {
                IStructuredSelection selection =
                        (IStructuredSelection) cntxt.getSelection();
                // Can only paste if one item selected
                if (selection.size() == 1) {
                    Object target = selection.getFirstElement();

                    if (target instanceof EditPart) {
                        // Cannot paste into badge edit parts or also
                        // OrganizationEditParts
                        /*
                         * XPD-7211: Do not allow paste to 'PositionSubEditPart'
                         * as we should not be able to paste if position is the
                         * selected target.
                         */
                        if (isBadgeOrChildOfBadgeEditPart((EditPart) target)
                                || target instanceof OrganizationEditPart
                                || target instanceof PositionSubEditPart) {
                            return false;
                        }
                        // If we have a hint in the clipboard then check that
                        if (OMClipboardHelper.hasHint()) {
                            Object model = ((EditPart) target).getModel();
                            if (model instanceof View) {
                                Diagram diagram = ((View) model).getDiagram();
                                if (diagram != null) {
                                    String hint =
                                            OMClipboardHelper
                                                    .getViewHint(diagram);

                                    if (hint != null) {
                                        // Get the visual id of the target and
                                        // check whether the hint is it or it's
                                        // parent
                                        if (!OMClipboardHelper
                                                .canPaste((View) model, hint)) {
                                            return false;
                                        }
                                    }
                                }
                            }
                        }
                    }

                } else {
                    return false;
                }
            }

            return ClipboardManager.getInstance()
                    .doesClipboardHaveData(OMClipboardHelper.DRAWING_SURFACE,
                            ClipboardContentsHelper.getInstance())
                    || (ClipboardManager.getInstance()
                            .doesClipboardHaveData(ClipboardManager.COMMON_FORMAT,
                                    ClipboardContentsHelper.getInstance()));
        }

        @Override
        protected PasteViewRequest createPasteViewRequest() {
            ICustomData[] data =
                    ClipboardManager
                            .getInstance()
                            .getClipboardData(OMClipboardHelper.DRAWING_SURFACE,
                                    ClipboardContentsHelper.getInstance());
            if (data != null) {
                PasteViewRequest req = new PasteViewRequest(data);

                if (OMClipboardHelper.hasHint()) {
                    String hint = OMClipboardHelper.getHint();

                    if (hint != null) {
                        Map<String, Object> extData =
                                new HashMap<String, Object>();
                        extData.put(OMClipboardHelper.OM_HINT, hint);
                        if (pastePosition != null) {
                            extData.put(OMClipboardHelper.PASTE_POSITION,
                                    pastePosition);
                        }
                        req.setExtendedData(extData);
                    }
                }
                return req;
            }
            return super.createPasteViewRequest();
        }

        /**
         * Get cursor location to use as the paste target. This will return a
         * point if the context menu was used to paste (relative to the target
         * edit part).
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
                                part.getDiagramGraphicalViewer()
                                        .findObjectAt(new org.eclipse.draw2d.geometry.Point(
                                                p.x, p.y));
                        if (editPart instanceof GraphicalEditPart) {
                            IFigure figure =
                                    ((GraphicalEditPart) editPart).getFigure();
                            if (figure != null) {
                                org.eclipse.draw2d.geometry.Point point =
                                        new org.eclipse.draw2d.geometry.Point(
                                                p.x, p.y);
                                figure.translateToRelative(point);

                                Rectangle bounds = figure.getBounds();

                                point.performTranslate(-bounds.x
                                        - figure.getInsets().right,
                                        -bounds.y - figure.getInsets().bottom);
                                p.x = point.x;
                                p.y = point.y;
                            }
                        }
                    }
                }

            }

            return p;
        }

        /**
         * Check if the given edit part is a badge or child of a badge edit
         * part.
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

        /**
         * Get the underlying semantic elements of the selected views.
         * 
         * @param views
         * @return
         */
        private List<EObject> getSelectedSemanticElements(List<?> views) {
            List<EObject> objs = new ArrayList<EObject>();

            if (views != null) {
                for (Object view : views) {
                    if (view instanceof View) {
                        EObject eo = ((View) view).getElement();
                        if (eo != null) {
                            objs.add(eo);
                        }
                    }
                }
            }

            return objs;
        }

        /**
         * Get the command to delete the selected edit parts from the diagram.
         * This behaves in the same manner the "Delete From Model" context menu
         * does.
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
    };
}
