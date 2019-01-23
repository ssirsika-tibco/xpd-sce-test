package com.tibco.xpd.processeditor.xpdl2.contributors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IProject;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.extensions.IDropObjectContribution;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.DiagramDropObjectUtils;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.LocalDropObjectUtils;
import com.tibco.xpd.processwidget.adapters.DropObjectPopupItem;
import com.tibco.xpd.processwidget.adapters.DropTypeInfo;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.processwidget.popup.actions.SetTaskTypeAction.UserTask;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeExtPointHelper;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeReference;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypesMergedInfo;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.GraphicalNode;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.ViewType;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.edit.ui.ComplexDataUIUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Drop contribution to handle BOM entity drops on process editor. When BOM
 * entities are dragged from project explorer onto process editor this drop
 * contribution handles the required drop feedback, and creation of data and
 * user task when actual drop has occurred.
 * 
 * @author rsawant
 * @since 16-May-2013
 */
public class BomEntityDropContribution implements IDropObjectContribution {

    @Override
    public DropTypeInfo getDropTypeInfo(Object targetContainerObject,
            List<Object> dropObjects, Point location,
            Object actualTargetObject, int userRequestedDropType) {

        boolean validdrop = true;
        for (Object object : dropObjects) {
            if (!(object instanceof Type)) {
                validdrop = false;
                break;
            }
        }
        DropTypeInfo dropType = new DropTypeInfo(DND.DROP_NONE);

        // if all drop items are valid then only proceed
        if (validdrop) {
            dropType.setFeedbackRectangles(DiagramDropObjectUtils
                    .getTaskFeedbackRectangles(1));

            // if lane (process background) is the target
            if (actualTargetObject instanceof Lane) {
                dropType = new DropTypeInfo(DND.DROP_COPY);
            }

            // if User task is the target
            if (actualTargetObject instanceof Activity
                    && TaskType.USER_LITERAL.equals(TaskObjectUtil
                            .getTaskTypeStrict((Activity) actualTargetObject))) {
                dropType = new DropTypeInfo(DND.DROP_MOVE);
                dropType.setFeedbackRectangles(DiagramDropObjectUtils
                        .getTaskFeedbackRectangles(0));
            }

            // if embedded sub-process/event sub-process is the target

            /*
             * ABPM-911: Saket: An event subprocess should mostly behave like an
             * embedded sub-process.
             */
            if (actualTargetObject instanceof Activity
                    && (TaskType.EMBEDDED_SUBPROCESS_LITERAL
                            .equals(TaskObjectUtil
                                    .getTaskTypeStrict((Activity) actualTargetObject)) || TaskType.EVENT_SUBPROCESS_LITERAL
                            .equals(TaskObjectUtil
                                    .getTaskTypeStrict((Activity) actualTargetObject)))
                    && (!isEmbeddedSubProcessCollapsed((Activity) actualTargetObject))) {
                dropType = new DropTypeInfo(DND.DROP_COPY);
            }

            // if transition is the target
            if (actualTargetObject instanceof Transition) {
                dropType = new DropTypeInfo(DND.DROP_COPY);

            }

        }
        return dropType;
    }

