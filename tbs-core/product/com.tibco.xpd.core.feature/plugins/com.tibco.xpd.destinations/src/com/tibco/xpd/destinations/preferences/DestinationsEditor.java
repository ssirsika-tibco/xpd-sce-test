/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.destinations.preferences;

import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.destinations.DestinationsActivator;
import com.tibco.xpd.destinations.internal.Messages;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.ui.preferences.PreferenceStoreKeys;
import com.tibco.xpd.ui.util.MessageDialogUtil;

/**
 * @author nwilson
 * 
 */
public class DestinationsEditor extends FieldEditor {

    private DestinationPreferences preferences;

    private Composite container;

    private DestinationEnvironmentsViewer destinations;

    private DestinationComponentsViewer components;

    public DestinationsEditor(Composite parent) {
        super(DestinationPreferences.PREFERENCE,
                "Destination Environments", parent); //$NON-NLS-1$
    }

    private DestinationPreferences getPreferences() {
        if (preferences == null) {
            preferences = new DestinationPreferences();
        }
        return preferences;
    }

    @Override
    protected void adjustForNumColumns(int numColumns) {
        ((GridData) container.getLayoutData()).horizontalSpan = numColumns;
    }

    @Override
    protected void doFillIntoGrid(Composite parent, int numColumns) {
        if (container == null) {
            container = new Composite(parent, SWT.NONE);
            GridData gd =
                    new GridData(SWT.FILL, SWT.FILL, true, true, numColumns, 1);
            container.setLayoutData(gd);

            container.setLayout(new GridLayout());

            destinations = new DestinationEnvironmentsViewer(container);
            destinations.getControl().setLayoutData(new GridData(SWT.FILL,
                    SWT.FILL, true, true));
            components = new DestinationComponentsViewer(container);
            components.getControl().setLayoutData(new GridData(SWT.FILL,
                    SWT.FILL, true, true));

            destinations.setInput(getPreferences());
            components.setPreferences(getPreferences());
            components.setInput(null);

            destinations
                    .addSelectionChangedListener(new ISelectionChangedListener() {

                        public void selectionChanged(SelectionChangedEvent event) {
                            ISelection selection = event.getSelection();
                            Object input = null;
                            if (selection instanceof IStructuredSelection) {
                                IStructuredSelection structured =
                                        (IStructuredSelection) selection;
                                if (structured.size() == 1) {
                                    input = structured.getFirstElement();
                                }
                            }
                            components.setInput(input);
                        }

                    });
        }
    }

    @Override
    protected void doLoad() {
        IPreferenceStore store = getPreferenceStore();
        String s = store.getString(DestinationPreferences.PREFERENCE);
        if (s != null && s.length() != 0) {
            preferences.parse(s);
        }
        destinations.setInput(getPreferences());
        components.setPreferences(getPreferences());
        components.setInput(null);
    }

    @Override
    protected void doLoadDefault() {
        getPreferences().restoreDefaults();
        setPresentsDefaultValue(false);
    }

    @Override
    protected void doStore() {
        String s = getPreferences().toString();
        if (s != null && s.length() != 0) {
            DestinationPreferences live =
                    DestinationsActivator.getDefault()
                            .getDestinationPreferences();
            String old = live.toString();
            if (!s.equals(old)) {
                getPreferenceStore().setValue(getPreferenceName(), s);
                DestinationsActivator.getDefault().getDestinationPreferences()
                        .parse(s);
                String title = Messages.DestinationsEditor_RebuildDialogTitle;
                String message = Messages.DestinationsEditor_RebuildDialogMessage;
                String toggleMessage = Messages.DestinationsEditor_RebuildDialog_BuildWithoutAsking_Label;
               /*
                 * Ask user if they wish to build
                 */
                int returnCode =
                        MessageDialogUtil
                                .openYesNoQuestion(PlatformUI.getWorkbench()
                                        .getActiveWorkbenchWindow().getShell(),
                                        title,
                                        message,
                                        toggleMessage,
                                        false,
                                        PreferenceStoreKeys.DONT_ASK_TO_BUILD_WHEN_CHANGING_DESTINATIONENVIRONMENTS);
                if (returnCode == 2) {
                    Job job = new Job(Messages.DestinationsEditor_RebuildJobName) {

                        @Override
                        protected IStatus run(IProgressMonitor monitor) {
                            IStatus status;
                            try {
                                ResourcesPlugin
                                        .getWorkspace()
                                        .build(IncrementalProjectBuilder.FULL_BUILD,
                                                monitor);
                                status = Status.OK_STATUS;
                            } catch (CoreException e) {
                                status =
                                        new Status(
                                                IStatus.ERROR,
                                                DestinationsActivator.PLUGIN_ID,
                                                e.getMessage(), e);
                            }
                            return status;
                        }

                    };
                    job.schedule();
                }
            }
        }
    }

    @Override
    public int getNumberOfControls() {
        return 1;
    }

}
