/* 
 ** 
 **  MODULE:             $RCSfile: ScriptableXpdlData.java $ 
 **                      $Revision: 1.6 $ 
 **                      $Date: 2005/05/12 11:10:13Z $ 
 ** 
 ** DESCRIPTION    :           
 **                                              
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2004 TIBCO Software Inc., All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: ScriptableXpdlData.java $ 
 **    Revision 1.6  2005/05/12 11:10:13Z  KamleshU 
 **    Made change to enfore the syntax for the supported methods on the class 
 **    Revision 1.5  2005/04/29 10:16:51Z  KamleshU 
 **    Made change so that getIndexedValue method can be called on any XpdlData 
 **    Revision 1.4  2005/03/01 19:30:04Z  KamleshU 
 **    Added some more meaningful messages 
 **    Revision 1.3  2005/01/28 12:54:26Z  KamleshU 
 **    Revision 1.2  2004/12/21 18:30:08Z  KamleshU 
 **    Revision 1.1  2004/12/21 17:31:20Z  KamleshU 
 **    Initial revision 
 **    Revision 1.0  10-Dec-2004  KamleshU 
 **    Initial revision 
 ** 
 */
package com.tibco.inteng.xpdldata.scriptable;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.FunctionObject;
import org.mozilla.javascript.Scriptable;

import com.tibco.inteng.exceptions.XpdlDataFormatException;
import com.tibco.inteng.xpdldata.SchemaTypedArray;
import com.tibco.inteng.xpdldata.XpdlComplexData;
import com.tibco.inteng.xpdldata.XpdlData;
import com.tibco.inteng.xpdldata.XpdlSimpleData;

/**
 * This class is a wrapper class for XpdlData which will be used for script 
 * application. This class will be registered with the java script engine. 
 * 
 * @author KamleshU
 */
public class ScriptableXpdlData implements Scriptable {

    private XpdlData data;
    private Context ctx;
    private Scriptable prototype, parent;
    private static Logger logger = Logger.getLogger(ScriptableXpdlData.class);
    /**
     * 
     * @param data
     * @param scope
     * @param ctx
     * 
     */
    public ScriptableXpdlData(XpdlData data, Scriptable scope, Context ctx) {
        this.data = data;
        this.parent = scope;
        this.ctx = ctx;
    }
    /**
     * 
     * @param data
     */
    public ScriptableXpdlData(XpdlData data) {
        this.data = data;
    }
    /* (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#getClassName()
     */
    /**
     * @return String the name of the class
     */
    public String getClassName() {
        return "ScriptableXpdlData";
    }

    /**
     * 
     * @return XpdlData
     */
    public XpdlData getXpdlData() {
        return data;
    }

