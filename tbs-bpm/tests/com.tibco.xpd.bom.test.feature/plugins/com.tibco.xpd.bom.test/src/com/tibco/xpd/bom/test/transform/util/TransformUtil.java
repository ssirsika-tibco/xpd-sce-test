package com.tibco.xpd.bom.test.transform.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.Assert;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.eclipse.wst.wsdl.internal.util.WSDLResourceFactoryImpl;
import org.eclipse.wst.wsdl.util.WSDLResourceImpl;
import org.openarchitectureware.workflow.issues.Issues;

import com.tibco.xpd.bom.resources.utils.UML2ModelUtil;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.xsdtransform.XsdStereotypeUtils;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.explicit.Validator;
import com.tibco.xpd.validation.provider.IIssue;
import com.tibco.xpd.validation.provider.ValidationException;

/**
 * @author glewis
 * 
 */
public class TransformUtil {
    public static Class assertPackagedElementClass(
            EList<? extends PackageableElement> packagedElements, String name)
            throws Exception {
        Class tmpClass = null;
        for (PackageableElement elem : packagedElements) {
            if (elem.getName().equals(name)) {
                Assert.assertTrue("Packaged element is null or not of type Class", elem instanceof Class); //$NON-NLS-1$
                tmpClass = (Class) elem;
                break;
            }
        }

        Assert.assertNotNull("Class name " + name + " not found", tmpClass); //$NON-NLS-1$  //$NON-NLS-2$

        return tmpClass;
    }

    /**
     * @param packagedElements
     * @param name
     * @return
     * @throws Exception
     */
    public static Enumeration assertPackagedElementEnumeration(
            EList<? extends PackageableElement> packagedElements, String name)
            throws Exception {
        Enumeration tmpEnumeration = null;
        for (PackageableElement elem : packagedElements) {
            if (elem.getName().equals(name)) {
                Assert.assertTrue("Packaged element is null or not of type Enumeration", elem instanceof Enumeration); //$NON-NLS-1$
                tmpEnumeration = (Enumeration) elem;
                break;
            }
        }

        Assert.assertNotNull("Enumeration name " + name + " not found", tmpEnumeration); //$NON-NLS-1$  //$NON-NLS-2$

        return tmpEnumeration;
    }

    /**
     * @param packagedElements
     * @param name
     * @return
     * @throws Exception
     */
    public static Enumeration assertPackagedElementEnumeration(
            EList<PackageableElement> packagedElements, String name,
            ArrayList<Enumeration> ignoreEnumeratonsList) throws Exception {
        Enumeration tmpEnumeration = null;
        for (PackageableElement elem : packagedElements) {
            if (elem.getName().equals(name)) {
                Assert.assertTrue("Packaged element is null or not of type Enumeration", elem instanceof Enumeration); //$NON-NLS-1$                
                if (!ignoreEnumeratonsList.contains(elem)) {
                    tmpEnumeration = (Enumeration) elem;
                    break;
                }
            }
        }

        Assert.assertNotNull("Enumeration name " + name + " not found", tmpEnumeration); //$NON-NLS-1$  //$NON-NLS-2$

        return tmpEnumeration;
    }

    /**
     * @param packagedElements
     * @param name
     * @return
     * @throws Exception
     */
    public static PrimitiveType assertPackagedElementPrimitiveType(
            EList<? extends PackageableElement> packagedElements, String name)
            throws Exception {
        PrimitiveType tmpPrimitiveType = null;
        for (PackageableElement elem : packagedElements) {
            if (elem.getName().equals(name)) {
                Assert.assertTrue("Packaged element is null or not of type PrimitiveType", elem instanceof PrimitiveType); //$NON-NLS-1$
                tmpPrimitiveType = (PrimitiveType) elem;
                break;
            }
        }

        Assert.assertNotNull("PrimitiveType name " + name + " not found", tmpPrimitiveType); //$NON-NLS-1$  //$NON-NLS-2$

        return tmpPrimitiveType;
    }

    /**
     * @param packagedElements
     * @param name
     * @return
     * @throws Exception
     */
    public static org.eclipse.uml2.uml.Package assertPackagedElementPackage(
            EList<PackageableElement> packagedElements, String name)
            throws Exception {
        org.eclipse.uml2.uml.Package tmpPackage = null;
        for (PackageableElement elem : packagedElements) {
            if (elem.getName().equals(name)) {
                Assert.assertTrue("Packaged element is null or not of type Package", elem instanceof org.eclipse.uml2.uml.Package); //$NON-NLS-1$
                tmpPackage = (org.eclipse.uml2.uml.Package) elem;
                break;
            }
        }

        Assert.assertNotNull("Package name " + name + " not found", tmpPackage); //$NON-NLS-1$  //$NON-NLS-2$

        return tmpPackage;
    }

