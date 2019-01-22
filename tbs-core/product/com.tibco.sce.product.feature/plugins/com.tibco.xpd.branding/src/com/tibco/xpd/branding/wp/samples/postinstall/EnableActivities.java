/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.branding.wp.samples.postinstall;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.IActivityManager;
import org.eclipse.ui.activities.IWorkbenchActivitySupport;
import org.eclipse.ui.intro.IIntroSite;

import com.tibco.xpd.branding.BrandingPlugin;
import com.tibco.xpd.branding.internal.Messages;

/**
 * Enable workbench activities. This is {@link PostInstallAction} enabling
 * specified activities. Refer to extension point
 * "com.tibco.xpd.resources.ui.sampleProjects" documentation, to learn how to
 * use post-install actions. This action expect to get parameter
 * <b>activityId</b>. Multiple activity identifiers should be comma separated.
 * 
 * <pre>
 *  ...
 *  &lt;post-install
 *      class=&quot;com.tibco.xpd.ui.wp.samples.postinstall.EnableActivities&quot;&gt;
 *      &lt;property
 *          name=&quot;activityId&quot;
 *          value=&quot;com.tibco.xpd.capabilities.developer,com.tibco.xpd.capabilities.analyst&quot;/&gt;
 *  &lt;/post-install&gt;
 *  ...
 * </pre>
 * 
 * @author mmaciuki
 * 
 */
public class EnableActivities implements PostInstallAction {

    private static final String LOG_INFO_CATEGORY = "info/post-install/enable-activities"; //$NON-NLS-1$

    @SuppressWarnings("unchecked")
    public void run(IIntroSite site, Properties params) {

	Set<String> toEnable = new HashSet<String>();

	String ids = params.getProperty("activityId"); //$NON-NLS-1$
	if (ids == null) {
	    BrandingPlugin.logError(
		    Messages.EnableActivities_NoActivityID_message, null);
	    throw new NullPointerException(
		    Messages.EnableActivities_NoActivityID_message); //$NON-NLS-1$
	}
	if (ids.trim().length() == 0) {
	    BrandingPlugin.logError(
		    Messages.EnableActivities_EmptyActivityParameter_message,
		    null);
	    throw new IllegalArgumentException(
		    Messages.EnableActivities_EmptyActivityParameter_message); //$NON-NLS-1$
	}
	IWorkbenchActivitySupport workbenchActivitySupport = PlatformUI
	        .getWorkbench().getActivitySupport();
	IActivityManager activityManager = workbenchActivitySupport
	        .getActivityManager();
	Set definedActivityIds = activityManager.getDefinedActivityIds();
	String[] idArray = ids.split(","); //$NON-NLS-1$
	for (String activityId : idArray) {
	    String aId = activityId.trim();
	    if (definedActivityIds.contains(aId)) {
		toEnable.add(aId);
		BrandingPlugin
		        .logInfo(String
		                .format(Messages.EnableActivities_EnablingActivity_message,
		                        aId));
	    } else {
		BrandingPlugin.logWarning(String.format(
		        Messages.EnableActivities_NoActivityWithThisID_message,
		        aId));
	    }
	}

	Set<String> enabledActivityIds = activityManager
	        .getEnabledActivityIds();
	Set<String> set = new HashSet<String>(enabledActivityIds);
	set.addAll(toEnable);
	workbenchActivitySupport.setEnabledActivityIds(set);
    }
}
