/**
 * FieldType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.tibco.n2.pfe.services.pageflowType;


/**
 * Defines a single data field (its name, type and an optional value).
 */
public class FieldType  implements java.io.Serializable {
    /* Value of the data field as a simple type. */
    private java.lang.String[] simpleSpec;

    /* Value of the data field as a complex type. */
    private java.lang.Object[] complexSpec;

    private java.lang.String name;  // attribute

    private com.tibco.n2.pfe.services.pageflowType.FieldTypeType type;  // attribute

    private java.lang.Boolean optional;  // attribute

    private boolean array;  // attribute

    public FieldType() {
    }

    public FieldType(
           java.lang.String[] simpleSpec,
           java.lang.Object[] complexSpec,
           java.lang.String name,
           com.tibco.n2.pfe.services.pageflowType.FieldTypeType type,
           java.lang.Boolean optional,
           boolean array) {
           this.simpleSpec = simpleSpec;
           this.complexSpec = complexSpec;
           this.name = name;
           this.type = type;
           this.optional = optional;
           this.array = array;
    }


    /**
     * Gets the simpleSpec value for this FieldType.
     * 
     * @return simpleSpec   * Value of the data field as a simple type.
     */
    public java.lang.String[] getSimpleSpec() {
        return simpleSpec;
    }


    /**
     * Sets the simpleSpec value for this FieldType.
     * 
     * @param simpleSpec   * Value of the data field as a simple type.
     */
    public void setSimpleSpec(java.lang.String[] simpleSpec) {
        this.simpleSpec = simpleSpec;
    }


    /**
     * Gets the complexSpec value for this FieldType.
     * 
     * @return complexSpec   * Value of the data field as a complex type.
     */
    public java.lang.Object[] getComplexSpec() {
        return complexSpec;
    }


    /**
     * Sets the complexSpec value for this FieldType.
     * 
     * @param complexSpec   * Value of the data field as a complex type.
     */
    public void setComplexSpec(java.lang.Object[] complexSpec) {
        this.complexSpec = complexSpec;
    }


    /**
     * Gets the name value for this FieldType.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this FieldType.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the type value for this FieldType.
     * 
     * @return type
     */
    public com.tibco.n2.pfe.services.pageflowType.FieldTypeType getType() {
        return type;
    }


    /**
     * Sets the type value for this FieldType.
     * 
     * @param type
     */
    public void setType(com.tibco.n2.pfe.services.pageflowType.FieldTypeType type) {
        this.type = type;
    }


    /**
     * Gets the optional value for this FieldType.
     * 
     * @return optional
     */
    public java.lang.Boolean getOptional() {
        return optional;
    }


    /**
     * Sets the optional value for this FieldType.
     * 
     * @param optional
     */
    public void setOptional(java.lang.Boolean optional) {
        this.optional = optional;
    }


    /**
     * Gets the array value for this FieldType.
     * 
     * @return array
     */
    public boolean isArray() {
        return array;
    }


    /**
     * Sets the array value for this FieldType.
     * 
     * @param array
     */
    public void setArray(boolean array) {
        this.array = array;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FieldType)) return false;
        FieldType other = (FieldType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.simpleSpec==null && other.getSimpleSpec()==null) || 
             (this.simpleSpec!=null &&
              java.util.Arrays.equals(this.simpleSpec, other.getSimpleSpec()))) &&
            ((this.complexSpec==null && other.getComplexSpec()==null) || 
             (this.complexSpec!=null &&
              java.util.Arrays.equals(this.complexSpec, other.getComplexSpec()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType()))) &&
            ((this.optional==null && other.getOptional()==null) || 
             (this.optional!=null &&
              this.optional.equals(other.getOptional()))) &&
            this.array == other.isArray();
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
        if (getSimpleSpec() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSimpleSpec());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSimpleSpec(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getComplexSpec() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getComplexSpec());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getComplexSpec(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        if (getOptional() != null) {
            _hashCode += getOptional().hashCode();
        }
        _hashCode += (isArray() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FieldType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://datamodel.common.n2.tibco.com", "FieldType"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("name");
        attrField.setXmlName(new javax.xml.namespace.QName("", "name"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("type");
        attrField.setXmlName(new javax.xml.namespace.QName("", "type"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://datamodel.common.n2.tibco.com", ">FieldType>type"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("optional");
        attrField.setXmlName(new javax.xml.namespace.QName("", "optional"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("array");
        attrField.setXmlName(new javax.xml.namespace.QName("", "array"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("simpleSpec");
        elemField.setXmlName(new javax.xml.namespace.QName("", "simpleSpec"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("", "value"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("complexSpec");
        elemField.setXmlName(new javax.xml.namespace.QName("", "complexSpec"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("", "value"));
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
