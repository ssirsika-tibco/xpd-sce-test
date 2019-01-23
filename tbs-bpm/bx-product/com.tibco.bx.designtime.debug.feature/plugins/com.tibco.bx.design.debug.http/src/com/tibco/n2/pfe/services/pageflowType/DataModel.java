/**
 * DataModel.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.tibco.n2.pfe.services.pageflowType;


/**
 * Sets of INPUT, OUTPUT and INOUT parameters that define the complete
 * data model for a work item or page activity.
 */
public class DataModel  implements java.io.Serializable {
    /* An array of parameters passed to a work item or page activity. */
    private com.tibco.n2.pfe.services.pageflowType.FieldType[] inputs;

    /* An array of parameters passed from a work item or page activity. */
    private com.tibco.n2.pfe.services.pageflowType.FieldType[] outputs;

    /* An array of parameters passed to/from a work item or page activity. */
    private com.tibco.n2.pfe.services.pageflowType.FieldType[] inouts;

    public DataModel() {
    }

    public DataModel(
    	com.tibco.n2.pfe.services.pageflowType.FieldType[] inputs,
    	com.tibco.n2.pfe.services.pageflowType.FieldType[] outputs,
    	com.tibco.n2.pfe.services.pageflowType.FieldType[] inouts) {
        this.inputs = inputs;
        this.outputs = outputs;
        this.inouts = inouts;
    }


    /**
     * Gets the inputs value for this DataModel.
     * 
     * @return inputs   * An array of parameters passed to a work item or page activity.
     */
    public com.tibco.n2.pfe.services.pageflowType.FieldType[] getInputs() {
        return inputs;
    }


    /**
     * Sets the inputs value for this DataModel.
     * 
     * @param inputs   * An array of parameters passed to a work item or page activity.
     */
    public void setInputs(com.tibco.n2.pfe.services.pageflowType.FieldType[] inputs) {
        this.inputs = inputs;
    }

    public com.tibco.n2.pfe.services.pageflowType.FieldType getInputs(int i) {
        return this.inputs[i];
    }

    public void setInputs(int i, com.tibco.n2.pfe.services.pageflowType.FieldType _value) {
        this.inputs[i] = _value;
    }


    /**
     * Gets the outputs value for this DataModel.
     * 
     * @return outputs   * An array of parameters passed from a work item or page activity.
     */
    public com.tibco.n2.pfe.services.pageflowType.FieldType[] getOutputs() {
        return outputs;
    }


    /**
     * Sets the outputs value for this DataModel.
     * 
     * @param outputs   * An array of parameters passed from a work item or page activity.
     */
    public void setOutputs(com.tibco.n2.pfe.services.pageflowType.FieldType[] outputs) {
        this.outputs = outputs;
    }

    public com.tibco.n2.pfe.services.pageflowType.FieldType getOutputs(int i) {
        return this.outputs[i];
    }

    public void setOutputs(int i, com.tibco.n2.pfe.services.pageflowType.FieldType _value) {
        this.outputs[i] = _value;
    }


    /**
     * Gets the inouts value for this DataModel.
     * 
     * @return inouts   * An array of parameters passed to/from a work item or page activity.
     */
    public com.tibco.n2.pfe.services.pageflowType.FieldType[] getInouts() {
        return inouts;
    }


    /**
     * Sets the inouts value for this DataModel.
     * 
     * @param inouts   * An array of parameters passed to/from a work item or page activity.
     */
    public void setInouts(com.tibco.n2.pfe.services.pageflowType.FieldType[] inouts) {
        this.inouts = inouts;
    }

    public com.tibco.n2.pfe.services.pageflowType.FieldType getInouts(int i) {
        return this.inouts[i];
    }

    public void setInouts(int i, com.tibco.n2.pfe.services.pageflowType.FieldType _value) {
        this.inouts[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DataModel)) return false;
        DataModel other = (DataModel) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.inputs==null && other.getInputs()==null) || 
             (this.inputs!=null &&
              java.util.Arrays.equals(this.inputs, other.getInputs()))) &&
            ((this.outputs==null && other.getOutputs()==null) || 
             (this.outputs!=null &&
              java.util.Arrays.equals(this.outputs, other.getOutputs()))) &&
            ((this.inouts==null && other.getInouts()==null) || 
             (this.inouts!=null &&
              java.util.Arrays.equals(this.inouts, other.getInouts())));
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
        if (getInputs() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getInputs());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getInputs(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getOutputs() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getOutputs());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getOutputs(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getInouts() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getInouts());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getInouts(), i);
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
        new org.apache.axis.description.TypeDesc(DataModel.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://datamodel.common.n2.tibco.com", "DataModel"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("inputs");
        elemField.setXmlName(new javax.xml.namespace.QName("", "inputs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://datamodel.common.n2.tibco.com", "FieldType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("outputs");
        elemField.setXmlName(new javax.xml.namespace.QName("", "outputs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://datamodel.common.n2.tibco.com", "FieldType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("inouts");
        elemField.setXmlName(new javax.xml.namespace.QName("", "inouts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://datamodel.common.n2.tibco.com", "FieldType"));
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
