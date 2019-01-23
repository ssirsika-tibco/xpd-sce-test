/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.modeler.custom.actions;

import java.util.List;

import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.diagram.ui.actions.DiagramAction;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.ui.IWorkbenchPage;

import com.tibco.xpd.om.modeler.custom.internal.Messages;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrgModelEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.OrgModelBadgeEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.OrganizationSubEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.custom.BadgeEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.custom.OrganizationSubBadgeEditPart;
import com.tibco.xpd.om.modeler.subdiagram.requests.custom.ToggleViewVisibilityCustomRequest;

/**
 * Action to show/hide the badge in the diagram.
 * 
 * @author rgreen
 * 
 */
public class ShowHideBadgeDiagramAction extends DiagramAction {

    /**
     * Show/hide the badge in the diagram.
     * 
     * @param workbenchPage
     */
    public ShowHideBadgeDiagramAction(IWorkbenchPage workbenchPage) {
        super(workbenchPage);
        setText(Messages.ShowHideBadgeDiagramAction_showHideBadge_action);
    }

    @Override
    protected void updateTargetRequest() {

        // We need to update the target every time we build the context menu in
        // case we have activated another diagram.
        List selectedObjects = getSelectedObjects();

        if (selectedObjects.size() == 1) {
            Request req = getTargetRequest();
            IGraphicalEditPart gEp = null;

            if (selectedObjects.get(0) instanceof BadgeEditPart) {
                gEp = (GraphicalEditPart) selectedObjects.get(0);

            } else if (selectedObjects.get(0) instanceof OrgModelEditPart
                    || selectedObjects.get(0) instanceof OrganizationSubEditPart) {
                GraphicalEditPart diagEp = (GraphicalEditPart) selectedObjects
                        .get(0);

                if (diagEp instanceof OrgModelEditPart) {
                    gEp = diagEp
                            .getChildBySemanticHint(OrgModelBadgeEditPart.VISUAL_ID);
                }

                if (diagEp instanceof OrganizationSubEditPart) {
                    gEp = diagEp
                            .getChildBySemanticHint(OrganizationSubBadgeEditPart.VISUAL_ID);
                }

            }

            if (gEp != null) {
                ((ToggleViewVisibilityCustomRequest) req).setView(gEp
                        .getPrimaryView());
            }
        }

    }

    @Override
    protected Request createTargetRequest() {

        Request request = null;
        DiagramEditPart diagramEditPart = getDiagramEditPart();

        List<?> children = diagramEditPart.getChildren();
        View view = null;
        for (Object object : children) {
            if (object instanceof BadgeEditPart) {
                BadgeEditPart badgeEP = (BadgeEditPart) object;
                view = badgeEP.getPrimaryView();
            }
        }

        if (view != null) {
            request = new ToggleViewVisibilityCustomRequest(view);
        }

        return request;
    }

    @Override
    protected boolean isSelectionListener() {
        return true;
    }

    @Override
    protected Command getCommand(Request request) {

        CompoundCommand command = new CompoundCommand(getCommandLabel());
        Command curCommand = getDiagramEditPart().getCommand(request);
        if (curCommand != null) {
            command.add(curCommand);
        }

        return command.isEmpty() ? UnexecutableCommand.INSTANCE
                : (Command) command;
    }

}
