package com.tibco.xpd.ui.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.internal.Messages;

public class ValidationBuildPreferencePage extends PreferencePage implements
        IWorkbenchPreferencePage {
    
    private BooleanFieldEditor preferencesEditor;

    private BooleanFieldEditor capabilitiesEditor;
    
    private IPropertyChangeListener propertyChangeListener =
            new IPropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent event) {
                    if (event.getProperty() != null) {
                        if (event
                                .getProperty()
                                .equals(PreferenceStoreKeys.DONT_ASK_TO_BUILD_WHEN_CHANGING_DESTINATIONENVIRONMENTS)) {
                            if (preferencesEditor != null) {
                                preferencesEditor.load();
                            }
                        } else if (event
                                .getProperty()
                                .equals(PreferenceStoreKeys.DONT_ASK_TO_BUILD_WHEN_CHANGING_CAPABILITIES)) {
                            if (capabilitiesEditor != null) {
                                capabilitiesEditor.load();
                            }
                        }
                    }
                }
            };

    public ValidationBuildPreferencePage() {
        super();
        setPreferenceStore(XpdResourcesUIActivator.getDefault()
                .getPreferenceStore());
    }

    public ValidationBuildPreferencePage(String title) {
        super(title);
        setPreferenceStore(XpdResourcesUIActivator.getDefault()
                .getPreferenceStore());
    }

    public ValidationBuildPreferencePage(String title, ImageDescriptor image) {
        super(title, image);
        setPreferenceStore(XpdResourcesUIActivator.getDefault()
                .getPreferenceStore());
    }

    protected Control createContents(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        preferencesEditor =
                new BooleanFieldEditor(
                        PreferenceStoreKeys.DONT_ASK_TO_BUILD_WHEN_CHANGING_DESTINATIONENVIRONMENTS,
                        Messages.XpdResourcesUIActivator_DestinationEnvironmentChanges_BuildWithoutAsking_Label,
                        composite);
        preferencesEditor.setPreferenceStore(getPreferenceStore());
        preferencesEditor.load();
        capabilitiesEditor =
                new BooleanFieldEditor(
                        PreferenceStoreKeys.DONT_ASK_TO_BUILD_WHEN_CHANGING_CAPABILITIES,
                        Messages.XpdResourcesUIActivator_CategoryChanges_BuildWithoutAsking_Label,
                        composite);
        capabilitiesEditor.setPreferenceStore(getPreferenceStore());
        capabilitiesEditor.load();
        return composite;
    }

    public void init(IWorkbench workbench) {
        if (getPreferenceStore() != null && propertyChangeListener != null) {
            getPreferenceStore()
                    .addPropertyChangeListener(propertyChangeListener);
        }
    }

    

    /**
     * 
     * @see org.eclipse.jface.preference.PreferencePage#performApply()
     */
    protected void performApply() {
        preferencesEditor.store();
        capabilitiesEditor.store();
        super.performApply();
    }

    /**
     * 
     * @see org.eclipse.jface.preference.PreferencePage#performOk()
     */
    public boolean performOk() {
        preferencesEditor.store();
        capabilitiesEditor.store();
        return super.performOk();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.preference.PreferencePage#performDefaults()
     */
    @Override
    protected void performDefaults() {
        preferencesEditor.loadDefault();
        capabilitiesEditor.loadDefault();
        super.performDefaults();
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.dialogs.DialogPage#dispose()
     */
    @Override
    public void dispose() {
        if(getPreferenceStore() != null && propertyChangeListener != null){
            getPreferenceStore().removePropertyChangeListener(propertyChangeListener);
        }
        super.dispose();
    }

}
