/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.modeler.subdiagram.edit.policies.custom;

import java.util.Map;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.ui.util.ICustomData;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ContainerEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.requests.PasteViewRequest;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.MapModeUtil;
import org.eclipse.gmf.runtime.notation.View;

import com.tibco.xpd.om.modeler.subdiagram.part.Messages;
import com.tibco.xpd.om.resources.ui.clipboard.OMClipboardHelper;

/**
 * {@link ContainerEditPolicy} extension that provides customised paste command
 * for the OM elements that use this policy.
 * 
 * @author njpatel
 * 
 */
public class OMContainerEditPolicy extends ContainerEditPolicy {

    @Override
    protected Command getPasteCommand(PasteViewRequest request) {
        /* Get the view context */
        IGraphicalEditPart editPart = (IGraphicalEditPart) getHost();
        View viewContext = (View) ((IAdaptable) editPart)
                .getAdapter(View.class);
        /* Get the clipboard data */
        ICustomData[] data = request.getData();
        Point location = new Point(0, 0);
        if (editPart != null && viewContext != null && data != null) {

            Map<?, ?> extData = request.getExtendedData();
            if (extData != null && !extData.isEmpty()) {
                Object obj = extData.get(OMClipboardHelper.PASTE_POSITION);
                if (obj instanceof org.eclipse.swt.graphics.Point) {
                    org.eclipse.swt.graphics.Point target = (org.eclipse.swt.graphics.Point) obj;
                    location.setLocation(target.x, target.y);
                }
            }

            return new ICommandProxy(OMClipboardHelper.getInstance()
                    .getPasteCommand(editPart.getEditingDomain(),
                            Messages.OMContainerEditPolicy_copy_action,
                            viewContext, data,
                            MapModeUtil.getMapMode(editPart.getFigure()),
                            location));
        }

        return null;
    }
}