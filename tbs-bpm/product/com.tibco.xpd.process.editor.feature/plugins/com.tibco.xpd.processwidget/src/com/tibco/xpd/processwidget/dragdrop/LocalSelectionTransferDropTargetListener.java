/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.processwidget.dragdrop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.util.TransferDropTargetListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.Transfer;

import com.tibco.xpd.processwidget.adapters.DropObjectPopupItem;
import com.tibco.xpd.processwidget.adapters.DropObjectPopupItem.DropPopupItemType;
import com.tibco.xpd.processwidget.adapters.DropTypeInfo;
import com.tibco.xpd.processwidget.adapters.XpdFlowRoutingStyle;
import com.tibco.xpd.processwidget.dragdrop.DropTargetUtil.DropTargetEditPartInfo;
import com.tibco.xpd.processwidget.figures.IHighlightableFigure;
import com.tibco.xpd.processwidget.figures.XPDFigureUtilities;
import com.tibco.xpd.processwidget.impl.ProcessWidgetImpl;
import com.tibco.xpd.processwidget.parts.BaseConnectionEditPart;
import com.tibco.xpd.processwidget.parts.ModelAdapterEditPart;
import com.tibco.xpd.processwidget.process.progression.EditPartHighlighter;

/**
 * @author aallway
 * 
 */
