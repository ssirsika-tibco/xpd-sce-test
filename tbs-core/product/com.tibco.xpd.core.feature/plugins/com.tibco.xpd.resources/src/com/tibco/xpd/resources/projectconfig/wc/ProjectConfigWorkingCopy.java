/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */

package com.tibco.xpd.resources.projectconfig.wc;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.projectconfig.AssetType;
import com.tibco.xpd.resources.projectconfig.DocumentRoot;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.ProjectConfigFactory;
import com.tibco.xpd.resources.projectconfig.ProjectConfigPackage;
import com.tibco.xpd.resources.projectconfig.ProjectDetails;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetElement;
import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetManager;
import com.tibco.xpd.resources.projectconfig.provider.ProjectConfigEditPlugin;
import com.tibco.xpd.resources.projectconfig.provider.ProjectConfigItemProviderAdapterFactory;
import com.tibco.xpd.resources.projectconfig.specialfolders.ISpecialFolderModel;
import com.tibco.xpd.resources.projectconfig.specialfolders.ISpecialFolderModel.MultiplicityType;
import com.tibco.xpd.resources.projectconfig.util.ProjectConfigResourceFactoryImpl;
import com.tibco.xpd.resources.wc.AbstractWorkingCopy;
import com.tibco.xpd.resources.wc.InvalidFileException;

/**
 * Working copy for the Project Config.
 * 
 * @author wzurek
 * 
 */
