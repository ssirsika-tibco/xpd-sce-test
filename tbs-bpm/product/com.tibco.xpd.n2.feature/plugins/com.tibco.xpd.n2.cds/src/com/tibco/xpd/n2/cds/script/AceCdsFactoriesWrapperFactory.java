/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.cds.script;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.internal.resource.UMLResourceFactoryImpl;

import com.tibco.bds.designtime.generator.BDSUtils;
import com.tibco.bds.designtime.generator.CDSBOMIndexerService;
import com.tibco.xpd.analyst.resources.xpdl2.ReservedWords;
import com.tibco.xpd.n2.cds.internal.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.script.model.client.DefaultJsClass;
import com.tibco.xpd.script.model.client.DefaultUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.jscript.JScriptUtils;

/**
 * Factory for creating the JavaScript ScriptRelevantData object(s) for BOM
 * class creation factories and package classes (containing enumerations).
 *
 * @author aallway
 * @since 19 Jun 2019
 */
public class AceCdsFactoriesWrapperFactory {

    public static AceCdsFactoriesWrapperFactory getDefault() {
        return DEFAULT;
    }

    private static final AceCdsFactoriesWrapperFactory DEFAULT =
            new AceCdsFactoriesWrapperFactory();


    /**
     * The BOM package enumerations set class suffix. This is the suffix to the
     * type name used for the dynamically generated UML class (as in
     * com_my_bom_enumerations).
     */
    private static final String BOM_PKG_ENUM_CLASS_SUFFIX = "_enumerations_" + DefaultJsClass.BOM_PKG_SUFFIX; //$NON-NLS-1$

    /**
     * The BOM package enumeration class suffix. This is the suffix to the type
     * name used for the dynamically generated UML class (as in
     * com_my_bom_Colours_enumerations).
     */
    private static final String BOM_ENUM_CLASS_SUFFIX = DefaultJsClass.BOM_ENUM_SUFFIX;

    /**
     * Keep a permanent resource set. As we load the primitive type definitions
     * and profiles etc into the resource set, creating a new one each time is
     * expensive (some initial testing showed something around 0.002 per call to
     * create wrapper object using a single resoruce set and 0.12 if we create
     * new resource set each time.
     */
    ResourceSetImpl wrapperResourceSet = new ResourceSetImpl();

