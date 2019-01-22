package com.tibco.xpd.om.modeler.subdiagram.edit.parts;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.util.ObjectAdapter;
import org.eclipse.gmf.runtime.diagram.ui.actions.ActionIds;
import org.eclipse.gmf.runtime.diagram.ui.commands.DeferredLayoutCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.internal.commands.SnapCommand;
import org.eclipse.gmf.runtime.diagram.ui.internal.properties.WorkspaceViewerProperties;
import org.eclipse.gmf.runtime.diagram.ui.internal.services.layout.IInternalLayoutRunnable;
import org.eclipse.gmf.runtime.diagram.ui.internal.services.layout.LayoutNode;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramColorRegistry;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramGraphicalViewer;
import org.eclipse.gmf.runtime.diagram.ui.requests.ArrangeRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.diagram.ui.services.layout.LayoutType;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.FillStyle;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.Style;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.Color;

import com.tibco.xpd.om.core.om.Feature;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgElementType;
import com.tibco.xpd.om.core.om.OrgMetaModel;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.OrgUnitFeature;
import com.tibco.xpd.om.core.om.OrgUnitRelationshipType;
import com.tibco.xpd.om.core.om.OrgUnitType;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.OrganizationType;
import com.tibco.xpd.om.core.om.PositionFeature;
import com.tibco.xpd.om.core.om.PositionType;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.custom.OMDiagramEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.custom.OrganizationSubBadgeEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.policies.OrganizationSubCanonicalEditPolicy;
import com.tibco.xpd.om.modeler.subdiagram.edit.policies.OrganizationSubItemSemanticEditPolicy;
import com.tibco.xpd.om.modeler.subdiagram.edit.policies.custom.OMContainerEditPolicy;
import com.tibco.xpd.om.modeler.subdiagram.part.OrganizationModelPaletteFactory;
import com.tibco.xpd.om.modeler.subdiagram.part.custom.OrganizationTypePaletteFactory;
import com.tibco.xpd.om.modeler.subdiagram.preferences.custom.IOMSubPreferenceConstants;

public class OrganizationSubEditPart extends OMDiagramEditPart {

    /**
     * @generated
     */
    public final static String MODEL_ID = "Organization Model Sub"; //$NON-NLS-1$

    /**
     * @generated
     */
    public static final int VISUAL_ID = 79;

    /**
     * @generated
     */
    public OrganizationSubEditPart(View view) {
        super(view);
    }

    /**
     * @generated NOT
     */
    @Override
    protected void createDefaultEditPolicies() {
        super.createDefaultEditPolicies();
        installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
                new OrganizationSubItemSemanticEditPolicy());
        installEditPolicy(EditPolicyRoles.CANONICAL_ROLE,
                new OrganizationSubCanonicalEditPolicy());
        // removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.
        // EditPolicyRoles.POPUPBAR_ROLE);

        removeEditPolicy(EditPolicy.CONTAINER_ROLE);
        installEditPolicy(EditPolicy.CONTAINER_ROLE,
                new ModifiedContainerRoleEditPolicy());

