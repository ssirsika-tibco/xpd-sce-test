/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.schema.ui.json;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.rest.schema.ui.ISO8601DateFormat;
import com.tibco.xpd.rest.schema.ui.ISO8601DateFormat.ISO8601DateFormatType;

/**
 * JSON Schema importer to support conversion of a sample JSON file to a schema
 * describing the structure of the supplied JSON. This conversion makes a number
 * of assumptions so it may be necessary for the user to edit the generated
 * schema to correct it.
 * 
 * @author nwilson
 * @since 30 Jan 2015
 */
public class JsonSampleImportConvertor implements JsonImportConvertor {

    private DateFormat dateTime;

    private DateFormat date;

    private DateFormat time;

    private String rootName;

    private JsonElement json;

    /**
     * @param rootName
     *            The name of the root element.
     * @param root
     *            The root JSON element.
     */
    public JsonSampleImportConvertor(String rootName, JsonElement json) {
        this.rootName = rootName;
        this.json = json;
        dateTime = new ISO8601DateFormat();
        date = new ISO8601DateFormat(ISO8601DateFormatType.DATE);
        time = new ISO8601DateFormat(ISO8601DateFormatType.TIME);
    }

    /**
     * @see com.tibco.xpd.rest.schema.ui.json.JsonImportConvertor
     *      #convert(org.eclipse.uml2.uml.Package)
     * 
     * @param rs
     *            The target Resource Set.
     * @param root
     *            The root JSON element.
     */
    @Override
    public void convert(ResourceSet rs, Package root) {
        if (json.isJsonObject()) {
            JsonObject jo = json.getAsJsonObject();
            createClass(rs, root, null, rootName, jo, false);
        } else if (json.isJsonArray()) {
            JsonArray ja = json.getAsJsonArray();
            createArray(rs, root, null, rootName, ja);
        }
    }

    /**
     * @param rs
     *            The target Resource Set.
     * @param root
     *            The root Package.
     * @param parent
     *            The parent Class.
     * @param name
     *            The JSON element name.
     * @param jo
     *            The JSON object.
     */
    private void createClass(ResourceSet rs, Package root, Class parent,
            String name, JsonObject jo, boolean isArray) {
        Class cls;
        Type existing = root.getOwnedType(name);
        if (existing instanceof Class) {
            cls = (Class) existing;
        } else {
            cls = root.createOwnedClass(name, false);
        }
        if (parent == null) {
            if (root.getEAnnotation(ROOT) == null) {
                EAnnotation annotation = root.createEAnnotation(ROOT);
                annotation.setEModelElement(cls);
            }
        } else {
            if (parent.getOwnedAttribute(name, cls) == null) {
                Property property = parent.createOwnedAttribute(name, cls);
                if (isArray) {
                    property.setUpper(LiteralUnlimitedNatural.UNLIMITED);
                }
            }
        }
        createFields(rs, root, cls, jo);
    }

    /**
     * @param rs
     *            The target Resource Set.
     * @param root
     *            The root Package.
     * @param cls
     *            The UML Class.
     * @param jo
     *            The JSON Object.
     */
    private void createFields(ResourceSet rs, Package root, Class cls,
            JsonObject jo) {
        List<String> existing = new ArrayList<>();
        for (Property property : cls.getOwnedAttributes()) {
            existing.add(property.getName());
        }
        List<String> optional = new ArrayList<>(existing);
        for (Entry<String, JsonElement> entry : jo.entrySet()) {
            String name = entry.getKey();
            JsonElement value = entry.getValue();
            if (existing.contains(name)) {
                optional.remove(name);
            } else {
                createField(rs, root, cls, name, value);
                if (existing.size() > 0) {
                    optional.add(name);
                }
            }
        }
        for (String name : optional) {
            Property ownedAttribute = cls.getOwnedAttribute(name, null);
            if (ownedAttribute != null) {
                ownedAttribute.setLower(0);
            }
        }
    }

    /**
     * @param rs
     *            The target Resource Set.
     * @param root
     *            The root Package.
     * @param cls
     *            The UML Class.
     * @param name
     *            The JSON field name.
     * @param value
     *            The JSON element
     */
    private void createField(ResourceSet rs, Package root, Class cls,
            String name, JsonElement value) {
        if (value instanceof JsonObject) {
            JsonObject jo = value.getAsJsonObject();
            createClass(rs, root, cls, name, jo, false);
        } else if (value instanceof JsonNull) {
            String type = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            createPrimitive(rs, cls, name, type, false);
        } else if (value instanceof JsonArray) {
            JsonArray array = (JsonArray) value;
            createArray(rs, root, cls, name, array);
        } else if (value instanceof JsonPrimitive) {
            JsonPrimitive primitive = (JsonPrimitive) value;
            String type = getPrimitiveType(primitive);
            if (type != null) {
                createPrimitive(rs, cls, name, type, false);
            }
        }
    }

