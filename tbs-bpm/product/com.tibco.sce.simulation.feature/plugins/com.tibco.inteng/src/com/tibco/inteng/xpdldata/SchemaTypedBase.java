/* 
 ** 
 **  MODULE:             $RCSfile: SchemaTypedBase.java $ 
 **                      $Revision: 1.34 $ 
 **                      $Date: 2005/05/31 16:29:19Z $ 
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
 **    $Log: SchemaTypedBase.java $ 
 **    Revision 1.34  2005/05/31 16:29:19Z  wzurek 
 **    support for seting empty values to xpdl data 
 **    Revision 1.33  2005/04/27 15:15:24Z  KamleshU 
 **    Made changes to allow actual paramters like /claimants/claimItems{0} 
 **    to be passed to applications 
 **    Revision 1.32  2005/03/31 14:18:55Z  pburton 
 **    Fixed problem with cursor disposal. 
 **    Revision 1.31  2005/03/29 15:10:48Z  pburton 
 **    Performance/memory enhancements. 
 **    Revision 1.30  2005/03/01 19:29:45Z  KamleshU 
 **    Added a method to expose the root of the element 
 **    Revision 1.29  2004/09/16 15:34:54Z  WojciechZ 
 **    fixed memory leak with XML Cursor 
 **    Revision 1.28  2004/08/17 10:34:28Z  WojciechZ 
 **    fix: posible to set empty value to optional elements with type that does not allow empty string 
 **    Revision 1.27  2004/08/13 14:07:36Z  WojciechZ 
 **    fix: proper order of new created subelements 
 **    Revision 1.26  2004/08/11 09:55:47Z  WojciechZ 
 **    Revision 1.25  2004/08/02 16:13:27Z  WojciechZ 
 **    New Features: 
 **    - resource locator 
 **    - interaction factory 
 **    - different interface to automatic application (possibility to mix: install predefined aplication and configure it for every call using extended attributes) 
 **    Revision 1.24  2004/07/27 12:52:38Z  WojciechZ 
 **    different method of getting string - using corsor insted of using 'getInnerString()' 
 **    Revision 1.23  2004/07/26 10:35:10Z  KamleshU 
 **    Changed the  getValue method to handle returning the value for the filetype  
 **    Revision 1.22  2004/07/01 15:41:08Z  WojciechZ 
 **    different message when value is invalid, and validation fail on mandatory fields with white chracters 
 **    Revision 1.21  2004/07/01 14:40:29Z  KamleshU 
 **    Added type for link and file 
 **    Revision 1.20  2004/06/28 14:13:53Z  WojciechZ 
 **    multiple interaction threads 
 **    Revision 1.19  2004/06/22 14:17:43Z  WojciechZ 
 **    added cache for datatype 
 **    Revision 1.18  2004/06/21 08:28:10Z  WojciechZ 
 **    fix: non existing elements are generated in correct order insted on the end 
 **    changed root element type to ShemaTypedField 
 **    getType and getRestrictionData check in root for overriden type and restriction 
 **    Revision 1.17  2004/06/10 16:30:19Z  WojciechZ 
 **    fix: get all simple properties from incorrect data 
 **    Revision 1.16  2004/06/10 10:54:38Z  WojciechZ 
 **    fix: readonly XpdlData 
 **    Revision 1.15  2004/06/09 13:27:11Z  WojciechZ 
 **    fix: listing properties works for not filled non mandatory data 
 **    Revision 1.14  2004/06/07 15:37:47Z  WojciechZ 
 **    additional check for null on 'getValue' method 
 **    Revision 1.13  2004/05/21 11:01:39Z  WojciechZ 
 **    ejbDelegate takes string as arguments (insted of xmlbeans) 
 **    Revision 1.12  2004/05/18 09:44:13Z  WojciechZ 
 **    fix: setting SchemaTypedBase from XmlBean 
 **    Revision 1.11  2004/05/14 16:08:03Z  WojciechZ 
 **    text field length restriction 
 **    Revision 1.10  2004/05/14 11:48:45Z  WojciechZ 
 **    mandatory field validation 
 **    Revision 1.9  2004/05/12 12:12:34Z  WojciechZ 
 **    setting subelements from different XpdlData 
 **    Revision 1.8  2004/05/12 11:32:50Z  WojciechZ 
 **    fix for simple schematyped elemend 
 **    work up to 12/05/2004 
 **    Revision 1.7  2004/05/07 12:59:52Z  WojciechZ 
 **    TEXT_AREA depends on maxLength attribute insted of Length 
 **    Revision 1.6  2004/05/06 07:51:14Z  WojciechZ 
 **    fixed double headers on complex fields 
 **    Revision 1.5  2004/05/05 16:02:40Z  WojciechZ 
 **    Move setValue to base schema type class. it is possible to set value as XmlBean or string to any complex data.  
 **    Checked exception on seting complex data 
 **    Revision 1.4  2004/05/05 08:45:40Z  WojciechZ 
 **    new type XPDL_BOOLEAN 
 **    Revision 1.3  2004/04/16 08:53:38Z  WojciechZ 
 **    work up to 16/04/2004 
 **    Revision 1.2  2004/04/15 09:12:16Z  WojciechZ 
 **    Support for TEXTAREA for long xs:string fields 
 **    Revision 1.1  2004/04/15 07:56:47Z  WojciechZ 
 **    Initial revision 
 ** 
 */
