/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.provider;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorConfigurationUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.destinations.DestinationsActivator;
import com.tibco.xpd.destinations.preferences.DestinationPreferences;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.validation.destinations.Destination;
import com.tibco.xpd.validation.internal.LiveValidationItem;
import com.tibco.xpd.validation.provider.IScopeProvider;
import com.tibco.xpd.validation.provider.IValidationItem;
import com.tibco.xpd.xpdExtension.DurationCalculation;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * The ExpressionScopeProvider is intended to provide performance benefits for
 * rules that can run in isolation on java script expressions. It returns all
 * the Expression objects when any change affects the expressions.
 * <p>
 * PLEASE NOTE: This scope provider returns ONLY xpdl2 Expressions affected by
 * the set of changed objects that we are validating (NOT all expressions in the
 * entire process).
 * 
 * 
 * 
 * @author agondal
 * @since 4 Sep 2013
 */
public class ExpressionScopeProvider implements IScopeProvider {

    /**
     * We will check if changed object is in an interesting ancestor and if so,
     * then we will re-validate just the expression contained within the changed
     * object.
     * 
     * In this way we effectively limit the maximum number of expressions to
     * re-validate to the number that can appear within an interesting ancestor.
     * 
     * If we are only interested in changes in the child objects of a changed
     * object (e.g., Activity) then we can set
     * {@link InterestingAncestorType#onlyInterestedInChildChanges}=true so that
     * anything that is set directly on Activity (such as name) will not cause
     * re-validation of all scripts under Activity because it is both the
     * changed object and an interesting ancestor. If its set to false then we
     * get all expressions under the changed object that is 'the' or 'is
     * contained within' the interesting ancestor.
     * 
     * IF we find that we are still re-validating too much THEN we can REFINE
     * interestingAncestorClassTypes list to be more specific (like
     * xpdExt:Audit, loop scripts container etc).
     */
    private static class InterestingAncestorType {
        Class type;

        boolean onlyInterestedInChildChanges;

        /**
         * @param type
         * @param onlyInterestedInChildChanges
         */
        public InterestingAncestorType(Class type,
                boolean onlyInterestedInChildChanges) {
            super();
            this.type = type;
            this.onlyInterestedInChildChanges = onlyInterestedInChildChanges;
        }

    }

    private InterestingAncestorType[] interestingAncestorClassTypes = {
            new InterestingAncestorType(Activity.class, true),
            new InterestingAncestorType(Transition.class, false),
            new InterestingAncestorType(DurationCalculation.class, false) };

