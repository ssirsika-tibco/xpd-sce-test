/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.resources.ui.picker;

import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.Assert;

/**
 * Query specifying the picker content extension (
 * <code>com.tibco.xpd.resources.ui.pickerContent</code>) and list of its items'
 * types. It's used by {@link CommonPickerDialog} to specify what type of
 * content ({@link PickerItem}s) should be shown.
 * <p/>
 * It is possible to additionally provide a context object allowing to specify
 * additional information which can then be used by extension's ContentProvider.
 * This can be used to optimize queries especially if the content is not taken
 * from an indexer. It should not be necessary to use this facility in most
 * cases as this task can usually be done by using filters for
 * {@link CommonPickerDialog} . See set of commonly used filters in
 * <code>com.tibco.xpd.resources.ui.picker.filters</code> package.
 * 
 * @since 3.3.1
 * @author Jan Arciuchiewicz
 */
public class PickerTypeQuery {

    private final PickerContentExtension pickerExtension;

    private final List<String> types;

    private final Object context;

    /**
     * Creates a query.
     * 
     * @param pickerExtensionId
     *            id of an extension.
     * @param types
     *            the list of types known to extension.
     */
    public PickerTypeQuery(String pickerExtensionId, String... types) {
        PickerContentExtension provider =
                PickerService.getInstance()
                        .getPickerContentExtension(pickerExtensionId);
        Assert.isNotNull(provider, String
                .format("Provider id '%s' is invalid.", pickerExtensionId)); //$NON-NLS-1$
        this.pickerExtension = provider;
        this.types = Arrays.asList(types);
        this.context = null;
    }

    /**
     * Creates a query.
     * 
     * @param pickerExtensionId
     *            id of an extension.
     * @param types
     *            the list of types known to extension.
     */
    public PickerTypeQuery(PickerContentExtension pickerExtension,
            String... types) {
        Assert.isNotNull(pickerExtension, String
                .format("Provider is invalid. (Should not be 'null')")); //$NON-NLS-1$
        this.pickerExtension = pickerExtension;
        this.types = Arrays.asList(types);
        this.context = null;
    }

    /**
     * Creates a query.
     * 
     * @param pickerExtension
     *            content providing extension.
     * @parem context context object providing additional information which can
     *        be used by extension's ContentProvider or <code>null</code>.
     * @param types
     *            list of types known to extension.
     */
    public PickerTypeQuery(PickerContentExtension pickerExtension,
            Object context, String... types) {
        Assert.isNotNull(pickerExtension, String
                .format("Provider is invalid. (Should not be 'null')")); //$NON-NLS-1$
        this.pickerExtension = pickerExtension;
        this.types = Arrays.asList(types);
        this.context = context;
    }

    /**
     * Gets the content provider extension.
     * 
     * @return the content provider extension.
     */
    public PickerContentExtension getPickerExtension() {
        return pickerExtension;
    }

    /**
     * Gets the list of types for this query.
     * 
     * @return the types.
     */
    public List<String> getTypes() {
        return types;
    }

    /**
     * Gets context object.
     * 
     * @return the context.
     */
    public Object getContext() {
        return context;
    }
}
