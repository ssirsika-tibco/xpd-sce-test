/**
 * DataPayload.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.tibco.n2.pfe.services.pageflowType;


/**
 * Payload data.
 */
public class DataPayload  implements java.io.Serializable {
    /* JSON data stream (string). */
    private java.lang.String serializedPayload;

    /* XML data stream (DataModel). */
    private com.tibco.n2.pfe.services.pageflowType.DataModel xmlPayload;

    private com.tibco.n2.pfe.services.pageflowType.PayloadModeType payloadMode;  // attribute

    public DataPayload() {
    }

    public DataPayload(
           java.lang.String serializedPayload,
           com.tibco.n2.pfe.services.pageflowType.DataModel xmlPayload,
           com.tibco.n2.pfe.services.pageflowType.PayloadModeType payloadMode) {
           this.serializedPayload = serializedPayload;
           this.xmlPayload = xmlPayload;
           this.payloadMode = payloadMode;
    }


    /**
     * Gets the serializedPayload value for this DataPayload.
     * 
     * @return serializedPayload   * JSON data stream (string).
     */
    public java.lang.String getSerializedPayload() {
        return serializedPayload;
    }


    /**
     * Sets the serializedPayload value for this DataPayload.
     * 
     * @param serializedPayload   * JSON data stream (string).
     */
    public void setSerializedPayload(java.lang.String serializedPayload) {
        this.serializedPayload = serializedPayload;
    }


    /**
     * Gets the xmlPayload value for this DataPayload.
     * 
     * @return xmlPayload   * XML data stream (DataModel).
     */
    public com.tibco.n2.pfe.services.pageflowType.DataModel getXmlPayload() {
        return xmlPayload;
    }


    /**
     * Sets the xmlPayload value for this DataPayload.
     * 
     * @param xmlPayload   * XML data stream (DataModel).
     */
    public void setXmlPayload(com.tibco.n2.pfe.services.pageflowType.DataModel xmlPayload) {
        this.xmlPayload = xmlPayload;
    }


    /**
     * Gets the payloadMode value for this DataPayload.
     * 
     * @return payloadMode
     */
    public com.tibco.n2.pfe.services.pageflowType.PayloadModeType getPayloadMode() {
        return payloadMode;
    }


    /**
     * Sets the payloadMode value for this DataPayload.
     * 
     * @param payloadMode
     */
    public void setPayloadMode(com.tibco.n2.pfe.services.pageflowType.PayloadModeType payloadMode) {
        this.payloadMode = payloadMode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DataPayload)) return false;
        DataPayload other = (DataPayload) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.serializedPayload==null && other.getSerializedPayload()==null) || 
             (this.serializedPayload!=null &&
              this.serializedPayload.equals(other.getSerializedPayload()))) &&
            ((this.xmlPayload==null && other.getXmlPayload()==null) || 
             (this.xmlPayload!=null &&
              this.xmlPayload.equals(other.getXmlPayload()))) &&
            ((this.payloadMode==null && other.getPayloadMode()==null) || 
             (this.payloadMode!=null &&
              this.payloadMode.equals(other.getPayloadMode())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getSerializedPayload() != null) {
            _hashCode += getSerializedPayload().hashCode();
        }
        if (getXmlPayload() != null) {
            _hashCode += getXmlPayload().hashCode();
        }
        if (getPayloadMode() != null) {
            _hashCode += getPayloadMode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DataPayload.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://datafeed.common.n2.tibco.com", "dataPayload"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("payloadMode");
        attrField.setXmlName(new javax.xml.namespace.QName("", "payloadMode"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://datafeed.common.n2.tibco.com", "payloadModeType"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serializedPayload");
        elemField.setXmlName(new javax.xml.namespace.QName("", "serializedPayload"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("xmlPayload");
        elemField.setXmlName(new javax.xml.namespace.QName("", "XmlPayload"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://datamodel.common.n2.tibco.com", "DataModel"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
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
