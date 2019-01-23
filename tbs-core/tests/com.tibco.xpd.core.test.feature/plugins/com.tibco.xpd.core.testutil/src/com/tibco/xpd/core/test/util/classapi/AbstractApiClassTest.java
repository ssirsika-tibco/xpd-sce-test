/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.core.test.util.classapi;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import junit.framework.TestCase;

/**
 * AbstractApiClassTest
 * <p>
 * Base class for testing the API of a class (constructors, fields and methods
 * that can have an impact on classes that override and / or use a given class).
 * <p>
 * This is done by comparing lists of description strings created (using
 * {@link #getApiConstructors(Class)},{@link #getApiMethods(Class)},
 * {@link #getApiFields(Class)}) and stored in the subclass with the same lists
 * generated from the same class at runtime.
 * <p>
 * The API of the class will include inherited fields and methods but not nested
 * class methods (which should be tested separately).
 * 
 * 
 * @author aallway
 * @since 3.3 (12 Oct 2009)
 */
public abstract class AbstractApiClassTest extends TestCase {

    /**
     * Specifies that when all api's built into the control set have been tested
     * for existence then fail if there are api's now in the test set (i.e. the
     * current version of api) that weren't covered by the control set.
     */
    public boolean isStrict = false;

    public static boolean wantLogging = false;

    protected Collection<String> untestedApiConstructors =
            Collections.emptyList();

    protected Collection<String> untestedApiFields = Collections.emptyList();

    protected Collection<String> untestedApiMethods = Collections.emptyList();

    protected Collection<String> untestedApiNestedClasses =
            Collections.emptyList();

    /**
     * Check the actual api constructors against the control sample.
     * 
     * @param clazz
     * @param control
     */
    protected void checkApiConstructors(Class<?> clazz, String[] control) {
        /*
         * Get the actual constructors.
         */
        Set<String> test = getApiConstructors(clazz);

        /*
         * Look for missing constructors, any that are found are removed from
         * test list - any left over can be reported as extra that should be
         * added to test.
         */
        String missing = ""; //$NON-NLS-1$

        for (String c : control) {
            if (!test.contains(c)) {
                missing += "    " + c + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
            } else {
                test.remove(c);
            }
        }

        if (missing.length() > 0) {
            fail("The following API class constructors were expected but not found: \n" //$NON-NLS-1$
                    + missing
                    + "==========================================================="); //$NON-NLS-1$
        }

        /*
         * Save record of any actual constructors in class that aren't in test
         * (so we can do a fail (in isStrict=true mode) if the whole test
         * passes.
         */
        untestedApiConstructors = test;

        return;
    }

    /**
     * Check the actual api fields against the control sample.
     * 
     * @param clazz
     * @param control
     */
    protected void checkApiFields(Class<?> clazz, String[] control) {
        /*
         * Get the actual fields.
         */
        Set<String> test = getApiFields(clazz);

        /*
         * Look for missing fields, any that are found are removed from test
         * list - any left over can be reported as extra that should be added to
         * test.
         */
        String missing = ""; //$NON-NLS-1$

        for (String c : control) {
            if (!test.contains(c)) {
                missing += "    " + c + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
            } else {
                test.remove(c);
            }
        }

        if (missing.length() > 0) {
            fail("The following API class fields were expected but not found: \n" //$NON-NLS-1$
                    + missing
                    + "==========================================================="); //$NON-NLS-1$
        }

        /*
         * Save record of any actual fields in class that aren't in test (so we
         * can do a fail (in isStrict=true mode) if the whole test passes.
         */
        untestedApiFields = test;

        return;
    }

    /**
     * Check the actual api methods against the control sample.
     * 
     * @param clazz
     * @param control
     */
    protected void checkApiMethods(Class<?> clazz, String[] control) {
        /*
         * Get the actual methods.
         */
        Set<String> test = getApiMethods(clazz);

        /*
         * Look for missing methods, any that are found are removed from test
         * list - any left over can be reported as extra that should be added to
         * test.
         */
        String missing = ""; //$NON-NLS-1$

        for (String c : control) {
            if (!test.contains(c)) {
                missing += "    " + c + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
            } else {
                test.remove(c);
            }
        }

        if (missing.length() > 0) {
            fail("The following API class methods were expected but not found: \n" //$NON-NLS-1$
                    + missing
                    + "==========================================================="); //$NON-NLS-1$
        }

        /*
         * Save record of any actual methods in class that aren't in test (so we
         * can do a fail (in isStrict=true mode) if the whole test passes.
         */
        untestedApiMethods = test;

        return;
    }

