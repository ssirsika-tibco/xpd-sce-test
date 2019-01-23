/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.bom.resources.utils.UML2ModelUtil;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.xsdtransform.XsdStereotypeUtils;

/**
 * Business Object Model comparison tool. This will compare 2 models and return
 * an error when a (first) difference is found. Note: this will only compare the
 * semantic model.
 * 
 * @author njpatel
 * 
 */
public class BOMCompareUtil {

    private static final String PLUGIN_ID = "com.tibco.xpd.bom.test";

    // Keeps track of all objects that have already been processed
    private final Map<EObject, EObject> alreadyProcessed;

    public BOMCompareUtil() {
        alreadyProcessed = new HashMap<EObject, EObject>();
    }

    /**
     * Compare the generated model against the gold model.
     * 
     * @param gold
     *            Model from the GOLD file
     * @param generated
     *            Model form the generated file to compare to the GOLD file.
     * 
     * @return OK status if they match, ERROR status if a difference is found
     *         (details of the difference will be in the message of the status).
     */
    public IStatus compare(Model gold, Model generated) {
        try {
            comparePackage("Model", gold, generated);
        } catch (CoreException e) {
            return e.getStatus();
        }

        return Status.OK_STATUS;
    }

    /**
     * Check if the pairing has already been processed.
     * 
     * @param context
     * @param gold
     * @param generated
     * @return
     * @throws CoreException
     */
    private boolean isAlreadyProcessed(String context, EObject gold,
            EObject generated) throws CoreException {
        boolean isPaired = alreadyProcessed.containsKey(gold);

        if (isPaired && alreadyProcessed.get(gold) != generated) {
            throw new CoreException(
                    generateStatus(context,
                            "Element '%s' in the GOLD file is already paired up, so it cannot match '%s'.",
                            gold.toString(),
                            generated.toString()));
        }
        return isPaired;
    }

    /**
     * Compare packages.
     * 
     * @param context
     *            message context
     * @param gold
     * @param generated
     * @throws CoreException
     */
    private void comparePackage(String context, Package gold, Package generated)
            throws CoreException {

        // To avoid cyclic processing
        if (isAlreadyProcessed(context, gold, generated)) {
            return;
        }
        alreadyProcessed.put(gold, generated);

        compareNamedElements(context, gold, generated);

        // Compare the children of the Model
        EList<PackageableElement> goldElements = gold.getPackagedElements();
        EList<PackageableElement> generatedElements =
                generated.getPackagedElements();
        if (goldElements.size() == generatedElements.size()) {
            for (int x = 0; x < goldElements.size(); x++) {
                PackageableElement goldElem = goldElements.get(x);
                PackageableElement genElem = generatedElements.get(x);

                if (genElem != null) {
                    // Check that the element types match
                    if (goldElem.getClass() != genElem.getClass()) {
                        throw new CoreException(
                                generateStatus(context,
                                        "Expected PackageableElement child %d to be of type '%s' but is '%s'.",
                                        x + 1,
                                        goldElem.eClass().getName(),
                                        genElem.eClass().getName()));
                    }

                    // Ignore associations - they will be compared when
                    // processing properties
                    if (goldElem instanceof Association) {
                        continue;
                    }

                    if (genElem instanceof Package) {
                        comparePackage("Package '" + genElem.getName() + "'",
                                (Package) goldElem,
                                (Package) genElem);
                    } else if (genElem instanceof Class) {
                        compareClass("Class '" + genElem.getName() + "'",
                                (Class) goldElem,
                                (Class) genElem);
                    } else if (genElem instanceof PrimitiveType) {
                        comparePrimitiveType("PrimitiveType '"
                                + genElem.getName() + "'",
                                (PrimitiveType) goldElem,
                                (PrimitiveType) genElem);
                    } else if (genElem instanceof Enumeration) {
                        compareEnumeration("Enumeration '" + genElem.getName()
                                + "'",
                                (Enumeration) goldElem,
                                (Enumeration) genElem);
                    } else {
                        throw new CoreException(generateStatus(context,
                                "Unexpected child '%s' (type: %s)",
                                genElem.getName(),
                                genElem.eClass().getName()));
                    }
                } else {
                    throw new CoreException(
                            generateStatus(context,
                                    "Cannot find child PackageableElement with name '%s'.",
                                    goldElem.getName()));
                }

            }

        } else {
            // List of children do not match
            throw new CoreException(generateStatus(context,
                    "Number of children - expected %d but were %d",
                    gold.getPackagedElements().size(),
                    generatedElements.size()));
        }
    }

