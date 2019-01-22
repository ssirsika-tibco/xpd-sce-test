/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.pickers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.om.resources.ui.types.OMTypesFactory;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.ui.picker.PickerService;
import com.tibco.xpd.resources.ui.types.TypeInfo;
import com.tibco.xpd.resources.ui.types.TypeProvider;
import com.tibco.xpd.resources.ui.types.TypedItem;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Convenience util for selection pickers based on the indexer.
 * 
 * @author njpatel
 * @deprecated Use {@link PickerService} instead.
 */
@Deprecated
public final class OMPicker {

    /**
     * Open a single element picker for the {@link TypeInfo info} type provided.
     * The picker will include items only from the resource of the given input
     * object.
     * 
     * @param shell
     *            parent shell
     * @param initialSearchPattern
     *            initial search pattern or <code>null</code> if not required.
     * @param input
     *            use this input's resource for picker input
     * @param info
     *            type of object to show in the picker
     * @param itemsToExclude
     *            array of items to exclude, <code>null</code> if include all
     *            objects.
     * 
     * @see OMTypesFactory
     * 
     * @return item selected from the picker.
     */
    public static Object openSingleElementPicker(Shell shell,
            String initialSearchPattern, EObject input, TypeInfo info,
            Object[] itemsToExclude) {
        Object selection = null;
        if (input != null && info != null) {
            info.setData(input.eClass());
            IResource resourceToSearch = null;
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(input);

            if (wc != null && wc.getEclipseResources() != null) {
                resourceToSearch = wc.getEclipseResources().get(0);
            }

            if (resourceToSearch != null) {
                List<IFilter> filters = new ArrayList<IFilter>();
                filters.add(new ResourceFilter(resourceToSearch));

                selection =
                        TypeProvider.openSinglePickerDialog(shell,
                                initialSearchPattern,
                                new IResource[] { resourceToSearch },
                                new TypeInfo[] { info },
                                itemsToExclude,
                                filters);
            } else {
                selection =
                        TypeProvider.openSinglePickerDialog(shell,
                                initialSearchPattern,
                                null,
                                new TypeInfo[] { info },
                                itemsToExclude);
            }
        }
        return selection;

    }

    /**
     * Open a multi-selection element picker for the {@link TypeInfo info} type
     * provided. The picker will include items only from the resource of the
     * given input object.
     * 
     * @param shell
     *            parent shell
     * @param initialSearchPattern
     *            initial search pattern or <code>null</code> if not required.
     * @param input
     *            use this input's resource for picker input
     * @param info
     *            type of object to show in the picker
     * @param itemsToExclude
     *            array of items to exclude, <code>null</code> if include all
     *            objects.
     * @param itemsToPreselect
     *            array of items to preselect in the picker, <code>null</code>
     *            if none should be preselected.
     * @see OMTypesFactory
     * 
     * @return objects selected in the picker.
     */
    public static Object[] openMultipleElementPicker(Shell shell,
            String initialSearchPattern, EObject input, TypeInfo info,
            Object[] itemsToExclude, Object[] itemsToPreselect) {
        Object[] selection = null;

        if (input != null && info != null) {
            info.setData(input.eClass());
            IResource resourceToSearch = null;
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(input);

            if (wc != null && wc.getEclipseResources() != null) {
                resourceToSearch = wc.getEclipseResources().get(0);
            }

            if (resourceToSearch != null) {
                List<IFilter> filters = new ArrayList<IFilter>();
                filters.add(new ResourceFilter(resourceToSearch));

                selection =
                        TypeProvider.openMultiPickerDialog(shell,
                                initialSearchPattern,
                                new IResource[] { resourceToSearch },
                                new TypeInfo[] { info },
                                itemsToExclude,
                                itemsToPreselect,
                                filters);
            }
        }
        return selection;

    }

    private static class ResourceFilter implements IFilter {
        private final URI resUri;

        public ResourceFilter(IResource res) {
            resUri =
                    URI.createPlatformResourceURI(res.getFullPath().toString(),
                            true);
        }

        public boolean select(Object toTest) {
            if (toTest instanceof TypedItem) {
                String uriStr = ((TypedItem) toTest).getUriString();
                if (uriStr != null) {
                    URI uri = URI.createURI(uriStr);

                    return uri.trimFragment().equals(resUri);
                }

            }
            return false;
        }

    }

}
