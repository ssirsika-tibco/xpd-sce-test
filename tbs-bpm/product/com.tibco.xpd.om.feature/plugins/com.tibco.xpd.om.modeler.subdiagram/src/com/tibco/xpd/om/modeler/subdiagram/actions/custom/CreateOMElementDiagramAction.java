/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.modeler.subdiagram.actions.custom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.ui.actions.DiagramAction;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramGraphicalViewer;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest.ViewAndElementDescriptor;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;

import com.tibco.xpd.om.core.om.Feature;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.OrgUnitFeature;
import com.tibco.xpd.om.core.om.OrgUnitType;
import com.tibco.xpd.om.core.om.OrganizationType;
import com.tibco.xpd.om.core.om.PositionFeature;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.DynamicOrgUnitEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.OrgUnitSubEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.PositionSubEditPart;
import com.tibco.xpd.om.modeler.subdiagram.part.Messages;
import com.tibco.xpd.om.modeler.subdiagram.part.OrganizationModelSubDiagramEditorPlugin;
import com.tibco.xpd.om.modeler.subdiagram.part.custom.IOrganizationModelDiagramConstants;
import com.tibco.xpd.om.modeler.subdiagram.providers.OrganizationModelElementTypes;

/**
 * 
 * Creates an Organization Model Element via a CreateViewAndElement Request.
 * 
 * @author rgreen
 * 
 */
public class CreateOMElementDiagramAction extends DiagramAction {

    private Feature feature;

    private OrgUnit parentOrgUnit;

    private OrganizationType orgType;

    public CreateOMElementDiagramAction(IWorkbenchPage workbenchPage,
            String actionId, ImageDescriptor imageDescriptor) {
        super(workbenchPage);
        setId(actionId);
        setImageDescriptor(imageDescriptor);

        if (actionId.equals(OMActionIds.ACTION_ADD_ORGUNIT)) {
            setText(Messages.CreateOMElementDiagramAction_AddOrganizationUnitLabel);
        }

        // XPD-5300: Dynamic OrgUnit
        if (actionId.equals(OMActionIds.ACTION_ADD_DYNAMICORGUNIT)) {
            setText(Messages.CreateOMElementDiagramAction_AddDynamicOrganizationUnitLabel);
        }

        else if (actionId.equals(OMActionIds.ACTION_ADD_POSITION)) {
            setText(Messages.CreateOMElementDiagramAction_AddPositionLabel);
        }

        else if (actionId.equals(OMActionIds.ACTION_ADD_ORGANIZATION)) {
            setText(Messages.CreateOMElementDiagramAction_AddOrganization);

        } else if (actionId.equals(OMActionIds.ACTION_ADD_DYNAMICORGANIZATION)) {
            setText(Messages.CreateOMElementDiagramAction_AddDynamicOrganization);
        }

    }

    public CreateOMElementDiagramAction(IWorkbenchPage workbenchPage,
            String actionId, ImageDescriptor imageDescriptor,
            OrganizationType orgType) {
        super(workbenchPage);
        setId(actionId);
        setImageDescriptor(imageDescriptor);
        setText(orgType.getDisplayName());
        setToolTipText(String
                .format(Messages.CreateOMElementDiagramAction_AddMenuItem,
                        getText()));
        setOrgType(orgType);
    }

    public CreateOMElementDiagramAction(IWorkbenchPage workbenchPage,
            String actionId, ImageDescriptor imageDescriptor, Feature feature) {
        super(workbenchPage);
        setId(actionId);
        setImageDescriptor(imageDescriptor);
        setText(feature.getDisplayName());
        setToolTipText(String
                .format(Messages.CreateOMElementDiagramAction_AddMenuItem,
                        getText()));
        setFeature(feature);
    }

