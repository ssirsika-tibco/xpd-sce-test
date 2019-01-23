package com.tibco.bds.designtime.generator.emfmod;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.BasicExtendedMetaData;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.xsd.XSDComponent;
import org.eclipse.xsd.XSDDiagnostic;

import com.tibco.bds.designtime.generator.internal.Messages;
import com.tibco.xpd.bom.gen.biz.GenerationException;

/**
 * Transforms XSD(s) to EPackages. This is mostly achieved by calling the
 * standard XsdEcoreBuilder, but we then perform various post-processing of the
 * packages.
 * 
 * @author smorgan
 * 
 */
public class XSDToEcoreTransformer {

    public List<EPackage> transform(Collection<URI> uris)
            throws GenerationException {

        CDSXSDEcoreBuilder builder = new CDSXSDEcoreBuilder();
        builder.setValidate(true);

        // Invoke XSDEcoreBuilder, then construct list of resulting EPackages
        Collection<Object> generatedObjects = builder.generate(uris);

        // Returned collection potentially contains diagnostics, but
        // it seems cleaner to explicitly retrieve them first via
        // getDiagnostics.
        List<XSDDiagnostic> diagnostics = builder.getDiagnostics();
        if (diagnostics != null && !diagnostics.isEmpty()) {
            StringBuilder buf = new StringBuilder();
            for (XSDDiagnostic diagnostic : diagnostics) {
                if (buf.length() != 0) {
                    buf.append("; ");
                }
                buf.append(String.format("%s (%s)",
                        diagnostic.getMessage(),
                        diagnostic.getLocationURI()));
            }
            throw new GenerationException(String.format(Messages
                    .getString("ReceivedInvalidSchema"), buf.toString()));
        }
        
        // Check for missing ecore:names and fail if any are found
        Map<XSDComponent, EModelElement> unnamedElements = builder.getUnnamedElements();
        if (!unnamedElements.isEmpty()) {
            StringBuilder buf = new StringBuilder();
            for (Entry<XSDComponent, EModelElement> entry : unnamedElements.entrySet()) {
                if (buf.length() != 0) {
                    buf.append("; ");
                }
                buf.append(String.format("Ecore: %s [XSD: %s]", entry.getValue(), entry.getKey()));
                
            }
            String errorMsg = String.format(Messages
                    .getString("XSDEcoreBuilder_missingEcoreNames"), buf.toString());
            throw new GenerationException(errorMsg);
        }

        List<EPackage> eCorePackages = new ArrayList<EPackage>();
        for (Object object : generatedObjects) {
            if (object instanceof EPackage) {
                eCorePackages.add((EPackage) object);
            }
        }

        // If there is a DocumentRoot then we need to add "dummy" element so
        // that
        // we can de-serialize Complex Types correctly
        for (EPackage pkg : eCorePackages) {
            addDocumentRootFeaturesForUnwrappedTypes(pkg);
        }

        return eCorePackages;
    }

