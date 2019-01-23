package com.tibco.xpd.n2.test.core.wsdlconversion;

/**
 * Data class to hold a a bds project name and the expected xsds for each bds
 * project.
 * <p>
 * These are to store the 'gold standard' at time of test creation.
 * 
 * @author aallway
 * @since 19 Apr 2011
 */
public class Xsd4BdsInfo {
    public String bomName;

    public String bdsProjectName;

    public String[] modelFolderRelativeXsds;

    /**
     * @param bomName
     * @param bdsProjectName
     * @param modelFolderRelativeXsds
     */
    public Xsd4BdsInfo(String bomName, String bdsProjectName,
            String[] modelFolderRelativeXsds) {
        super();
        this.bomName = bomName;
        this.bdsProjectName = bdsProjectName;
        this.modelFolderRelativeXsds = modelFolderRelativeXsds;
    }

}