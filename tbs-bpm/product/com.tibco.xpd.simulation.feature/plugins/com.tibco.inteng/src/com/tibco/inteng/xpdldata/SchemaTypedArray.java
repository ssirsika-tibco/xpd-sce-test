/* 
 ** 
 **  MODULE:             $RCSfile: SchemaTypedArray.java $ 
 **                      $Revision: 1.4 $ 
 **                      $Date: 2004/08/11 10:13:10Z $ 
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
 **    $Log: SchemaTypedArray.java $ 
 **    Revision 1.4  2004/08/11 10:13:10Z  WojciechZ 
 **    Revision 1.3  2004/06/21 08:15:33Z  WojciechZ 
 **    change root element type to SchemaTypedField, plus some code cleaning 
 **    Revision 1.2  2004/04/16 08:53:37Z  WojciechZ 
 **    work up to 16/04/2004 
 **    Revision 1.1  2004/04/15 07:56:46Z  WojciechZ 
 **    Initial revision 
 **    Revision 1.0  08-Apr-2004  WojciechZ 
 **    Initial revision 
 ** 
 */
package com.tibco.inteng.xpdldata;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlObject;

import com.tibco.inteng.exceptions.XpdlException;

/**
 * XPDL interface for array property.<br>
 * <br>
 * selection with key: /name/array, where array is an array property (with 
 * maxOccurs > 1) result with this object. 
 * 
 * @author WojciechZ
 */
public class SchemaTypedArray implements XpdlData {
    /**
     * array of XmlObjects returned by XPath selection
     */
    private XmlObject[] array;
    /**
     * key name, (i.e. 'name/subname')
     */
    private String name;
    /**
     * root XpdlData 
     */
    private SchemaTypedField root;
    /** log4j */
    private Logger log = Logger.getLogger(this.getClass());
    /**
     * Initialize array with result form XPath, the result must not be empty
     * 
     * @param array result from XPath selection
     * @param name key (before conferting to XPath)
     * @param root root xpdl data
     */
    protected SchemaTypedArray(XmlObject[] array, String name,
            SchemaTypedField root) {
        this.array = array;
        this.name = name;
        this.root = root;
    }
    /**
     * The constructor
     * 
     * @param parent - parent element
     * @param propertyName - name of the property
     * @param root - root XpdlData element
     */
    protected SchemaTypedArray(XmlObject parent, String propertyName,
            SchemaTypedField root) {
        this.root = root;
        this.array = parent.selectPath(propertyName);
    }

    /**
     * @see com.tibco.inteng.xpdldata.XpdlData#getType()
     */
    public String getType() {
        return XPDL_ARRAY;
    }

    /**
     * @see com.tibco.inteng.xpdldata.XpdlData#getValue()
     */
    public Object getValue() {
        return null;
    }

    /**
     * @see com.tibco.inteng.xpdldata.XpdlData#setValue(java.lang.Object)
     */
    public void setValue(Object value) {
        // ignore
    }

    /**
     * @see com.tibco.inteng.xpdldata.XpdlData#validate()
     */
    public boolean validate() {
        boolean result = true;
        for (int i = 0; i < array.length; i++) {
            if (!array[i].validate()) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * @see com.tibco.inteng.xpdldata.XpdlData#getMessage()
     */
    public String getMessage() {
        return null;
    }

    /**
     * @see com.tibco.inteng.xpdldata.XpdlData#getName()
     */
    public String getName() {
        return name;
    }

    /**
     * @see com.tibco.inteng.xpdldata.XpdlData#isArray()
     */
    public boolean isArray() {
        return true;
    }

    /**
     * @see com.tibco.inteng.xpdldata.XpdlData#getIndexedValue(int)
     */
    public XpdlData getIndexedValue(int index) {
        return new SchemaTypedData(root, array[index]);
    }

    /**
     * @see com.tibco.inteng.xpdldata.XpdlData#setIndexedValue(int, com.tibco.inteng.xpdldata.XpdlData)
     */
    public void setIndexedValue(int index, XpdlData value) {
        XpdlException e = new XpdlException(
                "Setting indexed data is not supported.");
        log.error(e);
        throw e;
    }

    /**
     * @see com.tibco.inteng.xpdldata.XpdlData#getArraySize()
     */
    public int getArraySize() {
        return array.length;
    }

    /**
     * @see com.tibco.inteng.xpdldata.XpdlData#getRestrictionsData()
     */
    public String getRestrictionsData() {
        return null;
    }

    /**
     * @see com.tibco.inteng.xpdldata.XpdlData#isMandatory()
     */
    public boolean isMandatory() {
        return false;
    }

    /**
     * @see com.tibco.inteng.xpdldata.XpdlData#isReadonly()
     */
    public boolean isReadonly() {
        return root.isReadonly();
    }

    /**
     * @see com.tibco.inteng.xpdldata.XpdlData#getXml()
     */
    public XmlObject getXml() {
        if (array.length == 1) {
            return array[0];
        }
        XpdlException e = new XpdlException("XML for arrays is not supported.");
        log.error(e);
        throw e;
    }
}