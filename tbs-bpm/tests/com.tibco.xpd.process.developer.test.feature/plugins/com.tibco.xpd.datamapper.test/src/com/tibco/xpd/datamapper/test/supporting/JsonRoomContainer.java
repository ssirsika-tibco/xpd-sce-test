/*
 * Copyright (c) TIBCO Software Inc 2004, 2017. All rights reserved.
 */

package com.tibco.xpd.datamapper.test.supporting;

/**
 *
 *
 * @author aallway
 * @since 22 May 2017
 */
public class JsonRoomContainer {
    private BdsRoom[] nestedRooms;
    /**
     * @return the rooms
     */
    public BdsRoom[] getRooms() {
        if (nestedRooms == null) {
            nestedRooms = new BdsRoom[0];
        }
        return nestedRooms;
    }

    /**
     * @param rooms
     *            the rooms to set
     */
    public void setRooms(BdsRoom[] rooms) {
        this.nestedRooms = rooms;
    }
}
