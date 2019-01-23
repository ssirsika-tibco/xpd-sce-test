/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties;

import java.util.List;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.bom.xsdtransform.XsdStereotypeUtils;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
import com.tibco.xpd.processeditor.xpdl2.fields.DataFieldTextProvider;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * @author nwilson
 */
public class ConceptPath {

    /**
     * The separator between parent and child content on concept paths.
     */
    public static final String CONCEPTPATH_SEPARATOR = "."; //$NON-NLS-1$

    private ConceptPath parent;

    private Object item;

    private Classifier type;

    /**
     * The index of this component inside its parent.
     * 
     * Was deprecated for (XPD-1666: Note that we no longer ever return
     * individual array elements), re-instated for XPD-3499 to support CIM
     * requirements.
     */
    private int index;

    /**
     * <code>true</code> if the equality of concept paths is based purely n teh
     * path string alone. <code>false</code> if the equality is based on the
     * path AND root path's data item - This can become significant when using
     * concepty paths in tree-views etc where the same 'path' exists in multiple
     * place (i.e. same named datafield referencing same bom class in different
     * processes!
     * 
     * Note that this is set on the ROOT element only and applies to the WHOLE
     * concept hierarchy!
     */
    private Boolean rootElementEqualityBasedOnPathOnly = Boolean.TRUE;

    /**
     * if its set to true, concept path returns array children as well in
     * getChidren()
     * 
     */
    private boolean showChildrenOfArrays;

    /**
     * Construct a root concept path given a particular root data item.
     * 
     * @param item
     *            The data item.
     * @param type
     *            The data item's type.
     */
    public ConceptPath(Object item, Classifier type) {
        this(item, type, Boolean.TRUE);
    }

    /**
     * Construct a root concept path given a particular root data item.
     * 
     * @param item
     *            The data item.
     * @param type
     *            The data item's type.
     * @param equalityBasedOnPathOnly
     *            <code>true</code> if the equality of concept paths is based
     *            purely n teh path string alone. <code>false</code> if the
     *            equality is based on the path AND root path's data item - This
     *            can become significant when using concepty paths in tree-views
     *            etc where the same 'path' exists in multiple place (i.e. same
     *            named datafield referencing same bom class in different
     *            processes!
     */
    public ConceptPath(Object item, Classifier type,
            boolean equalityBasedOnPathOnly) {
        this(null, item, type);
        this.rootElementEqualityBasedOnPathOnly = equalityBasedOnPathOnly;
    }

    public ConceptPath(ConceptPath parent, Object item, Classifier type) {
        this(parent, item, type, -1);
    }

    public ConceptPath(ConceptPath parent, Object item, Classifier type,
            int index) {
        this.parent = parent;
        this.item = item;
        this.type = type;
        this.index = index;
    }

    public ConceptPath getParent() {
        return parent;
    }

    public Object getItem() {
        return item;
    }

    public Classifier getType() {
        return type;
    }

    public List<ConceptPath> getChildren() {
        return ConceptUtil.getConceptPathChildren(this, showChildrenOfArrays);
    }

    /**
     * 
     * @param showChildAttribtuesForArrayTypes
     *            <code>true</code> if you wish child attributes of array type
     *            objects returned (not array elements though, just child
     *            properties of the type).
     * @return children
     */
    public List<ConceptPath> getChildren(
            boolean showChildAttribtuesForArrayTypes) {
        return ConceptUtil.getConceptPathChildren(this,
                showChildAttribtuesForArrayTypes);
    }

    public List<ConceptPath> getSiblings() {
        return ConceptUtil.getConceptPathChildren(getParent());
    }

    /**
     * 
     * @param showChildAttribtuesForArrayTypes
     *            <code>true</code> if you wish child attributes of array type
     *            objects returned (not array elements though, just child
     *            properties of the type)
     * 
     * @return children
     */
    public List<ConceptPath> getSiblings(
            boolean showChildAttribtuesForArrayTypes) {
        return ConceptUtil.getConceptPathChildren(getParent());
    }

