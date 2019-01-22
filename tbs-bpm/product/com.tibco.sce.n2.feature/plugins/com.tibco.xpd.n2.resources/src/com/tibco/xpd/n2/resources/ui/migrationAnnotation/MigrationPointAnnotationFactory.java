/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.n2.resources.ui.migrationAnnotation;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.NotificationFilter;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.ResourceSetListener;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

import com.tibco.xpd.n2.resources.BundleActivator;
import com.tibco.xpd.n2.resources.util.MigrationPointUtil;
import com.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter;
import com.tibco.xpd.processwidget.annotations.AbstractImageAnnotationFigure;
import com.tibco.xpd.processwidget.annotations.AnnotationFactoryEx;
import com.tibco.xpd.processwidget.annotations.AnnotationListener;
import com.tibco.xpd.processwidget.parts.BaseFlowNodeEditPart;
import com.tibco.xpd.processwidget.parts.EventEditPart;
import com.tibco.xpd.processwidget.parts.GatewayEditPart;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.xpdExtension.ValidationControl;
import com.tibco.xpd.xpdExtension.ValidationIssueOverride;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Process diagram annotation factory for placing icon on process activities
 * that are valid AMX BPM process-instance migration points.
 * <p>
 * There is one factory per-editor session. Each factory use the process-engine
 * provided flow analyser to discover migration points and place an appropriate
 * icon to indicate this over the migration point activities.
 * <p>
 * Each factory listens to the EMF editing domain for post-commit events and
 * will refresh the annotation figures when something within the editor's
 * process changes.
 * 
 * @author aallway
 * @since 14 Jun 2012
 */
public class MigrationPointAnnotationFactory extends AnnotationFactoryEx {

    private MigrationAnnotationProcessChangedListener processModelChangedListener;

    private ProcessDiagramAdapter processDiagramAdapter;

    private EditPartViewer editPartViewer;

    /**
     * A map of ALL XPDL activities in the process to the class for creating /
     * removing annotations.
     */
    private Map<Activity, MigrationPointAnnotationAndStatus> activityToAnnotationCreatorMap =
            new HashMap<Activity, MigrationPointAnnotationAndStatus>();

    /**
     * Ok, this is a bit gross but it's forced upon us by the
     * {@link AnnotationFactoryEx} lifecycle. Basically, on
     * {@link #registerListener(AnnotationListener)} we are given an
     * {@link AnnotationListener} which we use to get the annotation create when
     * we want to (we call {@link AnnotationListener#createAnnotations()}).
     * <p>
     * This method does a "call back" into our
     * {@link #createFigureEx(AnnotationListener, EditPart)} method but does not
     * return the figure returned by
     * {@link #createFigureEx(AnnotationListener, EditPart)}.
     * <p>
     * So we simply set this class field around a call to
     * {@link AnnotationListener#createAnnotations()} so that
     * {@link #createFigureEx(AnnotationListener, EditPart)} can tell it what
     * the associated figure created was.
     */
    private MigrationPointAnnotationAndStatus annotationBeingCreated = null;

    /**
     * Listens for changes to migration annotation visibility user-preference.
     */
    private MigrationAnnotationVisibilityChangeListener annotationVisibilityChangeListener;

    /**
     * The last known set of migration point activities.
     */
    private Set<Activity> previousMigrationPointActivities = Collections
            .emptySet();

    int figureCount = 0;

    static int overAllCount = 0;

