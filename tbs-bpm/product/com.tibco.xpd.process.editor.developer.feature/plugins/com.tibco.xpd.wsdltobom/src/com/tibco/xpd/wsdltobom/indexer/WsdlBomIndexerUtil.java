/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.wsdltobom.indexer;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.xsd.XSDPackage;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.internal.indexer.IndexerServiceImpl;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * @author rsomayaj
 * 
 */
public class WsdlBomIndexerUtil {

    /** Xsd Indexer provider ID */
    public static final String WSDL_BOM_INDEXER_ID =
            "com.tibco.xpd.bomgen.wsdlBomIndexer"; //$NON-NLS-1$

    /**
     * 
     * @param targetNamespace
     * @param typeName
     * @param sourceFileForType
     *            The file in which the given TYPE is defined (which might be
     *            the WSDL itself if type is in inline schema OR may be an XSD
     *            if the type is in external imported schema).
     * @return indexerItem for BOM element related to given WSDL / XSD type.
     */
    public static IndexerItem queryBOMElementUserDefinedWSDLForGivenNameAndNS(
            String targetNamespace, String typeName, boolean elementRequired,
            IFile sourceFileForType) {
        Map<String, String> additionalAttributes =
                new HashMap<String, String>();
        additionalAttributes
                .put(WsdlBomIndexerProvider.ATTRIBUTE_XSD_NAMESPACE,
                        targetNamespace);
        additionalAttributes
                .put(WsdlBomIndexerProvider.ATTRIBUTE_XSD_ELEMENT_NAME,
                        typeName);

        /*
         * We MUST check that we are picking up the BOM for the WSDL concerned
         * (not just any old copy of the WSDL in some other unreferenced
         * project.
         */
        if (sourceFileForType != null) {
            additionalAttributes
                    .put(WsdlBomIndexerProvider.ATTRIBUTE_WSDL_FILE_NAME,
                            sourceFileForType.getFullPath().toPortableString());
        }

        IndexerItem criteria =
                new IndexerItemImpl(null, null, null, additionalAttributes);
        Collection<IndexerItem> xsdResults =
                XpdResourcesPlugin.getDefault().getIndexerService()
                        .query(WSDL_BOM_INDEXER_ID, criteria);

        if (xsdResults != null) {
            /*
             * If there is only one result then return that
             */
            if (xsdResults.size() == 1) {
                return xsdResults.iterator().next();
            } else {
                for (IndexerItem indexerItem : xsdResults) {
                    // If part is element declaration
                    if (elementRequired) {

                        if (XSDPackage.eINSTANCE
                                .getXSDElementDeclaration()
                                .getName()
                                .equals(indexerItem.get(WsdlBomIndexerProvider.ATTRIBUTE_DERIVED_FROM_TYPE))) {
                            return indexerItem;
                        }
                    }

                    // If part is of type definition
                    else if (XSDPackage.eINSTANCE
                            .getXSDComplexTypeDefinition()
                            .getName()
                            .equals(indexerItem.get(WsdlBomIndexerProvider.ATTRIBUTE_DERIVED_FROM_TYPE))) {
                        return indexerItem;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 
     * @param targetNamespace
     * @param typeName
     * @elementRequired
     * @return
     * @deprecated <b>This method is provided ONLY to get us over a
     *             change-in-API hump whilst xpdl2bpel catches up with new
     *             version of method that takes the source WSDL/XSD file as
     *             input as well - see
     *             {@link #queryBOMElementUserDefinedWSDLForGivenNameAndNS(String, String, boolean, IFile)}
     */
    @Deprecated
    public static IndexerItem queryBOMElementUserDefinedWSDLForGivenNameAndNS(
            String targetNamespace, String typeName, boolean elementRequired) {
        return queryBOMElementUserDefinedWSDLForGivenNameAndNS(targetNamespace,
                typeName,
                elementRequired,
                null);
    }

    /**
     * @param bomFilePath
     * @param typeName
     * @param isPartReferringToElementDeclaration
     * @return
     */
    public static IndexerItem queryElementIdForGivenBOMFileAndTypeName(
            String bomFilePath, String typeName, boolean isAutoGeneratedWSDL,
            boolean isPartReferringToElementDeclaration) {

        Map<String, String> additionalAttributes =
                new HashMap<String, String>();
        additionalAttributes
                .put(WsdlBomIndexerProvider.ATTRIBUTE_BOM_FILE_NAME,
                        bomFilePath);
        additionalAttributes
                .put(WsdlBomIndexerProvider.ATTRIBUTE_XSD_ELEMENT_NAME,
                        typeName);

        if (isAutoGeneratedWSDL) {
            /*
             * XPD-4838: for auto generated wsdls look only for classes from the
             * indexer ignoring the TLEs
             */
            if (isPartReferringToElementDeclaration) {
                additionalAttributes
                        .put(WsdlBomIndexerProvider.ATTRIBUTE_DERIVED_FROM_TYPE,
                                WsdlBomIndexerProvider.TYPE_OR_DERIVED_FROM_TYPE_TLE);
            } else {
                additionalAttributes
                        .put(WsdlBomIndexerProvider.ATTRIBUTE_DERIVED_FROM_TYPE,
                                UMLPackage.eINSTANCE.getClass_().getName());
            }

        } else {

            if (isPartReferringToElementDeclaration) {
                additionalAttributes
                        .put(WsdlBomIndexerProvider.ATTRIBUTE_DERIVED_FROM_TYPE,
                                XSDPackage.eINSTANCE.getXSDElementDeclaration()
                                        .getName());
            } else {
                additionalAttributes
                        .put(WsdlBomIndexerProvider.ATTRIBUTE_DERIVED_FROM_TYPE,
                                XSDPackage.eINSTANCE
                                        .getXSDComplexTypeDefinition()
                                        .getName());
            }

        }

        IndexerItem criteria =
                new IndexerItemImpl(null, null, null, additionalAttributes);
        Collection<IndexerItem> xsdResults =
                XpdResourcesPlugin.getDefault().getIndexerService()
                        .query(WSDL_BOM_INDEXER_ID, criteria);

        if (xsdResults != null && xsdResults.iterator().hasNext()) {
            return xsdResults.iterator().next();
        }
        return null;
    }

    /**
     * @param targetNamespace
     * @param typeName
     * @param wsdlFile
     * @return
     */
    @SuppressWarnings("restriction")
    public static IndexerItem queryBOMElementForGeneratedWSDL(
            String targetNamespace, String typeName, IFile wsdlFile) {
        Map<String, String> additionalAttributes =
                new HashMap<String, String>();
        additionalAttributes
                .put(WsdlBomIndexerProvider.ATTRIBUTE_XSD_NAMESPACE,
                        targetNamespace);
        additionalAttributes
                .put(WsdlBomIndexerProvider.ATTRIBUTE_XSD_ELEMENT_NAME,
                        typeName);
        IndexerItem criteria =
                new IndexerItemImpl(null, null, null, additionalAttributes);
        Collection<IndexerItem> xsdResults =
                XpdResourcesPlugin.getDefault().getIndexerService()
                        .query(WSDL_BOM_INDEXER_ID, criteria);

        if (wsdlFile != null && wsdlFile.isAccessible()) {
            final String wsdlFullPath =
                    wsdlFile.getFullPath().toPortableString();

            for (IndexerItem indexerItem : xsdResults) {
                String path =
                        indexerItem.get(IndexerServiceImpl.ATTRIBUTE_PATH);
                if (path.equals(wsdlFullPath)) {
                    return indexerItem;
                }
            }
        }
        return null;
    }

    /***
     * For a given wsdl full path string return all the BOM's referenced(auto
     * generated as well as user defined) from that wsdl
     * 
     * @param wsdlOrXsd
     * @return All of the BOMs related to the given WSDL or XSD.
     */
    public static Set<IResource> queryBOMsForWSDL(IResource wsdlOrXsd) {

        /**
         * XPD-5767: the wsdlBomIndex contains entries for each type or element
         * generated from a WSDL (inline schema) or an XSD along with the BOM
         * file that was generated from that type or taht type was generated
         * from.
         * 
         * In the situation where a WSDL _only_ imports a schema (as a wsdl
         * import) without having it's own inline schema (or an import xsd from
         * its own inline schema BUT no types in it's own schema) then there
         * will be NO entries in wsdlBomIndex for the WSDL itself - only the
         * imported schema.
         * 
         * Therefore in order to accurately get 'all the BOMs related to a WSDL'
         * we need to look in the index for the BOMs related to all of the XSD's
         * that are related.
         * 
         * So we repeatedly query index for the base WSDL/XSD and all of it's
         * dependencies (whcih will be its imported/included schemas).
         */
        Set<IResource> fileAndDependencies = new LinkedHashSet<IResource>();
        fileAndDependencies.add(wsdlOrXsd);

        /*
         * get all the dependent WSDL/XSD files in 'fileAndDependencies'
         */
        fileAndDependencies.addAll(WorkingCopyUtil
                .getDeepDependencies(wsdlOrXsd));

        Set<IResource> referencedBOMsFromWsdl = new LinkedHashSet<IResource>();

        for (IResource fileOrDependency : fileAndDependencies) {
            Map<String, String> additionalAttributes =
                    new HashMap<String, String>();

            String wsdlOrXsdPath =
                    fileOrDependency.getFullPath().toPortableString();

            additionalAttributes
                    .put(WsdlBomIndexerProvider.ATTRIBUTE_WSDL_FILE_NAME,
                            wsdlOrXsdPath);

            IndexerItem criteria =
                    new IndexerItemImpl(null, null, null, additionalAttributes);

            Collection<IndexerItem> bomResults =
                    XpdResourcesPlugin
                            .getDefault()
                            .getIndexerService()
                            .query(WsdlBomIndexerUtil.WSDL_BOM_INDEXER_ID,
                                    criteria);

            for (IndexerItem indexerItem : bomResults) {
                if (null != indexerItem) {
                    String bomFullPathName =
                            indexerItem
                                    .get(WsdlBomIndexerProvider.ATTRIBUTE_BOM_FILE_NAME);
                    if (null != bomFullPathName) {
                        referencedBOMsFromWsdl.add(ResourcesPlugin
                                .getWorkspace().getRoot()
                                .getFile(new Path(bomFullPathName)));
                    }
                }
            }
        }
        return referencedBOMsFromWsdl;
    }
}
