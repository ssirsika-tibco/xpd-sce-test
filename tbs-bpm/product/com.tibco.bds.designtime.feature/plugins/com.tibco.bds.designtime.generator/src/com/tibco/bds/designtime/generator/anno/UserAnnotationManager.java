/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.bds.designtime.generator.anno;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

import com.tibco.bds.shared.anno.JPAAnnotation;
import com.tibco.bds.shared.anno.JPAAnnotationValue;
import com.tibco.bds.shared.anno.JPAAnnotations;

/**
 * Class that handles reading in all the Annotations added by a profile imported
 * into the BOM
 * 
 */
public class UserAnnotationManager {

    private static String PROFILE_TYPE_ANNO = "ProfileType";

    private static String PROFILE_TYPE_NAME = "name";

    private static String PROFILE_TYPE_VALUE_BDS = "bds";

    private static String PROFILE_TYPE_VALUE_HIBERNATE = "hibernate";

    private static String PROFILE_TYPE_VALUE_JPA = "jpa";

    /**
     * Check for any BDS User Annotations
     * 
     * @param umlElement
     *            Base element to check
     * @return BDS Annotations
     */
    static public JPAAnnotations getBdsUserAnnotations(
            org.eclipse.uml2.uml.Element umlElement) {
        return processUserAnnotations(umlElement,
                new String[] { PROFILE_TYPE_VALUE_BDS });
    }

    /**
     * Check for any User defined override annotations
     * 
     * @param umlElement
     *            Base element to check
     * @return User Annotations
     */
    static public JPAAnnotations getUserAnnotations(
            org.eclipse.uml2.uml.Element umlElement) {
        return processUserAnnotations(umlElement, new String[] {
                PROFILE_TYPE_VALUE_HIBERNATE, PROFILE_TYPE_VALUE_JPA });
    }

    /**
     * Checks a given UML element to see if there were any custom user
     * annotations added to it, then generates the set of annotations to add to
     * the eCore
     * 
     * These annotations are to enable the user to override any of the default
     * teneo.jpa annotations or enhance behaviour. It is not the intension that
     * the user will set any of these without the instruction from product
     * support
     * 
     * @param umlElement
     *            UML element to check for annotation
     * @return User Annotations
     */
    static private JPAAnnotations processUserAnnotations(
            org.eclipse.uml2.uml.Element umlElement, String[] userAnnoTypes) {

        // Master list of user annotations
        JPAAnnotations userAnnotations = new JPAAnnotations();

        // If element is a Property and has an associated Association, inherit
        // its annotations too.
        if (umlElement instanceof Property) {
            Property umlProp = (Property) umlElement;
            if (umlProp.getAssociation() != null) {
                JPAAnnotations assocAnnos =
                        processUserAnnotations(umlProp.getAssociation(),
                                userAnnoTypes);
                if (assocAnnos != null) {
                    userAnnotations.mergeAnnotations(assocAnnos);
                }
            }
        }

        EList<Stereotype> appliedStereotypes =
                umlElement.getAppliedStereotypes();
        // Nothing to do if there are no stereo types specified
        if (appliedStereotypes == null) {
            return null;
        }

        // Check each of the stereo types to check for JPA Profiles
        for (Stereotype stereotype : appliedStereotypes) {
            Profile profile = stereotype.getProfile();
            // Use the qualified name of the profile to check that it is
            // the profile that adds user set annotation, if it is not,
            // skip it and check the next one
            if (profile == null) {
                continue;
            }

            boolean isBdsProfile = false;
            EList<EAnnotation> annotations = profile.getEAnnotations();
            for (EAnnotation annotation : annotations) {
                if (annotation.getSource()
                        .compareToIgnoreCase(PROFILE_TYPE_ANNO) == 0) {
                    EMap<String, String> details = annotation.getDetails();
                    if (details.containsKey(PROFILE_TYPE_NAME)) {
                        String value = details.get(PROFILE_TYPE_NAME);
                        for (String annoType : userAnnoTypes) {
                            if (value.compareToIgnoreCase(annoType) == 0) {
                                isBdsProfile = true;
                                break;
                            }
                        }
                    }
                }
            }

            if (!isBdsProfile) {
                continue;
            }

            // It is the correct type of stereo type so create an
            // annotation with the stereo types name
            JPAAnnotation userAnno = new JPAAnnotation(stereotype.getName());

            // Check each of the properties that are on the stereo type
            // to see if they are set, and if so, store the data set
            for (Property attrib : stereotype.getAllAttributes()) {
                String name = attrib.getName();
                // Only process named attributes of primitive types
                if ((attrib.getType() instanceof PrimitiveType)
                        && (name != null) && !name.isEmpty()) {
                    // Need to retrieve the value from the core stereo type
                    // for this attribute
                    Object value =
                            umlElement.getValue(stereotype, attrib.getName());
                    // Only do something with this attribute if it has actually
                    // been set to a value
                    if (value != null) {
                        boolean isQuotedValue = false;
                        if (value instanceof String) {
                            // Strip any quotes from the string, this is because
                            // the interface to set the value is not very clear
                            // if the user needs to specify the quotes for
                            // strings or not, so to be safe - we allow both
                            // methods and strip any quotes if they are left in
                            value =
                                    ((String) value).trim()
                                            .replaceAll("\"", "");
                            if (((String) value).isEmpty()) {
                                continue;
                            }
                            isQuotedValue = true;
                        }
                        // Has both a name and value so add it to the
                        // annotation
                        userAnno.put(name, new JPAAnnotationValue(value,
                                isQuotedValue));
                    }
                }
            }

            boolean addAnnotation = true;
            if (userAnno.isEmpty()) {
                // No value specified - so just a stand-alone tag, check to see
                // if the only attributes are non Primitive types, if so, we can
                // just add the tag without any data, this will be the
                // difference between not setting any data on an annotation, or
                // having an annotation that does not have any data to set
                for (Property attrib : stereotype.getAllAttributes()) {
                    if (attrib.getType() instanceof PrimitiveType) {
                        addAnnotation = false;
                        break;
                    }
                }
            }

            // Check to see if any values were added to this annotation
            // and if they were then we need to add it to the main set
            if (addAnnotation) {
                userAnnotations.mergeAnnotation(userAnno);
            }
        }

        return userAnnotations;
    }
}
