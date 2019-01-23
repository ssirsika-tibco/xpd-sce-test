/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.n2.test.generate;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.preference.IPreferenceStore;

import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.ui.internal.preferences.BpmPreferenceUtil;
import com.tibco.xpd.bom.test.util.BOMTestUtils;
import com.tibco.xpd.core.test.util.TestUtil;

/**
 * Test the on-demand builder.
 * 
 * @author nwilson
 * @since 15 Aug 2014
 */
public class IncrementalGenTest extends AbstractGenTest {

    /**
     * Process project X depends on BOM project Y which depends on BOM project
     * Z.
     * 
     * @see junit.framework.TestCase#setUp()
     * 
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception {
        enableIncrementalBuild();
        super.setUp();
    }

    /**
     * @see junit.framework.TestCase#tearDown()
     * 
     * @throws Exception
     */
    @Override
    protected void tearDown() throws Exception {
        disableIncrementalBuild();
        super.tearDown();
    }

    private void enableIncrementalBuild() {
        IPreferenceStore store = Activator.getDefault().getPreferenceStore();
        store.setValue(BpmPreferenceUtil.IS_IN_BPM_DEBUG_MODE, true);
    }

    private void disableIncrementalBuild() {
        IPreferenceStore store = Activator.getDefault().getPreferenceStore();
        store.setValue(BpmPreferenceUtil.IS_IN_BPM_DEBUG_MODE, false);
    }

    /**
     * Changing the Y BOM should rebuild Y BDS but not Z.
     */
    public void testZOnChangeToYBom() {
        IProject yTarget = getTargetProject(bomY);
        long timestampY = yTarget.getLocalTimeStamp();
        IProject zTarget = getTargetProject(bomZ);
        long timestampZ = zTarget.getLocalTimeStamp();
        try {
            BOMTestUtils.changePropertyName(propertyY, "yy"); //$NON-NLS-1$
            wcY.save();
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        TestUtil.waitForBuilds(30);
        assertNotEquals(timestampY, yTarget.getLocalTimeStamp());
        assertEquals(timestampZ, zTarget.getLocalTimeStamp());
    }

    /**
     * Changing the Z BOM should rebuild Z BDS followed by Y BDS.
     */
    public void testYOnChangeToZBom() {
        IProject yTarget = getTargetProject(bomY);
        long timestampY = yTarget.getLocalTimeStamp();
        IProject zTarget = getTargetProject(bomZ);
        long timestampZ = zTarget.getLocalTimeStamp();
        try {
            BOMTestUtils.changePropertyName(propertyZ, "zz"); //$NON-NLS-1$
            wcZ.save();
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        TestUtil.waitForBuilds(30);
        assertNotEquals(timestampY, yTarget.getLocalTimeStamp());
        assertNotEquals(timestampZ, zTarget.getLocalTimeStamp());
        assertTrue(yTarget.getLocalTimeStamp() > zTarget.getLocalTimeStamp());
    }

    /**
     * Changing the Y2 BOM should rebuild Y2 BDS but not Y.
     */
    public void testYOnChangeToY2Bom() {
        IProject yTarget = getTargetProject(bomY);
        long timestampY = yTarget.getLocalTimeStamp();
        IProject y2Target = getTargetProject(bomY2);
        long timestampY2 = y2Target.getLocalTimeStamp();
        try {
            BOMTestUtils.changePropertyName(propertyY2, "yy2"); //$NON-NLS-1$
            wcY2.save();
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        TestUtil.waitForBuilds(30);
        assertNotEquals(timestampY2, y2Target.getLocalTimeStamp());
        assertEquals(timestampY, yTarget.getLocalTimeStamp());
    }

    /**
     * Changing the Y BOM should rebuild Y BDS followed by Y2 BDS.
     */
    public void testY2OnChangeToYBom() {
        IProject yTarget = getTargetProject(bomY);
        long timestampY = yTarget.getLocalTimeStamp();
        IProject y2Target = getTargetProject(bomY2);
        long timestampY2 = y2Target.getLocalTimeStamp();
        try {
            BOMTestUtils.changePropertyName(propertyY, "yy"); //$NON-NLS-1$
            wcY.save();
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        TestUtil.waitForBuilds(30);
        assertNotEquals(timestampY, yTarget.getLocalTimeStamp());
        assertNotEquals(timestampY2, y2Target.getLocalTimeStamp());
        assertTrue(y2Target.getLocalTimeStamp() > yTarget.getLocalTimeStamp());
    }
}
