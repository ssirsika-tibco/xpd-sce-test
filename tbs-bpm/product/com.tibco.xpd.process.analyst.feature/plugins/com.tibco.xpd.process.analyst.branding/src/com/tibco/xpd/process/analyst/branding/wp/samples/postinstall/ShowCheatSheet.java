/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.process.analyst.branding.wp.samples.postinstall;

import java.util.Properties;

import org.eclipse.ui.intro.IIntroSite;

import com.tibco.xpd.process.analyst.branding.Messages;

/**
 * Show specified cheatsheet. This is {@link PostInstallAction} enabling
 * specified activities. Refer to extension point
 * "com.tibco.xpd.resources.ui.sampleProjects" documentation, to learn how to
 * use post-install actions. This action expect to get parameter <b>cheatsheetId</b>.
 * 
 * <pre>
 *  ...
 *  &lt;post-install
 *      class=&quot;com.tibco.xpd.ui.wp.samples.postinstall.ShowCheatSheet&quot;&gt;
 *      &lt;property
 *          name=&quot;cheatsheetId&quot;
 *          value=&quot;com.tibco.xpd.cheatsheets.xpd.welcome&quot;/&gt;
 *  &lt;/post-install&gt;
 *  ...
 * </pre>
 * 
 * @author mmaciuki
 * 
 */
public class ShowCheatSheet implements PostInstallAction {

	private ShowStandby standby;

	public ShowCheatSheet() {
		standby = new ShowStandby();
	}

	public void run(IIntroSite site, Properties params) {
		String cheatsheetId = params.getProperty("cheatsheetId"); //$NON-NLS-1$
		if (cheatsheetId == null || cheatsheetId.trim().length() == 0) {
			throw new IllegalArgumentException(Messages.IllegalCheatSheetId_message);
		}

		params.setProperty("pluginId", "org.eclipse.platform"); //$NON-NLS-1$ //$NON-NLS-2$
		params.setProperty("partId", "org.eclipse.platform.cheatsheet"); //$NON-NLS-1$ //$NON-NLS-2$
		params.setProperty("inputId", cheatsheetId); //$NON-NLS-1$

		standby.run(site, params);
	}

}