    /**
     * Compare enumerations.
     * 
     * @param context
     * @param element
     * @param genElem
     */
    private void compareEnumeration(String context, Enumeration gold,
            Enumeration generated) throws CoreException {

        // To avoid cyclic processing
        if (isAlreadyProcessed(context, gold, generated)) {
            return;
        }
        alreadyProcessed.put(gold, generated);

        compareNamedElements(context, gold, generated);

        /*
         * Compare the literal
         */
        EList<EnumerationLiteral> goldLiterals = gold.getOwnedLiterals();
        EList<EnumerationLiteral> genLiterals = generated.getOwnedLiterals();
        if (goldLiterals.size() == genLiterals.size()) {
            for (int x = 0; x < goldLiterals.size(); x++) {
                EnumerationLiteral goldLiteral = goldLiterals.get(x);
                EnumerationLiteral genLiteral = genLiterals.get(x);
                if (genLiteral != null) {
                    compareLiterals(String.format("%s -> Literal '%s'",
                            context,
                            goldLiteral.getName()), goldLiteral, genLiteral);
                } else {
                    throw new CoreException(generateStatus(context,
                            "Did not find enum literal '%s'",
                            goldLiteral.getName()));
                }
            }
        } else {
            throw new CoreException(generateStatus(context,
                    "Expected %d number of literals, got %d",
                    goldLiterals.size(),
                    genLiterals.size()));
        }

        /*
         * Compare superclass, if any
         */
        EList<Classifier> goldGenerals = gold.getGenerals();
        EList<Classifier> generatedGenerals = generated.getGenerals();

        if (goldGenerals.size() == generatedGenerals.size()) {
            if (!goldGenerals.isEmpty()) {
                Classifier goldSuper = goldGenerals.get(0);
                Classifier genSuper = generatedGenerals.get(0);

                if (goldSuper.getClass() == genSuper.getClass()) {
                    if (goldSuper instanceof Enumeration) {
                        compareEnumeration(String.format("%s -> Superclass",
                                context),
                                (Enumeration) goldSuper,
                                (Enumeration) genSuper);
                    } else if (goldSuper instanceof PrimitiveType) {
                        comparePrimitiveType(String.format("%s -> Superclass",
                                context),
                                (PrimitiveType) goldSuper,
                                (PrimitiveType) genSuper);
                    }
                } else {
                    throw new CoreException(generateStatus(context,
                            "Expected the superclass to be '%s' but is '%s'.",
                            goldSuper.eClass().getName(),
                            genSuper.eClass().getName()));
                }
            }
        } else {
            throw new CoreException(generateStatus(context,
                    "Expected getGeneral to be %d but is %d.",
                    goldGenerals.size(),
                    generatedGenerals.size()));
        }
    }

    /**
     * Compare the Enumeration Literals.
     * 
     * @param context
     * @param gold
     * @param generated
     * @throws CoreException
     */
    private void compareLiterals(String context, EnumerationLiteral gold,
            EnumerationLiteral generated) throws CoreException {

        // To avoid cyclic processing
        if (isAlreadyProcessed(context, gold, generated)) {
            return;
        }
        alreadyProcessed.put(gold, generated);

        compareNamedElements(context, gold, generated);
    }

