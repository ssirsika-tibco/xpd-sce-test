/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.pickers;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import com.tibco.xpd.analyst.resources.xpdl2.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.providers.IProcessPickerProvider;
import com.tibco.xpd.analyst.resources.xpdl2.providers.IProcessPickerProvider.ProcessType;
import com.tibco.xpd.analyst.resources.xpdl2.providers.ProcessFilterPickerProviderHelper;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.util.CapabilityUtil;
import com.tibco.xpd.xpdl2.NamedElement;

/**
 * Process type picker. This will use the indexer to acquire (proxy) information
 * about relevant <code>EObject</code>s and will only resolve to the actual
 * <code>EObject</code> when a selection is made (by pressing OK in the dialog).
 * <p>
 * Use {@link #addFilter(IFilter) addFilter} to filter the content of the picker
 * (keep in mind that the objects in the picker will be
 * <code>IProcessPickerProxyItem</code> objects and not <code>EObject</code>s).
 * Alternatively, the constructor {@link #ProcessPicker(Shell, EObject[])} can
 * be used to provide a list of <code>EObject</code>s to exclude from the
 * picker.
 * </p>
 * <p>
 * The {{@link #getFirstResult() result} from this dialog will be a resolved
 * <code>EObject</code> of the selection.
 * </p>
 * 
 * @author Miguel Torres
 * 
 */
public class ProcessFilterPicker extends BaseFilterPicker {

    /**
     * Process Picker type
     * 
     * @author Miguel Torres
     * 
     */
    public enum ProcessPickerType {
        /**
         * Process picker
         */
        PROCESS(Messages.ProcessFilterPicker_ProcessPicker_SelectProcess_Title,
                "ProcessPicker.process"), //$NON-NLS-1$
        /**
         * Process interface type picker
         */
        PROCESSINTERFACE(
                Messages.ProcessFilterPicker_ProcessPicker_SelectProcessInterface_Title,
                "ProcessPicker.processInterface"), //$NON-NLS-1$

        SERVICEPROCESSINTERFACE(
                Messages.ProcessFilterPicker_ProcessPicker_SelectServiceProcessInterface_Title,
                "ProcessPicker.serviceProcessInterface"), //$NON-NLS-1$
        /**
         * Process and Process interface picker
         */
        PROCESS_PROCESSINTERFACE(
                Messages.ProcessFilterPicker_ProcessPicker_SelectProcessOrPInterface_Title,
                "ProcessPicker.processProcessInterface"), //$NON-NLS-1$ 

        /**
         * Pageflow picker
         */
        PAGEFLOW(Messages.ProcessFilterPicker_SelectPAgeflow_title,
                "ProcessPicker.pageflow"), //$NON-NLS-1$

        /**
         * Pageflow or process interface picker
         */
        PAGEFLOW_PROCESSINTERFACE(
                Messages.ProcessFilterPicker_SelectPageflowOrProcessIfc_title,
                "ProcessPicker.pageflowProcessInterface"), //$NON-NLS-1$

        /** 
         * <p>
         * Business/Service Process type picker.
         */
        BUSINESS_OR_SERVICE_PROCESS(
                Messages.ProcessFilterPicker_SelectBusinessOrServiceProcess_title,
                "ProcessPicker.businessProcessOrServiceProcess"), //$NON-NLS-1$

        /**
         * All process types picker.
         */
        ALL_PROCESS_TYPES(Messages.ProcessFilterPicker_SelectAnyProcess_title,
                "ProcessPicker.allProcessTypes"); //$NON-NLS-1$

        private final String title;

        private final String dialogSettingId;

        private final String message =
                Messages.ProcessFilterPicker_Dialog_Display_Message;

        ProcessPickerType(String title, String dialogSettingId) {
            this.title = title;
            this.dialogSettingId = dialogSettingId;
        }
    }

    private static final String URI_TAG = "uri"; //$NON-NLS-1$

    private static final String NAME_TAG = "name"; //$NON-NLS-1$

    private final Set<IFilter> filters;

    private final ProcessPickerType pickerType;

    private Comparator<IProcessPickerProxyItem> comparator;

    private final IProcessPickerProvider provider;

    private final ProcessPickerLabelProvider labelProvider;

