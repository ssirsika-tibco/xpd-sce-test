package com.tibco.xpd.simulation.ui;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class SimulationPreferencePage extends PreferencePage implements
        IWorkbenchPreferencePage {

    private IPreferenceStore preferenceStore;
    private BooleanFieldEditor editor;
    
    public SimulationPreferencePage() {
        super();
        preferenceStore=SimulationUIPlugin.getDefault().getPreferenceStore();
    }

    public SimulationPreferencePage(String title) {
        super(title);
        preferenceStore=SimulationUIPlugin.getDefault().getPreferenceStore();
    }

    public SimulationPreferencePage(String title, ImageDescriptor image) {
        super(title, image);
        preferenceStore=SimulationUIPlugin.getDefault().getPreferenceStore();
    }

    protected Control createContents(Composite parent) {
        Composite composite=new Composite(parent,SWT.NONE);
        editor = new BooleanFieldEditor(PreferenceStoreKeys.DONT_ASK_TO_SWITCH_PERSPECTIVE,Messages.SimulationPreferencePage_DontAskToSwitch2,composite);
        editor.setPreferenceStore(preferenceStore);
        editor.load();
        return composite;
    }

    public void init(IWorkbench workbench) {
    }

    /**
     * TODO comment me!
     * @see org.eclipse.jface.preference.PreferencePage#performApply()
     */
    protected void performApply() {
        editor.store();
        super.performApply();
    }


    /**
     * TODO comment me!
     * @see org.eclipse.jface.preference.PreferencePage#performOk()
     */
    public boolean performOk() {
        editor.store();
        return super.performOk();
    }

    
}
