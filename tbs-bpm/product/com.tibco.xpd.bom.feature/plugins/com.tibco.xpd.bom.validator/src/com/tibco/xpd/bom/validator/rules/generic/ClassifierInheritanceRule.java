/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.validator.rules.generic;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;

import com.tibco.xpd.bom.validator.GenericIssueIds;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * @author wzurek
 */
public class ClassifierInheritanceRule implements IValidationRule {

    List<org.eclipse.uml2.uml.Classifier> generalClasses;

    @Override
    public Class<?> getTargetClass() {
        return Classifier.class;
    }

    @Override
    public void validate(IValidationScope scope, Object o) {
        if (o instanceof Classifier) {
            org.eclipse.uml2.uml.Classifier cl = (Classifier) o;
            String uri = cl.eResource().getURIFragment(cl);

            if (cl.getGeneralizations() != null
                    && !cl.getGeneralizations().isEmpty()) {
                if (cl.getGeneralizations().size() > 1) {
                    scope.createIssue(GenericIssueIds.TYPE_SINGLE_INHERITANCE,
                            BOMValidationUtil.getLocation(cl),
                            uri);
                } else {
                    generalClasses = new LinkedList<Classifier>();
                    if (!checkGeneralizationLoop(cl)) {
                        scope.createIssue(
                                GenericIssueIds.TYPE_INHERITANCE_LOOP,
                                BOMValidationUtil.getLocation(cl),
                                uri);
                    }
                }
            }
        }

    }

    private boolean checkGeneralizationLoop(Classifier cl) {
        boolean result = true;
        Generalization generalization = cl.getGeneralizations().get(0);
        Classifier generalClass = generalization.getGeneral();
        if (generalClasses.contains(generalClass))
            return false;

        generalClasses.add(cl);
        if (generalClass != null
                && !generalClass.getGeneralizations().isEmpty()) {
            result = checkGeneralizationLoop(generalClass);
        }
        return result;
    }

}