    /**
     * Check the actual api nested classes against the control sample.
     * 
     * @param clazz
     * @param control
     */
    protected void checkApiNestedClasses(Class<?> clazz, String[] control) {
        /*
         * Get the actual nested classes.
         */
        Set<String> test = getApiNestedClassDescriptions(clazz);

        /*
         * Look for missing nested classes, any that are found are removed from
         * test list - any left over can be reported as extra that should be
         * added to test.
         */
        String missing = ""; //$NON-NLS-1$

        for (String c : control) {
            if (!test.contains(c)) {
                missing += "    " + c + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
            } else {
                test.remove(c);
            }
        }

        if (missing.length() > 0) {
            fail("The following API Nested Classes were expected but not found: \n" //$NON-NLS-1$
                    + missing
                    + "==========================================================="); //$NON-NLS-1$
        }

        /*
         * Save record of any actual nested classes in class that aren't in test
         * (so we can do a fail (in isStrict=true mode) if the whole test
         * passes.
         */
        untestedApiNestedClasses = test;

        return;
    }

    /**
     * If all else succeeds, fail if any API constructors, fields or methods are
     * now in class but are not included in the test.
     * <p>
     * <b> This is based on the result of the other checkXXX methods in this
     * class which MUST be called first</b>
     * 
     * @param clazz
     */
    public void checkUntestedApi(Class<?> clazz) {
        String message = ""; //$NON-NLS-1$

        if (!untestedApiConstructors.isEmpty()) {
            message +=
                    "New API Constructors Exist That Are Not Included In Test: \n"; //$NON-NLS-1$
            message +=
                    "-----------------------------------------------------------\n"; //$NON-NLS-1$

            for (String s : untestedApiConstructors) {
                message += "  " + s + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
            }
            message +=
                    "-----------------------------------------------------------\n"; //$NON-NLS-1$
        }

        if (!untestedApiFields.isEmpty()) {
            message += "New API Fields Exist That Are Not Included In Test: \n"; //$NON-NLS-1$
            message +=
                    "-----------------------------------------------------------\n"; //$NON-NLS-1$

            for (String s : untestedApiFields) {
                message += "  " + s + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
            }
            message +=
                    "-----------------------------------------------------------\n"; //$NON-NLS-1$
        }

        if (!untestedApiMethods.isEmpty()) {
            message +=
                    "New API Methods Exist That Are Not Included In Test: \n"; //$NON-NLS-1$
            message +=
                    "-----------------------------------------------------------\n"; //$NON-NLS-1$

            for (String s : untestedApiMethods) {
                message += "  " + s + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
            }
            message +=
                    "-----------------------------------------------------------\n"; //$NON-NLS-1$
        }

        if (!untestedApiNestedClasses.isEmpty()) {
            message +=
                    "New API Nested Classes Exist That Are Not Included In Test: \n"; //$NON-NLS-1$
            message +=
                    "-----------------------------------------------------------\n"; //$NON-NLS-1$

            for (String s : untestedApiNestedClasses) {
                message += "  " + s + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
            }
            message +=
                    "-----------------------------------------------------------\n"; //$NON-NLS-1$
        }

        if (message.length() > 0) {
            fail("Test Is Out Of Date With Current Class API: \n" + message); //$NON-NLS-1$
        }

        return;
    }

    /**
     * @param clazz
     * 
     * @return list of the constructors in the given clazz to be counted as API
     *         constructors.
     */
    public static Set<String> getApiConstructors(Class<?> clazz) {
        /*
         * Get this class's public / protected constructors
         */

        Set<String> apiConstructors = new LinkedHashSet<String>();
        Constructor[] constructors = clazz.getConstructors();
        if (constructors != null) {
            for (Constructor<?> constructor : constructors) {
                if (isApiConstructor(constructor)) {
                    String constructorDescription =
                            getConstructorDescription(constructor);
                    apiConstructors.add(constructorDescription);

                    log("  Constructor: " + constructorDescription); //$NON-NLS-1$
                }
            }
        }

        return apiConstructors;
    }

    /**
     * @param constructor
     * 
     * @return A string describing the constructor and it's parameters
     */
    public static String getConstructorDescription(Constructor<?> constructor) {
        return constructor.toGenericString();
    }

