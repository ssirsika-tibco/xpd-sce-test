/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.ui.properties;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.simulation.EnumerationValueType;
import com.tibco.xpd.simulation.ParameterDistribution;
import com.tibco.xpd.simulation.SimulationConstants;
import com.tibco.xpd.simulation.WorkflowProcessSimulationDataType;
import com.tibco.xpd.simulation.common.util.SimulationXpdlUtils;
import com.tibco.xpd.simulation.ui.action.AddSimulationParameterDataAction;
import com.tibco.xpd.simulation.ui.action.RemoveSimulationParameterDataAction;
import com.tibco.xpd.simulation.ui.provider.SimulationParametersDataCellModifier;
import com.tibco.xpd.simulation.ui.provider.SimulationParametersDataContentProvider;
import com.tibco.xpd.simulation.ui.provider.SimulationParametersDataLabelProvider;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Process simulation data section.
 */
public class ProcessSimulationSection extends
        AbstractFilteredTransactionalSection {

    /** Button width. */
    private static final int BUTTON_WIDTH = 80;

    /** Width of weighting table column. */
    private static final int WEIGHTING_COLUMN_WIDTH = 80;

    /** Width of percentage table column. */
    private static final int PERCENTAGE_COLUMN_WIDTH = 80;

    /** Width of transitions table column. */
    private static final int TRANSITIONS_COLUMN_WIDTH = 100;

    /** Width of flow table column. */
    private static final int FLOW_COLUMN_WIDTH = 200;

    /** Parameters tree minimum height. */
    private static final int PARAMETERS_HEIGHT = 20;

    /** Parameters tree vertical span. */
    private static final int PARAMETERS_VERTICAL_SPAN = 4;

    /** The tree viewer. */
    private TreeViewer treeViewer;

    /** The parameters tree. */
    private Tree parameters;

    /** Remove button. */
    private ViewerActionButton removeBtn;

    /** Add button. */
    private ViewerActionButton addBtn;

    /**
     * Constructor.
     */
    public ProcessSimulationSection() {
        super(Xpdl2Package.eINSTANCE.getProcess());
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
        root.setLayout(new GridLayout(2, false));

        Label l = toolkit.createLabel(root, PropertiesMessage
                .getString("ProcessSimulationSection.Parameters")); //$NON-NLS-1$
        GridData data = new GridData(GridData.FILL_HORIZONTAL);
        data.horizontalSpan = 2;
        l.setLayoutData(data);

        parameters = toolkit.createTree(root, SWT.FULL_SELECTION | SWT.MULTI);
        GridData paramterLayout = new GridData(GridData.FILL_BOTH);
        /*
         * paramterLayout.grabExcessHorizontalSpace = true;
         * paramterLayout.grabExcessVerticalSpace = true;
         * paramterLayout.horizontalAlignment = SWT.FILL;
         * paramterLayout.verticalAlignment = SWT.FILL;
         */
        paramterLayout.verticalSpan = PARAMETERS_VERTICAL_SPAN;
        paramterLayout.minimumHeight = PARAMETERS_HEIGHT;
        paramterLayout.minimumWidth = PARAMETERS_HEIGHT;

        parameters.setLayoutData(paramterLayout);
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

        /*
         * MenuManager menuManager = new MenuManager(); menuManager .add(new
         * GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS)); Menu menu =
         * menuManager.createContextMenu(parameters); parameters.setMenu(menu);
         * getSite() .getWorkbenchWindow() .getPartService() .getActivePart()
         * .getSite() .registerContextMenu(
         * "com.tibco.xpd.simulation.ui.ProcessSimulationSection.menu",
         * menuManager, treeViewer);
         */

        /*
         * MenuManager mm = new MenuManager(); mm.add(addDataAction);
         * mm.add(removeDataAction); Menu menu =
         * mm.createContextMenu(parameters); parameters.setMenu(menu);
         */

        IAction addDataAction = new AddSimulationParameterDataAction(treeViewer);
        addDataAction.setImageDescriptor(Xpdl2ResourcesPlugin
                .getImageDescriptor(Xpdl2ResourcesConsts.IMG_ADD));
        IAction removeDataAction = new RemoveSimulationParameterDataAction(
                treeViewer);
        removeDataAction.setImageDescriptor(Xpdl2ResourcesPlugin
                .getImageDescriptor(Xpdl2ResourcesConsts.IMG_REMOVE));
        addBtn = new ViewerActionButton(root, toolkit, addDataAction);
        addBtn.getButton().setLayoutData(
                new GridData(BUTTON_WIDTH, SWT.DEFAULT));
        addBtn.setEnabled(false);
        removeBtn = new ViewerActionButton(root, toolkit, removeDataAction);
        removeBtn.getButton().setLayoutData(
                new GridData(BUTTON_WIDTH, SWT.DEFAULT));
        removeBtn.setEnabled(false);

        return root;
    }

    /**
     * @param obj
     *            The object to get the command for.
     * @return null.
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     */
    public Command doGetCommand(Object obj) {
        return null;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     */
    protected void doRefresh() {
        Object input = getInput();
        if (input == null) {
            return;
        }
        Object[] expanded = treeViewer.getExpandedElements();
        treeViewer.setInput(input);
        treeViewer.setExpandedElements(expanded);
    }

    /**
     * @param object
     *            The input object.
     * @return true if the section should show.
     * @see com.tibco.xpd.ui.properties.AbstractEObjectSection#select(java.lang.Object)
     */
    public boolean select(Object object) {
        EObject eo = getBaseSelectObject(object);
        if (eo instanceof Process) {
            Process proc = (Process) eo;
            if (proc != null) {
                if (DestinationUtil.isValidationDestinationEnabled(proc,
                        SimulationConstants.SIMULATION_DEST_ENV_1_2_ID)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @return An array of objects to monitor for change.
     * @see com.tibco.xpd.ui.properties.AbstractEObjectSection#
     *      getNotifierObjects()
     */
    public EObject[] getNotifierObjects() {
        Object input = getInput();
        ArrayList<EObject> list = new ArrayList<EObject>();
        if (input != null && input instanceof Process) {
            Process process = (Process) input;
            list.add(process);
            WorkflowProcessSimulationDataType simulationData = SimulationXpdlUtils
                    .getWorkflowProcessSimulationData(process);
            list.add(simulationData);
            if (simulationData != null) {
                EList parameterList = simulationData.getParameterDistribution();
                for (Iterator i = parameterList.iterator(); i.hasNext();) {
                    Object object = i.next();
                    if (object instanceof ParameterDistribution) {
                        ParameterDistribution parameter = (ParameterDistribution) object;
                        list.add(parameter);
                        EList enumerationList = parameter.getEnumerationValue();
                        for (Iterator j = enumerationList.iterator(); j
                                .hasNext();) {
                            object = j.next();
                            if (object instanceof EnumerationValueType) {
                                EnumerationValueType enumeration = (EnumerationValueType) object;
                                list.add(enumeration);
                            }
                        }
                    }
                }
            }
        }
        EObject[] eObjects = new EObject[list.size()];
        list.toArray(eObjects);
        return eObjects;
    }

}
