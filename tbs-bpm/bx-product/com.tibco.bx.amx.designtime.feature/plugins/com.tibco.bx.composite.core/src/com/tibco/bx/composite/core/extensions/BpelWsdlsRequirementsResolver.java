package com.tibco.bx.composite.core.extensions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpel.model.Import;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;

import com.tibco.amf.dependency.osgi.ImportPackage;
import com.tibco.amf.dependency.osgi.OsgiFactory;
import com.tibco.amf.dependency.osgi.VersionRange;
import com.tibco.amf.sca.componenttype.util.RequirementsUtil;
import com.tibco.amf.sca.model.componenttype.RequiredCapability;
import com.tibco.amf.sca.model.componenttype.Requirements;
import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.bx.amx.model.service.BxServiceImplementation;
import com.tibco.bx.composite.core.BxCompositeCoreActivator;
import com.tibco.bx.composite.core.it.internal.WsdlDependenciesResolver;
import com.tibco.bx.composite.core.util.BxCompositeCoreConstants;
import com.tibco.bx.composite.core.util.BxCompositeCoreUtil;
import com.tibco.bx.core.model.Process;

public class BpelWsdlsRequirementsResolver implements IRequirementsResolver{

	public void addImplementationRequirements(Requirements requirements,
			Implementation impl) {
		if (!(impl instanceof BxServiceImplementation)) {
			return;
		}

		BxServiceImplementation bxImplementation = (BxServiceImplementation) impl;

		addBpelRequiredCapability(requirements.getRequiredCapabilities());

		addPackageImports(requirements.getPackageImports());

		addIncludedResources(requirements, bxImplementation);
	}

	protected void addPackageImports(List<ImportPackage> imports) {
		imports.add(RequirementsResolverUtil.createAMXModelImportPackage());
		imports.add(createAMXContainerImportPackage());
		imports.add(createGxmlImportPackage());
	}

	protected void addIncludedResources(Requirements requirements,
			BxServiceImplementation bxImplementation) {
		List<String> bpelFiles = getBpelResources(bxImplementation);
		requirements.getIncludedResources().addAll(bpelFiles);
		
		List<String> scriptDescriptorFiles = getScriptDescriptorFiles(bxImplementation);
		requirements.getIncludedResources().addAll(scriptDescriptorFiles);
		
		List<String> bpelImports = getBpelImportsResources(requirements, bxImplementation,bpelFiles);
		requirements.getIncludedResources().addAll(bpelImports);

		List<String> relativePaths = getEMFDependencies(bxImplementation);
		requirements.getIncludedResources().addAll(relativePaths);
	}

	private List<String> getBpelImportsResources(Requirements requirements,
			BxServiceImplementation bxImplementation, List<String> bpelFiles) {
		URI baseUri = bxImplementation.eResource().getURI();
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		// get all imports
		List<URI> importLocations = new ArrayList<URI>();
		for (String bf : bpelFiles) {
			URI resolved = URI.createURI(bf).resolve(baseUri);
			String filePath = resolved.toPlatformString(true);
			IFile bpelFile = root.getFile(new Path(filePath));
			org.eclipse.bpel.model.Process process = BxCompositeCoreUtil
					.getProcess(bpelFile);
			EList<Import> imports = process.getImports();
			for (Import ip : imports) {
				String location = ip.getLocation();
				URI importLocation = URI.createURI(location).resolve(resolved);
				importLocations.add(importLocation);
			}
		}

		// get all referenced by imports
		List<String> relativePaths = RequirementsUtil.getRelativePaths(baseUri,
				importLocations);
		EList<String> includedResources = requirements.getIncludedResources();
		List<String> references = new ArrayList<String>();
		for (String rp : relativePaths) {
			if (includedResources.contains(rp)) {
				continue;
			}
			URI resolve = URI.createURI(rp).resolve(baseUri);
			String filePath = resolve.toPlatformString(true);
			WsdlDependenciesResolver helper = new WsdlDependenciesResolver(
					URI.decode(filePath));
			try {
				List<URI> results = helper.run(null);
				references.addAll(RequirementsUtil.getRelativePaths(baseUri,
						results));
			} catch (Exception e) {
				BxCompositeCoreActivator.logError(e.getMessage(), e);
			}
		}
		return references;
	}

