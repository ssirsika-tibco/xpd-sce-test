/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.resources.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.indexer.IndexerService;
import com.tibco.xpd.resources.internal.indexer.IndexerServiceImpl;

/**
 * BOM Indexer Service. This provides methods to access the indexer service to
 * acquire profiles and stereotypes.
 * <p>
 * Either use {@link BOMResourcesPlugin#getIndexerService()} or
 * {@link #getInstance()} to access this service.
 * </p>
 * <p>
 * since 3.3 it also provides methods to access the BOM resource indexer.
 * </p>
 * 
 * @author njpatel
 * 
 */
public final class BOMIndexerService {

    /**
     * The internal project column in the indexer.
     */
    private static final String ATT_PROJECT = "internal_project"; //$NON-NLS-1$

    public static final String UML_PROFILE_INDEXER_ID =
            "com.tibco.xpd.bom.resources.indexing.umlProfileIndexer"; //$NON-NLS-1$

    public static final String UML_STEREOTYPE_INDEXER_ID =
            "com.tibco.xpd.bom.resources.indexing.umlStereotypeIndexer"; //$NON-NLS-1$

    // Additional attributes for the stereotype indexer
    public static final String INDEXER_ATTR_MODEL = "model"; //$NON-NLS-1$

    public static final String INDEXER_ATTR_PACKAGE = "package"; //$NON-NLS-1$

    public static final String INDEXER_ATTR_CLASS = "class"; //$NON-NLS-1$

    public static final String INDEXER_ATTR_PROP = "property"; //$NON-NLS-1$

    public static final String INDEXER_ATTR_OPERATION = "operation"; //$NON-NLS-1$

    public static final String INDEXER_ATTR_PRIMTYPE = "primtype"; //$NON-NLS-1$

    public static final String INDEXER_ATTR_GEN = "generalization"; //$NON-NLS-1$

    public static final String INDEXER_ATTR_ASSOC = "association"; //$NON-NLS-1$

    public static final String INDEXER_ATTR_ENUMERATION = "enumeration"; //$NON-NLS-1$

    public static final String INDEXER_ATTR_ENUMLIT = "enumLit"; //$NON-NLS-1$

    public static final String INDEXER_ATTR_ASSOC_CLASS = "assocClass"; //$NON-NLS-1$

    /*
     * Column to store FQN for a package in lower case.
     */

    public static final String INDEXER_ATTR_PACKAGE_LOWERCASE =
            "packageLowerCase"; //$NON-NLS-1$

    /*
     * Column in the Profile indexer.
     */
    public static final String INDEXER_ATTR_PROJECT = "eclipse_project"; //$NON-NLS-1$

    public static final String INDEXER_ATTR_CDS_FACTORY = "cds_factory"; //$NON-NLS-1$

    public static final String INDEXER_ATTR_NAMESPACE_URI = "namespace_uri"; //$NON-NLS-1$

    // Additional attribute value when set - this has to be one character.
    public static final String VALUE_SET = "y"; //$NON-NLS-1$

    /**
     * Singleton instance of this class
     */
    private static final BOMIndexerService INSTANCE = new BOMIndexerService();

    /** Label used in UI */
    public static final String INDEXER_ATTR_DISPLAY_LABEL = "displayLabel"; //$NON-NLS-1$

    private final IndexerService service;

    /**
     * Private constructor - use {@link #getInstance()} to get the singleton
     * instance of this class.
     */
    private BOMIndexerService() {
        service = XpdResourcesPlugin.getDefault().getIndexerService();
        Assert.isNotNull(service, "Indexer service"); //$NON-NLS-1$
    }

    /**
     * Get the singleton instance of this class.
     * 
     * @return <code>BOMIndexerService</code>.
     */
    public static BOMIndexerService getInstance() {
        return INSTANCE;
    }

    /**
     * Get all profiles from the indexer.
     * 
     * @return array of profile <code>IndexerItem</code> objects, empty list if
     *         none found.
     */
    public IndexerItem[] getProfiles() {
        return getProfiles(new IndexerItemImpl());
    }

    /**
     * Get all profiles from the picker using the criteria given.
     * 
     * @param criteria
     *            search criteria
     * @return array of profile <code>IndexerItem</code> objects, empty list if
     *         none found.
     */
    public IndexerItem[] getProfiles(IndexerItem criteria) {
        IndexerItem[] items = null;

        // If criteria is null then return all profiles
        if (criteria == null) {
            criteria = new IndexerItemImpl();
        }

        Collection<IndexerItem> result =
                service.query(UML_PROFILE_INDEXER_ID, criteria);

        if (result != null) {
            items = result.toArray(new IndexerItem[result.size()]);
        }

        return items != null ? items : new IndexerItem[0];
    }

