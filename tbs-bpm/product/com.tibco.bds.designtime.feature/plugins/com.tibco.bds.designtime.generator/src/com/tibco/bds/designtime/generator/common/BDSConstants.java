package com.tibco.bds.designtime.generator.common;

/**
 * BDS Specific constants
 * 
 */
public class BDSConstants {

    // Field descriptor #6 I
    public static final int CASE_DATA_STORE_DEFAULT_MINIMUM_STRING_LENGTH = 400;

    // Field descriptor #6 I
    public static final int CASE_DATA_STORE_DEFAULT_MAXIMUM_NUMERIC_PRECISION =
            31;

    // Internal database Table Name constants, these map directly to the
    // attribute name, so can not be used as attribute names

    // Column names for BDS id/version/case id attributes
    public static final String BDS_ID_COLNAME = "BDS_ID";

    public static final String BDS_VERSION_COLNAME = "BDS_VERSION";

    public static final String BDS_OWNER_ID_COLNAME = "BDS_OWNER_ID";

    public static final String BDS_BOM_FILE_NAME_ANNO = "BomFileName";

    // The following values are temporary as they are in com.tibco.bds.shared,
    // but when we first added this it was on a hot-fix, where we did not want
    // to pull in the shared item into studio at this point. Once this is pulled
    // into line, then we can safely remove this and everything should still
    // work
    public static final String BDS_SUMMARY = "summary";

    // The BDS Case Type is used to store the fully qualified class name, it is
    // called DTYPE so that it's DB column name matches the name used when BOMs
    // have the Discriminator value that is automatically populated at runtime
    // by Hibernate
    public static final String BDS_CASE_TYPE_COLNAME = "DTYPE";

    // The version of the eCore generation being used to create the eCore
    // This can be bumped at any point so that we know what the root version
    // creating this eCore is, if this does not exist, then it means it is
    // pre-4.0 of Studio that generated it
    public static final String currentEcoreFormat = "1";
}
