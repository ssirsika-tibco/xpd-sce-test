/* 
 ** MODULE: $RCSfile: XpdlSimpleData.java $ 
 ** $Revision: 1.7 $ 
 ** $Date:
 ** 2004/03/29 16:27:31Z $ 
 ** 
 ** DESCRIPTION : 
 ** 
 ** 
 ** ENVIRONMENT: Java - Platform independent 
 ** 
 ** COPYRIGHT: (c) 2003 Staffware plc, All Rights Reserved. 
 ** 
 **
 ** MODIFICATION HISTORY: 
 ** 
 ** $Log: XpdlSimpleData.java $ 
 ** Revision 1.7  2004/11/03 10:00:44Z  KamleshU 
 ** Commented out call to setName function in setValue function 
 ** Revision 1.6  2004/05/13 08:24:07Z  WojciechZ 
 ** fixed problem with setting XpdlData with XmlObject 
 ** Revision 1.5  2004/04/13 16:32:43Z  WojciechZ 
 ** work up to 13/04/2004 
 ** Revision 1.4  2004/04/08 16:02:24Z  WojciechZ 
 ** code review 
 ** move to apache xml beans 
 ** xpdl data use xml beans to hold the data 
 */
package com.tibco.inteng.xpdldata;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlString;

import com.tibco.inteng.utils.XmlUtils;

/**
 * Top level simple Xpdl data: BasicType:STRING
 * 
 * @author WojciechZ
 */
public class XpdlSimpleData implements XpdlData {
    /** log4j */
    private Logger log = Logger.getLogger(this.getClass());
    /** name of the data */
    private String name;
    /** simple string value */
    private String value;
    /** readonly flag */
    private boolean readonly;

    /**
     * The constructor
     * 
     * @param name
     *            of the data field
     */
    public XpdlSimpleData(String name) {
        this.name = name;
    }

    /**
     * @see com.tibco.inteng.XpdlData#getType()
     */
    public String getType() {
        return XPDL_TEXT;
    }

    /**
     * @see com.tibco.inteng.XpdlData#getValue()
     */
    public Object getValue() {
        log.debug("getValue: " + value);
        return value;
    }

    /**
     * @see com.tibco.inteng.XpdlData#setValue(java.lang.Object)
     */
    public void setValue(Object newValue) {
        if (readonly) {
            log.warn("Setting readonly data");
        }
        if (newValue instanceof XpdlData) {
            if (log.isDebugEnabled()) {
                log.debug("setValue (copy) to " + name + ": " + newValue);
            }
            this.value = (String) ((XpdlData) newValue).getValue();
            /*
             * Commented out by kamlesh on 02-Nov-2004 as this results in changing the 
             * name of the datafields/formal paramters of ProcessStateImpl, need to ensure 
             * with Woz
             */
            //this.name = ((XpdlData) newValue).getName();
        } else if (newValue instanceof XmlObject) {
            if (log.isDebugEnabled()) {
                log.debug("setValue (restore) to " + name + ": " + newValue);
            }
            this.value = XmlUtils.getInnerText(((XmlObject) newValue).xmlText());
        } else if (newValue instanceof String) {
            if (log.isDebugEnabled()) {
                log.debug("setValue to " + name + ": " + newValue);
            }
            this.value = XmlUtils.getInnerText((String) newValue);
        } else {
            if (log.isDebugEnabled()) {
                log.debug("setValue to " + name + ": " + newValue);
            }
            if (newValue == null) {
                this.value = null;
            } else {
                this.value = newValue.toString();
            }
        }
    }
    /**
     * @see com.tibco.inteng.XpdlData#validate()
     */
    public boolean validate() {
        return true;
    }

    /**
     * @see com.tibco.inteng.XpdlData#getMessage()
     */
    public String getMessage() {
        return null;
    }

    /**
     * @see com.tibco.inteng.XpdlData#getName()
     */
    public String getName() {
        return name;
    }
    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "XpdlSimple(" + name + "=" + value + ")";
    }

    /** 
     * @see com.tibco.inteng.xpdldata.XpdlData#isArray()
     */
    public boolean isArray() {
        return false;
    }

    /** 
     * @see com.tibco.inteng.xpdldata.XpdlData#getIndexedValue(int)
     */
    public XpdlData getIndexedValue(int index) {
        throw new IllegalStateException("XmldData is not an array.");
    }

    /** 
     * @see com.tibco.inteng.xpdldata.XpdlData#setIndexedValue(int,
     *      com.tibco.inteng.xpdldata.XpdlData)
     */
    public void setIndexedValue(int index, XpdlData value) {
        throw new IllegalStateException("XmldData is not an array.");
    }

    /** 
     * @see com.tibco.inteng.xpdldata.XpdlData#getArraySize()
     */
    public int getArraySize() {
        throw new IllegalStateException("XmldData is not an array.");
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
        return readonly;
    }
    /**
     * Set readonly flag
     * 
     * @param readonly flag to set
     */
    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }

    /**
     * @see com.tibco.inteng.xpdldata.XpdlData#getXml()
     */
    public XmlObject getXml() {
        XmlString string = XmlString.Factory.newInstance();
        if (value == null) {
            string.setStringValue("");
        } else {
            string.setStringValue(value);
        }
        return string;
    }
}