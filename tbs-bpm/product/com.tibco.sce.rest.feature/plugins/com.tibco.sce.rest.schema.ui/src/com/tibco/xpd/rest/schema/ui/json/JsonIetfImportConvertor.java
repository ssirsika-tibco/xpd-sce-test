/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.schema.ui.json;

import static com.tibco.xpd.bom.types.PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME;
import static com.tibco.xpd.bom.types.PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME;
import static com.tibco.xpd.bom.types.PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME;
import static com.tibco.xpd.bom.types.PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tibco.xpd.bom.types.PrimitivesUtil;

/**
 * JSON Schema importer to support conversion of an IETF JSON Schema file
 * (json-schema.org) to a schema describing the structure of the supplied JSON.
 * 
 * @author nwilson
 * @since 30 Jan 2015
 */
public class JsonIetfImportConvertor implements JsonImportConvertor {

    /**
     * Reference element type.
     */
    private static final String REF = "$ref"; //$NON-NLS-1$

    /**
     * Enum element type.
     */
    private static final String ENUM = "enum"; //$NON-NLS-1$

    /**
     * Null element type.
     */
    private static final String NULL = "null"; //$NON-NLS-1$

    /**
     * String element type.
     */
    private static final String STRING = "string"; //$NON-NLS-1$

    /**
     * Number element type.
     */
    private static final String NUMBER = "number"; //$NON-NLS-1$

    /**
     * Integer element type.
     */
    private static final String INTEGER = "integer"; //$NON-NLS-1$

    /**
     * Boolean element type.
     */
    private static final String BOOLEAN = "boolean"; //$NON-NLS-1$

    /**
     * Items element type.
     */
    private static final String ITEMS = "items"; //$NON-NLS-1$

    /**
     * Array element type.
     */
    private static final String ARRAY = "array"; //$NON-NLS-1$

    /**
     * Properties element type.
     */
    private static final String PROPERTIES = "properties"; //$NON-NLS-1$

    /**
     * Required element type.
     */
    private static final String REQUIRED = "required"; //$NON-NLS-1$

    /**
     * Object element name.
     */
    private static final String OBJECT = "object"; //$NON-NLS-1$

    /**
     * Type element name.
     */
    private static final String TYPE = "type"; //$NON-NLS-1$

    /**
     * Definitions element name.
     */
    private static final String DEFINITIONS = "definitions"; //$NON-NLS-1$

    /**
     * The root JSON element.
     */
    private JsonElement json;

    /**
     * Reference Path to UML Class map.
     */
    private Map<String, Class> definitions;

    /**
     * Property type references that need to be resolved.
     */
    private Map<String, Property> toResolve;

    /**
     * @param json
     *            The IETF JSON Schema source.
     */
    public JsonIetfImportConvertor(JsonElement json) {
        this.json = json;
        definitions = new HashMap<>();
        toResolve = new HashMap<>();
    }

    /**
     * @see com.tibco.xpd.rest.schema.ui.json.JsonImportConvertor
     *      #convert(org.eclipse.emf.ecore.resource.ResourceSet,
     *      org.eclipse.uml2.uml.Package)
     * 
     * @param rs
     *            The target resource set.
     * @param root
     *            The root UML Package.
     */
    @Override
    public void convert(ResourceSet rs, Package root) {
        if (json.isJsonObject()) {
            JsonObject rootJO = json.getAsJsonObject();
            JsonElement definitionsElement = rootJO.get(DEFINITIONS);
            if (definitionsElement != null
                    && definitionsElement.isJsonObject()) {
                JsonObject definitionsJO = definitionsElement.getAsJsonObject();
                for (Entry<String, JsonElement> entry : definitionsJO
                        .entrySet()) {
                    String name = entry.getKey();
                    JsonElement value = entry.getValue();
                    if (value != null && value.isJsonObject()) {
                        JsonObject valueJO = value.getAsJsonObject();
                        readClass(rs, root, name, valueJO);
                    }
                }
            }
            JsonElement typeElement = rootJO.get(TYPE);
            if (typeElement != null && typeElement.isJsonPrimitive()) {
                String typeName = typeElement.getAsString();
                if (OBJECT.equals(typeName)) {
                    Class cls = readClass(rs, root, ROOT, rootJO);
                    EAnnotation annotation = root.createEAnnotation(ROOT);
                    annotation.setEModelElement(cls);
                } else if (ARRAY.equals(typeName)) {
                    JsonElement itemsElement = rootJO.get(ITEMS);
                    if (itemsElement != null && itemsElement.isJsonObject()) {
                        JsonObject itemsJO = itemsElement.getAsJsonObject();
                        if (itemsJO != null) {
                            Class cls = readClass(rs, root, ROOT, itemsJO);
                            EAnnotation annotation =
                                    root.createEAnnotation(ROOT);
                            annotation.setEModelElement(cls);
                        }
                    }
                }
            }
        }
        // Resolve referenced types
        for (Entry<String, Property> entry : toResolve.entrySet()) {
            String ref = entry.getKey();
            Property property = entry.getValue();
            Class cls = definitions.get(ref);
            if (cls != null) {
                property.setType(cls);
            }
        }
    }

