/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.actions;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.resources.util.SetProjectReferenceCommand;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.CaseReferenceOperationsType;
import com.tibco.xpd.xpdExtension.GlobalDataOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.commands.AbstractLateExecuteCommand;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Implementation class that does the additional stuff required for update case
 * data generated business service after the base process creation is done.
 * 
 * <strong>
 * <p>
 * This class basically</strong>
 * <p>
 * 1. generates data fields and adds project references (if not set) as the data
 * fields refer to bom types from different project
 * <p>
 * 2. generates scripts in script task and conditional gateway
 * <p>
 * 3. adds/removes required/un-required data fields as associated parameters to
 * the user tasks
 * <p>
 * 4. removes the extended attributes from page activities that get copied from
 * fragment process
 * <p>
 * 5. updates the case data for global data operation
 * 
 * @author bharge
 * @since 6 Jan 2014
 */
public class UpdateCaseDataProcessCreator extends AbstractCaseProcessCreator {

    /**
     * 
     * @param rootCategoryId
     * @param defaultFragmentId
     * @param processType
     */
    public UpdateCaseDataProcessCreator(String rootCategoryId,
            String defaultFragmentId, ProcessWidgetType processType) {

        super(rootCategoryId, defaultFragmentId, processType);
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.actions.AbstractProcessCreator#postProcessCreate(com.tibco.xpd.xpdl2.Process)
     * 
     * @param process
     * @return
     */
    @Override
    protected Command postProcessCreate(Process process,
            EditingDomain editingDomain, Package xpdlPackage) {

        return new PostProcessCommand(editingDomain, process, xpdlPackage);

    }

    /**
     * Post Process Late execute command class that consolidates all commands to
     * be executed at the end of the process creation
     * 
     * @author bharge
     * @since 13 Aug 2014
     */
    private class PostProcessCommand extends AbstractLateExecuteCommand {

        Process process;

        Package xpdlPackage;

        /**
         * @param editingDomain
         * @param contextObject
         */
        public PostProcessCommand(EditingDomain editingDomain,
                Object contextObject, Package xpdlPackage) {

            super(editingDomain, contextObject);
            if (contextObject instanceof Process) {

                this.process = (Process) contextObject;
                this.xpdlPackage = xpdlPackage;
            }
        }

        /**
         * @see com.tibco.xpd.xpdl2.commands.AbstractLateExecuteCommand#createCommand(org.eclipse.emf.edit.domain.EditingDomain,
         *      java.lang.Object)
         * 
         * @param editingDomain
         * @param contextObject
         * @return
         */
        @Override
        protected Command createCommand(EditingDomain editingDomain,
                Object contextObject) {

            if (null != process) {

                CompoundCommand cmd = new CompoundCommand();

                IProject xpdlProject =
                        WorkingCopyUtil.getProjectFor(xpdlPackage);
                IFile bomFile = WorkingCopyUtil.getFile(getCaseClass());
                IProject bomProject = bomFile.getProject();

                /* set the project references */
                if (bomProject != xpdlProject) {

                    SetProjectReferenceCommand projectReferenceCommand =
                            new SetProjectReferenceCommand(xpdlProject,
                                    bomProject);
                    /*
                     * if the project reference is already set then this command
                     * doesn't get prepared. so if we append it to the compound
                     * command without checking if it is executable, other
                     * commands that get appended later fail to execute
                     */
                    if (projectReferenceCommand.canExecute()) {

                        cmd.append(projectReferenceCommand);
                    }
                }

                /*
                 * Fragment has bom type and case ref type fields created with
                 * no appropriate class type set (obviously) and no appropriate
                 * name. Need to set the types and names to the selected case
                 * class
                 */
                setBomOrCaseRefType(process, xpdlPackage);

                /*
                 * Basic types for case identifiers in the case class are
                 * needed. Create them here
                 */
                List<DataField> basicTypes = createBasicTypes();
                process.getDataFields().addAll(basicTypes);

                /* add script to the script task */
                ScriptTaskScriptCommand scriptTaskScriptCommand =
                        new ScriptTaskScriptCommand(editingDomain, process);
                if (null != scriptTaskScriptCommand) {

                    cmd.append(scriptTaskScriptCommand);
                }

                /* add script to the conditional gateway */
                TransitionScriptCommand transitionScriptCommand =
                        new TransitionScriptCommand(editingDomain, process);
                if (null != transitionScriptCommand) {

                    cmd.append(transitionScriptCommand);
                }

                /* Add associated parameters for Identifier task */
                AddFragmentActivityAssociatedParamsCommand associatedParamsCommand =
                        new AddFragmentActivityAssociatedParamsCommand(
                                editingDomain, process);
                if (null != associatedParamsCommand) {

                    cmd.append(associatedParamsCommand);
                }

                /* Delete the data field that comes from the fragment process */
                DeleteCaseIdDataField deleteDataFieldCmd =
                        new DeleteCaseIdDataField(editingDomain, process);
                if (null != deleteDataFieldCmd) {

                    cmd.append(deleteDataFieldCmd);
                }

                /* update global data task to update case ref */
                UpdateCaseDataCommand updateCaseDataCommand =
                        new UpdateCaseDataCommand(editingDomain, process);
                if (null != updateCaseDataCommand) {

                    cmd.append(updateCaseDataCommand);
                }

                return cmd;
            }
            return null;
        }
    }

    /**
     * Late execute command class to update global data with case ref field
     * information
     * 
     * @author bharge
     * @since 10 Jan 2014
     */
    private class UpdateCaseDataCommand extends AbstractLateExecuteCommand {

        Process process;

        /**
         * @param editingDomain
         * @param contextObject
         */
        public UpdateCaseDataCommand(EditingDomain editingDomain,
                Object contextObject) {

            super(editingDomain, contextObject);
            if (contextObject instanceof Process) {

                this.process = (Process) contextObject;
            }
        }

        /**
         * @see com.tibco.xpd.xpdl2.commands.AbstractLateExecuteCommand#createCommand(org.eclipse.emf.edit.domain.EditingDomain,
         *      java.lang.Object)
         * 
         * @param editingDomain
         * @param contextObject
         * @return
         */
        @Override
        protected Command createCommand(EditingDomain editingDomain,
                Object contextObject) {

            if (null != process) {

                CompoundCommand cmd = new CompoundCommand();
                GlobalDataOperation globalDataOp = null;

                EList<Activity> activities = process.getActivities();

                for (Activity activity : activities) {

                    Implementation implementation =
                            activity.getImplementation();
                    if (implementation instanceof Task) {

                        Task task = (Task) implementation;
                        TaskService taskService = task.getTaskService();
                        if (null != taskService) {

                            if (null != taskService) {

                                Object otherElement =
                                        Xpdl2ModelUtil
                                                .getOtherElement(taskService,
                                                        XpdExtensionPackage.eINSTANCE
                                                                .getDocumentRoot_GlobalDataOperation());
                                if (otherElement instanceof GlobalDataOperation) {

                                    globalDataOp =
                                            (GlobalDataOperation) otherElement;
                                    break;
                                }
                            }
                        }
                    }
                }

                if (null != globalDataOp) {

                    CaseReferenceOperationsType caseReferenceOperations =
                            globalDataOp.getCaseReferenceOperations();
                    /* set case ref field name */
                    Class caseClass = getCaseClass();
                    String caseRefField = caseClass.getName() + "Ref"; //$NON-NLS-1$
                    cmd.setLabel("Set Case Reference Field"); //$NON-NLS-1$
                    cmd.append(SetCommand
                            .create(getEditingDomain(),
                                    caseReferenceOperations,
                                    XpdExtensionPackage.eINSTANCE
                                            .getCaseReferenceOperationsType_CaseRefField(),
                                    caseRefField));

                    /* set bom type field */
                    cmd.setLabel("Set Local Data in Update Operation"); //$NON-NLS-1$
                    cmd.append(SetCommand.create(getEditingDomain(),
                            caseReferenceOperations.getUpdate(),
                            XpdExtensionPackage.eINSTANCE
                                    .getUpdateCaseOperationType_FromFieldPath(),
                            caseClass.getName()));
                }

                return cmd;
            }
            return null;
        }
    }

}
