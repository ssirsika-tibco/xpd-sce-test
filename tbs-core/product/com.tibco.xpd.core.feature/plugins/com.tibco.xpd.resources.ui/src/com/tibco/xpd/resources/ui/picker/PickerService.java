/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.resources.ui.picker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.picker.filters.ExcludedObjectsFilter;
import com.tibco.xpd.resources.ui.picker.filters.ResourcesFilter;

/**
 * Manages picker content extensions and provide utility methods to easy use of
 * {@link CommonPickerDialog}.
 * 
 * @since 3.3.1
 * @author Jan Arciuchiewicz
 */
public class PickerService {

    private static Logger LOG = XpdResourcesUIActivator.getDefault()
            .getLogger();

    private static PickerService INSTANCE = new PickerService();

    private final Map<String, PickerContentExtension> providers;

    private PickerService() {
        providers = createPickerExtensionsFromResistry();
    }

    /**
     * Gets an instance of this service.
     */
    public static PickerService getInstance() {
        return INSTANCE;
    }

    /**
     * Gets the picker content extension by id.
     * 
     * @param id
     *            of a picker content extension as defined in
     *            <code>com.tibco.xpd.resources.ui.pickerContent</code> plug-in
     *            extension.
     * @return reference to the PickerContentExtension <code>null</code> if
     *         there is no such extension defined.
     * 
     */
    public PickerContentExtension getPickerContentExtension(String id) {
        PickerContentExtension provider = providers.get(id);
        return provider;
    }

    /**
     * Gets all defined picker content extensions.
     * 
     * @return all picker content extensions.
     */
    public Collection<PickerContentExtension> getAllPickerContentExtensions() {
        return Collections.unmodifiableCollection(providers.values());
    }

    private Map<String, PickerContentExtension> createPickerExtensionsFromResistry() {
        Map<String, PickerContentExtension> providers =
                new LinkedHashMap<String, PickerContentExtension>();
        IExtensionPoint extensionPoint =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(XpdResourcesUIActivator.ID,
                                "pickerContent"); //$NON-NLS-1$
        IConfigurationElement[] configurationElements =
                extensionPoint.getConfigurationElements();
        for (IConfigurationElement ce : configurationElements) {
            if ("pickerContentProvider".equals(ce.getName())) { //$NON-NLS-1$
                try {
                    String id = ce.getAttribute("id"); //$NON-NLS-1$
                    IPickerItemProvider itemProvider =
                            (IPickerItemProvider) ce
                                    .createExecutableExtension("itemProvider"); //$NON-NLS-1$
                    IPickerLabelProvider labelProvider =
                            (IPickerLabelProvider) ce
                                    .createExecutableExtension("labelProvider"); //$NON-NLS-1$
                    PickerContentExtension pickerItemProvider =
                            new PickerContentExtension(id, itemProvider,
                                    labelProvider);
                    if (providers.keySet().contains(id)) {
                        LOG.warn(String
                                .format("Duplicate provider 'id' for pickerItemProvideers extension. (%1$s)", //$NON-NLS-1$
                                        id));
                    }
                    providers.put(id, pickerItemProvider);
                } catch (CoreException e) {
                    LOG.log(e.getStatus());
                }
            }
        }
        return providers;
    }

    /**
     * Create list of commonly used filters.
     */
    private List<IFilter> createCommonFilters(IResource[] queryResources,
            Object[] contentToExclude, IFilter[] filters) {
        List<IFilter> itemFilters =
                (filters == null) ? new ArrayList<IFilter>()
                        : new ArrayList<IFilter>(Arrays.asList(filters));
        if (queryResources != null) {
            itemFilters.add(new ResourcesFilter(queryResources));
        }
        if (contentToExclude != null) {
            itemFilters.add(new ExcludedObjectsFilter(contentToExclude));
        }
        return itemFilters;
    }

