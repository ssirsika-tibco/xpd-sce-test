/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.wizards;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.resources.util.SetProjectReferenceCommand;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeExtPointHelper;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeReference;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypesMergedInfo;
import com.tibco.xpd.xpdExtension.CaseReferenceOperationsType;
import com.tibco.xpd.xpdExtension.CaseService;
import com.tibco.xpd.xpdExtension.GlobalDataOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Condition;
import com.tibco.xpd.xpdl2.ConditionType;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Member;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskScript;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.commands.AbstractLateExecuteCommand;
import com.tibco.xpd.xpdl2.edit.ui.ComplexDataUIUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Post Process Late execute command class that consolidates all commands to be
 * executed at the end of the process creation for a case service
 * 
 * <p>
 * This command basically
 * <p>
 * 1. sets/resolves the bom type data fields to the referenced bom class
 * <p>
 * 2. sets/resolves the case reference formal parameter to the referenced case
 * class
 * <p>
 * 3. updates the script in the script task that gets copied from the fragment
 * <p>
 * 4. updates the global data task to reference the case class
 * 
 * 
 * @author bharge
 * @since 3 Sep 2014
 */
public class CaseServicePostProcessCommand extends AbstractLateExecuteCommand {

    Process process;

    Package xpdlPackage;

    IProject project;

    Class caseClass;

    /**
     * @param editingDomain
     * @param contextObject
     * @param xpdlPackage
     * @param caseClass
     */
    public CaseServicePostProcessCommand(EditingDomain editingDomain,
            Object contextObject, Package xpdlPackage, Class caseClass) {

        super(editingDomain, contextObject);
        if (contextObject instanceof Process) {

            this.process = (Process) contextObject;
            this.xpdlPackage = xpdlPackage;
            this.project = WorkingCopyUtil.getProjectFor(xpdlPackage);
            this.caseClass = caseClass;
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

            CaseService caseService =
                    (CaseService) Xpdl2ModelUtil.getOtherElement(process,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_CaseService());
            /* create case service and add to the process */
            if (null == caseService) {

                caseService = XpdExtensionFactory.eINSTANCE.createCaseService();
                ExternalReference caseClassExtRef =
                        createCaseExternalRefForCaseService(xpdlPackage);

                caseService.setCaseClassType(caseClassExtRef);
                cmd.append(Xpdl2ModelUtil
                        .getSetOtherElementCommand(editingDomain,
                                process,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_CaseService(),
                                caseService));
            }

            if (null != caseClass) {

                IProject xpdlProject =
                        WorkingCopyUtil.getProjectFor(xpdlPackage);
                IFile bomFile = WorkingCopyUtil.getFile(caseClass);
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
                setBomOrCaseRefTypeDataFields(process, xpdlPackage);

                /*
                 * View/Update Case Service template has case ref type formal
                 * parameter. Need to set the type and name to the selected case
                 * class
                 */
                setCaseRefTypeParameters(process, xpdlPackage);

                /* add script to the script task */
                CaseServiceScriptTaskCommand scriptTaskScriptCommand =
                        new CaseServiceScriptTaskCommand(editingDomain, process);

                if (null != scriptTaskScriptCommand) {

                    cmd.append(scriptTaskScriptCommand);
                }

                /* update global data task to update case ref */
                UpdateCaseDataCommand updateCaseDataCommand =
                        new UpdateCaseDataCommand(editingDomain, process);
                if (null != updateCaseDataCommand) {

                    cmd.append(updateCaseDataCommand);
                }

            }
            return cmd;
        }
        return null;
    }

    /**
     * @param xpdlPackage
     * @return creates and returns external reference to the case class
     */
    private ExternalReference createCaseExternalRefForCaseService(
            Package xpdlPackage) {

        ExternalReference externalRef =
                Xpdl2Factory.eINSTANCE.createExternalReference();

        ComplexDataTypeReference complexDataTypeReference =
                ComplexDataUIUtil.resolveSelectionToReference(caseClass,
                        WorkingCopyUtil.getProjectFor(xpdlPackage),
                        getComplexTypesInfo());

        if (complexDataTypeReference != null) {

            externalRef.setLocation(complexDataTypeReference.getLocation());
            externalRef.setNamespace(complexDataTypeReference.getNameSpace());
            externalRef.setXref(complexDataTypeReference.getXRef());
        }

        return externalRef;
    }

