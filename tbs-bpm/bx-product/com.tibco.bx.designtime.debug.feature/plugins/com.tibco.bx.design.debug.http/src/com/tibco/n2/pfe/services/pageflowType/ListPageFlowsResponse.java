/**
 * ListPageFlowsResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.tibco.n2.pfe.services.pageflowType;

public class ListPageFlowsResponse  implements java.io.Serializable {
    /* **PRIVATE API - Reserved for internal use** 
     * 
     * Position in the list of the first item on this page. */
    private long startPosition;

    /* **PRIVATE API - Reserved for internal use**
     * 
     * Position in the list of the last item on this page. */
    private long endPosition;

    /* **PRIVATE API - Reserved for internal use**
     * 
     * Total number of items in the list. (If this value is -1 the Page Flow
     * Engine did not build the count of total number of items.) */
    private long totalItems;

    /* Array describing deployed pageflows that match the criteria
     * specified in the request. */
    private com.tibco.n2.pfe.services.pageflowType.PageFlowTemplate[] pageFlowTemplate;

    public ListPageFlowsResponse() {
    }

    public ListPageFlowsResponse(
           long startPosition,
           long endPosition,
           long totalItems,
           com.tibco.n2.pfe.services.pageflowType.PageFlowTemplate[] pageFlowTemplate) {
           this.startPosition = startPosition;
           this.endPosition = endPosition;
           this.totalItems = totalItems;
           this.pageFlowTemplate = pageFlowTemplate;
    }


    /**
     * Gets the startPosition value for this ListPageFlowsResponse.
     * 
     * @return startPosition   * **PRIVATE API - Reserved for internal use** 
     * 
     * Position in the list of the first item on this page.
     */
    public long getStartPosition() {
        return startPosition;
    }


    /**
     * Sets the startPosition value for this ListPageFlowsResponse.
     * 
     * @param startPosition   * **PRIVATE API - Reserved for internal use** 
     * 
     * Position in the list of the first item on this page.
     */
    public void setStartPosition(long startPosition) {
        this.startPosition = startPosition;
    }


    /**
     * Gets the endPosition value for this ListPageFlowsResponse.
     * 
     * @return endPosition   * **PRIVATE API - Reserved for internal use**
     * 
     * Position in the list of the last item on this page.
     */
    public long getEndPosition() {
        return endPosition;
    }


    /**
     * Sets the endPosition value for this ListPageFlowsResponse.
     * 
     * @param endPosition   * **PRIVATE API - Reserved for internal use**
     * 
     * Position in the list of the last item on this page.
     */
    public void setEndPosition(long endPosition) {
        this.endPosition = endPosition;
    }


    /**
     * Gets the totalItems value for this ListPageFlowsResponse.
     * 
     * @return totalItems   * **PRIVATE API - Reserved for internal use**
     * 
     * Total number of items in the list. (If this value is -1 the Page Flow
     * Engine did not build the count of total number of items.)
     */
    public long getTotalItems() {
        return totalItems;
    }


    /**
     * Sets the totalItems value for this ListPageFlowsResponse.
     * 
     * @param totalItems   * **PRIVATE API - Reserved for internal use**
     * 
     * Total number of items in the list. (If this value is -1 the Page Flow
     * Engine did not build the count of total number of items.)
     */
    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }


    /**
     * Gets the pageFlowTemplate value for this ListPageFlowsResponse.
     * 
     * @return pageFlowTemplate   * Array describing deployed pageflows that match the criteria
     * specified in the request.
     */
    public com.tibco.n2.pfe.services.pageflowType.PageFlowTemplate[] getPageFlowTemplate() {
        return pageFlowTemplate;
    }


    /**
     * Sets the pageFlowTemplate value for this ListPageFlowsResponse.
     * 
     * @param pageFlowTemplate   * Array describing deployed pageflows that match the criteria
     * specified in the request.
     */
    public void setPageFlowTemplate(com.tibco.n2.pfe.services.pageflowType.PageFlowTemplate[] pageFlowTemplate) {
        this.pageFlowTemplate = pageFlowTemplate;
    }

    public com.tibco.n2.pfe.services.pageflowType.PageFlowTemplate getPageFlowTemplate(int i) {
        return this.pageFlowTemplate[i];
    }

    public void setPageFlowTemplate(int i, com.tibco.n2.pfe.services.pageflowType.PageFlowTemplate _value) {
        this.pageFlowTemplate[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ListPageFlowsResponse)) return false;
        ListPageFlowsResponse other = (ListPageFlowsResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.startPosition == other.getStartPosition() &&
            this.endPosition == other.getEndPosition() &&
            this.totalItems == other.getTotalItems() &&
            ((this.pageFlowTemplate==null && other.getPageFlowTemplate()==null) || 
             (this.pageFlowTemplate!=null &&
              java.util.Arrays.equals(this.pageFlowTemplate, other.getPageFlowTemplate())));
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
        _hashCode += new Long(getStartPosition()).hashCode();
        _hashCode += new Long(getEndPosition()).hashCode();
        _hashCode += new Long(getTotalItems()).hashCode();
        if (getPageFlowTemplate() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPageFlowTemplate());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPageFlowTemplate(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ListPageFlowsResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://pageflow.api.pfe.n2.tibco.com", ">listPageFlowsResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startPosition");
        elemField.setXmlName(new javax.xml.namespace.QName("", "startPosition"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endPosition");
        elemField.setXmlName(new javax.xml.namespace.QName("", "endPosition"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalItems");
        elemField.setXmlName(new javax.xml.namespace.QName("", "totalItems"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pageFlowTemplate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pageFlowTemplate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://common.api.pfe.n2.tibco.com", "PageFlowTemplate"));
        elemField.setMinOccurs(0);
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