    /**
     * @param processDiagramAdapter
     * @param editPartViewer
     */
    public MigrationPointAnnotationFactory(
            ProcessDiagramAdapter processDiagramAdapter,
            EditPartViewer editPartViewer) {

        this.processDiagramAdapter = processDiagramAdapter;
        this.editPartViewer = editPartViewer;

        Object hp = processDiagramAdapter.getProcess();

        if (hp instanceof Process) {
            Process hostProcess = (Process) hp;

            previousMigrationPointActivities =
                    getMigrationPointActivities(hostProcess);
        }

        TransactionalEditingDomain editingDomain =
                XpdResourcesPlugin.getDefault().getEditingDomain();

        /*
         * Add a post-commit listener to the editing domain.
         * 
         * This will listen for change commit's to the emf model and
         * re-calculate the migration points.
         */
        processModelChangedListener =
                new MigrationAnnotationProcessChangedListener();

        editingDomain.addResourceSetListener(processModelChangedListener);

        /*
         * Add a listener to preference store so we can react to user setting
         * the nominal visibility of migration annotation icons.
         */
        annotationVisibilityChangeListener =
                new MigrationAnnotationVisibilityChangeListener();

        BundleActivator.getDefault().getPreferenceStore()
                .addPropertyChangeListener(annotationVisibilityChangeListener);

    }

    /**
     * @param process
     * @return The activities that are migration points IF this is a business
     *         process and has AMX BPM destination.
     */
    private Set<Activity> getMigrationPointActivities(Process process) {
        if (MigrationPointUtil.doMigrationPointsApply(process)) {
            return MigrationPointUtil.getMigrationPointActivities(process);
        }
        return new HashSet<Activity>();
    }

    /**
     * @see com.tibco.xpd.processwidget.annotations.AnnotationFactory#registerListener(com.tibco.xpd.processwidget.annotations.AnnotationListener)
     * 
     * @param annotationCreator
     */
    @Override
    public void registerListener(AnnotationListener annotationCreator) {
        // System.out
        // .println(String.format("%s[%d].%s(%s)",
        // this.getClass().getSimpleName(),
        // this.hashCode(),
        // "registerListener",
        // listener));

        /*
         * Keep a map of activity model object to the class used to remove or
         * add it's annotations.
         */
        if (annotationCreator.getHostEditPart() instanceof BaseFlowNodeEditPart) {
            if (annotationCreator.getModelObject() instanceof Activity) {
                Activity activity =
                        (Activity) annotationCreator.getModelObject();

                MigrationPointAnnotationAndStatus migrationPointAnnotationAndStatus =
                        activityToAnnotationCreatorMap.get(activity);

                if (migrationPointAnnotationAndStatus != null) {
                    /*
                     * Urk! An annotation factory is already registered for this
                     * activity, this should never happen, just to be on the
                     * safe side, clean up after it, then remove the old one
                     */
                    if (migrationPointAnnotationAndStatus.isAnnotationCreated()) {
                        migrationPointAnnotationAndStatus.removeAnnotation();
                        activityToAnnotationCreatorMap
                                .remove(migrationPointAnnotationAndStatus);
                    }
                }

                /*
                 * Keep a map of activity to it's annotation creator (and status
                 * of the annotation for it)
                 */
                migrationPointAnnotationAndStatus =
                        new MigrationPointAnnotationAndStatus(annotationCreator);

                activityToAnnotationCreatorMap.put((Activity) annotationCreator
                        .getModelObject(), migrationPointAnnotationAndStatus);

                if (previousMigrationPointActivities.contains(activity)) {
                    /*
                     * Last time we updated list of migration point activities,
                     * this activity was one, so create the annotation figure
                     * for it now! The annotationListener.createAnnotations()
                     * method will call back into our createFigureEx() method.
                     * 
                     * This might happen if the diagram editor get's
                     * notification of a newly added activity and creates the
                     * annotation edit policy and calls this registerListener()
                     * AFTER we receive notification for the same in
                     * MigrationProcessChangedListener.
                     */
                    migrationPointAnnotationAndStatus.createAnnotation();
                }
            }
        }
    }

