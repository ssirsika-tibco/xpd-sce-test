package com.tibco.xpd.rest.schema.ui.internal.editor;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.PrimitiveType;

import com.tibco.xpd.bom.resources.ui.commonpicker.BOMTypeQuery;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.picker.BasePickerLabelProvider;
import com.tibco.xpd.resources.ui.picker.PickerItem;
import com.tibco.xpd.rest.schema.JsonSchemaUtil;
import com.tibco.xpd.rest.schema.internal.JsonSchemaIndexProvider;
import com.tibco.xpd.rest.schema.ui.internal.RestSchemaImage;
import com.tibco.xpd.rest.schema.ui.internal.RestSchemaUiPlugin;

/**
 * Picker label provider to add images to UML types.
 * 
 * @author nwilson
 * @since 23 Jan 2015
 */
public class UmlTypePickerLabelProvider extends BasePickerLabelProvider {

    /**
     * @see com.tibco.xpd.resources.ui.picker.BasePickerLabelProvider
     *      #getImage(com.tibco.xpd.resources.ui.picker.PickerItem)
     * 
     * @param pickerItem
     * @return
     */
    @Override
    public Image getImage(PickerItem pickerItem) {
        Image image = null;
        RestSchemaImage key = null;
        RestSchemaUiPlugin plugin = RestSchemaUiPlugin.getDefault();
        String type = pickerItem.getType();
        if (BOMTypeQuery.BASE_PRIMITIVE_TYPE.equals(type)) {
            String name = ""; //$NON-NLS-1$

            String struri = pickerItem.getURI();
            URI uri = URI.createURI(struri);

            if (uri != null) {
                EObject eObject =
                        XpdResourcesPlugin.getDefault().getEditingDomain()
                                .getResourceSet().getEObject(uri, true);
                if (eObject instanceof PrimitiveType) {
                    name = ((PrimitiveType) eObject).getName();
                }
            }
            switch (name) {
            case PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME:
                key = RestSchemaImage.JSON_STRING_PROPERTY;
                break;
            case PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME:
                key = RestSchemaImage.JSON_BOOLEAN_PROPERTY;
                break;
            case PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME:
            case PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME:
            case PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME:
                key = RestSchemaImage.JSON_DATE_TIME_PROPERTY;
                break;
            case PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME:
                key = RestSchemaImage.JSON_NUMBER_PROPERTY;
                break;
            case PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME:
                key = RestSchemaImage.JSON_INTEGER_PROPERTY;
                break;
            }
        } else if (Class.class.getName().equals(type)) {
            if (Boolean.TRUE
                    .toString()
                    .equalsIgnoreCase(pickerItem.get(JsonSchemaIndexProvider.IS_ROOT))) {
                key = RestSchemaImage.JSON_ROOT_CLASS_OBJECT;
            } else {
                key = RestSchemaImage.JSON_CLASS_OBJECT;
            }

        } else if (JsonSchemaUtil.UNPROCESSED_TEXT_PAYLOAD_REFERENCE
                .equals(type)) {
            key = RestSchemaImage.IMG_UNPROCESSED_TEXT_TYPE;
        }

        if (key == null) {
            key = RestSchemaImage.JSON_BASE_PROPERTY;
        }
        image = plugin.getImage(key);
        return image;
    }

}
