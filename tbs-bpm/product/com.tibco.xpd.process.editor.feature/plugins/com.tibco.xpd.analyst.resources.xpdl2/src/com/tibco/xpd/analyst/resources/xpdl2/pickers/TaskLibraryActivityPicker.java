/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.pickers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.dialogs.FilteredMultiSelectionDialog;
import com.tibco.xpd.ui.util.CapabilityUtil;
import com.tibco.xpd.xpdl2.NamedElement;

/**
 * Picker for activities stored in a task library.
 * <p>
 * <b>NOTE: This class is part of the process analyst feature DESPITE the fact
 * that task libraries are handled in the WM feature - this is because the task
 * library editor in WM feature has dependency on process analyst BUT analyst
 * feature (TaskTypeReferenceSeciton) needs to pick activities from task
 * libraries.
 * 
 * @author aallway
 * @since 3.2
 */
public class TaskLibraryActivityPicker extends FilteredMultiSelectionDialog {

    private HashSet<IFilter> filters;

    private Comparator comparator;

    TaskLibraryActivityPickerLabelProvider labelProvider;

    public TaskLibraryActivityPicker(Shell shell, boolean multi) {
        super(shell, multi);

        filters = new HashSet<IFilter>();

        setTitle(Messages.TaskLibraryActivityPicker_TaskLibraryTaskPicker_title);
        setMessage(Messages.TaskLibraryActivityPicker_SelectATask_longdesc);

        labelProvider = new TaskLibraryActivityPickerLabelProvider();
        setListLabelProvider(labelProvider);
        setListSelectionLabelDecorator(labelProvider);
        setDetailsLabelProvider(new TaskLibraryActivityPickerDetailsLabelProvider());

        return;
    }

