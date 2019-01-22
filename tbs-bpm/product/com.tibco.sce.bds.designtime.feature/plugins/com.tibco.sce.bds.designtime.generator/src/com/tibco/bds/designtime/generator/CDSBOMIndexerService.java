/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.bds.designtime.generator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;

import com.tibco.bds.designtime.generator.internal.CDSBOMIndexer;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.indexer.IndexerService;
import com.tibco.xpd.resources.internal.indexer.IndexerServiceImpl;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * @author kupadhya
 * 
 */
public class CDSBOMIndexerService {
    /**
     * Singleton instance of this class
     */
    private static final CDSBOMIndexerService INSTANCE =
            new CDSBOMIndexerService();

    private final IndexerService service;

    private static final String INDEXER_ID =
            "com.tibco.xpd.n2.bom.gen.cdsIndexer";

    public static final String PACKAGE_NAME = "packageName"; //$NON-NLS-1$

    public static final String FACTORY_NAME = "factoryName"; //$NON-NLS-1$

    public static final String PARENT = "parent"; //$NON-NLS-1$

    public static final String ASSOCIATION = "association"; //$NON-NLS-1$

    public static final String INDEXER_ATTR_NAMESPACE_URI = "namespace_uri"; //$NON-NLS-1$

    public static final String QUALIFIED_NAME = "qualifiedName"; //$NON-NLS-1$

    public static final String CDS_FACTORY_NAME = "qualifiedCDSFactoryName"; //$NON-NLS-1$

    public static final String PACKAGE_LITERAL = "Package";

    /**
     * Private constructor - use {@link #getInstance()} to get the singleton
     * instance of this class.
     */
    private CDSBOMIndexerService() {
        service = XpdResourcesPlugin.getDefault().getIndexerService();
        Assert.isNotNull(service, "Indexer service"); //$NON-NLS-1$
    }

    /**
     * Get the singleton instance of this class.
     * 
     * @return <code>BOMIndexerService</code>.
     */
    public static CDSBOMIndexerService getInstance() {
        return INSTANCE;
    }

    /**
     * Get all Packages in the given BOM file that would have content that would
     * cause generation of a CDS factory.
     * <p>
     * Unlike {@link #getAllPackages(IFile)} this method does not do an indexer
     * query so is more efficient.
     * 
     * @param bomFile
     * @return all package {@link IndexerItem}s, empty array if none found
     *         (could be because the BOM file does not exist any more) OR
     *         contains no items that would cause a need for CDS factories.
     * @since 3.5.5
     * 
     * @param bomFile
     * @return all Packages in the given BOM file that would have content that
     *         would cause generation of a CDS factory.
     */
    public Collection<Package> getAllPackagesWithCDS(IFile bomFile) {
        List<Package> packagesWithCDS = new ArrayList<Package>();

        /*
         * In order to behave like older deprecated getAllPackages() we need to
         * check whether bomFile WOULD have been indexed and ignore if not (it
         * would only have been indexed if certain constriants were met (such
         * as, it has content that actually requires CDS factory toi be created.
         */
        if (CDSBOMIndexer.willGenerateCDS(bomFile)) {

            WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(bomFile);
            if (wc != null) {
                if (wc.getRootElement() instanceof Model) {
                    Model model = (Model) wc.getRootElement();

                    if (CDSBOMIndexer.hasCDSContent(model)) {
                        packagesWithCDS.add(model);
                    }

                    addPackagesWithCDS(model.getPackagedElements(),
                            packagesWithCDS);
                }
            }
        }

        /*
         * DEBUGGING - Ensure that we get the same result as previous deprecated
         * method!
         */
        boolean debug = false;
        if (debug) {
            IndexerItem[] oldMethodReturn = getAllPackages(bomFile);

            ResourceSet resourceSet =
                    XpdResourcesPlugin.getDefault().getEditingDomain()
                            .getResourceSet();

            List<Package> oldMethodList = new ArrayList<Package>();
            for (IndexerItem indexerItem : oldMethodReturn) {
                String uri = indexerItem.getURI();

                EObject eObject =
                        resourceSet.getEObject(URI.createURI(uri), true);
                if (eObject instanceof Package) {
                    oldMethodList.add((Package) eObject);
                }
            }

            if (packagesWithCDS.size() != oldMethodList.size()
                    || !packagesWithCDS.containsAll(oldMethodList)) {
                XpdResourcesPlugin
                        .getDefault()
                        .getLogger()
                        .error("getAllPackagesWithCDS() returned differnet list than old method for: "
                                + bomFile.getProjectRelativePath().toString());
            }
        }

        return packagesWithCDS;
    }

