/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.bom.xsdtransform.dynamiceobjectreaders;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap.Entry;
import org.eclipse.emf.ecore.util.FeatureMapUtil.FeatureEList;
import org.eclipse.emf.ecore.xml.type.internal.QName;

/**
 * Reads feature values from a DynamicEObject representing an XML Schema
 * SimpleType.
 * 
 * @author rgreen
 * @since 19 Sep 2012
 */
public class DeoXsdSimpleTypeReader extends DeoXsdReader {

    private String name;

    private QName base;

    private DynamicEObjectImpl restriction;

    private String restrictionID;

    private String finalVal;

    private String union;

    private DynamicEObjectImpl topLevelAnnotation;

    private RestrictionFacets restrictionFacets;

    public DeoXsdSimpleTypeReader(DynamicEObjectImpl deo) {
        super(deo);
    }

    public String getName() {
        if (name == null) {
            Object feature = getFeature("name"); //$NON-NLS-1$
            if (feature instanceof String) {
                name = (String) feature;
            }
        }
        return name;
    }

    public String getID() {
        if (name == null) {
            Object feature = getFeature("id"); //$NON-NLS-1$
            if (feature instanceof String) {
                name = (String) feature;
            }
        }
        return name;
    }

    public String getFinal() {
        if (finalVal == null) {
            Object feature = getFeature("final"); //$NON-NLS-1$
            if (feature instanceof String) {
                finalVal = (String) feature;
            }
        }
        return finalVal;
    }

    public DynamicEObjectImpl getRestriction() {
        if (restriction == null) {
            Object feature = getFeature("restriction"); //$NON-NLS-1$
            if (feature instanceof DynamicEObjectImpl) {
                restriction = (DynamicEObjectImpl) feature;
            }
        }
        return restriction;
    }

    public String getUnion() {
        if (union == null) {
            Object feature = getFeature("union"); //$NON-NLS-1$
            if (feature instanceof DynamicEObjectImpl) {
                union = (String) feature;
            }
        }
        return union;
    }

    public QName getBaseType() {
        if (base == null) {
            DynamicEObjectImpl rest = getRestriction();

            if (rest != null) {
                Object feature = getFeature(rest, "base"); //$NON-NLS-1$
                if (feature instanceof QName) {
                    base = (QName) feature;
                }
            }
        }
        return base;
    }

    public String getRestrictionID() {
        if (restrictionID == null) {
            DynamicEObjectImpl rest = getRestriction();

            if (rest != null) {
                Object feature = getFeature(rest, "id"); //$NON-NLS-1$
                if (feature instanceof String) {
                    restrictionID = (String) feature;
                }
            }
        }
        return restrictionID;
    }

    public DynamicEObjectImpl getTopLevelAnnotation() {
        if (topLevelAnnotation == null) {

            Object feature = getFeature("annotation"); //$NON-NLS-1$
            if (feature instanceof DynamicEObjectImpl) {
                topLevelAnnotation = (DynamicEObjectImpl) feature;
                Object obj = getFeature(topLevelAnnotation, "documentation"); //$NON-NLS-1$

                if (obj instanceof FeatureEList<?>) {
                    FeatureEList<?> docEntries = (FeatureEList<?>) obj;
                    for (Object object : docEntries) {
                        if (object instanceof DynamicEObjectImpl) {
                            DynamicEObjectImpl entry =
                                    (DynamicEObjectImpl) object;
                            Object feature2 = getFeature(entry, "text"); //$NON-NLS-1$
                        }
                    }
                }
            }

        }
        return topLevelAnnotation;
    }

    /**
     * @see com.tibco.xpd.bom.xsdtransform.dynamiceobjectreaders.DeoXsdReader#getFeature(java.lang.String)
     * 
     *      Overidden because we need to use getEAllStructuralFeatures() instead
     *      of getEStructuralFeatures
     * 
     * @param featureName
     * @return
     */
    @Override
    protected Object getFeature(String featureName) {
        for (EStructuralFeature feat : getDeo().eClass()
                .getEAllStructuralFeatures()) {
            String name = feat.getName();
            if (name.equals(featureName)) {
                Object value = getDeo().eGet(feat);
                return value;
            }
        }
        return null;
    }

