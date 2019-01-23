package com.tibco.bx.debug.core.runtime.internal;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.eclipse.core.runtime.Platform;

import com.tibco.bx.debug.core.DebugCoreActivator;

public class ModelTypeManager {

	private static final String EXTENSION_MODEL_TYPE_ID = DebugCoreActivator.PLUGIN_ID+ ".modelTypes"; //$NON-NLS-1$
	private static final String ID = "id"; //$NON-NLS-1$
	private static final String NAME = "name"; //$NON-NLS-1$
	private static Map<String, String> map = null;
	
	static {
		map = new HashMap<String, String>();
		IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_MODEL_TYPE_ID);
		for (IConfigurationElement element : elements) {
			try {
				String type = element.getAttribute(ID);
				map.put(type, element.getAttribute(NAME));
			} catch (InvalidRegistryObjectException e) {
				DebugCoreActivator.log(e);
				continue;
			}
		}
	}
	
	public static String[] getModelTypes(){
		return map.keySet().toArray(new String[0]);
	}
	
	public static String[] getModelNames(){
		return map.values().toArray(new String[0]);
	}
	
	public static String getModelName(String id){
		return map.get(id);
	}

	public static String getModelType(String name){
		if(name != null)
			for (String id : map.keySet()) {
				if(name.equals(map.get(id)))
					return id;
			}
		return null;
	}
}
