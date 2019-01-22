/**
 * 
 */
package com.tibco.xpd.bom.resources.ui.internal.properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.ui.internal.Messages;
import com.tibco.xpd.bom.resources.ui.internal.calendar.BOMDateUtil;
import com.tibco.xpd.bom.resources.ui.providers.parsers.PrimitiveRestrictionFacetParser;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.components.calendar.DateType;

/**
 * @author wzurek
 * 
 */
public class RestrictionUtils {

    /**
     * Property descriptor for an Date/Time property.
     * 
     * @author njpatel
     * 
     */
    private static class DateTimePropertyDescriptor extends
            ItemPropertyDescriptor {

        Object feature = null;

        private final String featureName;

        private final DateType dateType;

        private final TransactionalEditingDomain ed;

        /**
         * Property descriptor for a Date/Time property. This will show a
         * calendar/time picker.
         * 
         * @param adapterFactory
         * @param displayName
         * @param description
         * @param facet
         * @param isSettable
         * @param category
         * @param dateType
         * @param ed
         */
        public DateTimePropertyDescriptor(AdapterFactory adapterFactory,
                String displayName, String description, String facet,
                boolean isSettable, String category, DateType dateType,
                TransactionalEditingDomain ed) {
            super(adapterFactory, null, displayName, description,
                    (EStructuralFeature) null, true, false, true, null,
                    category, null);
            this.featureName = facet;
            this.dateType = dateType;
            this.ed = ed;
        }

        @Override
        public Object getFeature(Object object) {
            return dateType;
        }

        @Override
        public Object getPropertyValue(Object object) {
            Property context = null;
            PrimitiveType type = null;

            if (object instanceof PrimitiveType) {
                type = (PrimitiveType) object;
            } else if (object instanceof Property) {
                context = (Property) object;
                type = (PrimitiveType) context.getType();
            }
            Object value =
                    PrimitivesUtil.getFacetPropertyValue(type,
                            featureName,
                            context);
            if (value instanceof String && ((String) value).length() > 0) {
                try {
                    return BOMDateUtil.parse((String) value, dateType);
                } catch (IllegalArgumentException e) {
                    // Do nothing as the value is invalid it will be cleared
                } catch (DatatypeConfigurationException e) {
                    Activator.getDefault().getLogger().error(e);
                }
            }
            return ""; //$NON-NLS-1$ 
        }

        @Override
        public void setPropertyValue(final Object object, Object value) {
            if (object != null) {
                String dateStr =
                        value instanceof XMLGregorianCalendar ? BOMDateUtil
                                .getISO8601String((XMLGregorianCalendar) value,
                                        dateType) : null;
                Property context = null;
                PrimitiveType type = null;

                if (object instanceof PrimitiveType) {
                    type = (PrimitiveType) object;
                } else if (object instanceof Property) {
                    context = (Property) object;
                    type = (PrimitiveType) context.getType();
                }

                setFacetValue(ed, type, featureName, dateStr, context);
            }
        }

        @Override
        public IItemLabelProvider getLabelProvider(Object object) {
            return new IItemLabelProvider() {
                public Object getImage(Object object) {
                    return null;
                }

                public String getText(Object object) {
                    if (object instanceof XMLGregorianCalendar) {
                        return BOMDateUtil
                                .getLocalizedString((XMLGregorianCalendar) object,
                                        dateType);
                    }
                    return Messages.PrimitiveRestrictionFacetParser_notset_label;
                }
            };
        }
    }

    /**
     * Property descriptor for an {@link Enumeration} property.
     * 
     * @author njpatel
     * 
     */
    private static class EnumPropertyDescriptor extends ItemPropertyDescriptor {

        Object feature = null;

        private final String featureName;

        private final List<EnumerationLiteral> values;

        private final Enumeration enumeration;

        private final TransactionalEditingDomain ed;

        /**
         * Property descriptor for an {@link Enumeration} property.
         * 
         * @param adapterFactory
         * @param displayName
         * @param description
         * @param facet
         * @param isSettable
         * @param category
         * @param enumeration
         * @param ed
         */
        public EnumPropertyDescriptor(AdapterFactory adapterFactory,
                String displayName, String description, String facet,
                boolean isSettable, String category, Enumeration enumeration,
                TransactionalEditingDomain ed) {
            super(adapterFactory, null, displayName, description,
                    (EStructuralFeature) null, isSettable, false, true, null,
                    category, null);
            this.featureName = facet;
            this.enumeration = enumeration;
            this.ed = ed;

            values = new ArrayList<EnumerationLiteral>();
            if (enumeration != null) {
                for (Object content : enumeration.eContents()) {
                    if (content instanceof EnumerationLiteral) {
                        values.add((EnumerationLiteral) content);
                    }
                }
            }
        }

