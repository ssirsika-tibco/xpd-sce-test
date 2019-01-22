/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.bom.test.transform.incremental_exports_bom_xsd;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 *
 *
 * @author aallway
 * @since 11 Jul 2013
 */
@RunWith(Suite.class)
@SuiteClasses({
        BOMXSD01_ClassTransformationTest.class,
        BOMXSD02_ClassGeneralisingInnerPkgClsTransformationTest.class,
        BOMXSD03_ClassGeneralisingExternalModelClsTransformationTest.class,
        BOMXSD04_ClassWithAttributeTypeInsideInnerPackageTransformationTest.class,
        BOMXSD05_ClassWithAttributeTypeInsideExternalModelTransformationTest.class,
        BOMXSD06_ClassPreserveTransformationTest.class,
        BOMXSD07_ClassGeneralisingInnerPkgClsPreserveTransformationTest.class,
        BOMXSD08_ClassGeneralisingExternalModelClsPreserveTransformationTest.class,
        BOMXSD09_ClassWithAttributeTypeInsideInnerPackagePreserveTransformationTest.class,
        BOMXSD10_ClassWithAttributeTypeInsideExternalModelPreserveTransformationTest.class })
public class AllTests {

}
