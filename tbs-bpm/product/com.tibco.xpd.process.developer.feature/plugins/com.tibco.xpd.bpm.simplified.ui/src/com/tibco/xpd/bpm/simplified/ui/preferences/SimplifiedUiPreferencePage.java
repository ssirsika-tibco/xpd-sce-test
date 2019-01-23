package com.tibco.xpd.bpm.simplified.ui.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.tibco.xpd.bpm.simplified.ui.BpmSimplifiedUiActivator;
import com.tibco.xpd.bpm.simplified.ui.internal.Messages;

/**
 * Preference page for controling values of simplified UI preferences.
 *
 * @author jarciuch
 * @since 15 Jun 2015
 */
public class SimplifiedUiPreferencePage extends FieldEditorPreferencePage
        implements IWorkbenchPreferencePage {

    public SimplifiedUiPreferencePage() {
        super(GRID);
        setPreferenceStore(BpmSimplifiedUiActivator.getDefault()
                .getPreferenceStore());
        setDescription(Messages.SimplifiedUiPreferencePage_perspectivePreference_desc);
    }

    /** {@inheritDoc} */
    @Override
    public void createFieldEditors() {
        addField(new BooleanFieldEditor(
                SimplifiedUiPreferenceInitializer.CHANGE_CAPABILITIES_WHEN_SWITCHING_PERSPECTIVE,
                Messages.SimplifiedUiPreferencePage_adjustCapabiliiesWhenSwitchingPerspective_label,
                getFieldEditorParent()));
    }

    /** {@inheritDoc} */
    @Override
    public void init(IWorkbench workbench) {
    }

}