/**
 * ListingRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.tibco.n2.pfe.services.pageflowType;


/**
 * Generic request description.
 */
public class ListingRequest  extends com.tibco.n2.pfe.services.pageflowType.PagingAndSorting  implements java.io.Serializable {
    /* Boolean value defining whether the response should include
     * items that have formal parameters.
     * 
     * If not specified the default value is FALSE. */
    private java.lang.Boolean includeFormalParameters;

    public ListingRequest() {
    }

    public ListingRequest(
    		com.tibco.n2.pfe.services.pageflowType.OrderFilterCriteria orderFilterCriteria,
           long startPosition,
           long numberOfItems,
           java.lang.Boolean getTotalCount,
           java.lang.Boolean includeFormalParameters) {
        super(orderFilterCriteria, startPosition, numberOfItems, getTotalCount);
        this.includeFormalParameters = includeFormalParameters;
    }


    /**
     * Gets the includeFormalParameters value for this ListingRequest.
     * 
     * @return includeFormalParameters   * Boolean value defining whether the response should include
     * items that have formal parameters.
     * 
     * If not specified the default value is FALSE.
     */
    public java.lang.Boolean getIncludeFormalParameters() {
        return includeFormalParameters;
    }


    /**
     * Sets the includeFormalParameters value for this ListingRequest.
     * 
     * @param includeFormalParameters   * Boolean value defining whether the response should include
     * items that have formal parameters.
     * 
     * If not specified the default value is FALSE.
     */
    public void setIncludeFormalParameters(java.lang.Boolean includeFormalParameters) {
        this.includeFormalParameters = includeFormalParameters;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ListingRequest)) return false;
        ListingRequest other = (ListingRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.includeFormalParameters==null && other.getIncludeFormalParameters()==null) || 
             (this.includeFormalParameters!=null &&
              this.includeFormalParameters.equals(other.getIncludeFormalParameters())));
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
        if (getIncludeFormalParameters() != null) {
            _hashCode += getIncludeFormalParameters().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ListingRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://common.api.pfe.n2.tibco.com", "ListingRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("includeFormalParameters");
        elemField.setXmlName(new javax.xml.namespace.QName("", "includeFormalParameters"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
