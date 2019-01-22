/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.imports_wsdl_bom;

import java.util.ArrayList;
import java.util.Collection;

import junit.framework.Assert;

import org.eclipse.core.resources.IFile;

import com.tibco.xpd.bom.test.transform.common.TransformationTest;
import com.tibco.xpd.bom.wsdltransform.api.WSDLTransformUtil;

/**
 * Its a very base transformation test it only test for now if transformation
 * will run without exceptions. You can later test manually the result in the
 * junit-workspace.
 * <p>
 * <i>Created: 31 Mar 2010</i>
 * </p>
 * 
 * @author glewis
 */
@SuppressWarnings("nls")
public class WSDLBOM12_GetBomNamesFromWSDLTest extends TransformationTest {

    protected String TEST_FILE_NAME = "myTest.bom";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl";

    protected String MODEL_FILE = "AtomicRPX.wsdl";

    protected String MODEL_FILE2 = "AtomicRPX/AtomicRPXQueryCoupons.xsd";

    protected String MODEL_FILE3 =
            "AtomicRPX/inner/AtomicRPXChangeCouponStatus.xsd";

    protected String MODEL_FILE4 = "AtomicRPX/inner/inner2/AtomicRPXTypes.xsd";

    protected String MODEL_FILE5 = "OrderService.wsdl";

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        modelFileNames = new ArrayList<String>();
        modelFiles = new ArrayList<IFile>();

        platformExampleFilesBase = PLATFORM_EXAMPLE_FILES_BASE;
        modelFileNames.add(MODEL_FILE);
        modelFileNames.add(MODEL_FILE2);
        modelFileNames.add(MODEL_FILE3);
        modelFileNames.add(MODEL_FILE4);
        modelFileNames.add(MODEL_FILE5);

        super.setUp();
    }

    public void testTransformation() throws Exception {

        IFile wsdlFile = outputSpecialFolder.getFolder().getFile(MODEL_FILE);

        // check it 2 times to check speed of second time round
        for (int i = 0; i < 2; i++) {
            Collection<String> outputBOMNames =
                    WSDLTransformUtil.getOutputBOMNames(wsdlFile);

            Assert.assertTrue(outputBOMNames
                    .contains("com.mobile.t.esp.atomic.rpx.QueryCoupons.bom"));

            Assert
                    .assertTrue(outputBOMNames
                            .contains("com.mobile.t.esp.atomic.rpx.ChangeCouponStatus.bom"));

            Assert.assertTrue(outputBOMNames
                    .contains("com.mobile.t.esp.atomic.rpx.types.bom"));

            Assert.assertTrue(outputBOMNames
                    .contains("com.mobile.t.atomic.rpx.services.bom"));
        }

        wsdlFile = outputSpecialFolder.getFolder().getFile(MODEL_FILE5);

        // check it 2 times to check speed of second time round
        for (int i = 0; i < 2; i++) {
            Collection<String> outputBOMNames =
                    WSDLTransformUtil.getOutputBOMNames(wsdlFile);

            Assert
                    .assertTrue(outputBOMNames
                            .contains("net.siemens.esb.PublicSchemas.Asset.Generic._2010._03.bom"));

            Assert
                    .assertTrue(outputBOMNames
                            .contains("net.siemens.esb.PublicSchemas.PurchaseOrder.Generic._2010._03.bom"));

            Assert
                    .assertTrue(outputBOMNames
                            .contains("net.siemens.esb.PublicServices.Order._2010._03.bom"));

            Assert
                    .assertTrue(outputBOMNames
                            .contains("net.siemens.esb.PublicSchemas.Person.Generic._2010._03.bom"));
        }
    }
}
