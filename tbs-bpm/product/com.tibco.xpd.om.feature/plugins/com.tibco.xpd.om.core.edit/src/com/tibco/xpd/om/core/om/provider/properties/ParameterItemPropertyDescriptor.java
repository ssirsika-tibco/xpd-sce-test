/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.om.core.om.provider.properties;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;

import com.tibco.xpd.om.core.om.Attribute;
import com.tibco.xpd.om.core.om.AttributeType;
import com.tibco.xpd.om.core.om.AttributeValue;
import com.tibco.xpd.om.core.om.EnumValue;
import com.tibco.xpd.om.core.om.OrgTypedElement;
import com.tibco.xpd.om.core.om.provider.OrganisationModelEditPlugin;

/**
 * 
 * <p>
 * <i>Created: 3 Dec 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class ParameterItemPropertyDescriptor implements IItemPropertyDescriptor {

    private final AdapterFactory adapterFactory;
    private final EStructuralFeature feature;
    private final boolean sortChoices;
    private final String category;
    private final String[] filterFlags;
    private final Attribute paramDescriptor;
    private final ParameterLabelProvider labelProvider;

    public ParameterItemPropertyDescriptor(AdapterFactory adapterFactory,
            EStructuralFeature feature, Attribute paramDescriptor) {
        this.adapterFactory = adapterFactory;
        // this.itemDelegator = new ItemDelegator(adapterFactory,
        // resourceLocator);
        this.feature = feature;
        this.paramDescriptor = paramDescriptor;
        this.sortChoices = true;
        this.category = OrganisationModelEditPlugin.getPlugin().getString(
                "_UI_propertyCustomCategory_label"); //$NON-NLS-1$
        this.filterFlags = null;
        this.labelProvider = new ParameterLabelProvider();
    }

    private class ParameterLabelProvider implements IItemLabelProvider {

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.emf.edit.provider.IItemLabelProvider#getImage(java.lang.Object)
         */
        public Object getImage(Object object) {
            return ItemPropertyDescriptor.GENERIC_VALUE_IMAGE;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.emf.edit.provider.IItemLabelProvider#getText(java.lang.Object)
         */
        public String getText(Object object) {
            AttributeValue param = (AttributeValue) object;
            if (param != null) {
                if (isMultiLine(object)) {
                    // comma delimited set of values
                    StringBuilder sb = new StringBuilder(""); //$NON-NLS-1$
                    for (Iterator<?> iter = param.getEnumSetValues().iterator(); iter
                            .hasNext();) {
                        EnumValue enumValue = (EnumValue) iter.next();
                        sb.append(enumValue.getValue());
                        if (iter.hasNext()) {
                            sb.append(", "); //$NON-NLS-1$
                        }
                    }
                    return sb.toString();
                } else {
                    String value = param.getValue();
                    return value == null ? "" : value; //$NON-NLS-1$
                }
            }
            return ""; //$NON-NLS-1$
        }
    }

    /**
     * Returns object's parameter.
     * 
     * @param object
     *            the OrgElement object.
     */
    private AttributeValue getObjectParameter(Attribute descriptor,
            Object object) {
        OrgTypedElement orgElement = (OrgTypedElement) object;
        for (AttributeValue param : orgElement.getAttributeValues()) {
            if (descriptor.equals(param.getAttribute())) {
                return param;
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.edit.provider.IItemPropertyDescriptor#canSetProperty(java.lang.Object)
     */
    public boolean canSetProperty(Object object) {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.edit.provider.IItemPropertyDescriptor#getCategory(java.lang.Object)
     */
    public String getCategory(Object object) {
        return category;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.edit.provider.IItemPropertyDescriptor#getChoiceOfValues(java.lang.Object)
     */
    public Collection<?> getChoiceOfValues(Object object) {
        if (paramDescriptor.getType() == AttributeType.ENUM) {
            return paramDescriptor.getValues();
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.edit.provider.IItemPropertyDescriptor#getDescription(java.lang.Object)
     */
    public String getDescription(Object object) {
        return paramDescriptor.getDisplayName();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.edit.provider.IItemPropertyDescriptor#getDisplayName(java.lang.Object)
     */
    public String getDisplayName(Object object) {
        return paramDescriptor.getDisplayName();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.edit.provider.IItemPropertyDescriptor#getFeature(java.lang.Object)
     */
    public Object getFeature(Object object) {
        return getId(object);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.edit.provider.IItemPropertyDescriptor#getFilterFlags(java.lang.Object)
     */
    public String[] getFilterFlags(Object object) {
        return filterFlags;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.edit.provider.IItemPropertyDescriptor#getHelpContextIds(java.lang.Object)
     */
    public Object getHelpContextIds(Object object) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.edit.provider.IItemPropertyDescriptor#getId(java.lang.Object)
     */
    public String getId(Object object) {
        return paramDescriptor.getDisplayName();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.edit.provider.IItemPropertyDescriptor#getLabelProvider(java.lang.Object)
     */
    public IItemLabelProvider getLabelProvider(Object object) {
        return labelProvider;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.edit.provider.IItemPropertyDescriptor#getPropertyValue(java.lang.Object)
     */
    public Object getPropertyValue(Object object) {
        return getObjectParameter(paramDescriptor, object);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.edit.provider.IItemPropertyDescriptor#isCompatibleWith(java.lang.Object,
     *      java.lang.Object,
     *      org.eclipse.emf.edit.provider.IItemPropertyDescriptor)
     */
    public boolean isCompatibleWith(Object object, Object anotherObject,
            IItemPropertyDescriptor anotherPropertyDescriptor) {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.edit.provider.IItemPropertyDescriptor#isMany(java.lang.Object)
     */
    public boolean isMany(Object object) {
        return paramDescriptor.getType() == AttributeType.ENUM_SET;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.edit.provider.IItemPropertyDescriptor#isMultiLine(java.lang.Object)
     */
    public boolean isMultiLine(Object object) {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.edit.provider.IItemPropertyDescriptor#isPropertySet(java.lang.Object)
     */
    public boolean isPropertySet(Object object) {
        return getPropertyValue(object) == null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.edit.provider.IItemPropertyDescriptor#isSortChoices(java.lang.Object)
     */
    public boolean isSortChoices(Object object) {
        return sortChoices;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.edit.provider.IItemPropertyDescriptor#resetPropertyValue(java.lang.Object)
     */
    public void resetPropertyValue(Object object) {
        // TODO implement
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.edit.provider.IItemPropertyDescriptor#setPropertyValue(java.lang.Object,
     *      java.lang.Object)
     */
    public void setPropertyValue(Object object, Object value) {
        // TODO implement
    }
}