    /**
     * Factory for creating the JavaScript ScriptRelevantData object(s) for BOM
     * class creation factories and package classes (containing enumerations)
     * top level data field that wraps all of the BOM package properties that
     * are factory for creating Class objects within the BOM packages related
     * (via data) with the process.
     * 
     * @param wrapperObjectName
     *            The name of the top level data field that will be created to
     *            was the factory wrapper
     * @param bomPackages
     *            The set of BOM packages to wrap.
     * 
     * @return The script relevant data contribution for the top level data
     *         field that wraps the given process data set
     */
    public List<IScriptRelevantData> createProcessDataWrapper(
            Collection<Package> bomPackages) {
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
        Resource wrapperResource = UMLResourceFactoryImpl.INSTANCE
                .createResource(URI.createURI("__FACTORY_WRAPPER_RESOURCE_URI_" //$NON-NLS-1$
                        + EcoreUtil.generateUUID()));

        wrapperResourceSet.getResources().add(wrapperResource);

        try {
            /*
             * Have to crate a UML package to house the class that houses the
             * properties that represent each BOM package's factory.
             */
            org.eclipse.uml2.uml.Package wrapperPackage =
                    UMLFactory.eINSTANCE.createModel();

            wrapperResource.getContents().add(wrapperPackage);

            /*
             * Create the UML class that houses the properties that represent
             * each BOM factory.
             */
            Class factoryWrapperClass = UMLFactory.eINSTANCE.createClass();
            factoryWrapperClass
                    .setName(ReservedWords.BOM_FACTORY_WRAPPER_OBJECT_NAME);

            wrapperPackage.getPackagedElements().add(factoryWrapperClass);

            /*
             * Create the UML class that houses the properties that represent
             * the package container for each BOM enumeration.
             */
            Class packageWrapperClass = UMLFactory.eINSTANCE.createClass();
            packageWrapperClass
                    .setName(ReservedWords.BOM_PACKAGE_WRAPPER_OBJECT_NAME);

            wrapperPackage.getPackagedElements().add(packageWrapperClass);

            for (Package bomPackage : bomPackages) {
                /*
                 * Create and add the property representing the factory for a
                 * give package.
                 */
                Property factoryPkgProperty =
                        UMLFactory.eINSTANCE.createProperty();

                String factoryName = CDSBOMIndexerService.getInstance()
                        .getCDSFactoryForPackage(bomPackage);
                factoryPkgProperty.setName(factoryName);
                factoryPkgProperty.setIsReadOnly(true);

				/* Sid ACE-8307 provide some popup help guidance for static classes. */
				Comment comment = UMLFactory.eINSTANCE.createComment();
				comment.setBody(
						String.format(Messages.AceCdsFactoriesWrapperFactory_BomPkgFactory_Content_Assist_Popup_Help,
								bomPackage.getName()));
				factoryPkgProperty.getOwnedComments().add(comment);

                factoryWrapperClass.getOwnedAttributes()
                        .add(factoryPkgProperty);

                /* Then configure it's type info. */
                configurePropertyForFactory(wrapperPackage,
                        factoryName,
                        factoryPkgProperty,
                        bomPackage);

                /*
                 * Create and add the property that represent the package
                 * container for each BOM enumeration.
                 */
                Property enumPkgProperty =
                        UMLFactory.eINSTANCE.createProperty();
                enumPkgProperty.setName(factoryName);
                enumPkgProperty.setIsReadOnly(true);

				/* Sid ACE-8307 provide some popup help guidance for static classes. */
				Comment comment2 = UMLFactory.eINSTANCE.createComment();
				comment2.setBody(
						String.format(Messages.AceCdsFactoriesWrapperFactory_BomPkgEnum_Content_Assist_Popup_Help, bomPackage.getName()));
				enumPkgProperty.getOwnedComments().add(comment2);

                packageWrapperClass.getOwnedAttributes().add(enumPkgProperty);

                /* Then configure it's type info. */
                configurePropertyForEnums(wrapperPackage,
                        factoryName,
                        enumPkgProperty,
                        bomPackage);

            }

            List<IScriptRelevantData> dataList =
                    new ArrayList<IScriptRelevantData>();

            /*
             * Create the appropriate structure for the script contributions (a
             * DefaultJsClass wrapped in a DefaultUMLScriptRelevantData object.
             */
			/* Sid ACE-8307 provide some popup help guidance for static classes. */
			IScriptRelevantData factoryRelevantData = createScriptRelevantDataForClass(factoryWrapperClass);
			factoryRelevantData.setAdditionalInfo(
					Messages.AceCdsFactoriesWrapperFactory_BomFactory_Content_Assist_Popup_Help);
			dataList.add(factoryRelevantData);

			IScriptRelevantData pkgRelevantData = createScriptRelevantDataForClass(packageWrapperClass);
			pkgRelevantData.setAdditionalInfo(
					Messages.AceCdsFactoriesWrapperFactory_BomPkg_Content_Assist_Popup_Help);
			dataList.add(pkgRelevantData);

            return dataList;

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
     * @param clazz
     * @return A script relevant data object to wrap the given class.
     */
    private IScriptRelevantData createScriptRelevantDataForClass(Class clazz) {
        DefaultJsClass factoryJsClass = new DefaultJsClass(clazz);

        DefaultUMLScriptRelevantData scriptData =
                new DefaultUMLScriptRelevantData(clazz.getName(),
                        clazz.getName(), false, factoryJsClass);

        scriptData.setReadOnly(true);

		/*
		 * Sid ACE-8307 for consistency use static JS class icon for content assisdt rather than complex type datafield
		 * icon.
		 */
		if (!XpdResourcesPlugin.isInHeadlessMode())
		{
			scriptData.setIcon(JScriptUtils.getDefaultJavascriptIcon());
		}

        return scriptData;
    }

    /**
     * Update the property with a class generated with all of the creator
     * methods required for all classes in the package.
     * 
     * @param wrapperPackage
     * @param factoryName
     * 
     * @param wrapperResourceSet
     * @param factoryPkgProperty
     * @param bomPackage
     */
    private void configurePropertyForFactory(Package wrapperPackage,
            String factoryName, Property factoryPkgProperty,
            Package bomPackage) {
        /*
         * The factory property will be a class that contains create methods for
         * all classes in the BOM.
         */
        Class factoryClass = UMLFactory.eINSTANCE.createClass();
        factoryClass.setName(factoryName + "_factory" + DefaultJsClass.BOM_PKG_SUFFIX); //$NON-NLS-1$
        wrapperPackage.getPackagedElements().add(factoryClass);

        factoryPkgProperty.setType(factoryClass);

        for (EObject element : bomPackage.eContents()) {
            if (element instanceof Class) {
                Operation classCreatorMethod =
                        UMLFactory.eINSTANCE.createOperation();
                classCreatorMethod.setName(getFactoryClassCreatorMethodName((Class) element));
                classCreatorMethod.setType((Class) element);

                factoryClass.getOwnedOperations().add(classCreatorMethod);
            }
        }
    }

    /**
     * Sid ACE-2896 Get the BOM factory class creator method name.
     * 
     * This is "createBomClassName" where the B in Bom is forced to uppercase.
     * 
     * @param clazz
     * 
     * @return The BOM factory class creator method name
     */
    public String getFactoryClassCreatorMethodName(Classifier clazz) {
        /* Sid ACE-3153 Moved to BDSUtils so that it can be shared. */
        return BDSUtils.getFactoryClassCreatorMethodName(clazz.getName());
    }

    /**
     * Update the property with a class generated with properties representing
     * all of the ENUMs in given package, each of these being a class
     * representing the enumeration and having properties for each enum literal
     * as a string property named after the literal. as JS text properties.
     * 
     * @param wrapperPackage
     * @param factoryName
     * @param enumPkgProperty
     * @param bomPackage
     */
    private void configurePropertyForEnums(Package wrapperPackage,
            String factoryName, Property enumPkgProperty, Package bomPackage) {

        /*
         * Create the class for the package (will contain property for each
         * enumeration type.
         */
        Class pkgEnumsClass = UMLFactory.eINSTANCE.createClass();
        pkgEnumsClass.setName(factoryName + BOM_PKG_ENUM_CLASS_SUFFIX);
        wrapperPackage.getPackagedElements().add(pkgEnumsClass);

        enumPkgProperty.setType(pkgEnumsClass);

        for (EObject element : bomPackage.eContents()) {
            if (element instanceof Enumeration) {
                Enumeration enumeration = (Enumeration) element;

                /*
                 * Create the class for the enumeration and add to the pkg
                 * enumerations class.
                 */
                Class enumClass = UMLFactory.eINSTANCE.createClass();
                enumClass.setName(factoryName + "_" + enumeration.getName() //$NON-NLS-1$
                        + BOM_ENUM_CLASS_SUFFIX);

                Property enumProperty = UMLFactory.eINSTANCE.createProperty();
                enumProperty.setName(enumeration.getName());
                enumProperty.setType(enumClass);
                enumProperty.setIsReadOnly(true);

                pkgEnumsClass.getOwnedAttributes().add(enumProperty);

                /*
                 * The create the properties in the enum class that represent
                 * each enum literal.
                 */
                for (EnumerationLiteral enumLiteral : enumeration
                        .getOwnedLiterals()) {
                    Property enumLiteralProperty =
                            UMLFactory.eINSTANCE.createProperty();

                    enumLiteralProperty
                            .setName(enumLiteral.getName().toUpperCase());
                    enumLiteralProperty.setType(enumeration);
                    enumLiteralProperty.setIsReadOnly(true);

                    enumClass.getOwnedAttributes().add(enumLiteralProperty);
                }
            }
        }
    }

}
