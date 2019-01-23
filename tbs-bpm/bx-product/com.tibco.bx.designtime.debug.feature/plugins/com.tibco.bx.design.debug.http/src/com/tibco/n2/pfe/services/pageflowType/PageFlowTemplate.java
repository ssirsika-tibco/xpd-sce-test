/**
 * PageFlowTemplate.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.tibco.n2.pfe.services.pageflowType;


/**
 * Definition of a pageflow.
 */
public class PageFlowTemplate  extends com.tibco.n2.pfe.services.pageflowType.PFETemplate  implements java.io.Serializable {
    private boolean hasFormalParameters;  // attribute

    public PageFlowTemplate() {
    }

    public PageFlowTemplate(
           java.lang.String moduleName,
           java.lang.String processName,
           java.lang.String version,
           boolean hasFormalParameters) {
        super(
            moduleName,
            processName,
            version);
        this.hasFormalParameters = hasFormalParameters;
    }


    /**
     * Gets the hasFormalParameters value for this PageFlowTemplate.
     * 
     * @return hasFormalParameters
     */
    public boolean isHasFormalParameters() {
        return hasFormalParameters;
    }


    /**
     * Sets the hasFormalParameters value for this PageFlowTemplate.
     * 
     * @param hasFormalParameters
     */
    public void setHasFormalParameters(boolean hasFormalParameters) {
        this.hasFormalParameters = hasFormalParameters;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PageFlowTemplate)) return false;
        PageFlowTemplate other = (PageFlowTemplate) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            this.hasFormalParameters == other.isHasFormalParameters();
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
        _hashCode += (isHasFormalParameters() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PageFlowTemplate.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://common.api.pfe.n2.tibco.com", "PageFlowTemplate"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("hasFormalParameters");
        attrField.setXmlName(new javax.xml.namespace.QName("", "hasFormalParameters"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
