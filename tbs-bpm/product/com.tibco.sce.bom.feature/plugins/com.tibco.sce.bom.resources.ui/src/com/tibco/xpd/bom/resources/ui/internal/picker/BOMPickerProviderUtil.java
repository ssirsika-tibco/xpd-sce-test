/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.resources.ui.internal.picker;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PrimitiveType;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.ui.BOMImages;
import com.tibco.xpd.bom.resources.ui.internal.Messages;
import com.tibco.xpd.bom.resources.ui.picker.IBOMPickerProvider;
import com.tibco.xpd.bom.resources.ui.picker.IBOMPickerProxyItem;
import com.tibco.xpd.bom.resources.utils.ResourceItemType;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;

/**
 * Provider util class that provides the content for the BOM picker.
 * 
 * @author njpatel
 * 
 */
public final class BOMPickerProviderUtil implements IBOMPickerProvider {

    // URL of the base primitive types
    private static final String BASE_TYPES_URL = "pathmap://BOM_TYPES/BomPrimitiveTypes.library.uml"; //$NON-NLS-1$

    // Singleton instance of this class
    private final static BOMPickerProviderUtil INSTANCE = new BOMPickerProviderUtil();

    // Primitive type image URI
    private static final URI IMAGE_URI = URI.createPlatformPluginURI(
            Activator.PLUGIN_ID + "/" + BOMImages.PRIMITIVE_TYPE, true); //$NON-NLS-1$;

    /**
     * Private constructor. This class cannot be instantiated.
     */
    private BOMPickerProviderUtil() {

    }

