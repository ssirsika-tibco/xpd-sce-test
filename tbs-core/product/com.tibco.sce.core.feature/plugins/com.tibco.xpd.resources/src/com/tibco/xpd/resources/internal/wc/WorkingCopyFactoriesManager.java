/**
 * 
 */
package com.tibco.xpd.resources.internal.wc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.jobs.ILock;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.NotificationImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.XMLResource.URIHandler;
import org.eclipse.gmf.runtime.common.core.util.Trace;

import com.tibco.xpd.resources.IWorkingCopyCreationListener;
import com.tibco.xpd.resources.IWorkingCopyDependencyProvider;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdProjectResourceFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.internal.XpdResourcesDebugOptions;
import com.tibco.xpd.resources.util.URIUtil;

/**
 * Manager for thats take care of the working copy factories.
 * 
 * @author wzurek
 */
public class WorkingCopyFactoriesManager {

    /**
     * map of resource factories for projects for holding project resources //
     * factories.
     */
    private final Map<IProject, XpdProjectResourceFactory> projectResourceFactories;

    /** Dependency providers for working copy classes. */
    private final Map<Class<? extends WorkingCopy>, Set<IWorkingCopyDependencyProvider>> dependencies;

    /** resource changes event broker */
    private final ResourceFactoriesChangeListener resourceFactoriesChangeListener;

    /**
     * Lock used to synchronize getting of the project resource factory
     */
    private static final ILock LOCK = Job.getJobManager().newLock();

    /**
     * The Constructor. It will install resource change listener.
     */
    public WorkingCopyFactoriesManager() {
        projectResourceFactories =
                new HashMap<IProject, XpdProjectResourceFactory>();
        dependencies =
                new HashMap<Class<? extends WorkingCopy>, Set<IWorkingCopyDependencyProvider>>();

        resourceFactoriesChangeListener =
                new ResourceFactoriesChangeListener(this);
        ResourcesPlugin.getWorkspace()
                .addResourceChangeListener(resourceFactoriesChangeListener);
        loadDependencyProviders();
    }

    /**
     * Returns factory for getting WorkingCopies for resources for specified
     * project.
     * 
     * @param project
     *            project
     * @return working copy factory for specified project, may return null if
     *         the project do not have xpd nature
     */
    public XpdProjectResourceFactory getXpdProjectResourceFactory(
            final IProject project) {
        XpdProjectResourceFactory factory = null;
        if (project != null && project.isAccessible()) {
            // Check the cache for the factory
            factory = projectResourceFactories.get(project);

            if (factory == null) {
                // Load the factory
                try {
                    LOCK.acquire();
                    if (project.isAccessible()) {
                        /*
                         * If we were a second thread waiting for the first
                         * thread to complete its run then that thread MAY have
                         * created and cached the resource factory, if so we do
                         * not want to create it again!
                         */
                        factory = projectResourceFactories.get(project);
                        if (factory == null) {
                            /*
                             * Nope, another thread didn't create factory whilst
                             * we were waiting, so we can do it now.
                             */

                            XpdProjectResourceFactoryImpl factoryImpl =
                                    new XpdProjectResourceFactoryImpl(project);
                            projectResourceFactories.put(project, factoryImpl);
                            factory = factoryImpl;

                            /*
                             * create working copy for all existing resources in
                             * the project
                             */
                            factoryImpl.addAllWorkingCopies();

                            // tracing
                            if (Trace.shouldTrace(XpdResourcesPlugin
                                    .getDefault(),
                                    XpdResourcesDebugOptions.DEBUG)) {
                                Trace
                                        .trace(XpdResourcesPlugin.getDefault(),
                                                XpdResourcesDebugOptions.DEBUG,
                                                "--- Created wc factory for: " + project); //$NON-NLS-1$
                            }
                        }
                    }
                } finally {
                    LOCK.release();
                }
            }
        }

        return factory;
    }

