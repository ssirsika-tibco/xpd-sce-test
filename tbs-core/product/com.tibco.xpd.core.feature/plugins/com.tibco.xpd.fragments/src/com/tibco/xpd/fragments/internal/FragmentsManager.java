/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments.internal;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.eclipse.core.commands.operations.IOperationHistoryListener;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.commands.operations.OperationHistoryEvent;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.UIJob;

import com.tibco.xpd.fragments.FragmentsActivator;
import com.tibco.xpd.fragments.FragmentsContributor;
import com.tibco.xpd.fragments.IContainedFragmentElement;
import com.tibco.xpd.fragments.IFragment;
import com.tibco.xpd.fragments.IFragmentCategory;
import com.tibco.xpd.fragments.IFragmentElement;
import com.tibco.xpd.fragments.internal.FragmentsExtensionHelper.FragmentsProvider;
import com.tibco.xpd.fragments.internal.impl.FragmentCategoryImpl;
import com.tibco.xpd.fragments.internal.impl.FragmentElementImpl;
import com.tibco.xpd.fragments.internal.impl.FragmentImpl;
import com.tibco.xpd.fragments.internal.impl.FragmentRootCategory;
import com.tibco.xpd.fragments.internal.operations.FragmentContext;
import com.tibco.xpd.fragments.internal.utils.FragmentsUtil;
import com.tibco.xpd.fragments.internal.view.FragmentsViewContentProvider;
import com.tibco.xpd.fragments.internal.view.FragmentsViewLabelProvider;
import com.tibco.xpd.resources.util.SubProgressMonitorEx;

/**
 * Fragments view manager. This will provide:
 * <ul>
 * <li>Content, label providers/decorators and sorter to the fragments view part
 * tree</li>
 * <li>Manage the fragments project and the root categories</li>
 * <li>Provide API to initialise the fragments project</li>
 * <li>Manage the resource change
 * {@link #addResourceListener(IResourceChangeListener) listeners} - has
 * delegate listener that will forward events to all fragment entities
 * interested in fragment project resource change events.</li>
 * </ul>
 * <p>
 * Use {@link #getInstance()} to get the singleton instance of this class.
 * </p>
 * 
 * @author njpatel
 * 
 */
public final class FragmentsManager {

    private static final String FRAGMENTS_NATURE =
            "com.tibco.xpd.fragments.nature"; //$NON-NLS-1$

    public interface FragmentsInitializationListener {
        void initializationComplete();
    }

    private final Set<FragmentsInitializationListener> initListeners;

    private static final FragmentsManager INSTANCE = new FragmentsManager();

    // Fragments project name
    private static final String PROJECT_NAME = ".bsProject"; //$NON-NLS-1$

    private IProject project;

    private final Set<IResourceChangeListener> resourceChangeListeners;

    private FragmentsResourceListenerDelegate resourceListener;

    private FragmentOperationHistoryListener operationListener;

    private IFragmentCategory[] rootCategories;

    private FragmentsViewPart viewPart;

    private ViewerComparator comparator;

    private Integer stopListeningRequestCount = new Integer(0);

    private boolean isInitializationComplete = false;

    private final IWorkspace workspace = ResourcesPlugin.getWorkspace();

    private final FragmentsExtensionHelper fragmentsExtHelper;

    /**
     * Fragments manager.
     */
    private FragmentsManager() {
        // Private constructor
        resourceChangeListeners =
                new CopyOnWriteArraySet<IResourceChangeListener>();
        initListeners =
                Collections
                        .synchronizedSet(new HashSet<FragmentsInitializationListener>());
        fragmentsExtHelper = FragmentsExtensionHelper.getInstance();
    }

    public void addInitializationListener(
            FragmentsInitializationListener listener) {
        if (listener != null) {
            initListeners.add(listener);

            // If the initialization is complete then send message now
            if (isInitializationComplete) {
                listener.initializationComplete();
            }
        }
    }

