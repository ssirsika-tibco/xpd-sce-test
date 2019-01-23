/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processwidget.policies.connectgadget;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.commands.Command;

import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.adapters.CreateAndConnectObjectPair;
import com.tibco.xpd.processwidget.adapters.CreateAndConnectObjectPair.CreateAndConnectObjectType;
import com.tibco.xpd.processwidget.adapters.DropObjectPopupItem;
import com.tibco.xpd.processwidget.adapters.ProcessContainerAdapter;
import com.tibco.xpd.processwidget.dragdrop.DropObjectPopupItemUtil;
import com.tibco.xpd.processwidget.dragdrop.DropPopupItemLabelProvider;
import com.tibco.xpd.processwidget.dragdrop.DropPopupMenu;
import com.tibco.xpd.processwidget.impl.ProcessWidgetImpl;
import com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart;
import com.tibco.xpd.processwidget.parts.LaneEditPart;
import com.tibco.xpd.processwidget.parts.ModelAdapterEditPart;
import com.tibco.xpd.processwidget.parts.TaskEditPart;
import com.tibco.xpd.processwidget.policies.clickOrDragGadgetPolicy.ClickOrDragGadgetRequest;

/**
 * CreateAndConnectObjectPopupCommand
 * <p>
 * Command to create a new object and connect to it after first giving the user
 * the choice of object type via a popup menu.
 * 
 * @author aallway
 * @since 3.3 (19 Aug 2009)
 */
public class CreateAndConnectObjectPopupCommand extends Command {

    private BaseGraphicalEditPart source;

    private ModelAdapterEditPart target;

    private Collection<CreateAndConnectObjectPair> createAndConnectTypes;

    private ProcessWidgetImpl processWidget;

    private BaseGraphicalEditPart targetContainer;

    private Map<DropObjectPopupItem, Point> adjustedPopupItemRelativeLocation =
            Collections.emptyMap();

    private CreateAndConnectFeedback createAndConnectFeedback;

    private ClickOrDragGadgetRequest clickOrDragGadgetRequest;

    /**
     * Command to create a new object and connect to it after first giving the
     * user the choice of object type via a popup menu.
     * 
     * @param source
     *            Source of connection
     * @param target
     *            Target of connection (container or connection)
     * @param createAndConnectTypes
     * @param creq
     * 
     */
    public CreateAndConnectObjectPopupCommand(BaseGraphicalEditPart source,
            ModelAdapterEditPart target,
            Collection<CreateAndConnectObjectPair> createAndConnectTypes,
            ClickOrDragGadgetRequest creq) {
        super();
        this.source = source;
        this.target = target;
        this.createAndConnectTypes = createAndConnectTypes;
        this.clickOrDragGadgetRequest = creq;

        processWidget =
                (ProcessWidgetImpl) source.getViewer()
                        .getProperty(ProcessWidgetConstants.PROP_WIDGET);
        targetContainer =
                CreateAndConnectGadgetHelper.INSTANCE
                        .getTargetContainer(target, creq.getLocation());

    }

    @Override
    public void execute() {
        final DropPopupMenu dropPopupMenu = createDropPopupMenu();

        if (dropPopupMenu != null) {

            /*
             * We have to allow drag-n-drop chance to clean up from this drag
             * drop because once we popup the menu there is nothing preventing
             * user from starting another drag drop WITHOUT cancelling the first
             * (which causes exceptions because underlying drag-n-drop gets
             * confused with 2 drops running at the same time).
             */

            /*
             * Must add feed back NOW before doing asynch exec so that IF we
             * happened to auto-expose scroll off edge of diagram then we need
             * to add feed back now to stop the diagram scrolling back (as it
             * will have been the during drag feedback that was all that was
             * holding the diagram out t that extent).
             */
            addFeedback();

            processWidget.getControl().getDisplay().asyncExec(new Runnable() {
                @Override
                public void run() {
                    try {
                        doDropPopupMenu(dropPopupMenu);
                    } finally {
                        removeFeedback();
                    }
                }

            });
        }
    }

