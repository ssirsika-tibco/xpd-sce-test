/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.core.test;

import junit.framework.TestCase;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.events.IHyperlinkListener;

import com.tibco.xpd.core.validate.system.internal.IConfigurationLogger;
import com.tibco.xpd.core.validate.system.internal.SystemCheckRules;

/**
 * Run the Studio validation to validate the installation.
 * 
 * @author njpatel
 */
@SuppressWarnings("restriction")
public class StudioValidationTest extends TestCase {

    /**
     * Test the Studio installation. This will run the Studio validation code as
     * available in the product through the Help menu.
     * 
     * @throws Exception
     */
    public void testStudioInstallation() throws Exception {
        IWorkbench workbench = PlatformUI.getWorkbench();
        assertNotNull("Failed to get the IWorkbench", workbench);
        IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
        assertNotNull("Failed to get the IWorkbenchWindow", window);

        Logger logger = new Logger();
        SystemCheckRules rules = new SystemCheckRules(window, logger);

        rules.runChecks(new NullProgressMonitor());

        assertTrue("Studio installation validation failed.", logger.getResult()
                .getSeverity() != IStatus.ERROR);
    }

    /**
     * Validation logger. This is used to get the final result of the
     * validation.
     * 
     * @author njpatel
     * 
     */
    private class Logger implements IConfigurationLogger {

        private IStatus status;

        private Logger() {
            status = Status.OK_STATUS;
        }

        public void advice(String message) {
        }

        public void heading(String message) {
        }

        public void hyperlink(String message, String href,
                IHyperlinkListener listener, boolean revalidateOnLinkActivate) {
        }

        public void message(String message, int severity) {
        }

        public void message(String message, String[] additionalBullets,
                int severity) {
        }

        public void result(IStatus status) {
            this.status = status;
        }

        public IStatus getResult() {
            return status;
        }
    }

}
