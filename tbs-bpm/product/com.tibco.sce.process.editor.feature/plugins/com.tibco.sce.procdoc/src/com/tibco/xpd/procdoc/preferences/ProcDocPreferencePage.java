/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.procdoc.preferences;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;

import com.tibco.xpd.procdoc.ProcDocOption;
import com.tibco.xpd.procdoc.ProcdocPlugin;
import com.tibco.xpd.procdoc.internal.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.components.PropertyAndPreferencePage;

/**
 * Creates Documentation top-level Preference page
 * 
 * <p>
 * <i>Created: 28 June 2010</i>
 * 
 * @author mtorres
 * 
 */
public class ProcDocPreferencePage extends PropertyAndPreferencePage implements SelectionListener {

    private List<ProcDocOption> options;

    private boolean inCreation = false;
    
    private Group optionContainer = null;
    
    public static final String PROP_ID =
            "com.tibco.xpd.procdoc.preferences.procDocPropertyPage"; //$NON-NLS-1$

    public static final String PREF_ID =
            "com.tibco.xpd.procdoc.preferences.procDocPreferencePage"; //$NON-NLS-1$
    
    public ProcDocPreferencePage() {
                
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected Control createPreferenceContent(Composite parent) {
        inCreation = true;
        initialize();
        try {
            Composite root = new Composite(parent, SWT.NONE);

            root.setLayout(new GridLayout(1, false));
            optionContainer = new Group(root, SWT.NONE);
            optionContainer
                    .setText(Messages.ProcDocOptionsPage_OptionalOut_label);
            GridData gridData = new GridData(GridData.FILL_BOTH);
            optionContainer.setLayoutData(gridData);
            GridLayout gridLayout = new GridLayout(1, false);
            optionContainer.setLayout(gridLayout);

            for (ProcDocOption option : options) {
                Button btn = new Button(optionContainer, SWT.CHECK);
                btn.setText(option.getDescription());
                btn.setData(option.getId());
                btn.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
                btn.addSelectionListener(this);
            }           

            setControlsFromOptions();
            return root;
        } finally {
            inCreation = false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getPreferencePageID() {
        return PREF_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getPropertyPageID() {
        return PROP_ID;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean hasProjectSpecificOptions(IProject project) {
        if (project != null) {
            return ProcDocOption
                    .hasProjectSpecificProcDocSettings(getProject());
        }
        return false;
    }
    
    
    private void setControlsFromOptions() {
        inCreation = true;
        try {
            Control[] optionCtrls = optionContainer.getChildren();
            for (int i = 0; i < optionCtrls.length; i++) {
                Object data = optionCtrls[i].getData();
                if (data instanceof String) {

                    ProcDocOption option = getOption((String) data);
                    if (option != null) {
                        ((Button) optionCtrls[i]).setSelection(option.isOn());
                    }
                }
            }

        } finally {
            inCreation = false;
        }
    }
    

    /**
     * Return state of given proc doc option.
     * 
     * @param optionId
     * @return true if option is on.
     */
    public ProcDocOption getOption(String optionId) {
        ProcDocOption ret = null;

        if (options != null) {
            for (ProcDocOption option : options) {
                if (option.getId().equals(optionId)) {
                    ret = option;
                }
            }
        }
        return ret;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
     */
    public void init(IWorkbench workbench) {
        setPreferenceStore(ProcdocPlugin.getDefault().getPreferenceStore());
        initialize();
    }
    
    private void initialize() {
        // If project specific settings are defined then override with those
        if (isProjectProperties()) {
            options = ProcDocOption.loadProcDocProjectOptions(getProject());
        } else {
            options = ProcDocOption.loadProcDocOptions();
        }
    }
    
    public void widgetDefaultSelected(SelectionEvent e) {
    }

    public void widgetSelected(SelectionEvent e) {
        if (!inCreation) {
            if (e.widget.getData() instanceof String) {
                ProcDocOption option = getOption((String) e.widget.getData());
                if (option != null) {
                    option.setOn(((Button) e.widget).getSelection());
                }
            }
        }
    }
    
    private void forceSavePreferences() {
        for (ProcDocOption option : options) {
            if (isProjectProperties()) {
                ProcDocOption.saveOptionToProjectPreferences(getProject(), option);
            } else {
                ProcDocOption.saveOptionToPreferences(option);
            }
        }
    }
    
    @Override
    protected void performDefaults() {
        options = ProcDocOption.getDefaultProcDocOptions();
        setControlsFromOptions();
        super.performDefaults();
    }
    
    @Override
    public boolean performOk() {
        forceSavePreferences();
        return true;
    }
    
    /**
     * Check if this page is being viewed as the project properties.
     * 
     * @return <code>true</code> if project properties, <code>false</code> if
     *         preference page.
     */
    private boolean isProjectProperties() {
        return getProject() != null;
    }
    
    
    public void removeProperties(IProject project) {
        if (project != null) {
            IEclipsePreferences preferences =
                    ProcDocOption.getProjectPreferences(project);
            try {
                if (preferences != null
                        && preferences
                                .nodeExists(ProcDocOption.PROJECT_SPECIFIC_NODE)) {
                    preferences.node(ProcDocOption.PROJECT_SPECIFIC_NODE)
                            .removeNode();
                    preferences.flush();
                }
            } catch (Exception e) {
                XpdResourcesPlugin.getDefault().getLogger().error(e);
            }
        }
    }
    
    @Override
    protected void enableProjectSpecificSettings(
            boolean useProjectSpecificSettings) {
        ProcDocOption.setHasProjectSpecificProcDocSettings(getProject(),
                useProjectSpecificSettings);
        super.enableProjectSpecificSettings(useProjectSpecificSettings);
    }
    
    
}
