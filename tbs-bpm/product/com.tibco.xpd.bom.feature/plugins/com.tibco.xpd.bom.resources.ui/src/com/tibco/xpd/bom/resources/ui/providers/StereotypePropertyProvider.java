/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.bom.resources.ui.providers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.EMFEditPlugin;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.core.service.IProviderChangeListener;
import org.eclipse.gmf.runtime.common.ui.services.properties.GetPropertySourceOperation;
import org.eclipse.gmf.runtime.common.ui.services.properties.ICompositePropertySource;
import org.eclipse.gmf.runtime.common.ui.services.properties.IPropertiesProvider;
import org.eclipse.swt.widgets.Control;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.VisibilityKind;
import org.eclipse.uml2.uml.edit.UMLEditPlugin;

import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager.StereotypeKind;
import com.tibco.xpd.bom.resources.ui.internal.Messages;
import com.tibco.xpd.bom.resources.ui.internal.picker.stereotype.ComplexTypePicker;
import com.tibco.xpd.bom.resources.ui.internal.properties.EMFCompositePropertySourceEx;
import com.tibco.xpd.bom.resources.ui.internal.properties.ItemPropertyDescriptorForModifier;
import com.tibco.xpd.bom.resources.ui.internal.properties.PropertyModifier;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * <code>Stereotype</code> properties provider for the properties service. This
 * provides the <code>Stereotype</code> attributes in the advanced tab.
 * 
 * @author njpatel
 * 
 */
public class StereotypePropertyProvider implements IPropertiesProvider {

