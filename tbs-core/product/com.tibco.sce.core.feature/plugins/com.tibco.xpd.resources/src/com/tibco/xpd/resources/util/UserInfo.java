/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.resources.util;

/**
 * UserInfo - class that participates in setting/getting values on the
 * UserInformation Preference page
 * 
 * @author bharge
 * 
 */
public class UserInfo {

    private String userName;

    private String domainName;

    private String organisationName;

    private String endpointURI;

    private String destination;

    public UserInfo(String userName, String domainName,
            String organisationName, String endpointURI, String destination) {
        this.userName = userName;
        this.domainName = domainName;
        this.organisationName = organisationName;
        this.endpointURI = endpointURI;
        this.destination = destination;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     *            the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the domainName
     */
    public String getDomainName() {
        return domainName;
    }

    /**
     * @param domainName
     *            the domainName to set
     */
    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    /**
     * @return the organisationName
     */
    public String getOrganisationName() {
        return organisationName;
    }

    /**
     * @param organisationName
     *            the organisationName to set
     */
    public void setOrganisationName(String organisationName) {
        this.organisationName = organisationName;
    }

    /**
     * @return the endpointURI
     */
    public String getEndpointURI() {
        return endpointURI;
    }

    /**
     * @param endpointURI
     *            the endpointURI to set
     */
    public void setEndpointURI(String endpointURI) {
        this.endpointURI = endpointURI;
    }

    /**
     * @return the destination
     */
    public String getDestination() {
        return destination;
    }

    /**
     * @param destination
     *            the destination to set
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

}
