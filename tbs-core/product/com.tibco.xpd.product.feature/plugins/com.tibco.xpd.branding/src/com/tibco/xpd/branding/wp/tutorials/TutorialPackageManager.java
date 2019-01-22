/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.branding.wp.tutorials;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;

/**
 * 
 * 
 * @author rgreen
 * 
 */
public class TutorialPackageManager {

    private final static TutorialPackageManager INSTANCE = new TutorialPackageManager();
    // private final Collection<TutorialCategory> categories;
    private final List<TutorialCategory> categories;
    private final Map<String, TutorialCategory> categoriesById;

    // private final Collection<TutorialPackage> packages;
    private final List<TutorialPackage> packages;
    private final Map<String, TutorialPackage> packagesById;

    /**
     * 
     * Return the Manager singleton
     * 
     * @return TutorialPackageManager
     */
    public static TutorialPackageManager getINSTANCE() {
        return INSTANCE;
    }

    public TutorialPackageManager() {
        // categories = new TreeSet<TutorialCategory>(new CategoryComparator());
        categories = new ArrayList<TutorialCategory>();
        categoriesById = new HashMap<String, TutorialCategory>();
        // packages = new TreeSet<TutorialPackage>(new PackageComparator());
        packages = new ArrayList<TutorialPackage>();
        packagesById = new HashMap<String, TutorialPackage>();
    }

    /**
     * 
     * Creates a TutorialCategory from an extension point IConfigurationElement
     * and adds it to the Manager's cache.
     * 
     * @param IConfigurationElement
     *            element
     */
    public void addCategory(IConfigurationElement element) {
        if ("category".equals(element.getName())) { //$NON-NLS-1$
            TutorialCategory cat = TutorialElementFactory
                    .CreateTutorialCategory(element);
            categories.add(cat);
            categoriesById.put(cat.getId(), cat);
        }

    }

    /**
     * 
     * Creates a TutorialPackage from an extension point IConfigurationElement
     * and adds it to the Manager's cache.
     * 
     * @param IConfigurationElement
     *            element
     */
    public void addPackage(IConfigurationElement element) {
        if ("project".equals(element.getName())) { //$NON-NLS-1$
            TutorialPackage proj = TutorialElementFactory
                    .CreateTutorialPackage(element);
            packages.add(proj);
            packagesById.put(proj.getId(), proj);
        }
    }

    /**
     * 
     * 
     * Returns true if the supplied TutorialCategory is in the managers cache
     * 
     * @param TutorialCategory
     *            category
     * @return boolean
     */
    public boolean containsCategory(TutorialCategory category) {
        return categories.contains(category);
    }

    /**
     * 
     * 
     * Returns true if the supplied TutorialCategory is in the managers cache
     * 
     * @param TutorialPackage
     *            package
     * @return boolean
     */
    public boolean containsPackage(TutorialPackage tutPackage) {
        return packages.contains(tutPackage);
    }

    public TutorialCategory getCategoryById(String categoryId) {
        return categoriesById.get(categoryId);
    }

    public TutorialPackage getPackageById(String packageId) {
        return packagesById.get(packageId);
    }

    /**
     * 
     * 
     * Returns true if the supplied categoryID corresponds to a TutorialCategory
     * in the manager's cache
     * 
     * @param String
     *            categoryId
     * @return boolean
     */
    public boolean containsCategoryId(String categoryId) {
        return categoriesById.containsKey(categoryId);
    }

    /**
     * 
     * 
     * Returns true if the supplied packageID corresponds to a TutorialPackage
     * in the manager's cache
     * 
     * @param String
     *            packageId
     * @return boolean
     */
    public boolean containsPackageId(String projectId) {
        return packagesById.containsKey(projectId);
    }

    /**
     * 
     * 
     * Returns a List of all the TutorialPackages in a TutorialCategory.
     * 
     * 
     * @param TutorialCategory
     *            category
     * @return List<TutorialPackage>
     */
    public List<TutorialPackage> getProjectsInCategory(TutorialCategory category) {
        String catId = category.getId();
        List<TutorialPackage> filteredProjects = new ArrayList<TutorialPackage>();

        for (TutorialPackage proj : packages) {
            if (catId.equals(proj.getCategory())) {
                filteredProjects.add(proj);
            }
        }

        return filteredProjects;
    }

    public Collection<TutorialCategory> getCategories() {
        return categories;
    }

    class CategoryComparator implements Comparator<TutorialCategory> {
        public int compare(TutorialCategory tc1, TutorialCategory tc2) {
            int result = 0;
            // String order1 = p1.getOrder();
            // String order2 = p2.getOrder();
            // result = order1.compareTo(order2);
            // if (result == 0) {
            // result = p1.getLabel().compareTo(p2.getLabel());
            // }
            return result;
        }
    }

    class PackageComparator implements Comparator<TutorialPackage> {
        public int compare(TutorialPackage tc1, TutorialPackage tc2) {
            int result = 0;
            // String order1 = p1.getOrder();
            // String order2 = p2.getOrder();
            // result = order1.compareTo(order2);
            // if (result == 0) {
            // result = p1.getLabel().compareTo(p2.getLabel());
            // }
            return result;
        }
    }

}