    /**
     * @param constructor
     * 
     * @return true if (for the purpose of this test) the constructor should be
     *         counted as an API constructor (currently true if it is protected
     *         or public).
     */
    public static boolean isApiConstructor(Constructor<?> constructor) {
        int mod = constructor.getModifiers();

        if (Modifier.isPublic(mod) || Modifier.isProtected(mod)) {

            if (constructor.getDeclaringClass().getEnclosingClass() != null) {
                /*
                 * This is a nested class, it's constructors are only part of
                 * the API IF it is static. If it isn't static then it's
                 * constructors cannot be called outside of enclosing class
                 * anyway.
                 */
                if (Modifier.isStatic(mod)) {
                    return true;
                }
            } else {

                return true;
            }
        }

        return false;
    }

    /**
     * @param clazz
     * 
     * @return list of the fields in the given clazz to be counted as API fields
     *         (includes superclass, interfaces and non-static nested class
     *         methods)..
     */
    public static Set<String> getApiFields(Class<?> clazz) {
        /**
         * First get fields from main class.
         */
        Set<String> apiFields = new LinkedHashSet<String>();

        Field[] fields = clazz.getDeclaredFields();
        if (fields != null) {
            for (Field field : fields) {
                if (isApiField(field)) {
                    String fieldDescription = getFieldDescription(field);
                    log("  Field: " + fieldDescription); //$NON-NLS-1$
                    apiFields.add(fieldDescription);
                }
            }
        }

        /*
         * Then fields from super-class(es), interfaces and non-static nested
         * classes.
         */
        Set<Class<?>> superAndNested = getSuperClassAndInterfaces(clazz);

        for (Class<?> clazzChild : superAndNested) {
            int mod = clazzChild.getModifiers();
            apiFields.addAll(getApiFields(clazzChild));
        }

        return apiFields;
    }

    /**
     * @param field
     * 
     * @return A string describing the field
     */
    public static String getFieldDescription(Field field) {
        return field.toGenericString();
    }

    /**
     * @param field
     * 
     * @return true if (for the purpose of this test) the field should be
     *         counted as an API field (currently true if it is protected or
     *         public).
     */
    public static boolean isApiField(Field field) {
        int mod = field.getModifiers();

        if (Modifier.isPublic(mod) || Modifier.isProtected(mod)) {
            return true;
        }

        return false;
    }

    /**
     * @param clazz
     * 
     * @return list of the methods in the given clazz to be counted as API
     *         methods (includes superclass, interfaces and non-static nested
     *         class methods).
     */
    public static Set<String> getApiMethods(Class<?> clazz) {
        Set<String> apiMethods = internalGetApiMethods(clazz);

        if (wantLogging) {
            for (String m : apiMethods) {
                log("  Method: " + m); //$NON-NLS-1$
            }
        }

        return apiMethods;
    }

    /**
     * @param clazz
     * @return list of the methods in the given clazz to be counted as API
     *         methods (includes superclass, interfaces and non-static nested
     *         class methods).
     */
    private static Set<String> internalGetApiMethods(Class<?> clazz) {
        /*
         * Get Methods from main class.
         */
        Set<String> apiMethods = new LinkedHashSet<String>();
        Method[] methods = clazz.getDeclaredMethods();
        if (methods != null) {
            for (Method method : methods) {
                if (isApiMethod(method)) {
                    String methodDescription = getMethodDescription(method);

                    apiMethods.add(methodDescription);
                }
            }
        }

        /*
         * The get methods from super class, interfaces and nested.
         */
        Set<Class<?>> superAndNested = getSuperClassAndInterfaces(clazz);

        for (Class<?> clazzChild : superAndNested) {
            int mod = clazzChild.getModifiers();

            Set<String> apiMethods2 = internalGetApiMethods(clazzChild);
            apiMethods.addAll(apiMethods2);
        }
        return apiMethods;
    }

