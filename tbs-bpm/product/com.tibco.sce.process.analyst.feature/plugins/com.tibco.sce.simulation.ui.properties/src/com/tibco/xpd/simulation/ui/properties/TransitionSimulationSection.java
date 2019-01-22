/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.ui.properties;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
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

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.simulation.EnumerationValueType;
import com.tibco.xpd.simulation.ParameterDistribution;
import com.tibco.xpd.simulation.SimulationConstants;
import com.tibco.xpd.simulation.SimulationPackage;
import com.tibco.xpd.simulation.StructuredConditionType;
import com.tibco.xpd.simulation.TransitionSimulationDataType;
import com.tibco.xpd.simulation.WorkflowProcessSimulationDataType;
import com.tibco.xpd.simulation.common.util.SimulationXpdlUtils;
import com.tibco.xpd.simulation.provider.SimulationItemProviderAdapterFactory;
import com.tibco.xpd.simulation.ui.action.AddSimulationParameterDataAction;
import com.tibco.xpd.simulation.ui.action.RemoveSimulationParameterDataAction;
import com.tibco.xpd.simulation.ui.picker.SimulationParameterValuesPicker;
import com.tibco.xpd.simulation.ui.provider.SelectedParameterFilter;
import com.tibco.xpd.simulation.ui.provider.SimulationParametersDataCellModifier;
import com.tibco.xpd.simulation.ui.provider.SimulationParametersDataContentProvider;
import com.tibco.xpd.simulation.ui.provider.SimulationParametersDataLabelProvider;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Condition;
import com.tibco.xpd.xpdl2.ConditionType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Property section for displaying simulation transition conditions.
 * 
 * @author nwilson
 */
