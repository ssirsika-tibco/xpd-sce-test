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
import com.tibco.xpd.xpdl2.TaskSend;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Message adapter for the REST Send Task.
 *
 * @author jarciuch
 * @since 17 Apr 2015
 */
public class RestSendTaskMessageAdapter extends
        BaseAbstractActivityMessageAdapter implements
        RestActivityMessageProvider {

    private TaskSend getTaskSend(Activity activity) {
        if (activity != null && activity.getImplementation() instanceof Task) {
            TaskSend taskSend =
                    ((Task) activity.getImplementation()).getTaskSend();
            return taskSend;
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSupported(Activity activity) {
        OtherAttributesContainer otherAttrContainer = getTaskSend(activity);
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
        TaskSend taskSend = getTaskSend(activity);
        if (taskSend != null) {
            return taskSend.getMessage();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Message getMessageOut(Activity activity) {
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
                Xpdl2Package.eINSTANCE.getTaskSend_Implementation(),
                newImplType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EObject getMessageContainer(Activity activity) {
        return getTaskSend(activity);
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
        return false;
    }

}