/**
 * PagingAndSorting.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.tibco.n2.pfe.services.pageflowType;


/**
 * **PRIVATE API - Reserved for internal use**
 * 
 * Paging and sorting parameters to enable clients to manipulate the
 * size of a response into manageable chunks of data.
 */
public class PagingAndSorting  implements java.io.Serializable {
    /* **PRIVATE API - Reserved for internal use**
     * 
     * Specification of sort/filter criteria to be applied to the list. */
    private com.tibco.n2.pfe.services.pageflowType.OrderFilterCriteria orderFilterCriteria;

    private long startPosition;  // attribute

    private long numberOfItems;  // attribute

    private java.lang.Boolean getTotalCount;  // attribute

    public PagingAndSorting() {
    }

    public PagingAndSorting(
    		com.tibco.n2.pfe.services.pageflowType.OrderFilterCriteria orderFilterCriteria,
           long startPosition,
           long numberOfItems,
           java.lang.Boolean getTotalCount) {
           this.orderFilterCriteria = orderFilterCriteria;
           this.startPosition = startPosition;
           this.numberOfItems = numberOfItems;
           this.getTotalCount = getTotalCount;
    }


    /**
     * Gets the orderFilterCriteria value for this PagingAndSorting.
     * 
     * @return orderFilterCriteria   * **PRIVATE API - Reserved for internal use**
     * 
     * Specification of sort/filter criteria to be applied to the list.
     */
    public com.tibco.n2.pfe.services.pageflowType.OrderFilterCriteria getOrderFilterCriteria() {
        return orderFilterCriteria;
    }


    /**
     * Sets the orderFilterCriteria value for this PagingAndSorting.
     * 
     * @param orderFilterCriteria   * **PRIVATE API - Reserved for internal use**
     * 
     * Specification of sort/filter criteria to be applied to the list.
     */
    public void setOrderFilterCriteria(com.tibco.n2.pfe.services.pageflowType.OrderFilterCriteria orderFilterCriteria) {
        this.orderFilterCriteria = orderFilterCriteria;
    }


    /**
     * Gets the startPosition value for this PagingAndSorting.
     * 
     * @return startPosition
     */
    public long getStartPosition() {
        return startPosition;
    }


    /**
     * Sets the startPosition value for this PagingAndSorting.
     * 
     * @param startPosition
     */
    public void setStartPosition(long startPosition) {
        this.startPosition = startPosition;
    }


    /**
     * Gets the numberOfItems value for this PagingAndSorting.
     * 
     * @return numberOfItems
     */
    public long getNumberOfItems() {
        return numberOfItems;
    }


    /**
     * Sets the numberOfItems value for this PagingAndSorting.
     * 
     * @param numberOfItems
     */
    public void setNumberOfItems(long numberOfItems) {
        this.numberOfItems = numberOfItems;
    }


    /**
     * Gets the getTotalCount value for this PagingAndSorting.
     * 
     * @return getTotalCount
     */
    public java.lang.Boolean getGetTotalCount() {
        return getTotalCount;
    }


    /**
     * Sets the getTotalCount value for this PagingAndSorting.
     * 
     * @param getTotalCount
     */
    public void setGetTotalCount(java.lang.Boolean getTotalCount) {
        this.getTotalCount = getTotalCount;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PagingAndSorting)) return false;
        PagingAndSorting other = (PagingAndSorting) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.orderFilterCriteria==null && other.getOrderFilterCriteria()==null) || 
             (this.orderFilterCriteria!=null &&
              this.orderFilterCriteria.equals(other.getOrderFilterCriteria()))) &&
            this.startPosition == other.getStartPosition() &&
            this.numberOfItems == other.getNumberOfItems() &&
            ((this.getTotalCount==null && other.getGetTotalCount()==null) || 
             (this.getTotalCount!=null &&
              this.getTotalCount.equals(other.getGetTotalCount())));
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
        if (getOrderFilterCriteria() != null) {
            _hashCode += getOrderFilterCriteria().hashCode();
        }
        _hashCode += new Long(getStartPosition()).hashCode();
        _hashCode += new Long(getNumberOfItems()).hashCode();
        if (getGetTotalCount() != null) {
            _hashCode += getGetTotalCount().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PagingAndSorting.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://common.api.pfe.n2.tibco.com", "PagingAndSorting"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("startPosition");
        attrField.setXmlName(new javax.xml.namespace.QName("", "startPosition"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("numberOfItems");
        attrField.setXmlName(new javax.xml.namespace.QName("", "numberOfItems"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("getTotalCount");
        attrField.setXmlName(new javax.xml.namespace.QName("", "getTotalCount"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orderFilterCriteria");
        elemField.setXmlName(new javax.xml.namespace.QName("", "orderFilterCriteria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://common.api.pfe.n2.tibco.com", "OrderFilterCriteria"));
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
