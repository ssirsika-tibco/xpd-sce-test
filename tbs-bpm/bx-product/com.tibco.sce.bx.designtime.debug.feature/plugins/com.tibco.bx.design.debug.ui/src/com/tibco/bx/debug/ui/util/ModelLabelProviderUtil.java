package com.tibco.bx.debug.ui.util;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ILabelProvider;

import com.tibco.bx.debug.core.DebugCoreActivator;
import com.tibco.bx.debug.ui.DebugUIActivator;

public class ModelLabelProviderUtil {

	private static final String EXTENSION_MODEL_LABEL_ID = DebugUIActivator.PLUGIN_ID + ".modelLabelProvider"; //$NON-NLS-1$
	private static final String CLASS = "class"; //$NON-NLS-1$
	private static final String ID = "id"; //$NON-NLS-1$
	private static Map<String, ILabelProvider> classMap = new HashMap<String, ILabelProvider>();

	static {
		IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor(
				EXTENSION_MODEL_LABEL_ID);
		for (IConfigurationElement element : elements) {
			try {
				String modelType = element.getAttribute(ID);
				ILabelProvider provider = (ILabelProvider) element.createExecutableExtension(CLASS);
				classMap.put(modelType, provider);
			} catch (Exception e) {
				DebugCoreActivator.log(e);
				continue;
			}
		}
	}

	public static ILabelProvider getModelLabelProvider(String modelType) {
		return classMap.get(modelType);
	}

}