    public String getBaseName() {
        String name = ""; //$NON-NLS-1$
        if (item instanceof DataFieldTextProvider) {
            name = ((DataFieldTextProvider) item).getId();
        } else if (item instanceof NamedElement) {
            name = ((NamedElement) item).getName();
        } else if (item instanceof org.eclipse.uml2.uml.NamedElement) {
            name = ((org.eclipse.uml2.uml.NamedElement) item).getName();
        } else if (item instanceof String) {
            name = (String) item;
        }
        return name;
    }

    /**
     * @return
     */
    public String getName() {
        StringBuilder name = new StringBuilder();
        if (isArrayItem()) {
            name.append(getParent().getName() + "[" + index + "]");//$NON-NLS-1$//$NON-NLS-2$
        } else {
            name.append(getBaseName());
        }
        return name.toString();
    }

    public String getPath() {
        StringBuilder path = new StringBuilder();
        if (isArrayItem()) {
            path.append(parent.getPath() + "[" + index + "]");//$NON-NLS-1$//$NON-NLS-2$
        } else {
            if (parent != null) {
                /*
                 * ArtificialConcept paths are introduced for logical grouping
                 * of items which aren't present in the model.
                 * 
                 * For eg: xsd:choice representation in BOM
                 * 
                 * The meta-model needs to remain the same so that BPEL
                 * generation doesn't get affected.
                 */

                /**
                 * XPD-7078 - Changed algorithm to better support artifical
                 * objects. Previously did not work correctly where parent of
                 * artificial object was artificial object. (Used to asume that
                 * is parent was artificial then grandparent would not be.
                 */
                if (parent.isArtificialConceptPath()) {
                    ConceptPath findNonArtificialParent = parent.getParent();

                    while (findNonArtificialParent != null
                            && findNonArtificialParent
                                    .isArtificialConceptPath()) {
                        findNonArtificialParent =
                                findNonArtificialParent.parent;
                    }
                    if (findNonArtificialParent != null) {
                        path.append(findNonArtificialParent.getPath());
                        path.append(CONCEPTPATH_SEPARATOR);
                    }
                } else {
                    path.append(parent.getPath());
                    path.append(CONCEPTPATH_SEPARATOR);
                }
            }
            path.append(getName());
        }
        return path.toString();
    }

    public String getBasePath() {
        StringBuilder path = new StringBuilder();
        if (isArrayItem()) {
            path.append(parent.getBasePath());
        } else {
            if (parent != null) {
                /*
                 * ArtificialConcept paths are introduced for logical grouping
                 * of items which aren't present in the model.
                 * 
                 * For eg: xsd:choice representation in BOM
                 * 
                 * The meta-model needs to remain the same so that BPEL
                 * generation doesn't get affected.
                 */
                /**
                 * XPD-7078 - Changed algorithm to better support artifical
                 * objects. Previously did not work correctly where parent of
                 * artificial object was artificial object. (Used to asume that
                 * is parent was artificial then grandparent would not be.
                 */
                if (parent.isArtificialConceptPath()) {
                    ConceptPath findNonArtificialParent = parent.getParent();

                    while (findNonArtificialParent != null
                            && findNonArtificialParent
                                    .isArtificialConceptPath()) {
                        findNonArtificialParent =
                                findNonArtificialParent.parent;
                    }
                    if (findNonArtificialParent != null) {
                        path.append(findNonArtificialParent.getBasePath());
                        path.append(CONCEPTPATH_SEPARATOR);
                    }
                } else {
                    path.append(parent.getBasePath());
                    path.append(CONCEPTPATH_SEPARATOR);
                }
            }
            path.append(getBaseName());
        }
        return path.toString();
    }