	private List<String> getBpelResources(BxServiceImplementation bxImplementation) {
		URI baseUri = bxImplementation.eResource().getURI();
		List<URI> bpelUris = new ArrayList<URI>();

		EList<Process> processes = bxImplementation.getServiceModel().getProcesses();
		for (Process process : processes) {
			URI uri = URI.createPlatformResourceURI(process.getProcessFileName(), false);
			bpelUris.add(uri);
		}

		return RequirementsUtil.getRelativePaths(baseUri, bpelUris);
	}

	private List<String> getScriptDescriptorFiles(BxServiceImplementation bxImplementation) {
		List<URI> bpelUris = new ArrayList<URI>();

		String scriptDescriptorFileName = bxImplementation.getServiceModel().getScriptDescriptorFileName();
		if (scriptDescriptorFileName != null) {
			URI uri = URI.createPlatformResourceURI(scriptDescriptorFileName, false);
			bpelUris.add(uri);
		}
		
		URI baseUri = bxImplementation.eResource().getURI();
		return RequirementsUtil.getRelativePaths(baseUri, bpelUris);
	}

	private List<String> getEMFDependencies(
			BxServiceImplementation bxImplementation) {
		URI baseUri = bxImplementation.eResource().getURI();
		List<URI> dependencies = RequirementsUtil.getEMFDependencies(
				bxImplementation.eContainer(), false);
		List<String> paths = RequirementsUtil.getRelativePaths(baseUri,
				dependencies);
		return paths;
	}

	protected RequiredCapability addBpelRequiredCapability(List<RequiredCapability> requiredCapabilities) {
		for (RequiredCapability requiredCapability : requiredCapabilities) {
			if (BxCompositeCoreConstants.CAPABILITY_ID.equals(requiredCapability.getId())) {
				//bx capability already exists
				return requiredCapability;
			}
		}

		RequiredCapability bxRequiredCapability = BxCompositeCoreUtil.createBxRequiredCapability();
		requiredCapabilities.add(bxRequiredCapability);
		
		return bxRequiredCapability;
	}

	private ImportPackage createAMXContainerImportPackage() {
		ImportPackage pkgImport = OsgiFactory.eINSTANCE.createImportPackage();
		pkgImport.setName(BxCompositeCoreConstants.BX_AMX_CONTAINER); //$NON-NLS-1$
		VersionRange versionRange = OsgiFactory.eINSTANCE.createVersionRange();
		versionRange.setLower("1.0.0"); //$NON-NLS-1$
		versionRange.setLowerIncluded(true);
		versionRange.setUpper("2.0.0"); //$NON-NLS-1$
		versionRange.setUpperIncluded(false);
		pkgImport.setRange(versionRange);
		return pkgImport;
	}

	private ImportPackage createGxmlImportPackage() {
		ImportPackage pkgImport = OsgiFactory.eINSTANCE.createImportPackage();
		pkgImport.setName(BxCompositeCoreConstants.CTC_WSTX_STAX); //$NON-NLS-1$
		VersionRange versionRange = OsgiFactory.eINSTANCE.createVersionRange();
		versionRange.setLower("3.2.0"); //$NON-NLS-1$
		versionRange.setLowerIncluded(true);
		versionRange.setUpper("4.0.0"); //$NON-NLS-1$
		versionRange.setUpperIncluded(false);
		pkgImport.setRange(versionRange);
		return pkgImport;
	}

	public IResource[] getImplementationResources(Implementation impl) {
		BxServiceImplementation bxImplementation = (BxServiceImplementation) impl;
		String bpelFilePath = bxImplementation.getServiceModel()
				.getModuleName();
		if (bpelFilePath == null || bpelFilePath.equals("")) {
			return new IResource[0];
		}
		IFile bpelFile = ResourcesPlugin.getWorkspace().getRoot().getFile(
				new Path(bpelFilePath));
		return bpelFile.exists() ? new IResource[] { bpelFile }
				: new IResource[0];
	}

}
