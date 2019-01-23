/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.annotation.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;

import com.tibco.xpd.simulation.annotation.Messages;
import com.tibco.xpd.simulation.annotation.SimulationAnnotationPlugin;

/**
 * This class represents a preference page that is contributed to the
 * Preferences dialog. By subclassing <samp>FieldEditorPreferencePage</samp>,
 * we can use the field support built into JFace that allows us to create a page
 * that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the
 * preference store that belongs to the main plug-in class. That way,
 * preferences can be accessed directly via the preference store.
 */

public class AnnotationSelectionPage extends FieldEditorPreferencePage
        implements IWorkbenchPreferencePage {

    /** The page description. */
    private String description;

    /** Observed cases field label. */
    private String observedLabel;

    /** Current queue size field label. */
    private String currentLabel;

    /** Activity delay field label. */
    private String delayLabel;

    /** Participant utilisation field label. */
    private String utilisationLabel;

    /**
     * Constructor.
     */
    public AnnotationSelectionPage() {
        super(GRID);
        setPreferenceStore(SimulationAnnotationPlugin.getDefault()
                .getPreferenceStore());
        description = Messages.getBundle().getString("description"); //$NON-NLS-1$
        observedLabel = Messages.getBundle().getString("observedLabel"); //$NON-NLS-1$
        currentLabel = Messages.getBundle().getString("currentLabel"); //$NON-NLS-1$
        delayLabel = Messages.getBundle().getString("delayLabel"); //$NON-NLS-1$
        utilisationLabel = Messages.getBundle().getString("utilisationLabel"); //$NON-NLS-1$
        setDescription(description);
    }

    /**
     * Creates the field editors. Field editors are abstractions of the common
     * GUI blocks needed to manipulate various types of preferences. Each field
     * editor knows how to save and restore itself.
     */
    public void createFieldEditors() {
        addField(new BooleanFieldEditor(PreferenceConstants.P_OBSERVED,
                observedLabel, getFieldEditorParent()));
        addField(new BooleanFieldEditor(PreferenceConstants.P_CURRENT,
                currentLabel, getFieldEditorParent()));
        addField(new BooleanFieldEditor(PreferenceConstants.P_DELAY,
                delayLabel, getFieldEditorParent()));
        addField(new BooleanFieldEditor(PreferenceConstants.P_UTILISATION,
                utilisationLabel, getFieldEditorParent()));
    }

    /**
     * @param workbench The workbench.
     * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
     */
    public void init(IWorkbench workbench) {
    }

}