    /**
     * Get the fragment's extension helper that will provide access to all
     * extensions of the fragments extension points.
     * 
     * @return extension helper.
     */
    public FragmentsExtensionHelper getExtensionHelper() {
        return fragmentsExtHelper;
    }

    public void removeInitializationListener(
            FragmentsInitializationListener listener) {
        if (listener != null) {
            initListeners.remove(listener);
        }
    }

    /**
     * Get the singleton instance of this class.
     * 
     * @return <code>FragmentsManager</code>.
     */
    public static final FragmentsManager getInstance() {
        return INSTANCE;
    }

    /**
     * Register the fragments view part when it is created.
     * 
     * @param viewPart
     *            <code>FragmentsViewPart</code>
     */
    public void registerFragmentViewPart(FragmentsViewPart viewPart) {
        this.viewPart = viewPart;
    }

    /**
     * Get the fragments view part.
     * 
     * @return view part, or <code>null</code> if one is not created yet.
     */
    public FragmentsViewPart getFragmentsViewPart() {
        return viewPart;
    }

    /**
     * Refresh the given category.
     * 
     * @param category
     *            category to refresh, <code>null</code> to refresh all content.
     */
    public void refreshFragmentsView(final FragmentCategoryImpl category) {
        if (getFragmentsViewPart() != null) {
            new UIJob(Messages.FragmentsManager_refreshJob_title) {

                @Override
                public IStatus runInUIThread(IProgressMonitor monitor) {
                    try {
                        IResource resource =
                                category != null ? category.getResource()
                                        : null;

                        if (resource == null) {
                            resource = project;
                        }

                        if (resource != null) {
                            resource.refreshLocal(IResource.DEPTH_INFINITE,
                                    monitor);
                        }

                        FragmentsViewPart part = getFragmentsViewPart();

                        if (part != null) {
                            part.refresh(category);
                            if (part.getTreeViewer() != null) {
                                if (category != null) {
                                    part.getTreeViewer()
                                            .setSelection(new StructuredSelection(
                                                    category));
                                }
                            }
                        }
                    } catch (CoreException e) {
                        FragmentsActivator.getDefault().getLogger().error(e);
                    }

                    return Status.OK_STATUS;
                }

            }.schedule();
        }
    }

    /**
     * Get the root category of the provider with the given provider id.
     * 
     * @param providerId
     *            provider id
     * @return root category of the provider contribution.
     * @throws CoreException
     */
    public IFragmentCategory getRootCategory(String providerId)
            throws CoreException {
        IFragmentCategory ret = null;

        if (providerId != null) {
            IFragmentCategory[] categories = getRootCategories();

            for (IFragmentCategory cat : categories) {
                FragmentsProvider provider =
                        ((FragmentElementImpl) cat).getProvider();
                if (provider != null && provider.getId().equals(providerId)) {
                    ret = cat;
                    break;
                }
            }
        }

        return ret;
    }

    /**
     * Register a listener that will be updated when a resource in the fragments
     * project changes.
     * 
     * @param listener
     */
    public void addResourceListener(IResourceChangeListener listener) {
        if (listener != null) {
            resourceChangeListeners.add(listener);

            // If the resource listener hasn't already been registered then do
            // so as there are listeners registered now
            startListeningToOperationalHistory();
        }
    }

    /**
     * Start listening to the workspace operational history (if not doing so
     * already).
     */
    private void startListeningToOperationalHistory() {
        if (operationListener == null) {
            operationListener = new FragmentOperationHistoryListener();
            PlatformUI.getWorkbench().getOperationSupport()
                    .getOperationHistory()
                    .addOperationHistoryListener(operationListener);
        }
    }

    /**
     * Stop listening to the workspace operational history (if not stopped
     * already).
     */
    private void stopListeningToOperationalHistory() {
        if (operationListener != null) {
            PlatformUI.getWorkbench().getOperationSupport()
                    .getOperationHistory()
                    .removeOperationHistoryListener(operationListener);
            operationListener = null;
        }
    }

