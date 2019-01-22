/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.process.analyst.branding.wp.samples.postinstall;

import java.util.Properties;

import org.eclipse.core.runtime.Assert;
import org.eclipse.ui.intro.IIntroSite;
import org.eclipse.ui.intro.config.IIntroURL;
import org.eclipse.ui.intro.config.IntroURLFactory;

import com.tibco.xpd.process.analyst.branding.Messages;

public class ShowStandby implements PostInstallAction {

	public void run(IIntroSite site, Properties params) {
		String pluginId = params.getProperty("pluginId"); //$NON-NLS-1$
		String partId = params.getProperty("partId"); //$NON-NLS-1$
		String inputId = params.getProperty("inputId"); //$NON-NLS-1$

		if (pluginId == null) {
			throw new NullPointerException(
			        Messages.ShowStandby_NullPluginId_message);
		}
		if (pluginId.trim().length() == 0) {
			throw new IllegalArgumentException(
			        Messages.ShowStandby_EmptyPluginId_message);
		}

		if (partId == null) {
			throw new NullPointerException(
			        Messages.ShowStandby_NullPartId_message);
		}
		if (partId.trim().length() == 0) {
			throw new IllegalArgumentException(
			        Messages.ShowStandby_EmptyPartId_message);
		}

		if (inputId == null) {
			throw new NullPointerException(
			        Messages.ShowStandby_NullInputId_message);
		}
		if (inputId.trim().length() == 0) {
			throw new IllegalArgumentException(
			        Messages.ShowStandby_EmptyPluginId_message);
		}

		show(pluginId, partId, inputId);
	}

	private void show(String pluginId, String partId, String input) {
		StringBuilder url = new StringBuilder();
		url.append("http://org.eclipse.ui.intro/showStandby?"); //$NON-NLS-1$
		url.append("pluginId="); //$NON-NLS-1$
		url.append(pluginId);
		url.append("&"); //$NON-NLS-1$
		url.append("partId="); //$NON-NLS-1$
		url.append(partId);
		url.append("&"); //$NON-NLS-1$
		url.append("input="); //$NON-NLS-1$
		url.append(input);
		IIntroURL introURL = IntroURLFactory.createIntroURL(url.toString());
		if (introURL == null) {
			throw new IllegalArgumentException(
			        String.format(Messages.ShowStandby_InvalidUrl_message, url.toString()));
		}
		introURL.execute();
	}

}
