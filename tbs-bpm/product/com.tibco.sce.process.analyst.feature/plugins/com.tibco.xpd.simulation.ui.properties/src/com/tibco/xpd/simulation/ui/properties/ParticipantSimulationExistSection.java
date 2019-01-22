/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.ui.properties;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.simulation.ParticipantSimulationDataType;
import com.tibco.xpd.simulation.SimulationConstants;
import com.tibco.xpd.simulation.SimulationPackage;
import com.tibco.xpd.simulation.TimeDisplayUnitType;
import com.tibco.xpd.simulation.TimeUnitCostType;
import com.tibco.xpd.simulation.common.util.DisplayTimeUnitConverter;
import com.tibco.xpd.simulation.common.util.SimulationXpdlUtils;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.TextFieldVerifier;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Participant simulation data exists section.
 */
public class ParticipantSimulationExistSection extends
        AbstractFilteredTransactionalSection implements TextFieldVerifier {

    /** Feature ID. */
    private static final String FEATURE = "FEATURE"; //$NON-NLS-1$

    /** Percentage regular expression definition. */
    private static final String PERCENTAGE =
            "^(0*100{1,1}(\\.0*)?)|(0*\\d{0,2}(\\.\\d*)?)$"; //$NON-NLS-1$

    /** Default time unit. */
    private final TimeDisplayUnitType xpdlTimeUnit =
            DisplayTimeUnitConverter.DEFAULT_TIME_UNIT;

    /** Enabled time display units. */
    private static final TimeDisplayUnitType[] ENABLED_UNITS = {
            TimeDisplayUnitType.SECOND_LITERAL,
            TimeDisplayUnitType.MINUTE_LITERAL,
            TimeDisplayUnitType.HOUR_LITERAL };

    /** The percentage regex pattern. */
    private Pattern percentage;

    /** The number of instances. */
    private Text instances;

    /** The cost. */
    private Text cost;

    /** Time unit combo. */
    private CCombo timeUnitCCombo;

    /** Minimum utilisation SLA. */
    private Text slaMinimumUtilisationText;

    /** Maximum utilisation SLA. */
    private Text slaMaximumUtilisationText;

    /**
     * Constructor.
     */
    public ParticipantSimulationExistSection() {
        super(Xpdl2Package.eINSTANCE.getParticipant());
        percentage = Pattern.compile(PERCENTAGE);
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
    @Override
    public Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {

        Composite form = toolkit.createComposite(parent);

        form.setLayout(new GridLayout(2, false));

        toolkit.createLabel(form,
                PropertiesMessage
                        .getString("ParticipantSimulationExistSection_NumberOfPeople"), SWT.NONE); //$NON-NLS-1$
        instances = toolkit.createText(form, ""); //$NON-NLS-1$
        instances.setData(FEATURE, SimulationPackage.eINSTANCE
                .getParticipantSimulationDataType_Instances());
        instances.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        manageControl(instances);

        toolkit.createLabel(form,
                PropertiesMessage
                        .getString("ParticipantSimulationExistSection_CostPerUnit"), SWT.NONE); //$NON-NLS-1$
        cost = toolkit.createText(form, ""); //$NON-NLS-1$
        cost.setData(FEATURE,
                SimulationPackage.eINSTANCE.getTimeUnitCostType_Cost());
        cost.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        manageControl(cost);

        toolkit.createLabel(form, PropertiesMessage
                .getString("ParticipantSimulationExistSection_Unit"), SWT.NONE); //$NON-NLS-1$
        timeUnitCCombo = toolkit.createCCombo(form, SWT.READ_ONLY);
        timeUnitCCombo.setData(FEATURE, SimulationPackage.eINSTANCE
                .getTimeUnitCostType_TimeDisplayUnit());
        String[] timeUnits = new String[ENABLED_UNITS.length];
        int j = 0;
        List enabledUnits = Arrays.asList(ENABLED_UNITS);
        for (Iterator iter = TimeDisplayUnitType.VALUES.iterator(); iter
                .hasNext();) {
            TimeDisplayUnitType timeDisplayUnitType =
                    (TimeDisplayUnitType) iter.next();
            if (enabledUnits.contains(timeDisplayUnitType)) {
                String name = timeDisplayUnitType.getName();
                String unitName =
                        PropertiesMessage
                                .getString("ParticipantSimulationExistSection_UnitType_" + name); //$NON-NLS-1$
                timeUnitCCombo.setData(name, unitName);
                timeUnits[j] = unitName;
                j++;
            }
        }
        timeUnitCCombo.setItems(timeUnits);
        timeUnitCCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        manageControl(timeUnitCCombo);

        toolkit.createLabel(form,
                PropertiesMessage
                        .getString("ParticipantSimulationExistSection_MinSLA"), SWT.NONE); //$NON-NLS-1$
        EAttribute minAtt =
                SimulationPackage.eINSTANCE
                        .getParticipantSimulationDataType_SlaMinimumUtilisation();
        slaMinimumUtilisationText = toolkit.createText(form, null, minAtt);
        slaMinimumUtilisationText.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));
        manageControl(slaMinimumUtilisationText);

        toolkit.createLabel(form,
                PropertiesMessage
                        .getString("ParticipantSimulationExistSection_MaxSLA"), SWT.NONE); //$NON-NLS-1$
        EAttribute maxAtt =
                SimulationPackage.eINSTANCE
                        .getParticipantSimulationDataType_SlaMaximumUtilisation();
        slaMaximumUtilisationText = toolkit.createText(form, null, maxAtt);
        slaMaximumUtilisationText.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));
        manageControl(slaMaximumUtilisationText);

        return form;
    }

    /**
     * @param src
     *            The object to get the command for.
     * @return The command.
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     */
    @Override
    public Command doGetCommand(Object src) {
        Participant participant = (Participant) getInput();
        EditingDomain ed = getEditingDomain(participant);
        ParticipantSimulationDataType participantSimData =
                getParticipantSimulationData(participant);
        if (participantSimData == null) {
            return null;
        }
        TimeUnitCostType timeUnitCostType =
                participantSimData.getTimeUnitCost();
        if (timeUnitCostType == null) {
            return null;
        }
        Command cmd;
        if (src == instances) {
            String text =
                    "".equals(instances.getText()) ? "0" : instances.getText(); //$NON-NLS-1$ //$NON-NLS-2$
            cmd =
                    SetCommand.create(ed,
                            participantSimData,
                            instances.getData(FEATURE),
                            new BigInteger(text));
        } else if (src == cost) {
            TimeDisplayUnitType currentTimeUnit =
                    timeUnitCostType.getTimeDisplayUnit();
            cmd =
                    SetCommand.create(ed, timeUnitCostType, cost
                            .getData(FEATURE), DisplayTimeUnitConverter
                            .convertToDouble(xpdlTimeUnit,
                                    currentTimeUnit,
                                    cost.getText()));
        } else if (src == timeUnitCCombo) {
            TimeUnitCostType timeUnitCost =
                    participantSimData.getTimeUnitCost();
            TimeDisplayUnitType selectedTimeUnit = null;
            List enabledUnits = Arrays.asList(ENABLED_UNITS);
            for (Iterator iter = TimeDisplayUnitType.VALUES.iterator(); iter
                    .hasNext();) {
                TimeDisplayUnitType timeDisplayUnitType =
                        (TimeDisplayUnitType) iter.next();
                if (enabledUnits.contains(timeDisplayUnitType)) {
                    String name = timeDisplayUnitType.getName();
                    String unitName =
                            PropertiesMessage
                                    .getString("ParticipantSimulationExistSection_UnitType_" + name); //$NON-NLS-1$
                    if (unitName.equals(timeUnitCCombo.getText())) {
                        selectedTimeUnit = timeDisplayUnitType;
                        break;
                    }
                }
            }
            cmd =
                    SetCommand.create(ed,
                            timeUnitCost,
                            timeUnitCCombo.getData(FEATURE),
                            selectedTimeUnit);
        } else if (src == slaMinimumUtilisationText) {
            Double minUtilisation = null;
            try {
                String text = slaMinimumUtilisationText.getText();
                if (text != null && text.length() != 0) {
                    minUtilisation = new Double(text);
                }
            } catch (NumberFormatException e) {
                // Ignore.
            }
            cmd =
                    SetCommand.create(ed,
                            participantSimData,
                            slaMinimumUtilisationText.getData(FEATURE),
                            minUtilisation);
        } else if (src == slaMaximumUtilisationText) {
            Double maxUtilisation = null;
            try {
                String text = slaMaximumUtilisationText.getText();
                if (text != null && text.length() != 0) {
                    maxUtilisation = new Double(text);
                }
            } catch (NumberFormatException e) {
                // Ignore.
            }
            cmd =
                    SetCommand.create(ed,
                            participantSimData,
                            slaMaximumUtilisationText.getData(FEATURE),
                            maxUtilisation);
        } else {
            throw new IllegalArgumentException();
        }
        return cmd;
    }

    /**
     * @see com.tibco.xpd.processwidget.properties.BaseSection#doRefresh()
     */
    @Override
    public void doRefresh() {
        Object input = getInput();
        if (input == null) {
            return;
        }
        Participant participant = (Participant) input;
        ParticipantSimulationDataType participantSimData =
                getParticipantSimulationData(participant);
        if (participantSimData == null) {
            return;
        }
        updateText(instances, participantSimData.getInstances().toString());

        TimeUnitCostType timeUnitCostType =
                participantSimData.getTimeUnitCost();
        if (timeUnitCostType != null) {
            TimeDisplayUnitType currentTimeUnit =
                    timeUnitCostType.getTimeDisplayUnit();
            String timeUnitName =
                    currentTimeUnit == null ? "" : currentTimeUnit.getName(); //$NON-NLS-1$
            timeUnitName = timeUnitName == null ? "" : timeUnitName; //$NON-NLS-1$
            String timeUnitDataValue =
                    (String) timeUnitCCombo.getData(timeUnitName);
            if (timeUnitDataValue == null) {
                timeUnitDataValue = timeUnitName;
            }
            updateCCombo(timeUnitCCombo, timeUnitDataValue);
            updateText(cost,
                    DisplayTimeUnitConverter.convertToString(currentTimeUnit,
                            xpdlTimeUnit,
                            timeUnitCostType.getCost()));
        } else {
            updateCCombo(timeUnitCCombo, ""); //$NON-NLS-1$
        }
        Double minUtilisation = participantSimData.getSlaMinimumUtilisation();
        updateText(slaMinimumUtilisationText, minUtilisation == null ? "" //$NON-NLS-1$
                : minUtilisation.toString());
        Double maxUtilisation = participantSimData.getSlaMaximumUtilisation();
        updateText(slaMaximumUtilisationText, maxUtilisation == null ? "" //$NON-NLS-1$
                : maxUtilisation.toString());
    }

    /**
     * @param event
     *            The modification event.
     */
    public void doHandleModifyEvent(ModifyEvent event) {
        Participant participant = (Participant) getInput();
        EditingDomain ed = getEditingDomain(participant);
        ParticipantSimulationDataType participantSimData =
                getParticipantSimulationData(participant);
        if (participantSimData == null) {
            return;
        }
        TimeUnitCostType timeUnitCostType =
                participantSimData.getTimeUnitCost();
        if (timeUnitCostType == null) {
            return;
        }
        Command cmd;
        if (event.widget == instances) {
            String text =
                    "".equals(instances.getText()) ? "0" : instances.getText(); //$NON-NLS-1$ //$NON-NLS-2$
            cmd =
                    SetCommand.create(ed,
                            participantSimData,
                            instances.getData(FEATURE),
                            new BigInteger(text));
        } else if (event.widget == cost) {
            TimeDisplayUnitType currentTimeUnit =
                    timeUnitCostType.getTimeDisplayUnit();
            cmd =
                    SetCommand.create(ed, timeUnitCostType, cost
                            .getData(FEATURE), DisplayTimeUnitConverter
                            .convertToDouble(xpdlTimeUnit,
                                    currentTimeUnit,
                                    cost.getText()));
        } else {
            throw new IllegalArgumentException();
        }
        ed.getCommandStack().execute(cmd);
    }

    /**
     * Gets ParticipantSimulationData for Participant if tihs data exists
     * otherwise returns null.
     * 
     * @param participant
     *            xpdl participant.
     * @return ParticipantSimulationData for Participant if tihs data exists
     *         otherwise returns null.
     */
    /* package */static ParticipantSimulationDataType getParticipantSimulationData(
            Participant participant) {
        for (Iterator iter = participant.getExtendedAttributes().iterator(); iter
                .hasNext();) {
            ExtendedAttribute ea = (ExtendedAttribute) iter.next();
            if ("ParticipantSimulationData".equals(ea.getName())) { //$NON-NLS-1$
                // found extended attribute
                EStructuralFeature feature =
                        SimulationPackage.eINSTANCE
                                .getDocumentRoot_ParticipantSimulationData();
                EList simProcessList = (EList) ea.getMixed().get(feature, true);
                // there can only be one of these
                if (simProcessList.size() == 1 && simProcessList.get(0) != null) {
                    return (ParticipantSimulationDataType) simProcessList
                            .get(0);
                }
                return null;
            }
        }
        return null;
    }

    /**
     * @param e
     *            The text modification event.
     * @see com.tibco.xpd.ui.properties.TextFieldVerifier#verifyText(org.eclipse.swt.widgets.Event)
     */
    @Override
    public void verifyText(Event e) {
        Text textControl = ((Text) e.widget);
        String t =
                textControl.getText(0, e.start - 1)
                        + e.text
                        + textControl.getText(e.end,
                                textControl.getCharCount() - 1);
        if ("".equals(t)) { //$NON-NLS-1$
            e.doit = true;
            return;
        }
        Participant participant = (Participant) getInput();
        ParticipantSimulationDataType participantSimData =
                getParticipantSimulationData(participant);
        if (participantSimData == null) {
            return;
        }
        if (textControl == slaMinimumUtilisationText
                || textControl == slaMaximumUtilisationText) {
            if (!percentage.matcher(t).matches()) {
                e.doit = false;
            }
            return;
        }
        TimeUnitCostType timeUnitCostType =
                participantSimData.getTimeUnitCost();

        if (timeUnitCostType == null) {
            e.doit = false;
            return;
        }
        if (textControl == instances) {
            e.doit =
                    canSetAttribute(participantSimData,
                            textControl.getData(FEATURE),
                            t);
        } else if (textControl == cost) {
            TimeDisplayUnitType currentTimeUnit =
                    timeUnitCostType.getTimeDisplayUnit();
            Double timeUnit =
                    DisplayTimeUnitConverter.convertToDouble(xpdlTimeUnit,
                            currentTimeUnit,
                            t);
            if (timeUnit == null) {
                e.doit = false;
                return;
            }
            t = timeUnit.toString();
            e.doit =
                    canSetAttribute(timeUnitCostType,
                            textControl.getData(FEATURE),
                            t);
        } else {
            throw new IllegalArgumentException();
        }

    }

    /**
     * @param owner
     *            The owning object.
     * @param feature
     *            The feature to check.
     * @param newValue
     *            The new value to check.
     * @return true if the feature of the object can be modified.
     */
    public boolean canSetAttribute(EObject owner, Object feature,
            String newValue) {
        if (owner != null && feature != null) {
            EAttribute ref = (EAttribute) feature;
            EDataType type = (EDataType) ref.getEType();
            if (type instanceof EDataType) {
                Object qw = null;
                try {
                    qw =
                            type.getEPackage().getEFactoryInstance()
                                    .createFromString(type, newValue);
                } catch (RuntimeException e) {
                    return false;
                }
                EditingDomain ed = getEditingDomain(owner);
                return SetCommand.create(ed, owner, feature, qw).canExecute();
            }
            return false;
        } else {
            return false;
        }
    }

    /**
     * @param object
     *            The input object.
     * @return true if the section should show.
     * @see com.tibco.xpd.ui.properties.AbstractEObjectSection#select(java.lang.Object)
     */
    @Override
    public boolean select(Object object) {
        EObject eo = getBaseSelectObject(object);
        if (eo instanceof Participant) {
            Participant participant = (Participant) eo;
            EObject pc = participant.eContainer();
            if (pc instanceof Process) {

                // on the process the section should appear when the proc has
                // simulation destination env
                if (DestinationUtil
                        .isValidationDestinationEnabled((Process) pc,
                                SimulationConstants.SIMULATION_DEST_ENV_1_2_ID)
                        && simDataExist(participant)) {
                    return true;
                }
            } else if (pc instanceof Package) {
                boolean hasSimulation = false;
                Package p = (Package) pc;
                if (DestinationUtil.isValidationDestinationEnabled(p,
                        SimulationConstants.SIMULATION_DEST_ENV_1_2_ID)
                        && simDataExist(participant)) {
                    hasSimulation = true;
                }
                if (hasSimulation) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param eo
     *            The object to check.
     * @return true if the object has simulation data.
     */
    private boolean simDataExist(EObject eo) {
        ParticipantSimulationDataType simData =
                SimulationXpdlUtils
                        .getParticipantSimulationData((Participant) eo);
        return simData != null;
    }

}
