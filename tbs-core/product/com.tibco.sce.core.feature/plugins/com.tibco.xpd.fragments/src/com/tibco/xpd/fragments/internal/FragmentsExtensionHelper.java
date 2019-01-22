/*
 * Copyright (c) TIBCO Software Inc. 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.fragments.internal;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.ui.IEditorPart;

import com.tibco.xpd.fragments.FragmentsActivator;
import com.tibco.xpd.fragments.FragmentsContributor;
import com.tibco.xpd.fragments.FragmentsImporter;
import com.tibco.xpd.fragments.ProviderBinding;
import com.tibco.xpd.fragments.dnd.FragmentDropTargetAdapter;

/**
 * Helper class that manages all the extensions of the following extension
 * points:
 * <ul>
 * <li><code>com.tibco.xpd.fragments.fragmentsProvider</code></li>
 * <li><code>com.tibco.xpd.fragments.fragmentsBinding</code></li>
 * <li><code>com.tibco.xpd.fragments.fragmentsImport</code></li>
 * </ul>
 * 
 * @author njpatel
 * 
 */
public final class FragmentsExtensionHelper {

    private static final String FRAGMENT_PROVIDER_ID = "fragmentsProvider"; //$NON-NLS-1$

    private static final String FRAGMENT_BINDING_ID = "fragmentsBinding"; //$NON-NLS-1$

    private static final String FRAGMENT_IMPORT_ID = "fragmentsImport"; //$NON-NLS-1$

    /* Binding extension attributes */
    private static final String ATTR_PROVIDER_ID = "fragmentId"; //$NON-NLS-1$

    private static final String ATTR_EDITOR_ID = "editorId"; //$NON-NLS-1$

    private static final String ELEM_FILTER = "filter"; //$NON-NLS-1$

    private static final String ATTR_FILTER = "filter"; //$NON-NLS-1$

    /** Fragment provider id to provider mapping */
    private final Map<String, FragmentsProvider> providers;

    /** Editor id to provider mappings */
    private final Map<String, Set<ProviderBinding>> bindings;

    /** Fragment providers */
    private final Set<FragmentsImportExtension> importers;

    private FragmentsProvider[] allBoundProviders;

    private final Map<IEditorPart, FragmentsProvider[]> providersMap;

    /**
     * Private constructor.
     */
    private FragmentsExtensionHelper() {
        providers = loadProviders();
        bindings = loadBindings(providers.keySet());
        importers = loadImporters();

        // Only cache 10 elements in the map
        providersMap = new FiniteMap<IEditorPart, FragmentsProvider[]>(10);
    }

    /**
     * Create an instance of this helper.
     * 
     * @return <code>FragmentsExtensionHelper</code>
     */
    public static FragmentsExtensionHelper getInstance() {
        return new FragmentsExtensionHelper();
    }

    /**
     * Get the fragments provider with the given id.
     * 
     * @param id
     *            id of the fragments provider
     * @return fragments provider or <code>null</code> if one is not found with
     *         the given id.
     */
    public FragmentsProvider getProvider(String id) {
        return providers.get(id);
    }

    /**
     * Get all registered {@link FragmentsImportExtension} providers.
     * 
     * @return array array of <code>FragmentsImport</code> providers, empty if
     *         none registered
     */
    public FragmentsImportExtension[] getAllImporters() {
        return importers
                .toArray(new FragmentsImportExtension[importers.size()]);
    }

    /**
     * Get all fragment providers registered with the fragmentsProvider
     * extension.
     * 
     * @return array of fragments providers, empty array if none registered.
     */
    public FragmentsProvider[] getAllProviders() {
        return providers.values().toArray(new FragmentsProvider[providers
                .values().size()]);
    }

