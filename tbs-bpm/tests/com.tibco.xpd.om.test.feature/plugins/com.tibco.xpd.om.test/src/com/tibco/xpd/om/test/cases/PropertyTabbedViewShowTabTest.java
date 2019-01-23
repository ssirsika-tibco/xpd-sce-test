/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.om.test.cases;

import java.util.Arrays;

import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.test.AbstractOMTestCase;
import com.tibco.xpd.om.test.OMTestUtil;

/**
 * This test is intended to detect any change of the API of the
 * org.eclipse.ui.internal.views.properties.tabbed.view.TabbedPropertyList, as
 * its restricted methods will be called by reflection. Though this code is
 * working reliable now, any API changes would need to be detected to avoid any
 * runtime errors by following those changes. It is important to know that this
 * test does not test the method itself. It checks only if the method finds the
 * needed classes by the reflection API in order to work properly.
 * 
 * @author rassisi
 * 
 */
public class PropertyTabbedViewShowTabTest extends AbstractOMTestCase {

    private OrgModel model;

    @SuppressWarnings("nls")
    public PropertyTabbedViewShowTabTest() {
        TEST_PROJECT_NAME = "TestPropertyTabbedViewShowTab";
        TEST_FILE_NAME = "test.om";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.om.test.AbstractOMTestCase#setUp()
     */
    @SuppressWarnings("nls")
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        closeWelcomePage();
        model = factory.createOrgModel();
        model.setName("OrgModel");
        OMTestUtil.createAndSaveResource(testResourceURI, Arrays.asList(model),
                null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.om.test.AbstractOMTestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        model = null;
        // Nothing to do as all cleaning is done in the superclass.
    }

    /**
     * @throws RollbackException
     * @throws InterruptedException
     * 
     */
    @SuppressWarnings("nls")
    public void testPropertyTabbedViewShowTab() throws InterruptedException,
            RollbackException {
        System.out
                .println("-->Testing the PropertyTabbedView.showTab() method");

        TestUtil.delay(3000);

        try {
            PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                    .getActivePage().showView(
                            "org.eclipse.ui.views.PropertySheet");
        } catch (PartInitException e) {
            fail("The Property Sheet View could not be shown.");
        }

        TestUtil.delay(3000);

        getProjectView().setFocus();

        // selectPathInProjectExplorer(new Object[] { project });

        TestUtil.delay(3000);

        // XpdPropertiesUtil.showTabMethodWasCalledSuccessfully = false;
        // XpdPropertiesUtil._showTab("Advanced");
        // assertTrue(
        // "The XpdPropertiesUtil.showTab(String) method could not be called any more (possibly due to an API change."
        // ,
        // XpdPropertiesUtil.showTabMethodWasCalledSuccessfully);

    }

}
