/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.core.test;

import java.util.Collections;
import java.util.List;

import com.tibco.xpd.destinations.DestinationsActivator;
import com.tibco.xpd.destinations.preferences.DestinationPreferences;
import com.tibco.xpd.destinations.preferences.DestinationPreferencesCodec;
import com.tibco.xpd.destinations.preferences.DestinationSetting;

import junit.framework.TestCase;

/**
 * @author NWilson
 * 
 */
public class DestinationsTest extends TestCase {

    public void testDestinationPreferencesCodecSimple() {
        String name = "simple"; //$NON-NLS-1$
        String persisted = "simple"; //$NON-NLS-1$
        checkCodec(name, persisted);
    }

    public void testDestinationPreferencesCodecSimpleCoded() {
        String name = "a:b"; //$NON-NLS-1$
        String persisted = "a/:b"; //$NON-NLS-1$
        checkCodec(name, persisted);
    }

    public void testDestinationPreferencesCodecCoded() {
        String name = "/,;:|"; //$NON-NLS-1$
        String persisted = "///,/;/:/|"; //$NON-NLS-1$
        checkCodec(name, persisted);
    }

    public void testDefaultNameSequence() {
        DestinationPreferences preferences =
                DestinationsActivator.getDefault().getDestinationPreferences();
        DestinationSetting setting1 = preferences.addGlobalDestination();
        assertEquals("newDestination", setting1.getName());
        DestinationSetting setting2 = preferences.addGlobalDestination();
        assertEquals("newDestination1", setting2.getName());
        preferences.removeGlobalDestination("newDestination");
        DestinationSetting setting3 = preferences.addGlobalDestination();
        assertEquals("newDestination2", setting3.getName());
    }

    public void testDuplicateName() {
        DestinationPreferences preferences =
                DestinationsActivator.getDefault().getDestinationPreferences();
        DestinationSetting setting1 = preferences.addGlobalDestination();
        preferences.renameGlobalDestination(setting1.getName(), "name");
        DestinationSetting setting2 = preferences.addGlobalDestination();
        preferences.renameGlobalDestination(setting2.getName(), "old");
        preferences.renameGlobalDestination(setting2.getName(), "name");
        assertEquals("old", setting2.getName());
    }

    public void testEmptyName() {
        DestinationPreferences preferences =
                DestinationsActivator.getDefault().getDestinationPreferences();
        DestinationSetting setting1 = preferences.addGlobalDestination();
        preferences.renameGlobalDestination(setting1.getName(), "emptyname");
        preferences.renameGlobalDestination("emptyname", "");
        assertEquals("emptyname", setting1.getName());
    }

    private void checkCodec(String name, String persisted) {
        DestinationSetting setting = new DestinationSetting(name);
        String store =
                DestinationPreferencesCodec.encode(Collections
                        .singletonList(setting));
        assertNotNull(store);
        assertTrue(store.startsWith(persisted));
        List<DestinationSetting> settings =
                DestinationPreferencesCodec.decode(store);
        assertEquals(1, settings.size());
        setting = settings.get(0);
        assertEquals(name, setting.getName());
    }
}
