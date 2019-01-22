/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.internal.general;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.om.core.om.Capability;
import com.tibco.xpd.om.core.om.CapabilityCategory;
import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.Privilege;
import com.tibco.xpd.om.core.om.PrivilegeCategory;
import com.tibco.xpd.om.resources.ui.commonpicker.OMTypeQuery;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.resources.ui.picker.PickerService;
import com.tibco.xpd.resources.ui.picker.PickerTypeQuery;
import com.tibco.xpd.resources.ui.picker.filters.SameResourceFilter;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.properties.components.AbstractPickerControl;

/**
 * Category selection section for {@link Capability} and {@link Privilege}
 * objects.
 * 
 * @author njpatel
 * 
 */
public class CategoryGeneralSection extends AbstractGeneralSection {

    private CategoryPickerControl pickerControl;

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = (Composite) super.doCreateControls(parent, toolkit);

        createLabel(root,
                toolkit,
                Messages.CategoryGeneralSection_category_label);
        pickerControl =
                new CategoryPickerControl(root, SWT.NONE, toolkit,
                        getEditingDomain());
        pickerControl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        pickerControl
                .setToolTipText(Messages.CategoryGeneralSection_category_add_tooltip);
        pickerControl
                .setClearTooltip(Messages.CategoryGeneralSection_category_clear_tooltip);
        return root;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        // Handled by the picker control
        return null;
    }

    @Override
    protected void doRefresh() {
        if (pickerControl != null && !pickerControl.isDisposed()) {
            EObject input = getInput();
            NamedElement category = null;
            if (input instanceof Capability) {
                category = ((Capability) input).getCategory();
            } else if (input instanceof Privilege) {
                category = ((Privilege) input).getCategory();
            }

            pickerControl.setValue(category);
        }
    }

    /**
     * Category picker control.
     * 
     * @author njpatel
     * 
     */
    private class CategoryPickerControl extends
            AbstractPickerControl<NamedElement> {

        public CategoryPickerControl(Composite parent, int style,
                XpdFormToolkit toolkit, EditingDomain editingDomain) {
            super(parent, style, toolkit, editingDomain);
        }

        @Override
        protected NamedElement doBrowse(Control control) {
            EObject input = getInput();
            if (input != null) {
                String queryType = null;
                EObject toExclude = null;

                if (input instanceof Capability) {
                    queryType = OMTypeQuery.TYPE_ID_CAPABILITY_CATEGORY;
                    toExclude = ((Capability) input).getCategory();
                } else if (input instanceof Privilege) {
                    queryType = OMTypeQuery.TYPE_ID_PRIVILEGE_CATEGORY;
                    toExclude = ((Privilege) input).getCategory();
                }

                if (queryType != null) {
                    Object selection =
                            PickerService
                                    .getInstance()
                                    .openSinglePickerDialog(getShell(),
                                            new PickerTypeQuery[] { new OMTypeQuery(
                                                    queryType) },
                                            null,
                                            null,
                                            toExclude != null ? new Object[] { toExclude }
                                                    : null,
                                            new IFilter[] { new SameResourceFilter(
                                                    input) });
                    if (selection instanceof NamedElement) {
                        return (NamedElement) selection;
                    }
                }
            }
            return null;
        }

        @Override
        protected Command getClearValueCommand(EditingDomain editingDomain,
                NamedElement value) {
            EObject input = getInput();
            EStructuralFeature feat = null;
            if (input instanceof Capability) {
                feat = OMPackage.eINSTANCE.getCapability_Category();
            } else if (input instanceof Privilege) {
                feat = OMPackage.eINSTANCE.getPrivilege_Category();
            }
            return feat != null ? SetCommand.create(editingDomain,
                    input,
                    feat,
                    SetCommand.UNSET_VALUE) : null;
        }

        @Override
        protected Command getSetValueCommand(EditingDomain editingDomain,
                NamedElement value) {
            EObject input = getInput();
            EStructuralFeature feat = null;
            if (input instanceof Capability
                    && value instanceof CapabilityCategory) {
                feat = OMPackage.eINSTANCE.getCapability_Category();
            } else if (input instanceof Privilege
                    && value instanceof PrivilegeCategory) {
                feat = OMPackage.eINSTANCE.getPrivilege_Category();
            }
            return feat != null ? SetCommand.create(editingDomain,
                    input,
                    feat,
                    value) : null;
        }

    }

}
