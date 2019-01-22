/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.actions;

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
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeReference;
import com.tibco.xpd.xpdExtension.CaseAccessOperationsType;
import com.tibco.xpd.xpdExtension.CreateCaseOperationType;
import com.tibco.xpd.xpdExtension.GlobalDataOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.commands.AbstractLateExecuteCommand;
import com.tibco.xpd.xpdl2.edit.ui.ComplexDataUIUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Implementation class that does the additional stuff required for create case
 * data generated business service after the base process creation is done.
 * 
 * <strong>
 * <p>
 * This class basically</strong>
 * <p>
 * 1. generates data fields and adds project references (if not set) as the data
 * fields refer to bom types from different project
 * <p>
 * 2. creates the case data for global data operation
 * 
 * @author bharge
 * @since 23 Jan 2014
 */
public class CreateCaseDataProcessCreator extends AbstractCaseProcessCreator {

    /**
     * @param rootCategoryId
     * @param defaultFragmentId
     * @param isBusinessService
     */
    public CreateCaseDataProcessCreator(String rootCategoryId,
            String defaultFragmentId, ProcessWidgetType processType) {

        super(rootCategoryId, defaultFragmentId, processType);
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.actions.AbstractProcessCreator#postProcessCreate(com.tibco.xpd.xpdl2.Process,
     *      org.eclipse.emf.edit.domain.EditingDomain,
     *      com.tibco.xpd.xpdl2.Package)
     * 
     * @param process
     * @param editingDomain
     * @param xpdlPackage
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
     * 
     * @author bharge
     * @since 13 Aug 2014
     */
    private class PostProcessCommand extends AbstractLateExecuteCommand {

        private Process process;

        private Package xpdlPackage;

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

                CompoundCommand cmpdCmd = new CompoundCommand();

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

                        cmpdCmd.append(projectReferenceCommand);
                    }
                }

                /*
                 * Fragment has bom type and case ref type fields created with
                 * no appropriate class type set (obviously) and no appropriate
                 * name. Need to set the types and names to the selected case
                 * class
                 */
                setBomOrCaseRefType(process, xpdlPackage);

                /* update global data task with create case data operation */
                CreateCaseDataCommand createCaseDataCmd =
                        new CreateCaseDataCommand(editingDomain, process,
                                xpdlPackage);
                if (null != createCaseDataCmd) {

                    cmpdCmd.append(createCaseDataCmd);
                }

                return cmpdCmd;
            }

            return null;
        }

    }

    /**
     * Late execute command class to update global task data with create case
     * data operation
     * 
     * @author bharge
     * @since 23 Jan 2014
     */
    private class CreateCaseDataCommand extends AbstractLateExecuteCommand {

        Process process;

        Package xpdlPackage;

        /**
         * @param editingDomain
         * @param process
         * @param xpdlPackage
         * @param process2
         */
        public CreateCaseDataCommand(EditingDomain editingDomain,
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

                    CaseAccessOperationsType caseAccessOperations =
                            globalDataOp.getCaseAccessOperations();

                    ExternalReference externalReference =
                            Xpdl2Factory.eINSTANCE.createExternalReference();

                    Class caseClass = getCaseClass();
                    ComplexDataTypeReference complexDataTypeReference =
                            ComplexDataUIUtil
                                    .resolveSelectionToReference(caseClass,
                                            WorkingCopyUtil
                                                    .getProjectFor(xpdlPackage),
                                            getComplexTypesInfo());

                    if (complexDataTypeReference != null) {

                        externalReference.setLocation(complexDataTypeReference
                                .getLocation());
                        externalReference.setNamespace(complexDataTypeReference
                                .getNameSpace());
                        externalReference.setXref(complexDataTypeReference
                                .getXRef());
                    }

                    CreateCaseOperationType createType =
                            caseAccessOperations.getCreate();
                    String caseRefField = caseClass.getName() + "Ref"; //$NON-NLS-1$
                    createType.setFromFieldPath(caseClass.getName());
                    createType.setToCaseRefField(caseRefField);

                    cmd.append(SetCommand
                            .create(editingDomain,
                                    caseAccessOperations,
                                    XpdExtensionPackage.eINSTANCE
                                            .getCaseAccessOperationsType_CaseClassExternalReference(),
                                    externalReference));

                    cmd.append(SetCommand.create(editingDomain,
                            caseAccessOperations,
                            XpdExtensionPackage.eINSTANCE
                                    .getCaseAccessOperationsType_Create(),
                            createType));
                }

                return cmd;
            }
            return null;
        }

    }

}
