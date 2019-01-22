/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.test.supporting;


/**
 * 
 * 
 * @author nwilson
 * @since 2 Jun 2015
 */
public class JsonProperty {

    private String house;

    private String postcode;

    private BdsRoom[] rooms;
    
    private JsonRoomContainer arrayNestedUnderComplexChild;

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
    public BdsRoom[] getRooms() {
        if (rooms == null) {
            rooms = new BdsRoom[0];
        }
        return rooms;
    }

    /**
     * @param rooms
     *            the rooms to set
     */
    public void setRooms(BdsRoom[] rooms) {
        this.rooms = rooms;
    }

    /**
     * @return the arrayNestedUnderComplexChild
     */
    public JsonRoomContainer getArrayNestedUnderComplexChild() {
        return arrayNestedUnderComplexChild;
    }

    /**
     * @param arrayNestedUnderComplexChild the arrayNestedUnderComplexChild to set
     */
    public void setArrayNestedUnderComplexChild(
            JsonRoomContainer arrayNestedUnderComplexChild) {
        this.arrayNestedUnderComplexChild = arrayNestedUnderComplexChild;
    }

    
}
