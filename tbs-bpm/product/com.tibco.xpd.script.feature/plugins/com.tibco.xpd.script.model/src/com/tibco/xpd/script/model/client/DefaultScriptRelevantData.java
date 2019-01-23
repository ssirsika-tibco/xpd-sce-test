/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.script.model.client;

import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.internal.client.IAdditionalInfoLabelProvider;
import com.tibco.xpd.script.model.internal.client.ITypeResolution;

public class DefaultScriptRelevantData implements IScriptRelevantData,
        ITypeResolution {

    private String name;

    private String type;

    private String id;

    private String description;

    private Image icon;

    private boolean isArray;

    private String additionalInfo;

    private IAdditionalInfoLabelProvider additionalInfoLabelProvider;

    private List<IScriptRelevantData> resolutionTypes;

    private IScriptRelevantData genericContextType;

    private boolean isReadOnly;

    private Object extendedInfo;

    /**
     * When this is IScriptRelevantData representing a method parameter then
     * this indicates an explicit handling for subType/superType coercion
     * compatibility.
     */
    private ParameterCoercionCriteria paramCoercionCriteria = null;

    /**
     * When this is IScriptRelevantData representing a method parameter then
     * this indicates an explicit handling for subType/superType coercion
     * compatibility.
     * 
     * @return the paramCoercionCriteria or <code>null</code> if this has not
     *         been explicitly set (if not derived from a parameter for
     *         instance).
     */
    public ParameterCoercionCriteria getParamCoercionCriteria() {
        return paramCoercionCriteria;
    }

    /**
     * set this criteria when you intend to tell the isValidAssignment() about
     * the kind of subType/superType compatibility you want
     * 
     * @param paramCoercionCriteria
     *            the paramCoercionCriteria to set
     */
    public void setParamCoercionCriteria(
            ParameterCoercionCriteria paramCoercionCriteria) {
        this.paramCoercionCriteria = paramCoercionCriteria;
    }

    public DefaultScriptRelevantData() {
        this.name = null;
        this.type = null;
        this.id = null;
        this.description = null;
        this.icon = null;
        this.isArray = false;
        this.additionalInfo = null;
        this.isReadOnly = false;
    }

    public DefaultScriptRelevantData(String name, String type, boolean isArray) {
        this.name = name;
        this.type = type;
        this.id = null;
        this.description = null;
        this.icon = null;
        this.isArray = isArray;
        this.additionalInfo = null;
        this.isReadOnly = false;
    }

    public DefaultScriptRelevantData(String name, String type, String id,
            String description, Image icon, boolean isArray,
            String additionalInfo) {
        this.name = name;
        this.type = type;
        this.id = id;
        this.description = description;
        this.icon = icon;
        this.isArray = isArray;
        this.additionalInfo = additionalInfo;
        this.isReadOnly = false;
    }

    /**
     * Get the name of the process script relevant data.
     * 
     * @return the name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Set the name of the process script relevant data.
     * 
     * @param name
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the id of the process script relevant data.
     * 
     * @return the id
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * Set the id of the process script relevant data.
     * 
     * @param id
     */
    @Override
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get the icon of the process script relevant data.
     * 
     * @return the icon
     */
    @Override
    public Image getIcon() {
        return icon;
    }

    /**
     * Set the icon of the process script relevant data.
     * 
     * @param icon
     */
    @Override
    public void setIcon(Image icon) {
        this.icon = icon;
    }

    /**
     * Get if the process script relevant data is an array.
     * 
     * @return true if the data is an array
     */
    @Override
    public boolean isArray() {
        return isArray;
    }

    /**
     * Set if the process script relevant data is an array.
     * 
     * @param isArray
     */
    @Override
    public void setIsArray(boolean isArray) {
        this.isArray = isArray;
    }

    /**
     * Get the additional information of the process script relevant data.
     * 
     * @return the additionalInfo
     */
    @Override
    public String getAdditionalInfo() {
        if (additionalInfo == null && additionalInfoLabelProvider != null) {
            additionalInfo =
                    additionalInfoLabelProvider.getAdditionalInfoLabel();
        }
        return additionalInfo;
    }

    /**
     * Set the additional info of the process script relevant data.
     * 
     * @param additionalInfo
     */
    @Override
    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    /**
     * Get the type of the process script relevant data.
     * 
     * @return the type
     */
    @Override
    public String getType() {
        if (type != null) {
            return type;
        }
        return JsConsts.UNDEFINED_DATA_TYPE;
    }

    /**
     * Set the type of the process script relevant data.
     * 
     * @param type
     */
    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public List<IScriptRelevantData> getTypesResolution() {
        return resolutionTypes;
    }

    @Override
    public void setTypesResolution(List<IScriptRelevantData> resolutionTypes) {
        this.resolutionTypes = resolutionTypes;
    }

    @Override
    public IScriptRelevantData getGenericContextType() {
        return genericContextType;
    }

    @Override
    public void setGenericContextType(IScriptRelevantData genericContextType) {
        this.genericContextType = genericContextType;
    }

    @Override
    public boolean isReadOnly() {
        return isReadOnly;
    }

    @Override
    public void setReadOnly(boolean isReadOnly) {
        this.isReadOnly = isReadOnly;
    }

    @Override
    public boolean isContextless() {
        return false;
    }

    public void setAdditionalInfoLabelProvider(
            IAdditionalInfoLabelProvider additionalInfoLabelProvider) {
        this.additionalInfoLabelProvider = additionalInfoLabelProvider;
    }

    @Override
    public void setExtendedInfo(Object extendedInfo) {
        this.extendedInfo = extendedInfo;
    }

    @Override
    public Object getExtendedInfo() {
        return extendedInfo;
    }

    /**
     * @see com.tibco.xpd.script.model.client.IScriptRelevantData#isStatic()
     * 
     * @return
     */
    @Override
    public boolean isStatic() {
        return false;
    }
}
