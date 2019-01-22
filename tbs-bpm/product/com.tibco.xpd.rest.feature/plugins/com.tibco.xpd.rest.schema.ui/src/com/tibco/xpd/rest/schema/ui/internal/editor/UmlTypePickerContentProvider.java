package com.tibco.xpd.rest.schema.ui.internal.editor;

import java.util.Collection;

import com.tibco.xpd.resources.ui.picker.BasePickerItemProvider;
import com.tibco.xpd.resources.ui.picker.PickerContentExtension;
import com.tibco.xpd.resources.ui.picker.PickerItem;
import com.tibco.xpd.resources.ui.picker.PickerItemImpl;
import com.tibco.xpd.rest.schema.JsonSchemaUtil;
import com.tibco.xpd.rest.schema.ui.internal.Messages;

/**
 * Picker content provider for the JSON Schema index.
 * 
 * @author nwilson
 * @since 30 Jan 2015
 */
public class UmlTypePickerContentProvider extends BasePickerItemProvider {
    @Override
    public String getIndexerId() {
        return JsonSchemaUtil.JSON_SCHEMA_INDEXER_ID;
    }

    /**
     * Overridden to support JSON String payload type.
     * 
     * @see com.tibco.xpd.resources.ui.picker.BasePickerItemProvider#getItemsForType(com.tibco.xpd.resources.ui.picker.PickerContentExtension,
     *      java.lang.Object, java.lang.String)
     * 
     * @param pickerExtension
     *            The picker extension.
     * @param context
     *            The context.
     * @param type
     *            The item type.
     * @return A collection of applicable picker items.
     */
    @Override
    protected Collection<PickerItem> getItemsForType(
            PickerContentExtension pickerExtension, Object context, String type) {
        PickerItem jsonString =
                new PickerItemImpl(
                        Messages.UmlTypePickerContentProvider_JsonPayloadLabel,
                        JsonSchemaUtil.UNPROCESSED_TEXT_PAYLOAD_REFERENCE, null, null,
                        pickerExtension);
        Collection<PickerItem> content =
                super.getItemsForType(pickerExtension, context, type);
        content.add(jsonString);
        return content;
    }

    /**
     * Overridden to support JSON String payload type.
     * 
     * @see com.tibco.xpd.resources.ui.picker.BasePickerItemProvider#resolvePickerItem(com.tibco.xpd.resources.ui.picker.PickerItem)
     * 
     * @param item
     *            The picker item to resolve.
     * @return The resolved item.
     */
    @Override
    public Object resolvePickerItem(PickerItem item) {
        if (JsonSchemaUtil.UNPROCESSED_TEXT_PAYLOAD_REFERENCE.equals(item.getType())) {
            return JsonSchemaUtil.UNPROCESSED_TEXT_PAYLOAD_REFERENCE;
        }
        return super.resolvePickerItem(item);
    }

}
