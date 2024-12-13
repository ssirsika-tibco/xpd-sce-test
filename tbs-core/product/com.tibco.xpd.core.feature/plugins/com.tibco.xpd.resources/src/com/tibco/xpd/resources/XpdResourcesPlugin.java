/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.resources;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.resources.indexer.IndexerService;
import com.tibco.xpd.resources.internal.XpdResourcesDebugOptions;
import com.tibco.xpd.resources.internal.indexer.IndexerServiceImpl;
import com.tibco.xpd.resources.internal.indexer.ResourceDependencyIndexer;
import com.tibco.xpd.resources.internal.wc.WorkingCopyCreationListenersManager;
import com.tibco.xpd.resources.internal.wc.WorkingCopyFactoriesManager;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;
import com.tibco.xpd.resources.projectconfig.DocumentRoot;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.ProjectConfigFactory;
import com.tibco.xpd.resources.projectconfig.ProjectDetails;
import com.tibco.xpd.resources.projectconfig.util.ProjectConfigResourceFactoryImpl;
import com.tibco.xpd.resources.util.XpdConsts;

/**
 * The main plugin class to access Studio resources.
 */
public class XpdResourcesPlugin extends AbstractUIPlugin {

    public static final Object FAMILY_SYNCH_BUILD = new Object();

    private static final String RCP_PRODUCT_ID = "com.tibco.xpd.rcp.product"; //$NON-NLS-1$

    private IndexerService indexerService = null;

    /**
     * The id of the XPD plugin (value <code>"com.tibco.xpd.resources"</code>).
     */
    public static final String ID_PLUGIN = "com.tibco.xpd.resources"; //$NON-NLS-1$

    /** The shared instance. */
    private static XpdResourcesPlugin plugin;

    /** logger instance. */
    private final Logger logger = LoggerFactory.createLogger(ID_PLUGIN);// new

    /**
     * Preference kye to store processes root folder for given project.
     */
    public static final String PROCESS_ROOT_PATH = "processRootPath"; //$NON-NLS-1$

    /**
     * Project config file.
     */
    public static final String PROJECTCONFIGFILE = ".config"; //$NON-NLS-1$

    /**
     * Lazy-loaded working copy factory manager.
     */
    private WorkingCopyFactoriesManager wcManager;

    public BundleContext context;

    /** Storage for overridden standard display. */
    private static Display standard = null;

    /** Set to true during import of projects */
    private Boolean isImportInProgress = Boolean.FALSE;

    /** Set to the project during project migration */
    private IProject projectBeingMigrated;

    /**
     * syncronization lock used by the getter and setter of the
     * "isProjectImportInProgress" lock
     */
    private static final Object IMPORTINGPROGRESS_LOCK = new Object();

    /**
     * syncronization lock used by the getter and setter of the
     * "isProjectMigrationInProgress" lock
     */
    private static final Object MIGRATIONPROGRESS_LOCK = new Object();

    /**
     * The constructor.
     */
    public XpdResourcesPlugin() {
        super();
        plugin = this;
    }

    /**
     * Get the shared transactional editing domain.
     * 
     * @return <code>TransactionalEditingDomain</code>
     * 
     * @since 3.0
     */
    public TransactionalEditingDomain getEditingDomain() {
        return TransactionalEditingDomain.Registry.INSTANCE
                .getEditingDomain(XpdConsts.EDITING_DOMAIN_ID);
    }