    /**
     * Start listening to workspace resource changes.
     * <p>
     * <b>NOTE: </b>For every {@link #stopListeningToWorkspace()} call there
     * must be a call to this method to start listening to workspace resources,
     * i.e. if there are two calls to stop and one to start then this manager
     * will not start listening to resource changes till another start call is
     * made. This is to avoid listening to resources early in recursive
     * operations.
     * </p>
     */
    private void startListeningToWorkspace() {

        synchronized (stopListeningRequestCount) {
            --stopListeningRequestCount;
            if (stopListeningRequestCount <= 0) {
                stopListeningRequestCount = 0;
                if (resourceListener == null) {
                    resourceListener = new FragmentsResourceListenerDelegate();
                    ResourcesPlugin.getWorkspace()
                            .addResourceChangeListener(resourceListener);
                }
            }
        }
    }

    /**
     * Stop listening to workspace resource changes.
     * <p>
     * <b>NOTE: </b>For every call to this method there must be a call to
     * {@link #startListeningToWorkspace()} to start listening to workspace
     * resources, i.e. if there are two calls to stop and one to start then this
     * manager will not start listening to resource changes till another start
     * call is made. This is to avoid listening to resources early in recursive
     * operations.
     * </p>
     */
    private void stopListeningToWorkspace() {

        synchronized (stopListeningRequestCount) {
            ++stopListeningRequestCount;
            if (stopListeningRequestCount > 0) {
                if (resourceListener != null) {
                    ResourcesPlugin.getWorkspace()
                            .removeResourceChangeListener(resourceListener);
                    resourceListener = null;
                }
            }
        }
    }

    /*
     * Remove the fragments resource change listener.
     * 
     * @param listener
     */
    public void removeResourceListener(IResourceChangeListener listener) {
        if (listener != null) {
            resourceChangeListeners.remove(listener);

            if (resourceChangeListeners.isEmpty()) {
                stopListeningToOperationalHistory();
            }
        }
    }

    /**
     * Get the fragments container (input) for the fragments view.
     * 
     * @return Fragments project.
     * @throws CoreException
     */
    public Object getFragmentsContainer() throws CoreException {
        if (project == null) {
            project =
                    ResourcesPlugin.getWorkspace().getRoot()
                            .getProject(PROJECT_NAME);
        }
        return project;
    }

    /**
     * Get the root fragment categories.
     * 
     * @return array of root categories, empty array if no root categories are
     *         found.
     * @throws CoreException
     */
    public IFragmentCategory[] getRootCategories() {
        return rootCategories != null ? rootCategories
                : new IFragmentCategory[0];
    }

    /**
     * Get the fragments view content provider.
     * 
     * @return content provider.
     */
    public ITreeContentProvider getFragmentsContentProvider() {
        return new FragmentsViewContentProvider();
    }

    /**
     * Get the fragments view label provider.
     * 
     * @return label provider.
     */
    public ILabelProvider getFragmentsLabelProvider() {
        return new FragmentsViewLabelProvider();
    }

    /**
     * Get the fragment view label decorator.
     * 
     * @return label decorator.
     */
    public ILabelDecorator getFragmentsLabelDecorator() {
        return new FragmentsViewLabelProvider();
    }

