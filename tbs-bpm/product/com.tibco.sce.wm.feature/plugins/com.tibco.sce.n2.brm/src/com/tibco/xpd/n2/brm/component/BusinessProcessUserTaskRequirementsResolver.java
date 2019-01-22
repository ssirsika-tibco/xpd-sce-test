/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.brm.component;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;

import com.tibco.amf.dependency.osgi.ImportPackage;
import com.tibco.amf.dependency.osgi.OsgiFactory;
import com.tibco.amf.dependency.osgi.VersionRange;
import com.tibco.amf.sca.model.componenttype.Requirements;
import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.bx.amx.model.service.BxServiceImplementation;
import com.tibco.bx.composite.core.extensions.IRequirementsResolver;
import com.tibco.bx.model.service.ServiceImplementation;
import com.tibco.xpd.n2.brm.utils.BRMUtils;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;

/**
 * @author kupadhya
 * 
 */
public class BusinessProcessUserTaskRequirementsResolver implements
        IRequirementsResolver {

    private static final String UPPER_RANGE = "2.0.0";

    private static final String LOWER_RANGE = "1.0.0";

    private static final String COM_TIBCO_N2_UT_MODEL = "com.tibco.n2.ut.model";

    /**
     * @see com.tibco.bx.composite.core.extensions.IRequirementsResolver#addImplementationRequirements(com.tibco.amf.sca.model.componenttype.Requirements,
     *      com.tibco.amf.sca.model.extensionpoints.Implementation)
     * 
     * @param requirements
     * @param implementation
     */
    public void addImplementationRequirements(Requirements requirements,
            Implementation implementation) {
        if (!(implementation instanceof BxServiceImplementation)) {
            return;
        }
        BxServiceImplementation bxImplementation =
                (BxServiceImplementation) implementation;
        ServiceImplementation serviceModel = bxImplementation.getServiceModel();
        String xpdlFileName = serviceModel.getModuleName();
        IFile xpdlFile =
                ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(
                        xpdlFileName));
        WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(xpdlFile);
        if (workingCopy instanceof Xpdl2WorkingCopyImpl) {
            Xpdl2WorkingCopyImpl xpdlWC = (Xpdl2WorkingCopyImpl) workingCopy;
            Package xpdlPackage = (Package) xpdlWC.getRootElement();
            Collection<Activity> manualActivities =
                    getManualActivities(xpdlPackage);
            if (manualActivities != null && !manualActivities.isEmpty()) {
                EList<ImportPackage> packageImports =
                        requirements.getPackageImports();
                ImportPackage importPackageForUserTask =
                        getImportPackageForUserTaskModel();
                packageImports.add(importPackageForUserTask);
            }
        }

    }

    protected Collection<Activity> getManualActivities(Package xpdlPackage) {
        ArrayList<Package> xpdlPackageList = new ArrayList<Package>();
        xpdlPackageList.add(xpdlPackage);
        Collection<Activity> manualActivities =
                BRMUtils.getN2ManualActivities(xpdlPackageList);
        return manualActivities;
    }

    private ImportPackage getImportPackageForUserTaskModel() {
        ImportPackage utModelPackage =
                OsgiFactory.eINSTANCE.createImportPackage();
        utModelPackage.setName(getModelPlugInName());
        VersionRange versionRange = OsgiFactory.eINSTANCE.createVersionRange();
        versionRange.setLower(getModelPlugLowerRange());
        versionRange.setUpper(getModelPlugUpperRange());
        utModelPackage.setRange(versionRange);
        return utModelPackage;
    }

    protected String getModelPlugInName() {
        return COM_TIBCO_N2_UT_MODEL;
    }

    protected String getModelPlugLowerRange() {
        return LOWER_RANGE;
    }

    protected String getModelPlugUpperRange() {
        return UPPER_RANGE;
    }

}
