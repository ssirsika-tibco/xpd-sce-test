/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.bom.resources.ui.clipboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.ui.action.actions.global.ClipboardManager;
import org.eclipse.gmf.runtime.common.ui.util.CustomData;
import org.eclipse.gmf.runtime.common.ui.util.CustomDataTransfer;
import org.eclipse.gmf.runtime.common.ui.util.ICustomData;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.render.clipboard.DiagramImageGenerator;
import org.eclipse.gmf.runtime.diagram.ui.util.MeasurementUnitHelper;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.IMapMode;
import org.eclipse.gmf.runtime.emf.clipboard.core.ClipboardSupportUtil;
import org.eclipse.gmf.runtime.emf.clipboard.core.CopyOperation;
import org.eclipse.gmf.runtime.emf.clipboard.core.IClipboardSupport;
import org.eclipse.gmf.runtime.emf.clipboard.core.PasteTarget;
import org.eclipse.gmf.runtime.emf.clipboard.core.internal.PasteOperation;
import org.eclipse.gmf.runtime.emf.clipboard.core.internal.SerializationEMFResource;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.Bendpoints;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.DiagramLinkStyle;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.MeasurementUnit;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.RelativeBendpoints;
import org.eclipse.gmf.runtime.notation.Size;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.notation.datatype.RelativeBendpoint;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.dnd.ImageTransfer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.ProfileApplication;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.ui.internal.Messages;
import com.tibco.xpd.bom.resources.ui.internal.clipboard.BOMClipboardSupportHelper;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Factory to create copy/paste GMF commands for the BOM. This uses the
 * {@link BOMClipboardSupportHelper} directly rather than go through the
 * clipboard support framework due to current limitations in the framework.
 * 
 * @author njpatel
 * 
 */
@SuppressWarnings("restriction")
public class BOMCopyPasteCommandFactory {

    public static final String ANNOT_MEASUREUNIT = "MeasureUnit"; //$NON-NLS-1$

    public static final String ANNOT_PROFILES = "Profiles"; //$NON-NLS-1$

    public static final String ANNOT_STEREOTYPES = "Stereotypes"; //$NON-NLS-1$

    public static final String ANNOT_FEEDBACK_RECTANGLES = "feedbackRectangles"; //$NON-NLS-1$

    private static final Dimension DEFAULT_CLASS = new Dimension(120, 120);

    private static final Dimension DEFAULT_PACKAGE = new Dimension(220, 150);

    private static final Dimension DEFAULT_PRIMITIVETYPE =
            new Dimension(90, 60);

    /**
     * String constant for the clipboard format
     */
    public static final String DRAWING_SURFACE = "Drawing Surface"; //$NON-NLS-1$

    private static final BOMCopyPasteCommandFactory INSTANCE =
            new BOMCopyPasteCommandFactory();

    /**
     * Private constructor. Users should use {@link #getInstance()}.
     */
    private BOMCopyPasteCommandFactory() {
        // Private constructor
    }

    /**
     * Get the singleton instance of this factory.
     * 
     * @return
     */
    public static BOMCopyPasteCommandFactory getInstance() {
        return INSTANCE;
    }

    /**
     * Create a copy image command. This will copy an image of the selection
     * plus string serialization of the selection into the system clipboard.
     * 
     * @param domain
     *            transactional editing domain
     * @param viewContext
     *            view context of the copy operation
     * @param source
     *            copied objects
     * @param diagramEP
     *            diagram edit part
     * @param isUndoable
     *            indicates whether command is undoable
     * @return <code>ICommand</code> to copy into clipboard.
     */
    public ICommand createCopyImageCommand(TransactionalEditingDomain domain,
            View viewContext, Collection<?> source, DiagramEditPart diagramEP,
            boolean isUndoable) {

        return new BOMCopyImageCommand(domain,
                Messages.BOMCopyPasteCommandFactory_copy_menu, viewContext,
                diagramEP, source, isUndoable);
    }

