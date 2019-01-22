package com.tibco.bx.xpdl2bpel;

import org.eclipse.bpel.model.adapters.AdapterRegistry;
import org.eclipse.bpel.model.adapters.BasicBPELAdapterFactory;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.bx.bpelExtension.BxExtensionRegistry;
import com.tibco.bx.bpelExtension.extensions.ExtensionsPackage;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataContextReferenceResolver;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.destinations.Destination;
import com.tibco.xpd.validation.explicit.Validator;


/** The activator class controls the plug-in life cycle. */
public class ConverterActivator extends AbstractUIPlugin {
	
    public static final String PLUGIN_ID = "com.tibco.bx.xpdl2bpel"; //$NON-NLS-1$
    
    protected static ConverterActivator m_plugin;

	private static Validator validator;

    private static BxExtensionRegistry bxExtensionRegistry;
    
    private static ProcessDataContextReferenceResolver resolver = new ProcessDataContextReferenceResolver();

    public ConverterActivator () {
        m_plugin = this;
    }

    
    /** @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext). */
    public void start (BundleContext context) throws Exception {
        super.start (context);
    }


    /** @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext). */
    public void stop (BundleContext context) throws Exception {
        m_plugin = null;
        super.stop (context);
    }

    
    /** Returns the shared instance
     * @return the shared instance. */
    public static ConverterActivator getDefault() {
        return m_plugin;
    }

	public static Validator getValidator() {
		if (validator == null) {
			Destination destination = ValidationActivator.getDefault().getDestination(N2PEConstants.N2PE_DESTINATION_ID);
			validator = new Validator(destination);
		}
		return validator;
	}

    public static BxExtensionRegistry getBxExtensionRegistry() {
    	if (bxExtensionRegistry == null) {
    		bxExtensionRegistry = new BxExtensionRegistry();
    	}
    	
		AdapterRegistry.INSTANCE.registerAdapterFactory(				
				ExtensionsPackage.eINSTANCE,
				BasicBPELAdapterFactory.INSTANCE
		);

    	return bxExtensionRegistry;
	}
    
    public static ProcessDataContextReferenceResolver getProcessDataContextReferenceResolver() {
    	return resolver;
    }

	public static IStatus createInfoStatus(String message) {
        return new Status (IStatus.INFO, ConverterActivator.PLUGIN_ID, IStatus.OK, message, null);
    }
    
    public static IStatus createWarningStatus(String message, Throwable exception) {
        return new Status (IStatus.WARNING, ConverterActivator.PLUGIN_ID, IStatus.OK, message, exception);
    }
    
    public static IStatus createErrorStatus(String message, Throwable exception) {
        return new Status (IStatus.ERROR, ConverterActivator.PLUGIN_ID, IStatus.OK, message, exception);
    }
    
    public static void logError(String message, Throwable exception) {
        getDefault().getLog().log(createErrorStatus(message, exception));
    }
    
    public static void log(IStatus status) {
        getDefault().getLog().log(status);
    }

}
