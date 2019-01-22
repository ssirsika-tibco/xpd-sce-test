/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.resources.ui.types;

import com.tibco.xpd.resources.ui.picker.PickerItem;

/**
 * The TypedItem describes the information which is derived from a query by a
 * filter and serves for the Resolvers to find the associated instances and also
 * for the labelProviders to display meaningful and localised information on the
 * screen by the <code>PickerDialog</code>. It acts as a proxy for the
 * <code>PickerDialog</code> to avoid unnecessary loadings.<br/>
 * The TypedItem corresponds with the TypeInfo by using it's id.<br/>
 * 
 * @author rassisi
 * @depricated Use {@link PickerItem} instead.
 */
final public class TypedItem {

    /**
     * The type id which is defined by a types provider.
     */
    private String typeId;

    /**
     * URI string. This information is mainly used to resolve the corresponding
     * object.
     */
    private String uriString;

    /**
     * The group id which is defined by a types provider.
     */
    private String groupId;

    /**
     * The name of the item. It is needed to display it in the PickerDialog.
     * 
     * name.
     */
    private String name;

    /**
     * The qualified name.
     */
    private String qualifiedName;

    /**
     * This field keeps additional data eg. a file extension.
     */
    private Object data;

    /**
     * Attribute to display one or more images for this type.
     */
    private Object[] images;

    /**
     * The type id which is defined by a types provider.
     * 
     * @param typeId
     */
    TypedItem(String typeId) {
        this.typeId = typeId;
    }

    /**
     * Constructs an empty TypedItem with the given TypeInfo.
     * 
     * @param typeInfo
     */
    public TypedItem(TypeInfo typeInfo) {
        typeId = typeInfo.getTypeId();
        groupId = typeInfo.getGroupId();
    }

    /**
     * The name of the item. It is needed to diplay it in the PickerDialog.
     * 
     * @return
     */
    public String getName() {
        if (name == null) {
            name = ""; //$NON-NLS-1$
        }
        return name;
    }

    /**
     * This field keeps additional data eg. a file extension.
     * 
     * @return
     */
    public Object getData() {
        return data;
    }

    /**
     * This field keeps additional data eg. a file extension.
     * 
     * @param data
     */
    public void setData(Object data) {
        this.data = data;
    }

    /**
     * One item can have many images. The PickerDialog needs actually only one,
     * which is displayed in the selection list.
     * 
     * @return
     */
    public Object[] getImages() {
        if (images == null) {
            images = new Object[0];
        }
        return images;
    }

    /**
     * One item can have many images. The PickerDialog needs actually only one,
     * which is displayed in the selection list.
     * 
     * @param images
     */
    public void setImages(Object[] images) {
        this.images = images;
    }

    /**
     * The qualified name is needed by the PickerDialog's label provider.
     * 
     * @return
     */
    public String getQualifiedName() {
        if (qualifiedName == null) {
            qualifiedName = ""; //$NON-NLS-1$
        }
        return qualifiedName;
    }

    /**
     * The qualified name is needed by the PickerDialog's label provider.
     * 
     * @param qualifiedName
     */
    public void setQualifiedName(String qualifiedName) {
        this.qualifiedName = qualifiedName;
    }

    /**
     * The group id which is defined by a types provider.
     * 
     * @return
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * URI string. This information is mainly used to resolve the corresponding
     * object.
     * 
     * @return
     */
    public String getUriString() {
        if (uriString == null) {
            uriString = ""; //$NON-NLS-1$
        }
        return uriString;
    }

    /**
     * URI string. This information is mainly used to resolve the corresponding
     * object.
     * 
     * @param uriString
     */
    public void setUriString(String uriString) {
        this.uriString = uriString;
    }

    /**
     * The type id which is defined by a types provider.
     * 
     * @return
     */
    public String getTypeId() {
        return typeId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int code = super.hashCode();
        if (uriString != null && qualifiedName != null) {
            code = uriString.hashCode() + qualifiedName.hashCode();
        }
        return code;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        boolean isEquals = false;
        if (obj == this) {
            return true;
        } else if (obj instanceof TypedItem) {
            TypedItem other = (TypedItem) obj;
            if (getUriString() != null && other.getUriString() != null) {
                isEquals =
                        other.getQualifiedName().equals(getQualifiedName())
                                && other.getUriString().equals(getUriString());
            } else {
                isEquals = other.getQualifiedName().equals(getQualifiedName());
            }
        } else if (obj instanceof TypeInfo) {
            if (getTypeId().equals(((TypeInfo) obj).getTypeId())) {
                return true;
            }
        }
        return isEquals;
    }

    /**
     * The type id which is defined by a types provider.
     * 
     * @param typeId
     */
    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    /**
     * The group id which is defined by a types provider.
     * 
     * @param groupId
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * The name of the item. It is needed to display it in the PickerDialog.
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

}
