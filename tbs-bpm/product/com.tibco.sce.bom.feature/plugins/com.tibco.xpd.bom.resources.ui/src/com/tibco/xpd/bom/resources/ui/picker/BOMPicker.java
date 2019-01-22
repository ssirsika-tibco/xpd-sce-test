/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.resources.ui.picker;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.dialogs.FilteredItemsSelectionDialog;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.ui.internal.Messages;
import com.tibco.xpd.bom.resources.ui.internal.picker.BOMPickerProviderUtil;
import com.tibco.xpd.bom.resources.ui.picker.IBOMPickerProvider.BOMType;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.ui.dialogs.FilteredMultiSelectionDialog;

/**
 * BOM type picker. This will use the indexer to acquire (proxy) information
 * about relevant <code>EObject</code>s and will only resolve to the actual
 * <code>EObject</code> when a selection is made (by pressing OK in the
 * dialog).
 * <p>
 * Use {@link #addFilter(IFilter) addFilter} to filter the content of the picker
 * (keep in mind that the objects in the picker will be
 * <code>IBOMPickerProxyItem</code> objects and not <code>EObject</code>s).
 * Alternatively, the constructor {@link #BOMPicker(Shell, EObject[])} can be
 * used to provide a list of <code>EObject</code>s to exclude from the
 * picker.
 * </p>
 * <p>
 * The {{@link #getFirstResult() result} from this dialog will be a resolved
 * <code>EObject</code> of the selection.
 * </p>
 * 
 * @deprecated The{@link FilteredMultiSelectionDialog} subclasses should be
 *             used. This class might be actually refactored to use
 *             {@link FilteredMultiSelectionDialog} in the future.
 * 
 * @author njpatel
 * 
 */
@Deprecated
public class BOMPicker extends FilteredItemsSelectionDialog {

    /**
     * BOM Picker type
     * 
     * @author njpatel
     * 
     */
    public enum BOMPickerType {
        /**
         * Class picker
         */
        CLASS(Messages.BOMPicker_selectClass_title, "BOMPicker.class"), //$NON-NLS-1$ 
        /**
         * Primitive type picker
         */
        PRIMITIVE(Messages.BOMPicker_selectPrimType_title,
                "BOMPicker.primitive"), //$NON-NLS-1$ 
        /**
         * Class and Primitive type picker
         */
        CLASS_PRIMITIVE(Messages.BOMPicker_selectType_title,
                "BOMPicker.classPrimitive"); //$NON-NLS-1$

        private final String title;
        private final String dialogSettingId;
        private final String message = Messages.BOMPicker_dialog_message;

        BOMPickerType(String title, String dialogSettingId) {
            this.title = title;
            this.dialogSettingId = dialogSettingId;
        }
    }

    private static final String URI_TAG = "uri"; //$NON-NLS-1$
    private static final String NAME_TAG = "name"; //$NON-NLS-1$

    private final Set<IFilter> filters;
    private final BOMPickerType pickerType;
    private Comparator<IBOMPickerProxyItem> comparator;
    private final IBOMPickerProvider provider;
    private final BOMPickerLabelProvider labelProvider;

    public BOMPicker(Shell shell, BOMPickerType type) {
        super(shell);
        Assert.isNotNull(type, "BOM Picker type"); //$NON-NLS-1$
        this.pickerType = type;
        filters = new HashSet<IFilter>();
        provider = BOMPickerProviderUtil.getInstance();
        Assert.isNotNull(provider, "BOM Picker provider"); //$NON-NLS-1$

        // Set the selection history
        SelectionHistory history = getSelectionHistory();

        if (history != null) {
            setSelectionHistory(history);
        }

        setTitle(type.title);
        setMessage(type.message);

        // Set the label providers
        labelProvider = new BOMPickerLabelProvider();
        setListLabelProvider(labelProvider);
        setListSelectionLabelDecorator(labelProvider);
        setDetailsLabelProvider(new BOMPickerDetailsLabelProvider());

    }

    /**
     * Constructor. BOM Picker base class.
     * 
     * @param shell
     *                parent shell
     * @param exclude
     *                <code>EObject</code>s to exclude from the picker.
     * @param type
     *                type of picker required.
     */
    public BOMPicker(Shell shell, EObject[] exclude, BOMPickerType type) {
        this(shell, type);

        // Add filter to exclude the EObject(s) given
        if (exclude != null && exclude.length > 0) {
            final Set<URI> exclusionList = new HashSet<URI>();

            for (EObject eo : exclude) {
                exclusionList.add(EcoreUtil.getURI(eo));
            }

            addFilter(new IFilter() {
                public boolean select(Object toTest) {
                    boolean select = true;

                    if (toTest instanceof IBOMPickerProxyItem) {
                        URI uri = ((IBOMPickerProxyItem) toTest).getURI();

                        select = !(uri != null && exclusionList.contains(uri));
                    }

                    return select;
                }
            });
        }
    }

