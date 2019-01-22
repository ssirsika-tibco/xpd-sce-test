/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.test.supporting;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

/**
 * Helper class for executing javascript.
 * 
 * @author nwilson
 * @since 27 May 2015
 */
public class DataMapperScriptExecutor {

    private Context cx;

    private Scriptable scope;

    /**
     * Initialise the execution context.
     */
    public DataMapperScriptExecutor() {
        cx = Context.enter();
        scope = cx.initStandardObjects();
    }

    /**
     * Adds a Java Class instance to the Javascript context under a given name.
     * This will allow the script to invoke methods on the instance.
     * 
     * @param name
     *            The field name.
     * @param value
     *            The Java class instance.
     */
    public void addComplex(String name, Object value) {
        Object wrappedRequest = Context.javaToJS(value, scope);
        ScriptableObject.putProperty(scope, name, wrappedRequest);
    }

    public void add(String name, Object value) {
        ScriptableObject.putProperty(scope, name, value);
    }

    /**
     * Adds an integer value variable to the Javascript context with the given
     * name.
     * 
     * @param name
     *            The field name.
     * @param value
     *            The field value.
     */
    public void add(String name, Integer value) {
        ScriptableObject.putProperty(scope, name, value);
    }

    /**
     * Adds a boolean value variable to the Javascript context with the given
     * name.
     * 
     * @param name
     *            The field name.
     * @param value
     *            The field value.
     */
    public void add(String name, Boolean value) {
        ScriptableObject.putProperty(scope, name, value);
    }

    /**
     * Adds a double value variable to the Javascript context with the given
     * name.
     * 
     * @param name
     *            The field name.
     * @param value
     *            The field value.
     */
    public void add(String name, Double value) {
        ScriptableObject.putProperty(scope, name, value);
    }

    /**
     * Adds an String value variable to the Javascript context with the given
     * name.
     * 
     * @param name
     *            The field name.
     * @param value
     *            The field value.
     */
    public void add(String name, String value) {
        ScriptableObject.putProperty(scope, name, value);
    }

    /**
     * @param name
     *            The property name.
     * @param type
     *            The expected property type.
     * @return The property value.
     */
    public <T> T get(String name, Class<T> type) {
        T result = null;
        Object property = ScriptableObject.getProperty(scope, name);
        if (type.isInstance(property)) {
            result = type.cast(property);
        }
        return result;
    }

    /**
     * @param name
     *            The property name.
     * @param type
     *            The expected property type.
     * @return The property value.
     */
    public <T> T getComplex(String name, Class<T> type) {
        T result = null;
        Object property = ScriptableObject.getProperty(scope, name);
        if (property != null) {
            Object converted = Context.jsToJava(property, type);
            if (type.isInstance(converted)) {
                result = type.cast(converted);
            }
        }
        return result;
    }

    /**
     * Executes the given script.
     * 
     * @param script
     *            The script to execute.
     */
    public void execute(String script) {
        cx.evaluateString(scope, script, "script", 1, null); //$NON-NLS-1$
    }

    /**
     * Closes the script context, this must be called after the script is
     * executed and any results obtained.
     */
    public void close() {
        Context.exit();
    }

}
