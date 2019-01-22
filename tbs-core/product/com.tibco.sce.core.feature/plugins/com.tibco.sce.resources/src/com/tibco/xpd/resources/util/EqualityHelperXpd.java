/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.resources.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMapUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.EqualityHelper;


/**
 * EcoreUtil has a very useful ability to do a (deep) compare of 2 EObject's.
 * This is done via {@link EqualityHelper}.
 * <p>
 * This class extends this ability by allowing the owner to...
 * <li>Specify features that should be ignored (i.e. unique id's etc)</li>
 * <li>Specify that sequences of children can be in any order (and how those
 * features can be sorted into an order before comparison
 * <li>Specify default values for EAttributes (simple type xml attribute /
 * element) when one is missing.</li>
 * 
 * 
 * @author aallway
 * @since 3.2
 */
public class EqualityHelperXpd extends EqualityHelper {

	/*
	 * Jan: There are problems during PDMS build if: 
	 * 
	 * org.eclipse.emf.ecore.util.FeatureMap.Entry
	 *  
	 * is imported (as it probably collides with an internal Map's Entry class) 
	 * so the FQN is used in all places. Please leave it this way! 
	 */
    private static final long serialVersionUID = 1L;

    private Set<Object> featuresToIgnore = new HashSet<Object>();

    private Map<EReference, Comparator<EObject>> eobjectListComparators =
            new HashMap<EReference, Comparator<EObject>>();

    private Map<EStructuralFeature, Object> eAttributeDefaultValues =
            new HashMap<EStructuralFeature, Object>();

    private boolean featureMapOrderSignificant = false;

    private boolean consoleLoggingOn = false;

    private Comparator<org.eclipse.emf.ecore.util.FeatureMap.Entry> featureMapEntryByNameComparator =
            new Comparator<org.eclipse.emf.ecore.util.FeatureMap.Entry>() {
                public int compare(org.eclipse.emf.ecore.util.FeatureMap.Entry o1, org.eclipse.emf.ecore.util.FeatureMap.Entry o2) {
                    return o1.getEStructuralFeature().getName().compareTo(o2
                            .getEStructuralFeature().getName());
                }
            };

    /**
     * By default, this class will treat the order of two child feature maps
     * (##other / ##any lists) as insignificant in terms of comparison. The
     * feature map children will be pre-sorted by their feature names.
     * <p>
     * You can switch this behaviour off/on with this method. When off,
     * equivalent features must appear in the same sequence for the feature maps
     * to be equivalent).
     * 
     * @param featureMapOrderSignificant
     */
    public void setFeatureMapOrderSignificant(boolean featureMapOrderSignificant) {
        this.featureMapOrderSignificant = featureMapOrderSignificant;
    }

    /**
     * Add the given feature name as one that should be ignored during
     * comparisons.
     * 
     * @param featureName
     */
    public void addFeatureToIgnore(String featureName) {
        featuresToIgnore.add(featureName);
    }

    /**
     * Add the given feature as one that should be ignored during comparisons.
     * 
     * @param feature
     */
    public void addFeatureToIgnore(EStructuralFeature feature) {
        featuresToIgnore.add(feature);
    }

    /**
     * Remove the given feature name from the list of features that should be
     * ignored.
     * 
     * @param featureName
     */
    public void removeFeatureToIgnore(String featureName) {
        featuresToIgnore.remove(featureName);
    }

    /**
     * Remove the given feature name from the list of features that should be
     * ignored.
     * 
     * @param feature
     */
    public void removeFeatureToIgnore(EStructuralFeature feature) {
        featuresToIgnore.remove(feature);
    }

    /**
     * Add a default value for use in comparisons of missing xml attributes /
     * simple type elements.
     * 
     * @param feature
     * @param defaultValue
     */
    public void addDefaultValue(EStructuralFeature feature, Object defaultValue) {
        eAttributeDefaultValues.put(feature, defaultValue);
    }

    /**
     * Remove a previously added default value for attribute
     * 
     * @param feature
     * @param defaultValue
     */
    public void removeEAttributeDefaultValue(EStructuralFeature feature,
            Object defaultValue) {
        eAttributeDefaultValues.put(feature, defaultValue);
    }

