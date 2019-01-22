/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.event.error;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableError;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorFolder;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorUtil;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.IBpmnCatchableErrorTreeItem;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.IBpmnCatchableErrorsContributor;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.ErrorMethod;
import com.tibco.xpd.xpdExtension.ErrorThrowerType;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Standard (built in contribution) Catchable errors provider for sub-process /
 * embedded sub-process tasks.
 * 
 * @author aallway
 * @since 3.2
 */
public class BpmnSubProcessCatchableErrorsContributor implements
        IBpmnCatchableErrorsContributor {

    public BpmnSubProcessCatchableErrorsContributor() {
    }

    @Override
    public Collection<IBpmnCatchableErrorTreeItem> getCatchableErrorTreeItems(
            Activity errorThrower) {
        // Use special internal method that will not fall prey to closed
        // process call looping.

        BpmnCatchableErrorFolder folder =
                internalGetCatchableErrorTreeItems(errorThrower,
                        new HashSet<Process>());

        if (folder != null) {
            List<IBpmnCatchableErrorTreeItem> items =
                    new ArrayList<IBpmnCatchableErrorTreeItem>();
            items.add(folder);
            return items;
        }

        return Collections.emptyList();
    }

    /**
     * Recursive method that returns a folder of catchable errors and
     * sub-folders that can be reached from within the given activity.
     * 
     * @param errorThrower
     * @param alreadyVisitedProcesses
     * 
     * @return folder or null if no errors.
     */
    private BpmnCatchableErrorFolder internalGetCatchableErrorTreeItems(
            Activity errorThrower, Set<Process> alreadyVisitedProcesses) {

        BpmnCatchableErrorFolder folderForThisActivity = null;

        if (errorThrower.getBlockActivity() != null) {
            //
            // Step into the embedded sub-process.
            //
            Image image = null;
            if (!XpdResourcesPlugin.isInHeadlessMode()) {
                image =
                        Xpdl2ResourcesPlugin
                                .getDefault()
                                .getImageRegistry()
                                .get(Xpdl2ResourcesConsts.IMG_SUBPROC_ENBEDDED_ICON);
            }
            folderForThisActivity =
                    new BpmnCatchableErrorFolder(
                            Xpdl2ModelUtil.getDisplayName(errorThrower), image);

            ActivitySet actSet =
                    errorThrower.getProcess().getActivitySet(errorThrower
                            .getBlockActivity().getActivitySetId());

            if (actSet != null) {
                for (Activity subAct : actSet.getActivities()) {
                    // And process each activity - if it's one we understand
                    // then do it here ourselves (so we can check for closed
                    // loops of subproc calls.
                    if (isApplicableErrorThrower(subAct)) {
                        // recurs
                        BpmnCatchableErrorFolder subFolder =
                                internalGetCatchableErrorTreeItems(subAct,
                                        alreadyVisitedProcesses);
                        if (subFolder != null) {
                            folderForThisActivity.addChild(subFolder);
                        }

                    } else {
                        boolean isAttachedToTask =
                                (BpmnCatchableErrorUtil
                                        .getAttachedToTask(subAct) != null);

                        if (!isAttachedToTask) {
                            // Otherwise use the standard utility method that
                            // will look for contributers for other task types.
                            Collection<IBpmnCatchableErrorTreeItem> errs =
                                    BpmnCatchableErrorUtil
                                            .getCatchableErrors(subAct);
                            for (IBpmnCatchableErrorTreeItem err : errs) {
                                folderForThisActivity.addChild(err);
                            }
                        }
                    }
                }
            }

        } else if (errorThrower.getImplementation() instanceof SubFlow) {
            //
            // Get the process.
            //
            Image image = null;
            if (!XpdResourcesPlugin.isInHeadlessMode()) {
                image =
                        Xpdl2ResourcesPlugin.getDefault().getImageRegistry()
                                .get(Xpdl2ResourcesConsts.IMG_SUBPROC_ICON);
            }
            folderForThisActivity =
                    new BpmnCatchableErrorFolder(
                            Xpdl2ModelUtil.getDisplayName(errorThrower), image);

            EObject procOrIfc =
                    ProcessDataUtil.getSubProcessOrInterface(errorThrower);

            if (procOrIfc instanceof ProcessInterface) {
                //
                // Add error events from process interface invoked from reusable
                // sub-process task.
                //
                ProcessInterface ifc = (ProcessInterface) procOrIfc;

                BpmnCatchableErrorFolder procIfcItemsFolder =
                        new BpmnCatchableErrorFolder(
                                Xpdl2ModelUtil.getDisplayName(errorThrower),
                                WorkingCopyUtil.getImage(procOrIfc));

                folderForThisActivity.addChild(procIfcItemsFolder);

                for (StartMethod startMethod : ifc.getStartMethods()) {
                    addStartMethodErrorItems(procIfcItemsFolder, startMethod);
                }

                for (IntermediateMethod intermediateMethod : ifc
                        .getIntermediateMethods()) {
                    addIntermediateMethodErrorItems(procIfcItemsFolder,
                            intermediateMethod);
                }

            } else if (procOrIfc instanceof Process) {
                if (!alreadyVisitedProcesses.contains(procOrIfc)) {
                    //
                    // Add errors from process
                    //
                    alreadyVisitedProcesses.add((Process) procOrIfc);

                    BpmnCatchableErrorFolder processItemsFolder =
                            new BpmnCatchableErrorFolder(
                                    Xpdl2ModelUtil
                                            .getDisplayName((Process) procOrIfc),
                                    WorkingCopyUtil.getImage(procOrIfc));

                    folderForThisActivity.addChild(processItemsFolder);

                    for (Activity subAct : ((Process) procOrIfc)
                            .getActivities()) {
                        // And process each activity - if it's one we understand
                        // then do it here ourselves (so we can check for closed
                        // loops of subproc calls.
                        if (isApplicableErrorThrower(subAct)) {
                            // recurs
                            BpmnCatchableErrorFolder subFolder =
                                    internalGetCatchableErrorTreeItems(subAct,
                                            alreadyVisitedProcesses);
                            if (subFolder != null) {
                                processItemsFolder.addChild(subFolder);
                            }

                        } else {
                            boolean isAttachedToTask =
                                    (BpmnCatchableErrorUtil
                                            .getAttachedToTask(subAct) != null);

                            if (!isAttachedToTask) {
                                // Otherwise use the standard utility method
                                // that will look for contributers for other
                                // task types.
                                Collection<IBpmnCatchableErrorTreeItem> errs =
                                        BpmnCatchableErrorUtil
                                                .getCatchableErrors(subAct);
                                for (IBpmnCatchableErrorTreeItem err : errs) {
                                    processItemsFolder.addChild(err);
                                }
                            }
                        }
                    }
                }
            }
        }

        return folderForThisActivity;
    }

    /**
     * @param procIfcItemsFolder
     * @param method
     */
    private void addStartMethodErrorItems(
            BpmnCatchableErrorFolder procIfcItemsFolder, StartMethod method) {
        if (TriggerType.NONE_LITERAL.equals(method.getTrigger())) {
            if (!method.getErrorMethods().isEmpty()) {
                BpmnCatchableErrorFolder methodFolder =
                        new BpmnCatchableErrorFolder(
                                Xpdl2ModelUtil.getDisplayNameOrName(method),
                                WorkingCopyUtil.getImage(method));

                procIfcItemsFolder.addChild(methodFolder);

                for (ErrorMethod error : method.getErrorMethods()) {
                    BpmnCatchableError catchableError =
                            new BpmnCatchableError(method,
                                    ErrorThrowerType.INTERFACE_EVENT,
                                    error.getErrorCode(), this);
                    methodFolder.addChild(catchableError);
                }
            }
        }
        return;
    }

    /**
     * @param procIfcItemsFolder
     * @param method
     */
    private void addIntermediateMethodErrorItems(
            BpmnCatchableErrorFolder procIfcItemsFolder,
            IntermediateMethod method) {
        if (TriggerType.NONE_LITERAL.equals(method.getTrigger())) {
            if (!method.getErrorMethods().isEmpty()) {
                BpmnCatchableErrorFolder methodFolder =
                        new BpmnCatchableErrorFolder(
                                Xpdl2ModelUtil.getDisplayNameOrName(method),
                                WorkingCopyUtil.getImage(method));

                procIfcItemsFolder.addChild(methodFolder);

                for (ErrorMethod error : method.getErrorMethods()) {
                    BpmnCatchableError catchableError =
                            new BpmnCatchableError(method,
                                    ErrorThrowerType.INTERFACE_EVENT,
                                    error.getErrorCode(), this);
                    methodFolder.addChild(catchableError);
                }
            }
        }

        return;
    }

    @Override
    public boolean isApplicableErrorThrower(Activity errorThrower) {
        if (errorThrower.getBlockActivity() != null
                || errorThrower.getImplementation() instanceof SubFlow) {
            return true;
        }
        return false;
    }

    @Override
    public Image getErrorImage(Object errorThrower, String errorId) {
        if (errorThrower instanceof InterfaceMethod) {
            for (ErrorMethod errorMethod : ((InterfaceMethod) errorThrower)
                    .getErrorMethods()) {
                if (errorId.equals(errorMethod.getErrorCode())) {
                    return WorkingCopyUtil.getImage(errorMethod);
                }
            }
            return null;
        } else {
            throw new RuntimeException(
                    "Expected errorThrower to be instanceof Activity - got: " //$NON-NLS-1$ 
                            + errorThrower.getClass().getName());
        }
    }

    @Override
    public String getErrorThrowerContainerId(Object errorThrower) {
        //
        // This sub-process task errors provider ONLY EVER returns (and
        // therefore handles) BpmnCatchError's for proces interface methods.
        if (errorThrower instanceof InterfaceMethod) {
            ProcessInterface pi =
                    ProcessInterfaceUtil
                            .getProcessInterface((InterfaceMethod) errorThrower);
            if (pi != null) {
                return pi.getId();
            } else {
                throw new RuntimeException("Parentless interface method!"); //$NON-NLS-1$
            }

        } else {
            throw new RuntimeException(
                    "Expected errorThrower to be instanceof Interface - got: " //$NON-NLS-1$ 
                            + errorThrower.getClass().getName());
        }
    }

    @Override
    public String getErrorThrowerId(Object errorThrower) {
        if (errorThrower instanceof InterfaceMethod) {
            return ((InterfaceMethod) errorThrower).getId();
        } else {
            throw new RuntimeException(
                    "Expected errorThrower to be instanceof Activity - got: " //$NON-NLS-1$ 
                            + errorThrower.getClass().getName());
        }
    }

}
