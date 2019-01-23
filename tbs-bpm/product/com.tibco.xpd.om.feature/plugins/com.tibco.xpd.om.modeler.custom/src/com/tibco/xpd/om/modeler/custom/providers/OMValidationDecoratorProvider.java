/**
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.modeler.custom.providers;

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
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.ui.resources.FileChangeManager;
import org.eclipse.gmf.runtime.common.ui.resources.IFileObserver;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.LabelEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditDomain;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.AbstractDecorator;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.CreateDecoratorsOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecorator;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorProvider;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorTarget;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.MapModeUtil;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IMarkerHelpRegistry;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import com.tibco.xpd.om.modeler.custom.Activator;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrgModelEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.PositionEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.LabelAuthorBadgeEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.LabelDateCreatedBadgeEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.LabelDateModifiedBadgeEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.LabelVersionBadgeEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.OrgModelBadgeEditPart;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelDiagramEditor;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelDiagramEditorPlugin;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.OrganizationSubEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.PositionSubEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.custom.LabelOrgModelSubBadgeEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.custom.LabelOrgTypeSubBadgeEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.custom.OrganizationNameSubBadgeEditPart;
import com.tibco.xpd.om.modeler.subdiagram.part.OrganizationModelSubDiagramEditor;
import com.tibco.xpd.quickfixtooltip.api.QuickFixToolTipProviderFigure;
import com.tibco.xpd.quickfixtooltip.api.QuickFixToolTipProviderFigure.IQuickFixToolTipContentProvider;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.XpdResourcesUIConstants;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.util.XpdConsts;

/**
 * 
 * A decorator provider for the Organization Model and Organization diagram
 * editors.
 * 
 * Provides for editparts that have a DecorationEditPolicy installed.
 * 
 * @author rgreen
 * 
 */
