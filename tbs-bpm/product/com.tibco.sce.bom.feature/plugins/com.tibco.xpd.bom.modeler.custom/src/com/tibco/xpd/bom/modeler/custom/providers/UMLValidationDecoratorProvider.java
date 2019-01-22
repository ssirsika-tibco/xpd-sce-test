package com.tibco.xpd.bom.modeler.custom.providers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Locator;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.handles.HandleBounds;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.ui.resources.FileChangeManager;
import org.eclipse.gmf.runtime.common.ui.resources.IFileObserver;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.editparts.LabelEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DecorationEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditDomain;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.AbstractDecorator;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.CreateDecoratorsOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoration;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecorator;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorProvider;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorTarget;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.IMapMode;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.MapModeUtil;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IMarkerHelpRegistry;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.modeler.custom.Activator;
import com.tibco.xpd.bom.modeler.custom.internal.util.ImageFigureEx;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationSourceLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationTargetLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.CanvasPackageEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.ClassEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.EnumerationLiteralEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.OperationEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.PackageEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.PrimitiveTypeEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.PropertyEditPart;
import com.tibco.xpd.bom.modeler.diagram.part.BOMDiagramEditorPlugin;
import com.tibco.xpd.bom.modeler.diagram.part.UMLDiagramEditor;
import com.tibco.xpd.quickfixtooltip.api.QuickFixToolTipProviderFigure;
import com.tibco.xpd.quickfixtooltip.api.QuickFixToolTipProviderFigure.IQuickFixToolTipContentProvider;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.XpdResourcesUIConstants;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.util.XpdConsts;

/**
 * @author wzurek
 */
