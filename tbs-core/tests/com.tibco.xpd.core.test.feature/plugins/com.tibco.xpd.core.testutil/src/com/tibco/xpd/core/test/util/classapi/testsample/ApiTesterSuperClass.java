/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.core.test.util.classapi.testsample;

/**
 * ApiTesterSuperClass
 * 
 * 
 * @author aallway
 * @since 3.3 (12 Oct 2009)
 */
public class ApiTesterSuperClass {
    public String superClassPublicField;

    protected String superClassProtectedField;

    private String superClassPrivateFieldSHOULDNOTAPPEAR;

    public ApiTesterSuperClass() {
    }

    public ApiTesterSuperClass(float superClassConstructor) {
    }

    public int superClassPublicMethod(String param) {
        return 0;
    }

    protected int superClassProtectedMethod(String param) {
        return 0;
    }

    public int superClassPublicMethodToOverride(String param) {
        return 0;
    }

    protected int superClassProtectedMethodToOverride(String param) {
        return 0;
    }

    private int superClassPrivateMethodSHOULDNOTAPPEAR(String param) {
        return 0;
    }

    public class SuperClassNestedClass {

        public void method() {

            return;
        }
    }

    public static class SuperClassNestedStaticClass {

        public void method() {

            return;
        }
    }
}
