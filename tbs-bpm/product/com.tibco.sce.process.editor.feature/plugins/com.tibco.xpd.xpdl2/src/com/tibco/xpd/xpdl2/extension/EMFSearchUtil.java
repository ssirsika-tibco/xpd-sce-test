/**
 * 
 */
package com.tibco.xpd.xpdl2.extension;

import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Utility class for searching EMF models
 * 
 * @author wzurek
 */
public class EMFSearchUtil {

    /**
     * Return first element on the list that has given value on given feature
     * 
     * @param list
     *            list with objects that have structural feature 'attrib'
     * @param attrib
     *            structural feature that will be tested
     * @param value
     *            required value of the structural feature
     * @return first element that meets criteria or null
     */
    public static EObject findInList(List list, EStructuralFeature attrib,
            Object value) {
        if (!list.isEmpty() && value != null) {
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                EObject obj = (EObject) iter.next();
                Object cVal = obj.eGet(attrib);
                if (value.equals(cVal)) {
                    return obj;
                }
            }
        }
        return null;
    }

    /**
     * Return all elements on the list that has given value on given feature
     * 
     * @param list
     *            list with objects that have structural feature 'attrib'
     * @param attrib
     *            structural feature that will be tested
     * @param value
     *            required value of the structural feature
     * @return first element that meets criteria or null
     */
    public static EList findManyInList(List list, EStructuralFeature attrib,
            Object value) {
        EList result = new BasicEList();
        if (!list.isEmpty()) {
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                EObject obj = (EObject) iter.next();
                Object cVal = obj.eGet(attrib);
                if (value.equals(cVal)) {
                    result.add(obj);
                }
            }
        }
        return result;
    }
}