public class ProjectConfigWorkingCopy extends AbstractWorkingCopy implements
        IResourceChangeListener {
    private static final Logger LOG = XpdResourcesPlugin.getDefault()
            .getLogger();

    /** Listeners defined in extension. */
    private static final List<PropertyChangeListener> wcExtetensionListeners;

    /* Initialize wcListeners from extensions. */
    static {
        List<PropertyChangeListener> wcListeners =
                new ArrayList<PropertyChangeListener>();
        IConfigurationElement[] configurationElements =
                Platform.getExtensionRegistry()
                        .getConfigurationElementsFor(XpdResourcesPlugin.ID_PLUGIN,
                                "projectConfigListeners"); //$NON-NLS-1$
        for (IConfigurationElement ce : configurationElements) {
            if ("workingCopyListener".equals(ce.getName())) { //$NON-NLS-1$
                try {
                    Object extension = ce.createExecutableExtension("class"); //$NON-NLS-1$
                    if (extension instanceof PropertyChangeListener) {
                        PropertyChangeListener wcListener =
                                (PropertyChangeListener) extension;
                        wcListeners.add(wcListener);
                    }
                } catch (CoreException e) {
                    LOG.error(e);
                }
            }
        }
        wcExtetensionListeners = wcListeners;
    }

    private ProjectConfigWCListener wcListener;

    /**
     * Project configuration working copy. This maintains the special folders
     * and asset types for a Studio project.
     * 
     * @param resource
     */
    public ProjectConfigWorkingCopy(IResource resource) {
        super(Collections.singletonList(resource));

        /*
         * Listen to resource changes so that when folders marked as special
         * folders are deleted we can update the config NOTE: This should be
         * PRE_BUILD as the resource tree will be modified during the save of
         * the config file.
         */
        ResourcesPlugin.getWorkspace().addResourceChangeListener(this,
                IResourceChangeEvent.PRE_BUILD);

        if (wcExtetensionListeners != null && !wcExtetensionListeners.isEmpty()) {
            /*
             * Register a working copy listener to this working copy so that it
             * can notify project config listeners of any change to this working
             * copy.
             */
            wcListener = new ProjectConfigWCListener();
            addListener(wcListener);
        }
    }

    /**
     * Set the project details for this project configuration.
     * 
     * @param details
     *            project details.
     * @throws IOException
     * @since 3.2
     */
    public void setDetails(ProjectDetails details) throws IOException {
        if (details != null && getRootElement() != null) {
            Command cmd =
                    SetCommand.create(getEditingDomain(),
                            getRootElement(),
                            ProjectConfigPackage.eINSTANCE
                                    .getProjectConfig_ProjectDetails(),
                            details);
            getEditingDomain().getCommandStack().execute(cmd);
            save();
        }
    }

    /**
     * @see com.tibco.xpd.resources.wc.AbstractWorkingCopy#doCheckFileName(org.eclipse.core.resources.IResource)
     * 
     * @param resource
     * @return
     */
    @Override
    protected String doCheckFileName(IResource resource) {
        /* No need to check filename for the config file. */
        return null;
    }

    @Override
    protected List<IResource> getDependencyHandles() {
        /*
         * Config files don't have dependencies.
         */
        return Collections.emptyList();
    }

    @Override
    protected void doSaveDependencyCache() throws CoreException {
        /*
         * Do nothing as the project config has no dependencies
         */
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.wc.AbstractWorkingCopy#createAdapterFactory()
     */
    @Override
    protected AdapterFactory createAdapterFactory() {
        registerResourceProvider(ProjectConfigPackage.eINSTANCE,
                ProjectConfigEditPlugin.getPlugin());

        return new ProjectConfigItemProviderAdapterFactory();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.wc.AbstractWorkingCopy#createEditingDomain()
     */
    @Override
    protected EditingDomain createEditingDomain() {
        return new AdapterFactoryEditingDomain(getAdapterFactory(),
                new BasicCommandStack());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.wc.AbstractWorkingCopy#doLoadModel()
     */
    @Override
    protected EObject doLoadModel() throws InvalidFileException {
        URI uri =
                URI.createPlatformResourceURI(getFirstResource().getFullPath()
                        .toPortableString(), true);
        Resource res =
                new ProjectConfigResourceFactoryImpl().createResource(uri);
        EObject result = null;
        try {
            res.load(null);
            DocumentRoot root = (DocumentRoot) res.getContents().get(0);
            root.getProjectConfig().setProject(getFirstResource().getProject());

            /*
             * If the SpecialFolders element is not present under the
             * ProjectConfig then add it
             */
            if (root.getProjectConfig().getSpecialFolders() == null) {
                root.getProjectConfig()
                        .setSpecialFolders(ProjectConfigFactory.eINSTANCE.createSpecialFolders());
            }

            result = root.getProjectConfig();
        } catch (IOException e) {
            throw new InvalidFileException();
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.wc.AbstractWorkingCopy#doSave()
     */
    @Override
    protected void doSave() throws IOException {
        getRootElement().eResource().save(null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.WorkingCopy#getName()
     */
    @Override
    public String getName() {
        return getFirstResource().getProject().getName();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.WorkingCopy#getWorkingCopyEPackage()
     */
    @Override
    public EPackage getWorkingCopyEPackage() {
        return ProjectConfigPackage.eINSTANCE;
    }

    @Override
    public String getMetaText(EObject eo) {
        if (eo instanceof SpecialFolder) {
            SpecialFolder sf = (SpecialFolder) eo;
            String text = sf.getLocation();
            ProjectConfig config = (ProjectConfig) getRootElement();

            if (config != null) {
                text += " - " + config.getProject().getName();
            }

            return text;
        }

        return super.getMetaText(eo);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.core.resources.IResourceChangeListener#resourceChanged(org
     * .eclipse.core.resources.IResourceChangeEvent)
     */
    @Override
    public void resourceChanged(final IResourceChangeEvent event) {

        try {
            event.getDelta().accept(new IResourceDeltaVisitor() {

                /*
                 * (non-Javadoc)
                 * 
                 * @see
                 * org.eclipse.core.resources.IResourceDeltaVisitor#visit(org
                 * .eclipse.core.resources.IResourceDelta)
                 */
                @Override
                public boolean visit(IResourceDelta delta) throws CoreException {
                    boolean ret = true;
                    IResource res = delta.getResource();

                    if (delta.getFlags() == IResourceDelta.MOVED_TO
                            && res instanceof IFolder) {
                        /*
                         * Resource possibly renamed so check if the existing
                         * resource is a special folder, if it is then unmark it
                         * as special folder and mark new resource as special
                         * folder
                         */
                        ProjectConfig config =
                                XpdResourcesPlugin.getDefault()
                                        .getProjectConfig(res.getProject());

                        if (config != null) {
                            SpecialFolder sFolder =
                                    config.getSpecialFolders()
                                            .getFolder((IFolder) res);

                            if (sFolder != null) {
                                IPath path = delta.getMovedToPath();

                                if (path != null) {
                                    IResource newResource =
                                            ResourcesPlugin.getWorkspace()
                                                    .getRoot().findMember(path);

                                    if (newResource instanceof IFolder) {
                                        IFolder target = (IFolder) newResource;
                                        ProjectConfig targetConfig;

                                        // If target folder is not in the same
                                        // project then update the appropriate
                                        // config
                                        if (target.getProject().equals(res
                                                .getProject())) {
                                            targetConfig = config;
                                        } else {
                                            targetConfig =
                                                    XpdResourcesPlugin
                                                            .getDefault()
                                                            .getProjectConfig(target
                                                                    .getProject());
                                        }

                                        if (targetConfig != null) {
                                            /*
                                             * Remove the special folder being
                                             * replaced before the new target is
                                             * marked as special folder. This is
                                             * so that single-instance special
                                             * folders can be re-marked.
                                             */
                                            config.getSpecialFolders()
                                                    .removeFolder(sFolder);

                                            /*
                                             * Mark this as a special folder if
                                             * allowed
                                             */
                                            if (canMarkAsSpecialFolder(targetConfig,
                                                    newResource,
                                                    sFolder.getKind())) {
                                                targetConfig
                                                        .getSpecialFolders()
                                                        .addFolder((IFolder) newResource,
                                                                sFolder.getKind());
                                            }

                                            ret = false;
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (res instanceof IFolder
                            && (res.isPhantom() || (delta.getKind() == IResourceDelta.REMOVED))) {
                        ProjectConfig config =
                                XpdResourcesPlugin.getDefault()
                                        .getProjectConfig(res.getProject());

                        if (config != null
                                && config.getSpecialFolders() != null) {
                            // If this is a special folder then remove it
                            SpecialFolder sFolder =
                                    config.getSpecialFolders()
                                            .getFolder((IFolder) res);

                            if (sFolder != null) {
                                config.getSpecialFolders()
                                        .removeFolder(sFolder);

                                // No point carrying on with delta
                                ret = false;
                            }
                        }
                    }

                    return ret;
                }

            });
        } catch (CoreException e1) {
            XpdResourcesPlugin.getDefault().getLogger().error(e1);
        }
    }

    /**
     * @see com.tibco.xpd.resources.wc.AbstractWorkingCopy#getResources()
     * 
     * @return
     */
    @Override
    protected Collection<Resource> getResources() {
        // Get the resource if the model is loaded
        if (isLoaded()) {
            Resource resource = rootElement.eResource();

            if (resource != null) {
                return Collections.singletonList(resource);
            }
        }

        return null;
    }

    @Override
    public void dispose() {
        if (wcListener != null) {
            removeListener(wcListener);
        }

        ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
        super.dispose();
    }

    /**
     * Check if the given kind of special folder can be set for this project. It
     * can only be set if the target folder (or parents) is not already a
     * special folder, the associated asset type (if specified) is configured
     * and, if a folder of this kind already exists, checks if multiple folders
     * of the kind are allowed.
     * 
     * @param config
     *            target project config
     * @param res
     *            the new resource being marked as special folder
     * @param folderKind
     *            special folder kind
     * @return <code>true</code> if folder can be marked, <code>false</code>
     *         otherwise.
     */
    private boolean canMarkAsSpecialFolder(ProjectConfig config, IResource res,
            String folderKind) {
        boolean canMark = false;

        if (config != null && res != null && folderKind != null) {

            // Check if the target folder is not already a special folder
            canMark =
                    (config.getSpecialFolders().getFolderContainer(res) == null);

            if (canMark) {
                // Check if the asset type for this special folder kind is
                // configured
                canMark = isAssetConfigured(config, folderKind);

                if (canMark) {
                    /*
                     * Check if the project already contains this kind of
                     * special folder and check multiplicity of folder kind
                     */
                    if (!config.getSpecialFolders()
                            .getFoldersOfKind(folderKind).isEmpty()) {
                        ISpecialFolderModel kindInfo =
                                config.getSpecialFolders()
                                        .getFolderKindInfo(folderKind);

                        if (kindInfo != null) {
                            canMark =
                                    kindInfo.getMultiplicity()
                                            .equals(MultiplicityType.MANY);
                        }
                    }
                }
            }
        }

        return canMark;
    }

    /**
     * Check if the asset for the given special folder kind is configured for
     * the project. If no asset type is associated with the special folder kind
     * then <code>true</code> will be returned.
     * 
     * @param config
     *            target project config
     * @param folderKind
     *            kind of special folder's asset to check
     * @return <code>true</code> if the asset is configured for the project,
     *         <code>false</code> otherwise.
     */
    private boolean isAssetConfigured(ProjectConfig config, String folderKind) {
        boolean configured = false;

        if (config != null && folderKind != null) {
            ISpecialFolderModel kindInfo =
                    config.getSpecialFolders().getFolderKindInfo(folderKind);

            if (kindInfo != null) {
                String assetId = kindInfo.getProjectAssetId();
                if (assetId != null) {
                    EList<AssetType> configuredAssets = config.getAssetTypes();
                    if (configuredAssets != null) {
                        /*
                         * SCF-382: If the asset type does not match , check if
                         * the passed asset type extends(is a sub type) the
                         * asset types enabled on the project.
                         */
                        ProjectAssetElement asset =
                                ProjectAssetManager.getProjectAssetMenager()
                                        .getAssetById(assetId);
                        String extendedAssetId =
                                (asset != null) ? asset.getExtends() : null;

                        for (AssetType configuredAsset : configuredAssets) {

                            String configuredAssetId = configuredAsset.getId();
                            configured =
                                    configuredAssetId.equals(assetId)
                                            || configuredAssetId
                                                    .equals(extendedAssetId);

                            if (configured) {
                                break;
                            }
                        }
                    }
                } else {
                    // No asset associated with the folder kind so return true
                    configured = true;
                }
            }
        }

        return configured;
    }

    private class ProjectConfigWCListener implements PropertyChangeListener {
        /**
         * {@inheritDoc}
         */
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            synchronized (wcExtetensionListeners) {
                for (PropertyChangeListener l : wcExtetensionListeners) {
                    l.propertyChange(evt);
                }
            }
        }

    }

}
