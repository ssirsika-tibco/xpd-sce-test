package com.tibco.xpd.bom.globaldata.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager.StereotypeKind;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;

/**
 * Utility class to aid in the management of the summary annotation on a Case
 * Class
 * 
 */
public class SummaryInfoUtils {

    private static final String SEP_VAL = " ";

    /**
     * Get all the values that make up the summary in an array format
     * 
     * Note: If the annotation is empty then it will return the "default values"
     * 
     * @param caseClass
     * @return
     */
    public static List<String> getSummaryArray(Class caseClass) {
        // Get the current summary
        String summaryValue = getSummaryAnnotationValue(caseClass);

        // If there are no entries yet, use the default ones
        if (summaryValue.isEmpty()) {
            summaryValue = getDefaultSummaryValues(caseClass);
        }

        List<String> summaryValues = new ArrayList<String>();
        if (summaryValue != null) {
            summaryValues.addAll(Arrays.asList(summaryValue.split(SEP_VAL)));
        }
        return summaryValues;
    }

    /**
     * Gets the value of the summary annotation
     * 
     * @param caseClass
     * @return
     */
    public static String getSummaryAnnotationValue(Class caseClass) {
        // Get the current value from the stereotype of the Case Class
        final Stereotype stereotype =
                GlobalDataProfileManager.getInstance()
                        .getStereotype(StereotypeKind.CASE);
        // return if Stereotype is not found
        if (stereotype == null) {
            return "";
        }

        // Get the existing stereotype
        final Stereotype appliedStereo =
                caseClass.getAppliedStereotype(stereotype.getQualifiedName());

        // Calculate the existing value
        String existingValueString = "";
        if (appliedStereo != null) {
            // Check to see if there is a summary attribute present
            if (caseClass
                    .hasValue(stereotype,
                            BOMResourcesPlugin.ModelGlobalDataProfile_CaseClass_Summary)) {
                Object value =
                        caseClass
                                .getValue(stereotype,
                                        BOMResourcesPlugin.ModelGlobalDataProfile_CaseClass_Summary);
                if (value instanceof String) {
                    existingValueString = (String) value;
                }
            }
        }

        return existingValueString;
    }

    private static void setSummaryValue(Class caseClass, String value) {
        // Get the current value from the stereotype of the Case Class
        // get Stereotype for Fetching Batch Size
        final Stereotype stereotype =
                GlobalDataProfileManager.getInstance()
                        .getStereotype(StereotypeKind.CASE);
        // return if Stereotype is not found
        if (stereotype == null) {
            return;
        }
        // Get the existing stereotype
        final Stereotype appliedStereo =
                caseClass.getAppliedStereotype(stereotype.getQualifiedName());

        if (appliedStereo == null) {
            caseClass.applyStereotype(stereotype);
        }
        // Check if the stereotype property needs to be removed
        caseClass.setValue(stereotype,
                BOMResourcesPlugin.ModelGlobalDataProfile_CaseClass_Summary,
                value.trim());
    }

    public static void setSummaryValue(Class caseClass,
            List<String> summaryValues) {
        // Convert the array into a string
        StringBuilder strBld = new StringBuilder();
        for (String item : summaryValues) {
            if (strBld.length() > 0) {
                strBld.append(SEP_VAL);
            }
            strBld.append(item);
        }

        setSummaryValue(caseClass, strBld.toString());
    }

    public static void addSummaryValue(Class caseClass, String value) {
        // Get the current summary
        List<String> summaryValues = getSummaryArray(caseClass);

        // Check that the value is not already in the list
        if (summaryValues.contains(value)) {
            // Already in the list, nothing to do
            return;
        }

        // Add the new addition
        summaryValues.add(value);

        setSummaryValue(caseClass, summaryValues);
    }

    public static void removeSummaryValue(Class caseClass, String value) {
        // Get the current summary
        List<String> summaryValues = getSummaryArray(caseClass);

        // Convert the array into a string, removing the item we do not want
        StringBuilder strBld = new StringBuilder();
        for (String item : summaryValues) {
            // Skip the item we are removing
            if (item.equals(value)) {
                continue;
            }
            if (strBld.length() > 0) {
                strBld.append(SEP_VAL);
            }
            strBld.append(item);
        }

        setSummaryValue(caseClass, strBld.toString());
    }

