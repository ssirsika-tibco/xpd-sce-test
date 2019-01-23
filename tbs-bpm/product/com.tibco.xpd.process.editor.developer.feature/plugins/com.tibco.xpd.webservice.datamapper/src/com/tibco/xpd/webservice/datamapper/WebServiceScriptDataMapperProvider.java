/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.webservice.datamapper;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.datamapper.scripts.AbstractScriptDataMapperEditorProvider;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.CatchThrow;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskReceive;
import com.tibco.xpd.xpdl2.TaskSend;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Script Data Mapper provider for Web Service mapping scenarios.
 * 
 * @author sajain
 * @since Jan 20, 2016
 */
public class WebServiceScriptDataMapperProvider extends
        AbstractScriptDataMapperEditorProvider {

    private MappingDirection direction;

    public WebServiceScriptDataMapperProvider(String mapperContext,
            MappingDirection direction) {
        super(
                mapperContext,
                MappingDirection.IN.equals(direction) ? DirectionType.IN_LITERAL
                        : DirectionType.OUT_LITERAL);
        this.direction = direction;
    }

    /**
     * @see com.tibco.xpd.datamapper.scripts.IScriptDataMapperProvider#getScriptDataMapper(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    public ScriptDataMapper getScriptDataMapper(Object inputObject) {
        ScriptDataMapper sdm = null;
        if (inputObject instanceof Activity) {
            Activity activity = (Activity) inputObject;

            OtherElementsContainer oec = getScriptDataMapperContainer(activity);
            if (oec != null) {
                Object other = null;

                if (MappingDirection.IN.equals(direction)) {
                    other =
                            Xpdl2ModelUtil.getOtherElement(oec,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_InputMappings());
                } else if (MappingDirection.OUT.equals(direction)) {
                    other =
                            Xpdl2ModelUtil.getOtherElement(oec,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_OutputMappings());
                }

                if (other instanceof ScriptDataMapper) {
                    sdm = (ScriptDataMapper) other;
                }
            }
        }
        return sdm;
    }

    /**
     * @param contextInputObject
     * 
     * @return the Correct container for the xpdExt:ScriptDataMapper element
     *         (depending on mapping direction we were constructed with.
     */
    public OtherElementsContainer getScriptDataMapperContainer(
            Object contextInputObject) {
        OtherElementsContainer oec = null;

        if (contextInputObject instanceof Activity) {
            Activity activity = (Activity) contextInputObject;
            Implementation impl = activity.getImplementation();

            if (impl instanceof Task) {

                /*
                 * Tasks.
                 */

                oec = getSDMContainerForTask(impl);

            } else if (null != activity.getEvent()) {

                /*
                 * Events.
                 */

                oec = getSDMContainerForEvents(activity);

            }
        }
        return oec;
    }

    /**
     * Get Script Data Mapper container for the specified event activity.
     * 
     * @param activity
     * @return Script Data Mapper container for the specified event activity.
     */
    private OtherElementsContainer getSDMContainerForEvents(Activity activity) {

        OtherElementsContainer oec = null;

        Event event = activity.getEvent();

        if (event instanceof EndEvent) {

            /*
             * End events
             */

            EndEvent endEvent = (EndEvent) event;

            TriggerResultMessage trm = endEvent.getTriggerResultMessage();
            ResultError resultError = endEvent.getResultError();
            if (null != trm) {

                Object msg = trm.getMessage();

                if (CatchThrow.THROW.equals(trm.getCatchThrow())
                        && MappingDirection.IN.equals(direction)) {

                    if (msg instanceof OtherElementsContainer) {

                        oec = (OtherElementsContainer) msg;

                    }
                }
            } else if (resultError != null) {
                oec = resultError;

                Object faultMessageObj =
                        Xpdl2ModelUtil.getOtherElement(resultError,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_FaultMessage());

                if (faultMessageObj instanceof Message) {
                    Message fm = (Message) faultMessageObj;
                    oec = fm;
                }

            }

        } else if (event instanceof IntermediateEvent) {

            /*
             * Intermediate events
             */

            IntermediateEvent intermediateEvent = (IntermediateEvent) event;

            TriggerResultMessage trm =
                    intermediateEvent.getTriggerResultMessage();

            if (null != trm) {

                Object msg = trm.getMessage();

                if (CatchThrow.THROW.equals(trm.getCatchThrow())
                        && MappingDirection.IN.equals(direction)) {

                    if (msg instanceof OtherElementsContainer) {

                        oec = (OtherElementsContainer) msg;

                    }

                } else if (CatchThrow.CATCH.equals(trm.getCatchThrow())
                        && MappingDirection.OUT.equals(direction)) {

                    if (msg instanceof OtherElementsContainer) {

                        oec = (OtherElementsContainer) msg;

                    }
                }
            }

        } else if (event instanceof StartEvent) {

            /*
             * Start events
             */

            StartEvent startEvent = (StartEvent) event;

            TriggerResultMessage trm = startEvent.getTriggerResultMessage();

            if (null != trm) {

                Object msg = trm.getMessage();

                if (CatchThrow.CATCH.equals(trm.getCatchThrow())
                        && MappingDirection.OUT.equals(direction)) {

                    if (msg instanceof OtherElementsContainer) {

                        oec = (OtherElementsContainer) msg;

                    }
                }
            }

        }

        if (oec == null) {
            WebServiceDataMapperPlugin.log(new RuntimeException(
                    "Script Data Mapper Container cannot be null.")); //$NON-NLS-1$
        }

        return oec;
    }

    /**
     * Get Script Data Mapper container for the specified task implementation.
     * 
     * @param impl
     * 
     * @return Script Data Mapper container for the specified task
     *         implementation.
     */
    private OtherElementsContainer getSDMContainerForTask(Implementation impl) {

        OtherElementsContainer oec = null;

        Task task = (Task) impl;

        if (null != task.getTaskService()) {

            /*
             * Service Task
             */

            TaskService taskService = task.getTaskService();

            if (MappingDirection.IN.equals(direction)
                    && null != taskService.getMessageIn()) {

                Object obj = taskService.getMessageIn();

                if (obj instanceof OtherElementsContainer) {
                    oec = (OtherElementsContainer) obj;
                }

            } else if (MappingDirection.OUT.equals(direction)
                    && null != taskService.getMessageOut()) {

                Object obj = taskService.getMessageOut();

                if (obj instanceof OtherElementsContainer) {
                    oec = (OtherElementsContainer) obj;
                }
            }

        } else if (null != task.getTaskSend()) {

            /*
             * Send Task.
             */

            TaskSend taskSend = task.getTaskSend();

            if (MappingDirection.IN.equals(direction)
                    && null != taskSend.getMessage()) {

                Object obj = taskSend.getMessage();

                if (obj instanceof OtherElementsContainer) {
                    oec = (OtherElementsContainer) obj;
                }

            }

        } else if (null != task.getTaskReceive()) {

            /*
             * Receive Task.
             */

            TaskReceive taskReceive = task.getTaskReceive();

            if (MappingDirection.OUT.equals(direction)
                    && null != taskReceive.getMessage()) {

                Object obj = taskReceive.getMessage();

                if (obj instanceof OtherElementsContainer) {
                    oec = (OtherElementsContainer) obj;
                }

            }
        }
        return oec;
    }

    /**
     * @see com.tibco.xpd.datamapper.scripts.AbstractScriptDataMapperEditorProvider#createScriptDataMapper(java.lang.Object,
     *      org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.common.command.CompoundCommand)
     * 
     * @param contextInputObject
     * @param editingDomain
     * @param optionalCreationCommand
     * @return
     */
    @Override
    protected ScriptDataMapper createScriptDataMapper(
            Object contextInputObject, EditingDomain editingDomain,
            CompoundCommand optionalCreationCommand) {
        ScriptDataMapper scriptDataMapper = null;

        OtherElementsContainer scriptDataMapperContainer =
                getScriptDataMapperContainer(contextInputObject);

        if (scriptDataMapperContainer != null) {
            scriptDataMapper =
                    XpdExtensionFactory.eINSTANCE.createScriptDataMapper();

            if (optionalCreationCommand != null) {

                if (MappingDirection.IN.equals(direction)) {

                    optionalCreationCommand.append(Xpdl2ModelUtil
                            .getSetOtherElementCommand(editingDomain,
                                    scriptDataMapperContainer,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_InputMappings(),
                                    scriptDataMapper));

                } else if (MappingDirection.OUT.equals(direction)) {

                    optionalCreationCommand.append(Xpdl2ModelUtil
                            .getSetOtherElementCommand(editingDomain,
                                    scriptDataMapperContainer,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_OutputMappings(),
                                    scriptDataMapper));

                }
            } else {

                if (MappingDirection.IN.equals(direction)) {

                    Xpdl2ModelUtil.setOtherElement(scriptDataMapperContainer,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_InputMappings(),
                            scriptDataMapper);

                } else if (MappingDirection.OUT.equals(direction)) {

                    Xpdl2ModelUtil.setOtherElement(scriptDataMapperContainer,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_OutputMappings(),
                            scriptDataMapper);

                }
            }
        }

        return scriptDataMapper;
    }

    /**
     * @see com.tibco.xpd.datamapper.scripts.IScriptDataMapperProvider#usesDataMapperGrammer(java.lang.Object)
     * 
     * @param contextInputObject
     * @return
     */
    @Override
    public boolean usesDataMapperGrammer(Object contextInputObject) {

        if (contextInputObject instanceof Activity) {

            return ScriptGrammarFactory.DATAMAPPER
                    .equals(ScriptGrammarFactory
                            .getGrammarToUse((Activity) contextInputObject,
                                    MappingDirection.IN.equals(direction) ? DirectionType.IN_LITERAL
                                            : DirectionType.OUT_LITERAL));

        }

        return false;
    }

    /**
     * @see com.tibco.xpd.datamapper.scripts.AbstractScriptDataMapperEditorProvider#getDataMapperDeselectedCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      java.lang.Object)
     * 
     * @param editingDomain
     * @param contextInputObject
     * @return
     */
    @Override
    public Command getDataMapperDeselectedCommand(EditingDomain editingDomain,
            Object contextInputObject) {
        /*
         * Remove the ScriptDatamapper (SubFlow/InputMappings | OutputMappings)
         * element completely.
         */
        OtherElementsContainer scriptDataMapperContainer =
                getScriptDataMapperContainer(contextInputObject);

        if (scriptDataMapperContainer != null) {
            ScriptDataMapper scriptDataMapper =
                    getScriptDataMapper(contextInputObject);

            if (scriptDataMapper != null) {
                if (MappingDirection.IN.equals(direction)) {
                    return Xpdl2ModelUtil
                            .getRemoveOtherElementCommand(editingDomain,
                                    scriptDataMapperContainer,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_InputMappings(),
                                    scriptDataMapper);

                } else if (MappingDirection.OUT.equals(direction)) {
                    return Xpdl2ModelUtil
                            .getRemoveOtherElementCommand(editingDomain,
                                    scriptDataMapperContainer,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_OutputMappings(),
                                    scriptDataMapper);
                }
            }
        }
        return null;
    }
}
