/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.modeler.diagram.actions.custom;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest.ViewAndElementDescriptor;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbenchPage;

import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.OrganizationType;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrganizationEditPart;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelDiagramEditorPlugin;
import com.tibco.xpd.om.modeler.subdiagram.actions.custom.OMActionIds;
import com.tibco.xpd.om.modeler.subdiagram.part.custom.IOrganizationModelDiagramConstants;

/**
 * 
 * Creates an Organization Model Element via a CreateViewAndElement Request.
 * 
 * @author rgreen
 * 
 */
public class CreateOMElementDiagramAction
        extends
        com.tibco.xpd.om.modeler.subdiagram.actions.custom.CreateOMElementDiagramAction {

    public CreateOMElementDiagramAction(IWorkbenchPage workbenchPage,
            String actionId, ImageDescriptor imageDescriptor) {
        super(workbenchPage, actionId, imageDescriptor);
    }

    public CreateOMElementDiagramAction(IWorkbenchPage workbenchPage,
            String actionId, ImageDescriptor imageDescriptor,
            OrganizationType orgType) {
        super(workbenchPage, actionId, imageDescriptor, orgType);
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
            CompoundCommand command = new CompoundCommand(getCommandLabel());

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
        // XPD-5300 Update request if creating dynamic organization with the
        // appropriate parameter.
        if (getId() == OMActionIds.ACTION_ADD_ORGANIZATION
                || getId() == OMActionIds.ACTION_ADD_DYNAMICORGANIZATION) {
            CreateElementRequest request =
                    new CreateElementRequest(
                            com.tibco.xpd.om.modeler.diagram.providers.OrganizationModelElementTypes.Organization_1001);

            if (getId() == OMActionIds.ACTION_ADD_DYNAMICORGANIZATION) {
                request.setParameter(OrganizationEditPart.DYNAMIC, Boolean.TRUE);
            }

            desc =
                    new ViewAndElementDescriptor(
                            new CreateElementRequestAdapter(request),
                            Node.class,
                            String.valueOf(OrganizationEditPart.VISUAL_ID),
                            OrganizationModelDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
        }

        if (desc != null) {
            req = new CreateViewAndElementRequest(desc);

            Map<String, Object> extData = new HashMap<String, Object>();
            // Add extended data

            if (req != null && getOrgType() != null) {
                extData.put(IOrganizationModelDiagramConstants.OMCreationToolOrgElementType,
                        getOrgType());
                req.setExtendedData(extData);
            }

        }

        return req;
    }

}
