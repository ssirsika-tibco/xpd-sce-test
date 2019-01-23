/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.bx.validation.resolutions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ui.PlatformUI;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.bx.validation.rules.process.CorrelateImmediatelyConfigurationRule;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityWebServiceReference;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskReceive;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Abstract resolution class to set / unset the "Correlate Immediately" config
 * of the correlating activity under focus.
 * 
 * 
 * @author kthombar
 * @since Dec 9, 2014
 */
public abstract class AbstractSetUnsetCorrelateImmediatelyConfigResolution
        extends AbstractWorkingCopyResolution {

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#run(org.eclipse.core.resources.IMarker)
     * 
     * @param marker
     * @throws ResolutionException
     */
    @Override
    public void run(IMarker marker) throws ResolutionException {

        // Marker may not exist if the previous resolution resolved this problem
        // too
        if (marker != null && marker.exists()) {
            try {
                /*
                 * As the user to save all the dirty editors first.
                 */
                boolean saveAllEditors =
                        PlatformUI.getWorkbench().saveAllEditors(true);

                EObject target = getTarget(marker);

                EditingDomain editingDomain = null;
                if (target != null) {
                    editingDomain = WorkingCopyUtil.getEditingDomain(target);
                }

                if (editingDomain != null) {
                    /*
                     * Get a list of commands.
                     */
                    List<Command> resolutionCommands =
                            getResolutionCommands(editingDomain, target, marker);

                    if (resolutionCommands != null) {

                        for (Command cmd : resolutionCommands) {

                            if (cmd.canExecute()) {
                                editingDomain.getCommandStack().execute(cmd);
                            }
                        }
                    }
                }
                /*
                 * Finally save all the editors again(only if the user chose to
                 * save them earlier) after the command is executed
                 */
                if (saveAllEditors) {
                    PlatformUI.getWorkbench().saveAllEditors(false);
                }
                // originally threw a ResolutionException here if editingDomain
                // was null - however
                // in some cases this was null and it was found better to ignore
                // rather than throw exception
            } catch (CoreException e) {
                throw new ResolutionException(e);
            }
        }
    }

    /**
     * 
     * @param editingDomain
     *            the editing domain
     * @param target
     *            the target EObject
     * @param marker
     * @return list of commands to be executed per xpdl file else
     *         <code>null</code>
     */
    private List<Command> getResolutionCommands(EditingDomain editingDomain,
            EObject target, IMarker marker) {
        List<Command> commands = null;
        if (target instanceof Activity) {

            Activity act = (Activity) target;

            /*
             * get the quick fix value of correlate immediately config.
             */
            boolean correlateImmediatelyValue = correlateImmediatelyValue();

            /*
             * Get the list of activities on which the quick fix must be
             * applied.
             */
            List<Activity> allActivitiesToApplyQuickFixOn =
                    getAllActivitiesToApplyQuickFixOn(act,
                            correlateImmediatelyValue);

            if (!allActivitiesToApplyQuickFixOn.isEmpty()) {

                /*
                 * Get a map of the xpdl file to all the activites in that xpdl
                 * file on which the command must be executed.
                 */
                Map<IFile, List<Activity>> xpdlFileToActivitiesMap =
                        getXpdlFileToActivitiesInItMap(allActivitiesToApplyQuickFixOn);

                Set<IFile> allXpdlFilesAffectedByQuickFix =
                        xpdlFileToActivitiesMap.keySet();

                commands = new ArrayList<Command>();

                String commandLabel = null;

                /*
                 * set the command label
                 */
                if (correlateImmediatelyValue) {

                    commandLabel =
                            Messages.AbstractSetUnsetCorrelateImmediatelyConfigResolution_SetCorrelateImmediatelyCommand_label;
                } else {

                    commandLabel =
                            Messages.AbstractSetUnsetCorrelateImmediatelyConfigResolution_UnsetCorrelateImmediatelyCommand_label;
                }

                for (IFile eachXpdlFile : allXpdlFilesAffectedByQuickFix) {

                    CompoundCommand cmd = new CompoundCommand();
                    cmd.setLabel(commandLabel);

                    List<Activity> activitiesInXpdl =
                            xpdlFileToActivitiesMap.get(eachXpdlFile);

                    for (Activity activity : activitiesInXpdl) {

                        /*
                         * We wish to set the correlate immediately config only
                         * for those correlating activites which have same
                         * operation.
                         */

                        Event event = activity.getEvent();

                        if (event != null) {
                            /*
                             * Set config for events.
                             */
                            TriggerResultMessage triggerResultMessage =
                                    EventObjectUtil
                                            .getTriggerResultMessage(activity);

                            if (triggerResultMessage != null) {
                                cmd.append(Xpdl2ModelUtil
                                        .getSetOtherAttributeCommand(editingDomain,
                                                triggerResultMessage,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_CorrelateImmediately(),
                                                correlateImmediatelyValue));
                            }
                        } else {
                            /*
                             * Set config for tasks.
                             */
                            Implementation impl = activity.getImplementation();

                            if (impl instanceof Task) {

                                Task task = (Task) impl;

                                TaskReceive taskReceive = task.getTaskReceive();

                                if (taskReceive != null) {

                                    cmd.append(Xpdl2ModelUtil
                                            .getSetOtherAttributeCommand(editingDomain,
                                                    taskReceive,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getDocumentRoot_CorrelateImmediately(),
                                                    correlateImmediatelyValue));
                                }
                            }
                        }
                    }
                    commands.add(cmd);
                }
            }
        }
        return commands;
    }

    /**
     * 
     * @param allActivitiesToApplyQuickFixOn
     *            list of activites on which the quick fix must be applied.
     * @return map of the xpdl file to all the activities in that xpdl file on
     *         which the command must be executed.
     */
    Map<IFile, List<Activity>> getXpdlFileToActivitiesInItMap(
            List<Activity> allActivitiesToApplyQuickFixOn) {

        Map<IFile, List<Activity>> xpdlFileToActivitiesMap =
                new HashMap<IFile, List<Activity>>();

        for (Activity activity : allActivitiesToApplyQuickFixOn) {

            IFile file = WorkingCopyUtil.getFile(activity);

            if (file != null) {

                List<Activity> activitiesInXpdl =
                        xpdlFileToActivitiesMap.get(file);

                if (activitiesInXpdl == null) {
                    /*
                     * If the file entry is not already present in the mao then
                     * add it
                     */
                    List<Activity> newListOfActInXpdl =
                            new ArrayList<Activity>();
                    newListOfActInXpdl.add(activity);
                    xpdlFileToActivitiesMap.put(file, newListOfActInXpdl);

                } else {
                    /*
                     * add the activity to the existing list of activities.
                     */
                    activitiesInXpdl.add(activity);
                }
            }
        }
        return xpdlFileToActivitiesMap;
    }

    /**
     * 
     * @param act
     *            the target activity
     * @param expectedCorrelateImmediatelyValue
     *            the expected value of correlate immediately.
     * @return list of all the correlating activities which do not have the
     *         expected correlate immediately value as that quick fix selected.
     */
    List<Activity> getAllActivitiesToApplyQuickFixOn(Activity act,
            boolean expectedCorrelateImmediatelyValue) {
        List<Activity> allActivitiesToApplyQuickFixOn =
                new ArrayList<Activity>();

        if (expectedCorrelateImmediatelyValue != CorrelateImmediatelyConfigurationRule
                .isActivityConfiguredToCorrelateImmediately(act)) {
            allActivitiesToApplyQuickFixOn.add(act);
        }

        /*
         * Create activity web service reference information from given
         * activity.
         */
        ActivityWebServiceReference webSvcRef =
                ActivityWebServiceReference
                        .createActivityWebServiceReference(act);

        String wsdlNamespace = webSvcRef.getWsdlNamespace();
        String portTypeNamespace = webSvcRef.getPortTypeNamespace();
        String portName = webSvcRef.getPortName();
        String operation = webSvcRef.getOperation();

        /*
         * Get all the activity web service references from the indexer.
         */
        List<ActivityWebServiceReference> webSvcRefs =
                CorrelateImmediatelyConfigurationRule
                        .getIndexedWebServiceReferences();

        for (ActivityWebServiceReference activityWebServiceReference : webSvcRefs) {

            Activity activity = activityWebServiceReference.getActivity();

            if (activity != null
                    && !activity.equals(act)
                    && Xpdl2ModelUtil.isCorrelatingActivity(activity)
                    && expectedCorrelateImmediatelyValue != CorrelateImmediatelyConfigurationRule
                            .isActivityConfiguredToCorrelateImmediately(activity)) {

                String indexedWebServiceActivitiesWsdlNS =
                        activityWebServiceReference.getWsdlNamespace();
                String indexedWebServiceActivitiesPortTypeNS =
                        activityWebServiceReference.getPortTypeNamespace();
                String indexedWebServiceActivitiesPortName =
                        activityWebServiceReference.getPortName();
                String indexedWebServiceActivitiesOperation =
                        activityWebServiceReference.getOperation();

                if (CorrelateImmediatelyConfigurationRule
                        .checkEquals(wsdlNamespace,
                                indexedWebServiceActivitiesWsdlNS)
                        && CorrelateImmediatelyConfigurationRule
                                .checkEquals(portTypeNamespace,
                                        indexedWebServiceActivitiesPortTypeNS)
                        && CorrelateImmediatelyConfigurationRule
                                .checkEquals(portName,
                                        indexedWebServiceActivitiesPortName)
                        && CorrelateImmediatelyConfigurationRule
                                .checkEquals(operation,
                                        indexedWebServiceActivitiesOperation)) {
                    /*
                     * collect the activities.
                     */
                    allActivitiesToApplyQuickFixOn.add(activity);
                }
            }
        }
        return allActivitiesToApplyQuickFixOn;
    }

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     * 
     * @param editingDomain
     * @param target
     * @param marker
     * @return
     * @throws ResolutionException
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        // Do nothing here because we override the run() method.
        return null;
    }

    /**
     * 
     * @return implementers should return <code>true</code> if we wish to SET
     *         the "Correlate Immediately" config , otherwise return
     *         <code>false</code> to UNSET it.
     */
    abstract protected boolean correlateImmediatelyValue();

    /**
     * 
     * Resolution class to set the "Correlate Immediately" config of the
     * correlating activity under focus.
     * 
     * @author kthombar
     * @since Dec 9, 2014
     */
    public static class SetCorrelateImmediatelyConfigResolution extends
            AbstractSetUnsetCorrelateImmediatelyConfigResolution {

        /**
         * @see com.tibco.bx.validation.resolutions.AbstractSetUnsetCorrelateImmediatelyConfigResolution#correlateImmediatelyValue()
         * 
         * @return
         */
        @Override
        protected boolean correlateImmediatelyValue() {

            return true;
        }
    }

    /**
     * Resolution class to unset the "Correlate Immediately" config of the
     * correlating activity under focus.
     * 
     * 
     * @author kthombar
     * @since Dec 9, 2014
     */
    public static class UnsetCorrelateImmediatelyConfigResolution extends
            AbstractSetUnsetCorrelateImmediatelyConfigResolution {

        /**
         * @see com.tibco.bx.validation.resolutions.AbstractSetUnsetCorrelateImmediatelyConfigResolution#correlateImmediatelyValue()
         * 
         * @return
         */
        @Override
        protected boolean correlateImmediatelyValue() {

            return false;
        }
    }
}