public class OMValidationDecoratorProvider extends AbstractProvider implements
        IDecoratorProvider {

    private static final String KEY = "validationStatus"; //$NON-NLS-1$

    private static final String MARKER_TYPE = XpdConsts.VALIDATION_MARKER_TYPE;

    private static MarkerObserver fileObserver = null;

    private static final String IMG_ERROR = "icons/obj16/errorLarge.png"; //$NON-NLS-1$

    private static final String IMG_WARN = "icons/obj16/warningLarge.gif"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorProvider
     * #createDecorators
     * (org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorTarget)
     */
    public void createDecorators(IDecoratorTarget decoratorTarget) {
        // The call to getModelIds gathers a list of IDs of semantic
        // model elements affected by the validation. It is a list
        // because it may be possible to have more than one semantic
        // element that is affected. This was inherited from BOM code.
        // There is currently no need for this to be a list as only one
        // ID should be returned. However, we will keep it a list for
        // now in case we have a requirement in the future.
        //
        // Also, use the call to getModelIds to filter out child
        // editparts that don't require a decorator. For example, we
        // don't need decorators for compartments and most labels.
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
                public void run() {
                    result.addAll(doGetModelIds(target, view));
                }
            });
        } catch (Exception e) {
            OrganizationModelDiagramEditorPlugin.getInstance()
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
     * This runs within readonly transaction.
     */
    private List<String> doGetModelIds(final IDecoratorTarget target,
            final View view) {
        List<String> res = new ArrayList<String>();
        if (view != null) {
            // handle special cases
            EditPart part = (EditPart) target.getAdapter(EditPart.class);

            if (shouldHaveDecorator(part)) {
                // handle generic case
                if (view.isSetElement() && view.getElement() != null) {
                    res.add(EcoreUtil.getURI(view.getElement()).fragment());
                } else if (view.getElement() != null) {
                    if (part instanceof OrganizationNameSubBadgeEditPart
                            || part instanceof OrgModelBadgeEditPart) {
                        res.add(EcoreUtil.getURI(view.getElement()).fragment());
                    }
                }
            }
        }

        return res;
    }

    /**
     * Check if the given EditPart should have a decorator.
     * 
     * @param part
     * @return
     */
    private boolean shouldHaveDecorator(EditPart part) {
        return !(part instanceof OrganizationSubEditPart
                || part instanceof OrgModelEditPart
                || part instanceof LabelOrgModelSubBadgeEditPart
                || part instanceof LabelOrgTypeSubBadgeEditPart
                || part instanceof LabelAuthorBadgeEditPart
                || part instanceof LabelDateCreatedBadgeEditPart
                || part instanceof LabelDateModifiedBadgeEditPart || part instanceof LabelVersionBadgeEditPart);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gmf.runtime.common.core.service.IProvider#provides(org.eclipse
     * .gmf.runtime.common.core.service.IOperation)
     */
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
                        IEditorPart editorPart =
                                ((DiagramEditDomain) ed).getEditorPart();

                        if (editorPart instanceof OrganizationModelDiagramEditor
                                || editorPart instanceof OrganizationModelSubDiagramEditor) {

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

        private List<String> modelIds;

        private final ToolTipContentProvider contentProvider;

        /**
         * @param decoratorTarget
         * @param ids
         */
        public StatusDecorator(IDecoratorTarget decoratorTarget,
                List<String> ids) {
            super(decoratorTarget);
            modelIds = ids;
            contentProvider = new ToolTipContentProvider();
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecorator#
         * activate()
         */
        public void activate() {
            View view = (View) getDecoratorTarget().getAdapter(View.class);
            if (view == null)
                return;
            Diagram diagramView = view.getDiagram();
            if (diagramView == null)
                return;
            IFile file = WorkspaceSynchronizer.getFile(diagramView.eResource());
            if (file != null) {
                if (fileObserver == null) {
                    fileObserver = new MarkerObserver(diagramView);
                }

                fileObserver.registerDecorator(this);
            }
            refresh();
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.gmf.runtime.diagram.ui.services.decorator.AbstractDecorator
         * #deactivate()
         */
        @Override
        public void deactivate() {
            if (fileObserver != null) {
                fileObserver.unregisterDecorator(this);
                if (!fileObserver.isRegistered()) {
                    fileObserver = null;
                }
            }

            super.deactivate();
        }

        /**
         * @return List
         */
        public List<String> getModelIds() {
            return modelIds;
        }

        /**
         * 
         * Returns the IResource for the supplied view
         * 
         * @param view
         * @return IResource
         */
        private static IResource getResource(View view) {
            Resource model = view.eResource();
            if (model != null) {
                return WorkspaceSynchronizer.getFile(model);
            }
            return null;
        }

        /**
         * 
         * Returns the image icon representing the supplied severity
         * 
         * @param int severity
         * @return Image
         */
        private Image getImage(int severity) {
            switch (severity) {
            case IMarker.SEVERITY_ERROR:
                return getImage(IMG_ERROR);
            case IMarker.SEVERITY_WARNING:
                return getImage(IMG_WARN);
            default:
                return PlatformUI.getWorkbench().getSharedImages()
                        .getImage(ISharedImages.IMG_OBJS_INFO_TSK);
            }
        }

        /**
         * Get the image from the specified path.
         * 
         * @param imgError
         * @return
         */
        private Image getImage(String imgPath) {
            ImageRegistry registry = Activator.getDefault().getImageRegistry();
            Image image = registry.get(imgPath);
            if (image == null) {
                ImageDescriptor desc =
                        Activator
                                .imageDescriptorFromPlugin(Activator.PLUGIN_ID,
                                        imgPath);
                if (desc != null) {
                    image = desc.createImage();
                    registry.put(imgPath, image);
                }
            }
            return image;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecorator#
         * refresh()
         */
        public void refresh() {
            removeDecoration();

            View view = (View) getDecoratorTarget().getAdapter(View.class);
            EditPart editPart =
                    (EditPart) getDecoratorTarget().getAdapter(EditPart.class);
            if (view == null || view.eResource() == null) {
                return;
            }

            IResource resource = getResource(view);
            // make sure we have a resource and that it exists in an open
            // project
            if (resource == null || !resource.exists()) {
                return;
            }

            // collect all the validation markers of the current resource
            IMarker[] markers = null;
            try {
                markers =
                        resource.findMarkers(MARKER_TYPE,
                                true,
                                IResource.DEPTH_INFINITE);
            } catch (CoreException e) {
                OrganizationModelDiagramEditorPlugin.getInstance()
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

            // Views of type "Note" will not have elements assigned to them as
            // semantic parts.
            if (element != null) {
                String elementURI = EcoreUtil.getURI(element).fragment();
                int severity = -1;
                List<IMarker> relevantMarkers = new ArrayList<IMarker>();

                // Loop through all the markers and match them to this semantic
                // element. There may be more than one marker per element with
                // different levels of severity.
                for (int i = 0; i < markers.length; i++) {
                    IMarker marker = markers[i];
                    String attribute =
                            marker.getAttribute(IMarker.LOCATION, "-"); //$NON-NLS-1$
                    if (attribute.equals(elementURI)) {
                        relevantMarkers.add(marker);

                        // Work out worst severity
                        int nextSeverity =
                                marker.getAttribute(IMarker.SEVERITY,
                                        IMarker.SEVERITY_INFO);
                        severity =
                                (nextSeverity > severity) ? nextSeverity
                                        : severity;
                    }
                }

                // no marker found
                if (severity >= 0) {
                    // add decoration
                    createDecoration(view, editPart, severity, relevantMarkers);
                }
            }
        }

        /**
         * @param View
         *            view
         * @param EditPart
         *            editPart
         * @param int severity
         * @param markers
         */
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
                    if (editPart instanceof GraphicalEditPart
                            && !(editPart instanceof ITextAwareEditPart)) {
                        margin =
                                MapModeUtil
                                        .getMapMode(((GraphicalEditPart) editPart)
                                                .getFigure()).DPtoLP(margin);
                    }

                    ImageRegistry reg =
                            XpdResourcesUIActivator.getDefault()
                                    .getImageRegistry();
                    if (editPart instanceof PositionEditPart
                            || editPart instanceof PositionSubEditPart) {
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
                    } else if (editPart instanceof OrganizationNameSubBadgeEditPart) {
                        setDecoration(getDecoratorTarget()
                                .addShapeDecoration(img,
                                        IDecoratorTarget.Direction.WEST,
                                        1,
                                        true));
                    } else if (editPart instanceof OrgModelBadgeEditPart) {
                        setDecoration(getDecoratorTarget()
                                .addShapeDecoration(img, direction, -1, true));
                    } else {
                        setDecoration(getDecoratorTarget()
                                .addShapeDecoration(img,
                                        direction,
                                        margin,
                                        true));
                    }
                }
                contentProvider.setMarkers(markers);
                getDecoration().setToolTip(new QuickFixToolTipProviderFigure(
                        view, contentProvider));
            }
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

        private MarkerObserver(Diagram diagramView) {
            this.diagramView = diagramView;
        }

        /**
         * @param StatusDecorator
         *            decorator
         */
        private void registerDecorator(StatusDecorator decorator) {
            if (decorator == null) {
                return;
            }

            if (mapOfIdsToDecorators == null) {
                mapOfIdsToDecorators =
                        new HashMap<String, List<StatusDecorator>>();
            }

            Collection<String> modelUris = decorator.getModelIds();

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

        /**
         * @param StatusDecorator
         *            decorator
         */
        private void unregisterDecorator(StatusDecorator decorator) {
            /* Return if invalid decorator */
            if (decorator == null) {
                return;
            }

            /* Return if the decorator has invalid view id */

            Collection<String> modelIds = decorator.getModelIds();
            for (String decoratorViewId : modelIds) {

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

        public void handleFileRenamed(IFile oldFile, IFile file) {
        }

        public void handleFileMoved(IFile oldFile, IFile file) {
        }

        public void handleFileDeleted(IFile file) { /* Empty Code */
        }

        public void handleFileChanged(IFile file) { /* Empty Code */
        }

        public void handleMarkerAdded(IMarker marker) {
            if (marker.getAttribute(IMarker.LOCATION, null) != null) {
                handleMarkerChanged(marker);
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @seeorg.eclipse.gmf.runtime.common.ui.resources.IFileObserver#
         * handleMarkerDeleted(org.eclipse.core.resources.IMarker,
         * java.util.Map)
         */
        @SuppressWarnings("unchecked")
        public void handleMarkerDeleted(IMarker marker, Map attributes) {
            if (mapOfIdsToDecorators == null) {
                return;
            }
            // Extract the element guid from the marker and retrieve
            // corresponding view
            String elementId = (String) attributes.get(IMarker.LOCATION);
            List list =
                    elementId != null ? (List) mapOfIdsToDecorators
                            .get(elementId) : null;
            if (list != null && !list.isEmpty()) {
                refreshDecorators(list);
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @seeorg.eclipse.gmf.runtime.common.ui.resources.IFileObserver#
         * handleMarkerChanged(org.eclipse.core.resources.IMarker)
         */
        public void handleMarkerChanged(IMarker marker) {
            if (mapOfIdsToDecorators == null
                    || !MARKER_TYPE.equals(getType(marker))) {
                return;
            }
            // Extract the element ID list from the marker and retrieve
            // corresponding view
            String elementId = marker.getAttribute(IMarker.LOCATION, ""); //$NON-NLS-1$

            List<StatusDecorator> list =
                    elementId != null ? mapOfIdsToDecorators.get(elementId)
                            : null;

            if (list != null && !list.isEmpty()) {
                refreshDecorators(list);
            }
        }

        /**
         * @param List
         *            <StatusDecorator> decorators
         */
        private void refreshDecorators(List<StatusDecorator> decorators) {
            final List<StatusDecorator> decoratorsToRefresh = decorators;
            PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
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
                        OrganizationModelDiagramEditorPlugin.getInstance()
                                .logError("Decorator refresh failure", e); //$NON-NLS-1$
                    }
                }
            });
        }

        /**
         * @return boolean
         */
        private boolean isRegistered() {
            return isRegistered;
        }

        /**
         * Get marker type
         * 
         * @param marker
         * @return String
         */
        private String getType(IMarker marker) {
            try {
                return marker.getType();
            } catch (CoreException e) {
                OrganizationModelDiagramEditorPlugin.getInstance()
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

        public Collection<IMarkerResolution> getMarkerResolutions(IMarker marker) {
            IMarkerResolution[] resolutions =
                    markerHelpRegistry.getResolutions(marker);
            if (resolutions != null) {
                return Arrays.asList(resolutions);
            }
            return Collections.emptyList();
        }

        public Collection<IMarker> getMarkers(Object markerHost) {
            return markers;
        }
    }

}