    /**
     * Get fragment providers bound to the given editor part. If the editor part
     * is <code>null</code> then all bound providers will be returned.
     * 
     * @param editorPart
     *            {@link IEditorPart} or <code>null</code> if all boundload
     *            providers are required.
     * @return bound providers, empty array if no providers are bound.
     */
    public FragmentsProvider[] getBoundProviders(IEditorPart editorPart) {
        FragmentsProvider[] boundProviders = null;
        if (editorPart != null && providersMap.containsKey(editorPart)) {
            boundProviders = providersMap.get(editorPart);
        } else if (editorPart != null) {
            String id = editorPart.getEditorSite().getId();
            Collection<ProviderBinding> providersBindings =
                    getProviderBindings(id);
            Set<FragmentsProvider> fragmentProviders =
                    new HashSet<FragmentsProvider>();

            if (providersBindings != null && !providersBindings.isEmpty()) {
                for (ProviderBinding binding : providersBindings) {
                    String providerId = null;
                    if (binding.getFilters() == null
                            || binding.getFilters().isEmpty()) {
                        // No filter set in this binding so include this
                        // provider
                        providerId = binding.getProviderId();
                    } else {
                        // Check the filters
                        for (IFilter filter : binding.getFilters()) {
                            if (filter.select(editorPart)) {
                                providerId = binding.getProviderId();
                                break;
                            }
                        }
                    }

                    if (providerId != null) {
                        FragmentsProvider provider = providers.get(providerId);

                        if (provider != null) {
                            fragmentProviders.add(provider);
                        }
                    }
                }

                boundProviders =
                        fragmentProviders
                                .toArray(new FragmentsProvider[fragmentProviders
                                        .size()]);
                providersMap.put(editorPart, boundProviders);
            }

        } else {
            if (allBoundProviders == null) {
                Set<FragmentsProvider> providersSet =
                        new HashSet<FragmentsProvider>();
                // Get all bounds providers
                Collection<Set<ProviderBinding>> providerBindingsSet =
                        bindings.values();

                for (Set<ProviderBinding> bindings : providerBindingsSet) {
                    for (ProviderBinding binding : bindings) {
                        String id = binding.getProviderId();

                        if (id != null) {
                            FragmentsProvider provider = providers.get(id);
                            if (provider != null) {
                                providersSet.add(provider);
                            }
                        }
                    }
                }
                allBoundProviders =
                        providersSet.toArray(new FragmentsProvider[providersSet
                                .size()]);
            }
            boundProviders = allBoundProviders;
        }

        return boundProviders;
    }

    /**
     * Load all fragment providers from the extension point.
     */
    private Map<String, FragmentsProvider> loadProviders() {
        IExtensionPoint point =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(FragmentsActivator.PLUGIN_ID,
                                FRAGMENT_PROVIDER_ID);
        Map<String, FragmentsProvider> fragmentProviders =
                new HashMap<String, FragmentsProvider>();
        // Get all providers
        if (point != null) {
            IConfigurationElement[] elements = point.getConfigurationElements();

            for (IConfigurationElement element : elements) {
                FragmentsProvider provider = new FragmentsProvider(element);

                if (!fragmentProviders.containsKey(provider.getId())) {
                    fragmentProviders.put(provider.getId(), provider);
                } else {
                    throw new IllegalArgumentException(
                            String
                                    .format("Fragment provider with id '%s' has already been defined.", //$NON-NLS-1$
                                            provider.getId()));
                }
            }
        }
        return fragmentProviders;
    }

    /**
     * Load all editor id to provider bindings from the extension point.
     * 
     * @param providerIds
     *            collection of registered provider ids.
     */
    private Map<String, Set<ProviderBinding>> loadBindings(
            Collection<String> providerIds) {
        IExtensionPoint point =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(FragmentsActivator.PLUGIN_ID,
                                FRAGMENT_BINDING_ID);

        Map<String, Set<ProviderBinding>> fragmentBindings =
                new HashMap<String, Set<ProviderBinding>>();

        if (point != null && providerIds != null && !providerIds.isEmpty()) {
            IConfigurationElement[] elements = point.getConfigurationElements();

            for (IConfigurationElement element : elements) {
                String providerId = element.getAttribute(ATTR_PROVIDER_ID);

                // Add the binding if the provider id is valid
                if (providerIds.contains(providerId)) {
                    String editorId = element.getAttribute(ATTR_EDITOR_ID);
                    Set<IFilter> filters = null;
                    try {
                        filters = getFilters(element);
                    } catch (CoreException e) {
                        FragmentsActivator.getDefault().getLogger().error(e);
                    }

                    if (editorId != null && providerId != null) {
                        Set<ProviderBinding> providerBindings =
                                fragmentBindings.get(editorId);

                        if (providerBindings == null) {
                            providerBindings = new HashSet<ProviderBinding>();
                            fragmentBindings.put(editorId, providerBindings);
                        }

                        providerBindings.add(new ProviderBinding(providerId,
                                filters));
                    }
                } else {
                    throw new IllegalArgumentException(
                            String
                                    .format("Fragment bindings: no fragment provider with id '%s' found..", //$NON-NLS-1$
                                            providerId));
                }
            }
        }
        return fragmentBindings;
    }

    /**
     * Get the binding filters (if any) in the given
     * <code>IConfigurationElement</code>.
     * 
     * @param element
     *            extension
     * @return set of {@link IFilter filters} if specified, empty set otherwise.
     * @throws CoreException
     */
    private Set<IFilter> getFilters(IConfigurationElement element)
            throws CoreException {
        Set<IFilter> filters = new HashSet<IFilter>();

        if (element != null) {
            IConfigurationElement[] children = element.getChildren(ELEM_FILTER);

            for (IConfigurationElement child : children) {
                IFilter filter =
                        (IFilter) child.createExecutableExtension(ATTR_FILTER);

                if (filter != null) {
                    filters.add(filter);
                }
            }
        }

        return !filters.isEmpty() ? filters : null;
    }