        @Override
        public Object getFeature(Object object) {
            return enumeration;
        }

        @Override
        public Object getPropertyValue(Object object) {
            Property context = null;
            PrimitiveType type = null;

            if (object instanceof PrimitiveType) {
                type = (PrimitiveType) object;
            } else if (object instanceof Property) {
                context = (Property) object;
                type = (PrimitiveType) context.getType();
            }
            Object value =
                    PrimitivesUtil.getFacetPropertyValue(type,
                            featureName,
                            context);
            if (value instanceof EnumerationLiteral) {
                return ((EnumerationLiteral) value).getLabel();
            }
            return values.get(0).getLabel();
        }

        @Override
        public void setPropertyValue(final Object object, Object value) {
            if (object != null && value instanceof String) {
                EnumerationLiteral selectedLiteral = null;
                for (EnumerationLiteral literal : values) {
                    if (literal.getLabel().equals(value)) {
                        selectedLiteral = literal;
                        break;
                    }
                }
                if (selectedLiteral != null) {
                    Property context = null;
                    PrimitiveType type = null;

                    if (object instanceof PrimitiveType) {
                        type = (PrimitiveType) object;
                    } else if (object instanceof Property) {
                        context = (Property) object;
                        type = (PrimitiveType) context.getType();
                    }

                    setFacetValue(ed,
                            type,
                            featureName,
                            selectedLiteral,
                            context);
                }
            }
        }

        @Override
        protected Collection<?> getComboBoxObjects(Object object) {
            List<String> strValues = new ArrayList<String>();

            for (EnumerationLiteral literal : values) {
                strValues.add(literal.getLabel());
            }
            return strValues;
        }

        @Override
        public IItemLabelProvider getLabelProvider(Object object) {
            return new IItemLabelProvider() {
                public Object getImage(Object object) {
                    return null;
                }

                public String getText(Object object) {
                    return object instanceof String ? (String) object : ""; //$NON-NLS-1$
                }
            };
        }
    }

    /**
     * @author wzurek
     * 
     */
    private static class ItemPropertyDescriptorForBooleanRestriction extends
            ItemPropertyDescriptor {
        private Property feature;

        private final TransactionalEditingDomain ed;

        private static final Object NOT_SET = new Object();

        /**
         */
        private ItemPropertyDescriptorForBooleanRestriction(
                AdapterFactory adapterFactory, ResourceLocator resourceLocator,
                String displayName, String description, Property feature,
                boolean isSettable, boolean multiLine, boolean sortChoices,
                Object staticImage, String category, String[] filterFlags,
                TransactionalEditingDomain ed) {
            super(adapterFactory, resourceLocator, displayName, description,
                    null, isSettable, multiLine, sortChoices, staticImage,
                    category, filterFlags);
            this.feature = feature;
            this.ed = ed;
        }

        @Override
        public Object getFeature(Object object) {
            return feature;
        }

        @Override
        public Object getPropertyValue(Object object) {
            Property context = null;
            PrimitiveType type = null;

            if (object instanceof PrimitiveType) {
                type = (PrimitiveType) object;
            } else if (object instanceof Property) {
                context = (Property) object;
                type = (PrimitiveType) context.getType();
            }
            Object value =
                    PrimitivesUtil.getFacetPropertyValue(type, feature
                            .getName(), context);

            return value != null ? value : NOT_SET;
        }

        @Override
        public IItemLabelProvider getLabelProvider(Object object) {
            return new IItemLabelProvider() {
                public Object getImage(Object object) {
                    return null;
                }

                public String getText(Object object) {
                    if (object instanceof Boolean) {
                        Boolean bool = (Boolean) object;
                        return bool ? Messages.RestrictionUtils_true
                                : Messages.RestrictionUtils_false;
                    }
                    return Messages.PrimitiveRestrictionFacetParser_notset_label;
                }
            };
        }

        @Override
        public void setPropertyValue(Object object, Object value) {
            Property context = null;
            PrimitiveType type = null;

            if (object instanceof PrimitiveType) {
                type = (PrimitiveType) object;
            } else if (object instanceof Property) {
                context = (Property) object;
                type = (PrimitiveType) context.getType();
            }

            // If value is not boolean then set to null (unset)
            setFacetValue(ed,
                    type,
                    feature.getName(),
                    value instanceof Boolean ? value : null,
                    context);
        }
    }