    /**
     * iterates thru the process formal parameters and resolves case ref type
     * parameter(s) to the referenced case class
     * 
     * @param process
     * @param xpdlPackage
     */
    private void setCaseRefTypeParameters(Process process, Package xpdlPackage) {

        ComplexDataTypeReference complexDataTypeReference =
                ComplexDataUIUtil.resolveSelectionToReference(caseClass,
                        project,
                        getComplexTypesInfo());
        ExternalReference externalReference = null;

        for (FormalParameter parameter : process.getFormalParameters()) {

            if (parameter.getDataType() instanceof RecordType) {

                parameter.setName(caseClass.getName() + "Ref"); //$NON-NLS-1$
                Xpdl2ModelUtil.setOtherAttribute(parameter,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_DisplayName(),
                        caseClass.getLabel() + "Ref"); //$NON-NLS-1$

                RecordType recordType = (RecordType) parameter.getDataType();
                Member member = recordType.getMember().get(0);
                externalReference = member.getExternalReference();
            }
            if (null != externalReference && complexDataTypeReference != null) {

                externalReference.setLocation(complexDataTypeReference
                        .getLocation());
                externalReference.setNamespace(complexDataTypeReference
                        .getNameSpace());
                externalReference.setXref(complexDataTypeReference.getXRef());
            }
        }
    }

    /**
     * iterates thru the process data fields and resolves the bom type data
     * field(s) to the referenced bom class
     * 
     * @param process
     * @param xpdlPackage
     */
    private void setBomOrCaseRefTypeDataFields(Process process,
            Package xpdlPackage) {

        ComplexDataTypeReference complexDataTypeReference =
                ComplexDataUIUtil.resolveSelectionToReference(caseClass,
                        project,
                        getComplexTypesInfo());
        ExternalReference externalReference = null;

        for (DataField dataField : process.getDataFields()) {

            if (dataField.getDataType() instanceof ExternalReference) {

                dataField.setName(caseClass.getName());
                Xpdl2ModelUtil.setOtherAttribute(dataField,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_DisplayName(),
                        caseClass.getLabel());
                externalReference = (ExternalReference) dataField.getDataType();
            }
            if (null != externalReference && complexDataTypeReference != null) {

                externalReference.setLocation(complexDataTypeReference
                        .getLocation());
                externalReference.setNamespace(complexDataTypeReference
                        .getNameSpace());
                externalReference.setXref(complexDataTypeReference.getXRef());
            }
        }
    }

    /**
     * 
     * @return ComplexDataTypesMergedInfo
     */
    private ComplexDataTypesMergedInfo getComplexTypesInfo() {

        ComplexDataTypesMergedInfo _complexTypesInfo = null;
        if (_complexTypesInfo == null) {

            _complexTypesInfo =
                    ComplexDataTypeExtPointHelper
                            .getAllComplexDataTypesMergedInfo();
        }
        return _complexTypesInfo;
    }

    /**
     * Late execute command class to add script in the new case service process,
     * that get copied from the fragment process
     * 
     * @author bharge
     * @since 3 Sep 2014
     */
    private class CaseServiceScriptTaskCommand extends
            AbstractLateExecuteCommand {

        Process process;

        /**
         * @param editingDomain
         * @param contextObject
         * @param caseClass
         */
        public CaseServiceScriptTaskCommand(EditingDomain editingDomain,
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

                EList<Activity> activities = process.getActivities();
                CompoundCommand cmd = new CompoundCommand();

                for (Activity activity : activities) {

                    Implementation implementation =
                            activity.getImplementation();
                    if (implementation instanceof Task) {

                        Task task = (Task) implementation;
                        TaskScript taskScript = task.getTaskScript();
                        if (null != taskScript) {

                            String newScript = getScriptTaskScript();
                            String scriptGrammar =
                                    taskScript.getScript().getScriptGrammar();
                            Expression newExpr =
                                    Xpdl2Factory.eINSTANCE.createExpression();
                            newExpr.setScriptGrammar(scriptGrammar);
                            newExpr.getMixed()
                                    .add(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                                            newScript);
                            cmd.append(SetCommand.create(editingDomain,
                                    taskScript,
                                    Xpdl2Package.eINSTANCE
                                            .getTaskScript_Script(),
                                    newExpr));
                        }
                    }
                }

                cmd.append(new ConditionalExpressionScriptCommand(editingDomain, process));

                return cmd;
            }
            return null;
        }

