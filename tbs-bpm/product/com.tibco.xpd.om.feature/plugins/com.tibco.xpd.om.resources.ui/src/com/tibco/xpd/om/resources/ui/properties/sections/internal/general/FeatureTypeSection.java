/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.internal.general;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgElementType;
import com.tibco.xpd.om.core.om.OrgUnitFeature;
import com.tibco.xpd.om.core.om.PositionFeature;
import com.tibco.xpd.om.resources.ui.commonpicker.OMTypeQuery;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.resources.ui.picker.PickerService;
import com.tibco.xpd.resources.ui.picker.PickerTypeQuery;
import com.tibco.xpd.resources.ui.picker.filters.SameResourceFilter;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.properties.components.AbstractPickerControl;

/**
 * Section for a feature's type selection.
 * 
 * @author njpatel
 * 
 */
public class FeatureTypeSection extends AbstractGeneralSection implements
        IFilter {

    private TypePickerControl typeControl;

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = (Composite) super.doCreateControls(parent, toolkit);

        createLabel(root, toolkit, Messages.OrgTypedElementSection_types_label);

        typeControl =
                new TypePickerControl(root, SWT.NONE, toolkit,
                        getEditingDomain());
        typeControl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        return root;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        // Type control handler will handle this
        return null;
    }

    @Override
    protected void doRefresh() {
        EObject input = getInput();

        if (select(input) && typeControl != null && !typeControl.isDisposed()) {
            typeControl.setValue(getType(input));
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     */
    @Override
    public boolean select(Object toTest) {
        EObject eo = resollveInput(toTest);

        if (eo instanceof OrgUnitFeature || eo instanceof PositionFeature) {
            return true;
        }
        return false;
    }

    /**
     * Get the {@link OrgElementType} of the given input.
     * 
     * @param input
     * @return type or <code>null</code> of the input has no type or is not a
     *         typed element.
     */
    protected OrgElementType getType(EObject input) {
        OrgElementType type = null;

        if (input instanceof OrgUnitFeature) {
            type = ((OrgUnitFeature) input).getFeatureType();
        } else if (input instanceof PositionFeature) {
            type = ((PositionFeature) input).getFeatureType();
        }

        return type;
    }

    /**
     * Get the element reference to set the element type of the given input.
     * 
     * @param input
     * @return <code>EReference</code> or <code>null</code>.
     */
    protected EReference getEReference(EObject input) {
        EReference ref = null;

        if (input instanceof PositionFeature) {
            ref = OMPackage.eINSTANCE.getPositionFeature_FeatureType();
        } else if (input instanceof OrgUnitFeature) {
            ref = OMPackage.eINSTANCE.getOrgUnitFeature_FeatureType();
        }

        return ref;
    }

    /**
     * Get the Organization type query for the picker for this input.
     * 
     * @param input
     *            element of Organization model.
     * @return
     */
    protected String getQueryType(EObject input) {
        if (input instanceof PositionFeature) {
            return OMTypeQuery.TYPE_ID_POSITION_TYPE;
        } else if (input instanceof OrgUnitFeature) {
            return OMTypeQuery.TYPE_ID_ORG_UNIT_TYPE;
        }
        return null;
    }

    /**
     * Control to select (using the type picker) and clear the type.
     * 
     * @author njpatel
     * 
     */
    private class TypePickerControl extends
            AbstractPickerControl<OrgElementType> {

        public TypePickerControl(Composite parent, int style,
                XpdFormToolkit toolkit, EditingDomain ed) {
            super(parent, style, toolkit, ed);
            setHyperlinkActive(true);
            setBrowseTooltip(Messages.OrgTypedElementSection_selectType_button_tooltip);
            setClearTooltip(Messages.OrgTypedElementSection_clearType_tooltip);
        }

        @Override
        protected String getClearText() {
            return Messages.OrgTypedElementSection_noTypeSet_shortdesc;
        }

        @Override
        protected void hyperlinkActivated(OrgElementType value) {
            if (value != null) {
                selectInProjectExplorer(value);
            }
        }

        @Override
        protected OrgElementType doBrowse(Control control) {
            EObject input = getInput();
            OrgElementType type = null;
            if (input != null) {
                String queryType = getQueryType(input);

                if (queryType != null) {
                    Object selection =
                            PickerService
                                    .getInstance()
                                    .openSinglePickerDialog(getShell(),
                                            new PickerTypeQuery[] { new OMTypeQuery(
                                                    queryType) },
                                            null,
                                            null,
                                            null,
                                            new IFilter[] { new SameResourceFilter(
                                                    input) });
                    if (selection instanceof OrgElementType) {
                        type = (OrgElementType) selection;
                    }
                } else {
                    throw new NullPointerException(
                            String.format(Messages.OrgTypedElementSection_cannotFindIndexerType_error_shortdesc,
                                    getInput()));
                }
            }
            return type;
        }

        @Override
        protected Command getClearValueCommand(EditingDomain editingDomain,
                OrgElementType value) {
            EObject input = getInput();
            if (input != null) {
                EReference ref = getEReference(input);
                if (ref != null) {
                    // Unset the value
                    return SetCommand.create(editingDomain,
                            input,
                            ref,
                            SetCommand.UNSET_VALUE);
                }
            }
            return null;
        }

        @Override
        protected Command getSetValueCommand(EditingDomain ed,
                OrgElementType value) {
            EObject input = getInput();
            if (input != null) {
                EReference ref = getEReference(input);

                if (ref != null) {
                    return SetCommand.create(ed, input, ref, value);
                }
            }
            return null;
        }

        @Override
        protected void postCommandExecution(Command cmd) {
            // Update the tabs to show/hide the attributes tab
            refreshTabs();
        }
    }

}
