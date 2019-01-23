/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.xsdtransform.api;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.bom.resources.utils.BOMProfileUtils;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.xsdtransform.XsdStereotypeUtils;
import com.tibco.xpd.bom.xsdtransform.exports.BOM2XMLTransformer;
import com.tibco.xpd.bom.xsdtransform.exports.template.TransformHelper;
import com.tibco.xpd.bom.xsdtransform.imports.XSD2BOMTransformer;
import com.tibco.xpd.bom.xsdtransform.internal.BaseBOMXtendTransformer;
import com.tibco.xpd.bom.xsdtransform.utils.NamespaceURIToJavaPackageMapper;
import com.tibco.xpd.bom.xsdtransform.utils.TopLevelElementWrapper;
import com.tibco.xpd.bom.xsdtransform.utils.XSDSequenceWrapper;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * This class is an api for all XSD transformation related operations.
 * 
 * @author glewis, njpatel
 * 
 */
public class XSDUtil {

    /**
     * Returns the actual output XSD File Names that would get generated if a
     * transformation from BOM to XML Schema was to take place
     * <p>
     * <b> This method is RECURSIVE - it will return all the xsd files that
     * would be generated from THIS BOM and every BOM it imports and so on.
     * 
     * @param source
     *            - the BOM file that we are to inspect
     * @return - Returns an collection of Strings holding the names of the XML
     *         Schema files that would be produced if a transformation from
     *         BOM-XSD was performed.
     */
    public static Collection<String> getOutputFileNamesForBOMFile(IFile source) {
        /*
         * Sid XPD-1605: TLEDependancyProvider now accounts for
         * top-level-element stereotypes that reference complex type classes in
         * other bom's. Therefore we can just get the working copy dependencies.
         */

        long start = System.currentTimeMillis();

        HashSet<String> names = new HashSet<String>();

        /* Add the output file name for the source bomFile */
        addOutputXsdFileNames(source, names);

        /*
         * Add the names of xsd's that the BOM's that this BOM references
         * (baseBOMXtendTransformer.getAllDependencies() is recursive!)
         */
        Set<IFile> dependencies = new HashSet<IFile>();
        BaseBOMXtendTransformer.getAllDependencies(source, dependencies);

        for (IResource dependency : dependencies) {
            addOutputXsdFileNames(dependency, names);
        }

        return names;
    }

    /**
     * @param bomFile
     * @return The names of XSD files that would be generated from THIS bom
     *         file. This does NOT recurs thru imports of other BOMs.
     */
    public static Collection<String> getOutputXsdFileNames(IResource bomFile) {
        Set<String> names = new HashSet<String>();

        addOutputXsdFileNames(bomFile, names);

        return names;
    }

    /**
     * Add the output file name for the source bomFile (i.e. the XSD for each of
     * the models in the bom).
     * 
     * @param source
     * @param names
     */
    private static void addOutputXsdFileNames(IResource source,
            Set<String> names) {
        WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(source);
        if (wc != null && wc.getRootElement() instanceof Model) {
            names.addAll(getOutputFileNames((Package) wc.getRootElement()));
        }
    }

    /**
     * This will return the expected output file name (XSD) for the given
     * package and any packages it contains.
     * 
     * @param pkg
     * @return
     */
    private static List<String> getOutputFileNames(Package pkg) {

        List<String> names = new ArrayList<String>();

        if (pkg != null) {
            String name = BOMWorkingCopy.getQualifiedPackageName(pkg) + ".xsd"; //$NON-NLS-1$
            names.add(name);

            for (PackageableElement elem : pkg.getPackagedElements()) {
                if (elem instanceof Package) {
                    names.addAll(getOutputFileNames((Package) elem));
                }
            }
        }
        return names;
    }

    /**
     * Gets namespace for any given package
     * 
     * @param pkg
     *            - The BOM package we wish to inspect - if null method returns
     *            a blank String
     * @return - Returns a String holding the Namespace value
     */
    public static String getNamespaceForPackage(Package pkg) {
        if (pkg != null) {
            return TransformHelper.getNamespace(pkg, true);
            //            return BOMWorkingCopy.getQualifiedPackageName(pkg) + ".xsd"; //$NON-NLS-1$
        }
        return ""; //$NON-NLS-1$
    }

    /**
     * Tranforms the given namespace URI into a legal Java package name.
     * 
     * @param project
     *            project of the bom file or bom package. Can be null
     * @param nsURI
     *            the namespace URI
     * @return the corresponding Java package name that should be used for
     *         generated code corresponding to the schema with the provided
     *         namespace URI
     */
    public static String getJavaPackageNameFromNamespaceURI(IProject project,
            String nsURI) {
        // Delegate to implementation in util class
        return NamespaceURIToJavaPackageMapper
                .getJavaPackageNameFromNamespaceURI(project, nsURI);
    }

    /**
     * Transforms a BOM Model to an XML Schema. If source or xsdFilePath are
     * null then the method returns an empty list.
     * 
     * @param source
     *            - The actual BOM Resource File to be transformed to XML Schema
     * @param finalTargetFolder
     *            Final target folder to copy generated XSD files (which are
     *            ALWAYS generated into appropriate .bom2xsd folder) to <b>or
     *            <code>null</code> if no onward copy to other folder
     *            required.</b>
     * @param isLocalWorkspaceTarget
     *            whether finalTargetFolder is in workspace (else in file system
     *            outside of workspace).
     * @param usePrefValidation
     *            - If true then only validates XML Schema if ticked in
     *            preferences else it always defaults to validating it each
     *            method call
     * @return - Returns any errors or warnings that occurred for inspection.
     */
    public static List<IStatus> transformBOMToXSD(final IFile source,
            final IPath finalTargetFolder, boolean usePrefValidation,
            boolean isLocalWorkspaceTarget) {
        return transformBOMToXSD(source,
                finalTargetFolder,
                usePrefValidation,
                null,
                isLocalWorkspaceTarget);
    }

