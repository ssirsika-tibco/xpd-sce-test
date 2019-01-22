/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */
package com.tibco.xpd.rsd.ui.components;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.resources.ui.picker.PickerService;
import com.tibco.xpd.resources.ui.picker.PickerTypeQuery;
import com.tibco.xpd.rsd.Method;
import com.tibco.xpd.rsd.RsdPackage;
import com.tibco.xpd.rsd.ui.internal.Messages;

/**
 * A custom picker class to allow the selection of a REST Method. from a REST
 * Service Descriptor.
 * 
 * @author nwilson
 * @since 23 Feb 2015
 */
public class RestMethodPicker {

    /**
     * Picker extension ID.
     */
    private static final String PICKER_EXTENSION_ID =
            "com.tibco.xpd.rsd.method.picker"; //$NON-NLS-1$

    /**
     * Rest method type used by indexer.
     */
    public static final String METHOD_TYPE = RsdPackage.eINSTANCE.getMethod()
            .getInstanceTypeName();

    /**
     * @param control
     * @return
     */
    public Method pickRestMethod(Shell shell) {
        String title = Messages.RestMethodPicker_Dialog_title;
        Method method = null;
        PickerTypeQuery typeQuery =
                new PickerTypeQuery(PICKER_EXTENSION_ID, METHOD_TYPE);
        PickerTypeQuery[] typeQueries = new PickerTypeQuery[] { typeQuery };
        Object contentToPreselect = null;
        IResource[] queryResources = null;
        IFilter[] filters = new IFilter[0];
        String initialPattern = null;
        Object[] contentToExclude = null;
        Object picked =
                PickerService.getInstance().openSinglePickerDialog(title,
                        shell,
                        typeQueries,
                        initialPattern,
                        queryResources,
                        contentToExclude,
                        filters,
                        contentToPreselect);
        if (picked instanceof Method) {
            method = (Method) picked;
        }
        return method;
    }
}
