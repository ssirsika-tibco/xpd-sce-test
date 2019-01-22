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
public class BdsCustomer {
    private Integer id;

    private String name;

    private XMLGregorianCalendar dob;

    private XMLGregorianCalendar lastContact;

    private XMLGregorianCalendar time;

    private Boolean vip;

    private Double balance;

    private EList<BdsProperty> properties;

    private EList<String> tags;

    private EList<String> labels;

    private EList<XMLGregorianCalendar> dates1;

    private EList<XMLGregorianCalendar> dates2;

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
     * @return the tags
     */
    public EList<String> getTags() {
        if (tags == null) {
            tags = new BasicEList<String>();
        }
        return tags;
    }

    /**
     * @return the labels
     */
    public EList<String> getLabels() {
        if (labels == null) {
            labels = new BasicEList<String>();
        }
        return labels;
    }

    /**
     * @return the labels
     */
    public EList<XMLGregorianCalendar> getDates1() {
        if (dates1 == null) {
            dates1 = new BasicEList<XMLGregorianCalendar>();
        }
        return dates1;
    }

    /**
     * @return the labels
     */
    public EList<XMLGregorianCalendar> getDates2() {
        if (dates2 == null) {
            dates2 = new BasicEList<XMLGregorianCalendar>();
        }
        return dates2;
    }

}