    @Override
    public List<DropObjectPopupItem> getDropPopupItems(
            EditingDomain editingDomain, Object targetObject,
            List<Object> dropObjects, Point location,
            Object actualTargetObject, int userRequestedDropType) {

        /*
         * Ask user to confirm for 'need to create a project reference' if any
         * are dragged from different project.
         */
        if (targetObject instanceof EObject) {
            List<EObject> dropEObjs = new ArrayList<EObject>();
            for (Object o : dropObjects) {
                EObject eo = (EObject) o;
                if (eo != null) {
                    dropEObjs.add(eo);
                }
            }
            if (!ProcessUIUtil.checkAndAddProjectReferences(Display
                    .getDefault().getActiveShell(),
                    (EObject) targetObject,
                    dropEObjs)) {
                return Collections.emptyList();
            }
        }
        List<DropObjectPopupItem> popupItems =
                new ArrayList<DropObjectPopupItem>();

        if (actualTargetObject instanceof Lane
                || actualTargetObject instanceof Activity
                || actualTargetObject instanceof Transition) {

            Process targetProcess =
                    Xpdl2ModelUtil.getProcess((EObject) actualTargetObject);
            List<DropObjectPopupItem> items =
                    new ArrayList<DropObjectPopupItem>();

            if (actualTargetObject instanceof Activity
                    && TaskType.USER_LITERAL.equals(TaskObjectUtil
                            .getTaskTypeStrict((Activity) actualTargetObject))) {

                // create new bom data from bom entity and assign it
                items.addAll(createAndAssignBomData(editingDomain,
                        (Activity) actualTargetObject,
                        dropObjects));

            }

            // if the actual target is a Lane OR is the actualTarget a
            // Transition with an underlying Lane
            else if (actualTargetObject instanceof Lane
                    || (actualTargetObject instanceof Transition && targetObject instanceof Lane)) {

                // create user task, process data, and associate them
                items.addAll(createUserTaskFromBomEntities(editingDomain,
                        dropObjects,
                        actualTargetObject,
                        location,
                        (Lane) targetObject));

                // Add separator to group similar items
                if (!items.isEmpty()) {
                    items.add(DropObjectPopupItem.createSeparatorItem());
                }

                // create new parameter from bom entity
                items.addAll(createParametersFromBomEntities(editingDomain,
                        targetProcess,
                        dropObjects,
                        actualTargetObject));

                // create new data field from bom entity
                items.addAll(createDataFieldsFromBomEntities(editingDomain,
                        targetProcess,
                        dropObjects,
                        actualTargetObject));

            } else if (actualTargetObject instanceof Activity
                    && (TaskType.EMBEDDED_SUBPROCESS_LITERAL
                            .equals(TaskObjectUtil
                                    .getTaskTypeStrict((Activity) actualTargetObject)) || TaskType.EVENT_SUBPROCESS_LITERAL
                            .equals(TaskObjectUtil
                                    .getTaskTypeStrict((Activity) actualTargetObject)))) {

                /*
                 * ABPM-911: Saket: An event subprocess should mostly behave
                 * like an embedded sub-process.
                 */

                // proceed if embedded/event sub-process is not in collapsed
                // state
                Activity activity = (Activity) actualTargetObject;
                if (!isEmbeddedSubProcessCollapsed(activity)) {

                    items.addAll(createUserTaskFromBomEntities(editingDomain,
                            dropObjects,
                            actualTargetObject,
                            location,
                            activity));
                }

            } else if (actualTargetObject instanceof Transition
                    && targetObject instanceof Activity) {
                /*
                 * If actualTargetObject is transition and targetObject is
                 * Activity then it must mean drop target is sequence flow in an
                 * embedded sub-process
                 */
                Activity embeddedSubProcessActivity = (Activity) targetObject;
                items.addAll(createUserTaskFromBomEntities(editingDomain,
                        dropObjects,
                        actualTargetObject,
                        location,
                        embeddedSubProcessActivity));
            }

            popupItems.addAll(items);
            return popupItems;
        }
        return null;
    }

