/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.xsdtransform.exports.template;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.eclipse.emf.ecore.impl.EEnumLiteralImpl;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap.Entry;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.ProfileApplication;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.openarchitectureware.workflow.WorkflowInterruptedException;
import org.openarchitectureware.xsd.OawXMLResource;
import org.openarchitectureware.xsd.XSDMetaModel;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.bom.resources.utils.UML2ModelUtil;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.xsdtransform.XsdStereotypeUtils;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
import com.tibco.xpd.bom.xsdtransform.internal.BaseBOMXtendTransformer;
import com.tibco.xpd.bom.xsdtransform.internal.Messages;
import com.tibco.xpd.bom.xsdtransform.utils.TopLevelAttributeWrapper;
import com.tibco.xpd.bom.xsdtransform.utils.TopLevelElementWrapper;
import com.tibco.xpd.bom.xsdtransform.utils.XSDSequenceWrapper;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Java extension used by the OAW transformation used in export of BOM to
 * WSDL/XSD.
 * 
 * @author glewis
 * 
 */
public class TransformHelper {

    private static final String XSD_NOTATION_PROFILE_NAME =
            "XsdNotationProfile"; //$NON-NLS-1$

    public static final String XSD_NOTATION_URI =
            "pathmap://XSD_NOTATION_TYPES/XsdNotation.profile.uml"; //$NON-NLS-1$

    public static final String URI_FILE_PREFIX = "file://"; //$NON-NLS-1$

    private static final String PRIMITIVE_STEREOTYPE_NAME = "RestrictedType"; //$NON-NLS-1$

    public static final String PRIMITIVES_PROFILE_NAME = "PrimitiveTypeFacets"; //$NON-NLS-1$

    private static final String RESTRICTED_TYPE_STEREOTYPE_QNAME =
            "PrimitiveTypeFacets::RestrictedType"; //$NON-NLS-1$

    private static final String INTEGER_SUBTYPE_SIGNEDINTEGER = "signedInteger"; //$NON-NLS-1$

    private static final String DECIMAL_SUBTYPE_FLOATINGPOINT = "floatingPoint"; //$NON-NLS-1$

    private static final List<Type> packagedElements = UML2ModelUtil
            .getPrimitives();

    private static List<PrimitiveType> defaultPrims;

    /**
     * Print trace message.
     * 
     * @param temp
     * @return
     */
    public static String traceMe(String temp) {
        // if (Platform.inDebugMode() || Platform.inDevelopmentMode()) {
        // Activator.getDefault().getLogger().debug(temp);
        // System.out.println(temp);
        // }
        return ""; //$NON-NLS-1$
    }

    public static String traceMeDebug(String temp) {
        // if (Platform.inDebugMode() || Platform.inDevelopmentMode()) {
        // Activator.getDefault().getLogger().debug(temp);
        // System.out.println(temp);
        // }
        return ""; //$NON-NLS-1$

    }

    /**
     * Check if the given primitive type name is of a default UML primitive
     * type.
     * 
     * @param primName
     * @return
     */
    public static boolean isDefaultPrimitiveType(String primName,
            String propertyTypePackageName) {
        for (Iterator<Type> iter = packagedElements.iterator(); iter.hasNext();) {
            Type tempElement = iter.next();
            if (tempElement.getNearestPackage().getName()
                    .equals(propertyTypePackageName)
                    && tempElement.getName().equals(primName)) {
                return true;
            }
        }
        return false;
    }

    /***
     * 
     * returns package name if the classifier is a primitive type
     * 
     * @param classifier
     * @return
     */
    public static String _getNearestPackageName(Classifier classifier) {
        /*
         * XPD-4730: instead of checking for instance of PrimitiveType, checking
         * it for DataType so that PrimitiveType and Enumeration are covered.
         */
        if (classifier instanceof DataType) {
            return classifier.getNearestPackage().getName();
        }
        return null;
    }

