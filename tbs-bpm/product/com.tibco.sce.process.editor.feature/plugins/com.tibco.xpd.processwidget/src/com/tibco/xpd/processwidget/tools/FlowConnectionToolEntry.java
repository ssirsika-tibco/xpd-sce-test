/*
 ** 
 **  MODULE:             $RCSfile: $ 
 **                      $Revision: $ 
 **                      $Date: $ 
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc., All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */

package com.tibco.xpd.processwidget.tools;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.gef.tools.ConnectionCreationTool;
import org.eclipse.gef.tools.CreationTool;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;

import com.tibco.xpd.processwidget.ProcessWidget;
import com.tibco.xpd.processwidget.adapters.AssociationAdapter;
import com.tibco.xpd.processwidget.adapters.MessageFlowAdapter;
import com.tibco.xpd.processwidget.adapters.SequenceFlowAdapter;
import com.tibco.xpd.processwidget.parts.EditPartUtil;
import com.tibco.xpd.processwidget.policies.ConnectableGraphicalNodeObjectEditPolicy;
import com.tibco.xpd.processwidget.viewer.EMFCommandWrapper;

/**
 * @author wzurek
 */
public class FlowConnectionToolEntry extends ConnectionCreationToolEntry {

    public static final String REQ_EXT_DATA_INITIAL_LOCATION =
            "Initial Start Connection Location"; //$NON-NLS-1$

    private ToolContextHelpUpdater toolContextHelpUpdater;

    private String contextHelpId;

    private Map requestExtendedData; // Extra data that the palette factory
                                     // can add.

    private ProcessWidget processWidgetImpl;

    /**
     * The constructor
     * 
     * @param label
     * @param shortDesc
     * @param factory
     * @param iconSmall
     * @param iconLarge
     * @param contextHelpId
     * @param toolContextHelpUpdater
     */
    public FlowConnectionToolEntry(String label, String shortDesc,
            CreationFactory factory, ImageDescriptor iconSmall,
            ImageDescriptor iconLarge, String contextHelpId,
            ToolContextHelpUpdater toolContextHelpUpdater,
            ProcessWidget processWidgetImpl) {

        super(label, shortDesc, factory, iconSmall, iconLarge);

        this.toolContextHelpUpdater = toolContextHelpUpdater;
        this.contextHelpId = contextHelpId;
        this.processWidgetImpl = processWidgetImpl;
    }