    /**
     * Create a copy command to copy the serialization of the selection into the
     * system clipboard.
     * 
     * @param domain
     *            transactional editing domain
     * @param context
     *            context of the copy operation
     * @param source
     *            copied objects
     * @return command to copy into clipboard.
     */
    public ICommand createCopyCommand(TransactionalEditingDomain domain,
            EObject context, Collection<?> source) {
        return new BOMCopyCommand(domain,
                Messages.BOMCopyPasteCommandFactory_copy_menu, context, source,
                false);
    }

    /**
     * Create a paste command to paste copied objects into the given context.
     * <p>
     * This is the same as using
     * {@link #createPasteCommand(TransactionalEditingDomain, EObject, ICustomData[], IMapMode, location)
     * createPasteCommand(TransactionalEditingDomain, EObject, ICustomData[],
     * IMapMode, null)}
     * </p>
     * 
     * @param editingDomain
     *            transactional editing domain
     * @param context
     *            context into which to paste (can be notation or semantic
     *            element)
     * @param data
     *            custom data from the clipboard
     * @param mm
     *            map mode
     * @return command to paste.
     */
    public ICommand createPasteCommand(
            TransactionalEditingDomain editingDomain, EObject context,
            ICustomData[] data, IMapMode mm) {

        return createPasteCommand(editingDomain, context, data, mm, null);
    }

    /**
     * Create a paste command to paste copied objects into the given context at
     * the given location.
     * 
     * @param editingDomain
     *            transactional editing domain
     * @param context
     *            target to paste into (can be notational or semantic element,
     *            if semantic then location will be ignored)
     * @param data
     *            custom data from the clipboard
     * @param mm
     *            map mode
     * @param location
     *            location of paste
     * @return
     * @since 3.2
     */
    public ICommand createPasteCommand(
            TransactionalEditingDomain editingDomain, EObject context,
            ICustomData[] data, IMapMode mm, Point location) {
        BOMPasteCommand command =
                new BOMPasteCommand(editingDomain,
                        Messages.BOMCopyPasteCommandFactory_paste_menu,
                        context, data, mm);

        if (location != null) {
            command.setLocation(location);
        }

        return command;
    }

    /**
     * Get auxiliary objects to include in the copy selection. This will be any
     * associations between copied classes, stereotypes, profiles, etc.
     * 
     * @param selection
     *            copy selection
     * @return collection of auxiliary objects.
     */
    private List<?> getAuxiliaryObjects(Collection<?> selection) {
        List<Object> auxObjects = new ArrayList<Object>();

        if (selection != null) {
            Set<Profile> profiles = new HashSet<Profile>();
            EAnnotation stereotypeAnnot = createAnnotation(ANNOT_STEREOTYPES);
            EList<EObject> stereotypeAnnotContent =
                    stereotypeAnnot.getContents();
            for (Object sel : selection) {
                if (sel instanceof Node) {
                    auxObjects.addAll(getEdgesToInclude(selection, (Node) sel));
                }

                if (sel instanceof View) {
                    EObject eo = ((View) sel).getElement();

                    if (eo instanceof AssociationClass) {
                        auxObjects.addAll(getAllAssocClassViews((View) sel));
                    }

                    if (eo instanceof Element) {
                        Set<EObject> stereotypeApplications =
                                getStereotypeApplications((Element) eo,
                                        profiles);

                        if (!stereotypeApplications.isEmpty()) {
                            auxObjects.addAll(stereotypeApplications);

                            if (!stereotypeApplications.isEmpty()) {
                                XMLResource res = null;

                                if (eo.eResource() instanceof XMLResource) {
                                    res = (XMLResource) eo.eResource();
                                }

                                if (res != null) {
                                    for (EObject appl : stereotypeApplications) {
                                        stereotypeAnnotContent
                                                .add(createAnnotation(res
                                                        .getID(appl)));
                                    }
                                }
                            }
                        }
                    }

                    if (sel instanceof Node && eo instanceof Package) {
                        // If this package has an associated diagram then a copy
                        // of that needs to be made
                        DiagramLinkStyle style =
                                (DiagramLinkStyle) ((Node) sel)
                                        .getStyle(NotationPackage.eINSTANCE
                                                .getDiagramLinkStyle());
                        if (style != null) {
                            Diagram diagram = style.getDiagramLink();
                            if (diagram != null) {
                                auxObjects.add(diagram);
                            }
                        }
                    }
                }
            }
            // Add annotations for profiles (resource URI)
            if (!profiles.isEmpty()) {
                EAnnotation profileAnnot = createAnnotation(ANNOT_PROFILES);
                EList<EObject> contents = profileAnnot.getContents();
                for (Profile profile : profiles) {
                    if (profile.eResource() != null
                            && profile.eResource().getURI() != null) {
                        contents.add(createAnnotation(EcoreUtil.getURI(profile)
                                .toString()));
                    }
                }
                // Add profile and stereotype annotations
                auxObjects.add(profileAnnot);
                auxObjects.add(stereotypeAnnot);
            }
        }

        return auxObjects;
    }

