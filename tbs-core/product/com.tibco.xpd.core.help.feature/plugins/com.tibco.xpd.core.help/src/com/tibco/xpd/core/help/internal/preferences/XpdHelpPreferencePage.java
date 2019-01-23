package com.tibco.xpd.core.help.internal.preferences;

import java.util.Iterator;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PreferenceLinkArea;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;
import org.osgi.service.prefs.BackingStoreException;

import com.tibco.xpd.core.help.CoreHelpActivator;
import com.tibco.xpd.core.help.internal.Messages;
import com.tibco.xpd.core.help.internal.preferences.XpdHelpPreferences.OpenMode;

/**
 * Main Studio help (TIBCO Help) preference page.
 * 
 * @author jarciuch
 * @since 4 Aug 2014
 * 
 * @deprecated Sid XPD-8237 - we no longer show users this page as the internal
 *             browser is very out of date and won't cope wth latest help pages.
 *             So now we force to open in external browser.
 */
@Deprecated
public class XpdHelpPreferencePage extends PreferencePage implements
        IWorkbenchPreferencePage {

    private static final String WEB_BROWSER_PAGE_ID =
            "org.eclipse.ui.browser.preferencePage";//$NON-NLS-1$

    private Combo showProductPageCombo;

    private Combo showHelpContentCombo;

    /**
     * Creates preference page controls on demand.
     * 
     * @param parent
     *            the parent for the preference page
     */
    @Override
    protected Control createContents(Composite parent) {
        Composite mainComposite = new Composite(parent, SWT.NONE);
        GridData data = new GridData();
        data.verticalAlignment = GridData.FILL;
        data.horizontalAlignment = GridData.FILL;
        mainComposite.setLayoutData(data);
        GridLayout layout = new GridLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        mainComposite.setLayout(layout);
        Label description = new Label(mainComposite, SWT.NONE);
        description
                .setText(Messages.XpdHelpPreferencePage_helpDisplayInfo_message);

        createOpenModesPrefs(mainComposite);
        createSpacer(mainComposite);
        Dialog.applyDialogFont(mainComposite);
        return mainComposite;
    }

    private void createOpenModesPrefs(Composite parent) {
        Group group = new Group(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        group.setLayout(layout);
        group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        group.setText(Messages.XpdHelpPreferencePage_openModes_label);
        createHelpContentPrefs(group);
        createProductPagePrefs(group);
        createLinkArea(group);
    }

    private void createHelpContentPrefs(Composite mainComposite) {
        Label searchLocationLabel = new Label(mainComposite, SWT.NONE);
        searchLocationLabel
                .setText(Messages.XpdHelpPreferencePage_openProductHelpContent_label);
        searchLocationLabel.setLayoutData(createLabelData());
        showHelpContentCombo = new Combo(mainComposite, SWT.READ_ONLY);
        showHelpContentCombo.add(OpenMode.IN_HELP_VIEW.getLabel());
        showHelpContentCombo.add(OpenMode.IN_BROWSER.getLabel());
        showHelpContentCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
                true, false));
        showHelpContentCombo.setText(XpdHelpPreferences
                .getHelpContentOpenPreference().getLabel());
    }

    private void createProductPagePrefs(Composite mainComposite) {
        Label isExternalLabel = new Label(mainComposite, SWT.NONE);
        isExternalLabel
                .setText(Messages.XpdHelpPreferencePage_openProductHelpPage_label);
        isExternalLabel.setLayoutData(createLabelData());
        showProductPageCombo = new Combo(mainComposite, SWT.READ_ONLY);
        showProductPageCombo.add(OpenMode.IN_HELP_VIEW.getLabel());
        showProductPageCombo.add(OpenMode.IN_BROWSER.getLabel());
        showProductPageCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
                true, false));
        showProductPageCombo.setText(XpdHelpPreferences
                .getProductPageOpenPreference().getLabel());
    }

    private void createLinkArea(Composite parent) {
        IPreferenceNode node = getPreferenceNode(WEB_BROWSER_PAGE_ID);
        if (node != null) {
            PreferenceLinkArea linkArea =
                    new PreferenceLinkArea(parent, SWT.WRAP,
                            WEB_BROWSER_PAGE_ID,
                            Messages.XpdHelpPreferencePage_browserLink_message,
                            (IWorkbenchPreferenceContainer) getContainer(),
                            null);
            GridData data =
                    new GridData(GridData.FILL_HORIZONTAL
                            | GridData.GRAB_HORIZONTAL);
            linkArea.getControl().setLayoutData(data);
        }
    }

    private GridData createLabelData() {
        GridData data = new GridData(SWT.FILL, SWT.CENTER, true, false);
        data.verticalIndent = 5;
        return data;
    }

    private IPreferenceNode getPreferenceNode(String pageId) {
        Iterator<?> iterator =
                PlatformUI.getWorkbench().getPreferenceManager()
                        .getElements(PreferenceManager.PRE_ORDER).iterator();
        while (iterator.hasNext()) {
            IPreferenceNode next = (IPreferenceNode) iterator.next();
            if (next.getId().equals(pageId))
                return next;
        }
        return null;
    }

    @Override
    public void init(IWorkbench workbench) {
    }

    @Override
    protected void performDefaults() {
        showHelpContentCombo.setText(XpdHelpPreferences
                .getDefaultHelpContentOpenPreference().getLabel());
        showProductPageCombo.setText(XpdHelpPreferences
                .getDefaultProductPageOpenPreference().getLabel());
        super.performDefaults();
    }

    @Override
    public boolean performOk() {
        IScopeContext instanceScope = InstanceScope.INSTANCE;

        IEclipsePreferences pref =
                instanceScope.getNode(CoreHelpActivator.PLUGIN_ID);

        OpenMode helpContentOpenMode =
                OpenMode.getFromLabel(showHelpContentCombo.getText());
        pref.put(XpdHelpPreferences.P_SHOW_HELP_CONTENT,
                helpContentOpenMode.toString());

        OpenMode productPageOpenMode =
                OpenMode.getFromLabel(showProductPageCombo.getText());
        pref.put(XpdHelpPreferences.P_SHOW_PRODUCT_PAGE,
                productPageOpenMode.toString());

        try {
            pref.flush();
        } catch (BackingStoreException e) {
            e.printStackTrace();
        }

        return true;
    }

    private void createSpacer(Composite parent) {
        Label spacer = new Label(parent, SWT.NONE);
        GridData data = new GridData();
        data.horizontalAlignment = GridData.FILL;
        data.verticalAlignment = GridData.BEGINNING;
        spacer.setLayoutData(data);
    }

}