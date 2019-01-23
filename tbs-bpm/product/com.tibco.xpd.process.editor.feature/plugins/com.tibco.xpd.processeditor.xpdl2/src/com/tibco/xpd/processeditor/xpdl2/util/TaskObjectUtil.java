/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.Bundle;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.DataFilterPicker;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.DataFilterPicker.DataPickerType;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.FormUrlPicker;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.ParticipantsPicker;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInfoUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.ElementsFactory;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.ProcessWidgetPlugin;
import com.tibco.xpd.processwidget.adapters.ActivityMarkerType;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.ActivityResourcePatterns;
import com.tibco.xpd.xpdExtension.AdHocExecutionTypeType;
import com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType;
import com.tibco.xpd.xpdExtension.AllocationStrategy;
import com.tibco.xpd.xpdExtension.AllocationStrategyType;
import com.tibco.xpd.xpdExtension.AllocationType;
import com.tibco.xpd.xpdExtension.Audit;
import com.tibco.xpd.xpdExtension.AuditEvent;
import com.tibco.xpd.xpdExtension.AuditEventType;
import com.tibco.xpd.xpdExtension.BusinessProcess;
import com.tibco.xpd.xpdExtension.ConditionalParticipant;
import com.tibco.xpd.xpdExtension.FormImplementation;
import com.tibco.xpd.xpdExtension.FormImplementationType;
import com.tibco.xpd.xpdExtension.MultiInstanceScripts;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdExtension.UserTaskScripts;
import com.tibco.xpd.xpdExtension.WorkItemPriority;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.BlockActivity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Icon;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.Loop;
import com.tibco.xpd.xpdl2.LoopMultiInstance;
import com.tibco.xpd.xpdl2.LoopStandard;
import com.tibco.xpd.xpdl2.LoopType;
import com.tibco.xpd.xpdl2.MIFlowConditionType;
import com.tibco.xpd.xpdl2.MIOrderingType;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.No;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.ParticipantTypeElem;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Performers;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Reference;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskReceive;
import com.tibco.xpd.xpdl2.TaskScript;
import com.tibco.xpd.xpdl2.TaskSend;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.TaskUser;
import com.tibco.xpd.xpdl2.TestTimeType;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.UniqueIdElement;
import com.tibco.xpd.xpdl2.ViewType;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.commands.AbstractLateExecuteCommand;
import com.tibco.xpd.xpdl2.commands.LateExecuteCompoundCommand;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl.Xpdl2FileType;
import com.tibco.xpd.xpdl2.util.DecisionFlowUtil;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Various utilities to help in getting / setting BPMN Task-related data.
 * <p>
 * Shared by xpdl2 process widget task adapter and task properties sections.
 * 
 * @author aallway
 * 
 */
