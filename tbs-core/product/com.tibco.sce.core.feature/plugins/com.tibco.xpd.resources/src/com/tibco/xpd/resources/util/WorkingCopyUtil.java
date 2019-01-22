/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.resources.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.internal.db.ResourceDbException;
import com.tibco.xpd.resources.internal.indexer.ResourceDependencyIndexer;
import com.tibco.xpd.resources.internal.indexer.ResourceDependencyIndexer.Type;
import com.tibco.xpd.resources.internal.wc.WorkingCopyProviderAdapter;
import com.tibco.xpd.resources.wc.AbstractWorkingCopy;

/**
 * Utils that provide usefull methods connected to WorkingCopy.
 * 
 * @author wzurek
 */
public final class WorkingCopyUtil {

    /**
     * private constructor.
     * 
     */
    private WorkingCopyUtil() {
    }

    public static Adapter getRegisteredAdapter(Resource resource, Object type) {
        Adapter result;
        try {
            result = EcoreUtil.getRegisteredAdapter(resource, type);
            // workaround for ComposedAdapterFactory
            // it doesn't work with ComposedAdapterFactory beacuse of it's
            // adaptNew method
            // https://bugs.eclipse.org/bugs/show_bug.cgi?id=159737
            // http://dev.eclipse.org/viewcvs/index.cgi/~checkout~/org.eclipse.emf
            // /plugins/org.eclipse.emf.edit/src/org/eclipse/emf/edit/provider/
            // ComposedAdapterFactory
            // .java.diff?r1=1.4&r2=1.5&cvsroot=Tools_Project
        } catch (Exception ex) {
            EList<AdapterFactory> adapterFactories =
                    resource.getResourceSet().getAdapterFactories();
            AdapterFactory adapterFactory =
                    EcoreUtil.getAdapterFactory(adapterFactories, type);
            result = adapterFactory.adapt(resource, type);
        }

        return result;
    }

    public static Adapter getRegisteredAdapter(EObject eObject, Object type) {
        Adapter result;
        // workaround for ComposedAdapterFactory
        // it doesn't work with ComposedAdapterFactory beacuse of it's
        // adaptNew method
        // https://bugs.eclipse.org/bugs/show_bug.cgi?id=159737
        // http://dev.eclipse.org/viewcvs/index.cgi/~checkout~/org.eclipse.emf/
        // plugins/org.eclipse.emf.edit/src/org/eclipse/emf/edit/provider/
        // ComposedAdapterFactory.java.diff?r1=1.4&r2=1.5&cvsroot=Tools_Project
        try {
            result = EcoreUtil.getRegisteredAdapter(eObject, type);
        } catch (Exception ex) {
            Resource resource = eObject.eResource();
            ResourceSet resourceSet = resource.getResourceSet();
            if (resourceSet != null) {
                EList<AdapterFactory> adapterFactories =
                        resourceSet.getAdapterFactories();
                AdapterFactory adapterFactory =
                        EcoreUtil.getAdapterFactory(adapterFactories, type);
                result = adapterFactory.adapt(eObject, type);
            } else {
                result = null;
            }
        }

        return result;
    }

    /**
     * Get the {@link WorkingCopy} of the given resource.
     * 
     * @param resource
     * @return <code>WorkingCopy</code> if it has one, <code>null</code>
     *         otherwise.
     * @since 3.3
     */
    public static WorkingCopy getWorkingCopy(IResource resource) {
        return XpdResourcesPlugin.getDefault().getWorkingCopy(resource);
    }

    /**
     * Returns working copy for given object. It use WorkingCopyProviderAdapter
     * from the resource, so any EObject that does not belongs to such resource
     * will end up with null. In this case an attempt will be made to create the
     * working copy from the EObjects file.
     * <p>
     * Nominally only returns workingCopy for EObject in an EMF resource for a
     * file that already has a workingCopy created. Use
     * forceWorkingCopyCreation=true in (rare) situations where you're trying to
     * access a working copy for an Eobject whose EMF resource is not loaded
     * into a workingCopy yet.
     * </p>
     * 
     * @param eo
     *            eobject to find working copy for.
     * @param forceWorkingCopyCreation
     *            <code>true</code> to force
     * 
     * @return working copy for this object
     */
    public static WorkingCopy getWorkingCopyFor(EObject eo,
            boolean forceWorkingCopyCreation) {
        WorkingCopy wc = null;
        Resource res = eo.eResource();
        if (res != null) {
            WorkingCopyProviderAdapter adapter =
                    (WorkingCopyProviderAdapter) getRegisteredAdapter(res,
                            WorkingCopy.class);
            wc = adapter == null ? null : adapter.getWorkingCopy();
        }

        /*
         * SCF-410 - Nick: force working copy creation if it is not already
         * loaded.
         */
        if (wc == null) {
            /*
             * SCF-413 - Sid: Make force creation optional. Otherwise we ran
             * into issues with generate DAA because AMX's CompositeWorkingCopy
             * does (incorrectly we think) a edDomain.startTransaction() as part
             * of it's wc.doLoadModel() and that was being called as a result of
             * getWorkingCopyFor() which was being called during initial save of
             * the composite resource (prior to the file existing).
             */
            if (forceWorkingCopyCreation) {
                IFile file = getFile(eo);
                if (file != null) {
                    wc = getWorkingCopy(file);
                    /*
                     * SCF-432: Need to get the root element in order to
                     * attached the working copy adapter to the EMF model so
                     * that NEXT time someone just does getWorkingCopy() without
                     * a forceCreateion=true, then it will work - else we`ll
                     * have to do force creation all over the place.
                     */
                    if (wc != null) {
                        @SuppressWarnings("unused")
                        EObject root = wc.getRootElement();
                    }
                }
            }
        }
        return wc;
    }

