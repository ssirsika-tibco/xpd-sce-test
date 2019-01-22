/**
 * ActivityReference.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.tibco.n2.pfe.services.pageflowType;


/**
 * Reference details of a page activity.
 */
public class ActivityReference  extends com.tibco.n2.pfe.services.pageflowType.BaseActivityReference  implements java.io.Serializable {
    private java.lang.String activityName;  // attribute

    private java.lang.String activityModelId;  // attribute

    private java.lang.String moduleName;  // attribute

    private java.lang.String moduleVersion;  // attribute

    private java.lang.String processName;  // attribute

    public ActivityReference() {
    }

    public ActivityReference(
           java.lang.String activityId,
           java.lang.String activityName,
           java.lang.String activityModelId,
           java.lang.String moduleName,
           java.lang.String moduleVersion,
           java.lang.String processName) {
        super(activityId);
        this.activityName = activityName;
        this.activityModelId = activityModelId;
        this.moduleName = moduleName;
        this.moduleVersion = moduleVersion;
        this.processName = processName;
    }


    /**
     * Gets the activityName value for this ActivityReference.
     * 
     * @return activityName
     */
    public java.lang.String getActivityName() {
        return activityName;
    }


    /**
     * Sets the activityName value for this ActivityReference.
     * 
     * @param activityName
     */
    public void setActivityName(java.lang.String activityName) {
        this.activityName = activityName;
    }


    /**
     * Gets the activityModelId value for this ActivityReference.
     * 
     * @return activityModelId
     */
    public java.lang.String getActivityModelId() {
        return activityModelId;
    }


    /**
     * Sets the activityModelId value for this ActivityReference.
     * 
     * @param activityModelId
     */
    public void setActivityModelId(java.lang.String activityModelId) {
        this.activityModelId = activityModelId;
    }


    /**
     * Gets the moduleName value for this ActivityReference.
     * 
     * @return moduleName
     */
    public java.lang.String getModuleName() {
        return moduleName;
    }


    /**
     * Sets the moduleName value for this ActivityReference.
     * 
     * @param moduleName
     */
    public void setModuleName(java.lang.String moduleName) {
        this.moduleName = moduleName;
    }


    /**
     * Gets the moduleVersion value for this ActivityReference.
     * 
     * @return moduleVersion
     */
    public java.lang.String getModuleVersion() {
        return moduleVersion;
    }


    /**
     * Sets the moduleVersion value for this ActivityReference.
     * 
     * @param moduleVersion
     */
    public void setModuleVersion(java.lang.String moduleVersion) {
        this.moduleVersion = moduleVersion;
    }


    /**
     * Gets the processName value for this ActivityReference.
     * 
     * @return processName
     */
    public java.lang.String getProcessName() {
        return processName;
    }


    /**
     * Sets the processName value for this ActivityReference.
     * 
     * @param processName
     */
    public void setProcessName(java.lang.String processName) {
        this.processName = processName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ActivityReference)) return false;
        ActivityReference other = (ActivityReference) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.activityName==null && other.getActivityName()==null) || 
             (this.activityName!=null &&
              this.activityName.equals(other.getActivityName()))) &&
            ((this.activityModelId==null && other.getActivityModelId()==null) || 
             (this.activityModelId!=null &&
              this.activityModelId.equals(other.getActivityModelId()))) &&
            ((this.moduleName==null && other.getModuleName()==null) || 
             (this.moduleName!=null &&
              this.moduleName.equals(other.getModuleName()))) &&
            ((this.moduleVersion==null && other.getModuleVersion()==null) || 
             (this.moduleVersion!=null &&
              this.moduleVersion.equals(other.getModuleVersion()))) &&
            ((this.processName==null && other.getProcessName()==null) || 
             (this.processName!=null &&
              this.processName.equals(other.getProcessName())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getActivityName() != null) {
            _hashCode += getActivityName().hashCode();
        }
        if (getActivityModelId() != null) {
            _hashCode += getActivityModelId().hashCode();
        }
        if (getModuleName() != null) {
            _hashCode += getModuleName().hashCode();
        }
        if (getModuleVersion() != null) {
            _hashCode += getModuleVersion().hashCode();
        }
        if (getProcessName() != null) {
            _hashCode += getProcessName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ActivityReference.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://common.api.pfe.n2.tibco.com", "ActivityReference"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("activityName");
        attrField.setXmlName(new javax.xml.namespace.QName("", "activityName"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("activityModelId");
        attrField.setXmlName(new javax.xml.namespace.QName("", "activityModelId"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("moduleName");
        attrField.setXmlName(new javax.xml.namespace.QName("", "moduleName"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("moduleVersion");
        attrField.setXmlName(new javax.xml.namespace.QName("", "moduleVersion"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("processName");
        attrField.setXmlName(new javax.xml.namespace.QName("", "processName"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
