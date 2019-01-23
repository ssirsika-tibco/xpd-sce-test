/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.test.supporting;

/**
 * House test class.
 * 
 * @author nwilson
 * @since 15 Jun 2015
 */
public class BdsHouse {
    private String name;

    private String postcode;

    private BdsRoom hall;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
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
     * @return the hall
     */
    public BdsRoom getHall() {
        return hall;
    }

    /**
     * @param hall
     *            the hall to set
     */
    public void setHall(BdsRoom hall) {
        this.hall = hall;
    }

}
