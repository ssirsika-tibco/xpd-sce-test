/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.core.createtestwizards;

import com.tibco.xpd.core.createtestwizards.generatordata.ApiClassTestJavaClassGeneratorData;
import com.tibco.xpd.core.createtestwizards.generators.ApiClassTestJavaClassGenerator;

/**
 * TestGen
 * 
 * 
 * @author aallway
 * @since 3.3 (13 Oct 2009)
 */
public class TestGen {

    public static void main(String[] args) {

        ApiClassTestJavaClassGeneratorData data =
                new ApiClassTestJavaClassGeneratorData(
                        "MyTest",
                        "MyTestClass",
                        "test.plugin",
                        "test.pkg",
                        "com.tibco.xpd.core.test.util",
                        new Class[] { com.tibco.xpd.core.test.util.classapi.testsample.ApiTester.class });

        ApiClassTestJavaClassGenerator generator =
                new ApiClassTestJavaClassGenerator();

        String clas = generator.generate(data);
        System.out.println(clas);

    }

}
