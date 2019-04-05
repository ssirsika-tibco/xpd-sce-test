package com.tibco.xpd.rasc.ui;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.tibco.xpd.rasc.ui.export.AdminUrlPropertyPanel;
import com.tibco.xpd.resources.ui.components.BaseXpdToolkit;
import com.tibco.xpd.ui.properties.XpdWizardToolkit;

public class AdminUrlPreferencePage extends PreferencePage
        implements IWorkbenchPreferencePage {

    private AdminUrlPropertyPanel panel;

    public AdminUrlPreferencePage() {
    }

    public AdminUrlPreferencePage(String title) {
        super(title);
    }

    public AdminUrlPreferencePage(String title, ImageDescriptor image) {
        super(title, image);
    }

    @Override
    public void init(IWorkbench workbench) {
        // Nothing to init.
    }

    @Override
    protected Control createContents(Composite parent) {
        BaseXpdToolkit toolkit = new XpdWizardToolkit(parent);
        panel = new AdminUrlPropertyPanel(toolkit, parent, SWT.NONE);
        return panel;
    }

    /**
     * @see org.eclipse.jface.preference.PreferencePage#performDefaults()
     *
     */
    @Override
    protected void performDefaults() {
        panel.reset();
        super.performDefaults();
    }

    /**
     * @see org.eclipse.jface.preference.PreferencePage#performOk()
     *
     * @return
     */
    @Override
    public boolean performOk() {
        RascUiActivator.getDefault().setAdminBaseUrl(panel.getAdminBaseUrl());
        return super.performOk();
    }

}
