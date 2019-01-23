/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.event;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.DataFilterPicker;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.DataFilterPicker.DataPickerType;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.navigator.pickers.BaseObjectPicker;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processinterface.properties.AssociatedParamContentProvider;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.resources.ui.components.BaseTableControl;
import com.tibco.xpd.resources.ui.components.XpdToolkit;
import com.tibco.xpd.resources.ui.components.actions.TableAddAction;
import com.tibco.xpd.resources.ui.components.actions.TableDeleteAction;
import com.tibco.xpd.resources.ui.components.actions.TableMoveDownAction;
import com.tibco.xpd.resources.ui.components.actions.TableMoveUpAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerAddAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerDeleteAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerMoveDownAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerMoveUpAction;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.properties.table.CheckboxCellEditor;
import com.tibco.xpd.ui.properties.table.MultilineTextCellEditor;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.AssociatedParametersContainer;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Description;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.extension.EMFSearchUtil;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * AssociatedParameterTable
 * 
 * 
 * @author bharge
 * @since 3.3 (27 Oct 2009)
 */
public class AssociatedParameterTable extends BaseTableControl {
    private final EditingDomain editingDomain;

    private IContentProvider contentProvider;

    public final static String ALL_INTERFACE_PARAMETERS_LABEL =
            Messages.AssociatedParamContentProvider_AllInterfaceParamatersLabel;

    public final static String ALL_PROCESS_PARAMETERS_LABEL =
            Messages.AssociatedParamContentProvider_AllProcessParamatersLabel;

    public final static String ALL_PROCESS_DATA_LABEL =
            Messages.AssociatedParamContentProvider_AllProcessDataLabel;

    private TableAddAction addAction = null;

    /**
     * @param parent
     * @param toolkit
     */
    public AssociatedParameterTable(Composite parent, XpdToolkit toolkit,
            EditingDomain editingDomain) {
        super(parent, toolkit, null, false);
        this.editingDomain = editingDomain;
        createContents(parent, toolkit, null);
        enableCellSpecificTooltips();
    }

    /**
     * @return the addAction
     */
    public TableAddAction getAddAction() {
        return addAction;
    }