    /**
     * Get all stereotypes from the indexer.
     * 
     * @return array of stereotype <code>IndexerItem</code> objects.
     */
    public IndexerItem[] getStereotypes() {
        // Get all stereotypes
        return getStereotypes(new IndexerItemImpl());
    }

    /**
     * Get stereotypes that can be applied to the given eClass.
     * 
     * @param eClass
     *            get Stereotypes that can be applied to this
     *            <code>EClass</code> type only.
     * @param project
     *            get Stereotypes from this project only. Can be
     *            <code>null</code>.
     * @return array of stereotype <code>IndexerItem</code> objects that can be
     *         applied to the given eClass.
     */
    public IndexerItem[] getStereotypesFor(EClass eClass, IProject project) {
        IndexerItem[] items = null;

        if (eClass != null) {
            String key = getKey(eClass);

            if (key != null) {
                IndexerItemImpl criteria = new IndexerItemImpl();
                criteria.set(key, VALUE_SET);
                if (project != null) {
                    criteria.set(INDEXER_ATTR_PROJECT, project.getName());
                }

                items = getStereotypes(criteria);
            }
        }

        return items != null ? items : new IndexerItem[0];
    }

    /**
     * Get stereotypes that can be applied to the given eClass.
     * 
     * @param eClass
     * @return array of stereotype <code>IndexerItem</code> objects that can be
     *         applied to the given eClass.
     */
    public IndexerItem[] getStereotypesFor(EClass eClass) {
        return getStereotypesFor(eClass, null);
    }

    /**
     * Get the (non-qualified) name from the given item.
     * 
     * @param item
     *            <code>IndexerItem</code>
     * @return non-qualified name.
     */
    public String getName(IndexerItem item) {
        String name = null;

        if (item != null) {
            name = item.getName();

            if (name.indexOf(NamedElement.SEPARATOR) > 0) {
                name =
                        name.substring(name.lastIndexOf(NamedElement.SEPARATOR)
                                + NamedElement.SEPARATOR.length());
            }
        }

        return name;
    }

    /**
     * Get the qualification of the given item.
     * 
     * @param item
     *            <code>IndexerItem</code>
     * @return qualification.
     */
    public String getQualification(IndexerItem item) {
        String qualification = null;

        if (item != null) {
            String name = item.getName();

            if (name != null && name.indexOf(NamedElement.SEPARATOR) > 0) {
                qualification =
                        name.substring(0,
                                name.lastIndexOf(NamedElement.SEPARATOR));
            }
        }

        return qualification;
    }

    /**
     * Resolve the given item into an <code>EObject</code> that it represents.
     * This will take the URI from the given item and resolve it using the
     * resourceset in the shared transactional editing domain.
     * 
     * @param item
     *            <code>IndexerItem</code> to resolve.
     * @return <code>EObject</code> if resolved, <code>null</code> otherwise.
     */
    public EObject resolve(IndexerItem item) {
        EObject eo = null;

        if (item != null) {
            String uriStr = item.getURI();

            if (uriStr != null) {
                URI uri = URI.createURI(uriStr);

                TransactionalEditingDomain ed =
                        XpdResourcesPlugin.getDefault().getEditingDomain();

                if (ed != null) {
                    eo = ed.getResourceSet().getEObject(uri, true);
                }
            }
        }

        return eo;
    }

    /**
     * Get stereotype from the indexer using the given criteria.
     * 
     * @param criteria
     * @return array of stereotype <code>IndexerItem</code> objects.
     */
    public IndexerItem[] getStereotypes(IndexerItem criteria) {
        IndexerItem[] items = null;
        Collection<IndexerItem> result =
                service.query(UML_STEREOTYPE_INDEXER_ID, criteria);

        if (result != null) {
            items = result.toArray(new IndexerItem[result.size()]);
        }

        return items != null ? items : new IndexerItem[0];
    }

    /**
     * Get all the model items from the entire workspace. This is the same as
     * {@link #getAllModels(IProject) getAllModels(null)}.
     * 
     * @return list model indexer items, empty array if none found.
     * @since 3.3
     */
    public IndexerItem[] getAllModels() {
        return getAllModels(null);
    }

