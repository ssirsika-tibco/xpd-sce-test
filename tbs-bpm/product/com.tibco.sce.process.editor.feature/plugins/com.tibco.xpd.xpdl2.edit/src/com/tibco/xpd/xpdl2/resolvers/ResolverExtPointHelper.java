package com.tibco.xpd.xpdl2.resolvers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import com.tibco.xpd.resources.XpdResourcesPlugin;

public class ResolverExtPointHelper {

    private static final String PLUGIN_ID = "com.tibco.xpd.xpdl2.edit"; //$NON-NLS-1$

    private static final String FIELDRESOLVER_EXTENSION_POINT =
            "fieldOrParamReferenceResolver"; //$NON-NLS-1$

    private static final String EXT_RESOLVER_ELEMENT =
            "FieldOrParamReferenceResolver"; //$NON-NLS-1$

    private static final String EXT_RESOLVERCLASS_ATTR = "class"; //$NON-NLS-1$

    private List<IFieldResolverExtension> resolvers = null;

    /**
     * XPD-5427: New data resolver extension for resolvers that deal with data
     * references outside of the standard places like activity and transition).
     */
    private static final String EXT_DATA_RESOLVER_ELEMENT =
            "DataReferenceResolver"; //$NON-NLS-1$

    /**
     * List of data reference resolvers that resolve references not necessarily
     * in Activity/Transition
     */
    private List<IDataReferenceResolver> dataResolvers = null;

    public static ResolverExtPointHelper INSTANCE =
            new ResolverExtPointHelper();

    private ResolverExtPointHelper() {
    }

    /**
     * Returns instances of all the loaded field reolver extension points.
     * 
     * @return
     */
    public Collection<IFieldResolverExtension> getExtensions() {
        if (resolvers == null) {
            cacheResolvers();
        }
        return resolvers;
    }

    /**
     * @return the dataResolvers
     */
    public List<IDataReferenceResolver> getDataReferenceResolvers() {
        if (resolvers == null) {
            cacheResolvers();
        }
        return dataResolvers;
    }

    /**
     * Cache the new and old resolver contributions.
     */
    private void cacheResolvers() {
        resolvers = new ArrayList<IFieldResolverExtension>();
        dataResolvers = new ArrayList<IDataReferenceResolver>();

        IExtensionPoint point =
                Platform.getExtensionRegistry().getExtensionPoint(PLUGIN_ID,
                        FIELDRESOLVER_EXTENSION_POINT);

        if (point != null) {
            IExtension[] extensions = point.getExtensions();

            if (extensions != null) {
                for (IExtension ext : extensions) {
                    // For each extension get the configuration elements
                    IConfigurationElement[] elements =
                            ext.getConfigurationElements();

                    if (elements != null) {
                        for (IConfigurationElement elem : elements) {

                            if (EXT_RESOLVER_ELEMENT.equals(elem.getName())) {
                                cacheFieldOrParamResolver(elem);

                            } else if (EXT_DATA_RESOLVER_ELEMENT.equals(elem
                                    .getName())) {
                                cacheDataResolver(elem);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Add a FieldOrParamReferenceResolver contribution to cache
     * 
     * @param configElement
     */
    private void cacheFieldOrParamResolver(IConfigurationElement configElement) {
        try {
            IFieldResolverExtension resolver =
                    (IFieldResolverExtension) configElement
                            .createExecutableExtension(EXT_RESOLVERCLASS_ATTR);
            resolvers.add(resolver);

        } catch (Exception e) {
            // ignore duff exceptions
            XpdResourcesPlugin
                    .getDefault()
                    .getLogger()
                    .error("Error loading '" + EXT_RESOLVER_ELEMENT + "' ext point class from contributer: " + configElement.getContributor() + "  Class: " + configElement.getAttribute(EXT_RESOLVERCLASS_ATTR)); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        }
    }

    /**
     * Add a DataReferenceResolver contribution to cache
     * <p>
     * 
     * @param configElement
     */
    private void cacheDataResolver(IConfigurationElement configElement) {
        try {
            IDataReferenceResolver resolver =
                    (IDataReferenceResolver) configElement
                            .createExecutableExtension(EXT_RESOLVERCLASS_ATTR);

            dataResolvers.add(resolver);

        } catch (Exception e) {
            // ignore duff exceptions
            XpdResourcesPlugin
                    .getDefault()
                    .getLogger()
                    .error("Error loading '" + EXT_RESOLVER_ELEMENT + "' ext point class from contributer: " + configElement.getContributor() + "  Class: " + configElement.getAttribute(EXT_RESOLVERCLASS_ATTR)); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

        }
    }
}
