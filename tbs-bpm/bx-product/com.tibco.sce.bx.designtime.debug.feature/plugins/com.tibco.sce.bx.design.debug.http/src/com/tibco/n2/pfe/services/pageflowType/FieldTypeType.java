/**
 * FieldTypeType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.tibco.n2.pfe.services.pageflowType;

public class FieldTypeType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected FieldTypeType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String StringType = "String";
    public static final java.lang.String DecimalType = "Decimal Number";
    public static final java.lang.String IntegerType = "Integer Number";
    public static final java.lang.String BooleanType = "Boolean";
    public static final java.lang.String DateType = "Date";
    public static final java.lang.String TimeType = "Time";
    public static final java.lang.String DateTimeType = "Date Time";
    public static final java.lang.String PerformerType = "Performer";
    public static final java.lang.String ComplexType = "Complex";
    
    public static final FieldTypeType STRING_TYPE = new FieldTypeType(StringType);
    public static final FieldTypeType DECIMAL_TYPE = new FieldTypeType(DecimalType);
    public static final FieldTypeType INTEGER_TYPE = new FieldTypeType(IntegerType);
    public static final FieldTypeType BOOLEAN_TYPE = new FieldTypeType(BooleanType);
    public static final FieldTypeType DATE_TYPE = new FieldTypeType(DateType);
    public static final FieldTypeType TIME_TYPE = new FieldTypeType(TimeType);
    public static final FieldTypeType DATETIME_TYPE = new FieldTypeType(DateTimeType);
    public static final FieldTypeType PERFORMER_TYPE = new FieldTypeType(PerformerType);
    public static final FieldTypeType COMPLEX_TYPE = new FieldTypeType(ComplexType);
    
    public java.lang.String getValue() { return _value_;}
    public static FieldTypeType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        FieldTypeType enumeration = (FieldTypeType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static FieldTypeType fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FieldTypeType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://datamodel.common.n2.tibco.com", ">FieldType>type"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
