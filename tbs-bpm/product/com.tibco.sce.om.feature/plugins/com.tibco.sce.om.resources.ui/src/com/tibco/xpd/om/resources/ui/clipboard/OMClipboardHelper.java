/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.clipboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.ui.action.actions.global.ClipboardContentsHelper;
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
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.RelativeBendpoints;
import org.eclipse.gmf.runtime.notation.Size;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.notation.datatype.RelativeBendpoint;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.dnd.ImageTransfer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.om.core.om.OrgElementType;
import com.tibco.xpd.om.core.om.OrgTypedElement;
import com.tibco.xpd.om.resources.ui.OMResourcesUIActivator;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;

@SuppressWarnings("restriction")
public final class OMClipboardHelper {
    /**
     * Clipboard format type for copy/paste from OM diagrams
     */
    public static final String DRAWING_SURFACE = "OM Drawing Surface"; //$NON-NLS-1$

    /**
     * Clipboard format type for paste target hint. This will contain the visual
     * id of the intended paste target in the OM diagram.
     */
    public static final String OM_HINT = "OM Hint"; //$NON-NLS-1$

    /**
     * Added to the paste request extended data to indicate the target position
     * of the paste.
     */
    public static final String PASTE_POSITION = "position"; //$NON-NLS-1$

    /**
     * Measurement annotation
     */
    private static final String ANNOT_MEASUREUNIT = "MeasureUnit"; //$NON-NLS-1$

    public static final String ANNOT_TYPERESOURCE = "TypeResource"; //$NON-NLS-1$

    private static final char SEPARATOR = 1;

    private static final OMClipboardHelper INSTANCE = new OMClipboardHelper();

    /**
     * Private constructor. Use {@link #getInstance()} to get the singleton
     * instanceof of this manager class.
     */
    private OMClipboardHelper() {
        // Private constructor
    }

    /**
     * Get singleton instance of this class.
     * 
     * @return INSTANCE
     */
    public static final OMClipboardHelper getInstance() {
        return INSTANCE;
    }

    /**
     * Ask the user if they wish to continue with the paste. This will be shown
     * when elements with types set are being pasted into external resources.
     * 
     * @param shell
     * @return <code>true</code> to continue with paste.
     */
    public static boolean shouldContinuePasteIntoExternalResource(
            final Shell shell) {
        final Boolean[] res = new Boolean[] { false };
        shell.getDisplay().syncExec(new Runnable() {
            public void run() {
                res[0] =
                        MessageDialog.openQuestion(shell,
                                Messages.OMClipboardHelper_pasteDialog_title,
                                Messages.OMClipboardHelper_pasteDialog_message);
            }
        });
        return res[0];
    }

    /**
     * Get a copy command to copy the given source.
     * 
     * @param editingDomain
     *            transactional editing domain
     * @param label
     *            command label
     * @param context
     *            the copy context
     * @param source
     *            elements to be copied
     * @return {@link ICommand}
     */
    public ICommand getCopyCommand(TransactionalEditingDomain editingDomain,
            String label, EObject context, Collection<?> source) {
        return new OMCopyCommand(editingDomain, label, context, source, false);
    }

    /**
     * Get the copy command to copy the given source. This will also generate an
     * image of the source and place it on the clipboard.
     * 
     * @param editingDomain
     *            transactional editing domain
     * @param label
     *            command label
     * @param context
     *            copy context
     * @param diagramEP
     *            diagram's edit part
     * @param source
     *            elements to copy
     * @param isUndoable
     *            <code>true</code> if this copy command should be undoable.
     * @return {@link ICommand}
     */
    public ICommand getCopyWithImageCommand(
            TransactionalEditingDomain editingDomain, String label,
            EObject context, DiagramEditPart diagramEP, Collection<?> source,
            boolean isUndoable) {
        return new OMCopyImageCommand(editingDomain, label, context, diagramEP,
                source, isUndoable);
    }

