package com.tibco.bds.designtime.generator;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.util.ExtendedMetaData;

import com.tibco.bds.designtime.generator.anno.AnnotationManager;
import com.tibco.bds.designtime.generator.common.BDSConstants;

/**
 * Creates 'opposite' EReferences for uni-directional containments. In other
 * words, this adds a new EReference to a reference's target class referring
 * back to the source class. This new reference and the existing one as set as
 * each other's 'opposite'; the end result looks like how a bi-directional
 * containment would look. Rather than offering the user bi-directional
 * navigability, the motivations for this are: (1) To force the database foreign
 * key for a to-one containment to appear in the child table (i.e. a
 * child-to-parent reference). Default behaviour would be for this to appear in
 * the parent table (parent-to-child) thus removing the possibility of database
 * cascade on delete. A second motivation relates to scenarios where database
 * cascade delete is not possible. In this case, 'fast delete' functionality
 * uses a series of queries (determined at deployment time) to achieve deletion.
 * In cases where circular references occur, it is sometime necessary to nullify
 * foreign keys (to 'break the chain') before deletion. These opposite
 * references make this possible via HQL.
 * 
 * @author smorgan
 * 
 */
public class ContainmentOppositeInserter {

    // Parameters are the names of the source class and reference
    private static final String OPPOSITE_REF_NAME_FORMAT = "opp%s%s";

    /**
     * Adds containment opposite references to all appropriate references
     * belonging to classes within the supplied packages.
     * 
     * @param pkgs
     */
    public static void addContainmentOpposites(List<EPackage> pkgs) {
        // For all classes within all packages...
        for (EPackage pkg : pkgs) {
            // Get the source BOM for the type
            String srcBom = getBOMSource(pkg);
            // Skip anything we can't determine the BOM of - don't want to
            // accidently change something that we do not know the origin of
            if (srcBom == null) {
                continue;
            }

            for (EClassifier cfr : pkg.getEClassifiers()) {
                // Exclude the document root class
                if (cfr instanceof EClass
                        && !ExtendedMetaData.INSTANCE
                                .isDocumentRoot((EClass) cfr)) {
                    EClass clazz = (EClass) cfr;
                    // We're only interested in persistent classes
                    if (AnnotationManager.isCaseClass(clazz)
                            || AnnotationManager.isGlobalClass(clazz)) {
                        for (EReference ref : clazz.getEReferences()) {
                            // Add an opposite if it's a uni-directional
                            // containment (i.e. don't affect associations
                            // or containments are are already bi-directional
                            // (which isn't allowed at the time of writing, but
                            // we could feasibly allow in the future)).
                            if (ref.isContainment()
                                    && ref.getEOpposite() == null) {
                                // Check to make sure that the referenced class
                                // is in the same BOM as this one, if it is not,
                                // then we do not want to add an eOpposite to
                                // the other BOM, this is because it may have
                                // already been generated and we cannot change
                                // an eCore that has already been generated
                                String bomSource = getBOMSource(ref.getEReferenceType().getEPackage());
                                if( (bomSource != null) && srcBom.compareTo(bomSource) == 0 ) {
                                    addContainmentOpposite(ref);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Retrieve the BOM that this ePackage was generated from
     * 
     * @param pkg
     * @return
     */
    private static String getBOMSource(EPackage pkg) {
        String bomSource = null;

        EAnnotation annotation = pkg.getEAnnotation(BDSConstants.BDS_ANNOTATION);
        if (annotation != null) {
            EMap<String, String> details = annotation.getDetails();
            if (details != null) {
                bomSource = details.get(BDSConstants.BDS_BOM_FILE_NAME_ANNO);
            }
        }

        return bomSource;
    }
    
    private static String generateReferenceNameForOpposite(EReference ref) {
        EClass targetType = ref.getEReferenceType();
        String oppFeatName =
                String.format(OPPOSITE_REF_NAME_FORMAT, ref
                        .getEContainingClass().getName(), ref.getName());

        // Ensure the name does not conflict with an existing feature of the
        // target type.
        List<String> usedNames = new ArrayList<String>();
        for (EStructuralFeature feat : targetType.getEAllStructuralFeatures()) {
            usedNames.add(feat.getName().toLowerCase());
        }

        String lcName = oppFeatName.toLowerCase();
        if (usedNames.contains(lcName)) {
            int suffixNum = 0;
            do {
                suffixNum++;
            } while (usedNames.contains(lcName + suffixNum));
            oppFeatName += suffixNum;
        }
        return oppFeatName;
    }

    /**
     * Adds an opposite for the given reference
     * 
     * @param ref
     */
    private static void addContainmentOpposite(EReference ref) {
        EClass targetType = ref.getEReferenceType();
        EReference oppRef = EcoreFactory.eINSTANCE.createEReference();
        // We won't rely on what it's called, as we won't be
        // locating it by name (instead, we'll start from the parent class and
        // study the model to find its opposite).
        String oppFeatName = generateReferenceNameForOpposite(ref);
        oppRef.setName(oppFeatName);
        oppRef.setEType(ref.getEContainingClass());
        oppRef.setUpperBound(1);
        // This must be set to 0 to avoid the resulting
        // child-to-parent reference being declared 'NOT NULL'
        // in the database, which would prevent the child object existing
        // without the parent (or within a different type of parent).
        oppRef.setLowerBound(0);

        // Make the references each other's opposite
        ref.setEOpposite(oppRef);
        oppRef.setEOpposite(ref);

        // XMLSerializer treats as such anyway; This seem harmless
        // though.
        oppRef.setTransient(true);

        targetType.getEStructuralFeatures().add(oppRef);

        // Add the associated annotations
        AnnotationManager.addContainmentOppositesAnnotations(ref);
    }
}
