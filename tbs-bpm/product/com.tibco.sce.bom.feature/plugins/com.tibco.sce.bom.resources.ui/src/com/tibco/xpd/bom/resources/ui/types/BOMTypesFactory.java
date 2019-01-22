/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.resources.ui.types;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.utils.BOMIndexerService;
import com.tibco.xpd.bom.resources.utils.ResourceItemType;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.ui.picker.PickerService;
import com.tibco.xpd.resources.ui.types.TypeInfo;
import com.tibco.xpd.resources.ui.types.TypeUtil;
import com.tibco.xpd.resources.ui.types.TypedItem;

/**
 * This class can be used to obtain predefined types, to create typed items from
 * the resource db items (IndexerItems) and to map given objects to its type
 * infos.
 * 
 * @author rassisi
 * @deprecated Use {@link PickerService} instead.
 */
@Deprecated
public class BOMTypesFactory {

    // ----------- These fields should not be used in the application

    final static private String TYPE_ID_PACKAGE =
            "com.tibco.xpd.bom.resources.ui.types.Package"; //$NON-NLS-1$

    final static private String TYPE_ID_CLASS =
            "com.tibco.xpd.bom.resources.ui.types.Class"; //$NON-NLS-1$

    final static private String TYPE_ID_PRIMITIVE_TYPE =
            "com.tibco.xpd.bom.resources.ui.types.PrimitiveType"; //$NON-NLS-1$

    final static private String TYPE_ID_BASE_PRIMITIVE_TYPE =
            "com.tibco.xpd.bom.resources.ui.types.BasePrimitiveType"; //$NON-NLS-1$

    final static private String TYPE_ID_STEREO_TYPE =
            "com.tibco.xpd.bom.resources.ui.types.Stereotype"; //$NON-NLS-1$

    final static private String TYPE_ID_PROFILE_TYPE =
            "com.tibco.xpd.bom.resources.ui.types.Profile"; //$NON-NLS-1$

    final static private String TYPE_ID_MODEL =
            "com.tibco.xpd.bom.resources.ui.types.Model"; //$NON-NLS-1$

    final static private String TYPE_ID_PROPERTY =
            "com.tibco.xpd.bom.resources.ui.types.Property"; //$NON-NLS-1$

    final static private String TYPE_ID_ASSOCIATION =
            "com.tibco.xpd.bom.resources.ui.types.Association"; //$NON-NLS-1$

    final static private String TYPE_ID_GENERALIZATION =
            "com.tibco.xpd.bom.resources.ui.types.Generalization"; //$NON-NLS-1$

    final static private String TYPE_ID_ENUMERATION =
            "com.tibco.xpd.bom.resources.ui.types.Enumeration"; //$NON-NLS-1$

    final static private String TYPE_ID_ENUMERATION_LITERAL =
            "com.tibco.xpd.bom.resources.ui.types.EnumLit"; //$NON-NLS-1$

    // ---------- Use only these fields in your application --------------------

    final static public TypeInfo TYPE_PACKAGE = new TypeInfo(TYPE_ID_PACKAGE);

    final static public TypeInfo TYPE_CLASS = new TypeInfo(TYPE_ID_CLASS);

    final static public TypeInfo TYPE_PRIMITIVE_TYPE =
            new TypeInfo(TYPE_ID_PRIMITIVE_TYPE);

    final static public TypeInfo TYPE_BASE_PRIMITIVE_TYPE =
            new TypeInfo(TYPE_ID_BASE_PRIMITIVE_TYPE);

    final static public TypeInfo TYPE_STEREOTYPE =
            new TypeInfo(TYPE_ID_STEREO_TYPE);

    final static public TypeInfo TYPE_PROFILE =
            new TypeInfo(TYPE_ID_PROFILE_TYPE);

    final static public TypeInfo TYPE_MODEL = new TypeInfo(TYPE_ID_MODEL);

    final static public TypeInfo TYPE_PROPERTY = new TypeInfo(TYPE_ID_PROPERTY);

    final static public TypeInfo TYPE_ASSOCIATION =
            new TypeInfo(TYPE_ID_ASSOCIATION);

    final static public TypeInfo TYPE_GENERALIZATION =
            new TypeInfo(TYPE_ID_GENERALIZATION);

    final static public TypeInfo TYPE_ENUMERATION =
            new TypeInfo(TYPE_ID_ENUMERATION);

