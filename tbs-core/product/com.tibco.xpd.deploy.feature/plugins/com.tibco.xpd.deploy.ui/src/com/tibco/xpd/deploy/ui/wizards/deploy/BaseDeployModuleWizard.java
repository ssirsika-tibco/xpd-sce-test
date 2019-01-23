package com.tibco.xpd.deploy.ui.wizards.deploy;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;

import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.WorkspaceModule;
import com.tibco.xpd.deploy.model.extension.Connection;
import com.tibco.xpd.deploy.model.extension.LateBindingSupport;
import com.tibco.xpd.deploy.model.extension.ResourceBinding;
import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.DeployUIConstants;
import com.tibco.xpd.deploy.ui.internal.Messages;
import com.tibco.xpd.resources.util.SpecialFolderUtil;

/**
 * Provides a base deployment wizard for selecting projects, binding shared
 * resource profiles etc.
 * 
 * <p>
 * Designed to be useful in its own right when a deploymentAction is plugged
 * into for invocation by the performFinish method but also for extension if
 * more sophisticated functionality is required.
 * 
 * @author tstephen
 * 
 */
public abstract class BaseDeployModuleWizard extends Wizard implements
        IProjectBasedDeployWizard, IBindingSupportWizard {
    private static final String DEPLOY_WIZARDS_EXTENSION_POINT =
            "deployWizards"; //$NON-NLS-1$

    private static final String SERVER_TYPE_ID_ATTRIBUTE = "serverTypeId"; //$NON-NLS-1$

    private static final String FILTER_KIND_TAG = "filterKind"; //$NON-NLS-1$

    private static final String ATTR_KIND_TAG = "kind"; //$NON-NLS-1$

    private Server server;

    protected BaseSelectModulesPage selectModulesPage;

    private final ArrayList<String> filterKinds = new ArrayList<String>();

    private Collection<WorkspaceModule> workspaceModules;

    private WizardPage lateBindingPage;

    /**
     * Constructor.
     */
    public BaseDeployModuleWizard() {
        super();
        selectModulesPage = new BaseSelectModulesPage(filterKinds);
        setWindowTitle(Messages.BaseDeployModuleWizard_title);
        setDefaultPageImageDescriptor(DeployUIActivator.getDefault()
                .getImageRegistry()
                .getDescriptor(DeployUIConstants.IMG_DEPLOY_MODULE_WIZARD));
        setNeedsProgressMonitor(true);
        setForcePreviousAndNextButtons(true);
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
        filterKinds.clear();
        String serverTypeId = server.getServerType().getId();
        IExtensionRegistry registry = Platform.getExtensionRegistry();
        IConfigurationElement[] elements =
                registry
                        .getConfigurationElementsFor(DeployUIActivator.PLUGIN_ID,
                                DEPLOY_WIZARDS_EXTENSION_POINT);
        for (int i = 0; i < elements.length; i++) {
            IConfigurationElement element = elements[i];
            String extServerTypeId =
                    element.getAttribute(SERVER_TYPE_ID_ATTRIBUTE);
            if (extServerTypeId != null && serverTypeId.equals(extServerTypeId)) {
                IConfigurationElement[] children =
                        element.getChildren(FILTER_KIND_TAG);
                if (children != null) {
                    for (IConfigurationElement child : children) {
                        String kind = child.getAttribute(ATTR_KIND_TAG);

                        if (kind != null) {
                            filterKinds.add(kind);
                        }
                    }
                }
            }
        }
    }

    /**
     * Adding the page(s) to the wizard.
     */
    @Override
    public void addPages() {
        // if (!mappingPageOnly) {
        // if (isBuildRequired()) {
        // buildPage = new BuildPage();
        // addPage(buildPage);
        // }
        addPage(selectModulesPage);

        if (server.getConnection() != null
                && server.getConnection().getAdapter(LateBindingSupport.class) instanceof LateBindingSupport) {
            lateBindingPage = createLateBindingPage();
            if (lateBindingPage != null) {
                addPage(lateBindingPage);
            }
        }

        // endpointMapperPage = new EndpointMapperPage();
        // addPage(endpointMapperPage);
        //
        // formDeployPage = new FormDeployPage();
        // addPage(formDeployPage);
    }

    /**
     * @param lateBindingPage
     */
    public void setLateBindingPage(WizardPage lateBindingPage) {
        this.lateBindingPage = lateBindingPage;
    }

    /**
     * Creates a page with late binding control for setting up modules' late
     * bindings.
     * 
     * @return a page with late binding control for setting up modules' late
     *         bindings. If no page is needed and should be presented method
     *         should return <code>null</code>.
     */
    public WizardPage createLateBindingPage() {
        return new LateBindingWizardPage();
    }

    @Override
    public boolean performFinish() {
        return true;
    }

    public List<URL> getModulesUrls() {
        List<URL> modulesURLs = new ArrayList<URL>();
        List<IProject> selectedProjects = getSelectedProjects();
        for (IProject project : selectedProjects) {
            if (filterKinds != null && filterKinds.size() > 0) {
                for (String filterKind : filterKinds) {
                    ArrayList<IResource> sfResources =
                            SpecialFolderUtil
                                    .getResourcesInSpecialFolderOfKind(project,
                                            filterKind,
                                            null);
                    for (IResource resource : sfResources) {
                        // only top level files are to be deployed
                        if (resource instanceof IFile) {
                            try {
                                modulesURLs.add(resource.getLocationURI()
                                        .toURL());
                            } catch (MalformedURLException e) {
                                // Log only.
                                DeployUIActivator.getDefault().getLogger()
                                        .error(e);
                            }
                        }
                    }
                }
            }
        }
        return modulesURLs;
    }

    public Collection<WorkspaceModule> getWorkspaceModules() {
        return selectModulesPage.getWorkspaceModules();
    }

    /** {@inheritDoc} */
    public void setContextProjects(List<IProject> projects) {
        selectModulesPage.setContextProjects(projects);
    }

    public List<IProject> getSelectedProjects() {
        return selectModulesPage.getSelectedProjects();
    }

    private final Map<URL, Collection<ResourceBinding>> bindingsCache =
            new HashMap<URL, Collection<ResourceBinding>>();

    private Collection<ResourceBinding> currentBingings =
            Collections.emptySet();

    /** {@inheritDoc} */
    public void setContextModules(Collection<URL> modules) {
        // for all modules create map of module to collection of bindings for
        // server.
        Connection connection = getServer().getConnection();
        Object adapter = connection.getAdapter(LateBindingSupport.class);
        if (adapter instanceof LateBindingSupport) {
            LateBindingSupport lateBindingConnection =
                    (LateBindingSupport) adapter;
            for (URL module : modules) {
                if (!bindingsCache.keySet().contains(module)) {
                    Collection<ResourceBinding> moduleBindings =
                            lateBindingConnection.getModuleBindings(module);
                    bindingsCache.put(module, moduleBindings);
                }
            }
            currentBingings = new LinkedHashSet<ResourceBinding>();
            for (URL url : modules) {
                currentBingings.addAll(bindingsCache.get(url));
            }

        }
    }

    /** {@inheritDoc} */
    public Collection<ResourceBinding> getBindings() {
        return currentBingings;
    }
}