public class TaskObjectUtil extends
        com.tibco.xpd.analyst.resources.xpdl2.utils.TaskObjectUtil {
    public static final String UNKNOWN_REF_ID = "-unknown-"; //$NON-NLS-1$

    /**
     * @deprecated The bpmJspTask extended attribute is now deprecated in favour
     *             of new extension model that explicitly states the type of URL
     *             intended by user (UserDefined, Form or pageflow) see
     *             {@link com.tibco.xpd.analyst.resources.xpdl2.utils.TaskObjectUtil#getUserTaskFormImplementation(Activity)}
     *             method for more detail.
     */
    @Deprecated
    public static final String USER_TASK_ATTR = "bpmJspTask"; //$NON-NLS-1$

    private static final String REFLECTION_OM_CORE_PLUGIN_ID =
            "com.tibco.xpd.om.core"; //$NON-NLS-1$

    private static final String REFLECTION_OM_CORE_OMUTIL_CLASS =
            "com.tibco.xpd.om.core.om.util.OMUtil"; //$NON-NLS-1$

    private static final String REFLECTION_OM_PACKAGE_CLASS =
            "com.tibco.xpd.om.core.om.OMPackage"; //$NON-NLS-1$

    private static final String REFLECTION_OM_CORE_NAMEDELEMENT_CLASS =
            "com.tibco.xpd.om.core.om.NamedElement"; //$NON-NLS-1$

    private static final String REFLECTION_OM_GET_ID_METHOD = "getID"; //$NON-NLS-1$

    private static final String REFLECTION_OM_GET_OBJECT_BY_ID_METHOD =
            "getEObjectByID"; //$NON-NLS-1$

    private static final String REFLECTION_OM_GET_DISPLAY_NAME_METHOD =
            "getDisplayName"; //$NON-NLS-1$

    private static Bundle omCoreBundle = null;

    private static Class omUtilCls = null;

    private static Class omPackageCls = null;

    private static Class namedElementCls = null;

    private static Method getIDMeth = null;

    private static Method getObjByIDMeth = null;

    private static Method getDisplayNameMeth = null;

    private static String omNSURI = null;

    public static EObject[] getActivityPerformers(Activity act) {
        return getActivityPerformers(act, act.getProcess());
    }

    public static EObject[] getActivityPerformers(Activity act, Process process) {
        // Take into account that we used to store single participant in
        // activity performer. Now we use activity performers.
        // An old defined task will have single a new one will use exclusively
        // performers.
        EList perfs = act.getPerformerList();

        if (perfs != null && perfs.size() > 0) {
            List<EObject> performerObjects = new ArrayList<EObject>();

            for (Iterator iter = perfs.iterator(); iter.hasNext();) {
                Performer performer = (Performer) iter.next();

                String performerId = performer.getValue();

                Participant partic = process.getParticipant(performerId);

                if (partic != null) {
                    performerObjects.add(partic);
                } else {
                    // Could be a data field / formal param.
                    ProcessRelevantData df = process.getDataField(performerId);

                    if (df == null) {
                        // MR 39410 - begin
                        List<ProcessRelevantData> allAvailableRelevantDataForActivity =
                                ProcessInterfaceUtil
                                        .getAllAvailableRelevantDataForActivity(act);
                        for (ProcessRelevantData processRelevantData : allAvailableRelevantDataForActivity) {
                            if (processRelevantData.getId().equals(performerId)) {
                                df = processRelevantData;
                                break;
                            }
                        }
                        // MR 39410 - end
                        if (df == null) {
                            List fps =
                                    ProcessInterfaceUtil
                                            .getAllFormalParameters(process);
                            for (Iterator iterator = fps.iterator(); iterator
                                    .hasNext();) {
                                FormalParameter fp =
                                        (FormalParameter) iterator.next();

                                if (fp.getId().equals(performerId)) {
                                    df = fp;
                                    break;
                                }
                            }
                            if (df == null) {
                                df =
                                        process.getPackage()
                                                .getDataField(performerId);
                            }
                        }
                    }

                    if (df != null) {
                        performerObjects.add(df);
                    }
                }

            }

            return performerObjects.toArray(new EObject[0]);
        }

        return new EObject[0];
    }

    /**
     * @param act
     * @param obj
     * @return
     */
    public static Participant getParticipantForExternalReference(Activity act,
            EObject obj) {
        try {
            omCoreBundle = Platform.getBundle(REFLECTION_OM_CORE_PLUGIN_ID);
            if (omCoreBundle != null) {
                namedElementCls =
                        omCoreBundle
                                .loadClass(REFLECTION_OM_CORE_NAMEDELEMENT_CLASS);

                List<Participant> participants = new ArrayList<Participant>();

                participants.addAll(act.getProcess().getParticipants());
                participants.addAll(act.getProcess().getPackage()
                        .getParticipants());

                Iterator<Participant> iter = participants.iterator();
                while (iter.hasNext()) {
                    Participant partic = iter.next();
                    if (partic != null && partic.getExternalReference() != null) {
                        EObject refObject =
                                (EObject) getObjByIDMeth.invoke(omUtilCls
                                        .getClass(), partic
                                        .getExternalReference().getXref());
                        if (refObject != null && obj.equals(refObject)) {
                            return partic;
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static Command getChangeTaskImplementationCommand(EditingDomain ed,
            Activity activity, TaskType taskType, String extImplementationType,
            String implementationTypeName) {
        CompoundCommand cmd = null;

        if (activity != null) {
            Implementation implementation = activity.getImplementation();

            if (implementation instanceof Task) {
                Task task = (Task) implementation;

                // Create the change service type command
                if (task != null) {
                    switch (taskType.getValue()) {
                    case TaskType.SERVICE:
                        cmd =
                                getChangeTaskServiceImpl(ed,
                                        extImplementationType,
                                        implementationTypeName,
                                        task);
                        if (cmd != null) {
                            if (isTaskUsingDefaultIconForType(activity)) {
                                cmd.append(new LateExecSetDefTaskIconCommand(
                                        ed, activity));
                            }
                        }

                        break;
                    case TaskType.SEND:
                        cmd =
                                getChangeTaskSendImp(ed,
                                        extImplementationType,
                                        implementationTypeName,
                                        task);
                        break;
                    case TaskType.RECEIVE:
                        cmd =
                                getChangeTaskReceiveImpl(ed,
                                        extImplementationType,
                                        implementationTypeName,
                                        task);
                    default:
                        break;
                    }
                }
            }

        }

        // Clear all assignments
        if (cmd != null && activity.getAssignments() != null
                && !activity.getAssignments().isEmpty()) {
            cmd.append(SetCommand.create(ed,
                    activity,
                    Xpdl2Package.eINSTANCE.getActivity_Assignments(),
                    SetCommand.UNSET_VALUE));
        }
        return cmd;
    }

    public static String getExistingSetCloseUserTaskScriptGrammarId(Activity act) {
        return getExistingUserTaskScriptGrammarId(XpdExtensionPackage.eINSTANCE.getUserTaskScripts_CloseScript(),
                act);
    }

    public static String getExistingSetOpenUserTaskScriptGrammarId(Activity act) {
        return getExistingUserTaskScriptGrammarId(XpdExtensionPackage.eINSTANCE.getUserTaskScripts_OpenScript(),
                act);
    }

    public static String getExistingSetScheduleUserTaskScriptGrammarId(
            Activity act) {
        return getExistingUserTaskScriptGrammarId(XpdExtensionPackage.eINSTANCE.getUserTaskScripts_ScheduleScript(),
                act);
    }

    public static String getExistingSetRescheduleUserTaskScriptGrammarId(
            Activity act) {
        return getExistingUserTaskScriptGrammarId(XpdExtensionPackage.eINSTANCE.getUserTaskScripts_RescheduleScript(),
                act);
    }

    public static String getExistingSetInitiatedAuditScriptGrammarId(
            Activity act) {
        return getExistingAuditScriptGrammarId(AuditEventType.INITIATED_LITERAL,
                act);
    }

    public static String getExistingSetCompletedAuditScriptGrammarId(
            Activity act) {
        return getExistingAuditScriptGrammarId(AuditEventType.COMPLETED_LITERAL,
                act);
    }

    public static String getExistingSetDeadlineExpiredAuditScriptGrammarId(
            Activity act) {
        return getExistingAuditScriptGrammarId(AuditEventType.DEADLINE_EXPIRED_LITERAL,
                act);
    }

    public static String getExistingSetCancelledAuditScriptGrammarId(
            Activity act) {
        return getExistingAuditScriptGrammarId(AuditEventType.CANCELLED_LITERAL,
                act);
    }

    public static String getExistingSetScriptGrammarId(Activity activity) {

        Implementation implementation = activity.getImplementation();
        String existingGrammarId = null;
        if (implementation instanceof Task) {
            Task task = (Task) implementation;
            TaskScript taskScript = task.getTaskScript();
            if (taskScript == null) {
                return existingGrammarId;
            }
            Expression script = taskScript.getScript();
            if (script == null) {
                return existingGrammarId;
            }
            existingGrammarId = script.getScriptGrammar();
        }
        return existingGrammarId;

    }

    public static String getExistingPerformerScriptGrammarId(
            ProcessRelevantData processRelevantData) {
        String existingGrammarId = null;
        ConditionalParticipant conditionalParticipant =
                (ConditionalParticipant) Xpdl2ModelUtil
                        .getOtherElement(processRelevantData,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ConditionalParticipant());
        if (conditionalParticipant == null) {
            return existingGrammarId;
        }
        Expression performerScript =
                conditionalParticipant.getPerformerScript();
        if (null == performerScript) {
            return existingGrammarId;
        }
        existingGrammarId = performerScript.getScriptGrammar();

        return existingGrammarId;
    }

    public static String getExistingQueryParticipantScriptGrammarId(
            Participant participant) {
        ParticipantTypeElem participantType = participant.getParticipantType();
        if (participantType == null) {
            return null;
        }
        if (ParticipantType.RESOURCE_SET_LITERAL.equals(participantType
                .getType())) {
            Object objParticipantQ =
                    Xpdl2ModelUtil.getOtherElement(participantType,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ParticipantQuery());
            if (objParticipantQ instanceof Expression) {
                Expression participantQuery = (Expression) objParticipantQ;
                return participantQuery.getScriptGrammar();
            } else {
                return "Text"; //$NON-NLS-1$
            }
        }
        return null;
    }

    public static String getExistingQueryParticipantPerformerScriptGrammarId(
            DataField dataField) {
        DataType dataType = dataField.getDataType();
        if (dataType instanceof BasicType) {
            BasicType bt = (BasicType) dataType;
            if (bt.getType() != null
                    && bt.getType().equals(BasicTypeType.PERFORMER_LITERAL)) {
                Object otherElement =
                        Xpdl2ModelUtil.getOtherElement(bt,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ParticipantQuery());
                if (otherElement instanceof Expression) {
                    Expression participantQuery = (Expression) otherElement;
                    return participantQuery.getScriptGrammar();
                } else {
                    return "Text"; //$NON-NLS-1$
                }
            }
        }
        return null;
    }

    public static String getExistingSetSubmitUserTaskScriptGrammarId(
            Activity act) {
        return getExistingUserTaskScriptGrammarId(XpdExtensionPackage.eINSTANCE.getUserTaskScripts_SubmitScript(),
                act);
    }

    public static ExtendedAttribute getExtendedAttributeByName(
            Activity activity, String extAttrName) {
        EList extendedAttributes = activity.getExtendedAttributes();
        for (Iterator iter = extendedAttributes.iterator(); iter.hasNext();) {
            ExtendedAttribute extAtt = (ExtendedAttribute) iter.next();
            if (extAtt.getName().equalsIgnoreCase(extAttrName)) {
                return extAtt;
            }
        }
        return null;
    }

    public static ExtendedAttribute getExtendedAttributeByName(
            Transition transition, String extAttrName) {
        EList extendedAttributes = transition.getExtendedAttributes();
        for (Iterator iter = extendedAttributes.iterator(); iter.hasNext();) {
            ExtendedAttribute extAtt = (ExtendedAttribute) iter.next();
            if (extAtt.getName().equalsIgnoreCase(extAttrName)) {
                return extAtt;
            }
        }
        return null;
    }

    public static Set<ActivityMarkerType> getMarkers(Activity act) {
        Set<ActivityMarkerType> markers = new HashSet<ActivityMarkerType>(4);
        if (act != null) {
            ActivitySet aSet = getActivitySet(act);
            if (aSet != null && aSet.isAdHoc()) {
                markers.add(ActivityMarkerType.MARKER_AD_HOC_LITERAL);

            } else if (Xpdl2ModelUtil.getOtherElement(act,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_AdHocTaskConfiguration()) != null) {
                markers.add(ActivityMarkerType.MARKER_AD_HOC_LITERAL);
            }
            Loop actLoop = act.getLoop();
            if (actLoop == null) {
                return markers;
            }
            LoopType loopType = actLoop.getLoopType();
            if (loopType != null && loopType.getValue() == LoopType.STANDARD) {
                LoopStandard loopStandard = actLoop.getLoopStandard();
                if (loopStandard != null) {
                    markers.add(ActivityMarkerType.MARKER_LOOP_LITERAL);
                }
            }
            LoopType mloopType = actLoop.getLoopType();
            if (mloopType != null
                    && mloopType.getValue() == LoopType.MULTI_INSTANCE) {
                LoopMultiInstance loopMultiInstance =
                        actLoop.getLoopMultiInstance();
                if (loopMultiInstance != null) {
                    markers.add(ActivityMarkerType.MARKER_MULTIPLE_LITERAL);
                }
            }
        }
        return markers;
    }

    public static Object getPerformerDescriptionStatic(EObject performer) {
        if (performer instanceof NamedElement) {
            return Xpdl2ModelUtil
                    .getDisplayNameOrName((NamedElement) performer);
        }

        return performer == null ? null : WorkingCopyUtil.getText(performer);
    }

    public static Command getSetMarkersCommand(EditingDomain ed, Activity act,
            Set markers) {

        CompoundCommand cmd = new CompoundCommand();
        cmd.setLabel(Messages.Xpdl2TaskAdapter_SetMarkersTask_menu);

        if (markers.contains(ActivityMarkerType.MARKER_AD_HOC_LITERAL)) {
            ActivitySet aset = getActivitySet(act);

            if (aset != null) {
                if (!aset.isAdHoc()) {

                    cmd.append(SetCommand.create(ed,
                            aset,
                            Xpdl2Package.eINSTANCE.getFlowContainer_AdHoc(),
                            Boolean.TRUE));
                }
            } else {
                /*
                 * If the activity is an user task or a re-usable sub process
                 * then add Adhoc Configuration to it if it already doesnt have
                 * it.
                 */

                TaskType taskType = TaskObjectUtil.getTaskTypeStrict(act);

                if (taskType != null) {

                    if (taskType.equals(TaskType.USER_LITERAL)
                            || taskType.equals(TaskType.SUBPROCESS_LITERAL)) {

                        Object adhocConfig =
                                Xpdl2ModelUtil
                                        .getOtherElement(act,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_AdHocTaskConfiguration());

                        if (adhocConfig == null) {

                            AdHocTaskConfigurationType createAdHocTaskConfigurationType =
                                    XpdExtensionFactory.eINSTANCE
                                            .createAdHocTaskConfigurationType();
                            /*
                             * by default the Adhoc Config should have Automatic
                             * invocation type.
                             */
                            createAdHocTaskConfigurationType
                                    .setAdHocExecutionType(AdHocExecutionTypeType.AUTOMATIC);

                            cmd.append(Xpdl2ModelUtil
                                    .getSetOtherElementCommand(ed,
                                            act,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_AdHocTaskConfiguration(),
                                            createAdHocTaskConfigurationType));
                        }
                    }
                }
            }

        } else {
            ActivitySet aset = getActivitySet(act);
            if (aset != null) {
                if (aset.isAdHoc()) {

                    cmd.append(SetCommand.create(ed,
                            aset,
                            Xpdl2Package.eINSTANCE.getFlowContainer_AdHoc(),
                            Boolean.FALSE));
                }
            }
            /*
             * Remove the Adhoc Configuration if there were any.
             */
            Object adhocConfig =
                    Xpdl2ModelUtil.getOtherElement(act,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_AdHocTaskConfiguration());

            if (adhocConfig != null) {

                cmd.append(SetCommand.create(ed,
                        act,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_AdHocTaskConfiguration(),
                        SetCommand.UNSET_VALUE));

            }
        }

        if (!markers.contains(ActivityMarkerType.MARKER_LOOP_LITERAL)
                && !markers
                        .contains(ActivityMarkerType.MARKER_MULTIPLE_LITERAL)) {
            // If standard loop or multiple loop is set then ditch the whole
            // xpdl2 element.
            Loop loop = act.getLoop();
            if (loop != null) {
                cmd.append(RemoveCommand.create(ed, loop));
            }

        } else {
            if (markers.contains(ActivityMarkerType.MARKER_LOOP_LITERAL)) {
                Loop loop = act.getLoop();
                if (act.getLoop() == null) {
                    loop = Xpdl2Factory.eINSTANCE.createLoop();
                    cmd.append(SetCommand.create(ed,
                            act,
                            Xpdl2Package.eINSTANCE.getActivity_Loop(),
                            loop));
                    loop.setLoopType(LoopType.STANDARD_LITERAL);
                } else {
                    cmd.append(SetCommand.create(ed,
                            loop,
                            Xpdl2Package.eINSTANCE.getLoop_LoopType(),
                            LoopType.STANDARD_LITERAL));
                }

                LoopStandard loopStandard = loop.getLoopStandard();
                if (loopStandard == null) {
                    loopStandard = Xpdl2Factory.eINSTANCE.createLoopStandard();

                    loopStandard.setLoopCondition(null);

                    loopStandard.setTestTime(TestTimeType.BEFORE_LITERAL);

                    cmd.append(SetCommand.create(ed,
                            loop,
                            Xpdl2Package.eINSTANCE.getLoop_LoopStandard(),
                            loopStandard));

                }

            } else {
                Loop loop = act.getLoop();
                if (loop != null) {
                    LoopStandard loopStandard = loop.getLoopStandard();
                    if (loopStandard != null) {
                        cmd.append(SetCommand.create(ed,
                                loop,
                                Xpdl2Package.eINSTANCE.getLoop_LoopStandard(),
                                null));
                    }
                }
            }

            if (markers.contains(ActivityMarkerType.MARKER_MULTIPLE_LITERAL)) {
                Loop loop = act.getLoop();
                if (act.getLoop() == null) {
                    loop = Xpdl2Factory.eINSTANCE.createLoop();
                    loop.setLoopType(LoopType.MULTI_INSTANCE_LITERAL);
                    cmd.append(SetCommand.create(ed,
                            act,
                            Xpdl2Package.eINSTANCE.getActivity_Loop(),
                            loop));
                } else {
                    cmd.append(SetCommand.create(ed,
                            loop,
                            Xpdl2Package.eINSTANCE.getLoop_LoopType(),
                            LoopType.MULTI_INSTANCE_LITERAL));
                }
                LoopMultiInstance loopMulti = loop.getLoopMultiInstance();
                if (loopMulti == null) {
                    loopMulti =
                            Xpdl2Factory.eINSTANCE.createLoopMultiInstance();
                    //                loopMulti.setComplexMIFlowCondition(""); //$NON-NLS-1$
                    //                loopMulti.setMICondition(""); //$NON-NLS-1$
                    loopMulti.setComplexMIFlowCondition(null);
                    loopMulti.setMICondition(null);
                    loopMulti.setMIOrdering(MIOrderingType.PARALLEL_LITERAL);
                    loopMulti
                            .setMIFlowCondition(MIFlowConditionType.ALL_LITERAL);
                    if (loop.eContainer() == null) {
                        loop.setLoopMultiInstance(loopMulti);
                    } else {
                        cmd.append(SetCommand.create(ed,
                                loop,
                                Xpdl2Package.eINSTANCE
                                        .getLoop_LoopMultiInstance(),
                                loopMulti));
                    }
                    MultiInstanceScripts createMultiInstanceScripts =
                            XpdExtensionFactory.eINSTANCE
                                    .createMultiInstanceScripts();
                    Xpdl2ModelUtil.setOtherElement(loopMulti,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_MultiInstanceScripts(),
                            createMultiInstanceScripts);
                } else {
                    MultiInstanceScripts createMultiInstanceScripts =
                            XpdExtensionFactory.eINSTANCE
                                    .createMultiInstanceScripts();
                    cmd.append(Xpdl2ModelUtil.getSetOtherElementCommand(ed,
                            loopMulti,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_MultiInstanceScripts(),
                            createMultiInstanceScripts));
                }
            } else {
                Loop loop = act.getLoop();
                if (loop != null) {
                    LoopMultiInstance loopMultiInstance =
                            loop.getLoopMultiInstance();

                    if (loopMultiInstance != null) {
                        cmd.append(SetCommand.create(ed,
                                loop,
                                Xpdl2Package.eINSTANCE
                                        .getLoop_LoopMultiInstance(),
                                null));
                    }
                }
            }
        }

        if (markers.contains(ActivityMarkerType.MARKER_COMPENSATION_LITERAL)) {
            return UnexecutableCommand.INSTANCE;
        }

        return cmd;
    }

    public static Participant createParticipantFromOMEntity(EditingDomain ed,
            Activity act, EObject obj) {

        try {
            omCoreBundle = Platform.getBundle(REFLECTION_OM_CORE_PLUGIN_ID);
            if (omCoreBundle != null) {
                omUtilCls =
                        omCoreBundle.loadClass(REFLECTION_OM_CORE_OMUTIL_CLASS);
                namedElementCls =
                        omCoreBundle
                                .loadClass(REFLECTION_OM_CORE_NAMEDELEMENT_CLASS);
                omPackageCls =
                        omCoreBundle.loadClass(REFLECTION_OM_PACKAGE_CLASS);

                Class getIDParam = EObject.class;
                Class getObjByIDParam = String.class;

                Field field = omPackageCls.getField("eNS_URI"); //$NON-NLS-1$
                omNSURI = (String) field.get(field);

                getIDMeth =
                        omUtilCls.getMethod(REFLECTION_OM_GET_ID_METHOD,
                                getIDParam);
                getObjByIDMeth =
                        omUtilCls
                                .getMethod(REFLECTION_OM_GET_OBJECT_BY_ID_METHOD,
                                        getObjByIDParam);
                getDisplayNameMeth =
                        namedElementCls
                                .getMethod(REFLECTION_OM_GET_DISPLAY_NAME_METHOD,
                                        null);

                Participant participant =
                        Xpdl2Factory.eINSTANCE.createParticipant();

                ExternalReference externalReference =
                        Xpdl2Factory.eINSTANCE.createExternalReference();
                participant.setExternalReference(externalReference);

                String displayName = (String) getDisplayNameMeth.invoke(obj);
                String uniqueID = (String) getIDMeth.invoke(omUtilCls, obj);
                if (obj != null && obj.eResource() != null
                        && obj.eResource().getURI() != null) {
                    externalReference.setLocation(obj.eResource().getURI()
                            .lastSegment());
                }

                externalReference.setNamespace(omNSURI);
                externalReference.setXref(uniqueID);

                //
                // Suffix with index if there is already a participant with same
                // name.
                Set<String> particInScopeNames = new HashSet<String>();
                Set<String> particInScopeDisplayNames = new HashSet<String>();

                for (Participant p : act.getProcess().getParticipants()) {
                    particInScopeNames.add(p.getName());
                    particInScopeDisplayNames.add(Xpdl2ModelUtil
                            .getDisplayName(p));
                }
                for (Participant p : act.getProcess().getPackage()
                        .getParticipants()) {
                    particInScopeNames.add(p.getName());
                    particInScopeDisplayNames.add(Xpdl2ModelUtil
                            .getDisplayName(p));
                }

                String particName = displayName;

                int nameIndex = 1;
                while (true) {
                    if (!particInScopeNames.contains(particName)
                            && !particInScopeDisplayNames.contains(particName)) {
                        break;
                    }
                    nameIndex++;
                    particName = displayName + nameIndex;
                }

                Xpdl2ModelUtil.setOtherAttribute(participant,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_DisplayName(),
                        particName);
                participant.setName(particName);

                return participant;
            }
        } catch (Exception e) {
        }

        return null;
    }

    public static Command getSetPerformersCommand(EditingDomain ed,
            Activity act, EObject[] newPerformers) {
        // Take into account that we used to store single participant in
        // activity performer. Now we use activity performers.
        // An old defined task will have single a new one will use exclusively
        // performers.
        CompoundCommand cmd = new CompoundCommand();

        // Delete the current performer(s)

        EList oldPerformers = act.getPerformerList();
        if (oldPerformers != null && oldPerformers.size() > 0) {
            cmd.append(RemoveCommand.create(ed, oldPerformers));

            // Remove empty performers sequence if there is one
            Performers perfsSeqParent = act.getPerformers();
            if (perfsSeqParent != null) {
                cmd.append(RemoveCommand.create(ed, perfsSeqParent));
            }

        }

        // And add the new ones.
        if (newPerformers != null && newPerformers.length > 0) {
            cmd.setLabel(Messages.Xpdl2TaskAdapter_SetPerformerTask_menu);

            Performers perfsSeqParent =
                    Xpdl2Factory.eINSTANCE.createPerformers();
            cmd.append(SetCommand.create(ed,
                    act,
                    Xpdl2Package.eINSTANCE.getActivity_Performers(),
                    perfsSeqParent));

            for (int i = 0; i < newPerformers.length; i++) {

                if (newPerformers[i] instanceof Participant) {
                    Participant p = (Participant) newPerformers[i];

                    Performer newPerf =
                            Xpdl2Factory.eINSTANCE.createPerformer();
                    newPerf.setValue(p.getId());

                    perfsSeqParent.getPerformers().add(newPerf);

                } else if (newPerformers[i] instanceof ProcessRelevantData) {
                    ProcessRelevantData data =
                            (ProcessRelevantData) newPerformers[i];

                    Performer newPerf =
                            Xpdl2Factory.eINSTANCE.createPerformer();
                    newPerf.setValue(data.getId());

                    perfsSeqParent.getPerformers().add(newPerf);
                }
            }
        } else {
            cmd.setLabel(Messages.Xpdl2TaskAdapter_UnsetPerformerTask_menu);

        }

        if (cmd.getCommandList().size() == 0) {
            cmd = null;
        }
        return cmd;
    }

    public static Command getSetSubprocessCommand(EditingDomain ed,
            Activity act, EObject subProcOrInterface) {

        CompoundCommand cmd = new CompoundCommand();
        cmd.setLabel(Messages.Xpdl2TaskAdapter_SetSubflow);

        if (act.getRoute() != null) {
            cmd.append(SetCommand.create(ed,
                    act,
                    Xpdl2Package.eINSTANCE.getActivity_Route(),
                    null));
        }
        if (act.getStartMode() != null) {
            cmd.append(SetCommand.create(ed,
                    act,
                    Xpdl2Package.eINSTANCE.getActivity_StartMode(),
                    null));
        }
        if (act.getFinishMode() != null) {
            cmd.append(SetCommand.create(ed,
                    act,
                    Xpdl2Package.eINSTANCE.getActivity_FinishMode(),
                    null));
        }
        EStructuralFeature initial =
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_InitialParameterValue();
        List<?> others = Xpdl2ModelUtil.getOtherElementList(act, initial);
        for (Object other : others) {
            cmd.append(Xpdl2ModelUtil.getRemoveOtherElementCommand(ed,
                    act,
                    initial,
                    other));
        }

        Xpdl2Factory f = Xpdl2Factory.eINSTANCE;
        SubFlow sf = f.createSubFlow();

        if (act.getImplementation() instanceof SubFlow) {
            EList aps = null;
            aps = ((SubFlow) act.getImplementation()).getActualParameters();
            // TODO WZ copy actual parameters when change from Task into Subflow
            // } else if (act.getImplementation().getTool().size() == 1) {
            // aps = ((Tool) act.getImplementation().getTool().get(0))
            // .getActualParameters();
            if (aps != null) {
                for (Iterator iter = aps.iterator(); iter.hasNext();) {
                    String str = (String) iter.next();
                    Expression expression =
                            Xpdl2Factory.eINSTANCE.createExpression();
                    expression
                            .getMixed()
                            .add(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                                    str);
                    sf.getActualParameters().add(expression);
                }
            }
            if (subProcOrInterface instanceof Process) {
                cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                        (SubFlow) act.getImplementation(),
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ProcessIdentifierField(),
                        null));
            }
        }

        if (subProcOrInterface != null) {
            UniqueIdElement proc = (UniqueIdElement) subProcOrInterface;

            String procId = proc.getId();
            if (procId == null) {
                return null;
            }

            WorkingCopy externalWc = WorkingCopyUtil.getWorkingCopyFor(proc);
            Xpdl2WorkingCopyImpl wc =
                    (Xpdl2WorkingCopyImpl) WorkingCopyUtil
                            .getWorkingCopyFor(act);
            if (wc == externalWc) {
                sf.setProcessId(procId);
            } else {
                String refId = wc.appendCreateReferenceCommand(externalWc, cmd);
                sf.setProcessId(procId);
                sf.setPackageRefId(refId);
            }
        } else {
            sf.setProcessId("none"); //$NON-NLS-1$
        }

        cmd.append(SetCommand.create(ed,
                act,
                Xpdl2Package.eINSTANCE.getActivity_Implementation(),
                sf));

        return cmd;
    }

    public static Command getSetSubProcessIsTransactionalCommand(
            EditingDomain ed, Activity act, boolean transactional) {
        CompoundCommand cmd = new CompoundCommand();
        cmd.append(SetCommand.create(ed,
                act,
                Xpdl2Package.eINSTANCE.getActivity_IsATransaction(),
                new Boolean(transactional)));
        if (!transactional) {
            cmd.append(getSetSubProcessIsRequireNewCommand(ed, act, false));
        }
        return cmd;

    }

    /**
     * @param ed
     * @param act
     * @param selection
     * @return
     */
    public static Command getSetSubProcessIsChainedCommand(EditingDomain ed,
            Activity act, boolean selection) {
        return Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                act,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_IsChained(),
                new Boolean(selection));
    }

    /**
     * @param ed
     * @param act
     * @param selection
     * @return
     */
    public static Command getSetSubProcessIsRequireNewCommand(EditingDomain ed,
            Activity act, boolean selection) {
        return Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                act,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_RequireNewTransaction(),
                new Boolean(selection));
    }

    /**
     * Get the xpdl2 Process <b>OR</b> xpdExtension ProcessInterface that is
     * referenced by the given independent sub-process task activity.
     * 
     * @param act
     * @return xpdl2:Process or xpdExt:ProcessInterface referenced by task OR
     *         <b>null</b> if the activity is not a sub-process task or there is
     *         no sub-process or process interface currently referenced.
     */
    public static EObject getSubProcessOrInterface(Activity act) {
        Xpdl2WorkingCopyImpl wc =
                (Xpdl2WorkingCopyImpl) WorkingCopyUtil.getWorkingCopyFor(act);
        return getSubProcessOrInterface(act, wc);
    }

    /**
     * Get the Activity that is referenced by the given send task's business
     * process
     * 
     * @param sendTask
     * @return refActivity or <b>null</b> if the activity couldn't be found.
     */
    public static Activity getInvokeBusinessProcessReferencedActivity(
            Activity sendTask) {

        BusinessProcess businessProcess =
                WebServiceOperationUtil.getBusinessProcess(sendTask);

        if (businessProcess != null
                && !businessProcess.getProcessId().isEmpty()) {

            Process refProcess = getInvokeBusinessProcess(sendTask);

            if (refProcess != null && businessProcess.getActivityId() != null) {

                Activity refActivity =
                        Xpdl2ModelUtil.getActivityById(refProcess,
                                businessProcess.getActivityId());
                return refActivity;
            }
        }
        return null;
    }

    /**
     * Get the given send task's business process.
     * 
     * @param sendTask
     * @return refProcess or <b>null</b> if the process couldn't be found.
     */
    public static Process getInvokeBusinessProcess(Activity sendTask) {

        BusinessProcess businessProcess =
                WebServiceOperationUtil.getBusinessProcess(sendTask);

        if (businessProcess != null
                && !businessProcess.getProcessId().isEmpty()) {

            Process refProcess =
                    ProcessUIUtil.getProcesById(businessProcess.getProcessId());

            if (refProcess != null) {
                return refProcess;
            }
        }

        return null;
    }

    /**
     * Get the xpdl2 Process <b>OR</b> xpdExtension ProcessInterface that is
     * referenced by the given independent sub-process task activity.
     * 
     * @param act
     * @param workingCopy
     *            Sometimes we may be working on activities that have NOT YET
     *            BEEN SAVED and therefore do not have a working copy
     *            themselves. In this case the caller can pass in a working copy
     *            that it does know about.
     * @return
     */
    public static EObject getSubProcessOrInterface(Activity act,
            Xpdl2WorkingCopyImpl workingCopy) {
        return ProcessDataUtil.getSubProcessOrInterface(act, workingCopy);
    }

    public static String getSubprocessDescription(Activity act) {
        EObject sf = getSubProcessOrInterface(act);
        if (sf == null) {
            return null;
        }
        return WorkingCopyUtil.getText(sf);
    }

    public static boolean getSubprocessIsTransactional(Activity act) {
        return act.isIsATransaction();
    }

    /**
     * @param act
     * @return
     */
    public static boolean getSubprocessIsChained(Activity act) {
        boolean isChained = false;
        Object other =
                Xpdl2ModelUtil.getOtherAttribute(act,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_IsChained());
        if (other instanceof Boolean) {
            isChained = ((Boolean) other).booleanValue();
        }
        return isChained;
    }

    /**
     * @param act
     * @return
     */
    public static boolean getSubprocessIsRequireNew(Activity act) {
        boolean isRequireNew = false;
        Object other =
                Xpdl2ModelUtil.getOtherAttribute(act,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_RequireNewTransaction());
        if (other instanceof Boolean) {
            isRequireNew = ((Boolean) other).booleanValue();
        }
        return isRequireNew;
    }

    public static String getSubprocessLocationDescription(Activity act) {
        EObject sf = getSubProcessOrInterface(act);
        if (sf == null) {
            return null;
        }
        WorkingCopy external = WorkingCopyUtil.getWorkingCopyFor(sf);
        WorkingCopy local = WorkingCopyUtil.getWorkingCopyFor(act);

        if (external == local) {
            return null;
        }
        // return external.getEclipseResource().getName();
        return external.getEclipseResources().get(0).getProjectRelativePath()
                .toString();
    }

    public static String getTaskImplementationExtensionId(Activity act) {
        Implementation implementation = act.getImplementation();

        if (implementation instanceof Task) {
            TaskService service = ((Task) implementation).getTaskService();

            if (service != null) {
                // Get the currently set task implementation extension name
                return (String) Xpdl2ModelUtil.getOtherAttribute(service,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ImplementationType());
            }

            TaskSend send = ((Task) implementation).getTaskSend();

            if (send != null) {
                // Get the currently set task implementation extension name
                return (String) Xpdl2ModelUtil.getOtherAttribute(send,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ImplementationType());
            }

            TaskReceive receive = ((Task) implementation).getTaskReceive();

            if (receive != null) {
                // Get the currently set task implementation extension name
                return (String) Xpdl2ModelUtil.getOtherAttribute(receive,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ImplementationType());
            }

        }
        return null;
    }

    /**
     * Get the task type of the given activity, returning TaskType.NONE_LITERAL
     * if it is not a valid task type.
     * 
     * @param act
     * @return the task type of the given activity or TaskType.NONE_LITERAL if
     *         it is not a valid task type.
     * @deprecated ,use getTaskTypeStrict() instead, as this method returns
     *             TaskType.None_Literal for activity which is not a Task, where
     *             as getTaskTypStrict() returns null for non-task activities.
     */
    @Deprecated
    public static TaskType getTaskType(Activity act) {
        TaskType tt = getTaskTypeStrict(act);
        if (tt == null) {
            tt = TaskType.NONE_LITERAL;
        }
        return tt;
    }

    /**
     * Get the task type of the given activity, returning null if it is not a
     * valid task type.
     * 
     * @param activity
     * @return the task type of the given activity or null if it is not a valid
     *         task type.
     */
    public static TaskType getTaskTypeStrict(Activity activity) {
        TaskType tt = null;
        if (activity != null) {
            Implementation impl = activity.getImplementation();
            if (impl instanceof Task) {
                Task t = (Task) impl;
                if (t.getTaskManual() != null) {
                    tt = TaskType.MANUAL_LITERAL;
                } else if (t.getTaskReceive() != null) {
                    tt = TaskType.RECEIVE_LITERAL;
                } else if (t.getTaskScript() != null) {
                    tt = TaskType.SCRIPT_LITERAL;
                } else if (t.getTaskSend() != null) {
                    tt = TaskType.SEND_LITERAL;
                } else if (t.getTaskService() != null) {
                    if (DecisionFlowUtil.isDecisionTableTask(activity)) {
                        tt = TaskType.DTABLE_LITERAL;
                    } else {
                        tt = TaskType.SERVICE_LITERAL;
                    }
                } else if (t.getTaskUser() != null) {
                    tt = TaskType.USER_LITERAL;
                }
            } else if (impl instanceof SubFlow) {
                tt = TaskType.SUBPROCESS_LITERAL;
            } else if (activity.getBlockActivity() != null) {

                /*
                 * ABPM-911: Saket: Can't predict the type of an embedded
                 * subprocess just on the basis of block activity now as we can
                 * have a block activity for event subprocesses as well.
                 */

                if (Xpdl2ModelUtil.isEventSubProcess(activity)) {

                    tt = TaskType.EVENT_SUBPROCESS_LITERAL;

                } else {

                    tt = TaskType.EMBEDDED_SUBPROCESS_LITERAL;
                }
            } else if (impl instanceof Reference) {
                tt = TaskType.REFERENCE_LITERAL;
            } else if (impl instanceof No) {
                tt = TaskType.NONE_LITERAL;
            }
        }
        return tt;
    }

    /**
     * Return command to set xpdExt:IsEventSubProcess attribute.
     * 
     * @param ed
     * @param act
     * @param selection
     * @return command to set xpdExt:IsEventSubProcess attribute.
     */
    public static Command getSetEventSubProcessCommand(EditingDomain ed,
            Activity act, boolean selection) {

        return Xpdl2ModelUtil.getSetOtherAttributeCommand(ed, act
                .getBlockActivity(), XpdExtensionPackage.eINSTANCE
                .getDocumentRoot_IsEventSubProcess(), new Boolean(selection));
    }

    /**
     * @param act
     * 
     * @return Xpdl Implementation type name for task (if it has one) or null if
     *         it doesn't.
     */
    public static String getTaskTypeImplementationName(Activity act) {
        ImplementationType taskImplementationType =
                getTaskImplementationType(act);

        if (taskImplementationType != null) {
            return taskImplementationType.getName();
        }
        return null;
    }

    /**
     * @param act
     * 
     * @return Xpdl Implementation type for task (if it has one) or null if it
     *         doesn't.
     */
    public static ImplementationType getTaskImplementationType(Activity act) {
        Implementation impl = act.getImplementation();

        if (impl instanceof Task) {
            Task task = (Task) impl;

            TaskType taskType = getTaskType(act);

            if (TaskType.MANUAL_LITERAL.equals(taskType)) {
                return null;

            } else if (TaskType.USER_LITERAL.equals(taskType)) {
                return task.getTaskUser().getImplementation();

            } else if (TaskType.SERVICE_LITERAL.equals(taskType)) {
                return task.getTaskService().getImplementation();

            } else if (TaskType.SEND_LITERAL.equals(taskType)) {
                return task.getTaskSend().getImplementation();

            } else if (TaskType.RECEIVE_LITERAL.equals(taskType)) {
                return task.getTaskReceive().getImplementation();

            } else if (TaskType.SCRIPT_LITERAL.equals(taskType)) {
                return null;

            } else if (TaskType.REFERENCE_LITERAL.equals(taskType)) {
                return null;

            }
        }
        return null;
    }

    /**
     * Get the USerTaskScripts extension element IF this is a user task AND the
     * scripts element exist.
     * 
     * @return existing UserTaskScripts element or null if none defined or no a
     *         user task.
     */
    public static UserTaskScripts getUserTaskScripts(Activity activity) {
        UserTaskScripts userTaskScripts = null;

        if (activity != null) {
            if (activity.getImplementation() instanceof Task) {
                Task task = (Task) activity.getImplementation();

                TaskUser taskUser = task.getTaskUser();
                if (taskUser != null) {
                    // Get or create the user task scripts element under
                    // TaskUser
                    userTaskScripts =
                            (UserTaskScripts) Xpdl2ModelUtil
                                    .getOtherElement(taskUser,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_UserTaskScripts());
                }
            }
        }
        return userTaskScripts;

    }

    /**
     * Get the Audit extension element IF the element exist.
     * 
     * @return existing Audit element or null if none defined.
     */
    public static Audit getAudit(Activity activity) {
        Audit audit = null;

        if (activity != null) {
            audit =
                    (Audit) Xpdl2ModelUtil.getOtherElement(activity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_Audit());
        }
        return audit;
    }

    public static ActivitySet getActivitySet(Activity act) {
        if (act.getBlockActivity() != null && act.getProcess() != null) {
            return act.getProcess().getActivitySet(act.getBlockActivity()
                    .getActivitySetId());
        }
        return null;
    }

    public static CompoundCommand getChangeTaskReceiveImpl(EditingDomain ed,
            String extImplementationType, String implementationTypeName,
            Task task) {
        CompoundCommand cmd;
        cmd = new CompoundCommand();
        cmd.setLabel(Messages.Xpdl2TaskAdapter_ChangeServiceTypeTask_menu);

        // Create the new service task
        TaskReceive newReceive = Xpdl2Factory.eINSTANCE.createTaskReceive();

        // Set implementation
        ImplementationType implementationType =
                ImplementationType.getByName(implementationTypeName);
        if (implementationType == null) {
            implementationType = ImplementationType.UNSPECIFIED_LITERAL;
        }
        newReceive.setImplementation(implementationType);

        // Set the message in and out
        newReceive.setMessage(Xpdl2Factory.eINSTANCE.createMessage());

        // set the new exrtended task send implementation type id
        Xpdl2ModelUtil.setOtherAttribute(newReceive,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_ImplementationType(),
                extImplementationType);

        if (newReceive != null) {
            // Add the set command to create new service task
            cmd.append(SetCommand.create(ed,
                    task,
                    Xpdl2Package.eINSTANCE.getTask_TaskReceive(),
                    newReceive));
        }
        return cmd;
    }

    public static CompoundCommand getChangeTaskSendImp(EditingDomain ed,
            String extImplementationType, String implementationTypeName,
            Task task) {
        CompoundCommand cmd;
        cmd = new CompoundCommand();
        cmd.setLabel(Messages.Xpdl2TaskAdapter_ChangeServiceTypeTask_menu);

        // Create the new service task
        TaskSend newSend = Xpdl2Factory.eINSTANCE.createTaskSend();

        // Set implementation
        ImplementationType implementationType =
                ImplementationType.getByName(implementationTypeName);
        if (implementationType == null) {
            implementationType = ImplementationType.UNSPECIFIED_LITERAL;
        }
        newSend.setImplementation(implementationType);

        // Set the message in and out
        newSend.setMessage(Xpdl2Factory.eINSTANCE.createMessage());

        // set the new extended task send implementation type id
        Xpdl2ModelUtil.setOtherAttribute(newSend, XpdExtensionPackage.eINSTANCE
                .getDocumentRoot_ImplementationType(), extImplementationType);

        if (newSend != null) {
            // Add the set command to create new service task
            cmd.append(SetCommand.create(ed,
                    task,
                    Xpdl2Package.eINSTANCE.getTask_TaskSend(),
                    newSend));
        }
        return cmd;
    }

    /**
     * @param ed
     * @param extImplementationType
     * @param implementationTypeName
     * @param task
     * @return
     */
    public static CompoundCommand getChangeTaskServiceImpl(EditingDomain ed,
            String extImplementationType, String implementationTypeName,
            Task task) {
        CompoundCommand cmd;
        cmd = new CompoundCommand();
        cmd.setLabel(Messages.Xpdl2TaskAdapter_ChangeServiceTypeTask_menu);

        // Create the new service task
        TaskService newService = Xpdl2Factory.eINSTANCE.createTaskService();

        // Set implementation
        ImplementationType implementationType =
                ImplementationType.getByName(implementationTypeName);
        if (implementationType == null) {
            implementationType = ImplementationType.UNSPECIFIED_LITERAL;
        }
        newService.setImplementation(implementationType);

        // Set the message in and out
        newService.setMessageIn(Xpdl2Factory.eINSTANCE.createMessage());
        newService.setMessageOut(Xpdl2Factory.eINSTANCE.createMessage());

        // set the new service name
        Xpdl2ModelUtil.setOtherAttribute(newService,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_ImplementationType(),
                extImplementationType);

        if (newService != null) {
            // Add the set command to create new service task
            cmd.append(SetCommand.create(ed,
                    task,
                    Xpdl2Package.eINSTANCE.getTask_TaskService(),
                    newService));
        }

        return cmd;
    }

    /**
     * Get the current script grammar for the open/close/submit script as
     * indicated by scriptFeature.
     * 
     * @param scriptFeature
     * @return
     */
    public static String getExistingUserTaskScriptGrammarId(
            EStructuralFeature scriptFeature, Activity act) {
        String scriptGrammar = null;

        UserTaskScripts userTaskScripts = getUserTaskScripts(act);

        if (userTaskScripts != null) {
            Expression script = null;

            script = (Expression) userTaskScripts.eGet(scriptFeature);
            if (script == null) {
                return null;
            }
            scriptGrammar = script.getScriptGrammar();
        }
        return scriptGrammar;
    }

    /**
     * Get the current script grammar for the initiated/complete script as
     * indicated by scriptFeature.
     * 
     * @param scriptFeature
     * @return
     */
    public static String getExistingAuditScriptGrammarId(
            AuditEventType auditEventType, Activity act) {
        String scriptGrammar = null;
        if (act != null) {
            Audit audit = getAudit(act);
            if (audit != null) {
                Expression script =
                        TaskObjectUtil.getAuditScriptExpression(auditEventType,
                                audit);
                if (script != null) {
                    scriptGrammar = script.getScriptGrammar();
                }
            }
        }
        return scriptGrammar;
    }

    /**
     * Get the current script expression for the audit script as indicated by
     * the auditEventType.
     * 
     * @param scriptFeature
     * @return
     */
    public static Expression getAuditScriptExpression(
            AuditEventType auditEventType, Audit audit) {
        Expression script = null;

        if (audit != null) {
            AuditEvent auditEvent =
                    TaskObjectUtil.getAuditEvent(auditEventType, audit);
            if (auditEvent != null) {
                script = auditEvent.getInformation();
            }
        }
        return script;
    }

    /**
     * Get the current Audit Event for the audit script as indicated by the
     * auditEventType.
     * 
     * @param scriptFeature
     * @return
     */
    public static AuditEvent getAuditEvent(AuditEventType auditEventType,
            Audit audit) {
        AuditEvent event = null;

        if (audit != null) {
            List<AuditEvent> auditEvents = audit.getAuditEvent();
            if (auditEvents != null) {
                for (AuditEvent auditEvent : auditEvents) {
                    if (auditEvent.getType() != null
                            && auditEvent.getType().equals(auditEventType)) {
                        event = auditEvent;
                        break;
                    }
                }
            }
        }
        return event;
    }

    /*
     * Get the Intermediate events that attached to given task activity's
     * boundary.
     */
    public static Collection<Activity> getAttachedEvents(Activity act) {
        return Xpdl2ModelUtil.getAttachedEvents(act);
    }

    public static String getExistingSetMILoopExpressionScriptGrammarId(
            Activity act) {
        LoopMultiInstance loopMultiInstance =
                ProcessScriptUtil.getLoopMultiInstance(act);
        if (loopMultiInstance != null) {
            Expression expr = loopMultiInstance.getMICondition();
            if (expr != null) {
                return expr.getScriptGrammar();
            }
        }

        return null;
    }

    public static String getExistingSetComplexExitScriptGrammarId(Activity act) {
        LoopMultiInstance loopMultiInstance =
                ProcessScriptUtil.getLoopMultiInstance(act);
        if (loopMultiInstance != null) {
            Expression expr = loopMultiInstance.getComplexMIFlowCondition();
            if (expr != null) {
                return expr.getScriptGrammar();
            }
        }

        return null;
    }

    public static String getExistingSetAdditionalInstancesMIScriptGrammarId(
            Activity act) {
        String scriptGrammar = null;
        MultiInstanceScripts miScripts =
                ProcessScriptUtil.getMILoopScripts(act);

        if (miScripts != null) {
            Expression script = null;
            script = miScripts.getAdditionalInstances();
            if (script == null) {
                return null;
            }
            scriptGrammar = script.getScriptGrammar();
        }
        return scriptGrammar;
    }

    /**
     * Get the current script grammar for the loop Expression/Complex
     * Exit/Additional Instances script as indicated by scriptFeature.
     * 
     * @param scriptFeature
     * @return
     */
    public static String getExistingSetStdLoopScriptGrammarId(Activity activity) {
        String scriptGrammar = null;
        LoopStandard loopStandard = ProcessScriptUtil.getLoopStandard(activity);
        if (loopStandard != null) {
            Expression loopExpression = loopStandard.getLoopCondition();
            if (loopExpression != null) {
                scriptGrammar = loopExpression.getScriptGrammar();
            }
        }
        return scriptGrammar;
    }

    public static MIFlowConditionType getMIFlowCondition(Activity activity) {
        LoopMultiInstance loopMultiInstance = getLoopMultiInstance(activity);
        if (loopMultiInstance == null) {
            return null;
        }
        MIFlowConditionType flowCondition =
                loopMultiInstance.getMIFlowCondition();
        return flowCondition;
    }

    public static MIOrderingType getMIOrdering(Activity activity) {
        LoopMultiInstance loopMultiInstance = getLoopMultiInstance(activity);
        if (loopMultiInstance == null) {
            return null;
        }
        MIOrderingType ordering = loopMultiInstance.getMIOrdering();
        return ordering;
    }

    public static LoopMultiInstance getLoopMultiInstance(Activity activity) {
        Loop loop = activity.getLoop();
        if (loop == null) {
            return null;
        }
        LoopMultiInstance loopMultiInstance = loop.getLoopMultiInstance();
        return loopMultiInstance;
    }

    public static LoopStandard getStandardLoop(Activity activity) {
        Loop loop = activity.getLoop();
        if (loop == null) {
            return null;
        }
        LoopStandard loopStandard = loop.getLoopStandard();
        return loopStandard;
    }

    public static TestTimeType getStdTestTime(Activity activity) {
        LoopStandard loopStandard = getStandardLoop(activity);
        if (loopStandard != null) {
            return loopStandard.getTestTime();
        }
        return null;
    }

    public static BigInteger getStdLoopMaximum(Activity activity) {
        LoopStandard loopStandard = getStandardLoop(activity);
        if (loopStandard != null) {
            return loopStandard.getLoopMaximum();
        }
        return new BigInteger("0"); //$NON-NLS-1$ 
    }

    /**
     * Get command to set the task type AND if appropriate reset the task name /
     * colours etc to default for new type.
     * 
     * @param ed
     * @param act
     * @param newTaskType
     * 
     * @return command
     * @deprecated Use
     *             {@link #getSetTaskTypeCommandEx(EditingDomain,Activity,TaskType,Process,boolean,boolean,boolean)}
     *             instead
     */
    @Deprecated
    public static Command getSetTaskTypeCommandEx(EditingDomain ed,
            Activity act, TaskType newTaskType, Process parentProcess,
            boolean setNameIfDefault, boolean setColoursIfDefault) {
        return getSetTaskTypeCommandEx(ed,
                act,
                newTaskType,
                parentProcess,
                setNameIfDefault,
                setColoursIfDefault,
                setColoursIfDefault);
    }

    /**
     * Get command to set the task type AND if appropriate reset the task name /
     * colours etc to default for new type.
     * 
     * @param ed
     * @param act
     * @param newTaskType
     * @param setIconIfDefault
     * @return command
     */
    public static Command getSetTaskTypeCommandEx(EditingDomain ed,
            Activity act, TaskType newTaskType, Process parentProcess,
            boolean setNameIfDefault, boolean setColoursIfDefault,
            boolean setIconIfDefault) {
        TaskType currTaskType = getTaskType(act);

        CompoundCommand cmd =
                new CompoundCommand(Messages.Xpdl2TaskAdapter_SetTypeTask_menu);

        if (!currTaskType.equals(newTaskType)) {
            cmd.append(getSetTaskTypeCommand(ed, act, newTaskType));
        }

        if (ReplyActivityUtil.isReplyActivity(act)
                && Xpdl2ModelUtil
                        .getDisplayName(act)
                        .startsWith(ReplyActivityUtil.REPLY_ACTIVITY_UTIL_REPLY_TO_LABEL)) {
            cmd.append(Xpdl2ModelUtil
                    .getSetOtherAttributeCommand(ed,
                            act,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName(),
                            newTaskType.toString()));
        } else {
            //
            // If current name is default name for current type then change task
            // name to match new type.
            // Create a command that resets the activity name to given name when
            // executed (ensuring it's unique).
            if (setNameIfDefault) {
                String curName = Xpdl2ModelUtil.getDisplayNameOrName(act);
                if (curName.startsWith(currTaskType.toString())
                        || (TaskType.REFERENCE_LITERAL.equals(currTaskType) && ReferenceTaskUtil
                                .isDefaultTaskName(curName))) {
                    cmd.append(new SetUniqueTaskNameCommand(ed, parentProcess,
                            act, newTaskType.toString()));
                }
            }
        }

        //
        // If current colours are default for current type then swap to default
        // for new type.
        if (setColoursIfDefault) {
            NodeGraphicsInfo ngi = Xpdl2ModelUtil.getNodeGraphicsInfo(act);
            if (ngi != null) {
                ProcessWidgetType procType = getProcessType(parentProcess);

                String defaultFillColorForType =
                        ProcessWidgetColors
                                .getInstance(procType)
                                .getGraphicalNodeColor(null,
                                        currTaskType.getGetDefaultFillColorId())
                                .toString();

                String defaultLineColorForType =
                        ProcessWidgetColors
                                .getInstance(procType)
                                .getGraphicalNodeColor(null,
                                        currTaskType.getGetDefaultLineColorId())
                                .toString();

                if (defaultFillColorForType.equals(ngi.getFillColor())
                        && defaultLineColorForType.equals(ngi.getBorderColor())) {
                    String newFillColorForType =
                            ProcessWidgetColors
                                    .getInstance(procType)
                                    .getGraphicalNodeColor(null,
                                            newTaskType
                                                    .getGetDefaultFillColorId())
                                    .toString();

                    String newLineColorForType =
                            ProcessWidgetColors
                                    .getInstance(procType)
                                    .getGraphicalNodeColor(null,
                                            newTaskType
                                                    .getGetDefaultLineColorId())
                                    .toString();

                    cmd.append(SetCommand.create(ed,
                            ngi,
                            Xpdl2Package.eINSTANCE
                                    .getNodeGraphicsInfo_FillColor(),
                            newFillColorForType));
                    cmd.append(SetCommand.create(ed,
                            ngi,
                            Xpdl2Package.eINSTANCE
                                    .getNodeGraphicsInfo_BorderColor(),
                            newLineColorForType));
                }
            }
        }

        if (setIconIfDefault) {
            if (isTaskUsingDefaultIconForType(act)) {
                /*
                 * Current set/unset icon is same as default so can reset to
                 * default for nest task type.
                 * 
                 * (But not til aftet the rest of command has changed task type.
                 */
                cmd.append(new LateExecSetDefTaskIconCommand(ed, act));
            }

        }

        return cmd;
    }

    public static boolean isTaskUsingDefaultIconForType(Activity act) {
        String defIcon = getDefaultIconForTaskType(act);
        String taskIcon = null;

        Icon icon = act.getIcon();
        if (icon != null) {
            taskIcon = icon.getValue();
        }

        if ((defIcon == null && (taskIcon == null || taskIcon.length() == 0))
                || (defIcon != null && defIcon.equals(taskIcon))) {
            return true;
        }

        return false;
    }

    public static Command getSetTaskTypeCommand(EditingDomain ed, Activity act,
            TaskType newTaskType) {

        TaskType currTaskType = getTaskType(act);

        if (currTaskType.equals(newTaskType)) {
            return UnexecutableCommand.INSTANCE;
        }

        CompoundCommand cmd = new CompoundCommand();
        cmd.setLabel(Messages.Xpdl2TaskAdapter_SetTypeTask_menu);

        if (act.getRoute() != null) {
            cmd.append(SetCommand.create(ed,
                    act,
                    Xpdl2Package.eINSTANCE.getActivity_Route(),
                    null));
        }

        /*
         * If the previous task [User Task or Re-usable Sub proc] Had Ad-Hoc
         * Configuration, then unset it.
         */
        Object adhocConfiguration =
                Xpdl2ModelUtil.getOtherElement(act,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_AdHocTaskConfiguration());

        if (adhocConfiguration != null) {
            /*
             * Unset the Ad-Hoc configuration if it was present on the previous
             * task.
             */
            cmd.append(SetCommand.create(ed,
                    act,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_AdHocTaskConfiguration(),
                    SetCommand.UNSET_VALUE));
        }

        if (TaskType.EMBEDDED_SUBPROCESS_LITERAL.equals(newTaskType)
                || TaskType.EVENT_SUBPROCESS_LITERAL.equals(newTaskType)) {
            /*
             * If the current and new task types are amongst Embedded and event
             * sub processes, then just set the xpdExt:isEventSubProcess
             * attribute accordingly and DON'T DELETE anything.
             */
            if (TaskType.EMBEDDED_SUBPROCESS_LITERAL.equals(currTaskType)
                    || TaskType.EVENT_SUBPROCESS_LITERAL.equals(currTaskType)) {

                if (TaskType.EVENT_SUBPROCESS_LITERAL.equals(newTaskType)) {

                    /*
                     * If new task type is event subprocess, set
                     * isEventSubProcess to true.
                     */
                    cmd.append(getSetEventSubProcessCommand(ed, act, true));

                } else {

                    /*
                     * If new task type is embedded subprocess, set
                     * isEventSubProcess to false.
                     */
                    cmd.append(getSetEventSubProcessCommand(ed, act, false));
                }
            } else {

                // If the new type is embedded sub-process or event sub-process
                // Then delete the implementation node that's there.
                Implementation impl = act.getImplementation();

                // Delete the current implementation node.
                // (This will actually get rid of Task, Subflow or Reference.
                if (impl != null) {
                    cmd.append(SetCommand
                            .create(ed, act, Xpdl2Package.eINSTANCE
                                    .getActivity_Implementation(), null));
                }

                // Create an activity set for the embedded/event subproc
                ActivitySet actSet = Xpdl2Factory.eINSTANCE.createActivitySet();

                BlockActivity newBlock =
                        Xpdl2Factory.eINSTANCE.createBlockActivity();
                newBlock.setActivitySetId(actSet.getId());
                newBlock.setView(ViewType.EXPANDED);

                cmd.append(AddCommand.create(ed,
                        act.getProcess(),
                        Xpdl2Package.eINSTANCE.getProcess_ActivitySets(),
                        actSet));

                cmd.append(SetCommand.create(ed,
                        act,
                        Xpdl2Package.eINSTANCE.getActivity_BlockActivity(),
                        newBlock));

                cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                        newBlock,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_IsEventSubProcess(),
                        TaskType.EVENT_SUBPROCESS_LITERAL.equals(newTaskType)));

                // cmd.append(getSetEventSubProcessCommand(ed,
                // act,
                // TaskType.EVENT_SUBPROCESS_LITERAL.equals(newTaskType)));

            }

        } else {
            // Switching to Task or Independent sub-proc...
            // both of these are controlled by Implementation element.
            if (TaskType.EMBEDDED_SUBPROCESS_LITERAL.equals(currTaskType)
                    || TaskType.EVENT_SUBPROCESS_LITERAL.equals(currTaskType)) {
                // If current task type is embedded or event subprocess then
                // delete the ancilliary
                // stuff.
                addCommandToRemoveEventOrEmbeddedSubProcStuff(ed, act, cmd);

            }

            // If old task type was a sub-proc then remove any InitialValue
            // settings.
            if (TaskType.SUBPROCESS_LITERAL.equals(currTaskType)) {
                EStructuralFeature initial =
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_InitialParameterValue();
                List<?> others =
                        Xpdl2ModelUtil.getOtherElementList(act, initial);
                for (Object other : others) {
                    cmd.append(Xpdl2ModelUtil.getRemoveOtherElementCommand(ed,
                            act,
                            initial,
                            other));
                }
            }

            // Remove any associated correlation data
            EStructuralFeature correlation =
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_AssociatedCorrelationFields();
            Object other = Xpdl2ModelUtil.getOtherElement(act, correlation);
            if (other != null) {
                cmd.append(Xpdl2ModelUtil.getRemoveOtherElementCommand(ed,
                        act,
                        correlation,
                        other));
            }

            /*
             * XPD-5176: Saket- Since now we show local data fields on UI for
             * every task type, we should not delete the local data fields when
             * user changes a task's type, given the fact that we show up an
             * error marker if a particular task type doesn't support local data
             * field(s).
             */
            // Removed activity datafields deletion

            // Ok, that's all the old stuff removed. Create the new stuff.
            Implementation newImpl = null;

            if (TaskType.SUBPROCESS_LITERAL.equals(newTaskType)) {
                // Sub-processes are slightly different.
                newImpl = Xpdl2Factory.eINSTANCE.createSubFlow();
                ((SubFlow) newImpl).setProcessId(UNKNOWN_REF_ID);

            } else {
                // For all non-sub-process type tasks must remove the
                // IsATransaction attribute.
                cmd.append(getSetSubProcessIsTransactionalCommand(ed,
                        act,
                        false));

                if (TaskType.REFERENCE_LITERAL.equals(newTaskType)) {
                    // XPDL2 has duplication of reference type
                    // (xpdl2:implementation/xpdl:Reference and
                    // xpdl2:Implementation/xpdl2:Task/xpdl2:TaskReference) but
                    // the former works for both sub-proc and tasks so we'll use
                    // that.

                    // Now Default to configuration to Library Task reference
                    // (but only if task lib editor installed)
                    if (Xpdl2ResourcesPlugin.isTaskLibraryPluginAvailable()) {
                        cmd.append(ReferenceTaskUtil
                                .getSetReferencedLibraryTaskCommand(ed,
                                        act,
                                        act.getProcess(),
                                        null,
                                        null));
                    } else {
                        cmd.append(ReferenceTaskUtil
                                .getSetReferencedLocalTaskCommand(ed, act, null));
                    }
                    newImpl = null; // Already deal with setup here.

                } else if (TaskType.NONE_LITERAL.equals(newTaskType)) {
                    // Task type None is now Implementation/No can't have empty
                    // Task
                    // attribute.
                    newImpl = Xpdl2Factory.eINSTANCE.createNo();
                } else {
                    // Standard task type...
                    newImpl =
                            ElementsFactory
                                    .createActivityTaskElement(newTaskType);
                }
            }

            if (newImpl != null) {
                cmd.append(SetCommand.create(ed,
                        act,
                        Xpdl2Package.eINSTANCE.getActivity_Implementation(),
                        newImpl));
            }
        }

        if (TaskType.SUBPROCESS_LITERAL.equals(newTaskType)) {
            /*
             * XPD-3445: With new Sub Process Start Strategy in place we now
             * don't initially have a default priority (because this will be
             * decided according to the type of sub-process selected in
             * SubProcessStartStrategyPreCommitCommandContributor
             */
        } else {
            /*
             * For all other task types unset it (as we don't allow priority to
             * be set.
             */
            cmd.append(SetCommand.create(ed,
                    act,
                    Xpdl2Package.eINSTANCE.getActivity_Priority(),
                    SetCommand.UNSET_VALUE));
        }

        appendSetResourcePatternsCommand(ed, act, newTaskType, cmd);

        return cmd;
    }

    /**
     * Add a command to remove event/embedded subprocess stuff to the specified
     * compound command.
     * 
     * @param ed
     *            Editing domain.
     * @param act
     *            Embedded/event sub process activity.
     * @param cmd
     *            Compound command to which the remove command is to be added.
     */
    private static void addCommandToRemoveEventOrEmbeddedSubProcStuff(
            EditingDomain ed, Activity act, CompoundCommand cmd) {

        BlockActivity blockAct = act.getBlockActivity();
        if (blockAct != null) {
            ActivitySet actSet = getActivitySet(act);

            // Delete the block activity first then the activity set.
            // Then when recreate on undo, the act set will be created
            // first.
            // That means that it's there ready for when the block act
            // is
            // recreated.

            cmd.append(SetCommand.create(ed,
                    act,
                    Xpdl2Package.eINSTANCE.getActivity_BlockActivity(),
                    null));

            if (actSet != null) {
                cmd.append(RemoveCommand.create(ed, actSet));
            }
        }
    }

    /**
     * Return <code>true</code> if the only start event inside the specified
     * event subprocess is a non-interrupting event, <code>false</code>
     * otherwise.
     * 
     * @param act
     *            Event subprocess activity.
     * @return <code>true</code> if the only start event inside the specified
     *         event subprocess is a non-interrupting event, <code>false</code>
     *         otherwise.
     */
    public static boolean isEventSubProcessNonInterrupting(Activity act) {

        if (TaskType.EVENT_SUBPROCESS_LITERAL.equals(getTaskTypeStrict(act))) {

            BlockActivity ba = act.getBlockActivity();

            Process prc = act.getProcess();

            ActivitySet actSet = prc.getActivitySet(ba.getActivitySetId());

            List<Activity> allActs = actSet.getActivities();

            for (Activity eachActivity : allActs) {

                if (eachActivity.getEvent() != null) {
                    if (EventFlowType.FLOW_START_LITERAL.equals(EventObjectUtil
                            .getFlowType(eachActivity))) {
                        return EventObjectUtil
                                .isNonInterruptingEventSubProcessStartEvent(eachActivity);
                    }
                }

            }
        }
        return false;
    }

    /**
     * @param ed
     * @param newTaskType
     * @param cmd
     */
    public static void appendSetResourcePatternsCommand(EditingDomain ed,
            Activity activity, TaskType newTaskType, CompoundCommand cmd) {
        ActivityResourcePatterns patterns = null;
        Object other =
                Xpdl2ModelUtil.getOtherElement(activity,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ActivityResourcePatterns());
        if (other instanceof ActivityResourcePatterns) {
            patterns = (ActivityResourcePatterns) other;
        }
        if (TaskType.USER_LITERAL.equals(newTaskType)) {
            WorkItemPriority createWorkItemPriority =
                    XpdExtensionFactory.eINSTANCE.createWorkItemPriority();
            createWorkItemPriority
                    .setInitialPriority(Xpdl2ModelUtil.DEFAULT_WORK_ITEM_PRIORITY);
            if (patterns == null) {
                patterns =
                        XpdExtensionFactory.eINSTANCE
                                .createActivityResourcePatterns();
                AllocationStrategy strategy =
                        XpdExtensionFactory.eINSTANCE
                                .createAllocationStrategy();
                patterns.setAllocationStrategy(strategy);
                strategy.setOffer(AllocationType.OFFER_ALL);
                strategy.setReOfferOnCancel(true);
                strategy.setReOfferOnClose(true);
                strategy.setStrategy(AllocationStrategyType.SYSTEM_DETERMINED);
                patterns.setWorkItemPriority(createWorkItemPriority);
                cmd.append(Xpdl2ModelUtil.getSetOtherElementCommand(ed,
                        activity,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ActivityResourcePatterns(),
                        patterns));
            } else {
                AllocationStrategy strategy = patterns.getAllocationStrategy();
                cmd.append(SetCommand.create(ed,
                        patterns,
                        XpdExtensionPackage.eINSTANCE
                                .getActivityResourcePatterns_WorkItemPriority(),
                        createWorkItemPriority));
                if (strategy == null) {
                    strategy =
                            XpdExtensionFactory.eINSTANCE
                                    .createAllocationStrategy();
                    strategy.setOffer(AllocationType.OFFER_ALL);
                    strategy.setReOfferOnCancel(true);
                    strategy.setReOfferOnClose(true);
                    strategy.setStrategy(AllocationStrategyType.SYSTEM_DETERMINED);
                    cmd.append(SetCommand.create(ed,
                            patterns,
                            XpdExtensionPackage.eINSTANCE
                                    .getActivityResourcePatterns_AllocationStrategy(),
                            strategy));
                } else {
                    if (!strategy.isSetStrategy()) {
                        cmd.append(SetCommand.create(ed,
                                strategy,
                                XpdExtensionPackage.eINSTANCE
                                        .getAllocationStrategy_Strategy(),
                                AllocationStrategyType.SYSTEM_DETERMINED));
                    }
                    if (!strategy.isSetOffer()) {
                        cmd.append(SetCommand.create(ed,
                                strategy,
                                XpdExtensionPackage.eINSTANCE
                                        .getAllocationStrategy_Offer(),
                                AllocationType.OFFER_ALL));
                    }
                }
            }
        } else if (TaskType.RECEIVE_LITERAL.equals(newTaskType)
                || TaskType.SEND_LITERAL.equals(newTaskType)
                || TaskType.SCRIPT_LITERAL.equals(newTaskType)
                || TaskType.SERVICE_LITERAL.equals(newTaskType)) {
            if (patterns == null) {
                patterns =
                        XpdExtensionFactory.eINSTANCE
                                .createActivityResourcePatterns();
                AllocationStrategy strategy =
                        XpdExtensionFactory.eINSTANCE
                                .createAllocationStrategy();
                patterns.setAllocationStrategy(strategy);
                strategy.setStrategy(AllocationStrategyType.SYSTEM_DETERMINED);
                cmd.append(Xpdl2ModelUtil.getSetOtherElementCommand(ed,
                        activity,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ActivityResourcePatterns(),
                        patterns));
            } else {
                AllocationStrategy strategy = patterns.getAllocationStrategy();
                if (strategy == null) {
                    strategy =
                            XpdExtensionFactory.eINSTANCE
                                    .createAllocationStrategy();
                    strategy.setStrategy(AllocationStrategyType.SYSTEM_DETERMINED);
                    cmd.append(SetCommand.create(ed,
                            patterns,
                            XpdExtensionPackage.eINSTANCE
                                    .getActivityResourcePatterns_AllocationStrategy(),
                            strategy));
                } else {
                    if (!strategy.isSetStrategy()) {
                        cmd.append(SetCommand.create(ed,
                                strategy,
                                XpdExtensionPackage.eINSTANCE
                                        .getAllocationStrategy_Strategy(),
                                AllocationStrategyType.SYSTEM_DETERMINED));
                    }
                    if (strategy.isSetOffer()) {
                        cmd.append(SetCommand.create(ed,
                                strategy,
                                XpdExtensionPackage.eINSTANCE
                                        .getAllocationStrategy_Offer(),
                                SetCommand.UNSET_VALUE));
                    }
                }
            }
        } else if (TaskType.MANUAL_LITERAL.equals(newTaskType)) {
            patterns =
                    XpdExtensionFactory.eINSTANCE
                            .createActivityResourcePatterns();
            WorkItemPriority createWorkItemPriority =
                    XpdExtensionFactory.eINSTANCE.createWorkItemPriority();
            createWorkItemPriority
                    .setInitialPriority(Xpdl2ModelUtil.DEFAULT_WORK_ITEM_PRIORITY);
            patterns.setWorkItemPriority(createWorkItemPriority);
            cmd.append(Xpdl2ModelUtil.getSetOtherElementCommand(ed,
                    activity,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_ActivityResourcePatterns(),
                    patterns));
        } else if (patterns != null) {
            cmd.append(Xpdl2ModelUtil.getRemoveOtherElementCommand(ed,
                    activity,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_ActivityResourcePatterns(),
                    patterns));
        }
    }

    /**
     * This method returns the name of the participant or the process field
     * whose id has been passed
     * 
     * @param participantIdOrName
     * @param eObject
     * @return
     */
    public static String getParticipantNameForPassedId(
            String participantIdOrName, EObject eObject) {
        String participantName = ""; //$NON-NLS-1$
        if (participantIdOrName == null
                || participantIdOrName.trim().length() < 1) {
            return participantName;
        }
        NamedElement matchingParticipant =
                getMatchingParticipant(participantIdOrName, eObject);
        if (matchingParticipant != null) {
            participantName = matchingParticipant.getName();
        } else {
            participantName = participantIdOrName;
        }
        return participantName;
    }

    private static NamedElement getMatchingParticipant(
            String participantIdOrName, EObject eObject) {
        NamedElement matchingParticipant = null;
        Process process = Xpdl2ModelUtil.getProcess(eObject);
        List<NamedElement> validPerformers =
                ProcessInfoUtil.getValidPerformerList(process);
        // trying to look for a participant which matches the id
        for (NamedElement namedElement : validPerformers) {
            if (participantIdOrName.equals(namedElement.getId())) {
                matchingParticipant = namedElement;
                break;
            }
        }
        if (matchingParticipant != null) {
            return matchingParticipant;
        }
        // trying to look for a participant which matches the name(considering
        // the passed partipcantId is a name not id) applies to Studio 3.0
        // process definition
        for (NamedElement namedElement : validPerformers) {
            if (participantIdOrName.equals(namedElement.getName())) {
                matchingParticipant = namedElement;
                break;
            }
        }
        return matchingParticipant;
    }

    /**
     * Returns a boolean value whether passed aliasValue is System Participant
     * or not
     * 
     * @param aliasValue
     * @param eObject
     * @return
     */
    public static boolean isAliasValueASystemParticipant(String aliasValue,
            EObject eObject) {
        boolean isSystemParticipant = false;
        if (aliasValue == null || aliasValue.trim().length() < 1) {
            return isSystemParticipant;
        }
        NamedElement matchingParticipant =
                TaskObjectUtil.getMatchingParticipant(aliasValue, eObject);
        if (matchingParticipant == null) {
            return isSystemParticipant;
        }
        if (matchingParticipant instanceof Participant) {
            Participant participant = (Participant) matchingParticipant;
            ParticipantTypeElem typeElem = participant.getParticipantType();
            if (typeElem != null) {
                if (ParticipantType.SYSTEM_LITERAL.equals(typeElem.getType())) {
                    isSystemParticipant = true;
                }
            }
        }
        return isSystemParticipant;
    }

    /**
     * util function which decides whether the passed url is remote or not.
     * 
     * @param url
     * @return
     */
    public static boolean isRemoteURL(String url) {
        if (url != null && (url.toLowerCase().startsWith("http://") || url //$NON-NLS-1$
                .toLowerCase().startsWith("https://"))) { //$NON-NLS-1$
            return true;
        }
        return false;
    }

    /**
     * @param process
     * @return The {@link ProcessWidgetType} for the given process.
     */
    public static ProcessWidgetType getProcessType(Process process) {

        if (process != null) {

            if (Xpdl2ModelUtil.isCaseService(process)) {

                return ProcessWidgetType.CASE_SERVICE;
            } else if (Xpdl2ModelUtil.isPageflowBusinessService(process)) {

                return ProcessWidgetType.BUSINESS_SERVICE;
            } else if (Xpdl2ModelUtil.isPageflow(process)) {

                return ProcessWidgetType.PAGEFLOW_PROCESS;
            } else if (Xpdl2ModelUtil.isServiceProcess(process)) {

                return ProcessWidgetType.SERVICE_PROCESS;
            } else {

                WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(process);
                if (wc instanceof Xpdl2WorkingCopyImpl) {

                    if (Xpdl2FileType.TASK_LIBRARY
                            .equals(((Xpdl2WorkingCopyImpl) wc)
                                    .getXpdl2FileType())) {

                        return ProcessWidgetType.TASK_LIBRARY;
                    } else if (Xpdl2FileType.DECISION_FLOW
                            .equals(((Xpdl2WorkingCopyImpl) wc)
                                    .getXpdl2FileType())) {

                        return ProcessWidgetType.DECISION_FLOW;
                    }
                }
            }
        }

        return ProcessWidgetType.BPMN_PROCESS;
    }

    /**
     * Given a sub-process invocation task activity, return the list of formal
     * parameters for starting the sub-process.
     * <p>
     * That is the parameters associated with the FIRST Type None start event in
     * the process or process interface invoked by the given activity.
     * <p>
     * If no start type none events are found then all formal parameters are
     * returned.
     * 
     * @param act
     * @param wantMode
     *            INOUT = all params, IN = IN and INOUT params, OUT = OUT and
     *            INOUT params
     * @return the list of formal parameters for starting the sub-process.
     */
    public static List<FormalParameter> getSubProcessStartParams(Activity act,
            ModeType wantMode) {
        List<FormalParameter> params = getSubProcessStartParams(act);
        List<FormalParameter> toReturn = new ArrayList<FormalParameter>();
        if (!ModeType.INOUT_LITERAL.equals(wantMode)) {
            // Excluding some results...
            for (Iterator iterator = params.iterator(); iterator.hasNext();) {
                FormalParameter formalParameter =
                        (FormalParameter) iterator.next();
                ModeType mode = formalParameter.getMode();

                if (ModeType.INOUT_LITERAL.equals(mode)) {
                    // In out's are always included.
                    toReturn.add(formalParameter);
                } else {
                    // Make sure it's the required type.
                    if (wantMode.equals(mode)) {
                        toReturn.add(formalParameter);
                    }
                }
            }
        } else {
            toReturn.addAll(params);
        }

        return toReturn;

    }

    /**
     * Given a sub-process invocation task activity, return the list of formal
     * parameters for starting the sub-process.
     * <p>
     * That is the parameters associated with the FIRST Type None start event in
     * the process or process interface invoked by the given activity.
     * <p>
     * If no start type none events are found then all formal parameters are
     * returned.
     * 
     * @param act
     * @return the list of formal parameters for starting the sub-process.
     */
    public static List<FormalParameter> getSubProcessStartParams(Activity act) {
        EObject procOrIfc = getSubProcessOrInterface(act);

        if (procOrIfc instanceof Process) {
            List<Activity> startEvents =
                    ProcessInterfaceUtil.getStartEventList((Process) procOrIfc);
            for (Activity se : startEvents) {
                if (TriggerType.NONE_LITERAL
                        .equals(((StartEvent) se.getEvent()).getTrigger())) {
                    return ProcessInterfaceUtil
                            .getAssociatedFormalParameters(se);
                }
            }

            return ProcessInterfaceUtil
                    .getAllFormalParameters((Process) procOrIfc);

        } else if (procOrIfc instanceof ProcessInterface) {
            EList<StartMethod> startEvents =
                    ((ProcessInterface) procOrIfc).getStartMethods();

            for (StartMethod se : startEvents) {
                if (TriggerType.NONE_LITERAL.equals(se.getTrigger())) {
                    return ProcessInterfaceUtil
                            .getStartMethodAssociatedFormalParameters(se);
                }
            }

            return ((ProcessInterface) procOrIfc).getFormalParameters();
        }

        return Collections.emptyList();
    }

    /**
     * @param ed
     * @param activityInput
     * @return
     */
    public static Command getSetNotGeneratedCommand(EditingDomain ed,
            Activity activity) {
        if (TaskType.RECEIVE_LITERAL.equals(getTaskType(activity))) {
            TaskReceive taskReceive =
                    ((Task) activity.getImplementation()).getTaskReceive();
            return Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                    taskReceive,
                    XpdExtensionPackage.eINSTANCE.getDocumentRoot_Generated(),
                    Boolean.FALSE);
        }
        return null;
    }

    /**
     * Either Bring up a participant picker for the given activity, allow the
     * user to select participants and then return a command that will set the
     * new participants in the activity.
     * <p>
     * Or clear the task participants.
     * 
     * @return Command to set / clear the participants.
     */
    public static Command selectOrClearActivityParticipantCommand(
            EditingDomain editingDomain, Process parentProcess,
            Activity activity, boolean clearParticipants) {
        Command cmd = UnexecutableCommand.INSTANCE;

        WebServiceOperation wso = null;
        if (activity.getImplementation() instanceof Task) {
            Task task = (Task) activity.getImplementation();
            if (task.getTaskService() != null) {
                TaskService taskService = task.getTaskService();
                wso = taskService.getWebServiceOperation();
            } else if (task.getTaskSend() != null) {
                TaskSend taskSend = task.getTaskSend();
                wso = taskSend.getWebServiceOperation();
            } else if (task.getTaskReceive() != null) {
                TaskReceive taskRecieve = task.getTaskReceive();
                wso = taskRecieve.getWebServiceOperation();
            }
        }

        if (!clearParticipants) {
            // Participant browsing

            EObject[] initialPerfs =
                    TaskObjectUtil.getActivityPerformers(activity,
                            parentProcess);

            List<EObject> newPerfs =
                    selectActivityPerformers(parentProcess, activity);

            /*
             * XPD-6764: Saket: Should only fire command when the section has
             * changed.
             */
            if (newPerfs != null && hasSelectionChanged(initialPerfs, newPerfs)) {
                CompoundCommand command = new CompoundCommand();
                command.append(TaskObjectUtil
                        .getSetPerformersCommand(editingDomain,
                                activity,
                                newPerfs.toArray(new EObject[0])));

                // if this process is a webservice type we need
                // to set the alias as this participant
                if (newPerfs.size() > 0 && wso != null) {
                    command.append(Xpdl2ModelUtil
                            .getSetEndpointFromDataPickerSelectionCommand(editingDomain,
                                    newPerfs.get(0),
                                    wso));
                }
                cmd = command;
            }
        } else {
            EObject[] emptyArray = new EObject[] {};
            CompoundCommand command = new CompoundCommand();
            command.append(TaskObjectUtil
                    .getSetPerformersCommand(editingDomain,
                            activity,
                            emptyArray));
            // clear web service alias also
            if (wso != null) {
                command.append(Xpdl2ModelUtil
                        .getSetOtherAttributeCommand(editingDomain,
                                wso,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_Alias(),
                                "")); //$NON-NLS-1$
            }
            cmd = command;
        }

        return cmd;
    }

    /**
     * Return <code>true</code> if the contents of initialPerfs and newPerfs are
     * NOT the same, <code>true</code> if both of them are exactly the same.
     * 
     * @param initialPerfs
     *            Initial performers.
     * @param newPerfs
     *            New performers.
     * @return <code>true</code> if the contents of initialPerfs and newPerfs
     *         are NOT the same (i.e., the selection has changed),
     *         <code>true</code> if both of them are exactly the same.
     */
    private static boolean hasSelectionChanged(EObject[] initialPerfs,
            List<EObject> newPerfs) {

        /*
         * Compare size.
         */
        if (initialPerfs.length == newPerfs.size()) {

            /*
             * Compare contents.
             */
            if (newPerfs.containsAll(Arrays.asList(initialPerfs))) {

                return false;
            }
        }

        return true;
    }

    public static List<EObject> selectActivityPerformers(Process parentProcess,
            Activity activity) {
        DataFilterPicker picker =
                new DataFilterPicker(Display.getCurrent().getActiveShell(),
                        DataPickerType.PARTICIPANTS, true);
        picker.setScope(activity);

        picker.setInitialSelections(TaskObjectUtil
                .getActivityPerformers(activity, parentProcess));
        int ret = picker.open();

        if (ret == ParticipantsPicker.OK) {
            Object[] newPerfs = picker.getResult();
            if (newPerfs != null) {
                List<EObject> perfs = new ArrayList<EObject>();
                for (int i = 0; i < newPerfs.length; i++) {
                    if (newPerfs[i] instanceof EObject) {
                        perfs.add((EObject) newPerfs[i]);
                    }
                }
                return perfs;
            }
        }
        return null;
    }

    /**
     * Command to set the name of a task to the given base name PLUS a unique
     * index IF the base name would duplicate a different tasks name.
     * <p>
     * This is a late execution command so that multiple renames can be done in
     * a single parent command and each rename (even to same baseName) will
     * result in a unique name.
     * 
     * @author aallway
     * @since 3.2
     */
    private static class SetUniqueTaskNameCommand extends CompoundCommand {
        private final Activity act;

        private final String baseName;

        private final EditingDomain editingDomain;

        private final Process parentProcess;

        SetUniqueTaskNameCommand(EditingDomain editingDomain,
                Process parentProcess, Activity act, String baseName) {
            this.act = act;
            this.baseName = baseName;
            this.editingDomain = editingDomain;
            this.parentProcess = parentProcess;
        }

        @Override
        public boolean canExecute() {
            return true;
        }

        @Override
        public void execute() {
            Collection<Activity> acts =
                    Xpdl2ModelUtil.getAllActivitiesInProc(parentProcess);

            int idx = 1;
            String finalName = baseName;
            while (isDuplicateTaskName(finalName, acts)) {
                idx++;
                finalName = baseName + " " + idx; //$NON-NLS-1$
            }

            this.appendAndExecute(SetCommand.create(editingDomain,
                    act,
                    Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                    NameUtil.getInternalName(finalName, false)));

            this.appendAndExecute(Xpdl2ModelUtil
                    .getSetOtherAttributeCommand(editingDomain,
                            act,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName(),
                            finalName));
            return;
        }

        private boolean isDuplicateTaskName(String finalName,
                Collection<Activity> acts) {
            String tokName = NameUtil.getInternalName(finalName, false);

            for (Activity a : acts) {
                if (finalName.equals(Xpdl2ModelUtil.getDisplayNameOrName(a))
                        || tokName.equals(a.getName())) {
                    return true;
                }
            }

            return false;
        }

    }

    /*
     * Methods getUserTaskFormImplementation(), getUserTaskPageflowProcess() and
     * getUserTaskFormFile() moved to super class
     * com.tibco.xpd.analyst.resources.xpdl2.utils.TaskObjectUtil.
     */
    /**
     * Get command to set (or unset) the FormImplementation element.
     * 
     * @param editingDomain
     * @param activity
     * @param formImpl
     *            FormImplementation or null to remove.
     * 
     * @return Command to set the TaskUser/FormImplementation element
     */
    public static Command getUserTaskSetFormImplementationCommand(
            EditingDomain editingDomain, Activity act,
            FormImplementation formImpl) {
        CompoundCommand cmd = new CompoundCommand();

        if (act != null && act.getImplementation() instanceof Task) {
            Task task = (Task) act.getImplementation();
            if (task.getTaskUser() != null) {

                if (formImpl != null) {
                    cmd.setLabel(Messages.TaskObjectUtil_SetUSerTaskFormImplementation_menu);
                    //
                    // Set the TaskUser/FormImplementation element.
                    cmd.append(Xpdl2ModelUtil.getSetOtherElementCommand(editingDomain,
                            task.getTaskUser(),
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_FormImplementation(),
                            formImpl));
                } else {
                    //
                    // Remove the TaskUser/FormImplementation element.
                    cmd.setLabel(Messages.TaskObjectUtil_UnsetUSerTaskFormImplementation_menu);

                    FormImplementation curFormImpl =
                            (FormImplementation) Xpdl2ModelUtil
                                    .getOtherElement(task.getTaskUser(),
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_FormImplementation());

                    if (curFormImpl != null) {
                        cmd.append(Xpdl2ModelUtil
                                .getRemoveOtherElementCommand(editingDomain,
                                        task.getTaskUser(),
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_FormImplementation(),
                                        curFormImpl));
                    }
                }

                if (isTaskUsingDefaultIconForType(act)) {
                    cmd.append(new LateExecSetDefTaskIconCommand(editingDomain,
                            act));
                }
            }
        }

        if (!cmd.isEmpty()) {
            return cmd;
        }

        return UnexecutableCommand.INSTANCE;
    }

    /**
     * Get command to set (or unset) the FormImplementation element.
     * 
     * @param editingDomain
     * @param act
     * @param formImplType
     * @param formURI
     * 
     * @return Command to set the TaskUser/FormImplementation element
     */
    public static Command getUserTaskSetFormImplementationCommand(
            EditingDomain editingDomain, Activity act,
            FormImplementationType formImplType, String formURI) {
        FormImplementation formImpl =
                XpdExtensionFactory.eINSTANCE.createFormImplementation();

        if (formImplType != null) {
            formImpl.setFormType(formImplType);
        }

        if (formURI != null) {
            formImpl.setFormURI(formURI);
        }

        return getUserTaskSetFormImplementationCommand(editingDomain,
                act,
                formImpl);
    }

    /**
     * @param process
     * @return The URI to use for Pageflow process Form referencing.
     */
    public static String getUserTaskFormURIFromPageflowProcess(Process process) {
        /*
         * MR 41967: Used to use EcoreUtil.getURI(process) but that's rubbish
         * because its an absolute workspace relateive uri which of course will
         * break if you copy the xpdl to a different project when the user task
         * and referenced pageflow are in the same xpdl.
         * 
         * Now we use <special folder relative path>#<process id>
         */
        IPath path = SpecialFolderUtil.getSpecialFolderRelativePath(process);

        URI uri = URI.createURI(path.toString());
        uri = uri.appendFragment(process.getId());

        return uri.toString();
    }

    /**
     * @param process
     * @return The URI to use for form file Form referencing.
     */
    public static String getUserTaskFormURIFromFormFile(IFile formFile) {
        if (formFile != null) {
            IPath sfRelativePath =
                    SpecialFolderUtil.getSpecialFolderRelativePath(formFile,
                            FORMS_KIND);
            return FORM_SCHEMA
                    + sfRelativePath.makeRelative().toPortableString();
        }
        return ""; //$NON-NLS-1$
    }

    /**
     * Allow user to select a form file that is in scope of given actviity using
     * a picker.
     * 
     * @param shell
     * @param userTaskActivity
     * 
     * @return Form file or null if user cancelled etc.
     */
    public static IFile getUserTaskFormFileFromPicker(Shell shell,
            Activity userTaskActivity) {
        FormUrlPicker picker = new FormUrlPicker(shell, true, false);

        picker.setScope(userTaskActivity);

        if (picker.open() == FormUrlPicker.OK) {
            Object result = picker.getFirstResult();
            if (result instanceof IFile) {
                return (IFile) result;
            }
        }

        return null;
    }

    /**
     * Allow user to select a form file that is in scope of given actviity using
     * a picker.
     * <p>
     * A command is returned that will set the reference to the user-selected
     * form file (or null if user cancels)
     * 
     * @param shell
     * @param userTaskActivity
     * 
     * @return Command to set the reference to the user-selected form file (or
     *         null if user cancels)
     */
    public static Command getUserTaskSetFormFileFromPickerCommand(Shell shell,
            EditingDomain ed, Activity userTaskActivity) {
        FormUrlPicker picker = new FormUrlPicker(shell, true, false);

        picker.setScope(userTaskActivity);

        if (picker.open() == FormUrlPicker.OK) {
            Object result = picker.getFirstResult();
            if (result instanceof IFile) {
                String newFormURI =
                        TaskObjectUtil
                                .getUserTaskFormURIFromFormFile((IFile) result);

                return TaskObjectUtil
                        .getUserTaskSetFormImplementationCommand(ed,
                                userTaskActivity,
                                FormImplementationType.FORM,
                                newFormURI);
            }
        }

        return null;
    }

    /**
     * Allow user to select a form file that is in scope of given actviity using
     * a picker.
     * <p>
     * A command is returned that will set the reference to the user-selected
     * form file (or null if user cancels)
     * 
     * @param shell
     * @param userTaskActivity
     * 
     * @return Command to set the reference to the user-selected form file (or
     *         null if user cancels)
     */
    public static Command getUserTaskSetPageflowProcessFromPickerCommand(
            Shell shell, EditingDomain ed, Activity userTaskActivity) {
        FormUrlPicker picker = new FormUrlPicker(shell, false, true);

        picker.setScope(userTaskActivity);

        if (picker.open() == FormUrlPicker.OK) {
            Object result = picker.getFirstResult();
            if (result instanceof Process) {
                Process pageflowProcess = (Process) result;

                /*
                 * Sid XPD-2976: Check if pageflow is in a project that is
                 * already referenced and if not, ask the user whether to add
                 * the reference.
                 */
                if (ProcessUIUtil.checkAndAddProjectReference(shell,
                        userTaskActivity,
                        pageflowProcess)) {
                    return getUserTaskSetPageflowProcessCommand(ed,
                            userTaskActivity,
                            pageflowProcess);
                }
            }
        }

        return null;
    }

    /**
     * Get the Command to the set the user task to reference the given pageflow.
     * 
     * @param ed
     * @param userTaskActivity
     * @param pageflowProcess
     * @return Command to the set the user task to reference the given pageflow
     *         or null if user cancels addition of extra data. <b>Note that ANY
     *         explicit / implicit pageflow start parameters that have no
     *         equivalent (by name) in target process will have data fields
     *         created for them and the yuser task associaitons are set to match
     *         the pageflow interface. This is in order that the best efforts
     *         are made to ensure that the user task interface and pageflow
     *         interface match on initial creation of reference.</b>
     */
    public static Command getUserTaskSetPageflowProcessCommand(
            EditingDomain ed, Activity userTaskActivity, Process pageflowProcess) {
        String newFormURI =
                TaskObjectUtil
                        .getUserTaskFormURIFromPageflowProcess(pageflowProcess);

        Command c =
                TaskObjectUtil.getUserTaskSetFormImplementationCommand(ed,
                        userTaskActivity,
                        FormImplementationType.PAGEFLOW,
                        newFormURI);

        LateExecuteCompoundCommand cmd = new LateExecuteCompoundCommand();
        cmd.setLabel(c.getLabel());
        cmd.append(c);

        Command repairIfcCmd =
                PageflowUtil.getCreateUserTaskDataForPageflowCommand(ed,
                        userTaskActivity,
                        userTaskActivity.getProcess(),
                        pageflowProcess,
                        true);
        if (repairIfcCmd != null) {
            cmd.append(repairIfcCmd);
        } else {
            cmd = null;
        }

        return cmd;
    }

    /**
     * @param activity
     * @return The eclipse registry id for the user-set custom default icon for
     *         particular task type.
     */
    public static String getTaskTypeIconRegistryId(Activity activity) {
        TaskType tt = getTaskTypeStrict(activity);
        if (tt != null) {
            String regId = "task.type.icon."; //$NON-NLS-1$
            regId += tt.toString();

            if (TaskType.SERVICE_LITERAL.equals(tt)) {
                /* Break down service task into type - by - implementation. */
                String implId = getTaskImplementationExtensionId(activity);
                if (implId != null && implId.length() > 0) {
                    regId += "." + implId; //$NON-NLS-1$

                } else {
                    regId +=
                            "."     + ImplementationType.UNSPECIFIED_LITERAL.getLiteral(); //$NON-NLS-1$
                }

            } else if (TaskType.USER_LITERAL.equals(tt)) {
                /* Break down user task into type - by - implementation. */
                String formId = "DefaultForm"; //$NON-NLS-1$
                FormImplementation formImpl =
                        getUserTaskFormImplementation(activity);
                if (formImpl != null) {
                    if (formImpl.getFormType() != null) {
                        formId = formImpl.getFormType().getLiteral();
                    }
                }

                regId += "." + formId; //$NON-NLS-1$
            }

            return regId;
        }

        return null;
    }

    /**
     * @param act
     * @return Last user-set path for default inon for act's task type
     */
    public static String getDefaultIconForTaskType(Activity act) {
        String iconRegId = TaskObjectUtil.getTaskTypeIconRegistryId(act);
        if (iconRegId != null && iconRegId.length() > 0) {
            IPreferenceStore prefStore =
                    ProcessWidgetPlugin.getDefault().getPreferenceStore();

            String defaultIconPath = prefStore.getString(iconRegId);
            if (defaultIconPath != null && defaultIconPath.length() > 0) {
                return defaultIconPath;
            }
        }
        return null;
    }

    /**
     * LateExecSetDefTaskIconCommand
     * <p>
     * Set the given activity's icon path to the defautl for it's type (type is
     * evaluated only at exec time).
     * 
     * @author aallway
     * @since 3.3 (5 Oct 2009)
     */
    public static final class LateExecSetDefTaskIconCommand extends
            AbstractLateExecuteCommand {
        /**
         * @param editingDomain
         * @param contextObject
         */
        public LateExecSetDefTaskIconCommand(EditingDomain editingDomain,
                Object contextObject) {
            super(editingDomain, contextObject);
        }

        @Override
        protected Command createCommand(EditingDomain editingDomain,
                Object contextObject) {
            Icon newIcon = null;

            String iconRegId =
                    TaskObjectUtil
                            .getTaskTypeIconRegistryId((Activity) contextObject);
            if (iconRegId != null) {
                IPreferenceStore prefStore =
                        ProcessWidgetPlugin.getDefault().getPreferenceStore();

                String defIcon = prefStore.getString(iconRegId);

                if (defIcon != null && defIcon.length() > 0) {
                    newIcon = Xpdl2Factory.eINSTANCE.createIcon();
                    newIcon.setValue(defIcon);
                }
            }

            return SetCommand.create(editingDomain,
                    contextObject,
                    Xpdl2Package.eINSTANCE.getActivity_Icon(),
                    newIcon);
        }
    }

    /**
     * Returns list of start event activities in the given process.
     * 
     * @param process
     */
    public static List<Activity> getInvokeBusinessProcessStartActivities(
            Process process) {
        List<Activity> startActivities = new LinkedList<Activity>();
        if (process != null) {
            for (Activity activity : process.getActivities()) {
                // check for Message Start Events
                if (activity.getEvent() instanceof StartEvent
                        && EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL
                                .equals(EventObjectUtil
                                        .getEventTriggerType(activity))) {
                    startActivities.add(activity);
                }
            }
        }
        return startActivities;
    }

}
