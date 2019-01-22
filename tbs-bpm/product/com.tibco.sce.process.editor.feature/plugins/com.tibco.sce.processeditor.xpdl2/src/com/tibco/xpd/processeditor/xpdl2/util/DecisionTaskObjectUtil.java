/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.util;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.analyst.resources.xpdl2.pickers.DecisionFlowFilterPicker;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.DecisionFlowFilterPicker.DecisionFlowPickerType;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.navigator.packageexplorer.editors.EditorInputFactory;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdProjectResourceFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.UniqueIdElement;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.DecisionFlowUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Various utilities to help in getting / setting BPMN DecisionFlow-related
 * data.
 * 
 * @author mtorres
 * 
 */
public class DecisionTaskObjectUtil {

    /** Script context identifier for sub-processes. */
    public static final String DEC_FLOW_SCRIPT_CONTEXT = "DecisionFlow"; //$NON-NLS-1$

    public static final String DECISIONS_SPECIAL_FOLDER_KIND = "decisions"; //$NON-NLS-1$

    /**
     * @param activity
     * @return <code>true</code> if the given activity is a decision service
     *         task.
     */
    public static boolean isDecisionServiceTask(Activity activity) {
        if (TaskType.SERVICE_LITERAL.equals(TaskObjectUtil
                .getTaskTypeStrict(activity))) {
            if (TaskImplementationTypeDefinitions.DECISION_SERVICE
                    .equals(TaskObjectUtil
                            .getTaskImplementationExtensionId(activity))) {
                return true;
            }
        }
        return false;
    }

    public static Command getSetDecFlowCommand(EditingDomain ed, Activity act,
            EObject decFlow) {
        // Safest to always re-create the task implementation detail from
        // scratch - delete existing implementation
        CompoundCommand cmd = new CompoundCommand();
        cmd.setLabel(Messages.DecisionTaskObjectUtil_SetDecisionFlow_menu);

        if (act.getRoute() != null) {
            cmd.append(SetCommand.create(ed,
                    act,
                    Xpdl2Package.eINSTANCE.getActivity_Route(),
                    null));
        }
        if (act.getStartMode() != null) {
            cmd.append(SetCommand.create(ed,
                    act,
                    Xpdl2Package.eINSTANCE.getActivity_StartMode(),
                    null));
        }
        if (act.getFinishMode() != null) {
            cmd.append(SetCommand.create(ed,
                    act,
                    Xpdl2Package.eINSTANCE.getActivity_FinishMode(),
                    null));
        }

        if (act != null && act.getImplementation() instanceof Task) {
            Task task = (Task) act.getImplementation();
            if (task.getTaskService() != null) {
                TaskService taskService = task.getTaskService();

                EStructuralFeature initial =
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_InitialParameterValue();
                List<?> others =
                        Xpdl2ModelUtil.getOtherElementList(act, initial);
                for (Object other : others) {
                    cmd.append(Xpdl2ModelUtil.getRemoveOtherElementCommand(ed,
                            act,
                            initial,
                            other));
                }
                Xpdl2Factory f = Xpdl2Factory.eINSTANCE;
                SubFlow subFlow = f.createSubFlow();
                if (decFlow != null) {
                    UniqueIdElement proc = (UniqueIdElement) decFlow;

                    String procId = proc.getId();
                    if (procId == null) {
                        return null;
                    }

                    WorkingCopy externalWc =
                            WorkingCopyUtil.getWorkingCopyFor(proc);
                    Xpdl2WorkingCopyImpl wc =
                            (Xpdl2WorkingCopyImpl) WorkingCopyUtil
                                    .getWorkingCopyFor(act);
                    if (wc == externalWc) {
                        subFlow.setProcessId(procId);
                    } else {
                        String refId =
                                wc.appendCreateReferenceCommand(externalWc, cmd);
                        subFlow.setProcessId(procId);
                        subFlow.setPackageRefId(refId);
                    }
                } else {
                    subFlow.setProcessId("none"); //$NON-NLS-1$
                }
                cmd.append(Xpdl2ModelUtil.getSetOtherElementCommand(ed,
                        taskService,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_DecisionService(),
                        subFlow));
            }
        }
        return cmd;
    }

    public static String getDecFlowDescription(Activity act) {
        EObject sf = DecisionTaskObjectUtil.getDecisionFlow(act);
        if (sf == null) {
            return null;
        }
        return WorkingCopyUtil.getText(sf);
    }

    /**
     * Get the xpdExtension DecisionFlow that is referenced by the given
     * independent decision service task activity.
     * 
     * @param act
     * @return xpdl2:Process ProcessInterface referenced by task OR <b>null</b>
     *         if the activity is not a decisionservice task or there is no
     *         decisionFlow currently referenced.
     */
    public static EObject getDecisionFlow(Activity act) {
        Xpdl2WorkingCopyImpl wc =
                (Xpdl2WorkingCopyImpl) WorkingCopyUtil.getWorkingCopyFor(act);
        return getDecisionFlow(act, wc);
    }

