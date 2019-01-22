package com.tibco.xpd.om.modeler.diagram.edit.policies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.actions.ActionIds;
import org.eclipse.gmf.runtime.diagram.ui.commands.DeferredLayoutCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.SetViewMutabilityCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalConnectionEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.requests.ArrangeRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.modeler.diagram.edit.parts.DynamicOrgReferenceEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.DynamicOrgUnitEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrgModelEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrgUnitEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrgUnitRelationshipEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrganizationEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.OrgModelBadgeEditPart;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelDiagramEditorPlugin;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelDiagramUpdater;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelLinkDescriptor;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelNodeDescriptor;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelVisualIDRegistry;

/**
 * @generated
 */
public class OrgModelCanonicalEditPolicy extends CanonicalConnectionEditPolicy {

    /**
     * @generated
     */
    Set myFeaturesToSynchronize;

    /**
     * @generated
     */
    @Override
    protected List getSemanticChildrenList() {
        View viewObject = (View) getHost().getModel();
        List result = new LinkedList();
        for (Iterator it =
                OrganizationModelDiagramUpdater
                        .getOrgModel_79SemanticChildren(viewObject).iterator(); it
                .hasNext();) {
            result.add(((OrganizationModelNodeDescriptor) it.next())
                    .getModelElement());
        }
        return result;
    }

    /**
     * @generated
     */
    @Override
    protected boolean shouldDeleteView(View view) {
        return true;
    }

    /**
     * @generated
     */
    @Override
    protected boolean isOrphaned(Collection semanticChildren, final View view) {
        int visualID = OrganizationModelVisualIDRegistry.getVisualID(view);
        switch (visualID) {
        case OrganizationEditPart.VISUAL_ID:
            if (!semanticChildren.contains(view.getElement())) {
                return true;
            }
        }
        return false;
    }

    /**
     * @generated
     */
    @Override
    protected String getDefaultFactoryHint() {
        return null;
    }

    /**
     * @generated NOT
     */
    @Override
    protected Set getFeaturesToSynchronize() {
        if (myFeaturesToSynchronize == null) {
            myFeaturesToSynchronize = new HashSet();
            myFeaturesToSynchronize.add(OMPackage.eINSTANCE
                    .getOrgModel_Organizations());
            myFeaturesToSynchronize.add(OMPackage.eINSTANCE
                    .getOrgModel_DynamicOrgReferences());
        }
        return myFeaturesToSynchronize;
    }

