package com.tibco.bx.composite.core.util;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.wsdl.extensions.ExtensionDeserializer;
import javax.xml.namespace.QName;

import org.eclipse.bpel.model.ExtensibleElement;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.model.extensions.BPELActivityDeserializer;
import org.eclipse.bpel.model.extensions.BPELExtensionRegistry;
import org.eclipse.bpel.model.extensions.BPELUnknownExtensionDeserializer;
import org.eclipse.bpel.model.resource.BPELResourceFactoryImpl;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.Resource.Factory;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.osgi.service.resolver.BundleDescription;
import org.eclipse.osgi.service.resolver.ExportPackageDescription;
import org.eclipse.pde.core.plugin.IPluginBase;
import org.eclipse.pde.core.plugin.IPluginModelBase;
import org.eclipse.pde.core.plugin.PluginRegistry;
import org.eclipse.wst.wsdl.internal.util.WSDLResourceFactoryImpl;
import org.eclipse.xsd.util.XSDResourceFactoryImpl;
import org.osgi.framework.Version;

import com.tibco.amf.dependency.osgi.ImportPackage;
import com.tibco.amf.dependency.osgi.OsgiFactory;
import com.tibco.amf.dependency.osgi.RequiredBundle;
import com.tibco.amf.dependency.osgi.RequiredFeature;
import com.tibco.amf.dependency.osgi.VersionRange;
import com.tibco.amf.model.productfeature.Dependencies;
import com.tibco.amf.model.productfeature.Dependency;
import com.tibco.amf.model.productfeature.Feature;
import com.tibco.amf.model.productfeature.IncludedPlugin;
import com.tibco.amf.model.productfeature.ProductFeatureFactory;
import com.tibco.amf.model.productfeature.VersionMatch;
import com.tibco.amf.model.productfeature.util.ProductFeatureResourceFactoryImpl;
import com.tibco.amf.sca.componenttype.util.UnprotectedWriteOperation;
import com.tibco.amf.sca.model.componenttype.CapabilityType;
import com.tibco.amf.sca.model.componenttype.ComponentTypeFactory;
import com.tibco.amf.sca.model.componenttype.RequiredCapability;
import com.tibco.amf.sca.model.componenttype.Requirements;
import com.tibco.amf.sca.model.composite.Component;
import com.tibco.amf.sca.model.composite.Composite;
import com.tibco.bx.amx.model.service.BxServiceImplementation;
import com.tibco.bx.bpelExtension.BxExtensionRegistry;
import com.tibco.bx.bpelExtension.extensions.impl.CallProcessImpl;
import com.tibco.bx.bpelExtension.extensions.misc.CallProcessDeserializer;
import com.tibco.bx.bpelExtension.extensions.misc.CallProcessSerializer;
import com.tibco.bx.bpelExtension.extensions.misc.IntermediateEventDeserializer;
import com.tibco.bx.bpelExtension.extensions.misc.OnReceiveEventExtensionElementDeserializer;
import com.tibco.bx.bpelExtension.extensions.misc.ReceiveEventActivityDeserializer;
import com.tibco.bx.bpelExtension.extensions.misc.ScriptDeserializer;
import com.tibco.bx.bpelExtension.extensions.misc.SignalDeserializer;
import com.tibco.bx.bpelExtension.extensions.misc.SignalEventDeserializer;
import com.tibco.bx.bpelExtension.extensions.misc.StartEventDeserializer;
import com.tibco.bx.bpelExtension.extensions.misc.TimerEventDeserializer;
import com.tibco.bx.composite.core.BxCompositeCoreActivator;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

public class BxCompositeCoreUtil {

    public static final String COMPOSITE_EXTENSION = "composite"; //$NON-NLS-1$
    public static final String DAA_EXTENSION = "daa"; //$NON-NLS-1$
    public static final String DAASPEC_EXTENSION = "daaspec"; //$NON-NLS-1$
    public static final String HTTP_CLIENT_EXTENSION = "httpClient";//$NON-NLS-1$

    private static final String EXTENSION_NAMESPACE_URI = "http://www.tibco.com/bpel/2007/extensions"; //$NON-NLS-1$
    private static final String EXTENSION_PREFIX = "tibex"; //$NON-NLS-1$

	public static RequiredBundle createRequiredBundle(String bundleId, String bundleVersion) {
		RequiredBundle requiredBundle = OsgiFactory.eINSTANCE.createRequiredBundle();
		requiredBundle.setName(bundleId);
		requiredBundle.setOptional(false);
		requiredBundle.setRange(createVersionRange(bundleVersion));
		return requiredBundle;
	}
	