public class LocalSelectionTransferDropTargetListener implements
        TransferDropTargetListener {

    static final DropPopupItemLabelProvider DROP_POPUP_LABEL_PROVIDER =
            new DropPopupItemLabelProvider();

    ProcessWidgetImpl processWidgetImpl;

    private IFigure currHighlightFigure = null;

    private Set<IFigure> previousHighlightFigures = null;

    private DropDiagramObjectFeedbackHelper feedbackHelper;

    // There's a nasty defect 'images left behind' which is exacerbated by
    // trying to do feedback rectangles.
    // Have left the code for feedback rectangles here but disabled it with this
    // flag.
    // Think I have solved it by putting feedback rectangles ABOVE the cursor so
    // that the cursor drag-items bitmap should never appear over the feedback
    // rectangles therefore no nastiness.
    private boolean doFeedbackRects = true;

    private List<Rectangle> lastFeedbackRectangles = null;

    private Point lastDragOverPos = new Point(0, 0);

    private int lastUserSelectedDropType = DND.DROP_NONE;

    public LocalSelectionTransferDropTargetListener(ProcessWidgetImpl impl) {
        processWidgetImpl = impl;

        feedbackHelper = new DropDiagramObjectFeedbackHelper(processWidgetImpl);
    }

    @Override
    public void dragEnter(DropTargetEvent event) {
        //        System.out.println("DragEnter"); //$NON-NLS-1$

        lastDragOverPos = new Point(0, 0);

        previousHighlightFigures = new HashSet<IFigure>();

        lastUserSelectedDropType = event.detail;

        /*
         * When user is drag-dropping objects from explorer etc it isn't good to
         * have things that don't reference the selected object faded out as it
         * can look diabled to the user.
         * 
         * So prevent highlighter activation
         */
        EditPartHighlighter referenceHighlighter =
                processWidgetImpl.getReferenceHighlighter();
        if (referenceHighlighter != null) {
            referenceHighlighter.preventActivation();
        }
    }

    @Override
    public void dragLeave(DropTargetEvent event) {
        //        System.out.println("DragLeave"); //$NON-NLS-1$
        if (currHighlightFigure != null) {
            setHighlight(currHighlightFigure, false);
            currHighlightFigure = null;
        }

        if (doFeedbackRects) {
            feedbackHelper.disposeFeedbackFigures();
            processWidgetImpl.getFeedbackLayer().repaint();
        }

        lastUserSelectedDropType = DND.DROP_NONE;

        /*
         * When user is drag-dropping objects from explorer etc it isn't good to
         * have things that don't reference the selected object faded out as it
         * can look disabled to the user.
         * 
         * So re-allow activation of the reference highlighter when mouse
         * leaves.
         */
        EditPartHighlighter referenceHighlighter =
                processWidgetImpl.getReferenceHighlighter();

        if (referenceHighlighter != null) {
            referenceHighlighter.allowActivation();
        }
    }

    @Override
    public void dragOperationChanged(DropTargetEvent event) {

        DropTargetEditPartInfo dropInfo =
                DropTargetUtil.getDropTargetEditPartInfo(processWidgetImpl,
                        event);

        lastUserSelectedDropType = event.detail;

        if (dropInfo != null) {
            event.detail = getDropType(dropInfo, event.detail).getDndDropType();
        } else {
            event.detail = DND.DROP_NONE;
        }

    }

    @Override
    public void dragOver(DropTargetEvent event) {
        DropTypeInfo dropType = DropTypeInfo.DROP_NONE;

        IFigure newDropHighlightFigure = null;

        DropTargetEditPartInfo dropInfo =
                DropTargetUtil.getDropTargetEditPartInfo(processWidgetImpl,
                        event);

        if (dropInfo != null) {

            dropType = getDropType(dropInfo, lastUserSelectedDropType);
            event.detail = dropType.getDndDropType();

            if (event.detail != DND.DROP_NONE) {
                if (dropInfo.targetEP instanceof GraphicalEditPart) {
                    IFigure fig =
                            ((GraphicalEditPart) dropInfo.targetEP).getFigure();

                    newDropHighlightFigure = fig;

                    //
                    // Make cursor 'sticky' in cases where the event position
                    // has been adjusted to correct place (say for instance, to
                    // closest point on target connection line).
                    if (false && dropInfo.adjustedCursorLocation != null) {
                        org.eclipse.swt.graphics.Point cursorLoc =
                                new org.eclipse.swt.graphics.Point(
                                        dropInfo.adjustedCursorLocation.x,
                                        dropInfo.adjustedCursorLocation.y);
                        processWidgetImpl.getControl().getDisplay()
                                .setCursorLocation(cursorLoc);
                        // System.out.println("STICKY!"); //$NON-NLS-1$
                    }
                }
            }

        } else {
            event.detail = DND.DROP_NONE;
        }

        //
        // If the figure under mouse has changed then switch the highlight.
        //
        if (newDropHighlightFigure != currHighlightFigure) {
            //
            // If the new highlight figure is in list of figures that are being
            // unhighlighted on a timed basis then we need to remove it from
            // there.
            if (previousHighlightFigures.contains(newDropHighlightFigure)) {
                previousHighlightFigures.remove(newDropHighlightFigure);
            }

            if (currHighlightFigure != null) {
                //
                // Unhighlight current highlighted figure and add it to list of
                // figures we repaint periodically to ensure that when
                // drag-cursor image finally moves of, we do do a repaint.
                setHighlight(currHighlightFigure, false);
                previousHighlightFigures.add(currHighlightFigure);
            }

            currHighlightFigure = newDropHighlightFigure;
            setHighlight(currHighlightFigure, true);

            // When target changes, re-do feedback rectangles.
            resetFeedback(event, dropType, dropInfo);

        }

        updateFeedback(event, dropInfo);

        return;
    }

    @Override
    public void drop(DropTargetEvent event) {
        // System.out.println("Drop"); //$NON-NLS-1$
        if (currHighlightFigure != null) {
            setHighlight(currHighlightFigure, false);
            currHighlightFigure = null;
        }

        if (doFeedbackRects) {
            feedbackHelper.disposeFeedbackFigures();
        }

        boolean success = false;

        DropTargetEditPartInfo dropInfo =
                DropTargetUtil.getDropTargetEditPartInfo(processWidgetImpl,
                        event);

        if (dropInfo != null) {
            event.detail =
                    getDropType(dropInfo, lastUserSelectedDropType)
                            .getDndDropType();

            if (event.detail != DND.DROP_NONE) {

                //
                // Ask the target container for the popup items that are
                // appropriate for the give drop objects. Create a popup menu
                // (unless there is only one command item in which case it is
                // executed straight away)
                doDropPopupMenu(dropInfo, event.detail);

                success = true;
            }
        }

        if (!success) {
            event.detail = DND.DROP_NONE;
        }

        return;
    }

    @Override
    public void dropAccept(DropTargetEvent event) {
    }

    @Override
    public Transfer getTransfer() {
        return LocalSelectionTransfer.getTransfer();
    }

    @Override
    public boolean isEnabled(DropTargetEvent event) {
        // Always return true, we will sort out whether its enabled once we know
        // the drop target edit part.
        return true;

    }

    /**
     * Get the objects from selection transfer as a list.
     * 
     * @return
     */
    private List<Object> getDropObjects() {
        List<Object> dropObjects = new ArrayList<Object>();
        ISelection selection =
                LocalSelectionTransfer.getTransfer().getSelection();

        if (selection instanceof IStructuredSelection) {
            IStructuredSelection sel = (IStructuredSelection) selection;

            for (Iterator iterator = sel.iterator(); iterator.hasNext();) {
                Object o = iterator.next();

                dropObjects.add(o);
            }
        }
        return dropObjects;
    }

    /**
     * Get the drop type (copy/move/none) from target container
     * 
     * @param dropInfo
     * @param dndDropType
     * @return
     */
    private DropTypeInfo getDropType(DropTargetEditPartInfo dropInfo,
            int dndDropType) {
        DropTypeInfo dropType = DropTypeInfo.DROP_NONE;

        List<Object> dropObjects = getDropObjects();
        if (dropObjects.size() > 0) {

            dropType =
                    dropInfo.targetContainer.getModelAdapter()
                            .getDropTypeInfo(dropObjects,
                                    dropInfo.containerRelativeLocation,
                                    dropInfo.targetEP.getModel(),
                                    dndDropType);
        }

        return dropType;
    }

    /**
     * Reset the feedback stuff for latest target.
     * 
     * @param event
     * @param dropType
     * @param dropInfo
     */
    private void resetFeedback(DropTargetEvent event, DropTypeInfo dropType,
            DropTargetEditPartInfo dropInfo) {

        lastFeedbackRectangles = null;
        if (dropType.getDndDropType() != DND.DROP_NONE) {
            lastFeedbackRectangles = dropType.getFeedbackRectangles();
        }

        if (doFeedbackRects) {
            feedbackHelper.disposeFeedbackFigures();

            if (dropType.getDndDropType() != DND.DROP_NONE) {
                if (lastFeedbackRectangles != null
                        && lastFeedbackRectangles.size() > 0) {
                    feedbackHelper.setupFeedbackFigures(lastFeedbackRectangles);
                }
            }

        }
    }

    /**
     * Update the feedback for the latest drag location/target.
     * 
     * @param event
     * @param dropInfo
     */
    private void updateFeedback(DropTargetEvent event,
            DropTargetEditPartInfo dropInfo) {
        if (doFeedbackRects) {
            if (lastFeedbackRectangles != null
                    && lastFeedbackRectangles.size() > 0) {

                IFigure feebackLayer = processWidgetImpl.getFeedbackLayer();

                // Reset the location of the feedback figure.
                Point relLoc =
                        absWidgetToFigureRelative(dropInfo, feebackLayer);

                feedbackHelper.updateFeedbackFigures(relLoc);
            }
        }

        if (lastDragOverPos.x != event.x || lastDragOverPos.y != event.y) {
            if (doFeedbackRects && lastFeedbackRectangles != null
                    && lastFeedbackRectangles.size() > 0) {
                processWidgetImpl.getFeedbackLayer().repaint();
            }

            if (previousHighlightFigures != null) {
                for (IFigure fig : previousHighlightFigures) {
                    fig.repaint();
                }
            }
        }

        lastDragOverPos.x = event.x;
        lastDragOverPos.y = event.y;
        return;
    }

    /**
     * Set the highlight on diagram object figure on/off
     * 
     * @param targetFig
     * @param on
     */
    private void setHighlight(IFigure targetFig, boolean on) {
        if (targetFig instanceof IHighlightableFigure) {
            ((IHighlightableFigure) targetFig).setHighlight(on);
        } else if (targetFig instanceof PolylineConnection) {
            ((PolylineConnection) targetFig).setLineWidth(on ? 2 : 1);
        }

    }

    /**
     * Return the processwidget control absolute co-ords in dropInfo as coords
     * relative to the given container such as feedback layer or actual target
     * container)
     * 
     * @param dropInfo
     * @param container
     * @return
     */
    private Point absWidgetToFigureRelative(DropTargetEditPartInfo dropInfo,
            IFigure container) {
        Point relLoc = dropInfo.absWidgetLocation.getCopy();
        container.translateToRelative(relLoc);

        relLoc = adjustFeedbackLocation(relLoc, dropInfo.targetEP);
        return relLoc;
    }

    /**
     * Adjust the feedback location for the type of target (basically, ensure
     * that feedback rectangles appear above cursor for non-connection targets
     * and first-rectangle-centred on point for connection line targets.
     * 
     * @param relLoc
     * @param targetEP
     * @return
     */
    private Point adjustFeedbackLocation(Point relLoc,
            ModelAdapterEditPart targetEP) {
        if (doFeedbackRects) {
            if (lastFeedbackRectangles != null
                    && lastFeedbackRectangles.size() > 0) {
                if (targetEP instanceof BaseConnectionEditPart) {
                    //
                    // For connection targets, assume first rectangle is start
                    // of flow of object(s) to be inserted and centre the
                    // Location there.
                    Rectangle r = lastFeedbackRectangles.iterator().next();

                    relLoc.x -= (r.width / 2);
                    relLoc.y -= (r.height / 2);

                    /*
                     * SID Routing Improvements. If objects are odd rather than
                     * even size then there are always rounding differences
                     * between the alignment guide (which uses floating poitn to
                     * get centre and our get centre.
                     * 
                     * The following code compensates for that fact when
                     * creating a gateway or intermediate Onto a connection
                     * between two horizontally (or vertically) aligned events /
                     * gateways.
                     * 
                     * Without this fix the inserted gateway would always be
                     * located shifted one pixel down. so the line would be
                     * kinked.
                     */
                    if (!XpdFlowRoutingStyle.UncenteredOnTasks
                            .equals(XPDFigureUtilities
                                    .getXpdRouterStyle(((BaseConnectionEditPart) targetEP)
                                            .getFigure()))) {

                        if (r.width % 2 == 0) {
                            relLoc.x += 1;
                        }

                        if (r.height % 2 == 0) {
                            relLoc.y += 1;
                        }
                    }

                } else {
                    //
                    // For non-connection targets...
                    // Offset the location in order to put the feedback
                    // rectangles ABOVE the cursor - this avoids the nastiness
                    // caused by the way that the drag-cursor image copies and
                    // restores screen behind it self out of synch with the
                    // process widget update.
                    Rectangle union = null;
                    for (Rectangle r : lastFeedbackRectangles) {
                        if (union == null) {
                            union = r.getCopy();
                        } else {
                            union = union.union(r);
                        }
                    }

                    relLoc.y -= union.height;
                    relLoc.y -= 4;

                    return relLoc;
                }
            }
        }
        return relLoc;
    }

    /**
     * Objects have been dropped - produce popup or execute single command if
     * that's all there is.
     * 
     * @param dropInfo
     * @param dndDropType
     */
    private void doDropPopupMenu(DropTargetEditPartInfo dropInfo,
            int dndDropType) {
        List<EditPart> affectedEditParts = Collections.EMPTY_LIST;

        Collection<DropObjectPopupItem> popupItems =
                getDropPopupItems(dropInfo, getDropObjects(), dndDropType);

        if (popupItems != null) {
            // Make sure that the create command is offset by our adjusted
            // feedback location. (Unless its a connection in which case
            // XPDPasteCommand already does it).
            Point adjustedContainerRelativeLocation =
                    dropInfo.containerRelativeLocation.getCopy();
            if (!(dropInfo.targetEP instanceof BaseConnectionEditPart)) {
                adjustedContainerRelativeLocation =
                        adjustFeedbackLocation(adjustedContainerRelativeLocation,
                                dropInfo.targetEP);
            }

            if (popupItems.size() == 1
                    && !DropObjectPopupItem.DropPopupItemType.SUB_MENU
                            .equals(popupItems.iterator().next()
                                    .getPopupItemType())) {
                // If there is only a single executable entry then we should
                // just execute it.
                DropObjectPopupItem popupItem = popupItems.iterator().next();

                DropObjectPopupItemUtil.executeDropPopupItem(popupItem,
                        processWidgetImpl,
                        dropInfo.targetEP,
                        dropInfo.targetContainer,
                        adjustedContainerRelativeLocation);

            } else {
                //
                // More than one entry, create a popup menu.
                DropPopupMenu popupMenu = createPopupMenu(popupItems);

                if (popupMenu != null) {
                    //
                    // Temporarily add the feed back figures to the target
                    // container.
                    // (Note, the asynch drop action handler will clear it up
                    // for us).
                    IFigure feedback = null;
                    IFigure targetContFig = null;

                    if (lastFeedbackRectangles != null
                            && lastFeedbackRectangles.size() > 0) {
                        targetContFig =
                                dropInfo.targetContainer.getContentPane();

                        feedback =
                                DropDiagramObjectFeedbackHelper
                                        .createRawFeedbackFigures(lastFeedbackRectangles);
                        targetContFig.add(feedback);

                        Point targetContLoc =
                                absWidgetToFigureRelative(dropInfo,
                                        targetContFig);
                        feedback.setLocation(targetContLoc);

                        feedback.setVisible(true);
                    }

                    //
                    // We have to allow drag-n-drop chance to clean up from this
                    // drag drop because once we popup the menu there is nothing
                    // preventing user from starting another drag drop WITHOUT
                    // cancelling the first (which causes exceptions because
                    // underlying drag-n-drop gets confused with 2 drops running
                    // at the same time)
                    processWidgetImpl
                            .getControl()
                            .getDisplay()
                            .asyncExec(new DisplayDropPopup(processWidgetImpl,
                                    popupMenu, feedback,
                                    adjustedContainerRelativeLocation, dropInfo));

                }

            }
        }

        return;
    }

    /**
     * Get list of drop-popup menu items from adapter implementation for target
     * container.
     * 
     * @param dropInfo
     * @param dndDropType
     * @return
     */
    private List<DropObjectPopupItem> getDropPopupItems(
            DropTargetEditPartInfo dropInfo, List<Object> dropObjects,
            int dndDropType) {
        return dropInfo.targetContainer.getModelAdapter()
                .getDropPopupItems(processWidgetImpl.getEditingDomain(),
                        dropObjects,
                        dropInfo.containerRelativeLocation,
                        dropInfo.targetEP.getModel(),
                        dndDropType);
    }

    /**
     * Create a popup menu for DropObjectPopupItems
     * 
     * @param popupCommands
     * @return
     */
    private DropPopupMenu createPopupMenu(
            Collection<DropObjectPopupItem> popupItems) {
        //
        // Convert sub-menu entries to cascading menu entry required by popup
        // menu.
        List<Object> menuItems = convertToMenuItemns(popupItems);

        // Create the popup.
        DropPopupMenu popupMenu =
                new DropPopupMenu(
                        menuItems,
                        LocalSelectionTransferDropTargetListener.DROP_POPUP_LABEL_PROVIDER);

        return popupMenu;
    }

    /**
     * Convert our popup items into understood by popup menu.
     * 
     * @param popupItems
     * @return
     */
    private List<Object> convertToMenuItemns(
            Collection<DropObjectPopupItem> popupItems) {
        List<Object> menuItems = new ArrayList<Object>(popupItems.size());

        for (DropObjectPopupItem popupItem : popupItems) {
            DropPopupItemType popupItemType = popupItem.getPopupItemType();

            if (DropObjectPopupItem.DropPopupItemType.SUB_MENU
                    .equals(popupItemType)) {
                //
                // Recurs to get sub-items first.
                List<Object> subMenuItems =
                        convertToMenuItemns(popupItem.getSubMenuItems());

                // Convert our submenu into DropPopupMenu's cascading menu
                // items.
                DropPopupMenu subMenu =
                        new DropPopupMenu(
                                subMenuItems,
                                LocalSelectionTransferDropTargetListener.DROP_POPUP_LABEL_PROVIDER);

                menuItems.add(new DropPopupMenu.CascadingMenu(popupItem,
                        subMenu));

            } else if (DropObjectPopupItem.DropPopupItemType.SEPARATOR
                    .equals(popupItemType)) {
                menuItems.add(DropPopupMenu.DROP_POPUP_SEPARATOR);

            } else {
                menuItems.add(popupItem);
            }
        }
        return menuItems;
    }

}
