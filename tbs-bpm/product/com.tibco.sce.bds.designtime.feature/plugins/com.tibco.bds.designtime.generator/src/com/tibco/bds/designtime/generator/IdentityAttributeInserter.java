package com.tibco.bds.designtime.generator;

import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.ExtendedMetaData;

import com.tibco.bds.designtime.generator.anno.AnnotationManager;
import com.tibco.bds.designtime.generator.common.BDSConstants;

/**
 * Adds id and version attributes to EClasses and annotates them correctly for
 * Teneo purposes.
 * 
 * @author smorgan
 * 
 */
public class IdentityAttributeInserter {

    /**
     * Adds id/version/case id attributes to the appropriate classes within the
     * supplied package(s).
     * 
     * @param ePackages
     */
    public static void addIdAndVersionAndCaseIdToClasses(
            List<EPackage> ePackages) {
        for (EPackage pkg : ePackages) {
            for (EClassifier classifier : pkg.getEClassifiers()) {
                if (classifier instanceof EClass) {
                    EClass clazz = (EClass) classifier;
                    // Only add id/version attributes if it's a
                    // base type. Others will inherit from parent.
                    if (!hasAncestor(clazz)
                            && !ExtendedMetaData.INSTANCE.isDocumentRoot(clazz)) {

                        // Don't add attributes to non-persistent types
                        if (AnnotationManager.isCaseClass(clazz)) {
                            // Only add id/version attributes if it's a
                            // base type. Others will inherit from parent.
                            addIdAttributeToClass(clazz);
                            // Only add Version to the case class
                            addVersionAttributeToClass(clazz);
                        }
                        // Additionally, for global types, add a bdsCaseId
                        // attribute. This aids fast deletion on a case
                        // object's containment tree by holding the bdsId
                        // of the owning case object. In many cases, we
                        // may discover that this class is deletable by
                        // database cascade (rendering the attribute
                        // unnecessary), but we won't know that until
                        // run-time, plus we need to consider that a later
                        // revision of the BOM may change things such that
                        // a class that was once cascade-deleteable is no
                        // longer.
                        if (AnnotationManager.isGlobalClass(clazz)) {
                            addIdAttributeToClass(clazz);
                            addCaseIdAttributeToClass(clazz);
                        }
                    }
                }
            }
        }
    }

    private static void addIdAttributeToClass(EClass clazz) {
        EAttribute attr = EcoreFactory.eINSTANCE.createEAttribute();
        attr.setName(BDSConstants.BDS_ID);
        attr.setEType(EcorePackage.eINSTANCE.getELongObject());
        AnnotationManager.addBDSIdAnnotations(attr);
        clazz.getEStructuralFeatures().add(attr);
    }

    private static void addVersionAttributeToClass(EClass clazz) {
        EAttribute attr = EcoreFactory.eINSTANCE.createEAttribute();
        attr.setName(BDSConstants.BDS_VERSION);
        attr.setEType(EcorePackage.eINSTANCE.getELongObject());
        AnnotationManager.addBDSVersionAnnotations(attr);
        clazz.getEStructuralFeatures().add(attr);
    }

    private static void addCaseIdAttributeToClass(EClass clazz) {
        EAttribute attr = EcoreFactory.eINSTANCE.createEAttribute();
        attr.setName(BDSConstants.BDS_OWNER_ID);
        attr.setEType(EcorePackage.eINSTANCE.getELongObject());
        AnnotationManager.addBDSCaseIdAnnotations(attr);
        clazz.getEStructuralFeatures().add(attr);
    }

    private static boolean hasAncestor(EClass clazz) {
        return (!clazz.getEAllSuperTypes().isEmpty());
    }
}