    @Override
    public boolean equals(Object obj) {
        boolean equal = false;
        if (obj == this) {
            equal = true;
        } else if (obj instanceof ConceptPath) {

            String otherPath = ((ConceptPath) obj).getPath();
            if (getPath().equals(otherPath)) {

                /*
                 * If we were constructed to be tested for equality based on
                 * root item then not only the path must be the same BUT also
                 * the root item. This prevents problems when concept paths are
                 * used in trees where the root element has the same name BUT is
                 * from a different object entirely (such as use in project
                 * explorer where 2 processes have same named data referencing
                 * same bom etc)
                 */
                if (isEqualityBasedOnPathOnly()) {
                    equal = true;

                } else {
                    /*
                     * Not basing equality on teh path alone so make sure the
                     * item-object at the root of the path is the same.
                     */
                    Object thisRootItem = getRoot().getItem();
                    Object otherRootItem =
                            ((ConceptPath) obj).getRoot().getItem();

                    if (thisRootItem == null) {
                        if (otherRootItem == null) {
                            equal = true;
                        }
                    } else if (thisRootItem.equals(otherRootItem)) {
                        equal = true;
                    }

                }
            }
        }
        return equal;
    }

    @Override
    public int hashCode() {
        return getPath().hashCode();
    }

    /**
     * @return The root ConceptPath.
     */
    public ConceptPath getRoot() {
        ConceptPath root;
        if (parent == null) {
            root = this;
        } else {
            root = parent.getRoot();
        }
        return root;
    }

    /**
     * @return true if this is an array type.
     */
    public boolean isArray() {
        boolean isArray = false;
        if (item instanceof ProcessRelevantData) {
            isArray = ((ProcessRelevantData) item).isIsArray();
        } else if (item instanceof Property) {
            Property property = (Property) item;
            if (property.getUpper() > 1 || property.getUpper() == -1) {
                isArray = true;
            }
        }
        return isArray;
    }

    /**
     * Was deprecated for (XPD-1666: Note that we no longer ever return
     * individual array elements), re-instated for XPD-3499 to support CIM
     * requirements.
     * 
     * @return true if this is an element inside an array.
     */
    public boolean isArrayItem() {
        if (isArray() && index >= 0) {
            return true;
        }
        return false;
    }

    public boolean isArrayHeader() {
        if (isArray() && index == -1) {
            return true;
        }
        return false;
    }

    /**
     * Was deprecated for (XPD-1666: Note that we no longer ever return
     * individual array elements), re-instated for XPD-3499 to support CIM
     * requirements.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Was deprecated for (XPD-1666: Note that we no longer ever return
     * individual array elements), re-instated for XPD-3499 to support CIM
     * requirements.
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * @return A copy of the ConceptPath with array index of -1.
     */
    public ConceptPath getCopy() {
        ConceptPath copy = new ConceptPath(parent, item, type);
        copy.index = index;
        return copy;
    }

    public boolean isAttribute() {
        if (getItem() instanceof Property) {
            Property property = (Property) getItem();
            Type propertyType = property.getType();
            if (propertyType != null
                    && (propertyType instanceof PrimitiveType || propertyType instanceof Enumeration)
                    && property.getAssociation() == null && !isArrayHeader()) {
                return true;
            }
        }
        return false;
    }

    /**
     * @see java.lang.Object#toString()
     * 
     * @return
     */
    @Override
    public String toString() {
        return getPath();
    }

    /**
     * 
     * ArtificialConcept paths are introduced for logical grouping of items
     * which aren't present in the model.
     * 
     * For eg: xsd:choice representation in BOM
     * 
     * The meta-model needs to remain the same so that BPEL generation doesn't
     * get affected.
     */
    public boolean isArtificialConceptPath() {
        return false;
    }

    /**
     * <code>true</code> if the equality of concept paths is based purely n teh
     * path string alone. <code>false</code> if the equality is based on the
     * path AND root path's data item - This can become significant when using
     * concepty paths in tree-views etc where the same 'path' exists in multiple
     * place (i.e. same named datafield referencing same bom class in different
     * processes!
     */
    public boolean isEqualityBasedOnPathOnly() {
        /*
         * Only the root concept path has the rootElementEqualityBasedOnPathOnly
         * flag set.
         */
        Boolean comparePathOnly = getRoot().rootElementEqualityBasedOnPathOnly;
        if (comparePathOnly == null || comparePathOnly) {
            return true;
        }
        return false;
    }

