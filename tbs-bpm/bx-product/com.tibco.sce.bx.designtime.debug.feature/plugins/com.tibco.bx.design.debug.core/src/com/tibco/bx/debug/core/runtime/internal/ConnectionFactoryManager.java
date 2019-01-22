package com.tibco.bx.debug.core.runtime.internal;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.eclipse.core.runtime.Platform;

import com.tibco.bx.debug.core.DebugCoreActivator;
import com.tibco.bx.debug.core.runtime.IConnectionFactory;

public class ConnectionFactoryManager {

	private static final String EXTENSION_CONNECTION_FACTORY_ID = DebugCoreActivator.PLUGIN_ID+ ".connectionFactories"; //$NON-NLS-1$
	private static final String ID = "id"; //$NON-NLS-1$
	private static final String CLASS = "class"; //$NON-NLS-1$
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
	
	public static IConnectionFactory getConnectionFactory(String type) throws CoreException{
		IConfigurationElement configurationElement = map.get(type);
		if (configurationElement != null) {
			return (IConnectionFactory) configurationElement.createExecutableExtension(CLASS);
		}
		return null;
	}
	
	public static String[] getConnectionFactoryTypes(){
		return map.keySet().toArray(new String[0]);
	}
	
}