    @Override
    protected ItemsFilter createFilter() {
        return new ItemsFilter() {
            @Override
            public boolean isConsistentItem(Object item) {
                boolean isConsistent = false;

                if (item instanceof ITaskLibraryActivityProxyItem) {
                    isConsistent = include(item);
                }

                return isConsistent;
            }

            @Override
            public boolean matchItem(Object item) {
                boolean matches = include(item);

                if (matches) {
                    if (item instanceof ITaskLibraryActivityProxyItem) {
                        matches =
                                matches(((ITaskLibraryActivityProxyItem) item)
                                        .getName());
                    }
                }

                return matches;
            }

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

    /**
     * Check whether the given item should be included in the picker. The added
     * set of filters will be used to determine whether the item should be
     * included, if no filters are available then all items will be added.
     * 
     * @param item
     *            item to test
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
     * Add IFilter to filter the content of the picker. Note that the object
     * passed to the filter will be of type
     * <code>ITaskLibraryActivityProxyItem</code>.
     * 
     * @param filter
     */
    public void addFilter(IFilter filter) {
        if (filter != null) {
            filters.add(filter);
        }
    }

    @Override
    protected void fillContentProvider(AbstractContentProvider contentProvider,
            ItemsFilter itemsFilter, IProgressMonitor progressMonitor)
            throws CoreException {
        List<ITaskLibraryActivityProxyItem> content =
                getContent(progressMonitor);

        if (content != null) {
            for (Object obj : content) {
                contentProvider.add(obj, itemsFilter);
            }
        }
        return;
    }

    protected List<ITaskLibraryActivityProxyItem> getContent(
            IProgressMonitor progressMonitor) {
        List<ITaskLibraryActivityProxyItem> content =
                new ArrayList<ITaskLibraryActivityProxyItem>();

        IndexerItem criteria =
                new IndexerItemImpl(null,
                        Xpdl2ResourcesPlugin.TASK_LIBRARY_TASK_INDEX_TYPE,
                        null, null);

        Collection<IndexerItem> indexedTasks =
                XpdResourcesPlugin.getDefault().getIndexerService()
                        .query(Xpdl2ResourcesPlugin.TASK_LIBRARY_INDEXER_ID,
                                criteria);
        if (indexedTasks != null) {
            for (IndexerItem indexTask : indexedTasks) {
                content.add(new TaskLibraryTaskItem(indexTask));
            }
        }

        return content;
    }

    @Override
    protected IDialogSettings getDialogSettings() {
        IDialogSettings settings =
                Activator.getDefault().getDialogSettings()
                        .getSection(getDialogSettingsId());

        if (settings == null) {
            settings =
                    Activator.getDefault().getDialogSettings()
                            .addNewSection(getDialogSettingsId());
        }

        return settings;
    }

    /**
     * @return Id under which dialog settings are saved / restored.
     */
    protected String getDialogSettingsId() {
        return "TaskLibraryActivityPicker.dialogSettings"; //$NON-NLS-1$
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
                if (obj instanceof ITaskLibraryActivityProxyItem) {
                    ITaskLibraryActivityProxyItem item =
                            (ITaskLibraryActivityProxyItem) obj;
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

    @Override
    public String getElementName(Object item) {
        if (item instanceof ITaskLibraryActivityProxyItem) {
            return ((ITaskLibraryActivityProxyItem) item).getDisplayName();
        }
        return null;
    }

    @Override
    protected Comparator getItemsComparator() {
        if (comparator == null) {
            comparator = new Comparator<ITaskLibraryActivityProxyItem>() {

                public int compare(ITaskLibraryActivityProxyItem o1,
                        ITaskLibraryActivityProxyItem o2) {
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
        return new Status(IStatus.OK, Xpdl2ResourcesPlugin.PLUGIN_ID,
                IStatus.OK, "", null); //$NON-NLS-1$
    }

    /**
     * Get the qualification of the given item
     * 
     * @param item
     * @return qualification of the event, empty string of no qualification
     *         found.
     */
    protected String getQualification(ITaskLibraryActivityProxyItem item) {
        String qualification = ""; //$NON-NLS-1$

        if (item != null) {
            String qualifiedName = item.getQualifiedName();
            String name = item.getName();

            if (qualifiedName != name) {
                qualification =
                        qualifiedName.substring(0, qualifiedName.length()
                                - name.length()
                                - ProcessUIUtil.PROCESS_PACKAGE_SEPARATOR
                                        .length());
            }
        }

        return qualification;
    }

    private class TaskLibraryTaskItem implements ITaskLibraryActivityProxyItem {

        private String qualifiedName;

        private String name;

        private URI uri;

        private URI imageUri;

        private String displayName;

        private IndexerItem indexerItem;

        /**
         * Constructor.
         * 
         * @param dbItem
         *            database item.
         */
        public TaskLibraryTaskItem(IndexerItem item) {
            Assert.isNotNull(item, "item"); //$NON-NLS-1$
            this.qualifiedName = item.getName();
            String uriString = item.getURI();
            this.uri = URI.createURI(uriString);
            this.displayName =
                    item.get(Xpdl2ResourcesPlugin.ATTRIBUTE_DISPLAY_NAME);
            String imgURIStr =
                    item.get(Xpdl2ResourcesPlugin.ATTRIBUTE_IMAGE_URI);

            if (imgURIStr != null) {
                imageUri = URI.createURI(imgURIStr);
            }
        }

        /*
          */
        public Image getImage() {
            Image img = null;

            if (imageUri != null) {
                img = ExtendedImageRegistry.getInstance().getImage(imageUri);
            }

            return img;
        }

        /*
         * (non-Javadoc)
         */
        public String getName() {

            if (name == null) {
                int idx =
                        qualifiedName
                                .lastIndexOf(ProcessUIUtil.PROCESS_PACKAGE_SEPARATOR);

                if (idx >= 0) {
                    name = qualifiedName.substring(idx + 1);
                } else {
                    name = qualifiedName;
                }
            }

            return name;
        }

        /**
         * @return the displayName
         */
        public String getDisplayName() {
            return displayName;
        }

        /*
         * (non-Javadoc)
         */
        public String getQualifiedName() {
            return qualifiedName;
        }

        /*
         * (non-Javadoc)
         */
        public URI getURI() {
            return uri;
        }

        public IndexerItem getIndexerItem() {
            return indexerItem;
        }

        @Override
        public int hashCode() {
            int code = super.hashCode();

            if (uri != null && qualifiedName != null) {
                code = uri.hashCode() + qualifiedName.hashCode();
            }

            return code;
        }

        @Override
        public boolean equals(Object obj) {
            boolean isEquals = false;

            if (obj == this) {
                isEquals = true;
            } else if (obj instanceof ITaskLibraryActivityProxyItem) {
                ITaskLibraryActivityProxyItem other =
                        (ITaskLibraryActivityProxyItem) obj;
                isEquals =
                        other.getQualifiedName().equals(getQualifiedName())
                                && other.getURI().equals(getURI());
            }

            return isEquals;
        }

    }

    public interface ITaskLibraryActivityProxyItem {
        /**
         * Get the name of the item.
         * 
         * @return name.
         */
        String getName();

        /**
         * Get the fully qualified name.
         * 
         * @return qualified name. If there is no qualification then this will
         *         return the same result as {@link #getName()}.
         */
        String getQualifiedName();

        /**
         * Get the image that will represent this item in the picker.
         * 
         * @return
         */
        Image getImage();

        /**
         * Get the URI of this item. This URI will be used to resolve this item
         * to it's {@link EObject}.
         * 
         * @return <code>URI</code>
         */
        URI getURI();

        /**
         * Gets the display name decorated depending on the developer capability
         * being switched on.
         * 
         * @return
         */
        String getDisplayName();
    }

    private class TaskLibraryActivityPickerLabelProvider extends LabelProvider
            implements ILabelDecorator {

        @Override
        public String getText(Object element) {
            String text = null;

            if (element instanceof ITaskLibraryActivityProxyItem) {
                ITaskLibraryActivityProxyItem processPickerProxyItem =
                        (ITaskLibraryActivityProxyItem) element;
                if (CapabilityUtil.isDeveloperActivityEnabled()) {
                    String displayName =
                            processPickerProxyItem.getDisplayName();
                    if (displayName == null || displayName.length() == 0) {
                        text = processPickerProxyItem.getName();
                    } else {
                        text =
                                String
                                        .format("%1$s (%2$s)", processPickerProxyItem.getDisplayName(), processPickerProxyItem.getName()); //$NON-NLS-1$
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

            if (element instanceof ITaskLibraryActivityProxyItem) {
                img = ((ITaskLibraryActivityProxyItem) element).getImage();
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
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.jface.viewers.ILabelDecorator#decorateText(java.lang.
         * String, java.lang.Object)
         */
        public String decorateText(String text, Object element) {
            if (element instanceof ITaskLibraryActivityProxyItem) {
                ITaskLibraryActivityProxyItem item =
                        (ITaskLibraryActivityProxyItem) element;
                text = String.format("%1$s - %2$s", text, //$NON-NLS-1$
                        getQualification(item));
            }
            return text;
        }
    }

    private class TaskLibraryActivityPickerDetailsLabelProvider extends
            LabelProvider {
        private final WorkbenchLabelProvider wbLabelProvider =
                new WorkbenchLabelProvider();

        @Override
        public String getText(Object element) {
            String text = null;

            if (element instanceof ITaskLibraryActivityProxyItem) {
                URI uri = ((ITaskLibraryActivityProxyItem) element).getURI();

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

            if (element instanceof ITaskLibraryActivityProxyItem) {
                URI uri = ((ITaskLibraryActivityProxyItem) element).getURI();

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