    /**
     * Get the schema element/attribute name for the BOM property represented by
     * the given concept path.
     * <p>
     * The name is based upon the property name, however if it from a generated
     * BOM then we need to take the name from the original schema name
     * stereotype.
     * 
     * @return schema element/attribute name for the BOM property or
     *         <code>null</code> if this ConceptPath does not represent a BOM
     *         Property element.
     */
    public String getSchemaNameForProperty() {
        if (getItem() instanceof Property) {
            Property property = (Property) getItem();

            /* Default to BOM property name. */
            String schemaNameForProperty = property.getName();

            /* Look for XSD schema element/attribute name stereotype. */
            Object schemaElementNameStereoType =
                    XsdStereotypeUtils.getValueForStereotype(property,
                            XsdStereotypeUtils.XSD_BASED_PROPERTY,
                            XsdStereotypeUtils.XSD_PROPERTY_NAME);

            if (schemaElementNameStereoType instanceof String) {
                if (((String) schemaElementNameStereoType).length() > 0) {
                    schemaNameForProperty =
                            (String) schemaElementNameStereoType;
                }
            }

            if (schemaNameForProperty != null
                    && schemaNameForProperty.length() > 0) {
                return schemaNameForProperty;
            }
        }

        return null;
    }

    /**
     * Check whether the schema item for the BOM property referenced by the
     * given concept path is qualified or not (when refernecing via XML schema /
     * XPath etc).
     * <p>
     * The schema element/attribute for the property should be qualified IF...
     * <li>The property is a top level element reference derived from original
     * schema by bom2xsd transform</li>
     * <li>The property was generated by bom2xsd with stereoType
     * "xsdForm=qualified" (which is the case if it is explicitly set to
     * qualified or the schema's element/attributeDefaultForm is set to
     * qualified and the element itself is not explicitly set to unqualified.</li>
     * <br>
     * </br> This effectively means that current <b>NO user-defined BOM property
     * will ever be considered qualified (as there is no way to configure this
     * for user-defiend BOM).</b>
     * 
     * @return
     */
    public boolean isQualifiedSchemaProperty() {
        if (getItem() instanceof Property) {
            Property property = (Property) getItem();

            /*
             * Properties that are reference to Top level element are always
             * qualified.
             */
            if (isSchemaTopLevelElementRefProperty()) {
                return true;
            }

            /*
             * Look for XSD schema element/attribute xsdForm stereotype - if
             * it's present and set to qualified then it'sshould be qualified
             * when used in XML / XPath
             */
            Object xsdFormStereoType =
                    XsdStereotypeUtils.getValueForStereotype(property,
                            XsdStereotypeUtils.XSD_BASED_PROPERTY,
                            XsdStereotypeUtils.XSD_PROPERTY_FORM);

            if (XsdStereotypeUtils.XSD_FORM_QUALIFIED.equals(xsdFormStereoType)) {
                return true;
            }

        }
        return false;
    }

    /**
     * Get the XML schema namespace that should be used for the BOM type
     * (Classifier).
     * <p>
     * In the case of generated BOM, we will get the namespace from the
     * additional stereotype.
     * 
     * @return The schema namespace for the given type or <code>null</code> if
     *         this ConceptPath does not have a BOM type.
     */
    public String getSchemaNamespaceForType() {
        Classifier type = getType();

        if (type != null) {
            Package containerClassPkg = type.getNearestPackage();

            String schemaNamespace =
                    BOMUtils.getNamespace(containerClassPkg, true);

            if (schemaNamespace != null && schemaNamespace.length() > 0) {
                return schemaNamespace;
            }
        }

        return null;
    }