public class UMLValidationDecoratorProvider extends AbstractProvider implements
        IDecoratorProvider {
    private static final String KEY = "validationStatus"; //$NON-NLS-1$

    private static final String MARKER_TYPE = XpdConsts.VALIDATION_MARKER_TYPE;

    @Override
    public void createDecorators(IDecoratorTarget decoratorTarget) {
        List<String> ids = getModelIds(decoratorTarget);
        if (!ids.isEmpty()) {
            decoratorTarget.installDecorator(KEY, new StatusDecorator(
                    decoratorTarget, ids));
        }
    }

    /**
     * Returns list of IDs of semantic elements that are affecting this error
     * decoration.
     * 
     * @param target
     * @return
     */
    private List<String> getModelIds(final IDecoratorTarget target) {
        final List<String> result = new ArrayList<String>();
        try {
            final View view = (View) target.getAdapter(View.class);
            TransactionUtil.getEditingDomain(view).runExclusive(new Runnable() {
                @Override
                public void run() {
                    result.addAll(doGetModelIds(target, view));
                }
            });
        } catch (Exception e) {
            BOMDiagramEditorPlugin.getInstance()
                    .logError("ViewID access failure", e); //$NON-NLS-1$          
        }
        // remove invalid ID mappings.
        for (Iterator<String> i = result.iterator(); i.hasNext();) {
            if (i.next() == null) {
                i.remove();
            }
        }
        return result;
    }

    /**
     * this runs within readonly transaction.
     */
    private List<String> doGetModelIds(final IDecoratorTarget target,
            final View view) {
        List<String> res = new ArrayList<String>();
        if (view != null) {
            // handle special cases
            EditPart part = (EditPart) target.getAdapter(EditPart.class);
            if (part instanceof AssociationSourceLabelEditPart) {
                EObject elem = view.getElement();
                if (elem instanceof Association) {
                    Property prop = ((Association) elem).getMemberEnds().get(0);
                    res.add(EcoreUtil.getURI(prop).fragment());
                }
            } else if (part instanceof AssociationTargetLabelEditPart) {
                EObject elem = view.getElement();
                if (elem instanceof Association) {
                    Property prop = ((Association) elem).getMemberEnds().get(1);
                    res.add(EcoreUtil.getURI(prop).fragment());
                }
            } else {
                // handle generic case
                if (view.isSetElement() && view.getElement() != null) {
                    res.add(EcoreUtil.getURI(view.getElement()).fragment());
                }

                // Additionally for a Class or PrimitiveType we need to register
                // all generalizations with the view
                if (part instanceof ClassEditPart
                        || part instanceof PrimitiveTypeEditPart
                        && view.getElement() instanceof Classifier) {
                    Classifier cl = (Classifier) view.getElement();
                    EList<Generalization> gens = cl.getGeneralizations();

                    if (gens != null) {
                        for (Generalization gen : gens) {
                            res.add(EcoreUtil.getURI(gen).fragment());
                        }
                    }
                }
            }
        }

        return res;
    }

    /**
     * Avaialabe for all CreateDecoratorsOperation operations.
     */
    @Override
    public boolean provides(IOperation operation) {
        if (operation instanceof CreateDecoratorsOperation) {
            IDecoratorTarget decoratorTarget =
                    ((CreateDecoratorsOperation) operation)
                            .getDecoratorTarget();
            if (decoratorTarget != null) {
                EditPart editPart =
                        (EditPart) decoratorTarget.getAdapter(EditPart.class);
                if (editPart instanceof org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart
                        || editPart instanceof AbstractConnectionEditPart) {

                    EditDomain ed = editPart.getViewer().getEditDomain();
                    if (ed instanceof DiagramEditDomain) {
                        if (((DiagramEditDomain) ed).getEditorPart() instanceof UMLDiagramEditor) {
                            return !getModelIds(decoratorTarget).isEmpty();
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Status decorator that is listening to file changes.
     */
    public static class StatusDecorator extends AbstractDecorator {

        private final List<String> ids;

        private final ToolTipContentProvider contentProvider;

        private static final Map<Diagram, MarkerObserver> markerObserverMap =
                Collections
                        .synchronizedMap(new HashMap<Diagram, UMLValidationDecoratorProvider.MarkerObserver>());

        public StatusDecorator(IDecoratorTarget decoratorTarget,
                List<String> ids) {
            super(decoratorTarget);
            this.ids = ids;
            contentProvider = new ToolTipContentProvider();
        }

        @Override
        public void refresh() {
            removeDecoration();

            View view = (View) getDecoratorTarget().getAdapter(View.class);
            EditPart editPart =
                    (EditPart) getDecoratorTarget().getAdapter(EditPart.class);
            if (view == null || view.eResource() == null
                    || editPart instanceof CanvasPackageEditPart
                    || editPart.getRoot() == null) {
                return;
            }

            IResource resource = getResource(view);
            // make sure we have a resource and that it exists in an open
            // project
            if (resource == null || !resource.exists()) {
                return;
            }

            // query for all the validation markers of the current resource
            IMarker[] markers = null;
            try {
                markers =
                        resource.findMarkers(MARKER_TYPE,
                                true,
                                IResource.DEPTH_INFINITE);
            } catch (CoreException e) {
                BOMDiagramEditorPlugin.getInstance()
                        .logError("Validation marker refresh failure", e); //$NON-NLS-1$
            }
            if (markers == null || markers.length == 0) {
                return;
            }

            String elementId = ViewUtil.getIdStr(view);
            if (elementId == null || elementId.length() == 0) {
                return;
            }
            EObject element = view.getElement();

            // Views of type "Note" will not have elements asssigned to them as
            // semantic parts.
            if (element != null) {
                int severity = -1;
                List<IMarker> relevantMarkers = new ArrayList<IMarker>();
                Collection<String> elementUris = getModelUris();
                for (String elementUri : elementUris) {
                    for (int i = 0; i < markers.length; i++) {
                        IMarker marker = markers[i];
                        String attribute =
                                marker.getAttribute(IMarker.LOCATION, "-"); //$NON-NLS-1$
                        if (attribute.equals(elementUri)) {
                            relevantMarkers.add(marker);
                            // Work out worst severity level
                            int nextSeverity =
                                    marker.getAttribute(IMarker.SEVERITY,
                                            IMarker.SEVERITY_INFO);
                            severity =
                                    (nextSeverity > severity) ? nextSeverity
                                            : severity;
                        }
                    }
                }
                // Markers found so create decorator
                if (severity >= 0) {
                    // add decoration
                    createDecoration(view, editPart, severity, relevantMarkers);
                }
            }
        }

        private void createDecoration(View view, EditPart editPart,
                int severity, List<IMarker> markers) {
            if (editPart instanceof GraphicalEditPart) {
                Image img = getImage(severity);
                if (view instanceof Edge) {
                    setDecoration(getDecoratorTarget()
                            .addConnectionDecoration(img, 50, true));
                } else {
                    int margin = -1;
                    IDecoratorTarget.Direction direction =
                            IDecoratorTarget.Direction.NORTH_EAST;
                    if (editPart instanceof GraphicalEditPart) {
                        margin =
                                MapModeUtil
                                        .getMapMode(((GraphicalEditPart) editPart)
                                                .getFigure()).DPtoLP(margin);
                    }
                    if (editPart instanceof PackageEditPart) {
                        direction = IDecoratorTarget.Direction.NORTH;
                        setDecoration(setPackageDecoration(getDecoratorTarget(),
                                img));
                    } else {
                        ImageRegistry reg =
                                XpdResourcesUIActivator.getDefault()
                                        .getImageRegistry();
                        if (editPart instanceof PropertyEditPart
                                || editPart instanceof OperationEditPart
                                || editPart instanceof EnumerationLiteralEditPart) {
                            if (severity == IMarker.SEVERITY_ERROR) {
                                img =
                                        reg.get(XpdResourcesUIConstants.ERROR_OVERLAY);
                            } else if (severity == IMarker.SEVERITY_WARNING) {
                                img =
                                        reg.get(XpdResourcesUIConstants.WARNING_OVERLAY);
                            }
                            setDecoration(getDecoratorTarget()
                                    .addShapeDecoration(img,
                                            IDecoratorTarget.Direction.SOUTH_WEST,
                                            margin,
                                            true));
                        } else if (editPart instanceof LabelEditPart) {
                            if (severity == IMarker.SEVERITY_ERROR) {
                                img =
                                        reg.get(XpdResourcesUIConstants.ERROR_OVERLAY);
                            } else if (severity == IMarker.SEVERITY_WARNING) {
                                img =
                                        reg.get(XpdResourcesUIConstants.WARNING_OVERLAY);
                            }
                            setDecoration(getDecoratorTarget()
                                    .addShapeDecoration(img,
                                            IDecoratorTarget.Direction.WEST,
                                            1,
                                            true));
                        } else {
                            setDecoration(getDecoratorTarget()
                                    .addShapeDecoration(img,
                                            direction,
                                            margin,
                                            true));
                        }
                    }
                }
                contentProvider.setMarkers(markers);
                getDecoration().setToolTip(new QuickFixToolTipProviderFigure(
                        view, contentProvider));
            }
        }

        private IDecoration setPackageDecoration(
                final DecorationEditPolicy.DecoratorTarget decoratorTarget,
                Image img) {
            GraphicalEditPart gep =
                    (GraphicalEditPart) getDecoratorTarget()
                            .getAdapter(GraphicalEditPart.class);
            IMapMode mm = MapModeUtil.getMapMode(gep.getFigure());
            final ImageFigureEx figure = new ImageFigureEx();
            figure.setImage(img);
            figure.setSize(mm.DPtoLP(img.getBounds().width),
                    mm.DPtoLP(img.getBounds().height));
            final int margin = mm.DPtoLP(-5);
            final IFigure reference = gep.getFigure();

            Locator loc = new Locator() {
                @Override
                public void relocate(IFigure f) {
                    Rectangle bounds =
                            reference instanceof HandleBounds ? ((HandleBounds) reference)
                                    .getHandleBounds().getCopy() : reference
                                    .getBounds().getCopy();
                    reference.translateToAbsolute(bounds);
                    f.translateToRelative(bounds);
                    int halfHeight = reference.getSize().height / 2 - margin;

                    Dimension shift =
                            new Dimension(-f.getBounds().width + margin,
                                    -halfHeight - margin + 1);
                    f.setLocation(bounds.getCenter().getTranslated(shift));
                }
            };
            return decoratorTarget.addDecoration(figure, loc, true);
        }

        private Image getImage(int severity) {
            String imageName = ISharedImages.IMG_OBJS_ERROR_TSK;
            switch (severity) {
            case IMarker.SEVERITY_ERROR:
                imageName = ISharedImages.IMG_OBJS_ERROR_TSK;
                break;
            case IMarker.SEVERITY_WARNING:
                imageName = ISharedImages.IMG_OBJS_WARN_TSK;
                break;
            default:
                imageName = ISharedImages.IMG_OBJS_INFO_TSK;
            }
            return PlatformUI.getWorkbench().getSharedImages()
                    .getImage(imageName);
        }

        private static IResource getResource(View view) {
            Resource model = view.eResource();
            if (model != null) {
                return WorkspaceSynchronizer.getFile(model);
            }
            return null;
        }

        @Override
        public void activate() {
            View view = (View) getDecoratorTarget().getAdapter(View.class);

            if (view != null && view.getDiagram() != null) {
                Diagram diagramView = view.getDiagram();

                IFile file =
                        WorkspaceSynchronizer.getFile(diagramView.eResource());
                if (file != null) {
                    MarkerObserver markerObserver =
                            markerObserverMap.get(diagramView);
                    if (markerObserver == null) {
                        markerObserver = new MarkerObserver(diagramView);
                        markerObserverMap.put(diagramView, markerObserver);
                    }

                    markerObserver.registerDecorator(this);
                }
                refresh();
            }
        }

        @Override
        public void deactivate() {

            View view = (View) getDecoratorTarget().getAdapter(View.class);
            if (view != null && view.getDiagram() != null) {
                MarkerObserver markerObserver =
                        markerObserverMap.get(view.getDiagram());
                if (markerObserver != null) {
                    markerObserver.unregisterDecorator(this);
                    if (!markerObserver.isRegistered()) {
                        markerObserverMap.remove(view.getDiagram());
                    }
                }
            }

            super.deactivate();
        }

        public Collection<String> getModelUris() {
            return ids;
        }
    }

    /**
     * File observer that refresh decorators.
     */
    static class MarkerObserver implements IFileObserver {

        private HashMap<String, List<StatusDecorator>> mapOfIdsToDecorators =
                null;

        private boolean isRegistered = false;

        private Diagram diagramView;

        private Logger log = LoggerFactory.createLogger(Activator.PLUGIN_ID);

        private MarkerObserver(Diagram diagramView) {
            this.diagramView = diagramView;
        }

        private void registerDecorator(StatusDecorator decorator) {
            if (decorator == null) {
                return;
            }

            if (mapOfIdsToDecorators == null) {
                mapOfIdsToDecorators =
                        new HashMap<String, List<StatusDecorator>>();
            }

            Collection<String> modelUris = decorator.getModelUris();
            for (String modelId : modelUris) {

                /* Add to the list */
                List<StatusDecorator> list = mapOfIdsToDecorators.get(modelId);
                if (list == null) {
                    list = new ArrayList<StatusDecorator>(2);
                    list.add(decorator);
                    mapOfIdsToDecorators.put(modelId, list);
                } else if (!list.contains(decorator)) {
                    list.add(decorator);
                }
            }

            /* Register with the file change manager */
            if (!isRegistered()) {
                FileChangeManager.getInstance().addFileObserver(this);
                isRegistered = true;
            }
        }

        private void unregisterDecorator(StatusDecorator decorator) {
            /* Return if invalid decorator */
            if (decorator == null) {
                return;
            }

            /* Return if the decorator has invalid view id */

            Collection<String> modelUris = decorator.getModelUris();
            for (String decoratorViewId : modelUris) {

                if (mapOfIdsToDecorators != null) {
                    List<StatusDecorator> list =
                            mapOfIdsToDecorators.get(decoratorViewId);
                    if (list != null) {
                        list.remove(decorator);
                        if (list.isEmpty()) {
                            mapOfIdsToDecorators.remove(decoratorViewId);
                        }
                    }
                    if (mapOfIdsToDecorators.isEmpty()) {
                        mapOfIdsToDecorators = null;
                    }
                }
            }
            if (mapOfIdsToDecorators == null) {
                /* Unregister with the file change manager */
                if (isRegistered()) {
                    FileChangeManager.getInstance().removeFileObserver(this);
                    isRegistered = false;
                }
            }
        }

        @Override
        public void handleFileRenamed(IFile oldFile, IFile file) {
        }

        @Override
        public void handleFileMoved(IFile oldFile, IFile file) {
        }

        @Override
        public void handleFileDeleted(IFile file) { /* Empty Code */
        }

        @Override
        public void handleFileChanged(IFile file) { /* Empty Code */
        }

        @Override
        public void handleMarkerAdded(IMarker marker) {
            if (marker.getAttribute(IMarker.LOCATION, null) != null) {
                handleMarkerChanged(marker);
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public void handleMarkerDeleted(IMarker marker, Map attributes) {
            if (mapOfIdsToDecorators == null) {
                return;
            }
            // Extract the element guid from the marker and retrieve
            // corresponding view
            String elementId = (String) attributes.get(IMarker.LOCATION);
            if (elementId != null
                    && mapOfIdsToDecorators.get(elementId) != null) {
                List<StatusDecorator> list =
                        mapOfIdsToDecorators.get(elementId);
                if (list != null && !list.isEmpty()) {
                    refreshDecorators(list);
                }
            }
        }

        @Override
        public void handleMarkerChanged(IMarker marker) {
            try {
                if (mapOfIdsToDecorators == null
                        || !marker.isSubtypeOf(MARKER_TYPE)) {
                    return;
                }
            } catch (CoreException e) {
                log.error(e);
            }

            // Extract the element ID list from the marker and retrieve
            // corresponding view
            String elementId = marker.getAttribute(IMarker.LOCATION, ""); //$NON-NLS-1$
            if (elementId != null
                    && mapOfIdsToDecorators.get(elementId) != null) {
                List<StatusDecorator> list =
                        mapOfIdsToDecorators.get(elementId);
                if (list != null && !list.isEmpty()) {
                    refreshDecorators(list);
                }
            }
        }

        private void refreshDecorators(List<StatusDecorator> decorators) {
            final List<StatusDecorator> decoratorsToRefresh = decorators;
            PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (diagramView != null) {
                            EObject semanticElement =
                                    ViewUtil.resolveSemanticElement(diagramView);

                            if (semanticElement != null) {
                                TransactionalEditingDomain ed =
                                        ((TransactionalEditingDomain) WorkingCopyUtil
                                                .getEditingDomain(semanticElement));

                                if (ed != null) {
                                    ed.runExclusive(new Runnable() {
                                        @Override
                                        public void run() {
                                            for (Iterator<StatusDecorator> it =
                                                    decoratorsToRefresh
                                                            .iterator(); it
                                                    .hasNext();) {
                                                IDecorator decorator =
                                                        it.next();
                                                if (decorator != null) {
                                                    decorator.refresh();
                                                }
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    } catch (Exception e) {
                        BOMDiagramEditorPlugin.getInstance()
                                .logError("Decorator refresh failure", e); //$NON-NLS-1$
                    }
                }
            });
        }

        private boolean isRegistered() {
            return isRegistered;
        }

        private String getType(IMarker marker) {
            try {
                return marker.getType();
            } catch (CoreException e) {
                BOMDiagramEditorPlugin.getInstance()
                        .logError("Validation marker refresh failure", e); //$NON-NLS-1$
                return ""; //$NON-NLS-1$
            }
        }
    }

    /**
     * Content provider for the quick-fix tooltip figure.
     * 
     * @author njpatel
     * 
     */
    private static class ToolTipContentProvider implements
            IQuickFixToolTipContentProvider {

        private List<IMarker> markers;

        private final IMarkerHelpRegistry markerHelpRegistry;

        public ToolTipContentProvider() {
            markerHelpRegistry = IDE.getMarkerHelpRegistry();
        }

        public void setMarkers(List<IMarker> markers) {
            this.markers = markers;
        }

        @Override
        public Collection<IMarkerResolution> getMarkerResolutions(IMarker marker) {
            IMarkerResolution[] resolutions =
                    markerHelpRegistry.getResolutions(marker);
            if (resolutions != null) {
                return Arrays.asList(resolutions);
            }
            return Collections.emptyList();
        }

        @Override
        public Collection<IMarker> getMarkers(Object markerHost) {
            return markers;
        }
    }
}
