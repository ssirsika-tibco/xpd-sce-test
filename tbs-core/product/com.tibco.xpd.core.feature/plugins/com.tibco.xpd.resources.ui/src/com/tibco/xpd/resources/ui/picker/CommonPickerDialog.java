/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.resources.ui.picker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IMemento;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.resources.ui.types.TypedItem;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.dialogs.FilteredMultiSelectionDialog;

/**
 * Picker dialog displaying aggregated content (collection of {@link PickerItem}
 * ) contributed by <code>com.tibco.xpd.resources.ui.pickerContent</code>
 * extensions. Client specifies a list of {@link PickerTypeQuery}s as a
 * parameter of constructor. Each {@link PickerTypeQuery} contain reference to a
 * contributing extension and set of extension specific item's types.
 * 
 * @since 3.3.1
 * @author Jan Arciuchiewicz
 */
public class CommonPickerDialog extends FilteredMultiSelectionDialog {

    /** ID of the Studio Reloaded RCP application. */
    private static final String RELOADED_PRODUCT_ID =
            "com.tibco.xpd.rcp.product"; //$NON-NLS-1$

    private final PickerTypeQuery[] typeQueries;

    private PickerItem[] contents;

    private Comparator<PickerItem> comparator;

    private List<IFilter> filters;

    /**
     * Creates CommonPickerDialog.
     * 
     * @param shell
     *            the context shell.
     * @param isMulti
     *            will create multi-selection dialog if <code>true</code> single
     *            selection otherwise.
     * @param contentToPreselect
     *            the collection of object to preselect in the picker. Objects
     *            have to be of type EObject or {@link PickerItem}.
     * @param filters
     *            list of filters to further filter selected PickerItems.
     * @param typesQueries
     *            array of PickerTypeQuery determining what type of content and
     *            from which extension should be displayed in the picker.
     */
    public CommonPickerDialog(Shell shell, boolean isMulti,
            Collection<?> contentToPreselect, List<IFilter> filters,
            PickerTypeQuery... typesQueries) {
        super(shell, isMulti);
        this.typeQueries = typesQueries;
        if (filters == null) {
            this.filters = Collections.emptyList();
        } else {
            this.filters = filters;
        }

        if (contentToPreselect != null) {
            setInitialElementSelections(new ArrayList<Object>(
                    contentToPreselect));
        }
        setTitle(Messages.PickerDialog_title);
        setMessage(Messages.PickerDialog_shortdesc);

        // Set all the label providers

        PickerLabelProvider pickerLabelProvider = new PickerLabelProvider();
        setListLabelProvider(pickerLabelProvider);
        setListSelectionLabelDecorator(pickerLabelProvider);
        setDetailsLabelProvider(new DetailsLabelProvider());

        // Set the selection history - this will just store the URI of the
        // item in the memento.

        setSelectionHistory(new SelectionHistory() {
            private static final String KEY = "URI"; //$NON-NLS-1$

            @Override
            protected Object restoreItemFromMemento(IMemento memento) {
                Object obj = null;
                if (memento != null) {
                    String uriStr = memento.getString(KEY);
                    PickerItem[] content =
                            getContent(new NullProgressMonitor());
                    for (PickerItem item : content) {
                        String uri = item.getURI();
                        if (uri != null) {
                            if (uri.equals(uriStr)) {
                                obj = item;
                                break;
                            }
                        }
                    }
                }
                return obj;
            }

            @Override
            protected void storeItemToMemento(Object item, IMemento memento) {
                if (item instanceof TypedItem) {
                    String uri = ((TypedItem) item).getUriString();

                    if (uri != null) {
                        memento.putString(KEY, uri);
                    }
                }
            }
        });
    }

    /**
     * 
     * Add supplied filter to the list of filters used to screen out unwanted
     * PickerItems from the picker.
     * 
     * Each filter's select() method is passed an argument of type PickerItem.
     * The filter can then decide if the PickerItem should be displayed in the
     * picker.
     * 
     * @param IFilter
     *            filter
     */
    public void addFilter(IFilter filter) {
        if (filters == null) {
            filters = new ArrayList<IFilter>();
        }
        filters.add(filter);
    }