    /**
     * Get the xpdExtension DecisionFlow that is referenced by the given
     * independent decision service task activity.
     * 
     * @param act
     * @param workingCopy
     *            Sometimes we may be working on activities that have NOT YET
     *            BEEN SAVED and therefore do not have a working copy
     *            themselves. In this case the caller can pass in a working copy
     *            that it does know about.
     * @return
     */
    public static EObject getDecisionFlow(Activity act,
            Xpdl2WorkingCopyImpl workingCopy) {
        String procId = null;
        String packageRefId = null;

        SubFlow decisionFlow = getDecisionFlowReference(act);

        if (decisionFlow != null) {
            procId = decisionFlow.getProcessId();
            packageRefId = decisionFlow.getPackageRefId();
        }
        if (packageRefId == null) {
            Process activityParentProcess = act.getProcess();
            if (activityParentProcess != null) {
                Process process =
                        activityParentProcess.getPackage().getProcess(procId);
                if (process != null) {
                    return process;
                }
            }

        } else {
            // Get the location of the external reference

            if (workingCopy != null) {
                String location =
                        workingCopy.getExternalPackageLocation(packageRefId);
                if (location != null) {

                    IResource callProcRes =
                            workingCopy.getEclipseResources().get(0);
                    IProject callProcProject = callProcRes.getProject();

                    List<IResource> resourceList =
                            ProcessUIUtil
                                    .getResourcesForLocation(callProcProject,
                                            location,
                                            DECISIONS_SPECIAL_FOLDER_KIND);
                    if (resourceList != null) {
                        for (IResource src : resourceList) {
                            IProject project = src.getProject();

                            if (project != null) {
                                XpdProjectResourceFactory fact =
                                        XpdResourcesPlugin
                                                .getDefault()
                                                .getXpdProjectResourceFactory(project);

                                if (fact != null) {
                                    IResource res =
                                            fact.resolveResourceReference(src,
                                                    location,
                                                    DECISIONS_SPECIAL_FOLDER_KIND);
                                    if (res != null) {
                                        WorkingCopy externalWc =
                                                fact.getWorkingCopy(res);

                                        if (externalWc != null) {
                                            Package externalPkg =
                                                    (Package) externalWc
                                                            .getRootElement();

                                            if (externalPkg != null) {
                                                Process process =
                                                        externalPkg
                                                                .getProcess(procId);
                                                if (process != null) {
                                                    return process;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

    /**
     * @param activity
     * @return The decision flow reference info element
     */
    public static SubFlow getDecisionFlowReference(Activity activity) {
        SubFlow decisionFlow = null;

        Implementation impl = activity.getImplementation();
        if (impl instanceof Task) {
            Task task = (Task) impl;
            if (null != task.getTaskService()) {
                Object otherElement =
                        Xpdl2ModelUtil.getOtherElement(task.getTaskService(),
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_DecisionService());
                if (otherElement instanceof SubFlow) {
                    decisionFlow = (SubFlow) otherElement;
                }
            }
        }
        return decisionFlow;
    }

    public static String getDecFlowLocationDescription(Activity act) {
        EObject sf = getDecisionFlow(act);
        if (sf == null) {
            return null;
        }
        WorkingCopy external = WorkingCopyUtil.getWorkingCopyFor(sf);
        WorkingCopy local = WorkingCopyUtil.getWorkingCopyFor(act);

        if (external == local) {
            return null;
        }
        // return external.getEclipseResource().getName();
        return external.getEclipseResources().get(0).getProjectRelativePath()
                .toString();
    }

    public static SubFlow getDecFlowExtendedElement(Activity act) {
        return DecisionFlowUtil.getDecisionServiceExt(act);
    }

    /**
     * Find the decision flow for the given decision service task and open it in
     * its editor.
     * <p>
     * If the decision flow cannot be found then a message dialog is displayed.
     * 
     * @param decisionServiceTask
     */
    public static void openDecisionFlowEditor(Activity decisionServiceTask) {

        if (decisionServiceTask != null) {
            EObject decisionFlow = getDecisionFlow(decisionServiceTask);
            if (decisionFlow != null) {
                try {
                    IConfigurationElement facConfig =
                            XpdResourcesUIActivator
                                    .getEditorFactoryConfigFor(decisionFlow);

                    String editorID = facConfig.getAttribute("editorID"); //$NON-NLS-1$
                    IWorkbenchPage page =
                            PlatformUI.getWorkbench()
                                    .getActiveWorkbenchWindow().getActivePage();

                    EditorInputFactory f =
                            (EditorInputFactory) facConfig
                                    .createExecutableExtension("factory"); //$NON-NLS-1$

                    IEditorInput input = f.getEditorInputFor(decisionFlow);

                    page.openEditor(input, editorID);

                } catch (CoreException e) {
                    e.printStackTrace();
                }

            } else {
                MessageDialog
                        .openError(Display.getDefault().getActiveShell(),
                                Messages.DecisionTaskObjectUtil_OpenDecisionFlow_title,
                                Messages.DecisionTaskObjectUtil_CannotAccessDecisionFlow_message);
            }
        }

    }

    /**
     * Get the process from a process picker displayed to user
     * 
     * @return the decision flow process or <code>null</code> if user cancels.
     */
    public static EObject getDecisionFlowFromPicker(Activity decisionServiceTask) {
        EObject decisionFlow = null;

        if (decisionServiceTask != null) {
            Shell shell = Display.getDefault().getActiveShell();

            DecisionFlowFilterPicker picker =
                    new DecisionFlowFilterPicker(shell,
                            DecisionFlowPickerType.DECISIONFLOW, false);

            Object curSel =
                    DecisionTaskObjectUtil.getDecisionFlow(decisionServiceTask);
            if (curSel != null) {
                picker.setInitialElementSelections(Collections
                        .singletonList(curSel));
            }

            decisionFlow =
                    ProcessUIUtil.getResultFromPicker(picker,
                            shell,
                            decisionServiceTask);

            if (decisionFlow != null) {
                if (!ProcessUIUtil.checkAndAddProjectReference(shell,
                        decisionServiceTask,
                        decisionFlow)) {
                    decisionFlow = null;
                }
            }
        }

        return decisionFlow;
    }
}
