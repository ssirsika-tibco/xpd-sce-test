package com.tibco.bx.debug.core.runtime.internal;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.eclipse.core.runtime.Platform;

import com.tibco.bx.debug.core.DebugCoreActivator;
import com.tibco.bx.debug.core.runtime.INodeDefinitionFactory;

public class NodeDefinitionFactoryManager {

	private static final String EXTENSION_CONNECTION_FACTORY_ID = DebugCoreActivator.PLUGIN_ID+ ".nodeDefinitionFactories"; //$NON-NLS-1$
	private static final String ID = "id"; //$NON-NLS-1$
	private static final String CLASS = "class"; //$NON-NLS-1$
	private static final String DEFAULT_ENDPOINT_RUI = "defaultEndpointURI"; //$NON-NLS-1$
	private static Map<String, IConfigurationElement> map = null;
	
	static {
		map = new HashMap<String, IConfigurationElement>();
		IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_CONNECTION_FACTORY_ID);
		for (IConfigurationElement element : elements) {
			try {
				String type = element.getAttribute(ID);
				map.put(type, element);
			} catch (InvalidRegistryObjectException e) {
				DebugCoreActivator.log(e);
				continue;
			}
		}
	}
	
	public static INodeDefinitionFactory getNodeDefinitionFactory(String id) throws CoreException{
		IConfigurationElement configurationElement = map.get(id);
		if (configurationElement != null) {
			return (INodeDefinitionFactory) configurationElement.createExecutableExtension(CLASS);
		}
		return null;
	}
	
	public static String getDefaultEndpointURI(String id){
		IConfigurationElement configurationElement = map.get(id);
		if (configurationElement != null) {
			return (String) configurationElement.getAttribute(DEFAULT_ENDPOINT_RUI);
		}
		return null;
	}
}
