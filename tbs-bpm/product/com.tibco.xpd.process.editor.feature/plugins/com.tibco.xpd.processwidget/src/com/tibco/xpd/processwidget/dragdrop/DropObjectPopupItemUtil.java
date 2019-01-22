/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processwidget.dragdrop;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.StructuredSelection;

import com.tibco.xpd.processwidget.adapters.DropObjectPopupItem;
import com.tibco.xpd.processwidget.adapters.DropObjectPopupItem.DropPopupCustomCommand;
import com.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter;
import com.tibco.xpd.processwidget.commands.internal.XPDPasteCommand;
import com.tibco.xpd.processwidget.impl.ProcessWidgetImpl;
import com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart;
import com.tibco.xpd.processwidget.parts.ModelAdapterEditPart;

/**
 * DropObjectPopupItemUtil
 * 
 * 
 * @author aallway
 * @since 3.3 (19 Aug 2009)
 */
public class DropObjectPopupItemUtil {

    /**
     * Execute the given drop popup menu item.
     * 
     * @param popupItem
     * @param processWidgetImpl
     * @param targetEP
     * @param targetContainer
     * @param adjustedContainerRelativeLocation
     */
    public static void executeDropPopupItem(DropObjectPopupItem popupItem,
            ProcessWidgetImpl processWidgetImpl, ModelAdapterEditPart targetEP,
            BaseGraphicalEditPart targetContainer,
            Point adjustedContainerRelativeLocation) {

        if (DropObjectPopupItem.DropPopupItemType.CUSTOM_COMMAND
                .equals(popupItem.getPopupItemType())) {
            DropPopupCustomCommand command = popupItem.getCustomCommand();
            DropObjectPopupItem newCmd =
                    command.execute(processWidgetImpl.getControl());

            if (newCmd != null) {
                executeDropPopupItem(newCmd,
                        processWidgetImpl,
                        targetEP,
                        targetContainer,
                        adjustedContainerRelativeLocation);
            }

        } else {
            // Not a custom command, we can assume its an executable one.
            Command cmd =
                    getCommandForPopupItem(processWidgetImpl,
                            popupItem,
                            targetEP,
                            targetContainer,
                            adjustedContainerRelativeLocation);
            if (cmd.canExecute()) {
                processWidgetImpl.getEditingDomain().getCommandStack()
                        .execute(cmd);

                // After executing command, select the affected edit parts.
                processWidgetImpl.getGraphicalViewer().getRootEditPart()
                        .refresh();

                List<EditPart> affectedEditParts = null;

                if (DropObjectPopupItem.DropPopupItemType.EMF_COMMAND
                        .equals(popupItem.getPopupItemType())) {
                    // For an EMF command, we'll assume that it was run on the
                    // target edit part.
                    affectedEditParts = new ArrayList<EditPart>(1);
                    affectedEditParts.add(targetEP);

                    processWidgetImpl.getGraphicalViewer()
                            .setSelection(new StructuredSelection(
                                    affectedEditParts));
                    processWidgetImpl.getGraphicalViewer().getControl()
                            .setFocus();

                } else if (DropObjectPopupItem.DropPopupItemType.CREATE_DIAGRAM_OBJECTS
                        .equals(popupItem.getPopupItemType())) {
                    // For a create diagram objects we'll assume that the edit
                    // parts that host the created objects are the affected edit
                    // parts.
                    XPDPasteCommand pasteCmd = getXPDPasteCommandFromCmd(cmd);

                    if (pasteCmd != null) {
                        // Select the model objects.
                        processWidgetImpl.setSelFromPastedObjects(pasteCmd);
                    }
                }

            }
        }

        return;
    }

    /**
     * Get an EMF command for the given drop popup menu item.
     * 
     * @param popupItem
     * @return
     */
    private static Command getCommandForPopupItem(
            ProcessWidgetImpl processWidgetImpl, DropObjectPopupItem popupItem,
            ModelAdapterEditPart targetEP,
            BaseGraphicalEditPart targetContainer, Point finalRelativeLocation) {
        if (DropObjectPopupItem.DropPopupItemType.EMF_COMMAND.equals(popupItem
                .getPopupItemType())) {
            // It's already a command.
            CompoundCommand cmd = new CompoundCommand();
            cmd.setLabel(popupItem.getLabel());
            cmd.append(popupItem.getPopupItemCommand());

            return cmd;

        } else if (DropObjectPopupItem.DropPopupItemType.CREATE_DIAGRAM_OBJECTS
                .equals(popupItem.getPopupItemType())) {

            ProcessDiagramAdapter processAdapter =
                    getProcessDiagramAdapter(processWidgetImpl);

            // Wrap up objects toi create in a paste command.
            CompoundCommand cmd = new CompoundCommand();
            cmd.setLabel(popupItem.getLabel());

            XPDPasteCommand pasteCmd =
                    new XPDPasteCommand(processWidgetImpl.getEditingDomain(),
                            processAdapter, targetEP, finalRelativeLocation,
                            targetContainer,
                            popupItem.getCreateDiagramObjectsList());
            cmd.append(pasteCmd);

            // If there is also an extra command to wrap up with paste command,
            // do it.
            if (popupItem.getPopupItemCommand() != null
                    && popupItem.getPopupItemCommand().canExecute()) {
                cmd.append(popupItem.getPopupItemCommand());
            }

            return cmd;

        }
        return UnexecutableCommand.INSTANCE;
    }

    /**
     * @param cmd
     * @return
     */
    private static XPDPasteCommand getXPDPasteCommandFromCmd(Command cmd) {
        XPDPasteCommand pasteCmd = null;

        if (cmd instanceof XPDPasteCommand) {
            pasteCmd = (XPDPasteCommand) cmd;
        } else if (cmd instanceof CompoundCommand) {
            for (Iterator iterator =
                    ((CompoundCommand) cmd).getCommandList().iterator(); iterator
                    .hasNext();) {
                Object childCmd = iterator.next();
                if (childCmd instanceof XPDPasteCommand) {
                    pasteCmd = (XPDPasteCommand) childCmd;
                }

            }
        }
        return pasteCmd;
    }

    private static ProcessDiagramAdapter getProcessDiagramAdapter(
            ProcessWidgetImpl processWidgetImpl) {
        ProcessDiagramAdapter adapter =
                (ProcessDiagramAdapter) processWidgetImpl
                        .getWidgetAdapter(processWidgetImpl.getInput());
        return adapter;
    }

}
