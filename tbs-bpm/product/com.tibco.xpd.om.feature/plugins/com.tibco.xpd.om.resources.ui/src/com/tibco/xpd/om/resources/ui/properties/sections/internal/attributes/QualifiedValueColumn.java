/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.internal.attributes;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.themes.ColorUtil;

import com.tibco.xpd.om.core.om.Attribute;
import com.tibco.xpd.om.core.om.AttributeType;
import com.tibco.xpd.om.core.om.AttributeValue;
import com.tibco.xpd.om.core.om.OMFactory;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.QualifiedAssociation;
import com.tibco.xpd.om.resources.ui.internal.Messages;

/**
 * Column for the qualified attribute value. This will display an appropriate
 * cell editor depending on attrbute type.
 * 
 * @author njpatel
 * 
 * @param <T>
 *            class that extends {@link QualifiedAssociation}
 */
public abstract class QualifiedValueColumn<T extends QualifiedAssociation>
        extends AttributeValueSettingColumn {

    private Color disableColor;

    /**
     * Column for the qualified attribute value.
     * 
     * @param editingDomain
     * @param viewer
     *            column viewer
     * @param label
     *            column label
     * @param width
     *            initial column width
     */
    public QualifiedValueColumn(EditingDomain editingDomain,
            ColumnViewer viewer, String label, int width) {
        super(editingDomain, viewer,
                Messages.QualifiedAssociationTableSection_valueColumn_title,
                200);
    }

    /**
     * Get the qualifier {@link Attribute} of this association.
     * 
     * @param element
     *            qualified association
     * @return {@link Attribute}
     */
    protected abstract Attribute getQualifier(T element);

    @Override
    protected AttributeValue getAttributeValue(Object element) {
        if (element instanceof QualifiedAssociation) {
            return ((QualifiedAssociation) element).getQualifierValue();
        }
        return null;
    }

    @Override
    protected void dispose() {
        if (disableColor != null) {
            disableColor.dispose();
            disableColor = null;
        }
        super.dispose();
    }

    @Override
    protected Command getRemoveAttributeValueCommand(EditingDomain ed,
            Object element, AttributeValue value) {
        if (element instanceof QualifiedAssociation) {
            return RemoveCommand.create(ed,
                    ((QualifiedAssociation) element).getQualifierValue());
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Command getSetCommand(Object element, Object value) {
        Command cmd = null;
        EditingDomain ed = getEditingDomain();
        /*
         * If value is null then set to empty string - this will indicate that
         * the user wishes to unset the value and not use the default value
         */
        if (value == null) {
            value = ""; //$NON-NLS-1$
        }
        if (element instanceof QualifiedAssociation) {
            AttributeValue qualifierValue =
                    ((QualifiedAssociation) element).getQualifierValue();
            if (qualifierValue != null) {
                Attribute attr = qualifierValue.getAttribute();

                if (attr != null) {
                    CompoundCommand ccmd = new CompoundCommand();
                    if (attr.getType() == AttributeType.ENUM
                            || attr.getType() == AttributeType.ENUM_SET) {
                        /*
                         * Unset the value and set the enum set
                         */
                        if (qualifierValue.getValue() != null) {
                            ccmd.append(SetCommand.create(ed,
                                    qualifierValue,
                                    OMPackage.eINSTANCE
                                            .getAttributeValue_Value(),
                                    SetCommand.UNSET_VALUE));
                        }

                        // If a single enumvalue is passed in then add to a
                        // collection
                        if (!(value instanceof Collection<?>)) {
                            value = Collections.singletonList(value);
                        }

                        ccmd.append(SetCommand.create(ed,
                                qualifierValue,
                                OMPackage.eINSTANCE
                                        .getAttributeValue_EnumSetValues(),
                                value));
                    } else if (attr.getType() == AttributeType.SET) {
                        /*
                         * Unset the value and set the set
                         */
                        if (qualifierValue.getValue() != null) {
                            ccmd.append(SetCommand.create(ed,
                                    qualifierValue,
                                    OMPackage.eINSTANCE
                                            .getAttributeValue_Value(),
                                    SetCommand.UNSET_VALUE));
                        }

                        // If a single value is passed in then add to a
                        // collection
                        if (!(value instanceof Collection<?>)) {
                            value = Collections.singletonList(value);
                        }

                        ccmd.append(SetCommand.create(ed,
                                qualifierValue,
                                OMPackage.eINSTANCE
                                        .getAttributeValue_SetValues(),
                                value));
                    } else {
                        /*
                         * Unset the enum set and set the value
                         */
                        if (!qualifierValue.getEnumSetValues().isEmpty()) {
                            ccmd.append(RemoveCommand.create(ed,
                                    qualifierValue,
                                    OMPackage.eINSTANCE
                                            .getAttributeValue_EnumSetValues(),
                                    qualifierValue.getEnumSetValues()));
                        }
                        ccmd.append(SetCommand.create(ed,
                                qualifierValue,
                                OMPackage.eINSTANCE.getAttributeValue_Value(),
                                value));
                    }
                    cmd = ccmd;
                }

            } else {
                Attribute attr = getQualifier((T) element);
                if (attr != null) {
                    qualifierValue = OMFactory.eINSTANCE.createAttributeValue();
                    qualifierValue.setAttribute(attr);
                    if (attr.getType() == AttributeType.ENUM
                            || attr.getType() == AttributeType.ENUM_SET
                            || attr.getType() == AttributeType.SET) {
                        if (value != null && !(value instanceof Collection<?>)) {
                            value = Collections.singletonList(value);
                        }
                        qualifierValue
                                .eSet(attr.getType() == AttributeType.SET ? OMPackage.eINSTANCE
                                        .getAttributeValue_SetValues()
                                        : OMPackage.eINSTANCE
                                                .getAttributeValue_EnumSetValues(),
                                        value);
                    } else {
                        if (value != null) {
                            qualifierValue.setValue(value.toString());
                        }
                    }
                    cmd =
                            SetCommand
                                    .create(ed,
                                            element,
                                            OMPackage.eINSTANCE
                                                    .getQualifiedAssociation_QualifierValue(),
                                            qualifierValue);
                }
            }
        }
        return cmd;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Object getValue(Object element) {
        if (element instanceof QualifiedAssociation) {
            AttributeValue value =
                    ((QualifiedAssociation) element).getQualifierValue();

            if (value != null) {
                if (value.getAttribute() != null) {
                    Attribute attr = value.getAttribute();

                    if (attr.getType() != null) {
                        switch (attr.getType()) {
                        case ENUM:
                        case ENUM_SET:
                            return value.getEnumSetValues();
                        case SET:
                            return value.getSetValues();
                        default:
                            return value.getValue();
                        }
                    }
                }
            } else {
                // Get the default value of the qualifier if one is
                // set
                Attribute qualifier = getQualifier((T) element);
                if (qualifier != null && qualifier.getType() != null) {
                    switch (qualifier.getType()) {
                    case ENUM:
                    case ENUM_SET:
                        return qualifier.getDefaultEnumSetValues();
                    default:
                        return qualifier.getDefaultValue();
                    }
                }
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected String getText(Object element) {
        String text = null;

        if (getQualifier((T) element) == null) {
            text =
                    Messages.QualifiedAssociationTableSection_noQualifierSet_label;
        } else {
            text = super.getText(element);
        }
        return text != null ? text : ""; //$NON-NLS-1$
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Color getForeground(Object element) {
        if (element instanceof QualifiedAssociation) {
            Attribute attr = getQualifier((T) element);
            if (attr == null) {
                if (disableColor == null) {
                    Color foreground =
                            getColumn().getViewer().getControl()
                                    .getForeground();
                    disableColor =
                            new Color(foreground.getDevice(),
                                    ColorUtil.blend(foreground.getRGB(),
                                            ColorConstants.white.getRGB(),
                                            40));
                }
                return disableColor;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Attribute getAttribute(Object input) {
        if (input instanceof QualifiedAssociation) {
            return getQualifier((T) input);
        }
        return super.getAttribute(input);
    }
}
