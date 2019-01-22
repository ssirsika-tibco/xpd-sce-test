/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.process.analyst.branding.wp.samples.postinstall;

import java.util.Properties;

import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveRegistry;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.intro.IIntroSite;

import com.tibco.xpd.process.analyst.branding.Messages;
import com.tibco.xpd.process.analyst.branding.wp.samples.SampleProjectsViewer;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * Switch to perspective.
 * This is {@link PostInstallAction} enabling specified activities.
 * Refer to extension point "com.tibco.xpd.resources.ui.sampleProjects" documentation,
 * to learn how to use post-install actions.
 * This action expect to get parameter <b>perspectiveId</b>.
 * <pre>
 * ...
 * &lt;post-install
 *     class="com.tibco.xpd.ui.wp.samples.postinstall.SwitchPerspective"&gt;
 *     &lt;property
 *         name="perspectiveId"
 *         value="com.tibco.xpd.analyst.resources.xpdl2.perspective"/&gt;
 * &lt;/post-install&gt;
 * ...
 * </pre>
 *
 * @author mmaciuki
 *
 */
public class SwitchPerspective implements PostInstallAction {

	private static Logger log = LoggerFactory.createLogger(SampleProjectsViewer.PLUGIN_ID);

	/**
	 * @throws IllegalArgumentException If the perspectiveId is null or empty string.
	 */
	public void run(IIntroSite site, Properties params) {
		String perspectiveId = params.getProperty("perspectiveId"); //$NON-NLS-1$
		if (perspectiveId == null || perspectiveId.trim().length() == 0) {
			throw new IllegalArgumentException(
					Messages.SwitchPerspective_IllegalPerspectiveId_message);
		}

		IWorkbenchWindow window = site.getWorkbenchWindow();
		IWorkbench workbench = PlatformUI.getWorkbench();
		IPerspectiveRegistry perspectiveRegistry = workbench.getPerspectiveRegistry();
		IPerspectiveDescriptor perspective = perspectiveRegistry
				.findPerspectiveWithId(perspectiveId);
		if (perspective == null) {
			log.warn(String.format(Messages.SwitchPerspective_PerspectiveNotFound_message, perspectiveId));
		} else {
			try {
				workbench.showPerspective(perspectiveId, window);
			} catch (WorkbenchException e) {
				log.error(e);
			}
		}
	}
}
