package com.tibco.xpd.rasc.ui;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.tibco.xpd.rasc.ui.export.AdminUrlPropertyPanel;
import com.tibco.xpd.rasc.ui.internal.Messages;
import com.tibco.xpd.resources.ui.components.BaseXpdToolkit;
import com.tibco.xpd.ui.properties.XpdWizardToolkit;

/**
 * Preference page for setting the Admin URL.
 *
 * @author nwilson
 * @since 9 Apr 2019
 */
public class AdminUrlPreferencePage extends PreferencePage
        implements IWorkbenchPreferencePage {

    /**
     * URL property panel, also used in export admin URL launch dialog.
     */
    private AdminUrlPropertyPanel panel;

    /**
     * Checkbox for hiding the lock confirmation dialog.
     */
    private Button showLockConfirmationDialog;

    /**
     * Checkbox for hiding the create new draft confirmation dialog.
     */
    private Button showCreateNewDraftConfirmationDialog;

    /**
     * Checkbox for hiding the admin URL dialog.
     */
    private Button showAdminUrl;

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
        Composite page = toolkit.createComposite(parent);
        // GridLayout pageLayout = new GridLayout();
        // pageLayout.marginHeight = 0;
        // pageLayout.marginWidth = 0;
        // page.setLayout(pageLayout);
        page.setLayout(new RowLayout(SWT.VERTICAL));

        panel = new AdminUrlPropertyPanel(toolkit, page, SWT.NONE);
        // panel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        boolean showAminUrlDialog =
                !RascUiActivator.getDefault().getHideAdminUrlDialog();
        showAdminUrl = toolkit.createCheckbox(page,
                showAminUrlDialog,
                Messages.AdminUrlPreferencePage_ShowAdminUrlDialog,
                RascUiActivator.HIDE_ADMIN_BASE_URL);

        boolean showLockConfirmation = !RascUiActivator.getDefault().getHideLockConfirmationDialog();
        showLockConfirmationDialog = toolkit.createCheckbox(page,
                showLockConfirmation,
                Messages.RascUi_ShowLockConfirmationDialog,
                RascUiActivator.HIDE_LOCK_CONFIRMATION);

        boolean showCreateNewDraftConfirmation =
                !RascUiActivator.getDefault().getHideCreateNewDraftConfirmationDialog();
        showCreateNewDraftConfirmationDialog = toolkit.createCheckbox(page,
                showCreateNewDraftConfirmation,
                Messages.RascUi_ShowCreateNewDraftConfirmationDialog,
                RascUiActivator.HIDE_CREATE_NEW_DRAFT_CONFIRMATION);
        // showAdminUrl.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true,
        // true));
        return page;
    }

    /**
     * @see org.eclipse.jface.preference.PreferencePage#performDefaults()
     *
     */
    @Override
    protected void performDefaults() {
        panel.reset();
        showAdminUrl.setSelection(
                !RascUiActivator.getDefault().getHideAdminUrlDialog());
        showLockConfirmationDialog.setSelection(!RascUiActivator.getDefault().getHideLockConfirmationDialog());
        showCreateNewDraftConfirmationDialog
                .setSelection(!RascUiActivator.getDefault().getHideCreateNewDraftConfirmationDialog());
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
        RascUiActivator.getDefault()
                .setHideAdminUrlDialog(!showAdminUrl.getSelection());
        RascUiActivator.getDefault().setHideLockConfirmationDialog(!showLockConfirmationDialog.getSelection());
        RascUiActivator.getDefault()
                .setHideCreateNewDraftConfirmationDialog(!showCreateNewDraftConfirmationDialog.getSelection());
        return super.performOk();
    }

}
