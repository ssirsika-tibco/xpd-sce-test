/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.test.supporting;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

/**
 * Customer test pojo.
 * 
 * @author nwilson
 * @since 28 May 2015
 */
public class BdsProspect {
    private Integer id;

    private String fullname;

    private XMLGregorianCalendar dob;

    private XMLGregorianCalendar lastContact;

    private XMLGregorianCalendar time;

    private Boolean vip;

    private Double balance;

    private String secret;

    private EList<BdsProperty> properties;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * @param fullname
     *            the name to set
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     * @return the vip
     */
    public Boolean getVip() {
        return vip;
    }

    /**
     * @param vip
     *            the vip to set
     */
    public void setVip(Boolean vip) {
        this.vip = vip;
    }

    /**
     * @return the balance
     */
    public Double getBalance() {
        return balance;
    }

    /**
     * @param balance
     *            the balance to set
     */
    public void setBalance(Double balance) {
        this.balance = balance;
    }

    /**
     * @return the dob
     */
    public XMLGregorianCalendar getDob() {
        return dob;
    }

    /**
     * @param dob
     *            the dob to set
     */
    public void setDob(XMLGregorianCalendar dob) {
        this.dob = dob;
    }

    /**
     * @return the lastContact
     */
    public XMLGregorianCalendar getLastContact() {
        return lastContact;
    }

    /**
     * @param lastContact
     *            the lastContact to set
     */
    public void setLastContact(XMLGregorianCalendar lastContact) {
        this.lastContact = lastContact;
    }

    /**
     * @return the time
     */
    public XMLGregorianCalendar getTime() {
        return time;
    }

    /**
     * @param time
     *            the time to set
     */
    public void setTime(XMLGregorianCalendar time) {
        this.time = time;
    }

    /**
     * @return the properties
     */
    public EList<BdsProperty> getProperties() {
        if (properties == null) {
            properties = new BasicEList<BdsProperty>();
        }
        return properties;
    }

    /**
     * @return the secret
     */
    public String getSecret() {
        return secret;
    }

    /**
     * @param secret
     *            the secret to set
     */
    public void setSecret(String secret) {
        this.secret = secret;
    }

}
