/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.util;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.processeditor.xpdl2.properties.script.IMapperGrammarTypeResolver;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.xpdExtension.SignalData;
import com.tibco.xpd.xpdExtension.SignalType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.CatchThrow;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskReceive;
import com.tibco.xpd.xpdl2.TaskSend;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Mapper grammar resolver for any Web Service/Global Signal (Catch/Throw)
 * activities.
 * 
 * @author sajain
 * @since Jan 20, 2016
 */
public class WSAndGSActivitiesMapperGrammarTypeResolver implements
        IMapperGrammarTypeResolver {

    public WSAndGSActivitiesMapperGrammarTypeResolver() {
    }

    @Override
    public String getMapperGrammarType(Activity activity,
            DirectionType direction) {

        if (activity != null) {

            if (activity.getImplementation() instanceof Task) {

                Message msg =
                        getMessageElementForTask(activity.getImplementation(),
                                direction);

                if (msg != null) {

                    if ((DirectionType.IN_LITERAL.equals(direction)
                            && null != Xpdl2ModelUtil.getOtherElement(msg,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_InputMappings()))
                            || (DirectionType.OUT_LITERAL.equals(direction)
                                    && null != Xpdl2ModelUtil.getOtherElement(msg,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_OutputMappings()))) {

                        return ScriptGrammarFactory.DATAMAPPER;

                    }
                }

            } else if (null != activity.getEvent()) {

                Object obj = getSDMContainerForEvents(activity, direction);

                if (obj instanceof Message) {
                    Message msg = (Message) obj;

                    if ((DirectionType.IN_LITERAL.equals(direction)
                            && null != Xpdl2ModelUtil.getOtherElement(msg,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_InputMappings())) || (DirectionType.OUT_LITERAL.equals(direction)
                            && null != Xpdl2ModelUtil.getOtherElement(msg,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_OutputMappings()))) {

                        return ScriptGrammarFactory.DATAMAPPER;

                    }

                } else if (obj instanceof ResultError) {
                    ResultError resultError = (ResultError) obj;

                    if ((DirectionType.IN_LITERAL.equals(direction)
                            && null != Xpdl2ModelUtil
                                    .getOtherElement(resultError,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_InputMappings()))
                            || (DirectionType.OUT_LITERAL.equals(direction)
                            && null != Xpdl2ModelUtil
                                    .getOtherElement(resultError,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_OutputMappings()))) {

                        return ScriptGrammarFactory.DATAMAPPER;

                    }
                } else if (obj instanceof SignalData) {

                    SignalData sigData = (SignalData) obj;
                    if ((DirectionType.IN_LITERAL.equals(direction)
                            && null != sigData.getInputScriptDataMapper()
                            || (DirectionType.OUT_LITERAL.equals(direction)
                            && null != sigData.getOutputScriptDataMapper()))) {

                        return ScriptGrammarFactory.DATAMAPPER;

                    }
                }

            }

        }

        return null;
    }

    /**
     * 
     * @param activity
     * @return <code>true</code> if the passed activity is an is an Global
     *         signal event.
     */
    public static boolean isGlobalSignalEvent(Activity activity) {
        Event event = activity.getEvent();
        if (event != null) {
            EObject eventTriggerTypeNode = event.getEventTriggerTypeNode();

            if (eventTriggerTypeNode instanceof TriggerResultSignal) {
                TriggerResultSignal signal =
                        (TriggerResultSignal) eventTriggerTypeNode;

                Object otherAttribute =
                        Xpdl2ModelUtil.getOtherAttribute(signal,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_SignalType());

                if (otherAttribute instanceof SignalType) {
                    SignalType sigType = (SignalType) otherAttribute;
                    return SignalType.GLOBAL.equals(sigType) ? true : false;
                }
            }
        }
        return false;
    }

    /**
     * Get Message element for the specified task implementation.
     * 
     * @param impl
     * 
     * @return message for the specified task implementation.
     */
    private Message getMessageElementForTask(Implementation impl,
            DirectionType direction) {

        Task task = (Task) impl;

        if (null != task.getTaskService()) {

            /*
             * Service Task
             */

            TaskService taskService = task.getTaskService();

            if (DirectionType.IN_LITERAL.equals(direction)
                    && null != taskService.getMessageIn()) {

                return taskService.getMessageIn();

            } else if (DirectionType.OUT_LITERAL.equals(direction)
                    && null != taskService.getMessageOut()) {

                return taskService.getMessageOut();
            }

        } else if (null != task.getTaskSend()) {

            /*
             * Send Task.
             */

            TaskSend taskSend = task.getTaskSend();

            if (DirectionType.IN_LITERAL.equals(direction)
                    && null != taskSend.getMessage()) {

                return taskSend.getMessage();

            }

        } else if (null != task.getTaskReceive()) {

            /*
             * Receive Task.
             */

            TaskReceive taskReceive = task.getTaskReceive();

            if (DirectionType.OUT_LITERAL.equals(direction)
                    && null != taskReceive.getMessage()) {

                return taskReceive.getMessage();

            }
        }
        return null;
    }

    /**
     * Get SDM container for the specified event activity.
     * 
     * @param activity
     * @return SDM container for the specified event activity.
     */
    private Object getSDMContainerForEvents(Activity activity,
            DirectionType direction) {

        Event event = activity.getEvent();

        if (event instanceof EndEvent) {

            /*
             * End events
             */

            EndEvent endEvent = (EndEvent) event;

            TriggerResultMessage trm = endEvent.getTriggerResultMessage();
            ResultError resultError = endEvent.getResultError();
            TriggerResultSignal trs = endEvent.getTriggerResultSignal();
            if (null != trm) {

                Object msg = trm.getMessage();

                if (CatchThrow.THROW.equals(trm.getCatchThrow())
                        && DirectionType.IN_LITERAL.equals(direction)) {

                    return msg;
                }
            } else if (resultError != null
                    && DirectionType.IN_LITERAL.equals(direction)) {

                return resultError;
            } else if (null != trs) {

                Object sigData =
                        Xpdl2ModelUtil.getOtherElement(trs,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_SignalData());

                if (CatchThrow.THROW.equals(trs.getCatchThrow())
                        && DirectionType.IN_LITERAL.equals(direction)) {

                    return sigData;
                }
            }

        } else if (event instanceof IntermediateEvent) {

            /*
             * Intermediate events
             */

            IntermediateEvent intermediateEvent = (IntermediateEvent) event;

            TriggerResultMessage trm =
                    intermediateEvent.getTriggerResultMessage();

            TriggerResultSignal trs =
                    intermediateEvent.getTriggerResultSignal();

            if (null != trm) {

                Object msg = trm.getMessage();

                if (CatchThrow.THROW.equals(trm.getCatchThrow())
                        && DirectionType.IN_LITERAL.equals(direction)) {

                    return msg;

                } else if (CatchThrow.CATCH.equals(trm.getCatchThrow())
                        && DirectionType.OUT_LITERAL.equals(direction)) {

                    return msg;
                }
            } else if (null != trs) {

                Object sigData =
                        Xpdl2ModelUtil.getOtherElement(trs,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_SignalData());

                if (CatchThrow.THROW.equals(trs.getCatchThrow())
                        && DirectionType.IN_LITERAL.equals(direction)) {

                    return sigData;

                } else if (CatchThrow.CATCH.equals(trs.getCatchThrow())
                        && DirectionType.OUT_LITERAL.equals(direction)) {

                    return sigData;
                }
            }

        } else if (event instanceof StartEvent) {

            /*
             * Start events
             */

            StartEvent startEvent = (StartEvent) event;

            TriggerResultMessage trm = startEvent.getTriggerResultMessage();
            TriggerResultSignal trs = startEvent.getTriggerResultSignal();

            if (null != trm) {

                Object msg = trm.getMessage();

                if (CatchThrow.CATCH.equals(trm.getCatchThrow())
                        && DirectionType.OUT_LITERAL.equals(direction)) {

                    return msg;
                }
            } else if (null != trs) {

                Object sigData =
                        Xpdl2ModelUtil.getOtherElement(trs,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_SignalData());

                if (CatchThrow.CATCH.equals(trs.getCatchThrow())
                        && DirectionType.OUT_LITERAL.equals(direction)) {

                    return sigData;
                }
            }

        }

        return null;
    }

}
