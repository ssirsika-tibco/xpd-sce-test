/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.test.supporting;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

/**
 * 
 * 
 * @author nwilson
 * @since 2 Jun 2015
 */
public class BdsProperty {

    private String house;

    private String postcode;

    private EList<BdsRoom> rooms;

    private BdsRoom entrance;
    
    private BdsRoomContainer arrayNestedUnderComplexChild;

    /**
     * @return the arrayNestedUnderComplexChild
     */
    public BdsRoomContainer getArrayNestedUnderComplexChild() {
        return arrayNestedUnderComplexChild;
    }

    /**
     * @param arrayNestedUnderComplexChild the arrayNestedUnderComplexChild to set
     */
    public void setArrayNestedUnderComplexChild(
            BdsRoomContainer arrayNestedUnderComplexChild) {
        this.arrayNestedUnderComplexChild = arrayNestedUnderComplexChild;
    }

    /**
     * @return the house
     */
    public String getHouse() {
        return house;
    }

    /**
     * @param house
     *            the house to set
     */
    public void setHouse(String house) {
        this.house = house;
    }

    /**
     * @return the postcode
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * @param postcode
     *            the postcode to set
     */
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    /**
     * @return the rooms
     */
    public EList<BdsRoom> getRooms() {
        if (rooms == null) {
            rooms = new BasicEList<BdsRoom>();
        }
        return rooms;
    }

    /**
     * @return the entrance
     */
    public BdsRoom getEntrance() {
        return entrance;
    }

    /**
     * @param entrance
     *            the entrance to set
     */
    public void setEntrance(BdsRoom entrance) {
        this.entrance = entrance;
    }

}
