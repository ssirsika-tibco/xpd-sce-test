package com.tibco.xpd.swtbot.performance.utils;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.eclipse.swtbot.swt.finder.waits.WaitForObjectCondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.eclipse.swtbot.swt.finder.widgets.TimeoutException;
import org.hamcrest.Matcher;

/**
 * Class contains a lot of util methods for SWTbot tests.
 * 
 * @author santkumar
 * 
 */
public class WaitUtils {
    private static final int WAIT_INTERVAL = 50;

    public static void slowDownExecutions(int factor) {
        long oldDelay = SWTBotPreferences.PLAYBACK_DELAY;
        // increase the delay
        SWTBotPreferences.PLAYBACK_DELAY = 50 * factor;
    }

    /**
     * Wait for the specified button to enable
     * 
     * @param bot
     * @param button
     * @param timeout
     */
    public static void waitForButtonToEnable(SWTWorkbenchBot bot,
            SWTBotButton button, long timeout) {
        bot.waitUntil(Conditions.widgetIsEnabled(button), timeout);
    }

    /**
     * Waits for specified amount of milliseconds the specified button to appear
     * 
     * @param bot
     *            - current SWTEclipseBot instance
     * @param buttonCaption
     *            - caption of button to wait
     * @param timeout
     *            - amount of milliseconds to wait
     * @throws InterruptedException
     *             - in case waiting timed out
     */
    public static void waitForButtonToAppear(final SWTWorkbenchBot bot,
            final String buttonCaption, int timeout)
            throws InterruptedException {

        Matcher<Button> withText = WidgetMatcherFactory.withText(buttonCaption);
        WaitForObjectCondition<Button> waitForWidget =
                Conditions.waitForWidget(withText);
        bot.waitUntil(waitForWidget, timeout, 200);
    }

    /**
     * Waits for specified amount of milliseconds the specified window to appear
     * 
     * @param bot
     *            - current SWTEclipseBot instance
     * @param windowName
     *            - Title of window to activate
     * @param timeout
     *            - amount of milliseconds to wait
     * @throws InterruptedException
     *             - in case waiting timed out
     */
    public static void waitForWindowToAppear(SWTWorkbenchBot bot,
            final String windowName, int timeout) {
        Matcher<Shell> containingText =
                ContainingText.containingText(windowName);
        WaitForObjectCondition<Shell> waitForShell =
                Conditions.waitForShell(containingText);
        try {
            bot.waitUntil(waitForShell, timeout);
        } catch (TimeoutException ex) {
            Shell[] shells = bot.getFinder().getShells();
            System.err.println("> Currently visible shells:");
            for (Shell s : shells) {
                try {
                    System.err.println("\t" + s.getText());
                } catch (Exception e) {
                }
                ;
            }

            throw ex;
        }
    }

    /**
     * Waits for specified amount of milliseconds the specified window to
     * disappear
     * 
     * @param bot
     *            - current SWTEclipseBot instance
     * @param windowName
     *            - Title of window to activate
     * @param timeout
     *            - amount of milliseconds to wait
     * @throws InterruptedException
     *             - in case waiting timed out
     */
    public static void waitForWindowToDisappear(SWTWorkbenchBot bot,
            final String windowName, int timeout) {
        Matcher<Shell> containingText =
                ContainingText.containingText(windowName);
        WaitForObjectCondition<Shell> waitForShell = null;
        try {
            waitForShell = Conditions.waitForShell(containingText);
            bot.waitUntil(waitForShell, 500);
        } catch (Exception ex) {
            // already closed.
            return;
        }
        Shell shell = waitForShell.get(0);
        SWTBotShell botShell = new SWTBotShell(shell);
        bot.waitUntil(Conditions.shellCloses(botShell), timeout);
    }

    /**
     * Maximize some view.
     * 
     * @param bot
     *            - current SWTEclipseBot instance
     * @param viewName
     *            - view name to maximize
     */
    public static void maximizeView(SWTWorkbenchBot bot, String viewName) {
        bot.viewByTitle(viewName).setFocus();
        bot.menu("Window").menu("Navigation")
                .menu("Maximize Active View or Editor").click();
    }