    /**
     * @generated
     */
    @Override
    protected List getSemanticConnectionsList() {
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated
     */
    @Override
    protected EObject getSourceElement(EObject relationship) {
        return null;
    }

    /**
     * @generated
     */
    @Override
    protected EObject getTargetElement(EObject relationship) {
        return null;
    }

    /**
     * @generated
     */
    @Override
    protected boolean shouldIncludeConnection(Edge connector,
            Collection children) {
        return false;
    }

    /**
     * @generated NOT
     */
    @Override
    protected void refreshSemantic() {
        List createdViews = new LinkedList();
        createdViews.addAll(refreshBadge());
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

    }

    /**
     * 
     * If the Badge is not present on the diagram then create a new view.
     * 
     * 
     * @return List
     */
    private List refreshBadge() {

        // Don't try to refresh children if the semantic element
        // cannot be resolved.
        if (resolveSemanticElement() == null) {
            return null;
        }

        // Check if badge exists
        List viewChildren = getViewChildren();

        boolean badgeExists = false;

        for (Object object : viewChildren) {
            if (object instanceof Node) {
                Node node = (Node) object;
                if (node.getType().equals(OrgModelBadgeEditPart.VISUAL_ID)) {
                    badgeExists = true;
                    break;
                }
            }
        }

        if (!badgeExists) {
            EObject eo = resolveSemanticElement();

            if (eo instanceof OrgModel) {
                OrgModel om = (OrgModel) eo;
                ViewDescriptor des =
                        new ViewDescriptor(
                                new EObjectAdapter(om),
                                Node.class,
                                OrgModelBadgeEditPart.VISUAL_ID,
                                OrganizationModelDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
                CreateViewRequest req = new CreateViewRequest(des);

                req.setLocation(new Point(0, 0));

                Command cmd = getCreateViewCommand(req);

                if (cmd != null && cmd.canExecute()) {
                    SetViewMutabilityCommand.makeMutable(new EObjectAdapter(
                            host().getNotationView())).execute();
                    executeCommand(cmd);
                    List adapters = (List) req.getNewObject();

                    if (!adapters.isEmpty()) {
                        postProcessRefreshSemantic(adapters);
                    }
                    return adapters;
                }

            }

        }

        return Collections.EMPTY_LIST;
    }

    /**
     * @generated
     */
    private Diagram getDiagram() {
        return ((View) getHost().getModel()).getDiagram();
    }

    /**
     * @generated
     */
    private Collection refreshConnections() {
        Map domain2NotationMap = new HashMap();
        Collection linkDescriptors =
                collectAllLinks(getDiagram(), domain2NotationMap);
        Collection existingLinks = new LinkedList(getDiagram().getEdges());
        for (Iterator linksIterator = existingLinks.iterator(); linksIterator
                .hasNext();) {
            Edge nextDiagramLink = (Edge) linksIterator.next();
            int diagramLinkVisualID =
                    OrganizationModelVisualIDRegistry
                            .getVisualID(nextDiagramLink);
            if (diagramLinkVisualID == -1) {
                if (nextDiagramLink.getSource() != null
                        && nextDiagramLink.getTarget() != null) {
                    linksIterator.remove();
                }
                continue;
            }
            EObject diagramLinkObject = nextDiagramLink.getElement();
            EObject diagramLinkSrc = nextDiagramLink.getSource().getElement();
            EObject diagramLinkDst = nextDiagramLink.getTarget().getElement();
            for (Iterator LinkDescriptorsIterator = linkDescriptors.iterator(); LinkDescriptorsIterator
                    .hasNext();) {
                OrganizationModelLinkDescriptor nextLinkDescriptor =
                        (OrganizationModelLinkDescriptor) LinkDescriptorsIterator
                                .next();
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
        return createConnections(linkDescriptors, domain2NotationMap);
    }

    /**
     * @generated
     */
    private Collection collectAllLinks(View view, Map domain2NotationMap) {
        if (!OrgModelEditPart.MODEL_ID.equals(OrganizationModelVisualIDRegistry
                .getModelID(view))) {
            return Collections.EMPTY_LIST;
        }
        Collection result = new LinkedList();
        switch (OrganizationModelVisualIDRegistry.getVisualID(view)) {
        case OrgModelEditPart.VISUAL_ID: {
            if (!domain2NotationMap.containsKey(view.getElement())) {
                result.addAll(OrganizationModelDiagramUpdater
                        .getOrgModel_79ContainedLinks(view));
            }
            if (!domain2NotationMap.containsKey(view.getElement())
                    || view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
                domain2NotationMap.put(view.getElement(), view);
            }
            break;
        }
        case OrganizationEditPart.VISUAL_ID: {
            if (!domain2NotationMap.containsKey(view.getElement())) {
                result.addAll(OrganizationModelDiagramUpdater
                        .getOrganization_1001ContainedLinks(view));
            }
            if (!domain2NotationMap.containsKey(view.getElement())
                    || view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
                domain2NotationMap.put(view.getElement(), view);
            }
            break;
        }
        case OrgUnitEditPart.VISUAL_ID: {
            if (!domain2NotationMap.containsKey(view.getElement())) {
                result.addAll(OrganizationModelDiagramUpdater
                        .getOrgUnit_2001ContainedLinks(view));
            }
            if (!domain2NotationMap.containsKey(view.getElement())
                    || view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
                domain2NotationMap.put(view.getElement(), view);
            }
            break;
        }
        case DynamicOrgUnitEditPart.VISUAL_ID: {
            if (!domain2NotationMap.containsKey(view.getElement())) {
                result.addAll(OrganizationModelDiagramUpdater
                        .getDynamicOrgUnit_2003ContainedLinks(view));
            }
            if (!domain2NotationMap.containsKey(view.getElement())
                    || view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
                domain2NotationMap.put(view.getElement(), view);
            }
            break;
        }
        case OrgUnitRelationshipEditPart.VISUAL_ID: {
            if (!domain2NotationMap.containsKey(view.getElement())) {
                result.addAll(OrganizationModelDiagramUpdater
                        .getOrgUnitRelationship_3001ContainedLinks(view));
            }
            if (!domain2NotationMap.containsKey(view.getElement())
                    || view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
                domain2NotationMap.put(view.getElement(), view);
            }
            break;
        }
        case DynamicOrgReferenceEditPart.VISUAL_ID: {
            if (!domain2NotationMap.containsKey(view.getElement())) {
                result.addAll(OrganizationModelDiagramUpdater
                        .getDynamicOrgReference_3002ContainedLinks(view));
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
            final OrganizationModelLinkDescriptor nextLinkDescriptor =
                    (OrganizationModelLinkDescriptor) linkDescriptorsIterator
                            .next();
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

    public void performAutoArrange() {
        EditPart host = getHost();
        if (host != null && host instanceof OrgModelEditPart) {

            ArrangeRequest request =
                    new ArrangeRequest(ActionIds.ACTION_ARRANGE_ALL, "DEFAULT"); //$NON-NLS-1$

            List<EditPart> editParts = new ArrayList<EditPart>();
            editParts.add(host);
            request.setPartsToArrange(editParts);
            Command cmd = host.getCommand(request);

            if (cmd != null) {
                if (cmd.canExecute()) {
                    executeCommand(cmd);
                }
            }
        }

    }

}
