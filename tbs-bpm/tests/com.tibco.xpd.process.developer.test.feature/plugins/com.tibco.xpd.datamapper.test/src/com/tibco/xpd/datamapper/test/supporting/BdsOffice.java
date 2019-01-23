/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.test.supporting;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

/**
 * BDS Office test object.
 * 
 * @author nwilson
 * @since 11 Jun 2015
 */
public class BdsOffice {

    private String name;

    private String postcode;

    private BdsCubicle entrance;

    private BdsRoom kitchen;

    private EList<BdsCubicle> cubicles;

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
     * @return the entrance
     */
    public BdsCubicle getEntrance() {
        return entrance;
    }

    /**
     * @param entrance
     *            the entrance to set
     */
    public void setEntrance(BdsCubicle entrance) {
        this.entrance = entrance;
    }

    /**
     * @return the kitchen
     */
    public BdsRoom getKitchen() {
        return kitchen;
    }

    /**
     * @param kitchen
     *            the kitchen to set
     */
    public void setKitchen(BdsRoom kitchen) {
        this.kitchen = kitchen;
    }

    /**
     * @return the cubicles
     */
    public EList<BdsCubicle> getCubicles() {
        if (cubicles == null) {
            cubicles = new BasicEList<BdsCubicle>();
        }
        return cubicles;
    }

}
