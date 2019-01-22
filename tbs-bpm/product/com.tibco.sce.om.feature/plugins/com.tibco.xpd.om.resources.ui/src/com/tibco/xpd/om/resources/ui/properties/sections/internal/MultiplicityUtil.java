/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.internal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.om.core.om.MultipleFeature;
import com.tibco.xpd.om.core.om.OMPackage;

/**
 * Provides util methods to read/set multiplicity value for the
 * {@link MultipleFeature} objects.
 * 
 * @author njpatel
 * 
 */
public final class MultiplicityUtil {

    // Multiplicity value,e.g. 12, 0..*, 1..3 etc
    private static final Pattern entryPattern = Pattern
            .compile("\\*|\\d+(\\.\\.(\\*|\\d+)?)?"); //$NON-NLS-1$

    /**
     * Get the multiplicity of this feature in string form.
     * 
     * @param feature
     * @return multiplicity string or <code>null</code> if not valid or set
     */
    public static String getText(MultipleFeature feature) {
        String value = null;

        if (feature != null) {
            int lowerBound = feature.getLowerBound();
            int upperBound = feature.getUpperBound();

            if (lowerBound == upperBound) {
                value = String.valueOf(lowerBound);
            } else if (upperBound > 0) {
                value = String.format("%d..%d", lowerBound, upperBound); //$NON-NLS-1$
            } else {
                value = String.format("%d..*", lowerBound); //$NON-NLS-1$
            }
        }

        return value;
    }

    /**
     * Get the set multiplicity command for the given feature.
     * 
     * @param domain
     *            editing domain
     * @param feature
     *            feature to update
     * @param value
     *            multiplicity value - cannot be <code>null</code> or empty.
     * @return {@link Command} if the value is valid, otherwise
     *         <code>null</code> will be returned
     */
    public static Command createSetMultiplicityCommand(EditingDomain domain,
            MultipleFeature feature, String value) {
        if (feature != null && value != null && value.length() > 0) {
            int lowerValue = 0;
            int upperValue = 1;

            // Remove any spaces from the value
            value = value.trim();
            value = value.replace(" ", ""); //$NON-NLS-1$ //$NON-NLS-2$

            Matcher matcher = entryPattern.matcher(value);
            try {
                if (matcher.matches()) {
                    if (value.startsWith("*")) { //$NON-NLS-1$
                        lowerValue = 0;
                        upperValue = -1;
                    } else if (value.contains("..")) { //$NON-NLS-1$
                        String[] split = value.split("\\.\\."); //$NON-NLS-1$

                        lowerValue = Integer.parseInt(split[0]);

                        if (split.length > 1) {
                            upperValue = split[1].equals("*") | split[1].equals("") ? -1 //$NON-NLS-1$ //$NON-NLS-2$
                                    : Integer.parseInt(split[1]);
                        } else {
                            upperValue = -1;
                        }
                    } else {
                        // This must be a single number
                        lowerValue = upperValue = Integer.parseInt(value);
                    }

                    if (upperValue >= lowerValue || upperValue == -1) {
                        CompoundCommand cmd = new CompoundCommand();

                        if (feature.getLowerBound() != lowerValue) {
                            cmd.append(SetCommand.create(domain, feature,
                                    OMPackage.eINSTANCE
                                            .getMultipleFeature_LowerBound(),
                                    lowerValue));
                        }

                        if (feature.getUpperBound() != upperValue) {
                            cmd.append(SetCommand.create(domain, feature,
                                    OMPackage.eINSTANCE
                                            .getMultipleFeature_UpperBound(),
                                    upperValue));
                        }
                        if (!cmd.isEmpty()) {
                            return cmd;
                        }
                    }
                }
            } catch (NumberFormatException e) {
                // Do nothing - return null command as the string is invalid
            }
        }
        return null;
    }
}
