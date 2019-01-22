/**
 * PageData.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.tibco.n2.pfe.services.pageflowType;

/**
 * Complete data for the page.
 */
public class PageData implements java.io.Serializable {
	/* Reference details for the page. */
	private com.tibco.n2.pfe.services.pageflowType.PageReference pageReference;

	/* Data payload for the page. */
	private com.tibco.n2.pfe.services.pageflowType.DataPayload payload;

	public PageData() {
	}

	public PageData(com.tibco.n2.pfe.services.pageflowType.PageReference pageReference, com.tibco.n2.pfe.services.pageflowType.DataPayload payload) {
		this.pageReference = pageReference;
		this.payload = payload;
	}

	/**
	 * Gets the pageReference value for this PageData.
	 * 
	 * @return pageReference * Reference details for the page.
	 */
	public com.tibco.n2.pfe.services.pageflowType.PageReference getPageReference() {
		return pageReference;
	}

	/**
	 * Sets the pageReference value for this PageData.
	 * 
	 * @param pageReference
	 *            * Reference details for the page.
	 */
	public void setPageReference(com.tibco.n2.pfe.services.pageflowType.PageReference pageReference) {
		this.pageReference = pageReference;
	}

	/**
	 * Gets the payload value for this PageData.
	 * 
	 * @return payload * Data payload for the page.
	 */
	public com.tibco.n2.pfe.services.pageflowType.DataPayload getPayload() {
		return payload;
	}

	/**
	 * Sets the payload value for this PageData.
	 * 
	 * @param payload
	 *            * Data payload for the page.
	 */
	public void setPayload(com.tibco.n2.pfe.services.pageflowType.DataPayload payload) {
		this.payload = payload;
	}

	private java.lang.Object __equalsCalc = null;

	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof PageData))
			return false;
		PageData other = (PageData) obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = true
				&& ((this.pageReference == null && other.getPageReference() == null) || (this.pageReference != null && this.pageReference
						.equals(other.getPageReference())))
				&& ((this.payload == null && other.getPayload() == null) || (this.payload != null && this.payload.equals(other.getPayload())));
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
		if (getPageReference() != null) {
			_hashCode += getPageReference().hashCode();
		}
		if (getPayload() != null) {
			_hashCode += getPayload().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	// Type metadata
	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(PageData.class, true);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName("http://common.api.pfe.n2.tibco.com", "PageData"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("pageReference");
		elemField.setXmlName(new javax.xml.namespace.QName("", "pageReference"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://common.api.pfe.n2.tibco.com", "PageReference"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("payload");
		elemField.setXmlName(new javax.xml.namespace.QName("", "payload"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://datafeed.common.n2.tibco.com", "dataPayload"));
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
	public static org.apache.axis.encoding.Serializer getSerializer(java.lang.String mechType, java.lang.Class _javaType,
			javax.xml.namespace.QName _xmlType) {
		return new org.apache.axis.encoding.ser.BeanSerializer(_javaType, _xmlType, typeDesc);
	}

	/**
	 * Get Custom Deserializer
	 */
	public static org.apache.axis.encoding.Deserializer getDeserializer(java.lang.String mechType, java.lang.Class _javaType,
			javax.xml.namespace.QName _xmlType) {
		return new org.apache.axis.encoding.ser.BeanDeserializer(_javaType, _xmlType, typeDesc);
	}

}
