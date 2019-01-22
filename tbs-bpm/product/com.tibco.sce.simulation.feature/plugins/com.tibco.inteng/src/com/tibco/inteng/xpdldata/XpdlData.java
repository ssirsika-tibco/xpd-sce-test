/* 
 ** 
 **  MODULE:             $RCSfile: XpdlData.java $ 
 **                      $Revision: 1.11 $ 
 **                      $Date: 2004/07/01 14:40:37Z $ 
 ** 
 ** DESCRIPTION    :           
 **                                              
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2003 Staffware plc, All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: XpdlData.java $ 
 **    Revision 1.11  2004/07/01 14:40:37Z  KamleshU 
 **    Added type for link and file 
 **    Revision 1.10  2004/05/05 08:44:43Z  WojciechZ 
 **    new datatype XPDL_BOOLEAN 
 **    Revision 1.9  2004/04/16 08:53:40Z  WojciechZ 
 **    work up to 16/04/2004 
 **    Revision 1.8  2004/04/15 09:12:17Z  WojciechZ 
 **    Support for TEXTAREA for long xs:string fields 
 **    Revision 1.7  2004/04/14 14:40:38Z  WojciechZ 
 **    added: date picker, xslt formating for data fields 
 **    Revision 1.6  2004/04/13 16:32:45Z  WojciechZ 
 **    work up to 13/04/2004 
 **    Revision 1.5  2004/04/08 16:02:23Z  WojciechZ 
 **    code review 
 **    move to apache xml beans 
 **    xpdl data use xml beans to hold the data 
 **    Revision 1.4  2004/04/02 14:19:46Z  WojciechZ 
 **    work up to 02/04/2004 
 **    Revision 1.3  2004/04/01 11:00:41Z  WojciechZ 
 **    Work up to 01/04/2004 (working) 
 **    Revision 1.2  2004/03/29 16:27:31Z  WojciechZ 
 **    work up to 29/03/2004 
 **    Revision 1.1  2004/03/26 15:08:19Z  WojciechZ 
 **    Initial revision 
 **    Revision 1.1  2004/03/18 13:01:25Z  WojciechZ 
 **    Initial revision 
 **    Revision 1.0  16-Mar-2004 WojciechZ 
 **    Initial revision 
 ** 
 */
package com.tibco.inteng.xpdldata;

import org.apache.xmlbeans.XmlObject;

import com.tibco.inteng.exceptions.XpdlDataFormatException;

/**
 * Interface for simple value in Xpdl, either top level BasicType or simple value of 
 * complex bean's property.
 * 
 * @author WojciechZ
 */
public interface XpdlData {
    /** 
     * type of XpdlData, complex type with subproperties 
     */
    String XPDL_COMPLEX_TYPE = "complexType";
    /** 
     * type of XpdlData, file which will help in generating a file upload widget. 
     */
    String XPDL_FILE = "file";
    /** 
     * type of XpdlData, link which will help in viewing a file on the server. 
     */
    String XPDL_LINK = "link";    
    /**
     *  type of XpdlData, simple type with enum restriction 
     */     
    String XPDL_CHOOSE = "choose";
    /** 
     * type of XpdlData, simple text data with no length restriction or 
     * with length &lt;= 200 
     */
    String XPDL_TEXT = "text";
    /** 
     * type of XpdlData, boolean with 'true' or 'false' value
     */
    String XPDL_BOOLEAN = "boolean";
    /**
     * maximum value of length for text fields.
     * over this length strings are treat as text areas
     */
    int XPDL_TEXT_MAX = 200;
    /** 
     * type of XpdlData, simple text data with lengh restriction over XPDL_TEXT_MAX chars 
     */
    String XPDL_TEXT_AREA = "textArea";
    /** 
     * type of XpdlData, date 
     */
    String XPDL_DATE = "date";
    /** 
     * type of XpdlData, array  
     */
    String XPDL_ARRAY = "array";
    /** 
     * type of XpdlData, datetime 
     */
    String XPDL_DATE_TIME = "dateTime";
    /**
     * Type of data (see constants in <code>XpdlData</code>)
     * 
     * <p>There is only one type for complex data and many types for simple one.
     * This type is mainly used to render diferent control for different data 
     * types and to distinguish between complex and simple types.</p> 
     *  
     * @return name of type 
     */
    String getType();
    /**
     * Simple value of this data, may  be not applicable (returns null) on complex data
     *  
     * @return value of the field 
     */
    Object getValue();
    /**
     * New value for this data object<br>
     *  
     * For simple data value is casted/converted/wraped into proper type of XpdlData,<br>  
     * For complex data value can be:
     * <ul>
     * <li>XmlObject (i.e. from getXml method)</li>
     * <li>String - which is parsed as xml</li>
     * <li>XpdlData - which is coppied</li>
     * </ul>
     *  
     * @param value to set
     * @throws XpdlDataFormatException when vale is invalid according to data type 
     */
    void setValue(Object value) throws XpdlDataFormatException;
    /**
     * Validate if current values are correct according to schema
     *  
     * @return if field value is valid
     */
    boolean validate();
    /** 
     * @return message from last validation
     */
    String getMessage();
    /**
     * If the field is a part of Complex xpdl data, 
     * the whole name is returned: 'field/otherProp/thisData'
     * 
     * @return name of the field 
     */
    String getName();
    /** 
     * @return if it is an array 
     */
    boolean isArray();
    /** 
     * get XpdlData from the array
     * should be called only on array data
     * 
     * @param index index in the array 
     * @return XpdlData from the array
     */
    XpdlData getIndexedValue(int index);
    /**
     * set XpdlData in array.
     * should be called only on array data
     *  
     * @param index in array
     * @param value value to set
     */
    void setIndexedValue(int index, XpdlData value);
    /** 
     * should be called only on array data
     * @return array size 
     */
    int getArraySize();
    /**
     * Restrictions data used to help rendering object.
     * Format depends on type of data (see <code>getType()</code>)
     * i.e. for XPDL_CHOOSE it contain list ov values to chose. 
     *  
     * @return restrictions data */
    String getRestrictionsData();
    /**
     * Mandatory flag is treat different then data type. 
     * All basic fields are mandatory, all schema elements with minOccurs &gt; 0 are mandatory
     *  
     * @return mandatory flag 
     */
    boolean isMandatory();
    /**
     * Read only flag. Data is read only when it is passed as IN parameter to application
     * This flag does not depends on type definition.
     *  
     * @return mandatory flag 
     */
    boolean isReadonly();
    /** 
     * @return xml representation of data 
     */
    XmlObject getXml();
}