    public ProcessFilterPicker(Shell shell, ProcessPickerType type,
            boolean multi) {
        super(shell, multi);
        Assert.isNotNull(type, "Process Picker type"); //$NON-NLS-1$
        this.pickerType = type;
        filters = new HashSet<IFilter>();
        provider = ProcessFilterPickerProviderHelper.getInstance();
        Assert.isNotNull(provider, "Process Picker provider"); //$NON-NLS-1$

        // Set the selection history
        SelectionHistory history = getSelectionHistory();

        if (history != null) {
            setSelectionHistory(history);
        }

        setTitle(type.title);
        setMessage(type.message);

        // Set the label providers
        labelProvider = new ProcessPickerLabelProvider();
        setListLabelProvider(labelProvider);
        setListSelectionLabelDecorator(labelProvider);
        setDetailsLabelProvider(new ProcessPickerDetailsLabelProvider());

    }

    /**
     * Constructor. Process Picker base class.
     * 
     * @param shell
     *            parent shell
     * @param exclude
     *            <code>EObject</code>s to exclude from the picker.
     * @param type
     *            type of picker required.
     */
    public ProcessFilterPicker(Shell shell, EObject[] exclude,
            ProcessPickerType type, boolean multi) {
        this(shell, type, multi);

        // Add filter to exclude the EObject(s) given
        if (exclude != null && exclude.length > 0) {
            final Set<URI> exclusionList = new HashSet<URI>();

            for (EObject eo : exclude) {
                exclusionList.add(EcoreUtil.getURI(eo));
            }

            addFilter(new IFilter() {
                @Override
                public boolean select(Object toTest) {
                    boolean select = true;

                    if (toTest instanceof IProcessPickerProxyItem) {
                        URI uri = ((IProcessPickerProxyItem) toTest).getURI();

                        select = !(uri != null && exclusionList.contains(uri));
                    }

                    return select;
                }
            });
        }
    }

    @Override
    protected void fillContentProvider(AbstractContentProvider contentProvider,
            ItemsFilter itemsFilter, IProgressMonitor progressMonitor)
            throws CoreException {

        Object[] content = getContent(progressMonitor);

        if (content != null) {
            for (Object obj : content) {
                contentProvider.add(obj, itemsFilter);
            }
        }
    }

    /**
     * Add IFilter to filter the content of the picker. Note that the object
     * passed to the filter will be of type <code>IProcessPickerProxyItem</code>
     * .
     * 
     * @param filter
     */
    public void addFilter(IFilter filter) {
        if (filter != null) {
            filters.add(filter);
        }
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
                boolean isConsistent = false;

                if (item instanceof IProcessPickerProxyItem) {
                    isConsistent = include(item);
                }

                return isConsistent;
            }

