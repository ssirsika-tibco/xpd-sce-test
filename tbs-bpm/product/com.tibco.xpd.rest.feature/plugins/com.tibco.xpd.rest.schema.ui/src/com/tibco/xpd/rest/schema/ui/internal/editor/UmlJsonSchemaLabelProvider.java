/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.schema.ui.internal.editor;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.TypedElement;

import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.rest.schema.JsonSchemaUtil;
import com.tibco.xpd.rest.schema.ui.internal.RestSchemaImage;
import com.tibco.xpd.rest.schema.ui.internal.RestSchemaUiPlugin;

/**
 * Label provider for JSON Schema UML elements.
 * 
 * @author nwilson
 * @since 18 Feb 2015
 */
public class UmlJsonSchemaLabelProvider implements ILabelProvider {

    /**
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
     * 
     * @param listener
     */
    @Override
    public void addListener(ILabelProviderListener listener) {
    }

    /**
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
     * 
     */
    @Override
    public void dispose() {
    }

    /**
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object,
     *      java.lang.String)
     * 
     * @param element
     * @param property
     * @return
     */
    @Override
    public boolean isLabelProperty(Object element, String property) {
        return false;
    }

    /**
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
     * 
     * @param listener
     */
    @Override
    public void removeListener(ILabelProviderListener listener) {
    }

    /**
     * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public Image getImage(Object element) {
        Image image = null;
        Object el = getElement(element);
        RestSchemaUiPlugin plugin = RestSchemaUiPlugin.getDefault();
        RestSchemaImage key = null;
        boolean isArray = false;
        if (el instanceof Class) {
            Class cls = (Class) el;
            if (new JsonSchemaUtil().isRootClass(cls)) {
                key = RestSchemaImage.JSON_ROOT_CLASS_OBJECT;
            } else {
                key = RestSchemaImage.JSON_CLASS_OBJECT;
            }
        } else if (el instanceof Property) {
            Property property = (Property) el;
            el = property.getType();
            isArray = property.getUpper() == LiteralUnlimitedNatural.UNLIMITED;
        }
        if (key == null && el instanceof Type) {
            Type type = (Type) el;
            if (isArray) {
                // Array
                if (type instanceof Class) {
                    key = RestSchemaImage.JSON_CLASS_ARRAY_PROPERTY;
                } else if (type instanceof PrimitiveType) {
                    String name = type.getName();
                    switch (name) {
                    case PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME:
                        key = RestSchemaImage.JSON_STRING_ARRAY_PROPERTY;
                        break;
                    case PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME:
                        key = RestSchemaImage.JSON_BOOLEAN_ARRAY_PROPERTY;
                        break;
                    case PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME:
                    case PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME:
                    case PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME:
                        key = RestSchemaImage.JSON_DATE_TIME_ARRAY_PROPERTY;
                        break;
                    case PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME:
                        key = RestSchemaImage.JSON_NUMBER_ARRAY_PROPERTY;
                        break;
                    case PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME:
                        key = RestSchemaImage.JSON_INTEGER_ARRAY_PROPERTY;
                        break;
                    }
                }
            } else {
                if (type instanceof Class) {
                    key = RestSchemaImage.JSON_CLASS_PROPERTY;
                } else if (type instanceof PrimitiveType) {
                    String name = type.getName();
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
                }
            }
        }
        if (key == null) {
            key = RestSchemaImage.JSON_BASE_PROPERTY;
        }
        image = plugin.getImage(key);
        return image;
    }

    /**
     * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public String getText(Object element) {
        Object el = getElement(element);
        StringBuilder text = new StringBuilder();
        if (el instanceof NamedElement) {
            NamedElement ne = (NamedElement) el;
            /*
             * ACE-1486: "Decimal" is termed as "Number" in ACE.
             */
            if (PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME
                    .equals(ne.getName())) {
                text.append(PrimitivesUtil.BOM_PRIMITIVE_NUMBER_NAME);
            } else {
                text.append(ne.getName());
            }

            if (ne instanceof TypedElement) {
                TypedElement te = (TypedElement) ne;
                Type type = te.getType();
                if (type != null) {
                    text.append(" : "); //$NON-NLS-1$
                    text.append(type.getLabel());
                }
            }
        }
        if (el instanceof MultiplicityElement) {
            MultiplicityElement me = (MultiplicityElement) el;
            if (me.getUpper() == LiteralUnlimitedNatural.UNLIMITED) {
                text.append(" []"); //$NON-NLS-1$
            }
        }
        return text.toString();
    }

    /**
     * Converts StucturedSelection and UmlTreePropertyNode to model objects.
     * 
     * @param element
     *            The element to get a label for.
     * @return The corresponding model object.
     */
    protected Object getElement(Object element) {
        Object el = element;
        if (el instanceof StructuredSelection) {
            StructuredSelection ss = (StructuredSelection) el;
            if (ss.size() == 1) {
                el = ss.getFirstElement();
            }
        }
        if (el instanceof UmlTreePropertyNode) {
            el = ((UmlTreePropertyNode) el).getItem();
        }
        return el;
    }

}
