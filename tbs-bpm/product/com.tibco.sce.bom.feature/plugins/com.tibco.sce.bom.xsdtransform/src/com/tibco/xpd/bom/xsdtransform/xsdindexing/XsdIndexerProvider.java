/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.xsdtransform.xsdindexing;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.xsd.XSDComplexTypeDefinition;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDPackage;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDSimpleTypeDefinition;
import org.eclipse.xsd.XSDTypeDefinition;
import org.eclipse.xsd.util.XSDUtil;

import com.tibco.xpd.bom.xsdtransform.Activator;
import com.tibco.xpd.bom.xsdtransform.XsdStereotypeUtils;
import com.tibco.xpd.bom.xsdtransform.utils.Bom2XsdUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.indexer.WorkingCopyIndexProvider;
import com.tibco.xpd.resources.ui.util.ResourceLocator;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Xsd indexer provider.
 * 
 * @author mtorres
 * 
 */
public class XsdIndexerProvider implements WorkingCopyIndexProvider {

    /**
     * 
     */
    private static final String ID_ATTRIBUTE = "id"; //$NON-NLS-1$

    /**
     * WSDL indexer provider.
     */
    public XsdIndexerProvider() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.indexer.WorkingCopyIndexProvider#getIndexItems
     * (com.tibco.xpd.resources.WorkingCopy)
     */
    @Override
    public Collection<IndexerItem> getIndexItems(WorkingCopy wc) {

        List<IndexerItem> items = new ArrayList<IndexerItem>();
        if (wc != null && !wc.isInvalidFile()) {

            EPackage ep = wc.getWorkingCopyEPackage();
            if (ep != null && ep instanceof XSDPackage) {

                IResource resource = wc.getEclipseResources().get(0);

                IFolder bomToXsdSpecialFolder;
                try {

                    bomToXsdSpecialFolder = getBomToXsdSpecialFolder(resource);
                    /* Index only those files which are in the "bom2xsd" */
                    if (bomToXsdSpecialFolder != null) {

                        EObject root = wc.getRootElement();
                        updateIndex_2(items, root);
                    }
                } catch (CoreException e) {

                    e.printStackTrace();
                }
            }
        }
        return items;
    }

    private IFolder getBomToXsdSpecialFolder(IResource resource)
            throws CoreException {

        if (resource != null) {

            IProject project = resource.getProject();
            IFolder sourceFolder =
                    Bom2XsdUtil.getXsdOutputFolder(project, false, true);

            boolean isPreCompiled = ProjectUtil.isPrecompiledProject(project);

            if (isPreCompiled) {

                IResource targetResource =
                        ResourceLocator
                                .getTargetResource(project, sourceFolder);
                return (IFolder) targetResource;

            }

            return sourceFolder;
        }
        return null;

    }

    private void updateIndex_2(Collection<IndexerItem> items, EObject element) {
        if (element == null) {
            return;
        }
        EList<EObject> list = element.eContents();
        Iterator<EObject> it = list.iterator();
        String uri = XsdUIUtil.getURI(element);

        // This must be a proxy EObject so don't index
        if (uri == null) {
            return;
        }

        String item_id = XsdUIUtil.getAttributeValue(ID_ATTRIBUTE, element);
        if (element instanceof XSDSchema) {
            XSDSchema schema = (XSDSchema) element;
            String name = null;
            if (schema.getElement() != null
                    && schema.getElement().getNodeName() != null) {
                name = schema.getElement().getNodeName();
            }
            String type = XSDUtil.SCHEMA_ELEMENT_TAG;
            String targetNamespace = schema.getTargetNamespace();
            String bomOriginFileName =
                    XsdUIUtil
                            .getDocumentationAttributeValue(XsdUIUtil.BOM_ORIGIN_TAGNAME,
                                    element);
            createResourceItem(items,
                    name,
                    type,
                    uri,
                    item_id,
                    targetNamespace,
                    bomOriginFileName);
        } else if (element instanceof XSDComplexTypeDefinition) {

            XSDComplexTypeDefinition complexTypeDefinition =
                    (XSDComplexTypeDefinition) element;
            /**
             * Do not index the complex type which have been set as anonymous
             * type in the BOM
             * 
             * This would be the case only when an XSD is created from a BOM
             * that is generated from an XSD/WSDL
             */
            if (!isAnonymousTypeDefinition(complexTypeDefinition, item_id)) {
                String name = complexTypeDefinition.getQName();
                String type = XSDUtil.COMPLEXTYPE_ELEMENT_TAG;
                String targetNamespace =
                        complexTypeDefinition.getTargetNamespace();

                String bomOriginFileName =
                        XsdUIUtil
                                .getDocumentationAttributeValue(XsdUIUtil.BOM_ORIGIN_TAGNAME,
                                        complexTypeDefinition.getSchema());

                if (name != null && name.length() > 0) {
                    createResourceItem(items,
                            name,
                            type,
                            uri,
                            item_id,
                            targetNamespace,
                            bomOriginFileName);
                }
            }
        } else if (element instanceof XSDSimpleTypeDefinition) {
            XSDSimpleTypeDefinition simpleTypeDefinition =
                    (XSDSimpleTypeDefinition) element;
            /**
             * Do not index the complex type which have been set as anonymous
             * type in the BOM
             * 
             * This would be the case only when an XSD is created from a BOM
             * that is generated from an XSD/WSDL
             */
            if (!isAnonymousTypeDefinition(simpleTypeDefinition, item_id)) {
                String name = simpleTypeDefinition.getQName();
                String type = XSDUtil.SIMPLETYPE_ELEMENT_TAG;

                String targetNamespace =
                        simpleTypeDefinition.getTargetNamespace();
                String bomOriginFileName =
                        XsdUIUtil
                                .getDocumentationAttributeValue(XsdUIUtil.BOM_ORIGIN_TAGNAME,
                                        simpleTypeDefinition.getSchema());
                ;
                if (name != null && name.length() > 0) {
                    createResourceItem(items,
                            name,
                            type,
                            uri,
                            item_id,
                            targetNamespace,
                            bomOriginFileName);
                }
            }
        }
        // This is required when XSD root elements need to be indexed.
        else if (element instanceof XSDElementDeclaration) {
            EObject elementContainer = element.eContainer();
            if (elementContainer != null
                    && elementContainer instanceof XSDSchema) {
                // Only indexing root elements and not all elements under the
                // schema
                XSDElementDeclaration elementDeclaration =
                        (XSDElementDeclaration) element;
                if (item_id == null) {
                    // This might be a case of anonymous type being imported
                    // from XSD/WSDL
                    XSDTypeDefinition anonymousTypeDefinition =
                            elementDeclaration.getAnonymousTypeDefinition();
                    if (anonymousTypeDefinition != null) {
                        item_id =
                                XsdUIUtil.getAttributeValue(ID_ATTRIBUTE,
                                        anonymousTypeDefinition);
                    } else {
                        // this isn't anonymous definition so must associate the
                        // same BOM type as its type.
                        XSDTypeDefinition typeDefinition =
                                elementDeclaration.getTypeDefinition();
                        if (typeDefinition != null) {
                            item_id =
                                    XsdUIUtil.getAttributeValue(ID_ATTRIBUTE,
                                            typeDefinition);
                        }
                    }
                }
                String name = elementDeclaration.getQName();
                String type = XSDUtil.ELEMENT_ELEMENT_TAG;

                String targetNamespace =
                        elementDeclaration.getTargetNamespace();
                String bomOriginFileName =
                        XsdUIUtil
                                .getDocumentationAttributeValue(XsdUIUtil.BOM_ORIGIN_TAGNAME,
                                        elementDeclaration.getSchema());

                createResourceItem(items,
                        name,
                        type,
                        uri,
                        item_id,
                        targetNamespace,
                        bomOriginFileName);
            }
        }
        while (it.hasNext()) {
            updateIndex_2(items, it.next());
        }
    }

