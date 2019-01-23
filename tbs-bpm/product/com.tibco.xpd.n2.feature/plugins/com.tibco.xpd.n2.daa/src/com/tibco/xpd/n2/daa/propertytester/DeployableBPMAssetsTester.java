/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.n2.daa.propertytester;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.ProcessResourceItemType;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataBOMTester;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.destinations.GlobalDestinationUtil;
import com.tibco.xpd.n2.daa.Activator;
import com.tibco.xpd.n2.daa.internal.Messages;
import com.tibco.xpd.n2.resources.util.N2Utils;
import com.tibco.xpd.om.resources.OMResourcesActivator;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.internal.indexer.IndexerServiceImpl;
import com.tibco.xpd.resources.propertytesters.SpecialFolderTester;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.xpdl2.Package;

/**
 * Property Tester for BPM project. The test includes:
 * <ul>
 * <li><b>hasDeployableBPMAssets</b>: Tests whether the given
 * <code>IProject</code> is a BPM project and has deploy-able assets (i.e., has
 * an OrgModel or a BOM with Global Data constructs or contains N2 process
 * (packages with only process-interface(s) are not deploy-able)).</li>
 * 
 * 
 * @author agondal
 * @since 9 Aug 2013
 */
public class DeployableBPMAssetsTester extends PropertyTester {

    /**
     * Test to check if the given IProject has deploy-able BPM assets
     */
    public static final String PROP_HAS_DEPLOYABLE_BPM_ASSETS =
            "hasDeployableBPMAssets"; //$NON-NLS-1$

    /**
     * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object,
     *      java.lang.String, java.lang.Object[], java.lang.Object)
     * 
     * @param receiver
     * @param property
     * @param args
     * @param expectedValue
     * @return
     */
    @Override
    public boolean test(Object receiver, String property, Object[] args,
            Object expectedValue) {
        if (receiver instanceof IProject
                && PROP_HAS_DEPLOYABLE_BPM_ASSETS.equals(property)) {
            SpecialFolderTester sfTester = new SpecialFolderTester();
            GlobalDataBOMTester gdTester = new GlobalDataBOMTester();

            IProject project = (IProject) receiver;
            /*
             * If the project is a Studio project then first check whether it
             * has an OrgModel or Global Data model. If it doesn't have these
             * then check if it contains N2 process (packages with process
             * interface only should not be deploy-able).
             */
            if (project.isAccessible()
                    && ProjectUtil.isStudioProject(project)
                    && GlobalDestinationUtil
                            .isGlobalDestinationEnabled(project,
                                    N2Utils.N2_GLOBAL_DESTINATION_ID)
                    && (sfTester
                            .test(project,
                                    SpecialFolderTester.PROP_ISSPECIALFOLDERNOTEMPTY,
                                    new Object[] { OMResourcesActivator.OM_FILE_EXTENSION },
                                    OMResourcesActivator.OM_SPECIAL_FOLDER_KIND) || gdTester
                            .test(project,
                                    GlobalDataBOMTester.PROP_BOM_HASGLOBALDATA,
                                    new Object[] { BOMResourcesPlugin.BOM_FILE_EXTENSION },
                                    BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND))
                    || hasN2Process(project) || hasWorkListFacade(project)
                    || hasGlobalSignalDefinition(project)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param proj
     * @return true if the project contains a process package with BPM-relevant
     *         process
     */
    @SuppressWarnings("restriction")
    private boolean hasN2Process(IProject proj) {

        Map<String, String> additionalAttributes =
                new HashMap<String, String>();
        additionalAttributes.put(IndexerServiceImpl.ATTRIBUTE_PROJECT,
                proj.getName());

        // does the project have a process special folder?
        IndexerItem criteria =
                new IndexerItemImpl(null,
                        ProcessResourceItemType.PROCESSPACKAGE.toString(),
                        null, additionalAttributes);
        final Collection<IndexerItem> results =
                XpdResourcesPlugin
                        .getDefault()
                        .getIndexerService()
                        .query(Xpdl2ResourcesPlugin.PROCESS_INDEXER_ID,
                                criteria);

        // only retrieve Package objects when needed
        if (!results.isEmpty()) {
            final ResourceSet resourceSet =
                    XpdResourcesPlugin.getDefault().getEditingDomain()
                            .getResourceSet();

            // XPD-4708: Run this in a transaction to avoid concurrent
            // modification exception
            try {
                final Boolean result[] = new Boolean[] { false };
                XpdResourcesPlugin.getDefault().getEditingDomain()
                        .runExclusive(new Runnable() {

                            @Override
                            public void run() {
                                boolean hasN2Process = false;
                                for (IndexerItem item : results) {
                                    // Check if package has BPM process
                                    EObject eObject =
                                            resourceSet.getEObject(URI
                                                    .createURI(item.getURI()),
                                                    true);
                                    if (eObject instanceof Package) {
                                        Package pkg = (Package) eObject;
                                        if (N2Utils.hasN2Processes(pkg)) {
                                            hasN2Process = true;
                                            break;
                                        }
                                    }
                                }

                                result[0] = hasN2Process;
                            }
                        });

                return result[0];
            } catch (InterruptedException e) {
                Activator
                        .getDefault()
                        .getLogger()
                        .error(e,
                                Messages.DAAFromProjectExportWizard_problemCheckingProcessesInPackage_longdesc);
            }
        }

        return false;
    }

    /**
     * Returns 'true' if project contains Global signal Definition file in
     * appropriate special folder.
     */
    private boolean hasGlobalSignalDefinition(IProject project) {
        /**
         * Kapil: Because of the plug-in dependency we inline values of the
         * following constants:
         * GlobalSignalDefinitionResourcePlugin.GSD_SPECIAL_FOLDER_KIND = "gsd";
         * GlobalSignalDefinitionResourcePlugin.GSD_FILE_EXTENSION = "gsd;"
         */
        final List<IResource> gsdFiles =
                SpecialFolderUtil
                        .getAllDeepResourcesInSpecialFolderOfKind(project,
                                /* kind */"gsd", //$NON-NLS-1$
                                /* extension */"gsd", //$NON-NLS-1$
                                false);
        return !gsdFiles.isEmpty();
    }

    /**
     * Returns 'true' if project contains Work List Facade file in appropriate
     * special folder.
     */
    private boolean hasWorkListFacade(IProject project) {
        /**
         * JA: Because of the plug-in dependency we inline values of the
         * following constants:
         * WorkListFacadeResourcePlugin.WLF_SPECIAL_FOLDER_KIND = "wlf";
         * WorkListFacadeResourcePlugin.WLF_FILE_EXTENSION = "wlf;"
         */
        final List<IResource> wlfFiles =
                SpecialFolderUtil
                        .getAllDeepResourcesInSpecialFolderOfKind(project,
                                /* kind */"wlf", //$NON-NLS-1$
                                /* extension */"wlf", //$NON-NLS-1$
                                false);
        return !wlfFiles.isEmpty();
    }
}