    /**
     * Add IFilter to filter the content of the picker. Note that the object
     * passed to the filter will be of type <code>IBOMPickerProxyItem</code>.
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
        return new ItemsFilter() {
            @Override
            public boolean isConsistentItem(Object item) {
                boolean isConsistent = false;

                if (item instanceof IBOMPickerProxyItem) {
                    isConsistent = include(item);
                }

                return isConsistent;
            }

            @Override
            public boolean matchItem(Object item) {
                boolean matches = include(item);

                if (matches) {
                    if (item instanceof IBOMPickerProxyItem) {
                        matches = matches(((IBOMPickerProxyItem) item)
                                .getName());
                    }
                }

                return matches;
            }

            /*
             * (non-Javadoc)
             * 
             * @see org.eclipse.ui.dialogs.FilteredItemsSelectionDialog.ItemsFilter#getPattern()
             */
            @Override
            public String getPattern() {
                if ("".equals(patternMatcher.getPattern())) { //$NON-NLS-1$
                    return "?"; //$NON-NLS-1$
                } else {
                    return super.getPattern();
                }
            }
        };
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
     * Get the picker content.
     * 
     * @param monitor
     * @return
     */
    protected Object[] getContent(IProgressMonitor monitor) {
        Object[] items = null;

        if (provider != null) {
            Set<IBOMPickerProxyItem> itemsList = new HashSet<IBOMPickerProxyItem>();
            int work = 1;

            // Work out number of work units for the progress monitor
            if (pickerType == BOMPickerType.PRIMITIVE) {
                work = 2;
            } else if (pickerType == BOMPickerType.CLASS_PRIMITIVE) {
                work = 3;
            }

            monitor.beginTask(
                    Messages.BOMPicker_gettingItems_monitor_shortdesc, work);

            // Get all classes
            if (pickerType == BOMPickerType.CLASS
                    || pickerType == BOMPickerType.CLASS_PRIMITIVE) {
                SubProgressMonitor subMon = new SubProgressMonitor(monitor, 1);
                itemsList.addAll(Arrays.asList(provider.getContent(subMon,
                        BOMType.CLASS)));
                monitor.worked(1);
            }

            // Get all base and primitive types
            if (pickerType == BOMPickerType.PRIMITIVE
                    || pickerType == BOMPickerType.CLASS_PRIMITIVE) {
                SubProgressMonitor subMon = new SubProgressMonitor(monitor, 1);
                itemsList.addAll(Arrays.asList(provider.getContent(subMon,
                        BOMType.BASE_PRIMITIVE)));
                monitor.worked(1);
                subMon = new SubProgressMonitor(monitor, 1);
                itemsList.addAll(Arrays.asList(provider.getContent(subMon,
                        BOMType.PRIMITIVE_TYPE)));
                monitor.worked(1);
            }

            items = itemsList
                    .toArray(new IBOMPickerProxyItem[itemsList.size()]);
        }
        monitor.done();

        return items != null ? items : new IBOMPickerProxyItem[0];
    }

    @Override
    protected IDialogSettings getDialogSettings() {
        IDialogSettings settings = Activator.getDefault().getDialogSettings()
                .getSection(pickerType.dialogSettingId);

        if (settings == null) {
            settings = Activator.getDefault().getDialogSettings()
                    .addNewSection(pickerType.dialogSettingId);
        }

        return settings;
    }

    @Override
    public String getElementName(Object item) {
        String name = null;

        if (item instanceof IBOMPickerProxyItem) {
            name = ((IBOMPickerProxyItem) item).getName();
        }

        return name;
    }

    @Override
    public Object[] getResult() {
        /*
         * Resolve the proxy item to it's EObject
         */
        Object[] result = super.getResult();
        Set<EObject> eObjects = new HashSet<EObject>();
        EditingDomain ed = getEditingDomain();

        Assert.isNotNull(ed, "Editing domain"); //$NON-NLS-1$

        if (result != null) {
            for (Object obj : result) {
                if (obj instanceof IBOMPickerProxyItem) {
                    IBOMPickerProxyItem item = (IBOMPickerProxyItem) obj;
                    URI uri = item.getURI();

                    if (uri != null) {
                        EObject eo = ed.getResourceSet().getEObject(uri, true);

                        if (eo != null) {
                            eObjects.add(eo);
                        }
                    }
                }
            }
        }

        return eObjects.toArray();
    }

    /**
     * Get the editing domain. Default implementation returns the shared single
     * transactional editing domain.
     * 
     * @return Editing domain.
     */
    protected EditingDomain getEditingDomain() {
        return XpdResourcesPlugin.getDefault().getEditingDomain();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Comparator getItemsComparator() {
        if (comparator == null) {
            comparator = new Comparator<IBOMPickerProxyItem>() {

                public int compare(IBOMPickerProxyItem o1,
                        IBOMPickerProxyItem o2) {
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
    protected IStatus validateItem(Object item) {
        // Assume valid as it's already in the picker
        return new Status(IStatus.OK, Activator.PLUGIN_ID, IStatus.OK, "", null); //$NON-NLS-1$
    }

    /**
     * Check whether the given item should be included in the picker. The added
     * set of filters will be used to determine whether the item should be
     * included, if no filters are available then all items will be added.
     * 
     * @param item
     *                item to test
     * @return <code>true</code> if it should be included in the picker,
     *         <code>false</code> otherwise.
     */
    protected boolean include(Object item) {
        boolean include = true;

        for (IFilter filter : filters) {
            include = filter.select(item);

            if (!include) {
                // Filtered out
                break;
            }
        }

        return include;
    }

    /**
     * Get the qualification of the given item
     * 
     * @param item
     * @return qualification of the event, empty string of no qualification
     *         found.
     */
    protected String getQualification(IBOMPickerProxyItem item) {
        String qualification = ""; //$NON-NLS-1$

        if (item != null) {
            String qualifiedName = item.getQualifiedName();
            String name = item.getName();

            if (qualifiedName != name) {
                qualification = qualifiedName.substring(0, qualifiedName
                        .length()
                        - name.length()
                        - BOMWorkingCopy.JAVA_PACKAGE_SEPARATOR.length());
            }
        }

        return qualification;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.dialogs.FilteredItemsSelectionDialog#getSelectionHistory()
     */
    @Override
    public SelectionHistory getSelectionHistory() {
        return new SelectionHistory() {
            @Override
            protected Object restoreItemFromMemento(IMemento memento) {
                IBOMPickerProxyItem item = null;
                String uriStr = memento.getString(URI_TAG);
                String name = memento.getString(NAME_TAG);

                if (uriStr != null && name != null) {

                    if (provider != null) {
                        item = provider.getItem(uriStr, name);
                    }
                }
                return item;
            }

            @Override
            protected void storeItemToMemento(Object item, IMemento memento) {
                if (item instanceof IBOMPickerProxyItem) {
                    IBOMPickerProxyItem proxyItem = (IBOMPickerProxyItem) item;
                    URI uri = proxyItem.getURI();
                    String name = proxyItem.getQualifiedName();
                    if (uri != null && name != null) {
                        memento.putString(URI_TAG, uri.toString());
                        memento.putString(NAME_TAG, name);
                    }
                }
            }
        };
    }

    private class BOMPickerLabelProvider extends LabelProvider implements
            ILabelDecorator {

        @Override
        public String getText(Object element) {
            String text = null;

            if (element instanceof IBOMPickerProxyItem) {
                text = ((IBOMPickerProxyItem) element).getName();

                // If duplicate item then show the context
                if (isDuplicateElement(element)) {
                    text = decorateText(text, element);
                }
            }

            return text != null ? text : ""; //$NON-NLS-1$
        }

        @Override
        public Image getImage(Object element) {
            Image img = null;

            if (element instanceof IBOMPickerProxyItem) {
                img = ((IBOMPickerProxyItem) element).getImage();
            }

            return img;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.jface.viewers.ILabelDecorator#decorateImage(org.eclipse.swt.graphics.Image,
         *      java.lang.Object)
         */
        public Image decorateImage(Image image, Object element) {
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.jface.viewers.ILabelDecorator#decorateText(java.lang.String,
         *      java.lang.Object)
         */
        public String decorateText(String text, Object element) {
            if (element instanceof IBOMPickerProxyItem) {
                IBOMPickerProxyItem item = (IBOMPickerProxyItem) element;

                text = String.format("%s - %s", item.getName(), //$NON-NLS-1$
                        getQualification(item));
            }

            return text;
        }
    }

    private class BOMPickerDetailsLabelProvider extends LabelProvider {
        private final WorkbenchLabelProvider wbLabelProvider = new WorkbenchLabelProvider();

        @Override
        public String getText(Object element) {
            String text = null;

            if (element instanceof IBOMPickerProxyItem) {
                URI uri = ((IBOMPickerProxyItem) element).getURI();

                if (uri != null) {
                    uri = uri.trimFragment();
                    // If this is not a platform URI then just display the
                    // qualification
                    if (uri.isPlatformResource()) {
                        text = uri.toPlatformString(true);
                    } else {
                        text = getQualification((IBOMPickerProxyItem) element);
                    }
                }
            }

            return text != null ? text : ""; //$NON-NLS-1$
        }

        @Override
        public Image getImage(Object element) {
            Image img = null;

            if (element instanceof IBOMPickerProxyItem) {
                URI uri = ((IBOMPickerProxyItem) element).getURI();

                if (uri != null) {
                    uri = uri.trimFragment();
                    String platformString = uri.toPlatformString(true);

                    if (platformString != null) {
                        IResource resource = ResourcesPlugin.getWorkspace()
                                .getRoot().findMember(platformString);

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
