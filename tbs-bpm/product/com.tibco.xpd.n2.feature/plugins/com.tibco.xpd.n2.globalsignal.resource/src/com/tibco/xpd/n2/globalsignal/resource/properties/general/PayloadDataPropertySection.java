/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.properties.general;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import com.tibco.xpd.bom.resources.ui.commonpicker.BOMTypeQuery;
import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitionPackage;
import com.tibco.xpd.globalSignalDefinition.PayloadDataField;
import com.tibco.xpd.globalSignalDefinition.impl.PayloadDataFieldImpl;
import com.tibco.xpd.n2.globalsignal.resource.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.general.DataFieldPropertySection;
import com.tibco.xpd.ui.properties.ExpandableSectionStacker;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Property section for payload data.
 * 
 * @author aprasad
 * @since Feb 18, 2015
 */
public class PayloadDataPropertySection extends DataFieldPropertySection {

    /**
     * Button to use the payload for signal correlation.
     */
    private Button btnUseForSignalCorrelation;

    /**
     * Label 'Mandatory'.
     */
    private Label labelMandatory;

    /**
     * Button to set payload data as mandatory.
     */
    private Button btnMandatory;

    /**
     * Pre-type controls container.
     */
    private Composite preTypeControlsContainer;

    /**
     * Property section for payload data.
     */
    public PayloadDataPropertySection() {

        /*
         * XPD-7559: Saket: Don't show the references and initial values section
         * for payload data.
         */
        super(GlobalSignalDefinitionPackage.eINSTANCE.getPayloadDataField(),
                false, false);

        setShowArrayField(true);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.general.DataFieldPropertySection#doCreatePreTypeControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param toolkit
     */
    @Override
    protected void doCreatePreTypeControls(Composite parent,
            XpdFormToolkit toolkit) {

        preTypeControlsContainer = parent;

        /*
         * Label 'Use for signal correlation'.
         */
        Label labelUseForSignalCorrelation =
                toolkit.createLabel(preTypeControlsContainer,
                        Messages.PayloadDataPropertySection_UseForSignalCorrelationLabel);
        labelUseForSignalCorrelation.setLayoutData(new GridData());

        /*
         * Button 'Use for signal correlation'.
         */
        btnUseForSignalCorrelation =
                toolkit.createButton(preTypeControlsContainer, "", SWT.CHECK); //$NON-NLS-1$
        btnUseForSignalCorrelation.setLayoutData(new GridData());
        btnUseForSignalCorrelation
                .setToolTipText(Messages.PayloadDataPropertySection_UseForSignalCorrelationTooltip);

        manageControl(btnUseForSignalCorrelation);

        /*
         * Label 'Mandatory'.
         */
        labelMandatory =
                toolkit.createLabel(preTypeControlsContainer,
                        Messages.PayloadDataPropertySection_IsMandatoryLabel);
        labelMandatory.setLayoutData(new GridData());

        /*
         * Button 'Mandatory'.
         */
        btnMandatory =
                toolkit.createButton(preTypeControlsContainer, "", SWT.CHECK); //$NON-NLS-1$
        btnMandatory.setLayoutData(new GridData());
        btnMandatory
                .setToolTipText(Messages.PayloadDataPropertySection_IsMandatoryTooltip);

        manageControl(btnMandatory);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.general.DataFieldPropertySection#doGetCommand(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    public Command doGetCommand(Object obj) {

        Command cmd = super.doGetCommand(obj);

        EditingDomain ed = getEditingDomain();

        PayloadDataField pdf = getPayloadData();

        if (obj == btnMandatory) {

            /*
             * Handle Mandatory button control.
             */

            cmd = new CompoundCommand();

            ((CompoundCommand) cmd).append(SetCommand.create(ed,
                    pdf,
                    GlobalSignalDefinitionPackage.eINSTANCE
                            .getPayloadDataField_Mandatory(),
                    btnMandatory.getSelection()));

        } else if (obj == btnUseForSignalCorrelation) {

            /*
             * Handle 'Use for signal correlation' button control.
             */

            cmd = new CompoundCommand();

            ((CompoundCommand) cmd).append(SetCommand.create(ed,
                    getDataField(),
                    Xpdl2Package.eINSTANCE.getDataField_Correlation(),
                    btnUseForSignalCorrelation.getSelection()));

            if (btnUseForSignalCorrelation.getSelection()) {

                /*
                 * Unset 'Mandatory' attribute while setting the 'Correlation'
                 * attribute.
                 */
                ((CompoundCommand) cmd).append(SetCommand.create(ed,
                        pdf,
                        GlobalSignalDefinitionPackage.eINSTANCE
                                .getPayloadDataField_Mandatory(),
                        false));
            }
        }

        return cmd;
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.properties.general.BaseTypeSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {

        super.doRefresh();

        PayloadDataField pdf = getPayloadData();

        DataField df = getDataField();

        boolean isPdfCorrelational = df.isCorrelation();

        btnMandatory.setSelection(pdf.isMandatory());
        btnUseForSignalCorrelation.setSelection(isPdfCorrelational);

        /*
         * XPD-7627: Saket: Hide 'Mandatory' control for correlation payloads.
         */
        showOrHideMandatoryControl(!isPdfCorrelational, pdf);

    }

    /**
     * Show/Hide 'Mandatory' checkbox for a Payload Data.
     * 
     * @param show
     *            Pass <code>true</code> if we want to show the 'Mandatory'
     *            checkbox for Payload Data, <code>false</code> otherwise.
     * @param pdf
     *            Payload data field selected.
     */
    private void showOrHideMandatoryControl(boolean show, PayloadDataField pdf) {

        GridData gd1 = (GridData) (labelMandatory.getLayoutData());
        gd1.exclude = !show;
        labelMandatory.setLayoutData(gd1);

        GridData gd2 = (GridData) (btnMandatory.getLayoutData());
        gd2.exclude = !show;
        btnMandatory.setLayoutData(gd2);

        labelMandatory.setVisible(show);
        btnMandatory.setVisible(show);

        if (null != preTypeControlsContainer.getParent()) {

            if (null != preTypeControlsContainer.getParent().getParent()) {

                if (null != preTypeControlsContainer.getParent().getParent()
                        .getParent()) {

                    preTypeControlsContainer.getParent().getParent()
                            .getParent().layout();
                }
            }
        }
    }

    /**
     * Get the DataField input
     * 
     * @return
     */
    @Override
    protected DataField getDataField() {

        if (getInput() instanceof PayloadDataField) {

            return ((PayloadDataField) getInput());
        }
        return null;
    }

    /**
     * Get PayloadData from the input.
     * 
     * @return <code>PayloadData</code> if input is valid, <b>null</b>
     *         otherwise.
     */
    private PayloadDataField getPayloadData() {

        Object o = getInput();

        if (o instanceof PayloadDataField) {

            PayloadDataField pdf = (PayloadDataField) o;

            return pdf;
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.general.DataFieldPropertySection#contributeToExpandables(com.tibco.xpd.ui.properties.ExpandableSectionStacker)
     * 
     * @param expandableHeaderController2
     */
    @Override
    protected void contributeToExpandables(
            ExpandableSectionStacker expandableHeaderController2) {
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.general.DataFieldPropertySection#doCreateInitialValuesControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param toolkit
     * @return
     */
    @Override
    protected Control doCreateInitialValuesControls(Composite parent,
            XpdFormToolkit toolkit) {
        return parent;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.general.DataFieldPropertySection#select(java.lang.Object)
     * 
     * @param toTest
     * @return
     */
    @Override
    public boolean select(Object toTest) {

        if (toTest instanceof PayloadDataField) {

            if (toTest.getClass() == PayloadDataFieldImpl.class) {

                return true;
            }
        }

        return false;
    }

    /**
     * Sid ACE-5361 Disallow selection of enumeation type for global signal data.
     * 
     * @retrun String[] bomTypeFilter Set of string constants as defined in {@link BOMTypeQuery} of each type to appear
     *         on picker.
     */
    @Override
    protected String[] getBOMTypeFilter() {
        /* Sid ACE-5361 allow subclass to set filter. */
        return new String[] { BOMTypeQuery.CLASS_TYPE, BOMTypeQuery.PRIMITIVE_TYPE, 
                BOMTypeQuery.CASE_CLASS_TYPE, BOMTypeQuery.GLOBAL_CLASS_TYPE };
    }
}
