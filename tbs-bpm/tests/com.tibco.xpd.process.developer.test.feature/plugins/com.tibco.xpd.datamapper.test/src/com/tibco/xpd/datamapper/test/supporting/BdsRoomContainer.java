/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.test.supporting;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

/**
 * 
 * 
 * @author aallway
 * @since 22 May 2017
 */
public class BdsRoomContainer {
    private EList<BdsRoom> nestedRooms;

    /**
     * @return the rooms
     */
    public EList<BdsRoom> getNestedRooms() {
        if (nestedRooms == null) {
            nestedRooms = new BasicEList<BdsRoom>();
        }
        return nestedRooms;
    }


}