    /**
     * Creates a list of {@link DropObjectPopupItem}s to <b>create and
     * assign/associate new</b> {@link DataField}/{@link FormalParameter} that
     * refer to and have similar, but unique, name as of selected BOM Entity to
     * drop onto the target {@link Activity}
     * 
     * @param editingDomain
     * @param actualTargetObject
     * @param dropObjects
     * @return A list {@link DropObjectPopupItem}
     */
    private List<DropObjectPopupItem> createAndAssignBomData(
            EditingDomain editingDomain, Activity actualTargetObject,
            List<Object> dropObjects) {
        List<DropObjectPopupItem> popupItems =
                new ArrayList<DropObjectPopupItem>();
        Process targetProcess = Xpdl2ModelUtil.getProcess(actualTargetObject);

        List<ProcessRelevantData> dataList =
                new ArrayList<ProcessRelevantData>();
        List<ProcessRelevantData> exstDataList =
                new ArrayList<ProcessRelevantData>();

        for (Object object : dropObjects) {
            ProcessRelevantData exstData =
                    findRelevantExistingData(object,
                            targetProcess,
                            actualTargetObject);
            if (exstData != null) {
                exstDataList.add(exstData);
            }
        }

        // Sub menu for 'Create and associate formal params to existing user
        // task'
        CompoundCommand cmd = new CompoundCommand();

        for (Object object : dropObjects) {
            if (!exstDataList.contains(findRelevantExistingData(object,
                    targetProcess,
                    actualTargetObject))) {
                dataList.add(createProcessRelevantData(editingDomain,
                        object,
                        targetProcess,
                        Xpdl2Package.eINSTANCE.getFormalParameter()));
            }
        }

        if (!dataList.isEmpty()) {
            cmd.append(getCreateParametersFromBomEntitiesCommand(editingDomain,
                    targetProcess,
                    dataList,
                    actualTargetObject));
        }

        // add existing relevant data to the new data list
        dataList.addAll(exstDataList);
        popupItems
                .add(DropObjectPopupItem
                        .createSubMenu(createDataSubMenuPopupItems(editingDomain,
                                actualTargetObject,
                                dataList,
                                cmd),
                                Messages.BomEntityDropContribution_CreateNewParamAndAssign,
                                Xpdl2ProcessEditorPlugin
                                        .getDefault()
                                        .getImageRegistry()
                                        .get(ProcessEditorConstants.IMG_DATA_INOUT)));

        // sub menu for 'create and associate data fields to user task'
        dataList = new ArrayList<ProcessRelevantData>();
        cmd = new CompoundCommand();

        for (Object object : dropObjects) {

            if (!exstDataList.contains(findRelevantExistingData(object,
                    targetProcess,
                    actualTargetObject))) {
                dataList.add(createProcessRelevantData(editingDomain,
                        object,
                        targetProcess,
                        Xpdl2Package.eINSTANCE.getDataField()));
            }
        }
        if (!dataList.isEmpty()) {
            cmd.append(getCreateDataFieldsFromBomEntitiesCommand(editingDomain,
                    targetProcess,
                    dataList,
                    actualTargetObject));
        }

        // add existing relevant data to the new data list
        dataList.addAll(exstDataList);
        popupItems
                .add(DropObjectPopupItem
                        .createSubMenu(createDataSubMenuPopupItems(editingDomain,
                                actualTargetObject,
                                dataList,
                                cmd),
                                Messages.BomEntityDropContribution_CreateNewDataFieldAndAssign,
                                Xpdl2ProcessEditorPlugin
                                        .getDefault()
                                        .getImageRegistry()
                                        .get(ProcessEditorConstants.IMG_DATAFIELD_NEW)));
        return popupItems;
    }

