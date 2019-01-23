/*
 * Copyright (c) TIBCO Software Inc. 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.wsdl.ui.pickers;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.util.WsdlIndexerUtil;
import com.tibco.xpd.util.WsdlIndexerUtil.WsdlElementType;
import com.tibco.xpd.util.WsdlIndexerUtil.WsdlIndexerAttributes;
import com.tibco.xpd.wsdl.ui.internal.Messages;

/**
 * Operation picker that will list all port type and service (bound) operations.
 * 
 * @author njpatel
 * 
 */
public class OperationPicker extends WsdlElementPicker {

    private IndexerItem serviceItem;

    private IndexerItem portItem;

    private IndexerItem portTypeItem;

    private IndexerItem opItem;

    private final boolean inSpecialFolderOnly;

    private final boolean includeDerivedResources;

    private Comparator<IndexerItem> comparator;

    public enum WsdlType {
        ALL, BW, STANDARD;
    }

    /**
     * Operation picker that will list all port type and service (bound)
     * operations. This will include elements from WSDLs in the Services special
     * folder, including derived resources: same as calling
     * <code>OperationPicker(shell, type, true, true)</code>.
     * 
     * @see #OperationPicker(Shell, WsdlType, boolean, boolean)
     * 
     * @param shell
     *            parent shell
     * @param type
     *            WSDL type to include (i.e. BW, Standard or All)
     */
    public OperationPicker(Shell shell, WsdlType type) {
        this(shell, type, true, true);
    }

    /**
     * Operation picker that will list all port type and service (bound)
     * operations.
     * 
     * @see #OperationPicker(Shell, WsdlType)
     * 
     * @param shell
     *            parent shell
     * @param type
     *            WSDL type to include (i.e. BW, Standard or All)
     * @param inSpecialFolderOnly
     *            <code>true</code> to only included WSDLs from the Services
     *            special folder, <code>false</code> to include all WSDLs.
     * @param includeDerivedResources
     *            <code>true</code> to include WSDLs marked as derived,
     *            <code>false</code> to exclude derived resources.
     */
    public OperationPicker(Shell shell, WsdlType type,
            boolean inSpecialFolderOnly, boolean includeDerivedResources) {
        super(shell, new WsdlElementType[] {
                WsdlElementType.PORTTYPE_OPERATION,
                WsdlElementType.SERVICE_OPERATION }, inSpecialFolderOnly,
                includeDerivedResources);
        this.inSpecialFolderOnly = inSpecialFolderOnly;
        this.includeDerivedResources = includeDerivedResources;

        if (type != null && type != WsdlType.ALL) {
            // Need to filter on service type
            setFilter(new Filter(type));
        }

        setTitle(Messages.OperationPicker_title);
        setMessage(type != null && type == WsdlType.BW ? Messages.OperationPicker_bw_message
                : Messages.OperationPicker_message);
        setHelpAvailable(false);
    }

    /**
     * Get the service name.
     * 
     * @return name or <code>null</code> if the selected operation was from a
     *         port type.
     */
    public String getServiceName() {
        return serviceItem != null ? serviceItem.getName() : null;
    }

    /**
     * Get the port name.
     * 
     * @return name or <code>null</code> if the selected operation was from a
     *         port type.
     */
    public String getPortName() {
        return portItem != null ? portItem.getName() : null;
    }

    /**
     * Get the port type name
     * 
     * @return name or <code>null</code> if the selected operation was from a
     *         port binding.
     */
    public String getPortTypeName() {
        return portTypeItem != null ? portTypeItem.getName() : null;
    }

    /**
     * Get the operation name.
     * 
     * @return name
     */
    public String getOperationName() {
        return opItem != null ? opItem.getName() : null;
    }

    /**
     * Get the name of the project the selected operation comes from.
     * 
     * @return name
     */
    public String getProjectName() {
        return WsdlIndexerUtil.getProjectName(opItem);
    }

