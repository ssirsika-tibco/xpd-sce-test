/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.bom.test.transform.exports_bom_xsd_rt;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;

import com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip;
import com.tibco.xpd.core.xmlunit.XmlDiff;

/**
 * XPD-5918: checks if the generated xsd doesn't have an empty complex type.
 * (BOM has a complex type with name Products that has a composition association
 * to Product. The composition association name is same as the complex type
 * i.e.Products. This was not outputting the complex type for composition in the
 * generated xsd)
 * 
 * The resultant generated xsd must have
 * 
 * <xsd:complexType ecore:name="Products" id="_kesUEIdREeOtab1uXoFDhg"
 * name="Products"> <xsd:sequence> <xsd:element ecore:name="products"
 * id="_llT944dREeOtab1uXoFDhg" maxOccurs="unbounded" minOccurs="0"
 * name="products" type="Product"/> </xsd:sequence> </xsd:complexType>
 * 
 * instead of <xsd:complexType ecore:name="Products"
 * id="_kesUEIdREeOtab1uXoFDhg" name="Products"> <xsd:sequence/>
 * </xsd:complexType>
 * 
 * @author bharge
 * @since 3 Mar 2014
 */
public class BOMXSD03_UserDefBOMCompTypeAndCompositionAssocSameNameTest extends
        TransformationTestRoundtrip {

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/bom-xsd/round-trip/bom";

    protected String PLATFORM_BUILDER_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/bom-xsd/round-trip/gold/builder";

    protected String MODEL_FILE = "Product.bom";

    protected String GOLD_FILE = "com.fos.product.xsd";

    /**
     * @see com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip#setUp()
     * 
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception {

        modelFileNames = new ArrayList<String>();
        modelFiles = new ArrayList<IFile>();

        platformExampleFilesBase = PLATFORM_EXAMPLE_FILES_BASE;
        platformBuilderGoldFilesBase = PLATFORM_BUILDER_GOLD_FILES_BASE;

        modelFileNames.add(MODEL_FILE);

        goldFileNames.add(GOLD_FILE);

        super.setUp();
    }

    /**
     * @see com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip#testTransformation()
     * 
     * @throws Exception
     */
    @Override
    public void testTransformation() throws Exception {

        List<IStatus> result =
                exportBOMtoXSD(modelFiles.get(0),
                        true,
                        ResourcesPlugin
                                .getWorkspace()
                                .getRoot()
                                .getProject(testProjectName)
                                .getFullPath()
                                .append(outputSpecialFolder.getFolder()
                                        .getName()),
                        true);
        assertEquals("There were errors on this transformation process (simulating an export using builder).",
                0,
                getErrors(result).size());

        processTransformedFiles(modelFiles.get(0));

        for (int index = 0; index < transformedFiles.size(); index++) {
            XmlDiff xmlDiff =
                    initXMLDiff(transformedFiles.get(index),
                            builderGoldFiles.get(index));
            Assert.assertTrue("Builder file must be identical to the gold file",
                    xmlDiff.similar());
        }
    }

}
