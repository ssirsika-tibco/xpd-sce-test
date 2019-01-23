/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.parts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.AbstractLayout;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutAnimator;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.ResourceSetListenerImpl;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.commands.SetPropertyCommand;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IPrimaryEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ContainerEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DragDropEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.PropertyHandlerEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.preferences.IPreferenceConstants;
import org.eclipse.gmf.runtime.diagram.ui.render.editparts.RenderedDiagramRootEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.ArrangeRequest;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.FillStyle;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.ProfileApplication;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.diagram.edit.parts.custom.BOMStereoAwareDiagramEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.customfigures.ModelSummaryCustomFigure;
import com.tibco.xpd.bom.modeler.diagram.edit.policies.BOMContainerNodeEditPolicy;
import com.tibco.xpd.bom.modeler.diagram.edit.policies.BOMDiagramPopupBarEditPolicy;
import com.tibco.xpd.bom.modeler.diagram.edit.policies.CanvasPackageCanonicalEditPolicy;
import com.tibco.xpd.bom.modeler.diagram.edit.policies.CanvasPackageItemSemanticEditPolicy;
import com.tibco.xpd.bom.modeler.diagram.part.DynamicPaletteFactory;
import com.tibco.xpd.bom.modeler.diagram.part.Messages;
import com.tibco.xpd.bom.modeler.diagram.part.UMLPaletteFactory;
import com.tibco.xpd.bom.modeler.diagram.preferences.IBOMPreferenceConstants;
import com.tibco.xpd.bom.modeler.diagram.requests.custom.IBOMCustomRequestConstants;
import com.tibco.xpd.bom.modeler.diagram.requests.custom.ToggleViewVisibilityCustomRequest;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.firstclassprofiles.FirstClassProfileManager;
import com.tibco.xpd.bom.resources.firstclassprofiles.IFirstClassProfileExtension;
import com.tibco.xpd.bom.resources.ui.util.BomUIUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;

