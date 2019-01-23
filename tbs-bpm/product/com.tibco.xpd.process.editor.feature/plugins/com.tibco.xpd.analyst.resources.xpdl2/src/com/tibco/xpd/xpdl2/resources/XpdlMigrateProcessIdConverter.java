/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.xpdl2.resources;

import java.util.Collection;
import java.util.HashMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.internal.indexer.IndexerServiceImpl;
import com.tibco.xpd.resources.util.SpecialFolderUtil;

/**
 * Converter utility used by MigrateStudioXPDL_2.xslt to handle process ids or
 * process interface ids. The Ids are either generated or looked up in the
 * indexer
 * 
 * @author rsomayaj
 * @since 3.3 (23 Aug 2010)
 */
public class XpdlMigrateProcessIdConverter {

    /* 
     * Map of process id (from package) to process id. 
     * 
     * The id is either
     * 
     * a) IF the process was included in the package we're importing then it's the 
     * new unique id generated for the process (via (addImportPackageProcess())
     * 
     * b) IF the process is _NOT_ included in the package we're importing _BUT_ there 
     * is a process with the same _NAME_ already in workspace, then it is the id of that process in workspace.
     * 
     * c) IF the process is _NOT_ included in the package we're importing _AND_ a process
     * with the same name _DOES NOT_ exist in workspace, then it's own id is returned. 
     */
    private HashMap<String, String> processIdToActualId =
            new HashMap<String, String>();

    /*
     * As per processIdToActualId but for prorcess interface.
     */
    private HashMap<String, String> interfaceIdToActualId =
            new HashMap<String, String>();

    /**
     * Add a workflow process that exists in the incoming import package and
     * assign a new unique id for it.
     * 
     * @param importId
     *            Id of process as defined in imported package
     */
    public void addImportPackageProcess(String importId) {
        processIdToActualId.put(importId, EcoreUtil.generateUUID());
        return;
    }

    /**
     * Add a workflow process that exists in the incoming import package and
     * assign a new unique id for it.
     * 
     * @param importId
     *            Id of process as defined in imported package
     */
    public void addImportPackageInterface(String importId) {
        interfaceIdToActualId.put(importId, EcoreUtil.generateUUID());
        return;
    }

    /**
     * Get the actual process id for the process in (or referenced by) the local
     * package.
     * <p>
     * a) IF the process was included in the package we're importing then it's
     * the new unique id generated for the process (via
     * (addImportPackageProcess())
     * 
     * b) IF the process is _NOT_ included in the package we're importing _BUT_
     * there is a process with the same _NAME_ already in workspace, then it is
     * the id of that process in workspace.
     * 
     * c) IF the process is _NOT_ included in the package we're importing _AND_
     * a process with the same name _DOES NOT_ exist in workspace, then it's own
     * id is returned.
     * 
     * @param importId
     *            Id of process as defined in imported package
     * 
     * @return The process Id.
     */
    public String getActualProcessId(String importId) {
        if (processIdToActualId.containsKey(importId)) {
            /* Already generated or looked up. */
            return processIdToActualId.get(importId);
        }

        /* 
         * The process is not in the local package 
         * (because if it was it should have had addImportPackageProcess() called)) 
         * _AND_ we haven't tried to look it up in index.
         * 
         * So look for process with this (v1 id) as it's name in target workspace.
         */
        // TODO lookup process by name = improtPackageId.
        String workspaceId = getProcessForName(importId);
        if (workspaceId != null && workspaceId.length() > 0) {
            /* Save this for next call (saves looking up multi times. */
            processIdToActualId.put(importId, workspaceId);
        } else {
            /* 
             * Just use the original id if all else fails (will mean broken reference 
             * in xpdl but at least user caqn see original name in xpdl if necessary). 
             */
            workspaceId = importId;
            processIdToActualId.put(importId, workspaceId);
        }

        return workspaceId;
    }

    /**
     * @param importId
     * @return
     */
    private String getProcessForName(String importId) {
        return getIdFromIndexerItem(importId, "PROCESS"); //$NON-NLS-1$
    }

