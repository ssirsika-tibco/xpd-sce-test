/*
 * Copyright (c) TIBCO Software Inc. 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.wsdl.ui.pickers;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.ui.dialogs.FilteredMultiSelectionDialog;
import com.tibco.xpd.util.WsdlIndexerUtil;
import com.tibco.xpd.util.WsdlIndexerUtil.WsdlElementType;
import com.tibco.xpd.util.WsdlIndexerUtil.WsdlIndexerAttributes;
import com.tibco.xpd.wsdl.ui.ImageCache;
import com.tibco.xpd.wsdl.ui.WsdlUIPlugin;

/**
 * A picker to pick an element from an indexed WSDL.
 * 
 * @see WsdlIndexerUtil
 * 
 * @author njpatel
 * 
 */
public class WsdlElementPicker extends FilteredMultiSelectionDialog {

    private static final String DIALOG_SETTING = "wsdl.operation.picker"; //$NON-NLS-1$

    private Comparator<IndexerItem> comparator;

    private Set<IndexerItem> content;

    private Map<WsdlIndexerAttributes, String> attrFilter;

    private final WsdlElementType[] elementTypes;

    private final boolean inSpecialFolderOnly;

    private final boolean includeDerivedResources;

    private IWsdlElementPickerFilter filter;

    /**
     * The WSDL element picker content filter.
     */
    public interface IWsdlElementPickerFilter {
        boolean include(IndexerItem item);
    }

    /**
     * A WSDL element picker.
     * 
     * @see WsdlElementType
     * @see #setPickerFilter(Map)
     * 
     * @param shell
     *            parent shell
     * @param elementTypes
     *            types of elements to include in the picker
     * @param inSpecialFolderOnly
     *            <code>true</code> to include WSDLs from the Services special
     *            folder only, <code>false</code> if all WSDLs should be
     *            included
     * @param includeDerivedResources
     *            <code>true</code> to include derived WSDLs in the picker,
     *            <code>false</code> to exclude.
     */
    public WsdlElementPicker(Shell shell, WsdlElementType[] elementTypes,
            boolean inSpecialFolderOnly, boolean includeDerivedResources) {
        super(shell, false);
        this.elementTypes = elementTypes;
        this.inSpecialFolderOnly = inSpecialFolderOnly;
        this.includeDerivedResources = includeDerivedResources;
        setDetailsLabelProvider(new WsdlElementDetailsLabelProvider());
        WsdlElementPickerLabelProvider lProvider =
                new WsdlElementPickerLabelProvider();
        setListLabelProvider(lProvider);
        setListSelectionLabelDecorator(lProvider);
    }

    /**
     * Additional filtering can be provided based on attributes of the indexed
     * element.
     * 
     * @see WsdlIndexerAttributes
     * @see #setFilter(IWsdlElementPickerFilter)
     * 
     * @param pickerFilter
     */
    public void setFilter(Map<WsdlIndexerAttributes, String> pickerFilter) {
        this.attrFilter = pickerFilter;
    }

    /**
     * Additional filtering can be provided to exclude certain elements from the
     * picker.
     * 
     * @see #setFilter(IWsdlElementPickerFilter)
     * 
     * @param filter
     */
    public void setFilter(IWsdlElementPickerFilter filter) {
        this.filter = filter;
    }

    @Override
    protected Control createExtendedContentArea(Composite parent) {
        return null;
    }

    @Override
    protected ItemsFilter createFilter() {
        return new ShowAllWhenEmptyItemsFilter() {

            @Override
            public boolean isConsistentItem(Object item) {
                return true;
            }

            @Override
            public boolean matchItem(Object item) {
                if (item instanceof IndexerItem) {
                    String name = ((IndexerItem) item).getName();
                    if (name != null) {
                        return matches(name);
                    }
                }
                return false;
            }
        };
    }

    @Override
    protected void fillContentProvider(AbstractContentProvider contentProvider,
            ItemsFilter itemsFilter, IProgressMonitor progressMonitor)
            throws CoreException {

        if (content == null) {
            // Get items from the indexer
            content = new HashSet<IndexerItem>();
            if (elementTypes != null) {
                for (WsdlElementType type : elementTypes) {
                    content.addAll(WsdlIndexerUtil.getIndexedItems(null,
                            type,
                            null,
                            attrFilter,
                            inSpecialFolderOnly,
                            includeDerivedResources));
                }
            }
        }
        for (IndexerItem item : content) {
            if (filter == null || filter.include(item)) {
                contentProvider.add(item, itemsFilter);
            }
        }
    }

    @Override
    protected IDialogSettings getDialogSettings() {
        IDialogSettings settings =
                WsdlUIPlugin.getDefault().getDialogSettings()
                        .getSection(DIALOG_SETTING);

        if (settings == null) {
            settings =
                    WsdlUIPlugin.getDefault().getDialogSettings()
                            .addNewSection(DIALOG_SETTING);
        }

        return settings;
    }

