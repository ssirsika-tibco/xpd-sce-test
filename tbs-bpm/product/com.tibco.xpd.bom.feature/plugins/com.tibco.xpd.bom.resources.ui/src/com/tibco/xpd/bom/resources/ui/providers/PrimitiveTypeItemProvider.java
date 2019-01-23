/*
 */
package com.tibco.xpd.bom.resources.ui.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
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
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.swt.widgets.Control;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.ui.BOMImages;
import com.tibco.xpd.bom.resources.ui.internal.properties.RestrictionUtils;
import com.tibco.xpd.bom.resources.ui.picker.BOMPicker;
import com.tibco.xpd.bom.resources.ui.picker.BOMPicker.BOMPickerType;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.destinations.GlobalDestinationUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 */
public class PrimitiveTypeItemProvider extends TypeItemProvider {

    private static final String IP_GLOBAL_DESTINATION =
            "com.tibco.xpd.iprocess.globalDestination"; //$NON-NLS-1$

    PrimitiveType standardPrimTypeInteger;

    PrimitiveType standardPrimTypeDecimal;

    public PrimitiveTypeItemProvider(AdapterFactory adapterFactory) {
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

    @Override
    public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
        List<IItemPropertyDescriptor> result =
                new ArrayList<IItemPropertyDescriptor>();
        addPrimitiveTypeRestrictionProperties(getPrimitiveType(object), result);
        return result;
    }

    private final Map<String, ItemPropertyDescriptor> descriptorCache =
            new HashMap<String, ItemPropertyDescriptor>();

    private ItemPropertyDescriptor getPropertyDescriptor(String facet) {

        if (descriptorCache.containsKey(facet)) {
            ItemPropertyDescriptor pdesc = descriptorCache.get(facet);
            return pdesc;
        }

        ItemPropertyDescriptor result =
                RestrictionUtils.createPropertyDescriptor(getAdapterFactory(),
                        facet);
        descriptorCache.put(facet, result);
        return result;
    }

    /**
     * @param pt
     * @param result
     */
    private void addPrimitiveTypeRestrictionProperties(PrimitiveType pt,
            List<IItemPropertyDescriptor> result) {

        ResourceSet rs =
                XpdResourcesPlugin.getDefault().getEditingDomain()
                        .getResourceSet();
        Collection<String> facets =
                PrimitivesUtil.getFacetPropertiesNames(rs, pt, null);

        if (facets != null) {
            for (String facet : facets) {
                ItemPropertyDescriptor descriptor =
                        getPropertyDescriptor(facet);
                if (descriptor != null) {
                    result.add(descriptor);
                }
            }
        }
    }

    /**
     */
    public Object getImage(Object object) {
        return Activator.getDefault().getImageRegistry()
                .get(BOMImages.PRIMITIVE_TYPE);
    }

    private PrimitiveType getPrimitiveType(Object object) {
        return ((org.eclipse.uml2.uml.PrimitiveType) object);
    }

    public Collection<?> getChildren(Object object) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Object getParent(Object object) {
        return getPrimitiveType(object).getPackage();
    }

    @Override
    protected Command getAddCommand(Object object, EditingDomain editingDomain,
            CommandParameter parameter) {

        Command cmd = super.getAddCommand(object, editingDomain, parameter);

        EStructuralFeature structuralFeature =
                parameter.getEStructuralFeature();

        if (structuralFeature == UMLPackage.eINSTANCE
                .getClassifier_Generalization()
                && (object instanceof PrimitiveType)
                && !parameter.getCollection().isEmpty()) {

            // Depending on destination environment, may need to set the default
            // subtype if changing to a Decimal or Integer
            IProject project = WorkingCopyUtil.getProjectFor((EObject) object);

            boolean isIProcess =
                    GlobalDestinationUtil.isGlobalDestinationEnabled(project,
                            IP_GLOBAL_DESTINATION);

            if (isIProcess) {
                // If we setting the specific PTs type to Integer or Decimal we
                // need to return a Compound command appended with a command to
                // set the integer/decimal "subtype" appropriate to the iProcess
                // destination

                // When we set type we are actually making the PrimitiveType a
                // subclass of its data type PrimitiveType. I.e. it contains a
                // generalization with the data type as the general

                // So first, check that the new super type is either an Integer
                // or Decimal

                final PrimitiveType specificPT = (PrimitiveType) object;

                // Pull out the PT that will be the general
                Collection<?> collection = parameter.getCollection();
                Generalization newGeneralization = null;
                Classifier general = null;

                for (Object obj : collection) {
                    if (obj instanceof Generalization) {
                        newGeneralization = (Generalization) obj;
                        general = newGeneralization.getGeneral();

                        // Should only be one generalization so break here
                        break;
                    }
                }

                if ((general == standardPrimTypeInteger)
                        || (general == standardPrimTypeDecimal)) {
                    // Generate the Compound command
                    return (getAddCommandWithSubTypeChange(cmd,
                            editingDomain,
                            specificPT,
                            (PrimitiveType) general));
                }

            }
        }

        return cmd;
    }

