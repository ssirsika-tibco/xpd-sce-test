/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.core.createtestwizards.generatordata;

import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;

/**
 * ValidationTestJavaClassGeneratorData
 * 
 * 
 * @author aallway
 * @since 3.3 (23 Jul 2009)
 */
public class ValidationTestJavaClassGeneratorData extends
        BaseTestJavaClassGeneratorData {

    public ValidationsTestProblemMarkerInfo[] markerInfos;

    public final Boolean isCheckProblemsExists;

    public final String testDescription;

    /**
     * @param testName
     * @param testClassName
     * @param testMethodName
     * @param testPluginId
     * @param testPackageId
     * @param baseResourcePath
     * @param testResourcePaths
     * @param isCheckProblemsExists
     * @param selectedQuickFixes
     * @param selectedMarkers
     */
    public ValidationTestJavaClassGeneratorData(String testName,
            String testClassName, String testSuperClassName,
            String testSuperClassPackage, String testMethodName,
            String testPluginId, String testPackageId, String baseResourcePath,
            String[] testResourcePaths,
            ValidationsTestProblemMarkerInfo[] markerInfos,
            Boolean isCheckProblemsExists, String testDescription) {

        super(testName, testClassName, testSuperClassName,
                testSuperClassPackage, testMethodName, testPluginId,
                testPackageId, baseResourcePath, testResourcePaths);

        this.markerInfos = markerInfos;
        this.isCheckProblemsExists = isCheckProblemsExists;
        this.testDescription = testDescription;

    }

}