    /**
     * Get the command to paste the OM elements from the clipboard. This will
     * get the data from the clipboard.
     * 
     * @param editingDomain
     *            transactional editing domain
     * @param label
     *            command label
     * @param context
     *            paste target
     * @param mm
     *            mapper that will map the units in the model to pixels
     * @return {@link ICommand} or <code>null</code> if there is no data in the
     *         clipboard.
     */
    public ICommand getPasteCommand(TransactionalEditingDomain editingDomain,
            String label, EObject context, IMapMode mm) {
        ICustomData[] data =
                ClipboardManager.getInstance()
                        .getClipboardData(DRAWING_SURFACE,
                                ClipboardContentsHelper.getInstance());

        if (data != null) {
            return new OMPasteCommand(editingDomain, label, context, data, mm);
        }
        return null;
    }

    /**
     * Get the command to paste the OM elements from the clipboard.
     * 
     * @param editingDomain
     *            transactional editing domain
     * @param label
     *            command label
     * @param context
     *            paste target
     * @param data
     *            custom data from the clipboard to paste
     * @param mm
     *            mapper that will map the units in the model to pixels
     * @return {@link ICommand}
     */
    public ICommand getPasteCommand(TransactionalEditingDomain editingDomain,
            String label, EObject context, ICustomData[] data, IMapMode mm) {

        return new OMPasteCommand(editingDomain, label, context, data, mm);
    }

    public ICommand getPasteCommand(TransactionalEditingDomain editingDomain,
            String label, EObject context, ICustomData[] data, IMapMode mm,
            Point location) {

        OMPasteCommand pasteCommand =
                new OMPasteCommand(editingDomain, label, context, data, mm);
        pasteCommand.setLocation(location);
        return pasteCommand;
    }

    /**
     * Check if the item in the clipboard has an hint for the intended target of
     * the paste.
     * 
     * @see #getHint()
     * @see #canPaste(View, String)
     * 
     * @return <code>true</code> if hint is available, <code>false</code>
     *         otherwise.
     */
    public static boolean hasHint() {
        return ClipboardManager.getInstance()
                .doesClipboardHaveData(OMClipboardHelper.OM_HINT,
                        ClipboardContentsHelper.getInstance());
    }

    /**
     * Get the hint of the intended paste target from the clipboard. This will
     * be the combination of the diagram and visual id of View that is the
     * intended target. Use {@link #getViewHint(Diagram)} to get the actual View
     * id from this hint.
     * 
     * @see #hasHint()
     * @see #canPaste(View, String)
     * @see #getViewHint(Diagram)
     * 
     * @return visual id of the intended target or <code>null</code> if no hint
     *         available.
     */
    public static String getHint() {
        ICustomData[] data =
                ClipboardManager.getInstance()
                        .getClipboardData(OMClipboardHelper.OM_HINT,
                                ClipboardContentsHelper.getInstance());

        if (data != null && data.length > 0) {
            return new String(data[0].getData());
        }
        return null;
    }

    /**
     * Get the view id of the intended paste target of the objects in the
     * clipboard.
     * 
     * @see #hasHint()
     * @see #canPaste(View, String)
     * @see #getHint()
     * 
     * @param diagram
     * @return
     */
    public static String getViewHint(Diagram diagram) {
        if (diagram != null) {
            String hint = getHint();
            if (hint != null && hint.indexOf(SEPARATOR) > 0) {
                hint = hint.substring(hint.indexOf(SEPARATOR) + 1);
            }

            return hint;
        }
        return null;
    }

    /**
     * Check if the hint allows the paste into the given target.
     * 
     * @see #hasHint()
     * @see #getHint()
     * 
     * @param target
     *            paste target
     * @param hint
     *            hint of intended target from the clipboard
     * @return <code>true</code> if the elements in the clipboard can be pasted
     *         into the target, <code>false</code> otherwise.
     */
    public static boolean canPaste(View target, String hint) {
        boolean can = false;
        if (target != null && hint != null) {

            can = hint.equals(target.getType());

            if (!can) {
                // Check if the parent of the target is the intended target
                can =
                        target.eContainer() instanceof View
                                && hint.equals(((View) target.eContainer())
                                        .getType());
            }

            if (!can) {
                // Check if any of the target's children are the intended
                // target
                EList<?> children = target.getChildren();
                if (children != null) {
                    for (Object child : children) {
                        if (child instanceof View
                                && hint.equals(((View) child).getType())) {
                            can = true;
                            break;
                        }
                    }
                }
            }
        }
        return can;
    }

