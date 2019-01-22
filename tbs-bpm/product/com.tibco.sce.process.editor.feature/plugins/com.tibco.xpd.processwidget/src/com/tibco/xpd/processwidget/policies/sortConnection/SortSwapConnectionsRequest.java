/**
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.processwidget.policies.sortConnection;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.Request;

public class SortSwapConnectionsRequest extends Request {
    private ConnectionEditPart hostConnection = null;

    private ConnectionEditPart targetConnection = null;

    private Point location = new Point(0,0);
    
    /**
     */
    public SortSwapConnectionsRequest() {
    }

    /**
     * @return the hostConnection
     */
    public ConnectionEditPart getHostConnection() {
        return hostConnection;
    }

    /**
     * @param hostConnection the hostConnection to set
     */
    public void setHostConnection(ConnectionEditPart hostConnection) {
        this.hostConnection = hostConnection;
    }


    /**
     * @param targetConnection
     *            the targetConnection to set
     */
    public void setTargetConnection(ConnectionEditPart targetConnection) {
        this.targetConnection = targetConnection;
    }

    /**
     * @return the targetConnection
     */
    public ConnectionEditPart getTargetConnection() {
        return targetConnection;
    }
    
    /**
     * @return the location
     */
    public Point getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(Point location) {
        this.location = location;
    }


}