    /**
     * 
     * Returns the value of the named property or NOT_FOUND.
     *
     * If the property was created using defineProperty, the
     * appropriate getter method is called.
     *
     * @param name the name of the property
     * @param start the object in which the lookup began
     * @return the value of the property (may be null or NOT_FOUND)
     */
    public Object get(String name, Scriptable start) {
        if (logger.isInfoEnabled()) {
            logger.info("entry() get(String name, Scriptable start) " + name);
        }
        if (data == null) {
            throw new IllegalStateException("XpdlData is null");
        }
        if (name == null || name.trim().length() < 1) {
            throw new IllegalArgumentException(
                    "Property name cannot be null or empty");
        }
        FunctionObject function = null;
        Method method = null;
        if (data instanceof XpdlComplexData) {
            if (logger.isDebugEnabled()) {
                logger.debug("data is XpdlComplexData");
            }
            XpdlComplexData complexData = (XpdlComplexData) data;
            try {
                if (name.equals("executeQuery")) {
                    method = this.getClass().getMethod("executeQuery",
                            new Class[]{String.class});
                    function = new FunctionObject("executeQuery", method,
                            this.parent);
                    return function;
                } else if (name.equals("getValue")) {
                    method = this.getClass().getMethod("getValue", null);
                    function = new FunctionObject("getValue", method,
                            this.parent);
                    return function;
                } else if (name.equals("setValue")) {
                    method = this.getClass().getMethod("setValue",
                            new Class[]{Object.class});
                    function = new FunctionObject("setValue", method,
                            this.parent);
                    return function;
                } else if (name.equals("getArraySize")) {
                    method = this.getClass().getMethod("getArraySize", null);
                    function = new FunctionObject("getArraySize", method,
                            this.parent);
                    return function;
                } else if (name.equals("getIndexedValue")) {
                    method = this.getClass().getMethod("getIndexedValue",
                            new Class[]{Integer.class});
                    function = new FunctionObject("getIndexedValue", method,
                            this.parent);
                    return function;
                }
            } catch (NoSuchMethodException nsme) {
                nsme.printStackTrace();
            }
            if (logger.isDebugEnabled()) {
                logger.debug("Calling get function on XpdlComplexData "
                        + "with parameter " + name);
            }
            XpdlData newXpdlData = complexData.get(name);
            if (logger.isDebugEnabled()) {
                logger.debug("Return value of get function on XpdlComplexData "
                        + "with parameter " + name + " is " + newXpdlData);
            }
            if (newXpdlData == null) {
                return Scriptable.NOT_FOUND;
            }
            ScriptableXpdlData scriptData = new ScriptableXpdlData(newXpdlData,
                    this.getParentScope(), this.ctx);
            return scriptData;
        } else if (data instanceof SchemaTypedArray) {
            if (logger.isDebugEnabled()) {
                logger.debug("data is SchemaTypedArray");
            }
            try {
                if (name.equals("getArraySize")) {

                    method = this.getClass().getMethod("getArraySize", null);
                    function = new FunctionObject("getArraySize", method,
                            this.parent);
                    return function;
                }
                if (name.equals("getIndexedValue")) {

                    method = this.getClass().getMethod("getIndexedValue",
                            new Class[]{Integer.class});
                    function = new FunctionObject("getIndexedValue", method,
                            this.parent);
                    return function;
                }
            } catch (NoSuchMethodException nsme) {
                nsme.printStackTrace();
            }
        } else if (data instanceof XpdlSimpleData) {
            // for the case when the data is XpdlSimpleData
            if (logger.isDebugEnabled()) {
                logger.debug("data is XpdlSimpleData");
            }
            try {
                if (name.equals("setValue")) {

                    method = this.getClass().getMethod("setValue",
                            new Class[]{Object.class});
                    function = new FunctionObject("setValue", method,
                            this.parent);
                    return function;
                }
                if (name.equals("getValue")) {

                    method = this.getClass().getMethod("getValue", null);
                    function = new FunctionObject("getValue", method,
                            this.parent);
                    return function;
                }
            } catch (NoSuchMethodException nsme) {
                nsme.printStackTrace();
            }
            XpdlData newXpdlData = (XpdlSimpleData) data.getValue();
            if (newXpdlData == null) {
                return Scriptable.NOT_FOUND;
            }
            ScriptableXpdlData scriptData = new ScriptableXpdlData(newXpdlData,
                    this.getParentScope(), this.ctx);
            return scriptData;
        }
        if (logger.isInfoEnabled()) {
            logger.info("exit() get(String name, Scriptable start)");
        }
        return data.getValue();
    }
    /**
     * 
     * @param index
     * @param start
     * @return Object     
     */
    public Object get(int index, Scriptable start) {
        if (logger.isInfoEnabled()) {
            logger.info("entry() get(int index, Scriptable start)");
        }
        XpdlData newXpdlData = data.getIndexedValue(index);
        if (newXpdlData == null
                || ((newXpdlData instanceof XpdlComplexData) && newXpdlData
                        .getArraySize() < 1)) {
            return Scriptable.NOT_FOUND;
        }
        ScriptableXpdlData scriptData = new ScriptableXpdlData(newXpdlData,
                this.getParentScope(), this.ctx);
        if (logger.isInfoEnabled()) {
            logger.info("exit() get(int index, Scriptable start)");
        }
        return scriptData;
    }

    /**
     * 
     * @param name
     * @param start
     * @return boolean     
     */
    public boolean has(String name, Scriptable start) {
        if (logger.isInfoEnabled()) {
            logger.info("entry() has(String name, Scriptable start)");
        }
        if (data instanceof XpdlComplexData) {
            XpdlComplexData complexData = (XpdlComplexData) data;
            XpdlData newXpdlData = complexData.get(name);
            if (newXpdlData == null) {
                return false;
            }
            return true;
        }
        if (logger.isInfoEnabled()) {
            logger.info("exit() has(String name, Scriptable start)");
        }
        return false;
    }

    /**
     * 
     * @param index
     * @param start
     * @return boolean     
     */
    public boolean has(int index, Scriptable start) {
        if (logger.isInfoEnabled()) {
            logger.info("entry() has(int index, Scriptable start)");
        }
        if (data instanceof SchemaTypedArray) {
            XpdlData newXpdlData = data.getIndexedValue(index);
            if (newXpdlData == null) {
                return false;
            }
            return true;
        }
        if (logger.isInfoEnabled()) {
            logger.info("exit() has(int index, Scriptable start)");
        }
        return false;
    }

