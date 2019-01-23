/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.resources.projectconfig.projectassets.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.SWT;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.projectassets.IProjectAssetCategory;
import com.tibco.xpd.resources.projectconfig.projectassets.IProjectAssetManager;

/**
 * Helper class to get the list of elements that are defined as extensions of
 * <i>projectAsset</i> extension point. This will define all assets and assets
 * categories available for a project.
 * <p>
 * (Since 3.5) For asset migration functionality use
 * {@link ProjectAssetMigrationManager}.
 * </p>
 * 
 * @author njpatel, Jan Arciuchiewicz
 */
public class ProjectAssetManager implements IProjectAssetManager {

    /** Project asset extension point name */
    private static final String PROJECT_ASSET_EXTENSIONPOINT = "projectAsset"; //$NON-NLS-1$

    /** Category element */
    private static final String CATEGORY_ELEMENT = "category"; //$NON-NLS-1$

    /** Asset element */
    private static final String ASSSET_ELEMENT = "asset"; //$NON-NLS-1$

    /**
     * All declared assets.
     * <p>
     * NOTE: This list is ordered in order of execution based on dependencies.
     * Uses <code>{@link AssetComparator}</code> for the ordering.
     * </p>
     */
    private ProjectAssetElement[] assets;

    /** Top level categories - categories with null parent category */
    private Collection<IProjectAssetCategory> topLevelCategories;

    /**
     * The private constructor constructor. To obtain instance of the class use
     * {@link #getProjectAssetMenager()}.
     */
    private ProjectAssetManager() {
        createAssetsModel();
    }