    /**
     * @see com.tibco.xpd.processwidget.annotations.AnnotationFactory#unregisterListener(com.tibco.xpd.processwidget.annotations.AnnotationListener)
     * 
     * @param annotationCreator
     */
    @Override
    public void unregisterListener(AnnotationListener annotationCreator) {
        // System.out.println(String.format("%s[%d].%s(%s)",
        // this.getClass().getSimpleName(),
        // this.hashCode(),
        // "unregisterListener",
        // listener));

        /*
         * See if we have created annotation for this model object and remove it
         * from our map (and remove it's annotations).
         */
        if (annotationCreator.getModelObject() instanceof Activity) {
            Activity activity = (Activity) annotationCreator.getModelObject();

            MigrationPointAnnotationAndStatus migrationPointAnnotationAndStatus =
                    activityToAnnotationCreatorMap.get(activity);

            if (migrationPointAnnotationAndStatus != null) {
                if (migrationPointAnnotationAndStatus.isAnnotationCreated()) {
                    migrationPointAnnotationAndStatus.removeAnnotation();
                }

                activityToAnnotationCreatorMap.remove(activity);
            }
        }
    }

    /**
     * @see com.tibco.xpd.processwidget.annotations.AnnotationFactory#disposeFactory()
     * 
     */
    @Override
    public void disposeFactory() {
        // System.out.println(String.format("%s[%d].%s()", this.getClass()
        // .getSimpleName(), this.hashCode(), "disposeFactory"));

        if (processModelChangedListener != null) {
            XpdResourcesPlugin.getDefault().getEditingDomain()
                    .removeResourceSetListener(processModelChangedListener);
            processModelChangedListener = null;
        }

        if (annotationVisibilityChangeListener != null) {
            BundleActivator
                    .getDefault()
                    .getPreferenceStore()
                    .removePropertyChangeListener(annotationVisibilityChangeListener);
            annotationVisibilityChangeListener = null;
        }
    }

    /**
     * @see com.tibco.xpd.processwidget.annotations.AnnotationFactoryEx#createFigureEx(com.tibco.xpd.processwidget.annotations.AnnotationListener,
     *      org.eclipse.gef.EditPart)
     * 
     * @param listener
     * @param hostEditPart
     * @return
     */
    @Override
    public IFigure createFigureEx(AnnotationListener listener,
            EditPart hostEditPart) {

        if (hostEditPart instanceof BaseFlowNodeEditPart) {
            // System.out.println(String.format("%s[%d].%s(%s, %s)",
            // this.getClass().getSimpleName(),
            // this.hashCode(),
            // "createFigureEx",
            // listener,
            // hostEditPart));

            IFigure diagramObjectFigure =
                    ((BaseFlowNodeEditPart) hostEditPart).getFigure();

            AbstractMigrationPointAnnotationFigure fig;

            if (hostEditPart instanceof GatewayEditPart) {
                fig =
                        new GatewayMigrationPointAnnotationFigure(
                                diagramObjectFigure);
            } else if (hostEditPart instanceof EventEditPart) {
                fig =
                        new EventMigrationPointAnnotationFigure(
                                diagramObjectFigure);
            } else {
                fig =
                        new TaskMigrationPointAnnotationFigure(
                                diagramObjectFigure);
            }

            /**
             * Ok, this is a bit gross but it's forced upon us by the
             * {@link AnnotationFactoryEx} lifecycle. Basically, on
             * {@link #registerListener(AnnotationListener)} we are given an
             * {@link AnnotationListener} which we use to get the annotation
             * create when we want to (we call
             * {@link AnnotationListener#createAnnotations()}).
             * <p>
             * This method does a "call back" into our
             * {@link #createFigureEx(AnnotationListener, EditPart)} method but
             * does not return the figure returned by
             * {@link #createFigureEx(AnnotationListener, EditPart)}.
             * <p>
             * So we simply set this class field around a call to
             * {@link AnnotationListener#createAnnotations()} so that
             * {@link #createFigureEx(AnnotationListener, EditPart)} can tell it
             * what the associated figure created was.
             */
            if (annotationBeingCreated != null) {
                annotationBeingCreated.setAnnotationFigure(fig);
            } else {
                BundleActivator
                        .getDefault()
                        .getLogger()
                        .error(this.getClass().getSimpleName()
                                + ": The createFigureEx() method was called outside of annotationBeingCreated being set."); //$NON-NLS-1$
            }

            /* For debug */
            figureCount++;
            overAllCount++;
            // System.out.println(this.hashCode() +
            // ":AddedFigure ThisClassCount("
            // + figureCount + ") " + "  OverAll(" + overAllCount + ")");
            return fig;
        }

        return null;
    }