    /**
     * Transforms a BOM Model to an XML Schema. If source or xsdFilePath are
     * null then the method returns an empty list.
     * 
     * @param source
     *            - The actual BOM Resource File to be transformed to XML Schema
     * @param finalTargetFolder
     *            Final target folder to copy generated XSD files (which are
     *            ALWAYS generated into appropriate .bom2xsd folder) to <b>or
     *            <code>null</code> if no onward copy to other folder
     *            required.</b>
     * @param isLocalWorkspaceTarget
     *            whether finalTargetFolder is in workspace (else in file system
     *            outside of workspace).
     * @param usePrefValidation
     *            - If true then only validates XML Schema if ticked in
     *            preferences else it always defaults to validating it each
     *            method call
     * @param monitor
     *            the progress monitor (or <code>null</code> if progress
     *            monitoring not required)
     * @return - Returns any errors or warnings that occurred for inspection.
     */
    public static List<IStatus> transformBOMToXSD(final IFile source,
            final IPath finalTargetFolder, boolean usePrefValidation,
            IProgressMonitor monitor, boolean isLocalWorkspaceTarget) {

        Bom2XsdCachedTransformer transformer =
                new Bom2XsdCachedTransformer(usePrefValidation,
                        Bom2XsdSourceValidationType.LIVE_VALIDATION);

        return transformer.transform(source,
                finalTargetFolder,
                isLocalWorkspaceTarget,
                monitor);
    }

    /**
     * Transforms any BOM Class to an XML Schema and only parses the
     * dependencies of that particular class selected. If umlClass is null then
     * method returns null.
     * 
     * @param umlClass
     *            - The actual BOM Class to be transformed into XML Schema ...
     *            obviously only its dependencies are carried across to Schema
     *            and not whole BOM file in this method.
     * @param xsdFilePath
     *            - This is the full XSD file path where you wish the
     *            transformed XML Schema to reside. It can be set to null if you
     *            don't want to physically create an XSD file in a particular
     *            location.
     * @param preserveSchemas
     *            - If this is set to true then this produces just 1 XML Schema
     *            using umlClass name with .xsd suffix. Otherwise the
     *            transformation will generate an XML Schema for each BOM
     *            package that holds any dependencies of the umlClass passed in.
     * @param result
     *            - Holds any problems that occurred on transformation - user
     *            can later inspect this
     * @param useEcoreNames
     *            - determines whether the export will generate ecore names for
     *            each construct
     * @param usePrefValidation
     *            - If true then only validates XML Schema if ticked in
     *            preferences else it always defaults to validating it each
     *            method call
     * 
     * @return - Returns the XSD Schema for inspection
     */
    public static Object incrementalTransformBOMToXSD(final Class umlClass,
            final String xsdFilePath, boolean preserveSchemas,
            List<IStatus> result, boolean usePrefValidation) {
        return new BOM2XMLTransformer().incrementalTransformBOMToXSD(umlClass,
                xsdFilePath,
                preserveSchemas,
                result,
                usePrefValidation);
    }

    /**
     * Transforms any given XML Schema to a BOM Model If source or bomFilePath
     * are null then method returns an empty list.
     * 
     * @param source
     *            - The actual XSD Schema file to be transformed
     * @param bomFilePath
     *            - The path that the newly created BOM file will take
     * @param monitor
     *            progress monitor
     * @return - Returns any errors or warnings that occurred for inspection
     */
    public static List<IStatus> transformXSDToBOM(final File source,
            final IPath bomFilePath, IProgressMonitor monitor) {
        return new XSD2BOMTransformer().transform(source, bomFilePath, monitor);
    }

    /**
     * Transforms any given XML Schema to a BOM Model If source or bomFilePath
     * are null then method returns an empty list.
     * 
     * @param source
     *            - The actual XSD Schema file to be transformed
     * @param bomFilePath
     *            - The path that the newly created BOM file will take
     * @param monitor
     *            progress monitor
     * @param doMerge
     *            Determines whether to perform a merge after transformation
     * @param doOverwrite
     *            Determines whether to overwrite the model regardless if exists
     *            or not
     * @return - Returns any errors or warnings that occurred for inspection
     */
    public static List<IStatus> transformXSDToBOM(final File source,
            final IPath bomFilePath, IProgressMonitor monitor, boolean doMerge,
            boolean doOverwrite) {
        return transformXSDToBOM(source,
                bomFilePath,
                monitor,
                doMerge,
                doOverwrite,
                false);
    }

    /**
     * Transforms any given XML Schema to a BOM Model If source or bomFilePath
     * are null then method returns an empty list.
     * 
     * @param source
     *            - The actual XSD Schema file to be transformed
     * @param bomFilePath
     *            - The path that the newly created BOM file will take
     * @param monitor
     *            progress monitor
     * @param doMerge
     *            Determines whether to perform a merge after transformation
     * @param doOverwrite
     *            Determines whether to overwrite the model regardless if exists
     *            or not
     * @param doRemoveXSDNotation
     *            if set to true removes XSDNotation profile from result BOM
     *            model.
     * @return - Returns any errors or warnings that occurred for inspection
     */
    public static List<IStatus> transformXSDToBOM(final File source,
            final IPath bomFilePath, IProgressMonitor monitor, boolean doMerge,
            boolean doOverwrite, boolean doRemoveXSDNotation) {
        return new XSD2BOMTransformer(doMerge, doOverwrite, doRemoveXSDNotation)
                .transform(source, bomFilePath, monitor);
    }