    /**
     * Get the XML schema namespace that should be used for the BOM property
     * represented by this ConceptPath.
     * <p>
     * Nominally the namespace of a property is the namespace of containing
     * class. This will actually be the TYPE of the PARENT ConceptPath rather
     * than the direct eContainer of property - this will cover properties
     * inherited from a generalised superclass.
     * <p>
     * An exception to this is when the property was originally generated from a
     * schema element that references a top-level element. In this circumstance
     * the namespace will be that of the top-level-element's package.
     * 
     * @return The schema namespace for the given type or <code>null</code> if
     *         this ConceptPath does idoes not represent a BOM property with a
     *         parent type.
     */
    public String getSchemaNamespaceForProperty() {
        if (getItem() instanceof Property) {
            Property property = (Property) getItem();

            Package containerClassPkg = null;

            /*
             * Handle properties of TopLevelElement type (in this case, the
             * namespace should be that of the property's ToplevelElement class.
             */
            if (getType() instanceof Classifier
                    && isSchemaTopLevelElementRefProperty()) {
                /*
                 * Type is the type of the top level element, use the namespace
                 * of the property type in that case.
                 */
                Classifier classifier = getType();
                containerClassPkg = classifier.getNearestPackage();

            } else {
                /*
                 * The namespace will be based on the package in which the
                 * container of the property (the class) is defined (not the
                 * property itself because in the case of properties inherited
                 * from super-class we don't want package for the super-class in
                 * which an inherited property is defined we want the package of
                 * the sub-class that inherits the property.
                 */

                /*
                 * Get the class type definition from the container concept
                 * path. (Ignoring artificial containers such as choice's)
                 */
                ConceptPath parentConceptPath = getParent();

                while (parentConceptPath != null
                        && parentConceptPath.isArtificialConceptPath()) {
                    parentConceptPath = parentConceptPath.getParent();
                }

                if (parentConceptPath != null
                        && (parentConceptPath.getType() instanceof Classifier)) {
                    containerClassPkg =
                            parentConceptPath.getType().getNearestPackage();
                }

            }

            if (containerClassPkg != null) {
                String schemaNamespace =
                        BOMUtils.getNamespace(containerClassPkg, true);

                if (schemaNamespace != null && schemaNamespace.length() > 0) {
                    return schemaNamespace;
                }
            }
        }

        return null;
    }

    /**
     * @return <code>true</code>if the BOM property referenced by this concept
     *         path represents a schema element that references a top level
     *         element (rather than a type).
     */
    public boolean isSchemaTopLevelElementRefProperty() {
        if (getItem() instanceof Property) {
            Property property = (Property) getItem();

            /*
             * If there is aan xsdRef stereotype trhen this is definitely a
             * property generated from schema element that was top-level-element
             * ref.
             */
            Object xsdElementRef =
                    XsdStereotypeUtils.getValueForStereotype(property,
                            XsdStereotypeUtils.XSD_BASED_PROPERTY,
                            XsdStereotypeUtils.XSD_PROPERTY_REF);

            if (xsdElementRef instanceof String
                    && ((String) xsdElementRef).length() > 0) {
                return true;
            }

            /*
             * There will only have been an xsdRef if BOM property was generated
             * from WSDL/XSD originally. However, this may be a property in a
             * user-defined BOM that has been set to the type in a generated BOM
             * that was generated to represent the type of a top-level element.
             * 
             * i.e. A used defined bom property with type set to generated
             * top-level-element's type.
             * 
             * In which case, IF the type represents the type in an ANONYMOUS
             * type top-level- then the user defined BOM property WILL be output
             * as an xsd:element with ref="toplevelelement" (top level element
             * reference). Note that if original top level element was a
             * non-anonymous type then there will be a real complex type
             * definition backing the user defined property (so in that case it
             * would always reference the real type).
             */
            if (getType() instanceof Classifier) {
                /*
                 * So if the referenced type is an stereotyped as generated from
                 * ANONYMOUS type and stereotyped as a top-level-element
                 */
                if (XSDUtil.isClassifierAnonymousType(getType())
                        && XSDUtil.isClassifierTopLevelElement(getType())) {
                    return true;
                }
            }

        }
        return false;
    }

    /**
     * @param showChildrenOfArrays
     *            true to include child elements of arrays elements
     */
    public void setIncludeChi1ldrenOfArrays(boolean showChildrenOfArrays) {
        this.showChildrenOfArrays = showChildrenOfArrays;

    }
}
