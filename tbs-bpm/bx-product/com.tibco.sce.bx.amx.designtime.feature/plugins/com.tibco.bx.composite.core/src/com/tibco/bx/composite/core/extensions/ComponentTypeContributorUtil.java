package com.tibco.bx.composite.core.extensions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

import com.tibco.amf.sca.model.componenttype.ComponentType;
import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.bx.composite.core.BxCompositeCoreActivator;

/**
 * This is a helper class that finds all the <code>com.tibco.bx.composite.core.componentTypeContributor</code> extensions
 * and invokes them for a particular SCA <code>Implementation</code> instance. 
 */
public class ComponentTypeContributorUtil {

	private static final String EXTENSION_ID = BxCompositeCoreActivator.PLUGIN_ID
			+ ".componentTypeContributor"; //$NON-NLS-1$
	private static final String CLASS = "class"; //$NON-NLS-1$
	private static final String IMPLEMENTATION_TYPE_ID = "implementationTypeId"; //$NON-NLS-1$
	private static Map<String, List<IComponentTypeContributor>> contributorMap = new HashMap<String, List<IComponentTypeContributor>>();

	private static IComponentTypeContributor[] getComponentTypeContributors(
			String implementationTypeId) {
		Assert.isNotNull(implementationTypeId, "implementationTypeId cannot be Null"); //$NON-NLS-1$
		
		List<IComponentTypeContributor> result = contributorMap.get(implementationTypeId);
		if (result == null) {
			result = new ArrayList<IComponentTypeContributor>();
			contributorMap.put(implementationTypeId, result);

			IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_ID);
			for (IConfigurationElement element : elements) {
				try {
					String id = element.getAttribute(IMPLEMENTATION_TYPE_ID);
					if (implementationTypeId.equals(id)) {
						Object executableExtension = element.createExecutableExtension(CLASS);
						result.add((IComponentTypeContributor) executableExtension);
					}
				} catch (CoreException e) {
					BxCompositeCoreActivator.log(e.getStatus());
					continue;
				}
			}
		}
		return result.toArray(new IComponentTypeContributor[result.size()]);
	}

	/**
	 * Finds all the <code>com.tibco.bx.composite.core.componentTypeContributor</code> extensions for
	 * an SCA implementation type (for example, "implementation.bx"), and invokes them for a particular 
	 * <code>Implementation</code> instance. 
	 * @param impl
	 * @param implementationTypeId
	 * @param serviceType 
	 */
	public static void contributeToComponentType(ComponentType serviceType, Implementation impl,
			String implementationTypeId) {
		IComponentTypeContributor[] contributors = getComponentTypeContributors(implementationTypeId);
		for (IComponentTypeContributor contributor : contributors) {
			contributor.contributeToComponentType(serviceType, impl);
		}
	}

}
