package com.tibco.bx.composite.xpdl.core.extensions.internal;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;

import com.tibco.amf.dependency.osgi.ImportPackage;
import com.tibco.amf.dependency.osgi.OsgiFactory;
import com.tibco.amf.dependency.osgi.RequiredBundle;
import com.tibco.amf.dependency.osgi.VersionRange;
import com.tibco.amf.sca.model.componenttype.ComponentTypeFactory;
import com.tibco.amf.sca.model.componenttype.Requirements;
import com.tibco.bx.composite.xpdl.core.N2PEComponentCoreActivator;

class ExtensionDependenciesLoader {

	public static final String EXTENSION_DEPENDENCIES_ID = N2PEComponentCoreActivator.PLUGIN_ID + ".extensionDependencies"; //$NON-NLS-1$

	// elements
	private static final String REQUIRED_BUNDLE = "requiredBundle"; //$NON-NLS-1$
	private static final String IMPORT_PACKAGE = "importPackage"; //$NON-NLS-1$

	// attributes
	private static final String OBJECT_CLASS = "objectClass"; //$NON-NLS-1$
	private static final String BUNDLE_ID = "bundleId"; //$NON-NLS-1$
	private static final String MINIMUM_VERSION = "minimum"; //$NON-NLS-1$
	private static final String MAXINUM_VERSION = "maximum"; //$NON-NLS-1$
	private static final String OPTIONAL = "optional"; //$NON-NLS-1$
	private static final String NAME = "name"; //$NON-NLS-1$

	private Map<String, Requirements> requirementsMap = new HashMap<String, Requirements>(); //key: objectClass name

	public ExtensionDependenciesLoader() {
		IConfigurationElement[] elements = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(EXTENSION_DEPENDENCIES_ID);
		for (IConfigurationElement element : elements) {
			handleOneExtension(element);
		}
	}

	private void handleOneExtension(IConfigurationElement element) {
		String objectClassName = element.getAttribute(OBJECT_CLASS);

		Requirements requirements = requirementsMap.get(objectClassName);
		if (requirements == null) {
			requirements = ComponentTypeFactory.eINSTANCE.createRequirements();
			requirementsMap.put(objectClassName, requirements);
		}

		IConfigurationElement[] imports = element.getChildren(IMPORT_PACKAGE);
		handleImports(imports, requirements.getPackageImports());
		
		IConfigurationElement[] requiredBundles = element.getChildren(REQUIRED_BUNDLE);
		handleRequiredBundles(requiredBundles, requirements.getRequiredBundles());
	}

	private void handleRequiredBundles(IConfigurationElement[] elements, EList<RequiredBundle> requiredBundles) {
		for (IConfigurationElement cfg : elements) {
			String bundleId = cfg.getAttribute(BUNDLE_ID);
			String optional = cfg.getAttribute(OPTIONAL);
			String minimum = cfg.getAttribute(MINIMUM_VERSION);
			String maximum = cfg.getAttribute(MAXINUM_VERSION);
			
			RequiredBundle requiredBundle = OsgiFactory.eINSTANCE.createRequiredBundle();
			requiredBundle.setName(bundleId);
			requiredBundle.setOptional(Boolean.valueOf(optional));
			
			VersionRange versionRange = OsgiFactory.eINSTANCE.createVersionRange();
			versionRange.setLower(minimum);
			versionRange.setLowerIncluded(true);
			versionRange.setUpper(maximum);
			versionRange.setUpperIncluded(false);
			requiredBundle.setRange(versionRange);
			
			requiredBundles.add(requiredBundle);
		}
	}

	private void handleImports(IConfigurationElement[] elements, EList<ImportPackage> packageImports) {
		for (IConfigurationElement cfg : elements) {
			ImportPackage importPackage = OsgiFactory.eINSTANCE.createImportPackage();
			String name = cfg.getAttribute(NAME);
			String minimum = cfg.getAttribute(MINIMUM_VERSION);
			String maximum = cfg.getAttribute(MAXINUM_VERSION);
			String optional = cfg.getAttribute(OPTIONAL);
			
			importPackage.setName(name);
			importPackage.setOptional(Boolean.valueOf(optional));
			VersionRange versionRange = OsgiFactory.eINSTANCE.createVersionRange();
			versionRange.setLower(minimum);
			versionRange.setLowerIncluded(true);
			versionRange.setUpper(maximum);
			versionRange.setUpperIncluded(false);
			importPackage.setRange(versionRange);
			
			packageImports.add(importPackage);
		}
	}

}
