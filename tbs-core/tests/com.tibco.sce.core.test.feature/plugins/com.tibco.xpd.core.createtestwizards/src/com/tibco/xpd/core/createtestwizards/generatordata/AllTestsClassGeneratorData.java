/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.core.createtestwizards.generatordata;

import java.util.HashSet;
import java.util.Set;

/**
 * AllTestsClassGeneratorData
 * 
 * 
 * @author aallway
 * @since 3.3 (11 Sep 2009)
 */
public class AllTestsClassGeneratorData {

    public Set<String> testClassNames = new HashSet<String>();

    public String testPackageId;

    public AllTestsClassGeneratorData(Set<String> testClassNames,
            String testPackageId) {
        this.testClassNames = testClassNames;
        this.testPackageId = testPackageId;
    }

}
