/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.bom.resources.ui.internal.indexing;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Profile;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.ui.BOMImages;
import com.tibco.xpd.bom.resources.utils.BOMIndexerService;
import com.tibco.xpd.bom.resources.wc.UMLWorkingCopy;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.indexer.WorkingCopyIndexProvider;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Indexer provider for UML profiles.
 * 
 * @author njpatel
 */
public class UMLProfileIndexProvider implements WorkingCopyIndexProvider {

    // private String imageURI;
    // private final ProfileItemProvider provider;

    /**
     * Indexer provider for UML profiles.
     */
    public UMLProfileIndexProvider() {
        // provider = new ProfileItemProvider(XpdResourcesPlugin.getDefault()
        // .getAdapterFactory());

        // provider = new ProfileItemProvider(new
        // UMLItemProviderAdapterFactory());

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.indexer.WorkingCopyIndexProvider#getIndexItems
     * (com.tibco.xpd.resources.WorkingCopy)
     */
    public Collection<IndexerItem> getIndexItems(WorkingCopy wc) {
        List<IndexerItem> items = new ArrayList<IndexerItem>();

        if (wc instanceof UMLWorkingCopy && !wc.isInvalidFile()) {
            EObject root = wc.getRootElement();

            if (root != null) {
                // Index the root profile
                if (root instanceof Profile && ((Profile) root).isDefined()) {
                    addProfile(items, (Profile) root);
                }

                // Iterate through model for other profiles
                TreeIterator<Object> iterator =
                        EcoreUtil.getAllProperContents(root, false);

                while (iterator.hasNext()) {
                    Object next = iterator.next();

                    if (next instanceof Profile && ((Profile) next).isDefined()) {
                        addProfile(items, (Profile) next);
                    }
                }
            }
        }
        return items;
    }

    /**
     * Add <code>Profile</code> to the indexer items list.
     * 
     * @param items
     *            indexer items list
     * @param profile
     *            <code>Profile</code> to add.
     */
    private void addProfile(List<IndexerItem> items, Profile profile) {
        IndexerItem item = getIndexerItem(profile);

        // Add item if it has name and URI
        if (item != null && item.getName() != null
                && item.getName().length() > 0 && item.getURI() != null
                && item.getURI().length() > 0) {
            items.add(item);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.indexer.WorkingCopyIndexProvider#isAffectingEvent
     * (java.beans.PropertyChangeEvent)
     */
    public boolean isAffectingEvent(PropertyChangeEvent event) {
        return true;
    }

    /**
     * Build an indexer item for the given profile.
     * 
     * @param profile
     * @return <code>IndexerItem</code>
     */
    private IndexerItem getIndexerItem(Profile profile) {
        IndexerItemImpl item = null;

        if (profile != null) {
            item = new IndexerItemImpl();
            String name = profile.getQualifiedName();
            if (name == null) {
                name = profile.getName();
            }
            item.setName(name);
            item.setUri(EcoreUtil.getURI(profile).toString());
            item.set(BOMIndexerService.INDEXER_ATTR_PROJECT, WorkingCopyUtil
                    .getFile(profile).getProject().getName());

            URI imageURI =
                    URI.createPlatformPluginURI(Activator.PLUGIN_ID + "/" //$NON-NLS-1$
                            + BOMImages.PROFILE, true);

            if (imageURI != null) {
                item.set(BOMResourcesPlugin.INDEXER_ATTRIBUTE_IMAGE_URI,
                        imageURI.toString());
            }

        }

        return item;
    }

}
