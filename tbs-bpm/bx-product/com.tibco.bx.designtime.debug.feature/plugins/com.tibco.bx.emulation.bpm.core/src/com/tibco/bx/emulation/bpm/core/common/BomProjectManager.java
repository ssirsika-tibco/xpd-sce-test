package com.tibco.bx.emulation.bpm.core.common;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.internal.registry.ExtensionRegistry;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IContributor;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.spi.RegistryContributor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.osgi.service.resolver.BundleDescription;
import org.eclipse.pde.core.plugin.IPluginModelBase;
import org.eclipse.pde.core.plugin.PluginRegistry;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

import com.tibco.bx.emulation.core.EmulationCoreActivator;

/*
 * to cache all the bom emf projects for retrieval of EPackage classes
 */
public class BomProjectManager {

    private static final String TIBCO_PLUGIN_XML = "tibco_plugin.xml"; //$NON-NLS-1$
    private static final String ATT_URI = "uri"; //$NON-NLS-1$
    private static final String ATT_CLASS = "class"; //$NON-NLS-1$
    private static final String ATT_GEN_MODEL = "genModel"; //$NON-NLS-1$

	public static BomProjectManager INSTANCE = new BomProjectManager();
	private Set<IProject> projectsToRefresh;
	private Map<String, EPackage> packageNameToPackageMap;
	
	public static BomProjectManager getDefault(){
		return INSTANCE;
	}
	
	private BomProjectManager() {
		projectsToRefresh = new HashSet<IProject>();
		packageNameToPackageMap = new HashMap<String, EPackage>();
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for (IProject project : projects) {
			if (isBomProject(project)) {
				projectsToRefresh.add(project);
			}
		}
	}
	
	private boolean isBomProject(IProject project) {
        try {
			if (!project.hasNature("org.eclipse.pde.PluginNature")) { //$NON-NLS-1$
				return false;
			}
		} catch (CoreException e) {
			return false;
		}
		
        IResource plugin = project.findMember(TIBCO_PLUGIN_XML);
        if (plugin == null || !plugin.exists()|| !plugin.isAccessible()) {
        	return false;
        }
        
        return true;
	}
	
	public EPackage getEPackage(String packageName) {
		refresh();
		return packageNameToPackageMap.get(packageName);
	
	}

	public void refresh() {
		for (IProject project : projectsToRefresh) {
			try {
				processProject(project);
			} catch (CoreException e) {
				EmulationCoreActivator.log(e.getStatus());
			}
		}
		projectsToRefresh.clear();
	}
	
	public void addProject(IProject project) {
		if (isBomProject(project)) {
			projectsToRefresh.add(project);
		}
	}
	
	public void removeProject(IProject project) {
		if (!isBomProject(project)) {
			return;
		}

		projectsToRefresh.remove(project);

		IPluginModelBase workspaceModel = PluginRegistry.findModel(project);
		if(workspaceModel == null){
			return;
		}

        String bundleId = workspaceModel.getBundleDescription().getSymbolicName();
		String bundleName = workspaceModel.getBundleDescription().getName();
		
		Bundle bundle = Platform.getBundle(bundleId);
		if (bundle != null) {
			try {
				bundle.stop(Bundle.STOP_TRANSIENT);
			} catch (BundleException e) {
				EmulationCoreActivator.log(e);
			}
		}
		IContributor contributor = new RegistryContributor(bundleId, bundleName, null, null);
		IExtension[] extensions = Platform.getExtensionRegistry().getExtensions(contributor);
		for (IExtension extension : extensions) {
			if ("org.eclipse.emf.ecore.generated_package".equals(extension.getExtensionPointUniqueIdentifier())) { //$NON-NLS-1$
				IConfigurationElement[] ces = extension.getConfigurationElements();
				for (IConfigurationElement ce : ces) {
					String packageUri = ce.getAttribute(ATT_URI);
					String packageClassName = ce.getAttribute(ATT_CLASS);
					int pos = packageClassName.lastIndexOf("."); //$NON-NLS-1$
					String packageName = packageClassName.substring(0, pos);
					EPackage.Registry.INSTANCE.remove(packageUri);
					packageNameToPackageMap.remove(packageName);
				}
			}
		}
	}
	
	private void processProject(IProject project) throws CoreException {
        IResource plugin = project.findMember(TIBCO_PLUGIN_XML);
        if (plugin == null || !plugin.exists()|| !plugin.isAccessible()) {
        	return;
        }

        IPluginModelBase workspaceModel = PluginRegistry.findModel(project);
		if(workspaceModel == null){
			return;
		}

		BundleDescription bundleDescription = workspaceModel.getBundleDescription();
		String bundleId = bundleDescription.getSymbolicName();
		String bundleName = bundleDescription.getName();
		IContributor contributor = new RegistryContributor(bundleId, bundleName, null, null);
		Object userToken = ((ExtensionRegistry)Platform.getExtensionRegistry()).getTemporaryUserToken();
		try {
			InputStream is = ((IFile)plugin).getContents();
			Platform.getExtensionRegistry().addContribution(is, contributor, false, bundleName, null, userToken);
		} catch (Throwable t) {
			EmulationCoreActivator.log(t);
		}
	
		try {
			BundleContext context = EmulationCoreActivator.getDefault().getBundle().getBundleContext();
			URI uri = URI.createPlatformResourceURI(project.getFullPath().toString(), true);
			URL projectURL = new URL(uri.toString());
			projectURL = FileLocator.toFileURL(projectURL);
			Bundle bundle = context.installBundle(projectURL.toString());
			bundle.start(Bundle.START_TRANSIENT);
			
			IExtension[] extensions = Platform.getExtensionRegistry().getExtensions(contributor);
			for (IExtension extension : extensions) {
				if ("org.eclipse.emf.ecore.generated_package".equals(extension.getExtensionPointUniqueIdentifier())) {
					IConfigurationElement[] ces = extension.getConfigurationElements();
					for (IConfigurationElement ce : ces) {
						String packageClassName = ce.getAttribute(ATT_CLASS);
						int pos = packageClassName.lastIndexOf("."); //$NON-NLS-1$
						String packageName = packageClassName.substring(0, pos);
						Class<?> javaClass = bundle.loadClass(packageClassName);
					    Field field = javaClass.getField("eINSTANCE");
					    EPackage ePackage = (EPackage)field.get(null);
					    EPackage.Registry.INSTANCE.put(ce.getAttribute("uri"), ePackage);
						packageNameToPackageMap.put(packageName, ePackage);
					}
				}
			}
		} catch (Exception e) {
			EmulationCoreActivator.log(e);
		}
		
	}

}