    /**
     * Recursively go thru the list of elements looking for packages, if they
     * contain content that needs CDS factories then add them to the return list
     * of packages.
     * 
     * @param packagedElements
     * @param packagesWithCDS
     */
    private void addPackagesWithCDS(EList<PackageableElement> packagedElements,
            List<Package> packagesWithCDS) {
        for (PackageableElement element : packagedElements) {
            if (element instanceof Package) {
                Package pkg = (Package) element;

                /*
                 * In order to behave like older deprecated getAllPackages() we
                 * need to check whether bomFile WOULD have been indexed and
                 * ignore if not (it would only have been indexed if certain
                 * constriants were met (such as, it has content that actually
                 * requires CDS factory to be created.
                 */
                if (CDSBOMIndexer.hasCDSContent(pkg)) {
                    packagesWithCDS.add(pkg);
                }

                /* Recurs to do nested packages! */
                addPackagesWithCDS(pkg.getPackagedElements(), packagesWithCDS);
            }
        }
    }

    /**
     * Get all {@link IndexerItem Items} Packages indexed for the given BOM
     * file.
     * 
     * @param bomFile
     * @return all package {@link IndexerItem}s, empty array if none found
     *         (could be because the BOM file does not exist any more).
     * @since 3.3
     * @deprecated This method returns info according what has been indexed by
     *             actually looking in the index (so that it can return
     *             <code>null</code> if nothing is being generated for package
     *             because it has no content that requires any cds stuff. Using
     *             {@link #getAllPackagesWithCDS(IFile)} does the same BUT is
     *             much more efficient because it doesn't go to the index.
     */
    @SuppressWarnings("restriction")
    @Deprecated
    public IndexerItem[] getAllPackages(IFile bomFile) {
        Collection<IndexerItem> items = null;
        if (bomFile != null) {
            IndexerItemImpl criteria = new IndexerItemImpl();
            criteria.set(IndexerServiceImpl.ATTRIBUTE_PATH, bomFile
                    .getFullPath() //$NON-NLS-1$
                    .toPortableString());
            criteria.setType(CDSBOMIndexerService.PACKAGE_LITERAL);
            items = service.query(CDSBOMIndexerService.INDEXER_ID, criteria);
        }
        return items != null ? items.toArray(new IndexerItem[items.size()])
                : new IndexerItem[0];
    }

    /**
     * Get all the qualified names of enumeration defined in the given BOM file.
     * 
     * @param resource
     * @return all the names of enumeration defined in the given BOM file.
     * 
     * @since 3.5.10
     */

    public Collection<String> getBOMEnumNamesFaster(IFile bomFile) {
        List<String> returnEnumNames = new ArrayList<String>();

        /*
         * In order to behave like older deprecated getBOMEnumNames() we need to
         * check whether bomFile WOULD have been indexed and ignore if not (it
         * would only have been indexed if certain constriants were met (such
         * as, it has content that actually requires CDS factory toi be created.
         */
        if (CDSBOMIndexer.willGenerateCDS(bomFile)) {

            WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(bomFile);
            if (wc != null) {
                if (wc.getRootElement() instanceof Model) {
                    Model model = (Model) wc.getRootElement();

                    EList<PackageableElement> packagedElements =
                            model.getPackagedElements();

                    addEnumerationNames(packagedElements, returnEnumNames);
                }
            }
        }

        /*
         * DEBUGGING - Ensure that we get the same result as previous deprecated
         * method!
         */
        boolean debug = false;
        if (debug) {
            Collection<String> oldMethodReturn = getBOMEnumNames(bomFile);

            if (returnEnumNames.size() != oldMethodReturn.size()
                    || !returnEnumNames.containsAll(oldMethodReturn)) {
                XpdResourcesPlugin
                        .getDefault()
                        .getLogger()
                        .error("getBOMEnumNamesFaster() returned differnet list than old method for: "
                                + bomFile.getProjectRelativePath().toString());
            }
        }

        return returnEnumNames;
    }

    /**
     * Recursively add the qualified names of any enumerations.
     * 
     * @param packagedElements
     * @param returnEnumNames
     */
    private void addEnumerationNames(
            EList<PackageableElement> packagedElements,
            List<String> returnEnumNames) {

        for (PackageableElement element : packagedElements) {
            if (element instanceof Enumeration) {
                Enumeration en = (Enumeration) element;

                String enumFullyQualifiedName =
                        BOMWorkingCopy.getQualifiedName(en.getQualifiedName());

                returnEnumNames.add(enumFullyQualifiedName);

            } else if (element instanceof Package) {
                /* Recurs for nested packages. */
                addEnumerationNames(((Package) element).getPackagedElements(),
                        returnEnumNames);
            }
        }
    }

