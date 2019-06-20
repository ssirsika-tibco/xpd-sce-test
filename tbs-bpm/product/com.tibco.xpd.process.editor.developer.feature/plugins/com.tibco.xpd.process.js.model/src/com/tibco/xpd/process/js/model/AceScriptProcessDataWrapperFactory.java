/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.process.js.model;

import java.util.Collection;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.internal.resource.UMLResourceFactoryImpl;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.script.model.client.DefaultJsClass;
import com.tibco.xpd.script.model.client.DefaultUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RecordType;

/**
 * Script relevant data class that represents the "data" object that wraps all
 * process data in ACE.
 * 
 * Whereas in other versions of Studio, all process fields were top level data,
 * in ACE all process data is wrapped in an object called "data" (although, for
 * now this is set by the caller as I think this may be required for other
 * scenarios like to group together the input or output data on a sub-process
 * call mappings
 *
 * @author aallway
 * @since 3 Jun 2019
 */
public class AceScriptProcessDataWrapperFactory {

    private static final AceScriptProcessDataWrapperFactory DEFAULT =
            new AceScriptProcessDataWrapperFactory();

    /*
     * Sid ACE-542 removed the _$_PROCESS_DATA_WRAPPER_ prefix from "data"
     * object type as this will appear to user in problem marker if they do try
     * and assign something to the "data" object.
     */    
    
    public static AceScriptProcessDataWrapperFactory getDefault() {
        return DEFAULT;
    }

    /**
     * Keep a permanent resource set. As we load the primitive type definitions
     * and profiles etc into the resoruce set, creating a new one each time is
     * expensive (some initial testing showed something around 0.002 per call to
     * create wrapper object using a single resoruce set and 0.12 if we create
     * new resource set each time.
     */
    ResourceSetImpl wrapperResourceSet = new ResourceSetImpl();

    /**
     * Creates the script contribution for a top level data field that wraps the
     * given process data set. This is represented as a custom UML class with a
     * UML property of an equivalent type to the process data field.
     * 
     * @param wrapperObjectName
     *            The name of the top level data field that will be created to
     *            wrap the properties representing the given process data
     * @param dataSet
     *            The set of process data to wrap.
     * 
     * @return The script relevant data contribution for the top level data
     *         field that wraps the given process data set
     */
    public IScriptRelevantData createProcessDataWrapper(
            String wrapperObjectName, Collection<ProcessRelevantData> dataSet) {
        /*
         * Create a resource set and resource to place our temporary UML package
         * in (many of our utilities currently demand this in order to apply
         * profiles and stereotypes required for setting primitive type to fixed
         * point etc).
         * 
         * We remove the resource from resource set at the end so that we don't
         * get a build up (isn't needed for reading the stereotype facets.
         * 
         * We use a UUI for all in case we get called by multiple threads in
         * parallel (so we create a unique resource (then remove it at end)
         */
        @SuppressWarnings("restriction")
        Resource wrapperResource =
                UMLResourceFactoryImpl.INSTANCE.createResource(URI.createURI(
                        "__WRAPPER_RESOURCE_URI_" + wrapperObjectName + "_" //$NON-NLS-1$ //$NON-NLS-2$
                                + EcoreUtil.generateUUID()));

        wrapperResourceSet.getResources().add(wrapperResource);

        try {
            /*
             * Have to crate a UML package to house the class that houses the
             * properties that represent each process data. Otherwise we cannot
             * set number facets on properties for number fields.
             */
            org.eclipse.uml2.uml.Package wrapperPackage =
                    UMLFactory.eINSTANCE.createModel();

            wrapperResource.getContents().add(wrapperPackage);

            /*
             * Create the UML class that houses the properties that represent
             * each process data.
             */
            Class wrapperClass = UMLFactory.eINSTANCE.createClass();
            wrapperClass.setName(wrapperObjectName);

            wrapperPackage.getPackagedElements().add(wrapperClass);

            for (ProcessRelevantData data : dataSet) {
                /*
                 * Create and add the property before configuring it (needs to
                 * be in a package before certain stereotype facets are set.
                 */
                Property property = UMLFactory.eINSTANCE.createProperty();
                property.setName(data.getName());

                wrapperClass.getOwnedAttributes().add(property);

                /* Then configure it's type info. */
                configurePropertyTypeForDataField(wrapperResourceSet,
                        property,
                        data);
            }

            /*
             * Create the appropriate structure for the script contributions (a
             * DefaultJsClass wrapped in a DefaultUMLScriptRelevantData object.
             */
            DefaultJsClass jsClass = new DefaultJsClass(wrapperClass);

            DefaultUMLScriptRelevantData scriptData =
                    new DefaultUMLScriptRelevantData(wrapperObjectName,
                            wrapperObjectName,
                            false, jsClass);

            scriptData.setIcon(
                    Xpdl2ResourcesPlugin.getDefault().getImageRegistry()
                            .get(Xpdl2ResourcesConsts.IMG_DATAFIELD_EXTERNALREFERENCE));

            return scriptData;

        } finally {
            /*
             * REMOVE that asset from the resource set (there is no tear-down
             * lifecycle we can plugin into so we have to as we don't want a
             * build up of resources.
             * 
             * As far as I can tell, once removed from resource set, it is still
             * possible to read the primitive type facets stereo etc So
             * downstream things using the wrapper class and properties should
             * work fine.
             */
            wrapperResourceSet.getResources().remove(wrapperResource);
        }

    }

