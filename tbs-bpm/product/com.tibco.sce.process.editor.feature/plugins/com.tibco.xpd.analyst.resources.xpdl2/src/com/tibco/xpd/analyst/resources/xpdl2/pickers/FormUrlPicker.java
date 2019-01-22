/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.pickers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.providers.IProcessPickerProvider;
import com.tibco.xpd.analyst.resources.xpdl2.providers.IProcessPickerProvider.ProcessType;
import com.tibco.xpd.analyst.resources.xpdl2.providers.ProcessFilterPickerProviderHelper;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.util.CapabilityUtil;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author NWilson
 * 
 */
public class FormUrlPicker extends BaseFilterPicker {

    private final Set<IFilter> filters;

    private Comparator<Object> comparator;

    private final IProcessPickerProvider provider;

    private final ProcessPickerLabelProvider labelProvider;

    private boolean wantFormFiles;

    private boolean wantPageflows;

    public FormUrlPicker(Shell shell, boolean wantFormFiles,
            boolean wantPageflows) {
        super(shell, false);
        this.wantFormFiles = wantFormFiles;
        this.wantPageflows = wantPageflows;

        filters = new HashSet<IFilter>();
        provider = ProcessFilterPickerProviderHelper.getInstance();
        Assert.isNotNull(provider, "Process Picker provider"); //$NON-NLS-1$

        // Set the selection history
        SelectionHistory history = getSelectionHistory();

        if (history != null) {
            setSelectionHistory(history);
        }

        if (wantFormFiles && wantPageflows) {
            setTitle(Messages.FormUrlPicker_DialogTitle);
            setMessage(Messages.FormUrlPicker_DialogMessage);
        } else if (wantPageflows) {
            setTitle(Messages.FormUrlPicker_SelPageflowProcess_title);
            setMessage(Messages.FormUrlPicker_SelPageflowProcess_desciption);
        } else {
            setTitle(Messages.FormUrlPicker_SelForm_title);
            setMessage(Messages.FormUrlPicker_SelForm_description);
        }

        // Set the label providers
        labelProvider = new ProcessPickerLabelProvider();
        setListLabelProvider(labelProvider);
        setListSelectionLabelDecorator(labelProvider);
        setDetailsLabelProvider(new ProcessPickerDetailsLabelProvider());

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
            Set<Object> itemsList = new HashSet<Object>();

            Collection<IProject> projects = getValidProjects();
            Collection<String> projectNames = new HashSet<String>();
            for (IProject project : projects) {
                projectNames.add(project.getName());
            }

            if (wantPageflows) {
                // Add pageflow processes if we're in a business process...
                if (!isPageflowProcess()) {
                    for (IProcessPickerProxyItem item : Arrays.asList(provider
                            .getContent(monitor, ProcessType.PAGEFLOW))) {

                        /*
                         * Sid XPD-2976: Don't filter by referenced projects. We
                         * can add the project reference later if it's not
                         * already referenced.
                         */
                        URI uri = item.getURI();
                        if (uri != null) {
                            EObject eo = ProcessUIUtil.getEObjectFrom(uri);
                            if (eo != null && eo instanceof Process) {
                                boolean pageflowBusinessService =
                                        Xpdl2ModelUtil
                                                .isPageflowBusinessService((Process) eo);
                                if (!pageflowBusinessService) {
                                    itemsList.add(item);
                                }
                            }
                        }
                    }
                }
            }

            // ... and Forms
            if (wantFormFiles) {
                itemsList.addAll(getForms(projects));
            }
            items = itemsList.toArray();
        }

        return items != null ? items : new Object[0];
    }

    private boolean isPageflowProcess() {
        boolean isPageflow = false;
        EObject scope = getScope();
        if (scope != null) {
            Process process = Xpdl2ModelUtil.getProcess(scope);
            isPageflow = Xpdl2ModelUtil.isPageflow(process);
        }
        return isPageflow;
    }

    private Collection<IProject> getValidProjects() {
        Collection<IProject> projects = new HashSet<IProject>();
        EObject scope = getScope();
        if (scope != null) {
            IProject root = WorkingCopyUtil.getProjectFor(scope);
            if (root != null) {
                projects = Xpdl2ModelUtil.getAllReferencedProjects(root);
            }
        }
        return projects;
    }

    private List<IFile> getForms(Collection<IProject> projects) {
        List<IFile> forms = new ArrayList<IFile>();
        for (IProject project : projects) {
            SpecialFolder special =
                    SpecialFolderUtil.getSpecialFolderOfKind(project, "forms"); //$NON-NLS-1$
            if (special != null) {
                IFolder folder = special.getFolder();
                try {
                    addForms(forms, folder);
                } catch (CoreException e) {
                    Logger log =
                            LoggerFactory
                                    .createLogger(Xpdl2ResourcesPlugin.PLUGIN_ID);
                    log.error(e);
                }
            }
        }
        return forms;
    }

    private void addForms(List<IFile> forms, IFolder folder)
            throws CoreException {
        for (IResource member : folder.members()) {
            if (member instanceof IFile) {
                IFile file = (IFile) member;
                if ("form".equals(file.getFileExtension())) { //$NON-NLS-1$
                    forms.add(file);
                }
            } else if (member instanceof IFolder) {
                IFolder subfolder = (IFolder) member;
                addForms(forms, subfolder);
            }
        }
    }

    @Override
    protected IDialogSettings getDialogSettings() {
        IDialogSettings settings =
                Xpdl2ResourcesPlugin.getDefault().getDialogSettings()
                        .getSection("FormUrlPicker"); //$NON-NLS-1$

        if (settings == null) {
            settings =
                    Xpdl2ResourcesPlugin.getDefault().getDialogSettings()
                            .addNewSection("FormUrlPicker"); //$NON-NLS-1$
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
        Set<Object> resultSet = new HashSet<Object>();
        if (result != null) {
            for (Object obj : result) {
                if (obj instanceof IProcessPickerProxyItem) {
                    IProcessPickerProxyItem item =
                            (IProcessPickerProxyItem) obj;
                    URI uri = item.getURI();

                    if (uri != null) {
                        EObject eo = ProcessUIUtil.getEObjectFrom(uri);
                        if (eo != null) {
                            resultSet.add(eo);
                        }
                    }
                } else if (obj instanceof IFile) {
                    resultSet.add(obj);
                }
            }
        }

        return resultSet.toArray();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Comparator getItemsComparator() {
        if (comparator == null) {
            comparator = new Comparator<Object>() {

                @Override
                public int compare(Object o1, Object o2) {
                    int ret = 0;
                    if (o1 == o2) {
                        ret = 0;
                    } else {
                        if (o1 instanceof IProcessPickerProxyItem
                                && o2 instanceof IProcessPickerProxyItem) {
                            ret =
                                    (((IProcessPickerProxyItem) o1).getName()
                                            .compareTo(((IProcessPickerProxyItem) o2)
                                                    .getName()));
                        } else if (o1 instanceof IProcessPickerProxyItem) {
                            ret = 1;
                        } else if (o2 instanceof IProcessPickerProxyItem) {
                            ret = -1;
                        } else if (o1 instanceof IFile && o2 instanceof IFile) {
                            ret =
                                    (((IFile) o1).getName()
                                            .compareTo(((IFile) o2).getName()));
                        }
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
            } else if (element instanceof IFile) {
                text = ((IFile) element).getName();
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
            } else if (element instanceof IFile) {
                text = ((IFile) element).getFullPath().toString();
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
