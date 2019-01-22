/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.branding.wp.samples.postinstall;

import java.util.Properties;

import org.eclipse.ui.intro.IIntroSite;
import org.eclipse.ui.intro.config.IIntroURL;
import org.eclipse.ui.intro.config.IntroURLFactory;

import com.tibco.xpd.branding.internal.Messages;

public class ShowStandby implements PostInstallAction {

	public void run(IIntroSite site, Properties params) {
		String pluginId = params.getProperty("pluginId"); //$NON-NLS-1$
		String partId = params.getProperty("partId"); //$NON-NLS-1$
		String inputId = params.getProperty("inputId"); //$NON-NLS-1$

		if (pluginId == null) {
			throw new NullPointerException(
			        Messages.ShowStandby_NullPluginID_message);
		}
		if (pluginId.trim().length() == 0) {
			throw new IllegalArgumentException(
			        Messages.ShowStandby_EmptyPluginID_message);
		}

		if (partId == null) {
			throw new NullPointerException(
			        Messages.ShowStandby_NullPartID_message);
		}
		if (partId.trim().length() == 0) {
			throw new IllegalArgumentException(
			        Messages.ShowStandby_EmptyPartID_message);
		}

		if (inputId == null) {
			throw new NullPointerException(
			        Messages.ShowStandby_NullInputID_message);
		}
		if (inputId.trim().length() == 0) {
			throw new IllegalArgumentException(
			        Messages.ShowStandby_EmptyInputID_message);
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
			        String.format(Messages.ShowStandby_InvalidURL_message, url.toString()));
		}
		introURL.execute();
	}

}