    /**
     * Compare primitive types.
     * 
     * @param string
     * @param element
     * @param genElem
     */
    private void comparePrimitiveType(String context, PrimitiveType gold,
            PrimitiveType generated) throws CoreException {

        // To avoid cyclic processing
        if (isAlreadyProcessed(context, gold, generated)) {
            return;
        }
        alreadyProcessed.put(gold, generated);

        if (isBaseType(gold)) {
            if (isBaseType(generated)) {
                if (!gold.getName().equals(generated.getName())) {
                    throw new CoreException(generateStatus(context,
                            "Expected base type '%s' but got '%s'.",
                            gold.getName(),
                            generated.getName()));
                }
            } else {
                throw new CoreException(
                        generateStatus(context,
                                "Expected base type '%s' but got a Primitive Type '%s' that is not a base type.",
                                gold.getName(),
                                generated.getName()));
            }
        }

        compareNamedElements(context, gold, generated);

        // Compare the types
        if (gold.getGenerals().size() == generated.getGenerals().size()) {
            if (!gold.getGenerals().isEmpty()) {
                Classifier goldClassifier = gold.getGenerals().get(0);
                Classifier genClassifier = generated.getGenerals().get(0);

                if (goldClassifier instanceof PrimitiveType) {
                    if (genClassifier instanceof PrimitiveType) {
                        comparePrimitiveType(String.format("Supertype of '%s'.",
                                gold.getName()),
                                (PrimitiveType) goldClassifier,
                                (PrimitiveType) genClassifier);
                    } else {
                        throw new CoreException(
                                generateStatus(context,
                                        "Expected superclass to be a PrimitiveType, instead is '%s'",
                                        genClassifier.eClass().getName()));
                    }
                } else {
                    // Shouldn't happen
                    throw new CoreException(
                            generateStatus(context,
                                    "Expected super class to be a Primitive Type in the GOLD file but was '%s'",
                                    goldClassifier.eClass().getName()));
                }
            }

        } else {
            throw new CoreException(generateStatus(context,
                    "Expected size of getGenerals() is %d but got %d.",
                    gold.getGenerals().size(),
                    generated.getGenerals().size()));
        }
    }

    /**
     * Check if the given {@link PrimitiveType} is a base type.
     * 
     * @param type
     * @return
     */
    private boolean isBaseType(PrimitiveType type) {
        if (type.eResource() != null
                && PrimitivesUtil.BOM_PRIMITIVE_TYPES_LIBRARY_URI.equals(type
                        .eResource().getURI().toString())) {
            return true;
        }
        return false;
    }

    /**
     * Compare classes.
     * 
     * @param string
     * @param element
     * @param genElem
     */
    private void compareClass(String context, Class gold, Class generated)
            throws CoreException {

        // To avoid cyclic processing
        if (isAlreadyProcessed(context, gold, generated)) {
            return;
        }
        alreadyProcessed.put(gold, generated);

        compareNamedElements(context, gold, generated);

        /*
         * Compare properties
         */
        EList<Property> goldProperties = gold.getOwnedAttributes();
        EList<Property> genProperties = generated.getOwnedAttributes();
        if (goldProperties.size() == genProperties.size()) {
            for (int x = 0; x < goldProperties.size(); x++) {
                Property goldProp = goldProperties.get(x);
                Property genProp = genProperties.get(x);

                if (genProp != null) {
                    compareProperty(String.format("%s -> Property '%s'",
                            context,
                            genProp.getName()), goldProp, genProp);
                } else {
                    throw new CoreException(generateStatus(context,
                            "Cannot find property '%s'.",
                            goldProp.getName()));
                }
            }
        } else {
            throw new CoreException(generateStatus(context,
                    "Expected %d number of properties, got %d.",
                    goldProperties.size(),
                    genProperties.size()));
        }

        /*
         * Compare superclass if any
         */
        if (gold.getGenerals().size() == generated.getGenerals().size()) {
            if (!gold.getGenerals().isEmpty()) {
                Classifier goldClassifier = gold.getGenerals().get(0);
                Classifier generatedClassifier = generated.getGenerals().get(0);

                if (goldClassifier instanceof Class) {
                    if (generatedClassifier instanceof Class) {
                        // Compare the classes
                        compareClass(String.format("Superclass of '%s'",
                                gold.getName()),
                                (Class) goldClassifier,
                                (Class) generatedClassifier);
                    } else {
                        throw new CoreException(
                                generateStatus(context,
                                        "Expected superclass to be a 'Class' but was '%s'.",
                                        generatedClassifier.eClass().getName()));
                    }
                } else {
                    // Shouldn't happen
                    throw new CoreException(
                            generateStatus(context,
                                    "Expected the superclass in the GOLD file to be a 'Class' but was '%s'.",
                                    goldClassifier.eClass().getName()));
                }
            }
        } else {
            throw new CoreException(generateStatus(context,
                    "Expected size of getGenerals() %d but was %d,",
                    gold.getGenerals().size(),
                    generated.getGenerals().size()));
        }

        /*
         * Compare operations, if any
         */
        EList<Operation> goldOps = gold.getOperations();
        EList<Operation> genOps = generated.getOperations();

        if (goldOps.size() == genOps.size()) {
            for (int x = 0; x < goldOps.size(); x++) {
                Operation goldOp = goldOps.get(x);
                Operation genOp = genOps.get(x);

                if (genOp != null) {
                    compareOperation(String.format("%s -> Operation[%s]",
                            context,
                            goldOp.getName()), goldOp, genOp);
                } else {
                    throw new CoreException(generateStatus(context,
                            "Cannot find operation '%s'.",
                            goldOp.getName()));
                }
            }
        } else {
            throw new CoreException(generateStatus(context,
                    "Expected %d operations, got %d.",
                    goldOps.size(),
                    genOps.size()));
        }
    }

