/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.event.actions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.analyst.resources.xpdl2.pickers.DataFilterPicker;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.DataFilterPicker.DataPickerType;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.navigator.pickers.BaseObjectPicker;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.event.AssociatedParameterSection;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.ui.properties.table.TableWithButtons;
import com.tibco.xpd.ui.properties.table.TableWithButtonsNewRowAction;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.AssociatedParametersContainer;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.extension.EMFSearchUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Action to add an Associated Parameter to either a start or intermediate
 * method. Required by {@link TableWithButtons} to enable the Add button
 * 
 * @author rsomayaj
 * 
 * 
 */
public class AddAssociatedParamAction extends TableWithButtonsNewRowAction {

    private Object input;

    private AssociatedParameterSection paramSection;

    public AddAssociatedParamAction(StructuredViewer viewer,
            AssociatedParameterSection paramSection) {
        super(viewer, Messages.AddAssociatedParamAction_AddIfcAction_label);
        this.paramSection = paramSection;

    }

    @Override
    protected Object createNewRow(String firstCellVal) {

        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.AssociatedParameterSection_AddParametersCmd_label);

        if (input instanceof Activity) {
            Activity activity = (Activity) input;

            Object[] objectPicked = pickParameters(activity);
            if (objectPicked != null) {
                AssociatedParameters associatedParameters =
                        (AssociatedParameters) activity
                                .getOtherElement(XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_AssociatedParameters()
                                        .getName());
                if (associatedParameters == null) {
                    associatedParameters =
                            XpdExtensionFactory.eINSTANCE
                                    .createAssociatedParameters();
                    cmd
                            .append(Xpdl2ModelUtil
                                    .getSetOtherElementCommand(paramSection
                                            .getEditingDomain(),
                                            activity,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_AssociatedParameters(),
                                            associatedParameters));
                }
                // Remove Objects not picked
                List<AssociatedParameter> associatedParametersToRemove =
                        getAssociatedParametersToRemove(objectPicked,
                                associatedParameters.getAssociatedParameter());
                for (AssociatedParameter associatedParameter : associatedParametersToRemove) {
                    cmd
                            .append(RemoveCommand
                                    .create(paramSection.getEditingDomain(),
                                            associatedParameters,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getAssociatedParameters_AssociatedParameter(),
                                            associatedParameter));
                }

                boolean isUserOrManualTask = false;
                TaskType taskType = TaskObjectUtil.getTaskTypeStrict(activity);
                if (TaskType.USER_LITERAL.equals(taskType)
                        || TaskType.MANUAL_LITERAL.equals(taskType)) {
                    isUserOrManualTask = true;
                }

                // Add Objects picked
                for (Object paramPicked : objectPicked) {
                    if (paramPicked instanceof ProcessRelevantData) {
                        ProcessRelevantData param =
                                (ProcessRelevantData) paramPicked;
                        // Check if the associated parameter already
                        // exists
                        if (EMFSearchUtil.findInList(associatedParameters
                                .getAssociatedParameter(),
                                XpdExtensionPackage.eINSTANCE
                                        .getAssociatedParameter_FormalParam(),
                                param.getName()) == null) {
                            AssociatedParameter associatedParameter =
                                    ProcessInterfaceUtil
                                            .createAssociatedParam(param);
                            associatedParameter.setFormalParam(param.getName());
                            
                            //
                            // There is a BPMN / iProcess validation rule that states that
                            // read only data MUST be input mode for User / Manual tasks.
                            // So we should make sure we set the mode appropriately in such
                            // cases.
                            // So override the assoc param mode set by createAssociatedParam()
                            if (isUserOrManualTask && param.isReadOnly()) {
                                associatedParameter.setMode(ModeType.IN_LITERAL);
                            }
                            
                            cmd
                                    .append(AddCommand
                                            .create(paramSection
                                                    .getEditingDomain(),
                                                    associatedParameters,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getAssociatedParameters_AssociatedParameter(),
                                                    associatedParameter));
                        }
                    }
                }
            }
        } else if (input instanceof AssociatedParametersContainer) {
            AssociatedParametersContainer parameterContainer =
                    (AssociatedParametersContainer) input;
            Object[] picked = pickInterfaceParameters(parameterContainer);
            if (picked != null) {
                // Remove Objects not picked
                List<AssociatedParameter> associatedParametersToRemove =
                        getAssociatedParametersToRemove(picked,
                                parameterContainer.getAssociatedParameters());
                if (!associatedParametersToRemove.isEmpty()) {
                    cmd
                            .append(RemoveCommand
                                    .create(paramSection.getEditingDomain(),
                                            parameterContainer,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getAssociatedParametersContainer_AssociatedParameters(),
                                            associatedParametersToRemove));
                }
                // Create message object
                // Process each data field picked
                List<AssociatedParameter> associatedList =
                        new ArrayList<AssociatedParameter>();
                for (Object itemPicked : picked) {
                    if (itemPicked instanceof FormalParameter) {
                        FormalParameter param = (FormalParameter) itemPicked;

                        // Check if the associated parameter already
                        // exists
                        EObject obj =
                                EMFSearchUtil
                                        .findInList(parameterContainer
                                                .getAssociatedParameters(),
                                                XpdExtensionPackage.eINSTANCE
                                                        .getAssociatedParameter_FormalParam(),
                                                param.getName());
                        if (obj == null) {
                            AssociatedParameter associatedParameter =
                                    ProcessInterfaceUtil
                                            .createAssociatedParam(param);

                            associatedList.add(associatedParameter);
                        }
                    }
                }
                if (!(associatedList.isEmpty())) {
                    cmd
                            .append(AddCommand
                                    .create(paramSection.getEditingDomain(),
                                            parameterContainer,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getAssociatedParametersContainer_AssociatedParameters(),
                                            associatedList));
                }
            }

        }