        /*
         * XPD-5155: In Eclipse 3.7 dragging a position from one Organization
         * unit to another causes a NPE in the log. This is due to the
         * XYLayoutEditPolicy being asked to provide a command during the drag
         * of a Position and during the handling of this request an NPE gets
         * thrown. The end result seems to be OK but the log gets polluted with
         * a lot of exceptions. There seems to be some change in the way this
         * layout edit policy works in Eclipse 3.7.
         */
        removeEditPolicy(EditPolicy.LAYOUT_ROLE);
        installEditPolicy(EditPolicy.LAYOUT_ROLE, new XYLayoutEditPolicy() {
            /**
             * @see org.eclipse.gmf.runtime.diagram.ui.editpolicies.XYLayoutEditPolicy#getCommand(org.eclipse.gef.Request)
             * 
             * @param request
             * @return
             */
            @Override
            public Command getCommand(Request request) {
                if (request instanceof ChangeBoundsRequest) {
                    List<?> editParts =
                            ((ChangeBoundsRequest) request).getEditParts();

                    if (editParts != null) {
                        for (Object editPart : editParts) {
                            if (editPart instanceof PositionSubEditPart) {
                                return null;
                            }
                        }
                    }
                }
                return super.getCommand(request);
            }
        });

    }

    public void refreshDiagramConnections() {
        OrganizationSubCanonicalEditPolicy epol =
                (OrganizationSubCanonicalEditPolicy) getEditPolicy(EditPolicyRoles.CANONICAL_ROLE);
        epol.refresh();
    }

    @Override
    protected void refreshVisuals() {
        super.refreshVisuals();
        // Update the background colour
        IFigure fig = getFigure();
        if (fig != null) {
            Object value =
                    getStructuralFeatureValue(NotationPackage.eINSTANCE
                            .getFillStyle_FillColor());

            if (value instanceof Integer) {
                Color color =
                        DiagramColorRegistry.getInstance()
                                .getColor((Integer) value);
                fig.setBackgroundColor(color);
            }
        }
    }

    /*
     * Overide this method so that we can set background to opaque.
     * 
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart#createFigure
     * ()
     */
    @Override
    protected IFigure createFigure() {
        IFigure fig = super.createFigure();
        fig.setOpaque(true);
        return fig;
    }

    @Override
    protected void handleNotificationEvent(Notification event) {
        // TODO Auto-generated method stub
        super.handleNotificationEvent(event);
        Object notifier = event.getNotifier();

        if ((event.getEventType() == Notification.SET)
                && (notifier instanceof FillStyle)) {
            FillStyle notifierStyle = (FillStyle) notifier;

            // The background color has probably been reset
            // Check this event relates to a change in this editparts figure
            // then refresh
            Object obj = getModel();

            if (obj instanceof Diagram) {
                Diagram diag = (Diagram) obj;

                Style style =
                        diag.getStyle(NotationPackage.Literals.FILL_STYLE);

                if (style != null) {
                    if (style == notifierStyle) {
                        refreshVisuals();
                    }
                }

            }
        }

        // TODO: Not sure why this is here
        if ((event.getEventType() == Notification.SET)
                && (notifier instanceof Organization)) {
            if (event.getNewValue() instanceof OrganizationType
                    || event.getOldValue() instanceof OrganizationType) {
                refresh();
            }
        }

        // We need to do a whole series of checks to see if we need to refresh
        // the diagram palette and/or add to the semantic listener list i.e. if
        // there have been changes to the embedded
        // schema.
        if (notifier instanceof Notifier) {
            Notifier n = (Notifier) notifier;
            int eventType = event.getEventType();

            if (n instanceof OrgMetaModel) {
                if (eventType == Notification.ADD) {
                    Object newValue = event.getNewValue();
                    addListenerFilter("SemanticElement", this, (EObject) newValue);//$NON-NLS-1$ 

                    if (newValue instanceof OrgUnitRelationshipType) {
                        refresh();
                    }

                } else if (eventType == Notification.REMOVE) {
                    // TODO: DO WE NEED TO REMOVE FROM LISTENER LIST?
                    Object oldValue = event.getOldValue();

                    if (oldValue instanceof OrgUnitRelationshipType) {
                        refresh();
                    }
                }

            }

            if (n instanceof OrganizationType || n instanceof OrgUnitType
                    || n instanceof PositionType
                    || n instanceof OrgUnitRelationshipType
                    || n instanceof OrgUnitFeature) {
                if (eventType == Notification.ADD) {
                    Object newValue = event.getNewValue();
                    addListenerFilter("SemanticElement", this, (EObject) newValue);//$NON-NLS-1$

                    if (newValue instanceof OrgUnitFeature
                            || newValue instanceof PositionFeature) {
                        refresh();
                    }

                } else if (eventType == Notification.REMOVE) {
                    refresh();
                } else if (eventType == Notification.SET) {
                    if (event.getFeature() == OMPackage.Literals.NAMED_ELEMENT__DISPLAY_NAME
                            || event.getFeature() == OMPackage.Literals.NAMED_ELEMENT__NAME
                            || event.getFeature() == OMPackage.Literals.ORG_UNIT_FEATURE__FEATURE_TYPE
                            || event.getFeature() == OMPackage.Literals.POSITION_FEATURE__FEATURE_TYPE) {
                        refresh();
                    }
                }

            }

            if (n instanceof Feature) {
                // A feature of this organization is being set to an OrgUnitType
                // and therefore needs to appear on the palette (so refresh).
                if (eventType == Notification.SET) {
                    Object newValue = event.getNewValue();
                    if (newValue instanceof OrgUnitType
                            || newValue instanceof PositionType) {
                        refresh();
                    }

                    // New value is a name change i.e. to the name feature
                    if (event.getFeature() == OMPackage.Literals.NAMED_ELEMENT__DISPLAY_NAME
                            || event.getFeature() == OMPackage.Literals.POSITION_FEATURE__FEATURE_TYPE) {
                        refresh();
                    }

                }

                if (eventType == Notification.ADD) {
                    // New additions should be added to the semantic listener so
                    // that we are notified if the are changed (e.g. name
                    // change, set a type to a feature etc)
                    // list
                    Object newValue = event.getNewValue();
                    addListenerFilter("SemanticElement", this, (EObject) newValue);//$NON-NLS-1$

                    if (newValue instanceof OrgUnitFeature) {
                        OrgUnitFeature oufeature = (OrgUnitFeature) newValue;

                        if (oufeature.getFeatureType() != null) {
                            refresh();
                        }
                    }

                } else if (eventType == Notification.REMOVE) {

                }
            }
        }

    }

    @Override
    protected void addSemanticListeners() {
        super.addSemanticListeners();

        EObject elem = resolveSemanticElement();

        if (elem instanceof Organization) {
            Organization org = (Organization) elem;
            EObject container = org.eContainer();

            if (container != null && container instanceof OrgModel) {
                OrgModel om = (OrgModel) container;
                OrgMetaModel embeddedMetamodel = om.getEmbeddedMetamodel();

                if (embeddedMetamodel != null) {
                    // We want to add the embeddedMetaModel so that we can
                    // add/remove palette entries for additions/deletions of
                    // types and features to the schema.
                    addListenerFilter("SemanticElement", this, embeddedMetamodel);//$NON-NLS-1$

                    // And add all the schemas types and features already
                    // defined. This is because there might be palette entries
                    // for them. So we will need to be notified of any changes
                    // to them e.g. DisplayName change, so that we can decide
                    // whether to refresh.
                    EList<EObject> contents = embeddedMetamodel.eContents();
                    for (EObject eo : contents) {
                        if (isTypeAffectingPalette(eo)) {
                            addListenerFilter("SemanticElement", this, eo);//$NON-NLS-1$

                            if (eo instanceof OrganizationType) {
                                OrganizationType ot = (OrganizationType) eo;
                                EList<OrgUnitFeature> orgUnitFeatures =
                                        ot.getOrgUnitFeatures();

                                for (OrgUnitFeature orgUnitFeature : orgUnitFeatures) {
                                    addListenerFilter("SemanticElement", //$NON-NLS-1$
                                            this,
                                            orgUnitFeature);
                                }
                            }

                            if (eo instanceof OrgUnitType) {
                                OrgUnitType out = (OrgUnitType) eo;
                                EList<OrgUnitFeature> orgUnitFeatures =
                                        out.getOrgUnitFeatures();

                                for (OrgUnitFeature orgUnitFeature : orgUnitFeatures) {
                                    addListenerFilter("SemanticElement", //$NON-NLS-1$
                                            this,
                                            orgUnitFeature);
                                }

                                EList<PositionFeature> positionFeatures =
                                        out.getPositionFeatures();

                                for (PositionFeature positionFeature : positionFeatures) {
                                    addListenerFilter("SemanticElement", //$NON-NLS-1$
                                            this,
                                            positionFeature);
                                }

                            }

                        }
                    }
                }

            }

        }

    }

    private boolean isTypeAffectingPalette(EObject eo) {
        if (eo instanceof OrgUnitFeature || eo instanceof PositionFeature
                || eo instanceof OrgUnitRelationshipType
                || eo instanceof OrganizationType || eo instanceof OrgUnitType
                || eo instanceof PositionType || eo instanceof OrgElementType) {
            return true;
        }

        return false;
    }

    @Override
    public void refresh() {
        super.refresh();

        // If the Organization is typed then we need to build a palette to show
        // all the creation tools for the diferetnt typd entities
        EObject elem = resolveSemanticElement();
        if (elem instanceof Organization) {
            Organization org = (Organization) elem;
            OrgElementType type = org.getType();

            PaletteRoot paletteRoot = null;

            if (getEditDomain().getPaletteViewer() != null) {
                paletteRoot =
                        getEditDomain().getPaletteViewer().getPaletteRoot();
            }

            if (paletteRoot != null) {
                if (type != null) {
                    // Build a custom palette with a tool for each typed element
                    // Create a new drawer and tools
                    new OrganizationTypePaletteFactory()
                            .fillOrganizationTypePalette(paletteRoot, org);
                } else {
                    new OrganizationModelPaletteFactory()
                            .fillPalette(paletteRoot, org.isDynamic());
                }
            }

        }

    }

    @Override
    public Object getPreferredValue(EStructuralFeature feature) {
        Object result = null;
        if (feature.equals(NotationPackage.eINSTANCE.getFillStyle_FillColor())) {
            IPreferenceStore store =
                    (IPreferenceStore) getDiagramPreferencesHint()
                            .getPreferenceStore();
            result =
                    FigureUtilities
                            .RGBToInteger(PreferenceConverter
                                    .getColor(store,
                                            IOMSubPreferenceConstants.PREF_DIAGRAM_BG_COLOR));
        }
        return result != null ? result : super.getPreferredValue(feature);
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
                    if (ep instanceof OrganizationSubBadgeEditPart) {
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
