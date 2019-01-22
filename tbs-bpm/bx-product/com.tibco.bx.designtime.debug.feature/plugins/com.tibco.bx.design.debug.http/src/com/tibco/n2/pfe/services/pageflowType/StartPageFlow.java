/**
 * StartPageFlow.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.tibco.n2.pfe.services.pageflowType;

public class StartPageFlow  implements java.io.Serializable {
    /* Unique identifier of the pageflow to be started. */
    private com.tibco.n2.pfe.services.pageflowType.PFETemplate pageFlowDefinition;

    /* Data for the formal parameters associated with the pageflow
     * start event.
     * 
     * Note: You cannot determine the names of these formal parameters programmatically.
     * Instead, you must obtain them by examining the process in TIBCO Business
     * Studio. */
    private com.tibco.n2.pfe.services.pageflowType.DataPayload formalParams;

    /* Enumerated value defining the format of the data payload to
     * be used in the response message (XML or JSON). 
     * 
     * If not specified, the default value is JSON.
     * 
     * (RSS is currently not supported.) */
    private com.tibco.n2.pfe.services.pageflowType.PayloadModeType responsePayloadMode;

    /* Timeout value (in minutes) for the pageflow activity. */
    private java.lang.Long cacheTimeout;

    public StartPageFlow() {
    }

    public StartPageFlow(
    		com.tibco.n2.pfe.services.pageflowType.PFETemplate pageFlowDefinition,
    		com.tibco.n2.pfe.services.pageflowType.DataPayload formalParams,
    		com.tibco.n2.pfe.services.pageflowType.PayloadModeType responsePayloadMode,
           java.lang.Long cacheTimeout) {
           this.pageFlowDefinition = pageFlowDefinition;
           this.formalParams = formalParams;
           this.responsePayloadMode = responsePayloadMode;
           this.cacheTimeout = cacheTimeout;
    }


    /**
     * Gets the pageFlowDefinition value for this StartPageFlow.
     * 
     * @return pageFlowDefinition   * Unique identifier of the pageflow to be started.
     */
    public com.tibco.n2.pfe.services.pageflowType.PFETemplate getPageFlowDefinition() {
        return pageFlowDefinition;
    }


    /**
     * Sets the pageFlowDefinition value for this StartPageFlow.
     * 
     * @param pageFlowDefinition   * Unique identifier of the pageflow to be started.
     */
    public void setPageFlowDefinition(com.tibco.n2.pfe.services.pageflowType.PFETemplate pageFlowDefinition) {
        this.pageFlowDefinition = pageFlowDefinition;
    }


    /**
     * Gets the formalParams value for this StartPageFlow.
     * 
     * @return formalParams   * Data for the formal parameters associated with the pageflow
     * start event.
     * 
     * Note: You cannot determine the names of these formal parameters programmatically.
     * Instead, you must obtain them by examining the process in TIBCO Business
     * Studio.
     */
    public com.tibco.n2.pfe.services.pageflowType.DataPayload getFormalParams() {
        return formalParams;
    }


    /**
     * Sets the formalParams value for this StartPageFlow.
     * 
     * @param formalParams   * Data for the formal parameters associated with the pageflow
     * start event.
     * 
     * Note: You cannot determine the names of these formal parameters programmatically.
     * Instead, you must obtain them by examining the process in TIBCO Business
     * Studio.
     */
    public void setFormalParams(com.tibco.n2.pfe.services.pageflowType.DataPayload formalParams) {
        this.formalParams = formalParams;
    }


    /**
     * Gets the responsePayloadMode value for this StartPageFlow.
     * 
     * @return responsePayloadMode   * Enumerated value defining the format of the data payload to
     * be used in the response message (XML or JSON). 
     * 
     * If not specified, the default value is JSON.
     * 
     * (RSS is currently not supported.)
     */
    public com.tibco.n2.pfe.services.pageflowType.PayloadModeType getResponsePayloadMode() {
        return responsePayloadMode;
    }


    /**
     * Sets the responsePayloadMode value for this StartPageFlow.
     * 
     * @param responsePayloadMode   * Enumerated value defining the format of the data payload to
     * be used in the response message (XML or JSON). 
     * 
     * If not specified, the default value is JSON.
     * 
     * (RSS is currently not supported.)
     */
    public void setResponsePayloadMode(com.tibco.n2.pfe.services.pageflowType.PayloadModeType responsePayloadMode) {
        this.responsePayloadMode = responsePayloadMode;
    }


    /**
     * Gets the cacheTimeout value for this StartPageFlow.
     * 
     * @return cacheTimeout   * Timeout value (in minutes) for the pageflow activity.
     */
    public java.lang.Long getCacheTimeout() {
        return cacheTimeout;
    }


    /**
     * Sets the cacheTimeout value for this StartPageFlow.
     * 
     * @param cacheTimeout   * Timeout value (in minutes) for the pageflow activity.
     */
    public void setCacheTimeout(java.lang.Long cacheTimeout) {
        this.cacheTimeout = cacheTimeout;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof StartPageFlow)) return false;
        StartPageFlow other = (StartPageFlow) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.pageFlowDefinition==null && other.getPageFlowDefinition()==null) || 
             (this.pageFlowDefinition!=null &&
              this.pageFlowDefinition.equals(other.getPageFlowDefinition()))) &&
            ((this.formalParams==null && other.getFormalParams()==null) || 
             (this.formalParams!=null &&
              this.formalParams.equals(other.getFormalParams()))) &&
            ((this.responsePayloadMode==null && other.getResponsePayloadMode()==null) || 
             (this.responsePayloadMode!=null &&
              this.responsePayloadMode.equals(other.getResponsePayloadMode()))) &&
            ((this.cacheTimeout==null && other.getCacheTimeout()==null) || 
             (this.cacheTimeout!=null &&
              this.cacheTimeout.equals(other.getCacheTimeout())));
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
        if (getPageFlowDefinition() != null) {
            _hashCode += getPageFlowDefinition().hashCode();
        }
        if (getFormalParams() != null) {
            _hashCode += getFormalParams().hashCode();
        }
        if (getResponsePayloadMode() != null) {
            _hashCode += getResponsePayloadMode().hashCode();
        }
        if (getCacheTimeout() != null) {
            _hashCode += getCacheTimeout().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(StartPageFlow.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://pageflow.api.pfe.n2.tibco.com", ">startPageFlow"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pageFlowDefinition");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pageFlowDefinition"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://common.api.pfe.n2.tibco.com", "PFETemplate"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("formalParams");
        elemField.setXmlName(new javax.xml.namespace.QName("", "formalParams"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://datafeed.common.n2.tibco.com", "dataPayload"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("responsePayloadMode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "responsePayloadMode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://datafeed.common.n2.tibco.com", "payloadModeType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cacheTimeout");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cacheTimeout"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
