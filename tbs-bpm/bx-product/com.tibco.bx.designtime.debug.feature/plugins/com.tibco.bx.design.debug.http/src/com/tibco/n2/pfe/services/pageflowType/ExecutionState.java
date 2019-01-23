/**
 * ExecutionState.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.tibco.n2.pfe.services.pageflowType;

public class ExecutionState implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected ExecutionState(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _NOT_STARTED = "NOT_STARTED";
    public static final java.lang.String _STARTED = "STARTED";
    public static final java.lang.String _IN_PROGRESS = "IN_PROGRESS";
    public static final java.lang.String _COMPLETED = "COMPLETED";
    public static final java.lang.String _UNKNOWN = "UNKNOWN";
    public static final ExecutionState NOT_STARTED = new ExecutionState(_NOT_STARTED);
    public static final ExecutionState STARTED = new ExecutionState(_STARTED);
    public static final ExecutionState IN_PROGRESS = new ExecutionState(_IN_PROGRESS);
    public static final ExecutionState COMPLETED = new ExecutionState(_COMPLETED);
    public static final ExecutionState UNKNOWN = new ExecutionState(_UNKNOWN);
    public java.lang.String getValue() { return _value_;}
    public static ExecutionState fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        ExecutionState enumeration = (ExecutionState)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static ExecutionState fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(ExecutionState.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://common.api.pfe.n2.tibco.com", "ExecutionState"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
