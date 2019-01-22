/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.general;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.ActivityManagerEvent;
import org.eclipse.ui.activities.IActivityManager;
import org.eclipse.ui.activities.IActivityManagerListener;
import org.eclipse.ui.activities.WorkbenchActivityHelper;

import com.tibco.xpd.om.core.om.DynamicOrgUnit;
import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgMetaModel;
import com.tibco.xpd.om.resources.ui.OMResourcesUIActivator;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.general.AbstractGeneralSection;
import com.tibco.xpd.ui.properties.TextFieldVerifier;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.util.NameUtil;

/**
 * Property section for the {@link NamedElement} object.
 * 
 * @author njpatel
 * 
 */
public class NamedElementSection extends AbstractGeneralSection implements
        IFilter, TextFieldVerifier {

    private ActivityManagerListener activityListener;

    private Text labelTxt;

    private Text nameTxt;

    private Composite labelControls;

    private Composite nameControls;

    private boolean ignoreEvents = false;

    /**
     * plug-in contribution to check for capability.
     */
    private final IPluginContribution pluginContribution =
            new IPluginContribution() {

                @Override
                public String getLocalId() {
                    return "developer-name"; //$NON-NLS-1$
                }

                @Override
                public String getPluginId() {
                    return OMResourcesUIActivator.PLUGIN_ID;
                }

            };

    private Composite parentComposite;

    /**
     * Property section for the {@link NamedElement} object.
     */
    public NamedElementSection() {
        IActivityManager activityManager =
                PlatformUI.getWorkbench().getActivitySupport()
                        .getActivityManager();

        if (activityManager != null) {
            activityListener = new ActivityManagerListener();
            activityManager.addActivityManagerListener(activityListener);
        }
    }

    @Override
    public void dispose() {
        if (activityListener != null) {
            IActivityManager activityManager =
                    PlatformUI.getWorkbench().getActivitySupport()
                            .getActivityManager();

            if (activityManager != null) {
                activityManager.removeActivityManagerListener(activityListener);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     */
    @Override
    public boolean select(Object toTest) {
        EObject input = resollveInput(toTest);

        /*
         * XPD-5300: Don't show for DynamicOrgUnit. Although a DynamicOrgUnit is
         * a NamedElement its name will not be stored as it will be derived from
         * the dynamic organization it references (from the root org unit of
         * this organization).
         */
        if (input instanceof DynamicOrgUnit) {
            return false;
        }

        // Don't show section for schema model
        return input instanceof NamedElement
                && !(input instanceof OrgMetaModel);
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        parentComposite = parent;
        Composite root = toolkit.createComposite(parent);
        root.setLayout(new GridLayout());

        /*
         * Add label controls
         */
        labelControls = toolkit.createComposite(root);
        labelControls.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
                false));

        GridLayout layout = new GridLayout(2, false);
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        labelControls.setLayout(layout);

        createLabel(labelControls,
                toolkit,
                Messages.NamedElementSection_label_label);
        labelTxt = toolkit.createText(labelControls, "", "namedElement-label"); //$NON-NLS-1$ //$NON-NLS-2$
        setLayoutData(labelTxt);
        manageControlUpdateOnDeactivate(labelTxt);

        /*
         * Add name controls
         */

        nameControls = toolkit.createComposite(root);
        nameControls.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
                false));
        layout = new GridLayout(2, false);
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        nameControls.setLayout(layout);

        createLabel(nameControls,
                toolkit,
                Messages.NamedElementSection_name_label);
        nameTxt = toolkit.createText(nameControls, "", "namedElement-name"); //$NON-NLS-1$ //$NON-NLS-2$

        setLayoutData(nameTxt);
        manageControlUpdateOnDeactivate(nameTxt);

        // Update the name control visibility
        updateVisibility();

        return root;

    }

    @Override
    protected Command doGetCommand(Object obj) {
        EObject input = getInput();
        Command cmd = null;
        if (input instanceof NamedElement && obj instanceof Text) {
            String value = ((Text) obj).getText();
            if (value.length() > 0) {
                if (obj == nameTxt) {
                    /*
                     * XPD-4628: Only update the name if it has changed. At
                     * times, when Positions are added in quick succession to an
                     * OrgUnit (in the editor) this update can get triggered
                     * during a section update and cause transaction issues.
                     */
                    if (!value.equals(((NamedElement) input).getName())) {
                        // Update the name
                        cmd =
                                SetCommand.create(getEditingDomain(),
                                        input,
                                        OMPackage.eINSTANCE
                                                .getNamedElement_Name(),
                                        value);
                    }
                } else if (obj == labelTxt) {
                    /*
                     * XPD-4628: Only update the label if it has changed. At
                     * times, when Positions are added in quick succession to an
                     * OrgUnit (in the editor) this update can get triggered
                     * during a section update and cause transaction issues.
                     */
                    if (!value.equals(((NamedElement) input).getDisplayName())) {
                        CompoundCommand ccmd = new CompoundCommand();

                        // Add command to update label
                        ccmd.append(SetCommand.create(getEditingDomain(),
                                input,
                                OMPackage.eINSTANCE
                                        .getNamedElement_DisplayName(),
                                value));

                        // Update the name if it has changed
                        String currentName = ((NamedElement) input).getName();
                        if (!nameTxt.getText().equals(currentName)) {
                            ccmd.append(SetCommand.create(getEditingDomain(),
                                    input,
                                    OMPackage.eINSTANCE.getNamedElement_Name(),
                                    nameTxt.getText()));
                        }

                        cmd = ccmd;
                    }
                }
            } else {
                // Value not changed as it was blanked so restore previous value
                doRefresh();
            }
        }
        return cmd;
    }

    @Override
    protected void doRefresh() {
        ignoreEvents = true;
        try {
            EObject input = getInput();

            if (input instanceof NamedElement && labelTxt != null
                    && !labelTxt.isDisposed()) {
                NamedElement elem = (NamedElement) input;

                updateText(labelTxt, elem.getDisplayName());
                updateText(nameTxt, elem.getName());
            }
        } finally {
            ignoreEvents = false;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.TextFieldVerifier#verifyText(org.eclipse.
     * swt.widgets.Event)
     */
    @Override
    public void verifyText(Event event) {
        if (!ignoreEvents) {
            /*
             * Generate name if required
             */
            if (event.widget == labelTxt
                    && (labelTxt.getText().equals(nameTxt.getText()) || nameTxt
                            .getText()
                            .equals(getNameFromLabel(labelTxt.getText())))) {

                String text =
                        labelTxt.getText(0, event.start - 1)
                                + event.text
                                + labelTxt.getText(event.end,
                                        labelTxt.getCharCount() - 1);
                nameTxt.setText(getNameFromLabel(text));
            }
        }
    }

    /**
     * Get the (internal) name from the label to store as the name of this
     * element.
     * 
     * @param label
     *            user-defined label of this element
     * @return name
     */
    public static String getNameFromLabel(String label) {
        String name = label;

        if (label != null) {
            name = NameUtil.getInternalName(label, false);
        }

        return name;
    }

    /**
     * Update the visibility of the Name controls in accordance with the
     * selected capability.
     */
    private void updateVisibility() {
        if (nameControls != null && !nameControls.isDisposed()) {
            if (isNameVisible()) {
                nameControls.setVisible(true);
                nameControls.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
                        true, false));
            } else {
                nameControls.setVisible(false);
                nameControls.setLayoutData(new GridData(0, 0));
            }

            // Force layout on the section composite to hide/show the name
            // controls
            if (parentComposite != null && parentComposite.getParent() != null) {
                parentComposite.getParent().layout();
            }
        }
    }

    /**
     * Check if the name controls should be visible. This is determined by the
     * set capability.
     * 
     * @return <code>true</code> if the name controls should be visible,
     *         <code>false</code> otherwise.
     */
    private boolean isNameVisible() {
        return !WorkbenchActivityHelper.filterItem(pluginContribution);
    }

    /**
     * Activity manager listener that will update the visibility of the Name
     * controls in accordance with the selected capabilities (activities).
     * 
     * @author njpatel
     * 
     */
    private class ActivityManagerListener implements IActivityManagerListener {

        @Override
        public void activityManagerChanged(
                ActivityManagerEvent activityManagerEvent) {

            updateVisibility();
        }

    }
}
