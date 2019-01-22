/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.resources.ui.types;

import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.resources.ui.internal.types.PickerDialog;
import com.tibco.xpd.resources.ui.picker.PickerService;

/**
 * This Util class should by the application as the main entry point to the
 * types provider extension.<br/>
 * It's purpose is to provide content for pickers according to given criteria.
 * These criteria can be types and optional id's of the contributors of the
 * types provider. The reason for such additional criteria is, that different
 * providers can contribute different filter classes and resolver classes and to
 * avoid ambiguity.
 * 
 * @author rassisi
 * @deprecated Use {@link PickerService} instead.
 * 
 */
@Deprecated
public final class TypeProvider {

    /**
     * This method opens and shows the picker dialog. If a providerId is
     * specified in the type selection, its filters and possible resolvers are
     * used. If it is set to null, this method is only valid, if there is only
     * one types provider defined for every given type. If the user has
     * successfully selected an item and confirmed this by pressing the OK
     * Button, the method returns the instance of this selection.
     * 
     * @param shell
     *            The first top shell in the hierarchy.
     * @param initialPattern
     *            If set (not null) this String is set as the initial search
     *            pattern
     * @param queryResources
     *            If set (not null) only Objects for the given resources will be
     *            considered.
     * @param queryTypes
     *            This parameter describes the types for which the resolved
     *            objects should be searched for.
     * @param contentToExclude
     *            If set (not null) the given Objects will be exluded from the
     *            result.
     * 
     * @return Returns the resolved object of a successful selected Item.
     * 
     */
    public static Object openSinglePickerDialog(Shell shell,
            String initialPattern, IResource[] queryResources,
            TypeInfo[] queryTypes, Object[] contentToExclude) {
        PickerDialog pickerDialog =
                new PickerDialog(shell, queryResources, queryTypes,
                        contentToExclude, null, false);

        if (initialPattern != null) {
            // Set the initial pattern
            pickerDialog.setInitialPattern(initialPattern);
        }
        pickerDialog.open();
        Object[] result = pickerDialog.getResult();
        if (result != null && result.length > 0) {
            return result[0];
        }
        return null;
    }

    /**
     * This method opens and shows the picker dialog. If a providerId is
     * specified in the type selection, its filters and possible resolvers are
     * used. If it is set to null, this method is only valid, if there is only
     * one types provider defined for every given type. If the user has
     * successfully selected an item and confirmed this by pressing the OK
     * Button, the method returns the instance of this selection.
     * 
     * @param shell
     *            The first top shell in the hierarchy.
     * @param initialPattern
     *            If set (not null) this String is set as the initial search
     *            pattern
     * @param queryResources
     *            If set (not null) only Objects for the given resources will be
     *            considered.
     * @param queryTypes
     *            This parameter describes the types for which the resolved
     *            objects should be searched for.
     * @param contentToExclude
     *            If set (not null) the given Objects will be exluded from the
     *            result.
     * @param filters
     *            An IFilter List containing custom filters. These filter out
     *            TypedElements from being displayed in the picker. Each
     *            filter's select() method is passed an argument of type
     *            TypedItem. The filter can then decide if the TypedItem should
     *            be displayed in the picker.
     * 
     * @return Returns the resolved object of a successful selected Item.
     * 
     */
    public static Object openSinglePickerDialog(Shell shell,
            String initialPattern, IResource[] queryResources,
            TypeInfo[] queryTypes, Object[] contentToExclude,
            List<IFilter> filters) {
        PickerDialog pickerDialog =
                new PickerDialog(shell, queryResources, queryTypes,
                        contentToExclude, null, false);

        if (initialPattern != null) {
            // Set the initial pattern
            pickerDialog.setInitialPattern(initialPattern);
        }

        if (filters != null) {
            for (IFilter filter : filters) {
                pickerDialog.addFilter(filter);
            }
        }

        pickerDialog.open();
        Object[] result = pickerDialog.getResult();
        if (result != null && result.length > 0) {
            return result[0];
        }
        return null;
    }

    /**
     * Similar to the <code>openSinglePickerDialog</code>. The retrieved types
     * are shown to the user on the left side. He can add them to a list on the
     * right side which makes it possible to select multiple parts of the
     * results. If the user has successfully selected an item and confirmed this
     * by pressing the OK Button, the method returns the instances of this
     * selection.
     * 
     * @param shell
     *            The first top shell in the hierarchy.
     * @param initialContent
     *            If set (not null) this String is set to the editField;
     * @param queryResources
     *            If set (not null) only Objects for the given resources will be
     *            considered.
     * @param queryTypes
     *            This parameter describes the types for which the resolved
     *            objects should be searched for.
     * @param contentToExclude
     *            If set (not null) the given Objects will be excluded from the
     *            selection list.
     * 
     * @return Returns the resolved object of a successful selected Item if OK
     *         was pressed. Returns null otherwise.
     * 
     */
    public static Object[] openMultiPickerDialog(Shell shell,
            String initialContent, IResource[] queryResources,
            TypeInfo[] queryTypes, Object[] contentToExclude,
            Object[] contentToPreselect) {
        return openMultiPickerDialog(shell,
                initialContent,
                queryResources,
                queryTypes,
                contentToExclude,
                contentToPreselect,
                null);
    }

    /**
     * Similar to the <code>openSinglePickerDialog</code>. The retrieved types
     * are shown to the user on the left side. He can add them to a list on the
     * right side which makes it possible to select multiple parts of the
     * results. If the user has successfully selected an item and confirmed this
     * by pressing the OK Button, the method returns the instances of this
     * selection.
     * 
     * @param shell
     *            The first top shell in the hierarchy.
     * @param initialContent
     *            If set (not null) this String is set to the editField;
     * @param queryResources
     *            If set (not null) only Objects for the given resources will be
     *            considered.
     * @param queryTypes
     *            This parameter describes the types for which the resolved
     *            objects should be searched for.
     * @param contentToExclude
     *            If set (not null) the given Objects will be excluded from the
     *            selection list.
     * @param filters
     *            An IFilter List containing custom filters. These filter out
     *            TypedElements from being displayed in the picker. Each
     *            filter's select() method is passed an argument of type
     *            TypedItem. The filter can then decide if the TypedItem should
     *            be displayed in the picker.
     * 
     * @return Returns the resolved object of a successful selected Item if OK
     *         was pressed. Returns null otherwise.
     * 
     */
    public static Object[] openMultiPickerDialog(Shell shell,
            String initialContent, IResource[] queryResources,
            TypeInfo[] queryTypes, Object[] contentToExclude,
            Object[] contentToPreselect, List<IFilter> filters) {
        PickerDialog pickerDialog =
                new PickerDialog(shell, queryResources, queryTypes,
                        contentToExclude, contentToPreselect, true);

        if (filters != null) {
            for (IFilter filter : filters) {
                pickerDialog.addFilter(filter);
            }
        }
        if (pickerDialog.open() == pickerDialog.OK) {
            return pickerDialog.getResult();
        }

        return null;
    }

}