    public static void moveUp(Class caseClass, String value) {
        // Get the current summary
        List<String> summaryValues = getSummaryArray(caseClass);

        // Now get the position of the item, and move it up
        int origIdx = summaryValues.indexOf(value);
        if (origIdx > 0) {
            summaryValues.remove(origIdx);
            summaryValues.add(origIdx - 1, value);
        }

        setSummaryValue(caseClass, summaryValues);
    }

    public static void moveDown(Class caseClass, String value) {
        // Get the current summary
        List<String> summaryValues = getSummaryArray(caseClass);

        // Now get the position of the item, and move it up
        int origIdx = summaryValues.indexOf(value);
        if ((origIdx > -1) && (origIdx + 1 < summaryValues.size())) {
            summaryValues.remove(origIdx);
            summaryValues.add(origIdx + 1, value);
        }

        setSummaryValue(caseClass, summaryValues);
    }

    private static String getDefaultSummaryValues(Class caseClass) {
        StringBuilder strBld = new StringBuilder();

        // Now make sure that the required attributes such as the
        // case identifier and the case state are included in the
        // summary information
        for (Property prop : caseClass.getAllAttributes()) {
            if (GlobalDataProfileManager.getInstance()
                    .isAutoCaseIdentifier(prop)
                    || GlobalDataProfileManager.getInstance().isCID(prop)
                    || GlobalDataProfileManager.getInstance()
                            .isCompositeCaseIdentifier(prop)
                    || GlobalDataProfileManager.getInstance().isCaseState(prop)) {
                // Make sure that this property is in the list
                if (strBld.length() > 0) {
                    strBld.append(SEP_VAL);
                }
                strBld.append(prop.getName());
            }
        }
        return strBld.toString();
    }

    public static List<String> getDefaultSummaryArray(Class caseClass) {
        String defaultSummary = getDefaultSummaryValues(caseClass);
        if (defaultSummary.isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.asList(defaultSummary.split(SEP_VAL));
    }

    /**
     * Renames a value already in the list, if it does not exist, it will add it
     * 
     * @param caseClass
     *            Class to update the annotation of
     * @param newValue
     *            The new value to add
     * @param oldValue
     *            The value that is getting renamed
     */
    public static void renameSummaryValue(Class caseClass, String newValue,
            String oldValue) {
        // Get the current summary
        List<String> summaryValues = getSummaryArray(caseClass);

        // Check if the value is already in the list
        if (summaryValues.contains(oldValue)) {
            // Already in the list, so rename it
            int indexOf = summaryValues.indexOf(oldValue);
            if (indexOf >= 0) {
                summaryValues.set(indexOf, newValue);
            }
        } else {
            // Add the new addition
            summaryValues.add(newValue);
        }

        setSummaryValue(caseClass, summaryValues);
    }

    /**
     * Checks if the property is a summary attribute.
     * 
     * @param property
     *            the property to check.
     * @return <code>true</code> if the property is summary.
     */
    public static boolean isSummary(Property property) {
        // Get the current summary values
        Class ownerClass = (Class) property.getOwner();
        List<String> summaryValues = getSummaryArray(ownerClass);
        return summaryValues.contains(property.getName());
    }

    /**
     * Sets the property to be a summary property.
     * 
     * @param property
     *            the property to set summary.
     * @param isSummary
     *            the value of summary.
     */
    public static void setSummary(Property property, boolean isSummary) {
        // Get the current summary
        Class ownerClass = (Class) property.getOwner();
        String propertyName = property.getName();
        List<String> summaryValues = getSummaryArray(ownerClass);

        // Check that the value is not already in the list and we want to set
        // it or is not in the list and we want to unset it.
        if (isSummary && !summaryValues.contains(propertyName)) {
            // Add the name if not already in the list.
            summaryValues.add(propertyName);
            setSummaryValue(ownerClass, summaryValues);
        } else if (!isSummary && summaryValues.contains(propertyName)) {
            // Remove the name if in the list.
            summaryValues.remove(propertyName);
            setSummaryValue(ownerClass, summaryValues);
        }

    }
}
