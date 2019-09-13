/*
 */
package com.tibco.xpd.bom.resources.ui.providers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager.StereotypeKind;
import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.ui.BOMImages;
import com.tibco.xpd.bom.resources.ui.internal.properties.RestrictionUtils;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.destinations.GlobalDestinationUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.projectexplorer.decorator.OverlayImageDescriptor;

/**
 */
public class PropertyItemProvider extends NamedElementItemProvider {

    private static final String IP_GLOBAL_DESTINATION =
            "com.tibco.xpd.iprocess.globalDestination"; //$NON-NLS-1$

    PrimitiveType standardPrimTypeInteger;

    PrimitiveType standardPrimTypeDecimal;

    static private class PropertyHint implements IAdaptable {

        private final IDs id;

        private final Object base;

        public PropertyHint(Object base, IDs id) {
            this.base = base;
            this.id = id;
        }

        @Override
        @SuppressWarnings({ "rawtypes" })
        public Object getAdapter(Class adapter) {
            if (adapter.isInstance(id)) {
                return id;
            }
            if (adapter.isInstance(base)) {
                return base;
            }
            return null;
        }
    }

    /*
     * Sid: Was causing class load errors, because ImageRegistry() construction
     * fails if not done on UI thread. Changed to load on demand
     */
    private static ImageRegistry registry = null;

    private enum Overlays {
        MultiN, MultiNToUnbounded, MultiOne, MultiOneToN, MultiOneToUnbounded, MultiZero, //
        MultiZeroToN, MultiZeroToOne, MultiZeroToUnbounded, MultiNToM, Multi;

        /**
         * Select multiplicity icon. Returns null if it is undefined or invalid.
         * 
         * @param property
         * @return
         */
        static Overlays select(Property property) {
            if (property.getUpperValue() == null
                    && property.getLowerValue() == null) {
                return null;
            }

            /*
             * Sid ACE-1496. As with Process data only display whether it is
             * array or not, for BPM use cases this is enough. Trying to display
             * more makes the icon unreadable anyway at this scale.
             */
            if (property.getUpper() > 1 || property.getUpper() < 0) {
                return Multi;
            } else {
                return null;
            }

            // if (property.getLower() == 0) {
            // if (property.getUpper() == 1) {
            // return MultiZeroToOne;
            // }
            // if (property.getUpper() > 1) {
            // return MultiZeroToN;
            // }
            // if (property.getUpper() < 0) {
            // return MultiZeroToUnbounded;
            // }
            // // when lower == 0 && upper == 0, return null
            // } else if (property.getLower() == 1) {
            // if (property.getUpper() == 1) {
            // return MultiOne;
            // }
            // if (property.getUpper() > 1) {
            // return MultiOneToN;
            // }
            // if (property.getUpper() < 0) {
            // return MultiOneToUnbounded;
            // }
            // // when lower == 1 && upper == 0, return null
            // } else if (property.getLower() > 1) {
            // if (property.getUpper() == property.getLower()) {
            // return MultiN;
            // }
            // if (property.getUpper() > property.getLower()) {
            // return MultiNToM;
            // }
            // if (property.getUpper() < 0) {
            // return MultiNToUnbounded;
            // }
            // // when lower == 1 && upper == 0, return null
            // }
            // return null;
        }
    }

    /**
     * 
     */
    public enum IDs {
        MULTIPLICITY;

        public IAdaptable hint(Object base) {
            return new PropertyHint(base, this);
        }
    }

    /**
     */
    public PropertyItemProvider(AdapterFactory adapterFactory) {
        super(adapterFactory);

        TransactionalEditingDomain editingDomain =
                XpdResourcesPlugin.getDefault().getEditingDomain();

        standardPrimTypeInteger =
                PrimitivesUtil.getStandardPrimitiveTypeByName(editingDomain
                        .getResourceSet(),
                        PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);

        standardPrimTypeDecimal =
                PrimitivesUtil.getStandardPrimitiveTypeByName(editingDomain
                        .getResourceSet(),
                        PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME);
    }