    /**
     * Generates {@link DropObjectPopupItem} sub-menu to associate data with the
     * target activity
     * 
     * @param editingDomain
     * @param actualTargetObject
     * @param dataList
     * @param cmd
     * @return A list of {@link DropObjectPopupItem}
     */
    private List<DropObjectPopupItem> createDataSubMenuPopupItems(
            EditingDomain editingDomain, Activity actualTargetObject,
            List<ProcessRelevantData> dataList, Command cmd) {

        List<DropObjectPopupItem> dataPopupItems =
                new ArrayList<DropObjectPopupItem>();

        // popup item to create associated params with IN/OUT mode and set them
        // on activity
        CompoundCommand inOutCompoundCmd = new CompoundCommand();

        if ((cmd instanceof CompoundCommand && !((CompoundCommand) cmd)
                .isEmpty())
                || !((cmd instanceof CompoundCommand))
                && cmd instanceof Command) {
            inOutCompoundCmd.append(cmd);
        }

        AssociatedParameters assocParams =
                createAssociatedParams(editingDomain,
                        dataList,
                        ModeType.INOUT_LITERAL,
                        actualTargetObject);
        inOutCompoundCmd.append(Xpdl2ModelUtil
                .getSetOtherElementCommand(editingDomain,
                        actualTargetObject,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_AssociatedParameters(),
                        assocParams));

        dataPopupItems.add(DropObjectPopupItem
                .createCommandItem(inOutCompoundCmd,
                        Messages.Xpdl2TaskAdapter_AddDataToInOutParams_menu,
                        Xpdl2ProcessEditorPlugin.getDefault()
                                .getImageRegistry()
                                .get(ProcessEditorConstants.IMG_DATA_INOUT)));

        // popup item to create associated params with IN mode and set them on
        // activity
        CompoundCommand inCompoundCmd = new CompoundCommand();

        if ((cmd instanceof CompoundCommand && !((CompoundCommand) cmd)
                .isEmpty())
                || !((cmd instanceof CompoundCommand))
                && cmd instanceof Command) {
            inCompoundCmd.append(cmd);
        }

        assocParams =
                createAssociatedParams(editingDomain,
                        dataList,
                        ModeType.IN_LITERAL,
                        actualTargetObject);
        inCompoundCmd.append(Xpdl2ModelUtil
                .getSetOtherElementCommand(editingDomain,
                        actualTargetObject,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_AssociatedParameters(),
                        assocParams));

        dataPopupItems.add(DropObjectPopupItem.createCommandItem(inCompoundCmd,
                Messages.Xpdl2TaskAdapter_AddDataToInParams_menu,
                Xpdl2ProcessEditorPlugin.getDefault().getImageRegistry()
                        .get(ProcessEditorConstants.IMG_DATA_IN)));

        // popup item to create associated params with OUT mode and set them on
        // activity
        CompoundCommand outCompoundCmd = new CompoundCommand();

        if ((cmd instanceof CompoundCommand && !((CompoundCommand) cmd)
                .isEmpty())
                || !((cmd instanceof CompoundCommand))
                && cmd instanceof Command) {
            outCompoundCmd.append(cmd);
        }

        assocParams =
                createAssociatedParams(editingDomain,
                        dataList,
                        ModeType.OUT_LITERAL,
                        actualTargetObject);
        outCompoundCmd.append(Xpdl2ModelUtil
                .getSetOtherElementCommand(editingDomain,
                        actualTargetObject,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_AssociatedParameters(),
                        assocParams));

        dataPopupItems.add(DropObjectPopupItem
                .createCommandItem(outCompoundCmd,
                        Messages.Xpdl2TaskAdapter_AddDataToOutParams_menu,
                        Xpdl2ProcessEditorPlugin.getDefault()
                                .getImageRegistry()
                                .get(ProcessEditorConstants.IMG_DATA_OUT)));
        return dataPopupItems;
    }

    /**
     * Creates {@link AssociatedParameters} for {@link ProcessRelevantData}
     * 
     * @param editingDomain
     * @param dataList
     * @param mode
     * @param activity
     * @return {@link AssociatedParameters}
     */
    private AssociatedParameters createAssociatedParams(
            EditingDomain editingDomain, List<ProcessRelevantData> dataList,
            ModeType mode, Activity activity) {

        /*
         * XPD-5880 : If the activity already has AssociatedParameters assigned
         * then preserve those while adding new associated parameters.
         */
        AssociatedParameters assocParams =
                LocalDropObjectUtils
                        .copyOrCreateAssocParams(editingDomain,
                                (AssociatedParameters) Xpdl2ModelUtil
                                        .getOtherElement(activity,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_AssociatedParameters()));
        LocalDropObjectUtils.addDataItemsToAssocParams(assocParams,
                dataList,
                mode,
                activity);
        return assocParams;
    }

