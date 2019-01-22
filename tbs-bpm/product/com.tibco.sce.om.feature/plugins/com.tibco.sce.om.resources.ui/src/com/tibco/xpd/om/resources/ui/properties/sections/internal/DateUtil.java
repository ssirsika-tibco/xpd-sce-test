/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.internal;


/**
 * Utility class that can be used to serialize date/time in the Organization
 * Model using the ISO 8601 standardized format. The time will also be
 * normalized to UTC to avoid any confusion of time zones/daylight saving time.
 * 
 * @author njpatel
 * 
 * @deprecated use
 *             {@link com.tibco.xpd.resources.ui.components.calendar.DateUtil}
 *             instead.
 * 
 */
@Deprecated
public final class DateUtil extends
        com.tibco.xpd.resources.ui.components.calendar.DateUtil {
}