    /**
     */
    @Override
    public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
        Property prop = getProperty(object);
        if (prop.getType() instanceof PrimitiveType) {
            List<IItemPropertyDescriptor> result =
                    new ArrayList<IItemPropertyDescriptor>();
            addPrimitiveTypeRestrictionProperties(prop,
                    (PrimitiveType) prop.getType(),
                    result);
            return result;
        }
        return new ArrayList<IItemPropertyDescriptor>(0);
    }

    private final Map<String, ItemPropertyDescriptor> descriptorCache =
            new HashMap<String, ItemPropertyDescriptor>();

    private ItemPropertyDescriptor getPropertyDescriptor(Property property,
            String facet, boolean isSettable) {

        if (descriptorCache.containsKey(facet)) {
            // Make sure if it is read-only at the moment and we need to switch
            // it to read/write, then we do not use the already stored values
            if (property != null) {
                // Check to make sure that the value returned is not null, there
                // are cases where there is a key, but it's value is null (e.g.
                // datePatternValue) and we need to make sure we do not break
                // for these
                ItemPropertyDescriptor itemPropertyDescriptor =
                        descriptorCache.get(facet);
                if (itemPropertyDescriptor != null) {
                    if (itemPropertyDescriptor.canSetProperty(property) == isSettable) {
                        return descriptorCache.get(facet);
                    }
                }
            }
        }

        ItemPropertyDescriptor result =
                RestrictionUtils.createPropertyDescriptor(getAdapterFactory(),
                        facet,
                        isSettable);
        descriptorCache.put(facet, result);
        return result;
    }

    private void addPrimitiveTypeRestrictionProperties(Property property,
            PrimitiveType pt, List<IItemPropertyDescriptor> result) {
        // XPD-4793 Globaldata: PrimitiveType restrictions for
        // AutoCaseIdentifiers are READ-ONLY.
        boolean isAutoCaseIdentifier =
                GlobalDataProfileManager.getInstance()
                        .isAutoCaseIdentifier(property);
        if (isAutoCaseIdentifier) {
            // get Restrictions for AutoCaseIdentifier
            getAutoCaseIdentifierDescriptor(property, pt, result);
            return;
        }
        ResourceSet rs =
                XpdResourcesPlugin.getDefault().getEditingDomain()
                        .getResourceSet();
        Collection<String> facets =
                PrimitivesUtil.getFacetPropertiesNames(rs, pt, property);
        if (facets != null) {
            for (String facet : facets) {
                ItemPropertyDescriptor descriptor =
                        getPropertyDescriptor(property, facet, true);
                if (descriptor != null) {
                    result.add(descriptor);
                }
            }
        }
    }

    /**
     * This method computes restrictions for AutoCaseIdentifier types.This is
     * specific to GlobalData Case type.
     * 
     * @param property
     * @param pt
     * @param result
     * @return
     */
    private void getAutoCaseIdentifierDescriptor(Property property,
            PrimitiveType pt, List<IItemPropertyDescriptor> result) {
        ResourceSet rs =
                XpdResourcesPlugin.getDefault().getEditingDomain()
                        .getResourceSet();
        Collection<String> facets =
                PrimitivesUtil.getFacetPropertiesNames(rs, pt, property);
        if (facets != null) {
            for (String facet : facets) {
                ItemPropertyDescriptor descriptor = null;
                // Set all properties READ-ONLY
                descriptor = getPropertyDescriptor(property, facet, false);
                if (descriptor != null) {
                    result.add(descriptor);
                }
            }
        }
    }

    /**
     * 
     */
    @Override
    public Object getImage(Object object) {
        Property property = getProperty(object);
        Overlays overlay = Overlays.select(property);
        Image image = null;
        Stereotype st = null;
        if (overlay != null) {
            String imgId = overlay.name();

            if (BOMGlobalDataUtils.isGlobalDataBOM(property.getModel())) {

                if (GlobalDataProfileManager.getInstance().isCID(property)) {
                    st =
                            GlobalDataProfileManager.getInstance()
                                    .getStereotype(StereotypeKind.CID);
                } else if (GlobalDataProfileManager.getInstance()
                        .isSearchable(property)) {
                    st =
                            GlobalDataProfileManager.getInstance()
                                    .getStereotype(StereotypeKind.SEARCHABLE);
                } else if (GlobalDataProfileManager.getInstance()
                        .isAutoCaseIdentifier(property)) {
                    st =
                            GlobalDataProfileManager
                                    .getInstance()
                                    .getStereotype(StereotypeKind.AUTO_CASE_IDENTIFIER);
                } else if (GlobalDataProfileManager.getInstance()
                        .isCompositeCaseIdentifier(property)) {
                    st =
                            GlobalDataProfileManager
                                    .getInstance()
                                    .getStereotype(StereotypeKind.COMPOSITE_CASE_IDENTIFIER);
                } else if (GlobalDataProfileManager.getInstance()
                        .isCaseState(property)) {
                    st =
                            GlobalDataProfileManager.getInstance()
                                    .getStereotype(StereotypeKind.CASE_STATE);
                }
            } else {
                /*
                 * If this has a first-class stereotype and it has it's own
                 * image then need to get the appropriate image from the
                 * registry.
                 */
                st = getFirstClassStereotype(property);
            }

            if (st != null && isPublicStereotype(st)) {
                imgId += "_" + st.getQualifiedName(); //$NON-NLS-1$
            }

            /*
             * Sid: Was causing class load errors, because ImageRegistry()
             * construction fails if not done on UI thread. Changed to load on
             * demand
             */
            if (registry == null) {
                if (Display.getCurrent() != null) {
                    registry = new ImageRegistry(Display.getCurrent());
                }
            }

            if (registry != null) {
                /*
                 * Id must now distinguish between different icons for different
                 * types.
                 */
                String baseImagePath = getBasePropertyImagePath(property);

                if (baseImagePath != null) {
                    imgId += "_" + baseImagePath; //$NON-NLS-1$
                }

                image = registry.get(imgId);
                if (image == null) {
                    Image base = getBaseImage(property);
                    URL resource =
                            Activator
                                    .getDefault()
                                    .getBundle()
                                    .getEntry("icons/items/multiplicity_ovr/" + overlay.name() + ".gif"); //$NON-NLS-1$ //$NON-NLS-2$
                    ImageDescriptor ovrDesc =
                            ExtendedImageRegistry.getInstance()
                                    .getImageDescriptor(resource);
                    registry.put(imgId,
                            new OverlayImageDescriptor(base.getImageData(),
                                    ovrDesc.getImageData()));
                    image = registry.get(imgId);
                }
            }

        } else {
            image = getBaseImage(property);
        }
        return image;
    }

    /**
     * Get the image of this property. If this property has first-class
     * stereotype applied then it will attempt to get the stereotype image if
     * one is defined.
     * 
     * @param property
     * @return
     */
    private Image getBaseImage(Property property) {
        // Set first-class profile image if needed
        Image img = null;

        if ((property.getModel()) != null
                && BOMGlobalDataUtils.isGlobalDataBOM(property.getModel())) {
            Stereotype globalDataStereotype = null;

            globalDataStereotype =
                    GlobalDataProfileManager.getInstance()
                            .getAppliedGlobalDataStereotype(property);
            if (globalDataStereotype != null) {
                return getStereotypeImage(globalDataStereotype);
            }
        }

        /*
         * Sid ACE-1496 In BOM editor Standardise the base data type icons with
         * those used in processes
         */
        img = getEquivalentProcessDataImage(property);

        if (img == null) {
            img = getFirstClassStereotypeImage(property);
        }

        return img != null ? img : Activator.getDefault().getImageRegistry()
                .get(BOMImages.PROPERTY);
    }

    /**
     * @param property
     * @return The process data equivalent image for the given property type.
     */
    private Image getEquivalentProcessDataImage(Property property) {
        String imagePath = getBasePropertyImagePath(property);

        if (imagePath != null) {
            return Activator.getDefault().getImageRegistry().get(imagePath);
        }
        
        return null;
    }

    /**
     * @param property
     * @return the plugin relative path of the image for the given property type
     *         or null if not one handled here.
     */
    protected String getBasePropertyImagePath(Property property) {
        String imageName = null;
        
        Type type = property.getType();

        if (type instanceof PrimitiveType) {
            PrimitiveType pType =
                    PrimitivesUtil.getBasePrimitiveType((PrimitiveType) type);

            if (pType != null) {
                String typeName = pType.getName();

                if (PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME
                        .equals(typeName)) {
                    imageName = BOMImages.PROCESS_DATA_FIELD_BOOLEAN;

                } else if (PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME
                        .equals(typeName)
                        || PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME
                                .equals(typeName)
                        || PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME
                                .equals(typeName)
                        || PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME
                                .equals(typeName)) {
                    imageName = BOMImages.PROCESS_DATA_FIELD_DATETIME;

                } else if (PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME
                        .equals(typeName)) {
                    imageName = BOMImages.PROCESS_DATA_FIELD_FLOAT;

                } else if (PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME
                        .equals(typeName)) {
                    imageName = BOMImages.PROCESS_DATA_FIELD_INT;

                } else if (PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME
                        .equals(typeName)) {
                    imageName = BOMImages.PROCESS_DATA_FIELD_STRING;

                } else if (PrimitivesUtil.BOM_PRIMITIVE_URI_NAME
                        .equals(typeName)) {
                    imageName = BOMImages.PROCESS_DATA_FIELD_URI;

                } else if (PrimitivesUtil.BOM_PRIMITIVE_CASEREFERENCE_NAME.equals(typeName)) {
                    /*
                     * Sid ACE-3047 Handle case reference property types (used for case reference fields in the "data"
                     * object
                     */
                    imageName = BOMImages.PROCESS_DATA_FIELD_CASEREFERENCE;
                }
            }

        } else if (type instanceof org.eclipse.uml2.uml.Class) {
            imageName = BOMImages.PROCESS_DATA_FIELD_COMPLEXTYPE;

        } else if (type instanceof Enumeration) {
            imageName = BOMImages.PROCESS_DATA_FIELD_ENUM;

        } else {
            imageName = BOMImages.PROCESS_DATA_FIELD;
        }
        return imageName;
    }

    /**
     */
    @Override
    public String getText(Object object) {
        Property property = getProperty(object);
        String text = super.getText(object);
        Type type = property.getType();
        if (type != null && !type.eIsProxy() && type.getName() != null
                && type.getName().length() > 0) {
            text = text + " : " + type.getName(); //$NON-NLS-1$
        }
        return text == null ? "" : text; //$NON-NLS-1$
    }

    private Property getProperty(Object object) {
        return ((org.eclipse.uml2.uml.Property) object);
    }

    @Override
    public Collection<?> getChildren(Object object) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Object getParent(Object object) {
        return getProperty(object).getClass_();
    }

    @Override
    protected Command getSetCommand(Object object, EditingDomain editingDomain,
            CommandParameter parameter) {

        Command cmd = super.getSetCommand(object, editingDomain, parameter);

        EStructuralFeature structuralFeature =
                parameter.getEStructuralFeature();

        if (structuralFeature == UMLPackage.eINSTANCE.getTypedElement_Type()
                && (object instanceof Property)
                && (parameter.value instanceof PrimitiveType)) {

            // Depending on destination environment, may need to set the default
            // subtype if changing to a Decimal or Integer
            IProject project = WorkingCopyUtil.getProjectFor((EObject) object);

            boolean isIProcess =
                    GlobalDestinationUtil.isGlobalDestinationEnabled(project,
                            IP_GLOBAL_DESTINATION);

            if (isIProcess) {
                // If we setting the data type to Integer or Decimal we
                // need to return a Compound command appended with a command to
                // set the integer/decimal "subtype" appropriate to the iProcess
                // destination

                // So first, check that the new super type is either an Integer
                // or Decimal

                CompoundCommand cmpCmd = new CompoundCommand();
                cmpCmd.append(cmd);

                final String propertyName;
                final Object value;
                final PrimitiveType pt = (PrimitiveType) parameter.value;
                final Property prop = (Property) object;

                if (parameter.value == standardPrimTypeInteger) {
                    propertyName =
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_SUBTYPE;

                    Enumeration en =
                            PrimitivesUtil
                                    .getSubTypeEnumerationFromFacetPropertyValue(propertyName,
                                            pt);

                    if (en != null) {
                        value =
                                en.getOwnedLiteral(PrimitivesUtil.INTEGER_SUBTYPE_FIXEDLENGTH);
                    } else {
                        value = null;
                    }

                } else if (parameter.value == standardPrimTypeDecimal) {
                    propertyName =
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_SUBTYPE;

                    Enumeration en =
                            PrimitivesUtil
                                    .getSubTypeEnumerationFromFacetPropertyValue(propertyName,
                                            pt);

                    if (en != null) {
                        value =
                                en.getOwnedLiteral(PrimitivesUtil.DECIMAL_SUBTYPE_FIXEDPOINT);
                    } else {
                        value = null;
                    }

                } else {
                    propertyName = null;
                    value = null;
                }

                RecordingCommand recCmd =
                        new RecordingCommand(
                                (TransactionalEditingDomain) editingDomain) {

                            @Override
                            protected void doExecute() {
                                PrimitivesUtil.setFacetPropertyValue(pt,
                                        propertyName,
                                        value,
                                        prop);

                            }

                        };

                cmpCmd.append(recCmd);
                return cmpCmd;
            }
        }
        return cmd;
    }
}