    /**
     * Creates a list of {@link DropObjectPopupItem}s for creating
     * {@link FormalParameter}, {@link UserTask}, and their association from BOM
     * Entities
     * 
     * @param editingDomain
     * @param actualTargetObject
     * @param dropObjects
     * @param targetContainerObject
     * @param actualTargetObject2
     * @return A list of {@link DropObjectPopupItem}
     */
    private List<DropObjectPopupItem> createUserTaskFromBomEntities(
            EditingDomain editingDomain, List<Object> dropObjects,
            Object actualTargetObject, Point loaction,
            GraphicalNode targetContainerObject) {

        List<DropObjectPopupItem> popupItems =
                new ArrayList<DropObjectPopupItem>();
        Process targetProcess =
                Xpdl2ModelUtil.getProcess((EObject) actualTargetObject);
        NodeGraphicsInfo nodeInfo = null;

        if (targetContainerObject != null) {
            nodeInfo =
                    Xpdl2ModelUtil.getNodeGraphicsInfo(targetContainerObject);
        }
        String laneId = nodeInfo != null ? nodeInfo.getLaneId() : null;

        if (!dropObjects.isEmpty()) {
            List<ProcessRelevantData> newDataList =
                    new ArrayList<ProcessRelevantData>();

            for (Object object : dropObjects) {
                ProcessRelevantData exstData =
                        findRelevantExistingData(object,
                                targetProcess,
                                targetContainerObject);

                if (exstData != null) {
                    newDataList.add(exstData);
                } else {
                    newDataList.add(createProcessRelevantData(editingDomain,
                            object,
                            targetProcess,
                            Xpdl2Package.eINSTANCE.getDataField()));
                }
            }

            // popup item for in/out
            popupItems.add(LocalDropObjectUtils
                    .createTaskPopupItemForFieldParams(targetProcess,
                            laneId,
                            newDataList,
                            loaction,
                            ModeType.INOUT_LITERAL));

            // popup item for in
            popupItems.add(LocalDropObjectUtils
                    .createTaskPopupItemForFieldParams(targetProcess,
                            laneId,
                            newDataList,
                            loaction,
                            ModeType.IN_LITERAL));

            // popup item for out
            popupItems.add(LocalDropObjectUtils
                    .createTaskPopupItemForFieldParams(targetProcess,
                            laneId,
                            newDataList,
                            loaction,
                            ModeType.OUT_LITERAL));
        }
        return popupItems;
    }

    /**
     * Finds existing data (Field/Parameter) that matches exact name and type of
     * the BOM entity being dropped
     * 
     * @param object
     * @param targetContainerObject
     * @return {@link ProcessRelevantData} if there is exact match, otherwise
     *         <code>null</code>
     */
    private ProcessRelevantData findRelevantExistingData(Object object,
            Process targetProcess, GraphicalNode targetContainerObject) {

        if (object instanceof Type) {
            Type clazz = (Type) object;
            String className = clazz.getName();

            Pattern pattern = Pattern.compile(className + "[0-9]*"); //$NON-NLS-1$

            /*
             * SIDFIX - when checking for existing data, use the data that is in
             * scope of the target container (which if it is activity (i.e.
             * embedded sub-process that we should include it's locally defined
             * fields
             */
            List<ProcessRelevantData> scopeData = Collections.emptyList();

            if (targetContainerObject instanceof Activity) {
                scopeData =
                        ProcessInterfaceUtil
                                .getActivityDataInScopeOrder((Activity) targetContainerObject);
            } else {
                scopeData =
                        ProcessDataUtil.getProcessRelevantData(targetProcess);
            }

            for (ProcessRelevantData pData : scopeData) {

                /*
                 * SIDFIX - It is possible to get into a situation where we have
                 * created a field suffixed with "(2)" etc (thus resulting in a
                 * name ClassA2 etc) I managed to do this by dragging ClassA
                 * first into embedded sub-process (which creates field ClassA
                 * at embedded sub-process level rather than process. Then drag
                 * ClassA into lane and it will create field ClassA2.
                 * 
                 * Therefore I think we can just say "if process data name is
                 * ClassName with or without suffixing integer numbers then
                 * that's a match
                 */
                Matcher matcher = pattern.matcher(pData.getName());

                if (matcher.matches()) {
                    /*
                     * Also we should check that actual types are the same
                     * within the loop so that we check the next if this one
                     * isn't correct type.
                     */

                    if (clazz.equals(BasicTypeConverterFactory.INSTANCE
                            .getBaseType(pData, false))) {
                        return pData;
                    }
                }

            }

        }
        return null;
    }

