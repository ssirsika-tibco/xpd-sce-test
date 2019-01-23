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
import com.tibco.xpd.analyst.resources.xpdl2.pickers.DecisionFlowResourceItemType;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.IProcessPickerProxyItem;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.internal.db.ResourceDbException;
import com.tibco.xpd.resources.internal.indexer.IndexerServiceImpl;

/**
 * Provider helper class that provides the content for the Decision Flow picker.
 * 
 * @author Miguel Torres
 * 
 */
public final class DecisionFlowFilterPickerProviderHelper implements
        IDecisionFlowPickerProvider {

    // Singleton instance of this class
    private final static DecisionFlowFilterPickerProviderHelper INSTANCE =
            new DecisionFlowFilterPickerProviderHelper();

    /**
     * Private constructor. This class cannot be instantiated.
     */
    private DecisionFlowFilterPickerProviderHelper() {

    }

    /**
     * Get singleton instance of this class.
     * 
     * @return <code>BOMPickerProviderUtil</code>
     */
    public static DecisionFlowFilterPickerProviderHelper getInstance() {
        return INSTANCE;
    }

    /*
     * (non-Javadoc)
     */
    public IProcessPickerProxyItem[] getContent(IProgressMonitor monitor,
            DecisionFlowType type) {
        Set<IProcessPickerProxyItem> items =
                new HashSet<IProcessPickerProxyItem>();

        switch (type) {
        case DECISIONFLOW:
            items = getAllDecisionFlows(monitor);
            break;
        case DECISIONFLOW_PACKAGE:
            items = getAllDecisionFlowPackages(monitor);
            break;
        }

        return items.toArray(new IProcessPickerProxyItem[items.size()]);
    }

    /*
     * (non-Javadoc)
     */
    public IProcessPickerProxyItem getItem(String uri, String name) {
        IProcessPickerProxyItem item = null;

        if (uri != null && name != null) {

            IndexerItem criteria = new IndexerItemImpl(name, null, uri, null);
            Collection<IndexerItem> result =
                    XpdResourcesPlugin.getDefault().getIndexerService()
                            .query(Xpdl2ResourcesPlugin.DECISIONFLOW_INDEXER_ID,
                                    criteria);

            if (result != null && !result.isEmpty()) {
                for (IndexerItem indexerItem : result) {
                    item = new DecisionFlowPickerItem(indexerItem);
                    break;
                }
            }

        }

        return item;
    }

    /**
     * Get all decision flows from the database.
     * 
     * @param monitor
     *            progress monitor
     * @return set of <code>IProcessPickerProxyItem</code> objects. Empty set if
     *         none found.
     */
    private Set<IProcessPickerProxyItem> getAllDecisionFlows(
            IProgressMonitor monitor) {
        monitor
                .beginTask(Messages.DecisionFlowFilterPickerProviderHelper_GettingDecisionFlowNames_shortdesc,
                        2);

        Set<IProcessPickerProxyItem> items =
                getAllItems(monitor, DecisionFlowResourceItemType.DECISIONFLOW.toString());
        return items;
    }

    /**
     * Get all decision flows packages from the database.
     * 
     * @param monitor
     *            progress monitor
     * @return set of <code>IProcessPickerProxyItem</code> objects. Empty set if
     *         none found.
     */
    private Set<IProcessPickerProxyItem> getAllDecisionFlowPackages(
            IProgressMonitor monitor) {
        monitor
                .beginTask(Messages.DecisionFlowFilterPickerProviderHelper_GettingDecisionFlowPackageNames_shortdesc,
                        2);

        Set<IProcessPickerProxyItem> items =
                getAllItems(monitor, DecisionFlowResourceItemType.DECISIONFLOW_PACKAGE.toString());
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
     * @throws ResourceDbException
     */
    private Set<IProcessPickerProxyItem> getAllItems(IProgressMonitor monitor,
            String resourceItemType) {
        Set<IProcessPickerProxyItem> items =
                new HashSet<IProcessPickerProxyItem>();

        IndexerItem criteria =
                new IndexerItemImpl(null, resourceItemType, null, null);
        Collection<IndexerItem> result =
                XpdResourcesPlugin.getDefault().getIndexerService()
                        .query(Xpdl2ResourcesPlugin.DECISIONFLOW_INDEXER_ID,
                                criteria);

        monitor.worked(1);

        if (result != null) {
            for (IndexerItem indexerItem : result) {
                items.add(new DecisionFlowPickerItem(indexerItem));
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
    private class DecisionFlowPickerItem implements IProcessPickerProxyItem {

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
        public DecisionFlowPickerItem(String qualifiedName, URI uri, URI imageUri) {

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
        public DecisionFlowPickerItem(IndexerItem item) {
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
        public String getDisplayName() {
            return displayName;
        }

        /*
         * (non-Javadoc)
         */
        public String getQualifiedName() {
            return qualifiedName;
        }
        
        public String getProjectName() {
            return projectName;
        }

        /*
         * (non-Javadoc)
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
