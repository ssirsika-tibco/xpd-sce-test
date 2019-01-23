/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.schema;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.rest.schema.internal.JsonSchemaIndexProvider;

/**
 * Utility class for JSON Schema elements.
 * 
 * @author nwilson
 * @since 24 Feb 2015
 */
public class JsonSchemaUtil {

    public static final String JSON_SCHEMA_INDEXER_ID =
            "com.tibco.xpd.rest.schema.json.indexer"; //$NON-NLS-1$

    /**
     * The JSON String identifier as stored in the payload reference.
     */
    public static final String UNPROCESSED_TEXT_PAYLOAD_REFERENCE =
            "UNPROCESSED-TEXT"; //$NON-NLS-1$

    /**
     * Checks a class to see if it is the root class.
     * 
     * @param cls
     *            The class to check.
     * @return true if it is the root class.
     */
    public boolean isRootClass(Class cls) {
        return cls.getEAnnotation("root") != null; //$NON-NLS-1$
    }

    /**
     * 
     * @param input
     *            the Schema Type whose duploicate we wish to fetch
     * @param pkg
     *            the parent package of the schema type
     * @param nameText
     *            the name of the schema type
     * @return <code>true</code> if the Type name is duplicate within the same
     *         package else return <code>false</code>
     */
    public static boolean isDuplicateSchemaTypeName(EObject input, Package pkg,
            String nameText) {

        if (input instanceof Class) {

            EList<PackageableElement> elements = pkg.getPackagedElements();
            for (PackageableElement element : elements) {
                if (element instanceof Class) {
                    if (!element.equals(input)) {
                        if (nameText.equals(element.getName())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Get the list of Json schema indexer items for the specified URI of
     * property class.
     * 
     * @param uri
     * 
     * @return The list of Json schema indexer items for the specified URI.
     */
    public static Collection<IndexerItem> getJSonSchemaIndexerItemList(URI uri) {

        String id = uri.fragment();

        String clsType = org.eclipse.uml2.uml.Class.class.getName();

        Map<String, String> additionalAttributes = new HashMap<>();

        additionalAttributes.put(JsonSchemaIndexProvider.TYPE_ID, id);

        IndexerItem queryItem =
                new IndexerItemImpl(null, clsType, null, additionalAttributes);

        Collection<IndexerItem> results =
                XpdResourcesPlugin
                        .getDefault()
                        .getIndexerService()
                        .query(JsonSchemaUtil.JSON_SCHEMA_INDEXER_ID, queryItem);
        return results;
    }

    /**
     * 
     * @param input
     *            the property whose duplicate we wish to fetch
     * @param cls
     *            the parent class of the passed property
     * @param nameText
     *            the duplicate name to find
     * @return <code>true</code> if the Property name is duplicate within the
     *         same Type else return <code>false</code>
     */
    public static boolean isDuplicatePropertyName(EObject input, Class cls,
            String nameText) {

        if (input instanceof Property) {

            EList<Property> ownedAttributes = cls.getOwnedAttributes();

            for (Property property : ownedAttributes) {

                if (!property.equals(input)) {

                    if (nameText.equals(property.getName())) {
                        return true;
                    }
                }
            }
        }
        return false;

    }

    /**
     * Checks if the property name is valid. See http://json.org for details of
     * valid JSON strings.
     * 
     * @param name
     *            The property name.
     * @return <code>true</code> if the property name is valid else
     *         <code>false</code>
     */
    public static boolean isValidPropertyName(String name) {
        int length = name.length();
        boolean valid = true;
        boolean escape = false;
        int expectedHex = 0;
        for (int i = 0; i < length; i++) {
            char c = name.charAt(i);
            if (expectedHex > 0) {
                if (c == '1' || c == '2' || c == '3' || c == '4' || c == '5'
                        || c == '6' || c == '7' || c == '8' || c == '9'
                        || c == '0' || c == 'A' || c == 'B' || c == 'C'
                        || c == 'D' || c == 'E' || c == 'F' || c == 'a'
                        || c == 'b' || c == 'c' || c == 'd' || c == 'e'
                        || c == 'f') {
                    expectedHex--;
                } else {
                    valid = false;
                    break;
                }
            } else if (escape) {
                if (c == '\"' || c == '\\' || c == '/' || c == 'b' || c == 'f'
                        || c == 'n' || c == 'r' || c == 't') {
                    escape = false;
                } else if (c == 'u') {
                    expectedHex = 4;
                    escape = false;
                } else {
                    valid = false;
                    break;
                }
            } else if (c == '\\') {
                escape = true;
            } else if (c == '"') {
                return false;
            }
        }
        if (escape || expectedHex > 0) {
            valid = false;
        }
        return valid;
    }
}
