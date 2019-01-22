/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.controls;

import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.globalSignalDefinition.GlobalSignal;
import com.tibco.xpd.globalSignalDefinition.util.GlobalSignalUtil;
import com.tibco.xpd.n2.globalsignal.resource.ui.commonpicker.GsdTypeQuery;
import com.tibco.xpd.n2.process.globalsignal.internal.Messages;
import com.tibco.xpd.resources.ui.picker.PickerService;
import com.tibco.xpd.resources.ui.picker.PickerTypeQuery;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Picker Control for Global Signals.
 * 
 * @author kthombar
 * @since Jan 29, 2015
 */
public abstract class GlobalSignalPickerControl extends Composite {
    /**
     * global Signal Text
     */
    private Text globalSignalText;

    /**
     * browse button
     */
    private Button browseGlobalSignals;

    /**
     * clear button
     */
    private Button clearButton;

    /**
     * the previous selected global signal
     */
    private Object previousSelectedGlobalSignal = null;

    /**
     * the current activity for the control.
     */
    private Activity activityForControl = null;

    /**
     * @param parent
     * @param style
     */
    public GlobalSignalPickerControl(Composite parent, XpdFormToolkit toolkit,
            String pickerTitle) {
        super(parent, SWT.NONE);

        toolkit.adapt(this);
        toolkit.paintBordersFor(this);

        createControl(toolkit);

    }

    /**
     * Set the previously selected global signal.
     * 
     * @param globalSignal
     *            the previous global signal name.
     * @param activityForControl
     *            the parent activity for the signal
     */
    protected void setInitiallySelectedGlobalSignal(String globalSignalName,
            Activity activityForControl) {
        this.activityForControl = activityForControl;

        if (globalSignalName != null) {
            GlobalSignal globalSignal =
                    GlobalSignalUtil.getGlobalSignalFromName(globalSignalName);

            previousSelectedGlobalSignal = globalSignal;

            if (globalSignal != null) {
                globalSignalText.setText(GlobalSignalUtil
                        .getSignalDisplayNameFromGlobalSignal(globalSignal));
            } else {
                /*
                 * XPD-7551: When we cannot find the global signal it does not
                 * mean that it does not exists, e.g. in case a GSD file is
                 * renamed, then upon undo-redo the GSD name changes but the
                 * indexers are not indexed as yet and hence
                 * 'GlobalSignalUtil.getGlobalSignalFromName' will return null
                 * as it fetches the Global Signal via the indexer. Hence we set
                 * the global signal text to the Signal name rather than
                 * displaying empty signal name, when the indexers are run and
                 * the global signal is available the name will be displayed
                 * appropriately.
                 */
                globalSignalText
                        .setText(String
                                .format(Messages.GlobalSignalPickerControl_UnresolvedRef_label,
                                        globalSignalName));
            }
        } else {
            /*
             * if no signal selected then set the previsous selected signal to
             * null
             */
            previousSelectedGlobalSignal = null;
            globalSignalText.setText(""); //$NON-NLS-1$
        }
    }

    /**
     * Set or unset the global signal. Based on the value of Global Signal the
     * user is expected to set or unset the signal in model.
     * 
     * @param globalSignal
     *            the global signal selected form the picker.
     */
    protected abstract void setUnsetGlobalSignal(GlobalSignal globalSignal);

    /**
     * Create the controls.
     * 
     * @param toolkit
     */
    protected void createControl(XpdFormToolkit toolkit) {
        GridLayout gl = new GridLayout(3, false);
        gl.marginWidth = 0;
        gl.marginHeight = 0;
        gl.horizontalSpacing = 0;
        this.setLayout(gl);

        globalSignalText = toolkit.createText(this, ""); //$NON-NLS-1$
        globalSignalText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        globalSignalText.setEditable(false);

        globalSignalText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL
                | GridData.VERTICAL_ALIGN_CENTER));

        Point sz = globalSignalText.computeSize(SWT.DEFAULT, SWT.DEFAULT);

        /*
         * Launch picker button.
         */
        browseGlobalSignals =
                toolkit.createButton(this,
                        Messages.GlobalSignalPickerControl_GlobalSignalPickerElipsis_label,
                        SWT.PUSH);

        GridData gd = new GridData(GridData.VERTICAL_ALIGN_CENTER);
        gd.heightHint = sz.y;
        browseGlobalSignals.setLayoutData(gd);

        browseGlobalSignals.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (activityForControl == null) {
                    throw new RuntimeException(
                            "Activity scope not set on picker control via the 'setInitiallySelectedGlobalSignal' method"); //$NON-NLS-1$
                }

                /*
                 * The query for the picker.
                 */
                PickerTypeQuery[] queries =
                        new PickerTypeQuery[] { new GsdTypeQuery(
                                GsdTypeQuery.QUERY_GLOBAL_SIGNAL) };

                IFilter[] filters = new IFilter[] {};

                /*
                 * open picker.
                 */
                Object result =
                        PickerService
                                .getInstance()
                                .openSinglePickerDialog(Messages.GlobalSignalPickerControl_GlobalSignalPicker_title,
                                        getShell(),
                                        queries,
                                        null,
                                        null,
                                        null,
                                        filters,
                                        previousSelectedGlobalSignal);

                if (result != null
                        && !result.equals(previousSelectedGlobalSignal)) {
                    /*
                     * proceed if the user has selected a different global
                     * signal.
                     */
                    if (result instanceof GlobalSignal) {
                        /*
                         * Ask user to add necesary project reference.
                         */
                        boolean checkAndAddProjectReference =
                                ProcessUIUtil
                                        .checkAndAddProjectReference(getShell(),
                                                activityForControl,
                                                (GlobalSignal) result);
                        if (checkAndAddProjectReference) {
                            /*
                             * Proceed only if the user chooses to add a project
                             * reference or there is already a project reference
                             * to the gsd project.
                             */
                            setUnsetGlobalSignal((GlobalSignal) result);
                        }
                    }
                }
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
            }
        });

        clearButton =
                toolkit.createButton(this,
                        Messages.GlobalSignalPickerControl_GlobalSignalPickerClearButton_label,
                        SWT.PUSH);

        gd = new GridData(GridData.VERTICAL_ALIGN_CENTER);
        gd.heightHint = sz.y;
        clearButton.setLayoutData(gd);

        clearButton.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                /*
                 * Unset the global signal.
                 */
                setUnsetGlobalSignal(null);
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
            }
        });
    }
}