    /**
     * @param allAttributes
     * @param name
     * @param pattern
     * @throws Exception
     */
    public static void assertPropertyPattern(EList<Property> allAttributes,
            String name, String pattern) throws Exception {
        Property property = null;
        for (Property prop : allAttributes) {
            if (prop.getName().equals(name) && prop.getType() != null) {
                property = prop;
                break;
            }
        }

        Assert.assertNotNull("Property name " + name + " not found", property); //$NON-NLS-1$  //$NON-NLS-2$

        if (property != null) {
            String facetPropertyValue =
                    (String) PrimitivesUtil
                            .getFacetPropertyValue((PrimitiveType) property
                                    .getType(), "textPatternValue", property); //$NON-NLS-1$      
            Assert.assertEquals("Pattern is not correct for this type", pattern, facetPropertyValue); //$NON-NLS-1$      
        }
    }

    /**
     * @param allAttributes
     * @param name
     * @param intLower
     * @throws Exception
     */
    public static void assertPropertyIntegerLower(
            EList<Property> allAttributes, String name, String intLower)
            throws Exception {
        Property property = null;
        for (Property prop : allAttributes) {
            if (prop.getName().equals(name) && prop.getType() != null) {
                property = prop;
                break;
            }
        }

        Assert.assertNotNull("Property name " + name + " not found", property); //$NON-NLS-1$  //$NON-NLS-2$

        if (property != null) {
            String facetPropertyValue =
                    (String) PrimitivesUtil
                            .getFacetPropertyValue((PrimitiveType) property
                                    .getType(), "integerLower", property); //$NON-NLS-1$      
            Assert.assertEquals("Lower integer value is not correct for this type", intLower, facetPropertyValue); //$NON-NLS-1$      
        }
    }

    /**
     * @param allAttributes
     * @param name
     * @param intUpper
     * @throws Exception
     */
    public static void assertPropertyIntegerUpper(
            EList<Property> allAttributes, String name, String intUpper)
            throws Exception {
        Property property = null;
        for (Property prop : allAttributes) {
            if (prop.getName().equals(name) && prop.getType() != null) {
                property = prop;
                break;
            }
        }

        Assert.assertNotNull("Property name " + name + " not found", property); //$NON-NLS-1$  //$NON-NLS-2$

        if (property != null) {
            String facetPropertyValue =
                    (String) PrimitivesUtil
                            .getFacetPropertyValue((PrimitiveType) property
                                    .getType(), "integerUpper", property); //$NON-NLS-1$      
            Assert.assertEquals("Upper integer value is not correct for this type", intUpper, facetPropertyValue); //$NON-NLS-1$      
        }
    }

    /**
     * @param allAttributes
     * @param name
     * @param decLower
     * @throws Exception
     */
    public static void assertPropertyDecimalLower(
            EList<Property> allAttributes, String name, String decLower)
            throws Exception {
        Property property = null;
        for (Property prop : allAttributes) {
            if (prop.getName().equals(name) && prop.getType() != null) {
                property = prop;
                break;
            }
        }

        Assert.assertNotNull("Property name " + name + " not found", property); //$NON-NLS-1$  //$NON-NLS-2$

        if (property != null) {
            String facetPropertyValue =
                    (String) PrimitivesUtil
                            .getFacetPropertyValue((PrimitiveType) property
                                    .getType(), "decimalLower", property); //$NON-NLS-1$      
            Assert.assertEquals("Lower decimal value is not correct for this type", decLower, facetPropertyValue); //$NON-NLS-1$      
        }
    }

    /**
     * @param allAttributes
     * @param name
     * @param decUpper
     * @throws Exception
     */
    public static void assertPropertyDecimalUpper(
            EList<Property> allAttributes, String name, String decUpper)
            throws Exception {
        Property property = null;
        for (Property prop : allAttributes) {
            if (prop.getName().equals(name) && prop.getType() != null) {
                property = prop;
                break;
            }
        }

        Assert.assertNotNull("Property name " + name + " not found", property); //$NON-NLS-1$  //$NON-NLS-2$

        if (property != null) {
            String facetPropertyValue =
                    (String) PrimitivesUtil
                            .getFacetPropertyValue((PrimitiveType) property
                                    .getType(), "decimalUpper", property); //$NON-NLS-1$      
            Assert.assertEquals("Upper decimal value is not correct for this type", decUpper, facetPropertyValue); //$NON-NLS-1$      
        }
    }

