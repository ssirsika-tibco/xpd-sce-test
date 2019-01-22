/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.simulation.ui.properties;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.DataFilterPicker;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.DataFilterPicker.DataPickerType;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.simulation.EnumerationValueType;
import com.tibco.xpd.simulation.ParameterDistribution;
import com.tibco.xpd.simulation.SimulationConstants;
import com.tibco.xpd.simulation.SimulationPackage;
import com.tibco.xpd.simulation.SplitParameterType;
import com.tibco.xpd.simulation.SplitSimulationDataType;
import com.tibco.xpd.simulation.WorkflowProcessSimulationDataType;
import com.tibco.xpd.simulation.common.util.SimulationCommandUtils;
import com.tibco.xpd.simulation.common.util.SimulationXpdlUtils;
import com.tibco.xpd.simulation.ui.action.AddSimulationParameterDataAction;
import com.tibco.xpd.simulation.ui.action.RemoveSimulationParameterDataAction;
import com.tibco.xpd.simulation.ui.provider.SelectedParameterFilter;
import com.tibco.xpd.simulation.ui.provider.SimulationParametersDataCellModifier;
import com.tibco.xpd.simulation.ui.provider.SimulationParametersDataContentProvider;
import com.tibco.xpd.simulation.ui.provider.SimulationParametersDataLabelProvider;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.JoinSplitType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Route;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;
import com.tibco.xpd.xpdl2.util.XpdlSearchUtil;

/**
 * Gateway simulation data section.
 */