        if (paramSection.getEditingDomain() != null) {
            if (cmd.canExecute()) {

                paramSection.getEditingDomain().getCommandStack().execute(cmd);

                Display.getCurrent().asyncExec(new Runnable() {

                    public void run() {
                        paramSection.doRefreshTabs();
                    }
                });
            }
        }

        return null;
    }

    private List<AssociatedParameter> getAssociatedParametersToRemove(
            Object[] objectPicked,
            List<AssociatedParameter> existingAssociatedParameters) {
        Set<ProcessRelevantData> pickedProcessRelevantData =
                new HashSet<ProcessRelevantData>();
        List<AssociatedParameter> associatedParametersToRemove =
                new ArrayList<AssociatedParameter>();
        if (existingAssociatedParameters != null) {
            for (Object paramPicked : objectPicked) {
                if (paramPicked instanceof ProcessRelevantData) {
                    pickedProcessRelevantData
                            .add((ProcessRelevantData) paramPicked);
                }
            }
            for (AssociatedParameter existingAssociatedParameter : existingAssociatedParameters) {
                ProcessRelevantData existingPRD =
                        ProcessInterfaceUtil
                                .getProcessRelevantDataFromAssociatedParam(existingAssociatedParameter);
                if (existingPRD != null
                        && !pickedProcessRelevantData.contains(existingPRD)) {
                    EObject obj =
                            EMFSearchUtil
                                    .findInList(existingAssociatedParameters,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getAssociatedParameter_FormalParam(),
                                            existingPRD.getName());
                    if (obj instanceof AssociatedParameter) {
                        associatedParametersToRemove
                                .add((AssociatedParameter) obj);
                    }
                }
            }
        }
        return associatedParametersToRemove;
    }

    @Override
    protected String getNewRowFirstCellVal() {
        return Messages.AddAssociatedParamAction_ParameterInitialVal_label;
    }

    public void setInput(Object input) {
        this.input = input;
    }

    private Object[] pickParameters(Activity activity) {
        Object[] picked = null;
        if (activity != null) {
            DataFilterPicker picker = null;
            picker =
                    new DataFilterPicker(getShell(),
                            getDataPickerType(activity), true);
            picker.setScope(activity);
            AssociatedParameters associatedParameters =
                    (AssociatedParameters) activity
                            .getOtherElement(XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_AssociatedParameters()
                                    .getName());
            if (associatedParameters != null
                    && associatedParameters.getAssociatedParameter() != null) {
                List<AssociatedParameter> associatedParameterList =
                        associatedParameters.getAssociatedParameter();
                Set<ProcessRelevantData> prdList =
                        resolveProcessRelevantData(associatedParameterList);
                if (prdList != null) {
                    picker
                            .setInitialElementSelections(new ArrayList<ProcessRelevantData>(
                                    prdList));
                }
            }

            if (picker.open() == BaseObjectPicker.OK) {
                picked = picker.getResult();
            }
        }
        return picked;
    }

    /**
     * @param act
     * @return Get the type objects that should be pickable.
     */
    private DataPickerType getDataPickerType(Activity act) {

        return DataPickerType.DATAFIELD_FORMALPARAMETER;
    }

    private Set<ProcessRelevantData> resolveProcessRelevantData(
            List<AssociatedParameter> associatedParameterList) {
        Set<ProcessRelevantData> prdList = new HashSet<ProcessRelevantData>();
        if (associatedParameterList != null) {
            for (AssociatedParameter associatedParameter : associatedParameterList) {
                ProcessRelevantData prd =
                        ProcessInterfaceUtil
                                .getProcessRelevantDataFromAssociatedParam(associatedParameter);
                if (prd != null) {
                    prdList.add(prd);
                }
            }
        }
        return prdList;
    }

    /**
     * @param activity
     *            The activity to pick a field for.
     * @param list
     *            The list to pick the field for.
     * @return The picked items or null.
     */
    private Object[] pickInterfaceParameters(
            AssociatedParametersContainer parametersContainer) {
        Object[] picked = null;
        DataFilterPicker picker =
                new DataFilterPicker(getShell(),
                        DataPickerType.FORMALPARAMETER, true);
        if (parametersContainer != null) {
            picker.setScope(parametersContainer.eContainer());
            if (parametersContainer != null
                    && parametersContainer.getAssociatedParameters() != null) {
                List<AssociatedParameter> associatedParameterList =
                        parametersContainer.getAssociatedParameters();
                Set<ProcessRelevantData> prdList =
                        resolveProcessRelevantData(associatedParameterList);
                if (prdList != null) {
                    picker
                            .setInitialElementSelections(new ArrayList<ProcessRelevantData>(
                                    prdList));
                }
            }
        }

        if (picker.open() == DataFilterPicker.OK) {
            picked = picker.getResult();
        }
        return picked;
    }

}
