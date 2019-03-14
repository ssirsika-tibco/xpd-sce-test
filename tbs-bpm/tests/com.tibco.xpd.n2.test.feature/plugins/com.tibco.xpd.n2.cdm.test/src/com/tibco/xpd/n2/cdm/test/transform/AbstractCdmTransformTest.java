/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.cdm.test.transform;

import java.util.List;
import java.util.function.Function;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.uml2.uml.Model;
import org.junit.Assert;

import com.tibco.bpm.da.dm.api.DataModel;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.core.test.util.AbstractBaseResourceTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.n2.cdm.transform.BomTransformer;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Base test class for BOM->CDM transformation tests.
 *
 * @author jarciuch
 * @since 4 Mar 2019
 */
public abstract class AbstractCdmTransformTest
        extends AbstractBaseResourceTest {

    /**
     * BOM -> CDM model transformation result.
     */
    protected static class BomCdmTransfomation {
        private DataModel cdmModel;

        private Model bomModel;

        public BomCdmTransfomation(DataModel cdmModel, Model bomModel) {
            this.cdmModel = cdmModel;
            this.bomModel = bomModel;
        };

        /**
         * returns Case Data Model after transformation.
         */
        public Model getBomModel() {
            return bomModel;
        }

        /**
         * returns source BOM model.
         */
        public DataModel getCdmModel() {
            return cdmModel;
        }
    };

    /**
     * Gets a .bom file names in the project to be imported from this test
     * plug-in: resources/BomCdmTransformTest/Business Objects.
     * <p>
     * For example: Arrays.asList("BusinessDataProject.bom","Simple.bom",...)
     * </p>
     * 
     * @return bom filenames to be tested imported.
     */
    protected abstract List<String> getBomFileNames();

    /**
     * Gets the workspace path to the folder that contains the .bom file to be
     * tested.
     * <p>
     * For example: "BomCdmTransformTest/Business Objects"
     * </p>
     * 
     * @return workspace folder path containing the .bom file to be tested.
     */
    protected String getBomFolderPath() {
        return "BomCdmTransformTest/Business Objects"; //$NON-NLS-1$
    }

    protected static final String BOM_SF_KIND = "{bom}"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestName()
     *
     * @return
     */
    @Override
    protected String getTestName() {
        return "BOM->CDM transformation test"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestResources()
     *
     * @return
     */
    @Override
    protected TestResourceInfo[] getTestResources() {
        Function<String, TestResourceInfo> nameToTestResource =
                bomFileName -> new TestResourceInfo("resources", //$NON-NLS-1$
                        String.format("%s%s/%s", //$NON-NLS-1$
                                getBomFolderPath(),
                                BOM_SF_KIND,
                                bomFileName));
        return getBomFileNames().stream().map(nameToTestResource)
                .toArray(TestResourceInfo[]::new);
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestPlugInId()
     *
     * @return
     */
    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.n2.cdm.test"; //$NON-NLS-1$
    }

    /**
     * The test method creates the basic OrgModel, does the Org Model to DE
     * model transformation. Validates the transformed DE model .
     * 
     * @throws Exception
     */
    public BomCdmTransfomation getBomCdmTransformation(String bomFolderPath,
            String bomFileName) throws Exception {
        // Get the Model Resource
        String bomPath = String.format("%s/%s", bomFolderPath, bomFileName); //$NON-NLS-1$
        IFile bomFile = ResourcesPlugin.getWorkspace().getRoot()
                .getFile(new Path(bomPath));
        // Get BOM WorkingCopy
        WorkingCopy bomWorkingCopy = WorkingCopyUtil.getWorkingCopy(bomFile);
        // Obtain BOM Model
        Model bomModel = null;
        if (bomWorkingCopy instanceof BOMWorkingCopy
                && ((BOMWorkingCopy) bomWorkingCopy)
                        .getRootElement() instanceof Model
                && ((BOMWorkingCopy) bomWorkingCopy).getRootElement()
                        .eResource() != null) {
            bomModel =
                    (Model) ((BOMWorkingCopy) bomWorkingCopy).getRootElement();
        }
        assertNotNull("Bom Model could not be loaded", bomModel); //$NON-NLS-1$
        // Transform to CDM model
        long start = System.currentTimeMillis();
        DataModel cdmModel = new BomTransformer().transformBomModel(bomModel);
        long elapsed = System.currentTimeMillis() - start;
        System.out.printf("BOM->CDM transf. time [%s]: %d ms./n", //$NON-NLS-1$
                bomFileName,
                elapsed);
        Assert.assertNotNull(cdmModel);
        return new BomCdmTransfomation(cdmModel, bomModel);
    }

}