package com.tibco.inteng.xpdldata;

import java.math.BigInteger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.SchemaProperty;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlAnySimpleType;
import org.apache.xmlbeans.XmlBoolean;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlDate;
import org.apache.xmlbeans.XmlDateTime;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.impl.store.Path;
import org.apache.xmlbeans.impl.values.XmlValueOutOfRangeException;

import com.tibco.inteng.IntEngConsts;
import com.tibco.inteng.exceptions.XpdlDataFormatException;
import com.tibco.inteng.exceptions.XpdlException;
import com.tibco.inteng.utils.XmlUtils;

/**
 * Base class for storing Xpdl data in xml beans
 * 
 * @author WojciechZ
 */
public abstract class SchemaTypedBase implements XpdlComplexData {
    /** log4j */
    private Logger log = Logger.getLogger(this.getClass());
    /**
     * XmlObject for this data object
     */
    private XmlObject data;
    /**
     * root element for this data
     */
    private SchemaTypedField root;
    /**
     * Regular expresion to check if field name is a pointer to item in array
     */
    private static String FILE_TYPE = "FileType";
    private static String LINK_TYPE = "LinkType";

    protected static Pattern arrayPropPattern = Pattern.compile("(.*)\\"
            + LEFT_DELIM + "(\\d+)\\" + RIGHT_DELIM + "$");
    private String typeCache;

