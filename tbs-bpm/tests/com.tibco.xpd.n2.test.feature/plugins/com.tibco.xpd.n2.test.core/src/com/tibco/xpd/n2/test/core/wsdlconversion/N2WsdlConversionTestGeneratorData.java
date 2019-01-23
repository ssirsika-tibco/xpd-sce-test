/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.n2.test.core.wsdlconversion;

import java.util.Collection;

import com.tibco.xpd.core.createtestwizards.generatordata.BaseTestJavaClassGeneratorData;

/**
 * Class storing data passed to the JET emmitter for N2 WSDL Conversion test
 * creation: createWsdlConversionTest.javajet
 * 
 * @author aallway
 * @since 19 Apr 2011
 */
public class N2WsdlConversionTestGeneratorData extends
        BaseTestJavaClassGeneratorData {

    public String targetProjectName;

    public String baseWsdlOrXsdPath;

    public String[] bomFolderRelativeBoms;

    public String[] bom2XsdFolderRelativeXsds;

    public Xsd4BdsInfo[] xsd4BdsInfos;

    /**
     * @param testName
     * @param testClassName
     * @param testSuperClassName
     * @param testSuperClassPackage
     * @param testMethodName
     * @param testPluginId
     * @param testPackageId
     * @param baseResourcePath
     * @param testResourcePaths
     */
    public N2WsdlConversionTestGeneratorData(String testName,
            String testClassName, String testSuperClassName,
            String testSuperClassPackage, String testMethodName,
            String testPluginId, String testPackageId, String baseResourcePath,
            String[] testResourcePaths, String targetProjectName,
            String baseWsdlOrXsdPath, Collection<String> bomFolderRelativeBoms,
            Collection<String> bom2XsdFolderRelativeXsds,
            Collection<Xsd4BdsInfo> xsd4BdsInfos) {

        super(testName, testClassName, testSuperClassName,
                testSuperClassPackage, testMethodName, testPluginId,
                testPackageId, baseResourcePath, testResourcePaths);

        this.targetProjectName = targetProjectName;

        this.baseWsdlOrXsdPath = baseWsdlOrXsdPath;

        this.bomFolderRelativeBoms =
                bomFolderRelativeBoms.toArray(new String[0]);

        this.bom2XsdFolderRelativeXsds =
                bom2XsdFolderRelativeXsds.toArray(new String[0]);

        this.xsd4BdsInfos = xsd4BdsInfos.toArray(new Xsd4BdsInfo[0]);

    }

}