    /**
     * This method returns true if the BOM class associated with the type has
     * xsdIsAnonType set true
     * 
     * @param element
     * @param item_id
     * @return
     */
    private boolean isAnonymousTypeDefinition(XSDTypeDefinition typeDef,
            String item_id) {

        XSDSchema containerSchema = typeDef.getSchema();
        String bomOriginFileName =
                XsdUIUtil
                        .getDocumentationAttributeValue(XsdUIUtil.BOM_ORIGIN_TAGNAME,
                                containerSchema);
        if (bomOriginFileName != null) {
            IFile bomFile =
                    ResourcesPlugin.getWorkspace().getRoot()
                            .getFile(new Path(bomOriginFileName));
            if (bomFile != null && bomFile.isAccessible()) {
                WorkingCopy bomWC = WorkingCopyUtil.getWorkingCopy(bomFile);
                if (bomWC != null && bomWC.getRootElement() != null) {
                    EObject bomObj =
                            bomWC.getRootElement().eResource()
                                    .getEObject(item_id);
                    if (bomObj instanceof PackageableElement) {
                        PackageableElement packageableElement =
                                (PackageableElement) bomObj;
                        Stereotype appliedStereotype =
                                getAppliedStereotype(packageableElement,
                                        XsdStereotypeUtils.XSD_BASED_CLASS);
                        if (appliedStereotype != null) {
                            Object value =
                                    packageableElement
                                            .getValue(appliedStereotype,
                                                    XsdStereotypeUtils.XSD_PROPERTY_IS_ANON_TYPE);
                            if ("true".equals(value)) { //$NON-NLS-1$
                                return true;
                            }
                        }
                    }

                }
            }
        }
        return false;
    }

    /**
     * @param element
     * @param stereotypeName
     * @return
     */
    private Stereotype getAppliedStereotype(PackageableElement element,
            String stereotypeName) {

        List<Stereotype> appliedStereotypes = element.getAppliedStereotypes();
        for (Stereotype stereotype : appliedStereotypes) {
            if (stereotype.getName().equals(stereotypeName)) {
                return stereotype;
            }

        }
        return null;
    }

    /**
     * create an resource item entry
     * 
     * @param name
     * @param uri
     * @param type
     */
    void createResourceItem(Collection<IndexerItem> list, String name,
            String type, String uri, String item_id, String targetNamespace,
            String bomOriginFileName) {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put(Activator.ATTRIBUTE_ITEM_ID, item_id);
        map.put(Activator.ATTRIBUTE_TARGET_NAMESPACE, targetNamespace);
        map.put(Activator.ATTRIBUTE_BOM_ORIGIN_FILENAME, bomOriginFileName);

        IndexerItem item = new IndexerItemImpl(name, type, uri, map);
        list.add(item);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.indexer.WorkingCopyIndexProvider#isAffectingEvent
     * (java.beans.PropertyChangeEvent) Don't need this method as only listening
     * to reload events
     */
    @Override
    public boolean isAffectingEvent(PropertyChangeEvent event) {
        return true;
    }

}
