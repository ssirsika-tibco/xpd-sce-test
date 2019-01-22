/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.ui.properties;

import java.util.Iterator;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.simulation.SimulationConstants;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Simulation reporting preference section.
 */
public class SimulationReportingPreferenceSection extends
        AbstractFilteredTransactionalSection {

    /** Currency unit combo. */
    private CCombo currencyUnit;

    /** Time unit combo. */
    private CCombo timeUnit;

    /** Currency label list. */
    private String[] currencyArrLabel = new String[] {
            PropertiesMessage.getString("GBP"), //$NON-NLS-1$
            PropertiesMessage.getString("USD"), //$NON-NLS-1$
            PropertiesMessage.getString("EURO") }; //$NON-NLS-1$

    /** Currency ID list. */
    private String[] currencyArr = new String[] { "GBP", "USD", "EURO" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

    /** Time unit label list. */
    private String[] timeArrLabel = new String[] {
            PropertiesMessage.getString("Second"), //$NON-NLS-1$
            PropertiesMessage.getString("Minute"), //$NON-NLS-1$
            PropertiesMessage.getString("Hour"), //$NON-NLS-1$
            PropertiesMessage.getString("Day"), //$NON-NLS-1$
            PropertiesMessage.getString("Month"), //$NON-NLS-1$
            PropertiesMessage.getString("Year") }; //$NON-NLS-1$

    /** Time unit ID list. */
    private String[] timeArr = new String[] {
            "Second", "Minute", "Hour", "Day", "Month", "Year" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$

    /**
     * Constructor.
     */
    public SimulationReportingPreferenceSection() {
        super(Xpdl2Package.eINSTANCE.getPackage());
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
    public Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = toolkit.createComposite(parent);
        root.setLayout(new GridLayout(2, false));

        toolkit.createLabel(root, PropertiesMessage.getString("currencyUnit")); //$NON-NLS-1$
        currencyUnit = toolkit.createCCombo(root, SWT.FLAT);
        currencyUnit.setItems(currencyArrLabel);
        currencyUnit.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        currencyUnit.setEditable(false);
        currencyUnit.setEnabled(false);

        toolkit.createLabel(root, PropertiesMessage.getString("timeUnit")); //$NON-NLS-1$
        timeUnit = toolkit.createCCombo(root, SWT.FLAT);
        timeUnit.setItems(timeArrLabel);
        timeUnit.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        timeUnit.setEditable(false);
        timeUnit.setEnabled(false);
        return root;

    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     */
    public void doRefresh() {
        Package xpdlPackage = (Package) getInput();
        if (xpdlPackage != null) {
            EList extendedAttributes = xpdlPackage.getExtendedAttributes();
            for (Iterator iter = extendedAttributes.iterator(); iter.hasNext();) {
                ExtendedAttribute element = (ExtendedAttribute) iter.next();
                if (SimulationConstants.REPORTING_TIME_UNIT.equals(element
                        .getName())) {
                    String value = element.getValue();
                    String defaultTimeUnit = PropertiesMessage
                            .getString(SimulationConstants.REPORTING_CURRENCY_UNIT_VALUE);
                    if (value != null) {
                        int indexToSelect = getIndexToSelect(currencyArr, value);
                        if (indexToSelect != -1) {
                            currencyUnit.setText(PropertiesMessage
                                    .getString(value));
                        } else {
                            currencyUnit.setText(defaultTimeUnit);
                        }
                    } else {
                        currencyUnit.setText(defaultTimeUnit);
                    }

                } else if (SimulationConstants.REPORTING_CURRENCY_UNIT
                        .equals(element.getName())) {
                    String value = element.getValue();
                    String defaultTimeUnit = PropertiesMessage
                            .getString(SimulationConstants.REPORTING_TIME_UNIT_VALUE);
                    if (value != null) {
                        int indexToSelect = getIndexToSelect(timeArr, value);
                        if (indexToSelect != -1) {
                            timeUnit
                                    .setText(PropertiesMessage.getString(value));
                        } else {
                            timeUnit.setText(defaultTimeUnit);
                        }
                    } else {
                        timeUnit.setText(defaultTimeUnit);
                    }
                }
            }

        }
    }

    /**
     * @param stringArr
     *            The string array.
     * @param value
     *            The value to check for.
     * @return The index of the value in the array or -1.
     */
    private int getIndexToSelect(String[] stringArr, String value) {
        int toReturn = -1;
        for (int index = 0; index < stringArr.length; index++) {
            String element = stringArr[index];
            if (element.equals(value)) {
                toReturn = index;
                break;
            }
        }
        return toReturn;

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
     * @param object
     *            The input object
     * @return true if the section should show.
     * @see com.tibco.xpd.ui.properties.AbstractEObjectSection#select(java.lang.Object)
     */
    public boolean select(Object object) {
        EObject eo = getBaseSelectObject(object);
        if (eo instanceof Package) {
            Package pack = (Package) eo;
            EList workflowList = pack.getProcesses();
            for (Iterator iter = workflowList.iterator(); iter.hasNext();) {
                Process process = (Process) iter.next();
                if (DestinationUtil.isValidationDestinationEnabled(process,
                        SimulationConstants.SIMULATION_DEST_ENV_1_2_ID)) {
                    return true;
                }
            }
        }
        return false;
    }

}