    /**
     * Load all fragment import providers.
     */
    private Set<FragmentsImportExtension> loadImporters() {
        IExtensionPoint point =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(FragmentsActivator.PLUGIN_ID,
                                FRAGMENT_IMPORT_ID);

        Set<FragmentsImportExtension> fragmentImporters =
                new HashSet<FragmentsImportExtension>();

        if (point != null) {
            IConfigurationElement[] elements = point.getConfigurationElements();

            for (IConfigurationElement element : elements) {
                // Check that the extension has atleast the projects specified
                if (!(element
                        .getAttribute(FragmentsImportExtension.ATTR_NATURES) == null && element
                        .getAttribute(FragmentsImportExtension.ATTR_PROJECTS) == null)) {
                    fragmentImporters
                            .add(new FragmentsImportExtension(element));
                } else {
                    FragmentsActivator
                            .getDefault()
                            .getLogger()
                            .error(String
                                    .format(Messages.FragmentsExtensionHelper_FragmentImportHasNoProjectNameOrNature_error_message,
                                            element
                                                    .getAttribute(FragmentsImportExtension.ATTR_ID)));
                }
            }
        }

        return fragmentImporters;
    }

    /**
     * Fragments importer extension.
     * 
     * @author njpatel
     * 
     */
    public class FragmentsImportExtension {
        /* Import extension attributes */
        private static final String ATTR_ID = "id"; //$NON-NLS-1$

        private static final String ATTR_NATURES = "projectNatures"; //$NON-NLS-1$

        private static final String ATTR_PROJECTS = "projects"; //$NON-NLS-1$

        private static final String ATTR_CLASS = "class"; //$NON-NLS-1$

        private final IConfigurationElement element;

        private String id;

        private String[] projectNatures;

        private String[] projectNames;

        private FragmentsImporter importer;

        /**
         * Fragments importer extension.
         * 
         * @param element
         */
        protected FragmentsImportExtension(IConfigurationElement element) {
            this.element = element;
        }

        /**
         * Get the importer id.
         * 
         * @return extension id
         */
        public String getId() {
            if (id == null) {
                id = element.getAttribute(ATTR_ID);
            }
            return id;
        }

        /**
         * Get the fragments importer class.
         * 
         * @return {@link IFragmentsImporter}
         * @throws CoreException
         */
        public FragmentsImporter getImporter() throws CoreException {
            if (importer == null && element != null) {
                importer =
                        (FragmentsImporter) element
                                .createExecutableExtension(ATTR_CLASS);
            }
            return importer;
        }

        /**
         * Get the list of project natures this importer recognises.
         * 
         * @return array of natures ids, <code>null</code> if no natures are
         *         specified.
         */
        public String[] getProjectNatures() {
            if (projectNatures == null) {
                String natures = element.getAttribute(ATTR_NATURES);

                if (natures != null) {
                    // Comma-separated list of project names
                    projectNatures = natures.split("\\s*,\\s*"); //$NON-NLS-1$
                } else {
                    projectNatures = new String[0];
                }
            }
            return projectNatures;
        }

        /**
         * Get the list of project names this importer recognises.
         * 
         * @return array of project names, <code>null</code> if no names are
         *         specified.
         */
        public String[] getProjectNames() {
            if (projectNames == null) {
                String names = element.getAttribute(ATTR_PROJECTS);

                if (names != null) {
                    // Comma-separated list of project names
                    projectNames = names.split("\\s*,\\s*"); //$NON-NLS-1$
                } else {
                    projectNames = new String[0];
                }
            }
            return projectNames;
        }
    }

    /**
     * Representation of the fragments provider extension.
     * 
     * @author njpatel
     * 
     */
    public class FragmentsProvider {

        private static final String ATTR_ID = "id"; //$NON-NLS-1$

        private static final String ATTR_NAME = "name"; //$NON-NLS-1$

        private static final String ATT_DESC = "description"; //$NON-NLS-1$

        private static final String ATTR_CLASS = "class"; //$NON-NLS-1$

        /* Drop adapters */
        private static final String ELEM_DROPADAPTERS = "dropAdapters"; //$NON-NLS-1$

        private String id;

        private String name;

        private FragmentsContributor providerClass;

        private final IConfigurationElement element;

        private FragmentDropTargetAdapter[] dropAdapters;

