/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.processinterface.test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.analyst.resources.xpdl2.wizards.newparameter.NewParameterWizard;
import com.tibco.xpd.analyst.resources.xpdl2.wizards.processinterfaces.NewProcessInterfaceWizard;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.ElementsFactory;
import com.tibco.xpd.processeditor.xpdl2.wizards.NewBusinessProcessWizard;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.WidgetRGB;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.FormalParametersContainer;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.OtherAttributesContainer;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author rsomayaj
 * 
 */
public class BaseAnalystTest extends TestCase {

    /**
     * 
     */
    private static final String XPDL_FILE_EXTN = "xpdl"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String BUSINESS_PROCESS_ASSET = "processes"; //$NON-NLS-1$

    /**
     * Create a Sub-Process task in a given process. To Set the sub-flow id, use
     * {@link #setSubFlowId(EditingDomain, Activity, String)}
     * 
     * @param editingDomain
     * @param process
     * @return
     */
    protected Activity createSubProc(EditingDomain editingDomain,
            Process process) {

        ProcessWidgetType processType = TaskObjectUtil.getProcessType(process);

        WidgetRGB fillColor =
                ProcessWidgetColors.getInstance(processType)
                        .getGraphicalNodeColor(null,
                                TaskType.SUBPROCESS_LITERAL
                                        .getGetDefaultFillColorId());
        WidgetRGB lineColor =
                ProcessWidgetColors.getInstance(processType)
                        .getGraphicalNodeColor(null,
                                TaskType.SUBPROCESS_LITERAL
                                        .getGetDefaultLineColorId());

        Activity subProcActivity =
                ElementsFactory.createTask(new Point(100, 100),
                        new Dimension(ProcessWidgetConstants.TASK_WIDTH_SIZE,
                                ProcessWidgetConstants.TASK_HEIGHT_SIZE),
                        getLaneIdFromProcess(process),
                        TaskType.SUBPROCESS_LITERAL,
                        fillColor.toString(),
                        lineColor.toString());

        Command command =
                AddCommand.create(editingDomain,
                        process,
                        Xpdl2Package.eINSTANCE.getFlowContainer_Activities(),
                        subProcActivity);
        executeCommand(editingDomain, command);
        return subProcActivity;
    }

    private String getLaneIdFromProcess(Process process) {
        Collection<Pool> processPools = Xpdl2ModelUtil.getProcessPools(process);
        if (processPools.size() > 0) {
            Pool pool = processPools.iterator().next();
            if (pool.getLanes().size() > 0) {
                return pool.getLanes().get(0).getId();
            }
        }
        return null;
    }

    /**
     * Creates a process that implements a given {@link ProcessInterface}.
     * 
     * @param editingDomain
     * @param xpdlPackage
     *            {@link Package} within which the {@link Process}needs to be
     * @param procIfc
     *            is optional. If null - sets it to the default name which is
     *            "PackageName"-Process
     */
    protected Process createProcessThatImplementsIfc(
            EditingDomain editingDomain, Package xpdlPackage,
            ProcessInterface procIfc, String processName) {

        Process process = null;
        NewBusinessProcessWizard procWizard = new NewBusinessProcessWizard();
        procWizard.init(PlatformUI.getWorkbench(), new StructuredSelection(
                procIfc));

        WizardDialog dialog =
                new WizardDialog(PlatformUI.getWorkbench()
                        .getActiveWorkbenchWindow().getShell(), procWizard);

        dialog.create();

        procWizard.performFinish();
        Collection<?> result =
                editingDomain.getCommandStack().getMostRecentCommand()
                        .getResult();
        if (result.size() > 0) {
            for (Object res : result) {
                if (res instanceof Process) {
                    process = (Process) res;
                    break;
                }
            }
        }

        if (processName != null) {
            Command command =
                    getUpdateDisplayNameCmd(editingDomain, process, processName);
            executeCommand(editingDomain, command);
        }
        return process;
    }

    /**
     * Utility to alter the Display name of a given {@link NamedElement}
     * 
     * @param editingDomain
     * @param namedElement
     * @param displayName
     */
    protected void renameProcess(EditingDomain editingDomain,
            NamedElement namedElement, String name) {
        Command cmd = null;
        cmd = getUpdateDisplayNameCmd(editingDomain, namedElement, name);
        executeCommand(editingDomain, cmd);
    }

