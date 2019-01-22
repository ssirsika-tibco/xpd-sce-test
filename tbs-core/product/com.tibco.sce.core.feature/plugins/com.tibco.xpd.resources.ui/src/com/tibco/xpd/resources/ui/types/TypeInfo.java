package com.tibco.xpd.resources.ui.types;

import org.eclipse.core.resources.IResource;

import com.tibco.xpd.resources.ui.picker.PickerItem;

/**
 * The TypeInfo is a class which is used by PickerDialog. It reflects the types
 * element of the typesProvider extension. Also it can be used as a criteria for
 * queries executed by the filters.
 * 
 * @author rassisi
 * @deprecated Now the java.lang.String is used for type. See also:
 *             {@link PickerItem#getType()}
 */
@Deprecated
public class TypeInfo {

    /**
     * The TypeInfo can only be constructed with the attributes of the extension
     * point.
     * 
     * @param typeId
     * @param groupId
     * @param data
     */
    public TypeInfo(String typeId, String groupId, String data) {
        this.typeId = typeId;
        this.groupId = groupId;
        this.data = data;
    }

    /**
     * This constructor is not allowed.
     */
    TypeInfo() {
    }

    @Override
    public boolean equals(Object arg0) {
        if (this == arg0) {
            return true;
        }
        if (arg0 instanceof TypeInfo) {
            if (typeId.equals(((TypeInfo) arg0).getTypeId())) {
                return true;
            }
        }
        return false;
    }

    // ---------- Attributes from the extension point --------------------------

    /**
     * The id of the types provider.
     */
    private String providerId;

    /**
     * Unique type id.
     */
    private String typeId;

    /**
     * The group id which is defined by a types provider.
     */
    private String groupId;

    // ---------- Attributes from the extension point and for queries ----------

    /**
     * This field keeps additional data eg. a file extension. It will be set by
     * the extension point or by the application for providing additional
     * information (e.g. Stereotype query).
     */
    private Object data;

    // ---------- Attributes for queries ---------------------------------------

    /**
     * If items are retrieved, the context can be narrowed by setting this field
     * (e.g. to the current project).
     */
    private IResource queryResource;

    /**
     * This field is needed by the resolver which retrieves the corresponding
     * object.
     */
    private String queryUriString;

    /**
     * If the type is part of a hierarchical structure then this field provides
     * the information. For EObjects this is the fullQualifiedName.
     */
    private String queryPath;

    /**
     * The TypeInfo can only be constructed with the attributes of the extension
     * point.
     * 
     * @param typeId
     */
    public TypeInfo(String typeId) {
        this.typeId = typeId;
    }

    /**
     * The TypeInfo can only be constructed with the attributes of the extension
     * point.
     * 
     * @param typeId
     * @param groupId
     */
    public TypeInfo(String typeId, String groupId) {
        this.typeId = typeId;
        this.groupId = groupId;
    }

    // ---------- Attributes from the extension point --------------------------

    /**
     * The id of this type.
     * 
     * @return
     */
    public String getTypeId() {
        return typeId;
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
     * This field keeps additional data eg. a file extension. It will be set by
     * the extension point or by the application for providing additional
     * information (e.g. Stereotype query).
     * 
     * @return
     */
    public Object getData() {
        return data;
    }

    // ---------- attributes for queries ---------------------------------------

    /**
     * This field is needed by the resolver which retrieves the corresponding
     * object.
     * 
     * @return
     */
    public IResource getQueryResource() {
        return queryResource;
    }

    /**
     * This field is needed by the resolver which retrieves the corresponding
     * object.
     * 
     * @param queryResource
     */
    public void setQueryResource(IResource queryResource) {
        this.queryResource = queryResource;
    }

    /**
     * This field is needed by the resolver which retrieves the corresponding
     * object.
     * 
     * @return
     */
    public String getQueryUriString() {
        return queryUriString;
    }

    /**
     * This field is needed by the resolver which retrieves the corresponding
     * object.
     * 
     * @param queryUriString
     */
    public void setQueryUriString(String queryUriString) {
        this.queryUriString = queryUriString;
    }

    /**
     * @return
     */
    public String getQueryPath() {
        return queryPath;
    }

    /**
     * @param queryPath
     */
    public void setQueryPath(String queryPath) {
        this.queryPath = queryPath;
    }

    /**
     * @return
     */
    public String getProviderId() {
        return providerId;
    }

    /**
     * The id of the types provider.
     * 
     * @param providerId
     */
    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    /**
     * This field keeps additional data eg. a file extension. It will be set by
     * the extension point or by the application for providing additional
     * information (e.g. Stereotype query).
     * 
     * @param data
     */
    public void setData(Object data) {
        this.data = data;
    }

}