    protected Object getFeature(DynamicEObjectImpl deo, String featureName) {
        for (EStructuralFeature feat : deo.eClass().getEAllStructuralFeatures()) {
            String name = feat.getName();
            if (name.equals(featureName)) {
                Object value = deo.eGet(feat);
                return value;
            }
        }

        for (EStructuralFeature feat : deo.eClass().getEStructuralFeatures()) {
            String name = feat.getName();
            if (name.equals(featureName)) {
                Object value = deo.eGet(feat);
                return value;
            }
        }

        return null;
    }

    /**
     * 
     * 
     * @author rgreen
     * @since 1 Oct 2012
     */
    public class RestrictionFacets {

        private String annotation; // ??

        private String enumeration; // ??

        private String fractionDigits;

        private String length;

        private String maxInclusive;

        private String maxExclusive;

        private String maxLength;

        private String minInclusive;

        private String minExclusive;

        private String minLength;

        private String pattern;

        private DynamicEObjectImpl simpleType;

        private String totalDigits;

        private String whitespace;

        private DynamicEObjectImpl restrictionDeo;

        private String fractionDigitsId;

        private String lengthId;

        private String maxExclusiveId;

        private String maxInclusiveId;

        private String maxLengthId;

        private String minExclusiveId;

        private String minInclusiveId;

        private String minLengthId;

        private String patternId;

        private String totalDigitsId;

        private String whitespaceId;

        private String fractionDigitsFixed;

        private String lengthFixed;

        private String maxExclusiveFixed;

        private String maxInclusiveFixed;

        private String maxLengthFixed;

        private String minExclusiveFixed;

        private String minInclusiveFixed;

        private String minLengthFixed;

        private String patternFixed;

        private String totalDigitsFixed;

        private String whitespaceFixed;

        public RestrictionFacets(DynamicEObjectImpl deo) {
            restrictionDeo = deo;
            setValues();
        }