    @Override
    public String getElementName(Object item) {
        String name = null;
        if (item instanceof IndexerItem) {
            name = ((IndexerItem) item).getName();
        }
        return name;
    }

    @Override
    protected Comparator<?> getItemsComparator() {
        if (comparator == null) {
            comparator = new Comparator<IndexerItem>() {
                public int compare(IndexerItem o1, IndexerItem o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            };
        }
        return comparator;
    }

    @Override
    protected IStatus validateItem(Object item) {
        // Assume valid as it's already in the picker
        return new Status(IStatus.OK, WsdlUIPlugin.PLUGIN_ID, IStatus.OK,
                "", null); //$NON-NLS-1$
    }

    /**
     * The details label provider for the WSDL element picker.
     * 
     * @author njpatel
     */
    private class WsdlElementDetailsLabelProvider extends LabelProvider {

        private final ImageCache imgCache;

        private final Image fileImg;

        public WsdlElementDetailsLabelProvider() {
            imgCache = new ImageCache();
            fileImg = imgCache.getImage(ImageCache.FILE);
        }

        @Override
        public String getText(Object element) {
            String text = null;
            if (element instanceof IndexerItem) {
                text = WsdlIndexerUtil.getPath((IndexerItem) element);
            }
            return text != null ? text : ""; //$NON-NLS-1$
        }

        @Override
        public Image getImage(Object element) {
            Image img = null;
            if (element instanceof IndexerItem) {
                img = fileImg;
            }
            return img;
        }

        @Override
        public void dispose() {
            imgCache.dispose();
            super.dispose();
        }
    }

    /**
     * The label provider for the WSDL element picker.
     * 
     * @author njpatel
     */
    private class WsdlElementPickerLabelProvider extends LabelProvider
            implements ILabelDecorator {

        private final ImageCache imgCache = new ImageCache();

        @Override
        public String getText(Object element) {
            String text = null;

            if (element instanceof IndexerItem) {
                IndexerItem item = (IndexerItem) element;
                text = item.getName();
                if (isDuplicateElement(item)) {
                    text = decorateText(text, element);
                }
            }

            return text != null ? text : ""; //$NON-NLS-1$
        }

        @Override
        public Image getImage(Object element) {
            Image img = null;

            if (element instanceof IndexerItem) {
                String type = ((IndexerItem) element).getType();
                if (type != null) {
                    WsdlElementType elementType = WsdlElementType.valueOf(type);
                    if (elementType != null) {
                        String imgPath = null;
                        switch (elementType) {
                        case PORTTYPE_OPERATION:
                            imgPath = ImageCache.OPERATION;
                            break;
                        case SERVICE_OPERATION:
                            imgPath = ImageCache.OPERATION_BINDING;
                            break;
                        case PORT:
                            imgPath = ImageCache.PORT;
                            break;
                        case PORT_TYPE:
                            imgPath = ImageCache.PORT_TYPE;
                            break;
                        case SERVICE:
                            imgPath = ImageCache.SERVICE;
                            break;
                        case WSDL:
                            imgPath = ImageCache.FILE;
                            break;
                        }

                        if (imgPath != null) {
                            img = imgCache.getImage(imgPath);
                        }
                    }
                }
            }

            return img;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.jface.viewers.ILabelDecorator#decorateImage(org.eclipse
         * .swt.graphics.Image, java.lang.Object)
         */
        public Image decorateImage(Image image, Object element) {
            return image;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.jface.viewers.ILabelDecorator#decorateText(java.lang.
         * String, java.lang.Object)
         */
        public String decorateText(String text, Object element) {
            if (text != null && text.length() > 0
                    && element instanceof IndexerItem) {
                String value =
                        ((IndexerItem) element)
                                .get(WsdlIndexerAttributes.QUALIFICATION
                                        .toString());
                if (value != null && value.length() > 0) {
                    text = String.format("%s - %s", ((IndexerItem) element) //$NON-NLS-1$
                            .getName(), value);
                }
            }
            return text;
        }

        @Override
        public void dispose() {
            imgCache.dispose();
            super.dispose();
        }
    }

    @Override
    protected void updateInitialSelection(Set<Object> selectedItems) {
        List<?> selections = getInitialElementSelections();
        Set<IndexerItem> _content = content;
        if (selections != null) {
            for (Object sel : selections) {
                if (sel instanceof IndexerItem) {
                    IndexerItem selIndex = (IndexerItem) sel;
                    for (IndexerItem item : _content) {
                        if (item.getURI() != null
                                && item.getURI().equals(selIndex.getURI())) {
                            selectedItems.add(item);
                            break;
                        }
                    }
                }
            }
        }
    }
}