    /**
     * 
     * If the feature passed in is of type OrgUnitFeature and is a subunit, then
     * the parentOrgUnit parameter can be used to specify its parent OrgUnit.
     * 
     * @param workbenchPage
     * @param actionId
     * @param imageDescriptor
     * @param feature
     * @param parentOrgUnit
     */
    public CreateOMElementDiagramAction(IWorkbenchPage workbenchPage,
            String actionId, ImageDescriptor imageDescriptor, Feature feature,
            OrgUnit parentOrgUnit) {
        super(workbenchPage);
        setId(actionId);
        setImageDescriptor(imageDescriptor);
        setText(feature.getDisplayName());
        setToolTipText(String
                .format(Messages.CreateOMElementDiagramAction_AddMenuItem,
                        getText()));
        setFeature(feature);
        setParentOrgUnit(parentOrgUnit);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gmf.runtime.diagram.ui.actions.DiagramAction#getCommand(org
     * .eclipse.gef.Request)
     */
    @Override
    protected Command getCommand(Request request) {
        if (request instanceof CreateViewAndElementRequest) {

            // If this is a request to create a subunit feature then we need to
            // get the command from the right editpart i.e. the diagram
            // (Organization).
            if (request
                    .getExtendedData()
                    .containsKey(IOrganizationModelDiagramConstants.OMCreationToolIsSubUnit)) {

                Object object =
                        request.getExtendedData()
                                .get(IOrganizationModelDiagramConstants.OMCreationToolParentOrgUnit);

                if (object instanceof OrgUnit) {
                    // get the Organization editpart (which will be the diagram)
                    DiagramEditPart diagramEditPart = getDiagramEditPart();
                    CompoundCommand command =
                            new CompoundCommand(getCommandLabel());

                    if (diagramEditPart != null) {
                        Command curCommand =
                                diagramEditPart.getCommand(request);

                        if (curCommand != null) {
                            command.add(curCommand);
                        }
                    }

                    return command.isEmpty() ? UnexecutableCommand.INSTANCE
                            : (Command) command;
                }
            }
        }
        return super.getCommand(request);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gmf.runtime.diagram.ui.actions.DiagramAction#createTargetRequest
     * ()
     */
    @Override
    protected Request createTargetRequest() {
        CreateViewAndElementRequest req = null;
        ViewAndElementDescriptor desc = null;

        // This method is overidden so that we can step in and create a
        // CreateViewAndElementRequest.
        if (getId() == OMActionIds.ACTION_ADD_ORGUNIT) {
            desc =
                    new ViewAndElementDescriptor(
                            new CreateElementRequestAdapter(
                                    new CreateElementRequest(
                                            OrganizationModelElementTypes.OrgUnit_1001)),
                            Node.class,
                            String.valueOf(OrgUnitSubEditPart.VISUAL_ID),
                            OrganizationModelSubDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);

        }

        // XPD-5300 Dynamic OrgUnit
        if (getId() == OMActionIds.ACTION_ADD_DYNAMICORGUNIT) {
            desc =
                    new ViewAndElementDescriptor(
                            new CreateElementRequestAdapter(
                                    new CreateElementRequest(
                                            OrganizationModelElementTypes.DynamicOrgUnit_1002)),
                            Node.class,
                            String.valueOf(DynamicOrgUnitEditPart.VISUAL_ID),
                            OrganizationModelSubDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);

        }

        if (getId() == OMActionIds.ACTION_ADD_POSITION) {
            desc =
                    new ViewAndElementDescriptor(
                            new CreateElementRequestAdapter(
                                    new CreateElementRequest(
                                            OrganizationModelElementTypes.Position_2001)),
                            Node.class,
                            String.valueOf(PositionSubEditPart.VISUAL_ID),
                            OrganizationModelSubDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
        }

        if (desc != null) {
            req = new CreateViewAndElementRequest(desc);

            Map<String, Object> extData = new HashMap<String, Object>();
            // Add extended data
            if (req != null && feature != null) {
                if (feature instanceof OrgUnitFeature) {
                    extData.put(IOrganizationModelDiagramConstants.OMCreationToolFeature,
                            feature);

                    EObject container = feature.eContainer();
                    if (container instanceof OrgUnitType) {
                        // This is a subunit
                        extData.put(IOrganizationModelDiagramConstants.OMCreationToolIsSubUnit,
                                new Boolean(true));
                        extData.put(IOrganizationModelDiagramConstants.OMCreationToolParentOrgUnit,
                                parentOrgUnit);
                    }
                }

                if (feature instanceof PositionFeature) {
                    extData.put(IOrganizationModelDiagramConstants.OMCreationToolFeature,
                            feature);
                }

                req.setExtendedData(extData);
            }

            if (req != null && orgType != null) {
                extData.put(IOrganizationModelDiagramConstants.OMCreationToolOrgElementType,
                        orgType);
                req.setExtendedData(extData);
            }

        }

        return req;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gmf.runtime.diagram.ui.actions.DiagramAction#updateTargetRequest
     * ()
     */
    @Override
    protected void updateTargetRequest() {

        super.updateTargetRequest();

        CreateViewRequest req = (CreateViewRequest) getTargetRequest();

        if (req != null) {
            req.setLocation(getMouseLocation());

            // Offset the subunit from the parent OrgUnit
            if (req.getExtendedData()
                    .containsKey(IOrganizationModelDiagramConstants.OMCreationToolParentOrgUnit)) {
                Point mouseLocation = getMouseLocation();
                if (mouseLocation != null) {
                    Point location = new Point();
                    location.x = mouseLocation.x + 25;
                    location.y = mouseLocation.y + 100;
                    req.setLocation(location);
                }

            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gmf.runtime.diagram.ui.actions.DiagramAction#isSelectionListener
     * ()
     */
    @Override
    protected boolean isSelectionListener() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gmf.runtime.diagram.ui.actions.DiagramAction#doRun(org.eclipse
     * .core.runtime.IProgressMonitor)
     */
    @Override
    protected void doRun(IProgressMonitor progressMonitor) {
        super.doRun(progressMonitor);
        selectAddedObject();
    }

    /**
     * Selects the newly added shape view(s) by default.
     */
    protected void selectAddedObject() {
        Object result = ((CreateRequest) getTargetRequest()).getNewObject();
        if (!(result instanceof Collection)) {
            return;
        }
        final List editparts = new ArrayList(1);

        IDiagramGraphicalViewer viewer = getDiagramGraphicalViewer();
        if (viewer == null) {
            return;
        }

        Map editpartRegistry = viewer.getEditPartRegistry();
        for (Iterator iter = ((Collection) result).iterator(); iter.hasNext();) {
            Object viewAdaptable = iter.next();
            if (viewAdaptable instanceof IAdaptable) {
                Object editPart =
                        editpartRegistry.get(((IAdaptable) viewAdaptable)
                                .getAdapter(View.class));
                if (editPart != null)
                    editparts.add(editPart);
            }
        }

        if (!editparts.isEmpty()) {
            viewer.setSelection(new StructuredSelection(editparts));

            // automatically put the first shape into edit-mode
            Display.getCurrent().asyncExec(new Runnable() {

                @Override
                public void run() {
                    EditPart editPart = (EditPart) editparts.get(0);
                    editPart.performRequest(new Request(
                            RequestConstants.REQ_DIRECT_EDIT));
                }
            });
        }
    }

    /**
     * @return
     */
    public Feature getFeature() {
        return feature;
    }

    /**
     * @param feature
     */
    public void setFeature(Feature feature) {
        this.feature = feature;
    }

    /**
     * @return
     */
    public OrgUnit getParentOrgUnit() {
        return parentOrgUnit;
    }

    /**
     * @param parentOrgUnit
     */
    public void setParentOrgUnit(OrgUnit parentOrgUnit) {
        this.parentOrgUnit = parentOrgUnit;
    }

    public OrganizationType getOrgType() {
        return orgType;
    }

    public void setOrgType(OrganizationType orgType) {
        this.orgType = orgType;
    }

}