public class GatewaySimulationSection extends
        AbstractFilteredTransactionalSection {

    /** Button width. */
    private static final int BUTTON_WIDTH = 80;

    /** Width of small columns. */
    private static final int SMALL_COLUMN_WIDTH = 80;

    /** Width of large columns. */
    private static final int LARGE_COLUMN_WIDTH = 200;

    /** Parameters table height. */
    private static final int PARAMETERS_HEIGHT = 20;

    /** Parameters table vertical span. */
    private static final int PARAMETERS_VERTICAL_SPAN = 4;

    /** Root control grid columns. */
    private static final int GRID_COLUMNS = 3;

    /** Parameter name. */
    private Text parameterName;

    /** Parameter based check box. */
    private Button parameterBasedCheckBox;

    /** Tree viewer. */
    private TreeViewer treeViewer;

    /** Add button. */
    private ViewerActionButton addBtn;

    /** Remove button. */
    private ViewerActionButton removeBtn;

    /** Parameter filter. */
    private SelectedParameterFilter selectedParameterFilter;

    /**
     * Constructor.
     */
    public GatewaySimulationSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
    }

    /**
     * @param parent
     *            The parent composite.
     * @param toolkit
     *            The UI toolkit.
     * @return The root section control.
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doCreateControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     */
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = toolkit.createComposite(parent);
        GridLayout gridLayout = new GridLayout(GRID_COLUMNS, false);
        root.setLayout(gridLayout);
        toolkit.createLabel(root, PropertiesMessage
                .getString("GatewaySimulationSection.Dependant")); //$NON-NLS-1$
        parameterBasedCheckBox = toolkit.createButton(root, "", SWT.CHECK); //$NON-NLS-1$
        parameterBasedCheckBox.setData(XpdFormToolkit.FEATURE_DATA,
                SimulationPackage.eINSTANCE
                        .getSplitSimulationDataType_ParameterDeterminedSplit());
        GridData layout = new GridData(GridData.FILL_HORIZONTAL);
        layout.horizontalSpan = 2;
        parameterBasedCheckBox.setLayoutData(layout);
        parameterBasedCheckBox.setEnabled(false);
        parameterBasedCheckBox.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                checkBoxSelected(e);
            }
        });
        toolkit.createLabel(root, PropertiesMessage
                .getString("GatewaySimulationSection.Parameter")); //$NON-NLS-1$
        parameterName = toolkit.createText(root, ""); //$NON-NLS-1$
        parameterName
                .setData(XpdFormToolkit.FEATURE_DATA,
                        SimulationPackage.eINSTANCE
                                .getSplitParameterType_ParameterId());
        parameterName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        parameterName.setData(FormToolkit.KEY_DRAW_BORDER,
                FormToolkit.TEXT_BORDER);
        manageControl(parameterName);
        parameterName.setEditable(false);
        Button parameterBrowse = toolkit.createButton(root, "...", SWT.NONE); //$NON-NLS-1$
        parameterBrowse.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                DataFilterPicker dialog =
                        new DataFilterPicker(getPart().getSite().getShell(),
                                DataPickerType.FORMALPARAMETER, false);
                Object input = getInput();
                if (input instanceof Activity) {
                    /*
                     * Activity activity = (Activity) input; Process process =
                     * activity.getProcess(); dialog.setInput(process);
                     * FormalParameter value = XpdlSearchUtil
                     * .findFormalParameterByName(process, parameterName
                     * .getText()); if (value != null) {
                     * dialog.setInitialSelection(value); }
                     */
                    dialog.setScope((Activity) input);
                    if (dialog.open() == Window.OK) {
                        Object result = dialog.getFirstResult();
                        if (result instanceof FormalParameter) {
                            FormalParameter selectedFp =
                                    (FormalParameter) result;
                            EditingDomain ed = getEditingDomain(selectedFp);
                            Command cmd = null;
                            EStructuralFeature feature =
                                    (EStructuralFeature) parameterName
                                            .getData(XpdFormToolkit.FEATURE_DATA);
                            String newValue = selectedFp.getName();
                            SplitSimulationDataType splitData =
                                    SimulationXpdlUtils
                                            .getSplitSimulationData((Activity) input);
                            EObject owner = splitData.getSplitParameter();
                            cmd =
                                    SetCommand.create(ed,
                                            owner,
                                            feature,
                                            newValue);
                            cmd =
                                    SimulationCommandUtils
                                            .assignSplitParameterInTransitionEA(cmd,
                                                    ed,
                                                    owner,
                                                    feature,
                                                    selectedFp.getName());
                            ed.getCommandStack().execute(cmd);
                        }

                    }
                }
            }
        });

        // Parameters Table
        Composite parametersComposite = toolkit.createComposite(root);
        GridData parametersCompositeGridData = new GridData(GridData.FILL_BOTH);
        parametersCompositeGridData.horizontalSpan = GRID_COLUMNS;
        parametersComposite.setLayoutData(parametersCompositeGridData);
        GridLayout parametersCompositeLayout = new GridLayout(2, false);
        parametersComposite.setLayout(parametersCompositeLayout);

        Tree parameters =
                toolkit.createTree(parametersComposite, SWT.FULL_SELECTION
                        | SWT.MULTI);
        GridData paramterGridData = new GridData(GridData.FILL_BOTH);

        paramterGridData.verticalSpan = PARAMETERS_VERTICAL_SPAN;
        paramterGridData.minimumHeight = PARAMETERS_HEIGHT;
        paramterGridData.minimumWidth = PARAMETERS_HEIGHT;

        parameters.setLayoutData(paramterGridData);
        parameters.setHeaderVisible(true);
        TreeColumn description = new TreeColumn(parameters, SWT.LEFT);
        description.setText(PropertiesMessage.getString("FLOW")); //$NON-NLS-1$
        description.setWidth(LARGE_COLUMN_WIDTH);
        TreeColumn version = new TreeColumn(parameters, SWT.LEFT);
        version.setText(PropertiesMessage.getString("WEIGHTING")); //$NON-NLS-1$
        version.setWidth(SMALL_COLUMN_WIDTH);
        TreeColumn percentage = new TreeColumn(parameters, SWT.LEFT);
        percentage.setText(PropertiesMessage.getString("PERCENTAGE")); //$NON-NLS-1$
        percentage.setWidth(SMALL_COLUMN_WIDTH);
        TreeColumn transitions = new TreeColumn(parameters, SWT.LEFT);
        transitions.setText(PropertiesMessage.getString("TRANSITIONS")); //$NON-NLS-1$
        transitions.setWidth(LARGE_COLUMN_WIDTH);

        treeViewer = new TreeViewer(parameters);
        treeViewer
                .setContentProvider(new SimulationParametersDataContentProvider());
        treeViewer
                .setLabelProvider(new SimulationParametersDataLabelProvider());
        treeViewer.setColumnProperties(new String[] {
                PropertiesMessage.getString("FLOW"), //$NON-NLS-1$
                PropertiesMessage.getString("WEIGHTING"), //$NON-NLS-1$
                PropertiesMessage.getString("PERCENTAGE"), //$NON-NLS-1$
                PropertiesMessage.getString("TRANSITIONS") }); //$NON-NLS-1$
        treeViewer.setCellModifier(new SimulationParametersDataCellModifier(
                this));
        TextCellEditor weightEditor = new TextCellEditor(parameters);
        TextCellEditor flowEditor = new TextCellEditor(parameters);
        CellEditor[] cellEditors =
                new CellEditor[] { flowEditor, weightEditor, null, null };
        treeViewer.setCellEditors(cellEditors);
        selectedParameterFilter = new SelectedParameterFilter();
        treeViewer.addFilter(selectedParameterFilter);

        // Viewer buttons
        IAction addDataAction =
                new AddSimulationParameterDataAction(treeViewer);
        addDataAction.setImageDescriptor(Xpdl2ResourcesPlugin
                .getImageDescriptor(Xpdl2ResourcesConsts.IMG_ADD));
        IAction removeDataAction =
                new RemoveSimulationParameterDataAction(treeViewer);
        removeDataAction.setImageDescriptor(Xpdl2ResourcesPlugin
                .getImageDescriptor(Xpdl2ResourcesConsts.IMG_REMOVE));
        addBtn =
                new ViewerActionButton(parametersComposite, toolkit,
                        addDataAction);
        addBtn.getButton()
                .setLayoutData(new GridData(BUTTON_WIDTH, SWT.DEFAULT));
        addBtn.setEnabled(false);
        removeBtn =
                new ViewerActionButton(parametersComposite, toolkit,
                        removeDataAction);
        removeBtn.getButton().setLayoutData(new GridData(BUTTON_WIDTH,
                SWT.DEFAULT));
        removeBtn.setEnabled(false);

        return root;
    }

    /**
     * @param e
     *            The selection event.
     */
    private void checkBoxSelected(SelectionEvent e) {
        Object input = getInput();
        if (input != null && input instanceof Activity) {
            Activity activity = (Activity) input;
            SplitSimulationDataType simData =
                    SimulationXpdlUtils.getSplitSimulationData(activity);
            if (simData != null) {
                Command cmd = null;
                EditingDomain ed = getEditingDomain(activity);
                boolean checkBoxSelected = ((Button) e.widget).getSelection();
                EStructuralFeature feature =
                        (EStructuralFeature) e.widget
                                .getData(XpdFormToolkit.FEATURE_DATA);
                if (checkBoxSelected) {
                    cmd = SetCommand.create(ed, simData, feature, Boolean.TRUE);
                    cmd =
                            SimulationCommandUtils
                                    .updateParameterBasedSplitTransitionEA(cmd,
                                            ed,
                                            simData,
                                            feature,
                                            Boolean.TRUE);
                } else {
                    cmd =
                            SetCommand.create(ed,
                                    simData,
                                    feature,
                                    Boolean.FALSE);
                    cmd =
                            SimulationCommandUtils
                                    .updateParameterBasedSplitTransitionEA(cmd,
                                            ed,
                                            simData,
                                            feature,
                                            Boolean.FALSE);
                }
                if (cmd.canExecute()) {
                    ed.getCommandStack().execute(cmd);
                }
            }
        }
    }

    /**
     * @param obj
     *            The object to get the command for.
     * @return The command.
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     */
    public Command doGetCommand(Object obj) {
        Activity activity = (Activity) getInput();
        SplitSimulationDataType splitSimulationData =
                SimulationXpdlUtils.getSplitSimulationData(activity);
        if (splitSimulationData == null) {
            return null;
        }
        Command cmd = null;
        EditingDomain ed = getEditingDomain(activity);
        if (obj == parameterName) {
            EStructuralFeature feature =
                    (EStructuralFeature) parameterName
                            .getData(XpdFormToolkit.FEATURE_DATA);
            String newValue = parameterName.getText();
            EObject owner = splitSimulationData.getSplitParameter();
            cmd = SetCommand.create(ed, owner, feature, newValue);
            cmd =
                    SimulationCommandUtils
                            .assignSplitParameterInTransitionEA(cmd,
                                    ed,
                                    owner,
                                    feature,
                                    newValue);
        }
        return cmd;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     */
    protected void doRefresh() {
        Object input = getInput();
        if (input != null && input instanceof Activity) {
            Activity activity = (Activity) input;
            SplitSimulationDataType simData =
                    SimulationXpdlUtils.getSplitSimulationData(activity);
            if (simData != null) {
                SplitParameterType splitType = simData.getSplitParameter();
                if (splitType != null) {
                    parameterBasedCheckBox.setSelection(simData
                            .isParameterDeterminedSplit());
                    if (!simData.isParameterDeterminedSplit()) {
                        parameterName.setEnabled(false);
                    } else {
                        parameterName.setEnabled(true);
                        // updateContentAssist();
                    }
                    String parameterId = splitType.getParameterId();
                    updateText(parameterName, parameterId);
                    selectedParameterFilter.setSelectedParameterId(parameterId);
                } else {
                    selectedParameterFilter.setSelectedParameterId(null);
                }

            }
            Object[] expanded = treeViewer.getExpandedElements();
            treeViewer.setInput(activity.getProcess());
            treeViewer.setExpandedElements(expanded);
        }
    }

    /**
     * @param object
     *            The input object.
     * @return true if the section should show.
     * @see com.tibco.xpd.ui.properties.AbstractEObjectSection#select(java.lang.Object)
     */
    public boolean select(Object object) {
        EObject eo = getBaseSelectObject(object);
        if (eo instanceof Activity) {
            Activity activity = (Activity) eo;
            if (activity == null) {
                return false;
            }
            Process process = activity.getProcess();
            if (process != null) {
                if (!DestinationUtil.isValidationDestinationEnabled(process,
                        SimulationConstants.SIMULATION_DEST_ENV_1_2_ID)) {
                    return false;
                }
                Route route = activity.getRoute();
                if (route != null) {
                    if (!JoinSplitType.EXCLUSIVE_LITERAL.equals(Xpdl2ModelUtil
                            .safeGetGatewayType(activity))) {
                        return false;
                    }
                }
                SplitSimulationDataType simData =
                        SimulationXpdlUtils.getSplitSimulationData(activity);
                if (simData != null) {
                    boolean b =
                            SimulationXpdlUtils.isConditionalSplit(activity);
                    if (b) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @return An array of objects to monitor for change.
     * @see com.tibco.xpd.ui.properties.AbstractEObjectSection#getNotifierObjects()
     */
    public EObject[] getNotifierObjects() {
        Object input = getInput();
        if (input != null && input instanceof Activity) {
            ArrayList<EObject> list = new ArrayList<EObject>();

            Activity activity = (Activity) input;
            SplitSimulationDataType simData =
                    SimulationXpdlUtils.getSplitSimulationData(activity);
            if (simData != null) {
                list.add(simData.getSplitParameter());
                list.add(simData);
            }

            Process process = XpdlSearchUtil.findParentProcess(activity);
            list.add(process);
            WorkflowProcessSimulationDataType simulationData =
                    SimulationXpdlUtils
                            .getWorkflowProcessSimulationData(process);
            list.add(simulationData);
            if (simulationData != null) {
                EList parameterList = simulationData.getParameterDistribution();
                for (Iterator<?> i = parameterList.iterator(); i.hasNext();) {
                    Object object = i.next();
                    if (object instanceof ParameterDistribution) {
                        ParameterDistribution parameter =
                                (ParameterDistribution) object;
                        list.add(parameter);
                        EList enumerationList = parameter.getEnumerationValue();
                        for (Iterator<?> j = enumerationList.iterator(); j
                                .hasNext();) {
                            object = j.next();
                            if (object instanceof EnumerationValueType) {
                                EnumerationValueType enumeration =
                                        (EnumerationValueType) object;
                                list.add(enumeration);
                            }
                        }
                    }
                }
            }
            EObject[] eObjects = new EObject[list.size()];
            list.toArray(eObjects);
            return eObjects;
        }
        return new EObject[] {};
    }

    @Override
    protected boolean shouldRefresh(List<Notification> notifications) {
        for (Notification notification : notifications) {
            Object notifier = notification.getNotifier();
            if (notifier instanceof ParameterDistribution) {
                return true;
            } else if (notifier instanceof EnumerationValueType) {
                EnumerationValueType enumerationValueType =
                        (EnumerationValueType) notifier;
                if (enumerationValueType.eContainer() instanceof ParameterDistribution) {
                    return true;
                }
            }
        }
        return super.shouldRefresh(notifications);
    }

}
