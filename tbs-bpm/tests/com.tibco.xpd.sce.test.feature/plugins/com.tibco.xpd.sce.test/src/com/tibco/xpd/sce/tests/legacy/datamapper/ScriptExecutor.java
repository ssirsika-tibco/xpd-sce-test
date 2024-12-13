/*
 * Copyright (c) TIBCO Software Inc 2004 - 2017. All rights reserved.
 */

package com.tibco.xpd.sce.tests.legacy.datamapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

/**
 * Helper class to execute scripts and verify the result.
 *
 * @author nwilson
 * @since 29 Mar 2017
 */
public class ScriptExecutor {
    // Returned by functions to differentiate between undefined and null JS
    // variables.
    public static final Object UNDEFINED = new Object();

    // Returned by functions to differentiate between undefined and null JS
    // array variables.
    public static final List<?> UNDEFINED_LIST = new ArrayList<Object>();

    private ScriptEngine nashorn;

    private List<String> scripts;

    /**
     * 
     */
    public ScriptExecutor() {
        this(new String[0]);
    }

    public ScriptExecutor(String[] scriptNames) {
        scripts = new ArrayList<>();
        nashorn = new ScriptEngineManager().getEngineByName("nashorn");
        try {
            for (String scriptName : scriptNames) {
                scripts.add(readAll(
                        ScriptExecutor.class.getResourceAsStream(scriptName)));
            }
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Initialise a new script context.
     * 
     * @return a new script context.
     */
    public ScriptContext initContext() {
        ScriptContext ctx = new SimpleScriptContext();
        ctx.setBindings(nashorn.createBindings(), ScriptContext.ENGINE_SCOPE);
        return ctx;
    }

    /**
     * Attempt to run the generated script with test data.
     * 
     * @param script
     *            The script to run
     */
    public void verifyScript(String script, String paramScript,
            String paramName, Object expected) {
        Object actual = getActual(script, paramScript, paramName);
        assertEquals(expected, actual);
    }

    /**
     * Attempt to run the generated script with test data.
     * 
     * @param script
     *            The script to run
     */
    public Object getActual(String script, String paramScript,
            String paramName) {
        Object actual = null;
        try {
            ScriptContext ctx = executeScript(script, paramScript);
            actual = getScriptParameter(paramName, ctx);
        } catch (ScriptException e) {
            fail(e.getMessage());
        }
        return actual;
    }

    public ScriptContext executeScript(String script, String paramScript)
            throws ScriptException {
        ScriptContext ctx = initContext();
        executeScript(ctx, script, paramScript);
        return ctx;
    }

    public void executeScript(ScriptContext ctx, String script,
            String paramScript) throws ScriptException {
        for (String libScript : scripts) {
            nashorn.eval(libScript, ctx);
        }
        nashorn.eval("$status = [];", ctx);

		// System.out.println("PARAM-SCRIPT");
		// System.out.println("===========================================================================");
		// System.out.println(paramScript);
		// System.out.println("===========================================================================");

        nashorn.eval(paramScript, ctx);

		// System.out.println("SCRIPT");
		// System.out.println("===========================================================================");
		// System.out.println(script);
		// System.out.println("===========================================================================");

        nashorn.eval(script, ctx);
    }

    /**
     * Gets a named variable from the script context. Numbers are converted to
     * Java Double. UNDEFINED is returned if the variable is undefined.
     * 
     * @param paramName
     *            The parameter name.
     * @param ctx
     *            The script context.
     * @return The variable value, null or UNDEFINED.
     */
    public Object getScriptParameter(String paramName, ScriptContext ctx) {
        Object actual;
        String[] parts = paramName.split("\\.");
        if (exists(ctx, parts[0])) {
            actual = ctx.getAttribute(parts[0], ScriptContext.ENGINE_SCOPE);
            for (int i = 1; i < parts.length; i++) {
                if (actual instanceof Bindings) {
                    Bindings bindings = (Bindings) actual;
                    if (bindings.containsKey(parts[i])) {
                        actual = bindings.get(parts[i]);
                    } else {
                        actual = UNDEFINED;
                        break;
                    }
                }
            }
            if (actual instanceof Number) {
                actual = Double.valueOf(((Number) actual).doubleValue());
            }
        } else {
            actual = UNDEFINED;
        }
        return actual;
    }

    /**
     * Checks if a JS variable exists.
     * 
     * @param ctx
     *            The script context.
     * @param path
     *            The variable name.
     * @return true if it exists, false if it is undefined.
     */
    private boolean exists(ScriptContext ctx, String path) {
        boolean exists = false;
        try {
            Object result =
                    nashorn.eval("typeof " + path + " !== 'undefined'", ctx);
            if (Boolean.TRUE.equals(result)) {
                exists = true;
            }
        } catch (ScriptException e) {
            e.printStackTrace();
            fail("Error evaluating 'exists' script.");
        }
        return exists;
    }

    /**
     * Extracts a parameter from the Javascript context as a specific class. The
     * List class can be used to extract arrays.
     * 
     * @param ctx
     *            The script context.
     * @param name
     *            The parameter name.
     * @param cls
     *            The expected class.
     * @return The parameter cast to the expected class.
     */
    public <T> T getScriptParameter(ScriptContext ctx, String name,
            Class<T> cls) throws ScriptException {
        T result = null;
        if (List.class.isAssignableFrom(cls)) {
            if (exists(ctx, name)) {
                Object value = nashorn.eval(
                        "Java.to(" + name + ", '" + cls.getName() + "')",
                        ctx);
                result = cls.cast(value);
            } else {
                result = cls.cast(UNDEFINED_LIST);
            }
        } else {
            Object value = getScriptParameter(name, ctx);
            result = cls.cast(value);
        }
        return result;
    }

    /**
     * Read the contents of an input stream into a String.
     * 
     * @param input
     *            The input stream.
     * @return The data from the input stream as a UTF-8 string.
     * @throws IOException
     *             If the data could not be read.
     */
    private static String readAll(InputStream input) throws IOException {
        try (Scanner scanner = new Scanner(input, "UTF-8")) {
            return scanner.useDelimiter("\\Z").next();
        }
    }

}
