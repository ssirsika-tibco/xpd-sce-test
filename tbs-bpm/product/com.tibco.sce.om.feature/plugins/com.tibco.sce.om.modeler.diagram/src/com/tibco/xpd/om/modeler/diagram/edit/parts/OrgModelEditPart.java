package com.tibco.xpd.om.modeler.diagram.edit.parts;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.util.ObjectAdapter;
import org.eclipse.gmf.runtime.diagram.ui.actions.ActionIds;
import org.eclipse.gmf.runtime.diagram.ui.commands.DeferredLayoutCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.internal.commands.SnapCommand;
import org.eclipse.gmf.runtime.diagram.ui.internal.properties.WorkspaceViewerProperties;
import org.eclipse.gmf.runtime.diagram.ui.internal.services.layout.IInternalLayoutRunnable;
import org.eclipse.gmf.runtime.diagram.ui.internal.services.layout.LayoutNode;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramGraphicalViewer;
import org.eclipse.gmf.runtime.diagram.ui.requests.ArrangeRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.diagram.ui.services.layout.LayoutType;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.preference.IPreferenceStore;

import com.tibco.xpd.om.core.om.DynamicOrgReference;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgMetaModel;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.OrgUnitRelationshipType;
import com.tibco.xpd.om.core.om.OrganizationType;
import com.tibco.xpd.om.modeler.diagram.edit.policies.OrgModelCanonicalEditPolicy;
import com.tibco.xpd.om.modeler.diagram.edit.policies.OrgModelItemSemanticEditPolicy;
import com.tibco.xpd.om.modeler.diagram.part.custom.OrganizationModelCustomPaletteFactory;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.custom.BadgeEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.custom.OMDiagramEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.policies.custom.OMContainerEditPolicy;

public class OrgModelEditPart extends OMDiagramEditPart {

    /**
     * @generated
     */
    public final static String MODEL_ID = "Organization Model"; //$NON-NLS-1$

    /**
     * @generated
     */
    public static final int VISUAL_ID = 79;

    /**
     * @generated
     */
    public OrgModelEditPart(View view) {
        super(view);
    }

    /**
     * @generated NOT
     */
    @Override
    protected void createDefaultEditPolicies() {
        super.createDefaultEditPolicies();
        installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
                new OrgModelItemSemanticEditPolicy());
        installEditPolicy(EditPolicyRoles.CANONICAL_ROLE,
                new OrgModelCanonicalEditPolicy());
        // removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.
        // EditPolicyRoles.POPUPBAR_ROLE);