    /**
     * Create a fragment category.
     * 
     * @param parent
     *            parent category
     * @param id
     *            unique id for the category resource name
     * @param key
     *            user provided key
     * @param name
     *            name of category
     * @param description
     *            description of category
     * @param isSystem
     *            <code>true</code> if system category, <code>false</code>
     *            otherwise
     * @param monitor
     *            progress monitor, <code>null</code> if no progress required
     * @return fragment category or <code>null</code> if failed to create
     * @throws CoreException
     */
    public IFragmentCategory createCategory(IFragmentCategory parent,
            String id, String key, String name, String description,
            boolean isSystem, IProgressMonitor monitor) throws CoreException {
        FragmentCategoryImpl cat = null;
        Assert.isNotNull(parent,
                Messages.FragmentsManager_parentCategory_err_shortdesc);
        Assert.isNotNull(name,
                Messages.FragmentsManager_newCategoryName_err_shortdesc);
        Assert.isNotNull(id, "New category id"); //$NON-NLS-1$

        if (monitor != null) {
            monitor.beginTask(Messages.FragmentsManager_createCategory_monitor_shortdesc,
                    1);
        }

        try {
            IResource resource = ((FragmentCategoryImpl) parent).getResource();
            if (resource instanceof IFolder && resource.exists()) {
                final IFolder folder = ((IFolder) resource).getFolder(id);

                // If folder doesn't exist then create it
                if (!folder.exists()) {
                    runWorkspaceOperation(new IWorkspaceRunnable() {

                        @Override
                        public void run(IProgressMonitor monitor)
                                throws CoreException {
                            folder.create(true, true, null);
                        }

                    }, workspace.getRuleFactory().createRule(folder), monitor);
                }

                // Create the child category
                cat =
                        ((FragmentCategoryImpl) parent)
                                .createChildCategory(folder);
                if (cat != null) {
                    cat.setDetails(key, name, description);
                    cat.setIsSystem(isSystem);
                }
            } else {
                throw new CoreException(
                        new Status(
                                IStatus.ERROR,
                                FragmentsActivator.PLUGIN_ID,
                                String.format(Messages.FragmentsManager_resourceNotFound_error_message,
                                        parent.getNameLabel()), null));
            }
        } finally {
            if (monitor != null) {
                monitor.worked(1);
                monitor.done();
            }
        }

        return cat;
    }

    /**
     * Delete a fragment element.
     * 
     * @param element
     *            fragment element to delete
     * @param monitor
     *            progress monitor or <code>null</code> if no progress required
     * @throws CoreException
     */
    public void deleteFragmentElement(IFragmentElement element,
            IProgressMonitor monitor) throws CoreException {
        Assert.isNotNull(element,
                Messages.FragmentsManager_fragmentElemement_toDelete_err_shortdesc);

        if (element instanceof FragmentRootCategory) {
            // Cannot delete root category
            throw new IllegalArgumentException(
                    "Root category cannot be deleted."); //$NON-NLS-1$
        }

        if (monitor != null) {
            monitor.beginTask(Messages.FragmentsManager_deleteFragmentElem_monitor_shortdesc,
                    1);
        }

        try {
            if (element instanceof IContainedFragmentElement) {
                IFragmentCategory parent =
                        ((IContainedFragmentElement) element).getParent();

                // Remove the category from its parent
                if (parent instanceof FragmentCategoryImpl) {
                    ((FragmentCategoryImpl) parent).removeChild(element);
                }
            }

            // Delete all resources associated with the element
            final Collection<IResource> resources =
                    ((FragmentElementImpl) element).getAllResources();

            if (resources != null && !resources.isEmpty()) {
                runWorkspaceOperation(new IWorkspaceRunnable() {

                    @Override
                    public void run(IProgressMonitor monitor)
                            throws CoreException {
                        for (IResource res : resources) {
                            res.delete(true, null);
                        }
                    }
                },
                        workspace.getRuleFactory().deleteRule(resources
                                .iterator().next()),
                        monitor);
            }
            element.dispose();
        } finally {
            if (monitor != null) {
                monitor.worked(1);
                monitor.done();
            }
        }
    }

