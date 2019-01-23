/* 
 ** 
 **  MODULE:             $RCSfile: ScriptableLogger.java $ 
 **                      $Revision: 1.1 $ 
 **                      $Date: 2004/12/21 17:31:21Z $ 
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
 **    $Log: ScriptableLogger.java $ 
 **    Revision 1.1  2004/12/21 17:31:21Z  KamleshU 
 **    Initial revision 
 **    Revision 1.0  17-Dec-2004  KamleshU 
 **    Initial revision 
 ** 
 */
package com.tibco.inteng.xpdldata.scriptable;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.FunctionObject;
import org.mozilla.javascript.Scriptable;

/**
 * TODO Description of 'ScriptableLogger' class
 * 
 * @author KamleshU
 */
public class ScriptableLogger implements Scriptable {

    private Logger logger = Logger.getLogger(ScriptableLogger.class);
    private Context ctx;
    private Scriptable parent, prototype;
    /**
     * @param data
     * @param ctx 
     */
    public ScriptableLogger(Scriptable scope, Context ctx) {
        this.parent = scope;
        this.ctx = ctx;
    }
    /* (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#getClassName()
     */
    public String getClassName() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#get(java.lang.String, org.mozilla.javascript.Scriptable)
     */
    public Object get(String name, Scriptable arg1) {
        // TODO Auto-generated method stub
        Method getMethod = null;
        FunctionObject function = null;
        try {
            if (name.equals("debug")) {
                getMethod = this.getClass().getMethod("debug",
                        new Class[]{Object.class});
                function = new FunctionObject("debug", getMethod, this.parent);
            } else if (name.equals("warn")) {
                getMethod = this.getClass().getMethod("warn",
                        new Class[]{Object.class});
                function = new FunctionObject("warn", getMethod, this.parent);
            } else if (name.equals("info")) {
                getMethod = this.getClass().getMethod("info",
                        new Class[]{Object.class});
                function = new FunctionObject("info", getMethod, this.parent);
            } else if (name.equals("error")) {
                getMethod = this.getClass().getMethod("error",
                        new Class[]{Object.class});
                function = new FunctionObject("error", getMethod, this.parent);
            }
        } catch (NoSuchMethodException nsme) {
            nsme.printStackTrace();
        }
        return function;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#get(int, org.mozilla.javascript.Scriptable)
     */
    public Object get(int arg0, Scriptable arg1) {
        // TODO Auto-generated method stub        
        return null;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#has(java.lang.String, org.mozilla.javascript.Scriptable)
     */
    public boolean has(String arg0, Scriptable arg1) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#has(int, org.mozilla.javascript.Scriptable)
     */
    public boolean has(int arg0, Scriptable arg1) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#put(java.lang.String, org.mozilla.javascript.Scriptable, java.lang.Object)
     */
    public void put(String arg0, Scriptable arg1, Object arg2) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#put(int, org.mozilla.javascript.Scriptable, java.lang.Object)
     */
    public void put(int arg0, Scriptable arg1, Object arg2) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#delete(java.lang.String)
     */
    public void delete(String arg0) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#delete(int)
     */
    public void delete(int arg0) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#getPrototype()
     */
    public Scriptable getPrototype() {
        logger.debug("entry() getPrototype()");
        logger.debug("exit() getPrototype()");
        return this.prototype;

    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#setPrototype(org.mozilla.javascript.Scriptable)
     */
    public void setPrototype(Scriptable prototype) {
        logger.debug("entry() setPrototype(Scriptable prototype)");
        this.prototype = prototype;
        logger.debug("exit() setPrototype(Scriptable prototype)");
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#getParentScope()
     */
    public Scriptable getParentScope() {
        logger.debug("entry() getParentScope()");
        logger.debug("exit() getParentScope()");
        return this.parent;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#setParentScope(org.mozilla.javascript.Scriptable)
     */
    public void setParentScope(Scriptable parent) {
        logger.debug("entry() setParentScope(Scriptable parent)");
        this.parent = parent;
        logger.debug("exit() setParentScope(Scriptable parent)");
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#getIds()
     */
    public Object[] getIds() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#getDefaultValue(java.lang.Class)
     */
    public Object getDefaultValue(Class arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#hasInstance(org.mozilla.javascript.Scriptable)
     */
    public boolean hasInstance(Scriptable arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    public void debug(Object message) {
        logger.debug("LOGGER DEBUG::"+message);
    }

    public void info(Object message) {
        logger.info("LOGGER INFO::"+message);
    }

    public void warn(Object message) {
        logger.warn("LOGGER WARN::"+message);
    }
    public void error(Object message) {
        logger.error("LOGGER ERROR::"+message);
    }

}