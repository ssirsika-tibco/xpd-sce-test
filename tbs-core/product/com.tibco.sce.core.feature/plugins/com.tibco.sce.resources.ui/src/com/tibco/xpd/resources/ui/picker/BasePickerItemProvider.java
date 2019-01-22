/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.resources.ui.picker;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.ui.internal.Messages;

/**
 * Provides content based on the indexer queries. It uses indexer specified by
 * {@link #getIndexerId()} to get items.
 * 
 * @since 3.3.1
 * @author Jan Arciuchiewicz
 */
public class BasePickerItemProvider implements IPickerItemProvider {

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<PickerItem> getContent(PickerTypeQuery typeQuery,
            IProgressMonitor monitor) {
        Set<PickerItem> items = new HashSet<PickerItem>();
        if (monitor != null) {
            monitor = new NullProgressMonitor();
        }
        monitor.beginTask(Messages.BasePickerItemProvider_gettinItems_message,
                IProgressMonitor.UNKNOWN);
        try {
            for (String type : typeQuery.getTypes()) {
                items.addAll(getItemsForType(typeQuery.getPickerExtension(),
                        typeQuery.getContext(),
                        type));
            }
        } finally {
            monitor.done();
        }
        return items;
    }

    /**
     * Gets items for a type of picker content.
     * 
     * @param pickerExtension
     *            the context picker content extension.
     * @param context
     *            the context object if needed or 'null' if no context
     *            information is necessary (typical case). This object is passed
     *            from a {@link PickerTypeQuery}.
     * @param type
     *            the type specific to picker content extension.
     * @return a collection of {@link PickerItem}s of the provided type.
     * 
     * @see PickerTypeQuery
     */
    protected Collection<PickerItem> getItemsForType(
            PickerContentExtension pickerExtension, Object context, String type) {
        if (getIndexerId() != null) {
            return getItemsForTypeFromIndexer(getIndexerId(),
                    pickerExtension,
                    type);
        }
        return Collections.emptyList();
    }

    /**
     * Retrieves PickerItems by querying an indexer. It calls
     * {@link #createPickerItem(IndexerItem, PickerContentExtension)} to create
     * a PickerItem from an indexer item, so it may need to be overwritten if
     * indexer item have to be mapped in a non standard way.
     * 
     * @param indexerId
     *            the ID of an indexer to query.
     * @param pickerExtension
     *            the context extension.
     * @param type
     *            the type of item to retrieve.
     * @return a collection of {@link PickerItem}s created from indexer query.
     */
    protected Collection<PickerItem> getItemsForTypeFromIndexer(
            String indexerId, PickerContentExtension pickerExtension,
            String type) {
        Set<PickerItem> items = new HashSet<PickerItem>();
        IndexerItemImpl queryItem = new IndexerItemImpl();
        queryItem.setType(type);
        Collection<IndexerItem> result =
                XpdResourcesPlugin.getDefault().getIndexerService()
                        .query(indexerId, queryItem);
        for (IndexerItem indexerItem : result) {
            items.add(createPickerItem(indexerItem, pickerExtension));
        }
        return items;
    }

    /**
     * Returns the id of the indexer to perform a query on.
     * 
     * @return id of the indexer to perform a query on or <code>null</code> if
     */
    protected String getIndexerId() {
        return null;
    }

    /**
     * Creates PickerItem from an indexer item. The implementations of this
     * class may provide specific qualified names of adjust other PickerItem
     * attributes.
     * 
     * @param indexerItem
     *            the input item from indexer.
     * @param pickerExtension
     *            the context extension
     * @return PickerItem based on IndexerItem.
     */
    protected PickerItem createPickerItem(IndexerItem indexerItem,
            PickerContentExtension pickerExtension) {
        return new PickerItemImpl(indexerItem, pickerExtension);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object resolvePickerItem(PickerItem item) {
        EditingDomain ed = getEditingDomain();
        Assert.isNotNull(ed, "Editing domain should not be null."); //$NON-NLS-1$
        if (item != null) {
            URI uri = URI.createURI(item.getURI());
            if (uri != null) {
                Object result = ed.getResourceSet().getEObject(uri, true);
                return result;
            }
        }
        return null;
    }

    /**
     * Returns editing domain used to resolve eObjects.
     * 
     * @return editing domain used to resolve eObjects.
     */
    protected EditingDomain getEditingDomain() {
        return XpdResourcesPlugin.getDefault().getEditingDomain();
    }

    /**
     * Utility method to get and URI of an EObject.
     * 
     * @param eObject
     *            EObject to get an URI for. It <b>MUST</b> be contained in a
     *            resource.
     * @return URI of an EObject.
     */
    public static String getURI(EObject eObject) {
        return EcoreUtil.getURI(eObject).toString();
    }
}
