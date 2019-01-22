/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.dialogs.PreferencesUtil;

/**
 * Action to show the Eclipse Preferences dialog.
 * 
 * @author njpatel
 */
public class ShowPreferencesAction extends Action {

    private final IWorkbenchWindow window;

    private String defaultPage =
            "com.tibco.xpd.resources.ui.userInfoPreferencePage"; //$NON-NLS-1$

    /**
     * Pages to show in the preference dialog
     */
    private static final String[] PREFERENCE_PAGES = new String[] { //
            // Business Object Model Diagram
                    "com.tibco.xpd.bom.modeler.diagram.general", //$NON-NLS-1$
                    // Appearance
                    "com.tibco.xpd.bom.modeler.diagram.appearance", //$NON-NLS-1$
                    // Connections
                    "com.tibco.xpd.bom.modeler.diagram.connections", //$NON-NLS-1$
                    // Pathmaps
                    "com.tibco.xpd.bom.modeler.diagram.pathmaps", //$NON-NLS-1$
                    // Process Modeler
                    "com.tibco.xpd.processeditor.xpdl2.process_editor", //$NON-NLS-1$
                    // Errors/Warnings
                    "com.tibco.xpd.processeditor.xpdl2.err_warn", //$NON-NLS-1$
                    // Organization Model Diagram
                    "com.tibco.xpd.om.core.diagram.general", //$NON-NLS-1$
                    // Appearance
                    "com.tibco.xpd.om.core.diagram.appearance", //$NON-NLS-1$
                    // Connections
                    "com.tibco.xpd.om.core.diagram.connections", //$NON-NLS-1$
                    // Printing
                    "com.tibco.xpd.om.core.diagram.printing", //$NON-NLS-1$
                    // Rulers And Grid
                    "com.tibco.xpd.om.core.diagram.rulersAndGrid", //$NON-NLS-1$
                    // Pathmaps
                    "com.tibco.xpd.om.core.diagram.pathmaps", //$NON-NLS-1$
                    // Printing
                    "com.tibco.xpd.resources.ui.DiagramPrintingPreferencePage", //$NON-NLS-1$
                    // Ruler and Grid
                    "com.tibco.xpd.resources.ui.DiagramRulerAndGridPreferencePage", //$NON-NLS-1$
                    // Diagram
                    "com.tibco.xpd.resources.ui.DiagramGeneralPreferencePage", //$NON-NLS-1$
                    // User Profile
                    "com.tibco.xpd.resources.ui.userInfoPreferencePage", //$NON-NLS-1$
                    // Business Object Modeler
                    "com.tibco.xpd.bom.pref", //$NON-NLS-1$
                    // Organization Diagram
                    "com.tibco.xpd.om.core.subdiagram.general", //$NON-NLS-1$
                    // Appearance
                    "com.tibco.xpd.om.core.subdiagram.appearance", //$NON-NLS-1$
                    // Connections
                    "com.tibco.xpd.om.core.subdiagram.connections", //$NON-NLS-1$
                    // Printing
                    "com.tibco.xpd.om.core.subdiagram.printing", //$NON-NLS-1$
                    // Rulers And Grid
                    "com.tibco.xpd.om.core.subdiagram.rulersAndGrid", //$NON-NLS-1$
                    // Pathmaps
                    "com.tibco.xpd.om.core.subdiagram.pathmaps", //$NON-NLS-1$
                    // Process Documentation
                    "com.tibco.xpd.procdoc.preferences.procDocPreferencePage", //$NON-NLS-1$
                    // Errors/Warnings
                    "com.tibco.xpd.bom.validator.preferences.BOMValidationPreferencePage", //$NON-NLS-1$
                    // Network preferences (eg proxy)
                    "org.eclipse.ui.net.NetPreferences", //$NON-NLS-1$
                    // Forms designer
                    "com.tibco.xpd.forms.designer.editor.preferences.DesignerPreferencePage", //$NON-NLS-1$
                    // Forms generator
                    "com.tibco.xpd.forms.designer.resources.ui.formgen", //$NON-NLS-1$
                    // Help preferences
                    "com.tibco.xpd.core.help.internal.preferences.XpdHelpPreferencePage", //$NON-NLS-1$
                    // Help contents
                    "com.tibco.xpd.core.help.internal.preferences.XpdHelpContentPreferencePage", //$NON-NLS-1$
            };

    public ShowPreferencesAction(IWorkbenchWindow window) {
        this.window = window;
    }

    @Override
    public void run() {
        PreferenceDialog dlg =
                PreferencesUtil.createPreferenceDialogOn(window.getShell(),
                        defaultPage,
                        PREFERENCE_PAGES,
                        null);
        /*
         * Set the default page to null so that the next time the preference
         * dialog is shown it will select the last page the user had selected.
         */
        defaultPage = null;
        dlg.open();
    }

    /* package */static final String[] getDisplayedPreferencePagesIds() {
        return PREFERENCE_PAGES;
    }
}