    /**
     * Get singleton instance of this class.
     * 
     * @return <code>BOMPickerProviderUtil</code>
     */
    public static BOMPickerProviderUtil getInstance() {
        return INSTANCE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.bom.resources.ui.picker.IBOMPickerProvider#getContent(org.eclipse.core.runtime.IProgressMonitor,
     *      com.tibco.xpd.bom.resources.ui.picker.IBOMPickerProvider.BOMType)
     */
    public IBOMPickerProxyItem[] getContent(IProgressMonitor monitor,
            BOMType type) {
        Set<IBOMPickerProxyItem> items = new HashSet<IBOMPickerProxyItem>();

        switch (type) {
        case CLASS:
            items = getAllClasses(monitor);
            break;

        case PRIMITIVE_TYPE:
            items = getAllPrimitiveTypes(monitor);
            break;

        case BASE_PRIMITIVE:
            items = getAllBasePrimitives(monitor);
            break;
        }

        return items.toArray(new IBOMPickerProxyItem[items.size()]);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.bom.resources.ui.picker.IBOMPickerProvider#getItem(org.eclipse.emf.common.util.URI)
     */
    public IBOMPickerProxyItem getItem(String uri, String name) {
        IBOMPickerProxyItem item = null;

        if (uri != null && name != null) {
            IndexerItemImpl criteria = new IndexerItemImpl();
            criteria.setName(name);
            Collection<IndexerItem> result = XpdResourcesPlugin.getDefault()
                    .getIndexerService().query(
                            BOMResourcesPlugin.BOM_INDEXER_ID, criteria);

            if (result != null && !result.isEmpty()) {
                for (IndexerItem indexerItem : result) {
                    String resultUri = indexerItem.getURI();
                    if (resultUri.equals(uri)) {
                        item = new BOMPickerItem(indexerItem);
                        break;
                    }
                }
            }

            if (item == null) {
                // Maybe this is a base type
                Set<IBOMPickerProxyItem> basePrimitives = getAllBasePrimitives(new NullProgressMonitor());

                if (basePrimitives != null) {
                    for (IBOMPickerProxyItem primitive : basePrimitives) {
                        if (primitive.getQualifiedName().equals(name)
                                && primitive.getURI().toString().equals(uri)) {
                            item = primitive;
                            break;
                        }
                    }
                }

            }
        }

        return item;
    }

    /**
     * Get all classes from the database.
     * 
     * @param monitor
     *            progress monitor
     * @return set of <code>IBOMPickerProxyItem</code> objects. Empty set if
     *         none found.
     */
    private Set<IBOMPickerProxyItem> getAllClasses(IProgressMonitor monitor) {
        Set<IBOMPickerProxyItem> items = new HashSet<IBOMPickerProxyItem>();
        monitor.beginTask(
                Messages.BOMPickerProviderUtil_gettingClass_monitor_shortdesc,
                2);

        IndexerItemImpl criteria = new IndexerItemImpl();
        criteria.setType(ResourceItemType.CLASS
                .toString());
        Collection<IndexerItem> result = XpdResourcesPlugin.getDefault()
                .getIndexerService().query(BOMResourcesPlugin.BOM_INDEXER_ID,
                        criteria);
        monitor.worked(1);

        if (result != null) {
            for (IndexerItem indexerItem : result) {
                items.add(new BOMPickerItem(indexerItem));
            }
        }
        monitor.worked(1);
        return items;
    }

    /**
     * Get all primitive types from the database.
     * 
     * @param monitor
     *            progress monitor.
     * @return set of <code>IBOMPickerProxyItem</code> objects. Empty set if
     *         none found.
     */
    private Set<IBOMPickerProxyItem> getAllPrimitiveTypes(
            IProgressMonitor monitor) {
        Set<IBOMPickerProxyItem> items = new HashSet<IBOMPickerProxyItem>();

        monitor
                .beginTask(
                        Messages.BOMPickerProviderUtil_gettingPrimType_monitor_shortdesc,
                        2);

        IndexerItemImpl criteria = new IndexerItemImpl();
        criteria.setType(ResourceItemType.PRIMITIVE_TYPE
                .toString());
        Collection<IndexerItem> result = XpdResourcesPlugin.getDefault()
                .getIndexerService().query(BOMResourcesPlugin.BOM_INDEXER_ID,
                        criteria);

        monitor.worked(1);

        if (result != null) {
            for (IndexerItem indexerItem : result) {
                items.add(new BOMPickerItem(indexerItem));
            }
        }

        monitor.worked(1);

        return items;
    }

    /**
     * Get all base primitive types.
     * 
     * @param monitor
     *            progress monitor.
     * @return set of <code>IBOMPickerProxyItem</code> objects. Empty set if
     *         none found.
     */
    private Set<IBOMPickerProxyItem> getAllBasePrimitives(
            IProgressMonitor monitor) {

        Set<IBOMPickerProxyItem> items = new HashSet<IBOMPickerProxyItem>();
        EditingDomain ed = XpdResourcesPlugin.getDefault().getEditingDomain();

        if (ed != null) {
            monitor
                    .beginTask(
                            Messages.BOMPickerProviderUtil_gettingBasePrimType_monitor_shortdesc,
                            1);

            Resource resource = ed.loadResource(BASE_TYPES_URL);

            if (resource != null) {
                EList<EObject> contents = resource.getContents();

                for (EObject content : contents) {
                    if (content instanceof Model) {
                        TreeIterator<Object> iterator = EcoreUtil
                                .getAllProperContents(content, false);

                        while (iterator.hasNext()) {
                            Object next = iterator.next();

                            if (next instanceof PrimitiveType) {
                                items
                                        .add(getPickerItemFor((PrimitiveType) next));
                            }
                        }
                    }
                }
            }

            monitor.worked(1);
        }
        return items;
    }

    /**
     * Convert the given <code>PrimitiveType</code> to the picker item type.
     * 
     * @param type
     *            <code>PrimitiveType</code>
     * @return <code>BOMPickerItem</code>
     */
    private BOMPickerItem getPickerItemFor(PrimitiveType type) {
        String qualifiedName = type.getQualifiedName();
        if (qualifiedName == null) {
            qualifiedName = type.getName();
        }

        // Convert UML delimiter to JAVA style delimiter
        qualifiedName = BOMWorkingCopy.getQualifiedName(qualifiedName);

        return new BOMPickerItem(qualifiedName, EcoreUtil.getURI(type),
                IMAGE_URI);
    }

    /**
     * Implementation of the <code>IBOMPickerProxyItem</code>
     * 
     * @author njpatel
     * 
     */
    private class BOMPickerItem implements IBOMPickerProxyItem {

        private String qualifiedName;
        private String name;
        private URI uri;
        private URI imageUri;

        /**
         * Constructor.
         * 
         * @param qualifiedName
         *            qualified name.
         * @param uri
         *            URI of the item.
         * @param imageUri
         *            URI of the image.
         */
        public BOMPickerItem(String qualifiedName, URI uri, URI imageUri) {

            this.qualifiedName = qualifiedName;
            this.uri = uri;
            this.imageUri = imageUri;
        }

        /**
         * Constructor.
         * 
         * @param dbItem
         *            databae item.
         */
        public BOMPickerItem(IndexerItem item) {
            Assert.isNotNull(item, "item"); //$NON-NLS-1$
            this.qualifiedName = item.getName();
            String uriString = item.getURI();
            this.uri = URI.createURI(uriString);
            String imgURIStr = item
                    .get(BOMResourcesPlugin.INDEXER_ATTRIBUTE_IMAGE_URI);

            if (imgURIStr != null) {
                imageUri = URI.createURI(imgURIStr);
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see com.tibco.xpd.bom.resources.ui.picker.IBOMPickerProxyItem#getImage()
         */
        public Image getImage() {
            Image img = null;

            if (imageUri != null) {
                img = ExtendedImageRegistry.getInstance().getImage(imageUri);
            }

            return img;
        }

        /*
         * (non-Javadoc)
         * 
         * @see com.tibco.xpd.bom.resources.ui.picker.IBOMPickerProxyItem#getName()
         */
        public String getName() {

            if (name == null) {
                int idx = qualifiedName
                        .lastIndexOf(BOMWorkingCopy.JAVA_PACKAGE_SEPARATOR);

                if (idx >= 0) {
                    name = qualifiedName.substring(idx + 1);
                } else {
                    name = qualifiedName;
                }
            }

            return name;
        }

        /*
         * (non-Javadoc)
         * 
         * @see com.tibco.xpd.bom.resources.ui.picker.IBOMPickerProxyItem#getQualifiedName()
         */
        public String getQualifiedName() {
            return qualifiedName;
        }

        /*
         * (non-Javadoc)
         * 
         * @see com.tibco.xpd.bom.resources.ui.picker.IBOMPickerProxyItem#getURI()
         */
        public URI getURI() {
            return uri;
        }

        @Override
        public int hashCode() {
            int code = super.hashCode();

            if (uri != null && qualifiedName != null) {
                code = uri.hashCode() + qualifiedName.hashCode();
            }

            return code;
        }

        @Override
        public boolean equals(Object obj) {
            boolean isEquals = false;

            if (obj == this) {
                isEquals = true;
            } else if (obj instanceof IBOMPickerProxyItem) {
                IBOMPickerProxyItem other = (IBOMPickerProxyItem) obj;
                isEquals = other.getQualifiedName().equals(getQualifiedName())
                        && other.getURI().equals(getURI());
            }

            return isEquals;
        }
    }
}
