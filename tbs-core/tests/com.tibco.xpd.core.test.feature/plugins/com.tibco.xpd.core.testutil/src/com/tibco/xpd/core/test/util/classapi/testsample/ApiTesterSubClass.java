/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.core.test.util.classapi.testsample;

import java.util.List;

/**
 * ApiTesterSubClass
 * 
 * 
 * @author aallway
 * @since 3.3 (13 Oct 2009)
 */
public class ApiTesterSubClass extends ApiTester {

    public void checkAccessToSuperClassData() {

        ApiTesterNestedClass nestedClass = protectedMethod(null, null);

        nestedClass.nestedProtectedField = null;

        nestedClass.nestedProtectedMethod(null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.core.createtestwizards.apiclasstestwizard.ApiTesterInterface
     * #apiInterfaceMethodUnImplementedByTopClass(java.lang.String)
     */
    public List<String> apiInterfaceMethodUnImplementedByTopClass(String param) {
        // TODO Auto-generated method stub
        return null;
    }

}