    /**
     * Obtains the DocumentRoot EClass for the given EPackage. If one does not
     * exist, this method will create and return a new one.
     * 
     * @param pkg
     * @return
     */
    private EClass createDocumentRootForPackage(EPackage pkg) {

        // This will return null if no existing DocumentRoot exists
        // (i.e. if there was nothing in the XSD that prompted introduction
        // of a DocumentRoot)
        EClass drClass = getDocumentRoot(pkg);

        // No existing DocumentRoot class, so create one. This code is based on
        // reverse-engineering the logic in XsdEcoreBuilder.
        if (drClass == null) {
            ExtendedMetaData extendedMetaData = ExtendedMetaData.INSTANCE;

            // No DocumentRoot exists, so create one
            drClass = EcoreFactory.eINSTANCE.createEClass();
            // Get and set the name for the document root
            drClass.setName(getUniqueDocumentRootName(pkg));

            // Decorate the class appropriately
            extendedMetaData.setDocumentRoot(drClass);
            pkg.getEClassifiers().add(drClass);

            // Add standard features
            EAttribute mixedAttr = EcoreFactory.eINSTANCE.createEAttribute();
            mixedAttr.setName("mixed");
            mixedAttr.setUnique(false);
            mixedAttr.setEType(EcorePackage.Literals.EFEATURE_MAP_ENTRY);
            mixedAttr.setLowerBound(0);
            mixedAttr.setUpperBound(-1);
            drClass.getEStructuralFeatures().add(mixedAttr);
            extendedMetaData.setFeatureKind(mixedAttr,
                    ExtendedMetaData.ELEMENT_WILDCARD_FEATURE);
            extendedMetaData.setName(mixedAttr, ":" + mixedAttr.getName());

            EReference xmlnsPrefixMapFeature =
                    EcoreFactory.eINSTANCE.createEReference();
            xmlnsPrefixMapFeature.setName("xMLNSPrefixMap");
            xmlnsPrefixMapFeature
                    .setEType(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY);
            xmlnsPrefixMapFeature.setLowerBound(0);
            xmlnsPrefixMapFeature.setUpperBound(-1);
            xmlnsPrefixMapFeature.setContainment(true);
            xmlnsPrefixMapFeature.setResolveProxies(false);
            xmlnsPrefixMapFeature.setTransient(true);
            drClass.getEStructuralFeatures().add(xmlnsPrefixMapFeature);
            extendedMetaData.setFeatureKind(xmlnsPrefixMapFeature,
                    ExtendedMetaData.ATTRIBUTE_FEATURE);
            extendedMetaData.setName(xmlnsPrefixMapFeature, "xmlns:prefix");

            EReference xsiSchemaLocationMapFeature =
                    EcoreFactory.eINSTANCE.createEReference();
            xsiSchemaLocationMapFeature.setName("xSISchemaLocation");
            xsiSchemaLocationMapFeature
                    .setEType(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY);
            xsiSchemaLocationMapFeature.setLowerBound(0);
            xsiSchemaLocationMapFeature.setUpperBound(-1);
            xsiSchemaLocationMapFeature.setContainment(true);
            xsiSchemaLocationMapFeature.setResolveProxies(false);
            xsiSchemaLocationMapFeature.setTransient(true);
            drClass.getEStructuralFeatures().add(xsiSchemaLocationMapFeature);
            extendedMetaData.setFeatureKind(xsiSchemaLocationMapFeature,
                    ExtendedMetaData.ATTRIBUTE_FEATURE);
            extendedMetaData.setName(xsiSchemaLocationMapFeature,
                    "xmlns:prefix");
            extendedMetaData.setName(xsiSchemaLocationMapFeature,
                    "xsi:schemaLocation");

        }

        return drClass;
    }

    /**
     * This method looks at each complex type and adds a matching element of
     * that type if one does not already exist
     * 
     * @param pkg
     */
    private void addDocumentRootFeaturesForUnwrappedTypes(EPackage pkg) {

        // Make the call to get the document root, if there is one already
        // present then it will return the existing one, otherwise it
        // will create one and return that
        EClass drClass = createDocumentRootForPackage(pkg);
        if (drClass == null) {
            return;
        }

        ExtendedMetaData extendedMetaData = ExtendedMetaData.INSTANCE;

        if (extendedMetaData == null) {
            return;
        }

        for (EObject obj : pkg.eContents()) {
            if (obj instanceof EClass && obj != drClass) {
                EClass eClass = (EClass) obj;
                // Get the real name of the Complex Type
                String realComplexTypeName = extendedMetaData.getName(eClass);

                // Check to make sure that there really is a complex name, if
                // there is not,
                // then there is nothing for us to do.
                if (realComplexTypeName == null) {
                    continue;
                }

                // Check each of the existing elements and see if there is
                // already
                // an element for the given type with an identical name
                boolean found = false;
                for (Iterator<EStructuralFeature> elementIter =
                        extendedMetaData.getElements(drClass).iterator(); elementIter
                        .hasNext()
                        && !found;) {
                    EStructuralFeature element = elementIter.next();

                    // Check if this is one that takes our type
                    if (element.getEType() == eClass && element.isChangeable()) {
                        // Make sure that the method name on the DocumentRoot is
                        // the same
                        String realElementName =
                                extendedMetaData.getName(element);
                        if (realElementName.equals(realComplexTypeName)) {
                            found = true;
                            break; // no need to check any more, we know it
                                   // already exists
                        }
                    }
                }
                if (!found) {
                    // Construct the expected new feature name that will be the
                    // name
                    // of the element that will match the Complex Type, this
                    // will be
                    // appended by a BDS Specific string and make unique
                    String newFeatureName =
                            createBDSInternalFeatureName(eClass.getName(),
                                    drClass);

                    // Add a new feature
                    EReference newReference =
                            EcoreFactory.eINSTANCE.createEReference();
                    newReference.setName(newFeatureName);
                    newReference.setEType(eClass);
                    newReference
                            .setUpperBound(ETypedElement.UNSPECIFIED_MULTIPLICITY);
                    newReference.setVolatile(true);
                    newReference.setTransient(true);
                    newReference.setDerived(true);
                    newReference.setContainment(true);
                    newReference.setResolveProxies(false);

                    drClass.getEStructuralFeatures().add(newReference);
                    extendedMetaData.setFeatureKind(newReference,
                            ExtendedMetaData.ELEMENT_FEATURE);
                    // Initial char NOT lower-cased in this context (element
                    // name)
                    extendedMetaData.setName(newReference, realComplexTypeName);
                    // TODO Check this is always correct. Was
                    // xsdElementDeclaration.getTargetNamespace()
                    extendedMetaData.setNamespace(newReference,
                            "##targetNamespace");
                }
            }
        }
    }

