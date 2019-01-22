package com.tibco.bds.designtime.generator.test;

import junit.framework.TestCase;

import com.tibco.xpd.bom.resources.utils.NameMapper;

/**
 * This test is of limited use not as the method exercised isn't used any more.
 * There are other methods in the class worth testing, though. Also, the mapping
 * code we ARE calling doesn't have unit tests as the time of writing, so it
 * wouldn't hurt to (temporarily) test that from here.
 * 
 * @author smorgan
 * 
 */
public class NameMapperTest extends TestCase {

    public void testNamespaceURIToJavaPackageName() {
        String pkg =
                NameMapper
                        .getJavaPackageNameFromNamespaceURI("http://www.xyz.com/a/b/c"); //$NON-NLS-1$
        assertEquals("Unexpected package name", "com.xyz.www.a.b.c", pkg); //$NON-NLS-1$ //$NON-NLS-2$
    }

    public void testNamespaceURIToJavaPackageName2() {
        String pkg =
                NameMapper
                        .getJavaPackageNameFromNamespaceURI("http://blah.com/HelloWorld"); //$NON-NLS-1$
        assertEquals("Unexpected package name", "com.blah.HelloWorld", pkg); //$NON-NLS-1$ //$NON-NLS-2$
    }

    public void testNamespaceURIToJavaPackageName3() {
        String pkg =
                NameMapper
                        .getJavaPackageNameFromNamespaceURI("http://Cheese.Box/HelloWorld"); //$NON-NLS-1$
        assertEquals("Unexpected package name", "Box.Cheese.HelloWorld", pkg); //$NON-NLS-1$ //$NON-NLS-2$
    }

    public void testNamespaceURIWithTrailingSlashToJavaPackageName() {
        String pkg =
                NameMapper
                        .getJavaPackageNameFromNamespaceURI("http://Cheese.Box/HelloWorld/"); //$NON-NLS-1$
        assertEquals("Unexpected package name", "Box.Cheese.HelloWorld", pkg); //$NON-NLS-1$ //$NON-NLS-2$
    }
}
