/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;

import com.tibco.xpd.om.core.om.Capability;
import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.util.OMSwitch;

/**
 * This is the item provider adapter for a {@link com.tibco.xpd.om.core.om.Capability} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class CapabilityItemProvider extends QualifiedOrgElementItemProvider
        implements IEditingDomainItemProvider, IStructuredItemContentProvider,
        ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.emf.edit.provider.ItemProviderAdapter#getParent(java.lang
     * .Object)
     */
    @Override
    public Object getParent(Object object) {
        Capability capability = (Capability) object;
        if (capability.getCategory() != null) {
            return capability.getCategory();
        }
        Object orgModel = super.getParent(object);
        OrgModelItemProvider orgModelItemProvider =
                (OrgModelItemProvider) adapterFactory.adapt(orgModel,
                        IEditingDomainItemProvider.class);
        return orgModelItemProvider != null ? orgModelItemProvider
                .getTransientParent(orgModel,
                        OMPackage.Literals.ORG_MODEL__CAPABILITIES) : null;
    }

    /**
     * This constructs an instance from a factory and a notifier. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public CapabilityItemProvider(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    /**
     * This returns the property descriptors for the adapted class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
        if (itemPropertyDescriptors == null) {
            super.getPropertyDescriptors(object);

            // These properties will not be visible on advanced properties page.
            // addCategoryPropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Category feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    protected void addCategoryPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(new ItemPropertyDescriptor(
                        ((ComposeableAdapterFactory) adapterFactory)
                                .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_Capability_category_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_AbstractCapability_category_feature", "_UI_Capability_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        OMPackage.Literals.CAPABILITY__CATEGORY, true, false,
                        true, null, null, null) {
                    /*
                     * (non-Javadoc)
                     * 
                     * @see
                     * org.eclipse.emf.edit.provider.ItemPropertyDescriptor#
                     * getChoiceOfValues(java.lang.Object)
                     */
                    @Override
                    public Collection<?> getChoiceOfValues(Object object) {
                        final List<EObject> list = new ArrayList<EObject>();
                        list.add(null);
                        for (TreeIterator<Object> allContents =
                                EcoreUtil.getAllContents(Collections
                                        .singletonList(((Capability) object)
                                                .eContainer())); allContents
                                .hasNext();) {
                            (new OMSwitch<EObject>() {
                                @Override
                                public EObject caseCapabilityCategory(
                                        com.tibco.xpd.om.core.om.CapabilityCategory object) {
                                    list.add(object);
                                    return object;
                                };
                            }).doSwitch((EObject) allContents.next());

                        }
                        return list;
                    }
                });
    }

    /**
     * This specifies how to implement {@link #getChildren} and is used to
     * deduce an appropriate feature for an
     * {@link org.eclipse.emf.edit.command.AddCommand},
     * {@link org.eclipse.emf.edit.command.RemoveCommand} or
     * {@link org.eclipse.emf.edit.command.MoveCommand} in
     * {@link #createCommand}. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public Collection<? extends EStructuralFeature> getChildrenFeatures(
            Object object) {
        if (childrenFeatures == null) {
            super.getChildrenFeatures(object);
            // childrenFeatures
            // .add(OMPackage.Literals.CAPABILITY__PARAMETER_DESCRIPTORS);
        }
        return childrenFeatures;
    }

    /**
     * This returns Capability.gif. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object, getResourceLocator()
                .getImage("full/obj16/Capability")); //$NON-NLS-1$
    }

    /**
     * This returns the label text for the adapted class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public String getText(Object object) {
        String label = ((NamedElement) object).getLabel();
        return label == null || label.length() == 0 ? getString("_UI_Capability_type") : //$NON-NLS-1$
                label;
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
        super.notifyChanged(notification);
    }

    /**
     * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s
     * describing the children that can be created under this object. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    protected void collectNewChildDescriptors(
            Collection<Object> newChildDescriptors, Object object) {
        super.collectNewChildDescriptors(newChildDescriptors, object);

        // newChildDescriptors.add(createChildParameter(
        // OMPackage.Literals.CAPABILITY__PARAMETER_DESCRIPTORS,
        // OMFactory.eINSTANCE.createParameterDescriptor()));
    }

}
