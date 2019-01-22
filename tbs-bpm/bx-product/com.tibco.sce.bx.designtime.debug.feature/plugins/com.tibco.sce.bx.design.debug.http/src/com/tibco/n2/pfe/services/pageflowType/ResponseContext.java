/**
 * ResponseContext.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.tibco.n2.pfe.services.pageflowType;


/**
 * Unique identifier for an in-progress pageflow or business service.
 */
public class ResponseContext  implements java.io.Serializable {
    /* Description of the pageflow or business service. */
    private com.tibco.n2.pfe.services.pageflowType.PFETemplate pageFlowTemplate;

    /* Unique identifier for an in-progress pageflow or business service. */
    private com.tibco.n2.pfe.services.pageflowType.ProcessReference processReference;

    public ResponseContext() {
    }

    public ResponseContext(
    	com.tibco.n2.pfe.services.pageflowType.PFETemplate pageFlowTemplate,
    	com.tibco.n2.pfe.services.pageflowType.ProcessReference processReference) {
        	this.pageFlowTemplate = pageFlowTemplate;
        	this.processReference = processReference;
    }


    /**
     * Gets the pageFlowTemplate value for this ResponseContext.
     * 
     * @return pageFlowTemplate   * Description of the pageflow or business service.
     */
    public com.tibco.n2.pfe.services.pageflowType.PFETemplate getPageFlowTemplate() {
        return pageFlowTemplate;
    }


    /**
     * Sets the pageFlowTemplate value for this ResponseContext.
     * 
     * @param pageFlowTemplate   * Description of the pageflow or business service.
     */
    public void setPageFlowTemplate(com.tibco.n2.pfe.services.pageflowType.PFETemplate pageFlowTemplate) {
        this.pageFlowTemplate = pageFlowTemplate;
    }


    /**
     * Gets the processReference value for this ResponseContext.
     * 
     * @return processReference   * Unique identifier for an in-progress pageflow or business service.
     */
    public com.tibco.n2.pfe.services.pageflowType.ProcessReference getProcessReference() {
        return processReference;
    }


    /**
     * Sets the processReference value for this ResponseContext.
     * 
     * @param processReference   * Unique identifier for an in-progress pageflow or business service.
     */
    public void setProcessReference(com.tibco.n2.pfe.services.pageflowType.ProcessReference processReference) {
        this.processReference = processReference;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ResponseContext)) return false;
        ResponseContext other = (ResponseContext) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.pageFlowTemplate==null && other.getPageFlowTemplate()==null) || 
             (this.pageFlowTemplate!=null &&
              this.pageFlowTemplate.equals(other.getPageFlowTemplate()))) &&
            ((this.processReference==null && other.getProcessReference()==null) || 
             (this.processReference!=null &&
              this.processReference.equals(other.getProcessReference())));
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
        if (getPageFlowTemplate() != null) {
            _hashCode += getPageFlowTemplate().hashCode();
        }
        if (getProcessReference() != null) {
            _hashCode += getProcessReference().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ResponseContext.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://common.api.pfe.n2.tibco.com", "ResponseContext"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pageFlowTemplate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pageFlowTemplate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://common.api.pfe.n2.tibco.com", "PFETemplate"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("processReference");
        elemField.setXmlName(new javax.xml.namespace.QName("", "processReference"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://common.api.pfe.n2.tibco.com", "ProcessReference"));
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
