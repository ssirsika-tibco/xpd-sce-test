/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.swtbot.performance;

import static org.junit.Assert.assertTrue;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCheckBox;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotRadio;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.tibco.xpd.resources.logger.events.EventProcessor;
import com.tibco.xpd.resources.logger.events.PrintEventHandler;
import com.tibco.xpd.resources.logger.events.SummaryEventHandler;
import com.tibco.xpd.resources.logger.events.TimedAction;
import com.tibco.xpd.swtbot.performance.utils.FindUtils;
import com.tibco.xpd.swtbot.performance.utils.WaitUtils;

/**
 * The performance test for MacTel projects.
 * 
 * @author jarciuch
 * @since 24 Nov 2011
 */
// @RunWith(SWTBotJunit4ClassRunner.class)
public class MacTelPerformanceTest {

    private String projectsPath = "C:\\src\\projects\\"; //$NON-NLS-1$

    private static SWTWorkbenchBot bot;

    private static EventProcessor ep;

    @BeforeClass
    public static void beforeClass() throws Exception {
        ep =
                new EventProcessor(new PrintEventHandler(),
                        new SummaryEventHandler());
        WaitUtils.slowDownExecutions(2);
        bot = new SWTWorkbenchBot();
        bot.viewByTitle("Welcome").close(); //$NON-NLS-1$
        // bot.shell("Subclipse Usage").close();
        // bot.shell("Atlassian Connector for Eclipse").close();
    }

    @SuppressWarnings("nls")
    @Ignore("For now")
    @Test
    public void testImportCleanBuild() throws Exception {
        // wait a bit for all "on startup" stuff.
        bot.sleep(15000);
        WaitUtils.waitForBuildsToFinish(new NullProgressMonitor(), 500);
        importProjects(projectsPath + "mactel-ServiceDeliveryBuild-2",
                false,
                true);
        cleanBuild();
        ep.event(SummaryEventHandler.PRINT_SUMMARY, "testImportCleanBuild");
    }

    @SuppressWarnings("nls")
    @Ignore("For now")
    @Test
    public void testImportDaaGenOMProject() throws Exception {
        // Wait a bit for all "on startup" stuff.
        bot.sleep(15000);
        WaitUtils.waitForBuildsToFinish(new NullProgressMonitor(), 500);
        importProjects(projectsPath + "ServiceDeliveryOrg.zip", true, true);
        exportBpmDaa("ServiceDeliveryOrg");
        ep.event(SummaryEventHandler.PRINT_SUMMARY, "testImportDaaGenOMProject");
    }

    @SuppressWarnings("nls")
    @Ignore("For now")
    @Test
    public void testImportDaaGen() throws Exception {
        // Wait a bit for all "on startup" stuff.
        bot.sleep(15000);
        WaitUtils.waitForBuildsToFinish(new NullProgressMonitor(), 500);

        importProjects(projectsPath + "ImportFirst_MacTel-2011-11-15.zip",
                true,
                true);
        importProjects(projectsPath + "ImportSecond_MacTel-2011-11-15.zip",
                true,
                true);

        cleanBuild();

        ep.begin("All DAA export");
        exportBpmDaa("ServiceDeliveryOrg");
        exportBpmDaa("Services_v1.0");
        exportBpmDaa("Utilities");
        exportBpmDaa("UtilitiesTester");
        exportBpmDaa("ServicesTester");
        exportBpmDaa("ServiceDeliveryProduct");
        exportBpmDaa("ServiceDeliveryHostingBundle");
        exportBpmDaa("ServiceDeliveryBundle");
        exportBpmDaa("ServiceDeliveryBuild");
        exportBpmDaa("OrderProductDelivery");
        ep.end("All DAA export");

        ep.event(SummaryEventHandler.PRINT_SUMMARY, "testImportDaaGen");
    }

