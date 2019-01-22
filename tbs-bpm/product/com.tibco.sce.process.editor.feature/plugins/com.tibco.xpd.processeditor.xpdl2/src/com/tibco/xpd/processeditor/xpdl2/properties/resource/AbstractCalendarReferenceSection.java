/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.resource;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.analyst.resources.xpdl2.indexing.CalendarReferenceIndexProvider;
import com.tibco.xpd.analyst.resources.xpdl2.indexing.CalendarReferenceIndexProvider.CalendarReferenceIndex;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.DataFilterPicker;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.DataFilterPicker.DataPickerType;
import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.util.FixedValueFieldAssistHelper;
import com.tibco.xpd.processeditor.xpdl2.properties.util.FixedValueFieldAssistHelper.FixedValueFieldProposalProvider;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.properties.components.AbstractPickerControl;
import com.tibco.xpd.xpdExtension.CalendarReference;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Abstract class for the {@link CalendarReference} section in the properties
 * view.
 * 
 * @author njpatel
 * 
 */
@SuppressWarnings("restriction")
public abstract class AbstractCalendarReferenceSection extends
        AbstractFilteredTransactionalSection {

    private Button aliasBtn;

    private Text aliasTxt;

    private Button dataFieldBtn;

    private DataFieldPicker dataFieldPicker;

    private Button currentSelection;

    /**
     * @param eClass
     */
    public AbstractCalendarReferenceSection(EClass eClass) {
        super(eClass);
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {
        CalendarReference reference = getCalendarReference();
        boolean reset = false;
        if (reference != null) {
            if (reference.getDataFieldId() != null
                    && !reference.getDataFieldId().isEmpty()) {
                // Data field has been set
                ProcessRelevantData data =
                        getProcessRelevantData(reference.getDataFieldId());
                if (data != null) {
                    dataFieldPicker.setValue(data);
                } else {
                    // Data field does not exist - show unresolved reference
                    // error
                    dataFieldPicker
                            .setErrorText(Messages.AbstractCalendarReferenceSection_unresolvedReference_error_label);
                }
                updateText(aliasTxt, null);
                enableAlias(false);
            } else if (reference.getAlias() != null
                    && !reference.getAlias().isEmpty()) {
                // Alias has been set
                updateText(aliasTxt, reference.getAlias());
                dataFieldPicker.setValue(null);
                enableAlias(true);
            } else {
                reset = true;
            }
        } else {
            reset = true;
        }

        if (reset) {
            // No calendar reference set
            updateText(aliasTxt, null);
            dataFieldPicker.setValue(null);
        }
    }

    /**
     * Get the {@link CalendarReference} from the input of this section.
     * 
     * @return CalendarReference if set, <code>null</code> otherwise.
     */
    protected CalendarReference getCalendarReference() {
        CalendarReference ref = null;
        OtherElementsContainer container = getOtherElementsContainer();

        if (container != null) {
            return (CalendarReference) Xpdl2ModelUtil
                    .getOtherElement(container, XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_CalendarReference());
        }

        return ref;
    }

    /**
     * Get the command to set the calendar reference runtime identifier field on
     * the input of this section.
     * 
     * @param ed
     * @param data
     * @return
     */
    protected Command getSetDataFieldCommand(EditingDomain ed,
            ProcessRelevantData data) {
        Command cmd = null;
        OtherElementsContainer container = getOtherElementsContainer();

        if (container != null) {
            CalendarReference ref =
                    XpdExtensionFactory.eINSTANCE.createCalendarReference();
            ref.setDataFieldId(data.getId());
            cmd =
                    Xpdl2ModelUtil.getSetOtherElementCommand(ed,
                            container,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_CalendarReference(),
                            ref);
        }

        return cmd;
    }

    /**
     * Get the command to set the calendar reference alias on the input of this
     * section.
     * 
     * @param ed
     * @param alias
     * @return
     */
    protected Command getSetAliasCommand(EditingDomain ed, String alias) {
        Command cmd = null;
        OtherElementsContainer container = getOtherElementsContainer();

        if (container != null) {
            CalendarReference ref =
                    XpdExtensionFactory.eINSTANCE.createCalendarReference();
            ref.setAlias(alias);
            cmd =
                    Xpdl2ModelUtil.getSetOtherElementCommand(ed,
                            container,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_CalendarReference(),
                            ref);
        }
        return cmd;
    }

    /**
     * Get the command to clear the calendar reference from the input of this
     * section.
     * 
     * @param ed
     * @return
     */
    protected Command getClearCalendarReferenceCommand(EditingDomain ed) {
        Command cmd = null;
        OtherElementsContainer container = getOtherElementsContainer();

        if (container != null) {
            CalendarReference calendarReference = getCalendarReference();
            if (calendarReference != null) {
                cmd =
                        Xpdl2ModelUtil.getRemoveOtherElementCommand(ed,
                                container,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_CalendarReference(),
                                getCalendarReference());
            }
        }

        return cmd;
    }

    /**
     * Get the {@link OtherElementsContainer} to which the
     * {@link CalendarReference} will be added.
     * 
     * @return
     */
    protected abstract OtherElementsContainer getOtherElementsContainer();

    /**
     * Get the {@link ProcessRelevantData} with the given id.
     * 
     * @param id
     * @return data if found, <code>null</code> otherwise.
     */
    protected abstract ProcessRelevantData getProcessRelevantData(String id);

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doCreateControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param toolkit
     * @return
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = toolkit.createComposite(parent);
        root.setLayout(new GridLayout(2, false));

        Label label =
                toolkit.createLabel(root,
                        Messages.AbstractCalendarReferenceSection_section_longdesc,
                        SWT.WRAP);
        GridData data = new GridData(SWT.FILL, SWT.FILL, true, false);
        data.horizontalSpan = 2;
        data.verticalIndent = 5;
        label.setLayoutData(data);

        aliasBtn =
                toolkit.createButton(root,
                        Messages.AbstractCalendarReferenceSection_alias_label,
                        SWT.RADIO);
        aliasTxt = createContentAssistedAliasControl(root, toolkit);

        manageControlUpdateOnDeactivate(aliasTxt);

        dataFieldBtn =
                toolkit.createButton(root,
                        Messages.AbstractCalendarReferenceSection_runtimeIdentifierField_label,
                        SWT.RADIO);

        dataFieldPicker =
                new DataFieldPicker(root, SWT.NONE, toolkit, getEditingDomain());
        data = new GridData(SWT.LEFT, SWT.FILL, true, false);
        data.widthHint = 350;
        dataFieldPicker.setLayoutData(data);

        // Enable the Alias input by default
        aliasBtn.setSelection(true);
        dataFieldBtn.setSelection(false);
        dataFieldPicker.setEnabled(false);
        currentSelection = aliasBtn;

        manageControl(aliasBtn);
        manageControl(dataFieldBtn);

        return root;
    }

    /**
     * Create the content-assisted Alias text control.
     * 
     * @param parent
     * @param toolkit
     * @return
     */
    @SuppressWarnings("deprecation")
    private Text createContentAssistedAliasControl(Composite parent,
            XpdFormToolkit toolkit) {
        FixedValueFieldProposalProvider proposalProvider =
                new FixedValueFieldAssistHelper.FixedValueFieldProposalProvider() {
                    @SuppressWarnings("restriction")
                    @Override
                    public Object[] getProposals() {
                        Set<String> proposals = new HashSet<String>();

                        List<CalendarReferenceIndex> items =
                                CalendarReferenceIndexProvider
                                        .getIndexerItems(null);
                        for (CalendarReferenceIndex item : items) {
                            proposals.add(item.getAlias());
                        }
                        return proposals.toArray();
                    }
                };

        FixedValueFieldAssistHelper fieldAssistHelper =
                new FixedValueFieldAssistHelper(toolkit, parent,
                        proposalProvider, false);

        return (Text) fieldAssistHelper.getDecoratedField().getControl();
    }

    /**
     * Enable either the Alias input or Data Field input.
     * 
     * @param enable
     *            <code>true</code> to enable Alias input, <code>false</code>
     *            for data field input.
     */
    private void enableAlias(boolean enable) {
        if (aliasTxt.getEnabled() != enable) {
            aliasBtn.setSelection(enable);
            aliasTxt.setEnabled(enable);
            dataFieldBtn.setSelection(!enable);
            dataFieldPicker.setEnabled(!enable);

            if (enable) {
                aliasTxt.setFocus();
                currentSelection = aliasBtn;
            } else {
                dataFieldPicker.setFocus();
                currentSelection = dataFieldBtn;
            }
        }
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    protected Command doGetCommand(Object obj) {
        Command cmd = null;
        if (obj == aliasBtn || obj == dataFieldBtn) {
            if (currentSelection != obj) {
                // Enable the appropriate options
                enableAlias(aliasBtn.getSelection());

                // Clear the Calendar Reference as the reference choice has
                // changed
                CalendarReference reference = getCalendarReference();
                if (reference != null) {
                    cmd = getClearCalendarReferenceCommand(getEditingDomain());
                }

            }
        }
        if (obj == aliasTxt) {
            if (aliasTxt.getText().isEmpty()) {
                /*
                 * If a data field is already set then don't reset this as
                 * otherwise just tabbing out of the text control without
                 * entering anything will clear this value
                 */
                CalendarReference reference = getCalendarReference();
                if (reference == null || reference.getDataFieldId() == null
                        || reference.getDataFieldId().isEmpty()) {
                    cmd = getClearCalendarReferenceCommand(getEditingDomain());
                }
            } else {
                cmd =
                        getSetAliasCommand(getEditingDomain(), aliasTxt
                                .getText().trim());
            }
        }
        return cmd;
    }

    /**
     * Picker control to pick the Runtime Identifier Field.
     */
    private class DataFieldPicker extends
            AbstractPickerControl<ProcessRelevantData> {

        /**
         * @param parent
         * @param style
         * @param toolkit
         * @param editingDomain
         */
        public DataFieldPicker(Composite parent, int style,
                XpdFormToolkit toolkit, EditingDomain editingDomain) {
            super(parent, style, toolkit, editingDomain);
            setBrowseTooltip(Messages.AbstractCalendarReferenceSection_selectRuntimeIdentifierField_tooltip);
            setClearTooltip(Messages.AbstractCalendarReferenceSection_clearRuntimeIdentifierField_tooltip);
            setHyperlinkActive(false);
        }

        /**
         * @see com.tibco.xpd.ui.properties.components.AbstractPickerControl#doBrowse(org.eclipse.swt.widgets.Control)
         * 
         * @param control
         * @return
         */
        @Override
        protected ProcessRelevantData doBrowse(Control control) {
            DataFilterPicker picker =
                    new DataFilterPicker(control.getShell(),
                            DataPickerType.DATAFIELD_FORMALPARAMETER, false);
            picker.setScope(getInput());
            if (picker.open() == DataFilterPicker.OK) {
                Object result = picker.getFirstResult();
                if (result instanceof ProcessRelevantData) {
                    return (ProcessRelevantData) result;
                }
            }
            return null;
        }

        /**
         * @see com.tibco.xpd.ui.properties.components.AbstractPickerControl#getSetValueCommand(org.eclipse.emf.edit.domain.EditingDomain,
         *      java.lang.Object)
         * 
         * @param editingDomain
         * @param value
         * @return
         */
        @Override
        protected Command getSetValueCommand(EditingDomain editingDomain,
                ProcessRelevantData value) {
            if (editingDomain != null && value != null) {
                return getSetDataFieldCommand(editingDomain, value);
            }
            return null;
        }

        /**
         * @see com.tibco.xpd.ui.properties.components.AbstractPickerControl#getClearValueCommand(org.eclipse.emf.edit.domain.EditingDomain,
         *      java.lang.Object)
         * 
         * @param editingDomain
         * @param value
         * @return
         */
        @Override
        protected Command getClearValueCommand(EditingDomain editingDomain,
                ProcessRelevantData value) {
            return getClearCalendarReferenceCommand(editingDomain);
        }

    }

}
