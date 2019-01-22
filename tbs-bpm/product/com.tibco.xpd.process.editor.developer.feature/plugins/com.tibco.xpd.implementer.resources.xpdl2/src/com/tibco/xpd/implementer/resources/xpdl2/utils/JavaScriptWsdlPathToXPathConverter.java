/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.utils;

import java.util.Map;

import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.implementer.resources.xpdl2.properties.JavaScriptConceptUtil;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.WsdlFilter.WsdlDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Utility for converting a JavaScript grammar mapping referencing WSDL
 * input/output path content to the equivalent XPath expression
 * 
 * @author aallway
 * @since 5 Sep 2012
 */
public class JavaScriptWsdlPathToXPathConverter {

    /**
     * Convert the given WSDL input/output JavaScript grammar xpdl:DataMapping
     * path string to a <b><i>message part</i> relative XPath expression</b>
     * (that is a true XPath expression <b>not</b> the mangled Studio XPath
     * grammar mapping xtyle xpath expression (elements in the expression will
     * be qualified with prefix if required by schema to which they below).
     * <p>
     * This is done by
     * <li>Examining the BOM property for each element in the path</li>
     * <li>Discovering the correct host BOM package for the property's parent
     * class.
     * <li>Convert BOM package name to appropriate WSDL/XSD namespace</li>
     * <li>Lookup the appropriate prefix for the given namespace according to
     * the WSDL/XSD set referenced by the activity.</li>
     * <li>Get the appropriate schema element/attribute name for the BOM
     * property name.</li>
     * <li>Can then rebuild the path according to this info.</li>
     * 
     * @param hostActivity
     *            The activity containing the data mapping.
     * @param javaScriptDataMappingPath
     *            The JavaScript grammar WSDL data path from the
     *            xpdl:DataMapping
     * @param isInputPart
     *            <code>true</code> if this is a WSDL input part path, <code>
     *            false</code> if it's a WSDL output path mapping.
     * @param namespaceToPrefixMap
     *            Map of all namespaces (key) to the prefixes (value) to be used
     *            for the context in which the XPath expression is evaluated.
     *            Any element that references a namespace that is not in list
     *            will not be prefixed (and therefore is assumed to be in the
     *            default namespace).
     * 
     * @return The message part relative XPath (actual XPath not the mangled
     *         Studio XPath datamapping type path) for the given JavaScript
     *         grammar WSDL mapping path provided. If the mapping is directly
     *         from the part itself then the returned XPath will be "."
     *         ("current element" in XPath expression) meaning
     *         "the message part root element". For mapping to child content of
     *         the message part, the path will start with the child element
     *         beneath the message part. i.e...
     * 
     *         <li>a mapping from the message part itself) will return "."</li>
     * 
     *         <li>A mapping from complex content will return "child/text".</li>
     *         <p>
     *         If a problem is encountered the <code>null</code> is returned or
     *         exceptions below thrown.
     * 
     * @throws IllegalStateException
     *             If the given javaScriptDataMappingPath cannot be dereferenced
     *             into the content input/output part of the operation
     *             referenced by the WSDL or some other issue with the
     *             JavaScript path processing.
     * 
     */
    public static String javaScriptWsdlPathToXPath(Activity hostActivity,
            String javaScriptDataMappingPath, boolean isInputPart,
            Map<String, String> namespaceToPrefixMap)
            throws IllegalStateException {

        /*
         * Create a ConceptPath from the JavaScript path, this can be used to
         * access the properties and class-types of the selected BOM element
         * (property) in the path and it's parent elements.
         */
        ConceptPath conceptPath =
                JavaScriptConceptUtil.INSTANCE
                        .resolveJavaScriptWSDLConceptPath(hostActivity,
                                javaScriptDataMappingPath,
                                isInputPart ? WsdlDirection.IN
                                        : WsdlDirection.OUT);
        if (conceptPath == null) {
            throw new IllegalStateException(
                    String.format("Could not resolve path '%1$s' into content of an message part in the operation referenced by activity %3$s.", //$NON-NLS-1$
                            javaScriptDataMappingPath,
                            getActivityLocationLabel(hostActivity, isInputPart)));
        }

        /*
         * Now traverse from the element conceptPath up to the top level element
         * (WsdlPartConceptPath) resolving each into a BOM
         */
        StringBuilder xPath = new StringBuilder();

        if (conceptPath.getParent() == null) {
            /*
             * Mapping direct to/from message part itelf.
             * 
             * The path we are returning is Message PART relative. When we get
             * to the Part path we represent it as "." XPath expression it means
             * "current element".
             */
            xPath.append("."); //$NON-NLS-1$

        } else {
            /*
             * Mapping is to some child content of part.
             */
            ConceptPath currentElementPath = conceptPath;

            /* Traverse up until reach element BELOW the root element. */
            while (currentElementPath != null
                    && currentElementPath.getParent() != null) {
                /*
                 * Ignore artificially inserted elements (such as
                 * ChoiceConceptPath)
                 */
                if (!currentElementPath.isArtificialConceptPath()) {
                    /*
                     * Get the XPath element name equivalent of the referenced
                     * BOM property.
                     */
                    String pathElementName =
                            getXPathElementNameForProperty(hostActivity,
                                    isInputPart,
                                    currentElementPath,
                                    namespaceToPrefixMap);

                    /*
                     * Prefix the path we have so far with the name for this
                     * element.
                     */
                    if (xPath.length() == 0) {
                        /* First part of path. */
                        xPath.append(pathElementName);
                    } else {
                        /* Subsequent part of path. */
                        xPath.insert(0, pathElementName + "/"); //$NON-NLS-1$
                    }
                }

                /* Traverse up path. */
                currentElementPath = currentElementPath.getParent();
            }
        }

        return xPath.toString();
    }

