/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.swtbot.performance;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCheckBox;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotRadio;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.tibco.xpd.resources.logger.events.EventProcessor;
import com.tibco.xpd.resources.logger.events.PrintEventHandler;
import com.tibco.xpd.resources.logger.events.SummaryEventHandler;
import com.tibco.xpd.swtbot.performance.utils.WaitUtils;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.utils.ValidationStats;
import com.tibco.xpd.validation.utils.ValidationStats.RulesData;

/**
 * Contains the following tests : <li>1. Import & Clean Build</li> <li>2.
 * Validate on start event name change (package 7)</li> <li>3. Validate on start
 * event name change (package 12)</li>. <br/>
 * It then prints out the following validation time measurements for: <li>import
 * </li> <li>clean build</li> <li>total no of validation rules invoked</li> <li>
 * time each validation rule took</li> <li>no of times a particular rule was
 * invoked</li> <li>etc.</li>
 * 
 * @author agondal
 * @since 22 Aug 2013
 */
public class ValidationPerformanceTest {

    private String tempDir = "C:\\tempValidationPerf\\"; //$NON-NLS-1$

    private String projectsPath = tempDir + "perfTestProjects.zip"; //$NON-NLS-1$

    private long cleanBuildTime = 0;

    private long importTime = 0;

    private static SWTGefBot bot;

    private static EventProcessor ep;

    @BeforeClass
    public static void beforeClass() throws Exception {
        ep =
                new EventProcessor(new PrintEventHandler(),
                        new SummaryEventHandler());
        WaitUtils.slowDownExecutions(2);
        bot = new SWTGefBot();
        bot.perspectiveByLabel("Modeling").activate(); //$NON-NLS-1$
        bot.sleep(20000);

        // enable stats collection
        ValidationActivator.getDefault().setCollectValidationStats(true);
    }

    @SuppressWarnings("nls")
    @Ignore
    @Test
    public void testImportCleanBuildValidation() throws Exception {
        // wait a bit for all "on startup" stuff.
        bot.sleep(15000);

        clearValidationStatsData();
        importProjects(projectsPath, true, true);
        // print import validation stats to excel file
        printStatsToFile(tempDir + "ImportValidationStats.xls");

        clearValidationStatsData();
        cleanBuild();
        // print validation stats to excel file
        printStatsToFile(tempDir + "CleanBuildValidationStats.xls");
        bot.closeAllShells();
    }

    @Ignore
    @Test
    public void testLiveValidationInAProcessOfPackage7() throws Exception {
        // wait a bit for any running jobs
        bot.sleep(15000);
        performStartEventNameChangeAction(6);
        bot.closeAllShells();
    }

    // @Ignore
    @Test
    public void testLiveValidationInAProcessOfPackage12() throws Exception {
        // wait a bit for any running jobs
        bot.sleep(15000);
        performStartEventNameChangeAction(11);
        bot.closeAllShells();
    }

    private void clearValidationStatsData() {
        ep.log("clearing collected stats data..."); //$NON-NLS-1$
        ValidationStats.getInstance().clearStats();
    }

    /**
     * Changes name of 'Start Event' activity in the given package's first
     * process and prints out the validation stats to a file on the second
     * validation run
     * 
     * @throws InterruptedException
     */
    @SuppressWarnings("nls")
    private void performStartEventNameChangeAction(int packageIndex)
            throws InterruptedException {

        SWTBotShell mainShell = bot.shells()[0];
        mainShell.activate();

        assertTrue("Build Automatically should be switched on", ResourcesPlugin
                .getWorkspace().isAutoBuilding());

        SWTBotView explorerView = bot.viewByTitle("Project Explorer");
        explorerView.show();
        explorerView.setFocus();

        ep.log("Selecting PEF project...");
        SWTBotTree projExplorerTree = explorerView.bot().tree();
        SWTBotTreeItem projTreeItem = projExplorerTree.getTreeItem("PEF");
        projTreeItem.select();
        projTreeItem.expand();

        SWTBotTreeItem processesPackages =
                projTreeItem.getNode("Process Packages");
        processesPackages.expand();

        String eventName = "Start Event";

        // perform the action twice to record stats on seond run as the first
        // time it takes long to validate due to loading/initialisation
        for (int i = 1; i < 3; i++) {

            // clear validation data prior to performing the name change action
            clearValidationStatsData();

            SWTBotTreeItem pckg = processesPackages.getNode(packageIndex);
            ep.log("Selecting package " + pckg.getText() + "...");
            pckg.select().expand();
            SWTBotTreeItem processes =
                    pckg.getNode(0).select().getNode(4).select().expand();

            SWTBotTreeItem process = processes.getNode(0);
            String processName = process.getText();
            ep.log("Selecting process " + processName + "...");
            process.doubleClick();
            SWTBotEditor editor = bot.activeEditor();

            SWTBotGefEditor gmfEditor = bot.gefEditor(editor.getTitle());
            SWTBotGefEditor startEvent;
            // change name of start event
            try {
                startEvent = gmfEditor.select(eventName);
            } catch (Exception e) {
                startEvent = null;
            }
            if (startEvent != null) {
                eventName = "Start Event N" + i;
                startEvent.clickContextMenu("Edit Label")
                        .directEditType(eventName);

                WaitUtils.waitForBuildsToFinish(new NullProgressMonitor(),
                        20000);
            }
            if (i == 2) {
                printStatsToFile(tempDir + "ValidatonStats_" + processName
                        + ".xls");
            }
        }
    }

