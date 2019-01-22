/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.bom.xsdtransform.internal;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.xsd.XSDAttributeDeclaration;
import org.eclipse.xsd.XSDAttributeGroupDefinition;
import org.eclipse.xsd.XSDComplexTypeContent;
import org.eclipse.xsd.XSDComplexTypeDefinition;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDModelGroup;
import org.eclipse.xsd.XSDNamedComponent;
import org.eclipse.xsd.XSDParticle;
import org.eclipse.xsd.XSDParticleContent;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDSchemaContent;
import org.eclipse.xsd.XSDSimpleTypeDefinition;
import org.eclipse.xsd.XSDTypeDefinition;

import com.tibco.xpd.bom.xsdtransform.XsdStereotypeUtils;
import com.tibco.xpd.bom.xsdtransform.api.XSDSchemaResolver;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
import com.tibco.xpd.bom.xsdtransform.exports.template.TransformHelper;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.indexer.IndexerService;
import com.tibco.xpd.resources.indexer.WorkingCopyIndexProvider;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.wsdl.Activator;

/**
 * Indexes the inline-schemas in a WSDL. This is used during the merging of BOMs
 * generated from inline schemas with the same target namespace.
 * <p>
 * This class also provides some helper static methods to interrogate the
 * indexer.
 * </p>
 * 
 * @author njpatel
 * @since 4 Jul 2011
 */
public class WsdlSchemaIndexManager implements WorkingCopyIndexProvider {

    private static final String INDEXER_ID =
            "com.tibco.xpd.bom.xsdtransform.wsdlSchemaIndexer"; //$NON-NLS-1$

    private enum ElementType {
        COMPLEX, SIMPLE, ATTRIBUTE, ELEMENT, ATTR_GROUP;
    }

    private static final String ATT_TNS = "tns"; //$NON-NLS-1$

    /**
     * Wsdl schema indexer. Used during merging of BOMs generated from multiple
     * schemas with the same target namespace.
     */
    public WsdlSchemaIndexManager() {
    }

    /**
     * @see com.tibco.xpd.resources.indexer.WorkingCopyIndexProvider#getIndexItems(com.tibco.xpd.resources.WorkingCopy)
     * 
     * @param wc
     * @return
     */
    @Override
    public Collection<IndexerItem> getIndexItems(WorkingCopy wc) {
        if (wc != null) {
            EObject element = wc.getRootElement();

            if (element instanceof Definition) {
                IResource resource = wc.getEclipseResources().get(0);
                if (isInSpecialFolder(resource)) {
                    return getIndexItems((Definition) element);
                }
            }
        }
        return new ArrayList<IndexerItem>(0);
    }

    /**
     * Get all items to index from the given WSDL Definition.
     * 
     * @param element
     * @return collection of items to index, empty list if none.
     */
    private Collection<IndexerItem> getIndexItems(Definition def) {

        List<IndexerItem> items = new ArrayList<IndexerItem>();

        XSDSchemaResolver schemaResolver = XSDSchemaResolver.getInstance();
        Set<XSDSchema> referencedSchemas =
                schemaResolver.getReferencedSchemas(def);

        for (XSDSchema schema : referencedSchemas) {

            String tns = schema.getTargetNamespace();
            for (XSDSchemaContent content : schema.getContents()) {

                if (content instanceof XSDNamedComponent) {

                    Map<String, String> additional =
                            new HashMap<String, String>();
                    additional.put(ATT_TNS, tns);
                    ElementType type = getType(content);

                    IndexerItemImpl item =
                            new IndexerItemImpl(
                                    ((XSDNamedComponent) content).getName(),
                                    type != null ? type.toString() : "", EcoreUtil //$NON-NLS-1$
                                            .getURI(content).toString(),
                                    additional);

                    if (ElementType.COMPLEX.equals(type)) {

                        items.add(item);
                        XSDComplexTypeDefinition compTypeDef =
                                (XSDComplexTypeDefinition) content;
                        TreeIterator<EObject> complexTypesContents =
                                compTypeDef.eAllContents();
                        while (complexTypesContents.hasNext()) {

                            EObject elemNode = complexTypesContents.next();
                            if (elemNode instanceof XSDElementDeclaration) {

                                XSDElementDeclaration xsdElementDeclaration =
                                        (XSDElementDeclaration) elemNode;
                                if (null != xsdElementDeclaration
                                        .getAnonymousTypeDefinition()) {

                                    inspectElementsForAnonymousTypes((XSDElementDeclaration) elemNode,
                                            items,
                                            additional);
                                }
                            }
                        }
                    } else if (ElementType.ELEMENT.equals(type)) {

                        /**
                         * XPD-7337: Deleted the fix for XPD-7132 (and XPD-5167)
                         * as I found that was causing other regressions.
                         * Instead of starting to index the simple types, I
                         * thought it was a better solution to not search for
                         * the simple types in the indexer (as they are not
                         * being indexed anyways). See jira for more details.
                         */
                        inspectElementsForAnonymousTypes((XSDElementDeclaration) content,
                                items,
                                additional);
                    } else {
                        items.add(item);
                    }

                }
            }
        }

        return items;
    }