    /**
     * @param hostActivity
     * @param isInputPart
     * @param currentElementPath
     * @param namespaceToPrefixMap
     * 
     * @return The XPath element name for the BOM property referenced byt the
     *         given conceptpath.
     * 
     * @throws IllegalStateException
     *             If the given javaScriptDataMappingPath cannot be dereferenced
     *             into the content input/output part of the operation
     *             referenced by the WSDL or some other issue with the
     *             JavaScript path processing.
     */
    private static String getXPathElementNameForProperty(Activity hostActivity,
            boolean isInputPart, ConceptPath currentElementPath,
            Map<String, String> namespaceToPrefixMap)
            throws IllegalStateException {

        /*
         * Get the equivalent schema element/attribute name for BOM proeprty.
         */
        String name =
                getSchemaNameForProperty(hostActivity,
                        isInputPart,
                        currentElementPath);

        /*
         * Check the equivalent schema element/attribute should be qualified
         * with namespace or not.
         */
        boolean shouldQualifyWithPrefix =
                isQualifiedSchemaProperty(hostActivity,
                        isInputPart,
                        currentElementPath);

        if (!shouldQualifyWithPrefix) {
            /* The element does not need to be qualified so just return name. */
            return name;
        }

        /*
         * Needs to be qualified so get the equivalent schema namespace to use
         * for property
         */
        String namespace =
                getSchemaNamespaceForProperty(hostActivity,
                        isInputPart,
                        currentElementPath);

        /*
         * Got namespace - find prefix to use for it.
         */
        String prefix = namespaceToPrefixMap.get(namespace);

        if (prefix == null || prefix.length() == 0) {
            /* default namespace,so don't prefix. */
            return name;

        } else {
            /* Non-default namespace, use prefix with prefix. */
            return prefix + ":" + name; //$NON-NLS-1$
        }
    }

