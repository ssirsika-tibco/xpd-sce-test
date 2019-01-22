/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.ActivityRef;
import com.tibco.xpd.xpdExtension.SeparationOfDutiesActivities;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author bharge
 * 
 */
public class SeparationOfDutiesCommandActions extends
        AbstractResourcePatternsCommandActions {

    private EObject currentContextObject = null;

    public SeparationOfDutiesCommandActions() {

    }

    public SeparationOfDutiesCommandActions(EObject currentContextObject) {
        super();
        this.currentContextObject = currentContextObject;
    }

    @Override
    public CompoundCommand deleteTasksOrGroup(ISelection selection,
            CompoundCommand cCmd) {

        List<SeparationOfDutiesActivities> separations =
                new ArrayList<SeparationOfDutiesActivities>();
        List<ActivityRef> refs = new ArrayList<ActivityRef>();

        SeparationOfDutiesActivities sepActivities = null;
        EditingDomain ed = null;

        if (selection instanceof StructuredSelection) {
            Object object = ((StructuredSelection) selection).getFirstElement();
            if (object instanceof EObject) {
                ed = WorkingCopyUtil.getEditingDomain((EObject) object);
            }
        }

        if (selection instanceof StructuredSelection) {
            StructuredSelection structured = (StructuredSelection) selection;
            for (Object next : structured.toList()) {
                if (next instanceof SeparationOfDutiesActivities) {
                    sepActivities = (SeparationOfDutiesActivities) next;
                    separations.add(sepActivities);
                } else if (next instanceof ActivityRef) {
                    ActivityRef ref = (ActivityRef) next;

                    /**
                     * Only delete activity ref if the context object is NOT
                     * activity (i.e. process is selected) OR the activity ref
                     * is not for the selected activity.
                     */
                    if (!(currentContextObject instanceof Activity)
                            || !((Activity) currentContextObject).getId()
                                    .equals(ref.getIdRef())) {
                        refs.add(ref);
                    }
                }
            }
        }

        for (ActivityRef ref : refs) {
            cCmd.append(RemoveCommand.create(ed,
                    sepActivities,
                    XpdExtensionPackage.eINSTANCE
                            .getSeparationOfDutiesActivities_ActivityRef(),
                    ref));
        }
        // if (refs.size() > 0){
        // cCmd.append(new ResourcePatternsCommand(ed, sepActivities, refs,
        // null));
        // }

        for (SeparationOfDutiesActivities separation : separations) {
            cCmd.append(RemoveCommand.create(ed, separation));
        }
        return cCmd;
    }

    @Override
    public CompoundCommand addTasksToGroup(ISelection selection,
            CompoundCommand cCmd) {
        SeparationOfDutiesActivities sepOfDutiesActivities = null;
        // Activity activity = null;
        EditingDomain ed = null;
        Object input = null;

        if (selection instanceof StructuredSelection) {

            Object object = ((StructuredSelection) selection).getFirstElement();
            if (object instanceof EObject) {
                ed = WorkingCopyUtil.getEditingDomain((EObject) object);
                /* setting the input to process */
                input = Xpdl2ModelUtil.getProcess((EObject) object);
            }
            /**
             * setting the input to Activity if the object is instance of
             * ActivityRef
             */
            if (object instanceof ActivityRef) {
                ActivityRef actRef = (ActivityRef) object;
                input = actRef.getActivity();
            }
        }

        if (selection instanceof StructuredSelection) {
            StructuredSelection structuredSelection =
                    (StructuredSelection) selection;
            for (Object next : structuredSelection.toList()) {
                if (next instanceof SeparationOfDutiesActivities) {
                    sepOfDutiesActivities = (SeparationOfDutiesActivities) next;
                } else if (next instanceof ActivityRef) {
                    ActivityRef actRef = (ActivityRef) next;
                    EObject container = actRef.eContainer();
                    if (container instanceof SeparationOfDutiesActivities) {
                        sepOfDutiesActivities =
                                (SeparationOfDutiesActivities) container;
                    }
                }
                /**
                 * retrieving the activities from the selected group
                 */
                EList<ActivityRef> activityRef =
                        sepOfDutiesActivities.getActivityRef();
                /**
                 * for an activity task group, the selected task must not be
                 * shown in the initial selection list in the picker, otherwise
                 * all the tasks in the group retrieved above are shown in the
                 * initial selection list
                 */
                List<Activity> activityList = new ArrayList<Activity>();

                for (ActivityRef activityRef2 : activityRef) {
                    activityList.add(activityRef2.getActivity());
                    /**
                     * uncomment this block and delete the above line if you
                     * dont want the selected activity to be in the list
                     */
                    // if (input instanceof Activity) {
                    // activity = (Activity) input;
                    // if (!activityRef2.getActivity().equals(activity)) {
                    // activityList.add(activityRef2.getActivity());
                    // }
                    // } else if (input instanceof Process) {
                    // activityList.add(activityRef2.getActivity());
                    // }
                }

                Object[] pickedTasks =
                        ResourcePatternsActivityPicker.pickActivities(input,
                                activityList);

                if (null != pickedTasks) {

                    /**
                     * when all the initial selections are removed from the
                     * picker, then we delete the separation of duties
                     * activities group and we dont have any activity refs to
                     * create so we return the cCmd
                     */
                    if (pickedTasks.length == 0 && input instanceof Process) {
                        cCmd.append(RemoveCommand.create(ed,
                                sepOfDutiesActivities));
                        return cCmd;
                    }

                    /**
                     * checking if there any changes in the existing activities
                     * and the selected activities. if no changes take place
                     * then we do nothing and return the command
                     */
                    boolean noChangesOccured = false;
                    noChangesOccured =
                            findForChanges(pickedTasks, activityList);
                    if (noChangesOccured) {
                        return cCmd;
                    }

                    /**
                     * first remove the existing ones from the group. then add
                     * the ones from the picked tasks
                     */
                    List<ActivityRef> activityRefsToRemove =
                            removeExistingActivityRefsBeforeAdd(sepOfDutiesActivities,
                                    activityList,
                                    pickedTasks);

                    if (!activityRefsToRemove.isEmpty()) {

                        /**
                         * calling the RemoveCommand directly was causing
                         * problem when we play with selections in the picker.
                         * eg. if UT1 and UT2 are the initial selections and in
                         * the picker you remove UT1, retain UT2 and select UT3,
                         * then in actuality we are removing UT2 and creating
                         * again which gives transaction already closing error.
                         * Hence created our own ResourcePatternsCommand which
                         * helped solving this issue while removing old ones and
                         * creating new ones
                         */
                        // cCmd
                        // .append(RemoveCommand
                        // .create(ed,
                        // sepOfDutiesActivities,
                        // XpdExtensionPackage.eINSTANCE
                        // .getSeparationOfDutiesActivities_ActivityRef(),
                        // activityRefsToRemove));
                        cCmd.append(new ResourcePatternsCommand(ed,
                                sepOfDutiesActivities, activityRefsToRemove,
                                null));
                    }

                    List<ActivityRef> newActivityRefsToCreate =
                            new ArrayList<ActivityRef>();

                    for (Object object : pickedTasks) {
                        if (object instanceof Activity) {
                            Activity act = (Activity) object;
                            ActivityRef activRef =
                                    XpdExtensionFactory.eINSTANCE
                                            .createActivityRef();
                            activRef.setIdRef(act.getId());
                            newActivityRefsToCreate.add(activRef);
                        }
                    }

                    /**
                     * if sectionInputIsActivity and newActivityRefsToCreate
                     * does not contain sectionInputActivity.getId() then add it
                     * to newActivityRefsToCreate
                     */

                    if (currentContextObject instanceof Activity) {
                        Activity selectedActivity =
                                (Activity) currentContextObject;
                        boolean found = false;
                        for (ActivityRef activityRef2 : newActivityRefsToCreate) {
                            if (activityRef2.getIdRef().equals(selectedActivity
                                    .getId())) {
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            ActivityRef activRef =
                                    XpdExtensionFactory.eINSTANCE
                                            .createActivityRef();
                            activRef.setIdRef(selectedActivity.getId());
                            newActivityRefsToCreate.add(activRef);
                        }
                    }

                    // cCmd
                    // .append(AddCommand
                    // .create(ed,
                    // sepOfDutiesActivities,
                    // XpdExtensionPackage.eINSTANCE
                    // .getSeparationOfDutiesActivities_ActivityRef(),
                    // newActivityRefsToCreate));
                    cCmd.append(new ResourcePatternsCommand(ed,
                            sepOfDutiesActivities, null,
                            newActivityRefsToCreate));
                }
            }
        }

        return cCmd;
    }

    /**
     * @param sepOfDutiesActivites
     * @param text
     */
    @Override
    public void updateName(Object object, String oldValue, String value) {
        if (object instanceof SeparationOfDutiesActivities) {
            SeparationOfDutiesActivities sepOfDutiesActivites =
                    (SeparationOfDutiesActivities) object;

            if (!value.equals(oldValue)) {
                CompoundCommand command =
                        new CompoundCommand(
                                Messages.RenameSeparationOfDutiesAction_EditSODNameCommand);
                EditingDomain ed =
                        WorkingCopyUtil.getEditingDomain(sepOfDutiesActivites);
                /**
                 * restricting the length of the group name to 200 characters
                 * */
                if (value.length() > 200) {
                    value = value.substring(0, 200);
                }
                if (ed != null) {
                    if (sepOfDutiesActivites.eContainer() != null) {
                        command.append(SetCommand.create(ed,
                                sepOfDutiesActivites,
                                Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                                value));
                    }
                    if (command != null && command.canExecute()) {
                        ed.getCommandStack().execute(command);
                    }
                }
            }
        }
    }
}
