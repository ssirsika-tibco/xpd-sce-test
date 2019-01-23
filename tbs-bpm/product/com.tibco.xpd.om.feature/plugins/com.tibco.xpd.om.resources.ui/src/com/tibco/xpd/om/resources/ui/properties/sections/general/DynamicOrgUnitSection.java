/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.general;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.om.core.om.DynamicOrgReference;
import com.tibco.xpd.om.core.om.DynamicOrgUnit;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.resources.ui.OMResourcesUIActivator;
import com.tibco.xpd.om.resources.ui.commonpicker.OMTypeQuery;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.general.AbstractGeneralSection;
import com.tibco.xpd.resources.ui.picker.PickerService;
import com.tibco.xpd.resources.ui.picker.PickerTypeQuery;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.properties.components.AbstractPickerControl;

/**
 * General section for a Dynamic OrgUnit.
 * 
 * @author njpatel
 */
public class DynamicOrgUnitSection extends AbstractGeneralSection implements
        IFilter {

    private static final String DEFAULT_LABEL_MSG =
            Messages.DynamicOrgUnitSection_labelIsDerived;

    private static final String DEFAULT_NAME_MSG =
            Messages.DynamicOrgUnitSection_nameIsDerived;

    private DynamicOrgPicker picker;

    private Text labelTxt;

    private Text nameTxt;

    public DynamicOrgUnitSection() {
    }

    @Override
    protected Command doGetCommand(Object obj) {
        /*
         * Command set in picker
         */
        return null;
    }

    @Override
    protected void doRefresh() {
        EObject input = getInput();

        if (input instanceof DynamicOrgUnit) {
            Organization dynOrg = null;
            DynamicOrgReference reference =
                    ((DynamicOrgUnit) input).getDynamicOrganization();
            if (reference != null) {
                dynOrg = reference.getTo();
            }
            picker.setValue(dynOrg);

            if (dynOrg != null) {
                EList<OrgUnit> subUnits = dynOrg.getSubUnits();

                if (!subUnits.isEmpty()) {
                    OrgUnit orgUnit = subUnits.get(0);
                    labelTxt.setText(orgUnit.getDisplayName());
                    nameTxt.setText(orgUnit.getName());
                } else {
                    labelTxt.setText(Messages.DynamicOrgUnitSection_noRootOrgUnitSetInDynamicOrg);
                    nameTxt.setText(Messages.DynamicOrgUnitSection_noRootOrgUnitSetInDynamicOrg);
                }
            } else {
                labelTxt.setText(DEFAULT_LABEL_MSG);
                nameTxt.setText(DEFAULT_NAME_MSG);
            }
        }
    }

    /**
     * @see com.tibco.xpd.om.resources.ui.properties.sections.internal.general.AbstractGeneralSection#doCreateControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param toolkit
     * @return
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = (Composite) super.doCreateControls(parent, toolkit);

        // Label
        createLabel(root, toolkit, Messages.DynamicOrgUnitSection_label_label);
        labelTxt = toolkit.createText(root, DEFAULT_LABEL_MSG);
        labelTxt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        labelTxt.setEnabled(false);

        // Name
        createLabel(root, toolkit, Messages.DynamicOrgUnitSection_name_label);
        nameTxt = toolkit.createText(root, DEFAULT_NAME_MSG);
        nameTxt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        nameTxt.setEnabled(false);

        // Dynamic Organization picker
        Label dynamicOrgLabel =
                createLabel(root,
                        toolkit,
                        Messages.DynamicOrgUnitSection_dynamicOrganizationLabel);
        dynamicOrgLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
                false));

        picker =
                new DynamicOrgPicker(root, SWT.NONE, toolkit,
                        getEditingDomain());
        picker.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        return root;
    }

    /**
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     * 
     * @param toTest
     * @return
     */
    @Override
    public boolean select(Object toTest) {
        return resollveInput(toTest) instanceof DynamicOrgUnit;
    }

    /**
     * Picker for a Dynamic Organization.
     * 
     */
    private class DynamicOrgPicker extends AbstractPickerControl<Organization> {

        /**
         * @param parent
         * @param style
         * @param toolkit
         * @param editingDomain
         */
        public DynamicOrgPicker(Composite parent, int style,
                XpdFormToolkit toolkit, EditingDomain editingDomain) {
            super(parent, style, toolkit, editingDomain);
            setHyperlinkActive(true);
        }

        /**
         * @see com.tibco.xpd.ui.properties.components.AbstractPickerControl#doBrowse(org.eclipse.swt.widgets.Control)
         * 
         * @param control
         * @return
         */
        @Override
        protected Organization doBrowse(Control control) {
            EObject input = getInput();
            IResource res = null;

            if (input != null) {
                res = WorkingCopyUtil.getFile(input);
            }

            Object selection =
                    PickerService
                            .getInstance()
                            .openSinglePickerDialog(control.getShell(),
                                    new PickerTypeQuery[] { new OMTypeQuery(
                                            OMTypeQuery.TYPE_ID_DYNAMIC_ORGANIZATION) },
                                    null,
                                    res != null ? new IResource[] { res }
                                            : null,
                                    null,
                                    null,
                                    null);
            return (Organization) (selection instanceof Organization ? selection
                    : null);
        }

        /**
         * @see com.tibco.xpd.ui.properties.components.AbstractPickerControl#getClearValueCommand(org.eclipse.emf.edit.domain.EditingDomain,
         *      java.lang.Object)
         * 
         * @param editingDomain
         * @param org
         * @return
         */
        @Override
        protected Command getClearValueCommand(EditingDomain editingDomain,
                Organization org) {
            EObject input = getInput();
            if (input instanceof DynamicOrgUnit) {
                return SetCommand.create(editingDomain,
                        input,
                        OMPackage.eINSTANCE
                                .getDynamicOrgUnit_DynamicOrganization(),
                        SetCommand.UNSET_VALUE);
            }
            return null;
        }

        /**
         * @see com.tibco.xpd.ui.properties.components.AbstractPickerControl#getSetValueCommand(org.eclipse.emf.edit.domain.EditingDomain,
         *      java.lang.Object)
         * 
         * @param editingDomain
         * @param org
         * @return
         */
        @Override
        protected Command getSetValueCommand(EditingDomain editingDomain,
                Organization org) {
            EObject input = getInput();
            if (input instanceof DynamicOrgUnit) {
                return SetCommand.create(editingDomain,
                        input,
                        OMPackage.eINSTANCE
                                .getDynamicOrgUnit_DynamicOrganization(),
                        org);
            }
            return null;
        }

        /**
         * @see com.tibco.xpd.ui.properties.components.AbstractPickerControl#hyperlinkActivated(java.lang.Object)
         * 
         * @param org
         */
        @Override
        protected void hyperlinkActivated(Organization org) {

            try {
                OMResourcesUIActivator.openEditor(getSite().getPage(), org);
            } catch (CoreException e) {
                ErrorDialog
                        .openError(getShell(),
                                Messages.DynamicOrgUnitSection_unableToOpenOrganization_errDlg_tile,
                                Messages.DynamicOrgUnitSection_unableToOpenOrganization_errDlg_longdesc,
                                e.getStatus());
            }
        }
    }

}
