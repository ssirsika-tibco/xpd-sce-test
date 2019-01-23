/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.ui.preferences;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.components.BaseXpdToolkit;
import com.tibco.xpd.resources.ui.components.PropertyAndPreferencePage;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.resources.ui.internal.destination.GlobalDestinationUtil;
import com.tibco.xpd.resources.util.UserInfo;
import com.tibco.xpd.resources.util.UserInfoUtil;

/**
 * Preference page to provide initial user info default values to a Studio BPM
 * project. This page will be available both at workspace (preferences) and
 * project (properties) level.
 * 
 * @author bharge
 * 
 */
public class UserInfoPreferencePage extends PropertyAndPreferencePage {

    public static final String PREF_ID =
            "com.tibco.xpd.resources.ui.userInfoPreferencePage"; //$NON-NLS-1$

    public static final String PROP_ID =
            "com.tibco.xpd.resources.ui.userInfoPropertyPage"; //$NON-NLS-1$

    private UserInfo userInfo;

    private Text txtName;

    private Text txtOrgName;

    private Text txtDomainName;

    private Text txtEndpointURI;

    private CCombo destinationCombo;

    private Map<String, String> destinationMap =
            new LinkedHashMap<String, String>();

    /**
     * @see com.tibco.xpd.resources.ui.components.PropertyAndPreferencePage#createPreferenceContent(org.eclipse.swt.widgets.Composite)
     * 
     * @param composite
     * @return
     */
    @Override
    protected Control createPreferenceContent(Composite composite) {
        BaseXpdToolkit toolkit = new BaseXpdToolkit();
        Composite root = toolkit.createComposite(composite);

        initialize();

        GridLayout layout = new GridLayout(2, true);
        root.setLayout(layout);

        GridData gData = new GridData();

        // User name label and text box
        Label lblName =
                toolkit.createLabel(root,
                        Messages.UserInfoPreferencePage_userName_label);
        lblName.setLayoutData(gData);
        txtName = toolkit.createText(root, userInfo.getUserName(), "User Name"); //$NON-NLS-1$
        gData = new GridData(GridData.FILL_HORIZONTAL);
        txtName.setLayoutData(gData);
        txtName.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {
                String errorMessage = null;

                if (txtName.getText().trim().length() == 0) {
                    errorMessage =
                            Messages.UserInfoPreferencePage_userName_message;
                }
                if (null == errorMessage) {
                    errorMessage = validateAll();
                }

                if (null != errorMessage) {
                    setErrorMessage(errorMessage);
                    setValid(false);
                } else {
                    setErrorMessage(null);
                    setValid(true);
                }
            }

        });

        // Organisation Name label and text box
        Label lblOrgName =
                toolkit.createLabel(root,
                        Messages.UserInfoPreferencePage_orgName_label);
        gData = new GridData();
        lblOrgName.setLayoutData(gData);
        txtOrgName =
                toolkit.createText(root,
                        userInfo.getOrganisationName(),
                        "Organisation Name"); //$NON-NLS-1$
        gData = new GridData(GridData.FILL_HORIZONTAL);
        txtOrgName.setLayoutData(gData);
        txtOrgName.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {
                String errorMessage = null;
                if (txtOrgName.getText().trim().length() == 0) {
                    errorMessage =
                            Messages.UserInfoPreferencePage_organisationName_message1;
                }
                if (null == errorMessage) {
                    errorMessage = validateAll();
                }

                if (null != errorMessage) {
                    setErrorMessage(errorMessage);
                    setValid(false);
                } else {
                    setErrorMessage(null);
                    setValid(true);
                }
            }

        });

        // Domain Name label and text box
        Label lblDomainName =
                toolkit.createLabel(root,
                        Messages.UserInfoPreferencePage_domainName_label1);
        gData = new GridData();
        lblDomainName.setLayoutData(gData);
        txtDomainName =
                toolkit.createText(root,
                        userInfo.getDomainName(),
                        "Domain Name"); //$NON-NLS-1$
        gData = new GridData(GridData.FILL_HORIZONTAL);
        txtDomainName.setLayoutData(gData);
        txtDomainName.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {
                String errorMessage = null;

                if (txtDomainName.getText().trim().length() == 0) {
                    errorMessage =
                            Messages.UserInfoPreferencePage_domainName_message;
                }
                if (null == errorMessage) {
                    errorMessage = validateAll();
                }

                if (null != errorMessage) {
                    setErrorMessage(errorMessage);
                    setValid(false);
                } else {
                    setErrorMessage(null);
                    setValid(true);
                }
            }
        });

        // Endpoint URI label and text box
        Label lblEndpointURI =
                toolkit.createLabel(root,
                        Messages.UserInfoPreferencePage_endpointURI_label);
        gData = new GridData();
        lblEndpointURI.setLayoutData(gData);
        txtEndpointURI =
                toolkit.createText(root,
                        userInfo.getEndpointURI(),
                        "Endpoint URI"); //$NON-NLS-1$
        gData = new GridData(GridData.FILL_HORIZONTAL);
        txtEndpointURI.setLayoutData(gData);
        txtEndpointURI.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {
                String errorMessage = null;

                if (txtEndpointURI.getText().equalsIgnoreCase(userInfo
                        .getEndpointURI())) {
                    errorMessage = null;
                } else if (txtEndpointURI.getText().trim().length() == 0
                        || !txtEndpointURI.getText().startsWith("/") //$NON-NLS-1$
                        || txtEndpointURI.getText().endsWith("/")) { //$NON-NLS-1$
                    errorMessage =
                            Messages.UserInfoPreferencePage_endpointURI_message;
                }
                if (null == errorMessage) {
                    errorMessage = validateAll();
                }

                if (null != errorMessage) {
                    setErrorMessage(errorMessage);
                    setValid(false);
                } else {
                    setErrorMessage(null);
                    setValid(true);
                }
            }
        });

        /*
         * Don't show destination in RCP (XPD-4409)
         */
        if (!XpdResourcesPlugin.isRCP()) {
            // Project Destination label and combo box
            Label lblDestination =
                    toolkit.createLabel(root,
                            Messages.UserInfoPreferencePage_destination_label);
            gData = new GridData();
            lblDestination.setLayoutData(gData);

            destinationCombo = new CCombo(root, SWT.BORDER);
            gData = new GridData(GridData.FILL_HORIZONTAL);
            destinationCombo.setLayoutData(gData);

            for (Map.Entry<String, String> entry : destinationMap.entrySet()) {
                destinationCombo.add(entry.getValue());
                destinationCombo.setData(entry.getValue(), entry.getKey());
            }

            int index = getTypeIndex(userInfo.getDestination());
            destinationCombo.setText(getTypeValue(index));
            destinationCombo.select(index);
        }

        return root;
    }

    // protected String getTypeName(String id) {
    // String typeName = null;
    // if (destinationMap != null) {
    // typeName = destinationMap.get(id);
    // }
    // return typeName;
    // }

    private int getTypeIndex(String type) {
        int typeIndex = 0;
        if (destinationMap != null) {
            Collection<String> values = destinationMap.values();
            if (values.contains(type)) {
                for (Iterator<String> iterator = values.iterator(); iterator
                        .hasNext();) {
                    String typeName = iterator.next();
                    if (typeName != null && typeName.equals(type)) {
                        break;
                    }
                    typeIndex++;
                }
            }
        }
        if (type.equalsIgnoreCase("") || type.length() == 0) { //$NON-NLS-1$
            return -1;
        }
        return typeIndex;
    }

    private String getTypeValue(int index) {
        String typeValue = null;
        if (index >= 0) {
            if (destinationMap != null) {
                ArrayList<String> values =
                        new ArrayList<String>(destinationMap.keySet());
                typeValue = values.get(index);
            }
        }
        if (index < 0) {
            typeValue = ""; //$NON-NLS-1$
        }
        return typeValue;
    }

    private String validateAll() {
        String errorMessage = null;
        if (txtName.getText().trim().length() == 0) {
            errorMessage = Messages.UserInfoPreferencePage_userName_message;
            return errorMessage;
        }
        if (txtDomainName.getText().trim().length() == 0) {
            errorMessage = Messages.UserInfoPreferencePage_domainName_message;
            return errorMessage;
        }
        if (txtOrgName.getText().trim().length() == 0) {
            errorMessage =
                    Messages.UserInfoPreferencePage_organisationName_message1;
            return errorMessage;
        }
        if (txtEndpointURI.getText()
                .equalsIgnoreCase(userInfo.getEndpointURI())) {
            errorMessage = null;
        } else if (txtEndpointURI.getText().trim().length() == 0
                || !txtEndpointURI.getText().startsWith("/") //$NON-NLS-1$
                || txtEndpointURI.getText().endsWith("/")) { //$NON-NLS-1$
            errorMessage = Messages.UserInfoPreferencePage_endpointURI_message;
            return errorMessage;
        }
        return errorMessage;
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.PropertyAndPreferencePage#getPreferencePageID()
     * 
     * @return
     */
    @Override
    protected String getPreferencePageID() {
        return PREF_ID;
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.PropertyAndPreferencePage#getPropertyPageID()
     * 
     * @return
     */
    @Override
    protected String getPropertyPageID() {
        return PROP_ID;
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.PropertyAndPreferencePage#hasProjectSpecificOptions(org.eclipse.core.resources.IProject)
     * 
     * @param project
     * @return
     */
    @Override
    protected boolean hasProjectSpecificOptions(IProject project) {
        if (null != project) {
            return UserInfoUtil.hasProjectSpecificUserInfoSettings(project);
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.PropertyAndPreferencePage#init(org.eclipse.ui.IWorkbench)
     * 
     * @param workbench
     */
    @Override
    public void init(IWorkbench workbench) {
        setPreferenceStore(XpdResourcesUIActivator.getDefault()
                .getPreferenceStore());
    }

    private void initialize() {
        List<String> destinations =
                GlobalDestinationUtil.getGlobalDestinations();
        for (String destination : destinations) {
            destinationMap.put(destination, destination);
        }

        // Get the user-profile values
        userInfo = UserInfoUtil.getProjectPreferences(getProject());
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.PropertyAndPreferencePage#performDefaults()
     * 
     */
    @Override
    protected void performDefaults() {
        if (!useProjectSettings()) {
            userInfo = UserInfoUtil.getDefaultValue();
            txtName.setText(userInfo.getUserName());
            txtOrgName.setText(userInfo.getOrganisationName());
            txtDomainName.setText(userInfo.getDomainName());
            txtEndpointURI.setText(userInfo.getEndpointURI());
            // Destination combo will not be created if running in RCP
            if (destinationCombo != null) {
                destinationCombo.setText(userInfo.getDestination());
            }
        }
        super.performDefaults();
    }

    /**
     * @see org.eclipse.jface.preference.PreferencePage#performOk()
     * 
     * @return
     */
    @Override
    public boolean performOk() {
        userInfo.setUserName(txtName.getText());
        userInfo.setDomainName(txtDomainName.getText());
        userInfo.setOrganisationName(txtOrgName.getText());
        userInfo.setEndpointURI(txtEndpointURI.getText());

        // Destination combo will not be created if running in RCP
        if (destinationCombo != null) {
            String typeValue =
                    getTypeValue(getTypeIndex(destinationCombo.getText()));
            userInfo.setDestination(typeValue);
        }

        if (isProjectPreferencePage()) {
            if (useProjectSettings()) {
                UserInfoUtil.saveUserInfoToProjectPreferences(getProject(),
                        userInfo);
            } else {
                // Remove project specific settings
                UserInfoUtil.removeProjectPreferences(getProject());
            }
        } else {
            // Update the workspace preferences
            UserInfoUtil.saveUserInfoToWorkspacePreferences(userInfo);
        }
        return true;
    }

}
