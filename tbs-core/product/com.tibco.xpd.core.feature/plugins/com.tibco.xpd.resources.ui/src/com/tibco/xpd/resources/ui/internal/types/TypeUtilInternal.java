/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.resources.ui.internal.types;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.picker.PickerService;
import com.tibco.xpd.resources.ui.types.ITypeFilterProvider;
import com.tibco.xpd.resources.ui.types.ITypeLabelProvider;
import com.tibco.xpd.resources.ui.types.ITypeResolver;
import com.tibco.xpd.resources.ui.types.TypeInfo;
import com.tibco.xpd.resources.ui.types.TypedItem;

/**
 * This class should not be used in applications. It is used internally by the
 * PickerDialog.
 * 
 * @author rassisi
 * @deprecated Use {@link PickerService} instead. See also
 *             <code>com.tibco.xpd.resources.ui.pickerContent</code> extension.
 */
@Deprecated
public class TypeUtilInternal {

    Hashtable<String, ITypeFilterProvider> filterRegistry =
            new Hashtable<String, ITypeFilterProvider>();

    Hashtable<String, ITypeResolver> resolverRegistry =
            new Hashtable<String, ITypeResolver>();

    Hashtable<String, ITypeLabelProvider> labelProviderRegistry =
            new Hashtable<String, ITypeLabelProvider>();

    Hashtable<Object, TypeInfo> resolverCache =
            new Hashtable<Object, TypeInfo>();

    Hashtable<String, TypeInfo> typesRegistry =
            new Hashtable<String, TypeInfo>();

    // Singleton instance of this class

    private static TypeUtilInternal INSTANCE = new TypeUtilInternal();

    /**
     * This class is Singleton and should not be instantiated.
     */
    TypeUtilInternal() {
    }

    /**
     * Get singleton instance of this class.
     * 
     * @return <code>TypeProviderUtil</code>
     */
    public static TypeUtilInternal getDefault() {
        return INSTANCE;
    }

    /**
     * This class is Singleton and should not be instantiated.
     */

