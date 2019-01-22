/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ViewerNotification;

import com.tibco.xpd.om.core.om.Attribute;
import com.tibco.xpd.om.core.om.AttributeValue;
import com.tibco.xpd.om.core.om.OMFactory;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgElementType;
import com.tibco.xpd.om.core.om.util.OMUtil;

/**
 * This is the item provider adapter for a {@link com.tibco.xpd.om.core.om.OrgElementType} object.
 * <!-- begin-user-doc
 * --> <!-- end-user-doc -->
 * @generated
 */
public class OrgElementTypeItemProvider extends NamedElementItemProvider
        implements IEditingDomainItemProvider, IStructuredItemContentProvider,
        ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
    /**
     * This constructs an instance from a factory and a notifier. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public OrgElementTypeItemProvider(AdapterFactory adapterFactory) {
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

        }
        return itemPropertyDescriptors;
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
                    .add(OMPackage.Literals.ORG_ELEMENT_TYPE__ATTRIBUTES);
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
     * This returns the label text for the adapted class.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getText(Object object) {
        String label = ((OrgElementType) object).getName();
        return label == null || label.length() == 0 ? getString("_UI_OrgElementType_type") : //$NON-NLS-1$
                getString("_UI_OrgElementType_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

        switch (notification.getFeatureID(OrgElementType.class)) {
        case OMPackage.ORG_ELEMENT_TYPE__ATTRIBUTES:
            fireNotifyChanged(new ViewerNotification(notification, notification
                    .getNotifier(), true, false));
            return;
        }
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

        Attribute attribute = OMFactory.eINSTANCE.createAttribute();
        attribute.setDisplayName(OMUtil
                .getDefaultName(getString("_UI_DefaultName_Attribute_label"), //$NON-NLS-1$
                        getDisplayNamesArray(getChildren(object))));
        newChildDescriptors
                .add(createChildParameter(OMPackage.Literals.ORG_ELEMENT_TYPE__ATTRIBUTES,
                        attribute));
    }

    @Override
    protected Command createRemoveCommand(EditingDomain domain, EObject owner,
            EStructuralFeature feature, Collection<?> collection) {
        if (OMPackage.Literals.ORG_ELEMENT_TYPE__ATTRIBUTES.equals(feature)) {
            /*
             * If removing an attribute then also remove all AttributeValues
             * referencing this attribute.
             */
            if (collection != null && !collection.isEmpty()) {
                CompoundCommand ccmd = new CompoundCommand();
                for (Object obj : collection) {
                    if (obj instanceof Attribute) {
                        Command cmd =
                                createRemoveAttributeValuesCommand(domain,
                                        (Attribute) obj);
                        if (cmd != null) {
                            ccmd.append(cmd);
                        }
                    }
                }

                if (!ccmd.isEmpty()) {
                    ccmd.append(super.createRemoveCommand(domain,
                            owner,
                            feature,
                            collection));
                    return ccmd;
                }
            }
        }
        return super.createRemoveCommand(domain, owner, feature, collection);
    }

    /**
     * Create Command to remove all references to the given eObject.
     * 
     * @param domain
     * @param attr
     * @return
     */
    private Command createRemoveAttributeValuesCommand(EditingDomain domain,
            Attribute attr) {
        if (attr != null) {
            CompoundCommand cmd = new CompoundCommand();
            ECrossReferenceAdapter adapter =
                    ECrossReferenceAdapter.getCrossReferenceAdapter(attr);
            if (adapter != null) {
                Collection<Setting> references =
                        adapter.getInverseReferences(attr);

                if (references != null) {
                    for (Setting ref : references) {
                        if (ref.getEObject() instanceof AttributeValue) {
                            EObject eo = ref.getEObject();
                            if (eo.eContainingFeature() != null
                                    && eo.eContainer() != null) {
                                if (eo.eContainingFeature().isMany()) {
                                    cmd.append(RemoveCommand.create(domain, eo
                                            .eContainer(), eo
                                            .eContainingFeature(), eo));
                                } else {
                                    cmd.append(SetCommand.create(domain,
                                            eo.eContainer(),
                                            eo.eContainingFeature(),
                                            SetCommand.UNSET_VALUE));
                                }

                            }
                        }
                    }
                }
            }
            return !cmd.isEmpty() ? cmd : null;
        }
        return null;
    }
}
