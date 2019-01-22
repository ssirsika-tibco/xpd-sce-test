/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.daa;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;

import com.tibco.amf.sca.model.composite.Composite;
import com.tibco.xpd.daa.internal.IChangeRecorder;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;

/**
 * Represents participant in the composite creation process.
 * <p>
 * <i>Created: 15 Apr 2009</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public abstract class CompositeContributor {

    public static final Logger LOG = XpdResourcesPlugin.getDefault()
            .getLogger();

    protected Context context;

    public CompositeContributor() {
        this.context = null;
    }

    /**
     * Prepares project for contribution. This method is called before
     * WorkspaceModifyOperation surrounding composite creation and DAA
     * generation.
     * 
     * @param project
     *            project to prepare.
     * 
     * @return the status of the operation.
     */
    public IStatus prepareProject(final IProject project,
            IProgressMonitor monitor) {
        if (monitor != null) {
            /* Make sure we "tock" the parent monitors "tick" */
            monitor.beginTask("", 1); //$NON-NLS-1$
            monitor.done();
        }
        return Status.OK_STATUS;
    }

    /**
     * Contribute to the composite. Usually this process is composed of two
     * activities: <li>
     * Prepare artifacts in the staging area. Usually the staging area contains
     * resources referenced by the composite's components. Staging area is later
     * used during creation of archive (DAA).</li> <li>
     * Create and add component(s) to the composite.</li>
     * <p>
     * <b>The process is part of the builder and is usually run in a background
     * thread so it should not create their own threads and/or synchronize to
     * UI.</b>
     * </p>
     * 
     * @param project
     *            the context project.
     * @param stagingArea
     *            the root of the staging. Usually the staging area will contain
     *            resources referenced by the composite's components. Staging
     *            area is also later used to during creation of archive (DAA).
     * @param composite
     *            the context composite to which the contributions will be
     *            added.
     * @param compositeLocation
     *            the URI of composite.
     * @param timeStamp
     *            timeStamp used to replace 'qualifier' during DAA generation
     * @param replaceWithTS
     *            flag to decide whether 'qualifier' should be replaced with
     *            timeStamp during DAA generation
     * @param changeRecorder
     *            helps to records any changes during composite generation which
     *            needs to be be undone
     * @return IStatus
     * @deprecated Use progress monitor enabled
     *             {@link #contributeToComposite(IProject, IContainer, Composite, URI, String, boolean, IChangeRecorder, IProgressMonitor)}
     */
    @Deprecated
    public IStatus contributeToComposite(final IProject project,
            final IContainer stagingArea, final Composite composite,
            final URI compositeLocation, final String timeStamp,
            final boolean replaceWithTS, final IChangeRecorder changeRecorder) {
        return Status.OK_STATUS;
    }

    /**
     * Contribute to the composite. Usually this process is composed of two
     * activities: <li>
     * Prepare artifacts in the staging area. Usually the staging area contains
     * resources referenced by the composite's components. Staging area is later
     * used during creation of archive (DAA).</li> <li>
     * Create and add component(s) to the composite.</li>
     * <p>
     * <b>The process is part of the builder and is usually run in a background
     * thread so it should not create their own threads and/or synchronize to
     * UI.</b>
     * </p>
     * 
     * @param project
     *            the context project.
     * @param stagingArea
     *            the root of the staging. Usually the staging area will contain
     *            resources referenced by the composite's components. Staging
     *            area is also later used to during creation of archive (DAA).
     * @param composite
     *            the context composite to which the contributions will be
     *            added.
     * @param compositeLocation
     *            the URI of composite.
     * @param timeStamp
     *            timeStamp used to replace 'qualifier' during DAA generation
     * @param replaceWithTS
     *            flag to decide whether 'qualifier' should be replaced with
     *            timeStamp during DAA generation
     * @param changeRecorder
     *            helps to records any changes during composite generation which
     *            needs to be be undone
     * @monitor Progress monitor
     * @return IStatus
     */
    public IStatus contributeToComposite(final IProject project,
            final IContainer stagingArea, final Composite composite,
            final URI compositeLocation, final String timeStamp,
            final boolean replaceWithTS, final IChangeRecorder changeRecorder,
            IProgressMonitor monitor) {
        try {
            monitor.beginTask("", 1); //$NON-NLS-1$

            /*
             * Call the exiting default implementation (so that existing
             * contributers aren't broken)
             */
            return contributeToComposite(project,
                    stagingArea,
                    composite,
                    compositeLocation,
                    timeStamp,
                    replaceWithTS,
                    changeRecorder);

        } finally {
            monitor.done();
        }
    }

    /**
     * Gives a chance to the Individual Contributers to reset the Name as per
     * their requirements.
     * 
     * @param composite
     *            the composite
     * @param project
     *            the parent project for the composite
     */
    public void resetCompositeName(Composite composite, IProject project) {

    }

    /**
     * Checks if contributor can provide a valid contribution.
     * 
     * @param project
     *            the context project.
     * @return IStatus determining if the contribution is valid. If status'
     *         severity is greater then IStatus.WARNING then composite will not
     *         be created.
     * @deprecated Use progress monitor enabled
     *             {@link #validate(IProject, IProgressMonitor)}
     */
    @Deprecated
    public IStatus validate(final IProject project) {
        return Status.OK_STATUS;
    }

    /**
     * Checks if contributor can provide a valid contribution.
     * 
     * @param project
     *            the context project.
     * @monitor Progress monitor
     * 
     * @return IStatus determining if the contribution is valid. If status'
     *         severity is greater then IStatus.WARNING then composite will not
     *         be created.
     */
    public IStatus validate(final IProject project, IProgressMonitor monitor) {
        try {
            monitor.beginTask("", 1); //$NON-NLS-1$

            /*
             * Call the exiting default implementation (so that existing
             * contributers aren't broken)
             */
            return validate(project);

        } finally {
            monitor.done();
        }
    }

    /**
     * @return the context.
     */
    public Context getContext() {
        return context;
    }

    /**
     * @param context
     *            the context to set.
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * The contest of the contribution. Usually each contribution has assigned
     * context which corresponds to the AMX target application. Contexts could
     * be defined using 'com.tibco.xpd.daa.com.compositeContributors'
     * extensions.
     */
    public static class Context {

        private final String id;

        private final String name;

        /**
         * Creates context.
         */
        public Context(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((id == null) ? 0 : id.hashCode());
            result = prime * result + ((name == null) ? 0 : name.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Context other = (Context) obj;
            if (id == null) {
                if (other.id != null)
                    return false;
            } else if (!id.equals(other.id))
                return false;
            if (name == null) {
                if (other.name != null)
                    return false;
            } else if (!name.equals(other.name))
                return false;
            return true;
        }
    }
}