    /**
     * @return The URL of the WSDL file.
     */
    public String getWsdlUrl() {
        String url = null;
        if (opItem != null) {
            url = WsdlIndexerUtil.getWsdlUrl(opItem, true);
        }
        return url;
    }

    /**
     * Get the Services special folder relative path to the local wsdl file.
     * 
     * @return
     */
    public String getLocalFilePath() {
        String path = null;
        if (opItem != null) {
            IPath relativePath = WsdlIndexerUtil.getRelativePath(opItem, true);
            if (relativePath != null) {
                path = relativePath.toString();
            }
        }
        return path;
    }

    @Override
    protected Comparator<?> getItemsComparator() {
        if (comparator == null) {
            comparator = new Comparator<IndexerItem>() {
                public int compare(IndexerItem o1, IndexerItem o2) {
                    // Compare the names of the items
                    int value = o1.getName().compareTo(o2.getName());

                    /*
                     * If the names are the same then if Operation from Port
                     * Type then place it ahead of the binding Operation
                     */
                    if (value == 0) {
                        WsdlElementType o1Type = WsdlElementType.getType(o1);
                        if (WsdlElementType.PORTTYPE_OPERATION == o1Type) {
                            value = -1;
                        } else if (WsdlElementType.SERVICE_OPERATION == o1Type) {
                            value = 1;
                        }
                    }
                    return value;
                }
            };
        }
        return comparator;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void setResult(List newResult) {
        if (newResult != null && !newResult.isEmpty()) {
            if (newResult.get(0) instanceof IndexerItem) {
                opItem = (IndexerItem) newResult.get(0);

                if (WsdlElementType.PORTTYPE_OPERATION == WsdlElementType
                        .getType(opItem)) {
                    // Port type operation is selected
                    // Get the port type container
                    portTypeItem = WsdlIndexerUtil.getContainer(opItem);
                } else {
                    // Port operation is selected
                    // Get the Port container
                    portItem = WsdlIndexerUtil.getContainer(opItem);
                    if (portItem != null) {
                        // Get the Service container
                        serviceItem = WsdlIndexerUtil.getContainer(portItem);
                    }

                    // Now get the Port type
                    String portTypeUri =
                            portItem.get(WsdlIndexerAttributes.REFERED_PORTTYPE
                                    .toString());
                    if (portTypeUri != null) {
                        Collection<IndexerItem> items =
                                WsdlIndexerUtil.getIndexedItems(null,
                                        WsdlElementType.PORT_TYPE,
                                        portTypeUri,
                                        null,
                                        inSpecialFolderOnly,
                                        includeDerivedResources);
                        if (items != null && !items.isEmpty()) {
                            portTypeItem = items.iterator().next();
                        }
                    }
                }
            }
        }
    }

    /**
     * Operation picker filter to filter between types of WSDLs to include, ie
     * BW or Standard.
     * 
     * @author njpatel
     * 
     */
    private class Filter implements IWsdlElementPickerFilter {

        private final WsdlType type;

        private final Map<String, Boolean> bwMap;

        public Filter(WsdlType type) {
            this.type = type;
            bwMap = new HashMap<String, Boolean>();
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.wsdl.ui.pickers.WsdlElementPicker.IWsdlElementPickerFilter
         * #include(com.tibco.xpd.resources.indexer.IndexerItem)
         */
        public boolean include(IndexerItem item) {
            String path = WsdlIndexerUtil.getPath(item);
            if (path != null) {
                // Cache the result so that we don't constantly use the indexer
                // to get the value for a resource that has been checked already
                Boolean include = bwMap.get(path);
                if (include == null) {
                    /*
                     * XPD-1778: Allow Studio users to choose operations from BW
                     * WSDL (irrespective of binding type, SOAP/HTTP or
                     * SOAP/JMS)
                     */
                    // boolean isBW = WsdlIndexerUtil.isBW(item);
                    include =
                            (type == WsdlType.STANDARD)
                                    || (type == WsdlType.BW);
                    bwMap.put(path, include);
                }
                return include;
            }
            return false;
        }

    }
}