    @SuppressWarnings("nls")
    // @Ignore("For now")
    @Test
    public void testEventProcessor() throws Exception {
        ep.begin("First Activity", "a", "b");
        bot.sleep(3000);
        ep.end("First Activity", "a", "b");

        ep.begin("Second Activity", "a");
        bot.sleep(3000);
        ep.end("Second Activity", "a");

        ep.runTimedAction("Timed Action", new TimedAction() {
            @Override
            public void run() throws Exception {
                bot.sleep(3000);
            }
        }, "c", "d", "e");
        ep.event(SummaryEventHandler.PRINT_SUMMARY, "Test Report");

    }

    /**
     * @throws InterruptedException
     */
    @SuppressWarnings("nls")
    private void exportBpmDaa(String projectName) throws Exception {
        SWTBotShell mainShell = bot.shells()[0];
        mainShell.activate();
        bot.activeShell().activate();
        SWTBotView explorerView = bot.viewByTitle("Project Explorer");
        explorerView.show();
        explorerView.setFocus();

        ep.log("Selecting ServiceDeliveryOrg proj.");
        SWTBotTree projExplorerTree = explorerView.bot().tree();
        SWTBotTreeItem projTreeItem = projExplorerTree.getTreeItem(projectName);
        projTreeItem.select();
        ep.log("Contex menu - export DAA.");
        SWTBotMenu daaExportMenu =
                FindUtils.findContextMenu(projTreeItem,
                        "Export",
                        "Distributed Application Archive (DAA) Export");
        daaExportMenu.click();
        ep.log("DAA Export Wizard.");
        WaitUtils.waitForWindowToAppear(bot,
                "Distributed Application Archive (DAA) Export Wizard",
                60000);
        SWTBotShell wizardShell =
                bot.shell("Distributed Application Archive (DAA) Export Wizard");
        wizardShell.activate();

        ep.log("Find button: 'Finish'");
        final SWTBotButton finishButton = bot.button("Finish");

        ep.log("Wait for 'Finish' to be enabled (max 60s).");
        bot.waitUntil(new DefaultCondition() {
            @Override
            public boolean test() throws Exception {
                return finishButton.isEnabled();
            }

            @Override
            public String getFailureMessage() {
                return "Finish is not enabled after 60s timeout.";
            }
        }, 60000, 1000);
        ep.log("Click: 'Finish'.");

        finishButton.click();
        ep.begin("DAA Export", projectName);
        ep.log("Wait for 'Distributed Application Archive (DAA) Export Wizard' window to disappear (max 30 min.).");
        WaitUtils.waitForWindowToDisappear(bot,
                "Distributed Application Archive (DAA) Export Wizard",
                30 * 60000);
        ep.end("DAA Export", projectName);

        ep.log("Waiting for build jobs to complete.");
        ep.runTimedAction("Wait for build after DAA export.",
                new TimedAction() {
                    @Override
                    public void run() throws Exception {
                        WaitUtils
                                .waitForBuildsToFinish(new NullProgressMonitor(),
                                        500);
                    }
                },
                projectName);
    }

