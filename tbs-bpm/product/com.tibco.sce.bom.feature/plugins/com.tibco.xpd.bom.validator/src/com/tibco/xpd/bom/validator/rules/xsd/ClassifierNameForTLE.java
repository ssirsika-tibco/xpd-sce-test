/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.validator.rules.xsd;

import org.eclipse.uml2.uml.Classifier;

import com.tibco.xpd.bom.resources.utils.BOMProfileUtils;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * 
 * Rule that validates the name of a Classifier within a user-defined BOM so
 * that it does not end with the string "Element". This is because XSD Top Level
 * Elements generated from Classifiers have names generated that end in
 * "Element".
 * 
 * @author rgreen
 * 
 */
public class ClassifierNameForTLE implements IValidationRule {

    private static final String XSD_CLASSIFIER_TLENAME_ISSUE =
            "xsd.classifierTLEname.issue";

    @Override
    public Class<?> getTargetClass() {

        return Classifier.class;
    }

    @Override
    public void validate(IValidationScope scope, Object o) {
        if (o instanceof Classifier) {
            Classifier cls = (Classifier) o;
            boolean hasXSDProfile =
                    BOMProfileUtils.isXSDProfileApplied(cls.getModel());

            if (!hasXSDProfile && BOMUtils.isExportAsTLE(cls)) {
                if (cls.getName() != null) {
                    String name = cls.getName().toLowerCase();
                    if (name != null) {
                        if (name.endsWith("element")) { //$NON-NLS-1$
                            // create issue
                            scope.createIssue(XSD_CLASSIFIER_TLENAME_ISSUE, cls
                                    .getLabel(), cls.eResource()
                                    .getURIFragment(cls));
                        }
                    }
                }
            }
        }
    }
}