    /**
     * Returns working copy for given object. It use WorkingCopyProviderAdapter
     * from the resource, so any EObject that does not belongs to such resource
     * will end up with null. In this case an attempt will be made to create the
     * working copy from the EObjects file.
     * <p>
     * Only returns workingCopy for EObject in an EMF resource for a file that
     * already has a workingCopy created. Use
     * {@link #getWorkingCopyFor(EObject, boolean)} with
     * forceWorkingCopyCreation=true in (rare) situations where you're trying to
     * access a working copy for an Eobject whose EMF resource is not loaded
     * into a workingCopy yet.
     * </p>
     * 
     * @param eo
     *            eobject
     * 
     * @return working copy for this object
     */
    public static WorkingCopy getWorkingCopyFor(EObject eo) {
        return getWorkingCopyFor(eo, false);
    }

    /**
     * @param eo
     * @return <code>true</code> if object is in a read only EMF resource.
     */
    public static boolean isReadOnly(EObject eo) {
        if (eo != null) {
            WorkingCopy workingCopyFor = getWorkingCopyFor(eo);
            if (workingCopyFor != null) {
                return workingCopyFor.isReadOnly();
            }
        }
        return false;
    }

    /**
     * Get the project in which the given objects resource belongs to.
     * <p>
     * Nominally only returns workingCopy for EObject in an EMF resource for a
     * file that already has a workingCopy created. Use
     * forceWorkingCopyCreation=true in (rare) situations where you're trying to
     * access a working copy for an Eobject whose EMF resource is not loaded
     * into a workingCopy yet.
     * </p>
     * 
     * @param eo
     *            eobject to find working copy for.
     * @param forceWorkingCopyCreation
     *            <code>true</code> to force
     * 
     * @return The project.
     */
    public static IProject getProjectFor(EObject eo,
            boolean forceWorkingCopyCreation) {
        IProject project = null;
        WorkingCopy wc =
                WorkingCopyUtil.getWorkingCopyFor(eo, forceWorkingCopyCreation);
        if (wc != null) {
            IResource resource = wc.getEclipseResources().get(0);
            if (resource != null) {
                project = resource.getProject();
            }
        }
        return project;
    }

    /**
     * Get the project in which the given objects resource belongs to.
     * <p>
     * Only returns workingCopy for EObject in an EMF resource for a file that
     * already has a workingCopy created. Use
     * {@link #getProjectFor(EObject, boolean)} forceWorkingCopyCreation=true in
     * (rare) situations where you're trying to access a working copy for an
     * Eobject whose EMF resource is not loaded into a workingCopy yet.
     * </p>
     * 
     * @param eo
     *            eobject to find working copy for.
     * 
     * @return The project.
     */
    public static IProject getProjectFor(EObject eo) {
        return getProjectFor(eo, false);
    }

    /**
     * This method returns EditingDomain associated with the passed EObject.
     * 
     * @param eo
     *            EObject
     * @return editing domain
     */
    public static EditingDomain getEditingDomain(EObject eo) {
        Resource resource = eo.eResource();
        if (resource != null) {
            IEditingDomainProvider existingAdapter =
                    (IEditingDomainProvider) getRegisteredAdapter(eo.eResource(),
                            IEditingDomainProvider.class);
            /* mmaciuki: existingAdapter may be null */
            if (existingAdapter == null) {
                if (eo.eResource() != null
                        && eo.eResource().getResourceSet() != null
                        && eo.eResource().getResourceSet() == XpdResourcesPlugin
                                .getDefault().getEditingDomain()
                                .getResourceSet()) {
                    return XpdResourcesPlugin.getDefault().getEditingDomain();
                }
                return null;
            }
            EditingDomain editingDomain = existingAdapter.getEditingDomain();
            return editingDomain;
        } else {
            return null;
        }
    }