    /**
     * Get all the {@link AssociationClass} views related to the view provided.
     * This will ensure that when an <code>AssociationClass</code> is selected
     * to be copied the dangling node and the connector are also copied. Also,
     * the association class ends will also be copied for any owner ends.
     * 
     * @param sel
     * @return
     */
    private List<EObject> getAllAssocClassViews(View sel) {
        List<EObject> auxViews = new ArrayList<EObject>();
        EObject element = sel.getElement();

        if (element instanceof AssociationClass) {
            if (sel != null && sel.eContainer() != null) {
                for (EObject child : sel.eContainer().eContents()) {
                    if (child != sel && child instanceof View
                            && ((View) child).getElement() == element) {
                        auxViews.add(child);
                    }
                }
            }

            EList<Property> ends = ((AssociationClass) element).getOwnedEnds();

            for (Property end : ends) {
                // Get all edges associated with the properties
                ECrossReferenceAdapter adapter =
                        ECrossReferenceAdapter.getCrossReferenceAdapter(end);
                Collection<Setting> references =
                        adapter.getInverseReferences(end);

                for (Setting ref : references) {
                    if (ref.getEObject() instanceof View) {
                        auxViews.add(ref.getEObject());
                    }
                }
            }
        }

        return auxViews;
    }

    /**
     * Get <code>Stereotype</code> applications for the given element. This will
     * also return the relevant <code>Profile</code>s in the profiles
     * collection.
     * 
     * @param element
     *            element to get stereotypes of
     * @param profiles
     *            corresponding profiles that contain the stereotypes, this can
     *            be <code>null</code> if profiles information is not required.
     * @return collection of stereotype applications, empty set if none found.
     */
    public Set<EObject> getStereotypeApplications(Element element,
            Set<Profile> profiles) {
        Set<EObject> appls = new HashSet<EObject>();

        if (element != null) {
            EList<Stereotype> appliedStereotypes =
                    element.getAppliedStereotypes();

            if (appliedStereotypes != null) {
                for (Stereotype type : appliedStereotypes) {

                    EObject appl = element.getStereotypeApplication(type);

                    if (appl != null) {
                        if (profiles != null) {
                            profiles.add(type.getProfile());
                        }
                        appls.add(appl);
                    }
                }
            }

            // Process all contained elements
            EList<EObject> children = element.eContents();

            for (EObject child : children) {
                if (child instanceof Element) {
                    appls.addAll(getStereotypeApplications((Element) child,
                            profiles));
                }
            }
        }

        return appls;
    }

