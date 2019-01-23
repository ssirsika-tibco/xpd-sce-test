/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.core.test.util.classapi;

import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

/**
 * ApiClassTest
 * 
 * 
 * @author aallway
 * @since 3.3 (12 Oct 2009)
 */
public class ApiClassTest extends AbstractApiClassTest {

    public void testApiTester() throws Exception {

        Bundle classBundle =
                Platform.getBundle("com.tibco.xpd.implementer.script"); //$NON-NLS-1$

        String clazzName = "com.tibco.xpd.implementer.script.WsdlUtil"; //$NON-NLS-1$

        Class clazz = classBundle.loadClass(clazzName);

        // Class<?> clazz = Class.forName(clazzName);

        assertNotNull("Failed to load class to test: " + clazzName, clazz); //$NON-NLS-1$

        AbstractApiClassTest.log(clazz.toString());

        String[] constructorControlSample = new String[] {
        // TODO
                };
        checkApiConstructors(clazz, constructorControlSample);

        String[] fieldControlSample = new String[] {
        // TODO
                };
        checkApiFields(clazz, fieldControlSample);

        String[] methodControlSample = new String[] {
        // TODO
                };
        checkApiMethods(clazz, fieldControlSample);

        String[] nestedClassControlSample = new String[] {
        // TODO
                };
        checkApiNestedClasses(clazz, fieldControlSample);

        if (isStrict) {
            checkUntestedApi(clazz);
        }

    }
}
