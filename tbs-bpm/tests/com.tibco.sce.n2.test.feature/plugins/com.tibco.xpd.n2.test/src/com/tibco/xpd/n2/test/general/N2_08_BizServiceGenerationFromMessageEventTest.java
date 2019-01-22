/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.test.general;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.xml.sax.InputSource;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.implementer.resources.xpdl2.actions.GenerateBizServiceOnIncomingReqActivityAction;
import com.tibco.xpd.n2.test.core.AbstractN2BaseResourceTest;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * JUnit test to protect business service generation from a message event.
 * 
 * @author sajain
 * @since Jan 21, 2015
 */
public class N2_08_BizServiceGenerationFromMessageEventTest
        extends AbstractN2BaseResourceTest {

    /**
     * testBizServiceGenerationTest
     * 
     * @throws Exception
     */
    public void testBizServiceGenerationTest() throws Exception {
        cleanProjectAtEnd = false;

        // Check all files created correctly.
        checkTestFilesCreated();

        StringBuilder failureLog = new StringBuilder();

        /*
         * Get start events of type none.
         */
        List<Activity> incomingRequestActivities = new ArrayList<Activity>();

        IFile testFile = getTestFile("BusinessService.xpdl"); //$NON-NLS-1$

        WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(testFile);

        if (wc != null && wc.getRootElement() instanceof Package) {

            Package pckg = (Package) wc.getRootElement();

            for (Process process : pckg.getProcesses()) {

                for (Activity activity : Xpdl2ModelUtil
                        .getAllActivitiesInProc(process)) {

                    if (EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL.equals(
                            EventObjectUtil.getEventTriggerType(activity))) {
                        incomingRequestActivities.add(activity);
                    } else if (TaskType.RECEIVE_LITERAL.equals(
                            TaskObjectUtil.getTaskTypeStrict(activity))) {
                        incomingRequestActivities.add(activity);
                    }
                }
            }
        }

        /* Sort the activities into expected order. */
        Collections.sort(incomingRequestActivities, new Comparator<Activity>() {

            @Override
            public int compare(Activity o1, Activity o2) {
                return o1.getName().compareTo(o2.getName());
            }

        });

        IViewPart navigator = PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow().getActivePage()
                .showView("org.eclipse.ui.views.ResourceNavigator"); //$NON-NLS-1$

        /*
         * To avoid the save dialog, set preference
         * 'PREF_DONT_ASK_AGAIN_FOR_SAVE' to true and then save all editors.
         */

        IPreferenceStore prefStore =
                Xpdl2ProcessEditorPlugin.getDefault().getPreferenceStore();
        prefStore.setDefault(
                ProcessEditorConstants.PREF_DONT_ASK_AGAIN_FOR_SAVE,
                true);

        PlatformUI.getWorkbench().saveAllEditors(false);
        PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
                .closeAllEditors(false);

        /*
         * Generate business services for each of the request activity using the
         * context menu GenerateStartEventPageflowAction
         */

        for (final Activity activity : incomingRequestActivities) {

            /*
             * create action to run the context menu for generating the business
             * services
             */

            StructuredSelection selection =
                    new StructuredSelection(new IAdaptable() {

                        @Override
                        public Object getAdapter(Class adapter) {
                            if (Activity.class.equals(adapter)) {
                                return activity;
                            }
                            return null;
                        }
                    });

            final IObjectActionDelegate delegate =
                    new GenerateBizServiceOnIncomingReqActivityAction();

            IAction action = new myAction(delegate);

            delegate.setActivePart(action, navigator);

            delegate.selectionChanged(action, selection);

            delegate.run(action);

            PlatformUI.getWorkbench().saveAllEditors(false);
            PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
                    .closeAllEditors(false);
        }

        /*
         * Get the gold xpdl file and Compare it with the test xpdl file
         */

        IFile goldFile = getTestFile("GoldBusinessService.xpdl"); //$NON-NLS-1$

        failureLog.append(compareGoldAndTest(goldFile, testFile));

        if (failureLog.length() != 0) {
            failureLog.insert(0, "Business service generation test failed:\n"); //$NON-NLS-1$
            fail(failureLog.toString());
        }

        return;
    }

    @Override
    protected String getTestName() {
        return "BizServiceGenerationFromStartEventTest"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.n2.test"; //$NON-NLS-1$
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources = new TestResourceInfo[] {
                new TestResourceInfo(
                        "resources/N2_08_BizServiceGenerationMessageEventTest", //$NON-NLS-1$
                        "N2_08_BusinessServiceGeneration/Business Objects{bom}/N2_08_BizServiceGenerationFromStartEventTest.bom"), //$NON-NLS-1$
                new TestResourceInfo(
                        "resources/N2_08_BizServiceGenerationMessageEventTest", //$NON-NLS-1$
                        "N2_08_BusinessServiceGeneration/Process Packages{processes}/BusinessService.xpdl"), //$NON-NLS-1$
                new TestResourceInfo(
                        "resources/N2_08_BizServiceGenerationMessageEventTest", //$NON-NLS-1$
                        "N2_08_BusinessServiceGeneration/GoldFiles/GoldBusinessService.xpdl"), //$NON-NLS-1$
        };

        return testResources;
    }

    /**
     * Compare the two given xpdl files for generated business Services (ignores
     * other parts of the package)
     * 
     * @param goldFile
     * @param testFile
     * 
     * @return empty string buffer on success else it will contain the failure
     *         state of failed comparisons.
     * @throws IOException
     */
    private StringBuffer compareGoldAndTest(IFile goldFile, IFile testFile)
            throws IOException {

        StringBuffer errorResults = new StringBuffer();

        if (!goldFile.exists()) {
            errorResults.append(String.format("Gold file '%s' is missing.", //$NON-NLS-1$
                    goldFile.getFullPath()));
            errorResults.append(
                    "-------------------------------------------------------------------\n\n"); //$NON-NLS-1$
        }

        if (!testFile.exists()) {
            errorResults.append(String.format("Test file '%s' is missing.", //$NON-NLS-1$
                    testFile.getFullPath()));
            errorResults.append(
                    "-------------------------------------------------------------------\n\n"); //$NON-NLS-1$
        }

        InputStream goldStream = null;
        InputStream testStream = null;

        try {

            goldStream = goldFile.getContents();
            InputSource goldSource = new InputSource(goldStream);

            testStream = testFile.getContents();
            InputSource testSource = new InputSource(testStream);

            XpdlDiff xpdlDiff =
                    new FormatVersionInXpdlDiff(goldSource, testSource);

            if (!xpdlDiff.similar()) {
                errorResults.append(String.format(
                        "Gold file '%1$s' and Test file '%2$s' do not match: ", //$NON-NLS-1$
                        goldFile.getName(),
                        testFile.getName()));
                xpdlDiff.appendMessage(errorResults);
                errorResults.append(
                        "-------------------------------------------------------------------\n\n"); //$NON-NLS-1$

            }

        } catch (Exception e) {
            fail(e.getMessage());
        } finally {
            if (goldStream != null) {
                goldStream.close();
            }
            if (testStream != null) {
                testStream.close();
            }
        }

        return errorResults;
    }

    private class myAction implements IAction {

        private IObjectActionDelegate delegate;

        /**
         * @param delegate
         */
        public myAction(IObjectActionDelegate delegate) {
            this.delegate = delegate;
        }

        /**
         * @see org.eclipse.jface.action.IAction#addPropertyChangeListener(org.eclipse.jface.util.IPropertyChangeListener)
         * 
         * @param listener
         */
        @Override
        public void addPropertyChangeListener(
                IPropertyChangeListener listener) {

        }

        /**
         * @see org.eclipse.jface.action.IAction#getAccelerator()
         * 
         * @return
         */
        @Override
        public int getAccelerator() {

            return 0;
        }

        /**
         * @see org.eclipse.jface.action.IAction#getActionDefinitionId()
         * 
         * @return
         */
        @Override
        public String getActionDefinitionId() {

            return null;
        }

        /**
         * @see org.eclipse.jface.action.IAction#getDescription()
         * 
         * @return
         */
        @Override
        public String getDescription() {

            return null;
        }

        /**
         * @see org.eclipse.jface.action.IAction#getDisabledImageDescriptor()
         * 
         * @return
         */
        @Override
        public ImageDescriptor getDisabledImageDescriptor() {

            return null;
        }

        /**
         * @see org.eclipse.jface.action.IAction#getHelpListener()
         * 
         * @return
         */
        @Override
        public HelpListener getHelpListener() {

            return null;
        }

        /**
         * @see org.eclipse.jface.action.IAction#getHoverImageDescriptor()
         * 
         * @return
         */
        @Override
        public ImageDescriptor getHoverImageDescriptor() {

            return null;
        }

        /**
         * @see org.eclipse.jface.action.IAction#getId()
         * 
         * @return
         */
        @Override
        public String getId() {

            return null;
        }

        /**
         * @see org.eclipse.jface.action.IAction#getImageDescriptor()
         * 
         * @return
         */
        @Override
        public ImageDescriptor getImageDescriptor() {

            return null;
        }

        /**
         * @see org.eclipse.jface.action.IAction#getMenuCreator()
         * 
         * @return
         */
        @Override
        public IMenuCreator getMenuCreator() {

            return null;
        }

        /**
         * @see org.eclipse.jface.action.IAction#getStyle()
         * 
         * @return
         */
        @Override
        public int getStyle() {

            return 0;
        }

        /**
         * @see org.eclipse.jface.action.IAction#getText()
         * 
         * @return
         */
        @Override
        public String getText() {

            return null;
        }

        /**
         * @see org.eclipse.jface.action.IAction#getToolTipText()
         * 
         * @return
         */
        @Override
        public String getToolTipText() {

            return null;
        }

        /**
         * @see org.eclipse.jface.action.IAction#isChecked()
         * 
         * @return
         */
        @Override
        public boolean isChecked() {

            return false;
        }

        /**
         * @see org.eclipse.jface.action.IAction#isEnabled()
         * 
         * @return
         */
        @Override
        public boolean isEnabled() {

            return false;
        }

        /**
         * @see org.eclipse.jface.action.IAction#isHandled()
         * 
         * @return
         */
        @Override
        public boolean isHandled() {

            return false;
        }

        /**
         * @see org.eclipse.jface.action.IAction#removePropertyChangeListener(org.eclipse.jface.util.IPropertyChangeListener)
         * 
         * @param listener
         */
        @Override
        public void removePropertyChangeListener(
                IPropertyChangeListener listener) {

        }

        /**
         * @see org.eclipse.jface.action.IAction#run()
         * 
         */
        @Override
        public void run() {
            delegate.run(this);

        }

        /**
         * @see org.eclipse.jface.action.IAction#runWithEvent(org.eclipse.swt.widgets.Event)
         * 
         * @param event
         */
        @Override
        public void runWithEvent(Event event) {

        }

        /**
         * @see org.eclipse.jface.action.IAction#setActionDefinitionId(java.lang.String)
         * 
         * @param id
         */
        @Override
        public void setActionDefinitionId(String id) {

        }

        /**
         * @see org.eclipse.jface.action.IAction#setChecked(boolean)
         * 
         * @param checked
         */
        @Override
        public void setChecked(boolean checked) {

        }

        /**
         * @see org.eclipse.jface.action.IAction#setDescription(java.lang.String)
         * 
         * @param text
         */
        @Override
        public void setDescription(String text) {

        }

        /**
         * @see org.eclipse.jface.action.IAction#setDisabledImageDescriptor(org.eclipse.jface.resource.ImageDescriptor)
         * 
         * @param newImage
         */
        @Override
        public void setDisabledImageDescriptor(ImageDescriptor newImage) {

        }

        /**
         * @see org.eclipse.jface.action.IAction#setEnabled(boolean)
         * 
         * @param enabled
         */
        @Override
        public void setEnabled(boolean enabled) {

        }

        /**
         * @see org.eclipse.jface.action.IAction#setHelpListener(org.eclipse.swt.events.HelpListener)
         * 
         * @param listener
         */
        @Override
        public void setHelpListener(HelpListener listener) {

        }

        /**
         * @see org.eclipse.jface.action.IAction#setHoverImageDescriptor(org.eclipse.jface.resource.ImageDescriptor)
         * 
         * @param newImage
         */
        @Override
        public void setHoverImageDescriptor(ImageDescriptor newImage) {

        }

        /**
         * @see org.eclipse.jface.action.IAction#setId(java.lang.String)
         * 
         * @param id
         */
        @Override
        public void setId(String id) {

        }

        /**
         * @see org.eclipse.jface.action.IAction#setImageDescriptor(org.eclipse.jface.resource.ImageDescriptor)
         * 
         * @param newImage
         */
        @Override
        public void setImageDescriptor(ImageDescriptor newImage) {

        }

        /**
         * @see org.eclipse.jface.action.IAction#setMenuCreator(org.eclipse.jface.action.IMenuCreator)
         * 
         * @param creator
         */
        @Override
        public void setMenuCreator(IMenuCreator creator) {

        }

        /**
         * @see org.eclipse.jface.action.IAction#setText(java.lang.String)
         * 
         * @param text
         */
        @Override
        public void setText(String text) {

        }

        /**
         * @see org.eclipse.jface.action.IAction#setToolTipText(java.lang.String)
         * 
         * @param text
         */
        @Override
        public void setToolTipText(String text) {

        }

        /**
         * @see org.eclipse.jface.action.IAction#setAccelerator(int)
         * 
         * @param keycode
         */
        @Override
        public void setAccelerator(int keycode) {

        }

    }
}