    /**
     * Nominally a child sequence of EObjects in two compared EObjects is
     * significant.
     * <p>
     * This behaviour can be overridden using this method. By setting a
     * comparator for 2 objects in a sequence you can determine their simple
     * equivalence for being fully compared with each other.
     * <p>
     * For instance, if a sequence contains Element's that have a Name attribute
     * then you can specify a comparator that compares the Name attribute of 2
     * Element's. When this comparator returns true then a deep comparison is
     * performed on the 2 objects.
     * <p>
     * <i>Note: In actuality, the comparator is used to presort the 2 sequences
     * before comparing the each element in sequence 1 with the same indexed
     * item in sequence 2.</i>
     * 
     * @param eReference
     * @param comparator
     */
    public void addEObjectSequenceComparator(EReference eReference,
            Comparator<EObject> comparator) {
        eobjectListComparators.put(eReference, comparator);
    }

    /**
     * Remove the EObject sequence comparator (the sequence indicated by
     * eReference will go back to being order-significant).
     * 
     * @param eReference
     * @param comparator
     */
    public void removeEObjectSequenceComparator(EReference eReference,
            Comparator<EObject> comparator) {
        eobjectListComparators.remove(eReference);
    }

    @Override
    public boolean equals(EObject object1, EObject object2) {
        boolean ret = super.equals(object1, object2);
        if (!ret) {
            consoleLog("equals() MISMATCH: (" + object1 + ") (" + object2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        }
        return ret;
    }

    @Override
    public boolean equals(List<EObject> list1, List<EObject> list2) {
        boolean ret = super.equals(list1, list2);
        if (!ret) {
            consoleLog("equals(List,List) MISMATCH: (" + list1 + ") (" + list1 //$NON-NLS-1$ //$NON-NLS-2$
                    + ")"); //$NON-NLS-1$
        }
        return ret;
    }

    @Override
    protected boolean haveEqualFeature(EObject object1, EObject object2,
            EStructuralFeature feature) {
        boolean ret = internalHaveEqualFeature(object1, object2, feature);
        if (!ret) {
            consoleLog("haveEqualFeature(): MISMATCH: " + feature.getName()); //$NON-NLS-1$
        }
        return ret;
    }

    protected boolean internalHaveEqualFeature(EObject object1,
            EObject object2, EStructuralFeature feature) {
        consoleLog("internalHaveEqualFeature(): MISMATCH FEATURE: " //$NON-NLS-1$
                + feature.getName());
        consoleLog("internalHaveEqualFeature(): object1: " + object1); //$NON-NLS-1$
        consoleLog("internalHaveEqualFeature(): object2: " + object2); //$NON-NLS-1$
        //
        // Override standard class - check whether we've been asked to ignore
        // this feature (or features with this name.
        if (isIgnoredStructuralFeature(feature)) {
            // We've been asked to ignore this type or type name. Just tell the
            // super they're the same.
            return true;
        }

        //
        // Check if we need to use our special override behaviour for giving
        // default values for simple type elements/attributes that are
        // missing.
        // If not we'll use the super's method direct (then we pick up
        // benefits
        // of later version unless we want to do ourt special stuff.

        // Can't do it defaulting for feature map.
        if (!FeatureMapUtil.isFeatureMap(feature)) {

            // If either value if undefiend we need to check for default
            // values
            // supplied.
            boolean val1IsSet = object1.eIsSet(feature);
            boolean val2IsSet = object2.eIsSet(feature);

            if (!val1IsSet || !val2IsSet) {
                Object defaultVal = eAttributeDefaultValues.get(feature);
                if (defaultVal != null) {
                    Object val1 = object1.eGet(feature);
                    if (!val1IsSet || val1 == null) {
                        val1 = defaultVal;
                    }

                    Object val2 = object2.eGet(feature);
                    if (!val2IsSet || val2 == null) {
                        val2 = defaultVal;
                    }

                    boolean ret = false;
                    if (val1 instanceof EObject && val2 instanceof EObject) {
                        ret = this.equals((EObject) val1, (EObject) val2);
                    } else {
                        ret = val1.equals(val2);
                    }

                    // Remove default value from the underlying hashmap because
                    // we don't want to treat it like it's been chekced already.
                    remove(defaultVal);

                    return ret;
                }
            }
        } else if (eAttributeDefaultValues.containsKey(feature)) {
            // 
            // Handle default values for feature maps.
            Object defValue = eAttributeDefaultValues.get(feature);

            int size1 = 1;
            Object val1 = null;

            FeatureMap fm1 = (FeatureMap) object1.eGet(feature);
            if (fm1 == null || fm1.isEmpty()) {
                val1 = defValue;
            } else {
                size1 = fm1.size();
                val1 = fm1.getValue(0);
            }

            int size2 = 1;
            Object val2 = null;

            FeatureMap fm2 = (FeatureMap) object2.eGet(feature);
            if (fm2 == null || fm2.isEmpty()) {
                val2 = defValue;
            } else {
                size2 = fm2.size();
                val2 = fm2.getValue(0);
            }

            // Do the comparison same number of items and same value.
            boolean ret = false;
            if (size1 == size2) {
                ret = (val1 == null ? val2 == null : val1.equals(val2));
            }

            // Remove default value from the underlying hashmap because
            // we don't want to treat it like it's been chekced already.
            remove(defValue);

            return ret;
        }

        return super.haveEqualFeature(object1, object2, feature);
    }

    @Override
    protected boolean equalFeatureMaps(FeatureMap featureMap1,
            FeatureMap featureMap2) {
        boolean ret = internalEqualFeatureMaps(featureMap1, featureMap2);

        if (!ret) {
            consoleLog("equalFeatureMaps(): MISMATCH"); //$NON-NLS-1$
        }
        return ret;
    }

    protected boolean internalEqualFeatureMaps(FeatureMap featureMap1,
            FeatureMap featureMap2) {
        //
        // We want the order of elements in th ##any / ##other list to be
        // insignificant. So we will sort the 2 maps on their feature names.
        //

        // Create copies (without the features we've been asked to ignore) of
        // the 2 maps.
        List<org.eclipse.emf.ecore.util.FeatureMap.Entry> fm1 = new ArrayList<org.eclipse.emf.ecore.util.FeatureMap.Entry>(featureMap1.size());
        for (org.eclipse.emf.ecore.util.FeatureMap.Entry e : featureMap1) {
            if (!isIgnoredStructuralFeature(e.getEStructuralFeature())) {
                fm1.add(e);
            }
        }

        List<org.eclipse.emf.ecore.util.FeatureMap.Entry> fm2 = new ArrayList<org.eclipse.emf.ecore.util.FeatureMap.Entry>(featureMap2.size());
        for (org.eclipse.emf.ecore.util.FeatureMap.Entry e : featureMap2) {
            if (!isIgnoredStructuralFeature(e.getEStructuralFeature())) {
                fm2.add(e);
            }
        }

        // Quick compare of list size first...
        int size = fm1.size();
        if (size != fm2.size()) {
            // Can't possibly be the same.
            return false;
        }

        // 
        // Now sort them into structural feature name order and then we can
        // compare them...
        if (!featureMapOrderSignificant) {
            Collections.sort(fm1, featureMapEntryByNameComparator);
            Collections.sort(fm2, featureMapEntryByNameComparator);
        }

        for (int i = 0; i < size; i++) {
            // If entries don't have the same feature, the feature maps aren't
            // equal.
            org.eclipse.emf.ecore.util.FeatureMap.Entry e1 = fm1.get(i);
            org.eclipse.emf.ecore.util.FeatureMap.Entry e2 = fm2.get(i);

            EStructuralFeature feature = e1.getEStructuralFeature();
            if (feature != e2.getEStructuralFeature()) {
                return false;
            }

            // Equivalent feature - check types.
            Object value1 = e1.getValue();
            Object value2 = e2.getValue();

            if (!equalFeatureMapValues(value1, value2, feature)) {
                consoleLog("internalEqualFeatureMaps( mapfeaturevalue ) MISMATCH: " //$NON-NLS-1$
                        + feature.getName());
                return false;
            }
        }

        // 
        // There is no reason they aren't equals.
        return true;

    }

    @Override
    protected boolean equalFeatureMapValues(Object value1, Object value2,
            EStructuralFeature feature) {
        //
        // Override default behaviour for attrs and simple type elements to
        // provide default values for missing elements if necessary.
        Object defaultVal = null;
        if (value1 == null) {
            defaultVal = eAttributeDefaultValues.get((EAttribute) feature);
            if (defaultVal != null) {
                value1 = defaultVal;
            }
        }

        if (value2 == null) {
            defaultVal = eAttributeDefaultValues.get((EAttribute) feature);
            if (defaultVal != null) {
                value2 = defaultVal;
            }
        }

        boolean ret = super.equalFeatureMapValues(value1, value2, feature);

        // Remove default value from the underlying hashmap because
        // we don't want to treat it like it's been checked already.
        if (defaultVal != null) {
            remove(defaultVal);
        }

        return ret;

    }

    @Override
    protected boolean haveEqualReference(EObject object1, EObject object2,
            EReference reference) {
        if (!reference.isMany()
                || !eobjectListComparators.containsKey(reference)) {
            // Let super deal with non sequences and sequences that we don't
            // have a sort comparator for.
            return super.haveEqualReference(object1, object2, reference);
        }

        //
        // This reference is a sequence that this class's creator has specified
        // that the sequence order is insignificant (by providing a means to
        // pre-sort the lists.
        List<EObject> list1 = (List<EObject>) object1.eGet(reference);
        List<EObject> list2 = (List<EObject>) object2.eGet(reference);

        // No checking whether we're ignoring each inidividual member because
        // their structural feature will ALWAYS be that of the reference.
        // (i.e. currently you cqan only set to ignore a whole sequence).

        // Check the quick things first.
        if (list1 == null) {
            return (list2 == null);
        } else if (list2 == null) {
            return (list1 == null);
        }

        if (list1.size() != list2.size()) {
            return false;
        }

        // Get the comparator that we will use to order the list according to
        // the creators idea of which elements should be treated as equal enough
        // to compare properly.
        Comparator<EObject> comparator = eobjectListComparators.get(reference);

        List<EObject> l1 = new ArrayList<EObject>(list1);
        List<EObject> l2 = new ArrayList<EObject>(list1);

        Collections.sort(l1, comparator);
        Collections.sort(l2, comparator);

        // Then compare as usual.
        return equals(l1, l2);
    }

    public void setConsoleLoggingOn(boolean consoleLoggingOn) {
        this.consoleLoggingOn = consoleLoggingOn;
    }

    private void consoleLog(String msg) {
        if (consoleLoggingOn) {
            System.err.println(this.getClass().getSimpleName() + "." + msg); //$NON-NLS-1$
        }
    }

    private boolean isIgnoredStructuralFeature(EStructuralFeature feature) {
        if (featuresToIgnore.contains(feature)
                || featuresToIgnore.contains(feature.getName())) {
            return true;
        }
        return false;
    }

    /**
     * Compares to {@link List}s irrespective of the order of the contents.
     * 
     * @param <T>
     * @param originalList1
     * @param originalList2
     * @param comparator
     * @return
     */
    public <T extends EObject> boolean equals(List<T> originalList1,
            List<T> originalList2, Comparator<? super T> comparator) {

        consoleLog("equals:: compare lists with comparator as parameter"); //$NON-NLS-1$
        List<T> list1 = new ArrayList<T>(originalList1);
        List<T> list2 = new ArrayList<T>(originalList2);
        Collections.sort(list1, comparator);
        Collections.sort(list2, comparator);
        boolean eq = equals((List<EObject>) list1, (List<EObject>) list2);
        consoleLog("equals::Lists are equal" + eq); //$NON-NLS-1$
        return eq;
    }
}