    private XmlObject[] dataArr;
    /**
     * The constructor
     * 
     * @param root root element for this xpdl data 
     * @param data xml bean for this data
     */
    SchemaTypedBase(SchemaTypedField root, XmlObject data) {
        if (root == null) {
            this.root = (SchemaTypedField) this;
        } else {
            this.root = root;
        }
        this.data = data;
    }
    /**
     * 
     * @param root
     * @param dataArr
     */
    SchemaTypedBase(SchemaTypedField root, XmlObject[] dataArr) {
        if (root == null) {
            this.root = (SchemaTypedField) this;
        } else {
            this.root = root;
        }
        if (dataArr.length > 0) {
            this.data = dataArr[0];
        }
        this.dataArr = dataArr;
    }
    /**
     * base xml data should be not be changable
     * 
     * @param data new base XmlObject
     */
    protected void setData(XmlObject data) {
        this.data = data;
    }
    /**
     * @see com.tibco.inteng.xpdldata.XpdlComplexData#getProperties()
     */
    public List getProperties() {

        SchemaProperty[] props = data.schemaType().getElementProperties();
        List result = new ArrayList(props.length);
        XpdlData property;
        XmlCursor cursor = data.newCursor();

        try {
            for (int i = 0; i < props.length; i++) {
                if (props[i].getMaxOccurs() != null
                        && props[i].getMaxOccurs().compareTo(BigInteger.ONE) == 0) {
                    // only ordinary properties, without arrays
                    cursor.push();
                    if (cursor.toChild(props[i].getName())) {
                        property = new SchemaTypedData(getRoot(), cursor
                                .getObject());
                    } else {
                        createNonExistingSubelement(props[i], cursor);
                        property = new SchemaTypedData(getRoot(), cursor
                                .getObject());
                    }
                    result.add(property);
                    cursor.pop();
                }
            }
        } finally {
            cursor.dispose();
        }

        if (log.isInfoEnabled()) {
            log.info("exit: getProperties: " + result.size());
        }

        return result;
    }
    /**
     * Create alement  for ginven property in current location
     * of cursor and move the cursor to start of this element
     * 
     * @param property
     * @param cursor
     */
    private void createNonExistingSubelement(SchemaProperty property,
            XmlCursor cursor) {
        log.info("Create non existing element: " + property.getName());
        cursor.push();

        XmlObject parent = cursor.getObject();
        if (!cursor.toFirstChild()) {
            // empty element, insert as the only child
            log.debug("No subelements, create the first one!");
            cursor.toEndToken();
            cursor.insertElement(property.getName());
            cursor.pop();
        } else {
            // there are some children, we must insert in specific position
            int currentProp = 0;
            int propertyNo = -1;
            SchemaProperty[] props = parent.schemaType().getElementProperties();

            // search for number of property that we are looking for
            for (int i = 0; i < props.length; i++) {
                if (property.getName().equals(props[i].getName())) {
                    propertyNo = i;
                    break;
                }
            }

            if (propertyNo < 0) {
                // should never heppend
                XpdlException e = new XpdlException(
                        "Invalid element to create: " + property.getName());
                log.error(e.getMessage(), e);
                throw e;
            }

            QName currentName = cursor.getName();
            while (currentProp < propertyNo
                    && !props[currentProp].getName().equals(currentName)) {
                currentProp++;
            }
            while (currentProp < propertyNo) {
                if (!cursor.toNextSibling()) {
                    break;
                }
                currentName = cursor.getName();
                while (currentProp < propertyNo
                        && !props[currentProp].getName().equals(currentName)) {
                    currentProp++;
                }
            }
            if (currentProp < propertyNo) {
                // insert as the last child
                log.debug("Create the last one!");
                cursor.pop();
                cursor.push();
                cursor.toEndToken();
                cursor.insertElement(property.getName());
                cursor.pop();
            } else {
                // insert before current element
                log.debug("Insert on specific position: just before: "
                        + cursor.getName());
                cursor.insertElement(property.getName());
                cursor.pop();
            }
        }

        if (!cursor.toChild(property.getName())) {
            throw new XpdlException("Cannot create optional element");
        }
    }
    /**
     * @see com.tibco.inteng.xpdldata.XpdlComplexData#getAllSimpleProperties()
     */
    public List getAllSimpleProperties() {

        SchemaProperty[] props = data.schemaType().getElementProperties();
        List result = new ArrayList(props.length);
        XpdlComplexData property;
        XmlCursor cursor = data.newCursor();

        try {
            for (int i = 0; i < props.length; i++) {
                if (props[i].getMaxOccurs() != null
                        && (props[i].getMaxOccurs().compareTo(BigInteger.ONE) == 0)) {
                    // only ordinary properties, without arrays
                    cursor.push();
                    if (!cursor.toChild(props[i].getName())) {
                        createNonExistingSubelement(props[i], cursor);
                    }
                    property = new SchemaTypedData(getRoot(), cursor
                            .getObject());
                    if (cursor.getObject().schemaType().isSimpleType()) {
                        result.add(property);
                    } else {
                        result.addAll(property.getAllSimpleProperties());
                    }
                    cursor.pop();
                }
            }
        } finally {
            cursor.dispose();
        }

        return result;
    }
    /**
     * @see com.tibco.inteng.xpdldata.XpdlComplexData#getPropertiesSize()
     */
    public int getPropertiesSize() {
        return data.schemaType().getElementProperties().length;
    }
    /**
     * @see com.tibco.inteng.xpdldata.XpdlComplexData#get(java.lang.String)
     */
    public XpdlData get(String key) {
        if (log.isInfoEnabled()) {
            log.info("enter: get(" + key + ")");
        }
        String xPath = keyToXPatch(key);
        if (log.isDebugEnabled()) {
            log.debug("selecting XPath: " + xPath);
        }
        XmlOptions opt = new XmlOptions();
        Map vars = new HashMap();
        vars.put("this", data);
        opt.put(Path._useXbeanForXpath);
        XmlObject[] obj = data.selectPath(xPath, opt);
        if (log.isDebugEnabled()) {
            log.debug("select result size: " + obj.length);
        }
        XpdlData result;
        if (obj == null || obj.length == 0) {
            result = createNonExistingData(key);
        } else {
            SchemaProperty property = getSchemaProperty(obj[0]);
            if (property != null && isArrayProperty(property)) {
                /**
                 * The element might be repeating, but the user might have 
                 * asked for a specific instance of the repeating element.
                 * e.g. /claimants/claimItems{0}
                 */
                if (obj.length > 1) {
                    result = new SchemaTypedArray(obj, getName() + "/" + key,
                            root);
                } else {
                    result = new SchemaTypedData(root, obj);
                }
            } else {
                result = new SchemaTypedData(root, obj);
            }
        }
        if (log.isInfoEnabled()) {
            log.info("exit: get(" + key + ")");
        }
        return result;
    }
    /**
     * Check if key (address in 'aa/bb{3}/cc') is valid 
     * and create emty value vor this key 
     * 
     * @param key key of field to create
     * @return XpdlData attached to new created xml element
     */
    protected XpdlData createNonExistingData(String key) {
        if (log.isInfoEnabled()) {
            log.info("enter: createNonExistingData(" + key + ")");
        }
        if (key.indexOf('[') >= 0) {
            return new SchemaTypedData(getRoot(), new XmlObject[0]);
            //            XpdlException e = new XpdlException(
            //                    "Empty result from conditional query: '" + key
            //                            + "' on data: " + getName());
            //            log.error(e.getMessage(), e);
            //            throw e;
        }
        String[] addr = key.split("/");
        XmlObject root = getData();
        XmlObject obj = null;
        XmlCursor cursor = root.newCursor();

        try {
            if (cursor.isStartdoc()) {
                cursor.toFirstChild();
            }
            QName name;
            for (int i = 0; i < addr.length; i++) {
                if (log.isDebugEnabled()) {
                    log.debug("checking: " + addr[i]);
                }
                Matcher matcher = arrayPropPattern.matcher(addr[i]);
                if (matcher.matches()) {
                    int id = Integer.parseInt(matcher.group(2));
                    name = new QName(matcher.group(1));
                    if (!cursor.toChild(name, id)) {
                        if (log.isDebugEnabled()) {
                            log.debug("creating indexed: " + addr[i]);
                        }
                        XmlObject parent = cursor.getObject();
                        SchemaType parentType = parent.schemaType();
                        SchemaProperty prop = parentType
                                .getElementProperty(name);
                        if (prop == null) {
                            XpdlException e = new XpdlException("Element "
                                    + cursor.getName() + " has no property '"
                                    + name + "'");
                            log.error(e);
                            throw e;
                        }
                        int size = parent.selectPath(prop.getName()
                                .getLocalPart()).length;
                        cursor.push();
                        cursor.toEndToken();
                        for (int j = size; j <= id; j++) {
                            buildDocument(cursor, prop);
                        }
                        cursor.pop();
                        if (!cursor.toChild(name, id)) {
                            throw new XpdlException("Something went wrong...");
                        }
                    }
                } else {
                    name = new QName(addr[i]);
                    if (!cursor.toChild(name)) {
                        obj = cursor.getObject();
                        if (obj == null) {
                            cursor.toParent();
                            obj = cursor.getObject();
                        }
                        SchemaType type = obj.schemaType();
                        SchemaProperty property = type.getElementProperty(name);
                        if (property == null) {
                            XpdlException e = new XpdlException(
                                    "No subelement '" + name + "' in '"
                                            + cursor.getName() + "'");
                            log.error(e.getMessage(), e);
                            throw e;
                        }
                        if (i + 1 == addr.length
                                && (property.getMaxOccurs() == null || property
                                        .getMaxOccurs().compareTo(
                                                BigInteger.ONE) > 0)) {
                            XmlObject parent = cursor.getObject();

                            return new SchemaTypedArray(parent, name
                                    .getLocalPart(), getRoot());
                        }
                        createNonExistingSubelement(property, cursor);
                    }
                }
            }
            obj = cursor.getObject();
            if (obj == null) {
                cursor.toParent();
                obj = cursor.getObject();
            }
        } finally {
            cursor.dispose();
        }

        return new SchemaTypedData(getRoot(), obj);
    }
    /**
     * @see com.tibco.inteng.xpdldata.XpdlComplexData#put(java.lang.Object, java.lang.Object)
     */
    public Object put(Object key, Object value) throws XpdlDataFormatException {
        if (log.isInfoEnabled()) {
            log.info("enter: put(" + key + "," + value + ")");
        }
        XmlObject property;
        try {
            String xPath = keyToXPatch((String) key);
            XmlObject[] obj = data.selectPath(xPath);
            if (obj != null && obj.length > 0) {
                property = obj[0];
                if (property.schemaType().isSimpleType()) {
                    property.set(property.schemaType().newValue(value));
                } else {
                    property = null;
                }
            } else {
                property = null;
            }
        } catch (XmlValueOutOfRangeException e) {
            log.error("Try to set incorrect value: " + value, e);
            throw new XpdlDataFormatException(e);
        }
        if (log.isInfoEnabled()) {
            log.info("exit: put");
        }
        return property;
    }
    /**
     * Type of data, can be set on root element, or can be
     * deducted from schema type:
     * 
     * <li>XPDL_COMPLEX_TYPE - any complex type</li>
     * <li>XPDL_CHOOSE - any simple type with enumeration restriction</li>
     * <li>XPDL_DATE - xs:data</li>
     * <li>XPDL_DATE_TIME - xs:datatime</li>
     * <li>XPDL_BOOLEAN - xs:boolean</li>
     * <li>XPDL_TEXT_AREA - any other with fixed length &gt; XPDL_TEXT_MAX</li>
     * <li>XPDL_TEXT - for any other schema type</li>
     * <br>Type is used mainly for presentation and distinction between simple and complex data
     * 
     * @see com.tibco.inteng.xpdldata.XpdlData#getType()
     */
    public String getType() {
        String result;
        result = getRoot().getType(getName());
        if (result == null) {
            if (typeCache == null) {

                SchemaType schemaType;
                XmlCursor cur = data.newCursor();

                try {
                    schemaType = cur.getObject().schemaType();
                } finally {
                    cur.dispose();
                }

                if (schemaType.isSimpleType()) {
                    XmlAnySimpleType[] enums = schemaType
                            .getEnumerationValues();
                    if (enums != null) {
                        result = XPDL_CHOOSE;
                    } else if (XmlDate.type.getBuiltinTypeCode() == schemaType
                            .getBuiltinTypeCode()) {
                        result = XPDL_DATE;
                    } else if (XmlDateTime.type.getBuiltinTypeCode() == schemaType
                            .getBuiltinTypeCode()) {
                        result = XPDL_DATE_TIME;
                    } else if (XmlBoolean.type.getBuiltinTypeCode() == schemaType
                            .getBuiltinTypeCode()) {
                        result = XPDL_BOOLEAN;
                    } else {
                        // distinction between TEXT and TEXT_AREA 
                        // depends on maxLength facet in schema type
                        XmlAnySimpleType type = schemaType
                                .getFacet(SchemaType.FACET_MAX_LENGTH);
                        if (type != null
                                && Integer.parseInt(type.getStringValue()) > XpdlData.XPDL_TEXT_MAX) {
                            result = XPDL_TEXT_AREA;
                        } else {
                            result = XPDL_TEXT;
                        }
                        QName schemaTypeName = schemaType.getName();
                        if (schemaTypeName != null
                                && schemaTypeName.toString().equalsIgnoreCase(
                                        SchemaTypedBase.FILE_TYPE)) {
                            result = XPDL_FILE;
                        }
                        if (schemaTypeName != null
                                && schemaTypeName.toString().equalsIgnoreCase(
                                        SchemaTypedBase.LINK_TYPE)) {
                            result = XPDL_LINK;
                        }
                    }
                } else {
                    log.info(schemaType.getName() + " is a complex type");
                    result = XPDL_COMPLEX_TYPE;
                    QName schemaTypeName = schemaType.getName();
                    if (schemaTypeName != null
                            && schemaTypeName.toString().equalsIgnoreCase(
                                    SchemaTypedBase.FILE_TYPE)) {
                        result = XPDL_FILE;
                    }
                }
                if (log.isInfoEnabled()) {
                    log.info("Type for " + getName() + " is " + result);
                }
                typeCache = result;
            } else {
                result = typeCache;
            }
        } else {
            if (log.isInfoEnabled()) {
                log.info("Overrided type for " + getName() + " is " + result);
            }
        }
        return result;
    }
    /**
     * @see com.tibco.inteng.xpdldata.XpdlData#getValue()
     */
    public Object getValue() {
        SchemaType type = data.schemaType();
        if (type == null || type.isSimpleType()) {
            String result;
            XmlCursor cur = data.newCursor();

            try {
                result = cur.getTextValue();
            } finally {
                cur.dispose();
            }

            return result;
        } else if (type != null
                && type.getName().toString().equalsIgnoreCase(
                        SchemaTypedBase.FILE_TYPE)) {
            XmlObject[] xObject = data.selectPath(IntEngConsts.FILENAME);
            String toReturn = XmlUtils.getInnerText(xObject[0].xmlText());
            return toReturn;
        }
        return null;
    }
    /**
     * @see com.tibco.inteng.xpdldata.XpdlData#setValue(java.lang.Object)
     */
    public void setValue(Object value) throws XpdlDataFormatException {
        if (log.isDebugEnabled()) {
            log.debug("enter: setValue(" + value + ") for type: "
                    + data.schemaType());
        }
        if (value==null){
            if (!isMandatory()) {
                removeXml();
                return;
            } else {
                throw new XpdlDataFormatException("null cannot be set for mandatory field");
            }
        }
        
        SchemaType type = data.schemaType();
        if (type.isSimpleType()) {
            // try to set simple value
            try {
                if (value instanceof XpdlData) {
                    data.set(type.newValue(((XpdlData) value).getValue()));
                } else {
                    data.set(type.newValue(value));
                }
            } catch (XmlValueOutOfRangeException e) {
                // if value is null or empty, and element is optional, we set empty value
                if ((value == null || "".equals(value) || (value instanceof XpdlData && (""
                        .equals(((XpdlData) value).getValue()))))
                        && !isMandatory()) {

                    removeXml();
                } else {
                    throw new XpdlDataFormatException(e.getMessage(), e);
                }
            }
        } else {
            // complex data
            if (value instanceof XmlObject) {
                // try to set complex value from xml file
                getData().set((XmlObject) value);
            } else if (value instanceof String) {
                // try to parse given string as xml
                setXmlFromString((String) value);
            } else if (value instanceof XpdlData) {

                XmlObject xmlObject = ((XpdlData) value).getXml();
                stripEmptyOptionalFields(xmlObject);
                String txt;
                XmlCursor cur = xmlObject.newCursor();

                try {
                    if (cur.isStartdoc()) {
                        cur.toFirstChild();
                    }
                    txt = cur.getObject().xmlText();
                } finally {
                    cur.dispose();
                }

                setXmlFromString(txt);
            } else {
                // no other types are supported
                XpdlDataFormatException e = new XpdlDataFormatException();
                log.error("Setting complex xpdl data with: " + value, e);
                throw e;
            }
        }
        if (log.isInfoEnabled()) {
            log.info("exit: setValue(" + value + ")");
        }
    }
    
