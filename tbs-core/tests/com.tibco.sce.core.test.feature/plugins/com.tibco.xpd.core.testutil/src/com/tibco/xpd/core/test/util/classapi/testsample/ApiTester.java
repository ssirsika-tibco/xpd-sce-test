/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.core.test.util.classapi.testsample;

import java.util.List;
import java.util.Map;

/**
 * ApiTester
 * 
 * 
 * @author aallway
 * @since 3.3 (12 Oct 2009)
 */
public abstract class ApiTester extends ApiTesterSuperClass implements
        ApiTesterInterface {

    public static String publicStaticField;

    protected static String protectedStaticField;

    private static String privateStaticFieldSHOULDNOTAPPEAR;

    public String publicField;

    protected Map<String, List<Integer>> protectedField;

    private String privateFieldSHOULDNOTAPPEAR;

    public ApiTester() {
    }

    public ApiTester(String stringParam) {
    }

    public ApiTester(String stringParam, int intParam) {
    }

    public ApiTester(List<String> listStringParam, String[] stringArrayParam) {
    }

    protected ApiTester(String protectd, String Constructor) {
    }

    private ApiTester(String SHOULDNOTAPPEAR, char Constructor) {
    }

    public ApiTesterNestedClass publicMethod() {
        return new ApiTesterNestedClass(null);
    }

    protected ApiTesterNestedClass protectedMethod(String param,
            List<Object> ints) {
        return new ApiTesterNestedClass(null);
    }

    private ApiTesterNestedClass privateMethodSHOULDNOTAPPEAR(String param) {
        return new ApiTesterNestedClass(null);
    }

    @Override
    public int superClassPublicMethodToOverride(String param) {
        // TODO Auto-generated method stub
        return super.superClassPublicMethodToOverride(param);
    }

    @Override
    protected int superClassProtectedMethodToOverride(String param) {
        // TODO Auto-generated method stub
        return super.superClassProtectedMethodToOverride(param);
    }

    public int apiInterfaceMethod(String interfaceMethod) {
        return 0;
    }

    public int apiInterfacePublicMethod(String interfaceMethod) {
        return 0;
    }

    public static int publicStaticMethod(String param) {
        return 0;
    }

    protected static int protectedStaticMethod(String param) {
        return 0;
    }

    private static int privateStaticMethodSHOULDNOTAPPEAR(String param) {
        return 0;
    }

    public class ApiTesterNestedClass {
        public String nestedPublicField;

        protected String nestedProtectedField;

        private String nestedPrivateFieldSHOULDNOTAPPEAR;

        public ApiTesterNestedClass(
                String nestedNonStaticClassConstructorShouldNotBeChecked) {
        }

        public String nestedPublicMethod(String param) {
            return "Bogeys"; //$NON-NLS-1$
        }

        protected String nestedProtectedMethod(String param) {
            return "Bogeys"; //$NON-NLS-1$
        }

        private String nestedPrivateMethodSHOULDNOTAPPEAR(String param) {
            return "Bogeys"; //$NON-NLS-1$
        }
    }

    public static class ApiTesterNestedStaticClass {
        public String nestedPublicField;

        protected String nestedProtectedField;

        private String nestedPrivateFieldSHOULDNOTAPPEAR;

        public ApiTesterNestedStaticClass(String param) {
        }

        public String nestedStaticClassPublicMethod(String param) {
            return "Bogeys"; //$NON-NLS-1$
        }

        protected String nestedStaticClassProtectedMethod(String param) {
            return "Bogeys"; //$NON-NLS-1$
        }

        private String nestedStaticClassPrivateMethodSHOULDNOTAPPEAR(
                String param) {
            return "Bogeys"; //$NON-NLS-1$
        }
    }

    private class ApiTesterPrivateNestedClassSHOULDNOTAPPEAR {
        public String nestedPublicFieldSHOULDNOTAPPEAR;

        protected String nestedProtectedFieldSHOULDNOTAPPEAR;

        private String nestedPrivateFieldSHOULDNOTAPPEAR;

        public ApiTesterPrivateNestedClassSHOULDNOTAPPEAR(String SHOULDNOTAPPEAR) {
        }

        public String nestedStaticClassPublicMethodSHOULDNOTAPPEAR(String param) {
            return "Bogeys"; //$NON-NLS-1$
        }

        protected String nestedStaticClassProtectedMethodSHOULDNOTAPPEAR(
                String param) {
            return "Bogeys"; //$NON-NLS-1$
        }

        private String nestedStaticClassPrivateMethodSHOULDNOTAPPEAR(
                String param) {
            return "Bogeys"; //$NON-NLS-1$
        }
    }

    private static class ApiTesterPrivateNestedStaticClassSHOULDNOTAPPEAR {
        public String nestedPublicFieldSHOULDNOTAPPEAR;

        protected String nestedProtectedFieldSHOULDNOTAPPEAR;

        private String nestedPrivateFieldSHOULDNOTAPPEAR;

        public ApiTesterPrivateNestedStaticClassSHOULDNOTAPPEAR(String param) {
        }

        public String nestedStaticClassPublicMethodSHOULDNOTAPPEAR(String param) {
            return "Bogeys"; //$NON-NLS-1$
        }

        protected String nestedStaticClassProtectedMethodSHOULDNOTAPPEAR(
                String param) {
            return "Bogeys"; //$NON-NLS-1$
        }

        private String nestedStaticClassPrivateMethodSHOULDNOTAPPEAR(
                String param) {
            return "Bogeys"; //$NON-NLS-1$
        }
    }
}