    /**
     * Create a fragment.
     * 
     * @param parent
     *            parent category
     * @param id
     *            unique id for the fragment resource name
     * @param key
     *            user provided key
     * @param name
     *            name of fragment
     * @param description
     *            description of fragment
     * @param data
     *            fragment data
     * @param imgData
     *            fragment image data or <code>null</code> if no image available
     * @param monitor
     *            progress monitor or <code>null</code> if no progress required
     * @return fragment or <code>null</code> if failed to create
     * @throws CoreException
     */
    public IFragment createFragment(IFragmentCategory parent, String id,
            String key, String name, String description, String data,
            ImageData imgData, IProgressMonitor monitor) throws CoreException {
        FragmentImpl frag = null;
        Assert.isNotNull(parent,
                Messages.FragmentsManager_parentCategory_err_shortdesc);
        Assert.isNotNull(name,
                Messages.FragmentsManager_newFragmentName_err_shortdesc);
        Assert.isNotNull(data,
                Messages.FragmentsManager_newFragmentData_err_shortdesc);
        Assert.isNotNull(id, "New fragment id"); //$NON-NLS-1$
        IResource resource = ((FragmentCategoryImpl) parent).getResource();

        if (monitor != null) {
            monitor.beginTask(Messages.FragmentsManager_createFragment_monitor_shortdesc,
                    1);
        }
        try {
            if (resource instanceof IFolder && resource.exists()) {
                IFolder folder = (IFolder) resource;
                IFile fragmentFile =
                        FragmentImpl.createFragmentFile(folder, id, data);
                if (imgData != null) {
                    // Create image file
                    FragmentImpl.createImageFile(folder, id, imgData);
                }

                if (fragmentFile != null && fragmentFile.exists()) {
                    frag =
                            ((FragmentCategoryImpl) parent)
                                    .createChildFragment(fragmentFile);

                    if (frag != null) {
                        frag.setDetails(key, name, description);
                    }
                }

            } else {
                throw new CoreException(
                        new Status(
                                IStatus.ERROR,
                                FragmentsActivator.PLUGIN_ID,
                                String.format(Messages.FragmentsManager_resourceNotFound_error_message,
                                        parent.getNameLabel()), null));
            }
        } finally {
            if (monitor != null) {
                monitor.worked(1);
                monitor.done();
            }
        }

        return frag;
    }

    /**
     * Get the fragment view comparator.
     * 
     * @return viewer comparator.
     */
    public ViewerComparator getFragmentsViewerComparator() {

        if (comparator == null) {
            comparator = new ViewerComparator() {
                @Override
                public int category(Object element) {
                    if (element instanceof IFragmentCategory) {
                        if (((IFragmentCategory) element).isSystem()) {
                            return 0;
                        } else {
                            return 1;
                        }
                    } else if (element instanceof IFragment) {
                        return 2;
                    }
                    return 3;
                }
            };
        }
        return comparator;
    }