        /**
         * 
         */
        private void setValues() {
            Object featureFacets = getFeature(restrictionDeo, "facets"); //$NON-NLS-1$
            if (featureFacets != null) {
                if (featureFacets instanceof FeatureMap) {
                    FeatureMap map = (FeatureMap) featureFacets;

                    for (Entry entry : map) {
                        String featureName =
                                entry.getEStructuralFeature().getName();
                        if (entry.getValue() instanceof DynamicEObjectImpl) {
                            Object featureValue =
                                    getFeature((DynamicEObjectImpl) entry.getValue(),
                                            "value"); //$NON-NLS-1$
                            Object featureId =
                                    getFeature((DynamicEObjectImpl) entry.getValue(),
                                            "id"); //$NON-NLS-1$
                            Object featureFixed =
                                    getFeature((DynamicEObjectImpl) entry.getValue(),
                                            "fixed"); //$NON-NLS-1$

                            if (featureValue != null) {
                                if (featureName.equals("fractiondigits")) { //$NON-NLS-1$
                                    fractionDigits = (String) featureValue;
                                } else if (featureName.equals("length")) { //$NON-NLS-1$
                                    length = (String) featureValue;
                                } else if (featureName.equals("maxExclusive")) { //$NON-NLS-1$
                                    maxExclusive = (String) featureValue;
                                } else if (featureName.equals("maxInclusive")) { //$NON-NLS-1$
                                    maxInclusive = (String) featureValue;
                                } else if (featureName.equals("maxLength")) { //$NON-NLS-1$
                                    maxLength = (String) featureValue;
                                } else if (featureName.equals("minExclusive")) { //$NON-NLS-1$
                                    minExclusive = (String) featureValue;
                                } else if (featureName.equals("minInclusive")) { //$NON-NLS-1$
                                    minInclusive = (String) featureValue;
                                } else if (featureName.equals("minLength")) { //$NON-NLS-1$
                                    minLength = (String) featureValue;
                                } else if (featureName.equals("pattern")) { //$NON-NLS-1$
                                    pattern = (String) featureValue;
                                } else if (featureName.equals("totalDigits")) { //$NON-NLS-1$
                                    totalDigits = (String) featureValue;
                                } else if (featureName.equals("whitespace")) { //$NON-NLS-1$
                                    whitespace = (String) featureValue;
                                }
                            }

                            if (featureId != null) {
                                if (featureName.equals("fractiondigits")) { //$NON-NLS-1$
                                    fractionDigitsId = (String) featureId;
                                } else if (featureName.equals("length")) { //$NON-NLS-1$
                                    lengthId = (String) featureId;
                                } else if (featureName.equals("maxExclusive")) { //$NON-NLS-1$
                                    maxExclusiveId = (String) featureId;
                                } else if (featureName.equals("maxInclusive")) { //$NON-NLS-1$
                                    maxInclusiveId = (String) featureId;
                                } else if (featureName.equals("maxLength")) { //$NON-NLS-1$
                                    maxLengthId = (String) featureId;
                                } else if (featureName.equals("minExclusive")) { //$NON-NLS-1$
                                    minExclusiveId = (String) featureId;
                                } else if (featureName.equals("minInclusive")) { //$NON-NLS-1$
                                    minInclusiveId = (String) featureId;
                                } else if (featureName.equals("minLength")) { //$NON-NLS-1$
                                    minLengthId = (String) featureId;
                                } else if (featureName.equals("pattern")) { //$NON-NLS-1$
                                    patternId = (String) featureId;
                                } else if (featureName.equals("totalDigits")) { //$NON-NLS-1$
                                    totalDigitsId = (String) featureId;
                                } else if (featureName.equals("whitespace")) { //$NON-NLS-1$
                                    whitespaceId = (String) featureId;
                                }
                            }

                            if (featureFixed != null) {
                                Boolean isFixed = (Boolean) featureFixed;
                                if (featureName.equals("fractiondigits")) { //$NON-NLS-1$
                                    fractionDigitsFixed = isFixed.toString();
                                } else if (featureName.equals("length")) { //$NON-NLS-1$
                                    lengthFixed = isFixed.toString();
                                } else if (featureName.equals("maxExclusive")) { //$NON-NLS-1$
                                    maxExclusiveFixed = isFixed.toString();
                                } else if (featureName.equals("maxInclusive")) { //$NON-NLS-1$
                                    maxInclusiveFixed = isFixed.toString();
                                } else if (featureName.equals("maxLength")) { //$NON-NLS-1$
                                    maxLengthFixed = isFixed.toString();
                                } else if (featureName.equals("minExclusive")) { //$NON-NLS-1$
                                    minExclusiveFixed = isFixed.toString();
                                } else if (featureName.equals("minInclusive")) { //$NON-NLS-1$
                                    minInclusiveFixed = isFixed.toString();
                                } else if (featureName.equals("minLength")) { //$NON-NLS-1$
                                    minLengthFixed = isFixed.toString();
                                } else if (featureName.equals("pattern")) { //$NON-NLS-1$
                                    patternFixed = isFixed.toString();
                                } else if (featureName.equals("totalDigits")) { //$NON-NLS-1$
                                    totalDigitsFixed = isFixed.toString();
                                } else if (featureName.equals("whitespace")) { //$NON-NLS-1$
                                    whitespaceFixed = isFixed.toString();
                                }
                            }
                        }
                    }
                }
            }

        }

        /**
         * @return the annotation
         */
        public String getAnnotation() {
            // TODO: Retrieve Annotations
            Object feature = getFeature(restrictionDeo, "annotation"); //$NON-NLS-1$
            Object feature2 =
                    getFeature((DynamicEObjectImpl) feature, "documentation"); //$NON-NLS-1$

            if (feature2 instanceof FeatureEList) {
                FeatureEList<?> featMap = (FeatureEList<?>) feature2;
                for (Object object : featMap) {

                    if (object instanceof DynamicEObjectImpl) {
                        DynamicEObjectImpl d = (DynamicEObjectImpl) object;
                        getFeature(d, "text");
                        System.out.println("");
                    }
                }
            }

            return annotation;
        }

        /**
         * @return the enumeration
         */
        public String getEnumeration() {
            return enumeration;
        }

        /**
         * @return the fractionDigits
         */
        public String getFractionDigits() {
            return fractionDigits;
        }