    /**
     * 
     * @param name
     * @param start
     * @param value     
     */
    public void put(String name, Scriptable start, Object value) {
        if (logger.isInfoEnabled()) {
            logger
                    .info("entry() put(String name, Scriptable start, Object value)"
                            + value.getClass().getName()
                            + " data is "
                            + data.getXml() + "value of name:: " + name);
        }
        try {
            if (value instanceof ScriptableXpdlData) {
                value = ((ScriptableXpdlData) value).getXpdlData();
            }
            if (data instanceof XpdlComplexData) {
                ((XpdlComplexData) data).get(name).setValue(value);
            } else {
                data.setValue(value);
            }
        } catch (XpdlDataFormatException ex) {
            ex.printStackTrace();
            throw new RuntimeException(" XpdlDataFormatException occurred "
                    + ex.getMessage());
        }
        if (logger.isInfoEnabled()) {
            logger
                    .info("exit() put(String name, Scriptable start, Object value)");
        }
    }

    /**
     * 
     * @param index
     * @param start
     * @param value     
     */
    public void put(int index, Scriptable start, Object value) {
        if (logger.isInfoEnabled()) {
            logger.info("entry()::put(int index,Scriptable start,Object value)"
                    + "index:: " + index + " value:: "
                    + ((ScriptableXpdlData) value).getXpdlData() + "data:: "
                    + data);
        }
        if (value instanceof ScriptableXpdlData) {
            value = ((ScriptableXpdlData) value).getXpdlData();
        }
        if (value instanceof XpdlData) {
            data.setIndexedValue(index, (XpdlData) value);
        } else {
            throw new UnsupportedOperationException(
                    "put(int index, Scriptable start, Object value) does not "
                            + "take any other data than XpdlData");
        }
        if (logger.isInfoEnabled()) {
            logger
                    .info("exit() put(int index, Scriptable start, Object value)");
        }
    }

    /**
     * 
     * @param name     
     */
    public void delete(String name) {
        if (logger.isInfoEnabled()) {
            logger.info("entry() delete(String name)");
        }
        if (logger.isInfoEnabled()) {
            logger.info("exit() delete(String name)");
        }
    }

    /**
     * 
     * @param index     
     */
    public void delete(int index) {
        if (logger.isInfoEnabled()) {
            logger.info("entry() delete(int index)");
        }
        if (logger.isInfoEnabled()) {
            logger.info("exit() delete(int index)");
        }
    }

    /**
     *
     * @return Scriptable     
     */
    public Scriptable getPrototype() {
        if (logger.isInfoEnabled()) {
            logger.info("entry() getPrototype()");
        }
        if (logger.isInfoEnabled()) {
            logger.info("exit() getPrototype()");
        }
        return this.prototype;

    }

    /**
     * 
     * @param prototype     
     */
    public void setPrototype(Scriptable prototype) {
        if (logger.isInfoEnabled()) {
            logger.info("entry() setPrototype(Scriptable prototype)");
        }
        this.prototype = prototype;
        if (logger.isInfoEnabled()) {
            logger.info("exit() setPrototype(Scriptable prototype)");
        }
    }

    /**
     *
     * @return Scriptable     
     */
    public Scriptable getParentScope() {
        if (logger.isInfoEnabled()) {
            logger.info("entry() getParentScope()");
        }
        if (logger.isInfoEnabled()) {
            logger.info("exit() getParentScope()");
        }
        return this.parent;
    }

    /**
     * 
     * @param parent     
     */
    public void setParentScope(Scriptable parent) {
        if (logger.isInfoEnabled()) {
            logger.info("entry() setParentScope(Scriptable parent)");
        }
        this.parent = parent;
        if (logger.isInfoEnabled()) {
            logger.info("exit() setParentScope(Scriptable parent)");
        }
    }

    /**
     *
     * @return Object[]     
     */
    public Object[] getIds() {
        if (logger.isInfoEnabled()) {
            logger.info("entry() getIds()");
        }
        if (logger.isInfoEnabled()) {
            logger.info("exit() getIds()");
        }
        return null;
    }

    /**
     * 
     * @param hint
     * @return Object     
     */
    public Object getDefaultValue(Class hint) {
        if (logger.isInfoEnabled()) {
            logger.info("entry() getDefaultValue(Class hint)" + data != null
                    ? data.getClass().getName()
                    : "XpdlData is null");
        }
        if (logger.isInfoEnabled()) {
            logger.info("exit() getDefaultValue(Class hint)");
        }
        return getValue();
    }