        /**
         * Fragments provider extension.
         * 
         * @param element
         */
        protected FragmentsProvider(IConfigurationElement element) {
            this.element = element;
        }

        /**
         * Provider id.
         * 
         * @return id
         */
        public String getId() {
            if (id == null) {
                id = element.getAttribute(ATTR_ID);
            }
            return id;
        }

        /**
         * Provider name.
         * 
         * @return name
         */
        public String getName() {
            if (name == null) {
                name = element.getAttribute(ATTR_NAME);
            }
            return name;
        }

        /**
         * Provider short description.
         * 
         * @return description or <code>null</code> if no description is
         *         provided.
         */
        public String getDescription() {
            // Optional attribute
            return element.getAttribute(ATT_DESC);
        }

        /**
         * Get the provider class.
         * 
         * @return {@link FragmentsContributor}.
         * @throws CoreException
         */
        public FragmentsContributor getProviderClass() throws CoreException {
            if (providerClass == null) {
                providerClass =
                        (FragmentsContributor) element
                                .createExecutableExtension(ATTR_CLASS);
            }
            return providerClass;
        }

        /**
         * Get any registered <code>FragmentDropTargetAdapter</code>s for this
         * provider.
         * 
         * @return array of <code>FragmentDropTargetAdapter</code>s if
         *         registered, or empty array if no drop adapters are
         *         registered.
         * @throws CoreException
         */
        public FragmentDropTargetAdapter[] getDropAdapters()
                throws CoreException {

            if (dropAdapters == null) {
                // Load the drop target adapters if any
                Set<FragmentDropTargetAdapter> adapters =
                        new HashSet<FragmentDropTargetAdapter>();

                IConfigurationElement[] children =
                        element.getChildren(ELEM_DROPADAPTERS);

                if (children != null) {
                    for (IConfigurationElement child : children) {
                        Object obj =
                                child.createExecutableExtension(ATTR_CLASS);

                        if (obj instanceof FragmentDropTargetAdapter) {
                            FragmentDropTargetAdapter adapter =
                                    (FragmentDropTargetAdapter) obj;
                            adapter.setProvider(this);
                            adapters.add(adapter);
                        }
                    }
                }

                dropAdapters =
                        adapters.toArray(new FragmentDropTargetAdapter[adapters
                                .size()]);
            }
            return dropAdapters;
        }

        @Override
        public String toString() {
            return getId();
        }
    }

    /**
     * An extension of the <code>HashMap</code> that has a finite number of
     * elements. Once this max number is reached in the map the item with the
     * longest last-access time will be removed to maintain the max number of
     * elements.
     * 
     * @author njpatel
     * 
     * @param <K>
     *            key
     * @param <V>
     *            value
     */
    private class FiniteMap<K, V> extends HashMap<K, V> {
        /**
         * Generated version UID
         */
        private static final long serialVersionUID = 8654027008776463566L;

        private int maxCount = 5;

        /**
         * Stores the last access time of each key in this map.
         */
        private final Map<Object, Long> accessTime;

        public FiniteMap() {
            accessTime = new HashMap<Object, Long>();
        }

        public FiniteMap(int maxCount) {
            this();
            if (maxCount > 0) {
                this.maxCount = maxCount;
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.util.HashMap#put(java.lang.Object, java.lang.Object)
         */
        public V put(K key, V value) {
            if (size() > maxCount) {
                // Remove an entry that has not been access for some time
                Object leastAccessedKey = getLeastAccessedKey();

                if (leastAccessedKey != null) {
                    remove(leastAccessedKey);
                }
            }
            accessTime.put(key, new Long(System.currentTimeMillis()));
            return super.put(key, value);
        }

        @Override
        public V get(Object key) {
            V value = super.get(key);

            if (value != null) {
                accessTime.put(key, new Long(System.currentTimeMillis()));
            }

            return value;
        }

        /**
         * Get the key that was has the longest last-access time.
         * 
         * @return key
         */
        private Object getLeastAccessedKey() {
            long time = 0;
            Object key = null;
            for (Entry<Object, Long> entry : accessTime.entrySet()) {
                if (time == 0) {
                    time = entry.getValue();
                    key = entry.getKey();
                } else {
                    if (time > entry.getValue()) {
                        time = entry.getValue();
                        key = entry.getKey();
                    }
                }
            }
            return key;
        }

    }

    /**
     * 
     * Get the provider bindings associated with the given editor id.
     * 
     * @param editorId
     * 
     * @return provider bindings, or <code>null</code> if not found.
     * 
     * @since 3.3
     */

    public Collection<ProviderBinding> getProviderBindings(String editorId) {
        if (editorId != null) {
            return bindings.get(editorId);
        }

        return null;
    }

}