    /**
     * Elements that contain anonymous ComplexTypes and/or SimpleTypes will have
     * corresponding Classes and PrimitiveTypes in BOM. Therefore these
     * anonymous types need to be indexed.
     * 
     * @param element
     * @param items
     * @param additional
     */
    private void inspectElementsForAnonymousTypes(
            XSDElementDeclaration element, List<IndexerItem> items,
            Map<String, String> additional) {

        XSDTypeDefinition anonDef = element.getAnonymousTypeDefinition();

        if (anonDef instanceof XSDComplexTypeDefinition) {

            IndexerItemImpl item =
                    new IndexerItemImpl(
                            ((XSDNamedComponent) element).getName(),
                            ElementType.ELEMENT.toString(), getURI(element),
                            additional);

            items.add(item);

            XSDComplexTypeDefinition anonCT =
                    (XSDComplexTypeDefinition) anonDef;
            XSDComplexTypeContent content = anonCT.getContent();

            if (content instanceof XSDParticle) { // sequence

                XSDParticle part = (XSDParticle) content;
                if (part.getElement().getLocalName().equals("sequence") || part.getElement().getLocalName().equals("choice")) { //$NON-NLS-1$ //$NON-NLS-2$

                    XSDParticleContent content2 = part.getContent();
                    if (content2 instanceof XSDModelGroup) {
                        /*
                         * Will be List of Particles (element,choice, group etc)
                         */
                        XSDModelGroup mGrp = (XSDModelGroup) content2;
                        EList<XSDParticle> contents = mGrp.getContents();
                        for (XSDParticle p : contents) {

                            XSDParticleContent content3 = p.getContent();
                            if (content3 instanceof XSDElementDeclaration) {
                                /*
                                 * Recurse in case there are more embedded
                                 * anonymous types Need to index this element
                                 * Add element to Indexer item list
                                 */
                                inspectElementsForAnonymousTypes((XSDElementDeclaration) content3,
                                        items,
                                        additional);
                            }
                        }
                    }
                }
            }
        }

    }

    /**
     * Get the URI of the given EObject.
     * 
     * @param eo
     * @return
     */
    private String getURI(EObject eo) {
        if (eo != null && eo.eResource() != null) {
            return EcoreUtil.getURI(eo).toString();
        }
        return ""; //$NON-NLS-1$

    }

    /**
     * Get the index item type of the given element.
     * 
     * @param item
     * @return ElementType if the item is a recognised type, <code>null</code>
     *         otherwise.
     */
    private ElementType getType(XSDSchemaContent item) {
        ElementType type = null;

        if (item instanceof XSDComplexTypeDefinition) {
            type = ElementType.COMPLEX;
        } else if (item instanceof XSDSimpleTypeDefinition) {
            type = ElementType.SIMPLE;
        } else if (item instanceof XSDElementDeclaration) {
            type = ElementType.ELEMENT;
        } else if (item instanceof XSDAttributeDeclaration) {
            type = ElementType.ATTRIBUTE;
        } else if (item instanceof XSDAttributeGroupDefinition) {
            type = ElementType.ATTR_GROUP;
        }

        return type;
    }

    @Override
    public boolean isAffectingEvent(PropertyChangeEvent event) {
        return WorkingCopy.CHANGED.equals(event.getPropertyName())
                || WorkingCopy.PROP_RELOADED.equals(event.getPropertyName())
                || WorkingCopy.PROP_DEPENDENCY_CHANGED.equals(event
                        .getPropertyName());
    }

    /**
     * Remove all redundant elements from the given generated BOM. This will use
     * the information from the indexer to determine which elements are expected
     * in the model.
     * 
     * @param model
     * @return <code>true</code> if the model has changed.
     */
    public static boolean removeRedundantElements(Model model) {
        if (model != null) {
            Collection<PackageableElement> elements =
                    getRedundantElements(model);

            if (!elements.isEmpty()) {

                // Remove all stereotypes applied to the elements before
                // removing the elements from the model
                for (PackageableElement element : elements) {

                    for (Stereotype sType : element.getAppliedStereotypes()) {
                        element.unapplyStereotype(sType);
                    }

                    // Also remove stereotype application of all element
                    // contents of the packageable element
                    for (EObject eo : element.eContents()) {
                        if (eo instanceof Element) {
                            Element child = (Element) eo;
                            for (Stereotype sType : child
                                    .getAppliedStereotypes()) {
                                child.unapplyStereotype(sType);
                            }
                        }
                    }
                }

                return model.getPackagedElements().removeAll(elements);
            }
        }

        return false;
    }

