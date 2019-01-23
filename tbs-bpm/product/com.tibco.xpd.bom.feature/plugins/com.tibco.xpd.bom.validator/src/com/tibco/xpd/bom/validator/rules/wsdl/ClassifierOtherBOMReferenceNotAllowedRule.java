/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.bom.validator.rules.wsdl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.PrimitiveType;

import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.validator.WsdlIssueIds;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * A WSDL validation rule ensuring classifiers only have reference to other
 * classifiers within same BOM Model
 * 
 * @author glewis
 */
public class ClassifierOtherBOMReferenceNotAllowedRule extends AbstractWsdlRule {

    @Override
    public Class<?> getTargetClass() {
        return Classifier.class;
    }

    @Override
    public void performWSDLValidation(IValidationScope scope, Object o) {
        if (!enabledInCapabilities()) {
            return;
        }

        Classifier tempClassifier = (Classifier) o;
        String uri = tempClassifier.eResource().getURIFragment(tempClassifier);
        IFile bomFile = WorkspaceSynchronizer.getFile(tempClassifier
                .eResource());

        List<Generalization> gens = tempClassifier.getGeneralizations();
        if (gens.size() > 0) {
            Generalization gen = gens.get(0);
            Classifier superClassifier = gen.getGeneral();
            if (!(tempClassifier.eResource()
                    .equals(superClassifier.eResource()))) {
                Collection<String> standardPrimtiveTypeNames = PrimitivesUtil
                        .getStandardPrimtiveTypeNames(tempClassifier
                                .eResource().getResourceSet());
                boolean isIssue = true;
                for (String primName : standardPrimtiveTypeNames) {
                    PrimitiveType standardPrimType = PrimitivesUtil
                            .getStandardPrimitiveTypeByName(tempClassifier
                                    .eResource().getResourceSet(), primName);
                    if (superClassifier.equals(standardPrimType)) {
                        isIssue = false;
                        break;
                    }
                }
                if (isIssue) {
                    scope.createIssue(WsdlIssueIds.BOM_REF_NOT_ALLOWED,
                            BOMValidationUtil.getLocation((NamedElement) o),
                            uri, Collections
                                    .singleton(tempClassifier.getName()));
                }
            }
        }
    }
}