    /**
     * Compare operations.
     * 
     * @param context
     * @param gold
     * @param gen
     * @throws CoreException
     */
    private void compareOperation(String context, Operation gold,
            Operation generated) throws CoreException {

        compareNamedElements(context, gold, generated);

        EList<Parameter> goldParams = gold.getOwnedParameters();
        EList<Parameter> genParams = generated.getOwnedParameters();

        if (goldParams.size() == genParams.size()) {
            for (int x = 0; x < goldParams.size(); x++) {
                Parameter goldParam = goldParams.get(0);
                Parameter genParam = genParams.get(0);

                if (goldParam.getName().equals(genParam.getName())) {
                    compareParameter(String.format("%s -> Parameter [%s]",
                            context,
                            goldParam.getName()), goldParam, genParam);
                } else {
                    throw new CoreException(generateStatus(context,
                            "Expected parameter %d to be '%s' but is '%s'.",
                            x + 1,
                            goldParam.getName(),
                            genParam.getName()));
                }
            }
        } else {
            throw new CoreException(generateStatus(context,
                    "Expected %d parameters, got %d.",
                    goldParams.size(),
                    genParams.size()));
        }
    }

    /**
     * Compare operation parameter.
     * 
     * @param context
     * @param gold
     * @param generated
     * @throws CoreException
     */
    private void compareParameter(String context, Parameter gold,
            Parameter generated) throws CoreException {

        compareNamedElements(context, gold, generated);

        // Compare direction
        if (!gold.getDirection().equals(generated.getDirection())) {
            throw new CoreException(generateStatus(context,
                    "Expected direction to be '%s' but is '%s.",
                    gold.getDirection(),
                    generated.getDirection()));
        }

        /*
         * Compare multiplicity
         */
        if (gold.getLower() != generated.getLower()) {
            throw new CoreException(generateStatus(context,
                    "Expected multiplicity lower value to be %d but is %d.",
                    gold.getLower(),
                    generated.getLower()));
        }
        if (gold.getUpper() != generated.getUpper()) {
            throw new CoreException(generateStatus(context,
                    "Expected multiplicity upper value to be %d but is %d.",
                    gold.getUpper(),
                    generated.getUpper()));
        }

        /*
         * Compare types
         */
        Type goldType = gold.getType();
        Type genType = generated.getType();

        if (goldType != null) {
            if (genType != null) {
                if (goldType.getClass() == genType.getClass()) {
                    if (goldType instanceof Class) {
                        compareClass(String.format("%s -> Type", context),
                                (Class) goldType,
                                (Class) genType);
                    } else if (goldType instanceof PrimitiveType) {
                        comparePrimitiveType(String.format("%s -> Type",
                                context),
                                (PrimitiveType) goldType,
                                (PrimitiveType) genType);
                    }
                } else {
                    throw new CoreException(
                            generateStatus(context,
                                    "Expected the type to be an instance of '%s' but is '%s'.",
                                    goldType.eClass(),
                                    genType.eClass()));
                }
            } else {
                throw new CoreException(generateStatus(context,
                        "Expected return type to be '%s' but is null.",
                        goldType.getName()));
            }
        } else {
            if (genType != null) {
                throw new CoreException(generateStatus(context,
                        "Expected no return type but is '%s'.",
                        genType.getName()));
            }
        }
    }