	public static RequiredFeature createRequiredFeature(String featureId, String featureVersion) {
		RequiredFeature requiredFeature = OsgiFactory.eINSTANCE.createRequiredFeature();
		requiredFeature.setName(featureId);
		requiredFeature.setOptional(false);
		requiredFeature.setRange(createVersionRange(featureVersion));
		return requiredFeature;
	}
	
	public static VersionRange createVersionRange(String versionString) {
		Version version = new Version(versionString);
		return createVersionRange(version);
	}
	
	public static VersionRange createVersionRange(Version version) {
		VersionRange versionRange = OsgiFactory.eINSTANCE.createVersionRange();
		versionRange.setLower(String.format("%d.%d.%d", new Object[] {version.getMajor(), version.getMinor(), version.getMicro()}));//$NON-NLS-1$
		versionRange.setLowerIncluded(true);
		versionRange.setUpper((version.getMajor() + 1) + ".0.0"); //$NON-NLS-1$
		versionRange.setUpperIncluded(false);
		return versionRange;
	}
	
	public static Dependency createDependency(String bundleId, String bundleVersion) {
		Dependency dependency = ProductFeatureFactory.eINSTANCE.createDependency();
		dependency.setFeature(bundleId);
		dependency.setMatch(VersionMatch.COMPATIBLE);
		dependency.setVersion(bundleVersion);
		return dependency;
	}
	
	public static RequiredCapability createBxRequiredCapability() {
		RequiredCapability bxRequiredCapability = ComponentTypeFactory.eINSTANCE.createRequiredCapability();
		bxRequiredCapability.setId(BxCompositeCoreConstants.CAPABILITY_ID);
		bxRequiredCapability.setType(CapabilityType.FACTORY);
		bxRequiredCapability.setVersion("1.0.0"); //$NON-NLS-1$
		return bxRequiredCapability;
	}

	/**
	 * returns the name of a file without extension
	 * 
	 * @param resource
	 *            : the file
	 * @return the file name without the extension
	 */
	public static String getFileName(IResource resource) {
		if (resource != null) {
			String name = resource.getName();
			String fileExtension = resource.getFileExtension();
			if (fileExtension != null && fileExtension.length() > 0
					&& name.length() > fileExtension.length() - 1) {
				name = name.substring(0, name.length() - fileExtension.length()
						- 1);
			}
			return name;
		}

		return null;
	}
	
	public static String getFileName(EObject object){
		String lastSegment = object.eResource().getURI().lastSegment();
		int lastDot = lastSegment.lastIndexOf(".");//$NON-NLS-1$
		if(lastDot > 0){
			return lastSegment.substring(0,lastDot);
		}
		return "default";//$NON-NLS-1$
	}
	
	public static String getFileName(URI fileUri){
		String lastSegment = fileUri.lastSegment();
		int lastDot = lastSegment.lastIndexOf(".");//$NON-NLS-1$
		if(lastDot > 0){
			return lastSegment.substring(0,lastDot);
		}
		return "default";//$NON-NLS-1$
	}
	
