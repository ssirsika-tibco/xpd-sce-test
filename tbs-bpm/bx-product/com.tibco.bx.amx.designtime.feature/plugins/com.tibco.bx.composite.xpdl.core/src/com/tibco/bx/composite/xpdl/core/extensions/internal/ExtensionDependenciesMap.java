package com.tibco.bx.composite.xpdl.core.extensions.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;

import com.tibco.amf.dependency.osgi.ImportPackage;
import com.tibco.amf.dependency.osgi.OsgiFactory;
import com.tibco.amf.dependency.osgi.RequiredBundle;
import com.tibco.amf.dependency.osgi.VersionRange;
import com.tibco.amf.sca.model.componenttype.CapabilityType;
import com.tibco.amf.sca.model.componenttype.ComponentTypeFactory;
import com.tibco.amf.sca.model.componenttype.RequiredCapability;
import com.tibco.amf.sca.model.componenttype.Requirements;
import com.tibco.bx.composite.xpdl.core.N2PEComponentCoreActivator;

public class ExtensionDependenciesMap {

	public static final String EXTENSION_DEPENDENCIES_ID = N2PEComponentCoreActivator.PLUGIN_ID + ".extensionDependencies"; //$NON-NLS-1$

	// elements
	private static final String REQUIRED_BUNDLE = "requiredBundle"; //$NON-NLS-1$
	private static final String IMPORT_PACKAGE = "importPackage"; //$NON-NLS-1$
	private static final String REQUIRED_CAPABILITY = "requiredCapability"; //$NON-NLS-1$

	// attributes
	private static final String OBJECT_CLASS = "objectClass"; //$NON-NLS-1$
	private static final String BUNDLE_ID = "bundleId"; //$NON-NLS-1$
	private static final String MINIMUM_VERSION = "minimum"; //$NON-NLS-1$
	private static final String MAXINUM_VERSION = "maximum"; //$NON-NLS-1$
	private static final String OPTIONAL = "optional"; //$NON-NLS-1$
	private static final String NAME = "name"; //$NON-NLS-1$

	private Map<String, SimpleRequirements> requirementsMap = new HashMap<String, SimpleRequirements>(); //key: objectClass name

	private static ExtensionDependenciesMap INSTANCE = null;

	private static Object matrix = new Object();

	public static ExtensionDependenciesMap getInstance() {
		if (INSTANCE == null) {
			synchronized (matrix) {
				if (INSTANCE == null) {
					INSTANCE = new ExtensionDependenciesMap();
				}
			}
		}
		return INSTANCE;
	}

	private ExtensionDependenciesMap() {
		IConfigurationElement[] elements = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(EXTENSION_DEPENDENCIES_ID);
		for (IConfigurationElement element : elements) {
			String objectClassName = element.getAttribute(OBJECT_CLASS);

			SimpleRequirements requirements = requirementsMap.get(objectClassName);
			if (requirements == null) {
				requirements = new SimpleRequirements();
				requirementsMap.put(objectClassName, requirements);
			}

			IConfigurationElement[] imports = element.getChildren(IMPORT_PACKAGE);
			requirements.addPackageImports(imports);
			
			IConfigurationElement[] requiredBundles = element.getChildren(REQUIRED_BUNDLE);
			requirements.addRequiredBundles(requiredBundles);
			
			IConfigurationElement[] requiredCapabilities = element.getChildren(REQUIRED_CAPABILITY);
			requirements.addRequiredCapabilities(requiredCapabilities);
		}
	}

	public void getExtensionRequirements(Class<?> instanceClass, Requirements requirements) {
		// search from self
		SimpleRequirements extensionRequirements = requirementsMap.get(instanceClass.getName());
		if (extensionRequirements != null) {
			for (RequiredCapability rc : extensionRequirements.getRequiredCapabilities()) {
				addRequiredCapability(requirements, rc);
			}
			for (ImportPackage pkg : extensionRequirements.getPackageImports()) {
				addImportPackage(requirements, pkg);
			}
			for (RequiredBundle rb : extensionRequirements.getRequiredBundles()) {
				addRequiredBundle(requirements, rb);
			}
		}

		// search from super interfaces
		Class<?>[] interfaces = instanceClass.getInterfaces();
		for (Class<?> c : interfaces) {
			getExtensionRequirements(c, requirements);
		}

		// search from super class
		if (instanceClass.getSuperclass() != null) {
			getExtensionRequirements(instanceClass.getSuperclass(), requirements);
		}
	}

	private void addRequiredBundle(Requirements requirements, RequiredBundle newBundle) {
		EList<RequiredBundle> requiredBundles = requirements.getRequiredBundles();
		for (RequiredBundle rb : requiredBundles) {
			if (rb.getName().equals(newBundle.getName())) {
				return;
			}
		}
		requiredBundles.add(newBundle);
	}