    /**
     * Create a UML property that is the equivalent type of the given process
     * data.
     * 
     * @param wrapperResourceSet
     * @param property
     * @param data
     */
    private void configurePropertyTypeForDataField(
            ResourceSetImpl wrapperResourceSet, Property property,
            ProcessRelevantData data) {

        Object baseType =
                BasicTypeConverterFactory.INSTANCE.getBaseType(data, false);

        /*
         * TODO Handle case reference types... For NOW we will set it to unknown
         * (Object) type (although it is actually a string, we don't want user
         * to be able to fiddle about with it.
         * 
         * When we implement the case data scripting, which as changed to all
         * static methods that take case references as parameters, then we will
         * need to be able to distinguish Case Reference properties properly.
         * 
         * We cannot do it like we used to because we can't add a UML Script
         * relevant data object as a bom property in the data object (and we
         * wouldn't want to because all the methods are static now). Instead, I
         * think the approach will be to add a new internal case reference type
         * to CdsJavaScript.uml and et the property to that type here (or even
         * PrimitiveType.uml PROVIDED we can keep it hidden). The static cae
         * access methods will then use this type as their parameter type.
         */
        if (data.getDataType() instanceof RecordType) {
            // TODO for now we will treat them as strings type
            // Later when we do case data scripting then we will have to
            // consider how we validate that these are what they say they are in
            // the new Static Case Data scripting class methods
            property.setType(PrimitivesUtil.getStandardPrimitiveTypeByName(
                    wrapperResourceSet,
                    PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME));

        } else if (baseType instanceof BasicType) {
            /* Handle simple types first. */

            BasicType basicType = (BasicType) baseType;
            BasicTypeType basicTypeType = ((BasicType) baseType).getType();

            if (BasicTypeType.STRING_LITERAL.equals(basicTypeType)) {
                property.setType(PrimitivesUtil.getStandardPrimitiveTypeByName(
                        wrapperResourceSet,
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME));

            } else if (BasicTypeType.BOOLEAN_LITERAL.equals(basicTypeType)) {
                property.setType(PrimitivesUtil.getStandardPrimitiveTypeByName(
                        wrapperResourceSet,
                        PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME));

            } else if (BasicTypeType.DATE_LITERAL.equals(basicTypeType)) {
                property.setType(PrimitivesUtil.getStandardPrimitiveTypeByName(
                        wrapperResourceSet,
                        PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME));

            } else if (BasicTypeType.DATETIME_LITERAL.equals(basicTypeType)) {
                property.setType(PrimitivesUtil.getStandardPrimitiveTypeByName(
                        wrapperResourceSet,
                        PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME));

            } else if (BasicTypeType.FLOAT_LITERAL.equals(basicTypeType)) {
                /* For number's there's a bit more to the configuration. */
                setupNumberProperty(wrapperResourceSet, property, basicType);

            } else if (BasicTypeType.INTEGER_LITERAL.equals(basicTypeType)) {
                property.setType(PrimitivesUtil.getStandardPrimitiveTypeByName(
                        wrapperResourceSet,
                        PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME));

            } else if (BasicTypeType.PERFORMER_LITERAL.equals(basicTypeType)) {
                property.setType(PrimitivesUtil.getStandardPrimitiveTypeByName(
                        wrapperResourceSet,
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME));

            } else if (BasicTypeType.TIME_LITERAL.equals(basicTypeType)) {
                property.setType(PrimitivesUtil.getStandardPrimitiveTypeByName(
                        wrapperResourceSet,
                        PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME));

            } else {
                /* Default to the generic Object type. */
                property.setType(PrimitivesUtil.getStandardPrimitiveTypeByName(
                        wrapperResourceSet,
                        PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME));
            }

        } else if (baseType instanceof Class) {
            property.setType((Class) baseType);

        } else {
            /* Default to the generic Object type. */
            property.setType(PrimitivesUtil.getStandardPrimitiveTypeByName(
                    wrapperResourceSet,
                    PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME));
        }

        /*
         * Make it a list property if it's an array field
         */
        property.setLower(0);
        if (data.isIsArray()) {
            property.setUpper(-1);
        }

        return;
    }

    /**
     * Given a property set it up according to the process data basic
     * configuration.
     * 
     * @param wrapperResourceSet
     * @param property
     *            the property to change
     * @param basicType
     *            the configuration for process data
     */
    protected void setupNumberProperty(ResourceSetImpl wrapperResourceSet,
            Property property, BasicType basicType) {
        PrimitiveType decimalType = PrimitivesUtil
                .getStandardPrimitiveTypeByName(wrapperResourceSet,
                        PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME);

        property.setType(decimalType);

        if (basicType.getScale() != null
                && basicType.getScale().getValue() >= 0) {
            /*
             * Number with decimal places must be defined as a fixed point
             * property.
             */
            PrimitivesUtil.setFacetPropertyValue(decimalType,
                    PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_SUBTYPE,
                    PrimitivesUtil.DECIMAL_SUBTYPE_FIXEDPOINT,
                    property);

            PrimitivesUtil.setFacetPropertyValue(decimalType,
                    PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_PLACES,
                    (int) basicType.getScale().getValue(),
                    property);

            int length = PrimitivesUtil.MAX_FIXED_POINT_NUMBER_LENGTH;
            if (basicType.getPrecision() != null
                    && basicType.getPrecision().getValue() > 0) {
                length = basicType.getPrecision().getValue();
            }

            PrimitivesUtil.setFacetPropertyValue(decimalType,
                    PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LENGTH,
                    length,
                    property);

        } else {
            // Floating point number has all default config
        }
    }

}