    /**
     * @param allAttributes
     * @param name
     * @param decUpper
     * @throws Exception
     */
    public static void assertIntegerLengthNotSet(EList<Property> allAttributes,
            String name) throws Exception {
        Property property = null;
        for (Property prop : allAttributes) {
            if (prop.getName().equals(name) && prop.getType() != null) {
                property = prop;
                break;
            }
        }

        Assert.assertNotNull("Property name " + name + " not found", property); //$NON-NLS-1$  //$NON-NLS-2$

        if (property != null) {
            Object facetPropertyValue =
                    PrimitivesUtil
                            .getFacetPropertyValue((PrimitiveType) property
                                    .getType(), "integerLength", property); //$NON-NLS-1$
            int h = 0;
            //Assert.assertEquals("Upper decimal value is not correct for this type", decUpper, facetPropertyValue);  //$NON-NLS-1$      
        }
    }

    /**
     * 
     * Given a list of Properties, throws an Exception on encountering the first
     * occurence of a duplicate name.
     * 
     * @param props
     * @throws Exception
     */
    private static void checkUniquePropertyNames(List<Property> props)
            throws Exception {

        // Gather all the names in a set
        Set<String> names = new HashSet<String>();

        for (Property prop : props) {
            if (names.contains(prop.getName())) {
                throw new Exception("Duplicate Property name found");
            } else {
                names.add(prop.getName());
            }
        }
    }

    /**
     * Checks that a Property exists within a Class with the following criteria:
     * <ul>
     * <li>name</li>
     * <li>type</li>
     * </ul>
     * where type is specified by a PrimitiveType.
     * 
     * @param clazz
     * @param name
     * @param propType
     * @return property
     */
    public static Property assertPropertyInClass(Class clazz, String name,
            PrimitiveType propType) throws Exception {

        EList<Property> ownedAttributes = clazz.getOwnedAttributes();
        Property foundProp = null;
        checkUniquePropertyNames(ownedAttributes);

        for (Property prop : ownedAttributes) {
            if (prop.getName().equals(name)) {
                foundProp = prop;
                break;
            }
        }

        Assert.assertNotNull("Property name " + name + " not found", foundProp); //$NON-NLS-1$  //$NON-NLS-2$
        Assert.assertEquals("Property type does not match expected",
                propType,
                foundProp.getType());

        return foundProp;

    }

    /**
     * Checks that a Property exists within a Class with the following criteria:
     * <ul>
     * <li>name</li>
     * <li>type</li>
     * </ul>
     * 
     * where type is a BOM base PrimitiveType.
     * 
     * @param clazz
     * @param name
     * @param propType
     * @return
     */
    public static Property assertPropertyInClass(Class clazz, String name,
            String propType) throws Exception {

        EList<Property> ownedAttributes = clazz.getOwnedAttributes();
        Property foundProp = null;

        // Gather all the names in a set
        Set<String> names = new HashSet<String>();

        for (Property prop : ownedAttributes) {
            if (names.contains(prop.getName())) {
                throw new Exception("Duplicate Property name found");
            } else {
                names.add(prop.getName());
            }
        }

        for (Property prop : ownedAttributes) {
            if (prop.getName().equals(name)) {
                foundProp = prop;
                break;
            }
        }

        Assert.assertNotNull("Property name " + name + " not found", foundProp); //$NON-NLS-1$  //$NON-NLS-2$

        Type tmpType =
                PrimitivesUtil.getStandardPrimitiveTypeByName(foundProp
                        .eResource().getResourceSet(), propType);

        if (tmpType != null) {
            Assert.assertEquals("Property Type not found", tmpType, foundProp.getType()); //$NON-NLS-1$
        } else {
            throw new Exception("Unrecognized Primitive Type name");
        }

        return foundProp;

    }

    /**
     * @param allAttributes
     * @param name
     * @param attPrimType
     * @return
     * @throws Exception
     */
    public static Property assertAttributeInClass(
            EList<Property> allAttributes, String name, String attPrimType)
            throws Exception {
        Property property = null;
        for (Property prop : allAttributes) {
            if (prop.getName().equals(name) && prop.getType() != null) {
                property = prop;
                break;
            }
        }

        Assert.assertNotNull("Property name " + name + " not found", property); //$NON-NLS-1$  //$NON-NLS-2$

        if (attPrimType != null) {
            Type tmpType =
                    PrimitivesUtil.getStandardPrimitiveTypeByName(property
                            .eResource().getResourceSet(), attPrimType);
            if (tmpType != null) {
                Assert.assertEquals("Property Type " + attPrimType, tmpType, property.getType()); //$NON-NLS-1$
            } else if (!property.getType().eIsProxy()) {
                Assert.assertEquals("Property Type " + attPrimType, attPrimType, property.getType().getName()); //$NON-NLS-1$
            }
        }

        return property;
    }

    /**
     * @param tmpClass
     * @param numAtts
     */
    public static void assertAttributeSize(Class tmpClass, int numAtts) {
        EList<Property> attributes = tmpClass.getAllAttributes();
        Assert.assertEquals("Number of attributes elements in class", numAtts, attributes.size()); //$NON-NLS-1$
    }

