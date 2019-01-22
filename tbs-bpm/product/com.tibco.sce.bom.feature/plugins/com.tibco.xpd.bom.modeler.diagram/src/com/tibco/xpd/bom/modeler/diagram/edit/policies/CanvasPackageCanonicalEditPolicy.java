/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.policies;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.transaction.Transaction;
import org.eclipse.emf.workspace.AbstractEMFOperation;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.common.core.util.StringStatics;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.commands.DeferredLayoutCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalConnectionEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.Ratio;
import org.eclipse.gmf.runtime.notation.Size;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Relationship;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationClassConnectorEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationClassDanglingNodeEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationClassEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationEndEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.BadgeEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.CanvasPackageEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.ClassEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.EnumerationEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.GeneralizationEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.PackageEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.PrimitiveTypeEditPart;
import com.tibco.xpd.bom.modeler.diagram.part.BOMDiagramEditorPlugin;
import com.tibco.xpd.bom.modeler.diagram.part.UMLDiagramUpdater;
import com.tibco.xpd.bom.modeler.diagram.part.UMLLinkDescriptor;
import com.tibco.xpd.bom.modeler.diagram.part.UMLNodeDescriptor;
import com.tibco.xpd.bom.modeler.diagram.part.UMLVisualIDRegistry;
import com.tibco.xpd.bom.modeler.diagram.part.custom.utils.DiagramUtils;
import com.tibco.xpd.bom.resources.ui.util.BomUIUtil;

/**
 * @generated
 */
