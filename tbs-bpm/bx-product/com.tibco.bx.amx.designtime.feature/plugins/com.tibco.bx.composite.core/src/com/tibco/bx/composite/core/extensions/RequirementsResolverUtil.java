package com.tibco.bx.composite.core.extensions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

import com.tibco.amf.dependency.osgi.ImportPackage;
import com.tibco.amf.dependency.osgi.OsgiFactory;
import com.tibco.amf.dependency.osgi.VersionRange;
import com.tibco.amf.sca.model.componenttype.ComponentTypeFactory;
import com.tibco.amf.sca.model.componenttype.Requirements;
import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.bx.composite.core.BxCompositeCoreActivator;
import com.tibco.bx.composite.core.util.BxCompositeCoreConstants;

/**
 * This is a helper class that finds all the <code>com.tibco.bx.composite.core.requirementsResolver</code> extensions
 * and invokes them for a particular SCA <code>Implementation</code> instance. 
 */
public class RequirementsResolverUtil {

	private static final String EXTENSION_DEPENDENCIES_ID = BxCompositeCoreActivator.PLUGIN_ID
			+ ".requirementsResolver"; //$NON-NLS-1$
	private static final String CLASS = "class"; //$NON-NLS-1$
	private static final String IMPLEMENTATION_TYPE_ID = "implementationTypeId"; //$NON-NLS-1$
	private static Map<String, List<IRequirementsResolver>> requirementResolverMap = new HashMap<String, List<IRequirementsResolver>>();

	private static IRequirementsResolver[] getRequirementsResolvers(
			String implementationTypeId) {
		Assert.isNotNull(implementationTypeId, "implementationTypeId cannot be Null"); //$NON-NLS-1$
		
		List<IRequirementsResolver> result = requirementResolverMap.get(implementationTypeId);
		if (result == null) {
			result = new ArrayList<IRequirementsResolver>();
			requirementResolverMap.put(implementationTypeId, result);

			IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_DEPENDENCIES_ID);
			for (IConfigurationElement element : elements) {
				try {
					String id = element.getAttribute(IMPLEMENTATION_TYPE_ID);
					if (implementationTypeId.equals(id)) {
						Object executableExtension = element.createExecutableExtension(CLASS);
						result.add((IRequirementsResolver) executableExtension);
					}
				} catch (CoreException e) {
					BxCompositeCoreActivator.log(e.getStatus());
					continue;
				}
			}
		}
		return result.toArray(new IRequirementsResolver[result.size()]);
	}

	/**
	 * Finds all the <code>com.tibco.bx.composite.core.requirementsResolver</code> extensions for
	 * an SCA implementation type (for example, "implementation.bx"), and invokes them for a particular 
	 * <code>Implementation</code> instance. 
	 * @param impl
	 * @param implementationTypeId
	 */
	public static Requirements resolveRequirements(Implementation impl,
			String implementationTypeId) {
		Requirements requirements = ComponentTypeFactory.eINSTANCE.createRequirements();

		for (IRequirementsResolver resolver : getRequirementsResolvers(implementationTypeId)) {
			resolver.addImplementationRequirements(requirements, impl);
		}

		return requirements;
	}

	public static ImportPackage createAMXModelImportPackage() {
		ImportPackage pkgImport = OsgiFactory.eINSTANCE.createImportPackage();
		pkgImport.setName(BxCompositeCoreConstants.BX_AMX_MODEL_SERVICE); //$NON-NLS-1$
		VersionRange versionRange = OsgiFactory.eINSTANCE.createVersionRange();
		versionRange.setLower("1.0.0"); //$NON-NLS-1$
		versionRange.setLowerIncluded(true);
		versionRange.setUpper("2.0.0"); //$NON-NLS-1$
		versionRange.setUpperIncluded(false);
		pkgImport.setRange(versionRange);
		return pkgImport;
	}

}