    /**
     * Initialize the fragments project. If the project doesn't exist then it
     * will be created and all providers will be initialized. Otherwise, all
     * providers will be checked to see if they are current and update
     * accordingly. Note that only bound providers are included.
     * 
     */
    public void initialize() {
        isInitializationComplete = false;
        Job initJob =
                new Job(
                        Messages.FragmentsManager_InitFragment_monitor_shortdesc) {

                    @Override
                    public boolean belongsTo(Object family) {
                        /*
                         * XPD-2673: Set the fragments initialise to its own
                         * family, then thenings that use it (like new project
                         * wizard process package template page) can wait for
                         * this job family to be finished before they continue.
                         */
                        return family == FragmentsActivator.FRAGMENTS_INITIALISE_JOB_FAMILY;
                    }

                    @Override
                    protected IStatus run(IProgressMonitor monitor) {
                        IStatus status = Status.OK_STATUS;

                        monitor.beginTask(Messages.FragmentsManager_initializingFragments_monitor_shortdesc, 10);

                        project = workspace.getRoot().getProject(PROJECT_NAME);

                        /*
                         * Ignore all workspace changes while the project is
                         * being initialized
                         */
                        stopListeningToWorkspace();

                        try {
                            if (!project.isAccessible()) {
                                runWorkspaceOperation(new IWorkspaceRunnable() {

                                    @Override
                                    public void run(IProgressMonitor monitor)
                                            throws CoreException {
                                        if (!project.exists()) {
                                            // Create the project
                                            project.create(monitor);
                                            project.open(monitor);
                                            // Add fragments nature
                                            IProjectDescription description =
                                                    project.getDescription();
                                            description.setNatureIds(new String[] { FRAGMENTS_NATURE });
                                            project.setDescription(description,
                                                    IResource.AVOID_NATURE_CONFIG,
                                                    monitor);

                                        } else {
                                            /*
                                             * XPD-3913 missing fragments
                                             * /templates open the .bsProject ,
                                             * if it was closed due to any
                                             * workspace restore in prev
                                             * launches
                                             */
                                            if (!project.isAccessible()) {
                                                project.open(monitor);
                                            }
                                        }
                                    }
                                },
                                        workspace.getRuleFactory()
                                                .createRule(project),
                                        null);
                            } else {
                                project.refreshLocal(IResource.DEPTH_INFINITE,
                                        monitor);
                            }

                            monitor.worked(1);

                            Set<IFragmentCategory> cats =
                                    initializeFragmentContributors(project,
                                            new SubProgressMonitorEx(monitor, 9));

                            if (cats != null) {
                                rootCategories =
                                        cats.toArray(new IFragmentCategory[cats
                                                .size()]);
                            }
                        } catch (CoreException e) {
                            status = e.getStatus();
                        } finally {
                            // Start listening to resource changes if not
                            // already doing
                            // so
                            startListeningToWorkspace();
                            monitor.done();
                        }

                        if (rootCategories == null
                                || rootCategories.length == 0) {
                            FragmentsActivator
                                    .getDefault()
                                    .getLogger()
                                    .error("FragmentsManager.initialize(): No root categories found!!!"); //$NON-NLS-1$
                        }

                        return status;
                    }
                };

        initJob.addJobChangeListener(new JobChangeAdapter() {
            @Override
            public void done(IJobChangeEvent event) {
                isInitializationComplete = true;

                synchronized (initListeners) {
                    for (FragmentsInitializationListener listener : initListeners) {
                        listener.initializationComplete();
                    }
                }
            }
        });

        initJob.setRule(ResourcesPlugin.getWorkspace().getRoot());

        initJob.schedule();

    }

    /**
     * Run the given workspace runnable. This will ensure that this manager will
     * stop listening to resource changes while this action is being executed.
     * 
     * @param runnable
     *            workspace runnable
     * @param rule
     *            scheduling rule
     * @param monitor
     *            progress monitor or <code>null</code> if no progress required
     * @throws CoreException
     */
    public void runWorkspaceOperation(IWorkspaceRunnable runnable,
            ISchedulingRule rule, IProgressMonitor monitor)
            throws CoreException {
        if (runnable != null) {
            stopListeningToWorkspace();
            try {
                ResourcesPlugin.getWorkspace().run(runnable,
                        rule,
                        IWorkspace.AVOID_UPDATE,
                        monitor);
            } finally {
                startListeningToWorkspace();
            }
        }
    }

    /**
     * Run the given runnable context. This will make sure that while the
     * runnable is running this manager will stop listening to any resource
     * changes.
     * 
     * @param context
     *            runnable context
     * @param fork
     *            <code>true</code> if the runnable should be run in a separate
     *            thread
     * @param cancelable
     *            true to enable the cancelation, and false to make the
     *            operation uncancellable
     * @param runnable
     *            the runnable to run
     * @throws InvocationTargetException
     * @throws InterruptedException
     * 
     * @see IRunnableContext#run(boolean, boolean, IRunnableWithProgress)
     */
    public void runRunnableContext(IRunnableContext context, boolean fork,
            boolean cancelable, IRunnableWithProgress runnable)
            throws InvocationTargetException, InterruptedException {
        if (context != null) {
            stopListeningToWorkspace();
            try {
                context.run(fork, cancelable, runnable);
            } finally {
                startListeningToWorkspace();
            }
        }
    }