    /**
     * Get the hint to add to the clipboard. The hint will provide information
     * during paste where this paste can be applied. The hint is the visual id
     * of the intended target parent.
     * 
     * @param views
     * @return
     */
    private String createHint(EObject eo) {
        if (eo != null) {
            View view = null;
            if (eo instanceof View) {
                view = (View) eo;
            } else {
                ECrossReferenceAdapter adapter =
                        ECrossReferenceAdapter.getCrossReferenceAdapter(eo);
                if (adapter != null) {
                    Collection<Setting> references =
                            adapter.getInverseReferences(eo);
                    if (references != null) {
                        for (Setting ref : references) {
                            if (ref.getEStructuralFeature() == NotationPackage.eINSTANCE
                                    .getView_Element()
                                    && ref.getEObject() instanceof View) {
                                view = (View) ref.getEObject();
                                break;
                            }
                        }
                    }
                }
            }
            if (view != null) {
                EObject parent = view.eContainer();
                if (parent instanceof View) {
                    StringBuffer hint = new StringBuffer();
                    Diagram diagram = ((View) parent).getDiagram();
                    if (diagram != null) {
                        hint.append(diagram.getType());
                        hint.append(SEPARATOR);
                    }

                    return hint.append(((View) parent).getType()).toString();
                }
            }
        }

        return null;
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
     *            target location of paste, <code>null</code> if no location
     *            available - the pasted element will be offset relative to the
     *            source.
     * @return objects pasted
     * @throws Exception
     */
    private Collection<?> pasteFromString(EObject context, String clipboard,
            IMapMode mm, Point location) throws Exception {
        Set<?> pastedElements = null;
        Set<View> views = new HashSet<View>();

        if (clipboard != null && clipboard.length() > 0 && context != null) {
            IClipboardSupport helper = new OMClipboardSupportHelper();

            PasteOperation pasteProcess =
                    new PasteOperation(null, helper, clipboard,
                            new PasteTarget(context),
                            SerializationEMFResource.LOAD_OPTIONS, null);

            pasteProcess.paste();

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
                Set<?> edges =
                        convertNodesConstraint(context, views, mu, location, mm);

                // now go through all associated edges and adjust the
                // bendpoints
                convertEdgeBendpoints(mu, edges, mm);
            }
        }

        return views;
    }

