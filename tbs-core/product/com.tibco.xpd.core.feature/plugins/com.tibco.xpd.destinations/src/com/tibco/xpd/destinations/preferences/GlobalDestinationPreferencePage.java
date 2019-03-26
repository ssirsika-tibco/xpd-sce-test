/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.destinations.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.tibco.xpd.destinations.DestinationsActivator;

/**
 * @author nwilson
 * 
 * @deprecated Sid ACE-446 The Global destination configuration page is no
 *             longer required as SCE is for use only for the latest and
 *             greatest ACE runtime - so CE destination is always selected and
 *             never needs to be configured. The contribution has been disabled
 *             in plugin.xml
 */
@Deprecated
public class GlobalDestinationPreferencePage extends FieldEditorPreferencePage
        implements IWorkbenchPreferencePage {

    /**
     * 
     */
    public GlobalDestinationPreferencePage() {
        super(GRID);
        setPreferenceStore(DestinationsActivator.getDefault()
                .getPreferenceStore());
    }

    /**
     * @param title
     */
    public GlobalDestinationPreferencePage(String title) {
        super(title, GRID);
    }

    /**
     * @param title
     * @param image
     */
    public GlobalDestinationPreferencePage(String title, ImageDescriptor image) {
        super(title, image, GRID);
    }

    @Override
    protected void createFieldEditors() {
        addField(new DestinationsEditor(getFieldEditorParent()));
    }

    @Override
    public void init(IWorkbench workbench) {
    }

}