    /**
     * Create a property descriptor for the given facet.
     * 
     * @param adapterFactory
     * @param facet
     * @param isSettable
     * @return {@link ItemPropertyDescriptor} or <code>null</code> if this facet
     *         is marked as private in the restricted type's stereotype.
     */
    public static ItemPropertyDescriptor createPropertyDescriptor(
            AdapterFactory adapterFactory, String facet) {
        // call new method with isSettable as true to support existing default
        // implementation
        return createPropertyDescriptor(adapterFactory, facet, true);
    }

    /**
     * Create a property descriptor for the given facet.Addditionally takes the
     * value for isSettable to configure the Descriptor as required.
     * 
     * @param adapterFactory
     * @param facet
     * @return {@link ItemPropertyDescriptor} or <code>null</code> if this facet
     *         is marked as private in the restricted type's stereotype.
     */
    // XPD-4793 This method is introduced to support the Global
    // data Identifier types with READ ONLY restrictions.
    public static ItemPropertyDescriptor createPropertyDescriptor(
            AdapterFactory adapterFactory, String facet, boolean isSettable) {
        TransactionalEditingDomain ed =
                XpdResourcesPlugin.getDefault().getEditingDomain();
        ResourceSet rs = ed.getResourceSet();
        String label = PrimitivesUtil.getFacetLabel(rs, facet);

        ItemPropertyDescriptor result = null;
        if (!PrimitivesUtil.isPrivate(rs, facet)) {
            Type type = PrimitivesUtil.getFacetType(rs, facet);
            if ("Boolean".equals(type.getName())) { //$NON-NLS-1$
                result =
                        new ItemPropertyDescriptorForBooleanRestriction(
                                adapterFactory,
                                null,
                                label,
                                label,
                                PrimitivesUtil.getFacetProperty(rs, facet),
                                isSettable,
                                false,
                                false,
                                null,
                                Messages.PrimitiveTypeItemProvider_restriction_label,
                                new String[] {}, ed);
            } else if (type instanceof Enumeration) {
                result =
                        new EnumPropertyDescriptor(
                                adapterFactory,
                                label,
                                label,
                                facet,
                                isSettable,
                                Messages.PrimitiveTypeItemProvider_restriction_label,
                                (Enumeration) type, ed);
            } else {
                // Check if this a date/time attribute
                DateType dateType = getDateType(facet);

                if (dateType != null) {
                    result =
                            new DateTimePropertyDescriptor(
                                    adapterFactory,
                                    label,
                                    label,
                                    facet,
                                    isSettable,
                                    Messages.PrimitiveTypeItemProvider_restriction_label,
                                    dateType, ed);
                } else {
                    result =
                            new ItemPropertyDescriptorForParser(
                                    adapterFactory,
                                    null,
                                    label,
                                    label,
                                    isSettable,
                                    false,
                                    false,
                                    null,
                                    Messages.PrimitiveTypeItemProvider_restriction_label,
                                    null, new PrimitiveRestrictionFacetParser(
                                            facet));
                }
            }
        }
        return result;
    }

    /**
     * Get the {@link DateType} of the given facet.
     * 
     * @param facet
     * @return <code>DateType</code> if this is date/time facet,
     *         <code>null</code> otherwise.
     */
    private static DateType getDateType(String facet) {
        DateType type = null;
        if (facet != null && facet.length() > 0) {
            if (facet
                    .equals(PrimitivesUtil.BOM_PRIMITIVE_FACET_DATE_DEFAULT_VALUE)) {
                type = DateType.DATE;
            } else if (facet
                    .equals(PrimitivesUtil.BOM_PRIMITIVE_FACET_DATE_TIME_DEFAULT_VALUE)) {
                type = DateType.DATETIME;
            } else if (facet
                    .equals(PrimitivesUtil.BOM_PRIMITIVE_FACET_TIME_DEFAULT_VALUE)) {
                type = DateType.TIME;
            } else if (facet
                    .equals(PrimitivesUtil.BOM_PRIMITIVE_FACET_DATE_TIME_TZ_DEFAULT_VALUE)) {
                type = DateType.DATETIMETZ;
            }
        }
        return type;
    }

    /**
     * Set the given facet value. This will set the value in a
     * {@link RecordingCommand}.
     * 
     * @param ed
     * @param type
     * @param facetName
     * @param value
     * @param context
     */
    private static void setFacetValue(TransactionalEditingDomain ed,
            final PrimitiveType type, final String facetName,
            final Object value, final Property context) {
        ed.getCommandStack().execute(new RecordingCommand(ed,
                "Set property value") {

            @Override
            protected void doExecute() {
                PrimitivesUtil.setFacetPropertyValue(type,
                        facetName,
                        value,
                        context);
            }
        });
    }

}
