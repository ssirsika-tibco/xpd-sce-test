/**
 * OrderFilterCriteria.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.tibco.n2.pfe.services.pageflowType;


/**
 * **PRIVATE API - Reserved for internal use**
 * 
 * Specification of sort/filter criteria to be applied to a list.
 */
public class OrderFilterCriteria  implements java.io.Serializable {
    /* **PRIVATE API - Reserved for internal use**
     * 
     * Expression defining the sort criteria to be applied to the list. */
    private java.lang.String order;

    /* **PRIVATE API - Reserved for internal use**
     * 
     * Expression defining the filter criteria to be applied to the list. */
    private java.lang.String filter;

    public OrderFilterCriteria() {
    }

    public OrderFilterCriteria(
           java.lang.String order,
           java.lang.String filter) {
           this.order = order;
           this.filter = filter;
    }


    /**
     * Gets the order value for this OrderFilterCriteria.
     * 
     * @return order   * **PRIVATE API - Reserved for internal use**
     * 
     * Expression defining the sort criteria to be applied to the list.
     */
    public java.lang.String getOrder() {
        return order;
    }


    /**
     * Sets the order value for this OrderFilterCriteria.
     * 
     * @param order   * **PRIVATE API - Reserved for internal use**
     * 
     * Expression defining the sort criteria to be applied to the list.
     */
    public void setOrder(java.lang.String order) {
        this.order = order;
    }


    /**
     * Gets the filter value for this OrderFilterCriteria.
     * 
     * @return filter   * **PRIVATE API - Reserved for internal use**
     * 
     * Expression defining the filter criteria to be applied to the list.
     */
    public java.lang.String getFilter() {
        return filter;
    }


    /**
     * Sets the filter value for this OrderFilterCriteria.
     * 
     * @param filter   * **PRIVATE API - Reserved for internal use**
     * 
     * Expression defining the filter criteria to be applied to the list.
     */
    public void setFilter(java.lang.String filter) {
        this.filter = filter;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OrderFilterCriteria)) return false;
        OrderFilterCriteria other = (OrderFilterCriteria) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.order==null && other.getOrder()==null) || 
             (this.order!=null &&
              this.order.equals(other.getOrder()))) &&
            ((this.filter==null && other.getFilter()==null) || 
             (this.filter!=null &&
              this.filter.equals(other.getFilter())));
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
        if (getOrder() != null) {
            _hashCode += getOrder().hashCode();
        }
        if (getFilter() != null) {
            _hashCode += getFilter().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OrderFilterCriteria.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://common.api.pfe.n2.tibco.com", "OrderFilterCriteria"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("order");
        elemField.setXmlName(new javax.xml.namespace.QName("", "order"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("filter");
        elemField.setXmlName(new javax.xml.namespace.QName("", "filter"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
