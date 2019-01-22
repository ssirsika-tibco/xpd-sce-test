/**
 */
package com.tibco.xpd.rsd.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import com.tibco.xpd.rsd.DataType;
import com.tibco.xpd.rsd.Parameter;
import com.tibco.xpd.rsd.RsdPackage;
import com.tibco.xpd.rsd.ui.RsdImage;

/**
 * This is the item provider adapter for a {@link com.tibco.xpd.rsd.Parameter} object.
 * <!-- begin-user-doc --> <!-- end-user-doc -->
 * @generated
 */
public class ParameterItemProvider extends NamedElementItemProvider implements IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
    /**
     * This constructs an instance from a factory and a notifier. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public ParameterItemProvider(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    /**
     * This returns the property descriptors for the adapted class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
        if (itemPropertyDescriptors == null) {
            super.getPropertyDescriptors(object);

            addStylePropertyDescriptor(object);
            addDataTypePropertyDescriptor(object);
            addMandatoryPropertyDescriptor(object);
            addDefaultValuePropertyDescriptor(object);
            addFixedPropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Style feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addStylePropertyDescriptor(Object object) {
        itemPropertyDescriptors.add
            (createItemPropertyDescriptor
                (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
                 getResourceLocator(),
                 getString("_UI_Parameter_style_feature"),
                 getString("_UI_PropertyDescriptor_description", "_UI_Parameter_style_feature", "_UI_Parameter_type"),
                 RsdPackage.Literals.PARAMETER__STYLE,
                 true,
                 false,
                 false,
                 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                 null,
                 null));
    }

    /**
     * This adds a property descriptor for the Data Type feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addDataTypePropertyDescriptor(Object object) {
        itemPropertyDescriptors.add
            (createItemPropertyDescriptor
                (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
                 getResourceLocator(),
                 getString("_UI_Parameter_dataType_feature"),
                 getString("_UI_PropertyDescriptor_description", "_UI_Parameter_dataType_feature", "_UI_Parameter_type"),
                 RsdPackage.Literals.PARAMETER__DATA_TYPE,
                 true,
                 false,
                 false,
                 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                 null,
                 null));
    }

    /**
     * This adds a property descriptor for the Mandatory feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addMandatoryPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add
            (createItemPropertyDescriptor
                (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
                 getResourceLocator(),
                 getString("_UI_Parameter_mandatory_feature"),
                 getString("_UI_PropertyDescriptor_description", "_UI_Parameter_mandatory_feature", "_UI_Parameter_type"),
                 RsdPackage.Literals.PARAMETER__MANDATORY,
                 true,
                 false,
                 false,
                 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
                 null,
                 null));
    }

    /**
     * This adds a property descriptor for the Default Value feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addDefaultValuePropertyDescriptor(Object object) {
        itemPropertyDescriptors.add
            (createItemPropertyDescriptor
                (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
                 getResourceLocator(),
                 getString("_UI_Parameter_defaultValue_feature"),
                 getString("_UI_PropertyDescriptor_description", "_UI_Parameter_defaultValue_feature", "_UI_Parameter_type"),
                 RsdPackage.Literals.PARAMETER__DEFAULT_VALUE,
                 true,
                 false,
                 false,
                 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                 null,
                 null));
    }

    /**
     * This adds a property descriptor for the Fixed feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addFixedPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add
            (createItemPropertyDescriptor
                (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
                 getResourceLocator(),
                 getString("_UI_Parameter_fixed_feature"),
                 getString("_UI_PropertyDescriptor_description", "_UI_Parameter_fixed_feature", "_UI_Parameter_type"),
                 RsdPackage.Literals.PARAMETER__FIXED,
                 true,
                 false,
                 false,
                 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
                 null,
                 null));
    }

    /**
     * This returns Parameter.png.
     * 
     * @generated NOT
     */
    @Override
    public Object getImage(Object object) {
        DataType dataType = ((Parameter) object).getDataType();
        switch (dataType) {
        case TEXT:
            return RsdImage.getImage(RsdImage.PARAMETER_TEXT);
        case BOOLEAN:
            return RsdImage.getImage(RsdImage.PARAMETER_BOOLEAN);
        case DECIMAL:
            return RsdImage.getImage(RsdImage.PARAMETER_DECIMAL);
        case INTEGER:
            return RsdImage.getImage(RsdImage.PARAMETER_INTEGER);
        case DATE:
        case TIME:
        case DATE_TIME:
            return RsdImage.getImage(RsdImage.PARAMETER_DATE_TIME);
        }
        return RsdImage.getImage(RsdImage.PARAMETER);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected boolean shouldComposeCreationImage() {
        return true;
    }

    /**
     * This returns the label text for the adapted class.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getText(Object object) {
        String label = ((Parameter)object).getName();
        return label == null || label.length() == 0 ?
            getString("_UI_Parameter_type") :
            getString("_UI_Parameter_type") + " " + label;
    }

    /**
     * This handles model notifications by calling {@link #updateChildren} to update any cached
     * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @generated
     */
    @Override
    public void notifyChanged(Notification notification) {
        updateChildren(notification);

        switch (notification.getFeatureID(Parameter.class)) {
            case RsdPackage.PARAMETER__STYLE:
            case RsdPackage.PARAMETER__DATA_TYPE:
            case RsdPackage.PARAMETER__MANDATORY:
            case RsdPackage.PARAMETER__DEFAULT_VALUE:
            case RsdPackage.PARAMETER__FIXED:
                fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
                return;
        }
        super.notifyChanged(notification);
    }

    /**
     * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s
     * describing the children that can be created under this object. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected void collectNewChildDescriptors(
            Collection<Object> newChildDescriptors, Object object) {
        super.collectNewChildDescriptors(newChildDescriptors, object);
    }

}
