/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.core.test;

import junit.framework.TestCase;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.ui.internal.actions.ActivitiesUtil;
import com.tibco.xpd.ui.preferences.PreferenceStoreKeys;

/**
 * Test to test the Activity Category. The test checks only if the disabling of
 * a categry fails while there are other categories which depends on that
 * category.
 * 
 * @author rassisi
 * 
 */
public class ActivateCapabilityTest extends TestCase {

    private static final String PROJECT_NAME =
            "ActivateCapabilityActionProject";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // 2 lines added so that junit does not wait for user interation.
        IPreferenceStore preferenceStore =
                XpdResourcesUIActivator.getDefault().getPreferenceStore();
        preferenceStore
                .setValue(PreferenceStoreKeys.DONT_ASK_TO_BUILD_WHEN_CHANGING_CAPABILITIES,
                        Boolean.TRUE);
        TestUtil.createProject(PROJECT_NAME);
    }

    @Override
    protected void tearDown() throws Exception {
        TestUtil.removeProject(PROJECT_NAME);
        TestUtil.waitForJobs();
        super.tearDown();
    }

    /**
     * 
     * @throws Exception
     */
    @SuppressWarnings("restriction")
    public void testActivateCapabilityTest() throws Exception {
        ActivitiesUtil activitiesUtil =
                new ActivitiesUtil(PlatformUI.getWorkbench()
                        .getActiveWorkbenchWindow());
        ActivitiesUtil.setTestMode(true);
        activitiesUtil.setActivity(true, "com.tibco.activity1", "");
        activitiesUtil.setActivity(true, "com.tibco.activity2", "");
        activitiesUtil.setActivity(false, "com.tibco.activity1", "");
        String result = ActivitiesUtil.getTestBuffer();
        assertTrue(result.indexOf("Category Category1") != -1);
        activitiesUtil.setActivity(false, "com.tibco.activity2", "");
        result = ActivitiesUtil.getTestBuffer();
        assertTrue(result == null);
    }

}