    /**
     * Creates a unique feature name that would be able to be added to the
     * document root
     * 
     * @param eClassName
     *            Name of the Complex Type this is representing
     * @param documentRoot
     *            The document root eclass to avoid clashes in
     * @return The name to use for the internal feature
     */
    private String createBDSInternalFeatureName(String eClassName,
            EClass documentRoot) {
        // Start with the simple name, if this clashes then we will have to
        // change it
        final String coreFeatureName =
                String
                        .format("%s%sBDSInternalSerializationElement",
                                eClassName.substring(0, 1).toLowerCase(),
                                eClassName.substring(1));

        String newFeatureName = coreFeatureName;

        // Document Roots will only exist if there are elements present, so if
        // there are no other elements, our default name is OK
        if (documentRoot != null) {
            boolean nameClash = false;
            int appendValue = 0;

            do {
                // Check if the previous name clashed with something
                if (nameClash) {
                    // Update the name so that it will have an additional digit
                    // at the end
                    ++appendValue;
                    newFeatureName =
                            String.format("%s%d", coreFeatureName, appendValue);
                    nameClash = false;
                }

                for (EStructuralFeature structuralFeature : documentRoot
                        .getEStructuralFeatures()) {
                    // Check the existing name against the proposed name
                    if (structuralFeature.getName().equals(newFeatureName)) {
                        nameClash = true;
                        break;
                    }
                }

            } while (nameClash);
        }

        return newFeatureName;
    }

    /**
     * Checks if there is a document root in the package and returns it. If
     * there is no document root then return null
     * 
     * @param pkg
     *            EPackage to retrieve the document root from
     * @return Document root EClass, or null if there is no document root
     */
    private EClass getDocumentRoot(EPackage pkg) {
        EClass docRoot = null;

        ExtendedMetaData extendedMetaData = BasicExtendedMetaData.INSTANCE;

        if (extendedMetaData != null) {
            // This will return null if no existing DocumentRoot exists
            // (i.e. if there was nothing in the XSD that prompted introduction
            // of a DocumentRoot)
            docRoot = BasicExtendedMetaData.INSTANCE.getDocumentRoot(pkg);
        }

        return docRoot;
    }

    /**
     * Gets a unique name for the document root
     * 
     * @param pkg
     *            Package for the document root
     * @return
     */
    private String getUniqueDocumentRootName(EPackage pkg) {
        String docRootDefaultName = "DocumentRoot";
        String documentRootName = docRootDefaultName;

        boolean nameClash = false;
        int appendValue = 0;

        do {
            // Check if the previous name clashed with something
            if (nameClash != false) {
                // Update the name so that it will have an additional digit at
                // the end
                ++appendValue;
                documentRootName = docRootDefaultName + appendValue;
                nameClash = false;
            }

            // Check all the names for a clash
            for (EObject topLevelItem : pkg.eContents()) {
                if (topLevelItem instanceof EClassifier) {
                    EClassifier eClassifier = (EClassifier) topLevelItem;
                    // Get the real name of the Complex Type
                    String realTypeName =
                            BasicExtendedMetaData.INSTANCE.getName(eClassifier);

                    if (realTypeName.equals(documentRootName)) {
                        nameClash = true;
                        break;
                    }
                }

            }
        } while (nameClash);

        return documentRootName;
    }
}
