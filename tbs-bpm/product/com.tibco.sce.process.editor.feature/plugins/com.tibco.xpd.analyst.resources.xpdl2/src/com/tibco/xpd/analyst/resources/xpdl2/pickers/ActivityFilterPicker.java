/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.pickers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IResource;
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
import com.tibco.xpd.analyst.resources.xpdl2.providers.ActivityFilterPickerProviderHelper;
import com.tibco.xpd.analyst.resources.xpdl2.providers.IActivityPickerProvider;
import com.tibco.xpd.analyst.resources.xpdl2.providers.IActivityPickerProvider.ActivityType;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Activity picker. This will use the content provider to acquire (proxy)
 * information about relevant <code>EObject</code>s and will resolve to the
 * actual <code>EObject</code> when a selection is made (by pressing OK in the
 * dialog), the proxy item contains the actual EObject.
 * <p>
 * Use {@link #addFilter(IFilter) addFilter} to filter the content of the
 * picker. Alternatively, the constructor
 * {@link #ActivityPicker(Shell, EObject[])} can be used to provide a list of
 * <code>EObject</code>s to exclude from the picker.
 * </p>
 * <p>
 * The {{@link #getFirstResult() result} from this dialog will be a resolved
 * <code>EObject</code> of the selection.
 * </p>
 * 
 * @author Miguel Torres
 * 
 */
public class ActivityFilterPicker extends BaseFilterPicker {

    /**
     * Activity Picker type
     * 
     * @author Miguel Torres
     * 
     */
    public enum ActivityPickerType {
        /**
         * Activity field picker
         */
        ACTIVITY(
                Messages.ActivityFilterPicker_ActivityPicker_SelectActivity_Title,
                "ActivityPicker.activity"); //$NON-NLS-1$

        private final String title;

        private final String dialogSettingId;

        private final String message =
                Messages.ActivityFilterPicker_Dialog_Display_Message;

        ActivityPickerType(String title, String dialogSettingId) {
            this.title = title;
            this.dialogSettingId = dialogSettingId;
        }
    }

    private static final String URI_TAG = "uri"; //$NON-NLS-1$

    private static final String NAME_TAG = "name"; //$NON-NLS-1$

    private final Set<IFilter> filters;

    private final ActivityPickerType pickerType;

    private Comparator<IActivityPickerProxyItem> comparator;

    private final IActivityPickerProvider provider;

    private final ActivityPickerLabelProvider labelProvider;

    private Object[] content = null;

    public ActivityFilterPicker(Shell shell, ActivityPickerType type,
            boolean multi) {
        super(shell, multi);
        Assert.isNotNull(type, "Activity Picker type"); //$NON-NLS-1$
        this.pickerType = type;
        filters = new HashSet<IFilter>();
        provider = ActivityFilterPickerProviderHelper.getInstance();
        Assert.isNotNull(provider, "Activity Picker provider"); //$NON-NLS-1$

        // Set the selection history
        SelectionHistory history = getSelectionHistory();

        if (history != null) {
            setSelectionHistory(history);
        }

        setTitle(type.title);
        setMessage(type.message);

        // Set the label providers
        labelProvider = new ActivityPickerLabelProvider();
        setListLabelProvider(labelProvider);
        setListSelectionLabelDecorator(labelProvider);
        setDetailsLabelProvider(new ActivityPickerDetailsLabelProvider());

    }

    /**
     * Constructor. Activity Picker base class.
     * 
     * @param shell
     *            parent shell
     * @param exclude
     *            <code>EObject</code>s to exclude from the picker.
     * @param type
     *            type of picker required.
     */
    public ActivityFilterPicker(Shell shell, EObject[] exclude,
            ActivityPickerType type, boolean multi) {
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

                    if (toTest instanceof IActivityPickerProxyItem) {
                        URI uri = ((IActivityPickerProxyItem) toTest).getURI();

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

        Object[] content = getContent(getScope());
        if (content != null) {
            for (Object obj : content) {
                contentProvider.add(obj, itemsFilter);
            }
        }
    }

    /**
     * Add IFilter to filter the content of the picker. Note that the object
     * passed to the filter will be of type
     * <code>IActivityPickerProxyItem</code>.
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

                if (item instanceof IActivityPickerProxyItem) {
                    isConsistent = include(item);
                }

                return isConsistent;
            }

            @Override
            public boolean matchItem(Object item) {
                boolean matches = include(item);

                if (matches) {
                    if (item instanceof IActivityPickerProxyItem) {
                        matches =
                                matches(((IActivityPickerProxyItem) item)
                                        .getName());
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
    protected Object[] getContent(EObject scope) {
        if (content == null) {
            Set<IActivityPickerProxyItem> itemsList =
                    new HashSet<IActivityPickerProxyItem>();
            if (provider != null) {

                // Get all data fields
                if (pickerType == ActivityPickerType.ACTIVITY) {
                    itemsList.addAll(Arrays.asList(provider
                            .getContent(ActivityType.ACTIVITY, scope)));
                }

            }
            itemsList.addAll(getExtraItems());

            content =
                    itemsList.toArray(new IActivityPickerProxyItem[itemsList
                            .size()]);
            content =
                    content != null ? content : new IActivityPickerProxyItem[0];
        }
        return content;
    }

    protected Set<IActivityPickerProxyItem> getExtraItems() {
        return Collections.EMPTY_SET;
    }

    protected IActivityPickerProxyItem getExtraItem(String name) {
        return null;
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
        Set<Object> objects = new HashSet<Object>();
        if (result != null) {
            for (Object obj : result) {
                if (obj instanceof IActivityPickerProxyItem) {
                    IActivityPickerProxyItem item =
                            (IActivityPickerProxyItem) obj;
                    Object eo = item.getItem();
                    if (eo != null) {
                        objects.add(eo);
                    }
                }
            }
        }
        return objects.toArray();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Comparator getItemsComparator() {
        if (comparator == null) {
            comparator = new Comparator<IActivityPickerProxyItem>() {

                @Override
                public int compare(IActivityPickerProxyItem o1,
                        IActivityPickerProxyItem o2) {
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
    protected String getQualification(IActivityPickerProxyItem item) {
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

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.pickers.BaseFilterPicker#setInitialSelections(java.lang.Object[])
     * 
     * @param selectedElements
     */
    @Override
    public void setInitialSelections(Object[] selectedElements) {
        Set<IActivityPickerProxyItem> pickerProxyItems =
                new HashSet<IActivityPickerProxyItem>();
        if (selectedElements != null && getScope() != null) {
            List<EObject> elementList = new ArrayList<EObject>();
            for (Object obj : selectedElements) {
                if (obj instanceof EObject) {
                    elementList.add((EObject) obj);
                } else if (obj instanceof IActivityPickerProxyItem) {
                    pickerProxyItems.add((IActivityPickerProxyItem) obj);
                }
            }
            pickerProxyItems.addAll(ActivityFilterPickerProviderHelper
                    .getActivityIndexerItemList(elementList, getScope()));
        }
        super.setInitialSelections(pickerProxyItems.toArray());
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.pickers.BaseFilterPicker#setInitialElementSelections(java.util.List)
     * 
     * @param selectedElements
     */
    @Override
    public void setInitialElementSelections(List selectedElements) {
        Set<IActivityPickerProxyItem> pickerProxyItems =
                new HashSet<IActivityPickerProxyItem>();
        if (selectedElements != null && getScope() != null) {
            List<EObject> elementList = new ArrayList<EObject>();
            for (Object obj : selectedElements) {
                if (obj instanceof EObject) {
                    elementList.add((EObject) obj);
                } else if (obj instanceof IActivityPickerProxyItem) {
                    pickerProxyItems.add((IActivityPickerProxyItem) obj);
                }
            }
            pickerProxyItems.addAll(ActivityFilterPickerProviderHelper
                    .getActivityIndexerItemList(elementList, getScope()));
        }
        super.setInitialElementSelections(new ArrayList(pickerProxyItems));

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
         * IActivityPickerProxyItem item = null; String uriStr =
         * memento.getString(URI_TAG); String name =
         * memento.getString(NAME_TAG); if (uriStr != null && name != null) {
         * URI elementURI = URI.createPlatformResourceURI(uriStr, false); if
         * (provider != null) { if(isValidHistoryItem(uriStr)){ item =
         * provider.getItem(elementURI, name, getScope()); } } } else if (name
         * != null) { item = getExtraItem(name); } return item; }
         * 
         * @Override protected void storeItemToMemento(Object item, IMemento
         * memento) { if (item instanceof IActivityPickerProxyItem) {
         * IActivityPickerProxyItem proxyItem = (IActivityPickerProxyItem) item;
         * URI uri = proxyItem.getURI(); String name =
         * proxyItem.getQualifiedName(); if (uri != null){
         * memento.putString(URI_TAG, uri.toString()); } if(name != null) {
         * memento.putString(NAME_TAG, name); } } } };
         */
        return null;
    }

    private boolean isValidHistoryItem(String uriStr) {
        Object[] content = getContent(getScope());
        if (content != null && content.length > 0) {
            for (int i = 0; i < content.length; i++) {
                Object obj = content[i];
                if (obj instanceof IActivityPickerProxyItem) {
                    IActivityPickerProxyItem item =
                            (IActivityPickerProxyItem) obj;
                    URI itemURI = item.getURI();
                    if (itemURI != null && uriStr.equals(itemURI.toString())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private class ActivityPickerLabelProvider extends LabelProvider implements
            ILabelDecorator {

        @Override
        public String getText(Object element) {
            String text = null;

            if (element instanceof IActivityPickerProxyItem) {
                text = ((IActivityPickerProxyItem) element).getName();

                // If duplicate item then show the context
                if (isDuplicateElement(element)) {
                    text = decorateText(text, element);
                }
            } else if (element instanceof NamedElement) {
                text =
                        Xpdl2ModelUtil
                                .getDisplayNameOrName((NamedElement) element);
            } else if (element instanceof String) {
                text = (String) element;
            }

            return text != null ? text : ""; //$NON-NLS-1$
        }

        @Override
        public Image getImage(Object element) {
            Image img = null;

            if (element instanceof IActivityPickerProxyItem) {
                img = ((IActivityPickerProxyItem) element).getImage();
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
            if (element instanceof IActivityPickerProxyItem) {
                IActivityPickerProxyItem item =
                        (IActivityPickerProxyItem) element;

                text = String.format("%s - %s", item.getName(), //$NON-NLS-1$
                        getQualification(item));
            }

            return text;
        }
    }

    private class ActivityPickerDetailsLabelProvider extends LabelProvider {
        private final WorkbenchLabelProvider wbLabelProvider =
                new WorkbenchLabelProvider();

        @Override
        public String getText(Object element) {
            String text = null;

            if (element instanceof IActivityPickerProxyItem) {
                URI uri = ((IActivityPickerProxyItem) element).getURI();

                if (uri != null) {
                    uri = uri.trimFragment();
                    text = uri.toString();
                } else {
                    text = ((IActivityPickerProxyItem) element).getName();
                }
            }

            return text != null ? text : ""; //$NON-NLS-1$
        }

        @Override
        public Image getImage(Object element) {
            Image img = null;

            if (element instanceof IActivityPickerProxyItem) {
                Object item = ((IActivityPickerProxyItem) element).getItem();
                if (item instanceof EObject) {
                    IResource resource =
                            WorkingCopyUtil.getFile((EObject) item);
                    if (resource != null) {
                        img = wbLabelProvider.getImage(resource);
                    }
                } else {
                    if (getScope() != null) {
                        IResource resource =
                                WorkingCopyUtil.getFile(getScope());
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
