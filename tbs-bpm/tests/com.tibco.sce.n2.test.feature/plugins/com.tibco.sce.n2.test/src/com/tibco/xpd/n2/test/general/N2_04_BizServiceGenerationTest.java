/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.test.general;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

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
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;

/**
 * BizServiceGenerationTest - Generate business services for various
 * request-only activities inside a package and compare the resulting package
 * file with the gold package file to test that the expected business services
 * have been successfully generated.
 * 
 * @author agondal
 * @since 12 Feb 2013
 */
public class N2_04_BizServiceGenerationTest extends AbstractN2BaseResourceTest {

    /**
     * testBizServiceGenerationTest
     * 
     * @throws Exception
     */
    public void testBizServiceGenerationTest() throws Exception {
        // Check all files created correctly.
        checkTestFilesCreated();

        StringBuilder failureLog = new StringBuilder();

        /* Get all the incoming web service request activities in the package */

        Set<Activity> requestActivities = new HashSet<Activity>();

        IFile testFile =
                getTestFile("UC13_BusinessServiceForEventHandlers.xpdl"); //$NON-NLS-1$

        WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(testFile);

        if (wc != null && wc.getRootElement() instanceof Package) {

            Package pckg = (Package) wc.getRootElement();

            for (Process process : pckg.getProcesses()) {

                for (Activity activity : process.getActivities()) {

                    if (ReplyActivityUtil.isIncomingRequestActivity(activity)) {

                        requestActivities.add(activity);
                    }
                }
            }
        }

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

        for (final Activity activity : requestActivities) {

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

        IFile goldFile =
                getTestFile("UC13_BusinessServiceForEventHandlersGold.xpdl"); //$NON-NLS-1$

        failureLog.append(compareGoldAndTest(goldFile, testFile));

        if (failureLog.length() != 0) {
            failureLog.insert(0, "Business service generation test failed:\n"); //$NON-NLS-1$
            fail(failureLog.toString());
        }

        return;
    }

    @Override
    protected String getTestName() {
        return "BizServiceGenerationTest"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.n2.test"; //$NON-NLS-1$
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources = new TestResourceInfo[] {
                new TestResourceInfo("resources/BizServiceGenerationTest", //$NON-NLS-1$
                        "UC13_BusinessServiceForEventHandlers/Process Packages{processes}/UC13_BusinessServiceForEventHandlers.xpdl"), //$NON-NLS-1$
                new TestResourceInfo("resources/BizServiceGenerationTest", //$NON-NLS-1$
                        "UC13_BusinessServiceForEventHandlers/GoldFiles/UC13_BusinessServiceForEventHandlersGold.xpdl"), //$NON-NLS-1$
                new TestResourceInfo("resources/BizServiceGenerationTest", //$NON-NLS-1$
                        "UC13_BusinessServiceForEventHandlers/Service Descriptors/RequestOnlyDocLiteral.wsdl"), //$NON-NLS-1$
                new TestResourceInfo("resources/BizServiceGenerationTest", //$NON-NLS-1$
                        "UC13_BusinessServiceForEventHandlers/Service Descriptors/RequestOnlyDocLiteral2.wsdl"), //$NON-NLS-1$
                new TestResourceInfo("resources/BizServiceGenerationTest", //$NON-NLS-1$
                        "UC13_BusinessServiceForEventHandlers/Service Descriptors/RequestOnlyRpcLiteral.wsdl"), //$NON-NLS-1$
                new TestResourceInfo("resources/BizServiceGenerationTest", //$NON-NLS-1$
                        "UC13_BusinessServiceForEventHandlers/Service Descriptors/RequestOnlyRpcLiteral2.wsdl"), //$NON-NLS-1$
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
            // TODO Auto-generated method stub

        }

        /**
         * @see org.eclipse.jface.action.IAction#getAccelerator()
         * 
         * @return
         */
        @Override
        public int getAccelerator() {
            // TODO Auto-generated method stub
            return 0;
        }

        /**
         * @see org.eclipse.jface.action.IAction#getActionDefinitionId()
         * 
         * @return
         */
        @Override
        public String getActionDefinitionId() {
            // TODO Auto-generated method stub
            return null;
        }

        /**
         * @see org.eclipse.jface.action.IAction#getDescription()
         * 
         * @return
         */
        @Override
        public String getDescription() {
            // TODO Auto-generated method stub
            return null;
        }

        /**
         * @see org.eclipse.jface.action.IAction#getDisabledImageDescriptor()
         * 
         * @return
         */
        @Override
        public ImageDescriptor getDisabledImageDescriptor() {
            // TODO Auto-generated method stub
            return null;
        }

        /**
         * @see org.eclipse.jface.action.IAction#getHelpListener()
         * 
         * @return
         */
        @Override
        public HelpListener getHelpListener() {
            // TODO Auto-generated method stub
            return null;
        }

        /**
         * @see org.eclipse.jface.action.IAction#getHoverImageDescriptor()
         * 
         * @return
         */
        @Override
        public ImageDescriptor getHoverImageDescriptor() {
            // TODO Auto-generated method stub
            return null;
        }

        /**
         * @see org.eclipse.jface.action.IAction#getId()
         * 
         * @return
         */
        @Override
        public String getId() {
            // TODO Auto-generated method stub
            return null;
        }

        /**
         * @see org.eclipse.jface.action.IAction#getImageDescriptor()
         * 
         * @return
         */
        @Override
        public ImageDescriptor getImageDescriptor() {
            // TODO Auto-generated method stub
            return null;
        }

        /**
         * @see org.eclipse.jface.action.IAction#getMenuCreator()
         * 
         * @return
         */
        @Override
        public IMenuCreator getMenuCreator() {
            // TODO Auto-generated method stub
            return null;
        }

        /**
         * @see org.eclipse.jface.action.IAction#getStyle()
         * 
         * @return
         */
        @Override
        public int getStyle() {
            // TODO Auto-generated method stub
            return 0;
        }

        /**
         * @see org.eclipse.jface.action.IAction#getText()
         * 
         * @return
         */
        @Override
        public String getText() {
            // TODO Auto-generated method stub
            return null;
        }

        /**
         * @see org.eclipse.jface.action.IAction#getToolTipText()
         * 
         * @return
         */
        @Override
        public String getToolTipText() {
            // TODO Auto-generated method stub
            return null;
        }

        /**
         * @see org.eclipse.jface.action.IAction#isChecked()
         * 
         * @return
         */
        @Override
        public boolean isChecked() {
            // TODO Auto-generated method stub
            return false;
        }

        /**
         * @see org.eclipse.jface.action.IAction#isEnabled()
         * 
         * @return
         */
        @Override
        public boolean isEnabled() {
            // TODO Auto-generated method stub
            return false;
        }

        /**
         * @see org.eclipse.jface.action.IAction#isHandled()
         * 
         * @return
         */
        @Override
        public boolean isHandled() {
            // TODO Auto-generated method stub
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
            // TODO Auto-generated method stub

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
            // TODO Auto-generated method stub

        }

        /**
         * @see org.eclipse.jface.action.IAction#setActionDefinitionId(java.lang.String)
         * 
         * @param id
         */
        @Override
        public void setActionDefinitionId(String id) {
            // TODO Auto-generated method stub

        }

        /**
         * @see org.eclipse.jface.action.IAction#setChecked(boolean)
         * 
         * @param checked
         */
        @Override
        public void setChecked(boolean checked) {
            // TODO Auto-generated method stub

        }

        /**
         * @see org.eclipse.jface.action.IAction#setDescription(java.lang.String)
         * 
         * @param text
         */
        @Override
        public void setDescription(String text) {
            // TODO Auto-generated method stub

        }

        /**
         * @see org.eclipse.jface.action.IAction#setDisabledImageDescriptor(org.eclipse.jface.resource.ImageDescriptor)
         * 
         * @param newImage
         */
        @Override
        public void setDisabledImageDescriptor(ImageDescriptor newImage) {
            // TODO Auto-generated method stub

        }

        /**
         * @see org.eclipse.jface.action.IAction#setEnabled(boolean)
         * 
         * @param enabled
         */
        @Override
        public void setEnabled(boolean enabled) {
            // TODO Auto-generated method stub

        }

        /**
         * @see org.eclipse.jface.action.IAction#setHelpListener(org.eclipse.swt.events.HelpListener)
         * 
         * @param listener
         */
        @Override
        public void setHelpListener(HelpListener listener) {
            // TODO Auto-generated method stub

        }

        /**
         * @see org.eclipse.jface.action.IAction#setHoverImageDescriptor(org.eclipse.jface.resource.ImageDescriptor)
         * 
         * @param newImage
         */
        @Override
        public void setHoverImageDescriptor(ImageDescriptor newImage) {
            // TODO Auto-generated method stub

        }

        /**
         * @see org.eclipse.jface.action.IAction#setId(java.lang.String)
         * 
         * @param id
         */
        @Override
        public void setId(String id) {
            // TODO Auto-generated method stub

        }

        /**
         * @see org.eclipse.jface.action.IAction#setImageDescriptor(org.eclipse.jface.resource.ImageDescriptor)
         * 
         * @param newImage
         */
        @Override
        public void setImageDescriptor(ImageDescriptor newImage) {
            // TODO Auto-generated method stub

        }

        /**
         * @see org.eclipse.jface.action.IAction#setMenuCreator(org.eclipse.jface.action.IMenuCreator)
         * 
         * @param creator
         */
        @Override
        public void setMenuCreator(IMenuCreator creator) {
            // TODO Auto-generated method stub

        }

        /**
         * @see org.eclipse.jface.action.IAction#setText(java.lang.String)
         * 
         * @param text
         */
        @Override
        public void setText(String text) {
            // TODO Auto-generated method stub

        }

        /**
         * @see org.eclipse.jface.action.IAction#setToolTipText(java.lang.String)
         * 
         * @param text
         */
        @Override
        public void setToolTipText(String text) {
            // TODO Auto-generated method stub

        }

        /**
         * @see org.eclipse.jface.action.IAction#setAccelerator(int)
         * 
         * @param keycode
         */
        @Override
        public void setAccelerator(int keycode) {
            // TODO Auto-generated method stub

        }

    }

}