    /**
     * Gets a matching primitive type string value for an xsd type.
     * 
     * @param xsdType
     * @return
     */
    public static String getBOMPrimitiveType(String xsdType) {
        String searchBomElement = ""; //$NON-NLS-1$

        if (xsdType != null) {
            if (xsdType.equalsIgnoreCase("string")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            } else if (xsdType.equalsIgnoreCase("float")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME;
            } else if (xsdType.equalsIgnoreCase("decimal")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME;
            } else if (xsdType.equalsIgnoreCase("double")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME;
            } else if (xsdType.equalsIgnoreCase("integer")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME;
            } else if (xsdType.equalsIgnoreCase("int")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME;
            } else if (xsdType.equalsIgnoreCase("boolean")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME;
            } else if (xsdType.equalsIgnoreCase("date")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME;
            } else if (xsdType.equalsIgnoreCase("time")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME;
            } else if (xsdType.equalsIgnoreCase("datetime")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME;
            } else if (xsdType.equalsIgnoreCase("id")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            } else if (xsdType.equalsIgnoreCase("anyuri")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_URI_NAME;
            } else if (xsdType.equalsIgnoreCase("duration")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_DURATION_NAME;
            } else if (xsdType.equalsIgnoreCase("base64binary")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            } else if (xsdType.equalsIgnoreCase("hexbinary")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            } else if (xsdType.equalsIgnoreCase("anytype")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME;
            } else if (xsdType.equalsIgnoreCase("anysimpletype")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME;
            } else if (xsdType.equalsIgnoreCase("byte")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME;
            } else if (xsdType.equalsIgnoreCase("entities")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            } else if (xsdType.equalsIgnoreCase("entity")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            } else if (xsdType.equalsIgnoreCase("gday")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            } else if (xsdType.equalsIgnoreCase("gmonth")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            } else if (xsdType.equalsIgnoreCase("gmonthday")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            } else if (xsdType.equalsIgnoreCase("gyear")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            } else if (xsdType.equalsIgnoreCase("gyearmonth")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            } else if (xsdType.equalsIgnoreCase("idref")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            } else if (xsdType.equalsIgnoreCase("idrefs")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            } else if (xsdType.equalsIgnoreCase("language")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            } else if (xsdType.equalsIgnoreCase("long")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME;
            } else if (xsdType.equalsIgnoreCase("name")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            } else if (xsdType.equalsIgnoreCase("ncname")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            } else if (xsdType.equalsIgnoreCase("negativeinteger")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME;
            } else if (xsdType.equalsIgnoreCase("nmtoken")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            } else if (xsdType.equalsIgnoreCase("nmtokens")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            } else if (xsdType.equalsIgnoreCase("nonnegativeinteger")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME;
            } else if (xsdType.equalsIgnoreCase("nonpositiveinteger")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME;
            } else if (xsdType.equalsIgnoreCase("positiveinteger")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME;
            } else if (xsdType.equalsIgnoreCase("normalizedstring")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            } else if (xsdType.equalsIgnoreCase("positiveinteger")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            } else if (xsdType.equalsIgnoreCase("qname")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            } else if (xsdType.equalsIgnoreCase("short")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME;
            } else if (xsdType.equalsIgnoreCase("token")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            } else if (xsdType.equalsIgnoreCase("unsignedbyte")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME;
            } else if (xsdType.equalsIgnoreCase("unsignedint")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME;
            } else if (xsdType.equalsIgnoreCase("unsignedlong")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME;
            } else if (xsdType.equalsIgnoreCase("unsignedshort")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME;
            }

        }
        return searchBomElement;
    }

    /**
     * Top level elements of a schema don't have a UML class created. The names
     * of the XSD elements are added to the BOM class(representing the complex
     * type) as a stereotype. These objects are likely the
     * {@link XSDElementWrapper}
     * 
     * @deprecated use overloaded method getTopLevelElements(Model model)
     * @param classifier
     * @return
     */
    @Deprecated
    public static List<Object> getTopLevelElements(Classifier classifier) {
        return TransformHelper.getTopLevelElements(classifier.getModel());
    }

    public static List<Object> getTopLevelElements(Model model) {
        return TransformHelper.getTopLevelElements(model);
    }

    public static List<Object> getTopLevelAttributes(Model model) {
        return TransformHelper.getTopLevelAttributes(model);
    }

    /**
     * The top level element name is the xsd element name representation - this
     * is scraped out from the {@link XSDElementWrapper}
     * 
     * @param xsdElementWrapper
     * @return
     */
    public static String getTopLevelElementName(Object xsdElementWrapper) {
        return TransformHelper.getElementName(xsdElementWrapper);
    }

    /**
     * The top level element type is the xsd element type representation - this
     * is scraped out from the {@link XSDElementWrapper}
     * 
     * @param xsdElementWrapper
     * @return
     */
    public static Classifier getTopLevelElementType(Object xsdElementWrapper) {
        return TransformHelper.getElementType(xsdElementWrapper);
    }