public class CanvasPackageEditPart extends BOMStereoAwareDiagramEditPart
        implements IPrimaryEditPart {

    private final Package root = (Package) resolveSemanticElement();

    private ModelSummaryCustomFigure summFig;

    private LineBorder bdr;

    private IFigure fig;

    private CanvasResourceListener canvasResList;

    private String firstClassProfileLabel;

    private Image firstClassProfileImage;

    private final int MAX_CHILDREN_FOR_ANIMATION = 50;

    /**
     * @generated
     */
    public final static String MODEL_ID = "Business Object Model"; //$NON-NLS-1$

    /**
     * @generated
     */
    public static final int VISUAL_ID = 79;

    /**
     * @generated
     */
    public CanvasPackageEditPart(View view) {
        super(view);
    }

    @Override
    protected List getModelChildren() {
        // The superclass method returns only the "Visible" children. This is a
        // problem for our diagram because the Badge may be hidden when the
        // editor is opened and hence not included in the list of active
        // children editparts. This causes a problem for creating the popup
        // menu's show/hide action.
        Object model = getModel();
        if (model != null && model instanceof View) {
            return new ArrayList(((View) model).getChildren());
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated NOT
     */
    @Override
    protected void createDefaultEditPolicies() {
        super.createDefaultEditPolicies();
        installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
                new CanvasPackageItemSemanticEditPolicy());
        installEditPolicy(EditPolicyRoles.CANONICAL_ROLE,
                new CanvasPackageCanonicalEditPolicy());

        // Override drag drop policy to not provide any command - this will be
        // left to the edit policies
        removeEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE);
        installEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE,
                new DragDropEditPolicy() {
                    @Override
                    public Command getCommand(Request request) {
                        if (request.getType()
                                .equals(BOMResourcesPlugin.REQ_FRAGMENT_DRAG)) {
                            return new Command() {
                                // Return dummy command to show selection when
                                // fragment is dragged onto diagram
                            };
                        } else if (request instanceof ChangeBoundsRequest) {
                            /*
                             * XPD-5196: Don't allow the drag-drop of
                             * Enumeration Literals directly into the model (for
                             * some reason the EnumLit is a packageable
                             * element!).
                             */
                            List<?> editParts =
                                    ((ChangeBoundsRequest) request)
                                            .getEditParts();
                            if (editParts != null) {
                                for (Object obj : editParts) {
                                    if (obj instanceof EnumerationLiteralEditPart) {
                                        return null;
                                    }
                                }
                            }
                        }
                        return super.getCommand(request);
                    }
                });

        // removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.
        // Edit PolicyRoles.POPUPBAR_ROLE);

        removeEditPolicy(EditPolicy.CONTAINER_ROLE);
        installEditPolicy(EditPolicy.CONTAINER_ROLE,
                new DeferredArrangeCmdContainerEditPolicy());

        // removeEditPolicy(EditPolicyRoles.CREATION_ROLE);

        installEditPolicy(EditPolicyRoles.CREATION_ROLE,
                new CreationEditPolicy() {

                    @Override
                    protected ICommand getReparentCommand(IGraphicalEditPart gep) {
                        // TODO Auto-generated method stub
                        ISelection selection =
                                PlatformUI.getWorkbench()
                                        .getActiveWorkbenchWindow()
                                        .getSelectionService().getSelection();

                        if (selection instanceof StructuredSelection) {
                            StructuredSelection sel =
                                    (StructuredSelection) selection;

                            List<?> list = sel.toList();
                            for (Object object : list) {
                                if (object instanceof AssociationClassEditPart
                                        || object instanceof AssociationClassDanglingNodeEditPart) {
                                    if (CanReparentSelectionWithAssociationClass(list)) {
                                        return super.getReparentCommand(gep);
                                    }
                                }
                            }
                        }

                        View view = (View) gep.getModel();
                        EObject element = ViewUtil.resolveSemanticElement(view);

                        if (element instanceof AssociationClass) {
                            return null;
                        }

                        return super.getReparentCommand(gep);
                    }

                });

        removeEditPolicy(EditPolicyRoles.PROPERTY_HANDLER_ROLE);

        installEditPolicy(EditPolicyRoles.PROPERTY_HANDLER_ROLE,
                new PropertyHandlerEditPolicy() {

                    // Overide here because we want to check for a customized
                    // ChangeProperty request originating from the PopUp menu
                    // option to ShowHideBadge. The request originates from a
                    // DiagramAction for the currently selected (right-clicked)
                    // editpart which could be the diagram - which is NOT the
                    // editpart for which we want to change the visiblity
                    // property of the view. (This is of course the badge).
                    @Override
                    public Command getCommand(Request request) {

                        if (!understandsRequest(request)) {
                            return null;
                        }

                        if (request
                                .getType()
                                .equals(IBOMCustomRequestConstants.REQ_TOGGLE_VIEW_VISIBILITY)
                                && request instanceof ToggleViewVisibilityCustomRequest) {

                            ToggleViewVisibilityCustomRequest togRequest =
                                    (ToggleViewVisibilityCustomRequest) request;

                            View view2Toggle = togRequest.getView();

                            if (view2Toggle != null) {
                                EditPart ep = getHost();
                                if (ep instanceof IGraphicalEditPart) {
                                    View hostView =
                                            ((IGraphicalEditPart) ep)
                                                    .getNotationView();

                                    // Check that the view is a child of this
                                    // editpart
                                    EList childViews = hostView.getChildren();

                                    for (Object object : childViews) {
                                        if (object == view2Toggle) {

                                            // Get current property setting so
                                            // that
                                            // we can flip it
                                            boolean visible =
                                                    view2Toggle.isVisible();
                                            boolean newVisibleState = true;

                                            if (visible) {
                                                newVisibleState = false;
                                            }

                                            if (ViewUtil
                                                    .isPropertySupported(view2Toggle,
                                                            togRequest
                                                                    .getPropertyID())) {
                                                return new ICommandProxy(
                                                        new SetPropertyCommand(
                                                                getEditingDomain(),
                                                                new EObjectAdapter(
                                                                        view2Toggle),
                                                                togRequest
                                                                        .getPropertyID(),
                                                                togRequest
                                                                        .getPropertyName(),
                                                                newVisibleState));
                                            }

                                        }
                                    }

                                }

                            }

                        }

                        return super.getCommand(request);
                    }

                    @Override
                    public boolean understandsRequest(Request request) {
                        if (request
                                .getType()
                                .equals(IBOMCustomRequestConstants.REQ_TOGGLE_VIEW_VISIBILITY)) {
                            return true;
                        }
                        return super.understandsRequest(request);
                    }
                });

        /*
         * Handle first-class profile extensions.
         */
        removeEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE);
        installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
                new BOMContainerNodeEditPolicy());

        removeEditPolicy(EditPolicyRoles.POPUPBAR_ROLE);
        installEditPolicy(EditPolicyRoles.POPUPBAR_ROLE,
                new BOMDiagramPopupBarEditPolicy());

        /*
         * XPD-5156: In Eclipse 3.7 dragging a property / enumlit from one
         * class/enumeration unit to another respectively causes a NPE in the
         * log. This is due to the XYLayoutEditPolicy of this edit part being
         * asked to provide a command during the drag of a these elements and
         * during the handling of this request an NPE gets thrown. The end
         * result seems to be OK but the log gets polluted with a lot of
         * exceptions. There seems to be some change in the way this layout edit
         * policy works in Eclipse 3.7.
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
                            if (editPart instanceof PropertyEditPart
                                    || editPart instanceof OperationEditPart
                                    || editPart instanceof EnumerationLiteralEditPart) {
                                return null;
                            }
                        }
                    }
                }
                return super.getCommand(request);
            }
        });

    }

    private boolean CanReparentSelectionWithAssociationClass(List<?> list) {

        List<AssociationClassEditPart> lstAssocClassEp =
                new ArrayList<AssociationClassEditPart>();
        Map<AssociationClassDanglingNodeEditPart, EObject> assocClassDanglingNodeMap =
                new HashMap<AssociationClassDanglingNodeEditPart, EObject>();

        for (Object object : list) {
            if (object instanceof AssociationClassEditPart) {
                AssociationClassEditPart assocEP =
                        (AssociationClassEditPart) object;
                lstAssocClassEp.add(assocEP);
                continue;
            }

            if (object instanceof AssociationClassDanglingNodeEditPart) {
                AssociationClassDanglingNodeEditPart assocDanglingEP =
                        (AssociationClassDanglingNodeEditPart) object;
                AssociationClass element =
                        (AssociationClass) assocDanglingEP
                                .resolveSemanticElement();
                assocClassDanglingNodeMap.put(assocDanglingEP, element);
                continue;
            }
        }

        // First check:
        if (lstAssocClassEp.size() != assocClassDanglingNodeMap.size()) {
            return false;
        }

        // Second check: Is there a matching dangling node for this editpart?
        for (AssociationClassEditPart associationClassEP : lstAssocClassEp) {
            if (!assocClassDanglingNodeMap.containsValue(associationClassEP
                    .resolveSemanticElement())) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void activate() {
        super.activate();

        canvasResList = new CanvasResourceListener();
        XpdResourcesPlugin.getDefault().getEditingDomain()
                .addResourceSetListener(canvasResList);

    }

    @Override
    public void deactivate() {

        if (canvasResList != null) {
            XpdResourcesPlugin.getDefault().getEditingDomain()
                    .removeResourceSetListener(canvasResList);
            canvasResList = null;
        }

        if (firstClassProfileImage != null) {
            firstClassProfileImage.dispose();
            firstClassProfileImage = null;
        }
        firstClassProfileLabel = null;

        super.deactivate();
    }

    /**
     * 
     * Override the superclass so that we can draw a figure representing a
     * summary of the model in the top-left of the diagram.
     * 
     * @generated NOT
     * 
     * @author rgreen
     * 
     *         (non-Javadoc)
     * 
     * @see org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart#createFigure()
     */
    @Override
    protected final IFigure createFigure() {
        fig = super.createFigure();

        // Disable the layout animation that GMF diagrams inherit by default
        fig.removeLayoutListener(LayoutAnimator.getDefault());

        fig.setOpaque(true);

        // Create a base node figure that will expand to the correct
        // size of its children. We have to write a custom layout for
        // this though and override getPreferredSize() and
        // calculatePreferredSize().
        NodeFigure n = new NodeFigure();

        AbstractLayout layMgr = new AbstractLayout() {

            @Override
            public Dimension getPreferredSize(IFigure container, int hint,
                    int hint2) {
                return super.getPreferredSize(container, hint, hint2);
            }

            @Override
            protected Dimension calculatePreferredSize(IFigure container,
                    int hint, int hint2) {

                // These are to be the dimensions for this layoutmanager
                Dimension dim = new Dimension(0, 0);

                Dimension min = new Dimension(20, 20);
                if (min != null) {
                    dim.width = min.width;
                }

                // Iterate through all the children of this container
                // so that we can set our dimension to fit the largest child
                List children = container.getChildren();
                for (Iterator iterator = children.iterator(); iterator
                        .hasNext();) {
                    IFigure child = (IFigure) iterator.next();

                    Dimension childDim = child.getPreferredSize(hint, hint2);
                    if (childDim.width > dim.width) {
                        dim.width = childDim.width;
                    }

                    // Make sure that the height is set so that the
                    // child can fit and add a few pixels for a margin
                    // if needed (modify the offset if required)
                    dim.height += childDim.height + 0;
                }

                if (min != null) {
                    if (min.height > dim.height) {
                        dim.height = min.height;
                    }
                }

                // add margins.
                // Change the offsets is required
                dim.width += 0;
                dim.height += 0;
                return dim;
            }

            @Override
            public void layout(IFigure container) {
                // FreeFormLayer's layout never asks the top level figures for
                // their preferred size, just takes figure bounds as-read.
                // So we'll have to force bounds on our label container here.
                Dimension dim =
                        calculatePreferredSize(container,
                                SWT.DEFAULT,
                                SWT.DEFAULT);

                Rectangle bnds = container.getBounds().getCopy();
                bnds.width = dim.width;
                bnds.height = dim.height;
                container.setBounds(bnds);

                // System.out.println("Layout BOUNDS: " + bnds);
                int y = 0;

                List<?> children = container.getChildren();
                for (Iterator<?> iterator = children.iterator(); iterator
                        .hasNext();) {
                    IFigure child = (IFigure) iterator.next();

                    Dimension childDim = child.getPreferredSize(bnds.width, y);
                    child.setBounds(new Rectangle(bnds.x + 0, bnds.y + y,
                            childDim.width, childDim.height));

                    // System.out.println(" CHILD BOUNDS: " +
                    // child.getBounds());
                    // calculate offset for next child below current
                    y += childDim.height + 10;
                }
            }
        };

        n.setLayoutManager(layMgr);

        n.setLocation(new Point(0, 0));

        // Create the actual figure that will display the
        // model information
        summFig = new ModelSummaryCustomFigure();
        summFig.setOpaque(true);
        setSummaryLabels();
        n.add(summFig);
        n.setOpaque(false);
        // fig.add(n);

        return fig;
    }

    /**
     * Sets the labels of the model summary figure
     * 
     * @author rgreen
     */
    private void setSummaryLabels() {

        // Model name
        // This is the default summary name. Further on down we will
        // append stereotypes if required.
        String modelSummaryName = "  " + root.getQualifiedName(); //$NON-NLS-1$
        summFig.setModelSummaryName(modelSummaryName); //$NON-NLS-1$

        Model model = root.getModel();
        IFirstClassProfileExtension ext =
                FirstClassProfileManager.getInstance()
                        .getAppliedFirstClassProfile(model);
        // If first class profile and the info hasn't already been loaded then
        // do so
        if (ext != null && firstClassProfileImage == null
                && firstClassProfileLabel == null) {
            // First class profile applied so use appropriate image and label
            ImageDescriptor desc = ext.getDiagramImage();
            if (desc != null) {
                firstClassProfileImage = desc.createImage();
            }
            firstClassProfileLabel = ext.getLabel();
        }

        if (firstClassProfileImage != null || firstClassProfileLabel != null) {
            summFig.setModelSummaryIcon(firstClassProfileImage);

            summFig.setGradientStart(ColorConstants.lightGreen);
            summFig.setGradientEnd(ColorConstants.white);

            // This is not actually a stereotype label now but a
            // generic
            // diagram "type" title
            // e.g. "Concept Model" or "Business Object Model".
            summFig.setStereotypeLabel(firstClassProfileLabel);
            return;

        }

        // This is not actually a stereotype label now but a generic diagram
        // "type" title
        // e.g. "Concept Model" or "Business Object Model".
        summFig.setStereotypeLabel(Messages.CanvasPackageEditPart_BOM_stereotype_label);

        // Show all stereotypes applied to the top level model package
        List<Stereotype> lstModelStereos = root.getAppliedStereotypes();

        int numStereos = lstModelStereos.size();
        if (numStereos > 0) {
            String stereos = "  <<"; //$NON-NLS-1$

            for (int i = 0; i < numStereos; i++) {
                if (i < (numStereos - 1)) {
                    // Not the last stereotype so show a comma
                    stereos = stereos + lstModelStereos.get(i).getName() + ", "; //$NON-NLS-1$
                } else {
                    // The final stereotype so don't show a comma
                    stereos = stereos + lstModelStereos.get(i).getName();
                }

            }
            stereos = stereos + ">>\n" + modelSummaryName; //$NON-NLS-1$
            summFig.setModelSummaryName(stereos);
        }
    }

    /**
     * generated NOT
     * 
     * @override
     */
    /** Invoke the editpart's refresh mechanism. */
    @Override
    public void refresh() {
        if (!FirstClassProfileManager.getInstance()
                .isFirstClassProfileApplied(root.getModel())) {

            // Re-create the palette
            // List<Profile> lstProfs = root.getAppliedProfiles();
            List<Profile> lstProfs = new ArrayList<Profile>();

            /*
             * Find profiles that have not been broken (i.e. profiles that have
             * not been changed since being used in this model).
             */
            EList<ProfileApplication> applications =
                    root.getAllProfileApplications();
            for (ProfileApplication appl : applications) {
                EPackage def = appl.getAppliedDefinition();
                if (def != null && !def.eIsProxy()) {
                    lstProfs.add(appl.getAppliedProfile());
                }
            }

            PaletteRoot paletteRoot = null;

            EditDomain editDomain = getEditDomain();
            if (editDomain != null) {
                if (editDomain.getPaletteViewer() != null) {
                    paletteRoot =
                            editDomain.getPaletteViewer().getPaletteRoot();
                }
            }

            boolean isUserDiagram = BomUIUtil.isUserDiagram(getDiagramView());

            if (paletteRoot != null) {
                if (lstProfs.size() > 0) {

                    // Create a new drawer and tools
                    new DynamicPaletteFactory().fillDynamicPalette(paletteRoot,
                            lstProfs,
                            isUserDiagram);
                } else {
                    new UMLPaletteFactory().fillPalette(paletteRoot,
                            isUserDiagram);
                }
            }
        }
        refreshBackgroundColor();
        super.refresh();
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
    public void refreshGeneralizationConnections() {
        CanvasPackageCanonicalEditPolicy epol =
                (CanvasPackageCanonicalEditPolicy) getEditPolicy(EditPolicyRoles.CANONICAL_ROLE);
        epol.refresh();
    }

    /**
     * 
     * Because zoom and layout animation can take a very long time when you have
     * lots of overlapping views, we have introduced a threshold of view numbers
     * over which animation will be disabled.
     * 
     * @author rgreen
     * 
     */
    @SuppressWarnings("static-access")
    private void UpdateAnimationOption() {

        IPreferenceStore store =
                (IPreferenceStore) getDiagramPreferencesHint()
                        .getPreferenceStore();

        boolean animatedLayoutEnabled =
                store.getBoolean(IPreferenceConstants.PREF_ENABLE_ANIMATED_LAYOUT);

        int size = getModelChildren().size();

        if (animatedLayoutEnabled) {
            if (size > MAX_CHILDREN_FOR_ANIMATION) {
                // Disable the layout animation that GMF diagrams inherit by
                // default
                fig.removeLayoutListener(LayoutAnimator.getDefault());
            } else {
                // So we don't add the listener multiple times call a remove
                // first
                // in case the listener is already there
                fig.removeLayoutListener(LayoutAnimator.getDefault());
                fig.addLayoutListener(LayoutAnimator.getDefault());

            }
        }

        boolean animatedZoomEnabled =
                store.getBoolean(IPreferenceConstants.PREF_ENABLE_ANIMATED_ZOOM);

        if (animatedZoomEnabled) {
            RenderedDiagramRootEditPart rootEP =
                    (RenderedDiagramRootEditPart) getRoot();
            ZoomManager zoomManager = rootEP.getZoomManager();
            if (size > MAX_CHILDREN_FOR_ANIMATION) {
                zoomManager.setZoomAnimationStyle(zoomManager.ANIMATE_NEVER);
            } else {
                zoomManager
                        .setZoomAnimationStyle(ZoomManager.ANIMATE_ZOOM_IN_OUT);
            }
        }

    }

    @Override
    protected void handleNotificationEvent(Notification event) {

        int eventType = event.getEventType();

        switch (eventType) {
        case Notification.ADD:
        case Notification.REMOVE:
        case Notification.ADD_MANY:
        case Notification.REMOVE_MANY:
            UpdateAnimationOption();
        }
        ;

        Object feature = event.getFeature();

        if (UMLPackage.Literals.PACKAGE__PROFILE_APPLICATION.equals(feature)) {
            refresh();
        }

        if (event.getFeature() instanceof EAttribute) {
            EAttribute ea = (EAttribute) event.getFeature();
            // Check for changes to model name
            if (ea == UMLPackage.Literals.NAMED_ELEMENT__NAME) {
                refresh();
            }
        }

        super.handleNotificationEvent(event);
    }

    /**
     * Resource listener to refresh when any content of the <code>Model</code>
     * changes.
     */
    class CanvasResourceListener extends ResourceSetListenerImpl {
        @Override
        public boolean isPostcommitOnly() {
            return true;
        }

        @Override
        public void resourceSetChanged(ResourceSetChangeEvent event) {
            List<Notification> notifications = event.getNotifications();
            boolean doRefresh = false;
            if (notifications != null) {
                for (Notification notification : notifications) {
                    Object notifier = notification.getNotifier();

                    if (notifier instanceof FillStyle) {
                        EObject container = ((FillStyle) notifier).eContainer();
                        if (container == getModel()) {
                            doRefresh = true;
                            break;
                        }
                    }
                }

                if (!doRefresh && isProfileChange(notifications)) {
                    doRefresh = true;
                }

                if (doRefresh) {
                    Display display = XpdResourcesPlugin.getStandardDisplay();
                    if (display != null) {
                        display.asyncExec(new Runnable() {

                            @Override
                            public void run() {
                                refresh();
                            }
                        });
                    }
                }

                if (isAssociationEndRetargeting(notifications)) {
                    /*
                     * Need to refresh the canonical edit policy to redraw the
                     * association. This is necessary as the re-target could
                     * have happened in another diagram.
                     */
                    EditPolicy policy =
                            getEditPolicy(EditPolicyRoles.CANONICAL_ROLE);
                    if (policy instanceof CanonicalEditPolicy) {
                        ((CanonicalEditPolicy) policy).refresh();
                    }
                }
            }
        }
    }

    /**
     * Check if a profile definition has changed. This will require a refresh to
     * update the palette.
     * 
     * @param notifications
     * @return
     */
    private boolean isProfileChange(List<Notification> notifications) {
        EList<Profile> profiles = root.getModel().getAppliedProfiles();
        for (Notification notification : notifications) {
            if (!notification.isTouch()
                    && profiles.contains(notification.getNewValue())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check the notifications to see if the user has dragged one end of an
     * Association from one Class to another.
     * 
     * @param notifications
     * @return
     */
    private boolean isAssociationEndRetargeting(List<?> notifications) {
        if (notifications != null && !notifications.isEmpty()) {
            List<Notification> adds = new ArrayList<Notification>();
            List<Notification> removes = new ArrayList<Notification>();

            for (Object obj : notifications) {
                if (obj instanceof Notification) {
                    Notification notification = (Notification) obj;

                    if (notification.getNotifier() instanceof Class
                            && notification.getFeature() == UMLPackage.eINSTANCE
                                    .getStructuredClassifier_OwnedAttribute()) {

                        if (notification.getEventType() == Notification.ADD
                                && notification.getNewValue() instanceof Property
                                && ((Property) notification.getNewValue())
                                        .getAssociation() != null) {
                            adds.add(notification);

                        } else if (notification.getEventType() == Notification.REMOVE
                                && notification.getOldValue() instanceof Property
                                && ((Property) notification.getOldValue())
                                        .getAssociation() != null) {
                            removes.add(notification);
                        }

                    }
                }
            }

            if (!adds.isEmpty() && !removes.isEmpty()) {
                for (Notification add : adds) {
                    for (Notification remove : removes) {
                        if (add.getNewValue() == remove.getOldValue()) {
                            // The association end target has been changed
                            return true;
                        }
                    }
                }
            }

        }
        return false;
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
                                            IBOMPreferenceConstants.PREF_DIAGRAM_BG_COLOR));
        }
        return result != null ? result : super.getPreferredValue(feature);
    }

    /**
     * Override the default ContainerEditPolicy with our own which has special
     * handling for AutoArrange commands. The creation of auto-arrange commands
     * is deferred until the user ACTUALLY selects it. Auto arrange can take a
     * LONG time to build (several secs on big diagrams) so no point doing it
     * unnecessarily.
     * 
     * @author rgreen
     * 
     */
    public static class DeferredArrangeCmdContainerEditPolicy extends
            ContainerEditPolicy {

        @Override
        public Command getCommand(Request request) {
            if (request instanceof ArrangeRequest) {
                // Defer build of arrange command until it is actually exe cuted
                // as it takes a loooong time with lots on diagram
                Command cmd = new DeferredContainerEditPolicyCommand(request);
                return cmd;
            }
            return super.getCommand(request);
        }

        /**
         * Specifcally call the ContainerEditPolicy.createCommand() method
         * (bypasses creation of deferred commands).
         * 
         * @param request
         * @return
         */
        private Command superGetCommand(Request request) {
            return super.getCommand(request);
        }

        /**
         * Command that defers creation of command by container edit policy
         * until actual execution
         * 
         * @author rgreen
         */
        private class DeferredContainerEditPolicyCommand extends
                DeferredTilExecGEFCommand {

            private final Request request;

            public DeferredContainerEditPolicyCommand(Request arrangeRequest) {
                super();
                this.request = arrangeRequest;
            }

            @Override
            protected Command createDeferredCommand() {
                return superGetCommand(request);
            }
        }

    }

    /**
     * Command that can be used to wrap up other commands (which are not created
     * until command is actually executed)
     * 
     * @author rgreen
     * 
     */
    public static abstract class DeferredTilExecGEFCommand extends Command {

        private Command deferredCommand = null;

        /**
         * This method is called ONLY when the command is actually executed.
         * 
         * @return The command to execute.
         */
        protected abstract Command createDeferredCommand();

        @Override
        public void execute() {
            deferredCommand = createDeferredCommand();
            if (deferredCommand != null) {
                if (deferredCommand.canExecute()) {
                    deferredCommand.execute();
                }
            }

            return;
        }

        @Override
        public void redo() {
            if (deferredCommand != null) {
                deferredCommand.redo();
            }
        }

        @Override
        public void undo() {
            if (deferredCommand != null) {
                deferredCommand.undo();
            }
        }

        @Override
        public boolean canExecute() {
            return true;
        }

    }

}
