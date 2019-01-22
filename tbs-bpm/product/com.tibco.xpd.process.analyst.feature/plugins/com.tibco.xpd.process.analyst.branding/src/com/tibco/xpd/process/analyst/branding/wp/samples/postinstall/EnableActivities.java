/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.process.analyst.branding.wp.samples.postinstall;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.IActivityManager;
import org.eclipse.ui.activities.IWorkbenchActivitySupport;
import org.eclipse.ui.intro.IIntroSite;

import com.tibco.xpd.process.analyst.branding.Messages;
import com.tibco.xpd.process.analyst.branding.wp.samples.SampleProjectsViewer;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * Enable workbench activities. This is {@link PostInstallAction} enabling
 * specified activities. Refer to extension point
 * "com.tibco.xpd.resources.ui.sampleProjects" documentation, to learn how to
 * use post-install actions. This action expect to get parameter <b>activityId</b>.
 * Multiple activity identifiers should be comma separated.
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

	private final Logger log = LoggerFactory
	        .createLogger(SampleProjectsViewer.PLUGIN_ID);

	@SuppressWarnings("unchecked") 
	public void run(IIntroSite site, Properties params) {

		Set<String> toEnable = new HashSet<String>();

		String ids = params.getProperty("activityId"); //$NON-NLS-1$
		if (ids == null) {
			log.error(Messages.EnableActivities_NoActivityId_message);
			throw new NullPointerException(Messages.EnableActivities_NoActivityId_message);
		}
		if (ids.trim().length() == 0) {
			log.error(Messages.EnableActivities_EmptyActivityId_message);
			throw new IllegalArgumentException(
			        Messages.EnableActivities_EmptyActivityId_message);
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
				log.info(String.format(Messages.EnableActivities_EnablingActivity_message, aId), LOG_INFO_CATEGORY);
			} else {
				log.warn(String.format(Messages.EnableActivities_ActivityNotFound_message, aId));
			}
		}

		Set<String> enabledActivityIds = activityManager
		        .getEnabledActivityIds();
		Set<String> set = new HashSet<String>(enabledActivityIds);
		set.addAll(toEnable);
		workbenchActivitySupport.setEnabledActivityIds(set);
	}
}
