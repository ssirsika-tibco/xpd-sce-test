/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.processwidget;

/**
 * Interface for provision of activity icon hyperlink action (via extensoin
 * point contribution).
 * 
 * @author aallway
 * @since 19 Aug 2011
 */
public interface IActivityHyperlinkHandler {

    /**
     * Only return <code>true</code> if this contribution is appropriate for the
     * given activity's configuration.
     * 
     * @param activityModelObject
     *            (regardless of whether the hyperlink should be enabeld or
     *            not).
     * 
     * @return <code>true</code> If this contribution can handle the hyperlink
     *         requirements of this activity.
     */
    boolean isApplicableActivity(Object activityModelObject);

    /**
     * Get the enablement context object. If non-null is returned then the
     * hyperlink is enabled and the enablement context object is passed back to
     * other methods. This might be useful where lookup is an expensive
     * operation.
     * 
     * @param activityModelObject
     * 
     * @return Context object (!= null) If it is currently possible to hyperlink
     *         to the object. The context object will be passed back to the
     *         {@link #getHyperlinkTooltip(Object)} and
     *         {@link #doHyperLink(Object)}.
     */
    Object getEnablementContextObject(Object activityModelObject);

    /**
     * @param activityModelObject
     * @param contextObject
     *            The context object returned by
     *            {@link #getEnablementContextObject(Object)} last time for thus
     *            activity. May be <code>null</code> if hyperlink is disabled.
     * 
     * @return The hyperlink tooltip text.
     */
    String getHyperlinkTooltip(Object activityModelObject, Object contextObject);

    /**
     * Perform the hyperlink action.
     * 
     * @param activityModelObject
     * @param contextObject
     *            The context object returned by
     *            {@link #getEnablementContextObject(Object)} last time for thus
     *            activity
     */
    void doHyperLink(Object activityModelObject, Object contextObject);
}
