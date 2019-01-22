/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties;

import java.util.List;

import org.eclipse.emf.common.notify.Notification;

/**
 * @author NWilson
 *
 */
public interface SashSection {

    boolean shouldSashSectionRefresh(List<Notification> notifications);
}