    /**
     * Get actual id for process interface (as per rules laid out in
     * getActualProcessId() above).
     * 
     * @param importId
     *            Id of process as defined in imported package
     * 
     * @return The process Id.
     */
    public String getActualProcessInterfaceId(String importId) {
        if (interfaceIdToActualId.containsKey(importId)) {
            /* Already generated or looked up. */
            return interfaceIdToActualId.get(importId);
        }

        /* 
         * The process is not in the local package 
         * (because if it was it should have had addImportPackageProcess() called)) 
         * _AND_ we haven't tried to look it up in index.
         * 
         * So look for process with this (v1 id) as it's name in target workspace.
         */
        // TODO lookup process by name = improtPackageId.
        String workspaceId = getInterfaceForName(importId);
        if (workspaceId != null && workspaceId.length() > 0) {
            /* Save this for next call (saves looking up multi times. */
            interfaceIdToActualId.put(importId, workspaceId);
        } else {
            /* 
             * Just use the original id if all else fails (will mean broken reference 
             * in xpdl but at least user caqn see original name in xpdl if necessary). 
             */
            workspaceId = importId;
            interfaceIdToActualId.put(importId, workspaceId);
        }

        return workspaceId;
    }

    /**
     * @param importId
     * @return
     */
    private String getInterfaceForName(String importId) {
        return getIdFromIndexerItem(importId, "PROCESSINTERFACE"); //$NON-NLS-1$
    }

    /**
     * @param importId
     * @param type
     * @return
     */
    private String getIdFromIndexerItem(String importId, String type) {
        IndexerItem indexerItem = getIndexerItem(importId, type);
        if (indexerItem != null) {
            String itemId = indexerItem.get("item_id"); //$NON-NLS-1$
            if (itemId != null) {
                return itemId;
            }
        }

        return ""; //$NON-NLS-1$
    }

    /**
     * @param importId
     * @param type
     * @return
     */
    private IndexerItem getIndexerItem(String importId, String type) {
        IndexerItemImpl criteria = new IndexerItemImpl();
        criteria.setType(type);
        Collection<IndexerItem> queryResults =
                XpdResourcesPlugin.getDefault().getIndexerService()
                        .query(Xpdl2ResourcesPlugin.PROCESS_INDEXER_ID,
                                criteria);

        for (IndexerItem indexerItem : queryResults) {
            String processName = indexerItem.getName();
            int lastIndexOfSlashDelimiter = processName.lastIndexOf('/');

            String procNameOnly =
                    processName.substring(lastIndexOfSlashDelimiter + 1,
                            processName.length());
            if (importId.equals(procNameOnly)) {
                return indexerItem;
            }
        }
        return null;

    }

    /**
     * @param importId
     * @return
     */
    public String getPackageName(String importId, String type) {
        IndexerItem indexerItem = getIndexerItem(importId, type);
        if (indexerItem != null) {
            String attributePath =
                    indexerItem.get(IndexerServiceImpl.ATTRIBUTE_PATH);
            IndexerItemImpl criteria = new IndexerItemImpl();
            criteria.set(IndexerServiceImpl.ATTRIBUTE_TYPE, "PROCESSPACKAGE"); //$NON-NLS-1$
            criteria.set(IndexerServiceImpl.ATTRIBUTE_PATH, attributePath);

            Collection<IndexerItem> queryResults =
                    XpdResourcesPlugin.getDefault().getIndexerService()
                            .query(Xpdl2ResourcesPlugin.PROCESS_INDEXER_ID,
                                    criteria);

            if (queryResults.size() == 1) {
                IndexerItem item = queryResults.iterator().next();
                return item.getName();

            }
        }
        return ""; //$NON-NLS-1$
    }

    /**
     * @param importId
     * @return
     */
    public String getPackageSpecialFolderRelativePath(
            String importId, String type) {

        IndexerItem indexerItem = getIndexerItem(importId, type);
        if (indexerItem != null) {
            String attributePath =
                    indexerItem.get(IndexerServiceImpl.ATTRIBUTE_PATH);

            IFile xpdlFile =
                    ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(
                            attributePath));
            if (xpdlFile != null && xpdlFile.isAccessible()) {
                IPath specialFolderRelativePath =
                        SpecialFolderUtil
                                .getSpecialFolderRelativePath(xpdlFile);
                return specialFolderRelativePath.toPortableString();
            }
        }
        return "";// return packagename //$NON-NLS-1$
    }

}