    /**
     * Creates list of {@link DropObjectPopupItem}s for creating
     * {@link DataField}s from BOM Entities
     * 
     * @param editingDomain
     * @param targetProcess
     * @param dropObjects
     * @param actualTargetObject
     * @return A List of {@link DropObjectPopupItem}s
     */
    private List<DropObjectPopupItem> createDataFieldsFromBomEntities(
            EditingDomain editingDomain, Process targetProcess,
            List<Object> dropObjects, Object actualTargetObject) {
        List<DropObjectPopupItem> popupItems =
                new ArrayList<DropObjectPopupItem>();
        CompoundCommand cmd = null;

        if (!dropObjects.isEmpty()) {
            cmd =
                    getCreateDataFieldsFromBomEntitiesCommand(editingDomain,
                            targetProcess,
                            dropObjects,
                            actualTargetObject);

            popupItems.add(DropObjectPopupItem.createCommandItem(cmd,
                    Messages.BomEntityDropContribution_CreateNewDataField,
                    Xpdl2ProcessEditorPlugin.getDefault().getImageRegistry()
                            .get(ProcessEditorConstants.IMG_DATAFIELD_NEW)));
        }
        return popupItems;
    }

    /**
     * Creates command to create {@link DataField}(s) from BOM Entities
     * 
     * @param editingDomain
     * @param targetProcess
     * @param dropObjects
     *            Drop objects can be of type {@link ProcessRelevantData} or
     *            {@link Type}
     * @param actualTargetObject
     * @return
     */
    private CompoundCommand getCreateDataFieldsFromBomEntitiesCommand(
            EditingDomain editingDomain, Process targetProcess,
            List<? extends Object> dropObjects, Object actualTargetObject) {

        ProcessRelevantData input = null;
        CompoundCommand cmd = new CompoundCommand();

        for (Object object : dropObjects) {
            if (object instanceof ProcessRelevantData) {

                input = (ProcessRelevantData) object;
            } else {
                input =
                        createProcessRelevantData(editingDomain,
                                object,
                                targetProcess,
                                Xpdl2Package.eINSTANCE.getDataField());
            }
            if (input != null) {
                cmd.append(AddCommand.create(editingDomain,
                        targetProcess,
                        Xpdl2Package.eINSTANCE
                                .getDataFieldsContainer_DataFields(),
                        input));
            }
        }
        return cmd;
    }

    /**
     * Creates list of {@link DropObjectPopupItem}s for creating
     * {@link FormalParameter}s from BOM Entities
     * 
     * @param editingDomain
     * @param targetObject
     * @param dropObjects
     * @param actualTargetObject
     * @return List of {@link DropObjectPopupItem}s
     */
    private List<DropObjectPopupItem> createParametersFromBomEntities(
            EditingDomain editingDomain, Process targetProcess,
            List<Object> dropObjects, Object actualTargetObject) {

        List<DropObjectPopupItem> popupItems =
                new ArrayList<DropObjectPopupItem>();
        CompoundCommand cmd = null;

        if (!dropObjects.isEmpty()) {

            cmd =
                    getCreateParametersFromBomEntitiesCommand(editingDomain,
                            targetProcess,
                            dropObjects,
                            actualTargetObject);

            popupItems.add(DropObjectPopupItem.createCommandItem(cmd,
                    Messages.BomEntityDropContribution_CreateNewParameter,
                    Xpdl2ProcessEditorPlugin.getDefault().getImageRegistry()
                            .get(ProcessEditorConstants.IMG_DATA_INOUT)));
        }
        return popupItems;
    }

    /**
     * Creates command to create {@link FormalParameter}(s) from BOM Entities
     * 
     * @param editingDomain
     * @param targetProcess
     * @param dropObjects
     *            Drop objects can be of type {@link ProcessRelevantData} or
     *            {@link Type}
     * @param actualTargetObject
     * @return {@link CompoundCommand}
     */
    private CompoundCommand getCreateParametersFromBomEntitiesCommand(
            EditingDomain editingDomain, Process targetProcess,
            List<? extends Object> dropObjects, Object actualTargetObject) {

        CompoundCommand cmd = new CompoundCommand();
        ProcessRelevantData input = null;

        for (Object object : dropObjects) {
            if (object instanceof ProcessRelevantData) {

                input = (ProcessRelevantData) object;
            } else {
                input =
                        createProcessRelevantData(editingDomain,
                                object,
                                targetProcess,
                                Xpdl2Package.eINSTANCE.getFormalParameter());
            }

            if (input != null) {
                cmd.append(AddCommand
                        .create(editingDomain,
                                targetProcess,
                                Xpdl2Package.eINSTANCE
                                        .getFormalParametersContainer_FormalParameters(),
                                input));
            }
        }
        return cmd;
    }