    /**
     * @param rs
     *            The target resource set.
     * @param root
     *            The UML Package root.
     * @param className
     *            The class name.
     * @param descriptor
     *            The class descriptor.
     */
    private Class readClass(ResourceSet rs, Package root, String className,
            JsonObject descriptor) {
        Class cls = root.createOwnedClass(className, false);
        definitions.put("#/definitions/" + className, cls); //$NON-NLS-1$
        JsonElement requiredElement = descriptor.get(REQUIRED);
        List<String> required = getAsStringArray(requiredElement);
        JsonElement propertiesElement = descriptor.get(PROPERTIES);
        if (propertiesElement != null && propertiesElement.isJsonObject()) {
            JsonObject propertiesJO = propertiesElement.getAsJsonObject();
            for (Entry<String, JsonElement> entry : propertiesJO.entrySet()) {
                String name = entry.getKey();
                JsonElement valueElement = entry.getValue();
                if (valueElement != null && valueElement.isJsonObject()) {
                    JsonObject valueJO = valueElement.getAsJsonObject();
                    Property property =
                            readProperty(rs, root, cls, name, valueJO);
                    if (!required.contains(name)) {
                        property.setLower(0);
                    }
                }
            }
        }
        return cls;
    }

    /**
     * @param rs
     *            The target resource set.
     * @param cls
     *            The parent class.
     * @param propertyName
     *            The property name.
     * @param descriptor
     *            The property descriptor.
     */
    private Property readProperty(ResourceSet rs, Package root, Class cls,
            String propertyName, JsonObject descriptor) {
        Type type = null;
        boolean isArray = false;
        String itemsRef = null;
        JsonElement typeElement = descriptor.get(TYPE);
        if (typeElement != null && typeElement.isJsonPrimitive()) {
            String typeName = typeElement.getAsString();
            if (ARRAY.equals(typeName)) {
                JsonElement itemsElement = descriptor.get(ITEMS);
                if (itemsElement != null && itemsElement.isJsonObject()) {
                    JsonObject itemsJO = itemsElement.getAsJsonObject();
                    if (itemsJO != null) {
                        typeElement = itemsJO.get(TYPE);
                        if (typeElement != null
                                && typeElement.isJsonPrimitive()) {
                            typeName = typeElement.getAsString();
                            isArray = true;
                        }
                        JsonElement refElement = itemsJO.get(REF);
                        if (refElement != null
                                && refElement.isJsonPrimitive()) {
                            itemsRef = refElement.getAsString();
                            isArray = true;
                        }
                    }
                }
            }
            if (typeName != null) {
                switch (typeName) {
                case BOOLEAN:
                    type = getPrimitive(rs, BOM_PRIMITIVE_BOOLEAN_NAME);
                    break;
                case INTEGER:
                    type = getPrimitive(rs, BOM_PRIMITIVE_INTEGER_NAME);
                    break;
                case NUMBER:
                    type = getPrimitive(rs, BOM_PRIMITIVE_DECIMAL_NAME);
                    break;
                case STRING:
                case NULL:
                    type = getPrimitive(rs, BOM_PRIMITIVE_TEXT_NAME);
                    break;
                case OBJECT:
                    type = readClass(rs, root, propertyName, descriptor);
                    break;
                }
            }

        }
        if (type == null) {
            JsonElement enumElement = descriptor.get(ENUM);
            if (enumElement != null) {
                type = getPrimitive(rs, BOM_PRIMITIVE_TEXT_NAME);
            }

        }
        Property property = cls.createOwnedAttribute(propertyName, type);
        if (type == null) {
            JsonElement refElement = descriptor.get(REF);
            if (refElement != null && refElement.isJsonPrimitive()) {
                String ref = refElement.getAsString();
                if (ref != null) {
                    toResolve.put(ref, property);
                }
            }
            if (itemsRef != null) {
                toResolve.put(itemsRef, property);
            }
        }
        if (isArray) {
            property.setUpper(LiteralUnlimitedNatural.UNLIMITED);
        }
        return property;
    }

    /**
     * @param rs
     *            The target resource set.
     * @param typeName
     *            The BOM primitive type name.
     * @return The BOM type.
     */
    private Type getPrimitive(ResourceSet rs, String typeName) {
        return PrimitivesUtil.getStandardPrimitiveTypeByName(rs, typeName);
    }

    /**
     * Extracts a list of string values from a JSON array element.
     * 
     * @param element
     *            The JSON element
     * @return The array contents as a list of strings.
     */
    private List<String> getAsStringArray(JsonElement element) {
        List<String> data = new ArrayList<>();
        if (element != null && element.isJsonArray()) {
            JsonArray array = element.getAsJsonArray();
            Iterator<JsonElement> iterator = array.iterator();
            while (iterator.hasNext()) {
                JsonElement next = iterator.next();
                if (next != null && next.isJsonPrimitive()) {
                    String s = next.getAsString();
                    if (s != null) {
                        data.add(s);
                    }
                }
            }
        }
        return data;
    }
}