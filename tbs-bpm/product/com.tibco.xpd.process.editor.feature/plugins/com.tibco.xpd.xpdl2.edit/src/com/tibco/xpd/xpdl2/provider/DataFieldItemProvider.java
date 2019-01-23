/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.DeclaredType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * This is the item provider adapter for a {@link com.tibco.xpd.xpdl2.DataField} object.
 * <!-- begin-user-doc --> <!-- end-user-doc -->
 * @generated
 */
public class DataFieldItemProvider extends NamedElementItemProvider implements
        IEditingDomainItemProvider, IStructuredItemContentProvider,
        ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * This constructs an instance from a factory and a notifier. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public DataFieldItemProvider(AdapterFactory adapterFactory) {
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

            addDescriptionPropertyDescriptor(object);
            addIsArrayPropertyDescriptor(object);
            addReadOnlyPropertyDescriptor(object);
            addCorrelationPropertyDescriptor(object);
            addDeprecatedDataIsArrayPropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Description feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addDescriptionPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_DescribedElement_description_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_DescribedElement_description_feature", "_UI_DescribedElement_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        Xpdl2Package.Literals.DESCRIBED_ELEMENT__DESCRIPTION,
                        true,
                        false,
                        false,
                        null,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Correlation feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addCorrelationPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_DataField_correlation_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_DataField_correlation_feature", "_UI_DataField_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        Xpdl2Package.Literals.DATA_FIELD__CORRELATION,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Deprecated Data Is Array feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected void addDeprecatedDataIsArrayPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_DataField_deprecatedDataIsArray_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_DataField_deprecatedDataIsArray_feature", "_UI_DataField_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        Xpdl2Package.Literals.DATA_FIELD__DEPRECATED_DATA_IS_ARRAY,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Read Only feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addReadOnlyPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_ProcessRelevantData_readOnly_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_ProcessRelevantData_readOnly_feature", "_UI_ProcessRelevantData_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        Xpdl2Package.Literals.PROCESS_RELEVANT_DATA__READ_ONLY,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Is Array feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addIsArrayPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_ProcessRelevantData_isArray_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_ProcessRelevantData_isArray_feature", "_UI_ProcessRelevantData_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        Xpdl2Package.Literals.PROCESS_RELEVANT_DATA__IS_ARRAY,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
     * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
     * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Collection<? extends EStructuralFeature> getChildrenFeatures(
            Object object) {
        if (childrenFeatures == null) {
            super.getChildrenFeatures(object);
            childrenFeatures
                    .add(Xpdl2Package.Literals.OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS);
            childrenFeatures
                    .add(Xpdl2Package.Literals.PROCESS_RELEVANT_DATA__DATA_TYPE);
            childrenFeatures
                    .add(Xpdl2Package.Literals.PROCESS_RELEVANT_DATA__LENGTH);
            childrenFeatures
                    .add(Xpdl2Package.Literals.EXTENDED_ATTRIBUTES_CONTAINER__EXTENDED_ATTRIBUTES);
            childrenFeatures
                    .add(Xpdl2Package.Literals.DATA_FIELD__INITIAL_VALUE);
        }
        return childrenFeatures;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EStructuralFeature getChildFeature(Object object, Object child) {
        // Check the type of the specified child object and return the proper feature to use for
        // adding (see {@link AddCommand}) it as a child.

        return super.getChildFeature(object, child);
    }

    /**
     * This returns DataField.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public Object getImage(Object object) {
        String img = "DataField"; //$NON-NLS-1$

        if (object instanceof DataField) {
            DataField df = (DataField) object;

            DataType dt = df.getDataType();
            if (dt != null) {
                if (dt instanceof DeclaredType) {
                    img = "DataFieldDeclaredType"; //$NON-NLS-1$

                } else if (dt instanceof ExternalReference) {
                    img = "DataFieldExtRef"; //$NON-NLS-1$
                } else if (dt instanceof RecordType) {
                    img = "DataFieldCaseRefType"; //$NON-NLS-1$

                } else if (dt instanceof BasicType) {
                    BasicType bt = (BasicType) dt;
                    switch (bt.getType().getValue()) {
                    case BasicTypeType.BOOLEAN:
                        img = "DataFieldBoolean"; //$NON-NLS-1$
                        break;
                    case BasicTypeType.DATE:
                    case BasicTypeType.TIME:
                    case BasicTypeType.DATETIME:
                        img = "DataFieldDateTime"; //$NON-NLS-1$
                        break;
                    case BasicTypeType.FLOAT:
                        img = "DataFieldFloat"; //$NON-NLS-1$
                        break;
                    case BasicTypeType.INTEGER:
                        img = "DataFieldInt"; //$NON-NLS-1$
                        break;
                    case BasicTypeType.REFERENCE:
                        img = "DataFieldReference"; //$NON-NLS-1$
                        break;
                    case BasicTypeType.PERFORMER:
                        img = "DataFieldPerformer"; //$NON-NLS-1$

                        break;
                    case BasicTypeType.STRING:
                        img = "DataFieldString"; //$NON-NLS-1$
                        break;
                    }
                }

            }

            if (df.isCorrelation()) {
                img = "Correlation" + img; //$NON-NLS-1$
            }

            if (df.isIsArray()) {
                img += "Array"; //$NON-NLS-1$
            }
        }
        return overlayImage(object,
                getResourceLocator().getImage("full/obj16/" + img)); //$NON-NLS-1$
    }

    /**
     * This returns the label text for the adapted class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public String getText(Object object) {
        return super.getText(object);
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

        switch (notification.getFeatureID(DataField.class)) {
        case Xpdl2Package.DATA_FIELD__DESCRIPTION:
        case Xpdl2Package.DATA_FIELD__IS_ARRAY:
        case Xpdl2Package.DATA_FIELD__READ_ONLY:
        case Xpdl2Package.DATA_FIELD__CORRELATION:
        case Xpdl2Package.DATA_FIELD__DEPRECATED_DATA_IS_ARRAY:
            fireNotifyChanged(new ViewerNotification(notification,
                    notification.getNotifier(), false, true));
            return;
        case Xpdl2Package.DATA_FIELD__OTHER_ELEMENTS:
        case Xpdl2Package.DATA_FIELD__DATA_TYPE:
        case Xpdl2Package.DATA_FIELD__LENGTH:
        case Xpdl2Package.DATA_FIELD__EXTENDED_ATTRIBUTES:
        case Xpdl2Package.DATA_FIELD__INITIAL_VALUE:
            fireNotifyChanged(new ViewerNotification(notification,
                    notification.getNotifier(), true, false));
            return;
        }
        super.notifyChanged(notification);
    }

    /**
     * This adds to the collection of
     * {@link org.eclipse.emf.edit.command.CommandParameter}s describing all of
     * the children that can be created under this object. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @generated not
     */
    @Override
    protected void collectNewChildDescriptors(
            Collection<Object> newChildDescriptors, Object object) {
        super.collectNewChildDescriptors(newChildDescriptors, object);

        newChildDescriptors.add(createChildParameter(Xpdl2Package.eINSTANCE
                .getExtendedAttributesContainer_ExtendedAttributes(),
                Xpdl2Factory.eINSTANCE.createExtendedAttribute()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.eINSTANCE
                .getDataField_InitialValue(), Xpdl2Factory.eINSTANCE
                .createExpression()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.eINSTANCE
                .getProcessRelevantData_Length(), Xpdl2Factory.eINSTANCE
                .createLength()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.eINSTANCE
                .getDescribedElement_Description(), Xpdl2Factory.eINSTANCE
                .createDescription()));
    }

}
