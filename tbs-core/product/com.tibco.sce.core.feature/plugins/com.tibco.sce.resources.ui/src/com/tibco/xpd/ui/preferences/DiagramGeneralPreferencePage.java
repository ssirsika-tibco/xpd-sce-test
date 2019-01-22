/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.preferences;

import org.eclipse.gmf.runtime.diagram.ui.preferences.DiagramsPreferencePage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.internal.Messages;

/**
 * Creates Diagram top-level Preference page
 * 
 * <p>
 * <i>Created: 21 June 2007</i>
 * 
 * @author rgreen
 * 
 */
public class DiagramGeneralPreferencePage extends DiagramsPreferencePage {

    /**
     * Creates a new instance and initializes the preference store.
     */
    public DiagramGeneralPreferencePage() {
        super();
        setPreferenceStore(XpdResourcesUIActivator.getDefault()
                .getPreferenceStore());

    }

    /*
     * Kapil:XPD-4483: Diagram Global Settings page removed from Diagram
     * Preference page.
     */
    @Override
    protected Control createContents(Composite parent) {
        // TODO Auto-generated method stub
        return createNoteComposite(getFont(), parent, "", //$NON-NLS-1$
                Messages.DiagramGeneralPreferencePage_DiagramPreferencePage_Label);
    }
}