    /**
     * @param primName
     * @return
     */
    public static boolean isImportCreatedXSDType(String primName) {
        String searchXSDName = primName.replace("xsd:", ""); //$NON-NLS-1$ //$NON-NLS-2$
        for (String xsdType : BaseBOMXtendTransformer.xsdTypes) {
            if (xsdType.equals(searchXSDName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the BOM base primitive type of the given type.
     * 
     * @param primType
     * @return
     */
    public static Type getBomPrimitiveType(PrimitiveType primType) {
        return PrimitivesUtil.getBasePrimitiveType(primType);
    }

    /**
     * Get the ID of the BOM object.
     * 
     * @param object
     *            EObject.
     * @return
     */
    public static String getXRefID(Object object) {
        String xRef = null;
        if (object instanceof EObject) {
            URI uri = EcoreUtil.getURI((EObject) object);
            if (uri != null) {
                xRef = uri.fragment();
                xRef = xRef.replace("%", ""); //$NON-NLS-1$
                xRef = xRef.replace("?", ""); //$NON-NLS-1$
            }
        }
        return xRef;
    }

    /**
     * Get the RestrictedType stereotype for the given BOM object.
     * 
     * @param object
     * @return
     */
    public static Stereotype getStereotypedProperty(Object object) {
        Stereotype stereotype = null;
        Model ownerModel = null;
        if (object instanceof PrimitiveType) {
            PrimitiveType primitiveType = (PrimitiveType) object;
            ownerModel = primitiveType.getModel();
            stereotype =
                    primitiveType
                            .getAppliedStereotype(RESTRICTED_TYPE_STEREOTYPE_QNAME);
        } else if (object instanceof Property) {
            Property property = (Property) object;
            ownerModel = property.getModel();
            stereotype =
                    property.getAppliedStereotype(RESTRICTED_TYPE_STEREOTYPE_QNAME);
        }

        if (stereotype != null) {
            return stereotype;
        }

        // if stereotype not applied we need to return default one for profile
        // so this gets included in the xsd simpletype restriction
        if (ownerModel != null) {
            TransactionalEditingDomain editingDomain =
                    XpdResourcesPlugin.getDefault().getEditingDomain();
            stereotype =
                    PrimitivesUtil.getFacetsStereotype(editingDomain
                            .getResourceSet());
        }
        return stereotype;
    }

    /**
     * @param object
     * @return
     */
    public static boolean isXSDNotationProfileApplied(Object object) {
        if (object instanceof Class) {
            object = ((Class) object).getPackage();
        }
        if (object instanceof Enumeration) {
            object = ((Enumeration) object).getPackage();
        }
        if (object instanceof Package) {
            Package umlPackage = (Package) object;

            Iterator<ProfileApplication> iter =
                    umlPackage.getAllProfileApplications().iterator();
            while (iter.hasNext()) {
                Profile appliedProfile = iter.next().getAppliedProfile();
                if (appliedProfile != null
                        && appliedProfile.getName()
                                .equals(XSD_NOTATION_PROFILE_NAME)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param object
     * @return
     */
    private static Profile getXSDNotationProfileApplied(Object object) {
        if (object instanceof Class) {
            object = ((Class) object).getPackage();
        }
        if (object instanceof PrimitiveType) {
            object = ((PrimitiveType) object).getPackage();
        }
        if (object instanceof Enumeration) {
            object = ((Enumeration) object).getPackage();
        }
        if (object instanceof Package) {
            Package umlPackage = (Package) object;

            Iterator<ProfileApplication> iter =
                    umlPackage.getAllProfileApplications().iterator();
            while (iter.hasNext()) {
                Profile appliedProfile = iter.next().getAppliedProfile();
                if (appliedProfile != null
                        && appliedProfile.getName()
                                .equals(XSD_NOTATION_PROFILE_NAME)) {
                    return appliedProfile;
                }
            }
        }
        return null;
    }

    /**
     * @param object
     * @param stereotypeName
     * @return
     */
    public static Stereotype getXSDNotationStereotype(Object object,
            String stereotypeName) {
        Profile xsdNotationProfile = getXSDNotationProfileApplied(object);
        if (xsdNotationProfile != null) {
            Iterator<Stereotype> iter = null;
            if (object instanceof Package) {
                iter = ((Package) object).getAppliedStereotypes().iterator();
            } else if (object instanceof Class) {
                iter = ((Class) object).getAppliedStereotypes().iterator();
            } else if (object instanceof PrimitiveType) {
                iter =
                        ((PrimitiveType) object).getAppliedStereotypes()
                                .iterator();
            } else if (object instanceof Enumeration) {
                iter =
                        ((Enumeration) object).getAppliedStereotypes()
                                .iterator();
            }
            if (iter != null) {
                while (iter.hasNext()) {
                    Stereotype tempStereotype = iter.next();
                    if (tempStereotype.getName().equals(stereotypeName)) {
                        return tempStereotype;
                    }
                }
            }
        }
        return null;
    }

    /**
     * @param object
     * @param stereotype
     * @param propertyName
     * @return
     */
    public static Object getXSDNotationProperty(Object object,
            Stereotype stereotype, String propertyName) {
        try {
            if (stereotype != null) {
                if (object instanceof Classifier) {
                    Classifier umlClassifier = (Classifier) object;
                    if (umlClassifier.hasValue(stereotype, propertyName)) {
                        return umlClassifier.getValue(stereotype, propertyName);
                    }
                } else if (object instanceof PrimitiveType) {
                    PrimitiveType primitiveType = (PrimitiveType) object;
                    if (primitiveType.hasValue(stereotype, propertyName)) {
                        return primitiveType.getValue(stereotype, propertyName);
                    }
                } else if (object instanceof Enumeration) {
                    Enumeration enumeration = (Enumeration) object;
                    if (enumeration.hasValue(stereotype, propertyName)) {
                        return enumeration.getValue(stereotype, propertyName);
                    }
                } else if (object instanceof Package) {
                    Package umlPackage = (Package) object;
                    if (umlPackage.hasValue(stereotype, propertyName)) {
                        return umlPackage.getValue(stereotype, propertyName);
                    }
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * @param model
     * @param namespace
     * @return
     */
    public static Object getExistingSchema(Model model, String namespace) {
        Stereotype stereotype = null;

        Iterator<Stereotype> stereoTypeIter =
                model.getAppliedStereotypes().iterator();
        while (stereoTypeIter.hasNext()) {
            stereotype = stereoTypeIter.next();
            if (stereotype.getName().equals(XsdStereotypeUtils.XSD_BASED_MODEL)) {
                Object value =
                        model.getValue(stereotype,
                                XsdStereotypeUtils.XSD_IMPORTED_SCHEMAS);
                if (value instanceof List<?>) {
                    List<?> xsdDocRootList = (List<?>) value;
                    Iterator<?> iter = xsdDocRootList.iterator();
                    while (iter.hasNext()) {
                        Object next = iter.next();
                        if (next instanceof String) {
                            String strXmlSchema = (String) iter.next();
                            ByteArrayInputStream byteArrayInputStream =
                                    new ByteArrayInputStream(
                                            strXmlSchema.getBytes());

                            XSDMetaModel metaModel =
                                    XSDMetaModel
                                            .getInstance(BaseBOMXtendTransformer.XSD_METAMODEL_ID);
                            if (metaModel == null)
                                throw new WorkflowInterruptedException(
                                        String.format(Messages.TransformHelper_noXsdMetaModelFound_error_message,
                                                BaseBOMXtendTransformer.XSD_METAMODEL_ID));

                            OawXMLResource oawRes =
                                    new OawXMLResource(null, metaModel);
                            try {
                                oawRes.load(byteArrayInputStream,
                                        new HashMap<String, Object>());
                            } catch (Throwable e) {
                                throw new WorkflowInterruptedException(
                                        Messages.TransformHelper_errorLoadingXMLFile_error_message,
                                        e);
                            }
                            if (metaModel.getXsdManager().hasErrors())
                                throw new WorkflowInterruptedException(
                                        Messages.TransformHelper_ErrorLoadingXSDMetaModel_error_message);

                            if (oawRes.getContents().size() < 1)
                                throw new WorkflowInterruptedException(
                                        Messages.TransformHelper_XMLFileContentEmpty_error_message);

                            EObject docroot = oawRes.getContents().get(0);

                            if (docroot.eContents().size() < 1)
                                throw new WorkflowInterruptedException(
                                        Messages.TransformHelper_XmlFileDocumentRootEmpty_error_message);

                            Object schemaTypeObject =
                                    docroot.eContents().get(0);
                            return schemaTypeObject;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Get the xsdDirty property value for the given element.
     * 
     * @param element
     * @return
     */
    public static boolean isDirty(Element element) {
        try {
            Stereotype stereotype = null;
            Iterator<Profile> profilesIter =
                    element.getModel().getAppliedProfiles().iterator();
            while (profilesIter != null && profilesIter.hasNext()) {
                Profile profile = profilesIter.next();
                if (profile.getName()
                        .equals(XsdStereotypeUtils.XSD_NOTATION_PROFILE_NAME)) {
                    Object obj =
                            profile.getPackagedElement(XsdStereotypeUtils.XSD_BASED_ELEMENT);
                    if (obj instanceof Stereotype) {
                        stereotype = (Stereotype) obj;
                        if (stereotype.getName()
                                .equals(XsdStereotypeUtils.XSD_BASED_ELEMENT)) {
                            if (element.hasValue(stereotype,
                                    XsdStereotypeUtils.XSD_DIRTY)) {
                                Boolean isDirty =
                                        (Boolean) element.getValue(stereotype,
                                                XsdStereotypeUtils.XSD_DIRTY);
                                return isDirty;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
        return true;
    }

    /**
     * @param data
     * @param property
     * @return
     */
    public static String getStereotypeType(ExportTransformationData data,
            Property property) {
        try {
            Stereotype stereotype = null;
            Iterator<Profile> profilesIter =
                    property.getModel().getAppliedProfiles().iterator();
            while (profilesIter != null && profilesIter.hasNext()) {
                Profile profile = profilesIter.next();
                if (profile.getName()
                        .equals(XsdStereotypeUtils.XSD_NOTATION_PROFILE_NAME)) {
                    Object obj =
                            profile.getPackagedElement(XsdStereotypeUtils.XSD_BASED_PROPERTY);
                    if (obj instanceof Stereotype) {
                        stereotype = (Stereotype) obj;
                        if (stereotype.getName()
                                .equals(XsdStereotypeUtils.XSD_BASED_PROPERTY)) {
                            if (property.hasValue(stereotype,
                                    XsdStereotypeUtils.XSD_PROPERTY_TYPE)) {
                                String type =
                                        (String) property
                                                .getValue(stereotype,
                                                        XsdStereotypeUtils.XSD_PROPERTY_TYPE);
                                if (type != null && type.trim().length() > 0) {
                                    String[] split = type.split(":"); //$NON-NLS-1$
                                    if (split.length >= 2) {
                                        type = "xsd:" + split[1]; //$NON-NLS-1$
                                    } else {
                                        type = "xsd:" + split[0]; //$NON-NLS-1$
                                    }
                                    return type;
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * @param data
     * @param property
     * @return
     */
    public static String getStereotypeRef(ExportTransformationData data,
            Property property) {
        try {
            Stereotype stereotype = null;
            Iterator<Profile> profilesIter =
                    property.getModel().getAppliedProfiles().iterator();
            while (profilesIter != null && profilesIter.hasNext()) {
                Profile profile = profilesIter.next();
                if (profile.getName()
                        .equals(XsdStereotypeUtils.XSD_NOTATION_PROFILE_NAME)) {
                    Object obj =
                            profile.getPackagedElement(XsdStereotypeUtils.XSD_BASED_PROPERTY);
                    if (obj instanceof Stereotype) {
                        stereotype = (Stereotype) obj;
                        if (stereotype.getName()
                                .equals(XsdStereotypeUtils.XSD_BASED_PROPERTY)) {
                            if (property.hasValue(stereotype,
                                    XsdStereotypeUtils.XSD_PROPERTY_REF)) {
                                String ref =
                                        (String) property
                                                .getValue(stereotype,
                                                        XsdStereotypeUtils.XSD_PROPERTY_REF);
                                if (ref != null && ref.trim().length() > 0) {
                                    String[] split = ref.split(":"); //$NON-NLS-1$
                                    if (split.length >= 2) {
                                        ref = "xsd:" + split[1]; //$NON-NLS-1$
                                    } else {
                                        ref = "xsd:" + split[0]; //$NON-NLS-1$
                                    }
                                    return ref;
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * Convert the given string to a Boolean value.
     * 
     * @param value
     * @return
     */
    public static Boolean toBoolean(String value) {
        if (value != null && value.trim().length() > 0) {
            return new Boolean(value).booleanValue();
        }
        return false;
    }

    /**
     * @param map
     * @param value
     */
    public static void addSchema(FeatureMap map, Object value) {

        // get the Package the XSD Adapter has registered globally
        EPackage schemaPackage = null;
        Iterator<EPackage> packagesIter =
                BaseBOMXtendTransformer.getXsdMetaModel().getXsdManager()
                        .getPackages().iterator();
        while (packagesIter.hasNext() && schemaPackage == null) {
            EPackage tempPackage = packagesIter.next();
            if (tempPackage.getNsURI()
                    .equals("http://www.w3.org/2001/XMLSchema")) { //$NON-NLS-1$
                schemaPackage = tempPackage;
            }
        }

        if (schemaPackage != null) {
            // get the EClass that contains the desired feature
            EClass cls =
                    (EClass) schemaPackage
                            .getEClassifier("XmlSchemaDocumentRoot"); //$NON-NLS-1$

            // get the feature from the class. The object 'value' must have the
            // correct type for this feature. The feature's name is basically
            // the
            // XML-Element's name.
            EStructuralFeature feat = cls.getEStructuralFeature("schema"); //$NON-NLS-1$

            // add the new entry to the map.
            map.add(feat, value);
        }
    }

    /**
     * @param rootModel
     *            The root source model
     * @param includeOperations
     *            Whether to include references via operation parameters.
     * 
     * @return A set of the tree of models that teh given rootModel references.
     */
    public static Set<Model> getModelAndReferences(Model rootModel,
            Boolean includeOperations) {

        /* Try new method. */
        Set<Model> newModelSet =
                ModelAndReferencesAnalyzer.getModelAndReferences(rootModel,
                        includeOperations);

        return newModelSet;
    }

    /**
     * @param wc
     * @param currentFile
     * @return Map of XSDBasedProeprty namespace to BOM file resources for given
     *         project 9these are only present for BOms imported from WSDL /
     *         XSD.
     */
    public static Map<String, IResource> getXsdNamespaceToBomFileMapForXsdXrefs(
            IProject project, IResource excludeBomFile) {
        Map<String, IResource> xsdNamespaceToBomFileMap =
                new HashMap<String, IResource>();

        if (project != null) {
            ArrayList<IResource> bomFiles =
                    SpecialFolderUtil
                            .getResourcesInSpecialFolderOfKind(project,
                                    BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND,
                                    BOMResourcesPlugin.BOM_FILE_EXTENSION);

            if (bomFiles != null) {
                /* Find bom with correct namespace. */
                for (IResource bomFile : bomFiles) {
                    /*
                     * This is called from indexer sometimes so indexer can
                     * cause laod on working copy so which causes an index -
                     * just make sure we don't try and laod a working copy that
                     * we're in the middle of loading and indexing!
                     */
                    if (!bomFile.equals(excludeBomFile)
                            && bomFile.isAccessible()) {
                        WorkingCopy otherWc =
                                WorkingCopyUtil.getWorkingCopy(bomFile);
                        if (otherWc != null
                                && otherWc.getRootElement() instanceof org.eclipse.uml2.uml.Package) {
                            String namespaceFromPackage =
                                    XSDUtil.getXSDNamespaceFromPackage((org.eclipse.uml2.uml.Package) otherWc
                                            .getRootElement());

                            if (namespaceFromPackage != null
                                    && namespaceFromPackage.length() > 0) {
                                xsdNamespaceToBomFileMap
                                        .put(namespaceFromPackage, bomFile);
                            }
                        }
                    }
                }
            }
        }
        return xsdNamespaceToBomFileMap;
    }

    /**
     * Get the BOM model (if any) that is referenced from the given proeprty via
     * the XsdBasedProperty -> xRef sterootype value
     * 
     * @return the BOM model (if any) that is referenced from the given proeprty
     *         via the XsdBasedProperty -> xRef sterootype value
     */
    public static IResource getXsdBasedXrefBomFile(Property property,
            Map<String, IResource> xsdNamespaceToBomFileMap) {
        Stereotype appliedStereotype =
                property.getAppliedStereotype("XsdNotationProfile::XsdBasedProperty");
        if (appliedStereotype != null) {
            Object xref = property.getValue(appliedStereotype, "xsdRef");
            if (xref instanceof String) {
                int i = ((String) xref).lastIndexOf(":");
                if (i > 0) {
                    String xRefNamespace = ((String) xref).substring(0, i);
                    if (xRefNamespace.length() > 0) {
                        IResource bom =
                                xsdNamespaceToBomFileMap.get(xRefNamespace);
                        if (bom != null) {
                            return bom;
                        }

                        /*
                         * We don't keep the namespace for self-reference in the
                         * xsdnamespace map so lets check the source model
                         * namespace.
                         */
                        if (property.getModel() != null) {
                            String namespaceForProp =
                                    XSDUtil.getXSDNamespaceFromPackage(property
                                            .getModel());
                            if (xRefNamespace.equals(namespaceForProp)) {
                                return WorkingCopyUtil.getFile(property
                                        .getModel());
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Get the file path of the resource that contains the given model.
     * 
     * @param model
     * @return
     */
    public static String getModelResourceURI(Model model) {
        IFile bomFile = WorkspaceSynchronizer.getFile(model.eResource());
        return bomFile.getFullPath().toPortableString();
    }

    /**
     * @param sourceText
     * @param findText
     * @param replaceText
     * @return
     */
    public static String ReplaceFirst(String sourceText, String findText,
            String replaceText) {
        String replaced = replaceText + sourceText;
        int lastIdx = sourceText.indexOf(findText);
        if (lastIdx > 0) {
            replaced = replaceText + sourceText.substring(lastIdx + 1);
        }
        return replaced;
    }

    /**
     * @param sourceText
     * @param findText
     * @param replaceText
     * @return
     */
    public static String ReplaceLast(String sourceText, String findText,
            String replaceText) {
        int lastIdx = sourceText.lastIndexOf(findText);
        return sourceText.substring(0, lastIdx) + replaceText;
    }

    public static String getPrefixPart(String sourceText) {
        /* XPD-3729: handle the case when namespace does not start with http */
        if (sourceText.startsWith("http:")) { //$NON-NLS-1$
            /*
             * if the source text is -> http://www.example.org/Schema1:person,
             * this would return http://www.example.org/Schema1
             */
            sourceText = sourceText.replace("http:", ""); //$NON-NLS-1$ //$NON-NLS-2$
            if (sourceText.indexOf(":") != -1) { //$NON-NLS-1$
                String[] tmpSplited = sourceText.split(":"); //$NON-NLS-1$
                return "http:" + tmpSplited[0]; //$NON-NLS-1$
            }
            /*
             * XPD-4062: need to check if the sourceText contains colon because
             * i am getting the last index of colon from sourceText. otherwise
             * this would throw runtime StringIndexOutOfBoundsException
             */
        } else if (sourceText.contains(":")) { //$NON-NLS-1$
            /*
             * i think we can safely return null instead of doing substring
             * here. if this returns null then prefix is taken from either
             * stereotype (xsdTargetNamespace) or namespace hashmap
             * (ExportTransformatinData.generatePrefix()). But we decide to
             * handle it to get the correct prefix part
             * 
             * if the source text is -> urn:alfam:nap:myxsd1.xsd:person, this
             * would return urn:alfam:nap:myxsd1.xsd
             */
            sourceText = sourceText.substring(0, sourceText.lastIndexOf(":")); //$NON-NLS-1$
            return sourceText;
        }
        return null;
    }

    public static String getLocalPart(String sourceText) {
        if (sourceText != null) {
            String[] tmpSplited = sourceText.split(":"); //$NON-NLS-1$
            return tmpSplited[tmpSplited.length - 1];
        }
        return null;
    }

    public static Boolean containsXSDPrefix(String sourceText) {
        if (sourceText.indexOf("xsd:") != -1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param primitiveType
     * @return
     */
    public static boolean isObjectAnyType(Object propertyOrDatatype,
            String subType) {
        if (propertyOrDatatype instanceof Property) {
            Property property = (Property) propertyOrDatatype;
            if (property != null
                    && property.getType() != null
                    && property.getType().getName()
                            .equals(PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME)
                    && property.getType() instanceof PrimitiveType) {
                Object facetPropertyValue =
                        PrimitivesUtil
                                .getFacetPropertyValue((PrimitiveType) property
                                        .getType(), "objectSubType", property); //$NON-NLS-1$
                if (facetPropertyValue instanceof EnumerationLiteral
                        && ((EnumerationLiteral) facetPropertyValue).getName()
                                .equals(subType)) {
                    return true;
                }
            }
        } else if (propertyOrDatatype instanceof DataType) {
            DataType dataType = (DataType) propertyOrDatatype;
            if (dataType != null
                    && dataType.getGenerals().size() > 0
                    && dataType.getGenerals().get(0) != null
                    && dataType.getGenerals().get(0).getName()
                            .equals(PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME)
                    && dataType.getGenerals().get(0) instanceof PrimitiveType) {
                Object facetPropertyValue =
                        PrimitivesUtil
                                .getFacetPropertyValue((PrimitiveType) dataType,
                                        "objectSubType"); //$NON-NLS-1$
                if (facetPropertyValue instanceof EnumerationLiteral
                        && ((EnumerationLiteral) facetPropertyValue).getName()
                                .equals(subType)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param primitiveType
     * @return
     */
    public static boolean isSignedInteger(Object primitiveType) {
        Object facetPropertyValue = null;
        if (primitiveType instanceof PrimitiveType) {
            facetPropertyValue =
                    PrimitivesUtil
                            .getFacetPropertyValue((PrimitiveType) primitiveType,
                                    "integerSubType"); //$NON-NLS-1$
        } else if (primitiveType instanceof Enumeration) {
            return true;
        }
        if (facetPropertyValue instanceof EnumerationLiteral
                && INTEGER_SUBTYPE_SIGNEDINTEGER
                        .equals((((EnumerationLiteral) facetPropertyValue)
                                .getName()))) {
            return true;
        }
        return false;
    }

    /**
     * @param primitiveType
     * @return
     */
    public static boolean isFloatingPointDecimal(Object primitiveType) {
        Object facetPropertyValue = null;
        if (primitiveType instanceof PrimitiveType) {
            facetPropertyValue =
                    PrimitivesUtil
                            .getFacetPropertyValue((PrimitiveType) primitiveType,
                                    "decimalSubType"); //$NON-NLS-1$
        } else if (primitiveType instanceof Enumeration) {
            return true;
        }
        if (facetPropertyValue instanceof EnumerationLiteral
                && DECIMAL_SUBTYPE_FLOATINGPOINT
                        .equals((((EnumerationLiteral) facetPropertyValue)
                                .getName()))) {
            return true;
        }
        return false;
    }

    /**
     * @param pkg
     * @param keepParsing
     * @return
     */
    public static String getNamespace(Package pkg, Boolean keepParsing) {
        String tmpNamespace = BOMUtils.getNamespace(pkg, keepParsing);
        return tmpNamespace;
    }

    /**
     * @param pkg
     * @param namespace
     * @param keepParsing
     * @return
     */
    public static String getNamespace(Package pkg, String namespace,
            Boolean keepParsing) {
        String tmpNamespace =
                BOMUtils.getNamespace(pkg, namespace, keepParsing);
        return tmpNamespace;
    }

    /**
     * @param original
     * @return
     */
    public static Object clone(Object original) {
        if (original != null) {
            EObject context = (EObject) original;
            EObject copy = EcoreUtil.copy(context);
            return copy;
        } else {
            return null;
        }
    }

    /**
     * @param umlElement
     * @param stereotype
     * @param propertyName
     * @return
     */
    public static boolean hasValue(Element umlElement, Stereotype stereotype,
            String propertyName) {
        if (umlElement != null && propertyName.length() > 0) {
            try {
                boolean value = false;
                if ((stereotype == null || stereotype.getName()
                        .equals(PRIMITIVE_STEREOTYPE_NAME))
                        && umlElement instanceof PrimitiveType
                        && !(umlElement instanceof Property)) {
                    Object facetPropertyValue =
                            PrimitivesUtil
                                    .getFacetPropertyValue((PrimitiveType) umlElement,
                                            propertyName);
                    if (facetPropertyValue != null
                            && String.valueOf(facetPropertyValue).trim()
                                    .length() > 0) {
                        value = true;
                    }
                } else if ((stereotype == null || stereotype.getName()
                        .equals(PRIMITIVE_STEREOTYPE_NAME))
                        && umlElement instanceof Property
                        && !(umlElement instanceof PrimitiveType)
                        && (((Property) umlElement).getType() instanceof PrimitiveType)) {
                    Object facetPropertyValue =
                            PrimitivesUtil
                                    .getFacetPropertyValue((PrimitiveType) ((Property) umlElement)
                                            .getType(),
                                            propertyName,
                                            (Property) umlElement);
                    if (facetPropertyValue != null
                            && String.valueOf(facetPropertyValue).trim()
                                    .length() > 0) {
                        value = true;
                    }
                } else if (stereotype != null) {
                    value = umlElement.hasValue(stereotype, propertyName);
                }
                return value;
            } catch (Exception e) {
            }
        }
        return false;
    }

    /**
     * @param umlElement
     * @param stereotype
     * @param propertyName
     * @return
     */
    public static Object getValue(Element umlElement, Stereotype stereotype,
            String propertyName) {
        Object value = null;
        if (umlElement != null && propertyName.length() > 0) {
            try {
                if (value == null || String.valueOf(value).trim().length() == 0) {
                    if ((stereotype == null || stereotype.getName()
                            .equals(PRIMITIVE_STEREOTYPE_NAME))
                            && umlElement instanceof PrimitiveType
                            && !(umlElement instanceof Property)) {
                        value =
                                PrimitivesUtil
                                        .getFacetPropertyValue((PrimitiveType) umlElement,
                                                propertyName);
                    } else if ((stereotype == null || stereotype.getName()
                            .equals(PRIMITIVE_STEREOTYPE_NAME))
                            && umlElement instanceof Property
                            && !(umlElement instanceof PrimitiveType)
                            && (((Property) umlElement).getType() instanceof PrimitiveType)) {
                        value =
                                PrimitivesUtil
                                        .getFacetPropertyValue((PrimitiveType) ((Property) umlElement)
                                                .getType(),
                                                propertyName,
                                                (Property) umlElement);
                    } else if (stereotype != null) {
                        value = umlElement.getValue(stereotype, propertyName);
                    }
                }

                String bomTypeName =
                        getBOMTypeNameForProperty(umlElement.eResource()
                                .getResourceSet(), propertyName);
                if (value != null
                        && String.valueOf(value).trim().length() > 0
                        && !(value instanceof EnumerationLiteral)
                        && PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME
                                .equals(bomTypeName)) {
                    value =
                            new BigDecimal(String.valueOf(value))
                                    .toEngineeringString();
                }
            } catch (Exception e) {
            }
        }
        return value;
    }

    /**
     * Brings back the BOM Type that a specific property relates to
     * 
     * @param rs
     * @param propertyName
     * @return
     */
    private static String getBOMTypeNameForProperty(ResourceSet rs,
            String propertyName) {
        if (PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_LENGTH.equals(propertyName)) {
            return PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
        } else if (PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_DEFAULT_VALUE
                .equals(propertyName)) {
            return PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
        } else if (PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_PATTERN_VALUE
                .equals(propertyName)) {
            return PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
        } else if (PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_UPPER
                .equals(propertyName)) {
            return PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME;
        } else if (PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_UPPER_INCLUSIVE
                .equals(propertyName)) {
            return PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME;
        } else if (PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LOWER
                .equals(propertyName)) {
            return PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME;
        } else if (PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LOWER_INCLUSIVE
                .equals(propertyName)) {
            return PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME;
        } else if (PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LENGTH
                .equals(propertyName)) {
            return PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME;
        } else if (PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_PLACES
                .equals(propertyName)) {
            return PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME;
        } else if (PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_DEFAULT_VALUE
                .equals(propertyName)) {
            return PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME;
        } else if (PrimitivesUtil.BOM_PRIMITIVE_FACET_BOOLEAN_DEFAULT_VALUE
                .equals(propertyName)) {
            return PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME;
        } else if (PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LENGTH
                .equals(propertyName)) {
            return PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME;
        } else if (PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_DEFAULT_VALUE
                .equals(propertyName)) {
            return PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME;
        } else if (PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_UPPER
                .equals(propertyName)) {
            return PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME;
        } else if (PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LOWER
                .equals(propertyName)) {
            return PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME;
        } else if (PrimitivesUtil.BOM_PRIMITIVE_FACET_DATE_DEFAULT_VALUE
                .equals(propertyName)) {
            return PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME;
        } else if (PrimitivesUtil.BOM_PRIMITIVE_FACET_DATE_TIME_DEFAULT_VALUE
                .equals(propertyName)) {
            return PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME;
        } else if (PrimitivesUtil.BOM_PRIMITIVE_FACET_TIME_DEFAULT_VALUE
                .equals(propertyName)) {
            return PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME;

        }
        return null;
    }

    /**
     * Check if the given classifier is a BOM base primitive type.
     * 
     * @param classifier
     * @return
     */
    public static Boolean isStandardPrimitive(Classifier classifier) {
        if (classifier != null && classifier.eResource() != null
                && classifier.eResource().getResourceSet() != null) {
            List<PrimitiveType> types = getDefaultPrims();
            if (types.contains(classifier)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the default primitive types
     * 
     * @return
     */
    private static List<PrimitiveType> getDefaultPrims() {
        if (defaultPrims == null) {
            ResourceSet rs =
                    XpdResourcesPlugin.getDefault().getEditingDomain()
                            .getResourceSet();
            defaultPrims = new ArrayList<PrimitiveType>();

            defaultPrims.add(PrimitivesUtil.getStandardPrimitiveTypeByName(rs,
                    PrimitivesUtil.BOM_PRIMITIVE_ATTACHMENT_NAME));
            defaultPrims.add(PrimitivesUtil.getStandardPrimitiveTypeByName(rs,
                    PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME));
            defaultPrims.add(PrimitivesUtil.getStandardPrimitiveTypeByName(rs,
                    PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME));
            defaultPrims.add(PrimitivesUtil.getStandardPrimitiveTypeByName(rs,
                    PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME));
            defaultPrims.add(PrimitivesUtil.getStandardPrimitiveTypeByName(rs,
                    PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME));
            defaultPrims.add(PrimitivesUtil.getStandardPrimitiveTypeByName(rs,
                    PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME));
            defaultPrims.add(PrimitivesUtil.getStandardPrimitiveTypeByName(rs,
                    PrimitivesUtil.BOM_PRIMITIVE_DURATION_NAME));
            defaultPrims.add(PrimitivesUtil.getStandardPrimitiveTypeByName(rs,
                    PrimitivesUtil.BOM_PRIMITIVE_ID_NAME));
            defaultPrims.add(PrimitivesUtil.getStandardPrimitiveTypeByName(rs,
                    PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME));
            defaultPrims.add(PrimitivesUtil.getStandardPrimitiveTypeByName(rs,
                    PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME));
            defaultPrims.add(PrimitivesUtil.getStandardPrimitiveTypeByName(rs,
                    PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME));
            defaultPrims.add(PrimitivesUtil.getStandardPrimitiveTypeByName(rs,
                    PrimitivesUtil.BOM_PRIMITIVE_URI_NAME));

        }
        return defaultPrims;
    }

    /**
     * Returns whether this classifier has any top level attributes by looking
     * to see if it has the stereotype applied
     * 
     * @param classifier
     * @return
     */
    public static boolean isTopLevelAttribute(Classifier classifier) {
        Stereotype topLevelAttributeStereotype =
                getTopLevelAttributeStereotype(classifier.getModel());
        if (topLevelAttributeStereotype != null) {
            Object attributes =
                    classifier
                            .getModel()
                            .getValue(topLevelAttributeStereotype,
                                    XsdStereotypeUtils.XSD_TOP_LEVEL_ATTRIBUTE_ATTRIBUTES);

            if (attributes instanceof EList
                    && ((EList<EObject>) attributes).size() > 0) {
                for (EObject attribute : (EList<EObject>) attributes) {
                    TopLevelAttributeWrapper topLevelAttributeWrapper =
                            new TopLevelAttributeWrapper(attribute);
                    if (topLevelAttributeWrapper.getType().equals(classifier)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Returns whether this classifier has any top level attributes by looking
     * to see if it has the stereotype applied
     * 
     * @param classifier
     * @return
     */
    public static boolean isTopLevelAttribute(Classifier classifier,
            List<TopLevelAttributeWrapper> topLevelAttributeWrappers) {
        for (TopLevelAttributeWrapper attributeWrapper : topLevelAttributeWrappers) {
            if (attributeWrapper.getType().equals(classifier)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the top level attribute stereotype if there is one applied for
     * the given classifier
     * 
     * @param classifier
     * @return
     */
    public static Stereotype getTopLevelAttributeStereotype(Package tmpPackage) {
        Stereotype stereotype = null;
        Iterator<Profile> profilesIter =
                tmpPackage.getAppliedProfiles().iterator();
        while (profilesIter.hasNext()) {
            Profile profile = profilesIter.next();
            if (profile.getName()
                    .equals(XsdStereotypeUtils.XSD_NOTATION_PROFILE_NAME)) {
                Object obj =
                        profile.getPackagedElement(XsdStereotypeUtils.TOP_LEVEL_ATTRIBUTES);
                if (obj instanceof Stereotype) {
                    stereotype = (Stereotype) obj;
                    if (stereotype.getName()
                            .equals(XsdStereotypeUtils.TOP_LEVEL_ATTRIBUTES)) {
                        Stereotype alreadyAppliedStereotype =
                                tmpPackage.getAppliedStereotype(stereotype
                                        .getQualifiedName());
                        if (alreadyAppliedStereotype != null) {
                            return alreadyAppliedStereotype;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Gets the list of top level attributes from a classifier with top level
     * attribute stereotype applied
     * 
     * @param classifier
     * @return
     */
    public static List<Object> getTopLevelAttributes(Package tmpPackage) {
        Stereotype topLevelAttributeStereotype =
                getTopLevelAttributeStereotype(tmpPackage);

        if (topLevelAttributeStereotype != null) {
            Object attributes =
                    tmpPackage
                            .getValue(topLevelAttributeStereotype,
                                    XsdStereotypeUtils.XSD_TOP_LEVEL_ATTRIBUTE_ATTRIBUTES);

            if (attributes instanceof EList) {
                return (EList) attributes;
            }
        }
        return new ArrayList<Object>();
    }

    public static List<TopLevelAttributeWrapper> getTopLevelAttributeWrappers(
            Package tmpPackage) {
        ArrayList<TopLevelAttributeWrapper> topLevelAttributeWrappers =
                new ArrayList<TopLevelAttributeWrapper>();
        List<Object> attributes = getTopLevelAttributes(tmpPackage);

        for (Object attribute : attributes) {
            TopLevelAttributeWrapper topLevelAttributeWrapper =
                    new TopLevelAttributeWrapper((EObject) attribute);
            topLevelAttributeWrappers.add(topLevelAttributeWrapper);

        }

        return topLevelAttributeWrappers;
    }

    /**
     * Returns whether this classifier is used to represent a top level
     * attribute of xsd type (in which case there will only ever be one top
     * level attribute for this obvioiusly)
     * 
     * @param classifier
     * @return
     */
    public static boolean isTopLevelAttributeXSDBaseType(Classifier classifier) {
        Stereotype topLevelAttributeStereotype =
                getTopLevelAttributeStereotype(classifier.getModel());
        if (topLevelAttributeStereotype != null) {
            Object attributes =
                    classifier
                            .getModel()
                            .getValue(topLevelAttributeStereotype,
                                    XsdStereotypeUtils.XSD_TOP_LEVEL_ATTRIBUTE_ATTRIBUTES);

            if (attributes instanceof EList
                    && ((EList<EObject>) attributes).size() > 0) {
                for (EObject attribute : (EList<EObject>) attributes) {
                    TopLevelAttributeWrapper topLevelAttributeWrapper =
                            new TopLevelAttributeWrapper(attribute);
                    if (topLevelAttributeWrapper.getIsBaseXSDType()
                            && topLevelAttributeWrapper.getType()
                                    .equals(classifier)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Returns whether this classifier is used to represent an anonymous top
     * level attribute type
     * 
     * @param classifier
     * @return
     */
    public static boolean isAnonymousTopLevelAttribute(Classifier classifier) {
        Stereotype topLevelAttributeStereotype =
                getTopLevelAttributeStereotype(classifier.getModel());
        if (topLevelAttributeStereotype != null) {
            Object attributes =
                    classifier
                            .getModel()
                            .getValue(topLevelAttributeStereotype,
                                    XsdStereotypeUtils.XSD_TOP_LEVEL_ATTRIBUTE_ATTRIBUTES);

            if (attributes instanceof EList
                    && ((EList<EObject>) attributes).size() > 0) {
                for (EObject attribute : (EList<EObject>) attributes) {
                    TopLevelAttributeWrapper topLevelAttributeWrapper =
                            new TopLevelAttributeWrapper(attribute);
                    if (topLevelAttributeWrapper.getIsAnonymous()
                            && topLevelAttributeWrapper.getType()
                                    .equals(classifier)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Returns whether this classifier is used to represent an anonymous top
     * level attribute type
     * 
     * @param classifier
     * @return
     */
    public static boolean isAnonymousTopLevelAttribute(Classifier classifier,
            List<TopLevelAttributeWrapper> topLevelAttributeWrappers) {
        for (TopLevelAttributeWrapper attributeWrapper : topLevelAttributeWrappers) {
            if (attributeWrapper.getIsAnonymous()
                    && attributeWrapper.getType().equals(classifier)) {
                return true;
            }
        }
        return false;
    }

    public static List<TopLevelAttributeWrapper> getAttributeWrappers(
            Classifier classifier) {
        ArrayList<TopLevelAttributeWrapper> attributeWrappers =
                new ArrayList<TopLevelAttributeWrapper>();
        List<Object> attributes = getTopLevelAttributes(classifier.getModel());

        for (Object attribute : attributes) {
            TopLevelAttributeWrapper topLevelAttributeWrapper =
                    new TopLevelAttributeWrapper((EObject) attribute);
            if (topLevelAttributeWrapper.getType().equals(classifier)) {
                attributeWrappers.add(topLevelAttributeWrapper);
            }

        }

        return attributeWrappers;
    }

    /**
     * @param xsdAttribute
     * @return
     */
    public static String getAttributeName(Object xsdAttribute) {
        return new TopLevelAttributeWrapper((EObject) xsdAttribute).getName();
    }

    /**
     * @param xsdAttribute
     * @return
     */
    public static Classifier getAttributeType(Object xsdAttribute) {
        return new TopLevelAttributeWrapper((EObject) xsdAttribute).getType();
    }

    /**
     * @param xsdAttribute
     * @return
     */
    public static String getAttributeTargetNamespace(Object xsdAttribute) {
        return new TopLevelAttributeWrapper((EObject) xsdAttribute)
                .getTargetNamespace();
    }

    /**
     * @param xsdAttribute
     * @return
     */
    public static Boolean getAttributeIsAnonymous(Object xsdAttribute) {
        return new TopLevelAttributeWrapper((EObject) xsdAttribute)
                .getIsAnonymous();
    }

    /**
     * @param xsdAttribute
     * @return
     */
    public static Boolean getAttributeIsBaseXSDType(Object xsdAttribute) {
        return new TopLevelAttributeWrapper((EObject) xsdAttribute)
                .getIsBaseXSDType();
    }

    /**
     * @param xsdAttribute
     * @return
     */
    public static String getAttributeID(Object xsdAttribute) {
        return new TopLevelAttributeWrapper((EObject) xsdAttribute).getID();
    }

    /**
     * @param xsdAttribute
     * @return
     */
    public static String getAttributeFixed(Object xsdAttribute) {
        return new TopLevelAttributeWrapper((EObject) xsdAttribute).getFixed();
    }

    /**
     * @param xsdAttribute
     * @return
     */
    public static String getAttributeDefault(Object xsdAttribute) {
        return new TopLevelAttributeWrapper((EObject) xsdAttribute)
                .getDefault();
    }

    /**
     * Returns whether this classifier has any top level elements by looking to
     * see if it has the stereotype applied
     * 
     * @param classifier
     * @return
     */
    public static boolean isTopLevelElement(Classifier classifier) {
        Stereotype topLevelElementStereotype =
                getTopLevelElementStereotype(classifier.getModel());
        if (topLevelElementStereotype != null) {
            Object elements =
                    classifier.getModel().getValue(topLevelElementStereotype,
                            XsdStereotypeUtils.XSD_TOP_LEVEL_ELEMENT_ELEMENTS);

            if (elements instanceof EList
                    && ((EList<EObject>) elements).size() > 0) {
                for (EObject element : (EList<EObject>) elements) {
                    TopLevelElementWrapper topLevelElementWrapper =
                            new TopLevelElementWrapper(element);
                    if (topLevelElementWrapper.getType().equals(classifier)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Returns whether this classifier has any top level elements by looking to
     * see if it has the stereotype applied
     * 
     * @param classifier
     * @return
     */
    public static boolean isTopLevelElement(Classifier classifier,
            List<TopLevelElementWrapper> topLevelElementWrappers) {
        for (TopLevelElementWrapper elementWrapper : topLevelElementWrappers) {
            if (elementWrapper.getType().equals(classifier)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the top level element stereotype if there is one applied for the
     * given classifier
     * 
     * @param classifier
     * @return
     */
    public static Stereotype getTopLevelElementStereotype(Package tmpPackage) {
        Stereotype stereotype = null;
        Iterator<Profile> profilesIter =
                tmpPackage.getAppliedProfiles().iterator();
        while (profilesIter.hasNext()) {
            Profile profile = profilesIter.next();
            if (profile.getName()
                    .equals(XsdStereotypeUtils.XSD_NOTATION_PROFILE_NAME)) {
                Object obj =
                        profile.getPackagedElement(XsdStereotypeUtils.TOP_LEVEL_ELEMENTS);
                if (obj instanceof Stereotype) {
                    stereotype = (Stereotype) obj;
                    if (stereotype.getName()
                            .equals(XsdStereotypeUtils.TOP_LEVEL_ELEMENTS)) {
                        Stereotype alreadyAppliedStereotype =
                                tmpPackage.getAppliedStereotype(stereotype
                                        .getQualifiedName());
                        if (alreadyAppliedStereotype != null) {
                            return alreadyAppliedStereotype;
                        }
                    }
                }
            }
        }
        return null;
    }

    public static void populateTopLevelDetails(ExportTransformationData data,
            List<Package> packages) {
        HashSet<String> topLevelElementsReferencedBySubstitutionGroups =
                new HashSet<String>();
        Set<Classifier> baseTypeTopLevelAttributes = new HashSet<Classifier>();
        Set<Classifier> anonymousTopLevelAttributes = new HashSet<Classifier>();
        Set<Classifier> baseTypeTopLevelElements = new HashSet<Classifier>();
        Set<Classifier> anonymousTopLevelElements = new HashSet<Classifier>();

        for (Package tmpPackage : packages) {
            List<Object> topLevelAttributes = getTopLevelAttributes(tmpPackage);
            for (Object topLevelAttribute : topLevelAttributes) {
                if (getAttributeIsBaseXSDType(topLevelAttribute)) {
                    baseTypeTopLevelAttributes
                            .add(getAttributeType(topLevelAttribute));
                }
                if (getAttributeIsAnonymous(topLevelAttribute)) {
                    anonymousTopLevelAttributes
                            .add(getAttributeType(topLevelAttribute));
                }
            }

            List<Object> topLevelElements = getTopLevelElements(tmpPackage);
            for (Object topLevelElement : topLevelElements) {
                String elementSubstitutionGroupLocalPart =
                        getElementSubstitutionGroupLocalPart(topLevelElement);
                if (elementSubstitutionGroupLocalPart != null) {
                    topLevelElementsReferencedBySubstitutionGroups
                            .add(elementSubstitutionGroupLocalPart);
                }
                if (getElementIsBaseXSDType(topLevelElement)) {
                    baseTypeTopLevelElements
                            .add(getElementType(topLevelElement));
                }
                if (getElementIsAnonymous(topLevelElement)) {
                    anonymousTopLevelElements
                            .add(getElementType(topLevelElement));
                }
            }
        }
        data.setAllTopLevelElementsReferencedBySubstitutionGroups(topLevelElementsReferencedBySubstitutionGroups);
        data.setBaseTypeTopLevelAttributes(baseTypeTopLevelAttributes);
        data.setAnonymousTopLevelAttributes(anonymousTopLevelAttributes);
        data.setBaseTypeTopLevelElements(baseTypeTopLevelElements);
        data.setAnonymousTopLevelElements(anonymousTopLevelElements);
    }

    /**
     * Gets the list of top level elements from a classifier with top level
     * element stereotype applied
     * 
     * @param classifier
     * @return
     */
    public static List<Object> getTopLevelElements(Package tmpPackage) {
        Stereotype topLevelElementStereotype =
                getTopLevelElementStereotype(tmpPackage);

        if (topLevelElementStereotype != null) {
            Object elements =
                    tmpPackage.getValue(topLevelElementStereotype,
                            XsdStereotypeUtils.XSD_TOP_LEVEL_ELEMENT_ELEMENTS);

            if (elements instanceof EList) {
                return (EList) elements;
            }
        }
        return new ArrayList<Object>();
    }

    public static List<TopLevelElementWrapper> getTopLevelElementWrappers(
            Package tmpPackage) {
        ArrayList<TopLevelElementWrapper> topLevelElementWrappers =
                new ArrayList<TopLevelElementWrapper>();
        List<Object> elements = getTopLevelElements(tmpPackage);

        for (Object element : elements) {
            TopLevelElementWrapper topLevelElementWrapper =
                    new TopLevelElementWrapper((EObject) element);
            topLevelElementWrappers.add(topLevelElementWrapper);

        }

        return topLevelElementWrappers;
    }

    /**
     * Returns whether this classifier is used to represent a top level element
     * of xsd type (in which case there will only ever be one top level element
     * for this obvioiusly)
     * 
     * @param classifier
     * @return
     */
    public static boolean isTopLevelElementXSDBaseType(Classifier classifier) {
        Stereotype topLevelElementStereotype =
                getTopLevelElementStereotype(classifier.getModel());
        if (topLevelElementStereotype != null) {
            Object elements =
                    classifier.getModel().getValue(topLevelElementStereotype,
                            XsdStereotypeUtils.XSD_TOP_LEVEL_ELEMENT_ELEMENTS);

            if (elements instanceof EList
                    && ((EList<EObject>) elements).size() > 0) {
                for (EObject element : (EList<EObject>) elements) {
                    TopLevelElementWrapper topLevelElementWrapper =
                            new TopLevelElementWrapper(element);
                    if (topLevelElementWrapper.getIsBaseXSDType()
                            && topLevelElementWrapper.getType()
                                    .equals(classifier)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Returns whether this classifier is used to represent a top level element
     * of xsd type (in which case there will only ever be one top level element
     * for this obvioiusly)
     * 
     * @param classifier
     * @return
     */
    public static boolean isTopLevelElementXSDBaseType(Classifier classifier,
            List<TopLevelElementWrapper> topLevelElementWrappers) {
        for (TopLevelElementWrapper elementWrapper : topLevelElementWrappers) {
            if (elementWrapper.getIsBaseXSDType()
                    && elementWrapper.getType().equals(classifier)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns whether this classifier is used to represent an anonymous top
     * level element type
     * 
     * @param classifier
     * @return
     */
    public static boolean isAnonymousTopLevelElement(Classifier classifier) {
        Stereotype topLevelElementStereotype =
                getTopLevelElementStereotype(classifier.getModel());
        if (topLevelElementStereotype != null) {
            Object elements =
                    classifier.getModel().getValue(topLevelElementStereotype,
                            XsdStereotypeUtils.XSD_TOP_LEVEL_ELEMENT_ELEMENTS);

            if (elements instanceof EList
                    && ((EList<EObject>) elements).size() > 0) {
                for (EObject element : (EList<EObject>) elements) {
                    TopLevelElementWrapper topLevelElementWrapper =
                            new TopLevelElementWrapper(element);
                    if (topLevelElementWrapper.getIsAnonymous()
                            && topLevelElementWrapper.getType()
                                    .equals(classifier)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Returns whether this classifier is used to represent an anonymous top
     * level element type
     * 
     * @param classifier
     * @return
     */
    public static boolean isAnonymousTopLevelElement(Classifier classifier,
            List<TopLevelElementWrapper> topLevelElementWrappers) {
        for (TopLevelElementWrapper elementWrapper : topLevelElementWrappers) {
            if (elementWrapper.getIsAnonymous()
                    && elementWrapper.getType().equals(classifier)) {
                return true;
            }
        }
        return false;
    }

    public static String getAnonymousTopLevelElementName(Classifier classifier) {
        Stereotype topLevelElementStereotype =
                getTopLevelElementStereotype(classifier.getModel());
        if (topLevelElementStereotype != null) {
            Object elements =
                    classifier.getModel().getValue(topLevelElementStereotype,
                            XsdStereotypeUtils.XSD_TOP_LEVEL_ELEMENT_ELEMENTS);

            if (elements instanceof EList
                    && ((EList<EObject>) elements).size() > 0) {
                for (EObject element : (EList<EObject>) elements) {
                    TopLevelElementWrapper topLevelElementWrapper =
                            new TopLevelElementWrapper(element);
                    if (topLevelElementWrapper.getIsAnonymous()
                            && topLevelElementWrapper.getType()
                                    .equals(classifier)) {
                        return topLevelElementWrapper.getName();
                    }
                }
            }
        }
        return null;
    }

    public static List<TopLevelElementWrapper> getElementWrappers(
            Classifier classifier) {
        ArrayList<TopLevelElementWrapper> elementWrappers =
                new ArrayList<TopLevelElementWrapper>();
        List<Object> elements = getTopLevelElements(classifier.getModel());
        if (elements != null) {
            for (Object element : elements) {
                TopLevelElementWrapper topLevelElementWrapper =
                        new TopLevelElementWrapper((EObject) element);
                if (topLevelElementWrapper.getType().equals(classifier)) {
                    elementWrappers.add(topLevelElementWrapper);
                }

            }
        }
        return elementWrappers;
    }

    /**
     * @param xsdElement
     * @return
     */
    public static String getElementName(Object xsdElement) {
        return new TopLevelElementWrapper((EObject) xsdElement).getName();
    }

    public static Classifier getElementType(Object xsdElement) {
        return new TopLevelElementWrapper((EObject) xsdElement).getType();
    }

    /**
     * @param xsdElement
     * @return
     */
    public static String getElementTargetNamespace(Object xsdElement) {
        return new TopLevelElementWrapper((EObject) xsdElement)
                .getTargetNamespace();
    }

    /**
     * @param xsdElement
     * @return
     */
    public static Boolean getElementIsAnonymous(Object xsdElement) {
        return new TopLevelElementWrapper((EObject) xsdElement)
                .getIsAnonymous();
    }

    /**
     * @param xsdElement
     * @return
     */
    public static Boolean getElementIsBaseXSDType(Object xsdElement) {
        return new TopLevelElementWrapper((EObject) xsdElement)
                .getIsBaseXSDType();
    }

    /**
     * @param xsdElement
     * @return
     */
    public static String getElementID(Object xsdElement) {
        return new TopLevelElementWrapper((EObject) xsdElement).getID();
    }

    /**
     * @param xsdElement
     * @return
     */
    public static String getElementFixed(Object xsdElement) {
        return new TopLevelElementWrapper((EObject) xsdElement).getFixed();
    }

    /**
     * @param xsdElement
     * @return
     */
    public static String getElementFinal(Object xsdElement) {
        return new TopLevelElementWrapper((EObject) xsdElement).getFinal();
    }

    /**
     * @param xsdElement
     * @return
     */
    public static String getElementBlock(Object xsdElement) {
        return new TopLevelElementWrapper((EObject) xsdElement).getBlock();
    }

    /**
     * @param xsdElement
     * @return
     */
    public static Boolean getElementNillable(Object xsdElement) {
        return new TopLevelElementWrapper((EObject) xsdElement).getNillable();
    }

    /**
     * @param xsdElement
     * @return
     */
    public static Boolean getElementAbstract(Object xsdElement) {
        return new TopLevelElementWrapper((EObject) xsdElement).getAbstract();
    }

    /**
     * @param xsdElement
     * @return
     */
    public static String getElementSubstitutionGroup(Object xsdElement) {
        return new TopLevelElementWrapper((EObject) xsdElement)
                .getSubstitutionGroup();
    }

    /**
     * @param xsdElement
     * @return
     */
    public static String getElementSubstitutionGroupNamespace(Object xsdElement) {
        String namespace = null;
        TopLevelElementWrapper topLevelElementWrapper =
                new TopLevelElementWrapper((EObject) xsdElement);
        // if (topLevelElementWrapper.getType().equals(classifier)) {
        String substGroup = topLevelElementWrapper.getSubstitutionGroup();
        if (substGroup != null && substGroup.length() > 0) {
            String[] splitSubstGroup = substGroup.split(":");
            if (splitSubstGroup.length == 3) {
                namespace = splitSubstGroup[0] + ":" + splitSubstGroup[1];
            }
        }
        // }
        return namespace;
    }

    /**
     * @param xsdElement
     * @return
     */
    public static String getElementSubstitutionGroupLocalPart(Object xsdElement) {
        String namespace = null;
        TopLevelElementWrapper topLevelElementWrapper =
                new TopLevelElementWrapper((EObject) xsdElement);
        // if (topLevelElementWrapper.getType().equals(classifier)) {
        String substGroup = topLevelElementWrapper.getSubstitutionGroup();
        if (substGroup != null && substGroup.length() > 0) {
            String[] splitSubstGroup = substGroup.split(":");
            if (splitSubstGroup.length == 3) {
                namespace = splitSubstGroup[2];
            }
        }
        // }
        return namespace;
    }

    /**
     * @param xsdElement
     * @return
     */
    public static String getElementDefault(Object xsdElement) {
        return new TopLevelElementWrapper((EObject) xsdElement).getDefault();
    }

    /**
     * <p>
     * Checks whether the Classifier should be exported to XSD with a
     * corresponding Top Level Element. The following criteria is used:
     * </p>
     * <p>
     * 1. If the Classifier originated from a BOM with the XSD notation applied
     * then it is not a user-defined BOM. Therefore the classifier is not
     * exported with a corresponding TLE and <b>false</b> is returned.
     * <p>
     * 
     * <p>
     * 2. The Classifier is queried to see if it has a EAnnotation boolean flag
     * indiacting whther to export with a TLE or not. The flag value is
     * returned.
     * </p>
     * 
     * <p>
     * 3. If no EAnnotation is specified then the Classifier will be exported
     * with a TLE and <b>true</b> is returned.
     * </p>
     * 
     * @param classifier
     * @return boolean
     */
    public static boolean isUserDefinedTopLevelElement(Classifier classifier) {

        return BOMUtils.isExportAsTLE(classifier);
    }

    /**
     * @param basicFeatureMap
     * @param value
     * @return
     */
    public static String addFeatureMapEntry(Object basicFeatureMap,
            String value, String propertyName) {
        if (basicFeatureMap instanceof BasicFeatureMap) {
            EStructuralFeature demandFeature =
                    org.eclipse.emf.ecore.util.ExtendedMetaData.INSTANCE
                            .demandFeature("http://www.eclipse.org/emf/2002/Ecore", //$NON-NLS-1$
                                    propertyName,
                                    false);

            boolean hasFeatureSetAlready = false;
            for (Iterator<Entry> iterator =
                    ((BasicFeatureMap) basicFeatureMap).iterator(); iterator
                    .hasNext();) {
                Entry entry = iterator.next();

                if (demandFeature.equals(entry.getEStructuralFeature())) {
                    /* feature added twice, ignore second attempt */
                    hasFeatureSetAlready = true;
                }
            }

            if (!hasFeatureSetAlready) {
                ((BasicFeatureMap) basicFeatureMap).add(demandFeature, value);
            }
        }
        return null;
    }

    /**
     * SID: XPD-1605: Get the root files of the given potentially dependent set
     * of files.
     * <p>
     * <b>Note:</b> that all BOM's immediately referenced from a BOM with
     * class-operations will be treated as root-boms. This is because the OAW
     * Bom2Xsd transform _does_not_ traverse down to referenced BOM's becasue of
     * types referenced from BOM operation parameters/returns.
     * <p>
     * <b>If there are cyclic dependencies this algorithm will return one
     * element in the cycle (but dependencies that start with a cycle but at
     * some point break out (like A refs B which refs A and C) then one or more
     * things after the break-out may be included twice. </b>
     * 
     * @param files
     * 
     * @return set of root files.
     */

    public static Set<IFile> getRootBomsForBom2XsdTransform(
            Collection<IFile> files) {

        /* Create a quick access cache for recursive dependencies of each file. */
        Map<IFile, Set<IFile>> dependencyMap = createFileToDependencyMap(files);

        /* AND Build Set of files referenced by other files. */
        Set<IFile> filesReferencedByOthers = new HashSet<IFile>();

        for (IFile file : files) {

            /*
             * If this is a BOM with Operations then the OAW transform WILL NOT
             * traverse into BOM's referenced ONLY from operation parameters -
             * simplest way around this is to pretend that the WSDL-BOM has no
             * dependencies so that the direct children of it are counted as
             * root level files (which won't hurt the transform to xsd anyway
             */
            if (!isBOMWithOperations(file)) {
                Set<IFile> dependencies = dependencyMap.get(file);
                filesReferencedByOthers.addAll(dependencies);
            }

        }

        /*
         * return rootFiles (or any file stuck in a cyclic loop).
         */
        Set<IFile> rootFiles = new HashSet<IFile>();

        /*
         * PHASE 1. The root top level files. This will be every file that does
         * not appear in set of files referenced by other files
         */
        for (IFile file : files) {
            if (!filesReferencedByOthers.contains(file)) {
                /* This file not referenced by any other - must be root */
                rootFiles.add(file);
            }
        }

        /*
         * PHASE 2. Find the files that are not referenced by the root files.
         * These MUST will be files either in or hanging off of cyclic
         * dependencies.
         */

        Set<IFile> filesReferencedByRootFiles = new HashSet<IFile>();
        for (IFile rootFile : rootFiles) {
            Set<IFile> dependencies = dependencyMap.get(rootFile);
            filesReferencedByRootFiles.addAll(dependencies);
        }

        Set<IFile> filesNotRootOrReferencedByRoot = new HashSet<IFile>();
        for (IFile file : files) {
            if (!rootFiles.contains(file)
                    && !filesReferencedByRootFiles.contains(file)) {
                filesNotRootOrReferencedByRoot.add(file);
            }
        }

        /*
         * PHASE 3: Go thru the files not root or referenced by root (files in
         * cyclic dependencies or hanging off cyclic dependencies), add the
         * first to the set of root's then remove it's dependencies from the
         * unhandled files.
         */
        while (!filesNotRootOrReferencedByRoot.isEmpty()) {

            IFile first = filesNotRootOrReferencedByRoot.iterator().next();
            rootFiles.add(first);

            Set<IFile> dependencies = dependencyMap.get(first);

            filesNotRootOrReferencedByRoot.remove(first);
            if (!dependencies.isEmpty()) {
                filesNotRootOrReferencedByRoot.removeAll(dependencies);
            }
        }

        return rootFiles;
    }

    /**
     * Create a quick access cache for recursive dependencies of each file.
     * 
     * @param files
     * 
     * @return Map of each of teh given files to it's dependencies.
     */
    public static Map<IFile, Set<IFile>> createFileToDependencyMap(
            Collection<IFile> files) {
        Map<IFile, Set<IFile>> dependencyMap = new HashMap<IFile, Set<IFile>>();

        for (IFile file : files) {
            Set<IFile> dependencies = new HashSet<IFile>();

            addDependencies(file, dependencies);

            dependencyMap.put(file, dependencies);
        }
        return dependencyMap;
    }

    /**
     * @param file
     * @return <code>true</code> if this is a BOM file with operations in
     *         classes.
     */
    private static boolean isBOMWithOperations(IResource file) {
        WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(file);

        if (wc instanceof BOMWorkingCopy) {
            BOMWorkingCopy bwc = (BOMWorkingCopy) wc;

            Model model = (Model) bwc.getRootElement();
            if (model != null) {
                EList<PackageableElement> elements =
                        model.getPackagedElements();

                if (hasOperations(elements)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * @param elements
     * @return <code>true</code> if any of the classes in packaged elements has
     *         operations.
     */
    private static boolean hasOperations(EList<PackageableElement> elements) {
        for (PackageableElement pkgElement : elements) {
            if (pkgElement instanceof Class) {
                /* Check for operations in Class */
                EList<Operation> operations =
                        ((Class) pkgElement).getOwnedOperations();
                if (operations != null && !operations.isEmpty()) {
                    return true;
                }

            } else if (pkgElement instanceof Package) {
                /* Recurs into sub-package. */
                EList<PackageableElement> subPackageElements =
                        ((Package) pkgElement).getPackagedElements();

                if (hasOperations(subPackageElements)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Sid XPD-1605:
     * 
     * @param file
     * @return Files that given file references (recursively adds to given list
     *         until hit a cycle.
     */
    private static void addDependencies(IFile file, Set<IFile> dependencies) {
        WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(file);
        if (workingCopy != null) {
            Collection<IResource> deps = workingCopy.getDependency();

            if (deps != null) {
                for (IResource dep : deps) {
                    if (dep instanceof IFile) {
                        if (!dependencies.contains(dep)) {
                            dependencies.add((IFile) dep);

                            addDependencies((IFile) dep, dependencies);
                        }
                    }
                }
            }
        }

        return;
    }

    /**
     * @param obj
     * @param value
     */
    public static void setMemberTypesList(Object obj, Object value) {
        EStructuralFeature feat =
                ((EObject) obj).eClass().getEStructuralFeature("memberTypes");
        ((EObject) obj).eSet(feat, value);
    }

    /**
     * @param data
     * @param memberTypes
     * @return
     */
    public static List<String> getMemberTypeStrValues(
            ExportTransformationData data, String memberTypes,
            List<DataType> anonymousDataTypes) {
        ArrayList<String> memberTypeStrVals = new ArrayList<String>();
        HashMap<String, String> anonymousDataTypeNames =
                new HashMap<String, String>();
        for (DataType dataType : anonymousDataTypes) {
            Stereotype xsdNotationStereotype =
                    getXSDNotationStereotype(dataType,
                            XsdStereotypeUtils.XSD_BASED_PRIMITIVETYPE);
            String existingName =
                    (String) getXSDNotationProperty(dataType,
                            xsdNotationStereotype,
                            XsdStereotypeUtils.XSD_PROPERTY_SIMPLETYPE_NAME);
            anonymousDataTypeNames.put(existingName, dataType.getName());

        }
        String[] splitStr =
                memberTypes.replace("[", "").replace("]", "").split(",");
        if (splitStr.length > 0) {
            for (int i = 0; i < splitStr.length; i++) {
                String namespaceAndName = splitStr[i].trim();
                String[] split = namespaceAndName.split("}");
                String namespace = split[0].trim().replace("{", "");
                String name = split[1].trim();
                if (anonymousDataTypeNames.get(name) != null) {
                    name = anonymousDataTypeNames.get(name);
                }
                String prefixForNamespace =
                        data.getPrefixForNamespace(namespace);
                if (prefixForNamespace == null
                        || prefixForNamespace.trim().length() == 0) {
                    prefixForNamespace =
                            data.generatePrefix(data
                                    .getJavaPackageNameFromURI(namespace));
                }
                memberTypeStrVals.add(prefixForNamespace + ":" + name);
            }
        }
        return memberTypeStrVals;
    }

    /**
     * @param classifier
     * @return
     */
    public static boolean isClassifierOriginAnonymous(Classifier classifier) {
        boolean isAnonUnionSimpleType = false;
        if (classifier != null && classifier instanceof DataType) {
            Iterator<Stereotype> stereoTypeIter =
                    classifier.getAppliedStereotypes().iterator();
            while (stereoTypeIter.hasNext()) {
                Stereotype stereotype = stereoTypeIter.next();
                if (stereotype.getName()
                        .equals(XsdStereotypeUtils.XSD_BASED_PRIMITIVETYPE)) {
                    Object value =
                            classifier
                                    .getValue(stereotype,
                                            XsdStereotypeUtils.XSD_PROPERTY_IS_ANON_UNION_SIMPLE_TYPE);
                    if (value != null) {
                        return Boolean.valueOf(value.toString());
                    }
                }
            }
        }
        return isAnonUnionSimpleType;
    }

    /**
     * @param class
     * @return
     */
    public static boolean isAnonymousComplexType(Class tmpCls) {
        boolean isAnonymousComplexType = false;
        if (tmpCls != null) {
            Iterator<Stereotype> stereoTypeIter =
                    tmpCls.getAppliedStereotypes().iterator();
            while (stereoTypeIter.hasNext()) {
                Stereotype stereotype = stereoTypeIter.next();
                if (stereotype.getName()
                        .equals(XsdStereotypeUtils.XSD_BASED_CLASS)) {
                    Object value =
                            tmpCls.getValue(stereotype,
                                    XsdStereotypeUtils.XSD_PROPERTY_IS_ANON_TYPE);
                    if (value != null) {
                        return Boolean.valueOf(value.toString());
                    }
                }
            }
        }
        return isAnonymousComplexType;
    }

    /**
     * @param umlCls
     * @param explicitGroupPosition
     * @return
     */
    public static int getMinOccursForExplicitGroup(Class umlCls,
            String explicitGroupPosition) {
        Iterator<Stereotype> stereoTypeIter =
                umlCls.getAppliedStereotypes().iterator();
        int minOccurs = 1;
        while (stereoTypeIter.hasNext()) {
            Stereotype stereotype = stereoTypeIter.next();
            if (stereotype.getName().equals(XsdStereotypeUtils.XSD_BASED_CLASS)) {
                Object xsdSequences =
                        umlCls.getValue(stereotype,
                                XsdStereotypeUtils.XSD_PROPERTY_SEQUENCE_LIST);
                if (xsdSequences != null && xsdSequences instanceof EList) {
                    Iterator<EObject> iter = ((EList) xsdSequences).iterator();
                    int index = 0;
                    while (iter.hasNext()) {
                        EObject xsdSequenceEObject = iter.next();
                        XSDSequenceWrapper sequenceWrapper =
                                new XSDSequenceWrapper(xsdSequenceEObject);
                        if (index == new Integer(explicitGroupPosition)
                                .intValue()) {
                            return sequenceWrapper.getMinOccurs();
                        }
                        index++;
                    }
                }
            }
        }
        return minOccurs;
    }

    /**
     * @param umlCls
     * @param explicitGroupPosition
     * @return
     */
    public static int getMaxOccursForExplicitGroup(Class umlCls,
            String explicitGroupPosition) {
        Iterator<Stereotype> stereoTypeIter =
                umlCls.getAppliedStereotypes().iterator();
        int maxOccurs = 1;
        while (stereoTypeIter.hasNext()) {
            Stereotype stereotype = stereoTypeIter.next();
            if (stereotype.getName().equals(XsdStereotypeUtils.XSD_BASED_CLASS)) {
                Object xsdSequences =
                        umlCls.getValue(stereotype,
                                XsdStereotypeUtils.XSD_PROPERTY_SEQUENCE_LIST);
                if (xsdSequences != null && xsdSequences instanceof EList) {
                    Iterator<EObject> iter = ((EList) xsdSequences).iterator();
                    int index = 0;
                    while (iter.hasNext()) {
                        EObject xsdSequenceEObject = iter.next();
                        XSDSequenceWrapper sequenceWrapper =
                                new XSDSequenceWrapper(xsdSequenceEObject);
                        if (index == new Integer(explicitGroupPosition)
                                .intValue()) {
                            return sequenceWrapper.getMaxOccurs();
                        }
                        index++;
                    }
                }
            }
        }
        return maxOccurs;
    }

    /**
     * Fetches the actual EObject representation of the XSD Sequence wrapper
     * class
     * 
     * @param property
     * @return
     */
    public static Object getXSDSequenceForProperty(Property property) {
        Object xsdSequence = null;
        Iterator<Stereotype> stereoTypeIter =
                property.getAppliedStereotypes().iterator();
        while (stereoTypeIter.hasNext()) {
            Stereotype stereotype = stereoTypeIter.next();
            if (stereotype.getName()
                    .equals(XsdStereotypeUtils.XSD_BASED_PROPERTY)) {
                xsdSequence =
                        property.getValue(stereotype,
                                XsdStereotypeUtils.XSD_PROPERTY_PARENT_SEQUENCE);
                break;
            }
        }
        return xsdSequence;
    }

    /**
     * Returns the depth of explicit group
     * 
     * @param xsdSequence
     * @return
     */
    public static int getDepth(Object xsdSequence) {
        return new XSDSequenceWrapper((EObject) xsdSequence).getDepth();
    }

    /**
     * Returns the position relevant to all other explicit groups
     * 
     * @param xsdSequence
     * @return
     */
    public static int getExplicitGroupPosition(Object xsdSequence) {
        return new XSDSequenceWrapper((EObject) xsdSequence)
                .getExplicitGroupPosition();
    }

    /**
     * Returns the position relevant to all other elements in the parent
     * explicit group
     * 
     * @param xsdSequence
     * @return
     */
    public static int getElementPosition(Object xsdSequence) {
        return new XSDSequenceWrapper((EObject) xsdSequence)
                .getElementPosition();
    }

    /**
     * Determines whether this is a choice or not
     * 
     * @param xsdSequence
     * @return
     */
    public static boolean isChoice(Object xsdSequence) {
        return new XSDSequenceWrapper((EObject) xsdSequence).isChoice();
    }

    /**
     * Determines whether this is an all or not
     * 
     * @param xsdSequence
     * @return
     */
    public static boolean isAll(Object xsdSequence) {
        return new XSDSequenceWrapper((EObject) xsdSequence).isAll();
    }

    /**
     * Gets the parent xsd sequence EObject representation of the wrapper class
     * 
     * @param xsdSequence
     * @return
     */
    public static Object getParentSequence(Object xsdSequence) {
        return new XSDSequenceWrapper((EObject) xsdSequence).getParent();
    }

    /**
     * Gets the min occurs value for this explicit group
     * 
     * @param xsdSequence
     * @return
     */
    public static Object getSequenceMinOccurs(Object xsdSequence) {
        return new XSDSequenceWrapper((EObject) xsdSequence).getMinOccurs();
    }

    /**
     * Gets the max occurs value for this explicit group
     * 
     * @param xsdSequence
     * @return
     */
    public static Object getSequenceMaxOccurs(Object xsdSequence) {
        return new XSDSequenceWrapper((EObject) xsdSequence).getMaxOccurs();
    }

    /**
     * Gets the name of this wrapper class if needed at some point
     * 
     * @param xsdSequence
     * @return
     */
    public static String getSeqName(Object xsdSequence) {
        return new XSDSequenceWrapper((EObject) xsdSequence).getName();
    }

    /**
     * Returns all the xsd sequence EObjects that are at a particular depth
     * 
     * @param umlCls
     * @param depth
     * @return
     */
    public static List<Object> getXSDSequencesAtDepth(Class umlCls, Object depth) {
        List<Object> tempSequences = new ArrayList<Object>();
        Iterator<Stereotype> stereoTypeIter =
                umlCls.getAppliedStereotypes().iterator();
        while (stereoTypeIter.hasNext()) {
            Stereotype stereotype = stereoTypeIter.next();
            if (stereotype.getName().equals(XsdStereotypeUtils.XSD_BASED_CLASS)) {
                Object xsdSequences =
                        umlCls.getValue(stereotype,
                                XsdStereotypeUtils.XSD_PROPERTY_SEQUENCE_LIST);
                if (xsdSequences != null && xsdSequences instanceof EList) {
                    Iterator<EObject> iter = ((EList) xsdSequences).iterator();
                    while (iter.hasNext()) {
                        EObject xsdSequenceEObject = iter.next();
                        XSDSequenceWrapper sequenceWrapper =
                                new XSDSequenceWrapper(xsdSequenceEObject);
                        int currentDepth = sequenceWrapper.getDepth();
                        if (new Integer("" + depth).intValue() == currentDepth) {
                            tempSequences.add(xsdSequenceEObject);
                        }
                    }
                }
            }
        }
        return tempSequences;
    }

    /**
     * Fetches all the xsd sequence EObjects that have the same parent as the
     * passed in parentSeq value
     * 
     * @param umlCls
     * @param parentSeq
     * @return
     */
    public static List<Object> getAllSequencesWithParent(Class umlCls,
            Object parentSeq) {
        List<Object> tempSequences = new ArrayList<Object>();
        Iterator<Stereotype> stereoTypeIter =
                umlCls.getAppliedStereotypes().iterator();
        while (stereoTypeIter.hasNext()) {
            Stereotype stereotype = stereoTypeIter.next();
            if (stereotype.getName().equals(XsdStereotypeUtils.XSD_BASED_CLASS)) {
                Object xsdSequences =
                        umlCls.getValue(stereotype,
                                XsdStereotypeUtils.XSD_PROPERTY_SEQUENCE_LIST);
                if (xsdSequences != null && xsdSequences instanceof EList) {
                    Iterator<EObject> iter = ((EList) xsdSequences).iterator();
                    while (iter.hasNext()) {
                        EObject xsdSequenceEObject = iter.next();
                        XSDSequenceWrapper sequenceWrapper =
                                new XSDSequenceWrapper(xsdSequenceEObject);
                        if (sequenceWrapper.getParent() != null
                                && sequenceWrapper.getParent()
                                        .equals(parentSeq)) {
                            tempSequences.add(xsdSequenceEObject);
                        }
                    }
                }
            }
        }
        return tempSequences;
    }

    /**
     * Returns the element position to insert the explicit group at in the
     * parent container
     * 
     * @param data
     * @param parentCls
     * @param property
     * @param propertySequence
     * @param attributes
     * @return
     */
    public static Object getPropertyPosition(ExportTransformationData data,
            Class parentCls, Property property, Object propertySequence,
            List<Property> attributes) {
        int position = 1;
        List<Object> allSequencesWithParent =
                getAllSequencesWithParent(parentCls, propertySequence);
        List<Property> attributesWithSameParent = new ArrayList<Property>();

        for (Property tmpProperty : attributes) {
            Object sequenceForProperty = getXSDSequenceForProperty(tmpProperty);
            if (sequenceForProperty != null
                    && sequenceForProperty.equals(propertySequence)) {
                attributesWithSameParent.add(tmpProperty);
            }
        }

        Object[] tmpArr =
                new Object[(attributesWithSameParent.size() + allSequencesWithParent
                        .size())];
        for (Object xsdSeq : allSequencesWithParent) {
            int xsdSequencePos = getElementPosition(xsdSeq);
            int tmpSlot = xsdSequencePos - 1;
            if (tmpSlot > tmpArr.length) {
                int difference = xsdSequencePos - tmpArr.length;
                tmpSlot = tmpArr.length - difference;
                if (tmpSlot < 0) {
                    return position;
                }
                if (tmpArr[tmpSlot] != null)
                    tmpSlot = tmpArr.length - 1;
            }
            if (tmpSlot - tmpArr.length == 0)
                tmpSlot--;
            tmpArr[tmpSlot] = xsdSeq;
        }
        for (Property tmpProperty : attributesWithSameParent) {
            for (int i = 0; i < tmpArr.length; i++) {
                if (tmpArr[i] == null) {
                    tmpArr[i] = tmpProperty;
                    break;
                }
            }
        }
        for (int i = 0; i < tmpArr.length; i++) {
            Object tmpContents = tmpArr[i];
            if (tmpContents.equals(property)) {
                position = i + 1;
            }
        }
        return position;
    }

    /***
     * 
     * Return enumeration if the property type is enumeration
     * 
     * @param elemRefEnum
     * @return
     */
    public static Enumeration _getEnumerationFromProperty(Property elemRefEnum) {
        if (null != elemRefEnum && elemRefEnum.getType() instanceof Enumeration) {
            Enumeration enumeration = (Enumeration) elemRefEnum.getType();
            return enumeration;
        }
        return null;
    }

    /**
     * 
     * returns the base class if the given property is from a restricted class
     * 
     * @param property
     * @return
     */
    public static Class _getBaseClassForRestrictedProperty(Property property) {
        return XSDUtil.getBaseClassForRestrictedProperty(property);
    }

    /**
     * 
     * @param element
     * @param propObj
     * @return <code>true</code> if the element is participating in composition
     *         association <code>false</code> otherwise
     */
    public static boolean _toAddAssocElement(Object element, Object propObj) {

        String propName = null;
        if (element instanceof DynamicEObjectImpl) {

            DynamicEObjectImpl eo = (DynamicEObjectImpl) element;

            EStructuralFeature feature =
                    eo.eClass().getEStructuralFeature("name"); //$NON-NLS-1$

            if (feature != null) {
                Object value = eo.eGet(feature);
                propName = value.toString();
            }
        }

        if (propObj instanceof Property) {

            Property property = (Property) propObj;
            if (null != property.getAssociation()) {

                Association association = property.getAssociation();
                List<Property> memberEnds = association.getMemberEnds();

                for (Property memProperty : memberEnds) {

                    if (null != propName
                            && propName.equals(memProperty.getName())) {

                        if (memProperty.getAggregation() == AggregationKind.COMPOSITE_LITERAL) {

                            return true;
                        }
                    }
                }

            }
        }
        return false;
    }

    /**
     * XPD-5906: A property that participates in bidirectional composition if
     * has unbounded max occurs, then getPropertyMaxOccurs in HelperFuncs
     * returns AllNniMember::unbounded xml schema enumeration type which does
     * not satisfy the parseIncrementalComposition() which expects Integer
     * 
     * @param data
     * @param upperBound
     * @return <code>Integer</code> equivalent for upper bound value if it is
     *         not already an integer
     */
    public static Integer _getIntegerUpperBound(ExportTransformationData data,
            Object upperBound) {

        if (upperBound instanceof EEnumLiteralImpl) {

            EEnumLiteralImpl eEnumLiteralImpl = (EEnumLiteralImpl) upperBound;
            if ("unbounded".equals(eEnumLiteralImpl.getName())) { //$NON-NLS-1$

                return -1;
            }

        }
        return new Integer(upperBound.toString());
    }
}