    /**
     * @param addAction
     *            the addAction to set
     */
    public void setAddAction(TableAddAction addAction) {
        this.addAction = addAction;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#addColumns
     * (org.eclipse.jface.viewers.ColumnViewer)
     */
    @Override
    protected void addColumns(ColumnViewer viewer) {
        new ParameterNameColumn(editingDomain, viewer);
        new ModeColumn(editingDomain, viewer);
        new MandatoryColumn(editingDomain, viewer);
        new DescriptionColumn(editingDomain, viewer);

        setColumnProportions(new float[] { 0.3f, 0.15f, 0.15f, 0.4f });
    }

    /**
     * Get the input of this table.
     * 
     * @return
     */
    private EObject getInput() {
        return (EObject) (getViewer() != null ? getViewer().getInput() : null);
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.resources.ui.components.BaseColumnViewerControl#
     * getViewerContentProvider()
     */
    @Override
    protected IContentProvider getViewerContentProvider() {
        if (contentProvider == null) {
            return new AssociatedParamContentProvider();
        }
        return contentProvider;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#createAddAction
     * (org.eclipse.jface.viewers.ColumnViewer)
     */
    @Override
    protected ViewerAddAction createAddAction(ColumnViewer viewer) {
        addAction =
                new TableAddAction(viewer,
                        Messages.TaskParametersSection_AddLabel,
                        Messages.AddAssociatedParamAction_AddIfcAction_label) {

                    @Override
                    protected Object addRow(StructuredViewer viewer) {
                        CompoundCommand cmd =
                                new CompoundCommand(
                                        Messages.AssociatedParameterSection_AddParametersCmd_label);
                        Object input = getInput();
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
                                    cmd.append(Xpdl2ModelUtil
                                            .getSetOtherElementCommand(editingDomain,
                                                    activity,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getDocumentRoot_AssociatedParameters(),
                                                    associatedParameters));
                                }

                                // Remove Objects not picked
                                List<AssociatedParameter> associatedParametersToRemove =
                                        getAssociatedParametersToRemove(activity,
                                                objectPicked,
                                                associatedParameters
                                                        .getAssociatedParameter());
                                for (AssociatedParameter associatedParameter : associatedParametersToRemove) {
                                    cmd.append(RemoveCommand
                                            .create(editingDomain,
                                                    associatedParameters,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getAssociatedParameters_AssociatedParameter(),
                                                    associatedParameter));
                                }

                                boolean isUserOrManualTask = false;
                                TaskType taskType =
                                        TaskObjectUtil
                                                .getTaskTypeStrict(activity);
                                if (TaskType.USER_LITERAL.equals(taskType)
                                        || TaskType.MANUAL_LITERAL
                                                .equals(taskType)) {
                                    isUserOrManualTask = true;
                                }

                                if (objectPicked.length > 0) {
                                    /*
                                     * Sid XPD-2087: Unset the Disable Implicit
                                     * Association flag as that doesn't make
                                     * sense if there are explicit associations.
                                     */
                                    cmd.append(SetCommand
                                            .create(editingDomain,
                                                    associatedParameters,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getAssociatedParameters_DisableImplicitAssociation(),
                                                    false));
                                }

                                // Add Objects picked
                                for (Object paramPicked : objectPicked) {
                                    if (paramPicked instanceof ProcessRelevantData) {
                                        ProcessRelevantData param =
                                                (ProcessRelevantData) paramPicked;
                                        // Check if the associated parameter
                                        // already
                                        // exists
                                        if (EMFSearchUtil
                                                .findInList(associatedParameters
                                                        .getAssociatedParameter(),
                                                        XpdExtensionPackage.eINSTANCE
                                                                .getAssociatedParameter_FormalParam(),
                                                        param.getName()) == null) {
                                            AssociatedParameter associatedParameter =
                                                    ProcessInterfaceUtil
                                                            .createAssociatedParam(param);
                                            associatedParameter
                                                    .setFormalParam(param
                                                            .getName());

                                            //
                                            // There is a BPMN / iProcess
                                            // validation
                                            // rule that states that
                                            // read only data MUST be input mode
                                            // for
                                            // User / Manual tasks.
                                            // So we should make sure we set the
                                            // mode
                                            // appropriately in such
                                            // cases.
                                            // So override the assoc param mode
                                            // set by
                                            // createAssociatedParam()
                                            if (isUserOrManualTask
                                                    && param.isReadOnly()) {
                                                associatedParameter
                                                        .setMode(ModeType.IN_LITERAL);
                                            }

                                            /*
                                             * XPD-6009: if the activity is an
                                             * user task and the data refers a
                                             * Case Class then the Mode Type and
                                             * mandatory flag should be as it is
                                             * for FormalParamters "WHEREAS" for
                                             * DataField the Mode Type should be
                                             * "IN" and Mandatory Flag should be
                                             * "false"
                                             */
                                            if (TaskType.USER_LITERAL
                                                    .equals(taskType)
                                                    && param.getDataType() instanceof RecordType) {

                                                if (param instanceof FormalParameter) {
                                                    /*
                                                     * FormalParam hence set
                                                     * mode and mandatory as it
                                                     * is
                                                     */
                                                    associatedParameter
                                                            .setMode(((FormalParameter) param)
                                                                    .getMode());
                                                    associatedParameter
                                                            .setMandatory(((FormalParameter) param)
                                                                    .isRequired());
                                                } else {
                                                    /*
                                                     * set Mode = "IN",
                                                     * Mandatory = "false"
                                                     */
                                                    associatedParameter
                                                            .setMode(ModeType.IN_LITERAL);
                                                    associatedParameter
                                                            .setMandatory(false);
                                                }
                                            }

                                            cmd.append(AddCommand
                                                    .create(editingDomain,
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
                            Object[] picked =
                                    pickInterfaceParameters(parameterContainer);
                            if (picked != null) {

                                // Remove Objects not picked
                                List<AssociatedParameter> associatedParametersToRemove =
                                        getAssociatedParametersToRemove(null,
                                                picked,
                                                parameterContainer
                                                        .getAssociatedParameters());
                                if (!associatedParametersToRemove.isEmpty()) {
                                    cmd.append(RemoveCommand
                                            .create(editingDomain,
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
                                        FormalParameter param =
                                                (FormalParameter) itemPicked;

                                        // Check if the associated parameter
                                        // already
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

                                            associatedList
                                                    .add(associatedParameter);
                                        }
                                    }
                                }
                                if (!(associatedList.isEmpty())) {
                                    /*
                                     * Sid XPD-2087: Unset the Disable Implicit
                                     * Association flag as that doesn't make
                                     * sense if there are explicit associations.
                                     */
                                    cmd.append(SetCommand
                                            .create(editingDomain,
                                                    parameterContainer,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getAssociatedParametersContainer_DisableImplicitAssociation(),
                                                    false));

                                    cmd.append(AddCommand
                                            .create(editingDomain,
                                                    parameterContainer,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getAssociatedParametersContainer_AssociatedParameters(),
                                                    associatedList));
                                }
                            }

                        }

                        if (editingDomain != null) {
                            if (cmd.canExecute()) {
                                editingDomain.getCommandStack().execute(cmd);

                                // Display.getCurrent().asyncExec(new Runnable()
                                // {
                                //
                                // public void run() {
                                // paramSection.doRefreshTabs();
                                // }
                                // });
                            }
                        }

                        return null;
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
                                    && associatedParameters
                                            .getAssociatedParameter() != null) {
                                List<AssociatedParameter> associatedParameterList =
                                        associatedParameters
                                                .getAssociatedParameter();
                                Set<ProcessRelevantData> prdList =
                                        resolveProcessRelevantData(activity,
                                                associatedParameterList);
                                if (prdList != null) {
                                    picker.setInitialElementSelections(new ArrayList<ProcessRelevantData>(
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
                            Activity activity,
                            List<AssociatedParameter> associatedParameterList) {
                        Set<ProcessRelevantData> prdList =
                                new HashSet<ProcessRelevantData>();
                        if (associatedParameterList != null) {
                            for (AssociatedParameter associatedParameter : associatedParameterList) {
                                ProcessRelevantData prd =
                                        ProcessInterfaceUtil
                                                .getProcessRelevantDataFromAssociatedParam(associatedParameter);

                                if (prd != null) {
                                    if (activity != null
                                            && ProcessInterfaceUtil
                                                    .isEventImplemented(activity)) {
                                        /*
                                         * For implementing events we should NOT
                                         * include process inteface params as
                                         * that mean sthe user could remove
                                         * them!
                                         */
                                        if (prd.eContainer() instanceof Process) {
                                            prdList.add(prd);
                                        }
                                    } else {
                                        prdList.add(prd);
                                    }
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
                                    && parametersContainer
                                            .getAssociatedParameters() != null) {
                                List<AssociatedParameter> associatedParameterList =
                                        parametersContainer
                                                .getAssociatedParameters();
                                Set<ProcessRelevantData> prdList =
                                        resolveProcessRelevantData(null,
                                                associatedParameterList);
                                if (prdList != null) {
                                    picker.setInitialElementSelections(new ArrayList<ProcessRelevantData>(
                                            prdList));
                                }
                            }
                        }

                        if (picker.open() == DataFilterPicker.OK) {
                            picked = picker.getResult();
                        }
                        return picked;
                    }

                    private List<AssociatedParameter> getAssociatedParametersToRemove(
                            Activity activity,
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

                            Boolean eventImplemented = false;
                            if (activity != null) {
                                eventImplemented =
                                        ProcessInterfaceUtil
                                                .isEventImplemented(activity);
                            }

                            for (AssociatedParameter existingAssociatedParameter : existingAssociatedParameters) {
                                ProcessRelevantData existingPRD =
                                        ProcessInterfaceUtil
                                                .getProcessRelevantDataFromAssociatedParam(existingAssociatedParameter);
                                if (existingPRD != null
                                        && !pickedProcessRelevantData
                                                .contains(existingPRD)) {
                                    EObject obj =
                                            EMFSearchUtil
                                                    .findInList(existingAssociatedParameters,
                                                            XpdExtensionPackage.eINSTANCE
                                                                    .getAssociatedParameter_FormalParam(),
                                                            existingPRD
                                                                    .getName());
                                    if (obj instanceof AssociatedParameter) {
                                        /*
                                         * if this is an implementing event then
                                         * don't ever remove interface params.
                                         */
                                        if (!eventImplemented
                                                || (existingPRD.eContainer() instanceof Process)) {
                                            associatedParametersToRemove
                                                    .add((AssociatedParameter) obj);
                                        }
                                    }
                                }
                            }
                        }
                        return associatedParametersToRemove;
                    }
                };

        return addAction;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.resources.ui.components.BaseColumnViewerControl#
     * createDeleteAction(org.eclipse.jface.viewers.ColumnViewer)
     */
    @Override
    protected ViewerDeleteAction createDeleteAction(ColumnViewer viewer) {
        return new TableDeleteAction(
                viewer,
                Messages.TaskParametersSection_RemoveLabel,
                Messages.DeleteAssociatedParamAction_DeleteInterfaceAssociatedParam_label) {

            @Override
            protected void deleteRows(IStructuredSelection selection) {
                CompoundCommand cmd =
                        new CompoundCommand(
                                Messages.DeleteAssociatedParamAction_CmdRemoveAssociatedParam_label);

                if (selection != null && !selection.isEmpty()) {
                    for (Iterator<?> iter = selection.iterator(); iter
                            .hasNext();) {
                        Object obj = iter.next();
                        cmd.append(RemoveCommand.create(editingDomain, obj));
                    }
                }
                if (cmd.canExecute()) {
                    editingDomain.getCommandStack().execute(cmd);
                }
            }

            @Override
            protected boolean canDelete(IStructuredSelection selection) {
                if (super.canDelete(selection)) {
                    if (getInput() instanceof Activity) {
                        Activity activity = (Activity) getInput();

                        if (ReplyActivityUtil.isReplyActivity(activity)) {
                            return false;
                        }

                        if (ProcessInterfaceUtil.isEventImplemented((activity))) {
                            for (Iterator<?> iterator = selection.iterator(); iterator
                                    .hasNext();) {
                                Object obj = iterator.next();
                                if (obj instanceof AssociatedParameter) {
                                    AssociatedParameter associatedParameter =
                                            (AssociatedParameter) obj;
                                    ProcessRelevantData param =
                                            ProcessInterfaceUtil
                                                    .getProcessRelevantDataFromAssociatedParam(associatedParameter);
                                    if (param.eContainer() instanceof ProcessInterface) {
                                        return false;
                                    }
                                }
                            }
                        }
                    }

                    return true;
                }
                return false;
            }

        };
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.resources.ui.components.BaseColumnViewerControl#
     * createMoveDownAction(org.eclipse.jface.viewers.ColumnViewer)
     */
    @Override
    protected ViewerMoveDownAction createMoveDownAction(ColumnViewer viewer) {
        return new TableMoveDownAction(viewer,
                Messages.PropertiesSection_DownLabel,
                Messages.AssociatedParamHandler_MoveParamDown_shortdesc) {

            @Override
            protected void moveDown(Object element) {
                Command cmd =
                        getMoveParameterCommand(editingDomain,
                                (AssociatedParameter) element,
                                false);
                if (cmd.canExecute()) {
                    editingDomain.getCommandStack().execute(cmd);
                }
            }

            /*
             * (non-Javadoc)
             * 
             * @see
             * com.tibco.xpd.resources.ui.components.actions.TableMoveDownAction
             * #canMoveDown(org.eclipse.jface.viewers.IStructuredSelection,
             * org.eclipse.jface.viewers.StructuredViewer)
             */
            @Override
            protected boolean canMoveDown(IStructuredSelection selection,
                    StructuredViewer viewer) {

                if (super.canMoveDown(selection, viewer)) {
                    if (getInput() instanceof Activity) {
                        Activity activity = (Activity) getInput();

                        if (ReplyActivityUtil.isReplyActivity(activity)) {
                            return false;
                        }

                        if (ProcessInterfaceUtil.isEventImplemented((activity))) {
                            for (Iterator<?> iterator = selection.iterator(); iterator
                                    .hasNext();) {
                                Object obj = iterator.next();
                                if (obj instanceof AssociatedParameter) {
                                    AssociatedParameter associatedParameter =
                                            (AssociatedParameter) obj;
                                    ProcessRelevantData param =
                                            ProcessInterfaceUtil
                                                    .getProcessRelevantDataFromAssociatedParam(associatedParameter);
                                    if (param.eContainer() instanceof ProcessInterface) {
                                        return false;
                                    }
                                }
                            }
                        }
                    }

                    return true;
                }
                return false;
            }

        };
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.resources.ui.components.BaseColumnViewerControl#
     * createMoveUpAction(org.eclipse.jface.viewers.ColumnViewer)
     */
    @Override
    protected ViewerMoveUpAction createMoveUpAction(ColumnViewer viewer) {
        return new TableMoveUpAction(viewer,
                Messages.PropertiesSection_UpLabel,
                Messages.AssociatedParamHandler_MoveParamUp_shortdesc) {

            @Override
            protected void moveUp(Object element) {
                Command cmd =
                        getMoveParameterCommand(editingDomain,
                                (AssociatedParameter) element,
                                true);
                if (cmd.canExecute()) {
                    editingDomain.getCommandStack().execute(cmd);
                }
            }

            @Override
            protected boolean canMoveUp(IStructuredSelection selection,
                    StructuredViewer viewer) {

                if (super.canMoveUp(selection, viewer)) {
                    if (getInput() instanceof Activity) {
                        Activity activity = (Activity) getInput();

                        if (ReplyActivityUtil.isReplyActivity(activity)) {
                            return false;
                        }

                        if (ProcessInterfaceUtil.isEventImplemented((activity))) {
                            if (getViewerContentProvider() instanceof IStructuredContentProvider) {
                                IStructuredContentProvider viewerCP =
                                        (IStructuredContentProvider) getViewerContentProvider();

                                Set<AssociatedParameter> selectedProcessParams =
                                        new HashSet<AssociatedParameter>();

                                for (Iterator<?> iterator =
                                        selection.iterator(); iterator
                                        .hasNext();) {
                                    Object obj = iterator.next();
                                    if (obj instanceof AssociatedParameter) {
                                        AssociatedParameter associatedParameter =
                                                (AssociatedParameter) obj;

                                        ProcessRelevantData param =
                                                ProcessInterfaceUtil
                                                        .getProcessRelevantDataFromAssociatedParam(associatedParameter);
                                        if (param.eContainer() instanceof ProcessInterface) {
                                            /*
                                             * Can NEVER move a process
                                             * interface param on implementing
                                             * event.
                                             */
                                            return false;
                                        }

                                        selectedProcessParams
                                                .add(associatedParameter);

                                    }
                                }

                                /*
                                 * Also can not move up if parameter above is an
                                 * interface parameter.
                                 */
                                Object[] elements =
                                        viewerCP.getElements(getInput());
                                if (elements != null) {
                                    for (int i = 1; i < elements.length; i++) {
                                        Object obj = elements[i];
                                        if (selectedProcessParams.contains(obj)) {
                                            Object prevObj = elements[i - 1];

                                            if (prevObj instanceof AssociatedParameter) {
                                                AssociatedParameter associatedParameter =
                                                        (AssociatedParameter) prevObj;

                                                ProcessRelevantData param =
                                                        ProcessInterfaceUtil
                                                                .getProcessRelevantDataFromAssociatedParam(associatedParameter);
                                                if (param.eContainer() instanceof ProcessInterface) {
                                                    return false;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    return true;
                }
                return false;
            }

        };
    }

    /**
     * Get <code>Command</code> to move the parameter up or down in the table.
     * 
     * @param ed
     *            Editing domain
     * @param database
     *            the <code>DatabaseType</code> object that is affected
     * @param selection
     *            Selection in the table
     * @param moveUp
     *            Set to <b>true</b> if the parameter needs moving up and
     *            <b>false</b> if the parameter needs moving down
     * @return <code>Command</code> to update the parameter listing
     */
    private Command getMoveParameterCommand(EditingDomain ed,
            AssociatedParameter selectedParam, boolean moveUp) {
        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.AssociatedParamHandler_MoveParamCmd_shortdesc) {
                    @Override
                    public boolean canExecute() {
                        return true;
                    }
                };
        List<AssociatedParameter> associatedParameters = null;
        Object input = getInput();
        if (input instanceof AssociatedParametersContainer) {
            AssociatedParametersContainer assocParamsContainer =
                    (AssociatedParametersContainer) input;
            associatedParameters =
                    assocParamsContainer.getAssociatedParameters();
            if (associatedParameters != null && !associatedParameters.isEmpty()
                    && selectedParam != null) {
                int index = associatedParameters.indexOf(selectedParam);

                if (moveUp && index > 0) {
                    cmd.append(RemoveCommand.create(editingDomain,
                            selectedParam));
                    cmd.append(AddCommand
                            .create(editingDomain,
                                    input,
                                    XpdExtensionPackage.eINSTANCE
                                            .getAssociatedParametersContainer_AssociatedParameters(),
                                    selectedParam,
                                    index - 1));
                } else if (!moveUp && index < associatedParameters.size()) {
                    cmd.append(RemoveCommand.create(editingDomain,
                            selectedParam));
                    cmd.append(AddCommand
                            .create(editingDomain,
                                    input,
                                    XpdExtensionPackage.eINSTANCE
                                            .getAssociatedParametersContainer_AssociatedParameters(),
                                    selectedParam,
                                    index + 1));
                }
            }

        } else if (input instanceof Activity) {
            Activity activity = (Activity) input;
            AssociatedParameters assocParamsParent =
                    (AssociatedParameters) Xpdl2ModelUtil
                            .getOtherElement(activity,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_AssociatedParameters());
            if (assocParamsParent != null) {
                associatedParameters =
                        assocParamsParent.getAssociatedParameter();
                if (associatedParameters != null
                        && !associatedParameters.isEmpty()
                        && selectedParam != null) {
                    int index = associatedParameters.indexOf(selectedParam);

                    if (moveUp && index > 0) {
                        cmd.append(RemoveCommand.create(editingDomain,
                                selectedParam));
                        cmd.append(AddCommand
                                .create(editingDomain,
                                        assocParamsParent,
                                        XpdExtensionPackage.eINSTANCE
                                                .getAssociatedParameters_AssociatedParameter(),
                                        selectedParam,
                                        index - 1));
                    } else if (!moveUp && index < associatedParameters.size()) {
                        cmd.append(RemoveCommand.create(editingDomain,
                                selectedParam));
                        cmd.append(AddCommand
                                .create(editingDomain,
                                        assocParamsParent,
                                        XpdExtensionPackage.eINSTANCE
                                                .getAssociatedParameters_AssociatedParameter(),
                                        selectedParam,
                                        index + 1));
                    }

                }
            }
        }
        return cmd;
    }

    private class ParameterNameColumn extends AbstractColumn {

        /**
         * @param editingDomain
         * @param viewer
         * @param style
         * @param heading
         * @param width
         */
        public ParameterNameColumn(EditingDomain editingDomain,
                ColumnViewer viewer) {
            super(editingDomain, viewer, SWT.NONE,
                    Messages.AssociatedParamHandler_ParamNameHeader_label, 200);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getForeground
         * (java.lang.Object)
         */
        @Override
        protected Color getForeground(Object element) {
            if (element instanceof AssociatedParameter) {
                AssociatedParameter associatedParameter =
                        (AssociatedParameter) element;
                if (associatedParameter.eContainer() instanceof AssociatedParametersContainer
                        && getInput() instanceof Activity) {
                    return Display.getDefault()
                            .getSystemColor(SWT.COLOR_DARK_GRAY);
                }

            } else if (element instanceof ImplicitAssociatedParamObject) {
                return Display.getDefault().getSystemColor(SWT.COLOR_DARK_GRAY);
            }
            return Display.getDefault()
                    .getSystemColor(SWT.COLOR_LIST_FOREGROUND);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getShowImage()
         */
        @Override
        protected boolean getShowImage() {
            return true;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getImage(java
         * .lang.Object)
         */
        @Override
        protected Image getImage(Object element) {
            if (element instanceof AssociatedParameter) {

                ProcessRelevantData data =
                        ProcessInterfaceUtil
                                .getProcessRelevantDataFromAssociatedParam((AssociatedParameter) element);

                return data != null ? WorkingCopyUtil.getImage(data)
                        : Xpdl2ResourcesPlugin.getDefault().getImageRegistry()
                                .get(Xpdl2ResourcesConsts.ICON_FORMALPARAMETER);

            } else if (element instanceof ImplicitAssociatedParamObject) {
                return ((ImplicitAssociatedParamObject) element).getImage();
            }

            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getCellEditor
         * (java.lang.Object)
         */
        @Override
        protected CellEditor getCellEditor(Object element) {
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getSetValueCommand
         * (java.lang.Object, java.lang.Object)
         */
        @Override
        protected Command getSetValueCommand(Object element, Object value) {
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getText(java
         * .lang.Object)
         */
        @Override
        protected String getText(Object element) {
            if (element instanceof AssociatedParameter) {
                AssociatedParameter parameter = (AssociatedParameter) element;

                /*
                 * Use the label of the referenced data instaead of the Name
                 * stored in the associated parameter.
                 */
                ProcessRelevantData data =
                        ProcessInterfaceUtil
                                .getProcessRelevantDataFromAssociatedParam(parameter);

                if (data != null) {
                    return Xpdl2ModelUtil.getDisplayNameOrName(data);
                }
                return parameter.getFormalParam();

            } else if (element instanceof ImplicitAssociatedParamObject) {
                return ((ImplicitAssociatedParamObject) element).getParamName();
            }
            return null;
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getToolTipText(java.lang.Object)
         * 
         * @param element
         * @return
         */
        @Override
        protected String getToolTipText(Object element) {
            if (element instanceof AssociatedParameter) {
                AssociatedParameter parameter = (AssociatedParameter) element;

                /*
                 * Use the label of the referenced data instead of the Name
                 * stored in the associated parameter.
                 */
                ProcessRelevantData data =
                        ProcessInterfaceUtil
                                .getProcessRelevantDataFromAssociatedParam(parameter);

                if (data != null) {
                    String label = Xpdl2ModelUtil.getDisplayNameOrName(data);
                    String name = data.getName();

                    if (!label.equals(name)) {
                        return String.format("%s (%s)", label, name); //$NON-NLS-1$
                    } else {
                        return label;
                    }
                }
                return parameter.getFormalParam();

            } else if (element instanceof ImplicitAssociatedParamObject) {
                return ((ImplicitAssociatedParamObject) element).getParamName();
            }

            return super.getToolTipText(element);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getValueForEditor
         * (java.lang.Object)
         */
        @Override
        protected Object getValueForEditor(Object element) {
            return getText(element);
        }

    }

    private class ModeColumn extends AbstractColumn {
        private ComboBoxViewerCellEditor editor;

        /**
         * @param editingDomain
         * @param viewer
         * @param style
         * @param heading
         * @param width
         */
        public ModeColumn(EditingDomain editingDomain, ColumnViewer viewer) {
            super(editingDomain, viewer, SWT.NONE,
                    Messages.AssociatedParamHandler_ParamModeHeader_label, 80);
            /*
             * XPD-6789: Saket: This editor should be read only.
             */
            editor =
                    new ComboBoxViewerCellEditor(
                            (Composite) viewer.getControl(), SWT.READ_ONLY);
            editor.setContenProvider(new ArrayContentProvider());
            editor.setLabelProvider(new LabelProvider());
            editor.setInput(new String[] {
                    Messages.AssociatedParamHandler_CmdValIn_value,
                    Messages.AssociatedParamHandler_CmbValOut_value,
                    Messages.AssociatedParamHandler_CmbInOutVal_value });
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getCellEditor
         * (java.lang.Object)
         */
        @Override
        protected CellEditor getCellEditor(Object element) {
            if (element instanceof AssociatedParameter) {
                return editor;
            }
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getSetValueCommand
         * (java.lang.Object, java.lang.Object)
         */
        @Override
        protected Command getSetValueCommand(Object element, Object value) {
            CompoundCommand cmd = null;
            if (element instanceof AssociatedParameter) {
                cmd = new CompoundCommand();
                AssociatedParameter parameter = (AssociatedParameter) element;
                String str = ((String) value).toUpperCase();
                if (((String) value)
                        .equals(Messages.AssociatedParamHandler_CmdValIn_value)) {
                    str = ModeType.IN_LITERAL.getLiteral();
                    editor.setValue(Messages.AssociatedParamHandler_CmdValIn_value);
                } else if (((String) value)
                        .equals(Messages.AssociatedParamHandler_CmbValOut_value)) {
                    str = ModeType.OUT_LITERAL.getLiteral();
                    editor.setValue(Messages.AssociatedParamHandler_CmbValOut_value);
                } else if (((String) value)
                        .equals(Messages.AssociatedParamHandler_CmbInOutVal_value)) {
                    str = ModeType.INOUT_LITERAL.getLiteral();
                    editor.setValue(Messages.AssociatedParamHandler_CmbInOutVal_value);
                }

                ModeType modeType = ModeType.get(str);

                cmd.append(SetCommand.create(editingDomain,
                        parameter,
                        XpdExtensionPackage.eINSTANCE
                                .getAssociatedParameter_Mode(),
                        modeType)); // ModeType.get(Integer.parseInt(value.
                // toString())))
            }
            return cmd;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getText(java
         * .lang.Object)
         */
        @Override
        protected String getText(Object element) {
            if (element instanceof AssociatedParameter) {
                AssociatedParameter parameter = (AssociatedParameter) element;
                // return new Integer(parameter.getMode().getValue());
                // return parameter.getMode().getLiteral();

                ModeType paramMode = parameter.getMode();
                // ModeType paramMode = ProcessInterfaceUtil
                // .getAssocParamModeType(param);
                switch (paramMode.getValue()) {
                case ModeType.IN:
                    return Messages.AssociatedParamHandler_CmdValIn_value;
                case ModeType.OUT:
                    return Messages.AssociatedParamHandler_CmbValOut_value;
                case ModeType.INOUT:
                    return Messages.AssociatedParamHandler_CmbInOutVal_value;
                }
                return paramMode.toString();
            }
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getValueForEditor
         * (java.lang.Object)
         */
        @Override
        protected Object getValueForEditor(Object element) {
            return getText(element);
        }

    }

    private class DescriptionColumn extends AbstractColumn {
        private final MultilineTextCellEditor editor;

        /**
         * @param editingDomain
         * @param viewer
         * @param style
         * @param heading
         * @param width
         */
        public DescriptionColumn(EditingDomain editingDomain,
                ColumnViewer viewer) {
            super(editingDomain, viewer, SWT.NONE,
                    Messages.AssociatedParamHandler_ParamDescHeader_label, 350);
            editor =
                    new MultilineTextCellEditor(
                            (Composite) viewer.getControl(), SWT.MULTI
                                    | SWT.WRAP | SWT.V_SCROLL);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getCellEditor
         * (java.lang.Object)
         */
        @Override
        protected CellEditor getCellEditor(Object element) {
            if (element instanceof AssociatedParameter) {
                return editor;
            }
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getSetValueCommand
         * (java.lang.Object, java.lang.Object)
         */
        @Override
        protected Command getSetValueCommand(Object element, Object value) {
            CompoundCommand cmd = null;
            if (element instanceof AssociatedParameter
                    && (value instanceof String || value == null)) {
                AssociatedParameter parameter = (AssociatedParameter) element;
                String newValue = (String) value;

                Description description = parameter.getDescription();

                cmd = new CompoundCommand();

                if (newValue != null && newValue.length() > 0) {
                    if (description == null) {
                        description =
                                Xpdl2Factory.eINSTANCE.createDescription();
                        cmd.append(SetCommand.create(editingDomain,
                                parameter,
                                Xpdl2Package.eINSTANCE
                                        .getDescribedElement_Description(),
                                description));
                    }
                    cmd.append(SetCommand.create(editingDomain,
                            description,
                            Xpdl2Package.eINSTANCE.getDescription_Value(),
                            value));

                } else {
                    if (description != null) {
                        cmd.append(SetCommand.create(editingDomain,
                                parameter,
                                Xpdl2Package.eINSTANCE
                                        .getDescribedElement_Description(),
                                SetCommand.UNSET_VALUE));
                    }
                }
            }
            return cmd;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getText(java
         * .lang.Object)
         */
        @Override
        protected String getText(Object element) {
            String ret = null;
            if (element instanceof AssociatedParameter) {
                AssociatedParameter associatedParameter =
                        (AssociatedParameter) element;

                if (associatedParameter.getDescription() != null) {
                    String val =
                            associatedParameter.getDescription().getValue();
                    if (val != null) {
                        int idx = val.indexOf('\n');
                        if (idx != -1) {
                            // When showing label for multi-line value then
                            // append
                            // chevron to indicate that there is more.
                            ret = val.substring(0, idx - 1) + " >>"; //$NON-NLS-1$
                        } else {
                            ret = val;
                        }
                    }
                }
            }
            return ret != null ? ret : ""; //$NON-NLS-1$
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getValueForEditor
         * (java.lang.Object)
         */
        @Override
        protected Object getValueForEditor(Object element) {
            return getText(element);
        }

    }

    private class MandatoryColumn extends AbstractColumn {
        private final CheckboxCellEditor editor;

        /**
         * @param editingDomain
         * @param viewer
         * @param style
         * @param heading
         * @param width
         */
        public MandatoryColumn(EditingDomain editingDomain, ColumnViewer viewer) {
            super(editingDomain, viewer, SWT.NONE,
                    Messages.AssociatedParamHandler_ParamMandatoryHeader_label,
                    80);
            editor =
                    new CheckboxCellEditor((Composite) viewer.getControl(),
                            SWT.NONE);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getCellEditor
         * (java.lang.Object)
         */
        @Override
        protected CellEditor getCellEditor(Object element) {
            if (element instanceof AssociatedParameter) {
                return editor;
            }
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getShowImage()
         */
        @Override
        protected boolean getShowImage() {
            return true;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getImage(java
         * .lang.Object)
         */
        @Override
        protected Image getImage(Object element) {
            if (element instanceof AssociatedParameter) {
                AssociatedParameter parameter = (AssociatedParameter) element;
                if (parameter.isMandatory()) {
                    return CheckboxCellEditor.getImgChecked();
                }
                return CheckboxCellEditor.getImgUnchecked();
            }
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getSetValueCommand
         * (java.lang.Object, java.lang.Object)
         */
        @Override
        protected Command getSetValueCommand(Object element, Object value) {
            CompoundCommand cmd = null;
            if (element instanceof AssociatedParameter) {
                AssociatedParameter parameter = (AssociatedParameter) element;
                cmd = new CompoundCommand();
                cmd.append(SetCommand.create(editingDomain,
                        parameter,
                        XpdExtensionPackage.eINSTANCE
                                .getAssociatedParameter_Mandatory(),
                        value));
            }
            return cmd;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getText(java
         * .lang.Object)
         */
        @Override
        protected String getText(Object element) {
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getValueForEditor
         * (java.lang.Object)
         */
        @Override
        protected Object getValueForEditor(Object element) {
            if (element instanceof AssociatedParameter) {
                AssociatedParameter associatedParameter =
                        (AssociatedParameter) element;
                return new Boolean(associatedParameter.isMandatory());
            }
            return null;
        }

    }
}
