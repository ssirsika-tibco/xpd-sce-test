/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.xsdbom_attr_groups;

public class XSDBOM_AttributeGroup01C_SimpleAttrGroup_IncludeAttrGroup extends
        AbstractXSDBOMTest {

    public XSDBOM_AttributeGroup01C_SimpleAttrGroup_IncludeAttrGroup() {
        super(
                new String[] {
                        "XSDBOM_AttributeGroup01C_SimpleAttrGroup_IncludeAttrGroup.xsd",
                        "XSDBOM_AttributeGroup01C_SimpleAttrGroup_IncludedSchema.xsd" });
        setPlatformExampleFilesBase("platform:/plugin/com.tibco.xpd.bom.test/test-resources/xsd-attribute-group");
    }
}