    final static public TypeInfo TYPE_ENUMERATION_LITERAL =
            new TypeInfo(TYPE_ID_ENUMERATION_LITERAL);

    /**
     * Calculates the type as described in the types provider extension from the
     * given object. These objects can be uml classes or indexer items.
     * 
     * @param eObject
     * @return
     */
    public static TypeInfo getType(Object object) {
        if (object instanceof Package) {
            return TYPE_PACKAGE;
        } else if (object instanceof Class) {
            return TYPE_CLASS;
        } else if (object instanceof PrimitiveType) {
            return TYPE_PRIMITIVE_TYPE;
        } else if (object instanceof PrimitiveType) {
            return TYPE_BASE_PRIMITIVE_TYPE;
        } else if (object instanceof Profile) {
            return TYPE_PROFILE;
        } else if (object instanceof Stereotype) {
            return TYPE_STEREOTYPE;
        } else if (object instanceof Model) {
            return TYPE_MODEL;
        } else if (object instanceof Property) {
            return TYPE_PROPERTY;
        } else if (object instanceof Association) {
            return TYPE_ASSOCIATION;
        } else if (object instanceof Generalization) {
            return TYPE_GENERALIZATION;
        } else if (object instanceof EPackage) {
            return TYPE_PACKAGE;
        } else if (object instanceof EClass) {
            return TYPE_CLASS;
        } else if (object instanceof Enumeration) {
            return TYPE_ENUMERATION;
        } else if (object instanceof EnumerationLiteral) {
            return TYPE_ENUMERATION_LITERAL;
        } else if (object instanceof IndexerItem) {
            String type = ((IndexerItem) object).getType();
            if (type.equals(ResourceItemType.PACKAGE.toString())) {
                return TYPE_PACKAGE;
            } else if (type.equals(ResourceItemType.CLASS.toString())) {
                return TYPE_CLASS;
            } else if (type.equals(ResourceItemType.PRIMITIVE_TYPE.toString())) {
                return TYPE_PRIMITIVE_TYPE;
            } else if (type.equals(ResourceItemType.ENUMERATION.toString())) {
                return TYPE_ENUMERATION;
            }

        }

        return null;
    }

    /**
     * Maps the TypeInfo to the Resource DB
     * 
     * @param item
     * @return
     */
    public static String getKey(TypedItem item) {
        TypeInfo typeInfo = TypeUtil.getTypeOfElement(item);
        return getKey(typeInfo);
    }

    /**
     * Maps the TypeInfo to the Resource DB
     * 
     * @param typeInfo
     * @return
     */
    public static String getKey(TypeInfo typeInfo) {
        String key = null;

        if (typeInfo == TYPE_MODEL) {
            key = BOMIndexerService.INDEXER_ATTR_MODEL;
        } else if (typeInfo == TYPE_PACKAGE) {
            key = BOMIndexerService.INDEXER_ATTR_PACKAGE;
        } else if (typeInfo == TYPE_CLASS) {
            key = BOMIndexerService.INDEXER_ATTR_CLASS;
        } else if (typeInfo == TYPE_PROPERTY) {
            key = BOMIndexerService.INDEXER_ATTR_PROP;
        } else if (typeInfo == TYPE_PRIMITIVE_TYPE) {
            key = BOMIndexerService.INDEXER_ATTR_PRIMTYPE;
        } else if (typeInfo == TYPE_ASSOCIATION) {
            key = BOMIndexerService.INDEXER_ATTR_ASSOC;
        } else if (typeInfo == TYPE_GENERALIZATION) {
            key = BOMIndexerService.INDEXER_ATTR_GEN;
        }
        return key;
    }

    /**
     * Creates a typed item from the resource db item.
     * 
     * @param item
     * @return
     */
    public static TypedItem createItem(IndexerItem item) {
        TypeInfo typeInfo = getType(item);
        TypedItem result = new TypedItem(typeInfo);
        String name = item.getName();
        int pos = name.lastIndexOf(BOMWorkingCopy.JAVA_PACKAGE_SEPARATOR);
        if (pos != -1) {
            name =
                    name.substring(pos
                            + BOMWorkingCopy.JAVA_PACKAGE_SEPARATOR.length(),
                            name.length());
        }
        result.setName(name);
        result.setQualifiedName(item.getName());
        result.setUriString(item.getURI());
        result.setImages(new Object[] { item
                .get(BOMResourcesPlugin.INDEXER_ATTRIBUTE_IMAGE_URI) });

        result.setData(item);
        return result;
    }

}
