/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.mappings;

import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.implementer.resources.xpdl2.Activator;
import com.tibco.xpd.implementer.resources.xpdl2.ImageConstants;
import com.tibco.xpd.implementer.resources.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.RestConceptPath;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.rest.schema.ui.internal.editor.UmlJsonSchemaLabelProvider;
import com.tibco.xpd.rsd.DataType;
import com.tibco.xpd.rsd.Parameter;
import com.tibco.xpd.rsd.ui.RsdImage;
import com.tibco.xpd.rsd.ui.components.columns.ParamDataTypeColumn.RestDataTypeUtil;

/**
 * Label provider for REST Data Mapper tree items.
 * 
 * @author jarciuch
 * @since 26 Mar 2015
 */
public class RestMapperLabelProvider extends LabelProvider {

    /**
     * Label provider for UML tree elements.
     */
    private final UmlJsonSchemaLabelProvider jsonSchemaLabelProvider;

    /**
     * Constructor.
     */
    public RestMapperLabelProvider() {
        jsonSchemaLabelProvider = new UmlJsonSchemaLabelProvider();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getText(Object element) {
        String text = ""; //$NON-NLS-1$
        if (element instanceof UnprocessedTextRestMapperTreeItem) {
            text = Messages.RestMapperLabelProvider_UnprocessedText_label;

        } else if (element instanceof RestMapperTreeItem) {

            text = ((RestMapperTreeItem) element).getText();

            if (element instanceof RestParamTreeItem) {
                /*
                 * XPD-7448: append the human readable data type to the text.
                 */
                Parameter param = ((RestParamTreeItem) element).getParam();

                if (param != null) {

                    DataType dataType = param.getDataType();

                    String primitiveType = getPrimitiveType(dataType);

                    /*
                     * ACE-1486: Need to make sure that we display "Number" in
                     * the data mapper UI for "Decimal" type mapper items.
                     */
                    if (PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME
                            .equals(primitiveType)) {
                        primitiveType =
                                RestDataTypeUtil.REST_NUMBER_DATA_TYPE_LABEL;
                    }

                    if (primitiveType != null) {

                        text += " : " + primitiveType; //$NON-NLS-1$
                    }
                }
            }
        } else if (element instanceof ConceptPath) {
            Object item = ((ConceptPath) element).getItem();
            if (item instanceof RestPayloadContainerTreeItem) {
                text =
                        jsonSchemaLabelProvider.getText(((ConceptPath) element)
                                .getType());
            } else {
                text = jsonSchemaLabelProvider.getText(item);
            }
            if (element instanceof RestConceptPath) {
                RestConceptPath rcp = (RestConceptPath) element;
                if (rcp.isArray()) {
                    text += " []"; //$NON-NLS-1$
                }
            }
        } else {
            text = super.getText(element);
        }
        return text;
    }

    /**
     * Converts a RSD DataType into the matching BOM primitive type name.
     * 
     * @param dataType
     *            The RSD dataType.
     * @return The matching BOM type name.
     */
    public static String getPrimitiveType(DataType dataType) {
        String type = null;
        switch (dataType) {
        case TEXT:
            type = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            break;
        case INTEGER:
            type = PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME;
            break;
        case DECIMAL:
            type = PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME;
            break;
        case BOOLEAN:
            type = PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME;
            break;
        case DATE:
            type = PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME;
            break;
        case TIME:
            type = PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME;
            break;
        case DATE_TIME:
            type = PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME;
            break;
        }
        return type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Image getImage(Object element) {
        ImageRegistry imageRegistry = Activator.getDefault().getImageRegistry();
        if (element instanceof RestParamContainerTreeItem) {
            return imageRegistry.get(ImageConstants.PART);
        } else if (element instanceof RestPayloadContainerTreeItem) {
            return imageRegistry.get(ImageConstants.PART);
        } else if (element instanceof RestParamTreeItem) {
            Parameter param = ((RestParamTreeItem) element).getParam();
            if (param != null) {
                return WorkingCopyUtil.getImage(param);
            }
        } else if (element instanceof UnprocessedTextRestMapperTreeItem) {
            return RsdImage.getImage((RsdImage.IMG_UNPROCESSED_TEXT_TYPE));

        } else if (element instanceof ConceptPath) {
            Object item = ((ConceptPath) element).getItem();
            if (item instanceof RestPayloadContainerTreeItem) {
                return jsonSchemaLabelProvider.getImage(((ConceptPath) element)
                        .getType());
            }
            return jsonSchemaLabelProvider.getImage(item);
        }
        return super.getImage(element);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose() {
        jsonSchemaLabelProvider.dispose();
        super.dispose();
    }
}