    /**
     * @param tmpClass
     * @param numAtts
     */
    public static void assertOperationSize(Class tmpClass, int numAtts) {
        EList<Operation> operations = tmpClass.getAllOperations();
        Assert.assertEquals("Number of operations elements in class", numAtts, operations.size()); //$NON-NLS-1$
    }

    /**
     * @param tmpOperation
     * @param numAtts
     */
    public static void assertOperationParameterSize(Operation tmpOperation,
            int numAtts) {
        EList<Parameter> parameters = tmpOperation.getOwnedParameters();
        Assert.assertEquals("Number of parameters elements in class", numAtts, parameters.size()); //$NON-NLS-1$
    }

    /**
     * @param allOperation
     * @param name
     * @param operationOutputType
     * @return
     * @throws Exception
     */
    public static Operation assertOperationInClass(
            EList<Operation> allOperation, String name, Type operationOutputType)
            throws Exception {
        Operation operation = null;
        for (Operation tempOperation : allOperation) {
            if (tempOperation.getName().equals(name)) {
                operation = tempOperation;
                break;
            }
        }

        Assert.assertNotNull("Operation name " + name + " not found", operation); //$NON-NLS-1$  //$NON-NLS-2$

        if (operationOutputType != null && !operation.getType().eIsProxy()) {
            Assert.assertEquals("Operation Output Type " + operationOutputType, operationOutputType, operation.getType()); //$NON-NLS-1$
        }

        return operation;
    }

    /**
     * @param allParamters
     * @param name
     * @param parameterType
     * @return
     * @throws Exception
     */
    public static Parameter assertParameterInOperation(
            EList<Parameter> allParamters, String name, Type parameterType)
            throws Exception {
        Parameter parameter = null;
        for (Parameter tempParameter : allParamters) {
            if (tempParameter.getName().equals(name)) {
                parameter = tempParameter;
                break;
            }
        }

        Assert.assertNotNull("Parameter name " + name + " not found", parameter); //$NON-NLS-1$  //$NON-NLS-2$

        if (parameterType != null) {
            Assert.assertEquals("Operation Output Type " + parameterType.getName(), parameterType, parameter.getType()); //$NON-NLS-1$
        }

        return parameter;
    }

    /**
     * @param tmpClass
     * @param numAtts
     */
    public static void assertCompositionSize(Class tmpClass, int numAtts) {
        EList<Association> associations = tmpClass.getAssociations();
        Assert.assertEquals("Number of associations for " + tmpClass.getName() + " Class", numAtts, associations.size()); //$NON-NLS-1$
    }

    public static void assertIsComposition(Association association,
            Class endClass) {
        AggregationKind kind = UML2ModelUtil.getAggregationType(association);
        Assert.assertEquals("Aggregation Kind is Composite", AggregationKind.COMPOSITE_LITERAL, kind); //$NON-NLS-1$
    }

