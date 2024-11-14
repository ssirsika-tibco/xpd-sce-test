/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.datamapper;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.datamapper.scripts.AbstractScriptDataMapperEditorProvider;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskSend;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Script Data Mapper provider for all REST Invocation activities and mapping
 * directions.
 * 
 * @author nwilson
 * @since 30 Apr 2015
 */
public class RestScriptDataMapperProvider extends
        AbstractScriptDataMapperEditorProvider {
    private MappingDirection direction;

	// public RestScriptDataMapperProvider(MappingDirection direction) {
	// this(direction, MappingDirection.IN.equals(direction) ? RestDataMapperConstants.PROCESS_TO_REST_SERVICE
	// : RestDataMapperConstants.REST_SERVICE_TO_PROCESS);
	// }

	public RestScriptDataMapperProvider(MappingDirection direction, String mapperContext)
	{
		super(mapperContext,
				MappingDirection.IN.equals(direction) ? DirectionType.IN_LITERAL : DirectionType.OUT_LITERAL);
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
                Object other =
                        Xpdl2ModelUtil.getOtherElement(oec,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ScriptDataMapper());
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
                Task task = (Task) impl;
                TaskService taskService = task.getTaskService();
                if (taskService != null) {
                    if (MappingDirection.IN.equals(direction)) {
                        oec = taskService.getMessageIn();
                    } else {
                        oec = taskService.getMessageOut();
                    }
                }
                TaskSend taskSend = task.getTaskSend();
                if (taskSend != null) {
                    if (MappingDirection.IN.equals(direction)) {
                        oec = taskSend.getMessage();
                    }
                }
            }
            Event event = activity.getEvent();
            if (event instanceof IntermediateEvent) {
                IntermediateEvent ie = (IntermediateEvent) event;
                ResultError re = ie.getResultError();
                if (MappingDirection.OUT.equals(direction) && re != null) {
                    oec = re;
                }
                TriggerResultMessage trm = ie.getTriggerResultMessage();
                if (MappingDirection.IN.equals(direction) && trm != null) {
                    oec = trm.getMessage();
                }
            }
            /* Sid XPD-7359 handle End Event as well. */
            else if (event instanceof EndEvent) {
                EndEvent endEvent = (EndEvent) event;

                TriggerResultMessage trm = endEvent.getTriggerResultMessage();
                if (MappingDirection.IN.equals(direction) && trm != null) {
                    oec = trm.getMessage();
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

        /*
         * XPD-7820: If command available then append to command, if
         * contextInputObject is not attached to model as yet then set the
         * element directly.
         */
        if (scriptDataMapperContainer != null) {
            scriptDataMapper =
                    XpdExtensionFactory.eINSTANCE.createScriptDataMapper();

            if (optionalCreationCommand != null) {
                optionalCreationCommand.append(Xpdl2ModelUtil
                        .getSetOtherElementCommand(editingDomain,
                                scriptDataMapperContainer,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ScriptDataMapper(),
                                scriptDataMapper));
            } else {
                Xpdl2ModelUtil.setOtherElement(scriptDataMapperContainer,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ScriptDataMapper(),
                        scriptDataMapper);
            }
        }

        return scriptDataMapper;
    }

    /**
     * @see com.tibco.xpd.datamapper.scripts.IScriptDataMapperProvider#usesDataMapperGrammer(java.lang.Object)
     * 
     * @param contextInputObject
     * @return true, REST data mapper only uses DataMapper grammar.
     */
    @Override
    public boolean usesDataMapperGrammer(Object contextInputObject) {
        return true;
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
         * This mapping scenario does not allow grammar selection, so never need
         * to do any clean up when different grammar selected.
         */
        return null;
    }

}
