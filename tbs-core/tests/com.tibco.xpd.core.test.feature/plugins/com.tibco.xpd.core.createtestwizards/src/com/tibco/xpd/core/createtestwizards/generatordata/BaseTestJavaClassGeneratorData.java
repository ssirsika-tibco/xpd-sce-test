/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.core.createtestwizards.generatordata;

/**
 * Data for passing to BaseTestJAvaClassGenerator.
 * 
 * @author aallway
 * @since 3.2
 */
public class BaseTestJavaClassGeneratorData {

    public String testName;

    public String testSuperClassName;

    public String testSuperClassPackage;

    public String testClassName;

    public String testMethodName;

    public String testPluginId;

    public String testPackageId;

    public String baseResourcePath;

    public String[] testResourcePaths;

    public BaseTestJavaClassGeneratorData(String testName,
            String testClassName, String testSuperClassName,
            String testSuperClassPackage, String testMethodName,
            String testPluginId, String testPackageId, String baseResourcePath,
            String[] testResourcePaths) {
        super();
        this.testName = testName;
        this.testClassName = testClassName;
        this.testSuperClassName = testSuperClassName;
        this.testSuperClassPackage = testSuperClassPackage;
        this.testMethodName = testMethodName;
        this.testPluginId = testPluginId;
        this.testPackageId = testPackageId;
        this.baseResourcePath = baseResourcePath;
        this.testResourcePaths = testResourcePaths;
    }

}
