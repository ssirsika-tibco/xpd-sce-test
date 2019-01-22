package com.tibco.inteng;

public interface ExtendedAttribute {

	/**
	 * @return Returns the content.
	 */
	public abstract String getContent();

	/**
	 * @return Returns the name.
	 */
	public abstract String getName();

	/**
	 * @return Returns the value.
	 */
	public abstract String getValue();	
	/**
     * @return Returns the xmlbeans definition of the ExtendedAttributeImpl
     */
    public String getXmlString();

}