    /**
     * @see com.tibco.xpd.processwidget.annotations.AnnotationFactoryEx#removingAnnotationFigure(com.tibco.xpd.processwidget.annotations.AnnotationListener,
     *      org.eclipse.draw2d.IFigure, org.eclipse.draw2d.IFigure)
     * 
     * @param listener
     * @param hostFigure
     * @param annotationFigure
     */
    @Override
    public void removingAnnotationFigure(AnnotationListener listener,
            IFigure hostFigure, IFigure annotationFigure) {
        // System.out.println(String.format("%s[%d].%s(%s, %s, %s)",
        // this.getClass().getSimpleName(),
        // this.hashCode(),
        // "removingAnnotationFigure",
        // listener,
        // hostFigure,
        // annotationFigure));

        if (annotationFigure instanceof AbstractImageAnnotationFigure) {
            ((AbstractImageAnnotationFigure) annotationFigure)
                    .aboutToRemove(hostFigure);

            /* For debug */
            figureCount--;
            overAllCount--;
            // System.out.println(this.hashCode()
            // + ":RemovedFigure ThisClassCount(" + figureCount + ") "
            // + "  OverAll(" + overAllCount + ")");
        }
    }

    /**
     * Get the migration points activities for the process and create the
     * annotation for any that haven't been created yet. And remove the
     * migration point annotation for any that have been created but aren't
     * migration points any more.
     * 
     * @param hostProcess
     */
    private void updateMigrationAnnotations(Process hostProcess) {
        Set<Activity> latestMigrationPointActivities =
                getMigrationPointActivities(hostProcess);

        /*
         * Get a list of the last set of migration points we had and remove all
         * activities that are still migration points from it. This is then the
         * set of migration points we need to remove annotations for.
         */
        Set<Activity> migrationPointsToRemove =
                new HashSet<Activity>(previousMigrationPointActivities);
        migrationPointsToRemove.removeAll(latestMigrationPointActivities);

        for (Activity activity : migrationPointsToRemove) {
            MigrationPointAnnotationAndStatus migrationPointAnnotationAndStatus =
                    activityToAnnotationCreatorMap.get(activity);

            /*
             * When activity is removed it is possible that unregisterListener()
             * gets called before the post-commit, in which case it will already
             * have had it's annotations removed and it's annotationCreator map
             * entry removed too. So may not have it cached for this activity
             * anymore; if we do then we'll remove the annotation.
             */
            if (migrationPointAnnotationAndStatus != null
                    && migrationPointAnnotationAndStatus.isAnnotationCreated()) {
                migrationPointAnnotationAndStatus.removeAnnotation();
            }
        }

        /*
         * Now create the annotations for any of the latest set of migration
         * point activities that aren't already created
         */
        for (Activity activity : latestMigrationPointActivities) {
            MigrationPointAnnotationAndStatus migrationPointAnnotationAndStatus =
                    activityToAnnotationCreatorMap.get(activity);

            /*
             * The activity may not be in the annotationCreator map if we
             * process the post-commit before the editor adds edit part for new
             * activity (and hence ends up calling registerListener()). In that
             * situation the annotation will be created during registerListener,
             * so we won't need to do anything here.
             */
            if (migrationPointAnnotationAndStatus != null
                    && !migrationPointAnnotationAndStatus.isAnnotationCreated()) {
                migrationPointAnnotationAndStatus.createAnnotation();
            }
        }

        /*
         * Finally, reference the latest migration points as being previous ones
         * processed (as they are now!).
         */
        previousMigrationPointActivities = latestMigrationPointActivities;
    }