    /**
     * This method opens and shows the single item picker dialog. Provided list
     * of type queries specify what content will be displayed in the picker.
     * However, this method's use has been deprecated as it doesn't have
     * 'contentToPreselect' parameter which can cause some serious problems.
     * 
     * @param shell
     *            the context shell to associate picker with.
     * @param typeQueries
     *            specifies queries to get content from specific extensions and
     *            its types.
     * @param initialPattern
     *            if not <code>null</code> this String is set as the initial
     *            search pattern (ignored if <code>null</code>).
     * @param queryResources
     *            only items for the given resources will be considered (all
     *            resources if <code>null</code>).
     * @param contentToExclude
     *            the given Objects will be excluded from the result. The
     *            specified objects can be EObjects or PickerItems. (no objects
     *            excluded if <code>null</code>)
     * @param filters
     *            filters to further filter content.
     * @return the resolved object for a selected item or <code>null</code> if
     *         picker was canceled or closed.
     * 
     */
    @Deprecated
    public Object openSinglePickerDialog(Shell shell,
            PickerTypeQuery[] typeQueries, String initialPattern,
            IResource[] queryResources, Object[] contentToExclude,
            IFilter[] filters) {
        // List<IFilter> itemFilters =
        // createCommonFilters(queryResources, contentToExclude, filters);
        // CommonPickerDialog pickerDialog =
        // new CommonPickerDialog(shell, false, Collections.emptyList(),
        // itemFilters, typeQueries);
        //
        // if (initialPattern != null) {
        // // Set the initial pattern
        // pickerDialog.setInitialPattern(initialPattern);
        // }
        // pickerDialog.open();
        // Object[] result = pickerDialog.getResult();
        // if (result != null && result.length > 0) {
        // return result[0];
        // }
        // return null;

        /*
         * Saket SCF-104: Since the use of this method is deprecated, it has
         * been overriden to add the 'contentToPreselect' parameter. But since
         * this method already forms a part of an API, we cannot change or
         * remove it as there may be code outside of our control that uses it.
         * Therefore we must still support that method “as is”. To make these
         * things work collectively, the 'new' overriden method is called here
         * (passing 'null' for 'contentToPreselect' parameter) and the code
         * written above this comment has been copied to the new overriden
         * method. That code, however, has been commented (and not deleted from
         * here) if in case anyone wants to view it.
         */
        return openSinglePickerDialog(shell,
                typeQueries,
                initialPattern,
                queryResources,
                contentToExclude,
                filters,
                null);
    }

    /**
     * This method opens and shows the single item picker dialog. Provided list
     * of type queries specify what content will be displayed in the picker.
     * This method also allows to set the dialog title.
     * 
     * @param title
     *            the dialog title (or <code>null</code> to use default).
     * @param shell
     *            the context shell to associate picker with.
     * @param typeQueries
     *            specifies queries to get content from specific extensions and
     *            its types.
     * @param initialPattern
     *            if not <code>null</code> this String is set as the initial
     *            search pattern (ignored if <code>null</code>).
     * @param queryResources
     *            only items for the given resources will be considered (all
     *            resources if <code>null</code>).
     * @param contentToExclude
     *            the given Objects will be excluded from the result. The
     *            specified objects can be EObjects or PickerItems. (no objects
     *            excluded if <code>null</code>)
     * @param filters
     *            filters to further filter content.
     * @param contentToPreselect
     *            content to be preselected
     * @return the resolved object for a selected item or <code>null</code> if
     *         picker was cancelled or closed.
     */
    public Object openSinglePickerDialog(String title, Shell shell,
            PickerTypeQuery[] typeQueries, String initialPattern,
            IResource[] queryResources, Object[] contentToExclude,
            IFilter[] filters, Object contentToPreselect) {

        List<IFilter> itemFilters =
                createCommonFilters(queryResources, contentToExclude, filters);

        CommonPickerDialog pickerDialog;
        if (contentToPreselect != null) {
            pickerDialog =
                    new CommonPickerDialog(shell, false,
                            Arrays.asList(contentToPreselect), itemFilters,
                            typeQueries);
        } else {
            pickerDialog =
                    new CommonPickerDialog(shell, false,
                            Collections.emptyList(), itemFilters, typeQueries);
        }

        if (title != null) {
            pickerDialog.setTitle(title);
        }
        pickerDialog.open();
        Object[] result = pickerDialog.getResult();
        if (result != null && result.length > 0) {
            return result[0];
        }
        return null;
    }

