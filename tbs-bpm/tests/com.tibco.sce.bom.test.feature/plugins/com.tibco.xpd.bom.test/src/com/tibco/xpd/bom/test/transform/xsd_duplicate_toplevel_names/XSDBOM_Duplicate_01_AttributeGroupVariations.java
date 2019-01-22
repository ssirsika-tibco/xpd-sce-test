/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.xsd_duplicate_toplevel_names;

public class XSDBOM_Duplicate_01_AttributeGroupVariations extends
        AbstractXSDBOMTest {

    public XSDBOM_Duplicate_01_AttributeGroupVariations() {
        super(new String[] {
                "XSDBOM_Duplicate_01_AttributeGroupVariations.xsd",
                "XSDBOM_Duplicate_01_AttributeGroupVariationsImported.xsd" });
        setPlatformExampleFilesBase("platform:/plugin/com.tibco.xpd.bom.test/test-resources/xsd-duplicate-toplevel-names");
    }
}
