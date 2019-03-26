/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.destinations.ui.properties;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.tibco.xpd.destinations.DestinationsActivator;
import com.tibco.xpd.destinations.preferences.DestinationPreferences;
import com.tibco.xpd.destinations.preferences.DestinationPreferencesEvent;
import com.tibco.xpd.destinations.preferences.DestinationPreferencesListener;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.destinations.ui.internal.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.Destination;
import com.tibco.xpd.resources.projectconfig.Destinations;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.util.UserInfoUtil;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Property sheet to list possible destination environments and allow them to be
 * modified.
 * 
 * @author NWilson
 */
public class GlobalDestinationsPropertySection extends
        AbstractFilteredTransactionalSection {

    /** Default name column width. */
    private static final int NAME_COLUMN_WIDTH = 1000;

    private static final String TABLE_ITEM_DESTINATION_KEY =
            "TABLE_ITEM_DESTINATION_KEY";//$NON-NLS-1$

    /** The destination environments. */
    private Table destinations;

    private DestinationPreferences preferences;

    private DestinationPreferencesListener preferenceListener;

    private Set<String> lastRefreshEnabledDestinations = null;

    private Object lastEnabledDestinationsStoredForInput = null;

    /**
     * Constructor.
     */
    public GlobalDestinationsPropertySection() {
        super(Xpdl2Package.eINSTANCE.getProcess());
    }

    /**
     * @param parent
     *            The parent composite.
     * @param toolkit
     *            The section toolkit.
     * @return The root control of this section.
     * @see com.tibco.xpd.ui.properties.BaseXpdSection#doCreateControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     */
    @Override
    public Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = toolkit.createComposite(parent);
        root.setLayout(new GridLayout(1, false));

        toolkit.createLabel(root,
                Messages.DestinationEnvironmentsPropertySection_DestEnvs_title);
        destinations =
                toolkit.createTable(root,
                        SWT.FULL_SELECTION | SWT.CHECK,
                        "globalDestinationsPropertyTable"); //$NON-NLS-1$
        destinations.setLayoutData(new GridData(GridData.FILL_BOTH));
        TableColumn description = new TableColumn(destinations, SWT.LEFT);
        description
                .setText(Messages.DestinationEnvironmentsPropertySection_Envs_title);

        TableLayout tl = new TableLayout();
        tl.addColumnData(new ColumnWeightData(100, 100, false));
        destinations.setLayout(tl);

        preferences =
                DestinationsActivator.getDefault().getDestinationPreferences();
        preferenceListener = new DestinationPreferencesListener() {

            @Override
            public void destinationPreferencesChanged(
                    DestinationPreferencesEvent event) {
                // refreshContent();
                refresh();
            }

        };
        preferences.addDestinationPreferencesListener(preferenceListener);
        manageControl(destinations);
        return root;
    }

    @Override
    public void dispose() {
        if (preferences != null) {
            preferences
                    .removeDestinationPreferencesListener(preferenceListener);
        }
    }

    /**
     * @param obj
     *            The command object.
     * @return The command to execute.
     * @see com.tibco.xpd.ui.properties.BaseXpdSection#doGetCommand(java.lang.Object)
     */
    @Override
    public Command doGetCommand(Object obj) {
        EObject input = getInput();
        Command command = null;

        if (obj instanceof TableItem) {
            TableItem item = (TableItem) obj;
            String name = (String) item.getData(TABLE_ITEM_DESTINATION_KEY);
            if (name != null) {
                EditingDomain ed = getEditingDomain();

                boolean enabled = item.getChecked();

                if (input instanceof Process) {
                    command =
                            DestinationUtil.getSetDestinationEnabledCommand(ed,
                                    (Process) input,
                                    name,
                                    enabled);

                } else if (input instanceof ProcessInterface) {
                    command =
                            DestinationUtil.getSetDestinationEnabledCommand(ed,
                                    (ProcessInterface) input,
                                    name,
                                    enabled);
                } else if (input instanceof Package) {
                    command =
                            DestinationUtil.getSetDestinationEnabledCommand(ed,
                                    (Package) input,
                                    name,
                                    enabled);
                }
            }
        }
        return command;
    }

    /**
     * Refreshes the table.
     * 
     * @see com.tibco.xpd.ui.properties.BaseXpdSection#doRefresh()
     */
    @Override
    protected void doRefresh() {
        EObject input = getInput();
        if (input == null) {
            return;
        }
        if (destinations != null && !destinations.isDisposed()) {
            destinations.removeAll();
            List<String> destinationList = preferences.getGlobalDestinations();

            for (String destination : destinationList) {
                TableItem item = new TableItem(destinations, SWT.NONE);
                item.setData(TABLE_ITEM_DESTINATION_KEY, destination);
                item.setText(destination);
            }

            Set<String> enabledSet =
                    DestinationUtil.getEnabledGlobalDestinations(input);

            for (String enabled : enabledSet) {
                if (!destinationList.contains(enabled)) {
                    TableItem item = new TableItem(destinations, SWT.NONE);
                    item.setData(TABLE_ITEM_DESTINATION_KEY, enabled);
                    item.setText(enabled
                            + " " + Messages.NonExistentDestination);//$NON-NLS-1$
                    item.setForeground(Display.getDefault()
                            .getSystemColor(SWT.COLOR_RED));
                }
            }

            Set<String> currentlyEnabledDestinations = new HashSet<String>();

            TableItem[] items = destinations.getItems();
            for (int i = 0; i < items.length; i++) {
                String name =
                        (String) items[i].getData(TABLE_ITEM_DESTINATION_KEY);

                boolean enabled = false;

                if (input instanceof Package) {
                    enabled =
                            DestinationUtil
                                    .isGlobalDestinationEnabled((Package) input,
                                            name);
                } else if (input instanceof Process) {
                    enabled =
                            DestinationUtil
                                    .isGlobalDestinationEnabled((Process) input,
                                            name);
                } else if (input instanceof ProcessInterface) {
                    enabled =
                            DestinationUtil
                                    .isGlobalDestinationEnabled((ProcessInterface) input,
                                            name);
                }

                items[i].setChecked(enabled);

                if (enabled) {
                    currentlyEnabledDestinations.add(name);
                }
            }

            /*
             * Sid XPD-2516 Refresh tabs when destinations have changed (because
             * tabs that appear could be destination specific.
             */
            if (!ContainerType.WIZARD.equals(getSectionContainerType())) {
                lastEnabledDestinationsStoredForInput = getInput();

                if (lastRefreshEnabledDestinations == null) {
                    lastRefreshEnabledDestinations =
                            currentlyEnabledDestinations;

                } else if (currentlyEnabledDestinations.size() != lastRefreshEnabledDestinations
                        .size()
                        || !currentlyEnabledDestinations
                                .containsAll(lastRefreshEnabledDestinations)) {
                    lastRefreshEnabledDestinations =
                            currentlyEnabledDestinations;

                    /*
                     * Delay refresh tabs until after current UI execution else
                     * we can get concurrent mods exceptions from validation.
                     */
                    Display.getDefault().asyncExec(new Runnable() {

                        @Override
                        public void run() {
                            if (getInput() != null && destinations != null
                                    && !destinations.isDisposed()) {
                                refreshTabs();
                            }
                        }
                    });
                }
            }
        }
    }

    private void addProjectDestinationsToProcess(EObject input) {
        Command command = null;
        Process process = null;
        ProcessInterface processInterface = null;
        EList<ExtendedAttribute> extendedAttributes = null;

        CompoundCommand cCmd = new CompoundCommand();
        IProject project = getProject();

        if (input instanceof Process) {
            process = (Process) input;
            extendedAttributes = process.getExtendedAttributes();
        } else if (input instanceof ProcessInterface) {
            processInterface = (ProcessInterface) input;
            extendedAttributes = processInterface.getExtendedAttributes();
        }
        // before adding check if no destinations from project
        // have been added yet to the new process / process
        // interface
        if (null != extendedAttributes && extendedAttributes.size() == 0) {
            ProjectConfig config =
                    XpdResourcesPlugin.getDefault().getProjectConfig(project);
            if (config != null) {
                if (null != config.getProjectDetails()) {
                    Destinations globalDestinations =
                            config.getProjectDetails().getGlobalDestinations();
                    if (null != globalDestinations) {
                        EList<Destination> destinations =
                                globalDestinations.getDestination();
                        if (destinations.size() > 0) {
                            for (Destination destination : destinations) {
                                String type = destination.getType();

                                // if (null != extendedAttributes
                                // && extendedAttributes.size() == 0) {
                                if (input instanceof Process) {
                                    command =
                                            DestinationUtil
                                                    .getSetDestinationEnabledCommand(getEditingDomain(),
                                                            (Process) input,
                                                            type,
                                                            true);
                                    cCmd.append(command);
                                } else if (input instanceof ProcessInterface) {
                                    command =
                                            DestinationUtil
                                                    .getSetDestinationEnabledCommand(getEditingDomain(),
                                                            (ProcessInterface) input,
                                                            type,
                                                            true);
                                    cCmd.append(command);
                                }
                            }
                        } else {

                            String destinationFromPreferences =
                                    UserInfoUtil.getProjectPreferences(project)
                                            .getDestination();
                            if (null != destinationFromPreferences
                                    && destinationFromPreferences.length() > 0) {
                                if (input instanceof Process) {
                                    command =
                                            DestinationUtil
                                                    .getSetDestinationEnabledCommand(getEditingDomain(),
                                                            (Process) input,
                                                            destinationFromPreferences,
                                                            true);
                                    cCmd.append(command);
                                } else if (input instanceof ProcessInterface) {
                                    command =
                                            DestinationUtil
                                                    .getSetDestinationEnabledCommand(getEditingDomain(),
                                                            (ProcessInterface) input,
                                                            destinationFromPreferences,
                                                            true);
                                    cCmd.append(command);
                                }
                            }
                        }
                    }
                }
            }
        }
        if (!cCmd.isEmpty() && cCmd.canExecute()) {
            Xpdl2ModelUtil.executeCommandOutsideTransaction(getEditingDomain(),
                    cCmd);
        }
    }

    @Override
    public boolean select(Object toTest) {

        /*
         * Sid ACE-445 - SCE is only for ACE so no need to show the destinations
         * selection.
         * 
         * NOTE: not removing for now in case we need to re-introduce this into
         * ACE for some other reason. So just disabling it using the filter.
         */
        if (true) {
            return false;
        }

        // Do not show this section in the RCP application
        if (XpdResourcesPlugin.isRCP()) {
            return false;
        }

        boolean result = super.select(toTest);

        boolean isProcessInterface = false;

        EObject eo = null;
        if (toTest instanceof EObject) {
            eo = (EObject) toTest;
        } else if (toTest instanceof IAdaptable) {
            eo = ((IAdaptable) toTest).getAdapter(EObject.class);
        }
        if (eo != null && eClass != null) {
            if (XpdExtensionPackage.eINSTANCE.getProcessInterface()
                    .isSuperTypeOf(eo.eClass())) {
                isProcessInterface = true;
            }
        }

        return result || isProcessInterface;
    }

    /**
     * Returns help context for sections to show individual help details
     * 
     * @return
     */
    public String getHelpContextID() {
        return "com.tibco.xpd.process.analyst.doc.CreateProc3"; //$NON-NLS-1$
    }

    // MR 39946 - begin
    @Override
    public void setInput(Collection<?> items) {
        super.setInput(items);

        if (ContainerType.WIZARD.equals(getSectionContainerType())) {
            Object eObject = getInput();
            if (eObject instanceof EObject) {
                /*
                 * * Sid XPD-4165. Should not automatically add project
                 * destinations into new processes in Studio for Analysts
                 * because
                 * 
                 * (a) destinations are not exposed to user in Studio for
                 * Analysts and
                 * 
                 * (b) running a simulation in studio for analysts forces the
                 * simulation destination on in the project and thus if we
                 * inherited project destinations then all future packages
                 * created would stat to have simulation on automatically and we
                 * want the user to have to explicitly enable simual;tion for
                 * each process.
                 */
                if (!XpdResourcesPlugin.isRCP()) {
                    addProjectDestinationsToProcess((EObject) eObject);
                }
            }
        } else {
            /*
             * Sid XPD-2516 Reset the last destinations set whenever the
             * setInput is called for a different input so that on first refresh
             * we will store that currently enabled list and refresh tabs when
             * that list changes.
             * 
             * If the input hasn't changed then don't reset it (brecaus refresh
             * tab works by calling setInput()!
             */
            if (getInput() != lastEnabledDestinationsStoredForInput) {
                lastRefreshEnabledDestinations = null;
            }
        }

    }
    // MR 39946 - end
}