    /**
     * This method opens and shows the single item picker dialog. Provided list
     * of type queries specify what content will be displayed in the picker.
     * 
     * @param shell
     *            the context shell to associate picker with.
     * @param typeQueries
     *            specifies queries to get content from specific extensions and
     *            its types.
     * @param initialPattern
     *            if not <code>null</code> this String is set as the initial
     *            search pattern (ignored if <code>null</code>).
     * @param queryResources
     *            only items for the given resources will be considered (all
     *            resources if <code>null</code>).
     * @param contentToExclude
     *            the given Objects will be excluded from the result. The
     *            specified objects can be EObjects or PickerItems. (no objects
     *            excluded if <code>null</code>)
     * @param filters
     *            filters to further filter content.
     * @param contentToPreselect
     *            content to be preselected
     * @return the resolved object for a selected item or <code>null</code> if
     *         picker was cancelled or closed.
     */
    public Object openSinglePickerDialog(Shell shell,
            PickerTypeQuery[] typeQueries, String initialPattern,
            IResource[] queryResources, Object[] contentToExclude,
            IFilter[] filters, Object contentToPreselect) {
        return openSinglePickerDialog(null,
                shell,
                typeQueries,
                initialPattern,
                queryResources,
                contentToExclude,
                filters,
                contentToPreselect);
    }

    /**
     * This method opens and shows the multiple items picker dialog. Provided
     * list of type queries specify what content will be displayed in the
     * picker. This method also allows to set the dialog title.
     * 
     * @param title
     *            the dialog title (or <code>null</code> to use default).
     * @param shell
     *            the context shell to associate picker with.
     * @param typeQueries
     *            specifies queries to get content from specific extensions and
     *            its types.
     * @param initialPattern
     *            if not <code>null</code> this String is set as the initial
     *            search pattern (ignored if <code>null</code>).
     * @param queryResources
     *            only items for the given resources will be considered (all
     *            resources if <code>null</code>).
     * @param contentToExclude
     *            the given Objects will be excluded from the result. The
     *            specified objects can be EObjects or PickerItems. (no objects
     *            excluded if <code>null</code>)
     * @param filters
     *            filters to further filter content.
     * @return the resolved object for a selected item or <code>null</code> if
     *         picker was cancelled or closed.
     * 
     */
    public Object[] openMultiPickerDialog(String title, Shell shell,
            PickerTypeQuery[] typeQuery, String initialPattern,
            IResource[] queryResources, Object[] contentToExclude,
            IFilter[] filters, Object[] contentToPreselect) {

        List<IFilter> itemFilters =
                createCommonFilters(queryResources, contentToExclude, filters);
        CommonPickerDialog pickerDialog =
                new CommonPickerDialog(shell, true,
                        Arrays.asList(contentToPreselect), itemFilters,
                        typeQuery);
        if (title != null) {
            pickerDialog.setTitle(title);
        }
        if (initialPattern != null) {
            // Set the initial pattern
            pickerDialog.setInitialPattern(initialPattern);
        }
        if (pickerDialog.open() == Window.OK) {
            return pickerDialog.getResult();
        }
        return null;
    }

    /**
     * This method opens and shows the multiple items picker dialog. Provided
     * list of type queries specify what content will be displayed in the
     * picker.
     * 
     * @param shell
     *            the context shell to associate picker with.
     * @param typeQueries
     *            specifies queries to get content from specific extensions and
     *            its types.
     * @param initialPattern
     *            if not <code>null</code> this String is set as the initial
     *            search pattern (ignored if <code>null</code>).
     * @param queryResources
     *            only items for the given resources will be considered (all
     *            resources if <code>null</code>).
     * @param contentToExclude
     *            the given Objects will be excluded from the result. The
     *            specified objects can be EObjects or PickerItems. (no objects
     *            excluded if <code>null</code>)
     * @param filters
     *            filters to further filter content.
     * @return the resolved object for a selected item or <code>null</code> if
     *         picker was cancelled or closed.
     * 
     */
    public Object[] openMultiPickerDialog(Shell shell,
            PickerTypeQuery[] typeQuery, String initialPattern,
            IResource[] queryResources, Object[] contentToExclude,
            IFilter[] filters, Object[] contentToPreselect) {
        return openMultiPickerDialog(null,
                shell,
                typeQuery,
                initialPattern,
                queryResources,
                contentToExclude,
                filters,
                contentToPreselect);
    }
}
