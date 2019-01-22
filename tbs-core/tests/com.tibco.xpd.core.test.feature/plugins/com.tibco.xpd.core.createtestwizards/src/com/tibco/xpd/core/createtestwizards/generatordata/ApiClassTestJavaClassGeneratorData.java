/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.core.createtestwizards.generatordata;

/**
 * ApiClassTestJavaClassGeneratorData
 * <p>
 * Class API test generator data.
 * 
 * @author aallway
 * @since 3.3 (13 Oct 2009)
 */
public class ApiClassTestJavaClassGeneratorData {

    public String testName;

    public String testClassName;

    public String testPluginId;

    public String testPackageId;

    public String apiClassParentPluginId;

    public Class[] apiClasses;

    /**
     * @param testName
     * @param testClassName
     * @param testPluginId
     * @param testPackageId
     * @param apiClasses
     */
    public ApiClassTestJavaClassGeneratorData(String testName,
            String testClassName, String testPluginId, String testPackageId,
            String apiClassParentPluginId, Class[] apiClasses) {
        super();
        this.testName = testName;
        this.testClassName = testClassName;
        this.testPluginId = testPluginId;
        this.testPackageId = testPackageId;
        this.apiClassParentPluginId = apiClassParentPluginId;
        this.apiClasses = apiClasses;
    }

    /**
     * @param name
     * @return Given a name replace all but valid java class/method (simple)
     *         name characters with "_"
     */
    public String getJavaTokenName(String name) {
        if (name != null) {
            char[] chars = name.toCharArray();

            String className = ""; //$NON-NLS-1$
            boolean upperCaseNext = true;
            for (char c : chars) {

                if (Character.isLetter(c) || Character.isDigit(c)) {
                    className += c;
                } else {
                    className += '_';
                }
            }

            return className;
        }

        return ""; //$NON-NLS-1$
    }

}
