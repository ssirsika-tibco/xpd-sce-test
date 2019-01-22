/* 
 ** 
 **  MODULE:             $RCSfile: SchemaTypedField.java $ 
 **                      $Revision: 1.17 $ 
 **                      $Date: 2005/03/29 15:10:49Z $ 
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
 **    $Log: SchemaTypedField.java $ 
 **    Revision 1.17  2005/03/29 15:10:49Z  pburton 
 **    Performance/memory enhancements. 
 **    Revision 1.16  2004/10/20 08:44:56Z  KamleshU 
 **    Changes made for making the restricted schema work 
 **    Revision 1.15  2004/09/16 15:35:07Z  WojciechZ 
 **    fixed memory leak with XML Cursor 
 **    Revision 1.14  2004/08/12 07:29:03Z  WojciechZ 
 **    Revision 1.13  2004/08/11 09:55:47Z  WojciechZ 
 **    Revision 1.12  2004/06/21 08:36:33Z  WojciechZ 
 **    support for override type and restriction data for particular subelement of the field 
 **    Revision 1.11  2004/06/10 16:30:42Z  WojciechZ 
 **    disabled validation 
 **    Revision 1.10  2004/06/10 10:54:39Z  WojciechZ 
 **    fix: readonly XpdlData 
 **    Revision 1.9  2004/06/09 13:28:07Z  WojciechZ 
 **    disabled validatoin on setting vale from string 
 **    Revision 1.8  2004/05/21 11:01:39Z  WojciechZ 
 **    ejbDelegate takes string as arguments (insted of xmlbeans) 
 **    Revision 1.7  2004/05/18 10:17:41Z  WojciechZ 
 **    Revision 1.6  2004/05/12 11:32:50Z  WojciechZ 
 **    fix for simple schematyped elemend 
 **    work up to 12/05/2004 
 **    Revision 1.5  2004/05/06 07:51:15Z  WojciechZ 
 **    fixed double headers on complex fields 
 **    Revision 1.4  2004/05/05 16:02:40Z  WojciechZ 
 **    Move setValue to base schema type class. it is possible to set value as XmlBean or string to any complex data.  
 **    Checked exception on seting complex data 
 **    Revision 1.3  2004/04/28 14:04:38Z  WojciechZ 
 **    Field's setValue cen be set from string 
 **    Revision 1.2  2004/04/16 08:53:39Z  WojciechZ 
 **    work up to 16/04/2004 
 **    Revision 1.1  2004/04/15 07:56:50Z  WojciechZ 
 **    Initial revision 
 **    Revision 1.0  07-Apr-2004  WojciechZ 
 **    Initial revision  
 ** 
 */
package com.tibco.inteng.xpdldata;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.SchemaProperty;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;

import com.tibco.inteng.exceptions.XpdlDataFormatException;

/**
 * Xpdl data with schema type. 
 * 
 * @author WojciechZ
 */
public class SchemaTypedField extends SchemaTypedBase {
    /** log4j */
    private Logger log = Logger.getLogger(this.getClass());
    /** xslt as text for fields with set extended attribute [field_name]_xlst */
    private String xslt;
    /** root document node */
    private XmlObject document;
    /** readonly flag */
    private boolean readonly;
    /** map of custom restrictions for subelements @see #setRestriction(String, String) */
    private Map restrictions = new HashMap();
    /** map of custom types for subelements */
    private Map types = new HashMap();
    private String name;