    private void removeXml() {
        XmlCursor cur = null;
        try {
            String name = getName();
            int ind = name.indexOf('/');
            if (ind>=0){
                name = name.substring(ind+1);
            }
            
            cur = data.newCursor();
            cur.removeXml();
            
            SchemaTypedBase dta = (SchemaTypedBase) root.get(name);
            data = dta.data;
        } finally {
            if (cur != null) {
                cur.dispose();
            }
        }
        return;
    }
    /**
     * Set the whole xml data from String.
     * 
     * @param txt - string that contains xml fragment
     * @throws XpdlDataFormatException - when xml fragment is not well formated or document is not valid according to Schema
     */
    protected void setXmlFromString(String txt) throws XpdlDataFormatException {
        log.info("enter: setXmlFromString");
        try {
            if (log.isDebugEnabled()) {
                log.debug("PARSING: '" + txt + "'");
            }
            XmlOptions opt = new XmlOptions();
            opt.setDocumentType(data.schemaType());
            XmlObject obj = XmlObject.Factory.parse(txt, opt);
            data.set(obj);

        } catch (XmlException e) {
            log.error("Exception when setting ComplexData with String", e);
            throw new XpdlDataFormatException(e);
        }
        log.info("exit: setXmlFromString");
    }
    /**
     * @see com.tibco.inteng.xpdldata.XpdlData#validate()
     */
    public boolean validate() {
        if (log.isInfoEnabled()) {
            log.info("enter: validate");
        }
        boolean result = true;
        if (getData().schemaType().isSimpleType()) {
            boolean mand = isMandatory();
            String val = (String) getValue();
            val = val.trim();
            if (!"".equals(val)) {
                // validate value according to schema
                result = data.validate();
            } else if (mand && "".equals(val.trim())) {
                // on value in mandatory field
                result = false;
            }
        } else {
            List props = getAllSimpleProperties();
            for (Iterator iter = props.iterator(); iter.hasNext();) {
                XpdlData element = (XpdlData) iter.next();
                if (!element.validate()) {
                    result = false;
                }
            }
        }
        if (log.isInfoEnabled()) {
            log.info("exit: validate - " + result);
        }
        return result;
    }
    /**
     * @see com.tibco.inteng.xpdldata.XpdlData#getMessage()
     */
    public String getMessage() {
        if (log.isInfoEnabled()) {
            log.info("enter: getMessage");
        }
        String result = null;
        List errors = new ArrayList();
        XmlOptions opt = new XmlOptions();
        opt.setErrorListener(errors);
        String val = (String) getValue();
        val = val.trim();
        if (!data.validate(opt)) {
            result = "validation.invalid";
        } else {
            if (isMandatory() && "".equals(val)) {
                result = "validation.mandatory";
            }
        }
        if (result != null) {
            if ((val == null || "".equals(val)) && !isMandatory()) {
                result = null;
            }
        }
        if (log.isInfoEnabled()) {
            log.info("exit: getMessage - " + result);
        }
        return result;
    }
    /**
     * Name of the element
     * 
     * @see com.tibco.inteng.xpdldata.XpdlData#getName()
     */
    public String getName() {
        return getSchemaProperty(getData()).getName().getLocalPart();
    }
    /**
     * @see com.tibco.inteng.xpdldata.XpdlData#isArray()
     * 
     */
    public boolean isArray() {
        return isArrayProperty(getSchemaProperty(getData()));
    }
    /**
     * @see com.tibco.inteng.xpdldata.XpdlData#getIndexedValue(int)
     */
    public XpdlData getIndexedValue(int index) {
        if (index != 0 && dataArr == null) {
            XpdlException e = new XpdlException(
                    "Access to indexed bean on no array data");
            log.error(e);
            throw e;
        } else if (dataArr == null) {
            return this;
        }
        return new SchemaTypedData(getRoot(), dataArr[index]);
    }
    /**
     * Not supported
     * 
     * @see com.tibco.inteng.xpdldata.XpdlData#setIndexedValue(int, com.tibco.inteng.xpdldata.XpdlData)
     */
    public void setIndexedValue(int index, XpdlData value) {
        XpdlException e = new XpdlException(
                "Access to indexed bean on no array data");
        log.error(e);
        throw e;
    }
    /**
     * Not supported
     * 
     * @see com.tibco.inteng.xpdldata.XpdlData#getArraySize()
     */
    public int getArraySize() {

        if (dataArr != null) {
            return dataArr.length;
        }

        if (isArray()) {
            int result;
            XmlCursor cursor = data.newCursor();

            try {
                QName name = cursor.getName();
                cursor.toParent();
                result = cursor.getObject().selectPath(name.getLocalPart()).length;
            } finally {
                cursor.dispose();
            }

            return result;
        } else
            return 1;
    }

