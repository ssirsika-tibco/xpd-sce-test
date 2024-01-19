/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.implementer.nativeservices.databaseservice.properties.table;

import java.util.Comparator;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.analyst.resources.xpdl2.pickers.IDataPickerProxyItem;
import com.tibco.xpd.analyst.resources.xpdl2.providers.DataFilterPickerProviderHelper;
import com.tibco.xpd.implementer.nativeservices.NativeServicesActivator;
import com.tibco.xpd.implementer.nativeservices.NativeServicesConsts;
import com.tibco.xpd.implementer.nativeservices.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.pickers.DBDataFilterPicker;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;

/**
 * Overrides DBDataFilterPicker and provides the ability to generate a result
 * parameter and associated BOM type.
 * 
 * @author NWilson
 * 
 */
public class DBDataFilterResponsePicker extends DBDataFilterPicker {

    private IDataPickerProxyItem createItem;

    public DBDataFilterResponsePicker(Shell shell, DataPickerType type,
            boolean multi) {
        super(shell, type, multi);
        URI imageURI =
                URI.createPlatformPluginURI(NativeServicesActivator.PLUGIN_ID
                        + "/" //$NON-NLS-1$
                        + NativeServicesConsts.IMG_BOM, true);

        createItem =
                DataFilterPickerProviderHelper.getInstance().new DataPickerItem(
                        Messages.DBDataFilterResponsePicker_CreateNewParameterMessage,
                        null, imageURI, new ConceptPath(
                                new ReturnTypeGenerator(), null));
    }

    @Override
    protected Set<IDataPickerProxyItem> getExtraItems() {
        Set<IDataPickerProxyItem> items = super.getExtraItems();
        items.add(createItem);
        return items;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Comparator<?> getItemsComparator() {
        final Comparator<IDataPickerProxyItem> parent =
                super.getItemsComparator();
        Comparator<?> newComparator = new Comparator<IDataPickerProxyItem>() {

            public int compare(IDataPickerProxyItem o1, IDataPickerProxyItem o2) {
                int value = 0;
                if (o1 == createItem) {
                    value = 1;
                } else if (o2 == createItem) {
                    value = -1;
                } else {
                    value = parent.compare(o1, o2);
                }
                return value;
            }

        };
        return newComparator;
    }

}