    /**
     *
     * @param instance
     * @return boolean
     */
    public boolean hasInstance(Scriptable instance) {
        if (logger.isInfoEnabled()) {
            logger.info("entry() hasInstance(Scriptable instance)");
        }
        if (!(instance instanceof XpdlData)) {
            return false;
        }
        if (logger.isInfoEnabled()) {
            logger.info("exit() hasInstance(Scriptable instance)");
        }
        return true;
    }
    /**
     * This method is used to return the size of the array 
     * if it a SchemaTypedArray
     * @return
     */
    public Integer getArraySize() {
        if (logger.isInfoEnabled()) {
            logger.info("entry() Integer getArraySize())");
        }
        if (this.data instanceof SchemaTypedArray
                || this.data instanceof XpdlComplexData) {
            int arraySize = this.data.getArraySize();
            return new Integer(arraySize);
        }
        if (logger.isInfoEnabled()) {
            logger.info("exit() Integer getArraySize())");
        }
        throw new UnsupportedOperationException(
                "getArraySize() method is not supported on " + data.getName());
    }
    /**
     * This method is used to return the value at the specified Index
     * if it a SchemaTypedArray
     * @param index
     * @return Object
     * 
     */
    public Object getIndexedValue(Integer index) {
        if (logger.isInfoEnabled()) {
            logger.info("entry() Object getIndexedValue(int index)) " + index);
        }
        if (this.data instanceof SchemaTypedArray
                || this.data instanceof XpdlComplexData) {
            XpdlData d = this.data.getIndexedValue(index.intValue());
            if (d == null
                    || ((d instanceof XpdlComplexData) && d.getArraySize() < 1)) {
                return null;
            }
            return new ScriptableXpdlData(d, this.parent, this.ctx);
        }
        if (logger.isInfoEnabled()) {
            logger.info("exit() Object getIndexedValue(int index))");
        }
        throw new UnsupportedOperationException(
                "getIndexedValue(int) method is not supported on "
                        + data.getName());
    }
    /**
     * 
     * @param path
     * @return
     * @throws XpdlDataFormatException
     */
    public Object executeQuery(String path) {
        if (logger.isInfoEnabled()) {
            logger.info("entry() Object executeQuery(String path)) " + path);
        }
        if (path == null || path.length() < 1 || path.equals("undefined")) {
            throw new UnsupportedOperationException(
                    "Proper syntax for executeQuery function is executeQuery(String)");
        }
        if (this.data instanceof XpdlComplexData) {
            XpdlData d = ((XpdlComplexData) this.data).get(path);
            if (d == null
                    || ((d instanceof XpdlComplexData) && d.getArraySize() < 1)) {
                return null;
            }
            return new ScriptableXpdlData(d, this.parent, this.ctx);
        }
        if (logger.isInfoEnabled()) {
            logger.info("exit() Object executeQuery(String path)) " + path);
        }
        throw new UnsupportedOperationException(
                "executeQuery() method is not supported on " + data.getName());
    }
    /**
     * 
     * @return Object
     */
    public Object getValue() {
        if (logger.isInfoEnabled()) {
            logger.info("entry() Object getValue())");
        }
        if (data == null
                || ((data instanceof XpdlComplexData) && data.getArraySize() < 1)) {
            return null;
        }
        if (data instanceof XpdlComplexData) {
            if (data.getType().equalsIgnoreCase(XpdlData.XPDL_COMPLEX_TYPE)) {
                return data.getXml();
            }
            return data.getValue();
        }
        if (data instanceof XpdlSimpleData) {
            return data.getValue();
        }
        if (logger.isInfoEnabled()) {
            logger.info("exit() Object getValue())");
        }
        throw new UnsupportedOperationException(
                "getValue() method is not supported on " + data.getName());
    }
    /**
     *  
     * @param obj, the value to be set
     */
    public void setValue(Object obj) {
        if (logger.isInfoEnabled()) {
            logger.info("entry() void setValue(Object obj)) " + obj);
        }
        if (obj instanceof org.mozilla.javascript.Undefined) {
            throw new UnsupportedOperationException(
                    "Proper syntax for setValue function is setValue(Object)");
        }
        try {
            if (obj instanceof ScriptableXpdlData) {
                obj = ((ScriptableXpdlData) obj).getXpdlData();
            }
            this.data.setValue(obj);
        } catch (XpdlDataFormatException dfe) {
            dfe.printStackTrace();
            throw new RuntimeException("DataFormatException occurred");
        }
        if (logger.isInfoEnabled()) {
            logger.info("exit() void setValue(Object obj))");
        }
    }
}