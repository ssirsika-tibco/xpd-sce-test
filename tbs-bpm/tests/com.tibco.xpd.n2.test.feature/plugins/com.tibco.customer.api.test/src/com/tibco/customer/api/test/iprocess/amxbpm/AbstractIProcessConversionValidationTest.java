/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.Test;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.iprocess.amxbpm.imports.validations.IBpmConversionValidator;
import com.tibco.xpd.iprocess.amxbpm.imports.validations.IProcessImportValidator;
import com.tibco.xpd.iprocess.amxbpm.imports.validations.ImportValidationError;
import com.tibco.xpd.iprocess.amxbpm.imports.validations.IpsToBpmConversionValidator;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.SpecialFolderUtil;

/**
 * Base class for testing the Validation cycle of an iPM/iPS to BPM xpdl
 * conversion WITHOUT actually performing the conversion.
 * <p>
 * Subclass just provides the test import resources via
 * {@link #getImportResourcesInfo()} and {@link #getOtherResourcesInfo()} and
 * then implements {@link #doTestImportValidations(List)}
 * 
 * @author aallway
 * @since 3 Jun 2014
 */
public abstract class AbstractIProcessConversionValidationTest extends
        AbstractBaseIProcessToBpmTest {

    /**
     * Validate that the correct validation errors for your test are in the list
     * and {@link #fail()} if not.
     * 
     * @param errors
     */
    protected abstract void doTestImportValidations(
            List<ImportValidationError> errors);

    @Test
    public void testImportValidations() throws Exception {

        IProject project = getMainImportProject();

        CONVERSION_TYPE conversionType = getConversionType();

        IBpmConversionValidator validator = null;

        if (CONVERSION_TYPE.IPM_TO_BPM_IMPORT_AND_CONVERT
                .equals(conversionType)) {
            /*
             * get the IPM to BPM validator
             */
            validator = getIpmToBpmImportValidator(project);

        } else if (CONVERSION_TYPE.IPS_TO_BPM_CONVERT.equals(conversionType)) {
            /*
             * get the IPS to BPM validator
             */
            validator = getIProcStudioToBpmImportValidator(project);

        } else {

            fail("The type of conversion(IPM to BPM / IPS to BPM) was not specified."); //$NON-NLS-1$
        }
        /*
         * run the validator and get the status messages.
         */
        doTestImportValidations(validator.validate(new NullProgressMonitor()));

        /* Done. */
    }

    /**
     * 
     * @param project
     *            the target Project
     * @return the IPM to BPM import Validator
     */
    private IBpmConversionValidator getIpmToBpmImportValidator(IProject project) {

        /*
         * Get files to import and project to convert into...
         */
        List<File> importFiles = new ArrayList<File>();
        for (TestResourceInfo testResourceInfo : getImportResourcesInfo()) {
            importFiles.add(testResourceInfo.getTestFile().getLocation()
                    .toFile());
        }

        /*
         * Run the validator.
         */
        return new IProcessImportValidator(project, importFiles);

    }

    /**
     * 
     * @param project
     *            the target project
     * @return the IPS to BPM conversion Validator
     */
    private IBpmConversionValidator getIProcStudioToBpmImportValidator(
            IProject project) {

        /*
         * Get iProcess Studio xpdls to convert.
         */
        List<IFile> studioIProcessXPDLs = new ArrayList<IFile>();

        SpecialFolder specialFolder =
                SpecialFolderUtil.getSpecialFolderOfKind(project,
                        Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND);

        for (TestResourceInfo testResourceInfo : getImportResourcesInfo()) {
            studioIProcessXPDLs.add(testResourceInfo.getTestFile());
        }

        /*
         * Run the validator.
         */
        return new IpsToBpmConversionValidator(new HashSet<IResource>(
                studioIProcessXPDLs), specialFolder.getFolder());

    }

}