    @Override
    public org.eclipse.gef.Tool createTool() {
        return new ConnectionCreationTool(
                (CreationFactory) getToolProperty(CreationTool.PROPERTY_CREATION_FACTORY)) {

            // Add to the state machine to...
            // Keep track of whether we're moved away from original source
            // object at least once...
            protected static final int STATE_LEFT_SOURCE =
                    ConnectionCreationTool.MAX_STATE << 1;

            protected static final int MAX_STATE = STATE_LEFT_SOURCE;

            // Keep the current menu manager so that we can deactivate it when
            // the tool is selected...
            private MenuManager saveContextMenu = null;

            private MenuManager dummyMenu = null;

            @Override
            protected void executeCommand(Command command) {
                super.executeCommand(command);

                // After executing a command select the created edit part.
                Collection<Object> modelObjects =
                        getModelObjectFromCommand(command);
                if (modelObjects != null && modelObjects.size() > 0) {
                    List<EditPart> editParts =
                            EditPartUtil
                                    .getEditPartsForModelObjects(processWidgetImpl
                                            .getGraphicalViewer(),
                                            modelObjects,
                                            false);

                    if (editParts != null && editParts.size() > 0) {
                        processWidgetImpl
                                .getGraphicalViewer()
                                .setSelection(new StructuredSelection(editParts));
                    }
                }

                return;
            }

            private Collection getModelObjectFromCommand(Command command) {
                if (command instanceof EMFCommandWrapper) {
                    EMFCommandWrapper cmd = (EMFCommandWrapper) command;

                    return cmd.getEmfCommand().getAffectedObjects();
                }
                return null;
            }

            private void reenableContextMenu() {
                if (saveContextMenu != null) {
                    EditPartViewer viewer = getCurrentViewer();
                    if (viewer != null) {
                        viewer.setContextMenu(saveContextMenu);
                    }
                    saveContextMenu = null;
                }

                dummyMenu = null;
            }

            private void disableContextMenu() {
                // When user left-clicks to start connection we come thru here
                // and
                // disable right click context menu by setting an empty menu
                // manager.
                // If user lets go of mouse in invalid position then don't
                // always get
                // the mouse button up / deactivate.
                // So make sure we only replace current context menu with dummy
                // one if we have done so already.
                // Otherwise we end up replacing one dummy menu with another on
                // a proper deactivate and that then
                // is the permanent one!
                if (saveContextMenu == null) {
                    dummyMenu = new MenuManager();

                    EditPartViewer viewer = getCurrentViewer();
                    if (viewer != null) {
                        saveContextMenu = viewer.getContextMenu();

                        viewer.setContextMenu(dummyMenu);
                    }
                }
            }

            @Override
            protected Request createTargetRequest() {
                CreateRequest req = (CreateRequest) super.createTargetRequest();
                if (requestExtendedData != null) {
                    req.getExtendedData().putAll(requestExtendedData);
                }

                return req;
            }

            /*
             * (non-Javadoc)
             * 
             * @see org.eclipse.gef.tools.AbstractConnectionCreationTool#
             * updateTargetRequest()
             */
            @Override
            protected void updateTargetRequest() {
                // TODO Auto-generated method stub
                super.updateTargetRequest();

                if (requestExtendedData != null) {
                    getTargetRequest().getExtendedData()
                            .putAll(requestExtendedData);
                }

            }

            @Override
            public void activate() {
                super.activate();
                toolContextHelpUpdater.update(contextHelpId);
            }

            @Override
            public void deactivate() {
                reenableContextMenu();

                super.deactivate();
            }

            @Override
            protected boolean handleButtonDown(int button) {
                if (button == 1) {
                    if (isInState(STATE_INITIAL)) {
                        disableContextMenu();
                    }
                    getTargetRequest().getExtendedData()
                            .put(REQ_EXT_DATA_INITIAL_LOCATION,
                                    getLocation().getCopy());

                } else if (button == 3 && isInState(STATE_CONNECTION_STARTED)) {
                    // Ignore right button

                    return (true);
                }

                return (super.handleButtonDown(button));
            }

            // Drag - mouse button up without re-click.
            @Override
            protected boolean handleButtonUp(int button) {
                if (button == 1) {
                    if (isInState(STATE_LEFT_SOURCE)) {
                        if (stateTransition(STATE_CONNECTION_STARTED,
                                STATE_TERMINAL)) {
                            // If we haven't moved away at least once from
                            // source object then don't create connection...
                            handleCreateConnection();
                            handleFinished();
                            reenableContextMenu();
                            return (true);
                        }
                    }
                } else {
                    // On right button add a bendpoint
                    if (button == 3 && isInState(STATE_CONNECTION_STARTED)) {
                        addBendPoint();

                        return (true);
                    }
                }

                return super.handleButtonUp(button);
            }

            /**
             * Add a new bend point to the feedback figure for connection being
             * created.
             * 
             */
            private void addBendPoint() {
                CreateConnectionRequest req =
                        (CreateConnectionRequest) getSourceRequest();

                // Only add for flow connections, not diagram note associations.
                Object type = req.getNewObjectType();
                if (type == SequenceFlowAdapter.class
                        || type == AssociationAdapter.class
                        || type == MessageFlowAdapter.class) {

                    Map extData = req.getExtendedData();

                    if (extData != null) {
                        Point loc = getLocation().getCopy();

                        extData.put(ConnectableGraphicalNodeObjectEditPolicy.NEW_BENDPOINT_KEY,
                                loc);

                        // This makes sure that the edit policy gets the new
                        // bendPoint before
                        // the request is regenerated.
                        showSourceFeedback();
                    }
                }
            }

            /**
             * Delete last bend point created during connection creation
             * 
             * @return true if deleted false if no bendpoints.
             */
            private boolean delLastBendPoint() {
                boolean ret = false;

                CreateConnectionRequest req =
                        (CreateConnectionRequest) getSourceRequest();
                if (req != null) {
                    Map extData = req.getExtendedData();

                    if (extData != null) {
                        List bendPoints =
                                (List) extData
                                        .get(ConnectableGraphicalNodeObjectEditPolicy.NEW_BENDPOINT_OFFSETS_KEY);

                        if (bendPoints != null && bendPoints.size() > 0) {
                            extData.put(ConnectableGraphicalNodeObjectEditPolicy.DEL_LAST_BENDPOINT_KEY,
                                    new Boolean(true));

                            // This makes sure that the edit policy gets the
                            // signal before
                            // the request is regenerated.
                            showSourceFeedback();

                            ret = true;
                        }
                    }
                }
                return (ret);
            }

            // Only allow target of connection to be set once we have moved away
            // from source.
            @Override
            protected void setTargetEditPart(EditPart editpart) {
                if (isInState(STATE_CONNECTION_STARTED)) {
                    if (!isInState(STATE_LEFT_SOURCE)) {
                        // If we haven't moved away from source object yet,
                        // check whether we have now.
                        CreateConnectionRequest req =
                                (CreateConnectionRequest) getTargetRequest();

                        GraphicalEditPart srcPart =
                                (GraphicalEditPart) req.getSourceEditPart();

                        Point loc = req.getLocation().getCopy();

                        srcPart.getFigure().translateToRelative(loc);

                        if (!srcPart.getFigure().containsPoint(loc)) {
                            setState(getState() | STATE_LEFT_SOURCE);
                        }
                    }

                    // If we've previously moved away from object then allow the
                    // set otherwise unset it.
                    if (isInState(STATE_LEFT_SOURCE)) {
                        super.setTargetEditPart(editpart);
                    } else {
                        super.setTargetEditPart(null);
                    }
                } else {
                    super.setTargetEditPart(editpart);
                }

            }

            @Override
            protected void handleFinished() {
                if (getCurrentInput().isControlKeyDown())
                    reactivate();
                else
                    getDomain().loadDefaultTool();
            }

            @Override
            protected boolean handleKeyDown(KeyEvent e) {
                if (e.keyCode == SWT.ESC) {
                    // If we were in the middle of creating a connection then
                    // don't select the
                    // default tool (otherwise it may get the mouse button up
                    // event and doesn't know
                    // what to do with it)
                    if (isInState(STATE_INITIAL)) {
                        getDomain().loadDefaultTool();
                    } else {
                        // In the middle of conneciton create
                        // First priority is to delete last bendpoint added by
                        // user if there is one
                        if (!delLastBendPoint()) {
                            // No bend point to delete, re-activating the tool
                            // will cancel.
                            reactivate();
                        }
                    }
                    return true;
                }
                // On space, create a new bend point if currently creating a
                // connection.
                else if (e.character == ' ') {
                    if (isInState(STATE_CONNECTION_STARTED)) {
                        addBendPoint();

                        return (true);
                    }
                }
                return super.handleKeyDown(e);
            }

            @Override
            protected void showSourceFeedback() {
                // Don't show feed back until we have left source object.
                if (isInState(STATE_LEFT_SOURCE)) {
                    super.showSourceFeedback();
                }
            }

        };
    }

    public void setRequestExtendedData(Object key, Object value) {
        if (requestExtendedData == null) {
            requestExtendedData = new HashMap();
        }
        requestExtendedData.put(key, value);
    }

    /**
     * @return the requestExtendedData
     */
    public Map getRequestExtendedData() {
        return requestExtendedData;
    }
}
