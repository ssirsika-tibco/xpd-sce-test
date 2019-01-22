/* 
** 
**  MODULE:             $RCSfile: XpdlComplexData.java $ 
**                      $Revision: 1.8 $ 
**                      $Date: 2005/03/21 09:08:19Z $ 
** 
** DESCRIPTION    :           
**                                              
** 
**  ENVIRONMENT:  Java - Platform independent 
** 
**  COPYRIGHT:    (c) 2004 Staffware plc, All Rights Reserved. 
** 
**  MODIFICATION HISTORY: 
** 
**    $Log: XpdlComplexData.java $ 
**    Revision 1.8  2005/03/21 09:08:19Z  tstephen 
**    comment and spelling only 
**    Revision 1.7  2004/05/05 16:03:22Z  WojciechZ 
**    Checked exception on setting complex data (put) 
**    Revision 1.6  2004/04/14 08:32:53Z  WojciechZ 
**    finished save/restore interaction state 
**    Revision 1.5  2004/04/08 16:02:21Z  WojciechZ 
**    code review 
**    move to apache xml beans 
**    xpdl data use xml beans to hold the data 
**    Revision 1.4  2004/04/02 14:19:46Z  WojciechZ 
**    work up to 02/04/2004 
**    Revision 1.3  2004/04/01 11:00:40Z  WojciechZ 
**    Work up to 01/04/2004 (working) 
**    Revision 1.2  2004/03/29 16:27:30Z  WojciechZ 
**    work up to 29/03/2004 
**    Revision 1.1  2004/03/26 15:08:16Z  WojciechZ 
**    Initial revision 
**    Revision 1.0  24-Mar-2004  WojciechZ 
**    Initial revision 
** 
*/
package com.tibco.inteng.xpdldata;

import java.util.List;

import com.tibco.inteng.exceptions.XpdlDataFormatException;

/**
 * Representation of complex data in xpdl process.<br>
 * 
 * @author WojciechZ
 */
public interface XpdlComplexData extends XpdlData {
	/** field/sub-field name delimeter */
	char DELIM = '/';
    /** delimeters for arrays */
    String LEFT_DELIM = "{";
    /** delimeters for arrays */
    String RIGHT_DELIM = "}";
	/**
	 * @return List of ptoperties (as XpdlData)
	 */
	 List getProperties();
	/**
	 * @return List of simple properties from all subelements
	 */
	 List getAllSimpleProperties();
	/**
	 * @return Number of properties
	 */
	 int getPropertiesSize();
	/**
	 * get subproperty as XpdlData<br>
	 * you can select any subproperty using syntax: 'prop1/subprop2{4}/prop3'
	 * using '/' as delimeter for sub properties and '{}' for selecting 
	 * indexed property 
	 * 
	 * @param key name of subproperty
	 * @return subproperty as XpdlData
	 */
	 XpdlData get(String key);
	/**
	 * Set value of selected subproperty. you can select any subproperty with the same syntax
	 * as in <code>get</code> method.
	 * 
	 * @param key name of subproperty
	 * @param value value to set
	 * @return this obhect
     * @throws XpdlDataFormatException when it is impossible to set value
	 */
	 Object put(Object key, Object value) throws XpdlDataFormatException;
}