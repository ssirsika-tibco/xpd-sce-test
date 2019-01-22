/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.general;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.om.core.om.DynamicOrgUnit;
import com.tibco.xpd.om.core.om.Locatable;
import com.tibco.xpd.om.core.om.Location;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.resources.ui.commonpicker.OMTypeQuery;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.general.AbstractGeneralSection;
import com.tibco.xpd.resources.ui.picker.PickerService;
import com.tibco.xpd.resources.ui.picker.PickerTypeQuery;
import com.tibco.xpd.resources.ui.picker.filters.SameResourceFilter;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.properties.components.AbstractPickerControl;

/**
 * Property section for the {@link Locatable} objects.
 * 
 * @author njpatel
 * 
 */
public class LocatableSection extends AbstractGeneralSection implements IFilter {

    private LocationPickerControl picker;

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = (Composite) super.doCreateControls(parent, toolkit);
        createLabel(root, toolkit, Messages.LocatableSection_location_label);
        picker =
                new LocationPickerControl(root, SWT.NONE, toolkit,
                        getEditingDomain());
        picker.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        picker.setValue(null);
        return root;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        // Location picker will update the model
        return null;
    }

    @Override
    protected void doRefresh() {
        EObject input = getInput();

        if (picker != null && !picker.isDisposed()
                && input instanceof Locatable) {
            picker.setValue(((Locatable) input).getLocation());
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
         * XPD-5300: Don't show for Dynamic OrgUnit
         */
        if (input instanceof DynamicOrgUnit) {
            return false;
        } else if (input instanceof Organization) {
            /*
             * XPD-5772: Saket: Don't show for Dynamic Organization templates.
             */
            Organization org = (Organization) input;
            if (org.isDynamic()) {
                return false;
            }
        }

        return input instanceof Locatable;
    }

    /**
     * Picker control to select a {@link Location}.
     * 
     * @author njpatel
     * 
     */
    private class LocationPickerControl extends AbstractPickerControl<Location> {

        public LocationPickerControl(Composite parent, int style,
                XpdFormToolkit toolkit, EditingDomain ed) {
            super(parent, style, toolkit, ed);
            setHyperlinkActive(true);
            setBrowseTooltip(Messages.LocatableSection_selectLocation_button_tooltip);
            setClearTooltip(Messages.LocatableSection_clearLocation_button_tooltip);
        }

        @Override
        protected Location doBrowse(Control control) {
            Location loc = null;
            EObject input = getInput();

            if (input instanceof Locatable) {
                Object sel =
                        PickerService
                                .getInstance()
                                .openSinglePickerDialog(getShell(),
                                        new PickerTypeQuery[] { new OMTypeQuery(
                                                OMTypeQuery.TYPE_ID_LOCATION) },
                                        null,
                                        null,
                                        null,
                                        new IFilter[] { new SameResourceFilter(
                                                input) });
                if (sel instanceof Location) {
                    loc = (Location) sel;
                }
            }
            return loc;
        }

        @Override
        protected void hyperlinkActivated(Location value) {
            if (value != null) {
                selectInProjectExplorer(value);
            }
        }

        @Override
        protected String getClearText() {
            return Messages.LocatableSection_noLocationSet_shortdesc;
        }

        @Override
        protected Command getSetValueCommand(EditingDomain editingDomain,
                Location value) {
            EObject input = getInput();

            if (input != null) {
                return SetCommand.create(editingDomain,
                        input,
                        OMPackage.eINSTANCE.getLocatable_Location(),
                        value);
            }
            return null;
        }

        @Override
        protected Command getClearValueCommand(EditingDomain editingDomain,
                Location value) {
            EObject input = getInput();

            if (input != null) {
                return SetCommand.create(editingDomain,
                        input,
                        OMPackage.eINSTANCE.getLocatable_Location(),
                        SetCommand.UNSET_VALUE);
            }
            return null;
        }
    }
}
