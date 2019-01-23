/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.general;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.PolicySetReference;
import com.tibco.xpd.resources.ui.picker.BasePickerItemProvider;
import com.tibco.xpd.resources.ui.picker.BasePickerLabelProvider;
import com.tibco.xpd.resources.ui.picker.CommonPickerDialog;
import com.tibco.xpd.resources.ui.picker.PickerItem;
import com.tibco.xpd.resources.ui.picker.PickerTypeQuery;

/**
 * Utility class for picking references to the policy set from index using a
 * picker.
 * 
 * @author jarciuch
 * @since 3.5.3
 */
/* package */class PolicySetPickerUtil {

    /**
     * Id of the PolicySet picker content provider.
     */
    public static final String POLICY_SET_PICKER_CONTENT_EXTENSION =
            "com.tibco.xpd.resources.ui.policySetPickerRefContentProvider"; //$NON-NLS-1$

    /**
     * Policy set indexer id. This is a copy of
     * {@link com.tibco.amf.tools.composite.resources.internal.indexer.PolicySetsIndexProvider#POLICY_SETS_INDEX}
     */
    public static final String POLICY_SETS_INDEX = "policysets.index"; //$NON-NLS-1$

    public static final String POLICY_SETS_TYPE = "policyset"; //$NON-NLS-1$

    public static final String POLICY_SETS_FILE_TYPE = "policysets.file"; //$NON-NLS-1$

    public static final PickerTypeQuery POLICY_SET_TYPE_QUERY =
            new PickerTypeQuery(POLICY_SET_PICKER_CONTENT_EXTENSION,
                    POLICY_SETS_TYPE);

    /**
     * Provides items for the picker from PolicySet indexer.
     */
    public static class PolicyPickerItemProvider extends BasePickerItemProvider {
        /**
         * Gets PolicySet indexer id.
         */
        @Override
        protected String getIndexerId() {
            return POLICY_SETS_INDEX;
        }

        /**
         * Resolves PickerItem to PolicySetReference.
         */
        @Override
        public Object resolvePickerItem(PickerItem item) {
            return PolicySetReference.getPolicySetReference(item);
        }
    }

    /**
     * Provides labels for PickerItems representing PolicySet objects.
     */
    public static class PolicyPickerLabelProvider extends
            BasePickerLabelProvider {
    }

    /**
     * Opens picker dialog for picking all policies in the workspace. Will not
     * include policies from default hidden (starting with '.' folders.)
     * folders.
     * 
     * @param shell
     *            the parent shell.
     * @return the PolicySetReference representing selected object or 'null' if
     *         user pressed cancel.
     */
    public static PolicySetReference browsePolicySetReference(Shell shell) {
        List<IFilter> itemFilters =
                Arrays.asList((IFilter) new HiddenResourcesFilter());
        CommonPickerDialog pickerDialog =
                new CommonPickerDialog(shell, false, Collections.emptyList(),
                        itemFilters,
                        new PickerTypeQuery[] { POLICY_SET_TYPE_QUERY });
        pickerDialog.setTitle(Messages.PolicySetPickerUtil_PolicyPicker_title);
        pickerDialog
                .setMessage(Messages.PolicySetPickerUtil_PolicyPicker_message);
        pickerDialog.open();
        Object[] result = pickerDialog.getResult();
        if (result != null && result.length > 0) {
            return (PolicySetReference) result[0];
        }
        return null;
    }

    /**
     * Filter excludes objects coming from hidden folders. (Starting with '.'.)
     * 
     * @since 3.5.3
     * @author Jan Arciuchiewicz
     */
    public static class HiddenResourcesFilter implements IFilter {

        /**
         * Creates a new filter.
         */
        public HiddenResourcesFilter() {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean select(Object toTest) {
            if (toTest instanceof PickerItem) {
                String uriString = ((PickerItem) toTest).getURI();
                if (uriString != null) {
                    URI uri = URI.createURI(uriString);
                    if (uri != null) {
                        String platformStr = uri.toPlatformString(true);
                        if (platformStr != null) {
                            Path path = new Path(platformStr);
                            for (String segment : path.segments()) {
                                if (segment.startsWith(".")) { //$NON-NLS-1$
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
            return true;
        }
    }
}
