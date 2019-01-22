/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.properties.general;

import java.math.BigInteger;
import java.util.Iterator;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.globalSignalDefinition.GlobalSignal;
import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitionPackage;
import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions;
import com.tibco.xpd.n2.globalsignal.resource.internal.Messages;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.properties.AbstractTransactionalSection;
import com.tibco.xpd.ui.properties.DigitTextVerifyListener;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdl2.edit.ui.Xpdl2UiPlugin;

/**
 * General property section for Global Signals.
 * 
 * @author sajain
 * @since Feb 5, 2015
 */
public class GlobalSignalPropertySection extends AbstractTransactionalSection
        implements IFilter {

    private static final int NO_OF_COLUMNS = 3;

    /**
     * Signal name label.
     */
    private CLabel lblSignalName;

    /**
     * Text control for Global Signal name.
     */
    private Text txtSignalName;

    /**
     * Text control for Global Signal timeout value.
     */
    private Text txtTimeoutValue;

    /**
     * Text control for Global Signal desccrption.
     */
    private Text txtDescription;

    /**
     * Button to set Global Signal to 'Correlate immediate'.
     */
    private Button btnCorrelateImmediately;

    /**
     * Button to set Global Signal to timeout after a period of time which is
     * specified through timeout value.
     */
    private Button btnTimeoutSignalAfter;

    /**
     * Error message.
     */
    protected String err = null;

    /**
     * Flag to inidicate whether signal name is valid or not.
     */
    protected boolean nameValid = true;

    /**
     * General property section for Global Signals.
     */
    public GlobalSignalPropertySection() {

    }

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

        /*
         * Create root container.
         */
        Composite rootContainer = toolkit.createComposite(parent);
        rootContainer.setLayout(new GridLayout(1, true));

        GridData gridData = null;

        /*
         * Create container of signal name controls.
         */
        Composite nameContainer = toolkit.createComposite(rootContainer);
        GridLayout layout = new GridLayout(NO_OF_COLUMNS, false);
        // layout.numColumns = NO_OF_COLUMNS;
        nameContainer.setLayout(layout);
        nameContainer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        /*
         * Label for Signal name.
         */
        lblSignalName =
                toolkit.createCLabel(nameContainer,
                        Messages.NewGlobalSignalWizardPage_SignalName_label);
        lblSignalName
                .setLayoutData(new GridData(GridData.VERTICAL_ALIGN_CENTER));
        lblSignalName.setImage(Xpdl2UiPlugin.getDefault().getImageRegistry()
                .get(Xpdl2UiPlugin.IMG_ERROR));

        /*
         * Text box for signal name.
         */
        txtSignalName = toolkit.createText(nameContainer, ""); //$NON-NLS-1$
        gridData =
                new GridData(GridData.FILL_HORIZONTAL
                        | GridData.VERTICAL_ALIGN_CENTER);
        txtSignalName.setLayoutData(gridData);
        manageControl(txtSignalName);

        /*
         * Container for signal correlation controls.
         */
        Composite correlateControlsContainer =
                toolkit.createComposite(rootContainer);
        layout = new GridLayout();
        layout.numColumns = 5;
        correlateControlsContainer.setLayout(layout);
        correlateControlsContainer.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));

        /*
         * Label for signal correlation.
         */
        Label lblCorrelation =
                toolkit.createLabel(correlateControlsContainer,
                        Messages.NewGlobalSignalWizardPage_Correlation_label,
                        SWT.NONE);
        gridData = new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false);
        lblCorrelation.setLayoutData(gridData);

        /*
         * Button 'Correlation immediately'.
         */
        btnCorrelateImmediately =
                toolkit.createButton(correlateControlsContainer,
                        Messages.NewGlobalSignalWizardPage_CorrelateImmediately_label,
                        SWT.RADIO);
        gridData = new GridData();
        btnCorrelateImmediately.setLayoutData(gridData);
        btnCorrelateImmediately.setSelection(true);
        btnCorrelateImmediately
                .setToolTipText(Messages.NewGlobalSignalWizardPage_CorrelateImmediately_tooltip);
        manageControl(btnCorrelateImmediately);

        /*
         * Button 'Timeout Signal After'.
         */
        btnTimeoutSignalAfter =
                toolkit.createButton(correlateControlsContainer,
                        Messages.NewGlobalSignalWizardPage_TimeoutSignalAfter_label,
                        SWT.RADIO);
        gridData = new GridData();
        btnTimeoutSignalAfter.setLayoutData(gridData);
        btnTimeoutSignalAfter.setSelection(false);
        btnTimeoutSignalAfter
                .setToolTipText(Messages.NewGlobalSignalWizardPage_TimeoutSignalAfter_tooltip);
        manageControl(btnTimeoutSignalAfter);

        /*
         * Text for timeout value.
         */
        txtTimeoutValue = toolkit.createText(correlateControlsContainer, ""); //$NON-NLS-1$
        gridData = new GridData(GridData.BEGINNING);
        gridData.widthHint = 60;
        txtTimeoutValue.setLayoutData(gridData);
        txtTimeoutValue.setEnabled(false);
        txtTimeoutValue
                .setToolTipText(Messages.GlobalSignalSection_TimeoutValueText_Tooltip);
        txtTimeoutValue.addVerifyListener(new DigitTextVerifyListener());
        manageControlUpdateOnDeactivate(txtTimeoutValue);

        /*
         * Label 'seconds'.
         */
        Label lblSeconds =
                toolkit.createLabel(correlateControlsContainer,
                        Messages.NewGlobalSignalWizardPage_Seconds_label);
        gridData = new GridData();
        lblSeconds.setLayoutData(gridData);

        /*
         * Create composite for description controls.
         */
        Composite descComposite = toolkit.createComposite(rootContainer);
        GridLayout gl = new GridLayout(2, false);
        descComposite.setLayout(gl);
        descComposite.setLayoutData(new GridData(GridData.FILL, GridData.FILL,
                true, true));

        /*
         * Description label.
         */
        Label lblDescription =
                toolkit.createLabel(descComposite,
                        Messages.GSDFilePropertSection_DescriptionLabel);
        lblDescription.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING,
                false, false));

        /*
         * Description text.
         */
        txtDescription = toolkit.createText(descComposite, "", SWT.MULTI); //$NON-NLS-1$
        txtDescription.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
                true));
        manageControl(txtDescription);

        return rootContainer;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {

        /*
         * Get global signal.
         */
        GlobalSignal gs = getGlobalSignal();

        /*
         * Global signal should not be null.
         */
        if (gs != null) {

            /*
             * Populate Signal name text control.
             */
            if (null != gs.getName()) {

                updateText(txtSignalName, gs.getName());
                verifyName(gs.getName());

            } else {

                updateText(txtSignalName, ""); //$NON-NLS-1$

            }

            /*
             * Handle correlation controls.
             */
            if (null != gs.getCorrelationTimeout()) {

                btnCorrelateImmediately.setSelection(false);
                btnTimeoutSignalAfter.setSelection(true);
                txtTimeoutValue.setEnabled(true);

                if (!BigInteger.ZERO.equals(gs.getCorrelationTimeout())) {

                    updateText(txtTimeoutValue, gs.getCorrelationTimeout()
                            .toString());
                } else {
                    updateText(txtTimeoutValue, ""); //$NON-NLS-1$
                }

            } else {

                btnCorrelateImmediately.setSelection(true);
                btnTimeoutSignalAfter.setSelection(false);
                txtTimeoutValue.setEnabled(false);
                updateText(txtTimeoutValue, ""); //$NON-NLS-1$
            }

            /*
             * Populate Signal description text control.
             */
            if (null != gs.getDescription()) {

                updateText(txtDescription, gs.getDescription());

            } else {

                updateText(txtDescription, ""); //$NON-NLS-1$

            }
        }
    }

    /**
     * Get GlobalSignal from the input.
     * 
     * @return <code>GlobalSignal</code> if input is valid, <b>null</b>
     *         otherwise.
     */
    private GlobalSignal getGlobalSignal() {

        Object o = getInput();

        if (o instanceof GlobalSignal) {

            GlobalSignal gs = (GlobalSignal) o;

            return gs;
        }
        return null;
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

        EditingDomain ed = getEditingDomain();

        GlobalSignal gs = getGlobalSignal();

        if (obj == txtDescription) {

            /*
             * Handle description text control.
             */
            if (txtDescription.getText() != null) {

                cmd = new CompoundCommand();

                ((CompoundCommand) cmd).append(SetCommand.create(ed,
                        gs,
                        GlobalSignalDefinitionPackage.eINSTANCE
                                .getGlobalSignal_Description(),
                        txtDescription.getText()));
            }

        } else if (obj == txtSignalName) {

            /*
             * Handle signal name text control.
             */
            if (txtSignalName.getText() != null) {

                cmd = new CompoundCommand();

                ((CompoundCommand) cmd).append(SetCommand.create(ed,
                        gs,
                        GlobalSignalDefinitionPackage.eINSTANCE
                                .getGlobalSignal_Name(),
                        txtSignalName.getText()));
            }

        } else if (obj == txtTimeoutValue) {

            /*
             * Handle timeout value text control.
             */
            if (txtTimeoutValue.getText() != null
                    && txtTimeoutValue.getText().length() > 0) {

                cmd = new CompoundCommand();

                try {

                    ((CompoundCommand) cmd).append(SetCommand.create(ed,
                            gs,
                            GlobalSignalDefinitionPackage.eINSTANCE
                                    .getGlobalSignal_CorrelationTimeout(),
                            BigInteger.valueOf(Integer.parseInt(txtTimeoutValue
                                    .getText()))));

                } catch (NumberFormatException e) {

                    /*
                     * XPD-7591: Saket: The maximum value we can have for the
                     * global signal correlation timeout is 86399, so reset to
                     * empty string.
                     */
                    updateText(txtTimeoutValue, ""); //$NON-NLS-1$
                }
            } else {
                cmd = new CompoundCommand();

                ((CompoundCommand) cmd).append(SetCommand.create(ed,
                        gs,
                        GlobalSignalDefinitionPackage.eINSTANCE
                                .getGlobalSignal_CorrelationTimeout(),
                        BigInteger.ZERO));
            }

        } else if (obj == btnCorrelateImmediately) {

            /*
             * Handle 'Correlate immediately' radio button control.
             */
            if (btnCorrelateImmediately.getSelection()) {

                if (gs.getCorrelationTimeout() != null) {

                    cmd = new CompoundCommand();

                    ((CompoundCommand) cmd).append(SetCommand.create(ed,
                            gs,
                            GlobalSignalDefinitionPackage.eINSTANCE
                                    .getGlobalSignal_CorrelationTimeout(),
                            SetCommand.UNSET_VALUE));
                }

                txtTimeoutValue.setEnabled(false);
            }

        } else if (obj == btnTimeoutSignalAfter) {

            /*
             * Handle 'Timeout After' radio button control.
             */
            if (btnTimeoutSignalAfter.getSelection()) {

                txtTimeoutValue.setEnabled(true);

                cmd = new CompoundCommand();

                ((CompoundCommand) cmd).append(SetCommand.create(ed,
                        gs,
                        GlobalSignalDefinitionPackage.eINSTANCE
                                .getGlobalSignal_CorrelationTimeout(),
                        BigInteger.ZERO));
            }

        }

        return cmd;
    }

    /**
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     * 
     * @param toTest
     * @return
     */
    @Override
    public boolean select(Object toTest) {
        if (toTest instanceof GlobalSignal) {
            return true;
        }
        return false;
    }

    /**
     * Verify that the specified name text follows all naming conventions.
     * 
     * @param nameText
     */
    protected void verifyName(String nameText) {

        nameValid = true;

        if ((nameText == null || nameText.length() == 0)) {

            nameValid = false;
            err = Messages.GlobalSignalPropertySection_NameEmpty;

        } else if (!NameUtil.isValidName(nameText, false)) {

            nameValid = false;
            err = Messages.GSDElements_invalidNameErrorMessage;

        } else {

            if (getInputContainer() instanceof GlobalSignalDefinitions) {

                if (getDuplicateNameObject(((GlobalSignalDefinitions) getInputContainer())
                        .getGlobalSignals(),
                        getInput(),
                        nameText) != null) {

                    nameValid = false;
                    err = Messages.GlobalSignalPropertySection_NameMustBeUnique;

                }

            } else if (getInputContainer() instanceof GlobalSignal) {

                WorkingCopy wc =
                        WorkingCopyUtil.getWorkingCopyFor(getInputContainer());

                if (wc.getRootElement() instanceof GlobalSignalDefinitions) {

                    if (getDuplicateNameObject(((GlobalSignalDefinitions) (wc.getRootElement()))
                            .getGlobalSignals(),
                            getInput(),
                            nameText) != null) {
                        nameValid = false;
                        err =
                                Messages.GlobalSignalPropertySection_NameMustBeUnique;
                    }
                }

            }
        }

        if (!nameValid) {

            lblSignalName.setImage(Xpdl2UiPlugin.getDefault()
                    .getImageRegistry().get(Xpdl2UiPlugin.IMG_ERROR));

            lblSignalName.setToolTipText(err);
            lblSignalName.getParent().layout(true);
            setCanFinish(false, err);

        } else {

            lblSignalName.setImage(null);
            lblSignalName.setToolTipText(""); //$NON-NLS-1$
            lblSignalName.getParent().layout(true);
            setCanFinish(true, Messages.NewGlobalSignalWizard_PAGEDESCRIPTION);
        }

    }

    /**
     * Get a duplicate named object in the specified list of global signals (if
     * it exists), else return null.
     * 
     * @param globalSignals
     *            List of global signals to look in.
     * @param input
     *            EObject whose duplicate is to found out.
     * @param nameText
     *            Name text
     * 
     * @return a duplicate named object in the specified list of global signals
     *         (if it exists), else return null.
     */
    private EObject getDuplicateNameObject(EList<GlobalSignal> globalSignals,
            EObject input, String nameText) {

        /*
         * Search for duplicate.
         */

        for (Iterator iter = globalSignals.iterator(); iter.hasNext();) {

            GlobalSignal eachGs = (GlobalSignal) iter.next();
            GlobalSignal gs = (GlobalSignal) input;

            if (eachGs != gs) {

                if (nameText.equals(eachGs.getName())) {

                    return eachGs;
                }
            }
        }

        return null;

    }

}