    /**
     * Is top level element anonymous - this is scraped out from the
     * {@link XSDElementWrapper}
     * 
     * @param xsdElementWrapper
     * @return
     */
    public static boolean isAnonymousTopLevelElement(Object xsdElementWrapper) {
        return TransformHelper.getElementIsAnonymous(xsdElementWrapper);
    }

    /**
     * The top level element target namespace is the xsd element target
     * namespace representation - this is scraped out from the
     * {@link XSDElementWrapper}
     * 
     * @param xsdElementWrapper
     * @return
     */
    public static String getTopLevelElementTargetNamespace(
            Object xsdElementWrapper) {
        return TransformHelper.getElementTargetNamespace(xsdElementWrapper);
    }

    /**
     * Utility to retrieve the XSD namespace from a package that has been
     * imported from an XSD.
     * 
     * @param umlPackage
     * @return
     */
    public static String getXSDNamespaceFromPackage(Package umlPackage) {
        Stereotype notationStereotype =
                TransformHelper.getXSDNotationStereotype(umlPackage,
                        XsdStereotypeUtils.XSD_BASED_MODEL);
        Object notationProperty =
                TransformHelper.getXSDNotationProperty(umlPackage,
                        notationStereotype,
                        XsdStereotypeUtils.XSD_TARGET_NAMESPACE);

        if (notationProperty != null) {
            return notationProperty.toString();
        }
        return null;
    }