public class CanvasPackageCanonicalEditPolicy extends
        CanonicalConnectionEditPolicy {

    /**
     * @generated
     */
    Set myFeaturesToSynchronize;

    /**
     * @generated
     */
    protected List getSemanticChildrenList() {
        View viewObject = (View) getHost().getModel();
        List result = new LinkedList();
        // Dummy top-level node (Badge) with semantic element of model
        // Make sure it is first in the list so an ArrangeAll will put this
        // view
        // at origin (0,0) first.
        if (viewObject instanceof Diagram) {
            result.add(viewObject.getElement());
        }

        for (Iterator it =
                UMLDiagramUpdater.getPackage_79SemanticChildren(viewObject)
                        .iterator(); it.hasNext();) {
            result.add(((UMLNodeDescriptor) it.next()).getModelElement());
        }

        return result;
    }

    /**
     * @generated
     */
    protected boolean shouldDeleteView(View view) {
        return true;
    }

    @Override
    public void activate() {
        super.activate();
        if (BomUIUtil.isUserDiagram(getDiagram())) {
            Object model = getHost().getModel();
            if (model instanceof View) {
                /*
                 * For user diagrams register a listener to listen to changes to
                 * the "Model". This will ensure we are in synch with
                 * relationship changes in the model
                 */
                EObject element = ((View) model).getElement();
                if (element != null) {
                    addListenerFilter("userDiagram", this, element); //$NON-NLS-1$
                }
            }
        }
    }

    @Override
    public EObject getSemanticHost() {
        if (BomUIUtil.isUserDiagram(getDiagram())) {
            /*
             * For user diagrams its semantic host will be the notation diagram
             * rather than the semantic "Model" element.
             */
            return (EObject) getHost().getModel();
        }
        return super.getSemanticHost();
    }

    /**
     * @generated
     */
    protected boolean isOrphaned(Collection semanticChildren, final View view) {
        int visualID = UMLVisualIDRegistry.getVisualID(view);
        switch (visualID) {
        case PackageEditPart.VISUAL_ID:
        case ClassEditPart.VISUAL_ID:
        case PrimitiveTypeEditPart.VISUAL_ID:
        case EnumerationEditPart.VISUAL_ID:
            // case AssociationClassEditPart.VISUAL_ID:
            // case AssociationClassDanglingNodeEditPart.VISUAL_ID:
        case BadgeEditPart.VISUAL_ID:
            if (!semanticChildren.contains(view.getElement())) {
                return true;
            }
            EObject domainModelElement = view.getElement();
            if (visualID != UMLVisualIDRegistry
                    .getNodeVisualID((View) getHost().getModel(),
                            domainModelElement)) {
                List createdViews =
                        createViews(Collections
                                .singletonList(domainModelElement));
                assert createdViews.size() == 1;
                final View createdView =
                        (View) ((IAdaptable) createdViews.get(0))
                                .getAdapter(View.class);
                if (createdView != null) {
                    try {
                        new AbstractEMFOperation(
                                host().getEditingDomain(),
                                StringStatics.BLANK,
                                Collections
                                        .singletonMap(Transaction.OPTION_UNPROTECTED,
                                                Boolean.TRUE)) {
                            protected IStatus doExecute(
                                    IProgressMonitor monitor, IAdaptable info)
                                    throws ExecutionException {
                                populateViewProperties(view, createdView);
                                return Status.OK_STATUS;
                            }
                        }.execute(new NullProgressMonitor(), null);
                    } catch (ExecutionException e) {
                        BOMDiagramEditorPlugin
                                .getInstance()
                                .logError("Error while copyign view information to newly created view", e); //$NON-NLS-1$
                    }
                }
                deleteViews(Collections.singletonList(view).iterator());
            }
        }
        return false;
    }

    /**
     * @generated
     */
    private void populateViewProperties(View oldView, View newView) {
        if (oldView instanceof Node && newView instanceof Node) {
            Node oldNode = (Node) oldView;
            Node newNode = (Node) newView;
            if (oldNode.getLayoutConstraint() instanceof Location
                    && newNode.getLayoutConstraint() instanceof Location) {
                ((Location) newNode.getLayoutConstraint())
                        .setX(((Location) oldNode.getLayoutConstraint()).getX());
                ((Location) newNode.getLayoutConstraint())
                        .setY(((Location) oldNode.getLayoutConstraint()).getY());
            }
            if (oldNode.getLayoutConstraint() instanceof Size
                    && newNode.getLayoutConstraint() instanceof Size) {
                ((Size) newNode.getLayoutConstraint()).setWidth(((Size) oldNode
                        .getLayoutConstraint()).getWidth());
                ((Size) newNode.getLayoutConstraint())
                        .setHeight(((Size) oldNode.getLayoutConstraint())
                                .getHeight());
            }
            if (oldNode.getLayoutConstraint() instanceof Ratio
                    && newNode.getLayoutConstraint() instanceof Ratio) {
                ((Ratio) newNode.getLayoutConstraint())
                        .setValue(((Ratio) oldNode.getLayoutConstraint())
                                .getValue());
            }
            newNode.persist();
        }
    }

    /**
     * @generated
     */
    protected String getDefaultFactoryHint() {
        return null;
    }

    @Override
    protected Command getDropCommand(DropObjectsRequest request) {
        // Let the semantic edit policy decide what can be dropped
        return null;
    }

    /**
     * @generated
     */
    protected Set getFeaturesToSynchronize() {
        if (myFeaturesToSynchronize == null) {
            myFeaturesToSynchronize = new HashSet();
            myFeaturesToSynchronize.add(UMLPackage.eINSTANCE
                    .getPackage_PackagedElement());
        }
        return myFeaturesToSynchronize;
    }

    /**
     * @generated
     */
    protected List getSemanticConnectionsList() {
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated
     */
    protected EObject getSourceElement(EObject relationship) {
        return null;
    }

    /**
     * @generated
     */
    protected EObject getTargetElement(EObject relationship) {
        return null;
    }

    /**
     * @generated
     */
    protected boolean shouldIncludeConnection(Edge connector,
            Collection children) {
        return false;
    }

    /**
     * @generated NOT
     */
    protected void refreshSemantic() {
        List createdViews = new LinkedList();
        createdViews.addAll(refreshSemanticChildren());
        List createdConnectionViews = new LinkedList();
        createdConnectionViews.addAll(refreshSemanticConnections());
        createdConnectionViews.addAll(refreshConnections());

        if (createdViews.size() > 1) {
            // perform a layout of the container
            DeferredLayoutCommand layoutCmd =
                    new DeferredLayoutCommand(host().getEditingDomain(),
                            createdViews, host());
            executeCommand(new ICommandProxy(layoutCmd));
        }

        createdViews.addAll(createdConnectionViews);
        makeViewsImmutable(createdViews);

        // Added extra to generated code
        // getDiagram().persistChildren();
    }

    /**
     * @generated NOT
     * 
     *            Changed from private to protected
     */
    protected Diagram getDiagram() {
        return ((View) getHost().getModel()).getDiagram();
    }

    /**
     * @generated NOT
     * 
     *            Since implementing AssociationClass this method uses a
     *            slightly different way to gather notation elements. Instead of
     *            using a Map to map domain and notation objects we use a
     *            locally defined Class Domain2Notation.
     */
    protected synchronized Collection refreshConnections() {
        NotationDomainLookUp domNotMap = new NotationDomainLookUp();

        // linkdescriptors should come back with a UML Link descriptor for
        // AssocClass connection
        Collection linkDescriptors =
                collectAllLinksIntoNotationMap(getDiagram(), domNotMap);
        Collection existingLinks = new LinkedList(getDiagram().getEdges());
        for (Iterator linksIterator = existingLinks.iterator(); linksIterator
                .hasNext();) {
            Edge nextDiagramLink = (Edge) linksIterator.next();
            int diagramLinkVisualID =
                    UMLVisualIDRegistry.getVisualID(nextDiagramLink);
            if (diagramLinkVisualID == -1) {
                if (nextDiagramLink.getSource() != null
                        && nextDiagramLink.getTarget() != null) {
                    linksIterator.remove();
                }
                continue;
            }

            if (isNotationOnlyEdge(nextDiagramLink)) {
                linksIterator.remove();
                continue;
            }

            EObject diagramLinkObject = nextDiagramLink.getElement();
            EObject diagramLinkSrc = nextDiagramLink.getSource().getElement();
            EObject diagramLinkDst = nextDiagramLink.getTarget().getElement();
            for (Iterator LinkDescriptorsIterator = linkDescriptors.iterator(); LinkDescriptorsIterator
                    .hasNext();) {
                UMLLinkDescriptor nextLinkDescriptor =
                        (UMLLinkDescriptor) LinkDescriptorsIterator.next();
                if (diagramLinkObject == nextLinkDescriptor.getModelElement()
                        && diagramLinkSrc == nextLinkDescriptor.getSource()
                        && diagramLinkDst == nextLinkDescriptor
                                .getDestination()
                        && diagramLinkVisualID == nextLinkDescriptor
                                .getVisualID()) {
                    linksIterator.remove();
                    LinkDescriptorsIterator.remove();
                }
            }
        }
        deleteViews(existingLinks.iterator());
        return createConnections(linkDescriptors, domNotMap);
    }

    /**
     * @generated
     */
    private Collection collectAllLinks(View view, Map domain2NotationMap) {
        if (!CanvasPackageEditPart.MODEL_ID.equals(UMLVisualIDRegistry
                .getModelID(view))) {
            return Collections.EMPTY_LIST;
        }
        Collection result = new LinkedList();
        switch (UMLVisualIDRegistry.getVisualID(view)) {
        case CanvasPackageEditPart.VISUAL_ID: {
            if (!domain2NotationMap.containsKey(view.getElement())) {
                result.addAll(UMLDiagramUpdater
                        .getPackage_79ContainedLinks(view));
            }
            if (!domain2NotationMap.containsKey(view.getElement())
                    || view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
                domain2NotationMap.put(view.getElement(), view);
            }
            break;
        }
        case PackageEditPart.VISUAL_ID: {
            if (!domain2NotationMap.containsKey(view.getElement())) {
                result.addAll(UMLDiagramUpdater
                        .getPackage_1001ContainedLinks(view));
            }
            if (!domain2NotationMap.containsKey(view.getElement())
                    || view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
                domain2NotationMap.put(view.getElement(), view);
            }
            break;
        }
        case ClassEditPart.VISUAL_ID: {
            if (!domain2NotationMap.containsKey(view.getElement())) {
                result.addAll(UMLDiagramUpdater
                        .getClass_1002ContainedLinks(view));
            }
            if (!domain2NotationMap.containsKey(view.getElement())
                    || view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
                domain2NotationMap.put(view.getElement(), view);
            }
            break;
        }
        case PrimitiveTypeEditPart.VISUAL_ID: {
            if (!domain2NotationMap.containsKey(view.getElement())) {
                result.addAll(UMLDiagramUpdater
                        .getPrimitiveType_1003ContainedLinks(view));
            }
            if (!domain2NotationMap.containsKey(view.getElement())
                    || view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
                domain2NotationMap.put(view.getElement(), view);
            }
            break;
        }
        case EnumerationEditPart.VISUAL_ID: {
            if (!domain2NotationMap.containsKey(view.getElement())) {
                result.addAll(UMLDiagramUpdater
                        .getEnumeration_1004ContainedLinks(view));
            }
            if (!domain2NotationMap.containsKey(view.getElement())
                    || view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
                domain2NotationMap.put(view.getElement(), view);
            }
            break;
        }
        case AssociationClassEditPart.VISUAL_ID: {
            if (!domain2NotationMap.containsKey(view.getElement())) {
                result.addAll(UMLDiagramUpdater
                        .getAssociationClass_1005ContainedLinks(view));
            }
            if (!domain2NotationMap.containsKey(view.getElement())
                    || view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
                domain2NotationMap.put(view.getElement(), view);
            }
            break;
        }
        case AssociationClassDanglingNodeEditPart.VISUAL_ID: {
            if (!domain2NotationMap.containsKey(view.getElement())) {
                result.addAll(UMLDiagramUpdater
                        .getAssociationClass_1006ContainedLinks(view));
            }
            if (!domain2NotationMap.containsKey(view.getElement())
                    || view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
                domain2NotationMap.put(view.getElement(), view);
            }
            break;
        }
        case BadgeEditPart.VISUAL_ID: {
            if (!domain2NotationMap.containsKey(view.getElement())) {
                result.addAll(UMLDiagramUpdater
                        .getPackage_1007ContainedLinks(view));
            }
            if (!domain2NotationMap.containsKey(view.getElement())
                    || view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
                domain2NotationMap.put(view.getElement(), view);
            }
            break;
        }
        case GeneralizationEditPart.VISUAL_ID: {
            if (!domain2NotationMap.containsKey(view.getElement())) {
                result.addAll(UMLDiagramUpdater
                        .getGeneralization_3001ContainedLinks(view));
            }
            if (!domain2NotationMap.containsKey(view.getElement())
                    || view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
                domain2NotationMap.put(view.getElement(), view);
            }
            break;
        }
        case AssociationEditPart.VISUAL_ID: {
            if (!domain2NotationMap.containsKey(view.getElement())) {
                result.addAll(UMLDiagramUpdater
                        .getAssociation_3002ContainedLinks(view));
            }
            if (!domain2NotationMap.containsKey(view.getElement())
                    || view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
                domain2NotationMap.put(view.getElement(), view);
            }
            break;
        }
        case AssociationEndEditPart.VISUAL_ID: {
            if (!domain2NotationMap.containsKey(view.getElement())) {
                result.addAll(UMLDiagramUpdater
                        .getProperty_3003ContainedLinks(view));
            }
            if (!domain2NotationMap.containsKey(view.getElement())
                    || view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
                domain2NotationMap.put(view.getElement(), view);
            }
            break;
        }
        case AssociationClassConnectorEditPart.VISUAL_ID: {
            if (!domain2NotationMap.containsKey(view.getElement())) {
                result.addAll(UMLDiagramUpdater
                        .getAssociationClass_3004ContainedLinks(view));
            }
            if (!domain2NotationMap.containsKey(view.getElement())
                    || view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
                domain2NotationMap.put(view.getElement(), view);
            }
            break;
        }
        }
        for (Iterator children = view.getChildren().iterator(); children
                .hasNext();) {
            result.addAll(collectAllLinks((View) children.next(),
                    domain2NotationMap));
        }
        for (Iterator edges = view.getSourceEdges().iterator(); edges.hasNext();) {
            result.addAll(collectAllLinks((View) edges.next(),
                    domain2NotationMap));
        }
        return result;
    }

    /**
     * @generated
     */
    private Collection createConnections(Collection linkDescriptors,
            Map domain2NotationMap) {
        List adapters = new LinkedList();
        for (Iterator linkDescriptorsIterator = linkDescriptors.iterator(); linkDescriptorsIterator
                .hasNext();) {
            final UMLLinkDescriptor nextLinkDescriptor =
                    (UMLLinkDescriptor) linkDescriptorsIterator.next();
            EditPart sourceEditPart =
                    getEditPart(nextLinkDescriptor.getSource(),
                            domain2NotationMap);
            EditPart targetEditPart =
                    getEditPart(nextLinkDescriptor.getDestination(),
                            domain2NotationMap);
            if (sourceEditPart == null || targetEditPart == null) {
                continue;
            }
            CreateConnectionViewRequest.ConnectionViewDescriptor descriptor =
                    new CreateConnectionViewRequest.ConnectionViewDescriptor(
                            nextLinkDescriptor.getSemanticAdapter(), null,
                            ViewUtil.APPEND, false,
                            ((IGraphicalEditPart) getHost())
                                    .getDiagramPreferencesHint());
            CreateConnectionViewRequest ccr =
                    new CreateConnectionViewRequest(descriptor);
            ccr.setType(RequestConstants.REQ_CONNECTION_START);
            ccr.setSourceEditPart(sourceEditPart);
            sourceEditPart.getCommand(ccr);
            ccr.setTargetEditPart(targetEditPart);
            ccr.setType(RequestConstants.REQ_CONNECTION_END);
            Command cmd = targetEditPart.getCommand(ccr);
            if (cmd != null && cmd.canExecute()) {
                executeCommand(cmd);
                IAdaptable viewAdapter = (IAdaptable) ccr.getNewObject();
                if (viewAdapter != null) {
                    adapters.add(viewAdapter);
                }
            }
        }
        return adapters;
    }

    /**
     * @generated
     */
    private EditPart getEditPart(EObject domainModelElement,
            Map domain2NotationMap) {
        View view = (View) domain2NotationMap.get(domainModelElement);
        if (view != null) {
            return (EditPart) getHost().getViewer().getEditPartRegistry()
                    .get(view);
        }
        return null;
    }

    /**
     * This is really an override of the collectAllLinks method that takes a
     * Domain2Notation parameter rather than a Map. It had to be given a
     * different name so that the generator would not keep regenerating the
     * original method. The generator seems to be unpredictable when you try to
     * override a generated method.
     * 
     * Introduced as part of AssociationClass implementation.
     * 
     */
    private Collection collectAllLinksIntoNotationMap(View view,
            NotationDomainLookUp dnMap) {
        if (!CanvasPackageEditPart.MODEL_ID.equals(UMLVisualIDRegistry
                .getModelID(view))) {
            return Collections.EMPTY_LIST;
        }
        Collection result = new LinkedList();
        switch (UMLVisualIDRegistry.getVisualID(view)) {
        case CanvasPackageEditPart.VISUAL_ID: {
            if (!dnMap.containsKey(view.getElement())) {
                result.addAll(UMLDiagramUpdater
                        .getPackage_79ContainedLinks(view));
            }
            if (!dnMap.containsKey(view.getElement())
                    || view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
                dnMap.put(view.getElement(), view);
            }
            break;
        }
        case PackageEditPart.VISUAL_ID: {
            if (!dnMap.containsKey(view.getElement())) {
                result.addAll(UMLDiagramUpdater
                        .getPackage_1001ContainedLinks(view));
            }
            if (!dnMap.containsKey(view.getElement())
                    || view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
                dnMap.put(view.getElement(), view);
            }
            break;
        }
        case ClassEditPart.VISUAL_ID: {
            if (!dnMap.containsKey(view.getElement())) {
                result.addAll(UMLDiagramUpdater
                        .getClass_1002ContainedLinks(view));
            }
            if (!dnMap.containsKey(view.getElement())
                    || view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
                dnMap.put(view.getElement(), view);
            }
            break;
        }
        case PrimitiveTypeEditPart.VISUAL_ID: {
            if (!dnMap.containsKey(view.getElement())) {
                result.addAll(UMLDiagramUpdater
                        .getPrimitiveType_1003ContainedLinks(view));
            }
            if (!dnMap.containsKey(view.getElement())
                    || view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
                dnMap.put(view.getElement(), view);
            }
            break;
        }
        case EnumerationEditPart.VISUAL_ID: {
            if (!dnMap.containsKey(view.getElement())) {
                result.addAll(UMLDiagramUpdater
                        .getEnumeration_1004ContainedLinks(view));
            }
            if (!dnMap.containsKey(view.getElement())
                    || view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
                dnMap.put(view.getElement(), view);
            }
            break;
        }
        case AssociationClassEditPart.VISUAL_ID: {
            if (!dnMap.containsKey(view.getElement())) {
                result.addAll(UMLDiagramUpdater
                        .getAssociationClass_1005ContainedLinks(view));
            }
            if (!dnMap.containsKey(view.getElement())
                    || view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
                dnMap.put(view.getElement(), view);
            }
            break;
        }
        case AssociationClassDanglingNodeEditPart.VISUAL_ID: {
            if (!dnMap.containsKey(view.getElement())) {
                result.addAll(UMLDiagramUpdater
                        .getAssociationClass_1006ContainedLinks(view));
            }
            if (!dnMap.containsKey(view.getElement())
                    || view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
                dnMap.put(view.getElement(), view);
            }
            break;
        }
        case GeneralizationEditPart.VISUAL_ID: {
            if (!dnMap.containsKey(view.getElement())) {
                result.addAll(UMLDiagramUpdater
                        .getGeneralization_3001ContainedLinks(view));
            }
            if (!dnMap.containsKey(view.getElement())
                    || view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
                dnMap.put(view.getElement(), view);
            }
            break;
        }
        case AssociationEditPart.VISUAL_ID: {
            if (!dnMap.containsKey(view.getElement())) {
                result.addAll(UMLDiagramUpdater
                        .getAssociation_3002ContainedLinks(view));
            }
            if (!dnMap.containsKey(view.getElement())
                    || view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
                dnMap.put(view.getElement(), view);
            }
            break;
        }
        case AssociationEndEditPart.VISUAL_ID: {
            if (!dnMap.containsKey(view.getElement())) {
                result.addAll(UMLDiagramUpdater
                        .getProperty_3003ContainedLinks(view));
            }
            if (!dnMap.containsKey(view.getElement())
                    || view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
                dnMap.put(view.getElement(), view);
            }
            break;
        }
        case AssociationClassConnectorEditPart.VISUAL_ID: {
            if (!dnMap.containsKey(view.getElement())) {
                result.addAll(UMLDiagramUpdater
                        .getAssociationClass_3004ContainedLinks(view));
            }
            if (!dnMap.containsKey(view.getElement())
                    || view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
                dnMap.put(view.getElement(), view);
            }
            break;
        }
        }

        for (Iterator children = view.getChildren().iterator(); children
                .hasNext();) {
            result
                    .addAll(collectAllLinksIntoNotationMap((View) children
                            .next(), dnMap));
        }

        for (Iterator edges = view.getSourceEdges().iterator(); edges.hasNext();) {
            result.addAll(collectAllLinksIntoNotationMap((View) edges.next(),
                    dnMap));
        }

        return result;
    }

    /**
     * @generated NOT
     */
    private Collection createConnections(Collection linkDescriptors,
            NotationDomainLookUp domNotation) {
        List adapters = new LinkedList();
        for (Iterator linkDescriptorsIterator = linkDescriptors.iterator(); linkDescriptorsIterator
                .hasNext();) {
            final UMLLinkDescriptor nextLinkDescriptor =
                    (UMLLinkDescriptor) linkDescriptorsIterator.next();
            // EditPart sourceEditPart = getEditPart(nextLinkDescriptor
            // .getSource(), domain2NotationMap);
            // EditPart targetEditPart = getEditPart(nextLinkDescriptor
            // .getDestination(), domain2NotationMap);

            EditPart sourceEditPart =
                    getSourceEditPart(nextLinkDescriptor, domNotation);
            EditPart targetEditPart =
                    getTargetEditPart(nextLinkDescriptor, domNotation);

            if (sourceEditPart == null || targetEditPart == null) {
                continue;
            }
            CreateConnectionViewRequest.ConnectionViewDescriptor descriptor =
                    new CreateConnectionViewRequest.ConnectionViewDescriptor(
                            nextLinkDescriptor.getSemanticAdapter(), null,
                            ViewUtil.APPEND, false,
                            ((IGraphicalEditPart) getHost())
                                    .getDiagramPreferencesHint());
            CreateConnectionViewRequest ccr =
                    new CreateConnectionViewRequest(descriptor);
            ccr.setType(RequestConstants.REQ_CONNECTION_START);
            ccr.setSourceEditPart(sourceEditPart);
            sourceEditPart.getCommand(ccr);
            ccr.setTargetEditPart(targetEditPart);
            ccr.setType(RequestConstants.REQ_CONNECTION_END);
            Command cmd = targetEditPart.getCommand(ccr);
            if (cmd != null && cmd.canExecute()) {
                executeCommand(cmd);
                IAdaptable viewAdapter = (IAdaptable) ccr.getNewObject();
                if (viewAdapter != null) {
                    adapters.add(viewAdapter);
                }
            }
        }
        return adapters;
    }

    /**
     * @param domainModelElement
     * @param domain2NotationMap
     * @return EditPart
     */
    private EditPart getEditPartFromDomain2Notation(EObject domainModelElement,
            NotationDomainLookUp domain2NotationMap) {
        View view = (View) domain2NotationMap.get(domainModelElement);
        if (view != null) {
            return (EditPart) getHost().getViewer().getEditPartRegistry()
                    .get(view);
        }
        return null;
    }

    /**
     * @param descriptor
     * @param domain2NotationMap
     * @return EditPart
     */
    private EditPart getSourceEditPartGen(UMLLinkDescriptor descriptor,
            NotationDomainLookUp domain2NotationMap) {
        return getEditPartFromDomain2Notation(descriptor.getSource(),
                domain2NotationMap);
    }

    /**
     * @param descriptor
     * @param domain2NotationMap
     * @return EditPart
     */
    private EditPart getSourceEditPart(UMLLinkDescriptor descriptor,
            NotationDomainLookUp domain2NotationMap) {
        if (AssociationClassConnectorEditPart.VISUAL_ID == descriptor
                .getVisualID()) {
            /*
             * If in user diagram and the hint is of an AssocationClass then
             * return null as Association Classes are not supported in the user
             * diagram currently
             */
            if (BomUIUtil.isUserDiagram(getDiagram())) {
                return null;
            }
            return getHintedEditPart(descriptor.getSource(),
                    domain2NotationMap,
                    AssociationClassEditPart.VISUAL_ID);
        }

        if (AssociationEndEditPart.VISUAL_ID == descriptor.getVisualID()) {
            return getHintedEditPart(descriptor.getSource(),
                    domain2NotationMap,
                    AssociationClassDanglingNodeEditPart.VISUAL_ID);
        }

        if (GeneralizationEditPart.VISUAL_ID == descriptor.getVisualID()) {
            // A special case if the source is an AssociationClass. We don't
            // want the link to be drawn from from the DanglingNode. The trouble
            // is we have three editparts corresponding to one semantic element.
            // Asking getSourceGenEditPart may give us the wrong view!
            if ((descriptor.getSource() instanceof AssociationClass)
                    && (descriptor.getDestination() instanceof Class)) {
                AssociationClass ac = (AssociationClass) descriptor.getSource();
                EditPart acEP =
                        DiagramUtils.GetAssociationClassEditPart(ac,
                                AssociationClassEditPart.VISUAL_ID);
                return acEP;
            }

        }

        return getSourceEditPartGen(descriptor, domain2NotationMap);
    }

    /**
     * 
     * Wrapper around the generated getEditPart.
     * 
     * @param descriptor
     * @param domain2NotationMap
     * @return EditPart
     */
    private EditPart getTargetEditPartGen(UMLLinkDescriptor descriptor,
            NotationDomainLookUp domain2NotationMap) {
        return getEditPartFromDomain2Notation(descriptor.getDestination(),
                domain2NotationMap);
    }

    /**
     * @param descriptor
     * @param domain2NotationMap
     * @return EditPart
     */
    private EditPart getTargetEditPart(UMLLinkDescriptor descriptor,
            NotationDomainLookUp domain2NotationMap) {
        if (AssociationClassConnectorEditPart.VISUAL_ID == descriptor
                .getVisualID()) {
            /*
             * If in user diagram and the hint is of an AssocationClass then
             * return null as Association Classes are not supported in the user
             * diagram currently
             */
            if (BomUIUtil.isUserDiagram(getDiagram())) {
                return null;
            }
            return getHintedEditPart(descriptor.getDestination(),
                    domain2NotationMap,
                    AssociationClassDanglingNodeEditPart.VISUAL_ID);
        }
        return getTargetEditPartGen(descriptor, domain2NotationMap);
    }

    /**
     * 
     * Looks in the domain2Notation map for a view corresponding to the EObject
     * and returns its editpart.
     * 
     * @param domainModelElement
     * @param domain2NotationMap
     * @param hintVisualId
     * @return EditPart
     */
    protected final EditPart getHintedEditPart(EObject domainModelElement,
            NotationDomainLookUp domain2NotationMap, int hintVisualId) {

        View view =
                (View) domain2NotationMap.getHinted(domainModelElement,
                        UMLVisualIDRegistry.getType(hintVisualId));
        if (view != null) {
            return (EditPart) getHost().getViewer().getEditPartRegistry()
                    .get(view);
        }
        return null;
    }

    /**
     * @param edge
     * @return
     */
    private boolean isNotationOnlyEdge(Edge edge) {
        return false;
    }

    /**
     * Class introduced for the implementation of AssociationClass.
     */
    private static class NotationDomainLookUp {

        private final HashMap notMap = new HashMap();

        public boolean containsDomainElement(EObject domainElement) {
            return notMap.containsKey(domainElement);
        }

        public boolean containsKey(EObject domainElement) {
            return containsDomainElement(domainElement);
        }

        public void put(EObject domainElement, View view) {
            Object viewOrList = notMap.get(domainElement);
            if (viewOrList instanceof View) {
                notMap.remove(domainElement);
                List<View> list = new LinkedList<View>();
                list.add((View) viewOrList);
                notMap.put(domainElement, list);
                list.add(view);
            } else if (viewOrList instanceof List) {
                ((List) viewOrList).add(view);
            } else {
                notMap.put(domainElement, view);
            }
        }

        public View get(EObject domainEObject) {
            Object viewOrList = notMap.get(domainEObject);
            if (viewOrList instanceof View) {
                return (View) viewOrList;
            }
            if (viewOrList instanceof List) {
                for (Object next : (List) viewOrList) {
                    View nextView = (View) next;
                    if (nextView.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
                        return nextView;
                    }
                }
                return (View) ((List) viewOrList).get(0);
            }
            return null;
        }

        public View getHinted(EObject domainEObject, String hint) {
            if (hint == null) {
                return get(domainEObject);
            }
            Object viewOrList = notMap.get(domainEObject);
            if (viewOrList instanceof View) {
                return (View) viewOrList;
            }
            for (Object next : (List) viewOrList) {
                View nextView = (View) next;
                if (hint.equals(nextView.getType())) {
                    return nextView;
                }
            }

            return (View) ((List) viewOrList).get(0);
        }

    }

    @Override
    protected Command getCreateViewCommand(CreateRequest request) {

        if (request instanceof CreateViewRequest) {
            // This could be a request to complete the second node for
            // an AssociationClass. If it is then we need to set the
            // View's location.

            CreateViewRequest req = (CreateViewRequest) request;
            List viewDescriptors = req.getViewDescriptors();

            if (!viewDescriptors.isEmpty()) {
                ViewDescriptor vdesc = (ViewDescriptor) viewDescriptors.get(0);

                IAdaptable elementAdapter = vdesc.getElementAdapter();
                Object adapter = elementAdapter.getAdapter(EObject.class);

                // Check it is actually for an AssociationClass. The main node
                // NOT the dangling node is created canonically so we use the
                // coordinates of the dangling node (which has already been
                // created) to calculate
                // an offset for the main node.
                if (adapter instanceof AssociationClass) {
                    AssociationClass ac = (AssociationClass) adapter;

                    ECrossReferenceAdapter referenceAdapter =
                            ECrossReferenceAdapter.getCrossReferenceAdapter(ac);

                    if (referenceAdapter != null) {
                        Collection<Setting> references =
                                referenceAdapter.getInverseReferences(ac);

                        for (Setting ref : references) {
                            if (ref.getEObject() instanceof Node) {
                                Node node = (Node) ref.getEObject();

                                if (node
                                        .getType()
                                        .equals(String
                                                .valueOf(AssociationClassDanglingNodeEditPart.VISUAL_ID))) {
                                    LayoutConstraint constraint =
                                            node.getLayoutConstraint();

                                    if (constraint instanceof Bounds) {
                                        int x = ((Bounds) constraint).getX();
                                        int y = ((Bounds) constraint).getY();

                                        Point location =
                                                new Point(x + 20, y - 100);

                                        // Take the editor's zoom level into
                                        // account
                                        EditPart part = getHost();
                                        if (part instanceof GraphicalEditPart) {
                                            IFigure figure =
                                                    ((GraphicalEditPart) part)
                                                            .getFigure();

                                            if (figure != null) {
                                                figure
                                                        .translateToAbsolute(location);
                                            }
                                        }

                                        request.setLocation(location);
                                    }

                                    break;

                                }
                            }
                        }
                    }
                }
            }
        }

        return super.getCreateViewCommand(request);
    }

    @Override
    public boolean canCreate(EObject object) {
        /*
         * If this is a user-diagram then report that it cannot create view for
         * the given object (except Relationship objects as they will be created
         * by the edit policy). One effect of this is that the
         * "Delete From Diagram" option will be enabled for user diagrams
         */
        if (BomUIUtil.isUserDiagram(getDiagram())
                && !(object instanceof Relationship)) {
            return false;
        }
        return super.canCreate(object);
    }
}