    /**
     * @see com.tibco.inteng.xpdldata.XpdlData#getRestrictionsData()
     */
    public String getRestrictionsData() {
        log.info("enter: getRestrictionsData");
        String restrictionData = root.getRestriction(getName());
        if (restrictionData == null) {
            String type = getType();
            if (XPDL_CHOOSE.equals(type)) {
                XmlAnySimpleType[] enums = data.schemaType()
                        .getEnumerationValues();
                if (enums != null) {
                    StringBuffer result = new StringBuffer(enums[0]
                            .getStringValue());
                    for (int i = 1; i < enums.length; i++) {
                        result.append(",");
                        result.append(enums[i].getStringValue());
                    }
                    restrictionData = result.toString();
                }
            } else if (XPDL_TEXT.equals(type)) {
                SchemaType sType = data.schemaType();
                XmlAnySimpleType val = sType
                        .getFacet(SchemaType.FACET_MAX_LENGTH);
                if (val != null) {
                    restrictionData = val.getStringValue();
                }
            }
            log.info("Restriction data for " + getName() + ": "
                    + restrictionData);
        } else {
            log.info("Custom restriction data for " + getName() + ": "
                    + restrictionData);
        }
        return restrictionData;
    }
    /**
     * @see com.tibco.inteng.xpdldata.XpdlData#isMandatory()
     */
    public boolean isMandatory() {
        return getSchemaProperty(getData()).getMinOccurs().compareTo(
                BigInteger.ZERO) > 0;
    }
    /**
     * check if given property is an array property<br>
     * (array property has no maxOccurs restruction or maxOccurs > 1)
     * 
     * @param property property to check
     * @return true if property is an array property
     */
    private boolean isArrayProperty(SchemaProperty property) {
        return property.getMaxOccurs() == null
                || property.getMaxOccurs().compareTo(BigInteger.ONE) > 0;
    }
    /**
     * @see com.tibco.inteng.xpdldata.XpdlData#isReadonly()
     */
    public boolean isReadonly() {
        SchemaTypedBase r = getRoot();
        return r != this && r != null ? getRoot().isReadonly() : false;
    }
    /**
     * Transfer key in format: /ala/ola{2}/an to /ala/ola[position()=2]/an
     * 
     * @param key key to translate
     * @return xpatch
     */
    private String keyToXPatch(String key) {
        Pattern pattern = Pattern.compile("\\{(\\d+)\\}");
        Matcher matcher = pattern.matcher(key);
        String result = key;
        while (matcher.find()) {
            int pos = Integer.parseInt(matcher.group(1)) + 1;
            result = matcher.replaceFirst("[position()=" + pos + "]");
            matcher = pattern.matcher(result);
        }
        return result;
    }
    /**
     * Helper method. Retrieve property from parent for this XmlObject.
     * 
     * @param obj XmlObject 
     * @return schema property for this object or null if this element has no parent 
     */
    protected SchemaProperty getSchemaProperty(XmlObject obj) {

        SchemaProperty property;
        XmlCursor cursor = obj.newCursor();

        try {
            QName name = cursor.getName();
            if (!cursor.toParent()) {
                log.warn("check for property of root element");
                property = null;
            } else {
                SchemaType type = cursor.getObject().schemaType();
                if (type == null) {
                    XpdlException e = new XpdlException(
                            "Parent element has no type");
                    log.error(e);
                    throw e;
                }
                property = type.getElementProperty(name);
            }
        } finally {
            cursor.dispose();
        }
        return property;
    }
    /**
     * @return Returns the data.
     */
    protected XmlObject getData() {
        return data;
    }
    /**
     * @return Returns the root element.
     */
    public SchemaTypedField getRoot() {
        return root;
    }
    /**
     * Create empty subelements of property starting at given cursor position 
     * in complex type
     * 
     * @param cursor - current position
     * @param prop - property to build
     */
    protected void buildDocument(XmlCursor cursor, SchemaProperty prop) {
        if (log.isInfoEnabled()) {
            log.info("enter: buildDocument - " + prop.getName());
        }
        cursor.push();
        cursor.beginElement(prop.getName());
        SchemaProperty[] props = prop.getType().getProperties();
        for (int i = 0; i < props.length; i++) {
            if (props[i].getMaxOccurs() != null
                    && props[i].getMaxOccurs().compareTo(BigInteger.ONE) == 0) {
                // build it if it is not array
                if (props[i].getType().isSimpleType()) {
                    buildSimpleElement(cursor, props[i]);
                } else {
                    buildDocument(cursor, props[i]);
                }
            }
        }
        cursor.pop();
        cursor.toNextSibling();
        log.info("exit: buildDocument");
    }
    /**
     * Initialize new simple element, empty or with default value from schema
     * 
     * @param cursor where to create
     * @param prop element description
     */
    protected void buildSimpleElement(XmlCursor cursor, SchemaProperty prop) {
        if (prop.hasDefault() != SchemaProperty.NEVER) {
            cursor.insertElementWithText(prop.getName(), prop.getDefaultText());
        } else {
            cursor.insertElement(prop.getName());
        }
    }
    /**
     * @see com.tibco.inteng.xpdldata.XpdlData#getXml()
     */
    public XmlObject getXml() {
        return getData();
    }