    /**
     * @param readonly The readonly flag to set.
     */
    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }
    /**
     * @see com.tibco.inteng.xpdldata.SchemaTypedBase#isReadonly()
     */
    public boolean isReadonly() {
        return readonly;
    }
    /**
     * @param name name of the element
     * @param data xml bean's document with schema type. 
     */
    SchemaTypedField(String name, XmlObject data) {
        super(null, data);
        this.name = name;
        document = data;
        initializeData();
        initializeParentData();
        log.info("constructor: SchemaTypedField");
    }
    /**
     * Set parent data element
     *
     */
    private void initializeParentData() {
        XmlCursor cur = document.newCursor();
        try {
            if (cur.isStartdoc()) {
                if (!cur.toFirstChild()) {
                    throw new IllegalArgumentException("Not root element: "
                            + document);
                }
                setData(cur.getObject());

            } else {
                throw new IllegalArgumentException(
                        "Setting SchemaTypedField with sub elament");
            }
        }
        finally {
            cur.dispose();
        }
    }
    /**
     * Copy constructor
     * 
     * @param source source to copy
     * @param name name of the root element of the data, the name is 
     * not copied from <code>source</code> 
     */
    SchemaTypedField(SchemaTypedField source, String name) {
        super(null, (XmlObject)null);
        this.name = name;
        
        document = source.document.copy();
        initializeParentData();
        log.info("constructor (copy): SchemaTypedField");
    }
    /**
     * @see com.tibco.inteng.xpdldata.SchemaTypedBase#getName()
     */
    public String getName() {
        log.info("enter/: SchemaTypedField.getName");
        return name;
        //return document.schemaType().getDocumentElementName().getLocalPart();
    }
    /**
     * Create new empty document
     *
     */
    private void initializeData() {
        log.info("enter: initializeData");
        XmlObject xmlObject = document;
        SchemaType type = xmlObject.schemaType();
        XmlCursor cursor = xmlObject.newCursor();
        
        try {            
            cursor.toNextToken();
            SchemaProperty[] props = type.getProperties();
            for (int i = 0; i < props.length; i++) {
                if (props[i].getType().isSimpleType()) {
                    buildSimpleElement(cursor, props[i]);
                } else {
                    buildDocument(cursor, props[i]);
                }
            }
        }
        finally {
            cursor.dispose();
        }
        log.info("exit: initializeData");
    }
    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "SchemaTypedField: " + getData().toString();
    }
    /**
     * @see com.tibco.inteng.xpdldata.SchemaTypedBase#setValue(java.lang.Object)
     */
    public void setValue(Object value) throws XpdlDataFormatException {
        if (value instanceof XmlObject) {
            // try to set complex value from xml file
            XmlObject xmlVal = (XmlObject) value;
            XmlCursor cur = getData().newCursor();
            try {
                cur.getObject().set(xmlVal);

            } finally {
                cur.dispose();
            }
        } else {
            super.setValue(value);
        }
    }
    /**
     * get xslt property
     * 
     * @return xslt as string
     */
    public String getXslt() {
        return xslt;
    }
    /**
     * set xslt property 
     * 
     * @param xslt vale to set
     */
    public void setXslt(String xslt) {
        this.xslt = xslt;
    }
    /**
     * @see com.tibco.inteng.xpdldata.SchemaTypedBase#getType()
     */
    public String getType() {
        if (xslt == null) {
            return super.getType();
        }
        return "xslt";
    }
    /**
     * Root complex elements are always mandatory
     * 
     * @see com.tibco.inteng.xpdldata.SchemaTypedBase#isMandatory()
     */
    public boolean isMandatory() {
        return true;
    }
    /**
     * Root element cannot be an array
     * 
     * @see com.tibco.inteng.xpdldata.SchemaTypedBase#isArray()
     */
    public boolean isArray() {
        return false;
    }
    /**
     * @see com.tibco.inteng.xpdldata.SchemaTypedBase#getXml()
     */
    public XmlObject getXml() {
        return document;
    }
    /**
     * Extends SchemaTypedBase, if key equals '.' returns root element as SchemaTypedData
     * 
     * @see com.tibco.inteng.xpdldata.SchemaTypedBase#get(java.lang.String)
     */
    public XpdlData get(String key) {
        if (".".equals(key)) {
            return new SchemaTypedData(this, getData());
        }
        return super.get(key);
    }
    /**
     * @see com.tibco.inteng.xpdldata.SchemaTypedBase#put(java.lang.Object, java.lang.Object)
     */
    public Object put(Object key, Object value) throws XpdlDataFormatException {
        if (".".equals(key)) {
            SchemaTypedData sub = new SchemaTypedData(this, getData());
            sub.setValue(value);
            return sub;
        }
        return super.put(key, value);
    }
    /**
     * @see com.tibco.inteng.xpdldata.SchemaTypedBase#setXmlFromString(java.lang.String)
     */
    protected void setXmlFromString(String txt) throws XpdlDataFormatException {
        log.info("enter: setXmlFromString");
        if (txt.startsWith("<xml-fragment")) {
            super.setXmlFromString(txt);
        } else {
            try {
                if (log.isDebugEnabled()) {
                    log.debug("PARSING: '" + txt + "'");
                }
                XmlOptions opt = new XmlOptions();
                opt.setDocumentType(document.schemaType());
                XmlObject obj = XmlObject.Factory.parse(txt, opt);
                document.set(obj);

                initializeParentData();

            } catch (XmlException e) {
                log.error("Exception when setting ComplexData with String", e);
                throw new XpdlDataFormatException(e);
            }
        }
        log.info("exit: setXmlFromString");
    }

    /**
     * Set custom restriction for given key.
     * <br>
     * Method can be used to set restriction that cannot be
     * deducted from element schema or to override (change) 
     * default restrictions.
     * <br>
     * Restrictions are only stored on root element, all subelements
     * check if there is any restriction for their name before 
     * trying to computer restriction from schema definition. Root element
     * looks for restriction stored on empty string ("")
     * <br>
     * <i>Note:</i> restrictions are not coppied by copy method from
     * XpdlDataFactory. On manual application, the usual pattern 
     * should be: 
     * <li>obtain list of formal parameters
     * <li>set additional (custom) restrictions
     * <li>show data on form (using restrictions)
     * <li>submit data to the interaction state
     * 
     * @param path - path to the subelement on for this restriction
     * @param restriction - restriction desription
     * @return previous restriction for this path
     */
    public String setRestriction(String path, String restriction) {
        return (String) restrictions.put(path, restriction);
    }
    /**
     * Get restriction for given element
     * 
     * @see #setRestriction(String, String)
     * @param path subelement or key
     * @return restriction for subelement or key, or null if not found
     */
    public String getRestriction(String path) {
        return (String) restrictions.get(path);
    }
    /**
     * overriden type of given element
     * 
     * @param path name of the element 
     * @return overriden type for this element
     */
    public String getType(String path) {
        return (String) types.get(path);
    }
    /**
     * set new overriden type for given element 
     * 
     * @param path name of the element 
     * @param typeName type name to set
     * @return previous overriden type for this path
     */
    public String setType(String path, String typeName) {
        return (String) types.put(path, typeName);
    }
}