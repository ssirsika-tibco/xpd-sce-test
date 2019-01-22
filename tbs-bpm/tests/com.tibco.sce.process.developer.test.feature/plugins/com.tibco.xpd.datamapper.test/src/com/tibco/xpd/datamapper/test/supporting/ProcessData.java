/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.test.supporting;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Pojo to hold process data.
 * 
 * @author nwilson
 * @since 8 Jun 2015
 */
public class ProcessData {
    private String description;

    private String id;

    private String masterProcessId;

    private String moduleName;

    private String originator;

    private String originatorName;

    private String parentProcessId;

    private Integer priority;

    private XMLGregorianCalendar startTime;

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the masterProcessId
     */
    public String getMasterProcessId() {
        return masterProcessId;
    }

    /**
     * @param masterProcessId
     *            the masterProcessId to set
     */
    public void setMasterProcessId(String masterProcessId) {
        this.masterProcessId = masterProcessId;
    }

    /**
     * @return the moduleName
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * @param moduleName
     *            the moduleName to set
     */
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    /**
     * @return the originator
     */
    public String getOriginator() {
        return originator;
    }

    /**
     * @param originator
     *            the originator to set
     */
    public void setOriginator(String originator) {
        this.originator = originator;
    }

    /**
     * @return the originatorName
     */
    public String getOriginatorName() {
        return originatorName;
    }

    /**
     * @param originatorName
     *            the originatorName to set
     */
    public void setOriginatorName(String originatorName) {
        this.originatorName = originatorName;
    }

    /**
     * @return the parentProcessId
     */
    public String getParentProcessId() {
        return parentProcessId;
    }

    /**
     * @param parentProcessId
     *            the parentProcessId to set
     */
    public void setParentProcessId(String parentProcessId) {
        this.parentProcessId = parentProcessId;
    }

    /**
     * @return the priority
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * @param priority
     *            the priority to set
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    /**
     * @return the startTime
     */
    public XMLGregorianCalendar getStartTime() {
        return startTime;
    }

    /**
     * @param startTime
     *            the startTime to set
     */
    public void setStartTime(XMLGregorianCalendar startTime) {
        this.startTime = startTime;
    }

}
