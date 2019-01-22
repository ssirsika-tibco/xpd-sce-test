/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.implementer.script;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.OtherAttributesContainer;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Message adapter for rest Service Task.
 *
 * @author jarciuch
 * @since 17 Apr 2015
 */
public class RestServiceTaskMessageAdapter extends
        BaseAbstractActivityMessageAdapter implements
        RestActivityMessageProvider {

    private TaskService getTaskService(Activity activity) {
        if (activity != null && activity.getImplementation() instanceof Task) {
            TaskService taskService =
                    ((Task) activity.getImplementation()).getTaskService();
            return taskService;
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSupported(Activity activity) {
        OtherAttributesContainer otherAttrContainer = getTaskService(activity);
        if (otherAttrContainer != null) {
            Object type =
                    Xpdl2ModelUtil.getOtherAttribute(otherAttrContainer,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ImplementationType());
            return RestActivityAdapterFactory.getRestServiceImplName()
                    .equals(type);
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Message getMessageIn(Activity activity) {
        TaskService taskService = getTaskService(activity);
        if (taskService != null) {
            return taskService.getMessageIn();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Message getMessageOut(Activity activity) {
        TaskService taskService = getTaskService(activity);
        if (taskService != null) {
            return taskService.getMessageOut();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImplementationType getImplementation(Activity activity) {
        return TaskObjectUtil.getTaskImplementationType(activity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Command getSetImplementationCommand(EditingDomain ed, Activity act,
            ImplementationType newImplType) {
        EObject taskTypeNode = getMessageContainer(act);
        return SetCommand.create(ed,
                taskTypeNode,
                Xpdl2Package.eINSTANCE.getTaskService_Implementation(),
                newImplType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EObject getMessageContainer(Activity activity) {
        return getTaskService(activity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasMappingIn(Activity act) {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasMappingOut(Activity act) {
        return true;
    }

}