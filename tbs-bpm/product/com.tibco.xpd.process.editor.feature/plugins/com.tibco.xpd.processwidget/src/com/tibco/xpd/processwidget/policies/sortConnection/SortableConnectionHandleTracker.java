/**
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.processwidget.policies.sortConnection;

import java.util.List;

import org.eclipse.draw2d.Cursors;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.editparts.LayerManager;
import org.eclipse.gef.tools.SimpleDragTracker;

import com.tibco.xpd.processwidget.policies.sortConnection.SortableConnectionHandle.ISortableConnectionSetProvider;

public class SortableConnectionHandleTracker extends SimpleDragTracker {

    private static final String REQ_SORT_SWAP_CONNECTIONS =
            "REQ_SORT_SWAP_CONNECTIONS"; //$NON-NLS-1$

    private ConnectionEditPart myHostConnection;

    private ISortableConnectionSetProvider connectionSetProvider;

    public SortableConnectionHandleTracker(ConnectionEditPart hostConnection,
            ISortableConnectionSetProvider connectionSetProvider) {
        this.myHostConnection = hostConnection;
        this.connectionSetProvider = connectionSetProvider;

        this.setCursor(Cursors.HAND);
        this.setDefaultCursor(Cursors.HAND);
        this.setDisabledCursor(Cursors.HAND);
    }

    @Override
    protected String getCommandName() {
        return REQ_SORT_SWAP_CONNECTIONS;
    }

    @Override
    protected Request createSourceRequest() {
        return new SortSwapConnectionsRequest();
    }

    @Override
    protected Command getCommand() {
        SortSwapConnectionsRequest req =
                (SortSwapConnectionsRequest) getSourceRequest();

        ConnectionEditPart targetConn = req.getTargetConnection();

        if (targetConn != null) {
            return myHostConnection.getCommand(req);
        }

        return UnexecutableCommand.INSTANCE;
    }

    @Override
    protected void updateSourceRequest() {
        EditPartViewer viewer = getCurrentViewer();
        EditPart target = viewer.findObjectAt(getLocation());

        ConnectionEditPart connTarget = null;
        if (target instanceof ConnectionEditPart) {
            connTarget = (ConnectionEditPart) target;

        } else {
            // If the cursor is over one of the other sort index marker
            // handles then we need to work it out for ourselves.
            IFigure layer =
                    LayerManager.Helper.find(myHostConnection)
                            .getLayer(LayerConstants.HANDLE_LAYER);

            Point loc = getLocation().getCopy();
            layer.translateToRelative(loc);

            IFigure fig = layer.findFigureAt(loc);
            if (fig instanceof SortableConnectionHandle) {
                connTarget =
                        ((SortableConnectionHandle) fig).getHostConnection();
            }
        }

        SortSwapConnectionsRequest srcRequest =
                (SortSwapConnectionsRequest) getSourceRequest();
        
        srcRequest.setLocation(getLocation().getCopy());
        
        srcRequest.setHostConnection(myHostConnection);

        if (connTarget != myHostConnection) {
            List<ConnectionEditPart> connectionSet =
                    connectionSetProvider.getSortedConnections();

            if (connectionSet != null && connectionSet.contains(connTarget)) {
                srcRequest.setTargetConnection(connTarget);
                return;
            }
        }

        srcRequest.setTargetConnection(null);

    }

    @Override
    protected void executeCurrentCommand() {
        super.executeCurrentCommand();

        // Force repaint on handle layer (we've just swapped some over).
        IFigure layer =
                LayerManager.Helper.find(myHostConnection)
                        .getLayer(LayerConstants.HANDLE_LAYER);
        layer.repaint();

    }

}