    /**
     * Prints validation stats to provided excel file
     * 
     * @param statsFileLocation
     */
    @SuppressWarnings("nls")
    private void printStatsToFile(String statsFileLocation) {

        try {

            File statsFile = new File(statsFileLocation);
            if (statsFile != null) {
                if (statsFile.exists()) {
                    statsFile.delete();
                }
                FileOutputStream fout = new FileOutputStream(statsFileLocation);
                ByteArrayOutputStream outputStream =
                        new ByteArrayOutputStream();
                HSSFWorkbook workBook = new HSSFWorkbook();
                HSSFSheet spreadSheet = workBook.createSheet("Stats"); //$NON-NLS-1$         
                HSSFCell cell;

                int i = 0;
                short j = 0;
                HSSFRow row = spreadSheet.createRow(i++);
                cell = row.createCell(j++);
                cell.setCellValue("Rule"); //$NON-NLS-1$

                cell = row.createCell(j++);
                cell.setCellValue("Invoked"); //$NON-NLS-1$

                cell = row.createCell(j++);
                cell.setCellValue("Total validation time (ms)"); //$NON-NLS-1$

                cell = row.createCell(j++);
                cell.setCellValue("Avg execution time (ms)"); //$NON-NLS-1$

                cell = row.createCell(j++);
                cell.setCellValue("Validation time for each execution"); //$NON-NLS-1$

                Collection<RulesData> rulesData =
                        ValidationStats.getInstance().getRulesData();
                for (RulesData ruleData : rulesData) {
                    j = 0;
                    row = spreadSheet.createRow(i++);
                    cell = row.createCell(j++);
                    cell.setCellValue(ruleData.getRuleName());

                    cell = row.createCell(j++);
                    cell.setCellValue(ruleData.getNumberOfTimesCalled());

                    cell = row.createCell(j++);
                    cell.setCellValue(ruleData.getTotalValidationTime());

                    cell = row.createCell(j++);
                    cell.setCellValue((double) ruleData
                            .getTotalValidationTime()
                            / ruleData.getNumberOfTimesCalled());

                    cell = row.createCell(j++);
                    cell.setCellValue(ruleData.getValidationTimes());
                }

                // Add total validation time

                row = spreadSheet.createRow(i++);
                cell = row.createCell((short) 0);
                cell.setCellValue("Total Validation Times:");
                cell = row.createCell((short) 1);
                cell.setCellValue("=sum(C2:C" + (i - 1) + ")");

                // Add import and clean times

                if (importTime > 0) {
                    row = spreadSheet.createRow(i++);
                    cell = row.createCell((short) 0);
                    cell.setCellValue("Import Time:");
                    cell = row.createCell((short) 1);
                    cell.setCellValue(importTime);
                }

                if (cleanBuildTime > 0) {

                    row = spreadSheet.createRow(i++);
                    cell = row.createCell((short) 0);
                    cell.setCellValue("Clean Build Time:");
                    cell = row.createCell((short) 1);
                    cell.setCellValue(cleanBuildTime);
                }

                // finally write to excel file and close it
                workBook.write(outputStream);
                outputStream.writeTo(fout);
                outputStream.close();
                fout.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        long startTime = System.currentTimeMillis();
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
        WaitUtils.waitForBuildsToFinish(new NullProgressMonitor(), 1000);

        long endTime = System.currentTimeMillis();
        importTime = endTime - startTime;

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

        long startTime = System.currentTimeMillis();

        ep.begin("Clean Build");
        ep.log("Wait for 'Clean' window to disappear (max 10 min.).");
        WaitUtils.waitForWindowToDisappear(bot, "Clean", 10 * 60000);
        bot.sleep(1000);

        ep.log("Waiting for build jobs to complete.");
        WaitUtils.waitForBuildsToFinish(new NullProgressMonitor(), 10000);
        ep.end("Clean Build");

        long endTime = System.currentTimeMillis();
        cleanBuildTime = endTime - startTime;
    }

    /**
     * Run this test at the end of all the test in this class to turn of
     * validation stats collection.
     * 
     * @throws Exception
     */
    @Test
    public void testAlwaysRunToDisableValidationStatsCollection()
            throws Exception {
        // disable stats collection
        ValidationActivator.getDefault().setCollectValidationStats(false);
        clearValidationStatsData();
    }
}