    /**
	 * Return <code>true</code> if we should validate all expressions under the given changed object.
	 * <p>
	 * Nominally we check if changed object is something in an object with a limited scope that can contain expressions
	 * (i.e. activity and transitions but NOT whole process). This allows us to be future proof enough to catch any
	 * expression under the {@link InterestingAncestorType} type objects WITHOUT re-validating all expressions in a
	 * large object like the whole process.
	 * <p>
	 * In some cases (Activity), we want to future proof to catch any change under activity but because all scripts are
	 * grand children of it we don't want to re-validate all expressions in whole activity for a minor change directly
	 * in activity. In those cases we set the {@link InterestingAncestorType#onlyInterestedInChildChanges}=true to
	 * prevent re-validating everything under activity IF activity is the changed object. Therefore we re-validate only
	 * the expressions under the changed child objects of activity.
	 * 
	 * ACE-7402: Changed to protected so that extending classes can override the implementation.
	 * 
	 * @param changedObject
	 * @param notifications
	 *            Notifications received for the changedObject
	 * @return <code>true</code> if should validate all expressions under the given changed object.
	 */
	protected boolean shouldValidateChangedObjectExpressions(EObject changedObject, Collection<Notification> notifications)
	{
        for (InterestingAncestorType interestingClassType : interestingAncestorClassTypes) {

            EObject anc =
                    Xpdl2ModelUtil.getAncestor(changedObject,
                            interestingClassType.type);
            if (anc != null) {

                if (interestingClassType.onlyInterestedInChildChanges) {
                    if (anc != changedObject) {
                        /*
                         * Is child underneath ancestor that we only validate
                         * when child changes, so re-validate.
                         */
                        return true;
                    }
                } else {
                    /*
                     * InterestingClassType Don't care if changed object was the
                     * interesting ancestor itself, so need to validate the
                     * expression.
                     */
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param destination
     *            The destination environment.
     * @param providerId
     *            The validation provider ID.
     * @param item
     *            The validation item.
     * @return A collection of objects that will need validation.
     * @see com.tibco.xpd.validation.provider.IScopeProvider#getAffectedObjects(com.tibco.xpd.validation.engine.ValidationItem)
     */
    @Override
    public Collection<EObject> getAffectedObjects(Destination destination,
            String providerId, IValidationItem item) {
        Collection<EObject> affectedObjects = new LinkedHashSet<EObject>();
        Collection<EObject> objects = item.getObjects();
        /*
         * The given validation destination is now abstracted from the
         * destination stored in xpdl2 (via the global destination environment).
         * 
         * Therefore when asking 'is this validation dest environment enabled'
         * we must find the global dest envs that bind to it and ask if they are
         * enabled.
         */
        DestinationPreferences preferences =
                DestinationsActivator.getDefault().getDestinationPreferences();
        Set<String> globalNames =
                preferences
                        .getGlobalDestinationsForValidationDestination(destination
                                .getId());

        // live validation
        /* Sid XPD-5218: For some live validaitons, notifications can be null! */
        if (item instanceof LiveValidationItem
                && ((LiveValidationItem) item).getNotifications() != null
                && !((LiveValidationItem) item).getNotifications().isEmpty()) {

            for (EObject changedObject : objects) {
                /*
                 * If data changes then re-validate all expressions in the scope
                 * of that data.
                 */
                if (changedObject instanceof ProcessRelevantData) {

                    EObject parent = changedObject.eContainer();
                    if (parent != null) {
                        affectedObjects.addAll(getAffectedExpressions(parent,
                                destination,
                                providerId,
                                globalNames));
                    }

                } else {
                    /*
                     * Check if changedObject is at a level which we would like
                     * to say re-validate all expressions within changedObject
                     * and if so, get all those expressions and add to the
                     * list-to-validate.
                     */
					if (shouldValidateChangedObjectExpressions(changedObject,
							((LiveValidationItem) item).getNotifications()))
					{
                        affectedObjects
                                .addAll(getAffectedExpressions(changedObject,
                                        destination,
                                        providerId,
                                        globalNames));
                    }
                }
            }

            /*
             * Sid XPD-5218. Addition/Deletion of data does not re-validate
             * scripts in above loop because it won't appear as a changed object
             * in validation item. Look thru notifications instead.
             */
            for (Notification notification : ((LiveValidationItem) item)
                    .getNotifications()) {
                if (notification.getEventType() == Notification.REMOVE) {
                    if (notification.getOldValue() instanceof ProcessRelevantData) {
                        Object parent = notification.getNotifier();

                        if (parent instanceof EObject) {
                            affectedObjects
                                    .addAll(getAffectedExpressions((EObject) parent,
                                            destination,
                                            providerId,
                                            globalNames));
                        }
                    }
                } else if (notification.getEventType() == Notification.ADD) {
                    if (notification.getNewValue() instanceof ProcessRelevantData) {
                        Object parent = notification.getNotifier();

                        if (parent instanceof EObject) {
                            affectedObjects
                                    .addAll(getAffectedExpressions((EObject) parent,
                                            destination,
                                            providerId,
                                            globalNames));
                        }
                    }
                }
            }

            /*
             * System.out.println("==========================================");
             * //$NON-NLS-1$ for (EObject eObject : affectedObjects) { String
             * name = null; NamedElement namedAnc = (NamedElement)
             * Xpdl2ModelUtil.getAncestor(eObject, NamedElement.class); if
             * (namedAnc != null) { name = namedAnc.getName(); }
             * 
             * System.out .println(String
             * .format(" Validating Script In '%s': %s", name == null ? "" :
             * name, EcoreUtil.getURI(eObject).toString())); //$NON-NLS-1$
             * //$NON-NLS-2$
             * 
             * }
             * System.out.println("==========================================");
             * //$NON-NLS-1$
             */
        } else {
            /*
             * Complete validate (validate on save&build)
             */
            WorkingCopy workingCopy = item.getWorkingCopy();
            if (workingCopy != null
                    && workingCopy.getRootElement() instanceof Package) {

                affectedObjects
                        .addAll(getAffectedExpressions(workingCopy
                                .getRootElement(),
                                destination,
                                providerId,
                                globalNames));
            }
        }

        return affectedObjects;
    }

    /**
     * @param eo
     *            The object to get affected expressions for.
     * @param destination
     *            The destination environment.
     * @param providerId
     * @param globalNames
     * @return The expression objects beneath the given EObject.
     */
    private Collection<EObject> getAffectedExpressions(EObject eo,
            Destination destination, String providerId, Set<String> globalNames) {

        Collection<EObject> affected = new HashSet<EObject>();

        if (eo instanceof Package) {

            for (Process proc : ((Package) eo).getProcesses()) {
                affected.addAll(getAffectedExpressions(proc,
                        destination,
                        providerId,
                        globalNames));
            }

        } else if (eo instanceof ProcessInterface) {

            Package pckg = Xpdl2ModelUtil.getPackage(eo);
            String processInterfaceId = ((ProcessInterface) eo).getId();
            for (Process proc : pckg.getProcesses()) {

                if (processInterfaceId.equals(ProcessInterfaceUtil
                        .getImplementedProcessInterfaceId(proc))) {
                    affected.addAll(getAffectedExpressions(proc,
                            destination,
                            providerId,
                            globalNames));
                }
            }
        } else if (eo instanceof Process) {

            Process process = (Process) eo;
            if (isValidationEnabledForProcess(process,
                    destination,
                    providerId,
                    globalNames)) {
                addAffectedExpressions(eo, affected);

            }

        } else {

            Process process = Xpdl2ModelUtil.getProcess(eo);
            if (process != null) {

                if (isValidationEnabledForProcess(process,
                        destination,
                        providerId,
                        globalNames)) {
                    addAffectedExpressions(eo, affected);

                    if (eo instanceof Activity) {
                        if (((Activity) eo).getBlockActivity() != null) {
                            ActivitySet activitySet =
                                    Xpdl2ModelUtil
                                            .getEmbeddedSubProcessActivitySet((Activity) eo);
                            if (activitySet != null) {
                                addAffectedExpressions(activitySet, affected);
                            }
                        }
                    }
                }
            }

        }

        return affected;
    }

    /**
     * 
     * @param process
     * @param destination
     * @param providerId
     * @param globalNames
     * @return
     */
    private boolean isValidationEnabledForProcess(Process process,
            Destination destination, String providerId, Set<String> globalNames) {
        if (!destination.isSelectable() || isEnabled(globalNames, process)) {
            if (!ProcessEditorConfigurationUtil
                    .isExcludedValidationProvider(process,
                            destination.getId(),
                            providerId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param eo
     *            The object to get affected expressions for.
     * @param affected
     *            To add the affected expression objects beneath the given
     *            EObject.
     */
    private void addAffectedExpressions(EObject eo, Collection<EObject> affected) {

        TreeIterator<Object> allContents =
                EcoreUtil.getAllProperContents(eo, false);
        for (Iterator iterator = allContents; iterator.hasNext();) {
            Object o = iterator.next();
            if (o instanceof Expression) {
                affected.add((EObject) o);
            }
        }
    }

    /**
     * @param destination
     *            The process destination environment.
     * @param process
     *            The process.
     * @return <code>true</code> if the environment is enabled for this process,
     *         <code>false</code> otherwise
     */
    private boolean isEnabled(Set<String> globalNames, Process process) {
        boolean enabled = false;

        for (String name : globalNames) {
            if (DestinationUtil.isGlobalDestinationEnabled(process, name)) {
                enabled = true;
                break;
            }
        }
        return enabled;

    }
}
