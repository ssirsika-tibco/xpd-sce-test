/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */
package com.tibco.xpd.rsd.ui.components;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.resources.ui.picker.PickerItem;
import com.tibco.xpd.resources.ui.picker.PickerService;
import com.tibco.xpd.resources.ui.picker.PickerTypeQuery;
import com.tibco.xpd.rsd.RsdPackage;
import com.tibco.xpd.rsd.ui.internal.Messages;

import io.swagger.v3.oas.models.Operation;

/**
 * A custom picker class to allow the selection of a REST Method from a REST Service Descriptor or a Swagger
 * specification
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
	 * Picker extension ID for Swagger specific picker content
	 */
	private static final String	SWAGGER_PICKER_EXTENSION_ID	= 
			"com.tibco.xpd.rest.swagger.method.picker"; //$NON-NLS-1$
    /**
     * Rest method type used by indexer.
     */
    public static final String METHOD_TYPE = RsdPackage.eINSTANCE.getMethod()
            .getInstanceTypeName();

	/**
	 * Method Type used by the Swagger indexer
	 */
	public static final String	SWAGGER_METHOD_TYPE	= Operation.class.getName();

    /**
     * @param control
     * @return
     */
	public PickerItem pickRestMethod(Shell shell)
	{
        String title = Messages.RestMethodPicker_Dialog_title;
		PickerItem pickerItem = null;
        PickerTypeQuery typeQuery =
                new PickerTypeQuery(PICKER_EXTENSION_ID, METHOD_TYPE);
		PickerTypeQuery swaggerTypeQuery = new PickerTypeQuery(SWAGGER_PICKER_EXTENSION_ID, SWAGGER_METHOD_TYPE);
		PickerTypeQuery[] typeQueries = new PickerTypeQuery[]{typeQuery, swaggerTypeQuery};
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
		// Nikita-8267 The picker dialog can now return two types - Method and Operation
		// To generalize here and handle the differences between the two in RestServiceTasAdapter, return the PickerItem
		// rather than specific types
		if (picked instanceof PickerItem)
		{
			pickerItem = (PickerItem) picked;
		}
		return pickerItem;
    }
}