    /**
     * Get all the model items from the entire workspace.
     * 
     * @param projectName
     *            get all items from this project, <code>null</code> for all
     *            projects.
     * @return list model indexer items, empty array if none found.
     * @since 3.5
     */
    public IndexerItem[] getAllModels(String projectName) {
        List<IndexerItem> modelItems = new ArrayList<IndexerItem>();

        IndexerItemImpl criteria =
                new IndexerItemImpl(null, ResourceItemType.PACKAGE.toString(),
                        null, null);

        if (projectName != null && projectName.length() > 0) {
            criteria.set(ATT_PROJECT, projectName);
        }

        Collection<IndexerItem> items =
                service.query(BOMResourcesPlugin.BOM_INDEXER_ID, criteria);
        if (items != null && !items.isEmpty()) {
            // Mapping from file URI to all packages contained in the file
            Map<URI, List<IndexerItem>> packages =
                    new HashMap<URI, List<IndexerItem>>();
            for (IndexerItem item : items) {
                String uriStr = item.getURI();
                if (uriStr != null) {
                    URI uri = URI.createURI(uriStr).trimFragment();
                    List<IndexerItem> pkgs = packages.get(uri);
                    if (pkgs == null) {
                        pkgs = new ArrayList<IndexerItem>();
                        packages.put(uri, pkgs);
                    }
                    pkgs.add(item);
                }
            }

            /*
             * For each entry in the map find the package name with the shortest
             * length - this should be the model name (as all package names are
             * fully qualified
             */
            for (List<IndexerItem> pkgs : packages.values()) {
                IndexerItem model = null;
                for (IndexerItem pkg : pkgs) {
                    String name = pkg.getName();
                    if (name != null) {
                        if (model == null) {
                            model = pkg;
                        } else {
                            if (name.length() < model.getName().length()) {
                                model = pkg;
                            }
                        }
                    }
                }
                modelItems.add(model);
            }
        }

        return modelItems.toArray(new IndexerItem[modelItems.size()]);
    }

    /**
     * Get all {@link IndexerItem Items} Packages indexed for the given BOM
     * file.
     * 
     * @param bomFile
     * @return all package {@link IndexerItem}s, empty array if none found
     *         (could be because the BOM file does not exist any more).
     * @since 3.3
     */
    public IndexerItem[] getAllPackages(IFile bomFile) {
        Collection<IndexerItem> items = null;
        if (bomFile != null) {
            IndexerItemImpl criteria = new IndexerItemImpl();
            criteria.setType(ResourceItemType.PACKAGE.toString());
            criteria.set("internal_path", bomFile.getFullPath() //$NON-NLS-1$
                    .toPortableString());
            items = service.query(BOMResourcesPlugin.BOM_INDEXER_ID, criteria);
        }

        return items != null ? items.toArray(new IndexerItem[items.size()])
                : new IndexerItem[0];
    }

    /**
     * Get the column key to set in the indexer criteria for the given
     * <code>EClass</code> when searching for stereotypes.
     * 
     * @param ec
     * @return attribute name to set in the query.
     */
    private String getKey(EClass ec) {
        String key = null;

        if (ec == UMLPackage.eINSTANCE.getModel()) {
            key = INDEXER_ATTR_MODEL;
        } else if (ec == UMLPackage.eINSTANCE.getPackage()) {
            key = INDEXER_ATTR_PACKAGE;
        } else if (ec == UMLPackage.eINSTANCE.getClass_()) {
            key = INDEXER_ATTR_CLASS;
        } else if (ec == UMLPackage.eINSTANCE.getProperty()) {
            key = INDEXER_ATTR_PROP;
        } else if (ec == UMLPackage.eINSTANCE.getPrimitiveType()) {
            key = INDEXER_ATTR_PRIMTYPE;
        } else if (ec == UMLPackage.eINSTANCE.getAssociation()) {
            key = INDEXER_ATTR_ASSOC;
        } else if (ec == UMLPackage.eINSTANCE.getGeneralization()) {
            key = INDEXER_ATTR_GEN;
        } else if (ec == UMLPackage.eINSTANCE.getOperation()) {
            key = INDEXER_ATTR_OPERATION;
        } else if (ec == UMLPackage.eINSTANCE.getEnumeration()) {
            key = INDEXER_ATTR_ENUMERATION;
        } else if (ec == UMLPackage.eINSTANCE.getEnumerationLiteral()) {
            key = INDEXER_ATTR_ENUMLIT;
        } else if (ec == UMLPackage.eINSTANCE.getAssociationClass()) {
            key = INDEXER_ATTR_ASSOC_CLASS;
        }

        return key;
    }

    /**
     * 
     * @param packageName
     * 
     * @return The BOM package(s) (or sub-package with the given package name
     *         (com.xx.xxx etc)
     */
    public Collection<Package> getPackageById(String packageName) {
        IndexerItemImpl criteria =
                new IndexerItemImpl(null, ResourceItemType.PACKAGE.toString(),
                        null, null);

        criteria.set(IndexerServiceImpl.ATTRIBUTE_NAME, packageName);

        Collection<IndexerItem> items =
                service.query(BOMResourcesPlugin.BOM_INDEXER_ID, criteria);
        if (items != null && !items.isEmpty()) {
            Set<Package> packages = new HashSet<Package>();

            for (IndexerItem item : items) {
                Package pkg = (Package) resolve(item);

                if (pkg != null) {
                    packages.add(pkg);
                }
            }
            return packages;
        }

        return Collections.emptyList();
    }

}