public class TransitionSimulationSection extends
        AbstractFilteredTransactionalSection {

    /**
     * Selection listener for browsing.
     */
    private final class BrowseSelectionListener extends SelectionAdapter {
        /**
         * @param e
         *            The selection event.
         * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
         */
        public void widgetSelected(SelectionEvent e) {
            Object input = getInput();
            if (input instanceof Transition) {
                SimulationParameterValuesPicker dialog = new SimulationParameterValuesPicker(
                        getPart().getSite().getShell());
                Transition transition = (Transition) input;
                TransitionSimulationDataType simData = SimulationXpdlUtils
                        .getTransitionSimulationData(transition);
                if (simData != null) {
                    StructuredConditionType condition = simData
                            .getStructuredCondition();
                    if (condition != null) {
                        String parameterId = condition.getParameterId();
                        String currentParameterValue = condition
                                .getParameterValue();
                        Process process = transition.getProcess();
                        WorkflowProcessSimulationDataType wpsd = SimulationXpdlUtils
                                .getWorkflowProcessSimulationData(process);
                        if (wpsd != null) {
                            // find parameter to set as an input
                            for (Iterator iter = wpsd
                                    .getParameterDistribution().iterator(); iter
                                    .hasNext();) {
                                ParameterDistribution pd = (ParameterDistribution) iter
                                        .next();
                                if (pd.getParameterId().equals(parameterId)) {
                                    dialog.setInput(pd);

                                    // set initial selection
                                    for (Iterator iterator = pd
                                            .getEnumerationValue().iterator(); iterator
                                            .hasNext();) {
                                        EnumerationValueType enumValue = (EnumerationValueType) iterator
                                                .next();
                                        if (enumValue.getValue().equals(
                                                currentParameterValue)) {
                                            dialog
                                                    .setInitialSelection(enumValue);
                                            break;
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
                if (dialog.open() == Window.OK) {
                    Object result = dialog.getFirstResult();
                    if (result instanceof EnumerationValueType) {
                        parameterValue.setText(((EnumerationValueType) result)
                                .getValue());
                    }
                }
            }
        }
    }

    /** Horizontal component spacing. */
    private static final int SECTION_HORIZONTAL_SPACING = 8;

    /** Section column count. */
    private static final int SECTION_COLUMNS = 5;

    /** Parameter tree minimum width/height. */
    private static final int PARAMETERS_MINIMUM_SIZE = 20;

    /** Parameter tree vertical span. */
    private static final int PARAMETERS_VERTICAL_SPAN = 4;

    /** The button width. */
    private static final int BUTTON_WIDTH = 80;

    /** Width of weighting table column. */
    private static final int WEIGHTING_COLUMN_WIDTH = 80;

    /** Width of percentage table column. */
    private static final int PERCENTAGE_COLUMN_WIDTH = 80;

    /** Width of transitions table column. */
    private static final int TRANSITIONS_COLUMN_WIDTH = 100;

    /** Width of flow table column. */
    private static final int FLOW_COLUMN_WIDTH = 200;

    /** The parameter. */
    private Text parameter;

    /** The operand combo. */
    private CCombo operandCombo;

    /** The parameter value. */
    private Text parameterValue;

    /** The tree viewer. */
    private TreeViewer treeViewer;

    /** The add button. */
    private ViewerActionButton addBtn;

    /** The remove button. */
    private ViewerActionButton removeBtn;

    /** Parameter filter. */
    private SelectedParameterFilter selectedParameterFilter;

    /**
     * Constructor.
     */
    public TransitionSimulationSection() {
        super(Xpdl2Package.eINSTANCE.getTransition());
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
        GridLayout gridLayout = new GridLayout(SECTION_COLUMNS, false);
        gridLayout.horizontalSpacing = SECTION_HORIZONTAL_SPACING;
        root.setLayout(gridLayout);

        toolkit.createLabel(root, PropertiesMessage
                .getString("TransitionSimulationSection.Follow"), //$NON-NLS-1$
                SWT.NONE);

        parameter = toolkit.createText(root, ""); //$NON-NLS-1$
        parameter.setEditable(false);
        parameter.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        parameter.setData(XpdFormToolkit.FEATURE_DATA,
                SimulationPackage.eINSTANCE
                        .getStructuredConditionType_ParameterId());

        operandCombo = toolkit.createCCombo(root, SWT.READ_ONLY | SWT.FLAT);
        String[] operandNamesArr = new String[] { ">", ">=", "=", "<=", "<" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
        operandCombo.setItems(operandNamesArr);
        operandCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        operandCombo.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                onOperandSelected(operandCombo.getText());
            }
        });
        operandCombo.setData(XpdFormToolkit.FEATURE_DATA,
                SimulationPackage.eINSTANCE
                        .getStructuredConditionType_Operator());
        parameterValue = toolkit.createText(root, ""); //$NON-NLS-1$
        parameterValue.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        parameterValue.setData(XpdFormToolkit.FEATURE_DATA,
                SimulationPackage.eINSTANCE
                        .getStructuredConditionType_ParameterValue());
        manageControl(parameterValue);
        parameterValue.setEditable(false);
        // setContentAssistSupport();

        List<AdapterFactory> factories = new ArrayList<AdapterFactory>();
        factories.add(new ResourceItemProviderAdapterFactory());
        factories.add(new SimulationItemProviderAdapterFactory());
        factories.add(new ReflectiveItemProviderAdapterFactory());

        Button valueBrowse = toolkit.createButton(root, "...", SWT.NONE); //$NON-NLS-1$
        valueBrowse.addSelectionListener(new BrowseSelectionListener());

        // Parameters Table
        Composite parametersComposite = toolkit.createComposite(root);
        GridData parametersCompositeGridData = new GridData(GridData.FILL_BOTH);
        parametersCompositeGridData.horizontalSpan = SECTION_COLUMNS;
        parametersComposite.setLayoutData(parametersCompositeGridData);
        GridLayout parametersCompositeLayout = new GridLayout(2, false);
        parametersComposite.setLayout(parametersCompositeLayout);

        Tree parameters = toolkit.createTree(parametersComposite,
                SWT.FULL_SELECTION | SWT.MULTI);
        GridData paramterGridData = new GridData(GridData.FILL_BOTH);

        paramterGridData.verticalSpan = PARAMETERS_VERTICAL_SPAN;
        paramterGridData.minimumHeight = PARAMETERS_MINIMUM_SIZE;
        paramterGridData.minimumWidth = PARAMETERS_MINIMUM_SIZE;

        parameters.setLayoutData(paramterGridData);
        parameters.setHeaderVisible(true);
        TreeColumn description = new TreeColumn(parameters, SWT.LEFT);
        description.setText(PropertiesMessage.getString("FLOW")); //$NON-NLS-1$
        description.setWidth(FLOW_COLUMN_WIDTH);
        TreeColumn version = new TreeColumn(parameters, SWT.LEFT);
        version.setText(PropertiesMessage.getString("WEIGHTING")); //$NON-NLS-1$
        version.setWidth(WEIGHTING_COLUMN_WIDTH);
        TreeColumn percentage = new TreeColumn(parameters, SWT.LEFT);
        percentage.setText(PropertiesMessage.getString("PERCENTAGE")); //$NON-NLS-1$
        percentage.setWidth(PERCENTAGE_COLUMN_WIDTH);
        TreeColumn transitions = new TreeColumn(parameters, SWT.LEFT);
        transitions.setText(PropertiesMessage.getString("TRANSITIONS")); //$NON-NLS-1$
        transitions.setWidth(TRANSITIONS_COLUMN_WIDTH);

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
        CellEditor[] cellEditors = new CellEditor[] { flowEditor, weightEditor,
                null, null };
        treeViewer.setCellEditors(cellEditors);
        selectedParameterFilter = new SelectedParameterFilter();
        treeViewer.addFilter(selectedParameterFilter);

        // Viewer buttons
        IAction addDataAction = new AddSimulationParameterDataAction(treeViewer);
        addDataAction.setImageDescriptor(Xpdl2ResourcesPlugin
                .getImageDescriptor(Xpdl2ResourcesConsts.IMG_ADD));
        IAction removeDataAction = new RemoveSimulationParameterDataAction(
                treeViewer);
        removeDataAction.setImageDescriptor(Xpdl2ResourcesPlugin
                .getImageDescriptor(Xpdl2ResourcesConsts.IMG_REMOVE));
        addBtn = new ViewerActionButton(parametersComposite, toolkit,
                addDataAction);
        addBtn.getButton().setLayoutData(
                new GridData(BUTTON_WIDTH, SWT.DEFAULT));
        addBtn.setEnabled(false);
        removeBtn = new ViewerActionButton(parametersComposite, toolkit,
                removeDataAction);
        removeBtn.getButton().setLayoutData(
                new GridData(BUTTON_WIDTH, SWT.DEFAULT));
        removeBtn.setEnabled(false);

        return root;
    }

    /**
     * @param obj
     *            The object to get the command for.
     * @return The command.
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     */
    public Command doGetCommand(Object obj) {
        Transition transition = (Transition) getInput();
        TransitionSimulationDataType transitionSimulationData = SimulationXpdlUtils
                .getTransitionSimulationData(transition);
        if (transitionSimulationData == null) {
            return null;
        }
        Command cmd = null;
        EditingDomain ed = getEditingDomain(transition);
        if (obj == parameterValue) {
            EStructuralFeature feature = (EStructuralFeature) parameterValue
                    .getData(XpdFormToolkit.FEATURE_DATA);
            String newValue = parameterValue.getText();
            EObject owner = transitionSimulationData.getStructuredCondition();
            cmd = SetCommand.create(ed, owner, feature, newValue);
        }
        return cmd;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     */
    protected void doRefresh() {
        Object input = getInput();
        if (input != null && input instanceof Transition) {
            Transition transition = (Transition) input;
            TransitionSimulationDataType simData = SimulationXpdlUtils
                    .getTransitionSimulationData(transition);
            Object[] expanded = treeViewer.getExpandedElements();
            treeViewer.setInput(transition.getProcess());
            treeViewer.setExpandedElements(expanded);
            if (simData != null) {
                StructuredConditionType condition = simData
                        .getStructuredCondition();
                if (condition != null) {
                    String parameterId = condition.getParameterId();
                    String currentParameterValue = condition
                            .getParameterValue();
                    String currentOperator = condition.getOperator();
                    if (currentParameterValue == null) {
                        currentParameterValue = ""; //$NON-NLS-1$
                    }
                    // updateContentAssist(parameterId);
                    parameter.setText(parameterId);
                    operandCombo.setText(currentOperator);
                    updateText(parameterValue,currentParameterValue);
                    selectedParameterFilter.setSelectedParameterId(parameterId);
                } else {
                    selectedParameterFilter.setSelectedParameterId(null);
                }
            } else {
                selectedParameterFilter.setSelectedParameterId(null);
            }
            treeViewer.refresh();
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
        if (eo instanceof Transition) {
            Transition transition = (Transition) eo;
            if (transition != null) {
                Process process = transition.getProcess();
                if (!DestinationUtil.isValidationDestinationEnabled(process,
                        SimulationConstants.SIMULATION_DEST_ENV_1_2_ID)) {
                    return false;
                }
                Condition condition = transition.getCondition();
                if (condition != null
                        && condition.getType() == ConditionType.CONDITION_LITERAL) {
                    TransitionSimulationDataType simData = SimulationXpdlUtils
                            .getTransitionSimulationData(transition);
                    if (simData != null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param operation
     *            The operand selected.
     */
    private void onOperandSelected(String operation) {
        Transition transition = (Transition) getInput();
        EditingDomain ed = getEditingDomain(transition);
        TransitionSimulationDataType transitionSimulationData = SimulationXpdlUtils
                .getTransitionSimulationData(transition);
        StructuredConditionType structuredCondition = transitionSimulationData
                .getStructuredCondition();
        Command command = SetCommand.create(ed, structuredCondition,
                operandCombo.getData(XpdFormToolkit.FEATURE_DATA), operation);
        ed.getCommandStack().execute(command);

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
