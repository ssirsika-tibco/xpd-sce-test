/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.branding.wp.samples.postinstall;

import java.util.Properties;

import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveRegistry;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.intro.IIntroPart;
import org.eclipse.ui.intro.IIntroSite;

import com.tibco.xpd.branding.BrandingPlugin;
import com.tibco.xpd.branding.internal.Messages;

/**
 * Switch to perspective. This is {@link PostInstallAction} enabling specified
 * activities. Refer to extension point
 * "com.tibco.xpd.resources.ui.sampleProjects" documentation, to learn how to
 * use post-install actions. This action expect to get parameter
 * <b>perspectiveId</b>.
 * 
 * <pre>
 * ...
 * &lt;post-install
 *     class=&quot;com.tibco.xpd.ui.wp.samples.postinstall.SwitchPerspective&quot;&gt;
 *     &lt;property
 *         name=&quot;perspectiveId&quot;
 *         value=&quot;com.tibco.xpd.analyst.resources.xpdl2.perspective&quot;/&gt;
 * &lt;/post-install&gt;
 * ...
 * </pre>
 * 
 * @author mmaciuki
 * 
 */
public class SwitchPerspective implements PostInstallAction {

    /**
     * @throws IllegalArgumentException
     *             If the perspectiveId is null or empty string.
     */
    public void run(IIntroSite site, Properties params) {
        String perspectiveId = params.getProperty("perspectiveId"); //$NON-NLS-1$
        if (perspectiveId == null || perspectiveId.trim().length() == 0) {
            throw new IllegalArgumentException(
                    Messages.SwitchPerspective_NullOrEmptyPerspectiveID_message);
        }

        IWorkbenchWindow window = site.getWorkbenchWindow();
        IWorkbench workbench = PlatformUI.getWorkbench();
        IPerspectiveRegistry perspectiveRegistry = workbench
                .getPerspectiveRegistry();
        IPerspectiveDescriptor perspective = perspectiveRegistry
                .findPerspectiveWithId(perspectiveId);
        if (perspective == null) {
            BrandingPlugin
                    .logWarning(
                            String
                                    .format(
                                            Messages.SwitchPerspective_InvalidPerspectiveID_message,
                                            perspectiveId));
        } else {
            try {
                workbench.showPerspective(perspectiveId, window);

                IIntroPart intro = workbench.getIntroManager().getIntro();

                if (intro != null) {
                    workbench.getIntroManager().closeIntro(intro);
                }

            } catch (WorkbenchException e) {
                BrandingPlugin.logError(e);
            }
        }
    }
}