    /**
     * Resource change listener delegate that will delegate resource change
     * events that affect the fragments project to all registered listeners.
     * 
     * @author njpatel
     * 
     */
    private class FragmentsResourceListenerDelegate implements
            IResourceChangeListener {

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.core.resources.IResourceChangeListener#resourceChanged
         * (org.eclipse.core.resources.IResourceChangeEvent)
         */
        @Override
        public void resourceChanged(IResourceChangeEvent event) {
            if (project != null && project.isAccessible()) {
                if (event.getDelta() != null
                        && event.getDelta().getKind() == IResourceDelta.CHANGED) {
                    IResourceDelta member =
                            event.getDelta().findMember(project.getFullPath());

                    // Ignore if the affected resource is not from the fragments
                    // project or are fragments.properties files
                    if (member != null
                            && !isAllPropertiesFiles(event.getDelta())) {
                        /*
                         * Flush undo/redo history as this resource change has
                         * come from outside the fragments framework
                         */
                        FragmentsUtil.flushUndoRedoHistory();

                        synchronized (resourceChangeListeners) {
                            for (IResourceChangeListener listener : resourceChangeListeners) {
                                listener.resourceChanged(event);
                            }
                        }
                    }
                }
            } else {
                // Project has been removed or closed
                FragmentsUtil.flushUndoRedoHistory();
                // Remove all root categories
                rootCategories = null;

                // Clear all listeners
                resourceChangeListeners.clear();

                // Dispose all listeners
                stopListeningToOperationalHistory();
                stopListeningToWorkspace();

                FragmentsViewPart part = getFragmentsViewPart();

                if (part != null && part.getTreeViewer() != null) {
                    part.getTreeViewer().refresh();
                }
            }
        }

        /**
         * Check if the affected resources in the deltas are just
         * fragment.properties files.
         * 
         * @param delta
         *            change resource delta
         * @return <code>true</code> if all affected resources are
         *         fragment.properties files, <code>false</code> otherwise.
         */
        private boolean isAllPropertiesFiles(IResourceDelta delta) {
            if (delta != null) {
                List<IResource> resources = getAllAffectedResources(delta);

                for (IResource resource : resources) {
                    if (!(resource instanceof IFile && resource.getName()
                            .equals(FragmentConsts.FRAGMENTS_PROPERTIES))) {
                        return false;
                    }
                }

                return true;
            }

            return false;
        }

        /**
         * Get all the affected resources from the given resource delta.
         * 
         * @param delta
         *            resource delta
         * @return list of affected resources, empty list if none.
         */
        private List<IResource> getAllAffectedResources(IResourceDelta delta) {
            List<IResource> resources = new ArrayList<IResource>();

            if (delta != null) {
                IResourceDelta[] children = delta.getAffectedChildren();

                if (children.length > 0) {
                    for (IResourceDelta child : children) {
                        resources.addAll(getAllAffectedResources(child));
                    }
                } else {
                    resources.add(delta.getResource());
                }
            }

            return resources;
        }

    }

    /**
     * Fragment operation history listener. This will ensure that this fragments
     * manager stops listening to resource changes in the workspace when a
     * fragment operation is running.
     * 
     * @author njpatel
     * 
     */
    private class FragmentOperationHistoryListener implements
            IOperationHistoryListener {

        /*
         * (non-Javadoc)
         * 
         * @seeorg.eclipse.core.commands.operations.IOperationHistoryListener#
         * historyNotification
         * (org.eclipse.core.commands.operations.OperationHistoryEvent)
         */
        @Override
        public void historyNotification(OperationHistoryEvent event) {
            IUndoableOperation operation = event.getOperation();

            if (operation != null) {
                IUndoContext[] contexts = operation.getContexts();

                if (contexts != null && contexts.length > 0
                        && contexts[0] instanceof FragmentContext) {

                    // Stop listening to resource events when a fragment
                    // operation is being executed
                    switch (event.getEventType()) {
                    case OperationHistoryEvent.ABOUT_TO_EXECUTE:
                    case OperationHistoryEvent.ABOUT_TO_REDO:
                    case OperationHistoryEvent.ABOUT_TO_UNDO:
                        stopListeningToWorkspace();
                        break;

                    case OperationHistoryEvent.DONE:
                        startListeningToWorkspace();
                        break;

                    }
                }
            }
        }
    }

