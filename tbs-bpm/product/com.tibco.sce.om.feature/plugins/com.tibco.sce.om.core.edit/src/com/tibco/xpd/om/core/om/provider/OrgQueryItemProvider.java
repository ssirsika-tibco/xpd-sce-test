/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om.provider;

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

import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.OrgQuery;

/**
 * This is the item provider adapter for a {@link com.tibco.xpd.om.core.om.OrgQuery} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class OrgQueryItemProvider extends NamedElementItemProvider implements
        IEditingDomainItemProvider, IStructuredItemContentProvider,
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
        Object parent = super.getParent(object);
        if (parent instanceof OrgModel) {
            OrgModelItemProvider orgModelItemProvider =
                    (OrgModelItemProvider) adapterFactory.adapt(parent,
                            IEditingDomainItemProvider.class);
            return orgModelItemProvider != null ? orgModelItemProvider
                    .getTransientParent(parent,
                            OMPackage.Literals.ORG_MODEL__QUERIES) : null;
        }
        return parent;
    }

    /**
     * This constructs an instance from a factory and a notifier. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public OrgQueryItemProvider(AdapterFactory adapterFactory) {
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
            // addQueryPropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Grammar feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addGrammarPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_OrgQuery_grammar_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_OrgQuery_grammar_feature", "_UI_OrgQuery_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        OMPackage.Literals.ORG_QUERY__GRAMMAR,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Query feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addQueryPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_OrgQuery_query_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_OrgQuery_query_feature", "_UI_OrgQuery_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        OMPackage.Literals.ORG_QUERY__QUERY,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This returns OrgQuery.gif.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object, getResourceLocator()
                .getImage("full/obj16/OrgQuery")); //$NON-NLS-1$
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
        return label == null || label.length() == 0 ? getString("_UI_OrgQuery_type") : //$NON-NLS-1$
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

        switch (notification.getFeatureID(OrgQuery.class)) {
        case OMPackage.ORG_QUERY__GRAMMAR:
        case OMPackage.ORG_QUERY__QUERY:
            fireNotifyChanged(new ViewerNotification(notification, notification
                    .getNotifier(), false, true));
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