    /**
     * Maximize some view.
     * 
     * @param bot
     *            - current SWTEclipseBot instance
     * @param viewName
     *            - view name to minimize
     */
    public static void minimizeView(SWTWorkbenchBot bot, String viewName) {
        bot.viewByTitle(viewName).setFocus();
        bot.menu("Window").menu("Navigation")
                .menu("Minimize Active View or Editor").click();
    }

    /**
     * 
     * @author shruti Class implements the ICondition interface methods needed
     *         to wait for the selection of tree item
     * 
     */
    private static final class WaitForSelectionCondition implements ICondition {
        private final SWTBotTreeItem item;

        /**
         * 
         * @param item
         *            Tree Item that needs to selected
         */
        private WaitForSelectionCondition(SWTBotTreeItem item) {
            this.item = item;
        }

        @Override
        public boolean test() throws Exception {
            return item.isSelected();
        }

        @Override
        public void init(SWTBot bot) {
        }

        @Override
        public String getFailureMessage() {
            return "Failed to select an item: " + item.getText();
        }
    }

    /**
     * 
     * @param bot
     *            current SWTEclipseBot instance
     * @param item
     *            Tree item which needs to selected
     * @param timeout
     *            wait until timeout is reached or condition is met
     */
    public static void waitForSelection(final SWTWorkbenchBot bot,
            final SWTBotTreeItem item, long timeout) {

        bot.waitUntil(new WaitForSelectionCondition(item), timeout, 10);

    }

    /**
     * Waits for all build jobs to complete
     */
    public static void waitForBuildJobsToComplete() {
        Object[] buildFamilies =
                new Object[] { ResourcesPlugin.FAMILY_AUTO_BUILD,
                        ResourcesPlugin.FAMILY_MANUAL_BUILD,
                        ResourcesPlugin.FAMILY_AUTO_REFRESH,
                        ResourcesPlugin.FAMILY_AUTO_REFRESH };

        waitForJobFamilyToComplete(buildFamilies);
    }

    public static void waitForBuildsToFinish(IProgressMonitor monitor,
            final long interval) throws InterruptedException {
        // There is a wait interval needed because there may be another build(s)
        // scheduled for the artifacts generated during the first build (
        // this process can be recursive).
        final long WAIT_FOR_SCHEDULED_BUILD_INTERVAL = interval; // in
                                                                 // milliseconds
        // Wait for the builds to finish.
        monitor.setTaskName("Waiting for builds to finish.");
        IJobManager jobMan = Job.getJobManager();
        Object[] JOB_FAMILIES =
                new Object[] { ResourcesPlugin.FAMILY_AUTO_BUILD,
                        ResourcesPlugin.FAMILY_MANUAL_BUILD,
                        ResourcesPlugin.FAMILY_MANUAL_REFRESH,
                        ResourcesPlugin.FAMILY_AUTO_REFRESH };
        int foundActiveJobs;
        do {
            foundActiveJobs = 0;
            Thread.sleep(WAIT_FOR_SCHEDULED_BUILD_INTERVAL);
            for (Object family : JOB_FAMILIES) {
                Job[] familyActiveJobs = jobMan.find(family);
                if (familyActiveJobs.length > 0) {
                    foundActiveJobs++;
                    jobMan.join(family, monitor);
                }
            }
        } while (foundActiveJobs > 0);
    }

    /**
     * Waits for the specified job families to complete
     * 
     * @param family
     */
    public static void waitForJobFamilyToComplete(Object... family) {
        for (Object f : family) {
            Job[] jobs = Job.getJobManager().find(f);
            if (jobs.length > 0) {
                try {
                    jobs[0].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                waitForJobFamilyToComplete(family);
            }
        }
    }

    /**
     * Waits for any job that matches the class name provided to finish
     * 
     * @param className
     */
    public static void waitForJobToComplete(String className) {
        Job[] jobs = Job.getJobManager().find(null);

        for (Job job : jobs) {
            if (job.getClass().getName().equalsIgnoreCase(className)) {
                try {
                    job.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}