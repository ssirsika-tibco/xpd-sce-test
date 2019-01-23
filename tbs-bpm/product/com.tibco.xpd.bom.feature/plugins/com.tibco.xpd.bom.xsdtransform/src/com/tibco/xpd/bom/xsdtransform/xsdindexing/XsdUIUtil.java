/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.xsdtransform.xsdindexing;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xsd.XSDAnnotation;
import org.eclipse.xsd.XSDComplexTypeDefinition;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDTypeDefinition;
import org.eclipse.xsd.util.XSDUtil;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.tibco.xpd.bom.xsdtransform.Activator;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.internal.indexer.IndexerServiceImpl;
import com.tibco.xpd.resources.util.ProjectUtil;

/**
 * @author mtorres
 * 
 */
public class XsdUIUtil {

    /** Xsd Indexer provider ID */
    public static final String XSD_INDEXER_ID =
            "com.tibco.xpd.xsd.xsdBomIndexer"; //$NON-NLS-1$

    public static final String BOM_ORIGIN_TAGNAME = "BOMORIGIN";//$NON-NLS-1$

    /**
     * @param modelElement
     * @return
     */
    public static String getURI(EObject modelElement) {
        Resource modelElementResource = modelElement.eResource();
        if (modelElementResource != null) {
            URI uri =
                    modelElementResource
                            .getURI()
                            .appendFragment(modelElementResource.getURIFragment(modelElement));
            String uriToString = uri.toString();
            return uriToString;
        }
        return null;
    }

    /**
     * @param wc
     * @return
     */
    public static String createPath(WorkingCopy wc) {
        IResource resource = wc.getEclipseResources().get(0);
        String path = resource.getProjectRelativePath().toPortableString(); //$NON-NLS-1$
        return path;
    }

    public static String getAttributeValue(String attName, EObject element) {
        String attValue = null;
        if (attName != null) {
            Element ctdElement = null;
            if (element instanceof XSDSchema) {
                XSDSchema xsdSchema = (XSDSchema) element;
                ctdElement = xsdSchema.getElement();
            } else if (element instanceof XSDTypeDefinition) {
                XSDTypeDefinition typeDefinition = (XSDTypeDefinition) element;
                ctdElement = typeDefinition.getElement();
            }
            if (ctdElement != null) {
                attValue = XsdUIUtil.getAttributeValue(attName, ctdElement);
            }
        }
        return attValue;
    }

    private static String getAttributeValue(String attName, Element element) {
        String attValue = null;
        if (element != null) {
            attValue = element.getAttribute(attName);
        }
        return attValue;
    }

    public static String getDocumentationAttributeValue(String attName,
            EObject element) {
        String attValue = null;
        if (attName != null) {
            EList<XSDAnnotation> annotations = null;
            if (element instanceof XSDSchema) {
                XSDSchema schema = (XSDSchema) element;
                annotations = schema.getAnnotations();
            } else if (element instanceof XSDComplexTypeDefinition) {
                XSDComplexTypeDefinition complexTypeDefinition =
                        (XSDComplexTypeDefinition) element;
                annotations = complexTypeDefinition.getAnnotations();
            }
            if (annotations != null) {
                return getDocumentationAttributeValue(attName, annotations);
            }
        }
        return attValue;
    }