    /**
     * Compare a Property.
     * 
     * @param context
     * @param gold
     * @param generated
     * @throws CoreException
     */
    private void compareProperty(String context, Property gold,
            Property generated) throws CoreException {

        // To avoid cyclic processing
        if (isAlreadyProcessed(context, gold, generated)) {
            return;
        }
        alreadyProcessed.put(gold, generated);

        compareNamedElements(context, gold, generated);

        /*
         * Compare property types
         */
        Type goldType = gold.getType();
        Type genType = generated.getType();

        if (goldType != null) {
            if (genType != null) {
                if (goldType.getClass() == genType.getClass()) {
                    if (goldType instanceof Class) {
                        compareClass(String.format("%s -> Type[%s]",
                                context,
                                goldType.getName()),
                                (Class) goldType,
                                (Class) genType);
                    } else if (goldType instanceof PrimitiveType) {
                        comparePrimitiveType(String.format("%s -> Type[%s]",
                                context,
                                goldType.getName()),
                                (PrimitiveType) goldType,
                                (PrimitiveType) genType);
                    } else if (goldType instanceof Enumeration) {
                        compareEnumeration(String.format("%s -> Type[%s]",
                                context,
                                goldType.getName()),
                                (Enumeration) goldType,
                                (Enumeration) genType);
                    } else {
                        throw new CoreException(generateStatus(context,
                                "Unexpected type '%s'.",
                                goldType.eClass().getName()));
                    }
                } else {
                    throw new CoreException(generateStatus(context,
                            "Expected type to be '%s' but is '%s'.",
                            goldType.eClass().getName(),
                            genType.eClass().getName()));
                }
            } else {
                throw new CoreException(generateStatus(context,
                        "Expected to be of type '%s' but has no type set.",
                        goldType.getName()));
            }
        } else {
            if (genType != null) {
                throw new CoreException(generateStatus(context,
                        "Expected to have no type set but has type '%s' set.",
                        genType.getName()));
            }
        }

        /*
         * Compare multiplicity
         */
        if (gold.getLower() != generated.getLower()) {
            throw new CoreException(generateStatus(context,
                    "Expected multiplicty lower value to be %d but is %d",
                    gold.getLower(),
                    generated.getLower()));
        }
        if (gold.getUpper() != generated.getUpper()) {
            throw new CoreException(generateStatus(context,
                    "Expected multiplicty upper value to be %d but is %d",
                    gold.getUpper(),
                    generated.getUpper()));
        }

        /*
         * Compare associations, if any
         */
        Association goldAssoc = gold.getAssociation();
        Association generatedAssoc = generated.getAssociation();

        if (goldAssoc != null && generatedAssoc != null) {
            compareAssociation(String.format("%s -> Association", context),
                    goldAssoc,
                    generatedAssoc);
        } else if (goldAssoc != null) {
            throw new CoreException(generateStatus(context,
                    "Expected to have an association."));
        } else if (generatedAssoc != null) {
            throw new CoreException(generateStatus(context,
                    "Expected no association."));
        }
    }

    /**
     * Compare Associations.
     * 
     * @param context
     * @param gold
     * @param generated
     * @throws CoreException
     */
    private void compareAssociation(String context, Association gold,
            Association generated) throws CoreException {

        // To avoid cyclic processing
        if (isAlreadyProcessed(context, gold, generated)) {
            return;
        }
        alreadyProcessed.put(gold, generated);

        compareElements(context, gold, generated);

        /*
         * Check aggregation kind
         */
        AggregationKind goldType = UML2ModelUtil.getAggregationType(gold);
        AggregationKind generatedType =
                UML2ModelUtil.getAggregationType(generated);

        // TODO: Need to test this
        if (!goldType.equals(generatedType)) {
            throw new CoreException(generateStatus(context,
                    "Expected association to be of type '%s' but is '%s'.",
                    goldType,
                    generatedType));
        }

        /*
         * Compare member ends
         */
        EList<Property> goldEnds = gold.getMemberEnds();
        EList<Property> generatedEnds = generated.getMemberEnds();

        if (goldEnds.size() == generatedEnds.size()) {
            for (int idx = 0; idx < goldEnds.size(); idx++) {
                compareProperty(String.format("%s -> MemberEnd[%d]",
                        context,
                        idx), goldEnds.get(idx), generatedEnds.get(idx));
            }
        } else {
            throw new CoreException(generateStatus(context,
                    "Expected %d number of member ends, got %d",
                    goldEnds.size(),
                    generatedEnds.size()));
        }

        /*
         * Compare owned ends
         */
        goldEnds = gold.getOwnedEnds();
        generatedEnds = generated.getOwnedEnds();

        if (goldEnds.size() == generatedEnds.size()) {
            for (int idx = 0; idx < goldEnds.size(); idx++) {
                compareProperty(String.format("%s -> OwnedEnd[%d]",
                        context,
                        idx), goldEnds.get(idx), generatedEnds.get(idx));
            }
        } else {
            throw new CoreException(generateStatus(context,
                    "Expected %d number of owner ends, got %d",
                    goldEnds.size(),
                    generatedEnds.size()));
        }
    }