            @Override
            public boolean matchItem(Object item) {
                boolean matches = include(item);

                if (matches) {
                    if (item instanceof IProcessPickerProxyItem) {
                        String text = labelProvider.getText(item);
                        matches = matches(text);
                    }
                }

                return matches;
            }
        };
    }

    /**
     * Get the picker content.
     * 
     * @param monitor
     * @return
     */
    protected Object[] getContent(IProgressMonitor monitor) {
        Object[] items = null;

        if (provider != null) {
            Set<IProcessPickerProxyItem> itemsList =
                    new HashSet<IProcessPickerProxyItem>();

            if (ProcessPickerType.PROCESS.equals(pickerType)) {

                itemsList.addAll(Arrays.asList(provider.getContent(monitor,
                        ProcessType.PROCESS)));
            } else if (ProcessPickerType.PROCESSINTERFACE.equals(pickerType)) {

                itemsList.addAll(Arrays.asList(provider.getContent(monitor,
                        ProcessType.PROCESSINTERFACE)));
            } else if (ProcessPickerType.SERVICEPROCESSINTERFACE
                    .equals(pickerType)) {

                itemsList.addAll(Arrays.asList(provider.getContent(monitor,
                        ProcessType.SERVICEPROCESSINTERFACE)));
            } else if (pickerType == ProcessPickerType.PROCESS_PROCESSINTERFACE) {

                itemsList.addAll(Arrays.asList(provider.getContent(monitor,
                        ProcessType.PROCESS_PROCESSINTERFACE)));
            } else if (pickerType == ProcessPickerType.PAGEFLOW) {

                itemsList.addAll(Arrays.asList(provider.getContent(monitor,
                        ProcessType.PAGEFLOW)));
            } else if (pickerType == ProcessPickerType.PAGEFLOW_PROCESSINTERFACE) {

                itemsList.addAll(Arrays.asList(provider.getContent(monitor,
                        ProcessType.PAGEFLOW_PROCESSINTERFACE)));
            } else if (pickerType == ProcessPickerType.BUSINESS_OR_SERVICE_PROCESS) {

                itemsList.addAll(Arrays.asList(provider.getContent(monitor,
                        ProcessType.PROCESS)));
                itemsList.addAll(Arrays.asList(provider.getContent(monitor,
                        ProcessType.PROCESSINTERFACE)));               
                itemsList.addAll(Arrays.asList(provider.getContent(monitor,
                        ProcessType.SERVICEPROCESS)));                
                itemsList.addAll(Arrays.asList(provider.getContent(monitor,
                        ProcessType.SERVICEPROCESSINTERFACE)));
            } else if (pickerType == ProcessPickerType.ALL_PROCESS_TYPES) {

                itemsList.addAll(Arrays.asList(provider.getContent(monitor,
                        ProcessType.PROCESS)));
                itemsList.addAll(Arrays.asList(provider.getContent(monitor,
                        ProcessType.PROCESSINTERFACE)));
                itemsList.addAll(Arrays.asList(provider.getContent(monitor,
                        ProcessType.PAGEFLOW)));
                itemsList.addAll(Arrays.asList(provider.getContent(monitor,
                        ProcessType.SERVICEPROCESSINTERFACE)));
                itemsList.addAll(Arrays.asList(provider.getContent(monitor,
                        ProcessType.SERVICEPROCESS)));               
            }

            items =
                    itemsList.toArray(new IProcessPickerProxyItem[itemsList
                            .size()]);
        }

        return items != null ? items : new IProcessPickerProxyItem[0];
    }

    @Override
    protected IDialogSettings getDialogSettings() {
        IDialogSettings settings =
                Xpdl2ResourcesPlugin.getDefault().getDialogSettings()
                        .getSection(pickerType.dialogSettingId);

        if (settings == null) {
            settings =
                    Xpdl2ResourcesPlugin.getDefault().getDialogSettings()
                            .addNewSection(pickerType.dialogSettingId);
        }

        return settings;
    }

    @Override
    public String getElementName(Object item) {
        return labelProvider != null ? labelProvider.getText(item) : ""; //$NON-NLS-1$
    }

    @Override
    public Object[] getResult() {
        /*
         * Resolve the proxy item to it's EObject
         */
        Object[] result = super.getResult();
        Set<EObject> eObjects = new HashSet<EObject>();
        if (result != null) {
            for (Object obj : result) {
                if (obj instanceof IProcessPickerProxyItem) {
                    IProcessPickerProxyItem item =
                            (IProcessPickerProxyItem) obj;
                    URI uri = item.getURI();

                    if (uri != null) {
                        EObject eo = ProcessUIUtil.getEObjectFrom(uri);
                        if (eo != null) {
                            eObjects.add(eo);
                        }
                    }
                }
            }
        }

        return eObjects.toArray();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Comparator getItemsComparator() {
        if (comparator == null) {
            comparator = new Comparator<IProcessPickerProxyItem>() {

                @Override
                public int compare(IProcessPickerProxyItem o1,
                        IProcessPickerProxyItem o2) {
                    int ret = 0;
                    if (o1 == o2) {
                        ret = 0;
                    } else {
                        ret = (o1.getName().compareTo(o2.getName()));
                    }
                    return ret;
                }

            };
        }
        return comparator;
    }

    @Override
    protected Set<IFilter> getFilters() {
        return filters;
    }

    /**
     * Get the qualification of the given item
     * 
     * @param item
     * @return qualification of the event, empty string of no qualification
     *         found.
     */
    protected String getQualification(IProcessPickerProxyItem item) {
        String qualification = ""; //$NON-NLS-1$

        if (item != null) {
            String qualifiedName = item.getQualifiedName();
            String name = item.getName();

            if (qualifiedName != name) {
                qualification =
                        qualifiedName
                                .substring(0,
                                        qualifiedName.length()
                                                - name.length()
                                                - ProcessUIUtil.PROCESS_PACKAGE_SEPARATOR
                                                        .length());
            }
        }

        return qualification;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.dialogs.FilteredItemsSelectionDialog#getSelectionHistory()
     */
    @Override
    public SelectionHistory getSelectionHistory() {
        /*
         * return new SelectionHistory() {
         * 
         * @Override protected Object restoreItemFromMemento(IMemento memento) {
         * IProcessPickerProxyItem item = null; String uriStr =
         * memento.getString(URI_TAG); String name =
         * memento.getString(NAME_TAG);
         * 
         * if (uriStr != null && name != null) { if (provider != null) { item =
         * provider.getItem(uriStr, name); } } return item; }
         * 
         * @Override protected void storeItemToMemento(Object item, IMemento
         * memento) { if (item instanceof IProcessPickerProxyItem) {
         * IProcessPickerProxyItem proxyItem = (IProcessPickerProxyItem) item;
         * URI uri = proxyItem.getURI(); String name =
         * proxyItem.getQualifiedName(); if (uri != null && name != null) {
         * memento.putString(URI_TAG, uri.toString());
         * memento.putString(NAME_TAG, name); } } } };
         */
        return null;
    }

    private class ProcessPickerLabelProvider extends LabelProvider implements
            ILabelDecorator {

        @Override
        public String getText(Object element) {
            String text = null;

            if (element instanceof IProcessPickerProxyItem) {
                IProcessPickerProxyItem processPickerProxyItem =
                        (IProcessPickerProxyItem) element;
                if (CapabilityUtil.isDeveloperActivityEnabled()) {
                    String displayName =
                            processPickerProxyItem.getDisplayName();
                    if (displayName == null || displayName.length() == 0) {
                        text = processPickerProxyItem.getName();
                    } else {
                        text =
                                String.format("%1$s (%2$s)", processPickerProxyItem.getDisplayName(), processPickerProxyItem.getName()); //$NON-NLS-1$
                    }
                } else {
                    text = processPickerProxyItem.getDisplayName();
                }

                // If duplicate item then show the context
                if (isDuplicateElement(element)) {
                    text = decorateText(text, element);
                }
            } else if (element instanceof NamedElement) {
                text = WorkingCopyUtil.getText((NamedElement) element);
            }

            return text != null ? text : ""; //$NON-NLS-1$
        }

        @Override
        public Image getImage(Object element) {
            Image img = null;

            if (element instanceof IProcessPickerProxyItem) {
                img = ((IProcessPickerProxyItem) element).getImage();
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
        @Override
        public Image decorateImage(Image image, Object element) {
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.jface.viewers.ILabelDecorator#decorateText(java.lang.
         * String, java.lang.Object)
         */
        @Override
        public String decorateText(String text, Object element) {
            if (element instanceof IProcessPickerProxyItem) {
                IProcessPickerProxyItem item =
                        (IProcessPickerProxyItem) element;
                text = String.format("%1$s - %2$s", text, //$NON-NLS-1$
                        getQualification(item));
            }
            return text;
        }
    }

    private class ProcessPickerDetailsLabelProvider extends LabelProvider {
        private final WorkbenchLabelProvider wbLabelProvider =
                new WorkbenchLabelProvider();

        @Override
        public String getText(Object element) {
            String text = null;

            if (element instanceof IProcessPickerProxyItem) {
                URI uri = ((IProcessPickerProxyItem) element).getURI();

                if (uri != null) {
                    uri = uri.trimFragment();
                    if (uri != null) {
                        if (uri.isPlatform()) {
                            text = uri.toPlatformString(true);
                        } else if (uri.isFile()) {
                            text = uri.toFileString();
                        } else {
                            text = uri.toString();
                        }
                    }
                }
            }

            return text != null ? text : ""; //$NON-NLS-1$
        }

        @Override
        public Image getImage(Object element) {
            Image img = null;

            if (element instanceof IProcessPickerProxyItem) {
                URI uri = ((IProcessPickerProxyItem) element).getURI();

                if (uri != null) {
                    uri = uri.trimFragment();
                    String path = null;
                    if (uri != null) {
                        if (uri.isPlatform()) {
                            path = uri.toPlatformString(true);
                        } else if (uri.isFile()) {
                            path = uri.toFileString();
                        } else {
                            path = uri.toString();
                        }
                    }
                    if (path != null) {
                        IResource resource =
                                ResourcesPlugin.getWorkspace().getRoot()
                                        .findMember(path);

                        if (resource != null) {
                            img = wbLabelProvider.getImage(resource);
                        }
                    }
                }
            }
            return img;
        }

        @Override
        public void dispose() {
            wbLabelProvider.dispose();
            super.dispose();
        }
    }

}