    /**
     * If a PrimitiveType is extending Decimal or Integer then this method sets
     * the subtype to iProcess specific defaults. It needs to create a
     * RecordingCommand to do this and is wrapped up with the existing prepared
     * command inside a CompoundCommand which is returned.
     * 
     * @param cmd
     *            The command that has already been prepared and needs to be
     *            included in the compound command
     * @param editingDomain
     * @param specificPT
     *            Primitive type which is the specific of the generalization
     * @param superType
     *            The PrimitiveType that will be the general of the
     *            generalization i.e. the data type that specificPT extends
     * @return CompoundCommand
     */
    private Command getAddCommandWithSubTypeChange(Command cmd,
            EditingDomain editingDomain, final PrimitiveType specificPT,
            PrimitiveType superType) {

        CompoundCommand cmpCmd = new CompoundCommand();
        cmpCmd.append(cmd);

        final String propertyName;
        final Object value;

        if (superType == standardPrimTypeInteger) {
            propertyName = PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_SUBTYPE;
            Enumeration en =
                    PrimitivesUtil
                            .getSubTypeEnumerationFromFacetPropertyValue(propertyName,
                                    superType);

            if (en != null) {
                value =
                        en
                                .getOwnedLiteral(PrimitivesUtil.INTEGER_SUBTYPE_FIXEDLENGTH);
            } else {
                value = null;
            }
        } else if (superType == standardPrimTypeDecimal) {
            propertyName = PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_SUBTYPE;
            Enumeration en =
                    PrimitivesUtil
                            .getSubTypeEnumerationFromFacetPropertyValue(propertyName,
                                    superType);

            if (en != null) {
                value =
                        en
                                .getOwnedLiteral(PrimitivesUtil.DECIMAL_SUBTYPE_FIXEDPOINT);
            } else {
                value = null;
            }
        } else {
            propertyName = null;
            value = null;
        }

        RecordingCommand recCmd =
                new RecordingCommand((TransactionalEditingDomain) editingDomain) {

                    @Override
                    protected void doExecute() {
                        PrimitivesUtil.setFacetPropertyValue(specificPT,
                                propertyName,
                                value);
                    }

                };

        cmpCmd.append(recCmd);

        return cmpCmd;
    }

    /**
     * 
     * If the supplied PrimitiveType has a subtype and it contains an
     * EnumerationLiteral matching the propertyName then that Enumeration is
     * returned
     * 
     * @param propertyName
     *            subtype name
     * @param primType
     *            The primitive type that has a subtype
     * @return Enumeration
     */
    // private Enumeration getSubTypeEnumerationFromFacetPropertyValue(
    // String propertyName, PrimitiveType primType) {
    // Enumeration enumeration = null;
    //
    // Object facetPropertyValue =
    // PrimitivesUtil.getFacetPropertyValue(primType, propertyName);
    //
    // if (facetPropertyValue instanceof EnumerationLiteral) {
    // enumeration =
    // ((EnumerationLiteral) facetPropertyValue).getEnumeration();
    // }
    //
    // return enumeration;
    //
    // }
    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.bom.resources.ui.providers.TypeItemProvider#getSuperTypePicker
     * (org.eclipse.swt.widgets.Control)
     */
    @Override
    protected BOMPicker getSuperTypePicker(Control parentControl) {
        return new BOMPicker(parentControl.getShell(), BOMPickerType.PRIMITIVE);
    }

}