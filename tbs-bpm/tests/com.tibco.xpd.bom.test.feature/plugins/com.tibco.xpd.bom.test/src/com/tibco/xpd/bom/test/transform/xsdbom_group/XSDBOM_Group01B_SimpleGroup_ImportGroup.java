/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.xsdbom_group;

public class XSDBOM_Group01B_SimpleGroup_ImportGroup extends AbstractXSDBOMTest {

    public XSDBOM_Group01B_SimpleGroup_ImportGroup() {
        super(new String[] { "XSDBOM_Group01B_SimpleGroup_ImportGroup.xsd",
                "XSDBOM_Group01B_SimpleGroup_ImportedSchema.xsd" });
        setPlatformExampleFilesBase("platform:/plugin/com.tibco.xpd.bom.test/test-resources/xsd-group");
    }
}
