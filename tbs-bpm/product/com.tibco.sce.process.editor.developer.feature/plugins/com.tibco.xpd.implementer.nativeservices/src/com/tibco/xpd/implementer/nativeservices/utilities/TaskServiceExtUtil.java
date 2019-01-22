/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.utilities;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Utility class that provides methods to get the <code>TaskService</code>
 * extended model stored in the <b>##OTHER</b>.
 * 
 * @author njpatel
 */
public final class TaskServiceExtUtil {

    /**
     * Get the given extended model from the <code>TaskService</code> object.
     * This assumes that there will be only one extended model in the <b>##OTHER</b>
     * of the <code>TaskService</code> object.
     * 
     * @param taskService -
     *            Get extended model from this object
     * @param documentRootRef -
     *            The extended model's document root reference
     * @return Extended model <code>EObject</code> if found, <b>null</b>
     *         otherwise.
     */
    public static EObject getExtendedModel(TaskService taskService,
            EReference documentRootRef) {
        EObject model = null;

        if (taskService != null && documentRootRef != null) {
            // Get email model
            model = taskService.getOtherElement(documentRootRef.getName());

        } else {
            throw new NullPointerException(
                    "Parameter to getExtendedModel is null."); //$NON-NLS-1$
        }

        return model;
    }

    public static CompoundCommand addExtendedModel(EditingDomain ed,
            TaskService taskService, EReference documentRootRef, EObject model) {

        CompoundCommand cmd = null;

        if (ed != null && taskService != null && documentRootRef != null
                && model != null) {

            cmd = new CompoundCommand();

            // Set the implementation to OTHER
            cmd.append(SetCommand.create(ed, taskService,
                    Xpdl2Package.eINSTANCE.getTaskService_Implementation(),
                    ImplementationType.OTHER_LITERAL));

            // Add the extended model to ##OTHER
            
            cmd.append(Xpdl2ModelUtil.getSetOtherElementCommand(ed, taskService, documentRootRef, model));

        } else {
            throw new NullPointerException(
                    "Parameter to addExtendedModel is null."); //$NON-NLS-1$
        }

        return cmd;
    }
}