    private static String getDocumentationAttributeValue(String attName,
            EList<XSDAnnotation> annotations) {
        if (annotations != null && !annotations.isEmpty()) {
            for (XSDAnnotation annotation : annotations) {
                if (annotation != null) {
                    EList<Element> userInformations =
                            annotation.getUserInformation();
                    if (userInformations != null && !userInformations.isEmpty()) {
                        for (Element userInformation : userInformations) {
                            if (userInformation != null) {
                                Node node = userInformation.getFirstChild();
                                if (node != null) {
                                    String data = node.getNodeValue();
                                    if (data != null) {
                                        String attValue =
                                                XsdUIUtil
                                                        .getBomOriginTagValue(data,
                                                                attName);
                                        if (attValue != null) {
                                            return attValue;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    private static String getBomOriginTagValue(String text, String tagName) {
        if (text != null && text.startsWith(tagName + "::")) { //$NON-NLS-1$
            String tagValue = text.replaceFirst(tagName + "::", "");//$NON-NLS-1$ //$NON-NLS-2$
            return tagValue;
        }
        return null;
    }

    /**
     * This method is to query an xsd element that is present in an XSD
     * generated from the given BOM.
     * 
     * @param xsdId
     *            id of the xsd element to look for
     * @param bomFile
     *            the original BOM file from which this XSD is generated
     * @param isElementRequired
     *            <code>true</code> if an Element type should be searched for,
     *            otherwise it will search for a Simple or Complex type.
     * 
     * @return IndexerItem
     * @since 3.5.10
     **/
    public static IndexerItem queryXsdElement(String xsdId, IFile bomFile,
            boolean isElementRequired) {
        if (xsdId != null && bomFile != null) {
            // Query the indexer
            Map<String, String> additionalAttributes =
                    new HashMap<String, String>();
            additionalAttributes.put(Activator.ATTRIBUTE_ITEM_ID, xsdId);
            additionalAttributes.put(Activator.ATTRIBUTE_BOM_ORIGIN_FILENAME,
                    bomFile.getFullPath().toString());

            IndexerItem criteria =
                    new IndexerItemImpl(null, null, null, additionalAttributes);
            Collection<IndexerItem> xsdResults =
                    XpdResourcesPlugin.getDefault().getIndexerService()
                            .query(XsdUIUtil.XSD_INDEXER_ID, criteria);

            for (IndexerItem item : xsdResults) {
                if (isElementRequired) {
                    if (XSDUtil.ELEMENT_ELEMENT_TAG.equals(item.getType())) {
                        return item;
                    }
                } else {
                    if (XSDUtil.SIMPLETYPE_ELEMENT_TAG.equals(item.getType())
                            || XSDUtil.COMPLEXTYPE_ELEMENT_TAG.equals(item
                                    .getType())) {
                        return item;
                    }
                }
            }

        }
        return null;
    }

    /**
     * This method is to query an xsd element. If the XSD for a particular BOM
     * model needs to be searched then use
     * {@link #queryXsdElement(String, IFile, boolean)}.
     * 
     * @param xsdId
     *            id of the XSD element to look for.
     * @param project
     *            context of the search (will also look for any XSDs in
     *            referenced projects)
     * @param isElementRequired
     *            <code>true</code> if an Element type should be searched for,
     *            otherwise it will search for a Simple or Complex type.
     * 
     * @return IndexerItem
     **/
    public static IndexerItem queryXsdElement(String xsdId, IProject project,
            boolean isElementRequired) {
        if (xsdId != null) {
            // Query the indexer
            Map<String, String> additionalAttributes =
                    new HashMap<String, String>();
            additionalAttributes.put(Activator.ATTRIBUTE_ITEM_ID, xsdId);

            IndexerItem criteria =
                    new IndexerItemImpl(null, null, null, additionalAttributes);
            Collection<IndexerItem> xsdResults =
                    XpdResourcesPlugin.getDefault().getIndexerService()
                            .query(XsdUIUtil.XSD_INDEXER_ID, criteria);
            return getIndexedItemWithinScope(xsdResults,
                    project,
                    isElementRequired);

        }
        return null;
    }

    /**
     * There could be multiple results i.e multiple classes with the same id. We
     * need to ensure we return the one which in the project scope of the given
     * project.
     * 
     * @param xsdResults
     * @param project
     * @param isElementRequired
     * @return
     */
    @SuppressWarnings("restriction")
    private static IndexerItem getIndexedItemWithinScope(
            Collection<IndexerItem> xsdResults, IProject project,
            boolean isElementRequired) {
        Set<IProject> projectSet = new HashSet<IProject>();
        projectSet.add(project);
        Set<IProject> referencingProjectsHierarchy =
                ProjectUtil
                        .getReferencingProjectsHierarchy(project, projectSet);
        Set<String> projectNames = new HashSet<String>();
        for (IProject proj : referencingProjectsHierarchy) {
            projectNames.add(proj.getName());
        }
        for (IndexerItem item : xsdResults) {
            String indexerProject =
                    item.get(IndexerServiceImpl.ATTRIBUTE_PROJECT);
            String typeAttrib = item.getType();
            if (isElementRequired) {
                // If element is required
                if (XSDUtil.ELEMENT_ELEMENT_TAG.equals(typeAttrib)) {
                    if (projectNames.contains(indexerProject)) {
                        return item;
                    }
                }
            } else {
                if (XSDUtil.SIMPLETYPE_ELEMENT_TAG.equals(typeAttrib)
                        || XSDUtil.COMPLEXTYPE_ELEMENT_TAG.equals(typeAttrib)) {
                    if (projectNames.contains(indexerProject)) {
                        return item;
                    }
                }
            }
        }
        return null;
    }

    /**
     * This method is to query an xsd element
     * 
     * @param xsdId
     * 
     * @return IndexerItem
     **/
    public static IndexerItem queryXsdElementForNsAndName(String targetNS,
            String complexTypeName) {
        // Query the indexer
        Map<String, String> additionalAttributes =
                new HashMap<String, String>();
        additionalAttributes
                .put(Activator.ATTRIBUTE_TARGET_NAMESPACE, targetNS);
        additionalAttributes.put(IndexerServiceImpl.ATTRIBUTE_NAME,
                complexTypeName);
        IndexerItem criteria =
                new IndexerItemImpl(null, null, null, additionalAttributes);
        Collection<IndexerItem> xsdResults =
                XpdResourcesPlugin.getDefault().getIndexerService()
                        .query(XsdUIUtil.XSD_INDEXER_ID, criteria);
        if (xsdResults != null && xsdResults.iterator().hasNext()) {
            return xsdResults.iterator().next();
        }
        return null;
    }

    /**
     * This method is to query the bom origin filename for a given xsd filename
     * 
     * @param xsdFileName
     *            The XSD file name must be project relative path so it must be
     *            of the format "projectname/foldername/xsdfilename.xsd"
     * 
     * @return String the origin filename
     **/
    public static String queryBomOriginFilename(String xsdFileName) {
        if (xsdFileName != null) {
            // Query the indexer
            Map<String, String> additionalAttributes =
                    new HashMap<String, String>();
            additionalAttributes.put(IndexerServiceImpl.ATTRIBUTE_PATH,
                    xsdFileName);
            IndexerItem criteria =
                    new IndexerItemImpl(null, XSDUtil.SCHEMA_ELEMENT_TAG, null,
                            additionalAttributes);
            Collection<IndexerItem> xsdResults =
                    XpdResourcesPlugin.getDefault().getIndexerService()
                            .query(XsdUIUtil.XSD_INDEXER_ID, criteria);
            if (xsdResults != null && xsdResults.iterator().hasNext()) {
                // Select the first result
                IndexerItem indexerItem = xsdResults.iterator().next();
                if (indexerItem != null) {
                    return indexerItem
                            .get(Activator.ATTRIBUTE_BOM_ORIGIN_FILENAME);
                }
            }
        }
        return null;
    }

    /**
     * Provides the name of the BOM file which corresponds to the
     * 
     * @param targetNameSpace
     * @return
     */
    public static String queryBomOriginNameProvidedTgtNamespace(
            String targetNameSpace) {
        if (null != targetNameSpace) {
            Map<String, String> additionalAttributes =
                    new HashMap<String, String>();
            additionalAttributes.put("target_namespace", targetNameSpace); //$NON-NLS-1$
            IndexerItem criteria =
                    new IndexerItemImpl(null, XSDUtil.SCHEMA_ELEMENT_TAG, null,
                            additionalAttributes);

            Collection<IndexerItem> xsdResults =
                    XpdResourcesPlugin.getDefault().getIndexerService()
                            .query(XsdUIUtil.XSD_INDEXER_ID, criteria);

            if (xsdResults != null && xsdResults.iterator().hasNext()) {
                // Select the first result
                IndexerItem indexerItem = xsdResults.iterator().next();
                if (indexerItem != null) {
                    return indexerItem
                            .get(Activator.ATTRIBUTE_BOM_ORIGIN_FILENAME);
                }
            }
        }
        return null;
    }

    /**
     * This method is to query the list of generated xsds for a given bom
     * filename
     * 
     * @param xsdFileName
     * 
     * @return List of generated xsds
     **/
    public static Set<String> queryGeneratedXsdsFromBom(String bomFileName) {
        if (bomFileName != null) {
            // Query the indexer
            Map<String, String> additionalAttributes =
                    new HashMap<String, String>();
            additionalAttributes.put(Activator.ATTRIBUTE_BOM_ORIGIN_FILENAME,
                    bomFileName);
            IndexerItem criteria =
                    new IndexerItemImpl(null, XSDUtil.SCHEMA_ELEMENT_TAG, null,
                            additionalAttributes);
            Collection<IndexerItem> xsdResults =
                    XpdResourcesPlugin.getDefault().getIndexerService()
                            .query(XsdUIUtil.XSD_INDEXER_ID, criteria);
            if (xsdResults != null && !xsdResults.isEmpty()) {
                Set<String> generatedXsds = new HashSet<String>();
                for (IndexerItem indexerItem : xsdResults) {
                    if (indexerItem != null) {
                        String xsdPath =
                                indexerItem
                                        .get(IndexerServiceImpl.ATTRIBUTE_PATH);
                        if (xsdPath != null) {
                            generatedXsds.add(xsdPath);
                        }
                    }
                }
                return generatedXsds;
            }
        }
        return Collections.emptySet();
    }

    /**
     * Returns the generated XSDs for a given BOM file name within the project
     * provided.
     * 
     * @see XsdUIUtil#queryGeneratedXsdsFromBom(String) ; just that this method
     *      returns xsds within the provided project only.
     * @param project
     * @param bomFileName
     * @return
     */
    public static Set<String> queryGeneratedXsdsFromBom(IProject project,
            String bomFileName) {
        if (bomFileName != null) {
            // Query the indexer
            Map<String, String> additionalAttributes =
                    new HashMap<String, String>();
            additionalAttributes.put(Activator.ATTRIBUTE_BOM_ORIGIN_FILENAME,
                    bomFileName);
            additionalAttributes.put(IndexerServiceImpl.ATTRIBUTE_PROJECT,
                    project.getName());
            IndexerItem criteria =
                    new IndexerItemImpl(null, XSDUtil.SCHEMA_ELEMENT_TAG, null,
                            additionalAttributes);
            Collection<IndexerItem> xsdResults =
                    XpdResourcesPlugin.getDefault().getIndexerService()
                            .query(XsdUIUtil.XSD_INDEXER_ID, criteria);
            if (xsdResults != null && !xsdResults.isEmpty()) {
                Set<String> generatedXsds = new HashSet<String>();
                for (IndexerItem indexerItem : xsdResults) {
                    if (indexerItem != null) {
                        String xsdPath =
                                indexerItem
                                        .get(IndexerServiceImpl.ATTRIBUTE_PATH);
                        if (xsdPath != null) {
                            generatedXsds.add(xsdPath);
                        }
                    }
                }
                return generatedXsds;
            }
        }
        return Collections.emptySet();
    }

    /**
     * Returns the generated XSDs for a given project provided.
     * 
     * @see XsdUIUtil#queryGeneratedXsdsFromBom(String) ; just that this method
     *      returns xsds within the provided project only.
     * @param project
     * @return
     */
    public static Set<String> queryGeneratedXsdsForProject(IProject project) {
        if (project != null) {
            // Query the indexer
            Map<String, String> additionalAttributes =
                    new HashMap<String, String>();
            additionalAttributes.put(IndexerServiceImpl.ATTRIBUTE_PROJECT,
                    project.getName());
            IndexerItem criteria =
                    new IndexerItemImpl(null, XSDUtil.SCHEMA_ELEMENT_TAG, null,
                            additionalAttributes);
            Collection<IndexerItem> xsdResults =
                    XpdResourcesPlugin.getDefault().getIndexerService()
                            .query(XsdUIUtil.XSD_INDEXER_ID, criteria);
            if (xsdResults != null && !xsdResults.isEmpty()) {
                Set<String> generatedXsds = new HashSet<String>();
                for (IndexerItem indexerItem : xsdResults) {
                    if (indexerItem != null) {
                        String xsdPath =
                                indexerItem
                                        .get(IndexerServiceImpl.ATTRIBUTE_PATH);
                        if (xsdPath != null) {
                            generatedXsds.add(xsdPath);
                        }
                    }
                }
                return generatedXsds;
            }
        }
        return Collections.emptySet();
    }

}