    /**
     * 
     */
    private Set<IFragmentCategory> initializeFragmentContributors(
            IProject project, IProgressMonitor monitor) {
        Set<IFragmentCategory> rootCats = new HashSet<IFragmentCategory>();
        if (project != null && project.isAccessible()) {
            // Get all bound providers to update
            FragmentsProvider[] providers =
                    fragmentsExtHelper.getBoundProviders(null);

            if (providers != null) {
                monitor.beginTask(Messages.FragmentsManager_updateFragmentContribution_monitor_shortdesc,
                        providers.length);
                for (FragmentsProvider provider : providers) {
                    final IFolder folder = project.getFolder(provider.getId());
                    FragmentRootCategory cat = null;
                    try {
                        FragmentsContributor providerClass =
                                provider.getProviderClass();
                        boolean newCategory = false;

                        if (!folder.exists()) {
                            newCategory = true;
                            // This provider's contribution needs to be
                            // initialized as it hasn't been created yet
                            runWorkspaceOperation(new IWorkspaceRunnable() {

                                @Override
                                public void run(IProgressMonitor monitor)
                                        throws CoreException {
                                    folder.create(true, true, null);
                                }

                            },
                                    workspace.getRuleFactory()
                                            .createRule(folder),
                                    null);
                        }

                        if (folder.exists()) {
                            cat = new FragmentRootCategory(folder, provider);
                            if (!newCategory) {
                                // Check if the fragment contribution has
                                // changed and if so update
                                if ((cat.getFragmentVersion() == null && providerClass
                                        .getFragmentVersion() != null)
                                        || (cat.getFragmentVersion() != null && !cat
                                                .getFragmentVersion()
                                                .equals(providerClass.getFragmentVersion()))) {
                                    providerClass.updateContent(cat,
                                            cat.getFragmentVersion(),
                                            new SubProgressMonitor(monitor, 1));

                                    updatedFragmentVersion(provider, cat);
                                } else {
                                    monitor.worked(1);
                                }
                            } else {
                                // New category so initialize contribution
                                providerClass
                                        .initialize(new SubProgressMonitor(
                                                monitor, 1));
                                updatedFragmentVersion(provider, cat);
                            }
                        }
                    } catch (CoreException e) {
                        // Log the error and continue with next provider
                        FragmentsActivator.getDefault().getLogger().error(e);
                    }

                    if (cat != null) {
                        rootCats.add(cat);
                    }
                }
                monitor.done();
            }

        } else {
            FragmentsActivator
                    .getDefault()
                    .getLogger()
                    .error("FragmentsManager.initializeFragmentContributors() - Project: " //$NON-NLS-1$
                            + project + " is not accessible."); //$NON-NLS-1$
        }
        return rootCats;
    }

    /**
     * Update the fragment contribution version stored in the root category.
     * 
     * @param provider
     *            fragment provider
     * @param cat
     *            root category
     * @throws CoreException
     */
    private void updatedFragmentVersion(FragmentsProvider provider,
            FragmentRootCategory cat) throws CoreException {

        if (provider != null && cat != null) {
            // Update the fragment version
            String version = provider.getProviderClass().getFragmentVersion();

            if (version != null) {
                cat.setFragmentVersion(version);
            } else {
                throw new NullPointerException(
                        String.format(Messages.FragmentsManager_providerNullVersion_err_shortdesc,
                                provider.getId()));
            }
        }
    }
}
