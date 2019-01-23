/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.xsdbom_group;

public class XSDBOM_Group01C_SimpleGroup_IncludeGroup extends
        AbstractXSDBOMTest {

    public XSDBOM_Group01C_SimpleGroup_IncludeGroup() {
        super(new String[] { "XSDBOM_Group01C_SimpleGroup_IncludeGroup.xsd",
                "XSDBOM_Group01C_SimpleGroup_IncludedSchema.xsd" });
        setPlatformExampleFilesBase("platform:/plugin/com.tibco.xpd.bom.test/test-resources/xsd-group");
    }
}