    /**
     * Converts a JSON primitive type into the matching BOM primitive type.
     * 
     * @param primitive
     *            The JSON primitive.
     * @return The matching BOM type name.
     */
    private String getPrimitiveType(JsonPrimitive primitive) {
        String type = null;
        if (primitive.isBoolean()) {
            type = PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME;
        } else if (primitive.isNumber()) {
            type = PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME;
        } else if (primitive.isString()) {
            String s = primitive.getAsString();
            if (isDouble(s)) {
                type = PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME;
            } else if (isDateTime(s)) {
                type = PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME;
            } else if (isDate(s)) {
                type = PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME;
            } else if (isTime(s)) {
                type = PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME;
            } else {
                type = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            }
        }
        return type;
    }

    /**
     * @param value
     *            The value to parse.
     * @return true if it could be a double.
     */
    private boolean isDouble(String value) {
        boolean isDouble;
        try {
            Double.parseDouble(value);
            isDouble = true;
        } catch (NumberFormatException e) {
            isDouble = false;
        }
        return isDouble;
    }

    /**
     * @param value
     *            The value to parse.
     * @return true if it could be a DateTime.
     */
    private boolean isDateTime(String value) {
        boolean isOk;
        try {
            dateTime.parse(value);
            isOk = true;
        } catch (ParseException e) {
            isOk = false;
        }
        return isOk;
    }

    /**
     * @param value
     *            The value to parse.
     * @return true if it could be a Date.
     */
    private boolean isDate(String value) {
        boolean isOk;
        try {
            date.parse(value);
            isOk = true;
        } catch (ParseException e) {
            isOk = false;
        }
        return isOk;
    }

    /**
     * @param value
     *            The value to parse.
     * @return true if it could be a Time.
     */
    private boolean isTime(String value) {
        boolean isOk;
        try {
            time.parse(value);
            isOk = true;
        } catch (ParseException e) {
            isOk = false;
        }
        return isOk;
    }

    /**
     * @param rs
     *            The target Resource Set.
     * @param cls
     *            The UML Class.
     * @param name
     *            The element name.
     * @param typeName
     *            The primitive type name.
     */
    private void createPrimitive(ResourceSet rs, Class cls, String name,
            String typeName, boolean isArray) {
        PrimitiveType type =
                PrimitivesUtil.getStandardPrimitiveTypeByName(rs, typeName);
        Property property = cls.createOwnedAttribute(name, type);
        if (isArray) {
            property.setUpper(LiteralUnlimitedNatural.UNLIMITED);
        }
    }

    /**
     * @param rs
     *            The target Resource Set.
     * @param root
     *            The root Package.
     * @param cls
     *            The UML Class.
     * @param name
     *            The element name.
     * @param array
     *            The JSON array.
     */
    private void createArray(ResourceSet rs, Package root, Class parent,
            String name, JsonArray array) {
        java.lang.Class<?> type = null;
        for (JsonElement child : array) {
            java.lang.Class<?> childType = child.getClass();
            if (type == null) {
                type = childType;
            } else if (!type.equals(childType)) {
                type = null;
                break;
            }
        }
        if (JsonObject.class.equals(type)) {
            for (JsonElement child : array) {
                JsonObject jo = (JsonObject) child;
                createClass(rs, root, parent, name, jo, true);
            }
        } else if (JsonPrimitive.class.equals(type)) {
            String primitiveType = null;
            for (JsonElement child : array) {
                if (child.isJsonPrimitive()) {
                    JsonPrimitive primitive = child.getAsJsonPrimitive();
                    String nextType = getPrimitiveType(primitive);
                    if (primitiveType == null) {
                        primitiveType = nextType;
                    } else if (!primitiveType.equals(nextType)) {
                        primitiveType = null;
                        break;
                    }
                }
            }
            if (primitiveType == null) {
                primitiveType = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            }
            createPrimitive(rs, parent, name, primitiveType, true);
        } else if (JsonNull.class.equals(type)) {
            createField(rs, root, parent, name, array.get(0));
        }
    }
}