    @SuppressWarnings("unchecked")
    private Set<?> convertNodesConstraint(EObject context, Collection<?> views,
            MeasurementUnit mu, Point location, IMapMode mm) {
        Set<?> edges = new HashSet<Object>();

        Rectangle rect = null;

        for (Iterator<?> iter = views.iterator(); iter.hasNext();) {
            View view = (View) iter.next();
            if (view instanceof Node) {
                LayoutConstraint constraint =
                        ((Node) view).getLayoutConstraint();

                if (constraint instanceof Bounds) {
                    Bounds bounds = (Bounds) constraint;
                    if (rect != null) {
                        rect.union(bounds.getX(), bounds.getY(), bounds
                                .getWidth(), bounds.getHeight());
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
        if (rect != null) {
            Point top = rect.getTopLeft();
            if (location != null) {
                offset = location.getTranslated(-top.x, -top.y);
            } else {
                int offSetBy = mm != null ? mm.DPtoLP(10) : 10;
                offset = new Point(offSetBy, offSetBy);
            }
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
                    if (context instanceof View) {
                        loc = translate((View) context, loc, offset);
                    } else {
                        loc.translate(offset);
                    }
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
     * Translate the location so that it does not overlap with an existing Node
     * in the diagram.
     * 
     * @param context
     *            context view being pasted into
     * @param location
     *            location of the node being pasted
     * @param offset
     *            offset
     * @return new location of where it should go
     */
    private Point translate(View context, Point location, Point offset) {

        if (context != null && location != null && offset != null
                && (location.x > 0 || location.y > 0)
                && (offset.x > 0 || offset.y > 0)) {
            EList<?> children = context.getChildren();
            location.translate(offset);
            if (children != null) {
                for (Object child : children) {
                    if (child instanceof Node) {
                        LayoutConstraint constraint =
                                ((Node) child).getLayoutConstraint();

                        if (constraint instanceof Location) {
                            int x = ((Location) constraint).getX();
                            int y = ((Location) constraint).getY();

                            if (location.x == x && location.y == y) {
                                location = translate(context, location, offset);
                            }
                        }
                    }
                }
            }
        }

        return location;
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
     * This will get any auxiliary objects that should be copied with the given
     * selection, for example the Diagram if an Organization view is being
     * copied.
     * 
     * @param selection
     * @return
     */
    private Collection<Object> getAuxiliaryObjectsToCopy(Collection<?> selection) {
        List<Object> objs = new ArrayList<Object>();

        for (Object sel : selection) {
            if (sel instanceof Node) {
                DiagramLinkStyle style =
                        (DiagramLinkStyle) ((Node) sel)
                                .getStyle(NotationPackage.eINSTANCE
                                        .getDiagramLinkStyle());
                if (style != null) {
                    Diagram diagram = style.getDiagramLink();
                    if (diagram != null) {
                        objs.add(diagram);
                    }
                }
            }
        }

        return objs;
    }

    /**
     * Copy command for the BOM. This will serialize the selection to the system
     * clipboard.
     * 
     * @author njpatel
     * 
     */
    private class OMCopyCommand extends AbstractTransactionalCommand {

        private final List<Object> source;

        private final OMClipboardSupportHelper clipboardHelper;

        private final boolean isUndoable;

        public OMCopyCommand(TransactionalEditingDomain domain, String label,
                EObject context, Collection<?> source, boolean isUndoable) {
            super(domain, label, getWorkspaceFiles(context));
            this.isUndoable = isUndoable;
            this.source = new ArrayList<Object>(source);

            clipboardHelper = new OMClipboardSupportHelper();
        }

        @Override
        protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
                IAdaptable info) throws ExecutionException {

            /* Check if the source has elements */
            if (source != null && !source.isEmpty()) {

                /* Copy the views */
                List<CustomData> data = new ArrayList<CustomData>();
                try {
                    data.add(new CustomData(DRAWING_SURFACE,
                            copyToString(source).getBytes()));

                    if (source.get(0) instanceof EObject) {
                        String hint = createHint((EObject) source.get(0));
                        if (hint != null) {
                            data.add(new CustomData(OM_HINT, hint.getBytes()));
                        }
                    }

                    /* Add the data to the clipboard manager */
                    ClipboardManager.getInstance().addToCache(data
                            .toArray(new ICustomData[data.size()]),
                            CustomDataTransfer.getInstance());
                    ClipboardManager.getInstance().flushCacheToClipboard();
                } catch (Exception e) {
                    CommandResult.newErrorCommandResult(e);
                }
            }

            return CommandResult.newOKCommandResult();
        }

        /**
         * Serialize the given selection to put onto the clipboard.
         * 
         * @param selection
         *            copy selection
         * @return <code>String</code>.
         * @throws Exception
         */
        protected String copyToString(List<?> selection) throws Exception {
            String copyStr = null;

            if (selection != null && !selection.isEmpty()) {
                List<Object> newSelection = new ArrayList<Object>(selection);

                /*
                 * Sort the list of objects that can be copied, remove any
                 * objects that don't need to be in the list as its parent is
                 * already in the list.
                 */
                ClipboardSupportUtil.getCopyElements(newSelection);

                Collection<Object> otherObjs =
                        getAuxiliaryObjectsToCopy(newSelection);
                if (otherObjs != null && !otherObjs.isEmpty()) {
                    newSelection.addAll(otherObjs);
                }

                // Find a view
                for (Object sel : selection) {
                    if (sel instanceof View) {
                        // add the measurement unit in an annotation.
                        Diagram dgrm = ((View) sel).getDiagram();
                        EAnnotation measureUnitAnnotation =
                                createAnnotation(ANNOT_MEASUREUNIT);
                        measureUnitAnnotation.getContents()
                                .add(createAnnotation(dgrm.getMeasurementUnit()
                                        .getName()));
                        newSelection.add(measureUnitAnnotation);
                        break;

                    }
                }

                /*
                 * If any elements are of OrgElementType and have a type set
                 * then keep track of the source of the type. If these elements
                 * are pasted in another resource then the types will have to be
                 * cleared.
                 */
                Resource typeResource = getTypeResource(selection);
                if (typeResource != null) {
                    EAnnotation annot = createAnnotation(ANNOT_TYPERESOURCE);
                    annot.getContents().add(createAnnotation(typeResource
                            .getURI().toString()));
                    newSelection.add(annot);
                }

                CopyOperation copyOperation =
                        new CopyOperation(new NullProgressMonitor(),
                                clipboardHelper, newSelection,
                                new HashMap<Object, Object>());
                copyStr = copyOperation.copy();
            }
            return copyStr;
        }

        private Resource getTypeResource(Collection<?> selection) {
            if (selection != null) {
                for (Object sel : selection) {
                    if (sel instanceof View) {
                        sel = ((View) sel).getElement();
                    }
                    if (sel instanceof OrgTypedElement) {
                        OrgElementType type = ((OrgTypedElement) sel).getType();
                        if (type != null && type.eResource() != null) {
                            return type.eResource();
                        }
                    }
                    if (sel instanceof EObject) {
                        TreeIterator<EObject> contents =
                                ((EObject) sel).eAllContents();

                        if (contents != null) {
                            while (contents.hasNext()) {
                                Object next = contents.next();

                                if (next instanceof OrgTypedElement) {
                                    OrgElementType type =
                                            ((OrgTypedElement) next).getType();
                                    if (type != null
                                            && type.eResource() != null) {
                                        return type.eResource();
                                    }
                                }
                            }
                        }
                    }
                }
            }

            return null;
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

        /**
         * Create annotation with the given source.
         * 
         * @param source
         *            annotation source, <code>null</code> if not required.
         * @return <code>EAnnotation</code>.
         */
        protected EAnnotation createAnnotation(String source) {
            EAnnotation annot = EcoreFactory.eINSTANCE.createEAnnotation();
            if (source != null) {
                annot.setSource(source);
            }
            return annot;
        }
    }

    /**
     * Copy image command for the BOM. This will copy the image of the selection
     * and also serialize the selection to the system clipboard.
     * 
     * @author njpatel
     * 
     */
    private class OMCopyImageCommand extends OMCopyCommand {

        private final DiagramEditPart diagramEP;

        public OMCopyImageCommand(TransactionalEditingDomain domain,
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
                        ClipboardManager.getInstance().addToCache(img
                                .getImageData(),
                                ImageTransfer.getInstance());
                        /*
                         * Don't flush the cache to clipboard here as this will
                         * be done by the super method.
                         */
                    }

                } catch (OutOfMemoryError error) {
                    OMResourcesUIActivator.getDefault().getLogger()
                            .error(error);
                    String eMsg =
                            Messages.OMClipboardHelper_UnableToCopyImage_error_message;
                    MessageDialog
                            .openInformation(null,
                                    Messages.OMClipboardHelper_outOfMemory_error_message,
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
     * Paste command for the BOM. This will get the serialized copy selection
     * from the system clipboard and paste it to the given object.
     * 
     * @author njpatel
     * 
     */
    private class OMPasteCommand extends AbstractTransactionalCommand {

        private final IMapMode mm;

        private final ICustomData[] data;

        private final EObject context;

        private Point location;

        public OMPasteCommand(TransactionalEditingDomain editingDomain,
                String label, EObject context, ICustomData[] data, IMapMode mm) {
            super(editingDomain, label, getWorkspaceFiles(context));
            this.context = context;
            this.data = data;
            this.mm = mm;
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
                        views =
                                pasteFromString(getContext(), xml, mm, location);
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
                        OMResourcesUIActivator.getDefault().getLogger()
                                .error(e);
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