    /**
     * @param method
     * 
     * @return A string describing the method
     */
    public static String getMethodDescription(Method method) {
        /*
         * For method name we want to remove the class name part because we only
         * want to count the fact that there is a method
         * 
         * "public String method()"
         * 
         * somewhere in the class / interface hierarchy. i.e. we do not want to
         * end up testing that
         * 
         * "public org.me.subclass.method()" AND
         * "public org.me.superclass.method()"
         * 
         * Are both there because as far as anything using the class as an api
         * is concerned it will not care which class in hoerarchy implements it,
         * just that one does.
         */
        String meth = method.toGenericString();
        int startParams = meth.indexOf('(');

        String beforeParams = meth.substring(0, startParams);

        int startPkg = beforeParams.lastIndexOf(' ');

        String modifiersAndReturnType = meth.substring(0, startPkg);

        /*
         * One modifier we really don't care about is abstract because when have
         * public method that implements an interface method or overrides an
         * abstract superclass method we end up with "public int method()" from
         * class and "public abstract int method()" in the set - the caller of
         * the API does not care about this!
         */
        modifiersAndReturnType =
                modifiersAndReturnType.replaceAll("abstract ", ""); //$NON-NLS-1$ //$NON-NLS-2$

        String params = meth.substring(startParams);

        return modifiersAndReturnType + " " + method.getName() + params; //$NON-NLS-1$
    }

    /**
     * @param method
     * 
     * @return true if (for the purpose of this test) the method should be
     *         counted as an API method (currently true if it is protected or
     *         public).
     */
    public static boolean isApiMethod(Method method) {
        int mod = method.getModifiers();

        if (Modifier.isPublic(mod) || Modifier.isProtected(mod)) {
            return true;
        }

        return false;
    }

    /**
     * @param clazz
     * 
     * @return list of the nested clasees in the given clazz to be counted as
     *         part of API.
     */
    public static Set<String> getApiNestedClassDescriptions(Class<?> clazz) {
        Set<Class<?>> apiNestedClasses = getApiNestedClasses(clazz);

        Set<String> apiClasses = new LinkedHashSet<String>();

        for (Class<?> nestedClazz : apiNestedClasses) {
            apiClasses.add(getNestedClassDescription(nestedClazz));
        }

        return apiClasses;
    }

    /**
     * @param clazz
     * 
     * @return list of the nested clasees in the given clazz to be counted as
     *         part of API.
     */
    public static Set<Class<?>> getApiNestedClasses(Class<?> clazz) {
        /**
         * First get fields from main class.
         */
        Set<Class<?>> apiCLasses = new LinkedHashSet<Class<?>>();

        Class[] classes = clazz.getDeclaredClasses();
        if (classes != null) {
            for (Class<?> nestedClazz : classes) {
                if (isApiNestedClass(nestedClazz)) {
                    log("  NestedClass: " //$NON-NLS-1$
                            + getNestedClassDescription(nestedClazz));
                    apiCLasses.add(nestedClazz);
                }
            }
        }

        /*
         * Then nested classes from super-class(es), interfaces
         */
        Set<Class<?>> superAndInterfaces = getSuperClassAndInterfaces(clazz);

        for (Class<?> clazzChild : superAndInterfaces) {
            int mod = clazzChild.getModifiers();
            apiCLasses.addAll(getApiNestedClasses(clazzChild));
        }

        return apiCLasses;
    }

    /**
     * @param nestedClazz
     * 
     * @return A string describing the nested Class
     */
    public static String getNestedClassDescription(Class<?> nestedClazz) {
        return nestedClazz.getName();
    }

    /**
     * @param nestedClazz
     * 
     * @return true if (for the purpose of this test) the nested class should be
     *         counted part of API
     */
    public static boolean isApiNestedClass(Class<?> nestedClazz) {
        int mod = nestedClazz.getModifiers();

        if ((Modifier.isPublic(mod) || Modifier.isProtected(mod))) {
            return true;
        }

        return false;
    }

    public static void log(String msg) {
        if (wantLogging) {
            System.out.println(msg);
        }
    }

    /**
     * @param clazz
     * @return List containing the (immediate) superclass, implemented
     *         interfaces
     */
    private static Set<Class<?>> getSuperClassAndInterfaces(Class<?> clazz) {
        Set<Class<?>> clazzes = new LinkedHashSet<Class<?>>();

        /* Super */
        Class<?> superClazz = clazz.getSuperclass();
        if (superClazz != null) {
            clazzes.add(superClazz);
        }

        /* Interfaces */
        Class[] interfaces = clazz.getInterfaces();
        if (interfaces != null) {
            for (Class<?> interfase : interfaces) {
                clazzes.add(interfase);
            }
        }

        return clazzes;
    }

}