    /**
     * Returns Project assets manager containing assets model created from
     * extensions. The model will have initialized all necessary elements but
     * some extensions element can be initialized in a lazy manner.
     * 
     * @return
     */
    public static IProjectAssetManager getProjectAssetMenager() {
        return new ProjectAssetManager();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.projectconfig.projectassets.IProjectAssetManager
     * #getTopLevelCategories()
     */
    @Override
    public Collection<IProjectAssetCategory> getTopLevelCategories() {
        return Collections.unmodifiableCollection(topLevelCategories);
    }

    /**
     * Get a list of <code>ProjectAssetElement</code> objects that represents
     * extensions of the <b>projectAsset</b> extension point.
     * <p>
     * NOTE: This list is ordered in order of execution based on dependencies.
     * Uses <code>{@link AssetComparator}</code> for the ordering.
     * </p>
     * 
     * @return Array of <code>ProjectAssetElement</code> objects. The extension
     *         will contain the main asset types, all extending asset types can
     *         be accessed via
     *         <code>{@link ProjectAssetElement#getExtendingAssets()}</code> of
     *         their respective parent assets. If there are no asset types then
     *         an empty array will be returned.
     */
    @Override
    public ProjectAssetElement[] getAssets() {
        return assets;
    }

    /**
     * Get the project asset of the given <i>id</i>.
     * 
     * @param id
     * @return <code>{@link ProjectAssetElement}</code> with the given Id. If
     *         there is no asset type defined of the given <i>id</i> then
     *         <b>null</b> will be returned.
     */
    @Override
    public ProjectAssetElement getAssetById(String id) {
        if (id != null) {
            for (ProjectAssetElement asset : assets) {
                if (id.equals(asset.getId())) {
                    return asset;
                }

                // check all extending assets for id
                HashSet<ProjectAssetElement> extendingAssets =
                        new HashSet<ProjectAssetElement>(Arrays.asList(asset
                                .getExtendingAssets()));
                while (!extendingAssets.isEmpty()) {
                    HashSet<ProjectAssetElement> assetsToCheck =
                            new HashSet<ProjectAssetElement>();
                    for (ProjectAssetElement extendingAsset : extendingAssets) {
                        if (id.equals(extendingAsset.getId())) {
                            return extendingAsset;
                        }
                        assetsToCheck.addAll(Arrays.asList(extendingAsset
                                .getExtendingAssets()));
                    }
                    extendingAssets = assetsToCheck;
                }
            }
        } else {
            throw new NullPointerException("id is null."); //$NON-NLS-1$
        }

        return null;
    }

    /**
     * Get the extensions of the ProjectAsset extension point
     * 
     * @return Map using asset id as key and <code>ProjectAssetElement</code>
     *         object as the value. If no extensions found an empty map will be
     *         returned.
     */
    private void createAssetsModel() {
        OtherProjectAssetCategory otherCategory =
                new OtherProjectAssetCategory();
        Map<String, ProjectAssetElement> assetMap =
                new HashMap<String, ProjectAssetElement>();
        Map<String, ProjectAssetCategoryElement> categoryMap =
                new HashMap<String, ProjectAssetCategoryElement>();
        IExtensionPoint point =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(XpdResourcesPlugin.ID_PLUGIN,
                                PROJECT_ASSET_EXTENSIONPOINT);

        if (point != null) {
            IExtension[] extensions = point.getExtensions();

            if (extensions != null) {
                for (IExtension ext : extensions) {
                    // For each extension get the configuration elements
                    IConfigurationElement[] elements =
                            ext.getConfigurationElements();

                    if (elements != null) {
                        for (IConfigurationElement elem : elements) {
                            if (ASSSET_ELEMENT.equals(elem.getName())) {
                                ProjectAssetElement asset =
                                        new ProjectAssetElement(elem);
                                if (!assetMap.containsKey(asset.getId())) {
                                    assetMap.put(asset.getId(), asset);
                                } else {
                                    String message =
                                            MessageFormat
                                                    .format("Doubled asset id: {0} in extension: {1}", //$NON-NLS-1$
                                                            asset.getId(),
                                                            PROJECT_ASSET_EXTENSIONPOINT);
                                    XpdResourcesPlugin.getDefault().getLogger()
                                            .warn(message);
                                }
                            } else if (CATEGORY_ELEMENT.equals(elem.getName())) {
                                ProjectAssetCategoryElement category =
                                        new ProjectAssetCategoryElement(elem);
                                if (!categoryMap.containsKey(category.getId())) {
                                    categoryMap.put(category.getId(), category);
                                } else {
                                    String message =
                                            MessageFormat
                                                    .format("Doubled category id: {0} in extension: {1}", //$NON-NLS-1$
                                                            category.getId(),
                                                            PROJECT_ASSET_EXTENSIONPOINT);
                                    XpdResourcesPlugin.getDefault().getLogger()
                                            .warn(message);
                                }
                            } else {
                                String message =
                                        MessageFormat
                                                .format("Unsupported element: {0} in extension: {1}", //$NON-NLS-1$
                                                        elem.getName(),
                                                        PROJECT_ASSET_EXTENSIONPOINT);
                                XpdResourcesPlugin.getDefault().getLogger()
                                        .warn(message);
                            }
                        }
                    }
                }
            }
        }

        List<ProjectAssetElement> assetList =
                new ArrayList<ProjectAssetElement>();

        for (ProjectAssetElement asset : assetMap.values()) {
            if (asset.getExtends() != null) {
                ProjectAssetElement parent = assetMap.get(asset.getExtends());

                if (parent != null) {
                    parent.addExtendingAsset(asset);
                }
            } else {
                if (!assetList.contains(asset)) {
                    assetList.add(asset);
                }
            }
            // setting asset category
            if (asset.getCategoryId() != null) {
                ProjectAssetCategoryElement category =
                        categoryMap.get(asset.getCategoryId());
                if (category != null) {
                    asset.setCategory(category);
                } else {
                    String message =
                            MessageFormat
                                    .format("Asset: {0} references non existing category: {1}", //$NON-NLS-1$
                                            asset.getId(),
                                            asset.getCategoryId());
                    XpdResourcesPlugin.getDefault().getLogger().warn(message);
                    // assign to other category
                    asset.setCategory(otherCategory);
                }
            } else {
                // assign to other category
                asset.setCategory(otherCategory);
            }
        }

        for (ProjectAssetCategoryElement category : categoryMap.values()) {
            // setting category parent
            if (category.getParentCategoryId() != null) {
                ProjectAssetCategoryElement parent =
                        categoryMap.get(category.getParentCategoryId());
                if (parent != null) {
                    category.setParent(parent);
                } else {
                    String message =
                            MessageFormat
                                    .format("Category: {0} references non existing category: {1}", //$NON-NLS-1$
                                            category.getId(),
                                            category.getParentCategoryId());
                    XpdResourcesPlugin.getDefault().getLogger().warn(message);
                }
            }
            // setting category children
            String categoryId = category.getId();
            List<Object> categoryChildren = new ArrayList<Object>();
            for (ProjectAssetCategoryElement otherDefinedCategory : categoryMap
                    .values()) {
                if (categoryId.equals(otherDefinedCategory.getId()))
                    continue;
                if (categoryId.equals(otherDefinedCategory
                        .getParentCategoryId())) {
                    categoryChildren.add(otherDefinedCategory);
                }
            }
            for (ProjectAssetElement asset : assetMap.values()) {
                if (categoryId.equals(asset.getCategoryId())) {
                    categoryChildren.add(asset);
                }
            }
            category.setChildren(categoryChildren
                    .toArray(new Object[categoryChildren.size()]));

        }
        // Sort the assets in order of the dependencies
        this.assets = sortAssets(assetList);

        List<IProjectAssetCategory> topLevelCategoryList =
                new ArrayList<IProjectAssetCategory>();
        for (ProjectAssetCategoryElement category : categoryMap.values()) {
            if (category.getParentCategory() == null) {
                topLevelCategoryList.add(category);
            }
        }

        List<Object> otherCategoryChildren = new ArrayList<Object>();
        for (ProjectAssetElement asset : assets) {
            if (asset.getCategory() == otherCategory) {
                otherCategoryChildren.add(asset);
            }
        }
        if (otherCategoryChildren.size() > 0) {
            otherCategory.setChildren(otherCategoryChildren
                    .toArray(new Object[otherCategoryChildren.size()]));
            topLevelCategoryList.add(otherCategory);
        }

        this.topLevelCategories = topLevelCategoryList;
    }

    /**
     * Sort the assets in accordance with any dependencies defined.
     * 
     * @param assets
     * @return sorted assets
     * @since 3.2
     */
    private ProjectAssetElement[] sortAssets(List<ProjectAssetElement> assets) {
        if (assets != null && !assets.isEmpty()) {
            DependencySorter sorter = new DependencySorter(assets);
            List<ProjectAssetElement> sortedAssets = sorter.getOrderedList();

            if (!sortedAssets.isEmpty()) {
                return sortedAssets
                        .toArray(new ProjectAssetElement[sortedAssets.size()]);
            }

        }
        return new ProjectAssetElement[0];
    }

    /**
     * Asset sorter that sorts the asset types in accordance with any
     * dependencies defined in the extension. (Uses topological sort).
     * 
     * @since 3.2
     */
    private class DependencySorter {
        /**
         * Set of assets nodes that appear in dependency relationships
         */
        private List<ProjectAssetElement> assetNodes =
                new ArrayList<ProjectAssetElement>();

        /**
         * Represents an asset dependency relationship ('to' depends on 'from').
         */
        private class Arc {
            ProjectAssetElement from;

            ProjectAssetElement to;

            Arc(ProjectAssetElement from, ProjectAssetElement to) {
                this.from = from;
                this.to = to;
            }

            @Override
            public String toString() {
                return (from.getId() + "<-" + to.getId()); //$NON-NLS-1$
            }
        }

        /**
         * Set of asset dependencies
         */
        private Set<Arc> arcs = new HashSet<Arc>();

        /**
         * Ordered list of the asset types.
         */
        private List<ProjectAssetElement> sortedList =
                new ArrayList<ProjectAssetElement>();

        /**
         * Copy of the assets list passed to this object - this will be used to
         * find the asset types related to the dependencies.
         */
        private List<ProjectAssetElement> origAssetsList;

        /**
         * @param assets
         */
        public DependencySorter(List<ProjectAssetElement> assets) {
            this.origAssetsList = new ArrayList<ProjectAssetElement>(assets);

            for (ProjectAssetElement asset : assets) {
                String[] dependencies = asset.getDependencies();

                if (dependencies != null && dependencies.length > 0) {
                    // This asset type has dependencies so add to dependency
                    // relationship collection
                    for (String id : dependencies) {
                        ProjectAssetElement dAsset =
                                getAssetById(id, origAssetsList);
                        if (dAsset != null) {
                            addArc(dAsset, asset);
                        }
                    }
                } else {
                    // This asset type has no dependencies so add it directly to
                    // the sorted list
                    addToSortedList(asset);
                }
            }
        }

        /**
         * Get the sorted asset type list.
         * 
         * @return
         */
        public List<ProjectAssetElement> getOrderedList() {
            while (!isEmpty()) {
                // Get minimal asset type - asset with no dependencies
                ProjectAssetElement minimalAsset = getMinimalNode();

                if (minimalAsset != null) {
                    addToSortedList(minimalAsset);
                    removeNode(minimalAsset);
                } else {
                    // There is a cyclic dependency so report it
                    StringBuffer message = new StringBuffer();
                    message.append("Cyclic dependency has been detected in the project asset type definitions:"); //$NON-NLS-1$
                    for (Arc arc : arcs) {
                        message.append(SWT.CR);
                        message.append(SWT.LF);
                        message.append(String
                                .format("'%1$s'(%2$s) depends on '%3$s'(%4$s)", //$NON-NLS-1$
                                        arc.to.getName(),
                                        arc.to.getId(),
                                        arc.from.getName(),
                                        arc.from.getId()));
                    }

                    XpdResourcesPlugin
                            .getDefault()
                            .getLogger()
                            .error(new IllegalArgumentException(
                                    message.toString()),
                                    "Cyclic dependency detected in project asset definitions"); //$NON-NLS-1$

                    throw new IllegalArgumentException(
                            "Cyclic dependency detected in project asset definitions - see error log for more details."); //$NON-NLS-1$
                }
            }

            return sortedList;
        }

        /**
         * Add the given asset to the sorted list if the list doesn't already
         * contain it.
         * 
         * @param asset
         */
        private void addToSortedList(ProjectAssetElement asset) {
            if (asset != null && !sortedList.contains(asset)) {
                sortedList.add(asset);
            }
        }

        /**
         * Add the given asset to the asset nodes list if the list doesn't
         * already contain it.
         * 
         * @param asset
         */
        private void addAssetNode(ProjectAssetElement asset) {
            if (asset != null && !assetNodes.contains(asset)) {
                assetNodes.add(asset);
            }
        }

        /**
         * Add a dependency relationship.
         * 
         * @param from
         *            parent asset
         * @param to
         *            dependent asset
         */
        private void addArc(ProjectAssetElement from, ProjectAssetElement to) {
            addAssetNode(from);
            addAssetNode(to);
            arcs.add(new Arc(from, to));
        }

        /**
         * Returns <code>true</code> if there is no arc such that the asset is
         * on its 'to' side, i.e. asset type with no dependencies.
         * 
         * @param asset
         * @return
         */
        private boolean isMinimal(ProjectAssetElement asset) {
            for (Arc arc : arcs) {
                if (asset.equals(arc.to))
                    return false;
            }
            return true;
        }

        /**
         * Get the minimal node. This will be the asset type that has no
         * dependencies.
         * 
         * @return minimal asset or <code>null</code> if none found.
         */
        private ProjectAssetElement getMinimalNode() {
            for (ProjectAssetElement asset : assetNodes) {
                if (isMinimal(asset))
                    return asset;
            }
            return null;
        }

        /**
         * Remove the given asset type from the arc and asset nodes collections.
         * 
         * @param id
         */
        private void removeNode(ProjectAssetElement asset) {
            for (Iterator<Arc> iter = arcs.iterator(); iter.hasNext();) {
                if (asset.equals(iter.next().from)) {
                    iter.remove();
                }
            }
            assetNodes.remove(asset);
        }

        /**
         * Check if the asset nodes collection is empty.
         * 
         * @return
         */
        private boolean isEmpty() {
            return assetNodes.isEmpty();
        }

        /**
         * Get the asset element by id from the given list of assets.
         * 
         * @param id
         * @param assets
         *            list of assets to search
         * @return asset with the given id or <code>null</code> if not found.
         */
        private ProjectAssetElement getAssetById(String id,
                List<ProjectAssetElement> assets) {
            for (ProjectAssetElement asset : assets) {
                if (id.equals(asset.getId())) {
                    return asset;
                }
            }
            return null;
        }
    }

}