        removeEditPolicy(EditPolicy.CONTAINER_ROLE);
        installEditPolicy(EditPolicy.CONTAINER_ROLE,
                new ModifiedContainerRoleEditPolicy());

    }

    /**
     * 
     * 
     * @generated NOT
     * 
     *            Forces a semanticRefresh for the canvas.
     * 
     *            Required when adding supertypes via the property page button.
     *            Adding a generalization by other means than through the UI
     *            tool would not force a refresh of connections. This method has
     *            been seperated out from the refresh() method so ass not to
     *            interfere with other connection creations e.e. a side-affect
     *            was that two association connections would be drawn between
     *            classes not inside the main canvas.
     * 
     */
    public void refreshDiagramConnections() {
        OrgModelCanonicalEditPolicy epol =
                (OrgModelCanonicalEditPolicy) getEditPolicy(EditPolicyRoles.CANONICAL_ROLE);
        epol.refresh();
    }

    @Override
    public void refresh() {
        super.refresh();

        // If the Organization is typed then we need to build a palette to show
        // all the creation tools for the different typed entities
        EObject elem = resolveSemanticElement();
        if (elem instanceof OrgModel) {
            OrgModel om = (OrgModel) elem;
            PaletteRoot paletteRoot = null;

            if (getEditDomain().getPaletteViewer() != null) {
                paletteRoot =
                        getEditDomain().getPaletteViewer().getPaletteRoot();
            }

            if (paletteRoot != null) {
                new OrganizationModelCustomPaletteFactory()
                        .fillPalette(paletteRoot, om);
            }

        }

    }

    @Override
    protected void addSemanticListeners() {
        super.addSemanticListeners();

        EObject elem = resolveSemanticElement();

        if (elem instanceof OrgModel) {
            OrgModel om = (OrgModel) elem;
            OrgMetaModel embeddedMetamodel = om.getEmbeddedMetamodel();

            if (embeddedMetamodel != null) {
                addListenerFilter("SemanticElement", this, embeddedMetamodel);//$NON-NLS-1$

                // And add all the schemas types and features already defined
                // that may require palette entries
                EList<EObject> contents = embeddedMetamodel.eContents();
                for (EObject eo : contents) {
                    if (isTypeAffectingPalette(eo)) {
                        addListenerFilter("SemanticElement", this, eo);//$NON-NLS-1$ 
                    }
                }
            }

        }

    }

    private boolean isTypeAffectingPalette(EObject eo) {
        if (eo instanceof OrganizationType
                || eo instanceof OrgUnitRelationshipType) {
            return true;
        }
        return false;
    }

    @Override
    protected void handleNotificationEvent(Notification event) {
        super.handleNotificationEvent(event);
        Object obj = event.getNotifier();

        // We need to do a whole series of checks to see if we need to refresh
        // the diagram palette and/or add to the semantic listener list i.e. if
        // there have been changes to the embedded
        // schema.
        if (obj instanceof Notifier) {
            Notifier notifier = (Notifier) obj;
            int eventType = event.getEventType();

            if (notifier instanceof OrgMetaModel) {
                if (eventType == Notification.ADD) {
                    // May need to add this new type to the palette
                    Object newValue = event.getNewValue();
                    if (newValue instanceof OrganizationType
                            || newValue instanceof OrgUnitRelationshipType) {
                        addListenerFilter("SemanticElement", this, (EObject) newValue);//$NON-NLS-1$                                            
                    }
                    if (newValue instanceof OrganizationType
                            || newValue instanceof OrgUnitRelationshipType) {
                        refresh();
                    }
                }
                if (eventType == Notification.REMOVE) {
                    Object oldValue = event.getOldValue();
                    if (oldValue instanceof OrganizationType
                            || oldValue instanceof OrgUnitRelationshipType) {
                        refresh();
                    }
                }
            }

            if (notifier instanceof OrganizationType
                    || notifier instanceof OrgUnitRelationshipType) {
                if (eventType == Notification.SET) {
                    // New value is a name change i.e. to the name feature
                    if (event.getFeature() == OMPackage.Literals.NAMED_ELEMENT__DISPLAY_NAME) {
                        refresh();
                    }
                }
            } else if (notifier instanceof DynamicOrgReference) {
                if (eventType == Notification.SET) {
                    refresh();
                }
            }

        }
    }

    class ModifiedContainerRoleEditPolicy extends OMContainerEditPolicy {

        /**
         * gets an arrange command
         * 
         * @param request
         * @return command
         */
        @Override
        protected Command getArrangeCommand(ArrangeRequest request) {

            if (RequestConstants.REQ_ARRANGE_DEFERRED.equals(request.getType())) {
                String layoutType = request.getLayoutType();
                TransactionalEditingDomain editingDomain =
                        ((IGraphicalEditPart) getHost()).getEditingDomain();
                return new ICommandProxy(new DeferredLayoutCommand(
                        editingDomain, request.getViewAdaptersToArrange(),
                        (IGraphicalEditPart) getHost(), layoutType));
            }

            String layoutDesc =
                    request.getLayoutType() != null ? request.getLayoutType()
                            : LayoutType.DEFAULT;
            boolean offsetFromBoundingBox = false;
            List editparts = new ArrayList();

            if ((ActionIds.ACTION_ARRANGE_ALL.equals(request.getType()))
                    || (ActionIds.ACTION_TOOLBAR_ARRANGE_ALL.equals(request
                            .getType()))) {

                List editpartsAll = new ArrayList();
                editpartsAll = ((IGraphicalEditPart) getHost()).getChildren();

                for (Object ep : editpartsAll) {
                    if (ep instanceof BadgeEditPart) {
                        continue;
                    } else {
                        editparts.add(ep);
                    }
                }

                request.setPartsToArrange(editparts);
            }
            if ((ActionIds.ACTION_ARRANGE_SELECTION.equals(request.getType()))
                    || (ActionIds.ACTION_TOOLBAR_ARRANGE_SELECTION
                            .equals(request.getType()))) {
                editparts = request.getPartsToArrange();
                if (editparts.size() < 2
                        || !(((GraphicalEditPart) ((EditPart) editparts.get(0))
                                .getParent()).getContentPane()
                                .getLayoutManager() instanceof XYLayout)) {
                    return null;
                }
                offsetFromBoundingBox = true;
            }
            if (RequestConstants.REQ_ARRANGE_RADIAL.equals(request.getType())) {
                editparts = request.getPartsToArrange();
                offsetFromBoundingBox = true;
                layoutDesc = LayoutType.RADIAL;
            }
            if (editparts.isEmpty())
                return null;

            // Get rid of the Badge edit part

            List nodes = new ArrayList(editparts.size());
            ListIterator li = editparts.listIterator();
            while (li.hasNext()) {
                IGraphicalEditPart ep = (IGraphicalEditPart) li.next();
                View view = ep.getNotationView();
                if (ep.isActive() && view != null && view instanceof Node) {
                    Rectangle bounds = ep.getFigure().getBounds();
                    nodes.add(new LayoutNode((Node) view, bounds.width,
                            bounds.height));
                }
            }
            if (nodes.isEmpty()) {
                return null;
            }

            List hints = new ArrayList(2);
            hints.add(layoutDesc);
            hints.add(getHost());
            IAdaptable layoutHint = new ObjectAdapter(hints);

            offsetFromBoundingBox = true;

            final Runnable layoutRun =
                    layoutNodes(nodes, offsetFromBoundingBox, layoutHint);

            boolean isSnap = true;
            // retrieves the preference store from the first edit part
            IGraphicalEditPart firstEditPart =
                    (IGraphicalEditPart) editparts.get(0);
            if (firstEditPart.getViewer() instanceof DiagramGraphicalViewer) {
                IPreferenceStore preferenceStore =
                        ((DiagramGraphicalViewer) firstEditPart.getViewer())
                                .getWorkspaceViewerPreferenceStore();
                if (preferenceStore != null) {
                    isSnap =
                            preferenceStore
                                    .getBoolean(WorkspaceViewerProperties.SNAPTOGRID);
                }
            }

            // the snapCommand still invokes proper calculations if snap to grid
            // is turned off, this additional check
            // is intended to make the code more appear more logical

            CompoundCommand cmd = new CompoundCommand();
            if (layoutRun instanceof IInternalLayoutRunnable) {
                cmd.add(((IInternalLayoutRunnable) layoutRun).getCommand());
            } else {
                TransactionalEditingDomain editingDomain =
                        ((IGraphicalEditPart) getHost()).getEditingDomain();
                cmd.add(new ICommandProxy(new AbstractTransactionalCommand(
                        editingDomain, "", null) {//$NON-NLS-1$
                            @Override
                            protected CommandResult doExecuteWithResult(
                                    IProgressMonitor progressMonitor,
                                    IAdaptable info) throws ExecutionException {
                                layoutRun.run();
                                return CommandResult.newOKCommandResult();
                            }
                        }));
            }
            if (isSnap) {
                cmd.add(getSnapCommand(request));
            }
            return cmd;
        }

        private Command getSnapCommand(Request request) {

            List editparts = null;
            if (request instanceof GroupRequest) {
                editparts = ((GroupRequest) request).getEditParts();
            } else if (request instanceof ArrangeRequest) {
                editparts = ((ArrangeRequest) request).getPartsToArrange();
            }

            TransactionalEditingDomain editingDomain =
                    ((IGraphicalEditPart) getHost()).getEditingDomain();
            if (editparts != null) {
                return new ICommandProxy(new SnapCommand(editingDomain,
                        editparts));
            }
            return null;
        }

    }

}
