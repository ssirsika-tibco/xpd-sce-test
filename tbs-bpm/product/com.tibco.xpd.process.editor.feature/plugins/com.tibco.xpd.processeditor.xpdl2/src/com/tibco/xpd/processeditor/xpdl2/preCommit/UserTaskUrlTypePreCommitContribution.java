/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.preCommit;

import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.FormImplementation;
import com.tibco.xpd.xpdExtension.FormImplementationType;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskUser;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.resources.IProcessPreCommitContributor;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * <p>
 * Currently (early studio 3.2) the bpmJspTask is the only place that defines
 * the url to the form and other teams (forms/N2) are relying on this - that are
 * out of synch (product release wise).
 * <p>
 * It is necessary to move to an underlying model whereby we can distinguish
 * between different types of form url specification (user defined, form or
 * pageflow).
 * <p>
 * Therefore the old bpmJspTask extended attribute is to be deprecated but must
 * still be maintained for a while - until other teams have caught up.
 * <p>
 * The forms feature also writes this attribute (because they contribute the
 * Generate Form / Use Existing form options in process editor.
 * <p>
 * In the new model the form url type and form url are stored in a new
 * FormImplementation element under TaskUser. So for now we will keep the form
 * url in extended attribute in synch with that in the
 * TaskUser/FormaImplementation.
 * <p>
 * This means - when the bpmJsp ext attr changes we will calculate the type from
 * the value. This covers for when forms feature changes the value directly.
 * <p>
 * When the FormImplementation form url is changed then we will add the
 * bpmJspTask ext attr. New property section etc will write only the new
 * FormImplementation, so we will keep deprecated bpmJspTask in synch.
 * 
 * 
 * @author aallway
 * @since 3.2
 */
public class UserTaskUrlTypePreCommitContribution implements
        IProcessPreCommitContributor {

    private boolean consoleLoggingOn = false;

    @Override
    public Command contributeCommand(ResourceSetChangeEvent event,
            Collection<ENotificationImpl> notifications)
            throws RollbackException {

        TransactionalEditingDomain editingDomain = event.getEditingDomain();

        CompoundCommand cmd = new CompoundCommand();

        for (ENotificationImpl n : notifications) {

            switch (n.getEventType()) {
            case ENotificationImpl.ADD:
                handleAddNotification(n, editingDomain, cmd);
                break;

            case ENotificationImpl.SET:
                handleSetNotification(n, editingDomain, cmd);
                break;

            case ENotificationImpl.REMOVE:
                handleRemoveNotification(n, editingDomain, cmd);
                break;
            }
        }

        if (!cmd.isEmpty()) {
            return cmd;
        }

        return null;
    }

    /**
     * If the given Remove object notification is a set for a bpmJspTask
     * extended attribute then append command to update the new extension
     * attributes.
     * 
     * @param n
     * @param editingDomain
     * @param cmd
     */
    private void handleRemoveNotification(ENotificationImpl n,
            TransactionalEditingDomain editingDomain, CompoundCommand cmd) {
        if (n.getNotifier() instanceof Activity
                && n.getOldValue() instanceof ExtendedAttribute) {

            ExtendedAttribute extAttr = (ExtendedAttribute) n.getOldValue();

            if (TaskObjectUtil.USER_TASK_ATTR.equals(extAttr.getName())) {
                logToConsole("BPMJSPTASK ExtendedAttribute Remove"); //$NON-NLS-1$

                cmd.append(new SynchFormImplementationWithBpmJspTaskCommand(
                        editingDomain, (Activity) n.getNotifier(), null));
            }

        } else if (n.getOldValue() instanceof TaskUser
                && n.getNotifier() instanceof EObject) {
            // When removing TaskUser feature remove the bpmJspTask ext attr.
            logToConsole("TaskUser Remove"); //$NON-NLS-1$

            cmd.append(new SynchBpmJspTaskWithFormImplementationCommand(
                    editingDomain, getActivityAncestor((EObject) n
                            .getNotifier()), null));

        } else if (n.getNotifier() instanceof TaskUser
                && n.getOldValue() instanceof FormImplementation) {
            // When removing FormImplementation from TaskUser remove the
            // bpmJspTask ext attr
            logToConsole("FormImplementation Remove"); //$NON-NLS-1$

            cmd.append(new SynchBpmJspTaskWithFormImplementationCommand(
                    editingDomain, getActivityAncestor((EObject) n
                            .getNotifier()), null));
        }

        return;
    }

    /**
     * If the given Set object notification is a set for a bpmJspTask extended
     * attribute then append command to update the new extension attributes.
     * 
     * @param n
     * @param editingDomain
     * @param cmd
     */
    private void handleSetNotification(ENotificationImpl n,
            TransactionalEditingDomain editingDomain, CompoundCommand cmd) {
        if (n.getNotifier() instanceof ExtendedAttribute) {
            ExtendedAttribute extAttr = (ExtendedAttribute) n.getNotifier();

            if (TaskObjectUtil.USER_TASK_ATTR.equals(extAttr.getName())) {
                logToConsole("BPMJSPTASK ExtendedAttribute Attribute set"); //$NON-NLS-1$

                cmd.append(new SynchFormImplementationWithBpmJspTaskCommand(
                        editingDomain, getActivityAncestor(extAttr), extAttr));
            }

        } else if (n.getNotifier() instanceof FormImplementation
                && n.getFeature() == XpdExtensionPackage.eINSTANCE
                        .getFormImplementation_FormURI()) {
            logToConsole("FormImplementyation/FormURI set. set"); //$NON-NLS-1$

            cmd.append(new SynchBpmJspTaskWithFormImplementationCommand(
                    editingDomain, getActivityAncestor((FormImplementation) n
                            .getNotifier()), (FormImplementation) n
                            .getNotifier()));

        } else if (n.getNotifier() instanceof Activity
                && n.getFeature() == Xpdl2Package.eINSTANCE
                        .getActivity_Implementation()) {
            if (n.getNewValue() instanceof Task
                    && ((Task) n.getNewValue()).getTaskUser() != null) {
                logToConsole("Implementation TaskUser. set"); //$NON-NLS-1$

                cmd.append(new SynchBpmJspTaskWithFormImplementationCommand(
                        editingDomain,
                        (Activity) n.getNotifier(),
                        (FormImplementation) Xpdl2ModelUtil.getOtherElement(((Task) n
                                .getNewValue()).getTaskUser(),
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_FormImplementation())));

            } else {
                logToConsole("Implementation set to something else."); //$NON-NLS-1$

                cmd.append(new SynchBpmJspTaskWithFormImplementationCommand(
                        editingDomain, (Activity) n.getNotifier(), null));
            }
        }

        return;
    }

    /**
     * If the given Add object notification is the add for a bpmJspTask extended
     * attribute then append command to update the new extension attributes.
     * 
     * @param n
     * @param editingDomain
     * @param cmd
     */
    private void handleAddNotification(ENotificationImpl n,
            TransactionalEditingDomain editingDomain, CompoundCommand cmd) {
        if (n.getFeature() == Xpdl2Package.eINSTANCE
                .getExtendedAttributesContainer_ExtendedAttributes()
                && n.getNotifier() instanceof Activity
                && n.getNewValue() instanceof ExtendedAttribute) {

            ExtendedAttribute extAttr = (ExtendedAttribute) n.getNewValue();

            if (TaskObjectUtil.USER_TASK_ATTR.equals(extAttr.getName())) {
                logToConsole("BPMJSPTASK ExtendedAttribute Added"); //$NON-NLS-1$

                cmd.append(new SynchFormImplementationWithBpmJspTaskCommand(
                        editingDomain, (Activity) n.getNotifier(), extAttr));
            }

        } else if (n.getNotifier() instanceof TaskUser
                && n.getNewValue() instanceof FormImplementation) {
            logToConsole("FormImplementation Added"); //$NON-NLS-1$

            cmd.append(new SynchBpmJspTaskWithFormImplementationCommand(
                    editingDomain, getActivityAncestor((TaskUser) n
                            .getNotifier()), (FormImplementation) n
                            .getNewValue()));

        } else if (n.getNotifier() instanceof FlowContainer
                && n.getNewValue() instanceof Activity) {
            Activity act = (Activity) n.getNewValue();
            if (act.getImplementation() instanceof Task
                    && ((Task) act.getImplementation()).getTaskUser() != null) {

                logToConsole("User Task Activity added"); //$NON-NLS-1$

                TaskUser taskUser =
                        ((Task) act.getImplementation()).getTaskUser();

                FormImplementation formImpl =
                        (FormImplementation) Xpdl2ModelUtil
                                .getOtherElement(taskUser,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_FormImplementation());
                if (formImpl != null) {
                    cmd.append(new SynchBpmJspTaskWithFormImplementationCommand(
                            editingDomain, act, formImpl));

                } else {
                    ExtendedAttribute extAttr =
                            TaskObjectUtil.getExtendedAttributeByName(act,
                                    TaskObjectUtil.USER_TASK_ATTR);
                    if (extAttr != null) {
                        cmd.append(new SynchFormImplementationWithBpmJspTaskCommand(
                                editingDomain, act, extAttr));
                    }
                }
            }
        }
        return;
    }

    private Activity getActivityAncestor(EObject eo) {
        while (eo != null) {
            if (eo instanceof Activity) {
                return (Activity) eo;
            }
            eo = eo.eContainer();
        }
        return null;
    }

    private void logToConsole(String msg) {
        if (consoleLoggingOn) {
            System.out.println(msg);
        }
    }

    /**
     * Command that only analyses and builds command oin execution (so that
     * model is analysed 'as is') - this prevents problems when multiple
     * notifications cause multiple synch's for same activity
     * 
     * @author aallway
     * @since 3.2
     */
    private class SynchBpmJspTaskWithFormImplementationCommand extends
            CompoundCommand {
        private EditingDomain editingDomain;

        private Activity act;

        private FormImplementation formImpl;

        public SynchBpmJspTaskWithFormImplementationCommand(
                EditingDomain editingDomain, Activity act,
                FormImplementation formImpl) {
            super();
            this.editingDomain = editingDomain;
            this.act = act;
            this.formImpl = formImpl;
        }

        @Override
        public boolean canExecute() {
            return true;
        }

        @Override
        public void execute() {
            Command cmd =
                    synchBpmJspTaskWithFormImplementationCommand(act,
                            formImpl,
                            editingDomain);
            if (cmd != null) {
                appendAndExecute(cmd);
            }

            return;
        }

        /**
         * Add command to Synchronise the bpmJspTask extended attribute with the
         * FormImplementation
         * 
         * @param formImpl
         *            FormImplementationElement or null to remove bpmJspTask
         * @param editingDomain
         * @param cmd
         */
        private Command synchBpmJspTaskWithFormImplementationCommand(
                Activity taskActivity, FormImplementation formImpl,
                EditingDomain editingDomain) {
            CompoundCommand cmd = new CompoundCommand();

            if (taskActivity != null) {
                ExtendedAttribute extAttr =
                        TaskObjectUtil.getExtendedAttributeByName(taskActivity,
                                TaskObjectUtil.USER_TASK_ATTR);

                if (formImpl == null) {
                    // Delete bpmJspTask extended attribute if it exists.
                    if (extAttr != null) {
                        logToConsole("  Removing bpmJspTask ext attr (" + taskActivity.getName() + ")"); //$NON-NLS-1$ //$NON-NLS-2$
                        cmd.append(RemoveCommand.create(editingDomain, extAttr));
                    }

                } else {
                    // Add / modify the bpmJspTask extended Attribute to match
                    // FormImplementation/FormURI.
                    if (extAttr == null) {
                        logToConsole("  Adding bpmJspTask ext attr (" + taskActivity.getName() + "): " + formImpl.getFormURI()); //$NON-NLS-1$ //$NON-NLS-2$

                        extAttr =
                                Xpdl2Factory.eINSTANCE
                                        .createExtendedAttribute();
                        extAttr.setName(TaskObjectUtil.USER_TASK_ATTR);

                        if (formImpl.getFormURI() != null) {
                            extAttr.setValue(formImpl.getFormURI());
                        }
                        cmd.append(AddCommand
                                .create(editingDomain,
                                        taskActivity,
                                        Xpdl2Package.eINSTANCE
                                                .getExtendedAttributesContainer_ExtendedAttributes(),
                                        extAttr));
                    } else {
                        logToConsole("  Setting bpmJspTask ext attr (" + taskActivity.getName() + "): " + formImpl.getFormURI()); //$NON-NLS-1$ //$NON-NLS-2$

                        String formUri =
                                formImpl.getFormURI() != null ? formImpl
                                        .getFormURI() : ""; //$NON-NLS-1$
                        cmd.append(SetCommand.create(editingDomain,
                                extAttr,
                                Xpdl2Package.eINSTANCE
                                        .getExtendedAttribute_Value(),
                                formUri));
                    }

                }

            }

            if (!cmd.isEmpty()) {
                return cmd;
            }
            return null;
        }
    }

    /**
     * Command that only analyses and builds command oin execution (so that
     * model is analysed 'as is') - this prevents problems when multiple
     * notifications cause multiple synch's for same activity
     * 
     * @author aallway
     * @since 3.2
     */
    private class SynchFormImplementationWithBpmJspTaskCommand extends
            CompoundCommand {
        private EditingDomain editingDomain;

        private Activity act;

        private ExtendedAttribute bpmJspTaskExtAttr;

        public SynchFormImplementationWithBpmJspTaskCommand(
                EditingDomain editingDomain, Activity act,
                ExtendedAttribute bpmJspTaskExtAttr) {
            super();
            this.editingDomain = editingDomain;
            this.act = act;
            this.bpmJspTaskExtAttr = bpmJspTaskExtAttr;
        }

        @Override
        public boolean canExecute() {
            return true;
        }

        @Override
        public void execute() {
            Command cmd =
                    synchFormImplementationWithBpmJspTaskCommand(act,
                            bpmJspTaskExtAttr,
                            editingDomain);
            if (cmd != null) {
                appendAndExecute(cmd);
            }

            return;
        }

        /**
         * Add command to Synchronise the FormImplementation with the bpmJspTask
         * extended attribute
         * 
         * @param extAttr
         *            bpmJspTask extended attribute or null if it was unset.
         * @param editingDomain
         * @param cmd
         */
        private Command synchFormImplementationWithBpmJspTaskCommand(
                Activity taskActivity, ExtendedAttribute extAttr,
                EditingDomain editingDomain) {
            CompoundCommand cmd = new CompoundCommand();
            if (taskActivity != null) {
                TaskUser taskUser = null;
                if (taskActivity.getImplementation() instanceof Task) {
                    Task task = (Task) taskActivity.getImplementation();

                    taskUser = task.getTaskUser();
                }

                if (taskUser != null) {
                    FormImplementation formImpl =
                            (FormImplementation) Xpdl2ModelUtil
                                    .getOtherElement(taskUser,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_FormImplementation());

                    if (extAttr == null) {
                        // Extended Attribute removed - if TaskUser still has
                        // FormImplementation then remove it.
                        if (formImpl != null) {
                            logToConsole("  Removing FormImplementation (" + taskActivity.getName() + ")"); //$NON-NLS-1$ //$NON-NLS-2$

                            cmd.append(RemoveCommand.create(editingDomain,
                                    formImpl));
                        }
                    } else {
                        // Work out (well guess really) the Form Implementation
                        // type
                        // from the URL.
                        String extendedAttributeFormUriValue = ""; //$NON-NLS-1$

                        if (extAttr.getValue() != null) {
                            extendedAttributeFormUriValue = extAttr.getValue();
                        }

                        if (formImpl != null && formImpl.getFormURI() != null
                                && !formImpl.getFormURI().isEmpty()) {
                            /*
                             * XPD-6874: If the Form Uri and the extended
                             * attribute value are already in sync then we dont
                             * need to do anything, hence return immediately.
                             */
                            if (formImpl.getFormURI()
                                    .equals(extendedAttributeFormUriValue)) {
                                return null;
                            }
                        }

                        FormImplementationType formType =
                                FormImplementationType.USER_DEFINED;
                        if (extendedAttributeFormUriValue
                                .startsWith(TaskObjectUtil.FORM_SCHEMA)) {

                            formType = FormImplementationType.FORM;

                        } else {
                            /*
                             * Check for Pageflow Form
                             */
                            /*
                             * XPD-6874: We were not dealing correctly with the
                             * pageflow over here because the pageflow is not a
                             * platform uri, it is simply the special folder
                             * relative path followed by the pageflow id.
                             * Confirm this with Sid...
                             */
                            URI uri =
                                    URI.createURI(extendedAttributeFormUriValue);

                            String path = uri.path();
                            String id = uri.fragment();
                            IProject project =
                                    WorkingCopyUtil.getProjectFor(taskActivity);
                            Process uriProcess = null;

                            if (project != null && path != null
                                    && path.length() > 0 && id != null
                                    && id.length() > 0) {

                                /*
                                 * Find the file in this project and then get
                                 * the pro9cess from that file.
                                 */
                                IFile pageflowProcessFile =
                                        SpecialFolderUtil
                                                .resolveSpecialFolderRelativePath(project,
                                                        Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND,
                                                        path,
                                                        true);

                                if (pageflowProcessFile != null
                                        && pageflowProcessFile.exists()) {

                                    WorkingCopy wc =
                                            WorkingCopyUtil
                                                    .getWorkingCopy(pageflowProcessFile);

                                    if (wc.getRootElement() instanceof Package) {
                                        uriProcess =
                                                ((Package) wc.getRootElement())
                                                        .getProcess(id);
                                    }
                                }
                            }

                            if (uriProcess != null
                                    && Xpdl2ModelUtil.isPageflow(uriProcess)) {
                                /*
                                 * found the referenced pageflow
                                 */
                                formType = FormImplementationType.PAGEFLOW;
                            }

                        }

                        if (formImpl == null) {
                            logToConsole("  Adding FormImplementation (" + taskActivity.getName() + "): " + formType + " - " + extendedAttributeFormUriValue); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

                            formImpl =
                                    XpdExtensionFactory.eINSTANCE
                                            .createFormImplementation();
                            formImpl.setFormType(formType);
                            formImpl.setFormURI(extendedAttributeFormUriValue);

                            cmd.append(Xpdl2ModelUtil
                                    .getSetOtherElementCommand(editingDomain,
                                            taskUser,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_FormImplementation(),
                                            formImpl));

                        } else {
                            if (!formType.equals(formImpl.getFormType())
                                    || !extendedAttributeFormUriValue
                                            .equals(formImpl.getFormURI())) {
                                logToConsole("  Setting FormImplementation (" + taskActivity.getName() + "): " + formType + " - " + extendedAttributeFormUriValue); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

                                cmd.append(SetCommand
                                        .create(editingDomain,
                                                formImpl,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getFormImplementation_FormType(),
                                                formType));
                                cmd.append(SetCommand
                                        .create(editingDomain,
                                                formImpl,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getFormImplementation_FormURI(),
                                                extendedAttributeFormUriValue));
                            }
                        }

                    }
                }
            }

            if (!cmd.isEmpty()) {
                return cmd;
            }
            return null;
        }
    }
}
