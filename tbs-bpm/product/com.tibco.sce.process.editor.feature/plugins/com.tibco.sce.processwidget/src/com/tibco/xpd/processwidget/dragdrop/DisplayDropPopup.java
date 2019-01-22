/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.processwidget.dragdrop;

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;

import com.tibco.xpd.processwidget.adapters.DropObjectPopupItem;
import com.tibco.xpd.processwidget.dragdrop.DropTargetUtil.DropTargetEditPartInfo;
import com.tibco.xpd.processwidget.impl.ProcessWidgetImpl;

/**
 * The drag-drop popup (i.e. the action performed for a LocalSelectionTransfer
 * drop on diagram MUST be performed via a Display.asynchExec().
 * <p>
 * This is because having created a popup menu on a drop, there is nothing that
 * prevents user from starting another drag-drop operation without explicitly
 * cancelling the first.
 * <p>
 * Unfortunately if this happens and we do NOT give the first drop chance to
 * terminate and cleanup (by using an asynchExec()), then when the second drop
 * is performed (and quit) the native drag drop gets confused and causes
 * exceptions.
 * <p>
 * Doing an asynchExec() gives the underlying drag-drop stuff chance to clear up
 * before next drag-drop starts.
 * 
 * @author aallway
 * 
 */
public class DisplayDropPopup implements Runnable {

    private ProcessWidgetImpl processWidgetImpl;

    private DropPopupMenu popupMenu;

    private IFigure targetContainerFeedback;

    private Point targetContainerDropLocation;

    private DropTargetEditPartInfo dropInfo;

    /**
     * A runnable that displays the given drop popup menu, executes the
     * user-selected popup item then clears up the in-target container feedback
     * (if there is any) i.e. removes the given targetContainerFeedback figure
     * from its parent.
     * 
     * @param processWidgetImpl
     * @param popupMenu
     * @param targetContainerFeedback
     */
    public DisplayDropPopup(ProcessWidgetImpl processWidgetImpl,
            DropPopupMenu popupMenu, IFigure targetContainerFeedback,
            Point targetContainerDropLocation, DropTargetEditPartInfo dropInfo) {
        this.processWidgetImpl = processWidgetImpl;
        this.popupMenu = popupMenu;
        this.targetContainerFeedback = targetContainerFeedback;
        this.targetContainerDropLocation = targetContainerDropLocation;
        this.dropInfo = dropInfo;
    }

    @Override
    public void run() {

        // System.out.println("Running popup..."); //$NON-NLS-1$
        /*
         * XPD-3541 Set menu location before show, for consistency of display
         * location at drop location
         */
        popupMenu.setLocation(dropInfo.dropEventLocation.getSWTPoint());
        if (popupMenu.show(processWidgetImpl.getControl())) {
            // User didn't cancel, get the selected command and
            // execute it.
            Object res = popupMenu.getResult();

            DropObjectPopupItem popupItem = null;

            if (res instanceof DropObjectPopupItem) {
                popupItem = (DropObjectPopupItem) res;
            } else if (res instanceof List) {
                Object o = ((List) res).get(((List) res).size() - 1);

                if (o instanceof DropObjectPopupItem) {
                    popupItem = (DropObjectPopupItem) o;
                }
            }

            if (popupItem != null) {
                DropObjectPopupItemUtil.executeDropPopupItem(popupItem,
                        processWidgetImpl,
                        dropInfo.targetEP,
                        dropInfo.targetContainer,
                        targetContainerDropLocation);
            }
        }

        // System.out.println("...BackFrompopup"); //$NON-NLS-1$

        // Cleanup feedback figure
        if (targetContainerFeedback != null) {
            IFigure parent = targetContainerFeedback.getParent();
            if (parent != null) {
                parent.remove(targetContainerFeedback);
            }
        }

    }

}