    /**
     * Get any edges (association) to include in the copy selection. If two
     * classes included in a copy selection have an association between them
     * then this should also be included in the copy.
     * 
     * @param selection
     *            copy selection
     * @param node
     *            check the edges of this node to determine if they should be
     *            included in the copy selection.
     * @return list of copy selection including any edges added.
     */
    private List<?> getEdgesToInclude(Collection<?> selection, Node node) {
        List<Object> auxObjects = new ArrayList<Object>();

        if (selection != null && node != null) {

            EList<?> children = node.getPersistedChildren();

            if (children != null && !children.isEmpty()) {
                for (Object child : children) {
                    if (child instanceof Node) {
                        auxObjects.addAll(getEdgesToInclude(selection,
                                (Node) child));
                    }
                }
            }

            EList<?> edges = node.getSourceEdges();

            if (edges != null) {
                for (Object obj : edges) {
                    if (obj instanceof Edge) {
                        Edge edge = (Edge) obj;

                        // If this edge has no semantic element then ignore (eg
                        // NoteAttachment)
                        if (edge.getElement() != null) {
                            View target = edge.getTarget();

                            if (target != null) {
                                if (selection.contains(target)) {
                                    auxObjects.add(edge);
                                } else {
                                    /*
                                     * Check if the selection contains the
                                     * target's ancestor
                                     */
                                    for (Object sel : selection) {
                                        if (sel instanceof EObject
                                                && EcoreUtil
                                                        .isAncestor((EObject) sel,
                                                                target)) {
                                            auxObjects.add(edge);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return auxObjects;
    }

    /**
     * Serialize the given selection to put onto the clipboard.
     * 
     * @param selection
     *            copy selection
     * @return <code>String</code>.
     */
    public String copyToString(List<?> selection) {
        String copyStr = null;

        if (selection != null) {
            List<Object> newSelection = new ArrayList<Object>(selection);

            // Get all auxiliary objects to copy (includes associations,
            // stereotypes etc)
            List<?> auxillaryObjects = getAuxiliaryObjects(newSelection);
            if (auxillaryObjects != null) {
                for (Object aux : auxillaryObjects) {
                    if (!newSelection.contains(aux)) {
                        newSelection.add(aux);
                    }
                }
            }

            addFeedbackRectangles(newSelection);

            View view = null;
            // Find a view
            for (Object sel : selection) {
                if (sel instanceof View) {
                    view = (View) sel;
                    break;
                }
            }

            if (view != null) {
                // add the measurement unit in an annotation.
                Diagram dgrm = view.getDiagram();
                EAnnotation measureUnitAnnotation =
                        createAnnotation(ANNOT_MEASUREUNIT);
                measureUnitAnnotation.getContents().add(createAnnotation(dgrm
                        .getMeasurementUnit().getName()));
                newSelection.add(measureUnitAnnotation);
            }

            CopyOperation op =
                    new CopyOperation(null, new BOMClipboardSupportHelper(),
                            ClipboardSupportUtil.getCopyElements(newSelection),
                            Collections.EMPTY_MAP);

            try {
                copyStr = op.copy();
            } catch (Exception e) {
                Activator.getDefault().getLogger().error(e);
            }
        }

        return copyStr;
    }

    /**
     * Get feedback annotation to add to the copy objects. This will be used to
     * show visual feedback during drag - used by the fragments.
     * 
     * @param nodes
     * @return Annotation containing feedback information.
     * @since 3.2
     */
    public EAnnotation getFeedbackAnnotation(List<Node> nodes) {
        List<Rectangle> rects = new ArrayList<Rectangle>();
        Rectangle overallRect = null;
        if (nodes != null) {
            for (Node node : nodes) {
                LayoutConstraint constraint = node.getLayoutConstraint();
                if (constraint instanceof Bounds) {
                    Bounds bound = (Bounds) constraint;
                    int width = bound.getWidth();
                    int height = bound.getHeight();

                    if (width < 0 || height < 0) {
                        Dimension dimension = getDefaultDimensions(node);
                        width = dimension.width;
                        height = dimension.height;
                    }

                    Rectangle r =
                            new Rectangle(bound.getX(), bound.getY(), width,
                                    height);

                    rects.add(r);
                    if (overallRect != null) {
                        overallRect.union(r);
                    } else {
                        overallRect = new Rectangle(r);
                    }
                }
            }

            if (!rects.isEmpty()) {
                EAnnotation annot = createAnnotation(ANNOT_FEEDBACK_RECTANGLES);
                EList<EObject> contents = annot.getContents();
                for (Rectangle r : rects) {
                    Bounds b = NotationFactory.eINSTANCE.createBounds();
                    b.setX(r.x - overallRect.x);
                    b.setY(r.y - overallRect.y);
                    b.setHeight(r.height);
                    b.setWidth(r.width);
                    contents.add(b);
                }

                return annot;
            }
        }

        return null;
    }

    /**
     * Add feedback rectangles to the copy string. This can be used to show
     * feedback of the objects being pasted (used in fragments, for example).
     * 
     * @param newSelection
     */
    private void addFeedbackRectangles(List<Object> newSelection) {
        List<Node> nodes = new ArrayList<Node>();
        for (Object obj : newSelection) {
            if (obj instanceof Node) {
                nodes.add((Node) obj);
            }
        }

        EAnnotation annot = getFeedbackAnnotation(nodes);
        if (annot != null) {
            newSelection.add(annot);
        }
    }

    /**
     * Get the default dimensions of the given node.
     * 
     * @param node
     * @return default dimensions of the given node. If this cannot be
     *         determined then the default dimensions of a class will be
     *         returned.
     */
    private Dimension getDefaultDimensions(Node node) {
        // By default set to class size so we have atleast some dimensions
        Dimension dimension = DEFAULT_CLASS;

        if (node != null) {
            EObject element = node.getElement();

            if (element instanceof org.eclipse.uml2.uml.Package) {
                dimension = DEFAULT_PACKAGE;
            } else if (element instanceof PrimitiveType
                    || element instanceof Enumeration) {
                dimension = DEFAULT_PRIMITIVETYPE;
            }
        }

        return dimension;
    }

    /**
     * Paste the given serialized copy string into the given context.
     * 
     * @param context
     *            target of paste.
     * @param clipboard
     *            clipboard serialized copy string
     * @param mm
     *            map mode
     * @param location
     *            target location of paste
     * @return objects pasted
     * @throws Exception
     */
    public Collection<?> pasteFromString(EObject context, String clipboard,
            IMapMode mm, Point location) throws Exception {
        Set<?> pastedElements = null;
        Set<View> views = new HashSet<View>();

        if (clipboard != null && clipboard.length() > 0 && context != null) {
            IClipboardSupport helper = new BOMClipboardSupportHelper();

            PasteOperation pasteProcess =
                    new PasteOperation(null, helper, clipboard,
                            new PasteTarget(context),
                            SerializationEMFResource.LOAD_OPTIONS, null);

            pasteProcess.paste();

            // Check if any of the paste failed - if so, we want to stop the
            // entire paste operation
            Set<?> pasteFailuresObjectSet =
                    pasteProcess.getPasteFailuresObjectSet();
            if (pasteFailuresObjectSet != null) {
                for (Object object : pasteFailuresObjectSet) {
                    // Allow Annotation failures, just catch the others
                    if (!(object instanceof EAnnotation)
                            && !(object instanceof ProfileApplication)) {
                        // Stop the paste operation and have the transaction
                        // rolled back so any changes already processed are
                        // stopped
                        throw new OperationCanceledException();
                    }
                }
            }

            pastedElements = pasteProcess.getPastedElementSet();
            helper.performPostPasteProcessing(pastedElements);

            // get the measurement unit
            MeasurementUnit mu = MeasurementUnit.HIMETRIC_LITERAL;

            for (Object element : pastedElements) {
                if (element instanceof View) {
                    views.add((View) element);
                } else if (element instanceof EAnnotation) {
                    EAnnotation annot = (EAnnotation) element;
                    if (annot.getSource() != null
                            && annot.getSource().equals(ANNOT_MEASUREUNIT)
                            && !annot.getContents().isEmpty()) {
                        EAnnotation measureUnitAnnotation =
                                (EAnnotation) annot.getContents().get(0);
                        String unitName = measureUnitAnnotation.getSource();
                        mu = MeasurementUnit.get(unitName);
                    }
                    if (context instanceof View) {
                        ((View) context).getEAnnotations().remove(element);
                    }
                }
            }

            if (!views.isEmpty()) {
                /* Set the new bounds for the pasted IShapeView views */
                Set<?> edges = convertNodesConstraint(views, mu, location, mm);

                // now go through all associated edges and adjust the
                // bendpoints
                convertEdgeBendpoints(mu, edges, mm);
            }
        }

        return views;
    }

    private Set<?> convertNodesConstraint(Collection<?> views,
            MeasurementUnit mu, Point location, IMapMode mm) {
        Set<Object> edges = new HashSet<Object>();

        Rectangle rect = null;

        for (Iterator<?> iter = views.iterator(); iter.hasNext();) {
            View view = (View) iter.next();
            if (view instanceof Node) {
                LayoutConstraint constraint =
                        ((Node) view).getLayoutConstraint();

                if (constraint instanceof Bounds) {
                    Bounds bounds = (Bounds) constraint;
                    if (rect != null) {
                        rect.union(bounds.getX(),
                                bounds.getY(),
                                bounds.getWidth(),
                                bounds.getHeight());
                    } else {
                        rect =
                                new Rectangle(bounds.getX(), bounds.getY(),
                                        bounds.getWidth(), bounds.getHeight());
                    }
                }
            }
        }
        // Calculate the offset to apply to all views when pasted
        Point offset = new Point(0, 0);
        if (rect != null && location != null) {
            Point top = rect.getTopLeft();
            offset = location.getTranslated(-top.x, -top.y);
        }

        for (Iterator<?> i = views.iterator(); i.hasNext();) {
            View nextView = (View) i.next();
            if (nextView instanceof Node) {
                Node node = (Node) nextView;
                Point loc = new Point(0, 0);
                LayoutConstraint lc = node.getLayoutConstraint();
                if (lc instanceof Location) {
                    Location locC = (Location) lc;
                    loc = new Point(locC.getX(), locC.getY());
                    loc.translate(offset);
                }

                Dimension size = new Dimension(0, 0);
                if (lc instanceof Size) {
                    Size sizeC = (Size) lc;
                    size = new Dimension(sizeC.getWidth(), sizeC.getHeight());
                }

                IMapMode viewMapMode = MeasurementUnitHelper.getMapMode(mu);

                if (mm != null && !viewMapMode.equals(mm)) {
                    // convert location to native coordinates
                    loc = (Point) viewMapMode.LPtoDP(loc);
                    loc = (Point) mm.DPtoLP(loc);

                    // convert size to native coordinates
                    Dimension origSize = new Dimension(size);
                    size = (Dimension) viewMapMode.LPtoDP(size);
                    size = (Dimension) mm.DPtoLP(size);
                    if (origSize.width == -1)
                        size.width = -1;
                    if (origSize.height == -1)
                        size.height = -1;
                }

                Rectangle constraintRect = new Rectangle(loc, size);
                ViewUtil.setStructuralFeatureValue(nextView,
                        NotationPackage.eINSTANCE.getLocation_X(),
                        new Integer(constraintRect.x));
                ViewUtil.setStructuralFeatureValue(nextView,
                        NotationPackage.eINSTANCE.getLocation_Y(),
                        new Integer(constraintRect.y));
                ViewUtil.setStructuralFeatureValue(nextView,
                        NotationPackage.eINSTANCE.getSize_Width(),
                        new Integer(constraintRect.width));
                ViewUtil.setStructuralFeatureValue(nextView,
                        NotationPackage.eINSTANCE.getSize_Height(),
                        new Integer(constraintRect.height));

                edges.addAll(((Node) nextView).getTargetEdges());
                edges.addAll(((Node) nextView).getSourceEdges());
            }
        }
        return edges;
    }

    /**
     * Create annotation with the given source.
     * 
     * @param source
     *            annotation source, <code>null</code> if not required.
     * @return <code>EAnnotation</code>.
     */
    private EAnnotation createAnnotation(String source) {
        EAnnotation annot = EcoreFactory.eINSTANCE.createEAnnotation();
        if (source != null) {
            annot.setSource(source);
        }
        return annot;
    }

    /**
     * @param mu
     *            the <code>MeasurementUnit</code> for the notation diagram.
     * @param edges
     *            the <code>Set</code> of edges to convert the bendpoints of.
     * @param mm
     *            pixel mapping model
     */
    private void convertEdgeBendpoints(MeasurementUnit mu, Set<?> edges,
            IMapMode mm) {
        for (Iterator<?> i = edges.iterator(); i.hasNext();) {
            Edge nextEdge = (Edge) i.next();
            Bendpoints bendpoints = nextEdge.getBendpoints();

            if (bendpoints instanceof RelativeBendpoints) {
                RelativeBendpoints relBendpoints =
                        (RelativeBendpoints) bendpoints;
                List<?> points = relBendpoints.getPoints();
                List<RelativeBendpoint> newpoints =
                        new ArrayList<RelativeBendpoint>(points.size());
                ListIterator<?> li = points.listIterator();

                IMapMode viewMapMode = MeasurementUnitHelper.getMapMode(mu);

                while (li.hasNext()) {
                    RelativeBendpoint rb = (RelativeBendpoint) li.next();

                    Dimension source =
                            new Dimension(rb.getSourceX(), rb.getSourceY());
                    Dimension target =
                            new Dimension(rb.getTargetX(), rb.getTargetY());
                    if (mm != null && !viewMapMode.equals(mm)) {
                        source = (Dimension) viewMapMode.LPtoDP(source);
                        source = (Dimension) mm.DPtoLP(source);

                        target = (Dimension) viewMapMode.LPtoDP(target);
                        target = (Dimension) mm.DPtoLP(target);
                    }

                    newpoints.add(new RelativeBendpoint(source.width,
                            source.height, target.width, target.height));
                }

                relBendpoints.setPoints(newpoints);
            }

        }
    }

    /**
     * Copy image command for the BOM. This will copy the image of the selection
     * and also serialize the selection to the system clipboard.
     * 
     * @author njpatel
     * 
     */
    private class BOMCopyImageCommand extends BOMCopyCommand {

        private final DiagramEditPart diagramEP;

        public BOMCopyImageCommand(TransactionalEditingDomain domain,
                String label, EObject context, DiagramEditPart diagramEP,
                Collection<?> source, boolean isUndoable) {
            super(domain, label, context, source, isUndoable);
            this.diagramEP = diagramEP;
        }

        @Override
        protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
                IAdaptable info) throws ExecutionException {
            // Copy image to clipboard
            List<Object> source = getSource();

            if (source != null) {
                /* Check if the source has elements */
                boolean imageCopyDiagram = false;
                if (source.size() == 0) {
                    imageCopyDiagram = true;
                }

                Map<?, ?> epRegistry =
                        diagramEP.getViewer().getEditPartRegistry();

                List<EditPart> editParts = new ArrayList<EditPart>();
                for (Object src : source) {
                    if (src instanceof View) {
                        Object part = epRegistry.get(src);

                        if (part instanceof EditPart) {
                            editParts.add((EditPart) part);
                        }
                    }
                }

                DiagramImageGenerator imageGenerator =
                        new DiagramImageGenerator(diagramEP);

                ImageDescriptor imgDesc;
                Image img = null;
                try {
                    if (imageCopyDiagram) {
                        imgDesc =
                                imageGenerator
                                        .createSWTImageDescriptorForDiagram();
                    } else {
                        imgDesc =
                                imageGenerator
                                        .createSWTImageDescriptorForParts(editParts);

                    }

                    if (imgDesc != null) {
                        img = imgDesc.createImage();
                    }

                    if (img != null) {
                        ClipboardManager.getInstance()
                                .addToCache(img.getImageData(),
                                        ImageTransfer.getInstance());
                        /*
                         * Don't flush the cache to clipboard here as this will
                         * be done by the super method.
                         */
                    }

                } catch (OutOfMemoryError error) {
                    Activator.getDefault().getLogger().error(error);
                    String eMsg =
                            Messages.BOMCopyPasteCommandFactory_unableToCopy_err_message;
                    MessageDialog
                            .openInformation(null,
                                    Messages.BOMCopyPasteCommandFactory_outOfMemory_errDlg_title,
                                    eMsg);
                } finally {
                    if (img != null) {
                        img.dispose();
                        img = null;
                    }
                }
            }

            return super.doExecuteWithResult(monitor, info);
        }
    }

    /**
     * Copy command for the BOM. This will serialize the selection to the system
     * clipboard.
     * 
     * @author njpatel
     * 
     */
    private class BOMCopyCommand extends AbstractTransactionalCommand {

        private final List<Object> source;

        private final boolean isUndoable;

        public BOMCopyCommand(TransactionalEditingDomain domain, String label,
                EObject context, Collection<?> source, boolean isUndoable) {
            super(domain, label, getWorkspaceFiles(context));
            this.isUndoable = isUndoable;
            this.source = new ArrayList<Object>(source);
        }

        @Override
        protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
                IAdaptable info) throws ExecutionException {
            /* Check if the source has elements */
            if (source != null && !source.isEmpty()) {
                /* Copy the views */
                CustomData data =
                        new CustomData(DRAWING_SURFACE, copyToString(source)
                                .getBytes());

                /* Add the data to the clipboard manager */
                ClipboardManager.getInstance()
                        .addToCache(new ICustomData[] { data },
                                CustomDataTransfer.getInstance());
                ClipboardManager.getInstance().flushCacheToClipboard();
            }

            return CommandResult.newOKCommandResult();
        }

        protected List<Object> getSource() {
            return source;
        }

        @Override
        public boolean canUndo() {
            return isUndoable ? super.canUndo() : false;
        }

        @Override
        public boolean canRedo() {
            return isUndoable ? super.canRedo() : false;
        }
    }

    /**
     * Paste command for the BOM. This will get the serialized copy selection
     * from the system clipboard and paste it to the given object.
     * 
     * @author njpatel
     * 
     */
    private class BOMPasteCommand extends AbstractTransactionalCommand {

        private final IMapMode mm;

        private final ICustomData[] data;

        private final EObject context;

        private Point location;

        public BOMPasteCommand(TransactionalEditingDomain editingDomain,
                String label, EObject context, ICustomData[] data, IMapMode mm) {
            super(editingDomain, label, getWorkspaceFiles(context));
            this.context = context;
            this.data = data;
            this.mm = mm;
            this.location = new Point(20, 20);
        }

        public void setLocation(Point location) {
            this.location = location;
        }

        @Override
        protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
                IAdaptable info) throws ExecutionException {
            /* Paste on the target */
            if (data != null && data.length > 0) {
                List<Object> allViews = new ArrayList<Object>();
                for (int j = 0; j < data.length; j++) {
                    /* Get the string from the clipboard data */
                    String xml = new String(data[j].getData());

                    /* Paste the xml on to the target view's diagram */
                    try {
                        Collection<?> views = new ArrayList<Object>();
                        if (location != null) {
                            views =
                                    pasteFromString(getContext(),
                                            xml,
                                            mm,
                                            location);
                        }
                        allViews.addAll(views);

                        // Revalidate the project references incase the copied
                        // objects contained references to project that are not
                        // accessible
                        XpdResourcesPlugin.getDefault()
                                .revalidateReferences(context.eResource());

                    } catch (OperationCanceledException e) {
                        // Paste operation cancelled
                        return CommandResult.newCancelledCommandResult();
                    } catch (Exception e) {
                        Activator.getDefault().getLogger().error(e);
                        return CommandResult.newErrorCommandResult(e);
                    }
                }
                return CommandResult.newOKCommandResult(allViews);
            }
            return CommandResult.newOKCommandResult();
        }

        /**
         * Get the target element of this paste action.
         * 
         * @return notation or semantic target.
         */
        protected EObject getContext() {
            return context;
        }
    }
}