    /**
     * add gadget dropped background feedback to diagram.
     */
    protected void addFeedback() {
        IFigure feedbackLayer = processWidget.getFeedbackLayer();

        if (createAndConnectFeedback != null) {
            removeFeedback();
        }

        ConnectionAnchor srcAnchor = null;

        EditPart hostEditPart = clickOrDragGadgetRequest.getHostEditPart();

        if (hostEditPart instanceof NodeEditPart) {
            srcAnchor =
                    ((NodeEditPart) hostEditPart)
                            .getSourceConnectionAnchor(clickOrDragGadgetRequest);
            if (srcAnchor == null) {
                srcAnchor =
                        new ChopboxAnchor(
                                ((NodeEditPart) hostEditPart).getFigure());
            }
        }

        createAndConnectFeedback =
                new CreateAndConnectFeedback(feedbackLayer,
                        clickOrDragGadgetRequest, srcAnchor);

        createAndConnectFeedback.showFeedback(clickOrDragGadgetRequest);

        return;
    }

    /**
     * Remove gadget dropped background feedback from diagram.
     */
    protected void removeFeedback() {
        if (createAndConnectFeedback != null) {
            createAndConnectFeedback.eraseFeedback();
            createAndConnectFeedback = null;
        }

        return;
    }

    /**
     * @param dropPopupMenu
     * @param processWidget
     */
    private void doDropPopupMenu(final DropPopupMenu dropPopupMenu) {
        if (dropPopupMenu.show(processWidget.getControl())) {
            // User didn't cancel, get the selected command and
            // execute it.
            Object res = dropPopupMenu.getResult();

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
                Point adjustableRelativeLocation =
                        adjustedPopupItemRelativeLocation.get(popupItem);
                DropObjectPopupItemUtil.executeDropPopupItem(popupItem,
                        processWidget,
                        target,
                        targetContainer,
                        adjustableRelativeLocation);
            }
        }
    }

    /**
     * Create the drop popup menu items for the createAndConnectTypes.
     * 
     * @return drop popup menu.
     */
    private DropPopupMenu createDropPopupMenu() {
        /*
         * Convert the CreateAndConnectObjectPair's to popup menu items.
         */
        List<Object> menuItems = new ArrayList<Object>();

        adjustedPopupItemRelativeLocation =
                new HashMap<DropObjectPopupItem, Point>();

        for (CreateAndConnectObjectPair cacPair : createAndConnectTypes) {
            if (CreateAndConnectObjectType.SEPARATOR.equals(cacPair
                    .getObjectType())) {
                /* Insert separator item. */
                menuItems.add(DropPopupMenu.DROP_POPUP_SEPARATOR);

            } else {
                /*
                 * Do a final adjustment to snap position given the size of the
                 * object (this needs to be done because width/height being even
                 * or odd affects real snap location).
                 */
                Point adjustableRelativeLocation =
                        getFinalDropLocation(cacPair);

                /*
                 * Ask for the target container's process model adapter adpater
                 * for a popup menu item that will create and connect to new
                 * object.
                 */
                DropObjectPopupItem popupItem =
                        getDropPopupItem(cacPair, adjustableRelativeLocation);

                if (popupItem != null) {
                    menuItems.add(popupItem);

                    adjustedPopupItemRelativeLocation.put(popupItem,
                            adjustableRelativeLocation);
                }
            }
        }

        if (menuItems.size() > 0) {
            DropPopupMenu dropPopupMenu =
                    new DropPopupMenu(menuItems,
                            new DropPopupItemLabelProvider());

            return dropPopupMenu;
        }

        return null;
    }

    /**
     * @param cacPair
     * @return
     */
    private Point getFinalDropLocation(CreateAndConnectObjectPair cacPair) {
        Point finalAbsDropLocation =
                CreateAndConnectGadgetHelper
                        .getSnapLocation(clickOrDragGadgetRequest,
                                getCreateObjectSize(cacPair));

        Point relDropLocation = finalAbsDropLocation.getCopy();

        IFigure contentPane = targetContainer.getContentPane();
        contentPane.translateToRelative(relDropLocation);

        if (contentPane.getLayoutManager() instanceof XYLayout) {
            XYLayout lay = (XYLayout) contentPane.getLayoutManager();
            relDropLocation.translate(lay.getOrigin(contentPane).getCopy()
                    .negate());
        }

        Point adjustableRelativeLocation = relDropLocation.getCopy();
        return adjustableRelativeLocation;
    }

    /**
     * @param cacPair
     * @return
     */
    private Dimension getCreateObjectSize(CreateAndConnectObjectPair cacPair) {
        if (CreateAndConnectObjectType.TASK.equals(cacPair.getObjectType())) {
            return new Dimension(ProcessWidgetConstants.TASK_WIDTH_SIZE,
                    ProcessWidgetConstants.TASK_HEIGHT_SIZE);

        } else if (CreateAndConnectObjectType.GATEWAY.equals(cacPair
                .getObjectType())) {
            /*
             * SID Routing Improvements - was using the GROUP_HEIGHT_SIZE (which
             * because it is even not odd like gateway height ended up causing a
             * single pixel down-kink when creating a gateway next to an
             * intermediate event)
             */
            return new Dimension(ProcessWidgetConstants.GATEWAY_WIDTH_SIZE,
                    ProcessWidgetConstants.GATEWAY_HEIGHT_SIZE);

        } else if (CreateAndConnectObjectType.START_EVENT.equals(cacPair
                .getObjectType())) {
            return new Dimension(ProcessWidgetConstants.START_EVENT_SIZE,
                    ProcessWidgetConstants.START_EVENT_SIZE);

        } else if (CreateAndConnectObjectType.INTERMEDIATE_EVENT.equals(cacPair
                .getObjectType())) {
            return new Dimension(
                    ProcessWidgetConstants.INTERMEDIATE_EVENT_SIZE,
                    ProcessWidgetConstants.INTERMEDIATE_EVENT_SIZE);

        } else if (CreateAndConnectObjectType.END_EVENT.equals(cacPair
                .getObjectType())) {
            return new Dimension(ProcessWidgetConstants.END_EVENT_SIZE,
                    ProcessWidgetConstants.END_EVENT_SIZE);

        } else if (CreateAndConnectObjectType.DATAOBJECT.equals(cacPair
                .getObjectType())) {
            return new Dimension(ProcessWidgetConstants.DATAOBJECT_WIDTH_SIZE,
                    ProcessWidgetConstants.DATAOBJECT_HEIGHT_SIZE);

        } else if (CreateAndConnectObjectType.ANNOTATION.equals(cacPair
                .getObjectType())) {
            return new Dimension(2, ProcessWidgetConstants.NOTE_HEIGHT);

        }

        return new Dimension(2, 2);
    }

    /**
     * @param cacPair
     * @param target
     * @param targetContainer
     * @param adjustableRelativeLocation
     *            passed as initial drop location relative to target container,
     *            can be used to adjust the position to executre the drop (for
     *            centering object etc).
     * 
     * @return The drop popup menu item for the given create object and
     *         connection pair (from model adpaters).
     */
    private DropObjectPopupItem getDropPopupItem(
            CreateAndConnectObjectPair cacPair, Point adjustableRelativeLocation) {

        if (targetContainer instanceof LaneEditPart
                || targetContainer instanceof TaskEditPart) {
            ProcessContainerAdapter adapter =
                    (ProcessContainerAdapter) targetContainer.getModelAdapter();

            return adapter.getCreateAndConnectPopupItem(processWidget
                    .getEditingDomain(),
                    cacPair,
                    adjustableRelativeLocation,
                    target.getModel(),
                    source.getModel());

        }

        return null;
    }

}
