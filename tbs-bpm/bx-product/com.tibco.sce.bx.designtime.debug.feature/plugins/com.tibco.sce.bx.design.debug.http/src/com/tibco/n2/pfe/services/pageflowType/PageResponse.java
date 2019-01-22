/**
 * PageResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.tibco.n2.pfe.services.pageflowType;


/**
 * Generic container for a business service or pageflow operation
 * response.
 */
public class PageResponse  implements java.io.Serializable {
    /* Reference to a pageflow or business service. */
    private com.tibco.n2.pfe.services.pageflowType.ResponseContext context;

    /* Data for each page of the pageflow or business service. */
    private com.tibco.n2.pfe.services.pageflowType.PageData[] pageData;

    private com.tibco.n2.pfe.services.pageflowType.ExecutionState executionState;  // attribute

    public PageResponse() {
    }

    public PageResponse(
    		com.tibco.n2.pfe.services.pageflowType.ResponseContext context,
    		com.tibco.n2.pfe.services.pageflowType.PageData[] pageData,
    		com.tibco.n2.pfe.services.pageflowType.ExecutionState executionState) {
           this.context = context;
           this.pageData = pageData;
           this.executionState = executionState;
    }


    /**
     * Gets the context value for this PageResponse.
     * 
     * @return context   * Reference to a pageflow or business service.
     */
    public com.tibco.n2.pfe.services.pageflowType.ResponseContext getContext() {
        return context;
    }


    /**
     * Sets the context value for this PageResponse.
     * 
     * @param context   * Reference to a pageflow or business service.
     */
    public void setContext(com.tibco.n2.pfe.services.pageflowType.ResponseContext context) {
        this.context = context;
    }


    /**
     * Gets the pageData value for this PageResponse.
     * 
     * @return pageData   * Data for each page of the pageflow or business service.
     */
    public com.tibco.n2.pfe.services.pageflowType.PageData[] getPageData() {
        return pageData;
    }


    /**
     * Sets the pageData value for this PageResponse.
     * 
     * @param pageData   * Data for each page of the pageflow or business service.
     */
    public void setPageData(com.tibco.n2.pfe.services.pageflowType.PageData[] pageData) {
        this.pageData = pageData;
    }

    public com.tibco.n2.pfe.services.pageflowType.PageData getPageData(int i) {
        return this.pageData[i];
    }

    public void setPageData(int i, com.tibco.n2.pfe.services.pageflowType.PageData _value) {
        this.pageData[i] = _value;
    }


    /**
     * Gets the executionState value for this PageResponse.
     * 
     * @return executionState
     */
    public com.tibco.n2.pfe.services.pageflowType.ExecutionState getExecutionState() {
        return executionState;
    }


    /**
     * Sets the executionState value for this PageResponse.
     * 
     * @param executionState
     */
    public void setExecutionState(com.tibco.n2.pfe.services.pageflowType.ExecutionState executionState) {
        this.executionState = executionState;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PageResponse)) return false;
        PageResponse other = (PageResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.context==null && other.getContext()==null) || 
             (this.context!=null &&
              this.context.equals(other.getContext()))) &&
            ((this.pageData==null && other.getPageData()==null) || 
             (this.pageData!=null &&
              java.util.Arrays.equals(this.pageData, other.getPageData()))) &&
            ((this.executionState==null && other.getExecutionState()==null) || 
             (this.executionState!=null &&
              this.executionState.equals(other.getExecutionState())));
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
        if (getContext() != null) {
            _hashCode += getContext().hashCode();
        }
        if (getPageData() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPageData());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPageData(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getExecutionState() != null) {
            _hashCode += getExecutionState().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PageResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://common.api.pfe.n2.tibco.com", "PageResponse"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("executionState");
        attrField.setXmlName(new javax.xml.namespace.QName("", "executionState"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://common.api.pfe.n2.tibco.com", "ExecutionState"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("context");
        elemField.setXmlName(new javax.xml.namespace.QName("", "context"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://common.api.pfe.n2.tibco.com", "ResponseContext"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pageData");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pageData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://common.api.pfe.n2.tibco.com", "PageData"));
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
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
