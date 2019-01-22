package com.tibco.xpd.deploy.ui.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.DeployUIConstants;
import com.tibco.xpd.deploy.ui.internal.Messages;

public class DeploymentPreferencePage extends PreferencePage implements
        IWorkbenchPreferencePage {

    private final IPreferenceStore preferenceStore;

    private BooleanFieldEditor editor;

    private BooleanFieldEditor editor2;

    private BooleanFieldEditor editor3;
    
    private BooleanFieldEditor editor4;

    public DeploymentPreferencePage() {
        super();
        preferenceStore = DeployUIActivator.getDefault().getPreferenceStore();
    }

    public DeploymentPreferencePage(String title) {
        super(title);
        preferenceStore = DeployUIActivator.getDefault().getPreferenceStore();
    }

    public DeploymentPreferencePage(String title, ImageDescriptor image) {
        super(title, image);
        preferenceStore = DeployUIActivator.getDefault().getPreferenceStore();
    }

    @Override
    protected Control createContents(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        editor =
                new BooleanFieldEditor(
                        PreferenceStoreKeys.DONT_ASK_TO_SAVE_BEFORE_DEPLOYMENT,
                        Messages.DeploymentPreferencePage_Dont_Ask_Save_Before_Deployment,
                        composite);
        editor.setPreferenceStore(preferenceStore);
        editor.load();
        DeployUIActivator.getDefault().showDeployResultDialog();
        editor2 =
                new BooleanFieldEditor(
                        DeployUIConstants.SHOW_DEPLOY_RESULT_DIALOG,
                        Messages.DeploymentPreferencePage_Show_Deployment_Results_Dialog,
                        composite);
        editor2.setPreferenceStore(preferenceStore);
        editor2.load();

        editor3 =
                new BooleanFieldEditor(
                        DeployUIConstants.SHOW_NOTHING_TO_DEPLOY_DIALOG,
                        Messages.DeploymentPreferencePage_Show_Nothing_To_Deploy_Dialog,
                        composite);
        editor3.setPreferenceStore(preferenceStore);
        editor3.load();
        
        editor4 =
            new BooleanFieldEditor(
                    DeployUIConstants.SHOW_DELETE_NODE_DIALOG,
                    Messages.DeploymentPreferencePage_Show_Delete_Node_Dialog,
                    composite);
        editor4.setPreferenceStore(preferenceStore);
        editor4.load();        
        
        return composite;
    }

    public void init(IWorkbench workbench) {
    }

    /**
     * 
     * @see org.eclipse.jface.preference.PreferencePage#performApply()
     */
    @Override
    protected void performApply() {
        editor.store();
        editor2.store();
        editor3.store();
        editor4.store();
        super.performApply();
    }

    /**
     * 
     * @see org.eclipse.jface.preference.PreferencePage#performOk()
     */
    @Override
    public boolean performOk() {
        editor.store();
        editor2.store();
        editor3.store();
        editor4.store();
        return super.performOk();
    }

}
