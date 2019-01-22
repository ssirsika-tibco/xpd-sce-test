/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.resources.ui.internal.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IMemento;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.resources.ui.picker.CommonPickerDialog;
import com.tibco.xpd.resources.ui.types.ITypeLabelProvider;
import com.tibco.xpd.resources.ui.types.TypeInfo;
import com.tibco.xpd.resources.ui.types.TypedItem;
import com.tibco.xpd.ui.dialogs.FilteredMultiSelectionDialog;

/**
 * This class should not be used directly in applications. Instead use the class
 * <code>TypeProvider</code> utility class instead to invoke this dialog. <br/>
 * This class provides a general mechanism to let users select items from a
 * previous query of the content.
 * 
 * @author rassisi
 * @deprecated Use {@link CommonPickerDialog} instead.
 */
@Deprecated
public class PickerDialog extends FilteredMultiSelectionDialog {

    private final IResource[] queryResources;

    private final TypeInfo[] queryTypes;

    private final Object[] contentToExclude;

    private TypedItem[] contents;

    private Comparator<TypedItem> comparator;

    private List<IFilter> filters;

    /**
     * 
     * @param shell
     *            parent <code>Shell</code>.
     * @param project
     *            list only Profiles found in this project.
     */
    public PickerDialog(Shell shell, IResource[] queryResources,
            TypeInfo[] queryTypes, Object[] contentToExclude,
            Object[] contentToPreselect, boolean isMulti) {
        super(shell, isMulti);
        this.queryResources = queryResources;
        this.queryTypes = queryTypes;
        this.contentToExclude = contentToExclude;

        if (contentToPreselect != null) {
            setInitialElementSelections(Arrays.asList(contentToPreselect));
        }
        setTitle(Messages.PickerDialog_title);
        setMessage(Messages.PickerDialog_shortdesc);

        // Set all the label providers

        PickerLabelProvider pickerLabelProvider = new PickerLabelProvider();
        setListLabelProvider(pickerLabelProvider);
        setListSelectionLabelDecorator(pickerLabelProvider);
        setDetailsLabelProvider(new StatusLabelProvider());

        // setInitialPattern("?"); //$NON-NLS-1$

        // Set the selection history - this will just store the URI of the
        // profile in the memento

        setSelectionHistory(new SelectionHistory() {
            private static final String KEY = "URI"; //$NON-NLS-1$

            @Override
            protected Object restoreItemFromMemento(IMemento memento) {
                Object obj = null;
                if (memento != null) {
                    String uriStr = memento.getString(KEY);
                    TypedItem[] content = getContent(new NullProgressMonitor());
                    for (TypedItem item : content) {
                        if (item.getUriString().equals(uriStr)) {
                            obj = item;
                            break;
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
     * TypedItems from the picker.
     * 
     * Each filter's select() method is passed an argument of type TypedItem.
     * The filter can then decide if the TypedItem should be displayed in the
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
        TypedItem[] content = getContent(new NullProgressMonitor());
        if (selections != null) {
            for (Object sel : selections) {
                if (sel instanceof TypedItem) {
                    selectedItems.add(sel);
                } else if (sel instanceof EObject) {
                    URI uri = EcoreUtil.getURI((EObject) sel);
                    if (uri != null) {
                        String uriStr = uri.toString();
                        for (TypedItem item : content) {
                            if (item.getUriString().equals(uriStr)) {
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

        // Resolve the selection to

        Set<Object> resolvedObjs = new HashSet<Object>();
        for (Object sel : results) {
            Object resolved =
                    TypeUtilInternal.getResolver(TypeUtilInternal
                            .getTypeOfElement(sel)).toObject((TypedItem) sel,
                            queryResources,
                            contentToExclude);
            if (resolved != null) {
                resolvedObjs.add(resolved);
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
                TypeInfo typeInfo = null;
                try {
                    typeInfo = TypeUtilInternal.getTypeOfElement(item);
                    for (TypeInfo ti : queryTypes) {
                        if (typeInfo.equals(ti)) {

                            if (item instanceof TypedItem) {
                                String matchString =
                                        ((TypedItem) item).getName();
                                if (matchString == null
                                        || matchString.length() == 0) {
                                    matchString =
                                            ((TypedItem) item)
                                                    .getQualifiedName();
                                }
                                if (matchString != null) {
                                    boolean matches = matches(matchString);
                                    return matches;
                                }
                            }
                            return true;
                        }

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
        TypedItem[] content = getContent(progressMonitor);

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
    @SuppressWarnings("restriction")
    protected TypedItem[] getContent(IProgressMonitor monitor) {

        contents = null;

        if (contents == null) {
            Set<TypedItem> items =
                    TypeUtilInternal.getContent(queryResources,
                            queryTypes,
                            contentToExclude,
                            monitor);

            Set<TypedItem> result = new HashSet<TypedItem>();
            for (TypedItem item : items) {

                // Object resolved = TypeUtilInternal.getResolver(
                // TypeUtilInternal.getTypeOfElement(item)).toObject(item,
                // queryResources, contentToExclude);
                if (!isObjectToExclude(item) && !isItemExcluded(item)) {
                    result.add(item);
                }
            }

            contents = new TypedItem[result.size()];
            Object[] itemsArray = result.toArray();
            for (int i = 0; i < itemsArray.length; i++) {
                contents[i] = (TypedItem) itemsArray[i];
            }

        }
        return contents;
    }

    private boolean isItemExcluded(TypedItem item) {

        if (filters != null) {
            for (IFilter filter : filters) {
                if (!filter.select(item)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * @param object
     * @return
     */
    private boolean isObjectToExclude(TypedItem typedItem) {
        String uriString = typedItem.getUriString();
        if (contentToExclude != null) {
            for (Object o : contentToExclude) {
                if (o instanceof EObject) {
                    EObject eObject = (EObject) o;
                    Resource modelElementResource = eObject.eResource();
                    if (modelElementResource != null) {
                        URI uri =
                                modelElementResource.getURI()
                                        .appendFragment(modelElementResource
                                                .getURIFragment(eObject));
                        if (uriString.equals(uri.toString())) {
                            return true;
                        }
                    }
                }
            }
            return false;
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
        if (item instanceof TypedItem) {
            return ((TypedItem) item).getName();
        }
        return ""; //$NON-NLS-1$
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Comparator getItemsComparator() {
        if (comparator == null) {
            comparator = new Comparator<TypedItem>() {

                public int compare(TypedItem typedItem1, TypedItem typedItem2) {
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
     * Label provider for the UML profile picker - this will also provider the
     * selection label provider.
     * 
     * @author njpatel
     */
    protected class PickerLabelProvider extends LabelProvider implements
            ILabelDecorator {

        @Override
        public String getText(Object element) {
            if (element == null) {
                return ""; //$NON-NLS-1$
            }
            TypeInfo typeInfo = TypeUtilInternal.getTypeOfElement(element);
            return TypeUtilInternal.getLabelProvider(typeInfo)
                    .getText((TypedItem) element);
        }

        @Override
        public Image getImage(Object element) {
            if (element == null) {
                return null;
            }
            TypeInfo typeInfo = TypeUtilInternal.getTypeOfElement(element);
            return TypeUtilInternal.getLabelProvider(typeInfo)
                    .getImage((TypedItem) element);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.jface.viewers.ILabelDecorator#decorateImage(org.eclipse
         * .swt.graphics.Image, java.lang.Object)
         */
        public Image decorateImage(Image image, Object element) {
            TypeInfo typeInfo = TypeUtilInternal.getTypeOfElement(element);
            return TypeUtilInternal.getLabelProvider(typeInfo)
                    .decorateImage(image, (TypedItem) element);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.jface.viewers.ILabelDecorator#decorateText(java.lang.
         * String, java.lang.Object)
         */
        public String decorateText(String text, Object element) {
            TypeInfo typeInfo = TypeUtilInternal.getTypeOfElement(element);
            return TypeUtilInternal.getLabelProvider(typeInfo)
                    .decorateText(text, (TypedItem) element);
        }

    }

    /**
     * Status label provider.
     */
    protected class StatusLabelProvider extends LabelProvider {
        @Override
        public String getText(Object element) {
            TypeInfo item = TypeUtilInternal.getTypeOfElement(element);
            if (item == null) {
                return ""; //$NON-NLS-1$
            }
            ITypeLabelProvider labelProvider =
                    TypeUtilInternal.getLabelProvider(item);
            return labelProvider.getResourceText((TypedItem) element);
        }

        @Override
        public Image getImage(Object element) {
            TypeInfo item = TypeUtilInternal.getTypeOfElement(element);
            if (item == null) {
                return null;
            }
            return TypeUtilInternal.getLabelProvider(item)
                    .getResourceImage((TypedItem) element);
        }
    }

}