    private static final String PRIMITIVETYPE_FACET_URI =
            "pathmap://BOM_TYPES/PrimitiveTypeFacets.profile.uml"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gmf.runtime.common.ui.services.properties.IPropertiesProvider
     * #getPropertySource(java.lang.Object)
     */
    public ICompositePropertySource getPropertySource(Object object) {
        ICompositePropertySource src = null;

        if (object instanceof Element) {
            Element elem = (Element) object;
            EList<Stereotype> stereotypes = elem.getAppliedStereotypes();

            // Provide property source for all applied stereotypes
            if (stereotypes != null && !stereotypes.isEmpty()) {
                for (Stereotype type : stereotypes) {
                    if (type.equals(GlobalDataProfileManager.getInstance()
                            .getStereotype(StereotypeKind.TAG))) {
                        // XPD-4793 GLobal Data: We do not want to display
                        // the ModelTag Stereotype Attributes.
                        continue;
                    }
                    EObject appl = elem.getStereotypeApplication(type);
                    if (appl != null) {
                        // Need to ignore restricted primitive type
                        // stereotype
                        if (appl.eClass() != null) {
                            Resource resource = appl.eClass().eResource();

                            if (resource != null
                                    && resource.getURI().toString()
                                            .equals(PRIMITIVETYPE_FACET_URI)) {
                                continue;
                            }
                        }
                        // Provide a stereotype application property source
                        EMFCompositePropertySourceEx source =
                                new EMFCompositePropertySourceEx(
                                        appl,
                                        new StereotypeApplicationPropertySource(
                                                type), type.getLabel());

                        if (src == null) {
                            src = source;
                        } else {
                            src.addPropertySource(source);
                        }
                    }
                }
            }
        }

        return src;
    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.gmf.runtime.common.core.service.IProvider#
     * addProviderChangeListener
     * (org.eclipse.gmf.runtime.common.core.service.IProviderChangeListener)
     */
    public void addProviderChangeListener(IProviderChangeListener listener) {
        // Nothing to do
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gmf.runtime.common.core.service.IProvider#provides(org.eclipse
     * .gmf.runtime.common.core.service.IOperation)
     */
    public boolean provides(IOperation operation) {
        if (operation instanceof GetPropertySourceOperation) {
            return ((GetPropertySourceOperation) operation).getObject() instanceof Element;
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.gmf.runtime.common.core.service.IProvider#
     * removeProviderChangeListener
     * (org.eclipse.gmf.runtime.common.core.service.IProviderChangeListener)
     */
    public void removeProviderChangeListener(IProviderChangeListener listener) {
        // Nothing to do
    }

    /**
     * Check if the given feature is a stereotype extension.
     * 
     * @param feat
     *            <code>EStructuralFeature</code> to inspect.
     * @return <code>true</code> if it's an extension, <code>false</code> if
     *         it's not (i.e. it's a stereotype attribute).
     */
    private boolean isExtension(EStructuralFeature feat) {
        boolean ext = false;

        if (feat instanceof EReference) {
            EClass refType = ((EReference) feat).getEReferenceType();

            if (refType != null) {
                ext = ("base_" + refType.getName()) //$NON-NLS-1$
                        .equals(((EReference) feat).getName());
            }
        }

        return ext;
    }

    /**
     * Property source for <code>Stereotype</code> applications.
     * 
     * @author njpatel
     * 
     */
    private class StereotypeApplicationPropertySource implements
            IItemPropertySource {

        private final Stereotype type;

        /**
         * Constructor.
         * 
         * @param type
         *            Stereotype of the application.
         */
        public StereotypeApplicationPropertySource(Stereotype type) {
            this.type = type;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.emf.edit.provider.IItemPropertySource#getEditableValue
         * (java.lang.Object)
         */
        public Object getEditableValue(Object object) {
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.emf.edit.provider.IItemPropertySource#getPropertyDescriptor
         * (java.lang.Object, java.lang.Object)
         */
        public IItemPropertyDescriptor getPropertyDescriptor(Object object,
                Object propertyID) {
            List<IItemPropertyDescriptor> descriptors =
                    getPropertyDescriptors(object);

            if (descriptors != null) {
                for (IItemPropertyDescriptor desc : descriptors) {
                    if (propertyID.equals(desc.getId(object))) {
                        return desc;
                    }
                }
            }
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.emf.edit.provider.IItemPropertySource#getPropertyDescriptors
         * (java.lang.Object)
         */
        public List<IItemPropertyDescriptor> getPropertyDescriptors(
                Object object) {
            List<IItemPropertyDescriptor> descList = null;
            TransactionalEditingDomain ed =
                    TransactionUtil.getEditingDomain(object);

            if (ed instanceof AdapterFactoryEditingDomain) {
                AdapterFactory af =
                        ((AdapterFactoryEditingDomain) ed).getAdapterFactory();
                // Stereotype application
                if (object instanceof DynamicEObjectImpl) {
                    DynamicEObjectImpl eo = (DynamicEObjectImpl) object;
                    descList = new ArrayList<IItemPropertyDescriptor>();

                    if (eo.eClass() != null) {
                        // Create a property descriptor for each attribute of
                        // the the stereotype
                        for (Property attribute : type.getAllAttributes()) {
                            // Add if property is not private
                            if (!attribute.isSetVisibility()
                                    || attribute.getVisibility() != VisibilityKind.PRIVATE_LITERAL) {
                                EStructuralFeature feat =
                                        eo
                                                .eClass()
                                                .getEStructuralFeature(attribute
                                                        .getName());
                                if (feat instanceof EAttribute) {
                                    // Simple type
                                    String label = getLabel((EAttribute) feat);
                                    Object img = getImage((EAttribute) feat);
                                    IItemPropertyDescriptor desc;
                                    if (feat.getEType() instanceof EEnum) {
                                        desc =
                                                new EnumPropertyDescriptor(
                                                        af,
                                                        null,
                                                        label,
                                                        label,
                                                        feat,
                                                        !isValueReadOnly(attribute),
                                                        img, (EEnum) feat
                                                                .getEType());
                                    } else {
                                        desc =
                                                new ItemPropertyDescriptor(
                                                        af,
                                                        label,
                                                        label,
                                                        feat,
                                                        !isValueReadOnly(attribute),
                                                        img);
                                    }
                                    descList.add(desc);
                                } else if (feat instanceof EReference) {
                                    // If this is not a stereotype extension
                                    // then it's a complex type
                                    if (!isExtension(feat)) {
                                        IItemPropertyDescriptor desc =
                                                getDescriptorForComplexType(af,
                                                        eo.eResource(),
                                                        (EReference) feat,
                                                        !isValueReadOnly(attribute));

                                        if (desc != null) {
                                            descList.add(desc);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return descList;
        }

        /**
         * Check if this element is a read-only element. This will be the case
         * if its (or its ancestors) Visibility is set to Protected.
         * 
         * @param element
         * @return
         */
        private boolean isValueReadOnly(NamedElement element) {
            if (element != null) {
                if (element.isSetVisibility()
                        && element.getVisibility() == VisibilityKind.PROTECTED_LITERAL) {
                    return true;
                }

                // Check the visibility of the parent
                EObject container = element.eContainer();
                if (container instanceof NamedElement) {
                    return isValueReadOnly((NamedElement) container);
                }
            }
            return false;
        }

        /**
         * Get the descriptor for the given <i>ref</i>.
         * 
         * @param af
         *            <code>AdapterFactory</code>
         * @param resource
         *            <code>Resource</code> to search for objects to reference
         *            (in picker).
         * @param ref
         *            <code>EReference</code> of attribute.
         * @param isSettable
         *            <code>true</code> if this value can be set,
         *            <code>false</code> otherwise.
         * @return property descriptor.
         */
        private IItemPropertyDescriptor getDescriptorForComplexType(
                AdapterFactory af, Resource resource, EReference ref,
                boolean isSettable) {
            IItemPropertyDescriptor desc = null;

            if (ref != null && ref.getEReferenceType() != null) {
                desc =
                        new ComplexTypeDescriptor(af, ref, null, resource,
                                isSettable);
            }

            return desc;
        }

        /**
         * Get the externalized label of the given attribute from it's related
         * <code>Stereotype</code>.
         * 
         * @param eAttr
         *            EAttribute to get label of.
         * @return externalized label, or name of the attribute if no label
         *         found.
         */
        private String getLabel(EAttribute eAttr) {
            String label = null;

            if (eAttr != null) {
                EList<Property> attrs = type.getAllAttributes();

                if (attrs != null) {
                    for (Property attr : attrs) {
                        if (attr.getName().equals(eAttr.getName())) {
                            label = attr.getLabel();
                            break;
                        }
                    }
                }

                // Use name if no label found
                if (label == null) {
                    label = eAttr.getName();
                }
            }

            return label;
        }

        /**
         * Get the image description for the given <code>EAttribute</code>.
         * 
         * @param eAttr
         *            attribute to get image for
         * @return image description or <code>null</code> if no image found.
         */
        private Object getImage(EAttribute eAttr) {
            Object imgDesc = null;

            if (eAttr != null) {
                EDataType type = eAttr.getEAttributeType();
                String imgLoc = "full/obj16/GenericValue"; //$NON-NLS-1$
                if (type != null) {
                    Class<?> instanceClass = type.getInstanceClass();

                    if (instanceClass == int.class) {
                        imgLoc = "full/obj16/IntegralValue"; //$NON-NLS-1$
                    } else if (instanceClass == String.class) {
                        imgLoc = "full/obj16/TextValue"; //$NON-NLS-1$
                    } else if (instanceClass == boolean.class) {
                        imgLoc = "full/obj16/BooleanValue"; //$NON-NLS-1$
                    } else if (instanceClass == float.class
                            || instanceClass == double.class) {
                        imgLoc = "full/obj16/RealValue"; //$NON-NLS-1$
                    }
                }
                imgDesc = EMFEditPlugin.INSTANCE.getImage(imgLoc);
            }

            return imgDesc;
        }
    }

    /**
     * Item property descriptor for complex types.
     * 
     * @author njpatel
     */
    private class ComplexTypeDescriptor extends
            ItemPropertyDescriptorForModifier {

        private IItemLabelProvider labelProvider;

        private final EReference ref;

        /**
         * Item property descriptor for complex types.
         * 
         * @param adapterFactory
         *            <code>AdapterFactory</code>
         * @param ref
         *            <code>EReference</code> of attribute.
         * @param staticImage
         *            image to show in the property view
         * @param resource
         *            <code>Resource</code> to search for objects to refer (from
         *            picker).
         * @param isSettable
         *            <code>true</code> if this value can be set.
         */
        public ComplexTypeDescriptor(AdapterFactory adapterFactory,
                EReference ref, Object staticImage, Resource resource,
                boolean isSettable) {

            super(adapterFactory, null, ref.getName(), ref.getName(),
                    isSettable, false, false, staticImage, null, null,
                    new ComplexTypePropertyModifier(resource, ref));
            this.ref = ref;
        }

        @Override
        public IItemLabelProvider getLabelProvider(Object object) {

            if (labelProvider == null) {
                labelProvider = new IItemLabelProvider() {

                    // Default image for all complex types
                    private final Object defaultImage =
                            UMLEditPlugin.getPlugin()
                                    .getImage("full/obj16/Class"); //$NON-NLS-1$

                    /*
                     * (non-Javadoc)
                     * 
                     * @see
                     * org.eclipse.emf.edit.provider.IItemLabelProvider#getImage
                     * (java.lang.Object)
                     */
                    public Object getImage(Object object) {
                        return defaultImage;
                    }

                    /*
                     * (non-Javadoc)
                     * 
                     * @see
                     * org.eclipse.emf.edit.provider.IItemLabelProvider#getText
                     * (java.lang.Object)
                     */
                    public String getText(Object object) {
                        EObject eo = null;
                        String text = null;

                        if (object instanceof DynamicEObjectImpl) {
                            eo = getReference((DynamicEObjectImpl) object);
                            text = getLabel(eo);
                        } else if (object instanceof EObject) {
                            text = getLabel((EObject) object);
                        } else if (object instanceof Collection) {
                            Collection<?> objs = (Collection<?>) object;
                            // For collection display a comma separated list
                            for (Object obj : objs) {
                                if (obj instanceof DynamicEObjectImpl) {
                                    eo = getReference((DynamicEObjectImpl) obj);
                                    if (eo == null) {
                                        eo = (EObject) obj;
                                    }
                                } else if (obj instanceof EObject) {
                                    eo = (EObject) obj;
                                }

                                if (eo != null) {
                                    if (text == null) {
                                        text = getLabel(eo);
                                    } else {
                                        text += ", " //$NON-NLS-1$
                                                + getLabel(eo);
                                    }
                                }
                            }
                        }

                        return text != null ? text : ""; //$NON-NLS-1$
                    }

                    /**
                     * Get the name of the given <code>EObject</code>. For
                     * <code>NamedElement</code> objects this will return then
                     * fully qualified name, otherwise the item provider will be
                     * asked for the label. The name will be prefixed by the
                     * type of object expected by the attribute (e.g.
                     * &lt;Stereotype&gt;).
                     * 
                     * @param eo
                     *            <code>EObject</code> to get label for.
                     * @return name of object, <code>null</code> if it cannot be
                     *         determined.
                     */
                    private String getLabel(EObject eo) {
                        String text = null;

                        if (eo != null) {
                            if (eo instanceof NamedElement) {
                                text = ((NamedElement) eo).getQualifiedName();
                            }

                            if (text == null) {
                                text = WorkingCopyUtil.getText(eo);
                            }

                            if (text != null && ref != null
                                    && ref.getEReferenceType() != null) {
                                text = String.format("<%s> %s", ref //$NON-NLS-1$
                                        .getEReferenceType().getName(), text);
                            }
                        }

                        return text;
                    }

                    /**
                     * Get the object that this stereotype application belongs
                     * to.
                     * 
                     * @param deo
                     *            Stereotype application.
                     * @return referenced object.
                     */
                    private EObject getReference(DynamicEObjectImpl deo) {
                        EObject eo = null;

                        if (deo.eClass() != null) {
                            EList<EStructuralFeature> features =
                                    deo.eClass().getEAllStructuralFeatures();

                            for (EStructuralFeature feat : features) {
                                if (isExtension(feat)) {
                                    Object value = deo.eGet(feat);

                                    if (value instanceof EObject) {
                                        eo = (EObject) value;
                                        break;
                                    }
                                }
                            }
                        }
                        return eo;
                    }
                };
            }
            return labelProvider;
        }
    }

    /**
     * Property modifier for a complex type. This provides with a picker to add
     * references for the complex type. Depending on the multiplicity of the
     * attribute a single or multi select picker will be presented.
     * 
     * @author njpatel
     */
    private class ComplexTypePropertyModifier implements PropertyModifier {

        private final EClass eClass;

        private final Resource resource;

        private final boolean multiple;

        private final EReference ref;

        /**
         * Property modifier for a complex type.
         * 
         * @param resource
         *            <code>Resource</code> to search for objects to display in
         *            the picker.
         * @param ref
         *            <code>Stereotype</code> attribute.
         */
        public ComplexTypePropertyModifier(Resource resource, EReference ref) {
            this.resource = resource;
            this.ref = ref;
            this.eClass = ref.getEReferenceType();
            // Check for multiplicity
            this.multiple =
                    ref.getUpperBound() == ETypedElement.UNBOUNDED_MULTIPLICITY
                            || ref.getUpperBound() > 1;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.bom.resources.ui.internal.properties.PropertyModifier
         * #getPropertyValue(java.lang.Object)
         */
        public Object getPropertyValue(Object object) {
            Object obj = null;
            if (object instanceof EObject) {
                EObject eo = (EObject) object;
                obj = eo.eGet(ref);
            }

            return obj;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.bom.resources.ui.internal.properties.PropertyModifier
         * #performAction(org.eclipse.swt.widgets.Control,
         * org.eclipse.emf.edit.provider.IItemPropertyDescriptor,
         * java.lang.Object)
         */
        public Object performAction(Control parentControl,
                IItemPropertyDescriptor itemPropertyDescriptor, Object object) {
            Object sel = null;

            ComplexTypePicker picker =
                    new ComplexTypePicker(parentControl.getShell(), multiple,
                            resource, eClass);

            picker
                    .setTitle(String
                            .format(Messages.StereotypePropertyProvider_complexPicker_title,
                                    ref.getEReferenceType().getName()));
            picker
                    .setMessage(String
                            .format(multiple ? Messages.StereotypePropertyProvider_complexPicker_multiselect_shortdesc
                                    : Messages.StereotypePropertyProvider_complexPicker_singleselect_shortdesc,
                                    ref.getEReferenceType().getName()));

            // For a multi picker set the initial selection in the picker
            if (object instanceof EObject) {
                Object value = ((EObject) object).eGet(ref);

                if (value instanceof Collection<?>) {
                    picker.setInitialElementSelections((List<?>) value);
                }
            }

            if (picker.open() == ComplexTypePicker.OK) {
                if (multiple) {
                    Object[] result = picker.getResult();

                    if (result != null) {
                        sel = Arrays.asList(result);
                    }
                } else {
                    sel = picker.getFirstResult();
                }
            }

            return sel;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.bom.resources.ui.internal.properties.PropertyModifier
         * #resetPropertyValue(java.lang.Object)
         */
        public void resetPropertyValue(Object object) {
            if (object instanceof EObject) {
                final EObject eo = (EObject) object;
                // Clear the attribute
                TransactionalEditingDomain ed =
                        TransactionUtil.getEditingDomain(eo);

                if (ed != null) {
                    // Unset the value
                    ed.getCommandStack().execute(new RecordingCommand(ed) {
                        @Override
                        protected void doExecute() {
                            eo.eUnset(ref);
                        }
                    });
                }
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.bom.resources.ui.internal.properties.PropertyModifier
         * #setPropertyValue(java.lang.Object, java.lang.Object)
         */
        public void setPropertyValue(Object object, final Object value) {
            TransactionalEditingDomain ed =
                    TransactionUtil.getEditingDomain(object);

            if (ed != null) {
                // Set the reference
                ed.getCommandStack().execute(SetCommand.create(ed,
                        object,
                        ref,
                        value));
            }
        }
    }

    /**
     * Property descriptor for {@link Enumeration} types. This will provide the
     * drop-down values of the Enumeration without adding additional descriptors
     * for the {@link EEnumLiteral}s.
     * 
     * @author njpatel
     * 
     */
    private class EnumPropertyDescriptor extends ItemPropertyDescriptor {

        private final EEnum enumeration;

        public EnumPropertyDescriptor(AdapterFactory adapterFactory,
                ResourceLocator resourceLocator, String displayName,
                String description, EStructuralFeature feature,
                boolean isSettable, Object staticImg, EEnum enumeration) {
            super(adapterFactory, resourceLocator, displayName, description,
                    feature, isSettable, staticImg);
            this.enumeration = enumeration;
        }

        @Override
        protected Collection<?> getComboBoxObjects(Object object) {
            if (object instanceof EObject && enumeration != null) {
                List<String> options = new ArrayList<String>();
                if (!feature.isRequired()) {
                    // Allow this feature to be unset
                    options.add(""); //$NON-NLS-1$
                }

                EList<EEnumLiteral> literals = enumeration.getELiterals();
                for (EEnumLiteral literal : literals) {
                    options.add(literal.getName());
                }

                return options;
            }
            return null;
        }

        @Override
        public void setPropertyValue(Object object, Object value) {
            if (value instanceof String) {
                if (value.equals("")) { //$NON-NLS-1$
                    value = null;
                } else {
                    for (EEnumLiteral literal : enumeration.getELiterals()) {
                        if (value.equals(literal.getName())) {
                            value = literal;
                            break;
                        }
                    }
                }
            }

            super.setPropertyValue(object, value);
        }

        @Override
        public Object getPropertyValue(Object object) {
            if (object instanceof EObject) {
                Object value = ((EObject) object).eGet(feature);

                if (value instanceof EEnumLiteral) {
                    return ((EEnumLiteral) value).getName();
                }
            }

            return ""; //$NON-NLS-1$
        }
    }
}