    /**
     * Imports project(s) into workspace.
     * 
     * @param location
     *            absolute location of the root directory or zip/tar file
     *            (depending on isZip parameter).
     * @param isZip
     *            if this is zip (true) of rolder (false) option.
     * @param copyToWorkspace
     *            if 'Copy to workspace' checkbox should be selected. (Only
     *            aplicable if isZip is set to 'false').
     * @throws InterruptedException
     */
    @SuppressWarnings("nls")
    private void importProjects(String location, boolean isZip,
            boolean copyToWorkspace) throws InterruptedException {
        SWTBotShell mainShell = bot.shells()[0];
        mainShell.activate();
        bot.activeShell().activate();

        ep.log("Switching autobuild off in UI thread.");
        bot.getDisplay().syncExec(new Runnable() {
            @Override
            public void run() {
                IWorkspace workspace = ResourcesPlugin.getWorkspace();
                if (workspace.isAutoBuilding()) {
                    IWorkspaceDescription description =
                            workspace.getDescription();
                    description.setAutoBuilding(false);
                    try {
                        workspace.setDescription(description);
                    } catch (CoreException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        assertTrue("Build Automatically should be switched off",
                !ResourcesPlugin.getWorkspace().isAutoBuilding());

        ep.log("Opening and activating wizard: 'Import'");
        bot.menu("File").menu("Import...").click();
        SWTBotShell importShell = bot.shell("Import");
        importShell.activate();

        ep.log("Expanding; 'General'");
        SWTBotTreeItem expandNode = bot.tree().expandNode("General");

        ep.log("Selecting: 'Existing Projects into Workspace'");
        expandNode.select("Existing Projects into Workspace");

        ep.log("Clicking: 'Next >'");
        bot.button("Next >").click();

        if (isZip) {
            ep.log("Clicking radio: 'Select archive file'");
            SWTBotRadio radioSelectArchiveFile = bot.radio(1);
            radioSelectArchiveFile.click();

            ep.log("Selecting text field: 'Select archive file'");
            SWTBotText zipText = bot.text(1);

            ep.log("Setting text into text field: 'Select root directory'");
            zipText.setText(location);

        } else { // Import from root directory.
            ep.log("Selecting text field: 'Select root directory'");
            SWTBotText directoryText = bot.text(0);

            ep.log("Setting text into text field: 'Select root directory'");
            directoryText.setText(location);

            ep.log("Selecting checkbox: 'Copy projects into workspace'");
            SWTBotCheckBox projectIntoWsButton = bot.checkBox(0);
            if (copyToWorkspace) {
                projectIntoWsButton.select();
            } else {
                projectIntoWsButton.deselect();
            }
        }

        // to lose focus
        bot.button("Cancel").setFocus();

        ep.log("Find button: 'Finish'");
        final SWTBotButton finishButton = bot.button("Finish");

        ep.log("Wait for 'Finish' to be enabled (max 60s).");
        bot.waitUntil(new DefaultCondition() {
            @Override
            public boolean test() throws Exception {
                return finishButton.isEnabled();
            }

            @Override
            public String getFailureMessage() {
                return "Finish is not enabled after 60s timeout.";
            }
        }, 60000, 1000);
        ep.log("Click: 'Finish'.");
        finishButton.click();
        ep.begin("Importing Projects", location);
        ep.log("Wait for 'Import' window to disappear (max 10 min.).");
        WaitUtils.waitForWindowToDisappear(bot, "Import", 10 * 60000);

        ep.log("Switching autobuild on in UI thread.");
        bot.getDisplay().syncExec(new Runnable() {
            @Override
            public void run() {
                IWorkspace workspace = ResourcesPlugin.getWorkspace();
                if (!workspace.isAutoBuilding()) {
                    IWorkspaceDescription description =
                            workspace.getDescription();
                    description.setAutoBuilding(true);
                    try {
                        workspace.setDescription(description);
                    } catch (CoreException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        assertTrue("Build Automatically should be switched on", ResourcesPlugin
                .getWorkspace().isAutoBuilding());

        ep.log("Waiting for build jobs to complete.");
        WaitUtils.waitForBuildsToFinish(new NullProgressMonitor(), 500);
        ep.end("Importing Projects", location);
    }

    /**
     * @throws InterruptedException
     */
    @SuppressWarnings("nls")
    private void cleanBuild() throws InterruptedException {
        SWTBotShell mainShell = bot.shells()[0];
        mainShell.activate();
        bot.activeShell().activate();
        ep.log("Opening Project->Clean");
        bot.menu("Project", 6).menu("Clean...").click();

        WaitUtils.waitForWindowToAppear(bot, "Clean", 10 * 60000);
        SWTBotShell cleanShell = bot.shell("Clean");
        cleanShell.activate();

        ep.log("Select 'Clean all projects'");
        bot.radio("Clean all projects").click();

        ep.log("Press 'OK'");
        bot.button("OK").click();

        ep.begin("Clean Build");
        ep.log("Wait for 'Clean' window to disappear (max 10 min.).");
        WaitUtils.waitForWindowToDisappear(bot, "Clean", 10 * 60000);
        bot.sleep(1000);

        ep.log("Waiting for build jobs to complete.");
        WaitUtils.waitForBuildsToFinish(new NullProgressMonitor(), 1000);
        ep.end("Clean Build");
    }

    @AfterClass
    public static void sleep() {
        // A little sleep at the end. It was an exhausting suite.;)
        bot.sleep(10000);
    }

}