    /**
     * Get the elements that are redundant from the generated BOM. This will
     * return elements that do not appear in the WSDL Schema indexer for a given
     * target namespace.
     * 
     * @param model
     * @return collection of elements that are not present in the schema any
     *         more. Empty collection if there are no redundant elements.
     */
    private static Collection<PackageableElement> getRedundantElements(
            Model model) {
        EList<PackageableElement> elementsToRemove =
                new BasicEList<PackageableElement>();
        String tns = XSDUtil.getXSDNamespaceFromPackage(model);
        if (tns != null) {
            for (EObject eo : model.eContents()) {

                if (eo instanceof PackageableElement
                        && eo.eContainer() instanceof Package) {
                    boolean isValid = true;

                    if (eo instanceof Class) {
                        isValid =
                                WsdlSchemaIndexManager
                                        .isSchemaElementForClassPresent(tns,
                                                (org.eclipse.uml2.uml.Class) eo);

                        if (!isValid) {
                            // Check if this is a containment class
                            isValid =
                                    isContainmentClass(tns,
                                            (Class) eo,
                                            new HashSet<Class>());
                        }
                        /**
                         * XPD-7337: We do not index primitive types, so why
                         * look for them! If we look for them, then when the
                         * second wsdl referring to the same schema is merged,
                         * because the primitive type was not indexed in the
                         * first place it can't find and so it removes from the
                         * bom. And when the new elements are copied into the
                         * bom it may not add this deleted type. See XPD-5167
                         * for an example of this problem situation.
                         */
                        // } else if (eo instanceof PrimitiveType) {
                        // isValid =
                        // WsdlSchemaIndexManager
                        // .isSchemaElementForPrimitivePresent(tns,
                        // (PrimitiveType) eo);
                    } else if (eo instanceof Enumeration) {
                        isValid =
                                WsdlSchemaIndexManager
                                        .isSchemaElementForEnumerationPresent(tns,
                                                (Enumeration) eo);
                    }

                    if (!isValid) {
                        elementsToRemove.add((PackageableElement) eo);
                    }
                }
            }

            /*
             * Now process any associations that need to be removed
             */
            EList<Association> assocsToRemove = new BasicEList<Association>();

            for (EObject eo : model.eContents()) {
                if (eo instanceof Association) {
                    Association assoc = (Association) eo;
                    for (Type type : assoc.getEndTypes()) {
                        if (elementsToRemove.contains(type)) {
                            assocsToRemove.add(assoc);
                            break;
                        }
                    }
                }
            }
            elementsToRemove.addAll(assocsToRemove);
        }
        return elementsToRemove;
    }

