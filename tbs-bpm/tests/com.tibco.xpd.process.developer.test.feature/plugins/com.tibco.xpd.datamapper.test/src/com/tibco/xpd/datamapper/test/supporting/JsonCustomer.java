/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.test.supporting;

/**
 * Customer test pojo.
 * 
 * @author nwilson
 * @since 28 May 2015
 */
public class JsonCustomer {
    private Integer id;

    private String name;

    private String dob;

    private String lastContact;

    private String time;

    private Boolean vip;

    private Double balance;

    private String secret;

    private JsonProperty[] address = new JsonProperty[0];

    private String[] tags;

    private String[] labels;

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
    public String getDob() {
        return dob;
    }

    /**
     * @param dob
     *            the dob to set
     */
    public void setDob(String dob) {
        this.dob = dob;
    }

    /**
     * @return the lastContact
     */
    public String getLastContact() {
        return lastContact;
    }

    /**
     * @param lastContact
     *            the lastContact to set
     */
    public void setLastContact(String lastContact) {
        this.lastContact = lastContact;
    }

    /**
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time
     *            the time to set
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * @return the properties
     */
    public JsonProperty[] getAddress() {
        return address;
    }

    /**
     * @param properties
     *            the properties to set
     */
    public void setAddress(JsonProperty[] address) {
        this.address = address;
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

    /**
     * @return the tags
     */
    public String[] getTags() {
        return tags;
    }

    /**
     * @param tags
     *            the tags to set
     */
    public void setTags(String[] tags) {
        this.tags = tags;
    }

    /**
     * @return the labels
     */
    public String[] getLabels() {
        return labels;
    }

    /**
     * @param labels
     *            the labels to set
     */
    public void setLabels(String[] labels) {
        this.labels = labels;
    }

}