    /**
     * Compare contents of NamedElements.
     * 
     * @param context
     *            message context
     * @param gold
     * @param generated
     * @throws CoreException
     */
    private void compareNamedElements(String context, NamedElement gold,
            NamedElement generated) throws CoreException {
        // Compare names
        if (!gold.getName().equals(generated.getName())) {
            throw new CoreException(generateStatus(context,
                    "Expected name to be '%s' but was '%s'.",
                    gold.getName(),
                    generated.getName()));
        }

        // Compare labels (display names)
        String goldLabel = PrimitivesUtil.getDisplayLabel(gold);
        String generatedLabel = PrimitivesUtil.getDisplayLabel(generated);

        if (goldLabel == null && generatedLabel != null) {
            throw new CoreException(generateStatus(context,
                    "Expected display label to be null but was '%s'",
                    generatedLabel));
        } else if (!goldLabel.equals(generatedLabel)) {
            throw new CoreException(generateStatus(context,
                    "Expected label to be '%s' but was '%s'.",
                    goldLabel,
                    generatedLabel));
        }

        // Compare stereotype applications
        compareElements(context, gold, generated);
    }

    /**
     * Compare stereotype applications.
     * 
     * @param gold
     * @param generated
     * @throws CoreException
     */
    private void compareElements(String context, Element gold, Element generated)
            throws CoreException {
        EList<Stereotype> goldStereotypes = gold.getAppliedStereotypes();

        if (goldStereotypes.size() == generated.getAppliedStereotypes().size()) {
            for (Stereotype goldStereotype : goldStereotypes) {
                Stereotype genStereotype =
                        generated.getAppliedStereotype(goldStereotype
                                .getQualifiedName());
                if (genStereotype != null) {
                    EObject goldApp =
                            gold.getStereotypeApplication(goldStereotype);
                    EObject genApp =
                            generated.getStereotypeApplication(genStereotype);

                    if (goldApp != null) {
                        if (genApp != null) {
                            compareStereotypeApplications(String.format("%s -> Stereotype App(%s)",
                                    context,
                                    goldStereotype.getQualifiedName()),
                                    goldApp,
                                    genApp);
                        } else {
                            throw new CoreException(
                                    generateStatus(context,
                                            "Cannot find stereotype application for '%s',",
                                            goldStereotype.getQualifiedName()));
                        }
                    } else {
                        // This shouldn't happen
                        throw new CoreException(
                                generateStatus(context,
                                        "Cannot find stereotype application for '%s' in the GOLD file,",
                                        goldStereotype.getQualifiedName()));
                    }
                } else {
                    throw new CoreException(generateStatus(context,
                            "Stereotype '%s' is not applied.",
                            goldStereotype.getQualifiedName()));
                }
            }
        } else {
            throw new CoreException(generateStatus(context,
                    "Expected %d stereotypes to be applied but found %d.",
                    goldStereotypes.size(),
                    generated.getAppliedStereotypes().size()));
        }
    }