    /**
     * Creates Process relevant data: {@link FormalParameter} or
     * {@link DataField}
     * 
     * @param editingDomain
     * @param object
     * @param targetProcess
     * @param dataType
     *            Meta object class for a type of {@link ProcessRelevantData} to
     *            create. Either {@link FormalParameter} or {@link DataField}
     * @return {@link ProcessRelevantData}
     */
    private ProcessRelevantData createProcessRelevantData(
            EditingDomain editingDomain, Object object, Process targetProcess,
            EClass dataType) {

        ProcessRelevantData input = null;

        if (object instanceof Type) {
            Type clazz = (Type) object;
            String dataName = ""; //$NON-NLS-1$
            Xpdl2Factory factory = Xpdl2Factory.eINSTANCE;

            if (dataType.equals(Xpdl2Package.eINSTANCE.getFormalParameter())) {

                input = factory.createFormalParameter();
                ((FormalParameter) input).setMode(ModeType.INOUT_LITERAL);
                ((FormalParameter) input).setRequired(true);
            } else if (dataType.equals(Xpdl2Package.eINSTANCE.getDataField())) {

                input = factory.createDataField();
            }
            dataName =
                    Xpdl2ModelUtil.getUniqueLabelInSet(PrimitivesUtil
                            .getDisplayLabel(clazz), ProcessInterfaceUtil
                            .getAllDataDefinedInProcess(targetProcess));

            Xpdl2ModelUtil
                    .setOtherAttribute(input, XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_DisplayName(), dataName);
            input.setName(NameUtil.getInternalName(dataName, false));

            ExternalReference reference =
                    createXpdl2ExternalRef(editingDomain, targetProcess, clazz);
            input.setDataType(reference);
        }
        return input;
    }

    /**
     * Creates XPDL external reference from complex data type reference.
     * 
     * @param editingDomain
     * @param targetProcess
     * @param clazz
     *            BOM Complex data type
     * @return
     */
    private ExternalReference createXpdl2ExternalRef(
            EditingDomain editingDomain, Process targetProcess, Type clazz) {

        IProject project = WorkingCopyUtil.getProjectFor(targetProcess);
        ComplexDataTypesMergedInfo complexDataTypesMergedInfo =
                getComplexTypesInfo();

        ComplexDataTypeReference complexDataTypeRef =
                ComplexDataUIUtil.resolveSelectionToReference(clazz,
                        project,
                        complexDataTypesMergedInfo);

        ExternalReference extRef =
                complexDataTypeRefToXpdl2Ref(complexDataTypeRef);

        return extRef;
    }

    /**
     * Creates XPDL external reference from complex data type reference
     * 
     * @param complexDataTypeRef
     *            ComplexDataTypeReference
     * @return extReference ExternalReference
     */
    private ExternalReference complexDataTypeRefToXpdl2Ref(
            ComplexDataTypeReference complexDataTypeRef) {

        ExternalReference extReference =
                Xpdl2Factory.eINSTANCE.createExternalReference();
        extReference.setLocation(complexDataTypeRef.getLocation());
        extReference.setXref(complexDataTypeRef.getXRef());
        extReference.setNamespace(complexDataTypeRef.getNameSpace());

        return extReference;
    }

    /**
     * @return
     */
    private ComplexDataTypesMergedInfo getComplexTypesInfo() {
        return ComplexDataTypeExtPointHelper.getAllComplexDataTypesMergedInfo();
    }

    /**
     * Checks whether embedded sub-process is in collapsed state
     * 
     * @param activity
     * @return true if embedded sub-process is in collapsed state
     */
    private boolean isEmbeddedSubProcessCollapsed(Activity activity) {

        if (activity != null) {
            if (activity.getBlockActivity()
                    .eIsSet(Xpdl2Package.eINSTANCE.getBlockActivity_View())) {
                if (ViewType.COLLAPSED.equals(activity.getBlockActivity()
                        .getView())) {
                    return true;
                }
            }
        }
        return false;
    }
}
// BombEntityDropContribution