    /**
     * Claen unused optional children from xml representation of the XpdlData.
     * 
     * For some purposes (like showing form with all simple properties) xml document must
     * have all subelements create, even empty. For others (like calling a service) all
     * unused xml elements must be removed. 
     */
    public void stripEmptyOptionalFields() {
        stripEmptyOptionalFields(data);
    }
    /**
     * helper method 
     * Remove all optional unused XmlElements from given xml document 
     * 
     * @param xml
     */
    protected static void stripEmptyOptionalFields(XmlObject xml) {
        XmlCursor cur = xml.newCursor();

        try {
            Logger log = Logger.getLogger(SchemaTypedBase.class);
            log.debug("enter: stripEmptyOptionalFields");
            while (cur.toFirstChild()) {
                stripChildren(cur);
                while (cur.toNextSibling()) {
                    stripChildren(cur);
                }
            }
        } finally {
            cur.dispose();
        }
    }
    /**
     * remove all empty optional children from xml object at current
     * location of given cursor
     * 
     * @param cursor - XmlCursor that points to object to clean 
     */
    private static void stripChildren(XmlCursor cursor) {
        Logger log = Logger.getLogger(SchemaTypedBase.class);
        log.debug("enter: stripChildren of " + cursor.getName());
        cursor.push();
        XmlObject parent = cursor.getObject();
        SchemaType parentType = parent.schemaType();
        if (cursor.toFirstChild()) {
            boolean hasSibling;
            do {
                XmlObject child = cursor.getObject();
                SchemaType type = child.schemaType();
                if (type != null && type.isSimpleType()) {
                    SchemaProperty prop = parentType.getElementProperty(cursor
                            .getName());
                    if (BigInteger.ZERO.compareTo(prop.getMinOccurs()) == 0
                            && "".equals(cursor.getTextValue())) {

                        log.debug("removeChild: " + cursor.getName());
                        cursor.removeXml();
                        hasSibling = cursor.getObject() != null;
                    } else {
                        hasSibling = cursor.toNextSibling();
                    }
                } else if (type != null) {
                    stripChildren(cursor);
                    hasSibling = cursor.toNextSibling();
                } else {
                    hasSibling = cursor.toNextSibling();
                }
            } while (hasSibling);
        }
        cursor.pop();
    }
}