        /**
         * 
         * @return String - script for script task
         */
        private String getScriptTaskScript() {

            StringBuilder scriptBuilder = new StringBuilder();
            /* if (caseRef != null) { */
            getFirstine(scriptBuilder);

            scriptBuilder.append("\n"); //$NON-NLS-1$
            /* casebomtype = caseRef.readCase(); */
            getSecondLine(scriptBuilder);

            return scriptBuilder.toString();
        }

        /**
         * if (custRef != null) {
         * 
         * @param sb
         *            - script string for second line in the script
         */
        private void getFirstine(StringBuilder sb) {

            /* caseClassName + Ref - caseRefType field name */
            String refName = caseClass.getName() + "Ref"; //$NON-NLS-1$
            sb.append("if (data."); //$NON-NLS-1$
            sb.append(refName);
            sb.append(" != null) {"); //$NON-NLS-1$
        }

        /**
         * custBomType = custRef.readCustomer();
         * 
         * @param sb
         *            - script string for third line in the script
         */
        private void getSecondLine(StringBuilder sb) {

            String refName = "data." + caseClass.getName() + "Ref"; //$NON-NLS-1$ //$NON-NLS-2$
            sb.append("data." + caseClass.getName() + " = "); //$NON-NLS-1$ //$NON-NLS-2$
            sb.append("bpm.caseData.read" + "("); //$NON-NLS-1$ //$NON-NLS-2$
            sb.append(refName);
            sb.append(");"); //$NON-NLS-1$
            sb.append("\n"); //$NON-NLS-1$
            sb.append("}"); //$NON-NLS-1$
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

    /**
     * Command to generate the script for a conditional transition in a case
     * action.
     *
     * @author sajain
     * @since Aug 9, 2019
     */
    class ConditionalExpressionScriptCommand extends AbstractLateExecuteCommand {

        Process process;

        /**
         * @param editingDomain
         * @param contextObject
         */
        private ConditionalExpressionScriptCommand(EditingDomain editingDomain, Object contextObject) {

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
        protected Command createCommand(EditingDomain editingDomain, Object contextObject) {

            if (null != process) {
                EList<Transition> transitions = process.getTransitions();
                CompoundCommand cmd = new CompoundCommand();

                Condition oldCondition = null;
                Transition transition = null;
                for (Transition trans : transitions) {
                    if (null != trans.getCondition() && ConditionType.CONDITION_LITERAL.equals(trans.getCondition().getType())) {
                        transition = trans;
                        oldCondition = trans.getCondition();
                        break;
                    }
                }
                String newScript = getTransitionScript();

                if (null != oldCondition) {

                    String scriptGrammar = oldCondition.getExpression().getScriptGrammar();
                    Condition newCondition = Xpdl2Factory.eINSTANCE.createCondition();
                    newCondition.setType(ConditionType.CONDITION_LITERAL);
                    Expression expression = Xpdl2Factory.eINSTANCE.createExpression();
                    expression.setScriptGrammar(scriptGrammar);
                    expression.getMixed().add(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(), newScript);
                    newCondition.setExpression(expression);

                    Command removeCommand = SetCommand.create(editingDomain,
                            transition,
                            Xpdl2Package.eINSTANCE.getTransition_Condition(),
                            oldCondition);
                    Command setCommand = SetCommand.create(editingDomain,
                            transition,
                            Xpdl2Package.eINSTANCE.getTransition_Condition(),
                            newCondition);
                    cmd.append(removeCommand);
                    cmd.append(setCommand);
                }

                return cmd;
            }
            return null;

        }

        /**
         * 
         * @return String - script for conditional transition
         */
        private String getTransitionScript() {

            /* custRef == null; */
            StringBuilder sb = new StringBuilder();
            String refName = "data." + caseClass.getName(); //$NON-NLS-1$
            sb.append(refName);
            sb.append(" == null;"); //$NON-NLS-1$
            return sb.toString();
        }
    }
}