    /**
     * Post commit listener for changes to the process that we are annotating.
     * <p>
     * Adds or removes migration point icon as appropriate.
     * 
     * @author aallway
     * @since 14 Jun 2012
     */
    private final class MigrationAnnotationProcessChangedListener implements
            ResourceSetListener {
        @Override
        public void resourceSetChanged(ResourceSetChangeEvent event) {

            /*
             * Check if any of the notifications effect
             */
            Object hp = processDiagramAdapter.getProcess();

            if (hp instanceof Process) {
                Process hostProcess = (Process) hp;

                boolean eventsAffectHostProcess = false;

                for (Notification notification : event.getNotifications()) {
                    // AbstractProcessPreCommitContributor
                    // .outputNotfication(notification);

                    eventsAffectHostProcess =
                            getAffectedProcess(notification, hostProcess);
                    if (eventsAffectHostProcess) {
                        break;
                    }
                }

                if (eventsAffectHostProcess) {
                    updateMigrationAnnotations(hostProcess);
                }
            }

            return;
        }

        /**
         * @param notification
         * @param hostProcess
         * @return True if the process (if any) affected by the given
         *         notification is same as passed hosProcess
         */
        public boolean getAffectedProcess(Notification notification,
                Process hostProcess) {
            Process affectedProcess = null;

            if (notification.getNotifier() instanceof EObject) {
                /* Attempt to get the relevant process from the notifier. */
                EObject notifier = (EObject) notification.getNotifier();

                affectedProcess = getProcess(notifier, hostProcess);

                /*
                 * If can't do that then try the new value / old value (delete
                 * of pool menas package is notifier and getProcess() won't
                 * succeed for package
                 */
                if (affectedProcess == null
                        && notification.getNewValue() instanceof EObject) {
                    affectedProcess =
                            Xpdl2ModelUtil.getProcess((EObject) notification
                                    .getNewValue());
                }
                if (affectedProcess == null
                        && notification.getOldValue() instanceof EObject) {
                    affectedProcess =
                            Xpdl2ModelUtil.getProcess((EObject) notification
                                    .getOldValue());
                }

                /*
                 * Sid XPD-4902:
                 * 
                 * If still not found then check specifically for a change in
                 * migration point suppression at package level in which case we
                 * should re-do migration point annotations on this process.
                 */
                if (affectedProcess == null) {
                    Package pkg = Xpdl2ModelUtil.getPackage(notifier);

                    if (pkg != null && pkg.equals(hostProcess.getPackage())) {
                        if (notifier instanceof ValidationControl) {
                            Object value = notification.getOldValue();
                            if (value == null) {
                                value = notification.getNewValue();
                            }

                            if (value instanceof ValidationIssueOverride) {
                                if (MigrationPointUtil.BX_MIGRATION_POINT_DECORATION_SUPPRESSOR_ID
                                        .equals(((ValidationIssueOverride) value)
                                                .getValidationIssueId())) {
                                    affectedProcess = hostProcess;
                                }
                            }

                        } else if (notifier instanceof Package
                                && Notification.REMOVE == notification
                                        .getEventType()) {
                            if (notification.getOldValue() instanceof ValidationControl) {
                                affectedProcess = hostProcess;
                            }
                        }
                    }
                }
            }

            if (hostProcess.equals(affectedProcess)) {
                return true;
            }

            return false;
        }

        /**
         * @param hostProcess
         * @param notifier
         * @return Owner process of given object if possible
         */
        public Process getProcess(EObject object, Process hostProcess) {
            if (object instanceof EObject) {
                Process process = Xpdl2ModelUtil.getProcess(object);

                if (process != null) {
                    return process;
                }

                /*
                 * Handle exceptions such as pool removed from package (have to
                 * look at the process id.
                 */
                if (object instanceof Pool) {
                    if (((Pool) object).getProcessId()
                            .equals(hostProcess.getId())) {
                        return hostProcess;
                    }
                }

            }
            return null;
        }

        @Override
        public Command transactionAboutToCommit(ResourceSetChangeEvent event)
                throws RollbackException {
            return null;
        }

        @Override
        public boolean isPrecommitOnly() {
            return false;
        }

        @Override
        public boolean isPostcommitOnly() {
            return true;
        }

        @Override
        public boolean isAggregatePrecommitListener() {
            return false;
        }

        @Override
        public NotificationFilter getFilter() {
            return null;
        }
    }