    /**
     * Retrieves the default {@link Process} in the default {@link Package}
     * created by the New Project Wizard.
     * 
     * @param xpdlPackage
     * @return
     */
    protected Process getInitialProcess(Package xpdlPackage) {
        EList<Process> processes = xpdlPackage.getProcesses();
        if (processes.size() > 0) {
            Process process = processes.get(0);
            return process;
        }
        return null;
    }

    /**
     * @param editingDomain
     * @param cmd
     */
    private void executeCommand(EditingDomain editingDomain, Command cmd) {
        editingDomain.getCommandStack().execute(cmd);
    }

    /**
     * Create a {@link FormalParameter} in the given Process or
     * {@link ProcessInterface}
     * 
     * @param editingDomain
     * @param formalParamsContainer
     *            either a {@link Process} or {@link ProcessInterface}
     * @param formalParameterName
     *            name of the {@link FormalParameter}
     * @param mode
     *            {@link ModeType} of the {@link FormalParameter}
     * 
     * 
     */
    protected FormalParameter createFormalParam(EditingDomain editingDomain,
            FormalParametersContainer formalParamsContainer, String fpName,
            ModeType mode) {
        FormalParameter formalParam = null;
        NewParameterWizard newParamWizard = new NewParameterWizard();
        newParamWizard.init(PlatformUI.getWorkbench(), new StructuredSelection(
                formalParamsContainer));

        WizardDialog dialog =
                new WizardDialog(PlatformUI.getWorkbench()
                        .getActiveWorkbenchWindow().getShell(), newParamWizard);

        dialog.create();
        if (newParamWizard.canFinish()) {
            newParamWizard.performFinish();
        } else {
            // TODO - Need to assert something is not right
            // assert
        }

        Collection<?> result =
                editingDomain.getCommandStack().getMostRecentCommand()
                        .getResult();
        if (result.size() > 0) {
            Object formalParamObj = result.iterator().next();
            if (formalParamObj instanceof FormalParameter) {
                formalParam = (FormalParameter) formalParamObj;
            }
        }

        if (fpName != null) {
            Command command =
                    getUpdateDisplayNameCmd(editingDomain, formalParam, fpName);
            executeCommand(editingDomain, command);
        }
        if (mode != null) {
            Command command =
                    SetCommand.create(editingDomain,
                            formalParam,
                            Xpdl2Package.eINSTANCE.getFormalParameter_Mode(),
                            mode);
            executeCommand(editingDomain, command);
        }

        return formalParam;
    }

    /**
     * Creates a process interface in a package, if the proc ifc name is null,
     * it uses the default name in wizard
     * 
     * @param editingDomain
     * @param xpdlPackage
     * @return
     */
    protected ProcessInterface createProcessInterfaceInPackage(
            EditingDomain editingDomain, Package xpdlPackage, String procIfcName) {
        ProcessInterface procIfc = null;
        NewProcessInterfaceWizard procIfcWizard =
                new NewProcessInterfaceWizard();
        procIfcWizard.init(PlatformUI.getWorkbench(), new StructuredSelection(
                WorkingCopyUtil.getFile(xpdlPackage)));
        WizardDialog dialog =
                new WizardDialog(PlatformUI.getWorkbench()
                        .getActiveWorkbenchWindow().getShell(), procIfcWizard);

        dialog.create();
        if (procIfcWizard.canFinish()) {
            procIfcWizard.performFinish();
        } else {
            // TODO - Need to assert something is not right
            // assert
        }

        // Collection<?> result =
        // editingDomain.getCommandStack().getMostRecentCommand()
        // .getResult();

        /*
         * XPD-5974: Saket: Instead of fetching the most recent command which is
         * a touch risky, we should do what this method is supposed to do i.e.,
         * create a new process interface.
         */
        ProcessInterfaces processInterfaces =
                XpdExtensionFactory.eINSTANCE.createProcessInterfaces();
        CompoundCommand ccmd = new CompoundCommand();
        ccmd.append(Xpdl2ModelUtil.getSetOtherElementCommand(editingDomain,
                xpdlPackage,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_ProcessInterfaces(),
                processInterfaces));

        procIfc = XpdExtensionFactory.eINSTANCE.createProcessInterface();
        // procIfc.setName(procIfcName);

        ccmd.append(AddCommand.create(editingDomain,
                processInterfaces,
                XpdExtensionPackage.eINSTANCE
                        .getProcessInterfaces_ProcessInterface(),
                procIfc));

        if (ccmd.canExecute()) {
            editingDomain.getCommandStack().execute(ccmd);
        } else {
            System.out.println();
        }

        // if (result.size() > 0) {
        // Object procIfcObj = result.iterator().next();
        // if (procIfcObj instanceof ProcessInterface) {
        // procIfc = (ProcessInterface) procIfcObj;
        // }
        // }

        if (procIfcName != null) {
            Command command =
                    getUpdateDisplayNameCmd(editingDomain, procIfc, procIfcName);

            executeCommand(editingDomain, command);
        }

        return procIfc;
    }

