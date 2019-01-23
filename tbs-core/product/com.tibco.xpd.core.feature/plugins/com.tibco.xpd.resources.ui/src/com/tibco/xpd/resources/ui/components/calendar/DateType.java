/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.resources.ui.components.calendar;

/**
 * Type of date being stored/read from the model.
 * 
 * @see DateTimePopup
 * 
 * @author njpatel
 * @since 3.3
 * 
 */
public enum DateType {

    /**
     * Date type
     */
    DATE,

    /**
     * Time type
     */
    TIME,

    /**
     * Date and Time type
     */
    DATETIME,

    /**
     * Date, Time and Time Zone type
     */
    DATETIMETZ;
}
