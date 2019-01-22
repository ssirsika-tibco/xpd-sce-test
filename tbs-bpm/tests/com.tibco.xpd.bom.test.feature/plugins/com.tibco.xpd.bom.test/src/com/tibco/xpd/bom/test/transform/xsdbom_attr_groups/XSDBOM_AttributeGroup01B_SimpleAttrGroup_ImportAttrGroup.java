/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.xsdbom_attr_groups;

public class XSDBOM_AttributeGroup01B_SimpleAttrGroup_ImportAttrGroup extends
        AbstractXSDBOMTest {

    public XSDBOM_AttributeGroup01B_SimpleAttrGroup_ImportAttrGroup() {
        super(new String[] {
                "XSDBOM_AttributeGroup01B_SimpleAttrGroup_ImportAttrGroup.xsd",
                "XSDBOM_AttributeGroup01B_SimpleAttrGroup_ImportedSchema.xsd" });
        setPlatformExampleFilesBase("platform:/plugin/com.tibco.xpd.bom.test/test-resources/xsd-attribute-group");
    }
}
