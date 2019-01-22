/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.xpdl2.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdExtension.XpdModelType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.edit.internal.Messages;

/**
 * @author mtorres
 */
public final class DecisionFlowUtil {

    public static final String DECISIONS_SPECIAL_FOLDER_KIND = "decisions"; //$NON-NLS-1$

    public static final String BOM_SPECIALFOLDER_KIND = "bom"; //$NON-NLS-1$

    public static final String DEFAULT_DECISION_FLOW_PACKAGE_FILENAME_EXT =
            "dflow"; //$NON-NLS-1$ //

    public static final java.lang.String DECISIONS_GLOBAL_DESTINATION_ID =
            "com.tibco.xpd.decisions.globalDestination"; //$NON-NLS-1$

    public static boolean isDecisionFlow(Process process) {
        boolean decisionFlow = false;
        if (process != null) {
            Object other =
                    Xpdl2ModelUtil.getOtherAttribute(process,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_XpdModelType());
            if (XpdModelType.DECISION_FLOW.equals(other)) {
                decisionFlow = true;
            }
        }
        return decisionFlow;
    }

    public static boolean isDecisionServiceTask(Activity activity) {
        if (isDecisionServiceTaskImplementation(activity)) {
            /*
             * Have to distinguish between decision service task in bpm proc and
             * decision table task in decision flow which _both_ have the same
             * implementation id.
             */
            if (!DecisionFlowUtil.isDecisionsContent(activity)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isDecisionTableTask(Activity activity) {
        if (isDecisionTableTaskImplementation(activity)) {
            /*
             * Have to distinguish between decision service task in bpm proc and
             * decision table task in decision flow which _both_ have the same
             * implementation id.
             */
            if (DecisionFlowUtil.isDecisionsContent(activity)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if the given Activity is a "DecisionService" task type.
     * <p>
     * <b>Note that BOTH Decision Table Task in a decision flow AND decision
     * Service task in a BPM process have the same task implementation id!!
     * 
     * @param activity
     * @return
     */
    public static boolean isDecisionServiceTaskImplementation(Activity activity) {
        Implementation impl = activity.getImplementation();
        if (impl instanceof Task) {
            Task task = (Task) impl;
            if (task.getTaskService() != null) {
                String extId =
                        (String) Xpdl2ModelUtil.getOtherAttribute(task
                                .getTaskService(),
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ImplementationType());

                if ("DecisionService".equals(extId)) { //$NON-NLS-1$
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isDecisionTableTaskImplementation(Activity activity) {
        return isDecisionServiceTaskImplementation(activity);
    }

    public static SubFlow getDecisionServiceExt(Activity activity) {
        if (activity != null) {
            Implementation impl = activity.getImplementation();
            if (impl instanceof Task) {
                Task task = (Task) impl;
                if (null != task.getTaskService()) {
                    Object otherElement =
                            Xpdl2ModelUtil.getOtherElement(task
                                    .getTaskService(),
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_DecisionService());
                    if (otherElement instanceof SubFlow) {
                        return (SubFlow) otherElement;
                    }

                }
            }
        }
        return null;
    }

    /**
     * @param eObject
     * @return true if the given eObject if the content of a decisions flow (or
     *         a decisions flow itself).
     */
    public static boolean isDecisionsContent(EObject eObject) {

        EObject toTest = eObject;
        while (toTest != null) {
            if (toTest instanceof Process) {
                return DecisionFlowUtil.isDecisionFlow((Process) toTest);

            } else if (toTest instanceof Package) {
                return isDecisionFlowPackage((Package) toTest);
            }
            toTest = toTest.eContainer();
        }

        return false;
    }

    public static boolean isDecisionFlowPackage(Package decisionFlowPackage) {
        boolean decisionFlow = false;
        if (decisionFlowPackage != null) {
            Object other =
                    Xpdl2ModelUtil.getOtherAttribute(decisionFlowPackage,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_XpdModelType());
            if (XpdModelType.DECISION_FLOW.equals(other)) {
                decisionFlow = true;
            }
        }
        return decisionFlow;
    }

    public static String getDecisionFlowAppName(Process decFlow) {
        if (decFlow != null && decFlow.getPackage() != null) {
            return "/" + decFlow.getPackage().getName() + "/" + decFlow.getName(); //$NON-NLS-1$//$NON-NLS-2$
        }
        return null;
    }

    public static Collection<Process> getAllDecisionFlows(IProject project) {
        Collection<Process> decFlows = new ArrayList<Process>();
        ProjectConfig config =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);
        if (config != null) {
            EList<IFolder> packageFolders =
                    config.getSpecialFolders()
                            .getEclipseIFoldersOfKind(DecisionFlowUtil.DECISIONS_SPECIAL_FOLDER_KIND);
            try {
                SpecialFolderVisitor specialFolderVisitor =
                        new SpecialFolderVisitor(decFlows);
                for (IFolder folder : packageFolders) {
                    folder.accept(specialFolderVisitor);
                }
            } catch (CoreException e) {
                XpdResourcesPlugin.getDefault().getLogger().error(e);
            }
        }

        return decFlows;
    }

    private static class SpecialFolderVisitor implements IResourceVisitor {
        private final Collection<Process> decFlows;

        /**
         * Constructor takes the reference to the packages to which packages
         * will be added.
         */
        public SpecialFolderVisitor(Collection<Process> packages) {
            this.decFlows = packages;
        }

        /** {@inheritDoc} */
        @Override
        public boolean visit(IResource resource) throws CoreException {
            if (resource.getType() == IResource.FILE) {
                IFile file = (IFile) resource;
                String fileExtension = file.getFileExtension();
                if (fileExtension != null
                        && DecisionFlowUtil.DEFAULT_DECISION_FLOW_PACKAGE_FILENAME_EXT
                                .equals(fileExtension.toLowerCase())) {
                    WorkingCopy wc =
                            XpdResourcesPlugin.getDefault()
                                    .getWorkingCopy(resource);
                    if (!wc.isInvalidFile()) {
                        Package xpdlPackage = (Package) wc.getRootElement();
                        if (xpdlPackage != null) {
                            for (Process process : xpdlPackage.getProcesses()) {
                                if (DecisionFlowUtil.isDecisionFlow(process)) {
                                    decFlows.add(process);
                                }
                            }
                        } else {
                            XpdResourcesPlugin
                                    .getDefault()
                                    .getLogger()
                                    .error(String.format("Could not load DFlow WC for '%s'.", //$NON-NLS-1$
                                            file.getName()));
                        }
                    }
                }
                return false;
            }
            return true;
        }
    }

    public static Collection<Activity> getDecisionTableTaskActivities(
            Collection<Process> xpdlProcesses) {
        if (xpdlProcesses != null) {
            Collection<Activity> activities = new ArrayList<Activity>();
            for (Process process : xpdlProcesses) {
                if (process != null && !process.getActivities().isEmpty()) {
                    for (Activity activity : process.getActivities()) {
                        if (DecisionFlowUtil.isDecisionTableTask(activity)) {
                            activities.add(activity);
                        }
                    }
                }
            }
            return activities;
        }
        return Collections.emptyList();
    }

    public static Collection<Activity> getDecisionTableTaskActivities(
            Process xpdlProcess) {
        if (xpdlProcess != null && !xpdlProcess.getActivities().isEmpty()) {
            Collection<Activity> activities = new ArrayList<Activity>();
            for (Activity activity : xpdlProcess.getActivities()) {
                if (DecisionFlowUtil.isDecisionTableTask(activity)) {
                    activities.add(activity);
                }
            }
            return activities;
        }
        return Collections.emptyList();
    }

    /**
     * This method will return the table task Activities in the order as they
     * appear in the decision flow.
     * 
     * @param dFlow
     * @return
     */
    public static Collection<Activity> getSecuentialDecisionTableTaskActivities(
            Process dFlow) {
        List<Activity> startEventsForDFlow =
                DecisionFlowUtil.getAllStartEventsForDFlow(dFlow);
        // It can only be one otherwise throw an error
        if (startEventsForDFlow != null && startEventsForDFlow.size() == 1) {
            Collection<Activity> dTableTaskActivities =
                    new ArrayList<Activity>();
            List<Activity> processedActivities = new ArrayList<Activity>();
            ProcessFlowAnalyser analyser =
                    new ProcessFlowAnalyser(false, dFlow);
            Activity activity = startEventsForDFlow.get(0);
            if (activity != null) {
                processedActivities.add(activity);
                String activityId = activity.getId();
                while (activityId != null) {
                    List<Activity> outgoingActivities =
                            analyser.getOutgoingActivities(activityId);
                    activityId = null;
                    if (outgoingActivities != null
                            && outgoingActivities.size() == 1) {
                        Activity outGoingActivity =
                                outgoingActivities.iterator().next();
                        if (DecisionFlowUtil
                                .isDecisionTableTask(outGoingActivity)
                                && !processedActivities
                                        .contains(outGoingActivity)) {
                            dTableTaskActivities.add(outGoingActivity);
                            processedActivities.add(outGoingActivity);
                            activityId = outGoingActivity.getId();
                        }
                    } else {
                        XpdResourcesPlugin
                                .getDefault()
                                .getLogger()
                                .error(String.format(Messages.DecisionFlowUtil_NoOutGoingActivitiesForActivityId,
                                        activityId));
                    }
                }
            }
            return dTableTaskActivities;
        } else {
            XpdResourcesPlugin
                    .getDefault()
                    .getLogger()
                    .error(String.format(Messages.DecisionFlowUtil_NoOrMoreThanOneStartEvent,
                            dFlow.getName()));
        }

        if (dFlow != null && !dFlow.getActivities().isEmpty()) {
            Collection<Activity> activities = new ArrayList<Activity>();
            for (Activity activity : dFlow.getActivities()) {
                if (DecisionFlowUtil.isDecisionTableTask(activity)) {
                    activities.add(activity);
                }
            }
            return activities;
        }
        return Collections.emptyList();
    }

    public static List<Activity> getAllStartEventsForDFlow(Process dFlow) {
        if (dFlow != null) {
            Collection<Activity> allActivitiesInProc =
                    Xpdl2ModelUtil.getAllActivitiesInProc(dFlow);
            List<Activity> startEvents = new ArrayList<Activity>();
            for (Object next : allActivitiesInProc) {
                Activity activity = (Activity) next;
                Event event = activity.getEvent();
                if (event instanceof StartEvent) {
                    startEvents.add(activity);
                }
            }
            return startEvents;
        }
        return Collections.emptyList();
    }

    /**
     * returns true if the package contains at least one Decision Service Task
     * 
     * @param xpdlPackage
     * @return
     */
    public static boolean hasDecisionServiceTasks(Package xpdlPackage) {
        if (xpdlPackage != null) {
            EList<Process> processes = xpdlPackage.getProcesses();
            for (Process process : processes) {
                if (DecisionFlowUtil.hasDecisionServiceTasks(process)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * returns true if the process contains at least one Decision Service Task
     * 
     * @param process
     * @return
     */
    public static boolean hasDecisionServiceTasks(Process process) {
        if (process != null) {
            Collection<Activity> allActivitiesInProc =
                    Xpdl2ModelUtil.getAllActivitiesInProc(process);
            for (Activity activity : allActivitiesInProc) {
                if (DecisionFlowUtil.isDecisionServiceTask(activity)) {
                    return true;
                }
            }
        }
        return false;
    }

}
