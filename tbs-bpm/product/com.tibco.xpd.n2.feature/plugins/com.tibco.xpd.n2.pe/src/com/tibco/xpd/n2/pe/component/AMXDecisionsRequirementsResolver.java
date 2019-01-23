/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.pe.component;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.query.conditions.eobjects.EObjectCondition;
import org.eclipse.emf.query.handlers.PruneHandler;
import org.eclipse.emf.query.statements.FROM;
import org.eclipse.emf.query.statements.IQueryResult;
import org.eclipse.emf.query.statements.SELECT;
import org.eclipse.emf.query.statements.WHERE;

import com.tibco.amf.dependency.osgi.ImportPackage;
import com.tibco.amf.dependency.osgi.OsgiFactory;
import com.tibco.amf.dependency.osgi.VersionRange;
import com.tibco.amf.sca.model.componenttype.Requirements;
import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.bx.amx.model.service.BxServiceImplementation;
import com.tibco.bx.composite.core.extensions.IRequirementsResolver;
import com.tibco.bx.model.service.ServiceImplementation;
import com.tibco.xpd.destinations.ui.GlobalDestinationHelper;
import com.tibco.xpd.n2.resources.util.N2Utils;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * will add import packages for AMX decisions service activity model if it is
 * there in the business process
 * 
 * @author kupadhya
 * 
 */
public class AMXDecisionsRequirementsResolver implements IRequirementsResolver {

    private static final String UPPER_RANGE = "2.0.0"; //$NON-NLS-1$

    private static final String LOWER_RANGE = "1.0.0"; //$NON-NLS-1$

    private static final String COM_TIBCO_AMX_DECISIONS_MODEL =
            "com.tibco.adec.dt.model"; //$NON-NLS-1$

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
                ResourcesPlugin.getWorkspace().getRoot()
                        .getFile(new Path(xpdlFileName));
        WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(xpdlFile);
        if (workingCopy instanceof Xpdl2WorkingCopyImpl) {
            Xpdl2WorkingCopyImpl xpdlWC = (Xpdl2WorkingCopyImpl) workingCopy;
            Package xpdlPackage = (Package) xpdlWC.getRootElement();
            Collection<Activity> amxDecisionsActivities =
                    getAMXDecisionServiceActivities(xpdlPackage);
            if (amxDecisionsActivities != null
                    && !amxDecisionsActivities.isEmpty()) {
                EList<ImportPackage> packageImports =
                        requirements.getPackageImports();
                ImportPackage importPackageForUserTask =
                        getImportPackageForUserTaskModel();
                packageImports.add(importPackageForUserTask);
            }
        }
    }

    protected Collection<Activity> getAMXDecisionServiceActivities(
            Package xpdlPackage) {
        ArrayList<Package> xpdlPackageList = new ArrayList<Package>();
        xpdlPackageList.add(xpdlPackage);
        Collection<Activity> amxDecisionsActivities =
                getAMXDecisionActivities(xpdlPackageList);
        return amxDecisionsActivities;
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
        return COM_TIBCO_AMX_DECISIONS_MODEL;
    }

    protected String getModelPlugLowerRange() {
        return LOWER_RANGE;
    }

    protected String getModelPlugUpperRange() {
        return UPPER_RANGE;
    }

    @SuppressWarnings("unchecked")
    public static Collection<Activity> getAMXDecisionActivities(
            Collection<Package> xpdlPackages) {

        // Prune all processes without global N2 destination environment
        // enabled also page flows.
        PruneHandler pruneHandler = new PruneHandler() {
            /** {@inheritDoc} */
            @Override
            public boolean shouldPrune(EObject object) {
                if (object instanceof com.tibco.xpd.xpdl2.Process) {
                    com.tibco.xpd.xpdl2.Process process =
                            (com.tibco.xpd.xpdl2.Process) object;
                    if (!GlobalDestinationHelper
                            .isGlobalDestinationEnabled(process,
                                    N2Utils.N2_GLOBAL_DESTINATION_ID)) {
                        return true;
                    }
                }
                return false;
            }
        };
        IQueryResult result =
                new SELECT(new FROM(xpdlPackages), new WHERE(
                        new IsAMXDecisionsActivity(pruneHandler))).execute();
        Exception e = result.getException();
        if (e != null) {
            throw new RuntimeException(e);
        }
        return (Collection<Activity>) result.getEObjects();
    }

    /**
     * Condition for selection of manual task activities.
     */
    public static class IsAMXDecisionsActivity extends EObjectCondition {
        public IsAMXDecisionsActivity() {
            super();
        }

        public IsAMXDecisionsActivity(PruneHandler pruneHandler) {
            super(pruneHandler);
        }

        @Override
        public boolean isSatisfied(EObject object) {
            if (object instanceof Activity) {
                Activity activity = (Activity) object;
                if (activity.getImplementation() instanceof Task) {
                    Task task = (Task) activity.getImplementation();
                    TaskService taskService = task.getTaskService();
                    if (taskService != null) {
                        String type =
                                (String) Xpdl2ModelUtil
                                        .getOtherAttribute(taskService,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_ImplementationType());
                        if ("DecisionService".equals(type)) { //$NON-NLS-1$
                            return true;
                        }
                    }
                }
            }
            return false;
        }

    }

}