    /**
     * @param editingDomain
     * @param procIfc
     * @param procIfcName
     * @return
     */
    private Command getUpdateDisplayNameCmd(EditingDomain editingDomain,
            OtherAttributesContainer otherAttributesContainer,
            String displayName) {
        return Xpdl2ModelUtil.getSetOtherAttributeCommand(editingDomain,
                otherAttributesContainer,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                displayName);
    }

    /**
     * Retrieve the default XPDL Package created in the project
     * 
     * @param project
     * @return {@link Package} The default XPDL package that is created with the
     *         New Project Wizard.
     * @throws CoreException
     */
    protected Package getDefaultXPDLPackage(IProject project)
            throws CoreException {
        ProjectConfig projectConfig =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);
        SpecialFolders specialFolders = projectConfig.getSpecialFolders();

        List<IFolder> eclipseIFoldersOfKind =
                specialFolders.getEclipseIFoldersOfKind(BUSINESS_PROCESS_ASSET);
        if (eclipseIFoldersOfKind.size() > 0) {
            IFolder folder = eclipseIFoldersOfKind.get(0);
            if (folder.members().length > 0) {
                IResource resource = folder.members()[0];
                if (resource instanceof IFile
                        && ((IFile) resource).getFileExtension()
                                .equals(XPDL_FILE_EXTN)) {
                    WorkingCopy workingCopy =
                            XpdResourcesPlugin.getDefault()
                                    .getWorkingCopy(resource);
                    if (workingCopy != null) {
                        Object rootElement = workingCopy.getRootElement();
                        if (rootElement instanceof Package) {
                            return (Package) rootElement;
                        }
                    }
                }
            }
        }

        return null;
    }

    /**
     * Creates a New BPM/SOA project as does the
     * {@link NewBusinessProcessWizard} does. This adds the default assets to
     * the project, and creates a Process Package, and a BOM with default names.
     * 
     * @param projectName
     *            The name the project wants to be called.
     */
    protected IProject createProject(String projectName) {
        TestUtil.delay(30000);
        return TestUtil.createProjectFromWizard(projectName);
    }

    /**
     * Sets the sub-flow id of a sub-process task.
     * 
     * @param editingDomain
     * @param activity
     * @param id
     */
    public void setSubFlowId(EditingDomain editingDomain, Activity activity,
            String id) {
        if (TaskType.SUBPROCESS_LITERAL.equals(TaskObjectUtil
                .getTaskType(activity))) {
            SubFlow subflow = ((SubFlow) activity.getImplementation());
            Command cmd =
                    SetCommand.create(editingDomain,
                            subflow,
                            Xpdl2Package.eINSTANCE.getSubFlow_ProcessId(),
                            id);
            executeCommand(editingDomain, cmd);
        }
    }

    /**
     * Given whether a given list of {@link FormalParameter}s exists in the
     * given {@link Process} or {@link ProcessInterface}.
     * 
     * @param formalParamsContainer
     * @param formalParams
     * @return
     */
    protected boolean doesProcessOrProcIfcContainGivenParams(
            FormalParametersContainer formalParamsContainer,
            FormalParameter... formalParams) {
        List<FormalParameter> paramsAsList = Arrays.asList(formalParams);
        return formalParamsContainer.getFormalParameters()
                .containsAll(paramsAsList);
    }
}