	private static void addRequiredCapability(Requirements requirements, RequiredCapability newCapability) {
		EList<RequiredCapability> requiredCapabilities = requirements.getRequiredCapabilities();
		for (RequiredCapability rc : requiredCapabilities) {
			if (rc.getId().equals(newCapability.getId())) {
				return;
			}
		}
		requiredCapabilities.add(newCapability);
	}

	private static void addImportPackage(Requirements requirements, ImportPackage newPackage) {
		EList<ImportPackage> packageImports = requirements.getPackageImports();
		for (ImportPackage ip : packageImports) {
			if (ip.getName().equals(newPackage.getName())) {
				return;
			}
		}
		packageImports.add(newPackage);
	}

	private static class SimpleRequirements {
		
		private List<RequiredCapability> requiredCapabilities;
		private List<RequiredBundle> requiredBundles;
		private List<ImportPackage> packageImports;

		List<RequiredCapability> getRequiredCapabilities() {
			if (requiredCapabilities != null) {
				return Collections.unmodifiableList(requiredCapabilities);
			}
			
			return  Collections.emptyList();
		}

		Collection<RequiredBundle> getRequiredBundles() {
			if (requiredBundles != null) {
				return Collections.unmodifiableList(requiredBundles);
			}
			
			return  Collections.emptyList();
		}

		Collection<ImportPackage> getPackageImports() {
			if (packageImports != null) {
				return Collections.unmodifiableList(packageImports);
			}
			
			return  Collections.emptyList();
		}
		
		void addRequiredBundles(IConfigurationElement[] elements) {
			if (elements.length == 0) return;
			
			if (requiredBundles == null) {
				requiredBundles = new ArrayList<RequiredBundle>(elements.length);
			}
			
			for (IConfigurationElement cfg : elements) {
				String bundleId = cfg.getAttribute(BUNDLE_ID);
				String optional = cfg.getAttribute(OPTIONAL);
				String minimum = cfg.getAttribute(MINIMUM_VERSION);
				String maximum = cfg.getAttribute(MAXINUM_VERSION);
				
				RequiredBundle requiredBundle = OsgiFactory.eINSTANCE.createRequiredBundle();
				requiredBundle.setName(bundleId);
				requiredBundle.setOptional(Boolean.valueOf(optional));
				
				if (minimum != null || maximum != null) {
					VersionRange versionRange = OsgiFactory.eINSTANCE.createVersionRange();
					if (minimum != null) {
						versionRange.setLower(minimum);
						versionRange.setLowerIncluded(true);
					}
					if (maximum != null) {
						versionRange.setUpper(maximum);
						versionRange.setUpperIncluded(false);
					}
					requiredBundle.setRange(versionRange);
				}
				
				requiredBundles.add(requiredBundle);
			}
		}

		void addPackageImports(IConfigurationElement[] elements) {
			if (elements.length == 0) return;
			
			if (packageImports == null) {
				packageImports = new ArrayList<ImportPackage>(elements.length);
			}

			for (IConfigurationElement cfg : elements) {
				String name = cfg.getAttribute(NAME);
				String minimum = cfg.getAttribute(MINIMUM_VERSION);
				String maximum = cfg.getAttribute(MAXINUM_VERSION);
				String optional = cfg.getAttribute(OPTIONAL);
				
				ImportPackage importPackage = OsgiFactory.eINSTANCE.createImportPackage();
				importPackage.setName(name);
				importPackage.setOptional(Boolean.valueOf(optional));
				
				if (minimum != null || maximum != null) {
					VersionRange versionRange = OsgiFactory.eINSTANCE.createVersionRange();
					if (minimum != null) {
						versionRange.setLower(minimum);
						versionRange.setLowerIncluded(true);
					}
					if (maximum != null) {
						versionRange.setUpper(maximum);
						versionRange.setUpperIncluded(false);
					}
					importPackage.setRange(versionRange);
				}

				packageImports.add(importPackage);
			}
		}

		void addRequiredCapabilities(IConfigurationElement[] elements) {
			if (elements.length == 0) return;
			
			if (requiredCapabilities == null) {
				requiredCapabilities = new ArrayList<RequiredCapability>(elements.length);
			}

			for (IConfigurationElement cfg : elements) {
				String id = cfg.getAttribute("id"); //$NON-NLS-1$
				String version = cfg.getAttribute("version"); //$NON-NLS-1$
				String type = cfg.getAttribute("type"); //$NON-NLS-1$
				
		        RequiredCapability requiredCapability = ComponentTypeFactory.eINSTANCE.createRequiredCapability();
				requiredCapability.setId(id);
				requiredCapability.setVersion(version);
				requiredCapability.setType(CapabilityType.get(type));

				requiredCapabilities.add(requiredCapability);
			}
		}
		
	}

}
