/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.core.test;

import junit.framework.TestCase;

import org.eclipse.jface.preference.IPreferenceStore;

import com.tibco.xpd.validation.preferences.util.ValidationPreferenceUtil;

/**
 * JUnit4 class to test the <code>{@link ValidationPreferenceUtil}</code> class.
 * Following tests are performed:
 * <ul>
 * <li>Set a preference value in the validation preference store</li>
 * <li>Get the preference value from the store</li>
 * <li>Get the default preference value from the store</li>
 * </ul>
 * 
 * @author njpatel
 * 
 */
public class ValidationPreferenceUtilTest extends TestCase {

    private static final String ID = "myId"; //$NON-NLS-1$

    private static final int DEFAULT = 10;

    private static final int VALUE = 24;

    private IPreferenceStore store;

    @Override
    public void setUp() throws Exception {

        store = ValidationPreferenceUtil.getPreferenceStore();

        assertNotNull("Unable to get the validation preference store.", store); //$NON-NLS-1$

        store.setDefault(ValidationPreferenceUtil.getPreferenceKey(ID), DEFAULT);
    }

    public void testSetPreference() {
        ValidationPreferenceUtil.setPreferenceValue(ID, VALUE);

        assertTrue(String.format("Preference value %s not set.", ID), store //$NON-NLS-1$
                .contains(ValidationPreferenceUtil.getPreferenceKey(ID)));

        int value = ValidationPreferenceUtil.getPreferenceValue(ID);

        assertTrue(String.format("Expected %d, got %d.", VALUE, value), //$NON-NLS-1$
                value == VALUE);

        value = ValidationPreferenceUtil.getDefaultPreferenceValue(ID);

        assertTrue(String.format("Expected default value %d, got %d.", DEFAULT, //$NON-NLS-1$
                value), value == DEFAULT);
    }
}