    /**
     * Check if the source schema element for the given generated {@link Class}
     * is present (uses this indexer).
     * 
     * @param tns
     *            schema target namespace
     * @param clz
     *            BOM Class
     * @return <code>true</code> if the schema element is still present
     *         (indexed).
     */
    public static boolean isSchemaElementForClassPresent(String tns, Class clz) {

        if (tns != null && clz != null) {
            Collection<IndexerItem> items = null;
            /*
             * Use the original schema element name to search the indexer
             */
            Stereotype st =
                    XsdStereotypeUtils.getAppliedXsdStereotype(clz.getModel(),
                            clz,
                            XsdStereotypeUtils.XSD_BASED_CLASS);
            if (st != null) {
                String name =
                        (String) clz.getValue(st,
                                XsdStereotypeUtils.XSD_PROPERTY_NAME);

                if (name != null) {
                    items = getIndexerItem(tns, name);
                } else if (TransformHelper.isAnonymousTopLevelElement(clz)) {
                    name = TransformHelper.getAnonymousTopLevelElementName(clz);
                    if (name != null) {
                        items = getIndexerItem(tns, name);
                    }
                }
            }

            if (items != null) {
                for (IndexerItem item : items) {
                    if (item.getType() != null && item.getType().length() > 0) {
                        switch (ElementType.valueOf(item.getType())) {
                        case COMPLEX:
                        case ELEMENT:
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * Check if the given class is a containment class.
     * 
     * @param tns
     *            target namespace of the source schema
     * @param clz
     *            class to check
     * @param alreadyProcessed
     *            temporary cache to store all the classes that have been
     *            processed. This is required to avoid cyclic processing when
     *            dealing with a possible chain of containment classes.
     * @return <code>true</code> if this is a containment class.
     */
    private static boolean isContainmentClass(String tns, Class clz,
            Set<Class> alreadyProcessed) {

        for (Association assoc : clz.getAssociations()) {
            alreadyProcessed.add(clz);

            for (Type type : assoc.getEndTypes()) {
                if (type != clz && type instanceof Class) {
                    if (isSchemaElementForClassPresent(tns, (Class) type)) {
                        return true;
                    } else {
                        if (!alreadyProcessed.contains(type)) {
                            return isContainmentClass(tns,
                                    (Class) type,
                                    alreadyProcessed);
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * Check if the source schema element for the given generated
     * {@link PrimitiveType} is present (uses this indexer).
     * 
     * @param tns
     *            schema target namespace
     * @param pType
     *            BOM PrimitiveType
     * @return <code>true</code> if the schema element is still present
     *         (indexed).
     */
    public static boolean isSchemaElementForPrimitivePresent(String tns,
            PrimitiveType pType) {

        if (tns != null && pType != null) {
            Stereotype st =
                    XsdStereotypeUtils
                            .getAppliedXsdStereotype(pType.getModel(),
                                    pType,
                                    XsdStereotypeUtils.XSD_BASED_PRIMITIVETYPE);
            if (st != null) {
                String name =
                        (String) pType
                                .getValue(st,
                                        XsdStereotypeUtils.XSD_PROPERTY_SIMPLETYPE_NAME);
                boolean isAnonType =
                        (Boolean) pType.getValue(st,
                                XsdStereotypeUtils.XSD_PROPERTY_IS_ANON_TYPE);
                if (isAnonType) {
                    return true;
                }
                if (name != null) {
                    Collection<IndexerItem> items = getIndexerItem(tns, name);
                    if (items != null) {
                        for (IndexerItem item : items) {
                            if (item.getType() != null
                                    && item.getType().length() > 0) {
                                switch (ElementType.valueOf(item.getType())) {
                                case SIMPLE:
                                case ATTRIBUTE:
                                case ELEMENT:
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Check if the source schema element for the given generated
     * {@link Enumeration} is present (uses this indexer).
     * 
     * @param tns
     *            schema target namespace
     * @param pType
     *            BOM Enumeration
     * @return <code>true</code> if the schema element is still present
     *         (indexed).
     */
    public static boolean isSchemaElementForEnumerationPresent(String tns,
            Enumeration pType) {

        if (tns != null && pType != null) {
            Stereotype st =
                    XsdStereotypeUtils
                            .getAppliedXsdStereotype(pType.getModel(),
                                    pType,
                                    XsdStereotypeUtils.XSD_BASED_PRIMITIVETYPE);
            if (st != null) {
                String name =
                        (String) pType
                                .getValue(st,
                                        XsdStereotypeUtils.XSD_PROPERTY_SIMPLETYPE_NAME);
                boolean isAnonType =
                        (Boolean) pType.getValue(st,
                                XsdStereotypeUtils.XSD_PROPERTY_IS_ANON_TYPE);
                if (isAnonType) {
                    return true;
                }
                if (name != null) {
                    Collection<IndexerItem> items = getIndexerItem(tns, name);
                    if (items != null) {
                        for (IndexerItem item : items) {
                            if (item.getType() != null
                                    && item.getType().length() > 0) {
                                switch (ElementType.valueOf(item.getType())) {
                                case SIMPLE:
                                case ATTRIBUTE:
                                case ELEMENT:
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Get the indexed item with the given target namespace and name.
     * 
     * @param tns
     * @param name
     * @return
     */
    private static Collection<IndexerItem> getIndexerItem(String tns,
            String name) {
        IndexerService service =
                XpdResourcesPlugin.getDefault().getIndexerService();
        IndexerItemImpl criteria = new IndexerItemImpl();
        criteria.setName(name);
        criteria.set(ATT_TNS, tns);

        return service.query(INDEXER_ID, criteria);
    }

    /**
     * Check if the given resource is in a Services special folder.
     * 
     * @param resource
     * @return
     */
    private boolean isInSpecialFolder(IResource resource) {

        if (resource != null) {

            SpecialFolder sf = SpecialFolderUtil.getRootSpecialFolder(resource);
            return sf != null
                    && Activator.WSDL_SPECIALFOLDER_KIND.equals(sf.getKind());
        }
        return false;
    }
}