	public static IProject getProject(EObject object){
		URI uri = object.eResource().getURI();
		if(uri.isPlatform()){
			String path = uri.toPlatformString(true);
			return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(path)).getProject();
		}
		return null;
	}

	public static URI getHttpClientFilePath(IProject project, String fileName){
		SpecialFolder specialFolder = createSpecialFolder("sharedResource", "Shared Resources", project);
		IPath p = specialFolder.getFolder().getFullPath().append(fileName+"."+HTTP_CLIENT_EXTENSION);
		URI uri = URI.createPlatformResourceURI(p.toString(), true);
		return uri;
	}

	public static URI getCompositePath(IFile xpdlFile) {
		IProject project = xpdlFile.getProject();
		IFolder compositesFolder = createSpecialFolder("composite", "Composites", project).getFolder(); //$NON-NLS-1$
		IPath path = compositesFolder.getFullPath().append(xpdlFile.getName());
		path = path.removeFileExtension();
		path = path.addFileExtension(COMPOSITE_EXTENSION);
		URI compositePath = URI.createPlatformResourceURI(path.toString(), true);
		return compositePath;
	}

	public static String getDaaPath(IFile xpdlFile) {
		IProject p = xpdlFile.getProject();

		// FIXME: this needs to get the special folder and deal with that.
		IFolder deploymentFolder = createSpecialFolder("deployArtifacts", "Deployment Artifacts", p).getFolder(); //$NON-NLS-1$

		IPath path = deploymentFolder.getFullPath().append(xpdlFile.getName());
		path = path.removeFileExtension();
		path = path.addFileExtension(DAA_EXTENSION);

		return path.toString();
	}
	
	public static String getDaaSpecPath(IFile xpdlFile) {
		IProject p = xpdlFile.getProject();

		// FIXME: this needs to get the special folder and deal with that.
		IFolder deploymentFolder = createSpecialFolder("deployArtifacts", "Deployment Artifacts", p).getFolder();

		IPath path = deploymentFolder.getFullPath().append(xpdlFile.getName());
		path = path.removeFileExtension();
		path = path.addFileExtension(DAASPEC_EXTENSION);

		return path.toString();
	}
	
	
	private static SpecialFolder getSpecialFolder(String kind, IProject project) {
		SpecialFolder specialFolder = SpecialFolderUtil.getSpecialFolderOfKind(
				project, kind);//$NON-NLS-1$
		return specialFolder;
	}

	/**
	 * if the kind special folder exist, return it directly. else create
	 * @param kind
	 * @param folderName
	 * @param project
	 * @return
	 */
	public static SpecialFolder createSpecialFolder(String kind,
			String folderName, IProject project) {
		SpecialFolder specialFolder = getSpecialFolder(kind, project);
		if(specialFolder==null){
			specialFolder = SpecialFolderUtil
			.getCreateSpecialFolderOfKind(project, kind, folderName);
		}
		return specialFolder;
	}
	
	public static IFolder getBpelOutputFolder(URI compositeLocation) {
		//generate the BPEL files in the "processes" sub-directory in the composite file's folder
		String compositePath = compositeLocation.toPlatformString(true);
		IFile compositeFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(compositePath));
		IContainer compositesFolder = compositeFile.getParent();
		IFolder bpelRootFolder = compositesFolder.getFolder(new Path("processes")); //$NON-NLS-1$
		return bpelRootFolder;
	}

	public static IFolder getBpelOutputFolder(IProject project) {
		IFolder compositesFolder = createSpecialFolder("composite", "Composites", project).getFolder(); //$NON-NLS-1$
		IFolder bpelRootFolder = compositesFolder.getFolder(new Path("processes")); //$NON-NLS-1$
		return bpelRootFolder;
	}

	/**
	 * process plugin requirements
	 * @param requirements: Component requirements, can't be null.
	 * @param implementation: bx implementation,can't be null
	 * @param dependentPlugins: plugins referenced by this component, can't be null
	 * @param referencedPackages: packages required by this component,can be null
	 * 							if it's null, then all exported packages in the plugins will be used.
	 */
	public static void handlePluginRequirement(final Requirements requirements,
			final BxServiceImplementation implementation,
			final Set<RequiredBundle> dependentPlugins,
			final Set<String> referencedPackages) {

		if (dependentPlugins.isEmpty()) {
			return;
		}
		UnprotectedWriteOperation operation = new UnprotectedWriteOperation(
				TransactionUtil.getEditingDomain(implementation.eContainer())) {
			protected Object doExecute() {
				try {
					Feature feature = getFeatureResource(implementation);
					addFeatures(requirements, dependentPlugins, feature,
							referencedPackages);
					feature.eResource().save(Collections.EMPTY_MAP);
					return Status.OK_STATUS;
				} catch (Exception e) {
					return BxCompositeCoreActivator.createErrorStatus(e
							.getMessage(), e);
				}
			}

		};
		operation.execute();
	}

	private static Feature getFeatureResource(
			BxServiceImplementation implementation) throws Exception {
		Component component = (Component) implementation.eContainer();
		Composite composite = component.getComposite();

		String featureId = composite.getName() + ".custom.feature";
		String featureFileName = featureId + ".customfeature";

		IFile compositeFile = WorkingCopyUtil.getFile(composite);
		SpecialFolder deploymentSpecialFolder = SpecialFolderUtil
				.getSpecialFolderOfKind(compositeFile.getProject(),
						"deployArtifacts");
		IContainer targetResource = deploymentSpecialFolder != null ? deploymentSpecialFolder
				.getFolder()
				: compositeFile.getParent();
		IFile featureFile = targetResource.getFile(new Path(featureFileName));
		Feature feature = null;
		if (!featureFile.exists()) {
			feature = ProductFeatureFactory.eINSTANCE.createFeature();
			feature.setId(featureId);
			feature.setVersion("1.0.0.qualifier"); //$NON-NLS-1$
			feature.setLabel(composite.getName() + " feature"); //$NON-NLS-1$

			Dependencies dependencies = ProductFeatureFactory.eINSTANCE.createDependencies();
			Dependency dependency = ProductFeatureFactory.eINSTANCE.createDependency();
			dependency.setFeature("com.tibco.silver.runtime.product.feature"); //$NON-NLS-1$
			dependency.setVersion("1.0.0"); //$NON-NLS-1$
			dependency.setMatch(VersionMatch.COMPATIBLE);
			dependencies.getImports().add(dependency);
			
			feature.setRequire(dependencies);
			
			Factory resourceFacotry = new ProductFeatureResourceFactoryImpl();
			Resource resource = resourceFacotry.createResource(URI
					.createPlatformResourceURI(featureFile.getFullPath()
							.toString(), true));
			resource.getContents().add(feature);
			resource.save(Collections.EMPTY_MAP);
		} else {
			ResourceSet resourceSet = new ResourceSetImpl();
			URI uri = URI.createPlatformResourceURI(featureFile.getFullPath()
					.toString(), true);
			Resource resource = resourceSet.getResource(uri, true);
			resource.load(Collections.EMPTY_MAP);
			feature = (Feature) resource.getContents().get(0);
		}
		featureFile.refreshLocal(IResource.DEPTH_INFINITE, null);
		return feature;
	}

	private static void addFeatures(Requirements requirements,
			Set<RequiredBundle> dependentPlugins, Feature feature,
			Set<String> referencedPackages) {
		for (RequiredBundle s : dependentPlugins) {
			if (!isPluginExist(s.getName(), feature)) {
				VersionRange range = s.getRange();
				IncludedPlugin includedPlugin = ProductFeatureFactory.eINSTANCE
						.createIncludedPlugin();
				includedPlugin.setId(s.getName());
				includedPlugin.setVersion(range.getLower());
				feature.getPlugins().add(includedPlugin);
			}

			addExportedPackagesIntoRequirements(requirements, s.getName(),
					referencedPackages);
		}
		
		RequiredFeature requiredFeature = createRequiredFeature(feature.getId(), feature.getVersion());
		requirements.getFeatureDependencies().add(requiredFeature);
	}

	private static void addExportedPackagesIntoRequirements(
			Requirements requirements, String pluginId,
			Set<String> referencedPackages) {
		// package should be exported
		ExportPackageDescription[] exportedPackages = getExportedPackages(pluginId);
		for (ExportPackageDescription epd : exportedPackages) {
			// name can't be null
			String packageName = epd.getName();
			if (packageName == null) {
				continue;
			}
			// package should be not exist
			if (isPackageExist(requirements, packageName)) {
				continue;
			}

			// package should be used
			if (referencedPackages == null
					|| referencedPackages.contains(packageName)) {
				requirements.getPackageImports().add(createImportPackage(epd));
			}
		}
	}

	private static ExportPackageDescription[] getExportedPackages(
			String pluginId) {
		IPluginModelBase[] workspaceModels = PluginRegistry
				.getWorkspaceModels();
		IPluginModelBase model = null;
		for (IPluginModelBase base : workspaceModels) {
			BundleDescription bundleDescription = base.getBundleDescription();
			String id = null;
			if (bundleDescription == null) {
				IPluginBase pluginBase = base.getPluginBase();
				if (pluginBase != null) {
					id = pluginBase.getId();
					if (id.equals(pluginId)) {
						BxCompositeCoreActivator
								.logError(
										"The bundle Description of "
												+ pluginId
												+ " can not be read, Please check the MANIFEST.MF.",
										new NullPointerException(
												"The bundle Description can not be read."));
					}
				}
				continue;
			}
			id = bundleDescription
					.getSymbolicName();
			if (id.equals(pluginId)) {
				model = base;
				break;
			}
		}
		if (model != null) {
			return model.getBundleDescription().getExportPackages();
		}
		return new ExportPackageDescription[0];
	}

	private static ImportPackage createImportPackage(
			ExportPackageDescription epd) {
		ImportPackage importPackage = OsgiFactory.eINSTANCE
				.createImportPackage();
		importPackage.setName(epd.getName());
		importPackage.setRange(createVersionRange(epd.getVersion()));
		return importPackage;
	}

	private static boolean isPluginExist(String pluginId, Feature feature) {
		EList<IncludedPlugin> plugins = feature.getPlugins();
		for (IncludedPlugin ip : plugins) {
			if (ip.getId().equals(pluginId)) {
				return true;
			}
		}
		return false;
	}

	private static boolean isPackageExist(Requirements requirements,
			String packageName) {
		EList<ImportPackage> packageImports = requirements.getPackageImports();
		for (ImportPackage ip : packageImports) {
			if (ip.getName().equals(packageName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * get the Process Model of BPEL from a bpelFile
	 * 
	 * @param bpelFile
	 * @return
	 * @throws IOException
	 */
	public static Process getProcess(IFile bpelFile) {
		if (!bpelFile.exists()) {
			return null;
		}
		ResourceSet resourceSet = new ResourceSetImpl();

		// Register the default resource factory -- only needed for stand-alone!
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
				.put(BxCompositeCoreConstants.BPEL, new BPELResourceFactoryImpl());
		// Resource.Factory.Registry.DEFAULT_EXTENSION, new
		// BPELResourceFactoryImpl());

		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
				.put(BxCompositeCoreConstants.WSDL, new WSDLResourceFactoryImpl());

		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
				.put(BxCompositeCoreConstants.XSD, new XSDResourceFactoryImpl());
		
        BPELExtensionRegistry extensionRegistry = BPELExtensionRegistry.getInstance();
        QName serviceTaskQname = new QName(EXTENSION_NAMESPACE_URI, "extActivity", EXTENSION_PREFIX);
        QName onReceiveEventQname = new QName(EXTENSION_NAMESPACE_URI, "onReceiveEvent", EXTENSION_PREFIX);
        QName receiveEventQname = new QName(EXTENSION_NAMESPACE_URI, "receiveEvent", EXTENSION_PREFIX);
        QName signalEventQname = new QName(EXTENSION_NAMESPACE_URI, "signalEvent", EXTENSION_PREFIX);
        QName startEventQname = new QName(EXTENSION_NAMESPACE_URI, "startEvent", EXTENSION_PREFIX);
        QName intermediateEventQname = new QName(EXTENSION_NAMESPACE_URI, "intermediateEvent", EXTENSION_PREFIX);
        QName timerEventQname = new QName(EXTENSION_NAMESPACE_URI, "timerEvent", EXTENSION_PREFIX);
        QName signalQname = new QName(EXTENSION_NAMESPACE_URI, "signal", EXTENSION_PREFIX);
        QName callProcessQname = new QName(EXTENSION_NAMESPACE_URI, "callProcess", EXTENSION_PREFIX);
        QName scriptQname = new QName(EXTENSION_NAMESPACE_URI, "script", EXTENSION_PREFIX);
        QName attributeQname = new QName(EXTENSION_NAMESPACE_URI, "extensibilityAttributes", EXTENSION_PREFIX);
//        BPELActivityDeserializer bpelActivityDeserializer = extensionRegistry.getActivityDeserializer(serviceTaskQname);
//        if (bpelActivityDeserializer == null) {
//            extensionRegistry.registerActivityDeserializer(serviceTaskQname, new EObjectActivityDeserializer());
//        }

        BxExtensionRegistry bxExtensionRegistry = new BxExtensionRegistry();

        try {
            ExtensionDeserializer bpelExtensionDeserializer = extensionRegistry.queryDeserializer(ExtensibleElement.class, onReceiveEventQname);
            if (bpelExtensionDeserializer == null || bpelExtensionDeserializer instanceof BPELUnknownExtensionDeserializer) {
                extensionRegistry.registerDeserializer(ExtensibleElement.class, onReceiveEventQname, new OnReceiveEventExtensionElementDeserializer(bxExtensionRegistry));
            }
            BPELActivityDeserializer bpelActivityDeserializer = extensionRegistry.getActivityDeserializer(receiveEventQname);
            if (bpelActivityDeserializer == null) {
                extensionRegistry.registerActivityDeserializer(receiveEventQname, new ReceiveEventActivityDeserializer(bxExtensionRegistry));
            }
//            bpelExtensionDeserializer = extensionRegistry.queryDeserializer(ExtensibleElement.class, attributeQname);
//            if (bpelExtensionDeserializer == null || bpelExtensionDeserializer instanceof BPELUnknownExtensionDeserializer) {
//                extensionRegistry.registerDeserializer(ExtensibleElement.class, attributeQname, new BxExtensibilityAttributesDeserializer());
//            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (bxExtensionRegistry.getDeserializer(signalEventQname)==null) {
        	bxExtensionRegistry.registerDeserializer(signalEventQname, new SignalEventDeserializer());
        	bxExtensionRegistry.registerDeserializer(startEventQname, new StartEventDeserializer());
        	bxExtensionRegistry.registerDeserializer(intermediateEventQname, new IntermediateEventDeserializer());
        	bxExtensionRegistry.registerDeserializer(timerEventQname, new TimerEventDeserializer());
        	bxExtensionRegistry.registerDeserializer(signalQname, new SignalDeserializer());
        	bxExtensionRegistry.registerDeserializer(callProcessQname, new CallProcessDeserializer());
        	bxExtensionRegistry.registerDeserializer(scriptQname, new ScriptDeserializer());
        	bxExtensionRegistry.registerSerializer(CallProcessImpl.class, new CallProcessSerializer());
        }

		URI uri = URI.createPlatformResourceURI(bpelFile.getFullPath()
				.toString(), true);
		Resource resource = resourceSet.getResource(uri, true);
		try {
			resource.load(Collections.EMPTY_MAP);
		} catch (IOException e) {
			BxCompositeCoreActivator.logError(e.getMessage(), e);
		}
		EList<EObject> contents = resource.getContents();
		if (contents != null && !contents.isEmpty()) {
			EObject object = contents.get(0);
			if (object instanceof Process) {
				return (Process) object;
			}
		}

		return null;
	}
	
	public static void save(EObject eObject) throws IOException {
		WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(eObject);
		if (wc == null) {
			Resource resource = eObject.eResource();
			URI uri = resource.getURI();
			String path = uri.path();
			IPath filePath = new Path(path);
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(
					filePath);
			if (file != null)
				wc = XpdResourcesPlugin.getDefault().getWorkingCopy(file);
		}
		if (wc != null)
			wc.save();
		else {
			Resource resource = eObject.eResource();
			if (resource != null)
				resource.save(null);
		}
	}
	
	/**
	 * create folder
	 * 
	 * @param folder:
	 *            the folder will be created
	 * @param monitor
	 * @throws CoreException
	 */
	public static void createFolder(IFolder folder, IProgressMonitor monitor)
			throws CoreException {
		while (!folder.getParent().exists() && folder.getParent().getType() == IResource.FOLDER) {
			createFolder((IFolder) folder.getParent(), monitor);
		}
		if (!folder.exists()) {
			folder.create(true, true, monitor);
		}
		return;
	}

	/**
     * creates a folder for a given path
     * 
     * @param absolutePath:
     *            the path
     */
	public static void createFolder(String absolutePath) {
		File file = new File(absolutePath);
		while (!file.getParentFile().exists()) {
			createFolder(file.getParent());
		}
		if (!file.exists() || file.isFile()) {
			file.mkdir();
		}
	}

	private static final String COMPOSITE_MODULES_OUTPUT_KIND = "compositeModulesOutput"; //$NON-NLS-1$
	private static final String SCRIPT_DESCRIPTOR_FILE_EXT = ".scripts"; //$NON-NLS-1$
	public static IFile getScriptDescriptorFile(IProject project) {
		SpecialFolder sf = SpecialFolderUtil.getSpecialFolderOfKind(project, COMPOSITE_MODULES_OUTPUT_KIND);
		if (sf != null && sf.getFolder() != null && sf.getFolder().exists()) {
			String projectId = ProjectUtil.getProjectId(project);
			return sf.getFolder().getFile(projectId + SCRIPT_DESCRIPTOR_FILE_EXT);
		}
		return null;
	}
	
	/**
     * 
     * @param requiredCapabilities
     * @param capabilityId
     * @return
     */
    public static RequiredCapability findCapabilityWithId(
            List<RequiredCapability> requiredCapabilities, String capabilityId) {
        RequiredCapability requiredCapability = null;
        if (requiredCapabilities == null || requiredCapabilities.isEmpty()
                || capabilityId == null || capabilityId.trim().length() < 1) {
            return requiredCapability;
        }
        for (RequiredCapability rc : requiredCapabilities) {
            if (capabilityId.equals(rc.getId())) {
                requiredCapability = rc;
                break;
            }
        }
        return requiredCapability;
    }

}
