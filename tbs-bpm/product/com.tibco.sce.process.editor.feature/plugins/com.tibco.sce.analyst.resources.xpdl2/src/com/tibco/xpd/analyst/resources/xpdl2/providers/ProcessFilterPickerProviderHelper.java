/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.providers;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.IProcessPickerProxyItem;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.ProcessResourceItemType;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.internal.indexer.IndexerServiceImpl;

/**
 * Provider helper class that provides the content for the Process picker.
 * 
 * @author Miguel
 * 
 */
public final class ProcessFilterPickerProviderHelper implements
        IProcessPickerProvider {

    // Singleton instance of this class
    private final static ProcessFilterPickerProviderHelper INSTANCE =
            new ProcessFilterPickerProviderHelper();

    /**
     * Private constructor. This class cannot be instantiated.
     */
    private ProcessFilterPickerProviderHelper() {

    }

    /**
     * Get singleton instance of this class.
     * 
     * @return <code>BOMPickerProviderUtil</code>
     */
    public static ProcessFilterPickerProviderHelper getInstance() {
        return INSTANCE;
    }

    /*
     * (non-Javadoc)
     */
    @Override
    public IProcessPickerProxyItem[] getContent(IProgressMonitor monitor,
            ProcessType type) {
        Set<IProcessPickerProxyItem> items =
                new HashSet<IProcessPickerProxyItem>();

        switch (type) {
        case PROCESS:
            items = getAllProcesses(monitor);
            break;

        case PROCESSINTERFACE:
            items = getAllProcessInterfaces(monitor);
            break;

        case SERVICEPROCESSINTERFACE:
            items = getAllServiceProcessInterfaces(monitor);
            break;

        case SERVICEPROCESS:
            items = getAllServiceProcesses(monitor);
            break;

        case PROCESS_PROCESSINTERFACE:
            items = getAllProcessNProcessInterfaces(monitor);
            break;

        case PAGEFLOW:
            items = getAllPageflows(monitor);
            break;

        case PAGEFLOW_PROCESSINTERFACE:
            items = getAllPageflowsNProcessInterfaces(monitor);
            break;
        }

        return items.toArray(new IProcessPickerProxyItem[items.size()]);
    }

    /**
     * Get all service processes from the Process Indexer
     * 
     * @param monitor
     * @return set of <code>IProcessPickerProxyItem</code> objects. Empty set if
     *         none found.
     */
    private Set<IProcessPickerProxyItem> getAllServiceProcesses(
            IProgressMonitor monitor) {

        monitor.beginTask(Messages.ProcessFilterPickerProviderHelper_MonitorProcessNames_shortdesc,
                2);
        Set<IProcessPickerProxyItem> items =
                getAllItems(monitor,
                        ProcessResourceItemType.SERVICEPROCESS.toString());
        return items;
    }

    /*
     * (non-Javadoc)
     */
    @Override
    public IProcessPickerProxyItem getItem(String uri, String name) {
        IProcessPickerProxyItem item = null;

        if (uri != null && name != null) {

            IndexerItem criteria = new IndexerItemImpl(name, null, uri, null);
            Collection<IndexerItem> result =
                    XpdResourcesPlugin
                            .getDefault()
                            .getIndexerService()
                            .query(Xpdl2ResourcesPlugin.PROCESS_INDEXER_ID,
                                    criteria);

            if (result != null && !result.isEmpty()) {
                for (IndexerItem indexerItem : result) {
                    item = new ProcessPickerItem(indexerItem);
                    break;
                }
            }

        }

        return item;
    }

    /**
     * Get all processes from the database.
     * 
     * @param monitor
     *            progress monitor
     * @return set of <code>IProcessPickerProxyItem</code> objects. Empty set if
     *         none found.
     */
    private Set<IProcessPickerProxyItem> getAllProcesses(
            IProgressMonitor monitor) {
        monitor.beginTask(Messages.ProcessFilterPickerProviderHelper_MonitorProcessNames_shortdesc,
                2);

        Set<IProcessPickerProxyItem> items =
                getAllItems(monitor, ProcessResourceItemType.PROCESS.toString());
        return items;
    }

    /**
     * Get all pageflows from the database.
     * 
     * @param monitor
     *            progress monitor
     * @return set of <code>IProcessPickerProxyItem</code> objects. Empty set if
     *         none found.
     */
    private Set<IProcessPickerProxyItem> getAllPageflows(
            IProgressMonitor monitor) {
        monitor.beginTask(Messages.ProcessFilterPickerProviderHelper_ProcessFilterPickerProviderHelper_MonitorPageflowNames_shortdesc,
                2);

        Set<IProcessPickerProxyItem> items =
                getAllItems(monitor,
                        ProcessResourceItemType.PAGEFLOW.toString());
        return items;
    }

    /**
     * Get all pageflows and process interfaces from the database.
     * 
     * @param monitor
     *            progress monitor
     * @return set of <code>IProcessPickerProxyItem</code> objects. Empty set if
     *         none found.
     */
    private Set<IProcessPickerProxyItem> getAllPageflowsNProcessInterfaces(
            IProgressMonitor monitor) {
        monitor.beginTask(Messages.ProcessFilterPickerProviderHelper_ProcessFilterPickerProviderHelper_MonitorPageflowNames_shortdesc,
                2);

        Set<IProcessPickerProxyItem> items =
                getAllItems(monitor,
                        ProcessResourceItemType.PAGEFLOW.toString());
        if (items == null) {
            items = new HashSet<IProcessPickerProxyItem>();
        }

        items.addAll(getAllItems(monitor,
                ProcessResourceItemType.PROCESSINTERFACE.toString()));

        return items;
    }

    /**
     * Get all service process interfaces
     * 
     * @param monitor
     * @return set of <code>IProcessPickerProxyItem</code> objects. Empty set if
     *         none found.
     */
    private Set<IProcessPickerProxyItem> getAllServiceProcessInterfaces(
            IProgressMonitor monitor) {

        monitor.beginTask(Messages.ProcessFilterPickerProviderHelper_MonitorServiceProcessIfcNames_shortdesc,
                2);

        Set<IProcessPickerProxyItem> items =
                getAllItems(monitor,
                        ProcessResourceItemType.SERVICEPROCESSINTERFACE
                                .toString());
        return items;
    }

    /**
     * Get all process interfaces from the database.
     * 
     * @param monitor
     *            progress monitor
     * @return set of <code>IProcessPickerProxyItem</code> objects. Empty set if
     *         none found.
     */
    private Set<IProcessPickerProxyItem> getAllProcessInterfaces(
            IProgressMonitor monitor) {
        monitor.beginTask(Messages.ProcessFilterPickerProviderHelper_MonitorProcessIfcNames_shortdesc,
                2);

        Set<IProcessPickerProxyItem> items =
                getAllItems(monitor,
                        ProcessResourceItemType.PROCESSINTERFACE.toString());
        return items;
    }

    /**
     * Get all processes and process interfaces from the database.
     * 
     * @param monitor
     *            progress monitor
     * @return set of <code>IProcessPickerProxyItem</code> objects. Empty set if
     *         none found.
     */
    private Set<IProcessPickerProxyItem> getAllProcessNProcessInterfaces(
            IProgressMonitor monitor) {
        monitor.beginTask(Messages.ProcessFilterPickerProviderHelper_MonitorIfcAndProcessNames_shortdesc,
                2);

        Set<IProcessPickerProxyItem> items =
                getAllItems(monitor, ProcessResourceItemType.PROCESS.toString());
        if (items == null) {
            items = new HashSet<IProcessPickerProxyItem>();
        }
        items.addAll(getAllItems(monitor,
                ProcessResourceItemType.PROCESSINTERFACE.toString()));
        return items;
    }

    /**
     * Get all items from the database for the given resourceItemType.
     * 
     * @param monitor
     *            progress monitor
     * @param resourceItemType
     * 
     * @return set of <code>IProcessPickerProxyItem</code> objects. Empty set if
     *         none found.
     */
    private Set<IProcessPickerProxyItem> getAllItems(IProgressMonitor monitor,
            String resourceItemType) {

        Set<IProcessPickerProxyItem> items =
                new HashSet<IProcessPickerProxyItem>();

        IndexerItem criteria =
                new IndexerItemImpl(null, resourceItemType, null, null);
        Collection<IndexerItem> result =
                XpdResourcesPlugin
                        .getDefault()
                        .getIndexerService()
                        .query(Xpdl2ResourcesPlugin.PROCESS_INDEXER_ID,
                                criteria);

        monitor.worked(1);

        if (result != null) {

            for (IndexerItem indexerItem : result) {

                items.add(new ProcessPickerItem(indexerItem));
            }
        }
        monitor.worked(1);
        return items;
    }

    /**
     * Implementation of the <code>IProcessPickerProxyItem</code>
     * 
     * @author Miguel Torres
     * 
     */
    private class ProcessPickerItem implements IProcessPickerProxyItem {

        private String qualifiedName;

        private String name;

        private URI uri;

        private URI imageUri;

        private String displayName;

        private String projectName;

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
        public ProcessPickerItem(String qualifiedName, URI uri, URI imageUri) {

            this.qualifiedName = qualifiedName;
            this.uri = uri;
            this.imageUri = imageUri;
        }

        /**
         * Constructor.
         * 
         * @param dbItem
         *            database item.
         */
        public ProcessPickerItem(IndexerItem item) {
            Assert.isNotNull(item, "item"); //$NON-NLS-1$
            this.qualifiedName = item.getName();
            String uriString = item.getURI();
            this.uri = URI.createURI(uriString);
            this.displayName =
                    item.get(Xpdl2ResourcesPlugin.ATTRIBUTE_DISPLAY_NAME);
            String imgURIStr =
                    item.get(Xpdl2ResourcesPlugin.ATTRIBUTE_IMAGE_URI);

            if (imgURIStr != null) {
                imageUri = URI.createURI(imgURIStr);
            }
            projectName = item.get(IndexerServiceImpl.ATTRIBUTE_PROJECT);
        }

        /*
          */
        @Override
        public Image getImage() {
            Image img = null;

            if (imageUri != null) {
                img = ExtendedImageRegistry.getInstance().getImage(imageUri);
            }

            return img;
        }

        /*
         * (non-Javadoc)
         */
        @Override
        public String getName() {

            if (name == null) {
                int idx =
                        qualifiedName
                                .lastIndexOf(ProcessUIUtil.PROCESS_PACKAGE_SEPARATOR);

                if (idx >= 0) {
                    name = qualifiedName.substring(idx + 1);
                } else {
                    name = qualifiedName;
                }
            }

            return name;
        }

        /**
         * @return the displayName
         */
        @Override
        public String getDisplayName() {
            return displayName;
        }

        /*
         * (non-Javadoc)
         */
        @Override
        public String getQualifiedName() {
            return qualifiedName;
        }

        @Override
        public String getProjectName() {
            return projectName;
        }

        /*
         * (non-Javadoc)
         */
        @Override
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
            } else if (obj instanceof IProcessPickerProxyItem) {
                IProcessPickerProxyItem other = (IProcessPickerProxyItem) obj;
                isEquals =
                        other.getQualifiedName().equals(getQualifiedName())
                                && other.getURI().equals(getURI());
            }

            return isEquals;
        }

    }
}