    /**
     * Loads the provider list from the extension points.
     */
    public static void loadTypesProviders() {

        IExtensionPoint extensionPoint =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(XpdResourcesUIActivator.ID,
                                "typesProvider"); //$NON-NLS-1$
        IConfigurationElement[] configs =
                extensionPoint.getConfigurationElements();

        try {
            for (IConfigurationElement config : configs) {

                if ("typesProvider".equals(config.getName())) { //$NON-NLS-1$
                    String providerId = config.getAttribute("id"); //$NON-NLS-1$

                    IConfigurationElement[] providerElements =
                            config.getChildren();
                    for (IConfigurationElement providerElement : providerElements) {
                        IConfigurationElement[] elements =
                                providerElement.getChildren();
                        String name = providerElement.getName();
                        if (name.equals("types")) { //$NON-NLS-1$
                            for (IConfigurationElement configurationElement : elements) {
                                String typeId =
                                        configurationElement
                                                .getAttribute("typeId"); //$NON-NLS-1$
                                String typeGroupId =
                                        configurationElement
                                                .getAttribute("groupId"); //$NON-NLS-1$
                                String typeData =
                                        configurationElement
                                                .getAttribute("data"); //$NON-NLS-1$
                                addType(new TypeInfo(typeId, typeGroupId,
                                        typeData));
                            }
                        } else if (name.equals("filters")) { //$NON-NLS-1$
                            for (IConfigurationElement configurationElement : elements) {
                                String typeId =
                                        configurationElement
                                                .getAttribute("typeId"); //$NON-NLS-1$
                                try {
                                    Object o =
                                            configurationElement
                                                    .createExecutableExtension("class"); //$NON-NLS-1$
                                    if (o instanceof ITypeFilterProvider) {
                                        ((ITypeFilterProvider) o)
                                                .setProviderId(providerId);
                                        addFilter(typeId,
                                                (ITypeFilterProvider) o);
                                    }
                                } catch (CoreException e) {
                                    XpdResourcesUIActivator.getDefault()
                                            .getLogger().error(e);
                                }
                            }
                        } else if (name.equals("resolvers")) { //$NON-NLS-1$
                            for (IConfigurationElement configurationElement : elements) {
                                String typeId =
                                        configurationElement
                                                .getAttribute("typeId"); //$NON-NLS-1$
                                try {
                                    Object o =
                                            configurationElement
                                                    .createExecutableExtension("class"); //$NON-NLS-1$
                                    if (o instanceof ITypeResolver) {
                                        ((ITypeResolver) o)
                                                .setProviderId(providerId);
                                        addResolver(typeId, (ITypeResolver) o);
                                    }
                                } catch (CoreException e) {
                                    XpdResourcesUIActivator.getDefault()
                                            .getLogger().error(e);
                                }
                            }
                        } else if (name.equals("labelProviders")) { //$NON-NLS-1$
                            for (IConfigurationElement configurationElement : elements) {
                                String typeId =
                                        configurationElement
                                                .getAttribute("typeId"); //$NON-NLS-1$
                                try {
                                    Object o =
                                            configurationElement
                                                    .createExecutableExtension("class"); //$NON-NLS-1$
                                    if (o instanceof ITypeLabelProvider) {
                                        ((ITypeLabelProvider) o)
                                                .setProviderId(providerId);
                                        addLabelProvider(typeId,
                                                (ITypeLabelProvider) o);
                                    }
                                } catch (CoreException e) {
                                    XpdResourcesUIActivator.getDefault()
                                            .getLogger().error(e);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            XpdResourcesUIActivator.getDefault().getLogger().error(ex);
        }
    }

    /**
     * @param typeInfo
     */
    public static void addType(TypeInfo typeInfo) {
        TypeUtilInternal.getDefault().typesRegistry.put(typeInfo.getTypeId(),
                typeInfo);
    }

    /**
     * @param typeId
     * @param resolver
     */
    public static void addResolver(String typeId, ITypeResolver resolver) {
        TypeUtilInternal.getDefault().resolverRegistry.put(typeId, resolver);
    }

    /**
     * @param typeId
     * @param filter
     */
    public static void addFilter(String typeId, ITypeFilterProvider filter) {
        TypeUtilInternal.getDefault().filterRegistry.put(typeId, filter);
    }

    /**
     * @param typeId
     * @param labelProvider
     */
    public static void addLabelProvider(String typeId,
            ITypeLabelProvider labelProvider) {
        TypeUtilInternal.getDefault().labelProviderRegistry.put(typeId,
                labelProvider);
    }

    /**
     * @return
     */
    public static Set<TypeInfo> getRegisteredTypes() {
        return (Set<TypeInfo>) TypeUtilInternal.getDefault().typesRegistry
                .values();
    }

    public static Set<TypedItem> getContent(IResource[] queryResources,
            TypeInfo[] queryTypes, Object[] contentToExclude,
            IProgressMonitor monitor) {
        Set<TypedItem> result = new HashSet<TypedItem>();
        for (TypeInfo typeInfo : queryTypes) {
            ITypeFilterProvider filterProvider = getFilterProvider(typeInfo);
            if (filterProvider != null) {
                Set<TypedItem> temp =
                        filterProvider.getContent(monitor,
                                queryResources,
                                typeInfo,
                                contentToExclude);
                Iterator<TypedItem> it = temp.iterator();
                while (it.hasNext()) {
                    result.add(it.next());
                }
            }
        }
        return result;
    }

    /**
     * @param type
     * @return
     */
    public static int hashCode(TypeInfo selectEntry) {
        return getFilterProvider(selectEntry).hashCode();
    }

    /**
     * @param type
     * @param obj
     * @return
     */
    public static boolean equals(TypeInfo selectEntry, Object obj) {
        return getFilterProvider(selectEntry).equals(obj);
    }

    /**
     * @param selectEntry
     * @return
     */
    public static ITypeFilterProvider getFilterProvider(TypeInfo selectEntry) {
        if (selectEntry.getProviderId() != null) {
            return getDefault().filterRegistry.get(selectEntry.getProviderId());
        }
        return getDefault().filterRegistry.get(selectEntry.getTypeId());
    }

    /**
     * @param element
     * @return
     */
    public static TypeInfo getTypeOfElement(Object element) {

        // resolverRegistry

        if (element instanceof TypedItem) {
            TypeInfo typeInfo =
                    getDefault().typesRegistry.get(((TypedItem) element)
                            .getTypeId());
            return typeInfo;
        }

        Object result = getDefault().resolverCache.get(element);
        if (result instanceof TypedItem) {
            return (TypeInfo) result;
        }

        Enumeration<String> en = getDefault().resolverRegistry.keys();

        while (en.hasMoreElements()) {
            String id = en.nextElement();
            Object rr = getDefault().resolverRegistry.get(id);
            if (rr instanceof ITypeResolver) {
                ITypeResolver tr = (ITypeResolver) rr;
                TypeInfo item = tr.toType(element);
                if (item != null) {
                    getDefault().resolverCache.put(element, item);
                    return item;
                }
            }
        }
        return null;
    }

    /**
     * @param selectionEntry
     * @return
     */
    public static ITypeResolver getResolver(TypeInfo selectEntry) {
        if (selectEntry.getProviderId() != null) {
            return getDefault().resolverRegistry.get(selectEntry
                    .getProviderId());
        }
        return getDefault().resolverRegistry.get(selectEntry.getTypeId());
    }

    /**
     * @param selectionEntry
     * @return
     */
    public static ITypeLabelProvider getLabelProvider(TypeInfo selectEntry) {
        if (selectEntry.getProviderId() != null) {
            return getDefault().labelProviderRegistry.get(selectEntry
                    .getProviderId());
        }
        return getDefault().labelProviderRegistry.get(selectEntry.getTypeId());
    }

    /**
     * @param providerId
     * @return
     */
    public static Set<TypeInfo> getRegisteredTypes(String providerId) {
        return (Set<TypeInfo>) TypeUtilInternal.getDefault().typesRegistry
                .values();
    }

}
