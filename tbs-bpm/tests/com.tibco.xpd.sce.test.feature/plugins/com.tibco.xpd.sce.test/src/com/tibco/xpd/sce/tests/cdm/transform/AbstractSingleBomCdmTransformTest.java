/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.tests.cdm.transform;

import java.util.Arrays;
import java.util.List;

import org.eclipse.uml2.uml.Model;

import com.tibco.bpm.da.dm.api.DataModel;

/**
 * Test single bom file transformation.
 *
 * @author jarciuch
 * @since 12 Mar 2019
 */
public abstract class AbstractSingleBomCdmTransformTest
        extends AbstractCdmTransformTest {

    /**
     * @return <code>true</<code> if you would like to see output model.
     */
    protected boolean printCdmModel() {
        return false;
    }

    /**
     * Gets a .bom file name to be tested. The file will be imported to the
     * project from this test plug-in:
     * <code>resources/BomCdmTransformTest/Business Objects</code>.
     * <p>
     * For example: "Simple.bom"
     * </p>
     * 
     * @return bom filename to be tested.
     */
    protected abstract String getBomFileName();

    /**
     * Asserts transformation of the bom model (taken from
     * {@link #getBomFileName()} file).
     * 
     * @param cdmModel
     *            result CDM model.
     * @param bomModel
     *            source BOM model.
     */
    protected abstract void assertBomCdmTransformation(DataModel cdmModel,
            Model bomModel);

    /**
     * @see com.tibco.xpd.sce.tests.cdm.transform.AbstractCdmTransformTest#getBomFileName()
     */
    @Override
    protected List<String> getBomFileNames() {
        return Arrays.asList(getBomFileName());
    }

    /**
     * Execute transformation and calls
     * {@link #assertBomCdmTransformation(DataModel, DataModel)} to assert the
     * result.
     * 
     * @throws Exception
     */
    public void testBomCdmTransformation() throws Exception {
        BomCdmTransfomation transform =
                getBomCdmTransformation(getBomFolderPath(), getBomFileName());
        DataModel cdmModel = transform.getCdmModel();
        if (printCdmModel()) {
            System.out.printf("========== CDM model for %s =============\n", //$NON-NLS-1$
                    getBomFileName());
            System.out.println(cdmModel.serialize());
            System.out.printf("---------- END model for %s -------------\n", //$NON-NLS-1$
                    getBomFileName());
        }
        assertBomCdmTransformation(transform.getCdmModel(),
                transform.getBomModel());
    }

}