    /**
     * @param tmpClassifier
     * @param numAtts
     */
    public static void assertGeneralizationSize(Classifier tmpClassifier,
            int numAtts) {
        EList<Generalization> generalizations =
                tmpClassifier.getGeneralizations();
        Assert.assertEquals("Number of generalizations in " + tmpClassifier.getName() + " Class", numAtts, generalizations.size()); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * @param generalization
     * @param endClassifier
     */
    public static void assertGeneralization(Generalization generalization,
            Classifier endClassifier) {
        Assert.assertEquals("Generalizes " + endClassifier.getName(), generalization.getGeneral(), endClassifier); //$NON-NLS-1$ 
    }

    /**
     * @param generalization
     * @param endType
     */
    public static void assertGeneralization(Generalization generalization,
            Type endType) {
        Assert.assertEquals("Generalizes " + endType.getName(), generalization.getGeneral(), endType); //$NON-NLS-1$ 
    }

    /**
     * @param tmpEnumeration
     * @param numLiterals
     */
    public static void assertEnumLiteralCount(Enumeration tmpEnumeration,
            int numLiterals) {
        EList<EnumerationLiteral> literals = tmpEnumeration.getOwnedLiterals();
        Assert.assertEquals("Number of enum literals elements in class", numLiterals, literals.size()); //$NON-NLS-1$
    }

    /**
     * @param literals
     * @param name
     * @return
     * @throws Exception
     */
    public static EnumerationLiteral assertEnumLiteralInEnum(
            EList<EnumerationLiteral> literals, String name) throws Exception {
        EnumerationLiteral literal = null;
        for (EnumerationLiteral tempLiteral : literals) {
            if (tempLiteral.getName().equals(name)) {
                literal = tempLiteral;
                break;
            }
        }

        Assert.assertNotNull("Literal name " + name + " not found", literal); //$NON-NLS-1$  //$NON-NLS-2$

        return literal;
    }

    /**
     * @param allAttributes
     * @param name
     * @param subtype
     * @param subTypeName
     * @throws Exception
     */
    public static void assertPropertySubtype(EList<Property> allAttributes,
            String name, String subtype, String subTypeName) throws Exception {
        Property property = null;
        for (Property prop : allAttributes) {
            if (prop.getName().equals(name) && prop.getType() != null) {
                property = prop;
                break;
            }
        }

        if (property != null) {
            Object obj =
                    PrimitivesUtil
                            .getFacetPropertyValue((PrimitiveType) property
                                    .getType(), subtype, property); //$NON-NLS-1$
            if (obj instanceof EnumerationLiteral) {
                EnumerationLiteral enumerationLiteral =
                        (EnumerationLiteral) obj;
                Assert.assertEquals(enumerationLiteral.getName(), subTypeName);
            }
        }
    }

    /**
     * @param destIds
     * @param modelFile
     * @param severityLevel
     * @return
     * @throws ValidationException
     */
    public static boolean isMarkerSeverityExist(String[] destIds,
            IResource modelFile, int severityLevel) throws ValidationException {
        Collection<com.tibco.xpd.validation.destinations.Destination> destinations =
                new ArrayList<com.tibco.xpd.validation.destinations.Destination>();
        for (String destId : destIds) {
            com.tibco.xpd.validation.destinations.Destination destination =
                    ValidationActivator.getDefault().getDestination(destId);
            if (destination != null) {
                destinations.add(destination);
            }
        }
        Validator validator = new Validator(destinations);
        Collection<IIssue> issues = validator.validate(modelFile);
        boolean hasErrorMarker = false;
        for (IIssue issue : issues) {
            int markerSeverity = issue.getSeverity();
            if (markerSeverity == severityLevel) {
                hasErrorMarker = true;
                break;
            }
        }
        return hasErrorMarker;
    }

    /**
     * @param destIds
     * @param modelFile
     * @param severityLevel
     * @return
     * @throws ValidationException
     */
    public static int getMarkerSeverityCount(String[] destIds,
            IResource modelFile, int severityLevel) throws ValidationException {
        Collection<com.tibco.xpd.validation.destinations.Destination> destinations =
                new ArrayList<com.tibco.xpd.validation.destinations.Destination>();
        for (String destId : destIds) {
            com.tibco.xpd.validation.destinations.Destination destination =
                    ValidationActivator.getDefault().getDestination(destId);
            if (destination != null) {
                destinations.add(destination);
            }
        }
        Validator validator = new Validator(destinations);
        Collection<IIssue> issues = validator.validate(modelFile);
        int count = 0;
        for (IIssue issue : issues) {
            int markerSeverity = issue.getSeverity();
            if (markerSeverity == severityLevel) {
                count++;
            }
        }
        return count;
    }

    /**
     * 
     * @param destIds
     *            Specific destination to look for or <code>null</code> if
     *            marker with text from ANY destination should be counted.
     * @param modelFile
     * @param markerMatchesPattern
     * 
     * @return number of problem markers raised that match the given pattern.
     */
    public static int countMarkersMatchingPattern(String[] destIds,
            IResource modelFile, String markerMatchesPattern) {
        /*
         * Sid: Change this method to use already created markers freom build
         * validation
         */
        int count = 0;

        List<IMarker> errorMarkers = TestUtil.getErrorMarkers(modelFile);

        for (IMarker errorMarker : errorMarkers) {
            String errMsg = errorMarker.getAttribute(IMarker.MESSAGE, "");

            if (errMsg != null) {
                if (errMsg.matches(markerMatchesPattern)) {
                    boolean countIt = true;

                    /*
                     * If provided with dest id's then only count if appropriate
                     * dest id in marker.
                     */
                    if (destIds != null && destIds.length > 0) {
                        String markerDestId =
                                errorMarker.getAttribute(IIssue.DESTINATION_ID,
                                        "");
                        if (markerDestId != null && markerDestId.length() > 0) {
                            /* Only count if dest is one we're looking for. */
                            countIt = false;
                            for (String destId : destIds) {
                                if (markerDestId.equals(destId)) {
                                    countIt = true;
                                    break;
                                }
                            }
                        }
                    }

                    count++;
                }

            }

        }

        return count;
    }

    public static void assertTargetNamespace(Model model,
            String expectedTargetNamespace) {
        Stereotype stereotype = null;
        Iterator<Profile> profilesIter = model.getAppliedProfiles().iterator();
        boolean found = false;
        while (profilesIter.hasNext()) {
            Profile profile = profilesIter.next();
            if (profile.getName()
                    .equals(XsdStereotypeUtils.XSD_NOTATION_PROFILE_NAME)) {
                Iterator<Stereotype> ownedStereotypesIter =
                        profile.getOwnedStereotypes().iterator();
                while (ownedStereotypesIter.hasNext()) {
                    stereotype = ownedStereotypesIter.next();
                    if (stereotype.getName()
                            .equals(XsdStereotypeUtils.XSD_BASED_MODEL)) {
                        Object targetNamespace =
                                model.getValue(stereotype,
                                        XsdStereotypeUtils.XSD_TARGET_NAMESPACE);
                        found = true;
                        Assert.assertEquals("Invalid Target Namespace", expectedTargetNamespace, targetNamespace);//$NON-NLS-1$                    	
                    }
                }
            }
        }
        Assert.assertEquals("Target Namespace not found", true, found);//$NON-NLS-1$
    }

    public static void assertXSDBasedEnumerationLiteral(Model model,
            EnumerationLiteral enumLiteral, Object expectedValue,
            String propertyName) {
        Stereotype stereotype = null;
        Iterator<Profile> profilesIter = model.getAppliedProfiles().iterator();
        boolean found = false;
        while (profilesIter.hasNext()) {
            Profile profile = profilesIter.next();
            if (profile.getName()
                    .equals(XsdStereotypeUtils.XSD_NOTATION_PROFILE_NAME)) {
                Iterator<Stereotype> ownedStereotypesIter =
                        profile.getOwnedStereotypes().iterator();
                while (ownedStereotypesIter.hasNext()) {
                    stereotype = ownedStereotypesIter.next();
                    if (stereotype
                            .getName()
                            .equals(XsdStereotypeUtils.XSD_BASED_ENUMERATION_LITERAL)) {
                        Object value =
                                enumLiteral.getValue(stereotype, propertyName);
                        found = true;
                        Assert.assertEquals("Invalid Value", expectedValue, value);//$NON-NLS-1$                      
                    }
                }
            }
        }
        Assert.assertEquals("XSD Based Enumeration Literal not found for " + propertyName, true, found);//$NON-NLS-1$
    }

    public static void assertXSDBasedPropertyValue(Model model,
            Property property, Object expectedValue, String propertyName) {
        Stereotype stereotype = null;
        Iterator<Profile> profilesIter = model.getAppliedProfiles().iterator();
        boolean found = false;
        while (profilesIter.hasNext()) {
            Profile profile = profilesIter.next();
            if (profile.getName()
                    .equals(XsdStereotypeUtils.XSD_NOTATION_PROFILE_NAME)) {
                Iterator<Stereotype> ownedStereotypesIter =
                        profile.getOwnedStereotypes().iterator();
                while (ownedStereotypesIter.hasNext()) {
                    stereotype = ownedStereotypesIter.next();
                    if (stereotype.getName()
                            .equals(XsdStereotypeUtils.XSD_BASED_PROPERTY)) {
                        Object value =
                                property.getValue(stereotype, propertyName);
                        found = true;
                        Assert.assertEquals("Invalid Value", expectedValue, value);//$NON-NLS-1$                    	
                    }
                }
            }
        }
        Assert.assertEquals("XSD Based Property not found for " + propertyName, true, found);//$NON-NLS-1$
    }

    public static void assertXSDBasedRestrictionValue(Model model,
            Classifier classifier, Object expectedValue, String propertyName) {
        Stereotype stereotype = null;
        Iterator<Profile> profilesIter = model.getAppliedProfiles().iterator();
        boolean found = false;
        while (profilesIter.hasNext()) {
            Profile profile = profilesIter.next();
            if (profile.getName()
                    .equals(XsdStereotypeUtils.XSD_NOTATION_PROFILE_NAME)) {
                Iterator<Stereotype> ownedStereotypesIter =
                        profile.getOwnedStereotypes().iterator();
                while (ownedStereotypesIter.hasNext()) {
                    stereotype = ownedStereotypesIter.next();
                    if (stereotype.getName()
                            .equals(XsdStereotypeUtils.XSD_BASED_RESTRICTION)) {
                        Object value =
                                classifier.getValue(stereotype, propertyName);
                        found = true;
                        Assert.assertEquals("Invalid Value", expectedValue, value);//$NON-NLS-1$                    	
                    }
                }
            }
        }
        Assert.assertEquals("XSD Based Property not found for " + propertyName, true, found);//$NON-NLS-1$
    }

    public static void assertXSDBasedRestrictionValue(Model model,
            Property property, Object expectedValue, String propertyName) {
        Stereotype stereotype = null;
        Iterator<Profile> profilesIter = model.getAppliedProfiles().iterator();
        boolean found = false;
        while (profilesIter.hasNext()) {
            Profile profile = profilesIter.next();
            if (profile.getName()
                    .equals(XsdStereotypeUtils.XSD_NOTATION_PROFILE_NAME)) {
                Iterator<Stereotype> ownedStereotypesIter =
                        profile.getOwnedStereotypes().iterator();
                while (ownedStereotypesIter.hasNext()) {
                    stereotype = ownedStereotypesIter.next();
                    if (stereotype.getName()
                            .equals(XsdStereotypeUtils.XSD_BASED_RESTRICTION)) {
                        Object value =
                                property.getValue(stereotype, propertyName);
                        found = true;
                        Assert.assertEquals("Invalid Value", expectedValue, value);//$NON-NLS-1$                      
                    }
                }
            }
        }
        Assert.assertEquals("XSD Based Property not found for " + propertyName, true, found);//$NON-NLS-1$
    }

    public static Class getClass(EList<PackageableElement> packagedElements,
            String name) {
        for (PackageableElement elem : packagedElements) {
            if (elem.getName().equals(name) && elem instanceof Class) {
                return (Class) elem;
            }
        }
        return null;
    }

    public static void assertMaxLength(NamedElement namedElement,
            Object expectedValue) {
        if (namedElement instanceof PrimitiveType) {
            PrimitiveType tmpPrim = (PrimitiveType) namedElement;
            PrimitiveType basePrimitiveType =
                    PrimitivesUtil.getBasePrimitiveType(tmpPrim);
            if (basePrimitiveType.getName()
                    .equals(PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME)) {
                Object facetPropertyValue =
                        PrimitivesUtil.getFacetPropertyValue(tmpPrim,
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_LENGTH);
                Assert.assertEquals("Max Length is not correct for " + namedElement.getName(), expectedValue, facetPropertyValue.toString());//$NON-NLS-1$
            }
        } else if (namedElement instanceof Property) {
            Property tmpProperty = (Property) namedElement;
            if (tmpProperty.getType() != null
                    && tmpProperty.getType().getName()
                            .equals(PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME)) {
                if (tmpProperty.getType() instanceof PrimitiveType) {
                    PrimitiveType basePrimitiveType =
                            (PrimitiveType) tmpProperty.getType();
                    Object facetPropertyValue =
                            PrimitivesUtil
                                    .getFacetPropertyValue(basePrimitiveType,
                                            PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_LENGTH,
                                            tmpProperty);
                    Assert.assertEquals("Max Length is not correct for " + namedElement.getName(), expectedValue, facetPropertyValue.toString());//$NON-NLS-1$
                }
            }
        }
    }

    public static void assertIntegerLength(NamedElement namedElement,
            Object expectedValue) {
        if (namedElement instanceof PrimitiveType) {
            PrimitiveType tmpPrim = (PrimitiveType) namedElement;
            PrimitiveType basePrimitiveType =
                    PrimitivesUtil.getBasePrimitiveType(tmpPrim);
            if (basePrimitiveType.getName()
                    .equals(PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME)) {
                Object facetPropertyValue =
                        PrimitivesUtil
                                .getFacetPropertyValue(tmpPrim,
                                        PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LENGTH);
                Assert.assertEquals("Integer Length is not correct", expectedValue, facetPropertyValue.toString());//$NON-NLS-1$
            }
        } else if (namedElement instanceof Property) {
            Property tmpProperty = (Property) namedElement;
            if (tmpProperty.getType() != null
                    && tmpProperty.getType().getName()
                            .equals(PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME)) {
                if (tmpProperty.getType() instanceof PrimitiveType) {
                    PrimitiveType basePrimitiveType =
                            (PrimitiveType) tmpProperty.getType();
                    Object facetPropertyValue =
                            PrimitivesUtil
                                    .getFacetPropertyValue(basePrimitiveType,
                                            PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LENGTH,
                                            tmpProperty);
                    Assert.assertEquals("Integer Length is not correct", expectedValue, facetPropertyValue.toString());//$NON-NLS-1$
                }
            }
        }
    }

    public static void assertDecimalLength(NamedElement namedElement,
            Object expectedValue) {
        if (namedElement instanceof PrimitiveType) {
            PrimitiveType tmpPrim = (PrimitiveType) namedElement;
            PrimitiveType basePrimitiveType =
                    PrimitivesUtil.getBasePrimitiveType(tmpPrim);
            if (basePrimitiveType.getName()
                    .equals(PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME)) {
                Object facetPropertyValue =
                        PrimitivesUtil
                                .getFacetPropertyValue(tmpPrim,
                                        PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LENGTH);
                Assert.assertEquals("Decimal Length is not correct", expectedValue, facetPropertyValue.toString());//$NON-NLS-1$
            }
        } else if (namedElement instanceof Property) {
            Property tmpProperty = (Property) namedElement;
            if (tmpProperty.getType() != null
                    && tmpProperty.getType().getName()
                            .equals(PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME)) {
                if (tmpProperty.getType() instanceof PrimitiveType) {
                    PrimitiveType basePrimitiveType =
                            (PrimitiveType) tmpProperty.getType();
                    Object facetPropertyValue =
                            PrimitivesUtil
                                    .getFacetPropertyValue(basePrimitiveType,
                                            PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LENGTH,
                                            tmpProperty);
                    Assert.assertEquals("Decimal Length is not correct", expectedValue, facetPropertyValue.toString());//$NON-NLS-1$
                }
            }
        }
    }

    public static void assertDecimalPlaces(NamedElement namedElement,
            Object expectedValue) {
        if (namedElement instanceof PrimitiveType) {
            PrimitiveType tmpPrim = (PrimitiveType) namedElement;
            PrimitiveType basePrimitiveType =
                    PrimitivesUtil.getBasePrimitiveType(tmpPrim);
            if (basePrimitiveType.getName()
                    .equals(PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME)) {
                Object facetPropertyValue =
                        PrimitivesUtil
                                .getFacetPropertyValue(tmpPrim,
                                        PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_PLACES);
                Assert.assertEquals("Decimal Places is not correct", expectedValue, facetPropertyValue.toString());//$NON-NLS-1$
            }
        } else if (namedElement instanceof Property) {
            Property tmpProperty = (Property) namedElement;
            if (tmpProperty.getType() != null
                    && tmpProperty.getType().getName()
                            .equals(PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME)) {
                if (tmpProperty.getType() instanceof PrimitiveType) {
                    PrimitiveType basePrimitiveType =
                            (PrimitiveType) tmpProperty.getType();
                    Object facetPropertyValue =
                            PrimitivesUtil
                                    .getFacetPropertyValue(basePrimitiveType,
                                            PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_PLACES,
                                            tmpProperty);
                    Assert.assertEquals("Decimal Places is not correct", expectedValue, facetPropertyValue.toString());//$NON-NLS-1$
                }
            }
        }
    }

    /**
     * Asserts the upper and lower Multiplicty values of the Property match the
     * values passed in.
     * 
     * @param prop
     * @param lowerValue
     * @param upperValue
     */
    public static void assertPropertyMultiplicity(Property prop,
            int lowerValue, int upperValue) {

        int actualLower = prop.getLower();
        int actualUpper = prop.getUpper();

        Assert.assertEquals("Check Property multiplicity lower value",
                actualLower,
                lowerValue);
        Assert.assertEquals("Check Property multiplicity upper value",
                actualUpper,
                upperValue);
    }

    /**
     * @param issues
     * @param modelFile
     * @param project
     * @return
     * @throws IOException
     */
    public static IFile getOutputWSDLFile(Issues issues, IFile modelFile,
            IFolder folder) throws IOException {
        ResourceSet resourceSet = new ResourceSetImpl();
        Resource.Factory.Registry registry =
                resourceSet.getResourceFactoryRegistry();
        Map extensionToFactoryMap = registry.getExtensionToFactoryMap();
        extensionToFactoryMap.put("wsdl", new WSDLResourceFactoryImpl()); //$NON-NLS-1$

        URI uri = URI.createFileURI(modelFile.getFullPath().toPortableString());

        WSDLResourceImpl wsdlResource =
                (WSDLResourceImpl) resourceSet.createResource(uri);

        wsdlResource.load(new HashMap());
        String targetNamespace =
                wsdlResource.getDefinition().getTargetNamespace();
        String javaPackageNameFromNamespaceURI =
                XSDUtil.getJavaPackageNameFromNamespaceURI(modelFile
                        .getProject(), targetNamespace);
        IFile file = folder.getFile(javaPackageNameFromNamespaceURI + ".bom");
        return file;
    }

    public static boolean isDecimalSubTypeFloatingPoint(PrimitiveType pt) {

        boolean ret = false;

        Object objSubType =
                PrimitivesUtil.getFacetPropertyValue(pt,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_SUBTYPE);

        if (objSubType instanceof EnumerationLiteral) {
            EnumerationLiteral enl = (EnumerationLiteral) objSubType;

            if (enl.getName()
                    .equals(PrimitivesUtil.DECIMAL_SUBTYPE_FLOATINGPOINT)) {
                ret = true;
            }
        }

        return ret;
    }

    public static boolean isDecimalSubTypeFixedPoint(PrimitiveType pt) {

        boolean ret = false;

        Object objSubType =
                PrimitivesUtil.getFacetPropertyValue(pt,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_SUBTYPE);

        if (objSubType instanceof EnumerationLiteral) {
            EnumerationLiteral enl = (EnumerationLiteral) objSubType;

            if (enl.getName().equals(PrimitivesUtil.DECIMAL_SUBTYPE_FIXEDPOINT)) {
                ret = true;
            }
        }

        return ret;
    }
}