    /**
     * Get all the names of enumeration defined in the given BOM file.
     * 
     * @param resource
     * @return all the names of enumeration defined in the given BOM file.
     * @since 3.3
     * @deprecated This method returns info according what has been indexed by
     *             actually looking in the index (so that it can return
     *             <code>null</code> if nothing is being generated for package
     *             because it has no content that requires any cds stuff. Using
     *             {@link #getBOMEnumNamesFaster(IResource)} does the same BUT
     *             is much more efficient because it doesn't go to the index.
     */
    @Deprecated
    @SuppressWarnings("restriction")
    public Collection<String> getBOMEnumNames(IResource resource) {

        ArrayList<String> enumNames = new ArrayList<String>();
        IndexerItemImpl criteria = new IndexerItemImpl();
        criteria.set(IndexerServiceImpl.ATTRIBUTE_PATH, resource.getFullPath()
                .toPortableString());
        criteria.setType("Enumeration"); //$NON-NLS-1$
        Collection<IndexerItem> query =
                service.query(CDSBOMIndexerService.INDEXER_ID, criteria);
        for (IndexerItem indexerItem : query) {
            enumNames.add(indexerItem.get(CDSBOMIndexerService.QUALIFIED_NAME));
        }
        return enumNames;
    }

    /**
     * Get the CDS factory name that will be generated for the given BOM
     * package. If the BOM package will not generate a CDS factory because there
     * is no CDS'able content like classes etc then return null.
     * 
     * @param pkg
     * @return CDS factory name for BOM package or <code>null</code> if no CDS
     *         factory will be generated for it.
     */
    public String getCDSFactoryForPackage(Package pkg) {
        String factoryName = null;

        IFile file = WorkingCopyUtil.getFile(pkg);

        /*
         * In order to behave like older deprecated
         * getCDSFactoryForPackage(packageName) we need to check whether bomFile
         * WOULD have been indexed and ignore if not (it would only have been
         * indexed if certain constriants were met (such as, it has content that
         * actually requires CDS factory toi be created.
         */
        if (file != null && CDSBOMIndexer.willGenerateCDS(file)) {
            if (CDSBOMIndexer.hasCDSContent(pkg)) {
                factoryName = getQualifiedFactoryName(pkg);
            }
        }

        /*
         * DEBUGGING - Ensure that we get the same result as previous deprecated
         * method!
         */
        boolean debug = false;
        if (debug) {
            String oldMethodReturn =
                    getCDSFactoryForPackage(BOMWorkingCopy
                            .getQualifiedPackageName(pkg));

            boolean isSame = true;
            if (factoryName == null) {
                if (oldMethodReturn != null) {
                    isSame = false;
                }
            } else if (!factoryName.equals(oldMethodReturn)) {
                isSame = false;
            }

            if (!isSame) {
                XpdResourcesPlugin
                        .getDefault()
                        .getLogger()
                        .error("getCDSFactoryForPackage() returned different list than old method for: "
                                + pkg.getName());
            }
        }

        return factoryName;
    }

    /**
     * 
     * @param packageName
     * @return
     * @deprecated This method returns info according what has been indexed by
     *             actually looking in the index (so that it can return
     *             <code>null</code> if nothing is being generated for package
     *             because it has no content that requires any cds stuff. Using
     *             {@link #getCDSFactoryForPackage(Package)} does the same BUT
     *             is much more efficient because it doesn't go to the index.
     */
    @SuppressWarnings("restriction")
    @Deprecated
    public String getCDSFactoryForPackage(String packageName) {
        Map<String, String> additionalAttributes =
                new HashMap<String, String>();
        additionalAttributes.put(PACKAGE_NAME, packageName);
        additionalAttributes.put(IndexerServiceImpl.ATTRIBUTE_TYPE,
                CDSBOMIndexerService.PACKAGE_LITERAL);
        IndexerItem criteria =
                new IndexerItemImpl(null, null, null, additionalAttributes);
        Collection<IndexerItem> queryResult =
                service.query(CDSBOMIndexerService.INDEXER_ID, criteria);
        if (!queryResult.isEmpty()) {
            IndexerItem item = queryResult.iterator().next();
            return item.get(CDSBOMIndexerService.CDS_FACTORY_NAME);
        }
        return null;
    }

    /**
     * Get the CDS factory name that <i>would</i> be generated for the given
     * package name <i>if</i> that package were to have CDS factory generated.
     * <p>
     * Unlike {@link #getCDSFactoryForPackage(Package)} it <b>does not make any
     * judgement as to whether the package will generate a CDS factory or
     * not.</b>
     * </p>
     * 
     * @param packageName
     * @return The CDS factory name.
     */
    public String getCDSFactoryName(String packageName) {
        return CDSBOMIndexer.getCDSFactoryName(packageName);
    }

    /**
     * Get the CDS factory name that <i>would</i> be generated for the given
     * package name <i>if</i> that package were to have CDS factory generated.
     * <p>
     * Unlike {@link #getCDSFactoryForPackage(Package)} it <b>does not make any
     * judgement as to whether the package will generate a CDS factory or
     * not.</b>
     * </p>
     * 
     * @param packageName
     * @return The CDS factory name.
     */
    public String getQualifiedFactoryName(Package pkg) {
        return CDSBOMIndexer.getQualifiedFactoryName(pkg);
    }

    /**
     * Get the namespace URI to store for the given package.
     * 
     * @param pkg
     * @return
     */
    public static String getNamespaceUri(Package pkg) {
        return CDSBOMIndexer.getNamespaceUri(pkg);
    }
}