    /**
     * Get object's description from ItemProvider adapter.
     * 
     * @param eo
     *            EObject
     * @return text from itep provider
     */
    public static String getText(EObject eo) {
        String result = null;
        IItemLabelProvider ad = getAdapter(eo);
        if (ad != null) {
            result = ad.getText(eo);
        } else {
            EStructuralFeature feature =
                    eo.eClass().getEStructuralFeature("name");//$NON-NLS-1$
            if (feature != null) {
                result = eo.eGet(feature).toString();
            }

            if (result == null) {
                result = eo.toString();
            }
        }
        return result;
    }

    private static IItemLabelProvider getAdapter(EObject eo) {
        IItemLabelProvider ad;
        ad =
                (IItemLabelProvider) WorkingCopyUtil.getRegisteredAdapter(eo,
                        IItemLabelProvider.class);
        if (ad == null) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(eo);
            AdapterFactory factory = null;
            if (wc != null) {
                factory = wc.getAdapterFactory();
            } else {
                factory = XpdResourcesPlugin.getDefault().getAdapterFactory();
            }
            if (factory != null) {
                ad =
                        (IItemLabelProvider) factory.adapt(eo, eo.eClass()
                                .getEPackage());
            }
        }
        return ad;
    }

    /**
     * Get meta-data name of the EObject, it takes trnaslateble description of
     * the EClass from the model.edit plugin.
     * <p>
     * Example: for EObject of type Activity it will return 'Activity', as
     * oposite to getText() which will return activity name.
     * 
     * @param eo
     *            object to test
     * @return meta-data description
     */
    public static String getMetaText(EObject eo) {
        WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(eo);
        if (wc == null) {
            return getText(eo);
        }
        return wc.getMetaText(eo);
    }

    /**
     * Returns image of the EObject.
     * 
     * @param eo
     *            EObject
     * @return image from item provider
     */
    public static Image getImage(EObject eo) {
        if (XpdResourcesPlugin.isInHeadlessMode()) {
            return null;
        }
        Image result = null;
        IItemLabelProvider ad = getAdapter(eo);

        if (ad != null) {
            Object image = ad.getImage(eo);
            if (image != null) {
                //
                // DO NOT attempt to use extended image registry if we are not
                // on the UI thread.
                // ExtendedImageRegistry class load will fail if not called on
                // UI thread (it has a static instance which use
                // display.getCurrent() without checking).
                if (Display.getCurrent() != null) {
                    result =
                            ExtendedImageRegistry.getInstance().getImage(image);
                }
            }
        }

        return result;
    }

    /**
     * Get the image descriptor of the given EObject.
     * 
     * @param eo
     *            EObject
     * @return image descriptor from item provider, <code>null</code> if one
     *         cannot be determined.
     */
    public static ImageDescriptor getImageDescriptor(final EObject eo) {

        WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(eo);
        /*
         * DO NOT attempt to use extended image registry if we are not on the UI
         * thread. ExtendedImageRegistry class load will fail if not called on
         * UI thread (it has a static instance which use display.getCurrent()
         * without checking).
         */
        if (wc != null && Display.getCurrent() != null) {

            /*
             * Most of the cases adapter factory must not be null. But checking
             * for not null to avoid getting NPE
             */
            AdapterFactory adapterFactory = wc.getAdapterFactory();

            if (null != adapterFactory) {

                IItemLabelProvider ad =
                        (IItemLabelProvider) adapterFactory.adapt(eo,
                                IItemLabelProvider.class);
                /* Cannot assume that it cannot be null, so check for not null! */
                if (null != ad) {

                    return ExtendedImageRegistry.getInstance()
                            .getImageDescriptor(ad.getImage(eo));
                }
            }
        }
        return new ImageDescriptor() {
            @Override
            public ImageData getImageData() {

                Image image = getImage(eo);
                /*
                 * getImage() can return null. So check for not null to avoid
                 * getting NPE
                 */
                if (null != image) {

                    return image.getImageData();
                }
                return null;
            }
        };
    }

    /**
     * Build and returns list of all resources dependent on given resource. This
     * will search all referencing projects.
     * 
     * @param res
     *            resource
     * @return all resources dependent on the given resource, empty list if
     *         none.
     */
    public static Collection<IResource> getAffectedResources(final IResource res) {
        List<IResource> result = new ArrayList<IResource>();
        Set<IProject> projects = new HashSet<IProject>();
        IProject resProject = res.getProject();
        projects.add(resProject);
        ProjectUtil.getReferencingProjectsHierarchy(resProject, projects);

        Collection<IResource> dependencies =
                ResourceDependencyIndexer.getInverseDependencies(res,
                        Type.EXPLICIT);

        /*
         * Make sure the dependencies are in the referenced projects, ignore any
         * other
         */
        for (IResource resource : dependencies) {
            if (projects.contains(resource.getProject())) {
                result.add(resource);
            }
        }

        return result;
    }

    /**
     * Get the <code>IFile</code> resource that contains the given
     * <code>EObject</code>.
     * 
     * @param eo
     *            <code>EObject</code> to get the container <code>IFile</code>
     *            of.
     * @return <code>IFile</code> of the <code>EObject</code> resource.
     */
    public static IFile getFile(EObject eo) {
        IFile result = null;
        Resource resource = eo.eResource();

        if (resource != null) {
            result = WorkspaceSynchronizer.getFile(resource);
        }

        return result;
    }

    /**
     * Get all proxy resources from the working copy dependency index.
     * 
     * @return map of resources with proxy dependencies (key being the proxy
     *         resource path and the value being collection of files that has a
     *         dependency on this proxy), empty map if none found.
     * @since 3.5.10
     */
    public static Map<IPath, Collection<IFile>> getProxyDependencies() {
        try {
            return ResourceDependencyIndexer.getProxyResources();
        } catch (ResourceDbException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
        return Collections.emptyMap();
    }

    /**
     * <p>
     * This method is intended to be called from workspace operations to ensure
     * that when a project, folder or resource is deleted then any associated
     * working copies are properly disposed of.
     * </p>
     * <p>
     * Calling this method on a project or folder will delete the entire project
     * or folder and clear up any working copies that it contains. Calling on a
     * file will just clear up the working copy and delete that single file.
     * </p>
     * <p>
     * This method was created to handle the situation where a resource was
     * deleted and then recreated as part of the same workspace operation. At
     * the end of the operation, as the resource existed, there were no event
     * notifications to indicate that the resource had been deleted. This meant
     * that the working copy didn't unload the old resource and the associated
     * entries in the inverse cross-reference map in
     * org.eclipse.uml2.common.util.CacheAdapter were not removed. This could
     * cause the map to grow very large and result in heap space issues.
     * </p>
     * <p>
     * Please see http://jira.tibco.com/browse/XPD-6655 for details of the
     * original issue.
     * </p>
     * 
     * @param root
     *            The root resource to remove from.
     * @param monitor
     *            A progress monitor or null.
     * @throws CoreException
     *             If there were problems removing the resource.
     */
    public static void deleteResourceInWorkspaceOperation(final IResource root,
            final IProgressMonitor monitor) throws CoreException {
        root.accept(new IResourceVisitor() {
            @Override
            public boolean visit(IResource resource) throws CoreException {
                if (resource instanceof IFile) {
                    IFile file = (IFile) resource;
                    WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(file);
                    if (wc instanceof AbstractWorkingCopy) {
                        ((AbstractWorkingCopy) wc).dispose();
                    }
                    file.delete(true, monitor);
                }
                return resource instanceof IContainer;
            }
        });
        if (root instanceof IContainer) {
            root.delete(true, monitor);
        }
    }

    /**
     * Get all of the resources that the given resource depends on directly or
     * indirectly.
     * 
     * @param inputResource
     * 
     * @return All of the resources that the given resource depends on directly
     *         or indirectly.
     * @since 3.6.2
     */
    public static Set<IResource> getDeepDependencies(IResource inputResource) {
        Set<IResource> nestedDependencies = new LinkedHashSet<IResource>();

        recursiveGetDeepDependencies(inputResource, nestedDependencies);

        return nestedDependencies;
    }

    /**
     * Gets Deep Dependencies by Recursively populating the input Set<>
     * <b>"filesAndDependencies"</b> with all the IResource's on which the input
     * file <b>"inputResource"</b> is dependent on.
     * 
     * @param inputResource
     *            : the IResouce whose deep dependencies on other files needs to
     *            be fetched.
     * @param nestedDependencies
     *            : An empty set which will be populated with all the dependent
     *            IResouce's at the end of method call.
     * @since 3.6.2
     */
    private static void recursiveGetDeepDependencies(IResource inputResource,
            Set<IResource> nestedDependencies) {

        WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(inputResource);
        if (wc != null) {
            List<IResource> dependencies = wc.getDependency();
            if (dependencies != null) {
                for (IResource eachDependency : dependencies) {
                    if (!nestedDependencies.contains(eachDependency)) {
                        nestedDependencies.add(eachDependency);
                        recursiveGetDeepDependencies(eachDependency,
                                nestedDependencies);
                    }
                }
            }
        }
    }
}