    /**
     * This method is called upon plug-in activation
     * 
     * @param context
     * @throws Exception
     */
    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        this.context = context;
        this.wcManager = new WorkingCopyFactoriesManager();
        IndexerServiceImpl indexerService =
                (IndexerServiceImpl) getIndexerService();
        indexerService.start();
        /*
         * See the init() method for more details...
         */
        ResourceDependencyIndexer.init(indexerService);
    }

    /**
     * This method is called when the plug-in is stopped.
     * 
     * @param context
     * @throws Exception
     */
    @Override
    public void stop(BundleContext context) throws Exception {
        if (wcManager != null) {
            wcManager.dispose();
        }
        ((IndexerServiceImpl) getIndexerService()).stop();
        super.stop(context);

    }

    /**
     * Returns the shared instance.
     * 
     * @return shared instance
     */
    public static XpdResourcesPlugin getDefault() {
        return plugin;
    }

    /**
     * Returns factory for geting WorkingCopies for resources for specified
     * project.
     * 
     * @param project
     *            project
     * @return working copy factory for specified project, may return null if
     *         the project do not have xpd nature
     */
    public XpdProjectResourceFactory getXpdProjectResourceFactory(
            IProject project) {
        return getWorkingCopyFactoriesManager()
                .getXpdProjectResourceFactory(project);
    }

    /**
     * Return WorkingCopy for given resource.
     * <p>
     * Shortcut for:<br>
     * <code>
     *  XpdProjectResourceFactory resourceFactory = getXpdProjectResourceFactory(resource
     *                 .getProject());
     *  WorkingCopy wc = resourceFactory.getWorkingCopy(resource);
     * </code>
     * 
     * @param resource
     *            resource to check
     * @return working copy for given resource, may be null if there is no
     *         working copy registered for this kind of resource. The working
     *         copy will be returned even is the file is invalid.
     */
    public WorkingCopy getWorkingCopy(IResource resource) {
        XpdProjectResourceFactory resourceFactory =
                getXpdProjectResourceFactory(resource.getProject());
        if (resourceFactory == null) {
            return null;
        }
        return resourceFactory.getWorkingCopy(resource);
    }

    /**
     * @param cls
     *            The class to get providers for.
     * @return A set of dependency providers.
     */
    public Set<IWorkingCopyDependencyProvider> getDependencyProviders(
            Class<? extends WorkingCopy> cls) {
        return getWorkingCopyFactoriesManager().getDependencyProviders(cls);
    }

    /**
     * Revalidate external references in the given <code>Resource</code>.
     * 
     * @param res
     *            <code>Resource</code> to revalidate.
     */
    public void revalidateReferences(Resource res) {
        WorkingCopyFactoriesManager manager = getWorkingCopyFactoriesManager();
        if (manager != null) {
            manager.revalidateProjectReferences(res);
        }
    }

    /**
     * Check whether project import is in progress.
     * <p>
     * <strong>FOR INTERNAL USE ONLY </strong>
     * </p>
     * 
     * @return <code>true</code> to indicate import is in progress.
     */
    public boolean isProjectsImportInProgress() {
        synchronized (IMPORTINGPROGRESS_LOCK) {
            return isImportInProgress;
        }
    }

    /**
     * Check whether project migration is in progress.
     * <p>
     * <strong>FOR INTERNAL USE ONLY </strong>
     * </p>
     * 
     * @param project
     *            check if this project is being migrated. Passing
     *            <code>null</code> will check for any project being migrated.
     * 
     * @return <code>true</code> to indicate project migration is in progress.
     */
    public boolean isProjectMigrationInProgress(IProject project) {
        synchronized (MIGRATIONPROGRESS_LOCK) {
            return projectBeingMigrated != null
                    && (project == null || projectBeingMigrated.equals(project));
        }
    }

    /**
     * Set whether project import is in progress.
     * <p>
     * <strong>FOR INTERNAL USE ONLY </strong>
     * </p>
     * 
     * @param isInProgress
     *            <code>true</code> to indicate import is in progress.
     */
    public void setIsProjectsImportInProgress(boolean isInProgress) {
        synchronized (IMPORTINGPROGRESS_LOCK) {
            isImportInProgress = isInProgress;
        }
    }

    /**
     * Set whether project migration is in progress.
     * <p>
     * <strong>FOR INTERNAL USE ONLY </strong>
     * </p>
     * 
     * @param project
     *            the project being migrated, <code>null</code> if migration is
     *            complete.
     */
    public void setProjectMigrationInProgress(IProject project) {
        synchronized (MIGRATIONPROGRESS_LOCK) {
            projectBeingMigrated = project;
        }
    }

    /**
     * Get the working copy factory manager (lazy loaded).
     * 
     * @return
     */
    private WorkingCopyFactoriesManager getWorkingCopyFactoriesManager() {
        return wcManager;
    }

    /**
     * Returns an image descriptor for the image file at the given plug-in
     * relative path.
     * 
     * @param path
     *            the path
     * @return the image descriptor
     */
    public static ImageDescriptor getImageDescriptor(String path) {
        return AbstractUIPlugin.imageDescriptorFromPlugin(ID_PLUGIN, path);
    }

    /**
     * This method is provided to allow the standard Display to be overridden.
     * This should only be done from external programs that make use of this
     * plugin. It should NOT be called from within Studio.
     * 
     * @param display
     *            The display to use as standard.
     */
    public static void overrideStandardDisplay(Display display) {
        synchronized (XpdResourcesPlugin.class) {
            standard = display;
        }
    }

    /**
     * Returns the standard display to be used. The method first checks, if the
     * thread calling this method has an associated display. If so, this display
     * is returned. Otherwise the method returns the default display.
     * 
     * @return current or default display
     */
    public static Display getStandardDisplay() {
        Display display = null;
        synchronized (XpdResourcesPlugin.class) {
            display = standard;
        }
        if (display == null) {
            display = Display.getCurrent();
        }
        if (display == null) {
            display = Display.getDefault();
        }
        return display;
    }

    /**
     * Access to eclipse log.
     * 
     * @return the logger
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * @param msg
     * @return
     */
    private static String getTraceINDEXmsg(String indexerId, String msg) {
        return String
                .format("[%d]Indexer{%s}: %s", Thread.currentThread().getId(), indexerId, msg); //$NON-NLS-1$
    }

    /**
     * @param msg
     * @return
     */
    private static String getTraceINDEXmsg(String msg) {
        return String
                .format("[%d]Indexer: %s", Thread.currentThread().getId(), msg); //$NON-NLS-1$
    }

    /**
     * Add the message to the indexer trace of specific ID.
     * 
     * @param msg
     * @param args
     */
    public static void traceINDEX(String indexerId, String msg) {

        // log specific indexer tracing if indexer's ID enabled
        boolean indexerIdSpecified =
                (indexerId != null) && !indexerId.trim().isEmpty();
        boolean indexerToTraceable =
                XpdResourcesDebugOptions.INDEXER_IDS.contains(indexerId);

        if (indexerIdSpecified && indexerToTraceable) {
            String indexerCategory =
                    XpdResourcesDebugOptions.INDEXER + '/' + indexerId;
            Logger logger = getDefault().getLogger();
            logger.trace(indexerCategory, getTraceINDEXmsg(indexerId, msg));
        }
    }

    /**
     * Add the message to the indexer trace of specific ID.
     * 
     * @param msg
     */
    public static void traceINDEX(String msg) {
        getDefault().getLogger().trace(XpdResourcesDebugOptions.INDEXER,
                getTraceINDEXmsg(msg));
    }

    /**
     * Checks whether there is any tracing based upon index is configured
     * 
     * @return <code>true</code> if any of the indexer options are turned on
     */
    public static boolean isTraceINDEX() {

        if (Platform.inDebugMode()) {
            Logger logger = getDefault().getLogger();

            if (logger.isCategoryEnabled(XpdResourcesDebugOptions.INDEXER)) {
                return true;
            } else {
                for (String indexerId : XpdResourcesDebugOptions.INDEXER_IDS) {
                    String indexerCategory =
                            XpdResourcesDebugOptions.INDEXER + '/' + indexerId;
                    if (logger.isCategoryEnabled(indexerCategory)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Get the <code>ProjectConfig</code> model for the given <i>project</i>.
     * 
     * @param project
     * @return <ul>
     *         <li><code>ProjectConfig</code> - If config is available for the
     *         project.</li>
     *         <li><b>null</b> - If the project does not exist, is closed or is
     *         not enabled for the BPM nature.</li>
     *         </ul>
     */
    public ProjectConfig getProjectConfig(IProject project) {
        ProjectConfig config = null;

        if (project != null) {
            try {
                // JA: There is a need to check if project exists, because
                // nature cannot be obtained for a project if the project does
                // not exist (if for example method is invoked on project delete
                // resource change).
				/*
				 * Sid ACE-8885 refined to use 'isAccessible()' as I found that in some circumstances the 'exists()'
				 * will return true if the project is in the process of being deleted.
				 */
				if (project.isAccessible()
                        && project.isNatureEnabled(XpdConsts.PROJECT_NATURE_ID)) {
                    IResource res = project.findMember(PROJECTCONFIGFILE);

                    // If the config file is not found then try synchronizing
                    // the project, if required, and try again
                    if (res == null
                            && !project.isSynchronized(IResource.DEPTH_ONE)) {
                        project.refreshLocal(IResource.DEPTH_ONE, null);

                        // Try finding the file again
                        res = project.findMember(PROJECTCONFIGFILE);
                    }

                    IFile configFile = null;

                    if (res != null && res instanceof IFile) {
                        configFile = (IFile) res;
                    }

                    // If the config file wasn't found then create it
                    if (configFile == null) {
                        configFile = createDefaultProjectConfigFile(project);
                    }

                    if (configFile != null) {
                        // Load the working copy
                        WorkingCopy wc = getWorkingCopy(configFile);

                        if (wc != null
                                && wc.getRootElement() instanceof ProjectConfig) {
                            config = (ProjectConfig) wc.getRootElement();
                        }
                    }
                }
            } catch (CoreException e) {
            }
        }

        return config;
    }

    /**
     * Create default model for project config for given project.
     * 
     * @param project
     *            project where config should be created
     * @return file that contains the model
     */
    public IFile createDefaultProjectConfigFile(final IProject project) {
        return createDefaultProjectConfigFile(project, null);
    }

    /**
     * Create default model for project config for given project and set the
     * project details.
     * 
     * @param project
     *            project where config should be created
     * @param details
     *            the project details to set in the config
     * @return file that contains the model
     * 
     * @since 3.2
     */
    public IFile createDefaultProjectConfigFile(final IProject project,
            ProjectDetails details) {
        // Check that the project exists and is open and the workspace is not
        // locked before creating the config file
        if (project != null && project.isAccessible()
                && !project.getWorkspace().isTreeLocked()) {
            // create empty project config
            ProjectConfigResourceFactoryImpl fact =
                    new ProjectConfigResourceFactoryImpl();
            DocumentRoot doc =
                    ProjectConfigFactory.eINSTANCE.createDocumentRoot();
            doc.setProjectConfig(ProjectConfigFactory.eINSTANCE
                    .createProjectConfig());
            ProjectConfig config = doc.getProjectConfig();
            config.setSpecialFolders(ProjectConfigFactory.eINSTANCE
                    .createSpecialFolders());
            if (details != null) {
                config.setProjectDetails(details);
            }

            final IFile projRes = project.getFile(PROJECTCONFIGFILE);

            final Resource res =
                    fact.createResource(URI.createPlatformResourceURI(projRes
                            .getFullPath().toString(), true));

            res.getContents().clear();
            res.getContents().add(doc);
            // save the model
            try {
                res.save(Collections.EMPTY_MAP);
                return projRes;
            } catch (IOException e) {
                logger.error(e, "Failed to create config file for: " //$NON-NLS-1$
                        + project.getName());
            }
        }

        return null;
    }

    /**
     * Add a working copy creation listener.
     * <p>
     * NOTE: If <i>notifyOfCreatedWorkingCopies</i> is set to true then the
     * listener will immediately be notified of all working copies that have
     * already been loaded.
     * </p>
     * 
     * @param listener
     *            working copy creation listener to register
     * @param notifyOfCreatedWorkingCopies
     *            set to <code>true</code> if notification is required of
     *            already created working copies.
     */
    public void addWorkingCopyCreationListener(
            IWorkingCopyCreationListener listener,
            boolean notifyOfCreatedWorkingCopies) {
        getWorkingCopyFactoriesManager()
                .addWorkingCopyCreationListener(listener,
                        notifyOfCreatedWorkingCopies);
    }

    /**
     * Remove a working copy creation listener
     * 
     * @param listener
     */
    public void removeWorkingCopyCreationListener(
            IWorkingCopyCreationListener listener) {
        if (listener != null) {
            WorkingCopyCreationListenersManager.getInstance()
                    .removeListener(listener);
        }
    }

    /**
     * Gets generic adapter factory from the shared editing domain.
     * 
     * @return the generic adapter factory.
     */
    public AdapterFactory getAdapterFactory() {
        TransactionalEditingDomain ed = getEditingDomain();
        return ((AdapterFactoryEditingDomain) ed).getAdapterFactory();
    }

    public IndexerService getIndexerService() {
        if (indexerService == null) {
            indexerService = new IndexerServiceImpl();
        }
        return indexerService;
    }

    public static void log(Throwable e) {
        getDefault().getLogger().error(e);
    }

    /**
     * This method checks if the platform is running in the headless mode. Some
     * XPD applications my be run without the workbench but still need a valid
     * display (for example: Process Documentation generator). In this case they
     * would call {@link #overrideStandardDisplay(Display)} method to provide
     * valid not null display. In that case this method will return false as it
     * is requiring fact GUI environment to run (usually it creates not visible
     * shell(s) and SWT components and operate on them).
     * 
     * @return true if the workbench is not running and standard XPD display was
     *         not overwritten.
     * @see #overrideStandardDisplay(Display)
     * @see #getStandardDisplay()
     * @since 3.3
     */
    public static boolean isInHeadlessMode() {
        synchronized (XpdResourcesPlugin.class) {
            return standard == null && !PlatformUI.isWorkbenchRunning();
        }
    }

    /**
     * This method checks if this is the RCP application.
     * 
     * @return <code>true</code> if this is the RCP application.
     * @since 3.5
     */
    public static boolean isRCP() {
        IProduct product = Platform.getProduct();
        return product != null && RCP_PRODUCT_ID.equals(product.getId());
    }
}
