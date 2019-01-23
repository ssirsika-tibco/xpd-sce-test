/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.test.supporting;

/**
 * BDS factory test helper class.
 * 
 * @author nwilson
 * @since 2 Jun 2015
 */
public class BdsFactory {
    public BdsCustomer createContact() {
        return new BdsCustomer();
    }

    public BdsProspect createProspect() {
        return new BdsProspect();
    }

    public BdsRoom createRoom() {
        return new BdsRoom();
    }

    public BdsProperty createProperty() {
        return new BdsProperty();
    }

    public BdsCubicle createCubicle() {
        return new BdsCubicle();
    }

    public Phone createPhone() {
        return new Phone();
    }

    public ContactDetails createContactDetails() {
        return new ContactDetails();
    }

    public Visitor createVisitor() {
        return new Visitor();
    }

    public BdsOffice createOffice() {
        return new BdsOffice();
    }
    
    public BdsRoomContainer createRoomContainer() {
        return new BdsRoomContainer();
    }
    
}