    @Override
    protected void updateInitialSelection(Set<Object> selectedItems) {
        List<?> selections = getInitialElementSelections();
        PickerItem[] content = getContent(new NullProgressMonitor());
        if (selections != null) {
            for (Object sel : selections) {
                if (sel instanceof PickerItem) {
                    selectedItems.add(sel);
                } else if (sel instanceof EObject) {
                    URI uri = EcoreUtil.getURI((EObject) sel);
                    if (uri != null) {
                        String uriStr = uri.toString();
                        for (PickerItem item : content) {
                            if (item.getURI().equals(uriStr)) {
                                selectedItems.add(item);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public Object[] getResult() {
        Object[] results = super.getResult();
        if (results == null) {
            return new Object[0];
        }
        // Resolve the selection to targt objects
        Set<Object> resolvedObjs = new LinkedHashSet<Object>();
        for (Object o : results) {
            if (o instanceof PickerItem) {
                PickerItem item = (PickerItem) o;
                Object resolved =
                        item.getPickerExtension().getPickerItemProvider()
                                .resolvePickerItem(item);
                if (resolved != null) {
                    /*
                     * SCF-432, XPD-7560: We force load the working copy because
                     * the Picker creates(gets hold) the Object using the object
                     * URI present in the indexer and there are situations where
                     * the WorkingCopy for the object are not loaded, hence we
                     * would need to force load it so that the working copy and
                     * the adapters are loaded.
                     */
                    if (resolved instanceof EObject) {
                        @SuppressWarnings("unused")
                        WorkingCopy forceLoadWorkingCopy =
                                WorkingCopyUtil
                                        .getWorkingCopyFor((EObject) resolved,
                                                true);
                    }

                    resolvedObjs.add(resolved);
                }
            }
        }
        return resolvedObjs.toArray();
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
                try {
                    if (item instanceof PickerItem) {
                        String matchString = ((PickerItem) item).getName();
                        if (matchString == null || matchString.length() == 0) {
                            matchString =
                                    ((PickerItem) item).getQualifiedName();
                        }
                        if (matchString != null) {
                            boolean matches = matches(matchString);
                            return matches;
                        }
                        return true;
                    }
                    return false;
                } catch (Exception ex) {
                    return false;
                }
            }
        };
    }

    @Override
    protected void fillContentProvider(AbstractContentProvider contentProvider,
            ItemsFilter itemsFilter, IProgressMonitor progressMonitor)
            throws CoreException {

        progressMonitor.beginTask(Messages.PickerDialog_monitor_label, 2);
        // Get the contents for the picker
        PickerItem[] content = getContent(progressMonitor);

        progressMonitor.worked(1);

        if (content != null) {
            for (Object obj : content) {
                contentProvider.add(obj, itemsFilter);
            }
            progressMonitor.worked(1);
        }
        progressMonitor.done();
    }

    /**
     * Get the content for the picker. This will be cached after the first call,
     * therefore all subsequent calls to this method will return the same
     * result.
     * 
     * @param progressMonitor
     * @return
     */
    protected PickerItem[] getContent(IProgressMonitor monitor) {
        if (contents == null) {
            Collection<PickerItem> result = new LinkedHashSet<PickerItem>();
            for (PickerTypeQuery typeQuery : typeQueries) {
                PickerContentExtension pickerExtension =
                        typeQuery.getPickerExtension();
                if (pickerExtension != null) {
                    IPickerItemProvider itemsProvider =
                            pickerExtension.getPickerItemProvider();
                    Collection<PickerItem> itmesContent =
                            itemsProvider.getContent(typeQuery, monitor);
                    for (PickerItem item : itmesContent) {
                        // Filtering
                        /*
                         * ACE-481: Saket: Need to ensure that we only show the
                         * Base Primitive Types that we support in ACE.
                         */
                        if (!isItemExcluded(item) && isBasePrimitiveTypeItemSupported(item)) {
                            result.add(item);
                        }
                    }
                }
            }

            contents = result.toArray(new PickerItem[result.size()]);
        }
        return contents;
    }

    /**
     * Return <code>true</code> if the specified picker item is one of the Base
     * Primitive Types that we support in ACE, <code>false</code> otherwise.
     * 
     * @param item
     * 
     * @return <code>true</code> if the specified picker item is one of the Base
     *         Primitive Types that we support in ACE, <code>false</code>
     *         otherwise.
     */
    private boolean isBasePrimitiveTypeItemSupported(PickerItem item) {
        if ("BASE_PRIMITIVE".equals(item.getType())) { //$NON-NLS-1$
            List<String> allowedBasePrimitiveTypes = new ArrayList<String>();
            allowedBasePrimitiveTypes.add("Text"); //$NON-NLS-1$
            allowedBasePrimitiveTypes.add("Number"); //$NON-NLS-1$
            allowedBasePrimitiveTypes.add("Boolean"); //$NON-NLS-1$
            allowedBasePrimitiveTypes.add("Date"); //$NON-NLS-1$
            allowedBasePrimitiveTypes.add("Time"); //$NON-NLS-1$
            allowedBasePrimitiveTypes.add("Date Time and Time Zone"); //$NON-NLS-1$
            allowedBasePrimitiveTypes.add("Performer"); //$NON-NLS-1$
            allowedBasePrimitiveTypes.add("URI"); //$NON-NLS-1$

            if (allowedBasePrimitiveTypes.contains(item.getName())) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    private boolean isItemExcluded(PickerItem item) {
        if (filters != null) {
            for (IFilter filter : filters) {
                if (!filter.select(item)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected IDialogSettings getDialogSettings() {
        IDialogSettings settings =
                XpdResourcesUIActivator.getDefault().getDialogSettings()
                        .getSection(getDialogSettingId());
        if (settings == null) {
            settings =
                    XpdResourcesUIActivator.getDefault().getDialogSettings()
                            .addNewSection(getDialogSettingId());
        }
        return settings;
    }

    protected String getDialogSettingId() {
        return "PickerDialog.Profile"; //$NON-NLS-1$;
    }

    @Override
    public String getElementName(Object item) {
        if (item instanceof PickerItem) {
            return ((PickerItem) item).getName();
        }
        return ""; //$NON-NLS-1$
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Comparator getItemsComparator() {
        if (comparator == null) {
            comparator = new Comparator<PickerItem>() {

                @Override
                public int compare(PickerItem typedItem1, PickerItem typedItem2) {
                    return typedItem1.getName().compareTo(typedItem2.getName());
                }
            };
        }
        return comparator;
    }

    @Override
    protected IStatus validateItem(Object item) {
        return Status.OK_STATUS;
    }

    /**
     * This provider will delegate labels provision and decoration of a list
     * elements to PickerItem's specific extension's IPickerLabelProvider.
     */
    protected static class PickerLabelProvider extends BaseLabelProvider
            implements ILabelProvider, ILabelDecorator {

        @Override
        public String getText(Object element) {
            if (element instanceof PickerItem) {
                PickerItem pickerItem = (PickerItem) element;
                PickerContentExtension extension =
                        pickerItem.getPickerExtension();
                return extension.getPickerLabelProvider().getText(pickerItem);
            }
            return element == null ? "" : element.toString(); //$NON-NLS-1$
        }

        @Override
        public Image getImage(Object element) {
            if (element instanceof PickerItem) {
                PickerItem pickerItem = (PickerItem) element;
                PickerContentExtension extension =
                        pickerItem.getPickerExtension();
                return extension.getPickerLabelProvider().getImage(pickerItem);
            }
            return null;
        }

        @Override
        public Image decorateImage(Image image, Object element) {
            if (element instanceof PickerItem) {
                PickerItem pickerItem = (PickerItem) element;
                PickerContentExtension extension =
                        pickerItem.getPickerExtension();
                return extension.getPickerLabelProvider()
                        .decorateSelectedItemImage(image, pickerItem);
            }
            return image;
        }

        @Override
        public String decorateText(String text, Object element) {
            if (element instanceof PickerItem) {
                PickerItem pickerItem = (PickerItem) element;
                PickerContentExtension extension =
                        pickerItem.getPickerExtension();
                return extension.getPickerLabelProvider()
                        .decorateSelectedItemText(text, pickerItem);
            }
            return text;
        }

    }

    /**
     * Provide label for details of a selected item displayed in the status
     * field of a picker. It will delegate provision to the items specific
     * picker content extension if exists and use standard label provider
     * otherwise.
     */
    protected static class DetailsLabelProvider extends LabelProvider {
        @Override
        public String getText(Object element) {
            if (element instanceof PickerItem) {
                PickerItem pickerItem = (PickerItem) element;
                PickerContentExtension extension =
                        pickerItem.getPickerExtension();
                return extension.getPickerLabelProvider()
                        .getStatusText(pickerItem);
            }
            return element == null ? "" : element.toString(); //$NON-NLS-1$
        }

        @Override
        public Image getImage(Object element) {
            if (element instanceof PickerItem) {
                PickerItem pickerItem = (PickerItem) element;
                PickerContentExtension extension =
                        pickerItem.getPickerExtension();
                return extension.getPickerLabelProvider()
                        .getStatusImage(pickerItem);
            }
            return null;
        }
    }

}