    /**
     * Simple wrapper for AnnotationListener (which actually is an annotation
     * creator but historically mis-named) and the state of the annotation
     * 
     * 
     * @author aallway
     * @since 18 Jun 2012
     */
    private class MigrationPointAnnotationAndStatus {
        private boolean annotationCreated = false;

        private AnnotationListener annotationCreator;

        private AbstractMigrationPointAnnotationFigure annotationFigure = null;

        /**
         * @param annotationCreator
         */
        public MigrationPointAnnotationAndStatus(
                AnnotationListener annotationCreator) {
            super();
            this.annotationCreator = annotationCreator;
        }

        /**
         * Create the annotation figure
         */
        public void createAnnotation() {
            if (!annotationCreated) {
                annotationCreated = true;

                /**
                 * Ok, this is a bit gross but it's forced upon us by the
                 * {@link AnnotationFactoryEx} lifecycle. Basically, on
                 * {@link #registerListener(AnnotationListener)} we are given an
                 * {@link AnnotationListener} which we use to get the annotation
                 * create when we want to (we call
                 * {@link AnnotationListener#createAnnotations()}).
                 * <p>
                 * This method does a "call back" into our
                 * {@link #createFigureEx(AnnotationListener, EditPart)} method
                 * but does not return the figure returned by
                 * {@link #createFigureEx(AnnotationListener, EditPart)}.
                 * <p>
                 * So we simply set this class field around a call to
                 * {@link AnnotationListener#createAnnotations()} so that
                 * {@link #createFigureEx(AnnotationListener, EditPart)} can
                 * tell it what the associated figure created was.
                 */
                try {
                    annotationBeingCreated = this;

                    annotationCreator.createAnnotations();

                } finally {
                    annotationBeingCreated = null;

                    if (annotationFigure == null) {
                        BundleActivator
                                .getDefault()
                                .getLogger()
                                .error(this.getClass().getSimpleName()
                                        + ": The annotation figure should have been assigned during callb-back to createFigureEx()"); //$NON-NLS-1$
                    }
                }
            }
        }

        /**
         * Remove the annotation figure.
         */
        public void removeAnnotation() {
            if (annotationCreated) {
                annotationCreated = false;
                annotationCreator.removeAnnotations();
                annotationFigure = null;
            }
        }

        /**
         * @param annotationFigure
         *            the annotationFigure to set
         */
        public void setAnnotationFigure(
                AbstractMigrationPointAnnotationFigure annotationFigure) {
            this.annotationFigure = annotationFigure;
        }

        /**
         * @return the annotationFigure
         */
        public AbstractMigrationPointAnnotationFigure getAnnotationFigure() {
            return annotationFigure;
        }

        /**
         * @return true if the annotation figure has been created
         */
        public boolean isAnnotationCreated() {
            return annotationCreated;
        }
    }

    /**
     * Listeners for changes to the migration annotation icon visibility
     * preference setting and resets all of the visibility of the existing
     * annotations.
     * 
     * @author aallway
     * @since 16 Jul 2012
     */
    private class MigrationAnnotationVisibilityChangeListener implements
            IPropertyChangeListener {

        /**
         * @see org.eclipse.jface.util.IPropertyChangeListener#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
         * 
         * @param event
         */
        @Override
        public void propertyChange(PropertyChangeEvent event) {
            /*
             * When the user's migration point annotation visibility preference
             * changes, then tell all our figures to reset.
             */
            if (AbstractMigrationPointAnnotationFigure.MIGRATION_ANNOTATION_VISIBILITY_PREF
                    .equals(event.getProperty())) {

                for (MigrationPointAnnotationAndStatus annotationCreator : activityToAnnotationCreatorMap
                        .values()) {
                    if (annotationCreator.getAnnotationFigure() != null) {
                        annotationCreator.getAnnotationFigure()
                                .resetVisibilityFromPreference();
                    }
                }
            }
        }
    }

}