    /**
     * @param cls
     *            The class to get providers for.
     * @return A set of dependency providers.
     */
    public Set<IWorkingCopyDependencyProvider> getDependencyProviders(
            Class<? extends WorkingCopy> cls) {
        return dependencies.get(cls);
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

        if (listener != null) {
            WorkingCopyCreationListenersManager.getInstance()
                    .addListener(listener);

            if (notifyOfCreatedWorkingCopies) {
                /*
                 * Get all created working copies from all project factories
                 */
                if (projectResourceFactories != null) {
                    List<XpdProjectResourceFactory> factories =
                            new ArrayList<XpdProjectResourceFactory>(
                                    projectResourceFactories.values());

                    if (factories != null) {
                        for (XpdProjectResourceFactory factory : factories) {
                            WorkingCopy[] workingCopies =
                                    factory.getWorkingCopies();

                            for (WorkingCopy wc : workingCopies) {
                                listener.workingCopyCreated(wc);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Loads the dependency provider extensions.
     */
    private void loadDependencyProviders() {
        dependencies.clear();
        IExtensionPoint extensionPoint =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(XpdResourcesPlugin.ID_PLUGIN,
                                "workingCopyDependencyProvider"); //$NON-NLS-1$
        IConfigurationElement[] configs =
                extensionPoint.getConfigurationElements();
        for (IConfigurationElement config : configs) {
            try {
                Object providerObject =
                        config.createExecutableExtension("provider"); //$NON-NLS-1$
                if (providerObject instanceof IWorkingCopyDependencyProvider) {
                    IWorkingCopyDependencyProvider provider =
                            (IWorkingCopyDependencyProvider) providerObject;
                    Class<? extends WorkingCopy> cls =
                            provider.getWorkingCopyClass();
                    Set<IWorkingCopyDependencyProvider> providers =
                            dependencies.get(cls);
                    if (providers == null) {
                        providers =
                                new HashSet<IWorkingCopyDependencyProvider>();
                        dependencies.put(cls, providers);
                    }
                    providers.add(provider);
                }
            } catch (CoreException e) {
                XpdResourcesPlugin.getDefault().getLogger().error(e);
            }

        }
    }

    /**
     * Dispose - uninstall listeners.
     */
    public void dispose() {
        ResourcesPlugin.getWorkspace()
                .removeResourceChangeListener(resourceFactoriesChangeListener);
    }

    /**
     * @return the projectResourceFactories
     */
    public Map<IProject, XpdProjectResourceFactory> getProjectResourceFactories() {
        return projectResourceFactories;
    }

    /**
     * Revalidate project references for the given resource.
     * 
     * @param res
     *            <code>Resource</code> to revalidate.
     */
    public void revalidateProjectReferences(Resource res) {
        if (res != null) {
            URIHandler handler = null;
            if (res instanceof XMLResource) {
                handler =
                        (URIHandler) ((XMLResource) res)
                                .getDefaultLoadOptions()
                                .get(XMIResource.OPTION_URI_HANDLER);
            }
            if (handler != null) {
                Map<EObject, Collection<Setting>> refs =
                        EcoreUtil.CrossReferencer.find(res.getContents());
                for (EObject refeo : refs.keySet()) {
                    if (refeo.eIsProxy()) {
                        InternalEObject refieo = (InternalEObject) refeo;
                        // use resource uri handler and recreate proxy uri
                        URI uri = refieo.eProxyURI();
                        uri = handler.deresolve(uri);
                        uri = handler.resolve(uri);
                        refieo.eSetProxyURI(uri);
                        Notification notification =
                                new NotificationImpl(Notification.RESOLVE,
                                        null, null);
                        refeo.eNotify(notification);
                    } else {
                        Resource refres = refeo.eResource();
                        if (!res.equals(refres)) {
                            // Re-resolve only if the reference is between files
                            URI olduri = URIUtil.getURIWithFragmentQuery(refeo);

                            if (olduri != null) {
                                URI deresolved = handler.deresolve(olduri);
                                URI reresolved = handler.resolve(deresolved);

                                if (!olduri.equals(reresolved)) {
                                    InternalEObject proxy =
                                            (InternalEObject) refeo.eClass()
                                                    .getEPackage()
                                                    .getEFactoryInstance()
                                                    .create(refeo.eClass());
                                    proxy.eSetProxyURI(reresolved);

                                    Collection<Setting> settings =
                                            refs.get(refeo);
                                    for (Setting setting : settings) {
                                        EObject eo = setting.getEObject();
                                        EStructuralFeature feature =
                                                setting.getEStructuralFeature();
                                        if (!feature.isDerived()
                                                && feature.isChangeable()) {
                                            eo.eSetDeliver(false);
                                            if (feature.isMany()) {
                                                // TODO: not tested!!!
                                                EList list =
                                                        (EList) eo
                                                                .eGet(feature);
                                                int index = list.indexOf(refeo);
                                                list.set(index, proxy);
                                            } else {
                                                eo.eSet(feature, proxy);
                                            }
                                            eo.eSetDeliver(true);
                                            Notification notification =
                                                    new NotificationImpl(
                                                            Notification.RESOLVE,
                                                            null, null);
                                            eo.eNotify(notification);
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

    /**
     * Check references between all working copies in the workspace and validate
     * if they have access to each other when project dependency has changed.
     */
    @SuppressWarnings("unchecked")
    public void revalidateProjectReferences() {
        ResourceSet rset =
                XpdResourcesPlugin.getDefault().getEditingDomain()
                        .getResourceSet();
        Resource[] resources =
                rset.getResources().toArray(new Resource[rset.getResources()
                        .size()]);
        // Re-resolve all broken references
        for (Resource res : resources) {
            revalidateProjectReferences(res);
        }
    }

    /**
     * @param sourceProject
     * @param project
     * @return
     */
    protected boolean hasDependency(IProject source, IProject target) {
        return hasDependency(source, target, new HashSet<IResource>());
    }

    /**
     * Recursive method for searching the dependency
     * 
     * @param source
     * @param target
     * @param visitedProjects
     * @return
     */
    private boolean hasDependency(IProject source, IProject target,
            Set<IResource> visitedProjects) {
        if (source.equals(target)) {
            return true;
        }
        if (visitedProjects.contains(source)) {
            return false;
        }
        visitedProjects.add(source);
        try {
            IProject[] refs = source.getDescription().getReferencedProjects();
            for (IProject ref : refs) {
                if (hasDependency(ref, target, visitedProjects)) {
                    return true;
                }
            }
        } catch (CoreException e) {
            // ignore return false
            e.printStackTrace();
        }
        return false;
    }
}