    /**
     * Checks whether we have top level elements or attributes in a given BOM
     * Model. If we have 1 or more elements or attributes then we return true
     * 
     * @param bomFile
     * @return
     */
    public static boolean checkModelForTopLevelElements(IFile bomFile) {
        if (bomFile != null) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(bomFile);
            if (wc != null && wc.getRootElement() instanceof Model) {
                Model model = (Model) wc.getRootElement();

                // get all top level attributes
                Stereotype topLevelAttributesStereotype =
                        getAppliedStereotype(model,
                                model,
                                XsdStereotypeUtils.TOP_LEVEL_ATTRIBUTES);
                if (topLevelAttributesStereotype != null) {
                    Object attributes =
                            model.getValue(topLevelAttributesStereotype,
                                    XsdStereotypeUtils.XSD_TOP_LEVEL_ATTRIBUTE_ATTRIBUTES);
                    if (attributes instanceof EList) {
                        EList listAttrs = (EList<?>) attributes;
                        if (listAttrs.size() > 0) {
                            return true;
                        }
                    }
                }

                // get all top level elements
                Stereotype topLevelAElementsStereotype =
                        getAppliedStereotype(model,
                                model,
                                XsdStereotypeUtils.TOP_LEVEL_ELEMENTS);
                if (topLevelAElementsStereotype != null) {
                    Object elements =
                            model.getValue(topLevelAElementsStereotype,
                                    XsdStereotypeUtils.XSD_TOP_LEVEL_ELEMENT_ELEMENTS);
                    if (elements instanceof EList) {
                        EList listElems = (EList<?>) elements;
                        if (listElems.size() > 0) {
                            return true;
                        }
                    }
                }

            }
        }
        return false;
    }

    /**
     * Gets an XSD Notation Profile stereotype value for a given element
     * 
     * @param model
     * @param element
     * @param stereotypeName
     * @return
     */
    private static Stereotype getAppliedStereotype(Model model,
            Element element, String stereotypeName) {
        Stereotype stereotype = null;
        if (model != null) {
            Iterator<Profile> profilesIter =
                    model.getAppliedProfiles().iterator();
            while (profilesIter.hasNext()) {
                Profile profile = profilesIter.next();
                if (profile.getName()
                        .equals(XsdStereotypeUtils.XSD_NOTATION_PROFILE_NAME)) {
                    Object obj = profile.getPackagedElement(stereotypeName);
                    if (obj instanceof Stereotype) {
                        stereotype = (Stereotype) obj;
                        if (stereotype.getName().equals(stereotypeName)) {
                            Stereotype alreadyAppliedStereotype =
                                    element.getAppliedStereotype(stereotype
                                            .getQualifiedName());
                            return alreadyAppliedStereotype;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Checks the supplied Property for the xsdExplicitGroupHierarchy stereotype
     * and returns true if it represents an Xsd Choice
     * 
     * @param prop
     * @return boolean
     */
    public static boolean isPropertyXsdChoice(Property prop) {
        Stereotype appliedStereotype =
                prop.getAppliedStereotype(XsdStereotypeUtils.XSD_NOTATION_PROFILE_NAME
                        + "::" + XsdStereotypeUtils.XSD_BASED_PROPERTY);

        if (appliedStereotype != null
                && BOMProfileUtils.isProfileXSDProfile(appliedStereotype
                        .getProfile())) {

            Object xsdPropertyTypeObj =
                    prop.getValue(appliedStereotype,
                            XsdStereotypeUtils.XSD_PROPERTY_EXPLICIT_GROUP_HIERARCHY);

            if (xsdPropertyTypeObj instanceof String) {
                String hierStr = (String) xsdPropertyTypeObj;

                if (hierStr.contains("C")) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * XPD-8147 Checks the supplied Property for the isXsdAttribute stereotype which
     * signifies whether the property was originally an XSD Attribute (rather
     * than XSD Element)
     * 
     * @param prop
     * @return boolean
     */
    public static boolean isPropertyXsdAttribute(Property prop) {
        Stereotype appliedStereotype =
                prop.getAppliedStereotype(XsdStereotypeUtils.XSD_NOTATION_PROFILE_NAME
                        + "::" + XsdStereotypeUtils.XSD_BASED_PROPERTY);

        if (appliedStereotype != null
                && BOMProfileUtils.isProfileXSDProfile(appliedStereotype
                        .getProfile())) {

            Object xsdPropertyTypeObj =
                    prop.getValue(appliedStereotype,
                            XsdStereotypeUtils.XSD_PROPERTY_IS_ATTRIBUTE);

            if (xsdPropertyTypeObj instanceof Boolean) {

                return (Boolean) xsdPropertyTypeObj;

            }
        }

        return false;
    }

    /**
     * 
     * Returns a List of Properties that have xsd stereotypes corresponding to
     * the same xsd choice level as the supplied Property. The list will contain
     * the original supplied Property so it is in fact a complete listing for
     * the choice.
     * 
     * 
     * @param Property
     * 
     * @return List &ltProperty&gt
     */
    public static List<Property> getPropertySiblingXsdChoices(Property prop) {
        List<Property> choices = new ArrayList<Property>();

        if (!isPropertyXsdChoice(prop)) {
            return Collections.emptyList();
        }

        // Find the parent class and collect all owned attributes to trawl
        // through
        EObject container = prop.eContainer();

        if (!(container instanceof Class)) {
            return Collections.emptyList();
        }

        // Make sure we add the calling Property to the list so that we get a
        // "complete" list of the choices in this Explicit Group
        choices.add(prop);

        String hierStr = getXsdExplictGroupHierarchy(prop);

        if (hierStr != null) {
            String baseLevelChoice = getBaseLevelChoice(hierStr);

            Class parentClass = (Class) container;

            // TODO: SHOULD THIS BE ALL ATTRIBUTES BECAUSE WE MAY NEED TO
            // COLLECT ASSOCIATED CLASSES PROPERTIES??
            EList<Property> ownedAttributes = parentClass.getOwnedAttributes();

            for (Property prop1 : ownedAttributes) {
                // Find the choice level for the property in question

                if (prop1 == prop) {
                    continue;
                }

                if (isPropertyXsdChoice(prop1)) {
                    String xsdExplictGroupHierarchy =
                            getXsdExplictGroupHierarchy(prop1);

                    if (getBaseLevelChoice(xsdExplictGroupHierarchy)
                            .equals(baseLevelChoice)) {
                        choices.add(prop1);
                    }
                }
            }
        }

        return choices;
    }

    /**
     * Returns the XsdExplicitGroupHierarchy value from the XsdBasedProperty
     * stereotype. Returns null if not set.
     * 
     * @param Property
     * @return String
     */
    public static String getXsdExplictGroupHierarchy(Property prop) {
        String hier = null;
        Stereotype appliedStereotype =
                prop.getAppliedStereotype(XsdStereotypeUtils.XSD_NOTATION_PROFILE_NAME
                        + "::" + XsdStereotypeUtils.XSD_BASED_PROPERTY);

        if (isPropertyXsdChoice(prop)) {
            Object xsdPropertyTypeObj =
                    prop.getValue(appliedStereotype,
                            XsdStereotypeUtils.XSD_PROPERTY_EXPLICIT_GROUP_HIERARCHY);

            if (xsdPropertyTypeObj instanceof String) {
                hier = (String) xsdPropertyTypeObj;
            }

        }
        return hier;
    }

    /**
     * Check the supplied string for the Choice identifier and parses
     * accordingly. Returns the base level choice.
     * 
     * @param String
     * @return String
     */
    private static String getBaseLevelChoice(String xsdExplGroupHier) {
        String baseLevelChoice = null;
        if (xsdExplGroupHier.contains("C")) {
            String[] split = xsdExplGroupHier.split(":");
            List<String> asList = Arrays.asList(split);

            for (String chunk : asList) {
                if (chunk.contains("C")) {
                    baseLevelChoice = chunk;
                    break;
                }
            }
        }

        return baseLevelChoice;
    }

    /**
     * Utility method to identify whether a given BOM classifier belongs to a
     * BOM which is generated by an XSD.
     * 
     * @param classifier
     * @return
     */
    public static Boolean doesClassifierBelongToXsdGeneratedBom(
            Classifier classifier) {
        Model model = classifier.getModel();

        if (model != null
                && model.getAppliedProfile(XsdStereotypeUtils.XSD_NOTATION_PROFILE_NAME) != null) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * Utility to check if the classifier is a representation of an XSD
     * anonymous type.
     * 
     * @param classifer
     * @return <code>true</code> if the classifier has a stereotype value
     *         <code>true</code> for <code>xsdIsAnonType</code>, otherwise will
     *         return <code>false</code>
     */
    public static Boolean isClassifierAnonymousType(Classifier classifer) {
        Stereotype xsdBasedClassSteroType =
                getAppliedStereotype(classifer,
                        XsdStereotypeUtils.XSD_BASED_CLASS);
        if (xsdBasedClassSteroType != null) {
            Object value =
                    classifer.getValue(xsdBasedClassSteroType,
                            XsdStereotypeUtils.XSD_PROPERTY_IS_ANON_TYPE);
            if (Boolean.TRUE.equals(value)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;

    }

    /***
     * 
     * @param classifier
     * @return TRUE if the given generated bom class (with stereotypes) is a top
     *         level element
     */
    public static Boolean isClassifierTopLevelElement(Classifier classifier) {

        if (null != classifier) {

            Model model = null;
            /* get the uml model to which the classifier belongs */
            EObject eContainer = classifier.eContainer();
            if (eContainer instanceof Model) {
                model = (Model) eContainer;
            }

            if (null != model) {
                /* get all top level elements from the model */
                List<Object> topLevelElements = getTopLevelElements(model);

                for (Object object : topLevelElements) {
                    /*
                     * create a TopLevelElement wrapper class
                     */
                    TopLevelElementWrapper tleWrapper =
                            new TopLevelElementWrapper((EObject) object);
                    /*
                     * get the top level element type to compare with classifier
                     */
                    Classifier tleClassfier = tleWrapper.getType();
                    if (tleClassfier == classifier) {
                        return Boolean.TRUE;
                    }
                }
            }
        }
        return Boolean.FALSE;
    }

    /**
     * Applied stereotype method on the Classifier wasn't returning expected
     * values.
     * 
     * @param cls
     * @param stereotypeName
     * @return
     */
    private static Stereotype getAppliedStereotype(Classifier cls,
            String stereotypeName) {

        List<Stereotype> appliedStereotypes = cls.getAppliedStereotypes();
        for (Stereotype stereotype : appliedStereotypes) {
            if (stereotype.getName().equals(stereotypeName)) {
                return stereotype;
            }
        }
        return null;
    }

    /**
     * Returns whether the data type has got a Union associated with it
     * 
     * @param dataType
     * @return
     */
    public static boolean isUnion(DataType dataType) {
        Stereotype xsdBasedUnionSteroType =
                getAppliedStereotype(dataType,
                        XsdStereotypeUtils.XSD_BASED_UNION);
        return xsdBasedUnionSteroType != null;
    }

    /**
     * Fetches all the Primitives and Enumerations that represent the dataTypes
     * Union members
     * 
     * @param dataType
     * @return
     */
    public static List<DataType> getUnionMemberTypes(DataType dataType) {
        List<DataType> lstMemberTypes = new ArrayList<DataType>();

        if (BOMProfileUtils.isXSDProfileApplied(dataType.getModel())) {
            EList<Property> ownedAttributes = dataType.getOwnedAttributes();

            for (Property property : ownedAttributes) {
                Type type = property.getType();

                if (type != null && type instanceof DataType) {
                    lstMemberTypes.add((DataType) type);
                }
            }
        }

        return lstMemberTypes;

    }

    /**
     * Returns true if dtype is an EndMember of the unionDataType, false
     * otherwise.
     * 
     * @param unionDataType
     * @param dType
     * @return
     */
    public static boolean isUnionEndMember(DataType unionDataType,
            DataType dType) {
        List<DataType> unionMemberTypes = getUnionMemberTypes(unionDataType);

        for (DataType memberType : unionMemberTypes) {
            if (memberType == dType) {
                return true;
            }
        }

        return false;
    }

    /**
     * The upper limit multiplicity of the given Property, is calculated by
     * inspecting multiplicity values of any parent XSD sequence stereotypes.
     * 
     * @param property
     * @return
     */
    public static int calcUMLUpperBoundFromParentSequences(Property property) {
        return calcMultiplicityFromParentSequences(property, "upper");
    }

    /**
     * The lower limit multiplicity of the given Property, is calculated by
     * inspecting multiplicity values of any parent XSD sequence stereotypes.
     * 
     * @param property
     * @return
     */
    public static int calcUMLLowerBoundFromParentSequences(Property property) {
        return calcMultiplicityFromParentSequences(property, "lower");
    }

    /**
     * The multiplicity of the given Property, is calculated by inspecting
     * multiplicity values of any parent XSD sequence stereotypes.
     * 
     * @param property
     * @param limitBoundary
     *            Either "upper" or "lower"
     * @return
     */
    private static int calcMultiplicityFromParentSequences(Property property,
            String limitBoundary) {
        int limit;

        if (limitBoundary.equals("upper")) {
            limit = property.getUpper();
        } else if (limitBoundary.equals("lower")) {
            limit = property.getLower();
        } else {
            // default to upper for safety
            limit = property.getUpper();
        }

        // Only need to proceed if bound is greater than 0. If bound is -1 then
        // it is unbounded and will remain unbounded. It should not be possible
        // for bound to be zero. If the bound is "lower" and set to 0 then zero
        // will be passed back
        if (limit > 0) {
            List<XSDSequenceWrapper> parentSequenceStereotypeWrappers =
                    getParentSequenceStereotypeWrappers(property);

            // parents will be IN ORDER UP THE HIERARCHY i.e. parent,
            // great-parent, great-great-parent etc
            for (XSDSequenceWrapper wrapper : parentSequenceStereotypeWrappers) {
                int wrapperLimit;

                if (limitBoundary.equals("lower")) {
                    wrapperLimit = wrapper.getMinOccurs();
                } else {
                    wrapperLimit = wrapper.getMaxOccurs();
                }

                if (wrapperLimit == -1) {
                    limit = -1;
                    // Don't need to go any further, we are unbounded
                    break;
                } else {
                    limit = limit * wrapperLimit;
                }
            }
        }

        return limit;
    }

    /**
     * 
     * If the given Property has an XSD Sequence stereotype applied then the
     * full hierarchy of sequences is traversed. Moving up the hierarchy a list
     * is populated with each sequence stereotype object encountered. Therefore,
     * the list will be ORDERED starting with parent, grandparent,
     * greatgrandparent and onwards.
     * 
     * @param property
     * @return
     */
    public static List<XSDSequenceWrapper> getParentSequenceStereotypeWrappers(
            Property property) {
        List<XSDSequenceWrapper> lstWrappers =
                new ArrayList<XSDSequenceWrapper>();
        XSDSequenceWrapper wrapper = getXSDSequenceWrapper(property);

        if (wrapper != null) {
            lstWrappers.add(wrapper);
            XSDSequenceWrapper parentSeqWrapper = wrapper.getParentSequence();

            while (parentSeqWrapper != null) {
                lstWrappers.add(parentSeqWrapper);
                parentSeqWrapper = parentSeqWrapper.getParentSequence();
            }
        }

        return lstWrappers;
    }

    /**
     * 
     * Returns an XSD Sequence Object for the supplied Property if present.
     * 
     * @param property
     * @return
     */
    private static XSDSequenceWrapper getXSDSequenceWrapper(Property property) {
        XSDSequenceWrapper wrapper = null;

        Stereotype appliedStereotype =
                getAppliedStereotype(property.getModel(),
                        property,
                        XsdStereotypeUtils.XSD_BASED_PROPERTY);
        if (appliedStereotype != null) {
            Object value =
                    property.getValue(appliedStereotype,
                            XsdStereotypeUtils.XSD_PROPERTY_PARENT_SEQUENCE);

            if (value != null && value instanceof EObject) {
                wrapper = new XSDSequenceWrapper((EObject) value);
            }
        }

        return wrapper;
    }

    /**
     * This method checks to see if the property parent (sequence or choice) has
     * a max occurs of greater than 1 and hence multiplicity is set on this
     * object. Note this only checks the direct parent and not any other
     * containers up the stack.
     * 
     * @param property
     *            - Property that we want to check
     * @return
     */
    public static boolean isParentSequenceMultiplicitySet(Property property) {
        boolean isParentSequenceMultiplicitySet = false;
        Stereotype appliedStereotype =
                getAppliedStereotype(property.getModel(),
                        property,
                        XsdStereotypeUtils.XSD_BASED_PROPERTY);
        if (appliedStereotype != null) {
            Object value =
                    property.getValue(appliedStereotype,
                            XsdStereotypeUtils.XSD_PROPERTY_PARENT_SEQUENCE);
            if (value != null && value instanceof EObject) {
                XSDSequenceWrapper tmpParent =
                        new XSDSequenceWrapper((EObject) value);
                if (tmpParent != null
                        && (tmpParent.getMaxOccurs() == -1 || tmpParent
                                .getMaxOccurs() > 1)) {
                    isParentSequenceMultiplicitySet = true;
                }
            }
        }
        return isParentSequenceMultiplicitySet;
    }

    /**
     * Checks the property to see if it has a choice somewhere up its hierarchy
     * of parent containers and returns true if so.
     * 
     * @param property
     * @return
     */
    public static boolean isContainedInChoice(Property property) {
        boolean isContainedInChoice = false;
        Stereotype appliedStereotype =
                getAppliedStereotype(property.getModel(),
                        property,
                        XsdStereotypeUtils.XSD_BASED_PROPERTY);
        if (appliedStereotype != null) {
            Object value =
                    property.getValue(appliedStereotype,
                            XsdStereotypeUtils.XSD_PROPERTY_PARENT_SEQUENCE);
            while (value != null && value instanceof EObject
                    && isContainedInChoice == false) {
                XSDSequenceWrapper tmpParent =
                        new XSDSequenceWrapper((EObject) value);
                if (tmpParent != null && tmpParent.isChoice()) {
                    isContainedInChoice = true;
                }
                value = tmpParent.getParent();
            }
        }
        return isContainedInChoice;
    }

    /**
     * This method checks whether this property has ANY parent sequence/choice
     * containers that have multiplicity defined i.e. max occurs > 1 and this
     * does indeed go up the stack to check all of its parents.
     * 
     * @param property
     *            - Property that we want to check
     * @return
     */
    public static boolean isContainedInSequenceWithMultiplicity(
            Property property) {
        boolean isContainedInSequenceWithMultiplicity = false;
        Stereotype appliedStereotype =
                getAppliedStereotype(property.getModel(),
                        property,
                        XsdStereotypeUtils.XSD_BASED_PROPERTY);
        if (appliedStereotype != null) {
            Object value =
                    property.getValue(appliedStereotype,
                            XsdStereotypeUtils.XSD_PROPERTY_PARENT_SEQUENCE);
            while (value != null && value instanceof EObject
                    && isContainedInSequenceWithMultiplicity == false) {
                XSDSequenceWrapper tmpParent =
                        new XSDSequenceWrapper((EObject) value);
                if (tmpParent != null
                        && (tmpParent.getMaxOccurs() == -1 || tmpParent
                                .getMaxOccurs() > 1)) {
                    isContainedInSequenceWithMultiplicity = true;
                }
                value = tmpParent.getParent();
            }
        }
        return isContainedInSequenceWithMultiplicity;
    }

    /**
     * Iterates through XsdSequence stereotype data structures if present.
     * Returns true if encounters sequence multiplicity upper bound of greater
     * than 1 or unbounded. I.e. the Class contains sequences with multiplicity.
     * 
     * @param cl
     * @return
     */
    public static boolean isXsdSequenceMultiplicityInClass(Class cl) {
        boolean ret = false;
        Stereotype appliedStereotype =
                getAppliedStereotype(cl.getModel(),
                        cl,
                        XsdStereotypeUtils.XSD_BASED_CLASS);

        if (appliedStereotype != null) {
            Object value =
                    cl.getValue(appliedStereotype,
                            XsdStereotypeUtils.XSD_PROPERTY_SEQUENCE_LIST);

            if (value instanceof EList) {
                EList<?> lst = (EList<?>) value;
                for (Object obj : lst) {
                    XSDSequenceWrapper wrapper =
                            new XSDSequenceWrapper((EObject) obj);
                    int maxBound = wrapper.getMaxOccurs();

                    if (maxBound > 1 || maxBound == -1) {
                        ret = true;
                        break;
                    }
                }
            }
        }

        return ret;

    }

    /**
     * Returns true if the supplied Class has the XsdNotation stereotype value
     * corresponding to the xsd construct "Restriction of another ComplexType"
     * set to true
     * 
     * @param cl
     * @return
     */
    public static boolean isClassXsdComplexTypeRestriction(Class cl) {
        boolean isSet = false;

        if (BOMProfileUtils.isXSDProfileApplied(cl.getModel())) {
            Stereotype appliedStereotype =
                    cl.getAppliedStereotype(XsdStereotypeUtils.XSD_NOTATION_PROFILE_NAME
                            + "::" + XsdStereotypeUtils.XSD_BASED_CLASS); //$NON-NLS-1$

            if (appliedStereotype != null) {
                Object value =
                        cl.getValue(appliedStereotype,
                                XsdStereotypeUtils.XSD_PROPERTY_IS_RESTRICTION);

                if (value instanceof Boolean) {
                    isSet = ((Boolean) value).booleanValue();
                }
            }
        }

        return isSet;

    }

    /**
     * 
     * Determines whether the supplied property upper multiplicity is unbounded.
     * It is possible that the property's parent class is a representation of
     * the ComplexType Restriction XML Schema construct. In which case the
     * hierarchy of generalized classes will be examined to determine the
     * correct multiplicity. Should only really need to call this when the
     * supplied Property's upper bound multiplicity is 1.
     * 
     * @param prop
     * @return
     */
    public static boolean isMultiInstancePropertyBasedOnXSDRestriction(
            Property prop) {
        boolean isMulti = false;
        if (prop.getUpper() == 1) {
            Element owner = prop.getOwner();

            if (owner instanceof Class) {
                Class cl = (Class) owner;

                if (isClassXsdComplexTypeRestriction(cl)) {
                    // We have identified this property as being contained in a
                    // complex type restriction. We need to check identify the
                    // parent Class/Property that is being restricted as we
                    // still need to support n or * multiplicity (i.e. a List)
                    // even is we are restricting to a single value. This is due
                    // to behaviour in BDS/EMF code generation

                    // Collect the whole hierarchy of Classes so that we can
                    // work from the topmost parent down, searching for the
                    // first definition of the restricted Property.
                    // We can then read its multiplicity to see if it is
                    // multi-instance.
                    String propName = prop.getName();
                    List<Class> lstSuperClasses = new ArrayList<Class>();
                    collectSuperClasses(cl, lstSuperClasses);

                    if (!lstSuperClasses.isEmpty()) {
                        // Reverse the array so that we can start iteration from
                        // the top-most super Class
                        Collections.reverse(lstSuperClasses);

                        boolean found = false;
                        for (Class clazz : lstSuperClasses) {
                            EList<Property> ownedAttributes =
                                    clazz.getOwnedAttributes();

                            for (Property superProp : ownedAttributes) {
                                if (superProp.getName().equals(propName)) {
                                    // FOUND THE TOP-MOST PROPERTY
                                    int upper = superProp.getUpper();
                                    if (upper > 1 || upper == -1) {
                                        // We've found our multiplicity
                                        isMulti = true;
                                        found = true;
                                        break;
                                    }
                                }
                            }
                            if (found) {
                                break;
                            }
                        }
                    }
                }
            }
        } else if (prop.getUpper() > 1 || prop.getUpper() == -1) {
            isMulti = true;
        }

        return isMulti;
    }

    /**
     * Collects the classes in the genearlized hierarchy.
     * 
     * @param cl
     * @param lstSuperClasses
     */
    private static void collectSuperClasses(Class cl,
            List<Class> lstSuperClasses) {

        if (!cl.getGenerals().isEmpty()) {
            Classifier classifier = cl.getGenerals().get(0);

            if (classifier instanceof Class) {
                lstSuperClasses.add((Class) classifier);
                collectSuperClasses((Class) classifier, lstSuperClasses);
            }
        }
    }

    /***
     * 
     * returns the base class if the given property is from a restricted class
     * 
     * @param property
     * @return
     */
    public static Class getBaseClassForRestrictedProperty(Property property) {
        if (null != property) {
            EObject propContainer = property.eContainer();
            if (propContainer instanceof Class) {
                Class propOwner = (Class) propContainer;

                if (XSDUtil.isClassXsdComplexTypeRestriction(propOwner)) {
                    List<Class> lstSuperClasses = new ArrayList<Class>();
                    collectSuperClasses(propOwner, lstSuperClasses);

                    if (!lstSuperClasses.isEmpty()) {
                        Collections.reverse(lstSuperClasses);

                        for (Class clazz : lstSuperClasses) {
                            EList<Property> ownedAttributes =
                                    clazz.getOwnedAttributes();
                            for (Property superProp : ownedAttributes) {
                                if (superProp.getName()
                                        .equals(property.getName())) {
                                    return clazz;
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}