/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.n2.brm.component;

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
import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.DocumentOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Requirements Resolver class that adds document service activity model import
 * package info in the requirements file for PE component. This class gets the
 * xpdl file from bx implementation and checks if there is document service
 * activity in any of its processes. Adds the document service model to the
 * import package list if it finds a document service activity.
 * 
 * <p>
 * This requirement came from XPD-6734
 * 
 * @author bharge
 * @since 11 Sep 2014
 */
public class BusinessProcessDocumentServiceTaskRequirementsResolver implements
        IRequirementsResolver {

    private final String DOCUMENT_MODEL_ACTIVITY_PACKAGE_ID =
            "com.tibco.amxbpm.documentactivity.model"; //$NON-NLS-1$

    /**
     * @see com.tibco.bx.composite.core.extensions.IRequirementsResolver#addImplementationRequirements(com.tibco.amf.sca.model.componenttype.Requirements,
     *      com.tibco.amf.sca.model.extensionpoints.Implementation)
     * 
     * @param arg0
     * @param arg1
     */
    public void addImplementationRequirements(Requirements reqs,
            Implementation impl) {

        if (!(impl instanceof BxServiceImplementation)) {

            return;
        }

        BxServiceImplementation bxImplementation =
                (BxServiceImplementation) impl;
        ServiceImplementation serviceModel = bxImplementation.getServiceModel();
        String xpdlFileName = serviceModel.getModuleName();
        IFile xpdlFile =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getFile(new Path(xpdlFileName));
        WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(xpdlFile);
        if (workingCopy instanceof Xpdl2WorkingCopyImpl) {

            Xpdl2WorkingCopyImpl xpdlWC = (Xpdl2WorkingCopyImpl) workingCopy;
            Package xpdlPackage = (Package) xpdlWC.getRootElement();
            boolean documentServiceActivityFound =
                    isDocumentServiceActivityFound(xpdlPackage);
            if (documentServiceActivityFound) {

                ImportPackage activityModelImportPkg =
                        getDocumentActivityModelImportPkg();
                EList<ImportPackage> packageImports = reqs.getPackageImports();
                packageImports.add(activityModelImportPkg);
            }
        }
    }

    /**
     * Iterate thru each process in the given xpdl package to find if there is
     * any document service activity
     * 
     * @param xpdlPackage
     * @return <code>true</code> if any document service activity is found in
     *         any of the processes in the given xpdl package <code>false</code>
     *         otherwise
     */
    private boolean isDocumentServiceActivityFound(Package xpdlPackage) {

        EList<Process> processes = xpdlPackage.getProcesses();
        for (Process process : processes) {

            Collection<Activity> activities =
                    Xpdl2ModelUtil.getAllActivitiesInProc(process);
            for (Activity activity : activities) {

                DocumentOperation documentOperation = null;
                TaskType taskType = TaskObjectUtil.getTaskTypeStrict(activity);

                if (TaskType.SERVICE_LITERAL.equals(taskType)) {

                    String extensionId =
                            TaskObjectUtil
                                    .getTaskImplementationExtensionId(activity);

                    if (TaskImplementationTypeDefinitions.DOCUMENT_OPERATIONS
                            .equals(extensionId)) {

                        TaskService taskService =
                                ((Task) activity.getImplementation())
                                        .getTaskService();

                        Object otherElement =
                                Xpdl2ModelUtil
                                        .getOtherElement(taskService,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_DocumentOperation());

                        if (otherElement instanceof DocumentOperation) {
                            documentOperation =
                                    (DocumentOperation) otherElement;
                        }
                    }
                }
                if (null != documentOperation) {

                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Create and return an instance of {@link ImportPackage}
     * 
     * @return {@link ImportPackage} instance with the import package id and
     *         version set
     */
    private ImportPackage getDocumentActivityModelImportPkg() {

        ImportPackage importPackage =
                OsgiFactory.eINSTANCE.createImportPackage();
        importPackage.setName(DOCUMENT_MODEL_ACTIVITY_PACKAGE_ID);

        VersionRange vr = OsgiFactory.eINSTANCE.createVersionRange();
        vr.setLower("1.0.0"); //$NON-NLS-1$
        vr.setLowerIncluded(true);
        vr.setUpper("2.0.0"); //$NON-NLS-1$
        vr.setUpperIncluded(false);
        importPackage.setRange(vr);

        return importPackage;
    }

}