    /**
     * Get the schema element/attribute name for the BOM property represented by
     * the given concept path.
     * <p>
     * The name is based upon the property name, however if it from a generated
     * BOM then we need to take the name from the original schema name
     * stereotype.
     * 
     * @param hostActivity
     * @param isInputPart
     * 
     * @param conceptPath
     * 
     * @return schema element/attribute name for the BOM property
     * 
     * @throws IllegalStateException
     *             If the given javaScriptDataMappingPath cannot be dereferenced
     *             into the content input/output part of the operation
     *             referenced by the WSDL or some other issue with the
     *             JavaScript path processing.
     */
    private static String getSchemaNameForProperty(Activity hostActivity,
            boolean isInputPart, ConceptPath conceptPath)
            throws IllegalStateException {
        Object item = conceptPath.getItem();
        if (!(item instanceof Property)) {
            throw new IllegalStateException(
                    String.format("Concept path '%1$s' references element that is not a class property. (activity %2$s)", //$NON-NLS-1$
                            conceptPath.toString(),
                            getActivityLocationLabel(hostActivity, isInputPart)));
        }

        String schemaNameForProperty = conceptPath.getSchemaNameForProperty();

        if (schemaNameForProperty == null
                || schemaNameForProperty.length() == 0) {
            throw new IllegalStateException(
                    String.format("Concept path '%1$s' references element that has no name. (activity %2$s)", //$NON-NLS-1$
                            conceptPath.toString(),
                            getActivityLocationLabel(hostActivity, isInputPart)));
        }

        return schemaNameForProperty;
    }

    /**
     * Check whether the schema item for the BOM property referenced by the
     * given concept path is qualified or not.
     * 
     * @param hostActivity
     * @param isInputPart
     * 
     * @param conceptPath
     * 
     * @return schema element/attribute name for the BOM property
     * 
     * @throws IllegalStateException
     *             If the given javaScriptDataMappingPath cannot be dereferenced
     *             into the content input/output part of the operation
     *             referenced by the WSDL or some other issue with the
     *             JavaScript path processing.
     */
    private static boolean isQualifiedSchemaProperty(Activity hostActivity,
            boolean isInputPart, ConceptPath conceptPath)
            throws IllegalStateException {
        Object item = conceptPath.getItem();
        if (!(item instanceof Property)) {
            throw new IllegalStateException(
                    String.format("Concept path '%1$s' references element that is not a class property. (activity %2$s)", //$NON-NLS-1$
                            conceptPath.toString(),
                            getActivityLocationLabel(hostActivity, isInputPart)));
        }

        return conceptPath.isQualifiedSchemaProperty();
    }

    /**
     * Get the schema namespace for the given property.
     * <p>
     * The namespace will be based on the package in which the <b>container</b>
     * of the property (the class) is defined (not the property itself because
     * in the case of properties inhertied from super-class we don't want
     * package for the super-class in which an inherited property is defined we
     * want the package of the sub-class that inherits the property.
     * <p>
     * In the case of generated BOM, we will get the namespace from the
     * additional stereotype.
     * 
     * @param hostActivity
     * @param isInputPart
     * 
     * @param conceptPath
     * @return The schema namespace for the given property.
     * 
     * @throws IllegalStateException
     *             If the given javaScriptDataMappingPath cannot be dereferenced
     *             into the content input/output part of the operation
     *             referenced by the WSDL or some other issue with the
     *             JavaScript path processing.
     */
    private static String getSchemaNamespaceForProperty(Activity hostActivity,
            boolean isInputPart, ConceptPath conceptPath)
            throws IllegalStateException {
        String schemaNamespaceForProperty =
                conceptPath.getSchemaNamespaceForProperty();

        if (schemaNamespaceForProperty == null
                || schemaNamespaceForProperty.length() == 0) {
            throw new IllegalStateException(
                    String.format("Cannot access namespace for Concept path '%1$s'. (activity %2$s)", //$NON-NLS-1$ 
                            conceptPath.toString(),
                            getActivityLocationLabel(hostActivity, isInputPart)));
        }

        return schemaNamespaceForProperty;
    }

    /**
     * @param hostActivity
     * @param isInputPart
     * @return label representing the location of the activity.
     */
    private static String getActivityLocationLabel(Activity hostActivity,
            boolean isInputPart) {
        String processName =
                hostActivity.getProcess() != null ? Xpdl2ModelUtil
                        .getDisplayNameOrName(hostActivity.getProcess())
                        : "<unknown process>"; //$NON-NLS-1$

        String activityName = Xpdl2ModelUtil.getDisplayNameOrName(hostActivity);

        return processName + ":" + activityName + ":" //$NON-NLS-1$ //$NON-NLS-2$
                + (isInputPart ? "WSDL_InputPart" : "WSDL_OutputPart"); //$NON-NLS-1$ //$NON-NLS-2$
    }

}