        /**
         * @return the length
         */
        public String getLength() {
            return length;
        }

        /**
         * @return the maxInclusive
         */
        public String getMaxInclusive() {
            return maxInclusive;
        }

        /**
         * @return the maxExclusive
         */
        public String getMaxExclusive() {
            return maxExclusive;
        }

        /**
         * @return the maxLength
         */
        public String getMaxLength() {
            return maxLength;
        }

        /**
         * @return the minInclusive
         */
        public String getMinInclusive() {
            return minInclusive;
        }

        /**
         * @return the minExclusive
         */
        public String getMinExclusive() {
            return minExclusive;
        }

        /**
         * @return the minLength
         */
        public String getMinLength() {
            return minLength;
        }

        /**
         * @return the pattern
         */
        public String getPattern() {
            return pattern;
        }

        /**
         * @return the simpleType
         */
        public DynamicEObjectImpl getSimpleType() {
            return simpleType;
        }

        /**
         * @return the totalDigits
         */
        public String getTotalDigits() {
            return totalDigits;
        }

        /**
         * @return the whitespace
         */
        public String getWhitespace() {
            return whitespace;
        }

        /**
         * @return the fractionDigitsId
         */
        public String getFractionDigitsId() {
            return fractionDigitsId;
        }

        /**
         * @return the lengthId
         */
        public String getLengthId() {
            return lengthId;
        }

        /**
         * @return the maxExclusiveId
         */
        public String getMaxExclusiveId() {
            return maxExclusiveId;
        }

        /**
         * @return the maxInclusiveId
         */
        public String getMaxInclusiveId() {
            return maxInclusiveId;
        }

        /**
         * @return the maxLengthId
         */
        public String getMaxLengthId() {
            return maxLengthId;
        }

        /**
         * @return the minExclusiveId
         */
        public String getMinExclusiveId() {
            return minExclusiveId;
        }

        /**
         * @return the minInclusiveId
         */
        public String getMinInclusiveId() {
            return minInclusiveId;
        }

        /**
         * @return the minLengthId
         */
        public String getMinLengthId() {
            return minLengthId;
        }

        /**
         * @return the patternId
         */
        public String getPatternId() {
            return patternId;
        }

        /**
         * @return the totalDigitsId
         */
        public String getTotalDigitsId() {
            return totalDigitsId;
        }

        /**
         * @return the whitespaceId
         */
        public String getWhitespaceId() {
            return whitespaceId;
        }

        /**
         * @return the fractionDigitsFixed
         */
        public String getFractionDigitsFixed() {
            return fractionDigitsFixed;
        }

        /**
         * @return the lengthFixed
         */
        public String getLengthFixed() {
            return lengthFixed;
        }

        /**
         * @return the maxExclusiveFixed
         */
        public String getMaxExclusiveFixed() {
            return maxExclusiveFixed;
        }

        /**
         * @return the maxInclusiveFixed
         */
        public String getMaxInclusiveFixed() {
            return maxInclusiveFixed;
        }

        /**
         * @return the maxLengthFixed
         */
        public String getMaxLengthFixed() {
            return maxLengthFixed;
        }

        /**
         * @return the minExclusiveFixed
         */
        public String getMinExclusiveFixed() {
            return minExclusiveFixed;
        }

        /**
         * @return the minInclusiveFixed
         */
        public String getMinInclusiveFixed() {
            return minInclusiveFixed;
        }

        /**
         * @return the minLengthFixed
         */
        public String getMinLengthFixed() {
            return minLengthFixed;
        }

        /**
         * @return the patternFixed
         */
        public String getPatternFixed() {
            return patternFixed;
        }

        /**
         * @return the totalDigitsFixed
         */
        public String getTotalDigitsFixed() {
            return totalDigitsFixed;
        }

        /**
         * @return the whitespaceFixed
         */
        public String getWhitespaceFixed() {
            return whitespaceFixed;
        }

    }

    /**
     * @return the restrictionFacets
     */
    public RestrictionFacets getRestrictionFacets() {
        if (restrictionFacets == null) {
            restrictionFacets = new RestrictionFacets(getRestriction());
        }

        return restrictionFacets;
    }

}
