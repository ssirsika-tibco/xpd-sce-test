/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.customer.api.iprocess.internal;

import java.util.Collection;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.ProcessResourceItemType;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.destinations.ui.GlobalDestinationHelper;
import com.tibco.xpd.n2.resources.util.N2Utils;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.internal.indexer.IndexerServiceImpl;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.ExtendedAttributesContainer;
import com.tibco.xpd.xpdl2.Process;

/**
 * Utility Class for Conversion, provides methods like
 * getBPMDestinationProcessOrIFCInWorkspace() to be used in the
 * AbstractIProcessToBPMContribution.
 * 
 * @author aprasad
 * @since 16-May-2014
 */
public class ConversionUtility {

    /**
     * Checks whether the BPM process with given procName exists in the
     * workspace. Since the indexer can't check whether procName is a PROCESS OR
     * PROCESSINTERFACE in one go, had to call this twice.
     * 
     * @param procName
     *            , name of process/process interface to check for.
     * @param procOrProcIfc
     *            , check for process/ OR interface valid values 'PROCESS' or
     *            'PROCESSINTERFACE'
     * @return true, if a BPM destination process with given name exists in the
     *         project.
     */
    public static EObject getBPMDestinationProcessOrIFCInWorkspace(
            String procName, String procOrProcIfc) {

        IndexerItemImpl criteria = new IndexerItemImpl();
        // get all process/interface from the workspace
        criteria.set(IndexerServiceImpl.ATTRIBUTE_TYPE, procOrProcIfc);

        Collection<IndexerItem> queryResults =
                XpdResourcesPlugin
                        .getDefault()
                        .getIndexerService()
                        .query(Xpdl2ResourcesPlugin.PROCESS_INDEXER_ID,
                                criteria);

        for (IndexerItem indexerItem : queryResults) {

            String processName = indexerItem.getName();
            int lastIndexOfSlashDelimiter = processName.lastIndexOf('/');

            String procNameOnly =
                    processName.substring(lastIndexOfSlashDelimiter + 1,
                            processName.length());

            if (procName.equals(procNameOnly)) {
                // check destination to be BPM
                String struri = indexerItem.getURI();
                URI uri = URI.createURI(struri);

                if (uri != null) {
                    EObject processOrInterface =
                            ProcessUIUtil.getEObjectFrom(uri);

                    if (processOrInterface != null) {

                        IFile file =
                                WorkingCopyUtil.getFile(processOrInterface);

                        if (file != null) {

                            IContainer processPackageFolder = file.getParent();

                            if (processPackageFolder != null) {
                                /*
                                 * Proceed only if the Process or Interface is
                                 * not in the hidden special folder or the
                                 * folder name does not start with '.'(dot).
                                 */
                                if (!processPackageFolder.isHidden()
                                        && !processPackageFolder.getName()
                                                .startsWith(".")) { //$NON-NLS-1$

                                    /*
                                     * Check for existing process with BPM
                                     * destination , duplicate only if this is a
                                     * BPM destination process/interface
                                     */
                                    if (containsBPMDestination(processOrInterface) == true) {
                                        return processOrInterface;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Checks if given eObject has BPM destination enabled.
     * 
     * @param processOrInterface
     * @return true if given eObject has BPM destination enabled
     */
    public static boolean containsBPMDestination(EObject processOrInterface) {

        if (processOrInterface instanceof ExtendedAttributesContainer) {
            Collection<ExtendedAttribute> extendedAttributes =
                    ((ExtendedAttributesContainer) processOrInterface)
                            .getExtendedAttributes();

            Collection<String> globalDestinationNamesForId =
                    GlobalDestinationHelper
                            .getGlobalDestinationNamesForId(N2Utils.N2_GLOBAL_DESTINATION_ID);

            for (ExtendedAttribute eachExtendedAttribute : extendedAttributes) {
                if (DestinationUtil.DESTINATION_ATTRIBUTE
                        .equals(eachExtendedAttribute.getName())) {

                    String value = eachExtendedAttribute.getValue();
                    if (value != null && !value.isEmpty()) {

                        if (globalDestinationNamesForId.contains(value))
                            return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks for existence of a BPM destination {@link Process} with same name
     * as that of the Given {@link Process} in the workspace, returns true if
     * exists.
     * 
     * @param conversionProcess
     * @return true if a BPM destination {@link Process} with same name as the
     *         given {@link Process} exist in the workspace.
     */
    public static boolean isExistingProcess(Process conversionProcess) {

        if (conversionProcess == null) {
            return false;
        }

        EObject processInWorkspace =
                getBPMDestinationProcessOrIFCInWorkspace(conversionProcess.getName(),
                        ProcessResourceItemType.PROCESS.name());

        return (processInWorkspace != null);
    }

    /**
     * Checks for existence of a BPM destination {@link ProcessInterface} with
     * same name as that of the Given {@link ProcessInterface} in the workspace,
     * returns true if exists.
     * 
     * @param conversionProcess
     * @return true if a BPM destination {@link ProcessInterface} with same name
     *         as the given {@link ProcessInterface} exist in the workspace.
     */
    public static boolean isExistingProcessInterface(
            ProcessInterface conversionProcessInterface) {

        if (conversionProcessInterface == null) {
            return false;
        }

        EObject processInterfaceInWorkspace =
                getBPMDestinationProcessOrIFCInWorkspace(conversionProcessInterface
                        .getName(),
                        ProcessResourceItemType.PROCESSINTERFACE.name());

        return (processInterfaceInWorkspace != null);
    }
}