    /**
     * Compare the stereotype applications.
     * 
     * @param context
     * @param gold
     * @param gen
     * @throws CoreException
     */
    private void compareStereotypeApplications(String context, EObject gold,
            EObject generated) throws CoreException {

        // To avoid cyclic processing
        if (isAlreadyProcessed(context, gold, generated)) {
            return;
        }
        alreadyProcessed.put(gold, generated);

        EList<EStructuralFeature> goldFeatures =
                gold.eClass().getEStructuralFeatures();

        if (goldFeatures.size() == generated.eClass().getEStructuralFeatures()
                .size()) {
            for (EStructuralFeature goldFeature : goldFeatures) {

                // If this a reference to base type then ignore
                if (goldFeature instanceof EReference
                        && goldFeature.getName().startsWith("base_")) {
                    continue;
                }

                /*
                 * bharti: JUnit tests do not use the builders
                 * (WsdlToBomBuilder) so bom wsdl resources (bomWsdlSources)
                 * attribute on XsdBasedModel notation profile will not be
                 * available in the generated bom. At the moment ignoring this
                 * for comparison. May be there is a scope to improve this!
                 */
                if ("bomWsdlSources".equals(goldFeature.getName())) {

                    continue;
                }

                EStructuralFeature genFeature =
                        generated.eClass()
                                .getEStructuralFeature(goldFeature.getName());
                if (genFeature != null) {
                    Object goldValue = gold.eGet(goldFeature);
                    Object genValue = generated.eGet(genFeature);

                    // Ignore xsdSchemaLocation as they will not match
                    // anyway (paths will be different)
                    if (XsdStereotypeUtils.XSD_SCHEMA_LOCATION
                            .equals(genFeature.getName())) {
                        if (goldValue instanceof String
                                && goldValue.toString().length() > 0) {
                            // A value is expected
                            if (genValue == null
                                    || genValue.toString().length() == 0) {
                                throw new CoreException(
                                        generateStatus(context,
                                                "Expected value in attribute 'xsdSchemaLocation' but is null or empty."));
                            }
                        }
                        continue;
                    }

                    if (XsdStereotypeUtils.XSD_STUDIO_VERSION.equals(genFeature
                            .getName())) {
                        continue;
                    }

                    if (goldValue != null) {
                        if (genValue != null) {
                            compareStereotypeApplicationValues(String.format("%s -> Feature[%s]",
                                    context,
                                    goldFeature.getName()),
                                    goldValue,
                                    genValue);
                        } else {
                            throw new CoreException(
                                    generateStatus(context,
                                            "Expected a value for feature '%s' but got null.",
                                            goldFeature.getName()));
                        }
                    } else {
                        if (genValue != null) {
                            throw new CoreException(
                                    generateStatus(context,
                                            "Expected no value for feature '%s' but got '%s'.",
                                            goldFeature.getName(),
                                            genValue.toString()));
                        }
                    }
                } else {
                    throw new CoreException(generateStatus(context,
                            "Cannot find feature '%s'.",
                            goldFeature.getName()));
                }
            }
        } else {
            throw new CoreException(generateStatus(context,
                    "Expected %d features but got %d,",
                    goldFeatures.size(),
                    generated.eClass().getEStructuralFeatures().size()));
        }

    }

    /**
     * Compare the values of a feature of a stereotype application.
     * 
     * @param context
     * @param gold
     * @param generated
     * @throws CoreException
     */
    private void compareStereotypeApplicationValues(String context,
            Object gold, Object generated) throws CoreException {
        if (gold instanceof Collection<?>) {
            Object[] goldList = ((Collection<?>) gold).toArray();
            if (generated instanceof Collection<?>) {
                Object[] genList = ((Collection<?>) generated).toArray();

                if (goldList.length == genList.length) {
                    if (goldList.length == 0) {
                        // Both collections are empty so the values match
                        return;
                    }
                    for (int x = 0; x < goldList.length; x++) {
                        compareStereotypeApplicationValues(String.format("%s -> List Item[%d]",
                                context,
                                x),
                                goldList[x],
                                genList[x]);
                    }
                } else {
                    throw new CoreException(generateStatus(context,
                            "Expected the collection size to be %d but is %d.",
                            goldList.length,
                            genList.length));
                }

            } else {
                throw new CoreException(generateStatus(context,
                        "Expected value to be a collection but is '%s'.",
                        generated.getClass().getName()));
            }
        } else if (gold instanceof EObject) {
            if (generated instanceof EObject) {
                compareStereotypeApplications(context,
                        (EObject) gold,
                        (EObject) generated);
            } else {
                throw new CoreException(generateStatus(context,
                        "Expected value to be an EObject but is '%s'.",
                        generated.getClass().getName()));
            }
        } else {
            if (gold != null) {
                if (!gold.equals(generated)) {
                    throw new CoreException(generateStatus(context,
                            "Expected value to be '%s' but is '%s'.",
                            gold,
                            generated));
                }
            } else if (generated != null) {
                throw new CoreException(generateStatus(context,
                        "Expected value to be null but is '%s'.",
                        generated));
            }
        }
    }

    /**
     * Generate an {@link IStatus} with the information given.
     * 
     * @param context
     * @param message
     * @param params
     * @return
     */
    private IStatus generateStatus(String context, String message,
            Object... params) {
        return new Status(IStatus.ERROR, PLUGIN_ID, context + ": "
                + String.format(message, params));
    }
}
