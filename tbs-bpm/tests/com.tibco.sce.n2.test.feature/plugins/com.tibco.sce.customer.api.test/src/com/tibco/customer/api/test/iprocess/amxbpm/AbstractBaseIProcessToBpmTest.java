/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;

import com.tibco.customer.api.test.Activator;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.n2.test.core.AbstractN2BaseResourceTest;

/**
 * Base class for the iprocess conversion tests.
 * <p>
 * This class contains only the very basic things and does not actually perforam
 * any validation / conversion (just has some common methods etc.
 * 
 * @author aallway
 * @since 3 Jun 2014
 */
public abstract class AbstractBaseIProcessToBpmTest extends
        AbstractN2BaseResourceTest {

    /**
     * Return the set of test resources that are iProcess XPDL format files.
     * <p>
     * <b>NOTE that ONLY during IPM to BPM import the test IPM resources SHOULD
     * NOT have the file extension .xpdl</b> otherwise the
     * XpdlMigrationTestFileManager will automatically attempt to migrate the
     * iprocess XPDL to latest Studio BPM XPDL format version and hence corrupt
     * the source file </b>
     * 
     * @return the importResourcesInfo
     */
    public abstract TestResourceInfo[] getImportResourcesInfo();

    /**
     * Return the sert of Test resources to copy to the test workspace that ARE
     * NOT to be converted from iProcess to bpm xpdl.
     * 
     * @return the importResourcesInfo
     */
    public abstract TestResourceInfo[] getOtherResourcesInfo();

    /**
     * @return The name of the target project in which the conversion result
     *         files are created.
     */
    protected abstract String getMainImportProjectName();

    /**
     * @return The project named as the main target project for converted files.
     */
    protected IProject getMainImportProject() {
        IProject project =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getProject(getMainImportProjectName());
        return project;
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestResources()
     * 
     * @return
     */
    @Override
    protected TestResourceInfo[] getTestResources() {
        /**
         * COMBINE the Import imp xpdl resources and the 'other' resources tp
         * create in the test workspace.
         */
        List<TestResourceInfo> resources = new ArrayList<TestResourceInfo>();

        TestResourceInfo[] importResourcesInfo = getImportResourcesInfo();
        if (importResourcesInfo != null) {
            for (TestResourceInfo testResourceInfo : importResourcesInfo) {
                resources.add(testResourceInfo);
            }
        }

        TestResourceInfo[] otherResourcesInfo = getOtherResourcesInfo();
        if (otherResourcesInfo != null) {
            for (TestResourceInfo testResourceInfo : otherResourcesInfo) {
                resources.add(testResourceInfo);
            }
        }

        return resources.toArray(new TestResourceInfo[0]);

    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestName()
     * 
     * @return
     */
    @Override
    protected String getTestName() {
        return getClass().getName();
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestPlugInId()
     * 
     * @return
     */
    @Override
    protected String getTestPlugInId() {
        return Activator.PLUGIN_ID;
    }

    /**
     * Allows to select the type of Conversion we wish to do
     * <p>
     * 1. Import and Convert IPM Xpdl's to AMX-BPM Xpdl's
     * <p>
     * 2. Convert Studio iProcess Xpdl's to AMX-BPM Xpdl's
     */
    public enum CONVERSION_TYPE {
        IPM_TO_BPM_IMPORT_AND_CONVERT, IPS_TO_BPM_CONVERT;
    }

    /**
     * Gets the Type of Conversion we wish to perform. If the implementers wish
     * to perform IPM to BPM import and convert , then this method should return
     * {@link CONVERSION_TYPE#IPM_TO_BPM_IMPORT_AND_CONVERT} else if the
     * implementer wants to Convert the IPS xpdls to BPM xpdls then this method
     * should return {@link CONVERSION_TYPE#IPS_TO_BPM_CONVERT}
     * <p>
     * Note: Implementers should not return <code>null</code> else the test will
     * fail.
     * 
     * @return {@link CONVERSION_TYPE#IPM_TO_BPM_IMPORT_AND_CONVERT} to perform
     *         IPM to BPM conversion else return
     *         {@link CONVERSION_TYPE#IPS_TO_BPM_CONVERT} to perform IPS to BPM
     *         converiosn.
     */
    protected abstract CONVERSION_TYPE getConversionType();

}
