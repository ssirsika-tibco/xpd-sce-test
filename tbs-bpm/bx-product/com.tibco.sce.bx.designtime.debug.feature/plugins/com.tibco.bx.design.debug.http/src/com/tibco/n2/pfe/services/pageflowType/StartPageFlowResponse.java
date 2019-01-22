/**
 * StartPageFlowResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.tibco.n2.pfe.services.pageflowType;

public class StartPageFlowResponse  implements java.io.Serializable {
    /* Pageflow execution state, context and page data. */
    private com.tibco.n2.pfe.services.pageflowType.PageResponse pageResponse;

    public StartPageFlowResponse() {
    }

    public StartPageFlowResponse(
    		com.tibco.n2.pfe.services.pageflowType.PageResponse pageResponse) {
           this.pageResponse = pageResponse;
    }


    /**
     * Gets the pageResponse value for this StartPageFlowResponse.
     * 
     * @return pageResponse   * Pageflow execution state, context and page data.
     */
    public com.tibco.n2.pfe.services.pageflowType.PageResponse getPageResponse() {
        return pageResponse;
    }


    /**
     * Sets the pageResponse value for this StartPageFlowResponse.
     * 
     * @param pageResponse   * Pageflow execution state, context and page data.
     */
    public void setPageResponse(com.tibco.n2.pfe.services.pageflowType.PageResponse pageResponse) {
        this.pageResponse = pageResponse;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof StartPageFlowResponse)) return false;
        StartPageFlowResponse other = (StartPageFlowResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.pageResponse==null && other.getPageResponse()==null) || 
             (this.pageResponse!=null &&
              this.pageResponse.equals(other.getPageResponse())));
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
        if (getPageResponse() != null) {
            _hashCode += getPageResponse().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(StartPageFlowResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://pageflow.api.pfe.n2.tibco.com", ">startPageFlowResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pageResponse");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pageResponse"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://common.api.pfe.n2.tibco.com", "PageResponse"));
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
